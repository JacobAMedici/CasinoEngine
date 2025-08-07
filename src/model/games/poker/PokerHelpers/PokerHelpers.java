package model.games.poker.PokerHelpers;

import model.cards.SuitedCard;

import java.util.*;
import java.util.stream.Stream;

import static model.games.poker.PokerHelpers.PokerHand.*;

/**
 * Helper class for poker game logic, including hand evaluation and comparison.
 */
public class PokerHelpers {

    /**
     * Number of cards in a standard poker hand. Do not use this, this is for reverse compatability.
     *
     * @param communityCards the community cards
     * @param holeCards the hole cards
     * @return the best five-card poker hand
     */
    public PokerHand findBestFiveCardHand(List<SuitedCard> communityCards, List<SuitedCard> holeCards){
        List<SuitedCard> cardsList =
            new ArrayList<>(Stream.concat(communityCards.stream(), holeCards.stream()).toList());
        Collections.sort(cardsList);
        return findBestFiveCardHand(cardsList);
    }

    /**
     * Finds the best five-card poker hand from the given community and hole cards.
     *
     * @param cardsInHand the cards to make the hand with
     * @return the best five-card poker hand
     */
    public PokerHand findBestFiveCardHand(List<SuitedCard> cardsInHand){

        if (cardsInHand.size() < NUM_CARDS_IN_POKER_HAND) {
            throw new IllegalArgumentException(String.format("Cannot make best five card hand with only %d cards",
                cardsInHand.size()));
        }

        boolean[][] cardsTable = new boolean[NUMBER_OF_SUITS][NUMBER_OF_RANKS];
        for (int suit = 0; suit < NUMBER_OF_SUITS; suit++) {
            for (int rank = 0; rank < NUMBER_OF_RANKS; rank++) {
                cardsTable[suit][rank] = false;
            }
        }

        for (SuitedCard card : cardsInHand) {
            cardsTable[suitToInt.get(card.suit())][SuitedCard.rankToNum.get(card.rank()) - 2] = true;
        }

        Map<SuitedCard.RANK, Integer> likeRankCount = new HashMap<>();
        for (SuitedCard.RANK rank : SuitedCard.RANK.values()) {
            likeRankCount.put(rank, 0);
        }

        for (SuitedCard card : cardsInHand) {
            likeRankCount.replace(card.rank(), likeRankCount.get(card.rank()) + 1);
        }

        List<SuitedCard.RANK> rankList = new ArrayList<>();
        for (SuitedCard card : cardsInHand) {
            rankList.add(card.rank());
        }

        rankList.sort(Collections.reverseOrder());

        // Check Royal Flush
        PokerHand royalFlushHand = hasRoyalFlush(cardsTable, likeRankCount, rankList);
        if (royalFlushHand != null) {
            return royalFlushHand;
        }
        // Check Straight Flush
        PokerHand straightFlush = hasStraightFlush(cardsTable, likeRankCount, rankList);
        if (straightFlush != null) {
            return straightFlush;
        }
        // Check Four of a Kind
        PokerHand fourOfAKind = hasFourOfAKind(cardsTable, likeRankCount, rankList);
        if (fourOfAKind != null) {
            return fourOfAKind;
        }
        // Check Full House
        PokerHand fullHouse = hasFullHouse(cardsTable, likeRankCount, rankList);
        if (fullHouse != null) {
            return fullHouse;
        }
        // Check Flush
        PokerHand flush = hasFlush(cardsTable, likeRankCount, rankList);
        if (flush != null) {
            return flush;
        }
        // Check Straight
        PokerHand straight = hasStraight(likeRankCount, rankList);
        if (straight != null) {
            return straight;
        }
        // Check Three of a Kind
        PokerHand threeOfAKind = hasThreeOfAKind(likeRankCount, rankList);
        if (threeOfAKind != null) {
            return threeOfAKind;
        }
        // Check Two Pair
        PokerHand twoPair = hasTwoPair(likeRankCount, rankList);
        if (twoPair != null) {
            return twoPair;
        }
        // Check Pair
        PokerHand pair = hasPair(likeRankCount, rankList);
        if (pair != null) {
            return pair;
        }

        // Check high card
        return new PokerHand(HAND_TYPE.HIGH_CARD, new ArrayList<>(List.of(rankList.getFirst())), rankList.subList(1, 5));
    }

    private PokerHand hasRoyalFlush(boolean[][] cardsTable, Map<SuitedCard.RANK, Integer> likeRankCount, List<SuitedCard.RANK> rankList) {
        for (int suit = 0; suit < NUMBER_OF_SUITS; suit++) {
            if (cardsTable[suit][NUMBER_OF_RANKS - 1] &&
                    cardsTable[suit][NUMBER_OF_RANKS - 2] &&
                    cardsTable[suit][NUMBER_OF_RANKS - 3] &&
                    cardsTable[suit][NUMBER_OF_RANKS - 4] &&
                    cardsTable[suit][NUMBER_OF_RANKS - 5]) {
                return new PokerHand(HAND_TYPE.ROYAL_FLUSH,
                        new ArrayList<>(List.of(SuitedCard.RANK.ACE)),
                        new ArrayList<>(0));
            }
        }
        return null;
    }

    private PokerHand hasStraightFlush(boolean[][] cardsTable, Map<SuitedCard.RANK, Integer> likeRankCount, List<SuitedCard.RANK> rankList) {
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
                    return new PokerHand(HAND_TYPE.STRAIGHT_FLUSH,
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
                return new PokerHand(HAND_TYPE.STRAIGHT_FLUSH,
                        new ArrayList<>(List.of(SuitedCard.RANK.FIVE)),
                        new ArrayList<>(0));
            }
        }
        return null;
    }

    private PokerHand hasFourOfAKind(boolean[][] cardsTable, Map<SuitedCard.RANK, Integer> likeRankCount, List<SuitedCard.RANK> rankList) {
        for (int rankNumIndex = NUMBER_OF_RANKS - 1; rankNumIndex >= 0; rankNumIndex--) {
            if (likeRankCount.get(numToRank.get(rankNumIndex + 2)) == NUM_NEEDED_FOR_FOUR_OF_A_KIND) {
                int finalRankNumIndex = rankNumIndex;
                rankList.removeIf(rank -> rank.equals(numToRank.get(finalRankNumIndex + 2)));
                return new PokerHand(HAND_TYPE.FOUR_OF_A_KIND,
                        new ArrayList<>(List.of(numToRank.get(rankNumIndex + 2))),
                        rankList.subList(0, 1));
            }
        }
        return null;
    }

    private PokerHand hasFullHouse(boolean[][] cardsTable, Map<SuitedCard.RANK, Integer> likeRankCount, List<SuitedCard.RANK> rankList) {
        for (int rankNumIndex1 = NUMBER_OF_RANKS - 1; rankNumIndex1 >= 0; rankNumIndex1--) {
            if (likeRankCount.get(numToRank.get(rankNumIndex1 + 2)) == NUM_NEEDED_FOR_THREE_OF_A_KIND) {
                for (int rankNumIndex2 = NUMBER_OF_RANKS - 1; rankNumIndex2 >= 0; rankNumIndex2--) {
                    if (!numToRank.get(rankNumIndex1 + 2).equals(numToRank.get(rankNumIndex2 + 2)) &&
                            likeRankCount.get(numToRank.get(rankNumIndex2 + 2)) >= NUM_NEEDED_FOR_PAIR) {
                        return new PokerHand(HAND_TYPE.FULL_HOUSE,
                                new ArrayList<>(List.of(numToRank.get(rankNumIndex1 + 2))),
                                new ArrayList<>(List.of(numToRank.get(rankNumIndex2 + 2))));
                    }
                }
            }
        }
        return null;
    }

    private PokerHand hasFlush(boolean[][] cardsTable, Map<SuitedCard.RANK, Integer> likeRankCount, List<SuitedCard.RANK> rankList) {
        for (int suit = 0; suit < NUMBER_OF_SUITS; suit++) {
            List<SuitedCard.RANK> rankInComboList = new ArrayList<>(5);
            int countSuited = 0;
            for (int rank = NUMBER_OF_RANKS - 1; rank >= 0; rank--) {
                if (cardsTable[suit][rank]) {
                    rankInComboList.add(numToRank.get(rank + 2));
                    countSuited++;
                }
            }
            if (countSuited == NUM_SUITED_FOR_FLUSH) {
                return new PokerHand(HAND_TYPE.FLUSH, rankInComboList, new ArrayList<>(0));
            }
        }
        return null;
    }

    private PokerHand hasStraight(Map<SuitedCard.RANK, Integer> likeRankCount, List<SuitedCard.RANK> rankList) {
        for (int highestCardIndex = NUMBER_OF_RANKS - 1;
             highestCardIndex >= INDEX_OF_LOWEST_POSSIBLE_STRAIGHT_HIGH_CARD;
             highestCardIndex--) {
            if (likeRankCount.get(numToRank.get(highestCardIndex + 2)) >= 1 &&
                    likeRankCount.get(numToRank.get(highestCardIndex + 1)) >= 1 &&
                    likeRankCount.get(numToRank.get(highestCardIndex)) >= 1 &&
                    likeRankCount.get(numToRank.get(highestCardIndex - 1)) >= 1 &&
                    likeRankCount.get(numToRank.get(highestCardIndex - 2)) >= 1) {
                return new PokerHand(HAND_TYPE.STRAIGHT,
                        new ArrayList<>(List.of(numToRank.get(highestCardIndex + 2))),
                        new ArrayList<>(0));
            }
        }
        if (likeRankCount.get(SuitedCard.RANK.TWO) >= 1 &&
                likeRankCount.get(SuitedCard.RANK.THREE) >= 1 &&
                likeRankCount.get(SuitedCard.RANK.FOUR) >= 1 &&
                likeRankCount.get(SuitedCard.RANK.FIVE) >= 1 &&
                likeRankCount.get(SuitedCard.RANK.ACE) >= 1) {
            return new PokerHand(HAND_TYPE.STRAIGHT,
                    new ArrayList<>(List.of(SuitedCard.RANK.FIVE)),
                    new ArrayList<>(0));
        }
        return null;
    }

    private PokerHand hasThreeOfAKind(Map<SuitedCard.RANK, Integer> likeRankCount, List<SuitedCard.RANK> rankList) {
        for (int rankNumIndex = NUMBER_OF_RANKS - 1; rankNumIndex >= 0; rankNumIndex--) {
            if (likeRankCount.get(numToRank.get(rankNumIndex + 2)) == NUM_NEEDED_FOR_THREE_OF_A_KIND) {
                int finalRankNumIndex = rankNumIndex;
                rankList.removeIf(rank -> rank.equals(numToRank.get(finalRankNumIndex + 2)));
                return new PokerHand(HAND_TYPE.THREE_OF_A_KIND,
                        new ArrayList<>(List.of(numToRank.get(rankNumIndex + 2))),
                        rankList.subList(0, 2));
            }
        }
        return null;
    }

    private PokerHand hasTwoPair(Map<SuitedCard.RANK, Integer> likeRankCount, List<SuitedCard.RANK> rankList) {
        for (int rankNumIndex1 = NUMBER_OF_RANKS - 1; rankNumIndex1 >= 0; rankNumIndex1--) {
            if (likeRankCount.get(numToRank.get(rankNumIndex1 + 2)) == NUM_NEEDED_FOR_PAIR) {
                for (int rankNumIndex2 = rankNumIndex1 - 1; rankNumIndex2 >= 0; rankNumIndex2--) {
                    if (!numToRank.get(rankNumIndex1 + 2).equals(numToRank.get(rankNumIndex2 + 2)) &&
                            likeRankCount.get(numToRank.get(rankNumIndex2 + 2)) >= NUM_NEEDED_FOR_PAIR) {
                        final SuitedCard.RANK firstPairRank = numToRank.get(rankNumIndex1 + 2);
                        final SuitedCard.RANK secondPairRank = numToRank.get(rankNumIndex2 + 2);
                        rankList.removeIf(rank -> rank.equals(firstPairRank) || rank.equals(secondPairRank));
                        return new PokerHand(HAND_TYPE.TWO_PAIR,
                                new ArrayList<>(List.of(firstPairRank, secondPairRank)),
                                rankList.subList(0, 1));
                    }
                }
            }
        }
        return null;
    }

    private PokerHand hasPair(Map<SuitedCard.RANK, Integer> likeRankCount, List<SuitedCard.RANK> rankList) {
        for (int rankNumIndex = NUMBER_OF_RANKS - 1; rankNumIndex >= 0; rankNumIndex--) {
            if (likeRankCount.get(numToRank.get(rankNumIndex + 2)) == NUM_NEEDED_FOR_PAIR) {
                int finalRankNumIndex = rankNumIndex;
                rankList.removeIf(rank -> rank.equals(numToRank.get(finalRankNumIndex + 2)));
                return new PokerHand(HAND_TYPE.PAIR,
                        new ArrayList<>(List.of(numToRank.get(rankNumIndex + 2))),
                        rankList.subList(0, 3));
            }
        }
        return null;
    }


    /**
     * Checks if all cards in the list are of the same suit.
     *
     * @param cards the list of cards to check
     * @return true if all cards are of the same suit, false otherwise
     */
    public boolean allSameSuit(List<SuitedCard> cards) {
        // Map to count the number of cards in each suit
        Map<SuitedCard.SUIT, Integer> suitCount = new HashMap<>();
        for (SuitedCard.SUIT suit : SuitedCard.SUIT.values()) {
            suitCount.put(suit, 0);
        }

        // Count the number of cards for each suit
        for (SuitedCard card : cards) {
            suitCount.put(card.suit(), suitCount.get(card.suit()) + 1);
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
