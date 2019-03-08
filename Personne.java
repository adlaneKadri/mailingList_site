
import javax.mail.internet.AddressException;
import java.util.regex.*;

public class Personne {

    private String email;
    /* Constructor */

    public Personne(String email) throws AddressException {
        if (isValid(email))
            this.email = email;
        else {
            System.out.println("Be careful, you have used an invalid email\n" +
                    "you have to choose an other email using setEmail function\n");
            }
    }

    /* GETTER and SETTER */
    public String getMail() {
        return email;
    }

    public void setEmail(String email) throws AddressException {
        if (isValid(email))
            this.email = email;
        else
            System.out.println("EMAIL not valid");
    }

    public boolean isValid(String email) throws AddressException {
          return Email.isValid(email);

    }

    /*public static void main(String[] args) throws AddressException {
        Personne personne = new Personne("adlan@lipn.univ-paris13.fr");
        System.out.println(personne.getMail());
    }*/
}
