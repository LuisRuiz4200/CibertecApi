
function validarDniExiste(){
	
	var dni = document.getElementById("dni").value;
	var formulario=document.getElementById("guardarPrestamista");
	
	fetch("/api/prestamista/validarDniExiste/" + dni,{
		method:"GET",
		body:"",
		redirect:"follow"
	})
	.then(response=>{
		toastr.warning(response.json());
		alert(response.json());
	});
	
	
}