package model.cards;

import java.util.Map;

/**
 * Represents an immutable playing card defined by its rank.
 *
 * <p>This interface defines the operations supported by a Card object,
 * such as retrieving its rank and comparing it to another card.</p>
 */
public interface Card extends Comparable<Card> {

  /**
   * Enumeration of possible card ranks in ascending order.
   */
  enum RANK {
    TWO,
    THREE,
    FOUR,
    FIVE,
    SIX,
    SEVEN,
    EIGHT,
    NINE,
    TEN,
    JACK,
    QUEEN,
    KING,
    ACE
  }


  /**
   * Enumeration of possible card suits, unordered.
   */
  enum SUIT {
    HEARTS,
    DIAMONDS,
    SPADES,
    CLUBS
  }


  /**
   * Mapping of card ranks to their standard numerical values.
   * For example, ACE is valued at 14 and KING at 13.
   */
  public Map<RANK, Integer> rankToNum = Map.ofEntries(
      Map.entry(RANK.TWO, 2),
      Map.entry(RANK.THREE, 3),
      Map.entry(RANK.FOUR, 4),
      Map.entry(RANK.FIVE, 5),
      Map.entry(RANK.SIX, 6),
      Map.entry(RANK.SEVEN, 7),
      Map.entry(RANK.EIGHT, 8),
      Map.entry(RANK.NINE, 9),
      Map.entry(RANK.TEN, 10),
      Map.entry(RANK.JACK, 11),
      Map.entry(RANK.QUEEN, 12),
      Map.entry(RANK.KING, 13),
      Map.entry(RANK.ACE, 14)
  );

  /**
   * Mapping of card ranks to their numerical values in Blackjack.
   * Face cards are valued at 10, and ACE is valued at 11.
   */
  public Map<RANK, Integer> rankToBjNum = Map.ofEntries(
      Map.entry(RANK.TWO, 2),
      Map.entry(RANK.THREE, 3),
      Map.entry(RANK.FOUR, 4),
      Map.entry(RANK.FIVE, 5),
      Map.entry(RANK.SIX, 6),
      Map.entry(RANK.SEVEN, 7),
      Map.entry(RANK.EIGHT, 8),
      Map.entry(RANK.NINE, 9),
      Map.entry(RANK.TEN, 10),
      Map.entry(RANK.JACK, 10),
      Map.entry(RANK.QUEEN, 10),
      Map.entry(RANK.KING, 10),
      Map.entry(RANK.ACE, 11)
  );

  /**
   * Returns the rank of this card.
   *
   * @return the rank of the card
   */
  RANK rank();

  /**
   * Compares this card to another card based on their rank values.
   *
   * @param other the card to compare to
   * @return a negative integer, zero, or a positive integer as this card is less than,
   * equal to, or greater than the specified card
   */
  @Override
  int compareTo(Card other);

  /**
   * Checks whether this card is equal to another object.
   * Two cards are considered equal if they have the same rank.
   *
   * @param obj the object to compare with
   * @return true if the object is a Card with the same rank, false otherwise
   */
  @Override
  boolean equals(Object obj);

  /**
   * Returns the hash code value for this card.
   *
   * @return the hash code of the card
   */
  @Override
  int hashCode();

  /**
   * Returns a string representation of the card, including its rank.
   *
   * @return a string representation of the card
   */
  @Override
  String toString();
}
