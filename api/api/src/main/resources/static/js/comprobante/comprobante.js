function init() {
	asignarSerie();
}
init();

function validarFormulario() {

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
	
	if(!modalCodItem.value || modalCodItem.value <= 0){
		toastr.error('Debe ingresar un código');
		return;
	}
	if(!modalDescripcion.value){
		toastr.error('Debe ingresar una descripción');
		return;
	}
	if(!modalMontoItem.value || modalMontoItem.value <= 0){
		toastr.error('Debe ingresar un monto mayor a 0');
		return;
	}
	if(!modalCantidadItem.value || modalMontoItem.value <= 0){
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
