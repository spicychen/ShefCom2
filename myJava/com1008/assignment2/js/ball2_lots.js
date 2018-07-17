// Author: Steve Maddock
// Date: 26 November 2015

// variables
var context;

// main program body
render();

//functions 
function render() {
  // get the rendering area for the canvas
  context = $('#canvas_example')[0].getContext("2d");
  var WIDTH = $('#canvas_example').width();
  var HEIGHT = $('#canvas_example').height();
  // now draw
  var NUM_BALLS = 100;	
  console.log(WIDTH);
  for (i=0; i < NUM_BALLS; ++i) {
    var r = 5+Math.random()*15;
    var x = Math.random()*WIDTH;
    var y = Math.random()*HEIGHT;
    var red = Math.floor(Math.random()*256);
    var green = Math.floor(Math.random()*256);
    var blue = Math.floor(Math.random()*256);
    var c = "rgb("+red+","+green+","+blue+")";
    circle(x, y, r, c);
  }
}
    
// draw a circle with centre (x,y), radius r and colour c  

function circle(x, y, r, c) {
  context.fillStyle = c;
  context.beginPath();
    context.arc(x, y, r, 0, Math.PI*2, true);
  context.closePath();
  context.fill();
}