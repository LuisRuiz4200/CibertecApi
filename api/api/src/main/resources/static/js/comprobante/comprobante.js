function init() {
	asignarSerie();
	consultaDni();
}
init();

async function apiConsultaDocumentoIdentidad(tipoDocumento, numDocumento) {

	var nomReceptor = document.getElementById("nomReceptor");


	await fetch("http://localhost:9090/api/reuzable/consulta/" + tipoDocumento + "/" + numDocumento)
		.then(response => response.json())
		.then(data => {
			if (data.razonSocial) {
				toastr.warning(data.razonSocial);
				nomReceptor.value = data.razonSocial;
				return;
			}
			if (data.nombres) {
				toastr.warning(data.nombres);
				nomReceptor.value = data.nombres + ' ' + data.apellidoPaterno + ' ' + data.apellidoMaterno
				return;
			}
			if (!data.success) {
				toastr.warning(data.message);
				nomReceptor.value = '';
				return;
			}

		});



}

function consultaDni() {

	var numDocReceptor = document.getElementById("numDocReceptor");
	var idTipoDocumento = document.getElementById("idTipoDocumento");

	numDocReceptor.addEventListener('input', function(event) {



		switch (idTipoDocumento.value) {
			case "1":
				if (event.target.value.length === 11) {
					event.target.maxLength = 11;
					apiConsultaDocumentoIdentidad("ruc", numDocReceptor.value);
				}
				break;
			case "2":
				if (event.target.value.length === 8) {
					event.target.maxLength = 8;
					apiConsultaDocumentoIdentidad("dni", numDocReceptor.value);
				}
				break;
		}

	});
}

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

	var listaComprobanteDetalle = document.getElementById("tbItem").getElementsByTagName("tbody")[0];
	var filas = listaComprobanteDetalle.getElementsByTagName("tr");

	var listaDetalles = [];

	for (var i = 0; i < filas.length; i++) {
		var fila = filas[i];
		var celdas = fila.getElementsByTagName("td");

		var item = {
			"idComprobanteDetalle": celdas[0].innerText,
			"codItem": celdas[1].innerText,
			"descripcion": celdas[2].innerText,
			"cantidadItem": celdas[3].innerText,
			"montoItem": celdas[4].innerText,
			"montoTotal": celdas[5].innerText
		};

		listaDetalles.push(item);
	}


	var comprobante = {
		"idTipoComprobante": idTipoComprobante.value,
		"serie": serie.value,
		"correlativo": correlativo.value,
		"fechaEmision": new Date(),
		"rucEmisor": rucEmisor.value,
		"nomEmisor": nomEmisor.value,
		"idTipoDocumento": idTipoDocumento.value,
		"idPrestatario": "32",
		"numDocReceptor": numDocReceptor.value,
		"nomReceptor": nomReceptor.value,
		"serieRef": "",
		"correlativoRef": "",
		"fechaRegistro": new Date(),
		"estado": "Pendiente de Pago",
		"listaComprobanteDetalle": listaDetalles
	};


	var jsonBody = JSON.stringify(comprobante);

	await fetch("http://localhost:9090/api/comprobante/registrar", {
		method: 'post',
		headers: { "content-type": 'application/json' },
		body: jsonBody
	})
		.then(response => response.json())
		.then(data => {
			if (data.mensaje) {
				toastr.success(data.mensaje);
				location.reload();
				return;
			}
			if (data.error) {
				toastr.error(data.error);
				return;
			}
		});
}

function validarFormularioComprobante() {

	var idTipoComprobante = document.getElementById("idTipoComprobante");
	var serie = document.getElementById("serie");
	var correlativo = document.getElementById("correlativo");
	var rucEmisor = document.getElementById("rucEmisor");
	var nomEmisor = document.getElementById("nomEmisor");
	var idTipoDocumento = document.getElementById("idTipoDocumento");
	var numDocReceptor = document.getElementById("numDocReceptor");
	var nomReceptor = document.getElementById("nomReceptor");
	var listaComprobanteDetalle = document.getElementById("tbItem").getElementsByTagName("tbody")[0].getElementsByTagName("tr");



	if (!idTipoComprobante.value) {
		toastr.error('Debe pertenecer a un tipo de comprobante');
		return false;
	}
	if (!serie.value) {
		toastr.error('Debe pertenecer a una serie');
		return false;
	}
	if (!correlativo.value) {
		toastr.error('Debe tener un correlativo');
		return false;
	}
	if (!rucEmisor.value) {
		toastr.error('Debe consignar el ruc del emisor');
		return false;
	}
	if (!nomEmisor.value) {
		toastr.error('Debe consignar el nombre del emisor');
		return false;
	}
	if (!idTipoDocumento.value) {
		toastr.error('Debe especificar el tipo de documento del receptor');
		return false;
	}

	if (idTipoDocumento.value === "1") {
		if (numDocReceptor.value.length != 11) {
			toastr.error('El RUC debe contener 11 digitos');
			return false;
		}
	}
	if (idTipoDocumento.value === "2") {
		if (numDocReceptor.value.length != 8) {
			toastr.error('El DNI debe contener 8 digitos');
			return false;
		}
	}

	if (!numDocReceptor.value) {
		toastr.error('Debe consignar el número de documento del receptor');
		return false;
	}
	if (!nomReceptor.value) {
		toastr.error('Debe consignar el nombre del receptor');
		return false;
	}
	if (listaComprobanteDetalle.length <= 0) {
		toastr.error('Debe registrar alemnos un item');
		return false;
	}

	return true;
}

function guardarComprobante() {


	const formularioComprobante = document.getElementById("formularioComprobante");


	if (validarFormularioComprobante()) {
		formularioComprobante.onsubmit();
	}


}

function asignarSerie() {
	var tipoComprobante = document.getElementById("idTipoComprobante");
	var tipoDocumento = document.getElementById("idTipoDocumento");
	var numDocReceptor = document.getElementById("numDocReceptor")
	var lblTipoDocumento = document.getElementById("lblTipoDocumento");
	var serie = document.getElementById("serie");

	numDocReceptor.disabled = true;
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
				numDocReceptor.disabled = false;
				break;
			case "2":
				lblTipoDocumento.innerHTML = 'DNI DEL RECEPTOR';
				numDocReceptor.disabled = false;
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

	$("#modalNuevoItem").modal('hide');
}


function limpiarTablaItem() {

	var tbItem = document.getElementById("tbItem").getElementsByTagName("tbody")[0];

	tbItem.innerHTML = '';

}
