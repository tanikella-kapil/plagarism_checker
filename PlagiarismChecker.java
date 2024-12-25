import java.io.*;
import java.util.*;

public class PlagiarismChecker {

    public static void main(String[] args) throws IOException {
        // Paths to the two files
        String filePath1 = "plagarismfile1.txt";
        String filePath2 = "plagarismfile2.txt";

        // Read the files
        String text1 = readFile(filePath1);
        String text2 = readFile(filePath2);

        // Tokenize and create word frequency maps
        Map<String, Integer> freqMap1 = getWordFrequency(text1);
        Map<String, Integer> freqMap2 = getWordFrequency(text2);

        // Calculate cosine similarity
        double similarity = calculateCosineSimilarity(freqMap1, freqMap2);

        // Display the result
        System.out.printf("Similarity between files: %.2f%%\n", similarity * 100);
    }

    // Read content from a file
    public static String readFile(String filePath) throws IOException {
        StringBuilder content = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                content.append(line).append(" ");
            }
        }
        return content.toString().toLowerCase(); // Convert to lowercase for uniformity
    }

    // Create a word frequency map
    public static Map<String, Integer> getWordFrequency(String text) {
        Map<String, Integer> freqMap = new HashMap<>();
        String[] words = text.split("\\W+"); // Split by non-word characters
        for (String word : words) {
            if (!word.isEmpty()) {
                freqMap.put(word, freqMap.getOrDefault(word, 0) + 1);
            }
        }
        return freqMap;
    }

    // Calculate cosine similarity between two frequency maps
    public static double calculateCosineSimilarity(Map<String, Integer> freqMap1, Map<String, Integer> freqMap2) {
        Set<String> uniqueWords = new HashSet<>();
        uniqueWords.addAll(freqMap1.keySet());
        uniqueWords.addAll(freqMap2.keySet());

        // Compute dot product and magnitudes
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

        // Avoid division by zero
        if (magnitude1 == 0 || magnitude2 == 0) {
            return 0.0;
        }

        return dotProduct / (Math.sqrt(magnitude1) * Math.sqrt(magnitude2));
    }
}
