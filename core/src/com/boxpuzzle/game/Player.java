package com.boxpuzzle.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;

/**
 * Created by Matt on 9/26/2014.
 */
public class Player extends Box{
    public int goal_x, goal_y;
    public Texture goal_img;
    public boolean completed_goal;

    public Player(int set_x, int set_y, int set_goal_x, int set_goal_y, Texture play_text, Texture goal_text){
        x = set_x *64;
        y = set_y * 64;
        width = 64;
        height = 64;
        speed_x = 0;
        speed_y = 0;
        img = play_text;
        goal_img = goal_text;
        player = true;
        goal_x = set_goal_x * 64;
        goal_y = set_goal_y * 64;
        completed_goal = false;
    }

    public void draw(Batch batch, float x_offset, float y_offset){
        batch.draw(img, x*(img.getWidth()/64f)+x_offset, y*(img.getHeight()/64f)+y_offset);
    }

    public void drawGoal(Batch batch, float x_offset, float y_offset){
        batch.draw(goal_img, goal_x*(goal_img.getWidth()/64f)+x_offset, goal_y*(goal_img.getHeight()/64f)+y_offset);
    }

    public void checkGoal(){
        if (x+width > goal_x && x < goal_x+64 && y < goal_y+64 && y+64 > goal_y) {
            if (speed_x > 0 && x > goal_x){
                x = goal_x;
                speed_x = 0;
                completed_goal = true;
            }
            if (speed_x < 0 && x < goal_x){
                x = goal_x;
                speed_x = 0;
                completed_goal = true;
            }
            if (speed_y > 0 && y > goal_y){
                 y = goal_y;
                speed_y = 0;
                completed_goal = true;
            }
            if (speed_y < 0 && y < goal_y){
                y = goal_y;
                speed_y = 0;
                completed_goal = true;
            }
        }
    }

    public boolean getCompletedGoal(){
        return completed_goal;
    }
}
