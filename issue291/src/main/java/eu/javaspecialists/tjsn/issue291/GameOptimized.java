package eu.javaspecialists.tjsn.issue291;

public class GameOptimized {
    public static void main(String... args) {
        var tournamentObserver1 = new TournamentObserver();
        tournamentObserver1.play(
                new EfiBoard(),
                new DiceOptimized(10, 1));
        System.out.println(tournamentObserver1);

        System.out.println();

        var tournamentObserver2 = new TournamentObserver();
        tournamentObserver2.play(
                new EfiBoard().reverse(),
                new DiceOptimized(18, 5));
        System.out.println(tournamentObserver2);
    }
}
