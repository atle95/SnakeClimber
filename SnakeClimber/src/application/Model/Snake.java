package application.Model;

import java.awt.Image;
import java.util.ArrayList;

public class Snake extends Drawable
{

  int snakeVertebrae;
  int snakeWidth;
  ArrayList<ArrayList<SnakePart>> spine = new ArrayList<>();
  
  public Snake(Image image)
  {
    super(image);
    for(int i = 0; i < snakeVertebrae; i++)
    {
//      ArrayList<SnakePart> slice = new ArrayList<>();
      for(int j = 0; j < snakeWidth; i++)
      {
//        SnakePart part = new SnakePart(null);
      }
//      spine.add(slice);
    }
    // TODO Auto-generated constructor stub
  }
  
}
