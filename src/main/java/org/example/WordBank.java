package org.example;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class WordBank {
    private List<String> words;

    public WordBank(String filePath) throws IOException {
        words = new ArrayList<>();
        try {
            loadWords(filePath);
        } catch (IOException e) {
            throw new IOException("Erro ao carregar o banco de palavras do arquivo: " + filePath, e);
        }
    }

    private void loadWords(String filePath) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath));) {
            String line;
            while ((line = reader.readLine()) != null) {
                words.add(line.trim().toUpperCase());
            }
        }
    }

    public String selectWord() {
        Random rand = new Random();
        return words.get(rand.nextInt(words.size()));
    }
}
