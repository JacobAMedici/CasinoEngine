package model.cards;

/**
 * Represents a playing card defined only by its rank.
 *
 * <p>This class models cards without suits, supporting
 * standard comparison by rank and mappings for games
 * such as Blackjack.</p>
 */
public record SuitlessCard(RANK rank) implements Card, Comparable<Card> {

  /**
   * Constructs a Card with the specified rank.
   *
   * @param rank the rank of the card
   */
  public SuitlessCard {

  }

  /**
   * Gets the rank of this card.
   *
   * @return the rank of this card.
   */
  @Override
  public RANK rank() {
    return this.rank;
  }

  /**
   * Compares this card to another card based on rank values.
   *
   * @param other the card to compare with
   * @return a negative integer, zero, or a positive integer as this card is less than,
   * equal to, or greater than the specified card
   */
  @Override
  public int compareTo(Card other) {
    return rankToNum.get(rank) - rankToNum.get(other.rank());
  }

  /**
   * Checks whether this card is equal to another object.
   * Two cards are considered equal if they have the same rank.
   *
   * @param other the object to compare with
   * @return true if the object is a Card with the same rank, false otherwise
   */
  @Override
  public boolean equals(Object other) {
    if (other instanceof SuitlessCard otherCard) {
      return this.rank == otherCard.rank();
    }
    return false;
  }
}
