/**
 * 提醒规则设置
 * luyy 2014-2-18
 */

Ext.onReady(function() {
	var showColums = '';//需要展示的字段
	var colums = 'ruleRole,adjustCust,custAge,sex,beforeDays,lastDays,threshhold,remindModle,ifMessage,followDeal,messageModel,comCustLevel,indivCustLevel';//所有需要控制的字段
	
	//控制页面字段的方法
	function dealWithColums(fields,action){
		var fields_arr = fields.split(",");
		if (fields_arr != null && fields_arr.length > 0) {
			var fieldName = null;
			for ( var i = 0; i < fields_arr.length; i++) {
				fieldName = fields_arr[i];
				if (fieldName != null && fieldName != "") {
					if(action=='hide'){
						formInfo.getForm().findField(fieldName).hide();
					}
					if(action=='show'){
						formInfo.getForm().findField(fieldName).show();
					}
				}
				}
			}
	}
	
	//判断表单填写情况  showColums 全部填写
	function ifRight(showColums){
		var fields_arr = showColums.split(",");
		if (fields_arr != null && fields_arr.length > 0) {
			var fieldName = null;
			for ( var i = 0; i < fields_arr.length; i++) {
				fieldName = fields_arr[i];
				if (fieldName != null && fieldName != "") {
					var value =	formInfo.getForm().findField(fieldName).getValue();
					if(value == null||value == ''){
						return false;
					}
					else if(fieldName=='adjustCust'){//如果是适用客户类型字段还要判断客户等级是否填写
						if (value == "0") {//所有
							if(formInfo.getForm().findField("comCustLevel").getValue()==''||formInfo.getForm().findField("indivCustLevel").getValue()=='')
								return false;
						}
						if (value == "1"){//零售
							if(formInfo.getForm().findField("indivCustLevel").getValue()=='')
								return false;
						}
						if (value == "2"){//对公
							if(formInfo.getForm().findField("comCustLevel").getValue()=='')
								return false;
						}
					}
					else if(fieldName=='ifMessage'){//如果是是否短信字段还要判断短信模板是否正确
						if (value == "1"){
							if(formInfo.getForm().findField("messageModel").getValue()=='')
								return false;
						}
					}
				}
				}
			return true;
			}
	}
	
	//规则数据存储
	var Record = Ext.data.Record.create( [ {
		name : 'ruleId',
		mapping : 'RULE_ID'
	}, {
		name : 'ruleCode',
		mapping : 'RULE_CODE'
	}, {
		name : 'ruleName',
		mapping : 'RULE_NAME'
	}, {
		name : 'ruleRole',
		mapping : 'RULE_ROLE'
	},{
		name:'adjustCust',
		mapping:'ADJUST_CUST'
	},{
		name:'custAge',
		mapping:'CUST_AGE'
	},{
		name:'sex',
		mapping:'SEX'
	},{
		name:'beforeDays',
		mapping:'BEFORE_DAYS'
	},{
		name:'lastDays',
		mapping:'LAST_DAYS'
	},{
		name:'threshhold',
		mapping:'THRESHHOLD'
	},{
		name:'remindModle',
		mapping:'REMIND_MODLE'
	},{
		name:'ifMessage',
		mapping:'IF_MESSAGE'
	},{
		name:'followDeal',
		mapping:'FOLLOW_DEAL'
	},{
		name:'comCustLevel',
		mapping:'COM_CUST_LEVEL'
	},{
		name:'indivCustLevel',
		mapping:'INDIV_CUST_LEVEL'
	},{
		name:'messageModel',
		mapping:'MESSAGE_MODEL'
	}]);
	var store = new Ext.data.Store({
		restful:true,	
        proxy : new Ext.data.HttpProxy(
        		{
        			url:basepath+'/remindrule.json'
        		}),
        reader: new Ext.data.JsonReader({
            successProperty: 'success',
        root:'json.data',
        totalProperty: 'json.count'
        }, Record)
	});
	
	//根据所点击类型和当前法人机构号，查询规则配置并反显
	function initForm(frid,ruleCode,ifFirst){
		store.load({
			params : {
			frid:frid,
			ruleCode:ruleCode
        },
        callback:function(){
        	if(store.getCount()!=0){
        		formInfo.getForm().loadRecord(store.getAt(0));
        		//控制按钮
        		if(ifFirst){//true:点击节点时调用，需要控制按钮  false：点击重置时调用，不用控制按钮
        			panel.buttons[0].setDisabled(false);
        			panel.buttons[1].setDisabled(false);
        		}
        		//根据所加载的数据控制客户基线和短信模板是否可见
        		//适用客户类型字段
        		var value = formInfo.getForm().findField("adjustCust").getValue();
					if (value == "0") {//所有
						formInfo.getForm().findField("comCustLevel").show();
						formInfo.getForm().findField("indivCustLevel").show();
					}
					if (value == "1"){//零售
						formInfo.getForm().findField("indivCustLevel").show();
					}
					if (value == "2"){//对公
						formInfo.getForm().findField("comCustLevel").show();
					}
				//是否短信字段
				var value = formInfo.getForm().findField("ifMessage").getValue();	
					if (value == "1"){
						formInfo.getForm().findField("messageModel").show();
					}
				//判断提醒内容和短信内容是否可编辑
//				if(__frId!=''){
//					formInfo.getForm().findField("remindModle").setDisabled(true);
//					formInfo.getForm().findField("messageModel").setDisabled(true);
//				}
//				
        	}else{//未返回（当前为法人机构（非总行），总行未配置该规则），当前不能配置该规则
//        		if(frid!=''&&frid!=null){
//        			Ext.Msg.alert('提示','总行未配置该规则，不能配置');
//        		}else
        			if(ifFirst){//true:点击节点时调用，需要控制按钮  false：点击重置时调用，不用控制按钮
        			panel.buttons[0].setDisabled(false);
        			panel.buttons[1].setDisabled(false);
        		}
        	}
		}});
		
	}
	
	//数据字典 start
	//适用客户类型
	var rangeStore = new Ext.data.Store({
		restful:true,   
		autoLoad :true,
		proxy : new Ext.data.HttpProxy({
				url :basepath+'/lookup.json?name=TYPE_RANGE'
		}),
		reader : new Ext.data.JsonReader({
			root : 'JSON'
		}, [ 'key', 'value' ])
	});
	//COM_CUST_LEVEL	对公基线客户层级（字典）
	var comStore = new Ext.data.Store({
		restful:true,   
		autoLoad :true,
		sortInfo: {
	    	field: 'key',
	    	direction: 'ASC' // or 'DESC' (case sensitive for local sorting)
		},
		proxy : new Ext.data.HttpProxy({
				url :basepath+'/lookup.json?name=COM_CUST_LEVEL'
		}),
		reader : new Ext.data.JsonReader({
			root : 'JSON'
		}, [ 'key', 'value' ])
	});
	//INDIV_CUST_LEVEL	对私基线客户层级（字典）
	var indivStore = new Ext.data.Store({
		restful:true,   
		autoLoad :true,
		sortInfo: {
	    	field: 'key',
	    	direction: 'ASC' // or 'DESC' (case sensitive for local sorting)
		},
		proxy : new Ext.data.HttpProxy({
				url :basepath+'/lookup.json?name=INDIV_CUST_LEVEL'
		}),
		reader : new Ext.data.JsonReader({
			root : 'JSON'
		}, [ 'key', 'value' ])
	});
	//CUST_AGE	客户年龄控制（全部，60岁以上）
	var ageStore = new Ext.data.Store({
		restful:true,   
		autoLoad :true,
		proxy : new Ext.data.HttpProxy({
				url :basepath+'/lookup.json?name=CUST_AGE'
		}),
		reader : new Ext.data.JsonReader({
			root : 'JSON'
		}, [ 'key', 'value' ])
	});
	ageStore.load();
	//ADJUST_SEX	客户性别控制（全部，限女性）
	var sexStore = new Ext.data.Store({
		restful:true,   
		autoLoad :true,
		proxy : new Ext.data.HttpProxy({
				url :basepath+'/lookup.json?name=ADJUST_SEX'
		}),
		reader : new Ext.data.JsonReader({
			root : 'JSON'
		}, [ 'key', 'value' ])
	});
	sexStore.load();
	//FOLLOW_DEAL	后续处理方式（短信跟踪，电话跟踪，忽略）
	var followStore = new Ext.data.Store({
		restful:true,   
		autoLoad :true,
		proxy : new Ext.data.HttpProxy({
				url :basepath+'/lookup.json?name=FOLLOW_DEAL'
		}),
		reader : new Ext.data.JsonReader({
			root : 'JSON'
		}, [ 'key', 'value' ])
	});
	//角色
	var roleStore = new Ext.data.ArrayStore({
		fields : [ 'key', 'value' ],
		data : [ [ 'R_XHKHJL', '客户经理' ],
				[ 'R_XHLCJL', '理财经理' ],
				[ 'R_FHHZ', '分行行长' ]]
	});
	var ifStore = new Ext.data.Store({
		restful:true,   
		autoLoad :true,
		proxy : new Ext.data.HttpProxy({
				url :basepath+'/lookup.json?name=IF_FLAG'
		}),
		reader : new Ext.data.JsonReader({
			root : 'JSON'
		}, [ 'key', 'value' ])
	});
	 
	//数据字典 end
	
	// 获取提醒规则类型
	var remindType = new Com.yucheng.bcrm.ArrayTreeLoader({
		parentAttr : 'TYPE',//指向父节点的属性列
		locateAttr : 'F_CODE',//节点定位属性列，也是父属性所指向的列
		rootValue :'0',//虚拟根节点id 若果select的值为null则为根节点
		textField : 'F_VALUE',//用于展示节点名称的属性列
		idProperties : 'F_CODE'//,//指定节点ID的属性列
	});
	
	//数据加载请求
	Ext.Ajax.request({
		url : basepath + '/remindType.json',
		method:'GET',
		success:function(response){
			var nodeArra = Ext.util.JSON.decode(response.responseText).json.data;
			//添加类别控制
			nodeArra.push({F_CODE:'t1',F_VALUE:'账户变动情况',TYPE:'0'},
					{F_CODE:'t2',F_VALUE:'产品发售、到期和购买信息提醒',TYPE:'0'},
					{F_CODE:'t3',F_VALUE:'生日、节假日和客户重大事件提醒',TYPE:'0'},
					{F_CODE:'t4',F_VALUE:'客户状态变动情况提醒',TYPE:'0'},
					{F_CODE:'t5',F_VALUE:'客户联系和维护提醒',TYPE:'0'},
					{F_CODE:'t6',F_VALUE:'授信客户提醒',TYPE:'0'});
			remindType.nodeArray = nodeArra;
			var children = remindType.loadAll();
			Ext.getCmp('treePanel').appendChild(children);
			Ext.getCmp('treePanel').root.firstChild.expand();
		}
	});

	//左侧树形展示
	 var tree  = new Com.yucheng.bcrm.TreePanel({
		id:'treePanel',
		region : 'west',
		width : 300,
		autoScroll:true,
		checkBox : false, //是否现实复选框：
		resloader:remindType,
		split:true,
		root: new Ext.tree.AsyncTreeNode({//设置根节点
			id:'root',
			expanded:true,
			text:'提醒规则类型',
			autoScroll:true,
			children:[]
		}),
		clickFn:function(node){//单击事件
			//叶子节点的点击处理
			if(node.isLeaf()){
				showColums = 'ruleRole,adjustCust,remindModle,ifMessage,followDeal';
				
				//1.根据说点击的提醒类型，画出右侧表单
				formInfo.form.reset();
				formInfo.setTitle(node.text);
				formInfo.getForm().findField("ruleCode").setValue(node.id);
				formInfo.getForm().findField("ruleName").setValue(node.text);
				dealWithColums(colums,'hide');
				panel.buttons[0].setDisabled(true);
    			panel.buttons[1].setDisabled(true);
				
				if(node.id=='101'||node.id=='102')
					showColums += ',lastDays,threshhold';
				if(node.id=='201'||node.id=='207'||node.id.substring(0,1)=='4'||node.id.substring(0,1)=='5')
					showColums += ',lastDays';
				if(node.id=='202'||node.id=='208')
					showColums += ',beforeDays,lastDays,threshhold';
				if(node.id=='203'||node.id=='204'||node.id=='301'||node.id=='302'||node.id=='303'||node.id=='601'||node.id=='602')
					showColums += ',beforeDays,lastDays';
				if(node.id=='205'||node.id=='206')
					showColums += ',threshhold,lastDays';
				if(node.id=='304')
					showColums += ',custAge,beforeDays,lastDays';
				if(node.id=='305')
					showColums += ',sex,beforeDays,lastDays';
				
				dealWithColums(showColums,'show');
				
				//2.根据所点击类型和当前法人机构号，查询规则配置并反显
//				initForm(__frId,node.id,true);
				initForm('',node.id,true);
			}
	 	}
	 }); 
	
	 //表单内容
	 var formInfo =  new Ext.form.FormPanel({
			frame : true,
			title : '<font color="red">请选择提醒规则类型</font>',
			labelWidth : 150,
			labelAlign : 'right',
			items : [{
				xtype : 'fieldset',
				title : '提醒规则设置',
				autoHeight : true,
				anchor : '100%',
				layout : 'form',
				items : [{
                	xtype : 'textfield',
					fieldLabel : '规则id',
					name : 'ruleId',
					hidden:true,
					labelStyle: 'text-align:right;',
					editable : false,
					anchor : '60%'
				},{
					xtype : 'textfield',
					fieldLabel : '规则类别编号',
					name : 'ruleCode',
					hidden:true,
					editable : false,
					labelStyle: 'text-align:right;',
					anchor : '60%'
				},{
					xtype : 'textfield',
					fieldLabel : '规则名称',
					name : 'ruleName',
					hidden:true,
					labelStyle: 'text-align:right;',
					anchor : '60%'
				},new Ext.ux.form.LovCombo({
					fieldLabel : '接收角色',
					labelStyle : 'text-align:right;',
					name : 'ruleRole',
					hiddenName : 'ruleRole',
					hidden:true,
					triggerAction : 'all',
					store : roleStore,
					displayField : 'value',
					valueField : 'key',
					mode : 'local',
					emptyText : '请选择 ',
					resizable : true,
					hideOnSelect : false,
					triggerAction : 'all',
					allowBlank : false,
					editable : true,
					anchor : '60%'
				})
				,new Ext.form.ComboBox({
						name : 'adjustCust',
						hiddenName : 'adjustCust',
						fieldLabel : '适用客户类别',
						labelStyle: 'text-align:right;',
						hidden:true,
						triggerAction : 'all',
						store : rangeStore,
						displayField : 'value',
						valueField : 'key',
						mode : 'local',
						forceSelection : true,
						typeAhead : true,
						emptyText:'请选择',
						resizable : true,
						anchor : '60%',
						listeners:{
							//根据接受角色控制两个客户层级的是否显示
							"select" : function() {
								var type = formInfo.getForm().findField("adjustCust").getValue();
								if (type == "0") {//所有
									formInfo.getForm().findField("comCustLevel").show();
									formInfo.getForm().findField("comCustLevel").setValue('');
									formInfo.getForm().findField("indivCustLevel").show();
									formInfo.getForm().findField("indivCustLevel").setValue('');
								}
								if (type == "1"){//零售
									formInfo.getForm().findField("comCustLevel").hide();
									formInfo.getForm().findField("comCustLevel").setValue('');
									formInfo.getForm().findField("indivCustLevel").show();
									formInfo.getForm().findField("indivCustLevel").setValue('');
								}
								if (type == "2"){//对公
									formInfo.getForm().findField("indivCustLevel").hide();
									formInfo.getForm().findField("indivCustLevel").setValue('');
									formInfo.getForm().findField("comCustLevel").show();
									formInfo.getForm().findField("comCustLevel").setValue('');
								}
							}
						}
					}),new Ext.form.ComboBox({
						name : 'comCustLevel',
						hiddenName : 'comCustLevel',
						fieldLabel : '对公基线客户层级',
						labelStyle: 'text-align:right;',
						hidden:true,
						triggerAction : 'all',
						store : comStore,
						displayField : 'value',
						valueField : 'key',
						mode : 'local',
						forceSelection : true,
						typeAhead : true,
						emptyText:'请选择',
						resizable : true,
						anchor : '60%'
					}),new Ext.form.ComboBox({
						name : 'indivCustLevel',
						hiddenName:'indivCustLevel',
						hidden:true,
						fieldLabel : '对私基线客户层级',
						labelStyle: 'text-align:right;',
						triggerAction : 'all',
						store : indivStore,
						displayField : 'value',
						valueField : 'key',
						mode : 'local',
						forceSelection : true,
						typeAhead : true,
						emptyText:'请选择',
						resizable : true,
						anchor : '60%'
					}),new Ext.form.ComboBox({
						name : 'custAge',
						hiddenName : 'custAge',
						hidden:true,
						fieldLabel : '客户年龄控制',
						labelStyle: 'text-align:right;',
						triggerAction : 'all',
						store : ageStore,
						displayField : 'value',
						valueField : 'key',
						mode : 'local',
						forceSelection : true,
						typeAhead : true,
						emptyText:'请选择',
						resizable : true,
						anchor : '60%'
					}),new Ext.form.ComboBox({
						name : 'sex',
						hiddenName : 'sex',
						hidden:true,
						fieldLabel : '客户性别控制',
						labelStyle: 'text-align:right;',
						triggerAction : 'all',
						store : sexStore,
						displayField : 'value',
						valueField : 'key',
						mode : 'local',
						forceSelection : true,
						typeAhead : true,
						emptyText:'请选择',
						resizable : true,
						anchor : '60%'
					}), {
					xtype : 'numberfield',
					fieldLabel : '提醒提前天数',
//					maxValue : 90,
//					minValue : 15,
					decimalPrecision:0,
					hidden:true,
					labelStyle : 'text-align:right;',
					name : 'beforeDays',
					anchor : '60%'
				}, {
					xtype : 'numberfield',
					fieldLabel : '提醒持续天数',
//					maxValue : 90,
//					minValue : 15,
					decimalPrecision:0,
					hidden:true,
					labelStyle : 'text-align:right;',
					name : 'lastDays',
					anchor : '60%'
				}, {
					xtype : 'numberfield',
					fieldLabel : '变动阀值',
					decimalPrecision:2,
					hidden:true,
					labelStyle : 'text-align:right;',
					name : 'threshhold',
					anchor : '60%'
				}, {
					xtype : 'textarea',
					fieldLabel : '提醒模板',
					hidden:true,
					labelStyle : 'text-align:right;',
					name : 'remindModle',
					anchor : '60%'
				},new Ext.form.ComboBox({
					name : 'ifMessage',
					hiddenName : 'ifMessage',
					hidden:true,
					fieldLabel : '是否发送短信',
					labelStyle: 'text-align:right;',
					triggerAction : 'all',
					store : ifStore,
					displayField : 'value',
					valueField : 'key',
					mode : 'local',
					forceSelection : true,
					typeAhead : true,
					emptyText:'请选择',
					resizable : true,
					anchor : '60%',
					listeners:{
						"select" : function() {
							var type = formInfo.getForm().findField("ifMessage").getValue();
							if (type == "1") {//是
								formInfo.getForm().findField("messageModel").show();
							}
							if (type == "0"){//否
								formInfo.getForm().findField("messageModel").hide();
								formInfo.getForm().findField("messageModel").setValue('');
							}
						}
					}
				}), {
					xtype : 'textarea',
					hidden:true,
					fieldLabel : '短信内容模板',
					labelStyle : 'text-align:right;',
					name : 'messageModel',
					anchor : '60%'
				},new Ext.form.ComboBox({
					name : 'followDeal',
					hiddenName : 'followDeal',
					hidden:true,
					fieldLabel : '后续处理方式',
					labelStyle: 'text-align:right;',
					triggerAction : 'all',
					store : followStore,
					displayField : 'value',
					valueField : 'key',
					mode : 'local',
					forceSelection : true,
					typeAhead : true,
					emptyText:'请选择',
					resizable : true,
					anchor : '60%'
				})
				]
                }]
	 });
	 
	//右侧内容展示部分
	var panel = new Ext.Panel({
				region : 'center',
				layout : 'fit',
				frame : true, // 渲染面板
				buttonAlign : 'center',
				items : [ formInfo ],
				buttons : [{
							text : '保存',
							disabled:true,
							handler : function() {
								if(!ifRight(showColums)){
									Ext.Msg.alert('提示','请检查输入项');
									return;
								}
//								if(__frId==''){
									Ext.Ajax.request({
										url : basepath+ '/remindrule!saveData.json',
										method : 'POST',
										form : formInfo.getForm().id,
										success : function() {
											Ext.Msg.alert('提示','保存成功');
											//重新加载（返回ruleId）
//											initForm(__frId,
//													formInfo.getForm().findField("ruleCode").getValue()
//													,false);
											initForm('',
													formInfo.getForm().findField("ruleCode").getValue()
													,false);
										},
										failure : function() {
											Ext.Msg.alert('提示','保存失败');
										}
									});
//								}else{
//									Ext.Ajax.request({
//										url : basepath+ '/remindrule!saveData.json',
//										method : 'POST',
//										form : formInfo.getForm().id,
//										params : {
//											remindModle:formInfo.getForm().findField("remindModle").getValue(),
//											messageModel:formInfo.getForm().findField("messageModel").getValue()
//	      								},
//										success : function() {
//											Ext.Msg.alert('提示','保存成功');
//											//重新加载（返回ruleId）
//											initForm(__frId,
//													formInfo.getForm().findField("ruleCode").getValue()
//													,false);
//										},
//										failure : function() {
//											Ext.Msg.alert('提示','保存失败');
//										}
//									});
//								}
								
							}
						},
						{
							text : '重置',
							disabled:true,
							handler : function() {
								var ruleCode = formInfo.getForm().findField("ruleCode").getValue();
								formInfo.form.reset();
								//根据所点击类型和当前法人机构号，查询规则配置并反显
//								initForm(__frId,
//										formInfo.getForm().findField("ruleCode").getValue()
//										,false);
								initForm('',
										ruleCode
										,false);
							}
						} ]
			});
	
	new Ext.Viewport({
		layout : 'border',
		items : [ tree, panel ]
	});
	
});