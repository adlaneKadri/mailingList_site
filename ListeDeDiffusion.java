import javax.mail.internet.AddressException;
import java.util.ArrayList;
import java.util.logging.Logger;

public class ListeDeDiffusion {
    /*public enum Theme {
        sociale ("sociale"),
        evenement ("evenement"),
        reunion ("reunion"),
        nouvelles ("nouvelles");

        private  String theme;
        Theme(String theme) {
            this.theme=theme;
        }
    }*/
    private String nom;
    private Theme theme;
    private Personne diffuseur;
    private String password;
    private ArrayList<Personne> listAbonnes;

    /* Constructor */
    public ListeDeDiffusion(String nom, String theme, String diffuseurEmail, String password) throws AddressException {
        this.nom = nom;
        this.theme = Theme.valueOf(theme);
        this.diffuseur = new Personne(diffuseurEmail);
        this.password = password;
        this.listAbonnes = new ArrayList<>();
    }

    /* Gestion d'abonnement */

    public boolean addAbonnenment(Personne personne){
        return listAbonnes.add(personne);
    }

    public boolean removeAbonnenement(Personne personne){
        return listAbonnes.remove(personne);
    }

    /* Getters And Setters */
    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public Theme getTheme() {
        return theme;
    }

    public void setTheme(Theme theme) {
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

    public ArrayList<Personne> getListAbonnes() {
        return listAbonnes;
    }

    public void setListAbonnes(ArrayList<Personne> listAbonnes) {
        this.listAbonnes = listAbonnes;
    }

    //ajouter abonne
    public boolean addAbonne(String mailAbonne) throws AddressException {
          return   listAbonnes.add(new Personne(mailAbonne));
    }

    //supprimer abonne
    public  boolean removeAbonne(String mailAbonne) {

        boolean deleted =  listAbonnes.removeIf(personne->personne.getMail().equals(mailAbonne));
            if (deleted)
                System.out.println("Abonné supprimé avec succes");
            else
                System.out.println("Abonné n'existe pas");
        return deleted;
    }

    @Override
    public String toString() {
        return "{ " +
                "\n\t<nom> " + this.getNom()  +" </nom>"+
                "\n\t<theme> " + this.getTheme() + " </theme>" +
                "\n\t<email-diffuseur> " + this.getDiffuseur().getMail() +" </email-diffuseur>"+
                "\n\t<password> " + this.getPassword() + " </password> "+
                "\n\t<nombre-abonnées> " + this.getListAbonnes().size()+" </nombre-abonnées> \n"+
                '}';
    }

}
