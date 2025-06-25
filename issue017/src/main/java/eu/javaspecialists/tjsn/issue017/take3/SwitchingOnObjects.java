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
package eu.javaspecialists.tjsn.issue017.take3;

import eu.javaspecialists.tjsn.issue017.take3.TransactionType.*;

public class SwitchingOnObjects {
    public static void switchStatement(TransactionType transact) {
        try {
            throw transact;
        } catch (None type) {
            System.out.println("Case None received");
        } catch (ReadUncommitted type) {
            System.out.println("Case Read Uncommitted");
        } catch (ReadCommitted type) {
            System.out.println("Case Read Committed");
        } catch (RepeatableRead type) {
            System.out.println("Case Repeatable Read");
        } catch (TransactionType type) {
            System.out.println("Default");
        }
    }

    public static void main(String[] args) {
        switchStatement(None.type);
        switchStatement(ReadUncommitted.type);
        switchStatement(ReadCommitted.type);
        switchStatement(RepeatableRead.type);
        switchStatement(Serializable.type);
    }
}
