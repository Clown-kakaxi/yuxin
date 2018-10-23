		
	var mktTeamStore = new Ext.data.Store({//渠道类型的store
		restful:true,   
		autoLoad :true,
		proxy : new Ext.data.HttpProxy({
			url :basepath+'/chaneltypeinfo.json?tableName='+'OCRM_F_CM_MKT_TEAM'
		}),
		reader : new Ext.data.JsonReader({
			root : 'json.data'
		}, [ 'MKT_TEAM_ID', 'MKT_TEAM_NAME' ])
	});  

	
			 var tempAssuStatStore = new Ext.data.ArrayStore({
			        fields:['key','value'],
			        data:[['4','已完成'],['5','已取消']]
			    });
			 
			var assuStatStore = new Ext.data.Store({  
		    	restful:true,   
		    	autoLoad :true,
		    	proxy : new Ext.data.HttpProxy({
					url : basepath+'/lookup.json?name=MTASK_STAT'
				}),
				reader : new Ext.data.JsonReader({
					root : 'JSON'
				}, [ 'key', 'value' ])
			});

			var taskTypeStatStore = new Ext.data.Store({  
				restful:true,   
				autoLoad :true,
				proxy : new Ext.data.HttpProxy({
					url : basepath+'/lookup.json?name=MTASK_TYPE'
				}),
				reader : new Ext.data.JsonReader({
					root : 'JSON'
				}, [ 'key', 'value' ])
			});
			
			var operTypeStatStore = new Ext.data.Store({
				restful:true,   
				autoLoad :true,
				proxy : new Ext.data.HttpProxy({
					url : basepath+'/lookup.json?name=MTASK_OPER_TYPE'
				}),
				sortInfo : {
                field : 'key',
                direction : 'ASC'
            	},
				reader : new Ext.data.JsonReader({
					root : 'JSON'
				}, [ 'key', 'value' ])
			});
		// 新增窗口展示的from
			var mktAssEditInfoForm = new Ext.form.FormPanel({
				labelWidth : 100,
				height : 450,
				id:'info1',
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
//							hidden:true,
							anchor : '90%'
						}/*,new Com.yucheng.bcrm.common.OrgField({
								searchType:'SUBTREE',指定查询机构范围属性  SUBTREE（子机构树）SUBORGS（直接子机构）PARENT（父机构）PARPATH （所有父、祖机构）ALLORG（所有机构）
								fieldLabel : '所属机构',
								labelStyle : 'text-align:right;',
//								hidden:true,
								id : 'a1', //放大镜组件ID，用于在重置清空时获取句柄
								name : 'operObjName', 
								hiddenName: 'operObjId',   //后台获取的参数名称
								anchor : '90%',
								checkBox:true //复选标志
							}),
						new Com.yucheng.crm.common.OrgUserManage({ 
						xtype:'userchoose',
						fieldLabel : '客户经理', 
						name : 'operObjName',
						hiddenName:'operObjId',
						id:'a2',
						hidden:true,
						labelStyle: 'text-align:right;',
						//searchRoleType:('127,47'),  //指定查询角色属性
						searchType:'SUBTREE',//指定查询机构范围属性  SUBTREE（子机构树）SUBORGS（直接子机构）PARENT（父机构）PARPATH （所有父、祖机构）ALLORG（所有机构）
						singleSelect:false,
						anchor : '90%'
						}),{	
                id : 'a3',
                fieldLabel : '营销团队',
                name:'operObjName',
                hiddenName:'operObjId',
                forceSelection : true,
				resizable:true,
                xtype:'combo',
                width:170,
                hidden:true,
                labelStyle: 'text-align:right;',
                triggerAction:'all',
                mode:'local',
                store:mktTeamStore,
                valueField:'MKT_TEAM_ID',
                displayField:'MKT_TEAM_NAME',
                emptyText:'请选择',
                anchor : '90%'
            	}*/]
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
    			if (!mktAssEditInfoForm.getForm().isValid()) {
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
    				form : mktAssEditInfoForm.getForm().id,
    				success : function() {
    					 Ext.Ajax.request({
    				         url: basepath +'/marketassuinfo!getPid.json',
    					         success:function(response){
    							 var taskId = Ext.util.JSON.decode(response.responseText).pid;
    							 mktAssEditInfoForm.form.findField('taskId').setValue(taskId);
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
    		}}, {
				text : '重    置',
				handler : function() {
					mktAssEditInfoForm.form.reset();
				}
				} ]
			});