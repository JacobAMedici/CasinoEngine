package model.games.twentyOne.blackjack;

import model.cards.Card;
import model.cards.SuitlessCard;

import static model.cards.Card.RANK.ACE;

/**
 * Represents a basic Blackjack playing strategy that selects the best action based on maximizing expected value (EV).
 */
public class BasicStrategy implements BlackjackStrategy {

  /**
   * Represents the possible actions a player can take during a Blackjack hand.
   */
  public enum ACTION {
    HIT,
    STAND,
    DOUBLE_DOWN,
    SPLIT,
    SURRENDER
  }

  /**
   * Represents the result of choosing a Blackjack action, including both the action and its
   * expected value.
   */
  public record ActionAndEV(ACTION action, double ev) {
  }

  final private int totalNumOfCards;
  private final Rules rules;

  /**
   * Constructs a BasicStrategy instance based on the given Blackjack rules.
   *
   * @param rules the set of Blackjack rules to use for strategy decisions
   */
  public BasicStrategy(Rules rules) {
    this.rules = rules;
    int CARDS_IN_DECK = 52;
    this.totalNumOfCards = rules.getNumberOfDecks() * CARDS_IN_DECK;
  }

  /**
   * Chooses the best action for the player based on the dealer's hand and player's hand
   *
   * @param dealerHand the dealer's hand
   * @param playerHand the player's hand
   * @param handIndex  the index of the player's hand (in case of multiple hands)
   * @return the best action and its expected value
   */
  @Override
  public ActionAndEV chooseAction(BlackjackDealerHand dealerHand,
                                  BlackjackPlayerHand playerHand,
                                  int handIndex) {

    if (playerHand.getHandSum(handIndex) > 21) {
      return new ActionAndEV(ACTION.STAND, -1.0);
    }

    Double hitEV = hitEV(dealerHand, playerHand, handIndex);
    Double standEV = standEV(dealerHand, playerHand, handIndex);
    Double doubleEV = doubleEV(dealerHand, playerHand, handIndex);
    Double splitEV = splitEV(dealerHand, playerHand, handIndex);
    Double surrenderEV = surrenderEV(dealerHand, playerHand, handIndex);

    /*
    if (playerHand.getHandSize() == 1 && playerHand.getHand(0).size() == 2) {
      System.out.println("Hit EV: " + hitEV);
      System.out.println("Stand EV: " + standEV);
      System.out.println("Double EV: " + doubleEV);
      System.out.println("Split EV: " + splitEV);
      System.out.println("Surrender EV: " + surrenderEV);
    }
    */

    ACTION bestAction = ACTION.STAND;
    Double actionEV = standEV;

    if (hitEV != null && hitEV > actionEV) {
      bestAction = ACTION.HIT;
      actionEV = hitEV;
    }

    if (doubleEV != null && doubleEV > actionEV) {
      bestAction = ACTION.DOUBLE_DOWN;
      actionEV = doubleEV;
    }

    if (splitEV != null && splitEV > actionEV) {
      bestAction = ACTION.SPLIT;
      actionEV = splitEV;
    }

    if (surrenderEV != null && surrenderEV > actionEV) {
      bestAction = ACTION.SURRENDER;
      actionEV = surrenderEV;
    }

    // Eventually I want to return the EV of each choice
    return new ActionAndEV(bestAction, actionEV);
  }

  private Double hitEV(BlackjackDealerHand dealerHand,
                       BlackjackPlayerHand playerHand,
                       int handIndex) {
    if (playerHand.getHandSum(handIndex) >= 21
        // This is so you can't hit split aces
        || (playerHand.getHand(handIndex).getFirst().rank() == ACE
        && playerHand.getSplitCount() != 0)) {
      return null;
    }
    return drawOneCardEV(dealerHand, playerHand, handIndex, false);
  }

  private Double standEV(BlackjackDealerHand dealerHand,
                         BlackjackPlayerHand playerHand,
                         int handIndex) {
    // What this does, is it considers if the dealer had checked for blackjack and normalizes
    // splits and doubles for the entire thing to just lose one single bet
    if (rules.getHoleCardRule() == Rules.HOLE_CARD_RULE.AMERICAN_STYLE
        && dealerHand.isBlackjack()) {
      if (playerHand.isBlackjack(handIndex)) {
        return 0.0;
      }
      int totalNumberOfBets = 1 + playerHand.getSplitCount() + playerHand.getDoubleCount();
      return -1.0 / totalNumberOfBets;
    }

    // Check if player has Blackjack
    if (playerHand.getCards().get(handIndex).size() == 2
        && playerHand.isBlackjack(handIndex)
        && playerHand.getSplitCount() == 0) {
      return rules.getBlackjackPayout().doubleValue();
    }

    boolean dealerShouldStand = switch (rules.getStandRule()) {
      case STAND_ON_SOFT_17 -> dealerHand.getHandSum() >= 17;
      case HIT_SOFT_17 -> dealerHand.getHandSum() > 17
          || ((dealerHand.getHandSum() == 17 && !dealerHand.isSoft()));
    };

    if (dealerShouldStand) {
      int playerTotal = playerHand.getHandSum(handIndex);
      int dealerTotal = dealerHand.getHandSum();

      if (playerTotal > 21) {
        return -1.0;
      }
      if (dealerTotal > 21) {
        return 1.0;
      }
      return (double) Double.compare(playerTotal, dealerTotal);
    }

    double standEV = 0.0;

    for (Card.RANK rank : Card.RANK.values()) {
      double probability = getCardProbability(dealerHand, playerHand, handIndex, rank);

      BlackjackDealerHand newDealerHand = new BlackjackDealerHand(dealerHand);
      newDealerHand.hit(new SuitlessCard(rank));

      standEV += probability * standEV(newDealerHand, playerHand, handIndex);
    }

    return standEV;
  }

  private Double doubleEV(BlackjackDealerHand dealerHand,
                          BlackjackPlayerHand playerHand,
                          int handIndex) {
    // Check if the player can double down
    if (!playerHand.canDouble(rules.getDoubleRule(), rules.getDoubleAfterSplitRule(), handIndex)) {
      return null;
    }

    playerHand.doubleHand(rules.getDoubleRule(), rules.getDoubleAfterSplitRule(), handIndex);

    // We just want the combos and corresponding probabilities
    return 2 * drawOneCardEV(dealerHand, playerHand, handIndex, true);
  }

  private Double splitEV(BlackjackDealerHand dealerHand,
                         BlackjackPlayerHand playerHand,
                         int handIndex) {
    if (!playerHand.canSplit(rules.getMaxSplits(), rules.getResplitAcesRule(), handIndex)) {
      return null;
    } else {
      BlackjackPlayerHand playerHandCopy = new BlackjackPlayerHand(playerHand);

      playerHandCopy.splitCards(rules.getMaxSplits(), rules.getResplitAcesRule(), handIndex);

      // Get EV for the two hands after splitting
      return drawOneCardEV(dealerHand, playerHandCopy, handIndex, false)
          + drawOneCardEV(dealerHand, playerHandCopy, playerHandCopy.getHandSize() - 1, false);
    }
  }

  private Double surrenderEV(BlackjackDealerHand dealerHand,
                             BlackjackPlayerHand playerHand,
                             int handIndex) {
    if (rules.getSurrenderRule().equals(Rules.SURRENDER_RULE.NO_SURRENDER)) {
      return null;
    }
    if (playerHand.getHand(handIndex).size() != 2) {
      return null;
    }
    return -0.5;
  }

  private double drawOneCardEV(BlackjackDealerHand dealerHand,
                               BlackjackPlayerHand playerHand,
                               int handIndex,
                               boolean standAfterDrawing) {
    double standAfterDrawingEV = 0.0;
    double recurseAfterDrawingEV = 0.0;
    for (Card.RANK rank : Card.RANK.values()) {
      double probability = getCardProbability(dealerHand, playerHand, handIndex, rank);
      BlackjackPlayerHand playerHandCopy = new BlackjackPlayerHand(playerHand);

      playerHandCopy.hit(new SuitlessCard(rank), handIndex);
      if (standAfterDrawing) {
        standAfterDrawingEV += probability * standEV(dealerHand, playerHandCopy, handIndex);
      } else {
        recurseAfterDrawingEV += probability * chooseAction(dealerHand, playerHandCopy, handIndex).ev();
      }
    }
    if (standAfterDrawing) {
      return standAfterDrawingEV;
    } else {
      return recurseAfterDrawingEV;
    }
  }

  public double getCardProbability(BlackjackDealerHand dealerHand,
                                    BlackjackPlayerHand playerHand,
                                    int handIndex,
                                    Card.RANK rank) {
    int usedCards = 0;

    // This is just a double for type purposes
    double EACH_RANK_PER_DECK = 4.0;
    int numOfRank = 0;
    for (int cardIndex = 0; cardIndex < playerHand.getHand(handIndex).size(); cardIndex++) {
      usedCards++;
      if (playerHand.getHand(handIndex).get(cardIndex).rank() == rank) {
        numOfRank++;
      }
    }
    for (int cardIndex = 0; cardIndex < dealerHand.getCards().size(); cardIndex++) {
      usedCards++;
      if (dealerHand.getCards().get(cardIndex).rank() == rank) {
        numOfRank++;
      }
    }
    return (EACH_RANK_PER_DECK * rules.getNumberOfDecks() - numOfRank)
        / (totalNumOfCards - usedCards);
  }
}