package model.games.twentyOne.blackjack;

import java.math.BigDecimal;

/**
 * Standard implementation of BlackjackRules for typical casino play.
 */
public class BlackjackRules implements Rules {

  private final BigDecimal blackjackPayout;
  private final int numberOfDecks;
  private final int deckPenetration;
  private final int maxSplits;
  private final DOUBLE_RULE doubleRule;
  private final DOUBLE_AFTER_SPLIT_RULE doubleAfterSplit;
  private final RESPLIT_ACES_RULE resplitAcesRule;
  private final DEALER_ACTION_SOFT_SEVENTEEN dealerActionSoftSeventeen;
  private final SURRENDER_RULE surrenderRule;
  private final HOLE_CARD_RULE holeCard;

  /**
   * Constructs a BlackjackRules object with the specified game rule parameters.
   *
   * @param blackjackPayout           the payout multiplier for a player Blackjack (e.g., 3:2 as 1.5)
   * @param numberOfDecks             the number of decks used in the game
   * @param deckPenetration           the percentage of the shoe dealt before reshuffling (0–100)
   * @param maxSplits                 the maximum number of times a player can split their hand
   * @param doubleRule                the rule defining when a player may double down
   * @param doubleAfterSplit          the rule defining whether doubling after a split is allowed
   * @param resplitAcesRule           the rule defining whether Aces may be resplit after splitting
   * @param dealerActionSoftSeventeen the rule defining whether the dealer hits or stands on soft 17
   * @param surrenderRule             the rule defining if and when surrendering is allowed
   * @param holeCard                  the rule defining whether the dealer peeks for Blackjack (American or European style)
   */
  public BlackjackRules(BigDecimal blackjackPayout,
                        int numberOfDecks,
                        int deckPenetration,
                        int maxSplits,
                        DOUBLE_RULE doubleRule,
                        DOUBLE_AFTER_SPLIT_RULE doubleAfterSplit,
                        RESPLIT_ACES_RULE resplitAcesRule,
                        DEALER_ACTION_SOFT_SEVENTEEN dealerActionSoftSeventeen,
                        SURRENDER_RULE surrenderRule,
                        HOLE_CARD_RULE holeCard) {
    this.blackjackPayout = blackjackPayout;
    this.numberOfDecks = numberOfDecks;
    this.deckPenetration = deckPenetration;
    this.maxSplits = maxSplits;
    this.doubleRule = doubleRule;
    this.doubleAfterSplit = doubleAfterSplit;
    this.resplitAcesRule = resplitAcesRule;
    this.dealerActionSoftSeventeen = dealerActionSoftSeventeen;
    this.surrenderRule = surrenderRule;
    this.holeCard = holeCard;
  }

  // BJ-1.5_6D_75_3_DATC_DAS_RSA_S17_EARLY-S_American
  // BJ-1.2_2D_50_4_DTE_NDAS_NRSA_H17_Late-S_American
  // BJ-2.0_8D_100_3_DNTE_DAS_RSA_S17_No-S_European

  @Override
  public BigDecimal getBlackjackPayout() {
    return blackjackPayout;
  }

  @Override
  public int getNumberOfDecks() {
    return numberOfDecks;
  }

  @Override
  public int getDeckPenetration() {
    return deckPenetration;
  }

  @Override
  public int getMaxSplits() {
    return maxSplits;
  }

  @Override
  public DOUBLE_RULE getDoubleRule() {
    return doubleRule;
  }

  @Override
  public DOUBLE_AFTER_SPLIT_RULE getDoubleAfterSplitRule() {
    return doubleAfterSplit;
  }

  @Override
  public RESPLIT_ACES_RULE getResplitAcesRule() {
    return resplitAcesRule;
  }

  @Override
  public DEALER_ACTION_SOFT_SEVENTEEN getStandRule() {
    return dealerActionSoftSeventeen;
  }

  @Override
  public SURRENDER_RULE getSurrenderRule() {
    return surrenderRule;
  }

  @Override
  public HOLE_CARD_RULE getHoleCardRule() {
    return holeCard;
  }

  @Override
  public String toString() {
    return String.format(
        "BJ-%.1f_%dD_%d_%d_%s_%s_%s_%s_%s_%s",
        blackjackPayout.doubleValue(),                      // e.g., 1.5
        numberOfDecks,                                      // e.g., 6
        deckPenetration,                                    // e.g., 75
        maxSplits,                                          // e.g., 3
        switch (doubleRule) {
          case ANY_TWO_CARDS -> "DATC";
          case TEN_ELEVEN -> "DTE";
          case NINE_TEN_ELEVEN -> "DNTE";
          case ELEVEN_ONLY -> "DE";
        },
        doubleAfterSplit == DOUBLE_AFTER_SPLIT_RULE.DOUBLE_AFTER_SPLIT_ALLOWED ? "DAS" : "NDAS",
        switch (resplitAcesRule) {
          case NO_RESPLIT_ACES -> "NRSA";
          case RESPLIT_ACES_ONCE -> "RSA1";
          case RESPLIT_ACES_MAX_SPLIT_TIMES -> "RSA";
        },
        dealerActionSoftSeventeen == DEALER_ACTION_SOFT_SEVENTEEN.STAND_ON_SOFT_17 ? "S17" : "H17",
        switch (surrenderRule) {
          case NO_SURRENDER -> "No-S";
          case EARLY_SURRENDER -> "EARLY-S";
          case LATE_SURRENDER -> "Late-S";
        },
        holeCard == HOLE_CARD_RULE.AMERICAN_STYLE ? "American" : "European"
    );
  }
}
