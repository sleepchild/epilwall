package sleepchild.epilwall;
import android.view.*;
import android.graphics.*;

public class AnimationThread extends Thread
{
    SurfaceHolder surface;
    Scene scene;
    SPrefs prefs;
    
    boolean running = true;
    boolean paused = true;
    
    Object lock = new Object();
    
    int fps = 30;
    int drawtime = 1000 / fps;
    
    public AnimationThread(SurfaceHolder surface, Scene scene, SPrefs prefs){
        this.surface = surface;
        this.scene = scene;
        this.prefs = prefs;
    }
    
    /*

    @Override
    public void run()
    {
        while(running){
            
            waitOnPause();
            
            if(!running){
                return;
            }
            
            Canvas c = surface.lockCanvas();
            
            if(c!=null){
               
                scene.update();
                scene.draw(c);
                
                surface.unlockCanvasAndPost(c);
            }
            
            try{
                Thread.sleep(drawtime);
            }
            catch (InterruptedException e)
            {}
        }
    }
    
    //*/
    
    
    
    //*
    // this code is taken from ...

    @Override
    public void run(){
        while(running){
            //
            waitOnPause();
            
            if(!running){
                return;
            }
            
            if(prefs.getClearFlag()){
                prefs.setClearAll(false);
                scene.onResetFlag();
            }
            
            long start = System.currentTimeMillis();
            
            Canvas canvas = null;
            
            try{
                canvas = surface.lockCanvas();
                
                if(canvas==null){
                    continue;
                }
                
                scene.onDraw(canvas);
                scene.onUpdateFrame();
                
            }catch(IllegalArgumentException e){
                stopAnim();
            }finally{
                if(canvas!=null){
                    try{
                        surface.unlockCanvasAndPost(canvas);
                    }catch(IllegalArgumentException e){
                        stopAnim();
                    }
                }
            }
            
            long end = System.currentTimeMillis() - start;
            if(drawtime> end){
                try{
                    Thread.sleep(drawtime - end);
                }catch (InterruptedException e){}
            }
            
            
        }
    }
    
    //*/
    
    public void waitOnPause(){
        while(paused){
            try{
                Thread.sleep(1000);
            }catch (InterruptedException e){}
        }
    }
    
    public void pauseAnim(){
        synchronized(lock){
            paused = true;
        }
    }
    
    public void resumeAnim(){
        synchronized(lock){
            paused = false;
            lock.notifyAll();
        }
    }
    
    public void stopAnim(){
        synchronized(lock){
            paused = false;
            running = false;
            lock.notifyAll();
        }
    }
}
