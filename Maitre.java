import java.util.concurrent.*;
import java.net.*;
import java.io.*;

public class Maitre {
/* Class Server */
    private  ServerSocket serverSocket;
    private ExecutorService pool;

    public Maitre(int port, int poolSize) {
        try {
            serverSocket = new ServerSocket(port);
            pool = Executors.newFixedThreadPool(poolSize);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    public void  run() throws IOException {
        while (true){
            pool.execute(new Esclave(serverSocket.accept()));
        }
    }

    public static void main(String[] args) throws IOException {
        Maitre maitre = new Maitre(3344,5);
        maitre.run();
    }
}
