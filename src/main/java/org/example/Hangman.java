package org.example;

import java.util.HashSet;
import java.util.Set;

public class Hangman {

    private String word;
    private StringBuilder hiddenWord;
    private Set<Character> wrongGuesses;
    private int tries;
    private long roundMaxTime;
    private long roundStartTime;

    private static final String[] HANGMAN_PICTURES = {
            "  _______\n |     |\n |\n |\n |\n |",
            "  _______\n |     |\n |     O\n |\n |\n |",
            "  _______\n |     |\n |     O\n |     |\n |\n |",
            "  _______\n |     |\n |     O\n |    /|\n |\n |",
            "  _______\n |     |\n |     O\n |    /|\\\n |\n |",
            "  _______\n |     |\n |     O\n |    /|\\\n |    /\n |",
            "  _______\n |     |\n |     O\n |    /|\\\n |    / \\\n |"
    };

    public Hangman(String word, int lives, int roundTimeSec) {
        this.word = word;
        this.hiddenWord = new StringBuilder("_".repeat(word.length()));
        this.wrongGuesses = new HashSet<>();
        this.tries = lives;
        this.roundMaxTime = roundTimeSec * 1000L;
    }

    public void showHangman() {
        int mistakes = wrongGuesses.size();
        System.out.println(HANGMAN_PICTURES[mistakes]);
    }

    public void StartRound() {
        roundStartTime = System.currentTimeMillis();
    }

    public boolean isTimeOut() {
        long elapsedTime = System.currentTimeMillis() - roundStartTime;
        return elapsedTime > roundMaxTime;
    }

    public boolean guess(char letter) {
        if (isTimeOut()) {
            System.out.println("\nO tempo para esta rodada acabou!");
            return false;
        }

        if (wrongGuesses.contains(letter) || hiddenWord.toString().contains(String.valueOf(letter))) {
            System.out.println("Você já selecionou esta letra!");
            return false;
        }

        if (word.contains(String.valueOf(letter))) {
            updateHiddenWord(letter);
            return true;
        } else {
            wrongGuesses.add(letter);
            // showHangman();
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
        return wrongGuesses.size() >= tries;
    }

    public String getHiddenWord() {
        return hiddenWord.toString();
    }

    public int getTries() {
        return tries - wrongGuesses.size();
    }

    public Set<Character> getWrongGuesses() {
        return wrongGuesses;
    }
}
