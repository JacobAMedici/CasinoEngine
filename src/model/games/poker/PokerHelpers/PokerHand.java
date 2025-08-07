package model.games.poker.PokerHelpers;

import model.cards.SuitedCard;

import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * Represents a poker hand with its type, highest cards in the combination, and other high cards.
 * Implements Comparable to allow for hand comparison based on poker rules.
 */
public class PokerHand implements Comparable<PokerHand> {

  public static final int NUMBER_OF_RANKS = 13;
  public static final int NUM_NEEDED_FOR_THREE_OF_A_KIND = 3;
  public static final int NUM_NEEDED_FOR_PAIR = 2;
  public static final int NUM_NEEDED_FOR_FOUR_OF_A_KIND = 4;
  public static final int NUM_CARDS_IN_POKER_HAND = 5;

  public enum HAND_TYPE {
    ROYAL_FLUSH,
    STRAIGHT_FLUSH,
    FOUR_OF_A_KIND,
    FULL_HOUSE,
    FLUSH,
    STRAIGHT,
    THREE_OF_A_KIND,
    TWO_PAIR,
    PAIR,
    HIGH_CARD
  }

  public static final int NUMBER_OF_SUITS = 4;
  public static final int INDEX_OF_LOWEST_POSSIBLE_STRAIGHT_HIGH_CARD = 4;
  public static final int NUM_SUITED_FOR_FLUSH = 5;

  public static Map<HAND_TYPE, Integer> handStrengthToInt = Map.ofEntries(
      Map.entry(HAND_TYPE.ROYAL_FLUSH, 9),
      Map.entry(HAND_TYPE.STRAIGHT_FLUSH, 8),
      Map.entry(HAND_TYPE.FOUR_OF_A_KIND, 7),
      Map.entry(HAND_TYPE.FULL_HOUSE, 6),
      Map.entry(HAND_TYPE.FLUSH, 5),
      Map.entry(HAND_TYPE.STRAIGHT, 4),
      Map.entry(HAND_TYPE.THREE_OF_A_KIND, 3),
      Map.entry(HAND_TYPE.TWO_PAIR, 2),
      Map.entry(HAND_TYPE.PAIR, 1),
      Map.entry(HAND_TYPE.HIGH_CARD, 0)
  );

  public static Map<Integer, SuitedCard.RANK> numToRank = Map.ofEntries(
      Map.entry(2, SuitedCard.RANK.TWO),
      Map.entry(3, SuitedCard.RANK.THREE),
      Map.entry(4, SuitedCard.RANK.FOUR),
      Map.entry(5, SuitedCard.RANK.FIVE),
      Map.entry(6, SuitedCard.RANK.SIX),
      Map.entry(7, SuitedCard.RANK.SEVEN),
      Map.entry(8, SuitedCard.RANK.EIGHT),
      Map.entry(9, SuitedCard.RANK.NINE),
      Map.entry(10, SuitedCard.RANK.TEN),
      Map.entry(11, SuitedCard.RANK.JACK),
      Map.entry(12, SuitedCard.RANK.QUEEN),
      Map.entry(13, SuitedCard.RANK.KING),
      Map.entry(14, SuitedCard.RANK.ACE)
  );

  public static Map<SuitedCard.SUIT, Integer> suitToInt = Map.ofEntries(
      Map.entry(SuitedCard.SUIT.SPADES, 0),
      Map.entry(SuitedCard.SUIT.HEARTS, 1),
      Map.entry(SuitedCard.SUIT.DIAMONDS, 2),
      Map.entry(SuitedCard.SUIT.CLUBS, 3)
  );

  public final HAND_TYPE handStrength;
  public final List<SuitedCard.RANK> highestCardInCombo;
  public final List<SuitedCard.RANK> otherHighCards;

  /**
   * Constructor for Hand.
   *
   * @param handStrength The type of poker hand (e.g., flush, straight).
   * @param highestCard The highest card(s) in the hand combination.
   * @param highestCards Other high cards in the hand for tie-breaking.
   */
  public PokerHand(HAND_TYPE handStrength, List<SuitedCard.RANK> highestCard, List<SuitedCard.RANK> highestCards) {
    this.handStrength = handStrength;
    this.highestCardInCombo = highestCard;
    this.otherHighCards = highestCards;
  }

  /**
   * Compares this hand with another hand based on poker rules.
   *
   * @param otherHand The other hand to compare against.
   * @return A positive integer if this hand is stronger, negative if weaker, or zero if equal.
   */
  @Override
  public int compareTo(PokerHand otherHand) {
    highestCardInCombo.sort(Collections.reverseOrder());
    otherHighCards.sort(Collections.reverseOrder());
    if (handStrength == otherHand.handStrength) {
      for (int highestCardInHandIndex = 0;
           highestCardInHandIndex < highestCardInCombo.size();
           highestCardInHandIndex++) {
        if (!this.highestCardInCombo.get(highestCardInHandIndex).equals(otherHand.highestCardInCombo.get(highestCardInHandIndex))) {
          return SuitedCard.rankToNum.get(highestCardInCombo.get(highestCardInHandIndex)) -
              SuitedCard.rankToNum.get(otherHand.highestCardInCombo.get(highestCardInHandIndex));
        }
      }
      for (int cardIndex = 0; cardIndex < otherHighCards.size(); cardIndex++) {
        if (otherHighCards.get(cardIndex).compareTo(otherHand.otherHighCards.get(cardIndex)) != 0) {
          return SuitedCard.rankToNum.get(otherHighCards.get(cardIndex)) -
              SuitedCard.rankToNum.get(otherHand.otherHighCards.get(cardIndex));
        }
      }
      return 0;
    }
    return handStrengthToInt.get(handStrength) - handStrengthToInt.get(otherHand.handStrength);
  }

  /**
   * Checks if this hand is equal to another object.
   *
   * @param other The object to compare against.
   * @return True if the other object is a Hand and is equal to this hand, false otherwise.
   */
  @Override
  public boolean equals(Object other) {
    if (other instanceof PokerHand otherHand) {
      return this.compareTo(otherHand) == 0;
    }
    return false;
  }

  @Override
  public String toString() {
    String input = (handStrength + ", " + highestCardInCombo + " high").replace("_", " ").replace("[", "").replace("]", "").toLowerCase();
    return input.isEmpty() ? input : Character.toUpperCase(input.charAt(0)) + input.substring(1);
  }
}