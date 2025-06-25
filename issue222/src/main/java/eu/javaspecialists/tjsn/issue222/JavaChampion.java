package eu.javaspecialists.tjsn.issue222;

public final class JavaChampion {
    private final String name;

    public JavaChampion(String name) {
        this.name = name;
    }

    public boolean equals(Object o) { // simplified equals()
        if (!(o instanceof JavaChampion)) return false;
        return name.equals(((JavaChampion) o).name);
    }
}
