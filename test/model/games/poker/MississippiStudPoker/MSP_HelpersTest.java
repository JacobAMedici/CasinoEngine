

package model.games.poker.MississippiStudPoker;


import model.cards.Card;
import model.cards.Deck;
import model.cards.SuitedCard;
import model.cards.SuitedDeck;
import model.games.poker.PokerHelpers.PokerHand;
import model.games.poker.mississippiStudPoker.MSP_Helpers;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

class MSP_HelpersTest {

    MSP_Helpers MSP_Helper = new MSP_Helpers();

    PokerHand baseRoyalFlush =
            new PokerHand(PokerHand.HAND_TYPE.ROYAL_FLUSH,
                    new ArrayList<>(List.of(Card.RANK.ACE)),
                    new ArrayList<>(0)
            );

    PokerHand baseStraightFlush =
            new PokerHand(PokerHand.HAND_TYPE.STRAIGHT_FLUSH,
                    new ArrayList<>(List.of(Card.RANK.KING)),
                    new ArrayList<>(0)
            );

    PokerHand baseFourOfAKind =
            new PokerHand(PokerHand.HAND_TYPE.FOUR_OF_A_KIND,
                    new ArrayList<>(List.of(Card.RANK.ACE)),
                    new ArrayList<>(List.of(Card.RANK.TWO))
            );

    PokerHand baseFullHouse =
            new PokerHand(PokerHand.HAND_TYPE.FULL_HOUSE,
                    new ArrayList<>(List.of(Card.RANK.ACE)),
                    new ArrayList<>(List.of(Card.RANK.KING))
            );

    PokerHand baseFlush =
            new PokerHand(PokerHand.HAND_TYPE.FLUSH,
                    new ArrayList<>(List.of(Card.RANK.ACE,
                            Card.RANK.QUEEN,
                            Card.RANK.JACK,
                            Card.RANK.TEN,
                            Card.RANK.NINE)),
                    new ArrayList<>(0)
            );

    PokerHand baseStraight =
            new PokerHand(PokerHand.HAND_TYPE.STRAIGHT,
                    new ArrayList<>(List.of(Card.RANK.ACE)),
                    new ArrayList<>(0)
            );

    PokerHand baseThreeOfAKind =
            new PokerHand(PokerHand.HAND_TYPE.THREE_OF_A_KIND,
                    new ArrayList<>(List.of(Card.RANK.ACE)),
                    new ArrayList<>(List.of(Card.RANK.KING, Card.RANK.QUEEN))
            );

    PokerHand baseTwoPair =
            new PokerHand(PokerHand.HAND_TYPE.TWO_PAIR,
                    new ArrayList<>(List.of(Card.RANK.ACE, Card.RANK.KING)),
                    new ArrayList<>(List.of(Card.RANK.QUEEN))
            );

    PokerHand baseHighHighPair =
            new PokerHand(PokerHand.HAND_TYPE.PAIR,
                    new ArrayList<>(List.of(Card.RANK.ACE)),
                    new ArrayList<>(List.of(Card.RANK.KING, Card.RANK.QUEEN, Card.RANK.JACK))
            );

    PokerHand baseLowHighPair =
            new PokerHand(PokerHand.HAND_TYPE.PAIR,
                    new ArrayList<>(List.of(Card.RANK.JACK)),
                    new ArrayList<>(List.of(Card.RANK.KING, Card.RANK.QUEEN, Card.RANK.JACK))
            );

    PokerHand baseHighMidPair =
            new PokerHand(PokerHand.HAND_TYPE.PAIR,
                    new ArrayList<>(List.of(Card.RANK.TEN)),
                    new ArrayList<>(List.of(Card.RANK.KING, Card.RANK.QUEEN, Card.RANK.JACK))
            );

    PokerHand baseLowMidPair =
            new PokerHand(PokerHand.HAND_TYPE.PAIR,
                    new ArrayList<>(List.of(Card.RANK.SIX)),
                    new ArrayList<>(List.of(Card.RANK.KING, Card.RANK.QUEEN, Card.RANK.JACK))
            );

    PokerHand baseHighLowPair =
            new PokerHand(PokerHand.HAND_TYPE.PAIR,
                    new ArrayList<>(List.of(Card.RANK.FIVE)),
                    new ArrayList<>(List.of(Card.RANK.KING, Card.RANK.QUEEN, Card.RANK.JACK))
            );

    PokerHand baseLowLowPair =
            new PokerHand(PokerHand.HAND_TYPE.PAIR,
                    new ArrayList<>(List.of(Card.RANK.TWO)),
                    new ArrayList<>(List.of(Card.RANK.KING, Card.RANK.QUEEN, Card.RANK.JACK))
            );

    PokerHand baseHighCard =
            new PokerHand(PokerHand.HAND_TYPE.HIGH_CARD,
                    new ArrayList<>(List.of(Card.RANK.ACE)),
                    new ArrayList<>(List.of(Card.RANK.QUEEN,
                            Card.RANK.JACK,
                            Card.RANK.TEN,
                            Card.RANK.NINE))
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
    public void testSolveFirstStreet() {
        SuitedDeck deck = new SuitedDeck();

        // Pair of High
        List<SuitedCard> cards = new ArrayList<>(List.of(
                new SuitedCard(Card.RANK.ACE, Card.SUIT.HEARTS),
                new SuitedCard(Card.RANK.ACE, Card.SUIT.DIAMONDS)
        ));
        deck.removeCards(cards);
        assertEquals(3, MSP_Helper.solveHand(cards, 1, 1));

        // Pair of mid
        deck = new Deck();
        cards = new ArrayList<>(List.of(
                new SuitedCard(Card.RANK.TEN, Card.SUIT.HEARTS),
                new SuitedCard(Card.RANK.TEN, Card.SUIT.DIAMONDS)
        ));
        deck.removeCards(cards);
        assertEquals(3, MSP_Helper.solveHand(cards, 1, 1));

        // Pair of low
        deck = new Deck();
        cards = new ArrayList<>(List.of(
                new SuitedCard(Card.RANK.TWO, Card.SUIT.HEARTS),
                new SuitedCard(Card.RANK.TWO, Card.SUIT.DIAMONDS)
        ));
        deck.removeCards(cards);
        assertEquals(3, MSP_Helper.solveHand(cards, 1, 1));

        // High Cards
        deck = new Deck();
        cards = new ArrayList<>(List.of(
                new SuitedCard(Card.RANK.ACE, Card.SUIT.HEARTS),
                new SuitedCard(Card.RANK.KING, Card.SUIT.DIAMONDS)
        ));
        deck.removeCards(cards);
        assertEquals(1, MSP_Helper.solveHand(cards, 1, 1));

        // Low Cards
        deck = new Deck();
        cards = new ArrayList<>(List.of(
                new SuitedCard(Card.RANK.TWO, Card.SUIT.HEARTS),
                new SuitedCard(Card.RANK.FIVE, Card.SUIT.DIAMONDS)
        ));
        deck.removeCards(cards);
        assertEquals(0, MSP_Helper.solveHand(cards, 1, 1));

        // 45 Suited
        deck = new Deck();
        cards = new ArrayList<>(List.of(
                new SuitedCard(Card.RANK.FOUR, Card.SUIT.HEARTS),
                new SuitedCard(Card.RANK.FIVE, Card.SUIT.HEARTS)
        ));
        deck.removeCards(cards);
        assertEquals(0, MSP_Helper.solveHand(cards, 1, 1));

    }

    @Test
    public void testSolveFirstStreetWeird() {
        // --------- Testing Interesting Results ---------
        // Eight of Diamonds and Three of Diamonds
        Deck deck = new Deck();
        List<SuitedCard> cards = new ArrayList<>(List.of(
                new SuitedCard(Card.RANK.EIGHT, Card.SUIT.DIAMONDS),
                new SuitedCard(Card.RANK.THREE, Card.SUIT.DIAMONDS)
        ));
        deck.removeCards(cards);
         assertEquals(0, MSP_Helper.solveHand(cards, 1, 1));

        // Two Seven Off
        // Fixed
        deck = new Deck();
        cards = new ArrayList<>(List.of(
                new SuitedCard(Card.RANK.TWO, Card.SUIT.HEARTS),
                new SuitedCard(Card.RANK.SEVEN, Card.SUIT.SPADES)
        ));
        deck.removeCards(cards);
        assertEquals(0, MSP_Helper.solveHand(cards, 1, 1));

    }

    @Test
    public void testSolveSecondStreet() {
        // Royal Flush Draw
        Deck deck = new Deck();
        List<SuitedCard> cards = new ArrayList<>(List.of(
                new SuitedCard(Card.RANK.KING, Card.SUIT.HEARTS),
                new SuitedCard(Card.RANK.QUEEN, Card.SUIT.HEARTS),
                new SuitedCard(Card.RANK.JACK, Card.SUIT.HEARTS)
        ));
        deck.removeCards(cards);
        assertEquals(3, MSP_Helper.solveHand(cards, 1, 2));

        // Straight Flush Draw 567
        deck = new Deck();
        cards = new ArrayList<>(List.of(
                new SuitedCard(Card.RANK.FIVE, Card.SUIT.HEARTS),
                new SuitedCard(Card.RANK.SIX, Card.SUIT.HEARTS),
                new SuitedCard(Card.RANK.SEVEN, Card.SUIT.HEARTS)
        ));
        deck.removeCards(cards);
         assertEquals(3, MSP_Helper.solveHand(cards, 1, 2));

        // Straight Flush Draw 678
        deck = new Deck();
        cards = new ArrayList<>(List.of(
                new SuitedCard(Card.RANK.EIGHT, Card.SUIT.HEARTS),
                new SuitedCard(Card.RANK.SIX, Card.SUIT.HEARTS),
                new SuitedCard(Card.RANK.SEVEN, Card.SUIT.HEARTS)
        ));
        deck.removeCards(cards);
        assertEquals(3, MSP_Helper.solveHand(cards, 1, 2));

        // Straight Flush Draw 456
        deck = new Deck();
        cards = new ArrayList<>(List.of(
                new SuitedCard(Card.RANK.FOUR, Card.SUIT.HEARTS),
                new SuitedCard(Card.RANK.FIVE, Card.SUIT.HEARTS),
                new SuitedCard(Card.RANK.SIX, Card.SUIT.HEARTS)
        ));
        deck.removeCards(cards);
        assertEquals(1, MSP_Helper.solveHand(cards, 1, 2));

        // Straight Flush Draw one gap one high
        deck = new Deck();
        cards = new ArrayList<>(List.of(
                new SuitedCard(Card.RANK.EIGHT, Card.SUIT.HEARTS),
                new SuitedCard(Card.RANK.NINE, Card.SUIT.HEARTS),
                new SuitedCard(Card.RANK.JACK, Card.SUIT.HEARTS)
        ));
        deck.removeCards(cards);
        assertEquals(3, MSP_Helper.solveHand(cards, 1, 2));

        // Straight Flush Draw one gap no high
        deck = new Deck();
        cards = new ArrayList<>(List.of(
                new SuitedCard(Card.RANK.SEVEN, Card.SUIT.HEARTS),
                new SuitedCard(Card.RANK.NINE, Card.SUIT.HEARTS),
                new SuitedCard(Card.RANK.TEN, Card.SUIT.HEARTS)
        ));
        deck.removeCards(cards);
        assertEquals(1, MSP_Helper.solveHand(cards, 1, 2));

        // Straight Flush Draw two gap two high
        deck = new Deck();
        cards = new ArrayList<>(List.of(
                new SuitedCard(Card.RANK.NINE, Card.SUIT.HEARTS),
                new SuitedCard(Card.RANK.JACK, Card.SUIT.HEARTS),
                new SuitedCard(Card.RANK.KING, Card.SUIT.HEARTS)
        ));
        deck.removeCards(cards);
        assertEquals(3, MSP_Helper.solveHand(cards, 1, 2));

        // Straight Flush Draw two gap one high
        deck = new Deck();
        cards = new ArrayList<>(List.of(
                new SuitedCard(Card.RANK.NINE, Card.SUIT.HEARTS),
                new SuitedCard(Card.RANK.JACK, Card.SUIT.HEARTS),
                new SuitedCard(Card.RANK.SEVEN, Card.SUIT.HEARTS)
        ));
        deck.removeCards(cards);
         assertEquals(1, MSP_Helper.solveHand(cards, 1, 2));

        // Straight Flush Draw two gap two mid
        deck = new Deck();
        cards = new ArrayList<>(List.of(
            new SuitedCard(Card.RANK.SIX, Card.SUIT.HEARTS),
            new SuitedCard(Card.RANK.EIGHT, Card.SUIT.HEARTS),
            new SuitedCard(Card.RANK.TEN, Card.SUIT.HEARTS)
        ));
        deck.removeCards(cards);
        assertEquals(1, MSP_Helper.solveHand(cards, 1, 2));

        // Flush Draw
        deck = new Deck();
        cards = new ArrayList<>(List.of(
                new SuitedCard(Card.RANK.ACE, Card.SUIT.HEARTS),
                new SuitedCard(Card.RANK.TEN, Card.SUIT.HEARTS),
                new SuitedCard(Card.RANK.TWO, Card.SUIT.HEARTS)
        ));
        deck.removeCards(cards);
        assertEquals(1, MSP_Helper.solveHand(cards, 1, 2));

        // Low Pair
        deck = new Deck();
        cards = new ArrayList<>(List.of(
                new SuitedCard(Card.RANK.TWO, Card.SUIT.HEARTS),
                new SuitedCard(Card.RANK.TWO, Card.SUIT.DIAMONDS),
                new SuitedCard(Card.RANK.THREE, Card.SUIT.SPADES)
        ));
        deck.removeCards(cards);
        assertEquals(1, MSP_Helper.solveHand(cards, 1, 5));

        // Three points
        deck = new Deck();
        cards = new ArrayList<>(List.of(
                new SuitedCard(Card.RANK.ACE, Card.SUIT.HEARTS),
                new SuitedCard(Card.RANK.TEN, Card.SUIT.DIAMONDS),
                new SuitedCard(Card.RANK.TWO, Card.SUIT.SPADES)
        ));
        deck.removeCards(cards);
        assertEquals(1, MSP_Helper.solveHand(cards, 1, 2));

        // Straight Draw Open But Low
        deck = new Deck();
        cards = new ArrayList<>(List.of(
                new SuitedCard(Card.RANK.THREE, Card.SUIT.HEARTS),
                new SuitedCard(Card.RANK.FIVE, Card.SUIT.DIAMONDS),
                new SuitedCard(Card.RANK.FOUR, Card.SUIT.SPADES)
        ));
        deck.removeCards(cards);
        assertEquals(0, MSP_Helper.solveHand(cards, 1, 2));

        // Straight Draw Inside Two Mid
        deck = new Deck();
        cards = new ArrayList<>(List.of(
                new SuitedCard(Card.RANK.FIVE, Card.SUIT.HEARTS),
                new SuitedCard(Card.RANK.SIX, Card.SUIT.DIAMONDS),
                new SuitedCard(Card.RANK.EIGHT, Card.SUIT.SPADES)
        ));
        deck.removeCards(cards);
        assertEquals(1, MSP_Helper.solveHand(cards, 1, 2));

        // Straight Draw Inside One Mid
        deck = new Deck();
        cards = new ArrayList<>(List.of(
                new SuitedCard(Card.RANK.FIVE, Card.SUIT.HEARTS),
                new SuitedCard(Card.RANK.SEVEN, Card.SUIT.DIAMONDS),
                new SuitedCard(Card.RANK.FOUR, Card.SUIT.SPADES)
        ));
        deck.removeCards(cards);
         assertEquals(0, MSP_Helper.solveHand(cards, 1, 2));

        // Two Points
        deck = new Deck();
        cards = new ArrayList<>(List.of(
                new SuitedCard(Card.RANK.TWO, Card.SUIT.HEARTS),
                new SuitedCard(Card.RANK.TEN, Card.SUIT.DIAMONDS),
                new SuitedCard(Card.RANK.SIX, Card.SUIT.SPADES)
        ));
        deck.removeCards(cards);
         assertEquals(0, MSP_Helper.solveHand(cards, 1, 2));

        // Made hands...
        // Three of a kind
        deck = new Deck();
        cards = new ArrayList<>(List.of(
                new SuitedCard(Card.RANK.ACE, Card.SUIT.HEARTS),
                new SuitedCard(Card.RANK.ACE, Card.SUIT.DIAMONDS),
                new SuitedCard(Card.RANK.ACE, Card.SUIT.SPADES)
        ));
        deck.removeCards(cards);
        assertEquals (3, MSP_Helper.solveHand(cards, 1, 5));
    }

    @Test
    public void testSolveSecondStreetWeird() {
        // --------- Testing Interesting Results ---------
        // Queen of Spades and Four of Clubs and two of spades
        Deck deck = new Deck();
        List<SuitedCard> cards = new ArrayList<>(List.of(
                new SuitedCard(Card.RANK.QUEEN, Card.SUIT.SPADES),
                new SuitedCard(Card.RANK.FOUR, Card.SUIT.CLUBS),
                new SuitedCard(Card.RANK.TWO, Card.SUIT.SPADES)
        ));
        deck.removeCards(cards);
         assertEquals(0, MSP_Helper.solveHand(cards, 1, 2));

        // Two of Diamonds and Jack of Spades and three of clubs and king of clubs
        deck = new Deck();
        cards = new ArrayList<>(List.of(
                new SuitedCard(Card.RANK.TWO, Card.SUIT.DIAMONDS),
                new SuitedCard(Card.RANK.JACK, Card.SUIT.SPADES),
                new SuitedCard(Card.RANK.THREE, Card.SUIT.CLUBS)
        ));
        deck.removeCards(cards);
         assertEquals(0, MSP_Helper.solveHand(cards, 1, 2));

         // Start with Ace Five, then get a gut shot straight
        deck = new Deck();
        cards = new ArrayList<>(List.of(
            new SuitedCard(Card.RANK.FIVE, Card.SUIT.DIAMONDS),
            new SuitedCard(Card.RANK.ACE, Card.SUIT.HEARTS),
            new SuitedCard(Card.RANK.THREE, Card.SUIT.CLUBS)
        ));
        deck.removeCards(cards);
        assertEquals(0, MSP_Helper.solveHand(cards, 1, 2));
    }

    @Test
    public void testSolveThirdStreet() {
        // Royal Flush Draw
        Deck deck = new Deck();
        List<SuitedCard> cards = new ArrayList<>(List.of(
                new SuitedCard(Card.RANK.KING, Card.SUIT.HEARTS),
                new SuitedCard(Card.RANK.QUEEN, Card.SUIT.HEARTS),
                new SuitedCard(Card.RANK.JACK, Card.SUIT.HEARTS),
                new SuitedCard(Card.RANK.TEN, Card.SUIT.HEARTS)
        ));
        deck.removeCards(cards);
        assertEquals (3, MSP_Helper.solveHand(cards, 1, 5));

        // Straight Flush Draw
        deck = new Deck();
        cards = new ArrayList<>(List.of(
                new SuitedCard(Card.RANK.JACK, Card.SUIT.HEARTS),
                new SuitedCard(Card.RANK.TEN, Card.SUIT.HEARTS),
                new SuitedCard(Card.RANK.NINE, Card.SUIT.HEARTS),
                new SuitedCard(Card.RANK.EIGHT, Card.SUIT.HEARTS)
        ));
        deck.removeCards(cards);
        assertEquals (3, MSP_Helper.solveHand(cards, 1, 5));

        // Flush Draw
        deck = new Deck();
        cards = new ArrayList<>(List.of(
                new SuitedCard(Card.RANK.SIX, Card.SUIT.HEARTS),
                new SuitedCard(Card.RANK.FIVE, Card.SUIT.HEARTS),
                new SuitedCard(Card.RANK.FOUR, Card.SUIT.HEARTS),
                new SuitedCard(Card.RANK.TWO, Card.SUIT.HEARTS)
        ));
        deck.removeCards(cards);
        assertEquals (3, MSP_Helper.solveHand(cards, 1, 5));

        // Straight Draw Open
        deck = new Deck();
        cards = new ArrayList<>(List.of(
                new SuitedCard(Card.RANK.EIGHT, Card.SUIT.HEARTS),
                new SuitedCard(Card.RANK.SEVEN, Card.SUIT.DIAMONDS),
                new SuitedCard(Card.RANK.SIX, Card.SUIT.SPADES),
                new SuitedCard(Card.RANK.FIVE, Card.SUIT.CLUBS)
        ));
        deck.removeCards(cards);
        assertEquals(3, MSP_Helper.solveHand(cards, 1, 5));

        // Straight Draw Inside
        deck = new Deck();
        cards = new ArrayList<>(List.of(
                new SuitedCard(Card.RANK.EIGHT, Card.SUIT.HEARTS),
                new SuitedCard(Card.RANK.SEVEN, Card.SUIT.DIAMONDS),
                new SuitedCard(Card.RANK.FIVE, Card.SUIT.SPADES),
                new SuitedCard(Card.RANK.NINE, Card.SUIT.CLUBS)
        ));
        deck.removeCards(cards);
        assertEquals(1, MSP_Helper.solveHand(cards, 1, 5));

        // Low Pair
        deck = new Deck();
        cards = new ArrayList<>(List.of(
                new SuitedCard(Card.RANK.TWO, Card.SUIT.HEARTS),
                new SuitedCard(Card.RANK.TWO, Card.SUIT.DIAMONDS),
                new SuitedCard(Card.RANK.THREE, Card.SUIT.SPADES),
                new SuitedCard(Card.RANK.FOUR, Card.SUIT.CLUBS)
        ));
        deck.removeCards(cards);
        assertEquals(1, MSP_Helper.solveHand(cards, 1, 5));

        // Four points
        deck = new Deck();
        cards = new ArrayList<>(List.of(
                new SuitedCard(Card.RANK.ACE, Card.SUIT.HEARTS),
                new SuitedCard(Card.RANK.KING, Card.SUIT.DIAMONDS),
                new SuitedCard(Card.RANK.TWO, Card.SUIT.SPADES),
                new SuitedCard(Card.RANK.FIVE, Card.SUIT.CLUBS)
        ));
        deck.removeCards(cards);
        // Or zero if implementing reduced variance start
        assertEquals(1, MSP_Helper.solveHand(cards, 1, 5));

        // Previous Over Bet
        deck = new Deck();
        cards = new ArrayList<>(List.of(
                new SuitedCard(Card.RANK.TEN, Card.SUIT.HEARTS),
                new SuitedCard(Card.RANK.EIGHT, Card.SUIT.DIAMONDS),
                new SuitedCard(Card.RANK.SIX, Card.SUIT.SPADES),
                new SuitedCard(Card.RANK.TWO, Card.SUIT.CLUBS)
        ));
        deck.removeCards(cards);
        assertEquals(1, MSP_Helper.solveHand(cards, 1, 5));

        // Three Points with 3x raise
        deck = new Deck();
        cards = new ArrayList<>(List.of(
                new SuitedCard(Card.RANK.ACE, Card.SUIT.HEARTS),
                new SuitedCard(Card.RANK.THREE, Card.SUIT.DIAMONDS),
                new SuitedCard(Card.RANK.TWO, Card.SUIT.SPADES),
                new SuitedCard(Card.RANK.SIX, Card.SUIT.CLUBS)
        ));
        deck.removeCards(cards);
        assertEquals(1, MSP_Helper.solveHand(cards, 1, 5));

        // Three Points
        deck = new Deck();
        cards = new ArrayList<>(List.of(
                new SuitedCard(Card.RANK.ACE, Card.SUIT.HEARTS),
                new SuitedCard(Card.RANK.THREE, Card.SUIT.DIAMONDS),
                new SuitedCard(Card.RANK.TWO, Card.SUIT.SPADES),
                new SuitedCard(Card.RANK.SIX, Card.SUIT.CLUBS)
        ));
        deck.removeCards(cards);
        assertEquals(0, MSP_Helper.solveHand(cards, 1, 3));

        // Made hands...
        // Four of a kind
        deck = new Deck();
        cards = new ArrayList<>(List.of(
                new SuitedCard(Card.RANK.ACE, Card.SUIT.HEARTS),
                new SuitedCard(Card.RANK.ACE, Card.SUIT.DIAMONDS),
                new SuitedCard(Card.RANK.ACE, Card.SUIT.SPADES),
                new SuitedCard(Card.RANK.ACE, Card.SUIT.CLUBS)
        ));
        deck.removeCards(cards);
        assertEquals (3, MSP_Helper.solveHand(cards, 1, 7));
    }
}