populate = function() {
	  var grid = grid || document.querySelector("vaadin-grid");
	  
	  grid.columns = [
	                   { name:"firstName", headerContent: "First name" },
	                   { name:"lastName", headerContent: "Last name" },
	                   { name:"email", headerContent: "Email" },
	                   { name:"status", headerContent: "Status" },
	                   { name:"estimate", headerContent: "Estimate" },
	                   { name:"progress", headerContent: "Progress" }
	  ];
	  grid.items = data;
};

HTMLImports.whenReady(function() {
	setTimeout(populate, 1000);
});



