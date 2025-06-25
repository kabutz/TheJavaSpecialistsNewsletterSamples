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

package eu.javaspecialists.tjsn.issue029;

public class Test {
    public static void main(String[] args) {
        MemoryTestBench bench = new MemoryTestBench();
        bench.showMemoryUsage(new BasicObjectFactory());
        bench.showMemoryUsage(new ByteFactory());
        bench.showMemoryUsage(new ThreeByteFactory());
        bench.showMemoryUsage(new PrimitiveByteArrayFactory());
        bench.showMemoryUsage(new BooleanObjectFactory());
        bench.showMemoryUsage(new BooleanArrayFactory());
        bench.showMemoryUsage(new BooleanFlyweightFactory());
        bench.showMemoryUsage(new EmptyBooleanArrayFactory());
        bench.showMemoryUsage(new ThirtyTwoBooleanFactory());
        bench.showMemoryUsage(new SixtyFourBooleanFactory());
        bench.showMemoryUsage(new StringFactory());
        bench.showMemoryUsage(new EmptyStringFactory());
        bench.showMemoryUsage(new VectorFactory());
        bench.showMemoryUsage(new EmptyVectorFactory());
        bench.showMemoryUsage(new ArrayListFactory());
        bench.showMemoryUsage(new CharArrayFactory());
        bench.showMemoryUsage(new FullArrayListFactory());
        bench.showMemoryUsage(new FullLinkedListFactory());
        bench.showMemoryUsage(new EmptyCircularArrayListFactory());
        bench.showMemoryUsage(new HashMapFactory());
        bench.showMemoryUsage(new SmallHashMapFactory());
        bench.showMemoryUsage(new FullHashMapFactory());
        bench.showMemoryUsage(new MapEntryFactory());
        bench.showMemoryUsage(new IntegerToStringFactory());
        bench.showMemoryUsage(new VMIDFactory());
        bench.showMemoryUsage(new VMIDFactoryString());
    }
}
