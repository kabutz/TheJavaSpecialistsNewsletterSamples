package eu.javaspecialists.tjsn.issue113.take2;

import java.io.*;

public class CommunicationTest {
    public static void main(String... args) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        Communication<Animal> acom = new Communication<Animal>(Animal.Cat);
        acom.sendOne(baos, Animal.Ape);
        baos.close();
        ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
        Animal animal = acom.receiveOne(bais);
        System.out.println(animal);
    }
}
