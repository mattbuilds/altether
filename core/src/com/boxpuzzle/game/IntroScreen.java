package com.boxpuzzle.game;

/**
 * Created by Matt on 12/7/14.
 */
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;
import com.badlogic.gdx.utils.viewport.FitViewport;

import java.util.ArrayList;
import java.util.List;

public class IntroScreen implements Screen {

    private BoxPuzzle game; // Note it's "MyGame" not "Game"
    private SpriteBatch batch;
    private OrthographicCamera camera;
    private BitmapFont font, big_font;



    // constructor to keep a reference to the main Game class
    public IntroScreen(BoxPuzzle game, int game_width, int game_height){
        this.game = game;
        batch = new SpriteBatch();
        camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        camera.position.set(camera.viewportWidth/2f, camera.viewportHeight/2f, 0);
        camera.update();
        big_font = new BitmapFont(Gdx.files.internal(this.game.resolution + "/font.fnt"), Gdx.files.internal(this.game.resolution + "/font.png"), false);
        font = new BitmapFont(Gdx.files.internal(this.game.resolution + "/num_font.fnt"), Gdx.files.internal(this.game.resolution + "/num_font.png"), false);

    }



    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(71/255f, 81/255f, 93/255f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.setProjectionMatrix(camera.combined);

        batch.begin();

        big_font.draw(batch, "All Together",
                  camera.viewportWidth/2f - big_font.getBounds("All Together").width/2f,
                  camera.viewportHeight/2f + 5f*big_font.getBounds("All Together").height);
        font.draw(batch, "Play",
                camera.viewportWidth/2f - font.getBounds("Play").width/2f,
                camera.viewportHeight/2f + 2f*font.getBounds("Play").height);
        /*font.draw(batch, "How to Play",
                camera.viewportWidth/2f - font.getBounds("How to Play").width/2f,
                camera.viewportHeight/2f - .5f*font.getBounds("How to Play").height);
        font.draw(batch, "Settings",
                camera.viewportWidth/2f - font.getBounds("Settings").width/2f,
                camera.viewportHeight/2f - 3f*font.getBounds("Settings").height);
        */
        batch.end();
    }

    public void touch(int x, int y){
        System.out.println(x);
        if (x > camera.viewportWidth/2f - font.getBounds("Play").width/2f &&
            x < camera.viewportWidth/2f - font.getBounds("Play").width/2f + font.getBounds("Play").width &&
            y > camera.viewportHeight/2f + 2f*font.getBounds("Play").height - font.getBounds("Play").height &&
            y < camera.viewportHeight/2f + 2f*font.getBounds("Play").height ){
            this.game.setMenu();
        }
    }


    @Override
    public void resize(int width, int height) {

    }


    @Override
    public void show() {
        // called when this screen is set as the screen with game.setScreen();
    }


    @Override
    public void hide() {
        // called when current screen changes from this to a different screen
    }


    @Override
    public void pause() {
    }


    @Override
    public void resume() {
    }


    @Override
    public void dispose() {
        // never called automatically
    }

}
