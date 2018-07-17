// main program body
var hello = document.getElementById('button_hello');
console.log(hello);
hello.addEventListener('click', addHello, false);

// functions
function addHello() {
  var h = document.getElementById('div_hello');
  var p = document.createElement("p");
  var textNode = document.createTextNode('Hello');
  p.appendChild(textNode);
  h.appendChild(p);
}

