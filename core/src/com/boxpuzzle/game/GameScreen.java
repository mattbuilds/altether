package com.boxpuzzle.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.FPSLogger;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.input.GestureDetector.GestureListener;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.badlogic.gdx.utils.viewport.FitViewport;

import java.util.ArrayList;
import java.util.List;

public class GameScreen implements Screen{
    SpriteBatch batch;
    //Arrow arrow;
    FPSLogger fpsLogger;
    Texture back_text, refresh_text, previous_text, next_text, popup_text, popup_box_text;
    Sprite back, refresh, previous, next, popup, popup_box1, popup_box2, popup_box3, grey_sprite;
    BitmapFont font, popup_font;

    Player player;
    List<Box> boxes;
    Grid grid;
    Level level;
    JsonValue levels;
    private int width, height, lvl_num = 1;
    private int offset = 64;
    private float time;
    private boolean won = false;
    private OrthographicCamera camera;
    private FitViewport viewport;

    BoxPuzzle game; // Note it's "my game" not "your game"

    // constructor to keep a reference to the main Game class
    public GameScreen(BoxPuzzle game, int cam_width, int cam_height){

        //Load levels
        FileHandle file = Gdx.files.internal("levels.txt");
        String text = file.readString();
        levels = new JsonReader().parse(text);

        camera = new OrthographicCamera(cam_width, cam_height);
        camera.position.set(camera.viewportWidth/2f, camera.viewportHeight/2f, 0);
        camera.update();
        viewport = new FitViewport(cam_width,cam_height,camera);
        viewport.apply();

        this.game = game;
        batch = new SpriteBatch();
        level = new Level();
        level.load(lvl_num, levels);

        font = new BitmapFont(Gdx.files.internal("font.fnt"), Gdx.files.internal("font.png"), false);
        font.setColor(221/255f, 181/255f, 85/255f, 1);
        font.getRegion().getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);

        popup_font = new BitmapFont(Gdx.files.internal("font.fnt"), Gdx.files.internal("font.png"), false);
        popup_font.getRegion().getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        //popup_font.setColor(35/255f, 55/255f, 55/255f, 1);
        popup_font.setScale(.8f);

        back_text = new Texture("back_button.png");
        back_text.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        back = new Sprite(back_text);
        back.setOrigin(0,0);
        back.setPosition(40,880);

        refresh_text = new Texture("refresh.png");
        refresh_text.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        refresh = new Sprite(refresh_text);
        refresh.setOrigin(0, 0);
        refresh.setScale(.8f);
        refresh.setPosition(camera.viewportWidth/2 - (refresh.getWidth()/2 * .8f),100);

        previous_text = new Texture("previous.png");
        previous_text.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        previous = new Sprite(previous_text);
        previous.setOrigin(0,0);
        previous.setScale(.8f);
        previous.setPosition(camera.viewportWidth/2 - 2.5f*previous.getWidth() *.8f,130);

        next_text = new Texture("next.png");
        next_text.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        next = new Sprite(next_text);
        next.setOrigin(0,0);
        next.setScale(.8f);
        next.setPosition(camera.viewportWidth/2 + 1.5f*next.getWidth()*.8f,130);

        popup_text = new Texture("popup.png");
        popup_text.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        popup = new Sprite(popup_text);
        popup.setOrigin(0,0);
        popup.setPosition(camera.viewportWidth/2 - popup.getWidth()/2,camera.viewportHeight/2 - popup.getHeight()/2);

        popup_box_text = new Texture("popup_box.png");
        popup_box_text.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        popup_box_text.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        popup_box1 = new Sprite(popup_box_text);
        popup_box1.setOrigin(0,0);
        popup_box1.setPosition(camera.viewportWidth/2 - popup_box1.getWidth()/2,camera.viewportHeight/2 - popup_box1.getHeight()/2+50);
        popup_box2 = new Sprite(popup_box_text);
        popup_box2.setOrigin(0,0);
        popup_box2.setPosition(camera.viewportWidth/2 - popup_box2.getWidth()/2,camera.viewportHeight/2 - popup_box2.getHeight()/2-30);
        popup_box3 = new Sprite(popup_box_text);
        popup_box3.setOrigin(0,0);
        popup_box3.setPosition(camera.viewportWidth/2 - popup_box3.getWidth()/2,camera.viewportHeight/2 - popup_box3.getHeight()/2-110);

        fpsLogger = new FPSLogger();
    }

    public void load_level(int id){
        System.out.println("In Level");
        lvl_num = id;
        level = new Level();
        level.load(lvl_num, levels);
    }

    @Override
    public void render (float something) {
        time = Gdx.graphics.getDeltaTime();
        level.boxesSpeed(700, 700, offset, time);
        level.doCollisions();
        //level.stuff();
        //player.getSpeed(width, height, offset, time);
        Gdx.gl.glClearColor(71/255f, 81/255f, 93/255f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        //arrow.drawArrow(batch);
        level.draw(batch);
        font.draw(batch, "Level " +lvl_num, 120, 925);
        back.draw(batch);
        previous.draw(batch);
        refresh.draw(batch);
        next.draw(batch);
        if (won == false){
            won = level.checkCompleted(this.game, lvl_num);
        } else{
            popup.draw(batch);
            popup_font.draw(batch, "Level Completed!", camera.viewportWidth/2 - popup_font.getBounds("Level Completed!").width/2,camera.viewportHeight/2+135);
            popup_box1.draw(batch);
            popup_font.draw(batch, "Next Level", (popup_box1.getX() + (popup_box1.getWidth()/2)) - popup_font.getBounds("Next Level").width/2,popup_box1.getY() + popup_box1.getHeight() - popup_box1.getHeight()/4);
            popup_box2.draw(batch);
            popup_font.draw(batch, "Level Menu", (popup_box2.getX() + (popup_box2.getWidth()/2)) - popup_font.getBounds("Level Menu").width/2,popup_box2.getY() + popup_box2.getHeight() - popup_box2.getHeight()/4);
            popup_box3.draw(batch);
            popup_font.draw(batch, "Remove Ads", (popup_box3.getX() + (popup_box3.getWidth()/2)) - popup_font.getBounds("Remove Ads").width/2,popup_box3.getY() + popup_box3.getHeight() - popup_box3.getHeight()/4);
        }
        batch.end();
        fpsLogger.log();
    }

    public void move (int keycode) {
        if (keycode == 46){
            level = new Level();
            level.load(lvl_num, levels);
        }
        level.handleInput(keycode);
    }

    public void touch (int x, int y) {
        if (won == true){
            if (spriteTouch(x,y,popup_box1)){
                won = false;
                level = new Level();
                lvl_num += 1;
                level.load(lvl_num, levels);
            }

            if (spriteTouch(x,y,popup_box2)){
                won = false;
                game.setMenu();
            }
        } else{
            if (spriteTouch(x,y, back)){
                game.setMenu();
            }

            if (spriteTouch(x,y,refresh)){
                level = new Level();
                level.load(lvl_num, levels);
            }

            if (spriteTouch(x,y,previous)){
                level = new Level();
                lvl_num -=1;
                level.load(lvl_num, levels);
            }

            if (spriteTouch(x,y, next)){
                level = new Level();
                lvl_num += 1;
                level.load(lvl_num, levels);
            }
        }
    }

    private boolean spriteTouch(int x, int y, Sprite sprite){
        float sprite_x_offset = (Gdx.graphics.getWidth() - viewport.getLeftGutterWidth() - viewport.getRightGutterWidth())/camera.viewportWidth;
        float sprite_y_offset = (Gdx.graphics.getHeight() - viewport.getTopGutterHeight() - viewport.getBottomGutterHeight())/camera.viewportHeight;

        if(x >= (sprite.getX() * sprite_x_offset + viewport.getLeftGutterWidth()) &&
           x <= (sprite.getX() * sprite_x_offset + sprite.getWidth() * sprite_x_offset * sprite.getScaleX() + viewport.getLeftGutterWidth()) &&
           y >= (sprite.getY() * sprite_y_offset + viewport.getBottomGutterHeight()) &&
           y <= (sprite.getY() * sprite_y_offset + sprite.getHeight() * sprite_y_offset * sprite.getScaleY() + viewport.getBottomGutterHeight()) ){
            return true;
        }
        return false;
    }

    @Override
    public void hide(){
        //
    }

    @Override
    public void dispose(){
        //
    }

    @Override
    public void show(){
        //
    }

    @Override
    public void resume(){
        //
    }

    @Override
    public void pause(){
        //
    }

    @Override
    public void resize(int width, int height){
        System.out.println(width);
        System.out.println(height);
        viewport.update(width, height, true);
    }
}
