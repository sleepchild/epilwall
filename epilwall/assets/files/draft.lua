
local util = ...

-- this is how to import (bind actually) java classes
local Thread = luajava.bindClass('java.lang.Thread')
local Paint = luajava.bindClass('android.graphics.Paint')

-- width and height of the canvas (also the screen)
local mWidth, mHeight

-- background color
local col = util:color()

-- scene update titming
local prev, interval = os.time(), 0

-- circle props -- maybe we can have an object here
local zx, zy, zr = 10, 0, 70 --
local zfall = 15 -- changing this affects how fast the circle falls
local circle_paint = Paint.new()
circle_paint:setColor(util:color());

function onInit()
    -- circle_paint = Paint.new()
end

function onUpdateFrame()
    
    local curr = os.time()
    local diff = curr - prev
    if diff >= interval then
        update()
        local wait = 0 -- math.floor(1000 * (prev + interval - os.time()))
        if wait > 0 then pcall(Thread.sleep, Thread, wait) end
        prev = os.time()
    end
     
end

function update()

    zy = zy + zfall
    
    if zy>mHeight then
        zy=0
        col = util:color()
        circle_paint:setColor(util:color());
        zx = zx + zr
        if zx>mWidth then
            zx=0;
        end
    end
    
end

function onDraw(canvas)
    
     canvas:drawColor(col)
     canvas:drawCircle(zx,zy, zr,circle_paint)
    
    -- canvas:drawColor(0xff23a8ad)
end

function onUpdateSize(w, h)
    mWidth = w
    mHeight = h
end
