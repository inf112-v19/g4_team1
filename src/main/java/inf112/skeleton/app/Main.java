package inf112.skeleton.app;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

public class Main {
    public static void main(String[] args) {
        LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
        cfg.title = "hello-world";
        cfg.width = 480;
        cfg.height = 320;



        //new LwjglApplication(new HelloWorld(), cfg);
        new LwjglApplication(new ImageTest());
    }
}
