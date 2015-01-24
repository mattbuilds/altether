package com.boxpuzzle.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Created by Matt on 10/5/14.
 */
public class Goal {
    public int x, y;
    private Texture img;
    private boolean won = false;

    public Goal(int set_x, int set_y){
        x = set_x;
        y = set_y;
        img = new Texture("goal.png");
    }

    public void draw(SpriteBatch batch){
        batch.draw(img, x, y);
    }

    public boolean checkGoal(Player player){
        if (player.x+64 > x && player.x < x+64 && player.y < y+64 && player.y+64 > y){
            System.out.println("GOAL");
            won = true;
            return false;
        } else if (won == true){
            System.out.println("Won");
            return true;
        } else {
            return false;
        }
    }
}
