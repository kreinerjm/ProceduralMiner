package main.Maps;

import main.Blocks.Block;
import main.Blocks.Dirt;
import main.Blocks.Stone;
import main.Camera;
import main.Entities.Player;

import java.awt.*;
import java.util.ArrayList;

/**
 * Created by jacob on 2/19/16.
 */

public class Map
{
    public Block[][] blocks;
    public int width, height;
    private int gravity = 10;
    public int scale = 16;
    int groundLevel = 200;

    public int getGravity() {
        return gravity;
    }

    public void setGravity(int gravity) {
        this.gravity = gravity;
    }

    public Map()
    {
        width = 4096;
        height = 1024;

        blocks = new Block[width][height];

        fillWithDirt();
        createStones();
        createDirtInStone();
        createOverWorld();
        generateMountains();
        generateRavines();
        generateCaves();
    }

    public void createStones()
    {
        for(int j = 0; j < width; j++)
        {
            for (int i = height / 2; i < height; i++)
            {
                blocks[j][i] = new Stone(j*scale,i*scale);
                blocks[j][i].setMapCoords(j,i);
            }
        }
        int numStone = (height*width/(scale*8));
        int randx, randy;
        Block parent;
        for(int i = 0; i < numStone; i++)
        {
            randx = (int)(Math.random()*width);
            randy = (int)(Math.random()*height);
            if(blocks[randx][randy] instanceof Dirt)
            {
                blocks[randx][randy] = new Stone(randx*scale,randy*scale);
                blocks[randx][randy].setMapCoords(randx,randy);
                parent = blocks[randx][randy];
                ArrayList<Block> stones = new ArrayList<Block>();
                stones.add(parent);
                ArrayList<Block> surrounding;
                int size = (int)(Math.random()*32);
                for(int j = 0; j < size; j++)
                {
                    surrounding = getSurrounding(stones);
                    if(surrounding.size()==0)
                        break;
                    int rand =(int) (Math.random()*surrounding.size());
                    blocks[surrounding.get(rand).getMapX()][surrounding.get(rand).getMapY()] = new Stone(surrounding.get(rand).getX(),surrounding.get(rand).getY());
                    blocks[surrounding.get(rand).getMapX()][surrounding.get(rand).getMapY()].setMapCoords(surrounding.get(rand).getMapX(),surrounding.get(rand).getMapY());
                    stones.add(blocks[surrounding.get(rand).getMapX()][surrounding.get(rand).getMapY()]);
                }

            }
            else
            {
                i--;
                continue;
            }
        }
    }

    public void createDirtInStone()
    {
        int numDirt = (height*width/(scale*8));
        int randx, randy;
        Block parent;
        for(int i = 0; i < numDirt; i++)
        {
            randx = (int)(Math.random()*width);
            randy = (int)(height/2+Math.random()*(height/2));
            if(blocks[randx][randy] instanceof Stone)
            {
                blocks[randx][randy] = new Dirt(randx*scale,randy*scale);
                blocks[randx][randy].setMapCoords(randx,randy);
                parent = blocks[randx][randy];
                ArrayList<Block> dirts = new ArrayList<Block>();
                dirts.add(parent);
                ArrayList<Block> surrounding;
                int size = (int)(Math.random()*32);
                //System.out.println(size);
                for(int j = 0; j < size; j++)
                {
                    surrounding = getSurrounding(dirts);
                    if(surrounding.size()==0)
                        break;
                    //System.out.println(surrounding.size());
                    int rand =(int) (Math.random()*surrounding.size());
                    blocks[surrounding.get(rand).getMapX()][surrounding.get(rand).getMapY()] = new Dirt(surrounding.get(rand).getX(),surrounding.get(rand).getY());
                    blocks[surrounding.get(rand).getMapX()][surrounding.get(rand).getMapY()].setMapCoords(surrounding.get(rand).getMapX(),surrounding.get(rand).getMapY());
                    dirts.add(blocks[surrounding.get(rand).getMapX()][surrounding.get(rand).getMapY()]);
                }

            }
            else
            {
                i--;
                continue;
            }
        }
    }


    public void createOverWorld()
    {
        for(int i = 0; i < width; i++)
        {
            for(int j = 0; j < height; j++)
            {
                if(j <= groundLevel)
                    blocks[i][j].empty = true;
            }
        }
    }


    public void fillWithDirt()
    {
        for(int i = 0 ; i < width; i++)
        {
            for(int j = 0; j < height; j++)
            {
                blocks[i][j] = new Dirt(i*(scale),j*(scale));
                blocks[i][j].setMapCoords(i,j);
            }
        }
    }

    public void generateMountains()
    {
        long start, end;
        start = System.currentTimeMillis();
        System.out.println("Starting Mountains");
        int numMountains = (int)(1 + Math.random()*(width/(scale*2)));
        System.out.println(numMountains);
        for(int j = 0; j < numMountains; j++)
        {
            int randomX = (int) (Math.random() * width);
           // System.out.println("random x :"+randomX);
            Block parent = new Dirt(randomX*scale, groundLevel*scale);
            parent.setMapCoords(randomX,groundLevel);
           // System.out.println(parent.getMapY()+parent.getMapX());
            blocks[randomX][groundLevel] = parent;
            int maxHeight = groundLevel / 2;
            ArrayList<Block> mountain = new ArrayList<Block>();
            mountain.add(parent);
          //  System.out.println("Mountain size : "+mountain.size());
            int randomSize = (int) (Math.random() * (maxHeight * scale * 2));
            //System.out.println("parent mapy : "+currentRow);
            for (int i = 0; i < randomSize; i++)
            {
                ArrayList<Block> surrounding = getSurroundingAbove(mountain);
                if(surrounding.size() == 0)
                    break;
             //   System.out.println("surrounding :"+surrounding.size());
                int temp = (int) (Math.random()*surrounding.size());
                surrounding.get(temp).setEmpty(false);
                mountain.add(surrounding.get(temp));

            }
        }



            end = System.currentTimeMillis();
        System.out.println("Ending after "+(end-start)+" miliseconds");
    }

    public void generateCaves()
    {
        int numCaves = (int)(500 + (Math.random()*(((height-groundLevel)*width)/(scale*scale*scale))));
        System.out.println("Starting caves");
        System.out.println(numCaves);
        for(int j = 0; j < numCaves; j++)
        {
            int randomX = (int) (Math.random() * width);
            int randomY = (int) (groundLevel + (Math.random() * height - groundLevel));
            // System.out.println("random x :"+randomX);
            Block parent = getBlockAt(randomX * scale, randomY * scale);
            parent.setMapCoords(randomX,randomY);
            // System.out.println(parent.getMapY()+parent.getMapX());
            if(!blocks[randomX][randomY].isEmpty()) {
                blocks[randomX][randomY] = parent;
                int maxHeight = (height - groundLevel);
                ArrayList<Block> caves = new ArrayList<Block>();
                caves.add(parent);
                parent.setEmpty(true);
                //  System.out.println("Mountain size : "+mountain.size());
                int randomSize = (int) (Math.random() * (maxHeight));
                //System.out.println("parent mapy : "+currentRow);
                for (int i = 0; i < randomSize; i++) {
                    ArrayList<Block> surrounding = getSurrounding(caves);
                    if (surrounding.size() == 0)
                        break;
                    //System.out.println("surrounding :"+surrounding.size());
                    int temp = (int) (Math.random() * surrounding.size());
                    surrounding.get(temp).setEmpty(true);
                    caves.add(surrounding.get(temp));

                }
            }
        }
    }


    public void generateRavines()
    {
        long start, end;
        start = System.currentTimeMillis();
        //System.out.println("Starting Ravines");
        int numRavines = (int)(1 + Math.random()*(width/(scale*2)));
        System.out.println(numRavines);
        for(int j = 0; j < numRavines; j++)
        {
            int randomX = (int) (Math.random() * width);
            // System.out.println("random x :"+randomX);
            Block parent = getHighestY(randomX);
            parent.setMapCoords(randomX,parent.getY()/scale);
            // System.out.println(parent.getMapY()+parent.getMapX());
            blocks[randomX][parent.getMapY()] = parent;
            int maxHeight = groundLevel / 2;
            ArrayList<Block> ravines = new ArrayList<Block>();
            ravines.add(parent);
            parent.setEmpty(true);
            //  System.out.println("Mountain size : "+mountain.size());
            int randomSize = (int) (Math.random() * (maxHeight * scale) / 2);
            //System.out.println("parent mapy : "+currentRow);
            for (int i = 0; i < randomSize; i++)
            {
                ArrayList<Block> surrounding = getSurroundingBelow(ravines);
                if(surrounding.size() == 0)
                    break;
                //System.out.println("surrounding :"+surrounding.size());
                int temp = (int) (Math.random()*surrounding.size());
                surrounding.get(temp).setEmpty(true);
                ravines.add(surrounding.get(temp));

            }
        }

        for(int i = 0; i < width; i++)
        {
            for(int j = 0; j < height; j++)
            {
                blocks[i][j].tick();
                if(blocks[i][j] instanceof Dirt && !hasAnyAbove(i,j))
                    blocks[i][j].setC(Color.GREEN);
            }
        }

        end = System.currentTimeMillis();
        System.out.println("Ending after "+(end-start)+" miliseconds");
    }

    public ArrayList<Block> getSurrounding(ArrayList<Block> A)
    {
        ArrayList<Block> toReturn = new ArrayList<Block>();
        for(Block b : A)
        {
            if(hasBlockLeft(b)&&!toReturn.contains(getBlockAt(b.x-scale,b.y)))
            {
                //System.out.println("1");
                toReturn.add(getBlockAt(b.x-scale,b.y));
            }
            if(hasBlockAbove(b)&&!toReturn.contains(getBlockAt(b.x,b.y-scale)))
            {
                //System.out.println("2");
                toReturn.add(getBlockAt(b.x,b.y-scale));
            }
            if(hasBlockRight(b)&&!toReturn.contains(getBlockAt(b.x+scale,b.y)))
            {
                //System.out.println("3");
                toReturn.add(getBlockAt(b.x+scale,b.y));
            }
            if(hasBlockBelow(b)&&!toReturn.contains(getBlockAt(b.x,b.y+scale)))
            {
                //System.out.println("2");
                toReturn.add(getBlockAt(b.x,b.y+scale));
            }
        }
        return toReturn;
    }

    public ArrayList<Block> getSurroundingAbove(ArrayList<Block> A)
    {
        ArrayList<Block> toReturn = new ArrayList<Block>();
        for(Block b : A)
        {
            if(!hasBlockLeft(b)&&!toReturn.contains(getBlockAt(b.x-scale,b.y)))
            {
                //System.out.println("1");
                toReturn.add(getBlockAt(b.x-scale,b.y));
            }
            if(!hasBlockAbove(b)&&!toReturn.contains(getBlockAt(b.x,b.y-scale)))
            {
                //System.out.println("2");
                toReturn.add(getBlockAt(b.x,b.y-scale));
            }
            if (!hasBlockRight(b)&&!toReturn.contains(getBlockAt(b.x+scale,b.y)))
            {
                //System.out.println("3");
                toReturn.add(getBlockAt(b.x+scale,b.y));
            }
        }
        return toReturn;
    }

    public ArrayList<Block> getSurroundingBelow(ArrayList<Block> A)
    {
        ArrayList<Block> toReturn = new ArrayList<Block>();
        for(Block b : A)
        {
            if(hasBlockLeft(b)&&!toReturn.contains(getBlockAt(b.x-scale,b.y)))
            {
                //System.out.println("1");
                toReturn.add(getBlockAt(b.x-scale,b.y));
            }
            if(hasBlockBelow(b)&&!toReturn.contains(getBlockAt(b.x,b.y+scale)))
            {
                //System.out.println("2");
                toReturn.add(getBlockAt(b.x,b.y+scale));
            }
            if (hasBlockRight(b)&&!toReturn.contains(getBlockAt(b.x+scale,b.y)))
            {
                //System.out.println("3");
                toReturn.add(getBlockAt(b.x+scale,b.y));
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

    public Block getFirstBelow(int x, int y)
    {
        if(!blocks[x][y+1].isEmpty())
            return blocks[x][y+1];
        else
        {
            return getFirstBelow(x, y + 1);
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
        long start, end;
        start = System.currentTimeMillis();
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
        end = System.currentTimeMillis();
        //System.out.println("Ending getContaining Blocks after "+(end-start)+" miliseconds");
        return temp;
    }


    public Block getHighestY(int x)
    {
        for(int i = 0; i < height; i++)
        {
            if(!blocks[x][i].isEmpty())
            {
               // System.out.println("Found highest at y = "+i);
                return blocks[x][i];
            }

        }
        return null;
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
