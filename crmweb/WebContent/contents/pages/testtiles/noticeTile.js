(function(){
	
	var tg = new Wlj.widgets.views.index.grid.TileGrid({
		needheader :false,
		dataSize : 7,
		id:'listPanelNotice',
		title : '公告管理',
		root : 'json.data',
		url : basepath+'/noticequery.json?homeIndex=1',
		columns : [{
			columnName : 'NOTICE_ID',
			key : true
		},{
			columnName : 'ACTIVE_DATE',
			show : true
		},{
			columnName : 'NOTICE_TITLE',
			width:120,
			show : true
		}]
	});
	var load = function(){
		Ext.Ajax.request({
			url : basepath + '/noticequery.json?homeIndex=1',
			method : 'GET',
			success : function(response) {
				var ret = Ext.decode(response.responseText);
				var data = ret.json.data;
				var len = data.length;
				var curDate = new Date().format('Y-m-d');

				var gridQ=Ext.getCmp('listPanelNotice');
				var templateString = '';
				templateString += '<div class="w2h2" > ';
				templateString += gridQ.titleHTML();
				templateString +=  "<div id='demo1' class='tile_grid_list tile_content'><ul class='in_sys_ul'>";
				if(len>7){
					templateString +=  "<marquee onmouseover=stop(); onmouseout=start(); direction='up' align='top'  scrollamount=2>";
				}
				for(var i=0;i<len;i++){
					templateString +=  "<li id='xinxitixing10' ><div class='in_sys_line'><p class='in_sys_p'>	<a style=\"width:300px;\" class='tit' href=\"javascript:void(0);\">";
					var date = data[i].ACTIVE_DATE;
					if(date == curDate){
						templateString += "<img src='"+basepath+"/contents/images/clock.png'/>";
					}else{
						templateString += "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;";
					}
					templateString += data[i].ACTIVE_DATE;
					templateString += "&nbsp;";
					templateString += data[i].NOTICE_TITLE;
					templateString +=  "</a></div></li>";
				}
				if(len>7){
					templateString += '</marquee>';
				}
				templateString += "</ul></div>";
				templateString += "</div>";
				
				gridQ.tpl = new Ext.XTemplate(templateString);
				Wlj.widgets.views.index.grid.TileGrid.superclass.initComponent.call(gridQ);
			
			}
		});
	};
	load();
	return [tg];
})();