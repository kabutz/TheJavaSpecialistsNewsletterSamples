/*
 * Copyright 2000-2025 Heinz Max Kabutz
 * All rights reserved.
 *
 * From The Java Specialists' Newsletter (https://www.javaspecialists.eu)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *  https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

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
