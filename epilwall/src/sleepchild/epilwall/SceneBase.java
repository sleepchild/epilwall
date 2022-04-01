package sleepchild.epilwall;
import android.graphics.*;
import org.luaj.vm2.lib.jse.*;
import org.luaj.vm2.*;
import org.luaj.vm2.lib.*;
import java.io.*;

public class SceneBase implements Scene, ResourceFinder
{
    public Globals globals;
    
    public SceneBase(){
        globals = JsePlatform.standardGlobals();
        globals.finder = this;
        
        Utils.mkdirs(Utils.PATH_SCENE_BASE);
        
    }

    @Override
    public InputStream findResource(String name)
    {
        String fip = Utils.PATH_SCENE_BASE+name;
        try
        {
            FileInputStream ins = new FileInputStream(fip);
            return ins;
        }
        catch (FileNotFoundException e)
        {}
        return null;
    }

    @Override
    public void onInit()
    {
        LuaValue f = globals.get("onInit");
        if(!f.isnil()){
            try{
                f.call();
            }catch(Exception e){
                // log
            }
        }
    }
    
    @Override
    public void onUpdateFrame()
    {
        LuaValue f = globals.get("onUpdateFrame");
        if(!f.isnil()){
            try{
                f.call();
            }catch(Exception e){
                // log
            }
        }
    }

    @Override
    public void onDraw(Canvas c)
    {
        
        LuaValue f = globals.get("onDraw");
        if(!f.isnil()){
            try{
                f.call(CoerceJavaToLua.coerce(c));
            }catch(Exception e){
                // log
            }
        }
    }

    @Override
    public void onUpdateSize(int width, int height)
    {
        LuaValue f = globals.get("onUpdateSize");
        if(!f.isnil()){
            try{
                f.call(CoerceJavaToLua.coerce(width), CoerceJavaToLua.coerce(height));
            }catch(Exception e){}
        }
    }
    
}
