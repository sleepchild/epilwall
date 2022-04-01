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
        FIRST_RUN
    }
    
    // debug
    public void setShowDebug(boolean show){
        saveBool(K.DEBUG_ON, show);
    }
    
    public boolean getDebugOn(){
        return getBool(K.DEBUG_ON, false);
    }
    
    // firt run after install or data cleared
    public void clearFirstRun(){
        saveBool(K.FIRST_RUN, false);
    }

    public boolean getIsFirstRun(){
        return getBool(K.FIRST_RUN, true);
    }
    
    private void saveBool(K key, boolean value){
        edit.putBoolean(key.toString(), value).commit();
    }
    
    private boolean getBool(K key, boolean deft){
        return pref.getBoolean(key.toString(), deft);
    }
    
}
