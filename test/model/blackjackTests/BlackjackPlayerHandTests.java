package model.blackjackTests;

import model.games.twentyOne.blackjack.BlackjackPlayerHand;
import model.games.twentyOne.blackjack.Rules.*;

import model.cards.Card;
import model.cards.SuitlessCard;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class BlackjackPlayerHandTests {

  Card ace = new SuitlessCard(Card.RANK.ACE);
  Card ten = new SuitlessCard(Card.RANK.TEN);
  Card nine = new SuitlessCard(Card.RANK.NINE);
  Card two = new SuitlessCard(Card.RANK.TWO);
  Card six = new SuitlessCard(Card.RANK.SIX);

  @Test
  void testConstructorStoresInitialHand() {
    BlackjackPlayerHand hand = new BlackjackPlayerHand(List.of(ace, ten));
    assertEquals(1, hand.getHandSize());
    assertEquals(List.of(ace, ten), hand.getHand(0));
  }

  @Test
  void testCopyConstructorMakesDeepCopy() {
    BlackjackPlayerHand original = new BlackjackPlayerHand(List.of(nine, nine));
    BlackjackPlayerHand copy = new BlackjackPlayerHand(original);

    assertNotSame(original.getHand(0), copy.getHand(0));
    assertEquals(original.getHand(0), copy.getHand(0));
  }

  @Test
  void testHitAddsCardToCorrectHand() {
    BlackjackPlayerHand hand = new BlackjackPlayerHand(List.of(ace, two));
    hand.hit(ten, 0);
    assertEquals(3, hand.getHand(0).size());
    assertEquals(ten, hand.getHand(0).get(2));
  }

  @Test
  void testHitInvalidHandIndexThrows() {
    BlackjackPlayerHand hand = new BlackjackPlayerHand(List.of(two, two));
    assertThrows(IndexOutOfBoundsException.class, () -> hand.hit(ten, 5));
  }

  @Test
  void testSplitIncreasesHandCount() {
    BlackjackPlayerHand hand = new BlackjackPlayerHand(List.of(nine, nine));
    hand.splitCards(4, RESPLIT_ACES_RULE.RESPLIT_ACES_MAX_SPLIT_TIMES, 0);
    assertEquals(2, hand.getHandSize());
    assertEquals(1, hand.getSplitCount());
  }

  @Test
  void testSplitThrowsWhenInvalid() {
    BlackjackPlayerHand hand = new BlackjackPlayerHand(List.of(nine, ten));
    assertThrows(IllegalArgumentException.class, () ->
        hand.splitCards(4, RESPLIT_ACES_RULE.NO_RESPLIT_ACES, 0));
  }

  @Test
  void testCanSplitHandlesAcesAndRulesCorrectly() {
    BlackjackPlayerHand hand = new BlackjackPlayerHand(List.of(ace, ace));

    assertFalse(hand.canSplit(1, RESPLIT_ACES_RULE.NO_RESPLIT_ACES, 0));
    assertTrue(hand.canSplit(1, RESPLIT_ACES_RULE.RESPLIT_ACES_ONCE, 0));
    assertTrue(hand.canSplit(4, RESPLIT_ACES_RULE.RESPLIT_ACES_MAX_SPLIT_TIMES, 0));
  }

  @Test
  void testCanDoubleObeysDoubleRules() {
    BlackjackPlayerHand hand = new BlackjackPlayerHand(List.of(nine, two));

    assertTrue(hand.canDouble(DOUBLE_RULE.ANY_TWO_CARDS, DOUBLE_AFTER_SPLIT_RULE.DOUBLE_AFTER_SPLIT_ALLOWED, 0));
    assertTrue(hand.canDouble(DOUBLE_RULE.NINE_TEN_ELEVEN, DOUBLE_AFTER_SPLIT_RULE.DOUBLE_AFTER_SPLIT_ALLOWED, 0));
    assertTrue(hand.canDouble(DOUBLE_RULE.TEN_ELEVEN, DOUBLE_AFTER_SPLIT_RULE.DOUBLE_AFTER_SPLIT_ALLOWED, 0));
    assertTrue(hand.canDouble(DOUBLE_RULE.ELEVEN_ONLY, DOUBLE_AFTER_SPLIT_RULE.DOUBLE_AFTER_SPLIT_ALLOWED, 0));

    hand = new BlackjackPlayerHand(List.of(nine, six));

    assertTrue(hand.canDouble(DOUBLE_RULE.ANY_TWO_CARDS, DOUBLE_AFTER_SPLIT_RULE.DOUBLE_AFTER_SPLIT_ALLOWED, 0));
    assertFalse(hand.canDouble(DOUBLE_RULE.NINE_TEN_ELEVEN, DOUBLE_AFTER_SPLIT_RULE.DOUBLE_AFTER_SPLIT_ALLOWED, 0));
    assertFalse(hand.canDouble(DOUBLE_RULE.TEN_ELEVEN, DOUBLE_AFTER_SPLIT_RULE.DOUBLE_AFTER_SPLIT_ALLOWED, 0));
    assertFalse(hand.canDouble(DOUBLE_RULE.ELEVEN_ONLY, DOUBLE_AFTER_SPLIT_RULE.DOUBLE_AFTER_SPLIT_ALLOWED, 0));
  }

  @Test
  void testDoubleIncrementsDoubleCount() {
    BlackjackPlayerHand hand = new BlackjackPlayerHand(List.of(nine, two));
    hand.doubleHand(DOUBLE_RULE.NINE_TEN_ELEVEN, DOUBLE_AFTER_SPLIT_RULE.DOUBLE_AFTER_SPLIT_ALLOWED, 0);
    assertEquals(1, hand.getDoubleCount());
  }

  @Test
  void testDoubleThrowsIfNotAllowed() {
    BlackjackPlayerHand hand = new BlackjackPlayerHand(List.of(nine, six));
    assertThrows(IllegalArgumentException.class, () ->
        hand.doubleHand(DOUBLE_RULE.ELEVEN_ONLY, DOUBLE_AFTER_SPLIT_RULE.DOUBLE_AFTER_SPLIT_ALLOWED, 0));
  }

  @Test
  void testGetHandSumWithSoftAce() {
    BlackjackPlayerHand hand = new BlackjackPlayerHand(List.of(ace, six));
    assertEquals(17, hand.getHandSum(0));

    hand.hit(ten, 0); // now should demote ace from 11 to 1
    assertEquals(17, hand.getHandSum(0));
  }

  @Test
  void testIsBlackjackTrue() {
    BlackjackPlayerHand hand = new BlackjackPlayerHand(List.of(ace, ten));
    assertTrue(hand.isBlackjack(0));
  }

  @Test
  void testIsBlackjackFalse() {
    BlackjackPlayerHand hand = new BlackjackPlayerHand(List.of(nine, two));
    assertFalse(hand.isBlackjack(0));
  }
}
