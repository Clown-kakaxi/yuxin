Ext.onReady(function() {
	var tmepMethod = 'add';
	var sOrgIdJson={'orgid':[]};
	
	var boxstore = new Ext.data.Store({  
		restful:true,   
		autoLoad :true,
		proxy : new Ext.data.HttpProxy({
				url :basepath+'/lookup.json?name=DEM0100005'
    
				
			}),
			reader : new Ext.data.JsonReader({
				root : 'JSON'
			}, [ 'key', 'value' ])
		});
	var boxstore1 = new Ext.data.ArrayStore({
		fields:['key','value'],
	    data:[['<10','<10万'],['>10,<30','10万~30万'],['>30,<50','30万~50万'],['>50','>50万']]
	});
	var statusStore = new Ext.data.Store({  
		sortInfo: {
	    field: 'key',
	    direction: 'ASC' // or 'DESC' (case sensitive for local sorting)
	},
		restful:true,   
		autoLoad :true,
		proxy : new Ext.data.HttpProxy({
				url :basepath+'/lookup.json?name=DEM0100006'
			}),
			reader : new Ext.data.JsonReader({
				root : 'JSON'
			}, [ 'key', 'value' ])
	});
	var boxstore8 = new Ext.data.Store({  
		sortInfo: {
	    field: 'key',
	    direction: 'ASC' // or 'DESC' (case sensitive for local sorting)
	},
		restful:true,   
		autoLoad :true,
		proxy : new Ext.data.HttpProxy({
				url :basepath+'/lookup.json?name=P_CUST_GRADE'
			}),
			reader : new Ext.data.JsonReader({
				root : 'JSON'
			}, [ 'key', 'value' ])
	});
	
	var ageStore = new Ext.data.ArrayStore({
		fields:['key','value'],
	    data:[['<18','18岁以下'],['>18,<25','18~25岁'],['>25,<35','25~35岁'],['>35,<45','35~45岁'],['>45','45岁以上']]
	});
	var unitTypeStore = new Ext.data.ArrayStore({
		fields:['key','value'],
	    data:[['1','国有企业'],['2','合资企业'],['3','集体企业'],['4','联营企业'],['5','股份制企业'],['6','合作制企业'],['7','私营企业'],['8','外商独资企业']]
	});
	
	
	
	
/******************************************************************************************************/
	   //新增客户群
	  var addCustomerGroup=new Ext.FormPanel({
		//layout:'fit',
	  	//title : '新增客户群',
		name:'addCustomerGroup',
	    id:'addCustomerGroup',
	  	labelAlign:'right',
		frame:true,
		border:false,
		style:'padding:10 10 10 10',
		items : [
				{
					layout:'column',
					items : [
							{
								columnWidth : .5,
								layout : 'form',
								items : [{ 
									xtype : 'textfield',
									fieldLabel : '客户群名称',
									labelStyle:{
										width:'120px'
									},	
									Width:'100',
									name : 'customerBaseName',
									anchor : '90%'
								}]
							}, 
							{
								columnWidth : .5,
								layout : 'form',
								items : [{ 
									xtype : 'textfield',
									disabled:true,
									fieldLabel : '客户群编号',
									labelStyle:{
										width:'120px'
									},	
									Width:'100',
									id : 'customerBaseNumber',
									name : 'customerBaseNumber',
									anchor : '90%'
								}]
							}]
				}
				,
				{
					layout:'form',
					items:{
						name : 'customerBaseDescribe',
						height :155,
						anchor:'80%',
						xtype:'textarea',
						fieldLabel : '客户群描述'
					}
				}
				]
	});


// 表格工具栏
	var cusGrouptbar = new Ext.Toolbar({
		items : [ {
					text : '新增客户群',
					iconCls:'addIconCss',
					//iconCls : 'page_findIcon',
					handler : function() {
						var win=new Ext.Window({
							layout : 'fit',
									closable : true,
									resizable : false,
									collapsible : false,
									height:300,
									width:700,
									draggable : true,
									closeAction : 'hide',
									
									//titleCollapse : false,
									modal : true, // 模态窗口 
									//下拉层的动画效果必须关闭,否则将出现Flash图标下拉动画过场异常的现象
									animCollapse : false,
//									maximizable : true,
									border : false,
									closable : true,
									animateTarget : Ext.getBody(),
									constrain : true,
									items : [addCustomerGroup],
									buttonAlign:'center',
									buttons:[{
										text:'保存',
										handler:function()
										{Ext.Ajax.request({
										    url:basepath+'/customer-base.json',
										    method: 'POST',
										    form:addCustomerGroup.getForm().id,
											success : function(response) {
				    							Ext.Msg.alert('提示', '成功');
				    						},
				    						failure : function(response) {
				    							Ext.Msg.alert('提示', response.responseText);
				    						},
												params : {
													'customerBaseNumber':Ext.getCmp('customerBaseNumber').getValue(),
													'operate':'add'
												}
										});
											win.hide();
										}
									},{
	          				  			text: '取消',
	           				  			handler:function(){
	            			 		    win.hide();
	            						}
	       			 				}]	
							});
						Ext.Ajax.request({
							url:basepath+'/querycustomerbasenumber.json',
						    method: 'GET',
						    success:function(response){
						    	var json=Ext.util.JSON.decode(response.responseText);
						    	 Ext.getCmp('customerBaseNumber').setValue(json.json.data);
					    }
						});
						win.show();
					}
				}
				]
	});
var cusGroupsm = new Ext.grid.CheckboxSelectionModel();

var cusGrouprownum = new Ext.grid.RowNumberer({
			header : 'No.',
			width : 28
		});


// 定义列模型
	var cusGroupcm = new Ext.grid.ColumnModel([cusGrouprownum,
	       {header : 'id', dataIndex : 'id',sortable : true,width : 150,hidden :true}, 
	       {header : '客户群编号', dataIndex : 'customerBaseNumber',sortable : true,width : 150 }, 
        {header : '客户群名称',dataIndex : 'customerBaseName',sortable : true,width : 150}, 
        {header : '客户群创建日期',dataIndex : 'customerBaseCreateDate',sortable : true,width : 150},
        {header : '客户群成员数', dataIndex : 'customerBaseMemberNum',renderer:function(value){
            if(value==''){
                return "0";
            }else{
                return value;
            }
        },sortable : true,width : 150 }, 
        {header : '客户群描述',dataIndex : 'customerBaseDescribe',sortable : true,width : 150}
			]);

	/**
	* 数据存储
	*/
	
	var simple1 = new Ext.FormPanel({
	      frame:true,
	      id:'queryGroup',
	      bodyStyle:'padding:5px 5px 0',
	      split:true,
			height:100,
	      items: [{
	         // xtype:'fieldset',
	         // title: '查询条件',
	        // autoHeight:true,
	      	
	          items :[{ 
	          		layout:'column',
	          		labelWidth : 100, // 标签宽度
	                   items:[{
	                       columnWidth:.25,
	                       layout: 'form',
	                       items: [{
	                           xtype:'textfield',
	                           fieldLabel: '客户群名称',
	                           labelStyle: 'text-align:right;',
	                           name: 'CUST_BASE_NAME',
	                           anchor:'95%'
	                       }]
	                   },{
	                       columnWidth:.25,
	                       layout: 'form',
	                       items: [{
	                           xtype:'datefield',
	                           fieldLabel: '客户群创建日期',
	                           labelStyle: 'text-align:right;',
	                           format:'Y-m-d', //日期格式化
	                           name: 'CUST_BASE_CREATE_DATE',
	                           anchor:'95%'
	                       }]
	                   }
	          ]}
	          ]}],
			buttonAlign:'center',
	      buttons: [{
	          text: '查询',
	          handler : function() {
	          	cusGroupstore.load(
	      			/*	{ params : {
	                            start : 0,
	                            limit : cusGroupbbar.pageSize }}*/ );
	        
			}
	      },{
	          text: '重置',
	          handler : function() {
	          	simple1.getForm().reset();   
			}
	      }]
	  });
	var cusGroupstore = new Ext.data.Store({
		restful:true,
		proxy : new Ext.data.HttpProxy({url:basepath+'/querycustomerbase.json'
      }),
      reader: new Ext.data.JsonReader({
      totalProperty : 'json.count',
      root:'json.data'
      }, [{name: 'id', mapping: 'ID'},{name: 'customerBaseNumber', mapping: 'CUST_BASE_NUMBER'},{name: 'customerBaseName', mapping: 'CUST_BASE_NAME'},{name: 'customerBaseCreateDate', mapping: 'CUST_BASE_CREATE_DATE'},{name: 'customerBaseMemberNum', mapping: 'MEMBERSNUM',type:'float'},{name: 'customerBaseDescribe', mapping: 'CUST_BASE_DESC'}])
	});
	cusGroupstore.on('beforeload', function() {
  	var conditionStr =  simple1.getForm().getValues(false);
      this.baseParams = {
              "condition":Ext.encode(conditionStr)
      };
   });

	
	var cusGrouppagesize_combo = new Ext.form.ComboBox({
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
	   value : '100',
	   editable : false,
	   width : 85
	});
	var cusGroupnumber = parseInt(cusGrouppagesize_combo.getValue());
	
	cusGrouppagesize_combo.on("select", function(comboBox) {
		cusGroupbbar.pageSize = parseInt(cusGrouppagesize_combo.getValue()),
		 alert(cusGroupbbar.pageSize);
		cusGroupstore.load({
					params : {
						start : 0,
						limit : cusGroupparseInt(pagesize_combo.getValue())
					}
				});
	});
	var cusGroupbbar = new Ext.PagingToolbar({
	  pageSize : cusGroupnumber,
	  store : cusGroupstore,
	  displayInfo : true,
	  displayMsg : '显示{0}条到{1}条,共{2}条',
	  //plugins : new Ext.ux.ProgressBarPager(), // 分页进度条
	  emptyMsg : "没有符合条件的记录",
	  items : ['-', '&nbsp;&nbsp;', cusGrouppagesize_combo
	           ]
	});
	  var cusGroupGrid = new Ext.grid.GridPanel({
	  	height :285,
		width : 886,
	  	//title:'客户群列表',
	  	tbar:cusGrouptbar,
	      store: cusGroupstore,
			cm : cusGroupcm,
			bbar : cusGroupbbar,
			selModel:new Ext.grid.RowSelectionModel({
					singleSelect:true
					})
	     
	  });
	var cusGroupQueryWind = new Ext.Window({
	 	height:'450',
	 	width:'900',
	 	closable:true,
	 	closeAction:'hide',
	 	frame:true,
	 	maximizable:true,
		items:[
		    simple1,
			cusGroupGrid
		],
	
	 	buttonAlign:'center',
	 	buttons:[
	 	{
	 		text:'加入该群',
	 		handler:function()
	 		{
	 		//	batchAdd();
	 		var record = grid.getSelectionModel().getSelected();
			if(record==null || record=="undefined"){
					Ext.MessageBox.alert('提示','请选择一位客户！');
					return;
				}
			var record = cusGroupGrid.getSelectionModel().getSelected();
			if(record==null || record=="undefined"){
					Ext.MessageBox.alert('提示','请选择一个客户群！');
					return;
				}
	 		Ext.Msg.alert('提示', '加入成功');
	 			//cusGroupQueryWind.hide();
	 		}
	 	},   	
	 	{
	 		text:'返 回',
	 		handler:function()
	 		{
	 			cusGroupQueryWind.hide();
	 		}
	 	}
	 	]
	 });
	var batchAdd= function(){
		//var cbid= Ext.getCmp('cbid').getValue();
	 	var checkedNodes = grid.getSelectionModel().selections.items;
	 	var checkedNodes2 = cusGroupGrid.getSelectionModel().selections.items;
		var json={'id':[]};
		var json2={'cust_zh_name':[]};
	 	var json3={'cust_zzdm':[]};
			if(checkedNodes.length==0)
			{
				Ext.Msg.alert('提示', '未选择任何客户');
				return ;
			}
			for(var i=0;i<checkedNodes.length;i++)
			{
				json.id.push(checkedNodes[i].data.CUST_ID);
				json2.cust_zh_name.push(checkedNodes[i].data.CUST_ZH_NAME);
				json3.cust_zzdm.push(checkedNodes[i].data.CUST_ZZDM);
			}
			Ext.Ajax.request({
						url:basepath+'/customer-relate-customer-base.json',
	                    method: 'POST',
						success : function(response) {
							Ext.Msg.alert('提示', '加入成功');
						},
						failure : function(response) {
							  var resultArray = Ext.util.JSON.decode(response.status);
							   if(resultArray == 403) {
							      Ext.Msg.alert('提示','您没有此权限!');
							   } else {
								  Ext.Msg.alert('提示','加入失败!');
							   }
						},
						params : {
							'cid':Ext.encode(json),
							'cust_zh_name': Ext.encode(json2),
							'cust_zzdm': Ext.encode(json3),
							'cbid': checkedNodes2[0].data.id,
							'operate': 'add'
						}});
	
	};
							
/*****************************************************************************/
	var planStore = new Ext.data.JsonStore({
		restful : true,
		autoLoad : true,
		proxy : new Ext.data.HttpProxy({
			url : basepath + '/market-plan.json'
           
		}),
		fields : [ {name:'planId',mapping:'planId'}, {name:'planName',mapping:'planName'} ],
		reader : new Ext.data.JsonReader({
			root:'list'
		}, [ {
			name : 'planId',
			mapping : 'planId'
		}, {
			name : 'planName',
			mapping : 'planName'
		} ])
	});


/********************************************************************************************************/
	
	var sqlForm = new Ext.form.FormPanel({
		id : "sqlfrom",
		labelWidth : 90, // 标签宽度
		frame : true, //是否渲染表单面板背景色
		labelAlign : 'middle', // 标签对齐方式
		//bodyStyle : 'padding:3 5 0', // 表单元素和表单面板的边距
		buttonAlign : 'center',
	//	height : 420,
		items :[{
           autoHeight:true,
           items :[{
        	   	columnWidth:.99,
            	layout: 'form',
            	items: [
            	 {
                 xtype:'textarea',
                 labelStyle: 'text-align:right;',
                 fieldLabel: '请输入sql',
                 height: 270,
                 name: 'productDescription',
                 maxLength : 500,
                 anchor:'100%'
            	 }                     
                ]
            		}]
        }]
	});
/************************************上面是sql查询panel，下面sql查询相关*****************************************/
		var addRoleWindow = new Ext.Window(
			{
				layout : 'fit',
				width : 1000,
				height : 350,
				//resizable : false,//是否允许缩放
				draggable : true,//是否可以拖动
//				closable : true,// 是否可关闭
				modal : true,
				closeAction : 'hide',
				titleCollapse : true,
				buttonAlign : 'center',
				border : false,
				animCollapse : true,
				animateTarget : Ext.getBody(),
				constrain : true,
				items : [sqlForm],
				buttons : [
						{
							text : '查询',
							handler : function() {
								store.loadData(storeData);	
							}
						}, {
							text : '重置',
							handler : function() {
							sqlForm.getForm().reset();
							}
						}, {
							text : '关闭',
							handler : function() {
								addRoleWindow.hide();
							}
						} ]
			});
/***********************************上面高级查询相关，下面普通查询************************************/

		var qForm = new Ext.form.FormPanel({
			id : "qfrom",
			labelWidth : 80, // 标签宽度
			frame : true, //是否渲染表单面板背景色
			labelAlign : 'middle', // 标签对齐方式
			buttonAlign : 'center',
			region : 'north',
			height : 240,
			layout : 'column',
			autoScroll : true,
			items :[{
	           autoHeight:true,
	           items :[{
					xtype : 'fieldset',
					title : '零售客户基本属性信息',
					titleCollapse : true,
					collapsible : true,
					autoHeight : true,
					//height : 78,
					items : [ {
						layout : 'column',
						items : [ {
							layout : 'form',
							columnWidth : .25,
							labelWidth : 80,
							items : [ {
								xtype : 'combo',
								store : boxstore,
								fieldLabel : '性别',
								hiddenName : 'SEX',
								valueField : 'key',
								displayField : 'value',
								triggerAction : 'all',
								editable : false,
								emptyText : '请选择',
								labelStyle : 'text-align:right;',
							//	readOnly : true,
								anchor : '99%'
							}, {
								xtype : 'combo',
								store : ageStore,
								fieldLabel : '年龄',
								hiddenName : 'age',
								valueField : 'key',
								displayField : 'value',
								triggerAction : 'all',
								editable : false,
								emptyText : '请选择',
								mode : 'local',
								labelStyle : 'text-align:right;',
						//		readOnly : true,
								anchor : '99%'
							} ]
						   }, {
							layout : 'form',
							columnWidth : .25,
							labelWidth : 80,
							items : [ {
								xtype : 'combo',
								store : statusStore,
								fieldLabel : '职称',
								hiddenName : 'status',
								triggerAction : 'all',
								valueField : 'key',
								displayField : 'value',
								editable : false,
								emptyText : '请选择',
								mode : 'local',
								labelStyle : 'text-align:right;',
						//		readOnly : true,
								anchor : '99%'
							}, {
								xtype : 'combo',
								store : unitTypeStore,
								fieldLabel : '单位性质',
								hiddenName : 'type',
								triggerAction : 'all',
								valueField : 'key',
								displayField : 'value',
								editable : false,
								emptyText : '请选择',
								labelStyle : 'text-align:right;',
							//	readOnly : true,
								mode : 'local',
								anchor : '99%'
							}]
						}, {
							layout : 'form',
							columnWidth : .25,
							labelWidth : 80,
							items : [ {
								xtype : 'combo',
								store : boxstore8,
								fieldLabel : '客户级别',
								hiddenName : 'HY_CLASS2',
								valueField : 'key',
								displayField : 'value',
								triggerAction : 'all',
								editable : false,
								emptyText : '请选择',
								labelStyle : 'text-align:right;',
							//	readOnly : true,
								anchor : '99%'
							}, {
								xtype : 'combo',
								store : boxstore1,
								fieldLabel : '年收入',
								hiddenName : 'income',
								valueField : 'key',
								displayField : 'value',
								triggerAction : 'all',
								editable : false,
								emptyText : '请选择',
								mode : 'local',
								labelStyle : 'text-align:right;',
							//	readOnly : true,
								anchor : '99%'
							}]
						}, {
							columnWidth : .25,
							layout : 'form',
							labelWidth : 80, // 标签宽度
							defaultType : 'textfield',
							border : false,
							items : [new Com.yucheng.bcrm.common.OrgField({
								searchType:'SUBTREE',/*指定查询机构范围属性  SUBTREE（子机构树）SUBORGS（直接子机构）PARENT（父机构）PARPATH （所有父、祖机构）ALLORG（所有机构）*/
								fieldLabel : '所属机构',
								labelStyle : 'text-align:right;',
								id : 'CUST_ORG', //放大镜组件ID，用于在重置清空时获取句柄
								name : 'CUST_ORG', 
								hiddenName: 'instncode',   //后台获取的参数名称
								anchor : '90%',
								checkBox:true //复选标志
							})
							]
						} ]
					} ]
				
	           },{
	        	   columnWidth : .95,
	        		layout : 'form',
	        		items: [{
	        			xtype: 'fieldset',
	        			title: '客户持有产品类',
	        	//		autoHeight:true,
	        			height : 45,
	        			collapsible : true,
	        			defaultType: 'checkbox',
	        			hideLabels: true,
	        			hiddenname : 'productType',
	        			layout : 'column',
	        			items: [
	        				{columnWidth : .14,boxLabel: '储蓄', name: 'checkbox', inputValue: '1'},
	        				{columnWidth : .14,boxLabel: '个贷', name: 'checkbox', inputValue: '2'},
	        				{columnWidth : .14,boxLabel: '理财', name: 'checkbox', inputValue: '3'},
	        				{columnWidth : .14,boxLabel: '基金', name: 'checkbox', inputValue: '4'},
	        				{columnWidth : .14,boxLabel: '国债', name: 'checkbox', inputValue: '5'},
	        				{columnWidth : .14,boxLabel: '黄金', name: 'checkbox', inputValue: '6'},
	        				{columnWidth : .14,boxLabel: '信用卡', name: 'checkbox', inputValue: '7'}
	        			]
	        		}]
	           },{
	        	   columnWidth : .95,
	        		layout : 'form',
	        		items: [{
	        			xtype: 'fieldset',
	        			title: '客户使用渠道',
	        		//	autoHeight:true,
	        			height : 45,
	        			collapsible : true,
	        			defaultType: 'checkbox',
	        			hideLabels: true,
	        			hiddenname : 'qudao',
	        			layout : 'column',
	        			items: [
	        				{columnWidth : .1,boxLabel: '客户经理', name: 'checkbox', inputValue: '1'},
	        				{columnWidth : .1,boxLabel: 'CC', name: 'checkbox', inputValue: '2'},
	        				{columnWidth : .1,boxLabel: '柜面', name: 'checkbox', inputValue: '3'},
	        				{columnWidth : .1,boxLabel: '手机银行', name: 'checkbox', inputValue: '4'},
	        				{columnWidth : .1,boxLabel: '短信', name: 'checkbox', inputValue: '5'},
	        				{columnWidth : .1,boxLabel: '邮件', name: 'checkbox', inputValue: '6'},
	        				{columnWidth : .1,boxLabel: 'ATM', name: 'checkbox', inputValue: '7'},
	        				{columnWidth : .1,boxLabel: 'VTM', name: 'checkbox', inputValue: '8'},
	        				{columnWidth : .1,boxLabel: '网银', name: 'checkbox', inputValue: '9'}
	        			]
	        		}]
	           },{
					xtype : 'fieldset',
					title : '客户业务信息',
					titleCollapse : true,
					collapsible : true,
					autoHeight : true,
					//height : 50,
					items : [ {
						layout : 'column',
						items : [ {
							layout : 'form',
							columnWidth : .25,
							labelWidth : 80,
							items : [ {
								fieldLabel : '余额',
								name : 'remain',
								xtype : 'textfield', // 设置为数字输入框类型
								labelStyle: 'text-align:right;',
								anchor : '90%'
							}]
						   },{
								layout : 'form',
								columnWidth : .25,
								labelWidth : 80,
								items : [ {
									fieldLabel : '至',
									name : 'remain2',
									xtype : 'textfield', // 设置为数字输入框类型
							     	labelStyle: 'text-align:right;',
									anchor : '90%'
								}]
							}, {
							layout : 'form',
							columnWidth : .25,
							labelWidth : 80,
							items : [ {
								fieldLabel : '日均余额',
								name : 'averageremain',
								xtype : 'textfield', // 设置为数字输入框类型
								labelStyle: 'text-align:right;',
								anchor : '90%'
							}]
						}, {
							layout : 'form',
							columnWidth : .25,
							labelWidth : 80,
							items : [ {
								fieldLabel : '至',
								name : 'averageremain2',
								xtype : 'textfield', // 设置为数字输入框类型
								labelStyle: 'text-align:right;',
								anchor : '90%'
							}]
						}, {
							layout : 'form',
							columnWidth : .25,
							labelWidth : 80,
							items : [ {
								fieldLabel : '收入',
								name : 'CUST_ID',
								xtype : 'textfield', // 设置为数字输入框类型
								labelStyle: 'text-align:right;',
								anchor : '90%'
							}]
						}, {
							layout : 'form',
							columnWidth : .25,
						 	labelWidth : 80,
							items : [ {
								fieldLabel : '至',
								name : 'CUST_ID2',
								xtype : 'textfield', // 设置为数字输入框类型
								labelStyle: 'text-align:right;',
								anchor : '90%'
							}]
						}]
					} ]
				
	           }]
	        }],
		buttons : [{
					text : '查询',
					handler : function() {
						store.loadData(storeData);
					}}, {
					text : 'SQL查询',
						handler : function() {
							addRoleWindow.show();
						}
					},{
					text : '重置',
					     handler : function() {
					    	 qForm.getForm().reset();
					    	 Ext.getCmp('jigouhao').setValue('');
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
	        {header : '客户号',dataIndex : 'custId',sortable : true,width : 100},
		    {header : '客户名称',dataIndex : 'custZhName',width : 150,sortable : true},
		    {header : '证件类型',dataIndex : 'CERT_TYPE_ORA',width : 100,sortable : true},
		    {header : '证件号码',dataIndex : 'certNum',width : 150,sortable : true},
		    {header : '客户状态',dataIndex : 'CUST_STAT_ORA',width : 100,sortable : true},
		    {header : '客户类型',dataIndex : 'custTyp',width : 100,sortable : true,hidden:true},
		    {header : '客户类型',dataIndex : 'CUST_TYP_ORA',width : 100,sortable : true},
		    {header : '客户级别',dataIndex : 'CUST_LEV_ORA',width : 100,sortable : true},
		    {header : '主办机构',dataIndex : 'INSTITUTION_NAME',width : 150,sortable : true},
		    {header : '主办客户经理',dataIndex : 'MGR_NAME',sortable : true},
		    {header : '产品类型',dataIndex : 'PRODUCT_TYPE',sortable : true},
		    {header : '金额',dataIndex : 'SUM',sortable : true},
		    {header : '使用渠道',dataIndex : 'CHANNEL',sortable : true}
			]);

	var storeData = {//特性项数据
			num:6,
			rows:[
			{"custId":"100","custZhName":"王一平","CERT_TYPE_ORA":"居民身份证","certNum":"11092837463762534","CUST_STAT_ORA":"正式","custTyp":"对私", "CUST_TYP_ORA":"对私","CUST_LEV_ORA":"A客户","INSTITUTION_NAME":"世纪支行","PRODUCT_TYPE":"个贷","SUM":"10000","CHANNEL":"客户经理","MGR_NAME":"张三"},
			{"custId":"103","custZhName":"张吉龙","CERT_TYPE_ORA":"居民身份证","certNum":"11092837463762534","CUST_STAT_ORA":"正式","custTyp":"对私", "CUST_TYP_ORA":"对私","CUST_LEV_ORA":"A客户","INSTITUTION_NAME":"世纪支行","PRODUCT_TYPE":"理财","SUM":"30000","CHANNEL":"柜面","MGR_NAME":"李四"},
			{"custId":"101","custZhName":"赵芳","CERT_TYPE_ORA":"居民身份证","certNum":"11092837463762534","CUST_STAT_ORA":"正式","custTyp":"对私", "CUST_TYP_ORA":"对私","CUST_LEV_ORA":"A客户","INSTITUTION_NAME":"世纪支行","PRODUCT_TYPE":"基金","SUM":"10000","CHANNEL":"柜面","MGR_NAME":"张三"},
			{"custId":"102","custZhName":"李毅","CERT_TYPE_ORA":"居民身份证","certNum":"11490580939356935","CUST_STAT_ORA":"正式","custTyp":"对私", "CUST_TYP_ORA":"对私","CUST_LEV_ORA":"B客户","INSTITUTION_NAME":"世纪支行","PRODUCT_TYPE":"个贷","SUM":"400000","CHANNEL":"客户经理","MGR_NAME":"王五"}
			]
			
		}; 
	var storeRecord = Ext.data.Record.create(//特性项记录（record）
			[
				{name:'custId'},
				{name:'custZhName'},
				{name:'CERT_TYPE_ORA'},
				{name:'certNum'},
				{name:'CUST_STAT_ORA'},
				{name:'custTyp'},
				{name:'CUST_TYP_ORA'},
				{name:'CUST_LEV_ORA'},
				{name:'INSTITUTION_NAME'},
				{name:'MGR_NAME'},
				{name:'PRODUCT_TYPE'},
				{name:'SUM'},
				{name:'CHANNEL'}
				]
			);
	var storerReader = new Ext.data.JsonReader(//读取特性项数据的jsonReader
			{
				totalProperty:'num',
				root:'rows'
			},storeRecord
		);
	var store = new Ext.data.Store({//特性项数据的store
		reader:storerReader
	});
	
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
        items : ['-', '&nbsp;&nbsp;', pagesize_combo
                 ]
    });
	var checkedNodessd = '';
	// 表格工具栏

	var tbar = new Ext.Toolbar({
				items : [
					{
			      		text:'导出',
			      		iconCls:'exportIconCss',
			      		handler:function()
			      		{
			      			Ext.Msg.confirm('信息', '确定要导出', function(btn) {  
			      			      if(btn == 'yes') {
			      			    	  alert('已导出');
			      			      }  
			      			});
			      		}
			      	},'-',{
			      		text:'生成客户群',
			      		iconCls:'addIconCss',
			      		handler : function() {
			      			cusGroupQueryWind.show();
			            }
			      	}]
			});

	// 表格实例
	var grid = new Ext.grid.GridPanel({
//				height :350,
				id:'viewgrid',
				frame : true,
//				autoScroll : true,
				region : 'center', // 和VIEWPORT布局模型对应，充当center区域布局
				store : store, // 数据存储
				stripeRows : true, // 斑马线
				cm : cm, // 列模型
				sm : sm, // 复选框
				tbar : [tbar], // 表格工具栏
				bbar:bbar,
				viewConfig:{
					   forceFit:true,
					   autoScroll:true
					},
				loadMask : {
					msg : '正在加载表格数据,请稍等...'
				}
			});


	// 布局模型

	
	var viewport = new Ext.Viewport({
        layout:'fit',
		autoScroll : true,
        items:[{
                layout : 'border',
                items: [{   
                    title: "轻度即席查询->轻度即席查询", 
                    xtype : 'fieldset',
                    collapsed:false,
                    collapsible : true,
                    region: 'north',
                    height: 250,
//                    autoHeight:true,
//                    layout:'fit',
                    margins: '0 0 0 0',
                    //layout: 'fit',
                    items:[qForm]
                 },{   
                    region:'center',
                    layout:'fit',
//                    height: 300,
                    margins: '0 0 0 0',
                    items : [grid]
                }] 
        }]
    });
	
}); 