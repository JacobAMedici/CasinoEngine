package model.games.twentyOne.blackjack;

import java.math.BigDecimal;

/**
 * Represents the rules configuration for a Blackjack game.
 */
public interface Rules {
  /**
   * Represents when doubling down is allowed.
   */
  enum DOUBLE_RULE {
    ANY_TWO_CARDS,
    NINE_TEN_ELEVEN,
    TEN_ELEVEN,
    ELEVEN_ONLY
  }

  /**
   * Represents when doubling down is allowed after split.
   */
  enum DOUBLE_AFTER_SPLIT_RULE {
    DOUBLE_AFTER_SPLIT_ALLOWED,
    DOUBLE_AFTER_SPLIT_FORBIDDEN
  }

  /**
   * Represents how splitting pairs is handled.
   */
  enum RESPLIT_ACES_RULE {
    NO_RESPLIT_ACES,
    RESPLIT_ACES_ONCE,
    RESPLIT_ACES_MAX_SPLIT_TIMES
  }

  /**
   * Represents the dealer's behavior on soft 17.
   */
  enum DEALER_ACTION_SOFT_SEVENTEEN {
    STAND_ON_SOFT_17,
    HIT_SOFT_17
  }

  /**
   * Represents the surrender options available to the player.
   */
  enum SURRENDER_RULE {
    NO_SURRENDER,
    LATE_SURRENDER,
    EARLY_SURRENDER
  }

  /**
   * Represents if the dealer draws a hole card and peeks for blackjack.
   */
  enum HOLE_CARD_RULE {
    AMERICAN_STYLE,
    EUROPEAN_STYLE
  }

  /**
   * Returns the payout ratio for a player Blackjack.
   *
   * @return the Blackjack payout as a BigDecimal (e.g., 1.5 for 3:2)
   */
  BigDecimal getBlackjackPayout();

  /**
   * Returns the number of decks used in the shoe.
   *
   * @return the number of decks
   */
  int getNumberOfDecks();

  /**
   * Returns the number of cards after the cut card.
   *
   * @return the number of cards after the cut card
   */
  int getDeckPenetration();

  /**
   * Returns the maximum number of allowed splits.
   *
   * @return the number of splits allowed
   */
  int getMaxSplits();

  /**
   * Returns the rules for doubling down.
   *
   * @return the DoubleRule in effect
   */
  DOUBLE_RULE getDoubleRule();

  /**
   * Returns the rules for doubling after splitting.
   *
   * @return the DoubleAfterSplitRule in effect
   */
  DOUBLE_AFTER_SPLIT_RULE getDoubleAfterSplitRule();

  /**
   * Returns the rules for splitting pairs.
   *
   * @return the SplitRule in effect
   */
  RESPLIT_ACES_RULE getResplitAcesRule();

  /**
   * Returns the dealer's behavior on soft 17.
   *
   * @return the StandRule in effect
   */
  DEALER_ACTION_SOFT_SEVENTEEN getStandRule();

  /**
   * Returns the surrender rules available to the player.
   *
   * @return the SurrenderRule in effect
   */
  SURRENDER_RULE getSurrenderRule();

  /**
   * Whether the dealer takes a hole card and peeks for Blackjack.
   *
   * @return true if dealer has hole card, false if no hole card
   */
  HOLE_CARD_RULE getHoleCardRule();
}
