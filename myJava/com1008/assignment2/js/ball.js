// A single Ball
// Author: Steve Maddock
// Date: 26 November 2015

function Ball(x,y,r,dx,dy,c,visible,img) {
  this.x = x;
  this.y = y;
  this.r = r;
  this.dx = dx;
  this.dy = dy;
  this.c = c;
  this.visible = visible || true;
  this.image = img || null;
}

Ball.prototype.draw = function(context) {
  if (this.visible) {
    context.fillStyle = this.c;
    context.beginPath();
    context.arc(this.x,this.y,this.r, 0, Math.PI*2, true);
    context.closePath();
    context.fill();
  }
}

Ball.prototype.drawAsImage = function(context) {
  if (this.visible) {
    if (this.image!=null) {
      context.drawImage(this.image, this.x-this.r, this.y-this.r, this.r*2, this.r*2);
    }
    else this.draw();
  }
}

Ball.prototype.setX = function(x) {
  this.x = x;
}

Ball.prototype.setY = function(y) {
  this.y = y;
}

Ball.prototype.setR = function(r) {
  this.r = r;
}

Ball.prototype.setDX = function(dx) {
  this.dx = dx;
}

Ball.prototype.setDY = function(dy) {
  this.dy = dy;
}

Ball.prototype.setColor = function(c) {
  this.c = c;
}

Ball.prototype.setVisible = function(v) {
  this.visible = v;
}
  
Ball.prototype.getX = function() {
  return this.x;
}

Ball.prototype.getY = function() {
  return this.y;
}

Ball.prototype.getR = function() {
  return this.r;
}

Ball.prototype.getDX = function() {
  return this.dx;
}

Ball.prototype.getDY = function() {
  return this.dy;
}

Ball.prototype.getColour = function() {
  return this.c;
}

Ball.prototype.getVisible = function() {
  return this.visible;
}
