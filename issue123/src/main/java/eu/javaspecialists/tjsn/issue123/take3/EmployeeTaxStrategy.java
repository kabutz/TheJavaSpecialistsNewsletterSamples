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

package eu.javaspecialists.tjsn.issue123.take3;

public class EmployeeTaxStrategy implements TaxStrategy {
    private static final double NORMAL_RATE = 0.40;
    private static final double MARRIED_FEMALE_RATE = 0.48;

    public double extortCash(TaxPayer p) {
        Employee e = (Employee) p; // here we need to downcast!!!
        if (e.isMarried() &&
                e.getGender() == Employee.Gender.FEMALE) {
            return e.getIncome() * MARRIED_FEMALE_RATE;
        }
        return e.getIncome() * NORMAL_RATE;
    }
}