import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.Socket;
import java.util.function.Consumer;

public class Client extends Thread{
    int port;
    Socket socketClient;
    ObjectOutputStream out;
    ObjectInputStream in;

    private Consumer<Serializable> callback;

    Client(Consumer<Serializable> call, int port){
        callback = call;
        this.port = port;
    }

    public void run() {

        try {
            socketClient = new Socket("127.0.0.1", port);
            out = new ObjectOutputStream(socketClient.getOutputStream());
            in = new ObjectInputStream(socketClient.getInputStream());
            socketClient.setTcpNoDelay(true);
            System.out.println("Client started on port: " + port);
        }
        catch(Exception e) {
            System.out.println("Error creating socket");
        }

        while(true) {
            try {
                Message message = (Message) in.readObject();
                callback.accept(message);
            }
            catch(Exception e) {}
        }

    }

    public void send_message(Object object) {
        try {
            out.writeObject(object);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
