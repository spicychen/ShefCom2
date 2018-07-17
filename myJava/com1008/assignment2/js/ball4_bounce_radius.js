// Author: Steve Maddock
// Date: 26 November 2015

// variables
var context;
var intervalId;
var WIDTH;
var HEIGHT;
var x, y, radius, dx, dy;

// main program body
init();

//functions
function init() {
  var canvas = document.getElementById("canvas_example");
  context = canvas.getContext("2d");
  WIDTH = canvas.width;
  HEIGHT = canvas.height;
	  
  initBall();
	  
  <!-- start animation -->
  nextFrame();
}

// animation frames
function nextFrame() {
  requestID = requestAnimationFrame(nextFrame);
  render();
}

function initBall() {
  x = 100;
  y = 100;
  radius = 20;
  dx = 2;
  dy = 3;
}
	
function render() {
  <!-- clear -->
  clear();

  <!-- draw -->
  circle(x, y, radius, "rgb(0,0,255)");
 
  <!-- update -->
  if ((x+radius) + dx > WIDTH || (x-radius) + dx < 0)
    dx = -dx;
  if ((y+radius) + dy > HEIGHT || (y-radius) + dy < 0)
    dy = -dy;
		
  x += dx;
  y += dy;
}


function circle(x, y, r, c) {
  context.fillStyle = c;
  context.beginPath();
    context.arc(x, y, r, 0, Math.PI*2, true);
  context.closePath();
  context.fill();
}
		
function clear() {
  context.clearRect(0, 0, WIDTH, HEIGHT);
}