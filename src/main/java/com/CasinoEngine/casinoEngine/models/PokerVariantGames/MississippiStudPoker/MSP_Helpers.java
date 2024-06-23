package com.CasinoEngine.casinoEngine.models.PokerVariantGames.MississippiStudPoker;

import com.CasinoEngine.casinoEngine.models.AllHelpers.Deck;
import com.CasinoEngine.casinoEngine.models.AllHelpers.RequestFailedException;
import com.CasinoEngine.casinoEngine.models.PokerVariantGames.PokerHelpers.PokerHelpers;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.CasinoEngine.casinoEngine.models.AllHelpers.Deck.Card.rankToNum;

public class MSP_Helpers {

    public static void main(String[] ignored) throws RequestFailedException {
        MSP_Helpers msp_helpers = new MSP_Helpers();
//        System.out.println(msp_helpers.solveHand(new ArrayList<>(List.of(new Deck.Card(Deck.Card.RANK.ACE, Deck.Card.SUIT.HEARTS),
//                new Deck.Card(Deck.Card.RANK.TWO, Deck.Card.SUIT.CLUBS),
//                new Deck.Card(Deck.Card.RANK.FOUR, Deck.Card.SUIT.SPADES))), 1, 2));

        msp_helpers.houseEdge();

    }

    private final static int highPairPayout = 2;
    private final static int midPairPayout = 1;
    private final static int jacksIndex = 11;
    private final static int sixesIndex = 6;
    private final static int cardsInDeck = 52;

    private final List<Deck.Card> masterDeck = new Deck().getDeck();

    PokerHelpers pokerHelpers = new PokerHelpers();

//    private int completedStraightFlushPermutations = 0;
//    private int totalStraightFlushWager = 0;

    private final double JACKPOT = 25330;

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
        return payouts.get(hand.handStrength) * wager;
    }

    public int solveHand(List<Deck.Card> cards, int ante, int amountWagered) throws RequestFailedException {
        if (cards.size() == 2) {
            return getCachedMove(cards.get(0), cards.get(1));
        }
        if (cards.size() != 4 && cards.size() != 3) {
            return -1;
        }

        return evaluateCards(cards, ante, amountWagered);
    }

//    public int solveHand(List<Deck.Card> cards, int ante, int amountWagered) throws RequestFailedException {
//        if (cards.size() > 4 || cards.size() < 2) {
//            return -1;
//        }
//
//        return evaluateCards(cards, ante, amountWagered);
//    }

    private int evaluateCards(List<Deck.Card> cards, int ante, int amountWagered) throws RequestFailedException {

        double foldEV = -amountWagered;

        double totalEV1x = getEV(cards, ante, amountWagered + ante);

        final double delta = 0.000001;
        if (totalEV1x - delta < foldEV) {
            return 0;
        }

        double totalEV3x = getEV(cards, ante, amountWagered + ante * 3);

        if (totalEV1x >= totalEV3x) {
            return 1;
        } else {
            return 3;
        }
    }

    private double getEV(List<Deck.Card> cards, int ante, int amountWagered) throws RequestFailedException {
        if (cards.size() >= 4) {
            return calculateFinalEV(cards, amountWagered);
        }

        double totalEV = 0;
        double divByFactor = 1.0 / (cardsInDeck - cards.size());

        for (Deck.Card nextCard : masterDeck) {
            if (!cards.contains(nextCard)) {
                cards.add(nextCard);

                int nextStageMultiplier = evaluateCards(cards, ante, amountWagered);

                if (nextStageMultiplier == 0) {
                    totalEV += -amountWagered * divByFactor;
                } else {
                    double nextStageEV = getEV(cards, ante, amountWagered + ante * nextStageMultiplier);
                    totalEV += nextStageEV * divByFactor;
                }

                cards.remove(nextCard);
            }
        }

        return totalEV;
    }

    private double calculateFinalEV(List<Deck.Card> cards, int amountWagered) throws RequestFailedException {
        double totalEV = 0;
        double divByFactor = 1.0 / (cardsInDeck - cards.size());
        for (Deck.Card card : masterDeck) {
            if (!cards.contains(card)) {
                cards.add(card);
                PokerHelpers.Hand hand = pokerHelpers.findBestFiveCardHand(cards, List.of());
//                if (hand.handStrength == PokerHelpers.HAND_TYPE.STRAIGHT_FLUSH) {
//                    completedStraightFlushPermutations++;
//                    totalStraightFlushWager += amountWagered;
//                }
                double newEV = determinePayout(hand, amountWagered) - amountWagered;
//                if (hand.handStrength == PokerHelpers.HAND_TYPE.ROYAL_FLUSH) {
//                    newEV = JACKPOT;
//                } else if (hand.handStrength == PokerHelpers.HAND_TYPE.STRAIGHT_FLUSH) {
//                    newEV = (JACKPOT / 10);
//                } else {
//                    newEV = determinePayout(hand, amountWagered) - amountWagered;
//                }
                totalEV += newEV * divByFactor;
                cards.remove(card);
            }
        }
        return totalEV;
    }

    /*
    private void cacheMoves() throws RequestFailedException {
        HashMap<String, Integer> movesCache = new HashMap<>();
        for (Deck.Card firstCard : masterDeck) {
            for (Deck.Card secondCard : masterDeck) {
                if (!firstCard.equals(secondCard)) {
                    String key = firstCard.toString() + "," + secondCard.toString();
                    int move = solveHand(new ArrayList<>(List.of(firstCard, secondCard)), 1, 1);
                    movesCache.put(key, move);
                }
            }
        }

        // Serialize the HashMap to a file
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("movesCache.ser"))) {
            oos.writeObject(movesCache);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    */

    public int getCachedMove(Deck.Card firstCard, Deck.Card secondCard) {
        HashMap<String, Integer> movesCache;

        // Deserialize the HashMap from the file
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("movesCache.ser"))) {
            movesCache = (HashMap<String, Integer>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return -1; // Or handle error differently
        }

        // Query the cache and return the associated move
        String key = firstCard.toString() + "," + secondCard.toString();
        return movesCache.getOrDefault(key, -1); // Return -1 or some error code if no move is found
    }

    private void houseEdge() {
        double totalEV = 0;
        int count = 0;
        List<Deck.Card> usedFirstCards = new ArrayList<>();
        for (Deck.Card firstCard : masterDeck) {
            usedFirstCards.add(firstCard);
            for (Deck.Card secondCard : masterDeck) {
                if (!usedFirstCards.contains(secondCard)) {
                    try {
                        int handChoice = solveHand(new ArrayList<>(List.of(firstCard, secondCard)), 1, 1);
                        double newEV = getEV(new ArrayList<>(List.of(firstCard, secondCard)), 1, 1 + handChoice);
                        if (handChoice == 0) {
                            totalEV -= 1;
                            System.out.println("Hand Number: " + count++ + " Cards: " + firstCard + ", " + secondCard + " EV: " + -1);
                        } else {
                            totalEV += newEV;
                            System.out.println("Hand Number: " + count++ + " Cards: " + firstCard + ", " + secondCard + " EV: " + newEV);
                        }
                    } catch (RequestFailedException e) {
                        System.out.println(e.getMessage());
                    }
                }
            }
        }
        double defaultHouseEdge = -totalEV / ((double) (cardsInDeck * (cardsInDeck - 1)) / 2);

    }

// Calculated House Edge: 0.04914858250992733 or 4.914858250992733%


}