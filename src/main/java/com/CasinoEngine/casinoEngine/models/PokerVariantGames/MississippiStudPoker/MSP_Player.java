package com.CasinoEngine.casinoEngine.models.PokerVariantGames.MississippiStudPoker;

import com.CasinoEngine.casinoEngine.models.AllHelpers.Deck;
import com.CasinoEngine.casinoEngine.models.AllHelpers.RequestFailedException;
import com.CasinoEngine.casinoEngine.models.Player;

import java.util.List;

public class MSP_Player implements Player {

    private int chipStack;
    private Deck.Card card1;
    private Deck.Card card2;

    public MSP_Player(int chipStack) {
        this.chipStack = chipStack;
    }

    public void assignFirstCard(Deck.Card card1) {
        this.card1 = card1;
    }

    public void assignSecondCard(Deck.Card card2) {
        this.card2 = card2;
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

    public List<Deck.Card> getHoleCards() {
        return List.of(card1, card2);
    }

    @Override
    public void collectWinnings(int winnings) {
        chipStack += winnings;
    }
}
