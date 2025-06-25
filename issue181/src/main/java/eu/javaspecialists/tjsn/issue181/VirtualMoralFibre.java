package eu.javaspecialists.tjsn.issue181;

public abstract class VirtualMoralFibre implements MoralFibre {
    protected abstract MoralFibre realSubject();

    public final double actSociallyResponsibly() {
        return realSubject().actSociallyResponsibly();
    }

    public final double empowerEmployees() {
        return realSubject().empowerEmployees();
    }

    public final double cleanupEnvironment() {
        return realSubject().cleanupEnvironment();
    }
}
