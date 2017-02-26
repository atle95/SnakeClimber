package application.Model;

import javafx.scene.image.Image;

import java.util.ArrayList;

/**
 * Created by benma on 2/25/2017.
 *
 * Used for any baseline drawable
 */
public class Drawable extends GameObject{
    public double x, y, scale, angle;

    /**
     * the speed at which the images in the images arraylist cycle
     */
    public double ticksPerFrame;

    private int imageIndex, ticks;
    public ArrayList<Image> images;

    public Drawable(ArrayList<Image> images){
        this(images, 1);
    }

    public Drawable(ArrayList<Image> images, double scale){
        this(images, scale, 5);
    }

    public Drawable(ArrayList<Image> images, double scale, double ticksPerFrame){
        this.images = images;
        this.scale = scale;
        this.ticksPerFrame = ticksPerFrame;

        imageIndex = ticks = 0;
        x = y = angle = 0;
    }

    public void tick(){
        ticks++;
        if (ticks > ticksPerFrame){
            ticks = 0;
            imageIndex = (imageIndex+1)%images.size();
        }

    }

    public void render(){
        
    }
}
