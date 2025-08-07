package model.games.poker.mississippiStudPoker;

/**
 * Main class to run the Mississippi Stud Poker game logic.
 */
public class MSP_Game {
    /**
     * Main method to test the Mississippi Stud Poker game logic.
     *
     * @param ignored Command line arguments (not used).
     */
    public static void main(String[] ignored) {
        MSP_Helpers mspHelpers = new MSP_Helpers();
        System.out.println(mspHelpers.houseEdge());
    }
}