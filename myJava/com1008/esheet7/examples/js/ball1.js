// Author: Steve Maddock
// Date: 26 November 2015

// variables
var context;                                // This is the rendering link for the canvas.

// main program body
render();

//functions 
function render() {                         // A function to do the drawing. The name starts with a lower case character.
  console.log("render called");
  context = $('#canvas_example')[0].getContext("2d");
	                                          // get the rendering link for the canvas
  // now draw
  context.fillStyle = "rgb(0,0,255)";       // the rgb colour to fill the shape with
  context.beginPath();                      // start defining the things to draw/fill
    context.arc(100, 100, 20, 0, Math.PI*2, true);   // define part of a circle
                                            // Blank spaces after the commas help with readability.
  context.closePath();                      // finish defining the things to draw/fill
  context.fill();                           // now draw/fill what has been defined
}