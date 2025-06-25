package eu.javaspecialists.tjsn.issue145;

public enum TristateState {
    SELECTED {
        public TristateState next() {
            return INDETERMINATE;
        }
    },
    INDETERMINATE {
        public TristateState next() {
            return DESELECTED;
        }
    },
    DESELECTED {
        public TristateState next() {
            return SELECTED;
        }
    };

    public abstract TristateState next();
}
