package model.blackjackTests;

import model.cards.Card;
import model.cards.SuitlessCard;
import model.games.twentyOne.blackjack.*;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BasicStrategyTests {
  @Test
  public void testHardBasicStrategy_H17_6D_DAS_ES_AMERICAN() {
    // 3:2, 6 decks, 52 CO, 4 splits, double any two cards, DAS, RSA, H17, ES, American
    BlackjackStrategy H17_6D_DAS_ES_AMERICAN = get_H17_6D_DAS_ES_AMERICAN();

    // 15 vs 6
    BlackjackPlayerHand playerHand = new BlackjackPlayerHand(new ArrayList<>(List.of(
        new SuitlessCard(Card.RANK.TEN),
        new SuitlessCard(Card.RANK.FIVE)
    )));
    BlackjackDealerHand dealerHand = new BlackjackDealerHand(new ArrayList<>(List.of(
        new SuitlessCard(Card.RANK.SIX)
    )));
    assertEquals(BasicStrategy.ACTION.STAND, H17_6D_DAS_ES_AMERICAN.chooseAction(dealerHand, playerHand, 0).action());

    // 21 (BJ) vs 6
    playerHand = new BlackjackPlayerHand(new ArrayList<>(List.of(
        new SuitlessCard(Card.RANK.TEN),
        new SuitlessCard(Card.RANK.ACE)
    )));
    dealerHand = new BlackjackDealerHand(new ArrayList<>(List.of(
        new SuitlessCard(Card.RANK.TWO)
    )));
    assertEquals(BasicStrategy.ACTION.STAND, H17_6D_DAS_ES_AMERICAN.chooseAction(dealerHand, playerHand, 0).action());

    // 16 vs T
    playerHand = new BlackjackPlayerHand(new ArrayList<>(List.of(
        new SuitlessCard(Card.RANK.SIX),
        new SuitlessCard(Card.RANK.TEN)
    )));
    dealerHand = new BlackjackDealerHand(new ArrayList<>(List.of(
        new SuitlessCard(Card.RANK.TEN)
    )));
    assertEquals(BasicStrategy.ACTION.SURRENDER, H17_6D_DAS_ES_AMERICAN.chooseAction(dealerHand, playerHand, 0).action());

    // 11 vs 5
    playerHand = new BlackjackPlayerHand(new ArrayList<>(List.of(
        new SuitlessCard(Card.RANK.FOUR),
        new SuitlessCard(Card.RANK.SEVEN)
    )));
    dealerHand = new BlackjackDealerHand(new ArrayList<>(List.of(
        new SuitlessCard(Card.RANK.FIVE)
    )));
    assertEquals(BasicStrategy.ACTION.DOUBLE_DOWN, H17_6D_DAS_ES_AMERICAN.chooseAction(dealerHand, playerHand, 0).action());
  }

  @Test
  public void testHardBasicStrategy_S17_6D_DAS_ES_AMERICAN() {
    // 3:2, 6 decks, 52 CO, 4 splits, double any two cards, DAS, RSA, S17, ES, American
    BlackjackStrategy S17_6D_NDAS_ES_AMERICAN = get_S17_6D_NDAS_ES_AMERICAN();
  }

  @Test
  public void testHardBasicStrategy_S17_6D_NDAS_ES_AMERICAN() {
    // 3:2, 6 decks, 52 CO, 4 splits, double any two cards, NDAS, RSA, S17, ES, American
    BlackjackStrategy S17_6D_NDAS_ES_AMERICAN = get_S17_6D_NDAS_ES_AMERICAN();
  }

  // I just put a Z here, so it runs last because it takes so fucking long
  @Test
  public void testZSplitBasicStrategy() {
    // 3:2, 6 decks, 52 CO, 4 splits, double any two cards, DAS, RSA, S17, ES, American
    BlackjackStrategy H17_6D_DAS_ES_AMERICAN = get_H17_6D_DAS_ES_AMERICAN();
    BlackjackStrategy S17_6D_DAS_ES_AMERICAN = get_S17_6D_DAS_ES_AMERICAN();
    BlackjackStrategy S17_6D_NDAS_ES_AMERICAN = get_S17_6D_NDAS_ES_AMERICAN();

    // T, T vs T
    BlackjackPlayerHand playerHand = new BlackjackPlayerHand(new ArrayList<>(List.of(
        new SuitlessCard(Card.RANK.TEN),
        new SuitlessCard(Card.RANK.TEN)
    )));
    BlackjackDealerHand dealerHand = new BlackjackDealerHand(new ArrayList<>(List.of(
        new SuitlessCard(Card.RANK.TEN)
    )));
    assertEquals(BasicStrategy.ACTION.STAND, H17_6D_DAS_ES_AMERICAN.chooseAction(dealerHand, playerHand, 0).action());

    // T, T vs 6
    playerHand = new BlackjackPlayerHand(new ArrayList<>(List.of(
        new SuitlessCard(Card.RANK.TEN),
        new SuitlessCard(Card.RANK.TEN)
    )));
    dealerHand = new BlackjackDealerHand(new ArrayList<>(List.of(
        new SuitlessCard(Card.RANK.SIX)
    )));
    assertEquals(BasicStrategy.ACTION.STAND, H17_6D_DAS_ES_AMERICAN.chooseAction(dealerHand, playerHand, 0).action());

    // 5, 5 vs 6
    playerHand = new BlackjackPlayerHand(new ArrayList<>(List.of(
        new SuitlessCard(Card.RANK.FIVE),
        new SuitlessCard(Card.RANK.FIVE)
    )));
    dealerHand = new BlackjackDealerHand(new ArrayList<>(List.of(
        new SuitlessCard(Card.RANK.SIX)
    )));
    assertEquals(BasicStrategy.ACTION.DOUBLE_DOWN, H17_6D_DAS_ES_AMERICAN.chooseAction(dealerHand, playerHand, 0).action());

    // 5, 5 vs T
    playerHand = new BlackjackPlayerHand(new ArrayList<>(List.of(
        new SuitlessCard(Card.RANK.FIVE),
        new SuitlessCard(Card.RANK.FIVE)
    )));
    dealerHand = new BlackjackDealerHand(new ArrayList<>(List.of(
        new SuitlessCard(Card.RANK.TEN)
    )));
    assertEquals(BasicStrategy.ACTION.STAND, H17_6D_DAS_ES_AMERICAN.chooseAction(dealerHand, playerHand, 0).action());

    // 8, 8 vs 5
    playerHand = new BlackjackPlayerHand(new ArrayList<>(List.of(
        new SuitlessCard(Card.RANK.EIGHT),
        new SuitlessCard(Card.RANK.EIGHT)
    )));
    dealerHand = new BlackjackDealerHand(new ArrayList<>(List.of(
        new SuitlessCard(Card.RANK.FIVE)
    )));
    assertEquals(BasicStrategy.ACTION.SPLIT, H17_6D_DAS_ES_AMERICAN.chooseAction(dealerHand, playerHand, 0).action());
  }

  private static BlackjackStrategy get_H17_6D_DAS_ES_AMERICAN() {
    Rules rules = new BlackjackRules(
        new BigDecimal("1.5"),
        6,
        0,
        4,
        Rules.DOUBLE_RULE.ANY_TWO_CARDS,
        Rules.DOUBLE_AFTER_SPLIT_RULE.DOUBLE_AFTER_SPLIT_ALLOWED,
        Rules.RESPLIT_ACES_RULE.RESPLIT_ACES_MAX_SPLIT_TIMES,
        Rules.DEALER_ACTION_SOFT_SEVENTEEN.HIT_SOFT_17,
        Rules.SURRENDER_RULE.EARLY_SURRENDER,
        Rules.HOLE_CARD_RULE.AMERICAN_STYLE
    );
    return new BasicStrategy(rules);
  }

  private static BlackjackStrategy get_S17_6D_DAS_ES_AMERICAN() {
    Rules rules = new BlackjackRules(
        new BigDecimal("1.5"),
        6,
        0,
        4,
        Rules.DOUBLE_RULE.ANY_TWO_CARDS,
        Rules.DOUBLE_AFTER_SPLIT_RULE.DOUBLE_AFTER_SPLIT_ALLOWED,
        Rules.RESPLIT_ACES_RULE.RESPLIT_ACES_MAX_SPLIT_TIMES,
        Rules.DEALER_ACTION_SOFT_SEVENTEEN.STAND_ON_SOFT_17,
        Rules.SURRENDER_RULE.EARLY_SURRENDER,
        Rules.HOLE_CARD_RULE.AMERICAN_STYLE
    );
    return new BasicStrategy(rules);
  }

  private static BlackjackStrategy get_S17_6D_NDAS_ES_AMERICAN() {
    Rules rules = new BlackjackRules(
        new BigDecimal("1.5"),
        6,
        0,
        4,
        Rules.DOUBLE_RULE.ANY_TWO_CARDS,
        Rules.DOUBLE_AFTER_SPLIT_RULE.DOUBLE_AFTER_SPLIT_FORBIDDEN,
        Rules.RESPLIT_ACES_RULE.RESPLIT_ACES_MAX_SPLIT_TIMES,
        Rules.DEALER_ACTION_SOFT_SEVENTEEN.STAND_ON_SOFT_17,
        Rules.SURRENDER_RULE.EARLY_SURRENDER,
        Rules.HOLE_CARD_RULE.AMERICAN_STYLE
    );
    return new BasicStrategy(rules);
  }

}
