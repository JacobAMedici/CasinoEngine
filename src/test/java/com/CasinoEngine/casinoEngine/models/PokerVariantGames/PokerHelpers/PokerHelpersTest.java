package com.CasinoEngine.casinoEngine.models.PokerVariantGames.PokerHelpers;

import com.CasinoEngine.casinoEngine.models.AllHelpers.Deck;
import com.CasinoEngine.casinoEngine.models.AllHelpers.RequestFailedException;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class PokerHelpersTest {

    PokerHelpers pokerHelpers = new PokerHelpers();

    PokerHelpers.Hand baseRoyalFlush =
            new PokerHelpers.Hand(PokerHelpers.HAND_TYPE.ROYAL_FLUSH,
                    new ArrayList<>(List.of(Deck.Card.RANK.ACE)),
                    new ArrayList<>(0)
            );

    PokerHelpers.Hand baseStraightFlush =
            new PokerHelpers.Hand(PokerHelpers.HAND_TYPE.STRAIGHT_FLUSH,
                    new ArrayList<>(List.of(Deck.Card.RANK.KING)),
                    new ArrayList<>(0)
            );

    PokerHelpers.Hand baseFourOfAKind =
            new PokerHelpers.Hand(PokerHelpers.HAND_TYPE.FOUR_OF_A_KIND,
                    new ArrayList<>(List.of(Deck.Card.RANK.ACE)),
                    new ArrayList<>(List.of(Deck.Card.RANK.TWO))
            );

    PokerHelpers.Hand baseFullHouse =
            new PokerHelpers.Hand(PokerHelpers.HAND_TYPE.FULL_HOUSE,
                    new ArrayList<>(List.of(Deck.Card.RANK.ACE)),
                    new ArrayList<>(List.of(Deck.Card.RANK.KING))
            );

    PokerHelpers.Hand baseFlush =
            new PokerHelpers.Hand(PokerHelpers.HAND_TYPE.FLUSH,
                    new ArrayList<>(List.of(Deck.Card.RANK.ACE,
                            Deck.Card.RANK.QUEEN,
                            Deck.Card.RANK.JACK,
                            Deck.Card.RANK.TEN,
                            Deck.Card.RANK.NINE)),
                    new ArrayList<>(0)
            );

    PokerHelpers.Hand baseStraight =
            new PokerHelpers.Hand(PokerHelpers.HAND_TYPE.STRAIGHT,
                    new ArrayList<>(List.of(Deck.Card.RANK.ACE)),
                    new ArrayList<>(0)
            );

    PokerHelpers.Hand baseThreeOfAKind =
            new PokerHelpers.Hand(PokerHelpers.HAND_TYPE.THREE_OF_A_KIND,
                    new ArrayList<>(List.of(Deck.Card.RANK.ACE)),
                    new ArrayList<>(List.of(Deck.Card.RANK.KING, Deck.Card.RANK.QUEEN))
            );

    PokerHelpers.Hand baseTwoPair =
            new PokerHelpers.Hand(PokerHelpers.HAND_TYPE.TWO_PAIR,
                    new ArrayList<>(List.of(Deck.Card.RANK.ACE, Deck.Card.RANK.KING)),
                    new ArrayList<>(List.of(Deck.Card.RANK.QUEEN))
            );

    PokerHelpers.Hand basePair =
            new PokerHelpers.Hand(PokerHelpers.HAND_TYPE.PAIR,
                    new ArrayList<>(List.of(Deck.Card.RANK.ACE)),
                    new ArrayList<>(List.of(Deck.Card.RANK.KING, Deck.Card.RANK.QUEEN, Deck.Card.RANK.JACK))
            );

    PokerHelpers.Hand baseHighCard =
            new PokerHelpers.Hand(PokerHelpers.HAND_TYPE.HIGH_CARD,
                    new ArrayList<>(List.of(Deck.Card.RANK.ACE)),
                    new ArrayList<>(List.of(Deck.Card.RANK.QUEEN,
                            Deck.Card.RANK.JACK,
                            Deck.Card.RANK.TEN,
                            Deck.Card.RANK.NINE))
            );

    @Test
    public void handCompareToTest() {
        assertTrue(baseRoyalFlush.compareTo(baseStraightFlush) > 0);
        assertTrue(baseRoyalFlush.compareTo(baseFourOfAKind) > 0);
        assertTrue(baseRoyalFlush.compareTo(baseFullHouse) > 0);
        assertTrue(baseRoyalFlush.compareTo(baseFlush) > 0);
        assertTrue(baseRoyalFlush.compareTo(baseStraight) > 0);
        assertTrue(baseRoyalFlush.compareTo(baseFlush) > 0);
        assertTrue(baseRoyalFlush.compareTo(baseTwoPair) > 0);
        assertTrue(baseRoyalFlush.compareTo(basePair) > 0);
        assertTrue(baseRoyalFlush.compareTo(baseHighCard) > 0);
    }

    @Test
    public void findBestFiveCardHandRoyalFlush() throws RequestFailedException {
        // Make sure it finds the right royal flush
        PokerHelpers.Hand royalFlushHand = pokerHelpers.findBestFiveCardHand(
                List.of(
                        new Deck.Card(Deck.Card.RANK.ACE, Deck.Card.SUIT.HEARTS),
                        new Deck.Card(Deck.Card.RANK.QUEEN, Deck.Card.SUIT.HEARTS)),
                List.of(
                        new Deck.Card(Deck.Card.RANK.KING, Deck.Card.SUIT.HEARTS),
                        new Deck.Card(Deck.Card.RANK.JACK, Deck.Card.SUIT.HEARTS),
                        new Deck.Card(Deck.Card.RANK.TEN, Deck.Card.SUIT.HEARTS))
        );
        assertEquals(baseRoyalFlush, royalFlushHand);
    }

    @Test
    public void findBestStraightFlushTest() throws RequestFailedException {
        // Make sure it finds the right straight flush
        PokerHelpers.Hand baseStraightFlushHand = pokerHelpers.findBestFiveCardHand(
                List.of(
                        new Deck.Card(Deck.Card.RANK.NINE, Deck.Card.SUIT.HEARTS),
                        new Deck.Card(Deck.Card.RANK.JACK, Deck.Card.SUIT.HEARTS)),
                List.of(
                        new Deck.Card(Deck.Card.RANK.TEN, Deck.Card.SUIT.HEARTS),
                        new Deck.Card(Deck.Card.RANK.QUEEN, Deck.Card.SUIT.HEARTS),
                        new Deck.Card(Deck.Card.RANK.KING, Deck.Card.SUIT.HEARTS))
        );
        assertEquals(baseStraightFlush, baseStraightFlushHand);
        // Find lower straight flush
        PokerHelpers.Hand lowStraightFlush = pokerHelpers.findBestFiveCardHand(
                List.of(
                        new Deck.Card(Deck.Card.RANK.EIGHT, Deck.Card.SUIT.DIAMONDS),
                        new Deck.Card(Deck.Card.RANK.SEVEN, Deck.Card.SUIT.DIAMONDS)),
                List.of(
                        new Deck.Card(Deck.Card.RANK.SIX, Deck.Card.SUIT.DIAMONDS),
                        new Deck.Card(Deck.Card.RANK.FIVE, Deck.Card.SUIT.DIAMONDS),
                        new Deck.Card(Deck.Card.RANK.FOUR, Deck.Card.SUIT.DIAMONDS))
        );
        // Compare low to high
        assertTrue(lowStraightFlush.compareTo(baseStraightFlushHand) < 0);
        // Compare high to low
        assertTrue(baseStraightFlushHand.compareTo(lowStraightFlush) > 0);
    }

    @Test
    public void findBestFourOfAKindTest() throws RequestFailedException {
        PokerHelpers.Hand baseFourOfAKindHand = pokerHelpers.findBestFiveCardHand(
                List.of(
                        new Deck.Card(Deck.Card.RANK.ACE, Deck.Card.SUIT.HEARTS),
                        new Deck.Card(Deck.Card.RANK.ACE, Deck.Card.SUIT.CLUBS)),
                List.of(
                        new Deck.Card(Deck.Card.RANK.ACE, Deck.Card.SUIT.DIAMONDS),
                        new Deck.Card(Deck.Card.RANK.ACE, Deck.Card.SUIT.SPADES),
                        new Deck.Card(Deck.Card.RANK.TWO, Deck.Card.SUIT.HEARTS)));
        assertEquals(baseFourOfAKind, baseFourOfAKindHand);

        // Lower Four of a Kind hand with four Kings
        PokerHelpers.Hand lowFourOfAKindHand = pokerHelpers.findBestFiveCardHand(
                List.of(
                        new Deck.Card(Deck.Card.RANK.KING, Deck.Card.SUIT.HEARTS),
                        new Deck.Card(Deck.Card.RANK.KING, Deck.Card.SUIT.DIAMONDS)
                ),
                List.of(
                        new Deck.Card(Deck.Card.RANK.KING, Deck.Card.SUIT.CLUBS),
                        new Deck.Card(Deck.Card.RANK.KING, Deck.Card.SUIT.SPADES),
                        new Deck.Card(Deck.Card.RANK.THREE, Deck.Card.SUIT.HEARTS)
                )
        );

        // Higher Four of a Kind hand with four Aces and a Three
        PokerHelpers.Hand highFourOfAKindHand = pokerHelpers.findBestFiveCardHand(
                List.of(
                        new Deck.Card(Deck.Card.RANK.ACE, Deck.Card.SUIT.HEARTS),
                        new Deck.Card(Deck.Card.RANK.ACE, Deck.Card.SUIT.DIAMONDS)
                ),
                List.of(
                        new Deck.Card(Deck.Card.RANK.ACE, Deck.Card.SUIT.CLUBS),
                        new Deck.Card(Deck.Card.RANK.ACE, Deck.Card.SUIT.SPADES),
                        new Deck.Card(Deck.Card.RANK.THREE, Deck.Card.SUIT.DIAMONDS)
                )
        );

        // Compare lower to base (expect less than 0)
        assertTrue(lowFourOfAKindHand.compareTo(baseFourOfAKind) < 0);

        // Compare higher to base (expect greater than 0)
        assertTrue(highFourOfAKindHand.compareTo(baseFourOfAKind) > 0);

        // Compare base to higher (expect less than 0)
        assertTrue(baseFourOfAKind.compareTo(highFourOfAKindHand) < 0);
    }

    @Test
    public void findBestFullHouseTest() throws RequestFailedException {
        PokerHelpers.Hand actualHand = pokerHelpers.findBestFiveCardHand(
                List.of(
                        new Deck.Card(Deck.Card.RANK.ACE, Deck.Card.SUIT.HEARTS),
                        new Deck.Card(Deck.Card.RANK.ACE, Deck.Card.SUIT.CLUBS)),
                List.of(
                        new Deck.Card(Deck.Card.RANK.ACE, Deck.Card.SUIT.DIAMONDS),
                        new Deck.Card(Deck.Card.RANK.KING, Deck.Card.SUIT.SPADES),
                        new Deck.Card(Deck.Card.RANK.KING, Deck.Card.SUIT.HEARTS)));
        assertEquals(baseFullHouse, actualHand);

        // Lower Full House with three Kings and two Queens
        PokerHelpers.Hand lowFullHouseHand = pokerHelpers.findBestFiveCardHand(
                List.of(
                        new Deck.Card(Deck.Card.RANK.KING, Deck.Card.SUIT.HEARTS),
                        new Deck.Card(Deck.Card.RANK.KING, Deck.Card.SUIT.DIAMONDS)),
                List.of(
                        new Deck.Card(Deck.Card.RANK.KING, Deck.Card.SUIT.CLUBS),
                        new Deck.Card(Deck.Card.RANK.QUEEN, Deck.Card.SUIT.SPADES),
                        new Deck.Card(Deck.Card.RANK.QUEEN, Deck.Card.SUIT.HEARTS)
                )
        );

        // Higher Full House with three Aces and two Queens
        PokerHelpers.Hand highFullHouseHand = pokerHelpers.findBestFiveCardHand(
                List.of(
                        new Deck.Card(Deck.Card.RANK.ACE, Deck.Card.SUIT.HEARTS),
                        new Deck.Card(Deck.Card.RANK.ACE, Deck.Card.SUIT.CLUBS)),
                List.of(
                        new Deck.Card(Deck.Card.RANK.ACE, Deck.Card.SUIT.DIAMONDS),
                        new Deck.Card(Deck.Card.RANK.QUEEN, Deck.Card.SUIT.SPADES),
                        new Deck.Card(Deck.Card.RANK.QUEEN, Deck.Card.SUIT.HEARTS)
                )
        );

        // Compare lower to base (expect less than 0)
        assertTrue(lowFullHouseHand.compareTo(baseFullHouse) < 0);

        // Compare higher to base (expect greater than 0)
        assert (highFullHouseHand.compareTo(baseFullHouse) < 0);

        // Compare base to higher (expect less than 0)
        assertTrue(baseFullHouse.compareTo(highFullHouseHand) > 0);
    }

    @Test
    public void findBestFlushTest() throws RequestFailedException {
        PokerHelpers.Hand baseFlushHand = pokerHelpers.findBestFiveCardHand(
                List.of(
                        new Deck.Card(Deck.Card.RANK.QUEEN, Deck.Card.SUIT.HEARTS),
                        new Deck.Card(Deck.Card.RANK.JACK, Deck.Card.SUIT.HEARTS)),
                List.of(
                        new Deck.Card(Deck.Card.RANK.ACE, Deck.Card.SUIT.HEARTS),
                        new Deck.Card(Deck.Card.RANK.TEN, Deck.Card.SUIT.HEARTS),
                        new Deck.Card(Deck.Card.RANK.NINE, Deck.Card.SUIT.HEARTS)));
        assertEquals(baseFlush, baseFlushHand);

        // Lower Flush with a high card King
        PokerHelpers.Hand lowFlushHand = pokerHelpers.findBestFiveCardHand(
                List.of(
                        new Deck.Card(Deck.Card.RANK.KING, Deck.Card.SUIT.HEARTS),
                        new Deck.Card(Deck.Card.RANK.QUEEN, Deck.Card.SUIT.HEARTS)),
                List.of(
                        new Deck.Card(Deck.Card.RANK.JACK, Deck.Card.SUIT.HEARTS),
                        new Deck.Card(Deck.Card.RANK.TEN, Deck.Card.SUIT.HEARTS),
                        new Deck.Card(Deck.Card.RANK.SEVEN, Deck.Card.SUIT.HEARTS)
                )
        );

        // Higher Flush with a high card Ace and higher second card (King)
        PokerHelpers.Hand highFlushHand = pokerHelpers.findBestFiveCardHand(
                List.of(
                        new Deck.Card(Deck.Card.RANK.ACE, Deck.Card.SUIT.HEARTS),
                        new Deck.Card(Deck.Card.RANK.KING, Deck.Card.SUIT.HEARTS)),
                List.of(
                        new Deck.Card(Deck.Card.RANK.QUEEN, Deck.Card.SUIT.HEARTS),
                        new Deck.Card(Deck.Card.RANK.JACK, Deck.Card.SUIT.HEARTS),
                        new Deck.Card(Deck.Card.RANK.NINE, Deck.Card.SUIT.HEARTS)
                )
        );

        // Compare lower to base (expect less than 0)
        assertTrue(lowFlushHand.compareTo(baseFlushHand) < 0);

        // Compare higher to base (expect greater than 0)
        assertTrue(highFlushHand.compareTo(baseFlushHand) > 0);

        // Compare base to higher (expect less than 0)
        assertTrue(baseFlushHand.compareTo(highFlushHand) < 0);
    }

    @Test
    public void findBestStraightTest() throws RequestFailedException {
        PokerHelpers.Hand baseStraightHand = pokerHelpers.findBestFiveCardHand(
                List.of(
                        new Deck.Card(Deck.Card.RANK.KING, Deck.Card.SUIT.HEARTS),
                        new Deck.Card(Deck.Card.RANK.QUEEN, Deck.Card.SUIT.HEARTS)),
                List.of(
                        new Deck.Card(Deck.Card.RANK.JACK, Deck.Card.SUIT.SPADES),
                        new Deck.Card(Deck.Card.RANK.TEN, Deck.Card.SUIT.CLUBS),
                        new Deck.Card(Deck.Card.RANK.ACE, Deck.Card.SUIT.HEARTS)));
        assertEquals(baseStraight, baseStraightHand);

        // Lower Straight
        PokerHelpers.Hand lowStraightHand = pokerHelpers.findBestFiveCardHand(
                List.of(
                        new Deck.Card(Deck.Card.RANK.NINE, Deck.Card.SUIT.HEARTS),
                        new Deck.Card(Deck.Card.RANK.EIGHT, Deck.Card.SUIT.HEARTS)),
                List.of(
                        new Deck.Card(Deck.Card.RANK.SEVEN, Deck.Card.SUIT.SPADES),
                        new Deck.Card(Deck.Card.RANK.SIX, Deck.Card.SUIT.CLUBS),
                        new Deck.Card(Deck.Card.RANK.TEN, Deck.Card.SUIT.HEARTS)
                )
        );

        // Alternative Straight that also peaks at an Ace but includes a different lower card sequence (Wheel Straight)
        PokerHelpers.Hand altStraightHand = pokerHelpers.findBestFiveCardHand(
                List.of(
                        new Deck.Card(Deck.Card.RANK.ACE, Deck.Card.SUIT.DIAMONDS),
                        new Deck.Card(Deck.Card.RANK.TWO, Deck.Card.SUIT.HEARTS)),
                List.of(
                        new Deck.Card(Deck.Card.RANK.THREE, Deck.Card.SUIT.CLUBS),
                        new Deck.Card(Deck.Card.RANK.FOUR, Deck.Card.SUIT.SPADES),
                        new Deck.Card(Deck.Card.RANK.FIVE, Deck.Card.SUIT.HEARTS)
                )
        );

        // Compare lower to base (expect less than 0)
        assertTrue(lowStraightHand.compareTo(baseStraightHand) < 0);

        // Compare alternative to base (expect less than 0)
        assertTrue(altStraightHand.compareTo(baseStraightHand) < 0);

        // Compare base to alternative (expect greater than 0)
        assertTrue(baseStraightHand.compareTo(altStraightHand) > 0);
    }

    @Test
    public void findBestThreeOfAKindTest() throws RequestFailedException {
        PokerHelpers.Hand baseThreeOfAKindHand = pokerHelpers.findBestFiveCardHand(
                List.of(
                        new Deck.Card(Deck.Card.RANK.ACE, Deck.Card.SUIT.HEARTS),
                        new Deck.Card(Deck.Card.RANK.ACE, Deck.Card.SUIT.CLUBS)),
                List.of(
                        new Deck.Card(Deck.Card.RANK.ACE, Deck.Card.SUIT.DIAMONDS),
                        new Deck.Card(Deck.Card.RANK.KING, Deck.Card.SUIT.SPADES),
                        new Deck.Card(Deck.Card.RANK.QUEEN, Deck.Card.SUIT.HEARTS)));
        assertEquals(baseThreeOfAKind, baseThreeOfAKindHand);

        // Lower Three of a Kind hand with three Kings and kickers Ace and Queen
        PokerHelpers.Hand lowThreeOfAKindHand = pokerHelpers.findBestFiveCardHand(
                List.of(
                        new Deck.Card(Deck.Card.RANK.KING, Deck.Card.SUIT.HEARTS),
                        new Deck.Card(Deck.Card.RANK.KING, Deck.Card.SUIT.DIAMONDS)),
                List.of(
                        new Deck.Card(Deck.Card.RANK.KING, Deck.Card.SUIT.CLUBS),
                        new Deck.Card(Deck.Card.RANK.ACE, Deck.Card.SUIT.SPADES),
                        new Deck.Card(Deck.Card.RANK.QUEEN, Deck.Card.SUIT.HEARTS)
                )
        );

        // Compare lower to base (expect less than 0)
        assertTrue(lowThreeOfAKindHand.compareTo(baseThreeOfAKindHand) < 0);

        // Create an alternative Three of a Kind with three Aces and kickers Ace and Jack
        PokerHelpers.Hand altThreeOfAKindHand = pokerHelpers.findBestFiveCardHand(
                List.of(
                        new Deck.Card(Deck.Card.RANK.ACE, Deck.Card.SUIT.HEARTS),
                        new Deck.Card(Deck.Card.RANK.ACE, Deck.Card.SUIT.CLUBS)),
                List.of(
                        new Deck.Card(Deck.Card.RANK.ACE, Deck.Card.SUIT.DIAMONDS),
                        new Deck.Card(Deck.Card.RANK.JACK, Deck.Card.SUIT.SPADES),
                        new Deck.Card(Deck.Card.RANK.TEN, Deck.Card.SUIT.HEARTS)
                )
        );

        // Compare alternative to base (expect less than 0)
        assertTrue(altThreeOfAKindHand.compareTo(baseThreeOfAKindHand) < 0);

        // Compare base to alternative (expect greater than 0)
        assertTrue(baseThreeOfAKindHand.compareTo(altThreeOfAKindHand) > 0);
    }

    @Test
    public void findBestTwoPairTest() throws RequestFailedException {
        PokerHelpers.Hand baseTwoPairHand = pokerHelpers.findBestFiveCardHand(
                List.of(
                        new Deck.Card(Deck.Card.RANK.ACE, Deck.Card.SUIT.HEARTS),
                        new Deck.Card(Deck.Card.RANK.ACE, Deck.Card.SUIT.CLUBS)),
                List.of(
                        new Deck.Card(Deck.Card.RANK.KING, Deck.Card.SUIT.DIAMONDS),
                        new Deck.Card(Deck.Card.RANK.KING, Deck.Card.SUIT.SPADES),
                        new Deck.Card(Deck.Card.RANK.QUEEN, Deck.Card.SUIT.HEARTS)));
        assertEquals(baseTwoPair, baseTwoPairHand);

        // Lower Two Pair hand with pairs of Kings and Queens, and a kicker Ace
        PokerHelpers.Hand lowTwoPairHand = pokerHelpers.findBestFiveCardHand(
                List.of(
                        new Deck.Card(Deck.Card.RANK.KING, Deck.Card.SUIT.HEARTS),
                        new Deck.Card(Deck.Card.RANK.KING, Deck.Card.SUIT.CLUBS)),
                List.of(
                        new Deck.Card(Deck.Card.RANK.QUEEN, Deck.Card.SUIT.DIAMONDS),
                        new Deck.Card(Deck.Card.RANK.QUEEN, Deck.Card.SUIT.HEARTS),
                        new Deck.Card(Deck.Card.RANK.ACE, Deck.Card.SUIT.SPADES)
                )
        );

        // Compare lower to base (expect less than 0)
        assertTrue(lowTwoPairHand.compareTo(baseTwoPairHand) < 0);

        // Alternative Two Pair with pairs of Aces and Kings, and a kicker Jack (lower kicker than base)
        PokerHelpers.Hand altTwoPairHand = pokerHelpers.findBestFiveCardHand(
                List.of(
                        new Deck.Card(Deck.Card.RANK.ACE, Deck.Card.SUIT.HEARTS),
                        new Deck.Card(Deck.Card.RANK.ACE, Deck.Card.SUIT.DIAMONDS)),
                List.of(
                        new Deck.Card(Deck.Card.RANK.KING, Deck.Card.SUIT.CLUBS),
                        new Deck.Card(Deck.Card.RANK.KING, Deck.Card.SUIT.SPADES),
                        new Deck.Card(Deck.Card.RANK.JACK, Deck.Card.SUIT.HEARTS)
                )
        );

        // Compare alternative to base (expect less than 0)
        assertTrue(altTwoPairHand.compareTo(baseTwoPairHand) < 0);

        // Compare base to alternative (expect greater than 0)
        assertTrue(baseTwoPairHand.compareTo(altTwoPairHand) > 0);
    }

    @Test
    public void findBestPairTest() throws RequestFailedException {
        PokerHelpers.Hand basePairHand = pokerHelpers.findBestFiveCardHand(
                List.of(
                        new Deck.Card(Deck.Card.RANK.ACE, Deck.Card.SUIT.HEARTS),
                        new Deck.Card(Deck.Card.RANK.ACE, Deck.Card.SUIT.CLUBS)),
                List.of(
                        new Deck.Card(Deck.Card.RANK.KING, Deck.Card.SUIT.DIAMONDS),
                        new Deck.Card(Deck.Card.RANK.QUEEN, Deck.Card.SUIT.SPADES),
                        new Deck.Card(Deck.Card.RANK.JACK, Deck.Card.SUIT.HEARTS)));
        assertEquals(basePair, basePairHand);

        // Lower Pair hand with a pair of Kings and kickers Ace, Queen, and Jack
        PokerHelpers.Hand lowPairHand = pokerHelpers.findBestFiveCardHand(
                List.of(
                        new Deck.Card(Deck.Card.RANK.KING, Deck.Card.SUIT.HEARTS),
                        new Deck.Card(Deck.Card.RANK.KING, Deck.Card.SUIT.CLUBS)),
                List.of(
                        new Deck.Card(Deck.Card.RANK.ACE, Deck.Card.SUIT.DIAMONDS),
                        new Deck.Card(Deck.Card.RANK.QUEEN, Deck.Card.SUIT.HEARTS),
                        new Deck.Card(Deck.Card.RANK.JACK, Deck.Card.SUIT.SPADES)
                )
        );

        // Compare lower to base (expect less than 0)
        assertTrue(lowPairHand.compareTo(basePairHand) < 0);

        // Alternative Pair with a pair of Aces and kickers King, Queen, and Ten (lower kicker than base)
        PokerHelpers.Hand altPairHand = pokerHelpers.findBestFiveCardHand(
                List.of(
                        new Deck.Card(Deck.Card.RANK.ACE, Deck.Card.SUIT.HEARTS),
                        new Deck.Card(Deck.Card.RANK.ACE, Deck.Card.SUIT.DIAMONDS)),
                List.of(
                        new Deck.Card(Deck.Card.RANK.KING, Deck.Card.SUIT.CLUBS),
                        new Deck.Card(Deck.Card.RANK.QUEEN, Deck.Card.SUIT.SPADES),
                        new Deck.Card(Deck.Card.RANK.TEN, Deck.Card.SUIT.HEARTS)
                )
        );

        // Compare alternative to base (expect less than 0)
        assertTrue(altPairHand.compareTo(basePairHand) < 0);

        // Compare base to alternative (expect greater than 0)
        assertTrue(basePairHand.compareTo(altPairHand) > 0);

        // Pair Sevens
    }

    @Test
    public void sixesVsSevens() throws RequestFailedException {
        // Pair sixes with kickers Ace, Queen, and Jack
        PokerHelpers.Hand sixes = pokerHelpers.findBestFiveCardHand(
                List.of(
                        new Deck.Card(Deck.Card.RANK.SIX, Deck.Card.SUIT.HEARTS),
                        new Deck.Card(Deck.Card.RANK.SIX, Deck.Card.SUIT.CLUBS)),
                List.of(
                        new Deck.Card(Deck.Card.RANK.ACE, Deck.Card.SUIT.DIAMONDS),
                        new Deck.Card(Deck.Card.RANK.QUEEN, Deck.Card.SUIT.HEARTS),
                        new Deck.Card(Deck.Card.RANK.JACK, Deck.Card.SUIT.SPADES)
                )
        );

        // Pair Sevens
        PokerHelpers.Hand sevens = pokerHelpers.findBestFiveCardHand(
                List.of(
                        new Deck.Card(Deck.Card.RANK.SEVEN, Deck.Card.SUIT.HEARTS),
                        new Deck.Card(Deck.Card.RANK.SEVEN, Deck.Card.SUIT.CLUBS)),
                List.of(
                        new Deck.Card(Deck.Card.RANK.ACE, Deck.Card.SUIT.DIAMONDS),
                        new Deck.Card(Deck.Card.RANK.QUEEN, Deck.Card.SUIT.HEARTS),
                        new Deck.Card(Deck.Card.RANK.JACK, Deck.Card.SUIT.SPADES)
                )
        );

        // Compare lower to base (expect less than 0)
        assertTrue(sixes.compareTo(sevens) < 0);
    }

    @Test
    public void findBestHighCardTest() throws RequestFailedException {
        PokerHelpers.Hand baseHighCardHand = pokerHelpers.findBestFiveCardHand(
                List.of(
                        new Deck.Card(Deck.Card.RANK.ACE, Deck.Card.SUIT.HEARTS),
                        new Deck.Card(Deck.Card.RANK.QUEEN, Deck.Card.SUIT.CLUBS)),
                List.of(
                        new Deck.Card(Deck.Card.RANK.JACK, Deck.Card.SUIT.DIAMONDS),
                        new Deck.Card(Deck.Card.RANK.TEN, Deck.Card.SUIT.SPADES),
                        new Deck.Card(Deck.Card.RANK.NINE, Deck.Card.SUIT.HEARTS)));
        assertEquals(baseHighCard, baseHighCardHand);

        // Lower High Card hand with King, Queen, Jack, Ten, and Eight
        PokerHelpers.Hand lowHighCardHand = pokerHelpers.findBestFiveCardHand(
                List.of(
                        new Deck.Card(Deck.Card.RANK.KING, Deck.Card.SUIT.HEARTS),
                        new Deck.Card(Deck.Card.RANK.QUEEN, Deck.Card.SUIT.CLUBS)),
                List.of(
                        new Deck.Card(Deck.Card.RANK.JACK, Deck.Card.SUIT.DIAMONDS),
                        new Deck.Card(Deck.Card.RANK.TEN, Deck.Card.SUIT.SPADES),
                        new Deck.Card(Deck.Card.RANK.EIGHT, Deck.Card.SUIT.HEARTS)
                )
        );

        // Compare lower to base (expect less than 0)
        assertTrue(lowHighCardHand.compareTo(baseHighCardHand) < 0);

        // Higher High Card hand with Ace, King, Queen, Jack, and Ten
        PokerHelpers.Hand highHighCardHand = pokerHelpers.findBestFiveCardHand(
                List.of(
                        new Deck.Card(Deck.Card.RANK.ACE, Deck.Card.SUIT.DIAMONDS),
                        new Deck.Card(Deck.Card.RANK.KING, Deck.Card.SUIT.HEARTS)),
                List.of(
                        new Deck.Card(Deck.Card.RANK.QUEEN, Deck.Card.SUIT.CLUBS),
                        new Deck.Card(Deck.Card.RANK.JACK, Deck.Card.SUIT.SPADES),
                        new Deck.Card(Deck.Card.RANK.TEN, Deck.Card.SUIT.HEARTS)
                )
        );

        // Compare higher to base (expect greater than 0)
        assertTrue(highHighCardHand.compareTo(baseHighCardHand) > 0);

        // Compare base to higher (expect less than 0)
        assertTrue(baseHighCardHand.compareTo(highHighCardHand) < 0);
    }

    @Test
    public void sixSevenStraight() throws RequestFailedException {
        // 2-6 Straight
        PokerHelpers.Hand twoThroughSix = pokerHelpers.findBestFiveCardHand(
                List.of(
                        new Deck.Card(Deck.Card.RANK.TWO, Deck.Card.SUIT.HEARTS),
                        new Deck.Card(Deck.Card.RANK.THREE, Deck.Card.SUIT.CLUBS)),
                List.of(
                        new Deck.Card(Deck.Card.RANK.FOUR, Deck.Card.SUIT.DIAMONDS),
                        new Deck.Card(Deck.Card.RANK.FIVE, Deck.Card.SUIT.SPADES),
                        new Deck.Card(Deck.Card.RANK.SIX, Deck.Card.SUIT.HEARTS)
                )
        );

        System.out.println(twoThroughSix);

        // 3-7 Straight
        PokerHelpers.Hand threeThroughSeven = pokerHelpers.findBestFiveCardHand(
                List.of(
                        new Deck.Card(Deck.Card.RANK.THREE, Deck.Card.SUIT.HEARTS),
                        new Deck.Card(Deck.Card.RANK.FOUR, Deck.Card.SUIT.CLUBS)),
                List.of(
                        new Deck.Card(Deck.Card.RANK.FIVE, Deck.Card.SUIT.DIAMONDS),
                        new Deck.Card(Deck.Card.RANK.SIX, Deck.Card.SUIT.SPADES),
                        new Deck.Card(Deck.Card.RANK.SEVEN, Deck.Card.SUIT.HEARTS)
                )
        );

        // Compare lower to base (expect less than 0)
        assertTrue(twoThroughSix.compareTo(threeThroughSeven) < 0);

    }
}