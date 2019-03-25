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
public class Serverssl {
    private SSLServerSocket sslServerSocket;
    private SSLSocket sslSocket;
    public static final int port = 33333;
    private static final int poolSize = 10;
    private ExecutorService pool = null;
    private Boolean isFinished = false;
    public List<ListeDeDiffusion> AllList = new ArrayList();


    //constructor
    public Serverssl(int port, int size) {
	
            AllList = new ArrayList();



    }


    //Getters&setters
    public ServerSocket getServerSocket() {
        return sslServerSocket;
    }

    public void setServerSocket(SSLServerSocket sslServerSocket) {
        this.sslServerSocket = sslServerSocket;
    }

    public Socket getSocket() {
        return sslSocket;
    }

    public void setSocket(SSLSocket sslSocket) {
        this.sslSocket = sslSocket;
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


	 try

	{

            Security.addProvider(new Provider());
            //specifing the keystore file which contains the certificate/public key and the private key
            System.setProperty("javax.net.ssl.keyStore","myKeyStore.jks");
            //specifing the password of the keystore file
            System.setProperty("javax.net.ssl.keyStorePassword","123456");
            //This optional and it is just to show the dump of the details of the handshake process
            System.setProperty("javax.net.debug","all");
            SSLServerSocketFactory sslServerSocketFactory = (SSLServerSocketFactory)SSLServerSocketFactory.getDefault();
            SSLServerSocket sslServerSocket = (SSLServerSocket)sslServerSocketFactory.createServerSocket(port);

            System.out.println("je suis la pt con !! ");
          //  sslSocket = (SSLSocket)sslServerSocket.accept();
            //Create InputStream to recive messages send by the client
          //  DataInputStream inputStream = new DataInputStream(sslSocket.getInputStream());
            //Create OutputStream to send message to client
          //  DataOutputStream outputStream = new DataOutputStream(sslSocket.getOutputStream());

            //outputStream.writeUTF("je suis la pt con !!! ");
            //Keep sending the client the message you recive unless he sends the word "close"

            Esclavessl es = new Esclavessl( (SSLSocket)sslServerSocket.accept(),this);
            Thread tt = new Thread(es);
            tt.start();
          }  catch(Exception ex)  {
                System.err.println("Error Happened : "+ex.toString());
            }
            /*

            while(true)
            {
                String recivedMessage = inputStream.readUTF();
                System.out.println("Client Said : " + recivedMessage);
                if(recivedMessage.equals("close"))
                {
                    outputStream.writeUTF("Bye");
                    outputStream.close();
                    inputStream.close();
                    sslSocket.close();
                    sslServerSocket.close();
                    break;
                }
                else
                {
                    outputStream.writeUTF("You Said : "+recivedMessage);
                }
            }
        }*/


    }



	/*   while(true)
            {
                String recivedMessage = inputStream.readUTF();
                System.out.println("Client Said : " + recivedMessage);
                if(recivedMessage.equals("close"))
                {
                    outputStream.writeUTF("Bye");
                    outputStream.close();
                    inputStream.close();
                    sslSocket.close();
                    sslServerSocket.close();
                    break;
                }
                else
                {
                    outputStream.writeUTF("You Said : "+recivedMessage);
                }
            }
        }
        catch(Exception ex)
        {
            System.err.println("Error Happened : "+ex.toString());
        }*/




	/*
        while (!isFinished) {
            try {

                pool.execute(new Esclavessl(serverSocket.accept(),this));
            }
            catch (IOException e) {System.out.println(e);}
            finally {
                try { if (socket != null) socket.close();}
                catch (IOException e) {}
            }
        }*/
    public void ManageList(ListeDeDiffusion l)
    {
        AllList.add(l);
    }

    void DeleteList(ListeDeDiffusion n) {
        AllList.remove(n);
    }

}
