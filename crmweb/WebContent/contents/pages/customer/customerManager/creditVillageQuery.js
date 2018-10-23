Ext.onReady(function() {
	Ext.QuickTips.init();
	//年份，静态数据
	//nyear当前年份
	var nyear = new Date().getYear();
	var yearStore =new Ext.data.SimpleStore({ 
		fields : ['key','value'],
		data : [[nyear,nyear],[nyear-1,(nyear-1)],[nyear-2,(nyear-2)],[nyear-3,(nyear-3)],[nyear-4,(nyear-4)]]
	});
	
	//月份，静态数据
	var monthStore = new Ext.data.SimpleStore({
		fields : ['key','value'],
		data : [['1','01'],['2','02'],['3','03'],['4','04'],['5','05'],['6','06'],['7','07'],['8','08'],['9','09'],['10','10'],['11','11'],['12','12']]
	});
	
	var orgId=JsContext._orgId;
	var orgName=JsContext._unitname;
	
	//最终展现的panel
	var listPanel = new Mis.Ext.CrudPanel({
		id : "listPanel",
		title : "客户管理->小贷客户管理->自然村汇总查询",
		//查询的url
		stUrl : basepath + '/creditVillageQuery-info!indexPage.json',
		//详情的url
		detailUrl : basepath + '/creditVillageQuery-info!indexPage.json',
		primary : "villaNo",
//		orgTreePanelSign:true,
//		rrrrrrr: orgTreePanel.root.getUI().toggleCheck(false),
		//单选框
		singleSelect : true,
		//定义查询条件Form的高度
		seFormHeight : 80,
		//定义增删详情页面弹出窗口高度
		winHeight : 450,
		//宽度
		winWidth : 800,
		// 查询字段定义，若不定义则不出现查询条件From
		selectItems : {
			layout : 'column',
			items : [{
			    columnWidth : .25,
				layout : 'form',
				defaultType : 'textfield',
				labelWidth : 70,
				border : false,
				items : [{store : yearStore,xtype : 'combo',resizable : true,fieldLabel : '年份',name : 'year',hiddenName : 'year',valueField : 'key',displayField : 'value',	mode : 'local',typeAhead : true,forceSelection : true,triggerAction : 'all',emptyText : '请选择年份',selectOnFocus : true,anchor : '95%'} ]
			},{
			    columnWidth : .25,
				layout : 'form',
				defaultType : 'textfield',
				labelWidth : 70,
				border : false,
				items : [{store : monthStore,xtype : 'combo',resizable : true,fieldLabel : '月份',name : 'month',hiddenName : 'month',valueField : 'key',displayField : 'value',	mode : 'local',typeAhead : true,forceSelection : true,triggerAction : 'all',emptyText : '请选择月份',selectOnFocus : true,	anchor : '95%'} ]
			},{
			    columnWidth : .25,
			    layout : 'form',
			    defaultType : 'textfield',
			    labelWidth : 70,
			    border : false,
			    items : [new Com.yucheng.bcrm.common.OrgField({
					searchType:'ALLORG',/*指定查询机构范围属性  SUBTREE（子机构树）SUBORGS（直接子机构）PARENT（父机构）PARPATH （所有父、祖机构）ALLORG（所有机构）*/
					fieldLabel : '所属机构',
					labelStyle : 'text-align:right;',
					id : 'orgIdTemp', //放大镜组件ID，用于在重置清空时获取句柄
					name : 'CUST_ORG', 
					hiddenName: 'orgId',   //后台获取的参数名称
					anchor : '90%',
					checkBox:true //复选标志
			    })]
			}]
	   
		},
		//查询列表字段定义，有header属性则在页面显示
		//如果需要做映射需要定义store , mappingkey ,mappingvalue 三个属性
		gclms : [ 
		    {name : 'custId'	}, 
		    {name : 'villaNo'},
		    {name : 'villaName',header : '自然村名称',width : 100},
		    {name : 'villaBelong',header : '所属村委',hidden:true,width : 100},
		    //{name : 'townName',header : '镇区名称',width : 100},
		    //{name : 'orgId',header : '所属支行',width : 100},
		    {name : 'orgName',header : '所属支行',width : 100},
		    {name : 'hostUser',header : '负责人',width : 100},
		    {name : 'population',header : '人口',width : 100},
		    {name : 'houseNum',header : '户数',width : 100},
		    {name : 'creditHouse',header : '授信户数',width : 100},
		    {name : 'creditBall',header : '授信金额',width : 100,type:'float'},
		    {name : 'usecreCust',header : '用信客户',width : 100},
		    {name : 'usecreBall',header : '用信金额',width : 100,type:'float'},
		    {name : 'loanBall',header : '贷款当前余额',width : 100,type:'float'},
		    {name : 'loanAvg',header : '贷款当前日均',width : 100,type:'float'},
		    {name : 'savingBallLay',header : '存款上年余额',width : 100,type:'float'},
		    {name : 'savingAvgLay',header : '存款上年日均',width : 100,type:'float'},
		    {name : 'savingBallLam',header : '存款上月余额',width : 100,type:'float'},
		    {name : 'savingAvgLam',header : '存款上月日均',width : 100,type:'float'},
		    {name : 'savingBallCum',header : '存款本月余额',width : 100,type:'float'},
		    {name : 'savingAvgCum',header : '存款本月日均',width : 100,type:'float'},
		    {name : 'etlDate',header : 'ETL日期',hidden:true,width : 100}
				],
				
		//设置分页每页显示条数，若不设置则不出现分页栏
		pagesize : 20,
		ppp:123,
		
		// 新增、修改、详情的form的字段
		fclms : [ {
			layout : 'column',
			items : [ {
				columnWidth : .5,
				layout : 'form',
				items : [ {	name : 'villaName',fieldLabel : '自然村名称',xtype : 'textfield',width : 100,maxLength : 200,anchor : '90%'} ]
			}, {
				columnWidth : .5,
				layout : 'form',
				items : [ {	name : 'villaBelong',fieldLabel : '所属村委',xtype : 'textfield',width : 100,maxLength : 200,anchor : '90%'} ]
			} ]
		},{
			layout : 'column',
			items : [ {
				columnWidth : .5,
				layout : 'form',
				items : [ {	name : 'townName',fieldLabel : '镇区名称',xtype : 'textfield',width : 100,maxLength : 200,anchor : '90%'} ]
			}, {
				columnWidth : .5,
				layout : 'form',
				items : [ {	name : 'orgId',fieldLabel : '管理机构',xtype : 'textfield',width : 100,maxLength : 200,anchor : '90%'} ]
			} ]
		},{
			layout : 'column',
			items : [ {
				columnWidth : .5,
				layout : 'form',
				items : [ {	name : 'hostUser',fieldLabel : '负责人',xtype : 'textfield',width : 100,maxLength : 200,anchor : '90%'} ]
			},{
				columnWidth : .5,
				layout : 'form',
				items : [ {	name : 'orgName',fieldLabel : '机构名称',xtype : 'textfield',width : 100,maxLength : 200,anchor : '90%'} ]
			} ]
		},{
			layout : 'column',
			items : [ {
				columnWidth : .5,
				layout : 'form',
				items : [ {	name : 'population',fieldLabel : '人口',xtype : 'textfield',width : 100,maxLength : 200,anchor : '90%'} ]
			},{
				columnWidth : .5,
				layout : 'form',
				items : [ {	name : 'houseNum',fieldLabel : '户数',xtype : 'textfield',width : 100,maxLength : 200,anchor : '90%'} ]
			} ]
		},{
			layout : 'column',
			items : [ {
				columnWidth : .5,
				layout : 'form',
				items : [ {	name : 'creditHouse',fieldLabel : '授信户数',xtype : 'textfield',width : 100,maxLength : 200,anchor : '90%'} ]
			},{
				columnWidth : .5,
				layout : 'form',
				items : [ {	name : 'creditBall',fieldLabel : '授信金额',xtype : 'textfield',width : 100,maxLength : 200,anchor : '90%'} ]
			} ]
		},{
			layout : 'column',
			items : [ {
				columnWidth : .5,
				layout : 'form',
				items : [ {	name : 'usecreCust',fieldLabel : '用信客户',xtype : 'textfield',width : 100,maxLength : 200,anchor : '90%'} ]
			},{
				columnWidth : .5,
				layout : 'form',
				items : [ {	name : 'usecreBall',fieldLabel : '用信金额',xtype : 'textfield',width : 100,maxLength : 200,anchor : '90%'} ]
			} ]
		},{
			layout : 'column',
			items : [ {
				columnWidth : .5,
				layout : 'form',
				items : [ {	name : 'loanBall',fieldLabel : '贷款当前余额',xtype : 'textfield',width : 100,maxLength : 200,anchor : '90%'} ]
			},{
				columnWidth : .5,
				layout : 'form',
				items : [ {	name : 'loanAvg',fieldLabel : '贷款当前日均',xtype : 'textfield',width : 100,maxLength : 200,anchor : '90%'} ]
			} ]
		},{
			layout : 'column',
			items : [ {
				columnWidth : .5,
				layout : 'form',
				items : [ {	name : 'savingBallLay',fieldLabel : '存款上年余额',xtype : 'textfield',width : 100,maxLength : 200,anchor : '90%'} ]
			},{
				columnWidth : .5,
				layout : 'form',
				items : [ {	name : 'savingAvgLay',fieldLabel : '存款上年日均',xtype : 'textfield',width : 100,maxLength : 200,anchor : '90%'} ]
			} ]
		},{
			layout : 'column',
			items : [ {
				columnWidth : .5,
				layout : 'form',
				items : [ {	name : 'savingBallLam',fieldLabel : '存款上月余额',xtype : 'textfield',width : 100,maxLength : 200,anchor : '90%'} ]
			},{
				columnWidth : .5,
				layout : 'form',
				items : [ {	name : 'savingAvgLam',fieldLabel : '存款上月日均',xtype : 'textfield',width : 100,maxLength : 200,anchor : '90%'} ]
			} ]
		},{
			layout : 'column',
			items : [ {
				columnWidth : .5,
				layout : 'form',
				items : [ {	name : 'savingBallCum',fieldLabel : '存款本月余额',xtype : 'textfield',width : 100,maxLength : 200,anchor : '90%'} ]
			},{
				columnWidth : .5,
				layout : 'form',
				items : [ {	name : 'savingAvgCum',fieldLabel : '存款本月日均',xtype : 'textfield',width : 100,maxLength : 200,anchor : '90%'} ]
			} ]
		},{
			//特别注意：
			//必须放置隐藏域的主键
			name : 'villaNo',xtype : 'hidden'
		} ]
		
	});

	// 布局模型
	var viewport = new Ext.Viewport( {
		layout : 'fit',
		items : [ listPanel ]
	});
	
	//添加监听，重置时，清空机构下拉框中的内容
	Ext.getCmp('commonExtPanelForReset').addListener('click',function(){
		Ext.getCmp("orgIdTemp").setValue('');
	});
	
});