// Author: Steve Maddock
// Date: 26 November 2015

// variables
    var context;
    var requestID;
    var WIDTH;
    var HEIGHT;
    
    // ball
    var x;
    var y;
    var radius;
    var dx;
    var dy;
    
    // Human
    var humanPaddleX;
    var humanPaddleY;
    var humanColour;

    // Computer 
    var computerPaddleX;
    var computerPaddleY;
    var computerColour;

    // human and computer 
    var paddleh = 55;
    var paddlew = 10;
    
    // interaction
    var upKeyDown = false;
    var downKeyDown = false;

    // main program body
    init();
    
    //functions
    
    function initBall() {
      x = WIDTH/2;
      y = HEIGHT/2;
      radius = 10;
      dx = 2;
      dy = 3;
    }
    
    function drawBall() {
      context.fillStyle = "black";
      context.beginPath();
        context.arc(x, y, radius, 0, Math.PI*2, true);
      context.closePath();
      context.fill();
    }

    // Initialise paddle positions
    function initPaddles() {
      humanPaddleX = WIDTH-paddlew;
      humanPaddleY = HEIGHT/2;
      humanColour = "rgb(50,50,255)";
      computerPaddleX = 0;
      computerPaddleY = HEIGHT/2;
      computerColour = "rgb(255,50,50)";
    }
    
	  // draw a paddle
    function rect(x,y,w,h, c) {  
      context.fillStyle = c;
      context.beginPath();
        context.rect(x,y,w,h);
      context.closePath();
      context.fill();
    }
		
    function clear() {
      context.clearRect(0, 0, WIDTH, HEIGHT);
    }

    // These two functions handle the keyboard events
    // When a user presses the up arrow key (keyCode = 38) the user's paddle moves upwards
    // When a user presses the down arrow key (keyCode = 40) the user's paddle moves downwards
    function onKeyDown(evt) {
      if (evt.keyCode == 38) upKeyDown = true;
      else if (evt.keyCode == 40) downKeyDown = true;
    }

    function onKeyUp(evt) {
      if (evt.keyCode == 38) upKeyDown = false;
      else if (evt.keyCode == 40) downKeyDown = false;
    }

	  // do user input
	  // if user has pressed up or down arrow keys then move paddle
    function userInput() {
	    if (downKeyDown) humanPaddleY += 5;
      else if (upKeyDown) humanPaddleY -= 5;
      if (humanPaddleY <0) humanPaddleY = 0;
      else if (humanPaddleY+paddleh > HEIGHT) humanPaddleY = HEIGHT-paddleh;
    }
    
    // computer AI - very simple
    // This just looks at the current y position of the ball and moves up or down accordingly
    function computerAI() {
      var yinc = 2;
  	  if (dx<0) {
        if (computerPaddleY > y) computerPaddleY -= yinc;
        else computerPaddleY += yinc;
      }
	  } 
    
    // update game state
    function update() {
      // get userinput
      userInput();
      computerAI();
      
      // clear
      clear();
	  
      // display
      drawBall(x, y, radius, "rgb(0,0,255)");   
      rect(humanPaddleX, humanPaddleY, paddlew, paddleh, humanColour);
      rect(computerPaddleX, computerPaddleY, paddlew, paddleh, computerColour);
	  
      // update ball
      if (y + dy > HEIGHT || y + dy < 0) {
        dy = -dy;
      }
      if (x + dx - radius < paddlew) {
        if (y >= computerPaddleY && y <= computerPaddleY + paddleh) {
          dx = -dx;
        }
        else if (x + dx - radius < 0) {
          cancelAnimationFrame(requestID);
        }
      }
      else if (x + dx + radius > WIDTH - paddlew) {
        if (y >= humanPaddleY && y <= humanPaddleY + paddleh) {
          dx = -dx;
        }
        else if (x + dx + radius > WIDTH) {
          cancelAnimationFrame(requestID);
        }
      }

      x += dx;
      y += dy;
    }
    
    function nextFrame() {
      requestID = requestAnimationFrame(nextFrame);
      update();
    }
    
    function init() {
      // get the rendering link for the canvas
      context = $('#canvas_example')[0].getContext("2d");
      WIDTH = $('#canvas_example').width();
      HEIGHT = $('#canvas_example').height();
	  
      initBall();
      initPaddles();
	  
      // see http://api.jquery.com/ for further information
      $(document).keydown(onKeyDown);
      $(document).keyup(onKeyUp);
    
      // start animation
      nextFrame();
    }