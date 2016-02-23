package main;

import main.Entities.Player;
import main.Maps.Map;
import main.Blocks.Block;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

/**
 * Created by jacob on 2/18/16.
 */
public class MinerPanel extends JPanel implements MouseListener, KeyListener
{

    Player player = new Player(256,256);
    BufferedImage buffer;
    Map map;
    Camera camera = new Camera(0,0);
    boolean left;

    public MinerPanel()
    {
        setPreferredSize(new Dimension(1024+16, 512+32));
        map = new Map();
        this.setFocusable(true);
        addKeyListener(this);
        addMouseListener(this);
        buffer = new BufferedImage(1024+16,512+32,BufferedImage.TYPE_INT_ARGB);
        setOpaque(true);
    }

    public void paint(Graphics g)
    {
        Graphics2D g2d = (Graphics2D) g;
        Graphics b2d = buffer.createGraphics();
        b2d.setColor(Color.WHITE);
        b2d.fillRect(0, 0, buffer.getWidth(), buffer.getHeight());
        
        ArrayList<Block> cameraContains = map.getContainingBlocks(camera);
        if(left && camera.getX() > map.width*map.scale-camera.getWidth())
        {
            //System.out.println("left of seam");
            for(Block b : cameraContains) {
                b2d.setColor(Color.CYAN);
                if (b.getMapX() < map.width / 2) { // on right side of seam
                    //b2d.fillRect(-camera.getX() + (b.getX() + map.width * map.scale), -camera.getY() + b.getY(), map.scale, map.scale);

                    if (!b.empty) {
                        b2d.setColor(Color.BLACK);
                        // System.out.println("Not empty : "+i+","+j);
                        if (!map.hasBlockAbove(b)) {
                            //    System.out.println("Drawing line above "+i+","+j);
                            b2d.drawLine(-camera.getX() + (b.getMapX() + map.width) * map.scale, -camera.getY() + b.getMapY() * map.scale, -camera.getX() + (b.getMapX() + 1 + map.width) * map.scale, -camera.getY() + b.getMapY() * map.scale);
                        }
                        if (!map.hasBlockBelow(b)) {
                            //     System.out.prb.getMapX()ntln("Drawing line below "+i+","+j);
                            b2d.drawLine(-camera.getX() + (b.getMapX() + map.width) * map.scale, -camera.getY() + (b.getMapY() + 1) * map.scale, -camera.getX() + (b.getMapX() + 1 + map.width) * map.scale, -camera.getY() + (b.getMapY() + 1) * map.scale);
                        }
                        if (!map.hasBlockLeft(b)) {
                            //    System.out.println("Drawing line to the left of "+i+","+j);
                            b2d.drawLine(-camera.getX() + (b.getMapX() + map.width) * map.scale, -camera.getY() + b.getMapY() * map.scale, -camera.getX() + (b.getMapX() + map.width) * map.scale, -camera.getY() + (b.getMapY() + 1) * map.scale);
                        }
                        if (!map.hasBlockRight(b)) {
                            //    System.out.println("Drawing line to the right of "+i+","+j);
                            b2d.drawLine(-camera.getX() + (b.getMapX() + 1 + map.width) * map.scale, -camera.getY() + b.getMapY() * map.scale, -camera.getX() + (b.getMapX() + 1 + map.width) * map.scale, -camera.getY() + (b.getMapY() + 1) * map.scale);
                        }
//
                    }

                } //working
                else {
                    //b2d.fillRect(-camera.getX() + b.getX(), -camera.getY() + b.getY(), map.scale, map.scale);

                    if (!b.empty) {
                        b2d.setColor(Color.BLACK);
                        // System.out.println("Not empty : "+i+","+j);
                        if (!map.hasBlockAbove(b)) {
                            //    System.out.println("Drawing line above "+i+","+j);
                            b2d.drawLine(-camera.getX() + b.getMapX() * map.scale, -camera.getY() + b.getMapY() * map.scale, -camera.getX() + (b.getMapX() + 1) * map.scale, -camera.getY() + b.getMapY() * map.scale);
                        }
                        if (!map.hasBlockBelow(b)) {
                            //     System.out.prb.getMapX()ntln("Drawing line below "+i+","+j);
                            b2d.drawLine(-camera.getX() + b.getMapX() * map.scale, -camera.getY() + (b.getMapY() + 1) * map.scale, -camera.getX() + (b.getMapX() + 1) * map.scale, -camera.getY() + (b.getMapY() + 1) * map.scale);
                        }
                        if (!map.hasBlockLeft(b)) {
                            //    System.out.println("Drawing line to the left of "+i+","+j);
                            b2d.drawLine(-camera.getX() + b.getMapX() * map.scale, -camera.getY() + b.getMapY() * map.scale, -camera.getX() + b.getMapX() * map.scale, -camera.getY() + (b.getMapY() + 1) * map.scale);
                        }
                        if (!map.hasBlockRight(b)) {
                            //    System.out.println("Drawing line to the right of "+i+","+j);
                            b2d.drawLine(-camera.getX() + (b.getMapX() + 1) * map.scale, -camera.getY() + b.getMapY() * map.scale, -camera.getX() + (b.getMapX() + 1) * map.scale, -camera.getY() + (b.getMapY() + 1) * map.scale);
                        }
                    }
                }
            }

        }
        else if(!left && camera.getX() < 0)
        {
            //System.out.println("right of seam");
            for(Block b : cameraContains) {
                b2d.setColor(Color.CYAN);
                if (b.getMapX() > map.width / 2) {
                    //b2d.fillRect(-camera.getX() + (b.getX() - map.width * map.scale), -camera.getY() + b.getY(), map.scale, map.scale);

                    if (!b.empty) {
                        b2d.setColor(Color.BLACK);
                        // System.out.println("Not empty : "+i+","+j);
                        if (!map.hasBlockAbove(b)) {
                            //    System.out.println("Drawing line above "+i+","+j);
                            b2d.drawLine(-camera.getX() + (b.getMapX() - map.width) * map.scale, -camera.getY() + b.getMapY() * map.scale, -camera.getX() + (b.getMapX() + 1 - map.width) * map.scale, -camera.getY() + b.getMapY() * map.scale);
                        }
                        if (!map.hasBlockBelow(b)) {
                            //     System.out.prb.getMapX()ntln("Drawing line below "+i+","+j);
                            b2d.drawLine(-camera.getX() + (b.getMapX() - map.width) * map.scale, -camera.getY() + (b.getMapY() + 1) * map.scale, -camera.getX() + (b.getMapX() + 1 - map.width) * map.scale, -camera.getY() + (b.getMapY() + 1) * map.scale);
                        }
                        if (!map.hasBlockLeft(b)) {
                            //    System.out.println("Drawing line to the left of "+i+","+j);
                            b2d.drawLine(-camera.getX() + (b.getMapX() - map.width) * map.scale, -camera.getY() + b.getMapY() * map.scale, -camera.getX() + (b.getMapX() - map.width) * map.scale, -camera.getY() + (b.getMapY() + 1) * map.scale);
                        }
                        if (!map.hasBlockRight(b)) {
                            //    System.out.println("Drawing line to the right of "+i+","+j);
                            b2d.drawLine(-camera.getX() + (b.getMapX() + 1 - map.width) * map.scale, -camera.getY() + b.getMapY() * map.scale, -camera.getX() + (b.getMapX() + 1 - map.width) * map.scale, -camera.getY() + (b.getMapY() + 1) * map.scale);
                        }
//
                    }

                } else {
                   // b2d.fillRect(-camera.getX() + b.getX(), -camera.getY() + b.getY(), map.scale, map.scale);

                    if (!b.empty) {
                        b2d.setColor(Color.BLACK);
                        // System.out.println("Not empty : "+i+","+j);
                        if (!map.hasBlockAbove(b)) {
                            //    System.out.println("Drawing line above "+i+","+j);
                            b2d.drawLine(-camera.getX() + b.getMapX() * map.scale, -camera.getY() + b.getMapY() * map.scale, -camera.getX() + (b.getMapX() + 1) * map.scale, -camera.getY() + b.getMapY() * map.scale);
                        }
                        if (!map.hasBlockBelow(b)) {
                            //     System.out.prb.getMapX()ntln("Drawing line below "+i+","+j);
                            b2d.drawLine(-camera.getX() + b.getMapX() * map.scale, -camera.getY() + (b.getMapY() + 1) * map.scale, -camera.getX() + (b.getMapX() + 1) * map.scale, -camera.getY() + (b.getMapY() + 1) * map.scale);
                        }
                        if (!map.hasBlockLeft(b)) {
                            //    System.out.println("Drawing line to the left of "+i+","+j);
                            b2d.drawLine(-camera.getX() + b.getMapX() * map.scale, -camera.getY() + b.getMapY() * map.scale, -camera.getX() + b.getMapX() * map.scale, -camera.getY() + (b.getMapY() + 1) * map.scale);
                        }
                        if (!map.hasBlockRight(b)) {
                            //    System.out.println("Drawing line to the right of "+i+","+j);
                            b2d.drawLine(-camera.getX() + (b.getMapX() + 1) * map.scale, -camera.getY() + b.getMapY() * map.scale, -camera.getX() + (b.getMapX() + 1) * map.scale, -camera.getY() + (b.getMapY() + 1) * map.scale);
                        }
//
                    }
                }
            }

        }
        else
        {

            for(Block b : cameraContains) {
                b2d.setColor(Color.CYAN);
                System.out.println("seam not in view");
                //b2d.fillOval(-camera.getX() + b.getX(),-camera.getY() + b.getY(),10,10);
               // b2d.fillRect(-camera.getX() + b.getX(), -camera.getY() + b.getY(), map.scale, map.scale);
                System.out.println(cameraContains.size());
                //b2d.fillRect(-camera.getX() + b.getX(), -camera.getY() + b.getY(), map.scale, map.scale);

                if (!b.empty) {
                    b2d.setColor(Color.BLACK);
                    // System.out.println("Not empty : "+i+","+j);
                    if (!map.hasBlockAbove(b)) {
                        //    System.out.println("Drawing line above "+i+","+j);
                        b2d.drawLine(-camera.getX() + b.getMapX() * map.scale, -camera.getY() + b.getMapY() * map.scale, -camera.getX() + (b.getMapX() + 1) * map.scale, -camera.getY() + b.getMapY() * map.scale);
                    }
                    if (!map.hasBlockBelow(b)) {
                        //     System.out.prb.getMapX()ntln("Drawing line below "+i+","+j);
                        b2d.drawLine(-camera.getX() + b.getMapX() * map.scale, -camera.getY() + (b.getMapY() + 1) * map.scale, -camera.getX() + (b.getMapX() + 1) * map.scale, -camera.getY() + (b.getMapY() + 1) * map.scale);
                    }
                    if (!map.hasBlockLeft(b)) {
                        //    System.out.println("Drawing line to the left of "+i+","+j);
                        b2d.drawLine(-camera.getX() + b.getMapX() * map.scale, -camera.getY() + b.getMapY() * map.scale, -camera.getX() + b.getMapX() * map.scale, -camera.getY() + (b.getMapY() + 1) * map.scale);
                    }
                    if (!map.hasBlockRight(b)) {
                        //    System.out.println("Drawing line to the right of "+i+","+j);
                        b2d.drawLine(-camera.getX() + (b.getMapX() + 1) * map.scale, -camera.getY() + b.getMapY() * map.scale, -camera.getX() + (b.getMapX() + 1) * map.scale, -camera.getY() + (b.getMapY() + 1) * map.scale);
                    }

                }

            }
        }



        b2d.setColor(Color.blue);
        b2d.drawOval(player.getX()- camera.getX(),player.getY()-camera.getY(),20,20);
        Block temp = map.getBlockAt(player.getX(),player.getY());
        b2d.drawString("Player current square = "+temp.getMapX()+","+temp.getMapY(),500,20);
        b2d.drawString("Player current x,y = "+player.getX()+","+player.getY(),500,40);
        b2d.drawString("Camera current x,y = "+camera.getX()+","+camera.getY(),500,60);
        g2d.drawImage(buffer,0,0,buffer.getWidth(),buffer.getHeight(), null);

    }



    public void tick()
    {
       // System.out.println(left);
        if(player.getX() > (map.width*map.scale)/2 && player.getX() < (map.width*map.scale))
            left = true;
        else if(player.getX() > 0 && player.getX() < (map.width*map.scale)/2)
            left = false;

        player.tick();
        camera.setX(player.getX()-512);
        camera.setY(player.getY()-256);
        playerCollisionDetection();

        repaint();
    }

    public void playerCollisionDetection()
    {
        ArrayList<Block> blocks = map.getContainingBlocks(player);
        ArrayList<Block> lowest = getLowestY(blocks);
        boolean below = false;
        for(Block b : lowest)
        {
            if(map.hasBlockBelow(b))
            {
                below = true;
            }
        }
        if(below && !player.isUp())
        {
            if(player.getY()+map.getGravity()+20 < lowest.get(0).getY()+map.scale)
                player.setY(player.getY()+map.getGravity());
            else
                player.setY(lowest.get(0).getY()-20+map.scale);
        }
        else
        {
            player.setY(player.getY()+map.getGravity());
        }

        if(player.isLeft())
        {
            ArrayList<Block> leftMost = getLowestX(blocks);
            boolean left = false;
            for(Block b : leftMost)
            {
                if(map.hasBlockLeft(b))
                {
                    left = true;
                }
            }
            if(left)
            {
                if(player.getX()-player.getSpeed() > leftMost.get(0).getX()+map.scale)
                    player.setX(player.getX() - player.getSpeed());
                else
                    player.setX(leftMost.get(0).getX()+1);
            }
            else
                player.setX(player.getX()-player.getSpeed());

        }

        if(player.isRight())
        {
            ArrayList<Block> rightMost = getHighestX(blocks);
            boolean right = false;
            for(Block b : rightMost)
            {
                if(map.hasBlockRight(b))
                {
                    right = true;
                }
            }
            if(right)
            {
                if(player.getX()+player.getSpeed()+20 < rightMost.get(0).getX()+map.scale)
                    player.setX(player.getX() + player.getSpeed());
                else
                    player.setX(rightMost.get(0).getX() - 20 + map.scale);
            }
            else
                player.setX(player.getX()+player.getSpeed());

        }

        if(player.isUp())
        {
            ArrayList<Block> highest = getHighestY(blocks);
            boolean above = false;
            for(Block b : highest)
            {
                if(map.hasBlockAbove(b))
                {
                    above = true;
                }
            }
            if(above)
            {
                if(player.getY()-player.getSpeed() > highest.get(0).getY())
                    player.setY(player.getY() - player.getSpeed());
                else
                    player.setY(highest.get(0).getY());
            } else
                player.setY(player.getY()-player.getSpeed());

        }
        if(player.getX() < 0)
        {
            player.setX(map.width*map.scale+player.getX());
        }
        if(player.getX() > map.width*map.scale)
        {
            player.setX(player.getX()-map.width*map.scale);
        }
    }

    public ArrayList<Block> getHighestY(ArrayList<Block> B)
    {
        ArrayList<Block> toReturn = new ArrayList<Block>();
        int highest = Integer.MAX_VALUE;
        for(Block b : B)
        {
            if(b.getMapY() < highest)
            {
                highest = b.getMapY();
            }
        }
        for(Block b : B)
        {
            if(b.getMapY() == highest)
            {
                toReturn.add(b);
            }
        }
        return toReturn;
    }

    public ArrayList<Block> getLowestY(ArrayList<Block> B)
    {
        ArrayList<Block> toReturn = new ArrayList<Block>();
        int lowest = Integer.MIN_VALUE;
        for(Block b : B)
        {
            if(b.getMapY() > lowest)
            {
                lowest = b.getMapY();
            }
        }
        for(Block b : B)
        {
            if(b.getMapY() == lowest)
            {
                toReturn.add(b);
            }
        }
        return toReturn;
    }

    public ArrayList<Block> getLowestX(ArrayList<Block> B)
    {
        ArrayList<Block> toReturn = new ArrayList<Block>();
        int lowest = Integer.MAX_VALUE;
        for(Block b : B)
        {
            if(b.getMapX() < lowest)
            {
                lowest = b.getMapX();
            }
        }
        for(Block b : B)
        {
            if(b.getMapX() == lowest)
            {
                toReturn.add(b);
            }
        }
        return toReturn;
    }

    public ArrayList<Block> getHighestX(ArrayList<Block> B)
    {
        ArrayList<Block> toReturn = new ArrayList<Block>();
        int highest = Integer.MIN_VALUE;
        for(Block b : B)
        {
            if(b.getMapX() > highest)
            {
                highest = b.getMapX();
            }
        }
        for(Block b : B)
        {
            if(b.getMapX() == highest)
            {
                toReturn.add(b);
            }
        }
        return toReturn;
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e)
    {

    }

    @Override
    public void mouseReleased(MouseEvent e)
    {
        Block selected = map.blocks[(e.getX() + camera.getX())/(map.scale)][(e.getY() + camera.getY())/(map.scale)];
        int distance1 = Math.abs(selected.getX()+(map.scale/2)-player.getX()+10);
        int distance2 = Math.abs(selected.getY()+(map.scale/2)-player.getY()+10);
        int distance = (int)(Math.sqrt(distance1*distance1 + distance2*distance2)+.5);
        if(distance<=player.getMineDistance())
            selected.empty = true;
        System.out.println(distance);
        System.out.println(selected.getMapX()+","+selected.getMapY());

    }

    @Override
    public void mouseEntered(MouseEvent e)
    {

    }

    @Override
    public void mouseExited(MouseEvent e)
    {

    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e)
    {
        if(e.getKeyCode() == KeyEvent.VK_A)
            player.setLeft(true);
        if(e.getKeyCode() == KeyEvent.VK_D)
            player.setRight(true);
        if(e.getKeyCode() == KeyEvent.VK_W)
            player.setUp(true);
        if(e.getKeyCode() == KeyEvent.VK_S)
            player.setDown(true);
        if(e.getKeyCode() == KeyEvent.VK_LEFT)
        {
            //System.out.println("hello?");
            camera.setX(camera.getX()-1);
            //System.out.println(camera.getX());
        }

        if(e.getKeyCode() == KeyEvent.VK_RIGHT)
            camera.setX(camera.getX()+1);

        if(e.getKeyCode() == KeyEvent.VK_UP)
        {
            camera.setY(camera.getY()-1);
        }
        if(e.getKeyCode() == KeyEvent.VK_DOWN)
            camera.setY(camera.getY()+1);
    }

    @Override
    public void keyReleased(KeyEvent e)
    {
        if(e.getKeyCode() == KeyEvent.VK_A)
            player.setLeft(false);
        if(e.getKeyCode() == KeyEvent.VK_D)
            player.setRight(false);
        if(e.getKeyCode() == KeyEvent.VK_W)
            player.setUp(false);
        if(e.getKeyCode() == KeyEvent.VK_S)
            player.setDown(false);
    }
}

