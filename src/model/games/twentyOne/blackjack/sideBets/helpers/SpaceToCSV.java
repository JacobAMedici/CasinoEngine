package model.games.twentyOne.blackjack.sideBets.helpers;

import java.util.Scanner;

public class SpaceToCSV {
  public static void main(String[] args) {
    Scanner scanner = new Scanner(System.in);
    System.out.println("Paste your input (Ctrl+D to end):");

    while (scanner.hasNextLine()) {
      String line = scanner.nextLine().trim();
      if (!line.isEmpty()) {
        // Split on one or more spaces
        String[] parts = line.split("\\s+");
        System.out.println(parts[2]);
//        if (parts.length == 3) {
//          System.out.println(String.join(",", parts));
//        } else {
//          System.err.println("Skipping line (unexpected format): " + line);
//        }
      }
    }

    scanner.close();
  }
}
