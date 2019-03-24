import java.io.DataInputStream;
import java.io.DataOutputStream;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLServerSocket;
import javax.net.ssl.SSLServerSocketFactory;
import java.security.Security;
import com.sun.net.ssl.internal.ssl.Provider;
import java.util.*;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author KRICH
 */
public class Server {
    private SSLServerSocket serverSocket;
    private SSLSocket socket;
    public static final int port = 35786;
    private static final int poolSize = 10;
    private ExecutorService pool = null;
    private Boolean isFinished = false;
    public List<ListeDeDiffusion> AllList = new ArrayList();


    //constructor
    public Server(int port, int size) {
	       Security.addProvider(new Provider());
        //specifing the keystore file which contains the certificate/public key and the private key
        System.setProperty("javax.net.ssl.keyStore","myKeyStore.jks");
        //specifing the password of the keystore file
        System.setProperty("javax.net.ssl.keyStorePassword","123456");
        //This optional and it is just to show the dump of the details of the handshake process 
        System.setProperty("javax.net.debug","all");       

	 try {

		   //SSLServerSocketFactory establishes the ssl context and and creates SSLServerSocket 
            SSLServerSocketFactory sslServerSocketfactory = (SSLServerSocketFactory)SSLServerSocketFactory.getDefault();
            //Create SSLServerSocket using SSLServerSocketFactory established ssl context
            SSLServerSocket serverSocket = (SSLServerSocket)sslServerSocketfactory.createServerSocket(port,size);
            System.out.println("Echo Server Started & Ready to accept Client Connection");
            //Wait for the SSL client to connect to this server
		socket = (SSLSocket)serverSocket.accept();		

            //Create InputStream to recive messages send by the client
        //    DataInputStream inputStream = new DataInputStream(sslSocket.getInputStream());
            //Create OutputStream to send message to client
          //  DataOutputStream outputStream = new DataOutputStream(sslSocket.getOutputStream());
          //  outputStream.writeUTF("Hello Client, Say Something!");
            //Keep sending the client the message you recive unless he sends the word "close"
		
            pool = Executors.newFixedThreadPool(poolSize);
        } catch (IOException ex) {
            Logger.getLogger(Server.class.
                    getName()).log(Level.SEVERE, null, ex);
        }

    }


    //Getters&setters
    public ServerSocket getServerSocket() {
        return serverSocket;
    }

    public void setServerSocket(SSLServerSocket serverSocket) {
        this.serverSocket = serverSocket;
    }

    public Socket getSocket() {
        return socket;
    }

    public void setSocket(SSLSocket socket) {
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


    public void ManageRequest(){
        while (!isFinished) {
            try {
                pool.execute(new Esclavessl((SSLSocket)serverSocket.accept(),this));
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
