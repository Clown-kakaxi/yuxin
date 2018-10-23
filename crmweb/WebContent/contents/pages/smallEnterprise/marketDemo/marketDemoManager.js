Ext.onReady(function() {
    Ext.QuickTips.init(); 
    /**********************************判断是否为编辑状态的flag*****************************************/
    var editFlag = 0;
    /**********************************************************************************************/
    /************************************************************************/
	var certTypeStore = new Ext.data.Store({//证件类型store
		restful:true,   
		autoLoad :true,
		proxy : new Ext.data.HttpProxy({
			url :basepath+'/lookup.json?name=COM_CRET_TYPE'
			}),
			reader : new Ext.data.JsonReader({
				root : 'JSON'
			}, [ 'key', 'value' ])
	});
	var addCertInfoPanel = new Ext.FormPanel({//证件信息新增PANEL
		frame : true,
	    region : 'center',
	    width:980,
      	height : 390,
      	autoScroll : true,
		split : true,
		items : [
		         {
		        	 items :[ {  
		        		 layout:'column',
		        		 items:[
		        		        {
		        		        	columnWidth:.9,
		        		        	layout: 'form',
		        		        	items: [
		        		        	        {  xtype:'textfield',fieldLabel:'id',name:'id',labelStyle:'text-align:right;',anchor:'90%',hidden:true},
		        		        	        {  xtype:'textfield',fieldLabel:'客户号',name:'custId',labelStyle:'text-align:right;',anchor:'90%',hidden:true},
										    {  xtype:'textfield',fieldLabel:'姓名',name:'custId',labelStyle:'text-align:right;',anchor:'90%'},
									     	 {  xtype:'textfield',fieldLabel:'称谓',name:'custId',labelStyle:'text-align:right;',anchor:'90%'},
										 {  xtype:'textfield',fieldLabel:'职位',name:'custId',labelStyle:'text-align:right;',anchor:'90%'},
									 {  xtype:'textfield',fieldLabel:'办公电话',name:'custId',labelStyle:'text-align:right;',anchor:'90%'},
									 {  xtype:'textfield',fieldLabel:'分机号码',name:'custId',labelStyle:'text-align:right;',anchor:'90%'},
									 {  xtype:'textfield',fieldLabel:'手机号码',name:'custId',labelStyle:'text-align:right;',anchor:'90%'},
									 {  xtype:'textfield',fieldLabel:'家庭电话',name:'custId',labelStyle:'text-align:right;',anchor:'90%'},
									 {  xtype:'textfield',fieldLabel:'传真',name:'custId',labelStyle:'text-align:right;',anchor:'90%'},
									 {  xtype:'textfield',fieldLabel:'电子邮件',name:'custId',labelStyle:'text-align:right;',anchor:'90%'},
									 {  xtype:'textfield',fieldLabel:'国籍',name:'custId',labelStyle:'text-align:right;',anchor:'90%'},
										 {  xtype:'textfield',fieldLabel:'证件类型',name:'custId',labelStyle:'text-align:right;',anchor:'90%'},
										 {  xtype:'textfield',fieldLabel:'证件号码',name:'custId',labelStyle:'text-align:right;',anchor:'90%'},
									    {  xtype:'datefield',fieldLabel:'出生日期',name : 'issueDate',format:'Y-m-d',labelStyle:'text-align:right;',anchor:'90%'}
									   
										
									

		        		        	        ]
		        		        }]
		        	 }]
		         }]
		});
	var certInfoWind = new Ext.Window({
		closeAction:'hide',
		closable:true,
		constrain:true,
		modal:true,
		maximizable:true,
		height:450,
		width:500,
		buttonAlign:'center',
		items : [addCertInfoPanel],
		buttons : [
		           {
		        	   text : '保存',
		        	   handler:function(){
		        	   if (!addCertInfoPanel.getForm().isValid()) {
		        		   Ext.Msg.alert("系统提示信息", "输入有误或存在漏输项，请重新输入!");
		        		   return false;
		        	   } 
//		        	   var addIss = Ext.getCmp('issueDate').getValue();
//		        	   var addl = Ext.getCmp('lostDate').getValue();
//		        	   if(addIss >= addl){
//		        		   Ext.Msg.alert("系统提示信息", "证件登记日期不能或等于大于证件到期日，请重新输入!");
//		        		   return false;
//		        	   }
		        	   Ext.getCmp('custId').setValue(custid);
		        	   Ext.Ajax.request( {
		        		   url : basepath + '/ComCertInfo-action.json',
		        		   method : 'POST',
		        		   params : addCertInfoPanel.getForm().getFieldValues(),
		        		   success : checkResult,
		        		   failure: checkResult
		        	   });		        	   
		        	   
		        	   function checkResult(response) {
		        		   var resultArray = Ext.util.JSON.decode(response.status);
		        		   var resultError = response.responseText;
		        		   if ((resultArray == 200 ||resultArray == 201)&&resultError=='') {
		        			   Ext.Msg.alert('提示', '保存成功');
		        			   certInfoStore.reload();
		        			   certInfoWind.hide();
		        			   addCertInfoPanel.getForm().reset();
		        		   } else {
		        			   if(resultArray == 403){
		        				   Ext.Msg.alert('提示', response.responseText);
		        			   }else{
		        				   Ext.Msg.alert('提示', '操作失败,失败原因:' + resultError);
		        			   }
		        		   }
		        	   }	   
		           }
		           },'-',{
		        	   text:'取消',
		        	   handler:function(){
		        	   		certInfoWind.hide();
		        	   		addCertInfoPanel.getForm().reset();
		           }
		           }
		           ]
	});
	
	var sm = new Ext.grid.CheckboxSelectionModel();//复选框
	var rownum = new Ext.grid.RowNumberer({
		header : 'No.',
		width : 28
	});
	var certInfogrid = new Ext.grid.ColumnModel([//gridtable中的列定义
	                                             sm,rownum,
	                                             {header : 'ID',dataIndex : 'id',width : 100,sortable : true,hidden : true},
	                                             //{header : '联系人',dataIndex : 'custId',width : 120,sortable : true},
	                                             {header : '客户号',dataIndex : 'f1',width : 150,sortable : true},
	                                             {header : '客户名称',dataIndex : 'f2',width : 150,sortable : true},
	                                             {header : '性别',dataIndex : 'f3',width : 150,sortable : true},
	                                             {header : '年龄段',dataIndex : 'f4',width : 150,sortable : true},
	                                             {header : '联系电话',dataIndex : 'f5',width : 150 ,sortable : true},
	                                             {header : '投资偏好',dataIndex : 'f6',width : 145,sortable : true},
	                                             {header : '主办客户经理',dataIndex : 'f7',width : 145,sortable : true},
	                                             {header : '主办机构',dataIndex : 'f8',width : 145,sortable : true},
	                                             {header : 'AUM余额',dataIndex : 'f9',width : 145,sortable : true,align:'right',renderer: money('0,000.00')},
	                                             {header : '活期存款余额',dataIndex : 'f10',width : 145,sortable : true,align:'right',renderer: money('0,000.00')},
	                                             {header : '定期存款余额',dataIndex : 'f11',width : 145,sortable : true,align:'right',renderer: money('0,000.00')},
	                                             {header : '理财产品余额',dataIndex : 'f12',width : 145,sortable : true,align:'right',renderer: money('0,000.00')}
//	                                             {header : '来源系统',dataIndex : 'asAnnId',width : 145,sortable : true},
//	                                             {header : '出生日期',dataIndex : 'asAnnId',width : 145,sortable : true}
	                                             ]);
//	var certInfoRecord = new Ext.data.Record.create([
//	                                                 {name : 'id',mapping : 'ID'},
//	                                                 {name : 'custId',mapping : 'CUST_ID'},
//	                                                 {name : 'cretType',mapping : 'CRET_TYPE'},
//	                                                 {name : 'cretTypeOra',mapping : 'CRET_TYPE_ORA'},
//	                                                 {name : 'cretNo',mapping : 'CRET_NO'},
//	                                                 {name : 'cardOnName',mapping : 'CARD_ON_NAME'},
//	                                                 {name : 'issueDate',mapping : 'ISSUE_DATE'},
//	                                                 {name : 'lostDate',mapping : 'LOST_DATE'},
//	                                                 {name : 'tackInstn',mapping : 'TACK_INSTN'},
//	                                                 {name : 'asAnnId',mapping : 'AS_ANN_ID'}
//	                                                 ]);
	var certInfoRecord = new Ext.data.Record.create([
	                                                 {name : 'f1'},
	                                                 {name : 'f2'},
	                                                 {name : 'f3'},
	                                                 {name : 'f4'},
	                                                 {name : 'f5'},
	                                                 {name : 'f6'},
	                                                 {name : 'f7'},
	                                                 {name : 'f8'},
	                                                 {name : 'f9'},
	                                                 {name : 'f10'},
	                                                 {name : 'f11'},
	                                                 {name : 'f12'}
	                                                 ]);
	var certInfoReader = new Ext.data.JsonReader({//读取json数据的panel
//		totalProperty:'json.count',
//		root:'json.data'
	            root:'rows',
	            totalProperty: 'num'
	},certInfoRecord);
 	var certInfoStore = new Ext.data.Store(
 			{
 				proxy:new Ext.data.HttpProxy({
 					url:basepath+'/certInfoQuery-Action.json',
 					failure : function(response){
 					var resultArray = Ext.util.JSON.decode(response.status);
 					if(resultArray == 403) {
 						Ext.Msg.alert('提示', response.responseText);
 					}
 				},
 				method:'GET'
 				}),
 				reader:certInfoReader
 			}
 		);
// 	certInfoStore.load({
// 		params : {
// 			'custId' : custid
// 	}
// 	});
 		
 	var tb_memberData2= {
	num:1,
	rows:[
	{"f1":"101","f2":"张平","f3":"男","f4":"30-40岁","f5":"13029010920","f6":"股票基金","f7":"客户经理1","f8":"北京分行","f9":"300000","f10":"100000","f11":"100000","f12":"100000"},
	{"f1":"102","f2":"王帆","f3":"女","f4":"30-40岁","f5":"13623712617","f6":"证券","f7":"客户经理3","f8":"上海分行","f9":"500000","f10":"300000","f11":"100000","f12":"100000"},
	{"f1":"103","f2":"赵志航","f3":"男","f4":"40-50岁","f5":"18912901921","f6":"期货","f7":"客户经理3","f8":"上海分行","f9":"600000","f10":"400000","f11":"100000","f12":"100000"},
	{"f1":"104","f2":"李瓒","f3":"女","f4":"20-30岁","f5":"13721881281","f6":"黄金","f7":"客户经理4","f8":"天津分行","f9":"500000","f10":"300000","f11":"100000","f12":"100000"},
	{"f1":"105","f2":"李丽","f3":"女","f4":"30-40岁","f5":"13029010920","f6":"证券","f7":"客户经理2","f8":"广州分行","f9":"800000","f10":"200000","f11":"200000","f12":"100000"},
	{"f1":"106","f2":"张元","f3":"男","f4":"30-40岁","f5":"13229191112","f6":"理财","f7":"客户经理1","f8":"北京分行","f9":"300000","f10":"100000","f11":"100000","f12":"100000"},
	{"f1":"107","f2":"刘一凡","f3":"男","f4":"30-40岁","f5":"18239238923","f6":"股票基金","f7":"客户经理1","f8":"北京分行","f9":"300000","f10":"100000","f11":"100000","f12":"100000"}

	]
};
 		
certInfoStore.loadData(tb_memberData2);
 	
	var spagesize_combo = new Ext.form.ComboBox({	// 每页显示条数下拉选择框
		name : 'pagesize',
		triggerAction : 'all',
		mode : 'local',
		store : new Ext.data.ArrayStore({
			fields : [ 'value', 'text' ],
			data : [ [ 10, '10条/页' ], [ 20, '20条/页' ], [ 50, '50条/页' ],
			         [ 100, '100条/页' ], [ 250, '250条/页' ],
			         [ 500, '500条/页' ] ]
		}),
		valueField : 'value',
		displayField : 'text',
		value : '20',
		forceSelection : true,
		width : 85
	});
	
	spagesize_combo.on("select", function(comboBox) {	// 改变每页显示条数reload数据
		sbbar.pageSize = parseInt(spagesize_combo.getValue()),
		certInfoStore.reload({
			params : {
			start : 0,
			limit : parseInt(spagesize_combo.getValue())
		}
		}); 
	});
	var sbbar = new Ext.PagingToolbar({	// 分页工具栏
		pageSize : parseInt(spagesize_combo.getValue()),
		store : certInfoStore,
		displayInfo : true,
		displayMsg : '显示{0}条到{1}条,共{2}条',
		emptyMsg : "没有符合条件的记录",
		items : [ '-', '&nbsp;&nbsp;', spagesize_combo ]
	});
	var certBaseInfoGrid =  new Ext.grid.GridPanel({//主要证件信息列表数据grid
 		frame : true,
 		id : 'certBaseInfoGrid',
		width : 990,
		height:350,
		frame : true,
 		store : certInfoStore,
 		loadMask : true,
 		cm : certInfogrid,
 		sm : sm,
 		bbar : sbbar,
 		tbar : [
 		        {
 		        	text : '创建营销活动',
 		           iconCls :'addIconCss',
 		        	handler : function(){
    				addActivityForm.form.reset();
    				addActivityProdForm.form.reset();
    				addActivityCustForm.form.reset();
    				addActivityForm.form.findField('createUser').setValue(__userId);
    				addActivityForm.form.findField('test').setValue(__userName);
    				addActivityForm.form.findField('createDate').setValue(new Date());
    				addActivityForm.form.findField('mktActiStat').setValue(1);
    				addActivityForm.form.findField('mktActiName').setValue('小企业扶持贷款推广');
    				addActivityForm.form.findField('mktActiType').setValue('推广活动');
    				addActivityForm.form.findField('mktActiMode').setValue('宣传');
    				addActivityForm.form.findField('mktActiTeam').setValue('小企业贷款组');
    				addActivityForm.form.findField('mktActiCost').setValue('1000');
    				addActivityForm.form.findField('mktActiAddr').setValue('南京市建邺区应天西路所叶路20号');
    				addActivityForm.form.findField('mktActiCont').setValue('宣传小企业的扶持贷款政策，吸引贷款');
    				addActivityForm.form.findField('actiCustDesc').setValue('该工业园区的小企业');
    				addActivityForm.form.findField('actiOperDesc').setValue('本行支行客户经理');
    				addActivityForm.form.findField('actiProdDesc').setValue('小企业扶持到款');
    				addActivityForm.form.findField('mktActiAim').setValue('推广');
    				addActivityForm.form.findField('actiRemark').setValue('无');
    						 				
    				addActivityWindow.show();
 		        }}
 		        ],
 		        loadMask : {
					msg : '正在加载表格数据,请稍等...'
				} 
		});	

    var sjlxStore = new Ext.data.Store({  
        restful:true,   
        autoLoad :true,
        proxy : new Ext.data.HttpProxy({
                url :basepath+'/lookup.json?name=EVENT_TYP'
            }),
            reader : new Ext.data.JsonReader({
                root : 'JSON'
            }, [ 'key', 'value' ])
        });
    /************************************************************************/
	var panel2 = new Ext.FormPanel({ 
		frame:true,
		bodyStyle:'padding:5px 5px 0',
	
		width: 990,
	    height:150,
		buttonAlign : 'center',
		items: [{
		    autoHeight:true,
			items :[{ layout:'column',
				buttonAlign : 'center',
					 items:[{
						 columnWidth:.50,
						 layout: 'form',
						 items: [{
			                    fieldLabel : '年龄段',
			                    name:'EVENT_TYP',
			                	store : new Ext.data.ArrayStore({
									fields : [ 'key', 'value' ],
									data : [ [ 1, '20-30岁' ], [ 2, '30-40岁' ], 
											[ 4, '40-50岁' ], 
											[ 6, '50-60岁' ], [ 7, '60-70岁' ]]
								}),
	                             xtype : 'combo',
	                             //editable:false,
	                             allowBlank : false,
	                             labelStyle: 'text-align:right;',
	                             valueField:'key',
	                             displayField:'value',
	                             mode : 'local',
	                             typeAhead: true,
	                             forceSelection: true,
	                             triggerAction: 'all',
	                             emptyText:'请选择',
	                             selectOnFocus:true,
	                             width : '100',
	                             anchor : '90%'
			                }, {
							 xtype:'textfield',
							 fieldLabel: 'AUM余额起',
							 name: 'WHRY',
							 labelStyle: 'text-align:right;',
							 anchor:'90%'
						 },{
							 xtype:'textfield',
							 fieldLabel: 'AUM余额止',
							 name: 'WHRY',
							 labelStyle: 'text-align:right;',
							 anchor:'90%'
						 }]
					 },{
						 columnWidth:.50,
						 layout: 'form',
						 items: [{
			                    fieldLabel : '投资偏好',
			                    name:'EVENT_TYP',
			                	store : new Ext.data.ArrayStore({
									fields : [ 'key', 'value' ],
									data : [ [ 1, '基金型' ], [ 2, '股票型' ], 
											[ 4, '汇率型' ], 
											[ 6, '股票型' ], [ 7, '商品型' ],
											[ 8, '期货型' ] , [ 9, '票据型' ],
											[ 10, '债券型' ], [ 11, '其他理财' ] ]
								}),
	                             xtype : 'combo',
	                             //editable:false,
	                             allowBlank : false,
	                             labelStyle: 'text-align:right;',
	                             valueField:'key',
	                             displayField:'value',
	                             mode : 'local',
	                             typeAhead: true,
	                             forceSelection: true,
	                             triggerAction: 'all',
	                             emptyText:'请选择',
	                             selectOnFocus:true,
	                             width : '100',
	                             anchor : '90%'
			                }]
					 },{
						 columnWidth:.50,
						 layout: 'form',
						 items: [new Com.yucheng.crm.common.OrgUserManage({ 
								xtype:'userchoose',
								fieldLabel : '主办客户经理', 
								id:'CUST_MANAGER',
								labelStyle: 'text-align:right;',
								name : 'CUST_MANAGER',
								hiddenName:'custMgrId',
								searchRoleType:('127,47'),  //指定查询角色属性 ,默认全部角色
								searchType:'SUBTREE',/* 允许空，默认辖内机构用户，指定查询机构范围属性  SUBTREE（子机构树）SUBORGS（直接子机构）PARENT（父机构）PARPATH （所有父、祖机构）ALLORG（所有机构）*/
								singleSelect:false,
								anchor : '90%'
								})]
					 },{
						 columnWidth:.50,
						 layout: 'form',
						 items: [new Com.yucheng.bcrm.common.OrgField({
								searchType:'SUBTREE',/*指定查询机构范围属性  SUBTREE（子机构树）SUBORGS（直接子机构）PARENT（父机构）PARPATH （所有父、祖机构）ALLORG（所有机构）*/
								fieldLabel : '主办机构',
								labelStyle : 'text-align:right;',
//								id : 'CUST_ORG', //放大镜组件ID，用于在重置清空时获取句柄
								name : 'CUST_ORG', 
								hiddenName: 'instncode',   //后台获取的参数名称
								anchor : '90%',
								checkBox:true //复选标志
							}),{
						     name:'AUM余额止',
						     xtype:'textfield',
						     id:'EVENT_ID',
						     hidden:true
						 }]
					 }
				]}
				]}],
		buttons : [ {
			text : '查询',
			id : 'btnReset',
			handler : function() {}
		}]
		});
	var addRoleWindow = new Ext.Window(
	{
        height : 550,
        width:1000,
    	title : '<span style="font-weight:normal">客户明细</span>',
		buttonAlign : 'center',
		draggable : true,//是否可以拖动
		closable : true,// 是否可关闭
		modal : true,
        autoScroll:true,
		closeAction : 'hide',
		// iconCls : 'page_addIcon',
		//maximizable: true,
		//maximized:true,
		collapsible : true,// 是否可收缩
		titleCollapse : true,
		border : false,
		animCollapse : true,
		pageY : 20,
		animateTarget : Ext.getBody(),
		constrain : true,
		items: [{   
			region: 'north',
			items:[panel2]
	     },{   
	    	region:'center',
		    items : [certBaseInfoGrid]
	    }] 
//		items : [panel2,panel2]
	});
	var qForm = new Ext.form.FormPanel({
//		renderTo:'viewport_center',
		title:'交叉营销',
		labelWidth : 90, // 标签宽度
		frame : true, //是否渲染表单面板背景色
		labelAlign : 'middle', // 标签对齐方式
		height:80,
		//bodyStyle : 'padding:3 5 0', // 表单元素和表单面板的边距
		buttonAlign : 'center',
		items : [{
			layout : 'column',
			border : false,
			items : [{
				columnWidth : .3,
				layout : 'form',
				labelWidth : 80, // 标签宽度
				defaultType : 'textfield',
				border : false,
				items : [{
					fieldLabel : '数据周期从',
					name : 'EVENT_NAME',
                     maxLength:100,
					 labelStyle: 'text-align:right;',
					xtype : 'datefield', // 设置为数字输入框类型
					anchor : '90%'
				},new Ext.ux.form.LovCombo({
					anchor : '90%',
			    	fieldLabel: '产品类型',
			    	id:'tablePkField',
			    	displayField:'value',
			    	valueField:'key',
			    	hideOnSelect:false,
			    	store : new Ext.data.ArrayStore({
						fields : [ 'key', 'value' ],
						data : [ [ 1, '月得赢' ], [ 2, '汇得利' ], 
						         [ 3, '双币型' ],
								[ 4, '汇率型' ], [ 5, '利得盈' ],
								[ 6, '股票型' ], [ 7, '商品型' ],
								[ 8, '期货型' ] , [ 9, '票据型' ],
								[ 10, '债券型' ], [ 11, '其他理财' ] ]
					}),
			    	triggerAction:'all',
			    	mode:'local',
			    	allowBlank:false,
			    	editable:true
			    }) ,new Com.yucheng.bcrm.common.OrgField({
					searchType:'SUBTREE',/*指定查询机构范围属性  SUBTREE（子机构树）SUBORGS（直接子机构）PARENT（父机构）PARPATH （所有父、祖机构）ALLORG（所有机构）*/
					fieldLabel : '机构',
					labelStyle : 'text-align:right;',
					id : 'CUST_ORG', //放大镜组件ID，用于在重置清空时获取句柄
					name : 'CUST_ORG', 
					hiddenName: 'instncode',   //后台获取的参数名称
					anchor : '90%',
					checkBox:true //复选标志
				})]
			}, {
				columnWidth : .3,
				layout : 'form',
				labelWidth : 80, // 标签宽度
				defaultType : 'textfield',
				border : false,
				items : [{
					fieldLabel : '数据周期到',
					name : 'EVENT_NAME',
                     maxLength:100,
					 labelStyle: 'text-align:right;',
					xtype : 'datefield', // 设置为数字输入框类型
					anchor : '90%'
				},new Ext.form.ComboBox({
					name : 'certType',
					id:'certType',
					fieldLabel : '数据挖掘模型',
					labelStyle: 'text-align:right;',
					triggerAction : 'all',
					store : new Ext.data.ArrayStore({
						fields : [ 'key', 'value' ],
						data : [ [ 1, '决策树分析：C5.0' ], [ 2, '聚类分析：K-means' ], 
						         [ 3, '支持向量机分析：svm' ],
								[ 4, '关联分析：Apriori' ], [ 5, '分类分析：Naive Bayes' ],
								[ 6, '分类分析：神经网络' ], [ 7, '分类分析：逻辑式回归' ],
								[ 8, '分类分析：异常值分析' ] ]
					}),
					displayField : 'value',
					valueField : 'key',
					mode : 'local',
					forceSelection : true,
					typeAhead : true,
					emptyText:'请选择',
					resizable : true,
					anchor : '90%'
				})]
			}]
				}],
		buttons : [{
					text : '确定',
						handler : function() {
			  Ext.getCmp('tabs').setActiveTab(1);
		}
				}]
	});
	 //复选框
	var sm = new Ext.grid.CheckboxSelectionModel();

	// 定义自动当前页行号
	var rownum = new Ext.grid.RowNumberer({
				header : 'No.',
				width : 28
			});

	// 定义列模型
	var cm = new Ext.grid.ColumnModel([rownum,sm, 
	    {header : 'EVENT_ID',dataIndex : 'temp0',sortable : true,hidden :true}, 
        {header : '产品1',dataIndex : 'temp1',sortable : true,width : 120}, 
        {header : '产品组合（产品1，产品2）',dataIndex : 'temp2',sortable : true,width : 150},
        {header : '产品1客户数',dataIndex : 'temp3',width : 200}, 
        {header : '产品组合客户数',dataIndex : 'temp4',width : 100}, 
        {header : '相关度',dataIndex : 'temp5',sortable : true,width : 150}
	]);

	var tempUserId = 100;
	//数据源
   var store = new Ext.data.ArrayStore({
		fields : ['temp0','temp1', 'temp2', 'temp3' , 'temp4' , 'temp5'],
		data : [ [ 1, '月得赢', '月得赢,汇得利', '111830', '67780' ,'0.6061' ],
		         [ 2, '汇得利', '汇得利,汇率型', '159640', '21091' ,'0.4051' ],
		         [ 3, '汇率型', '汇率型,双币型', '159640', '28912' ,'0.2718' ],
		         [ 4, '双币型', '双币型,汇得利', '40870', '1212' ,'0.3298' ],
		         [ 5, '汇率型', '汇率型,汇得利', '34565', '2121' ,'0.1781' ],
		         [ 6, '汇率型', '汇率型,债券型', '178340', '18912' ,'0.4676' ],
		         [ 7, '债券型', '债券型,汇得利', '103469', '9078' ,'0.1627' ],
		         [ 8, '债券型', '期货型,汇得利', '56781', '9078' ,'0.1212' ],
		         [ 9, '债券型', '汇得利,月得赢', '45671', '9078' ,'0.6251' ]]
	})
// 表格工具栏
	var tbar = new Ext.Toolbar({
				items : [{
                    text : '查看客户明细',
                    iconCls :'addIconCss',
                    handler : function() {
					addRoleWindow.show();
				}}]
			});

	// 每页显示条数下拉选择框
    var pagesize_combo = new Ext.form.ComboBox({
        name : 'pagesize',
        triggerAction : 'all',
        mode : 'local',
        store : new Ext.data.ArrayStore({
            fields : ['value', 'text'],
            data : [ [ 10, '10条/页' ], [ 20, '20条/页' ], [ 50, '50条/页' ],
						[ 100, '100条/页' ], [ 250, '250条/页' ],
						[ 500, '500条/页' ] ]
        }),
        valueField : 'value',
        displayField : 'text',
        value : '20',
        editable : false,
        width : 85
    });
    var number = parseInt(pagesize_combo.getValue());
    // 改变每页显示条数reload数据
    pagesize_combo.on("select", function(comboBox) {
        bbar.pageSize = parseInt(comboBox.getValue());
        number = parseInt(comboBox.getValue());
        store.reload({
            params : {
                start : 0,
                limit : parseInt(pagesize_combo.getValue())
            }
        });
    });
    
    // 分页工具栏
    var bbar = new Ext.PagingToolbar({
        pageSize : number,
        store : store,
        displayInfo : true,
        displayMsg : '显示{0}条到{1}条,共{2}条',
        //plugins : new Ext.ux.ProgressBarPager(), // 分页进度条
        emptyMsg : "没有符合条件的记录",
        items : ['-', '&nbsp;&nbsp;', pagesize_combo
                 ]
    });
	// 表格实例
	var grid = new Ext.grid.GridPanel({
		height:500,
		width:700,
		frame : true,
		autoScroll : true,
		region : 'west', // 和VIEWPORT布局模型对应，充当center区域布局
		store : store, // 数据存储
		stripeRows : true, // 斑马线
		cm : cm, // 列模型
		sm : sm, // 复选框
		tbar : tbar, // 表格工具栏
		bbar : bbar,
		viewConfig : {
		// 不产横向生滚动条, 各列自动扩展自动压缩, 适用于列数比较少的情况
		// forceFit : true
		},
		loadMask : {
			msg : '正在加载表格数据,请稍等...'
		}
	});
	var detailPanel = new Ext.Panel({
		title:'交叉营销分析',
		height:document.body.clientHeight-30,
		width:document.body.clientWidth-20,
		layout:'border',
		autoScroll:true,
//	    layout: 'fit',
        items: [grid, {
            region: 'center',
            items : [{html:'<div style="width:100%;height:100%;"><img src="demo.png"></img></div>'}]
        }]
	});
	//拖动IE时.翻页条自适应
    Ext.EventManager.onWindowResize(function(){
        grid.setHeight(document.body.scrollHeight-130);
        grid.setWidth(document.body.scrollWidth);
        grid.getView().refresh();
    });
	var tabs = new Ext.TabPanel({
		id:'tabs',
		xtype:"tabpanel",			   			   
		region:"center",
		activeTab: 0,
	    items: [qForm,detailPanel]
	});
	var viewport = new Ext.Viewport( {
		layout : 'fit',
		 title: "营销管理->交叉营销",
		items : [ tabs ]
	});
	// 布局模型
	function insert(){
	    if(!panel2.getForm().isValid())
	    { 
	        alert('请填写正确信息');
	        return false;
	    }
        if(editFlag == 1){
            var infoRecord = grid.getSelectionModel().getSelected();
            id=infoRecord.data.EVENT_ID;
            custid=oCustInfo.cust_id;
            Ext.getCmp('EVENT_ID').setValue(id);
            Ext.getCmp('custid').setValue(custid);
        }
//		Ext.Ajax.request({
//            url: basepath+'/customer-event.json',
//            method: 'POST',
////            form:'panel2',
//            params:panel2.getForm().getFieldValues(),
//            waitMsg : '正在保存数据,请等待...', // 显示读盘的动画效果，执行完成后效果消失
//            success : checkResult,
//            failure : checkResult
//        });
        Ext.Msg.alert('提示','保存成功');
        addRoleWindow.hide();

	};
	grid.on('rowdblclick', function(grid, rowIndex, event) {
        editFlag = 1;
		editInit();
	});
	function editInit(){
        var selectLength = grid.getSelectionModel()
        .getSelections().length;
        
        if(selectLength > 1){
            alert('请选择一条记录!');
        } else{
        var infoRecord = grid.getSelectionModel().getSelected();
        if(infoRecord == null||infoRecord == ''){
            Ext.Msg.alert('提示','请选择一行数据');
        }else{
            panel2.getForm().loadRecord(infoRecord);
            addRoleWindow.show();
        }}
    }
    function addInit(){
        resetForm(panel2);
        panel2.getForm().reset();
        Ext.getCmp('EVENT_ID').setValue('');
        Ext.getCmp('custid').setValue(oCustInfo.cust_id);

        addRoleWindow.show();  
    }
    function deleteInit(){
        /****************************************************************************************/
        var selectLength = grid.getSelectionModel()
        .getSelections().length;
        
        if(selectLength < 1){
            alert('请选择需要删除的记录!');
        } 
        
        else {
            if(confirm("确定删除吗?"))
            {
            var selectRe;
            var tempId;
            var idStr = '';
            for(var i = 0; i<selectLength;i++)
            {
                selectRe = grid.getSelectionModel()
                .getSelections()[i];
                tempId = selectRe.data.EVENT_ID;
                idStr += tempId;
                if( i != selectLength-1)
                    idStr += ',';
            }
//            Ext.Ajax.request({
//                url : basepath+'/customer-event/'
//                        +tempId+'.json?idStr='+idStr,
//                method : 'DELETE',        
//                waitMsg : '正在保存数据,请等待...', // 显示读盘的动画效果，执行完成后效果消失
//                success : checkResult,
//                failure : checkResult
//            });
            Ext.Msg.alert('提示','删除成功');
            };

    }
        /****************************************************************************************/

    }
    
    function checkResult(response) {
        var resultArray = Ext.util.JSON.decode(response.status);
        var resultError = response.responseText;
//        debugger;
        if ((resultArray == 200 ||resultArray == 201)&&resultError=='') {
            Ext.Msg.alert('提示', '操作成功');
            store.reload({
                        params : {
                            start : 0,
                            limit : bbar.pageSize
                        }
                    });
        } else {
            Ext.Msg.alert('提示', '操作失败,失败原因:' + resultError);
            store.reload({
                        params : {
                            start : 0,
                            limit : bbar.pageSize
                        }
                    });
        }
    }

    /**********************************************************/
    function resetForm(form){debugger;
        var resetObj;
        if(typeof form == 'string'){
            resetObj = Ext.getCmp(form);
        }else resetObj = form;
        
        if(resetObj == undefined){
            alert('debug:the formPanel has not been defined!');
            return false;
        }
        
        debugger;
        
        if(resetObj.getXType() != 'form'){
            alert('debug:the Obj is not a FormPanel!');
            return false;
        }
        
        Ext.each(resetObj.getForm().items.items,function(f){
            debugger;
            f.setValue('');
           // f.originalValue = '';
        });
    }
    /**********************************************************/
}); 