package model.games.poker.pokerHelpers;

import model.cards.Card;
import model.cards.SuitedCard;
import model.games.poker.PokerHelpers.PokerHand;
import model.games.poker.PokerHelpers.PokerHelpers;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertTrue;

class PokerHelpersTest {

    PokerHelpers pokerHelpers = new PokerHelpers();

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

    PokerHand basePair =
            new PokerHand(PokerHand.HAND_TYPE.PAIR,
                    new ArrayList<>(List.of(Card.RANK.ACE)),
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
    public void findBestFiveCardHandRoyalFlush() {
        // Make sure it finds the right royal flush
        PokerHand royalFlushHand = pokerHelpers.findBestFiveCardHand(
                List.of(
                        new SuitedCard(Card.RANK.ACE, Card.SUIT.HEARTS),
                        new SuitedCard(Card.RANK.QUEEN, Card.SUIT.HEARTS)),
                List.of(
                        new SuitedCard(Card.RANK.KING, Card.SUIT.HEARTS),
                        new SuitedCard(Card.RANK.JACK, Card.SUIT.HEARTS),
                        new SuitedCard(Card.RANK.TEN, Card.SUIT.HEARTS))
        );
        assertEquals(baseRoyalFlush, royalFlushHand);
    }

    @Test
    public void findBestStraightFlushTest() {
        // Make sure it finds the right straight flush
        PokerHand baseStraightFlushHand = pokerHelpers.findBestFiveCardHand(
                List.of(
                        new SuitedCard(Card.RANK.NINE, Card.SUIT.HEARTS),
                        new SuitedCard(Card.RANK.JACK, Card.SUIT.HEARTS)),
                List.of(
                        new SuitedCard(Card.RANK.TEN, Card.SUIT.HEARTS),
                        new SuitedCard(Card.RANK.QUEEN, Card.SUIT.HEARTS),
                        new SuitedCard(Card.RANK.KING, Card.SUIT.HEARTS))
        );
        assertEquals(baseStraightFlush, baseStraightFlushHand);
        // Find lower straight flush
        PokerHand lowStraightFlush = pokerHelpers.findBestFiveCardHand(
                List.of(
                        new SuitedCard(Card.RANK.EIGHT, Card.SUIT.DIAMONDS),
                        new SuitedCard(Card.RANK.SEVEN, Card.SUIT.DIAMONDS)),
                List.of(
                        new SuitedCard(Card.RANK.SIX, Card.SUIT.DIAMONDS),
                        new SuitedCard(Card.RANK.FIVE, Card.SUIT.DIAMONDS),
                        new SuitedCard(Card.RANK.FOUR, Card.SUIT.DIAMONDS))
        );
        // Compare low to high
        assertTrue(lowStraightFlush.compareTo(baseStraightFlushHand) < 0);
        // Compare high to low
        assertTrue(baseStraightFlushHand.compareTo(lowStraightFlush) > 0);

        // King, Two, Three, Four, Five
        PokerHand kingToFive = pokerHelpers.findBestFiveCardHand(
                List.of(
                        new SuitedCard(Card.RANK.KING, Card.SUIT.DIAMONDS),
                        new SuitedCard(Card.RANK.TWO, Card.SUIT.DIAMONDS)),
                List.of(
                        new SuitedCard(Card.RANK.THREE, Card.SUIT.DIAMONDS),
                        new SuitedCard(Card.RANK.FOUR, Card.SUIT.DIAMONDS),
                        new SuitedCard(Card.RANK.FIVE, Card.SUIT.DIAMONDS))
        );

        // Compare kingToFive
        assertNotEquals(kingToFive.handStrength, PokerHand.HAND_TYPE.STRAIGHT_FLUSH);
    }

    @Test
    public void findBestFourOfAKindTest() {
        PokerHand baseFourOfAKindHand = pokerHelpers.findBestFiveCardHand(
                List.of(
                        new SuitedCard(Card.RANK.ACE, Card.SUIT.HEARTS),
                        new SuitedCard(Card.RANK.ACE, Card.SUIT.CLUBS)),
                List.of(
                        new SuitedCard(Card.RANK.ACE, Card.SUIT.DIAMONDS),
                        new SuitedCard(Card.RANK.ACE, Card.SUIT.SPADES),
                        new SuitedCard(Card.RANK.TWO, Card.SUIT.HEARTS)));
        assertEquals(baseFourOfAKind, baseFourOfAKindHand);

        // Lower Four of a Kind hand with four Kings
        PokerHand lowFourOfAKindHand = pokerHelpers.findBestFiveCardHand(
                List.of(
                        new SuitedCard(Card.RANK.KING, Card.SUIT.HEARTS),
                        new SuitedCard(Card.RANK.KING, Card.SUIT.DIAMONDS)
                ),
                List.of(
                        new SuitedCard(Card.RANK.KING, Card.SUIT.CLUBS),
                        new SuitedCard(Card.RANK.KING, Card.SUIT.SPADES),
                        new SuitedCard(Card.RANK.THREE, Card.SUIT.HEARTS)
                )
        );

        // Higher Four of a Kind hand with four Aces and a Three
        PokerHand highFourOfAKindHand = pokerHelpers.findBestFiveCardHand(
                List.of(
                        new SuitedCard(Card.RANK.ACE, Card.SUIT.HEARTS),
                        new SuitedCard(Card.RANK.ACE, Card.SUIT.DIAMONDS)
                ),
                List.of(
                        new SuitedCard(Card.RANK.ACE, Card.SUIT.CLUBS),
                        new SuitedCard(Card.RANK.ACE, Card.SUIT.SPADES),
                        new SuitedCard(Card.RANK.THREE, Card.SUIT.DIAMONDS)
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
    public void findBestFullHouseTest() {
        PokerHand actualHand = pokerHelpers.findBestFiveCardHand(
                List.of(
                        new SuitedCard(Card.RANK.ACE, Card.SUIT.HEARTS),
                        new SuitedCard(Card.RANK.ACE, Card.SUIT.CLUBS)),
                List.of(
                        new SuitedCard(Card.RANK.ACE, Card.SUIT.DIAMONDS),
                        new SuitedCard(Card.RANK.KING, Card.SUIT.SPADES),
                        new SuitedCard(Card.RANK.KING, Card.SUIT.HEARTS)));
        assertEquals(baseFullHouse, actualHand);

        // Lower Full House with three Kings and two Queens
        PokerHand lowFullHouseHand = pokerHelpers.findBestFiveCardHand(
                List.of(
                        new SuitedCard(Card.RANK.KING, Card.SUIT.HEARTS),
                        new SuitedCard(Card.RANK.KING, Card.SUIT.DIAMONDS)),
                List.of(
                        new SuitedCard(Card.RANK.KING, Card.SUIT.CLUBS),
                        new SuitedCard(Card.RANK.QUEEN, Card.SUIT.SPADES),
                        new SuitedCard(Card.RANK.QUEEN, Card.SUIT.HEARTS)
                )
        );

        // Higher Full House with three Aces and two Queens
        PokerHand highFullHouseHand = pokerHelpers.findBestFiveCardHand(
                List.of(
                        new SuitedCard(Card.RANK.ACE, Card.SUIT.HEARTS),
                        new SuitedCard(Card.RANK.ACE, Card.SUIT.CLUBS)),
                List.of(
                        new SuitedCard(Card.RANK.ACE, Card.SUIT.DIAMONDS),
                        new SuitedCard(Card.RANK.QUEEN, Card.SUIT.SPADES),
                        new SuitedCard(Card.RANK.QUEEN, Card.SUIT.HEARTS)
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
    public void findBestFlushTest() {
        PokerHand baseFlushHand = pokerHelpers.findBestFiveCardHand(
                List.of(
                        new SuitedCard(Card.RANK.QUEEN, Card.SUIT.HEARTS),
                        new SuitedCard(Card.RANK.JACK, Card.SUIT.HEARTS)),
                List.of(
                        new SuitedCard(Card.RANK.ACE, Card.SUIT.HEARTS),
                        new SuitedCard(Card.RANK.TEN, Card.SUIT.HEARTS),
                        new SuitedCard(Card.RANK.NINE, Card.SUIT.HEARTS)));
        assertEquals(baseFlush, baseFlushHand);

        // Lower Flush with a high card King
        PokerHand lowFlushHand = pokerHelpers.findBestFiveCardHand(
                List.of(
                        new SuitedCard(Card.RANK.KING, Card.SUIT.HEARTS),
                        new SuitedCard(Card.RANK.QUEEN, Card.SUIT.HEARTS)),
                List.of(
                        new SuitedCard(Card.RANK.JACK, Card.SUIT.HEARTS),
                        new SuitedCard(Card.RANK.TEN, Card.SUIT.HEARTS),
                        new SuitedCard(Card.RANK.SEVEN, Card.SUIT.HEARTS)
                )
        );

        // Higher Flush with a high card Ace and higher second card (King)
        PokerHand highFlushHand = pokerHelpers.findBestFiveCardHand(
                List.of(
                        new SuitedCard(Card.RANK.ACE, Card.SUIT.HEARTS),
                        new SuitedCard(Card.RANK.KING, Card.SUIT.HEARTS)),
                List.of(
                        new SuitedCard(Card.RANK.QUEEN, Card.SUIT.HEARTS),
                        new SuitedCard(Card.RANK.JACK, Card.SUIT.HEARTS),
                        new SuitedCard(Card.RANK.NINE, Card.SUIT.HEARTS)
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
    public void findBestStraightTest() {
        PokerHand baseStraightHand = pokerHelpers.findBestFiveCardHand(
                List.of(
                        new SuitedCard(Card.RANK.KING, Card.SUIT.HEARTS),
                        new SuitedCard(Card.RANK.QUEEN, Card.SUIT.HEARTS)),
                List.of(
                        new SuitedCard(Card.RANK.JACK, Card.SUIT.SPADES),
                        new SuitedCard(Card.RANK.TEN, Card.SUIT.CLUBS),
                        new SuitedCard(Card.RANK.ACE, Card.SUIT.HEARTS)));
        assertEquals(baseStraight, baseStraightHand);

        // Lower Straight
        PokerHand lowStraightHand = pokerHelpers.findBestFiveCardHand(
                List.of(
                        new SuitedCard(Card.RANK.NINE, Card.SUIT.HEARTS),
                        new SuitedCard(Card.RANK.EIGHT, Card.SUIT.HEARTS)),
                List.of(
                        new SuitedCard(Card.RANK.SEVEN, Card.SUIT.SPADES),
                        new SuitedCard(Card.RANK.SIX, Card.SUIT.CLUBS),
                        new SuitedCard(Card.RANK.TEN, Card.SUIT.HEARTS)
                )
        );

        // Alternative Straight that also peaks at an Ace but includes a different lower card sequence (Wheel Straight)
        PokerHand altStraightHand = pokerHelpers.findBestFiveCardHand(
                List.of(
                        new SuitedCard(Card.RANK.ACE, Card.SUIT.DIAMONDS),
                        new SuitedCard(Card.RANK.TWO, Card.SUIT.HEARTS)),
                List.of(
                        new SuitedCard(Card.RANK.THREE, Card.SUIT.CLUBS),
                        new SuitedCard(Card.RANK.FOUR, Card.SUIT.SPADES),
                        new SuitedCard(Card.RANK.FIVE, Card.SUIT.HEARTS)
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
    public void findBestThreeOfAKindTest() {
        PokerHand baseThreeOfAKindHand = pokerHelpers.findBestFiveCardHand(
                List.of(
                        new SuitedCard(Card.RANK.ACE, Card.SUIT.HEARTS),
                        new SuitedCard(Card.RANK.ACE, Card.SUIT.CLUBS)),
                List.of(
                        new SuitedCard(Card.RANK.ACE, Card.SUIT.DIAMONDS),
                        new SuitedCard(Card.RANK.KING, Card.SUIT.SPADES),
                        new SuitedCard(Card.RANK.QUEEN, Card.SUIT.HEARTS)));
        assertEquals(baseThreeOfAKind, baseThreeOfAKindHand);

        // Lower Three of a Kind hand with three Kings and kickers Ace and Queen
        PokerHand lowThreeOfAKindHand = pokerHelpers.findBestFiveCardHand(
                List.of(
                        new SuitedCard(Card.RANK.KING, Card.SUIT.HEARTS),
                        new SuitedCard(Card.RANK.KING, Card.SUIT.DIAMONDS)),
                List.of(
                        new SuitedCard(Card.RANK.KING, Card.SUIT.CLUBS),
                        new SuitedCard(Card.RANK.ACE, Card.SUIT.SPADES),
                        new SuitedCard(Card.RANK.QUEEN, Card.SUIT.HEARTS)
                )
        );

        // Compare lower to base (expect less than 0)
        assertTrue(lowThreeOfAKindHand.compareTo(baseThreeOfAKindHand) < 0);

        // Create an alternative Three of a Kind with three Aces and kickers Ace and Jack
        PokerHand altThreeOfAKindHand = pokerHelpers.findBestFiveCardHand(
                List.of(
                        new SuitedCard(Card.RANK.ACE, Card.SUIT.HEARTS),
                        new SuitedCard(Card.RANK.ACE, Card.SUIT.CLUBS)),
                List.of(
                        new SuitedCard(Card.RANK.ACE, Card.SUIT.DIAMONDS),
                        new SuitedCard(Card.RANK.JACK, Card.SUIT.SPADES),
                        new SuitedCard(Card.RANK.TEN, Card.SUIT.HEARTS)
                )
        );

        // Compare alternative to base (expect less than 0)
        assertTrue(altThreeOfAKindHand.compareTo(baseThreeOfAKindHand) < 0);

        // Compare base to alternative (expect greater than 0)
        assertTrue(baseThreeOfAKindHand.compareTo(altThreeOfAKindHand) > 0);
    }

    @Test
    public void findBestTwoPairTest() {
        PokerHand baseTwoPairHand = pokerHelpers.findBestFiveCardHand(
                List.of(
                        new SuitedCard(Card.RANK.ACE, Card.SUIT.HEARTS),
                        new SuitedCard(Card.RANK.ACE, Card.SUIT.CLUBS)),
                List.of(
                        new SuitedCard(Card.RANK.KING, Card.SUIT.DIAMONDS),
                        new SuitedCard(Card.RANK.KING, Card.SUIT.SPADES),
                        new SuitedCard(Card.RANK.QUEEN, Card.SUIT.HEARTS)));
        assertEquals(baseTwoPair, baseTwoPairHand);

        // Lower Two Pair hand with pairs of Kings and Queens, and a kicker Ace
        PokerHand lowTwoPairHand = pokerHelpers.findBestFiveCardHand(
                List.of(
                        new SuitedCard(Card.RANK.KING, Card.SUIT.HEARTS),
                        new SuitedCard(Card.RANK.KING, Card.SUIT.CLUBS)),
                List.of(
                        new SuitedCard(Card.RANK.QUEEN, Card.SUIT.DIAMONDS),
                        new SuitedCard(Card.RANK.QUEEN, Card.SUIT.HEARTS),
                        new SuitedCard(Card.RANK.ACE, Card.SUIT.SPADES)
                )
        );

        // Compare lower to base (expect less than 0)
        assertTrue(lowTwoPairHand.compareTo(baseTwoPairHand) < 0);

        // Alternative Two Pair with pairs of Aces and Kings, and a kicker Jack (lower kicker than base)
        PokerHand altTwoPairHand = pokerHelpers.findBestFiveCardHand(
                List.of(
                        new SuitedCard(Card.RANK.ACE, Card.SUIT.HEARTS),
                        new SuitedCard(Card.RANK.ACE, Card.SUIT.DIAMONDS)),
                List.of(
                        new SuitedCard(Card.RANK.KING, Card.SUIT.CLUBS),
                        new SuitedCard(Card.RANK.KING, Card.SUIT.SPADES),
                        new SuitedCard(Card.RANK.JACK, Card.SUIT.HEARTS)
                )
        );

        // Compare alternative to base (expect less than 0)
        assertTrue(altTwoPairHand.compareTo(baseTwoPairHand) < 0);

        // Compare base to alternative (expect greater than 0)
        assertTrue(baseTwoPairHand.compareTo(altTwoPairHand) > 0);
    }

    @Test
    public void findBestPairTest() {
        PokerHand basePairHand = pokerHelpers.findBestFiveCardHand(
                List.of(
                        new SuitedCard(Card.RANK.ACE, Card.SUIT.HEARTS),
                        new SuitedCard(Card.RANK.ACE, Card.SUIT.CLUBS)),
                List.of(
                        new SuitedCard(Card.RANK.KING, Card.SUIT.DIAMONDS),
                        new SuitedCard(Card.RANK.QUEEN, Card.SUIT.SPADES),
                        new SuitedCard(Card.RANK.JACK, Card.SUIT.HEARTS)));
        assertEquals(basePair, basePairHand);

        // Lower Pair hand with a pair of Kings and kickers Ace, Queen, and Jack
        PokerHand lowPairHand = pokerHelpers.findBestFiveCardHand(
                List.of(
                        new SuitedCard(Card.RANK.KING, Card.SUIT.HEARTS),
                        new SuitedCard(Card.RANK.KING, Card.SUIT.CLUBS)),
                List.of(
                        new SuitedCard(Card.RANK.ACE, Card.SUIT.DIAMONDS),
                        new SuitedCard(Card.RANK.QUEEN, Card.SUIT.HEARTS),
                        new SuitedCard(Card.RANK.JACK, Card.SUIT.SPADES)
                )
        );

        // Compare lower to base (expect less than 0)
        assertTrue(lowPairHand.compareTo(basePairHand) < 0);

        // Alternative Pair with a pair of Aces and kickers King, Queen, and Ten (lower kicker than base)
        PokerHand altPairHand = pokerHelpers.findBestFiveCardHand(
                List.of(
                        new SuitedCard(Card.RANK.ACE, Card.SUIT.HEARTS),
                        new SuitedCard(Card.RANK.ACE, Card.SUIT.DIAMONDS)),
                List.of(
                        new SuitedCard(Card.RANK.KING, Card.SUIT.CLUBS),
                        new SuitedCard(Card.RANK.QUEEN, Card.SUIT.SPADES),
                        new SuitedCard(Card.RANK.TEN, Card.SUIT.HEARTS)
                )
        );

        // Compare alternative to base (expect less than 0)
        assertTrue(altPairHand.compareTo(basePairHand) < 0);

        // Compare base to alternative (expect greater than 0)
        assertTrue(basePairHand.compareTo(altPairHand) > 0);

        // Pair Sevens
    }

    @Test
    public void sixesVsSevens() {
        // Pair sixes with kickers Ace, Queen, and Jack
        PokerHand sixes = pokerHelpers.findBestFiveCardHand(
                List.of(
                        new SuitedCard(Card.RANK.SIX, Card.SUIT.HEARTS),
                        new SuitedCard(Card.RANK.SIX, Card.SUIT.CLUBS)),
                List.of(
                        new SuitedCard(Card.RANK.ACE, Card.SUIT.DIAMONDS),
                        new SuitedCard(Card.RANK.QUEEN, Card.SUIT.HEARTS),
                        new SuitedCard(Card.RANK.JACK, Card.SUIT.SPADES)
                )
        );

        // Pair Sevens
        PokerHand sevens = pokerHelpers.findBestFiveCardHand(
                List.of(
                        new SuitedCard(Card.RANK.SEVEN, Card.SUIT.HEARTS),
                        new SuitedCard(Card.RANK.SEVEN, Card.SUIT.CLUBS)),
                List.of(
                        new SuitedCard(Card.RANK.ACE, Card.SUIT.DIAMONDS),
                        new SuitedCard(Card.RANK.QUEEN, Card.SUIT.HEARTS),
                        new SuitedCard(Card.RANK.JACK, Card.SUIT.SPADES)
                )
        );

        // Compare lower to base (expect less than 0)
        assertTrue(sixes.compareTo(sevens) < 0);
    }

    @Test
    public void findBestHighCardTest() {
        PokerHand baseHighCardHand = pokerHelpers.findBestFiveCardHand(
                List.of(
                        new SuitedCard(Card.RANK.ACE, Card.SUIT.HEARTS),
                        new SuitedCard(Card.RANK.QUEEN, Card.SUIT.CLUBS)),
                List.of(
                        new SuitedCard(Card.RANK.JACK, Card.SUIT.DIAMONDS),
                        new SuitedCard(Card.RANK.TEN, Card.SUIT.SPADES),
                        new SuitedCard(Card.RANK.NINE, Card.SUIT.HEARTS)));
        assertEquals(baseHighCard, baseHighCardHand);

        // Lower High Card hand with King, Queen, Jack, Ten, and Eight
        PokerHand lowHighCardHand = pokerHelpers.findBestFiveCardHand(
                List.of(
                        new SuitedCard(Card.RANK.KING, Card.SUIT.HEARTS),
                        new SuitedCard(Card.RANK.QUEEN, Card.SUIT.CLUBS)),
                List.of(
                        new SuitedCard(Card.RANK.JACK, Card.SUIT.DIAMONDS),
                        new SuitedCard(Card.RANK.TEN, Card.SUIT.SPADES),
                        new SuitedCard(Card.RANK.EIGHT, Card.SUIT.HEARTS)
                )
        );

        // Compare lower to base (expect less than 0)
        assertTrue(lowHighCardHand.compareTo(baseHighCardHand) < 0);

        // Higher High Card hand with Ace, King, Queen, Jack, and Ten
        PokerHand highHighCardHand = pokerHelpers.findBestFiveCardHand(
                List.of(
                        new SuitedCard(Card.RANK.ACE, Card.SUIT.DIAMONDS),
                        new SuitedCard(Card.RANK.KING, Card.SUIT.HEARTS)),
                List.of(
                        new SuitedCard(Card.RANK.QUEEN, Card.SUIT.CLUBS),
                        new SuitedCard(Card.RANK.JACK, Card.SUIT.SPADES),
                        new SuitedCard(Card.RANK.TEN, Card.SUIT.HEARTS)
                )
        );

        // Compare higher to base (expect greater than 0)
        assertTrue(highHighCardHand.compareTo(baseHighCardHand) > 0);

        // Compare base to higher (expect less than 0)
        assertTrue(baseHighCardHand.compareTo(highHighCardHand) < 0);
    }

    @Test
    public void sixSevenStraight() {
        // 2-6 Straight
        PokerHand twoThroughSix = pokerHelpers.findBestFiveCardHand(
                List.of(
                        new SuitedCard(Card.RANK.TWO, Card.SUIT.HEARTS),
                        new SuitedCard(Card.RANK.THREE, Card.SUIT.CLUBS)),
                List.of(
                        new SuitedCard(Card.RANK.FOUR, Card.SUIT.DIAMONDS),
                        new SuitedCard(Card.RANK.FIVE, Card.SUIT.SPADES),
                        new SuitedCard(Card.RANK.SIX, Card.SUIT.HEARTS)
                )
        );

        System.out.println(twoThroughSix);

        // 3-7 Straight
        PokerHand threeThroughSeven = pokerHelpers.findBestFiveCardHand(
                List.of(
                        new SuitedCard(Card.RANK.THREE, Card.SUIT.HEARTS),
                        new SuitedCard(Card.RANK.FOUR, Card.SUIT.CLUBS)),
                List.of(
                        new SuitedCard(Card.RANK.FIVE, Card.SUIT.DIAMONDS),
                        new SuitedCard(Card.RANK.SIX, Card.SUIT.SPADES),
                        new SuitedCard(Card.RANK.SEVEN, Card.SUIT.HEARTS)
                )
        );

        // Compare lower to base (expect less than 0)
        assertTrue(twoThroughSix.compareTo(threeThroughSeven) < 0);

    }

    @Test
    public void testAllSameSuit() {
        // Flush Draw
        assertTrue(pokerHelpers.allSameSuit(
                List.of(
                        new SuitedCard(Card.RANK.ACE, Card.SUIT.HEARTS),
                        new SuitedCard(Card.RANK.QUEEN, Card.SUIT.HEARTS),
                        new SuitedCard(Card.RANK.KING, Card.SUIT.HEARTS),
                        new SuitedCard(Card.RANK.JACK, Card.SUIT.HEARTS)
                )
        ));

        // Not all same suit
        assertFalse(pokerHelpers.allSameSuit(
                List.of(
                        new SuitedCard(Card.RANK.ACE, Card.SUIT.HEARTS),
                        new SuitedCard(Card.RANK.QUEEN, Card.SUIT.HEARTS),
                        new SuitedCard(Card.RANK.KING, Card.SUIT.HEARTS),
                        new SuitedCard(Card.RANK.JACK, Card.SUIT.SPADES)
                )
        ));

        // Three of same suit
        assertTrue(pokerHelpers.allSameSuit(
                List.of(
                        new SuitedCard(Card.RANK.ACE, Card.SUIT.HEARTS),
                        new SuitedCard(Card.RANK.QUEEN, Card.SUIT.HEARTS),
                        new SuitedCard(Card.RANK.KING, Card.SUIT.HEARTS)
                )
        ));

        // Two of same suit
        assertTrue(pokerHelpers.allSameSuit(
                List.of(
                        new SuitedCard(Card.RANK.ACE, Card.SUIT.HEARTS),
                        new SuitedCard(Card.RANK.QUEEN, Card.SUIT.HEARTS)
                )
        ));

        // Two of same suit
        assertFalse(pokerHelpers.allSameSuit(
                List.of(
                        new SuitedCard(Card.RANK.ACE, Card.SUIT.HEARTS),
                        new SuitedCard(Card.RANK.QUEEN, Card.SUIT.SPADES)
                )
        ));
    }

}