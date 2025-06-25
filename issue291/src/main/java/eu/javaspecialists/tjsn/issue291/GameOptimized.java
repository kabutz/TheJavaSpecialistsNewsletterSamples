/*
 * Copyright 2000-2025 Heinz Max Kabutz
 * All rights reserved.
 *
 * From The Java Specialists' Newsletter (https://www.javaspecialists.eu)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *  https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

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
