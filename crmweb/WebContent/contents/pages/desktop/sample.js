/*!
 * Ext JS Library 3.3.1
 * Copyright(c) 2006-2010 Sencha Inc.
 * licensing@sencha.com
 * http://www.sencha.com/license
 */
var menus;
var MyDesktop;

var handlerBuilder = function(m){
	if(m.menu){
		return false;
	}else{
		if(!m.ACTION){
			return false;
		}else{
			var desktop = MyDesktop.getDesktop();
			var win = desktop.getWindow('function_'+m.ID);
			if(!win){
				win = desktop.createWindow({
					id: 'function_'+m.ID,
					title:m.NAME,
					width:640,
					height:480,
					html :' <iframe style="border:0 solid #000;height:100%;width:100%;" src="'+basepath+m.ACTION+'?resId='+m.ID+'" ></iframe>',
					iconCls: 'bogus',
					shim:false,
					animCollapse:false,
					constrainHeader:true
				});
			}
			win.show();
		}
	}
};

Ext.Ajax.request({
	url:basepath+'/indexinit.json',
	method:'GET',
	success:function(a,b,c,d){
		menus = Ext.decode(a.responseText).json.data;
		MyDesktop = new Ext.app.App({
			init :function(){
				Ext.QuickTips.init();
			},
			getModules : function(){
				var modules = [];
				Ext.each(menus,function(m){
					m.id = m.ID;
					m.text = m.NAME;
					m.launcher = {
						text : m.NAME
					}
					var menusub = [];
					Ext.each(menus,function(ms){
						if(ms.PARENT_ID == m.ID){
							menusub.push(ms);
						}
					});
					if(menusub.length>0){
						m.launcher.iconCls = 'bogus';
						m.launcher.menu = {
							items : menusub
						};
						m.launcher.handler = Ext.emptyFn;
					}else {
						m.launcher.iconCls = 'bogus';
						m.launcher.handler = function(){
							debugger;
							handlerBuilder(m);
							//hb.runHandler();
						};
					}
					m.init = function(){
						m.id = m.ID;
						m.text = m.NAME;
						m.handler = m.handler;
						m.menu = m.launcher.menu;
					};
					m.handler = m.launcher.handler;
					m.menu = m.launcher.menu;
				});
				
				Ext.each(menus,function(mi){
					if(mi.PARENT_ID == '0'){
						var mo = new Ext.app.Module(mi);
						modules.push(mo);
					}
				});
				debugger;
				return modules;
			},
		    getStartConfig : function(){
		        return {
		            title: 'Jack Slocum',
		            iconCls: 'user',
		            toolItems: [{
		                text:'Settings',
		                iconCls:'settings',
		                scope:this
		            },'-',{
		                text:'Logout',
		                iconCls:'logout',
		                scope:this
		            }]
		        };
		    }
		});
		
	},
	failure:function(a,b,d,c){
	}
});