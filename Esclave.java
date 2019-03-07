
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;


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
    
}
