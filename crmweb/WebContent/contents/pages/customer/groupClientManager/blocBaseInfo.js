Ext.onReady(function(){
	//*****************************************
	
	//*********************
	
	//*****************************************
	 var khStore = new Ext.data.ArrayStore({
	        fields:['myId','displayText'],
	        data:[['停用','停用'],['启用','启用']]
	    });
	 
	 var jtlxStore = new Ext.data.ArrayStore({
	        fields:['myId','displayText'],
	        data:[['全行性','全行性'],['区域性','区域性']]
	    });
	 
	 //集团状态
		var JTKHZTStore = new Ext.data.Store({  
			restful:true,   
			autoLoad :true,
			proxy : new Ext.data.HttpProxy({
					url :basepath+'/lookup.json?name=GROUP_STS'
				}),
				reader : new Ext.data.JsonReader({
					root : 'JSON'
				}, [ 'key', 'value' ])
			});
		
		 //集团类型
		var JTKHLXStore = new Ext.data.Store({  
			restful:true,   
			autoLoad :true,
			proxy : new Ext.data.HttpProxy({
					url :basepath+'/lookup.json?name=GROUP_TYP'
				}),
				reader : new Ext.data.JsonReader({
					root : 'JSON'
				}, [ 'key', 'value' ])
			});
		
	
	var record = Ext.data.Record.create([
     {name: 'id'},
     {name: 'groupNo'},  
     {name: 'groupName'},
     {name: 'groupNumber1'},
     {name: 'groupNumber2'},
     {name : 'groupRootCustId'},
     {name: 'groupHostOrgNo'},
     {name : 'groupHostOrgNoName'},
     {name: 'updateDate'},
     {name: 'updateUserId'},
     {name:'groupRootCustName1'},
     {name: 'groupRootAdress'},
     {name: 'groupMemo'},
     {name: 'groupType'},
     {name: 'GROUP_TYPE_ORA'},
     {name: 'zhengshi_no'},
     {name: 'group_number'},
     {name: 'groupRootAdress'},
     {name: 'groupStatus'},
     {name: 'GROUP_STATUS_ORA'},
     {name: 'createUserId'},
     {name: 'createUserName'},
     {name: 'createUserOrgId'},
     {name: 'createDate'}
	  ]);
	
	
	var hid = true;
	if(__roles=='系统管理员*'){
		hid = false;
	}
	
	var blocBaseInfo = new Ext.FormPanel({//集团基本信息表单
		labelWidth : 120,
		region : 'north',
		height : 100,
		frame : true,
		split:true,
		labelAlign:'right',
//		collapsible : true,
		autoScroll : true,
		//title : '集团基本信息',
		buttonAlign:"center" ,
		reader: new Ext.data.JsonReader({
            root:'json.data'
            },record),
		items : [{ 
			   xtype:'fieldset',
	           title: '集团基本信息', 
				layout : 'column',
				items : [
						{
						columnWidth : .33,
						layout : 'form',
						items : [
								{
									xtype:'textfield',
									editable:true,
									name:'groupNo',
									readOnly : true,
									triggerAction:'all',
									anchor:'90%',
								//	lazyRender:true,
									fieldLabel:'集团编号'
								},								
								{
									xtype:'hidden',
									editable:true,
									name:'id',
									triggerAction:'all',
									anchor:'90%',
									fieldLabel:'隐藏ID'
								},{
									xtype:'textfield',
									editable:true,
									name:'groupName',
									triggerAction:'all',
									anchor:'90%',
									fieldLabel:'集团名称'
								},{
									xtype : 'textfield',
									fieldLabel : '集团母公司ID',
									hidden : true,
									name : 'groupRootCustId',
									readOnly : true,
									anchor : '90%'
								},											
								{
									xtype : 'textfield',
									fieldLabel : '集团母公司名称',
									name : 'groupRootCustName1',
									readOnly : true,
									anchor : '90%'
								}										
								]
					}, 
					{
						columnWidth : .33,
						layout : 'form',
						items : [
							   {
									xtype : 'textfield',
									fieldLabel : '集团母公司注册地址',
									readOnly : true,
									name : 'groupRootAdress',
									anchor : '90%'
								},
								{
									name:"groupHostOrgNo",
									id:"groupHostOrgNo",
									hidden:true,
									xtype:'textfield'
								},
								{
									xtype : 'textfield',
									fieldLabel : '集团成员总数',
									name : 'group_number',
									readOnly : true,
									anchor : '90%'
								},											
								{
									xtype : 'textfield',
									fieldLabel : '正式成员数',
									name : 'zhengshi_no',
									readOnly : true,
									anchor : '90%'
								}
								]
					},
					{
						columnWidth : .33,
						layout : 'form',
						items : [
								{
						store: JTKHLXStore,
						xtype : 'combo',
						name : 'groupType',
						hiddenName : 'groupType',
						fieldLabel : '集团类型',
						valueField:'key',
						displayField:'value',
						mode : 'local',
						typeAhead: true,
						forceSelection : true,
						triggerAction: 'all',
						emptyText:'请选择',
						selectOnFocus:true,
						width : '100',
						anchor : '90%'
						                 }	
								]
					}, {
						columnWidth : .33,
						layout : 'form',
						items : [
							{
							store: JTKHZTStore,
							xtype : 'combo',
							name : 'groupStatus',
							hiddenName : 'groupStatus',
							fieldLabel : '集团状态',
							valueField:'key',
							hidden:true,
							displayField:'value',
							mode : 'local',
							typeAhead: true,
							forceSelection : true,
							triggerAction: 'all',
							emptyText:'请选择',
							selectOnFocus:true,
							width : '100',
							anchor : '90%'
			                 		},											
									{
										xtype : 'textfield',
										fieldLabel : '对外担保客户数',
										name : 'groupNumber2',
										readOnly : true,
										anchor : '90%'
									}

								]
							},{
						columnWidth : .99,
						layout : 'form',
						items : [
								{
									xtype : 'textarea',
									fieldLabel : '集团公司简介',
									//width : 200,
									name : 'groupMemo',
									anchor : '90%'
								},{
									xtype : 'textfield',
									fieldLabel : '创建人ID',
									name : 'createUserId',
									hidden :true,
									readOnly : true,
									anchor : '90%'
								},{
									xtype : 'textfield',
									fieldLabel : '创建人姓名',
									name : 'createUserName',
									hidden :true,
									readOnly : true,
									anchor : '90%'
								},{
									xtype : 'textfield',
									fieldLabel : '创建人机构号',
									name : 'createUserOrgId',
									hidden :true,
									readOnly : true,
									anchor : '90%'
								},{
									xtype : 'textfield',
									fieldLabel : '创建时间',
									name : 'createDate',
									hidden :true,
									readOnly : true,
									anchor : '90%'
								}										
								]
					}
					],
					buttonAlign : 'center',
					buttons :[{
						id :'updat',
						text :'修改', 
						disabled:true,
//						hidden :hid,
						handler : function (){
							var ss = Ext.getCmp("groupHostOrgNo").getValue();
//							alert(ss);
							Ext.Ajax.request({

								url : basepath + '/GroupInfoAction.json',
								method : 'POST',
//								params:blocBaseInfo.getForm().getFieldValues(),
								form : blocBaseInfo.getForm().id,
								waitMsg : '正在保存数据,请等待...', // 显示读盘的动画效果，执行完成后效果消失
								success :checkResult,
								failure :checkResult
							});
							
							
							function checkResult(response) {
								var resultArray = Ext.util.JSON.decode(response.status);
								var resultError = response.responseText;
								if ((resultArray == 200 ||resultArray == 201)&&resultError=='') {
									Ext.Msg.alert('提示', '操作成功');
								} else {
									Ext.Msg.alert('提示', '操作失败,失败原因:' + resultError);
								}
							};
						}
					}]
		}]
	});
	
	if(__units=="00001"){

	    Ext.getCmp('updat').setDisabled(false);
//	    alert(__units);
	};
	
	var store = new Ext.data.Store({
		reader : new Ext.data.JsonReader({
											root : 'rows',
											totalProperty : 'num'
										 }, 
										 record
		)
	});
	var memberData= {
			TOTALCOUNT:1,
			rows:[{"id":"123",
				"groupNo":"G005511",
				"groupName":"擎宇数据有限公司",
				"groupNumber1":"1876543210",
				"groupNumber2":"132",
				"groupRootCustId":"123",
				"groupHostOrgNo":"G005510",
				"groupHostOrgNoName":"擎宇科技有限公司",
				"updateDate":"123",
				"updateUserId":"123",
				"groupRootCustName1":"擎宇科技有限公司",
				"groupRootAdress":"中国",
				"groupMemo":"北部数据",
				"groupType":"全行性",
				"GROUP_TYPE_ORA":"",
				"zhengshi_no":"254",
				"group_number":"309",
				"groupRootAdress":"北京市昌平区",
				"groupStatus":"123",
				"GROUP_STATUS_ORA":"",
				"createUserId":"123",
				"createUserName":"123",
				"createUserOrgId":"123",
				"createDate":"123"}		
			]
		};
	store.loadData(memberData);
//	debugger;
	var groupNo= parent.document.getElementById("groupNo").value;
	blocBaseInfo.getForm().loadRecord(store.getAt(0)
			);

	var viewBlocBaseInfo = new Ext.Viewport({
		layout:'fit',
		items:[blocBaseInfo]
		
	});

});