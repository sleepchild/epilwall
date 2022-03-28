package sleepchild.epilwall;
import android.service.wallpaper.WallpaperService;
import android.service.wallpaper.WallpaperService.Engine;
import android.view.*;
import android.content.*;

public class EpilWallService extends WallpaperService
{
    SPrefs mPrefs;
    Context mCtx;
    
    @Override
    public void onCreate(){
        super.onCreate();
        mCtx = getApplicationContext();
        mPrefs = new SPrefs(mCtx);
        //
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        //
    }
    
    @Override
    public WallpaperService.Engine onCreateEngine(){
        return new EpilwallEngine(mPrefs);
    }
    
    class EpilwallEngine extends Engine{
        AnimationThread animThread;
        Scene scene;
        SPrefs prefs;
        
        public EpilwallEngine(SPrefs prefs){
            this.prefs = prefs;
        }

        @Override
        public void onCreate(SurfaceHolder surfaceHolder){
            super.onCreate(surfaceHolder);
            
            scene = new EpilScene(prefs);
            
            animThread = new AnimationThread(surfaceHolder, scene, prefs);
            animThread.start();
            
        }

        @Override
        public void onDestroy()
        {
            animThread.stopAnim();
            joinThread(animThread);
            animThread = null;
            super.onDestroy();
        }

        @Override
        public void onSurfaceChanged(SurfaceHolder holder, int format, int width, int height)
        {
            super.onSurfaceChanged(holder, format, width, height);
            scene.onUpdateSize(width, height);
        }

        @Override
        public void onVisibilityChanged(boolean visible)
        {
            super.onVisibilityChanged(visible);
            if(visible){
                animThread.resumeAnim();
            }else{
                animThread.pauseAnim();
            }
        }
        
        public void joinThread(Thread t){
            boolean retry =true;
            while(retry){
                try
                {
                    t.join();
                    retry = false;
                }
                catch (InterruptedException e)
                {}
            }
        }
        
    }
    
}
