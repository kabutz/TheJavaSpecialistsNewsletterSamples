package eu.javaspecialists.tjsn.issue131;

import jakarta.mail.*;
import jakarta.mail.internet.*;

import java.util.*;

public class MailSender {
    private static final String SMTP_SERVER =
            "smtp.javaspecialists.eu";
    private static final String USERNAME =
            "heinz@javaspecialists.eu";
    private static final String PASSWORD = "some_password";
    private static final String FROM =
            "Dr Heinz M. Kabutz <heinz@javaspecialists.eu>";
    private static final String mailer = "TJSNMailer";

    private final Transport transport;
    private final Session session;
    private final MessageProvider provider;

    public MailSender(MessageProvider provider)
            throws MessagingException {
        this.provider = provider;
        Properties props = System.getProperties();
        props.put("mail.smtp.host", SMTP_SERVER);
        props.put("mail.smtp.auth", "true");
        // Get a Session object
        session = Session.getInstance(props, null);
        transport = session.getTransport("smtp");
        transport.connect(SMTP_SERVER, USERNAME, PASSWORD);
    }

    public void sendMessageTo(String to) throws MessagingException {
        Message msg = new MimeMessage(session);
        // set headers
        msg.setFrom(InternetAddress.parse(FROM, false)[0]);
        msg.setHeader("X-Mailer", mailer);
        msg.setSentDate(new Date());
        msg.setRecipients(Message.RecipientType.TO,
                InternetAddress.parse(to, false));

        // set title and body
        msg.setSubject(provider.getSubject());
        msg.setText(provider.getContent());

        // off goes the message...
        transport.sendMessage(msg, msg.getAllRecipients());
    }
}
