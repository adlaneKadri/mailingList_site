/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
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
            
        }
        
        
        
        connexion.shutdownOutput();
    }
    
}
