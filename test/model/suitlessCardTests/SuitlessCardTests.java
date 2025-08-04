package model.suitlessCardTests;

import model.cards.Card.RANK;
import model.cards.SuitlessCard;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class SuitlessCardTests {

  @Test
  void testRankIsStoredCorrectly() {
    SuitlessCard card = new SuitlessCard(RANK.QUEEN);
    assertEquals(RANK.QUEEN, card.rank());
  }

  @Test
  void testStandardNumericValueMapping() {
    assertEquals(2, SuitlessCard.rankToNum.get(RANK.TWO));
    assertEquals(11, SuitlessCard.rankToNum.get(RANK.JACK));
    assertEquals(14, SuitlessCard.rankToNum.get(RANK.ACE));
  }

  @Test
  void testBlackjackNumericValueMapping() {
    assertEquals(10, SuitlessCard.rankToBjNum.get(RANK.KING));
    assertEquals(11, SuitlessCard.rankToBjNum.get(RANK.ACE));
    assertEquals(3, SuitlessCard.rankToBjNum.get(RANK.THREE));
  }

  @Test
  void testCompareToIsCorrect() {
    SuitlessCard two = new SuitlessCard(RANK.TWO);
    SuitlessCard five = new SuitlessCard(RANK.FIVE);
    SuitlessCard ace = new SuitlessCard(RANK.ACE);

    assertTrue(two.compareTo(five) < 0);
    assertTrue(five.compareTo(two) > 0);
    assertEquals(0, ace.compareTo(new SuitlessCard(RANK.ACE)));
  }

  @Test
  void testEqualsSameRank() {
    SuitlessCard a = new SuitlessCard(RANK.TEN);
    SuitlessCard b = new SuitlessCard(RANK.TEN);
    assertEquals(a, b);
  }

  @Test
  void testNotEqualsDifferentRank() {
    SuitlessCard a = new SuitlessCard(RANK.TEN);
    SuitlessCard b = new SuitlessCard(RANK.JACK);
    assertNotEquals(a, b);
  }

  @Test
  void testNotEqualsNull() {
    SuitlessCard a = new SuitlessCard(RANK.TEN);
    assertNotEquals(a, null);
  }

  @Test
  void testNotEqualsDifferentObjectType() {
    SuitlessCard a = new SuitlessCard(RANK.TEN);
    assertNotEquals(a, "TEN");
  }
}
