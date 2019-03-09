import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import java.util.Optional;
import java.util.Properties;
import java.util.stream.Collectors;

public class Esclave implements  Runnable{
    private final Socket socket;
    private  Server server ;

    public Esclave(Socket socket) {
        this.socket = socket;
    }

    public Esclave(Socket socket, Server server){
        this.socket = socket;
        this.server = server;

    }

    /* Esclave queries */
    public boolean createList(String[] commande) throws AddressException {
        ListeDeDiffusion listeDeDiffusion =
                new ListeDeDiffusion(commande[1],commande[2],commande[3],commande[4]);
        return  server.addListeDifusion(listeDeDiffusion);
    }

    public void afficheList(){
        ArrayList<ListeDeDiffusion> AllList = server.getAllList();
        int i =1;
        System.out.println("Il y'a "+ AllList.size()+ " liste de diffusion :");
        AllList.stream().forEach(e-> System.out.println(e));
    }

    private boolean deleteList(String nomlist, String pswd) {
        ArrayList<ListeDeDiffusion> allList = server.getAllList();
        boolean deleted =
                allList.removeIf(l->l.getPassword().equals(pswd) && l.getNom().equals(nomlist));
        if(deleted){
            System.out.println("liste supprimé avec succés!");
            this.afficheList();
        }else
            System.out.println("nom de liste ou mdp incorrecte!");

        return deleted;
    }


    private boolean subscribeList(String liste, String mail) throws AddressException {
        ArrayList<ListeDeDiffusion> allList = server.getAllList();
        boolean added = false ;
        for(ListeDeDiffusion list : allList){
            if(list.getNom().equals(liste)){
                added = list.addAbonne(mail);
                break ;
            }
        }
        if (added)
            System.out.println("vous avez été ajouté !");
        else
            System.out.println("ERROR !");
        return added;
    }

    private boolean unscribeList(String liste, String Mail) {
        ArrayList<ListeDeDiffusion> allList = server.getAllList();
        boolean deleted = allList.removeIf(list->list.getNom().equals(liste));
        if (deleted)
            System.out.println("vous êtes désabonné de la liste de diffusion: "+liste);
        return deleted;
    }


    private ListeDeDiffusion GetListeDeDiffusion(String nomListe) {

        Optional<ListeDeDiffusion> list =
                server.getAllList().stream().filter(l -> l.getNom().equals(nomListe)).findFirst();
        if (list.isPresent()){
            ListeDeDiffusion listeDeDiffusion = list.get();
            return listeDeDiffusion;
        }
        return null;
    }

    private void sendMail(String NameList, String from, String password, String MailSubject, String MailBody){
        ListeDeDiffusion liste = GetListeDeDiffusion(NameList);
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
            message.setFrom(new InternetAddress(from));
            List<Personne> Abonnes = liste.getListAbonnes();
            List<String> to =new ArrayList();
            String receivers;
            for(Personne p : Abonnes)
            {
                to.add(p.getMail());
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


    @Override
    public void run() {
        BufferedReader input = null; // flux en lecture
        try {
            input = new BufferedReader(
                    new InputStreamReader(socket.getInputStream(),
                            "8859_1"),1024);
        } catch (IOException e) {
            e.printStackTrace();
        }

        StringBuffer sb = new StringBuffer();

        try {
            sb.append(input.readLine());
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(sb);

        String[] commande = sb.toString().split(" ");
        try {
            switch (commande[0]) {
            case "create_list":
                try {
                    createList(commande);
                } catch (AddressException e1) {
                    e1.printStackTrace();
                }
                break;
            case "remove_list":
                String nom = commande[1];
                String pswd = commande[2];
                deleteList(nom,pswd);
                break;
            case "subscribe_list":
                String liste = commande[1];
                String mail = commande[2];
                try {
                    subscribeList(liste,mail);
                } catch (AddressException e1) {
                    e1.printStackTrace();
                }
                break;
            case "unscribe_list":
                String Liste = commande[1];
                String Mail = commande[2];
                unscribeList(Liste,Mail);
                break;
            case "send_email_to_list":
                Msn.emails= (ArrayList<String>) GetListeDeDiffusion(commande[1]).getListAbonnes().stream().map(p-> p.getMail()).collect(Collectors.toList());;
                if (!commande[6].equals("o") && !commande[6].equals("O")){
                    Msn.EmailSender = commande[2];
                    Msn.Password = commande[3];
                }
                Msn.sujet = commande[4];
                Msn.messageCorp = commande[5];
                Msn.sendMail();
                break;
            case "afficher_list":
                afficheList();
                break;
            default: ;
        }
    } finally {
        try { if (socket != null) socket.close();}
        catch (IOException e) {}
    }
}


}

