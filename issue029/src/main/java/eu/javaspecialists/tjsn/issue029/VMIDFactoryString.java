package eu.javaspecialists.tjsn.issue029;

public class VMIDFactoryString implements ObjectFactory {
    public Object makeObject() {
        return new java.rmi.dgc.VMID().toString();
    }
}
