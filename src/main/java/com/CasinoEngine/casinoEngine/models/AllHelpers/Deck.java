package com.CasinoEngine.casinoEngine.models.AllHelpers;

import java.util.*;

public class Deck {
    public static class Card implements Comparable<Card> {

        public enum RANK {
            TWO,
            THREE,
            FOUR,
            FIVE,
            SIX,
            SEVEN,
            EIGHT,
            NINE,
            TEN,
            JACK,
            QUEEN,
            KING,
            ACE
        }

        public enum SUIT {
            HEARTS,
            DIAMONDS,
            SPADES,
            CLUBS
        }

        public final RANK rank;
        public final SUIT suit;

        public static Map<RANK, Integer> rankToNum = Map.ofEntries(
                Map.entry(RANK.TWO, 2),
                Map.entry(RANK.THREE, 3),
                Map.entry(RANK.FOUR, 4),
                Map.entry(RANK.FIVE, 5),
                Map.entry(RANK.SIX, 6),
                Map.entry(RANK.SEVEN, 7),
                Map.entry(RANK.EIGHT, 8),
                Map.entry(RANK.NINE, 9),
                Map.entry(RANK.TEN, 10),
                Map.entry(RANK.JACK, 11),
                Map.entry(RANK.QUEEN, 12),
                Map.entry(RANK.KING, 13),
                Map.entry(RANK.ACE, 14)
        );

        public Card(RANK rank, SUIT suit) {
            this.rank = rank;
            this.suit = suit;
        }

        @Override
        public int compareTo(Card other) {
            return rankToNum.get(rank) - rankToNum.get(other.rank);
        }

        @Override
        public boolean equals(Object other) {
            if (other instanceof Card otherCard) {
                return this.suit == otherCard.suit && this.rank == otherCard.rank;
            }
            return false;
        }

        @Override
        public String toString() {
            return String.format("%s of %s", rank, suit).toLowerCase();
        }
    }

    private final LinkedList<Card> cards;

    public Deck() {
        cards = fillOneDeck();
        shuffle();
    }

    public Deck(int numberOfDecks) {
        cards = fillNDecks(numberOfDecks);
        shuffle();
    }

    private static LinkedList<Card> fillOneDeck() {
        LinkedList<Card> cardDeck = new LinkedList<>();
        for (Card.SUIT suit : Card.SUIT.values()) {
            for (Card.RANK rank : Card.RANK.values()) {
                cardDeck.add(new Card(rank, suit));
            }
        }
        return cardDeck;
    }


    public LinkedList<Card> getDeck() {
        return cards;
    }

    public void shuffle() {
        Collections.shuffle(cards);
    }

    public Card getCard() {
        return cards.poll();
    }

    public void removeCards(List<Card> cardsToRemove) {
        Iterator<Card> cardIterator = cards.iterator();
        while (cardIterator.hasNext()) {
            Card card = cardIterator.next();
            for (Card cardToRemove : cardsToRemove) {
                if (card.rank.equals(cardToRemove.rank) && card.suit.equals(cardToRemove.suit)) {
                    cardIterator.remove();
                    break;
                }
            }
        }
    }

    private LinkedList<Card> fillNDecks(int numberOfDecks) {
        LinkedList<Card> cards = new LinkedList<>();
        for (int deckNumber = 1; deckNumber <= numberOfDecks; deckNumber++) {
            cards.addAll(fillOneDeck());
        }
        return cards;
    }

    public List<Deck.Card> getRankOnlyDeck() {
        List<Deck.Card> rankOnlyDeck = new ArrayList<>();
        for (Deck.Card.RANK rank : Deck.Card.RANK.values()) {
            rankOnlyDeck.add(new Deck.Card(rank, Deck.Card.SUIT.SPADES));
        }
        return rankOnlyDeck;
    }
}
