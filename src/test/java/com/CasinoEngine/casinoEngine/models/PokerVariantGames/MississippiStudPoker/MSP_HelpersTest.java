package com.CasinoEngine.casinoEngine.models.PokerVariantGames.MississippiStudPoker;

import com.CasinoEngine.casinoEngine.models.AllHelpers.Deck;
import com.CasinoEngine.casinoEngine.models.AllHelpers.RequestFailedException;
import com.CasinoEngine.casinoEngine.models.PokerVariantGames.PokerHelpers.PokerHelpers;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

class MSP_HelpersTest {
    MSP_Helpers MSP_Helper = new MSP_Helpers();

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

    PokerHelpers.Hand baseHighHighPair =
            new PokerHelpers.Hand(PokerHelpers.HAND_TYPE.PAIR,
                    new ArrayList<>(List.of(Deck.Card.RANK.ACE)),
                    new ArrayList<>(List.of(Deck.Card.RANK.KING, Deck.Card.RANK.QUEEN, Deck.Card.RANK.JACK))
            );

    PokerHelpers.Hand baseLowHighPair =
            new PokerHelpers.Hand(PokerHelpers.HAND_TYPE.PAIR,
                    new ArrayList<>(List.of(Deck.Card.RANK.JACK)),
                    new ArrayList<>(List.of(Deck.Card.RANK.KING, Deck.Card.RANK.QUEEN, Deck.Card.RANK.JACK))
            );

    PokerHelpers.Hand baseHighMidPair =
            new PokerHelpers.Hand(PokerHelpers.HAND_TYPE.PAIR,
                    new ArrayList<>(List.of(Deck.Card.RANK.TEN)),
                    new ArrayList<>(List.of(Deck.Card.RANK.KING, Deck.Card.RANK.QUEEN, Deck.Card.RANK.JACK))
            );

    PokerHelpers.Hand baseLowMidPair =
            new PokerHelpers.Hand(PokerHelpers.HAND_TYPE.PAIR,
                    new ArrayList<>(List.of(Deck.Card.RANK.SIX)),
                    new ArrayList<>(List.of(Deck.Card.RANK.KING, Deck.Card.RANK.QUEEN, Deck.Card.RANK.JACK))
            );

    PokerHelpers.Hand baseHighLowPair =
            new PokerHelpers.Hand(PokerHelpers.HAND_TYPE.PAIR,
                    new ArrayList<>(List.of(Deck.Card.RANK.FIVE)),
                    new ArrayList<>(List.of(Deck.Card.RANK.KING, Deck.Card.RANK.QUEEN, Deck.Card.RANK.JACK))
            );

    PokerHelpers.Hand baseLowLowPair =
            new PokerHelpers.Hand(PokerHelpers.HAND_TYPE.PAIR,
                    new ArrayList<>(List.of(Deck.Card.RANK.TWO)),
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
    public void testDeterminePayout() {
        assert (MSP_Helper.determinePayout(baseRoyalFlush, 1) == 501);
        assert (MSP_Helper.determinePayout(baseStraightFlush, 1) == 101);
        assert (MSP_Helper.determinePayout(baseFourOfAKind, 1) == 41);
        assert (MSP_Helper.determinePayout(baseFullHouse, 1) == 11);
        assert (MSP_Helper.determinePayout(baseFlush, 1) == 7);
        assert (MSP_Helper.determinePayout(baseStraight, 1) == 5);
        assert (MSP_Helper.determinePayout(baseThreeOfAKind, 1) == 4);
        assert (MSP_Helper.determinePayout(baseTwoPair, 1) == 3);
        assert (MSP_Helper.determinePayout(baseHighHighPair, 1) == 2);
        assert (MSP_Helper.determinePayout(baseLowHighPair, 1) == 2);
        assert (MSP_Helper.determinePayout(baseHighMidPair, 1) == 1);
        assert (MSP_Helper.determinePayout(baseLowMidPair, 1) == 1);
        assert (MSP_Helper.determinePayout(baseHighLowPair, 1) == 0);
        assert (MSP_Helper.determinePayout(baseLowLowPair, 1) == 0);
        assert (MSP_Helper.determinePayout(baseHighCard, 1) == 0);

        assert (MSP_Helper.determinePayout(baseRoyalFlush, 200) == 501 * 200);
        assert (MSP_Helper.determinePayout(baseStraightFlush, 200) == 101 * 200);
        assert (MSP_Helper.determinePayout(baseFourOfAKind, 200) == 41 * 200);
        assert (MSP_Helper.determinePayout(baseFullHouse, 200) == 11 * 200);
        assert (MSP_Helper.determinePayout(baseFlush, 200) == 7 * 200);
        assert (MSP_Helper.determinePayout(baseStraight, 200) == 5 * 200);
        assert (MSP_Helper.determinePayout(baseThreeOfAKind, 200) == 4 * 200);
        assert (MSP_Helper.determinePayout(baseTwoPair, 200) == 3 * 200);
        assert (MSP_Helper.determinePayout(baseHighHighPair, 200) == 2 * 200);
        assert (MSP_Helper.determinePayout(baseLowHighPair, 200) == 2 * 200);
        assert (MSP_Helper.determinePayout(baseHighMidPair, 200) == 200);
        assert (MSP_Helper.determinePayout(baseLowMidPair, 200) == 200);
        assert (MSP_Helper.determinePayout(baseHighLowPair, 200) == 0);
        assert (MSP_Helper.determinePayout(baseLowLowPair, 200) == 0);
        assert (MSP_Helper.determinePayout(baseHighCard, 200) == 0);
    }

    @Test
    public void testSolveFirstStreet() throws RequestFailedException {
        // Pair of High
        Deck deck = new Deck();
        List<Deck.Card> cards = new ArrayList<>(List.of(
                new Deck.Card(Deck.Card.RANK.ACE, Deck.Card.SUIT.HEARTS),
                new Deck.Card(Deck.Card.RANK.ACE, Deck.Card.SUIT.DIAMONDS)
        ));
        deck.removeCards(cards);
        assertEquals(3, MSP_Helper.solveHand(cards, 1, 1));

        // Pair of mid
        deck = new Deck();
        cards = new ArrayList<>(List.of(
                new Deck.Card(Deck.Card.RANK.TEN, Deck.Card.SUIT.HEARTS),
                new Deck.Card(Deck.Card.RANK.TEN, Deck.Card.SUIT.DIAMONDS)
        ));
        deck.removeCards(cards);
        assertEquals(3, MSP_Helper.solveHand(cards, 1, 1));

        // Pair of low
        deck = new Deck();
        cards = new ArrayList<>(List.of(
                new Deck.Card(Deck.Card.RANK.TWO, Deck.Card.SUIT.HEARTS),
                new Deck.Card(Deck.Card.RANK.TWO, Deck.Card.SUIT.DIAMONDS)
        ));
        deck.removeCards(cards);
        assertEquals(3, MSP_Helper.solveHand(cards, 1, 1));

        // High Cards
        deck = new Deck();
        cards = new ArrayList<>(List.of(
                new Deck.Card(Deck.Card.RANK.ACE, Deck.Card.SUIT.HEARTS),
                new Deck.Card(Deck.Card.RANK.KING, Deck.Card.SUIT.DIAMONDS)
        ));
        deck.removeCards(cards);
        assertEquals(1, MSP_Helper.solveHand(cards, 1, 1));

        // Low Cards
        deck = new Deck();
        cards = new ArrayList<>(List.of(
                new Deck.Card(Deck.Card.RANK.TWO, Deck.Card.SUIT.HEARTS),
                new Deck.Card(Deck.Card.RANK.FIVE, Deck.Card.SUIT.DIAMONDS)
        ));
        deck.removeCards(cards);
        assertEquals(0, MSP_Helper.solveHand(cards, 1, 1));

        // 45 Suited
        deck = new Deck();
        cards = new ArrayList<>(List.of(
                new Deck.Card(Deck.Card.RANK.FOUR, Deck.Card.SUIT.HEARTS),
                new Deck.Card(Deck.Card.RANK.FIVE, Deck.Card.SUIT.HEARTS)
        ));
        deck.removeCards(cards);
        assertEquals(0, MSP_Helper.solveHand(cards, 1, 1));

    }

    @Test
    public void testSolveFirstStreetWeird() throws RequestFailedException {
        // --------- Testing Interesting Results ---------
        // Eight of Diamonds and Three of Diamonds
        Deck deck = new Deck();
        List<Deck.Card> cards = new ArrayList<>(List.of(
                new Deck.Card(Deck.Card.RANK.EIGHT, Deck.Card.SUIT.DIAMONDS),
                new Deck.Card(Deck.Card.RANK.THREE, Deck.Card.SUIT.DIAMONDS)
        ));
        deck.removeCards(cards);
         assertEquals(0, MSP_Helper.solveHand(cards, 1, 1));

        // Two Seven Off
        // Fixed
        deck = new Deck();
        cards = new ArrayList<>(List.of(
                new Deck.Card(Deck.Card.RANK.TWO, Deck.Card.SUIT.HEARTS),
                new Deck.Card(Deck.Card.RANK.SEVEN, Deck.Card.SUIT.SPADES)
        ));
        deck.removeCards(cards);
        assertEquals(0, MSP_Helper.solveHand(cards, 1, 1));

    }

    @Test
    public void testSolveSecondStreet() throws RequestFailedException {
        // Royal Flush Draw
        Deck deck = new Deck();
        List<Deck.Card> cards = new ArrayList<>(List.of(
                new Deck.Card(Deck.Card.RANK.KING, Deck.Card.SUIT.HEARTS),
                new Deck.Card(Deck.Card.RANK.QUEEN, Deck.Card.SUIT.HEARTS),
                new Deck.Card(Deck.Card.RANK.JACK, Deck.Card.SUIT.HEARTS)
        ));
        deck.removeCards(cards);
        assertEquals(3, MSP_Helper.solveHand(cards, 1, 2));

        // Straight Flush Draw 567
        deck = new Deck();
        cards = new ArrayList<>(List.of(
                new Deck.Card(Deck.Card.RANK.FIVE, Deck.Card.SUIT.HEARTS),
                new Deck.Card(Deck.Card.RANK.SIX, Deck.Card.SUIT.HEARTS),
                new Deck.Card(Deck.Card.RANK.SEVEN, Deck.Card.SUIT.HEARTS)
        ));
        deck.removeCards(cards);
         assertEquals(3, MSP_Helper.solveHand(cards, 1, 2));

        // Straight Flush Draw 678
        deck = new Deck();
        cards = new ArrayList<>(List.of(
                new Deck.Card(Deck.Card.RANK.EIGHT, Deck.Card.SUIT.HEARTS),
                new Deck.Card(Deck.Card.RANK.SIX, Deck.Card.SUIT.HEARTS),
                new Deck.Card(Deck.Card.RANK.SEVEN, Deck.Card.SUIT.HEARTS)
        ));
        deck.removeCards(cards);
        assertEquals(3, MSP_Helper.solveHand(cards, 1, 2));

        // Straight Flush Draw 456
        deck = new Deck();
        cards = new ArrayList<>(List.of(
                new Deck.Card(Deck.Card.RANK.FOUR, Deck.Card.SUIT.HEARTS),
                new Deck.Card(Deck.Card.RANK.FIVE, Deck.Card.SUIT.HEARTS),
                new Deck.Card(Deck.Card.RANK.SIX, Deck.Card.SUIT.HEARTS)
        ));
        deck.removeCards(cards);
        assertEquals(1, MSP_Helper.solveHand(cards, 1, 2));

        // Straight Flush Draw one gap one high
        deck = new Deck();
        cards = new ArrayList<>(List.of(
                new Deck.Card(Deck.Card.RANK.EIGHT, Deck.Card.SUIT.HEARTS),
                new Deck.Card(Deck.Card.RANK.NINE, Deck.Card.SUIT.HEARTS),
                new Deck.Card(Deck.Card.RANK.JACK, Deck.Card.SUIT.HEARTS)
        ));
        deck.removeCards(cards);
        assertEquals(3, MSP_Helper.solveHand(cards, 1, 2));

        // Straight Flush Draw one gap no high
        deck = new Deck();
        cards = new ArrayList<>(List.of(
                new Deck.Card(Deck.Card.RANK.SEVEN, Deck.Card.SUIT.HEARTS),
                new Deck.Card(Deck.Card.RANK.NINE, Deck.Card.SUIT.HEARTS),
                new Deck.Card(Deck.Card.RANK.TEN, Deck.Card.SUIT.HEARTS)
        ));
        deck.removeCards(cards);
        assertEquals(1, MSP_Helper.solveHand(cards, 1, 2));

        // Straight Flush Draw two gap two high
        deck = new Deck();
        cards = new ArrayList<>(List.of(
                new Deck.Card(Deck.Card.RANK.NINE, Deck.Card.SUIT.HEARTS),
                new Deck.Card(Deck.Card.RANK.JACK, Deck.Card.SUIT.HEARTS),
                new Deck.Card(Deck.Card.RANK.KING, Deck.Card.SUIT.HEARTS)
        ));
        deck.removeCards(cards);
        assertEquals(3, MSP_Helper.solveHand(cards, 1, 2));

        // Straight Flush Draw two gap one high
        deck = new Deck();
        cards = new ArrayList<>(List.of(
                new Deck.Card(Deck.Card.RANK.NINE, Deck.Card.SUIT.HEARTS),
                new Deck.Card(Deck.Card.RANK.JACK, Deck.Card.SUIT.HEARTS),
                new Deck.Card(Deck.Card.RANK.SEVEN, Deck.Card.SUIT.HEARTS)
        ));
        deck.removeCards(cards);
         assertEquals(1, MSP_Helper.solveHand(cards, 1, 2));

        // Flush Draw
        deck = new Deck();
        cards = new ArrayList<>(List.of(
                new Deck.Card(Deck.Card.RANK.ACE, Deck.Card.SUIT.HEARTS),
                new Deck.Card(Deck.Card.RANK.TEN, Deck.Card.SUIT.HEARTS),
                new Deck.Card(Deck.Card.RANK.TWO, Deck.Card.SUIT.HEARTS)
        ));
        deck.removeCards(cards);
        assertEquals(1, MSP_Helper.solveHand(cards, 1, 2));

        // Low Pair
        deck = new Deck();
        cards = new ArrayList<>(List.of(
                new Deck.Card(Deck.Card.RANK.TWO, Deck.Card.SUIT.HEARTS),
                new Deck.Card(Deck.Card.RANK.TWO, Deck.Card.SUIT.DIAMONDS),
                new Deck.Card(Deck.Card.RANK.THREE, Deck.Card.SUIT.SPADES)
        ));
        deck.removeCards(cards);
        assertEquals(1, MSP_Helper.solveHand(cards, 1, 5));

        // Three points
        deck = new Deck();
        cards = new ArrayList<>(List.of(
                new Deck.Card(Deck.Card.RANK.ACE, Deck.Card.SUIT.HEARTS),
                new Deck.Card(Deck.Card.RANK.TEN, Deck.Card.SUIT.DIAMONDS),
                new Deck.Card(Deck.Card.RANK.TWO, Deck.Card.SUIT.SPADES)
        ));
        deck.removeCards(cards);
        assertEquals(1, MSP_Helper.solveHand(cards, 1, 2));

        // Straight Draw Open But Low
        deck = new Deck();
        cards = new ArrayList<>(List.of(
                new Deck.Card(Deck.Card.RANK.THREE, Deck.Card.SUIT.HEARTS),
                new Deck.Card(Deck.Card.RANK.FIVE, Deck.Card.SUIT.DIAMONDS),
                new Deck.Card(Deck.Card.RANK.FOUR, Deck.Card.SUIT.SPADES)
        ));
        deck.removeCards(cards);
        assertEquals(0, MSP_Helper.solveHand(cards, 1, 2));

        // Straight Draw Inside Two Mid
        deck = new Deck();
        cards = new ArrayList<>(List.of(
                new Deck.Card(Deck.Card.RANK.FIVE, Deck.Card.SUIT.HEARTS),
                new Deck.Card(Deck.Card.RANK.SIX, Deck.Card.SUIT.DIAMONDS),
                new Deck.Card(Deck.Card.RANK.EIGHT, Deck.Card.SUIT.SPADES)
        ));
        deck.removeCards(cards);
        assertEquals(1, MSP_Helper.solveHand(cards, 1, 2));

        // Straight Draw Inside One Mid
        deck = new Deck();
        cards = new ArrayList<>(List.of(
                new Deck.Card(Deck.Card.RANK.FIVE, Deck.Card.SUIT.HEARTS),
                new Deck.Card(Deck.Card.RANK.SEVEN, Deck.Card.SUIT.DIAMONDS),
                new Deck.Card(Deck.Card.RANK.FOUR, Deck.Card.SUIT.SPADES)
        ));
        deck.removeCards(cards);
         assertEquals(0, MSP_Helper.solveHand(cards, 1, 2));

        // Two Points
        deck = new Deck();
        cards = new ArrayList<>(List.of(
                new Deck.Card(Deck.Card.RANK.TWO, Deck.Card.SUIT.HEARTS),
                new Deck.Card(Deck.Card.RANK.TEN, Deck.Card.SUIT.DIAMONDS),
                new Deck.Card(Deck.Card.RANK.SIX, Deck.Card.SUIT.SPADES)
        ));
        deck.removeCards(cards);
         assertEquals(0, MSP_Helper.solveHand(cards, 1, 2));

        // Made hands...
        // Three of a kind
        deck = new Deck();
        cards = new ArrayList<>(List.of(
                new Deck.Card(Deck.Card.RANK.ACE, Deck.Card.SUIT.HEARTS),
                new Deck.Card(Deck.Card.RANK.ACE, Deck.Card.SUIT.DIAMONDS),
                new Deck.Card(Deck.Card.RANK.ACE, Deck.Card.SUIT.SPADES)
        ));
        deck.removeCards(cards);
        assertEquals (3, MSP_Helper.solveHand(cards, 1, 5));
    }

    @Test
    public void testSolveSecondStreetWeird() throws RequestFailedException {
        // --------- Testing Interesting Results ---------
        // Queen of Spades and Four of Clubs and two of spades
        Deck deck = new Deck();
        List<Deck.Card> cards = new ArrayList<>(List.of(
                new Deck.Card(Deck.Card.RANK.QUEEN, Deck.Card.SUIT.SPADES),
                new Deck.Card(Deck.Card.RANK.FOUR, Deck.Card.SUIT.CLUBS),
                new Deck.Card(Deck.Card.RANK.TWO, Deck.Card.SUIT.SPADES)
        ));
        deck.removeCards(cards);
         assertEquals(0, MSP_Helper.solveHand(cards, 1, 2));

        // Two of Diamonds and Jack of Spades and three of clubs and king of clubs
        deck = new Deck();
        cards = new ArrayList<>(List.of(
                new Deck.Card(Deck.Card.RANK.TWO, Deck.Card.SUIT.DIAMONDS),
                new Deck.Card(Deck.Card.RANK.JACK, Deck.Card.SUIT.SPADES),
                new Deck.Card(Deck.Card.RANK.THREE, Deck.Card.SUIT.CLUBS)
        ));
        deck.removeCards(cards);
         assertEquals(0, MSP_Helper.solveHand(cards, 1, 2));
    }

    @Test
    public void testSolveThirdStreet() throws RequestFailedException {
        // Royal Flush Draw
        Deck deck = new Deck();
        List<Deck.Card> cards = new ArrayList<>(List.of(
                new Deck.Card(Deck.Card.RANK.KING, Deck.Card.SUIT.HEARTS),
                new Deck.Card(Deck.Card.RANK.QUEEN, Deck.Card.SUIT.HEARTS),
                new Deck.Card(Deck.Card.RANK.JACK, Deck.Card.SUIT.HEARTS),
                new Deck.Card(Deck.Card.RANK.TEN, Deck.Card.SUIT.HEARTS)
        ));
        deck.removeCards(cards);
        assertEquals (3, MSP_Helper.solveHand(cards, 1, 5));

        // Straight Flush Draw
        deck = new Deck();
        cards = new ArrayList<>(List.of(
                new Deck.Card(Deck.Card.RANK.JACK, Deck.Card.SUIT.HEARTS),
                new Deck.Card(Deck.Card.RANK.TEN, Deck.Card.SUIT.HEARTS),
                new Deck.Card(Deck.Card.RANK.NINE, Deck.Card.SUIT.HEARTS),
                new Deck.Card(Deck.Card.RANK.EIGHT, Deck.Card.SUIT.HEARTS)
        ));
        deck.removeCards(cards);
        assertEquals (3, MSP_Helper.solveHand(cards, 1, 5));

        // Flush Draw
        deck = new Deck();
        cards = new ArrayList<>(List.of(
                new Deck.Card(Deck.Card.RANK.SIX, Deck.Card.SUIT.HEARTS),
                new Deck.Card(Deck.Card.RANK.FIVE, Deck.Card.SUIT.HEARTS),
                new Deck.Card(Deck.Card.RANK.FOUR, Deck.Card.SUIT.HEARTS),
                new Deck.Card(Deck.Card.RANK.TWO, Deck.Card.SUIT.HEARTS)
        ));
        deck.removeCards(cards);
        assertEquals (3, MSP_Helper.solveHand(cards, 1, 5));

        // Straight Draw Open
        deck = new Deck();
        cards = new ArrayList<>(List.of(
                new Deck.Card(Deck.Card.RANK.EIGHT, Deck.Card.SUIT.HEARTS),
                new Deck.Card(Deck.Card.RANK.SEVEN, Deck.Card.SUIT.DIAMONDS),
                new Deck.Card(Deck.Card.RANK.SIX, Deck.Card.SUIT.SPADES),
                new Deck.Card(Deck.Card.RANK.FIVE, Deck.Card.SUIT.CLUBS)
        ));
        deck.removeCards(cards);
        assertEquals(3, MSP_Helper.solveHand(cards, 1, 5));

        // Straight Draw Inside
        deck = new Deck();
        cards = new ArrayList<>(List.of(
                new Deck.Card(Deck.Card.RANK.EIGHT, Deck.Card.SUIT.HEARTS),
                new Deck.Card(Deck.Card.RANK.SEVEN, Deck.Card.SUIT.DIAMONDS),
                new Deck.Card(Deck.Card.RANK.FIVE, Deck.Card.SUIT.SPADES),
                new Deck.Card(Deck.Card.RANK.NINE, Deck.Card.SUIT.CLUBS)
        ));
        deck.removeCards(cards);
        assertEquals(1, MSP_Helper.solveHand(cards, 1, 5));

        // Low Pair
        deck = new Deck();
        cards = new ArrayList<>(List.of(
                new Deck.Card(Deck.Card.RANK.TWO, Deck.Card.SUIT.HEARTS),
                new Deck.Card(Deck.Card.RANK.TWO, Deck.Card.SUIT.DIAMONDS),
                new Deck.Card(Deck.Card.RANK.THREE, Deck.Card.SUIT.SPADES),
                new Deck.Card(Deck.Card.RANK.FOUR, Deck.Card.SUIT.CLUBS)
        ));
        deck.removeCards(cards);
        assertEquals(1, MSP_Helper.solveHand(cards, 1, 5));

        // Four points
        deck = new Deck();
        cards = new ArrayList<>(List.of(
                new Deck.Card(Deck.Card.RANK.ACE, Deck.Card.SUIT.HEARTS),
                new Deck.Card(Deck.Card.RANK.KING, Deck.Card.SUIT.DIAMONDS),
                new Deck.Card(Deck.Card.RANK.TWO, Deck.Card.SUIT.SPADES),
                new Deck.Card(Deck.Card.RANK.FIVE, Deck.Card.SUIT.CLUBS)
        ));
        deck.removeCards(cards);
        // Or zero if implementing reduced variance start
        assertEquals(1, MSP_Helper.solveHand(cards, 1, 5));

        // Previous Over Bet
        deck = new Deck();
        cards = new ArrayList<>(List.of(
                new Deck.Card(Deck.Card.RANK.TEN, Deck.Card.SUIT.HEARTS),
                new Deck.Card(Deck.Card.RANK.EIGHT, Deck.Card.SUIT.DIAMONDS),
                new Deck.Card(Deck.Card.RANK.SIX, Deck.Card.SUIT.SPADES),
                new Deck.Card(Deck.Card.RANK.TWO, Deck.Card.SUIT.CLUBS)
        ));
        deck.removeCards(cards);
        assertEquals(1, MSP_Helper.solveHand(cards, 1, 5));

        // Three Points with 3x raise
        deck = new Deck();
        cards = new ArrayList<>(List.of(
                new Deck.Card(Deck.Card.RANK.ACE, Deck.Card.SUIT.HEARTS),
                new Deck.Card(Deck.Card.RANK.THREE, Deck.Card.SUIT.DIAMONDS),
                new Deck.Card(Deck.Card.RANK.TWO, Deck.Card.SUIT.SPADES),
                new Deck.Card(Deck.Card.RANK.SIX, Deck.Card.SUIT.CLUBS)
        ));
        deck.removeCards(cards);
        assertEquals(1, MSP_Helper.solveHand(cards, 1, 5));

        // Three Points
        deck = new Deck();
        cards = new ArrayList<>(List.of(
                new Deck.Card(Deck.Card.RANK.ACE, Deck.Card.SUIT.HEARTS),
                new Deck.Card(Deck.Card.RANK.THREE, Deck.Card.SUIT.DIAMONDS),
                new Deck.Card(Deck.Card.RANK.TWO, Deck.Card.SUIT.SPADES),
                new Deck.Card(Deck.Card.RANK.SIX, Deck.Card.SUIT.CLUBS)
        ));
        deck.removeCards(cards);
        assertEquals(0, MSP_Helper.solveHand(cards, 1, 3));

        // Made hands...
        // Four of a kind
        deck = new Deck();
        cards = new ArrayList<>(List.of(
                new Deck.Card(Deck.Card.RANK.ACE, Deck.Card.SUIT.HEARTS),
                new Deck.Card(Deck.Card.RANK.ACE, Deck.Card.SUIT.DIAMONDS),
                new Deck.Card(Deck.Card.RANK.ACE, Deck.Card.SUIT.SPADES),
                new Deck.Card(Deck.Card.RANK.ACE, Deck.Card.SUIT.CLUBS)
        ));
        deck.removeCards(cards);
        assertEquals (3, MSP_Helper.solveHand(cards, 1, 7));
    }
}