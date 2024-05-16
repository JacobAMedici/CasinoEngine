package com.CasinoEngine.casinoEngine.models;

import com.CasinoEngine.casinoEngine.models.AllHelpers.RequestFailedException;

public interface Player {
    /**
     * Retrieves the current amount of chips the player has.
     *
     * @return the current chip stack as an integer.
     */
    int getChipStack();

    /**
     * Attempts to place a bet by subtracting the bet amount from the player's chip stack.
     * If the bet amount is greater than the available chip stack, a RequestFailedException is thrown.
     *
     * @param bet the amount of chips the player wishes to bet.
     * @throws RequestFailedException if the bet amount exceeds the player's current chip stack.
     */
    void makeBet(int bet) throws RequestFailedException;

    /**
     * Adds the specified winnings to the player's chip stack.
     *
     * @param winnings the amount of chips won.
     */
    void collectWinnings(int winnings);
}
