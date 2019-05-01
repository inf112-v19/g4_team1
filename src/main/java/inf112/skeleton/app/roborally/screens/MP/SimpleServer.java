package inf112.skeleton.app.roborally.screens.MP;


import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;

import java.util.Random;

public class SimpleServer implements Runnable {
    public boolean running = true;
    private volatile boolean Play = true;
    private String string;
    private long mFrameDelay = 564;

    private int port = 54634;
    private Server server;

    public SimpleServer() throws Exception {
        server = new Server();
        Thread gameThread = new Thread(this);

        Kryo kryo = server.getKryo();
        server.addListener(new Listener(){
            public void received(Connection c, Object object) {

            }

            public void disconnected(Connection c) {

            }
        });

        server.bind(port);
        server.start();
        gameThread.start();

        while (running) {
            Thread.sleep(100);
        }

        System.out.println("Server started.");

    }

    @Override
    public void run() {
        while (Play) {
            System.out.println("Start sending state.");
            string = "message" + Math.random();
            System.out.println("Sending message " + string);
            server.sendToAllTCP(string);
        }

        try {
            Thread.sleep(mFrameDelay);
        } catch (InterruptedException ie) {
            ie.printStackTrace();
        }
    }

    public static void main(String[] args) {
        try {
            new SimpleServer();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
