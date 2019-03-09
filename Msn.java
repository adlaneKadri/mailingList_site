import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.ArrayList;
import java.util.Date;
import java.util.Properties;

public class Msn {
    public static ArrayList<String> emails = new ArrayList<>();
    public static String sujet = new String();
    public static String messageCorp = new String();
    public static String EmailSender = "smartkey19061995@gmail.com";
    public static String Password = "smartkey123456789";


    public static void sendMail(){
        try{
            String host ="smtp.gmail.com" ;
            String to = emails.get(0);
            //String subject = "This is confirmation number for your expertprogramming account. Please insert this number to activate your account.";
            String subject = sujet;
            String messageText = messageCorp;
            boolean sessionDebug = false;

            Properties props = System.getProperties();

            props.put("mail.smtp.auth", "true");
            props.put("mail.smtp.starttls.enable", "true");
            props.put("mail.smtp.host", host);
            props.put("mail.smtp.port", "587");
            props.put("mail.smtp.starttls.required", "true");

            java.security.Security.addProvider(new com.sun.net.ssl.internal.ssl.Provider());
            Session mailSession = Session.getDefaultInstance(props, null);
            mailSession.setDebug(sessionDebug);
            Message msg = new MimeMessage(mailSession);
            msg.setFrom(new InternetAddress(EmailSender));
            InternetAddress[] address = {new InternetAddress(to)};
            msg.setRecipients(Message.RecipientType.TO, address);
            if (emails.size()>1){
                for (int i =1; i<emails.size();i++){
                    msg.addRecipient(Message.RecipientType.TO,
                            new InternetAddress(emails.get(i)));
                }
            }
            msg.setSubject(subject); msg.setSentDate(new Date());
            msg.setText(messageText);

            Transport transport=mailSession.getTransport("smtp");
            transport.connect(host, EmailSender, Password);
            transport.sendMessage(msg, msg.getAllRecipients());
            transport.close();
            System.out.println("message send successfully");
        }catch(Exception ex)
        {
            System.out.println(ex);
        }
    }
}
