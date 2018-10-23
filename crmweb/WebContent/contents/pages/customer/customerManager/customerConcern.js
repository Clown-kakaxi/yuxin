 var idStr = '';
Ext.onReady(function() {
	Ext.QuickTips.init();

	var cmeStore=util.form._store('/lookup.json?name=XD000080');
	cmeStore.load();
	var cmeeStore=util.form._store('/lookup.json?name=CDE0100016');
	cmeeStore.load();
	var cmeStatStore=util.form._store('/lookup.json?name=CUSTOMER_STATUS');
	cmeStatStore.load();
	var cerStatStore=util.form._store('/lookup.json?name=XD000040');
	cerStatStore.load();
//		// 最终展现的panel
		var listPanel = new Mis.Ext.CrudPanel( {
			id : "listPanel",
			title : "我关注的客户",
			//
			//seBaseForm ：true,
			stUrl : basepath + '/custConcern.json',
			// demoData :
			// {"json":{"count":9,"data":[{"UNITNAME":"总行","CREATE_ORG":"00001","CHANNEL_ID":"45396","CHANNEL_CODE":"","UPDATE_DATE":"2011-10-27","GUARANTEE":"","REMARK":"","CHANNEL_TYPE_NAME":"发改委","CHANNEL_NAME":"23","CHANNEL_FEATURE":"","ACCESS_CONDITION":"","CHANNEL_POLICY":"","CHANNEL_TYPE_ID":"10327","CREATE_USER":"admin","USERNAME":"系统管理员*","CREATE_DATE":"2011-10-27","UPDATE_USER":"admin"},{"UNITNAME":"总行","CREATE_ORG":"00001","CHANNEL_ID":"45386","CHANNEL_CODE":"","UPDATE_DATE":"2011-10-25","GUARANTEE":"","REMARK":"","CHANNEL_TYPE_NAME":"商会","CHANNEL_NAME":"7777777","CHANNEL_FEATURE":"","ACCESS_CONDITION":"","CHANNEL_POLICY":"","CHANNEL_TYPE_ID":"10328","CREATE_USER":"admin","USERNAME":"系统管理员*","CREATE_DATE":"2011-10-25","UPDATE_USER":"admin"},{"UNITNAME":"总行","CREATE_ORG":"00001","CHANNEL_ID":"45391","CHANNEL_CODE":"","UPDATE_DATE":"2011-10-25","GUARANTEE":"","REMARK":"","CHANNEL_TYPE_NAME":"商会","CHANNEL_NAME":"444444","CHANNEL_FEATURE":"","ACCESS_CONDITION":"123123","CHANNEL_POLICY":"","CHANNEL_TYPE_ID":"10328","CREATE_USER":"admin","USERNAME":"系统管理员*","CREATE_DATE":"2011-10-25","UPDATE_USER":"admin"},{"UNITNAME":"总行","CREATE_ORG":"00001","CHANNEL_ID":"45376","CHANNEL_CODE":"","UPDATE_DATE":"2011-10-24","GUARANTEE":"1","REMARK":"1","CHANNEL_TYPE_NAME":"委办局","CHANNEL_NAME":"测试2","CHANNEL_FEATURE":"1","ACCESS_CONDITION":"1","CHANNEL_POLICY":"1","CHANNEL_TYPE_ID":"10352","CREATE_USER":"admin","USERNAME":"系统管理员*","CREATE_DATE":"2011-10-09","UPDATE_USER":"admin"},{"UNITNAME":"北京管理部","CREATE_ORG":"00021","CHANNEL_ID":"17251","CHANNEL_CODE":"","UPDATE_DATE":"2011-10-14","GUARANTEE":"","REMARK":"","CHANNEL_TYPE_NAME":"委办局","CHANNEL_NAME":"新渠道22223333","CHANNEL_FEATURE":"","ACCESS_CONDITION":"","CHANNEL_POLICY":"","CHANNEL_TYPE_ID":"10352","CREATE_USER":"008755","USERNAME":"范*","CREATE_DATE":"2011-10-14","UPDATE_USER":"008755"},{"UNITNAME":"白云支行","CREATE_ORG":"04101","CHANNEL_ID":"17148","CHANNEL_CODE":"","UPDATE_DATE":"2011-10-14","GUARANTEE":"","REMARK":"","CHANNEL_TYPE_NAME":"委办局","CHANNEL_NAME":"白云山","CHANNEL_FEATURE":"","ACCESS_CONDITION":"","CHANNEL_POLICY":"","CHANNEL_TYPE_ID":"10352","CREATE_USER":"000727","USERNAME":"刘*","CREATE_DATE":"2011-10-09","UPDATE_USER":"000727"},{"UNITNAME":"总行","CREATE_ORG":"00001","CHANNEL_ID":"12367","CHANNEL_CODE":"","UPDATE_DATE":"2011-09-24","GUARANTEE":"","REMARK":"","CHANNEL_TYPE_NAME":"委办局","CHANNEL_NAME":"啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊1","CHANNEL_FEATURE":"","ACCESS_CONDITION":"","CHANNEL_POLICY":"","CHANNEL_TYPE_ID":"10352","CREATE_USER":"admin","USERNAME":"系统管理员*","CREATE_DATE":"2011-09-24","UPDATE_USER":"admin"},{"UNITNAME":"总行","CREATE_ORG":"00001","CHANNEL_ID":"45390","CHANNEL_CODE":"","UPDATE_DATE":"2011-10-25","GUARANTEE":"","REMARK":"","CHANNEL_TYPE_NAME":"财政","CHANNEL_NAME":"12212","CHANNEL_FEATURE":"","ACCESS_CONDITION":"","CHANNEL_POLICY":"","CHANNEL_TYPE_ID":"11006","CREATE_USER":"admin","USERNAME":"系统管理员*","CREATE_DATE":"2011-10-25","UPDATE_USER":"admin"},{"UNITNAME":"总行","CREATE_ORG":"00001","CHANNEL_ID":"17253","CHANNEL_CODE":"","UPDATE_DATE":"2011-10-14","GUARANTEE":"","REMARK":"","CHANNEL_TYPE_NAME":"财政","CHANNEL_NAME":"总行客户经理","CHANNEL_FEATURE":"","ACCESS_CONDITION":"","CHANNEL_POLICY":"","CHANNEL_TYPE_ID":"11006","CREATE_USER":"010514","USERNAME":"韩*","CREATE_DATE":"2011-10-14","UPDATE_USER":"010514"}]}},
			//新增URL，如果不定义则不出现新增按钮
			//addUrl : basepath + '/customerManagerLearning.json',
			//updateUrl : basepath + '/customerManagerLearning.json',
			deUrl : basepath + '/custConcernDel!batchDestroy.json',
			primary : "id",
			checkbox : true,
			//定义查询条件Form的高度
//			seFormHeight : 80,
			//定义增删详情页面弹出窗口高度
			winHeight : 450,
			//宽度
			winWidth : 800,
			//设置分页每页显示条数，若不设置则不出现分页栏
			pagesize : 20,
//			//重载afterSeOneFun方法，加载一条数据后做的特殊处理
//			afterSeOneFun : function(b) {
////				//debugger;
//			Ext.getCmp('from').setValue(new Date(b.FR_DATE.time));
//		    Ext.getCmp('to').setValue(new Date(b.TO_DATE.time));
//			},
			dbclick : true,
			detail :function(){

	        var checkedNodes = listPanel.grid.selModel.getSelections();
				if(checkedNodes.length==0){
					Ext.Msg.alert('提示', '未选择任何客户');
					return ;
				}else if(checkedNodes.length>1){
					Ext.Msg.alert('提示', '您只能选中一个客户进行查看');
					return ;
				}
				var viewWindow = new Com.yucheng.crm.cust.ViewWindow({
					id:'viewWindow',
					custId:checkedNodes[0].data.custId,
					custName:checkedNodes[0].data.custZhName,
					custTyp:checkedNodes[0].data.custTyp
				});
				
				Ext.Ajax.request({
					url : basepath + '/commsearch!isMainType.json',
					method : 'GET',
					params : {
					'mgrId' : __userId,
					'custId' : checkedNodes[0].data.custId
				},
				success : function(response) {
					var anaExeArray = Ext.util.JSON.decode(response.responseText); 
				if(anaExeArray.json != null){
					if(anaExeArray.json.MAIN_TYPE=='1'){
						oCustInfo.omain_type=true;
					}else{
						oCustInfo.omain_type=false;
					}}
				else {
					oCustInfo.omain_type=false;
				}
					oCustInfo.cust_id = checkedNodes[0].data.custId;
					oCustInfo.cust_name = checkedNodes[0].data.custZhName;
					oCustInfo.cust_type = checkedNodes[0].data.custTyp;
					oCustInfo.view_source = 'viewport_center';
					viewWindow.show();
				
				},
				failure : function(form, action) {}
				});
			
			
		},
			// 查询字段定义，若不定义则不出现查询条件From
			selectItems :
				util.layout._tr([util.form._td({name : 'custId',columnWidth : .25,mapping:'CUST_ID',xtype : 'textfield',fieldLabel : '客户编号'})],
								[util.form._td({name : 'custZhName',columnWidth : .25,mapping:'CUST_ZH_NAME',xtype : 'textfield',fieldLabel : '客户姓名'})]
//								[util.form._td({name : 'FR_DATE',xtype : 'datefield',fieldLabel : '学习时间从'})]
//								[util.form._td({name : 'TO_DATE',xtype : 'datefield',fieldLabel : '学习时间至'})	]
								),
	
			//查询列表字段定义，有header属性则在页面显示
			//如果需要做映射需要定义store , mappingkey ,mappingvalue 三个属性
			gclms : [ {name:'id',mapping:'ID'},
			    {name : 'custId',mapping:'CUST_ID',header:'客户编号'},  
			    {name : 'custZhName',mapping:'CUST_ZH_NAME',header : '客户名称'},  
				{name : 'custTyp',mapping:'CUST_TYP',header : '客户类型',type :'mapping',store : cmeStore, mappingkey : 'key',mappingvalue : 'value',hidden:true,hideable:false},
				{name : 'custLev',mapping:'CUST_LEV',header : '客户级别',type :'mapping',store : cmeeStore, mappingkey : 'key',mappingvalue : 'value'},
				{name : 'linkUser',mapping:'LINK_USER',header:'联系人'},  
			    {name : 'telephoneNum',mapping:'TELEPHONE_NUM',header : '手机号码'},  
				{name : 'custStat',mapping:'CUST_STAT',header : '客户状态',type :'mapping',store : cmeStatStore, mappingkey : 'key',mappingvalue : 'value'},
				{name : 'certType',mapping:'CERT_TYPE',header : '证件类型',type :'mapping',store : cerStatStore, mappingkey : 'key',mappingvalue : 'value'},
				{name : 'certNum',mapping:'CERT_NUM',header : '证件号码'}

				
			],
			buts:[{
				text : '客户视图',
				iconCls:'ReadIconCss',
				handler : function() {
		        var checkedNodes = listPanel.grid.selModel.getSelections();
					if(checkedNodes.length==0){
						Ext.Msg.alert('提示', '未选择任何客户');
						return ;
					}else if(checkedNodes.length>1){
						Ext.Msg.alert('提示', '您只能选中一个客户进行查看');
						return ;
					}
					var viewWindow = new Com.yucheng.crm.cust.ViewWindow({
						id:'viewWindow',
						custId:checkedNodes[0].data.custId,
						custName:checkedNodes[0].data.custZhName,
						custTyp:checkedNodes[0].data.custTyp
					});
					
					Ext.Ajax.request({
						url : basepath + '/commsearch!isMainType.json',
						method : 'GET',
						params : {
						'mgrId' : __userId,
						'custId' : checkedNodes[0].data.custId
					},
					success : function(response) {
						var anaExeArray = Ext.util.JSON.decode(response.responseText); 
					if(anaExeArray.json != null){
						if(anaExeArray.json.MAIN_TYPE=='1'){
							oCustInfo.omain_type=true;
						}else{
							oCustInfo.omain_type=false;
						}}
					else {
						oCustInfo.omain_type=false;
					}
						oCustInfo.cust_id = checkedNodes[0].data.custId;
						oCustInfo.cust_name = checkedNodes[0].data.custZhName;
						oCustInfo.cust_type = checkedNodes[0].data.custTyp;
						oCustInfo.view_source = 'viewport_center';
						viewWindow.show();
					
					},
					failure : function(form, action) {}
					});
				}
			},'-',
			{
				text : '加入客户群',
				iconCls:'addIconCss',
				handler : function() {
				var selectLength = listPanel.grid
				.getSelectionModel()
				.getSelections().length;
		if (selectLength < 1) {
			Ext.Msg.alert('提示','请至少选择一个客户!');
		}

		else {     
				var selectRe;
				var tempId;
				var custType,m,n;
				for ( var i = 0; i < selectLength; i++) {
					selectRe = listPanel.grid
							.getSelectionModel()
							.getSelections()[i];
					tempId = selectRe.data.custId;
					custType = selectRe.data.custTyp;
					if(custType == '1')
					{
					    m=1;
					}
					else if(custType == '2')
					{
						n=1;
					}
					
					idStr += tempId;
					if (i != selectLength - 1)
						idStr += ',';
				}
				groupMemberType = custType;
				if(m==1 && n==1)
					groupMemberType = '3';
				choseWin.show();
						choseWayForm.form.findField('custGroup').setVisible(false);
						choseWayForm.getForm().reset();
				}
			}
			}
			      ]
//			,
//			
//			// 新增、修改、详情的form的字段
//			formColums :function(){}

		});
		

		
		// 布局模型
		var viewport = new Ext.Viewport( {
			layout : 'fit',
			items : [ listPanel ]
		});
		
		
		
	});