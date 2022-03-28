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
    
    private void i(){
        //
        cbToggleDebug = (CheckBox) findViewById(R.id.cb_debug);
        cbToggleDebug.setChecked(prefs.getDebugOn());
        
        cbSpawnC = (CheckBox) fv(R.id.cb_show_circles);
        cbSpawnC.setChecked(prefs.getSpawnCircles());
        
        cbSpawnSq = (CheckBox) fv(R.id.cb_show_squares);
        cbSpawnSq.setChecked(prefs.getSpawnSquares());
        
        //
    }
    
    View fv(int resid){
        return findViewById(resid);
    }
    
    public void showPicker(View v){
        startActivity(new Intent(WallpaperManager.ACTION_LIVE_WALLPAPER_CHOOSER));
    }
    
    public void onClick(View v){
        int id = v.getId();
        switch(id){
            case R.id.cb_debug:
                toggleDevug();
                cbToggleDebug.setChecked(prefs.getDebugOn());
                break;
            case R.id.cb_show_circles:
                // changing this will not effect shapes already spawned
                prefs.setSpawnCircles(!prefs.getSpawnCircles());
                cbSpawnC.setChecked(prefs.getSpawnCircles());
                break;
            case R.id.cb_show_squares:
                // changing this will not effect shapes already spawned
                boolean sq = prefs.getSpawnSquares();
                prefs.setSpawnSquares(!sq);
                cbSpawnSq.setChecked(prefs.getSpawnSquares());
                break;
             case R.id.cb_clear_all:
                 prefs.setClearAll(true);
                 toast("cleared!");
                 
                 break;
        }
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
