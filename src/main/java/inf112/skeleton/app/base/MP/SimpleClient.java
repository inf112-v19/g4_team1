package inf112.skeleton.app.base.MP;

import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;

public class SimpleClient implements Runnable {
    private Client client;
    private String string;
    private boolean connected;
    public String status;
    private String host;

    public SimpleClient(String h) throws InterruptedException, UnknownHostException {
        Thread gameThread = new Thread(this);
        final boolean[] gotMessage = new boolean[1];
        host = h;

        try {
            client = new Client();
            client.start();

            Kryo kryo = client.getKryo();
            kryo.register(String.class);
            kryo.register(ArrayList.class);

            client.addListener(new Listener() {
                @Override
                public void received (Connection c, Object object) {
                    if (object instanceof String) {
                        string = (String) object;
                        System.out.println("Received " + string);
                        gotMessage[0] = true;
                    }
                }

                @Override
                public void disconnected (Connection c) {
                    System.out.println("Disconnected.");
                    connected = false;
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }

        gameThread.start();

        InetAddress hostAddress = InetAddress.getByName(host);

        // try to connect three times
        for (int i = 0; i < 3; i++) {
            if (!connected) {
                Thread.sleep(5000);
                try {
                    client.connect(5000, hostAddress, 54634, 54635);
                    status = "Connected to server @ " + hostAddress.getHostAddress() + ":54634";
                    System.out.println(status);
                    connected = true;
                    break;
                } catch (IOException ex) {
                    status = ex.getMessage();
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

//    public static void main(String[] args) {
//        try {
//            new SimpleClient("10.111.19.61");
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }

    @Override
    public void run() {

    }
}
