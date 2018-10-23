Ext.ns('Com.yucheng.bcrm.common');
/**
 * 客户选择放大镜
 * @author ZM
 * @since 2012-11-08
 * @modify luyy  2014-07-17
 */
Com.yucheng.bcrm.common.TradeGroupQueryField1 = Ext.extend(Ext.form.TwinTriggerField, {
    initComponent : function(){
        Ext.ux.form.SearchField.superclass.initComponent.call(this);
        this.on('specialkey', function(f, e){
            if(e.getKey() == e.ENTER){
                this.onTrigger2Click();
            }
        }, this);
    },
    onRender : function(ct, position){
		Com.yucheng.bcrm.common.TradeGroupQueryField1.superclass.onRender.call(this, ct, position);
		if(this.hiddenName){
			var ownerForm = this;
			while(ownerForm.ownerCt && !Ext.instanceOf(ownerForm.ownerCt,'form')){				//根据条件查询放大镜控件的最外层容器
				ownerForm = ownerForm.ownerCt;
			};
			if(Ext.instanceOf(ownerForm.ownerCt,'form')){										//判断父容器是否为form类型
				ownerForm = ownerForm.ownerCt;
				if(ownerForm.getForm().findField(this.hiddenName)){								//如果已经创建隐藏域
					this.hiddenField = ownerForm.getForm().findField(this.hiddenName);
				}else {		//如果未创建隐藏域，则根据hiddenName属性创建隐藏域
					
					this.hiddenField = ownerForm.add({
						
						xtype : 'hidden',
						id:this.hiddenName,
						name: this.hiddenName
					});
				}
			}
		}
	},
	minbrname:'',
	minbrid:'',
	grpname:'',
	groupNo:'',
	groupType:'',
	autoLoadFlag: false,
    singleSelected:false,//记录标志 true单选,false多选
    callback:false,
    customerId:'', 
    custType :'',//客户类型：  2：对私, 1:对公
    custStat:'',//客户状态
    newCust:false,//是否显示“新增潜在客户”的按钮
    identType:'',//证件类型
    identNo:'',//证件号码
    mobileNum:'',//联系电话
    mgrId:'',//主办客户经理ID
    mgrName:'',//主办客户经理姓名
    orgId:'',//主办机构代码
    OrgName:'',//主办机构名称
    linkUser:'',//客户联系人
    chooseLinkMan:false,//是否选择客户联系人
    potentialFlag:'',
    jobType:'',
    industType:'',
    validationEvent:false,
    validateOnBlur:false,
    trigger1Class:'x-form-clear-trigger',
    trigger2Class:'x-form-search-trigger',
    hideTrigger1:true,
    width:180,
    hasSearch : false,
    paramName : 'query',
    hiddenName:false, //用于存隐藏ID字段
    oCustomerQueryWindow : false,
    editable:false,
    listeners:{//增加鼠标点击放大镜输入框触发onTrigger2Click事件
		focus:function(){
			if(!this.disabled){ //禁用的放大镜不允许弹出选择
				this.onTrigger2Click();
			}
		}
	},
    onTrigger2Click : function(){
    	//禁用的放大镜不允许弹出选择
		if(this.disabled){
			return ;
		}
    	var _this=this;
    	if(_this.oCustomerQueryWindow){
    		_this.oCustomerQueryWindow.show();
    		return;
    	}
    	var oThisSearchField=_this;
    	
    	_this.boxstore9 = new Ext.data.Store({  
    		restful:true,   
    		autoLoad :true,
    		proxy : new Ext.data.HttpProxy({
    				url :basepath+'/lookup.json?name=XD000106'
    		}),
    		reader : new Ext.data.JsonReader({
    			root : 'JSON'
    		}, [ 'key', 'value' ])
    		
    	});
    	
    	_this.boxstore9.load({
    	callback:function(){
        		var groupType = oThisSearchField.custStat;
        		if(groupType!=''&&(groupType=='A')){
					_this.oCustomerQueryForm.form.findField('GROUP_TYPE').setValue(groupType);
					
        		}
		}}
    	);
    	_this.oCustomerQueryForm = new Ext.form.FormPanel({
			frame : true, //是否渲染表单面板背景色
			labelAlign : 'right', // 标签对齐方式
			buttonAlign : 'center',
			region:'north',
			height : 110,
			width : 1000,
			items : [{
				layout : 'column',
				border : false,
				items : [{
					columnWidth : .5,
					layout : 'form',
					labelWidth : 80, // 标签宽度
					defaultType : 'textfield',
					border : false,
					items : [{
						allowBlank : true,
						fieldLabel : '集团编号',
						name : 'GRP_NO',
						xtype : 'textfield', // 设置为数字输入框类型
						labelStyle: 'text-align:right;',
						anchor : '90%'
					}]
				}
				,{
					columnWidth : .5,
					layout : 'form',
					labelWidth: 100, // 标签宽度
					defaultType : 'textfield',
					border : false,
					items : [{
						fieldLabel : '集团名称',
						name : 'GRP_NAME',
						xtype : 'textfield', // 设置为数字输入框类型
						labelStyle: 'text-align:right;',
						anchor : '90%'
					}
//					,new Ext.form.ComboBox({
//						labelAlign : 'left',
//						hiddenName : 'GROUP_TYPE',
//						fieldLabel : '集团类型',
//						labelStyle: 'text-align:right;',
//						triggerAction : 'all',
//						value:oThisSearchField.groupType,
//						store : _this.boxstore9,
//						displayField : 'value',
//						valueField : 'key',
//						mode : 'local',
//						editable:false,
//						forceSelection : true,
//						typeAhead : true,
//						emptyText:'请选择',
//						resizable : true,
//						anchor : '90%'
//					})
					]
				}
				]
			}],

			listeners :{
//    			'render':function(){
//    				var groupType = oThisSearchField.groupType;
//    				if(groupType!=''){
//    					_this.oCustomerQueryForm.form.findField('GROUP_TYPE').setValue(groupType);
//    					_this.oCustomerQueryForm.form.findField('GROUP_TYPE').setReadOnly(true);
//    				}else {
//    					_this.oCustomerQueryForm.form.findField('GROUP_TYPE').setReadOnly(false);
//    				}
//    			}
			},
			buttons : [{
				text : '查询',
				handler : function() {
					_this.oCustomerQueryStore.on('beforeload', function() {
						var conditionStr =  _this.oCustomerQueryForm.getForm().getValues(false);
						this.baseParams = {
								"condition":Ext.encode(conditionStr)
						};
					});
					_this.oCustomerQueryStore.reload({
						params : {
							start : 0,
							limit : _this.oCustomerQueryBbar.pageSize
						}
					});
				}
			},{
				text : '重置',
				handler : function() {
					_this.oCustomerQueryForm.getForm().reset();  
				//	_this.oCustomerQueryForm.getForm().findField('MGR_ID').setValue('');
				//	_this.oCustomerQueryForm.getForm().findField('ORG_ID').setValue('');
				}
			}]
		});
    	_this.sm = new Ext.grid.CheckboxSelectionModel({singleSelect:oThisSearchField.singleSelected});

    	// 定义自动当前页行号
    	_this.rownum = new Ext.grid.RowNumberer({
    		header : 'No.',
    		width : 28
    	});
	    
    	// 定义列模型
    	_this.cm = new Ext.grid.ColumnModel([_this.rownum,_this.sm, 
     	                             	    {header : '集团编号',dataIndex : 'GRP_NO',sortable : true,width : 180,align : 'center'},
     	                             	    {header : '集团名称',dataIndex : 'GRP_NAME',width : 180,sortable : true,align : 'center'},
     	                             	    {header : '主申请(集团)名称',dataIndex : 'GROUP_NAME_MAIN',width : 120,sortable : true,align : 'center',hidden:true},
     	                             	    {header : '集团类型',dataIndex : 'GROUP_TYPE',width : 80,sortable : true,align : 'center',hidden:true},
     	                             	    {header : '授信主办行ID',dataIndex : 'MAIN_BR_ID',width : 150,sortable : true,hidden:false,align : 'center'},
     	                             	    {header : '授信主办行名称',dataIndex : 'MAIN_BR_NAME',width : 180,sortable : true,align : 'center'},
     	                             	    {header : '授信总额',dataIndex : 'CREDIT_AMT',width : 80,sortable : true,hidden:false,align : 'center',hidden:true},
     	                             	    {header : '授信币种',dataIndex : 'CREDIT_CUR',width : 80,sortable : true,align : 'center',hidden:true},
     	                             	    {header : '额度到期日',dataIndex : 'DUE_DATE',width : 80,sortable : true,hidden:false,align : 'center',hidden:true},
     	                             	    {header : '集团下辖关联公司名单',dataIndex : 'CORP_NAME_SUB',width : 150,sortable : true,align : 'center',hidden:true},
     	                             	    {header : '买售商使用情况',dataIndex : 'USE_SITUATION',width : 150,sortable : true,hidden:true,align : 'center'},
     	                             	    {header : '创建人ID',dataIndex : 'CREATE_USER_ID',width : 80,sortable : true,align : 'center',hidden:true},
     	                             	    {header : '创建人姓名',dataIndex : 'CREATE_USER_NAME',width : 80,sortable : true,align : 'center',hidden:true},
     	                             	    {header : '创建日期',dataIndex : 'CREATA_DATE',width : 110,sortable : true,align : 'center',hidden:true}
     	                             	]);
  
    	_this.oCustomerQueryStore = new Ext.data.Store({
    		restful:true,	
    		proxy : new Ext.data.HttpProxy({url:basepath+'/ocrmFCiBuygroupInfo.json?'}),
    		reader: new Ext.data.JsonReader({
    			totalProperty : 'json.count',
    			root:'json.data'
    		}, [{name: 'GROUP_TYPE'},
    		    {name: 'GRP_NAME'},
    		    {name: 'GROUP_STATUS'},
    		    {name: 'GROUP_ROOT_CUST_ID'},
    		    {name: 'GRP_FINANCE_TYPE'},
    		    {name: 'UPDATE_DATE'},
    		    {name: 'GROUP_MEMO'},
    		    {name: 'GRP_NO'},
    		    {name: 'UPDATE_USER_ID'},
    		    {name: 'MAIN_BR_ID'},
    		    {name: 'GROUP_ROOT_ADDRESS'},
    		    {name: 'CREATA_DATE'},
    		    {name: 'CREATE_USER_ID'},
    		    {name: 'CREATE_USER_NAME'},
    		    {name: 'CREATE_USER_ORG_ID'},
    		    {name: 'GROUP_NAME_MAIN'},
    		    {name: 'MAIN_BR_NAME'},
    		    {name:'GAO_ORG'},
    		    {name:'CREDIT_AMT'},
    		    {name:'CREDIT_CUR'},
    		    {name:'DUE_DATE'},
    		    {name:'CORP_NAME_SUB'},
    		    {name:'USE_SITUATION'},
    		    {name:'ADDR_REGIST'},
    		    {name: 'EMPLOYEE_NUM'},
    		    {name:'GURANT_CUS_NUM'},
    		    {name:'EMPLOYEE_NUM_FORMAL'},
    		    {name:'PENDING_MEMBER_NUM'},
    		    {name:'EXTERNAL_SECURITY_NUM'},
    		    {name:'GROUP_FORM'},
    		    {name:'USED_AMT'},
    		    {name:'LOAN_BALANCE'},
    		    {name:'BAD_LOAN_BALANCE'},
    		    {name:'GUARANTEE_TYPE'},
    		    {name:'ETL_DATE'},
    		    {name:'USER_NAME'},
    		    {name:'ORG_NAME'}
    		    ])
    	});

    	_this.oPagesizeCombo = new Ext.form.ComboBox({
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
    	_this.number = parseInt(_this.oPagesizeCombo.getValue());
    	_this.oPagesizeCombo.on("select", function(comboBox) {
    		_this.oCustomerQueryBbar.pageSize = parseInt(_this.oPagesizeCombo.getValue()),
    		_this.oCustomerQueryStore.load({
    			params : {
    				start : 0,
    				limit : parseInt(_this.oPagesizeCombo.getValue())
    			}
    		});
    	});
    	_this.oCustomerQueryBbar = new Ext.PagingToolbar({
    		pageSize : _this.number,
    		store : _this.oCustomerQueryStore,
    		displayInfo : true,
    		displayMsg : '显示{0}条到{1}条,共{2}条',
    		emptyMsg : "没有符合条件的记录",
    		items : ['-', '&nbsp;&nbsp;', _this.oPagesizeCombo]
    	});
		// 表格实例
    	_this.oCustomerQueryGrid = new Ext.grid.GridPanel({
    		height : 275,
			width:1000,
			region:'center',
			frame : true,
			autoScroll : true,
			tbar:[{text:'新建潜在客户',
				 hidden:!_this.newCust,
				 handler:function(){
					 newCust();
			}},{text:'选择联系人',
				 hidden:!_this.chooseLinkMan,
				 handler:function(){
					 chooseLinkMan();
			}}],
			store : _this.oCustomerQueryStore, // 数据存储
			stripeRows : true, // 斑马线
			cm : _this.cm, // 列模型
			sm : _this.sm, // 复选框
			bbar:_this.oCustomerQueryBbar,
			viewConfig:{
    			forceFit:false,
    			autoScroll:true
    		},
    		loadMask : {
    			msg : '正在加载表格数据,请稍等...'
    		}
    	});

    	_this.oCustomerQueryWindow=new Ext.Window({
    		title : '买售商客户查询',
    		closable : true,
    		resizable : true,
    		height:435,
    		width:1013,
    		draggable : true,
    		closeAction : 'hide',
    		modal : true, // 模态窗口 
    		border : false,
    		closable : true,
    		layout : 'border',
    		listeners : {
    			'show':function(){
					_this.oCustomerQueryForm.form.reset();
					_this.oCustomerQueryStore.removeAll();
					if(_this.autoLoadFlag)
						_this.oCustomerQueryStore.load({//如果自动加载，需要对数据进行分页
							params : {
								start : 0,
								limit : _this.oCustomerQueryBbar.pageSize
							}
						});
    			}    			
    		},
    		items : [_this.oCustomerQueryForm,_this.oCustomerQueryGrid],
    		buttonAlign:'center',
    		buttons:[
    		         

    	{
			text:'确定',
			handler:function(){
				var sName='';
				var checkedNodes = _this.oCustomerQueryGrid.getSelectionModel().selections.items;
				if(!(_this.oCustomerQueryGrid.getSelectionModel().selections==null)){
					if(oThisSearchField.hiddenField){
//						checkedNodes = _this.oCustomerQueryGrid.getSelectionModel().selections.items;
					if(oThisSearchField.singleSelected&&checkedNodes.length>1){
						Ext.Msg.alert('提示', '您只能选择一个客户');
						return ;
					}
					for(var i=0;i<checkedNodes.length;i++){
						if(i==0){
							sName=checkedNodes[i].data.GRP_NO;
							oThisSearchField.hiddenField.setValue(checkedNodes[i].data.GRP_NO);
						}else{
							sName=sName+','+checkedNodes[i].data.GRP_NO;
							oThisSearchField.hiddenField.setValue(_this.hiddenField.value+','+checkedNodes[i].data.GRP_NO);
						}
					}
					oThisSearchField.setRawValue(sName);
					if(checkedNodes.length==1){//如果单选，则设置该客户相应的附属属性
						oThisSearchField.grpname=checkedNodes[0].data.GRP_NAME;
						oThisSearchField.mainbrid=checkedNodes[0].data.MAIN_BR_ID;
						oThisSearchField.mainbrname=checkedNodes[0].data.MAIN_BR_NAME;
						oThisSearchField.identNo=checkedNodes[0].data.IDENT_NO;
						oThisSearchField.mobileNum=checkedNodes[0].data.LINKMAN_TEL;
						oThisSearchField.mgrId=checkedNodes[0].data.MGR_ID;
						oThisSearchField.mgrName=checkedNodes[0].data.MGR_NAME;
						oThisSearchField.orgId=checkedNodes[0].data.ORG_ID;
						oThisSearchField.orgName=checkedNodes[0].data.ORG_NAME;
						oThisSearchField.custStat=checkedNodes[0].data.CUST_STAT;
						oThisSearchField.linkUser=checkedNodes[0].data.LINKMAN_NAME;
						oThisSearchField.identTypeOra=checkedNodes[0].data.IDENT_TYPE_ORA;	
						oThisSearchField.jobType=checkedNodes[0].data.JOB_TYPE;
						oThisSearchField.industType=checkedNodes[0].data.INDUST_TYPE;
						oThisSearchField.sourceChannel=checkedNodes[0].data.SOURCE_CHANNEL;
					}
				}
					/**
					 * 如果未选择任何客户，清空隐藏域中的客户号 
					 * by GuoChi
					 * 2013-12-25
					 */
					if(_this.oCustomerQueryGrid.getSelectionModel().selections.length==0){
						oThisSearchField.hiddenField.setValue("");
					}	
					
			}
			if (typeof oThisSearchField.callback == 'function') {
				oThisSearchField.callback(oThisSearchField,checkedNodes);
			}
			_this.oCustomerQueryWindow.hide();
		}
	},
    	{
    		text: '取消',
    		handler:function(){
				_this.oCustomerQueryWindow.hide();
    		}
    	}]	
    });
    var chooseLinkMan=function(){
    				 var record= _this.oCustomerQueryGrid.getSelectionModel().getSelected();
					 var checkedNodes = _this.oCustomerQueryGrid.getSelectionModel().selections.items;
					 var custId=checkedNodes[0].data.CUST_ID;
					 var custType=checkedNodes[0].data.CUST_TYPE;
					 if(record==null){
						 Ext.Msg.alert('提示', '请先选择一个客户！');
							return false;
					 }
					 var store = new Ext.data.Store({
							restful:true,	
					        proxy : new Ext.data.HttpProxy({url:basepath+'/mktOpporLinkManQuery.json'}),
					        reader: new Ext.data.JsonReader({
					        	totalProperty : 'json.count',
					        	root:'json.data'
					        }, [
								{name: 'LINKMAN_NAME',mapping :'LINKMAN_NAME'},//联系人
								{name: 'MOBILE',mapping :'MOBILE'}
								
							])
						});
						store.load({params:{
							'custId':custId,
							'custType':custType
						}});
						//复选框
						var sm = new Ext.grid.CheckboxSelectionModel();
						// 定义自动当前页行号
						var rownum = new Ext.grid.RowNumberer({
							header : 'No.',
							width : 28
						});
						// 定义列模型
						var cm = new Ext.grid.ColumnModel([rownum,sm, 
						    {header : '联系人',dataIndex : 'LINKMAN_NAME',width : 150,sortable : true,hidden : false},
						    {header : '手机电话',dataIndex : 'MOBILE',width : 150,sortable : true,hidden : false}
						]);
						var addLinkManGrid=new Ext.grid.GridPanel({
							id:'viewgrid',
							frame : true,
							autoScroll : true,
							region : 'center', // 和VIEWPORT布局模型对应，充当center区域布局
							store : store, // 数据存储
							stripeRows : true, // 斑马线
							cm : cm, // 列模型
							sm : sm, // 复选框
							//tbar : [tbar], // 表格工具栏
							//bbar:bbar,
							viewConfig:{
								   forceFit:false,
								   autoScroll:true
								},
							loadMask : {
								msg : '正在加载表格数据,请稍等...'
							}
						});
						var addLinkManWindow=new Ext.Window({
							title:'联系人',
							layout : 'fit',
							width : 350,
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
							items:[addLinkManGrid],
							buttons : [{
								text:'确定',
								handler:function(){
									var checkedNodes = addLinkManGrid.getSelectionModel().selections.items;
									var checkedNodes2=_this.oCustomerQueryGrid.getSelectionModel().selections.items;
									var names='';
					    			for(var i=0;i<checkedNodes.length;i++){
					    				names+=checkedNodes[i].data.LINKMAN_NAME+';';
					    			}
					    			names.toString();
					    			checkedNodes2[0].data.LINKMAN_NAME=names.toString();
					    			addLinkManWindow.hide();
								}
							},{text : '关闭',
										handler : function() {
											addLinkManWindow.hide();
										}}]
							
						});
						addLinkManWindow.show();
					 //showLinkMan(custId,custType);
    };
	_this.oCustomerQueryWindow.show();
    return;
    }
    
});
Ext.reg('tradequery',Com.yucheng.bcrm.common.TradeGroupQueryField1);

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
var jobstore  = new Ext.data.Store({  
	restful:true,   
	autoLoad :true,
	proxy : new Ext.data.HttpProxy({
			url :basepath+'/lookup.json?name=PAR0400044'
	}),
	reader : new Ext.data.JsonReader({
		root : 'JSON'
	}, [ 'key', 'value' ])
});
var induststore  = new Ext.data.Store({  
	restful:true,   
	autoLoad :true,
	proxy : new Ext.data.HttpProxy({
			url :basepath+'/lookup.json?name=XD000002'
	}),
	reader : new Ext.data.JsonReader({
		root : 'JSON'
	}, [ 'key', 'value' ])
});
//新增潜在来客户
function newCust(){
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
	                             fieldLabel: '*客户名称',
	                             labelStyle: 'text-align:right;',
	                             maxLength:50,
	                             allowBlank : false,
	                             name: 'custName',
	                             anchor:'95%'
	                         },new Ext.form.ComboBox({
	 							name : 'custType',
								fieldLabel : '*客户类别',
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
								anchor : '95%',
								listeners:{
									select:function(){
										var value = addPotentialCustomerPanel.form.findField("custType").getValue();
										if(value == '2'){
											addPotentialCustomerPanel.form.findField("jobType").show();
											addPotentialCustomerPanel.form.findField("industType").hide();
										}else if(value == '1'){
											addPotentialCustomerPanel.form.findField("jobType").hide();
											addPotentialCustomerPanel.form.findField("industType").show();
										}
									}
								}}),{
	                             xtype:'textfield',
	                             fieldLabel: '联系人',
	                             labelStyle: 'text-align:right;',
	                             maxLength:50,
	                             id: 'linkmanName',
	                             anchor:'95%'
	                         }
//	                         ,new Ext.form.ComboBox({
//		 							name : 'phoneArea',
//									fieldLabel : '电话区域',
//									labelStyle: 'text-align:right;',
//									triggerAction : 'all',
//									store : aerastore,
//									displayField : 'value',
//									valueField : 'key',
//									mode : 'local',
//									forceSelection : true,
//									typeAhead : true,
//									emptyText:'请选择',
//									resizable : true,
//									anchor : '95%'
//								})
								]
	                     },{
	                         columnWidth:.33,
	                         layout: 'form',
	                         items: [new Ext.form.ComboBox({
									name : 'identType',
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
	                             name: 'enName',
	                             anchor:'95%'
	                         },{
	                             xtype:'textfield',
	                             fieldLabel: '手机号码',
	                             labelStyle: 'text-align:right;',
	                             maxLength:50,
	                             name: 'callNo',
	                             anchor:'95%'
	                         },{
	                             xtype:'textfield',
	                             fieldLabel: '座机号码',
	                             labelStyle: 'text-align:right;',
	                             maxLength:50,
	                             name: 'contmethInfo',
	                             anchor:'95%'
	                         },{
	                             xtype:'textfield',
	                             fieldLabel: '客户状态',
	                             labelStyle: 'text-align:right;',
	                             maxLength:50,
	                             hidden:true,
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
	                             allowBlank : false,
	                             name: 'identNo',
	                             anchor:'95%'
	                         },{
	                             xtype:'textfield',
	                             fieldLabel: '其它名称',
	                             maxLength:50,
	                             labelStyle: 'text-align:right;',
	                             name: 'shortName',
	                             anchor:'95%'
	                         },{
	                             xtype:'textfield',
	                             fieldLabel: '邮编',
	                             vtype: 'number',
								 maxLength : '6',
								 minLength : '6',
	                             labelStyle: 'text-align:right;',
	                             name: 'portNo',
	                             anchor:'95%'
	                         },new Ext.form.ComboBox({
		 							name : 'jobType',
									fieldLabel : '职业类别',
									labelStyle: 'text-align:right;',
									triggerAction : 'all',
									store : jobstore,
									hidden:true,
									displayField : 'value',
									valueField : 'key',
									mode : 'local',
									forceSelection : true,
									typeAhead : true,
									emptyText:'请选择',
									resizable : true,
									anchor : '95%'}),
									new Ext.form.ComboBox({
			 							name : 'industType',
										fieldLabel : '所属行业',
										labelStyle: 'text-align:right;',
										triggerAction : 'all',
										store : induststore,
										hidden:true,
										displayField : 'value',
										valueField : 'key',
										mode : 'local',
										forceSelection : true,
										typeAhead : true,
										emptyText:'请选择',
										resizable : true,
										anchor : '95%'})]
	                     },{
	                         columnWidth:.99,
	                         layout: 'form',
	                         items: [{
	                             xtype:'textarea',
	                             fieldLabel: '通讯地址',
	                             labelStyle: 'text-align:right;',
	                             maxLength:50,
	                             name: 'addr',
	                             anchor:'99%'
	                         }]
	                     }
	            ]}
	            ]}]
	    });
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
									if(addPotentialCustomerPanel.form.findField('callNo').getValue() == ''&&
											addPotentialCustomerPanel.form.findField('contmethInfo').getValue() == ''){
										Ext.Msg.alert("系统提醒","手机与座机至少填写一个!");
										return false;
									}
									Ext.Ajax.request({
										url : basepath + '/myPotentialCustomer.json',
										method : 'POST',
										form : addPotentialCustomerPanel.getForm().id,
										waitMsg : '正在保存数据,请等待...', // 显示读盘的动画效果，执行完成后效果消失
										params : {
											'custName':addPotentialCustomerPanel.form.findField('custName').getValue(),
											'custType':addPotentialCustomerPanel.form.findField('custType').getValue(),
											'linkmanName':addPotentialCustomerPanel.form.findField('linkmanName').getValue(),
//											'portNo':addPotentialCustomerPanel.form.findField('portNo').getValue(),
											'enName':addPotentialCustomerPanel.form.findField('enName').getValue(),
											'identType':addPotentialCustomerPanel.form.findField('identType').getValue(),
											'callNo':addPotentialCustomerPanel.form.findField('callNo').getValue(),
											'contmethInfo':addPotentialCustomerPanel.form.findField('contmethInfo').getValue(),
//											'phoneArea':addPotentialCustomerPanel.form.findField('phoneArea').getValue(),
											'shortName':addPotentialCustomerPanel.form.findField('shortName').getValue(),
											'identNo':addPotentialCustomerPanel.form.findField('identNo').getValue(),
											'addr':addPotentialCustomerPanel.form.findField('addr').getValue(),
											'jobType':addPotentialCustomerPanel.form.findField('jobType').getValue(),
											'industType':addPotentialCustomerPanel.form.findField('industType').getValue(),
											'grpname':addPotentialCustomerPanel.form.findField('grpname').getValue()
										},
										success :checkResult,
								  		failure:function(a,b){
											var t = Ext.decode(a.responseText);
											Ext.Msg.alert('系统提示','客户已重复，无法新增!');
										}
									});
									function checkResult(response) {
										Ext.Msg.alert('系统提示','新增成功!');
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
							} ]
				});
	 	addPotentialCustomerPanel.getForm().reset();
		addPotentialCustomerWindow.show();
	
};