package main.Blocks;

/**
 * Created by jacob on 2/19/16.
 */
public class Block
{
    public int x, y;

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getMapX() {
        return mapX;
    }

    public void setMapX(int mapX) {
        this.mapX = mapX;
    }

    public int getMapY() {
        return mapY;
    }

    public void setMapY(int mapY) {
        this.mapY = mapY;
    }

    public boolean isEmpty() {
        return empty;
    }

    public void setEmpty(boolean empty) {
        this.empty = empty;
    }

    public int mapX, mapY;
    public boolean empty = false;

    public Block(int x, int y)
    {
        this.x = x;
        this.y = y;
    }

    public void setMapCoords(int x, int y)
    {
        mapX = x;
        mapY = y;
    }
}
