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

package eu.javaspecialists.tjsn.issue164;

import java.util.concurrent.atomic.*;

public class ThreadLocalUser {
    private final int num;
    private MyThreadLocal<MyValue> value;
    private final AtomicBoolean finalized;

    public ThreadLocalUser(AtomicBoolean finalized,
                           AtomicBoolean nestedFinalized) {
        this(0, finalized, nestedFinalized);
    }

    public ThreadLocalUser(int num,
                           AtomicBoolean finalized,
                           AtomicBoolean nestedFinalized) {
        this.num = num;
        this.finalized = finalized;
        value = new MyThreadLocal<MyValue>(nestedFinalized);
    }

    protected void finalize() throws Throwable {
        System.out.println("ThreadLocalUser.finalize " + num);
        finalized.set(true);
        super.finalize();
    }

    public void setThreadLocal(MyValue myValue) {
        value.set(myValue);
    }

    public void clear() {
        value.remove();
    }
}
