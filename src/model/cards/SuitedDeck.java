package model.cards;

import java.util.ArrayList;
import java.util.List;

/**
 * A deck of cards (or n decks) where cards have a suit).
 */
public class SuitedDeck extends GameDeck {

  /**
   * Constructs a SuitedDeck containing one full set of cards (1 cards of each rank and suit).
   * Automatically shuffles the deck upon creation.
   */
  public SuitedDeck() {
    List<Card> cards = fillOneDeck();
    super(cards);
    shuffle();
  }

  /**
   * Constructs a SuitedDeck containing the specified number of decks.
   * Automatically shuffles the combined deck upon creation.
   *
   * @param numberOfDecks the number of standard decks to include
   */
  public SuitedDeck(int numberOfDecks) {
    List<Card> cards = fillNDecks(numberOfDecks);
    super(cards);
    shuffle();
  }

  /**
   * Constructs a SuitedDeck containing an old version of a deck.
   *
   * @param gameDeck an old version of a deck
   */
  public SuitedDeck(SuitedDeck gameDeck) {
    super(gameDeck.getDeck());
    shuffle();
  }

  /**
   * Gets the card at the top of the deck.
   *
   * @return the top card
   */
  @Override
  public SuitedCard dealCard() {
    if (super.getDeckSize() == 0) {
      throw new IllegalStateException("Cannot deal from empty deck");
    }
    SuitedCard topCard = (SuitedCard) super.cards.getFirst();
    super.cards.removeFirst();
    return topCard;
  }

  private static List<Card> fillOneDeck() {
    ArrayList<Card> cardDeck = new ArrayList<>();
    for (Card.SUIT suit : Card.SUIT.values()) {
      for (Card.RANK rank : Card.RANK.values()) {
        cardDeck.add(new SuitedCard(rank, suit));
      }
    }
    return cardDeck;
  }

  private static List<Card> fillNDecks(int numberOfDecks) {
    ArrayList<Card> cards = new ArrayList<>();
    for (int i = 0; i < numberOfDecks; i++) {
      cards.addAll(fillOneDeck());
    }
    return cards;
  }
}
