
// variables
var context;
var intervalId;
var WIDTH;
var HEIGHT;
var startedDrawing = false;
var cx=0, cy=0;

// main program body
init();

//functions
function init() {
  var canvas = document.getElementById("can");
  context = canvas.getContext("2d");
  WIDTH = canvas.width;
  HEIGHT = canvas.height;
	  
  <!-- start animation -->
  nextFrame();
  canvasMinX= $('#can').offset().left;
  canvasMinY= $('#can').offset().top;
  canvasMinX += ( ($('#can').outerWidth()-$('#can').width())/2 );
canvasMinY += ( ($('#can').outerHeight()-$('#can').height())/2 );
$('#can').mousemove(onMouseMove);
clearCanvas();
}

function onMouseMove(ev){
	cx = ev.pageX-canvasMinX;
	cy = ev.pageY-canvasMinY;
}

// animation frames
function nextFrame() {
  requestID = requestAnimationFrame(nextFrame);
  render();
}

function render() {
  <!-- clear -->
  clear();

  <!-- draw -->
  for (var i=0; i<20; i++) {
  circle(cx+Math.random()*60-30, cy-Math.random()*5, Math.random()*8, "rgb(255,255,255)");
  cy -= Math.random()*14;
 }
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