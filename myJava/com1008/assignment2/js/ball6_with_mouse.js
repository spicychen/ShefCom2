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
  <!-- get the rendering area for the canvas -->
  context = $('#canvas_example')[0].getContext("2d");
  WIDTH = $('#canvas_example').width();
  HEIGHT = $('#canvas_example').height();
  canvasMinX = $('#canvas_example').offset().left;
  canvasMaxX = canvasMinX + WIDTH;
      
  initBalls();
  initPaddle();
  
  // see http://api.jquery.com/ for further information
  $(document).keydown(onKeyDown);
  $(document).keyup(onKeyUp);
  $(document).mousemove(onMouseMove);
  
  <!-- start animation -->
  nextFrame();
}

// animation frames
function nextFrame() {
  requestID = requestAnimationFrame(nextFrame);
  render();
}
  
// render the screen display
function render() {
  clear();      
  drawBalls();
  updatePaddle();
  drawPaddle();      
  updateBalls();
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
  balls = new BallList();          // use the BallList data structure
  for (i=0; i < NUM_BALLS; ++i) {  // for each ball
    var r = 10;                    // set the attributes
    var sx = Math.random()*WIDTH;
    var sy = Math.random()*HEIGHT/8;
    var dx = Math.random()-0.5;
    var dy = 0.5+Math.random();
    var red = 0;
    var green = 0;
    var blue = 255;  
    var alpha = 1;                 // alpha must be in range 0 to 1
    var c = "rgba("+red+","+green+","+blue+","+alpha+")";
        //console.log(c);
        //console.log(sx+", "+sy);
    var ball = new Ball(sx,sy,r,dx,dy,c);  // create a new Ball object
    balls.add(ball);               // add the new Ball object to the list of balls
  }
}

// update the positions of all the balls
function updateBalls() {
  for (var i = 0; i < balls.getNumBalls(); ++i) {
    var b = balls.get(i);
    var bxNext = b.getX() + b.getDX();
    if (bxNext > WIDTH || bxNext < 0) {
      b.setDX(-b.getDX());
    }
    var byNext = b.getY() + b.getDY();
    if (byNext > HEIGHT || byNext < 0)
      b.setDY(-b.getDY());
  }    

  for (var i = 0; i < balls.getNumBalls(); ++i) {
    var ball = balls.get(i);
    var bxNext = ball.getX() + ball.getDX();
    ball.setX(bxNext);
    var byNext = ball.getY() + ball.getDY();
    ball.setY(byNext);
  }
}    
  
// draw the balls
function drawBalls() {
  balls.draw(context);
}