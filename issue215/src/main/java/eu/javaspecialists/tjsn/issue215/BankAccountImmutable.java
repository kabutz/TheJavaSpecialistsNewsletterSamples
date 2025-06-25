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

package eu.javaspecialists.tjsn.issue215;

public class BankAccountImmutable {
    private final long balance;

    public BankAccountImmutable(long balance) {
        this.balance = balance;
    }

    public BankAccountImmutable deposit(long amount) {
        return new BankAccountImmutable(balance + amount);
    }

    public BankAccountImmutable withdraw(long amount) {
        return new BankAccountImmutable(balance - amount);
    }

    public long getBalance() {
        return balance;
    }
}
