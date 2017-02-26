package application.View;
	
import java.awt.image.BufferedImage;
import java.io.File;

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
  
  static int width  = 800;
  static int height = 600;
  GraphicsContext gfx;
  ImageView imgv;
  
	@Override
	public void start(Stage primaryStage) {
		try {
		  
      File face = new File("resources/face.png");
      BufferedImage bimg = ImageIO.read(face);
      WritableImage wimg = SwingFXUtils.toFXImage(bimg, null);
      imgv = new ImageView(wimg);

      Canvas canvas = new Canvas(width, height);
			BorderPane root = new BorderPane();
			Scene scene = new Scene(root, width, height);

			root.getChildren().add(canvas);
			root.getChildren().add(imgv);
			gfx = canvas.getGraphicsContext2D();
			gfx.setFill(Color.BLACK);
			
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
	    increment++;
	    gfx.setFill(Color.BLACK);
      gfx.fillRect(width-increment, height-increment, width, height);
      imgv.setTranslateX(increment);
      imgv.setTranslateY(increment);
    }
  }
	
	public static void main(String[] args) {
		launch(args);
	}
}
