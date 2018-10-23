(function(){
	var searchComponent = new Wlj.widgets.search.search.SearchComponent({
		style : {
			marginTop :0,
			marginLeft : 0,
			top : 0,
			left : 0
		},
		listeners : {
			afterrender : function(){
				this.appObject = this.ownerCt.appObject;
			}
		}
	});
	return searchComponent;
})();