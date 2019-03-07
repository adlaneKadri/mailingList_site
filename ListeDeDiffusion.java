
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
    public enum theme {
        sociale ("sociale"), 
        evenement ("evenement"), 
        reunion ("reunion"),  
        nouvelles ("nouvelles");
        private  String theme;
        theme(String theme) {
            this.theme=theme;
        }
    }
    public Personne diffuseur;
    private String password;
    private List<Personne> abonnes = new ArrayList();
     
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

    public Personne getDiffuseur() {
        return diffuseur;
    }

    public void setDiffuseur(Personne diffuseur) {
        this.diffuseur = diffuseur;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public  List<Personne> getAbonnes() {
        return abonnes;
    }

    public void setAbonnes(List<Personne> abonne) {
        abonnes = abonne;
    }
    
    
    //constructeur

    public ListeDeDiffusion(String nomListe, String th, String mail_diffuseur, String password) {
        this.nomListe = nomListe;
        this.theme = theme.valueOf(th);
        this.password = password;
        try{
            this.diffuseur = new Personne(mail_diffuseur) ;
            System.out.println("liste crée ! nom de la liste: " +this.nomListe +
                ", theme choisi: "+ this.theme.theme+ ", adresse mail du proprietaire: "
                    +this.diffuseur.getMailAdress());
            AllList.add(this); //on garde une liste de toutes les listes qu'on crée
        } catch(AddressException e){
            Logger.getLogger(ListeDeDiffusion.class.getName()).log(Level.SEVERE, null, e);
            System.out.println("liste non créer");}    
    }
    
   
    
    //ajouter abonne
    public void addAbonne(String mailabonne) {
        personne abonne;
        try {
            abonne = new personne(mailabonne);
            ListeDeDiffusion.abonnes.add(abonne);
        } catch (AddressException ex) {
            Logger.getLogger(ListeDeDiffusion.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    //supprimer abonne
    public  boolean removeAbonne(String mailabonne) {
        for(personne abonne : abonnes) 
            if (abonne.getMailAdress().equals(mailabonne))
            {
                System.out.println("Abonné trouvé !");
                abonnes.remove(abonne);
                System.out.println("Abonné supprimé avec succes");
                return true;
            }
        System.out.println("Abonné n'existe pas");
        return false;
    }
    
}
