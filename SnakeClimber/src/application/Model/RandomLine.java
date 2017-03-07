package application.Model;

import java.util.ArrayList;
import java.util.Random;

import application.Main;
import javafx.scene.canvas.Canvas;
import javafx.scene.paint.Color;

public class RandomLine
{
  public double x, y, rad, angle, speed, lifespan;
  public Color color;
  public ArrayList <double[]> paths;
  public ArrayList <Color> colorList;
  public int branchRate;

  private double range;
  private double colorRange;
  private double decayRate;
  private double prune = 0.05;
  private Random r = new Random();
  private ArrayList <RandomLine> branches;
  
  public RandomLine(double x, double y, double rad, double angle, double speed, Color color, double lifespan)
  {
    this.setX(x).setY(y).setRad(rad).setAngle(angle).setSpeed(speed).setColor(color).setLifespan(lifespan);
    this.decayRate = rad/lifespan;
    this.branches = new ArrayList<>();
    this.paths = new ArrayList<>();
    this.branchRate = 60+r.nextInt(60);
    this.colorList = makePallet(20, r.nextDouble()*2*Math.PI);
    this.colorRange = r.nextDouble()*Math.PI;
  }
  
  public RandomLine(double x, double y)
  {
    this.setX(x).setY(y).setRad(1).setAngle(-Math.PI/2)
        .setLifespan(100).setSpeed(1).setLifespan(100).setBranchRate(120)
        .makePallet(20, r.nextDouble()*2*Math.PI);
    this.decayRate = rad/lifespan;
    this.branches = new ArrayList<>();
    this.paths = new ArrayList<>();
    this.branchRate = 120;
    this.colorList = makePallet(20, r.nextDouble()*2*Math.PI);
    this.colorRange = Math.PI*2/3;
    
  }
  
  public void tick()
  {
//    double decay      = r.nextDouble()*decayRate;
    double deltaAngle = r.nextDouble()*range;
    double deltaX     = Math.cos(angle)*speed;
    double deltaY     = Math.sin(angle)*speed;
    
    rad = rad < prune ? 0 : rad-rad*decayRate;
    this.angle += deltaAngle-range/2;
    this.x += deltaX;
    this.y += deltaY;
    paths.add(new double [] {x, y, rad, angle});
  }
  
  public RandomLine setX(double x)
  {
    this.x = x;
    return this;
  }
  
  public RandomLine setY(double y)
  {
    this.y = y;
    return this;
  }
  
  public RandomLine setPrune(double prune)
  { 
    this.prune = prune;
    return this;
  }
  
  public RandomLine setBranchRate(int branchRate)
  {
    this.branchRate = branchRate;
    return this;
  }
  
  public RandomLine setColor(Color c)
  {
    this.color = c;
    return this;
  }
  
  public RandomLine setRange(double range)
  {
    this.range = range;
    return this;
  }
  
  public RandomLine setRad(double rad)
  {
    this.rad = rad;
    return this;
  }
  
  public RandomLine setLifespan(double lifespan)
  {
    this.lifespan = lifespan;
    return this;
  }
  
  public RandomLine setSpeed(double speed) 
  {
    this.speed = speed;
    return this;
  }
  
  public RandomLine setAngle(double angle)
  {
    this.angle = angle;
    return this;
  }
  
  public RandomLine setColorList(ArrayList<Color> colorList )
  {
    this.colorList = colorList;
    return this;
  }
  
  public void grow()
  {
    for(int i = 0; i < lifespan; i++)
    {
        this.tick();
    }
  }
  
  public RandomLine branch()
  {
    
    RandomLine branch = new RandomLine(this.x, this.y);
    
    branch .setBranchRate(this.branchRate).setRange(this.range).setColor(this.color).
    setRad(this.rad).setLifespan(this.lifespan).setPrune(this.prune).
    setSpeed(this.speed).setAngle(this.angle).setColorList(this.colorList);
    
    double branchSplit = r.nextDouble();
    this.angle   += branchSplit*range;
    branch.angle -= branchSplit*range;
    branches.add(branch);
    return branch;
  }
  
  public void cycleColors()
  {
    Color tempc = colorList.get(0);
    colorList.remove(tempc);
    colorList.add(tempc);
  }
  
  ArrayList<Color> makePallet(int numcolors, double angle)
  {
    
    ArrayList<Color> tempcolorList = new ArrayList<>();
    double redOffset   = angle;
    double greenOffset = Math.PI*2/3+redOffset;
    double blueOffset  = -Math.PI*2/3+redOffset;
    
    for(int i = 0; i <= numcolors; i++)
    {
      double progress = ((double) i) / numcolors;
      int r = (int) (255 * (( 1 + Math.sin(Math.PI*progress + redOffset  )) / 2));
      int g = (int) (255 * (( 1 + Math.sin(Math.PI*progress + greenOffset)) / 2));
      int b = (int) (255 * (( 1 + Math.sin(Math.PI*progress + blueOffset )) / 2));
      tempcolorList.add(Color.rgb(r, g, b));
    }
    return tempcolorList;
  }

  public void trackOnCanvas(Canvas canvas)
  {     
    canvas.setTranslateX(Main.width/2-this.x);
    canvas.setTranslateY(Main.height/2-this.y);
  }
  
}
