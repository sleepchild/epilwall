package sleepchild.epilwall.shapes;
import android.graphics.*;
import sleepchild.epilwall.*;

public class Circle extends Shape
{
    float cx=0;
    float cy=0;
    int radius;
    
    // whether this shape is moving from up-to-down or from down-to-up
    // true == up-to-down ;;  false == down-to-up
    boolean upDown = true;
    
    // whether to change the bounds left value when updating.
    boolean mX=false;//
    // if mX==true;
    // whether to move left or right;; true==left;; false==right
    boolean mxLeft=false;
    
    public Circle(int w, int h){
        super(w, h);
        radius = Utils.randx(20, 90);
        int dice = Utils.randx(0,50);
        mX = Utils.randBool();
        if(mX){
            mxLeft = Utils.randBool();
        }
        
        if(dice<25){
            upDown=false;
            initDownUp(w,h);
        }else{
            //
            int r2 = radius*2;
            bounds.bottom = bounds.top+r2;
            bounds.right = bounds.left+r2;
            //
            float l = Utils.randx(0, w-radius);
            float t = 2 - (radius+radius);
            bounds.offsetTo(l, t);
            //
            cx = l + radius;
            cy = t + radius;
        }
         
    }
    
    void initDownUp(int w, int h){
        //
        int r2 = radius*2;
        bounds.top = h - 1;
        bounds.bottom = bounds.top+r2;
        bounds.right = bounds.left+r2;
        //
        float l = Utils.randx(0, w-radius);
        float t = bounds.top;//
        bounds.offsetTo(l, t);
        //
        cx = l + radius;
        cy = t + radius;
    }

    @Override
    public void update(){
        
        if(mX){// update left/right
            if(mxLeft){
                bounds.left -= 10;
            }else{
                bounds.left += 10;
            }
        }
        
        bounds.right = bounds.left + (radius + radius);
        
        if(upDown){
            updateUpDown();
        }else{
            updateDownUp();
        }
        
    }
    
    private void updateUpDown(){
        float l=bounds.left;
        float t = bounds.top+velocity;
        bounds.offsetTo(l, t);
        //
        cx = l + radius;
        cy = t + radius;
    }
    
    private void updateDownUp(){
        
        float l=bounds.left;
        float t = bounds.top-velocity;
        bounds.offsetTo(l, t);
        //
        cx = l + radius;
        cy = t + radius;
    }
    

    @Override
    public void draw(Canvas c)
    {
        c.drawCircle(cx, cy, radius, paint);
    }

    @Override
    public RectF getBounds(){
        return bounds;
    }

    
    
    
    
}
