Ext.onReady(function() {
	Ext.QuickTips.init();
	//重点客户类型
	var imptCustTypStore = new Ext.data.Store( {
		restful : true,
		autoLoad : true,
		sortInfo:{
			field:'key',
			direction:'ASC'
			},
		proxy : new Ext.data.HttpProxy( {
			url : basepath + '/lookup.json?name=IMPT_CUST_TYPE'
		}),
		reader : new Ext.data.JsonReader( {
			root : 'JSON'
		},[{name:'key',type:'int'}, 'value' ])
	});
	imptCustTypStore.load();
	//证件类型
	var certTypStore = new Ext.data.Store( {
		restful : true,
		autoLoad : true,
		proxy : new Ext.data.HttpProxy( {
			url : basepath + '/lookup.json?name=XD000040'
		}),
		reader : new Ext.data.JsonReader( {
			root : 'JSON'
		},['key','value'])
	});
	certTypStore.load();
	//客户类型
	var custTypStore = new Ext.data.Store( {
		restful : true,
		autoLoad : true,
	
		proxy : new Ext.data.HttpProxy( {
			url : basepath + '/lookup.json?name=XD000080'
		}),
		reader : new Ext.data.JsonReader( {
			root : 'JSON'
		},['key','value'])
	});
	custTypStore.load();
	/********创建小组start************/
	
	 var teamStatusStore = new Ext.data.ArrayStore({
	        fields:['myId','displayText'],
	        data:[['1','正常'],['2','注销']]
	        });
	 debugger;
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
//														store.reload({
//												params : {
//												start : 0,
//												limit :bbar.pageSize
//												                    }
//												                });
													} else{
														if(resultArray == 403){
															Ext.Msg.alert('提示', response.responseText);
															}
														else {
														Ext.Msg.alert('提示', '操作失败,失败原因:' + resultError);
//														store.reload({
//												params : {
//												start : 0,
//												limit :bbar.pageSize
//												                    }
//												                });
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
	/*********创建小组end**********/
		// 最终展现的panel
		var listPanel = new Mis.Ext.CrudPanel( {
			id : "listPanel",
			title : "客户管理->重点户管理->重点户查询",
			 demoData :
			 {
						"json" : {
							"count" : 5,
							"data" : [
									{
										"CUST_ID"      : "190",
										"CUST_ZH_NAME" : "金龙集团",
										'MGR_NAME'	   :'张锦',
										'SUPBRID' 	   :'某某银行',
										'TWO'          : '某某支行',
										'INSTITUTION_NAME': '某某银行',
										'CUST_TYP'     : "37253",
										'CUST_LEV'     : "B",
										'CERT_TYPE'    : "身份证",
										'CERT_NUM'     : "190996",
										'IF_IMPORTANT_CUST':'是',
										'IMPORT_CUST_TYPE':'重点类型三',
										 'yuer'         :'1000000',
										 'hangye'       :'IT',
										 'zuzhi'        :'事业单位',
										 'suoyouzhi'    :'民营企业',
										 'guim'         :'3000',
										 'ROTECB'       :'3%',
										 'CKBAL'        :'100000',
										 'CKBALAVG'     :'200000',
										 'LOANBAL'      :'100000',
										 'LOANBALAVG'   :'100000',
										 'CDBAL'        :'100000',
										 'DPCDBAL'      :'100000',
										 'CDSUM'        :'100000',
										 'DPCDSUM'      :'100000',
										 'TIEXBAL'      :'100000',
										 'IEXBAL'       :'100000',
										 'DPTIEXBAL'    :'100000',
										 'CUSTSUMBAL'   :'100000',
										 'MIDBAL'       :'100000', 
										 'NASSUMBAL'    :'100000',
										 'EBANKSUM'     :'100000'
										 
									},
									{
										"CUST_ID"      : "134",
										"CUST_ZH_NAME" : "秀双有限公司",
										'MGR_NAME'	   :'王婉',
										'SUPBRID' 	   :'某某银行',
										'TWO'          : '某某支行',
										'INSTITUTION_NAME': '某某银行',
										'CUST_TYP'     : "37253",
										'CUST_LEV'     : "A",
										'CERT_TYPE'    : "身份证",
										'CERT_NUM'     : "196666",
										'IF_IMPORTANT_CUST':'是',
										'IMPORT_CUST_TYPE':'重点类型三',
										 'yuer'         :'1000000',
										 'hangye'       :'IT',
										 'zuzhi'        :'事业单位',
										 'suoyouzhi'    :'民营企业',
										 'guim'         :'3000',
										 'ROTECB'       :'3%',
										 'CKBAL'        :'100000',
										 'CKBALAVG'     :'200000',
										 'LOANBAL'      :'100000',
										 'LOANBALAVG'   :'100000',
										 'CDBAL'        :'100000',
										 'DPCDBAL'      :'100000',
										 'CDSUM'        :'100000',
										 'DPCDSUM'      :'100000',
										 'TIEXBAL'      :'100000',
										 'IEXBAL'       :'100000',
										 'DPTIEXBAL'    :'100000',
										 'CUSTSUMBAL'   :'100000',
										 'MIDBAL'       :'100000', 
										 'NASSUMBAL'    :'100000',
										 'EBANKSUM'     :'100000'
									},
									{
										"CUST_ID"      : "156",
										"CUST_ZH_NAME" : "丽慧有限公司",
										'MGR_NAME'	   :'李晓明',
										'SUPBRID' 	   :'某某银行',
										'TWO'          : '某某支行',
										'INSTITUTION_NAME': '某某银行',
										'CUST_TYP'     : "37253",
										'CUST_LEV'     : "A",
										'CERT_TYPE'    : "身份证",
										'CERT_NUM'     : "133336",
										'IF_IMPORTANT_CUST':'是',
										'IMPORT_CUST_TYPE':'重点类型三',
										 'yuer'         :'1000000',
										 'hangye'       :'IT',
										 'zuzhi'        :'事业单位',
										 'suoyouzhi'    :'民营企业',
										 'guim'         :'3000',
										 'ROTECB'       :'3%',
										 'CKBAL'        :'100000',
										 'CKBALAVG'     :'200000',
										 'LOANBAL'      :'100000',
										 'LOANBALAVG'   :'100000',
										 'CDBAL'        :'100000',
										 'DPCDBAL'      :'100000',
										 'CDSUM'        :'100000',
										 'DPCDSUM'      :'100000',
										 'TIEXBAL'      :'100000',
										 'IEXBAL'       :'100000',
										 'DPTIEXBAL'    :'100000',
										 'CUSTSUMBAL'   :'100000',
										 'MIDBAL'       :'100000', 
										 'NASSUMBAL'    :'100000',
										 'EBANKSUM'     :'100000'
									}, {
										"CUST_ID"      : "189",
										"CUST_ZH_NAME" : "志强有限公司",
										'MGR_NAME'	   :'赵毅',
										'SUPBRID' 	   :'某某银行',
										'TWO'          : '某某支行',
										'INSTITUTION_NAME': '某某银行',
										'CUST_TYP'     : "37253",
										'CUST_LEV'     : "A",
										'CERT_TYPE'    : "身份证",
										'CERT_NUM'     : "12226",
										'IF_IMPORTANT_CUST':'是',
										'IMPORT_CUST_TYPE':'重点类型三',
										 'yuer'         :'1000000',
										 'hangye'       :'IT',
										 'zuzhi'        :'事业单位',
										 'suoyouzhi'    :'民营企业',
										 'guim'         :'3000',
										 'ROTECB'       :'3%',
										 'CKBAL'        :'100000',
										 'CKBALAVG'     :'200000',
										 'LOANBAL'      :'100000',
										 'LOANBALAVG'   :'100000',
										 'CDBAL'        :'100000',
										 'DPCDBAL'      :'100000',
										 'CDSUM'        :'100000',
										 'DPCDSUM'      :'100000',
										 'TIEXBAL'      :'100000',
										 'IEXBAL'       :'100000',
										 'DPTIEXBAL'    :'100000',
										 'CUSTSUMBAL'   :'100000',
										 'MIDBAL'       :'100000', 
										 'NASSUMBAL'    :'100000',
										 'EBANKSUM'     :'100000'
									}, {
										"CUST_ID"      : "109",
										"CUST_ZH_NAME" : "科技有限公司",
										'MGR_NAME'	   :'李丽',
										'SUPBRID' 	   :'某某银行',
										'TWO'          : '某某支行',
										'INSTITUTION_NAME': '某某银行',
										'CUST_TYP'     : "5553",
										'CUST_LEV'     : "A",
										'CERT_TYPE'    : "身份证",
										'CERT_NUM'     : "1456",
										'IF_IMPORTANT_CUST':'是',
										'IMPORT_CUST_TYPE':'重点类型三',
										 'yuer'         :'1000000',
										 'hangye'       :'IT',
										 'zuzhi'        :'事业单位',
										 'suoyouzhi'    :'民营企业',
										 'guim'         :'3000',
										 'ROTECB'       :'3%',
										 'CKBAL'        :'100000',
										 'CKBALAVG'     :'200000',
										 'LOANBAL'      :'100000',
										 'LOANBALAVG'   :'100000',
										 'CDBAL'        :'100000',
										 'DPCDBAL'      :'100000',
										 'CDSUM'        :'100000',
										 'DPCDSUM'      :'100000',
										 'TIEXBAL'      :'100000',
										 'IEXBAL'       :'100000',
										 'DPTIEXBAL'    :'100000',
										 'CUSTSUMBAL'   :'100000',
										 'MIDBAL'       :'100000', 
										 'NASSUMBAL'    :'100000',
										 'EBANKSUM'     :'100000'
									}]}},
			primary : "CUST_ID",
			checkbox : true,
			// 定义查询条件Form的高度
			seFormHeight : 80,
			// 定义增删详情页面弹出窗口高度
			winHeight : 250,
			//宽度
			winWidth : 600,
			dbclick : false,
			//新增
			// 查询字段定义，若不定义则不出现查询条件From
			selectItems : {
				layout : 'column',
				items : [ {
					columnWidth : .25,
					layout : 'form',
					labelWidth : 90,
					defaultType : 'textfield',
					border : false,
					items : [ {name : '',xtype : 'textfield',fieldLabel : '客户中文名称',anchor : '90%'
					} ]
				},{
					columnWidth : .25,
					layout : 'form',
					labelWidth :90,
					border : false,
					items : [ {store : imptCustTypStore,xtype : 'combo',resizable : true,fieldLabel : '重点户类型',name : '',hiddenName : '',
						valueField : 'key',displayField : 'value',mode : 'local',typeAhead : true,forceSelection : true,triggerAction : 'all',
						emptyText : '请选择',selectOnFocus : true,anchor : '90%'} ]
				},{
					columnWidth : .25,
					layout : 'form',
					labelWidth : 90,
					defaultType : 'textfield',
					border : false,
					items : [ new Com.yucheng.bcrm.common.OrgField({
						searchType:'SUBTREE',/*指定查询机构范围属性  SUBTREE（子机构树）SUBORGS（直接子机构）PARENT（父机构）PARPATH （所有父、祖机构）ALLORG（所有机构）*/
						fieldLabel : '所属机构',
						labelStyle : 'text-align:right;',
						id : 'CUST_ORG', //放大镜组件ID，用于在重置清空时获取句柄
						name : 'CUST_ORG', 
						hiddenName: 'instncode',   //后台获取的参数名称
						anchor : '90%',
						checkBox:true //复选标志
					}) ]
				} ]
			},
			gclms : [ {
	name : 'CUST_ID',header : '客户编号',width:100,sortable : true}, 
	{header : '客户名称',name : 'CUST_ZH_NAME',width:150,sortable : true},
	{header : '客户维护人',name : 'MGR_NAME',width:100,sortable : true},
	{header : '一级分行',name : 'SUPBRID',width:100,sortable : true},
    {header : '二级分行',name : 'TWO',width:100,sortable : true},
    {header : '所属机构',name : 'INSTITUTION_NAME',width:100,sortable : true},
    {header : '客户类型',name : 'CUST_TYP',width:100},
	{header : '客户级别',name : 'CUST_LEV',width:100,sortable : true}, 
	{header : '证件类型',name : 'CERT_TYPE',width : 100},
	{header : '证件号码',name : 'CERT_NUM',width : 100,sortable : true},
	{header : '是否重点户',name:'IF_IMPORTANT_CUST',width : 100,sortable : true},
	{header : '重点客户类型',name : 'IMPORT_CUST_TYPE',width : 100,sortable : true},
	{header : '有效授信额度',name :'shouxin',width : 100,sortable : true},
    {header : '用信余额',name :'yuer',width : 100,sortable : true},
	{header : '行业分类',name:'hangye',width:100,sortable : true},
    {header : '组织类别',name:'zuzhi',width:100,sortable : true},
    {header : '所有制',name:'suoyouzhi',width:100,sortable : true},
    {header : '客户规模',name:'guim',width:100,sortable : true},
    {header : '利润贡献度',name :'ROTECB',width:100,sortable : true},
    {header : '存款余额',name :'CKBAL',width:100,sortable : true},
    {header : '存款日均',name :'CKBALAVG',width : 100,sortable : true},
    {header : '贷款余额',name :'LOANBAL',width : 100,sortable : true},
    {header : '贷款日均',name :'LOANBALAVG',width : 100,sortable : true},
    {header : '承兑余额',name :'CDBAL',width : 100,sortable : true},
    {header : '其中:电票承兑余额',name :'DPCDBAL',width : 100,sortable : true},
    {header : '承兑累计',name :'CDSUM',width : 100,sortable : true},
    {header : '其中：电票承兑累计',name :'DPCDSUM',width : 100,sortable : true},
    {header : '贴现累计',name :'TIEXBAL',width : 100,sortable : true},
    {header : '其中：电票贴现累计',name :'DPTIEXBAL',width : 100,sortable : true},
    {header : '客户累计结算量',name :'CUSTSUMBAL',width : 100,sortable : true},
    {header : '中间业务收入',name :'MIDBAL',width : 100,sortable : true},
    {header : '国际结算量',name :'NASSUMBAL',width : 100,sortable : true},
    {header : '电子银行结算量',name :'EBANKSUM',width : 100,sortable : true}
	],buts : ['-',	new Ext.Button({
		text : '创建小组',
		iconCls:'addIconCss',
		listeners : {
			click : function(n) {
				if (listPanel.grid.selModel.hasSelection()) {
					var records = listPanel.grid.selModel.getSelections();// 得到被选择的行的数组
					var recordsLen = records.length;// 得到行数组的长度
					if (recordsLen != 1) {
						Ext.Msg.alert("系统提示信息", "请选择其中一条记录！");
					} else {
						addInit();
					}
				} else {
					Ext.Msg.alert("提示", "请先选择一条记录!");
				}
			}
		}
	}
	)
	],
		pagesize : 20
		});
	
		
		// 布局模型
		var viewport = new Ext.Viewport( {
			layout : 'fit',
			items : [ listPanel ]
		});
	});