// Author: Steve Maddock
// Date: 26 November 2015

// variables
var context;

// main program body
render();

//functions 
function render() {
  // get the rendering link for the canvas
  context = $('#canvas_example')[0].getContext("2d");
	  
  // now draw
  circle(100, 100, 20, "rgb(0,0,255)");  // the colour is passed to the function as a string
  circle(110, 110, 20, "rgb(255,0,0)");
}

// draw a circle with centre (x,y), radius r and colour c  
function circle(x, y, r, c) {  
  context.fillStyle = c;
  context.beginPath();
    context.arc(x, y, r, 0, Math.PI*2, true);
  context.closePath();
  context.fill();
}