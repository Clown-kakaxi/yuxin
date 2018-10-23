Ext.onReady(function() {
	Ext.QuickTips.init();
	
	 var teamStatusStore = new Ext.data.ArrayStore({
	        fields:['myId','displayText'],
	        data:[['1','正常'],['2','注销']]
	        });
	 
	var qForm = new Ext.form.FormPanel({
		title : "机构汇总查询",
		labelWidth : 90, // 标签宽度
		frame : true, // 是否渲染表单面板背景色
		labelAlign : 'middle', // 标签对齐方式
		buttonAlign : 'center',
		region : 'north',
		split : true,
		height : 120,
			layout : 'column',
			items : [{
				columnWidth : .25,
				layout : 'form',
				items : [ new Com.yucheng.bcrm.common.OrgField({
					searchType:'ALLORG',/*指定查询机构范围属性  SUBTREE（子机构树）SUBORGS（直接子机构）PARENT（父机构）PARPATH （所有父、祖机构）ALLORG（所有机构）*/
					fieldLabel : '所属机构',
					labelStyle : 'text-align:right;',
					id : 'jigouhao', //放大镜组件ID，用于在重置清空时获取句柄
					name : 'CUST_ORG', 
					hiddenName: 'checkedNodes',   //后台获取的参数名称
					anchor : '90%',
					checkBox:true //复选标志
				})]
			},{
				columnWidth : .25,
				layout : 'form',
				items : [ {
					fieldLabel : '截止日期',
					labelStyle: 'text-align:right;',
					xtype : 'datefield',
					format:'Y-m-d',
					name : 'END_DATE',
					anchor : '90%'
			}]
		} ],
		buttons : [ {
			text : '查询',
			handler : function() {debugger;
				var conditionStr = qForm.getForm().getValues(false);
				groupStore.baseParams = {
					"condition" : Ext.encode(conditionStr)
				};
				groupStore.load({
					params : {
						start : 0,
						limit : parseInt(pagesize_combo.getValue())
					}
				});
			}
		}, {
			text : '重置',
			handler : function() {
				qForm.getForm().reset();
				Ext.getCmp("jigouhao").setValue('');
			}
		} ]
	});

	// 定义自动当前页行号
	var rownum = new Ext.grid.RowNumberer({
		header : 'No.',
		width : 28
	});
	
	//********************************创建营销团队********************************
	var record = Ext.data.Record.create([
     {name: 'marketTeamId', mapping: 'MKT_TEAM_ID'},
     {name: 'createUser', mapping: 'CREATE_USER'},
     {name: 'createUserId', mapping: 'CREATE_USER_ID'},  
     {name: 'createUserName', mapping: 'CREATE_USER_NAME'},  
     {name: 'createUserOrgId', mapping: 'CREATE_USER_ORG_ID'},  
     {name: 'createDate', mapping: 'CREATE_DATE'},  
     {name:'teamstatus',mapping :'TEAM_STATUS'},
     {name: 'leadTelephone', mapping: 'LEAD_TELEPHONE'},
     {name: 'teamNumber', mapping: 'TEAM_NO',type:'float'},
     {name: 'custNumber', mapping: 'CUST_NO',type:'float'},
     {name: 'teamCustomerNumber', mapping: 'TEAM_CUS_NO'},
     {name: 'marketTeamName', mapping: 'MKT_TEAM_NAME'},
     {name: 'organizationId', mapping: 'ORG_ID'},
     {name: 'organizationName', mapping: 'ORG_NAME'},
     {name: 'teamLeader', mapping: 'TEAM_LEADER'},
     {name: 'teamLeaderId', mapping: 'TEAM_LEADER_ID'}
     ]);

	var store = new Ext.data.Store({
		restful : true,
		proxy : new Ext.data.HttpProxy({
			url : basepath + '/marketTeamQuery.json',
			failure : function(response){
				var resultArray = Ext.util.JSON.decode(response.status);
				if(resultArray == 403) {
					Ext.Msg.alert('提示', response.responseText);
				}
			}
		}),
		reader : new Ext.data.JsonReader({
			successProperty: 'success',
	        idProperty: 'MKT_TEAM_ID',
	        messageProperty: 'message',
			root : 'json.data',
			totalProperty: 'json.count'
		},record)
	});
//************************************机构汇总*********************************

	var groupRecord = Ext.data.Record.create([ {
		name : 'id',
		mapping : 'ID'
	}, {
		name : 'orgName',
		mapping : 'ORG_NAME'
	}, {
		name : 'importantCustNum',
		mapping : 'IMPORTANT_CUST_NUM'
	}, {
		name : 'hasCustMgrGroupNum',
		mapping : 'HAS_CUST_MGR_GROUP_NUM'
	}, {
		name : 'waitCreCustMgrGroupNum',
		mapping : 'WAIT_CRE_CUST_MGR_GROUP_NUM'
	}]);

	// 定义列模型
	var cm = new Ext.grid.ColumnModel([ rownum,
	    {
		header : 'ID',
		width : 100,
		align : 'center',
		dataIndex : 'id',
		hidden:true
	},
        {
		header : '机构名称',
		width : 200,
		align : 'center',
		dataIndex : 'orgName',
		cellclick:true,
		sortable : true
	}, {
		header : '重点客户数',
		width : 150,
		align : 'center',
		dataIndex : 'importantCustNum',
		sortable : true
	}, {
		header : '已建客户经理小组数量',
		width : 150,
		align : 'center',
		dataIndex : 'hasCustMgrGroupNum',
		sortable : true
	}, {
		header : '待建客户经理小组数量',
		width : 150,
		align : 'center',
		dataIndex : 'waitCreCustMgrGroupNum',
		cellclick:true,
		sortable : true
	}]);

	/**
	 * 数据存储
	 */
	var groupStore = new Ext.data.Store({
		restful : true,
		proxy : new Ext.data.HttpProxy({
			url : basepath + '/mktModelManage.json'//custMgrGroupCount
		}),
		reader : new Ext.data.JsonReader({
//			successProperty : 'success',
//			idProperty : 'ID',
//			messageProperty : 'message',
//			root : 'json.data',
//			totalProperty : 'json.count'
			totalProperty:'num',// 记录总数
			root:'rows'// Json中的列表数据根节点
		}, groupRecord)
	});
	
	var memberData= {
			TOTALCOUNT:3,
			rows:[
			{"rownum":"1","ID":"1","ORG_NAME":'<u>总行</u>',"IMPORTANT_CUST_NUM":"23","HAS_CUST_MGR_GROUP_NUM":"19","WAIT_CRE_CUST_MGR_GROUP_NUM":"<u>4</u>"},
			{"rownum":"2","ID":"1","ORG_NAME":"<u>北京分行</u>","IMPORTANT_CUST_NUM":"50","HAS_CUST_MGR_GROUP_NUM":"46","WAIT_CRE_CUST_MGR_GROUP_NUM":"<u>4</u>"},
			{"rownum":"3","ID":"3","ORG_NAME":"<u>上海分行</u>","IMPORTANT_CUST_NUM":"14","HAS_CUST_MGR_GROUP_NUM":"12","WAIT_CRE_CUST_MGR_GROUP_NUM":"<u>2</u>"},
			{"rownum":"4","ID":"4","ORG_NAME":"<u>广州分行</u>","IMPORTANT_CUST_NUM":"44","HAS_CUST_MGR_GROUP_NUM":"23","WAIT_CRE_CUST_MGR_GROUP_NUM":"<u>21</u>"},
			{"rownum":"5","ID":"5","ORG_NAME":"<u>天津分行</u>","IMPORTANT_CUST_NUM":"36","HAS_CUST_MGR_GROUP_NUM":"32","WAIT_CRE_CUST_MGR_GROUP_NUM":"<u>4</u>"},
			{"rownum":"6","ID":"6","ORG_NAME":"<u>北京支行</u>","IMPORTANT_CUST_NUM":"23","HAS_CUST_MGR_GROUP_NUM":"22","WAIT_CRE_CUST_MGR_GROUP_NUM":"<u>1</u>"},
			{"rownum":"7","ID":"7","ORG_NAME":"<u>上海支行</u>","IMPORTANT_CUST_NUM":"22","HAS_CUST_MGR_GROUP_NUM":"20","WAIT_CRE_CUST_MGR_GROUP_NUM":"<u>2</u>"},
			{"rownum":"8","ID":"8","ORG_NAME":"<u>广州支行</u>","IMPORTANT_CUST_NUM":"12","HAS_CUST_MGR_GROUP_NUM":"9","WAIT_CRE_CUST_MGR_GROUP_NUM":"<u>3</u>"}
			]
		};
	groupStore.loadData(memberData);

	// 每页显示条数下拉选择框
	var pagesize_combo = new Ext.form.ComboBox({
		name : 'pagesize',
		triggerAction : 'all',
		mode : 'local',
		store : new Ext.data.ArrayStore({
			fields : [ 'value', 'text' ],
			data : [ [ 100, '100条/页' ], [ 200, '200条/页' ],
					[ 500, '500条/页' ], [ 1000, '1000条/页' ] ]
		}),
		valueField : 'value',
		displayField : 'text',
		value : '100',
		editable : false,
		width : 85
	});

	// 默认加载数据
	groupStore.load({
		params : {
			start : 0,
			limit : parseInt(pagesize_combo.getValue())
		}
	});

	// 改变每页显示条数reload数据
	pagesize_combo.on("select", function(comboBox) {
		bbar.pageSize = parseInt(pagesize_combo.getValue()), groupStore
				.reload({
					params : {
						start : 0,
						limit : parseInt(pagesize_combo.getValue())
					}
				});
	});
	// 分页工具栏
	var bbar = new Ext.PagingToolbar({
		pageSize : parseInt(pagesize_combo.getValue()),
		store : groupStore,
		displayInfo : true,
		displayMsg : '显示{0}条到{1}条,共{2}条',
		emptyMsg : "没有符合条件的记录",
		items : [ '-', '&nbsp;&nbsp;', pagesize_combo ]
	});

	// 表格实例
	var grid = new Ext.grid.GridPanel({
		title : '机构汇总小组信息列表',
		width:700,
		height:315,
		frame : true,
		autoScroll : true,
		region : 'center',
		store : groupStore,
		stripeRows : true, // 斑马线
		cm : cm, // 列模型
		//sm : sm,
		//tbar : tbar, // 表格工具栏
		bbar : bbar,// 分页工具栏
		viewConfig : {},
		loadMask : {
			msg : '正在加载表格数据,请稍等...'
		}
	});
	
////////////////////////////////////////////////////////////////////////////////////////
	
	var continentGroupRow = [
	                     	{header: '', colspan: 5, align: 'center'},
	                    	{header: '主要人员', colspan: 3, align: 'center'}
	                    	];
	
	 var group = new Ext.ux.grid.ColumnHeaderGroup({
	        rows: [continentGroupRow]
	    });
	 
	var record1 = Ext.data.Record.create([ {
		name : 'id',
		mapping : 'ID'
	}, {
		name : 'groupName',
		mapping : 'GROUP_NAME'
	}, {
		name : 'belongSubOrgName',
		mapping : 'BELONG_SUB_ORG_NAME'
	}, {
		name : 'belongOrgName',
		mapping : 'BELONG_ORG_NAME'
	}, {
		name : 'memberNum',
		mapping : 'MEMBER_NUM'
	}, {
		name : 'groupLeader',
		mapping : 'GROUP_LEADER'
	}, {
		name : 'mainCustMgr',
		mapping : 'MAIN_CUST_MGR'
	}, {
		name : 'subBankLeader',
		mapping : 'SUB_BANK_LEADER'
	}]);
	 
	// 定义列模型
	var cm1 = new Ext.grid.ColumnModel([ //rownum,
	    {
		header : 'ID',
		width : 150,
		align : 'center',
		dataIndex : 'id',
		hidden:true
	},
        {
		header : '小组名称',
		width : 150,
		align : 'center',
		dataIndex : 'groupName',
		sortable : true
	}, {
		header : '所属分行',
		width : 150,
		align : 'center',
		dataIndex : 'belongSubOrgName',
		sortable : true
	}, {
		header : '所属机构',
		width : 150,
		align : 'center',
		dataIndex : 'belongOrgName',
		sortable : true
	}, {
		header : '成员人数',
		width : 150,
		align : 'center',
		dataIndex : 'memberNum',
		sortable : true
	}, {
		header : '组长',
		width : 150,
		align : 'center',
		dataIndex : 'groupLeader',
		sortable : true
	}, {
		header : '主办客户经理',
		width : 150,
		align : 'center',
		dataIndex : 'mainCustMgr',
		sortable : true
	}, {
		header : '分行领导',
		width : 150,
		align : 'center',
		dataIndex : 'subBankLeader',
		sortable : true
	}]);

	/**
	 * 数据存储
	 */
	var store1 = new Ext.data.Store({
		restful : true,
		proxy : new Ext.data.HttpProxy({
			url : basepath + '/mktModelManage.json'
		}),
		reader : new Ext.data.JsonReader({
//			successProperty : 'success',
//			idProperty : 'ID',
//			messageProperty : 'message',
//			root : 'json.data',
//			totalProperty : 'json.count'
			totalProperty:'num',// 记录总数
			root:'rows1'// Json中的列表数据根节点
		}, record1)
	});
	
	var memberData1= {
			TOTALCOUNT:3,
			rows1:[
			{"ID":"1","GROUP_NAME":'服务团队',"BELONG_SUB_ORG_NAME":"北京分行","BELONG_ORG_NAME":"北京银行","MEMBER_NUM":"5","GROUP_LEADER":"卢卡斯","MAIN_CUST_MGR":"赵恩","SUB_BANK_LEADER":"钱鸥"},
			{"ID":"2","GROUP_NAME":'中间业务团队',"BELONG_SUB_ORG_NAME":"海淀分行","BELONG_ORG_NAME":"北京银行","MEMBER_NUM":"8","GROUP_LEADER":"王振","MAIN_CUST_MGR":"李安","SUB_BANK_LEADER":"梁总"},
			{"ID":"3","GROUP_NAME":'贷款业务团队',"BELONG_SUB_ORG_NAME":"朝阳分行","BELONG_ORG_NAME":"北京银行","MEMBER_NUM":"6","GROUP_LEADER":"黄洲","MAIN_CUST_MGR":"曹傲","SUB_BANK_LEADER":"祁东"},
			{"ID":"4","GROUP_NAME":'存款业务团队',"BELONG_SUB_ORG_NAME":"中心分行","BELONG_ORG_NAME":"北京银行","MEMBER_NUM":"3","GROUP_LEADER":"宋伟","MAIN_CUST_MGR":"杨凌","SUB_BANK_LEADER":"张杰"},
			{"ID":"5","GROUP_NAME":'理财服务团队',"BELONG_SUB_ORG_NAME":"燕郊分行","BELONG_ORG_NAME":"北京银行","MEMBER_NUM":"9","GROUP_LEADER":"萧红","MAIN_CUST_MGR":"倪红","SUB_BANK_LEADER":"焦宏伟"},
			{"ID":"6","GROUP_NAME":'综合服务团队',"BELONG_SUB_ORG_NAME":"通州分行","BELONG_ORG_NAME":"北京银行","MEMBER_NUM":"11","GROUP_LEADER":"李显欧","MAIN_CUST_MGR":"宋雪松","SUB_BANK_LEADER":"孔祥东"}
			]
		};
	store1.loadData(memberData1);

	// 每页显示条数下拉选择框
	var pagesize_combo1 = new Ext.form.ComboBox({
		name : 'pagesize1',
		triggerAction : 'all',
		mode : 'local',
		store : new Ext.data.ArrayStore({
			fields : [ 'value', 'text' ],
			data : [ [ 100, '100条/页' ], [ 200, '200条/页' ],
					[ 500, '500条/页' ], [ 1000, '1000条/页' ] ]
		}),
		valueField : 'value',
		displayField : 'text',
		value : '100',
		editable : false,
		width : 85
	});

	// 默认加载数据
	store1.load({
		params : {
			start : 0,
			limit : parseInt(pagesize_combo1.getValue())
		}
	});

	// 改变每页显示条数reload数据
	pagesize_combo1.on("select", function(comboBox) {
		bbar.pageSize = parseInt(pagesize_combo1.getValue()), store1
				.reload({
					params : {
						start : 0,
						limit : parseInt(pagesize_combo1.getValue())
					}
				});
	});
	// 分页工具栏
	var bbar1 = new Ext.PagingToolbar({
		pageSize : parseInt(pagesize_combo1.getValue()),
		store : store1,
		displayInfo : true,
		displayMsg : '显示{0}条到{1}条,共{2}条',
		emptyMsg : "没有符合条件的记录",
		items : [ '-', '&nbsp;&nbsp;', pagesize_combo1 ]
	});

	// 表格实例
	var grid1 = new Ext.grid.GridPanel({
		title : '客户经理小组信息列表',
		width:800,
		height:450,
		frame : true,
		autoScroll : true,
		store : store1,
		stripeRows : true, // 斑马线
		cm : cm1, // 列模型
		//sm : sm,
		//tbar : tbar, // 表格工具栏
		bbar : bbar1,// 分页工具栏
		viewConfig : {},
		loadMask : {
			msg : '正在加载表格数据,请稍等...'
		},
        plugins: group
	});
	
	// 定义新增窗口
	var custMgrGroupWindow = new Ext.Window({
		title : '客户经理小组信息',
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
		collapsible : true,
		titleCollapse : true,
		border : false,
		items : [ grid1 ]
	});
	
////////////////////////////////////////////////////////////////////////////////////////////
	var record2 = Ext.data.Record.create([ {
		name : 'id',
		mapping : 'ID'
	}, {
		name : 'custName',
		mapping : 'CUST_NAME'
	}, {
		name : 'openAccBank',
		mapping : 'OPEN_ACC_BANK'
	}, {
		name : 'accumulDayAvg',
		mapping : 'ACCUMUL_DAY_AVG'
	}, {
		name : 'todayMount',
		mapping : 'TODAY_MOUNT'
	}, {
		name : 'atSubOrgName',
		mapping : 'AT_SUB_ORG_NAME'
	}, {
		name : 'atComp',
		mapping : 'AT_COMP'
	}, {
		name : 'custMgrGroupName',
		mapping : 'CUST_MGR_GROUP_NAME'
	}]);
	 
	// 定义列模型
	var cm2 = new Ext.grid.ColumnModel([ rownum,
	    {
		header : 'ID',
		width : 150,
		align : 'center',
		dataIndex : 'id',
		hidden:true
	},
        {
		header : '客户名称',
		width : 150,
		align : 'center',
		dataIndex : 'custName',
		sortable : true
	}, {
		header : '开户网点',
		width : 150,
		align : 'center',
		dataIndex : 'openAccBank',
		sortable : true
	}, {
		header : '累计日均',
		width : 150,
		align : 'right',
		dataIndex : 'accumulDayAvg',
		sortable : true
	}, {
		header : '本日余额',
		width : 150,
		align : 'right',
		dataIndex : 'todayMount',
		sortable : true
	}, {
		header : '所在分行',
		width : 150,
		align : 'center',
		dataIndex : 'atSubOrgName',
		sortable : true
	}, {
		header : '所在单位',
		width : 150,
		align : 'center',
		dataIndex : 'atComp',
		sortable : true
	}, {
		header : '客户经理小组名称',
		width : 150,
		align : 'center',
		dataIndex : 'custMgrGroupName',
		sortable : true
	}]);

	/**
	 * 数据存储
	 */
	var store2 = new Ext.data.Store({
		restful : true,
		proxy : new Ext.data.HttpProxy({
			url : basepath + '/mktModelManage.json'
		}),
		reader : new Ext.data.JsonReader({
//			successProperty : 'success',
//			idProperty : 'ID',
//			messageProperty : 'message',
//			root : 'json.data',
//			totalProperty : 'json.count'
			totalProperty:'num',// 记录总数
			root:'rows2'// Json中的列表数据根节点
		}, record2)
		});
		
		var memberData2= {
			TOTALCOUNT:3,
			rows2:[
			{"ID":"1","CUST_NAME":'安万特中国投资有限公司',"OPEN_ACC_BANK":"北京海淀支行","ACCUMUL_DAY_AVG":"3,000","TODAY_MOUNT":"5,000,000","AT_SUB_ORG_NAME":"北京分行","AT_COMP":"北京鸿天得力有限公司","CUST_MGR_GROUP_NAME":'<a href="../smallEnterprise/salesTeamManager.jsp">待建</a>'},
			{"ID":"2","CUST_NAME":'媒体世纪集团',"OPEN_ACC_BANK":"北京朝阳支行","ACCUMUL_DAY_AVG":"3,500","TODAY_MOUNT":"7,000,000","AT_SUB_ORG_NAME":"北京分行","AT_COMP":"北京花枝有限公司","CUST_MGR_GROUP_NAME":'<a href="../smallEnterprise/salesTeamManager.jsp">待建</a>'},
			{"ID":"3","CUST_NAME":'日立（中国）有限公司',"OPEN_ACC_BANK":"北京海淀支行","ACCUMUL_DAY_AVG":"5,000","TODAY_MOUNT":"10,000,000","AT_SUB_ORG_NAME":"北京分行","AT_COMP":"北京皇澳有限公司","CUST_MGR_GROUP_NAME":'<a href="../smallEnterprise/salesTeamManager.jsp">待建</a>'},
			{"ID":"4","CUST_NAME":' 松下电器（中国）有限公司',"OPEN_ACC_BANK":"北京燕郊支行","ACCUMUL_DAY_AVG":"2,000","TODAY_MOUNT":"3,000,000","AT_SUB_ORG_NAME":"北京分行","AT_COMP":"北京宇文科技公司","CUST_MGR_GROUP_NAME":'<a href="../smallEnterprise/salesTeamManager.jsp">待建</a>'}
			]
		};
		store2.loadData(memberData2);
		
	// 每页显示条数下拉选择框
	var pagesize_combo2 = new Ext.form.ComboBox({
		name : 'pagesize1',
		triggerAction : 'all',
		mode : 'local',
		store : new Ext.data.ArrayStore({
			fields : [ 'value', 'text' ],
			data : [ [ 100, '100条/页' ], [ 200, '200条/页' ],
					[ 500, '500条/页' ], [ 1000, '1000条/页' ] ]
		}),
		valueField : 'value',
		displayField : 'text',
		value : '100',
		editable : false,
		width : 85
	});

	// 默认加载数据
	store2.load({
		params : {
			start : 0,
			limit : parseInt(pagesize_combo2.getValue())
		}
	});

	// 改变每页显示条数reload数据
	pagesize_combo2.on("select", function(comboBox) {
		bbar.pageSize = parseInt(pagesize_combo2.getValue()), store2
				.reload({
					params : {
						start : 0,
						limit : parseInt(pagesize_combo2.getValue())
					}
				});
	});
	// 分页工具栏
	var bbar2 = new Ext.PagingToolbar({
		pageSize : parseInt(pagesize_combo2.getValue()),
		store : store2,
		displayInfo : true,
		displayMsg : '显示{0}条到{1}条,共{2}条',
		emptyMsg : "没有符合条件的记录",
		items : [ '-', '&nbsp;&nbsp;', pagesize_combo2 ]
	});

	// 表格实例
	var grid2 = new Ext.grid.GridPanel({
		title : '待建小组客户信息列表',
		width:800,
		height:450,
		frame : true,
		autoScroll : true,
		store : store2,
		stripeRows : true, // 斑马线
		cm : cm2, // 列模型
		//sm : sm,
		tbar : [{
			text : '创建小组',
			handler : function() {
			addInit();
			}
		}], // 表格工具栏
		bbar : bbar2,// 分页工具栏
		viewConfig : {},
		loadMask : {
			msg : '正在加载表格数据,请稍等...'
		}
	});
	
	// 新增小组展示的from
	var addGroupForm = new Ext.form.FormPanel({
		id:'addGroup',
		labelWidth : 100,
		height : 300,
		frame : true,
		autoScroll : true,
		buttonAlign : "center",
		items : [{
			layout : 'column',
			items : [{
						columnWidth : .9,
						layout : 'form',
						items : [{
								xtype : 'textfield',
								width : 200,
								fieldLabel : '<span style="color:red">*</span>营销团队名称',
								labelStyle: 'text-align:right;',
								allowBlank : false,
								name : 'marketTeamName',
								id:'marketTeamName',
								anchor : '90%'
							},{
								xtype : 'textfield',
								width : 200,
								hidden:true,
								name : 'organizationId',
								anchor : '90%'
							},{
								xtype : 'textfield',
								width : 200,
								fieldLabel : '归属机构',
								editable :false ,
								labelStyle: 'text-align:right;',
								name : 'organizationName',
								hidden:true,
								anchor : '90%'
							},{	
	                             fieldLabel: '<span style="color:red">*</span>团队状态',
	                             hiddenName : 'teamstatus',
	                             forceSelection : true,
	 							 resizable:true,
	 							allowBlank : false,
	                             xtype:'combo',
	                             labelStyle: 'text-align:right;',
	                             triggerAction:'all',
	                             mode:'local',
	                             editable:false,
	                             store:teamStatusStore,
	                             valueField:'myId',
	                             displayField:'displayText',
	                             emptyText:'请选择',
	                             anchor : '90%'
							},{
								xtype : 'textfield',
								width : 200,
								hidden:true,
								id:'teamLeaderId',
								name : 'teamLeaderId',
								anchor : '90%'
							},new Ext.ux.form.TeamManagerField({
								width : 200,
								fieldLabel : '负责人',
								labelStyle: 'text-align:right;',
								name : 'teamLeader',
								id :'teamLeader',
								anchor : '90%',
								callback:function(){
								var unit_id = null;
								var unit_name= null;
								var cust_name = null;
								cust_name = Ext.getCmp('teamLeader').getValue();
									if (cust_name != null && cust_name != '') {
										unit_id = Ext.getCmp('teamLeader').unitId.aId[0];
										unit_name = Ext.getCmp('teamLeader').unitName.unitName[0];
										addGroupForm.getForm().findField('organizationId').setValue(unit_id);
										addGroupForm.getForm().findField('organizationName').setValue(unit_name);
									}
								}
							}),{
								xtype : 'textfield',
								width : 200,
								fieldLabel : '负责人联系电话',
								labelStyle: 'text-align:right;',
								name : 'telephone',
								vtype:'mobile',
								name : 'leadTelephone',
								anchor : '90%'
							}
							
						    ]
					}]}],buttons : [

									{
										text : '保  存',
										handler : function() {
											if(!addGroupForm.getForm().isValid()){
												Ext.Msg.alert("系统提醒","输入有误，请重新输入!");
											return false;
											}
											Ext.Ajax.request({
												url : basepath + '/marketTeamAction.json?a=1',
												method : 'POST',
												form : addGroupForm.getForm().id,
												waitMsg : '正在保存数据,请等待...', // 显示读盘的动画效果，执行完成后效果消失
												success :checkResult,
												failure :checkResult
											});
											addGroupWindow.hide();
											function checkResult(response) {
												var resultArray = Ext.util.JSON.decode(response.status);
												var resultError = response.responseText;
												if ((resultArray == 200 ||resultArray == 201)&&resultError=='') {
													Ext.Msg.alert('提示', '操作成功');
													addGroupForm.getForm().reset();
													store.reload({
											params : {
											start : 0,
											limit :bbar.pageSize
											                    }
											                });
												} else{
													if(resultArray == 403){
														Ext.Msg.alert('提示', response.responseText);
														}
													else {
													Ext.Msg.alert('提示', '操作失败,失败原因:' + resultError);
													store.reload({
											params : {
											start : 0,
											limit :bbar.pageSize
											                    }
											                });
												}
											}
											};
										}
									}, {
										text : '取  消',
										handler : function() {
											addGroupWindow.hide();
										}
									} ]
	});

	
	// 定义新增小组窗口
	var addGroupWindow = new Ext.Window({
		title : '营销团队信息新增',
		plain : true,
		layout : 'fit',
		width : 500,
		height : 300,
		resizable : true,
		draggable : true,
		closable : true,
		closeAction : 'hide',
		modal : true, // 模态窗口
		loadMask : true,
		maximizable : true,
		collapsible : true,
		titleCollapse : true,
		border : false,
		items : [ addGroupForm ]
	});
	
	// 展示新增小组窗口
	function addInit() {
		addGroupWindow.show();
	}
	
	// 定义待建小组客户明细窗口
	var waitCreGroupWindow = new Ext.Window({
		title : '待建小组客户明细',
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
		collapsible : true,
		titleCollapse : true,
		border : false,
		items : [ grid2 ]
	});
	grid.on('cellclick',function(grid,row,col){//获取编辑的行数，从0开始，
		if(col==2){
			custMgrGroupWindow.show();
		}
		if(col==5){
			waitCreGroupWindow.show();
		}
	});
	// 布局模型
	var viewport = new Ext.Viewport({
		layout : 'fit',
		items : [ {
			layout : 'border',
			items : [ qForm, grid ]
		} ]
	});
});