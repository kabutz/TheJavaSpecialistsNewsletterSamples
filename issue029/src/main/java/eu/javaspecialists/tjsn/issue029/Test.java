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
