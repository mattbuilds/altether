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

    public Level(){
        walls = new ArrayList<Box>();
        //wall_img = new Texture("wall.jpg");
        boxes =  new ArrayList<Box>();

        background_grid = new Sprite(new Texture("tile.png"));
        red_text = new Texture(Gdx.files.internal("red_tile.png"), true);
        red_goal = new Texture("red_goal.png");
        blue_text = new Texture("blue_tile.png");
        blue_goal = new Texture("blue_goal.png");
        green_text = new Texture("green_tile.png");
        green_goal = new Texture("green_goal.png");
        yellow_text = new Texture("yellow_tile.png");
        yellow_text.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        yellow_goal = new Texture("yellow_goal.png");
        yellow_goal.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        purple_text = new Texture("purple_tile.png");
        purple_goal = new Texture("purple_goal.png");

        background_grid.setScale(.5f);
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
/*
        if (lvl_num == 1){
            player1 = new Player(0, 0, 7, 7, red_text, red_goal);

            boxes.add(player1);

        }else if (lvl_num == 2){
            player1 = new Player(0, 0, 0, 7, red_text, red_goal);
            player2 = new Player(7, 0, 7,7, blue_text, blue_goal);

            boxes.add(player1);
            boxes.add(player2);
        }else if (lvl_num == 3){
            player1 = new Player(0, 0, 7, 7, red_text, red_goal);
            player2 = new Player(1, 1, 7, 0, blue_text, blue_goal);

            boxes.add(player1);
            boxes.add(player2);
        }else if (lvl_num == 4){
            player1 = new Player(0, 0, 7, 0, red_text, red_goal);
            player2 = new Player(7, 7, 4, 1, blue_text, blue_goal);

            boxes.add(player1);
            boxes.add(player2);
        }else if (lvl_num == 5){
            player1 = new Player(0, 0, 6, 7, red_text, red_goal);
            player2 = new Player(1, 1, 5, 5, blue_text, blue_goal);

            boxes.add(player1);
            boxes.add(player2);
        }else if (lvl_num == 6){
            player1 = new Player(0, 0, 6, 6, red_text, red_goal);
            player2 = new Player(1, 1, 7, 7, blue_text, blue_goal);
            player3 = new Player(2, 0, 7, 6, yellow_text, yellow_goal);

            boxes.add(player1);
            boxes.add(player2);
            boxes.add(player3);
        }else if (lvl_num == 7){
            player1 = new Player(0, 0, 1, 6, red_text, red_goal);
            player2 = new Player(1, 1, 0, 7, blue_text, blue_goal);
            player3 = new Player(2, 0, 0, 6, yellow_text, yellow_goal);

            boxes.add(player1);
            boxes.add(player2);
            boxes.add(player3);
        }else if (lvl_num == 8){
            player1 = new Player(0, 0, 6, 5, red_text, red_goal);
            player2 = new Player(0, 1, 4, 4, blue_text, blue_goal);
            player3 = new Player(1, 0, 7, 5, yellow_text, yellow_goal);

            boxes.add(player1);
            boxes.add(player2);
            boxes.add(player3);
        }else if (lvl_num == 9){
            player1 = new Player(7, 7, 0, 7, red_text, red_goal);
            player2 = new Player(2, 5, 2, 2, blue_text, blue_goal);
            player3 = new Player(5, 2, 0, 1, yellow_text, yellow_goal);

            boxes.add(player1);
            boxes.add(player2);
            boxes.add(player3);
        }else if (lvl_num == 10){
            player1 = new Player(0, 0, 5, 5, red_text, red_goal);
            player2 = new Player(4, 1, 3, 4, blue_text, blue_goal);
            player3 = new Player(1, 0, 6, 0, yellow_text, yellow_goal);

            boxes.add(player1);
            boxes.add(player2);
            boxes.add(player3);
        }else if (lvl_num == 11){
            player1 = new Player(1, 2, 6, 7, red_text, red_goal);
            player2 = new Player(2, 1, 6, 5, blue_text, blue_goal);
            player3 = new Player(5, 2, 3, 1, yellow_text, yellow_goal);

            boxes.add(player1);
            boxes.add(player2);
            boxes.add(player3);
        }else if (lvl_num == 12){
            player1 = new Player(0, 0, 1, 1, red_text, red_goal);
            player2 = new Player(2, 5, 2, 2, blue_text, blue_goal);
            player3 = new Player(5, 2, 0, 1, yellow_text, yellow_goal);

            boxes.add(player1);
            boxes.add(player2);
            boxes.add(player3);
        }else if (lvl_num == 13){
            player1 = new Player(7, 7, 1, 4, red_text, red_goal);
            player2 = new Player(1, 1, 3, 1, blue_text, blue_goal);
            player3 = new Player(1, 0, 3, 0, yellow_text, yellow_goal);

            boxes.add(player1);
            boxes.add(player2);
            boxes.add(player3);
        }else if (lvl_num == 14){
            player1 = new Player(7, 0, 1, 1, red_text, red_goal);
            player2 = new Player(1, 6, 6, 0, blue_text, blue_goal);
            player3 = new Player(4, 2, 5, 6, yellow_text, yellow_goal);

            boxes.add(player1);
            boxes.add(player2);
            boxes.add(player3);
        }else if (lvl_num == 15){
            player1 = new Player(7,0, 1, 7, red_text, red_goal);
            player2 = new Player(0, 0, 2, 1, blue_text, blue_goal);
            player3 = new Player(6, 3, 5, 7, yellow_text, yellow_goal);

            boxes.add(player1);
            boxes.add(player2);
            boxes.add(player3);
        }else if (lvl_num == 16){
            player1 = new Player(3, 4, 2, 0, red_text, red_goal);
            player2 = new Player(7, 6, 5,4  , blue_text, blue_goal);
            player3 = new Player(5, 1, 3, 5, yellow_text, yellow_goal);

            boxes.add(player1);
            boxes.add(player2);
            boxes.add(player3);
        }else if (lvl_num == 17){
            player1 = new Player(0, 0, 1, 1, red_text, red_goal);
            player2 = new Player(7, 7, 4,0  , blue_text, blue_goal);
            player3 = new Player(0, 1, 3, 0, yellow_text, yellow_goal);

            boxes.add(player1);
            boxes.add(player2);
            boxes.add(player3);
        }else if (lvl_num == 18){
            player1 = new Player(0, 0, 1, 7, red_text, red_goal);
            player2 = new Player(0, 2, 5,7  , blue_text, blue_goal);
            player3 = new Player(5, 1, 3, 0, yellow_text, yellow_goal);
            player4 = new Player(7, 7, 3, 5, green_text, green_goal);

            boxes.add(player1);
            boxes.add(player2);
            boxes.add(player3);
            boxes.add(player4);
        }else if (lvl_num == 19){
            player1 = new Player(1, 6, 7, 3, red_text, red_goal);
            player2 = new Player(0, 2, 5,7  , blue_text, blue_goal);
            player3 = new Player(5, 1, 3, 7, yellow_text, yellow_goal);
            player4 = new Player(7, 7, 0, 4, green_text, green_goal);

            boxes.add(player1);
            boxes.add(player2);
            boxes.add(player3);
            boxes.add(player4);
        }else if (lvl_num == 20){
            player1 = new Player(2, 2, 0, 5, red_text, red_goal);
            player2 = new Player(0, 2, 1, 5, blue_text, blue_goal);
            player3 = new Player(4, 2, 4, 6, yellow_text, yellow_goal);
            player4 = new Player(8, 7, 1, 1, green_text, green_goal);

            boxes.add(player1);
            boxes.add(player2);
            boxes.add(player3);
            boxes.add(player4);
            grid_size = 9;
        }else if (lvl_num == 21){
            player1 = new Player(1, 6, 7, 3, red_text, red_goal);
            player2 = new Player(0, 2, 5,7  , blue_text, blue_goal);
            player3 = new Player(5, 1, 3, 7, yellow_text, yellow_goal);
            player4 = new Player(7, 7, 0, 4, green_text, green_goal);

            boxes.add(player1);
            boxes.add(player2);
            boxes.add(player3);
            boxes.add(player4);
            grid_size = 9;
        } else if (lvl_num == 22){
            player1 = new Player(7, 7, 0, 0, red_text, red_goal);
            player2 = new Player(5, 5, 8, 7, blue_text, blue_goal);
            player3 = new Player(2, 3, 5, 7, yellow_text, yellow_goal);
            player4 = new Player(3, 2, 2, 1, green_text, green_goal);

            boxes.add(player1);
            boxes.add(player2);
            boxes.add(player3);
            boxes.add(player4);
            grid_size = 9;
        }else if (lvl_num == 23){
            player1 = new Player(2, 1, 2, 7, red_text, red_goal);
            player2 = new Player(7, 8, 6, 2, blue_text, blue_goal);
            player3 = new Player(2, 5, 0, 0, yellow_text, yellow_goal);
            player4 = new Player(4, 0, 7, 6, green_text, green_goal);

            boxes.add(player1);
            boxes.add(player2);
            boxes.add(player3);
            boxes.add(player4);
            grid_size = 9;
        }else if (lvl_num == 24){
            player1 = new Player(6, 0, 2, 3, red_text, red_goal);
            player2 = new Player(6, 2, 7, 7, blue_text, blue_goal);
            player3 = new Player(2, 5, 1, 3, yellow_text, yellow_goal);
            player4 = new Player(1, 1, 5, 0, green_text, green_goal);

            boxes.add(player1);
            boxes.add(player2);
            boxes.add(player3);
            boxes.add(player4);
            grid_size = 9;
        }else if (lvl_num == 25){
            player1 = new Player(7, 3, 3, 5, red_text, red_goal);
            player2 = new Player(8, 0, 8, 7, blue_text, blue_goal);
            player3 = new Player(8, 6, 2, 1, yellow_text, yellow_goal);
            player4 = new Player(4, 8, 8, 8, green_text, green_goal);

            boxes.add(player1);
            boxes.add(player2);
            boxes.add(player3);
            boxes.add(player4);
            grid_size = 9;
        }else if (lvl_num == 26){
            player1 = new Player(1, 3, 1, 2, red_text, red_goal);
            player2 = new Player(0, 7, 2, 5, blue_text, blue_goal);
            player3 = new Player(6, 8, 1, 8, yellow_text, yellow_goal);
            player4 = new Player(5, 0, 3, 1, green_text, green_goal);

            boxes.add(player1);
            boxes.add(player2);
            boxes.add(player3);
            boxes.add(player4);
            grid_size = 9;
        }else if (lvl_num == 27){
            player1 = new Player(8, 6, 7, 0, red_text, red_goal);
            player2 = new Player(7, 3, 2, 2, blue_text, blue_goal);
            player3 = new Player(1, 8, 2, 7, yellow_text, yellow_goal);
            player4 = new Player(0, 3, 8, 4, green_text, green_goal);

            boxes.add(player1);
            boxes.add(player2);
            boxes.add(player3);
            boxes.add(player4);
            grid_size = 9;
        }else if (lvl_num == 28){
            player1 = new Player(4, 3, 8, 6, red_text, red_goal);
            player2 = new Player(8, 8, 7, 3, blue_text, blue_goal);
            player3 = new Player(5, 0, 4, 1, yellow_text, yellow_goal);
            player4 = new Player(5, 2, 7, 0, green_text, green_goal);

            boxes.add(player1);
            boxes.add(player2);
            boxes.add(player3);
            boxes.add(player4);
            grid_size = 9;
        }else if (lvl_num == 29){
            player1 = new Player(0, 0, 1, 2, red_text, red_goal);
            player2 = new Player(2, 2, 6, 1, blue_text, blue_goal);
            player3 = new Player(3, 3, 2, 0, yellow_text, yellow_goal);
            player4 = new Player(5, 1, 5, 5, green_text, green_goal);

            boxes.add(player1);
            boxes.add(player2);
            boxes.add(player3);
            boxes.add(player4);
            grid_size = 10;
        } else if (lvl_num ==30){
            player1 = new Player(root.get("1").get("start").get("x").asInt(), root.get("1").get("start").get("y").asInt(),
                                 root.get("1").get("answer").get("x").asInt(), root.get("1").get("answer").get("y").asInt(),
                                 red_text, red_goal);
            player2 = new Player(root.get("2").get("start").get("x").asInt(), root.get("2").get("start").get("y").asInt(),
                                 root.get("2").get("answer").get("x").asInt(), root.get("2").get("answer").get("y").asInt(),
                                 blue_text, blue_goal);
            player3 = new Player(root.get("3").get("start").get("x").asInt(), root.get("3").get("start").get("y").asInt(),
                                 root.get("3").get("answer").get("x").asInt(), root.get("3").get("answer").get("y").asInt(),
                                 yellow_text, yellow_goal);
            player4 = new Player(root.get("4").get("start").get("x").asInt(), root.get("4").get("start").get("y").asInt(),
                                 root.get("4").get("answer").get("x").asInt(), root.get("4").get("answer").get("y").asInt(),
                                 green_text, green_goal);

            boxes.add(player1);
            boxes.add(player2);
            boxes.add(player3);
            boxes.add(player4);
            grid_size = 9;
        }
*/

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
        int game_x_offset = 0;
        int game_y_offset = 220;
        int scale;

        if (grid_size == 8){
            scale = 80;
        } else if (grid_size == 9){
            game_x_offset = 5;
            scale = 70;
        } else if (grid_size == 10){
            game_x_offset = 20;
            scale = 60;
        } else{
            scale = 80;
        }

        for (int i=0; i <grid_size; i++){
            for (int j =0; j<grid_size; j++){
                batch.draw(background_grid, i*scale+game_x_offset, j*scale+game_y_offset, scale, scale);
            }
        }

        for (int i = 0; i < walls.size(); i++){
            walls.get(i).draw(batch, scale, game_x_offset, game_y_offset);
        }
        for (int i = 0; i < boxes.size(); i++){
            boxes.get(i).draw(batch, scale, game_x_offset, game_y_offset);
        }

        for (int i = 0; i < boxes.size(); i++){
            boxes.get(i).drawGoal(batch, scale, game_x_offset, game_y_offset);
        }
    }

    public boolean checkCompleted(BoxPuzzle game, int lvl_num){
        for (int i = 0; i< boxes.size(); i++){
            if (boxes.get(i).getCompletedGoal() == false){
                return false;
            }
        }
        game.completedLevel(lvl_num-1);
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
