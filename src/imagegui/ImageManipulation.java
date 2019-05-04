/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package imagegui;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

/**
 *
 * @author lordstorm
 */
public class ImageManipulation 
{

    /**
     * @param args the command line arguments
     */
    public static int x = 1000;
    public static int y = 1000;
    String directory = "C:\\Users\\lordstorm\\Pictures\\forest2.png";
    String directory2 = "C:\\Users\\lordstorm\\Pictures\\forest1.jpg";
    public static BufferedImage image1 = new BufferedImage(x,y,BufferedImage.TYPE_INT_RGB);
    static Color[] color_list = {Color.BLACK, Color.RED, Color.GREEN, Color.YELLOW, Color.BLUE};
    public ImageManipulation()
    {
    }
    
    public static void save_to(String dir)
    {
        try 
        {
            ImageIO.write(ImageManipulation.image1, "jpg",new File(dir));
        } 
        catch (IOException ex) 
        {
            Logger.getLogger(ImageManipulation.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static void load_from(String dir)
    {
        try 
        {
            ImageManipulation.image1 = ImageIO.read(new File(dir));
        } 
        catch (IOException ex) 
        {
            Logger.getLogger(ImageManipulation.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static void add_random_circles(int n, int min_diam, int max_diam, int brush_width)
    {
        Graphics2D g2d = ImageManipulation.image1.createGraphics();
        Random r = new Random();
        for(int i = 0; i < n; i++)
        {
            g2d.setColor(color_list[r.nextInt(5)]);
            int d = min_diam + r.nextInt(max_diam-min_diam);
            int w = r.nextInt(ImageManipulation.image1.getWidth());
            int h = r.nextInt(ImageManipulation.image1.getHeight());
            g2d.fillOval(w, h, d, d);
            g2d.setColor(Color.white);
            g2d.fillOval(w + brush_width, h + brush_width, d - 2*brush_width, d - 2*brush_width);
        }
        g2d.dispose();
    }
    
    public static void add_random_rectangles(int n, int min_w, int max_w, int min_h, int max_h,  int brush_width)
    {
        Graphics2D g2d = ImageManipulation.image1.createGraphics();
        Random r = new Random();
        for(int i = 0; i < n; i++)
        {
            g2d.setColor(color_list[r.nextInt(5)]);
            int dw = min_w + r.nextInt(max_w-min_w);
            int dh = min_h + r.nextInt(max_h - min_h);
            int w = r.nextInt(ImageManipulation.image1.getWidth());
            int h = r.nextInt(ImageManipulation.image1.getHeight());
            g2d.fillRect(w, h, dw, dh);
            g2d.setColor(Color.white);
            g2d.fillRect(w + brush_width, h + brush_width, dw - 2*brush_width, dh - 2*brush_width);
        }
        g2d.dispose();
    }
    
    public static void noise(int foreach)
    {
        int n = 0;
        int m = 0;
        Random r = new Random();
        for(int i = 0; i < ImageManipulation.image1.getWidth(); i++)
        {
            for(int j = 0; j < ImageManipulation.image1.getHeight(); j++)
            {
                n = r.nextInt();
                m = color_list[r.nextInt(5)].getRGB();
                if(n % foreach == 0)
                {
                    ImageManipulation.image1.setRGB(i, j, m);
                }
            }
        }
    }
    
    public static void noise1(int x1, int y1, int dx, int dy, Color color)
    {
        int n = 0;
        int m = color.getRGB();
        Random r = new Random();
        for(int i = 0; i < ImageManipulation.image1.getWidth(); i++)
        {
            for(int j = 0; j < ImageManipulation.image1.getHeight(); j++)
            {
               
                //if((i>=x1)&&(i<x1+dx)&&(j>=y1)&&(j<y1+dy))
                if(i*100 > j*j)
                {
                    ImageManipulation.image1.setRGB(i, j, m);
                }
            }
        }
    }
}
