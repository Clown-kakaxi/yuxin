Ext.onReady(function() {
	var tmepMethod = 'add';
	var sOrgIdJson={'orgid':[]};
	
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

	var addPotentialCustomerPanel = new Ext.FormPanel({
		id:'add',
		frame:true,
		title:'新增潜力客户',
		bodyStyle:'padding:5px 5px 0',
	    width: '100%',
	    autoHeight:true,
	    layout:'column',
	    items:[{
	        columnWidth:.33,
	        layout: 'form',
	        items: [{
            	xtype:'textfield',
                fieldLabel: '*客户ID',
                labelStyle: 'text-align:right;',
                maxLength:50,
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
                vtype: 'number',
                id: 'linkPhone',
                name: 'linkPhone',
                anchor:'95%'
            },{
                xtype:'textfield',
                fieldLabel: '客户状态',
                labelStyle: 'text-align:right;',
                maxLength:50,
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
                vtype: 'number',
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
                id: 'addr',
                name: 'addr',
                anchor:'99%'
            }]
        }] 
	 });
	    //潜在客户管理
	var addPotentialCustomerWindow = new Ext.Window({
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
		buttons : [{
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
						var t = Ext.decode(a.responseText);
						Ext.Msg.alert('系统提示','客户已重复，无法新增!');
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
					} else {
						if(resultArray == 403) {
							Ext.Msg.alert('提示', response.responseText);
						} else {
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
			}
		}, {
			text : '关闭',
			handler : function() {
				addPotentialCustomerWindow.hide();
			}
		}]
	});
	var addRoleWindow = new Ext.Window({
		layout : 'fit',
		width : 1000,
		height : 400,
		draggable : true,//是否可以拖动
		closable : true,// 是否可关闭
		modal : true,
		closeAction : 'hide',
		maximized:true,
		titleCollapse : true,
		buttonAlign : 'center',
		border : false,
		animCollapse : true,
		animateTarget : Ext.getBody(),
		constrain : true,
		items : [{html:' <div style="width:'+document.body.clientWidth+'px;height:'+document.body.clientHeight+'px;"><div style="position:absolute; left:0px; top:0px; " id=\'view\'></div></div>'}],
		buttons : [{
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
	var qFormAll = new Ext.form.FormPanel({
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
			border : false,
			items : [{
				fieldLabel : '客户号',
				name : 'CUST_ID',
				xtype : 'textfield', // 设置为数字输入框类型
				labelStyle: 'text-align:right;',
				anchor : '90%'
			},new Ext.form.ComboBox({
				id : 'custTyps1',
				hiddenName : 'CUST_TYP',
				fieldLabel : '客户类型',
				labelStyle: 'text-align:right;',
				triggerAction : 'all',
				store : boxstore,
				displayField : 'value',
				valueField : 'key',
				mode : 'local',
				forceSelection : true,
				typeAhead : true,
				emptyText:'请选择',
				hidden : true,
				resizable : true,
				anchor : '90%'
			}),new Com.yucheng.crm.common.OrgUserManage({ 
				xtype:'userchoose',
				fieldLabel : '所属客户经理', 
				id:'CUST_MANAGER',
				labelStyle: 'text-align:right;',
				name : 'CUST_MANAGER',
				hiddenName:'custMgrId',
				//searchRoleType:('127,47'),  //指定查询角色属性
				searchType:'SUBTREE',/*指定查询机构范围属性  SUBTREE（子机构树）SUBORGS（直接子机构）PARENT（父机构）PARPATH （所有父、祖机构）ALLORG（所有机构）*/
				singleSelect:false,
				anchor : '90%'
			})
		]}, {
			columnWidth : .25,
			layout : 'form',
			border : false,
			items : [{
				fieldLabel : '客户名称',
				id:'CUST_ZH_NAME',
				name : 'CUST_ZH_NAME',
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
				emptyText:'请选择 ',
				resizable : true,
				anchor : '90%'
			})]
		}, {
			columnWidth : .25,
			layout : 'form',
			border : false,
			items : [{
				fieldLabel : '证件号码',
				name : 'CERT_NUM',
				id:'CERT_NUM',
				xtype : 'textfield', // 设置为数字输入框类型
				labelStyle: 'text-align:right;',
				anchor : '90%'
			},{
				fieldLabel : '数据日期',
				name : 'dataDate',
				xtype : 'datefield',
                format:'Y-m-d',
				labelStyle: 'text-align:right;',
				anchor : '90%'
			}]
		}, {
			columnWidth : .25,
			layout : 'form',
			border : false,
			items : [
			    new Com.yucheng.bcrm.common.OrgField({
				searchType:'ALLORG',/*指定查询机构范围属性  SUBTREE（子机构树）SUBORGS（直接子机构）PARENT（父机构）PARPATH （所有父、祖机构）ALLORG（所有机构）*/
				fieldLabel : '所属机构',
				labelStyle : 'text-align:right;',
				id : 'jigouhao', //放大镜组件ID，用于在重置清空时获取句柄
				name : 'CUST_ORG', 
				hiddenName: 'instncode',   //后台获取的参数名称
				anchor : '90%',
				checkBox:true //复选标志
			})]
		}],
		buttons : [{
			text : '查询',
			handler : function() {
		        store.on('beforeload', function() {
		        	var conditionStr =  qFormAll.getForm().getValues(false);
		            this.baseParams = {
	                    "condition":Ext.encode(conditionStr)
		            };
		        });
				store.load({      
				    params : {
				        start : 0,
				        limit : bbar.pageSize
				    }
				});     
		    }
		},{
		    text : '灵活查询',
		    handler : function() {
		        addRoleWindow.show();
		        setTimeout(function(){
		            Ext.ScriptLoader.loadScript({        
		                scripts: [basepath+'/contents/pages/customer/customerManager/agileQuery.js'],        
		                callback: function() {}
		            });
		        },800);
		    }
		},{
		    text : '重置',
		    handler : function() {
    		    qFormAll.getForm().reset();
    		    Ext.getCmp('jigouhao').setValue('');
    		    Ext.getCmp('CUST_MANAGER').setValue('');
		    }
		}]
	});
	Ext.getCmp('custTyps1').setValue('2');
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
	    {header : '客户维护人',dataIndex : 'MGR_NAME',width : 150,hidden : true ,sortable : true},
	    {header : '一级分行',dataIndex : 'supbrId',width : 200,sortable : true},
	    {header : '二级分行',width : 200,sortable : true},
	    {header : '所属机构',dataIndex : 'INSTITUTION_NAME',sortable : true},
	    {header : '客户状态',dataIndex : 'CUST_STAT_ORA',width : 150,sortable : true},
	    {header : '客户类型',dataIndex : 'custTyp',width : 200,sortable : true,hidden:true},
	    {header : '客户类型',dataIndex : 'CUST_TYP_ORA',width : 200,sortable : true},
	    {header : '客户级别',dataIndex : 'CUST_LEV_ORA',width : 200,hidden : true ,sortable : true},
	    {header : '客户网银状态',dataIndex : 'ifNetbankOra',width : 150,sortable : true},
	    {header : '行业门类',dataIndex:'ENT_MAIN_INDUSTRY_ORA',width : 200,sortable : true},
	    {header : '组织类别',dataIndex:'ENT_ECOM_TYPE_ORA',width : 200,sortable : true},
	    {header : '所有制',dataIndex:'ENT_HOLDING_TYPE_ORA',width : 200,sortable : true},
	    {header : '客户规模',dataIndex:'ENT_SCALE_ORA',width : 200,sortable : true},
	    {header : '利润贡献度',dataIndex :'rotecb',width : 200,sortable : true},
	    {header : '存款余额',dataIndex :'ckbal',width : 200,sortable : true},
	    {header : '存款日均',dataIndex :'ckbalavg',width : 200,sortable : true},
	    {header : '贷款余额',dataIndex :'loanbal',width : 200,sortable : true},
	    {header : '贷款日均',dataIndex :'loanbalavg',width : 200,sortable : true},
	    {header : '承兑余额',dataIndex :'cdbal',width : 200,sortable : true},
	    {header : '其中:电票承兑余额',dataIndex :'dpcdbal',width : 200,sortable : true},
	    {header : '承兑累计',dataIndex :'cdsum',width : 200,sortable : true},
	    {header : '其中：电票承兑累计',dataIndex :'dpcdsum',width : 200,sortable : true},
	    {header : '贴现累计',dataIndex :'tiexsum',width : 200,sortable : true},
	    {header : '其中：电票贴现累计',dataIndex :'dptiebal',width : 200,sortable : true},
	    {header : '客户累计结算量',dataIndex :'custsumbal',width : 200,sortable : true},
	    {header : '中间业务收入',dataIndex :'midbal',width : 200,sortable : true},
	    {header : '国际结算量',dataIndex :'nassumbal',width : 200,sortable : true},
	    {header : '电子银行结算量',dataIndex :'ebanksum',width : 200,sortable : true},
	    {header : '证件类型',dataIndex : 'CERT_TYPE_ORA',width : 150,hidden : true ,sortable : true},
	    {header : '证件号码',dataIndex : 'certNum',width : 150,hidden : true ,sortable : true}
	]);

	/**
	 * 数据存储
	 */
	 var store = new Ext.data.Store({
					restful:true,	
			        proxy : new Ext.data.HttpProxy({url:basepath+'/comCustomerInfo.json'
			        }),
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
//						{name: 'EN_ABBR'},
						{name: 'INSTITUTION_NAME'},
//						{name: 'BGN_DT'},
						{name: 'MGR_NAME'},
						{name: 'custEnName',mapping :'CUST_EN_NAME'},//英文名
						{name: 'otherName',mapping :'OTHER_NAME'},//其他名
						{name: 'certNum',mapping :'CERT_NUM'},//证件号码
						{name: 'linkPhone',mapping :'LINK_PHONE'},//联系电话
						{name: 'postNo',mapping :'POST_NO'},//邮编
						{name: 'commuAddr',mapping :'COMMU_ADDR'},//地址
						{name: 'linkUser',mapping :'LINK_USER'},//联系人
						{name:'supbrId',mapping : 'SUPBRID'},//所属分行
						{name: 'rotecb',mapping : 'ROTECB'},//利润贡献度
						{name: 'ckbal',mapping : 'CKBAL'},//存款余额
						{name:'ckbalavg',mapping : 'CKBALAVG'},//存款日均
						{name:'loanbal',mapping:'LOANBAL'},//贷款余额
						{name:'loanbalavg',mapping:'LOANBALAVG'},//贷款日均
						{name:'cdbal',mapping:'CDBAL'},//承兑余额
						{name:'dpcdbal',mapping:'DPCDBAL'},//电票承兑余额
						{name:'cdsum',mapping:'CDSUM'},//承兑累计
						{name:'dpcdsum',mapping:'DPCDSUM'},//电票承兑累计
						{name:'tiexbal',mapping:'TIEXBAL '},//贴现余额
						{name:'dptiebal',mapping:'DPTIEXBAL'},//电票贴现余额
						{name:'tiexsum',mapping:'TIEXSUM'},//贴现累计
						{name:'dptiexsum',mapping:'DPTIEXSUM'},//电票贴现累计
						{name:'custsumbal',mapping:'CUSTSUMBAL'},//客户累计结算量
						{name:'midbal',mapping:'MIDBAL'},//中间业务收入
						{name:'nassumbal',mapping:'NASSUMBAL'},//国际结算量
						{name:'ebanksum',mapping:'EBANKSUM'},//电子银行结算量
						{name:'etldate',mapping:'ETLDATE'},//数据日期
						{name:'ifNetbank',mapping:'IF_NETBANK'},//客户网银状态
						{name:'ifNetbankOra',mapping:'IF_NETBANK_ORA'},//客户网银状态
						{name:'ENT_MAIN_INDUSTRY'},//行业分类
						{name:'ENT_MAIN_INDUSTRY_ORA'},
						{name:'ENT_ECOM_TYPE'},//组织类别
						{name:'ENT_ECOM_TYPE_ORA'},
						{name:'ENT_HOLDING_TYPE'},//所有制
						{name:'ENT_HOLDING_TYPE_ORA'},
						{name:'ENT_SCALE'},//客户规模
						{name:'ENT_SCALE_ORA'}
						
					])
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
					text : '客户视图',
					iconCls:'ReadIconCss',
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
							viewWindow.show();
						
						},
						failure : function(form, action) {}
						});
					
					}
				},'-',{
					text : '新增潜在客户',
					id:'addPer',
					iconCls:'addIconCss',
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
					Ext.getCmp('commuAddr').setValue('');
					
					Ext.getCmp("custZhName").setReadOnly(false);
					Ext.getCmp("certNum").setReadOnly(false);
					tmepMethod = 'add';
					addPotentialCustomerWindow.show();
					}
				}
				,'-',{
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
							addPotentialCustomerPanel.getForm().loadRecord(selectRe);
							addPotentialCustomerWindow.show();
							
						}
					}
				}				
				
				]
			});


	// 表格实例
	var grid = new Ext.grid.GridPanel({
				height :document.body.scrollHeight-123,
				width : document.body.scrollWidth,
				id:'viewgrid',
				frame : true,
				autoScroll : true,
				region : 'center', // 和VIEWPORT布局模型对应，充当center区域布局
				store : store, // 数据存储
				stripeRows : true, // 斑马线
				cm : cm, // 列模型
				sm : sm, // 复选框
				tbar : [tbar,{
					text : '设为关注客户',
					iconCls:'resetIconCss',
					handler : function() {

					if (grid.selModel.hasSelection()) {
						var records = grid.selModel
								.getSelections();// 得到被选择的行的数组
						var recordsLen = records.length;// 得到行数组的长度
						//alert(recordsLen);
						var idStr = '';
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
							waitMsg : '正在保存数据,请等待...', // 显示读盘的动画效果，执行完成后效果消失
							success : function() {
								Ext.Msg.alert('提示', '操作成功');
							},
							failure:function(a,b){
								var t = Ext.decode(a.responseText);
								Ext.Msg.alert('系统提示','该客户已经是您的关注客户!');
							}
						});		
					} else {
						Ext.Msg.alert("提示", "请先选择要增加的记录!");
					}

				}
				}], // 表格工具栏
				bbar:bbar,
				viewConfig:{
					   forceFit:false,
					   autoScroll:true
					},
				loadMask : {
					msg : '正在加载表格数据,请稍等...'
				},
				listeners : {
				    'dblclick': function(){
    			        var checkedNodes = grid.getSelectionModel().selections.items;
    			        if(checkedNodes.length>0){
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
				//layout : 'border',
				items: [{   
					region: 'north',
				    title: "客户管理->对公客户查询", 
				    height: 120,
				    hidden:false,
				    margins: '0 0 0 0',
				    //layout: 'fit',
					items:[qFormAll]
			     },{   
			    	region:'center',
				    height: document.body.scrollHeight-120,
				    margins: '0 0 0 0',
				    items : [grid]
			    }] 

			});
	var add= function(){
	 	var checkedNodes = grid.getSelectionModel().selections.items;
	 	var json={'cust_id':[]};
	 	var json1={'cust_lev':[]};
	 	var json2={'cust_zh_name':[]};
	 	var json3={'cust_zzdm':[]};
		
			if(checkedNodes.length==0)
			{
				Ext.Msg.alert('提示', '未选择任何客户');
				return ;
			}
			for(var i=0;i<checkedNodes.length;i++)
			{
				json.cust_id.push(checkedNodes[i].data.CUST_ID);
				json1.cust_lev.push(checkedNodes[i].data.CUST_LEV);
				json2.cust_zh_name.push(checkedNodes[i].data.CUST_ZH_NAME);
				json3.cust_zzdm.push(checkedNodes[i].data.CUST_ZZDM);
			}
			Ext.Ajax.request({
						url:basepath+'/customer-attention.json',
                        method: 'POST',
                        success : function(response) {
            				Ext.Msg.alert('提示', '设置成功');
            			},
            			failure : function(response) {
            				  var resultArray = Ext.util.JSON.decode(response.status);
							   if(resultArray == 403) {
							      Ext.Msg.alert('提示','您没有此权限!');
							   } else {
								  Ext.Msg.alert('提示','设置失败!');
							   }
            			},
						params : {
							'cust_id':Ext.encode(json),
							'cust_lev': Ext.encode(json1),
							'cust_zh_name': Ext.encode(json2),
							'cust_zzdm': Ext.encode(json3),
							'operate':'add'
						}});
	
	};
	var fnCondisDecide= function(){
      	var sName1=window.location.href.split("?condis=")[1];
      	var sID1=window.location.href.split("?qStyle=")[1];
      	if(typeof sName1 != "undefined"){
      		
      		Ext.getCmp('CUST_ZH_NAME').setValue(sName1);
      	  store.on('beforeload', function() {
	        	var conditionStr =  qFormAll.getForm().getValues(false);
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
			        	var conditionStr =  qFormAll.getForm().getValues(false);
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
}); 