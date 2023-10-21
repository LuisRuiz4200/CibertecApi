

function init() {
	validarFormulario();
}

init();
function obtenerDni() {

	var id = document.getElementById("CodePer").value;

	return new Promise((resolve, reject) => {
		fetch("/api/prestamista/obtenerDni/" + id)
			.then(response => response.json())
			.then(data => {
				if (data.mensaje) {
					
					resolve(data.mensaje);
				}
			});
	})
}

function validarDniExiste() {

	var dni = document.getElementById("dni").value;

	return new Promise((resolve, reject) => {
		fetch("/api/prestamista/validarDniExiste/" + dni)
			.then(response => response.json())
			.then(data => {
				if (data.mensaje) {
					toastr.warning(data.mensaje);
					resolve(true);
				} else {
					resolve(false);
				}
			});
	})
}

function validarRucExiste() {

	var ruc = document.getElementById("ruc").value;

	return new Promise((resolve, reject) => {
		fetch("/api/prestamista/validarRucExiste/" + ruc)
			.then(response => response.json())
			.then(data => {
				if (data.mensaje) {
					toastr.warning(data.mensaje);
					resolve(true);
				} else {
					resolve(false);
				}
			});
	})
}


function validarFormulario() {
	// Obtener el formulario
	const form = document.getElementById('guardarPrestamista');

	// Agregar un evento "submit" al formulario
	form.addEventListener('submit', (event) => {
		// Prevenir que se envíe el formulario
		event.preventDefault();

		// Obtener los valores de los campos
		const nombres = document.getElementById('nombres').value;
		const apellidos = document.getElementById('apellidos').value;
		const direccion = document.getElementById('direccion').value;
		const email = document.getElementById('email').value;
		const dni = document.getElementById('dni').value;


		// Verificar que no estén vacíos y mostrar mensajes de error por campo
		if (!nombres) {
			toastr.error('Falta completar el campo nombres');
			return;
		}

		if (!apellidos) {
			toastr.error('Falta completar el campo apellidos');
			return;
		}

		if (!direccion) {
			toastr.error('Falta completar el campo dirección');
			return;
		}

		if (!email) {
			toastr.error('Falta completar el campo email');
			return;
		} else if (!/\S+@\S+\.\S+/.test(email)) {
			toastr.error('El campo email debe tener una estructura de correo electrónico válida');
			return;
		}

		if (!dni) {
			toastr.error('Falta completar el campo DNI');
			return;
		} else if (dni.length !== 8) {
			toastr.error('El campo DNI debe tener 8 dígitos');
			return;
		}

		const codePre = document.getElementById('CodePre').value;
		const codePer = document.getElementById('CodePer').value;
		// Validar si los campos CodePre y CodePer son iguales a cero
		if (codePre == '0' && codePer == '0') {
			// Mostrar SweetAlert para prestamista registrado

			var documentoExiste = true;

			Promise.all([validarDniExiste()])
				.then(([dniExiste]) => {

					if (!dniExiste) {
						toastr.success("dni permitido");
					}

					documentoExiste = dniExiste

					if (!documentoExiste) {
						Swal.fire({
							title: '¡Formulario completado!',
							text: 'Registrado',
							icon: 'success',
							confirmButtonText: 'Aceptar'
						}).then(() => {
							form.submit();
						});
					}
				});
		} else {
			// Mostrar SweetAlert para prestamista actualizado
			
			Promise.all([obtenerDni()]).then(([dniPersona]) => {
				 
				if (dniPersona === dni) {
					Swal.fire({
						title: '¡Formulario completado!',
						text: 'Actualizado',
						icon: 'success',
						confirmButtonText: 'Aceptar'
					}).then(() => {
						form.submit();
					});
				} else {
					Promise.all([validarDniExiste()])
						.then(([dniExiste]) => {

							if (!dniExiste) {
								toastr.success("dni permitido");
							}

							documentoExiste = dniExiste

							if (!documentoExiste) {
								Swal.fire({
									title: '¡Formulario completado!',
									text: 'Actualizado',
									icon: 'success',
									confirmButtonText: 'Aceptar'
								}).then(() => {
									form.submit();
								});
							}
						});
				}

			})

		}
	}); //fin de form.addEventListener
}