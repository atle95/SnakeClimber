package application;
	
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
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


public class Main extends Application {
  
  static int width  = 1280;
  static int height = 800;
  GraphicsContext gfx;
  ImageView imgv;
  RandomLine randomLine;
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
			randomLine = new RandomLine(width/2, height/2, 10, 0, 10);
			
			for(int i = 0; i < 30; i++)
			{
			  RandomLine rl = new RandomLine(canvas.getWidth()/2, canvas.getHeight()/2, 20, 0, 20);
			  randomLines.add(rl);
			}
			
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
	  
	  @Override
    public void handle(long now)
    {
	    Random r = new Random();
	    if( increment % 60 == 0)
	    {
	      randomLine = randomLines.get(r.nextInt(randomLines.size()));
	    }
	    increment++;
	    gfx.setFill(Color.BLACK);
	    gfx.setStroke(Color.BLACK);
	    for(RandomLine rl : randomLines)
	    {
	      rl.tick();
	      gfx.fillOval(rl.x, rl.y, rl.rad, rl.rad);
	    }
	    randomLine.trackOnCanvas();
	    gfx.fillOval(randomLine.x, randomLine.y, randomLine.rad, randomLine.rad);
    }
  }
	
	class RandomLine
	{
	  double x, y, rad, angle, speed = 1;
	  double range = Math.PI/6;
	  double decayRate = 1.0/120;
	  Random r = new Random();
	  
	  RandomLine(double x, double y, double rad, double angle, double speed)
	  {
	    this.x = x;
	    this.y = y;
	    this.rad = rad;
	    this.angle = r.nextDouble()*Math.PI*2;
	  }
	  
	  void tick()
	  {
	    double decay      = r.nextDouble()*decayRate;
	    double deltaAngle = r.nextDouble()*range;
	    double deltaX     = Math.cos(angle)*speed;
	    double deltaY     = Math.sin(angle)*speed;
	    
	    rad -= decay;
	    this.angle += deltaAngle-range/2;
	    this.x += deltaX;
	    this.y += deltaY;
	  }
	  
	  void trackOnCanvas()
	  {	    
	    canvas.setTranslateX(width/2-this.x);
	    canvas.setTranslateY(height/2-this.y);
	  }
	  
	}
	
	public static void main(String[] args)
	{
	  
		launch(args);
	}
}
