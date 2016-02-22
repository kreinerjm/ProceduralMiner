package main.Entities;

/**
 * Created by jacob on 2/19/16.
 */
public class Player
{
    int x, y;
    int speed = 5;



    public int getMineDistance()
    {
        return mineDistance;
    }

    public void setMineDistance(int mineDistance) {
        this.mineDistance = mineDistance;
    }

    int mineDistance = 100;

    public boolean isLeft() {
        return left;
    }

    public void setLeft(boolean left) {
        this.left = left;
    }

    public boolean isRight() {
        return right;
    }

    public void setRight(boolean right) {
        this.right = right;
    }

    public boolean isUp() {
        return up;
    }

    public void setUp(boolean up) {
        this.up = up;
    }

    public boolean isDown() {
        return down;
    }

    public void setDown(boolean down) {
        this.down = down;
    }

    boolean left = false, right = false, up = false, down = false;
    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public Player(int x, int y)
    {
        this.x=x;
        this.y=y;
    }

    private void updatePosition()
    {

    }

    public void tick()
    {
        updatePosition();
    }

}
