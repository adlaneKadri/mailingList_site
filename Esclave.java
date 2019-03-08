
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;


public class Esclave implements Runnable {
    private final Socket socket;
    private final Server serveur;
    
    Esclave(Socket socket, Server serveur) {
        this.socket = socket;
        this.serveur = serveur;
    }

    @Override
    public void run() {
        try { 
            BufferedReader input = new BufferedReader(
                new InputStreamReader(socket.getInputStream(),
                            "8859_1"),1024); // flux en lecture
            StringBuffer sb = new StringBuffer();
            sb.append(input.readLine());
            System.out.println(sb);
            String[] commande = sb.toString().split(" ");
            switch (commande[0]) {
                case "create_list": 
                    System.out.println("im heree!");
                    createList(commande);
                    break;
                case "remove_list":
                    String nom = commande[1];
                    String pswd = commande[2];
                    deleteList(nom,pswd);
                    break;
                case "subscribe_list":
                    String liste = commande[1];
                    String mail = commande[2];
                    subscribeList(liste,mail);
                    break;
                case "unscribe_list":
                    String Liste = commande[1];
                    String Mail = commande[2];
                    unscribeList(Liste,Mail);
                    break;
                case "send_email_to_list":
                    String _Liste = commande[1];
                    String sender = commande[2];
                    String Pswd = commande[3];
                    String subject = commande[4];
                    String body = commande[5];
                    sendMail(_Liste,sender,Pswd,subject,body);
                    break;
                case "afficher_list":
                    afficheList();
                    break;
                default: ;
            }
        }
        catch (IOException e) {System.out.println(e);}
        finally {
            try { if (socket != null) socket.close();}
            catch (IOException e) {}
        }    }

    public void createList(String[] commande) {
        ListeDeDiffusion L=new ListeDeDiffusion(commande[1],commande[2],commande[3],commande[4]);
        serveur.ManageList(L);
    }
    
    public void afficheList(){
        List<ListeDeDiffusion> AllList = serveur.getAllList();
        int i =1;
        System.out.println("Il y'a "+ AllList.size()+ " liste de diffusion :");
        for(ListeDeDiffusion n : AllList)
            {System.out.println("liste numero: " + i + "\n "
            + "Nom: "+ n.nomListe + ", theme: "+ n.getTheme()
            +" , nombre d'abonnées: "+ n.getAbonnes().size());
            i++;}
        
    }

    private void deleteList(String nom, String pswd) {
        List<ListeDeDiffusion> AllList =serveur.getAllList();
        boolean deleted = false;
        searchloop:
        for(ListeDeDiffusion n : AllList)
        {
            if((n.getNomListe().equals(nom)) && (n.getPassword().equals(pswd)))
            {
                System.out.println("liste trouvé!");
                serveur.DeleteList(n);
                System.out.println("liste supprimé avec succés!");
                afficheList();
                deleted=true;
                break searchloop;
            }     
        }
        if(!deleted) System.out.println("nom de liste ou mdp incorrecte!");   
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
    
    private void sendMail(String NameList, String from, String password, String MailSubject, String MailBody)
    {
        ListeDeDiffusion liste = GetList(NameList);
        if (liste ==null)
        {
            System.out.println("liste de diffusion n'existe pas!!");
            return;
        }
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
	props.put("mail.smtp.starttls.enable", "true");
	props.put("mail.smtp.host", "smtp.gmail.com");
	props.put("mail.smtp.port", "587");     
        Session session = Session.getInstance(props,
		  new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(from, password);
			}
		  });
        try {
         // Create a default MimeMessage object.
         MimeMessage message = new MimeMessage(session);
         // Set From: header field of the header.
         message.setFrom(new InternetAddress(from));
         // Set To: header field of the header.
         List<personne> Abonnes = liste.getAbonnes();
         List<String> to =new ArrayList();
         String receivers;
         for(personne p : Abonnes)
         {
             to.add(p.getMailAdress());
         }
         receivers = String.join(",", to);
         InternetAddress[] parse = InternetAddress.parse(receivers , true);
         message.setRecipients(javax.mail.Message.RecipientType.TO,  parse);
         // Set Subject: header field
         message.setSubject(MailSubject);
         // Now set the actual message
         message.setText(MailBody);
         // Send message
         Transport.send(message);
         System.out.println("Email envoyé.");
      } catch (MessagingException mex) {
         mex.printStackTrace();
      }
   }        
}
