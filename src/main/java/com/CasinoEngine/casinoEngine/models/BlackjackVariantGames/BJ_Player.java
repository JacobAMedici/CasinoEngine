package com.CasinoEngine.casinoEngine.models.BlackjackVariantGames;

import com.CasinoEngine.casinoEngine.models.AllHelpers.Deck;
import com.CasinoEngine.casinoEngine.models.AllHelpers.RequestFailedException;
import com.CasinoEngine.casinoEngine.models.Player;

import java.util.ArrayList;
import java.util.List;

public class BJ_Player implements Player {

    private int chipStack;
    private List<Deck.Card> cards = new ArrayList<>();

    public BJ_Player(int chipStack) {
        this.chipStack = chipStack;
    }

    @Override
    public int getChipStack() {
        return chipStack;
    }

    @Override
    public void makeBet(int bet) throws RequestFailedException {
        if (bet <= chipStack) {
            chipStack -= bet;
        } else throw new RequestFailedException("Cannot Bet More Than Chip Stack");
    }

    public void assignCards(Deck.Card card1, Deck.Card card2) {
        cards.add(card1);
        cards.add(card2);
    }

    public void addCard(Deck.Card card) {
        cards.add(card);
    }

    @Override
    public void collectWinnings(int winnings) {
        chipStack += winnings;
    }

    public List<Deck.Card> getCards() {
        return cards;
    }

    public void clearHand() {
        cards.clear();
    }

    public int cardsValue() {
        return BJ_Helpers.getHandValue(cards);
    }

}
