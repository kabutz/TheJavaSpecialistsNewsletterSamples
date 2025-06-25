package eu.javaspecialists.tjsn.issue291;

public class GameReversed {
    public static void main(String... args) {
        Board board = new EfiBoard().reverse();
        Dice dice = new DiceFair();
        var tournamentObserver = new TournamentObserver();
        for (int i = 0; i < 10_000_000; i++) {
            tournamentObserver.play(board, dice);
        }
        System.out.println(tournamentObserver);
    }
}
