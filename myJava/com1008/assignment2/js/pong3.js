// Author: Steve Maddock
// Date: 26 November 2015

// variables

    // Number of variables is getting messy. 
    // The use of objects would help to tidy this up

    var context;
	  var requestID;
    var WIDTH;
    var HEIGHT;
    var WIN_SCORE = 5;

    // ball
    var x = 50;
    var y = 250;
    var dx = 1.5;
    var dy = -4;
    var radius = 10;

    // Human
    var humanScore = 0
    var humanPaddleX;
    var humanPaddleY;
    var humanColour = "rgb(50,50,255)";

    // computer
    var computerScore = 0;
    var computerPaddleX;
    var computerPaddleY;
    var computerColour = "rgb(255,50,50)";

    // human and computer 
    var paddleh = 55;
    var paddlew = 10;
	  var humanServe = false;

    // interaction
    var upKeyDown = false;
    var downKeyDown = false;

    // main program body
    init();
    
    // functions
    
	  // draw the ball
    function drawBall() {  
      context.fillStyle = "black";
      context.beginPath();
        context.arc(x, y, radius, 0, Math.PI*2, true);
      context.closePath();
      context.fill();
    }

	  // draw a paddle
    function rect(x,y,w,h, c) {  
      context.fillStyle = c;
      context.beginPath();
        context.rect(x,y,w,h);
      context.closePath();
      context.fill();
    }

	  // draw the centreline for the game court
    function centreLine() {  
      context.lineWidth = "5";
      context.strokeStyle = "gray";
      context.beginPath();
        context.moveTo(WIDTH/2, 0);
        context.lineTo(WIDTH/2, HEIGHT);
      context.closePath();
      context.stroke();
    }

	  // clear the drawing area
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

	  // start the ball quarter of the way down the centre line
	  // Computer serves first.
	  // Thereafter whoever wins a point serves next.
    function resetBall() { 
      x = WIDTH/2;
      y = 0.25*HEIGHT + 0.5*HEIGHT*Math.random();
      var xnudge = Math.random();
      dx = 1.5+xnudge;
	    if (humanServe) dx = -dx;
      var ynudge = -1+Math.random()*2; // add a little nudge to the y direction of travel
      dy = 4+ynudge;
	    if (Math.random()>0.5) dy=-dy;
    }

	  // when the game ends draw a message saying who won
    function drawFinish() { 
      resetBall();
      draw();
      context.lineWidth = "1";
      context.font = "48px serif";
      var endText = "";
      if (humanScore == WIN_SCORE) {
        context.fillStyle = humanColour;
        endText = "Human wins";
      }
      else {
        context.fillStyle = computerColour;
        endText = "Computer wins";
      }
      var textwidth = context.measureText(endText).width;
      context.fillText(endText, (WIDTH-textwidth)/2, HEIGHT/2);      
    }
    
	  // when the game ends draw the finish screen and stop the animation
    function finishGame() {  
      drawFinish();      
      if (humanScore == WIN_SCORE)
        $('#winner').html('<p>Human wins!!</p>');
      else
        $('#winner').html('<p>Computer wins!!</p>');
	    cancelAnimationFrame(requestID);
    }
    
	  // display scores on web page
    function updateWebPageScores() { 
      $('#score').html('<p>Computer ' + computerScore + ' : ' + humanScore + ' Human</p>');
      if (humanScore == WIN_SCORE || computerScore == WIN_SCORE) {
        finishGame();  
      }
    }

	  // draw the game area
    function draw() {  
      clear();
      centreLine();
      drawBall();
      rect(humanPaddleX, humanPaddleY, paddlew, paddleh, humanColour);
      rect(computerPaddleX, computerPaddleY, paddlew, paddleh, computerColour);
    }
    
	  // do user input
	  // if user has pressed up or down arrow keys then move paddle
    function userInput() {
	    if (downKeyDown) humanPaddleY += 5;
      else if (upKeyDown) humanPaddleY -= 5;
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

    // bounce ball off top and bottom of game area if necessary
    function checkTopAndBottom() {
      if (y + radius  > HEIGHT) {
	      dy = -dy;
		    y = HEIGHT-radius;
	    }
	  
	    if (y  - radius < 0) {
        dy = -dy;
		    y = radius;
	    }
    }

	  // check computer side 
    function doComputerPaddle() {
	    var leftEdgeOfBall = x+dx-radius;
      // if hits computer paddle
      if (leftEdgeOfBall < paddlew
          && y > computerPaddleY - radius/4               // -radius/4 makes the paddle a bit bigger
		      && y < computerPaddleY + paddleh + radius/4 ) { // to compensate for edge of ball hitting top of paddle
        dx = -dx;		                                      // reverse x direction 
        var xadjust = radius+(paddlew-x)-dx;              // Adjust x position so that on next update
		                                                      // it will move to front of paddle
		    var yadjust = (dy/Math.abs(dx))*Math.abs(xadjust);// adjust y to match
		    x += xadjust;
	      y += yadjust
      }
	    else if (leftEdgeOfBall < 0) { // if computer has missed the ball
        humanScore+=1;               // update human score by 1  
		    humanServe = true;
        updateWebPageScores();
        resetBall();                 // start new ball at centre
      }
    }
	
	  // check human side
	  function doHumanPaddle() {
	    var rightEdgeOfBall = x+dx+radius;
	    var xFront = WIDTH - paddlew;

       // if hits human paddle
	    if (rightEdgeOfBall > xFront
          && y >= humanPaddleY-radius/4                  // -radius/4 makes the paddle a bit bigger
  		    && y <= humanPaddleY + paddleh + radius/4) {   // to compensate for edge of ball hitting top of paddle
        dy = 8 * ((y-(humanPaddleY+paddleh/2))/paddleh); // human can vary rebound angle
        dx = -dx;                                        // reverse x direction
	  	  var xadjust = -(radius+(x-xFront))-dx;           // Adjust x position so that on next update
		                                                   // it will move to front of paddle
		    var yadjust = (dy/Math.abs(dx))*Math.abs(xadjust);  // adjust y to match
		    x += xadjust;
	      y += yadjust
      }
	    else if (rightEdgeOfBall > WIDTH) { // if human has missed the ball
        computerScore+=1;                 // update computer score by 1
		    humanServe = false;
        updateWebPageScores();
        resetBall();                      // start new ball at centre
      }
	  }
	
    // Update the game state 
    function update() {
      // get input
      userInput();
      computerAI();
  
	    checkTopAndBottom();
      
      // draw the game pieces
      draw();  
	    
      // check if ball hits paddles
	    // or if outside right or left of screen
	    if (x<WIDTH/2) {
	      doComputerPaddle();
	    }
	    else {
	      doHumanPaddle();
      }

      // update ball position
      x += dx;
      y += dy;
    }

	  // animation frames
    function nextFrame() {
      requestID = requestAnimationFrame(nextFrame);
      update();
    }
    
	  // initialise the game state and start the game
    function init() {
      // get the rendering link for the canvas
      context = $('#canvas')[0].getContext("2d");
  	  // Get width and height of canvas so they can be used
	    // in subsequent calculations
      WIDTH = $("#canvas").width()
      HEIGHT = $("#canvas").height()
    
      // starting x and y position for the user's paddle
	    // need to know width and height of canvas before these can be set
	    // human paddle is on right hand edge of playing area
      humanPaddleX = WIDTH-paddlew;
      humanPaddleY = HEIGHT/2;
      
      // starting x and y position for the computer's paddle
	    // computer paddle is on left hand edge of playing area
      computerPaddleX = 0;
      computerPaddleY = HEIGHT/2;
      
      // start position of the ball
      resetBall();
      
      // see http://api.jquery.com/ for further information
      $(document).keydown(onKeyDown);
      $(document).keyup(onKeyUp);
      
      // start animation, i.e. start the game
      nextFrame();
    }