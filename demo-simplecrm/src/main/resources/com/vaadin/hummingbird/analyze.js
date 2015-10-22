createCharts = function(agesJSON, genderJSON, statusJSON) {
	var agesData = JSON.parse(agesJSON);
	var genderData = JSON.parse(genderJSON);
	var statusData = JSON.parse(statusJSON);
	
	var ageChart = document.getElementById("ageChart");
	ageChart.chart.setTitle({text: "Age"});
	var ages = [];
	var categories =  [];
	for (var key in agesData) {
		ages.push([key, agesData[key]]);
		categories.push(key);
	}
	ageChart.chart.axes[0].setCategories(categories);
	ageChart.chart.addSeries({name: 'Ages', data: ages});
	
	var genderChart = document.getElementById("genderChart");
	genderChart.chart.setTitle({text: "Gender"});
	var genders = [];
	for (var key in genderData) {
		genders.push([key, genderData[key]]);
	}
	genderChart.chart.addSeries({name: 'Gender', data: genders});
	
	var statusChart = document.getElementById("statusChart");
	statusChart.chart.setTitle({text: "Sales funnel"});
	var statuses = [];
	for (var key in statusData) {
		statuses.push([key, statusData[key]]);
	}
	statusChart.chart.addSeries({name: 'Status', data: statuses});
	
};