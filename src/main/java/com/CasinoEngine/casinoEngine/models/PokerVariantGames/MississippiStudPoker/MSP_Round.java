package com.CasinoEngine.casinoEngine.models.PokerVariantGames.MississippiStudPoker;

import com.CasinoEngine.casinoEngine.models.AllHelpers.Deck;
import com.CasinoEngine.casinoEngine.models.AllHelpers.RequestFailedException;
import com.CasinoEngine.casinoEngine.models.PokerVariantGames.PokerHelpers.PokerHelpers;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Stream;

import static com.CasinoEngine.casinoEngine.models.PokerVariantGames.PokerHelpers.PokerHelpers.HAND_TYPE.*;

public class MSP_Round {

    private int numberOfRoyalFlushes = 0;
    private int numberOfStraightFlushes = 0;
    private int numberOfFourOfAKinds = 0;
    private int numberOfFullHouses = 0;
    private int numberOfFlushes = 0;
    private int numberOfStraights = 0;
    private int numberOfThreeOfAKinds = 0;
    private int numberOfTwoPairs = 0;
    private int numberOfHighPairs = 0;
    private int numberOfMidPairs = 0;
    private int numberOfLosers = 0;

    public static void main(String[] ignored) throws RequestFailedException {
        // play(800);
        // autoPlay(800, 15);
//        playAll();
    }

    private static void playAll() throws RequestFailedException {
        MSP_Helpers mspHelpers = new MSP_Helpers();
        Deck deck = new Deck();
        List<Deck.Card> cards = deck.getDeck();
        int totalExpectedBets = 0;
        int count = 0;
        for (Deck.Card firstCard : cards) {
            for (Deck.Card secondCard : cards) {
                if (!firstCard.equals(secondCard)) {
                    double expectedBet = 1;
                    int handMultiplierOne = mspHelpers.solveHand(List.of(firstCard, secondCard), 1, 1);
                    expectedBet += handMultiplierOne;
                    for (Deck.Card thirdCard : cards) {
                        if (!thirdCard.equals(firstCard) && !thirdCard.equals(secondCard)) {
                            int handMultiplierTwo = mspHelpers.solveHand(List.of(firstCard, secondCard), 1, 1 + handMultiplierOne);
                            expectedBet += handMultiplierTwo * (1.0 / (cards.size() - 2));
                            for (Deck.Card fourthCard : cards) {
                                if (!fourthCard.equals(firstCard) && !fourthCard.equals(secondCard) && !fourthCard.equals(thirdCard)) {
                                    int handMultiplierThree = mspHelpers.solveHand(List.of(firstCard, secondCard), 1, 1 + handMultiplierOne + handMultiplierTwo);
                                    expectedBet += handMultiplierThree * (1.0 / (cards.size() - 2)) * (1.0 / (cards.size() - 3));
                                }
                            }
                        }
                    }
                }
                System.out.println(count++);
            }
        }
        System.out.println("Average Expected Bets: " + totalExpectedBets * (1.0 / (cards.size() * (cards.size() - 1))));
    }

    private static void autoPlay(int startingBankroll, int ante) {
        int numberOfGames = 10_000;
        int totalDoubleEndingBankroll = 0;
        int doubledBankroll = 0;
        MSP_Round game = new MSP_Round();
        List<Integer> numHands = new ArrayList<>();
        for (int gameNumber = 1; gameNumber <= numberOfGames; gameNumber++) {
            int bankroll = startingBankroll;
            int hands = 0;
            while (bankroll > 0) {
                if (bankroll > 2 * startingBankroll) {
                    totalDoubleEndingBankroll += bankroll;
                    doubledBankroll++;
                    System.out.println("Doubled Bankroll");
                    break;
                }
                bankroll = game.startRoundAuto(bankroll, ante);
                hands++;
                System.out.println("Hand " + hands + " of game " + gameNumber + ". $" + bankroll + " remaining.");
            }
//             System.out.print("You lasted " + hands + " hands.");
            numHands.add(hands);
        }
        int totalNumberOfHands = numHands.stream().mapToInt(Integer::intValue).sum();
        System.out.println("Number of Hands: " + totalNumberOfHands);
        System.out.println("Average hands played: " + numHands.stream().mapToInt(Integer::intValue).average().orElse(0));
        System.out.println("Median hands played: " + numHands.stream().mapToInt(Integer::intValue).sorted().toArray()[numHands.size() / 2]);
        System.out.println("Max hands played: " + numHands.stream().mapToInt(Integer::intValue).max().orElse(0));
        System.out.println("Min hands played: " + numHands.stream().mapToInt(Integer::intValue).min().orElse(0));
        System.out.println("Standard Deviation of hands played: " + Math.sqrt(numHands.stream().mapToInt(Integer::intValue).mapToDouble(x -> Math.pow(x - numHands.stream().mapToInt(Integer::intValue).average().orElse(0), 2)).sum() / numHands.size()));
        System.out.println("Number of Royal Flushes: " + game.numberOfRoyalFlushes);
        System.out.println("Number of Straight Flushes: " + game.numberOfStraightFlushes);
        System.out.println("Number of Four of a Kinds: " + game.numberOfFourOfAKinds);
        System.out.println("Number of Full Houses: " + game.numberOfFullHouses);
        System.out.println("Number of Flushes: " + game.numberOfFlushes);
        System.out.println("Number of Straights: " + game.numberOfStraights);
        System.out.println("Number of Three of a Kinds: " + game.numberOfThreeOfAKinds);
        System.out.println("Number of Two Pairs: " + game.numberOfTwoPairs);
        System.out.println("Number of High Pairs: " + game.numberOfHighPairs);
        System.out.println("Number of Mid Pairs: " + game.numberOfMidPairs);
        System.out.println("Number of Losers: " + game.numberOfLosers);
        System.out.println("Number of wins: " + (totalNumberOfHands - game.numberOfLosers));
        System.out.println("Win percentage: " + (double) (totalNumberOfHands - game.numberOfLosers) / totalNumberOfHands * 100 + "%");
        System.out.println();
        System.out.println("Number of times bankroll doubled: " + doubledBankroll);
        System.out.println("Number of bankrolls lost: " + (numberOfGames - doubledBankroll));
        System.out.println("Doubled percentage: " + (double) doubledBankroll / numberOfGames * 100 + "%");
        System.out.println("Total profits: " + (totalDoubleEndingBankroll - (startingBankroll * numberOfGames)));

        // 10,000 games starting with 800, betting 15
//        Number of Hands: 773692
//        Average hands played: 77.3692
//        Median hands played: 60
//        Max hands played: 709
//        Min hands played: 1
//        Standard Deviation of hands played: 59.31216309796836
//        Number of Royal Flushes: 1
//        Number of Straight Flushes: 4
//        Number of Four of a Kinds: 153
//        Number of Full Houses: 820
//        Number of Flushes: 991
//        Number of Straights: 1,722
//        Number of Three of a Kinds: 11,827
//        Number of Two Pairs: 24,820
//        Number of High Pairs: 74,710
//        Number of Mid Pairs: 68,283
//        Number of Losers: 577,064
//        Number of wins: 196,628
//        Win percentage: 25.41424753002487%
//
//        Number of times bankroll doubled: 3342
//        Number of bankrolls lost: 6658
//        Doubled percentage: 33.42%
//        Total profits: -1,217,225
    }

    private int startRoundAuto(int bankroll, int ante) {
        int amountWagered = 0;
        List<Deck.Card> communityCards = new ArrayList<>();
        PokerHelpers pokerHelpers = new PokerHelpers();
        MSP_Helpers mspHelpers = new MSP_Helpers();
        MSP_Player player = new MSP_Player(bankroll);
        Deck deck = new Deck();
        amountWagered += ante;
        try {
            player.makeBet(ante);
        } catch (RequestFailedException e) {
            System.out.println(e.getMessage());
            return 0;
        }
        player.assignFirstCard(deck.getCard());
        player.assignSecondCard(deck.getCard());
//        System.out.println("Your cards are: " + player.getHoleCards().get(0) + " and " + player.getHoleCards().get(1));
        for (int street = 1; street <= 3; street++) {
            try {
                int betAmount = ante * mspHelpers.solveHand(
                        new ArrayList<>(Stream.concat(communityCards.stream(), player.getHoleCards().stream()).toList()),
                        ante,
                        amountWagered
                );
                if (betAmount == 0) {
                    numberOfLosers++;
//                    System.out.println("You folded. For a total wager of $" + amountWagered + ".");
                    return player.getChipStack();
                }
//                System.out.println("You bet $" + betAmount + ". For a total wager of $" + amountWagered + ".");
                player.makeBet(betAmount);
                amountWagered += betAmount;
            } catch (RequestFailedException e) {
                System.out.println(e.getMessage());
                break;
            }
            Deck.Card newCard = deck.getCard();
//            System.out.println("Community Card " + street + ": " + newCard);
            communityCards.add(newCard);
        }
        try {
            PokerHelpers.Hand hand = pokerHelpers.findBestFiveCardHand(player.getHoleCards(), communityCards);
            int winnings = mspHelpers.determinePayout(hand, amountWagered);
            player.collectWinnings(winnings);
            if (winnings < amountWagered) {
                numberOfLosers++;
//                System.out.println("You lost $" + amountWagered + " after hitting a " + hand + ". You are now at $" + player.getChipStack() + ".");
            } else if (winnings == amountWagered) {
                numberOfMidPairs++;
//                System.out.println("You pushed after hitting a " + hand + ". You are now at $" + player.getChipStack() + ".");
            } else {
                switch (hand.handStrength) {
                    case ROYAL_FLUSH:
                        numberOfRoyalFlushes++;
                        System.out.println("A royal flush occurred!");
                        break;
                    case STRAIGHT_FLUSH:
                        numberOfStraightFlushes++;
                        System.out.println("A straight flush occurred!");
                        break;
                    case FOUR_OF_A_KIND:
                        numberOfFourOfAKinds++;
                        System.out.println("Quads occurred!");
                        break;
                    case FULL_HOUSE:
                        numberOfFullHouses++;
                        break;
                    case FLUSH:
                        numberOfFlushes++;
                        break;
                    case STRAIGHT:
                        numberOfStraights++;
                        break;
                    case THREE_OF_A_KIND:
                        numberOfThreeOfAKinds++;
                        break;
                    case TWO_PAIR:
                        numberOfTwoPairs++;
                        break;
                    default:
                        numberOfHighPairs++;
                        break;
                }
//                System.out.println("You won $" + (winnings - amountWagered) + " after hitting a " + hand + ". You are now at $" + player.getChipStack() + ".");
            }
        } catch (RequestFailedException e) {
            System.out.println(e.getMessage());
        }
        return player.getChipStack();
    }


    private static void play(int startingBankroll) {
        MSP_Round game = new MSP_Round();
        int bankroll = startingBankroll;
        System.out.println("Welcome to Mississippi Stud Poker! You start with $" + startingBankroll + "!");
        while (bankroll > 0) {
            bankroll = game.startRoundHuman(bankroll);
        }
        System.out.print("Thanks for playing!");
    }

    private int startRoundHuman(int bankroll) {
        int amountWagered = 0;
        List<Deck.Card> communityCards = new ArrayList<>();
        PokerHelpers pokerHelpers = new PokerHelpers();
        MSP_Helpers mspHelpers = new MSP_Helpers();
        MSP_Player player = new MSP_Player(bankroll);
        Deck deck = new Deck();
        int ante = getAnte();
        amountWagered += ante;
        try {
            player.makeBet(ante);
        } catch (RequestFailedException e) {
            System.out.println(e.getMessage());
            return 0;
        }
        if (ante == 0) {
            return 0;
        }
        player.assignFirstCard(deck.getCard());
        player.assignSecondCard(deck.getCard());
        System.out.println("Your cards are: " + player.getHoleCards().get(0) + " and " + player.getHoleCards().get(1));
        for (int street = 1; street <= 3; street++) {
            try {
                int betAmount = ante * getWagerMultiplier();
                if (betAmount == 0) {
                    System.out.print("You lost $" + amountWagered + " after folding. You are now at $" + player.getChipStack() + ".");
                    if (street == 1) {
                        System.out.println("The next cards would have been: " + deck.getCard() + ", " + deck.getCard() + ", and " + deck.getCard() + ".");
                    } if (street == 2) {
                        System.out.println("The next cards would have been: " + deck.getCard() + " and " + deck.getCard() + ".");
                    } if (street == 3) {
                        System.out.println("The next card would have been: " + deck.getCard() + ".");
                    }
                    return player.getChipStack();
                }
                player.makeBet(betAmount);
                amountWagered += betAmount;
            } catch (RequestFailedException e) {
                System.out.println(e.getMessage());
                break;
            }
            Deck.Card newCard = deck.getCard();
            System.out.println("Community Card " + street + ": " + newCard);
            communityCards.add(newCard);
        }
        try {
            PokerHelpers.Hand hand = pokerHelpers.findBestFiveCardHand(player.getHoleCards(), communityCards);
            int winnings = mspHelpers.determinePayout(hand, amountWagered);
            player.collectWinnings(winnings);
            if (winnings < amountWagered) {
                System.out.print("You lost $" + amountWagered + " after hitting a " + hand + ". You are now at $" + player.getChipStack() + ".");
            } else if (winnings == amountWagered) {
                System.out.print("You pushed after hitting a " + hand + ". You are now at $" + player.getChipStack() + ".");
            } else {
                System.out.print("You won $" + (winnings - amountWagered) + " after hitting a " + hand + ". You are now at $" + player.getChipStack() + ".");
            }
        } catch (RequestFailedException e) {
            System.out.println(e.getMessage());
        }
        return player.getChipStack();
    }

    private int getWagerMultiplier() {
        int wagerMultiplier;
        while (true) {
            try {
                Scanner scanner = new Scanner(System.in);
                System.out.println("Enter Wager Multiplier (1-3) or 0 to Fold: ");
                String wagerMultiplierString = scanner.nextLine();
                wagerMultiplier = Integer.parseInt(wagerMultiplierString);
                if (wagerMultiplier >= 0) {
                    break;
                }
                System.out.println("Please Enter A Non Negative Integer");
            } catch (NumberFormatException e) {
                System.out.println("Please Enter A Integer");
            }
        }
        return wagerMultiplier;
    }

    private int getAnte() {
        int ante;
        while (true) {
            try {
                Scanner scanner = new Scanner(System.in);
                System.out.println();
                System.out.println("Enter Ante Amount or 0 to Quit: ");
                String anteString = scanner.nextLine();
                ante = Integer.parseInt(anteString);
                if (ante >= 0) {
                    break;
                }
                System.out.println("Please Enter A Non Negative Integer");
            } catch (NumberFormatException e) {
                System.out.println("Please Enter A Integer");
            }
        }
        return ante;
    }
}
