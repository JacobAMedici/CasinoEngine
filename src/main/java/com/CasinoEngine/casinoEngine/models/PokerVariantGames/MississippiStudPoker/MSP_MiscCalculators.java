package com.CasinoEngine.casinoEngine.models.PokerVariantGames.MississippiStudPoker;

import com.CasinoEngine.casinoEngine.models.AllHelpers.Deck;
import com.CasinoEngine.casinoEngine.models.AllHelpers.RequestFailedException;
import com.CasinoEngine.casinoEngine.models.PokerVariantGames.PokerHelpers.PokerHelpers;

import java.util.ArrayList;
import java.util.List;

public class MSP_MiscCalculators {

    MSP_Helpers msp_helpers = new MSP_Helpers();
    PokerHelpers pokerHelpers = new PokerHelpers();

    final int NUM_SUITS = 4;

    public static void main(String[] ignored) throws RequestFailedException {
        MSP_MiscCalculators msp_miscCalculators = new MSP_MiscCalculators();
        msp_miscCalculators.findNumPossibleSFPermutations();
    }

    private void findNumPossibleSFPermutations() throws RequestFailedException {
        int numSFPermutations = 0;
        int totalSFAmountWagered = 0;
        Deck deck = new Deck();
        List<Deck.Card> cards = deck.getRankOnlyDeck();
        for (Deck.Card card1 : cards) {
            for (Deck.Card card2 : cards) {
                int suggestedWagerSecondStreet = msp_helpers.solveHand(List.of(card1, card2),1, 1);
                if (!card1.equals(card2) && suggestedWagerSecondStreet != 0) {
                    for (Deck.Card card3 : cards) {
                        if (card3 != card1 && card3 != card2) {
                            int suggestedWagerThirdStreet = msp_helpers.solveHand(new ArrayList<>(List.of(card1, card2, card3)),1, 1 + suggestedWagerSecondStreet);
                            for (Deck.Card card4 : cards) {
                                if (card4 != card1 && card4 != card3 && card4 != card2) {
                                    for (Deck.Card card5 : cards) {
                                        if (card5 != card1 && card5 != card4 && card5 != card3 && card5 != card2) {
                                            List<Deck.Card> playingCards = List.of(card1, card2, card3, card4, card5);
                                            PokerHelpers.Hand hand = pokerHelpers.findBestFiveCardHand(playingCards, List.of());
                                            if (hand.handStrength == PokerHelpers.HAND_TYPE.STRAIGHT_FLUSH) {
                                                numSFPermutations++;
                                                totalSFAmountWagered += (1 + suggestedWagerSecondStreet + suggestedWagerThirdStreet + 3);
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        System.out.println("Number of Straight Flushes " + numSFPermutations * NUM_SUITS);
        System.out.println("Total amount wagered " + totalSFAmountWagered * NUM_SUITS);
        System.out.println("Average amount wagered " + (double) totalSFAmountWagered / numSFPermutations);

//        Number of Straight Flushes 2784
//        Total amount wagered 19584
//        Average amount wagered 7.0344827586206895
    }
}
