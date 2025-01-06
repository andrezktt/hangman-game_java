package org.example;

import java.util.HashSet;
import java.util.Set;

public class Hangman {

    private String word;
    private StringBuilder hiddenWord;
    private Set<Character> wrongGuesses;
    private Set<Character> rightGuesses;
    private int lives;

    public Hangman(String word, int lives) {
        this.word = word;
        this.hiddenWord = new StringBuilder("_".repeat(word.length()));
        this.wrongGuesses = new HashSet<>();
        this.rightGuesses = new HashSet<>();
        this.lives = lives;
    }

    public boolean guess(char letter) {
        if (word.contains(String.valueOf(letter))) {
            rightGuesses.add(letter);
            updateHiddenWord(letter);
            return true;
        } else {
            wrongGuesses.add(letter);
            return false;
        }
    }

    private void updateHiddenWord(char letter) {
        for (int i = 0; i < word.length(); i++) {
            if (word.charAt(i) == letter) {
                hiddenWord.setCharAt(i, letter);
            }
        }
    }

    public boolean win() {
        return hiddenWord.toString().equals(word);
    }

    public boolean lose() {
        return wrongGuesses.size() >= lives;
    }

    public String getHiddenWord() {
        return hiddenWord.toString();
    }

    public int getLives() {
        return lives - wrongGuesses.size();
    }

    public Set<Character> getWrongGuesses() {
        return wrongGuesses;
    }
}
