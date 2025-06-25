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

package eu.javaspecialists.tjsn.issue123.take1;

public class TaxPayer {
    public static final int COMPANY = 0;
    public static final int EMPLOYEE = 1;
    public static final int TRUST = 2;
    private static final double COMPANY_RATE = 0.30;
    private static final double EMPLOYEE_RATE = 0.45;
    private static final double TRUST_RATE = 0.35;

    private double income;
    private final int type;

    public TaxPayer(int type, double income) {
        this.type = type;
        this.income = income;
    }

    public double getIncome() {
        return income;
    }

    public double extortCash() {
        switch (type) {
            case COMPANY:
                return income * COMPANY_RATE;
            case EMPLOYEE:
                return income * EMPLOYEE_RATE;
            case TRUST:
                return income * TRUST_RATE;
            default:
                throw new IllegalArgumentException();
        }
    }
}
