/**
 * 功能：不良客户管理
 */

Ext.onReady(function() {
	Ext.QuickTips.init();
    // 机构下拉框的数据查询
	var orgId=JsContext._orgId;
	var orgName=JsContext._unitname;
	
	var tenTypeStore = new Ext.data.Store({  
		restful:true,   
	sortInfo:{
			field:'key',
			direction:'ASC'
		},
		autoLoad :true,
		proxy : new Ext.data.HttpProxy({
				url :basepath+'/lookup.json?name=TEN_LEVEL_STAT'
			}),
			reader : new Ext.data.JsonReader({
				root : 'JSON'
			}, [ 'key', 'value' ])
	});
	tenTypeStore.load();

/*	var search_cust = new Ext.ux.form.CustomerQueryField({ 
		fieldLabel : '客户名称', 
//		labelStyle: 'text-align:right;',
		name : 'borrowerName',
		id:'borrowerName',
		 editable : false,
		 allowBlank:false,//不允许为空
         blankText:"不能为空，请填写",
		singleSelected:true,
		width : 150,
			anchor : '90%',
			callback :function(){
					var cust_id = null;
						var cust_name = null;
						cust_name = Ext.getCmp('borrowerName').getValue();
						if (cust_name != null && cust_name != '') {
							cust_id = Ext.getCmp('borrowerName').customerId.aId[0];
							listPanel.fp.getForm().findField('custId').setValue(cust_id);
						}
			}
	});*/
	var search_cust = new Com.yucheng.crm.common.OrgUserManage({ 
		fieldLabel : '所属客户经理', 
		id:'borrowerName',
		labelStyle: 'text-align:left;',
		name : 'borrowerName',
		hiddenName:'custId',
		singleSelect:true,
		allowBlank:false,
		anchor : '90%'
		});

	// 最终展现的panel
	var listPanel =new Mis.Ext.CrudPanel( {
		id : "listPanel",
		title : "客户管理->不良客户管理",
		stUrl : basepath + '/badnessCustomer-info!indexPage.json',
		// 新增URL，如果不定义则不出现新增按钮
		addUrl : basepath + '/badnessCustomer-info.json',
		updateUrl : basepath + '/badnessCustomer-info.json',
		deUrl : basepath + '/badnessCustomer-info!batchDestroy.json',
		primary : "custId",
		checkbox : true,
		// 定义查询条件Form的高度
		seFormHeight : 80,
		// 定义增删详情页面弹出窗口高度
		winHeight : 450,
		// 宽度
		winWidth : 800,
		//重载afterSeOneFun方法，加载一条数据后做的特殊处理
		afterSeOneFun : function(b) {
			if(b.loanStartDate!=null&&b.loanStartDate!='')
				Ext.getCmp('loanStartDate').setValue(new Date(b.loanStartDate.time));
			if(b.loanEndDate!=null&&b.loanEndDate!='')
				Ext.getCmp('loanEndDate').setValue(new Date(b.loanEndDate.time));
			if(b.madeBadTime!=null&&b.madeBadTime!='')
				Ext.getCmp('madeBadTime').setValue(new Date(b.madeBadTime.time));
			if(b.changeLevelTime!=null&&b.changeLevelTime!='')
				Ext.getCmp('changeLevelTime').setValue(new Date(b.changeLevelTime.time));
		},
		// 查询字段定义，若不定义则不出现查询条件From
		selectItems : {
			layout : 'column',
			items : [
			   {columnWidth : .25,
				layout : 'form',
				defaultType : 'textfield',
				border : false,
				items : [{name : 'borrowerName',xtype : 'textfield',fieldLabel : '借款人姓名',width : '100',anchor : '90%'} ]
			   }, 
			   {columnWidth : .25,
				layout : 'form',
				labelWidth : 80,
				defaultType : 'textfield',
				border : false,
				items : [
			         new Com.yucheng.bcrm.common.OrgField({
							searchType:'SUBTREE',/*指定查询机构范围属性  SUBTREE（子机构树）SUBORGS（直接子机构）PARENT（父机构）PARPATH （所有父、祖机构）ALLORG（所有机构）*/
							fieldLabel : '*机构',
							labelStyle : 'text-align:right;',
							id : 'CUST_ORG', //放大镜组件ID，用于在重置清空时获取句柄
							name : 'institution', 
							hiddenName: 'institution',   //后台获取的参数名称
							anchor : '90%',
							checkBox:false //复选标志
						}) 
			         ]},
			   {columnWidth : .25,
				layout : 'form',
				labelWidth : 90,
				items : [{
						name : 'tenLevelStat',
						hiddenName : 'tenLevelStat',
						xtype : 'combo',
						fieldLabel : '十级分类状态',
						store : tenTypeStore,
						mode : 'local',
						valueField : 'key',
						displayField : 'value',
						editable:false,
						anchor : '90%'
								}]
				}]},
	
		// 查询列表字段定义，有header属性则在页面显示
		// 如果需要做映射需要定义store , mappingkey ,mappingvalue 三个属性
		gclms : [ 
		    {name : 'custId',header:'客户编号'},  
		    {name : 'borrowerName',header : '借款人姓名'},  
		    {name : 'borroweMoney',header : '借款金额',renderer:money('0,000.00')}, 
			{name : 'lendingRate',header : '贷款利率',renderer:money('0,000.000000%')},
		    {name : 'repaymentType',header : '还款方式'},
		    {name : 'loanStartDate',header : '贷款起贷日',type : 'date'},
		    {name : 'loanEndDate',header : '贷款到期日',type : 'date'},
		    {name : 'loanBalance',header : '贷款余额',renderer:money('0,000.00')},
		    {name : 'tenLevelStat',header : '十级分类状态',type:'mapping',store:tenTypeStore,mappingkey:'key',mappingvalue:'value'},
		    {name : 'madeBadTime',header : '产生不良时间',type : 'date'},
		    {name : 'changeLevelTime',header : '调级时间',type : 'date'},
		    {name : 'institution',header : '机构'},
		    {name : 'guarantor'},
		    {name : 'managerCreditName'},
		    {name : 'loanApproval'},
		    {name : 'affiGroup'},
		    {name : 'custBaseCondition'},
		    {name : 'disposeStepActuality'},
		    {name : 'remark'},
		    {name : 'madingBalance'},
		    {name : 'dataSource'}
		    ],
		// 设置分页每页显示条数，若不设置则不出现分页栏
		pagesize : 20,
		
		// 新增的form的字段
		// 特别注意：
		// 必须放置隐藏域的主键
		formColums :function(){
			return new Ext.form.FieldSet({items:[
				util.layout._tr([util.form._td({name : 'borroweMoney',xtype : 'numberfield',fieldLabel : '借款金额',allowBlank:false,blankText:"此项非空，请填写"})],
								[util.form._td({name : 'lendingRate',xtype : 'numberfield',fieldLabel : '贷款利率',allowBlank:false,blankText:"此项非空，请填写"})]
								),
				util.layout._tr([util.form._td({name : 'repaymentType',xtype : 'textfield',fieldLabel : '还款方式'})],
						        [util.form._td({name : 'loanBalance',xtype : 'numberfield',fieldLabel : '贷款余额'})]
								),
				util.layout._tr([util.form._td({name : 'guarantor',fieldLabel : '担保人',xtype : 'textfield',allowBlank:false,blankText:"此项非空，请填写"})],
						        [util.form._td({name : 'managerCreditName',xtype : 'textfield',fieldLabel : '管户信贷员',allowBlank:false,blankText:"此项非空，请填写"})]
				),
				util.layout._tr([util.form._td({name : 'loanApproval',fieldLabel : '贷款审批人',xtype : 'textfield',allowBlank:false,blankText:"此项非空，请填写"})],
						        [util.form._td({name : 'tenLevelStat',xtype : 'combo',fieldLabel : '十级分类状态',store:tenTypeStore,valueField:'key',displayField:'value',allowBlank:false,blankText:"此项非空，请填写",editable:false})]
				),
				util.layout._tr([util.form._td({name : 'affiGroup',fieldLabel : '所属团队',xtype : 'textfield'})],
						        [util.form._td({name : 'custBaseCondition',xtype : 'textfield',fieldLabel : '客户基本情况'})]
				),
				util.layout._tr([util.form._td({name : 'disposeStepActuality',fieldLabel : '处理措施及现状',xtype : 'textfield'})],
						        [util.form._td({name : 'remark',xtype : 'textfield',fieldLabel : '备注'})]
				                                                                                         
				),
				util.layout._tr([util.form._td({name : 'madingBalance',fieldLabel : '产生时的余额',xtype : 'numberfield',value:0})],
								[util.form._td({name : 'dataSource',fieldLabel : '数据来源',	xtype : 'textfield'})]
				),
				util.layout._tr([util.form._td(
						new Com.yucheng.bcrm.common.OrgField({
							searchType:'SUBTREE',/*指定查询机构范围属性  SUBTREE（子机构树）SUBORGS（直接子机构）PARENT（父机构）PARPATH （所有父、祖机构）ALLORG（所有机构）*/
							fieldLabel : '所属机构',
						//	labelStyle : 'text-align:right;',
							id : 'CUST_OGR2', //放大镜组件ID，用于在重置清空时获取句柄
							name : 'institution', 
							hiddenName: 'institution',   //后台获取的参数名称
							anchor : '90%',
							checkBox:false //复选标志
						})
						)],
						        [util.form._td(search_cust)]
		        ),
				util.layout._tr([util.form._td({id : 'loanStartDate',name : 'loanStartDate',fieldLabel : '贷款起贷日',xtype : 'datefield',allowBlank:false,blankText:"此项非空，请填写"})],
								[util.form._td({id : 'loanEndDate',name : 'loanEndDate',fieldLabel : '贷款到期日',xtype : 'datefield',allowBlank:false,blankText:"此项非空，请填写"})]
				),
				util.layout._tr([util.form._td({id : 'madeBadTime',name : 'madeBadTime',fieldLabel : '产生不良时间',xtype : 'datefield'})],
						        [util.form._td({id : 'changeLevelTime',name : 'changeLevelTime',fieldLabel : '调级时间',xtype : 'datefield'})]
		        ),
				util.layout._tr([util.form._td({name : 'custId',xtype : 'hidden'})]
				)
		]});}
	});				
    
	// 布局模型
	var viewport = new Ext.Viewport( {
		layout : 'fit',
		items : [listPanel]
	});
});
