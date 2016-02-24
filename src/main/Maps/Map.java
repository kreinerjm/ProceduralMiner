package main.Maps;

import main.Blocks.Block;
import main.Blocks.Dirt;
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
    int groundLevel = 50;

    public int getGravity() {
        return gravity;
    }

    public void setGravity(int gravity) {
        this.gravity = gravity;
    }

    public Map()
    {
        width = 1024;
        height = 512;


        blocks = new Block[width][height];
        for(int i = 0 ; i < width; i++)
        {
            for(int j = 0; j < height; j++)
            {
                blocks[i][j] = new Dirt(i*(scale),j*(scale));
                blocks[i][j].setMapCoords(i,j);
            }
        }


        for(int i = 0; i < width; i++)
        {
            for(int j = 0; j < height; j++)
            {
                if(j <= groundLevel)
                    blocks[i][j].empty = true;
            }
        }

        generateMountains();
    }

    public void generateMountains()
    {
        int numMountains = (int)(1 + Math.random()*(width/128));
        for(int j = 0; j < numMountains; j++)
        {
            int randomX = (int) (Math.random() * width);
            System.out.println("random x :"+randomX);
            Block parent = new Dirt(randomX*scale, groundLevel*scale);
            parent.setMapCoords(randomX,groundLevel);
            System.out.println(parent.getMapY()+parent.getMapX());
            blocks[randomX][groundLevel] = parent;
            int maxHeight = 3 * groundLevel / 4;
            ArrayList<Block> mountain = new ArrayList<Block>();
            mountain.add(parent);
            System.out.println("Mountain size : "+mountain.size());
            int randomSize = (int) (Math.random() * maxHeight * scale);
            int currentRow = parent.getMapY();
            //System.out.println("parent mapy : "+currentRow);
            for (int i = 0; i < randomSize; i++)
            {
                ArrayList<Block> surrounding = getSurrounding(mountain);
                System.out.println("surrounding :"+surrounding.size());
                int temp = (int) (Math.random()*surrounding.size());
                surrounding.get(temp).setEmpty(false);
                mountain.add(surrounding.get(temp));

            }
        }
    }

    public ArrayList<Block> getSurrounding(ArrayList<Block> A)
    {
        ArrayList<Block> toReturn = new ArrayList<Block>();
        for(Block b : A)
        {
            if(!hasBlockLeft(b)&&!toReturn.contains(blocks[b.mapX-1][b.mapY]))
            {
                System.out.println("1");
                toReturn.add(blocks[b.mapX-1][b.mapY]);
            }
            if(!hasBlockAbove(b)&&!toReturn.contains(blocks[b.mapX][b.mapY-1]))
            {
                System.out.println("2");
                toReturn.add(blocks[b.mapX][b.mapY-1]);
            }
            if (!hasBlockRight(b)&&!toReturn.contains(blocks[b.mapX+1][b.mapY]))
            {
                System.out.println("3");
                toReturn.add(blocks[b.mapX+1][b.mapY]);
            }
        }
        return toReturn;
    }


    public ArrayList<Block> getOpenBordering(Block b)
    {
        ArrayList<Block> toReturn = new ArrayList<Block>();
        if(hasBlockLeft(b))
            toReturn.add(getBlockAt(b.getMapX()-1,b.getMapY()));
        if(hasBlockRight(b))
            toReturn.add(getBlockAt(b.getMapX()+1,b.getMapY()));
        if(hasBlockAbove(b))
            toReturn.add(getBlockAt(b.getMapX(),b.getMapY()-1));
        return toReturn;
    }


    public boolean hasAnyAbove(int x, int y)
    {
        if(y==0)
            return false;
        else
        {
            if(hasBlockAbove(getBlockAt(x*scale,y*scale)))
            {
                return true;
            }
            else
                return hasAnyAbove(x, y-1);
        }
    }

    public Block getFirstLeft(int x, int y)
    {
        if(blocks[x-1][y].isEmpty())
            return blocks[x-1][y];
        else
        {
            return getFirstLeft(x - 1, y);
        }
    }

    public Block getFirstRight(int x, int y)
    {
        if(blocks[x+1][y].isEmpty())
            return blocks[x+1][y];
        else
        {
            return getFirstLeft(x+1, y);
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
