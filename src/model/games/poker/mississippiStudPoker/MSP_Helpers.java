package model.games.poker.mississippiStudPoker;

import model.cards.Card;
import model.cards.SuitedCard;
import model.cards.SuitedDeck;
import model.games.poker.PokerHelpers.PokerHelpers;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static model.cards.Card.rankToNum;

public class MSP_Helpers {

  private final static String cachedMovesFileName = "src/model/games/poker/mississippiStudPoker/mspCachedMoves.txt";

  private final static int highPairPayout = 2;
  private final static int midPairPayout = 1;
  private final static int jacksIndex = 11;
  private final static int sixesIndex = 6;
  private final static int cardsInDeck = 52;

  private final List<SuitedCard> masterDeck;
  private final Map<String, Integer> cachedMoves = new HashMap<>();
  PokerHelpers pokerHelpers = new PokerHelpers();

  public static Map<PokerHelpers.HAND_TYPE, Integer> payouts = Map.ofEntries(
      // Notice there is one additional payout per bonus which is used to return the wager
      Map.entry(PokerHelpers.HAND_TYPE.ROYAL_FLUSH, 501),
      Map.entry(PokerHelpers.HAND_TYPE.STRAIGHT_FLUSH, 101),
      Map.entry(PokerHelpers.HAND_TYPE.FOUR_OF_A_KIND, 41),
      Map.entry(PokerHelpers.HAND_TYPE.FULL_HOUSE, 11),
      Map.entry(PokerHelpers.HAND_TYPE.FLUSH, 7),
      Map.entry(PokerHelpers.HAND_TYPE.STRAIGHT, 5),
      Map.entry(PokerHelpers.HAND_TYPE.THREE_OF_A_KIND, 4),
      Map.entry(PokerHelpers.HAND_TYPE.TWO_PAIR, 3),
      Map.entry(PokerHelpers.HAND_TYPE.HIGH_CARD, 0)
  );

  MSP_Helpers() {
    masterDeck = new ArrayList<>();
    List<Card> suitedDeck = new SuitedDeck().getDeck();
    for (Card card : suitedDeck) {
      masterDeck.add((SuitedCard) card);
    }

    try (BufferedReader br = new BufferedReader(new FileReader(cachedMovesFileName))) {
      String line;
      while ((line = br.readLine()) != null) {
        String[] parts = line.split("=");
        if (parts.length == 2) {
          cachedMoves.put(parts[0], Integer.parseInt(parts[1]));
        }
      }
    } catch (IOException e) {
      System.err.println("Failed to load cached moves: " + e.getMessage());
    }
  }

  public static void main(String[] ignored) {
    MSP_Helpers msp_helpers = new MSP_Helpers();
    msp_helpers.houseEdge();
  }

  public int determinePayout(PokerHelpers.Hand hand, int wager) {
    if (hand.handStrength == PokerHelpers.HAND_TYPE.PAIR) {
      if (rankToNum.get(hand.highestCardInCombo.getFirst()) >= jacksIndex) {
        return wager * highPairPayout;
      } else if (rankToNum.get(hand.highestCardInCombo.getFirst()) >= sixesIndex) {
        return wager * midPairPayout;
      } else {
        return 0;
      }
    }
    int payout = payouts.get(hand.handStrength) * wager;
//    if (payout >= 100) {
//      return 100;
//    }
    return payout;
  }

  public int solveHand(List<SuitedCard> cards, int ante, int amountWagered) {
    if (cards.size() == 2) {
      return getCachedMove(cards.get(0), cards.get(1));
    }
    if (cards.size() != 4 && cards.size() != 3) {
      return -1;
    }

    return evaluateCards(cards, ante, amountWagered);
  }

  /*
  public int solveHand(List<SuitedCard> cards, int ante, int amountWagered) {
    if (cards.size() > 4 || cards.size() < 2) {
      return -1;
    }

    return evaluateCards(cards, ante, amountWagered);
  }
  */

  private int evaluateCards(List<SuitedCard> cards, int ante, int amountWagered) {

    double foldEV = -amountWagered;

    double totalEV1x = calculateEV(cards, ante, amountWagered + ante);

    final double delta = 0.000001;
    if (totalEV1x - delta < foldEV) {
      return 0;
    }

    double totalEV3x = calculateEV(cards, ante, amountWagered + ante * 3);

    if (totalEV1x >= totalEV3x) {
      return 1;
    } else {
      return 3;
    }
  }

  public double calculateEV(List<SuitedCard> cards, int ante, int amountWagered) {
    if (cards.size() >= 4) {
      return calculateFinalEV(cards, amountWagered);
    }

    double totalEV = 0;
    double divByFactor = 1.0 / (cardsInDeck - cards.size());

    for (SuitedCard nextCard : masterDeck) {
      if (!cards.contains(nextCard)) {
        cards.add(nextCard);

        int nextStageMultiplier = evaluateCards(cards, ante, amountWagered);

        if (nextStageMultiplier == 0) {
          totalEV += -amountWagered * divByFactor;
        } else {
          double nextStageEV = calculateEV(cards, ante, amountWagered + ante * nextStageMultiplier);
          totalEV += nextStageEV * divByFactor;
        }

        cards.remove(nextCard);
      }
    }

    return totalEV;
  }

  private double calculateFinalEV(List<SuitedCard> cards, int amountWagered) {
    double totalEV = 0;
    double divByFactor = 1.0 / (cardsInDeck - cards.size());
    for (SuitedCard card : masterDeck) {
      if (!cards.contains(card)) {
        cards.add(card);
        PokerHelpers.Hand hand = pokerHelpers.findBestFiveCardHand(cards, List.of());
        double newEV = determinePayout(hand, amountWagered) - amountWagered;
        totalEV += newEV * divByFactor;
        cards.remove(card);
      }
    }
    return totalEV;
  }

  /*
  private void cacheMoves() {
    try (BufferedWriter writer = new BufferedWriter(new FileWriter(cachedMovesFileName))) {

      for (SuitedCard firstCard : masterDeck) {
        for (SuitedCard secondCard : masterDeck) {
          if (!firstCard.equals(secondCard)) {
            String key = firstCard + "," + secondCard;
            System.out.println("Processing: " + key);
            int move = solveHand(new ArrayList<>(List.of(firstCard, secondCard)), 1, 1);
            writer.write(key + "=" + move);
            writer.newLine();
            writer.flush();
          }
        }
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
  */


  public int getCachedMove(SuitedCard firstCard, SuitedCard secondCard) {
    String key = firstCard.toString() + "," + secondCard.toString();
    return cachedMoves.getOrDefault(key, -1);
  }


  private void houseEdge() {
    double totalEV = 0;
    int count = 0;
    List<SuitedCard> usedFirstCards = new ArrayList<>();
    for (SuitedCard firstCard : masterDeck) {
      usedFirstCards.add(firstCard);
      for (SuitedCard secondCard : masterDeck) {
        if (!usedFirstCards.contains(secondCard)) {
          int handChoice = solveHand(new ArrayList<>(List.of(firstCard, secondCard)), 1, 1);
          double newEV = calculateEV(new ArrayList<>(List.of(firstCard, secondCard)), 1, 1 + handChoice);
          if (handChoice == 0) {
            totalEV -= 1;
            // System.out.println("Hand Number: " + count++ + " Cards: " + firstCard + ", " + secondCard + " EV: " + -1);
          } else {
            totalEV += newEV;
            // System.out.println("Hand Number: " + count++ + " Cards: " + firstCard + ", " + secondCard + " EV: " + newEV);
          }
        }
      }
    }
    double defaultHouseEdge = -totalEV / ((double) (cardsInDeck * (cardsInDeck - 1)) / 2);
    System.out.println(defaultHouseEdge);
  }

  // Calculated House Edge: 0.04914858250992733 or 4.914858250992733%

  // No Strategy Adjustment
  // 100 Payout Cap: 0.1219340043709795
  // Flush Pays 7:1, Straight Pays 5:1: 0.10239295718287317
  // Flush Pays 7:1, Straight Pays 6:1: 0.08997968418136491
  // Flush Pays 8:1, Straight Pays 5:1: 0.09414073321636339
  // Flush Pays 8:1, Straight Pays 6:1: 0.0817016037184106
  // Flush Pays 9:1, Straight Pays 7:1: 0.05978883861236805
  // Flush Pays 10:1, Straight Pays 8:1: 0.03682180564533502

  // Strategy Adjustment
  // A pair of 5s push House Edge: 0.028346415489272872 or 2.8346415489272872%
}