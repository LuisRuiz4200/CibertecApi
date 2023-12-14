export function apisMannager (){
	
	
	async function apidDashboard(idJefePrestamista) {
	var url = new URL("http://localhost:9090/api/dashboard/rentabilidad?timestamp=" + new Date().getTime());
	url.searchParams.append("idJefePrestamista", idJefePrestamista);

	return fetch(url)
		.then(response => response.json())
		.then(data => {
			console.log(data.mensaje);
			return data;
		})
		

}
}


