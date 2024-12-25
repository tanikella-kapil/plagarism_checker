import javafx.application.Application;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import java.io.*;
import java.util.*;

public class PlagiarismCheckerGUI extends Application {

    private File file1, file2;
    private Label resultLabel;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Plagiarism Checker");

        // Create buttons
        Button uploadFile1Btn = new Button("Upload File 1");
        Button uploadFile2Btn = new Button("Upload File 2");
        Button checkPlagiarismBtn = new Button("Check Plagiarism");
        Button saveReportBtn = new Button("Save Report");

        // Labels for file paths and results
        Label file1Label = new Label("File 1: Not selected");
        Label file2Label = new Label("File 2: Not selected");
        resultLabel = new Label("Result: Similarity not calculated yet");

        // Layout
        VBox layout = new VBox(10);
        layout.setStyle("-fx-padding: 20;");
        layout.getChildren().addAll(uploadFile1Btn, file1Label, uploadFile2Btn, file2Label, checkPlagiarismBtn, resultLabel, saveReportBtn);

        // Button actions
        uploadFile1Btn.setOnAction(e -> {
            file1 = chooseFile(primaryStage);
            file1Label.setText("File 1: " + (file1 != null ? file1.getName() : "Not selected"));
        });

        uploadFile2Btn.setOnAction(e -> {
            file2 = chooseFile(primaryStage);
            file2Label.setText("File 2: " + (file2 != null ? file2.getName() : "Not selected"));
        });

        checkPlagiarismBtn.setOnAction(e -> {
            if (file1 != null && file2 != null) {
                try {
                    String text1 = readFile(file1);
                    String text2 = readFile(file2);

                    Map<String, Integer> freqMap1 = getWordFrequency(text1);
                    Map<String, Integer> freqMap2 = getWordFrequency(text2);

                    double similarity = calculateCosineSimilarity(freqMap1, freqMap2);
                    resultLabel.setText(String.format("Result: Similarity = %.2f%%", similarity * 100));
                } catch (IOException ex) {
                    resultLabel.setText("Error: Unable to read files.");
                }
            } else {
                resultLabel.setText("Please select both files.");
            }
        });

        saveReportBtn.setOnAction(e -> {
            if (file1 != null && file2 != null && resultLabel.getText().contains("Similarity")) {
                saveReport(primaryStage, resultLabel.getText());
            } else {
                resultLabel.setText("Check plagiarism before saving the report.");
            }
        });

        // Scene and stage setup
        Scene scene = new Scene(layout, 400, 300);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    // File chooser method
    private File chooseFile(Stage stage) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select File");
        return fileChooser.showOpenDialog(stage);
    }

    // Read content from a file
    private String readFile(File file) throws IOException {
        StringBuilder content = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                content.append(line).append(" ");
            }
        }
        return content.toString().toLowerCase();
    }

    // Create word frequency map
    private Map<String, Integer> getWordFrequency(String text) {
        Map<String, Integer> freqMap = new HashMap<>();
        String[] words = text.split("\\W+");
        for (String word : words) {
            if (!word.isEmpty()) {
                freqMap.put(word, freqMap.getOrDefault(word, 0) + 1);
            }
        }
        return freqMap;
    }

    // Calculate cosine similarity
    private double calculateCosineSimilarity(Map<String, Integer> freqMap1, Map<String, Integer> freqMap2) {
        Set<String> uniqueWords = new HashSet<>();
        uniqueWords.addAll(freqMap1.keySet());
        uniqueWords.addAll(freqMap2.keySet());

        double dotProduct = 0.0;
        double magnitude1 = 0.0;
        double magnitude2 = 0.0;

        for (String word : uniqueWords) {
            int freq1 = freqMap1.getOrDefault(word, 0);
            int freq2 = freqMap2.getOrDefault(word, 0);

            dotProduct += freq1 * freq2;
            magnitude1 += Math.pow(freq1, 2);
            magnitude2 += Math.pow(freq2, 2);
        }

        if (magnitude1 == 0 || magnitude2 == 0) {
            return 0.0;
        }

        return dotProduct / (Math.sqrt(magnitude1) * Math.sqrt(magnitude2));
    }

    // Save report to a text file
    private void saveReport(Stage stage, String result) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save Report");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Text Files", "*.txt"));
        File saveFile = fileChooser.showSaveDialog(stage);

        if (saveFile != null) {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(saveFile))) {
                writer.write("Plagiarism Checker Report\n");
                writer.write("=========================\n");
                writer.write(result);
                resultLabel.setText("Report saved successfully.");
            } catch (IOException ex) {
                resultLabel.setText("Error: Unable to save the report.");
            }
        }
    }
}
