

function limpiarCuotas(){
	
	var table = document.getElementById("tbCuotas").getElementsByTagName('tbody')[0];
	table.innerHTML= '';
}

function cargarCuotas(){
	var table = document.getElementById("tbCuotas").getElementsByTagName('tbody')[0];
	var monto = document.getElementById("monto").value;
	var cuotas = document.getElementById("cuotas").value;
	
	var montoPorCuota =  monto / cuotas ;
	
	limpiarCuotas();
	
	for(let i =1 ; cuotas >= i ; i++){
		
    	var nuevaFila = table.insertRow(table.rows.length);
    	
    	var celdaCuota = nuevaFila.insertCell(0);
    	var celdaMonto = nuevaFila.insertCell(1);
    	var celdaFechaPago = nuevaFila.insertCell(2);
    	
    	celdaCuota.innerHTML = i;
    	celdaMonto.innerHTML = montoPorCuota;
    	celdaFechaPago.innerHtml = "";
    	
	}
}