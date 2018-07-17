function real(){
	var r = document.getElementById("some");
	console.log(r);
	r.className = "u";
}
var el = document.getElementById('reveal');
console.log(el);
el.addEventListener('click', real, false);