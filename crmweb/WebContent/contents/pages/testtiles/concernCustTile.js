(function(){
	var tileBox = new Wlj.widgets.views.index.grid.TileGrid({
		title : '关注客户',
		root : 'json.data',
		url :  basepath + '/custConcern.json',
		columns : [{
			columnName : 'ID',
			key : true
		},{
			columnName : 'LINK_USER',
			show : true
		},{
			columnName : 'CUST_ZH_NAME',
			show : true
		}]
	});
	return tileBox;
})();