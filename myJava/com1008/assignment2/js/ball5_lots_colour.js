// Author: Steve Maddock
// Date: 26 November 2015

// variables
var context;
var requestID;
var WIDTH;
var HEIGHT;
var NUM_BALLS = BallList.MAX_NUMBALLS;
var balls;
 
// main program body
init();

// functions
function init() {
  // get the rendering area for the canvas
  context = $('#canvas_example')[0].getContext("2d");
  WIDTH = $('#canvas_example').width();
  HEIGHT = $('#canvas_example').height();

  initBalls();

  // start animation
  nextFrame();
}

// animation frames
function nextFrame() {
  requestID = requestAnimationFrame(nextFrame);
  render();
}

// initialise all the balls
function initBalls() {
  balls = new BallList();           // A data structure to hold the collection of balls
 
  for (i=0; i < NUM_BALLS; ++i) {   // add NUM_BALLS to the data structure for the balls
    var r = 5+Math.random()*25;     // create random values for each attribute
    var sx = Math.random()*WIDTH;
    var sy = Math.random()*HEIGHT;
    var dx = Math.random()*10-5;
    var dy = Math.random()*10-5;
    var red = Math.floor(Math.random()*256); // red, green and blue have to be integer values
                                             // so use Math.floor()
                                             // alpha must be in range 0 to 1
    var green = Math.floor(Math.random()*256);
    var blue = Math.floor(Math.random()*256);  
    var alpha = Math.random();
    var c = "rgba("+red+","+green+","+blue+","+alpha+")";
    //console.log(c);                      // print debugging info if necessary
    //console.log(sx+", "+sy);
    var ball = new Ball(sx,sy,r,dx,dy,c);  // create a new Ball object
    balls.add(ball);                       // add the Ball object to the data structure for the balls
  }
}

// render function
function render() {
  // minimum time between clearing the screen and drawing the balls again
  clear();
  drawBalls();
  // update balls for next draw cycle
  updateBalls();
}

function clear() {
  context.clearRect(0, 0, WIDTH, HEIGHT);
}

// draw all the balls
function drawBalls() {
  balls.draw(context);
}
    
// update all the balls
function updateBalls() {
  for (var i = 0; i < balls.getNumBalls(); ++i) {
    var ball = balls.get(i);
    // bounce the balls off the sides, if necessary
    var bxNext = ball.getX() + ball.getDX();
    if (bxNext > WIDTH || bxNext < 0) {
      ball.setDX(-ball.getDX());      // reverse direction
    }
    // bounce the balls off the top or bottom, if necessary
    var byNext = ball.getY() + ball.getDY();
    if (byNext > HEIGHT || byNext < 0) {
      ball.setDY(-ball.getDY());      // reverse direction
    }
  }
  // now update the positions of the balls      
  for (var i = 0; i < balls.getNumBalls(); ++i) {
    var ball = balls.get(i);
    ball.setX( ball.getX() + ball.getDX() );
    ball.setY( ball.getY() + ball.getDY() );
  }
}    