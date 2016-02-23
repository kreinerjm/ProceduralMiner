package main.Maps;

import main.Blocks.Block;
import main.Camera;
import main.Entities.Player;

import java.util.ArrayList;

/**
 * Created by jacob on 2/19/16.
 */
public class Map
{
    public Block[][] blocks;
    public int width, height;
    private int gravity = 3;
    public int scale = 16;

    public int getGravity() {
        return gravity;
    }

    public void setGravity(int gravity) {
        this.gravity = gravity;
    }



    public Map()
    {
        width = 128;
        height = 64;


        blocks = new Block[width][height];
        for(int i = 0 ; i < width; i++)
        {
            for(int j = 0; j < height; j++)
            {
                blocks[i][j] = new Block(i*(scale),j*(scale));
                blocks[i][j].setMapCoords(i,j);
            }
        }


        for(int i = 0; i < width; i++)
        {
            for(int j = 0; j < height; j++)
            {
                if(j <= 20)
                    blocks[i][j].empty = true;
            }
        }
    }

    public ArrayList<Block> getContainingBlocks(Player p)
    {
        ArrayList<Block> temp = new ArrayList<Block>();
        for(int i = p.getX(); i < p.getX()+20; i++)
        {
            for (int j = p.getY(); j < p.getY()+20; j++)
            {
                    Block temp2 = getBlockAt(i, j);
                    if (!temp.contains(temp2))
                        temp.add(temp2);
            }
        }

        return temp;
    }

    public ArrayList<Block> getContainingBlocks(Camera c)
    {
        ArrayList<Block> temp = new ArrayList<Block>();
        Block topLeft = getBlockAt(c.getX(),c.getY());
        int topLeftX = topLeft.getMapX();
        int topLeftY = topLeft.getMapY();
        Block bottomRight = getBlockAt(c.getX()+c.getWidth(),c.getY()+c.getHeight());
        int bottomRightX = bottomRight.getMapX();
        int bottomRightY = bottomRight.getMapY();

        System.out.println("x1 :"+topLeftX+" x2 :"+bottomRightX+" y1 :"+topLeftY+" y2:"+bottomRightY);

        if(topLeftX < bottomRightX)
        {
            //System.out.println("2nd is bigger");
            for(int i = topLeftX; i < bottomRightX; i++)
            {
                for(int j = topLeftY; j < bottomRightY; j++)
                {
                    temp.add(blocks[i][j]);
                }
            }
        }
        else if(topLeftX > bottomRightX)
        {
            //System.out.println("1st is bigger");
            for(int i = topLeftX; i < width; i++)
            {
                for(int j = topLeftY; j < bottomRightY; j++)
                {
                    //System.out.println("Adding "+i+" "+j);
                    temp.add(blocks[i][j]);
                }
            }
            for(int i = 0; i < bottomRightX; i++)
            {
                for(int j = topLeftY; j < bottomRightY; j++)
                {
                    //System.out.println("Adding "+i+" "+j);
                    temp.add(blocks[i][j]);
                }
            }
        }




        return temp;
    }

    public Block getBlockAt(int x, int y)
    {
        if(x < 0)
            return blocks[(((width*scale)+x)/scale)][(y/scale)];
        else if(x > width*scale - 1)
            return blocks[((x%(width*scale))/scale)][(y/scale)];
        else
            return blocks[x/scale][y/scale];

    }

    public boolean hasBlockBelow(Block b)
    {
        if(b.mapY < height-1)
        {
            if(!blocks[b.mapX][b.mapY+1].empty)
            {
                return true;
            }
        }
        return false;
    }

    public boolean hasBlockAbove(Block b)
    {
            if(b.mapY > 0)
            {
                if(!blocks[b.mapX][b.mapY-1].empty)
                {
                    return true;
                }
            }
        return false;
    }

    public boolean hasBlockRight(Block b)
    {
        if(b.mapX < width-1)
        {
            if(!blocks[b.getMapX()+1][b.getMapY()].empty)
            {
                return true;
            }
        }
        else
        {
            if(!blocks[0][b.getMapY()].empty)
                return true;
        }
        return false;
    }

    public boolean hasBlockLeft(Block b)
    {
        if(b.mapX > 0)
        {
            if(!blocks[b.mapX-1][b.mapY].empty)
            {
                return true;
            }
        }
        else
        {
            if(!blocks[width-1][b.mapY].empty)
                return true;
        }
        return false;
    }

}
