package model.cards;

import java.util.ArrayList;
import java.util.List;

/**
 * A deck of cards (or n decks) where cards have a suit).
 */
public class SuitedDeck extends GameDeck<SuitedCard> {

  /**
   * Constructs a SuitedDeck containing one full set of cards (1 cards of each rank and suit).
   * Automatically shuffles the deck upon creation.
   */
  public SuitedDeck() {
    List<SuitedCard> cards = fillOneDeck();
    super(cards);
    super.shuffle();
  }

  /**
   * Constructs a SuitedDeck containing the specified number of decks.
   * Automatically shuffles the combined deck upon creation.
   *
   * @param numberOfDecks the number of standard decks to include
   */
  public SuitedDeck(int numberOfDecks) {
    List<SuitedCard> cards = fillNDecks(numberOfDecks);
    super(cards);
    super.shuffle();
  }

  private static List<SuitedCard> fillOneDeck() {
    ArrayList<SuitedCard> cardDeck = new ArrayList<>();
    for (Card.SUIT suit : Card.SUIT.values()) {
      for (Card.RANK rank : Card.RANK.values()) {
        cardDeck.add(new SuitedCard(rank, suit));
      }
    }
    return cardDeck;
  }

  private static List<SuitedCard> fillNDecks(int numberOfDecks) {
    ArrayList<SuitedCard> cards = new ArrayList<>();
    for (int i = 0; i < numberOfDecks; i++) {
      cards.addAll(fillOneDeck());
    }
    return cards;
  }
}
