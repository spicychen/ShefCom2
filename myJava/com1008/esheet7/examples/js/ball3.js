// Author: Steve Maddock
// Date: 26 November 2015

// variables
var context;        // used to link to the drawing area
var requestID;      // used to control animation
var WIDTH;          // CONSTANTs that will store the width 
var HEIGHT;         // and height of the canvas. Use uppercase for constants
                        // Attributes of a ball (i.e. a circle):
var x;              // x coordinate of the centre 
var y;              // y coordinate of the centre
var radius;         // radius
var dx;             // dx is how much to update x for each animation frame
var dy;             // dy is how much to update y for each animation frame

// main program body
init();

// functions     
function init() {
  // get the rendering link for the canvas
  context = $('#canvas_example')[0].getContext("2d");
  WIDTH = $('#canvas_example').width();
  HEIGHT = $('#canvas_example').height();
	  
  // give the attributes of the ball some initial values
  initBall();
	  
  // start animation
  requestID = requestAnimationFrame(nextFrame);
}

// This function will keep calling the render function every 1/60th of a second.
// This matches the refresh rate of the browser which is 60 frames per second.
function nextFrame() {
  requestID = requestAnimationFrame(nextFrame);
  render();
}

function render() {
  // clear the drawing area
  clear();
      
  // draw something      
  circle(x, y, radius, "rgb(0,0,255)");

  // update the variables
  x += dx;    // same as x = x + dx
  y += dy;    // same as y = y + dy
}
    
function initBall() {  // give the attributes of the ball some initial values
  x = WIDTH/4;
  y = HEIGHT/4;
  radius = 20;
  dx = 2;
  dy = 3;
}
	
function circle(x, y, r, c) {  // draw the ball
  context.fillStyle = c;
  context.beginPath();
    context.arc(x, y, r, 0, Math.PI*2, true);
  context.closePath();
  context.fill();
}
	
function clear() {
  context.clearRect(0, 0, WIDTH, HEIGHT);
}