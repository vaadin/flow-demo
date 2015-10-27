createCharts = function(genderJSON, statusJSON) {
	var genderData = JSON.parse(genderJSON);
	var statusData = JSON.parse(statusJSON);
	
	var genderChart = document.getElementById("genderChart");
	genderChart.chart.setTitle({text: "Gender"});
	var genders = [];
	for (var key in genderData) {
		genders.push([key, genderData[key]]);
	}
	removeSeries(genderChart.chart);
	genderChart.chart.addSeries({name: 'Gender', data: genders});
	
	var statusChart = document.getElementById("statusChart");
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