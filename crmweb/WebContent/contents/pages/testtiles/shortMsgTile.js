(function(){
	var tileBox = new Ext.DataView({
		title : '短信',
		index : 1,
		tpl : new Ext.XTemplate(
		 '<div class="tile base tile_c4">',
        	'<div class="tile_fun">',
            	'<div class="tile_fun_pic">','<img src="styles/search/searchpics/fun2.png" width="60" height="60" />','</div>',
                '<div class="tile_fun_name">',
               	 ' <p title="短信平台">','<i>','短信平台','</i>','</p>',
               '</div>   ',             
            '</div>',
        '</div>'),
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