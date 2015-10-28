createMarkers = function(lats, lons, mapElem) {
	if (lats.length <= 0) {
		return;
	}
	var cor = {lat:lats[0],lng:lons[0]};

	var map = new google.maps.Map(mapElem, {
		zoom: 4,
    	center: cor
	});
	for (var i = 0; i < lats.length; i++) {
		var cc = {lat:lats[i],lng:lons[i]};
		var marker = new google.maps.Marker({
			position: cc,
			map: map,
			title: 'Hello World!'
		});	
	}
	
}