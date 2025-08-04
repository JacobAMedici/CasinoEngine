package model.games.dice.craps;

public class Main {

  public static void main(String[] args) {

    double sevenDelta = 0.999647;
    // For 10x odds
    // 0.996824207 or 0.3175793% relative 7 reduction will give a house edge of zero percent
    // That means if 7s are rolled at a frequency of 0.166137367833 or better, the player has an advantage
    // This is as opposed to the standard 0.166666666666667

    // For 100x odds
    // 0.999647 or 0.0353% relative 7 reduction will give a house edge of zero percent
    // That means if 7s are rolled at a frequency of 0.166607833333 or better, the player has an advantage
    // This is as opposed to the standard 0.166666666666667


    Long[] theoreticalComeOutDiceRolls = new Long[13];
    theoreticalComeOutDiceRolls[0] = 0L; // unused
    theoreticalComeOutDiceRolls[1] = 0L; // unused
    theoreticalComeOutDiceRolls[2] = 1_000_000_000L;
    theoreticalComeOutDiceRolls[3] = 2_000_000_000L;
    theoreticalComeOutDiceRolls[4] = 3_000_000_000L;
    theoreticalComeOutDiceRolls[5] = 4_000_000_000L;
    theoreticalComeOutDiceRolls[6] = 5_000_000_000L;
    theoreticalComeOutDiceRolls[7] = (long) (6_000_000_000L * (2 - sevenDelta));
    theoreticalComeOutDiceRolls[8] = 5_000_000_000L;
    theoreticalComeOutDiceRolls[9] = 4_000_000_000L;
    theoreticalComeOutDiceRolls[10] = 3_000_000_000L;
    theoreticalComeOutDiceRolls[11] = 2_000_000_000L;
    theoreticalComeOutDiceRolls[12] = 1_000_000_000L;

    Long[] theoreticalPointDiceRolls = new Long[13];
    theoreticalPointDiceRolls[0] = 0L; // unused
    theoreticalPointDiceRolls[1] = 0L; // unused
    theoreticalPointDiceRolls[2] = 1_000_000_000L;
    theoreticalPointDiceRolls[3] = 2_000_000_000L;
    theoreticalPointDiceRolls[4] = 3_000_000_000L;
    theoreticalPointDiceRolls[5] = 4_000_000_000L;
    theoreticalPointDiceRolls[6] = 5_000_000_000L;
    theoreticalPointDiceRolls[7] = (long) (6_000_000_000L * sevenDelta);
    theoreticalPointDiceRolls[8] = 5_000_000_000L;
    theoreticalPointDiceRolls[9] = 4_000_000_000L;
    theoreticalPointDiceRolls[10] = 3_000_000_000L;
    theoreticalPointDiceRolls[11] = 2_000_000_000L;
    theoreticalPointDiceRolls[12] = 1_000_000_000L;

    DiceGame diceGame = new DiceGame();
    diceGame.setPointDiceRolls(theoreticalPointDiceRolls, theoreticalComeOutDiceRolls);

    double houseEdge = diceGame.distributionHouseEdge(100, 100, 100);
    // Technically this should be out of the sum, not 36_000_000_000L, but this is a good approximation
    //    System.out.println("Seven delta % (total): " + (sevenDelta * 100.0 / 36_000_000_000L) + "%");
    //    System.out.println("Seven delta % (relative): " + (sevenDelta * 100.0 / 36_000_000_000L) + "%");

    System.out.printf("House Edge: %.4f%%%n", houseEdge * 100);
    System.out.printf("Player EV: %.4f%n", -houseEdge);
//    System.out.printf("Expected payout for a $1000 pass line with 10x odds: $%.2f%n", -houseEdge * 11_000);
//    System.out.printf("That is $%.2f/hour at 100 bets per hour%n", -houseEdge * 11_000 * 100);

// https://rec.gambling.craps.narkive.com/rDIloqRe/how-different-odds-multiples-affect-standard-deviation
// 10x odds, 1.410 SD as a function of betting units
//    System.out.printf("Standard Deviation per hour: $%.2f betting units%n", 1.410 * 7_667 * 10);
  }
}