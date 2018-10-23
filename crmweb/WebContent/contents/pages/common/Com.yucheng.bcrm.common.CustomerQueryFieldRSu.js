Ext.ns('Com.yucheng.bcrm.common');
/**
 * 客户选择放大镜
 * @author ZM
 * @since 2012-11-08
 * @modify luyy  2015-11-23
 */
Com.yucheng.bcrm.common.CustomerQueryFieldRSu = Ext.extend(Ext.form.TwinTriggerField, {
    initComponent : function(){
        Ext.ux.form.SearchField.superclass.initComponent.call(this);
        this.on('specialkey', function(f, e){
            if(e.getKey() == e.ENTER){
                this.onTrigger2Click();
            }
        }, this);
    },
    onRender : function(ct, position){
		Com.yucheng.bcrm.common.CustomerQueryFieldRSu.superclass.onRender.call(this, ct, position);
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
	isNew:false,//是否只查询新户
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
	blname:'',  //客户类型
	blID:'',    //客户类型id
    entproperty:'',//企业性质
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
    	
    	_this.boxstore = new Ext.data.Store({  
    		restful:true,   
    		autoLoad :true,
    		proxy : new Ext.data.HttpProxy({
    				url :basepath+'/lookup.json?name=XD000080'
    		}),
    		reader : new Ext.data.JsonReader({
    			root : 'JSON'
    		}, [ 'key', 'value' ])
    		
    	});
    	_this.boxstore.load({
    	callback:function(){
        		var custType = oThisSearchField.custType;
        		if(custType!=''&&(custType=='1'||custType=='2')){
        		_this.oCustomerQueryForm.form.findField('CUST_TYPE').setValue(custType);
        		}
		}
    	});
    	_this.boxstore8 = new Ext.data.Store({  
    		sortInfo: {
	    	    field: 'key',
	    	    direction: 'ASC' // or 'DESC' (case sensitive for local sorting)
	    	},
    		restful:true,   
    		autoLoad :true,
    		proxy : new Ext.data.HttpProxy({
    				url :basepath+'/lookup.json?name=PRE_CUST_LEVEL'
    		}),
    		reader : new Ext.data.JsonReader({
    			root : 'JSON'
    		}, [ 'key', 'value' ])
    	});
    	_this.boxstore8.load();
    	//客户状态
    	_this.boxstore9 = new Ext.data.Store({  
//    		sortInfo: {
//	    	    field: 'key',
//	    	    direction: 'ASC' // or 'DESC' (case sensitive for local sorting)
//	    	},
    		restful:true,   
    		autoLoad :true,
    		proxy : new Ext.data.HttpProxy({
    				url :basepath+'/lookup.json?name=XD000081'
    		}),
    		reader : new Ext.data.JsonReader({
    			root : 'JSON'
    		}, [ 'key', 'value' ])
    		
    	});
    	
    	_this.boxstore9.load({
    	callback:function(){
        		var custStat = oThisSearchField.custStat;
        		if(custStat!=''&&(custStat=='A')){
					_this.oCustomerQueryForm.form.findField('CUST_STAT').setValue(custStat);
					
        		}
		}}
    	);
    	
    	_this.boxstore1 = new Ext.data.Store({  
    		restful:true,   
    		autoLoad :true,
    		proxy : new Ext.data.HttpProxy({
    				url :basepath+'/lookup.json?name=XD000084'
    		}),
    		reader : new Ext.data.JsonReader({
    			root : 'JSON'
    		}, [ 'key', 'value' ])
    		
    	});
    	_this.boxstore1.load({
    	callback:function(){
        		var potentialFlag = oThisSearchField.potentialFlag;
        		if(potentialFlag!=''&&(potentialFlag=='0'||potentialFlag=='1'||potentialFlag=='9')){
        		_this.oCustomerQueryForm.form.findField('POTENTIAL_FLAG').setValue(potentialFlag);
        		}
		}
    	});
    	
    	_this.oCustomerQueryForm = new Ext.form.FormPanel({
			frame : true, //是否渲染表单面板背景色
			labelAlign : 'middle', // 标签对齐方式
			buttonAlign : 'center',
			region:'north',
			height : 150,
			width : 1200,
			items : [{
				layout : 'column',
				border : false,
				items : [{
					columnWidth : .25,
					layout : 'form',
					labelWidth : 80, // 标签宽度
					defaultType : 'textfield',
					border : false,
					items : [{
						fieldLabel : '客户号',
						name : 'CUST_ID',
						xtype : 'textfield', // 设置为数字输入框类型
						labelStyle: 'text-align:right;',
						anchor : '90%'
					},new Ext.form.ComboBox({
						hiddenName : 'CUST_TYPE',
						name : 'CUST_TYPE',
						fieldLabel : '客户类型',
						value:oThisSearchField.custType,
						labelStyle: 'text-align:right;',
						triggerAction : 'all',
						store : _this.boxstore,
						displayField : 'value',
						valueField : 'key',
						mode : 'local',
						editable:false,
						forceSelection : true,
						typeAhead : true,
						emptyText:'请选择',
						resizable : true,
						anchor : '90%'
					}),new Ext.form.ComboBox({
						hiddenName : 'POTENTIAL_FLAG',
						name : 'POTENTIAL_FLAG',
						fieldLabel : '是否潜在客户',
						value:oThisSearchField.potentialFlag,
						labelStyle: 'text-align:right;',
						triggerAction : 'all',
						store : _this.boxstore1,
						displayField : 'value',
						valueField : 'key',
						mode : 'local',
						editable:false,
						forceSelection : true,
						typeAhead : true,
						emptyText:'请选择',
						resizable : true,
						anchor : '90%'
					})]
				},{
					columnWidth : .25,
					layout : 'form',
					labelWidth: 100, // 标签宽度
					defaultType : 'textfield',
					border : false,
					items : [{
						fieldLabel : '客户名称',
						name : 'CUST_NAME',
						xtype : 'textfield', // 设置为数字输入框类型
						labelStyle: 'text-align:right;',
						anchor : '90%'
					},new Com.yucheng.crm.common.OrgUserManage({ 
						xtype:'userchoose',
						fieldLabel : '所属客户经理', 
						labelStyle: 'text-align:right;',
						name : 'MGR_NAME',
						hiddenName:'MGR_ID',
						//searchRoleType:('304,100009'),  //指定查询角色属性 ,默认全部角色
						searchType:'SUBTREE',/* 允许空，默认辖内机构用户，指定查询机构范围属性  SUBTREE（子机构树）SUBORGS（直接子机构）PARENT（父机构）PARPATH （所有父、祖机构）ALLORG（所有机构）*/
						singleSelect:false,
						anchor : '90%'
					})]
				},{
					columnWidth : .25,
					layout : 'form',
					labelWidth : 80, // 标签宽度
					defaultType : 'textfield',
					border : false,
					items : [{
						fieldLabel : '证件号码',
						name : 'IDENT_NO',
						xtype : 'textfield', // 设置为数字输入框类型
						labelStyle: 'text-align:right;',
						anchor : '90%'
					},new Ext.form.ComboBox({
						hiddenName : 'CUST_LEVEL',
						fieldLabel : '客户级别',
						labelStyle: 'text-align:right;',
						triggerAction : 'all',
						store : _this.boxstore8,
						displayField : 'value',
						valueField : 'key',
						mode : 'local',
						editable:false,
						forceSelection : true,
						typeAhead : true,
						emptyText:'请选择',
						resizable : true,
						anchor : '90%'
					})]
				},{
					columnWidth : .25,
					layout : 'form',
					labelWidth : 80, // 标签宽度
					defaultType : 'textfield',
					border : false,
					items : [
					    new Com.yucheng.bcrm.common.OrgField({
					    	searchType:'SUBTREE',/*指定查询机构范围属性  SUBTREE（子机构树）SUBORGS（直接子机构）PARENT（父机构）PARPATH （所有父、祖机构）ALLORG（所有机构）*/
					    	fieldLabel : '所属机构',
					    	labelStyle : 'text-align:right;',
					    	name : 'ORG_NAME', 
					    	hiddenName: 'ORG_ID',   //后台获取的参数名称
					    	anchor : '90%',
					    	checkBox:true //复选标志
					    }),
					    new Ext.form.ComboBox({
					    	name : 'CUST_STAT',
					    	hiddenName : 'CUST_STAT',
					    	fieldLabel : '客户状态',
					    	value:oThisSearchField.custStat,
					    	labelStyle: 'text-align:right;',
					    	triggerAction : 'all',
					    	store : _this.boxstore9,//CUSTOMER_STATUS
					    	displayField : 'value',
					    	valueField : 'key',
					    	mode : 'local',
					    	editable:false,
					    	forceSelection : true,
					    	typeAhead : true,
					    	emptyText:'请选择',
					    	resizable : true,
					    	anchor : '90%'
					    })
					]
				}]
			}],
			listeners :{
    			'render':function(){
    				var custType = oThisSearchField.custType;
    				var custStat = oThisSearchField.custStat;
    				var potentialFlag = oThisSearchField.potentialFlag;
    				//默认客户类型
    				if(custType!=''&&(custType=='1'||custType=='2')){
    					_this.oCustomerQueryForm.form.findField('CUST_TYPE').setValue(custType);
    					_this.oCustomerQueryForm.form.findField('CUST_TYPE').setReadOnly(true);
    				}else {
    					_this.oCustomerQueryForm.form.findField('CUST_TYPE').setReadOnly(false);
    				}
    				//默认客户状态
    				if(custStat!=''&&(custStat=='A')){
    					_this.oCustomerQueryForm.form.findField('CUST_STAT').setValue(custStat);
    					_this.oCustomerQueryForm.form.findField('CUST_STAT').setReadOnly(true);
    				}else {
    					_this.oCustomerQueryForm.form.findField('CUST_STAT').setReadOnly(false);
    				}
    				//默认是否潜在客户
    				if(potentialFlag!=''&&(potentialFlag=='0'||potentialFlag=='1'||potentialFlag=='9')){
        				_this.oCustomerQueryForm.form.findField('POTENTIAL_FLAG').setValue(potentialFlag);
    					_this.oCustomerQueryForm.form.findField('POTENTIAL_FLAG').setReadOnly(true);
        			}else {
    					_this.oCustomerQueryForm.form.findField('POTENTIAL_FLAG').setReadOnly(false);
    				}
    			}
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
					_this.oCustomerQueryForm.getForm().findField('MGR_ID').setValue('');
					_this.oCustomerQueryForm.getForm().findField('ORG_ID').setValue('');
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
    	    {header : '客户号',dataIndex : 'CUST_ID',sortable : true,width : 150},
    	    {header : '客户名称',dataIndex : 'CUST_NAME',width : 200,sortable : true},
			{header : '客户类型',dataIndex : 'BL_NAME',width : 80,sortable : true},
    	    {header : '证件类型',dataIndex : 'IDENT_TYPE_ORA',width : 150,sortable : true},
    	    {header : '证件号码',dataIndex : 'IDENT_NO',width : 150,sortable : true},
    	   // {header : '是否潜在客户',dataIndex : 'POTENTIAL_FLAG',width : 80,sortable : true,hidden:true},
    	   // {header : '是否潜在客户',dataIndex : 'POTENTIAL_FLAG_ORA',width : 80,sortable : true},
    	    //{header : '职业类别',dataIndex : 'JOB_TYPE',width : 80,sortable : true,hidden:true},
    	   // {header : '职业类别',dataIndex : 'JOB_TYPE_ORA',width : 80,sortable : true},
    	    {header : '所在行业',dataIndex : 'INDUST_TYPE',width : 80,sortable : true,hidden:true},
    	    {header : '所在行业',dataIndex : 'INDUST_TYPE_ORA',width : 80,sortable : true},
    	    //{header : '客户类型',dataIndex : 'CUST_TYPE',width : 80,sortable : true,hidden:true},
    	    //{header : '客户类型',dataIndex : 'CUST_TYPE_ORA',width : 80,sortable : true},
    	    {header : '是否上市',dataIndex : 'IS_LISTED_CORP',width : 80,sortable : true},
    	    {header : '客户级别',dataIndex : 'CUST_LEVEL_ORA',width : 80,sortable : true},
    	    {header : '所属营业单位',dataIndex : 'ORG_NAME',width : 150,sortable : true},
    	    {header : '客户经理',dataIndex : 'MGR_NAME',width : 80,sortable : true},
    	    {header : '企业 性质',dataIndex : 'ENT_PROPERTY',width : 80,sortable : true},
    	    {header : '法人代表/决策者',dataIndex : 'LEGAL_REPR_NAME',width : 80,sortable : true},
    	    {header : '员工人数',dataIndex : 'EMPLOYEE_SCALE',width : 80,sortable : true},
    	    {header : '企业位置',dataIndex : 'REGISTER_AREA',width : 80,sortable : true},
    	    {header : '行业细分/主要产品',dataIndex : 'MAIN_BUSINESS',width : 80,sortable : true},
    	    {header : '经营年限',dataIndex : 'SPANYEARS',width : 80,sortable : true},
    	    {header : '上年销售额',dataIndex : 'SALE_AMT',width : 80,sortable : true},
    	    {header : '建立合作起日期 ',dataIndex : 'BASIC_ACCT_OPEN_DATE',width : 80,sortable : true},
    	   // {header : '联系人',dataIndex : 'LINKMAN_NAME',width : 150,sortable : true,hidden : true},
    	   // {header : '联系电话',dataIndex : 'LINKMAN_TEL',width : 150,sortable : true,hidden : true},
    	   // {header : '客户渠道',dataIndex : 'SOURCE_CHANNEL',width : 150,sortable : true,hidden : true}
    	    {header : '计划表ID ',dataIndex : 'PLAN_ID',width : 80,sortable : true},
    	    {header : '企业概况 ',dataIndex : 'CORP_PROFILE',width : 80,sortable : true},
    	    {header : '企业文化类型 ',dataIndex : 'CORP_CULTURE',width : 80,sortable : true},
    	    {header : '本年度预估销售额 ',dataIndex : 'SALE_ESTIMATE',width : 80,sortable : true},
    	    {header : '本年度预估销售额增幅比例 ',dataIndex : 'SALE_RANGE_ESTIMATE',width : 80,sortable : true},
    	    {header : '销售地区（前两大） ',dataIndex : 'SALE_AREA',width : 80,sortable : true},
    	    {header : '采购地区(前两大) ',dataIndex : 'PURCHASE_AREA',width : 80,sortable : true},
    	    {header : '销售结算方式第一类 ',dataIndex : 'SETTLE_TYPE_FIR',width : 80,sortable : true},
    	    {header : '销售结算方式第一类结算比例 ',dataIndex : 'SETTLE_TYPE_FIR_SCALE',width : 80,sortable : true},
    	    {header : '销售结算方式第二类 ',dataIndex : 'SETTLE_TYPE_SEC',width : 80,sortable : true},
    	    {header : '销售结算方式第二类结算比例 ',dataIndex : 'SETTLE_TYPE_SEC_SCALE',width : 80,sortable : true},
    	    {header : '销售结算方式第三类 ',dataIndex : 'SETTLE_TYPE_THIR',width : 80,sortable : true},
    	    {header : '销售结算方式第三类结算比例 ',dataIndex : 'SETTLE_TYPE_THIR_SCALE',width : 80,sortable : true},
    	    {header : '应收账款周转周期 ',dataIndex : 'RECEIVABLES_CYCLE',width : 80,sortable : true},
    	    {header : '采购结算方式第一类 ',dataIndex : 'PURCHASE_TYPE_FIR',width : 80,sortable : true},
    	    {header : '采购结算方式第一类结算比例 ',dataIndex : 'PURCHASE_TYPE_FIR_SCALE',width : 80,sortable : true},
    	    {header : '采购结算方式第二类 ',dataIndex : 'PURCHASE_TYPE_SEC',width : 80,sortable : true},
    	    {header : '采购结算方式第二类结算比例 ',dataIndex : 'PURCHASE_TYPE_SEC_SCALE',width : 80,sortable : true},
    	    {header : '采购结算方式第三类 ',dataIndex : 'PURCHASE_TYPE_THIR',width : 80,sortable : true},
    	    {header : '采购结算方式第三类结算比例 ',dataIndex : 'PURCHASE_TYPE_THIR_SCALE',width : 80,sortable : true},
    	    {header : '应付账款周转天期 ',dataIndex : 'ACCOUNTS_PAYABLE_CYCLE',width : 80,sortable : true},
    	    {header : '主要原材料 ',dataIndex : 'MAIN_MATERIAL',width : 80,sortable : true},
    	    {header : '原材料采购量 ',dataIndex : 'MATERIAL_AMMOUNT',width : 80,sortable : true},
    	    {header : '应收账款币种(前两大) ',dataIndex : 'RECEIVABLES_CURRENCE',width : 80,sortable : true},
    	    {header : '应付账款币种(前两大) ',dataIndex : 'ACCOUNTS_PAYABLE_CURRENCE',width : 80,sortable : true},
    	    {header : '出口量 ',dataIndex : 'EXPORT_VOLUME',width : 80,sortable : true},
    	    {header : '进口量 ',dataIndex : 'IMPORT_VOLUME',width : 80,sortable : true},
    	    {header : '法金信用评等 ',dataIndex : 'CREDIT_LEVEL',width : 80,sortable : true},
    	    {header : 'CB评等 ',dataIndex : 'CB_LEVLE',width : 80,sortable : true},
    	    {header : '总授信额度 ',dataIndex : 'LINE_OF_CREDIT',width : 80,sortable : true},
    	    {header : '目前贷款余额 ',dataIndex : 'OUTSTANDING_LOAN',width : 80,sortable : true},
    	    {header : '下次年审时间 ',dataIndex : 'NEXT_ANNUAL_TIME',width : 80,sortable : true}
    	   
    	]);
    	/**
    	 * 数据存储
    	 */
    	_this.oCustomerQueryStore = new Ext.data.Store({
    		restful:true,	//customerBaseInformation cusRelationshipSchedu
    		proxy : new Ext.data.HttpProxy({url:basepath+'/cusRelationshipSchedu.json'}),
    		reader: new Ext.data.JsonReader({
    			totalProperty : 'json.count',
    			root:'json.data'
    		}, [{name: 'CUST_ID'},
    		    {name: 'CUST_NAME'},
    		    {name: 'IDENT_TYPE'},
				{name: 'BL_ID'},//客户类型id
				{name: 'BL_NAME'},
    		    {name: 'IDENT_TYPE_ORA'},
    		    {name: 'IDENT_NO'},
    		    {name: 'CUST_TYPE'},
    		    {name: 'CUST_TYPE_ORA'},
    		    {name: 'CUST_LEVEL'},
    		    {name: 'CUST_LEVEL_ORA'},
    		    {name: 'ORG_ID'},
    		    {name: 'ORG_NAME'},
    		    {name: 'MGR_ID'},
    		    {name: 'MGR_NAME'},
    		    {name: 'CUST_STAT'},
    		    {name: 'CUST_STAT_ORA'},
    		    {name: 'LINKMAN_NAME'},
    		    {name: 'LINKMAN_TEL'},
    		    {name:'JOB_TYPE'},
    		    {name:'JOB_TYPE_ORA'},
    		    {name:'INDUST_TYPE'},
    		    {name:'SOURCE_CHANNEL'},
    		    {name:'POTENTIAL_FLAG'},
    		    {name:'POTENTIAL_FLAG_ORA'},
    		    {name:'INDUST_TYPE_ORA'},
    		    {name:'IS_LISTED_CORP'},
    		    {name:'ENT_PROPERTY'},
    		    {name:'LEGAL_REPR_NAME'},
    		    {name:'EMPLOYEE_SCALE'},
    		    {name:'REGISTER_AREA'},
    		    {name:'MAIN_BUSINESS'},
    		    {name:'SPANYEARS'},
    		    {name:'SALE_AMT'},
    		    {name:'BASIC_ACCT_OPEN_DATE'},
    		    {name:'ID'},
    		    {name:'PLAN_ID'},
    		    {name:'CORP_PROFILE'},
    		    {name:'CORP_CULTURE'},
    		    {name:'SALE_ESTIMATE'},
    		    {name:'SALE_RANGE_ESTIMATE'},
    		    {name:'SALE_AREA'},
    		    {name:'PURCHASE_AREA'},
    		    {name:'SETTLE_TYPE_FIR'},
    		    {name:'SETTLE_TYPE_FIR_SCALE'},
    		    {name:'SETTLE_TYPE_SEC'},
    		    {name:'SETTLE_TYPE_SEC_SCALE'},
    		    {name:'SETTLE_TYPE_THIR'},
    		    {name:'SETTLE_TYPE_THIR_SCALE'},
    		    {name:'RECEIVABLES_CYCLE'},
    		    {name:'PURCHASE_TYPE_FIR'},
    		    {name:'PURCHASE_TYPE_FIR_SCALE'},
    		    {name:'PURCHASE_TYPE_SEC'},
    		    {name:'PURCHASE_TYPE_SEC_SCALE'},
    		    {name:'PURCHASE_TYPE_THIR'},
    		    {name:'PURCHASE_TYPE_THIR_SCALE'},
    		    {name:'ACCOUNTS_PAYABLE_CYCLE'},
    		    {name:'MAIN_MATERIAL'},
    		    {name:'MATERIAL_AMMOUNT'},
    		    {name:'RECEIVABLES_CURRENCE'},
    		    {name:'ACCOUNTS_PAYABLE_CURRENCE'},
    		    {name:'EXPORT_VOLUME'},
    		    {name:'IMPORT_VOLUME'},
    		    {name:'CREDIT_LEVEL'},
    		    {name:'CB_LEVLE'},
    		    {name:'LINE_OF_CREDIT'},
    		    {name:'OUTSTANDING_LOAN'},
    		    {name:'NEXT_ANNUAL_TIME'},
    		    
    		    
    		    {name:'ANALYSIS_ID'},
    		    {name:'DEPOSIT_RMB_AVERAGE'},
    		    {name:'DEPOSIT_RMB_MARGIN'},
    		    {name:'DEPOSIT_RMB_PROPORTION'},
    		    {name:'DEPOSIT_TRADE_AVERAGE'},
    		    {name:'DEPOSIT_TRADE_MARGIN'},
    		    {name:'DEPOSIT_TRADE_PROPORTION'},
    		    {name:'DEPOSIT_OTHER_AVERAGE'},
    		    {name:'DEPOSIT_OTHER_MARGIN'},
    		    {name:'DEPOSIT_OTHER_PROPORTION'},
    		    {name:'EXCHANGE_IMMEDIATE_AVERAGE'},
    		    {name:'EXCHANGE_IMMEDIATE_MARGIN'},
    		    {name:'EXCHANGE_IMMEDIATE_PROPORTION'},
    		    {name:'EXCHANGE_FORWARD_AVERAGE'},
    		    {name:'EXCHANGE_FORWARD_MARGIN'},
    		    {name:'EXCHANGE_FORWARD_PROPORTION'},
    		    {name:'EXCHANGE_INTEREST_AVERAGE'},
    		    {name:'EXCHANGE_INTEREST_MARGIN'},
    		    {name:'EXCHANGE_INTEREST_PROPORTION'},
    		    {name:'OPTIONS_TRADING_AVERAGE'},
    		    {name:'OPTIONS_TRADING_MARGIN'},
    		    {name:'OPTIONS_TRADING_PROPORTION'},
    		    {name:'TRADE_FINANCING_AVERAGE'},
    		    {name:'TRADE_FINANCING_MARGIN'},
    		    {name:'TRADE_FINANCING_PROPORTION'},
    		    {name:'TRADE_FACTORING_AVERAGE'},
    		    {name:'TRADE_FACTORING_MARGIN'},
    		    {name:'TRADE_FACTORING_PROPORTION'},
    		    {name:'TRADE_DISCOUNT_AVERAGE'},
    		    {name:'TRADE_DISCOUNT_MARGIN'},
    		    {name:'TRADE_DISCOUNT_PROPORTION'},
    		    {name:'TRADE_ACCEPTANCE_AVERAGE'},
    		    {name:'TRADE_ACCEPTANCE_MARGIN'},
    		    {name:'TRADE_ACCEPTANCE_PROPORTION'},
    		    {name:'TRADE_CREDIT_AVERAGE'},
    		    {name:'TRADE_CREDIT_MARGIN'},
    		    {name:'TRADE_CREDIT_PROPORTION'},
    		    {name:'TRADE_GUARANTEE_AVERAGE'},
    		    {name:'TRADE_GUARANTEE_MARGIN'},
    		    {name:'TRADE_GUARANTEE_PROPORTION'},
    		    {name:'LOAN_AVERAGE'},
    		    {name:'LOAN_MARGIN'},
    		    {name:'LOAN_PROPORTION'},
    		    {name:'SUIT_PRODUCTS'},
    		    {name:'WALLETSIZE_PRODUCTS'},
    		    {name:'PROVIDE_PRODUCTS'},
    		   
    		    {name:'ANAL_ID'},
    		    {name:'DEPOSIT_RMB_AVERAGE_NY'},
    		    {name:'DEPOSIT_RMB_MARGIN_NY'},
    		    {name:'DEPOSIT_RMB_PROPORT_NY'},
    		    {name:'DEPOSIT_TRADE_AVERAGE_NY'},
    		    {name:'DEPOSIT_TRADE_MARGIN_NY'},
    		    {name:'DEPOSIT_TRADE_PROPORT_NY'},
    		    {name:'DEPOSIT_OTHER_AVERAGE_NY'},
    		    {name:'DEPOSIT_OTHER_MARGIN_NY'},
    		    {name:'DEPOSIT_OTHER_PROPORT_NY'},
    		    {name:'EXCHANGE_IMMEDIATE_AVERAGE_NY'},
    		    {name:'EXCHANGE_IMMEDIATE_MARGIN_NY'},
    		    {name:'EXCHANGE_IMMEDIATE_PROPORT_NY'},
    		    {name:'EXCHANGE_FORWARD_AVERAGE_NY'},
    		    {name:'EXCHANGE_FORWARD_MARGIN_NY'},
    		    {name:'EXCHANGE_FORWARD_PROPORT_NY'},
    		    {name:'EXCHANGE_INTEREST_AVERAGE_NY'},
    		    {name:'EXCHANGE_INTEREST_MARGIN_NY'},
    		    {name:'EXCHANGE_INTEREST_PROPORT_NY'},
    		    {name:'OPTIONS_TRADING_AVERAGE_NY'},
    		    {name:'OPTIONS_TRADING_MARGIN_NY'},
    		    {name:'OPTIONS_TRADING_PROPORT_NY'},
    		    {name:'TRADE_FINANCING_AVERAGE_NY'},
    		    {name:'TRADE_FINANCING_MARGIN_NY'},
    		    {name:'TRADE_FINANCING_PROPORT_NY'},
    		    {name:'TRADE_FACTORING_AVERAGE_NY'},
    		    {name:'TRADE_FACTORING_MARGIN_NY'},
    		    {name:'TRADE_FACTORING_PROPORT_NY'},
    		    {name:'TRADE_DISCOUNT_AVERAGE_NY'},
    		    {name:'TRADE_DISCOUNT_MARGIN_NY'},
    		    {name:'TRADE_DISCOUNT_PROPORT_NY'},
    		    {name:'TRADE_ACCEPTANCE_AVERAGE_NY'},
    		    {name:'TRADE_ACCEPTANCE_MARGIN_NY'},
    		    {name:'TRADE_ACCEPTANCE_PROPORT_NY'},
    		    {name:'TRADE_CREDIT_AVERAGE_NY'},
    		    {name:'TRADE_CREDIT_MARGIN_NY'},
    		    {name:'TRADE_CREDIT_PROPORT_NY'},
    		    {name:'TRADE_GUARANTEE_AVERAGE_NY'},
    		    {name:'TRADE_GUARANTEE_MARGIN_NY'},
    		    {name:'TRADE_GUARANTEE_PROPORT_NY'},
    		    {name:'LOAN_AVERAGE_NY'},
    		    {name:'LOAN_MARGIN_NY'},
    		    {name:'LOAN_PROPORT_NY'},
    		    {name:'SUIT_PRODUCTS_NY'},
    		    {name:'WALLETSIZE_PRODUCTS_NY'},
    		    {name:'PROVIDE_PRODUCTS_NY'},
    		    
    		    {name:'ISFLAG'},  //潜在客户标识
    		    {name:'ISDEBTS'}, //有存款产品的客户标识
    		    {name:'ISMEANS'}  //有贷款产品的客户标识
    		    
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
    		height : 285,
			width:1200,
			region:'center',
			frame : true,
			autoScroll : true,
			tbar:[{text:'新建潜在客户',
				 hidden:!_this.newCust,
				 handler:function(){
				
			}},{text:'选择联系人',
				 hidden:!_this.chooseLinkMan,
				 handler:function(){
					
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
    		title : '客户查询',
    		closable : true,
    		resizable : true,
    		height:450,
    		width:1250,
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
					//第一列有值统计
					 countClom1=0;
					//第四列有值统计
					 countClom4=0;
					//第四列有值统计
				    countClom41=0;
					//第四列有值统计
					countClom42=0;
					
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
    		buttons:[{
    			text:'确定',
    			handler:function(){
    				var sName='';
    				var checkedNodes = _this.oCustomerQueryGrid.getSelectionModel().selections.items;
    				if(!(_this.oCustomerQueryGrid.getSelectionModel().selections==null)){
    					if(oThisSearchField.hiddenField){
//    						checkedNodes = _this.oCustomerQueryGrid.getSelectionModel().selections.items;
						if(oThisSearchField.singleSelected&&checkedNodes.length>1){
							Ext.Msg.alert('提示', '您只能选择一个客户');
							return ;
						}
						for(var i=0;i<checkedNodes.length;i++){
							if(i==0){
								sName=checkedNodes[i].data.CUST_NAME;
								oThisSearchField.hiddenField.setValue(checkedNodes[i].data.CUST_ID);
							}else{
								sName=sName+','+checkedNodes[i].data.CUST_NAME;
								oThisSearchField.hiddenField.setValue(_this.hiddenField.value+','+checkedNodes[i].data.CUST_ID);
							}
						}
						oThisSearchField.setRawValue(sName);
						if(checkedNodes[0].data.ISMEANS=='1'){
							cust_form.getForm().findField('cb-col-1').setValue(true);
						}
						if(checkedNodes[0].data.ISDEBTS=='1'){
							cust_form.getForm().findField('cb-col-2').setValue(true);
						}
						if(checkedNodes[0].data.ISFLAG=='1'){
							cust_form.getForm().findField('cb-col-3').setValue(true);
							cust_form.getForm().findField('POT_INDUST_TYPE').setValue(checkedNodes[0].data.INDUST_TYPE);
							cust_form.getForm().findField('INDUST_TYPE_ORA').setVisible(false);
							cust_form.getForm().findField('POT_INDUST_TYPE').setVisible(true); //显示潜在客户所属行业
						}else{
							cust_form.getForm().findField('INDUST_TYPE_ORA').setVisible(true); //显示客户所属行业
							cust_form.getForm().findField('POT_INDUST_TYPE').setVisible(false);
						}
						cust_form.getForm().findField('CUST_ID').setValue(checkedNodes[0].data.CUST_ID);
						cust_form.getForm().findField('IDENT_NO').setValue(checkedNodes[0].data.IDENT_NO);  //组织机构代码证/商业登记证号
						//cust_form.getForm().findField('BL_ID').setValue(checkedNodes[0].data.BL_ID);    //客户类型ID
						cust_form.getForm().findField('BL_NAME').setValue(checkedNodes[0].data.BL_NAME);  //客户类型
						cust_form.getForm().findField('ORG_ID').setValue(checkedNodes[0].data.ORG_ID);    //所属营业单位ID
						cust_form.getForm().findField('ORG_NAME').setValue(checkedNodes[0].data.ORG_NAME);//所属营业单位
						cust_form.getForm().findField('MGR_ID').setValue(checkedNodes[0].data.MGR_ID);  //客户经理ID
						cust_form.getForm().findField('MGR_NAME').setValue(checkedNodes[0].data.MGR_NAME);  //客户经理
						cust_form.getForm().findField('IS_LISTED_CORP').setValue(checkedNodes[0].data.IS_LISTED_CORP);  //是否上市
						cust_form.getForm().findField('INDUST_TYPE').setValue(checkedNodes[0].data.INDUST_TYPE);      //所属行业
						cust_form.getForm().findField('INDUST_TYPE_ORA').setValue(checkedNodes[0].data.INDUST_TYPE_ORA);   //所属行业
						cust_form.getForm().findField('ENT_PROPERTY').setValue(checkedNodes[0].data.ENT_PROPERTY);        //企业性质
						cust_form.getForm().findField('LEGAL_REPR_NAME').setValue(checkedNodes[0].data.LEGAL_REPR_NAME); //法人代表
						cust_form.getForm().findField('EMPLOYEE_SCALE').setValue(checkedNodes[0].data.EMPLOYEE_SCALE); //员工人数
						cust_form.getForm().findField('REGISTER_AREA').setValue(checkedNodes[0].data.REGISTER_AREA);   //公司位置
						cust_form.getForm().findField('MAIN_BUSINESS').setValue(checkedNodes[0].data.MAIN_BUSINESS);     //行业 细/主要产品
						cust_form.getForm().findField('SPANYEARS').setValue(checkedNodes[0].data.SPANYEARS);             //经营年限
						cust_form.getForm().findField('SALE_AMT').setValue(checkedNodes[0].data.SALE_AMT);     //上年销售额
						cust_form.getForm().findField('BASIC_ACCT_OPEN_DATE').setValue(checkedNodes[0].data.BASIC_ACCT_OPEN_DATE);     //建立合作起日期
						
						cust_form.getForm().findField('ID').setValue(checkedNodes[0].data.ID);
						cust_form.getForm().findField('PLAN_ID').setValue(checkedNodes[0].data.PLAN_ID); 
						cust_form.getForm().findField('CORP_PROFILE').setValue(checkedNodes[0].data.CORP_PROFILE); 
						cust_form.getForm().findField('CORP_CULTURE').setValue(checkedNodes[0].data.CORP_CULTURE); 
						cust_form.getForm().findField('SALE_ESTIMATE').setValue(checkedNodes[0].data.SALE_ESTIMATE); 
						cust_form.getForm().findField('SALE_RANGE_ESTIMATE').setValue(checkedNodes[0].data.SALE_RANGE_ESTIMATE); 
						cust_form.getForm().findField('SALE_AREA').setValue(checkedNodes[0].data.SALE_AREA); 
						
						cust_form.getForm().findField('PURCHASE_AREA').setValue(checkedNodes[0].data.PURCHASE_AREA); 
						cust_form.getForm().findField('SETTLE_TYPE_FIR').setValue(checkedNodes[0].data.SETTLE_TYPE_FIR); 
						cust_form.getForm().findField('SETTLE_TYPE_FIR_SCALE').setValue(checkedNodes[0].data.SETTLE_TYPE_FIR_SCALE); 
						cust_form.getForm().findField('SETTLE_TYPE_SEC').setValue(checkedNodes[0].data.SETTLE_TYPE_SEC); 
						cust_form.getForm().findField('SETTLE_TYPE_SEC_SCALE').setValue(checkedNodes[0].data.SETTLE_TYPE_SEC_SCALE); 
						cust_form.getForm().findField('SETTLE_TYPE_THIR').setValue(checkedNodes[0].data.SETTLE_TYPE_THIR); 
						cust_form.getForm().findField('SETTLE_TYPE_THIR_SCALE').setValue(checkedNodes[0].data.SETTLE_TYPE_THIR_SCALE); 
						cust_form.getForm().findField('RECEIVABLES_CYCLE').setValue(checkedNodes[0].data.RECEIVABLES_CYCLE); 
						cust_form.getForm().findField('PURCHASE_TYPE_FIR').setValue(checkedNodes[0].data.PURCHASE_TYPE_FIR); 
						cust_form.getForm().findField('PURCHASE_TYPE_FIR_SCALE').setValue(checkedNodes[0].data.PURCHASE_TYPE_FIR_SCALE); 
						cust_form.getForm().findField('PURCHASE_TYPE_SEC').setValue(checkedNodes[0].data.PURCHASE_TYPE_SEC); 
						cust_form.getForm().findField('PURCHASE_TYPE_SEC_SCALE').setValue(checkedNodes[0].data.PURCHASE_TYPE_SEC_SCALE); 
						cust_form.getForm().findField('PURCHASE_TYPE_THIR').setValue(checkedNodes[0].data.PURCHASE_TYPE_THIR); 
						cust_form.getForm().findField('PURCHASE_TYPE_THIR_SCALE').setValue(checkedNodes[0].data.PURCHASE_TYPE_THIR_SCALE); 
						cust_form.getForm().findField('ACCOUNTS_PAYABLE_CYCLE').setValue(checkedNodes[0].data.ACCOUNTS_PAYABLE_CYCLE); 
						cust_form.getForm().findField('MAIN_MATERIAL').setValue(checkedNodes[0].data.MAIN_MATERIAL); 
						cust_form.getForm().findField('MATERIAL_AMMOUNT').setValue(checkedNodes[0].data.MATERIAL_AMMOUNT); 
						cust_form.getForm().findField('RECEIVABLES_CURRENCE').setValue(checkedNodes[0].data.RECEIVABLES_CURRENCE); 
						cust_form.getForm().findField('ACCOUNTS_PAYABLE_CURRENCE').setValue(checkedNodes[0].data.ACCOUNTS_PAYABLE_CURRENCE); 
						cust_form.getForm().findField('EXPORT_VOLUME').setValue(checkedNodes[0].data.EXPORT_VOLUME); 
						cust_form.getForm().findField('IMPORT_VOLUME').setValue(checkedNodes[0].data.IMPORT_VOLUME); 
						cust_form.getForm().findField('CREDIT_LEVEL').setValue(checkedNodes[0].data.CREDIT_LEVEL); 
						cust_form.getForm().findField('CB_LEVLE').setValue(checkedNodes[0].data.CB_LEVLE); 
						cust_form.getForm().findField('LINE_OF_CREDIT').setValue(checkedNodes[0].data.LINE_OF_CREDIT); 
						cust_form.getForm().findField('OUTSTANDING_LOAN').setValue(checkedNodes[0].data.OUTSTANDING_LOAN); 
						cust_form.getForm().findField('NEXT_ANNUAL_TIME').setValue(checkedNodes[0].data.NEXT_ANNUAL_TIME); 
					
						cust_form3.getForm().findField('CUST_ID').setValue(checkedNodes[0].data.CUST_ID);
						cust_form4.getForm().findField('CUST_ID').setValue(checkedNodes[0].data.CUST_ID);
						
						
						
						cust_form3.getForm().findField('ID').setValue(checkedNodes[0].data.ANALYSIS_ID);
						cust_form3.getForm().findField('PLAN_ID').setValue(checkedNodes[0].data.PLAN_ID);
						cust_form3.getForm().findField('DEPOSIT_RMB_AVERAGE').setValue(checkedNodes[0].data.DEPOSIT_RMB_AVERAGE);
						cust_form3.getForm().findField('DEPOSIT_RMB_MARGIN').setValue(checkedNodes[0].data.DEPOSIT_RMB_MARGIN);
						cust_form3.getForm().findField('DEPOSIT_RMB_PROPORTION').setValue(checkedNodes[0].data.DEPOSIT_RMB_PROPORTION);
						cust_form3.getForm().findField('DEPOSIT_TRADE_AVERAGE').setValue(checkedNodes[0].data.DEPOSIT_TRADE_AVERAGE);
						cust_form3.getForm().findField('DEPOSIT_TRADE_MARGIN').setValue(checkedNodes[0].data.DEPOSIT_TRADE_MARGIN);
						cust_form3.getForm().findField('DEPOSIT_TRADE_PROPORTION').setValue(checkedNodes[0].data.DEPOSIT_TRADE_PROPORTION);
						cust_form3.getForm().findField('DEPOSIT_OTHER_AVERAGE').setValue(checkedNodes[0].data.DEPOSIT_OTHER_AVERAGE);
						cust_form3.getForm().findField('DEPOSIT_OTHER_MARGIN').setValue(checkedNodes[0].data.DEPOSIT_OTHER_MARGIN);
						cust_form3.getForm().findField('DEPOSIT_OTHER_PROPORTION').setValue(checkedNodes[0].data.DEPOSIT_OTHER_PROPORTION);
						cust_form3.getForm().findField('EXCHANGE_IMMEDIATE_AVERAGE').setValue(checkedNodes[0].data.EXCHANGE_IMMEDIATE_AVERAGE);
						cust_form3.getForm().findField('EXCHANGE_IMMEDIATE_MARGIN').setValue(checkedNodes[0].data.EXCHANGE_IMMEDIATE_MARGIN);
						cust_form3.getForm().findField('EXCHANGE_IMMEDIATE_PROPORTION').setValue(checkedNodes[0].data.EXCHANGE_IMMEDIATE_PROPORTION);
						cust_form3.getForm().findField('EXCHANGE_FORWARD_AVERAGE').setValue(checkedNodes[0].data.EXCHANGE_FORWARD_AVERAGE);
						cust_form3.getForm().findField('EXCHANGE_FORWARD_MARGIN').setValue(checkedNodes[0].data.EXCHANGE_FORWARD_MARGIN);
						cust_form3.getForm().findField('EXCHANGE_FORWARD_PROPORTION').setValue(checkedNodes[0].data.EXCHANGE_FORWARD_PROPORTION);
						cust_form3.getForm().findField('EXCHANGE_INTEREST_AVERAGE').setValue(checkedNodes[0].data.EXCHANGE_INTEREST_AVERAGE);
						cust_form3.getForm().findField('EXCHANGE_INTEREST_MARGIN').setValue(checkedNodes[0].data.EXCHANGE_INTEREST_MARGIN);
						cust_form3.getForm().findField('EXCHANGE_INTEREST_PROPORTION').setValue(checkedNodes[0].data.EXCHANGE_INTEREST_PROPORTION);
						cust_form3.getForm().findField('OPTIONS_TRADING_AVERAGE').setValue(checkedNodes[0].data.OPTIONS_TRADING_AVERAGE);
						cust_form3.getForm().findField('OPTIONS_TRADING_MARGIN').setValue(checkedNodes[0].data.OPTIONS_TRADING_MARGIN);
						cust_form3.getForm().findField('OPTIONS_TRADING_PROPORTION').setValue(checkedNodes[0].data.OPTIONS_TRADING_PROPORTION);
						cust_form3.getForm().findField('TRADE_FINANCING_AVERAGE').setValue(checkedNodes[0].data.TRADE_FINANCING_AVERAGE);
						cust_form3.getForm().findField('TRADE_FINANCING_MARGIN').setValue(checkedNodes[0].data.TRADE_FINANCING_MARGIN);
						cust_form3.getForm().findField('TRADE_FINANCING_PROPORTION').setValue(checkedNodes[0].data.TRADE_FINANCING_PROPORTION);
						cust_form3.getForm().findField('TRADE_FACTORING_AVERAGE').setValue(checkedNodes[0].data.TRADE_FACTORING_AVERAGE);
						cust_form3.getForm().findField('TRADE_FACTORING_MARGIN').setValue(checkedNodes[0].data.TRADE_FACTORING_MARGIN);
						cust_form3.getForm().findField('TRADE_FACTORING_PROPORTION').setValue(checkedNodes[0].data.TRADE_FACTORING_PROPORTION);
						cust_form3.getForm().findField('TRADE_DISCOUNT_AVERAGE').setValue(checkedNodes[0].data.TRADE_DISCOUNT_AVERAGE);
						cust_form3.getForm().findField('TRADE_DISCOUNT_MARGIN').setValue(checkedNodes[0].data.TRADE_DISCOUNT_MARGIN);
						cust_form3.getForm().findField('TRADE_DISCOUNT_PROPORTION').setValue(checkedNodes[0].data.TRADE_DISCOUNT_PROPORTION);
						cust_form3.getForm().findField('TRADE_ACCEPTANCE_AVERAGE').setValue(checkedNodes[0].data.TRADE_ACCEPTANCE_AVERAGE);
						cust_form3.getForm().findField('TRADE_ACCEPTANCE_MARGIN').setValue(checkedNodes[0].data.TRADE_ACCEPTANCE_MARGIN);
						cust_form3.getForm().findField('TRADE_ACCEPTANCE_PROPORTION').setValue(checkedNodes[0].data.TRADE_ACCEPTANCE_PROPORTION);
						cust_form3.getForm().findField('TRADE_CREDIT_AVERAGE').setValue(checkedNodes[0].data.TRADE_CREDIT_AVERAGE);
						cust_form3.getForm().findField('TRADE_CREDIT_MARGIN').setValue(checkedNodes[0].data.TRADE_CREDIT_MARGIN);
						cust_form3.getForm().findField('TRADE_CREDIT_PROPORTION').setValue(checkedNodes[0].data.TRADE_CREDIT_PROPORTION);
						cust_form3.getForm().findField('TRADE_GUARANTEE_AVERAGE').setValue(checkedNodes[0].data.TRADE_GUARANTEE_AVERAGE);
						cust_form3.getForm().findField('TRADE_GUARANTEE_MARGIN').setValue(checkedNodes[0].data.TRADE_GUARANTEE_MARGIN);
						cust_form3.getForm().findField('TRADE_GUARANTEE_PROPORTION').setValue(checkedNodes[0].data.TRADE_GUARANTEE_PROPORTION);
						cust_form3.getForm().findField('LOAN_AVERAGE').setValue(checkedNodes[0].data.LOAN_AVERAGE);
						cust_form3.getForm().findField('LOAN_MARGIN').setValue(checkedNodes[0].data.LOAN_MARGIN);
						cust_form3.getForm().findField('LOAN_PROPORTION').setValue(checkedNodes[0].data.LOAN_PROPORTION);
						cust_form3.getForm().findField('SUIT_PRODUCTS').setValue(checkedNodes[0].data.SUIT_PRODUCTS);
						cust_form3.getForm().findField('WALLETSIZE_PRODUCTS').setValue(checkedNodes[0].data.WALLETSIZE_PRODUCTS);
						cust_form3.getForm().findField('PROVIDE_PRODUCTS').setValue(checkedNodes[0].data.PROVIDE_PRODUCTS);
						
						
						
						cust_form4.getForm().findField('ID').setValue(checkedNodes[0].data.ANAL_ID);
						cust_form4.getForm().findField('PLAN_ID').setValue(checkedNodes[0].data.PLAN_ID);
						cust_form4.getForm().findField('DEPOSIT_RMB_AVERAGE_NY').setValue(checkedNodes[0].data.DEPOSIT_RMB_AVERAGE_NY);
						cust_form4.getForm().findField('DEPOSIT_RMB_MARGIN_NY').setValue(checkedNodes[0].data.DEPOSIT_RMB_MARGIN_NY);
						cust_form4.getForm().findField('DEPOSIT_RMB_PROPORT_NY').setValue(checkedNodes[0].data.DEPOSIT_RMB_PROPORT_NY);
						cust_form4.getForm().findField('DEPOSIT_TRADE_AVERAGE_NY').setValue(checkedNodes[0].data.DEPOSIT_TRADE_AVERAGE_NY);
						cust_form4.getForm().findField('DEPOSIT_TRADE_MARGIN_NY').setValue(checkedNodes[0].data.DEPOSIT_TRADE_MARGIN_NY);
						cust_form4.getForm().findField('DEPOSIT_TRADE_PROPORT_NY').setValue(checkedNodes[0].data.DEPOSIT_TRADE_PROPORT_NY);
						cust_form4.getForm().findField('DEPOSIT_OTHER_AVERAGE_NY').setValue(checkedNodes[0].data.DEPOSIT_OTHER_AVERAGE_NY);
						cust_form4.getForm().findField('DEPOSIT_OTHER_MARGIN_NY').setValue(checkedNodes[0].data.DEPOSIT_OTHER_MARGIN_NY);
						cust_form4.getForm().findField('DEPOSIT_OTHER_PROPORT_NY').setValue(checkedNodes[0].data.DEPOSIT_OTHER_PROPORT_NY);
						cust_form4.getForm().findField('EXCHANGE_IMMEDIATE_AVERAGE_NY').setValue(checkedNodes[0].data.EXCHANGE_IMMEDIATE_AVERAGE_NY);
						cust_form4.getForm().findField('EXCHANGE_IMMEDIATE_MARGIN_NY').setValue(checkedNodes[0].data.EXCHANGE_IMMEDIATE_MARGIN_NY);
						cust_form4.getForm().findField('EXCHANGE_IMMEDIATE_PROPORT_NY').setValue(checkedNodes[0].data.EXCHANGE_IMMEDIATE_PROPORT_NY);
						cust_form4.getForm().findField('EXCHANGE_FORWARD_AVERAGE_NY').setValue(checkedNodes[0].data.EXCHANGE_FORWARD_AVERAGE_NY);
						cust_form4.getForm().findField('EXCHANGE_FORWARD_MARGIN_NY').setValue(checkedNodes[0].data.EXCHANGE_FORWARD_MARGIN_NY);
						cust_form4.getForm().findField('EXCHANGE_FORWARD_PROPORT_NY').setValue(checkedNodes[0].data.EXCHANGE_FORWARD_PROPORT_NY);
						cust_form4.getForm().findField('EXCHANGE_INTEREST_AVERAGE_NY').setValue(checkedNodes[0].data.EXCHANGE_INTEREST_AVERAGE_NY);
						cust_form4.getForm().findField('EXCHANGE_INTEREST_MARGIN_NY').setValue(checkedNodes[0].data.EXCHANGE_INTEREST_MARGIN_NY);
						cust_form4.getForm().findField('EXCHANGE_INTEREST_PROPORT_NY').setValue(checkedNodes[0].data.EXCHANGE_INTEREST_PROPORT_NY);
						cust_form4.getForm().findField('OPTIONS_TRADING_AVERAGE_NY').setValue(checkedNodes[0].data.OPTIONS_TRADING_AVERAGE_NY);
						cust_form4.getForm().findField('OPTIONS_TRADING_MARGIN_NY').setValue(checkedNodes[0].data.OPTIONS_TRADING_MARGIN_NY);
						cust_form4.getForm().findField('OPTIONS_TRADING_PROPORT_NY').setValue(checkedNodes[0].data.OPTIONS_TRADING_PROPORT_NY);
						cust_form4.getForm().findField('TRADE_FINANCING_AVERAGE_NY').setValue(checkedNodes[0].data.TRADE_FINANCING_AVERAGE_NY);
						cust_form4.getForm().findField('TRADE_FINANCING_MARGIN_NY').setValue(checkedNodes[0].data.TRADE_FINANCING_MARGIN_NY);
						cust_form4.getForm().findField('TRADE_FINANCING_PROPORT_NY').setValue(checkedNodes[0].data.TRADE_FINANCING_PROPORT_NY);
						cust_form4.getForm().findField('TRADE_FACTORING_AVERAGE_NY').setValue(checkedNodes[0].data.TRADE_FACTORING_AVERAGE_NY);
						cust_form4.getForm().findField('TRADE_FACTORING_MARGIN_NY').setValue(checkedNodes[0].data.TRADE_FACTORING_MARGIN_NY);
						cust_form4.getForm().findField('TRADE_FACTORING_PROPORT_NY').setValue(checkedNodes[0].data.TRADE_FACTORING_PROPORT_NY);
						cust_form4.getForm().findField('TRADE_DISCOUNT_AVERAGE_NY').setValue(checkedNodes[0].data.TRADE_DISCOUNT_AVERAGE_NY);
						cust_form4.getForm().findField('TRADE_DISCOUNT_MARGIN_NY').setValue(checkedNodes[0].data.TRADE_DISCOUNT_MARGIN_NY);
						cust_form4.getForm().findField('TRADE_DISCOUNT_PROPORT_NY').setValue(checkedNodes[0].data.TRADE_DISCOUNT_PROPORT_NY);
						cust_form4.getForm().findField('TRADE_ACCEPTANCE_AVERAGE_NY').setValue(checkedNodes[0].data.TRADE_ACCEPTANCE_AVERAGE_NY);
						cust_form4.getForm().findField('TRADE_ACCEPTANCE_MARGIN_NY').setValue(checkedNodes[0].data.TRADE_ACCEPTANCE_MARGIN_NY);
						cust_form4.getForm().findField('TRADE_ACCEPTANCE_PROPORT_NY').setValue(checkedNodes[0].data.TRADE_ACCEPTANCE_PROPORT_NY);
						cust_form4.getForm().findField('TRADE_CREDIT_AVERAGE_NY').setValue(checkedNodes[0].data.TRADE_CREDIT_AVERAGE_NY);
						cust_form4.getForm().findField('TRADE_CREDIT_MARGIN_NY').setValue(checkedNodes[0].data.TRADE_CREDIT_MARGIN_NY);
						cust_form4.getForm().findField('TRADE_CREDIT_PROPORT_NY').setValue(checkedNodes[0].data.TRADE_CREDIT_PROPORT_NY);
						cust_form4.getForm().findField('TRADE_GUARANTEE_AVERAGE_NY').setValue(checkedNodes[0].data.TRADE_GUARANTEE_AVERAGE_NY);
						cust_form4.getForm().findField('TRADE_GUARANTEE_MARGIN_NY').setValue(checkedNodes[0].data.TRADE_GUARANTEE_MARGIN_NY);
						cust_form4.getForm().findField('TRADE_GUARANTEE_PROPORT_NY').setValue(checkedNodes[0].data.TRADE_GUARANTEE_PROPORT_NY);
						cust_form4.getForm().findField('LOAN_AVERAGE_NY').setValue(checkedNodes[0].data.LOAN_AVERAGE_NY);
						cust_form4.getForm().findField('LOAN_MARGIN_NY').setValue(checkedNodes[0].data.LOAN_MARGIN_NY);
						cust_form4.getForm().findField('LOAN_PROPORT_NY').setValue(checkedNodes[0].data.LOAN_PROPORT_NY);
						cust_form4.getForm().findField('SUIT_PRODUCTS_NY').setValue(checkedNodes[0].data.SUIT_PRODUCTS);
						cust_form4.getForm().findField('WALLETSIZE_PRODUCTS_NY').setValue(checkedNodes[0].data.WALLETSIZE_PRODUCTS_NY);
						cust_form4.getForm().findField('PROVIDE_PRODUCTS_NY').setValue(checkedNodes[0].data.PROVIDE_PRODUCTS_NY);
						
						/*
						 * 调用计算函数   customerRelationshipSchedule.js
						 */
						creatCalFn();
						transedStore.load({params:{strnum:checkedNodes[0].data.CUST_ID}});
						 if(checkedNodes.length==1){//如果单选，则设置该客户相应的附属属性
							oThisSearchField.customerId=checkedNodes[0].data.CUST_ID;
							oThisSearchField.custType=checkedNodes[0].data.CUST_TYPE;
							oThisSearchField.identType=checkedNodes[0].data.IDENT_TYPE;
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
							oThisSearchField.blID=checkedNodes[0].data.BL_ID;
							oThisSearchField.blname=checkedNodes[0].data.BL_NAME;
							oThisSearchField.entproperty=checkedNodes[0].data.ENT_PROPERTY;
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
    	},{
    		text: '取消',
    		handler:function(){
				_this.oCustomerQueryWindow.hide();
    		}
    	}]	
    });
    
	_this.oCustomerQueryWindow.show();
    return;
    }
    
});
Ext.reg('customerrsquery',Com.yucheng.bcrm.common.CustomerQueryFieldRSu);


