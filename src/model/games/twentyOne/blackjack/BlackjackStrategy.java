package model.games.twentyOne.blackjack;

/**
 * Defines a strategy for determining the best action a player should take in Blackjack,
 * based on the current game state and rules.
 */
public interface BlackjackStrategy {

  /**
   * Determines the best action for the player based on the initial two cards, the dealer's upcard,
   * the current Blackjack rules, and the state of the deck.
   *
   * @param rules        the Blackjack rules being used
   * @param dealerUpCard the dealer's visible card
   * @param playerCards  the player's cards
   * @return the recommended player action (e.g., HIT, STAND, DOUBLE_DOWN, SPLIT, SURRENDER)
   */
  BasicStrategy.ActionAndEV chooseAction(BlackjackDealerHand dealerHand,
                                         BlackjackPlayerHand playerHand,
                                         int handIndex);

}
