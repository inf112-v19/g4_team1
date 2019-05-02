package inf112.skeleton.app.base.MP;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import inf112.skeleton.app.base.actors.Player;

import java.io.IOException;
import java.net.InetAddress;
import java.util.ArrayList;

public class SimpleClient implements Runnable {
    private Client client;
    private String string;
    private boolean connected;
    public String status;

    public SimpleClient() {
        Thread gameThread = new Thread(this);
        //final boolean[] gotMessage = new boolean[1];

        try {
            client = new Client();
            client.start();

            Kryo kryo = client.getKryo();
            kryo.register(String.class);
            kryo.register(ArrayList.class);

            client.addListener(new Listener() {
                @Override
                public void received (Connection c, Object object) {
//                    if (object instanceof String) {
//                        string = (String) object;
//                        System.out.println("Received " + string);
//                        gotMessage[0] = true;
//                    }
                }

                @Override
                public void disconnected (Connection c) {
                    System.out.println("Disconnected.");
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }

        gameThread.start();

        // try to connect three times
        for (int i = 0; i < 3; i++) {
            InetAddress address = client.discoverHost(54634, 5000);
            System.out.println(address);

            if (!connected) {
                try {
                    client.connect(5000, "127.0.0.1", 54634);
                    System.out.println("Connected to server @ 54634.");
                    connected = true;
                } catch (IOException ex) {
                    System.out.println("Could not connect to the server, retrying ...");
                    System.out.println(ex.getMessage());
                    connected = false;
                }
            }
        }

//        while (connected) {
//            Thread.sleep(5000);
//            System.out.println("5 seconds passed.");
//            client.sendTCP("Sending " + string);
//
//            if (gotMessage[0]) {
//                System.out.println("Received " + string);
//                gotMessage[0] = false;
//            }
//        }
    }

    public boolean sendMessage(Object object) {
        if (object instanceof ArrayList) {
            client.sendTCP(object);
            return true;
        }

        return false;
    }

    public static void main(String[] args) {
        try {
            new SimpleClient();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {

    }
}
