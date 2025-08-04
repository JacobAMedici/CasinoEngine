package model.games.dice.craps;

import java.util.Arrays;

public class DiceGame {
  // Initialize it to 12 spaces so a 0 or 1 could be rolled, it is true index as well
  private Long[] pointDiceRolls = new Long[13];
  private Long[] comeOutDiceRolls = new Long[13];
  private final double FOUR_TEN_PAYOUT = 2.0;
  private final double FIVE_NINE_PAYOUT = 1.5;
  private final double SIX_EIGHT_PAYOUT = 1.2;

  public DiceGame() {
    Arrays.fill(pointDiceRolls, 0L);
  }

  public void setPointDiceRolls(Long[] newDiceRolls, Long[] newComeOutDiceRolls) {
    pointDiceRolls = newDiceRolls;
    comeOutDiceRolls = newComeOutDiceRolls;
  }

  public double distributionHouseEdge(int fourTenOdds, int fiveNineOdds, int sixEightOdds) {
    long totalComeOutRolls = Arrays.stream(comeOutDiceRolls).mapToLong(Long::longValue).sum();
    double naturalProbability = (double) (comeOutDiceRolls[7] + comeOutDiceRolls[11]) / totalComeOutRolls;
    double crapsProbability = (double) (comeOutDiceRolls[2] + comeOutDiceRolls[3] + comeOutDiceRolls[12]) / totalComeOutRolls;

    double houseEdge = (naturalProbability * -1.0) + (crapsProbability * 1);
    double averageWagerAmount = 1.0;
    averageWagerAmount += (double) ((comeOutDiceRolls[4] + comeOutDiceRolls[10]) * fourTenOdds
        + (comeOutDiceRolls[5] + comeOutDiceRolls[9]) * fiveNineOdds
        + (comeOutDiceRolls[6] + comeOutDiceRolls[8]) * sixEightOdds) / totalComeOutRolls;


    // Four / Ten
    double fourProbability = (double) pointDiceRolls[4] / (pointDiceRolls[4] + pointDiceRolls[7]);
    double fourEV = fourProbability * (1 + fourTenOdds * FOUR_TEN_PAYOUT)
        + (1 - fourProbability) * -(1 + fourTenOdds);
    houseEdge -= fourEV * comeOutDiceRolls[4] / totalComeOutRolls;
    double tenProbability = (double) pointDiceRolls[10] / (pointDiceRolls[10] + pointDiceRolls[7]);
    double tenEV = tenProbability * (1 + fourTenOdds * FOUR_TEN_PAYOUT)
        + (1 - tenProbability) * -(1 + fourTenOdds);
    houseEdge -= tenEV * comeOutDiceRolls[10] / totalComeOutRolls;


    // Five / Nine
    double fiveProbability = (double) pointDiceRolls[5] / (pointDiceRolls[5] + pointDiceRolls[7]);
    double fiveEV = fiveProbability * (1 + fiveNineOdds * FIVE_NINE_PAYOUT)
        + (1 - fiveProbability) * -(1 + fiveNineOdds);
    houseEdge -= fiveEV * comeOutDiceRolls[5] / totalComeOutRolls;
    double nineProbability = (double) pointDiceRolls[9] / (pointDiceRolls[9] + pointDiceRolls[7]);
    double nineEV = nineProbability * (1 + fiveNineOdds * FIVE_NINE_PAYOUT)
        + (1 - nineProbability) * -(1 + fiveNineOdds);
    houseEdge -= nineEV * comeOutDiceRolls[9] / totalComeOutRolls;


    // Six / Eight
    double sixProbability = (double) pointDiceRolls[6] / (pointDiceRolls[6] + pointDiceRolls[7]);
    double sixEV = sixProbability * (1 + sixEightOdds * SIX_EIGHT_PAYOUT)
        + (1 - sixProbability) * -(1 + sixEightOdds);
    houseEdge -= sixEV * comeOutDiceRolls[6] / totalComeOutRolls;
    double eightProbability = (double) pointDiceRolls[8] / (pointDiceRolls[8] + pointDiceRolls[7]);
    double eightEV = eightProbability * (1 + sixEightOdds * SIX_EIGHT_PAYOUT)
        + (1 - eightProbability) * -(1 + sixEightOdds);
    houseEdge -= eightEV * comeOutDiceRolls[8] / totalComeOutRolls;

    return houseEdge / averageWagerAmount;
  }

}
