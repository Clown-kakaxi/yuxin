 var functiontabs = null;
 var booter = null;
 var secManager = null;
 var informationWin = null;
Ext.onReady(function(){
	
	if(window.opener!=null && typeof(window.opener.document)!='undefined'&& typeof(window.opener.document)!='unknown'){
		window.focus();
		window.opener = null;
	}
	
	Ext.Ajax.request({//进入系统后，进行页面设置初始化
		url:basepath+'/pageSetManageAction.json',
		method: 'POST',
		success : function(response) {
		var respones = Ext.util.JSON.decode(response.responseText).json.data;
		var indexUtil = parent.INDEXUTIL;
		for(var i=0;i<respones.length;i++){
			var lName = respones[i].PROP_NAME;//取查询成功后数据中的prop_name的值
			var tName = lName.substring(9).trim();//截取查询成功后数据中的prop_name的值
			if(tName == 'indexContentUrl'){//对'indexContentUrl'进行处理
				//parent.INDEXUTIL[tName]=basepath+respones[i].PROP_VALUE;
			}else{//其他值得处理
				if(tName != 'tabMaxCount' && tName !='tabNameMaxLen' && tName != 'subMenusNameMaxLen'
					&& tName != 'menuReloadCount' && tName != 'menuReloadDelayMs' && tName != 'custManagerPlat'
					&& tName != 'offenOp' && tName != 'cu' && tName != 'logout' && tName != 'expandingSubMenuMode' ){
					if(respones[i].PROP_VALUE == '0'){
						parent.INDEXUTIL[tName] =true;
					}else if(respones[i].PROP_VALUE == '1'){
						parent.INDEXUTIL[tName] =false;
					}
				}else{
					if(tName == 'custManagerPlat' || tName == 'offenOp' ||
							tName == '_cu' || tName == 'logout'){//右上角快捷方式的处理
						for(var j=0;j<parent.INDEXUTIL.shortCuts.length;j++){
							if(parent.INDEXUTIL.shortCuts[j].id == tName){
								if(respones[i].PROP_VALUE == '0'){
									parent.INDEXUTIL.shortCuts[j].enable = true;
								}else if(respones[i].PROP_VALUE == '1'){
									parent.INDEXUTIL.shortCuts[j].enable = false;
								}
							}
						}
					}else if(tName == 'expandingSubMenuMode'){
						if(respones[i].PROP_VALUE == '1'){
							parent.INDEXUTIL[tName] =true;
						}else if(respones[i].PROP_VALUE == '2'){
							parent.INDEXUTIL[tName] =false;
						}
					}else{
						parent.INDEXUTIL[tName] = respones[i].PROP_VALUE;
					}
				}
			}
		}
		booter = new Com.yucheng.crm.index.IndexBooter();
		//informationWin = new Com.yucheng.crm.information.InformationBooter();
	},
	failure : function(response) {//若加载失败，不提示，依然读取com.yuchengtech.crm.index中的值
		booter = new Com.yucheng.crm.index.IndexBooter();
	}
	});
});