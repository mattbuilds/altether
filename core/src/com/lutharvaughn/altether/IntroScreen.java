package com.lutharvaughn.altether;

/**
 * Created by Matt on 12/7/14.
 */
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.*;

public class IntroScreen implements Screen {

    private BoxPuzzle game; // Note it's "MyGame" not "Game"
    private SpriteBatch batch;
    private Sprite title, play, more, sound_off, sound_on, by;
    private OrthographicCamera camera;
    private BitmapFont font, big_font;
    private Texture red_animation_text;
    private Animation red_animation;
    private TextureRegion[] red_animation_text_reg;
    private TextureRegion currentFrame;
    private float startTime;

    // constructor to keep a reference to the main Game class
    public IntroScreen(BoxPuzzle game, int game_width, int game_height){
        this.game = game;
        batch = new SpriteBatch();
        camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        camera.position.set(camera.viewportWidth / 2f, camera.viewportHeight / 2f, 0);
        camera.update();

        this.game.sound = false;

        title = new Sprite(new Texture(this.game.resolution + "/title.png"));
        title.setPosition(camera.viewportWidth / 2f - title.getWidth() / 2f, camera.viewportHeight - title.getHeight() * 1.4f);

        play = new Sprite(new Texture(this.game.resolution + "/play.png"));
        play.setPosition(camera.viewportWidth/2f - play.getWidth()/2f, camera.viewportHeight/2f + play.getHeight());
        more = new Sprite(new Texture(this.game.resolution + "/more.png"));
        more.setPosition(camera.viewportWidth/2f - more.getWidth()/2f, camera.viewportHeight/2f - .76f * more.getHeight());
        sound_off = new Sprite(new Texture(this.game.resolution + "/sound_off.png"));
        sound_off.setPosition(camera.viewportWidth/2f - sound_off.getWidth()/2f, camera.viewportHeight/2f - 2.5f *sound_off.getHeight());
        sound_on = new Sprite(new Texture(this.game.resolution + "/sound_on.png"));
        sound_on.setPosition(camera.viewportWidth/2f - sound_on.getWidth()/2f, camera.viewportHeight/2f - 2.5f *sound_on.getHeight());
        by = new Sprite(new Texture(this.game.resolution + "/by.png"));
        by.setPosition(camera.viewportWidth - by.getWidth(), 0);

        red_animation_text = new Texture(this.game.resolution + "/red_animation.png");
        TextureRegion[][] tmp = TextureRegion.split(red_animation_text, red_animation_text.getWidth()/4, red_animation_text.getHeight()/2);
        red_animation_text_reg = new TextureRegion[8];
        int index = 0;
        for (int i=0; i < 2; i++){
            for (int j=0; j < 4; j++){
                red_animation_text_reg[index++] = tmp[i][j];
            }
        }
        red_animation = new Animation(.075f, red_animation_text_reg);
        startTime = 0f;
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(71 / 255f, 81 / 255f, 93/255f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.setProjectionMatrix(camera.combined);

        batch.begin();
        title.draw(batch);
        play.draw(batch);
        more.draw(batch);
        if (this.game.sound == false)
            sound_off.draw(batch);
        else
            sound_on.draw(batch);
        by.draw(batch);
        batch.end();
    }

    public boolean spriteTouched(Sprite sprite, int x, int y){
        if(x >=sprite.getX() && x <= sprite.getX() + sprite.getWidth() &&
                y >= sprite.getY() && y <= sprite.getY() + sprite.getHeight() ){
            return true;
        }
        return false;
    }

    public void touch(int x, int y){
        if(spriteTouched(play, x, y) )
            this.game.setMenu();
        if(spriteTouched(more, x, y))
            this.game.setScreen(this.game.moreLevels);

        if(this.game.sound == false){
            if(spriteTouched(sound_off, x, y))
                this.game.sound = true;
        } else{
            if(spriteTouched(sound_on, x, y))
                this.game.sound = false;
        }
    }


    @Override
    public void resize(int width, int height) {

    }


    @Override
    public void show() {
        // called when this screen is set as the screen with altether.setScreen();
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
