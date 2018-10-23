(function(){
	
 
	var tileBox = new Ext.Panel({
		title: '存款统计',
		html :'<iframe id="contentFrame"  name="content" style="top:20px;" height="270" frameborder="no" width="100%" src= '+basepath+'/contents/pages/testtiles/chart/data3.html  " scrolling="no" allowTransparency="true"> </iframe> '
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
