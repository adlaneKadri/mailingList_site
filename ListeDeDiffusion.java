import java.util.ArrayList;

public class ListeDeDiffusion {
    private String nom;
    private String theme;
    private Personne diffuseur;
    private String pwd;
    private ArrayList<Personne> listAbonnes;


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

    public String getTheme() {
        return theme;
    }

    public void setTheme(String theme) {
        this.theme = theme;
    }

    public Personne getDiffuseur() {
        return diffuseur;
    }

    public void setDiffuseur(Personne diffuseur) {
        this.diffuseur = diffuseur;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public ArrayList<Personne> getListAbonnes() {
        return listAbonnes;
    }

    public void setListAbonnes(ArrayList<Personne> listAbonnes) {
        this.listAbonnes = listAbonnes;
    }
}
