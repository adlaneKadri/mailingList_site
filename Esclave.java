import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;

public class Esclave implements  Runnable{
    private final Socket socket;

    public Esclave(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {


        InputStream in = null;
        try {
            in = socket.getInputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }

        int i = 0;
        while (true){
            try {
                if (!((i = in.read())!= -1)) break;
            } catch (IOException e) {
                e.printStackTrace();
            }
            System.out.write(i);
        }

        try {
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /* Classe Thread */
}
