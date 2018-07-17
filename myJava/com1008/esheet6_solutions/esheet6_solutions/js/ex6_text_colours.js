// main program body
var b = document.getElementById('button_red');
b.addEventListener('click', makeRed, false);
b = document.getElementById('button_blue');
b.addEventListener('click', makeBlue, false);

// functions
function makeRed() {
  var p = document.getElementById('paragraph');
  console.log(p);
  console.log(p.className);
  p.className = "red";
}

function makeBlue() {
  var p = document.getElementById('paragraph');
  console.log(p);
  console.log(p.className);
  p.className += " blue";
}
