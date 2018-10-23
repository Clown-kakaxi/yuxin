Ext.onReady(function() {
	Ext.QuickTips.init();  
	Ext.Component.prototype.stateful = false;
	var mgrId;

	//客户经理识别标志Store
	 var custMgrFlagStore = new Ext.data.ArrayStore({
	        fields:['key','value'],
	        data:[['2','全部'],['1','是'],['0','否']]
	        });
	 
	// 定义自动当前页行号
	var rownum = new Ext.grid.RowNumberer({
		header : 'No.',
		width : 28
	});
	//对公客户经理认定查询条件
	var qForm = new Ext.form.FormPanel({
		title : "客户经理认定查询",
		labelWidth : 90, // 标签宽度
		frame : true, // 是否渲染表单面板背景色
		labelAlign : 'right', // 标签对齐方式
		buttonAlign : 'center',
		region: 'north',
		height : 120,
		layout : 'column',
			items : [ {
                columnWidth : .30,
                layout : 'form',
                items : [ new Com.yucheng.bcrm.common.OrgField({
                    searchType:'ALLORG',/*指定查询机构范围属性  SUBTREE（子机构树）SUBORGS（直接子机构）PARENT（父机构）PARPATH （所有父、祖机构）ALLORG（所有机构）*/
                    fieldLabel : '所在单位',
                    stateful:false,
                    labelStyle : 'text-align:right;',
                    id : 'jigouhao', //放大镜组件ID，用于在重置清空时获取句柄
                    name : 'CUST_ORG', 
                    hiddenName: 'checkedNodes',   //后台获取的参数名称
                    anchor : '85%',
                    checkBox:true //复选标志
                })]
                },{
				columnWidth : .30,
				layout : 'form',
				items : [ {
					fieldLabel : '客户经理识别标志',
					name : 'IS_CUST_MANAGER',
					id : 'custMgrFlag',
					xtype : 'combo', 
					store : custMgrFlagStore,
					hiddenName : 'cust_Mgr_Flag',
					triggerAction : 'all',
					valueField : 'key',
					displayField : 'value',
					forceSelection : true,
					typeAhead : true,
					editable : false,
					emptyText : '请选择',
					mode : 'local',
					anchor : '85%'
			}]
		} ],
		buttons : [ {
			text : '查询',
			handler : function() {
				var conditionStr = qForm.getForm().getValues(false);
				store.baseParams = {
					"condition" : Ext.encode(conditionStr)
				};
				store.load({
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
			}
		} ]
	});

	//对公客户经理认定record
	var record = Ext.data.Record.create([{
		name : 'userId',
		mapping : 'USERID'
	},{
		name : 'custManagerId',
		mapping : 'MGRID'
	},{
		name : 'custManagerName',
		mapping : 'USERNAME'
	},{
		name : 'superUnitName',
		mapping : 'SUPER_UNIT_NAME'
	},{
		name : 'unitName',
		mapping : 'UNITNAME'
	},{
		name : 'xcNo',
		mapping : 'USERCODE'
	},{
		name : 'roleName',
		mapping : 'ROLE_NAME'
	},{ 
		name: 'custMgrFlag',
		mapping:'IS_CUST_MANAGER'
	}]);
    
	// 对公客户经理认定定义列模型
	var cm = new Ext.grid.ColumnModel([ rownum,{
		header : '人员编号',
		hidden : 'true',
		dataIndex : 'userId',
		sortable : true
	},{
		header : '人员编号',
		hidden : 'true',
		dataIndex : 'custManagerId',
		renderer:function(value){
		  return  mgrId=value;
		},
		sortable : true
	},{
		header : '人员姓名',
		width : 100,
		align : 'center',
		dataIndex : 'custManagerName',
		sortable : true
	}, {
		header : '所在分行',
		width : 150,
		align : 'center',
		dataIndex : 'superUnitName',
		sortable : true
	}, {
		header : '所在单位',
		width : 180,
		align : 'center',
		dataIndex : 'unitName',
		sortable : true
	},{
		header : '吸存号码',
		width : 180,
		align : 'center',
		dataIndex : 'xcNo',
		sortable : true
	}, {
		header : '所在岗位',
		width : 180,
		align : 'center',
		dataIndex : 'roleName',
		sortable : true
	},{
        header: '纳入客户经理考核（是⊙否○）',
        dataIndex: 'custMgrFlag',
        id : 'custMgrFlag',
        renderer:function(value,record,e){
    		var checked =(e.json.IS_CUST_MANAGER=='1')?'checked':'';
    		var checkBox = '<input id='+e.id+'_check type="checkbox" '+checked+' />';
    		return  checkBox;
    	},
        width: 220,
        sortable : true
    }
	]);

	/**
	 * 数据存储，对公客户经理认定
	 */
	var store = new Ext.data.Store({
		restful : true,
		proxy : new Ext.data.HttpProxy({
			url : basepath + '/custMgrAffirmQuery.json',
			success : function(response) {
				var resultArray = Ext.util.JSON.decode(response.responseText);
				//Ext.Msg.alert('提示', response.responseText);
			}
		}),
		reader : new Ext.data.JsonReader({
			successProperty : 'success',
			idProperty : 'COUNT_ID',
			messageProperty : 'message',
			root : 'json.data',
			totalProperty : 'json.count'
		}, record),
	sortInfo: { field: 'superUnitName', direction: 'ASC' }
	});
	
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
	store.load({
		params : {
			start : 0,
			limit : parseInt(pagesize_combo.getValue())
		},callback:function(response){
			
		}
		
	});

	// 改变每页显示条数reload数据
	pagesize_combo.on("select", function(comboBox) {
		bbar.pageSize = parseInt(pagesize_combo.getValue()), store
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
		store : store,
		displayInfo : true,
		displayMsg : '显示{0}条到{1}条,共{2}条',
		emptyMsg : "没有符合条件的记录",
		items : [ '-', '&nbsp;&nbsp;', pagesize_combo ]
	});

	// 表格实例
	var grid = new Ext.grid.EditorGridPanel({
		title : '客户经理认定统计列表',
		id :'grid',
		frame : true,
		region : 'center',
		store : store,
		stripeRows : true, // 斑马线
		cm : cm, // 列模型
		tbar : [{text: '提交',
			iconCls:'completeIconCss',
		    id : 'commit',
		handler: function(){
			var addArray = [];
			var deleteArray =[];
			for(var j=0;j<grid.store.data.items.length;j++){
				var firstArray = {};
				var secondArray ={};
				var k =grid.store.data.keys;
				var one =document.getElementById(k[j]+"_check");
				var children = grid.store.data.items[j].json;
				if(one.checked==true){
					if (children.IS_CUST_MANAGER=='0'){
						firstArray.id = children.ID;
						firstArray.userid = children.USERID;
						firstArray.custmanagername = children.USERNAME; 
						firstArray.unitname = children.UNITNAME;
	//					firstArray.workunit = children.SUPER_UNIT_NAME;
	//					firstArray.rolename = children.ROLE_NAME;
						addArray.push(firstArray);
					}
				}else{
					if(children.IS_CUST_MANAGER=='1'){
						secondArray.id = children.ID;
						secondArray.userid = children.USER_ID;
						deleteArray.push(secondArray);
					}
					}
				
			}
			Ext.Ajax.request( {
				url : basepath + '/custMgrAffirm.json',
				method : 'POST',
				params : {
					'models' :Ext.encode(addArray),
					'models2':Ext.encode(deleteArray)
				},
			success : checkResult,
			failure : checkResult
			}); 
			function checkResult(response) {
				var resultArray = Ext.util.JSON
				.decode(response.status);
				var resultError = response.responseText;
	  
				if ((resultArray == 200 || resultArray == 201)
						&& resultError == '') {
					Ext.Msg.alert('系统提示信息', '操作成功');
		
				} else {
					if (resultArray == 403) {
						Ext.Msg.alert('系统提示信息',
								response.responseText);
					} else {
						Ext.Msg
						.alert(
								'系统提示信息',
								'操作失败,失败原因:' + resultError);
					}
				}
			}
		}
			}],
			bbar : bbar,// 分页工具栏
			viewConfig : {},
			loadMask : {
				msg : '正在加载表格数据,请稍等...'
			}
		});

	// 布局模型
	var viewport = new Ext.Viewport({
	    layout:'fit',
	    items :[{
    		layout : 'border',
    		items : [ qForm,grid]
        }]
	});

});