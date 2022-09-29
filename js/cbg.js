
/*  cbg.js - provides an always random background using the canvas element.
    .
*/
(function(w){
    var canvas = document.getElementById("canvas");
    canvas.width = window.innerWidth;
    canvas.height = window.innerHeight;
    
    var ctx = canvas.getContext("2d");
    
    var swidth = canvas.width;
    var sheight = canvas.height;
    
    var timer=0;
    var fps=30;
    var lapse = 1;//9000/fps;
    
    var spawnDiff=2000;
    var lastspawn=0;
    
    var mxr=false;
    
    var shapes;// = new Array();
    var max = 15;// 50; //max number of allowed shapes
    var cc = 0;// current number of shapes//todo: query shapes instead
    //var x=0,y=0;
    
    function init(){
        shapes = new Array();
        oldShapes = new Array();
        //
        window.requestAnimationFrame(step);
    }
    
    function step(el){
        //
        var ok=false;
        if(timer>lapse){
            timer=0;
            ok=true;
        }
        timer+=1;
        //
        if(ok){
            spawnShape();
            //
            render();
            update();
            //
        }
        
        window.requestAnimationFrame(step);
    }
    
    
    function render(){
        //
        ctx.clearRect(0,0, canvas.width, canvas.height);
        //
        shapes.forEach(function(s,i){
            //
            s.draw(ctx);
            
            //ctx.fillStyle = s.color;
            //ctx.fillRect(s.x, s.y, s.w, s.h);
        });
        
          
    }
    
    //important: this is called after render;
    function update(){
       // var keepshapes = [];
        
        shapes.forEach(function(s, i){
            s.update();
            if(s.y<sheight){
                //keepshapes.push(i);
            }
        });
        
        if(shapes.length==max){
            mxr=true;
        }else{
            mxr=false;
        }
        var newt = [];
        
       // keepshapes.forEach(function(e){
         //  var s = shapes.at(e);
       //    newt.push(s);
     //   });
        
       // shapes = newt;
       
    }
    
    var sx=0;
    function spawnShape(){
        //
        if(mxr){
            lastspawn=0;
            return;
        }
        
        if(lastspawn>spawnDiff){
            //
            var s = new Shape();
            //var v = randx(1,5);
            //if(v>2){
               //s = spawnCircle();
            //}
            //s.w = randx(10, 100);
           // s.h = randx(10, 100);
            s.x = randx(0, swidth-s.w);
            shapes.push(s);
            
            lastspawn=0;
            cc++;
        }
        
        lastspawn+=100;
    }
    
    function spawnCircle(){
        var c = new Shape();
        c.r=randx(10,50);
        c.y=0;
        
        c.fill = randColor();
        
        c.w=randx(10,50);
        c.h=c.w;
        
        c.lr=true;
        c.ud=true;
        
        //*
        c.draw=function(cv){
            cv.fillStyle = c.fill;// randColor();
            //cv.fillRect(c.x, c.y,c.w,c.h);
            cv.beginPath();
            cv.arc(c.x+(c.w/2),c.y+(c.h/2),c.w/2,0,2*Math.PI);
            cv.fill();
            //cv.endPath();
        };
        //*/
        c.update=function(){
            
            if(c.y+c.h>=sheight){
                c.ud = false;
                c.fill = randColor();
            }
            
            if(c.y<=0){
                c.ud=true;
                c.fill = randColor();
            }
            
            if(c.ud){
                c.y += 5; 
            }else{
                c.y -= 5;
            }
            
            if(c.x+c.w>=swidth){
                c.lr = false;
                c.fill = randColor();
            }
            
            if(c.x<=0){
                c.lr = true;
                c.fill = randColor();
            }
            
            if(c.lr){
                c.x += 2;
            }else{
                c.x -= 2;
            }
        };
        //*/
        
        return c;
    }
    
    
    var Shape = function(){
        this.x = 0;
        this.y=sheight;
        
        this.w = randx(1, 30);
        this.h= randx(1,30);
        
        this.vs=randx(3,6);
        
        this.color = randColor();// "rgb(200,100,22)";
        
        this.update = function(){
            this.y-= this.vs;
           // this.y = 300;
            //this.h=randx(20, 100);
            if(this.y<-this.h){
                this.w = randx(1, 50);
                this.h= randx(1,50);
        
                this.y=sheight;
                this.x= randx(0, swidth - this.w);
                
               // this.w = randx(1, 20);
                this.color = randColorx(0,255);
            }
        };
        
        this.draw = function(cv){
            //
            var x=this.x,y=this.y;
            cv.strokeStyle = this.color;
            cv.fillStyle = this.color;
            
            cv.beginPath();
            //
            cv.moveTo(x, y);
            cv.lineTo(x+this.w, y);//randx(y, y+this.h));
            cv.lineTo(x+this.w, y+this.h);
            cv.lineTo(x,y+this.h);
            //
            cv.closePath();
            cv.stroke();
           
        };
    };
    
    function randx(min, max){
        return Math.floor((Math.random() * max) + min);
    }
    
    function randColor(){
        var r,g,b;
        r = randx(1,255);
        g = randx(1,255);
        b = randx(1,255);
        var col = "rgb("+r+","+g+","+b+")";
        
        return col;
    }
    
    function randColorx(m1, m2){
        var r,g,b;
        r = randx(m1, m2);
        g = randx(m1,m2);
        b = randx(m1,m2);
        var col = "rgb("+r+","+g+","+b+")";
        
        return col;
    }
    
    init();
    
    function inspect(obj){
        var ret = "";
        for(p in obj){
            ret += p+" : "+obj[p] +"\n";
        }
        return ret;
    }
})(window);
