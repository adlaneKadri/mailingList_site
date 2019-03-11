import java.io.IOException;


public class MainAdmin {

    public static void main(String[] args) throws IOException {

        AdminServer serveur = new AdminServer( Server.port, 1 );
        serveur.ManageRequest();

        //serveur.getAllList().;


        //connexion.shutdownOutput()
    }
}