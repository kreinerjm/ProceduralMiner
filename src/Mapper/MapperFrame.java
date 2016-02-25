package Mapper;

import javax.swing.*;
import java.awt.*;

/**
 * Created by jacob on 2/18/16.
 */

public class MapperFrame extends JFrame
{
    Mapper panel;

    public MapperFrame()
    {
        panel = new Mapper();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(1024, 512));
        this.add(panel);
        pack();
        setVisible(true);
    }

    public static void main(String[] args)
    {
        new MapperFrame();
    }



}
