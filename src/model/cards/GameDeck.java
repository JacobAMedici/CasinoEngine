package model.cards;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * A deck of cards that may or may not have a suit.
 */
public abstract class GameDeck<T extends Card> implements Deck {

  protected final List<T> cards;
  private final static int CARDS_IN_DECK = 52;

  /**
   * Constructor for GameDeck.
   *
   * @param cards the given cards.
   */
  GameDeck(List<T> cards) {
    this.cards = cards;
  }

  /**
   * Returns the list of cards in the deck.
   *
   * @return the linked list of cards
   */
  @Override
  public List<T> getDeck() {
    return new ArrayList<>(cards);
  }

  /**
   * Returns the number of cards left in the deck/shoe.
   *
   * @return the number of cards left in the deck/shoe
   */
  @Override
  public int getDeckSize() {
    return cards.size();
  }

  /**
   * Randomly shuffles the deck.
   */
  @Override
  public void shuffle() {
    Collections.shuffle(cards);
  }

  /**
   * Returns the current running count of the deck or shoe.
   * The running count is typically updated as cards are seen.
   *
   * @return the running count
   */
  @Override
  public int getRunningCount() {
    int runningCount = 0;
    for (Card card : cards) {
      switch (card.rank()) {
        case TWO, THREE, FOUR, FIVE, SIX -> runningCount++;
        case TEN, JACK, QUEEN, KING, ACE -> runningCount--;
        default -> runningCount += 0;
      }
    }
    return runningCount;
  }

  /**
   * Returns the current true count of the deck or shoe.
   * The true count is the running count divided by the estimated number of remaining decks.
   *
   * @return the true count as an integer (rounded if necessary)
   */
  @Override
  public int getTrueCount() {
    int runningCount = getRunningCount();
    int cardsLeft = getDeckSize();
    return (int) Math.floor((double) runningCount / cardsLeft * CARDS_IN_DECK);
  }

  public abstract Card dealCard();
}
