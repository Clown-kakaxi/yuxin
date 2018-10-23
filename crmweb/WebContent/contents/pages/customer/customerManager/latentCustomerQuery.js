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
							}),{
								fieldLabel : '客户状态',
								name : 'custStatName',
								xtype : 'textfield',
								readOnly : true,
								value : '潜在',
								anchor : '90%'
							},{
								name : 'CUST_STAT',
								xtype : 'hidden',
								value : '2',
								anchor : '90%'
							}]
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
				
				   }},{
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

				items : [
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
//									  '&custId='+checkedNodes[0].data.custId+'&custName='+checkedNodes[0].data.custZhName+
//									  '&custTyp='+checkedNodes[0].data.custTyp;
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
					text : '新增潜在客户',
					id:'addPer',
					iconCls :'addIconCss',
					handler : function() {
					Ext.getCmp('custId').setValue('');
					Ext.getCmp('custZhName').setValue('');
					Ext.getCmp('custTyp').setValue('');
					Ext.getCmp('linkUser').setValue('');
					Ext.getCmp('certType').setValue('');
					Ext.getCmp('custEnName').setValue('');
					Ext.getCmp('linkPhone').setValue('');
					Ext.getCmp('custStat').setValue('');
					Ext.getCmp('certNum').setValue('');
					Ext.getCmp('otherName').setValue('');
					Ext.getCmp('postNo').setValue('');
					Ext.getCmp('addr').setValue('');
					
					Ext.getCmp("custZhName").setReadOnly(false);
					Ext.getCmp("certNum").setReadOnly(false);
					tmepMethod = 'add';
					addPotentialCustomerWindow.show();
					}
				},'-',{
					text : '修改潜在客户',
					iconCls:'editIconCss',
					handler : function() {
					tmepMethod = 'update';
						var selectLength = grid
								.getSelectionModel()
								.getSelections().length;

						var selectRe = grid
								.getSelectionModel()
								.getSelections()[0];

						if (selectLength != 1) {
							alert('请选择一条记录');
						} else {
							var tt = grid.getSelectionModel().getSelections()[0].data.CUST_STAT_ORA;
							if(tt!="潜在"){
								Ext.Msg.alert('系统提示','只能选择客户状态为【潜在】的客户!');
								return false;
							}
							Ext.getCmp("custZhName").setReadOnly(true);
							Ext.getCmp("certNum").setReadOnly(true);
//							var checkCreater = grid.getSelectionModel().selections.items[0].data.createUser;
//							if(__userId!=checkCreater){
//								Ext.Msg.alert("系统提示","您不能修改别人创建的团队！");
//								return false;
//							}
							addPotentialCustomerPanel.getForm().loadRecord(selectRe);
							addPotentialCustomerWindow.show();
							
						}
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
					iconCls:'addIconCss',
					handler : function() {
					var selectLength = grid
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
						selectRe = grid
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
	 * 灵活查询弹出窗口
	 */
	var addRoleWindow = new Ext.Window(
			{
				layout : 'fit',
				width : 1000,
				height : 400,
				//resizable : false,//是否允许缩放
				draggable : true,//是否可以拖动
				closable : true,// 是否可关闭
				modal : true,
				closeAction : 'hide',
				
				// iconCls : 'page_addIcon',
				//maximizable: true,
				maximized:true,
				//collapsible : true,// 是否可收缩
				titleCollapse : true,
				buttonAlign : 'center',
				border : false,
				animCollapse : true,
				animateTarget : Ext.getBody(),
				constrain : true,
				items : [{html:' <div style="width:'+document.body.clientWidth+'px;height:'+document.body.clientHeight+'px;"><div style="position:absolute; left:0px; top:0px; " id=\'view\'></div></div>'
			}],
				buttons : [
						/*{
							text : '查询',
							handler : function() {
								var conditionStr1 =  simple.getForm().getValues(false);
								store.baseParams={
																	
										'condition':Ext.encode(conditionStr1)								
						};
								store.reload({
									  params : {
		                                   start : 0,
		                                   limit : bbar.pageSize
									  
									  }}); 
								addRoleWindow.hide();
								 Ext.getCmp('exportbatten').formPanel=simple;
								//Ext.MessageBox.alert('提示', "保存成功!");
							}
						}, {
							text : '重置',
							handler : function() {
								simple.getForm().reset();
							}
						},*/ {
							text : '关闭',
							handler : function() {
								addRoleWindow.hide();
								//document.getElementById('view').innerHTML = "";
							}
						} ]
			});
		addRoleWindow.on('hide', function() {
			document.getElementById('view').innerHTML = "";
			addSolutionWindow.destroy();
		});

		/*
		 * 潜在客户管理
		 */
		var tmepMethod = 'add';
		var addPotentialCustomerPanel = new Ext.FormPanel({
			 id:'add',
			  frame:true,
		        bodyStyle:'padding:5px 5px 0',
		        width: '100%',
		        items: [{
		           autoHeight:true,
		            items :[{ layout:'column',
		                     items:[{
		                         columnWidth:.33,
		                         layout: 'form',
		                         items: [{
		                             xtype:'textfield',
		                             fieldLabel: '*客户ID',
		                             labelStyle: 'text-align:right;',
		                             maxLength:50,
//		                             allowBlank : false,
		                             hidden:true,
		                             id: 'custId',
		                             name: 'custId',
		                             anchor:'95%'
		                         },{
		                             xtype:'textfield',
		                             fieldLabel: '*客户名称',
		                             labelStyle: 'text-align:right;',
		                             maxLength:50,
		                             allowBlank : false,
		                             id: 'custZhName',
		                             name: 'custZhName',
		                             anchor:'95%'
		                         },new Ext.form.ComboBox({
		 							name : 'custTyp',
		 							id:'custTyp',
									fieldLabel : '*客户大类',
									labelStyle: 'text-align:right;',
									triggerAction : 'all',
									store : boxstore,
									displayField : 'value',
									allowBlank : false,
									valueField : 'key',
									mode : 'local',
									forceSelection : true,
									typeAhead : true,
									emptyText:'请选择',
									resizable : true,
									anchor : '95%'
								}),{
		                             xtype:'textfield',
		                             fieldLabel: '联系人',
		                             labelStyle: 'text-align:right;',
		                             maxLength:50,
//		                             allowBlank : false,
		                             id: 'linkUser',
		                             name: 'linkUser',
		                             anchor:'95%'
		                         }]
		                     },{
		                         columnWidth:.33,
		                         layout: 'form',
		                         items: [new Ext.form.ComboBox({
										name : 'certType',
										id:'certType',
										fieldLabel : '*证件类型',
										labelStyle: 'text-align:right;',
										triggerAction : 'all',
										store : certstore,
										allowBlank : false,
										displayField : 'value',
										valueField : 'key',
										mode : 'local',
										forceSelection : true,
										typeAhead : true,
										emptyText:'请选择',
										resizable : true,
										anchor : '95%'
									}),{
		                             xtype:'textfield',
		                             fieldLabel: '客户英文名称',
		                             maxLength:50,
		                             labelStyle: 'text-align:right;',
		                             id: 'custEnName',
		                             name: 'custEnName',
		                             anchor:'95%'
		                         },{
		                             xtype:'textfield',
		                             fieldLabel: '联系电话',
		                             labelStyle: 'text-align:right;',
		                             maxLength:50,
//		                             vtype: 'number',
//		                             allowBlank : false,
		                             id: 'linkPhone',
		                             name: 'linkPhone',
		                             anchor:'95%'
		                         },{
		                             xtype:'textfield',
		                             fieldLabel: '客户状态',
		                             labelStyle: 'text-align:right;',
		                             maxLength:50,
//		                             allowBlank : false,
		                             hidden:true,
		                             id: 'custStat',
		                             name: 'custStat',
		                             anchor:'95%'
		                         }]
		                     },{
		                         columnWidth:.33,
		                         layout: 'form',
		                         items: [{
		                             xtype:'textfield',
		                             fieldLabel: '*证件号码',
		                             labelStyle: 'text-align:right;',
		                             maxLength:50,
//		                             vtype: 'number',
		                             allowBlank : false,
		                             id: 'certNum',
		                             name: 'certNum',
		                             anchor:'95%'
		                         },{
		                             xtype:'textfield',
		                             fieldLabel: '其它名称',
		                             maxLength:50,
		                             labelStyle: 'text-align:right;',
		                             id: 'otherName',
		                             name: 'otherName',
		                             anchor:'95%'
		                         },{
		                             xtype:'textfield',
		                             fieldLabel: '邮编',
		                             vtype: 'number',
									 maxLength : '6',
									 minLength : '6',
		                             labelStyle: 'text-align:right;',
//		                             allowBlank : false,
		                             id: 'postNo',
		                             name: 'postNo',
		                             anchor:'95%'
		                         }]
		                     },{
		                         columnWidth:.99,
		                         layout: 'form',
		                         items: [{
		                             xtype:'textarea',
		                             fieldLabel: '通讯地址',
		                             labelStyle: 'text-align:right;',
		                             maxLength:50,
//		                             allowBlank : false,
		                             id: 'addr',
		                             name: 'addr',
		                             anchor:'99%'
		                         }]
		                     }
		            ]}
		            ]}]
		    });
		    //潜在客户管理
		 var addPotentialCustomerWindow = new Ext.Window(
					{
						layout : 'fit',
						width : 700,
						height : 280,
						draggable : true,//是否可以拖动
						closable : true,// 是否可关闭
						modal : true,
						closeAction : 'hide',
						titleCollapse : true,
						buttonAlign : 'center',
						border : false,
						animCollapse : true,
						animateTarget : Ext.getBody(),
						constrain : true,
						items : [addPotentialCustomerPanel],
						buttons : [
								{
									text : '保存',
									handler : function(){
										if(!addPotentialCustomerPanel.getForm().isValid()){
											Ext.Msg.alert("系统提醒","输入有误，请重新输入!");
										return false;
										}
										Ext.Ajax.request({
											url : basepath + '/myPotentialCustomer.json?a=1',
											method : 'POST',
											form : addPotentialCustomerPanel.getForm().id,
											waitMsg : '正在保存数据,请等待...', // 显示读盘的动画效果，执行完成后效果消失
											params : {
												'custId':Ext.getCmp('custId').getValue(),
												'custZhName':Ext.getCmp('custZhName').getValue(),
												'custTyp':Ext.getCmp('custTyp').getValue(),
												'linkUser':Ext.getCmp('linkUser').getValue(),
												'postNo':Ext.getCmp('postNo').getValue(),
												'custEnName':Ext.getCmp('custEnName').getValue(),
												'certType':Ext.getCmp('certType').getValue(),
												'linkPhone':Ext.getCmp('linkPhone').getValue(),
												'custStat':Ext.getCmp('custStat').getValue(),
												'otherName':Ext.getCmp('otherName').getValue(),
												'certNum':Ext.getCmp('certNum').getValue(),
												'addr':Ext.getCmp('addr').getValue(),
												'operate':'add'
											},
											success :checkResult,
									  		failure:function(a,b){
//												alert('text:'+a.responseText);
												var t = Ext.decode(a.responseText);
//												Ext.Msg.alert('系统提示',t.message);
												Ext.Msg.alert('系统提示','客户已重复，无法新增!');
//												alert('message:'+t.message);
//												alert('status_code:'+t.status_code);
//												alert('level:'+t.level);
//												alert('code:'+t.code);
//												alert('direct:'+t.direct);
//												alert('msg:'+t.msg);
											}
										});
										function checkResult(response) {
											var resultArray = Ext.util.JSON.decode(response.status);
											var resultError = response.responseText;
											if ((resultArray == 200 ||resultArray == 201)&&resultError=='') {
												Ext.Msg.alert('提示', '操作成功');
												addPotentialCustomerPanel.getForm().reset();
												addPotentialCustomerWindow.hide();
												store.reload({
										params : {
										start : 0,
										limit :bbar.pageSize
										                    }
										                });
											} else{
												if(resultArray == 403){
													Ext.Msg.alert('提示', response.responseText);
													}
												else {
												Ext.Msg.alert('提示', '操作失败,失败原因:' + resultError);
												store.reload({
										params : {
										start : 0,
										limit :bbar.pageSize
										                    }
										                });
											}
										}
										};
									}
								}, {
									text : '重置',
									id : 'btnReset',
									handler : function() {
									addPotentialCustomerPanel.getForm().reset();   
										//clearForm(addRoleFormPanel.getForm());
									}
								}, {
									text : '关闭',
									handler : function() {
									addPotentialCustomerWindow.hide();
									}
								} ]
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
	var custScreenStore = new Ext.data.Store({
	    restful: true,
	    autoLoad: true,
	    proxy: new Ext.data.HttpProxy({
	        url: basepath + '/custScreenQuery.json'
	    }),
	    reader: new Ext.data.JsonReader({
	        root: 'json.data'
	    },
	    [{
	        name: 'CUST_ID',
	        mapping: 'CUST_ID'
	    },{
	        name: 'CUST_ZH_NAME',
	        mapping: 'CUST_ZH_NAME'
	    },
	    {
	        name: 'SCREEN_DATE',
	        mapping: 'SCREEN_DATE'
	    },
	    {
	        name: 'SCREEN_STUTS',
	        mapping: 'SCREEN_STUTS'
	    },
	    {
	        name: 'SCREEN_ID',
	        mapping: 'SCREEN_ID'
	    }
	    
	    ])
	});

	var custScreenrownum = new Ext.grid.RowNumberer({
	    header: 'NO',
	    width: 28
	});

	var custScreensm = new Ext.grid.CheckboxSelectionModel();

	var custScreencm = new Ext.grid.ColumnModel([custScreenrownum, custScreensm, {
	    header: '客户名称',
	    dataIndex: 'CUST_ZH_NAME',
	    sortable: true,
	    width: 250
	},
	{
	    header: '屏蔽日期',
	    dataIndex: 'SCREEN_DATE',
	    sortable: true,
	    width: 200
	},
	{
	    header: '屏蔽状态',
	    dataIndex: 'SCREEN_STUTS',
	    sortable: true,
	    width: 200,
	    renderer:function(v){
			if(v=="1")
				return "<span style='color:red'>已屏蔽</span>";
			else 
				return "<span style='color:green'>未屏蔽</span>";
		}
	},
	{
	    dataIndex: 'CUST_ID',
	    header: '客户编号',
	    hidden:true,
	    allowBlank: false,
	    sortable: true,
	    //defaultValue : '2',
	    width: 100
	},
	{
	    dataIndex: 'SCREEN_ID',
	    header: '客户编号',
	    hidden:true,
	    allowBlank: false,
	    sortable: true,
	    //defaultValue : '2',
	    width: 100
	}]);
	
	var search_cust = new Com.yucheng.bcrm.common.CustomerQueryField({
	    fieldLabel: '客户姓名',
	    labelStyle: 'text-align:right;',
	    labelWidth: 100,
	    name: 'custName',
	    id: 'rel_cust_name',
	    //			custtype :'1',//客户类型：  1：对私, 2:对公,  不设默认全部
	    //		    custStat:'1',//客户状态: 1:正式 2：潜在     , 不设默认全部
	    singleSelected: false,
	    //单选复选标志
	    editable: false,
	    allowBlank: false,
	    //不允许为空
	    blankText: "不能为空，请填写",
	    anchor: '95%',
	    callback: function(a,b) {
//			alert();
			var records = Ext.getCmp('rel_cust_name').oCustomerQueryGrid.getSelectionModel().selections.items;
			custScreenStore.add(records);
//	        custid = Ext.getCmp('rel_cust_name').customerId.aId[0];
	        
	    }
	});
	
	var custScreenGrid = new Ext.grid.GridPanel({
	    id: 'custScreenGrid',
	    width: 500,
	    height: 400,
	    tbar : new Ext.Toolbar(
			{
				items : [
			         "添加客户：",search_cust,'-',
			         "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;筛选条件：",'-',"客户号",
			         {
						fieldLabel : '客户号',
						name : 'CUST_ID',
						id:"custIdScreen",
						xtype : 'textfield',
						labelStyle: 'text-align:right;',
						anchor : '90%'
					},'-','客户名称',
					{
						fieldLabel : '客户名称',
						name : 'CUST_ZH_NAME',
						id:"custZhNameScreen",
						xtype : 'textfield',
						labelStyle: 'text-align:right;',
						anchor : '90%'
					},
					{
						text : '筛选',
						id : 'descBtn',
						xtype : 'button',
						handler : function() {
							var CUST_ID = Ext.getCmp("custIdScreen").getValue();
							var CUST_ZH_NAME = Ext.getCmp("custZhNameScreen").getValue();
							custScreenStore.baseParams = {
								'condition':"{\"CUST_ID\":\""+CUST_ID+"\",\"CUST_ZH_NAME\":\""+CUST_ZH_NAME+"\"}"
							};
							custScreenStore.load();   		
						}
					},{
						text : '重置',
						id : 'descResetBtn',
						xtype : 'button',
						handler : function() {
							Ext.getCmp("custIdScreen").setValue("");
							Ext.getCmp("custZhNameScreen").setValue("");
						}
					}
				]
			}
	    ),
	    store: custScreenStore,
	    cm: custScreencm,
	    sm: custScreensm,
	    loadMask: {
	        msg: '正在加载表格数据,请稍等...'
	    }
	});
	
	
	/*
	 * 特殊客户屏蔽弹出窗口
	 */
	var custScreenWindow = new Ext.Window(
	{
		title:'特殊客户屏蔽',
		layout : 'fit',
		width : 1000,
		height : 400,
		draggable : false,//是否可以拖动
		closable : true,// 是否可关闭
		modal : true,
		closeAction : 'hide',
		maximized:false,
		titleCollapse : true,
		buttonAlign : 'center',
		border : false,
		animCollapse : true,
		animateTarget : Ext.getBody(),
		constrain : true,
		items : [custScreenGrid],
		buttons : [
			{
				text : '确认屏蔽',
				handler : function() {
					var records = custScreenStore.data.items;
					var custIds = "";
					for ( var i = 0; i < records.length; i++) {
						if(!records[i].data.SCREEN_STUTS)//选择未屏蔽的客户来屏蔽
						{
							custIds += records[i].data.CUST_ID+",";
						}
					}
					Ext.Ajax.request({
						url : basepath + '/custScreenOper!create.json',
						method : 'GET',
						 params:{
							'condition':custIds
						},
						waitMsg : '正在保存数据,请等待...', // 显示读盘的动画效果，执行完成后效果消失
						success : function(response){
							custScreenStore.load();
							Ext.Msg.alert("操作提示","客户屏蔽成功");
						},
						failure : function(form, action) {
							custScreenStore.load();
							Ext.Msg.alert("操作提示","客户屏蔽失败");
						}
					});	
				}
			},
			{
				text : '取消屏蔽',
				handler : function() {
					if (!custScreenGrid.selModel.hasSelection()) {
						Ext.Msg.alert("操作提示","请选择取消屏蔽的客户");
					}
					else
					{
						var records = custScreenGrid.getSelectionModel().selections.items;
						var screenIds = "";
						for ( var i = 0; i < records.length; i++) {
							if(records[i].data.SCREEN_STUTS)//选择未屏蔽的客户来屏蔽
							{
								screenIds += records[i].data.SCREEN_ID+",";
							}
						}
						Ext.Ajax.request({
							url : basepath + '/custScreenOper!remove.json',
							method : 'GET',
							 params:{
								'condition':screenIds
							},
							waitMsg : '正在保存数据,请等待...', // 显示读盘的动画效果，执行完成后效果消失
							success : function(response){
								custScreenStore.load();
								Ext.Msg.alert("操作提示","客户取消屏蔽成功");
							},
							failure : function(form, action) {
								custScreenStore.load();
								Ext.Msg.alert("操作提示","客户取消屏蔽失败");
							}
						});	
					}
				}
			},
			{
				text : '关闭',
				handler : function() {
					custScreenWindow.hide();
					store.load({      
						params : {
                           start : 0,
                           limit : bbar.pageSize/*,
                           userId:Ext.encode(userId.aId)*/
                        }
					});
				}
			} 
		]
	});
}); 