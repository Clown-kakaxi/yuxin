/**
 * @description 营销任务跟踪
 * @author sujm
 * @since 2014-08-13
 */
 imports([
          '/contents/pages/com.yucheng.bcrm/com.yucheng.bcrm.js'
      	 ,'/contents/pages/common/Com.yucheng.bcrm.common.MarketTask.js'		//营销任务放大镜
	      ]);
var needGrid = false;
WLJUTIL.suspendViews = false;
var fields = [{name:'CUST_ID'}];
var queryLock = false;

var h = document.documentElement.clientHeight;
var colMArray = new Array();
var mappArray = new Array();

var taskTypeStore = new Ext.data.Store( {
    restful : true,
    autoLoad : true,
    proxy : new Ext.data.HttpProxy( {
        url : basepath + '/lookup.json?name=MTASK_TYPE'
    }),
    reader : new Ext.data.JsonReader( {
        root : 'JSON'
    }, [ 'key', 'value' ])
});
var taskStatStore = new Ext.data.Store( {
    restful : true,
    autoLoad : true,
    proxy : new Ext.data.HttpProxy( {
        url : basepath + '/lookup.json?name=MTASK_STAT'
    }),
    reader : new Ext.data.JsonReader( {
        root : 'JSON'
    }, [ 'key', 'value' ])
});
var mtaskOperTypeStore = new Ext.data.Store( {
    restful : true,
    autoLoad : true,
    proxy : new Ext.data.HttpProxy( {
        url : basepath + '/lookup.json?name=MTASK_OPER_TYPE'
    }),
    reader : new Ext.data.JsonReader( {
        root : 'JSON'
    }, [ 'key', 'value' ])
});

//定义cm
colMArray[0] = {
		header : '营销任务名称',
		width : 275,
		dataIndex : 'TASK_NAME',
		sortable : true,
		align : 'right'
	};
colMArray[1] = {
		header : '执行对象类型',
		width : 275,
		dataIndex : 'DIST_TASK_TYPE_ORA',
		sortable : true,
		align : 'right'
	};
colMArray[2] = {
		header : '执行对象',
		width : 275,
		dataIndex : 'OPER_OBJ_NAME',
		sortable : true,
		align : 'right'
	};
colMArray[3] = {
		header : '营销任务状态',
		width : 275,
		dataIndex : 'TASK_STAT_ORA',
		sortable : true,
		align : 'right'
	};
colMArray[4] = {
		header : '营销任务类型_ORA',
		width : 275,
		dataIndex : 'TASK_TYPE',
		sortable : true,
		align : 'right'
	};
//定义列模型
var cm = new Ext.grid.ColumnModel(colMArray);
/**
 * 查询结果分页
 */
var pagesize_combo = new Ext.form.ComboBox( {
	name : 'pagesize',
	triggerAction : 'all',
	mode : 'local',
	store : new Ext.data.ArrayStore( {
		fields : [ 'value', 'text' ],
		data : [ [ 20, '20条/页' ], [ 50, '50条/页' ], [ 100, '100条/页' ],
				[ 250, '250条/页' ] ]
	}),
	valueField : 'value',
	displayField : 'text',
	value : 20,
	forceSelection : true,
	editable : false,
	width : 85
});
// 改变每页显示条数reload数据
pagesize_combo.on("select", function(comboBox) {
	bbar.pageSize = parseInt(pagesize_combo.getValue());
	resultstore2.reload( {
		params : {
			start : 0,
			limit : parseInt(pagesize_combo.getValue())
		}
	});
});
//分页工具栏
var bbar = new Ext.PagingToolbar( {
	pageSize : 20,// parseInt(pagesize_combo.getValue()),
	store : resultstore2,// 动态获取数据的时候注意修改这个
	displayInfo : true,
	displayMsg : '共{2}条记录,显示{0}-{1}',
	emptyMsg : "无数据",
	items : [ '-', '&nbsp;&nbsp;', pagesize_combo ]
});
// 查询结果数据源
var resultstore2 = new Ext.data.GroupingStore( {
	restful : true,
	proxy : new Ext.data.HttpProxy( {
		url : basepath + '/MktTaskTrack.json',
		method : 'POST'
	}),
	reader : new Ext.data.JsonReader( {
		successProperty : 'success',
		root : 'json.data',
		totalProperty : 'json.count'
	}, [ 'TASK_ID', 'TASK_NAME', 'TASK_PARENT_ID', 'OPER_OBJ_ID','OPER_OBJ_NAME',
			'DIST_TASK_TYPE_ORA', 'TASK_STAT_ORA', 'TASK_TYPE_ORA', 'TASK_BEGIN_DATE',
			'TASK_END_DATE', 'TASK_DIST_DATE', 'TARGET_CODE',
			'ORIGINAL_VALUE_0', 'TARGET_VALUE_0', 'ACHIEVE_VALUE_0',
			'ACHIEVE_PERCENT_0', 'ORIGINAL_VALUE_1', 'TARGET_VALUE_1',
			'ACHIEVE_VALUE_1', 'ACHIEVE_PERCENT_1', 'ORIGINAL_VALUE_2',
			'TARGET_VALUE_2', 'ACHIEVE_VALUE_2', 'ACHIEVE_PERCENT_2',
			'ORIGINAL_VALUE_3', 'TARGET_VALUE_3', 'ACHIEVE_VALUE_3',
			'ACHIEVE_PERCENT_3', 'ORIGINAL_VALUE_4', 'TARGET_VALUE_4',
			'ACHIEVE_VALUE_4', 'ACHIEVE_PERCENT_4', 'ORIGINAL_VALUE_5',
			'TARGET_VALUE_5', 'ACHIEVE_VALUE_5', 'ACHIEVE_PERCENT_5',
			'ORIGINAL_VALUE_6', 'TARGET_VALUE_6', 'ACHIEVE_VALUE_6',
			'ACHIEVE_PERCENT_6'])
		});
resultstore2.on('beforeload', function() {
	this.baseParams = {
		start : 0,
		limit : parseInt(pagesize_combo.getValue()),
		 "condition" : Ext.encode(searchPanel.getForm().getFieldValues())
	};
});

//初始化多维表头信息
var continentGroupUpArray = new Array();//一级表头信息
var continentGroupArray = new Array();//二级表头信息
continentGroupUpArray[0] = {
	    header : '指标跟踪基本信息',
	    colspan : 5,
	    align : 'center'
	};
continentGroupArray[0] = {
	    header : '指标跟踪基本信息',
	    colspan : 5,
	    align : 'center'
	};

//初始化多维表头对象
var group = new Ext.ux.grid.ColumnHeaderGroup( {
    rows : [ continentGroupUpArray,continentGroupArray ]
});

//查询结果列表面板
var resultGrid = new Ext.grid.GridPanel( {
	frame : true,
	plugins : group,
	height :document.body.scrollHeight-123,
	autoScroll : true,
	region : 'center', // 返回给页面的div
	store : resultstore2, // 数据存储
	stripeRows : true, // 斑马线
	cm : cm, // 列模型
	bbar : bbar,// 分页工具栏
	loadMask : {
		msg : '正在加载表格数据,请稍等...'
	}
});

//营销任务结构树获取方法
var getTaskTree = function(){
	 taskTreeLoader = new Com.yucheng.bcrm.ArrayTreeLoader({//营销任务树形结构加载属性值
		checkField : 'ASTRUE',
		parentAttr : 'TASK_PARENT_ID',//指向父节点的属性列
		locateAttr : 'TASK_ID',//任务编号
		rootValue :'0',
		textField : 'TASK_NAME',//任务名称
		idProperties : 'ID'//标示
	});
	var condition = {searchType:'SUBTREE'};
	var nodeArra;//机构树节点信息
	var filter = false;//过滤条件

	//营销任务树形结构
var orgTreeForShow = new Com.yucheng.bcrm.TreePanel({
	id:'taskTree',
	height : document.body.clientHeight,
	width : 210,
	rootVisable:false,
	autoScroll:true,
	checkBox : false, //是否现实复选框：
	_hiddens : [],
	resloader:taskTreeLoader,
	region:'west',
	split:true,
	root: new Ext.tree.AsyncTreeNode({
		id:'0',
//		hidden:true,
		expanded:true,
		text:'营销任务树',
		autoScroll:true,
		children:[]
	}),
	listeners : {
			click : function(n) {
				if(!n.isRoot){
					searchPanel.getForm().findField('TASK_ID').setValue(n.attributes.TASK_ID);
					// 将查询条件设置进去
					Ext.Ajax.request({
						url : basepath + '/MktTaskTrack!getColumn.json',
						method : 'GET',
						params : {
							taskId : n.attributes.TASK_ID
						},
						success : function(a) {
							if (a.responseText != null) {
								var arrayValue = Ext.decode(a.responseText);
								fnSearchResult(arrayValue);
								resultstore2.load();
								//解除中间面板蒙版限制
								infoPanel.getEl().unmask();
							}
						}
					});	
				}
			}
		}
	});
	
	//
	if(queryLock){
		Ext.Ajax.request( {
			url : basepath + '/TaskTreeQuery.json',
			method : 'GET',
			params : {
				condition : Ext.encode(searchPanel.getForm().getFieldValues())
			},
			success : function(response) {
				Ext.getCmp('taskTree').root.removeAll();
				paramNodeArra = Ext.util.JSON.decode(response.responseText).json.data;
				taskTreeLoader.nodeArray = paramNodeArra;
				var children = taskTreeLoader.loadAll();
				Ext.getCmp('taskTree').appendChild(children);
				Ext.getCmp('taskTree').expandAll();
				filter = new Ext.tree.TreeFilter(this.orgTreeForShow, {
					clearBlank : true,
					autoclear : true,
					ignoreFolder : true
				});
			},
			failure : function(a, b, c) {
			}
		});
	}
	return orgTreeForShow;
	};
	 var searchPanel = new Ext.form.FormPanel( {
         labelWidth : 105,
         labelAlign : 'right',
         frame : true,
         region : 'north',
         // autoScroll : true,
         layout : 'column',
         items : [ {
             columnWidth : .25,
             layout : 'form',
             items : [ {
			name : 'TASK_NAME',
			fieldLabel : '任务名称',
			xtype : 'taskchoose',
			hiddenName : 'TASK_ID',
			searchType : 'CURRENT',
			singleSelect : false,
			anchor : '90%'
		},{
            xtype : 'datefield',
            fieldLabel : '任务开始时间',
            format : 'Y-m-d',
            name : 'TASK_BEGIN_DATE',
            selectOnFocus : true,
            anchor : '90%'
        } ]
         }, {
		columnWidth : .25,
		layout : 'form',
		items : [ {
			xtype : 'combo',
			hiddenName : 'TASK_TYPE',
			fieldLabel : '任务类型',
			triggerAction : 'all',
			store : taskTypeStore,
			displayField : 'value',
			valueField : 'key',
			mode : 'local',
			emptyText : '请选择 ',
			resizable : true,
			anchor : '90%'
		}, {
			xtype : 'datefield',
			fieldLabel : '任务结束时间',
			format : 'Y-m-d',
			name : 'TASK_END_DATE',
			selectOnFocus : true,
			anchor : '90%'
		} ]
	},{
             columnWidth : .25,
             layout : 'form',
             items : [ {
     			xtype : 'combo',
    			hiddenName : 'TASK_STAT',
    			fieldLabel : '任务状态',
    			triggerAction : 'all',
    			store : taskStatStore,
    			displayField : 'value',
    			valueField : 'key',
    			mode : 'local',
    			emptyText : '请选择 ',
    			resizable : true,
    			anchor : '90%'} ]
         }, {
             columnWidth : .25,
             layout : 'form',
             items : [ {
     			xtype : 'combo',
    			hiddenName : 'DIST_TASK_TYPE',
    			fieldLabel : '执行对象类型',
    			triggerAction : 'all',
    			store : mtaskOperTypeStore,
    			displayField : 'value',
    			valueField : 'key',
    			mode : 'local',
    			emptyText : '请选择 ',
    			resizable : true,
    			anchor : '90%'} ]
         }]
     });

//声明左侧、顶部面板内容
var edgeVies = {
		top : {
		height:160,
		region:'north',
		xtype:'panel',
		buttons : [{
			text : '查询',
			handler : function() {
			infoPanel.getEl().mask();	//对中间面板添加蒙版限制
			queryLock= true;			//解锁左侧树查询
			
			//重新构建左侧树形面板
			getEdgePanel('left').removeAll();
			getEdgePanel('left').add(getTaskTree());
			getEdgePanel('left').doLayout();
		}
		}, {
			text : '重置',
			handler : function() {
			
				searchPanel.form.reset();
			}
		}],
		items:[searchPanel]
		},
		left : {
			height:110,
			region : 'west',
			items : [getTaskTree()]
		}
};

//声明中间面板信息
var infoPanel = new Ext.Panel({
	region:'center',
    layout:'fit',
	autoScroll : true,
	items:[resultGrid]
});

var customerView = [{
	title:'营销任务信息',
	xtype:'panel',
	autoScroll : true,
	items:[infoPanel]
}];

/**
 * @since20140815
 * @author sujm
 * @classDescription:动态面板生成程序，根据查询条件，动态展示营销任务的指标、周期与指标完成情况的二维列表
 * */
var fnSearchResult = function(arrayValue) {
	
	var colM = arrayValue.sbIndexName;
	var mapp = arrayValue.sbIndexId;
	var targetName = arrayValue.targetName;
	var cycleName = arrayValue.cycleName;
	var targetType = arrayValue.targetType;
	
	var colMArr = colM.split(",");
	var colLength = colMArr.length;
	var mappArr = mapp.split(",");
	var mappArrLength = mappArr.length;
	var targetNameArr = targetName.split(",");
	var targetNameLength = targetNameArr.length;
	var cycleNameArr = cycleName.split(",");
	var cycleNameLength = cycleNameArr.length;
	
	var colMArray = new Array();
	colMArray[0] = {
			header : '营销任务名称',
			width : 275,
			dataIndex : 'TASK_NAME',
			sortable : true,
			align : 'right'
		};
	colMArray[1] = {
			header : '执行对象类型',
			dataIndex : 'DIST_TASK_TYPE_ORA',
			sortable : true,
			align : 'right'
		};
	colMArray[2] = {
			header : '下发时间',
			dataIndex : 'TASK_DIST_DATE',
			sortable : true,
			align : 'right'
		};
	colMArray[3] = {
			header : '执行对象',
			dataIndex : 'OPER_OBJ_NAME',
			sortable : true,
			align : 'right'
		};
	colMArray[4] = {
			header : '营销任务状态',
			dataIndex : 'TASK_STAT_ORA',
			sortable : true,
			align : 'right'
		};
	colMArray[5] = {
			header : '营销任务类型',
			dataIndex : 'TASK_TYPE_ORA',
			sortable : true,
			align : 'right'
		};
	
	//更新列模型
	for ( var i = 5; i < colLength; i++) {
		colMArray[i] = {
			header : colMArr[i],
			dataIndex : mappArr[i],
			resizable: true,
			sortable : true,
			align : 'right'
		};
		mappArray[i] = {
			name : mappArr[i],
			mapping : mappArr[i]
		};
	}
	
	//重置多维表头信息
	continentGroupArray = new Array();
	continentGroupUpArray = new Array();
	continentGroupUpArray[0] = {
		    header : '指标跟踪基本信息',
		    colspan : 5,
		    align : 'center'
		};
	continentGroupArray[0] = {
		    header : '指标跟踪基本信息',
		    colspan : 5,
		    align : 'center'
		};
	
	//生成一级表头,判定该营销任务的指标类型是否为周期性，
	//当为周期性时，执行true操作，否则执行false操作
	if('01'==targetType){
			for ( var i = 1; i < cycleNameLength; i++) {
				continentGroupUpArray[i] = {
						header : cycleNameArr[i],
					    colspan : 3*((targetNameLength-1)/(cycleNameLength-1)),
					    align : 'center'
				};
			}		
	}else{
		for ( var i = 1; i < targetNameLength; i++) {
			continentGroupUpArray[i] = {
					header : '',
				    colspan : colMArray-1,
				    align : 'center'
			};
		}
	}
	
	//生成二级表头
	for ( var i = 1; i < targetNameLength; i++) {
		continentGroupArray[i] = {
				header : targetNameArr[i],
			    colspan : 3,
			    align : 'center'
		};
	}
	resultGrid.plugins.config.rows[0]=continentGroupUpArray;
	resultGrid.plugins.config.rows[1]=continentGroupArray;
	resultGrid.colModel.setConfig(colMArray, false) ;
};
var afterinit = function(){
	//初始化面板，对中间面板添加蒙版限制
	infoPanel.getEl().mask();
};
