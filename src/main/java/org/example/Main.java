package org.example;

import java.io.IOException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException {
        try (Scanner scanner = new Scanner(System.in);) {

            System.out.println("1. Jogo Solo");
            System.out.println("2. Multijogador");
            System.out.print("\nEscolha um modo de jogo:");
            int gameMode = scanner.nextInt();

            String selectedWord = "";

            if (gameMode == 1) {
                WordBank bank = new WordBank("src/main/resources/words.txt");
                selectedWord = bank.selectWord();
            } else if (gameMode == 2) {
                System.out.println("Jogador 1, digite a palavra secreta: ");
                scanner.nextLine();
                selectedWord = scanner.nextLine().trim().toUpperCase();
                System.out.println("Jogador 2, é a sua vez de adivinhar!");
            }

            System.out.println("\nEscolha o nível de dificuldade:");
            System.out.println("1. Fácil");
            System.out.println("2. Médio");
            System.out.println("3. Difícil");
            int level = scanner.nextInt();
            int lives = switch (level) {
                case 1 -> 10;
                case 2 -> 6;
                case 3 -> 4;
                default -> {
                    System.out.println("Opção inválida! Escolhendo o nível por padrão.");
                    yield 6;
                }
            };

            System.out.print("Digite seu nome: ");
            String playerName = scanner.nextLine();
            Player player = new Player(playerName);

            Hangman hangman = new Hangman(selectedWord, lives);

            long startTime = System.currentTimeMillis();

            while (!hangman.win() && !hangman.lose()) {
                System.out.println(
                        "\nPalavra: " + hangman.getHiddenWord()
                        + "\nTentativas erradas: " + hangman.getWrongGuesses()
                        + "\nTentativas restantes: " + hangman.getLives()
                );

                System.out.print("\nDigite uma letra: ");
                char letter = scanner.next().toUpperCase().charAt(0);

                if (!hangman.guess(letter)) {
                    System.out.println("Você errou!");
                } else {
                    System.out.println("Você acertou!");
                }
            }

            long finishTime = System.currentTimeMillis();
            long totalTime = (finishTime - startTime) / 1000;

            if (hangman.win()) {
                System.out.println("\nParabéns, " + player.getName() + "! Você venceu em " + totalTime + " segundos!");
            } else {
                System.out.println("\nVocê perdeu! A palavra era: " + selectedWord);
            }
        } catch (IOException e) {
            System.err.println("Erro ao carregar o banco de palavras: " + e.getMessage());
        }
    }
}