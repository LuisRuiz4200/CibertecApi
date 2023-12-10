function init() {
	
}

async function apiConsulta(idPrestamista){
    var url = new URL("http://localhost:9090/api/prestamo/listar");
    url.searchParams.append("idPrestamista", idPrestamista); // Utilizar idPrestamista en lugar de idPrestamo
       return fetch(url)
        .then(response => response.json())
        .then(data => {
            return data;
        });
}

function habilitarBoton() {
    var btnFiltrar = document.getElementById("btnFiltrar");
    var idPrestamista = document.getElementById("idFiltroPrestamista");

    // Habilitar el botón solo si se selecciona un prestamista válido
    btnFiltrar.disabled = idPrestamista.value === "-1";
}

function limpiarFormulario() {
    var idFiltroPrestamista = document.getElementById("idFiltroPrestamista");
    var btnFiltrar = document.getElementById("btnFiltrar");
    // Restablecer el valor del combo y deshabilitar el botón Filtrar
    idFiltroPrestamista.value = "-1";
    btnFiltrar.disabled = true;
    // Limpiar la tabla de préstamos
    var tablaPrestamo = document.getElementById("tablaPrestamos").getElementsByTagName("tbody")[0];
    tablaPrestamo.innerHTML = "";
}


async function tablaConsulta(){
	var tablaPrestamo = document.getElementById("tablaPrestamos").getElementsByTagName("tbody")[0];
	var idPrestamista = document.getElementById("idFiltroPrestamista");
	var listaPrestamo = await apiConsulta(idPrestamista.value);
    tablaPrestamo.innerHTML = "";
    if (listaPrestamo.prestamos.length === 0) {
        // El prestamista no tiene préstamos
        toastr.warning("El prestamista no ha realizado préstamos.");
        return;
    }
    
	for (const prestamo of listaPrestamo.prestamos){
		var fila = tablaPrestamo.insertRow();
        fila.insertCell(0).innerText = prestamo.idPrestamo;
        fila.insertCell(1).innerText = prestamo.nomPrestatario + ' ' + prestamo.apePrestatario;
        fila.insertCell(2).innerText = 'S/. '+prestamo.montoPrestado.toFixed(2);
        fila.insertCell(3).innerText = 'S/. '+prestamo.interesPagar;
        fila.insertCell(4).innerText = 'S/. '+prestamo.montoTotal.toFixed(2);
        fila.insertCell(5).innerText = prestamo.cuotas;
        fila.insertCell(6).innerText = prestamo.cuotaPagadas;
        fila.insertCell(7).innerText = prestamo.cuotaPorPagar;
        fila.insertCell(8).innerText = 'S/. '+prestamo.montoPagado.toFixed(2);
        fila.insertCell(9).innerText = 'S/. '+prestamo.montoPorPagar.toFixed(2);
	}
	
	// Verificar si DataTable ya está inicializado
	var existingDataTable = $('#tablaPrestamos').DataTable();
	if (existingDataTable) {
		existingDataTable.destroy(); // Destruir DataTable existente
	}

	// Inicializar DataTable
	$('#tablaPrestamos').DataTable({
		language: {
			url: '//cdn.datatables.net/plug-ins/1.13.7/i18n/es-ES.json',
		},
	});
}