package model.cards;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * A deck of cards that may or may not have a suit.
 */
public abstract class GameDeck<T extends Card> implements Deck<T> {

  protected final List<T> cards;
  private final static int CARDS_IN_DECK = 52;
  private int runningCount = 0;

  /**
   * Constructor for GameDeck.
   *
   * @param cards the given cards.
   */
  public GameDeck(List<T> cards) {
    this.cards = cards;
    runningCount = getNewRunningCount();
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
    return runningCount;
  }

  private int getNewRunningCount() {
    int newRunningCount = 0;
    for (Card card : cards) {
      switch (card.rank()) {
        case TWO, THREE, FOUR, FIVE, SIX -> newRunningCount++;
        case TEN, JACK, QUEEN, KING, ACE -> newRunningCount--;
        default -> newRunningCount += 0;
      }
    }
    return newRunningCount;
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

  /**
   * Gets the card at the top of the deck.
   *
   * @return the top card
   */
  @Override
  public T dealCard() {
    if (cards.isEmpty()) {
      throw new IllegalStateException("Cannot deal from empty deck");
    }
    T topCard = cards.getFirst();
    adjustRunningCountOnRemovedCard(topCard);
    cards.removeFirst();
    return topCard;
  }

  /**
   * Removes the specified cards from the deck.
   *
   * @param cardsToRemove the list of cards to remove
   */
  @Override
  public void removeCardsFromDeck(List<T> cardsToRemove) {
    for (T card : cardsToRemove) {
      adjustRunningCountOnRemovedCard(card);
      cards.remove(card);
    }
  }

  private void adjustRunningCountOnRemovedCard(T topCard) {
    switch (topCard.rank()) {
      case TWO, THREE, FOUR, FIVE, SIX -> runningCount++;
      case TEN, JACK, QUEEN, KING, ACE -> runningCount--;
      default -> runningCount += 0;
    }
  }

}
