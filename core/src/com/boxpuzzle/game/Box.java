package com.boxpuzzle.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;

/**
 * Created by Matt on 9/26/2014.
 */
public class Box {
    public int x, y, width, height, speed_x, speed_y;
    public Texture img;
    public boolean player;
    public int completed_goal;

    public Box(){
        x = 0;
        y = 0;
    }

    public Box(int set_x, int set_y){
        x = set_x;
        y = set_y;
        width = 64;
        height = 64;
        speed_x = 0;
        speed_y = 0;
        player =  false;
        img = new Texture("blue_tile.png");
    }

    public Box(int set_x, int set_y, Texture wall){
        x = set_x;
        y = set_y;
        width = 64;
        height = 64;
        speed_x = 0;
        speed_y = 0;
        img = wall;
    }

    public void draw(Batch batch, int scale, int game_x_offset, int game_y_offset){
        batch.draw(img, x + game_x_offset, y + game_y_offset);
    }

    public void drawGoal(Batch batch, int scale, int game_x_offset, int game_y_offset){
        //
    }

    public void getSpeed(int width, int height, int offset, float time){
        if (speed_x > 0 && x+offset >= width) {
            speed_x = 0;
            x = width - offset;
        }
        if (speed_x < 0 && x <= 0){
            speed_x =  0;
            x = 0;
        }
        if (speed_y > 0 && y+offset >= height) {
            speed_y =  0;
            y = height - offset;
        }
        if(speed_y < 0 && y <= 0){
            speed_y =  0;
            y = 0;
        }

        //System.out.println(1 * time * speed_x);
        x += Math.round(15 * time * speed_x);
        y += Math.round(15 * time * speed_y);
    }

    public void checkGoal(){
        // Blank for Boxes (only used for player)
    }

    public void getInput(int keycode){
        if (speed_x == 0 && speed_y == 0){
            if (keycode == 19){
                speed_x = 0;
                speed_y = 100;
            }
            if (keycode == 20){
                speed_x = 0;
                speed_y = -100;
            }
            if (keycode == 21){
                speed_x = -100;
                speed_y = 0;
            }
            if (keycode == 22){
                speed_x = 100;
                speed_y = 0;
            }
        }
    }

    public boolean getCompletedGoal(){
        return false;
    };
}
