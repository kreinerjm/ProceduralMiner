package main.Blocks;
import java.awt.Color;

/**
 * Created by jacob on 2/23/16.
 */
public class Dirt extends Block
{
    public Dirt(int x, int y)
    {
        this.x=x;
        this.y=y;
        setC(new Color(139,69,19));
    }

    public void tick()
    {

    }
}
