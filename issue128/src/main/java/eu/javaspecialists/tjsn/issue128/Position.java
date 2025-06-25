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

public final class Position {
    private final int row;
    private final int col;

    public Position(int row, int col) {
        this.row = row;
        this.col = col;
    }

    public int hashCode() {
        return row * 29 + col;
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Position)) return false;
        Position position = (Position) o;
        return !(col != position.col || row != position.row);
    }

    public String toString() {
        return "(" + row + "," + col + ")";
    }

    public Collection<Position> getRelatedPositions() {
        Collection<Position> result = new HashSet<Position>();
        result.addAll(getHorizontalPositions());
        result.addAll(getVerticalPositions());
        result.addAll(getSmallSquarePositions());
        return result;
    }

    public Collection<Position> getHorizontalPositions() {
        Collection<Position> result = new HashSet<Position>();
        for (int i = 0; i < 9; i++) {
            result.add(new Position(row, i));
        }
        result.remove(this);
        return result;
    }

    public Collection<Position> getVerticalPositions() {
        Collection<Position> result = new HashSet<Position>();
        for (int i = 0; i < 9; i++) {
            result.add(new Position(i, col));
        }
        result.remove(this);
        return result;
    }

    public Collection<Position> getSmallSquarePositions() {
        Collection<Position> result = new HashSet<Position>();
        for (int i = 0; i < 9; i++) {
            int smallSqRow = i / 3 + (row / 3) * 3;
            int smallSqCol = i % 3 + (col / 3) * 3;
            result.add(new Position(smallSqRow, smallSqCol));
        }
        result.remove(this);
        return result;
    }
}
