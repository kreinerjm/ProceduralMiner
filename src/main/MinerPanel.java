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

/**
 * Created by jacob on 2/18/16.
 */
public class MinerPanel extends JPanel implements MouseListener, KeyListener
{

    Player player = new Player(50,50);
    BufferedImage buffer;
    Map map;

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
        b2d.setColor(Color.BLACK);
        for(int i = 0; i < map.blocks.length; i++)
        {
           // System.out.print(i+",");
            for(int j = 0; j < map.blocks[i].length; j++)
            {
               // System.out.println(j);
                if(!map.blocks[i][j].empty)
                {
                    int scale = map.height/2;
                   // System.out.println("Not empty : "+i+","+j);
                    if (!map.hasBlockAbove(map.blocks[i][j]))
                    {
                    //    System.out.println("Drawing line above "+i+","+j);
                        b2d.drawLine(i * scale, j * scale, (i + 1) * scale, j * scale);
                    }
                    if (!map.hasBlockBelow(map.blocks[i][j]))
                    {
                   //     System.out.println("Drawing line below "+i+","+j);
                        b2d.drawLine(i * scale, (j + 1) * scale, (i + 1) * scale, (j + 1) * scale);
                    }
                    if (!map.hasBlockLeft(map.blocks[i][j]))
                    {
                    //    System.out.println("Drawing line to the left of "+i+","+j);
                        b2d.drawLine(i * scale, j * scale, i * scale, (j + 1) * scale);
                    }
                    if (!map.hasBlockRight(map.blocks[i][j]))
                    {
                    //    System.out.println("Drawing line to the right of "+i+","+j);
                        b2d.drawLine((i + 1) * scale, j * scale, (i + 1) * scale, (j + 1) * scale);
                    }
                }
                else
                {
                   // System.out.println(i+","+j+" is empty");
                }
            }
            b2d.drawOval(player.getX(),player.getY(),20,20);
        }
        g2d.drawImage(buffer,0,0,buffer.getWidth(),buffer.getHeight(), null);

    }

    public void tick()
    {
        player.tick();
        repaint();
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
        Block selected = map.blocks[e.getX()/(map.height/2)][e.getY()/(map.height/2)];
        int distance1 = Math.abs(selected.getX()+(map.height/4)-player.getX()+10);
        int distance2 = Math.abs(selected.getY()+(map.height/4)-player.getY()+10);
        int distance = (int)(Math.sqrt(distance1*distance1 + distance2*distance2)+.5);
        if(distance<=player.getMineDistance())
            selected.empty = true;
        System.out.println(distance);

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

