import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import java.security.Security;
import com.sun.net.ssl.internal.ssl.Provider;
/**
 *
 * @author KRICH
 */

public class Clientssl {

  public static enum theme {
      sociale( "sociale" ),
      evenement( "evenement" ),
      reunion( "reunion" ),
      nouvelles( "nouvelles" );
      private String theme;

      theme(String theme) {
          this.theme = theme;
      }
  }
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws UnknownHostException, IOException {
        // TODO code application logic here
        int serverPort = 33333;
        //The Server Address
        String serverName = "localhost";
        /*Adding the JSSE (Java Secure Socket Extension) provider which provides SSL and TLS protocols
        and includes functionality for data encryption, server authentication, message integrity,
        and optional client authentication.*/
        Security.addProvider(new Provider());
        //specifing the trustStore file which contains the certificate & public of the server
        System.setProperty("javax.net.ssl.trustStore","myTrustStore.jts");
        //specifing the password of the trustStore file
        System.setProperty("javax.net.ssl.trustStorePassword","123456");
        //This optional and it is just to show the dump of the details of the handshake process
        System.setProperty("javax.net.debug","all");

        //SSLSSocketFactory establishes the ssl context and and creates SSLSocket
        SSLSocketFactory sslsocketfactory = (SSLSocketFactory)SSLSocketFactory.getDefault();
        //Create SSLSocket using SSLServerFactory already established ssl context and connect to server
        SSLSocket sslSocket = (SSLSocket)sslsocketfactory.createSocket(serverName,serverPort);


        Writer output = new OutputStreamWriter(sslSocket.getOutputStream(), "8859_1");

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
                output.write("create_list "+nomliste+" "+th+" "+diffuseur+" "+mdp);
                output.flush();
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
            case 5 :
                //envoyer un message à une liste de diff
                Scanner msg = new Scanner(System.in);
                System.out.println("Veuillez saisir le nom de la liste :");
                String liste = msg.nextLine();
                System.out.println("si vous voulez utiliser l'email par default tape : 'o' or 'O' ");
                String defaults = msg.nextLine();
                String sender = "default email";
                String Pswd = "default password";
                if (!defaults.equals("o") && !defaults.equals("O")) {
                    System.out.println("Veuillez saisir votre mail");
                     sender = msg.nextLine();
                    System.out.println("Veuillez saisir votre mdp");
                     Pswd = msg.nextLine();
                }
                System.out.println("Veuillez saisir l'objet de votre mail");
                String object = msg.nextLine();
                System.out.println("Veuillez saisir le corps de votre mail");
                String body = msg.nextLine();
                output.write("send_email_to_list "+liste+" "+sender+" "+Pswd+" "+object+" "+body+" "+defaults);
                output.flush();
                break;
            case 6:
                output.write("recuperer_par_theme");
                output.flush();
                break;
            case 7:
                output.write("afficher_list");
                output.flush();
                break;
        }
        
        sslSocket.close();
    }
}
