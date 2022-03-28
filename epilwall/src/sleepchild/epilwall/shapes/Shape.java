package sleepchild.epilwall.shapes;
import android.graphics.*;
import sleepchild.epilwall.*;

public abstract class Shape{
    RectF bounds = new RectF(0,0,20,20);
    Paint paint;
    int color;
    int velocity=5; //falling speed
    
    int width;
    int height;
    
    public Shape(int w, int h){
        width = w;
        height = h;
        velocity = Utils.randx(1,30);
        //color = Color.parseColor("#fff600");
        color = Utils.randColorARGB();
        paint = new Paint();
        paint.setColor(color);
    }
    
    public void updateSize(int w, int h){
        width = w;
        height = h;
    }
    
    public abstract void draw(Canvas c);
    public abstract void update();
    public abstract RectF getBounds();
    public boolean canDestroy(){
        return false;
    }
    //public void onDestroy(){}
}
