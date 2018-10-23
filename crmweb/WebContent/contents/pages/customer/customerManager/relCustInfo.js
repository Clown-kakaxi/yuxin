Ext.onReady(function() {
	Ext.QuickTips.init();
	// 得到当前用户的客户号
		var cust_id = oCustInfo.cust_id;
		var cust_name = oCustInfo.cust_name;
		var op_flag ='';
		var relTypeStore = new Ext.data.Store({
				restful : true,
				autoLoad : true,
				proxy : new Ext.data.HttpProxy({
					url : basepath + '/lookup.json?name=CUST_REL_TYPE'
				}),
				reader : new Ext.data.JsonReader({
					root : 'JSON'
				}, [ 'key', 'value' ])
			});
			relTypeStore.load();
			var relationNameStore=util.form._store('/CustRelLookupItem!queryLookupItems.json','code','value');
			relationNameStore.load();
//		var relationNameStore = new Ext.data.JsonStore({
//				restful : true,
//				autoLoad : true,
//				proxy : new Ext.data.HttpProxy({
//					url : basepath + '/CustRelLookupItem!queryLookupItems.json'
//				}),
//				fields : [ 'code', 'value' ],
//				reader : new Ext.data.JsonReader({
//					totalProperty : 'list'
//				}, [ {
//					name : 'code'
//				}, {
//					name : 'value'
//				} ])
//			});
	     var relDirectStore = new Ext.data.ArrayStore({
	        fields:['key','value'],
	        data:[['上下级关系','->'],['合作关系','<->'],['从属关系','<-']]
	    });
	    
		var search_cust = new Com.yucheng.bcrm.common.CustomerQueryField({ 
				fieldLabel : '关联客户名称', 
				labelStyle: 'text-align:right;',
				name : 'relaCustName',
				hiddenName:'relaCustName',
				//store: store, 
				id:'rel_cust_name',
				 editable : false,
				 allowBlank:false,//不允许为空
                 blankText:"不能为空，请填写",
				singleSelected:true,
				callback :function(){
			debugger;
					 			var relcust_id = null;
								var relcust_name = null;
								relcust_name = Ext.getCmp('rel_cust_name').getValue();
								if (relcust_name != null && relcust_name != '') {
									if(relcust_name==cust_name){
										Ext.Msg.alert('消息框','关联客户不能为本人，请检查！');
										Ext.getCmp('rel_cust_name').setValue("");
									}else{
									relcust_id = Ext.getCmp('rel_cust_name').customerId;
									rel_form_1.getForm().findField('relaCustId').setValue(relcust_id);
									}
								}
				}
			});
			
		var rel_form_1 = new Ext.form.FormPanel({
			id :'rel_form_1',
			layout : 'form',
			autoScroll : true,
			labelAlign : 'right',
			frame : true,
			border :true,
			bodyBorder :false,
			defaultType :'textfield',
			defaults :{
			labelWidth: 100,
			width: 250
			},
			items:[
			{name : 'custId',xtype : 'textfield',fieldLabel : '客户编号',readOnly : true},
			{name : 'custName',fieldLabel : '客户名称',xtype : 'textfield',readOnly : true},
			{name : 'relaCustId',fieldLabel : '关联客户号',xtype : 'textfield',hidden:true},
			search_cust,
			{		id :'combo_relationType',
					hiddenName : 'relationType',
					name:'relationType',
					fieldLabel : '关联类型',
					xtype : 'combo',
					store : relTypeStore,
					valueField : 'key',
					displayField : 'value',
					mode : 'local',
					typeAhead : true,
					forceSelection : true,
					triggerAction : 'all',
					emptyText : '请选择',
					allowBlank : false,// 不允许为空
					blankText : "不能为空，请填写",
					resizable : true,
					selectOnFocus : true,
					listeners :{
					 	'select' :function(v1,v2){
					 				Ext.getCmp('relation_Name').setValue('');
								if(v1.value=='1'){
									v2 = '1';
									rel_form_2.hide();
									rel_form_2.form.reset();
									rel_form_3.show();
									rel_form_4.hide();
									rel_form_4.form.reset();
								}else if(v1.value=='2'){
									v2 = '2';
									rel_form_2.hide();
									rel_form_2.form.reset();
									rel_form_3.hide();
									rel_form_3.form.reset();
									rel_form_4.show();
								}else if(v1.value=='3'){
									v2 = '3';
									rel_form_2.show();
									rel_form_3.hide();
									rel_form_3.form.reset();
									rel_form_4.hide();
									rel_form_4.form.reset();
								}
//								relationNameStore.baseParams = {
//									"condition" : v2
//								};
								relationNameStore.load({
								   params:{
							  	     "condition" : v2
								   }
								});
							}
					}
				}, {
					id :'relation_Name',
					hiddenName : 'relationName',
					name:'relationName',
					fieldLabel : '关联关系',
					xtype : 'combo',
					store : relationNameStore,
					valueField : 'code',
					displayField : 'value',
					mode : 'local',
					typeAhead : true,
					forceSelection : true,
					triggerAction : 'all',
					emptyText : '请选择',
					allowBlank : false,// 不允许为空
					blankText : "不能为空，请填写",
					resizable : true,
					selectOnFocus : true,
					listeners :{
						'focus':function(){
						if(Ext.getCmp('combo_relationType').getValue()==''){
								 var v2 = '-1';
							relationNameStore.load({
								params:{
							  	"condition" : v2
								}
							});
						}
						}
					}
				}, {
					name : 'mxtid',
					xtype : 'hidden'
				}
			]
		});
		
		
		var rel_form_2 = new Ext.form.FormPanel ({
			id :'rel_form_2',
			layout : 'form',
			autoScroll : true,
			hidden :true,
			border :true,
			bodyBorder :false,
			labelAlign : 'right',
			frame : true,
			defaultType :'textfield',
			defaults :{
				labelWidth: 100,
				width: 250
				},
			items:[ {
					id:'direct_0',
					hiddenName : 'direct',
					name:'direct',
					fieldLabel : '关联方向',
					xtype : 'combo',
					store : relDirectStore,
					resizable : true,
					allowBlank : false,// 不允许为空
					blankText : "不能为空，请填写",
					valueField : 'key',
					displayField : 'value',
					mode : 'local',
					typeAhead : true,
					forceSelection : true,
					triggerAction : 'all',
					emptyText : '请选择',
					selectOnFocus : true
				}, {
					name : 'amt',
					fieldLabel : '投资金额',
					xtype : 'numberfield',
					value:0,
					regex : /^\d+\.?\d{0,2}$/,
					regexText : '例：9999999.99',
					maxLength :20,
					maxLengthText :'内容过长,请检查',
					allowBlank : false,// 不允许为空
					blankText : "不能为空，请填写"
				}, {
					name : 'kgRate',
					fieldLabel : '控股比例',
					xtype : 'numberfield',
					value:0,
					regex : /^[0]\.{0,1}\d*$/,
					regexText : '例：0.999',
					maxLength :5,
					maxLengthText :'内容过长,请检查',
					allowBlank : false,// 不允许为空
					blankText : "不能为空，请填写"
				},{
					name : 'sxAmt',
					fieldLabel : '授信金额',
					xtype : 'numberfield',
					value:0,
					regex : /^\d+\.?\d{0,2}$/,
					maxLength :19,
					maxLengthText :'内容过长,请检查',
					regexText : '例：9999999.99'
				},{
					name : 'yxBal',
					fieldLabel : '用信余额',
					xtype : 'numberfield',
					value:0,
					regex : /^\d+\.?\d{0,2}$/,
					maxLength :19,
					maxLengthText :'内容过长,请检查',
					regexText : '例：9999999.99'
				}]
			});
			var rel_form_3 = new Ext.form.FormPanel ({
							id :'rel_form_3',
							layout : 'form',
							autoScroll : true,
							labelAlign : 'right',
							hidden: true,
							border :true,
							bodyBorder :false,
							frame : true,
							defaultType :'textfield',
							defaults :{
							labelWidth: 100,
							width: 250
							},
							items:[	{name : 'maxEdu',fieldLabel : '最高学历',xtype : 'textfield'},
									{name : 'position',fieldLabel : '职务',xtype : 'textfield'},
									{id:'relcustinfo_birthday',name : 'birthday',editable:false,fieldLabel : '生日',xtype : 'datefield',format : 'Y-m-d'},
									{name : 'amt',fieldLabel : '参股金额',xtype : 'numberfield',value:0,regex : /^\d+\.?\d{0,2}$/,
										maxLength :20,maxLengthText :'内容过长,请检查',regexText : '例：9999999.99'},
									{name : 'kgRate',fieldLabel : '参股比例',xtype : 'numberfield',value:0,regex : /^[0]\.{0,1}\d*$/,
										regexText : '例：0.999',maxLength :5,maxLengthText :'内容过长,请检查'}
								]
							});
				var rel_form_4 = new Ext.form.FormPanel ({
							id :'rel_form_4',
							layout : 'form',
							autoScroll : true,
							labelAlign : 'right',
							border :true,
							bodyBorder :false,
							hidden:true,
							frame : true,
							defaultType :'textfield',
							defaults :{
							labelWidth: 100,
							width: 250
							},
							items:[{name : 'contactPhone',fieldLabel : '联系电话',xtype : 'textfield'},
									{name : 'telphone',fieldLabel : '手机号码',xtype : 'numberfield'},
									{name : 'workkUnit',fieldLabel : '工作单位',xtype : 'textfield'},
									{name : 'position',fieldLabel : '职位',xtype : 'textfield'}
								]
							});
				var rel_form_5 = new Ext.form.FormPanel ({
							id :'rel_form_5',
							layout : 'form',
							autoScroll : true,
							labelAlign : 'right',
							border :true,
							bodyBorder :false,
							frame : true,
							defaultType :'textfield',
							defaults :{
							labelWidth: 100,
							width: 250
							},
							items:[{name : 'demo',fieldLabel : '关系描述',xtype : 'textarea'},
							{name : 'createUser',fieldLabel : '创建人',xtype : 'textfield',readOnly : true},
							{name : 'createOrg',fieldLabel : '创建机构',xtype : 'textfield',readOnly : true},
							{id:'relcustinfo_createDate',name : 'createDate',fieldLabel : '创建日期',xtype : 'datefield',format : 'Y-m-d',readOnly : true}
							]
							});
					var rel_win = new Ext.Window({
							plain : true,
							layout : 'fit',
							frame : true,
							resizable : true,
							draggable : true,
							closable : true,
							closeAction : 'hide',
							modal : true, // 模态窗口
							shadow : true,
							loadMask : true,
							maximizable : true,
							collapsible : true,
							titleCollapse : true,
							border : true,
							buttonAlign : "center",
							width : 450,
							height : 450,
							title : '关联客户信息维护',
							items : [{
										layout :'form',
										autoScroll : true,
										items:[rel_form_1,rel_form_2,rel_form_3,rel_form_4,rel_form_5]
									}],
							buttons :[{
										text:'保存',
										id :'rel_save',
										handler:function(){
											var total_count =0;
											var record = null;
											var form_custId ='';
											var form_relaCustId='';
											var form_relationName='';
											var rel_cust_id = '';
											if (!rel_form_1.form.isValid()) {
											Ext.Msg.alert('提示', '输入不合法，请重新输入');
											return;
											}
											var type=rel_form_1.form.findField('combo_relationType').getValue();
											if(type == '1'){
												if (!rel_form_3.form.isValid()) {
													Ext.Msg.alert('提示', '输入不合法，请重新输入');
													return;
												}
	
											}else if(type == '2'){
												if (!rel_form_4.form.isValid()) {
													Ext.Msg.alert('提示', '输入不合法，请重新输入');
													return;
												}
											}else if(type == '3'){
												if (!rel_form_2.form.isValid()) {
													Ext.Msg.alert('提示', '输入不合法，请重新输入');
													return;
												}
											}
											if(Ext.getCmp('rel_cust_name').customerId!=''){
											rel_cust_id = Ext.getCmp('rel_cust_name').customerId;
											rel_form_1.getForm().findField('relaCustId').setValue(rel_cust_id);
											}
											form_custId = rel_form_1.getForm().findField('custId').getValue();
											form_relaCustId = rel_form_1.getForm().findField('relaCustId').getValue();
											form_relationName = rel_form_1.getForm().findField('relation_Name').getValue();
											total_count = listPanel.grid.getStore().getCount();
											for(var i =0 ;i<total_count;i++){
												record = listPanel.grid.getStore().getAt(i);
												if(record.data.custId==form_custId && record.data.relaCustId==form_relaCustId 
													&& record.data.relationName==form_relationName){
														Ext.Msg.alert('提示','该关联已经存在，请检查！');
														return ;
												}
											}
											var mxtid =rel_form_1.form.findField('mxtid').getValue();
											var custId=rel_form_1.form.findField('custId').getValue();
											var custName=rel_form_1.form.findField('custName').getValue();
											var relaCustId=rel_form_1.form.findField('relaCustId').getValue();
											var relaCustName=rel_form_1.form.findField('relaCustName').getValue();
											var relationType=rel_form_1.form.findField('combo_relationType').getValue();
											var relationName=rel_form_1.form.findField('relation_Name').getValue();
											 if(rel_form_2.hidden == false){
											  var direct = rel_form_2.form.findField('direct_0').getValue();
											  var amt = rel_form_2.form.findField('amt').getValue();
											  var kgRate = rel_form_2.form.findField('kgRate').getValue();
											  var sxAmt = rel_form_2.form.findField('sxAmt').getValue();
											  var yxBal = rel_form_2.form.findField('yxBal').getValue();
											  }
											   if(rel_form_3.hidden == false){
											  var maxEdu = rel_form_3.form.findField('maxEdu').getValue();
											  var position = rel_form_3.form.findField('position').getValue();
											  var birthday = rel_form_3.form.findField('birthday').getValue();
											  var amt = rel_form_3.form.findField('amt').getValue();
											  var kgRate = rel_form_3.form.findField('kgRate').getValue();
											  }
											  if(rel_form_4.hidden==false){
											  var contactPhone = rel_form_4.form.findField('contactPhone').getValue();
											  var telphone = rel_form_4.form.findField('telphone').getValue();
											  var workkUnit = rel_form_4.form.findField('workkUnit').getValue();
											  var position = rel_form_4.form.findField('position').getValue();
											  }
											  var demo=rel_form_5.form.findField('demo').getValue();
											  var createUser=rel_form_5.form.findField('createUser').getValue();
											  var createOrg=rel_form_5.form.findField('createOrg').getValue();
											  var createDate=rel_form_5.form.findField('createDate').getValue();
											  if(sxAmt==undefined||sxAmt==''){
											  		sxAmt =0;
											  }
											   if(yxBal==undefined||yxBal==''){
											  		yxBal =0;
											  }
											    if(amt==undefined||amt==''){
											  		amt =0;
											  }
											    if(kgRate==undefined||kgRate==''){
											  		kgRate =0;
											  }
											Ext.Ajax.request({
											 url : basepath + '/CustRelInfo!saveCustInfo.json',
											 method : 'POST',
											 params :{
											 		'mxtid' :mxtid,
											 		'custId':custId,
											 		'custName':custName,
											 		'relaCustId':relaCustId,
											 		'relaCustName':relaCustName,
											 		'relationType':relationType,
											 		'relationName':relationName,
											 		
											 		'amt':amt,
											 		'contactPhone':contactPhone,
											 		'telphone':telphone,
											 		'workkUnit':workkUnit,
											 		'position':position,
											 		'kgRate':kgRate,
											 		'sxAmt':sxAmt,
											 		'yxBal':yxBal,
											 		'maxEdu':maxEdu,
											 		'birthday':birthday,
											 		'demo':demo,
											 		'createUser':createUser,
											 		'createOrg':createOrg,
											 		'createDate':createDate
											 },
											 failure : function(form, action) {
													Ext.MessageBox.alert('新增操作', '新增失败！');
													op_flag ='';
													Ext.getCmp('relation_Name').getStore().load();
													listPanel.grid.getStore().reload();
											},
											success : function(response) {
													Ext.MessageBox.alert('新增操作', '新增成功！');
													Ext.getCmp('relation_Name').store.load();
													listPanel.grid.getStore().reload();
													op_flag ='';
													rel_win.hide();
												}
											});
											}
											
								},{
								text :'修改',
								id :'rel_update',
								handler:function(){
									var total_count =0;
										var record = null;
										var form_custId ='';
										var form_relaCustId='';
										var form_relationName='';
										var form_mxtid='';
									if (!rel_form_1.form.isValid()) {
										Ext.Msg.alert('提示', '输入不合法，请重新输入');
										return;
										}
										var type=rel_form_1.form.findField('combo_relationType').getValue();
										if(type == '1'){
											if (!rel_form_3.form.isValid()) {
												Ext.Msg.alert('提示', '输入不合法，请重新输入');
												return;
											}

										}else if(type == '2'){
											if (!rel_form_4.form.isValid()) {
												Ext.Msg.alert('提示', '输入不合法，请重新输入');
												return;
											}
										}else if(type == '3'){
											if (!rel_form_2.form.isValid()) {
												Ext.Msg.alert('提示', '输入不合法，请重新输入');
												return;
											}
										}
										form_mxtid = rel_form_1.getForm().findField('mxtid').getValue();
										form_custId = rel_form_1.getForm().findField('custId').getValue();
										form_relaCustId = rel_form_1.getForm().findField('relaCustId').getValue();
										form_relationName = rel_form_1.getForm().findField('relation_Name').getValue();
										total_count = listPanel.grid.getStore().getCount();
										for(var i =0 ;i<total_count;i++){
											record = listPanel.grid.getStore().getAt(i);
											if(record.data.mxtid !=form_mxtid&&record.data.custId==form_custId && record.data.relaCustId==form_relaCustId 
												&& record.data.relationName==form_relationName){
													Ext.Msg.alert('提示','该关联已经存在，请检查！');
													return ;
											}
										}
										var mxtid =rel_form_1.form.findField('mxtid').getValue();
											var custId=rel_form_1.form.findField('custId').getValue();
											var custName=rel_form_1.form.findField('custName').getValue();
											var relaCustId=rel_form_1.form.findField('relaCustId').getValue();
											var relaCustName=rel_form_1.form.findField('relaCustName').getValue();
											var relationType=rel_form_1.form.findField('combo_relationType').getValue();
											var relationName=rel_form_1.form.findField('relation_Name').getValue();
											 if(rel_form_2.hidden == false){
											  var direct = rel_form_2.form.findField('direct_0').getValue();
											  var amt = rel_form_2.form.findField('amt').getValue();
											  var kgRate = rel_form_2.form.findField('kgRate').getValue();
											  var sxAmt = rel_form_2.form.findField('sxAmt').getValue();
											  var yxBal = rel_form_2.form.findField('yxBal').getValue();
											  }
											   if(rel_form_3.hidden == false){
											  var maxEdu = rel_form_3.form.findField('maxEdu').getValue();
											  var position = rel_form_3.form.findField('position').getValue();
											  var birthday = rel_form_3.form.findField('birthday').getValue();
											  var amt = rel_form_3.form.findField('amt').getValue();
											  var kgRate = rel_form_3.form.findField('kgRate').getValue();
											  }
											  if(rel_form_4.hidden==false){
											  var contactPhone = rel_form_4.form.findField('contactPhone').getValue();
											  var telphone = rel_form_4.form.findField('telphone').getValue();
											  var workkUnit = rel_form_4.form.findField('workkUnit').getValue();
											  var position = rel_form_4.form.findField('position').getValue();
											  }
											  var demo=rel_form_5.form.findField('demo').getValue();
											  var createUser=rel_form_5.form.findField('createUser').getValue();
											  var createOrg=rel_form_5.form.findField('createOrg').getValue();
											  var createDate=rel_form_5.form.findField('createDate').getValue();
											   if(sxAmt==undefined||sxAmt==''){
											  		sxAmt =0;
											  }
											   if(yxBal==undefined||yxBal==''){
											  		yxBal =0;
											  }
											    if(amt==undefined||amt==''){
											  		amt =0;
											  }
											    if(kgRate==undefined||kgRate==''){
											  		kgRate =0;
											  }
											Ext.Ajax.request({
											 url : basepath + '/CustRelInfo!saveCustInfo.json',
											 method : 'POST',
											 params :{
											 		'mxtid' :mxtid,
											 		'custId':custId,
											 		'custName':custName,
											 		'relaCustId':relaCustId,
											 		'relaCustName':relaCustName,
											 		'relationType':relationType,
											 		'relationName':relationName,
											 		'direct':direct,
											 		'amt':amt,
											 		'contactPhone':contactPhone,
											 		'telphone':telphone,
											 		'workkUnit':workkUnit,
											 		'position':position,
											 		'kgRate':kgRate,
											 		'sxAmt':sxAmt,
											 		'yxBal':yxBal,
											 		'maxEdu':maxEdu,
											 		'birthday':birthday,
											 		'demo':demo,
											 		'createUser':createUser,
											 		'createOrg':createOrg,
											 		'createDate':createDate
											 },
											 failure : function(form, action) {
													Ext.MessageBox.alert('修改操作', '修改失败！');
													op_flag ='';
													Ext.getCmp('relation_Name').getStore().load();
													listPanel.grid.getStore().reload();
											},
											success : function(response) {
													Ext.MessageBox.alert('修改操作', '修改成功！');
													Ext.getCmp('relation_Name').store.load();
													listPanel.grid.getStore().reload();
													op_flag ='';
													rel_win.hide();
												}
											});
								}
							},{
								text:'重置',
								id :'rel_reset',
								handler:function(){
								rel_reset();
								}
							},{
								text:'取消',
								handler:function(){
								op_flag ='';
								rel_form_1.form.reset();
								rel_form_2.form.reset();
								rel_form_3.form.reset();
								rel_form_4.form.reset();
								rel_form_5.form.reset();
								rel_win.hide();
								}
							}				]
						});
						
		
		// 最终展现的panel
		var listPanel = new Mis.Ext.CrudPanel( {
			id : "listPanel",
			title : "关联客户信息",
			stUrl : basepath + '/CustRelInfo!indexPage.json?cust_id='+cust_id,
//			addUrl : basepath + '/ElecMgrInfo.json',
//			updateUrl : basepath + '/ElecMgrInfo.json',
//			deUrl : basepath + '/CustRelInfo!batchDestroy.json',
			primary : "mxtid",
			checkbox : true,
			//定义查询条件Form的高度
			seFormHeight : 30,
			//定义增删详情页面弹出窗口高度
			winHeight : 0,
			//宽度
			winWidth : 0,
			//设置分页每页显示条数，若不设置则不出现分页栏
			pagesize : 20,
//			afterSeOneFun : function(b) {
//				Ext.getCmp('createDate').setValue(new Date(b.createDate.time));
//			},
			detail : function() {

			var records = listPanel.grid.getSelectionModel().getSelections();
					var recordsLen = records.length;
					if (recordsLen !=1) {
						Ext.Msg.alert("系统提示信息","请选择其中一条记录进行查看！");
						return ;
					} else {
						rel_win.show();
						Ext.getCmp('relation_Name').store.load({
								params : {
									"condition" : records[0].data.relationType
								},callback:function(){
									Ext.getCmp('relation_Name').setValue(records[0].data.relationName);
								}
							});
			rel_form_5.form.findField('createUser').setVisible(true);
			rel_form_5.form.findField('createOrg').setVisible(true);
			rel_form_5.form.findField('createDate').setVisible(true);
						var type = records[0].json.relationType;
						rel_form_1.getForm().loadRecord(records[0]);
						rel_form_2.getForm().loadRecord(records[0]);
						rel_form_3.getForm().loadRecord(records[0]);
						rel_form_4.getForm().loadRecord(records[0]);
						rel_form_5.getForm().loadRecord(records[0]);
						if(type == '1'){
							rel_form_2.hide();
							rel_form_2.form.reset();
							rel_form_3.show();
							rel_form_4.hide();
							rel_form_4.form.reset();
							if(records[0].json.birthday!=null&&records[0].json.birthday!=''){
								Ext.getCmp('relcustinfo_birthday').setValue(new Date(records[0].json.birthday.time));
							}
						}else if(type == '2'){
							rel_form_2.hide();
							rel_form_2.form.reset();
							rel_form_3.hide();
							rel_form_3.form.reset();
							rel_form_4.show();
						}else if(type == '3'){
							rel_form_2.show();
							rel_form_3.hide();
							rel_form_3.form.reset();
							rel_form_4.hide();
							rel_form_4.form.reset();
						}
						Ext.getCmp('relcustinfo_createDate').setValue(new Date(records[0].json.createDate.time));
						Ext.getCmp('rel_save').hide();
						Ext.getCmp('rel_update').hide();
						Ext.getCmp('rel_reset').hide();
					}
				},
			//查询列表字段定义，有header属性则在页面显示
			//如果需要做映射需要定义store , mappingkey ,mappingvalue 三个属性
			gclms : [ 
			    {name : 'mxtid'	},  
			    {name : 'custId',header : '客户编号'},  
				{name : 'custName',header : '客户名称'},
				{name : 'relaCustId',header : '关联客户号'},
				{name : 'relaCustName',header : '关联客户名称'},
				{name : 'relationType',header :'关联类型',type :'mapping',store : relTypeStore, mappingkey : 'key',mappingvalue : 'value'},
				{name : 'relationName',header : '关联关系',type :'mapping',store : relationNameStore, mappingkey : 'code',mappingvalue : 'value'},
				{name : 'demo',header : '关系描述'},
				{name : 'createUser'},
				{name : 'createOrg'},
				{name : 'createDate',header : '创建日期',type :'date'},
				{name : 'direct'},
				{name : 'amt'},
				{name : 'birthday',type :'date'},
				{name : 'kgRate'},
				{name : 'contactPhone'},
				{name : 'maxEdu'},
				{name : 'position'},
				{name : 'sxAmt'},
				{name : 'telphone'},
				{name : 'workkUnit'},
				{name : 'yxBal'}
			],
					// 新增、修改、详情的form的字段
			buts :[{
					text :'新增',
					iconCls :'addIconCss',
					handler:function(){
						op_flag ='form_add';
						rel_reset();
						rel_win.show();
						rel_form_5.form.findField('createUser').setVisible(false);
						rel_form_5.form.findField('createOrg').setVisible(false);
						rel_form_5.form.findField('createDate').setVisible(false);
						Ext.getCmp('rel_save').show();
						Ext.getCmp('rel_update').hide();
						Ext.getCmp('rel_reset').show();
					}
				},'-',{
					text :'修改',
					iconCls:'editIconCss',
					handler:function(){
								op_flag ='form_update';
						debugger;		
						var records = listPanel.grid.getSelectionModel().getSelections();
									var recordsLen = records.length;
									if (recordsLen !=1) {
										Ext.Msg.alert("系统提示信息","请选择其中一条记录进行修改！");
										return ;
									} else {
										rel_reset();
										rel_win.show();
										Ext.getCmp('relation_Name').store.load({
											params : {
												"condition" : records[0].data.relationType
											},callback:function(){
												Ext.getCmp('relation_Name').setValue(records[0].data.relationName);
											}
										});
										rel_form_1.getForm().loadRecord(records[0]);
										rel_form_2.getForm().loadRecord(records[0]);
										rel_form_3.getForm().loadRecord(records[0]);
										rel_form_4.getForm().loadRecord(records[0]);
										rel_form_5.getForm().loadRecord(records[0]);
										rel_form_5.form.findField('createUser').setVisible(true);
										rel_form_5.form.findField('createOrg').setVisible(true);
										rel_form_5.form.findField('createDate').setVisible(true);
										var type = records[0].json.relationType;
										if(type == '1'){
											rel_form_2.hide();
											rel_form_2.form.reset();
											rel_form_3.show();
											rel_form_4.hide();
											rel_form_4.form.reset();
											if(records[0].json.birthday!=null&&records[0].json.birthday!=''){
												Ext.getCmp('relcustinfo_birthday').setValue(new Date(records[0].json.birthday.time));
											}
										}else if(type == '2'){
											rel_form_2.hide();
											rel_form_2.form.reset();
											rel_form_3.hide();
											rel_form_3.form.reset();
											rel_form_4.show();
										}else if(type == '3'){
											rel_form_2.show();
											rel_form_3.hide();
											rel_form_3.form.reset();
											rel_form_4.hide();
											rel_form_4.form.reset();
										}
										Ext.getCmp('relcustinfo_createDate').setValue(new Date(records[0].json.createDate.time));
										Ext.getCmp('rel_save').hide();
										Ext.getCmp('rel_update').show();
										Ext.getCmp('rel_reset').hide();
									}
					}
				},'-',{
					text:'删除',
					iconCls : 'deleteIconCss',
					handler:function(){debugger;
					var records = listPanel.grid.selModel.getSelections();// 得到被选择的行的数组
			var selectLength = records.length;// 得到行数组的长度
			var idStr = '';
			for ( var i = 0; i < selectLength; i++) {
				selectRe = records[i];
				tempId = selectRe.get(listPanel.primary);
				idStr += tempId;
				if (i != selectLength - 1)
					idStr += ',';
			};
			Ext.Ajax.request({
						url : basepath + '/CustRelInfo!batchDestroy.json',
						params : {
							idStr : idStr
						},
						waitMsg : '正在保存数据,请等待...', // 显示读盘的动画效果，执行完成后效果消失
						method : 'POST',
						scope : this,
						success : function() {
							Ext.Msg.alert('提示', '操作成功');
							debugger;
							listPanel.loadCurrData();
						}
					});
				}
					
				},'-',{
					text:'查看',
					iconCls : 'detailIconCss',
					handler:function(){
							var records = listPanel.grid.getSelectionModel().getSelections();
									var recordsLen = records.length;
									if (recordsLen !=1) {
										Ext.Msg.alert("系统提示信息","请选择其中一条记录进行查看！");
										return ;
									} else {
										rel_win.show();
										Ext.getCmp('relation_Name').store.load({
												params : {
													"condition" : records[0].data.relationType
												},callback:function(){
													Ext.getCmp('relation_Name').setValue(records[0].data.relationName);
												}
											});
							rel_form_5.form.findField('createUser').setVisible(true);
							rel_form_5.form.findField('createOrg').setVisible(true);
							rel_form_5.form.findField('createDate').setVisible(true);
										var type = records[0].json.relationType;
										rel_form_1.getForm().loadRecord(records[0]);
										rel_form_2.getForm().loadRecord(records[0]);
										rel_form_3.getForm().loadRecord(records[0]);
										rel_form_4.getForm().loadRecord(records[0]);
										rel_form_5.getForm().loadRecord(records[0]);
										if(type == '1'){
											rel_form_2.hide();
											rel_form_2.form.reset();
											rel_form_3.show();
											rel_form_4.hide();
											rel_form_4.form.reset();
											if(records[0].json.birthday!=null&&records[0].json.birthday!=''){
												Ext.getCmp('relcustinfo_birthday').setValue(new Date(records[0].json.birthday.time));
											}
										}else if(type == '2'){
											rel_form_2.hide();
											rel_form_2.form.reset();
											rel_form_3.hide();
											rel_form_3.form.reset();
											rel_form_4.show();
										}else if(type == '3'){
											rel_form_2.show();
											rel_form_3.hide();
											rel_form_3.form.reset();
											rel_form_4.hide();
											rel_form_4.form.reset();
										}
										Ext.getCmp('relcustinfo_createDate').setValue(new Date(records[0].json.createDate.time));
										Ext.getCmp('rel_save').hide();
										Ext.getCmp('rel_update').hide();
										Ext.getCmp('rel_reset').hide();
									}
					}
				},'-',{
		        	id:'_detailShow',
		        	text:'关系图查看',
		        	iconCls : 'detailIconCss',
		        	handler:function(){
		    	 			
		    	 			Ext.Ajax.request({
//		    	 				url:basepath+'/graphoption/11616/show.json',
		    	 				url:basepath+'/querycustrelachart.json?customerId='+cust_id,
		    	 				method:'GET',
		    	 				success:function(a,b,c){
//		    	 					store.removeAll();
//		    	 					custChoose.setVisible(false);
//		    	 					infoPanel.getForm().reset();
		    	 					GraphWindow.show();
		    	 					gco.showData(Ext.decode(a.responseText),false);
		    	 				},failure:function(a,b,c,d){
		    	 				}
		    	 			});
		    	 		
		        	}
		        }]
		});
		
	//	listPanel.grid.purgeListeners();
		var rel_reset = function(){
			rel_form_1.form.reset();
			rel_form_2.form.reset();
			rel_form_3.form.reset();
			rel_form_4.form.reset();
			rel_form_5.form.reset();
			if(op_flag =='form_add'){
				rel_form_1.getForm().findField('custId').setValue(cust_id);
				rel_form_1.getForm().findField('custName').setValue(cust_name);
				rel_form_1.getForm().findField('relaCustId').setReadOnly(false);
				rel_form_1.getForm().findField('relaCustName').setReadOnly(false);
			}
			if(op_flag =='form_update'){
			var rd = listPanel.grid.getSelectionModel().getSelected();
			if(rd!=null){
			if(rd.data.custId == cust_id){
				
			}else if(rd.data.relaCustId ==cust_id){
				rel_form_1.getForm().findField('relaCustId').setReadOnly(true);
				rel_form_1.getForm().findField('relaCustName').setReadOnly(true);
			}
			}
			}
		};
		// 布局模型
		var viewport = new Ext.Panel( {
			renderTo:'viewport_center',
			height:document.body.scrollHeight-30,
			layout : 'fit',
			autoScroll:true,
			items : [ listPanel ]
		});

		/***********************************关系图相关************************************/
		var containPanel =new  Ext.Panel({
			height:500,
			width:1000,
			region:'center',
			tbar : new Ext.Toolbar({
				items:[]
			})
		});
		var mainGraphPanel = new Ext.Panel({
			layout : 'border',
			items : [containPanel]
		 });
		var GraphWindow = new Ext.Window({
			id:'graphWindow',
			layout : 'fit',
			draggable : true,//是否可以拖动
			closable : true,// 是否可关闭
			modal : true,
			closeAction : 'hide',
			maximized:true,
			titleCollapse : true,
			buttonAlign : 'center',
			border : false,
			animCollapse : true,
			items:[mainGraphPanel]
		});
		var gco = new GraphCrmObject(containPanel,false,false,false);
		gco.initDataFunc = function(o){
			if(!o.graphData){
				return;
			}
			var v0 = gco.graph.insertVertex(o.graph.getDefaultParent(),cust_id, cust_name, 0, 0, 40, 30);
			for(var v in gco.graphData.json.data){
				if(gco.graphData.json.data[v].CUST_ID){
					var vid;
					var vname;
					var arrow;
					if(gco.graphData.json.data[v].RELA_CUST_ID==cust_id){//取非当前客户的另一名客户做节点
						vid = gco.graphData.json.data[v].CUST_ID;
						vname = gco.graphData.json.data[v].CUST_NAME;
						arrow = 0;  //方向为tB指向v0
					}else{
						vid = gco.graphData.json.data[v].RELA_CUST_ID;
						vname = gco.graphData.json.data[v].RELA_CUST_NAME;
						arrow = 1;  //方向为v0指向tB
					}
					var tB = gco.graph.insertVertex(o.graph.getDefaultParent(),vid, vname,0,0,40,30);
					if(arrow == 1){
						var te = gco.graph.insertEdge(o.graph.getDefaultParent(),gco.graphData.json.data[v].MXTID,gco.graphData.json.data[v].F_VALUE,v0,tB);
					}else{
						var te = gco.graph.insertEdge(o.graph.getDefaultParent(),gco.graphData.json.data[v].MXTID,gco.graphData.json.data[v].F_VALUE,tB,v0);
					}
					te._typecode = gco.graphData.json.data[v].RELATION_TYPE;
				}
			}
		};
		
		gco.pushToolButton({
			id:'hid',
			text:'树形布局',
			handler:function(){
				var layout = new mxHierarchicalLayout(gco.graph);
				layout.execute(gco.graph.getDefaultParent());
			}					
		}); 
		gco.pushToolButton({
			id:'on',
			text:'有机布局',
			handler:function(){
				var organic = new mxFastOrganicLayout(gco.graph);
				organic.forceConstant = 120;
				organic.execute(gco.graph.getDefaultParent());
			}					
		}); 
		gco.pushToolButton({
			id:'cir',
			text:'圆形布局',
			handler:function(){
				var circleLayout = new mxCircleLayout(gco.graph);
				circleLayout.radius = 100;
				circleLayout.execute(gco.graph.getDefaultParent());
			}
		});
		gco.pushToolButton({
			id:'cir1',
			text:'星形布局',
			handler:function(){
			var starLayout = new mxStarLayout(gco.graph,200);
			
//			starLayout.radius = 60;
			starLayout.execute(gco.graph.getDefaultParent());
			}
		});
		gco.pushToolButton({
			text:'放大',
			handler: function(){
				gco.graph.zoomIn();
			}
		});
		gco.pushToolButton({
			text:'缩小',
			handler: function(){
				gco.graph.zoomOut();
			}
		});
		gco.pushToolButton({
			text:'实际大小',
			handler: function(){
				gco.graph.zoomActual();
			}
		});
		gco.pushToolButton({
			text:'合适大小',
			handler: function(){
				gco.graph.fit();
			}
		});
		gco.pushToolButton({
			text:'打印',
			handler: function(){
				var scale = mxUtils.getScaleForPageCount(1, gco.graph);
				var preview = new mxPrintPreview(gco.graph, scale);
				preview.open();
			}
		});
		
		gco.setVertexStyle=function(o){
			var style = gco.graph.getStylesheet().getDefaultVertexStyle();
			style[mxConstants.STYLE_SHAPE] = mxConstants.SHAPE_ELLIPSE;
			style[mxConstants.STYLE_PERIMETER] = mxPerimeter.EllipsePerimeter;
			style[mxConstants.STYLE_GRADIENTCOLOR] = 'white';
		};
		
		/***********************************************************************/
	});