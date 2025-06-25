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

package eu.javaspecialists.tjsn.issue119;

import java.io.*;
import java.util.*;

public class ScannerTest {
    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(new FileReader("logfile.txt"));
        while (scanner.hasNext()) {
            String type = scanner.next();
            int year = scanner.nextInt();
            int month = scanner.nextInt();
            int day = scanner.nextInt();
            int time = scanner.nextInt();
            System.out.printf("%d/%d/%d@%d", year, month, day, time);
            if (type.equals("entry")) {
                String purpose = scanner.next();
                String restOfLine = scanner.nextLine().trim();
                System.out.printf(" entry %s: %s%n", purpose, restOfLine);
            } else if (type.equals("exit")) {
                String exitName = scanner.nextLine().trim();
                System.out.printf(" exit %s%n", exitName);
            } else if (type.equals("alarm")) {
                String alarmType = scanner.next();
                String comment = scanner.nextLine().trim();
                System.out.printf(" alarm %s: %s%n", alarmType, comment);
            } else {
                throw new IllegalArgumentException();
            }
        }
        scanner.close();
    }
}
