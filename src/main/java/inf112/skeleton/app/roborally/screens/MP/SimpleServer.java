package inf112.skeleton.app.roborally.screens.MP;


import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;

public class SimpleServer implements Runnable {
    public boolean running = true;
    private volatile boolean Play = true;

    private int port = 74634;
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

        System.out.println("Server started");

    }

    @Override
    public void run() {
        System.out.println("Server is running.");
    }
}
