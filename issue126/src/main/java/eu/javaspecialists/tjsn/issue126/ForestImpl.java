package eu.javaspecialists.tjsn.issue126;

public class ForestImpl implements Forest {
    private final String colour;

    public ForestImpl(String colour) {
        this.colour = colour;
    }

    public String getColour() {
        return colour;
    }

    public boolean equals(Object o) {
        return equalsCallBack(o, false);
    }

    public boolean equalsCallBack(Object o, boolean calledOnWrappedObject) {
        if (!(o instanceof Forest)) return false;
        Forest forest = (Forest) o;
        if (calledOnWrappedObject) {
            if (!(forest instanceof ForestImpl)) return false;
            return colour.equals(((ForestImpl) o).colour);
        } else {
            return forest.equalsCallBack(this, true);
        }
    }
}
