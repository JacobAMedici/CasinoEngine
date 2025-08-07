package model.games.twentyOne.blackjack;

import model.cards.Deck;
import model.cards.SuitlessCard;
import model.cards.SuitlessDeck;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * A collection of helper methods for card counting in Blackjack.
 */
public class CardCountingHelpers {

  /**
   * Main method to demonstrate the true count distribution calculation.
   *
   * @param ignored Command line arguments (not used).
   */
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
      System.out.printf("True Count %4d: %.6f%n", i - 15, distribution.get(i));
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
    int MAX_TC = 15;
    // MIN_TC must be negative
    int MIN_TC = -15;
    int arraySize = MAX_TC - MIN_TC + 1;

    int CARDS_IN_DECK = 52;
    int cardInShoe = CARDS_IN_DECK * rules.getNumberOfDecks();
    ArrayList<Long> trueCountCount = new ArrayList<>(arraySize);
    for (int i = 0; i < arraySize; i++) {
      trueCountCount.add(0L);
    }

    for (int shoeCount = 0; shoeCount < numberOfShoes; shoeCount++) {
      Deck<SuitlessCard> deck = new SuitlessDeck(rules.getNumberOfDecks());
      deck.shuffle();
      while (deck.getDeck().size() > rules.getDeckPenetration()) {
        deck.dealCard();
        int trueCount = deck.getTrueCount();
        if (trueCount > MAX_TC) {
          trueCount = MAX_TC;
        } else if (trueCount < MIN_TC) {
          trueCount = MIN_TC;
        }
        trueCountCount.set(trueCount - MIN_TC, trueCountCount.get(trueCount - MIN_TC) + 1);
      }
    }

    ArrayList<Double> trueCountDistribution = new ArrayList<>(arraySize);
    for (int index = 0; index < arraySize; index++) {
      long count = trueCountCount.get(index);
      trueCountDistribution.add((double) count / (numberOfShoes * (cardInShoe - rules.getDeckPenetration())));
    }

    return trueCountDistribution;
  }

  /*
  True Count  -15: 0.000298
  True Count  -14: 0.000208
  True Count  -13: 0.000414
  True Count  -12: 0.000692
  True Count  -11: 0.001153
  True Count  -10: 0.001854
  True Count   -9: 0.002995
  True Count   -8: 0.005062
  True Count   -7: 0.007918
  True Count   -6: 0.012804
  True Count   -5: 0.020144
  True Count   -4: 0.033733
  True Count   -3: 0.056043
  True Count   -2: 0.097570
  True Count   -1: 0.148064
  True Count    0: 0.203137
  True Count    1: 0.096553
  True Count    2: 0.056694
  True Count    3: 0.033475
  True Count    4: 0.021020
  True Count    5: 0.012722
  True Count    6: 0.007990
  True Count    7: 0.004911
  True Count    8: 0.003169
  True Count    9: 0.001880
  True Count   10: 0.001178
  True Count   11: 0.000683
  True Count   12: 0.000397
  True Count   13: 0.000267
  True Count   14: 0.000149
  True Count   15: 0.000156
   */
}
