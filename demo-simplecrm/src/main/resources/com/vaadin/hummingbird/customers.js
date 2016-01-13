amendGrid = function(grid) {
	grid.then(function () {
		grid.columns[0].hidden = true;
		grid.columns[1].hidden = true;
		grid.columns[2].hidden = true;
		grid.columns[3].hidden = true;
		grid.columns[7].hidden = true;
		grid.columns[9].hidden = true;
		grid.columns[10].renderer = function(cell) {
		    var element = cell.element.querySelector('progress-bubble');
		    if (!element || element.value !== cell.data) {
		        cell.element.innerHTML = '<progress-bubble max="100" value="' + cell.data + '">' + cell.data + '%</progress-bubble>';
		    }
		};
		grid.columns[5].renderer = function(cell) {
	        cell.element.innerHTML = '<demo-sparkline width="60" height="20" points="' + cell.data + '"></demo-sparkline>';
	    };

	});
		
};

displayEditor = function() {
	document.getElementById('form-wrapper').style.display = 'block';
	document.getElementById('customerForm').classList.add('visible');
};

closeEditor = function() {
	document.getElementById('customerForm').classList.remove('visible');
	document.getElementById('form-wrapper').style.display = 'none';
};

//HTMLImports.whenReady(function() {
//	setTimeout(amendGrid, 700);
//});