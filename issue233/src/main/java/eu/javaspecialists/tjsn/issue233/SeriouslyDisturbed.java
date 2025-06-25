package eu.javaspecialists.tjsn.issue233;

public class SeriouslyDisturbed extends MultiplePersonalities {
    public Runnable create() {
        return (
                Runnable &
                        java.awt.peer.FontPeer &
                        java.io.Serializable &
                        java.lang.Cloneable &
                        java.lang.reflect.Type &
                        java.rmi.Remote &
                        java.util.RandomAccess &
                        java.util.EventListener
                ) () -> System.out.printf("Oh my what am I?");
    }
}
