package model.games.twentyOne.blackjack.sideBets;

import model.cards.SuitedCard;
import model.cards.SuitedDeck;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents the MatchTheDealer side bet in Blackjack and Spanish.
 */
public class MatchTheDealer {

  private final static long SIMULATION_COUNT = 100_000_000L;
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
        int absTrueCount = Math.abs(shoe.getTrueCount());
        if (absTrueCount >= HIGHEST_POSSIBLE_TRUE_COUNT) {
          absTrueCount = HIGHEST_POSSIBLE_TRUE_COUNT - 1;
        }
        unitsPerTrueCount.set(absTrueCount, unitsPerTrueCount.get(absTrueCount) + playHand(shoe));
        handsPerTrueCount.set(absTrueCount, handsPerTrueCount.get(absTrueCount) + 1);
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

/*
Cut off percentage: 40
   0 TC has EV  -0.035921 with frequency 0.254291
   1 TC has EV  -0.035755 with frequency 0.214130
   2 TC has EV  -0.035218 with frequency 0.191781
   3 TC has EV  -0.034598 with frequency 0.121577
   4 TC has EV  -0.032075 with frequency 0.082859
   5 TC has EV  -0.028586 with frequency 0.051271
   6 TC has EV  -0.023717 with frequency 0.033710
   7 TC has EV  -0.017959 with frequency 0.020801
   8 TC has EV  -0.012250 with frequency 0.013345
   9 TC has EV  -0.004749 with frequency 0.007122
  10 TC has EV   0.003655 with frequency 0.003870
  11 TC has EV   0.011617 with frequency 0.002160
  12 TC has EV   0.025318 with frequency 0.001506
  13 TC has EV   0.032744 with frequency 0.000848
  14 TC has EV   0.043874 with frequency 0.000389
  15 TC has EV   0.060874 with frequency 0.000191
  16 TC has EV   0.066989 with frequency 0.000087
  17 TC has EV   0.127553 with frequency 0.000038
  18 TC has EV   0.103695 with frequency 0.000016
  19 TC has EV   0.145156 with frequency 0.000006
  20 TC has EV   0.145875 with frequency 0.000002
  21 TC has EV   0.069187 with frequency 0.000001
  22 TC has EV   0.385626 with frequency 0.000001
  23 TC has EV  -0.006912 with frequency 0.000000
  24 TC has EV  -0.040323 with frequency 0.000000
  25 TC has EV  -0.039216 with frequency 0.000000

  Units won per hand (2 deck)  0.000148
  Units won per hand played (2 deck)  0.016201
  Total hands played: 2000000000
  Positive hands played: 18226328
  Percentage of hands played: 0.911316%

  -----------------------------------------------

Cut off percentage: 35
   0 TC has EV  -0.035894 with frequency 0.246434
   1 TC has EV  -0.036324 with frequency 0.208088
   2 TC has EV  -0.035724 with frequency 0.190655
   3 TC has EV  -0.034991 with frequency 0.123055
   4 TC has EV  -0.032636 with frequency 0.085184
   5 TC has EV  -0.028207 with frequency 0.053966
   6 TC has EV  -0.024336 with frequency 0.034380
   7 TC has EV  -0.018399 with frequency 0.021539
   8 TC has EV  -0.013492 with frequency 0.015679
   9 TC has EV  -0.004842 with frequency 0.008877
  10 TC has EV   0.003556 with frequency 0.005087
  11 TC has EV   0.012912 with frequency 0.002947
  12 TC has EV   0.024244 with frequency 0.001774
  13 TC has EV   0.034396 with frequency 0.001204
  14 TC has EV   0.050589 with frequency 0.000479
  15 TC has EV   0.055655 with frequency 0.000348
  16 TC has EV   0.067411 with frequency 0.000168
  17 TC has EV   0.090839 with frequency 0.000078
  18 TC has EV   0.113793 with frequency 0.000035
  19 TC has EV   0.108766 with frequency 0.000012
  20 TC has EV   0.132644 with frequency 0.000004
  21 TC has EV   0.182368 with frequency 0.000004
  22 TC has EV   0.232453 with frequency 0.000002
  23 TC has EV   0.239162 with frequency 0.000001
  24 TC has EV   0.045346 with frequency 0.000000
  25 TC has EV   0.419048 with frequency 0.000000

  Units won per hand (2 deck)  0.000210
  Units won per hand played (2 deck)  0.017276
  Total hands played: 2100000000
  Positive hands played: 25498967
  Percentage of hands played: 1.214237%

  -----------------------------------------------

Cut off percentage: 30
   0 TC has EV  -0.036529 with frequency 0.232959
   1 TC has EV  -0.036505 with frequency 0.197728
   2 TC has EV  -0.037131 with frequency 0.188970
   3 TC has EV  -0.035598 with frequency 0.122609
   4 TC has EV  -0.033064 with frequency 0.084047
   5 TC has EV  -0.030442 with frequency 0.057645
   6 TC has EV  -0.026739 with frequency 0.040753
   7 TC has EV  -0.020387 with frequency 0.025369
   8 TC has EV  -0.014719 with frequency 0.018529
   9 TC has EV  -0.007398 with frequency 0.011850
  10 TC has EV   0.003122 with frequency 0.007220
  11 TC has EV   0.010894 with frequency 0.004667
  12 TC has EV   0.021709 with frequency 0.002864
  13 TC has EV   0.032378 with frequency 0.002306
  14 TC has EV   0.045592 with frequency 0.000887
  15 TC has EV   0.062105 with frequency 0.000568
  16 TC has EV   0.065699 with frequency 0.000536
  17 TC has EV   0.084769 with frequency 0.000268
  18 TC has EV   0.098274 with frequency 0.000097
  19 TC has EV   0.111675 with frequency 0.000042
  20 TC has EV   0.123823 with frequency 0.000048
  21 TC has EV   0.152523 with frequency 0.000023
  22 TC has EV   0.174019 with frequency 0.000008
  23 TC has EV   0.188643 with frequency 0.000003
  24 TC has EV   0.259170 with frequency 0.000003
  25 TC has EV   0.202649 with frequency 0.000002

  Units won per hand (2 deck)  0.000370
  Units won per hand played (2 deck)  0.018956
  Total hands played: 2300000000
  Positive hands played: 44946707
  Percentage of hands played: 1.954205%

  -----------------------------------------------

Cut off percentage: 25
   0 TC has EV  -0.037181 with frequency 0.221892
   1 TC has EV  -0.037010 with frequency 0.189270
   2 TC has EV  -0.037779 with frequency 0.184554
   3 TC has EV  -0.036159 with frequency 0.119566
   4 TC has EV  -0.034323 with frequency 0.086625
   5 TC has EV  -0.031684 with frequency 0.061348
   6 TC has EV  -0.027757 with frequency 0.042342
   7 TC has EV  -0.021589 with frequency 0.027647
   8 TC has EV  -0.015759 with frequency 0.022201
   9 TC has EV  -0.010804 with frequency 0.015531
  10 TC has EV   0.000143 with frequency 0.008982
  11 TC has EV   0.010150 with frequency 0.006847
  12 TC has EV   0.018330 with frequency 0.004215
  13 TC has EV   0.029615 with frequency 0.003730
  14 TC has EV   0.044965 with frequency 0.001817
  15 TC has EV   0.053422 with frequency 0.001124
  16 TC has EV   0.073846 with frequency 0.000833
  17 TC has EV   0.086984 with frequency 0.000768
  18 TC has EV   0.100593 with frequency 0.000269
  19 TC has EV   0.140682 with frequency 0.000128
  20 TC has EV   0.133260 with frequency 0.000149
  21 TC has EV   0.162591 with frequency 0.000063
  22 TC has EV   0.187097 with frequency 0.000052
  23 TC has EV   0.202470 with frequency 0.000023
  24 TC has EV   0.207491 with frequency 0.000009
  25 TC has EV   0.262330 with frequency 0.000015

  Units won per hand (2 deck)  0.000624
  Units won per hand played (2 deck)  0.021498
  Total hands played: 2500000000
  Positive hands played: 72561687
  Percentage of hands played: 2.902467%
 */