// A collection of balls
// Author: Steve Maddock
// Date: 26 November 2015


BallList.MAX_NUMBALLS = 500;

function BallList() {
  this.balls = new Array(BallList.MAX_NUMBALLS);
  this.numBalls = 0;
}

BallList.prototype.getNumBalls = function() {
  return this.numBalls;
}

BallList.prototype.add = function(b) {
  if (this.numBalls>=BallList.MAX_NUMBALLS) {
    console.log("too many balls");
    return;
  }
  this.balls[this.numBalls] = b;
  this.numBalls++;
}

BallList.prototype.get = function(i) {
  if (i >= this.numBalls) return 0;
  return this.balls[i];
}

BallList.prototype.draw = function(context) {
  for (var i=0; i<this.numBalls; i++) {
    this.balls[i].draw(context);
  }
}

BallList.prototype.drawAsImages = function(context) {
  for (var i=0; i<this.numBalls; i++) {
    this.balls[i].drawAsImage(context);
  }
}