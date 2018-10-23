	Ext.onReady(function() {
		Ext.QuickTips.init();
		var instanceid = curNodeObj.instanceid;
		var id = instanceid.split('_')[1];
		var type = instanceid.split('_')[2];
		var nodeid = curNodeObj.nodeid;
		
		var store;
		var infoForm;
		var infoForm1;
		var infoForm2;
		var infoForm3;
		var infoForm4;
		var infoForm5;
		var infoForm6;
		
		var lookup1Store = new Ext.data.Store({
			restful:true,   
			autoLoad :true,
			proxy : new Ext.data.HttpProxy({
				url :basepath+'/lookup.json?name=F_HTYPE'
			}),
			reader : new Ext.data.JsonReader({
				root : 'JSON'
			}, [ 'key', 'value' ])
		});
		
		var lookup2Store = new Ext.data.Store({
			restful:true,   
			autoLoad :true,
			proxy : new Ext.data.HttpProxy({
				url :basepath+'/lookup.json?name=F_OTYPE'
			}),
			reader : new Ext.data.JsonReader({
				root : 'JSON'
			}, [ 'key', 'value' ])
		});
		
		var lookup3Store = new Ext.data.Store({
			restful:true,   
			autoLoad :true,
			proxy : new Ext.data.HttpProxy({
				url :basepath+'/lookup.json?name=F_UTYPE'
			}),
			reader : new Ext.data.JsonReader({
				root : 'JSON'
			}, [ 'key', 'value' ])
		});
		var lookup4Store = new Ext.data.Store({
			restful:true,   
			autoLoad :true,
			proxy : new Ext.data.HttpProxy({
				url :basepath+'/lookup.json?name=IF_FLAG'
			}),
			reader : new Ext.data.JsonReader({
				root : 'JSON'
			}, [ 'key', 'value' ])
		});
		var lookup5Store = new Ext.data.Store({
			restful:true,   
			autoLoad :true,
			proxy : new Ext.data.HttpProxy({
				url :basepath+'/lookup.json?name=PS_PAYWAY'
			}),
			reader : new Ext.data.JsonReader({
				root : 'JSON'
			}, [ 'key', 'value' ])
		});
		var lookup6Store = new Ext.data.Store({
			restful:true,   
			autoLoad :true,
			proxy : new Ext.data.HttpProxy({
				url :basepath+'/lookup.json?name=CP_USE'
			}),
			reader : new Ext.data.JsonReader({
				root : 'JSON'
			}, [ 'key', 'value' ])
		});
		var lookup7Store = new Ext.data.Store({
			restful:true,   
			autoLoad :true,
			proxy : new Ext.data.HttpProxy({
				url :basepath+'/lookup.json?name=CP_PRODUCT'
			}),
			reader : new Ext.data.JsonReader({
				root : 'JSON'
			}, [ 'key', 'value' ])
		});
		var lookup8Store = new Ext.data.Store({
			restful:true,   
			autoLoad :true,
			proxy : new Ext.data.HttpProxy({
				url :basepath+'/lookup.json?name=CP_CURRENCY'
			}),
			reader : new Ext.data.JsonReader({
				root : 'JSON'
			}, [ 'key', 'value' ])
		});
		var lookup9Store = new Ext.data.Store({
			restful:true,   
			autoLoad :true,
			proxy : new Ext.data.HttpProxy({
				url :basepath+'/lookup.json?name=CP_PRODUCT_P'
			}),
			reader : new Ext.data.JsonReader({
				root : 'JSON'
			}, [ 'key', 'value' ])
		});
		lookup1Store.load();
		lookup2Store.load();
		lookup3Store.load();
		lookup4Store.load();
		lookup5Store.load();
		lookup6Store.load();
		lookup7Store.load();
		lookup8Store.load();
		lookup9Store.load();
		
		var viewContent_store = new Ext.data.Store({
		restful:true,	
        proxy : new Ext.data.HttpProxy({
        	url:basepath+'/wfComment.json?taskNumber='+instanceid.split('_')[1],
        	method:'GET'
        }),
        reader: new Ext.data.JsonReader({
        	totalProperty : 'json.count',
        	root:'json.data'
        }, [
            {name:'username',mapping:'USERNAME'},
            {name:'commentcontent',mapping:'COMMENTCONTENT'},
            {name:'commenttime',mapping:'COMMENTTIME'}
            
           
		])
	});
	viewContent_store.load();
	// 定义自动当前页行号
	var rownum = new Ext.grid.RowNumberer({
		header : 'No.',
		width : 35
	});
	
	// 定义列模型
	var viewContent_cm = new Ext.grid.ColumnModel([rownum, 
     	{ header : '审批人',dataIndex : 'username', sortable : 100, width : 100},
     	{ header : '审批意见',dataIndex : 'commentcontent', sortable : 100, width : 1200},
     	{ header : '审批日期', dataIndex : 'commenttime', sortable : true, width : 200 }
	]);
	// 表格实例
	var viewContent_grid = new Ext.grid.GridPanel({
		id:'viewgrid',
		frame : true,
		height:180,
		autoScroll : true,
		region : 'center', // 和VIEWPORT布局模型对应，充当center区域布局
		store : viewContent_store, // 数据存储
		stripeRows : true, // 斑马线
		cm : viewContent_cm, // 列模型
		loadMask : {
			msg : '正在加载表格数据,请稍等...'
		}
	});
	
	var viewContent_form = new Ext.form.FieldSet({
				xtype:'fieldset',
				title:'历史意见列表',
				titleCollapse : true,
				collapsed :false,
				anchor : '98%',
				items:[{
					layout : 'column',
					items:[{
						layout : 'form',
						columnWidth : 1,
						items:[viewContent_grid]
					}]
				}]
			});
	
	   if(type == '0'){//旧户
		   store = new Ext.data.Store({
			   restful:true,	
			   proxy : new Ext.data.HttpProxy(
					   {
						   url:basepath+'/ocrmFInterviewTask.json'
					   }),
					   reader: new Ext.data.JsonReader({
						   root : 'json.data'
					   }, [{name:'CUST_ID'},
					       {name:'CUST_NAME'},
					       {name:'MGR_ID'},
					       {name:'MGR_NAME'},
					       {name:'INTERVIEWEE_NAME'},
					       {name:'INTERVIEWEE_POST'},
					       {name:'INTERVIEWEE_PHONE'},
					       {name:'JOIN_PERSON'},
					       {name:'TASK_TYPE'},
					       {name:'VISIT_TYPE_ORA'},
					       {name:'VISIT_TIME'},
					       {name:'CALL_TIME'},
					       //旧客户拜访明细信息  
			              {name:'CUS_STATUS_ORA'},//,text:'客户运营状况  ',hidden:true,translateType:'CUS_STATUS'},   
			              {name:'ISBUSCHANGE_ORA'},//,text:'主营业务是否变更  ',hidden:true,translateType:'IF_FLAG'},   
			              {name:'BUS_EXPLAIN'},//,text:'主营业务变更说明  ',hidden:true},   
			              {name:'ISREVCHANGE_ORA'},//,text:'营收是否大幅变化  ',hidden:true,translateType:'IF_FLAG'},   
			              {name:'REV_EXPLAIN'},//,text:'营收变化说明  ',hidden:true},   
			              {name:'ISPROCHANGE_ORA'},//,text:'获利率是否大幅变化  ',hidden:true,translateType:'IF_FLAG'},   
			              {name:'PRO_EXPLAIN'},//,text:'获利率变化说明 ',hidden:true},   
			              {name:'ISSUPCHANGE_ORA'},//,text:'主要供应商是否调整  ',hidden:true,translateType:'IF_FLAG'},   
			              {name:'SUP_EXPLAIN'},//,text:'供应商调整说明  ',hidden:true},   
			              {name:'ISPURCHANGE_ORA'},//,text:'主要买方是否调整  ',hidden:true,translateType:'IF_FLAG'},   
			              {name:'PUR_EXPLAIN'},//,text:'买方调整说明  ',hidden:true},   
			              {name:'ISEQUCHANGE_ORA'},//,text:'股权结构是否变更  ',hidden:true,translateType:'IF_FLAG'},   
			              {name:'EQU_EXPLAIN'},//,text:'股权结构说明  ',hidden:true},   
			              {name:'ISOPCCHANGE_ORA'},//,text:'经营层是否有变更  ',hidden:true,translateType:'IF_FLAG'},   
			              {name:'OPC_EXPLAIN'},//,text:'经营层变更说明  ',hidden:true},   
			              {name:'ISCOLCHANGE_ORA'},//,text:'担保品状况  ',hidden:true,translateType:'ISCOLCHANGE'},   
			              {name:'COL_EXPLAIN'},//,text:'担保品说明  ',hidden:true},   
			              {name:'ISSYMCHANGE_ORA'},//,text:'与银行合作状况是否变化  ',hidden:true},   
			              {name:'SYM_EXPLAIN'},//,text:'与银行合作状况情况说明  ',hidden:true},   
			              {name:'MARK_PRODUCT'},//,text:'拟营销产品  ',hidden:true},
			              //旧户拜访目的
			              {name:'PUR_CUST2CALL'},//,text:'正常客户定期回访  ',hidden:true,translateType:'IF_FLAG'},   
			              {name:'PUR_SEEK2COLL'},//,text:'勘察担保品  ',hidden:true,translateType:'IF_FLAG'},   
			              {name:'PUR_WARN2CALL'},//,text:'预警客户定期回访  ',hidden:true,translateType:'IF_FLAG'},   
			              {name:'PUR_DEFEND2CALL'},//,text:'年审/条件变更风管部协访  ',hidden:true,translateType:'IF_FLAG'},   
			              {name:'PUR_MARK2PRO'},//,text:'营销新产品  ',hidden:true,translateType:'IF_FLAG'},   
			              {name:'PUR_RISK2CALL'},//,text:'授信风险增加临时拜访  ',hidden:true,translateType:'IF_FLAG'} 
			             
			              {name:'RES_FOLLOWUP'},//,text:'跟进事项',hidden:true,xtype:'textarea'},
			              {name:'RES_OTHERINFO'},//,text:'其他补充说明',hidden:true,xtype:'textarea'},
			              {name:'MARK_RESULT_ORA'},//,text:'拜访结果',hidden:true,translateType:'VISIT_RESULT_QS'},
			              {name:'MARK_REFUSEREASON_ORA'},//,text:'拒绝原因',hidden:true,translateType:'MARK_REFUSEREASON'},
//			              {name:'MARK_RESULT_OLD',text:'营销结果',hidden:true,translateType:'VISIT_RESULT_QS_OLD'},
//			              {name:'MARK_REFUSEREASON_OLD',text:'拒绝原因',hidden:true,translateType:'MARK_REASON_OLD'},
			              {name:'CALL_SPENDTIME'},//,text:'本次拜访花费时间(小时)',hidden:true},
			              {name:'CALL_NEXTTIME'},//,text:'预约下次拜访时间 ',dataType:'date',hidden:true},
			              {name:'ORDER_STATE'}
					       ]
					   )
		   });
		   infoForm = new Ext.FormPanel( {
			   frame : true,
			   items : [ {
				   layout : 'column',
				   items : [{
					   layout : 'form',columnWidth : .5,labelWidth:100,
					   items : [ {name : 'CUST_ID',xtype : 'textfield',fieldLabel : '客户编号',labelStyle : 'text-align:right;',disabled:true,anchor : '95%'},
					             {name : 'CUST_NAME',xtype : 'textfield',fieldLabel : '客户名称',labelStyle : 'text-align:right;',disabled:true,anchor : '95%'},
					             {name : 'MGR_NAME',xtype : 'textfield',fieldLabel : '客户经理名称',labelStyle : 'text-align:right;',disabled:true,anchor : '95%'},
					             {name : 'VISIT_TIME',xtype : 'datefield',format : 'Y-m-d',fieldLabel : '预约拜访日期',labelStyle : 'text-align:right;',disabled:true,anchor : '95%'},
					             {name : 'CALL_TIME',xtype : 'datefield',format : 'Y-m-d',fieldLabel : '实际拜访日期',labelStyle : 'text-align:right;',disabled:true,anchor : '95%'},
					             
					             {name : 'CUS_STATUS_ORA',xtype : 'textfield',fieldLabel : '客户运营状况',labelStyle : 'text-align:right;',disabled:true,anchor : '95%'},
					             {name : 'ISBUSCHANGE_ORA',xtype : 'textfield',fieldLabel : '主营业务是否变更',labelStyle : 'text-align:right;',disabled:true,anchor : '95%'},
					             {name : 'BUS_EXPLAIN',xtype : 'textfield',fieldLabel : '主营业务变更说明',labelStyle : 'text-align:right;',disabled:true,anchor : '95%'},
					             {name : 'ISREVCHANGE_ORA',xtype : 'textfield',fieldLabel : '营收是否大幅变化',labelStyle : 'text-align:right;',disabled:true,anchor : '95%'},
					             {name : 'REV_EXPLAIN',xtype : 'textfield',fieldLabel : '营收变化说明',labelStyle : 'text-align:right;',disabled:true,anchor : '95%'},
					             {name : 'ISPROCHANGE_ORA',xtype : 'textfield',fieldLabel : '获利率是否大幅变化 ',labelStyle : 'text-align:right;',disabled:true,anchor : '95%'},
					             {name : 'PRO_EXPLAIN',xtype : 'textfield',fieldLabel : '获利率变化说明',labelStyle : 'text-align:right;',disabled:true,anchor : '95%'},
					             {name : 'ISSUPCHANGE_ORA',xtype : 'textfield',fieldLabel : '主要供应商是否调整',labelStyle : 'text-align:right;',disabled:true,anchor : '95%'},
					             {name : 'PUR_EXPLAIN',xtype : 'textfield',fieldLabel : '买方调整说明',labelStyle : 'text-align:right;',disabled:true,anchor : '95%'},
					             {name : 'ISEQUCHANGE_ORA',xtype : 'textfield',fieldLabel : '股权结构是否变更',labelStyle : 'text-align:right;',disabled:true,anchor : '95%'},
					             {name : 'EQU_EXPLAIN',xtype : 'textfield',fieldLabel : '股权结构说明',labelStyle : 'text-align:right;',disabled:true,anchor : '95%'},
					             {name : 'ISOPCCHANGE_ORA',xtype : 'textfield',fieldLabel : '经营层是否有变更',labelStyle : 'text-align:right;',disabled:true,anchor : '95%'},
					             {name : 'OPC_EXPLAIN',xtype : 'textfield',fieldLabel : '经营层变更说明',labelStyle : 'text-align:right;',disabled:true,anchor : '95%'},
					             {name : 'MARK_RESULT_ORA',xtype : 'textfield',fieldLabel : '拜访结果',labelStyle : 'text-align:right;',disabled:true,anchor : '95%'},
					             {name : 'CALL_NEXTTIME',xtype : 'datefield',format : 'Y-m-d',fieldLabel : '预约下次拜访时间 ',labelStyle : 'text-align:right;',disabled:true,anchor : '95%'}
					             ]
				   },{
					   layout : 'form',columnWidth : .5,labelWidth:100,
					   items : [{name : 'ORDER_STATE',xtype : 'textfield',fieldLabel : '预约状态',labelStyle : 'text-align:right;',disabled:true,anchor : '95%'},
					   			{name : 'INTERVIEWEE_NAME',xtype : 'textfield',fieldLabel : '受访人名称',labelStyle : 'text-align:right;',disabled:true,anchor : '95%'},
					            {name : 'INTERVIEWEE_POST',xtype : 'textfield',fieldLabel : '受访人职位',labelStyle : 'text-align:right;',disabled:true,anchor : '95%'},
					            {name : 'INTERVIEWEE_PHONE',xtype : 'textfield',fieldLabel : '受访人电话',labelStyle : 'text-align:right;',disabled:true,anchor : '95%'},
					            {name : 'VISIT_TYPE_ORA',xtype : 'textfield',fieldLabel : '拜访类型',labelStyle : 'text-align:right;',disabled:true,anchor : '95%'},
					            {name : 'JOIN_PERSON',xtype : 'textarea',fieldLabel : '本次参与人员',labelStyle : 'text-align:right;',disabled:true,anchor : '95%'},
					            {name : 'ISCOLCHANGE_ORA',xtype : 'textfield',fieldLabel : '担保品状况',labelStyle : 'text-align:right;',disabled:true,anchor : '95%'},
					            {name : 'COL_EXPLAIN',xtype : 'textfield',fieldLabel : '担保品说明',labelStyle : 'text-align:right;',disabled:true,anchor : '95%'},
					            {name : 'ISSYMCHANGE_ORA',xtype : 'textfield',fieldLabel : '与银行合作状况是否变化',labelStyle : 'text-align:right;',disabled:true,anchor : '95%'},
					            {name : 'SYM_EXPLAIN',xtype : 'textfield',fieldLabel : '与银行合作状况情况说明 ',labelStyle : 'text-align:right;',disabled:true,anchor : '95%'},
					            {name : 'MARK_PRODUCT',xtype : 'textfield',fieldLabel : '拟营销产品',labelStyle : 'text-align:right;',disabled:true,anchor : '95%'},
//					            {name : 'PUR_CUST2CALL_ORA',xtype : 'textfield',fieldLabel : '正常客户定期回访  ',labelStyle : 'text-align:right;',disabled:true,anchor : '95%'},
//					            {name : 'PUR_SEEK2COLL_ORA',xtype : 'textfield',fieldLabel : '勘察担保品',labelStyle : 'text-align:right;',disabled:true,anchor : '95%'},
//					            {name : 'PUR_WARN2CALL_ORA',xtype : 'textfield',fieldLabel : '预警客户定期回访',labelStyle : 'text-align:right;',disabled:true,anchor : '95%'},
//					            {name : 'PUR_DEFEND2CALL_ORA',xtype : 'textfield',fieldLabel : '年审/条件变更风管部协访',labelStyle : 'text-align:right;',disabled:true,anchor : '95%'},
//					            {name : 'PUR_MARK2PRO_ORA',xtype : 'textfield',fieldLabel : '营销新产品 ',labelStyle : 'text-align:right;',disabled:true,anchor : '95%'},
//					            {name : 'PUR_RISK2CALL_ORA',xtype : 'textfield',fieldLabel : '授信风险增加临时拜访',labelStyle : 'text-align:right;',disabled:true,anchor : '95%'},
					            {name : 'MARK_REFUSEREASON_ORA',xtype : 'textfield',fieldLabel : '拒绝原因',labelStyle : 'text-align:right;',disabled:true,anchor : '95%'},
					            {name : 'CALL_SPENDTIME',xtype : 'textfield',fieldLabel : '本次拜访花费时间(小时)',labelStyle : 'text-align:right;',disabled:true,anchor : '95%'},
					            {name : 'RES_FOLLOWUP',xtype : 'textarea',fieldLabel : '跟进事项',labelStyle : 'text-align:right;',disabled:true,anchor : '95%'},
					            {name : 'RES_OTHERINFO',xtype : 'textarea',fieldLabel : '其他补充说明',labelStyle : 'text-align:right;',disabled:true,anchor : '95%'}
					            ]
				   }]
			   }]
		   });
		   
		   var jt_form = new Ext.form.FieldSet({
				xtype:'fieldset',
				title:'拜访目的',
				titleCollapse : true,
				collapsed :false,
				anchor : '98%',
				items:[{
					layout : 'column',
					items:[{
						layout : 'form',
						columnWidth : .43,
						items:[
						       {id:'p1',fieldLabel:'正常客户定期回访',xtype:'checkbox',labelStyle:'text-align:right;',anchor:'95%',name:'PUR_CUST2CALL'},
						       {id:'p2',fieldLabel:'勘察担保品',xtype:'checkbox',labelStyle:'text-align:right;',anchor:'95%',name:'PUR_SEEK2COLL'},
						       {id:'p3',fieldLabel:'预警客户定期回访',xtype:'checkbox',labelStyle:'text-align:right;',anchor:'95%',name:'PUR_WARN2CALL'}
						       ]
					},{
						layout : 'form',
						columnWidth : .43,
						labelWidth:200,
						items:[
						       {id:'p4',fieldLabel:'年审/条件变更风管部协访',xtype:'checkbox',labelWidth:150,anchor:'95%',name:'PUR_DEFEND2CALL'},
						       {id:'p5',fieldLabel:'营销新产品',xtype:'checkbox',labelStyle:'text-align:right;',anchor:'95%',name:'PUR_MARK2PRO'},
						       {id:'p6',fieldLabel:'授信风险增加临时拜访',xtype:'checkbox',labelStyle:'text-align:right;',anchor:'95%',name:'PUR_RISK2CALL'}
						       ]
					}]
				}]
			});
			
			
	   }
	   if(type == '1' || type == '01'){//新户
		   store = new Ext.data.Store({
			   restful:true,	
			   proxy : new Ext.data.HttpProxy(
					   {
						   url:basepath+'/ocrmFInterviewTask.json'
					   }),
					   reader: new Ext.data.JsonReader({
						   root : 'json.data'
					   }, [{name:'CUST_ID'},
					       {name:'CUST_NAME'},
					       {name:'MGR_ID'},
					       {name:'MGR_NAME'},
					       {name:'INTERVIEWEE_NAME'},
					       {name:'INTERVIEWEE_POST'},
					       {name:'INTERVIEWEE_PHONE'},
					       {name:'JOIN_PERSON'},
					       {name:'TASK_TYPE'},
					       {name:'VISIT_TYPE_ORA'},
					       {name:'VISIT_TIME'},
					       {name:'CALL_TIME'},
					       {name:'CUS_DOMICILE'},//,text:'企业注册地',hidden:true},
			               {name:'CUS_NATURE_ORA'},//,text:'企业性质',hidden:true,translateType:"CUS_NATURE"},
			               {name:'CUS_LEGALPERSON'},//,text:'法人代表',hidden:true},
			               {name:'CUS_REGTIME'},//,text:'企业成立时间',hidden:true,dataType:'date'},
			               {name:'CUS_CNTPEOPLE'},//,text:'员工人数',hidden:true,vtype:'number'},
			               {name:'CUS_ONMARK_ORA'},//,text:'是否上市',translateType:'IF_FLAG',hidden:true},
			               {name:'CUS_ONMARKPLACE_ORA'},//,text:上市地点',translateType:'IF_FLAG',hidden:true},
			               {name:'CUS_OWNBUSI_ORA'},//,text:'所属行业 ',hidden:true,translateType:'CUS_OWNBUSI'},
			               {name:'CUS_BUSISTATUS_ORA'},//,text:'行业地位',hidden:true,translateType:'CUS_BUSISTATUS'},
			               {name:'CUS_OPERATEPERSON'},//,text:'经营负责人',hidden:true},
			               {name:'CUS_ACCOUNTPERSON'},//,text:'财务负责人',hidden:true},
			               {name:'CUS_MAJORPRODUCT'},//,text:'主要产品',hidden:true},
			               {name:'CUS_MAJORRIVAL'},//,text:'主要竞争对手',hidden:true},
			               
			               {name:'DCRB_MAJORSHOLDER'},
			               {name:'DCRB_FLOW'},
			               {name:'DCRB_FIXEDASSETS'},
			               {name:'DCRB_PROFIT'},
			               {name:'DCRB_SYMBIOSIS'},
			               {name:'DCRB_OTHERTRADE'},
			               {name:'DCRB_MYSELFTRADE'},
			               
			               {name:'RES_CUSTSOURCE_ORA'},//,text:'客户来源',hidden:true,translateType:'RES_CUSTSOURCE'},
			               {name:'RES_CASEBYPERSON'},//,text:'转介人姓名',hidden:true},
			               {name:'RES_CASEBYPTEL'},//,text:'转介人电话',hidden:true,vtype:'mobile'},
			               
			               {name:'RES_FOLLOWUP'},//,text:'跟进事项',hidden:true,xtype:'textarea'},
			               {name:'RES_OTHERINFO'},//,text:'其他补充说明',hidden:true,xtype:'textarea'},
			               {name:'MARK_RESULT_ORA'},//,text:'拜访结果',hidden:true,translateType:'VISIT_RESULT_QS'},
			               {name:'MARK_REFUSEREASON_ORA'},//,text:'拒绝原因',hidden:true,translateType:'MARK_REFUSEREASON'},
			               
//			               {name:'MARK_RESULT_OLD'},//,text:'营销结果',hidden:true,translateType:'VISIT_RESULT_QS_OLD'},
//			               {name:'MARK_REFUSEREASON_OLD'},//,text:'拒绝原因',hidden:true,translateType:'MARK_REASON_OLD'},
			               
			               {name:'CALL_SPENDTIME'},//,text:'本次拜访花费时间(小时)',hidden:true},
			               {name:'CALL_NEXTTIME'},//,text:'预约下次拜访时间 ',dataType:'date',hidden:true},
			                {name:'ORDER_STATE'}
					       ]
					   )
		   });
		   infoForm = new Ext.FormPanel( {
			   frame : true,
			   items : [ {
				   layout : 'column',
				   items : [{
					   layout : 'form',columnWidth : .5,labelWidth:100,
					   items : [ {name : 'CUST_ID',xtype : 'textfield',fieldLabel : '客户编号',labelStyle : 'text-align:right;',disabled:true,anchor : '95%'},
					             {name : 'CUST_NAME',xtype : 'textfield',fieldLabel : '客户名称',labelStyle : 'text-align:right;',disabled:true,anchor : '95%'},
					             {name : 'MGR_NAME',xtype : 'textfield',fieldLabel : '客户经理名称',labelStyle : 'text-align:right;',disabled:true,anchor : '95%'},
					             {name : 'VISIT_TIME',xtype : 'datefield',format : 'Y-m-d',fieldLabel : '预约拜访日期',labelStyle : 'text-align:right;',disabled:true,anchor : '95%'},
					             {name : 'CALL_TIME',xtype : 'datefield',format : 'Y-m-d',fieldLabel : '实际拜访日期',labelStyle : 'text-align:right;',disabled:true,anchor : '95%'},
					             {name : 'CUS_DOMICILE',xtype : 'textfield',fieldLabel : '企业注册地',labelStyle : 'text-align:right;',disabled:true,anchor : '95%'},
					             {name : 'CUS_NATURE_ORA',xtype : 'textfield',fieldLabel : '企业性质',labelStyle : 'text-align:right;',disabled:true,anchor : '95%'},
					             {name : 'CUS_LEGALPERSON',xtype : 'textfield',fieldLabel : '法人代表',labelStyle : 'text-align:right;',disabled:true,anchor : '95%'},
					             {name : 'CUS_REGTIME',xtype : 'datefield',format : 'Y-m-d',fieldLabel : '企业成立时间',labelStyle : 'text-align:right;',disabled:true,anchor : '95%'},
					             {name : 'CUS_CNTPEOPLE',xtype : 'textfield',fieldLabel : '员工人数',labelStyle : 'text-align:right;',disabled:true,anchor : '95%'},
					             {name : 'CUS_ONMARK_ORA',xtype : 'textfield',fieldLabel : '是否上市',labelStyle : 'text-align:right;',disabled:true,anchor : '95%'},
					             {name : 'CUS_ONMARKPLACE_ORA',xtype : 'textfield',fieldLabel : '上市地点',labelStyle : 'text-align:right;',disabled:true,anchor : '95%'},
					             {name : 'CUS_OWNBUSI_ORA',xtype : 'textfield',fieldLabel : '所属行业',labelStyle : 'text-align:right;',disabled:true,anchor : '95%'}
					             ]
				   },{
					   layout : 'form',columnWidth : .5,labelWidth:100,
					   items : [{name : 'ORDER_STATE',xtype : 'textfield',fieldLabel : '预约状态',labelStyle : 'text-align:right;',disabled:true,anchor : '95%'},
					   			{name : 'INTERVIEWEE_NAME',xtype : 'textfield',fieldLabel : '受访人名称',labelStyle : 'text-align:right;',disabled:true,anchor : '95%'},
					            {name : 'INTERVIEWEE_POST',xtype : 'textfield',fieldLabel : '受访人职位',labelStyle : 'text-align:right;',disabled:true,anchor : '95%'},
					            {name : 'INTERVIEWEE_PHONE',xtype : 'textfield',fieldLabel : '受访人电话',labelStyle : 'text-align:right;',disabled:true,anchor : '95%'},
					            {name : 'VISIT_TYPE_ORA',xtype : 'textfield',fieldLabel : '拜访类型',labelStyle : 'text-align:right;',disabled:true,anchor : '95%'},
					            {name : 'JOIN_PERSON',xtype : 'textarea',fieldLabel : '本次参与人员',labelStyle : 'text-align:right;',disabled:true,anchor : '95%'},
					            {name : 'CUS_BUSISTATUS_ORA',xtype : 'textfield',fieldLabel : '行业地位',labelStyle : 'text-align:right;',disabled:true,anchor : '95%'},
					            {name : 'CUS_OPERATEPERSON',xtype : 'textfield',fieldLabel : '经营负责人',labelStyle : 'text-align:right;',disabled:true,anchor : '95%'},
					            {name : 'CUS_ACCOUNTPERSON',xtype : 'textfield',fieldLabel : '财务负责人',labelStyle : 'text-align:right;',disabled:true,anchor : '95%'},
					            {name : 'CUS_MAJORPRODUCT',xtype : 'textfield',fieldLabel : '主要产品',labelStyle : 'text-align:right;',disabled:true,anchor : '95%'},
					            {name : 'CUS_MAJORRIVAL',xtype : 'textfield',fieldLabel : '主要竞争对手',labelStyle : 'text-align:right;',disabled:true,anchor : '95%'}
					            ]
				   }]
			   },{name : 'DCRB_FLOW',xtype : 'textarea',fieldLabel : '行业产品及生产流程补充说明',labelStyle : 'text-align:right;',disabled:true,anchor : '95%'}]
		   });
		   infoForm1 = new Ext.FormPanel( {
			   frame : true,
			   items : [ 
			   	 {name : 'DCRB_MYSELFTRADE',xtype : 'textarea',fieldLabel : '本行往来业务补充说明',labelStyle : 'text-align:right;',disabled:true,anchor : '95%'},
			   	 {name : 'RES_OTHERINFO',xtype : 'textarea',fieldLabel : '其他补充说明',labelStyle : 'text-align:right;',disabled:true,anchor : '95%'}
			   	,{
				   layout : 'column',
				   items : [{
					   layout : 'form',columnWidth : .5,labelWidth:100,
					   items : [ 
					             {name : 'RES_FOLLOWUP',xtype : 'textarea',fieldLabel : '跟进事项',labelStyle : 'text-align:right;',disabled:true,anchor : '95%'},
					   			 {name : 'RES_CUSTSOURCE_ORA',xtype : 'textfield',fieldLabel : '客户来源',labelStyle : 'text-align:right;',disabled:true,anchor : '95%'},
					             {name : 'RES_CASEBYPERSON',xtype : 'textfield',fieldLabel : '转介人姓名',labelStyle : 'text-align:right;',disabled:true,anchor : '95%'},
					             {name : 'RES_CASEBYPTEL',xtype : 'textfield',fieldLabel : '转介人电话',labelStyle : 'text-align:right;',disabled:true,anchor : '95%'}
					             ]
				   },{
					   layout : 'form',columnWidth : .5,labelWidth:100,
					   items : [
					            {name : 'MARK_RESULT_ORA',xtype : 'textfield',fieldLabel : '拜访结果',labelStyle : 'text-align:right;',disabled:true,anchor : '95%'},
					            {name : 'MARK_REFUSEREASON_ORA',xtype : 'textfield',fieldLabel : '拒绝原因',labelStyle : 'text-align:right;',disabled:true,anchor : '95%'},
					            {name : 'CALL_SPENDTIME',xtype : 'textfield',fieldLabel : '本次拜访花费时间(小时)',labelStyle : 'text-align:right;',disabled:true,anchor : '95%'},
					            {name : 'CALL_NEXTTIME',xtype : 'datefield',format : 'Y-m-d',fieldLabel : '预约下次拜访时间',labelStyle : 'text-align:right;',disabled:true,anchor : '95%'}
					            ]
				   }]
			   }]
		   });
		    infoForm2 = new Ext.FormPanel( {
			   frame : true,
			   items : [ {
				   layout : 'column',
				   items : [{
					   layout : 'form',labelWidth:100,columnWidth : .99,
					   items : [ 
					             {name : 'DCRB_MAJORSHOLDER',xtype : 'textarea',fieldLabel : '主要股东补充说明',labelStyle : 'text-align:right;',disabled:true,anchor : '95%'}
					            ]
				   }]
			   }]
		   });
		    
		    infoForm3 = new Ext.FormPanel( {
			   frame : true,
			   items : [ {
				   layout : 'column',
				   items : [{
					   layout : 'form',labelWidth:100,columnWidth : .99,
					   items : [ 
					             {name : 'DCRB_FIXEDASSETS',xtype : 'textarea',fieldLabel : '固定资产补充说明',labelStyle : 'text-align:right;',disabled:true,anchor : '95%'}
					            ]
				   }]
			   }]
		   });
		    infoForm4 = new Ext.FormPanel( {
			   frame : true,
			   items : [ {
				   layout : 'column',
				   items : [{
					   layout : 'form',labelWidth:100,columnWidth : .99,
					   items : [ 
					             {name : 'DCRB_PROFIT',xtype : 'textarea',fieldLabel : '获利补充说明',labelStyle : 'text-align:right;',disabled:true,anchor : '95%'}
					            ]
				   }]
			   }]
		   });
		   infoForm5 = new Ext.FormPanel( {
			   frame : true,
			   items : [ {
				   layout : 'column',
				   items : [{
					   layout : 'form',labelWidth:100,columnWidth : .99,
					   items : [ 
					             {name : 'DCRB_SYMBIOSIS',xtype : 'textarea',fieldLabel : '上下游合作交易情况补充说明',labelStyle : 'text-align:right;',disabled:true,anchor : '95%'}
					            ]
				   }]
			   }]
		   });
		   infoForm6 = new Ext.FormPanel( {
			   frame : true,
			   items : [ {
				   layout : 'column',
				   items : [{
					   layout : 'form',labelWidth:100,columnWidth : .99,
					   items : [ 
					             {name : 'DCRB_OTHERTRADE',xtype : 'textarea',fieldLabel : '他行往来情况补充说明',labelStyle : 'text-align:right;',disabled:true,anchor : '95%'}
					            ]
				   }]
			   }]
		   });
	   }
	 
	   var newSm = new Ext.grid.CheckboxSelectionModel();
	   var editRrownum = new Ext.grid.RowNumberer({
	   	  header : 'No.',
	   	  width : 28
	   });
	   var newCm =  new Ext.grid.ColumnModel([editRrownum,newSm,	
	                                        {header : 'ID', dataIndex : 'ID',sortable : true,width : 120,hidden:true},
	                                        {header:'TASK_NUMBER',dataIndex:'TASK_NUMBER',sortable:true,width:120,hidden:true},
	                                        {header:'出资人',dataIndex:'M_SPONSOR',sortable:true,width:120},
	                                        {header:'出资比例(%)',dataIndex:'M_RATIO',sortable:true,width:120},
	                                        {header:'出资金额(人民币/千元)',dataIndex:'M_MONEY',sortable:true,width:120}
	           	                                     ]); 
	   var newPanelStroe_1 = new Ext.data.Store({
	   	restful : true,
	   	proxy : new Ext.data.HttpProxy(
	   			{
	   				url : basepath + '/ocrmFInterviewShareholder.json' 
	   			}),
	   			reader : new Ext.data.JsonReader( {
	   				root : 'json.data'
	   			}, [{name:'ID'},
	   			    {name:'TASK_NUMBER'},
	   			    {name:'M_SPONSOR'},
	   			    {name:'M_RATIO'},
	   			    {name:'M_MONEY'}
	   			     ])
	   });	
	   var newPanel_1 = new Ext.grid.GridPanel({
			title : '主要股东及持股比例',
			autoScroll: true,
			height:200,
			store : newPanelStroe_1,
			frame : true,
			sm:newSm,
			cm : newCm,
			loadMask : {
				msg : '正在加载表格数据,请稍等...'
			}
		});
	 //==================主要固定资产==========begin===================================//
	   var newSm_2 = new Ext.grid.CheckboxSelectionModel();
	   var editRrownum = new Ext.grid.RowNumberer({
	   	  header : 'No.',
	   	  width : 28
	   });

	   var newCm_2 =  new Ext.grid.ColumnModel([editRrownum,newSm_2,	
	                                        {header : 'ID', dataIndex : 'ID',sortable : true,width : 120,hidden:true},
	                                        {header:'TASK_NUMBER',dataIndex:'TASK_NUMBER',sortable:true,width:120,hidden:true},
	                                        {header:'房产类型',dataIndex:'F_HTYPE',sortable:true,width:120,renderer:function(value){
	                       					 for(var i=0;i< lookup1Store.data.length;i++){
	                     						if(lookup1Store.data.items[i].data.key==value){
	                     						   return lookup1Store.data.items[i].data.value
	                     						}
	                     					}
	                     					}},
	                                        {header:'持有类型',dataIndex:'F_OTYPE',sortable:true,width:120,renderer:function(value){
		                       					 for(var i=0;i< lookup2Store.data.length;i++){
			                     						if(lookup2Store.data.items[i].data.key==value){
			                     						   return lookup2Store.data.items[i].data.value
			                     						}
			                     					}
			                     					}},
	                                        {header:'面积（平方米）',dataIndex:'F_AREA',sortable:true,width:120},
	                                        {header:'使用状况',dataIndex:'F_UTYPE',sortable:true,width:120,renderer:function(value){
		                       					 for(var i=0;i< lookup3Store.data.length;i++){
			                     						if(lookup3Store.data.items[i].data.key==value){
			                     						   return lookup3Store.data.items[i].data.value
			                     						}
			                     					}
			                     					}},
	                                        {header:'估价(人民币/千元)',dataIndex:'F_ASSESS',sortable:true,width:120},
	                                        {header:'备注',dataIndex:'F_MEMO',sortable:true,width:120}
	           	                                     ]); 
	   var newPanelStroe_2 = new Ext.data.Store({
	   	restful : true,
	   	proxy : new Ext.data.HttpProxy(
	   			{
	   				url : basepath + '/ocrmFInterviewFixedasset.json' 
	   			}),
	   			reader : new Ext.data.JsonReader( {
	   				root : 'json.data'
	   			}, [{name:'ID'},
	   			    {name:'TASK_NUMBER'},
	   			    {name:'F_HTYPE'},
	   			    {name:'F_OTYPE'},
	   			    {name:'F_AREA'},
	   			    {name:'F_UTYPE'},
	   			    {name:'F_ASSESS'},
	   			    {name:'F_MEMO'}
	   			     ])
	   });	
	   var newPanel_2 = new Ext.grid.GridPanel({
	   	title : '主要固定资产',
	   	autoScroll: true,
	   	height:200,
	   	store : newPanelStroe_2,
	   	frame : true,
	   	sm:newSm_2,
	   	cm : newCm_2,
	   	loadMask : {
	   		msg : '正在加载表格数据,请稍等...'
	   	}
	   });
	   //======================主要固定资产======end===========================================//

	   ///////////////////////营收，获利情况//////////begin//////////////////////////////////////////////
	   var newSm_3 = new Ext.grid.CheckboxSelectionModel();
	   var editRrownum = new Ext.grid.RowNumberer({
	   	  header : 'No.',
	   	  width : 28
	   });

	   var newCm_3 =  new Ext.grid.ColumnModel([editRrownum,newSm_3,	
	                                        {header : 'ID', dataIndex : 'ID',sortable : true,width : 120,hidden:true},
	                                        {header:'TASK_NUMBER',dataIndex:'TASK_NUMBER',sortable:true,width:120,hidden:true},
	                                        {header:'开始年份',dataIndex:'P_YEARS',sortable:true,width:120},
	                                        {header:'结束年份',dataIndex:'P_YEARS_END',sortable:true,width:120},
	                                        {header:'营收(人民币/千元)',dataIndex:'P_REVENUE',sortable:true,width:120},
	                                        {header:'毛利率(%)',dataIndex:'P_GROSS',sortable:true,width:120},
	                                        {header:'税后净利率(%)',dataIndex:'P_PNET',sortable:true,width:120},
	                                        {header:'备注',dataIndex:'P_MEMO',sortable:true,width:120}
	           	                                     ]); 
	   var newPanelStroe_3 = new Ext.data.Store({
	   	restful : true,
	   	proxy : new Ext.data.HttpProxy(
	   			{
	   				url : basepath + '/ocrmFInterviewProfit.json' 
	   			}),
	   			reader : new Ext.data.JsonReader( {
	   				root : 'json.data'
	   			}, [{name:'ID'},
	   			    {name:'TASK_NUMBER'},
	   			    {name:'P_YEARS'},
	   			    {name:'P_YEARS_END'},
	   			    {name:'P_REVENUE'},
	   			    {name:'P_GROSS'},
	   			    {name:'P_PNET'},
	   			    {name:'P_MEMO'}
	   			     ])
	   });	

	   var newPanel_3 = new Ext.grid.GridPanel({
	   	title : '营收,获利情况',
	   	autoScroll: true,
	   	height:200,
	   	store : newPanelStroe_3,
	   	frame : true,
	   	sm:newSm_3,
	   	cm : newCm_3,
	   	loadMask : {
	   		msg : '正在加载表格数据,请稍等...'
	   	}
	   });
	   /////////////////////营收，获利情况//////end///////////////////////////////////////////////////

	   //====================原材料采购情况====begin================================================
	   var newSm_4 = new Ext.grid.CheckboxSelectionModel();
	   var editRrownum = new Ext.grid.RowNumberer({
	   	  header : 'No.',
	   	  width : 28
	   });

	   var newCm_4 =  new Ext.grid.ColumnModel([editRrownum,newSm_4,	
	                                        {header : 'ID', dataIndex : 'ID',sortable : true,width : 120,hidden:true},
	                                        {header:'TASK_NUMBER',dataIndex:'TASK_NUMBER',sortable:true,width:120,hidden:true},
	                                        {header:'供应商品',dataIndex:'MP_GOODS',sortable:true,width:120},
	                                        {header:'供应商名称',dataIndex:'MP_SUPPLIER',sortable:true,width:120},
	                                        {header:'是否关联企业',dataIndex:'MP_ISRELATE',sortable:true,width:120,renderer:function(value){
		                       					 for(var i=0;i< lookup4Store.data.length;i++){
			                     						if(lookup4Store.data.items[i].data.key==value){
			                     						   return lookup4Store.data.items[i].data.value
			                     						}
			                     					}
			                     					}},
	                                        {header:'月采购金额(人民币/千元)',dataIndex:'MP_MONTH2MONEY',sortable:true,width:120},
	                                        {header:'结算天数(天)',dataIndex:'MP_BALANCEDAYS',sortable:true,width:120},
	                                        {header:'往来年数(年)',dataIndex:'MP_TRADEYEARS',sortable:true,width:120},
	                                        {header:'结算方式',dataIndex:'MP_PAYWAY',sortable:true,width:120,renderer:function(value){
		                       					 for(var i=0;i< lookup5Store.data.length;i++){
			                     						if(lookup5Store.data.items[i].data.key==value){
			                     						   return lookup5Store.data.items[i].data.value
			                     						}
			                     					}
			                     					}},
	                                        {header:'备注',dataIndex:'MP_MEMO',sortable:true,width:120}
	           	                                     ]); 
	   var newPanelStroe_4 = new Ext.data.Store({
	   	restful : true,
	   	proxy : new Ext.data.HttpProxy(
	   			{
	   				url : basepath + '/ocrmFInterviewMatepurchase.json' 
	   			}),
	   			reader : new Ext.data.JsonReader( {
	   				root : 'json.data'
	   			}, [{name:'ID'},
	   			    {name:'TASK_NUMBER'},
	   			    {name:'MP_GOODS'},
	   			    {name:'MP_SUPPLIER'},
	   			    {name:'MP_ISRELATE'},
	   			    {name:'MP_MONTH2MONEY'},
	   			    {name:'MP_BALANCEDAYS'},
	   			    {name:'MP_TRADEYEARS'},
	   			    {name:'MP_PAYWAY'},
	   			    {name:'MP_MEMO'}
	   			     ])
	   });	

	   var newPanel_4 = new Ext.grid.GridPanel({
	   	title : '原材料采购情况',
	   	autoScroll: true,
	   	height:200,
	   	store : newPanelStroe_4,
	   	frame : true,
	   	sm:newSm_4,
	   	cm : newCm_4,
	   	loadMask : {
	   		msg : '正在加载表格数据,请稍等...'
	   	}
	   });
	   //================原材料采购情况=====end=============================================

	   //=================产品销售状况====begin================================================
	   var newSm_5 = new Ext.grid.CheckboxSelectionModel();
	   var editRrownum = new Ext.grid.RowNumberer({
	   	  header : 'No.',
	   	  width : 28
	   });

	   var newCm_5=  new Ext.grid.ColumnModel([editRrownum,newSm_5,	
	                                        {header : 'ID', dataIndex : 'ID',sortable : true,width : 120,hidden:true},
	                                        {header:'TASK_NUMBER',dataIndex:'TASK_NUMBER',sortable:true,width:120,hidden:true},
	                                        {header:'销售产品',dataIndex:'PS_GOODS',sortable:true,width:120},
	                                        {header:'购买方名称',dataIndex:'PS_BUYER',sortable:true,width:120},
	                                        {header:'是否关联企业',dataIndex:'PS_ISRELATE',sortable:true,width:120,renderer:function(value){
		                       					 for(var i=0;i< lookup4Store.data.length;i++){
			                     						if(lookup4Store.data.items[i].data.key==value){
			                     						   return lookup4Store.data.items[i].data.value
			                     						}
			                     					}
			                     					}},
	                                        {header:'月销售金额(人民币/千元)',dataIndex:'PS_MONTH2MONEY',sortable:true,width:120},
	                                        {header:'结算天数(天)',dataIndex:'PS_BALANCEDAYS',sortable:true,width:120},
	                                        {header:'往来年数(年)',dataIndex:'PS_TRADEYEARS',sortable:true,width:120},
	                                        {header:'结算方式',dataIndex:'PS_PAYWAY',sortable:true,width:120,renderer:function(value){
		                       					 for(var i=0;i< lookup5Store.data.length;i++){
			                     						if(lookup5Store.data.items[i].data.key==value){
			                     						   return lookup5Store.data.items[i].data.value
			                     						}
			                     					}
			                     					}},
	                                        {header:'备注',dataIndex:'PS_MEMO',sortable:true,width:120}
	           	                                     ]); 
	   var newPanelStroe_5 = new Ext.data.Store({
	   	restful : true,
	   	proxy : new Ext.data.HttpProxy(
	   			{
	   				url : basepath + '/ocrmFInterviewProsale.json' 
	   			}),
	   			reader : new Ext.data.JsonReader( {
	   				root : 'json.data'
	   			}, [{name:'ID'},
	   			    {name:'TASK_NUMBER'},
	   			    {name:'PS_GOODS'},
	   			    {name:'PS_BUYER'},
	   			    {name:'PS_ISRELATE'},
	   			    {name:'PS_MONTH2MONEY'},
	   			    {name:'PS_BALANCEDAYS'},
	   			    {name:'PS_TRADEYEARS'},
	   			    {name:'PS_PAYWAY'},
	   			    {name:'PS_MEMO'}
	   			     ])
	   });	

	   var newPanel_5 = new Ext.grid.GridPanel({
	   	title : '产品销售状况',
	   	autoScroll: true,
	   	height:200,
	   	store : newPanelStroe_5,
	   	frame : true,
	   	sm:newSm_5,
	   	cm : newCm_5,
	   	loadMask : {
	   		msg : '正在加载表格数据,请稍等...'
	   	}
	   });
	   //================产品销售状况=========end=============================================
	   //================存款往来银行表=====begin==============================================
	   var newSm_6 = new Ext.grid.CheckboxSelectionModel();
	   var editRrownum = new Ext.grid.RowNumberer({
	   	  header : 'No.',
	   	  width : 28
	   });

	   var newCm_6=  new Ext.grid.ColumnModel([editRrownum,newSm_6,	
	                                        {header : 'ID', dataIndex : 'ID',sortable : true,width : 120,hidden:true},
	                                        {header:'TASK_NUMBER',dataIndex:'TASK_NUMBER',sortable:true,width:120,hidden:true},
	                                        {header:'往来银行',dataIndex:'D_BANKNAME',sortable:true,width:120},
	                                        {header:'平均存款量（人民币/千元）',dataIndex:'D_AVGDEPOSIT',sortable:true,width:120}
	           	                                     ]); 
	   var newPanelStroe_6 = new Ext.data.Store({
	   	restful : true,
	   	proxy : new Ext.data.HttpProxy(
	   			{
	   				url : basepath + '/ocrmFInterviewDepositbank.json' 
	   			}),
	   			reader : new Ext.data.JsonReader( {
	   				root : 'json.data'
	   			}, [{name:'ID'},
	   			    {name:'TASK_NUMBER'},
	   			    {name:'D_BANKNAME'},
	   			    {name:'D_AVGDEPOSIT'}
	   			     ])
	   });	
	   var newPanel_6 = new Ext.grid.GridPanel({
	   	title : '存款往来银行表',
	   	autoScroll: true,
	   	height:200,
	   	store : newPanelStroe_6,
	   	frame : true,
	   	sm:newSm_6,
	   	cm : newCm_6,
	   	loadMask : {
	   		msg : '正在加载表格数据,请稍等...'
	   	}
	   });
	   //====================存款往来银行表======end====================================================
	   //====================贷款往来银行表======begin==========================================
	   var newSm_7 = new Ext.grid.CheckboxSelectionModel();
	   var editRrownum = new Ext.grid.RowNumberer({
	   	  header : 'No.',
	   	  width : 28
	   });

	   var newCm_7=  new Ext.grid.ColumnModel([editRrownum,newSm_7,	
	                                        {header:'ID', dataIndex : 'ID',sortable : true,width : 120,hidden:true},
	                                        {header:'TASK_NUMBER',dataIndex:'TASK_NUMBER',sortable:true,width:120,hidden:true},
	                                        {header:'往来银行',dataIndex:'L_BANKNAME',sortable:true,width:120},
	                                        {header:'额度类型',dataIndex:'L_LIMITTYPE',sortable:true,width:120},
	                                        {header:'额度金额(人民币/千元)',dataIndex:'L_LIMITMONEY',sortable:true,width:120},
	                                        {header:'动拨余额(人民币/千元)',dataIndex:'L_BALANCE',sortable:true,width:120},
	                                        {header:'利率(%)',dataIndex:'L_RATE',sortable:true,width:120},
	                                        {header:'担保率(%)',dataIndex:'L_DBRATE',sortable:true,width:120},
	                                        {header:'担保品',dataIndex:'L_COLLATERAL',sortable:true,width:120},
	                                        {header:'备注',dataIndex:'L_MEMO',sortable:true,width:120}
	           	                                     ]); 
	   var newPanelStroe_7 = new Ext.data.Store({
	   	restful : true,
	   	proxy : new Ext.data.HttpProxy(
	   			{
	   				url : basepath + '/ocrmFInterviewLoanbank.json' 
	   			}),
	   			reader : new Ext.data.JsonReader( {
	   				root : 'json.data'
	   			}, [{name:'ID'},
	   			    {name:'TASK_NUMBER'},
	   			    {name:'L_BANKNAME'},
	   			    {name:'L_LIMITTYPE'},
	   			    {name:'L_LIMITMONEY'},
	   			    {name:'L_BALANCE'},
	   			    {name:'L_RATE'},
	   			    {name:'L_DBRATE'},
	   			    {name:'L_COLLATERAL'},
	   			    {name:'L_MEMO'}
	   			     ])
	   });	

	   var newPanel_7 = new Ext.grid.GridPanel({
	   	title : '贷款往来银行表',
	   	autoScroll: true,
	   	height:200,
	   	store : newPanelStroe_7,
	   	frame : true,
	   	sm:newSm_7,
	   	cm : newCm_7,
	   	loadMask : {
	   		msg : '正在加载表格数据,请稍等...'
	   	}
	   });
	   //===================贷款往来银行表=====end======================================
	   //===================拟承做存款产品====begin======================================
	   var newSm_8 = new Ext.grid.CheckboxSelectionModel();
	   var editRrownum = new Ext.grid.RowNumberer({
	   	  header : 'No.',
	   	  width : 28
	   });

	   var newCm_8=  new Ext.grid.ColumnModel([editRrownum,newSm_8,	
	                                        {header:'ID', dataIndex : 'ID',sortable : true,width : 120,hidden:true},
	                                        {header:'TASK_NUMBER',dataIndex:'TASK_NUMBER',sortable:true,width:120,hidden:true},
	                                        {header:'存款产品',dataIndex:'DP_NAME',sortable:true,width:120},
	                                        {header:'预计平均存款量(人民币/千元)',dataIndex:'DP_AVGDEPOSIT',sortable:true,width:120}
	           	                                     ]); 
	   var newPanelStroe_8 = new Ext.data.Store({
	   	restful : true,
	   	proxy : new Ext.data.HttpProxy(
	   			{
	   				url : basepath + '/ocrmFInterviewDepositpro.json' 
	   			}),
	   			reader : new Ext.data.JsonReader( {
	   				root : 'json.data'
	   			}, [{name:'ID'},
	   			    {name:'TASK_NUMBER'},
	   			    {name:'DP_NAME'},
	   			    {name:'DP_AVGDEPOSIT'}
	   			     ])
	   });	

	   var newPanel_8 = new Ext.grid.GridPanel({
	   	title : '拟承做存款产品',
	   	autoScroll: true,
	   	height:200,
	   	store : newPanelStroe_8,
	   	frame : true,
	   	sm:newSm_8,
	   	cm : newCm_8,
	   	loadMask : {
	   		msg : '正在加载表格数据,请稍等...'
	   	}
	   });
	   //=================拟承做存款产品====end=======================================
	   //=================拟申请外汇产品额度==begin===================================
	   var newSm_9 = new Ext.grid.CheckboxSelectionModel();
	   var editRrownum = new Ext.grid.RowNumberer({
	   	  header : 'No.',
	   	  width : 28
	   });
	   var newCm_9=  new Ext.grid.ColumnModel([editRrownum,newSm_9,	
	                                        {header:'ID', dataIndex : 'ID',sortable : true,width : 120,hidden:true},
	                                        {header:'TASK_NUMBER',dataIndex:'TASK_NUMBER',sortable:true,width:120,hidden:true},
	                                        {header:'外汇产品',dataIndex:'FL_NAME',sortable:true,width:120},
	                                        {header:'月交易量（等值美金/千元）',dataIndex:'FL_DEAL2MONTH',sortable:true,width:120},
	                                        {header:'额度金额（等值美金/千元）',dataIndex:'FL_LIMITMONEY',sortable:true,width:120}
	           	                                     ]); 
	   var newPanelStroe_9 = new Ext.data.Store({
	   	restful : true,
	   	proxy : new Ext.data.HttpProxy(
	   			{
	   				url : basepath + '/ocrmFInterviewForexlimit.json' 
	   			}),
	   			reader : new Ext.data.JsonReader( {
	   				root : 'json.data'
	   			}, [{name:'ID'},
	   			    {name:'TASK_NUMBER'},
	   			    {name:'FL_NAME'},
	   			    {name:'FL_DEAL2MONTH'},
	   			    {name:'FL_LIMITMONEY'}
	   			     ])
	   });	

	   var newPanel_9 = new Ext.grid.GridPanel({
	   	title : '拟申请外汇产品额度',
	   	autoScroll: true,
	   	height:200,
	   	store : newPanelStroe_9,
	   	frame : true,
	   	sm:newSm_9,
	   	cm : newCm_9,
	   	loadMask : {
	   		msg : '正在加载表格数据,请稍等...'
	   	}
	   });
	   //=================拟申请外汇产品额度==end=====================================
	   //=================拟申请授信产品====begin=====================================
	   var newSm_10 = new Ext.grid.CheckboxSelectionModel();
	   var editRrownum = new Ext.grid.RowNumberer({
	   	  header : 'No.',
	   	  width : 28
	   });
	   var newCm_10=  new Ext.grid.ColumnModel([editRrownum,newSm_10,	
	                                        {header:'ID', dataIndex : 'ID',sortable : true,width : 120,hidden:true},
	                                        {header:'TASK_NUMBER',dataIndex:'TASK_NUMBER',sortable:true,width:120,hidden:true},
	                            			 {header:'产品类型',dataIndex:'CP_PRODUCT_P',sortable:true,width:120,renderer:function(value){
	                            				 for(var i=0;i< lookup9Store.data.length;i++){
	                            					 if(lookup9Store.data.items[i].data.key==value){
	                            						 return lookup9Store.data.items[i].data.value
	                            					 }
	                            				 }
	                            			 }},
	                                        {header:'授信产品',dataIndex:'CP_PRODUCT',sortable:true,width:120,renderer:function(value){
		                       					 for(var i=0;i< lookup7Store.data.length;i++){
			                     						if(lookup7Store.data.items[i].data.key==value){
			                     						   return lookup7Store.data.items[i].data.value
			                     						}
			                     					}
			                     					}},
	                     					{header:'用途',dataIndex:'CP_USE',sortable:true,width:120,renderer:function(value){
	                     						for(var i=0;i< lookup6Store.data.length;i++){
	                     							if(lookup6Store.data.items[i].data.key==value){
	                     								return lookup6Store.data.items[i].data.value
	                     							}
	                     						}
	                     					}},
	                                        {header:'币种',dataIndex:'CP_CURRENCY',sortable:true,width:120,renderer:function(value){
		                       					 for(var i=0;i< lookup8Store.data.length;i++){
			                     						if(lookup8Store.data.items[i].data.key==value){
			                     						   return lookup8Store.data.items[i].data.value
			                     						}
			                     					}
			                     					}},
	                                        {header:'额度金额',dataIndex:'CP_LIMITMONEY',sortable:true,width:120},
	                                        {header:'担保品',dataIndex:'CP_COLLATERAL',sortable:true,width:120},
	                                        {header:'担保比率(%)',dataIndex:'CP_DBRATE',sortable:true,width:120},
	                                        {header:'备注',dataIndex:'CP_MEMO',sortable:true,width:120}
	           	                                     ]); 
	   var newPanelStroe_10 = new Ext.data.Store({
	   	restful : true,
	   	proxy : new Ext.data.HttpProxy(
	   			{
	   				url : basepath + '/ocrmFInterviewCreditpro.json' 
	   			}),
	   			reader : new Ext.data.JsonReader( {
	   				root : 'json.data'
	   			}, [{name:'ID'},
	   			    {name:'TASK_NUMBER'},
	   			    {name:'CP_USE'},
	   			    {name:'CP_PRODUCT'},
	   			    {name:'CP_CURRENCY'},
	   			    {name:'CP_LIMITMONEY'},
	   			    {name:'CP_MEMO'},
	   			    {name:'CP_PRODUCT_P'},
				    {name:'CP_COLLATERAL'},
				    {name:'CP_DBRATE'}
	   			     ])
	   });	
	   var newPanel_10 = new Ext.grid.GridPanel({
	   	title : '拟申请授信产品',
	   	autoScroll: true,
	   	height:200,
	   	store : newPanelStroe_10,
	   	frame : true,
	   	sm:newSm_10,
	   	cm : newCm_10,
	   	loadMask : {
	   		msg : '正在加载表格数据,请稍等...'
	   	}
	   });
	   //=================拟申请授信产品====end=======================================
	   if(type == '0'){
		   var bussFieldSetGrid = new Ext.form.FieldSet({
			   animCollapse :true,
			   collapsible:true,
			   title: '流程业务信息',
			   items:[infoForm,jt_form,viewContent_form]
		   }); 
	   }
	   
	   if(type == '1' || type == '01'){
		   var bussFieldSetGrid = new Ext.form.FieldSet({
			   animCollapse :true,
			   collapsible:true,
			   title: '流程业务信息',
			   items:[infoForm,newPanel_1,infoForm2,
			           newPanel_2,infoForm3,
			           newPanel_3,infoForm4,
			           newPanel_4,newPanel_5,infoForm5,
			           newPanel_6,newPanel_7,infoForm6,
			           newPanel_8,newPanel_9,newPanel_10,infoForm1,viewContent_form]
		   }); 
	   }
		var EchainPanel = new Mis.Echain.EchainPanel({
			instanceID:instanceid,
			nodeId:nodeid,
			nodeName:curNodeObj.nodeName,
			fOpinionFlag:curNodeObj.fOpinionFlag,
			approvalHistoryFlag:curNodeObj.approvalHistoryFlag,
			WindowIdclode:curNodeObj.windowid,
			callbackCustomFun:'3_a10##1'
		});
		var view = new Ext.Panel( {
			renderTo : 'viewEChian',
			 frame : true,
			width : document.body.scrollWidth,
			height : document.body.scrollHeight-40,
			autoScroll : true,
			layout : 'form',
			items : [bussFieldSetGrid,EchainPanel]

		});
			
		store.load({params : {
			id:id,
			type:type
        },
        
        callback:function(){
        	if(store.getCount()!=0){
        		loadFormData();
        	}
		}});
		function loadFormData(){
    		infoForm.getForm().loadRecord(store.getAt(0));
    		if(type == '0'){
    			if(store.getAt(0).data.MARK_RESULT_ORA.trim() =='客户拒绝'){
        			infoForm.form.findField("MARK_REFUSEREASON_ORA").show();
        		}else{
					infoForm.form.findField("MARK_REFUSEREASON_ORA").hide();
				}
    			if(store.getAt(0).data.PUR_CUST2CALL == '1'){
    				Ext.getCmp("p1").setValue(true);
    			}
    			if(store.getAt(0).data.PUR_SEEK2COLL == '1'){
    				Ext.getCmp("p2").setValue(true);
    			}
    			if(store.getAt(0).data.PUR_WARN2CALL == '1'){
    				Ext.getCmp("p3").setValue(true);
    			}
    			if(store.getAt(0).data.PUR_DEFEND2CALL == '1'){
    				Ext.getCmp("p4").setValue(true);
    			}
    			if(store.getAt(0).data.PUR_MARK2PRO == '1'){
    				Ext.getCmp("p5").setValue(true);
    			}
    			if(store.getAt(0).data.PUR_RISK2CALL == '1'){
    				Ext.getCmp("p6").setValue(true);
    			}
    		}
    		if(type == '1' || type == '01'){
    			infoForm1.getForm().loadRecord(store.getAt(0));
    			infoForm2.getForm().loadRecord(store.getAt(0));
    			infoForm3.getForm().loadRecord(store.getAt(0));
    			infoForm4.getForm().loadRecord(store.getAt(0));
    			infoForm5.getForm().loadRecord(store.getAt(0));
    			infoForm6.getForm().loadRecord(store.getAt(0));
    			if(store.getAt(0).data.MARK_RESULT_ORA.trim() =='客户婉拒'){
        			infoForm1.form.findField("MARK_REFUSEREASON_ORA").show();
        		}else{
					infoForm1.form.findField("MARK_REFUSEREASON_ORA").hide();
				}
    			if(store.getAt(0).data.CUS_ONMARK_ORA.trim() =='是'){
        			infoForm.form.findField("CUS_ONMARKPLACE_ORA").show();
        		}else{
					infoForm.form.findField("CUS_ONMARKPLACE_ORA").hide();
				}
    			//主要股东及持股比例
				newPanelStroe_1.load({params:{ID:id}});
				//主要固定资产
				newPanelStroe_2.load({params:{ID:id}});
				//营收获利情况
				newPanelStroe_3.load({params:{ID:id}});
				//原材料采购情况
				newPanelStroe_4.load({params:{ID:id}});
				//产品销售状况
				newPanelStroe_5.load({params:{ID:id}});
				//存款往来银行表
				newPanelStroe_6.load({params:{ID:id}});
				//贷款往来银行表
				newPanelStroe_7.load({params:{ID:id}});
				//拟承做存款产品
				newPanelStroe_8.load({params:{ID:id}});
				//拟申请外汇产品额度
				newPanelStroe_9.load({params:{ID:id}});
				//拟申请授信产品
				newPanelStroe_10.load({params:{ID:id}});
    		}
		}
	});
