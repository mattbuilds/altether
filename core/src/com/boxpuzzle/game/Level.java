package com.boxpuzzle.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Matt on 10/6/14.
 */
public class Level {
    List<Box> walls;
    List<Box> boxes;
    Texture red_text, blue_text, red_goal, blue_goal, green_text, green_goal, yellow_text, yellow_goal,
            purple_text, purple_goal, wall_img;
    Sprite background_grid;

    public Goal goal;
    Grid grid, wall_grid;
    private boolean stopped = true;
    Player player1, player2, player3, player4, player5;
    int grid_size = 8;

    public Level(BoxPuzzle game, Sprite background_grid, Texture red_text, Texture red_goal, Texture blue_text,
                 Texture blue_goal, Texture green_text, Texture green_goal, Texture yellow_text, Texture yellow_goal){
        walls = new ArrayList<Box>();
        //wall_img = new Texture("wall.jpg");
        boxes =  new ArrayList<Box>();

        this.background_grid = background_grid;
        this.red_text = red_text;
        this.red_goal = red_goal;
        this.blue_text = blue_text;
        this.blue_goal = blue_goal;
        this.green_text = green_text;
        this.green_goal = green_goal;
        this.yellow_text = yellow_text;
        this.yellow_goal = yellow_goal;
    }

    public void load(int lvl_num, JsonValue levels) {
        JsonValue root = levels.get(Integer.toString(lvl_num));

        if (root.has("1")){
            player1 = new Player(root.get("1").get("start").get("x").asInt(), root.get("1").get("start").get("y").asInt(),
                    root.get("1").get("answer").get("x").asInt(), root.get("1").get("answer").get("y").asInt(),
                    red_text, red_goal);
            boxes.add(player1);
        }
        if(root.has("2")){
            player2 = new Player(root.get("2").get("start").get("x").asInt(), root.get("2").get("start").get("y").asInt(),
                    root.get("2").get("answer").get("x").asInt(), root.get("2").get("answer").get("y").asInt(),
                    blue_text, blue_goal);
            boxes.add(player2);
        }
        if(root.has("3")){
            player3 = new Player(root.get("3").get("start").get("x").asInt(), root.get("3").get("start").get("y").asInt(),
                    root.get("3").get("answer").get("x").asInt(), root.get("3").get("answer").get("y").asInt(),
                    yellow_text, yellow_goal);
            boxes.add(player3);
        }
        if(root.has("4")){
            player4 = new Player(root.get("4").get("start").get("x").asInt(), root.get("4").get("start").get("y").asInt(),
                    root.get("4").get("answer").get("x").asInt(), root.get("4").get("answer").get("y").asInt(),
                    green_text, green_goal);
            boxes.add(player4);
        }

        grid_size = root.get("size").asInt();

        grid = new Grid(64*grid_size, 64*grid_size, 32);

        for (int i=0; i<boxes.size(); i++){
            grid.add(boxes.get(i));
        }

        for (int i=0; i<walls.size(); i++){
            grid.add(walls.get(i));
        }
    }

    public void boxesSpeed(int width, int height, int offset, float time){
        // Removes each object from the grid, calculates new position, adds box to grid
        for (int i = 0; i <boxes.size(); i++){
            Box box = boxes.get(i);
            grid.remove(box);
            box.getSpeed(width, height, offset, time);
            if (box instanceof Player){
                box.checkGoal();
            }
            grid.add(box);
        }
    }

    public void handleInput(int keycode){
        for (int i = 0; i < boxes.size(); i++){
            if(boxes.get(i).speed_x != 0 ||boxes.get(i).speed_y != 0){
                return;
            }
        }

        for (int i = 0; i < boxes.size(); i++){
            boxes.get(i).getInput(keycode);
        }
    }

    public void draw(SpriteBatch batch){
       float x_offset = (Gdx.graphics.getWidth()-(background_grid.getWidth() * grid_size))/2;
       float y_offset = (Gdx.graphics.getHeight()-(background_grid.getHeight() * grid_size))/2;

        for (int i=0; i <grid_size; i++){
            for (int j =0; j<grid_size; j++){
                //batch.draw(background_grid, i*scale+game_x_offset, j*scale+game_y_offset, scale, scale);
                batch.draw(background_grid, i*background_grid.getWidth()+x_offset, j*background_grid.getHeight()+y_offset);
            }
        }
        for (int i = 0; i < boxes.size(); i++){
            boxes.get(i).drawGoal(batch, x_offset, y_offset);
        }
        for (int i = 0; i < boxes.size(); i++){

            boxes.get(i).draw(batch,x_offset, y_offset);
        }

/*
        for (int i = 0; i < walls.size(); i++){
            walls.get(i).draw(batch, scale, game_x_offset, game_y_offset);
        }
        for (int i = 0; i < boxes.size(); i++){
            boxes.get(i).draw(batch, scale, game_x_offset, game_y_offset);
        }

        for (int i = 0; i < boxes.size(); i++){
            boxes.get(i).drawGoal(batch, scale, game_x_offset, game_y_offset);
        }
       */
    }

    public boolean checkCompleted(BoxPuzzle game, int lvl_num){
        for (int i = 0; i< boxes.size(); i++){
            if (boxes.get(i).getCompletedGoal() == false){
                return false;
            }
        }
        game.completedLevel(lvl_num-1);
        game.analytics.createEvent();
        return true;
    }

    public void doCollisions(){
        int board_width = 64*grid_size;
        for (int i = 0; i < boxes.size(); i++){
            if (boxes.get(i).x+boxes.get(i).width > board_width){
                grid.remove(boxes.get(i));
                boxes.get(i).x = board_width - 64;
                boxes.get(i).speed_x = 0;
                grid.add(boxes.get(i));
                space_coll(boxes.get(i));
            }
            if (boxes.get(i).x < 0){
                grid.remove(boxes.get(i));
                boxes.get(i).x = 0;
                boxes.get(i).speed_x = 0;
                grid.add(boxes.get(i));
                space_coll(boxes.get(i));
            }

            if (boxes.get(i).y+boxes.get(i).height > board_width){
                grid.remove(boxes.get(i));
                boxes.get(i).y = board_width - 64;
                boxes.get(i).speed_y = 0;
                grid.add(boxes.get(i));
                space_coll(boxes.get(i));
            }

            if (boxes.get(i).y < 0){
                grid.remove(boxes.get(i));
                boxes.get(i).y = 0;
                boxes.get(i).speed_y = 0;
                grid.add(boxes.get(i));
                space_coll(boxes.get(i));
            }
            space_coll(boxes.get(i));
        }
    }

    public void space_coll(Box box){
        List<Box> retrieved = grid.space(box);
        for (int j=0; j<retrieved.size(); j++){
            if (box != retrieved.get(j)){
                collision(box, retrieved.get(j));
            }
        }
    }

    public void collision(Box current, Box compare){
        if (current.x < compare.x+compare.width && current.x+current.width > compare.x &&
                current.y < compare.y+compare.height && current.y+current.height > compare.y){
            if (current.speed_x > 0 || compare.speed_x > 0){
                if (current.x < compare.x){
                    grid.remove(current);
                    current.x = compare.x - 64;
                    current.speed_x = 0;
                    grid.add(current);
                    space_coll(current);
                }else {
                    grid.remove(compare);
                    compare.x = current.x - 64;
                    compare.speed_x = 0;
                    grid.add(compare);
                    space_coll(compare);
                }
            }
            if (current.speed_x < 0 || compare.speed_x < 0){
                if (current.x < compare.x){
                    grid.remove(compare);
                    compare.x = current.x + 64;
                    compare.speed_x = 0;
                    grid.add(compare);
                    space_coll(compare);
                }else {
                    grid.remove(current);
                    current.x = compare.x + 64;
                    current.speed_x = 0;
                    grid.add(current);
                    space_coll(current);
                }
            }
            if (current.speed_y > 0 || compare.speed_y > 0){
                if (current.y < compare.y){
                    grid.remove(current);
                    current.y = compare.y - 64;
                    current.speed_y = 0;
                    grid.add(current);
                    space_coll(current);
                }else {
                    grid.remove(compare);
                    compare.y = current.y - 64;
                    compare.speed_y = 0;
                    grid.add(compare);
                    space_coll(compare);
                }
            }
            if (current.speed_y < 0 || compare.speed_y < 0){
                if (current.y < compare.y){
                    grid.remove(compare);
                    compare.y = current.y + 64;
                    compare.speed_y = 0;
                    grid.add(compare);
                    space_coll(compare);
                }else {
                    grid.remove(current);
                    current.y = compare.y + 64;
                    current.speed_y = 0;
                    grid.add(current);
                    space_coll(current);
                }
            }
        }
    }
}
