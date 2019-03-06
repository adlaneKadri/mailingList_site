import java.util.Scanner;
import java.util.concurrent.TimeUnit;
import java.util.regex.*;

public class Personne {

    private String email;
    public static final Pattern VALID_EMAIL_ADDRESS_REGEX =
            Pattern.compile("^[a-zA-Z]+[A-Z0-9._%+-]*@(lipn.univ-paris13.fr)$", Pattern.CASE_INSENSITIVE);

    public String getMail() {
        return email;
    }

    public void setMail(String email){
        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX .matcher(email);
            Scanner sc = new Scanner(System.in);
            while (!matcher.find()){
                System.out.println("EMAIL NOT CORRECT!\nLa form d'un email est comme suit : " +
                        "\npseudo@lipn.univ-paris13.fr\n \nTapez un nouveau email s'il vous plait!");
                for (int i=0;i<5;i++){
                    try {
                        TimeUnit.MILLISECONDS.sleep(400);
                        System.out.print(".");
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                System.out.println();
                email = sc.nextLine();
               matcher = VALID_EMAIL_ADDRESS_REGEX .matcher(email);
            }

        System.out.println("email accepted!\n");
        this.email=email;
        }

    public static void main(String[] args) {
        Personne personne = new Personne();
        personne.setMail("9adlan@lipn.univ-paris13.fr");
    }
}
