function validarFormulario(){
	var formulario = document.getElementById("idRegistrar");
	var monto = document.getElementById("monto").value;
	var meses = document.getElementById("meses").value;
	var interes = document.getElementById("interes").value;
	var fechaPrestamo = document.getElementById("fecha").value;
	var numeroCuenta = document.getElementById("idObtenemosCuenta").value;
	
	formulario.addEventListener('submit', (event) => {
		
		
		event.preventDefault();
			
		if (monto === "0.0") {
			toastr.error('Falta completar el campo monto ' + monto);
			return;
		}
		if (meses === "0") {
			toastr.error('Falta completar el campo meses');
			return;
		}
		if (interes === "0.0") {
			toastr.error('El monto de interes no se pudo calcular');
			return;
		}
		if (!fechaPrestamo) {
			toastr.error('La fecha no esta asignada');
			return;
		}
		if (!numeroCuenta) {
			toastr.error('Numero de cuenta no asignada');
			return;
		}
		
		formulario.submit();
	});

}

validarFormulario();