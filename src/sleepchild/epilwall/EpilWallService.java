package sleepchild.epilwall;
import android.service.wallpaper.WallpaperService;
import android.service.wallpaper.WallpaperService.Engine;
import android.view.*;
import android.content.*;
import org.luaj.vm2.*;
import org.luaj.vm2.lib.jse.*;
import java.io.*;

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
        SceneBase scene;
        SPrefs prefs;
        
        public EpilwallEngine(SPrefs prefs){
            this.prefs = prefs;
            if(prefs.getIsFirstRun()){
                prefs.clearFirstRun();
                unpack();
            }
        }

        @Override
        public void onCreate(SurfaceHolder surfaceHolder){
            super.onCreate(surfaceHolder);
            boolean ok=true;
            
            LuaValue util = CoerceJavaToLua.coerce(new Utils());
            
            scene = new SceneBase(); // new EpilScene(prefs);
            LuaValue ls = scene.globals.loadfile("draft.lua");
            if(!ls.isnil()){
               //return; 
            }
            
            ls.call(util);
            
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
    
    void unpack(){
        Utils.mkdirs(Utils.PATH_SCENE_BASE);
        try
        {
            InputStream is = mCtx.getAssets().open("files/draft.lua", mCtx.MODE_PRIVATE);
            byte[] data = new byte[is.available()];
            is.read(data);
            
            try{
                is.close();
            }catch(IOException ioe){}
            
            //
            FileOutputStream o = new FileOutputStream(Utils.PATH_SCENE_BASE+"draft.lua");
            o.write(data);
            o.flush();
            
            o.close();
            
        }
        catch (IOException e)
        {}
    }

    
    // check that the selected scene file is present;
    // if not, then load the default file
    void checkfile(){
        //
    }
    
}
