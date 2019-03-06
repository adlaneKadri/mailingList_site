
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.internet.AddressException;


public class ListeDeDiffusion {

    /**
     * @param args the command line arguments
     */
    public String nomListe;
    public theme theme;
    public static enum theme {
        sociale ("sociale"), 
        evenement ("evenement"), 
        reunion ("reunion"),  
        nouvelles ("nouvelles");
        private  String theme;
        theme(String theme) {
            this.theme=theme;
        }
    }
    public personne diffuseur;
    private String password;
    private static List<personne> abonnes = new ArrayList();
    public static List<ListeDeDiffusion> AllList= new ArrayList(); 
    
    //Getters & Setters

    public String getNomListe() {
        return nomListe;
    }

    public void setNomListe(String nomListe) {
        this.nomListe = nomListe;
    }

    public String getTheme() {
        return theme.theme;
    }

    public void setTheme(theme theme) {
        this.theme = theme;
    }

    public personne getDiffuseur() {
        return diffuseur;
    }

    public void setDiffuseur(personne diffuseur) {
        this.diffuseur = diffuseur;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public static List<personne> getAbonnes() {
        return abonnes;
    }

    public static void setAbonnes(List<personne> abonnes) {
        ListeDeDiffusion.abonnes = abonnes;
    }
    
    
    //constructeur

    public ListeDeDiffusion(String nomListe, String th, String mail_diffuseur, String password) {
        this.nomListe = nomListe;
        this.theme = theme.valueOf(th);
        this.password = password;
        try{
            this.diffuseur = new personne(mail_diffuseur) ;
            System.out.println("liste crée ! nom de la liste: " +this.nomListe +
                ", theme choisi: "+ this.theme.theme+ ", adresse mail du proprietaire: "
                    +this.diffuseur.getMailAdress());
            AllList.add(this); //on garde une liste de toutes les listes qu'on crée
        } catch(AddressException e){
            Logger.getLogger(ListeDeDiffusion.class.getName()).log(Level.SEVERE, null, e);
            System.out.println("liste non créer");}

        
    }
    
    //ajouter abonne
    public static void addAbonne(personne abonne) {
        ListeDeDiffusion.abonnes.add(abonne);
    }
    
}
