

function limpiarCuotas() {

	var table = document.getElementById("tbCuotas").getElementsByTagName('tbody')[0];
	table.innerHTML = '';
}

function cargarCuotas() {
	var table = document.getElementById("tbCuotas").getElementsByTagName('tbody')[0];
	var monto = document.getElementById("montoPrestamo").value;
	var cuotas = document.getElementById("cuotaPrestamo").value;

	var montoPorCuota = monto / cuotas;

	limpiarCuotas();

	for (let i = 1; cuotas >= i; i++) {

		var nuevaFila = table.insertRow(table.rows.length);

		var celdaCuota = nuevaFila.insertCell(0);
		var celdaMonto = nuevaFila.insertCell(1);
		var celdaFechaPago = nuevaFila.insertCell(2);

		celdaCuota.innerHTML = i;
		celdaMonto.innerHTML = montoPorCuota;
		celdaFechaPago.innerHtml = "";

	}
}

function buscarSolicitudPrestamo() {


	var prestatarioPrestamo = document.getElementById("prestatarioPrestamo");
	var rucPrestamo = document.getElementById("rucPrestamo");
	var montoPrestamo = document.getElementById("montoPrestamo");
	var cuotaPrestamo = document.getElementById("cuotaPrestamo");

	var id = 1;

	fetch("/api/solicitudPrestamo/buscar/" + id)
		.then(response => response.json())
		.then(data => {
			prestatarioPrestamo.value = data.prestatario.prestatario.nombres;
			rucPrestamo.value = data.prestatario.prestatario.ruc;
			montoPrestamo.value = data.monto;
			cuotaPrestamo.value = data.cuotas;

		});
}

buscarSolicitudPrestamo();


function guardarPrestamo() {

	var montoPrestamo = document.getElementById("montoPrestamo");
	var cuotaPrestamo = document.getElementById("cuotaPrestamo");

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
			method:'POST',
			headers:{"Content-Type": "application/json"},
			body:requestBody,
		})
		.then(response => response.json())
		.then(result => toastr.success(result.mensaje))
		.catch(error => toastr.error(error));

}
