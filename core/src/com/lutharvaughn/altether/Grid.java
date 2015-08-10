package com.lutharvaughn.altether;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Matt on 10/9/14.
 */
public class Grid {
    public List<Box>[][] grid;
    private int rows, cols;
    private int cellSize;
    private List<Box> boxes =  new ArrayList<Box>();

    public Grid(int height, int width, int size){
        cellSize = size;
        rows = (height+cellSize-1)/cellSize;
        cols = (width+cellSize-1)/cellSize;
        grid = new ArrayList [rows][cols];
        for (int i = 0; i< rows; i++){
            for (int j=0; j<cols; j++){
                grid[i][j] = new ArrayList<Box>();
            }
        }
    }

    public void add(Box box){
        int topLeftX = Math.max(0, box.x / cellSize);
        int topLeftY = Math.max(0, box.y / cellSize);
        int bottomRightX = Math.min(cols-1, (box.x + box.width -1) / cellSize);
        int bottomRightY = Math.min(rows-1, (box.y + box.height -1) / cellSize);

        for (int i = topLeftX; i<=bottomRightX; i++){
            for (int j = topLeftY; j<= bottomRightY; j++){
                grid[i][j].add(box);
            }
        }
    }

    public void remove(Box box){
        int topLeftX = Math.max(0, box.x / cellSize);
        int topLeftY = Math.max(0, box.y / cellSize);
        int bottomRightX = Math.min(cols-1, (box.x + box.width -1) / cellSize);
        int bottomRightY = Math.min(rows-1, (box.y + box.height -1) / cellSize);

        for (int i = topLeftX; i<=bottomRightX; i++){
            for (int j = topLeftY; j<= bottomRightY; j++){
                grid[i][j].remove(box);
            }
        }
    }

    public List<Box> space(Box box){
        boxes.clear();
        int topLeftX = Math.max(0, box.x / cellSize);
        int topLeftY = Math.max(0, box.y / cellSize);
        int bottomRightX = Math.min(cols-1, (box.x + box.width -1) / cellSize);
        int bottomRightY = Math.min(rows-1, (box.y + box.height -1) / cellSize);

        for (int i = topLeftX; i<=bottomRightX; i++){
            for (int j = topLeftY; j<= bottomRightY; j++){
                List<Box> space = grid[i][j];
                for (int k = 0; k< space.size(); k++){
                    Box cell = space.get(k);
                    if (!boxes.contains(cell))
                        boxes.add(cell);
                }
            }
        }
        return boxes;
    }
}
