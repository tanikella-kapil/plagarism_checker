## Getting Started

Welcome to the VS Code Java world. Here is a guideline to help you get started to write Java code in Visual Studio Code.
## Overview
The Plagiarism Detection System is a Java-based application that compares two text files to identify similarities and potential plagiarism. It makes use of advanced algorithms that help in the analysis of content within the files, which generates a similarity report for the originality of content. This system can be used by educators, students, content creators, and businesses for verifying the authenticity of written material.
## Features ✨
- Compare two text files for plagiarism 📂📂
- Generate a similarity report indicating the percentage of copied content 📊
- Case-insensitive comparison for better accuracy 🔄
- Simple user interface built with JavaFX for file selection 🖥️
## Technologies Used 💻
- **Java** (Core Java and JavaFX)
- **JavaFX** for the user interface 🎨
- **BufferedReader** and **FileReader** for reading files 📄
- **String manipulation** to detect similarities between files 🔠
## Requirements ⚙️
- JDK 8 or higher
- JavaFX (if running on an older version of JDK)
## How to Run 🚀
### 0.Clone the Repository
Clone the project to your local machine.(CLI)
### 1. Compile and Run
- Open the project in your preferred IDE (e.g., IntelliJ IDEA, Eclipse).
- Ensure that your project is configured to use JavaFX.
- Compile and run the `Main.java` file.

### 2. Using the Application
- When the application starts, a file chooser will appear 📁.
- Select two text files to compare 📄📄.
- The system will display the similarity percentage between the two files 📊.

## Code Structure 🏗️
- **Main.java**: The entry point of the application. It initializes the JavaFX application and handles the file comparison logic.
- **FileReader.java**: Contains methods for reading the content of the selected text files.
- **SimilarityChecker.java**: The core logic for comparing the two files and generating a similarity score.
