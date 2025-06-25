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

package eu.javaspecialists.tjsn.issue220;

import java.util.concurrent.*;

public class PhaserForkJoin {
    public static void main(String... args) {
        ForkJoinPool common = ForkJoinPool.commonPool();
        Phaser phaser = new Phaser(200);
        common.invoke(new PhaserWaiter(phaser));
    }

    private static class PhaserWaiter extends RecursiveAction {
        private final Phaser phaser;

        private PhaserWaiter(Phaser phaser) {
            this.phaser = phaser;
            System.out.println(ForkJoinPool.commonPool().getPoolSize());
        }

        protected void compute() {
            if (phaser.getPhase() > 0) return; // we've passed first phase
            PhaserWaiter p1 = new PhaserWaiter(phaser);
            p1.fork();
            phaser.arriveAndAwaitAdvance();
            p1.join();
        }
    }
}
