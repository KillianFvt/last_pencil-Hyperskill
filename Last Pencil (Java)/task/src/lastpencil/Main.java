package lastpencil;

import java.util.InputMismatchException;
import java.util.Random;
import java.util.Scanner;

public class Main {

    private static final String player1 = "John", player2 = "Jack";
    private static String currentPlayer;
    private static Integer pencilAmt;
    private static boolean isGameRunning = true;
    private static final Scanner scanner = new Scanner(System.in);


    public static void main(String[] args) {
        System.out.println("How many pencils would you like to use:");
        pencilAmt = null;

        while (pencilAmt == null  || pencilAmt <= 0) {
            try {

                String input = scanner.nextLine();

                if (input.trim().isBlank()) {
                    throw new NumberFormatException();
                }

                pencilAmt = Integer.parseInt(input);

                if (pencilAmt <= 0) {
                    System.out.println("The number of pencils should be positive");
                }

            } catch (NumberFormatException e) {
                System.out.println("The number of pencils should be numeric");
            }
        }

        System.out.printf("Who will be the first (%s, %s):\n", player1, player2);

        do {
            final String firstToPlay = scanner.nextLine();

            if (!firstToPlay.equals(player1) && !firstToPlay.equals(player2)) {
                System.out.println("Choose between '" + player1 + "' and '" + player2 + "'");
            } else {
                currentPlayer = firstToPlay;
            }

        } while (currentPlayer == null);

        while (isGameRunning) {
            runGame();
        }

        scanner.close();
    }

    private static void runGame() {
        displayPencils();

        System.out.println(currentPlayer + "'s turn!");

        if (currentPlayer.equals(player1)) {
            playerTurn();
        } else {
            botTurn();
        }

        if (pencilAmt <= 0) {
            isGameRunning = false;
            switchPlayer();
            System.out.println(currentPlayer + " won!");
        } else {
            switchPlayer();
        }
    }

    private static void playerTurn() {
        int toRemove = -1;

        do {
            try {
                toRemove = scanner.nextInt();
            } catch (InputMismatchException e) {
                System.out.println();
                System.out.println("Possible values: '1', '2' or '3'");
                scanner.nextLine();
                continue;
            }

            if (toRemove <= 0 || toRemove > 3) {
                System.out.println("Possible values: '1', '2' or '3'");
                scanner.nextLine();
            } else if (toRemove > pencilAmt) {
                System.out.println("Too many pencils were taken");
            }
        } while (toRemove <= 0 || toRemove > 3 || toRemove > pencilAmt);

        removePencils(toRemove);
    }

    private static void botTurn() {

        // if only one pencil is left, the bot has lost
        if (pencilAmt == 1) {
            System.out.println(1);
            removePencils(1);
            return;
        }

        final int toRemove;
        final int pencilChunk;

        pencilChunk = (pencilAmt - 1) % 4;

        if (pencilChunk == 3) {
            toRemove = 3;
        } else if (pencilChunk == 2) {
            toRemove = 2;
        } else if (pencilChunk == 1) {
            toRemove = 1;
        } else {
            Random rd = new Random();
            toRemove = rd.nextInt(1, 4);
        }

        System.out.println(toRemove);
        removePencils(toRemove);
    }

    private static void displayPencils() {
        for (int i = 0; i < pencilAmt; i++) {
            System.out.print('|');
        }
        System.out.print("\n");
    }

    private static void removePencils(int amt) {
        pencilAmt -= amt;
    }

    private static void switchPlayer() {
        currentPlayer = currentPlayer.equals(player1) ? player2 : player1;
    }
}
