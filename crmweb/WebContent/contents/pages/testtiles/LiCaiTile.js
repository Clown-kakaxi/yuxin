(function(){
	var tileBox = new Ext.Panel({
	html:'<div class="w2h2" style="background-color:rgb(27, 150, 209)" ><div class="tile_tip_tit" ><p class="tit" ><span class=icon>客户理财产品排名</span></p></div>'+
	'<div class="tile_notice_list"><div class="tile_chart_tit"><p class="tit">'+
	'<table width="100%">'+
	'<tr class="in_sys_line"><td  width="40%">客户名称</td><td width="40%">产品购买金额</td><td width="20%">排名</td></tr>'+
	'<tr class="in_sys_line"><td width="40%">王一平</td><td width="40%">200,890,000.00</td><td width="20%">1</td>'+
	'</tr><tr class="in_sys_line"><td width="40%">李丽娟</td><td width="40%">190,890,000.00</td><td width="20%">2</td>'+
	'</tr><tr class="in_sys_line"><td width="40%">张馨予</td><td width="40%">187,890,000.00</td><td width="20%">3</td>'+
	'</tr><tr class="in_sys_line"><td width="40%">周鑫</td><td width="40%">179,890,000.00</td><td width="20%">4</td>'+
	'</tr><tr class="in_sys_line"><td width="40%">刘莎莎</td><td width="40%">156,890,000.00</td><td width="20%">5</td></tr>'+
	'</table></div></div></div>'
	,
		listeners : {
			afterrender : function(){
				
				var _this = this;
				this.el.on('click',function(){
					_this.ownerCt.clickFire();
				});
			}
		}
	});
	return tileBox;
})();