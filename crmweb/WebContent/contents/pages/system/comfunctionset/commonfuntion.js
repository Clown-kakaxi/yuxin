	

	var iconForViewPanel2 = new Ext.Window({
		id:'iconForViewPanel2',
		height : 400,
		width:550,
		frame : true,
		title : "常用功能",
		autoScroll : true,
		closeAction: 'hide'
	});
	
    iconForViewPanel2.on('show',function(){
    	while(iconForViewPanel2.body.dom.firstChild!=null){
    		iconForViewPanel2.body.dom.removeChild(iconForViewPanel2.body.dom.firstChild);
    	}
    	var _menus = [];
    	Ext.Ajax.request({
    		url : basepath + '/indexinit.json',
    		method:'GET',
    		success:function(response){
    			var nodeArra = Ext.util.JSON.decode(response.responseText);
    			var Array1= new Array();
    			Ext.each(nodeArra.json.data ,function(n){
    				var tmp = {};
    				tmp.id= n.ID;
    				if(n.PARENT_ID=='0')
    					tmp.parentId = 'root';
    				else
    					tmp.parentId=n.PARENT_ID;
    				tmp.text=n.NAME;
    				tmp.jumUrl=n.ACTION;
    				var iconNum = Math.round(Math.random()*20);
    				if(iconNum==0){
    					iconNum = 1;
    				}
    				if(iconNum<10){
    					var imgUrl =  basepath+"/contents/img/img/icon-0"+iconNum+'.png';
    				}else {
    					var imgUrl =  basepath+"/contents/img/img/icon-"+iconNum+'.png';
    				}
    				tmp.imgUrl = imgUrl;
    				Array1.push(tmp);
    		});
    			_menus = Array1;
    			Ext.Ajax.request({
    				url : basepath+'/comfunctionset.json',
    				method : "GET",
    				success : function(response){
    					userSetting = Ext.util.JSON.decode(response.responseText);
    					var userModule = userSetting.returns.data;
    					for(var i=0;i<_menus.length;i++){

							Ext.each(userModule,function(um){
								if(um.MODULE_ID==_menus[i].id){

		    						var w = um.MODULE_ID;
		    						var m ;
		    						var n = basepath+um.ICON_URL;
		    						var q ;
		    						var u ;
		    						var uid;
		    						for(var j = 0;j<_menus.length;j++){
		    							if (_menus[j].id == w){
		    								q = _menus[j].text;
		    								u =_menus[j].jumUrl;
		    								uid = _menus[j].id;
		    							}			
		    						}
		    						iconForViewPanel2.body.appendChild(iconForViewPanel2.body.createChild({
		    							tag: 'div',
		    							id:w+"_div",																			
		    							cls:'oftenlabel',
		    							html:'<a href="#" style="text-align:center;padding:80px 0 0 0;width:64px;height:64px;float:left;background:url('+n+') no-repeat;"  >'+q+'</a>',
		    							style:'margin-left:10px;margin-right:10px;margin-top:10px;'					
		    						}));
		    						Ext.fly(w+"_div").dom.onclick=function(){
		    							 if (u){
		    								 //document.getElementById('mainframe').src = basepath+u;
		    								 booter.indexLocate(uid);
		    								 iconForViewPanel2.hide(); 
		    							 }else {
		    								 Ext.Msg.alert("提示",'不存在该功能点！');
		    							 }
		    							};
		    					
								}
							});
						
    					}
//    					if(userModule[i].MODULE_ID == _menus[j].id){}
//    					for(var i=0;i<userModule.length;i++){
//    						for(var j=0;j<_menus.length;j++){
//    							
//    			
//    							
//    						}
//    					}
    				
    					return;
    				},
    				failure : function(action,form){
    					Ext.Msg.alert('提示','你的常用功能设置信息查询失败，将为您初始化默认配置'); 
    					var _resultP = Ext.getCmp('iconForViewPanel');
    					_resultP.doLayout();
    				}
    			});
    		}
    	});
    });
    
			
