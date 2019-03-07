
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ameni
 */
public class Server {
    private ServerSocket serverSocket;
    private Socket socket;
    public static final int port = 33333;
    private static final int poolSize = 10;
    private ExecutorService pool = null;
    private Boolean isFinished = false;
    public List<ListeDeDiffusion> AllList = new ArrayList(); 
    
    //Getters&setters
    public ServerSocket getServerSocket() {
        return serverSocket;
    }

    public void setServerSocket(ServerSocket serverSocket) {
        this.serverSocket = serverSocket;
    }

    public Socket getSocket() {
        return socket;
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
    }

    public ExecutorService getPool() {
        return pool;
    }

    public void setPool(ExecutorService pool) {
        this.pool = pool;
    }

    public Boolean getIsFinished() {
        return isFinished;
    }

    public void setIsFinished(Boolean isFinished) {
        this.isFinished = isFinished;
    }
    
    public List<ListeDeDiffusion> getAllList() {
        return AllList;
    }

    public void setAllList(List<ListeDeDiffusion> AllList) {
        this.AllList = AllList;
    }
    
    //constructor
    public Server(int port, int size) {
       try {
           serverSocket = new ServerSocket(port,size);
           pool = Executors.newFixedThreadPool(poolSize);
        } catch (IOException ex) {
            Logger.getLogger(Server.class.
                    getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    public void ManageRequest(){
        while (!isFinished) {
            try { 
                pool.execute(new Esclave(serverSocket.accept(),this));
            }
            catch (IOException e) {System.out.println(e);}
            finally {
                try { if (socket != null) socket.close();}
                catch (IOException e) {}
            }
        }
    }
    
    public void ManageList(ListeDeDiffusion l)
    {
        AllList.add(l);
    }
    
    void DeleteList(ListeDeDiffusion n) {
        AllList.remove(n);
    }
    
}
