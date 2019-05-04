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
import javafx.stage.Stage;
import javax.imageio.ImageIO;

/**
 *
 * @author lordstorm
 */
public class ImageGUI extends Application 
{
    ImageManipulation im = new ImageManipulation();
    @Override
    public void start(Stage primaryStage) 
    {
        ImageManipulation.load_from(im.directory2);
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
                Image image = SwingFXUtils.toFXImage(ImageManipulation.image1, null);
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
                ImageManipulation.load_from(im.directory2);
                Image image = SwingFXUtils.toFXImage(ImageManipulation.image1, null);
                gc.drawImage(image, 0, 0);
            }
        });
        
        Button btn3 = new Button();
        btn3.setText("SAVE");
        btn3.setOnAction(new EventHandler<ActionEvent>() 
        {
            
            @Override
            public void handle(ActionEvent event) 
            {
                WritableImage wim = new WritableImage(ImageManipulation.x, ImageManipulation.y);
                can.snapshot(null, wim);
                try 
                {
                    ImageIO.write(SwingFXUtils.fromFXImage(wim, null), "png", new File(im.directory));
                } 
                catch(Exception s)
                {
                }
            }
        });
        
        HBox root = new HBox();
        VBox buttons = new VBox();
        root.getChildren().add(can);
        root.getChildren().add(buttons);
        buttons.getChildren().add(btn2);
        buttons.getChildren().add(btn1);
        buttons.getChildren().add(btn3);
        
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
