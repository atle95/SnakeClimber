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
  public static ArrayList<RandomLine> randomLines = new ArrayList<>();
  public static ArrayList<RandomLine> addingList = new ArrayList<>();
  ArrayList<RandomLine> removalList = new ArrayList<>();
  ArrayList<Color> colorList = new ArrayList<>();
  Canvas canvas;
  Random r = new Random();
  public double globalPrune = 0.75;
  int numcolors = 100;
  
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
			
			
//			colorList = makePallet(numcolors);

			root.getChildren().add(canvas);
//			root.getChildren().add(imgv);
			gfx = canvas.getGraphicsContext2D();
			gfx.setFill(Color.BLACK);
			gfx.fillRect(0, 0, 3*width, 3*height);
			int numTrees = 15;
			int treeLength = 240;
			double centerx = canvas.getWidth()/2-width/2;
			double centery = canvas.getHeight()/2;
			for(int i = 0; i < numTrees; i++)
			{
			  double progress = ((double) i) / numTrees;
			  double xoffset = progress*width;
			  int lifespan = (int) (progress*treeLength);
			  
			  RandomLine redTree   = new RandomLine(centerx+xoffset, centery-200);
			  RandomLine greenTree = new RandomLine(centerx+xoffset, centery    );
			  RandomLine blueTree  = new RandomLine(centerx+xoffset, centery+200);
			  
			  redTree  .setBranchRate(120+i).setRange(Math.PI/3 ).setLifespan(lifespan).setRad(i).setColor(Color.RED);
			  greenTree.setBranchRate(60+i ).setRange(Math.PI/6 ).setLifespan(lifespan).setRad(i).setColor(Color.GREEN);
			  blueTree .setBranchRate(30+i ).setRange(Math.PI/12).setLifespan(lifespan).setRad(i).setColor(Color.DODGERBLUE);
			  
			  randomLines.addAll(Arrays.asList(redTree, greenTree, blueTree ));
			}
			randomLine = new RandomLine(canvas.getWidth()/2, canvas.getHeight()/2);
//			randomLine.grow();
			trackingLine = randomLine;
			
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
//	  int branchRate = 240;
	  
	  @Override
    public void handle(long now)
    {
//	    if( randomLines.size() > 0)
//	    {
//	      if( increment % 30 == 0)
//	      {
//	        trackingLine = randomLines.get((randomLines.indexOf(trackingLine)+1)%randomLines.size());
//	      }
//	    } else
//	    {
//	      trackingLine = randomLine;
//	    }
//	    cycleColors(colorList);
	    
	    increment++;
	    for(RandomLine rl : randomLines)
	    {
	      if(increment % rl.branchRate == 0)
	      {
	        addingList.add(rl.branch());
	      }
	      
	      if (rl.rad < globalPrune )
	      {
	        removalList.add(rl);
	      }
	      
	      rl.tick();
//	      for(double[] path : randomLine.paths)
//	      {
//	        gfx.setFill(Color.PURPLE);
//	        gfx.setStroke(Color.PURPLE);
//	        int samples = 5;
//	        for(int i = -samples; i <= samples; i++)
//	        {
////	          int col = 255 - Math.abs(i)*255/samples;
//	          int col = (int) (128*(1+Math.cos(i*Math.PI/samples)));
//	          int red   = (int) ((rl.color.getRed()*255)+col)/2;
//	          int green = (int) ((rl.color.getGreen()*255)+col)/2;
//	          int blue  = (int) ((rl.color.getBlue()*255)+col)/2;
//	          Color c = Color.rgb(red, green, blue);
//	          gfx.setFill(c);
//	          gfx.setStroke(c);
//	          gfx.fillOval(
//	              path[0]+Math.cos(path[3]+Math.PI/2)*path[2]*i,
//	              path[1]+Math.sin(path[3]+Math.PI/2)*path[2]*i,
//	              path[3],
//	              path[3]);
//	          
//	        }
//	        
//	      }
	      int samples = 5;
        for(int i = -samples; i <= samples; i++)
        {
//          int col = 255 - Math.abs(i)*255/samples;
          int col = (int) (128*(1+Math.cos(i*Math.PI/samples)));
//          int col = (int) (255*Math.sqrt(samples^2-i^2)/samples);
          
          
//          rl.cycleColors();
          rl.cycleColors();
//          rl.cycleColors();
          Color c = rl.colorList.get(0);
          
          int red   = (int) ((c.getRed()  *255)+col)/2;
          int green = (int) ((c.getGreen()*255)+col)/2;
          int blue  = (int) ((c.getBlue() *255)+col)/2;
          Color shadec = Color.rgb(red, green, blue, 1);
          
          gfx.setFill(shadec);
          gfx.setStroke(shadec);

          gfx.fillOval(
              rl.x+Math.cos(rl.angle+Math.PI/2)*rl.rad*i/samples,
              rl.y+Math.sin(rl.angle+Math.PI/2)*rl.rad*i/samples,
              rl.rad/samples*2,
              rl.rad/samples*2);
          
        }
        
//	    }
	    }
	    randomLines.addAll(addingList);
	    randomLines.removeAll(removalList);
	    addingList = new ArrayList<RandomLine>();
	    removalList = new ArrayList<RandomLine>();
	    trackingLine.trackOnCanvas(canvas);
    }
  }
	
	public static void main(String[] args)
	{
	  
		launch(args);
	}
}
