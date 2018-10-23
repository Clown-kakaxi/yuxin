/**
 * 规则的设置页面
 */
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
    id:'info1',
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
	    			anchor : '100%'
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
            	
                    }
                ]
    }]
});
//规则store
var ruleStore = new Ext.data.Store( {
	  restful:true,
	  proxy : new Ext.data.HttpProxy({url:basepath+'/ocrmFSysRule.json',
	    	method:'get'
	  }),
	  reader: new Ext.data.JsonReader({
		  successProperty : 'success',
		 idProperty : 'ID',
		  totalProperty : 'json.count',
		  root:'json.data'
	  }, [{name: 'id', mapping: 'ID'},
	      {name: 'rsId', mapping: 'RS_ID'},
	      {name: 'ruleName', mapping: 'RULE_NAME'},
	      {name: 'ruleExpress', mapping: 'RULE_EXPRESS'},
		  {name: 'operate', mapping: 'OPERATE'},
		  {name: 'ruleResult', mapping: 'RULE_RESULT'},
		  {name:'OPERATE_ORA'}
	      ])
  });

var rulesm = new Ext.grid.CheckboxSelectionModel();
var rulerownum = new Ext.grid.RowNumberer({
	  header : 'No.',
	  width : 28
});

var rulecm = new Ext.grid.ColumnModel([rulerownum,rulesm,	// 定义列模型
                                   {header : '编号', dataIndex : 'id',sortable : true,width : 120,hidden : true}, 
                                   {header : '规则编号', dataIndex : 'rsId',sortable : true,width : 120,hidden : true}, 
                                   {header : '折算规则名称', dataIndex : 'ruleName',sortable : true,width : 120},
                                   {header : '表达式', dataIndex : 'ruleExpress',sortable : true,width : 120,hidden:true},
                                   {header : '执行动作', dataIndex : 'operate',sortable : true,width : 120,hidden : true},
                                   {header : '执行动作', dataIndex : 'OPERATE_ORA',sortable : true,width : 120},
                                   {header : '分值/系数', dataIndex : 'ruleResult',sortable : true,width : 120}
                                   
                                   ]);

	
//规则grid
var ruleGrid = new Ext.grid.GridPanel({
	id:'info2',
	title : '规则列表',
    buttonAlign : "center",
    store: ruleStore,
	stripeRows : true, // 斑马线
	sm: rulesm,
	cm : rulecm,
	 tbar:[{
			text :'新增',
			tooltip: '新增',
			id:'add',
			iconCls:'addIconCss',
			handler :function(){
				conditionWindow.show();
				conditionWindow.setTitle('规则设置');
				conditionPanel.buttons[0].setDisabled(false);
				conditionGrid.tbar.setDisplayed(true);
				ruleForm.getForm().reset();
				ruleForm.getForm().findField('rsId').setValue(ruleSetForm.getForm().findField("id").getValue());
				ruleForm.getForm().findField('ruleName').setValue(ruleSetForm.getForm().findField("scoreName").getValue());
				conditionStore.reload();
				
			}
		  
	  },{
			text :'修改',
			iconCls:'editIconCss',
			id:'edit',
			handler :function(){
				var selectLength = ruleGrid.getSelectionModel().getSelections().length;
				var selectRe = ruleGrid.getSelectionModel().getSelections()[0];
				if (selectLength < 1) {
					Ext.Msg.alert('提示', '请先选择一条记录！');
					return false;
				} else if (selectLength > 1) {
					Ext.Msg.alert('提示', '只能选择一条记录！');
					return false;
				}
				conditionWindow.show();
				ruleForm.getForm().loadRecord(selectRe);
				conditionPanel.buttons[0].setDisabled(false);
				conditionWindow.setTitle('规则设置');
				conditionGrid.tbar.setDisplayed(true);
				conditionStore.reload();
				
			}},{
          text : '删除',
          iconCls:'deleteIconCss',
          id:'del',
          handler:function() {
        	  var checkedNodes = ruleGrid.getSelectionModel().selections.items;
      		var selectLength = ruleGrid.getSelectionModel().getSelections().length;
      		var selectRe;
      		var tempId;
      		var idStr = '';
      		if (selectLength < 1) {
      			Ext.Msg.alert('提示', '请选择要删除的记录！');
      		} else {
      				var json = {
      					'id' : []
      				};
      				for (var i = 0; i < checkedNodes.length; i++) {
      					json.id.push(checkedNodes[i].data.id);
      				}
      				Ext.MessageBox.confirm('提示', '确定删除吗？', function(buttonId) {
      					if (buttonId.toLowerCase() == "no") {
      						return;
      					}
      					Ext.Ajax.request({
      								url : basepath + '/ocrmFSysRule!batchDel.json',
      								method : 'POST',
      								params : {
      									ids : Ext.encode(json)
      								},
      								waitMsg : '正在保存数据,请等待...', // 显示读盘的动画效果，执行完成后效果消失
      								success : function() {
      									Ext.Msg.alert('提示', '操作成功！');
      									ruleStore.reload();
      								},
      								failure : function(response) {
      									var resultArray = Ext.util.JSON
      											.decode(response.status);
      									if (resultArray == 403) {
      										Ext.Msg.alert('提示',
      												response.responseText);
      									} else {

      										Ext.Msg
      												.alert(
      														'提示',
      														'操作失败，失败原因：'
      																+ response.responseText);
      									}
      									ruleStore.reload();
      								}
      							});

      				});
      		}
        	  
          }},{
        	  text : '查看',
				iconCls : 'detailIconCss',
				handler : function() {
					var selectLength = ruleGrid.getSelectionModel().getSelections().length;
					var selectRe = ruleGrid.getSelectionModel().getSelections()[0];
					if (selectLength < 1) {
						Ext.Msg.alert('提示', '请先选择一条记录！');
						return false;
					} else if (selectLength > 1) {
						Ext.Msg.alert('提示', '只能选择一条记录！');
						return false;
					}
					conditionWindow.show();
					ruleForm.getForm().loadRecord(selectRe);
					conditionWindow.setTitle('规则设置');
					conditionPanel.buttons[0].setDisabled(true);
					conditionGrid.tbar.setDisplayed(false);
					conditionStore.reload();
				}
          }],
	viewConfig : {},
	loadMask : {
		  msg : '正在加载表格数据,请稍等...'
	}
});
 
	function saveAndPage(btn){
		var index = Number(ruleSetPanel.layout.activeItem.id.substring(4)); //获取当前页
		if(index==1){//折算规则编辑页面保存
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
									if(type=='1'){
										 Ext.Msg.alert('提示', '操作成功');
										 ruleSetWindow.hide();
									}else{
										 Ext.Ajax.request({
									         url: basepath +'/ocrmFSysRuleScoreSet!getPid.json',
										         success:function(response){
												 var id = Ext.util.JSON.decode(response.responseText).pid;
												 ruleSetForm.form.findField('id').setValue(id);
												 //打开规则设置
												 ruleSetPanel.layout.setActiveItem('info2');
												 Ext.getCmp('add').setVisible(true);
												Ext.getCmp('edit').setVisible(true);
												Ext.getCmp('del').setVisible(true);
												 ruleStore.load({
														params : {
															rsId : id
														}
													});
												 Ext.Msg.alert('提示', '保存成功，请设置规则');
											 	}
											 });
									}
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
		}else{//规则设置页面其实已经保存，不需要再次保存
			Ext.Msg.alert('提示', '操作成功');
			 ruleSetWindow.hide();
		}
		
	};
	function Page(){
		//打开规则设置
		 ruleSetPanel.layout.setActiveItem('info2'); 
		 ruleStore.load({
				params : {
					rsId : ruleSetForm.form.findField('id').getValue()
				}
			});
	}
	function closeOrMention(){
		var index = Number(ruleSetPanel.layout.activeItem.id.substring(4)); //获取当前页
		if(index==1){//折算规则编辑页面关闭
			ruleSetWindow.hide();
		}else{//当没有设置具体规则时提示
			if(ruleStore.getCount()==0){
				Ext.MessageBox.confirm('提示','您还没有设定规则，确定要关闭吗?',function(buttonId){
					if(buttonId.toLowerCase() == "no"){
					return false;
					} 
					ruleSetWindow.hide();
					
				});
			}else{
				ruleSetWindow.hide();
			}
			
		}
	}

//规则编辑面板
var ruleSetPanel = new Ext.Panel({
    layout : 'card',
    activeItem : 0,     
    autoScroll : true,
    buttonAlign : "center",
    items : [ ruleSetForm,ruleGrid],
    buttons : [{ 
	     text : '保存', 
	     handler :saveAndPage 
	    },{ 
		     text : '规则配置', 
		     handler :Page 
		    }, {
			text : '关闭',
			handler : closeOrMention
		} ]
});

conditionWindow.addListener('hide',function(){ 
	ruleStore.reload();
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