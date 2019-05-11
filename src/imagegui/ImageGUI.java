/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package imagegui;

import java.io.File;
import javafx.application.Application;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javax.imageio.ImageIO;

/**
 *
 * @author lordstorm
 */
public class ImageGUI extends Application 
{
    int number_of_circle = 3;
    int circles_min_diam = 50;
    int circles_max_diam = 200;
    int circles_brush = 10;
    
    int number_of_rect = 3;
    int rectangle_min_x = 100;
    int rectangle_max_x = 200;
    int rectangle_min_y = 100;
    int rectangle_max_y = 200;
    int rectangle_brush = 10;
    
    String file_name = "";
    final FileChooser fileChooser = new FileChooser();
    ImageManipulation im = new ImageManipulation();
    @Override
    public void start(Stage primaryStage) 
    {
        //ImageManipulation.load_from();
        Canvas can = new Canvas(ImageManipulation.x, ImageManipulation.y);
        
        Button btn1 = new Button();
        btn1.setText("NOISE");
        btn1.setOnAction(new EventHandler<ActionEvent>() 
        {
            
            @Override
            public void handle(ActionEvent event) 
            {
                GraphicsContext gc = can.getGraphicsContext2D();
                ImageManipulation.noise(4);
                Image image = SwingFXUtils.toFXImage(ImageManipulation.images.get(ImageManipulation.id), null);
                gc.drawImage(image, 0, 0);
            }
        });
        
        Button btn2 = new Button();
        btn2.setText("RESET");
        btn2.setOnAction(new EventHandler<ActionEvent>() 
        {
            
            @Override
            public void handle(ActionEvent event) 
            {
                GraphicsContext gc = can.getGraphicsContext2D();
                
                Image image = SwingFXUtils.toFXImage(ImageManipulation.images.get(0), null);
                gc.drawImage(image, 0, 0);
            }
        });
        
        Button btn3 = new Button();
        btn3.setText("RECTANGLES");
        btn3.setOnAction(new EventHandler<ActionEvent>() 
        {
            
            @Override
            public void handle(ActionEvent event) 
            {
                GraphicsContext gc = can.getGraphicsContext2D();
                
               
                ImageManipulation.add_random_rectangles(number_of_rect, rectangle_min_x, rectangle_max_x, rectangle_min_y, rectangle_max_y, rectangle_brush);
                Image image = SwingFXUtils.toFXImage(ImageManipulation.images.get(ImageManipulation.id), null);
                gc.drawImage(image, 0, 0);
            }
        });
        
        Button btn4 = new Button();
        btn4.setText("CIRCLES");
        btn4.setOnAction(new EventHandler<ActionEvent>() 
        {
            
            @Override
            public void handle(ActionEvent event) 
            {
                GraphicsContext gc = can.getGraphicsContext2D();
                
               
                ImageManipulation.add_random_circles(number_of_circle, circles_min_diam, circles_max_diam, circles_brush);
                Image image = SwingFXUtils.toFXImage(ImageManipulation.images.get(ImageManipulation.id), null);
                gc.drawImage(image, 0, 0);
            }
        });
        Button choose_file = new Button();
        choose_file.setText("LOAD IMAGE");
        choose_file.setOnAction(
            new EventHandler<ActionEvent>() {
                @Override
                public void handle(final ActionEvent e) {
                    File file = fileChooser.showOpenDialog(primaryStage);
                    if (file != null) 
                    {
                        GraphicsContext gc = can.getGraphicsContext2D();
                        ImageManipulation.directory = file.getAbsolutePath();
                        ImageManipulation.load_from();
                        Image image = SwingFXUtils.toFXImage(ImageManipulation.image1, null);
                        gc.drawImage(image, 0, 0);
                    }
                }
            });
        
        Button choose_file_save = new Button();
        choose_file_save.setText("SAVE IMAGE");
        choose_file_save.setOnAction(
            new EventHandler<ActionEvent>() {
                @Override
                public void handle(final ActionEvent e) 
                {
                    File file = fileChooser.showOpenDialog(primaryStage);
                    if (file != null) 
                    {
                        WritableImage wim = new WritableImage(ImageManipulation.x, ImageManipulation.y);
                        can.snapshot(null, wim);
                        try 
                        {
                            ImageIO.write(SwingFXUtils.fromFXImage(wim, null), "png", file);
                        } 
                        catch(Exception s)
                        {
                        }
                    }
                }
            });
        
        Button undo = new Button();
        undo.setText("UNDO");
        undo.setOnAction(new EventHandler<ActionEvent>() 
        {
            
            @Override
            public void handle(ActionEvent event) 
            {
                GraphicsContext gc = can.getGraphicsContext2D();
                if(ImageManipulation.id>0)
                {
                    ImageManipulation.id--;
                    Image image = SwingFXUtils.toFXImage(ImageManipulation.images.get(ImageManipulation.id), null);
                    gc.drawImage(image, 0, 0);
                }
            }
        });
        
        Button redo = new Button();
        redo.setText("REDO");
        redo.setOnAction(new EventHandler<ActionEvent>() 
        {
            
            @Override
            public void handle(ActionEvent event) 
            {
                GraphicsContext gc = can.getGraphicsContext2D();
                if(ImageManipulation.id< ImageManipulation.images.size()-1)
                {
                    ImageManipulation.id++;
                    Image image = SwingFXUtils.toFXImage(ImageManipulation.images.get(ImageManipulation.id), null);
                    gc.drawImage(image, 0, 0);
                }
            }
        });
        
        HBox root = new HBox();
        VBox buttons = new VBox();
        root.getChildren().add(can);
        root.getChildren().add(buttons);
        buttons.getChildren().add(choose_file);
        buttons.getChildren().add(choose_file_save);
        buttons.getChildren().add(btn2);
        buttons.getChildren().add(btn1);
        buttons.getChildren().add(btn3);
        buttons.getChildren().add(btn4);
        buttons.getChildren().add(undo);
        buttons.getChildren().add(redo);
        
        Scene scene = new Scene(root, 1100, 1000);
        
        primaryStage.setTitle("ImageManipulation");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
