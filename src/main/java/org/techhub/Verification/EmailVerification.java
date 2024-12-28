package org.techhub.Verification;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;
import java.util.Random;
import java.util.Scanner;

public class EmailVerification {
    public static String storedEmail;
    public static int verificationCode;
    private static Scanner scanner = new Scanner(System.in);

    public static int generateVerificationCode() {
        Random random = new Random();
        return random.nextInt(900000) + 100000; // generates a 6-digit code
    }

    public static void sendVerificationEmail(String email, int verificationCode) {
        final String username = "mutkulekajal@gmail.com"; // your Gmail address
        final String password = "zgcp jwla knqz gpsx"; // your Gmail app password (not the account password)

        Properties prop = new Properties();
        prop.put("mail.smtp.host", "smtp.gmail.com"); // Correct SMTP host for Gmail
        prop.put("mail.smtp.port", "587"); // Port for TLS
        prop.put("mail.smtp.auth", "true");
        prop.put("mail.smtp.starttls.enable", "true"); // Enable TLS

        Session session = Session.getInstance(prop,
                new Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(username));
            message.setRecipients(
                    Message.RecipientType.TO,
                    InternetAddress.parse(email)
            );
            message.setSubject("Email Verification");
            message.setText("Your verification code is: " + verificationCode);

            Transport.send(message);

            System.out.println("Verification code sent to your email.");
        } catch (MessagingException e) {
            System.out.println("Error sending verification code: " + e.getMessage());
        }
    }

//    public static boolean verifyCode(int inputCode) {
//        return verificationCode == inputCode;
//    }
}
/*
public static void main(String[] args) {
    System.out.print("Enter your email address:");
    storedEmail = scanner.nextLine();
    verificationCode = generateVerificationCode();
    sendVerificationEmail(storedEmail, verificationCode);
    System.out.println("Verification code sent to your email. Please enter the code:");
    int inputCode = scanner.nextInt();
    if (verifyCode(inputCode)) {
        System.out.println("Email verified successfully!");
    } else {
        System.out.println("Invalid verification code.");
    }
}*/