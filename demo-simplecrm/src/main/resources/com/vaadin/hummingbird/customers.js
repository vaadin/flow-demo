populate = function() {
	var grid = grid || document.querySelector("vaadin-grid");
	grid.columns = [
		{ name:"firstName", headerContent: "First name", sortable: true },
		{ name:"lastName", headerContent: "Last name", sortable: true },
		{ name:"email", headerContent: "Email", sortable: true },
		{ name:"status", headerContent: "Status", sortable: true },
		{ name:"estimate", headerContent: "Estimate", sortable: false },
		{ name:"progress", headerContent: "Progress", sortable: true }
	];
	grid.items = data;
	
	grid.addEventListener('sort', function() {
        var grid = document.querySelector("vaadin-grid");
        var column = grid.data.sortOrder[0].column;
        var property = grid.columns[column].name;
        var asc = grid.data.sortOrder[0].direction === 'asc';
        sortCustomers(property, asc);
	});
	
	grid.addEventListener('select', function() {
		var grid = document.querySelector("vaadin-grid");
		var selected = grid.selection.selected();
        if (selected.length > 0) {
            var selectedCustomer = grid.data.source[selected[0]];
            //customerForm.selectedCustomer = this._cloneCustomer(selectedCustomer);
            displayEditor();
        } else {
            closeEditor();
        }
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

sortCustomers = function(sortProperty, ascending) {
	data.sort(function(item1, item2) {
	    if (item1[sortProperty] < item2[sortProperty]) {
	        return (ascending ? -1 : 1);
	    } else {
	        return (ascending ? 1 : -1);
	    }
	});
};

HTMLImports.whenReady(function() {
	setTimeout(populate, 700);
});