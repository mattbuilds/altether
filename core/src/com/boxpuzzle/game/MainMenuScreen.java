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
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;
import com.badlogic.gdx.utils.viewport.FitViewport;

import java.util.ArrayList;
import java.util.List;

public class MainMenuScreen implements Screen {

    private BoxPuzzle game; // Note it's "MyGame" not "Game"
    private OrthographicCamera camera, fixed_cam;
    private FitViewport viewport;
    private SpriteBatch batch, fixed_batch;
    private Texture check, texture;
    private Sprite  box, grey;
    private List<Sprite> sprites, checks;
    private float time, camera_x, sprite_x_offset, sprite_y_offset, scale_x, scale_y;
    private BitmapFont font;
    private JsonValue levels_completed;
    FileHandle level_file;
    FPSLogger fpsLogger;

    // constructor to keep a reference to the main Game class
    public MainMenuScreen(BoxPuzzle game, int game_width, int game_height){
        this.game = game;
        camera_x = 320;

        sprites = new ArrayList<Sprite>();
        checks = new ArrayList<Sprite>();

        level_file = Gdx.files.local("level_status.txt");
        try{
            levels_completed = new JsonReader().parse(level_file.readString());
        } catch (Exception e){
            String text = "{1:{status:0},2:{status:0},3:{status:0},4:{status:0},5:{status:0},6:{status:0},7:{status:0},8:{status:0},9:{status:0},10:{status:0},11:{status:0},12:{status:0},13:{status:0},14:{status:0},15:{status:0},16:{status:0},17:{status:0},18:{status:0},19:{status:0},20:{status:0},21:{status:0},22:{status:0},23:{status:0},24:{status:0},25:{status:0},26:{status:0},27:{status:0},28:{status:0},29:{status:0},30:{status:0},31:{status:0},32:{status:0},33:{status:0},34:{status:0},35:{status:0},36:{status:0},37:{status:0},38:{status:0},39:{status:0},40:{status:0}}";
            level_file.writeString(text, false);
            level_file = Gdx.files.local("level_status.txt");
            levels_completed = new JsonReader().parse(level_file.readString());
        }

        camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        camera.position.set(camera.viewportWidth/2f, camera.viewportHeight/2f, 0);
        camera.update();

        fixed_cam = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        fixed_cam.position.set(fixed_cam.viewportWidth/2f, fixed_cam.viewportHeight/2f, 0);
        fixed_cam.update();

        font = new BitmapFont(Gdx.files.internal("font.fnt"), Gdx.files.internal("font.png"), false);
        //font.setColor(1, 1, 1, 1);
        //font.setScale(.35f);
        //font.getRegion().getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);

        check = new Texture("check.png");
        check.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);

        batch = new SpriteBatch();
        texture = new Texture("box.png");
        for (int i = 1; i<= 60; i++){
            System.out.println( ((i-1)%30)/5);
            Sprite adding = new Sprite(texture,64,64);
            setPos(i, adding);
        }

        fixed_batch = new SpriteBatch();
        grey = new Sprite(new Texture("grey.png"));
        grey.setPosition(0,0);
        grey.setScale(5f);
        fpsLogger = new FPSLogger();

/*
        for (int i = 1; i<= 30; i++){
            String number_name = "box.png";
            //texture = new Texture(number_name);
            //texture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
            Sprite adding = new Sprite(texture);

            float width = getWidth(i-1);
            float height = getHeight(i-1);

            adding.setOrigin(0,0);
            adding.setPosition(camera.viewportHeight/2f + adding.getWidth() *width, camera.viewportHeight/2f + adding.getHeight()*height);

            sprites.add(adding);
        }

        for (int i = 1; i < 40; i++){
            if (levels_completed.get(Integer.toString(i)).getString("status").toString().equals("1") ){
                float sprite_x = sprites.get(i-1).getX();
                float sprite_y = sprites.get(i-1).getY();
                checks.add(new Sprite(check));
                checks.get(checks.size()-1).setPosition(sprite_x, sprite_y);
            }
        }
        */
    }

    private void setPos(int num, Sprite adding){
        float width_mult;
        float height_mult;
        int x_num, y_num, offset;

        x_num = num%5;
        if (x_num == 1){
            width_mult = adding.getWidth()*-3.5f;
        } else if (x_num == 2){
            width_mult = adding.getWidth()*-1.75f;
        } else if (x_num == 3){
            width_mult = 0f;
        } else if (x_num == 4){
            width_mult = adding.getWidth()*1.75f;
        } else {
            width_mult = adding.getWidth()*3.5f;
        }

        offset = (num-1)/30;
        if (offset == 1){
            width_mult = camera.viewportWidth + width_mult;
        }

        y_num = ((num-1)%30)/5;
        if (y_num == 0){
            height_mult = adding.getHeight()*5.5f;
        } else if (y_num == 1){
            height_mult = adding.getHeight()*3.75f;
        } else if (y_num == 2){
            height_mult = adding.getHeight()* 2f ;
        } else if (y_num == 3){
            height_mult = adding.getHeight() *.25f;
        } else if (y_num == 4){
            height_mult = adding.getHeight()*-1.5f;
        } else{
            height_mult = adding.getHeight()*-3.25f;
        }



        adding.setPosition(Gdx.graphics.getWidth()/2f - adding.getWidth()/2 + width_mult, Gdx.graphics.getHeight()/2f - adding.getHeight()/2+height_mult);
        sprites.add(adding);
    }

    public void updateCompletedLevel(int lvl_num){
        levels_completed.get(Integer.toString(lvl_num+1)).get("status").set(1);
        level_file.writeString(levels_completed.toString(), false);
        float sprite_x = sprites.get(lvl_num).getX();
        float sprite_y = sprites.get(lvl_num).getY();
        checks.add(new Sprite(check));
        checks.get(checks.size()-1).setPosition(sprite_x, sprite_y);
    }

    @Override
    public void render(float delta) {
        time = Gdx.graphics.getDeltaTime();
        Gdx.gl.glClearColor(71/255f, 81/255f, 93/255f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        cameraMove();
        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        //box.draw(batch);
        for (int i = 0; i<sprites.size(); i++){
            sprites.get(i).draw(batch);
        }
        for (int i = 0; i<checks.size(); i++){
            checks.get(i).draw(batch);
        }
        for (int i = 0; i<sprites.size(); i++){
            if (i < 9){
                font.draw(batch, Integer.toString(i+1), sprites.get(i).getX()+21, sprites.get(i).getY()+48);
            } else{
                font.draw(batch, Integer.toString(i+1), sprites.get(i).getX()+8, sprites.get(i).getY()+48);
            }
        }
        batch.end();
        fpsLogger.log();
        //camera.position.set(camera.position.x +1f, camera.position.y, 0);
        //camera.update();
        //fixed_batch.setProjectionMatrix(fixed_cam.combined);
        //fixed_batch.begin();
        //grey.draw(fixed_batch);
        //fixed_batch.end();
    }

    public void cameraMove(){
        if (camera_x == camera.position.x){
            //
        } else if ((int)camera_x > camera.position.x){
            camera.position.x += 1500f*time;
            if (camera.position.x > camera_x){
                camera.position.x = camera_x;
            }
            camera.update();
        } else if ((int)camera_x < camera.position.x){
            camera.position.x -= 1500f*time;
            if (camera.position.x < camera_x){
                camera.position.x = camera_x;
            }
            camera.update();
        }
    }

    public void forceCameraLocation(){
        camera.position.x = camera_x;
        camera.update();
    }

    public void move(int keycode){
        System.out.println(keycode);
         if (keycode == 21){
             camera_x = camera.position.x + camera.viewportWidth;
         } else if (keycode == 22){
             camera_x = camera.position.x - camera.viewportWidth;

         }
    }

    public void touch(int x, int y){
        Boolean clicked = false;
        System.out.println(x);
        System.out.println(y);
        for (int i = 0; i< sprites.size(); i++){
            clicked = number_click(x,y, sprites.get(i));
            if (clicked == true){
                //System.out.print(i+1);
                game.setGameScreen(i+1);
            }
        }
    }

    private boolean number_click(int x, int y, Sprite sprite){
        if(x >=sprite.getX() && x <= sprite.getX() + sprite.getWidth() &&
           y >= sprite.getY() && y <= sprite.getY() + sprite.getHeight() ){
           return true;
        } else{
            return false;
        }
    }

    @Override
    public void resize(int width, int height) {
        camera.viewportWidth =  width;
        camera.viewportHeight = height;
        camera.position.set(camera.viewportWidth/2f, camera.viewportHeight/2f, 0);
        camera.update();

        fixed_cam.viewportWidth =  width;
        fixed_cam.viewportHeight = height;
        fixed_cam.position.set(fixed_cam.viewportWidth/2f, fixed_cam.viewportHeight/2f, 0);
        fixed_cam.update();
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
