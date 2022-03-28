package sleepchild.epilwall;
import android.graphics.*;

public interface Scene{
    public void onUpdateFrame();
    public void onDraw(Canvas c);
    public void onUpdateSize(int width, int height);
    
    // @depricated
    //this is a debug feature and will be removed
    public void onResetFlag();
}
