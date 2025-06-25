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

package eu.javaspecialists.tjsn.issue123.take4;

public abstract class TaxPayer<P extends TaxPayer<P>> {
    public static final TaxStrategy<Employee> EMPLOYEE =
            new EmployeeTaxStrategy();
    public static final TaxStrategy<Company> COMPANY =
            new CompanyTaxStrategy();
    public static final TaxStrategy<Trust> TRUST =
            new TrustTaxStrategy();

    private double income;
    private TaxStrategy<P> strategy;

    public TaxPayer(TaxStrategy<P> strategy, double income) {
        this.strategy = strategy;
        this.income = income;
    }

    protected abstract P getDetailedType();

    public double getIncome() {
        return income;
    }

    public double extortCash() {
        return strategy.extortCash(getDetailedType());
    }
}
