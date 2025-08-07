package model.cards;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a deck of playing cards designed for Blackjack.
 *
 * <p>This deck uses cards without suits, with four copies of each rank per deck,
 * and supports shuffling and multi-deck games.</p>
 */
public class SuitlessDeck extends GameDeck<SuitlessCard> {

  /**
   * Constructs a BlackjackDeck containing one full set of cards (4 cards of each rank).
   * Automatically shuffles the deck upon creation.
   */
  public SuitlessDeck() {
    List<SuitlessCard> cards = fillOneDeck();
    super(cards);
    super.shuffle();
  }

  /**
   * Constructs a BlackjackDeck containing the specified number of decks.
   * Automatically shuffles the combined deck upon creation.
   *
   * @param numberOfDecks the number of standard decks to include
   */
  public SuitlessDeck(int numberOfDecks) {
    List<SuitlessCard> cards = fillNDecks(numberOfDecks);
    super(cards);
    super.shuffle();
  }

  private static List<SuitlessCard> fillOneDeck() {
    ArrayList<SuitlessCard> cardDeck = new ArrayList<>();
    int NUMBER_OF_SUITS = 4;
    for (int i = 0; i < NUMBER_OF_SUITS; i++) {
      for (Card.RANK rank : Card.RANK.values()) {
        cardDeck.add(new SuitlessCard(rank));
      }
    }
    return cardDeck;
  }

  private static List<SuitlessCard> fillNDecks(int numberOfDecks) {
    ArrayList<SuitlessCard> cards = new ArrayList<>();
    for (int i = 0; i < numberOfDecks; i++) {
      cards.addAll(fillOneDeck());
    }
    return cards;
  }
}
