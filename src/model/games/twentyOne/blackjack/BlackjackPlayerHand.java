package model.games.twentyOne.blackjack;

import model.cards.Card;

import java.util.ArrayList;
import java.util.List;

import static model.cards.SuitlessCard.rankToBjNum;

/**
 * Represents the player's hand(s) in a game of Blackjack.
 * A player can have multiple hands due to splits.
 */
public class BlackjackPlayerHand {
  List<List<Card>> cards = new ArrayList<>();
  private int splitCount = 0;
  private int doubleCount = 0;

  /**
   * Creates a deep copy of an existing BlackjackPlayerHand.
   *
   * @param other the BlackjackPlayerHand to copy
   */
  public BlackjackPlayerHand(BlackjackPlayerHand other) {
    for (List<Card> hand : other.cards) {
      this.cards.add(new ArrayList<>(hand));
    }
    this.splitCount = other.splitCount;
    this.doubleCount = other.doubleCount;
  }

  /**
   * Creates a new BlackjackPlayerHand.
   */
  public BlackjackPlayerHand(List<Card> cards) {
    this.cards.add(new ArrayList<>(cards));
  }

  /**
   * Returns the list of all hands the player currently has.
   *
   * @return a list of hands, where each hand is a list of cards
   */
  public List<List<Card>> getCards() {
    return cards;
  }

  /**
   * Adds a card to the specified hand.
   *
   * @param card      the card to add
   * @param handIndex the index of the hand to add the card to
   * @throws IndexOutOfBoundsException if the hand index is invalid
   */
  public void hit(Card card, int handIndex) {
    if (handIndex < 0 || handIndex >= cards.size()) {
      throw new IndexOutOfBoundsException("Invalid hand index.");
    }
    cards.get(handIndex).add(card);
  }

  /**
   * Splits the specified hand into two hands if splitting is allowed under the current rules.
   *
   * @param maxSplitCount the maximum number of splits allowed
   * @param rsa           the resplit aces rule
   * @param handIndex     the index of the hand to split
   * @throws IllegalArgumentException if the hand cannot be split
   */
  public void splitCards(int maxSplitCount, Rules.RESPLIT_ACES_RULE rsa, int handIndex) {
    if (!canSplit(maxSplitCount, rsa, handIndex)) {
      throw new IllegalArgumentException("Cannot split the hand.");
    }

    splitCount++;

    cards.get(handIndex).remove(1);
    cards.add(new ArrayList<>(cards.get(handIndex)));
  }

  /**
   * Returns the hand at the specified index.
   *
   * @param handIndex the index of the hand
   * @return the list of cards in the specified hand
   */
  public List<Card> getHand(int handIndex) {
    return cards.get(handIndex);
  }

  /**
   * Returns the total number of hands the player currently has.
   *
   * @return the number of hands
   */
  public int getHandSize() {
    return cards.size();
  }

  /**
   * Returns the number of times the player has split hands.
   *
   * @return the split count
   */
  public int getSplitCount() {
    return splitCount;
  }

  /**
   * Determines if the player can split the specified hand based on the rules.
   *
   * @param maxSplitCount the maximum number of splits allowed
   * @param rsa           the resplit aces rule
   * @param handIndex     the index of the hand to check
   * @return true if the hand can be split, false otherwise
   */
  public boolean canSplit(int maxSplitCount, Rules.RESPLIT_ACES_RULE rsa, int handIndex) {
    Card firstCard = cards.get(handIndex).get(0);
    Card secondCard = cards.get(handIndex).get(1);

    if (splitCount >= maxSplitCount
        || cards.get(handIndex).size() != 2
        || firstCard.rank() != secondCard.rank()) {
      return false;
    }

    if (firstCard.rank() == Card.RANK.ACE && secondCard.rank() == Card.RANK.ACE) {
      if (rsa == Rules.RESPLIT_ACES_RULE.NO_RESPLIT_ACES) {
        return false;
      } else if (rsa == Rules.RESPLIT_ACES_RULE.RESPLIT_ACES_ONCE) {
        return true;
      } else if (rsa == Rules.RESPLIT_ACES_RULE.RESPLIT_ACES_MAX_SPLIT_TIMES) {
        return splitCount < maxSplitCount;
      }
    }

    return true;
  }

  /**
   * Determines if the player can double down on the specified hand based on the rules.
   *
   * @param doubleRule the rules for when doubling is allowed
   * @param dasRule    the rules for doubling after a split
   * @param handIndex  the index of the hand to check
   * @return true if doubling is allowed, false otherwise
   */
  public boolean canDouble(Rules.DOUBLE_RULE doubleRule,
                           Rules.DOUBLE_AFTER_SPLIT_RULE dasRule,
                           int handIndex) {
    if (cards.get(handIndex).size() != 2) {
      return false;
    }

    if (dasRule == Rules.DOUBLE_AFTER_SPLIT_RULE.DOUBLE_AFTER_SPLIT_FORBIDDEN && splitCount > 0) {
      return false;
    }

    int handValue = getHandSum(handIndex);

    return switch (doubleRule) {
      case ANY_TWO_CARDS -> true;
      case NINE_TEN_ELEVEN -> handValue == 9 || handValue == 10 || handValue == 11;
      case TEN_ELEVEN -> handValue == 10 || handValue == 11;
      case ELEVEN_ONLY -> handValue == 11;
    };
  }

  /**
   * Doubles the specified hand if allowed under the rules.
   *
   * @param doubleRule the rules for when doubling is allowed
   * @param dasRule    the rules for doubling after a split
   * @param handIndex  the index of the hand to double
   * @throws IllegalArgumentException if doubling is not allowed
   */
  public void doubleHand(Rules.DOUBLE_RULE doubleRule,
                         Rules.DOUBLE_AFTER_SPLIT_RULE dasRule,
                         int handIndex) {
    if (!canDouble(doubleRule, dasRule, handIndex)) {
      throw new IllegalArgumentException("Cannot double the hand.");
    }
    doubleCount++;
  }

  /**
   * Returns the number of times the player has doubled down.
   *
   * @return the double count
   */
  public int getDoubleCount() {
    return doubleCount;
  }

  /**
   * Returns the total value of the specified hand.
   * Aces are counted as 11 or 1 to optimize the hand's value without busting.
   *
   * @param handIndex the index of the hand
   * @return the total hand value
   */
  public int getHandSum(int handIndex) {
    int sum = 0;
    int aceCount = 0;

    for (Card card : cards.get(handIndex)) {
      sum += rankToBjNum.get(card.rank());
      if (card.rank() == Card.RANK.ACE) {
        aceCount++;
      }
    }

    while (sum > 21 && aceCount > 0) {
      sum -= 10;
      aceCount--;
    }

    return sum;
  }

  /**
   * Checks if the specified hand is a natural Blackjack (two cards totaling 21).
   *
   * @param handIndex the index of the hand
   * @return true if the hand is a Blackjack, false otherwise
   */
  public boolean isBlackjack(int handIndex) {
    return cards.get(handIndex).size() == 2 && getHandSum(handIndex) == 21;
  }
}

