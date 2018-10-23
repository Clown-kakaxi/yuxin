(function(){
	var tg = new Wlj.widgets.views.index.grid.TileGrid({
		needheader :false,
		id:'listPanelSchedule',
		dataSize : 7,
		title : '工作日程',
		root : 'json.data',
		url : basepath+'/ocrmFWpSchedule!queryScheduleTwo.json',
		columns : [{
			columnName : 'SCH_ID',
			key : true
		},{
			columnName : 'SCH_DATE',
			show : true
		},{
			columnName : 'SCHEDULE_COUNT',
			show : true
		}]
	});

	var load = function(){
		Ext.Ajax.request({
			url : basepath + '/ocrmFWpSchedule!queryScheduleTwo.json',
			method : 'GET',
			success : function(response) {
				var ret = Ext.decode(response.responseText);
				var data = ret.json.data;
				var len = data.length;
				var curDate = new Date().format('Y-m-d');

				var gridQ=Ext.getCmp('listPanelSchedule');
				var templateString = '';
				templateString += '<div class="w2h2" > ';
				templateString += gridQ.titleHTML();
				templateString +=  " <div class='tile_grid_list tile_content'><ul class='in_sys_ul'> " ;
				if(len>7){
					templateString +=  "<marquee onmouseover=stop(); onmouseout=start(); direction='up' align='top'  scrollamount=2>";
				}
				for(var i=0;i<len;i++){
					templateString +=  "<li ><div class='in_sys_line'><p class='in_sys_p'>	<a style=\"width:300px;\" class='tit' href=\"javascript:void(0);\">";
					var date = data[i].SCH_DATE;
					if(date == curDate){
						templateString += "<img src='"+basepath+"/contents/images/clock.png'/>";
					}else{
						templateString += "&nbsp;&nbsp;&nbsp;&nbsp;";
					}
					templateString += data[i].SCH_DATE;
					templateString += "&nbsp;";
					templateString += data[i].SCHEDULE_COUNT;
					templateString +=  "</a></div></li>";
				}
				if(len>7){
					templateString += '</marquee>';
				}
				templateString += '</ul>';
				templateString += '</div></div>';
				
				gridQ.tpl = new Ext.XTemplate(templateString);
				Wlj.widgets.views.index.grid.TileGrid.superclass.initComponent.call(gridQ);
			
			}
		});
	};
	load();
	return [tg];
})();