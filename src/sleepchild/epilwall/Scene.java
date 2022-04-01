package sleepchild.epilwall;
import android.graphics.*;

public interface Scene{
    public void onInit();
    public void onUpdateFrame();
    public void onDraw(Canvas c);
    public void onUpdateSize(int width, int height);
}
