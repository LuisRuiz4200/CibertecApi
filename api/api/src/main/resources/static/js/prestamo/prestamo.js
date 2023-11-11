

function limpiarCuotas() {

	var table = document.getElementById("tbCuotas").getElementsByTagName('tbody')[0];
	table.innerHTML = '';
}

function cargarCuotas() {
	var table = document.getElementById("tbCuotas").getElementsByTagName('tbody')[0];
	var monto = document.getElementById("montoPrestamo").value;
	var cuotas = document.getElementById("cuotaPrestamo").value;
	
	var montoInteresTotal = document.getElementById("interes").value;
	
	montoInteresTotal = montoInteresTotal.substring(3);
	
	var montoMensual = monto / cuotas;
	var montoInteresMensual = montoInteresTotal / cuotas
	
	var montoCuota = montoMensual + montoInteresMensual;
	
	var fechaPago = new Date(); // Fecha actual
    fechaPago.setMonth(fechaPago.getMonth() + 1); // Sumar un mes

	limpiarCuotas();

	for (let i = 1; cuotas >= i; i++) {

		var nuevaFila = table.insertRow(table.rows.length);

		var celdaCuota = nuevaFila.insertCell(0);
		var celdaMonto = nuevaFila.insertCell(1);
		var celdaFechaPago = nuevaFila.insertCell(2);

		celdaCuota.innerHTML = i;
		celdaMonto.innerHTML = montoCuota;
		
		// Formatear el mes con dos dígitos
        var mm = (fechaPago.getMonth() + 1).toString().padStart(2, '0');
		
		// Formatear la fecha como dd/mm/yyyy
        var dd = fechaPago.getDate();
        var yyyy = fechaPago.getFullYear();
        celdaFechaPago.innerHTML = dd + '/' + mm + '/' + yyyy;

        // Sumar un mes para la próxima cuota
        fechaPago.setMonth(fechaPago.getMonth() + 1);

	}
}

function formularioPrestamo() {

	var btnPrestamo = document.getElementById("btnPrestamo");

	btnPrestamo.addEventListener('click', function(event) {

		limpiarCuotas();

		$("#modalPrestamo").modal('show')

		var id = document.getElementById("idCodigo").value;

		toastr.warning("imprime: " + id);

		var prestatarioPrestamo = document.getElementById("prestatarioPrestamo");
		var rucPrestamo = document.getElementById("rucPrestamo");
		var montoPrestamo = document.getElementById("montoPrestamo");
		var cuotaPrestamo = document.getElementById("cuotaPrestamo");

		fetch("/api/solicitudPrestamo/buscar/" + id)
			.then(response => response.json())
			.then(data => {
				prestatarioPrestamo.value = data.prestatario.prestatario.nombres;
				rucPrestamo.value = data.prestatario.prestatario.ruc;
				montoPrestamo.value = data.monto;
				cuotaPrestamo.value = data.cuotas;

			});



	});



}

formularioPrestamo();



function guardarPrestamo() {

	var montoPrestamo = document.getElementById("montoPrestamo");
	var cuotaPrestamo = document.getElementById("cuotaPrestamo");


	var formularioSolicitud = document.getElementById("idRegistrar");

	var requestBody = JSON.stringify({
		"solicitudPrestamo": {
			"idSolicitudPrestamo": "1"
		},
		"monto": montoPrestamo.value,
		"cuotas": cuotaPrestamo.value,
		"tea": "",
		"tem": "0.20"
	});


	fetch("http://localhost:9090/api/prestamo/guardarPrestamo",
		{
			method: 'POST',
			headers: { "Content-Type": "application/json" },
			body: requestBody,
		})
		.then(response => response.json())
		.then(result => {
			if (result.mensaje) {
				toastr.success(result.mensaje);

				formularioSolicitud.addEventListener('submit', function(event) {
					event.defaultPrevented();
				});
				
				formularioSolicitud.submit();
				
			}else{				
				toastr.error(result.error);
			}
		})
		.catch(error => toastr.error(error));



}
