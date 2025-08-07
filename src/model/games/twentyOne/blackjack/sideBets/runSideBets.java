package model.games.twentyOne.blackjack.sideBets;

/**
 * Runs simulations for side bets in blackjack.
 */
public class runSideBets {
  /**
   * Main method to run simulations.
   *
   * @param ignored command line arguments (not used)
   */
  public static void main(String[] ignored) {

  }
}

/*
Cut off percentage: 5
  Units won per hand (8 deck)  0.000471
  Units won per hand (6 deck)  0.000767
  Units won per hand (4 deck)  0.001355
  Units won per hand (2 deck)  0.004760
Cut off percentage: 15
  Units won per hand (8 deck)  0.000058
  Units won per hand (6 deck)  0.000138
  Units won per hand (4 deck)  0.000257
  Units won per hand (2 deck)  0.002087
Cut off percentage: 25
  Units won per hand (8 deck)  0.000008
  Units won per hand (6 deck)  0.000025
  Units won per hand (4 deck)  0.000046
  Units won per hand (2 deck)  0.000778
Cut off percentage: 35
  Units won per hand (8 deck)  0.000002
  Units won per hand (6 deck)  0.000001
  Units won per hand (4 deck)  0.000009
  Units won per hand (2 deck)  0.000378
Cut off percentage: 45
  Units won per hand (8 deck)  0.000001
  Units won per hand (6 deck)  0.000000
  Units won per hand (4 deck)  0.000002
  Units won per hand (2 deck)  0.000137
Cut off percentage: 55
  Units won per hand (8 deck)  0.000000
  Units won per hand (6 deck)  0.000000
  Units won per hand (4 deck)  0.000000
  Units won per hand (2 deck)  0.000045
Cut off percentage: 65
  Units won per hand (8 deck)  0.000000
  Units won per hand (6 deck)  0.000000
  Units won per hand (4 deck)  0.000000
  Units won per hand (2 deck)  0.000007
Cut off percentage: 75
  Units won per hand (8 deck)  0.000000
  Units won per hand (6 deck)  0.000000
  Units won per hand (4 deck)  0.000000
  Units won per hand (2 deck)  0.000001
 */

/*
for (int cutOffPercentage = 5; cutOffPercentage <= 75; cutOffPercentage += 10) {
  System.out.printf("Cut off percentage: %d", cutOffPercentage);
  System.out.println();

  MatchTheDealer matchTheDealerEight = new MatchTheDealer(8, 3, 14, 8 * 52 * cutOffPercentage / 100);
  matchTheDealerEight.printTheInfoHouseEdgePerTrueCount();
  MatchTheDealer matchTheDealerSix = new MatchTheDealer(6, 4, 11, 6 * 52 * cutOffPercentage / 100);
  matchTheDealerSix.printTheInfoHouseEdgePerTrueCount();
  MatchTheDealer matchTheDealerFour = new MatchTheDealer(4, 4, 12, 4 * 52 * cutOffPercentage / 100);
  matchTheDealerFour.printTheInfoHouseEdgePerTrueCount();
  MatchTheDealer matchTheDealerTwo = new MatchTheDealer(2, 4, 19, 2 * 52 * cutOffPercentage / 100);
  matchTheDealerTwo.printTheInfoHouseEdgePerTrueCount();
}
 */