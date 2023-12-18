function init() {
	cargarPrestamistasAgente();
}
init();

async function cargarPrestamistasAgente() {

	var cboPrestamistasJefe = document.getElementById("idFiltroPrestamista");
	var root = am5.Root.new("chartdiv3");
	cboPrestamistasJefe.addEventListener('change', async function() {
		graficos(root);
	});

}

async function dashboard(idJefePrestamista) {
	var url = new URL("http://localhost:9090/api/dashboard/rentabilidad?timestamp=" + new Date().getTime());
	url.searchParams.append("idJefePrestamista", idJefePrestamista);

	return fetch(url)
		.then(response => response.json())
		.then(data => {
			console.log(data.mensaje);
			return data;
		});

}


function cargarCboPrestamistasPorPrestamistaJefe(listaPrestamistas) {
	var cboPrestamistas = document.getElementById("cboPrestamistasPorPrestamistaJefe");

	var option = document.createElement("option");
	option.value = '-1';
	option.text = '[ SELECCIONE ]';
	cboPrestamistas.add(option);

	for (var prestamista of listaPrestamistas) {

		option = document.createElement("option");
		option.value = prestamista.idPrestamista;
		option.text = prestamista.nombreApellido;

		cboPrestamistas.add(option);
	}


}

function graficos(root) {

	am5.ready(async function() {

		var cboPrestamistasJefe = document.getElementById("idFiltroPrestamista");



		var cboPrestamistas = document.getElementById("cboPrestamistasPorPrestamistaJefe");


		cboPrestamistas.innerHTML = '';

		var apiDashboard = '';
		apiDashboard = await dashboard( cboPrestamistasJefe.value);

		if (apiDashboard.mensaje == 'No value present') {

			console.log('test ' + apiDashboard.mensaje);
			return;
		}

		cargarCboPrestamistasPorPrestamistaJefe(apiDashboard.detalle.prestamistas);

		// Create root element
		// https://www.amcharts.com/docs/v5/getting-started/#Root_element
		// var root = am5.Root.new("chartdiv3");



		// Set themes
		// https://www.amcharts.com/docs/v5/concepts/themes/
		root.setThemes([
			am5themes_Animated.new(root)
		]);


		// Create chart
		// https://www.amcharts.com/docs/v5/charts/radar-chart/
		var chart = root.container.children.push(am5radar.RadarChart.new(root, {
			panX: false,
			panY: false,
			startAngle: 160,
			endAngle: 380
		}));


		// Create axis and its renderer
		// https://www.amcharts.com/docs/v5/charts/radar-chart/gauge-charts/#Axes
		var axisRenderer = am5radar.AxisRendererCircular.new(root, {
			innerRadius: 0
		});

		axisRenderer.grid.template.setAll({
			stroke: root.interfaceColors.get("background"),
			visible: true,
			strokeOpacity: 0.8
		});

		var xAxis = chart.xAxes.push(am5xy.ValueAxis.new(root, {
			maxDeviation: 0,
			min: 0, //MINIMO PARA PONER
			max: 10000, //MAXIMO PARA PONER
			strictMinMax: true,
			renderer: axisRenderer
		}));


		// Add clock hand
		// https://www.amcharts.com/docs/v5/charts/radar-chart/gauge-charts/#Clock_hands
		var axisDataItem = xAxis.makeDataItem({});

		var clockHand = am5radar.ClockHand.new(root, {
			pinRadius: am5.percent(20),
			radius: am5.percent(100),
			bottomWidth: 40
		});

		var bullet = axisDataItem.set("bullet", am5xy.AxisBullet.new(root, {
			sprite: clockHand
		}));

		xAxis.createAxisRange(axisDataItem);

		var label = chart.radarContainer.children.push(am5.Label.new(root, {
			fill: am5.color(0xffffff),
			centerX: am5.percent(50),
			textAlign: "center",
			centerY: am5.percent(50),
			fontSize: "3em"
		}));

		axisDataItem.set("value", 0); //de Value 0 cambiara al to 349 de la linea 250
		bullet.get("sprite").on("rotation", function() {
			var value = axisDataItem.get("value");
			var text = Math.round(axisDataItem.get("value")).toString();
			var fill = am5.color(0x000000);
			xAxis.axisRanges.each(function(axisRange) {
				if (value >= axisRange.get("value") && value <= axisRange.get("endValue")) {
					fill = axisRange.get("axisFill").get("fill");
				}
			});

			label.set("text", Math.round(value).toString());

			clockHand.pin.animate({ key: "fill", to: fill, duration: 500, easing: am5.ease.out(am5.ease.cubic) });
			clockHand.hand.animate({ key: "fill", to: fill, duration: 500, easing: am5.ease.out(am5.ease.cubic) });
		});

		var montoTotalPrestado = 0;

		cboPrestamistas.addEventListener('change', () => {

			for (const prestamista of apiDashboard.detalle.prestamistas) {


				if (prestamista.idPrestamista === parseInt(cboPrestamistas.value)) {
					montoTotalPrestado = prestamista.resumen.montoTotalPrestado;

				}
			}

			axisDataItem.animate({
				key: "value",
				// to: Math.round(Math.random() * 140 - 40),
				to: montoTotalPrestado, //AQUI PONEMOS LA CANTIDAD DE HORAS EXTRAS
				duration: 500,
				easing: am5.ease.out(am5.ease.cubic)
			});

		});

		chart.bulletsContainer.set("mask", undefined);


		// Create axis ranges bands
		// https://www.amcharts.com/docs/v5/charts/radar-chart/gauge-charts/#Bands
		var bandsData = [{
			title: "Muy bajo",
			color: "#ee1f25",
			lowScore: 0,
			highScore: 1000
		}, {
			title: "Semi Bajo",
			color: "#f04922",
			lowScore: 1000,
			highScore: 2000
		}, {
			title: "Bajo",
			color: "#fdae19",
			lowScore: 2000,
			highScore: 5000
		}, {
			title: "Regular",
			color: "#f3eb0c",
			lowScore: 5000,
			highScore: 7000
		}, {
			title: "Bien",
			color: "#b0d136",
			lowScore: 7000,
			highScore: 8000
		}, {
			title: "Muy bien",
			color: "#54b947",
			lowScore: 8000,
			highScore: 9000
		}, {
			title: "Excelente",
			color: "#0f9747",
			lowScore: 9000,
			highScore: 10000
		}];

		am5.array.each(bandsData, function(data) {
			var axisRange = xAxis.createAxisRange(xAxis.makeDataItem({}));

			axisRange.setAll({
				value: data.lowScore,
				endValue: data.highScore
			});

			axisRange.get("axisFill").setAll({
				visible: true,
				fill: am5.color(data.color),
				fillOpacity: 0.8
			});

			axisRange.get("label").setAll({
				text: data.title,
				inside: true,
				radius: 15,
				fontSize: "0.9em",
				fill: root.interfaceColors.get("background")
			});
		});


		// Make stuff animate on load
		chart.appear(1000, 100);


	}); // end am5.ready()

}
