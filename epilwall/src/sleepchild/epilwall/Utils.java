package sleepchild.epilwall;

import java.util.Random;
import android.graphics.Color;

public class Utils
{
    // return a random number between min and max; excluding min
    public static int randx(int min, int max){
        int ix = new Random().nextInt(max);
        if(ix>max || ix<=min){
            while(ix>max || ix<=min){
                ix = new Random().nextInt(max);
            }
        }
        return ix;
    }
    
    // return a random number less than max
    public static int randm(int max){
        return new Random().nextInt(max);
    }
    
    // return a random argb color int 
    public static int randColorARGB(){
        return Color.argb(
            new Random().nextInt(255), //a 
            new Random().nextInt(255), //r
            new Random().nextInt(255), //g
            new Random().nextInt(255)  //b
        );
    }
    
    // return a random argb color int with the specified alpha
    public static int randColorARGB_A(int alpha){
        if(alpha>255 || alpha < 0){
            alpha = 255;
        }
        return Color.argb(
            new Random().nextInt(alpha),
            new Random().nextInt(255),
            new Random().nextInt(255),
            new Random().nextInt(255)
        );
    }
    
    // return a random rgb color int 
    public static int randColorRGB(){
        return Color.rgb(
            new Random().nextInt(255), //red
            new Random().nextInt(255), //green
            new Random().nextInt(255)  //blue
        );
    }
    
    public static boolean randBool(){
        return new Random().nextBoolean();
    }
    
    
}
