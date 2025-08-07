package model.games.dice.craps;

/**
 * Models a die game with adjustable probabilities and calculates the house edge
 * based on various betting odds.
 */
public class CrapsHouseEdge {

  private final double sevenDelta;

  /**
   * Constructs a DiceGame with a specified adjustment factor for the probability of rolling a seven.
   *
   * @param sevenDelta the adjustment factor for the probability of rolling a seven (between 0 and 1)
   */
  public CrapsHouseEdge(double sevenDelta) {
    this.sevenDelta = sevenDelta;
  }

  /**
   * Calculates the house edge for a craps game given the odds for different bets.
   *
   * @param sixEightOdds the odds for betting on 6 or 8
   * @param fiveNineOdds the odds for betting on 5 or 9
   * @param fourTenOdds  the odds for betting on 4 or 10
   * @return the house edge as a decimal (e.g., 0.01 for 1%)
   */
  public double distributionHouseEdge(double sixEightOdds, double fiveNineOdds, double fourTenOdds) {
    double FOUR_TEN_PAYOUT = 2.0;
    double FIVE_NINE_PAYOUT = 1.5;
    double SIX_EIGHT_PAYOUT = 1.2;

    double naturalProbability = getValueProbability(7) + getValueProbability(11);
    double crapsProbability = getValueProbability(2) + getValueProbability(3) + getValueProbability(12);

    double fourTenEV;
    double fiveNineEV;
    double sixEightEV;

    // Four / Ten
    double fourTenWinProbability = getValueProbabilitySD(4) / (getValueProbabilitySD(4) + getValueProbabilitySD(7));
    fourTenEV = (1 - fourTenWinProbability) * (1 + fourTenOdds)
        - (fourTenWinProbability) * (1 + fourTenOdds * FOUR_TEN_PAYOUT);

    // Five / Nine
    double fiveNineWinProbability = getValueProbabilitySD(5) / (getValueProbabilitySD(5) + getValueProbabilitySD(7));
    fiveNineEV = (1 - fiveNineWinProbability) * (1 + fiveNineOdds)
        - (fiveNineWinProbability) * (1 + fiveNineOdds * FIVE_NINE_PAYOUT);


    // Six / Eight
    double sixEightWinProbability = getValueProbabilitySD(6) / (getValueProbabilitySD(6) + getValueProbabilitySD(7));
    sixEightEV = (1 - sixEightWinProbability) * (1 + sixEightOdds)
        - (sixEightWinProbability) * (1 + sixEightOdds * SIX_EIGHT_PAYOUT);

    double houseEdge = -naturalProbability
        + crapsProbability
        + fourTenEV * (getValueProbability(4) + getValueProbability(10))
        + fiveNineEV * (getValueProbability(5) + getValueProbability(9))
        + sixEightEV * (getValueProbability(6) + getValueProbability(8));

    double averageBet = 1.0 * naturalProbability
        + 1.0 * crapsProbability
        + (getValueProbability(4) + getValueProbability(10)) * (1 + fourTenOdds)
        + (getValueProbability(5) + getValueProbability(9)) * (1 + fiveNineOdds)
        + (getValueProbability(6) + getValueProbability(8)) * (1 + sixEightOdds);

    return houseEdge / averageBet;
  }

  private double getValueProbability(int value) {
    return getValueProbability(value, false);
  }

  private double getValueProbabilitySD(int value) {
    return getValueProbability(value, true);
  }

  private double getValueProbability(int value, boolean useSevenDelta) {
    int NON_SEVEN_COMBOS = 30;
    int TOTAL_COMBOS = 36;

    double numberOfCombos = 0.0;

    if (value == 2 || value == 12) {
      numberOfCombos = 1.0;
    } else if (value == 3 || value == 11) {
      numberOfCombos = 2.0;
    } else if (value == 4 || value == 10) {
      numberOfCombos = 3.0;
    } else if (value == 5 || value == 9) {
      numberOfCombos = 4.0;
    } else if (value == 6 || value == 8) {
      numberOfCombos = 5.0;
    } else if (value == 7) {
      if (useSevenDelta) {
        return 6.0 * sevenDelta / TOTAL_COMBOS;
      } else {
        return 6.0 / TOTAL_COMBOS;
      }
    }

    if (useSevenDelta) {
      double removedSevenMass = (1.0 - sevenDelta) * 6.0 / TOTAL_COMBOS;
      return numberOfCombos / TOTAL_COMBOS + numberOfCombos / NON_SEVEN_COMBOS * removedSevenMass;
    } else {
      return numberOfCombos / TOTAL_COMBOS;
    }
  }
}
