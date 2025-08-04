package model.games.twentyOne.blackjack;

import model.cards.Card;
import model.cards.Deck;
import model.cards.SuitlessDeck;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static model.cards.Card.RANK.*;

public class CardCountingHelpers {

  public static void main(String[] ignored) {
    // 3:2, 6 decks, 52 CO, 4 splits, double any two cards, DAS, RSA, S17, ES, American
    Rules rules = new BlackjackRules(
        new BigDecimal("1.5"),
        6,
        52,
        4,
        Rules.DOUBLE_RULE.ANY_TWO_CARDS,
        Rules.DOUBLE_AFTER_SPLIT_RULE.DOUBLE_AFTER_SPLIT_ALLOWED,
        Rules.RESPLIT_ACES_RULE.RESPLIT_ACES_MAX_SPLIT_TIMES,
        Rules.DEALER_ACTION_SOFT_SEVENTEEN.STAND_ON_SOFT_17,
        Rules.SURRENDER_RULE.EARLY_SURRENDER,
        Rules.HOLE_CARD_RULE.AMERICAN_STYLE
    );
    List<Double> distribution = getTrueCountDistribution(rules, 1_000_000);

    for (int i = 0; i < distribution.size(); i++) {
      System.out.printf("True Count %4d: %.6f%n", i - 20, distribution.get(i));
    }
  }

  /**
   * Returns the true count distribution for a given number of decks.
   * <p>
   * This truncates between -inf and 1 and rounds between 1 and +inf.
   *
   * @param rules The rules of the game.
   * @param numberOfShoes The number of shoes to simulate.
   * @return A list of doubles representing the true count distribution.
   */
  public static List<Double> getTrueCountDistribution(Rules rules, int numberOfShoes) {
    final int CARDS_PER_DECK = 52;
    final int cardInDeck = rules.getNumberOfDecks() * CARDS_PER_DECK;
    ArrayList<Long> trueCountCount = new ArrayList<>(41);
    for (int i = 0; i < 41; i++) {
      trueCountCount.add(0L);
    }

    if (cardInDeck <= rules.getDeckPenetration()) {
      return List.of(0.0);
    }

    for (int shoeCount = 0; shoeCount < numberOfShoes; shoeCount++) {
      Deck<Card> deck = new SuitlessDeck(rules.getNumberOfDecks());
      int runningCount = 0;
      for (int cardNum = 0; cardNum < cardInDeck - rules.getDeckPenetration(); cardNum++) {
        switch (deck.getDeck().get(cardNum).rank()) {
          case TWO, THREE, FOUR, FIVE, SIX -> runningCount++;
          case TEN, JACK, QUEEN, KING, ACE -> runningCount--;
          default -> runningCount += 0;
        }
        int trueCount;
        if (runningCount < rules.getNumberOfDecks()) {
          trueCount = (int) Math.floor((double) runningCount / (cardInDeck - cardNum) * CARDS_PER_DECK);
        } else {
          trueCount = (int) Math.round((double) runningCount / (cardInDeck - cardNum) * CARDS_PER_DECK);
        }
        if (trueCount > 20) {
          trueCount = 20;
        } else if (trueCount < -20) {
          trueCount = -20;
        }
        trueCountCount.set(trueCount + 20, trueCountCount.get(trueCount + 20) + 1);
      }
    }

    ArrayList<Double> trueCountDistribution = new ArrayList<>(41);
    for (int i = 0; i < 41; i++) {
      long count = trueCountCount.get(i);
      trueCountDistribution.add((double) count / (numberOfShoes * cardInDeck));
    }


    return trueCountDistribution;
  }

  /*
    1,000,000 Shoes, 6 decks, 52 CO
    True Count -10: 0.00448
    True Count  -9: 0.00296
    True Count  -8: 0.00496
    True Count  -7: 0.00780
    True Count  -6: 0.01271
    True Count  -5: 0.02012
    True Count  -4: 0.03392
    True Count  -3: 0.05606
    True Count  -2: 0.09746
    True Count  -1: 0.14821
    True Count   0: 0.20361
    True Count   1: 0.07320
    True Count   2: 0.06241
    True Count   3: 0.03951
    True Count   4: 0.02467
    True Count   5: 0.01534
    True Count   6: 0.00974
    True Count   7: 0.00647
    True Count   8: 0.00383
    True Count   9: 0.00240
    True Count  10: 0.00347
   */
}
