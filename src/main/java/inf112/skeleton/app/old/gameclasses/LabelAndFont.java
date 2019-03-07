package inf112.skeleton.app.old.gameclasses;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class LabelAndFont implements ApplicationListener {
    private Stage stage;

    @Override
    public void create() {
        stage = new Stage(new ScreenViewport());
        Texture texture = new Texture(Gdx.files.internal("assets/image.jpg"));

        int gridColumns = 12;
        int row_height = Gdx.graphics.getWidth() / 13;
        int col_width = Gdx.graphics.getWidth() / 12;
        addBackground(gridColumns);

        // assign the bitmap font
        Label.LabelStyle label1Style = new Label.LabelStyle();
        BitmapFont myFont = new BitmapFont(Gdx.files.internal("assets/bitmapfont/segoeUI32_java.fnt"));
        label1Style.font = myFont;
        label1Style.fontColor = Color.RED;

        // simple image demo
        Image image1 = new Image(texture);
        image1.setSize(texture.getWidth()/1.5f, texture.getHeight()/1.5f);
        image1.setPosition(Gdx.graphics.getWidth()/3f - image1.getWidth()/2f,
                Gdx.graphics.getHeight()*2f/3f - image1.getHeight()/2f);
        stage.addActor(image1);

        // bitmap font demo
        Label label1 = new Label("Title (BitmapFont)", label1Style);
        label1.setSize(Gdx.graphics.getWidth(), row_height);
        label1.setPosition(Gdx.graphics.getWidth()/3f - image1.getWidth()/2f,
                Gdx.graphics.getHeight() - row_height*2f);
        // label1.setAlignment(Align.center);
        stage.addActor(label1);


        // image rotation demo
        Image image2 = new Image(texture);
        image2.setSize(texture.getWidth()/1.5f, texture.getHeight()/1.5f);
        image2.setPosition(Gdx.graphics.getWidth()*2f/3f - image2.getWidth()/2f,
                Gdx.graphics.getHeight()*2f/3f - image2.getHeight()/2f);
        image2.setOrigin(image2.getWidth()/2f, image2.getHeight()/2f);
        image2.rotateBy(45);
        stage.addActor(image2);


        // image resize demo
        Image image3 = new Image(texture);
        image3.setSize(texture.getWidth()/2f, texture.getHeight()/2f);
        image3.setPosition(Gdx.graphics.getWidth()/3f - image3.getWidth()/2f,
                Gdx.graphics.getHeight()/3f - image3.getHeight()/2f);
        stage.addActor(image3);

        // freetype font demo
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(
                Gdx.files.internal("assets/ttffont/segoe.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter =
                new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 32;
        parameter.borderWidth = 1;
        parameter.color = Color.CORAL;
        parameter.shadowOffsetX = 1;
        parameter.shadowOffsetY = 1;
        parameter.shadowColor = new Color(0, 0.5f, 0, 0.75f);
        BitmapFont fontSegoe32 = generator.generateFont(parameter);
        generator.dispose();

        Label.LabelStyle label2Style = new Label.LabelStyle();
        label2Style.font = fontSegoe32;

        Label label2 = new Label("Title (TrueTypeFont Segoe, size 32)", label2Style);
        label2.setSize(Gdx.graphics.getWidth()/12f * 5, row_height);
        label2.setPosition(Gdx.graphics.getWidth()/3f - image1.getWidth()/2f,
                Gdx.graphics.getHeight() - row_height * 5);
        stage.addActor(label2);


        // repeating image demo
        texture.setWrap(Texture.TextureWrap.MirroredRepeat, Texture.TextureWrap.MirroredRepeat);
        TextureRegion textureRegion = new TextureRegion(texture);
        textureRegion.setRegion(2, 2, texture.getWidth()*2f, texture.getHeight()*2f);
        Image image4 = new Image(textureRegion);
        image4.setSize(300, 200);
        image4.setPosition(Gdx.graphics.getWidth()*2f/3f - image4.getWidth()/2f,
                Gdx.graphics.getHeight()/3f - image4.getHeight()/2f);
        stage.addActor(image4);


        // font demo using the skin (css style)
        Skin mySkin = new Skin(Gdx.files.internal("assets/skin/comic-ui.json"));

        Label label3 = new Label("Title (skin \"Comic\")", mySkin, "title");
        label3.setSize(Gdx.graphics.getWidth()/12f * 5, row_height);
        label3.setPosition(Gdx.graphics.getWidth()*2f/3f - image4.getWidth()/2f,
                Gdx.graphics.getHeight() - row_height * 5);
        stage.addActor(label3);

        // same but with text wrap
        Label label4 = new Label("Title (skin \"Comic\" with wrap) -----------------", mySkin, "title");
        label4.setSize(Gdx.graphics.getWidth()/12f * 5, row_height);
        label4.setPosition(Gdx.graphics.getWidth()*2f/3f - image4.getWidth()/2f,
                Gdx.graphics.getHeight() - row_height * 7);
        label4.setWrap(true);
        stage.addActor(label4);
    }

    // fill the background with squares
    private void addBackground(int columns) {
        Texture texture = new Texture("assets/background.jpg");
        texture.setWrap(Texture.TextureWrap.MirroredRepeat, Texture.TextureWrap.MirroredRepeat);

        TextureRegion textureRegion = new TextureRegion(texture);
        textureRegion.setRegion(0, 0, texture.getWidth()*columns,
                texture.getWidth()*columns);
        Image background = new Image(textureRegion);
        background.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getWidth());
        background.setPosition(0, Gdx.graphics.getHeight() - background.getHeight());
        stage.addActor(background);
    }

    @Override
    public void resize(int i, int i1) {

    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act();
        stage.draw();
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void dispose() {

    }
}
