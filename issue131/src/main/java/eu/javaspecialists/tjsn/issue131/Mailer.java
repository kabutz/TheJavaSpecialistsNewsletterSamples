package eu.javaspecialists.tjsn.issue131;

import jakarta.mail.*;

import java.io.*;

public class Mailer {
    private final FileCollection to;
    private final MessageProvider provider;

    public Mailer(String addressFile, String messageFile)
            throws IOException {
        to = new FileCollection(addressFile);
        provider = new MessageProvider(messageFile);
    }

    public void sendMessages() throws MessagingException {
        MailSender sender = new MailSender(provider);
        for (String email : to) {
            sender.sendMessageTo(email);
            System.out.println("Mail sent to " + email);
        }
    }

    public static void main(String... args) throws Exception {
        if (args.length != 2) {
            System.err.println(
                    "Usage: java Mailer address_file message_file");
            System.exit(1);
        }

        long time = -System.currentTimeMillis();
        Mailer sender = new Mailer(args[0], args[1]);
        sender.sendMessages();
        time += System.currentTimeMillis();
        System.out.println(time + "ms");
        System.out.println("Finished");
    }
}
