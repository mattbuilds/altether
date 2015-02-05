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
    SpriteBatch batch, fixed_batch;
    //Arrow arrow;
    FPSLogger fpsLogger;
    Texture back_text, refresh_text, previous_text, next_text, popup_text, popup_box_text;
    Sprite top_bar, menu, back, refresh, previous, next, popup, lvl_comp, menu_popup, next_popup, grey;
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

    BoxPuzzle game; // Note it's "my game" not "your game"

    // constructor to keep a reference to the main Game class
    public GameScreen(BoxPuzzle game, int cam_width, int cam_height){

        //Load levels
        FileHandle file = Gdx.files.internal("levels.txt");
        String text = file.readString();
        levels = new JsonReader().parse(text);

        camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        camera.position.set(camera.viewportWidth/2f, camera.viewportHeight/2f, 0);
        camera.update();

        fixed_batch = new SpriteBatch();
        grey = new Sprite(new Texture("grey.png"));
        grey.setPosition(0,0);
        grey.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        top_bar = new Sprite(new Texture("top_bar.png"));
        top_bar.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getWidth()/10f *1.5f);
        top_bar.setPosition(0,Gdx.graphics.getHeight() - top_bar.getHeight());

        menu = new Sprite(new Texture("menu.png"));
        menu.setPosition(0,Gdx.graphics.getHeight() - menu.getHeight());

        this.game = game;
        batch = new SpriteBatch();
        level = new Level();
        level.load(lvl_num, levels);

        font = new BitmapFont(Gdx.files.internal("font.fnt"), Gdx.files.internal("font.png"), false);
        font.setColor(221/255f, 181/255f, 85/255f, 1);

        popup_font = new BitmapFont(Gdx.files.internal("font.fnt"), Gdx.files.internal("font.png"), false);
        popup_font.getRegion().getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        //popup_font.setColor(35/255f, 55/255f, 55/255f, 1);
        popup_font.setScale(.8f);

        back_text = new Texture("back_button.png");
        back = new Sprite(back_text);
        back.setOrigin(0,0);
        back.setPosition(back.getWidth(),camera.viewportHeight - ((camera.viewportHeight -camera.viewportWidth)/2f) - back.getHeight()/4f) ;

        refresh_text = new Texture("refresh.png");
        refresh = new Sprite(refresh_text);
        refresh.setOrigin(0, 0);
        refresh.setPosition(camera.viewportWidth/2 - (refresh.getWidth()/2),camera.viewportHeight/2f - camera.viewportWidth *(2f/3f));

        previous = new Sprite(new Texture("back_button.png"));
        previous.setOrigin(0, 0);
        previous.setPosition(camera.viewportWidth/2 - 2f *previous.getWidth(),camera.viewportHeight/2f - camera.viewportWidth *(2f/3f));


        next = new Sprite(new Texture("next.png"));
        next.setOrigin(0, 0);
        next.setPosition(camera.viewportWidth/2 + next.getWidth(),camera.viewportHeight/2f - camera.viewportWidth *(2f/3f));

        popup_text = new Texture("popup.png");
        popup = new Sprite(popup_text);
        popup.setOrigin(0,0);
        popup.setPosition(camera.viewportWidth/2 - popup.getWidth()/2,camera.viewportHeight/2 - popup.getHeight()/2);

        lvl_comp = new Sprite(new Texture("lvl_comp.png"));
        lvl_comp.setOrigin(0,0);
        lvl_comp.setPosition(camera.viewportWidth/2 - lvl_comp.getWidth()/2,camera.viewportHeight/2);

        menu_popup = new Sprite(new Texture("menu_popup.png"));
        menu_popup.setOrigin(0,0);
        menu_popup.setPosition(camera.viewportWidth/2 - 1.5f*menu_popup.getWidth(),camera.viewportHeight/2 - 1.25f* menu_popup.getHeight());

        next_popup = new Sprite(new Texture("next_popup.png"));
        next_popup.setOrigin(0,0);
        next_popup.setPosition(camera.viewportWidth/2 + .5f*next_popup.getWidth(),camera.viewportHeight/2 - 1.25f* next_popup.getHeight());

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
        top_bar.draw(batch);
        menu.draw(batch);
        font.draw(batch, "Level " +lvl_num, camera.viewportWidth/2f - font.getBounds("Level " + lvl_num).width/2f, (camera.viewportHeight/2f + camera.viewportWidth/2f) - font.getBounds("Level " + lvl_num).height/2f);
        //font.draw(batch, "Level " +lvl_num, camera.viewportWidth/2f - font.getBounds("Level " + lvl_num).width/2f, camera.viewportHeight - camera.viewportWidth/10f *1.5f + (1.5f * font.getBounds("Level " + lvl_num).height) );
        //back.draw(batch);
        previous.draw(batch);
        refresh.draw(batch);
        next.draw(batch);
        if (won == false){
            won = level.checkCompleted(this.game, lvl_num);
        } else{
            grey.draw(batch);
            popup.draw(batch);
            lvl_comp.draw(batch);
            menu_popup.draw(batch);
            next_popup.draw(batch);
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
            if (spriteTouch(x,y,next_popup)){
                won = false;
                level = new Level();
                lvl_num += 1;
                level.load(lvl_num, levels);
            }

            if (spriteTouch(x,y,menu_popup)){
                won = false;
                game.setMenu();
            }
        } else{
            if (spriteTouch(x,y, menu)){
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
        if(x >=sprite.getX() && x <= sprite.getX() + sprite.getWidth() &&
                y >= sprite.getY() && y <= sprite.getY() + sprite.getHeight() ){
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

    }
}
