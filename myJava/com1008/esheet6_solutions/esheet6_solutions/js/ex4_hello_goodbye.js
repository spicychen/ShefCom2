
// main program
var hello = document.getElementById('button_hello');
console.log(hello);
hello.addEventListener('click', addHello, false);
var goodbye = document.getElementById('button_goodbye');
console.log(goodbye);
goodbye.addEventListener('click', addGoodbye, false);


// functions
function addHello() {
  var h = document.getElementById('div_hello');
  var p = document.createElement("p");
  var textNode = document.createTextNode('Hello');
  p.appendChild(textNode);
  h.appendChild(p);
}

function addGoodbye() {
  var h = document.getElementById('div_goodbye');
  var p = document.createElement("p");
  var textNode = document.createTextNode('Goodbye');
  p.appendChild(textNode);
  h.appendChild(p);
}
