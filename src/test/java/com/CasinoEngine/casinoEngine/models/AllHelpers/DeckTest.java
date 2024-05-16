package com.CasinoEngine.casinoEngine.models.AllHelpers;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class DeckTest {

    Deck.Card aceOfSpades = new Deck.Card(Deck.Card.RANK.ACE, Deck.Card.SUIT.SPADES);
    Deck.Card kingOfSpaces = new Deck.Card(Deck.Card.RANK.KING, Deck.Card.SUIT.SPADES);
    Deck.Card aceOfHearts = new Deck.Card(Deck.Card.RANK.ACE, Deck.Card.SUIT.HEARTS);

    @Test
    public void cardCompareToTest() {
        assertEquals(0, aceOfSpades.compareTo(aceOfHearts));
        assertTrue(aceOfSpades.compareTo(kingOfSpaces) > 0);
        assertTrue(kingOfSpaces.compareTo(aceOfSpades) < 0);
    }

    @Test
    public void cardEqualsTest() {
        assertEquals(aceOfSpades, aceOfSpades);
    }

}