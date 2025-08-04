package model.blackjackTests;

import model.games.twentyOne.blackjack.BlackjackRules;
import model.games.twentyOne.blackjack.Rules;
import model.games.twentyOne.blackjack.Rules.*;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

public class BlackjackRulesTests {

  @Test
  void testAllGettersReturnCorrectValues() {
    // Arrange
    BigDecimal expectedPayout = new BigDecimal("1.5");
    int expectedDecks = 6;
    int expectedPenetration = 52;
    int expectedSplits = 4;

    DOUBLE_RULE doubleRule = DOUBLE_RULE.ANY_TWO_CARDS;
    DOUBLE_AFTER_SPLIT_RULE dasRule = DOUBLE_AFTER_SPLIT_RULE.DOUBLE_AFTER_SPLIT_ALLOWED;
    RESPLIT_ACES_RULE rsaRule = RESPLIT_ACES_RULE.RESPLIT_ACES_MAX_SPLIT_TIMES;
    DEALER_ACTION_SOFT_SEVENTEEN standRule = DEALER_ACTION_SOFT_SEVENTEEN.STAND_ON_SOFT_17;
    SURRENDER_RULE surrenderRule = SURRENDER_RULE.EARLY_SURRENDER;
    HOLE_CARD_RULE holeCardRule = HOLE_CARD_RULE.AMERICAN_STYLE;

    BlackjackRules rules = new BlackjackRules(
        expectedPayout,
        expectedDecks,
        expectedPenetration,
        expectedSplits,
        doubleRule,
        dasRule,
        rsaRule,
        standRule,
        surrenderRule,
        holeCardRule
    );

    // Act & Assert
    assertEquals(expectedPayout, rules.getBlackjackPayout(), "Blackjack payout mismatch");
    assertEquals(expectedDecks, rules.getNumberOfDecks(), "Number of decks mismatch");
    assertEquals(expectedPenetration, rules.getDeckPenetration(), "Deck penetration mismatch");
    assertEquals(expectedSplits, rules.getMaxSplits(), "Max splits mismatch");

    assertEquals(doubleRule, rules.getDoubleRule(), "Double rule mismatch");
    assertEquals(dasRule, rules.getDoubleAfterSplitRule(), "DAS rule mismatch");
    assertEquals(rsaRule, rules.getResplitAcesRule(), "RSA rule mismatch");
    assertEquals(standRule, rules.getStandRule(), "Dealer stand rule mismatch");
    assertEquals(surrenderRule, rules.getSurrenderRule(), "Surrender rule mismatch");
    assertEquals(holeCardRule, rules.getHoleCardRule(), "Hole card rule mismatch");
  }

  @Test
  void testDifferentRuleConfigurationsAreStoredSeparately() {
    Rules r1 = new BlackjackRules(
        new BigDecimal("1.5"),
        6,
        52,
        3,
        DOUBLE_RULE.ANY_TWO_CARDS,
        DOUBLE_AFTER_SPLIT_RULE.DOUBLE_AFTER_SPLIT_ALLOWED,
        RESPLIT_ACES_RULE.NO_RESPLIT_ACES,
        DEALER_ACTION_SOFT_SEVENTEEN.HIT_SOFT_17,
        SURRENDER_RULE.NO_SURRENDER,
        HOLE_CARD_RULE.EUROPEAN_STYLE
    );

    Rules r2 = new BlackjackRules(
        new BigDecimal("1.2"),
        1,
        26,
        1,
        DOUBLE_RULE.TEN_ELEVEN,
        DOUBLE_AFTER_SPLIT_RULE.DOUBLE_AFTER_SPLIT_ALLOWED,
        RESPLIT_ACES_RULE.RESPLIT_ACES_ONCE,
        DEALER_ACTION_SOFT_SEVENTEEN.STAND_ON_SOFT_17,
        SURRENDER_RULE.LATE_SURRENDER,
        HOLE_CARD_RULE.AMERICAN_STYLE
    );

    assertNotEquals(r1.getBlackjackPayout(), r2.getBlackjackPayout());
    assertNotEquals(r1.getNumberOfDecks(), r2.getNumberOfDecks());
    assertNotEquals(r1.getDoubleRule(), r2.getDoubleRule());
    assertNotEquals(r1.getHoleCardRule(), r2.getHoleCardRule());
  }

  private String toString(BlackjackRules rules) {
    return rules.toString(); // use actual toString implementation
  }

  @Test
  void testBasicToStringFormat1() {
    BlackjackRules rules = new BlackjackRules(
        new BigDecimal("1.5"),
        6,
        75,
        3,
        DOUBLE_RULE.ANY_TWO_CARDS,
        DOUBLE_AFTER_SPLIT_RULE.DOUBLE_AFTER_SPLIT_ALLOWED,
        RESPLIT_ACES_RULE.RESPLIT_ACES_MAX_SPLIT_TIMES,
        DEALER_ACTION_SOFT_SEVENTEEN.STAND_ON_SOFT_17,
        SURRENDER_RULE.EARLY_SURRENDER,
        HOLE_CARD_RULE.AMERICAN_STYLE
    );

    assertEquals("BJ-1.5_6D_75_3_DATC_DAS_RSA_S17_EARLY-S_American", toString(rules));
  }

  @Test
  void testDifferentDoubleRuleMappings() {
    BlackjackRules rulesDTE = new BlackjackRules(
        new BigDecimal("1.2"), 2, 50, 4,
        DOUBLE_RULE.TEN_ELEVEN,
        DOUBLE_AFTER_SPLIT_RULE.DOUBLE_AFTER_SPLIT_FORBIDDEN,
        RESPLIT_ACES_RULE.NO_RESPLIT_ACES,
        DEALER_ACTION_SOFT_SEVENTEEN.HIT_SOFT_17,
        SURRENDER_RULE.LATE_SURRENDER,
        HOLE_CARD_RULE.AMERICAN_STYLE
    );
    assertEquals("BJ-1.2_2D_50_4_DTE_NDAS_NRSA_H17_Late-S_American", toString(rulesDTE));

    BlackjackRules rulesDNTE = new BlackjackRules(
        new BigDecimal("1.3"), 4, 90, 2,
        DOUBLE_RULE.NINE_TEN_ELEVEN,
        DOUBLE_AFTER_SPLIT_RULE.DOUBLE_AFTER_SPLIT_FORBIDDEN,
        RESPLIT_ACES_RULE.NO_RESPLIT_ACES,
        DEALER_ACTION_SOFT_SEVENTEEN.HIT_SOFT_17,
        SURRENDER_RULE.NO_SURRENDER,
        HOLE_CARD_RULE.EUROPEAN_STYLE
    );
    assertEquals("BJ-1.3_4D_90_2_DNTE_NDAS_NRSA_H17_No-S_European", toString(rulesDNTE));

    BlackjackRules rulesDE = new BlackjackRules(
        new BigDecimal("1.4"), 1, 80, 1,
        DOUBLE_RULE.ELEVEN_ONLY,
        DOUBLE_AFTER_SPLIT_RULE.DOUBLE_AFTER_SPLIT_ALLOWED,
        RESPLIT_ACES_RULE.NO_RESPLIT_ACES,
        DEALER_ACTION_SOFT_SEVENTEEN.STAND_ON_SOFT_17,
        SURRENDER_RULE.NO_SURRENDER,
        HOLE_CARD_RULE.AMERICAN_STYLE
    );
    assertEquals("BJ-1.4_1D_80_1_DE_DAS_NRSA_S17_No-S_American", toString(rulesDE));
  }

  @Test
  void testDASvsNDAS() {
    BlackjackRules dasRules = new BlackjackRules(
        new BigDecimal("2.0"), 8, 100, 3,
        DOUBLE_RULE.ANY_TWO_CARDS,
        DOUBLE_AFTER_SPLIT_RULE.DOUBLE_AFTER_SPLIT_ALLOWED,
        RESPLIT_ACES_RULE.RESPLIT_ACES_MAX_SPLIT_TIMES,
        DEALER_ACTION_SOFT_SEVENTEEN.STAND_ON_SOFT_17,
        SURRENDER_RULE.LATE_SURRENDER,
        HOLE_CARD_RULE.EUROPEAN_STYLE
    );
    assertTrue(toString(dasRules).contains("DAS"));

    BlackjackRules ndasRules = new BlackjackRules(
        new BigDecimal("2.0"), 8, 100, 3,
        DOUBLE_RULE.ANY_TWO_CARDS,
        DOUBLE_AFTER_SPLIT_RULE.DOUBLE_AFTER_SPLIT_FORBIDDEN,
        RESPLIT_ACES_RULE.RESPLIT_ACES_MAX_SPLIT_TIMES,
        DEALER_ACTION_SOFT_SEVENTEEN.STAND_ON_SOFT_17,
        SURRENDER_RULE.LATE_SURRENDER,
        HOLE_CARD_RULE.EUROPEAN_STYLE
    );
    assertTrue(toString(ndasRules).contains("NDAS"));
  }

  @Test
  void testRSAEncodingVariants() {
    BlackjackRules nrsa = new BlackjackRules(
        new BigDecimal("1.5"), 6, 75, 3,
        DOUBLE_RULE.ANY_TWO_CARDS,
        DOUBLE_AFTER_SPLIT_RULE.DOUBLE_AFTER_SPLIT_ALLOWED,
        RESPLIT_ACES_RULE.NO_RESPLIT_ACES,
        DEALER_ACTION_SOFT_SEVENTEEN.STAND_ON_SOFT_17,
        SURRENDER_RULE.NO_SURRENDER,
        HOLE_CARD_RULE.AMERICAN_STYLE
    );
    assertTrue(toString(nrsa).contains("NRSA"));

    BlackjackRules rsa1 = new BlackjackRules(
        new BigDecimal("1.5"), 6, 75, 3,
        DOUBLE_RULE.ANY_TWO_CARDS,
        DOUBLE_AFTER_SPLIT_RULE.DOUBLE_AFTER_SPLIT_ALLOWED,
        RESPLIT_ACES_RULE.RESPLIT_ACES_ONCE,
        DEALER_ACTION_SOFT_SEVENTEEN.STAND_ON_SOFT_17,
        SURRENDER_RULE.NO_SURRENDER,
        HOLE_CARD_RULE.AMERICAN_STYLE
    );
    assertTrue(toString(rsa1).contains("RSA1"));

    BlackjackRules rsaMax = new BlackjackRules(
        new BigDecimal("1.5"), 6, 75, 3,
        DOUBLE_RULE.ANY_TWO_CARDS,
        DOUBLE_AFTER_SPLIT_RULE.DOUBLE_AFTER_SPLIT_ALLOWED,
        RESPLIT_ACES_RULE.RESPLIT_ACES_MAX_SPLIT_TIMES,
        DEALER_ACTION_SOFT_SEVENTEEN.STAND_ON_SOFT_17,
        SURRENDER_RULE.NO_SURRENDER,
        HOLE_CARD_RULE.AMERICAN_STYLE
    );
    assertTrue(toString(rsaMax).contains("RSA"));
  }

  @Test
  void testSurrenderAndHoleCardRules() {
    BlackjackRules rule = new BlackjackRules(
        new BigDecimal("1.0"), 1, 20, 1,
        DOUBLE_RULE.ANY_TWO_CARDS,
        DOUBLE_AFTER_SPLIT_RULE.DOUBLE_AFTER_SPLIT_ALLOWED,
        RESPLIT_ACES_RULE.NO_RESPLIT_ACES,
        DEALER_ACTION_SOFT_SEVENTEEN.STAND_ON_SOFT_17,
        SURRENDER_RULE.NO_SURRENDER,
        HOLE_CARD_RULE.EUROPEAN_STYLE
    );
    assertTrue(toString(rule).endsWith("No-S_European"));

    BlackjackRules rule2 = new BlackjackRules(
        new BigDecimal("1.0"), 1, 20, 1,
        DOUBLE_RULE.ANY_TWO_CARDS,
        DOUBLE_AFTER_SPLIT_RULE.DOUBLE_AFTER_SPLIT_ALLOWED,
        RESPLIT_ACES_RULE.NO_RESPLIT_ACES,
        DEALER_ACTION_SOFT_SEVENTEEN.STAND_ON_SOFT_17,
        SURRENDER_RULE.EARLY_SURRENDER,
        HOLE_CARD_RULE.AMERICAN_STYLE
    );
    assertTrue(toString(rule2).endsWith("EARLY-S_American"));
  }
}
