/*
 * This file must be runned in a separet project 
 * this is the test Client of the server
 */
package listediffusionclient;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

/**
 *
 * @author ameni
 */
public class ListeDiffusionClient {
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

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws UnknownHostException, IOException {
        // TODO code application logic here
        Socket connexion = new Socket(InetAddress.getLocalHost(),33333);
        Writer output = new OutputStreamWriter(connexion.getOutputStream(), "8859_1");
        
        /**************** TEST of server ************/
        
        Scanner lecture = new Scanner(System.in);
        System.out.println("Choisi le numero de l'action à faire");
        System.out.println("-------------------------\n");
        System.out.println("1 - Creer une liste de diffusion");
        System.out.println("2 - Supprimer une liste de diffusion");
        System.out.println("3 - S'abonner à une liste de diffusion");
        System.out.println("4 - Se désabonner d'une liste de diffusion");
        System.out.println("5 - Envoyer un message à une liste de diffusion");
        System.out.println("6 - Recupérer les thèmes d'une liste de diffusion");
        System.out.println("7 - Afficher toutes les listes de diffusion");
        int choix = lecture.nextInt();
        /***************************************************/
        
        switch (choix) {
            case 1:
                //Creation liste
                Scanner sc = new Scanner(System.in);
                System.out.println("Veuillez saisir le nom de la liste :");
                String nomliste = sc.nextLine();
                System.out.println("Veuillez saisir le thème de la liste :");
                theme th = theme.valueOf(sc.nextLine());
                System.out.println("Veuillez saisir votre adresse mail :");
                String diffuseur = sc.nextLine();
                System.out.println("Veuillez saisir votre mot de passe :");
                String mdp = sc.nextLine();
                output.write("create_list "+nomliste+" "+th+" "+diffuseur+" "+mdp); output.flush();
                break;
             case 2:
                //supprimer liste
                Scanner in = new Scanner(System.in);
                System.out.println("Veuillez saisir le nom de la liste que vous voulez supprimer :");
                String nomListe = in.nextLine();
                System.out.println("Veuillez saisir le mdp de la liste");
                String pswd = in.nextLine();
                output.write("remove_list "+nomListe+" "+pswd);
                output.flush();
                break;
            case 3:
                //s'abonner à une liste
                Scanner ab = new Scanner(System.in);
                System.out.println("Veuillez saisir le nom de la liste que vous voulez s'abonner :");
                String NomListe = ab.nextLine();
                System.out.println("Veuillez saisir votre mail");
                String mail = ab.nextLine();
                output.write("subscribe_list "+NomListe+" "+mail);
                output.flush();
                break;
            case 4 :
                //se désabonner d'une liste
                Scanner desaab = new Scanner(System.in);
                System.out.println("Veuillez saisir le nom de la liste que vous voulez se désabonner :");
                String Nomliste = desaab.nextLine();
                System.out.println("Veuillez saisir votre mail");
                String Mail = desaab.nextLine();
                output.write("unscribe_list "+Nomliste+" "+Mail);
                output.flush();
                break;
            case 7:
                output.write("afficher_list"); output.flush();
                break; 
        }
        
        
        
        connexion.shutdownOutput();
    }
    
}
