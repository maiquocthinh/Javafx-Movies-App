package com.javaproject.movie.Services;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.googleapis.json.GoogleJsonError;
import com.google.api.client.googleapis.json.GoogleJsonResponseException;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import org.apache.commons.codec.binary.Base64;
import com.google.api.services.gmail.Gmail;
import com.google.api.services.gmail.GmailScopes;
import com.google.api.services.gmail.model.Message;

import javax.mail.BodyPart;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.io.*;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Properties;

public class GmailService {
    /**
     * Application name.
     */
    private static final String APPLICATION_NAME = "Gmail API Java";
    /**
     * Global instance of the JSON factory.
     */
    private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
    /**
     * Directory to store authorization tokens for this application.
     */
    private static final String TOKENS_DIRECTORY_PATH = "tokens";
    /**
     * Global instance of the scopes required by this quickstart.
     * If modifying these scopes, delete your previously saved tokens/ folder.
     */
    private static final java.util.List<String> SCOPES = Collections.singletonList(GmailScopes.GMAIL_SEND);
    private static final String CREDENTIALS_FILE_PATH = "/credentials.json";
    private static final String USER_ID = "me";

    /**
     * Creates an authorized Credential object.
     *
     * @param HTTP_TRANSPORT The network HTTP Transport.
     * @return An authorized Credential object.
     * @throws IOException If the credentials.json file cannot be found.
     */
    private static Credential getCredentials(final NetHttpTransport HTTP_TRANSPORT) throws IOException {
        // Load client secrets.
        InputStream in = GmailService.class.getResourceAsStream(CREDENTIALS_FILE_PATH);
        if (in == null) throw new FileNotFoundException("Resource not found: " + CREDENTIALS_FILE_PATH);
        GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(JSON_FACTORY, new InputStreamReader(in));

        // Build flow and trigger user authorization request.
        GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(HTTP_TRANSPORT, JSON_FACTORY, clientSecrets, SCOPES)
                .setDataStoreFactory(new FileDataStoreFactory(new java.io.File(TOKENS_DIRECTORY_PATH)))
                .setAccessType("offline")
                .build();
        LocalServerReceiver receiver = new LocalServerReceiver.Builder().setPort(8888).build();
        Credential credential = new AuthorizationCodeInstalledApp(flow, receiver).authorize(USER_ID);

        //returns an authorized Credential object.
        return credential;
    }

    /**
     * Send an email message
     *
     * @param recipientEmail The email address of the recipient.
     * @param subject        The subject of the email.
     * @param body           The body of the email.
     * @throws GeneralSecurityException, IOException, MessagingException If there is a problem sending the email.
     */
    public static void sendMessage(String recipientEmail, String subject, String body) throws GeneralSecurityException, IOException, MessagingException {
        // Build a new authorized API client service.
        NetHttpTransport httpTransport = GoogleNetHttpTransport.newTrustedTransport();
        Gmail gmailService = new Gmail.Builder(httpTransport, JSON_FACTORY, getCredentials(httpTransport))
                .setApplicationName(APPLICATION_NAME)
                .build();

        // Create the email message
        MimeMessage email = new MimeMessage(Session.getDefaultInstance(new Properties()));
        email.setFrom(new InternetAddress(USER_ID));
        email.addRecipient(javax.mail.Message.RecipientType.TO, new InternetAddress(recipientEmail));
        email.setSubject(subject);
        email.setText(body);

        // Convert the MimeMessage to a raw message.
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        email.writeTo(bytes);
        String encodedEmail = Base64.encodeBase64URLSafeString(bytes.toByteArray());

        // Create the message object
        Message message = new Message();
        message.setRaw(encodedEmail);

        // Send the message
        gmailService.users().messages().send(USER_ID, message).execute();
        System.out.println("Message sent to " + recipientEmail);
    }

    /**
     * Send much email message
     *
     * @param recipientEmails The email address of the recipient.
     * @param subject         The subject of the email.
     * @param body            The body of the email.
     * @throws GeneralSecurityException, IOException, MessagingException If there is a problem sending the email.
     */
    public static void sendManyMessage(List<String> recipientEmails, String subject, String body) throws GeneralSecurityException, IOException, MessagingException {
        // Build a new authorized API client service.
        NetHttpTransport httpTransport = GoogleNetHttpTransport.newTrustedTransport();
        Gmail gmailService = new Gmail.Builder(httpTransport, JSON_FACTORY, getCredentials(httpTransport))
                .setApplicationName(APPLICATION_NAME)
                .build();

        // Add the recipient email addresses
        List<InternetAddress> toAddresses = new ArrayList<InternetAddress>();
        for (String recipientEmail : recipientEmails) {
            toAddresses.add(new InternetAddress(recipientEmail));
        }

        // Create the email message
        MimeMessage email = new MimeMessage(Session.getDefaultInstance(new Properties()));
        email.setFrom(new InternetAddress(USER_ID));
        email.addRecipients(javax.mail.Message.RecipientType.TO, toAddresses.toArray(new InternetAddress[toAddresses.size()]));
        email.setSubject(subject);
        email.setText(body);

        // Convert the MimeMessage to a raw message.
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        email.writeTo(bytes);
        String encodedEmail = Base64.encodeBase64URLSafeString(bytes.toByteArray());

        // Create the message object
        Message message = new Message();
        message.setRaw(encodedEmail);

        // Send the message
        gmailService.users().messages().send(USER_ID, message).execute();
        System.out.println("Message sent to " + recipientEmails);
    }

    /**
     * Send an email message with HTML content
     *
     * @param recipientEmail The email address of the recipient.
     * @param subject        The subject of the email.
     * @param htmlBody       The body of the email in HTML format.
     * @throws GeneralSecurityException, IOException, MessagingException If there is a problem sending the email.
     */
    public static void sendHtmlMessage(String recipientEmail, String subject, String htmlBody) throws GeneralSecurityException, IOException, MessagingException {
        // Build a new authorized API client service.
        NetHttpTransport httpTransport = GoogleNetHttpTransport.newTrustedTransport();
        Gmail gmailService = new Gmail.Builder(httpTransport, JSON_FACTORY, getCredentials(httpTransport))
                .setApplicationName(APPLICATION_NAME)
                .build();

        // Create multipart with the HTML body
        Multipart multipart = new MimeMultipart();
        BodyPart htmlPart = new MimeBodyPart();
        htmlPart.setContent(htmlBody, "text/html");
        multipart.addBodyPart(htmlPart);

        // Create the email message
        MimeMessage email = new MimeMessage(Session.getDefaultInstance(new Properties()));
        email.setFrom(new InternetAddress(USER_ID));
        email.addRecipient(javax.mail.Message.RecipientType.TO, new InternetAddress(recipientEmail));
        email.setSubject(subject);
        email.setContent(multipart);

        // Convert the MimeMessage to a raw message.
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        email.writeTo(bytes);
        String encodedEmail = Base64.encodeBase64URLSafeString(bytes.toByteArray());

        // Create the message object
        Message message = new Message();
        message.setRaw(encodedEmail);

        // Send the message
        message = gmailService.users().messages().send(USER_ID, message).execute();
        System.out.printf("Message sent with id: %s\n", message.getId());
    }

    /**
     * Send much email message with HTML content
     *
     * @param recipientEmails The email address of the recipient.
     * @param subject        The subject of the email.
     * @param htmlBody       The body of the email in HTML format.
     * @throws GeneralSecurityException, IOException, MessagingException If there is a problem sending the email.
     */
    public static void sendManyHtmlMessage(List<String> recipientEmails, String subject, String htmlBody) throws GeneralSecurityException, IOException, MessagingException {
        // Build a new authorized API client service.
        NetHttpTransport httpTransport = GoogleNetHttpTransport.newTrustedTransport();
        Gmail gmailService = new Gmail.Builder(httpTransport, JSON_FACTORY, getCredentials(httpTransport))
                .setApplicationName(APPLICATION_NAME)
                .build();

        // Add the recipient email addresses
        List<InternetAddress> toAddresses = new ArrayList<InternetAddress>();
        for (String recipientEmail : recipientEmails) {
            toAddresses.add(new InternetAddress(recipientEmail));
        }

        // Create multipart with the HTML body
        Multipart multipart = new MimeMultipart();
        BodyPart htmlPart = new MimeBodyPart();
        htmlPart.setContent(htmlBody, "text/html");
        multipart.addBodyPart(htmlPart);

        // Create the email message
        MimeMessage email = new MimeMessage(Session.getDefaultInstance(new Properties()));
        email.setFrom(new InternetAddress(USER_ID));
        email.addRecipients(javax.mail.Message.RecipientType.TO, toAddresses.toArray(new InternetAddress[toAddresses.size()]));
        email.setSubject(subject);
        email.setContent(multipart);

        // Convert the MimeMessage to a raw message.
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        email.writeTo(bytes);
        String encodedEmail = Base64.encodeBase64URLSafeString(bytes.toByteArray());

        // Create the message object
        Message message = new Message();
        message.setRaw(encodedEmail);

        // Send the message
        message = gmailService.users().messages().send(USER_ID, message).execute();
        System.out.printf("Message sent with id: %s\n", message.getId());
    }
}


