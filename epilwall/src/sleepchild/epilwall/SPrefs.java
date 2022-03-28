package sleepchild.epilwall;
import android.content.Context;
import android.content.SharedPreferences;

public class SPrefs
{
    SharedPreferences pref;
    SharedPreferences.Editor edit;
    
    public SPrefs(Context ctx){
        pref = ctx.getSharedPreferences("epilwall_shar12", ctx.MODE_PRIVATE);
        edit = pref.edit();
    }
    
    private enum K{
        DEBUG_ON,
        SPAWN_CIRCLES,
        SPAWN_SQUARES,
        FLAG_CLEAR_ALL
    }
    
    // debug
    public void setShowDebug(boolean show){
        saveBool(K.DEBUG_ON, show);
    }
    
    public boolean getDebugOn(){
        return getBool(K.DEBUG_ON, false);
    }
    
    // spawn circles
    // changing this will not effect shapes already spawned
    public void setSpawnCircles(boolean spawn){
        saveBool(K.SPAWN_CIRCLES, spawn);
    }

    public boolean getSpawnCircles(){
        return getBool(K.SPAWN_CIRCLES, true);
    }
    
    // spawn squares
    // changing this will not effect shapes already spawned
    public void setSpawnSquares(boolean spawn){
        saveBool(K.SPAWN_SQUARES, spawn);
    }

    public boolean getSpawnSquares(){
        return getBool(K.SPAWN_SQUARES, true);
    }
    
    //
    public void setClearAll(boolean clear){
        saveBool(K.FLAG_CLEAR_ALL, clear);
    }
    
    public boolean getClearFlag(){
        return getBool(K.FLAG_CLEAR_ALL, false);
    }
    
    
    private void saveBool(K key, boolean value){
        edit.putBoolean(key.toString(), value).commit();
    }
    
    private boolean getBool(K key, boolean deft){
        return pref.getBoolean(key.toString(), deft);
    }
    
}
