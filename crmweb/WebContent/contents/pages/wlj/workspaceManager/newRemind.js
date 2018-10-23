(function(){
	var tg = new Wlj.widgets.views.index.grid.TileGrid({
		needheader :false,
		dataSize : 700,
		id:'listPanelRemind',
		title : '提醒查询',
		root : 'json.data',
		url : basepath+'/remindListQueryNewAction.json',
		columns : [{
			columnName : 'RULE_CODE',
			width : 100,
			show : true
		},{
			columnName : 'TOTALNO',
			show : true
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
			len = len||0;
			var templateString = '';
				templateString += '<div class="w2h2" > ';
				templateString += this.titleHTML();
				templateString +=  "<div class='tile_notice_list tile_content'><ul id='ulText' class='in_sys_ul'>";
				if(len>maxLen){
					templateString +=  "<marquee id='marquee' onmouseover=stop(); onmouseout=start(); direction='up' align='top' style='height:230px;' scrollamount=2>";
				}
				templateString += '<tpl for=".">';
				templateString +=  "<li><div class='in_sys_line'><p class='in_sys_p'>";
				
				templateString +=  '<span class="time">{RULE_CODE}</span>';
				templateString +=  "<span class='type_red'></span>";
				templateString +=  '<a title="{TOTALNO}" href="javascript:void(0);" style="width:30px;" class="tit">{TOTALNO}</a>';
				
				templateString +=  "</p></div></li>";
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
			url : basepath + '/remindListQueryNewAction.json',
			method : 'GET',
			success : function(response) {
				var ret = Ext.decode(response.responseText);
				var data = ret.json.data;
				var len = data.length;

				var gridQ=Ext.getCmp('listPanelRemind');
				var templateString = '';
				templateString += '<div id="demo" class="w2h2" > ';
				templateString += gridQ.titleHTML();
				templateString +=  "<div id='demo1' class='tile_grid_list tile_content'><ul id='ulText' class='in_sys_ul'>";
				if(len>7){
					templateString +=  "<marquee id='marquee' onmouseover=stop(); onmouseout=start(); direction='up' align='top'  scrollamount=2>";
				}
				for(var i=0;i<len;i++){
					templateString +=  "<li id='xinxitixing"+i+"' ><div class='in_sys_line'><p class='in_sys_p'>	<a style=\"width:300px;\" class='tit' href=\"javascript:void(0);\">";
					if(data[i].RULE_CODE != '' && data[i].RULE_CODE != null && data[i].RULE_CODE!=undefined){
						templateString += "&nbsp;";
						templateString += data[i].RULE_CODE;
						templateString += "&nbsp;";
						templateString += data[i].TOTALNO;
					}
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
//	load();	
	return [tg];
})();