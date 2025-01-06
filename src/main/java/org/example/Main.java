package org.example;

import java.io.IOException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException {
        try (Scanner scanner = new Scanner(System.in);) {

            int gameMode = selectGameMode(scanner);
            String selectedWord = selectWord(scanner, gameMode);
            int difficulty = selectDifficulty(scanner);
            int tries = selectNumberOfTries(difficulty);

            System.out.print("\nDigite seu nome: ");
            scanner.nextLine();
            String playerName = scanner.nextLine();
            Player player = new Player(playerName);

            int roundTimeLimitInSeconds = 30;
            Hangman hangman = new Hangman(selectedWord, tries, roundTimeLimitInSeconds);

            long startTime = System.currentTimeMillis();

            while (!hangman.win() && !hangman.lose()) {
                System.out.println(
                        "\nPalavra: " + hangman.getHiddenWord()
                        + "\nTentativas erradas: " + hangman.getWrongGuesses()
                        + "\nTentativas restantes: " + hangman.getTries()
                );

                hangman.StartRound();
                System.out.println("**Você tem " + roundTimeLimitInSeconds + " segundos para tentar uma letra.");

                System.out.print("\nDigite uma letra: ");
                char letter = scanner.next().toUpperCase().charAt(0);

                if (!hangman.guess(letter)) {
                    System.out.println("\nVocê errou!");
                } else {
                    System.out.println("\nVocê acertou!");
                }
            }

            long finishTime = System.currentTimeMillis();
            long totalTime = (finishTime - startTime) / 1000;

            if (hangman.win()) {
                System.out.println("\nParabéns, " + player.getName() + "! Você venceu em " + totalTime + " segundos!");
            } else {
                System.out.println("\nVocê perdeu! A palavra era: " + selectedWord);
            }
        }
    }

    private static int selectGameMode(Scanner scanner) {
        System.out.println("MODOS DE JOGO:");
        System.out.println("1. Jogo Solo");
        System.out.println("2. Multijogador");
        System.out.print("\nEscolha um modo de jogo: ");
        return validateInput(scanner, 1, 2);
    }

    private static String selectWord(Scanner scanner, int gameMode) {
        String selectedWord = "";
        if (gameMode == 1) {
            try {
                WordBank bank = new WordBank("src/main/resources/words.txt");
                selectedWord = bank.selectWord();
            } catch (IOException e) {
                System.err.println("Erro ao carregar o banco de palavras.");
            }
        } else if (gameMode == 2) {
            System.out.print("\nJogador 1, digite a palavra secreta: ");
            scanner.nextLine();
            selectedWord = scanner.nextLine().trim().toUpperCase();
            System.out.println("Jogador 2, é a sua vez de adivinhar!");
        }
        return selectedWord;
    }

    private static int selectDifficulty(Scanner scanner) {
        System.out.println("\nDIFICULDADE:");
        System.out.println("1. Fácil");
        System.out.println("2. Médio");
        System.out.println("3. Difícil");
        System.out.print("\nEscolha o nível de dificuldade: ");
        return validateInput(scanner, 1, 3);
    }

    private static int selectNumberOfTries(int difficulty) {
        return switch (difficulty) {
            case 1 -> 10;
            case 2 -> 6;
            case 3 -> 4;
            default -> {
                System.out.println("\nOpção inválida! Escolhendo o nível médio por padrão.");
                yield 6;
            }
        };
    }

    private static int validateInput(Scanner scanner, int minValue, int maxValue) {
        int choice;
        do {
            choice = scanner.nextInt();
            if (choice < minValue || choice > maxValue) {
                System.out.println("Opção inválida. Por favor, escolha entre " + minValue + " e " + maxValue + ".");
            }
        } while (choice < minValue || choice > maxValue);
        return choice;
    }
}