package model.games.poker.mississippiStudPoker;


import model.cards.Card;
import model.cards.SuitedCard;

import java.util.ArrayList;
import java.util.List;

public class MSP_Game {
    public static void main(String[] ignored) {
        MSP_Helpers MSP_Helper = new MSP_Helpers();
        System.out.println(MSP_Helper.calculateEV(new ArrayList<>(List.of(
            new SuitedCard(Card.RANK.KING, Card.SUIT.HEARTS),
            new SuitedCard(Card.RANK.QUEEN, Card.SUIT.SPADES),
            new SuitedCard(Card.RANK.TWO, Card.SUIT.HEARTS),
            new SuitedCard(Card.RANK.THREE, Card.SUIT.SPADES)
            )
        ), 1, 4));
    }
}
