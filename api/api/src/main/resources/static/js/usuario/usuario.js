function init() {
	validarFormulario();
}
init();

function validarUsuarioExiste() {
	var nombre = document.getElementById("nombre").value;
	return new Promise((resolve) => {
		fetch("/api/usuario/validarUsuarioExiste/" + nombre)
		.then(response => response.json())
		.then(data => {
			if (data.mensaje) {
				toastr.warning(data.mensaje);
				resolve(true);
			} else {
				resolve(false);
			}
		})
	});
}

function obtenerUsuario() {
	var idUsuario = document.getElementById("code").value;
	return new Promise((resolve) => {
		fetch("/api/usuario/obtenerUsuario/" + idUsuario)
		.then(response => response.json())
		.then(data => {
			resolve(data.nombreUsuario);
		})
	});
}

function validarFormulario() {
	// Obtener el formulario
	const form = document.getElementById('formularioUsuario');

	// Agregar un evento "submit" al formulario
	form.addEventListener('submit', (event) => {
		// Prevenir que se envíe el formulario
		event.preventDefault();
		// Obtener los valores de los campos
		const nombre = document.getElementById('nombre').value;
		const clave = document.getElementById('clave').value;
		//const comboPersona = document.getElementById('idPer');
		//const comboRol = document.getElementById('idR');
		// Verificar que no estén vacíos y mostrar mensajes de error por campo
		if (!nombre) {
			toastr.error('Falta completar el campo nombre de usuario');
			return;
		}
		if (!clave) {
			toastr.error('Falta completar el campo clave de usuario');
			return;
		}
		//if(comboPersona.value == '0'){
		//  toastr.error('Debe seleccionar una persona válida');
		// return;
		//}

		//if(comboRol.value == '0'){
		//  toastr.error('Debe seleccionar una rol válido');
		//para que se quede aqui y no pase a lo demás hasta completar esto
		//return;
		//}
		//CONFIRMACION de Agregado
		// Si todo está bien, enviar el formulario validando por codePre y codePer para el mensaje
		// Obtener los valores de los campos
		const code = document.getElementById('code').value;
		// Validar si los campos CodePre y CodePer son iguales a cero
		if (code == '0') {
			// Mostrar SweetAlert para usuario registrado

			Promise.all([validarUsuarioExiste()])
				.then(([usuarioExiste]) => {
					if (!usuarioExiste) {
						Swal.fire({
							title: '¡Formulario completado!',
							text: 'Usuario Registrado',
							icon: 'success',
							confirmButtonText: 'Aceptar'
						}).then(() => {
							form.submit();
						});
					}
				})

		} else {
			
			Promise.all([obtenerUsuario()])
				.then(([nombreUsuario]) => {
					if (nombreUsuario === nombre) {
						
					}
				})
			
			// Mostrar SweetAlert para usuario actualizado
			Swal.fire({
				title: '¡Formulario completado!',
				text: 'Usuario Actualizado',
				icon: 'success',
				confirmButtonText: 'Aceptar'
			}).then(() => {
				form.submit();
			});
		}

	}); //fin de form.addEventListener
}


