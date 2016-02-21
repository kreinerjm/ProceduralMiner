package main;

import javax.swing.*;
import java.awt.*;

/**
 * Created by jacob on 2/18/16.
 */

public class MinerFrame extends JFrame
{
    MinerPanel panel;
    static int ticktime = 15;
    boolean quit = false;

    public MinerFrame()
    {
        panel = new MinerPanel();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.add(panel);
        setPreferredSize(new Dimension(1024+16, 512+32));
        pack();
        setVisible(true);

        while(true)
        {
            long start = System.currentTimeMillis();
            tick();
            long took = System.currentTimeMillis()-start;
            if(ticktime-took>1)
            {
                try
                {
                    Thread.sleep(ticktime-took);
                }catch(Exception e){}
            }
            if(quit)
                break;
        }
        dispose();
    }

    public static void main(String[] args)
    {
        new MinerFrame();
    }

    public void tick()
    {
        panel.tick();
    }


}
