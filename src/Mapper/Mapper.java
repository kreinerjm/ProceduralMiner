package Mapper;

import main.Maps.Map;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Created by jacob on 2/24/16.
 */
public class Mapper extends JPanel
{
    BufferedImage buffer;
    Map map;
    public Mapper()
    {
        map = new Map();
        buffer = new BufferedImage(map.width,map.height,BufferedImage.TYPE_INT_ARGB);
        setPreferredSize(new Dimension(1024, 512));
        setOpaque(true);
    }

    public void paint(Graphics g)
    {
        System.out.println("painting?");
        Graphics2D g2d = (Graphics2D) g;
        Graphics2D b2d = buffer.createGraphics();
        //b2d.setColor(new Color(0x2D287B));
       // b2d.fillRect(0, 0, buffer.getWidth(), buffer.getHeight());
        for(int i = 0; i < 1024; i++)
        {
            for(int j = 0; j < 512; j++)
            {
                if(!map.blocks[i][j].isEmpty())
                {
                    b2d.setColor(map.blocks[i][j].getC());
                    b2d.drawRect(i, j, 1, 1);
                }
                else
                {
                    b2d.setColor(Color.WHITE);
                    b2d.drawRect(i, j, 1, 1);
                }
            }
        }
        g2d.drawImage(buffer,0,0,buffer.getWidth(),buffer.getHeight(),null);
    }



//    int r =
//    int g =
//    int b =
//    int col = (r << 16) | (g << 8) | b;
//    img.setRGB(x, y, col);

}
