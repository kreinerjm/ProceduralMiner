package main.Maps;

import main.Blocks.Block;

/**
 * Created by jacob on 2/19/16.
 */
public class Map
{
    public Block[][] blocks;
    public int width, height;

    public Map()
    {
        width = 64;
        height = 32;

        blocks = new Block[width][height];
        for(int i = 0 ; i < width; i++)
        {
            for(int j = 0; j < height; j++)
            {
                blocks[i][j] = new Block(i*(height/2),j*(height/2));
                blocks[i][j].setMapCoords(i,j);
            }
        }


        for(int i = 0; i < width; i++)
        {
            for(int j = 0; j < height; j++)
            {
                if(i == 3 || i == 5 || j ==2 || j == 1)
                    blocks[i][j].empty = true;
            }
        }
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
            if(!blocks[b.mapX+1][b.mapY].empty)
            {
                return true;
            }
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
        return false;
    }

}
