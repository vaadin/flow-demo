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

updateStatusChart = function(statusJSON, statusChart) {
	var statusData = JSON.parse(statusJSON);
	statusChart.chart.setTitle({text: "Sales funnel"});
	var statuses = [];
	for (var key in statusData) {
		statuses.push([key, statusData[key]]);
	}
	removeSeries(statusChart.chart);
	statusChart.chart.addSeries({name: 'Status', data: statuses});
	
};

removeSeries = function(chart) {
	for (var i = 0; i < chart.series.length; i++) {
		chart.series[i].remove();
	}
}