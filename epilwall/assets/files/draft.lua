
local util = ...

local col = util:color()
local prev, interval = os.time(), 1

local Thread = luajava.bindClass('java.lang.Thread')


function onUpdateFrame()
    
    local curr = os.time()
    local diff = curr - prev
    if diff >= interval then
        col = util:color()
        local wait = math.floor(1000 * (prev + interval - os.time()))
        if wait > 0 then pcall(Thread.sleep, Thread, wait) end
        prev = os.time()
    end
     
end

function update()
    -- col = util:color()
end

-- this method is called every 1000/30 ms

function onDraw(canvas)
    
    canvas:drawColor(col)
    
    -- canvas:drawColor(0xff23a8ad)
end
