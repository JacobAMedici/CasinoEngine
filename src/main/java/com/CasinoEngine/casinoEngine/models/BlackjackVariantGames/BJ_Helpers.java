package com.CasinoEngine.casinoEngine.models.BlackjackVariantGames;

import com.CasinoEngine.casinoEngine.models.AllHelpers.Deck;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class BJ_Helpers {

    boolean surrenderAllowed;
    boolean doubleAfterSlitAllowed;
    boolean resplitAcesAllowed;
    boolean hitSplitAcesAllowed;
    boolean dealerHitsSoft17;
    double blackjackMultiplier;

    public BJ_Helpers(
            boolean surrenderAllowed,
            boolean doubleAfterSlitAllowed,
            boolean resplitAcesAllowed,
            boolean hitSplitAcesAllowed,
            boolean dealerHitsSoft17,
            double blackjackMultiplier
    ) {
        this.surrenderAllowed = surrenderAllowed;
        this.doubleAfterSlitAllowed = doubleAfterSlitAllowed;
        this.resplitAcesAllowed = resplitAcesAllowed;
        this.hitSplitAcesAllowed = hitSplitAcesAllowed;
        this.dealerHitsSoft17 = dealerHitsSoft17;
        this.blackjackMultiplier = blackjackMultiplier;
    }

    public enum MOVE {
        HIT,
        STAND,
        DOUBLE_DOWN,
        SPLIT,
        SURRENDER,
        ERROR
    }

    public static int getHandValue(List<Deck.Card> cards) {
        int value = 0;
        int softAces = 0;

        for (Deck.Card card : cards) {
            if (card.rank == Deck.Card.RANK.ACE) {
                softAces++;
                value += 11;
            }
            else {
                value += Deck.Card.rankToNum.get(card.rank);
            }
        }

        while (value > 21 && softAces > 0) {
            value -= 10;
            softAces--;
        }

        return value;
    }

    public MOVE determineBestMove(List<Deck.Card> playerCards, Deck.Card dealerCard) {
        // Find hit EV
        double hitEV = hitEV(playerCards, dealerCard);
        // Find stand EV
        double standEV = standEV(playerCards, dealerCard);
        // Find double down EV
        double doubleEV = doubleEV(playerCards, dealerCard);
        // Find split EV
        double splitEV = splitEV(playerCards, dealerCard);
        // Find surrender EV
        double surrenderEV = -0.5;

        // Find max EV
        double maxValue = Collections.max(Arrays.asList(hitEV, standEV, doubleEV, splitEV, surrenderEV));

        // Return move with highest EV
        if (maxValue == hitEV) {
            return MOVE.HIT;
        } else if (maxValue == standEV) {
            return MOVE.STAND;
        } else if (maxValue == doubleEV) {
            return MOVE.DOUBLE_DOWN;
        } else if (maxValue == splitEV) {
            return MOVE.SPLIT;
        } else if (maxValue == surrenderEV) {
            return MOVE.SURRENDER;
        } else {
            return MOVE.ERROR;
        }
    }

    private double hitEV(List<Deck.Card> playerCards, Deck.Card dealerCard) {
        return 0;
    }

    private double standEV(List<Deck.Card> playerCards, Deck.Card dealerCard) {
        return 0;
    }

    private double doubleEV(List<Deck.Card> playerCards, Deck.Card dealerCard) {
        return 0;
    }

    private double splitEV(List<Deck.Card> playerCards, Deck.Card dealerCard) {
        return 0;
    }

    private double showdownEV(List<Deck.Card> playerCards, Deck.Card dealerCard) {
        double ev = 0;
        int playerValue = getHandValue(playerCards);
        for (Deck.Card card : playerCards) {

        }
        return 0;
    }
}
