package model.games.twentyOne.blackjack.sideBets;

import model.cards.SuitedCard;
import model.cards.SuitedDeck;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents the MatchTheDealer side bet in Blackjack and Spanish.
 */
public class MatchTheDealer {

  private final static long SIMULATION_COUNT = 100_000L;
  private final static int CARDS_IN_DECK = 52;
  private final static int HIGHEST_POSSIBLE_TRUE_COUNT = 26;

  private final int numberOfDecks;
  private final int unsuitedPayout;
  private final int suitedPayout;
  private final int deckPen;
  private final List<Long> unitsPerTrueCount;
  private final List<Long> handsPerTrueCount;


  /**
   * Constructs a MatchTheDealer game.
   *
   * @param numberOfDecks  the number of decks in the shoe
   * @param unsuitedPayout the unsuitedPayout
   * @param suitedPayout   the suitedPayout
   * @param deckPen        the deckPenetration of the game in number of cards from the end
   */
  MatchTheDealer(int numberOfDecks, int unsuitedPayout, int suitedPayout, int deckPen) {
    if (numberOfDecks * CARDS_IN_DECK <= deckPen
        || unsuitedPayout < 0
        || suitedPayout < 0
        || deckPen < 3) {
      throw new IllegalArgumentException("Invalid arguments");
    }

    this.numberOfDecks = numberOfDecks;
    this.unsuitedPayout = unsuitedPayout;
    this.suitedPayout = suitedPayout;
    this.deckPen = deckPen;

    unitsPerTrueCount = new ArrayList<>(HIGHEST_POSSIBLE_TRUE_COUNT);
    handsPerTrueCount = new ArrayList<>(HIGHEST_POSSIBLE_TRUE_COUNT);

    for (int initializationIndex = 0; initializationIndex < HIGHEST_POSSIBLE_TRUE_COUNT; initializationIndex++) {
      handsPerTrueCount.add(0L);
      unitsPerTrueCount.add(0L);
    }
  }


  /**
   * Pretty prints information about the house edge per true count for the side bet.
   */
  public void printTheInfoHouseEdgePerTrueCount() {
    houseEdgePerTrueCount();
    long totalUnitsWon = unitsPerTrueCount.stream().mapToLong(Long::longValue).sum();
    long totalHandsPlayed = handsPerTrueCount.stream().mapToLong(Long::longValue).sum();

    List<Double> EVPerTrueCount = new ArrayList<>(HIGHEST_POSSIBLE_TRUE_COUNT);
    for (int trueCountIndex = 0; trueCountIndex < HIGHEST_POSSIBLE_TRUE_COUNT; trueCountIndex++) {
      if (handsPerTrueCount.get(trueCountIndex) >= 1) {
        EVPerTrueCount.add((double) unitsPerTrueCount.get(trueCountIndex) / handsPerTrueCount.get(trueCountIndex));
      } else {
        EVPerTrueCount.add(0.0);
      }
    }

    for (int index = 0; index < EVPerTrueCount.size(); index++) {
      double ratio = (double) handsPerTrueCount.get(index) / totalHandsPlayed;
      String formatted;
      if (EVPerTrueCount.get(index) < 0) {
        formatted = String.format("%4d TC has EV %10.6f with frequency %8.6f", index, EVPerTrueCount.get(index), ratio);
      } else {
        formatted = String.format("%4d TC has EV  %9.6f with frequency %8.6f", index, EVPerTrueCount.get(index), ratio);
      }
      System.out.println(formatted);
    }

    System.out.println();

//    double houseEdge = (double) totalUnitsWon / totalHandsPlayed;
//    System.out.printf("  The house edge is %10.6f%n", houseEdge);

    long positiveEV = 0;
    long positiveHandsPlayed = 0;
    for (int trueCountIndex = 0; trueCountIndex < HIGHEST_POSSIBLE_TRUE_COUNT; trueCountIndex++) {
      if (EVPerTrueCount.get(trueCountIndex) > 0) {
        positiveEV += unitsPerTrueCount.get(trueCountIndex);
        positiveHandsPlayed += handsPerTrueCount.get(trueCountIndex);
      }
    }

    System.out.printf("  Units won per hand (%d deck) %9.6f%n", numberOfDecks, (double) positiveEV / totalHandsPlayed);
    System.out.printf("  Units won per hand played (%d deck) %9.6f%n", numberOfDecks, (double) positiveEV / positiveHandsPlayed);
    System.out.printf("  Total hands played: %d%n", totalHandsPlayed);
    System.out.printf("  Positive hands played: %d%n", positiveHandsPlayed);
    System.out.printf("  Percentage of hands played: %6f%%%n", (double) positiveHandsPlayed / totalHandsPlayed * 100);
    System.out.println("\n  -----------------------------------------------\n");
  }

  private void houseEdgePerTrueCount() {
    for (int shoeCount = 0; shoeCount < SIMULATION_COUNT; shoeCount++) {
      SuitedDeck shoe = new SuitedDeck(numberOfDecks);
      shoe.shuffle();
      while (shoe.getDeckSize() > deckPen) {
        int trueCount = shoe.getTrueCount();
        if (trueCount >= HIGHEST_POSSIBLE_TRUE_COUNT) {
          trueCount = HIGHEST_POSSIBLE_TRUE_COUNT - 1;
        }
        if (trueCount < 0) {
          trueCount = 0;
        }
        unitsPerTrueCount.set(trueCount, unitsPerTrueCount.get(trueCount) + playHand(shoe));
        handsPerTrueCount.set(trueCount, handsPerTrueCount.get(trueCount) + 1);
      }
    }
  }

  /**
   * Generates the house edge for the side bet.
   *
   * @return the house edge of the size bet
   */
  public double houseEdge() {
    long unitsWon = 0;
    long numberOfHands = 0;

    for (int shoeCount = 0; shoeCount < SIMULATION_COUNT; shoeCount++) {
      SuitedDeck shoe = new SuitedDeck(numberOfDecks);
      shoe.shuffle();
      while (shoe.getDeckSize() > deckPen) {
        unitsWon += playHand(shoe);
        numberOfHands++;
      }
    }

    return (double) unitsWon / numberOfHands;
  }

  private long playHand(SuitedDeck shoe) {
    SuitedCard dealerCard = shoe.dealCard();
    SuitedCard playerCardFirst = shoe.dealCard();
    SuitedCard playerCardSecond = shoe.dealCard();

    boolean firstSuited = playerCardFirst.equals(dealerCard);
    boolean secondSuited = playerCardSecond.equals(dealerCard);

    boolean firstUnsuited = playerCardFirst.rank() == dealerCard.rank();
    boolean secondUnsuited = playerCardSecond.rank() == dealerCard.rank();

    if (firstSuited && secondSuited) {
      return suitedPayout * 2L;
    } else if (firstSuited && secondUnsuited || firstUnsuited && secondSuited) {
      return suitedPayout + unsuitedPayout;
    } else if (firstSuited || secondSuited) {
      return suitedPayout;
    } else if (firstUnsuited && secondUnsuited) {
      return unsuitedPayout * 2L;
    } else if (firstUnsuited || secondUnsuited) {
      return unsuitedPayout;
    } else {
      return -1L;
    }
  }
}