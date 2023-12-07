function init() {
	
}

async function apiConsulta(idPrestamo){
	var url = new URL("http://localhost:9090/api/prestamo/listar");
	url.searchParams.append("idPrestamo",idPrestamo); 
	return fetch(url)
	.then(response=>response.json())
	.then(data=>{ 
		toastr.warning(data.idPrestamo);
		return data;
	})
	
}

async function tablaConsulta(){
	var tablaPrestamo = document.getElementById("tablaPrestamos").getElementsByTagName("tbody")[0];
	var idPrestamista = document.getElementById("idFiltroPrestamista");
	var listaPrestamo = await apiConsulta(idPrestamista.value);
	for (const prestamo of listaPrestamo){
		var fila = tablaPrestamo.insertRow();
		fila.insertCell(0).innerText=prestamo.idPrestamo;
		fila.insertCell(1).innerText=prestamo.idPrestamo;
		fila.insertCell(2).innerText=prestamo.idPrestamo;
		fila.insertCell(3).innerText=prestamo.idPrestamo;
		fila.insertCell(4).innerText=prestamo.idPrestamo;
	}
}