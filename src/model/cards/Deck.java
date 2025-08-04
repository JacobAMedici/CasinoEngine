package model.cards;

import java.util.List;

/**
 * Represents a deck of playing cards.
 *
 * <p>This interface defines common operations for deck behavior,
 * such as retrieving the list of cards and shuffling the deck.</p>
 */
public interface Deck<T extends Card> {

  /**
   * Returns the list of cards in the deck.
   *
   * @return a list of cards
   */
  List<T> getDeck();

  /**
   * Returns the number of cards left in the deck/shoe.
   *
   * @return the number of cards left in the deck/shoe
   */
  int getDeckSize();

  /**
   * Shuffles the deck randomly.
   */
  void shuffle();

  /**
   * Gets the card at the top of the deck.
   *
   * @return the top card
   */
  Card dealCard();

  /**
   * Returns the current running count of the deck or shoe.
   * The running count is typically updated as cards are seen.
   *
   * @return the running count
   */
  int getRunningCount();

  /**
   * Returns the current true count of the deck or shoe.
   * The true count is the running count divided by the estimated number of remaining decks.
   *
   * @return the true count as an integer (rounded if necessary)
   */
  int getTrueCount();
}
