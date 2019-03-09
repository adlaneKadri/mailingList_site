import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {

    private ServerSocket serverSocket;
    private ExecutorService pool=null;
    public static final int port = 33333;
    public static final int poolsize= 10;
    private Socket socket;
    public ArrayList<ListeDeDiffusion> AllList;

    public Server(int port, int size) {
        try {
            AllList = new ArrayList();
            serverSocket = new ServerSocket(port,size);
            pool = Executors.newFixedThreadPool(poolsize);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void manageRequests(){
        while (true) {
            try {
                pool.execute(new Esclave(serverSocket.accept(),this));
            }
            catch (IOException e) {System.out.println(e);}
            finally {
                try { if (socket != null) socket.close();}
                catch (IOException e) {
                    System.out.println("no socket");
                }
            }
        }
    }

    /* Getters and setters */
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

    public static int getPort() {
        return port;
    }

    public static int getPoolsize() {
        return poolsize;
    }

    public ArrayList<ListeDeDiffusion> getAllList() {
        return AllList;
    }

    public void setPool(ExecutorService pool) {
        this.pool = pool;
    }

    public void setAllList(ArrayList<ListeDeDiffusion> allList) {
        AllList = allList;
    }

    public boolean addListeDifusion(ListeDeDiffusion listeDeDiffusion){
        return AllList.add(listeDeDiffusion);
    }

    public boolean removeListeDifusion(ListeDeDiffusion listeDeDiffusion){
        return  AllList.remove(listeDeDiffusion);
    }

}
