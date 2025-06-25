package eu.javaspecialists.tjsn.issue029;

public class VMIDFactory implements ObjectFactory {
    public Object makeObject() {
        return new java.rmi.dgc.VMID();
    }

    static {
        System.out.println("VMID.length()=" + new java.rmi.dgc.VMID().toString().length());
    }
}
