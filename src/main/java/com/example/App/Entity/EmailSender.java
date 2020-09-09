package com.example.App.Entity;

import java.util.*;
import javax.mail.*;
import javax.mail.internet.*;
import javax.activation.*;

public class EmailSender {

    public static void send(String to, String from, String subject, String text) {
        // Recipient's email ID needs to be mentioned.
        //String to = "abcd@gmail.com";

        // Sender's email ID needs to be mentioned
        //String from = "web@gmail.com";

        // Assuming you are sending email from localhost
        final String username = "3dabef4a9e21b5";
        final String password = "a1336dc8e743fb";
        String host = "smtp.mailtrap.io";

        Properties props = new Properties();
        props.put("mail.smtp.auth","true");
        //props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host",host);
        props.put("mail.smtp.port","465");

        // Get the default Session object.
        Session session = Session.getInstance(props,
                new javax.mail.Authenticator(){
                    protected PasswordAuthentication getPasswordAuthentication(){
                        return new PasswordAuthentication(username,password);
                    }
                });

        try {
            // Create a default MimeMessage object.
            MimeMessage message = new MimeMessage(session);

            // Set From: header field of the header.
            message.setFrom(new InternetAddress(from));

            // Set To: header field of the header.
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));

            // Set Subject: header field
            //message.setSubject("This is the Subject Line!");
            message.setSubject(subject);

            // Now set the actual message
            //message.setText("This is actual message");
            message.setText(text);

            // Send message
            Transport.send(message);
            System.out.println("Sent message successfully....");
        } catch (MessagingException mex) {
            mex.printStackTrace();
        }
    }
}