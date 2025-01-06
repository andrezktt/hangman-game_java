package org.example;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class WordBank {
    private List<String> words;

    private void loadWords(String path) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(path));
        String line;
        while ((line = reader.readLine()) != null) {
            words.add(line.trim().toUpperCase());
        }
        reader.close();
    }

    public void wordBank(String path) throws IOException {
        words = new ArrayList<>();
        loadWords(path);
    }

    public String selectWord() {
        Random rand = new Random();
        return words.get(rand.nextInt(words.size()));
    }
}
