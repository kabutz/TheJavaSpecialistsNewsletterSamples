package eu.javaspecialists.tjsn.issue161;


public class Human {
    public void sing(HumanState state) {
        switch (state) {
            case HAPPY:
                singHappySong();
                break;
            case SAD:
                singDirge();
                break;
            default:
                new IllegalStateException("Invalid State: " + state);
        }
    }

    private void singHappySong() {
        System.out.println("When you're happy and you know it ...");
    }

    private void singDirge() {
        System.out.println("Don't cry for me Argentina, ...");
    }
}