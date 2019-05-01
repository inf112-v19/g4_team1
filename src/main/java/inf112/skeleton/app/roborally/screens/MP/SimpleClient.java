package inf112.skeleton.app.roborally.screens.MP;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;

import java.io.IOException;

public class SimpleClient {
    private Client client;

    private boolean connected = false;

    public SimpleClient() {

        try {
            client = new Client();
            client.start();

            Kryo kryo = client.getKryo();
            kryo.register(String.class);

            client.addListener(new Listener() {
                public void received (Connection c, Object object) {
                    if (object instanceof String) {
                        String string = (String) object;
                        System.out.println("Received " + string);
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }

        new Thread("Connect") {
            @Override
            public void run() {
                try {
                    client.connect(5000, "127.0.0.1", 54634);
                } catch (IOException ex) {
                    ex.printStackTrace();
                    System.exit(1);
                }
            }
        } .start();
    }

    public static void main(String[] args) {
        try {
            new SimpleClient();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
