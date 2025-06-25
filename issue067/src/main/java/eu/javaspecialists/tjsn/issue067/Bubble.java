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

package eu.javaspecialists.tjsn.issue067;

public class Bubble extends Basic {
    // Original Code:
    // 10 DIM N(10):RANDOMIZE TIMER
    // 20 I = 0
    // 30 N(I) = INT(1000 * RND)
    // 40 I = I + 1: IF I < 10 GOTO 30
    // 50 S% = 1 : I = 0
    // 60 IF N(I) < N(I + 1) GOTO 80
    // 70 T = N(I) : N(I) = N(I + 1) : N(I + 1) = T : S% = 0
    // 80 I = I + 1 : IF I < 9 GOTO 60
    // 90 IF S% = 0 GOTO 50
    // 100 I = 0
    // 110 PRINT N(I)
    // 120 I = I + 1 : IF I < 10 GOTO 110
    public static void main(String[] args) {
        int I = 0;
        int S = 0;
        int N[] = null;
        while (jump != -1) {
            try {
                switch (jump) {
                    case 10:
                        N = DIM(10);
                    case 20:
                        I = 0;
                    case 30:
                        N[I] = INT(1000 * RND());
                    case 40:
                        I = I + 1;
                        if (I < 10) GOTO(30);
                    case 50:
                        S = 1;
                        I = 0;
                    case 60:
                        if (N[I] < N[I + 1]) GOTO(80);
                    case 70:
                        int T = N[I];
                        N[I] = N[I + 1];
                        N[I + 1] = T;
                        S = 0;
                    case 80:
                        I = I + 1;
                        if (I < 9) GOTO(60);
                    case 90:
                        if (S == 0) GOTO(50);
                    case 100:
                        I = 0;
                    case 110:
                        PRINT(N[I]);
                    case 120:
                        I = I + 1;
                        if (I < 10) GOTO(110);
                    case 130:
                        STOP();
                }
                // if there was no GOTO then we want to end the program
                STOP();
            } catch (GotoException ex) {
                // GOTO was called, and a GotoException has caused the
                // control to pass outside of the switch statement
            }
        }
    }
}
