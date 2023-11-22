function init(){
	asignarSerie();
}
init();

function validarFormulario(){
	
}

function asignarSerie(){
	var tipoComprobante = document.getElementById("idTipoComprobante");
	var tipoDocumento = document.getElementById("idTipoDocumento");
	var lblTipoDocumento = document.getElementById("lblTipoDocumento");
	var serie = document.getElementById("serie");
	
	serie.disabled = true;
	
	tipoComprobante.addEventListener('change',function(event){
		switch(tipoComprobante.value){
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
	tipoDocumento.addEventListener('change',function(event){
		switch(tipoDocumento.value){
			case "1":
				lblTipoDocumento.innerHTML = 'RUC DEL RECEPTOR';
				break;
			case "2":
				lblTipoDocumento.innerHTML = 'DNI DEL RECEPTOR';
				break;
		}
	});
	
	
	
}