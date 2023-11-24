function init() {
	asignarSerie();
}
init();

async function apiGuardarComprobante() {


	var idTipoComprobante = document.getElementById("idTipoComprobante");
	var serie = document.getElementById("serie");
	var fechaEmision = document.getElementById("fechaEmision");
	var correlativo = document.getElementById("correlativo");
	var rucEmisor = document.getElementById("rucEmisor");
	var nomEmisor = document.getElementById("nomEmisor");
	var idTipoDocumento = document.getElementById("idTipoDocumento");
	var idPrestatario = document.getElementById("idPrestatario");
	var numDocReceptor = document.getElementById("numDocReceptor");
	var nomReceptor = document.getElementById("nomReceptor");
	var serieRef = document.getElementById("serieRef");
	var correlativoRef = document.getElementById("correlativoRef");
	var fechaRegistro = document.getElementById("fechaRegistro");
	var estado = document.getElementById("estado");
	var listaComprobanteDetalle = document.getElementById("tbItem");

	var comprobante = {
		"idTipoComprobante": idTipoComprobante.value,
		"serie": serie.value,
		"correlativo": correlativo.value,
		"fechaEmision":"2023-11-23",
		"rucEmisor": rucEmisor.value,
		"nomEmisor": nomEmisor.value,
		"idTipoDocumento": idTipoDocumento.value,
		"idPrestatario": "32",
		"numDocReceptor": numDocReceptor.value,
		"nomReceptor": nomReceptor.value,
		"serieRef": "",
		"correlativoRef": "",
		"fechaRegistro": "2023-11-23",
		"estado": "Pendiente de Pago",
		"listaComprobanteDetalle": []
	};


	var jsonBody = JSON.stringify(comprobante);

	await fetch("http://localhost:9090/api/comprobante/registrar", {
		method: 'post',
		headers: {"content-type":'application/json'},
		body: jsonBody
	})
		.then(response => response.json())
		.then(data => {
			if (data.mensaje) {
				toastr.success(data.mensaje)
				return;
			}
			if (data.error) {
				toastr.error(data.error)
				return;
			}
		});
}

function guardarComprobante() {

	var formularioComprobante = document.getElementById("formularioComprobante");
	
	var idTipoComprobante = document.getElementById("idTipoComprobante");
	var serie = document.getElementById("serie");
	var correlativo = document.getElementById("correlativo");
	var fechaEmision = document.getElementById("fechaEmision");
	var rucEmisor = document.getElementById("rucEmisor");
	var nomEmisor = document.getElementById("nomEmisor");
	var idTipoDocumento = document.getElementById("idTipoDocumento");
	var idPrestatario = document.getElementById("idPrestatario");
	var numDocReceptor = document.getElementById("numDocReceptor");
	var nomReceptor = document.getElementById("nomReceptor");

	formularioComprobante.addEventListener('submit', function(event) {


		if (!idTipoComprobante.value) {
			toastr.error('Debe pertenecer a un tipo de comprobante');
			return;
		}
		if (!serie.value) {
			toastr.error('Debe pertenecer a una serie');
			return;
		}
		if (!correlativo.value) {
			toastr.error('Debe tener un correlativo');
			return;
		}
		if (!rucEmisor.value) {
			toastr.error('Debe consignar el ruc del emisor');
			return;
		}
		if (!nomEmisor.value) {
			toastr.error('Debe consignar el nombre del emisor');
			return;
		}
		if (!idTipoDocumento.value) {
			toastr.error('Debe especificar el tipo de documento del receptor');
			return;
		}
		if (!numDocReceptor.value) {
			toastr.error('Debe consignar el número de documento del receptor');
			return;
		}
		if (!nomReceptor.value) {
			toastr.error('Debe consignar el nombre del receptor');
			return;
		}
		
		
		apiGuardarComprobante();
		
	});


}


function asignarSerie() {
	var tipoComprobante = document.getElementById("idTipoComprobante");
	var tipoDocumento = document.getElementById("idTipoDocumento");
	var lblTipoDocumento = document.getElementById("lblTipoDocumento");
	var serie = document.getElementById("serie");

	serie.disabled = true;

	tipoComprobante.addEventListener('change', function(event) {
		switch (tipoComprobante.value) {
			case "1":
				serie.value = 'B001';
				break;
			case "2":
				serie.value = 'F001';
				break;
			case "3":
				serie.value = 'NC01';
				break;
			case "4":
				serie.value = 'ND01';
				break;
		}
	});
	tipoDocumento.addEventListener('change', function(event) {
		switch (tipoDocumento.value) {
			case "1":
				lblTipoDocumento.innerHTML = 'RUC DEL RECEPTOR';
				break;
			case "2":
				lblTipoDocumento.innerHTML = 'DNI DEL RECEPTOR';
				break;
		}
	});

}

function mostrarModalNuevoItem() {

	$("#modalNuevoItem").modal('show')

}

function agregarItem() {

	var tbItem = document.getElementById("tbItem").getElementsByTagName("tbody")[0];

	var modalCodItem = document.getElementById("idModalCodItem");
	var modalDescripcion = document.getElementById("idModalDescripcion");
	var modalMontoItem = document.getElementById("idModalMontoItem");
	var modalCantidadItem = document.getElementById("idModalCantidadItem");

	if (!modalCodItem.value || modalCodItem.value <= 0) {
		toastr.error('Debe ingresar un código');
		return;
	}
	if (!modalDescripcion.value) {
		toastr.error('Debe ingresar una descripción');
		return;
	}
	if (!modalMontoItem.value || modalMontoItem.value <= 0) {
		toastr.error('Debe ingresar un monto mayor a 0');
		return;
	}
	if (!modalCantidadItem.value || modalMontoItem.value <= 0) {
		toastr.error('Debe ingresar una cantidad mayor a 0');
		return;
	}

	var nuevaCelda = tbItem.insertRow();

	var celdaNroItem = nuevaCelda.insertCell(0);
	var celdaCodItem = nuevaCelda.insertCell(1);
	var celdaDescripcion = nuevaCelda.insertCell(2);
	var celdaCantidad = nuevaCelda.insertCell(3);
	var celdaMontoItem = nuevaCelda.insertCell(4);
	var celdaMontoTotal = nuevaCelda.insertCell(5);



	celdaNroItem.innerHTML = tbItem.rows.length;
	celdaCodItem.innerHTML = modalCodItem.value;
	celdaDescripcion.innerHTML = modalDescripcion.value;
	celdaCantidad.innerHTML = modalCantidadItem.value;
	celdaMontoItem.innerHTML = modalMontoItem.value;
	celdaMontoTotal.innerHTML = modalCantidadItem.value + modalMontoItem.value;

	modalCodItem.value = '';
	modalDescripcion.value = '';
	modalCantidadItem.value = '';
	modalMontoItem.value = '';


}


function limpiarTablaItem() {

	var tbItem = document.getElementById("tbItem").getElementsByTagName("tbody")[0];

	tbItem.innerHTML = '';

}
