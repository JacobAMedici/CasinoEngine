package model.games.twentyOne.blackjack;

import model.cards.Card;

import java.util.ArrayList;
import java.util.List;

import static model.cards.SuitlessCard.rankToBjNum;

/**
 * Represents the dealer's hand in a game of Blackjack.
 * The dealer's hand is a collection of cards that the dealer draws according to the game rules.
 */
public class BlackjackDealerHand {
  List<Card> cards;

  /**
   * Constructs a BlackjackDealerHand with the specified list of cards.
   *
   * @param cards the list of cards that form the initial dealer's hand
   */
  public BlackjackDealerHand(List<Card> cards) {
    this.cards = cards;
  }

  /**
   * Constructs a BlackjackDealerHand as a copy of another BlackjackDealerHand.
   *
   * @param other the dealer hand to copy
   */
  BlackjackDealerHand(BlackjackDealerHand other) {
    this.cards = new ArrayList<>(other.getCards());
  }

  /**
   * Returns the list of cards currently in the dealer's hand.
   *
   * @return the list of cards
   */
  public List<Card> getCards() {
    return cards;
  }

  /**
   * Adds a new card to the dealer's hand.
   *
   * @param card the card to add
   */
  public void hit(Card card) {
    cards.add(card);
  }

  /**
   * Checks if the dealer's hand is a natural Blackjack (exactly two cards totaling 21).
   *
   * @return true if the dealer has Blackjack, false otherwise
   */
  public boolean isBlackjack() {
    return cards.size() == 2 && getHandSum() == 21;
  }

  /**
   * Computes the total value of the dealer's hand.
   * Aces are counted as either 11 or 1 to get the best possible hand value without busting.
   *
   * @return the sum of the hand's value
   */
  public int getHandSum() {
    return computeHandValue().sum;
  }

  /**
   * Determines if the dealer's hand is a soft hand.
   * A soft hand contains at least one Ace counted as 11 without busting.
   *
   * @return true if the dealer's hand is soft, false otherwise
   */
  public boolean isSoft() {
    return computeHandValue().isSoft;
  }

  /**
   * Helper record to hold the computed hand sum and softness status.
   */
  private record HandValue(int sum, boolean isSoft) {
  }

  /**
   * Computes the hand value and whether the hand is soft internally.
   *
   * @return a HandValue object containing the sum and softness status
   */
  private HandValue computeHandValue() {
    int sum = 0;
    int aceCount = 0;

    for (Card card : cards) {
      sum += rankToBjNum.get(card.rank());
      if (card.rank() == Card.RANK.ACE) {
        aceCount++;
      }
    }

    boolean isSoft = false;
    while (sum > 21 && aceCount > 0) {
      sum -= 10;
      aceCount--;
    }
    if (aceCount > 0) {
      isSoft = true;
    }

    return new HandValue(sum, isSoft);
  }
}

