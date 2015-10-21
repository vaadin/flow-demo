createCharts = function() { // called from Analyze.java attach()
	var agesData = {'0-15':4,'15-30':3,'30-60':16,'60-100':7};
	var genderData = {'Men':40,'Women':60};
	var statusData = {'Imported lead': 4, 'Not contacted': 6, 'Contacted': 8, 'Customer': 5};
	
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