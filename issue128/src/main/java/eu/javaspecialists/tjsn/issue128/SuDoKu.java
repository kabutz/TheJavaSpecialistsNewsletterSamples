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

package eu.javaspecialists.tjsn.issue128;

import java.util.*;

public class SuDoKu {
    public enum Value {
        ONE, TWO, THREE, FOUR, FIVE, SIX, SEVEN, EIGHT, NINE;

        public String toString() {
            return Integer.toString(ordinal() + 1);
        }
    }

    private static final int GRID = 9;

    private final Map<Position, EnumSet<Value>> numbers =
            new HashMap<Position, EnumSet<Value>>();

    public SuDoKu(int... values) {
        if (values.length != GRID * GRID)
            throw new IllegalArgumentException("Bad value count");
        Value[] all = Value.values();
        for (int i = 0; i < values.length; i++) {
            Position pos = new Position(i / GRID, i % GRID);
            if (values[i] == 0) {
                numbers.put(pos, EnumSet.range(Value.ONE, Value.NINE));
            } else {
                numbers.put(pos, EnumSet.of(all[values[i] - 1]));
            }
        }
        sieveImpossibleNumbers();
    }

    public boolean solve() {
        do {
            System.out.println(this);
        } while (sieveImpossibleNumbers() || searchForAnswers());
        for (EnumSet<Value> values : numbers.values()) {
            if (values.size() != 1) return false;
        }
        return true;
    }

    /**
     * Goes through all the positions and removes numbers that are
     * not possible.  Also checks the correctness of the found
     * numbers.
     */
    private boolean sieveImpossibleNumbers() {
        boolean removed = false;
        for (Position pos : numbers.keySet()) {
            Value value = getNumber(pos);
            if (value == null) {
                // must be bitwise OR, otherwise it will fall through
                removed |= removeImpossibleNumbers(pos);
            } else {
                checkCorrectness(pos, value);
            }
        }
        return removed;
    }

    private boolean removeImpossibleNumbers(Position pos) {
        boolean removed = false;
        EnumSet<Value> vals = numbers.get(pos);
        for (Position other : pos.getRelatedPositions()) {
            removed |= vals.remove(getNumber(other));
        }
        return removed;
    }

    private Value getNumber(Position pos) {
        EnumSet<Value> vals = numbers.get(pos);
        if (vals.size() == 1) {
            return vals.iterator().next();
        }
        return null;
    }

    private void checkCorrectness(Position pos, Value val) {
        for (Position other : pos.getRelatedPositions()) {
            if (val == getNumber(other)) {
                throw new IllegalArgumentException("Error with: " + pos
                        + " clashes with relative " + other);
            }
        }
    }

    private boolean searchForAnswers() {
        for (Position pos : numbers.keySet()) {
            EnumSet<Value> possible = numbers.get(pos);
            if (possible.size() > 1) {
                for (Value value : possible) {
                    if (valueNotIn(value, pos.getHorizontalPositions()) ||
                            valueNotIn(value, pos.getVerticalPositions()) ||
                            valueNotIn(value, pos.getSmallSquarePositions())) {
                        System.out.println(pos + " MUST BE " + value);
                        numbers.put(pos, EnumSet.of(value));
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private boolean valueNotIn(Value value,
                               Collection<Position> positions) {
        for (Position pos : positions) {
            if (numbers.get(pos).contains(value)) {
                return false;
            }
        }
        return true;
    }

    public String toString() {
        StringBuffer result = new StringBuffer();
        for (int row = 0; row < GRID; row++) {
            for (int col = 0; col < GRID; col++) {
                EnumSet<Value> vals = numbers.get(new Position(row, col));
                result.append('[');
                for (Value v : vals) result.append(v);
                result.append(']').append('\t');
            }
            result.append('\n');
        }
        return result.toString();
    }
}
