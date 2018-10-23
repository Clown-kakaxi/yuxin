 var idStr = '';
Ext.onReady(function() {
	var oldAssistInput=null;
	/**防止内存控制机制误删Ext.MessageBox内部对象**/
	Ext.Msg.alert('ANTIDEBUG','ANTIDEBUG');
	Ext.Msg.hide();
	//客户类型
	var boxstore = new Ext.data.Store({
		restful:true,   
		autoLoad :true,
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
		proxy : new Ext.data.HttpProxy({
				url :basepath+'/lookup.json?name=XD000040'
		}),
		reader : new Ext.data.JsonReader({
			root : 'JSON'
		}, [ 'key', 'value' ])
	});
	//客户级别数据集
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

	//查询条件Form定义
	var qForm = new Ext.form.FormPanel({
			id:'qForm',
			labelWidth : 90, // 标签宽度
			frame : true, //是否渲染表单面板背景色
			labelAlign : 'middle', // 标签对齐方式
			//bodyStyle : 'padding:3 5 0', // 表单元素和表单面板的边距
			buttonAlign : 'center',
			height : 97,
				layout : 'column',
				border : false,
				items : [{
							columnWidth : .25,
							layout : 'form',
							labelWidth : 70, // 标签宽度
							defaultType : 'textfield',
							border : false,
							items : [
				         {
							fieldLabel : '客户号',
							name : 'CUST_ID',
							xtype : 'textfield', // 设置为数字输入框类型
							labelStyle: 'text-align:right;',
							anchor : '90%'
						},new Ext.form.ComboBox({
							hiddenName : 'CUST_TYP',
							fieldLabel : '客户类型',
							labelStyle: 'text-align:right;',
							triggerAction : 'all',
							name:'CUST_TYP',
							store : boxstore,
							displayField : 'value',
							valueField : 'key',
							mode : 'local',
							forceSelection : true,
							typeAhead : true,
							emptyText:'请选择',
							resizable : true,
							anchor : '90%'
						})]
						}, {
							columnWidth : .25,
							layout : 'form',
							labelWidth: 90, // 标签宽度
							defaultType : 'textfield',
							border : false,
							items : [{
								fieldLabel : '客户名称',
								id:'CUST_ZH_NAME',
								name : 'CUST_ZH_NAME',
								xtype : 'textfield', // 设置为数字输入框类型
								labelStyle: 'text-align:right;',
								anchor : '90%',
								enableKeyEvents:true,
								listeners:{
								/*
								 *前台只要引入JSP页面的CSS定义、div定义以及修改ID和URL就好，其余不用做改动。后台走相应url定义的action
								 * */
		                        'keydown': { fn: function(o, evt) {
		                            oldAssistInput = Ext.getCmp('CUST_ZH_NAME').getValue();
		                                                         }},
					         	 'keyup': { fn: function(o, evt) {
								var input = Ext.getCmp('CUST_ZH_NAME');
								var url = basepath + '/customerBaseInformation!NameFind.json';
								assistInput(input, url,oldAssistInput);
		                            }},
		                         'change':{fn:function(o){
		                            var findDivId=document.activeElement.id;
		                            if(findDivId=="custNameInputDiv"){
		                            }else{
		                            	custNameInputDiv.style.display = "none";  
		                            	}  	
		                            }}
							}}
							,new Com.yucheng.crm.common.OrgUserManage({ 
								xtype:'userchoose',
								fieldLabel : '所属客户经理', 
								id:'CUST_MANAGER',
								labelStyle: 'text-align:right;',
								name : 'CUST_MANAGER',
								hiddenName:'custMgrId',
								searchRoleType:('127,47'),  //指定查询角色属性 ,默认全部角色
								searchType:'SUBTREE',/* 允许空，默认辖内机构用户，指定查询机构范围属性  SUBTREE（子机构树）SUBORGS（直接子机构）PARENT（父机构）PARPATH （所有父、祖机构）ALLORG（所有机构）*/
								singleSelect:false,
								anchor : '90%'
								})]
						}, {
							columnWidth : .25,
							layout : 'form',
							labelWidth : 70, // 标签宽度
							defaultType : 'textfield',
							border : false,
							items : [{
								fieldLabel : '证件号码',
								name : 'CERT_NUM',
								id:'CERT_NUM',
								xtype : 'textfield', // 设置为数字输入框类型
								labelStyle: 'text-align:right;',
								anchor : '90%'
							},new Ext.form.ComboBox({
								hiddenName : 'CUST_LEV',
								fieldLabel : '客户级别',
								labelStyle: 'text-align:right;',
								triggerAction : 'all',
								store : boxstore8,
								displayField : 'value',
								valueField : 'key',
								mode : 'local',
								forceSelection : true,
								typeAhead : true,
								emptyText:'请选择',
								resizable : true,
								anchor : '90%'
							})
							]
						}, {
							columnWidth : .25,
							layout : 'form',
							labelWidth : 70, // 标签宽度
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
							})]
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
                               limit : bbar.pageSize/*,
                               userId:Ext.encode(userId.aId)*/
                            }
						});     
				
				   }}, {
					text : '高级查询',
					disabled:JsContext.checkGrant('_superQuery'),
					handler : function() {
					   
//					   if(Com.yucheng.crm.query){
//						   agileQueryWindow.show();
//						   return;
//					   }
					   Ext.ScriptLoader.loadScript({        
						   scripts: [
						             basepath+'/contents/pages/customer/customerManager/agileQuerySolutions.js',
						             basepath+'/contents/pages/customer/customerManager/agileQueryDatasets.js',
						             basepath+'/contents/pages/customer/customerManager/agileQueryItems.js',
						             basepath+'/contents/pages/customer/customerManager/agileQueryResults.js',
						             basepath+'/contents/pages/customer/customerManager/agileQueryGrouping.js',
						             basepath+'/contents/pages/customer/customerManager/agileQuery.js'
						             ],        
						    finalCallback: function() {  
						   		agileQueryWindow.show();
						   	}
						   });
					}
					},{
					text : '重置',
					     handler : function() {
					    	 qForm.getForm().reset();
					    	 Ext.getCmp('CUST_MANAGER').setValue('');
					    	 Ext.getCmp('CUST_ORG').setValue('');
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
//							{name: 'EN_ABBR'},
							{name: 'INSTITUTION_CODE'},
							{name: 'INSTITUTION_NAME'},
//							{name: 'BGN_DT'},
							{name: 'MGR_ID'},
							{name: 'MGR_NAME'},
							{name: 'custEnName',mapping :'CUST_EN_NAME'},//英文名
							{name: 'otherName',mapping :'OTHER_NAME'},//其他名
							{name: 'certNum',mapping :'CERT_NUM'},//证件号码
							{name: 'telephoneNum',mapping :'TELEPHONE_NUM'},//手机号码
							{name: 'officePhone',mapping :'OFFICE_PHONE'},//办公电话
							{name: 'linkPhone',mapping :'LINK_PHONE'},//联系电话
							{name: 'postNo',mapping :'POST_NO'},//邮编
							{name: 'addr',mapping :'ADDR'},//地址
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
		    {header : '证件号码',dataIndex : 'certNum',width : 150,sortable : true,
		    	renderer:function(v){
				if(JsContext.checkGrant('hideCNum'))
					return "******";
				else 
					return v;
			}},
		    {header : '客户状态',dataIndex : 'CUST_STAT_ORA',width : 150,sortable : true},
		    {header : '客户类型',dataIndex : 'custTyp',width : 200,sortable : true,hidden:true},
		    {header : '客户类型',dataIndex : 'CUST_TYP_ORA',width : 200,sortable : true},
		    {header : '客户级别',dataIndex : 'CUST_LEV_ORA',width : 200,sortable : true},
		    {header : '主办机构',dataIndex : 'INSTITUTION_CODE',hidden : true,sortable : true},
		    {header : '主办客户经理',dataIndex : 'MGR_ID',width : 150,hidden : true,sortable : true},
		    {header : '主办机构',dataIndex : 'INSTITUTION_NAME',hidden : true,sortable : true},
		    {header : '主办客户经理',dataIndex : 'MGR_NAME',width : 150,hidden : true,sortable : true}
		    
		    
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
	var checkedNodessd = '';
	// 表格工具栏

	var tbar = new Ext.Toolbar({

				items : [{
					text:'电话营销',
					iconCls:'detailIconCss',
					disabled:parent.MType==0?true:false,//根据接入设备控制按钮是否可用
					handler : function(){
						 var checkedNodes = grid.getSelectionModel().selections.items;
						if (checkedNodes.length == 0) {
							Ext.Msg.alert('提示', '未选择任何客户');
							return;
						} else if (checkedNodes.length > 1) {
							Ext.Msg.alert('提示', '您只能选择一个客户');
							return;
						}
						var data = checkedNodes[0].data;
						if (data.telephoneNum == '' && data.officePhone == ''
								&& data.linkPhone == '') {// 没有电话信息
							Ext.Msg.alert('提示', '本客户没有联系电话信息！');
							return;
						} else {
							newWindow(data, store);
						}
					}
				},{
					text:'面谈营销',
					iconCls:'detailIconCss',
					handler : function(){
						var checkedNodes = grid.getSelectionModel().selections.items;
						if (checkedNodes.length == 0) {
							Ext.Msg.alert('提示', '未选择任何客户');
							return;
						} else if (checkedNodes.length > 1) {
							Ext.Msg.alert('提示', '您只能选择一个客户');
							return;
						}
//						newWindow(data, store);
						var data = checkedNodes[0].data;
						CustdataInfo.custId=data.custId;
		        		CustdataInfo.custZhName=data.custZhName;
		        		CustdataInfo.custTyp=data.custTyp;
		        		CustdataInfo.bizNo = data.custId+"_"+new Date().getTime();
		        		CustdataInfo.ContactInfo = false;
						interview_initdata(CustdataInfo);
					}
				},
					{
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
				},'-',{
					text : '设为关注客户',
					iconCls:'resetIconCss ',
					handler : function() {

					if (grid.selModel.hasSelection()) {
						var records = grid.selModel
								.getSelections();// 得到被选择的行的数组
						var recordsLen = records.length;// 得到行数组的长度
						//alert(recordsLen);
						idStr = '';//需要重新清空
						for ( var i = 0; i < recordsLen; i++) {
							selectRe = records[i];
							tempId = selectRe.data.custId;
									//get(this.primary);
							idStr += tempId;
							if (i != recordsLen - 1)
								idStr += ',';
						};
//						alert(idStr);
						Ext.Ajax.request({
							url : basepath + '/custConcernOper!create.json',
							method : 'GET',
							 params:{
								'condition':idStr
							
							},
							//form : simple2.getForm().id,
							waitMsg : '正在保存数据,请等待...', // 显示读盘的动画效果，执行完成后效果消失
							success : checkResult
						});	
						function checkResult(response,a,b,c) {
								Ext.Msg.alert('提示', '操作成功');
						}
					} else {
						Ext.Msg.alert("提示", "请先选择要设置的客户!");
					}

				}
				},'-',{
					text : '加入客户群',
					hidden:JsContext.checkGrant('sxkhcx_custGroupAdd'),
					iconCls:'addIconCss',
					handler : function() {
						debugger;
						Ext.ScriptLoader.loadScript({        
							   scripts: [
										basepath+'/contents/pages/customer/customerClub/groupCustMgrEdit.js',
							             basepath+'/contents/pages/customer/customerClub/groupLeaguerEdit.js',
							             basepath+'/contents/pages/customer/customerClub/agileQuery.js',
							             basepath+'/contents/pages/customer/customerClub/customerGroupEdit.js',
							             basepath+'/contents/pages/customer/custSearchByDetailType/searchCustForGroup.js'
							            ],        
							    finalCallback: function() {  
							    	var selectLength = grid.getSelectionModel().getSelections().length;
									if (selectLength < 1) {
										Ext.Msg.alert('提示','请至少选择一个客户!');
									}
									else {     
											var selectRe;
											var tempId;
											var custType,m,n;
											idStr = '';//需要先清空
											for ( var i = 0; i < selectLength; i++) {
												selectRe = grid.getSelectionModel().getSelections()[i];
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
							   });
						
			
				}
				}
//				,'-',{
//					text : '特殊客户屏蔽',
//					iconCls:'resetIconCss',
//					handler : function() {
//						custScreenWindow.show();
//					}
//				}	
				]
			});


	// 表格实例
	var grid = new Ext.grid.GridPanel({
				height :document.body.scrollHeight-123,
//				width : document.body.scrollWidth,
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
				},
				listeners : {
					dblclick : function(){
    			        var checkedNodes = grid.getSelectionModel().selections.items;
    			        if (checkedNodes.length>0)
    			        {
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
        						}
        					}
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
        
    				}
				}
			});


   
	// 布局模型
	    
	var viewport = new Ext.Viewport({
	    layout:'fit',
	    items:[{
				layout : 'border',
				items: [{   
					region: 'north',
				    title: "客户管理->客户查询", 
				    height: 120,
				    hidden:false,
				    margins: '0 0 0 0',
				    //layout: 'fit',
					items:[qForm]
			     },{   
			    	region:'center',
				    layout:'fit',
				    height: document.body.scrollHeight-120,
				    margins: '0 0 0 0',
				    items : [grid]
			    }] 
	    }]
	});

	
	/*
	 * 首页客户快速查询参数处理
	 */
	var fnCondisDecide= function(){
		var parms = '';
		if(window.location.search){
			parms = Ext.urlDecode(window.location.search);
		}
      	var sName1=parms['?condis'];
      	var sID1=parms['?qStyle'];
      	if(typeof sName1 != "undefined"){
      		
      		Ext.getCmp('CUST_ZH_NAME').setValue(sName1);
      	  store.on('beforeload', function() {
	        	var conditionStr =  qForm.getForm().getValues(false);
	            this.baseParams = {
	                    "condition":Ext.encode(conditionStr)
	                    
	            };
		});
			store.reload({
		                   
				  params : {
                     start : 0,
                     limit : bbar.pageSize}});
      	};
		if(typeof sID1 != "undefined"){
		      		
		      		Ext.getCmp('CERT_NUM').setValue(sID1);
		      	  store.on('beforeload', function() {
			        	var conditionStr =  qForm.getForm().getValues(false);
			            this.baseParams = {
			                    "condition":Ext.encode(conditionStr)
			                    
			            };
				});
					store.reload({
				                   
						  params : {
		                     start : 0,
		                     limit : bbar.pageSize}});
		      	}
	};

	fnCondisDecide();
	
	/*
	 * 特殊客户屏蔽
	 */
//	var custScreenStore = new Ext.data.Store({
//	    restful: true,
//	    autoLoad: true,
//	    proxy: new Ext.data.HttpProxy({
//	        url: basepath + '/custScreenQuery.json'
//	    }),
//	    reader: new Ext.data.JsonReader({
//	        root: 'json.data'
//	    },
//	    [{
//	        name: 'CUST_ID',
//	        mapping: 'CUST_ID'
//	    },{
//	        name: 'CUST_ZH_NAME',
//	        mapping: 'CUST_ZH_NAME'
//	    },
//	    {
//	        name: 'SCREEN_DATE',
//	        mapping: 'SCREEN_DATE'
//	    },
//	    {
//	        name: 'SCREEN_STUTS',
//	        mapping: 'SCREEN_STUTS'
//	    },
//	    {
//	        name: 'SCREEN_ID',
//	        mapping: 'SCREEN_ID'
//	    }
//	    
//	    ])
//	});
//
//	var custScreenrownum = new Ext.grid.RowNumberer({
//	    header: 'NO',
//	    width: 28
//	});
//
//	var custScreensm = new Ext.grid.CheckboxSelectionModel();
//
//	var custScreencm = new Ext.grid.ColumnModel([custScreenrownum, custScreensm, {
//	    header: '客户名称',
//	    dataIndex: 'CUST_ZH_NAME',
//	    sortable: true,
//	    width: 250
//	},
//	{
//	    header: '屏蔽日期',
//	    dataIndex: 'SCREEN_DATE',
//	    sortable: true,
//	    width: 200
//	},
//	{
//	    header: '屏蔽状态',
//	    dataIndex: 'SCREEN_STUTS',
//	    sortable: true,
//	    width: 200,
//	    renderer:function(v){
//			if(v=="1")
//				return "<span style='color:red'>已屏蔽</span>";
//			else 
//				return "<span style='color:green'>未屏蔽</span>";
//		}
//	},
//	{
//	    dataIndex: 'CUST_ID',
//	    header: '客户编号',
//	    hidden:true,
//	    allowBlank: false,
//	    sortable: true,
//	    //defaultValue : '2',
//	    width: 100
//	},
//	{
//	    dataIndex: 'SCREEN_ID',
//	    header: '客户编号',
//	    hidden:true,
//	    allowBlank: false,
//	    sortable: true,
//	    //defaultValue : '2',
//	    width: 100
//	}]);
//	
//	var search_cust = new Com.yucheng.bcrm.common.CustomerQueryField({
//	    fieldLabel: '客户姓名',
//	    labelStyle: 'text-align:right;',
//	    labelWidth: 100,
//	    name: 'custName',
//	    id: 'rel_cust_name',
//	    //			custtype :'1',//客户类型：  1：对私, 2:对公,  不设默认全部
//	    //		    custStat:'1',//客户状态: 1:正式 2：潜在     , 不设默认全部
//	    singleSelected: false,
//	    //单选复选标志
//	    editable: false,
//	    allowBlank: false,
//	    //不允许为空
//	    blankText: "不能为空，请填写",
//	    anchor: '95%',
//	    callback: function(a,b) {
////			alert();
//			var records = Ext.getCmp('rel_cust_name').oCustomerQueryGrid.getSelectionModel().selections.items;
//			custScreenStore.add(records);
////	        custid = Ext.getCmp('rel_cust_name').customerId.aId[0];
//	        
//	    }
//	});
//	
//	var custScreenGrid = new Ext.grid.GridPanel({
//	    id: 'custScreenGrid',
//	    width: 500,
//	    height: 400,
//	    tbar : new Ext.Toolbar(
//			{
//				items : [
//			         "添加客户：",search_cust,'-',
//			         "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;筛选条件：",'-',"客户号",
//			         {
//						fieldLabel : '客户号',
//						name : 'CUST_ID',
//						id:"custIdScreen",
//						xtype : 'textfield',
//						labelStyle: 'text-align:right;',
//						anchor : '90%'
//					},'-','客户名称',
//					{
//						fieldLabel : '客户名称',
//						name : 'CUST_ZH_NAME',
//						id:"custZhNameScreen",
//						xtype : 'textfield',
//						labelStyle: 'text-align:right;',
//						anchor : '90%'
//					},
//					{
//						text : '筛选',
//						id : 'descBtn',
//						xtype : 'button',
//						handler : function() {
//							var CUST_ID = Ext.getCmp("custIdScreen").getValue();
//							var CUST_ZH_NAME = Ext.getCmp("custZhNameScreen").getValue();
//							custScreenStore.baseParams = {
//								'condition':"{\"CUST_ID\":\""+CUST_ID+"\",\"CUST_ZH_NAME\":\""+CUST_ZH_NAME+"\"}"
//							};
//							custScreenStore.load();   		
//						}
//					},{
//						text : '重置',
//						id : 'descResetBtn',
//						xtype : 'button',
//						handler : function() {
//							Ext.getCmp("custIdScreen").setValue("");
//							Ext.getCmp("custZhNameScreen").setValue("");
//						}
//					}
//				]
//			}
//	    ),
//	    store: custScreenStore,
//	    cm: custScreencm,
//	    sm: custScreensm,
//	    loadMask: {
//	        msg: '正在加载表格数据,请稍等...'
//	    }
//	});
	
	
	/*
	 * 特殊客户屏蔽弹出窗口
	 */
//	var custScreenWindow = new Ext.Window(
//	{
//		title:'特殊客户屏蔽',
//		layout : 'fit',
//		width : 1000,
//		height : 400,
//		draggable : false,//是否可以拖动
//		closable : true,// 是否可关闭
//		modal : true,
//		closeAction : 'hide',
//		maximized:false,
//		titleCollapse : true,
//		buttonAlign : 'center',
//		border : false,
//		animCollapse : true,
//		animateTarget : Ext.getBody(),
//		constrain : true,
//		items : [custScreenGrid],
//		buttons : [
//			{
//				text : '确认屏蔽',
//				handler : function() {
//					var records = custScreenStore.data.items;
//					var custIds = "";
//					for ( var i = 0; i < records.length; i++) {
//						if(!records[i].data.SCREEN_STUTS)//选择未屏蔽的客户来屏蔽
//						{
//							custIds += records[i].data.CUST_ID+",";
//						}
//					}
//					Ext.Ajax.request({
//						url : basepath + '/custScreenOper!create.json',
//						method : 'GET',
//						 params:{
//							'condition':custIds
//						},
//						waitMsg : '正在保存数据,请等待...', // 显示读盘的动画效果，执行完成后效果消失
//						success : function(response){
//							custScreenStore.load();
//							Ext.Msg.alert("操作提示","客户屏蔽成功");
//						},
//						failure : function(form, action) {
//							custScreenStore.load();
//							Ext.Msg.alert("操作提示","客户屏蔽失败");
//						}
//					});	
//				}
//			},
//			{
//				text : '取消屏蔽',
//				handler : function() {
//					if (!custScreenGrid.selModel.hasSelection()) {
//						Ext.Msg.alert("操作提示","请选择取消屏蔽的客户");
//					}
//					else
//					{
//						var records = custScreenGrid.getSelectionModel().selections.items;
//						var screenIds = "";
//						for ( var i = 0; i < records.length; i++) {
//							if(records[i].data.SCREEN_STUTS)//选择未屏蔽的客户来屏蔽
//							{
//								screenIds += records[i].data.SCREEN_ID+",";
//							}
//						}
//						Ext.Ajax.request({
//							url : basepath + '/custScreenOper!remove.json',
//							method : 'GET',
//							 params:{
//								'condition':screenIds
//							},
//							waitMsg : '正在保存数据,请等待...', // 显示读盘的动画效果，执行完成后效果消失
//							success : function(response){
//								custScreenStore.load();
//								Ext.Msg.alert("操作提示","客户取消屏蔽成功");
//							},
//							failure : function(form, action) {
//								custScreenStore.load();
//								Ext.Msg.alert("操作提示","客户取消屏蔽失败");
//							}
//						});	
//					}
//				}
//			},
//			{
//				text : '关闭',
//				handler : function() {
//					custScreenWindow.hide();
//					store.load({      
//						params : {
//                           start : 0,
//                           limit : bbar.pageSize/*,
//                           userId:Ext.encode(userId.aId)*/
//                        }
//					});
//				}
//			} 
//		]
//	});
}); 