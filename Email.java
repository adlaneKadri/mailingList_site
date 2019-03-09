import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.MimeMessage;
import java.util.*;

public class Email {

    public static final Pattern
            VALID_EMAIL_ADDRESS_REGEX =
            Pattern.compile("^[a-zA-Z]+[A-Z0-9._%+-]*@(lipn.univ-paris13.fr)$",
                    Pattern.CASE_INSENSITIVE);

    public static boolean isValidParis13(String email){
        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX .matcher(email);
           return matcher.find();
    }

    public static boolean isValid(String mail){
        boolean isValid = false;
        try{
            InternetAddress internetaddress = new InternetAddress(mail);
            internetaddress.validate();
            isValid = true;
        } catch(AddressException e)
        {
            System.out.println("ERREUR de creation: "+mail+ " est une adress mail invalide! ");
        }
        return isValid;

    }


    public static void main(String[] args) {
        Msn.emails.add("mr4youb@gmail.com");
        Msn.emails.add("adlan68@live.fr");

        Msn.EmailSender = "smartkey19061995@gmail.com";
        Msn.Password = "smartkey123456789";
        Msn.sujet =  "This is confirmation number for your expertprogramming account";
        Msn.messageCorp = " to confirm ";

        Msn.sendMail();

    }
}

