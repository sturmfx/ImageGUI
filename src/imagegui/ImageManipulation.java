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
import java.awt.image.ColorModel;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
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
    public static int id = 0;
    public static String file_name = "";
    public static int x = 1000;
    public static int y = 1000;
    static String directory = "C:\\Users\\lordstorm\\Pictures\\forest2.png";
    static String directory2 = "C:\\Users\\lordstorm\\Pictures\\forest1.jpg";
    public static BufferedImage image1 = new BufferedImage(x,y,BufferedImage.TYPE_INT_RGB);
    public static ArrayList<BufferedImage> images = new ArrayList<BufferedImage>();
    static Color[] color_list = {Color.BLACK, Color.RED, Color.GREEN, Color.YELLOW, Color.BLUE};
    public ImageManipulation()
    {
    }
    
    public static void save_to(String dir)
    {
        int k = 0;
        k = directory2.length();
        StringBuilder b = new StringBuilder(directory2);
        b.setCharAt(k-5,'t');
        directory2 = b.toString();
        
        try 
        {
            ImageIO.write(ImageManipulation.image1, "png",new File(directory2));
        } 
        catch (IOException ex) 
        {
            Logger.getLogger(ImageManipulation.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static void load_from()
    {
        try 
        {
            ImageManipulation.image1 = ImageIO.read(new File(directory));
            images.add(deepCopy(image1));
        } 
        catch (IOException ex) 
        {
            Logger.getLogger(ImageManipulation.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static void add_random_circles(int n, int min_diam, int max_diam, int brush_width)
    {
        images.add(deepCopy(images.get(id)));
        id++;
        Graphics2D g2d = images.get(id).createGraphics();
        Random r = new Random();
        for(int i = 0; i < n; i++)
        {
            g2d.setColor(color_list[r.nextInt(5)]);
            int d = min_diam + r.nextInt(max_diam-min_diam);
            int w = r.nextInt(images.get(id).getWidth());
            int h = r.nextInt(images.get(id).getHeight());
            g2d.fillOval(w, h, d, d);
            g2d.setColor(Color.white);
            g2d.fillOval(w + brush_width, h + brush_width, d - 2*brush_width, d - 2*brush_width);
        }
        g2d.dispose();
    }
    
    public static void add_random_rectangles(int n, int min_w, int max_w, int min_h, int max_h,  int brush_width)
    {
        images.add(deepCopy(images.get(id)));
        id++;
        Graphics2D g2d = images.get(id).createGraphics();
        Random r = new Random();
        for(int i = 0; i < n; i++)
        {
            g2d.setColor(color_list[r.nextInt(5)]);
            int dw = min_w + r.nextInt(max_w-min_w);
            int dh = min_h + r.nextInt(max_h - min_h);
            int w = r.nextInt(images.get(id).getWidth());
            int h = r.nextInt(images.get(id).getHeight());
            g2d.fillRect(w, h, dw, dh);
            g2d.setColor(Color.white);
            g2d.fillRect(w + brush_width, h + brush_width, dw - 2*brush_width, dh - 2*brush_width);
        }
        g2d.dispose();
    }
    
    public static void noise(int foreach)
    {
        images.add(deepCopy(images.get(id)));
        id++;
        int n = 0;
        int m = 0;
        Random r = new Random();
        for(int i = 0; i < images.get(id).getWidth(); i++)
        {
            for(int j = 0; j < images.get(id).getHeight(); j++)
            {
                n = r.nextInt();
                m = color_list[r.nextInt(5)].getRGB();
                if(n % foreach == 0)
                {
                    images.get(id).setRGB(i, j, m);
                }
            }
        }
    }
    
    public static void noise1(int x1, int y1, int dx, int dy, Color color)
    {
        id++;
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
    
    public static void load_previous()
    {
        if(id>1)
        {
        }
    }
    
    

    static BufferedImage deepCopy(BufferedImage bi)
    {
        ColorModel cm = bi.getColorModel();
        boolean isAlphaPremultiplied = cm.isAlphaPremultiplied();
        WritableRaster raster = bi.copyData(null);
        return new BufferedImage(cm, raster, isAlphaPremultiplied, null);
    }
}
