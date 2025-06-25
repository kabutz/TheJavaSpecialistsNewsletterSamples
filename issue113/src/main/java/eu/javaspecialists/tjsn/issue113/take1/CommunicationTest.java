package eu.javaspecialists.tjsn.issue113.take1;

import java.io.*;

public class CommunicationTest {
    public static void main(String... args) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        Communication<Animal> acom = new Communication<Animal>(Animal.class);
        acom.sendOne(baos, Animal.Ape);
        baos.close();
        ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
        Animal animal = acom.receiveOne(bais);
        System.out.println(animal);
    }
}
