public class Diff {
    private String login;
    private String password;
    private String machine;
    private String nomFichier=null;
    private String fichier=null;

    public Diff (String login,String password,String machine,String nomFichier,String fichier) {
        this.login = login;
        this.password = password;
        this.machine = machine;
        this.nomFichier = nomFichier;
        this.fichier = fichier;
    }

    public String getLogin () {
        return login;
    }

    public String getpassword() {
        return password;
    }

    public String getMachine () {
        return machine;
    }

    public String getNomFichier () {
        return nomFichier;
    }

    public String getFichier () {
        return fichier;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setMachine(String machine) {
        this.machine = machine;
    }

    public void setNomFichier(String nomFichier) {
        this.nomFichier = nomFichier;
    }

    public void setFichier(String fichier) {
        this.fichier = fichier;
    }
}
