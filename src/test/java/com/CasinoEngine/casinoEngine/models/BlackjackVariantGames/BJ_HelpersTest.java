package com.CasinoEngine.casinoEngine.models.BlackjackVariantGames;

import com.CasinoEngine.casinoEngine.models.AllHelpers.Deck;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BJ_HelpersTest {
    @Test
    public void testGetHandValue() {
        BJ_Player player = new BJ_Player(100);
        player.assignCards(new Deck.Card(Deck.Card.RANK.ACE, Deck.Card.SUIT.SPADES), new Deck.Card(Deck.Card.RANK.ACE, Deck.Card.SUIT.HEARTS));
        assertEquals(12, player.cardsValue());
        player.addCard(new Deck.Card(Deck.Card.RANK.TEN, Deck.Card.SUIT.SPADES));
        assertEquals(12, player.cardsValue());
        player.addCard(new Deck.Card(Deck.Card.RANK.TWO, Deck.Card.SUIT.SPADES));
        assertEquals(14, player.cardsValue());
        player.addCard(new Deck.Card(Deck.Card.RANK.ACE, Deck.Card.SUIT.CLUBS));
        assertEquals(15, player.cardsValue());
        player.addCard(new Deck.Card(Deck.Card.RANK.ACE, Deck.Card.SUIT.DIAMONDS));
        assertEquals(16, player.cardsValue());
        player.addCard(new Deck.Card(Deck.Card.RANK.ACE, Deck.Card.SUIT.SPADES));
        assertEquals(17, player.cardsValue());
        player.addCard(new Deck.Card(Deck.Card.RANK.ACE, Deck.Card.SUIT.HEARTS));
        assertEquals(18, player.cardsValue());
        player.addCard(new Deck.Card(Deck.Card.RANK.ACE, Deck.Card.SUIT.CLUBS));
        assertEquals(19, player.cardsValue());
        player.addCard(new Deck.Card(Deck.Card.RANK.ACE, Deck.Card.SUIT.DIAMONDS));
        assertEquals(20, player.cardsValue());
        player.addCard(new Deck.Card(Deck.Card.RANK.ACE, Deck.Card.SUIT.SPADES));
        assertEquals(21, player.cardsValue());
        player.addCard(new Deck.Card(Deck.Card.RANK.ACE, Deck.Card.SUIT.HEARTS));

        player.clearHand();
        player.assignCards(new Deck.Card(Deck.Card.RANK.TWO, Deck.Card.SUIT.SPADES), new Deck.Card(Deck.Card.RANK.TWO, Deck.Card.SUIT.HEARTS));
        assertEquals(4, player.cardsValue());
        player.addCard(new Deck.Card(Deck.Card.RANK.TWO, Deck.Card.SUIT.CLUBS));
        assertEquals(6, player.cardsValue());
        player.addCard(new Deck.Card(Deck.Card.RANK.ACE, Deck.Card.SUIT.DIAMONDS));
        assertEquals(17, player.cardsValue());
        player.addCard(new Deck.Card(Deck.Card.RANK.SIX, Deck.Card.SUIT.SPADES));
        assertEquals(13, player.cardsValue());
        player.addCard(new Deck.Card(Deck.Card.RANK.FIVE, Deck.Card.SUIT.HEARTS));
        assertEquals(18, player.cardsValue());
        player.addCard(new Deck.Card(Deck.Card.RANK.FOUR, Deck.Card.SUIT.CLUBS));
        assertEquals(22, player.cardsValue());

        player.clearHand();
        player.assignCards(new Deck.Card(Deck.Card.RANK.ACE, Deck.Card.SUIT.SPADES), new Deck.Card(Deck.Card.RANK.TEN, Deck.Card.SUIT.HEARTS));
        assertEquals(21, player.cardsValue());

        player.clearHand();
        player.assignCards(new Deck.Card(Deck.Card.RANK.ACE, Deck.Card.SUIT.SPADES), new Deck.Card(Deck.Card.RANK.ACE, Deck.Card.SUIT.HEARTS));
        assertEquals(12, player.cardsValue());
        player.addCard(new Deck.Card(Deck.Card.RANK.ACE, Deck.Card.SUIT.CLUBS));
        assertEquals(13, player.cardsValue());
        player.addCard(new Deck.Card(Deck.Card.RANK.EIGHT, Deck.Card.SUIT.DIAMONDS));
        assertEquals(21, player.cardsValue());

    }
}