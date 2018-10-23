			var rowNo2=-1;
		//指标信息		
		var searchIndex1 = new Com.yucheng.crm.common.IndexField({ 
					xtype:'userchoose',
					fieldLabel : '指标列表', 
					id:'searchIndex1',
					name:'searchIndex',
					hiddenName:'searchIndex',
					labelStyle: 'text-align:right;',
					singleSelect:false,
					anchor : '90%',
					callback :function(a,b,c,d){
					var mgr_namess = null;
					records1 = addGrid.getSelectionModel().selection;
					var mgrIds1 = '';
					mgr_namess = Ext.getCmp('searchIndex1').getValue();
					if (mgr_namess != null && mgr_namess != ''){
		            addStore.getAt(rowNo2).data.targetCode =this.ID;
		            addStore.getAt(rowNo2).data.targetName =this.NAME;
		            addGrid.getView().refresh(false);
						}
						}
					});

    var addRecord = Ext.data.Record.create(
    		[
    		 {name:'achievePercent',mapping:'ACHIEVE_PERCENT'},
    		 {name:'targetNo',mapping:'TARGET_NO'},
    		 {name:'targetCode',mapping:'TARGET_CODE'},
    		 {name:'targetName',mapping:'TARGET_NAME'},
    		 {name:'targetMark',mapping:'TARGET_MARK'},
    		 {name:'originalValue',mapping:'ORIGINAL_VALUE'},
    		 {name:'targetValue',mapping:'TARGET_VALUE'},
    		 {name:'achieveValue',mapping:'ACHIEVE_VALUE'}
    		 ]
    );
    var addReader = new Ext.data.JsonReader(//读取jsonReader
    		{
    			successProperty : 'success',
    			idProperty : 'ID',
    			totalProperty : 'json.count',
    			root:'json.data'
    		},addRecord
	);
	var addStore = new Ext.data.Store({//产品对照关系store
	        restful : true, 
	        proxy : new Ext.data.HttpProxy({ 
	        	url:basepath+'/marketassudetailinfo.json',
	        	method:'get'
	        }),
			reader:addReader
			
	});
	
	// 每页显示条数下拉选择框
	var add_combo = new Ext.form.ComboBox({
		name : 'pagesize',
		triggerAction : 'all',
		mode : 'local',
		store : new Ext.data.ArrayStore({
			fields : [ 'value', 'text' ],
			data : [ [ 10, '10条/页' ], [ 20, '20条/页' ], [ 50, '50条/页' ],
								         [ 100, '100条/页' ], [ 250, '250条/页' ],
								         [ 500, '500条/页' ] ]
		}),
		valueField : 'value',
		displayField : 'text',
		value : '100',
		resizable : true,
		width : 85
	});

	//指标信息store
    	addStore.on('beforeload', function() {
    		this.baseParams = {
    				querysign:'target',
    				taskId:mktAssuRessuResolveForm.form.findField('taskParentId').getValue()
    		};
    	});
    	
	// 改变每页显示条数reload数据
	add_combo.on("select", function(comboBox) {
		addBbar.pageSize = parseInt(add_combo.getValue()),
		addStore.reload({
			params : {
				start : 0,
				limit : parseInt(add_combo.getValue())
			}
		});
	});

	var addBbar= new Ext.PagingToolbar({//gridTable 底部工具栏	
			pageSize : parseInt(add_combo.getValue()),
			store : addStore,
			displayInfo : true,
			displayMsg : '显示{0}条到{1}条,共{2}条',
			emptyMsg : "没有符合条件的记录",
			items : [ '-', '&nbsp;&nbsp;', add_combo ]
	});
	 var addSm = new Ext.grid.CheckboxSelectionModel();
	// 定义自动当前页行号
	 var prod_rownum = new Ext.grid.RowNumberer({
		header : 'No.',
		width : 28
	    });
	 var addColumns = new Ext.grid.ColumnModel({
         columns : [{
                    header : 'ID',
                    width : 100,
                    hidden:true,
                    align : 'center',
                    dataIndex : 'targetNo',
                    sortable : true,
                    editor : new Ext.form.Field()
             		},{
	                header : '指标编号',
	                width : 200,
	                hidden:true,
	                align : 'center',
	                dataIndex : 'targetCode',
	                sortable : true
	                },{
	                header : '指标名称',
	                width : 200,
	                align : 'center',
	                dataIndex : 'targetName',
	                sortable : true,
 					editor : searchIndex1
 					},{
                    header : '指标描述',
                    width : 100,
                    align : 'center',
                    dataIndex : 'targetMark',
                    sortable : true,
                    editor : new Ext.form.Field()
             		},{
                    header : '初始值',
                    width : 100,
                    align : 'right',
                    dataIndex : 'originalValue',
                    sortable : true,
                    editor : new Ext.form.Field()
             		},{
                    header : '目标值',
                    width : 100,
                    align : 'right',
                    dataIndex : 'targetValue',
                    sortable : true,
                    editor : new Ext.form.Field()
             		},{
                    header : '达成值',
                    width : 100,
                    align : 'right',
                    dataIndex : 'achieveValue',
                    sortable : true,
                    editor : new Ext.form.Field()
             		},{
                    header : '达成率',
                    width : 100,
                    align : 'right',
                    dataIndex : 'achievePercent',
                    sortable : true,
                    editor : new Ext.form.Field()
             		}]
 				});
	 /*************************************列模型***********************************************/

	    	var onAdd = function(){
            var u = new addStore.recordType({
            	"targetNo" :"",             
				"targetCode" :"",
				"targetName" :"",
				"targetMark":"",
				"originalValue" :"",
				"targetValue" :"",
				"achieveValue" :"",
				"achievePercent":""
            });
            addGrid.stopEditing();
            addStore.insert(0, u);
            addGrid.startEditing(0, 0);
        };
        
    	var onDelete1 = function(){
        var index1 = addGrid.getSelectionModel().getSelectedCell();
        if (!index1) {
        	alert("请选择一条记录");
            return false;
        }
        var rec = addStore.getAt(index1[0]);
        addStore.remove(rec);
    	};

	 var addGrid = new Ext.grid.EditorGridPanel({	
	 		tbar:[{
		            text : '新增',
		            iconCls:'addIconCss',
		            handler:function() {
		            addInfo.buttons[0].setDisabled(false);	
		            onAdd();
		        }},{
                text : '删除',
                iconCls:'deleteIconCss',
                handler:function() {
                	addInfo.buttons[0].setDisabled(false);
                    onDelete1();
                },
                scope: this
                }/*new Ext.form.Label({
				text:'搜索对象->>:'
				}),operateUser,operateOrg*/
			 ],
			store:addStore, 
			frame:true,
			height : 300,
			stripeRows : true,
			clicksToEdit : 1,
			cm:addColumns,
//			sm:addSm,
		      bbar:addBbar,
		      viewConfig : {// 不产横向生滚动条, 各列自动扩展自动压缩, 适用于列数比较少的情况
 			  }
	 });
	 
	 	 addGrid.on('cellclick',function(grid,row,col){//获取编辑的行数，从0开始，
				rowNo2=row;	
			});
	 
	 var addInfo = new Ext.Panel({
		autoScroll:true,
		height:300,
		id:'resolve3',
		layout : 'fit',
		items : [addGrid],
		buttonAlign:'center',
		buttons:[{
		text:'保存',
		disabled:true,
		handler:function(){
		if(''==mktAssuRessuResolveForm.form.findField('taskId').getValue()||null==mktAssuRessuResolveForm.form.findField('taskId').getValue()){
		Ext.Msg.alert('系统提示','请完善基本信息再执行此操作!');
		return false;
		}
		 var json0 = {'targetNo':[]};
		 var json1 = {'targetCode':[]};
		 var json2 = {'originalValue':[]};
		 var json3 = {'targetValue':[]};
		 var json4 = {'achieveValue':[]};
		 var json5 = {'achievePercent':[]};
	for(var i=0;i<addStore.getCount();i++){
    var temp=addStore.getAt(i);
    if(temp.data.targetCode!=''){
    	json0.targetNo.push('');
        json1.targetCode.push(temp.data.targetCode);
        json2.originalValue.push(temp.data.originalValue);
        json3.targetValue.push(temp.data.targetValue);
        json4.achieveValue.push(temp.data.achieveValue);
        json5.achievePercent.push(temp.data.achievePercent);
    	}else{
    	Ext.Msg.alert('系统提示','请选择指标!');
    		return false;
    	}
	}
    Ext.Msg.wait('正在保存，请稍后......','系统提示');
    Ext.Ajax.request({
        url : basepath + '/marketassudetailinfo!saveData.json',
        method : 'POST',
        waitMsg : '正在保存数据,请等待...', // 显示读盘的动画效果，执行完成后效果消失
        params:{
            'targetNo':Ext.encode(json0),
            'targetCode':Ext.encode(json1),
            'originalValue':Ext.encode(json2),
            'targetValue':Ext.encode(json3),
            'achieveValue':Ext.encode(json4),
            'achievePercent':Ext.encode(json5),
            'taskId':mktAssuRessuResolveForm.form.findField('taskId').getValue(),
            'querysign':'target'
        },
        success : function() {
            Ext.Msg.alert('提示', '操作成功');
            addInfo.buttons[0].setDisabled(true);
        },
        failure : function(response) {
            Ext.Msg.alert('提示', '操作失败,失败原因:' + response.responseText);
        }
    });
		}},{
		text:'重置',
		handler:function(){
		addStore.reload({
			params : {
				start : 0,
				limit : parseInt(add_combo.getValue())
			}
		});
		}
		}]
	});


/*
 * 营销任务分解
 * 
 */
 			// 分解窗口展示的from
			var mktAssuRessuResolveForm = new Ext.form.FormPanel({
				labelWidth : 100,
				height : 450,
				id:'resolve1',
				frame : true,
				labelAlign : 'right',
				region : 'center',
				autoScroll : true,
				buttonAlign : "center",
				items : [ {
					layout : 'column',
					items : [ {
						columnWidth : .5,
						layout : 'form',
						items : [{
							name : 'taskId',
							xtype : 'textfield',
							fieldLabel : '*营销任务Id',
							hidden:true,
							width : '100',
							anchor : '90%'
						},{
							name : 'taskName',
							xtype : 'textfield',
							fieldLabel : '*营销任务名称',
							width : '100',
							anchor : '90%',
							allowBlank : false
						},{
							xtype : 'datefield',
							width : 200,
							fieldLabel : '任务开始时间',
							format:'Y-m-d',
							allowBlank : false,
							name : 'taskBeginDate',
							anchor : '90%'
						},{
							store : operTypeStatStore,
							xtype : 'combo', 
							resizable : true,
							name : 'distTaskType',
							hiddenName : 'distTaskType',
							fieldLabel : '执行对象类型',
							valueField : 'key',
							displayField : 'value',
							mode : 'local',
							editable : false,
							typeAhead : true,
							forceSelection : true,
							triggerAction : 'all',
							emptyText : '请选择',
							selectOnFocus : true,
							width : '100',
							anchor : '90%'
							}]
					}, {
						columnWidth : .5,
						layout : 'form',
						items : [{
							name : 'taskParentId',
							xtype : 'textfield',
							fieldLabel : '*上级任务ID',
							width : '100',
							hidden:true,
							anchor : '90%',
							allowBlank : false
						},{
							name : 'taskParentName',
							xtype : 'textfield',
							fieldLabel : '上级任务名称',
							width : '100',
							anchor : '90%',
							allowBlank : false
						},{
							store : taskTypeStatStore,
							xtype : 'combo', 
							resizable : true,
							name : 'taskType',
							hiddenName : 'taskType',
							fieldLabel : '营销任务类型',
							valueField : 'key',
							displayField : 'value',
							mode : 'local',
							editable : false,
							typeAhead : true,
							forceSelection : true,
							triggerAction : 'all',
							emptyText : '请选择',
							selectOnFocus : true,
							width : '100',
							anchor : '90%'
						},{
							xtype : 'datefield',
							width : 200,
							format:'Y-m-d',
							fieldLabel : '任务结束时间',
							name : 'taskEndDate',
							allowBlank : false,
							anchor : '90%'
						}]
					}
					]
				},{   
				layout : 'form',
				buttonAlign : 'center',
				items : [{
					xtype : 'textarea',
					labelStyle : {
					width : '120px'
					},
					width : 200,
					fieldLabel : '备注',
					name : 'memo',
					anchor : '90%'
				}
				]
				
			}],
			buttons : [
					{text : '保  存',
					handler : function() {
    			if (!mktAssuRessuResolveForm.getForm().isValid()) {
                    Ext.MessageBox.alert('提示','输入有误,请检查输入项');
                    return false;
                };
    				Ext.Msg.wait('正在保存，请稍后......','系统提示');
    			Ext.Ajax.request({
    				url : basepath + '/marketassuinfo.json',
    				params : {
    				operate:'add'
    				},
    				method : 'POST',
    				form : mktAssuRessuResolveForm.getForm().id,
    				success : function() {
    					 Ext.Ajax.request({
    				         url: basepath +'/marketassuinfo!getPid.json',
    					         success:function(response){
    							 var taskId = Ext.util.JSON.decode(response.responseText).pid;
    							 mktAssuRessuResolveForm.form.findField('taskId').setValue(taskId);
    							 Ext.Msg.alert('提示', '操作成功');
    						 	}
    						 });
    				},
    				failure : function(response) {
    					var resultArray = Ext.util.JSON.decode(response.status);
    				       if(resultArray == 403) {
    				           Ext.Msg.alert('系统提示', response.responseText);
    				  } else{
    					Ext.Msg.alert('提示', '操作失败,失败原因:' + response.responseText);
    				}}
    			});
    		}},{
				text : '重    置',
				handler : function() {
					mktAssuRessuResolveForm.form.reset();
				}}]
			});
 
			//涉及执行对象
	var resolveOperateUser = new Com.yucheng.crm.common.OrgUserManage({ 
						xtype:'userchoose',
						fieldLabel : '执行人', 
						labelStyle: 'text-align:right;',
						name : 'CUST_MANAGER',
						id:'resolveOperateUser',
						hiddenName:'OPER_USER',
						//searchRoleType:('127,47'),  //指定查询角色属性
						searchType:'SUBTREE',/*指定查询机构范围属性  SUBTREE（子机构树）SUBORGS（直接子机构）PARENT（父机构）PARPATH （所有父、祖机构）ALLORG（所有机构）*/
						singleSelect:false,
						anchor : '90%',
						callback:function(){
						Ext.MessageBox.confirm('提示','确定执行该操作吗?',function(buttonId){
											if(buttonId.toLowerCase() == "no"){
											return;
											}
			        			 Ext.Ajax.request({
										url : basepath + '/marketassudetailinfo!saveData.json',
										params : {
										'chanelId':chanelContrastForm.form.findField('chanelType').getValue(),
										'mktActStr':editBasePlanForm.form.findField('mktActiId').getValue()
										},
										method : 'POST',
										form : chanelContrastForm.getForm().id,
										waitMsg : '正在保存数据,请等待...', // 显示读盘的动画效果，执行完成后效果消失
										success : function() {
											Ext.Msg.alert('提示', '操作成功!');
											resolveStore.reload({
												params : {
													start : 0,
													limit : parseInt(resolve_combo.getValue())
												}
												});
										},
										failure : function(response) {
											var resultArray = Ext.util.JSON.decode(response.status);
										       if(resultArray == 403) {
										           Ext.Msg.alert('提示', response.responseText);
										  } else{

											Ext.Msg.alert('提示', '操作失败,失败原因:' + response.responseText);
										}
										}
									});	 
								});
						}
						});
	//执行对象--机构
	var resolveOperateOrg = new Com.yucheng.bcrm.common.OrgField({
						searchType:'SUBTREE',/*指定查询机构范围属性  SUBTREE（子机构树）SUBORGS（直接子机构）PARENT（父机构）PARPATH （所有父、祖机构）ALLORG（所有机构）*/
						fieldLabel : '搜索对象->>',
						labelStyle : 'text-align:left;',
						id : 'resolveOperateOrg', //放大镜组件ID，用于在重置清空时获取句柄
						name : 'CUST_ORG', 
						hiddenName: 'instncode',   //后台获取的参数名称
						anchor : '90%',
						checkBox:true, //复选标志
						callback:function(){
						Ext.MessageBox.confirm('提示','确定执行该操作吗?',function(buttonId){
											if(buttonId.toLowerCase() == "no"){
											return;
											}
			        			 Ext.Ajax.request({
										url : basepath + '/marketassudetailinfo!saveData.json',
										params : {
											'operUserId':searchresolve.form.findField('instncode').getValue(),
											'operUserName':searchresolve.form.findField('CUST_ORG').getValue(),
											'taskId':mktAssuRessuResolveForm.form.findField('taskId').getValue(),
											'taskType':mktAssuRessuResolveForm.form.findField('taskType').getValue()
										},
										method : 'POST',
										form : searchresolve.getForm().id,
										waitMsg : '正在保存数据,请等待...', // 显示读盘的动画效果，执行完成后效果消失
										success : function() {
											Ext.Msg.alert('提示', '操作成功!');
										},
										failure : function(response) {
											var resultArray = Ext.util.JSON.decode(response.status);
										       if(resultArray == 403) {
										           Ext.Msg.alert('提示', response.responseText);
										  } else{

											Ext.Msg.alert('提示', '操作失败,失败原因:' + response.responseText);
										}
										}
									});
						});
						}
						});

    var resolveRecord = Ext.data.Record.create(
    		[
    		 {name:'id',mapping:'ID'},
    		 {name:'createDate',mapping:'CREATE_DATE'},
    		 {name:'createUserId',mapping:'CREATE_USER_ID'},
    		 {name:'createUserName',mapping:'CREATE_USER_NAME'},
    		 {name:'operObjId',mapping:'OPER_OBJ_ID'},
    		 {name:'operObjName',mapping:'OPER_OBJ_NAME'},
    		 {name:'taskId',mapping:'TASK_ID'},
    		 {name:'taskType',mapping:'TASK_TYPE'}
    		 ]
    );
    var resolveReader = new Ext.data.JsonReader(//读取jsonReader
    		{
    			successProperty : 'success',
    			idProperty : 'ID',
    			totalProperty : 'json.count',
    			root:'json.data'
    		},resolveRecord
	);
	var resolveStore = new Ext.data.Store({//产品对照关系store
	        restful : true, 
	        proxy : new Ext.data.HttpProxy({ 
	        	url:basepath+'/marketassudetailinfo.json',
	        	method:'get'
	        }),
			reader:resolveReader
			
	});
	
    	resolveStore.on('beforeload', function() {
    		this.baseParams = {
    			taskId:mktAssuRessuResolveForm.form.findField('taskId').getValue(),
    			querysign:'oper_obj'
    		};
    	});
	// 每页显示条数下拉选择框
	var resolve_combo = new Ext.form.ComboBox({
		name : 'pagesize',
		triggerAction : 'all',
		mode : 'local',
		store : new Ext.data.ArrayStore({
			fields : [ 'value', 'text' ],
			data : [ [ 10, '10条/页' ], [ 20, '20条/页' ], [ 50, '50条/页' ],
								         [ 100, '100条/页' ], [ 250, '250条/页' ],
								         [ 500, '500条/页' ] ]
		}),
		valueField : 'value',
		displayField : 'text',
		value : '100',
		resizable : true,
		width : 85
	});

	// 改变每页显示条数reload数据
	resolve_combo.on("select", function(comboBox) {
		resolveBbar.pageSize = parseInt(resolve_combo.getValue()),
		resolveStore.reload({
			params : {
				start : 0,
				limit : parseInt(resolve_combo.getValue())
			}
		});
	});

	var resolveBbar= new Ext.PagingToolbar({//gridTable 底部工具栏	
			pageSize : parseInt(resolve_combo.getValue()),
			store : resolveStore,
			displayInfo : true,
			displayMsg : '显示{0}条到{1}条,共{2}条',
			emptyMsg : "没有符合条件的记录",
			items : [ '-', '&nbsp;&nbsp;', resolve_combo ]
	});
	 var resolveSm = new Ext.grid.CheckboxSelectionModel();
	// 定义自动当前页行号
	 var prod_rownum = new Ext.grid.RowNumberer({
		header : 'No.',
		width : 28
	    });
	 var resolveColumns = new Ext.grid.ColumnModel(
				{
					columns:[prod_rownum,resolveSm,
					{ header:'ID',dataIndex:'id',sortable:true,hidden:true},
					{ header:'操作对象ID',dataIndex:'operObjId',sortable:true,hidden:true},
					{ header:'操作对象',dataIndex:'operObjName',sortable:true,width:100},
					{ header:'任务ID',dataIndex:'taskId',sortable:true,width:100,hidden:true},
					{ header:'任务类型',dataIndex:'taskType',width:100,sortable:true,hidden:true},
					{ header:'创建时间',dataIndex:'createDate',width:100,sortable:true},
					{ header:'创建人ID',dataIndex:'createUserId',width:100,sortable:true,hidden:true},
					{ header:'创建人',dataIndex:'createUserName',width:100,sortable:true}
					]
				}
	 );
	 /*************************************列模型***********************************************/

	 var searchresolve = new Ext.form.FormPanel({
            labelWidth : 80,
            hight:50,
            labelAlign : 'right',
            frame : true,
            region : 'north',
            autoScroll : true,
            layout : 'column',
            items : [ {
                columnWidth : .33,
                layout : 'form',
                items : [resolveOperateOrg]
            }]
        });
        
	 var resolveGrid = new Ext.grid.EditorGridPanel({			
			store:resolveStore, 
			frame:true,
			height : 300,
			cm:resolveColumns,
			region:'center',
			sm:resolveSm,
			      bbar:resolveBbar,
			      viewConfig : {// 不产横向生滚动条, 各列自动扩展自动压缩, 适用于列数比较少的情况
	 			  },
	 			  loadMask : {
	 				  msg : '正在加载表格数据,请稍等...'
	 			  }
	 });
	 
	 var resolveInfo = new Ext.Panel({
		autoScroll:true,
		id:'resolve2',
		layout : 'fit',
		items : [{
	        	 layout : 'form',
	        	 border : false,
	        	 items : [{
	                 region : 'north',
	                 hight:50,
	                 layout : 'fit',
	                 items : [ searchresolve ]
	             },{
	                 region : 'center',
	                 layout : 'fit',
	                 height:340,
	                 border : false,
	                 items : [ resolveGrid]
	             }]
				}]
			});
			
			//切换子面板 
		  function changeResolvePage(btn){
		  	var tempId = mktAssuRessuResolveForm.form.findField('taskId').getValue();
		  	if(''==tempId||undefined==tempId){
		  		Ext.Msg.alert('系统提示','请先完善基本信息,并点击保存!');
		  		return false;
		  	}
		   var index = Number(mktAssuRessuResolvePanel.layout.activeItem.id.substring(7)); 
		   if(btn.text == '上一步'){ 
		    index -= 1; 
		    if(index <1){ 
		     index = 1; 
		    } 
		   distributePlanWindow.setTitle('营销任务新增-->第'+index+'步，共3步');
		   }else{
		    index += 1; 
		    distributePlanWindow.setTitle('营销任务新增-->第'+index+'步，共3步');
		    if(index=='2'){
		    var distTaskType = mktAssuRessuResolveForm.form.findField('distTaskType').getValue();
		    if('1'==distTaskType){
		    Ext.getCmp('resolveOperateUser').setVisible(false);
		    Ext.getCmp('resolveOperateOrg').setVisible(true);
		    }else if('2'==distTaskType){
		    Ext.getCmp('resolveOperateUser').setVisible(true);
		    Ext.getCmp('resolveOperateOrg').setVisible(false);
		    }	
	    	resolveStore.reload({
			params : {
				start : 0,
				limit : parseInt(resolve_combo.getValue())
			}
			});
		    }
		    if(index=='3'){
		    addStore.reload({
			params : {
				start : 0,
				limit : parseInt(add_combo.getValue())
			}
			});	
		    }   
		    if(index >3) index = 3; 
		   }
		   if(index==1){
			   mktAssuRessuResolvePanel.buttons[0].setDisabled(true);   
		   }else{
			   mktAssuRessuResolvePanel.buttons[0].setDisabled(false);   
		   }
		   
		   if(index==3){
			   mktAssuRessuResolvePanel.buttons[1].setDisabled(true);   
		   }else{
			   mktAssuRessuResolvePanel.buttons[1].setDisabled(false);   
		   }
		   mktAssuRessuResolvePanel.layout.setActiveItem('resolve'+index); 
		  };
		  
 	 //营销任务分解界面
    var mktAssuRessuResolvePanel = new Ext.Panel( {
        layout : 'card',
        activeItem : 0,     
        autoScroll : true,
        buttonAlign : "center",
        items : [ mktAssuRessuResolveForm,resolveInfo,addInfo],
        buttons : [{ 
		     text : '上一步', 
		     handler :changeResolvePage 
		    }, 
		    { 
		     text : '下一步', 
		     handler :changeResolvePage 
		    }, {
    			text : '完    成',
    			handler : function() {
    			distributePlanWindow.hide();
    			}
    		} ]
    });
    
	// 定义分解窗口
	var distributePlanWindow = new Ext.Window({
		title : '营销任务',
		plain : true,
		layout : 'fit',
		width : 800,
		height : 450,
		resizable : true,
		draggable : true,
		closable : true,
		closeAction : 'hide',
		modal : true, // 模态窗口
		loadMask : true,
		maximizable : true,
		border : false,
		items : [ mktAssuRessuResolvePanel ]
	});	  