package eu.javaspecialists.tjsn.issue291;

public class GameSimulationSingleBoardSingleDice {
    public static void main(String... args) {
        int players = 2;
        Board board = new EfiBoard();
        Dice dice = new DiceVeryFast();
        int games = 10_000_000;
        Simulation.play(players, __ -> board, __ -> dice, games);
    }
}
