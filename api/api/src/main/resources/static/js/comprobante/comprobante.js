function init() {
	asignarSerie();
	consultaDni();
}
init();

async function apiListaComprobante(idPrestamo, idCuotaPrestamo) {

	const url = new URL("http://localhost:9090/api/comprobante/listar");
	url.searchParams.append("idPrestamo", idPrestamo);
	url.searchParams.append("idCuotaPrestamo", idCuotaPrestamo);

	return fetch(url)
		.then(response => response.json());
}

async function apiListaCuotaPorPrestatario(idPrestatario, fechaInicioCuota, fechaFinCuota) {

	const url = new URL("http://localhost:9090/api/prestamo/listaCuotaPorPrestatario");

	url.searchParams.append("idPrestatario", idPrestatario);
	url.searchParams.append("fechaInicioCuota", fechaInicioCuota);
	url.searchParams.append("fechaFinCuota", fechaFinCuota);

	return fetch(url)
		.then(response => response.json())
		.then(data => {
			toastr.success(data[0].prestamo.solicitudPrestamo.prestatario.prestatario.nombres);
			return data;
		});
}

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


async function apiGuardarComprobante() {

	var params = new URLSearchParams(location.search);
	var idPrestamo = params.get("idPrestamo");

	var idTipoComprobante = document.getElementById("idTipoComprobante");
	var serie = document.getElementById("serie");
	var correlativo = document.getElementById("correlativo");
	var rucEmisor = document.getElementById("rucEmisor");
	var nomEmisor = document.getElementById("nomEmisor");
	var idTipoDocumento = document.getElementById("idTipoDocumento");
	var numDocReceptor = document.getElementById("numDocReceptor");
	var nomReceptor = document.getElementById("nomReceptor");

	var listaComprobanteDetalle = document.getElementById("tbItem").getElementsByTagName("tbody")[0];
	var filas = listaComprobanteDetalle.getElementsByTagName("tr");

	var listaDetalles = [];

	for (var i = 0; i < filas.length; i++) {
		var fila = filas[i];
		var celdas = fila.getElementsByTagName("td");

		var item = {
			"idComprobanteDetalle": celdas[0].innerText,
			"idCuotaPrestamo": celdas[1].innerText.replace(/.*[^0-9]/, ''),
			"codItem": celdas[1].innerText,
			"descripcion": celdas[2].innerText,
			"cantidadItem": celdas[3].innerText,
			"montoItem": celdas[4].innerText,
			"montoMora": celdas[5].innerText,
			"montoTotal": celdas[6].innerText
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
		"idPrestatario": 32,
		"idTipoDocumento": idTipoDocumento.value,
		"numDocReceptor": numDocReceptor.value,
		"idPrestamo": idPrestamo,
		"nomReceptor": nomReceptor.value,
		"serieRef": "",
		"correlativoRef": "",
		"fechaRegistro": new Date(),
		"estado": "EMITIDO",
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

				correlativo.value = data.detalle.correlativo;

				setTimeout(() => {
					location.href = "http://localhost:9090/web/comprobante/registrar";
				}, 2000)
				return;
			}
			if (data.error) {
				toastr.error(data.error);
				return;
			}
		});
}

async function apiBuscarCuotaPrestamo(idPrestamo, idCuotaPrestamo) {

	var url = new URL("http://localhost:9090/api/prestamo/buscar/cuotaPrestamo");
	url.searchParams.append("idPrestamo", idPrestamo);
	url.searchParams.append("idCuotaPrestamo", idCuotaPrestamo);

	var params = new URLSearchParams();
	params.append("idPrestamo", idPrestamo);
	params.append("idCuotaPrestamo", idCuotaPrestamo);

	return fetch(url)
		.then(response => response.json())
		.then(data => {
			return data;
		});

}


function cargarWebComprobante(idPrestamo, idCuota) {

	var btnCargarComprobante = document.getElementById("btnCargarComprobante");
	var modalEstadoCuota = document.getElementById("modalEstadoCuota");

	btnCargarComprobante.addEventListener('click', function(event) {

		var url = new URL("http://localhost:9090/web/comprobante/registrar/prestamo");
		url.searchParams.append("idPrestamo", idPrestamo);
		url.searchParams.append("idCuotaPrestamo", idCuota);

		if (modalEstadoCuota.value === 'Pagado') {
			toastr.error("No es posible acceder a pagar una cuota con estado pagado")
			return;
		}

		location.href = url;

	});

}


async function mostrarModalDetallePago(enlace, idPrestamo, idCuotaPrestamo) {

	var tbComprobante = document.getElementById("modalTbComprobante").getElementsByTagName("tbody")[0];
	var modalEstadoCuota = document.getElementById("modalEstadoCuota");
	tbComprobante.innerHTML = '';

	var fila = enlace.parentNode.parentNode;
	var celdaEstadoCuenta = fila.cells[5].innerText;

	var tituloModalDetallePago = document.getElementById("tituloModalDetallePago");
	tituloModalDetallePago.innerText = "DETALLE DE PAGO PARA EL PRESTAMO #" + idPrestamo + " CUOTA #" + idCuotaPrestamo;

	var listaComprobantePorPrestamo = await apiListaComprobante(idPrestamo, idCuotaPrestamo);

	modalEstadoCuota.value = celdaEstadoCuenta;

	for (var comprobante of listaComprobantePorPrestamo) {

		for (var item of comprobante.listaComprobanteDetalle) {
			var fila = tbComprobante.insertRow();
			fila.insertCell(0).innerText = comprobante.serie;
			fila.insertCell(1).innerText = comprobante.correlativo;
			fila.insertCell(2).innerText = item.descripcion;
			fila.insertCell(3).innerText = 'S/. ' + item.montoItem.toFixed(2);
			fila.insertCell(4).innerText = item.montoMora === null ? 'S/. 0.00' : 'S/. ' + item.montoMora.toFixed(2);
			fila.insertCell(5).innerText = 'S/. ' + item.montoTotal.toFixed(2);
			fila.insertCell(6).innerText = comprobante.fechaEmision;
		}
	}


	$("#modalDetallePago").modal("show");



	cargarWebComprobante(idPrestamo, idCuotaPrestamo);


}

async function listarCuotaPorPrestatario() {

	var tbPrestamo = document.getElementById("tbCuotaPorPrestatario").getElementsByTagName("tbody")[0];

	var idPrestatario = document.getElementById("filtroIdPrestatario");
	var fechaInicioCuota = document.getElementById("filtoFechaInicioCuota");
	var fechaFinCuota = document.getElementById("filtroFechaFinCuota");

	tbPrestamo.innerHTML = '';

	var listaCuotaPorPrestatario = await apiListaCuotaPorPrestatario(idPrestatario.value, fechaInicioCuota.value, fechaFinCuota.value);


	for (var cuota of listaCuotaPorPrestatario) {

		var fila = tbPrestamo.insertRow();

		var nombres = cuota.prestamo.solicitudPrestamo.prestatario.prestatario.nombres;
		var apellidos = cuota.prestamo.solicitudPrestamo.prestatario.prestatario.apellidos;

		fila.insertCell(0).innerText = cuota.prestamo.idPrestamo;
		fila.insertCell(1).innerText = nombres + ' ' + apellidos;
		fila.insertCell(2).innerText = cuota.cuotaPrestamoPk.idCuotaPrestamo;
		fila.insertCell(3).innerText = "S/." + cuota.montoTotal.toFixed(2);
		fila.insertCell(4).innerText = cuota.fechaPago;
		fila.insertCell(5).innerText = cuota.estado;

		var idPrestamo = fila.cells[0].innerText;
		var idCuotaPrestamo = fila.cells[2].innerText;

		fila.insertCell(6).innerHTML = "<button class='btn btn-primary btn-sm' onclick='mostrarModalDetallePago(this," + idPrestamo + ',' + idCuotaPrestamo + ")'>PAGOS</button>";

		var condicionEstado = fila.cells[5];

		switch (condicionEstado.innerText) {
			case "Pendiente":
				condicionEstado.classList = 'text-bg-primary';
				break;
			case "Pagado":
				condicionEstado.classList = 'text-bg-success';
				break;
			case "Pago Parcial":
				condicionEstado.classList = 'text-bg-warning';
				break;
		}

	}

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
		setTimeout(2000, () => {

			location.href = "http://localhost:9090/web/comprobante/listaCuotaPorPrestamo";
		})
	}


}

function limpiarFormularioComprobante() {

	var numDocReceptor = document.getElementById("numDocReceptor");
	var nomReceptor = document.getElementById("nomReceptor");
	var lblTipoDocumento = document.getElementById("lblTipoDocumento");
	var correlativo = document.getElementById("correlativo");


	numDocReceptor.value = '';
	nomReceptor.value = '';
	correlativo.value = '';
	lblTipoDocumento.innerHTML = 'DOCUMENTO RECEPTOR';


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
				limpiarFormularioComprobante();
				limpiarTablaItem();
				serie.value = 'B001';
				break;
			case "2":
				limpiarFormularioComprobante();
				limpiarTablaItem();
				serie.value = 'F001';
				break;
			case "3":
				limpiarFormularioComprobante();
				limpiarTablaItem();
				serie.value = 'ND01';
				break;
			case "4":
				limpiarFormularioComprobante();
				limpiarTablaItem();
				serie.value = 'NC01';
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

function limpiarModalItem() {

	var indicadorPagoParcial = document.getElementById("chckPagoParcial");
	var modalDescripcion = document.getElementById("idModalDescripcion");
	var modalCantidadItem = document.getElementById("idModalCantidadItem");
	var modalMontoItem = document.getElementById("idModalMontoItem");
	var modalMontoMora = document.getElementById("idModalMontoMora");

	modalDescripcion.value = '';
	modalCantidadItem.value = '';
	modalMontoItem.value = '';
	modalMontoMora.value = '';
	modalMontoItem.readOnly = true;
	indicadorPagoParcial.checked = false;
}

function cargarCuotaPrestamoModal() {
	var idPrestamo = new URLSearchParams(location.search).get("idPrestamo");
	var indicadorPagoParcial = document.getElementById("chckPagoParcial");
	var modalDescripcion = document.getElementById("idModalDescripcion");
	var modalCantidadItem = document.getElementById("idModalCantidadItem");
	var modalMontoItem = document.getElementById("idModalMontoItem");
	var modalFechaPago = document.getElementById("idModalFechaPago");


	idModalCodItem.addEventListener('change', async function(event) {

		limpiarModalItem();

		var codItem = idModalCodItem.value;
		var idCuotaPrestamo = codItem.replace(/.*[^0-9]/, "");

		var cuotaPrestamo = await apiBuscarCuotaPrestamo(idPrestamo, idCuotaPrestamo);

		modalDescripcion.value = "PAGO COMPLETO DE LA CUOTA NRO " + idCuotaPrestamo;
		modalCantidadItem.value = "1";
		modalMontoItem.readOnly = true;
		modalMontoItem.value = cuotaPrestamo.montoTotal.toFixed(2);
		modalFechaPago.value = cuotaPrestamo.fechaPago;

		toastr.warning(cuotaPrestamo.fechaPago);

	});

	indicadorPagoParcial.addEventListener('change', function(event) {

		var codItem = idModalCodItem.value;
		var idCuotaPrestamo = codItem.replace(/.*[^0-9]/, "");

		toastr.warning(indicadorPagoParcial.checked ? "ok" : "no ok");

		if (event.target.checked) {
			modalDescripcion.value = "PAGO PARCIAL DE LA CUOTA NRO " + idCuotaPrestamo;
			modalMontoItem.readOnly = false;
		} else {
			modalDescripcion.value = "PAGO COMPLETO DE LA CUOTA NRO " + idCuotaPrestamo;
			modalMontoItem.readOnly = true;
		}


	});
}

function agregarItem() {

	var modalTituloItem = document.getElementById("modalTituloItem");
	var idModalCodItem = document.getElementById("idModalCodItem");
	var btnAgregarItemModal = document.getElementById("btnAgregarItemModal");
	var btnEditarItemModal= document.getElementById("btnEditarItemModal");

	modalTituloItem.innerText = "NUEVO ITEM";
	idModalCodItem.value = '-1';

	cargarCuotaPrestamoModal();

	btnAgregarItemModal.hidden = false;
	btnEditarItemModal.hidden = true;
	
	limpiarModalItem();
	$("#modalNuevoItem").modal('show')
	
}

function editarItem(enlace) {

	cargarCuotaPrestamoModal();


	var modalCodItem = document.getElementById("idModalCodItem");
	var modalDescripcion = document.getElementById("idModalDescripcion");
	var modalCantidadItem = document.getElementById("idModalCantidadItem");
	var modalMontoItem = document.getElementById("idModalMontoItem");
	var modalMontoMora = document.getElementById("idModalMontoMora");
	var modalTituloItem = document.getElementById("modalTituloItem");
	var btnAgregarItemModal = document.getElementById("btnAgregarItemModal");
	var btnEditarItemModal = document.getElementById("btnEditarItemModal");

	var fila = enlace.parentNode.parentNode;

	var idItem = fila.cells[0].innerText;
	var codItem = fila.cells[1].innerText;
	var descripcion = fila.cells[2].innerText;
	var cantidad = fila.cells[3].innerText;
	var montoItem = fila.cells[4].innerText;
	var montoMora = fila.cells[5].innerText;


	modalCodItem.value = codItem;
	modalDescripcion.value = descripcion;
	modalCantidadItem.value = cantidad;
	modalMontoItem.value = montoItem;
	modalMontoMora.value = montoMora;
	modalTituloItem.innerText = "EDICION DEL ITEM " + fila.cells[0].innerText;


	btnAgregarItemModal.hidden = true;
	btnEditarItemModal.hidden = false;

	$("#modalNuevoItem").modal('show');

	toastr.warning(idItem);

}


function guardarItem(crud) {

	var tbItem = document.getElementById("tbItem").getElementsByTagName("tbody")[0];

	var modalCodItem = document.getElementById("idModalCodItem");
	var modalDescripcion = document.getElementById("idModalDescripcion");
	var modalMontoItem = document.getElementById("idModalMontoItem");
	var modalCantidadItem = document.getElementById("idModalCantidadItem");
	var indicadorPagoParcial = document.getElementById("chckPagoParcial");
	var modalMontoMora = document.getElementById("idModalMontoMora");
	var modalTituloItem = document.getElementById("modalTituloItem");

	if (crud === 'editar') {

		var nroFila = modalTituloItem.innerText.replace(/.*[^0-9]/, "")

		var fila = tbItem.rows[nroFila - 1];

		fila.cells[1].innerHTML = modalCodItem.value;
		fila.cells[2].innerHTML = modalDescripcion.value;
		fila.cells[3].innerHTML = modalCantidadItem.value;
		fila.cells[4].innerHTML = modalMontoItem.value;
		fila.cells[5].innerHTML = modalMontoMora.value;
		fila.cells[6].innerHTML = modalCantidadItem.value * modalMontoItem.value;


		$("#modalNuevoItem").modal('hide');

		return;
	}

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
	var celdaMontoMora = nuevaCelda.insertCell(5);
	var celdaMontoTotal = nuevaCelda.insertCell(6);
	var celdaDetalle = nuevaCelda.insertCell(7);



	celdaNroItem.innerHTML = tbItem.rows.length;
	celdaCodItem.innerHTML = modalCodItem.value;
	celdaDescripcion.innerHTML = modalDescripcion.value;
	celdaCantidad.innerHTML = modalCantidadItem.value;
	celdaMontoItem.innerHTML = modalMontoItem.value;
	celdaMontoMora.innerHTML = modalMontoMora.value;
	celdaMontoTotal.innerHTML = modalCantidadItem.value * modalMontoItem.value;
	celdaDetalle.innerHTML = "<a onclick='editarItem(this)' type='button' ><img src='https://cdn-icons-png.flaticon.com/512/6324/6324826.png' width='30px' height='30px'/></a>"


	modalCodItem.value = '';
	modalDescripcion.value = '';
	modalCantidadItem.value = '';
	modalMontoItem.value = '';
	indicadorPagoParcial.checked = false;

	$("#modalNuevoItem").modal('hide');
}


function limpiarTablaItem() {

	var tbItem = document.getElementById("tbItem").getElementsByTagName("tbody")[0];

	tbItem.innerHTML = '';

}
