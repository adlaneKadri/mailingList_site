
import javax.mail.internet.AddressException;
import java.util.ArrayList;
import java.util.Optional;
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

    public static void main(String[] args) throws AddressException {
        //Personne personne = new Personne("adlan@lipn.univ-paris13.fr");
        //System.out.println(personne.getMail());


        ArrayList<Personne> a = new ArrayList<>();

        Personne p1 = new Personne("a1@a.f");
        Personne p2 = new Personne("a2@a.f");
        Personne p3 = new Personne("a@a3.f");
        Personne p4 = new Personne("a@a4.f");
        Personne p5 = new Personne("a5@a.f");
        Personne p6 = new Personne("a64@a.f");


        a.add(p1);
        a.add(p2);
        a.add(p3);
        a.add(p4);
        a.add(p5);
        a.add(p6);
        /*
        boolean pp= false;
        for(Personne abonne : a)
            if (abonne.getMail().equals("a64@a.f"))
            {
                System.out.println("Abonné supprimé avec succes");
                pp = a.remove(abonne);
                break;
            }
        if (pp == false)
        System.out.println("Abonné n'existe pas");
*/
        boolean rrr =  a.removeIf(personne->personne.getMail().equals("a6s4@a.f"));
        if (rrr){
            System.out.println("good");
        }else
            System.out.println("nooot");



    }

}
