package eu.javaspecialists.tjsn.issue291;

import java.util.*;
import java.util.function.*;
import java.util.stream.*;

public class Simulation {
    public static void play(int players,
                            IntFunction<Board> boardCreator,
                            IntFunction<Dice> diceCreator,
                            int games) {
        int[] wins = new int[players];
        Board[] boards = IntStream.range(0, players)
                .mapToObj(boardCreator).toArray(Board[]::new);
        Dice[] dice = IntStream.range(0, players)
                .mapToObj(diceCreator).toArray(Dice[]::new);

        for (int i = 0; i < games; i++) {
            var obs = new GameObserverSummary[wins.length];
            Arrays.setAll(obs, __ -> new GameObserverSummary());
            int minTurns = Integer.MAX_VALUE;
            for (int player = 0; player < wins.length; player++) {
                boards[player].play(obs[player], dice[player]);
                minTurns = Math.min(minTurns, obs[player].turns());
            }
            for (int player = 0; player < obs.length; player++) {
                // The first player that has turns == minTurns wins
                if (obs[player].turns() == minTurns) {
                    wins[player]++;
                    break;
                }
            }
        }
        for (int i = 0; i < wins.length; i++) {
            if (i > 0) {
                double diff = (wins[i - 1] - wins[i]) * 100.0 / games;
                System.out.printf(Locale.US, "diff = %.2f%%%n", diff);
            }
            System.out.println("player" + (i + 1) +
                    " Wins = " + wins[i]);
        }
    }
}
