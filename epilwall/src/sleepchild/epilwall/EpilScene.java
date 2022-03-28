package sleepchild.epilwall;
import android.graphics.*;
import java.util.*;
import sleepchild.epilwall.shapes.*;

// an example scene in which shapes fall
public class EpilScene implements Scene
{
    private SPrefs prefs;
    
    int width; //scene width
    int height; //scene height
    
    Paint background;
    
    List<Shape> shapes = new ArrayList<>();
    List<Shape> oldShapes = new ArrayList<>();
    
    int spawnTime=3000;
    int lastSpawnTime=0;
   
    // START DEBUG INFO
    
    // Paints used when debug is on
    Paint debug_paint_shape_bounds, debug_paint_text;
    // counters
    int debug_count_total_shapes=0,
        debug_count_removed_shapes=0,
        debug_count_total_circles=0,
        debug_count_total_squares=0,
        debug_count_disposed_circles=0,
        debug_count_disposed_squares=0;
    
    // END DEBUG INFO
    
    
    
    public EpilScene(SPrefs prefs){
        this.prefs = prefs;
        background = new Paint();
        background.setColor(Color.parseColor("#000000"));
        
        debug_paint_text = new Paint();
        debug_paint_text.setColor(Color.CYAN);
        debug_paint_text.setTextSize(30);
        
        debug_paint_shape_bounds = new Paint();
        debug_paint_shape_bounds.setColor(Color.CYAN);
        debug_paint_shape_bounds.setStyle(Paint.Style.STROKE);
        //
    }
    
    @Override
    public void onUpdateSize(int w, int h){
        width = w;
        height = h;
        for(Shape s : shapes){
            s.updateSize(w,h);
        }
    }
    
    // remeber: last drawn is shown on top
    @Override
    public void onDraw(Canvas canvas){
        //
        canvas.drawPaint(background);
        for(Shape s : shapes){
            s.draw(canvas);
        }
        
        if(prefs.getDebugOn()){
            debug_stats(canvas);
        }
        
    }
    
    private void debug_stats(Canvas canvas){
        
        int dtextY=100;
        int debug_current_squares_count=0, debug_current_circles_num=0;
        
        // show shape bounds
        for(Shape s : shapes){
            canvas.drawRect(s.getBounds(), debug_paint_shape_bounds);
            
            if(s instanceof Circle){
                debug_current_circles_num++;
            }else if(s instanceof Square){
                debug_current_squares_count++;
            }
        }
        
        // WRITE STATS
        String st="";
        
        // shape visibility
        st = "spawn circles: " + prefs.getSpawnCircles();
        canvas.drawText(st, 10, dtextY, debug_paint_text);
        dtextY+=30;
        st = "spawn squares: " + prefs.getSpawnSquares();
        canvas.drawText(st, 10, dtextY, debug_paint_text);
        dtextY+=30;
        dtextY+=30;
        
        // current items
        st = "current: "+shapes.size();
        canvas.drawText(st,10, dtextY, debug_paint_text);
        dtextY+=30;
        st = "  circles: "+debug_current_circles_num;
        canvas.drawText(st,10, dtextY, debug_paint_text);
        dtextY+=30;
        st = "  squares: "+debug_current_squares_count;
        canvas.drawText(st,10, dtextY, debug_paint_text);
        dtextY+=30;
        dtextY+=30;
        
        // disposed items
        st ="disposed: "+ debug_count_removed_shapes;
        debug_paint_text.setColor(Color.RED);
        canvas.drawText(st,10, dtextY, debug_paint_text);
        dtextY+=30;
        st="  circles:"+debug_count_disposed_circles;
        canvas.drawText(st,10,dtextY, debug_paint_text);
        dtextY+=30;
        st = "  squares:"+debug_count_disposed_squares;
        canvas.drawText(st,10,dtextY, debug_paint_text);
        dtextY+=30;
        dtextY+=30;
        
        // total items;
        debug_paint_text.setColor(Color.WHITE);
        st ="total: "+debug_count_total_shapes;
        canvas.drawText(st,10,dtextY, debug_paint_text);
        dtextY+=30;
        st="  circles:"+debug_count_total_circles;
        canvas.drawText(st,10,dtextY, debug_paint_text);
        dtextY+=30;
        st = "  squares:"+debug_count_total_squares;
        canvas.drawText(st,10,dtextY, debug_paint_text);
        dtextY+=30;
        //
        dtextY+=30;
    }
    
    
    // note: this is called after onDraw();
    @Override
    public void onUpdateFrame(){
        for(Shape d : shapes){
            d.update();
            RectF b = d.getBounds();
            if(b.top>height || b.bottom<0 || b.right<0 || b.left>width){
                // out of bounds
                oldShapes.add(d);
            }
        }
        //*
        for(Shape s : oldShapes){
            if(shapes.contains(s)){
                shapes.remove(s);
                debug_count_removed_shapes++;
                
                if(s instanceof Circle){
                    debug_count_disposed_circles++;
                }else if(s instanceof Square){
                    debug_count_disposed_squares++;
                }
                s = null;
            }
        }
        oldShapes.clear();
        //*/
        
        lastSpawnTime += 1000;
        if(lastSpawnTime>spawnTime){
            lastSpawnTime = 0;
            spawnTime = Utils.randx(0,3000);
            //
            randomShape();
        }
    }
    
    void randomShape(){
        //
        Shape shp=null;
        //*
        int dice = Utils.randx(0,3);
        switch(dice){
            case 1:
                if(prefs.getSpawnCircles()){
                    shp = new Circle(width, height);
                }
                break;
            case 2:
                if(prefs.getSpawnSquares()){
                    shp = new Square(width, height);
                }
                break;
            default:
                //shp = new Circle(width, height);
                break;
        }
        //*/
        
        if(shp!=null){
            shapes.add(shp);
            debug_count_total_shapes++;
            //
            if(shp instanceof Circle){
                debug_count_total_circles++;
            }else if(shp instanceof Square){
                debug_count_total_squares++;
            }
        }
        
    }

    // this is an experimental/debug feature and will be removed
    @Override
    public void onResetFlag()
    {
        // clear all shapes
        shapes.clear();
        //
        //clear debug counters;
        debug_count_total_shapes=0;
        debug_count_removed_shapes=0;
        debug_count_total_circles=0;
        debug_count_total_squares=0;
        debug_count_disposed_circles=0;
        debug_count_disposed_squares=0;
    }
    
    
    
    
    
    
}
