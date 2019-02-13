package inf112.skeleton.app;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import inf112.skeleton.app.gameclasses.Drop;
import inf112.skeleton.app.gameclasses.DropV2;
import inf112.skeleton.app.gameclasses.TestTiledMap;

public class Main {
    public static void main(String[] args) {
        /*LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
        cfg.title = "Scene2d Image Manipulation";
        cfg.useGL30 = true;
        cfg.width = 1680;
        cfg.height = 1050;

        // new LwjglApplication(new HelloWorld(), cfg);
        // new LwjglApplication(new ImageTest(), cfg);
        // new LwjglApplication(new LabelAndFont(), cfg);
        new LwjglApplication(new TestTiledMap(), cfg);*/

        LwjglApplicationConfiguration cfg2 = new LwjglApplicationConfiguration();

        cfg2.title = "Drop";
        cfg2.width = 800;
        cfg2.height = 480;

        // new LwjglApplication(new Drop(), cfg2);
        new LwjglApplication(new DropV2(), cfg2);
    }
}
