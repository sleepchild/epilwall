package sleepchild.epilwall;

import android.app.Activity;
import android.os.Bundle;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;
import android.view.inputmethod.InputMethodManager;
import android.view.View;
import android.content.SharedPreferences;
import android.app.WallpaperManager;
import android.widget.*;

public class MainActivity extends Activity {

    public Context ctx;
    private SPrefs prefs;
    
    CheckBox cbToggleDebug;
    CheckBox cbSpawnC, cbSpawnSq;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //
        ctx = getApplicationContext();
        prefs = new SPrefs(getApplicationContext());
        //
        i();
    }
    
    void i(){
        //
    }
    
    public void openWallPicker(View v){
        showPicker(v);
    }
    
    View fv(int resid){
        return findViewById(resid);
    }
    
    public void showPicker(View v){
        startActivity(new Intent(WallpaperManager.ACTION_LIVE_WALLPAPER_CHOOSER));
    }
    
    public void onClick(View v){
        int id = v.getId();
        //
    }
    
    private void toggleDevug(){
        boolean d = prefs.getDebugOn();
        prefs.setShowDebug(!d);
    }
    
    public void startActivity(Class<?> clazz){
        startActivity(new Intent(this, clazz));
    }
    
    public void toast(String msg){
        Toast.makeText(ctx, msg, 500).show();
    }
    
    //@Override
    public void onPointerCaptureChanged(boolean hasCapture)
    {
        // TODO: Implement this method
    }
}
