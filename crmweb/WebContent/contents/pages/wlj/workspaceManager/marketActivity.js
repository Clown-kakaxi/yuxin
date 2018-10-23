/**
 * 首页营销活动的磁贴
 */
(function(){
	var tg = new Wlj.widgets.views.index.grid.TileGrid({
		needheader :false,
		dataSize : 700,
		title : '营销活动',
		root : 'json.data',
		url : basepath+'/mktCustManegerback.json',
		columns : [{
			columnName : 'CUST_NAME',
			width : 100,
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
				
				templateString +=  '<span class="time">{CUST_NAME}</span>';
				templateString +=  "<span class='type_red'></span>";
				//templateString +=  '<a title="{TOTALNO}" href="javascript:void(0);" style="width:30px;" class="tit">{TOTALNO}</a>';
				
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
	return [tg];
})();