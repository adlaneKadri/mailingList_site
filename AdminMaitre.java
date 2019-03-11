

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;


public class AdminMaitre implements Runnable {



    private final Socket socket;
    private final AdminServer serveur;

    AdminMaitre(Socket socket, AdminServer serveur) {
        this.socket = socket;
        this.serveur = serveur;
    }

    @Override
    public void run() {
        try {
            BufferedReader input = new BufferedReader(
                    new InputStreamReader( socket.getInputStream(),
                            "8859_1" ), 1024 ); // flux en lecture
            StringBuffer sb = new StringBuffer();
            sb.append( input.readLine() );
            System.out.println( sb );
            String[] commande = sb.toString().split( " " );
            switch (commande[0]) {
                case "create_list":
                    System.out.println( "im heree!" );
                    createList( commande );
                    break;
                case "remove_list":
                    String nom = commande[1];
                    deleteList(nom);
                    break;
                case "subscribe_list":
                    String liste = commande[1];
                    String mail = commande[2];
                    subscribeList( liste, mail );
                    break;
                case "unscribe_list":
                    String Liste = commande[1];
                    String Mail = commande[2];
                    unscribeList( Liste, Mail );
                    break;
                case "send_email_to_list":
                    String _Liste = commande[1];
                    String sender = commande[2];
                    String Pswd = commande[3];
                    String subject = commande[4];
                    String body = commande[5];

                    ListeDeDiffusion too = GetList(_Liste);
                    if (too ==null)
                    {
                        System.out.println("liste de diffusion n'existe pas!!");
                        return;
                    }

                    List<Personne> Abonnes = too.getAbonnes();
                    List<String> to = new ArrayList<>();
                    for(Personne p : Abonnes)
                    {
                        to.add(p.getMailAdress());
                    }

                    String[] array = to.toArray(new String[0]);


                    //String[] to = { "mr4youb@gmail.com" }; // list of recipient email addresses
                    sendEmail(sender,Pswd,array,subject,body);


                    break;
                case "afficher_list":
                    afficheList();
                    break;
                default:
                    ;
            }
        } catch (IOException e) {
            System.out.println( e );
        } finally {
            try {
                if (socket != null) socket.close();
            } catch (IOException e) {
            }
        }
    }

    public void createList(String[] commande) {
        ListeDeDiffusion L = new ListeDeDiffusion( commande[1], commande[2], commande[3], commande[4] );
        serveur.ManageList( L );
    }

    public void afficheList() {
        List<ListeDeDiffusion> AllList = serveur.getAllList();

        for (ListeDeDiffusion e : AllList)
            System.out.println(e.toString());
    }



    private void deleteList(String nom) {
        List<ListeDeDiffusion> AllList =serveur.getAllList();
        boolean deleted = false;
        searchloop:
        for(ListeDeDiffusion n : AllList)
        {
            if((n.getNomListe().equals(nom)))
            {
                System.out.println("liste trouvé!");
                serveur.DeleteList(n);
                System.out.println("liste supprimé avec succés!");
                afficheList();
                deleted=true;
                break searchloop;
            }
        }
        if(!deleted) System.out.println("nom de liste incorrecte!");
    }

    private void subscribeList(String liste, String mail) {
        List<ListeDeDiffusion> AllList = serveur.getAllList();
        searchloop:
        for(ListeDeDiffusion n : AllList)
        {
            if(n.getNomListe().equals(liste))
            {
                n.addAbonne(mail);
                System.out.println("vous avez été ajouté !");

            }
        }
    }

    private void unscribeList(String Liste, String Mail) {
        List<ListeDeDiffusion> AllList = serveur.getAllList();
        searchloop:
        for(ListeDeDiffusion n : AllList)
        {
            if(n.getNomListe().equals(Liste))
            {n.removeAbonne(Mail);
                System.out.println("vous êtes désabonné de la liste de diffusion: "+Liste);
            }
        }
    }

    private ListeDeDiffusion GetList(String nomListe){
        List<ListeDeDiffusion> AllList = serveur.getAllList();
        for(ListeDeDiffusion n : AllList)
        {
            if(n.getNomListe().equals(nomListe))
                return n;
        }
        return null;
    }






    public void sendEmail(String USER_NAME, String PASSWORD , String[] to , String subject , String body) {

        final String MAIL_SERVER = "smtp";
        final String SMTP_HOST_NAME = "smtp.gmail.com";
        final int SMTP_HOST_PORT = 587;
        // private static final String USER_NAME = "ayyoub.krich";  // GMail user name (just the part before "@gmail.com")
        //final String PASSWORD = "Jevaisttcasser123"; // GMail password


        // Message info

        // private String[] to; //{ "adlan68@live.fr" }; // list of recipient email addresses
        String[] cc = { "mr4youb@gmail.com" };
        //private String[] bcc; //{ "sxxxx@gmail.com" };
        // private String subject;  //= "Java Send Mail Attachement Example";
        // private String body;   //"Welcome to Java Mail!<h1>Hello</h1>";


//---------------------------------------------STEP 1---------------------------------------------

        System.out.println( "\n 1st ===> Setup SMTP Mail Server Properties..!" );

        // Get system properties
        Properties properties = System.getProperties();

        // Setup mail server
        properties.put( "mail.smtp.starttls.enable", "true" );
        properties.put( "mail.smtp.host", SMTP_HOST_NAME );
        properties.put( "mail.smtp.user", USER_NAME );
        properties.put( "mail.smtp.password", PASSWORD );
        properties.put( "mail.smtp.port", SMTP_HOST_PORT );
        properties.put( "mail.smtp.auth", "true" );

        try {
            //---------------------------------------------STEP 2---------------------------------------------


            System.out.println( "\n\n 2nd ===> Get Mail Session.." );
            // Get the Session object.

            // creates a new session with an authenticator
            Authenticator auth = new Authenticator() {
                public PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication( USER_NAME, PASSWORD );
                }
            };

            Session session = Session.getInstance( properties, auth );

            // Create a default MimeMessage object.
            MimeMessage message = new MimeMessage( session );

            try {

                //---------------------------------------------

                // Set From: header field of the header.
                message.setFrom( new InternetAddress( USER_NAME ) );

                //---------------------------------------------

                InternetAddress[] toAddress = new InternetAddress[to.length];

                // To get the array of toaddresses
                for (int i = 0; i < to.length; i++) {
                    toAddress[i] = new InternetAddress( to[i] );
                }

                // Set To: header field of the header.
                for (int i = 0; i < toAddress.length; i++) {
                    message.addRecipient( Message.RecipientType.TO, toAddress[i] );
                }

                InternetAddress[] ccAddress = new InternetAddress[cc.length];

                // To get the array of ccaddresses
                for (int i = 0; i < cc.length; i++) {
                    ccAddress[i] = new InternetAddress( cc[i] );
                }

                // Set cc: header field of the header.
                for (int i = 0; i < ccAddress.length; i++) {
                    message.addRecipient( Message.RecipientType.CC, ccAddress[i] );
                }


                // Set Subject: header field
                message.setSubject( subject );

                // Now set the date to actual message
                message.setSentDate( new Date() );

                // Now set the actual message
                message.setContent( body, "text/html" );


                //---------------------------------------------STEP 3---------------------------------------------

                System.out.println( "\n\n 3rd ===> Get Session and Send Mail" );
                // Send message
                Transport transport = session.getTransport( MAIL_SERVER );
                transport.connect( SMTP_HOST_NAME, SMTP_HOST_PORT, USER_NAME, PASSWORD );
                transport.sendMessage( message, message.getAllRecipients() );
                transport.close();
                System.out.println( "Sent Message Successfully...." );
            } catch (AddressException ae) {
                ae.printStackTrace();
            } catch (MessagingException me) {
                me.printStackTrace();
            }

            //---------------------------------------------------------------------------------------------------


            System.out.println( "Email Sent....!" );
        } catch (Exception ex) {
            System.out.println( "Could not send email....!" );
            ex.printStackTrace();
        }



    }







}


