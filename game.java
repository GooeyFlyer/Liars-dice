import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;
import java.util.StringJoiner;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class game {

    private static tuple getUserInputChoice(Scanner scanner, String prompt, List<tuple> choices) {
        int userInput = 0;
        boolean isValidInput = false;

        printOptions(choices);

        while (!isValidInput) {
            System.out.print(prompt + ": ");

            // Check if the next input is an integer
            if (scanner.hasNextInt()) {
                userInput = scanner.nextInt();
                isValidInput = true; // Break out of the loop if input is valid
            } else {
                System.out.println("Invalid input. Please enter a valid integer.");
                scanner.next(); // Consume the invalid input to avoid an infinite loop
            }
        }

        return choices.get(userInput-1);
        // need index out of range error checking here
    }

    private static String getUserInputString(Scanner scanner, String prompt) {
        String userResponse = "";
        boolean isValidInput = false;

        while (!isValidInput) {
            System.out.print(prompt);

            // Check if the next input is a string
            if (scanner.hasNext()) {
                userResponse = scanner.next().toUpperCase(); // Convert to uppercase for case-insensitivity
                if (userResponse.equals("Y") || userResponse.equals("N")) {
                    isValidInput = true; // Break out of the loop if input is "Y" or "N"
                } else {
                    System.out.println("Invalid input. Please enter either 'Y' or 'N'.");
                }
            } else {
                System.out.println("Invalid input. Please enter either 'Y' or 'N'.");
                scanner.next(); // Consume the invalid input to avoid an infinite loop
            }
        }

        return userResponse;
    }
    
    public static void printOptions(List<tuple> choices) {
        int count = 0;
        for (tuple choice: choices) {
            count += 1;
            System.out.println(count + ". " + choice.getFirst() + "-" + choice.getSecond() + "s");
        }
    }

    public static boolean equateTuples(tuple tuple1, tuple tuple2) {
        if ((tuple1.getFirst() == tuple2.getFirst()) && (tuple1.getSecond() == tuple2.getSecond())) {
            return true;
        }
        else {
            return false;
        }
    }

    public static List<tuple> createChoices(int total, tuple lastBet) {
        List<tuple> finalList = new ArrayList<>();
        List<tuple> lesserList = new ArrayList<>();

        for (int i = 1; i < total+1; i++) {
            for (int x = 0; x < 7; x++) {
                finalList.add(new tuple<>(i, x));
            }
        }

        int index = -1;
        while (true) {
            index += 1;
            boolean equal = equateTuples(lastBet, finalList.get(index));

            if (!(equal)) {
                lesserList.add(finalList.get(index));
            }
            else if (equal) {
                break;
            }
        }

        finalList.removeAll(lesserList);
        finalList.remove(0);
        return finalList;
    }

    public static player swapPlayer(player player1, player player2, player currentPlayer) {
        if (currentPlayer == player1) {
            return player2;
        }
        else {
            return player1;
        }
    }

    public static boolean getCall(String input) {
        System.out.println(input);
        return "N".equalsIgnoreCase(input);
    }

    public static boolean isBetOver(List<Integer> rolls, tuple lastBet) {
        int countTarget = (int) lastBet.getFirst();
        int value = (int) lastBet.getSecond();

        int countValues = Collections.frequency(rolls, value);
        System.out.println(countValues < countTarget);
        return countValues < countTarget;
    }


    public static player round(player player1, player player2) {
        Scanner scanner = new Scanner(System.in); 
        
        boolean firstCall = true;
        boolean noCall = true;
        player currentPlayer = player1;
        tuple lastBet = new tuple<>(1, 1);

        while (noCall) {

            System.out.println("Current player is " + currentPlayer.getName());

            
            List<tuple> choices = createChoices(player1.getLives() + player2.getLives(), lastBet);
            // limit choices by changing createChoices using last bet here

            if (firstCall) {
                noCall = getCall("N");
                firstCall = false;
            }
            else{
                noCall = getCall(getUserInputString(scanner, "Will you call? Y/N "));
            }

            if (noCall) {
                tuple bet = getUserInputChoice(scanner, "Choose a bet", choices);
                lastBet = bet;
                System.out.println(bet);

                currentPlayer = swapPlayer(player1, player2, currentPlayer);
            }
        }

        List<Integer> rolls = player1.getDie();
        rolls.addAll(player2.getDie());
        System.out.println(rolls);
        if (isBetOver(rolls, lastBet)) {
            return swapPlayer(player1, player2, currentPlayer);
        }
        else {
            return currentPlayer;
        }
    }

    public static String listToString(List<Integer> list) {
        List<Integer> integerList = new ArrayList<>();
        integerList.add(1);
        integerList.add(2);
        integerList.add(3);
        integerList.add(4);

        // Using StringJoiner
        StringJoiner joiner = new StringJoiner(", ", "[", "]");
        for (Integer number : integerList) {
            joiner.add(number.toString());
        }

        return joiner.toString();
    }
    public static void main(String[] args) {

        /* JFrame frame = new JFrame("Perudo");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(300, 200);

        JLabel label = new JLabel("Hello, players!");

        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout());
        panel.add(label);
        frame.getContentPane().add(panel);

        // Set the frame to be visible
        frame.setVisible(true); */

        String name1 = "Tom";
        String name2 = "Leo";

        player player1 = new player(name1);
        player player2 = new player(name2);

        while ((player1.getLives() > 0) && (player2.getLives() > 0)) {
            

            player1.rollDie();
            player2.rollDie();

            System.out.println();
            System.out.println(player1.getName() + " has:");
            System.out.println(player1.getDie());

            /* String dieAsString = listToString(player1.getDie());
            JLabel player1JRolls = new JLabel(dieAsString);
            panel.add(player1JRolls); */

            System.out.println(player2.getName() + " has:");
            System.out.println(player2.getDie());

            round(player1, player2).lostLife();
 
        }

        System.out.println();
        if (player1.getLives() > player2.getLives()) {
            System.out.println(player1.getName() + " Wins");
        }
        else {
            System.out.println(player2.getName() + " Wins");
        }
    }
}