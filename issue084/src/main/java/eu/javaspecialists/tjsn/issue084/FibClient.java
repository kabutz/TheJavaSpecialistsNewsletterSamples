package eu.javaspecialists.tjsn.issue084;

import org.apache.axis.client.*;

public class FibClient {
    public static void main(String[] args) throws Exception {
        String endpoint = "http://localhost:8080/axis/Fibonacci.jws";
        Service service = new Service();
        Call call = (Call) service.createCall();
        call.setTargetEndpointAddress(endpoint);
        call.setOperationName("calculate");
        Integer ret = (Integer) call.invoke(new Object[]{new Integer(args[0])});
        System.out.println("Got result : " + ret);
    }
}
