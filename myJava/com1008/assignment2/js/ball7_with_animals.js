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
    var MISSED_LEVEL;
    var rightDown = false;
    var leftDown = false;
    var canvasMinX = 0;
    var canvasMaxX = 0;

    // image loading variables
    // bunny image by K. Whiteford, CC0 1.0,
    // http://www.publicdomainpictures.net/view-image.php?image=35533&picture=brown-bunny-illustration&large=1
    // gray cat image by K. Whiteford, CC0 1.0,
    // http://www.publicdomainpictures.net/view-image.php?image=56845&picture=gray-cat-illustration

    var imageBaseStr = "images/";
    var imagePaddleStr = imageBaseStr + "bouncer.png";
    var imageCount = 0;
    var imageNames = [imageBaseStr+"bunny1.png", imageBaseStr+"cat1.png"];
    var NUM_IMAGES = imageNames.length+1;
    var imgArray = new Array(imageNames.length);
    var imgPaddle = new Image();
    
    // main program body
    init();
    
    //functions
    
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

    // draw grass
    function drawGrass() {
      context.fillStyle = "green";
      context.beginPath();
        context.rect(0, HEIGHT-MISSED_LEVEL, WIDTH, MISSED_LEVEL);
      context.closePath();
      context.fill();
    }
    
    // paddle functions
    // initialise the paddle
    function initPaddle() {
      paddleh = 30;
      paddlew = 70;
      paddlex = WIDTH/2;
      paddley = HEIGHT-paddleh;
      MISSED_LEVEL = paddleh*0.85;
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
      //context.fillStyle = "black";
      //context.beginPath();
      //  context.rect(paddlex, paddley, paddlew, paddleh);
      //context.closePath();
      //context.fill();
      context.drawImage(imgPaddle, paddlex, paddley);
    }
   
    // Balls functions
    // initialise all the balls
    function initBalls(img) {
      balls = new BallList();
      for (i=0; i < NUM_BALLS; ++i) {
        var r = 10+Math.random()*10;
        var sx = Math.random()*WIDTH;
        var sy = Math.random()*HEIGHT/8;
        var dx = Math.random()-0.5;
        var dy = 0.5+Math.random();
        var red = 0;
        var green = 0;
        var blue = 255;  
        var alpha = 1;
        var c = "rgba("+red+","+green+","+blue+","+alpha+")";
        //console.log(c);
        //console.log(sx+", "+sy);
        // red, green, blue, alpha, are irrelevant if images are used
        var imgNumber = Math.floor(imgArray.length*Math.random());
        var ball = new Ball(sx,sy,r,dx,dy,c, true, imgArray[imgNumber]);  // create a new Ball object
        balls.add(ball);
      }
    }      

    // reaction when paddle has hit ball
    function doSomethingHitBall(b) {
      console.log("hit ball "+b);
      $('#message_area').html($('#message_area').html()+'<span>hit ball '+b+', </span>');
    }
    
    // reaction when paddle misses ball
    function doSomethingMissedBall(b) {
      console.log("missed ball "+b);
      $('#message_area').html($('#message_area').html()+'<span>missed ball '+b+', </span>');
      var count = 0;
      for (var i=0; i<balls.getNumBalls(); ++i) {
        if (balls.get(i).getVisible())
          count++;
      }
      if (count==0) {
        $('#message_area').html($('#message_area').html()+'<span>game over: no balls, </span>');
        console.log("Game over: no balls");
        cancelAnimationFrame(requestID);
      }
    }
  
    function handleShot() {
      $('#message_area').html($('#message_area').html()+'<span>handle shot, </span>');
      console.log('Handle shot');
    }
    
    // update the positions of all the balls
    function updateBalls() {
      for (var i = 0; i < balls.getNumBalls(); ++i) {
        var b = balls.get(i);
        if (b.getVisible()) {
          var bxNext = b.getX() + b.getDX();
          if (bxNext > WIDTH || bxNext < 0) {
            b.setDX(-b.getDX());
          }
          var byNext = b.getY() + b.getDY();
          if (byNext < 0) {
            b.setDY(-b.getDY());
          }
          else if (byNext > HEIGHT-MISSED_LEVEL) {
            // check if ball is in paddle x range
            var bx = b.getX(); 
            if (bx > paddlex && bx < paddlex + paddlew) {           
              b.setDY(-b.getDY());
              doSomethingHitBall(i);
            }  
            else {
              b.setVisible(false);
              doSomethingMissedBall(i);
            }
          }
        }
      }    
      for (var i = 0; i < balls.getNumBalls(); ++i) {
        var ball = balls.get(i);
        ball.setX( ball.getX() + ball.getDX() );
        ball.setY( ball.getY() + ball.getDY() );
      }
    }    
  
    // draw the balls
    function drawBalls() {
      balls.drawAsImages(context);
    }
    
    // render the screen display
    function render() {
      clear();
      drawGrass();
      updatePaddle();
      drawPaddle();      
      drawBalls();
      updateBalls();
    }
    
    // animation frames
    function nextFrame() {
      requestID = requestAnimationFrame(nextFrame);
      render();
    }
    
    // This part of the program handles the loading of images,
    // which is quite complicated.
    // This is because we must make sure that the images are loaded into memory
    // before we try to draw any of them in the rest of the program.
    
    function startInteraction() {
      imageCount++;
      if (imageCount == NUM_IMAGES) {
        initBalls();
        initPaddle();
        // start animation
        nextFrame();
      }
    }   
    
    // Need to make sure images are same size as ball and paddle
    function loadResourcesThenStart() {
      for (var i = 0; i < imageNames.length; ++i) {
        imgArray[i] = new Image();
        imgArray[i].onload = startInteraction;
        imgArray[i].src = imageNames[i];
      }
      imgPaddle.onload = startInteraction;
      imgPaddle.src = imagePaddleStr;
    } 

    // initialise the simulation
    function init() {
      console.log("running...");
      <!-- get the rendering area for the canvas -->
      context = $('#canvas_example')[0].getContext("2d");
      WIDTH = $('#canvas_example').width();
      HEIGHT = $('#canvas_example').height();
      canvasMinX = $('#canvas_example').offset().left;
      canvasMaxX = canvasMinX + WIDTH;

      $(document).keydown(onKeyDown);
      $(document).keyup(onKeyUp);
      $(document).mousemove(onMouseMove);
    
      loadResourcesThenStart();
    }