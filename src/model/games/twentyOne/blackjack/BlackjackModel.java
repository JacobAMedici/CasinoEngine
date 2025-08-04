package model.games.twentyOne.blackjack;

import model.cards.Deck;
import model.cards.SuitlessDeck;

/**
 * Represents the model for a Blackjack game, including the deck and rules.
 */
public class BlackjackModel {

  public Deck blackjackDeck;
  public Rules blackjackRules;

  /**
   * Constructs a BlackjackModel with the specified rules.
   *
   * @param blackjackRules the rules of the Blackjack game
   */
  public BlackjackModel(Rules blackjackRules) {
    this.blackjackRules = blackjackRules;
    blackjackDeck = new SuitlessDeck(blackjackRules.getNumberOfDecks());
  }
}
