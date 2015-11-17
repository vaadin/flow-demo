updateGenterChart = function(genderJSON, genderChart) {
	var genderData = JSON.parse(genderJSON);
	genderChart.chart.setTitle({text: "Gender"});
	var genders = [];
	for (var key in genderData) {
		genders.push([key, genderData[key]]);
	}
	removeSeries(genderChart.chart);
	genderChart.chart.addSeries({name: 'Gender', data: genders});
};

removeSeries = function(chart) {
	for (var i = 0; i < chart.series.length; i++) {
		chart.series[i].remove();
	}
}