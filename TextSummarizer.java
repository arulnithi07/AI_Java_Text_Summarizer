import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class TextSummarizer {

    private static final String API_URL = "https://api-inference.huggingface.co/models/facebook/bart-large-cnn";
    private static final String API_TOKEN = "YOUR_HUGGINGFACE_API_TOKEN"; // Replace with your actual token

    public static void main(String[] args) {
        String text = "Your input text to summarize goes here. "
                + "This Java program connects to the Hugging Face inference API "
                + "to generate a concise summary.";

        try {
            String summary = summarize(text);
            System.out.println("Summary:");
            System.out.println(summary);
        } catch (Exception e) {
            System.err.println("Error during summarization: " + e.getMessage());
        }
    }

    public static String summarize(String inputText) throws Exception {
        URL url = new URL(API_URL);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();

        conn.setRequestMethod("POST");
        conn.setRequestProperty("Authorization", "Bearer " + API_TOKEN);
        conn.setRequestProperty("Content-Type", "application/json");
        conn.setDoOutput(true);

        String jsonInput = "{"inputs":"" + escapeJson(inputText) + ""}";

        try (OutputStream os = conn.getOutputStream()) {
            byte[] input = jsonInput.getBytes("utf-8");
            os.write(input, 0, input.length);
        }

        int code = conn.getResponseCode();

        BufferedReader br;
        if (code >= 200 && code < 300) {
            br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "utf-8"));
        } else {
            br = new BufferedReader(new InputStreamReader(conn.getErrorStream(), "utf-8"));
            throw new Exception("HTTP error code: " + code + ". Message: " + br.readLine());
        }

        StringBuilder response = new StringBuilder();
        String responseLine;

        while ((responseLine = br.readLine()) != null) {
            response.append(responseLine.trim());
        }

        br.close();

        // Parse summary from JSON response
        return parseSummaryFromResponse(response.toString());
    }

    private static String parseSummaryFromResponse(String jsonResponse) {
        int start = jsonResponse.indexOf(""summary_text":"");
        if (start == -1) {
            return "No summary found in response.";
        }
        start += ""summary_text":"".length();
        int end = jsonResponse.indexOf(""", start);
        if (end == -1) {
            return "No summary found in response.";
        }
        return jsonResponse.substring(start, end);
    }

    private static String escapeJson(String text) {
        return text.replace(""", "\"");
    }
}
