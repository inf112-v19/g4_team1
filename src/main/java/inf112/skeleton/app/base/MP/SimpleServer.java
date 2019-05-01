package inf112.skeleton.app.base.MP;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;

public class SimpleServer implements Runnable {
    public boolean running = true;
    private String string;
    public boolean gotMessage;

    private int port = 54634;
    private Server server;

    public SimpleServer() throws Exception {
        server = new Server();
        Thread gameThread = new Thread(this);

        Kryo kryo = server.getKryo();
        kryo.register(String.class);

        server.addListener(new Listener(){
            @Override
            public void received(Connection c, Object object) {

                // get the message
                if (object instanceof String) {

                    string = (String) object;
                    gotMessage = true;

                    // send the response if received the message
                    c.sendTCP("received " + string + ". Responding.");
                }
            }

            // handle the disconnects
            @Override
            public void disconnected (Connection c) {
                System.out.println("Disconnected.");
            }
        });

        server.bind(port);
        server.start();
        gameThread.start();

        while (running) {
            Thread.sleep(5000);
            System.out.println("waiting for messages...");
            if (gotMessage) {
                System.out.println("got message " + string);
                gotMessage = false;
            }
        }

        System.out.println("Server started.");
    }

    @Override
    public void run() {

    }

    public static void main(String[] args) {
        try {
            new SimpleServer();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
