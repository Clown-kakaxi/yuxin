Ext.onReady(function() {
	Ext.QuickTips.init();
		// 最终展现的panel
	    var onlineMax = '';
	    var onlineNum = '';
	    
		var listPanel = new Mis.Ext.CrudPanel( {
			id : "listPanel",
			title : "在线用户查询",
			stUrl : basepath + '/userOnlineAction!Query.json',
			primary : "id",
			info : '',
			buts : {
				text : this.info,
				handler:function(){}
			},
			checkbox : false,
			//定义查询条件Form的高度
			seFormHeight : 70,
			//宽度
			winWidth : 800,
			defaultLoad : true,
			// 查询字段定义，若不定义则不出现查询条件Form
			selectItems : {
				layout : 'column',
				items : [{
							columnWidth : .5,
							layout : 'form',
							items : [{
										name : 'userName',
										xtype : 'textfield',
										fieldLabel : '用户名称',
										anchor : '90%'
									}]
						}, {
							columnWidth : .5,
							layout : 'form',
							labelWidth : 80,
							items : [{
										name : 'unitName',
										xtype : 'textfield',
										fieldLabel : '所属机构名称',
										anchor : '90%'
									}]
						}]
			},
			loadCurrData : function(flag) {
				if (this.stUrl) {
					
					if (flag != 0 && this.selectForm) {
						if(!this.selectForm.form.isValid()){
							return false;
						}
						var conditionStr = this.selectForm.getForm().getValues(false);
						this.store.baseParams = {
							"condition" : Ext.encode(conditionStr)
						};
					};
					
					this.store.load({
						params : {
							start : 0,
							limit : parseInt(this.pagesize)
						},callback: function(records, options, success){
							Ext.Ajax.request({
								url : basepath + '/userOnlineAction!getOnlineMax.json',
								method : 'GET',
								scope : this,
								success : function(response) {
									 var resultInfo = Ext.util.JSON.decode(response.responseText);
									 onlineMax = resultInfo.json.onlineMax;
									 onlineNum = resultInfo.json.onlineNum;
									 refreshOnlineInfo("<b>在线用户个数：["+onlineNum+"];最大在线用户数:["+onlineMax+"]</b>");
								}
							});
					    }
					});
					
				} 
				
			},
	
			//查询列表字段定义，有header属性则在页面显示
			//如果需要做映射需要定义store , mappingkey ,mappingvalue 三个属性
			gclms : [{name : 'userId',mapping : 'userId',header : '用户ID',width : 150},  
				     {name : 'cname',mapping : 'cname',header : '用户名称',width : 150},  
				     {name : 'currentIP',mapping : 'currentIP',header : '用户IP',width : 150},  
				     {name : 'unitId',mapping : 'unitId',header : '用户机构ID',width : 250},  
					 {name : 'unitName',mapping : 'unitName',header : '用户机构名称',width : 400}]
		});
		var refreshOnlineInfo = function(info){
			document.getElementById(listPanel.grid.tbar.id).innerHTML = "";
			var butItems = [];
			butItems.push({text:info});
			var t = new Ext.Toolbar(butItems);
			t.render(listPanel.grid.tbar); 
		};
		// 布局模型
		var viewport = new Ext.Viewport( {
			layout : 'fit',
			items : [ listPanel ]
		});
				
	});