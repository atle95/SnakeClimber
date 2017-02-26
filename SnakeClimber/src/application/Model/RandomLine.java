package application.Model;

import java.util.Random;

import application.Main;
import javafx.scene.canvas.Canvas;
import javafx.scene.paint.Color;

public class RandomLine
{
  public double x, y, rad, angle, speed;
  public double range = Math.PI/12;
  public Color color;
  private double decayRate = 1.0/120;
  private Random r = new Random();
  
  public RandomLine(double x, double y, double rad, double angle, double speed, Color color)
  {
    this.x = x;
    this.y = y;
    this.rad = rad;
    this.angle = r.nextDouble()*Math.PI*2;
    this.speed = speed;
    this.color = color;
  }
  
  public void tick()
  {
//    double decay      = r.nextDouble()*decayRate;
    double deltaAngle = r.nextDouble()*range;
    double deltaX     = Math.cos(angle)*speed;
    double deltaY     = Math.sin(angle)*speed;
    
//    rad -= decay;
    this.angle += deltaAngle-range/2;
    this.x += deltaX;
    this.y += deltaY;
  }
  
  public void trackOnCanvas(Canvas canvas)
  {     
    canvas.setTranslateX(Main.width/2-this.x);
    canvas.setTranslateY(Main.height/2-this.y);
  }
  
}
