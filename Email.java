import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

}
