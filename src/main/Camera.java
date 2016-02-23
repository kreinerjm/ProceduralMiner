package main;

/**
 * Created by jacob on 2/23/16.
 */
public class Camera
{
    int x;
    int y;

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    int width = 1024+16;
    int height = 512+32;

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



    public Camera(int x, int y)
    {
        this.x=x;
        this.y=y;
    }
}
