	//合并单元格
	var continentGroupRow1 = [ {
		header : '',
		colspan : 2,
		align : 'center'
	}, {
		header : '分配存款账户',
		colspan : 5,
		align : 'center'
	}, {
		header : '推荐分行',
		colspan : 6,
		align : 'center'
	}, {
		header : '经办分行',
		colspan : 6,
		align : 'center'
	} ];
	
	var group1 = new Ext.ux.grid.ColumnHeaderGroup( {
		rows : [ continentGroupRow1 ]
			});
	
//record
	var record1 = Ext.data.Record.create([{
		name : 'Id',
		mapping : 'ID'
	},{
		name : 'custName',
		mapping : 'CUST_NAME'
	},{
		name : 'accountNo',
		mapping : 'ACCOUNT_NO'
	},{
		name : 'saveAmount',
		mapping : 'SAVE_AMOUNT'
	},{
		name : 'saveDayAvgYear',
		mapping : 'SAVE_DAY_AVG_YEAR'
	},{
		name : 'saveDayAvgMonth',
		mapping : 'SAVE_DAY_AVG_MONTH'
	},{
		name : 'saveDayAvgSeason',
		mapping : 'SAVE_DAY_AVG_SEASON'
	},{
		name : 'subOrgName',
		mapping : 'SUB_ORG_NAME'
	},{
		name : 'scale',
		mapping : 'SCALE'
	},{
		name : 'saveAmount1',
		mapping : 'SAVE_AMOUNT1'
	},{
		name : 'saveDayAvgYear1',
		mapping : 'SAVE_DAY_AVG_YEAR1'
	},{
		name : 'saveDayAvgMonth1',
		mapping : 'SAVE_DAY_AVG_MONTH1'
	},{
		name : 'saveDayAvgSeason1',
		mapping : 'SAVE_DAY_AVG_SEASON1'
	},{
		name : 'subOrgName1',
		mapping : 'SUB_ORG_NAME1'
	},{
		name : 'scale1',
		mapping : 'SCALE1'
	},{
		name : 'saveAmount2',
		mapping : 'SAVE_AMOUNT2'
	},{
		name : 'saveDayAvgYear2',
		mapping : 'SAVE_DAY_AVG_YEAR2'
	},{
		name : 'saveDayAvgMonth2',
		mapping : 'SAVE_DAY_AVG_MONTH2'
	},{
		name : 'saveDayAvgSeason2',
		mapping : 'SAVE_DAY_AVG_SEASON2'
	}]);

	// 定义列模型
	var cm1 = new Ext.grid.ColumnModel([ //rownum,
	  {
		header : 'No.',
		width : 50,
		align : 'center',
		dataIndex : 'Id',
		sortable : true
	},{
		header : '客户名称',
		width : 150,
		align : 'left',
		dataIndex : 'custName',
		sortable : true
	}, {
		header : '账号',
		width : 150,
		align : 'left',
		dataIndex : 'accountNo',
		sortable : true
	},{
		header : '存款余额',
		width : 150,
		align : 'right',
		dataIndex : 'saveAmount',
		sortable : true
	}, {
		header : '存款日均（本年）',
		width : 150,
		align : 'right',
		dataIndex : 'saveDayAvgYear',
		sortable : true
	}, {
		header : '存款日均（本季）',
		width : 150,
		align : 'right',
		dataIndex : 'saveDayAvgSeason',
		sortable : true
	}, {
		header : '存款日均（本月）',
		width : 150,
		align : 'right',
		dataIndex : 'saveDayAvgMonth',
		sortable : true
	}, {
		header : '分行名称',
		width : 150,
		align : 'left',
		dataIndex : 'subOrgName',
		sortable : true
	}, {
		header : '占比（％）',
		width : 150,
		align : 'right',
		dataIndex : 'scale',
		sortable : true
	},{
		header : '存款余额',
		width : 150,
		align : 'right',
		dataIndex : 'saveAmount1',
		sortable : true
	}, {
		header : '存款日均（本年）',
		width : 150,
		align : 'right',
		dataIndex : 'saveDayAvgYear1',
		sortable : true
	}, {
		header : '存款日均（本季）',
		width : 150,
		align : 'right',
		dataIndex : 'saveDayAvgSeason1',
		sortable : true
	}, {
		header : '存款日均（本月）',
		width : 150,
		align : 'right',
		dataIndex : 'saveDayAvgMonth1',
		sortable : true
	}, {
		header : '分行名称',
		width : 150,
		align : 'left',
		dataIndex : 'subOrgName1',
		sortable : true
	}, {
		header : '占比（％）',
		width : 150,
		align : 'right',
		dataIndex : 'scale1',
		sortable : true
	},{
		header : '存款余额',
		width : 150,
		align : 'right',
		dataIndex : 'saveAmount2',
		sortable : true
	}, {
		header : '存款日均（本年）',
		width : 150,
		align : 'right',
		dataIndex : 'saveDayAvgYear2',
		sortable : true
	}, {
		header : '存款日均（本季）',
		width : 150,
		align : 'right',
		dataIndex : 'saveDayAvgSeason2',
		sortable : true
	}, {
		header : '存款日均（本月）',
		width : 150,
		align : 'right',
		dataIndex : 'saveDayAvgMonth2',
		sortable : true
	}]);

	/**
	 * 数据存储
	 */
	var store1 = new Ext.data.Store({
		restful : true,
		proxy : new Ext.data.HttpProxy({
			url : basepath + '/channelCust.json'
		}),
		reader : new Ext.data.JsonReader({
//			successProperty : 'success',
//			idProperty : 'COUNT_ID',
//			messageProperty : 'message',
//			root : 'json.data',
//			totalProperty : 'json.count'
			totalProperty:'num',// 记录总数
			root:'rows'// Json中的列表数据根节点
		}, record1)
	});
	
	var memberData1= {
			TOTALCOUNT:3,
			rows:[
					{"rownum":"1","ID":'1',"CUST_NAME":"广东宏泽集团有限公司","ACCOUNT_NO":"7208822221832","SAVE_AMOUNT":"1,200.00","SAVE_DAY_AVG_YEAR":"3.00","SAVE_DAY_AVG_SEASON":"4.00",
						"SAVE_DAY_AVG_MONTH":"5.00","SUB_ORG_NAME":"北京分行","SCALE":"0.50","SAVE_AMOUNT1":"600.00","SAVE_DAY_AVG_YEAR1":"1.50","SAVE_DAY_AVG_SEASON1":"2.50",
						"SAVE_DAY_AVG_MONTH1":"2.70","SUB_ORG_NAME1":"广州分行","SCALE1":"0.50","SAVE_AMOUNT2":"600.00","SAVE_DAY_AVG_YEAR2":"1.60","SAVE_DAY_AVG_SEASON2":"2.30",
						"SAVE_DAY_AVG_MONTH2":"2.50"}
			]
		};
	store1.loadData(memberData1);
			
	// 每页显示条数下拉选择框
	var pagesize_combo1 = new Ext.form.ComboBox({
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
		//title : '跨行存款代理业务明细列表',
		frame : true,
		width : document.body.scrollWidth,
		height:document.body.scrollHeight-120,
		region : 'center',
		store : store1,
		stripeRows : true, // 斑马线
		cm : cm1, // 列模型
		tbar :  new Ext.Toolbar({
	        items  : ['数据日期：　　　　年　　月','->','单位：万元']
	    }), // 表格工具栏
		bbar : bbar1,// 分页工具栏
		viewConfig : {},
		loadMask : {
			msg : '正在加载表格数据,请稍等...'
		},
		plugins:group1
	});