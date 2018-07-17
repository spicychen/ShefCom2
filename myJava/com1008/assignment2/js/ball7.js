// Author: Steve Maddock
// Date: 26 November 2015

// variables
var context;
var requestID;
var WIDTH;
var HEIGHT;
var NUM_BALLS = 10;
var balls;
var paddlex, paddley, paddleh, paddlew;
var rightDown = false;
var leftDown = false;
var canvasMinX = 0;
var canvasMaxX = 0;
   
// main program body
init();

// functions
// initialise the simulation
function init() {
  // get the rendering area for the canvas
  context = $('#canvas_example')[0].getContext("2d");
  WIDTH = $('#canvas_example').width();
  HEIGHT = $('#canvas_example').height();
  canvasMinX = $('#canvas_example').offset().left;
  canvasMaxX = canvasMinX + WIDTH;
      
  initBalls();
  initPaddle();
     
  $(document).keydown(onKeyDown);
  $(document).keyup(onKeyUp);
  $(document).mousemove(onMouseMove);
    
  // start animation 
  nextFrame();
}
 
// render the screen display
function render() {
  clear();      
  drawBalls();
  updatePaddle();
  drawPaddle();      
  updateBalls();
}

// animation frames
function nextFrame() {
  requestID = requestAnimationFrame(nextFrame);
  render();
}
    
// paddle functions
// initialise the paddle
function initPaddle() {
  paddlex = WIDTH/2;
  paddley = HEIGHT-10;
  paddleh = 10;
  paddlew = 70;
}

//update the paddle based on user input    
function updatePaddle() {
  if (rightDown) {
    paddlex += 5;
    if (paddlex > WIDTH-paddlew) paddlex = WIDTH-paddlew;
  }
  else if (leftDown) {
    paddlex -= 5;
    if (paddlex < 0) paddlex = 0;
  }
}

// draw the paddle
function drawPaddle() {
  context.fillStyle = "black";
  context.beginPath();
    context.rect(paddlex, paddley, paddlew, paddleh);
  context.closePath();
  context.fill();
}

// Balls functions
// initialise all the balls
function initBalls() {
  balls = new BallList();
  for (i=0; i < NUM_BALLS; ++i) {
    var r = 10;
    var sx = Math.random()*WIDTH;
    var sy = Math.random()*HEIGHT/8;
    var dx = Math.random()-0.5;
    var dy = 0.5+Math.random();
    var red = 0;
    var green = 0;
    var blue = 255;  
    var alpha = 1;
    var c = "rgba("+red+","+green+","+blue+","+alpha+")";
    //console.log(c);
    //console.log(sx+", "+sy);
    var ball = new Ball(sx,sy,r,dx,dy,c, true);
    balls.add(ball);
  }
} 
    
// reaction when paddle has hit ball
function doSomethingHitBall(b) {
  console.log("hit ball "+b);
  $('#message_area').html('<p>hit ball '+b+'</p>');
}
    
// reaction when paddle misses ball
function doSomethingMissedBall(b) {
  console.log("missed ball "+b);
  $('#message_area').html('<p>missed ball '+b+'</p>');
  var count = 0;
  for (var i=0; i<balls.getNumBalls(); ++i) {
    if (balls.get(i).getVisible())
      count++;
  }
  if (count==0) {
    console.log("Game over: no balls");
    cancelAnimationFrame(requestID);
  }
}
  
// update the positions of all the balls
function updateBalls() {
  for (var i = 0; i < balls.getNumBalls(); ++i) {
    var b = balls.get(i);
    if (b.visible) {
      var bxNext = b.getX() + b.getDX();
      if (bxNext > WIDTH || bxNext < 0) {
        b.setDX(-b.getDX());
      }
      var byNext = b.getY() + b.getDY();
      if (byNext < 0) {
        b.setDY(-b.getDY());
      }
      else if (byNext > HEIGHT-paddleh) {
        // check if ball is in paddle x range
        if (b.getX() > paddlex && b.getX() < paddlex + paddlew) {
          b.setDY(-b.getDY());
          doSomethingHitBall(i);
        }  
        else {
          b.setVisible(false);
          doSomethingMissedBall(i);
        }
      }
    }
  }    
  for (var i = 0; i < balls.getNumBalls(); ++i) {
    var ball = balls.get(i);
    ball.setX( ball.getX() + ball.getDX() );
    ball.setY( ball.getY() + ball.getDY() );
  }
}    
  
// draw the balls
function drawBalls() {
  balls.draw(context);
}
       
function clear() {
  context.clearRect(0, 0, WIDTH, HEIGHT);
}

// keyboard interaction
function onKeyDown(evt) {
  if (evt.keyCode == 39) rightDown = true;
  else if (evt.keyCode == 37) leftDown = true;
}

function onKeyUp(evt) {
  if (evt.keyCode == 39) rightDown = false;
  else if (evt.keyCode == 37) leftDown = false;
}

// mouse
function onMouseMove(evt) {
  if (evt.pageX > canvasMinX && evt.pageX < canvasMaxX) {
    paddlex = evt.pageX - canvasMinX - (paddlew/2);
  }
  if (paddlex > WIDTH-paddlew) paddlex = WIDTH-paddlew;
  else if (paddlex < 0) paddlex = 0;
}