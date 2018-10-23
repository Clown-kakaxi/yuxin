/**
 * 短信管理的客户查询tab
 */
var approvestore = new Ext.data.Store({
		restful:true,   
		autoLoad :true,
		sortInfo : {
            field:'key',
            direction:'ASC'
        },
		proxy : new Ext.data.HttpProxy({
				url :basepath+'/lookup.json?name=APPROVEL_STATUS'
		}),
		reader : new Ext.data.JsonReader({
			root : 'JSON'
		}, [ 'key', 'value' ])
	});

//客户类型
	var boxstore = new Ext.data.Store({
		restful:true,   
		autoLoad :true,
		sortInfo : {
            field:'key',
            direction:'ASC'
        },
		proxy : new Ext.data.HttpProxy({
				url :basepath+'/lookup.json?name=XD000080'
		}),
		reader : new Ext.data.JsonReader({
			root : 'JSON'
		}, [ 'key', 'value' ])
	});
	//证件类型数据集
	var certstore = new Ext.data.Store({  
		restful:true,   
		autoLoad :true,
		sortInfo : {
            field:'key',
            direction:'ASC'
        },
		proxy : new Ext.data.HttpProxy({
				url :basepath+'/lookup.json?name=XD000040'
		}),
		reader : new Ext.data.JsonReader({
			root : 'JSON'
		}, [ 'key', 'value' ])
	});
	
	//短信模板
	var modelstore = new Ext.data.Store({
		sortInfo: {
		    field: 'key',
		    direction: 'ASC' 
		},
			restful:true,   
			autoLoad :true,
			proxy : new Ext.data.HttpProxy({
				url :basepath + '/ocrmFMmSysMsg!searchMktDic.json',
				success:function(response){
//					alert(response.responseText);
				}
			}),
			reader : new Ext.data.JsonReader({
				root : 'data'
			}, [ {name: 'key', mapping: 'KEY'},
			      {name: 'value', mapping: 'VALUE'} ])
	});
	//查询条件Form定义
	var qForm = new Ext.form.FormPanel({
		id:'qForm',
		labelWidth : 90, // 标签宽度
		frame : true, //是否渲染表单面板背景色
		labelAlign : 'middle', // 标签对齐方式
		buttonAlign : 'center',
		layout : 'column',
		border : false,
		items : [{
			columnWidth : .25,
			layout : 'form',
			labelWidth : 70, // 标签宽度
			defaultType : 'textfield',
			border : false,
			items : [
				{fieldLabel : '客户号',name : 'CUST_ID',xtype : 'textfield',labelStyle: 'text-align:right;',anchor : '90%'},
				{fieldLabel : '客户类型',hiddenName : 'CUST_TYP',name:'CUST_TYP',xtype : 'combo',labelStyle: 'text-align:right;',triggerAction : 'all',
					store : boxstore,displayField : 'value',valueField : 'key',mode : 'local',forceSelection : true,typeAhead : true,emptyText:'请选择',resizable : true,anchor : '90%'}
				]
		}, {
			columnWidth : .25,
			layout : 'form',
			labelWidth: 90, // 标签宽度
			defaultType : 'textfield',
			border : false,
			items : [
				{fieldLabel : '客户名称',name : 'CUST_ZH_NAME',xtype : 'textfield',labelStyle: 'text-align:right;',anchor : '90%'},
				{fieldLabel : '账号',name : 'ACC_NO',xtype : 'textfield',labelStyle: 'text-align:right;',anchor : '90%',hidden:true}
				]
		}, {
			columnWidth : .25,
			layout : 'form',
			labelWidth : 70, // 标签宽度
			defaultType : 'textfield',
			border : false,
			items : [
				{fieldLabel : '证件类型',hiddenName : 'CERT_TYPE',name:'CERT_TYPE',xtype : 'combo',labelStyle: 'text-align:right;',triggerAction : 'all',
					store : certstore,displayField : 'value',valueField : 'key',mode : 'local',forceSelection : true,typeAhead : true,emptyText:'请选择',resizable : true,anchor : '90%'}
			]
		}, {
			columnWidth : .25,
			layout : 'form',
			labelWidth : 70, // 标签宽度
			defaultType : 'textfield',
			border : false,
			items : [
				{fieldLabel : '证件号码',name : 'CERT_NUM',id:'CERT_NUM',xtype : 'textfield',labelStyle: 'text-align:right;',anchor : '90%'}
			]
		}],
		buttons : [{
			text : '查询',
			handler : function() {
				var parameters = qForm.getForm().getValues(false);
				store.baseParams = {
					'condition':Ext.util.JSON.encode(parameters)
				};
				store.load({      
					params : {
                       start : 0,
                       limit : bbar.pageSize
                    }
				});     
		
		   }
		},{
			text : '重置',
		     handler : function() {
		    	 qForm.getForm().reset();
			}
		}]
	});
	
	/**
	 * 客户查询结果数据存储
	 */
	var store = new Ext.data.Store({
		restful:true,	
        proxy : new Ext.data.HttpProxy({url:basepath+'/customerBaseInformation.json'}),
        reader: new Ext.data.JsonReader({
        	totalProperty : 'json.count',
        	root:'json.data'
        }, [
			{name: 'custId',mapping :'CUST_ID'},
			{name: 'custZhName',mapping :'CUST_ZH_NAME'},
			{name: 'CERT_TYPE_ORA'},
			{name:'CUST_STAT_ORA'},
			{name: 'CUST_TYP_ORA'},
			{name: 'CUST_LEV_ORA'},
			{name:'certType',mapping: 'CERT_TYPE'},
			{name:'custStat',mapping: 'CUST_STAT'},
			{name:'custTyp',mapping: 'CUST_TYP'},
			{name:'custLev',mapping: 'CUST_LEV'},
			{name: 'INSTITUTION_CODE'},
			{name: 'INSTITUTION_NAME'},
			{name: 'MGR_ID'},
			{name: 'MGR_NAME'},
			{name: 'custEnName',mapping :'CUST_EN_NAME'},//英文名
			{name: 'otherName',mapping :'OTHER_NAME'},//其他名
			{name: 'certNum',mapping :'CERT_NUM'},//证件号码
			{name: 'telephoneNum',mapping :'TELEPHONE_NUM'},//手机号码
			{name: 'officePhone',mapping :'OFFICE_PHONE'},//办公电话
			{name: 'linkPhone',mapping :'LINK_PHONE'},//联系电话
			{name: 'postNo',mapping :'POST_NO'},//邮编
			{name: 'addr',mapping :'aADDR'},//地址
			{name: 'linkUser',mapping :'LINK_USER'}//联系人
		])
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
        {header : '客户号',dataIndex : 'custId',sortable : true,width : 150},
	    {header : '客户名称',dataIndex : 'custZhName',width : 200,sortable : true},
	    {header : '证件类型',dataIndex : 'CERT_TYPE_ORA',width : 150,sortable : true},
	    {header : '证件号码',dataIndex : 'certNum',width : 150,sortable : true},
	    {header : '客户类型',dataIndex : 'custTyp',width : 100,sortable : true,hidden:true},
	    {header : '客户类型',dataIndex : 'CUST_TYP_ORA',width : 100,sortable : true},
	    {header : '客户级别',dataIndex : 'CUST_LEV_ORA',width : 100,sortable : true}
	]);

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
    pagesize_combo.on("select", function(comboBox) {
    	bbar.pageSize = parseInt(pagesize_combo.getValue()),
		store.load({
			params : {
				start : 0,
				limit : parseInt(pagesize_combo.getValue())
			}
		});
	});
	var bbar = new Ext.PagingToolbar({
        pageSize : number,
        store : store,
        displayInfo : true,
        displayMsg : '显示{0}条到{1}条,共{2}条',
        emptyMsg : "没有符合条件的记录",
        items : ['-', '&nbsp;&nbsp;', pagesize_combo]
    });
	var messageRemark = '';//短信内容
	//短信表单
	debugger;
	var panel = new Ext.form.FormPanel({
		frame:true,
		region:'center',
		labelWidth:80,
		items:[{xtype:'textfield', fieldLabel : '客户编号', name : 'custId', labelStyle : 'text-align:right;', anchor : '100%',allowBlank : false},
			{xtype:'textfield', fieldLabel : '客户名称', name : 'custName', labelStyle : 'text-align:right;', anchor : '100%',allowBlank : false},
			{xtype:'textfield', fieldLabel : '客户电话', name : 'cellNumber', labelStyle : 'text-align:right;', anchor : '100%',allowBlank : false},
			new Com.yucheng.crm.common.ProductManage({
				xtype : 'productChoose',
				fieldLabel : '产品名称',
				labelStyle : 'text-align:right;',
				name : 'prodName',
				hiddenName : 'prodId',
				singleSelect : true,
				allowBlank : false,
				blankText : '此项为必填项，请检查！',
				anchor : '100%',
				//选择产品之后查询该产品类别是否有模板   有择查询模板供选择，否则不显示模板选择
					callback:function(checkedNodes){
						modelstore.load({
							params : {
		 						'code':checkedNodes[0].data.productId
		 					},
							callback:function(){
								if(modelstore.getCount()!=0){
									panel.form.findField('modelId').setVisible(true);
					        	}else{
					        		Ext.Msg.alert("提示", "本产品没有相关的短信模板!");
					        		panel.form.findField('modelId').setVisible(false);
					        	}
								
							}
						});
					}
			}),
			{fieldLabel : '短信模板',hiddenName : 'modelId',name:'modelId',xtype : 'combo',labelStyle: 'text-align:right;',triggerAction : 'all',
	        	 store : modelstore,displayField : 'value',valueField : 'key',mode : 'local',forceSelection : true,typeAhead : true,emptyText:'请选择',
	        	 resizable : true,anchor : '100%',hidden:true,listeners:{
	        		 //根据选择的模板生成短信内容
	        		 'select':function(){
	        			 var model = panel.form.findField('modelId').getValue();
//	        			 Ext.Msg.wait('正在生成短信内容......','系统提示');
	        			 Ext.Ajax.request({
     						url : basepath + '/ocrmFMmSysMsg!getMessage.json',
     						method : 'POST',
     						params:{
				            	 'modelId':panel.form.findField('modelId').getValue(),
				            	 'custId':panel.form.findField('custId').getValue(),
				            	 'custName':panel.form.findField('custName').getValue(),
				            	 'prodId':panel.form.findField('prodId').getValue(),
				            	 'prodName':panel.form.findField('prodName').getValue()
				             },
     						waitMsg : '正在保存数据,请等待...', // 显示读盘的动画效果，执行完成后效果消失
     						success : function(response) {
     							messageRemark = response.responseText;
     							panel.form.findField('messageRemark').setValue(messageRemark);
//     							Ext.Msg.alert('提示', '操作成功');
     						},
     						failure : function(response) {
     							var resultArray = Ext.util.JSON.decode(response.status);
     						       if(resultArray == 403) {
     						           Ext.Msg.alert('提示', response.responseText);
     						  } else{
     							Ext.Msg.alert('提示', '操作失败,失败原因:' + response.responseText);
     						}
     						}
     					});
						}
	        	 }},
	        {xtype:'textarea', fieldLabel : '短信内容', name : 'messageRemark', labelStyle : 'text-align:right;', anchor : '100%',allowBlank : false,disabled:true}
		]
	});
	//短信窗口
	var dxWin = new Ext.Window({
		title:'短信营销',
		height:300,
		width:450,
		buttonAlign : 'center',
		draggable : true,// 是否可以拖动
		closable : true,// 是否可关闭
		modal : true,
		autoScroll : true,
		closeAction : 'hide',
		border : false,
		items : [ panel ],
		buttons : [ {text:'确定',
			handler:function(){
				if(!panel.getForm().isValid()){
					 Ext.MessageBox.alert('提示','输入有误,请检查输入项');
			         return false;
				}else{
					 Ext.Msg.wait('正在保存，请稍后......','系统提示');
						Ext.Ajax.request({
				             url : basepath + '/ocrmFMmSysMsg!saveData.json',
				             method : 'POST',
				             waitMsg : '正在保存数据,请等待...', // 显示读盘的动画效果，执行完成后效果消失
				             form : panel.getForm().id,
				             params:{
				            	 'messageRemark':panel.form.findField('messageRemark').getValue()
				             },
				             success : function() {
				                 Ext.Msg.alert('提示', '操作成功');
				                 dxWin.hide();
				             },
				             failure : function(response) {
				                 Ext.Msg.alert('提示', '操作失败,失败原因:' + response.responseText);
				             }
				         });
				}
			}},{
			text : '关 闭',
			handler : function() {
				dxWin.hide();
			}
		} ]
	});
	var tbar = new Ext.Toolbar({
		items : [{text:'短信营销',
			     iconCls:'detailIconCss',
			     handler:function(){
			    	 var checkedNodes = grid.getSelectionModel().selections.items;
						if (checkedNodes.length == 0) {
							Ext.Msg.alert('提示', '未选择任何客户');
							return;
						} else if (checkedNodes.length > 1) {
							Ext.Msg.alert('提示', '您只能选择一个客户');
							return;
						}
						var data = checkedNodes[0].data;
						if (data.telephoneNum == '' ) {// 没有手机信息
						Ext.Msg.alert('提示', '本客户没有手机信息！');
						return;
					} else {
						dxWin.show();
						panel.getForm().reset();
						panel.getForm().findField('custId').setValue(data.custId);
						panel.getForm().findField('custName').setValue(data.custZhName);
						panel.getForm().findField('cellNumber').setValue(data.telephoneNum);
					}
			 }},{
					text : '客户视图',
					iconCls :'custGroupMemIconCss',
					handler : function() {
			        var checkedNodes = grid.getSelectionModel().selections.items;
						if(checkedNodes.length==0)
							{
								Ext.Msg.alert('提示', '未选择任何客户');
								return ;
							}
						else if(checkedNodes.length>1)
						{
							Ext.Msg.alert('提示', '您只能选中一个客户进行查看');
							return ;
						}
//						var viewUrl = basepath+'/contents/pages/common/CommonCustomerView.jsp?resId='+__resId+
//						  '&custId='+checkedNodes[0].data.custId+'&custName='+checkedNodes[0].data.custZhName+
//						  '&custTyp='+checkedNodes[0].data.custTyp;
//						/**URL包含参数，打开新页签填出客户视图*/
//						parent.booter.addTag(Ext.id(),viewUrl,'客户视图');
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
				}]
	});
	
	// 表格实例
	var grid = new Ext.grid.GridPanel({
				id:'viewgrid',
				frame : true,
				autoScroll : true,
				region : 'center', // 和VIEWPORT布局模型对应，充当center区域布局
				store : store, // 数据存储
				stripeRows : true, // 斑马线
				cm : cm, // 列模型
				sm : sm, // 复选框
				tbar : [tbar], // 表格工具栏
				bbar:bbar,
				viewConfig:{
					   forceFit:false,
					   autoScroll:true
					},
				loadMask : {
					msg : '正在加载表格数据,请稍等...'
				}
			});


	
	var custPanel = new Ext.Panel({
		title: "客户查询", 
	    layout:'fit',
	    items:[{
				layout : 'border',
				items: [{   
					region: 'north',
				    height: 100,
				    hidden:false,
				    margins: '0 0 0 0',
					items:[qForm]
			     },{   
			    	region:'center',
				    layout:'fit',
				    margins: '0 0 0 0',
				    items : [grid]
			    }] 
	    }]
	});
	
	store.load({      
		params : {
           start : 0,
           limit : bbar.pageSize
        }
	});  