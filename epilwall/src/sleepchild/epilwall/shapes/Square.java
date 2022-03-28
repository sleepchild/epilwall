package sleepchild.epilwall.shapes;

import sleepchild.epilwall.Utils;
import android.graphics.*;

public class Square extends Shape
{
    int mWidth;
    int mHeight;

    boolean upDown = true;

    // whether to change the bounds left value when updating.
    boolean mX=false;//
    // if mX==true;
    // whether to move left or right;; true==left;; false==right
    boolean mxLeft=false;

    //Paint bz; //debug

    public Square(int w, int h){
        super(w, h);
        
        mWidth = Utils.randx(20, 90);
        mHeight = mWidth;
        
        mX = Utils.randBool();
        if(mX){
            mxLeft = Utils.randBool();
        }

        int dice = Utils.randx(0,50);
        if(dice<25){
            upDown=false;
            initDownUp(w,h);
        }else{
            //
            bounds.bottom = bounds.top + mHeight;
            bounds.right = bounds.left + mWidth;
            //
            float l = Utils.randx(0, w-10);
            float t = 2 - mHeight;
            bounds.offsetTo(l, t);
            //
        }


        //debug
        /*
         bz = new Paint();
         bz.setColor(Color.GRAY);
         bz.setStyle(Paint.Style.STROKE);
         bz.setTextSize(50);
         //*/
        //  
    }

    void initDownUp(int w, int h){
        //
        bounds.top = h - 1;
        bounds.bottom = bounds.top + mHeight;
        bounds.right = bounds.left + mWidth;
        //
        float l = Utils.randx(0, w-10);
        float t = bounds.top;//
        bounds.offsetTo(l, t);
        //
    }

    @Override
    public void update(){

        //*
        if(mX){// update left/right
            if(mxLeft){
                bounds.left -= velocity;
            }else{
                bounds.left += velocity;
            }
            
        }
        //*/
        
        bounds.right = bounds.left + mWidth;

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
    }

    private void updateDownUp(){

        float l=bounds.left;
        float t = bounds.top-velocity;
        bounds.offsetTo(l, t);
        //
    }


    @Override
    public void draw(Canvas c)
    {
//        bounds.right = bounds.left + mWidth;
        //c.drawCircle(cx, cy, radius, paint);
        c.drawRect(bounds, paint);
        // debug
        //c.drawRect(bounds, bz);
        //c.drawText(""+velocity,bounds.left, bounds.top, bz);
    }

    @Override
    public RectF getBounds(){
        return bounds;
    }





}
