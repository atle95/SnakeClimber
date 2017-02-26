package application;
	
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

import javax.imageio.ImageIO;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import application.Model.RandomLine;

public class Main extends Application {
  
  public static int width  = 1280;
  public static int height = 800;
  GraphicsContext gfx;
  ImageView imgv;
  RandomLine randomLine;
  RandomLine trackingLine;
  ArrayList<RandomLine> randomLines = new ArrayList<>();
  Canvas canvas;
  
	@Override
	public void start(Stage primaryStage) {
		try {
		  
      File face = new File("resources/face.png");
      BufferedImage bimg = ImageIO.read(face);
      WritableImage wimg = SwingFXUtils.toFXImage(bimg, null);
      imgv = new ImageView(wimg);

      canvas = new Canvas(3*width, 3*height);
			BorderPane root = new BorderPane();
			Scene scene = new Scene(root, width, height);

			root.getChildren().add(canvas);
//			root.getChildren().add(imgv);
			gfx = canvas.getGraphicsContext2D();
			gfx.setFill(Color.BLACK);
			for(int i = 0; i < 30; i++)
			{
			  RandomLine rl = new RandomLine(canvas.getWidth()/2, canvas.getHeight()/2, 1, 0, 1, Color.BLACK);
			  randomLines.add(rl);
			}
			trackingLine = new RandomLine(canvas.getWidth()/2, canvas.getHeight()/2, 0, 0, 0, Color.BLACK);
			
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			MainGameLoop gameLoop = new MainGameLoop();
			gameLoop.start();
			primaryStage.show();
		} catch(Exception e) {
		  
			e.printStackTrace();
		}
	}
	
	class MainGameLoop extends AnimationTimer
  {
	  int increment = 0;
	  double spread = 1;
	  
	  @Override
    public void handle(long now)
    {
//	    if( increment % 60 == 0)
//	    {
//	      trackingLine = randomLines.get((randomLines.indexOf(trackingLine)+1)%randomLines.size());
//	    }
	    increment++;
	    for(RandomLine rl : randomLines)
	    {
	      rl.tick();
	      gfx.setFill(rl.color);
	      gfx.setStroke(rl.color);
	      for(int i = -10; i < 10; i++)
	      {
	        int col = 255 - Math.abs(i)*255/10;
	        gfx.setFill(Color.rgb(col, col, col) );
	        gfx.fillOval(rl.x+Math.cos(rl.angle+Math.PI/2)*spread*i, rl.y+Math.sin(rl.angle+Math.PI/2)*spread*i, rl.rad, rl.rad);
	        
	      }
	    }
	    trackingLine.trackOnCanvas(canvas);
    }
  }
	
	
	
	public static void main(String[] args)
	{
	  
		launch(args);
	}
}
