package com.CasinoEngine.casinoEngine.models.PokerVariantGames.PokerHelpers;

import com.CasinoEngine.casinoEngine.models.AllHelpers.Deck;
import com.CasinoEngine.casinoEngine.models.AllHelpers.RequestFailedException;

import java.util.*;
import java.util.stream.Stream;

public class PokerHelpers {

    public PokerHelpers() {

    }

    public static class Hand implements Comparable<Hand> {
        public final HAND_TYPE handStrength;
        public final List<Deck.Card.RANK> highestCardInCombo;
        public final List<Deck.Card.RANK> otherHighCards;

        public Hand(HAND_TYPE handStrength, List<Deck.Card.RANK> highestCard, List<Deck.Card.RANK> highestCards) {
            this.handStrength = handStrength;
            this.highestCardInCombo = highestCard;
            this.otherHighCards = highestCards;
        }

        @Override
        public int compareTo(Hand otherHand) {
            highestCardInCombo.sort(Collections.reverseOrder());
            otherHighCards.sort(Collections.reverseOrder());
            if (handStrength == otherHand.handStrength) {
                for (int highestCardInHandIndex = 0;
                     highestCardInHandIndex < highestCardInCombo.size();
                     highestCardInHandIndex++) {
                    if (!this.highestCardInCombo.get(highestCardInHandIndex).equals(otherHand.highestCardInCombo.get(highestCardInHandIndex))) {
                        return Deck.Card.rankToNum.get(highestCardInCombo.get(highestCardInHandIndex)) -
                                Deck.Card.rankToNum.get(otherHand.highestCardInCombo.get(highestCardInHandIndex));
                    }
                }
                for (int cardIndex = 0; cardIndex < otherHighCards.size(); cardIndex++) {
                    if (otherHighCards.get(cardIndex).compareTo(otherHand.otherHighCards.get(cardIndex)) != 0) {
                        return Deck.Card.rankToNum.get(otherHighCards.get(cardIndex)) -
                                Deck.Card.rankToNum.get(otherHand.otherHighCards.get(cardIndex));
                    }
                }
                return 0;
            }
            return handStrengthToInt.get(handStrength) - handStrengthToInt.get(otherHand.handStrength);
        }

        @Override
        public boolean equals(Object other) {
            if (other instanceof Hand otherHand) {
                return this.compareTo(otherHand) == 0;
            }
            return false;
        }

        @Override
        public String toString() {
            return formatString(String.format("%s, %s high", handStrength.toString(), highestCardInCombo).toLowerCase());
        }

        public static String formatString(String inputString) {
            String modifiedString = inputString.replace("_", " ").replace("[", "").replace("]", "").toLowerCase();
            if (!modifiedString.isEmpty()) {
                modifiedString = modifiedString.substring(0, 1).toUpperCase() + modifiedString.substring(1);
            }
            return modifiedString;
        }

    }

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

    protected static Map<Integer, Deck.Card.RANK> numToRank = Map.ofEntries(
            Map.entry(2, Deck.Card.RANK.TWO),
            Map.entry(3, Deck.Card.RANK.THREE),
            Map.entry(4, Deck.Card.RANK.FOUR),
            Map.entry(5, Deck.Card.RANK.FIVE),
            Map.entry(6, Deck.Card.RANK.SIX),
            Map.entry(7, Deck.Card.RANK.SEVEN),
            Map.entry(8, Deck.Card.RANK.EIGHT),
            Map.entry(9, Deck.Card.RANK.NINE),
            Map.entry(10, Deck.Card.RANK.TEN),
            Map.entry(11, Deck.Card.RANK.JACK),
            Map.entry(12, Deck.Card.RANK.QUEEN),
            Map.entry(13, Deck.Card.RANK.KING),
            Map.entry(14, Deck.Card.RANK.ACE)
    );

    public static Map<Deck.Card.SUIT, Integer> suitToInt = Map.ofEntries(
            Map.entry(Deck.Card.SUIT.SPADES, 0),
            Map.entry(Deck.Card.SUIT.HEARTS, 1),
            Map.entry(Deck.Card.SUIT.DIAMONDS, 2),
            Map.entry(Deck.Card.SUIT.CLUBS, 3)
    );

    public Hand findBestFiveCardHand(List<Deck.Card> communityCards,
                                     List<Deck.Card> holeCards) throws RequestFailedException {

        List<Deck.Card> cardsList =
                new ArrayList<>(Stream.concat(communityCards.stream(), holeCards.stream()).toList());
        Collections.sort(cardsList);

        if (cardsList.size() < NUM_CARDS_IN_POKER_HAND) {
            throw new RequestFailedException(String.format("Cannot make best five card hand with only %d cards",
                    cardsList.size()));
        }

        Boolean[][] cardsTable = new Boolean[NUMBER_OF_SUITS][NUMBER_OF_RANKS];
        for (int suit = 0; suit < NUMBER_OF_SUITS; suit++) {
            for (int rank = 0; rank < NUMBER_OF_RANKS; rank++) {
                cardsTable[suit][rank] = false;
            }
        }

        for (Deck.Card card : cardsList) {
            cardsTable[suitToInt.get(card.suit)][Deck.Card.rankToNum.get(card.rank) - 2] = true;
        }

        Map<Deck.Card.RANK, Integer> likeRankCount = new HashMap<>();
        for (Deck.Card.RANK rank : Deck.Card.RANK.values()) {
            likeRankCount.put(rank, 0);
        }

        for (Deck.Card card : cardsList) {
            likeRankCount.replace(card.rank, likeRankCount.get(card.rank) + 1);
        }

        List<Deck.Card.RANK> rankList = new ArrayList<>();
        for (Deck.Card card : cardsList) {
            rankList.add(card.rank);
        }

        rankList.sort(Collections.reverseOrder());

        // Check Royal Flush
        Hand royalFlushHand = hasRoyalFlush(cardsTable, likeRankCount, rankList);
        if (royalFlushHand != null) {
            return royalFlushHand;
        }
        // Check Straight Flush
        Hand straightFlush = hasStraightFlush(cardsTable, likeRankCount, rankList);
        if (straightFlush != null) {
            return straightFlush;
        }
        // Check Four of a Kind
        Hand fourOfAKind = hasFourOfAKind(cardsTable, likeRankCount, rankList);
        if (fourOfAKind != null) {
            return fourOfAKind;
        }
        // Check Full House
        Hand fullHouse = hasFullHouse(cardsTable, likeRankCount, rankList);
        if (fullHouse != null) {
            return fullHouse;
        }
        // Check Flush
        Hand flush = hasFlush(cardsTable, likeRankCount, rankList);
        if (flush != null) {
            return flush;
        }
        // Check Straight
        Hand straight = hasStraight(likeRankCount, rankList);
        if (straight != null) {
            return straight;
        }
        // Check Three of a Kind
        Hand threeOfAKind = hasThreeOfAKind(likeRankCount, rankList);
        if (threeOfAKind != null) {
            return threeOfAKind;
        }
        // Check Two Pair
        Hand twoPair = hasTwoPair(likeRankCount, rankList);
        if (twoPair != null) {
            return twoPair;
        }
        // Check Pair
        Hand pair = hasPair(likeRankCount, rankList);
        if (pair != null) {
            return pair;
        }

        // Check high card
        return new Hand(HAND_TYPE.HIGH_CARD, new ArrayList<>(List.of(rankList.getFirst())), rankList.subList(1, 5));
    }

    private Hand hasRoyalFlush(Boolean[][] cardsTable, Map<Deck.Card.RANK, Integer> likeRankCount, List<Deck.Card.RANK> rankList) {
        for (int suit = 0; suit < NUMBER_OF_SUITS; suit++) {
            if (cardsTable[suit][NUMBER_OF_RANKS - 1] &&
                    cardsTable[suit][NUMBER_OF_RANKS - 2] &&
                    cardsTable[suit][NUMBER_OF_RANKS - 3] &&
                    cardsTable[suit][NUMBER_OF_RANKS - 4] &&
                    cardsTable[suit][NUMBER_OF_RANKS - 5]) {
                return new Hand(HAND_TYPE.ROYAL_FLUSH,
                        new ArrayList<>(List.of(Deck.Card.RANK.ACE)),
                        new ArrayList<>(0));
            }
        }
        return null;
    }

    private Hand hasStraightFlush(Boolean[][] cardsTable, Map<Deck.Card.RANK, Integer> likeRankCount, List<Deck.Card.RANK> rankList) {
        for (int suit = 0; suit < NUMBER_OF_SUITS; suit++) {
            for (int highestCardIndex = NUMBER_OF_RANKS - 2;
                 highestCardIndex >= INDEX_OF_LOWEST_POSSIBLE_STRAIGHT_HIGH_CARD;
                 highestCardIndex--) {
                if ((cardsTable[suit][highestCardIndex] &&
                        cardsTable[suit][highestCardIndex - 1] &&
                        cardsTable[suit][highestCardIndex - 2] &&
                        cardsTable[suit][highestCardIndex - 3] &&
                        cardsTable[suit][highestCardIndex - 4])
                ) {
                    return new Hand(HAND_TYPE.STRAIGHT_FLUSH,
                            new ArrayList<>(List.of(numToRank.get(highestCardIndex + 2))),
                            new ArrayList<>(0));
                }
            }
            if (cardsTable[suit][0] &&
                    cardsTable[suit][1] &&
                    cardsTable[suit][2] &&
                    cardsTable[suit][3] &&
                    cardsTable[suit][NUMBER_OF_RANKS - 1]
            ) {
                return new Hand(HAND_TYPE.STRAIGHT_FLUSH,
                        new ArrayList<>(List.of(Deck.Card.RANK.FIVE)),
                        new ArrayList<>(0));
            }
        }
        return null;
    }

    private Hand hasFourOfAKind(Boolean[][] cardsTable, Map<Deck.Card.RANK, Integer> likeRankCount, List<Deck.Card.RANK> rankList) {
        for (int rankNumIndex = NUMBER_OF_RANKS - 1; rankNumIndex >= 0; rankNumIndex--) {
            if (likeRankCount.get(numToRank.get(rankNumIndex + 2)) == NUM_NEEDED_FOR_FOUR_OF_A_KIND) {
                int finalRankNumIndex = rankNumIndex;
                rankList.removeIf(rank -> rank.equals(numToRank.get(finalRankNumIndex + 2)));
                return new Hand(HAND_TYPE.FOUR_OF_A_KIND,
                        new ArrayList<>(List.of(numToRank.get(rankNumIndex + 2))),
                        rankList.subList(0, 1));
            }
        }
        return null;
    }

    private Hand hasFullHouse(Boolean[][] cardsTable, Map<Deck.Card.RANK, Integer> likeRankCount, List<Deck.Card.RANK> rankList) {
        for (int rankNumIndex1 = NUMBER_OF_RANKS - 1; rankNumIndex1 >= 0; rankNumIndex1--) {
            if (likeRankCount.get(numToRank.get(rankNumIndex1 + 2)) == NUM_NEEDED_FOR_THREE_OF_A_KIND) {
                for (int rankNumIndex2 = NUMBER_OF_RANKS - 1; rankNumIndex2 >= 0; rankNumIndex2--) {
                    if (!numToRank.get(rankNumIndex1 + 2).equals(numToRank.get(rankNumIndex2 + 2)) &&
                            likeRankCount.get(numToRank.get(rankNumIndex2 + 2)) >= NUM_NEEDED_FOR_PAIR) {
                        return new Hand(HAND_TYPE.FULL_HOUSE,
                                new ArrayList<>(List.of(numToRank.get(rankNumIndex1 + 2))),
                                new ArrayList<>(List.of(numToRank.get(rankNumIndex2 + 2))));
                    }
                }
            }
        }
        return null;
    }

    private Hand hasFlush(Boolean[][] cardsTable, Map<Deck.Card.RANK, Integer> likeRankCount, List<Deck.Card.RANK> rankList) {
        for (int suit = 0; suit < NUMBER_OF_SUITS; suit++) {
            List<Deck.Card.RANK> rankInComboList = new ArrayList<>(5);
            int countSuited = 0;
            for (int rank = NUMBER_OF_RANKS - 1; rank >= 0; rank--) {
                if (cardsTable[suit][rank]) {
                    rankInComboList.add(numToRank.get(rank + 2));
                    countSuited++;
                }
            }
            if (countSuited == NUM_SUITED_FOR_FLUSH) {
                return new Hand(HAND_TYPE.FLUSH, rankInComboList, new ArrayList<>(0));
            }
        }
        return null;
    }

    private Hand hasStraight(Map<Deck.Card.RANK, Integer> likeRankCount, List<Deck.Card.RANK> rankList) {
        for (int highestCardIndex = NUMBER_OF_RANKS - 1;
             highestCardIndex >= INDEX_OF_LOWEST_POSSIBLE_STRAIGHT_HIGH_CARD;
             highestCardIndex--) {
            if (likeRankCount.get(numToRank.get(highestCardIndex + 2)) >= 1 &&
                    likeRankCount.get(numToRank.get(highestCardIndex + 1)) >= 1 &&
                    likeRankCount.get(numToRank.get(highestCardIndex)) >= 1 &&
                    likeRankCount.get(numToRank.get(highestCardIndex - 1)) >= 1 &&
                    likeRankCount.get(numToRank.get(highestCardIndex - 2)) >= 1) {
                return new Hand(HAND_TYPE.STRAIGHT,
                        new ArrayList<>(List.of(numToRank.get(highestCardIndex + 2))),
                        new ArrayList<>(0));
            }
        }
        if (likeRankCount.get(Deck.Card.RANK.TWO) >= 1 &&
                likeRankCount.get(Deck.Card.RANK.THREE) >= 1 &&
                likeRankCount.get(Deck.Card.RANK.FOUR) >= 1 &&
                likeRankCount.get(Deck.Card.RANK.FIVE) >= 1 &&
                likeRankCount.get(Deck.Card.RANK.ACE) >= 1) {
            return new Hand(HAND_TYPE.STRAIGHT,
                    new ArrayList<>(List.of(Deck.Card.RANK.FIVE)),
                    new ArrayList<>(0));
        }
        return null;
    }

    private Hand hasThreeOfAKind(Map<Deck.Card.RANK, Integer> likeRankCount, List<Deck.Card.RANK> rankList) {
        for (int rankNumIndex = NUMBER_OF_RANKS - 1; rankNumIndex >= 0; rankNumIndex--) {
            if (likeRankCount.get(numToRank.get(rankNumIndex + 2)) == NUM_NEEDED_FOR_THREE_OF_A_KIND) {
                int finalRankNumIndex = rankNumIndex;
                rankList.removeIf(rank -> rank.equals(numToRank.get(finalRankNumIndex + 2)));
                return new Hand(HAND_TYPE.THREE_OF_A_KIND,
                        new ArrayList<>(List.of(numToRank.get(rankNumIndex + 2))),
                        rankList.subList(0, 2));
            }
        }
        return null;
    }

    private Hand hasTwoPair(Map<Deck.Card.RANK, Integer> likeRankCount, List<Deck.Card.RANK> rankList) {
        for (int rankNumIndex1 = NUMBER_OF_RANKS - 1; rankNumIndex1 >= 0; rankNumIndex1--) {
            if (likeRankCount.get(numToRank.get(rankNumIndex1 + 2)) == NUM_NEEDED_FOR_PAIR) {
                for (int rankNumIndex2 = rankNumIndex1 - 1; rankNumIndex2 >= 0; rankNumIndex2--) {
                    if (!numToRank.get(rankNumIndex1 + 2).equals(numToRank.get(rankNumIndex2 + 2)) &&
                            likeRankCount.get(numToRank.get(rankNumIndex2 + 2)) >= NUM_NEEDED_FOR_PAIR) {
                        final Deck.Card.RANK firstPairRank = numToRank.get(rankNumIndex1 + 2);
                        final Deck.Card.RANK secondPairRank = numToRank.get(rankNumIndex2 + 2);
                        rankList.removeIf(rank -> rank.equals(firstPairRank) || rank.equals(secondPairRank));
                        return new Hand(HAND_TYPE.TWO_PAIR,
                                new ArrayList<>(List.of(firstPairRank, secondPairRank)),
                                rankList.subList(0, 1));
                    }
                }
            }
        }
        return null;
    }

    private Hand hasPair(Map<Deck.Card.RANK, Integer> likeRankCount, List<Deck.Card.RANK> rankList) {
        for (int rankNumIndex = NUMBER_OF_RANKS - 1; rankNumIndex >= 0; rankNumIndex--) {
            if (likeRankCount.get(numToRank.get(rankNumIndex + 2)) == NUM_NEEDED_FOR_PAIR) {
                int finalRankNumIndex = rankNumIndex;
                rankList.removeIf(rank -> rank.equals(numToRank.get(finalRankNumIndex + 2)));
                return new Hand(HAND_TYPE.PAIR,
                        new ArrayList<>(List.of(numToRank.get(rankNumIndex + 2))),
                        rankList.subList(0, 3));
            }
        }
        return null;
    }


    public boolean allSameSuit(List<Deck.Card> cards) {
        // Map to count the number of cards in each suit
        Map<Deck.Card.SUIT, Integer> suitCount = new HashMap<>();
        for (Deck.Card.SUIT suit : Deck.Card.SUIT.values()) {
            suitCount.put(suit, 0);
        }

        // Count the number of cards for each suit
        for (Deck.Card card : cards) {
            suitCount.put(card.suit, suitCount.get(card.suit) + 1);
        }

        // Check the number of suits with zero cards
        int zeroCount = 0;

        for (int count : suitCount.values()) {
            if (count == 0) {
                zeroCount++;
            }
        }

        // Return true if exactly three suits have zero cards and one suit has four cards
        return zeroCount == 3;
    }

}
