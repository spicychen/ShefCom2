// main program body
var myButton = document.getElementById('calculate');
myButton.addEventListener('click', area, false);


// functions
function area() {
  var radius = document.getElementById('input_radius').value;
  var area = Math.PI*radius*radius;
  var circumference = 2*Math.PI*radius;
  document.getElementById('output_area').value = area.toFixed(2);
  document.getElementById('output_circumference').value = circumference.toFixed(2);
}
