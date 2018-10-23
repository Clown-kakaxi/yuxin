/**
 * 规则的设置页面（带公示）
 */
var ifIndex = "";//if表达式的index  格式为1&2#5&9  #为表达式之间的间隔，&为起始的间隔

//拼接if表达式的位置参数
function getIfIndex(formula){
	var temp = formula;
	var index = 0;
	var begin = 0;//开始
	var end = 0;//结束
	var short = 0;//去掉的字符长度
	var ifIndexTemp = "";
	while(temp.indexOf('IF')!=-1){
		temp = temp.substring(temp.indexOf('IF'));//直接取第一个IF开始的部分
		index = temp.indexOf(')');//此处为紧跟着IF的那个）
		begin = temp.indexOf("IF");
		end = temp.indexOf(")");
		short = formula.length-temp.length;
		ifIndexTemp = (begin+short)+"&"+(end+short)+"#";
		ifIndex += ifIndexTemp;
		temp = temp.substring(index);//去掉IF表达式部分
	}
	
	ifIndex = ifIndex.substring(0,ifIndex.length-1);
}


//计算频率
	var countFrequenceStore = new Ext.data.Store( {
		restful : true,
		autoLoad : true,
		proxy : new Ext.data.HttpProxy( {
			url : basepath + '/lookup.json?name=COUNT_FREQUENCE'
		}),
		reader : new Ext.data.JsonReader( {
			root : 'JSON'
		}, [ 'key', 'value' ])
	});
	countFrequenceStore.load();

//规则用途
	var ruleUseStore = new Ext.data.Store( {
		restful : true,
		autoLoad : true,
		proxy : new Ext.data.HttpProxy( {
			url : basepath + '/lookup.json?name=RULE_USE'
		}),
		reader : new Ext.data.JsonReader( {
			root : 'JSON'
		}, [ 'key', 'value' ])
	});
	ruleUseStore.load();
	//客户类型范围
	var typeRangeStore = new Ext.data.Store( {
		restful : true,
		autoLoad : true,
		proxy : new Ext.data.HttpProxy( {
			url : basepath + '/lookup.json?name=TYPE_RANGE'
		}),
		reader : new Ext.data.JsonReader( {
			root : 'JSON'
		},['key','value'])
	});
	typeRangeStore.load();
	//计算方式
	var countWayStore = new Ext.data.Store( {
		restful : true,
		autoLoad : true,
		proxy : new Ext.data.HttpProxy( {
			url : basepath + '/lookup.json?name=COUNT_WAY'
		}),
		reader : new Ext.data.JsonReader( {
			root : 'JSON'
		},['key','value'])
	});
	countWayStore.load();
	
	//是否启用
	var ifStore = new Ext.data.Store( {
		restful : true,
		autoLoad : true,
		proxy : new Ext.data.HttpProxy( {
			url : basepath + '/lookup.json?name=IF_FLAG'
		}),
		reader : new Ext.data.JsonReader( {
			root : 'JSON'
		},['key','value'])
	});
	ifStore.load();
	
//规则编辑form
var ruleSetForm = new Ext.form.FormPanel({
    frame : true,
    title : '折算规则',
    buttonAlign : "center",
    region : 'center',
    autoScroll : true,
    labelWidth : 100,
    items:[{
    	layout : 'column',
        items:[
                {columnWidth : .5,
	            layout : 'form',
	            items :[{
	            	xtype : 'textfield',
					fieldLabel : '<span style="color:red">*</span>规则名称',
					name : 'scoreName',
					labelStyle: 'text-align:right;',
					allowBlank:false,
					anchor : '100%'
	            },new Com.yucheng.crm.common.IndexSearchField({
	    			xtype:'indexchoose',
	    			fieldLabel : '指标', 
	    			id:'INDEX1',
	    			labelStyle: 'text-align:right;',
	    			name : 'indexName',
	    			hiddenName:'indexCode',
	    			searchType:'INDEXTREEBYTYPE',//INDEXTREE：全部可以查询   INDEXTREEBYTYPE ：需要根据一个下拉框的状态来控制类别
	    			singleSelect:true,
	    			allowBlank:false,
	    			anchor : '100%',
	    			callback: function() {
	    				getFormula();
	    			}
	    		}),{
	            	store : ifStore,
					xtype : 'combo', 
					resizable : true,
					fieldLabel : '<span style="color:red">*</span>是否启用',
					hiddenName : 'status',
					name : 'status',
					valueField : 'key',
					labelStyle : 'text-align:right;',
					displayField : 'value',
					mode : 'local',
					allowBlank:false,
					forceSelection : true,
					triggerAction : 'all',
					emptyText : '请选择',
					anchor : '100%'
	            },{
	            	store : countWayStore,
					xtype : 'combo', 
					resizable : true,
					fieldLabel : '<span style="color:red">*</span>计算方式',
					hiddenName : 'computeType',
					name : 'computeType',
					allowBlank:false,
					valueField : 'key',
					labelStyle : 'text-align:right;',
					displayField : 'value',
					mode : 'local',
					forceSelection : true,
					triggerAction : 'all',
					emptyText : '请选择',
					anchor : '100%',
					listeners : {
						"select" : function() {
							var type = ruleSetForm.getForm().findField("computeType").getValue();
							if (type == "1") {//直接折算
								ruleSetForm.getForm().findField("convertRate").show();
								ruleSetForm.getForm().findField("convertRate").setValue('');
								ruleSetForm.getForm().findField("convertRate").setDisabled(false);
								
							} else {
								ruleSetForm.getForm().findField("convertRate").hide();
								ruleSetForm.getForm().findField("convertRate").setValue('');
								ruleSetForm.getForm().findField("convertRate").setDisabled(true);
							}
							
							ruleSetForm.getForm().findField("formula").setValue('');
							ruleSetForm.getForm().findField("formulaMean").setValue('');
						}
					}
	            }]
        	
                },
                {columnWidth : .5,
    	            layout : 'form',
    	            items :[{
    	            	store : ruleUseStore,
						xtype : 'combo', 
						resizable : true,
						fieldLabel : '<span style="color:red">*</span>用途',
						hiddenName : 'useWay',
						name : 'useWay',
						valueField : 'key',
						labelStyle : 'text-align:right;',
						displayField : 'value',
						mode : 'local',
						allowBlank:false,
						forceSelection : true,
						triggerAction : 'all',
						emptyText : '请选择',
						anchor : '100%',
						listeners : {
							"select" : function() {
								var useWay = ruleSetForm.getForm().findField("useWay").getValue();
								document.getElementById('useType').value = useWay;
							}
						}
    	            },{
    	            	store : typeRangeStore,
						xtype : 'combo', 
						resizable : true,
						fieldLabel : '<span style="color:red">*</span>适用客户类型',
						hiddenName : 'custType',
						name : 'custType',
						allowBlank:false,
						valueField : 'key',
						labelStyle : 'text-align:right;',
						displayField : 'value',
						mode : 'local',
						forceSelection : true,
						triggerAction : 'all',
						emptyText : '请选择',
						anchor : '100%'
    	            },{
    	            	store : countFrequenceStore,
						xtype : 'combo', 
						resizable : true,
						fieldLabel : '<span style="color:red">*</span>计算频率',
						hiddenName : 'frequence',
						name : 'frequence',
						allowBlank:false,
						valueField : 'key',
						labelStyle : 'text-align:right;',
						displayField : 'value',
						mode : 'local',
						forceSelection : true,
						triggerAction : 'all',
						emptyText : '请选择',
						anchor : '100%'
    	            },{
    	            	xtype : 'numberfield',
    					fieldLabel : '<span style="color:red">*</span>折算率',
    					name : 'convertRate',
    					id:'convertRate',
    					decimalPrecision : 6 ,
    					labelStyle: 'text-align:right;',
    					hidden:true,
    					anchor : '100%'
    	            },{
    	            	xtype : 'textfield',
    					fieldLabel : 'id',
    					name : 'id',
    					hidden:true,
    					anchor : '100%'
    	            }]
            	
                    },{
                    	columnWidth : 1,
        	            layout : 'form',
        	            items :[{
							xtype : 'textarea',
							fieldLabel : '<font color=red>*</font>公式',
							name : 'formula',
							id : 'formula',
							labelStyle: 'text-align:right;',
							allowBlank:false,
							readOnly:true,
							anchor : '100%'
						}, {
								xtype : 'textarea',
								fieldLabel : '<font color=red>*</font>公式解释',
								name : 'formulaMean',
								allowBlank:false,
								labelStyle: 'text-align:right;',
								readOnly:true,
								anchor : '100%'
							}]
                    }
                ]
    }]
});
//根据折算率生成公示
function getFormula(){
	if(ruleSetForm.getForm().findField("computeType").getValue()=='1')
		if((ruleSetForm.form.findField('convertRate').getValue()==''||ruleSetForm.form.findField('convertRate').getValue()==null)||
				(ruleSetForm.form.findField('indexCode').getValue()==''||ruleSetForm.form.findField('indexCode').getValue()==null))
					return ;
				else{
					ruleSetForm.form.findField('formula').setValue(
							ruleSetForm.form.findField('indexCode').getValue()+'*'+ruleSetForm.form.findField('convertRate').getValue());
					ruleSetForm.form.findField('formulaMean').setValue(
							ruleSetForm.form.findField('indexName').getValue()+'*'+ruleSetForm.form.findField('convertRate').getValue());
				}
}
Ext.getCmp('convertRate').on
("blur",function(){
	getFormula();
});
//编辑器窗口
var FormulaWindow = new Ext.Window
(
	{
		plain : true,
		defaults :
		{
			overflow :'auto',
			autoScroll :true
		},
		layout : 'fit',
		frame : true,
		resizable : true,
		draggable : true,
		closable : true,
		closeAction : 'hide',
		modal : true, // 模态窗口
		shadow : true,
		loadMask : true,
		maximizable : true,
		collapsible : true,
		titleCollapse : true,
		border : false,
		width : 450,
		height : 420,
		buttonAlign : "center",
		title : '公式管理',
		buttons:
		[
			{
				text : '确定',
				handler:function()
				{
					if (!checkFormula()) {
						Ext.Msg.alert('提示', '公式配置有误，请检查！');
						return false;
					}
					ruleSetForm.getForm().findField("formula").setValue(Ext.getCmp('FORMULAWINDOWT').getValue());
					ruleSetForm.getForm().findField("formulaMean").setValue(Ext.getCmp('FORMULAWINDOWW').getValue());
					
					FormulaWindow.hide();
				}
			},
			'-',
		 	{
		 		text : '返回',
		 		handler:function()
		 		{
		 			FormulaWindow.hide();
		 		}
		 	}
		 ]
	}
);

//打开公司编辑器页面
Ext.getCmp('formula').on
("focus",function(){
	if(ruleSetForm.getForm().findField("computeType").getValue()=='1'||ruleSetForm.getForm().findField("computeType").getValue()==null
			||ruleSetForm.getForm().findField("computeType").getValue()=='')
		return;
	else if(ruleSetForm.form.findField('indexCode').getValue()==''||ruleSetForm.form.findField('indexCode').getValue()==null){
		 Ext.MessageBox.alert('提示','请先选择指标');
		 return ;
	}else{
		FormulaWindow.removeAll(true);
		FormulaWindow.add({
			width : 210,
			height : document.body.clientHeight,
			layout : 'border',
			// frame : true ,
			items : [{
						region : 'north',
						id : 'FORMULAWINDOW',
						height : 75,
						title : '公式表达式',
						items : [{
									// fieldLabel : 'INDEX_ID',
									name : 'FORMULAWINDOWT',
									id : 'FORMULAWINDOWT',
									// xtype : 'textfield', // 设置为数字输入框类型
									width : 450,
									xtype : 'textarea',
									labelStyle : 'text-align:right;',
									disabled : true,
									anchor : '90%',
									enableKeyEvents : true,
									listeners : {
										'keypress' : function(field, e) {
											maskKeyEvent(e);
										},
										'specialkey' : function(field, e) {
											maskKeyEvent(e);
										},
										'focus' : function() {
											storeCaretWT(this);
										}
									}
								}]
					}, {
						region : 'center',
						id : 'FORMULACONTENTWINDOW',
						title : '中文表达式',
						items : [{
									// fieldLabel : 'INDEX_ID',
									name : 'FORMULAWINDOWW',
									id : 'FORMULAWINDOWW',
									// xtype : 'textfield', // 设置为数字输入框类型
									width : 450,
									xtype : 'textarea',
									labelStyle : 'text-align:right;',
									disabled : true,
									anchor : '90%'
								}]

					}, {
						region : 'south',
						id : 'COUNTWINDOW',
						height : 200,
						title : '计算器',
						layout : 'border',
						items : [{
									region : 'center',
									id : 'FORMULAWINDOW1',
									width : 310,
									title : '基本输入',
									layout : 'column',
									items : [{
												columnWidth : .16666,
												layout : 'form',
												// labelWidth : 100, // 标签宽度
												// defaultType : 'textfield',
												border : false,
												items : [
														new Ext.Button({
																	text : '1',
																	height : 50,
																	width : 50,
																	handler : function() {
																		insertWT('1');
																		transCode2Name();
																	}
																}),
														new Ext.Button({
																	text : '2',
																	height : 50,
																	width : 50,
																	handler : function() {
																		insertWT('2');
																		transCode2Name();
																	}
																}),
														new Ext.Button({
																	text : '3',
																	height : 50,
																	width : 50,
																	handler : function() {
																		insertWT('3');
																		transCode2Name();
																	}
																})]
											}, {
												columnWidth : .16666,
												layout : 'form',
												// labelWidth : 100, // 标签宽度
												// defaultType : 'textfield',
												border : false,
												items : [
														new Ext.Button({
																	text : '4',
																	height : 50,
																	width : 50,
																	handler : function() {
																		insertWT('4');
																		transCode2Name();
																	}
																}),
														new Ext.Button({
																	text : '5',
																	height : 50,
																	width : 50,
																	handler : function() {
																		insertWT('5');
																		transCode2Name();
																	}
																}),
														new Ext.Button({
																	text : '6',
																	height : 50,
																	width : 50,
																	handler : function() {
																		insertWT('6');
																		transCode2Name();
																	}
																})]
											}, {
												columnWidth : .16666,
												layout : 'form',
												// labelWidth : 100, // 标签宽度
												// defaultType : 'textfield',
												border : false,
												items : [
														new Ext.Button({
																	text : '7',
																	height : 50,
																	width : 50,
																	handler : function() {
																		insertWT('7');
																		transCode2Name();
																	}
																}),
														new Ext.Button({
																	text : '8',
																	height : 50,
																	width : 50,
																	handler : function() {
																		insertWT('8');
																		transCode2Name();
																	}
																}),
														new Ext.Button({
																	text : '9',
																	height : 50,
																	width : 50,
																	handler : function() {
																		insertWT('9');
																		transCode2Name();
																	}
																})]
											}, {
												columnWidth : .16666,
												layout : 'form',
												// labelWidth : 100, // 标签宽度
												// defaultType : 'textfield',
												border : false,
												items : [
														new Ext.Button({
																	text : '0',
																	height : 50,
																	width : 50,
																	handler : function() {
																		insertWT('0');
																		transCode2Name();
																	}
																}),
														new Ext.Button({
																	text : '+',
																	height : 50,
																	width : 50,
																	handler : function() {
																		insertWT('+');
																		transCode2Name();
																	}
																}),
														new Ext.Button({
																	text : '-',
																	height : 50,
																	width : 50,
																	handler : function() {
																		insertWT('-');
																		transCode2Name();
																	}
																})]
											}, {
												columnWidth : .16666,
												layout : 'form',
												// labelWidth : 100, // 标签宽度
												// defaultType : 'textfield',
												border : false,
												items : [
														new Ext.Button({
																	text : '*',
																	height : 50,
																	width : 50,
																	handler : function() {
																		insertWT('*');
																		transCode2Name();
																	}
																}),
														new Ext.Button({
																	text : '/',
																	height : 50,
																	width : 50,
																	handler : function() {
																		insertWT('/');
																		transCode2Name();
																	}
																}),
														new Ext.Button({
																	text : '(',
																	height : 50,
																	width : 50,
																	handler : function() {
																		insertWT('(');
																		transCode2Name();
																	}
																})]
											}, {
												columnWidth : .16666,
												layout : 'form',
												// labelWidth : 100, // 标签宽度
												// defaultType : 'textfield',
												border : false,
												items : [
														new Ext.Button({
																	text : ')',
																	height : 50,
																	width : 50,
																	handler : function() {
																		insertWT(')');
																		transCode2Name();
																	}
																}),
														new Ext.Button({
																	text : '.',
																	height : 50,
																	width : 50,
																	handler : function() {
																		insertWT('.');
																		transCode2Name();
																	}
																}),
														new Ext.Button({
																	text : '%',
																	height : 50,
																	width : 50,
																	handler : function() {
																		insertWT('%');
																		transCode2Name();
																	}
																})]
											}

									]
								},
	    					 	{
	    					 		region:'east',
	    					 		id:'FORMULACONTENTWINDOW3',
	    					 		width :140,
	    					 		title:'指标',
	    					 		items:
	    					 		[new Ext.Button
	    					 		 	(
		    					 		 		{
                                                    text:'当前指标('+ruleSetForm.form.findField('indexCode').getValue()+')',
                                                    height : 50,
                                                    width : 140,
                                                    handler:function()
                                                    {
                                                    	insertWT(ruleSetForm.form.findField('indexCode').getValue());
													transCode2Name();
													}
                                                }
		    					 		 	),
											 	new Ext.Button
											 	({
								                text:'IF表达式',
								                height : 50,
								                width : 140,
								                handler:function()
								                {
								                	/**********IF表达式编辑********************/
								                	//规则编辑面板
								                	var ruleForm = new Ext.form.FormPanel({
								                	    frame : true,
								                	    buttonAlign : "center",
								                	    region : 'south',
								                	    autoScroll : true,
								                	    height : 120,
								                	    labelWidth : 140,
								                	    items:[{ 
								                	    	layout : 'column',
								                	        items:[
								                	               {columnWidth : 1,
								                	   	            layout : 'form',
								                	            	 items:[{
								                	            		 xtype : 'textfield',
								                	  					fieldLabel : '默认值',
								                	  					name : 'number',
								                	  					value:'0',
								                	  					anchor : '100%'
								                	             	 },{
								                	            		 xtype : 'textfield',
								                	  					fieldLabel : '表达式',
								                	  					name : 'express',
								                	  					readOnly:true,
								                	  					anchor : '100%'
								                	             	 },{
								                	            		 xtype : 'textfield',
								                	 					fieldLabel : '解释',
								                	 					name : 'expressMean',
								                	 					readOnly:true,
								                	 					anchor : '100%'
								                	            	 }]  
								                	               }]
								                	    }]
								                	});
								                	var conditionStore = new Ext.data.Store({//数据存储
								                	  	restful:true,
								                	  	  reader: new Ext.data.JsonReader({
								                	  		 root:'rows',
								                	         totalProperty: 'num'
								                	  },  [{name: 'indexCode'}
								                	      ,{name: 'indexName'}
								                	      ,{name: 'operate'}
								                	      ,{name: 'compareValue'}
								                	      ,{name: 'value'}
								                	      ])
								                		});
								                			  

								                	var tarDictColumns = new Ext.grid.ColumnModel({
								                	    columns : [ {
								                	               header : '指标',
								                	               width : 100,
								                	               align : 'center',
								                	               hidden:true,
								                	               dataIndex : 'indexCode',
								                	               sortable : false
								                	               }, {
								                	                   header : '比较对象1',
								                	                   width : 200,
								                	                   align : 'center',
								                	                   dataIndex : 'indexName',
								                	                   sortable : false
								                	                   },
								                					{
								                	               header : '比较符',
								                	               width : 50,
								                	               align : 'center',
								                	               dataIndex : 'operate',
								                	               sortable : false,
								                					editor : new Ext.form.ComboBox({
								                					typeAhead : true,
								                					triggerAction : 'all',
								                					lazyRender : true,
								                					listClass : 'x-combo-list-small',
								                					mode : 'local',
								                					valueField : 'myId1',
								                					displayField : 'displayText1',
								                					store : new Ext.data.ArrayStore({
								                						id : 'tarName',
								                						fields : ['myId1', 'displayText1'],
								                						data : [['>', '大于'],['>=', '大于等于'],
								                						       ['<', '小于'],['<=', '小于等于'],
								                						       ['=', '等于']
								                								]})
								                					})},
								                				 {
								                	             header : '比较对象2',
								                	             width : 100,
								                	             align : 'center',
								                	             dataIndex : 'compareValue',
								                	             sortable : false,
								                	             editor : new Ext.form.Field()
								                	        		}, {
								                	                    header : '成立值',
								                	                    width : 100,
								                	                    align : 'center',
								                	                    dataIndex : 'value',
								                	                    sortable : false,
								                	                    editor : new Ext.form.Field()
								                	               		}]
								                				});	




								                	var onAdd = function(){
								                	    var u = new conditionStore.recordType({
								                			"indexCode" :ruleSetForm.getForm().findField("indexCode").getValue(),
								                			"indexName" :ruleSetForm.getForm().findField("indexName").getValue(),
								                			"operate":"",
								                			"compareValue" :"",
								                			"value" :""
								                	    });
								                	    conditionGrid.stopEditing();
								                	    conditionStore.insert(0, u);
								                	    conditionGrid.startEditing(0, 0);
								                	    ruleForm.getForm().findField('express').setValue('');
								                	    ruleForm.getForm().findField('expressMean').setValue('');
								                	};
								                	var onDelete = function(){
								                		 var index = conditionGrid.getSelectionModel().getSelectedCell();
								                	     if (!index) {
								                	     	alert("请选择一条记录");
								                	         return false;
								                	     }
								                	     var rec = conditionStore.getAt(index[0]);
								                	     conditionStore.remove(rec);
								                	     ruleForm.getForm().findField('express').setValue('');
								                	     ruleForm.getForm().findField('expressMean').setValue('');
								                		};
								                		var express = '';
								                		var expressMean = '';
								                		var checkResult = function() {
								                			if(conditionStore.getCount()<1){
								                				Ext.Msg.alert('系统提示','您没有添加任何规则');
								                				return;
								                			}else{
								                				express = 'IF(';
								                				expressMean = 'IF(';
								                				for(var i=0;i<conditionStore.getCount();i++){
								                		                var temp=conditionStore.getAt(i);
								                		                    if(""!=temp.data.value&&null!=temp.data.value){
								                		                    }else{
								                		                    Ext.Msg.alert('系统提示','成立值不能为空');
								                		                    return false;
								                		                    }if(""!=temp.data.operate&&null!=temp.data.operate){
								                		                    }else{
								                		                    Ext.Msg.alert('系统提示','比较符不能为空');
								                		                    return false;
								                		                    }if(""!=temp.data.compareValue&&null!=temp.data.compareValue){
								                		                    }else{
								                		                    Ext.Msg.alert('系统提示','比较对象2不能为空');
								                		                    return false;
								                		                    }
								                		                    express += temp.data.indexCode+temp.data.operate+temp.data.compareValue+':'+temp.data.value+';';
								                		                    expressMean+= temp.data.indexName+temp.data.operate+temp.data.compareValue+':'+temp.data.value+';';;
								                		         }
								                				ruleForm.getForm().findField('express').setValue(
								                						express+ruleForm.getForm().findField('number').getValue()+')');
								                				ruleForm.getForm().findField('expressMean').setValue(
								                						expressMean+ruleForm.getForm().findField('number').getValue()+')');
								                			 	return true;
								                			}
								                	};

								                	//规则编辑列表
								                	var conditionGrid = new Ext.grid.EditorGridPanel({
								                		tbar : [{
								                	        text : '新增',
								                	        iconCls:'addIconCss',
								                	        handler:function() {
								                	        onAdd();
								                	    }},{
								                	    text : '删除',
								                	    iconCls:'deleteIconCss',
								                	    handler:function() {
								                	        onDelete();
								                	    },
								                	    scope: this
								                	    }
								                		],
								                		height : 300,
								                		store : conditionStore,
								                		frame : true,
								                		cm : tarDictColumns,
								                		stripeRows : true,
								                		clicksToEdit : 1
								                	});

								                	//规则编辑panel
								                	var conditionForm = new Ext.form.FormPanel({
								                		labelWidth : 150,
								                		height : 200,
								                		frame : true,
								                		autoScroll : true,
								                		region : 'center',
								                		buttonAlign : "center",
								                		items : [
								                		         conditionGrid
								                					],
								                					buttons : [
								                						{
								                						text : '规则校验',
								                						handler : function(){
								                						if(checkResult()){
								                						 Ext.Msg.alert('系统提示','校验通过');
								                						};
								                						}
								                					}]

								                	});
								                	conditionGrid.on('cellclick',function(grid,row,col){//获取编辑的行数，从0开始，
								                		rowNo1=row;	
								                	});
								                	//If表达式编辑窗口
								                	var IndexWindow = new Ext.Window
								            		({
								            				plain : true,
								            				defaults :
								            				{
								            					overflow :'auto',
								            					autoScroll :true
								            				},
								            				layout : 'border',
								            				frame : true,
								            				resizable : true,
								            				draggable : true,
								            				closable : true,
								            				closeAction : 'hide',
								            				modal : true, // 模态窗口
								            				shadow : true,
								            				loadMask : true,
								            				maximizable : true,
								            				collapsible : true,
								            				titleCollapse : true,
								            				border : false,
								            				width : 600,
								            				height : 400,
								            				buttonAlign : "center",
								            				title : 'IF表达式',
								            				buttons:[{
							            				 		text : '确定',
							            				 		handler:function()
							            				 		{
							            				 			//拼接if语句，然后写到表达式中
							            				 			if(!checkResult()){
							            				 				Ext.Msg.alert('系统提示', '校验未通过，请检查');
							            								 return false;
							            								};
							            								insertWT(ruleForm.getForm().findField('express').getValue());
							            								transCode2Name();
							            				 			IndexWindow.hide();
							            				 		}
							            				 	},{
								            				 		text : '返回',
								            				 		handler:function()
								            				 		{
								            				 			IndexWindow.hide();
								            				 		}
								            				 	}],
								            				items:[
																conditionForm,
																ruleForm
								            				]}
								            		);
									                	IndexWindow.show();
								                }
								            }
									 	),new Ext.Button
									 	(
										 		{
									                text:'<-',
									                height : 50,
									                width : 140,
									                handler:function()
									                {
									                	delContent();
														transCode2Name();
                                                    	
									                }
									            }),
									 	
									 	{
	    					 		 		fieldLabel : 'INDEX_ID',
	    									name : 'WINDOW_INDEX_ID',
	    									id : 'WINDOW_INDEX_ID',
	    									xtype : 'hidden',
	    									labelStyle: 'text-align:right;',
	    									anchor : '90%'
									 	},
									 	{
	    					 		 		fieldLabel : 'INDEX_ID',
	    									name : 'TEMP',
	    									id : 'TEMP',
	    									value:'',
	    									xtype : 'hidden',
	    									labelStyle: 'text-align:right;',
	    									anchor : '90%'
									 	},
									 	{
	    					 		 		fieldLabel : 'INDEX_ID',
	    									name : 'TEMPCONTENT',
	    									id : 'TEMPCONTENT',
	    									value:'',
	    									xtype : 'hidden',
	    									labelStyle: 'text-align:right;',
	    									anchor : '90%'
									 	}
	    					 		]
	    					 		
	    					 	}
					 		]
					 	}
					]
				}
			);
			Ext.getCmp('FORMULAWINDOWT').setValue(ruleSetForm.getForm().findField("formula").getValue());
			Ext.getCmp('FORMULAWINDOWW').setValue(ruleSetForm.getForm().findField("formulaMean").getValue());
			FormulaWindow.show();
			transCode2Name();
			//重新计算ifIndex
			var wtValue = Ext.getCmp('FORMULAWINDOWT').getValue();
			ifIndex = "";
			getIfIndex(wtValue);
		
	}
				
	
	
});
	
//将“评级公式”转换成中文
function transCode2Name() {
	var wtValue = Ext.getCmp('FORMULAWINDOWT').getValue();
	if (wtValue && wtValue != "") {
		// 将“评级公式”中的指标编码转换成指标名称，其它字符不变
	wtValue = wtValue.replace(new RegExp(ruleSetForm.form.findField('indexCode').getValue(), "gm"), 
			ruleSetForm.form.findField('indexName').getValue());// 替换所有指标（如果有多个相同指标，替换所有相同的指标）
	}
	Ext.getCmp('FORMULAWINDOWW').setValue(wtValue);// 赋值
	Ext.getCmp('FORMULAWINDOWT').setDisabled(false);// 让“评级公式”输入框可编辑
}


// 在textarea的光标处增加字符
function insertWT(value) {
	var FORMULAWINDOWT = Ext.getCmp("FORMULAWINDOWT");
	if (Ext.isIE) {
		insertAtCaretWT(FORMULAWINDOWT.el.dom, value);
	} else {
		var startPos = FORMULAWINDOWT.el.dom.selectionStart;
		var endPos = FORMULAWINDOWT.el.dom.selectionEnd;
		FORMULAWINDOWT.el.dom.value = FORMULAWINDOWT.el.dom.value.substring(0,
				startPos)
				+ value
				+ FORMULAWINDOWT.el.dom.value.substring(endPos,
						FORMULAWINDOWT.el.dom.value.length);

		FORMULAWINDOWT.el.focus();
		FORMULAWINDOWT.el.dom.setSelectionRange(endPos + value.length, endPos
						+ value.length);
	}
	//重新计算ifIndex
	var textareaObj = Ext.getCmp('FORMULAWINDOWT');
	var wtValue = textareaObj.getValue();
	ifIndex = "";
	getIfIndex(wtValue);
}

function storeCaretWT() {
	if (Ext.getCmp("FORMULAWINDOWT").el.dom.createTextRange) {
		Ext.getCmp("FORMULAWINDOWT").el.dom.curRange = document.selection
				.createRange().duplicate();
	}
}

// 给textarea定义单击事件
Ext.getDoc().on('click', function handleDocClick(e) {
	if (Ext.getCmp("FORMULAWINDOWT")
			&& document.activeElement.id == "FORMULAWINDOWT") {
		if (Ext.getCmp("FORMULAWINDOWT").el.dom.createTextRange) {
			Ext.getCmp("FORMULAWINDOWT").el.dom.curRange = document.selection
					.createRange().duplicate();
		}
	}
});

// 在textarea的光标处插入字符
function insertAtCaretWT(txtobj, txt) {
	if (txtobj.curRange) {
		Ext.getCmp("FORMULAWINDOWT").el.focus();
		txtobj.curRange.text = txt;
		txtobj.curRange.select();
	} else {
		txtobj.focus();
		storeCaretWT(txtobj);
		insertAtCaretWT(txtobj, txt);
	}
}

// 屏蔽键盘事件
function maskKeyEvent(event) {
	event.stopEvent();
}

// 删除textarea中光标前的一个字符
function delContent() {
	var cusorPos = getCusorPostion();// 获取光标位置
	var wasIn  = false; //光标是否处于表达式之间
	debugger;
	if (cusorPos > 0) {// 光标位置大于0（光标前有字符）
		if(ifIndex!=""){//包含IF表达式
			var indexs = ifIndex.split("#");
			for(var i = 0; i < indexs.length; i++){
				var tempindexs = indexs[i].split("&");
				cusorPosTemp = Number(cusorPos)-1;
				if(cusorPosTemp>=Number(tempindexs[0])&&cusorPosTemp<=Number(tempindexs[1])){//处理光标在IF表达式之间的情况
					//因为每次光标必然只可能在一个IF表达式中，所以把起始值记录下来，因为Ext.MessageBox.confirm在for结束之后才弹出，
					//这时的tempindexs已改变，会变成删除最后一个IF表达式
					var bedgan = Number(tempindexs[0]);
					var end =  Number(tempindexs[1]);
					Ext.MessageBox.confirm('提示', 'IF表达式不可以手动编辑，是否直接删除本IF表达式？', function(buttonId) {
							if (buttonId.toLowerCase() == "no") {//不删除则将光标移至起始位置
								go2Pos(0);
								return;
							}
							//删除IF表达式，重新计算ifIndex，重新翻译公式，光标置于原IF的地方
							var textareaObj = Ext.getCmp('FORMULAWINDOWT');
							var wtValue = textareaObj.getValue();
							var preValue = wtValue.substring(0,bedgan );
							var sufValue = wtValue.substring(end+1);
							textareaObj.setValue(preValue + sufValue);
							ifIndex = "";
							getIfIndex(preValue + sufValue);
							transCode2Name();
							go2Pos(Number(tempindexs[0])+1);
							return;
						});
					wasIn  = true;
				}
			}
			//处理光标不在IF表达式之间的情况
			if(!wasIn){
				var textareaObj = Ext.getCmp('FORMULAWINDOWT');
				var wtValue = textareaObj.getValue();
				var preValue = wtValue.substring(0, cusorPos - 1);
				var sufValue = wtValue.substring(cusorPos);
				textareaObj.setValue(preValue + sufValue);
				ifIndex = "";
				getIfIndex(preValue + sufValue);
				go2Pos(cusorPos - 1);
			}
		}else{//不包含IF表达式
			var textareaObj = Ext.getCmp('FORMULAWINDOWT');
			var wtValue = textareaObj.getValue();
			var preValue = wtValue.substring(0, cusorPos - 1);
			var sufValue = wtValue.substring(cusorPos);
			textareaObj.setValue(preValue + sufValue);
			ifIndex = "";
			getIfIndex(preValue + sufValue);
			go2Pos(cusorPos - 1);
		}
	}
}

// 获取textarea中光标的位置
function getCusorPostion() {
	var txb = document.getElementById('FORMULAWINDOWT');// 根据ID获得对象
	var pos = 0;// 设置初始位置
	txb.focus();// 输入框获得焦点,这句也不能少,不然后面会出错,血的教训啦.
	var s = txb.scrollTop;// 获得滚动条的位置
	var r = document.selection.createRange();// 创建文档选择对象
	var t = txb.createTextRange();// 创建输入框文本对象
	t.collapse(true);// 将光标移到头
	t.select();// 显示光标,这个不能少,不然的话,光标没有移到头.当时我不知道,搞了十几分钟
	var j = document.selection.createRange();// 为新的光标位置创建文档选择对象
	r.setEndPoint("StartToStart", j);// 在以前的文档选择对象和新的对象之间创建对象,妈的,不好解释,我表达能力不算太好.有兴趣自己去看msdn的资料
	var str = r.text;// 获得对象的文本
	var re = new RegExp("[\\n]", "g");// 过滤掉换行符,不然你的文字会有问题,会比你的文字实际长度要长一些.搞死我了.我说我得到的数字怎么总比我的实际长度要长.
	str = str.replace(re, "");// 过滤
	pos = str.length;// 获得长度.也就是光标的位置
	r.collapse(false);
	r.select();// 把光标恢复到以前的位置
	txb.scrollTop = s;// 把滚动条恢复到以前的位置
	return pos;
}

// 将光标定位到textarea的某个位置
function go2Pos(pos) {
	var ta1 = document.getElementById('FORMULAWINDOWT');// 根据ID获得对象
	ta1.focus();
	var o = ta1.createTextRange();
	o.move("character", pos);
	o.select();
}

// 校验评级公式正确性：正确，返回true；错误，返回false
// 逻辑处理：将公式中的指标编码替换成数字20，IF表达式替换为20，然后调用js的eval方法执行表达式运算，如果出错，证明评级公式配置有问题，给出提示
function checkFormula() {
	var result = true;
	var wtValue = Ext.getCmp('FORMULAWINDOWT').getValue();
	if (wtValue && wtValue != null && wtValue != "") {
		debugger;
		if(ifIndex!=""){//包含IF表达式,IF表达式替换为20
			var indexs = ifIndex.split("#");
			for(var i =indexs.length-1 ; i >=0; i--){//倒着替换IF表达式(顺着替换的话，如果有多个IF表达式，没吃替换之后ifIndex会变化)
				var tempindexs = indexs[i].split("&");
				var preValue = wtValue.substring(0, Number(tempindexs[0]));
				var sufValue = wtValue.substring(Number(tempindexs[1])+1);
				wtValue = preValue+"20" +sufValue;
			}
		}
		wtValue = wtValue.replace(new RegExp(ruleSetForm.form.findField('indexCode').getValue(), "gm"),20);// 将指标编码转换成数字（转换成(20)），替换所有指标（如果有多个相同指标，替换所有相同的指标）
		// 执行公式
		try {
			eval(wtValue);
		} catch (e) {
			result = false;
		}
	}
	return result;
}



	function saveAndPage(btn){
			if (!ruleSetForm.getForm().isValid()) {
                Ext.MessageBox.alert('提示','输入有误,请检查输入项');
                return false;
            };
            //先判断是否重复问题（同一法人机构，同一指标，同一种客户类型只能配置一条规则）
            var indexCode = ruleSetForm.form.findField("indexCode").getValue();
            var custType =  ruleSetForm.form.findField("custType").getValue();
            var id = ruleSetForm.form.findField("id").getValue();
            Ext.Ajax.request({
				url : basepath + '/ocrmFSysRuleScoreSet!ifExits.json',
    				method : 'GET',
    				params : {
    					custType:custType,
    					indexCode:indexCode,
    					id:id
					},	
				waitMsg : '正在查询数据,请等待...', // 显示读盘的动画效果，执行完成后效果消失
				success :checkResult,
				failure :checkResult
			});
            function checkResult(response) {
				var resultArray = Ext.util.JSON.decode(response.status);
				if (resultArray == 200 ||resultArray == 201) {
					var exit =  Ext.util.JSON.decode(response.responseText).exit;
					if(exit=='no'){
						 var type = ruleSetForm.getForm().findField("computeType").getValue();//计算方式
				            if(type=='1')//直接折算
				            	if(ruleSetForm.getForm().findField("convertRate").getValue()==null||ruleSetForm.getForm().findField("convertRate").getValue()==''){
				            		Ext.MessageBox.alert('提示','请填写折算率');
				                    return false;
				            	}
							Ext.Msg.wait('正在保存，请稍后......','系统提示');
							Ext.Ajax.request({
								url : basepath + '/ocrmFSysRuleScoreSet!saveData.json',
								method : 'POST',
								form : ruleSetForm.getForm().id,
								success : function() {
										 Ext.Msg.alert('提示', '操作成功');
										 ruleSetWindow.hide();
								},
								failure : function(response) {
									var resultArray = Ext.util.JSON.decode(response.status);
								       if(resultArray == 403) {
								           Ext.Msg.alert('系统提示', response.responseText);
								  } else{
									Ext.Msg.alert('提示', '操作失败,失败原因:' + response.responseText);
								}}
							});
						
					}else{
						var type2 = '';
						if(custType=='0')
							 type2='所有';
						if(custType=='1')
							 type2='零售';
						if(custType=='2')
							 type2='对公';
						Ext.Msg.alert("提示", "本法人机构下已有针对"+type2+"客户的指标（"+ruleSetForm.form.findField("indexName").getValue()+"）的规则配置。");
					}
				}else {
					Ext.Msg.alert('提示', '失败' );
				}
			
		}
		
	};
	function close(){
				ruleSetWindow.hide();
	}

//规则编辑面板
var ruleSetPanel = new Ext.Panel({
    layout : 'border',
    activeItem : 0,     
    autoScroll : true,
    buttonAlign : "center",
    items : [ ruleSetForm],
    buttons : [{ 
	     text : '保存', 
	     handler :saveAndPage 
	    }, {
			text : '关闭',
			handler : close
		} ]
});


//规则设置window
var ruleSetWindow = new Ext.Window({
	layout : 'fit',
    autoScroll : true,
    draggable : true,
    closable : true,
    closeAction : 'hide',
    modal : true,
    width : 600,
    height : 400,
    loadMask : true,
    border : false,
    items : [ {
        buttonAlign : "center",
        layout : 'fit',
        items : [ruleSetPanel]
    }]
});