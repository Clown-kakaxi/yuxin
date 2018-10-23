Ext.onReady(function() {
	   // 修改完成状态
		function setValue(a) {
			Ext.getCmp('wancheng_1').setValue(a), listPanel.save();
		}
		// 提交给下一人
		function goToNextOne() {
			if (listPanel.grid.selModel.hasSelection()) {
				var records = listPanel.grid.selModel.getSelections();// 得到被选择的行的数组
				var recordsLen = records.length;// 得到行数组的长度
				if (recordsLen > 1) {
					Ext.Msg.alert("系统提示信息", "请选择其中一条记录进行提交！");
				} else {
					var record = listPanel.grid.getSelectionModel()
							.getSelected();
					var id = record.get(listPanel.primary);
					listPanel.opUrl = listPanel.commUserUrl;
					var winButsArray = [];
					winButsArray.push( {
						text : "确认",
						handler : listPanel.save,
						scope : listPanel
					});
					winButsArray.push( {
						text : "关闭",
						handler : listPanel.closeWin,
						scope : listPanel
					});
					listPanel.winButs = winButsArray;
					listPanel.showWin();
					if (listPanel.editFun)
						listPanel.editFun();
					if (listPanel.stUrl)
						listPanel.seOneRecord(id);
					else if (listPanel.demoData)
						listPanel.fp.getForm().loadRecord(record);
				}
			} else {
				Ext.Msg.alert("系统提示", "请选择要修改的记录!");
			}
			Ext.getCmp('wancheng_1').setReadOnly(true);
			Ext.getCmp('groupId_1').setReadOnly(true);
			Ext.getCmp('start_Date').setReadOnly(true);
			Ext.getCmp('end_Date').setReadOnly(true);
			Ext.getCmp('taskContent_1').setReadOnly(true);
		}
		Ext.QuickTips.init();
		// 职能组下拉列表
		var groupIdStore = new Ext.data.Store( {
			restful : true,
			sortInfo : {
				field : 'key',
				direction : 'ASC'
			},
			autoLoad : true,
			proxy : new Ext.data.HttpProxy( {
				url : basepath + '/lookup.json?name=GROUP_ID'
			}),
			reader : new Ext.data.JsonReader( {
				root : 'JSON',
				totalProperty : 'list'
			}, [ 'key', 'value' ])
		});

		// 完成状态下拉列表
		var finishStateStore = new Ext.data.Store( {
			restful : true,
			sortInfo : {
				field : 'key',
				direction : 'ASC'
			},
			autoLoad : true,
			proxy : new Ext.data.HttpProxy( {
				url : basepath + '/lookup.json?name=FINISH_STATE'
			}),
			reader : new Ext.data.JsonReader( {
				root : 'JSON',
				totalProperty : 'list'
			}, [ 'key', 'value' ])
		});
		// 最终展现的panel
		var listPanel = new Mis.Ext.CrudPanel( {
			id : "listPanel",
			title : "任务管理",
			// seBaseForm ：true,
			stUrl : basepath + '/ocrmFWpWorkTaskAction!indexPage.json',
			// demoData :
			// {"json":{"count":9,"data":[{"UNITNAME":"总行","CREATE_ORG":"00001","CHANNEL_ID":"45396","CHANNEL_CODE":"","UPDATE_DATE":"2011-10-27","GUARANTEE":"","REMARK":"","CHANNEL_TYPE_NAME":"发改委","CHANNEL_NAME":"23","CHANNEL_FEATURE":"","ACCESS_CONDITION":"","CHANNEL_POLICY":"","CHANNEL_TYPE_ID":"10327","CREATE_USER":"admin","USERNAME":"系统管理员*","CREATE_DATE":"2011-10-27","UPDATE_USER":"admin"},{"UNITNAME":"总行","CREATE_ORG":"00001","CHANNEL_ID":"45386","CHANNEL_CODE":"","UPDATE_DATE":"2011-10-25","GUARANTEE":"","REMARK":"","CHANNEL_TYPE_NAME":"商会","CHANNEL_NAME":"7777777","CHANNEL_FEATURE":"","ACCESS_CONDITION":"","CHANNEL_POLICY":"","CHANNEL_TYPE_ID":"10328","CREATE_USER":"admin","USERNAME":"系统管理员*","CREATE_DATE":"2011-10-25","UPDATE_USER":"admin"},{"UNITNAME":"总行","CREATE_ORG":"00001","CHANNEL_ID":"45391","CHANNEL_CODE":"","UPDATE_DATE":"2011-10-25","GUARANTEE":"","REMARK":"","CHANNEL_TYPE_NAME":"商会","CHANNEL_NAME":"444444","CHANNEL_FEATURE":"","ACCESS_CONDITION":"123123","CHANNEL_POLICY":"","CHANNEL_TYPE_ID":"10328","CREATE_USER":"admin","USERNAME":"系统管理员*","CREATE_DATE":"2011-10-25","UPDATE_USER":"admin"},{"UNITNAME":"总行","CREATE_ORG":"00001","CHANNEL_ID":"45376","CHANNEL_CODE":"","UPDATE_DATE":"2011-10-24","GUARANTEE":"1","REMARK":"1","CHANNEL_TYPE_NAME":"委办局","CHANNEL_NAME":"测试2","CHANNEL_FEATURE":"1","ACCESS_CONDITION":"1","CHANNEL_POLICY":"1","CHANNEL_TYPE_ID":"10352","CREATE_USER":"admin","USERNAME":"系统管理员*","CREATE_DATE":"2011-10-09","UPDATE_USER":"admin"},{"UNITNAME":"北京管理部","CREATE_ORG":"00021","CHANNEL_ID":"17251","CHANNEL_CODE":"","UPDATE_DATE":"2011-10-14","GUARANTEE":"","REMARK":"","CHANNEL_TYPE_NAME":"委办局","CHANNEL_NAME":"新渠道22223333","CHANNEL_FEATURE":"","ACCESS_CONDITION":"","CHANNEL_POLICY":"","CHANNEL_TYPE_ID":"10352","CREATE_USER":"008755","USERNAME":"范*","CREATE_DATE":"2011-10-14","UPDATE_USER":"008755"},{"UNITNAME":"白云支行","CREATE_ORG":"04101","CHANNEL_ID":"17148","CHANNEL_CODE":"","UPDATE_DATE":"2011-10-14","GUARANTEE":"","REMARK":"","CHANNEL_TYPE_NAME":"委办局","CHANNEL_NAME":"白云山","CHANNEL_FEATURE":"","ACCESS_CONDITION":"","CHANNEL_POLICY":"","CHANNEL_TYPE_ID":"10352","CREATE_USER":"000727","USERNAME":"刘*","CREATE_DATE":"2011-10-09","UPDATE_USER":"000727"},{"UNITNAME":"总行","CREATE_ORG":"00001","CHANNEL_ID":"12367","CHANNEL_CODE":"","UPDATE_DATE":"2011-09-24","GUARANTEE":"","REMARK":"","CHANNEL_TYPE_NAME":"委办局","CHANNEL_NAME":"啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊1","CHANNEL_FEATURE":"","ACCESS_CONDITION":"","CHANNEL_POLICY":"","CHANNEL_TYPE_ID":"10352","CREATE_USER":"admin","USERNAME":"系统管理员*","CREATE_DATE":"2011-09-24","UPDATE_USER":"admin"},{"UNITNAME":"总行","CREATE_ORG":"00001","CHANNEL_ID":"45390","CHANNEL_CODE":"","UPDATE_DATE":"2011-10-25","GUARANTEE":"","REMARK":"","CHANNEL_TYPE_NAME":"财政","CHANNEL_NAME":"12212","CHANNEL_FEATURE":"","ACCESS_CONDITION":"","CHANNEL_POLICY":"","CHANNEL_TYPE_ID":"11006","CREATE_USER":"admin","USERNAME":"系统管理员*","CREATE_DATE":"2011-10-25","UPDATE_USER":"admin"},{"UNITNAME":"总行","CREATE_ORG":"00001","CHANNEL_ID":"17253","CHANNEL_CODE":"","UPDATE_DATE":"2011-10-14","GUARANTEE":"","REMARK":"","CHANNEL_TYPE_NAME":"财政","CHANNEL_NAME":"总行客户经理","CHANNEL_FEATURE":"","ACCESS_CONDITION":"","CHANNEL_POLICY":"","CHANNEL_TYPE_ID":"11006","CREATE_USER":"010514","USERNAME":"韩*","CREATE_DATE":"2011-10-14","UPDATE_USER":"010514"}]}},
			// 管理员新增URL，如果不定义则不出现新增按钮
			// 定义删除按钮
			deUrl : basepath + '/ocrmFWpWorkTaskAction!batchDestroy.json',
			addUrl : basepath + '/ocrmFWpWorkTaskAction.json',
			// 管理员修改URL
			updateUrl : basepath + '/ocrmFWpWorkTaskAction.json',
			// 普通用户定义修改URL
			commUserUrl : basepath + '/ocrmFWpWorkTaskAction.json',
			createFun : function(){
			Ext.getCmp('wancheng_1').setReadOnly(false);
			Ext.getCmp('burdenUser_1').setReadOnly(false);
			Ext.getCmp('assistUser_1').setReadOnly(false);
			Ext.getCmp('groupId_1').setReadOnly(false);
			Ext.getCmp('start_Date').setReadOnly(false);
			Ext.getCmp('end_Date').setReadOnly(false);
			Ext.getCmp('taskContent_1').setReadOnly(false);
			},
			
			//edit方法的扩展方法
			editFun : function(){
				Ext.getCmp('wancheng_1').setReadOnly(false);
				Ext.getCmp('burdenUser_1').setReadOnly(false);
				Ext.getCmp('assistUser_1').setReadOnly(false);
				Ext.getCmp('groupId_1').setReadOnly(false);
				Ext.getCmp('start_Date').setReadOnly(false);
				Ext.getCmp('end_Date').setReadOnly(false);
				Ext.getCmp('taskContent_1').setReadOnly(false);
			},
			
			//查看详情方法的扩展方法
			detailFun : function(){
				Ext.getCmp('wancheng_1').setReadOnly(true);
				Ext.getCmp('burdenUser_1').setReadOnly(true);
				Ext.getCmp('assistUser_1').setReadOnly(true);
				Ext.getCmp('groupId_1').setReadOnly(true);
				Ext.getCmp('start_Date').setReadOnly(true);
				Ext.getCmp('end_Date').setReadOnly(true);
				Ext.getCmp('taskContent_1').setReadOnly(true);
			},
			// 扩展按钮
			buts : [ {
				id : 'renling',
				xtype : 'button',
				tooltip : '查看',
				text : '任务处理',
				iconCls:'toOtherCss',
				handler : function(n) {
					if (listPanel.grid.selModel.hasSelection()) {
					var records = listPanel.grid.selModel.getSelections();// 得到被选择的行的数组
				var recordsLen = records.length;// 得到行数组的长度
				if (recordsLen > 1) {
					Ext.Msg.alert("系统提示信息", "请选择其中一条记录进查看认领！");
				} else {
					var record = listPanel.grid.getSelectionModel()
							.getSelected();
					var id = record.get(listPanel.primary);
					listPanel.opUrl = listPanel.commUserUrl;
					var winButsArray = [];
					winButsArray.push( {
						text : "延后办理",
						handler : function() {
							setValue(2);
						},
						scope : listPanel
					});
					winButsArray.push( {
						text : "正在办理",
						handler : function() {
							setValue(3);
						},
						scope : listPanel
					});
					winButsArray.push( {
						text : "提交下一结点",
						handler : function() {
							listPanel.closeWin();
							goToNextOne();
						},
						scope : listPanel
					});
					winButsArray.push( {
						text : "关闭",
						handler : listPanel.closeWin,
						scope : listPanel
					});
					listPanel.winButs = winButsArray;
					listPanel.showWin();
					if (listPanel.editFun)
						listPanel.editFun();
					if (listPanel.stUrl)
						listPanel.seOneRecord(id);
					else if (listPanel.demoData)
						listPanel.fp.getForm().loadRecord(record);
				}
			} else {
				Ext.Msg.alert("系统提示", "请选择要修改的记录!");
			}
				Ext.getCmp('wancheng_1').setReadOnly(true);
				Ext.getCmp('burdenUser_1').setReadOnly(true);
				Ext.getCmp('assistUser_1').setReadOnly(true);
				Ext.getCmp('groupId_1').setReadOnly(true);
				Ext.getCmp('start_Date').setReadOnly(true);
				Ext.getCmp('end_Date').setReadOnly(true);
				Ext.getCmp('taskContent_1').setReadOnly(true);
			}
			} ],
			primary : "id",
			checkbox : true,
			// 定义查询条件Form的高度
			seFormHeight : 105,
			// 定义增删详情页面弹出窗口高度
			winHeight : 250,
			// 宽度
			winWidth : 550,
			// 重载afterSeOneFun方法，加载一条数据后做的特殊处理
			afterSeOneFun : function(b) {
					if(b.startDate&&b.startDate.time)
						Ext.getCmp('start_Date').setValue(new Date(b.startDate.time));
					if(b.endDate&&b.endDate.time)
						Ext.getCmp('end_Date').setValue(new Date(b.endDate.time));
			},
			selectItems : {
				layout : 'column',
				items : [ {
					columnWidth : .25,
					layout : 'form',
					labelWidth : 60,
					defaultType : 'textfield',
					border : false,
					items : [ {
						store : groupIdStore,
						xtype : 'combo',
						resizable : true,
						fieldLabel : '职能组',
						name : 'groupId',
						hiddenName : 'groupId',
						valueField : 'key',
						displayField : 'value',
						mode : 'local',
						typeAhead : true,
						forceSelection : true,
						triggerAction : 'all',
						emptyText : '请选择',
						editable:false,
						selectOnFocus : true,
						width : '100',
						anchor : '80%'
					} ]
				}, {
					columnWidth : .25,
					layout : 'form',
					labelWidth : 60,
				//	defaultType : 'textfield',
					border : false,
					items : [new Com.yucheng.crm.common.OrgUserManage({ 
						xtype:'userchoose',
						fieldLabel : '负责人', 
						id:'PUBLISHER1',
						labelStyle: 'text-align:right;',
						name : 'userName',
						hiddenName:'burdenUser',
						//searchRoleType:('127,47'),  //指定查询角色属性
						searchType:'SUBTREE',/*指定查询机构范围属性  SUBTREE（子机构树）SUBORGS（直接子机构）PARENT（父机构）PARPATH （所有父、祖机构）ALLORG（所有机构）*/
						singleSelect:true,
						anchor : '80%'
						})]
				}, {
					columnWidth : .4,
					layout : 'column',
					xtype : 'panel',
					items : [ {
						columnWidth : .5,
						layout : 'form',
						labelWidth : 60,
						items : {
							fieldLabel : '开始时间',
							xtype : 'datefield',
							format : 'Y-m-d',
							editable : false,
							name : 'startDate',
							id:'startDate',
							resizable : true,
							anchor : '100%'
						}
					},{
						columnWidth : .5,
						layout : 'form',
						labelStyle : 'text-align:center',
						labelAlign : 'right',
						// labelSeparator : '',
						labelWidth : 60,
						items : {
							xtype : 'datefield',
							resizable : true,
							fieldLabel : '结束时间',
							name : 'endDate',
							id:'endDate',
							format : 'Y-m-d',
							editable : false,
							anchor : '100%'
						}
					} ]
				},{
					columnWidth : .25,
					layout : 'form',
					labelWidth : 60,
					defaultType : 'textfield',
					border : false,
					items : [ {
						store : finishStateStore,
						xtype : 'combo',
						resizable : true,
						fieldLabel : '完成情况',
						name : 'finishState',
						hiddenName : 'finishState',
					    valueField : 'key',
						displayField : 'value',
						mode : 'local',
					    typeAhead : true,
						forceSelection : true,
						editable:false,
				    	triggerAction : 'all',
						emptyText : '请选择',
						selectOnFocus : true,
						width : '100',
						anchor : '80%'
					} ]
				} ]
			},
			// 查询列表字段定义，有header属性则在页面显示
			// 如果需要做映射需要定义store , mappingkey ,mappingvalue 三个属性
			gclms : [ {
				name : 'id',
				hidden:true
			}, {
				name : 'startDate',
				header : '开始时间',
				type : 'date'
			}, {
				name : 'endDate',
				header : '结束时间',
				type : 'date'
			}, {
				name : 'taskContent',
				header : '工作内容安排'
			}, {
				name : 'burdenUser',
				header : '负责人'
			}, {
				name : 'assistUser',
				header : '协办人'
			}, {
				name : 'groupId',
				header : '职能组',
				type : 'mapping',
				store : groupIdStore,
				mappingkey : 'key',
				mappingvalue : 'value'
			}, {
				name : 'finishState',
				header : '完成情况',
				type : 'mapping',
				store : finishStateStore,
				mappingkey : 'key',
				mappingvalue : 'value'
			} ],
			// 设置分页每页显示条数，若不设置则不出现分页栏
			pagesize : 20,

			// 新增、修改、详情的form的字段
			fclms : [ {
				layout : 'column',
				items : [ {
					columnWidth : .5,
					labelWidth : 60,
					layout : 'form',
					items : [new Com.yucheng.crm.common.OrgUserManage({ 
						xtype:'userchoose',
						fieldLabel : '负责人', 
						id:'burdenUser_1',
						labelStyle: 'text-align:right;',
						name : 'burdenUser',
						hiddenName:'PUBLISHER',
						//searchRoleType:('127,47'),  //指定查询角色属性
						searchType:'SUBTREE',/*指定查询机构范围属性  SUBTREE（子机构树）SUBORGS（直接子机构）PARENT（父机构）PARPATH （所有父、祖机构）ALLORG（所有机构）*/
						singleSelect:true,
						anchor : '90%'
						})]
				}, 
				{
					columnWidth : .5,
					labelWidth : 60,
					layout : 'form',
					items : [ new Com.yucheng.crm.common.OrgUserManage({ 
						xtype:'userchoose',
						fieldLabel : '协办人', 
						id:'assistUser_1',
						labelStyle: 'text-align:right;',
						name : 'assistUser',
						hiddenName:'PUBLISHER',
						//searchRoleType:('127,47'),  //指定查询角色属性
						searchType:'SUBTREE',/*指定查询机构范围属性  SUBTREE（子机构树）SUBORGS（直接子机构）PARENT（父机构）PARPATH （所有父、祖机构）ALLORG（所有机构）*/
						singleSelect:true,
						anchor : '90%'
						})]
				}
				, {
					columnWidth : .5,
					layout : 'form',
					labelWidth : 60,
					defaultType : 'textfield',
					border : false,
					items : [ {
						id : 'groupId_1',
						store : groupIdStore,
						xtype : 'combo',
						resizable : true,
						fieldLabel : '职能组',
						name : 'groupId',
						hiddenName : 'groupId',
						valueField : 'key',
						displayField : 'value',
						mode : 'local',
						typeAhead : true,
						forceSelection : true,
						triggerAction : 'all',
						emptyText : '请选择',
						allowBlank : false,
						selectOnFocus : true,
						anchor : '90%'
					} ]
				}, {
					columnWidth : .5,
					layout : 'form',
					labelWidth : 60,
					defaultType : 'textfield',
					border : false,
					items : [ {
						allowBlank : false,
						id : 'wancheng_1',
						store : finishStateStore,
						xtype : 'combo',
						resizable : true,
						fieldLabel : '完成情况',
						name : 'finishState',
						hiddenName : 'finishState',
						valueField : 'key',
						displayField : 'value',
						mode : 'local',
						typeAhead : true,
						value : 1,
						forceSelection : true,
						triggerAction : 'all',
						emptyText : '请选择',
						selectOnFocus : true,
						anchor : '90%'
					} ]
				} ]
			}, {
				layout : 'column',
				items : [ {
					columnWidth : .5,
					labelWidth : 60,
					layout : 'form',
					items : {
						id : 'start_Date',
						fieldLabel : '开始时间',
						xtype : 'datefield',
						format : 'Y-m-d',
						editable : false,
						name : 'startDate',
						anchor : '90%'
					}
				}, {
					columnWidth : .5,
					labelWidth : 60,
					layout : 'form',
					items : {
						id : 'end_Date',
						xtype : 'datefield',
						resizable : true,
						// allowBlank : false,
						fieldLabel : '结束时间',
						name : 'endDate',
						format : 'Y-m-d',
						editable : false,
						anchor : '90%'
					}
				}, {// 特别注意：须放置隐藏域的主键
							name : 'id',
							xtype : 'hidden'
						} ]
			}, {
				layout : 'form',
				columnWidth : .5,
				labelWidth : 60,
				items : [ {
					id : 'taskContent_1',
					name : 'taskContent',
					fieldLabel : '工作安排',
					xtype : 'textarea',
					width : 200,
					allowBlank : false,
					maxLength : 400,
					anchor : '95%'
				} ]
			} ]
		});
		// 布局模型
		var viewport = new Ext.Viewport( {
			layout : 'fit',
			items : [ listPanel ]
		});
		
		finishStateStore.load({callback:function(){
			listPanel.store.reload();
		}});

		Ext.getCmp('startDate').addListener("select",function(){
			 var startData = Ext.getCmp('startDate').getValue();
             var endData = Ext.getCmp('endDate').getValue();
             if(startData!=undefined&&startData!=""&&endData!=undefined&&endData!=""){
                 if(startData>endData){
                     Ext.MessageBox.alert('条件异常', '开始时间应该小于等于结束时间！');
                     Ext.getCmp('startDate').setValue('');
                 }
             }
		});
		Ext.getCmp('endDate').addListener("select",function(){
			 var startData = Ext.getCmp('startDate').getValue();
            var endData = Ext.getCmp('endDate').getValue();
            if(startData!=undefined&&startData!=""&&endData!=undefined&&endData!=""){
                if(startData>endData){
                    Ext.MessageBox.alert('条件异常', '开始时间应该小于等于结束时间！');
                    Ext.getCmp('endDate').setValue('');
                }
            }
		});
	});