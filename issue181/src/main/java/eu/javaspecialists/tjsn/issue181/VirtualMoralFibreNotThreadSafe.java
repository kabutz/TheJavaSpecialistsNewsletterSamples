package eu.javaspecialists.tjsn.issue181;

public class VirtualMoralFibreNotThreadSafe extends VirtualMoralFibre {
    private MoralFibre realSubject;

    protected MoralFibre realSubject() {
        if (realSubject == null) {
            realSubject = new MoralFibreImpl();
        }
        return realSubject;
    }
}
