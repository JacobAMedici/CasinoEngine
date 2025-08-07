package model.games.dice.craps;

/**
 * Main class to demonstrate the calculation of house edge in a craps game.
 */
public class Main {

  /**
   * Main method to run the craps house edge calculation.
   *
   * @param ignored command line arguments (not used)
   */
  public static void main(String[] ignored) {

    double sevenDelta = 0.999705;
    // 0x - 0.963684
    // 1x - 0.983644
    // 2x - 0.989445
    // 2X-2X-2.5X - 0.989945
    // 3X-4X-5X - 0.993692
    // 5X - 0.994886
    // 10X - 0.997250
    // 20X - 0.998571
    // 100X - 0.999705

    CrapsHouseEdge crapsHouseEdge = new CrapsHouseEdge(sevenDelta);

    double houseEdge = crapsHouseEdge.distributionHouseEdge(100, 100, 100);

    System.out.printf("House Edge: %.4f%%%n", houseEdge * 100);
//    System.out.printf("Player EV: %.4f%n", -houseEdge);
//    System.out.printf("Expected payout for a $1000 pass line with 10x odds: $%.2f%n", -houseEdge * 11_000);
//    System.out.printf("That is $%.2f/hour at 100 bets per hour%n", -houseEdge * 11_000 * 100);
  }
}