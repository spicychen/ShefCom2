// Author: Steve Maddock
// Date: 26 November 2015

// variables
    var context;
    var requestID;
    var WIDTH;
    var HEIGHT;
    var NUM_BALLS = 100;
    var balls;

     // image handling variables    
    var imageBaseStr = "images/";
    var imageCount = 0;
    var NUM_IMAGES = 5; // number in array 
    var imgArray = new Array(NUM_IMAGES);    
    var imageNames = [imageBaseStr+"tennis_ball.png", imageBaseStr+"basketball.png",
      imageBaseStr+"golf_ball.png", imageBaseStr+"football.png", imageBaseStr+"beachball.png"];
      
// main program body
    init();

// functions
    function init() {
      // get the rendering area for the canvas
      context = $('#canvas_example')[0].getContext("2d");
      WIDTH = $('#canvas_example').width();
      HEIGHT = $('#canvas_example').height();
      
      loadResourcesThenStart();
    }

    // This part of the program handles the loading of images,
    // which is quite complicated.
    // This is because we must make sure that the images are loaded into memory
    // before we try to draw any of them in the rest of the program.
      
    function loadResourcesThenStart() {
      for (var i = 0; i < imageNames.length; ++i) {
        imgArray[i] = new Image();
        imgArray[i].onload = startInteraction;
        imgArray[i].src = imageNames[i];
      }
    }

    function startInteraction() {
      imageCount++;
      if (imageCount == NUM_IMAGES) {
        // initialise data that depends on images already being loaded
        initBalls();
        // start animation
        nextFrame();
      }
    }
 
    // animation frames
    function nextFrame() {
      requestID = requestAnimationFrame(nextFrame);
      render();
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
    
    // initialise all the balls
    function initBalls() {
      balls = new BallList();           // A data structure to hold the collection of balls
 
      for (i=0; i < NUM_BALLS; ++i) {   // add NUM_BALLS to the data structure for the balls
        var r = 5+Math.random()*25;     // create random values for each attribute
        var sx = Math.random()*WIDTH;
        var sy = Math.random()*HEIGHT;
        var dx = Math.random()*10-5;
        var dy = Math.random()*10-5;
        var red = 0;
        var green = 0;
        var blue = Math.floor(Math.random()*256);  // red, green and blue have to be integer values
                                               // so use Math.floor()
        var alpha = 1;                         // alpha must be in range 0 to 1
        var c = "rgba("+red+","+green+","+blue+","+alpha+")";
        //console.log(c);                      // print debugging info if necessary
        //console.log(sx+", "+sy);
        
        // red, green, blue, alpha, are irrelevant if images are used
        var imgNumber = Math.floor(imgArray.length*Math.random());
        var ball = new Ball(sx,sy,r,dx,dy,c, true, imgArray[imgNumber]);  // create a new Ball object
        balls.add(ball);                       // add the Ball object to the data structure for the balls
      }
    }

    // draw all the balls
    function drawBalls() {
      balls.drawAsImages(context);
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