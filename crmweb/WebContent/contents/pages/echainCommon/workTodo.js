(function(){
	var tg = new Wlj.widgets.views.index.grid.MaxTileGrid({
		needheader :false,
		id:'listPanelApplywait',
		dataSize : 700,
		title : '待办工作',
		root : 'json.data',
		url : basepath+'/queryrestapplywaitNew.json',
		columns : [{
			columnName : 'INSTANCEID',
			key : true
		},{
			columnName : 'WFSTARTTIME',
			show : true
		},{
			columnName : 'WFJOBNAME',
			show : true,
			width : '70%'
		}],
		/**
		 * 加载数据后的回掉函数
		 */
		dataCb	: function(){
			var len = this.store.getCount();
			this.createTemplate(len);
			this.refresh();
		},
		/**
		 * 重写默认的createTemplate函数，改变默认的显示
		 */
		createTemplate	: function(len){
			var _this = this,maxLen = this.maxLen||7;
			var showColumns = [];
			Ext.each(_this.columns, function(col){
				col.width = col.width?col.width:100;
				col.align = col.align?col.align:'left';
				if(col.show){
					showColumns.push(col);
				}
			});
			len = len||0;
			var templateString = '';
				templateString += '<div id="demo" class="w3h3" > ';
				templateString += this.titleHTML();
				templateString +=  "<div id='demo1' class='tile_grid_list tile_content'><ul id='ulText' class='in_sys_ul'>";
				if(len>maxLen){
					templateString +=  "<marquee id='marquee' onmouseover=stop(); onmouseout=start(); direction='up' align='top'  scrollamount=2>";
				}
				templateString += '<tpl for=".">';
				templateString +=  "<li><div class='in_sys_line'><p class='in_sys_p'>";
				/*
				templateString +=  "<a style=\"width:100px;\" class='tit' href=\"javascript:void(0);\">";
				templateString +=  "&nbsp;{WFSTARTTIME}&nbsp;";
				templateString +=  "<span class='type_red'></span>";
				templateString +=  "</a>";
				templateString +=  "<a style=\"width:100px;\" class='tit' href=\"javascript:void(0);\">";
				templateString +=  "&nbsp;{WFJOBNAME}&nbsp;";
				templateString +=  "<span class='type_red'></span>";
				templateString +=  "</a>";
				*/
				var rowTpl = new Ext.XTemplate(
					'<tpl for=".">',
						"<a class='tit' style='width:{width}px;' href=\"javascript:void(0);\">",
						'{{columnName}}',
						"<span class='type_red'></span>",
						"</a>",
					'</tpl>'
				);
				var rowHtml = rowTpl.apply(showColumns);
				templateString += rowHtml;
				
				templateString +=  "</div></li>";
				templateString += '</tpl>';
				if(len>maxLen){
					templateString += '</marquee>';
				}
				templateString += "</ul></div>";
				templateString += "</div>";
				this.tpl = new Ext.XTemplate(templateString);
		}
	});

	var load = function(){
		Ext.Ajax.request({
			url : basepath + '/queryrestapplywaitNew.json',
			method : 'GET',
			success : function(response) {
				var ret = Ext.decode(response.responseText);
				var data = ret.json.data;
				var len = data.length;
				var curDate = new Date().format('Y-m-d');

				var gridQ=Ext.getCmp('listPanelApplywait');
				var templateString = '';
				templateString += '<div id="demoWorkTodo" class="w3h3" > ';
				templateString += gridQ.titleHTML();
				templateString +=  "<div id='demoWorkTodo1' class='tile_grid_list tile_content'><ul id='ulWorkTodo' class='in_sys_ul'>";
				if(len>7){
					templateString +=  "<marquee id='marqueeWorkTodo' onmouseover=stop(); onmouseout=start(); direction='up' align='top'  scrollamount=2>";
				}
				for(var i=0;i<len;i++){
					var instanceId = data[i].INSTANCEID;
					templateString +=  "<li id='workTodo"+instanceId+"' ><div class='in_sys_line'><p class='in_sys_p'>	<a style=\"width:300px;\" class='tit' href=\"javascript:void(0);\">";
					templateString += data[i].WFSTARTTIME;
					if(data[i].WFJOBNAME != '' && data[i].WFJOBNAME != null && data[i].WFJOBNAME!=undefined){
						templateString += "&nbsp;";
						templateString += data[i].WFJOBNAME;
					}
					templateString +=  "</a></div></li>";
				}
				if(len>7){
					templateString += '</marquee>';
				}
				templateString += "</ul></div>";
				templateString += "</div>";
				
				gridQ.tpl = new Ext.XTemplate(templateString);
				Wlj.widgets.views.index.grid.MaxTileGrid.superclass.initComponent.call(gridQ);
			}
		});
	};
	//load();
	return [tg];
})();