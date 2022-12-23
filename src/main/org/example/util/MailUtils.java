package org.example.util;

import javax.mail.*;
import javax.mail.internet.*;
import java.io.File;
import java.io.IOException;
import java.util.Properties;

public class MailUtils {

    private static Properties g_prop = new Properties();

    static {
        g_prop.put("mail.smtp.host", "smtp.gmail.com");
        g_prop.put("mail.smtp.port", "587");
        g_prop.put("mail.smtp.auth", "true");
        g_prop.put("mail.smtp.starttls.enable", "true"); //TLS
    }

    private static Properties prop = new Properties();

    static {
        prop.put("mail.smtp.host", "smtp.rambler.ru");
        prop.put("mail.smtp.port", "2525");
        prop.put("mail.smtp.auth", "true");
        prop.put("mail.smtp.starttls.enable", "true"); //TLS
    }


    private static Session getSession(){
        return Session.getInstance(prop,
                new Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(AppConstants.EMAIL_ACC, AppConstants.EMAIL_PWD);
                    }
                });
    }

    public static boolean send(String to, String subject, String content) {
        try {
            Message message = prepareMessage(to, subject);
            message.setText(content);
            System.out.println("Sending...");
            Transport.send(message);
            System.out.println("Message is sent.");
            return true;
        } catch (MessagingException e) {
            e.printStackTrace();
            return false;
        }
    }



    public static void send(String to, String subject, String content, String path) {
        try {
            Message message = prepareMessage(to, subject);

            MimeBodyPart textPart = new MimeBodyPart();
            textPart.setContent(content, "text/html");

            MimeBodyPart attachmentPart = new MimeBodyPart();
            if(path != null)
                attachmentPart.attachFile(new File(path));

            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(textPart);
            if(path != null)
                multipart.addBodyPart(attachmentPart);

            message.setContent(multipart); // TODO check HTML

            System.out.println("Sending...");
            Transport.send(message);
            System.out.println("Message is sent.");

        } catch (MessagingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private static Message prepareMessage(String to, String subject) throws MessagingException {
        if(!isValidEmailAddress(to)){
            throw new IllegalArgumentException("Wrong email address");
        }

        Message message = new MimeMessage(getSession());
        message.setFrom(new InternetAddress(AppConstants.EMAIL_ACC));
        message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
        message.setSubject(subject);
        return  message;
    }


    public static boolean isValidEmailAddress(String email) {
        boolean result = true;
        try {
            InternetAddress emailAddr = new InternetAddress(email);
            emailAddr.validate();
        } catch (AddressException ex) {
            result = false;
        }
        return result;
    }


    public static void main(String[] args) {

        send("Sharlan@outlook.com", "TEST", "TEST");

    }

}
