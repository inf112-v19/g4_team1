package inf112.skeleton.app;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

public class Main {
    public static void main(String[] args) {
        LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
        cfg.title = "Scene2d Image Manipulation";
        cfg.width = 1680;
        cfg.height = 1050;

        // new LwjglApplication(new HelloWorld(), cfg);
        new LwjglApplication(new ImageTest(), cfg);
    }
}
