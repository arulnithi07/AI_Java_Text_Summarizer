# AI_Java_Text_Summarizer

This is a simple **Java-based text summarization tool** that uses the **Hugging Face Inference API** and a transformer model (BART) to generate concise summaries from long text inputs.

## 💡 Features
- Connects to the Hugging Face API using HTTP POST
- Uses the `facebook/bart-large-cnn` model for summarization
- Easy to modify and integrate into larger projects
- Works with any input text you provide

## 🛠️ Requirements
- Java 8 or above
- Internet connection
- A free Hugging Face account and API token

## 🔧 Setup Instructions
1. **Clone or Download the Repository**
2. **Replace the API Token** in `TextSummarizer.java`
3. **Compile and Run**
   ```
   javac TextSummarizer.java
   java TextSummarizer
   ```

## ✅ Example Output
```
Summary:
This Java program uses Hugging Face API to generate summaries.
```

## 📄 License
MIT License

## ✨ Credits
Developed as part of an internship project to demonstrate AI + Java integration.
