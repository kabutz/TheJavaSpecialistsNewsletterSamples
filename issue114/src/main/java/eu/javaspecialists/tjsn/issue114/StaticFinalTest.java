package eu.javaspecialists.tjsn.issue114;

public interface StaticFinalTest {
    String LITERAL = "Literal";
    String LITERAL_PLUS = "Literal" + "Plus";
    String LITERAL_NEW = new String("LiteralNew");
    String LITERAL_CONCAT = "LiteralConcat".concat("");
    // Change to:
    // String LITERAL = "LiteralXXX";
    // String LITERAL_PLUS = "Literal" + "PlusXXX";
    // String LITERAL_NEW = new String("LiteralNewXXX");
    // String LITERAL_CONCAT = "LiteralConcat".concat("XXX");
}
