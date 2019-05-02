package inf112.skeleton.app.base.MP;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;
import inf112.skeleton.app.base.actors.Player;

import java.util.ArrayList;

public class SimpleServer implements Runnable {
    public boolean running;
    public String string, status;
    public boolean gotMessage;
    public int numOfPlayers;
    private int port = 54634;
    private int udp = 54635;
    private ArrayList<String> playerNames = new ArrayList<>();
    public Server server;

    public SimpleServer() throws Exception {
        System.out.println("Creating a server...");
        status = "Not connected";
        server = new Server();
        Thread gameThread = new Thread(this);

        Kryo kryo = server.getKryo();
        kryo.register(String.class);
        kryo.register(ArrayList.class);

        server.addListener(new Listener() {
            @Override
            public void received(Connection c, Object object) {
                // get the status
                if (object instanceof String) {
                    string = (String) object;
                    playerNames.add(string);
                    string = null;

                    //gotMessage = true;
                    // send the response if received the status
                    //c.sendTCP("Server received " + string + " from client. Responding back with this message.");
                } else if (object instanceof ArrayList) {
                    ArrayList<Player> players = (ArrayList<Player>) object;

                } else if (object instanceof Integer) {
                    numOfPlayers = (Integer) object;
                }
            }

            // handle the disconnects
            @Override
            public void disconnected (Connection c) {
                System.out.println("Disconnected.");
                running = false;
            }
        });

        server.bind(port, udp);
        server.start();
        gameThread.start();
        running = true;
        status = "Server started. Waiting for other connections...";

//        while (running) {
//            Thread.sleep(5000);
//
//            System.out.println("waiting for messages...");
//            if (gotMessage) {
//                System.out.println("got status " + string);
//                gotMessage = false;
//            }
//        }

        System.out.println("Server started.");
    }
    @Override
    public void run() {

    }

//    public static void main(String[] args) {
//        try {
//            new SimpleServer();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
}
