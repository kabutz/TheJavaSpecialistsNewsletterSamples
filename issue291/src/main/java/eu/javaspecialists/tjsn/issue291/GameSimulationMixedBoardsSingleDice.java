package eu.javaspecialists.tjsn.issue291;

public class GameSimulationMixedBoardsSingleDice {
    public static void main(String... args) {
        int players = 2;
        Board[] boards = {new EfiBoard(), new EfiBoard().reverse()};
        Dice dice = new DiceVeryFast();
        int games = 10_000_000;
        Simulation.play(players, i -> boards[i], __ -> dice, games);
    }
}
