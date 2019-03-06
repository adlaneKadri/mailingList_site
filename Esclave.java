
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
    }
    
}
