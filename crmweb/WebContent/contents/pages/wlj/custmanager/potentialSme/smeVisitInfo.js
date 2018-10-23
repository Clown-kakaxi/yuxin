/**
 * @description 中小企营销流程-  拜访信息页面
 * @author denghj
 * @since 2015-11-10
 */
imports([
    '/contents/pages/com.yucheng.bcrm/com.yucheng.bcrm.js'
	,'/contents/pages/common/Com.yucheng.bcrm.common.OrgField.js' // 机构放大镜
	,'/contents/pages/common/Com.yucheng.crm.common.OrgUserManage.js'	//用户放大镜
	,'/contents/pages/common/Com.yucheng.crm.common.OrgUserManageNew.js'	//用户放大镜 不含限制
	,'/contents/pages/common/Com.yucheng.bcrm.common.CustomerQueryFieldQZ.js'	//客户放大镜（企商金营销用）
]);
Ext.QuickTips.init();
var url = basepath + '/ocrmFInterviewTask.json';
var autoLoadGrid = true;
var custData={VISIT_TYPE:""};
var bl = {PEC:""};
var temp = {};
var aw=window.screen.width-210;
var lookupTypes = ['IF_FLAG','CHECK_STAT','VISIT_RESULT_QS','VISIT_TYPE_QS','CUS_BUSISTATUS','MARK_REFUSEREASON',
                   'IF_FLAG','CUS_NATURE','CUS_OWNBUSI','CUST_SOURCE','F_HTYPE','F_OTYPE','F_UTYPE','CP_USE'
                   ,'CP_PRODUCT'
                   ,'CP_PRODUCT_P'
                   ,'PS_PAYWAY','ISCOLCHANGE'//付款方式
                   ,'CUS_STATUS'//客户营运状况
                   ,'VISIT_RESULT_QS_OLD'
                   ,'MARK_REASON_OLD'
                   ,'VISIT_TYPE_OLD'
                   ,'VISIT_TYPE_NEW'
                   ,'VISIT_TYPE_ALL'
                   ,'CHECK_STAT_QSJ'
                   ,'CUS_ONMARKPLACE'
                   ,'DM0019'    //担保品类型
                   ,'DM0004'    //还款方式
                   ,'PER_ROLE'  //人员角色----公司相关人员信息
                   ,'F_SECURED' //是否已抵押
                   ];
var localLookup = {
		'CURRENCY' : [
		    {key : '10',value : 'RMB'},      
		    {key : '13',value : 'USD'},
		    {key : '5',value : 'EUR'},    
		    {key : '8',value : 'JPY'},      
		    {key : '7',value : 'HKD'},      
		    {key : '6',value : 'GBP'},  
			{key : '1',value : 'AUD'},
			{key : '2',value : 'CAD'},  
			{key : '3',value : 'CHF'},  
			{key : '9',value : 'NZD'},      
			{key : '11',value : 'SGD'},    
			{key : '12',value : 'TWD'}  
		],
		'CP_PRODUCT_1':[
           {key : '11',value : '100%现金质押/SBLC担保的授信业务'},
           {key : '12',value : '银行承兑汇票贴现业务'},
           {key : '13',value : '出口信用证项下无瑕疵押汇业务'}
        ],
        'CP_PRODUCT_2':[
            {key : '21',value : '获得买方签回通知书的保理业务（有追索权）'},
            {key : '22',value : '商票贴现'},
            {key : '23',value : '出口信用证项下瑕疵单证押汇（一般瑕疵）'},
            {key : '24',value : '未获得买方签回通知书的保理业务（有追索权）'},
            {key : '25',value : '开立即期信用证'},
            {key : '26',value : '180天以内的外汇避险业务（PSR),仅包括spot/forward/swap'}
        ],
        'CP_PRODUCT_3':[
            {key : '31',value : '发票融资-应收账款(电汇结算）'},
            {key : '32',value : '出口信用证项下瑕疵单证押汇（重大瑕疵）'},
            {key : '33',value : '商票保贴'},
            {key : '34',value : '开立远期信用证'},
            {key : '35',value : '发票融资-应付账款(电汇结算）'},
            {key : '36',value : 'T/R 贷款'},
            {key : '37',value : '打包贷款'},
            {key : '38',value : '开立非融资性保函'},
            {key : '39',value : '金融衍生品额度'}
    	],
    	'CP_PRODUCT_4':[
            {key : '41',value : '短期流动资金贷款'},
            {key : '42',value : '中期流动资金贷款'},
            {key : '43',value : '开立融资性保函'}
        ],
        'CP_PRODUCT_5':[
            {key : '51',value : '固定资产贷款（包括设备融资贷款）'}
        ],
        'CP_PRODUCT_6':[
            {key : '61',value : '福费廷业务'}
        ], 
        'CP_PRODUCT_7':[
            {key : '71',value : '其他'}
        ], 
        'VISIT_TYPE_1':[
            {key : '2',value : '旧户新案'}
        ],
        'VISIT_TYPE_2':[
            {key : '1',value : '签约对保'},
            {key : '3',value : '授信户定期拜访'},
            {key : '4',value : '日常维护拜访'},
            {key : '5',value : '营销拜访'}
        ]           
	};
var fields = [{name:'ID',text:'id',gridField:false},
              {name:'CUST_NAME',text:'客户名称',xtype:'customerqueryqz',hiddenName:'CUST_ID',resutlWidth:200,singleSelect: false,searchField: true,allowBlank:true},
			  {name:'TASK_NUMBER',text:'TASK_NUMBER',gridField:false},
			  {name:'NEW_RECORD_ID',text:'NEW_RECORD_ID',gridField:false},
			  {name:'FLAG',text:'FLAG',gridField:false},
			  {name:'REVIEW_STATE',text:'复核状态',translateType:"CHECK_STAT_QSJ",resutlWidth:100},
              {name:'TASK_TYPE',text:'客户类型',translateType:'VISIT_TYPE_QS',searchField:true,resutlWidth:50},
              {name:'VISIT_TYPE',text:'拜访类型',translateType:'VISIT_TYPE_ALL',allowBlank:false,searchField:true,editable:true},
              {name:'VISIT_TYPE_ORA',hidden:true},
              {name:'CUST_ID',text:'客户编号',dataType:'string',gridField:false},
              {name:'MGR_ID',text:'客户经理编号',hidden:true},
              {name:'MGR_NAME',text:'客户经理名称',xtype:'userchoose',hiddenName:'MGR_ID',resutlWidth:100,singleSelect: false,searchField: true},
              
              {name:'INTERVIEWEE_NAME',text:'受访人名称',maxLength:80},
              {name:'INTERVIEWEE_POST',text:'受访人职位 ',maxLength:50},
              {name:'INTERVIEWEE_PHONE',text:'受访人电话',allowBlank:true,maxLength:50,vtype:'number'},
//              {name:'JOIN_PERSON',text:'本次参与人员',hidden:true,allowBlank:false,maxLength:50},
              {name:'JOIN_PERSON_ID',text:'本次参与人员编号',hidden:true},
              {name:'JOIN_PERSON',text:'本次参与人员',xtype:'userchooseNew',hiddenName:'JOIN_PERSON_ID',searchType:'ALLORG',resutlWidth:100,singleSelect: false},
              {name:'CREATE_USER_ID',text:'创建者',hidden:true},
              
              {name:'VISIT_TIME',text:'预约拜访日期',dataType:'date',searchField: true,allowBlank:false,resutlWidth:100},
              {name: 'VISIT_START_TIME',text :'预约拜访开始时间',hidden:true,format:'H:i',xtype:'timefield',minValue:'09:00',maxValue:'17:45',allowBlank: false,value:new Date(),
            	  listeners:{
            		  select:function(combo,record){
            			//开始时间
  		    			var v = this.getValue().split(":");
  		    			var h1 = parseInt(v[0]);
  		    			var s1 = parseInt(v[1]);
  		    			//结束时间
  		    			var endTime = getCurrentView().contentPanel.getForm().findField('VISIT_END_TIME').getValue();
  		    			if(endTime != null && endTime != ''){
  		    				var o = getCurrentView().contentPanel.getForm().findField('VISIT_END_TIME').getValue().split(":");
  	  		    			var h2 = parseInt(o[0]);
  	  		    			var s2 = parseInt(o[1]);
  	  		    			if(h1>h2){
	  	  		    			alert("结束时间["+endTime+"]不能小于起始时间["+this.getValue()+"]");
			    				getCurrentView().contentPanel.getForm().findField('VISIT_END_TIME').setValue('');
  	  		    			}else if(h1==h2){
    		    				if(s1>s2){
    		    					alert("结束时间["+endTime+"]不能小于起始时间["+this.getValue()+"]");
    		    					getCurrentView().contentPanel.getForm().findField('VISIT_END_TIME').setValue('');
    		    				}
    		    			}
  		    			}
            		  }
            	  }
              },
              {name: 'VISIT_END_TIME',text :'预约拜访结束时间',hidden:true,format:'H:i',xtype:'timefield',minValue:'09:00',maxValue:'18:00',allowBlank: false,value:new Date(),
    		    	listeners:{
    		    		select:function(combo,record){
    		    			//结束时间
    		    			var v = this.getValue().split(":");
    		    			var h1 = parseInt(v[0]);
    		    			var s1 = parseInt(v[1]);
    		    			//起始时间
    		    			var o = getCurrentView().contentPanel.getForm().findField('VISIT_START_TIME').getValue().split(":");
    		    			var h2 = parseInt(o[0]);
    		    			var s2 = parseInt(o[1]);
    		    			if(h1<h2){
    		    				alert("结束时间["+this.getValue()+"]不能小于起始时间["+getCurrentView().contentPanel.getForm().findField('VISIT_START_TIME').getValue()+"]");
    		    				getCurrentView().contentPanel.getForm().findField('VISIT_END_TIME').setValue('');
    		    			}else if(h1==h2){
    		    				if(s1<s2){
    		    					alert("结束时间["+this.getValue()+"]不能小于起始时间["+getCurrentView().contentPanel.getForm().findField('VISIT_START_TIME').getValue()+"]");
    		    					getCurrentView().contentPanel.getForm().findField('VISIT_END_TIME').setValue('');
    		    				}
    		    			}
    					}
  		    	}},
              
              //新户拜访明细信息<font color='red'>*</font>
              {name:'CALL_TIME',text:'实际拜访日期  ',dataType:'date',hidden:true,resutlWidth:100},  
              {name:'CUS_DOMICILE',text:'企业注册地',hidden:true,maxLength:100},
              {name:'CUS_NATURE',text:'企业性质',hidden:true,translateType:"CUS_NATURE",editable:true},
              {name:'CUS_LEGALPERSON',text:'法人代表',hidden:true,maxLength:50},
//              {name:'CUS_REGTIME',text:'企业成立时间',hidden:true,dataType:'date'},
              {name:'CUS_CNTPEOPLE',text:'员工人数',hidden:true,vtype:'number',maxLength:6},
              {name:'CUS_ONMARK',text:'是否上市',translateType:'IF_FLAG',hidden:true,editable:true},
              {name:'CUS_ONMARKPLACE',text:'上市地点',translateType:'CUS_ONMARKPLACE',hidden:true,editable:true},
              {name:'CUS_OWNBUSI',text:'所属行业 ',hidden:true,translateType:'CUS_OWNBUSI',editable:true},
              {name:'CUS_BUSISTATUS',text:'行业地位',hidden:true,translateType:'CUS_BUSISTATUS',editable:true},
              {name:'CUS_OPERATEPERSON',text:'经营负责人',hidden:true,maxLength:20},
              {name:'CUS_ACCOUNTPERSON',text:'财务负责人',hidden:true,maxLength:20},
              {name:'CUS_MAJORPRODUCT',text:'主要产品',hidden:true,maxLength:50},
              {name:'CUS_MAJORRIVAL',text:'主要竞争对手',hidden:true,maxLength:50},
              {name:'RES_CUSTSOURCE',text:'客户来源',hidden:true,allowBlank:false,translateType:'CUST_SOURCE',editable:true},
              {name:'RES_CASEBYPERSON',text:'转介人姓名',hidden:true,maxLength:20},
              {name:'RES_CASEBYPTEL',text:'转介人电话',hidden:true,maxLength:20},
            
              {name:'CUS_REGTIME',text:'企业成立时间',hidden:true,dataType:'date'},
              {name:'CUS_OPERATEAGE',text:'企业经营年限',hidden:true,maxLength:20},
              {name:'CUS_SITEOPERATETIME',text:'现址经营开始时间',hidden:true,dataType:'date'},
              {name:'CUS_SITEOPERATEAGE',text:'现址经营年限',hidden:true,maxLength:20},
              {name:'CUS_INCOMETY',text:'企业今年销售收入(人民币 千元)',hidden:true,dataType: 'decimal', viewFn: money('0,000.00'),minValue: 0,maxLength:24},
              {name:'CUS_INCOMELY',text:'企业去年销售收入(人民币 千元)',hidden:true,dataType: 'decimal', viewFn: money('0,000.00'),minValue: 0,maxLength:24},
              {name:'CUS_OPERATEADDR',text:'经营地址',hidden:true,maxLength:100},
              {name:'CUS_LEGALPTEL',text:'法人代表电话',hidden:true,maxLength:20},
              {name:'CUS_OPERATEPTEL',text:'实际控制人电话',hidden:true,maxLength:20},
              {name:'CUS_ACCOUNTPTEL',text:'财务负责人电话',hidden:true,maxLength:20},
              {name:'CUS_OPERATEPWAGE',text:'实际控制人从业年限',hidden:true,maxLength:20},
              {name:'CUS_OPERATEPMAGE',text:'实际控制人核心管理年限',hidden:true,maxLength:20},
              {name:'IF_OWNBANKCUST',text:'是否我行往来客户',hidden:true,translateType:'IF_FLAG',maxLength:20},
              {name:'RES_CUSTSOURCEDATE',text:'客户来源日期',hidden:true,dataType:'date'},

              {name:'DCRB_MAJORSHOLDER',text:'主要股东/经营团队补充说明',xtype:'textarea',hidden:true,maxLength:500},
              {name:'DCRB_FLOW',text:'行业产品及生产流程补充说明',xtype:'textarea',hidden:true,maxLength:500},
              {name:'DCRB_FIXEDASSETS',text:'固定资产补充说明',xtype:'textarea',hidden:true,maxLength:500},
              {name:'DCRB_PROFIT',text:'营收/获利补充说明',xtype:'textarea',hidden:true,maxLength:500},
              {name:'DCRB_SYMBIOSIS',text:'上下游合作交易情况补充说明',xtype:'textarea',hidden:true,maxLength:500},
              {name:'DCRB_OTHERTRADE',text:'他行往来情况补充说明',xtype:'textarea',hidden:true,maxLength:500},
              {name:'DCRB_MYSELFTRADE',text:'本行往来业务补充说明',xtype:'textarea',hidden:true,maxLength:500},
              
              {name:'RES_FOLLOWUP',text:'跟进事项',hidden:true,xtype:'textarea',maxLength:500},
              {name:'RES_OTHERINFO',text:'其他补充说明',hidden:true,xtype:'textarea',maxLength:500},
              {name:'MARK_RESULT',text:'拜访结果',hidden:true,translateType:'VISIT_RESULT_QS',editable:true},
              {name:'MARK_REFUSEREASON',text:'拒绝原因',hidden:true,translateType:'MARK_REFUSEREASON',editable:true},
              
//              {name:'MARK_RESULT_OLD',text:'营销结果',hidden:true,translateType:'VISIT_RESULT_QS_OLD'},
              {name:'MARK_RESULT_OLD',text:'拜访结果',hidden:true,translateType:'VISIT_RESULT_QS_OLD',editable:true},
              {name:'MARK_REFUSEREASON_OLD',text:'拒绝原因',hidden:true,translateType:'MARK_REASON_OLD',editable:true},
              
              {name:'CALL_SPENDTIME',text:'本次拜访花费时间(小时)',hidden:true,vtype:'money',maxLength:6},
              {name:'CALL_NEXTTIME',text:'预约下次拜访时间 ',dataType:'date',hidden:true},
              //旧客户拜访明细信息  
              {name:'CUS_STATUS',text:'客户运营状况  ',hidden:true,translateType:'CUS_STATUS',editable:true},   
              {name:'ISBUSCHANGE',text:'主营业务是否变更  ',hidden:true,translateType:'IF_FLAG',editable:true},   
              {name:'BUS_EXPLAIN',text:'主营业务变更说明  ',hidden:true,maxLength:500},   
              {name:'ISREVCHANGE',text:'营收是否大幅变化  ',hidden:true,translateType:'IF_FLAG',editable:true},   
              {name:'REV_EXPLAIN',text:'营收变化说明  ',hidden:true,maxLength:500},   
              {name:'ISPROCHANGE',text:'获利率是否大幅变化  ',hidden:true,translateType:'IF_FLAG',editable:true},   
              {name:'PRO_EXPLAIN',text:'获利率变化说明 ',hidden:true,maxLength:500},   
              {name:'ISSUPCHANGE',text:'主要供应商是否调整  ',hidden:true,translateType:'IF_FLAG',editable:true},   
              {name:'SUP_EXPLAIN',text:'供应商调整说明  ',hidden:true,maxLength:500},   
              {name:'ISPURCHANGE',text:'主要买方是否调整  ',hidden:true,translateType:'IF_FLAG',editable:true},   
              {name:'PUR_EXPLAIN',text:'买方调整说明  ',hidden:true,maxLength:500},   
              {name:'ISEQUCHANGE',text:'股权结构是否变更  ',hidden:true,translateType:'IF_FLAG',editable:true},   
              {name:'EQU_EXPLAIN',text:'股权结构说明  ',hidden:true,maxLength:500},   
              {name:'ISOPCCHANGE',text:'经营层是否有变更  ',hidden:true,translateType:'IF_FLAG',editable:true},   
              {name:'OPC_EXPLAIN',text:'经营层变更说明  ',hidden:true,maxLength:500},   
              {name:'ISCOLCHANGE',text:'担保品状况  ',hidden:true,translateType:'ISCOLCHANGE',editable:true},   
              {name:'COL_EXPLAIN',text:'担保品说明  ',hidden:true,maxLength:500},   
              {name:'ISSYMCHANGE',text:'与银行合作状况是否变化  ',hidden:true,translateType:'ISCOLCHANGE',editable:true},   
              {name:'SYM_EXPLAIN',text:'与银行合作状况情况说明  ',hidden:true,maxLength:500},   
              {name:'MARK_PRODUCT',text:'拟营销产品  ',hidden:true,maxLength:100},
              //旧户拜访目的
              {name:'PUR_CUST2CALL',text:'正常客户定期回访  ',hidden:true},   
              {name:'PUR_SEEK2COLL',text:'勘察担保品  ',hidden:true},   
              {name:'PUR_WARN2CALL',text:'预警客户定期回访  ',hidden:true},   
              {name:'PUR_DEFEND2CALL',text:'年审/条件变更风管部协访  ',hidden:true},   
              {name:'PUR_MARK2PRO',text:'营销新产品  ',hidden:true},   
              {name:'PUR_RISK2CALL',text:'授信风险增加临时拜访  ',hidden:true},
              {name:'CONCLUSION',text:'客户筛选  ',hidden:true}  
             ];
var tbar = [{
	text:'删除',
	hidden:JsContext.checkGrant('smeVisitInfoDelete'),
	handler : function(){
		if(getSelectedData() == false){
			Ext.Msg.alert('提示','请选择一条数据');
			return false;
		}

		var ID = '';
		for (var i=0;i<getAllSelects().length;i++){
			if(getAllSelects()[i].data.MGR_ID != __userId){
				Ext.Msg.alert('提示','不能删除他人的拜访信息！');
				return false;
			}
			if(getAllSelects()[i].data.REVIEW_STATE == '2' || getAllSelects()[i].data.REVIEW_STATE == '02'){
				Ext.Msg.alert('提示','不能删除[复核中]状态的拜访信息！');
				return false;
			}
			if(getAllSelects()[i].data.REVIEW_STATE == '3' || getAllSelects()[i].data.REVIEW_STATE == '03'){
				Ext.Msg.alert('提示','不能删除[已完成并已提交CALLREPORT]状态的拜访信息！');
				return false;
			}
			ID += getAllSelects()[i].data.ID;
			ID += ",";
		}
		ID = ID.substring(0, ID.length-1);
		Ext.MessageBox.confirm('提示','确定删除拜访信息和拜访日程吗?',function(buttonId){
			if(buttonId.toLowerCase() == "no"){
			return false;
			} 
		    Ext.Ajax.request({
                url: basepath+'/ocrmFInterviewTask!batchDel.json?idStr='+ID,                                
                success : function(){
                    Ext.Msg.alert('提示', '删除成功');
                    reloadCurrentData();
                },
                failure : function(){
                    Ext.Msg.alert('提示', '删除失败');
                    reloadCurrentData();
                }
            });
		});			
	
	}
},{
	text:'新增',
	hidden:JsContext.checkGrant('smeVisitInfoCreate'),
	handler : function(){
		showCustomerViewByIndex(0);
	}
},{
	text:'CALLREPORT维护',
	hidden:JsContext.checkGrant('smeVisitInfoEdit'),
	handler : function(){
		if(getSelectedData() == false){
			Ext.Msg.alert('提示','请选择一条数据');
			return false;
		}
		if(getSelectedData().data.MGR_ID!=__userId){
			Ext.Msg.alert('提示','您不是该CALLREPORT的客户经理，不允许维护');
			return false;
		}
		if(getSelectedData().data.REVIEW_STATE == 3 || getSelectedData().data.REVIEW_STATE == 03){
			Ext.Msg.alert('提示','该项已完成CALLREPORT维护!');
			return false;
		}
		var j = getSelectedData().data.TASK_TYPE;
		var i = getSelectedData().data.VISIT_TYPE;//拜访类型  2 旧户新案
		if(j == 0){
			if(i == 2){//旧户新案
				window._app.collapseSearchPanel();
				showCustomerViewByIndex(3);
			}else if(i == null || i == ''){
				window._app.collapseSearchPanel();
//				showCustomerViewByIndex(13);
				showCustomerViewByIndex(25);
			}else {
				window._app.collapseSearchPanel();
//				showCustomerViewByIndex(12);
				showCustomerViewByIndex(24);
			}
		}else if(j == 1){
			window._app.collapseSearchPanel();
			showCustomerViewByIndex(3);
		}
	}
},{
	//2015-03-13
	text:'CALLREPORT打印',
	handler : function(){
		if(!getSelectedData()){
			Ext.Msg.alert('提示','请先选择一条记录！');
			return false;
		}
		var taskNum=getSelectedData().data.TASK_NUMBER;
		if(getSelectedData().data.TASK_TYPE=='1'){
        	var Url =' http://10.7.34.9:7001/crmweb/reportJsp/showReport.jsp?raq=baifangxinhu.raq&task_num='+taskNum;
		}else{
			if(getSelectedData().data.TASK_TYPE=='0'&&getSelectedData().data.VISIT_TYPE=='2'){
				var Url =' http://10.7.34.9:7001/crmweb/reportJsp/showReport.jsp?raq=baifangxinhu.raq&task_num='+taskNum;
			}else
			    var Url =' http://10.7.34.9:7001/crmweb/reportJsp/showReport.jsp?raq=baifangjiuhu.raq&task_num='+taskNum;
		}
		window.open(Url);
		}
},{
	text:'详情',
	hidden:JsContext.checkGrant('smeVisitInfoDetail'),
	handler : function(){
	    if(getSelectedData() == false){
			Ext.Msg.alert('提示','请选择一条数据');
			return false;
		}
		var j = getSelectedData().data.TASK_TYPE;
		var i = getSelectedData().data.VISIT_TYPE;//拜访类型  2 旧户新案
		if(j == 0){
			if(i == 2){//旧户新案
				window._app.collapseSearchPanel();
				showCustomerViewByIndex(1);
			}else if(i == null || i == ''){
				Ext.Msg.alert('提示','请先维护拜访类型！');
				return false;
			}else {
				window._app.collapseSearchPanel();
				showCustomerViewByIndex(2);
			}
		}else if(j == 1){
			window._app.collapseSearchPanel();
			showCustomerViewByIndex(1);
		}
	}
},{
	text : '展开',
	handler : function(){
		expandSearchPanel();
	}
}];
var customerView = [{
	//0
	title:'新增',
	hideTitle:true,
	type:'form',
	suspendWidth: aw,
	autoLoadSeleted : false,
	groups : [{
		columnCount : 2,
		fields:[{name:'CUST_NAME',text:'客户名称',xtype:'customerqueryqz',hiddenName:'CUST_ID',allowBlank:false,custType:'1',
			singleSelected:true,newCust:true,callback:function(a,b){
				getCurrentView().setValues({
					'INDUST_TYPE':getCurrentView().contentPanel.form.findField("CUST_NAME").industType,
					'LINK_PHONE':getCurrentView().contentPanel.form.findField("CUST_NAME").mobileNum,
					'LINK_MAN':getCurrentView().contentPanel.form.findField("CUST_NAME").linkUser,
					'TASK_TYPE':getCurrentView().contentPanel.form.findField("CUST_NAME").potentialFlag,
					'MGR_NAME':getCurrentView().contentPanel.form.findField("CUST_NAME").mgrName,
					'MGR_ID':getCurrentView().contentPanel.form.findField("CUST_NAME").mgrId
				});
				var qz = b[0].json.POTENTIAL_FLAG;
				getCurrentView().contentPanel.getForm().findField('VISIT_TYPE').setValue(null);
				getCurrentView().contentPanel.getForm().findField('VISIT_TYPE').show();
				if(qz == '0'){//旧户
					getCurrentView().contentPanel.getForm().findField('VISIT_TYPE').bindStore(findLookupByType('VISIT_TYPE_OLD'));
				}else if(qz == '1'){
					getCurrentView().contentPanel.getForm().findField('VISIT_TYPE').bindStore(findLookupByType('VISIT_TYPE_NEW'));
				}
		}},'MGR_NAME','INTERVIEWEE_NAME','INTERVIEWEE_POST','INTERVIEWEE_PHONE','VISIT_TIME','TASK_TYPE',
		//'VISIT_TYPE',
		{name:'VISIT_TYPE',text:'拜访类型',translateType:'VISIT_TYPE_ALL',allowBlank:false,editable:true,listeners:{
        	select:function(){
        		var VISIT_TYPE = this.value;
        		if(VISIT_TYPE == '7'){
        			var CUST_ID = getCurrentView().contentPanel.getForm().findField('CUST_ID').getValue(); //客户编号
        			var MGR_ID = getCurrentView().contentPanel.getForm().findField('MGR_ID').getValue();   //客户经理编号
        			Ext.Ajax.request({
    					url : basepath + '/ocrmFInterviewTask!checkNewRecord.json',
    					method : 'GET',
    					params : {
    						'CUST_ID' : CUST_ID,
    						'MGR_ID' : MGR_ID,
    						'VISIT_TYPE' : VISIT_TYPE
    					},
    					success : function(response) {
	   						 var ret = Ext.decode(response.responseText);
	   						 var isFlag = ret.isFlag;//该客户是否存在新户首次拜访
	   						 if(isFlag == '1'){
	   							Ext.Msg.alert('提示', '新户首次拜访只能创建一次，该客户已存在新户首次拜访');
	   							getCurrentView().contentPanel.getForm().findField('VISIT_TYPE').setValue(null);
	   						 }
    					}
    			});
        		}
        	}
		}},
		'ID','REVIEW_STATE','TASK_NUMBER','MGR_ID','JOIN_PERSON_ID'],
		fn:function(CUST_NAME,MGR_NAME,INTERVIEWEE_NAME,INTERVIEWEE_POST,INTERVIEWEE_PHONE,VISIT_TIME,TASK_TYPE,VISIT_TYPE,ID,REVIEW_STATE,TASK_NUMBER,MGR_ID,JOIN_PERSON_ID){
			MGR_ID.hidden = true;
			TASK_TYPE.hidden = true;
			ID.hidden = true;
			REVIEW_STATE.hidden = true;
			TASK_NUMBER.hidden = true;
			JOIN_PERSON_ID.hidden = true;
			return [CUST_NAME,MGR_NAME,INTERVIEWEE_NAME,INTERVIEWEE_POST,INTERVIEWEE_PHONE,VISIT_TIME,TASK_TYPE,VISIT_TYPE,ID,REVIEW_STATE,TASK_NUMBER,MGR_ID,JOIN_PERSON_ID];
		}
	},{
		   columnCount : 0.95,
		   fields:[{name:'JOIN_PERSON',text:'本次参与人员',xtype:'userchooseNew',hiddenName:'JOIN_PERSON_ID',searchType:'ALLORG',resutlWidth:100,singleSelect: false}],
		   fn:function(JOIN_PERSON){
			   return [JOIN_PERSON];
		   }
	}],formButtons : [{
		text:'保存',
		fn : function(formPanel,basicForm){
			 if (!formPanel.getForm().isValid()) {
	               Ext.MessageBox.alert('提示','输入有误,请检查输入项');
	               return false;
	           };
	        var data = formPanel.getForm().getFieldValues();
			var commintData = translateDataKey(data,1);
			Ext.Msg.wait('正在处理，请稍后......','系统提示');
			Ext.Ajax.request({
					url : basepath + '/ocrmFInterviewTask.json',
					method : 'POST',
					params : commintData,
					success : function(response) {
						Ext.Msg.alert('提示', '操作成功');
						reloadCurrentData();
						hideCurrentView();
					},
					failure : function() {
						Ext.Msg.alert('提示', '操作失败');
						reloadCurrentData();
						hideCurrentView();
					}
			});
		}
	},{
		text:'关闭',
		fn : function(formPanel,basicForm){
			 reloadCurrentData();
			 hideCurrentView();
		}
	}]
},{//1
	title:'详情',//新户及旧户新案_详情
	hideTitle:true,
	type:'form',
	suspendWidth: aw,
	autoLoadSeleted : true,
	groups : [{
		columnCount : 2,
		fields:['MGR_NAME','CUST_NAME','INTERVIEWEE_NAME','INTERVIEWEE_POST','INTERVIEWEE_PHONE','VISIT_TYPE','VISIT_TIME','VISIT_START_TIME','VISIT_END_TIME','CALL_TIME','TASK_TYPE',
			'MGR_ID','ID','TASK_NUMBER','JOIN_PERSON_ID'],
		fn:function(MGR_NAME,CUST_NAME,INTERVIEWEE_NAME,INTERVIEWEE_POST,INTERVIEWEE_PHONE,VISIT_TYPE,VISIT_TIME,VISIT_START_TIME,VISIT_END_TIME,CALL_TIME,TASK_TYPE,MGR_ID,ID,TASK_NUMBER,JOIN_PERSON_ID){
			MGR_ID.hidden = true;
			TASK_TYPE.hidden = true;
			TASK_NUMBER.hidden = true;
			ID.hidden = true;
			JOIN_PERSON_ID.hidden = true;
			
			MGR_NAME.readOnly = true;
			MGR_NAME.cls = "x-readOnly";
			CUST_NAME.readOnly = true;
			CUST_NAME.cls = "x-readOnly";
			TASK_TYPE.readOnly = true;
			TASK_TYPE.cls = "x-readOnly";
			INTERVIEWEE_NAME.readOnly = true;
			INTERVIEWEE_NAME.cls = "x-readOnly";
			INTERVIEWEE_POST.readOnly = true;
			INTERVIEWEE_POST.cls = "x-readOnly";
			INTERVIEWEE_PHONE.readOnly = true;
			INTERVIEWEE_PHONE.cls = "x-readOnly";
			VISIT_TYPE.readOnly = true;
			VISIT_TYPE.cls = "x-readOnly";
			VISIT_TIME.readOnly = true;
			VISIT_TIME.cls = "x-readOnly";
			VISIT_START_TIME.readOnly = true;
			VISIT_START_TIME.cls = "x-readOnly";
			VISIT_END_TIME.readOnly = true;
			VISIT_END_TIME.cls = "x-readOnly";
			CALL_TIME.readOnly = true;
			CALL_TIME.cls = "x-readOnly";
			TASK_TYPE.readOnly = true;
			TASK_TYPE.cls = "x-readOnly";
			TASK_NUMBER.readOnly = true;
			TASK_NUMBER.cls = "x-readOnly";
			return [MGR_NAME,CUST_NAME,INTERVIEWEE_NAME,INTERVIEWEE_POST,INTERVIEWEE_PHONE,VISIT_TYPE,VISIT_TIME,VISIT_START_TIME,VISIT_END_TIME,CALL_TIME,TASK_TYPE,MGR_ID,ID,TASK_NUMBER,JOIN_PERSON_ID];
		}
	},{
		   columnCount : 0.95,
		   fields:[{name:'JOIN_PERSON',text:'本次参与人员',xtype:'userchooseNew',hiddenName:'JOIN_PERSON_ID',searchType:'ALLORG',resutlWidth:100,singleSelect: false}],
		   fn:function(JOIN_PERSON){
		   		JOIN_PERSON.readOnly = true;
		   		JOIN_PERSON.cls = "x-readOnly";
			   return [JOIN_PERSON];
		   }
	},{
		columnCount : 2,
		fields:['CUS_DOMICILE','CUS_NATURE','CUS_REGTIME','CUS_OPERATEAGE','CUS_SITEOPERATETIME','CUS_SITEOPERATEAGE','CUS_ONMARK',
		        'CUS_CNTPEOPLE','CUS_OWNBUSI','CUS_MAJORPRODUCT','CUS_BUSISTATUS','CUS_MAJORRIVAL','CUS_INCOMETY','CUS_INCOMELY',
		        'ID','TASK_NUMBER'],
		fn:function(CUS_DOMICILE,CUS_NATURE,CUS_REGTIME,CUS_OPERATEAGE,CUS_SITEOPERATETIME,CUS_SITEOPERATEAGE,CUS_ONMARK,CUS_CNTPEOPLE,CUS_OWNBUSI,
				CUS_MAJORPRODUCT,CUS_BUSISTATUS,CUS_MAJORRIVAL,CUS_INCOMETY,CUS_INCOMELY,ID,TASK_NUMBER){
				ID.hidden = true;
				TASK_NUMBER.hidden = true;
				
				CUS_DOMICILE.readOnly = true;
				CUS_DOMICILE.cls = "x-readOnly";
				CUS_NATURE.readOnly = true;
			    CUS_NATURE.cls = "x-readOnly";
			    CUS_REGTIME.readOnly = true;
			    CUS_REGTIME.cls = "x-readOnly";
			    CUS_OPERATEAGE.readOnly = true;
			    CUS_OPERATEAGE.cls = "x-readOnly";
			    CUS_ONMARK.readOnly = true;
			    CUS_ONMARK.cls = "x-readOnly";
			    CUS_CNTPEOPLE.readOnly = true;
			    CUS_CNTPEOPLE.cls = "x-readOnly";
			    CUS_SITEOPERATETIME.readOnly = true;
			    CUS_SITEOPERATETIME.cls = "x-readOnly";
			    CUS_SITEOPERATEAGE.readOnly = true;
			    CUS_SITEOPERATEAGE.cls = "x-readOnly";
			    CUS_BUSISTATUS.readOnly = true;
			    CUS_BUSISTATUS.cls = "x-readOnly";
			    CUS_MAJORRIVAL.readOnly = true;
			    CUS_MAJORRIVAL.cls = "x-readOnly";
			    CUS_OWNBUSI.readOnly = true;
			    CUS_OWNBUSI.cls = "x-readOnly";
			    CUS_MAJORPRODUCT.readOnly = true;
			    CUS_MAJORPRODUCT.cls = "x-readOnly";
			    CUS_INCOMETY.readOnly = true;
			    CUS_INCOMETY.cls = "x-readOnly";
			    CUS_INCOMETY.hidden = true;
			    CUS_INCOMELY.readOnly = true;
			    CUS_INCOMELY.cls = "x-readOnly";
			    CUS_INCOMELY.hidden = true;
			return [CUS_DOMICILE,CUS_NATURE,CUS_REGTIME,CUS_OPERATEAGE,CUS_SITEOPERATETIME,CUS_SITEOPERATEAGE,CUS_ONMARK,CUS_CNTPEOPLE,CUS_OWNBUSI,
					CUS_MAJORPRODUCT,CUS_BUSISTATUS,CUS_MAJORRIVAL,CUS_INCOMETY,CUS_INCOMELY,ID,TASK_NUMBER];
		}
	},{
		columnCount:0.95,
		fields : ['CUS_OPERATEADDR'],
		fn : function(CUS_OPERATEADDR){
				CUS_OPERATEADDR.readOnly = true;
				CUS_OPERATEADDR.cls = "x-readOnly";
			return [CUS_OPERATEADDR];//经营地址
		}
	},{
		columnCount:2,
		fields : [
		          {name:'CUS_LEGALPERSON',text:'法定代表人姓名'},'CUS_LEGALPTEL',
			      {name:'CUS_ACCOUNTPERSON',text:'财务负责人姓名'},'CUS_ACCOUNTPTEL',
			      {name:'CUS_OPERATEPERSON',text:'实际控制人姓名'},'CUS_OPERATEPTEL',
			      'CUS_OPERATEPWAGE','CUS_OPERATEPMAGE','ID','TASK_NUMBER'],
		fn : function(CUS_LEGALPERSON,CUS_LEGALPTEL,CUS_ACCOUNTPERSON,CUS_ACCOUNTPTEL,CUS_OPERATEPERSON,CUS_OPERATEPTEL,CUS_OPERATEPWAGE,CUS_OPERATEPMAGE,ID,TASK_NUMBER){
					ID.hidden = true;
					TASK_NUMBER.hidden = true;
			
				    CUS_LEGALPERSON.readOnly = true;
				    CUS_LEGALPERSON.cls = "x-readOnly";
				    CUS_LEGALPERSON.hidden = true;
				    CUS_LEGALPTEL.readOnly = true;
				    CUS_LEGALPTEL.cls = "x-readOnly";
				    CUS_LEGALPTEL.hidden = true;
				    CUS_ACCOUNTPERSON.readOnly = true;
				    CUS_ACCOUNTPERSON.cls = "x-readOnly";
				    CUS_ACCOUNTPERSON.hidden = true;
				    CUS_ACCOUNTPTEL.readOnly = true;
				    CUS_ACCOUNTPTEL.cls = "x-readOnly";
				    CUS_ACCOUNTPTEL.hidden = true;
				    CUS_OPERATEPERSON.readOnly = true;
				    CUS_OPERATEPERSON.cls = "x-readOnly";
				    CUS_OPERATEPERSON.hidden = true;
				    CUS_OPERATEPTEL.readOnly = true;
				    CUS_OPERATEPTEL.cls = "x-readOnly";
				    CUS_OPERATEPTEL.hidden = true;
				    CUS_OPERATEPWAGE.readOnly = true;
				    CUS_OPERATEPWAGE.cls = "x-readOnly";
				    CUS_OPERATEPWAGE.hidden = true;
				    CUS_OPERATEPMAGE.readOnly = true;
				    CUS_OPERATEPMAGE.cls = "x-readOnly";
				    CUS_OPERATEPMAGE.hidden = true;
			return [CUS_LEGALPERSON,CUS_LEGALPTEL,CUS_ACCOUNTPERSON,CUS_ACCOUNTPTEL,CUS_OPERATEPERSON,CUS_OPERATEPTEL,CUS_OPERATEPWAGE,CUS_OPERATEPMAGE,ID,TASK_NUMBER];
		}
	},{
		columnCount:0.945,
		fields : ['DCRB_FLOW'],
		fn : function(DCRB_FLOW){
			DCRB_FLOW.readOnly = true;
		   	DCRB_FLOW.cls = 'x-readOnly';
			return [DCRB_FLOW];
		}
	},{
		columnCount:0.945,
		fields : ['TEST'],
		fn : function(TEST){
			return [newPanel_13_detail];//公司相关人员信息
		}
	},{
		columnCount:0.945,
		fields : ['DCRB_MAJORSHOLDER'],
		fn : function(DCRB_MAJORSHOLDER){
			DCRB_MAJORSHOLDER.readOnly = true;
		   	DCRB_MAJORSHOLDER.cls = 'x-readOnly';
			return [DCRB_MAJORSHOLDER];
		}
	},{
		columnCount:0.945,
		fields : ['TEST'],
		fn : function(TEST){
			return [newPanel_2_detail];//主要固定资产
		}
	},{
		columnCount:0.945,
		fields : ['DCRB_FIXEDASSETS'],
		fn : function(DCRB_FIXEDASSETS){
			DCRB_FIXEDASSETS.readOnly = true;
		   	DCRB_FIXEDASSETS.cls = 'x-readOnly';
			return [DCRB_FIXEDASSETS];
		}
	},{
		columnCount:0.945,
		fields : ['TEST'],
		fn : function(TEST){
			return [newPanel_3_detail];//营收获利情况
		}
	},{
		columnCount:0.945,
		fields : ['DCRB_PROFIT'],
		fn : function(DCRB_PROFIT){
			DCRB_PROFIT.readOnly = true;
		   	DCRB_PROFIT.cls = 'x-readOnly';
			return [DCRB_PROFIT];//营收/获利补充说明
		}
	},{
		columnCount:0.945,
		fields : ['TEST'],
		fn : function(TEST){
			return [newPanel_4_detail];//原材料采购情况
		}
	},{
		columnCount:0.945,
		fields : ['TEST'],
		fn : function(TEST){
			return [newPanel_5_detail];//产品销售状况
		}
	},{
		columnCount:0.945,
		fields : ['DCRB_SYMBIOSIS'],
		fn : function(DCRB_SYMBIOSIS){
			DCRB_SYMBIOSIS.readOnly = true;
		   	DCRB_SYMBIOSIS.cls = 'x-readOnly';
			return [DCRB_SYMBIOSIS];//上下游合作交易情况补充说明
		}
	},{
		columnCount:0.945,
		fields : ['TEST'],
		fn : function(TEST){
			return [newPanel_6_detail];//存款往来银行表
		}
	},{
		columnCount:0.945,
		fields : ['TEST'],
		fn : function(TEST){
			return [newPanel_7_detail];//贷款往来银行表
		}
	},{
		columnCount:0.945,
		fields : ['DCRB_OTHERTRADE'],
		fn : function(DCRB_OTHERTRADE){
			DCRB_OTHERTRADE.readOnly = true;
		   	DCRB_OTHERTRADE.cls = 'x-readOnly';
			return [DCRB_OTHERTRADE];//他行往来情况补充说明
		}
	},{
		columnCount:0.945,
		fields : ['TEST'],
		fn : function(TEST){
			return [newPanel_8_detail];//拟承做存款产品
		}
	},{
		columnCount:0.945,
		fields : ['TEST'],
		fn : function(TEST){
			return [newPanel_9_detail];//拟申请外汇产品额度
		}
	},{
		columnCount:0.945,
		fields : ['TEST'],
		fn : function(TEST){
			return [newPanel_11_detail];//拟申请授信产品
		}
	},{
		columnCount:0.945,
		fields : ['TEST'],
		fn : function(TEST){
			return [newPanel_12_detail];//担保品信息
		}
	},{
		columnCount : 0.945,
		fields:['DCRB_MYSELFTRADE'],
		fn:function(DCRB_MYSELFTRADE){
			DCRB_MYSELFTRADE.readOnly = true;
			DCRB_MYSELFTRADE.cls = "x-readOnly";
			return[DCRB_MYSELFTRADE];//本行往来业务补充说明
		}
	},{
		columnCount : 0.945,
		fields:['RES_OTHERINFO'],
		fn:function(RES_OTHERINFO){
			RES_OTHERINFO.readOnly = true;
			RES_OTHERINFO.cls = "x-readOnly";
			return[RES_OTHERINFO];//其他补充信息
		}
	},{
		columnCount : 0.945,
		fields:['RES_FOLLOWUP'],
		fn:function(RES_FOLLOWUP){
			RES_FOLLOWUP.readOnly = true;
			RES_FOLLOWUP.cls = "x-readOnly";
			return[RES_FOLLOWUP];//跟进事项
		}
	},{
		columnCount : 2,
		fields:['RES_CUSTSOURCE','RES_CUSTSOURCEDATE','IF_OWNBANKCUST','CALL_SPENDTIME','RES_CASEBYPERSON','RES_CASEBYPTEL',
		        'MARK_RESULT','MARK_REFUSEREASON','CALL_NEXTTIME','ID','TASK_NUMBER','NEW_RECORD_ID','FLAG','CONCLUSION'],
		fn:function(RES_CUSTSOURCE,RES_CUSTSOURCEDATE,IF_OWNBANKCUST,RES_CASEBYPERSON,RES_CASEBYPTEL,MARK_RESULT,MARK_REFUSEREASON,
				CALL_SPENDTIME,CALL_NEXTTIME,ID,TASK_NUMBER,NEW_RECORD_ID,FLAG,CONCLUSION){
			ID.hidden = true;
			TASK_NUMBER.hidden = true;
			NEW_RECORD_ID.hidden = true;
			FLAG.hidden = true;
			CONCLUSION.hidden = true;
			
			RES_CUSTSOURCE.readOnly = true;
			RES_CUSTSOURCE.cls = 'x-readOnly';
			RES_CUSTSOURCEDATE.readOnly = true;
			RES_CUSTSOURCEDATE.cls = 'x-readOnly';
			IF_OWNBANKCUST.readOnly = true;
			IF_OWNBANKCUST.cls = 'x-readOnly';
			RES_CASEBYPERSON.readOnly = true;
			RES_CASEBYPERSON.cls = 'x-readOnly';
			RES_CASEBYPTEL.readOnly = true;
			RES_CASEBYPTEL.cls = 'x-readOnly';
			MARK_RESULT.readOnly = true;
			MARK_RESULT.cls = 'x-readOnly';
			MARK_REFUSEREASON.readOnly = true;
			MARK_REFUSEREASON.cls = 'x-readOnly';
			CALL_SPENDTIME.readOnly = true;
			CALL_SPENDTIME.cls = 'x-readOnly';
			CALL_NEXTTIME.readOnly = true;
			CALL_NEXTTIME.cls = 'x-readOnly';
			return [RES_CUSTSOURCE,RES_CUSTSOURCEDATE,IF_OWNBANKCUST,RES_CASEBYPERSON,RES_CASEBYPTEL,MARK_RESULT,MARK_REFUSEREASON,
					CALL_SPENDTIME,CALL_NEXTTIME,ID,TASK_NUMBER,NEW_RECORD_ID,FLAG,CONCLUSION];
		}
	}]
},{//2
	title:'详情',//旧户_详情
	hideTitle:true,
	suspendWidth: aw,
	type:'form',
	autoLoadSeleted : true,
	groups : [{
		columnCount : 2,
		fields:['MGR_ID','CUST_NAME','MGR_NAME','INTERVIEWEE_NAME','INTERVIEWEE_POST','INTERVIEWEE_PHONE','VISIT_TIME','VISIT_END_TIME','VISIT_START_TIME','CALL_TIME','TASK_TYPE','VISIT_TYPE','ID','TASK_NUMBER','JOIN_PERSON_ID'],
		fn:function(MGR_ID,CUST_NAME,MGR_NAME,INTERVIEWEE_NAME,INTERVIEWEE_POST,INTERVIEWEE_PHONE,VISIT_TIME,VISIT_END_TIME,VISIT_START_TIME,CALL_TIME,TASK_TYPE,VISIT_TYPE,ID,TASK_NUMBER,JOIN_PERSON_ID){
			MGR_ID.hidden = true;
			TASK_TYPE.hidden = true;
			ID.hidden = true;
			TASK_NUMBER.hidden = true;
			JOIN_PERSON_ID.hidden = true;
			CUST_NAME.readOnly = true;
			CUST_NAME.cls = "x-readOnly";
			MGR_NAME.readOnly = true;
			TASK_TYPE.readOnly = true;
			MGR_NAME.cls = "x-readOnly";
			TASK_TYPE.cls = "x-readOnly";
			INTERVIEWEE_NAME.readOnly = true;
			INTERVIEWEE_NAME.cls = "x-readOnly";
			INTERVIEWEE_POST.readOnly = true;
			INTERVIEWEE_POST.cls = "x-readOnly";
			INTERVIEWEE_PHONE.readOnly = true;
			INTERVIEWEE_PHONE.cls = "x-readOnly";
			VISIT_TIME.readOnly = true;
			VISIT_TIME.cls = "x-readOnly";
			VISIT_START_TIME.readOnly = true;
			VISIT_START_TIME.cls = "x-readOnly";
			VISIT_END_TIME.readOnly = true;
			VISIT_END_TIME.cls = "x-readOnly";
			CALL_TIME.readOnly = true;
			CALL_TIME.cls = "x-readOnly";
			VISIT_TYPE.readOnly = true;
			VISIT_TYPE.cls = "x-readOnly";
			TASK_NUMBER.readOnly = true;
			TASK_NUMBER.cls = "x-readOnly";
			return [MGR_ID,CUST_NAME,MGR_NAME,INTERVIEWEE_NAME,INTERVIEWEE_POST,INTERVIEWEE_PHONE,VISIT_TIME,VISIT_END_TIME,VISIT_START_TIME,TASK_TYPE,VISIT_TYPE,ID,TASK_NUMBER,CALL_TIME,JOIN_PERSON_ID];
		}
	},{
		   columnCount : 0.95,
		   fields:[{name:'JOIN_PERSON',text:'本次参与人员',xtype:'userchooseNew',hiddenName:'JOIN_PERSON_ID',searchType:'ALLORG',resutlWidth:100,singleSelect: false}],
		   fn:function(JOIN_PERSON){
		   	JOIN_PERSON.readOnly = true;
		   	JOIN_PERSON.cls = "x-readOnly";
			   return [JOIN_PERSON];
		   }
	},
	{
		columnCount:0.95,
		fields:['TEST'],
		fn:function(TEST){
			return[jt_form_detail];
		}
	},{//客户运营状况
		columnCount:0.95,
		fields:['CUS_STATUS'],
		fn:function(CUS_STATUS){
			CUS_STATUS.readOnly = true;
			CUS_STATUS.cls = "x-readOnly";
			return[CUS_STATUS];
		}
	},{//营运状况表现
		columnCount:2,
		fields:['ISBUSCHANGE','BUS_EXPLAIN','ISREVCHANGE','REV_EXPLAIN','ISPROCHANGE','PRO_EXPLAIN','ISSUPCHANGE','SUP_EXPLAIN','ISPURCHANGE','PUR_EXPLAIN',
		        'ISEQUCHANGE','EQU_EXPLAIN','ISOPCCHANGE','OPC_EXPLAIN','ISCOLCHANGE','COL_EXPLAIN','ISSYMCHANGE','SYM_EXPLAIN','MARK_RESULT','MARK_REFUSEREASON','CALL_NEXTTIME'],
		fn:function(ISBUSCHANGE,BUS_EXPLAIN,ISREVCHANGE,REV_EXPLAIN,ISPROCHANGE,PRO_EXPLAIN,ISSUPCHANGE,SUP_EXPLAIN,ISPURCHANGE,PUR_EXPLAIN,
		        ISEQUCHANGE,EQU_EXPLAIN,ISOPCCHANGE,OPC_EXPLAIN,ISCOLCHANGE,COL_EXPLAIN,ISSYMCHANGE,SYM_EXPLAIN,MARK_RESULT,MARK_REFUSEREASON,CALL_NEXTTIME){
		        	
			    MARK_RESULT.hidden = true;
			    MARK_REFUSEREASON.hidden = true;
			    CALL_NEXTTIME.hidden = true;
	        	ISBUSCHANGE.readOnly = true;BUS_EXPLAIN.readOnly = true;ISREVCHANGE.readOnly = true;REV_EXPLAIN.readOnly = true;ISPROCHANGE.readOnly = true;
	        	PRO_EXPLAIN.readOnly = true;ISSUPCHANGE.readOnly = true;SUP_EXPLAIN.readOnly = true;ISPURCHANGE.readOnly = true;PUR_EXPLAIN.readOnly = true;
		        ISEQUCHANGE.readOnly = true;EQU_EXPLAIN.readOnly = true;ISOPCCHANGE.readOnly = true;OPC_EXPLAIN.readOnly = true;ISCOLCHANGE.readOnly = true;
		        COL_EXPLAIN.readOnly = true;ISSYMCHANGE.readOnly = true;SYM_EXPLAIN.readOnly = true;
		        
		        ISBUSCHANGE.cls = "x-readOnly";BUS_EXPLAIN.cls = "x-readOnly";ISREVCHANGE.cls = "x-readOnly";REV_EXPLAIN.cls = "x-readOnly";ISPROCHANGE.cls = "x-readOnly";
		        PRO_EXPLAIN.cls = "x-readOnly";ISSUPCHANGE.cls = "x-readOnly";SUP_EXPLAIN.cls = "x-readOnly";ISPURCHANGE.cls = "x-readOnly";PUR_EXPLAIN.cls = "x-readOnly";
		        ISEQUCHANGE.cls = "x-readOnly";EQU_EXPLAIN.cls = "x-readOnly";ISOPCCHANGE.cls = "x-readOnly";OPC_EXPLAIN.cls = "x-readOnly";ISCOLCHANGE.cls = "x-readOnly";
		        COL_EXPLAIN.cls = "x-readOnly";ISSYMCHANGE.cls = "x-readOnly";SYM_EXPLAIN.cls = "x-readOnly";
		        
			return[ISBUSCHANGE,BUS_EXPLAIN,ISREVCHANGE,REV_EXPLAIN,ISPROCHANGE,PRO_EXPLAIN,ISSUPCHANGE,SUP_EXPLAIN,ISPURCHANGE,PUR_EXPLAIN,
			        ISEQUCHANGE,EQU_EXPLAIN,ISOPCCHANGE,OPC_EXPLAIN,ISCOLCHANGE,COL_EXPLAIN,ISSYMCHANGE,SYM_EXPLAIN,MARK_RESULT,MARK_REFUSEREASON,CALL_NEXTTIME];
		}
	},{
		   columnCount : 0.95,
		   fields:['MARK_PRODUCT'],
		   fn:function(MARK_PRODUCT){
		   	MARK_PRODUCT.readOnly = true;
		   	MARK_PRODUCT.cls = "x-readOnly";
			   return [MARK_PRODUCT];
		   }
	},{//本次拜访营销情况
		columnCount : 2,
		fields:['MARK_RESULT_OLD','MARK_REFUSEREASON_OLD'],
		fn:function(MARK_RESULT_OLD,MARK_REFUSEREASON_OLD){
			MARK_RESULT_OLD.readOnly = true;
			MARK_RESULT_OLD.cls = "x-readOnly";
			MARK_REFUSEREASON_OLD.readOnly = true;
			MARK_REFUSEREASON_OLD.cls = "x-readOnly";
			return [MARK_RESULT_OLD,MARK_REFUSEREASON_OLD];
		}
	},{
		   columnCount : 0.95,
		   fields:['RES_FOLLOWUP'],
		   fn:function(RES_FOLLOWUP){
		   	RES_FOLLOWUP.readOnly = true;
		   	RES_FOLLOWUP.cls = "x-readOnly";
			   return [RES_FOLLOWUP];
		   }
	},{
		   columnCount : 0.95,
		   fields:['RES_OTHERINFO'],
		   fn:function(RES_OTHERINFO){
		   	RES_OTHERINFO.readOnly = true;
		   	RES_OTHERINFO.cls = "x-readOnly";
			   return [RES_OTHERINFO];
		   }
	},{
		columnCount : 0.95,
		fields:['CALL_SPENDTIME'],
		fn:function(CALL_SPENDTIME){
			CALL_SPENDTIME.readOnly = true;
			CALL_SPENDTIME.cls = "x-readOnly";
			return [CALL_SPENDTIME];
		}
	}]
},{//3
	title:'CALLREPORT维护',//新户及旧户新案
	hideTitle:true,
	type:'form',
	suspendWidth: aw,
	autoLoadSeleted : true,
	groups : [
	{
		columnCount:0.95,
		fields:['TEST'],
		fn:function(TEST){
			return[dw_fieldset];
		}
	},{
		columnCount : 2,
		fields:[
		        //'MGR_NAME',
		        {name:'MGR_NAME',text:'客户经理名称',editable:false},
//		        {name:'CUST_NAME',text:'企业名称',xtype:'customerqueryqz',hiddenName:'CUST_ID',allowBlank:false,custType:'1',
//				singleSelected:true,newCust:true,callback:function(a){
//					getCurrentView().setValues({
//						'INDUST_TYPE':getCurrentView().contentPanel.form.findField("CUST_NAME").industType,
//						'LINK_PHONE':getCurrentView().contentPanel.form.findField("CUST_NAME").mobileNum,
//						'LINK_MAN':getCurrentView().contentPanel.form.findField("CUST_NAME").linkUser,
//						'TASK_TYPE':getCurrentView().contentPanel.form.findField("CUST_NAME").potentialFlag,
//						'MGR_NAME':getCurrentView().contentPanel.form.findField("CUST_NAME").mgrName,
//						'MGR_ID':getCurrentView().contentPanel.form.findField("CUST_NAME").mgrId
//					});
//				}},
		        {name:'CUST_NAME',text:'企业名称',editable:false},
				'INTERVIEWEE_NAME','INTERVIEWEE_POST','INTERVIEWEE_PHONE','VISIT_TYPE','VISIT_TIME','VISIT_START_TIME','VISIT_END_TIME','CALL_TIME','TASK_TYPE',
				'MGR_ID','ID','TASK_NUMBER','JOIN_PERSON_ID'],
		fn:function(MGR_NAME,CUST_NAME,INTERVIEWEE_NAME,INTERVIEWEE_POST,INTERVIEWEE_PHONE,VISIT_TYPE,VISIT_TIME,VISIT_START_TIME,VISIT_END_TIME,CALL_TIME,TASK_TYPE,MGR_ID,ID,TASK_NUMBER,JOIN_PERSON_ID){
			TASK_TYPE.hidden = true;
			MGR_ID.hidden = true;
			ID.hidden = true;
			JOIN_PERSON_ID.hidden = true;
			TASK_NUMBER.hidden = true;
			MGR_NAME.readOnly = true;
			MGR_NAME.cls = 'readOnly';
			CUST_NAME.readOnly = true;
			CUST_NAME.cls = 'readOnly';
			TASK_TYPE.readOnly = true;
			TASK_TYPE.cls = 'readOnly';
			return[MGR_NAME,CUST_NAME,INTERVIEWEE_NAME,INTERVIEWEE_POST,INTERVIEWEE_PHONE,VISIT_TYPE,VISIT_TIME,VISIT_START_TIME,VISIT_END_TIME,CALL_TIME,TASK_TYPE,MGR_ID,ID,TASK_NUMBER,JOIN_PERSON_ID];//案件基本信息
		}
	},{
		   columnCount : 0.95,
		   fields:[{name:'JOIN_PERSON',text:'本次参与人员',xtype:'userchooseNew',hiddenName:'JOIN_PERSON_ID',searchType:'ALLORG',resutlWidth:100,singleSelect: false}],
		   fn:function(JOIN_PERSON){
			   return [JOIN_PERSON];
		   }
	},{
		columnCount : 2,
		fields:['CUS_DOMICILE','CUS_NATURE',
		        {name:'CUS_REGTIME',text:'企业成立时间',editable:true,dataType:'date',listeners:{
		        	select:function(){
		        		var val = getCurrentView().contentPanel.getForm().findField('CUS_REGTIME').getValue();
		        		if(val.format('Y-m-d') > new Date().format('Y-m-d')){
		        			Ext.Msg.alert('提示','企业成立时间不能大于当前时间!');
		   	             return false;
		        		}else{
		        			var year = new Date().getFullYear() - val.getFullYear();
		        			var month;
		        			var age = year + '年';
		        			if(val.getMonth() > new Date().getMonth()){
		        				year = year -1;
		        				month = new Date().getMonth() + 12 - val.getMonth();
		        				age += month + '月';
		        			}else if(val.getMonth() < new Date().getMonth()){
		        				month = new Date().getMonth() - val.getMonth();
		        				age += month + '月';
		        			}
		        			getCurrentView().contentPanel.getForm().findField('CUS_OPERATEAGE').setValue(age);
		        		}
		        	}
		        }},
		        'CUS_OPERATEAGE',
		        {name:'CUS_SITEOPERATETIME',text:'现址经营开始时间',editable:true,dataType:'date',listeners:{
		        	select:function(){
		        		var val = getCurrentView().contentPanel.getForm().findField('CUS_SITEOPERATETIME').getValue();
		        		if(val.format('Y-m-d') > new Date().format('Y-m-d')){
		        			Ext.Msg.alert('提示','现址经营开始时间不能大于当前时间!');
		   	             return false;
		        		}else{
		        			var year = new Date().getFullYear() - val.getFullYear();
		        			var month;
		        			var age = year + '年';
		        			if(val.getMonth() > new Date().getMonth()){
		        				year = year -1;
		        				month = new Date().getMonth() + 12 - val.getMonth();
		        				age += month + '月';
		        			}else if(val.getMonth() < new Date().getMonth()){
		        				month = new Date().getMonth() - val.getMonth();
		        				age += month + '月';
		        			}
		        			getCurrentView().contentPanel.getForm().findField('CUS_SITEOPERATEAGE').setValue(age);
		        		}
		        	}
		        }},
		        'CUS_SITEOPERATEAGE', 
		        {name:'CUS_ONMARK',text:'是否上市',editable:true,translateType:'IF_FLAG',listeners:{
//		    		select:function(combo,record){
//		    			var v = this.getValue();
////		    			if(v=='1'){
////		    				getCurrentView().contentPanel.getForm().findField('CUS_ONMARKPLACE').show(true);
////		    			}else{
////		    				getCurrentView().contentPanel.getForm().findField('CUS_ONMARKPLACE').hide(true);
////		    				getCurrentView().contentPanel.getForm().findField('CUS_ONMARKPLACE').setValue(null);
////		    			}
//					}
		        }},
		        'CUS_CNTPEOPLE','CUS_OWNBUSI','CUS_MAJORPRODUCT','CUS_BUSISTATUS','CUS_MAJORRIVAL',
		        'ID','TASK_NUMBER'],
		fn:function(CUS_DOMICILE,CUS_NATURE,CUS_REGTIME,CUS_OPERATEAGE,CUS_SITEOPERATETIME,CUS_SITEOPERATEAGE,CUS_ONMARK,CUS_CNTPEOPLE,CUS_OWNBUSI,
					CUS_MAJORPRODUCT,CUS_BUSISTATUS,CUS_MAJORRIVAL,ID,TASK_NUMBER){
					ID.hidden = true;
					TASK_NUMBER.hidden = true;
					CUS_OPERATEAGE.readOnly = true;
					CUS_OPERATEAGE.cls = 'x-readOnly';
					CUS_SITEOPERATEAGE.readOnly = true;
					CUS_SITEOPERATEAGE.cls = 'x-readOnly';
				return[CUS_DOMICILE,CUS_NATURE,CUS_REGTIME,CUS_OPERATEAGE,CUS_SITEOPERATETIME,CUS_SITEOPERATEAGE,CUS_ONMARK,CUS_CNTPEOPLE,CUS_OWNBUSI,
						CUS_MAJORPRODUCT,CUS_BUSISTATUS,CUS_MAJORRIVAL,ID,TASK_NUMBER];//企业基本信息
		}
	},{
		columnCount:0.95,
		fields : ['CUS_OPERATEADDR'],
		fn : function(CUS_OPERATEADDR){
			return [CUS_OPERATEADDR];//经营地址
		}
	},{
		columnCount:2,
		fields : [{name:'CUS_LEGALPERSON',text:'法定代表人姓名'},'CUS_LEGALPTEL',
			        {name:'CUS_ACCOUNTPERSON',text:'财务负责人姓名'},'CUS_ACCOUNTPTEL',
			        {name:'CUS_OPERATEPERSON',text:'实际控制人姓名'},'CUS_OPERATEPTEL',
			        'CUS_OPERATEPWAGE','CUS_OPERATEPMAGE','ID','TASK_NUMBER'],
		fn : function(CUS_LEGALPERSON,CUS_LEGALPTEL,CUS_ACCOUNTPERSON,CUS_ACCOUNTPTEL,
				CUS_OPERATEPERSON,CUS_OPERATEPTEL,CUS_OPERATEPWAGE,CUS_OPERATEPMAGE,ID,TASK_NUMBER){
				ID.hidden = true;
				TASK_NUMBER.hidden = true;	
				CUS_LEGALPERSON.hidden = true;
				CUS_LEGALPTEL.hidden = true;
				CUS_ACCOUNTPERSON.hidden = true;
				CUS_ACCOUNTPTEL.hidden = true;
				CUS_OPERATEPERSON.hidden = true;
				CUS_OPERATEPTEL.hidden = true;
				CUS_OPERATEPWAGE.hidden = true;
				CUS_OPERATEPMAGE.hidden = true;
			return [CUS_LEGALPERSON,CUS_LEGALPTEL,CUS_ACCOUNTPERSON,CUS_ACCOUNTPTEL,
					CUS_OPERATEPERSON,CUS_OPERATEPTEL,CUS_OPERATEPWAGE,CUS_OPERATEPMAGE,ID,TASK_NUMBER];//经营地址
		}
	},{
		columnCount:0.945,
		fields : ['DCRB_FLOW'],
		fn : function(DCRB_FLOW){
			return [DCRB_FLOW];
		}
	},{
		columnCount:0.945,
		fields : ['TEST'],
		fn : function(TEST){
			return [newPanel_13];//公司相关人员信息
		}
	},{
		columnCount:0.945,
		fields : ['DCRB_MAJORSHOLDER'],
		fn : function(DCRB_MAJORSHOLDER){
			return [DCRB_MAJORSHOLDER];//主要股东/经营团队补充说明
		}
	},{
		columnCount:0.945,
		fields : ['TEST'],
		fn : function(TEST){
			return [newPanel_2];//主要固定资产
		}
	},{
		columnCount:0.945,
		fields : ['DCRB_FIXEDASSETS'],
		fn : function(DCRB_FIXEDASSETS){
			return [DCRB_FIXEDASSETS];//固定资产补充说明
		}
	},{
		columnCount:0.945,
		fields : ['TEST'],
		fn : function(TEST){
			return [newPanel_3];//营收获利情况
		}
	},{
		columnCount:0.945,
		fields : ['DCRB_PROFIT'],
		fn : function(DCRB_PROFIT){
			return [DCRB_PROFIT];//营收/获利补充说明
		}
	},{
		columnCount:0.945,
		fields : ['TEST'],
		fn : function(TEST){
			return [newPanel_4];//原材料采购情况
		}
	},{
		columnCount:0.945,
		fields : ['TEST'],
		fn : function(TEST){
			return [newPanel_5];//产品销售状况
		}
	},{
		columnCount:0.945,
		fields : ['DCRB_SYMBIOSIS'],
		fn : function(DCRB_SYMBIOSIS){
			return [DCRB_SYMBIOSIS];//上下游合作交易情况补充说明
		}
	},{
		columnCount:0.945,
		fields : ['TEST'],
		fn : function(TEST){
			return [newPanel_6];//存款往来银行表
		}
	},{
		columnCount:0.945,
		fields : ['TEST'],
		fn : function(TEST){
			return [newPanel_7];//贷款往来银行表
		}
	},{
		columnCount:0.945,
		fields : ['DCRB_OTHERTRADE'],
		fn : function(DCRB_OTHERTRADE){
			return [DCRB_OTHERTRADE];
		}
	},{
		columnCount:0.945,
		fields : ['TEST'],
		fn : function(TEST){
			return [newPanel_8];//拟承做存款产品
		}
	},{
		columnCount:0.945,
		fields : ['TEST'],
		fn : function(TEST){
			return [newPanel_9];//拟申请外汇产品额度
		}
	},{
		columnCount:0.945,
		fields : ['TEST'],
		fn : function(TEST){
			return [newPanel_11];//拟申请授信产品--新户
		}
	},{
		columnCount:0.945,
		fields : ['TEST'],
		fn : function(TEST){
			return [newPanel_12];//担保品信息
		}
	},{
		columnCount : 0.945,
		fields:['DCRB_MYSELFTRADE'],
		fn:function(DCRB_MYSELFTRADE){
			return[DCRB_MYSELFTRADE];//本行往来业务补充说明
		}
	},{
		columnCount : 0.945,
		fields:['RES_OTHERINFO'],
		fn:function(RES_OTHERINFO){
			return[RES_OTHERINFO];//其他补充信息
		}
	},{
		columnCount : 0.945,
		fields:['RES_FOLLOWUP'],
		fn:function(RES_FOLLOWUP){
			return[RES_FOLLOWUP];//跟进事项
		}
	},{//补充栏位
		columnCount : 2,
		fields:['RES_CUSTSOURCE','RES_CUSTSOURCEDATE','IF_OWNBANKCUST','CALL_SPENDTIME','RES_CASEBYPERSON','RES_CASEBYPTEL',
		        {name:'MARK_RESULT',text:'拜访结果',editable:true,allowBlank:false,translateType:'VISIT_RESULT_QS',listeners:{
		    		select:function(combo,record){
			    			var v = this.getValue();
			    			if(v=='3'){
			    				getCurrentView().contentPanel.getForm().findField('MARK_REFUSEREASON').show(true);
			    				getCurrentView().contentPanel.getForm().findField('CALL_NEXTTIME').hide(true);
			    				getCurrentView().contentPanel.getForm().findField('CALL_NEXTTIME').setValue(null);
			    			}else if(v  == '4'){
			    				getCurrentView().contentPanel.getForm().findField('CALL_NEXTTIME').show(true);
			    				getCurrentView().contentPanel.getForm().findField('MARK_REFUSEREASON').hide(true);
			    				getCurrentView().contentPanel.getForm().findField('MARK_REFUSEREASON').setValue(null);
			    			}else{
			    				getCurrentView().contentPanel.getForm().findField('MARK_REFUSEREASON').hide(true);
			    				getCurrentView().contentPanel.getForm().findField('MARK_REFUSEREASON').setValue(null);
			    				getCurrentView().contentPanel.getForm().findField('CALL_NEXTTIME').hide(true);
			    				getCurrentView().contentPanel.getForm().findField('CALL_NEXTTIME').setValue(null);
			    			}
						}
		    	}},
		    	'MARK_REFUSEREASON','CALL_NEXTTIME','ID','TASK_NUMBER','NEW_RECORD_ID','FLAG','CONCLUSION'],
		fn:function(RES_CUSTSOURCE,RES_CUSTSOURCEDATE,IF_OWNBANKCUST,RES_CASEBYPERSON,RES_CASEBYPTEL,MARK_RESULT,MARK_REFUSEREASON,
				CALL_SPENDTIME,CALL_NEXTTIME,ID,TASK_NUMBER,NEW_RECORD_ID,FLAG,CONCLUSION){
				ID.hidden = true;
				TASK_NUMBER.hidden = true;
				NEW_RECORD_ID.hidden = true;
				FLAG.hidden = true;
				CALL_NEXTTIME.hidden = true;
				CONCLUSION.hidden=true;
				return[RES_CUSTSOURCE,RES_CUSTSOURCEDATE,IF_OWNBANKCUST,RES_CASEBYPERSON,RES_CASEBYPTEL,MARK_RESULT,MARK_REFUSEREASON,
						CALL_SPENDTIME,CALL_NEXTTIME,ID,TASK_NUMBER,NEW_RECORD_ID,FLAG,CONCLUSION];
		}
	}],
	formButtons : [{
		text:'保存',
		id:'fk_toSave',
		fn : function(formPanel,basicForm){
			 if (!formPanel.getForm().isValid()) {
	               Ext.MessageBox.alert('提示','输入有误,请检查输入项');
	               return false;
	           };
           var value = formPanel.form.findField("MARK_RESULT").getValue();
           var callTime = formPanel.form.findField("CALL_TIME").getValue();
	       var callNexttime = formPanel.form.findField("CALL_NEXTTIME").getValue();
           if(value == '3'){
        	   if(formPanel.form.findField("MARK_REFUSEREASON").getValue() == '' || formPanel.form.findField("MARK_REFUSEREASON").getValue() == null){
        		   Ext.MessageBox.alert('提示','请填写[拒绝原因]');
	               return false;
        	   }
	        }
           if(value == '4'){//改期回拨
//        	   if(formPanel.form.findField("CALL_NEXTTIME").getValue() == '' || formPanel.form.findField("CALL_NEXTTIME").getValue() == null){
//        		   Ext.MessageBox.alert('提示','请填写[预约下次拜访时间]');
//	               return false;
//        	   }
        	   if(callNexttime != null && callNexttime != ''){
	        	   	if(callNexttime.format('Y-m-d') < new Date().format('Y-m-d')){
	        	   		Ext.MessageBox.alert('提示','预计下次拜访时间不能小于当前时间!');
			            return false;
	        	   	}
	           }
           }
           if(callTime != null && callTime != ''){
	           if(callTime.format('Y-m-d') > new Date().format('Y-m-d')){
		        	 Ext.MessageBox.alert('提示','实际拜访日期不能大于当前日期!');
		             return false;
		       }
           }
	        
	        var data = formPanel.getForm().getFieldValues();
			var commintData = translateDataKey(data,1);
			if(value == 4 ){
				Ext.MessageBox.confirm('提示','改期拜访在点击【保存】时不产生拜访任务和日程,如需要请点击【提交】按钮。是否保存?',function(buttonId){
					if(buttonId.toLowerCase() == "no"){
					   return false;
					} 
					Ext.Msg.wait('正在处理，请稍后......','系统提示');
					Ext.Ajax.request({
							url : basepath + '/ocrmFInterviewNewRecord.json',
							method : 'POST',
							params : commintData,
							success : function(response) {
								Ext.Msg.alert('提示', '操作成功');
								reloadCurrentData();
								hideCurrentView();
							},
							failure : function() {
								Ext.Msg.alert('提示', '操作失败');
								reloadCurrentData();
								hideCurrentView();
							}
					});
				});	
			}else{
				Ext.Msg.wait('正在处理，请稍后......','系统提示');
				Ext.Ajax.request({
						url : basepath + '/ocrmFInterviewNewRecord.json',
						method : 'POST',
						params : commintData,
						success : function(response) {
							Ext.Msg.alert('提示', '操作成功');
							reloadCurrentData();
							hideCurrentView();
						},
						failure : function() {
							Ext.Msg.alert('提示', '操作失败');
							reloadCurrentData();
							hideCurrentView();
						}
				});
			}
		}
	},{
		text:'提交',
		fn : function(formPanel,basicForm){
			 if (!formPanel.getForm().isValid()) {
	               Ext.MessageBox.alert('提示','输入有误,请检查输入项');
	               return false;
	           };
           var value = formPanel.form.findField("MARK_RESULT").getValue();
           var callTime = formPanel.form.findField("CALL_TIME").getValue();
	       var callNexttime = formPanel.form.findField("CALL_NEXTTIME").getValue();
	       var conclusion=formPanel.form.findField("CONCLUSION").getValue();
	       var task_type=formPanel.form.findField("TASK_TYPE").getValue();
	       if( task_type==1 && conclusion==1 ){
	       	   Ext.MessageBox.alert('提示','该客户未完成客户筛选,不允许提交CALLREPORT');
	               return false;
	       }
           if(value == '3'){
        	   if(formPanel.form.findField("MARK_REFUSEREASON").getValue() == '' || formPanel.form.findField("MARK_REFUSEREASON").getValue() == null){
        		   Ext.MessageBox.alert('提示','请填写[拒绝原因]');
	               return false;
        	   }
	        }
           if(value == '4'){
        	   if(formPanel.form.findField("CALL_NEXTTIME").getValue() == '' || formPanel.form.findField("CALL_NEXTTIME").getValue() == null){
        		   Ext.MessageBox.alert('提示','请填写[预约下次拜访时间]');
	               return false;
        	   }
               if(callNexttime != null && callNexttime != ''){
	        	   	if(callNexttime.format('Y-m-d') < new Date().format('Y-m-d')){
	        	   		Ext.MessageBox.alert('提示','预计下次拜访时间不能小于当前时间!');
			            return false;
	        	   	}
	           }
           }
            if(callTime == '' || callTime == null){
             	   Ext.MessageBox.alert('提示','请填写[实际拜访日期]');
	               return false;
            }
	        if(callTime.format('Y-m-d') > new Date().format('Y-m-d')){
	        	 Ext.MessageBox.alert('提示','实际拜访日期不能大于当前时间!');
	             return false;
	        }
	        //提交是要填写拜访结果
	        var markResult = formPanel.form.findField("MARK_RESULT").getValue();
	        if(markResult == '' || markResult == null){
          	   Ext.MessageBox.alert('提示','请填写[拜访结果]');
	               return false;
	        }
	        var data = formPanel.getForm().getFieldValues();
			var commintData = translateDataKey(data,1);
			Ext.Msg.wait('正在处理，请稍后......','系统提示');
			Ext.Ajax.request({
					url : basepath + '/ocrmFInterviewNewRecord!save.json',
					method : 'GET',
					params : commintData,
					success : function(response) {
						 var ret = Ext.decode(response.responseText);
						 var instanceid = ret.instanceid;//流程实例ID
						 var currNode = ret.currNode;//当前节点
						 var nextNode = ret.nextNode;//下一步节点
						 selectUserList(instanceid,currNode,nextNode);//选择下一步办理人
					}
			});
		}
	},{
		text:'关闭',
		fn : function(formPanel,basicForm){
			 reloadCurrentData();
			 hideCurrentView();
		}
	}]
},{
//	title : '新增股东及持股比例',
//	hideTitle : true,
//	type : 'form',
//	autoLoadSeleted : true,
//	frame : true,
//	groups : [{
//	columnCount : 2 ,
//	fields : [	
//	            {name : 'ID',hidden : true},
//	            {name : 'TASK_NUMBER',hidden : true},
//	            {name : 'M_SPONSOR',text  : '出资人',hidden : false,maxLength:50},
//	            {name : 'M_RATIO',text  : '出资比例(%)',hidden : false,vtype:'percent',maxLength:7},
//	            {name : 'M_MONEY',text  : '出资金额(人民币/千元)',vtype:'money',maxLength:20}
//				],
//			fn : function(ID,TASK_NUMBER,M_SPONSOR,M_RATIO,M_MONEY){
//				return [ID,TASK_NUMBER,M_SPONSOR,M_RATIO,M_MONEY];
//			}
//	}],
//	formButtons:[{
//		text : '保存并继续增加',
//		fn : function(formPanel,baseform){						
//			if(!baseform.isValid())
//				{
//					Ext.Msg.alert('提示','请输入完整或正确的格式！');
//					return false;
//				}
//			    var commintData = translateDataKey(baseform.getFieldValues(),_app.VIEWCOMMITTRANS);
//			    if(commintData.mRatio!=null && commintData.mRatio!=''){
//			    	var v = parseFloat(bl.PEC)+parseFloat(commintData.mRatio);
//			    	if(v >100){
//			    		 Ext.MessageBox.alert('提示','所有股东出资比例之和不得超过100%');
//			             return false;
//			    	}
//			    }
//				Ext.Msg.wait('正在提交数据，请稍等...','提示');
//			    Ext.Ajax.request({
//					url : basepath + '/ocrmFInterviewShareholder.json?flag=0',//0新增1修改
//					method : 'POST',
//					params : commintData,
//					success : function() {
//						Ext.Msg.hide(); 
////						Ext.Msg.alert('提示',"操作成功!");
////						showCustomerViewByTitle('CALLREPORT维护');
//						/**
//						 * 回填CALLREPORT维护界面数据
//						 */
////						setBaseValue(getCurrentView().contentPanel.getForm());
//						/**
//						 * 计算出资比列
//						 */
//						if(commintData.mRatio!=null && commintData.mRatio!=''){
//							getmratio(commintData.mRatio);
//						}
//						/**
//						 * 清除页面数据
//						 */
//						cleanData(getCurrentView().contentPanel.getForm(),commintData);
//					},
//					failure : function(response) {
//						var resultArray = Ext.util.JSON.decode(response.status);
//				 		if(resultArray == 403) {
//			           		Ext.Msg.alert('提示', response.responseText);
//				 		}else{
//							Ext.Msg.alert('提示', '操作失败,失败原因:' + response.responseText);
//		 				}
//					}
//				});
//			}
//	},{
//		text : '保存并退出',
//		fn : function(formPanel,baseform){						
//			if(!baseform.isValid())
//				{
//					Ext.Msg.alert('提示','请输入完整或正确的格式！');
//					return false;
//				}
//			    var commintData = translateDataKey(baseform.getFieldValues(),_app.VIEWCOMMITTRANS);
//			    if(commintData.mRatio!=null && commintData.mRatio!=''){
//			    	var v = parseFloat(bl.PEC)+parseFloat(commintData.mRatio);
//			    	if(v >100){
//			    		 Ext.MessageBox.alert('提示','所有股东出资比例之和不得超过100%');
//			             return false;
//			    	}
//			    }
//				Ext.Msg.wait('正在提交数据，请稍等...','提示');
//			    Ext.Ajax.request({
//					url : basepath + '/ocrmFInterviewShareholder.json?flag=0',//0新增1修改
//					method : 'POST',
//					params : commintData,
//					success : function() {
//						Ext.Msg.hide(); 
////						Ext.Msg.alert('提示',"操作成功!");
//						showCustomerViewByTitle('CALLREPORT维护');
//						/**
//						 * 回填CALLREPORT维护界面数据
//						 */
//						setBaseValue(getCurrentView().contentPanel.getForm());
//					},
//					failure : function(response) {
//						var resultArray = Ext.util.JSON.decode(response.status);
//				 		if(resultArray == 403) {
//			           		Ext.Msg.alert('提示', response.responseText);
//				 		}else{
//							Ext.Msg.alert('提示', '操作失败,失败原因:' + response.responseText);
//		 				}
//					}
//				});
//			}
//	},{
//		text : '取消',
//		fn : function(formPanel,basicForm){
//			showCustomerViewByTitle('CALLREPORT维护');
//			/**
//			 * 回填CALLREPORT维护界面数据
//			 */
//			setBaseValue(getCurrentView().contentPanel.getForm());
//		}
//	}]
//},{
//	title : '修改股东及持股比例',
//	hideTitle : true,
//	type : 'form',
//	autoLoadSeleted : true,
//	frame : true,
//	groups : [{
//	columnCount : 2 ,
//	fields : [	
//	            {name : 'ID',hidden : true},
//	            {name : 'TASK_NUMBER',hidden : true},
//	            {name : 'M_SPONSOR',text  : '出资人',hidden : false,maxLength:50},
//	            {name : 'M_RATIO',text  : '出资比例(%)',hidden : false,vtype:'percent',maxLength:7},
//	            {name : 'M_MONEY',text  : '出资金额(人民币/千元)',vtype:'money',maxLength:20}
//				],
//			fn : function(ID,TASK_NUMBER,M_SPONSOR,M_RATIO,M_MONEY){
//				return [ID,TASK_NUMBER,M_SPONSOR,M_RATIO,M_MONEY];
//			}
//	}],
//	formButtons:[{
//		text : '保存',
//		//保存数据					 
//		fn : function(formPanel,baseform){						
//			if(!baseform.isValid())
//				{
//					Ext.Msg.alert('提示','请输入完整或正确的格式！');
//					return false;
//				}
//			    var commintData = translateDataKey(baseform.getFieldValues(),_app.VIEWCOMMITTRANS);
//			    if(commintData.mRatio!=null && commintData.mRatio!=''){
//			    	var v = parseFloat(bl.PEC)+parseFloat(commintData.mRatio);
//			    	if(v >100){
//			    		 Ext.MessageBox.alert('提示','所有股东出资比例之和不得超过100%');
//			             return false;
//			    	}
//			    }
//				Ext.Msg.wait('正在提交数据，请稍等...','提示');
//			    Ext.Ajax.request({
//	 				url : basepath + '/ocrmFInterviewShareholder.json?flag=1',
//	 				method : 'POST',
//	 				params : commintData,
//					success : function() {
//						Ext.Msg.hide(); 
////						Ext.Msg.alert('提示',"操作成功!");
//						showCustomerViewByTitle('CALLREPORT维护');
//						/**
//						 * 回填CALLREPORT维护界面数据
//						 */
//						setBaseValue(getCurrentView().contentPanel.getForm());
//					},
//					failure : function(response) {
//						var resultArray = Ext.util.JSON.decode(response.status);
//				 		if(resultArray == 403) {
//			           		Ext.Msg.alert('提示', response.responseText);
//				 		}else{
//							Ext.Msg.alert('提示', '操作失败,失败原因:' + response.responseText);
//		 				}
//					}
//				});
//			}
//	},{
//		text : '取消',
//		fn : function(formPanel,basicForm){
//			showCustomerViewByTitle('CALLREPORT维护');
//			/**
//			 * 回填CALLREPORT维护界面数据
//			 */
//			setBaseValue(getCurrentView().contentPanel.getForm());
//		}
//	}]
//},{
	//4
	title : '新增主要固定资产',
	hideTitle : true,
	type : 'form',
	autoLoadSeleted : true,
	frame : true,
	groups : [{
	columnCount : 2 ,
	fields : [	
	            {name : 'ID',hidden : true},
	            {name : 'TASK_NUMBER',hidden : true},
	            {name : 'F_HTYPE',text  : '房产类型',hidden : false,translateType:'F_HTYPE',editable:true},
	            {name : 'F_OTYPE',text  : '持有类型',hidden : false,translateType:'F_OTYPE',editable:true},
	            {name : 'F_HOLDER',text :'持有人',hidden : false,editable:true},
       		 	{name : 'F_REGION',text :'所在区域',hidden : false,editable:true},
	            {name : 'F_AREA',text  : '面积（平方米）',vtype:'money',maxLength:8},
	            {name : 'F_UTYPE',text  : '使用状况',translateType:'F_UTYPE',editable:true},
	            {name : 'F_ASSESS',text  : '估价(人民币/千元)',vtype:'money',maxLength:20},
	            {name : 'F_SECURED',text  : '是否已抵押',hidden : false,translateType:'F_SECURED',editable:true}
				],
			fn : function(ID,TASK_NUMBER,F_HTYPE,F_OTYPE,F_HOLDER,F_REGION,F_AREA,F_UTYPE,F_ASSESS,F_SECURED){
				return [ID,TASK_NUMBER,F_HTYPE,F_OTYPE,F_HOLDER,F_REGION,F_AREA,F_UTYPE,F_ASSESS,F_SECURED];
			}
	},{
		   columnCount : 1,
		   fields:[{name:'F_MEMO',text:'备注',xtype:'textarea',maxLength:50}],
		   fn:function(F_MEMO){
			   return [F_MEMO];
		   }
	}],
	formButtons:[{
		text : '保存并继续增加',
		fn : function(formPanel,baseform){						
			if(!baseform.isValid())
				{
					Ext.Msg.alert('提示','请输入完整或正确的格式！');
					return false;
				}
			    var commintData = translateDataKey(baseform.getFieldValues(),_app.VIEWCOMMITTRANS);
				Ext.Msg.wait('正在提交数据，请稍等...','提示');
			    Ext.Ajax.request({
					url : basepath + '/ocrmFInterviewFixedasset.json?flag=0',//0新增1修改
					method : 'POST',
					params : commintData,
					success : function() {
						Ext.Msg.hide(); 
//						Ext.Msg.alert('提示',"操作成功!");
//						showCustomerViewByTitle('CALLREPORT维护');
						/**
						 * 回填CALLREPORT维护界面数据
						 */
//						setBaseValue(getCurrentView().contentPanel.getForm());
						/**
						 * 清除页面数据
						 */
						cleanData(getCurrentView().contentPanel.getForm(),commintData);
					},
					failure : function(response) {
						var resultArray = Ext.util.JSON.decode(response.status);
				 		if(resultArray == 403) {
			           		Ext.Msg.alert('提示', response.responseText);
				 		}else{
							Ext.Msg.alert('提示', '操作失败,失败原因:' + response.responseText);
		 				}
					}
				});
			}
	},{
		text : '保存并退出',
		fn : function(formPanel,baseform){						
			if(!baseform.isValid())
				{
					Ext.Msg.alert('提示','请输入完整或正确的格式！');
					return false;
				}
			    var commintData = translateDataKey(baseform.getFieldValues(),_app.VIEWCOMMITTRANS);
				Ext.Msg.wait('正在提交数据，请稍等...','提示');
			    Ext.Ajax.request({
					url : basepath + '/ocrmFInterviewFixedasset.json?flag=0',//0新增1修改
					method : 'POST',
					params : commintData,
					success : function() {
						Ext.Msg.hide(); 
//						Ext.Msg.alert('提示',"操作成功!");
						showCustomerViewByTitle('CALLREPORT维护');
						/**
						 * 回填CALLREPORT维护界面数据
						 */
						setBaseValue(getCurrentView().contentPanel.getForm());
					},
					failure : function(response) {
						var resultArray = Ext.util.JSON.decode(response.status);
				 		if(resultArray == 403) {
			           		Ext.Msg.alert('提示', response.responseText);
				 		}else{
							Ext.Msg.alert('提示', '操作失败,失败原因:' + response.responseText);
		 				}
					}
				});
			}
	},{
		text : '取消',
		fn : function(formPanel,basicForm){
			showCustomerViewByTitle('CALLREPORT维护');
			/**
			 * 回填CALLREPORT维护界面数据
			 */
			setBaseValue(getCurrentView().contentPanel.getForm());
		}
	}]
},{//5
	title : '修改主要固定资产',
	hideTitle : true,
	type : 'form',
	autoLoadSeleted : true,
	frame : true,
	groups : [{
	columnCount : 2 ,
	fields : [	
	            {name : 'ID',hidden : true},
	            {name : 'TASK_NUMBER',hidden : true},
	            {name : 'F_HTYPE',text  : '房产类型',hidden : false,translateType:'F_HTYPE',editable:true},
	            {name : 'F_OTYPE',text  : '持有类型',hidden : false,translateType:'F_OTYPE',editable:true},
	            {name : 'F_HOLDER',text :'持有人',hidden : false,editable:true},
       		 	{name : 'F_REGION',text :'所在区域',hidden : false,editable:true},
	            {name : 'F_AREA',text  : '面积（平方米）',vtype:'money',maxLength:12},
	            {name : 'F_UTYPE',text  : '使用状况',translateType:'F_UTYPE',editable:true},
	            {name : 'F_ASSESS',text  : '估价(人民币/千元)',vtype:'money',maxLength:20},
	            {name : 'F_SECURED',text  : '是否已抵押',hidden : false,translateType:'F_SECURED',editable:true}
				],
			fn : function(ID,TASK_NUMBER,F_HTYPE,F_OTYPE,F_HOLDER,F_REGION,F_AREA,F_UTYPE,F_ASSESS,F_SECURED){
				return [ID,TASK_NUMBER,F_HTYPE,F_OTYPE,F_HOLDER,F_REGION,F_AREA,F_UTYPE,F_ASSESS,F_SECURED];
			}
	},{
		   columnCount : 1,
		   fields:[{name:'F_MEMO',text:'备注',xtype:'textarea',maxLength:50}],
		   fn:function(F_MEMO){
			   return [F_MEMO];
		   }
	}],
	formButtons:[{
		text : '保存',
		//保存数据					 
		fn : function(formPanel,baseform){						
			if(!baseform.isValid())
				{
					Ext.Msg.alert('提示','请输入完整或正确的格式！');
					return false;
				}
			    var commintData = translateDataKey(baseform.getFieldValues(),_app.VIEWCOMMITTRANS);
//			    debugger;
				Ext.Msg.wait('正在提交数据，请稍等...','提示');
			    Ext.Ajax.request({
	 				url : basepath + '/ocrmFInterviewFixedasset.json?flag=1',
	 				method : 'POST',
	 				params : commintData,
					success : function() {
						Ext.Msg.hide(); 
//						Ext.Msg.alert('提示',"操作成功!");
						showCustomerViewByTitle('CALLREPORT维护');
						/**
						 * 回填CALLREPORT维护界面数据
						 */
						setBaseValue(getCurrentView().contentPanel.getForm());
					},
					failure : function(response) {
						var resultArray = Ext.util.JSON.decode(response.status);
				 		if(resultArray == 403) {
			           		Ext.Msg.alert('提示', response.responseText);
				 		}else{
							Ext.Msg.alert('提示', '操作失败,失败原因:' + response.responseText);
		 				}
					}
				});
			}
	},{
		text : '取消',
		fn : function(formPanel,basicForm){
			showCustomerViewByTitle('CALLREPORT维护');
			/**
			 * 回填CALLREPORT维护界面数据
			 */
			setBaseValue(getCurrentView().contentPanel.getForm());
		}
	}]
},{//6
	title : '新增营收获利情况',
	hideTitle : true,
	type : 'form',
	autoLoadSeleted : true,
	frame : true,
	groups : [{
	labelWidth:150,
	columnCount : 2 ,
	fields : [	
	            {name : 'ID',hidden : true},
	            {name : 'TASK_NUMBER',hidden : true},
	            {name : 'P_YEARS',text  : '开始年份',hidden : false,maxLength:20,dataType:'date',format:'Y-m'},
	            {name : 'P_YEARS_END',text  : '结束年份',hidden : false,maxLength:20,dataType:'date',format:'Y-m'},
	            {name : 'P_REVENUE',text  : '营收(人民币/千元)',hidden : false,vtype:'money',maxLength:20},
	            {name : 'P_GROSS',text  : '毛利率(%)',vtype:'percent',maxLength:20},
	            {name : 'P_PNET',text  : '税后净利率(%)',vtype:'percent',maxLength:20}
				],
			fn : function(ID,TASK_NUMBER,P_YEARS,P_YEARS_END,P_REVENUE,P_GROSS,P_PNET){
				return [ID,TASK_NUMBER,P_YEARS,P_YEARS_END,P_REVENUE,P_GROSS,P_PNET];
			}
	},{
		   columnCount : 1,
		   fields:[{name:'P_MEMO',text:'备注',xtype:'textarea',maxLength:50}],
		   fn:function(P_MEMO){
			   return [P_MEMO];
		   }
	}],
	formButtons:[{
		text : '保存并继续增加',
		fn : function(formPanel,baseform){						
			if(!baseform.isValid())
				{
					Ext.Msg.alert('提示','请输入完整或正确的格式！');
					return false;
				}
			    var commintData = translateDataKey(baseform.getFieldValues(),_app.VIEWCOMMITTRANS);
			    if(commintData.pYears > commintData.pYearsEnd){
			    	Ext.Msg.alert('提示','开始年份不能大于结束年份！');
					return false;
			    }
			    if(commintData.pYearsEnd != undefined && commintData.pYears == undefined){
			    	Ext.Msg.alert('提示','开始年份不能为空！');
					return false;
			    }
			    if(commintData.pYearsEnd != undefined){
			    	if(commintData.pYearsEnd.format('Y-m') > new Date().format('Y-m')){   
			    		Ext.Msg.alert('提示','结束年份不能大于当前时间！');
						return false;
			    	}
			    }
			    if(commintData.pYears != undefined){
			    	if(commintData.pYears.format('Y-m') > new Date().format('Y-m')){   
			    		Ext.Msg.alert('提示','开始年份不能大于当前当前时间！');
						return false;
			    	}
			    }
				Ext.Msg.wait('正在提交数据，请稍等...','提示');
			    Ext.Ajax.request({
					url : basepath + '/ocrmFInterviewProfit.json?flag=0',//0新增1修改
					method : 'POST',
					params : commintData,
					success : function() {
						Ext.Msg.hide(); 
//						Ext.Msg.alert('提示',"操作成功!");
//						showCustomerViewByTitle('CALLREPORT维护');
						/**
						 * 回填CALLREPORT维护界面数据
						 */
//						setBaseValue(getCurrentView().contentPanel.getForm());
						/**
						 * 清除页面数据
						 */
						cleanData(getCurrentView().contentPanel.getForm(),commintData);
					},
					failure : function(response) {
						var resultArray = Ext.util.JSON.decode(response.status);
				 		if(resultArray == 403) {
			           		Ext.Msg.alert('提示', response.responseText);
				 		}else{
							Ext.Msg.alert('提示', '操作失败,失败原因:' + response.responseText);
		 				}
					}
				});
			}
	},{
		text : '保存并退出',
		fn : function(formPanel,baseform){						
			if(!baseform.isValid())
				{
					Ext.Msg.alert('提示','请输入完整或正确的格式！');
					return false;
				}
			    var commintData = translateDataKey(baseform.getFieldValues(),_app.VIEWCOMMITTRANS);
			    if(commintData.pYears > commintData.pYearsEnd){
			    	Ext.Msg.alert('提示','开始年份不能大于结束年份！');
					return false;
			    }
			    if(commintData.pYearsEnd != undefined && commintData.pYears == undefined){
			    	Ext.Msg.alert('提示','开始年份不能为空！');
					return false;
			    }
			    if(commintData.pYearsEnd != undefined){
			    	if(commintData.pYearsEnd.format('Y-m') > new Date().format('Y-m')){   
			    		Ext.Msg.alert('提示','结束年份不能大于当前时间！');
						return false;
			    	}
			    }
			    if(commintData.pYears != undefined){
			    	if(commintData.pYears.format('Y-m') > new Date().format('Y-m')){   
			    		Ext.Msg.alert('提示','开始年份不能大于当前当前时间！');
						return false;
			    	}
			    }
				Ext.Msg.wait('正在提交数据，请稍等...','提示');
			    Ext.Ajax.request({
					url : basepath + '/ocrmFInterviewProfit.json?flag=0',//0新增1修改
					method : 'POST',
					params : commintData,
					success : function() {
						Ext.Msg.hide(); 
//						Ext.Msg.alert('提示',"操作成功!");
						showCustomerViewByTitle('CALLREPORT维护');
						/**
						 * 回填CALLREPORT维护界面数据
						 */
						setBaseValue(getCurrentView().contentPanel.getForm());
					},
					failure : function(response) {
						var resultArray = Ext.util.JSON.decode(response.status);
				 		if(resultArray == 403) {
			           		Ext.Msg.alert('提示', response.responseText);
				 		}else{
							Ext.Msg.alert('提示', '操作失败,失败原因:' + response.responseText);
		 				}
					}
				});
			}
	},{
		text : '取消',
		fn : function(formPanel,basicForm){
			showCustomerViewByTitle('CALLREPORT维护');
			/**
			 * 回填CALLREPORT维护界面数据
			 */
			setBaseValue(getCurrentView().contentPanel.getForm());
		}
	}]
},{//7
	title : '修改营收获利情况',
	hideTitle : true,
	type : 'form',
	autoLoadSeleted : true,
	frame : true,
	groups : [{
	columnCount : 2 ,
	fields : [	
	            {name : 'ID',hidden : true},
	            {name : 'TASK_NUMBER',hidden : true},
	            {name : 'P_YEARS',text  : '开始年份',hidden : false,dataType:'date',format:'Y-m'},
	            {name : 'P_YEARS_END',text  : '结束年份',hidden : false,dataType:'date',format:'Y-m'},
	            {name : 'P_REVENUE',text  : '营收(人民币/千元)',hidden : false,vtype:'money',maxLength:20},
	            {name : 'P_GROSS',text  : '毛利率(%)',vtype:'percent',maxLength:20},
	            {name : 'P_PNET',text  : '税后净利率(%)',vtype:'percent',maxLength:20}
				],
			fn : function(ID,TASK_NUMBER,P_YEARS,P_YEARS_END,P_REVENUE,P_GROSS,P_PNET){
				return [ID,TASK_NUMBER,P_YEARS,P_YEARS_END,P_REVENUE,P_GROSS,P_PNET];
			}
	},{
		   columnCount : 1,
		   fields:[{name:'P_MEMO',text:'备注',xtype:'textarea',maxLength:50}],
		   fn:function(P_MEMO){
			   return [P_MEMO];
		   }
	}],
	formButtons:[{
		text : '保存',
		//保存数据					 
		fn : function(formPanel,baseform){						
			if(!baseform.isValid())
				{
					Ext.Msg.alert('提示','请输入完整或正确的格式！');
					return false;
				}
			    var commintData = translateDataKey(baseform.getFieldValues(),_app.VIEWCOMMITTRANS);
			    if(commintData.pYears > commintData.pYearsEnd){
			    	Ext.Msg.alert('提示','开始年份不能大于结束年份！');
					return false;
			    }
			    if(commintData.pYearsEnd != undefined && commintData.pYears == undefined){
			    	Ext.Msg.alert('提示','开始年份不能为空！');
					return false;
			    }
			    if(commintData.pYearsEnd != undefined){
			    	if(commintData.pYearsEnd.format('Y-m') > new Date().format('Y-m')){   
			    		Ext.Msg.alert('提示','结束年份不能大于当前当前时间！');
						return false;
			    	}
			    }
			    if(commintData.pYears != undefined){
			    	if(commintData.pYears.format('Y-m') > new Date().format('Y-m')){   
			    		Ext.Msg.alert('提示','开始年份不能大于当前当前时间！');
						return false;
			    	}
			    }
				Ext.Msg.wait('正在提交数据，请稍等...','提示');
			    Ext.Ajax.request({
	 				url : basepath + '/ocrmFInterviewProfit.json?flag=1',
	 				method : 'POST',
	 				params : commintData,
					success : function() {
						Ext.Msg.hide(); 
//						Ext.Msg.alert('提示',"操作成功!");
						showCustomerViewByTitle('CALLREPORT维护');
						/**
						 * 回填CALLREPORT维护界面数据
						 */
						setBaseValue(getCurrentView().contentPanel.getForm());
					},
					failure : function(response) {
						var resultArray = Ext.util.JSON.decode(response.status);
				 		if(resultArray == 403) {
			           		Ext.Msg.alert('提示', response.responseText);
				 		}else{
							Ext.Msg.alert('提示', '操作失败,失败原因:' + response.responseText);
		 				}
					}
				});
			}
	},{
		text : '取消',
		fn : function(formPanel,basicForm){
			showCustomerViewByTitle('CALLREPORT维护');
			/**
			 * 回填CALLREPORT维护界面数据
			 */
			setBaseValue(getCurrentView().contentPanel.getForm());
		}
	}]
},{//8
	title : '新增原材料采购情况',
	hideTitle : true,
	type : 'form',
	autoLoadSeleted : true,
	frame : true,
	groups : [{
	columnCount : 2 ,
	fields : [	
	          {name : 'ID',hidden : true},
	            {name : 'TASK_NUMBER',hidden : true},
	            {name : 'MP_GOODS',text  : '供应商品',hidden : false,maxLength:50},
	            {name : 'MP_SUPPLIER',text  : '供应商名称',hidden : false,maxLength:100},
	            {name : 'MP_ISRELATE',text  : '是否关联企业',translateType:'IF_FLAG',editable:true},
	            {name : 'MP_MONTH2MONEY',text  : '月采购金额(人民币/千元)',vtype:'money',maxLength:20},
	            {name : 'MP_BALANCEDAYS',text  : '结算天数(天)',vtype:'dueTime',maxLength:3},
	            {name : 'MP_TRADEYEARS',text  : '往来年数(年)',vtype:'dueTime',maxLength:3},
	            {name : 'MP_PAYWAY',text  : '结算方式',translateType:'PS_PAYWAY',editable:true}
				],
			fn : function(ID,TASK_NUMBER,MP_GOODS,MP_SUPPLIER,MP_ISRELATE,MP_MONTH2MONEY,MP_BALANCEDAYS,MP_TRADEYEARS,MP_PAYWAY){
				return [ID,TASK_NUMBER,MP_GOODS,MP_SUPPLIER,MP_ISRELATE,MP_MONTH2MONEY,MP_BALANCEDAYS,MP_TRADEYEARS,MP_PAYWAY];
			}
	},{
		   columnCount : 1,
		   fields:[{name:'MP_MEMO',text:'备注',xtype:'textarea',maxLength:50}],
		   fn:function(MP_MEMO){
			   return [MP_MEMO];
		   }
	}],
	formButtons:[{
		text : '保存并继续增加',
		fn : function(formPanel,baseform){						
			if(!baseform.isValid())
				{
					Ext.Msg.alert('提示','请输入完整或正确的格式！');
					return false;
				}
			    var commintData = translateDataKey(baseform.getFieldValues(),_app.VIEWCOMMITTRANS);
				Ext.Msg.wait('正在提交数据，请稍等...','提示');
			    Ext.Ajax.request({
					url : basepath + '/ocrmFInterviewMatepurchase.json?flag=0',//0新增1修改
					method : 'POST',
					params : commintData,
					success : function() {
						Ext.Msg.hide(); 
//						Ext.Msg.alert('提示',"操作成功!");
//						showCustomerViewByTitle('CALLREPORT维护');
						/**
						 * 回填CALLREPORT维护界面数据
						 */
//						setBaseValue(getCurrentView().contentPanel.getForm());
						/**
						 * 清除页面数据
						 */
						cleanData(getCurrentView().contentPanel.getForm(),commintData);
					},
					failure : function(response) {
						var resultArray = Ext.util.JSON.decode(response.status);
				 		if(resultArray == 403) {
			           		Ext.Msg.alert('提示', response.responseText);
				 		}else{
							Ext.Msg.alert('提示', '操作失败,失败原因:' + response.responseText);
		 				}
					}
				});
			}
	},{
		text : '保存并退出',
		fn : function(formPanel,baseform){						
			if(!baseform.isValid())
				{
					Ext.Msg.alert('提示','请输入完整或正确的格式！');
					return false;
				}
			    var commintData = translateDataKey(baseform.getFieldValues(),_app.VIEWCOMMITTRANS);
				Ext.Msg.wait('正在提交数据，请稍等...','提示');
			    Ext.Ajax.request({
					url : basepath + '/ocrmFInterviewMatepurchase.json?flag=0',//0新增1修改
					method : 'POST',
					params : commintData,
					success : function() {
						Ext.Msg.hide(); 
//						Ext.Msg.alert('提示',"操作成功!");
						showCustomerViewByTitle('CALLREPORT维护');
						/**
						 * 回填CALLREPORT维护界面数据
						 */
						setBaseValue(getCurrentView().contentPanel.getForm());
					},
					failure : function(response) {
						var resultArray = Ext.util.JSON.decode(response.status);
				 		if(resultArray == 403) {
			           		Ext.Msg.alert('提示', response.responseText);
				 		}else{
							Ext.Msg.alert('提示', '操作失败,失败原因:' + response.responseText);
		 				}
					}
				});
			}
	},{
		text : '取消',
		fn : function(formPanel,basicForm){
			showCustomerViewByTitle('CALLREPORT维护');
			/**
			 * 回填CALLREPORT维护界面数据
			 */
			setBaseValue(getCurrentView().contentPanel.getForm());
		}
	}]
},{//9
	title : '修改原材料采购情况',
	hideTitle : true,
	type : 'form',
	autoLoadSeleted : true,
	frame : true,
	groups : [{
	columnCount : 2 ,
	fields : [	
	            {name : 'ID',hidden : true},
	            {name : 'TASK_NUMBER',hidden : true},
	            {name : 'MP_GOODS',text  : '供应商品',hidden : false,maxLength:50},
	            {name : 'MP_SUPPLIER',text  : '供应商名称',hidden : false,maxLength:100},
	            {name : 'MP_ISRELATE',text  : '是否关联企业',translateType:'IF_FLAG',editable:true},
	            {name : 'MP_MONTH2MONEY',text  : '月采购金额(人民币/千元)',vtype:'money',maxLength:20},
	            {name : 'MP_BALANCEDAYS',text  : '结算天数(天)',vtype:'dueTime',maxLength:3},
	            {name : 'MP_TRADEYEARS',text  : '往来年数(年)',vtype:'dueTime',maxLength:3},
	            {name : 'MP_PAYWAY',text  : '结算方式',translateType:'PS_PAYWAY',editable:true}
				],
			fn : function(ID,TASK_NUMBER,MP_GOODS,MP_SUPPLIER,MP_ISRELATE,MP_MONTH2MONEY,MP_BALANCEDAYS,MP_TRADEYEARS,MP_PAYWAY){
				return [ID,TASK_NUMBER,MP_GOODS,MP_SUPPLIER,MP_ISRELATE,MP_MONTH2MONEY,MP_BALANCEDAYS,MP_TRADEYEARS,MP_PAYWAY];
			}
	},{
		   columnCount : 1,
		   fields:[{name:'MP_MEMO',text:'备注',xtype:'textarea',maxLength:50}],
		   fn:function(MP_MEMO){
			   return [MP_MEMO];
		   }
	}],
	formButtons:[{
		text : '保存',
		//保存数据					 
		fn : function(formPanel,baseform){						
			if(!baseform.isValid())
				{
					Ext.Msg.alert('提示','请输入完整或正确的格式！');
					return false;
				}
			    var commintData = translateDataKey(baseform.getFieldValues(),_app.VIEWCOMMITTRANS);
				Ext.Msg.wait('正在提交数据，请稍等...','提示');
			    Ext.Ajax.request({
	 				url : basepath + '/ocrmFInterviewMatepurchase.json?flag=1',
	 				method : 'POST',
	 				params : commintData,
					success : function() {
						Ext.Msg.hide(); 
//						Ext.Msg.alert('提示',"操作成功!");
						showCustomerViewByTitle('CALLREPORT维护');
						/**
						 * 回填CALLREPORT维护界面数据
						 */
						setBaseValue(getCurrentView().contentPanel.getForm());
					},
					failure : function(response) {
						var resultArray = Ext.util.JSON.decode(response.status);
				 		if(resultArray == 403) {
			           		Ext.Msg.alert('提示', response.responseText);
				 		}else{
							Ext.Msg.alert('提示', '操作失败,失败原因:' + response.responseText);
		 				}
					}
				});
			}
	},{
		text : '取消',
		fn : function(formPanel,basicForm){
			showCustomerViewByTitle('CALLREPORT维护');
			/**
			 * 回填CALLREPORT维护界面数据
			 */
			setBaseValue(getCurrentView().contentPanel.getForm());
		}
	}]
},{//10
	title : '新增产品销售状况',
	hideTitle : true,
	type : 'form',
	autoLoadSeleted : true,
	frame : true,
	groups : [{
	columnCount : 2 ,
	fields : [	
	            {name : 'ID',hidden : true},
	            {name : 'TASK_NUMBER',hidden : true},
	            {name : 'PS_GOODS',text  : '销售产品',hidden : false,maxLength:50},
	            {name : 'PS_BUYER',text  : '购买方名称',hidden : false,maxLength:100},
	            {name : 'PS_ISRELATE',text  : '是否关联企业',translateType:'IF_FLAG',editable:true},
	            {name : 'PS_MONTH2MONEY',text  : '月销售金额(人民币/千元)',vtype:'money',maxLength:20},
	            {name : 'PS_BALANCEDAYS',text  : '结算天数(天)',vtype:'dueTime',maxLength:3},
	            {name : 'PS_TRADEYEARS',text  : '往来年数(年)',vtype:'dueTime',maxLength:3},
	            {name : 'PS_PAYWAY',text  : '结算方式',translateType:'PS_PAYWAY',editable:true}
				],
			fn : function(ID,TASK_NUMBER,PS_GOODS,PS_BUYER,PS_ISRELATE,PS_MONTH2MONEY,PS_BALANCEDAYS,PS_TRADEYEARS,PS_PAYWAY){
				return [ID,TASK_NUMBER,PS_GOODS,PS_BUYER,PS_ISRELATE,PS_MONTH2MONEY,PS_BALANCEDAYS,PS_TRADEYEARS,PS_PAYWAY];
			}
	},{
		   columnCount : 1,
		   fields:[{name:'PS_MEMO',text:'备注',xtype:'textarea',maxLength:50}],
		   fn:function(PS_MEMO){
			   return [PS_MEMO];
		   }
	}],
	formButtons:[{
		text : '保存并继续增加',
		fn : function(formPanel,baseform){						
			if(!baseform.isValid())
				{
					Ext.Msg.alert('提示','请输入完整或正确的格式！');
					return false;
				}
			    var commintData = translateDataKey(baseform.getFieldValues(),_app.VIEWCOMMITTRANS);
				Ext.Msg.wait('正在提交数据，请稍等...','提示');
			    Ext.Ajax.request({
					url : basepath + '/ocrmFInterviewProsale.json?flag=0',//0新增1修改
					method : 'POST',
					params : commintData,
					success : function() {
						Ext.Msg.hide(); 
//						Ext.Msg.alert('提示',"操作成功!");
//						showCustomerViewByTitle('CALLREPORT维护');
						/**
						 * 回填CALLREPORT维护界面数据
						 */
//						setBaseValue(getCurrentView().contentPanel.getForm());
						/**
						 * 清除页面数据
						 */
						cleanData(getCurrentView().contentPanel.getForm(),commintData);
					},
					failure : function(response) {
						var resultArray = Ext.util.JSON.decode(response.status);
				 		if(resultArray == 403) {
			           		Ext.Msg.alert('提示', response.responseText);
				 		}else{
							Ext.Msg.alert('提示', '操作失败,失败原因:' + response.responseText);
		 				}
					}
				});
			}
	},{
		text : '保存并退出',
		fn : function(formPanel,baseform){						
			if(!baseform.isValid())
				{
					Ext.Msg.alert('提示','请输入完整或正确的格式！');
					return false;
				}
			    var commintData = translateDataKey(baseform.getFieldValues(),_app.VIEWCOMMITTRANS);
				Ext.Msg.wait('正在提交数据，请稍等...','提示');
			    Ext.Ajax.request({
					url : basepath + '/ocrmFInterviewProsale.json?flag=0',//0新增1修改
					method : 'POST',
					params : commintData,
					success : function() {
						Ext.Msg.hide(); 
//						Ext.Msg.alert('提示',"操作成功!");
						showCustomerViewByTitle('CALLREPORT维护');
						/**
						 * 回填CALLREPORT维护界面数据
						 */
						setBaseValue(getCurrentView().contentPanel.getForm());
					},
					failure : function(response) {
						var resultArray = Ext.util.JSON.decode(response.status);
				 		if(resultArray == 403) {
			           		Ext.Msg.alert('提示', response.responseText);
				 		}else{
							Ext.Msg.alert('提示', '操作失败,失败原因:' + response.responseText);
		 				}
					}
				});
			}
	},{
		text : '取消',
		fn : function(formPanel,basicForm){
			showCustomerViewByTitle('CALLREPORT维护');
			/**
			 * 回填CALLREPORT维护界面数据
			 */
			setBaseValue(getCurrentView().contentPanel.getForm());
		}
	}]
},{//11
	title : '修改产品销售状况',
	hideTitle : true,
	type : 'form',
	autoLoadSeleted : true,
	frame : true,
	groups : [{
	columnCount : 2 ,
	fields : [	
	          {name : 'ID',hidden : true},
	            {name : 'TASK_NUMBER',hidden : true},
	            {name : 'PS_GOODS',text  : '销售产品',hidden : false,maxLength:50},
	            {name : 'PS_BUYER',text  : '购买方名称',hidden : false,maxLength:100},
	            {name : 'PS_ISRELATE',text  : '是否关联企业',translateType:'IF_FLAG',editable:true},
	            {name : 'PS_MONTH2MONEY',text  : '月销售金额(人民币/千元)',vtype:'money',maxLength:20},
	            {name : 'PS_BALANCEDAYS',text  : '结算天数(天)',vtype:'dueTime',maxLength:3},
	            {name : 'PS_TRADEYEARS',text  : '往来年数(年)',vtype:'dueTime',maxLength:3},
	            {name : 'PS_PAYWAY',text  : '结算方式',translateType:'PS_PAYWAY',editable:true}
				],
			fn : function(ID,TASK_NUMBER,PS_GOODS,PS_BUYER,PS_ISRELATE,PS_MONTH2MONEY,PS_BALANCEDAYS,PS_TRADEYEARS,PS_PAYWAY){
				return [ID,TASK_NUMBER,PS_GOODS,PS_BUYER,PS_ISRELATE,PS_MONTH2MONEY,PS_BALANCEDAYS,PS_TRADEYEARS,PS_PAYWAY];
			}
	},{
		   columnCount : 1,
		   fields:[{name:'PS_MEMO',text:'备注',xtype:'textarea',maxLength:50}],
		   fn:function(PS_MEMO){
			   return [PS_MEMO];
		   }
	}],
	formButtons:[{
		text : '保存',
		//保存数据					 
		fn : function(formPanel,baseform){						
			if(!baseform.isValid())
				{
					Ext.Msg.alert('提示','请输入完整或正确的格式！');
					return false;
				}
			    var commintData = translateDataKey(baseform.getFieldValues(),_app.VIEWCOMMITTRANS);
				Ext.Msg.wait('正在提交数据，请稍等...','提示');
			    Ext.Ajax.request({
	 				url : basepath + '/ocrmFInterviewProsale.json?flag=1',
	 				method : 'POST',
	 				params : commintData,
					success : function() {
						Ext.Msg.hide(); 
//						Ext.Msg.alert('提示',"操作成功!");
						showCustomerViewByTitle('CALLREPORT维护');
						/**
						 * 回填CALLREPORT维护界面数据
						 */
						setBaseValue(getCurrentView().contentPanel.getForm());
					},
					failure : function(response) {
						var resultArray = Ext.util.JSON.decode(response.status);
				 		if(resultArray == 403) {
			           		Ext.Msg.alert('提示', response.responseText);
				 		}else{
							Ext.Msg.alert('提示', '操作失败,失败原因:' + response.responseText);
		 				}
					}
				});
			}
	},{
		text : '取消',
		fn : function(formPanel,basicForm){
			showCustomerViewByTitle('CALLREPORT维护');
			/**
			 * 回填CALLREPORT维护界面数据
			 */
			setBaseValue(getCurrentView().contentPanel.getForm());
		}
	}]
},{//12
	title : '新增存款往来银行表',
	hideTitle : true,
	type : 'form',
	autoLoadSeleted : true,
	frame : true,
	groups : [{
	columnCount : 2 ,
	fields : [	
	            {name : 'ID',hidden : true},
	            {name : 'TASK_NUMBER',hidden : true},
	            {name : 'D_BANKNAME',text  : '往来银行',hidden : false,maxLength:100},
	            {name : 'D_AVGDEPOSIT',text  : '平均存款量（人民币/千元）',hidden : false,vtype:'money',maxLength:20},
	            {name : 'D_DEPOSIT_TYPE',text  : '存款类型',hidden : false,maxLength:50},
	            {name : 'D_TERM',text  : '期限',hidden : false,maxLength:50}
				],
			fn : function(ID,TASK_NUMBER,D_BANKNAME,D_AVGDEPOSIT,D_DEPOSIT_TYPE,D_TERM){
				return [ID,TASK_NUMBER,D_BANKNAME,D_AVGDEPOSIT,D_DEPOSIT_TYPE,D_TERM];
			}
	}],
	formButtons:[{
		text : '保存并继续增加',
		fn : function(formPanel,baseform){						
			if(!baseform.isValid())
				{
					Ext.Msg.alert('提示','请输入完整或正确的格式！');
					return false;
				}
			    var commintData = translateDataKey(baseform.getFieldValues(),_app.VIEWCOMMITTRANS);
				Ext.Msg.wait('正在提交数据，请稍等...','提示');
			    Ext.Ajax.request({
					url : basepath + '/ocrmFInterviewDepositbank.json?flag=0',//0新增1修改
					method : 'POST',
					params : commintData,
					success : function() {
						Ext.Msg.hide(); 
//						Ext.Msg.alert('提示',"操作成功!");
//						showCustomerViewByTitle('CALLREPORT维护');
						/**
						 * 回填CALLREPORT维护界面数据
						 */
//						setBaseValue(getCurrentView().contentPanel.getForm());
						/**
						 * 清除页面数据
						 */
						cleanData(getCurrentView().contentPanel.getForm(),commintData);
					},
					failure : function(response) {
						var resultArray = Ext.util.JSON.decode(response.status);
				 		if(resultArray == 403) {
			           		Ext.Msg.alert('提示', response.responseText);
				 		}else{
							Ext.Msg.alert('提示', '操作失败,失败原因:' + response.responseText);
		 				}
					}
				});
			}
	},{
		text : '保存并退出',
		fn : function(formPanel,baseform){						
			if(!baseform.isValid())
				{
					Ext.Msg.alert('提示','请输入完整或正确的格式！');
					return false;
				}
			    var commintData = translateDataKey(baseform.getFieldValues(),_app.VIEWCOMMITTRANS);
				Ext.Msg.wait('正在提交数据，请稍等...','提示');
			    Ext.Ajax.request({
					url : basepath + '/ocrmFInterviewDepositbank.json?flag=0',//0新增1修改
					method : 'POST',
					params : commintData,
					success : function() {
						Ext.Msg.hide(); 
//						Ext.Msg.alert('提示',"操作成功!");
						showCustomerViewByTitle('CALLREPORT维护');
						/**
						 * 回填CALLREPORT维护界面数据
						 */
						setBaseValue(getCurrentView().contentPanel.getForm());
					},
					failure : function(response) {
						var resultArray = Ext.util.JSON.decode(response.status);
				 		if(resultArray == 403) {
			           		Ext.Msg.alert('提示', response.responseText);
				 		}else{
							Ext.Msg.alert('提示', '操作失败,失败原因:' + response.responseText);
		 				}
					}
				});
			}
	},{
		text : '取消',
		fn : function(formPanel,basicForm){
			showCustomerViewByTitle('CALLREPORT维护');
			/**
			 * 回填CALLREPORT维护界面数据
			 */
			setBaseValue(getCurrentView().contentPanel.getForm());
		}
	}]
},{//13
	title : '修改存款往来银行表',
	hideTitle : true,
	type : 'form',
	autoLoadSeleted : true,
	frame : true,
	groups : [{
	columnCount : 2 ,
	fields : [	
					{name : 'ID',hidden : true},
					{name : 'TASK_NUMBER',hidden : true},
					{name : 'D_BANKNAME',text  : '往来银行',hidden : false,maxLength:100},
					{name : 'D_AVGDEPOSIT',text  : '平均存款量（人民币/千元）',hidden : false,vtype:'money',maxLength:20},
     	            {name : 'D_DEPOSIT_TYPE',text  : '存款类型',hidden : false,maxLength:50},
     	            {name : 'D_TERM',text  : '期限',hidden : false,maxLength:50}
					],
				fn : function(ID,TASK_NUMBER,D_BANKNAME,D_AVGDEPOSIT,D_DEPOSIT_TYPE,D_TERM){
					return [ID,TASK_NUMBER,D_BANKNAME,D_AVGDEPOSIT,D_DEPOSIT_TYPE,D_TERM];
				}
	}],
	formButtons:[{
		text : '保存',
		//保存数据					 
		fn : function(formPanel,baseform){						
			if(!baseform.isValid())
				{
					Ext.Msg.alert('提示','请输入完整或正确的格式！');
					return false;
				}
			    var commintData = translateDataKey(baseform.getFieldValues(),_app.VIEWCOMMITTRANS);
				Ext.Msg.wait('正在提交数据，请稍等...','提示');
			    Ext.Ajax.request({
	 				url : basepath + '/ocrmFInterviewDepositbank.json?flag=1',
	 				method : 'POST',
	 				params : commintData,
					success : function() {
						Ext.Msg.hide(); 
//						Ext.Msg.alert('提示',"操作成功!");
						showCustomerViewByTitle('CALLREPORT维护');
						/**
						 * 回填CALLREPORT维护界面数据
						 */
						setBaseValue(getCurrentView().contentPanel.getForm());
					},
					failure : function(response) {
						var resultArray = Ext.util.JSON.decode(response.status);
				 		if(resultArray == 403) {
			           		Ext.Msg.alert('提示', response.responseText);
				 		}else{
							Ext.Msg.alert('提示', '操作失败,失败原因:' + response.responseText);
		 				}
					}
				});
			}
	},{
		text : '取消',
		fn : function(formPanel,basicForm){
			showCustomerViewByTitle('CALLREPORT维护');
			/**
			 * 回填CALLREPORT维护界面数据
			 */
			setBaseValue(getCurrentView().contentPanel.getForm());
		}
	}]
},{//14
	title : '新增贷款往来银行表',
	hideTitle : true,
	type : 'form',
	autoLoadSeleted : true,
	frame : true,
	groups : [{
	columnCount : 2 ,
	fields : [	
	            {name : 'ID',hidden : true},
	            {name : 'TASK_NUMBER',hidden : true},
	            {name : 'L_BANKNAME',text  : '往来银行',hidden : false,maxLength:100},
	            {name : 'L_LIMITTYPE',text  : '额度类型',hidden : false,maxLength:100},
	            {name : 'L_LIMITMONEY',text  : '额度金额(人民币/千元)',hidden : false,vtype:'money',maxLength:20},
	            {name : 'L_BALANCE',text  : '动拨余额(人民币/千元)',hidden : false,vtype:'money',maxLength:20},
	            {name : 'L_RATE',text  : '利率(%)',hidden : false,vtype:'percent',maxLength:20},
//	            {name : 'L_DBRATE',text  : '担保率(%)',hidden : true,vtype:'dbPercent',maxLength:20},
	            {name : 'L_COLLATERAL',text  : '担保品',hidden : false,maxLength:50}
				],
			fn : function(ID,TASK_NUMBER,L_BANKNAME,L_LIMITTYPE,L_LIMITMONEY,L_BALANCE,L_RATE,L_COLLATERAL){
				return [ID,TASK_NUMBER,L_BANKNAME,L_LIMITTYPE,L_LIMITMONEY,L_BALANCE,L_RATE,L_COLLATERAL];
			}
	},{
		   columnCount : 1,
		   fields:[{name:'L_MEMO',text:'备注',xtype:'textarea',maxLength:50}],
		   fn:function(L_MEMO){
			   return [L_MEMO];
		   }
	}],
	formButtons:[{
		text : '保存并继续增加',
		fn : function(formPanel,baseform){						
			if(!baseform.isValid())
				{
					Ext.Msg.alert('提示','请输入完整或正确的格式！');
					return false;
				}
			    var commintData = translateDataKey(baseform.getFieldValues(),_app.VIEWCOMMITTRANS);
				Ext.Msg.wait('正在提交数据，请稍等...','提示');
			    Ext.Ajax.request({
					url : basepath + '/ocrmFInterviewLoanbank.json?flag=0',//0新增1修改
					method : 'POST',
					params : commintData,
					success : function() {
						Ext.Msg.hide(); 
//						Ext.Msg.alert('提示',"操作成功!");
//						showCustomerViewByTitle('CALLREPORT维护');
						/**
						 * 回填CALLREPORT维护界面数据
						 */
//						setBaseValue(getCurrentView().contentPanel.getForm());
						/**
						 * 清除页面数据
						 */
						cleanData(getCurrentView().contentPanel.getForm(),commintData);
					},
					failure : function(response) {
						var resultArray = Ext.util.JSON.decode(response.status);
				 		if(resultArray == 403) {
			           		Ext.Msg.alert('提示', response.responseText);
				 		}else{
							Ext.Msg.alert('提示', '操作失败,失败原因:' + response.responseText);
		 				}
					}
				});
			}
	},{
		text : '保存并退出',
		fn : function(formPanel,baseform){						
			if(!baseform.isValid())
				{
					Ext.Msg.alert('提示','请输入完整或正确的格式！');
					return false;
				}
			    var commintData = translateDataKey(baseform.getFieldValues(),_app.VIEWCOMMITTRANS);
				Ext.Msg.wait('正在提交数据，请稍等...','提示');
			    Ext.Ajax.request({
					url : basepath + '/ocrmFInterviewLoanbank.json?flag=0',//0新增1修改
					method : 'POST',
					params : commintData,
					success : function() {
						Ext.Msg.hide(); 
//						Ext.Msg.alert('提示',"操作成功!");
						showCustomerViewByTitle('CALLREPORT维护');
						/**
						 * 回填CALLREPORT维护界面数据
						 */
						setBaseValue(getCurrentView().contentPanel.getForm());
					},
					failure : function(response) {
						var resultArray = Ext.util.JSON.decode(response.status);
				 		if(resultArray == 403) {
			           		Ext.Msg.alert('提示', response.responseText);
				 		}else{
							Ext.Msg.alert('提示', '操作失败,失败原因:' + response.responseText);
		 				}
					}
				});
			}
	},{
		text : '取消',
		fn : function(formPanel,basicForm){
			showCustomerViewByTitle('CALLREPORT维护');
			/**
			 * 回填CALLREPORT维护界面数据
			 */
			setBaseValue(getCurrentView().contentPanel.getForm());
		}
	}]
},{//15
	title : '修改贷款往来银行表',
	hideTitle : true,
	type : 'form',
	autoLoadSeleted : true,
	frame : true,
	groups : [{
	columnCount : 2 ,
	fields : [	
				{name : 'ID',hidden : true},
				{name : 'TASK_NUMBER',hidden : true},
				{name : 'L_BANKNAME',text  : '往来银行',hidden : false,maxLength:100},
				{name : 'L_LIMITTYPE',text  : '额度类型',hidden : false,maxLength:100},
				{name : 'L_LIMITMONEY',text  : '额度金额(人民币/千元)',hidden : false,vtype:'money',maxLength:20},
				{name : 'L_BALANCE',text  : '动拨余额(人民币/千元)',hidden : false,vtype:'money',maxLength:20},
				{name : 'L_RATE',text  : '利率(%)',hidden : false,vtype:'percent',maxLength:20},
//				{name : 'L_DBRATE',text  : '担保率(%)',hidden : true,vtype:'dbPercent',maxLength:20},
				{name : 'L_COLLATERAL',text  : '担保品',hidden : false,maxLength:50}
			],
			fn : function(ID,TASK_NUMBER,L_BANKNAME,L_LIMITTYPE,L_LIMITMONEY,L_BALANCE,L_RATE,L_COLLATERAL){
				return [ID,TASK_NUMBER,L_BANKNAME,L_LIMITTYPE,L_LIMITMONEY,L_BALANCE,L_RATE,L_COLLATERAL];
			}
	},{
		   columnCount : 1,
		   fields:[{name:'L_MEMO',text:'备注',xtype:'textarea',maxLength:50}],
		   fn:function(L_MEMO){
			   return [L_MEMO];
		   }
	}],
	formButtons:[{
		text : '保存',
		//保存数据					 
		fn : function(formPanel,baseform){						
			if(!baseform.isValid())
				{
					Ext.Msg.alert('提示','请输入完整或正确的格式！');
					return false;
				}
			    var commintData = translateDataKey(baseform.getFieldValues(),_app.VIEWCOMMITTRANS);
				Ext.Msg.wait('正在提交数据，请稍等...','提示');
			    Ext.Ajax.request({
	 				url : basepath + '/ocrmFInterviewLoanbank.json?flag=1',
	 				method : 'POST',
	 				params : commintData,
					success : function() {
						Ext.Msg.hide(); 
//						Ext.Msg.alert('提示',"操作成功!");
						showCustomerViewByTitle('CALLREPORT维护');
						/**
						 * 回填CALLREPORT维护界面数据
						 */
						setBaseValue(getCurrentView().contentPanel.getForm());
					},
					failure : function(response) {
						var resultArray = Ext.util.JSON.decode(response.status);
				 		if(resultArray == 403) {
			           		Ext.Msg.alert('提示', response.responseText);
				 		}else{
							Ext.Msg.alert('提示', '操作失败,失败原因:' + response.responseText);
		 				}
					}
				});
			}
	},{
		text : '取消',
		fn : function(formPanel,basicForm){
			showCustomerViewByTitle('CALLREPORT维护');
			/**
			 * 回填CALLREPORT维护界面数据
			 */
			setBaseValue(getCurrentView().contentPanel.getForm());
		}
	}]
},{//16
	title : '新增拟承做存款产品',
	hideTitle : true,
	type : 'form',
//	suspendWidth: aw,
	autoLoadSeleted : true,
	frame : true,
	groups : [{
	columnCount : 2 ,
	fields : [	
	            {name : 'ID',hidden : true},
	            {name : 'TASK_NUMBER',hidden : true},
	            {name : 'DP_NAME',text:'存款产品',hidden:false,maxLength:50},
	            {name : 'DP_AVGDEPOSIT',text :'预计平均存款量(人民币/千元)',hidden:false,dataType:'decimal',viewFn:money('0,000.00'),minValue:0,maxLength:24}
				],
			fn : function(ID,TASK_NUMBER,DP_NAME,DP_AVGDEPOSIT){
				return [ID,TASK_NUMBER,DP_NAME,DP_AVGDEPOSIT];
			}
	}],
	formButtons:[{
		text : '保存并继续增加',
		fn : function(formPanel,baseform){						
			if(!baseform.isValid())
				{
					Ext.Msg.alert('提示','请输入完整或正确的格式！');
					return false;
				}
			    var commintData = translateDataKey(baseform.getFieldValues(),_app.VIEWCOMMITTRANS);
				Ext.Msg.wait('正在提交数据，请稍等...','提示');
			    Ext.Ajax.request({
					url : basepath + '/ocrmFInterviewDepositpro.json?flag=0',//0新增1修改
					method : 'POST',
					params : commintData,
					success : function() {
						Ext.Msg.hide(); 
//						Ext.Msg.alert('提示',"操作成功!");
//						showCustomerViewByTitle('CALLREPORT维护');
						/**
						 * 回填CALLREPORT维护界面数据
						 */
//						setBaseValue(getCurrentView().contentPanel.getForm());
						/**
						 * 清除页面数据
						 */
						cleanData(getCurrentView().contentPanel.getForm(),commintData);
					},
					failure : function(response) {
						var resultArray = Ext.util.JSON.decode(response.status);
				 		if(resultArray == 403) {
			           		Ext.Msg.alert('提示', response.responseText);
				 		}else{
							Ext.Msg.alert('提示', '操作失败,失败原因:' + response.responseText);
		 				}
					}
				});
			}
	},{
		text : '保存并退出',
		fn : function(formPanel,baseform){						
			if(!baseform.isValid())
				{
					Ext.Msg.alert('提示','请输入完整或正确的格式！');
					return false;
				}
			    var commintData = translateDataKey(baseform.getFieldValues(),_app.VIEWCOMMITTRANS);
				Ext.Msg.wait('正在提交数据，请稍等...','提示');
			    Ext.Ajax.request({
					url : basepath + '/ocrmFInterviewDepositpro.json?flag=0',//0新增1修改
					method : 'POST',
					params : commintData,
					success : function() {
						Ext.Msg.hide(); 
//						Ext.Msg.alert('提示',"操作成功!");
						showCustomerViewByIndex(3);
						/**
						 * 回填CALLREPORT维护界面数据
						 */
						setBaseValue(getCurrentView().contentPanel.getForm());
					},
					failure : function(response) {
						var resultArray = Ext.util.JSON.decode(response.status);
				 		if(resultArray == 403) {
			           		Ext.Msg.alert('提示', response.responseText);
				 		}else{
							Ext.Msg.alert('提示', '操作失败,失败原因:' + response.responseText);
		 				}
					}
				});
			}
	},{
		text : '取消',
		fn : function(formPanel,basicForm){
			showCustomerViewByIndex(3);
			/**
			 * 回填CALLREPORT维护界面数据
			 */
			setBaseValue(getCurrentView().contentPanel.getForm());
		}
	}]
},{//17
	title : '修改拟承做存款产品',
	hideTitle : true,
	type : 'form',
//	suspendWidth: aw,
	autoLoadSeleted : true,
	frame : true,
	groups : [{
	columnCount : 2 ,
	fields : [	
	          {name : 'ID',hidden : true},
	            {name : 'TASK_NUMBER',hidden : true},
	            {name : 'DP_NAME',text  : '存款产品',hidden : false,maxLength:50},
	            {name : 'DP_AVGDEPOSIT',text  : '预计平均存款量(人民币/千元)',hidden : false,dataType: 'decimal', viewFn: money('0,000.00'),minValue: 0,maxLength:24}
				],
			fn : function(ID,TASK_NUMBER,DP_NAME,DP_AVGDEPOSIT){
				return [ID,TASK_NUMBER,DP_NAME,DP_AVGDEPOSIT];
			}
	}],
	formButtons:[{
		text : '保存',
		//保存数据					 
		fn : function(formPanel,baseform){						
			if(!baseform.isValid())
				{
					Ext.Msg.alert('提示','请输入完整或正确的格式！');
					return false;
				}
			    var commintData = translateDataKey(baseform.getFieldValues(),_app.VIEWCOMMITTRANS);
				Ext.Msg.wait('正在提交数据，请稍等...','提示');
			    Ext.Ajax.request({
	 				url : basepath + '/ocrmFInterviewDepositpro.json?flag=1',
	 				method : 'POST',
	 				params : commintData,
					success : function() {
						Ext.Msg.hide(); 
//						Ext.Msg.alert('提示',"操作成功!");
						showCustomerViewByIndex(3);
						/**
						 * 回填CALLREPORT维护界面数据
						 */
						setBaseValue(getCurrentView().contentPanel.getForm());
					},
					failure : function(response) {
						var resultArray = Ext.util.JSON.decode(response.status);
				 		if(resultArray == 403) {
			           		Ext.Msg.alert('提示', response.responseText);
				 		}else{
							Ext.Msg.alert('提示', '操作失败,失败原因:' + response.responseText);
		 				}
					}
				});
			}
	},{
		text : '取消',
		fn : function(formPanel,basicForm){
			showCustomerViewByIndex(3);
			/**
			 * 回填CALLREPORT维护界面数据
			 */
			setBaseValue(getCurrentView().contentPanel.getForm());
		}
	}]
},{//18
	title : '新增拟申请外汇产品额度',
	hideTitle : true,
	type : 'form',
//	suspendWidth: aw,
	autoLoadSeleted : true,
	frame : true,
	groups : [{
	columnCount : 2 ,
	fields : [	
	            {name : 'ID',hidden : true},
	            {name : 'TASK_NUMBER',hidden : true},
	            {name : 'FL_NAME',text  : '外汇产品',hidden : false,maxLength:50},
	            {name : 'FL_DEAL2MONTH',text  : '月交易量（等值美金/千元）',hidden : false,dataType: 'decimal', viewFn: money('0,000.00'),minValue: 0,maxLength:24},
	            {name : 'FL_LIMITMONEY',text  : '额度金额（等值美金/千元）',hidden : false,dataType: 'decimal', viewFn: money('0,000.00'),minValue: 0,maxLength:24}
				],
			fn : function(ID,TASK_NUMBER,FL_NAME,FL_DEAL2MONTH,FL_LIMITMONEY){
				return [ID,TASK_NUMBER,FL_NAME,FL_DEAL2MONTH,FL_LIMITMONEY];
			}
	}],
	formButtons:[{
		text : '保存并继续增加',
		fn : function(formPanel,baseform){						
			if(!baseform.isValid())
				{
					Ext.Msg.alert('提示','请输入完整或正确的格式！');
					return false;
				}
			    var commintData = translateDataKey(baseform.getFieldValues(),_app.VIEWCOMMITTRANS);
				Ext.Msg.wait('正在提交数据，请稍等...','提示');
			    Ext.Ajax.request({
					url : basepath + '/ocrmFInterviewForexlimit.json?flag=0',//0新增1修改
					method : 'POST',
					params : commintData,
					success : function() {
						Ext.Msg.hide(); 
//						Ext.Msg.alert('提示',"操作成功!");
//						showCustomerViewByTitle('CALLREPORT维护');
						/**
						 * 回填CALLREPORT维护界面数据
						 */
//						setBaseValue(getCurrentView().contentPanel.getForm());
						/**
						 * 清除页面数据
						 */
						cleanData(getCurrentView().contentPanel.getForm(),commintData);
					},
					failure : function(response) {
						var resultArray = Ext.util.JSON.decode(response.status);
				 		if(resultArray == 403) {
			           		Ext.Msg.alert('提示', response.responseText);
				 		}else{
							Ext.Msg.alert('提示', '操作失败,失败原因:' + response.responseText);
		 				}
					}
				});
			}
	},{
		text : '保存并退出',
		fn : function(formPanel,baseform){						
			if(!baseform.isValid())
				{
					Ext.Msg.alert('提示','请输入完整或正确的格式！');
					return false;
				}
			    var commintData = translateDataKey(baseform.getFieldValues(),_app.VIEWCOMMITTRANS);
				Ext.Msg.wait('正在提交数据，请稍等...','提示');
			    Ext.Ajax.request({
					url : basepath + '/ocrmFInterviewForexlimit.json?flag=0',//0新增1修改
					method : 'POST',
					params : commintData,
					success : function() {
						Ext.Msg.hide(); 
//						Ext.Msg.alert('提示',"操作成功!");
						showCustomerViewByIndex(3);
						/**
						 * 回填CALLREPORT维护界面数据
						 */
						setBaseValue(getCurrentView().contentPanel.getForm());
					},
					failure : function(response) {
						var resultArray = Ext.util.JSON.decode(response.status);
				 		if(resultArray == 403) {
			           		Ext.Msg.alert('提示', response.responseText);
				 		}else{
							Ext.Msg.alert('提示', '操作失败,失败原因:' + response.responseText);
		 				}
					}
				});
			}
	},{
		text : '取消',
		fn : function(formPanel,basicForm){
			showCustomerViewByIndex(3);
			/**
			 * 回填CALLREPORT维护界面数据
			 */
			setBaseValue(getCurrentView().contentPanel.getForm());
		}
	}]
},{//19
	title : '修改拟申请外汇产品额度',
	hideTitle : true,
	type : 'form',
//	suspendWidth: aw,
	autoLoadSeleted : true,
	frame : true,
	groups : [{
	columnCount : 2 ,
	fields : [	
	          {name : 'ID',hidden : true},
	            {name : 'TASK_NUMBER',hidden : true},
	            {name : 'FL_NAME',text  : '外汇产品',hidden : false,maxLength:50},
	            {name : 'FL_DEAL2MONTH',text  : '月交易量（等值美金/千元）',hidden : false,dataType: 'decimal', viewFn: money('0,000.00'),minValue: 0,maxLength:24},
	            {name : 'FL_LIMITMONEY',text  : '额度金额（等值美金/千元）',hidden : false,dataType: 'decimal', viewFn: money('0,000.00'),minValue: 0,maxLength:24}
				],
			fn : function(ID,TASK_NUMBER,FL_NAME,FL_DEAL2MONTH,FL_LIMITMONEY){
				return [ID,TASK_NUMBER,FL_NAME,FL_DEAL2MONTH,FL_LIMITMONEY];
			}
	}],
	formButtons:[{
		text : '保存',
		//保存数据					 
		fn : function(formPanel,baseform){						
			if(!baseform.isValid())
				{
					Ext.Msg.alert('提示','请输入完整或正确的格式！');
					return false;
				}
			    var commintData = translateDataKey(baseform.getFieldValues(),_app.VIEWCOMMITTRANS);
				Ext.Msg.wait('正在提交数据，请稍等...','提示');
			    Ext.Ajax.request({
	 				url : basepath + '/ocrmFInterviewForexlimit.json?flag=1',
	 				method : 'POST',
	 				params : commintData,
					success : function() {
						Ext.Msg.hide(); 
//						Ext.Msg.alert('提示',"操作成功!");
						showCustomerViewByIndex(3);
						/**
						 * 回填CALLREPORT维护界面数据
						 */
						setBaseValue(getCurrentView().contentPanel.getForm());
					},
					failure : function(response) {
						var resultArray = Ext.util.JSON.decode(response.status);
				 		if(resultArray == 403) {
			           		Ext.Msg.alert('提示', response.responseText);
				 		}else{
							Ext.Msg.alert('提示', '操作失败,失败原因:' + response.responseText);
		 				}
					}
				});
			}
	},{
		text : '取消',
		fn : function(formPanel,basicForm){
			showCustomerViewByIndex(3);
			/**
			 * 回填CALLREPORT维护界面数据
			 */
			setBaseValue(getCurrentView().contentPanel.getForm());
		}
	}]
},
//{
//	title : '新增拟申请授信产品',  //旧户 
//	hideTitle : true,
//	type : 'form',
//	autoLoadSeleted : true,
//	frame : true,
//	groups : [{
//	columnCount : 2 ,
//	fields : [	
//	            {name : 'ID',hidden : true},
//	            {name : 'TASK_NUMBER',hidden : true},
//	            {name : 'CP_PRODUCT_P',text  : '产品类型',editable:true,hidden : false,translateType:'CP_PRODUCT_P',
//	            	listeners:{
//	  		    		select:function(combo,record){
//	  		    			var v = this.getValue();
//	  		    			if(v=='1'){
//	  		    				getCurrentView().contentPanel.getForm().findField('CP_PRODUCT').setValue(null);
//	  		    				getCurrentView().contentPanel.getForm().findField('CP_PRODUCT').bindStore(findLookupByType('CP_PRODUCT_1'));
//	  		    			}else if(v == '2'){
//	  		    				getCurrentView().contentPanel.getForm().findField('CP_PRODUCT').setValue(null);
//	  		    				getCurrentView().contentPanel.getForm().findField('CP_PRODUCT').bindStore(findLookupByType('CP_PRODUCT_2'));
//	  		    			}else if(v == '3'){
//	  		    				getCurrentView().contentPanel.getForm().findField('CP_PRODUCT').setValue(null);
//	  		    				getCurrentView().contentPanel.getForm().findField('CP_PRODUCT').bindStore(findLookupByType('CP_PRODUCT_3'));
//	  		    			}else if(v == '4'){
//	  		    				getCurrentView().contentPanel.getForm().findField('CP_PRODUCT').setValue(null);
//	  		    				getCurrentView().contentPanel.getForm().findField('CP_PRODUCT').bindStore(findLookupByType('CP_PRODUCT_4'));
//	  		    			}else if(v == '5'){
//	  		    				getCurrentView().contentPanel.getForm().findField('CP_PRODUCT').setValue(null);
//	  		    				getCurrentView().contentPanel.getForm().findField('CP_PRODUCT').bindStore(findLookupByType('CP_PRODUCT_5'));
//	  		    			}else if(v == '6'){
//	  		    				getCurrentView().contentPanel.getForm().findField('CP_PRODUCT').setValue(null);
//	  		    				getCurrentView().contentPanel.getForm().findField('CP_PRODUCT').bindStore(findLookupByType('CP_PRODUCT_6'));
//	  		    			}else{
//	  		    				getCurrentView().contentPanel.getForm().findField('CP_PRODUCT').setValue(null);
//	  		    				getCurrentView().contentPanel.getForm().findField('CP_PRODUCT').bindStore(findLookupByType('CP_PRODUCT_7'));
//	  		    			}
//	  		    			getCurrentView().contentPanel.getForm().findField('CP_PRODUCT').show();
//	  					}
//		    	}},
//	            {name : 'CP_PRODUCT',text  : '授信产品',hidden : false,translateType:'CP_PRODUCT',editable:true},
//	            {name : 'CP_USE',text  : '用途',hidden : false,translateType:'CP_USE',editable:true},
//	            {name : 'CP_CURRENCY',text  : '币种',hidden : false,translateType:'CURRENCY',editable:true},
//	            {name : 'CP_LIMITMONEY',text  : '额度金额(人民币/千元)',hidden : false,vtype:'money',maxLength:20},
//	            {name : 'CP_COLLATERAL',text  : '担保品',hidden : false,maxLength:50},
//				{name : 'CP_DBRATE',text  : '担保比率(%)',hidden : false,vtype:'dbPercent',maxLength:20}
//				],
//			fn : function(ID,TASK_NUMBER,CP_USE,CP_PRODUCT_P,CP_PRODUCT,CP_CURRENCY,CP_LIMITMONEY,CP_COLLATERAL,CP_DBRATE){
//				return [ID,TASK_NUMBER,CP_USE,CP_PRODUCT_P,CP_PRODUCT,CP_CURRENCY,CP_LIMITMONEY,CP_COLLATERAL,CP_DBRATE];
//			}
//	},{
//		   columnCount : 1,
//		   fields:[{name:'CP_MEMO',text:'备注',xtype:'textarea',maxLength:50}],
//		   fn:function(CP_MEMO){
//			   return [CP_MEMO];
//		   }
//	}],
//	formButtons:[{
//		text : '保存并继续增加',
//		fn : function(formPanel,baseform){						
//			if(!baseform.isValid())
//				{
//					Ext.Msg.alert('提示','请输入完整或正确的格式！');
//					return false;
//				}
//			    var commintData = translateDataKey(baseform.getFieldValues(),_app.VIEWCOMMITTRANS);
//			    if(commintData.cpProductP != undefined && commintData.cpProductP != ''){
//			    	if(commintData.cpProduct == null || commintData.cpProduct == ''){
//			    		Ext.Msg.alert('提示',"请选择[授信产品]!");
//			    		return false;
//			    	}
//			    }
//			    
//				Ext.Msg.wait('正在提交数据，请稍等...','提示');
//			    Ext.Ajax.request({
//					url : basepath + '/ocrmFInterviewCreditpro.json?flag=0',//0新增1修改
//					method : 'POST',
//					params : commintData,
//					success : function() {
//						Ext.Msg.hide(); 
////						Ext.Msg.alert('提示',"操作成功!");
////						showCustomerViewByTitle('CALLREPORT维护');
//						/**
//						 * 回填CALLREPORT维护界面数据
//						 */
////						setBaseValue(getCurrentView().contentPanel.getForm());
//						/**
//						 * 清除页面数据
//						 */
//						cleanData(getCurrentView().contentPanel.getForm(),commintData);
//						getCurrentView().contentPanel.getForm().findField('CP_PRODUCT').hide();
//					},
//					failure : function(response) {
//						var resultArray = Ext.util.JSON.decode(response.status);
//				 		if(resultArray == 403) {
//			           		Ext.Msg.alert('提示', response.responseText);
//				 		}else{
//							Ext.Msg.alert('提示', '操作失败,失败原因:' + response.responseText);
//		 				}
//					}
//				});
//			}
//	},{
//		text : '保存并退出',
//		fn : function(formPanel,baseform){						
//			if(!baseform.isValid())
//				{
//					Ext.Msg.alert('提示','请输入完整或正确的格式！');
//					return false;
//				}
//			    var commintData = translateDataKey(baseform.getFieldValues(),_app.VIEWCOMMITTRANS);
//			    if(commintData.cpProductP != undefined && commintData.cpProductP != ''){
//			    	if(commintData.cpProduct == null || commintData.cpProduct == ''){
//			    		Ext.Msg.alert('提示',"请选择[授信产品]!");
//			    		return false;
//			    	}
//			    }
//			    
//				Ext.Msg.wait('正在提交数据，请稍等...','提示');
//			    Ext.Ajax.request({
//					url : basepath + '/ocrmFInterviewCreditpro.json?flag=0',//0新增1修改
//					method : 'POST',
//					params : commintData,
//					success : function() {
//						Ext.Msg.hide(); 
////						Ext.Msg.alert('提示',"操作成功!");
//						showCustomerViewByTitle('CALLREPORT维护');
//						/**
//						 * 回填CALLREPORT维护界面数据
//						 */
//						setBaseValue(getCurrentView().contentPanel.getForm());
//					},
//					failure : function(response) {
//						var resultArray = Ext.util.JSON.decode(response.status);
//				 		if(resultArray == 403) {
//			           		Ext.Msg.alert('提示', response.responseText);
//				 		}else{
//							Ext.Msg.alert('提示', '操作失败,失败原因:' + response.responseText);
//		 				}
//					}
//				});
//			}
//	},{
//		text : '取消',
//		fn : function(formPanel,basicForm){
//			showCustomerViewByTitle('CALLREPORT维护');
//			/**
//			 * 回填CALLREPORT维护界面数据
//			 */
//			setBaseValue(getCurrentView().contentPanel.getForm());
//		}
//	}]
//},{
//	title : '修改拟申请授信产品',  //旧户
//	hideTitle : true,
//	type : 'form',
//	autoLoadSeleted : true,
//	frame : true,
//	groups : [{
//	columnCount : 2 ,
//	fields : [	
//	          {name : 'ID',hidden : true},
//	            {name : 'TASK_NUMBER',hidden : true},
//	            {name : 'CP_PRODUCT_P',text  : '产品类型',editable:true,hidden : false,translateType:'CP_PRODUCT_P',
//	            	listeners:{
//	  		    		select:function(combo,record){
//	  		    			var v = this.getValue();
//	  		    			if(v=='1'){
//	  		    				getCurrentView().contentPanel.getForm().findField('CP_PRODUCT').setValue(null);
//	  		    				getCurrentView().contentPanel.getForm().findField('CP_PRODUCT').bindStore(findLookupByType('CP_PRODUCT_1'));
//	  		    			}else if(v == '2'){
//	  		    				getCurrentView().contentPanel.getForm().findField('CP_PRODUCT').setValue(null);
//	  		    				getCurrentView().contentPanel.getForm().findField('CP_PRODUCT').bindStore(findLookupByType('CP_PRODUCT_2'));
//	  		    			}else if(v == '3'){
//	  		    				getCurrentView().contentPanel.getForm().findField('CP_PRODUCT').setValue(null);
//	  		    				getCurrentView().contentPanel.getForm().findField('CP_PRODUCT').bindStore(findLookupByType('CP_PRODUCT_3'));
//	  		    			}else if(v == '4'){
//	  		    				getCurrentView().contentPanel.getForm().findField('CP_PRODUCT').setValue(null);
//	  		    				getCurrentView().contentPanel.getForm().findField('CP_PRODUCT').bindStore(findLookupByType('CP_PRODUCT_4'));
//	  		    			}else if(v == '5'){
//	  		    				getCurrentView().contentPanel.getForm().findField('CP_PRODUCT').setValue(null);
//	  		    				getCurrentView().contentPanel.getForm().findField('CP_PRODUCT').bindStore(findLookupByType('CP_PRODUCT_5'));
//	  		    			}else if(v == '6'){
//	  		    				getCurrentView().contentPanel.getForm().findField('CP_PRODUCT').setValue(null);
//	  		    				getCurrentView().contentPanel.getForm().findField('CP_PRODUCT').bindStore(findLookupByType('CP_PRODUCT_6'));
//	  		    			}else{
//	  		    				getCurrentView().contentPanel.getForm().findField('CP_PRODUCT').setValue(null);
//	  		    				getCurrentView().contentPanel.getForm().findField('CP_PRODUCT').bindStore(findLookupByType('CP_PRODUCT_7'));
//	  		    			}
//	  					}
//		    	}},
//	            {name : 'CP_PRODUCT',text  : '授信产品',hidden : false,translateType:'CP_PRODUCT',editable:true},
//	            {name : 'CP_USE',text  : '用途',hidden : false,translateType:'CP_USE',editable:true},
//	            {name : 'CP_CURRENCY',text  : '币种',hidden : false,translateType:'CURRENCY',editable:true},
//	            {name : 'CP_LIMITMONEY',text  : '额度金额(人民币/千元)',hidden : false,vtype:'money',maxLength:20},
//	            {name : 'CP_COLLATERAL',text  : '担保品',hidden : false,maxLength:50},
//				{name : 'CP_DBRATE',text  : '担保比率(%)',hidden : false,vtype:'dbPercent',maxLength:20}
//				],
//			fn : function(ID,TASK_NUMBER,CP_USE,CP_PRODUCT_P,CP_PRODUCT,CP_CURRENCY,CP_LIMITMONEY,CP_COLLATERAL,CP_DBRATE){
//				return [ID,TASK_NUMBER,CP_USE,CP_PRODUCT_P,CP_PRODUCT,CP_CURRENCY,CP_LIMITMONEY,CP_COLLATERAL,CP_DBRATE];
//			}
//	},{
//		   columnCount : 1,
//		   fields:[{name:'CP_MEMO',text:'备注',xtype:'textarea',maxLength:50}],
//		   fn:function(CP_MEMO){
//			   return [CP_MEMO];
//		   }
//	}],
//	formButtons:[{
//		text : '保存',
//		//保存数据					 
//		fn : function(formPanel,baseform){						
//			if(!baseform.isValid())
//				{
//					Ext.Msg.alert('提示','请输入完整或正确的格式！');
//					return false;
//				}
//			    var commintData = translateDataKey(baseform.getFieldValues(),_app.VIEWCOMMITTRANS);
//			    if(commintData.cpProduct == null || commintData.cpProduct == ''){
//			    	Ext.Msg.alert('提示',"请选择[授信产品]!");
//			    	return false;
//			    }
//			    
//				Ext.Msg.wait('正在提交数据，请稍等...','提示');
//			    Ext.Ajax.request({
//	 				url : basepath + '/ocrmFInterviewCreditpro.json?flag=1',
//	 				method : 'POST',
//	 				params : commintData,
//					success : function() {
//						Ext.Msg.hide(); 
////						Ext.Msg.alert('提示',"操作成功!");
//						showCustomerViewByTitle('CALLREPORT维护');
//						/**
//						 * 回填CALLREPORT维护界面数据
//						 */
//						setBaseValue(getCurrentView().contentPanel.getForm());
//					},
//					failure : function(response) {
//						var resultArray = Ext.util.JSON.decode(response.status);
//				 		if(resultArray == 403) {
//			           		Ext.Msg.alert('提示', response.responseText);
//				 		}else{
//							Ext.Msg.alert('提示', '操作失败,失败原因:' + response.responseText);
//		 				}
//					}
//				});
//			}
//	},{
//		text : '取消',
//		fn : function(formPanel,basicForm){
//			showCustomerViewByTitle('CALLREPORT维护');
//			/**
//			 * 回填CALLREPORT维护界面数据
//			 */
//			setBaseValue(getCurrentView().contentPanel.getForm());
//		}
//	}]
//},
{//20
	title : '新增拟申请授信产品',   //新户及旧户新案
	hideTitle : true,
	type : 'form',
//	suspendWidth: aw,
	autoLoadSeleted : true,
	frame : true,
	groups : [{
	columnCount : 2 ,
	fields : [	
	            {name: 'ID'},
	            {name: 'TASK_NUMBER'},
	            {name: 'CP_USE',text: '贷款用途',translateType:'CP_USE',width:120},
	            {name : 'CP_PRODUCT',text : '授信产品',hidden : false,translateType:'CP_PRODUCT',width:120,editable:true},
	            {name: 'CP_CURRENCY',text:'币种',translateType:'CURRENCY',width:120},
	            {name: 'CP_LIMITMONEY',text:'额度金额(人民币/千元)',dataType:'decimal',viewFn:money('0,000.00'),minValue:0,maxLength:24,width:120},
	            {name: 'CREDIT_PERIOD',text:'贷款期限(月)',vtype:'number',width:120},
	            {name: 'REPAYMENT_METHOD',text:'还款方式',translateType:'DM0004',width:120}
	            ],
			fn : function(ID,TASK_NUMBER,CP_USE,CP_PRODUCT,CP_CURRENCY,CP_LIMITMONEY,CREDIT_PERIOD,REPAYMENT_METHOD){
				ID.hidden = true;
				TASK_NUMBER.hidden = true;
				return [ID,TASK_NUMBER,CP_USE,CP_PRODUCT,CP_CURRENCY,CP_LIMITMONEY,CREDIT_PERIOD,REPAYMENT_METHOD];
			}
	},{
		   columnCount : 1,
		   fields:[{name:'CP_MEMO',text:'备注',xtype:'textarea',maxLength:50}],
		   fn:function(CP_MEMO){
			   return [CP_MEMO];
		   }
	}],
	formButtons:[{
		text : '保存并继续增加',
		fn : function(formPanel,baseform){						
			if(!baseform.isValid())
				{
					Ext.Msg.alert('提示','请输入完整或正确的格式！');
					return false;
				}
			    var commintData = translateDataKey(baseform.getFieldValues(),_app.VIEWCOMMITTRANS);
		    	if(commintData.cpProduct == null || commintData.cpProduct == ''){
		    		Ext.Msg.alert('提示',"请选择[授信产品]!");
		    		return false;
		    	}
			    
				Ext.Msg.wait('正在提交数据，请稍等...','提示');
			    Ext.Ajax.request({
					url : basepath + '/ocrmFInterviewCreditpro.json?flag=0',//0新增1修改
					method : 'POST',
					params : commintData,
					success : function() {
						Ext.Msg.hide(); 
//						Ext.Msg.alert('提示',"操作成功!");
//						showCustomerViewByTitle('CALLREPORT维护');
						/**
						 * 回填CALLREPORT维护界面数据
						 */
//						setBaseValue(getCurrentView().contentPanel.getForm());
						/**
						 * 清除页面数据
						 */
						cleanData(getCurrentView().contentPanel.getForm(),commintData);
						getCurrentView().contentPanel.getForm().findField('CP_PRODUCT').hide();
					},
					failure : function(response) {
						var resultArray = Ext.util.JSON.decode(response.status);
				 		if(resultArray == 403) {
			           		Ext.Msg.alert('提示', response.responseText);
				 		}else{
							Ext.Msg.alert('提示', '操作失败,失败原因:' + response.responseText);
		 				}
					}
				});
			}
	},{
		text : '保存并退出',
		fn : function(formPanel,baseform){						
			if(!baseform.isValid())
				{
					Ext.Msg.alert('提示','请输入完整或正确的格式！');
					return false;
				}
			    var commintData = translateDataKey(baseform.getFieldValues(),_app.VIEWCOMMITTRANS);
		    	if(commintData.cpProduct == null || commintData.cpProduct == ''){
		    		Ext.Msg.alert('提示',"请选择[授信产品]!");
		    		return false;
		    	}
			    
				Ext.Msg.wait('正在提交数据，请稍等...','提示');
			    Ext.Ajax.request({
					url : basepath + '/ocrmFInterviewCreditpro.json?flag=0',//0新增1修改
					method : 'POST',
					params : commintData,
					success : function() {
						Ext.Msg.hide(); 
//						Ext.Msg.alert('提示',"操作成功!");
						showCustomerViewByIndex(3);
						/**
						 * 回填CALLREPORT维护界面数据
						 */
						setBaseValue(getCurrentView().contentPanel.getForm());
					},
					failure : function(response) {
						var resultArray = Ext.util.JSON.decode(response.status);
				 		if(resultArray == 403) {
			           		Ext.Msg.alert('提示', response.responseText);
				 		}else{
							Ext.Msg.alert('提示', '操作失败,失败原因:' + response.responseText);
		 				}
					}
				});
			}
	},{
		text : '取消',
		fn : function(formPanel,basicForm){
			showCustomerViewByIndex(3);
			/**
			 * 回填CALLREPORT维护界面数据
			 */
			setBaseValue(getCurrentView().contentPanel.getForm());
		}
	}]
},{//21
	title : '修改拟申请授信产品',  //新户及旧户新案
	hideTitle : true,
	type : 'form',
//	suspendWidth: aw,
	autoLoadSeleted : true,
	frame : true,
	groups : [{
		columnCount : 2 ,
		fields : [	
		            {name : 'ID'},
		            {name : 'TASK_NUMBER'},
		            {name : 'CP_USE',text  : '贷款用途',translateType:'CP_USE'},
		            {name : 'CP_PRODUCT',text  : '授信产品',translateType:'CP_PRODUCT'},
		            {name : 'CP_CURRENCY',text  : '币种',translateType:'CURRENCY'},
		            {name : 'CP_LIMITMONEY',text  : '额度金额(人民币/千元)',dataType: 'decimal', viewFn: money('0,000.00'),minValue: 0,maxLength:24},
		            {name : 'CREDIT_PERIOD',text : '贷款期限(月)',vtype:'number'},
		            {name : 'REPAYMENT_METHOD',text : '还款方式',translateType:'DM0004'}
		            ],
				fn : function(ID,TASK_NUMBER,CP_USE,CP_PRODUCT,CP_CURRENCY,CP_LIMITMONEY,CREDIT_PERIOD,REPAYMENT_METHOD){
					ID.hidden = true;
					TASK_NUMBER.hidden = true;
					return [ID,TASK_NUMBER,CP_USE,CP_PRODUCT,CP_CURRENCY,CP_LIMITMONEY,CREDIT_PERIOD,REPAYMENT_METHOD];
				}
		},{
		   columnCount : 1,
		   fields:[{name:'CP_MEMO',text:'备注',xtype:'textarea',maxLength:50}],
		   fn:function(CP_MEMO){
			   return [CP_MEMO];
		   }
	}],
	formButtons:[{
		text : '保存',
		//保存数据					 
		fn : function(formPanel,baseform){						
			if(!baseform.isValid())
				{
					Ext.Msg.alert('提示','请输入完整或正确的格式！');
					return false;
				}
			    var commintData = translateDataKey(baseform.getFieldValues(),_app.VIEWCOMMITTRANS);
			    if(commintData.cpProduct == null || commintData.cpProduct == ''){
			    	Ext.Msg.alert('提示',"请选择[授信产品]!");
			    	return false;
			    }
			    
				Ext.Msg.wait('正在提交数据，请稍等...','提示');
			    Ext.Ajax.request({
	 				url : basepath + '/ocrmFInterviewCreditpro.json?flag=1',
	 				method : 'POST',
	 				params : commintData,
					success : function() {
						Ext.Msg.hide(); 
//						Ext.Msg.alert('提示',"操作成功!");
						showCustomerViewByIndex(3);
						/**
						 * 回填CALLREPORT维护界面数据
						 */
						setBaseValue(getCurrentView().contentPanel.getForm());
					},
					failure : function(response) {
						var resultArray = Ext.util.JSON.decode(response.status);
				 		if(resultArray == 403) {
			           		Ext.Msg.alert('提示', response.responseText);
				 		}else{
							Ext.Msg.alert('提示', '操作失败,失败原因:' + response.responseText);
		 				}
					}
				});
			}
	},{
		text : '取消',
		fn : function(formPanel,basicForm){
			showCustomerViewByIndex(3);
			/**
			 * 回填CALLREPORT维护界面数据
			 */
			setBaseValue(getCurrentView().contentPanel.getForm());
		}
	}]
},{//22
	title: '新增担保品信息',
	hideTitle: true,
	type: 'form',
//	suspendWidth: aw,
	autoLoadSeleted: true,
	groups: [{
		columnCount:2,
		fields: [
		        {name:'ID',hidden:true},
			    {name:'TASK_NUMBER',hidden:true},
			    {name:'COLLATERAL_TYPE',text:'担保品类型',translateType:'DM0019',allowBlank:false},
			    {name:'ESTIMATE_VALUE',text:'担保品估值(人民币 /千元)',dataType:'decimal',viewFn:money('0,000.00'),minValue:0,maxLength:24,allowBlank:false},
			    {name:'NET_VALUE',text:'担保品净值(人民币 /千元)',dataType:'decimal',viewFn:money('0,000.00'),minValue:0,maxLength:24,allowBlank:false},
			    {name:'COLLATERAL_ADDR',text:'担保品所在地',allowBlank:true}
		         ],
         fn : function(ID,TASK_NUMBER,COLLATERAL_TYPE,ESTIMATE_VALUE,NET_VALUE,COLLATERAL_ADDR){
				return [ID,TASK_NUMBER,COLLATERAL_TYPE,ESTIMATE_VALUE,NET_VALUE,COLLATERAL_ADDR];
			}
	}],
	formButtons:[{
		text : '保存并继续增加',
		fn : function(formPanel,baseform){						
			if(!baseform.isValid())
				{
					Ext.Msg.alert('提示','请输入完整或正确的格式！');
					return false;
				}
			    var commintData = translateDataKey(baseform.getFieldValues(),_app.VIEWCOMMITTRANS);
		    	if(commintData.collateralType == null || commintData.collateralType == ''){
		    		Ext.Msg.alert('提示',"请选择[担保品类型]!");
		    		return false;
		    	}
			    
				Ext.Msg.wait('正在提交数据，请稍等...','提示');
			    Ext.Ajax.request({
					url : basepath + '/ocrmFInterviewCollateral.json?flag=0',//0新增1修改
					method : 'POST',
					params : commintData,
					success : function() {
						Ext.Msg.hide(); 
						/**
						 * 回填CALLREPORT维护界面数据
						 */
//						setBaseValue(getCurrentView().contentPanel.getForm());
						/**
						 * 清除页面数据
						 */
						cleanData(getCurrentView().contentPanel.getForm(),commintData);
					},
					failure : function(response) {
						var resultArray = Ext.util.JSON.decode(response.status);
				 		if(resultArray == 403) {
			           		Ext.Msg.alert('提示', response.responseText);
				 		}else{
							Ext.Msg.alert('提示', '操作失败,失败原因:' + response.responseText);
		 				}
					}
				});
			}
	},{
		text : '保存并退出',
		fn : function(formPanel,baseform){						
			if(!baseform.isValid())
				{
					Ext.Msg.alert('提示','请输入完整或正确的格式！');
					return false;
				}
			    var commintData = translateDataKey(baseform.getFieldValues(),_app.VIEWCOMMITTRANS);
			    if(commintData.collateralType == null || commintData.collateralType == ''){
		    		Ext.Msg.alert('提示',"请选择[担保品类型]!");
		    		return false;
		    	}
			    
				Ext.Msg.wait('正在提交数据，请稍等...','提示');
			    Ext.Ajax.request({
					url : basepath + '/ocrmFInterviewCollateral.json?flag=0',//0新增1修改
					method : 'POST',
					params : commintData,
					success : function() {
						Ext.Msg.hide(); 
						showCustomerViewByIndex(3);
						/**
						 * 回填CALLREPORT维护界面数据
						 */
						setBaseValue(getCurrentView().contentPanel.getForm());
					},
					failure : function(response) {
						var resultArray = Ext.util.JSON.decode(response.status);
				 		if(resultArray == 403) {
			           		Ext.Msg.alert('提示', response.responseText);
				 		}else{
							Ext.Msg.alert('提示', '操作失败,失败原因:' + response.responseText);
		 				}
					}
				});
			}
	},{
		text : '取消',
		fn : function(formPanel,basicForm){
			showCustomerViewByIndex(3);
			/**
			 * 回填CALLREPORT维护界面数据
			 */
			setBaseValue(getCurrentView().contentPanel.getForm());
		}
	}]
},{//23
	title: '修改担保品信息',
	hideTitle: true,
	type: 'form',
//	suspendWidth: aw,
	autoLoadSeleted: true,
	groups: [{
		columnCount: 2,
		fields: [
		        {name:'ID',hidden:true},
			    {name:'TASK_NUMBER',hidden:true},
			    {name:'COLLATERAL_TYPE',text:'担保品类型',translateType:'DM0019',editable:true},
			    {name:'ESTIMATE_VALUE',text:'担保品估值(人民币 千元)',vtype:'money',editable:true},
			    {name:'NET_VALUE',text:'担保品净值(人民币 千元)',vtype:'money',editable:true},
			    {name:'COLLATERAL_ADDR',text:'担保品所在地',editable:true}
		         ],
         fn : function(ID,TASK_NUMBER,COLLATERAL_TYPE,ESTIMATE_VALUE,NET_VALUE,COLLATERAL_ADDR){
				return [ID,TASK_NUMBER,COLLATERAL_TYPE,ESTIMATE_VALUE,NET_VALUE,COLLATERAL_ADDR];
			}
	}],
	formButtons:[{
		text : '保存',
		//保存数据					 
		fn : function(formPanel,baseform){						
			if(!baseform.isValid())
				{
					Ext.Msg.alert('提示','请输入完整或正确的格式！');
					return false;
				}
			    var commintData = translateDataKey(baseform.getFieldValues(),_app.VIEWCOMMITTRANS);
			    if(commintData.collateralType == null || commintData.collateralType == ''){
		    		Ext.Msg.alert('提示',"请选择[担保品类型]!");
		    		return false;
		    	}
			    
				Ext.Msg.wait('正在提交数据，请稍等...','提示');
			    Ext.Ajax.request({
	 				url : basepath + '/ocrmFInterviewCollateral.json?flag=1',  //0新增 1修改
	 				method : 'POST',
	 				params : commintData,
					success : function() {
						Ext.Msg.hide(); 
//						Ext.Msg.alert('提示',"操作成功!");
						showCustomerViewByIndex(3);
						/**
						 * 回填CALLREPORT维护界面数据
						 */
						setBaseValue(getCurrentView().contentPanel.getForm());
					},
					failure : function(response) {
						var resultArray = Ext.util.JSON.decode(response.status);
				 		if(resultArray == 403) {
			           		Ext.Msg.alert('提示', response.responseText);
				 		}else{
							Ext.Msg.alert('提示', '操作失败,失败原因:' + response.responseText);
		 				}
					}
				});
			}
	},{
		text : '取消',
		fn : function(formPanel,basicForm){
			showCustomerViewByIndex(3);
			/**
			 * 回填CALLREPORT维护界面数据
			 */
			setBaseValue(getCurrentView().contentPanel.getForm());
		}
	}]
},{//24
	title:'CALLREPORT维护',//旧户
	hideTitle:true,
	suspendWidth: aw,
	type:'form',
	autoLoadSeleted : true,
	groups : [{
		columnCount : 2,
		fields:['MGR_ID',
//		        {name:'CUST_NAME',text:'客户名称',xtype:'customerqueryqz',hiddenName:'CUST_ID',allowBlank:false,custType:'1',
//			singleSelected:true,newCust:true,callback:function(a){
//				getCurrentView().setValues({
//					'INDUST_TYPE':getCurrentView().contentPanel.form.findField("CUST_NAME").industType,
//					'LINK_PHONE':getCurrentView().contentPanel.form.findField("CUST_NAME").mobileNum,
//					'LINK_MAN':getCurrentView().contentPanel.form.findField("CUST_NAME").linkUser,
//					'TASK_TYPE':getCurrentView().contentPanel.form.findField("CUST_NAME").potentialFlag,
//					'MGR_NAME':getCurrentView().contentPanel.form.findField("CUST_NAME").mgrName,
//					'MGR_ID':getCurrentView().contentPanel.form.findField("CUST_NAME").mgrId
//				});
//		}},
		{name:'CUST_NAME',text:'客户名称',editable:false},
//		'MGR_NAME',
		{name:'MGR_NAME',text:'客户经理名称',editable:false},
		'INTERVIEWEE_NAME','INTERVIEWEE_POST','INTERVIEWEE_PHONE','VISIT_TIME','VISIT_END_TIME','VISIT_START_TIME','CALL_TIME','TASK_TYPE','VISIT_TYPE','ID','TASK_NUMBER','JOIN_PERSON_ID'],
		fn:function(MGR_ID,CUST_NAME,MGR_NAME,INTERVIEWEE_NAME,INTERVIEWEE_POST,INTERVIEWEE_PHONE,VISIT_TIME,VISIT_END_TIME,VISIT_START_TIME,CALL_TIME,TASK_TYPE,VISIT_TYPE,ID,TASK_NUMBER,JOIN_PERSON_ID){
			MGR_ID.hidden = true;
			TASK_TYPE.hidden = true;
			ID.hidden = true;
			TASK_NUMBER.hidden = true;
			JOIN_PERSON_ID.hidden = true;
			CUST_NAME.readOnly = true;
			MGR_NAME.readOnly = true;
			TASK_TYPE.readOnly = true;
//			VISIT_TYPE.readOnly = true;
//			VISIT_TYPE.cls = "x-readOnly";
			CUST_NAME.cls = "x-readOnly";
			MGR_NAME.cls = "x-readOnly";
			TASK_TYPE.cls = "x-readOnly";
			return [MGR_ID,CUST_NAME,MGR_NAME,INTERVIEWEE_NAME,INTERVIEWEE_POST,INTERVIEWEE_PHONE,VISIT_TIME,VISIT_END_TIME,VISIT_START_TIME,TASK_TYPE,VISIT_TYPE,ID,TASK_NUMBER,CALL_TIME,JOIN_PERSON_ID];
		}
	},{
		   columnCount : 0.95,
		   fields:[{name:'JOIN_PERSON',text:'本次参与人员',xtype:'userchooseNew',hiddenName:'JOIN_PERSON_ID',searchType:'ALLORG',resutlWidth:100,singleSelect: false}],
		   fn:function(JOIN_PERSON){
			   return [JOIN_PERSON];
		   }
	},
	{
		columnCount:0.95,
		fields:['TEST'],
		fn:function(TEST){
			return[jt_form];
		}
	},{//客户运营状况
		columnCount:0.95,
		fields:['CUS_STATUS'],
		fn:function(CUS_STATUS){
			return[CUS_STATUS];
		}
	},{//营运状况表现
		columnCount:2,
		fields:['ISBUSCHANGE','BUS_EXPLAIN','ISREVCHANGE','REV_EXPLAIN','ISPROCHANGE','PRO_EXPLAIN','ISSUPCHANGE','SUP_EXPLAIN','ISPURCHANGE','PUR_EXPLAIN',
		        'ISEQUCHANGE','EQU_EXPLAIN','ISOPCCHANGE','OPC_EXPLAIN','ISCOLCHANGE','COL_EXPLAIN','ISSYMCHANGE','SYM_EXPLAIN','MARK_RESULT','MARK_REFUSEREASON'],
		fn:function(ISBUSCHANGE,BUS_EXPLAIN,ISREVCHANGE,REV_EXPLAIN,ISPROCHANGE,PRO_EXPLAIN,ISSUPCHANGE,SUP_EXPLAIN,ISPURCHANGE,PUR_EXPLAIN,
		        ISEQUCHANGE,EQU_EXPLAIN,ISOPCCHANGE,OPC_EXPLAIN,ISCOLCHANGE,COL_EXPLAIN,ISSYMCHANGE,SYM_EXPLAIN,MARK_RESULT,MARK_REFUSEREASON){
			MARK_RESULT.hidden = true;
			MARK_REFUSEREASON.hidden = true;
			return[ISBUSCHANGE,BUS_EXPLAIN,ISREVCHANGE,REV_EXPLAIN,ISPROCHANGE,PRO_EXPLAIN,ISSUPCHANGE,SUP_EXPLAIN,ISPURCHANGE,PUR_EXPLAIN,
			        ISEQUCHANGE,EQU_EXPLAIN,ISOPCCHANGE,OPC_EXPLAIN,ISCOLCHANGE,COL_EXPLAIN,ISSYMCHANGE,SYM_EXPLAIN,MARK_RESULT,MARK_REFUSEREASON];
		}
	},{
		   columnCount : 0.95,
		   fields:['MARK_PRODUCT'],
		   fn:function(MARK_PRODUCT){
			   return [MARK_PRODUCT];
		   }
	},{//本次拜访营销情况
		columnCount : 2,
		fields:[{name:'MARK_RESULT_OLD',text:'拜访结果',editable:true,translateType:'VISIT_RESULT_QS_OLD',listeners:{
				    		select:function(combo,record){
				    			var v = this.getValue();
				    			if(v=='5'){
				    				getCurrentView().contentPanel.getForm().findField('MARK_REFUSEREASON_OLD').show(true);
				    			}else{
				    				getCurrentView().contentPanel.getForm().findField('MARK_REFUSEREASON_OLD').hide(true);
				    				getCurrentView().contentPanel.getForm().findField('MARK_REFUSEREASON_OLD').setValue(null);
				    			}
							}
					}},'MARK_REFUSEREASON_OLD'],
		fn:function(MARK_RESULT_OLD,MARK_REFUSEREASON_OLD){
			return [MARK_RESULT_OLD,MARK_REFUSEREASON_OLD];
		}
	},{
		   columnCount : 0.95,
		   fields:['RES_FOLLOWUP'],
		   fn:function(RES_FOLLOWUP){
			   return [RES_FOLLOWUP];
		   }
	},{
		   columnCount : 0.95,
		   fields:['RES_OTHERINFO'],
		   fn:function(RES_OTHERINFO){
			   return [RES_OTHERINFO];
		   }
	},{
		columnCount : 2,
		fields:['CALL_SPENDTIME','CALL_NEXTTIME'],
		fn:function(CALL_SPENDTIME,CALL_NEXTTIME){
			return [CALL_SPENDTIME,CALL_NEXTTIME];
		}
	}],
	formButtons : [{
		text:'保存',
		id:'fk_toSave',
		fn : function(formPanel,basicForm){
			 if (!formPanel.getForm().isValid()) {
	               Ext.MessageBox.alert('提示','输入有误,请检查输入项');
	               return false;
	           };
           var value = formPanel.form.findField("MARK_RESULT_OLD").getValue();
           var callNexttime = formPanel.form.findField("CALL_NEXTTIME").getValue();
           if(value == '5'){
        	   if(formPanel.form.findField("MARK_REFUSEREASON_OLD").getValue() == '' || formPanel.form.findField("MARK_REFUSEREASON_OLD").getValue() == null){
        		   Ext.MessageBox.alert('提示','请填写[拒绝原因]');
	               return false;
        	   }
	        }
	        var callTime = formPanel.form.findField("CALL_TIME").getValue();
	        if(callTime != null && callTime != ''){
	        	if(callTime.format('Y-m-d') > new Date().format('Y-m-d')){
	        		Ext.MessageBox.alert('提示','实际拜访日期不能大于当前日期!');
	        		return false;
	        	}
            }
	        
	       if(callNexttime != null && callNexttime != ''){
        	   	if(callNexttime.format('Y-m-d') < new Date().format('Y-m-d')){
        	   		Ext.MessageBox.alert('提示','预计下次拜访时间不能小于当前时间!');
		            return false;
        	   	}
            }
	        
	        var data = formPanel.getForm().getFieldValues();
			var commintData = translateDataKey(data,1);
			Ext.Msg.wait('正在处理，请稍后......','系统提示');
			Ext.Ajax.request({
					url : basepath + '/ocrmFInterviewOldRecord.json',
					method : 'POST',
					params : commintData,
					success : function(response) {
						Ext.Msg.alert('提示', '操作成功');
						reloadCurrentData();
						hideCurrentView();
					},
					failure : function() {
						Ext.Msg.alert('提示', '操作失败');
						reloadCurrentData();
						hideCurrentView();
					}
			});
		}
	},{
		text:'提交',
		fn : function(formPanel,basicForm){
			 if (!formPanel.getForm().isValid()) {
	               Ext.MessageBox.alert('提示','输入有误,请检查输入项');
	               return false;
	           };
           var value = formPanel.form.findField("MARK_RESULT_OLD").getValue();
           var callNexttime = formPanel.form.findField("CALL_NEXTTIME").getValue();
           if(value == '5'){
        	   if(formPanel.form.findField("MARK_REFUSEREASON_OLD").getValue() == '' || formPanel.form.findField("MARK_REFUSEREASON_OLD").getValue() == null){
        		   Ext.MessageBox.alert('提示','请填写[拒绝理由]');
	               return false;
        	   }
	        }
	        var callTime = formPanel.form.findField("CALL_TIME").getValue();
	        if(callTime == '' || callTime == null){
	        	Ext.MessageBox.alert('提示','请填写[实际拜访日期]!');
	            return false;
	        }
	        if(callTime.format('Y-m-d') > new Date().format('Y-m-d')){
	        	 Ext.MessageBox.alert('提示','实际拜访日期不能大于当前日期!');
	             return false;
	        }
	        //提交是要填写拜访结果
	        var markResultOld = formPanel.form.findField("MARK_RESULT_OLD").getValue();
	        if(markResultOld == '' || markResultOld == null){
          	   Ext.MessageBox.alert('提示','请填写[拜访结果]');
	               return false;
	        }
	        
	        if(callNexttime != null && callNexttime != ''){
        	   	if(callNexttime.format('Y-m-d') < new Date().format('Y-m-d')){
        	   		Ext.MessageBox.alert('提示','预计下次拜访时间不能小于当前时间!');
		            return false;
        	   	}
            }
	        
	        var data = formPanel.getForm().getFieldValues();
			var commintData = translateDataKey(data,1);
			Ext.Msg.wait('正在处理，请稍后......','系统提示');
			Ext.Ajax.request({
					url : basepath + '/ocrmFInterviewOldRecord!save.json',
					method : 'GET',
					params : commintData,
					success : function(response) {
						 var ret = Ext.decode(response.responseText);
						 var instanceid = ret.instanceid;//流程实例ID
						 var currNode = ret.currNode;//当前节点
						 var nextNode = ret.nextNode;//下一步节点
						 selectUserList(instanceid,currNode,nextNode);//选择下一步办理人
					}
			});
		}
	},{
		text:'关闭',
		fn : function(formPanel,basicForm){
			 reloadCurrentData();
			 hideCurrentView();
		}
	}]
},{//25
	title:'补充CALLREPORT维护',
	hideTitle:true,
	type:'form',
	autoLoadSeleted : true,
	groups : [{
		columnCount : 2,
		fields:[{name:'CUST_NAME',text:'客户名称',xtype:'customerqueryqz',hiddenName:'CUST_ID',allowBlank:false,custType:'1',
			singleSelected:true,newCust:true},
			'MGR_NAME',
			'VISIT_TIME',
			{name:"VISIT_TYPE",text:"拜访类型",translateType:'VISIT_TYPE_OLD',allowBlank:false,editable:true},
			'TASK_TYPE',
			'ID','REVIEW_STATE','TASK_NUMBER','MGR_ID','INTERVIEWEE_NAME','INTERVIEWEE_POST','INTERVIEWEE_PHONE'],
		fn:function(CUST_NAME,MGR_NAME,VISIT_TIME,VISIT_TYPE,TASK_TYPE,ID,REVIEW_STATE,TASK_NUMBER,MGR_ID,INTERVIEWEE_NAME,INTERVIEWEE_POST,INTERVIEWEE_PHONE){
			MGR_ID.hidden = true;
			TASK_TYPE.hidden = true;
			ID.hidden = true;
			REVIEW_STATE.hidden = true;
			TASK_NUMBER.hidden = true;
			CUST_NAME.cls = "x-readOnly";
			CUST_NAME.readOnly = true;
			MGR_NAME.cls = "x-readOnly";
			MGR_NAME.readOnly = true;
			VISIT_TIME.cls = "x-readOnly";
			VISIT_TIME.readOnly = true;
			INTERVIEWEE_NAME.hidden = true;
			INTERVIEWEE_POST.hidden = true;
			INTERVIEWEE_PHONE.hidden = true;
			return [CUST_NAME,MGR_NAME,VISIT_TIME,VISIT_TYPE,TASK_TYPE,ID,REVIEW_STATE,TASK_NUMBER,MGR_ID,INTERVIEWEE_NAME,INTERVIEWEE_POST,INTERVIEWEE_PHONE];
		}
	}],formButtons : [{
		text:'确认',
		fn : function(formPanel,basicForm){
			 if (!formPanel.getForm().isValid()) {
	               Ext.MessageBox.alert('提示','输入有误,请检查输入项');
	               return false;
	           };
	        var type = getCurrentView().contentPanel.form.findField("VISIT_TYPE").getValue();
	        custData.VISIT_TYPE = type;
	        if(type == 2){//旧户新案
				showCustomerViewByIndex(3);
			}else {
				showCustomerViewByIndex(24);
			}
		}
	}]
},{//26
	title: '主管审批意见',
	type: 'grid',
	url  : basepath+'/wfComment.json',//主管审批意见访问后台数据的路径
	frame : true,
	isCsm:false,
	fields: {
		fields : [
			  	  {name: 'TASK_NUMBER',text:'TASK_NUMBER',hidden:true},
		          {name: 'USERNAME', text: '审批人'},
		          {name: 'COMMENTCONTENT', text: '审批意见',width:400},
		          {name: 'COMMENTTIME', text: '审批日期', format:'Y-m-d h:M:s',width:150}
				]
		}
},{//27
	title : '新增公司相关人员信息',
	hideTitle : true,
	type : 'form',
	autoLoadSeleted : true,
	frame : true,
	groups : [{
		columnCount : 2 ,
		fields : [	
		            {name : 'ID',hidden : true},
		            {name : 'TASK_NUMBER',hidden : true},
		            {name : 'PER_ROLE',text:'人员角色',translateType:'PER_ROLE'},
		            {name : 'PER_NAME',text:'人员姓名'},
		            {name : 'PER_TELPHONE',text:'人员电话'},
		            {name : 'SHOLDER_CAPITAL',text:'股东持股出资比例'},
		            {name : 'AMOUNT_CAPITAL',text:'出资金额',dataType:'decimal',viewFn:money('0,000.00'),minValue:0,maxLength:24}
					],
				fn : function(ID,TASK_NUMBER,PER_ROLE,PER_NAME,PER_TELPHONE,SHOLDER_CAPITAL,AMOUNT_CAPITAL,PER_EXPLAIN){
					return [ID,TASK_NUMBER,PER_ROLE,PER_NAME,PER_TELPHONE,SHOLDER_CAPITAL,AMOUNT_CAPITAL,PER_EXPLAIN];
				}
		},{
			columnCount : 0.95,
			fields : [{name : 'PER_EXPLAIN',text:'说明',xtype:'textarea'}],
			fn : function(PER_EXPLAIN){
					return [PER_EXPLAIN];
				}
			}],
	formButtons:[{
		text : '保存并继续增加',
		fn : function(formPanel,baseform){						
			if(!baseform.isValid())
				{
					Ext.Msg.alert('提示','请输入完整或正确的格式！');
					return false;
				}
			    var commintData = translateDataKey(baseform.getFieldValues(),_app.VIEWCOMMITTRANS);
				Ext.Msg.wait('正在提交数据，请稍等...','提示');
			    Ext.Ajax.request({
					url : basepath + '/ocrmFInterviewPerson.json?flag=0',//0新增1修改
					method : 'POST',
					params : commintData,
					success : function() {
						Ext.Msg.hide(); 
						cleanData(getCurrentView().contentPanel.getForm(),commintData);
					},
					failure : function(response) {
						var resultArray = Ext.util.JSON.decode(response.status);
				 		if(resultArray == 403) {
			           		Ext.Msg.alert('提示', response.responseText);
				 		}else{
							Ext.Msg.alert('提示', '操作失败,失败原因:' + response.responseText);
		 				}
					}
				});
			}
	},{
		text : '保存并退出',
		fn : function(formPanel,baseform){						
			if(!baseform.isValid())
				{
					Ext.Msg.alert('提示','请输入完整或正确的格式！');
					return false;
				}
			    var commintData = translateDataKey(baseform.getFieldValues(),_app.VIEWCOMMITTRANS);
				Ext.Msg.wait('正在提交数据，请稍等...','提示');
			    Ext.Ajax.request({
					url : basepath + '/ocrmFInterviewPerson.json?flag=0',//0新增1修改
					method : 'POST',
					params : commintData,
					success : function() {
						Ext.Msg.hide(); 
						showCustomerViewByIndex(3);
						/**
						 * 回填CALLREPORT维护界面数据
						 */
						setBaseValue(getCurrentView().contentPanel.getForm());
					},
					failure : function(response) {
						var resultArray = Ext.util.JSON.decode(response.status);
				 		if(resultArray == 403) {
			           		Ext.Msg.alert('提示', response.responseText);
				 		}else{
							Ext.Msg.alert('提示', '操作失败,失败原因:' + response.responseText);
		 				}
					}
				});
			}
	},{
		text : '取消',
		fn : function(formPanel,basicForm){
			showCustomerViewByIndex(3);
			/**
			 * 回填CALLREPORT维护界面数据
			 */
			setBaseValue(getCurrentView().contentPanel.getForm());
		}
	}]
},{//28
	title : '修改公司相关人员信息',
	hideTitle : true,
	type : 'form',
	autoLoadSeleted : true,
	frame : true,
	groups : [{
		columnCount : 2 ,
		fields : [	
		            {name : 'ID',hidden : true},
		            {name : 'TASK_NUMBER',hidden : true},
		            {name : 'PER_ROLE',text:'人员角色',translateType:'PER_ROLE'},
		            {name : 'PER_NAME',text:'人员姓名'},
		            {name : 'PER_TELPHONE',text:'人员电话'},
		            {name : 'SHOLDER_CAPITAL',text:'股东持股出资比例'},
		            {name : 'AMOUNT_CAPITAL',text:'出资金额',dataType:'decimal',viewFn:money('0,000.00'),minValue:0,maxLength:24}
					],
				fn : function(ID,TASK_NUMBER,PER_ROLE,PER_NAME,PER_TELPHONE,SHOLDER_CAPITAL,AMOUNT_CAPITAL,PER_EXPLAIN){
					return [ID,TASK_NUMBER,PER_ROLE,PER_NAME,PER_TELPHONE,SHOLDER_CAPITAL,AMOUNT_CAPITAL,PER_EXPLAIN];
				}
		},{
			columnCount : 0.95,
			fields : [{name : 'PER_EXPLAIN',text:'说明',xtype:'textarea'}],
			fn : function(PER_EXPLAIN){
					return [PER_EXPLAIN];
				}
			}],
	formButtons:[{
		text : '保存',
		//保存数据					 
		fn : function(formPanel,baseform){						
			if(!baseform.isValid())
				{
					Ext.Msg.alert('提示','请输入完整或正确的格式！');
					return false;
				}
			    var commintData = translateDataKey(baseform.getFieldValues(),_app.VIEWCOMMITTRANS);
				Ext.Msg.wait('正在提交数据，请稍等...','提示');
			    Ext.Ajax.request({
	 				url : basepath + '/ocrmFInterviewPerson.json?flag=1',
	 				method : 'POST',
	 				params : commintData,
					success : function() {
						Ext.Msg.hide(); 
						showCustomerViewByIndex(3);
						/**
						 * 回填CALLREPORT维护界面数据
						 */
						setBaseValue(getCurrentView().contentPanel.getForm());
					},
					failure : function(response) {
						var resultArray = Ext.util.JSON.decode(response.status);
				 		if(resultArray == 403) {
			           		Ext.Msg.alert('提示', response.responseText);
				 		}else{
							Ext.Msg.alert('提示', '操作失败,失败原因:' + response.responseText);
		 				}
					}
				});
			}
	},{
		text : '取消',
		fn : function(formPanel,basicForm){
			showCustomerViewByIndex(3);
			/**
			 * 回填CALLREPORT维护界面数据
			 */
			setBaseValue(getCurrentView().contentPanel.getForm());
		}
	}]
}
];

var jt_form = new Ext.form.FieldSet({
	xtype:'fieldset',
	title:'拜访目的',
	titleCollapse : true,
	collapsed :false,
	anchor : '90%',
	items:[{
		layout : 'column',
		items:[{
			layout : 'form',
			columnWidth : .43,
			items:[
			       {id:'p1',fieldLabel:'正常客户定期回访',xtype:'checkbox',labelStyle:'text-align:right;',anchor:'95%',name:'PUR_CUST2CALL',inputValue:1},
			       {fieldLabel:'勘察担保品',xtype:'checkbox',labelStyle:'text-align:right;',anchor:'95%',name:'PUR_SEEK2COLL'},
			       {fieldLabel:'预警客户定期回访',xtype:'checkbox',labelStyle:'text-align:right;',anchor:'95%',name:'PUR_WARN2CALL'}
			       ]
		},{
			layout : 'form',
			columnWidth : .43,
			labelWidth:200,
			items:[
			       {fieldLabel:'年审/条件变更风管部协访',xtype:'checkbox',labelWidth:150,anchor:'95%',name:'PUR_DEFEND2CALL'},
			       {fieldLabel:'营销新产品',xtype:'checkbox',labelStyle:'text-align:right;',anchor:'95%',name:'PUR_MARK2PRO'},
			       {fieldLabel:'授信风险增加临时拜访',xtype:'checkbox',labelStyle:'text-align:right;',anchor:'95%',name:'PUR_RISK2CALL'}
			       ]
		}]
	}]
});

//=================快速定位=====================================================
var dw_fieldset = new Ext.form.FieldSet({
	xtype:'fieldset',
	title:'快速定位 ',
	titleCollapse:false,
	layout:'column',
	anchor : '90%',
	items:[
			new Ext.Button({  
			    //Ext.getBody().dom == document.body;  
			renderTo: dw_fieldset,  
			text: "轉介人電話",  
			minWidth: 100,  
			style: { marginRight:'10px' },
			handler: function(){  
				pagejump("转介人电话"); 
			    }  
			}) ,
			new Ext.Button({  
			    //Ext.getBody().dom == document.body;  
			renderTo: dw_fieldset,  
			text: "客戶來源日期",  
			minWidth: 100,  
			style: { marginRight:'10px' },
			handler: function(){  
				pagejump("客户来源日期"); 
			    }  
			}) ,
			new Ext.Button({  
			    //Ext.getBody().dom == document.body;  
			renderTo: dw_fieldset,  
			text: "跟進事項",  
			minWidth: 100,  
			style: { marginRight:'10px' },
			handler: function(){  
				pagejump("跟进事项"); 
			    }  
			}),
			new Ext.Button({  
			    //Ext.getBody().dom == document.body;  
			renderTo: dw_fieldset,  
			text: "實際拜訪日期",  
			minWidth: 100,  
			style: { marginRight:'10px' },
			handler: function(){  
				pagejump("实际拜访日期"); 
			    }  
			})
			,
			new Ext.Button({  
			    //Ext.getBody().dom == document.body;  
			renderTo: dw_fieldset,  
			text: "受訪人職位",  
			minWidth: 100,  
			style: { marginRight:'10px' },
			handler: function(){  
				pagejump("受访人职位"); 
			    }  
			})],
});

//=================主要股东及持股比列 =========begin=============================//
//var newSm = new Ext.grid.CheckboxSelectionModel();
//var editRrownum = new Ext.grid.RowNumberer({
//	  header : 'No.',
//	  width : 28
//});
//var newCm =  new Ext.grid.ColumnModel([editRrownum,newSm,	
//                                     {header : 'ID', dataIndex : 'ID',sortable : true,width : 120,hidden:true},
//                                     {header:'TASK_NUMBER',dataIndex:'TASK_NUMBER',sortable:true,width:120,hidden:true},
//                                     {header:'出资人',dataIndex:'M_SPONSOR',sortable:true,width:120},
//                                     {header:'出资比例(%)',dataIndex:'M_RATIO',sortable:true,width:120},
//                                     {header:'出资金额(人民币/千元)',dataIndex:'M_MONEY',sortable:true,width:120}
//        	                                     ]); 
//var newPanelStroe_1 = new Ext.data.Store({
//	restful : true,
//	proxy : new Ext.data.HttpProxy(
//			{
//				url : basepath + '/ocrmFInterviewShareholder.json' 
//			}),
//			reader : new Ext.data.JsonReader( {
//				root : 'json.data'
//			}, [{name:'ID'},
//			    {name:'TASK_NUMBER'},
//			    {name:'M_SPONSOR'},
//			    {name:'M_RATIO'},
//			    {name:'M_MONEY'}
//			     ])
//});	
//
//var newPanel_1 = new Ext.grid.GridPanel({
//	title : '主要股东及持股比例',
//	autoScroll: true,
//	height:200,
//    tbar : [{
//    	 text : '新增',
//		 handler:function() {
//			/**
//			 * 保存CALLREPORT维护部分页面数据
//			 */
//			saveBaseValue(getCurrentView().contentPanel.getForm());
//			showCustomerViewByTitle('新增股东及持股比例');
//		}
//    },{
//    	text : '修改',
//		handler:function(){
//			var selectLength = newPanel_1.getSelectionModel().getSelections().length;
//			
//			if (selectLength != 1) {
//				Ext.Msg.alert('提示', '请选择一条记录！');
//				return false;
//			} 
//		    record = newPanel_1.getSelectionModel().getSelections()[0];
//		    /**
//		     * 保存CALLREPORT维护部分页面数据
//		     */
//		    saveBaseValue(getCurrentView().contentPanel.getForm());
//			
//		    showCustomerViewByTitle("修改股东及持股比例");
//		    getCurrentView().contentPanel.getForm().findField('ID').setValue(record.data.ID);
//			getCurrentView().contentPanel.getForm().findField('TASK_NUMBER').setValue(record.data.TASK_NUMBER);
//			getCurrentView().contentPanel.getForm().findField('M_SPONSOR').setValue(record.data.M_SPONSOR);
//			getCurrentView().contentPanel.getForm().findField('M_RATIO').setValue(record.data.M_RATIO);
//			getCurrentView().contentPanel.getForm().findField('M_MONEY').setValue(record.data.M_MONEY);
//			
//			bl.PEC  = parseFloat(bl.PEC)-parseFloat(record.data.M_RATIO);
//		}
//    },{
//    	text:'删除',
//    	handler :function(){
//    		//删除
//    		var selectLength = newPanel_1.getSelectionModel().getSelections().length;
//    	 	var selectRecords = newPanel_1.getSelectionModel().getSelections();
//    		if(selectLength < 1){
//     			Ext.Msg.alert('提示','请选择一条数据进行操作!');
//    			return false;
//    		}
//    		var tempIdStr = '';
//    		var tempStatus = '';
//    		for(var i=0; i < selectLength; i++){
//    			var selectRecord = selectRecords[i];
//    			//临时变量，保存要删除的ID
//    			tempIdStr +=  selectRecord.data.ID;
//    			tempStatus = selectRecord.data.TASK_NUMBER;
//    		 		if( i != selectLength - 1){
//    		   		 	tempIdStr += ',';
//    					}
//    		 }
//    		Ext.MessageBox.confirm('提示','你确定删除吗!',function(buttonId){
//    		if(buttonId.toLowerCase() == 'no'){
//        		return false;
//    		}
//    		Ext.Msg.wait('正在提交数据，请稍等...','提示');
//    			Ext.Ajax.request({
//    				url : basepath + '/ocrmFInterviewShareholder!batchDel.json?idStr='+ tempIdStr,
//    				method : 'GET',
//    				success : function() {
//    					Ext.Msg.hide(); 
////    					Ext.Msg.alert('提示',"操作成功!");
//    					newPanelStroe_1.load({params:{ID:tempStatus}});
//    				},
//    				failure : function(response) {
//    					var resultArray = Ext.util.JSON.decode(response.status);
//    			 		if(resultArray == 403) {
//    		           		Ext.Msg.alert('提示', response.responseText);
//    			 		}else{
//    						Ext.Msg.alert('提示', '操作失败,失败原因:' + response.responseText);
//    	 				}
//    				}
//    			});
//    	   });
//    	}
//      }],
//	store : newPanelStroe_1,
//	frame : true,
//	sm:newSm,
//	cm : newCm,
//	loadMask : {
//		msg : '正在加载表格数据,请稍等...'
//	}
//});
//===========主要股东及持股比列====end============================================//

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
                             			var val = translateLookupByKey("F_HTYPE",value);
                            			return val?val:"";
                            			}},
                                     {header:'持有类型',dataIndex:'F_OTYPE',sortable:true,width:120,renderer:function(value){
                             			var val = translateLookupByKey("F_OTYPE",value);
                            			return val?val:"";
                            			}},
                            		 {header:'持有人',dataIndex:'F_HOLDER',sortable:true,width:120},
                            		 {header:'所在区域',dataIndex:'F_REGION',sortable:true,width:120},
                                     {header:'面积（平方米）',dataIndex:'F_AREA',sortable:true,width:120},
                                     {header:'使用状况',dataIndex:'F_UTYPE',sortable:true,width:120,renderer:function(value){
                             			var val = translateLookupByKey("F_UTYPE",value);
                            			return val?val:"";
                            			}},
                                     {header:'估价(人民币/千元)',dataIndex:'F_ASSESS',sortable:true,width:120},
                                     {header:'是否已抵押',dataIndex:'F_SECURED',sortable:true,width:120,renderer:function(value){
                              			var val = translateLookupByKey("F_SECURED",value);
                             			return val?val:"";
                             			}},
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
			    {name:'F_MEMO'},
			    {name:'F_HOLDER'},
			    {name:'F_REGION'},
			    {name:'F_SECURED'}
			     ])
});	
var newPanel_2 = new Ext.grid.GridPanel({
	title : '主要固定资产',
	autoScroll: true,
	height:200,
    tbar : [{
    	 text : '新增',
		 handler:function() {
			/**
			 * 保存CALLREPORT维护部分页面数据
			 */
			saveBaseValue(getCurrentView().contentPanel.getForm());
			showCustomerViewByTitle('新增主要固定资产');
		}
    },{
    	text : '修改',
		handler:function(){
			var selectLength = newPanel_2.getSelectionModel().getSelections().length;
			
			if (selectLength != 1) {
				Ext.Msg.alert('提示', '请选择一条记录！');
				return false;
			} 
			
		    record = newPanel_2.getSelectionModel().getSelections()[0];
		    /**
			 * 保存CALLREPORT维护部分页面数据
			 */
			saveBaseValue(getCurrentView().contentPanel.getForm());
			
		    showCustomerViewByTitle("修改主要固定资产");
		    getCurrentView().contentPanel.getForm().findField('ID').setValue(record.data.ID);
			getCurrentView().contentPanel.getForm().findField('TASK_NUMBER').setValue(record.data.TASK_NUMBER);
			getCurrentView().contentPanel.getForm().findField('F_HTYPE').setValue(record.data.F_HTYPE);
			getCurrentView().contentPanel.getForm().findField('F_OTYPE').setValue(record.data.F_OTYPE);
			getCurrentView().contentPanel.getForm().findField('F_AREA').setValue(record.data.F_AREA);
			getCurrentView().contentPanel.getForm().findField('F_UTYPE').setValue(record.data.F_UTYPE);
			getCurrentView().contentPanel.getForm().findField('F_ASSESS').setValue(record.data.F_ASSESS);
			getCurrentView().contentPanel.getForm().findField('F_MEMO').setValue(record.data.F_MEMO);
			getCurrentView().contentPanel.getForm().findField('F_HOLDER').setValue(record.data.F_HOLDER);
			getCurrentView().contentPanel.getForm().findField('F_REGION').setValue(record.data.F_REGION);
			getCurrentView().contentPanel.getForm().findField('F_SECURED').setValue(record.data.F_SECURED);
		}
    },{
    	text:'删除',
    	handler :function(){
    		//删除
    		var selectLength = newPanel_2.getSelectionModel().getSelections().length;
    	 	var selectRecords = newPanel_2.getSelectionModel().getSelections();
    	 	
    		if(selectLength < 1){
     			Ext.Msg.alert('提示','请选择一条数据进行操作!');
    			return false;
    		}
    		var tempIdStr = '';
    		var tempStatus = '';
    		for(var i=0; i < selectLength; i++){
    			var selectRecord = selectRecords[i];
    			//临时变量，保存要删除的ID
    			tempIdStr +=  selectRecord.data.ID;
    			tempStatus = selectRecord.data.TASK_NUMBER;
    		 		if( i != selectLength - 1){
    		   		 	tempIdStr += ',';
    					}
    		 }
    		Ext.MessageBox.confirm('提示','你确定删除吗!',function(buttonId){
    		if(buttonId.toLowerCase() == 'no'){
        		return false;
    		}
    		Ext.Msg.wait('正在提交数据，请稍等...','提示');
    			Ext.Ajax.request({
    				url : basepath + '/ocrmFInterviewFixedasset!batchDel.json?idStr='+ tempIdStr,
    				method : 'GET',
    				success : function() {
    					Ext.Msg.hide(); 
//    					Ext.Msg.alert('提示',"操作成功!");
    					newPanelStroe_2.load({params:{ID:tempStatus}});
    				},
    				failure : function(response) {
    					var resultArray = Ext.util.JSON.decode(response.status);
    			 		if(resultArray == 403) {
    		           		Ext.Msg.alert('提示', response.responseText);
    			 		}else{
    						Ext.Msg.alert('提示', '操作失败,失败原因:' + response.responseText);
    	 				}
    				}
    			});
    	   });
    	}
      }],
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
    tbar : [{
    	 text : '新增',
		 handler:function() {
			/**
			 * 保存CALLREPORT维护部分页面数据
			 */
			saveBaseValue(getCurrentView().contentPanel.getForm());
			showCustomerViewByTitle('新增营收获利情况');
		}
    },{
    	text : '修改',
		handler:function(){
			var selectLength = newPanel_3.getSelectionModel().getSelections().length;
			
			if (selectLength != 1) {
				Ext.Msg.alert('提示', '请选择一条记录！');
				return false;
			} 
			
		    record = newPanel_3.getSelectionModel().getSelections()[0];
		    /**
			 * 保存CALLREPORT维护部分页面数据
			 */
			saveBaseValue(getCurrentView().contentPanel.getForm());
			
		    showCustomerViewByTitle("修改营收获利情况");
		    getCurrentView().contentPanel.getForm().findField('ID').setValue(record.data.ID);
			getCurrentView().contentPanel.getForm().findField('TASK_NUMBER').setValue(record.data.TASK_NUMBER);
			getCurrentView().contentPanel.getForm().findField('P_YEARS').setValue(record.data.P_YEARS);
			getCurrentView().contentPanel.getForm().findField('P_YEARS_END').setValue(record.data.P_YEARS_END);
			getCurrentView().contentPanel.getForm().findField('P_REVENUE').setValue(record.data.P_REVENUE);
			getCurrentView().contentPanel.getForm().findField('P_GROSS').setValue(record.data.P_GROSS);
			getCurrentView().contentPanel.getForm().findField('P_PNET').setValue(record.data.P_PNET);
			getCurrentView().contentPanel.getForm().findField('P_MEMO').setValue(record.data.P_MEMO);
		}
    },{
    	text:'删除',
    	handler :function(){
    		//删除
    		var selectLength = newPanel_3.getSelectionModel().getSelections().length;
    	 	var selectRecords = newPanel_3.getSelectionModel().getSelections();
    	 	
    		if(selectLength < 1){
     			Ext.Msg.alert('提示','请选择一条数据进行操作!');
    			return false;
    		}
    		var tempIdStr = '';
    		var tempStatus = '';
    		for(var i=0; i < selectLength; i++){
    			var selectRecord = selectRecords[i];
    			//临时变量，保存要删除的ID
    			tempIdStr +=  selectRecord.data.ID;
    			tempStatus = selectRecord.data.TASK_NUMBER;
    		 		if( i != selectLength - 1){
    		   		 	tempIdStr += ',';
    					}
    		 }
    		Ext.MessageBox.confirm('提示','你确定删除吗!',function(buttonId){
    		if(buttonId.toLowerCase() == 'no'){
        		return false;
    		}
    		Ext.Msg.wait('正在提交数据，请稍等...','提示');
    			Ext.Ajax.request({
    				url : basepath + '/ocrmFInterviewProfit!batchDel.json?idStr='+ tempIdStr,
    				method : 'GET',
    				success : function() {
    					Ext.Msg.hide(); 
//    					Ext.Msg.alert('提示',"操作成功!");
    					newPanelStroe_3.load({params:{ID:tempStatus}});
    				},
    				failure : function(response) {
    					var resultArray = Ext.util.JSON.decode(response.status);
    			 		if(resultArray == 403) {
    		           		Ext.Msg.alert('提示', response.responseText);
    			 		}else{
    						Ext.Msg.alert('提示', '操作失败,失败原因:' + response.responseText);
    	 				}
    				}
    			});
    	   });
    	}
      }],
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
                             			var val = translateLookupByKey("IF_FLAG",value);
                            			return val?val:"";
                            			}},
                                     {header:'月采购金额(人民币/千元)',dataIndex:'MP_MONTH2MONEY',sortable:true,width:120},
                                     {header:'结算天数(天)',dataIndex:'MP_BALANCEDAYS',sortable:true,width:120},
                                     {header:'往来年数(年)',dataIndex:'MP_TRADEYEARS',sortable:true,width:120},
                                     {header:'结算方式',dataIndex:'MP_PAYWAY',sortable:true,width:120,renderer:function(value){
                             			var val = translateLookupByKey("PS_PAYWAY",value);
                            			return val?val:"";
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
    tbar : [{
    	 text : '新增',
		 handler:function() {
		    /**
			 * 保存CALLREPORT维护部分页面数据
			 */
			saveBaseValue(getCurrentView().contentPanel.getForm());
			showCustomerViewByTitle('新增原材料采购情况');
		}
    },{
    	text : '修改',
		handler:function(){
			var selectLength = newPanel_4.getSelectionModel().getSelections().length;
			
			if (selectLength != 1) {
				Ext.Msg.alert('提示', '请选择一条记录！');
				return false;
			} 
		    record = newPanel_4.getSelectionModel().getSelections()[0];
		    /**
			 * 保存CALLREPORT维护部分页面数据
			 */
			saveBaseValue(getCurrentView().contentPanel.getForm());
			
		    showCustomerViewByTitle("修改原材料采购情况");
		    getCurrentView().contentPanel.getForm().findField('ID').setValue(record.data.ID);
			getCurrentView().contentPanel.getForm().findField('TASK_NUMBER').setValue(record.data.TASK_NUMBER);
			getCurrentView().contentPanel.getForm().findField('MP_GOODS').setValue(record.data.MP_GOODS);
			getCurrentView().contentPanel.getForm().findField('MP_SUPPLIER').setValue(record.data.MP_SUPPLIER);
			getCurrentView().contentPanel.getForm().findField('MP_ISRELATE').setValue(record.data.MP_ISRELATE);
			getCurrentView().contentPanel.getForm().findField('MP_MONTH2MONEY').setValue(record.data.MP_MONTH2MONEY);
			getCurrentView().contentPanel.getForm().findField('MP_BALANCEDAYS').setValue(record.data.MP_BALANCEDAYS);
			getCurrentView().contentPanel.getForm().findField('MP_TRADEYEARS').setValue(record.data.MP_TRADEYEARS);
			getCurrentView().contentPanel.getForm().findField('MP_PAYWAY').setValue(record.data.MP_PAYWAY);
			getCurrentView().contentPanel.getForm().findField('MP_MEMO').setValue(record.data.MP_MEMO);
		}
    },{
    	text:'删除',
    	handler :function(){
    		//删除
    		var selectLength = newPanel_4.getSelectionModel().getSelections().length;
    	 	var selectRecords = newPanel_4.getSelectionModel().getSelections();
    	 	
    		if(selectLength < 1){
     			Ext.Msg.alert('提示','请选择一条数据进行操作!');
    			return false;
    		}
    		var tempIdStr = '';
    		var tempStatus = '';
    		for(var i=0; i < selectLength; i++){
    			var selectRecord = selectRecords[i];
    			//临时变量，保存要删除的ID
    			tempIdStr +=  selectRecord.data.ID;
    			tempStatus = selectRecord.data.TASK_NUMBER;
    		 		if( i != selectLength - 1){
    		   		 	tempIdStr += ',';
    					}
    		 }
    		Ext.MessageBox.confirm('提示','你确定删除吗!',function(buttonId){
    		if(buttonId.toLowerCase() == 'no'){
        		return false;
    		}
    		Ext.Msg.wait('正在提交数据，请稍等...','提示');
    			Ext.Ajax.request({
    				url : basepath + '/ocrmFInterviewMatepurchase!batchDel.json?idStr='+ tempIdStr,
    				method : 'GET',
    				success : function() {
    					Ext.Msg.hide(); 
//    					Ext.Msg.alert('提示',"操作成功!");
    					newPanelStroe_4.load({params:{ID:tempStatus}});
    				},
    				failure : function(response) {
    					var resultArray = Ext.util.JSON.decode(response.status);
    			 		if(resultArray == 403) {
    		           		Ext.Msg.alert('提示', response.responseText);
    			 		}else{
    						Ext.Msg.alert('提示', '操作失败,失败原因:' + response.responseText);
    	 				}
    				}
    			});
    	   });
    	}
      }],
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
                             			var val = translateLookupByKey("IF_FLAG",value);
                            			return val?val:"";
                            			}},
                                     {header:'月销售金额(人民币/千元)',dataIndex:'PS_MONTH2MONEY',sortable:true,width:120},
                                     {header:'结算天数(天)',dataIndex:'PS_BALANCEDAYS',sortable:true,width:120},
                                     {header:'往来年数(年)',dataIndex:'PS_TRADEYEARS',sortable:true,width:120},
                                     {header:'结算方式',dataIndex:'PS_PAYWAY',sortable:true,width:120,renderer:function(value){
                             			var val = translateLookupByKey("PS_PAYWAY",value);
                            			return val?val:"";
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
    tbar : [{
    	 text : '新增',
		 handler:function() {
			/**
			 * 保存CALLREPORT维护部分页面数据
			 */
			saveBaseValue(getCurrentView().contentPanel.getForm());
			showCustomerViewByTitle('新增产品销售状况');
		}
    },{
    	text : '修改',
		handler:function(){
			var selectLength = newPanel_5.getSelectionModel().getSelections().length;
			
			if (selectLength != 1) {
				Ext.Msg.alert('提示', '请选择一条记录！');
				return false;
			} 
		    record = newPanel_5.getSelectionModel().getSelections()[0];
		    /**
			 * 保存CALLREPORT维护部分页面数据
			 */
			saveBaseValue(getCurrentView().contentPanel.getForm());
			
		    showCustomerViewByTitle("修改产品销售状况");
		    getCurrentView().contentPanel.getForm().findField('ID').setValue(record.data.ID);
			getCurrentView().contentPanel.getForm().findField('TASK_NUMBER').setValue(record.data.TASK_NUMBER);
			getCurrentView().contentPanel.getForm().findField('PS_GOODS').setValue(record.data.PS_GOODS);
			getCurrentView().contentPanel.getForm().findField('PS_BUYER').setValue(record.data.PS_BUYER);
			getCurrentView().contentPanel.getForm().findField('PS_ISRELATE').setValue(record.data.PS_ISRELATE);
			getCurrentView().contentPanel.getForm().findField('PS_MONTH2MONEY').setValue(record.data.PS_MONTH2MONEY);
			getCurrentView().contentPanel.getForm().findField('PS_BALANCEDAYS').setValue(record.data.PS_BALANCEDAYS);
			getCurrentView().contentPanel.getForm().findField('PS_TRADEYEARS').setValue(record.data.PS_TRADEYEARS);
			getCurrentView().contentPanel.getForm().findField('PS_PAYWAY').setValue(record.data.PS_PAYWAY);
			getCurrentView().contentPanel.getForm().findField('PS_MEMO').setValue(record.data.PS_MEMO);
		}
    },{
    	text:'删除',
    	handler :function(){
    		//删除
    		var selectLength = newPanel_5.getSelectionModel().getSelections().length;
    	 	var selectRecords = newPanel_5.getSelectionModel().getSelections();
    	 	
    		if(selectLength < 1){
     			Ext.Msg.alert('提示','请选择一条数据进行操作!');
    			return false;
    		}
    		var tempIdStr = '';
    		var tempStatus = '';
    		for(var i=0; i < selectLength; i++){
    			var selectRecord = selectRecords[i];
    			//临时变量，保存要删除的ID
    			tempIdStr +=  selectRecord.data.ID;
    			tempStatus = selectRecord.data.TASK_NUMBER;
    		 		if( i != selectLength - 1){
    		   		 	tempIdStr += ',';
    					}
    		 }
    		Ext.MessageBox.confirm('提示','你确定删除吗!',function(buttonId){
    		if(buttonId.toLowerCase() == 'no'){
        		return false;
    		}
    		Ext.Msg.wait('正在提交数据，请稍等...','提示');
    			Ext.Ajax.request({
    				url : basepath + '/ocrmFInterviewProsale!batchDel.json?idStr='+ tempIdStr,
    				method : 'GET',
    				success : function() {
    					Ext.Msg.hide(); 
//    					Ext.Msg.alert('提示',"操作成功!");
    					newPanelStroe_5.load({params:{ID:tempStatus}});
    				},
    				failure : function(response) {
    					var resultArray = Ext.util.JSON.decode(response.status);
    			 		if(resultArray == 403) {
    		           		Ext.Msg.alert('提示', response.responseText);
    			 		}else{
    						Ext.Msg.alert('提示', '操作失败,失败原因:' + response.responseText);
    	 				}
    				}
    			});
    	   });
    	}
      }],
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
                                     {header:'平均存款量（人民币/千元）',dataIndex:'D_AVGDEPOSIT',sortable:true,width:120},
                                     {header:'存款类型',dataIndex:'D_DEPOSIT_TYPE',sortable:true,width:120},
                                     {header:'期限',dataIndex:'D_TERM',sortable:true,width:120}
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
			    {name:'D_AVGDEPOSIT'},
			    {name:'D_DEPOSIT_TYPE'},
			    {name:'D_TERM'}
			     ])
});	
var newPanel_6 = new Ext.grid.GridPanel({
	title : '存款往来银行表',
	autoScroll: true,
	height:200,
    tbar : [{
    	 text : '新增',
		 handler:function() {
			/**
			 * 保存CALLREPORT维护部分页面数据
			 */
			saveBaseValue(getCurrentView().contentPanel.getForm());
			showCustomerViewByTitle('新增存款往来银行表');
		}
    },{
    	text : '修改',
		handler:function(){
			var selectLength = newPanel_6.getSelectionModel().getSelections().length;
			
			if (selectLength != 1) {
				Ext.Msg.alert('提示', '请选择一条记录！');
				return false;
			} 
		    record = newPanel_6.getSelectionModel().getSelections()[0];
		    /**
			 * 保存CALLREPORT维护部分页面数据
			 */
			saveBaseValue(getCurrentView().contentPanel.getForm());
			
		    showCustomerViewByTitle("修改存款往来银行表");
		    getCurrentView().contentPanel.getForm().findField('ID').setValue(record.data.ID);
			getCurrentView().contentPanel.getForm().findField('TASK_NUMBER').setValue(record.data.TASK_NUMBER);
			getCurrentView().contentPanel.getForm().findField('D_BANKNAME').setValue(record.data.D_BANKNAME);
			getCurrentView().contentPanel.getForm().findField('D_AVGDEPOSIT').setValue(record.data.D_AVGDEPOSIT);
			getCurrentView().contentPanel.getForm().findField('D_DEPOSIT_TYPE').setValue(record.data.D_DEPOSIT_TYPE);
			getCurrentView().contentPanel.getForm().findField('D_TERM').setValue(record.data.D_TERM);
		}
    },{
    	text:'删除',
    	handler :function(){
    		//删除
    		var selectLength = newPanel_6.getSelectionModel().getSelections().length;
    	 	var selectRecords = newPanel_6.getSelectionModel().getSelections();
    	 	
    		if(selectLength < 1){
     			Ext.Msg.alert('提示','请选择一条数据进行操作!');
    			return false;
    		}
    		var tempIdStr = '';
    		var tempStatus = '';
    		for(var i=0; i < selectLength; i++){
    			var selectRecord = selectRecords[i];
    			//临时变量，保存要删除的ID
    			tempIdStr +=  selectRecord.data.ID;
    			tempStatus = selectRecord.data.TASK_NUMBER;
    		 		if( i != selectLength - 1){
    		   		 	tempIdStr += ',';
    					}
    		 }
    		Ext.MessageBox.confirm('提示','你确定删除吗!',function(buttonId){
    		if(buttonId.toLowerCase() == 'no'){
        		return false;
    		}
    		Ext.Msg.wait('正在提交数据，请稍等...','提示');
    			Ext.Ajax.request({
    				url : basepath + '/ocrmFInterviewDepositbank!batchDel.json?idStr='+ tempIdStr,
    				method : 'GET',
    				success : function() {
    					Ext.Msg.hide(); 
//    					Ext.Msg.alert('提示',"操作成功!");
    					newPanelStroe_6.load({params:{ID:tempStatus}});
    				},
    				failure : function(response) {
    					var resultArray = Ext.util.JSON.decode(response.status);
    			 		if(resultArray == 403) {
    		           		Ext.Msg.alert('提示', response.responseText);
    			 		}else{
    						Ext.Msg.alert('提示', '操作失败,失败原因:' + response.responseText);
    	 				}
    				}
    			});
    	   });
    	}
      }],
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
                                     {header:'担保率(%)',dataIndex:'L_DBRATE',sortable:true,width:120,hidden:true},
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
    tbar : [{
    	 text : '新增',
		 handler:function() {
			/**
			 * 保存CALLREPORT维护部分页面数据
			 */
			saveBaseValue(getCurrentView().contentPanel.getForm());
			showCustomerViewByTitle('新增贷款往来银行表');
		}
    },{
    	text : '修改',
		handler:function(){
			var selectLength = newPanel_7.getSelectionModel().getSelections().length;
			
			if (selectLength != 1) {
				Ext.Msg.alert('提示', '请选择一条记录！');
				return false;
			} 
		    record = newPanel_7.getSelectionModel().getSelections()[0];
		    /**
			 * 保存CALLREPORT维护部分页面数据
			 */
			saveBaseValue(getCurrentView().contentPanel.getForm());
			
		    showCustomerViewByTitle("修改贷款往来银行表");
		    getCurrentView().contentPanel.getForm().findField('ID').setValue(record.data.ID);
			getCurrentView().contentPanel.getForm().findField('TASK_NUMBER').setValue(record.data.TASK_NUMBER);
			getCurrentView().contentPanel.getForm().findField('L_BANKNAME').setValue(record.data.L_BANKNAME);
			getCurrentView().contentPanel.getForm().findField('L_LIMITTYPE').setValue(record.data.L_LIMITTYPE);
			getCurrentView().contentPanel.getForm().findField('L_LIMITMONEY').setValue(record.data.L_LIMITMONEY);
			getCurrentView().contentPanel.getForm().findField('L_BALANCE').setValue(record.data.L_BALANCE);
			getCurrentView().contentPanel.getForm().findField('L_RATE').setValue(record.data.L_RATE);
//			getCurrentView().contentPanel.getForm().findField('L_DBRATE').setValue(record.data.L_DBRATE);
			getCurrentView().contentPanel.getForm().findField('L_COLLATERAL').setValue(record.data.L_COLLATERAL);
			getCurrentView().contentPanel.getForm().findField('L_MEMO').setValue(record.data.L_MEMO);
		}
    },{
    	text:'删除',
    	handler :function(){
    		//删除
    		var selectLength = newPanel_7.getSelectionModel().getSelections().length;
    	 	var selectRecords = newPanel_7.getSelectionModel().getSelections();
    	 	
    		if(selectLength < 1){
     			Ext.Msg.alert('提示','请选择一条数据进行操作!');
    			return false;
    		}
    		var tempIdStr = '';
    		var tempStatus = '';
    		for(var i=0; i < selectLength; i++){
    			var selectRecord = selectRecords[i];
    			//临时变量，保存要删除的ID
    			tempIdStr +=  selectRecord.data.ID;
    			tempStatus = selectRecord.data.TASK_NUMBER;
    		 		if( i != selectLength - 1){
    		   		 	tempIdStr += ',';
    					}
    		 }
    		Ext.MessageBox.confirm('提示','你确定删除吗!',function(buttonId){
    		if(buttonId.toLowerCase() == 'no'){
        		return false;
    		}
    		Ext.Msg.wait('正在提交数据，请稍等...','提示');
    			Ext.Ajax.request({
    				url : basepath + '/ocrmFInterviewLoanbank!batchDel.json?idStr='+ tempIdStr,
    				method : 'GET',
    				success : function() {
    					Ext.Msg.hide(); 
//    					Ext.Msg.alert('提示',"操作成功!");
    					newPanelStroe_7.load({params:{ID:tempStatus}});
    				},
    				failure : function(response) {
    					var resultArray = Ext.util.JSON.decode(response.status);
    			 		if(resultArray == 403) {
    		           		Ext.Msg.alert('提示', response.responseText);
    			 		}else{
    						Ext.Msg.alert('提示', '操作失败,失败原因:' + response.responseText);
    	 				}
    				}
    			});
    	   });
    	}
      }],
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
                                     {header:'预计平均存款量（人民币/千元）',dataIndex:'DP_AVGDEPOSIT',sortable:true,width:120}
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
    tbar : [{
    	 text : '新增',
		 handler:function() {
			/**
			 * 保存CALLREPORT维护部分页面数据
			 */
			saveBaseValue(getCurrentView().contentPanel.getForm());
			showCustomerViewByTitle('新增拟承做存款产品');
		}
    },{
    	text : '修改',
		handler:function(){
			var selectLength = newPanel_8.getSelectionModel().getSelections().length;
			
			if (selectLength != 1) {
				Ext.Msg.alert('提示', '请选择一条记录！');
				return false;
			} 
		    record = newPanel_8.getSelectionModel().getSelections()[0];
		    /**
			 * 保存CALLREPORT维护部分页面数据
			 */
			saveBaseValue(getCurrentView().contentPanel.getForm());
			
		    showCustomerViewByTitle("修改拟承做存款产品");
		    getCurrentView().contentPanel.getForm().findField('ID').setValue(record.data.ID);
			getCurrentView().contentPanel.getForm().findField('TASK_NUMBER').setValue(record.data.TASK_NUMBER);
			getCurrentView().contentPanel.getForm().findField('DP_NAME').setValue(record.data.DP_NAME);
			getCurrentView().contentPanel.getForm().findField('DP_AVGDEPOSIT').setValue(record.data.DP_AVGDEPOSIT);
		}
    },{
    	text:'删除',
    	handler :function(){
    		//删除
    		var selectLength = newPanel_8.getSelectionModel().getSelections().length;
    	 	var selectRecords = newPanel_8.getSelectionModel().getSelections();
    	 	
    		if(selectLength < 1){
     			Ext.Msg.alert('提示','请选择一条数据进行操作!');
    			return false;
    		}
    		var tempIdStr = '';
    		var tempStatus = '';
    		for(var i=0; i < selectLength; i++){
    			var selectRecord = selectRecords[i];
    			//临时变量，保存要删除的ID
    			tempIdStr +=  selectRecord.data.ID;
    			tempStatus = selectRecord.data.TASK_NUMBER;
    		 		if( i != selectLength - 1){
    		   		 	tempIdStr += ',';
    					}
    		 }
    		Ext.MessageBox.confirm('提示','你确定删除吗!',function(buttonId){
    		if(buttonId.toLowerCase() == 'no'){
        		return false;
    		}
    		Ext.Msg.wait('正在提交数据，请稍等...','提示');
    			Ext.Ajax.request({
    				url : basepath + '/ocrmFInterviewDepositpro!batchDel.json?idStr='+ tempIdStr,
    				method : 'GET',
    				success : function() {
    					Ext.Msg.hide(); 
//    					Ext.Msg.alert('提示',"操作成功!");
    					newPanelStroe_8.load({params:{ID:tempStatus}});
    				},
    				failure : function(response) {
    					var resultArray = Ext.util.JSON.decode(response.status);
    			 		if(resultArray == 403) {
    		           		Ext.Msg.alert('提示', response.responseText);
    			 		}else{
    						Ext.Msg.alert('提示', '操作失败,失败原因:' + response.responseText);
    	 				}
    				}
    			});
    	   });
    	}
      }],
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
    tbar : [{
    	 text : '新增',
		 handler:function() {
		    /**
			 * 保存CALLREPORT维护部分页面数据
			 */
			saveBaseValue(getCurrentView().contentPanel.getForm());
			showCustomerViewByTitle('新增拟申请外汇产品额度');
		}
    },{
    	text : '修改',
		handler:function(){
			var selectLength = newPanel_9.getSelectionModel().getSelections().length;
			
			if (selectLength != 1) {
				Ext.Msg.alert('提示', '请选择一条记录！');
				return false;
			} 
		    record = newPanel_9.getSelectionModel().getSelections()[0];
		    /**
			 * 保存CALLREPORT维护部分页面数据
			 */
			saveBaseValue(getCurrentView().contentPanel.getForm());
			
		    showCustomerViewByTitle("修改拟申请外汇产品额度");
		    getCurrentView().contentPanel.getForm().findField('ID').setValue(record.data.ID);
			getCurrentView().contentPanel.getForm().findField('TASK_NUMBER').setValue(record.data.TASK_NUMBER);
			getCurrentView().contentPanel.getForm().findField('FL_NAME').setValue(record.data.FL_NAME);
			getCurrentView().contentPanel.getForm().findField('FL_DEAL2MONTH').setValue(record.data.FL_DEAL2MONTH);
			getCurrentView().contentPanel.getForm().findField('FL_LIMITMONEY').setValue(record.data.FL_LIMITMONEY);
		}
    },{
    	text:'删除',
    	handler :function(){
    		//删除
    		var selectLength = newPanel_9.getSelectionModel().getSelections().length;
    	 	var selectRecords = newPanel_9.getSelectionModel().getSelections();
    	 	
    		if(selectLength < 1){
     			Ext.Msg.alert('提示','请选择一条数据进行操作!');
    			return false;
    		}
    		var tempIdStr = '';
    		var tempStatus = '';
    		for(var i=0; i < selectLength; i++){
    			var selectRecord = selectRecords[i];
    			//临时变量，保存要删除的ID
    			tempIdStr +=  selectRecord.data.ID;
    			tempStatus = selectRecord.data.TASK_NUMBER;
    		 		if( i != selectLength - 1){
    		   		 	tempIdStr += ',';
    					}
    		 }
    		Ext.MessageBox.confirm('提示','你确定删除吗!',function(buttonId){
    		if(buttonId.toLowerCase() == 'no'){
        		return false;
    		}
    		Ext.Msg.wait('正在提交数据，请稍等...','提示');
    			Ext.Ajax.request({
    				url : basepath + '/ocrmFInterviewForexlimit!batchDel.json?idStr='+ tempIdStr,
    				method : 'GET',
    				success : function() {
    					Ext.Msg.hide(); 
//    					Ext.Msg.alert('提示',"操作成功!");
    					newPanelStroe_9.load({params:{ID:tempStatus}});
    				},
    				failure : function(response) {
    					var resultArray = Ext.util.JSON.decode(response.status);
    			 		if(resultArray == 403) {
    		           		Ext.Msg.alert('提示', response.responseText);
    			 		}else{
    						Ext.Msg.alert('提示', '操作失败,失败原因:' + response.responseText);
    	 				}
    				}
    			});
    	   });
    	}
      }],
	store : newPanelStroe_9,
	frame : true,
	sm:newSm_9,
	cm : newCm_9,
	loadMask : {
		msg : '正在加载表格数据,请稍等...'
	}
});
//=================拟申请外汇产品额度==end=====================================
//=================拟申请授信产品旧户====begin=====================================
//var newSm_10 = new Ext.grid.CheckboxSelectionModel();
//var editRrownum = new Ext.grid.RowNumberer({
//	  header : 'No.',
//	  width : 28
//});
//var newCm_10=  new Ext.grid.ColumnModel([editRrownum,newSm_10,	
//                                     {header:'ID', dataIndex : 'ID',sortable : true,width : 120,hidden:true},
//                                     {header:'TASK_NUMBER',dataIndex:'TASK_NUMBER',sortable:true,width:120,hidden:true},
//                        			 {header:'产品类型',dataIndex:'CP_PRODUCT_P',sortable:true,width:120,renderer:function(value){
//                        				var val = translateLookupByKey("CP_PRODUCT_P",value);
//                        				return val?val:"";
//                        			 }},
//                                     {header:'授信产品',dataIndex:'CP_PRODUCT',sortable:true,width:120,renderer:function(value){
//                             			var val = translateLookupByKey("CP_PRODUCT",value);
//                            			return val?val:"";
//                            			}},
//                        			 {header:'用途',dataIndex:'CP_USE',sortable:true,width:120,renderer:function(value){
//                        				var val = translateLookupByKey("CP_USE",value);
//                        				return val?val:"";
//                        			 }},
//                                     {header:'币种',dataIndex:'CP_CURRENCY',sortable:true,width:120,renderer:function(value){
//                             			var val = translateLookupByKey("CURRENCY",value);
//                            			return val?val:"";
//                            			}},
//                                     {header:'额度金额(人民币/千元)',dataIndex:'CP_LIMITMONEY',sortable:true,width:120},
//                                     {header:'担保品',dataIndex:'CP_COLLATERAL',sortable:true,width:120},
//                                     {header:'担保比率(%)',dataIndex:'CP_DBRATE',sortable:true,width:120},
//                                     {header:'备注',dataIndex:'CP_MEMO',sortable:true,width:120}
//        	                                     ]); 
//var newPanelStroe_10 = new Ext.data.Store({
//	restful : true,
//	proxy : new Ext.data.HttpProxy(
//			{
//				url : basepath + '/ocrmFInterviewCreditpro.json' 
//			}),
//			reader : new Ext.data.JsonReader( {
//				root : 'json.data'
//			}, [{name:'ID'},
//			    {name:'TASK_NUMBER'},
//			    {name:'CP_USE'},
//			    {name:'CP_PRODUCT'},
//			    {name:'CP_CURRENCY'},
//			    {name:'CP_LIMITMONEY'},
//			    {name:'CP_MEMO'},
//			    {name:'CP_PRODUCT_P'},
//			    {name:'CP_COLLATERAL'},
//			    {name:'CP_DBRATE'}
//			     ])
//});	
//var newPanel_10 = new Ext.grid.GridPanel({
//	title : '拟申请授信产品',
//	autoScroll: true,
//	height:200,
//    tbar : [{
//    	 text : '新增',
//		 handler:function() {
//		    /**
//			 * 保存CALLREPORT维护部分页面数据
//			 */
//			saveBaseValue(getCurrentView().contentPanel.getForm());
//			showCustomerViewByTitle('新增拟申请授信产品');
//		}
//    },{
//    	text : '修改',
//		handler:function(){
//			var selectLength = newPanel_10.getSelectionModel().getSelections().length;
//			
//			if (selectLength != 1) {
//				Ext.Msg.alert('提示', '请选择一条记录！');
//				return false;
//			} 
//		    record = newPanel_10.getSelectionModel().getSelections()[0];
//		    /**
//			 * 保存CALLREPORT维护部分页面数据
//			 */
//			saveBaseValue(getCurrentView().contentPanel.getForm());
//			
//		    showCustomerViewByTitle("修改拟申请授信产品");
//		    getCurrentView().contentPanel.getForm().findField('ID').setValue(record.data.ID);
//			getCurrentView().contentPanel.getForm().findField('TASK_NUMBER').setValue(record.data.TASK_NUMBER);
//			getCurrentView().contentPanel.getForm().findField('CP_USE').setValue(record.data.CP_USE);
//			getCurrentView().contentPanel.getForm().findField('CP_PRODUCT').setValue(record.data.CP_PRODUCT);
//			getCurrentView().contentPanel.getForm().findField('CP_CURRENCY').setValue(record.data.CP_CURRENCY);
//			getCurrentView().contentPanel.getForm().findField('CP_LIMITMONEY').setValue(record.data.CP_LIMITMONEY);
//			getCurrentView().contentPanel.getForm().findField('CP_MEMO').setValue(record.data.CP_MEMO);
//			getCurrentView().contentPanel.getForm().findField('CP_PRODUCT_P').setValue(record.data.CP_PRODUCT_P);
//			getCurrentView().contentPanel.getForm().findField('CP_COLLATERAL').setValue(record.data.CP_COLLATERAL);
//			getCurrentView().contentPanel.getForm().findField('CP_DBRATE').setValue(record.data.CP_DBRATE);
//			
//			var v = record.data.CP_PRODUCT_P;
//		    if(v=='1'){
//				getCurrentView().contentPanel.getForm().findField('CP_PRODUCT').bindStore(findLookupByType('CP_PRODUCT_1'));
//			}else if(v == '2'){
//				getCurrentView().contentPanel.getForm().findField('CP_PRODUCT').bindStore(findLookupByType('CP_PRODUCT_2'));
//			}else if(v == '3'){
//				getCurrentView().contentPanel.getForm().findField('CP_PRODUCT').bindStore(findLookupByType('CP_PRODUCT_3'));
//			}else if(v == '4'){
//				getCurrentView().contentPanel.getForm().findField('CP_PRODUCT').bindStore(findLookupByType('CP_PRODUCT_4'));
//			}else if(v == '5'){
//				getCurrentView().contentPanel.getForm().findField('CP_PRODUCT').bindStore(findLookupByType('CP_PRODUCT_5'));
//			}else if(v == '6'){
//				getCurrentView().contentPanel.getForm().findField('CP_PRODUCT').bindStore(findLookupByType('CP_PRODUCT_6'));
//			}else{
//				getCurrentView().contentPanel.getForm().findField('CP_PRODUCT').bindStore(findLookupByType('CP_PRODUCT_7'));
//			}
//		}
//    },{
//    	text:'删除',
//    	handler :function(){
//    		//删除
//    		var selectLength = newPanel_10.getSelectionModel().getSelections().length;
//    	 	var selectRecords = newPanel_10.getSelectionModel().getSelections();
//    	 	
//    		if(selectLength < 1){
//     			Ext.Msg.alert('提示','请选择一条数据进行操作!');
//    			return false;
//    		}
//    		var tempIdStr = '';
//    		var tempStatus = '';
//    		for(var i=0; i < selectLength; i++){
//    			var selectRecord = selectRecords[i];
//    			//临时变量，保存要删除的ID
//    			tempIdStr +=  selectRecord.data.ID;
//    			tempStatus = selectRecord.data.TASK_NUMBER;
//    		 		if( i != selectLength - 1){
//    		   		 	tempIdStr += ',';
//    					}
//    		 }
//    		Ext.MessageBox.confirm('提示','你确定删除吗!',function(buttonId){
//    		if(buttonId.toLowerCase() == 'no'){
//        		return false;
//    		}
//    		Ext.Msg.wait('正在提交数据，请稍等...','提示');
//    			Ext.Ajax.request({
//    				url : basepath + '/ocrmFInterviewCreditpro!batchDel.json?idStr='+ tempIdStr,
//    				method : 'GET',
//    				success : function() {
//    					Ext.Msg.hide(); 
////    					Ext.Msg.alert('提示',"操作成功!");
//    					newPanelStroe_10.load({params:{ID:tempStatus}});
//    				},
//    				failure : function(response) {
//    					var resultArray = Ext.util.JSON.decode(response.status);
//    			 		if(resultArray == 403) {
//    		           		Ext.Msg.alert('提示', response.responseText);
//    			 		}else{
//    						Ext.Msg.alert('提示', '操作失败,失败原因:' + response.responseText);
//    	 				}
//    				}
//    			});
//    	   });
//    	}
//      }],
//	store : newPanelStroe_10,
//	frame : true,
//	sm:newSm_10,
//	cm : newCm_10,
//	loadMask : {
//		msg : '正在加载表格数据,请稍等...'
//	}
//});
//=================拟申请授信产品旧户====end=======================================


//=================拟申请授信产品新户====begin=====================================
var newSm_11 = new Ext.grid.CheckboxSelectionModel();
var editRrownum = new Ext.grid.RowNumberer({
	  header : 'No.',
	  width : 28
});
var newCm_11 =  new Ext.grid.ColumnModel([editRrownum,newSm_11,	
                                     {header:'ID', dataIndex : 'ID',sortable : true,width : 120,hidden:true},
                                     {header:'TASK_NUMBER',dataIndex:'TASK_NUMBER',sortable:true,width:120,hidden:true},
                                     {header:'贷款用途',dataIndex:'CP_USE',sortable:true,width:120,renderer:function(value){
                         				var val = translateLookupByKey("CP_USE",value);
                         				return val?val:"";
                         			 }},
                                     {header:'授信产品',dataIndex:'CP_PRODUCT',sortable:true,width:120,renderer:function(value){
                             			var val = translateLookupByKey("CP_PRODUCT",value);
                            			return val?val:"";
                            			}},
                                     {header:'币种',dataIndex:'CP_CURRENCY',sortable:true,width:120,renderer:function(value){
                             			var val = translateLookupByKey("CURRENCY",value);
                            			return val?val:"";
                            			}},
                                     {header:'额度金额(人民币/千元)',dataIndex:'CP_LIMITMONEY',sortable:true,width:120},
                                     {header:'贷款期限(月)',dataIndex:'CREDIT_PERIOD',sortable:true,width:120},
                                     {header:'还款方式',dataIndex:'REPAYMENT_METHOD',sortable:true,width:120,renderer:function(value){
                                    	 var val = translateLookupByKey("DM0004",value);
                                    	 return val?val:"";
                                     }}
        	                                     ]); 
var newPanelStroe_11 = new Ext.data.Store({
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
			    {name:'CREDIT_PERIOD'},
			    {name:'REPAYMENT_METHOD'}
			     ])
});	
var newPanel_11 = new Ext.grid.GridPanel({
	title : '拟申请授信产品',
	autoScroll: true,
	height:200,
    tbar : [{
    	 text : '新增',
		 handler:function() {
		    /**
			 * 保存CALLREPORT维护部分页面数据
			 */
			saveBaseValue(getCurrentView().contentPanel.getForm());
			showCustomerViewByTitle("新增拟申请授信产品");
		}
    },{
    	text : '修改',
		handler:function(){
			var selectLength = newPanel_11.getSelectionModel().getSelections().length;
			
			if (selectLength != 1) {
				Ext.Msg.alert('提示', '请选择一条记录！');
				return false;
			} 
		    record = newPanel_11.getSelectionModel().getSelections()[0];
		    /**
			 * 保存CALLREPORT维护部分页面数据
			 */
			saveBaseValue(getCurrentView().contentPanel.getForm());
			showCustomerViewByTitle("修改拟申请授信产品");
			
		    getCurrentView().contentPanel.getForm().findField('ID').setValue(record.data.ID);
			getCurrentView().contentPanel.getForm().findField('TASK_NUMBER').setValue(record.data.TASK_NUMBER);
			getCurrentView().contentPanel.getForm().findField('CP_USE').setValue(record.data.CP_USE);
			getCurrentView().contentPanel.getForm().findField('CP_PRODUCT').setValue(record.data.CP_PRODUCT);
			getCurrentView().contentPanel.getForm().findField('CP_CURRENCY').setValue(record.data.CP_CURRENCY);
			getCurrentView().contentPanel.getForm().findField('CP_LIMITMONEY').setValue(record.data.CP_LIMITMONEY);
			getCurrentView().contentPanel.getForm().findField('CP_MEMO').setValue(record.data.CP_MEMO);
			getCurrentView().contentPanel.getForm().findField('CREDIT_PERIOD').setValue(record.data.CREDIT_PERIOD);
			getCurrentView().contentPanel.getForm().findField('REPAYMENT_METHOD').setValue(record.data.REPAYMENT_METHOD);
			getCurrentView().contentPanel.getForm().findField('CP_DBRATE').setValue(record.data.CP_DBRATE);
			
		}
    },{
    	text:'删除',
    	handler :function(){
    		//删除
    		var selectLength = newPanel_11.getSelectionModel().getSelections().length;
    	 	var selectRecords = newPanel_11.getSelectionModel().getSelections();
    	 	
    		if(selectLength < 1){
     			Ext.Msg.alert('提示','请选择一条数据进行操作!');
    			return false;
    		}
    		var tempIdStr = '';
    		var tempStatus = '';
    		for(var i=0; i < selectLength; i++){
    			var selectRecord = selectRecords[i];
    			//临时变量，保存要删除的ID
    			tempIdStr +=  selectRecord.data.ID;
    			tempStatus = selectRecord.data.TASK_NUMBER;
    		 		if( i != selectLength - 1){
    		   		 	tempIdStr += ',';
    					}
    		 }
    		Ext.MessageBox.confirm('提示','你确定删除吗!',function(buttonId){
    		if(buttonId.toLowerCase() == 'no'){
        		return false;
    		}
    		Ext.Msg.wait('正在提交数据，请稍等...','提示');
    			Ext.Ajax.request({
    				url : basepath + '/ocrmFInterviewCreditpro!batchDel.json?idStr='+ tempIdStr,
    				method : 'GET',
    				success : function() {
    					Ext.Msg.hide(); 
//    					Ext.Msg.alert('提示',"操作成功!");
    					newPanelStroe_11.load({params:{ID:tempStatus}});
    				},
    				failure : function(response) {
    					var resultArray = Ext.util.JSON.decode(response.status);
    			 		if(resultArray == 403) {
    		           		Ext.Msg.alert('提示', response.responseText);
    			 		}else{
    						Ext.Msg.alert('提示', '操作失败,失败原因:' + response.responseText);
    	 				}
    				}
    			});
    	   });
    	}
      }],
	store : newPanelStroe_11,
	frame : true,
	sm:newSm_11,
	cm : newCm_11,
	loadMask : {
		msg : '正在加载表格数据,请稍等...'
	}
});

//=================拟申请授信产品新户====end=======================================


//=================担保品信息============begin========================================
var newSm_12 = new Ext.grid.CheckboxSelectionModel();
var editRrownum = new Ext.grid.RowNumberer({
	  header : 'No.',
	  width : 28
});
var newCm_12 =  new Ext.grid.ColumnModel([editRrownum,newSm_12,	
                                         {header: 'ID', dataIndex: 'ID',sortable: true,width: 120,hidden: true},
                                         {header: 'TASK_NUMBER', dataIndex: 'TASK_NUMBER',sortable: true,width: 120,hidden: true},
                                         {header: '担保品类型',dataIndex: 'COLLATERAL_TYPE',width: 120,renderer:function(value){
                                         	var val = translateLookupByKey("DM0019",value);
                                         	return val?val:"";
                                         }},
                                         {header: '担保品估值(人民币/千元)',dataIndex: 'ESTIMATE_VALUE',sortable: 'true',width: 120},
                                         {header: '担保品净值(人民币/千元)',dataIndex: 'NET_VALUE',sortable: 'true',width: 120},
                                         {header:'担保品所在地',dataIndex: 'COLLATERAL_ADDR',sortable:true,width:120}
        	                                     ]); 
var newPanelStroe_12 = new Ext.data.Store({
	restful : true,
	proxy : new Ext.data.HttpProxy(
			{
				url : basepath + '/ocrmFInterviewCollateral.json' 
			}),
			reader : new Ext.data.JsonReader( {
				root : 'json.data'
			}, [{name:'ID'},
			    {name:'TASK_NUMBER'},
			    {name:'COLLATERAL_TYPE'},
			    {name:'ESTIMATE_VALUE'},
			    {name:'COLLATERAL_ADDR'},
			    {name:'NET_VALUE'}
			     ])
});	
var newPanel_12 = new Ext.grid.GridPanel({
	title : '担保品信息',
	autoScroll: true,
	height:200,
    tbar : [{
    	 text : '新增',
		 handler:function() {
		    /**
			 * 保存CALLREPORT维护部分页面数据
			 */
			saveBaseValue(getCurrentView().contentPanel.getForm());
			showCustomerViewByTitle('新增担保品信息');
		}
    },{
    	text : '修改',
		handler:function(){
			var selectLength = newPanel_12.getSelectionModel().getSelections().length;
			
			if (selectLength != 1) {
				Ext.Msg.alert('提示', '请选择一条记录！');
				return false;
			} 
		    record = newPanel_12.getSelectionModel().getSelections()[0];
		    /**
			 * 保存CALLREPORT维护部分页面数据
			 */
			saveBaseValue(getCurrentView().contentPanel.getForm());
		    showCustomerViewByTitle('修改担保品信息');
		    
		    getCurrentView().contentPanel.getForm().findField('ID').setValue(record.data.ID);
			getCurrentView().contentPanel.getForm().findField('TASK_NUMBER').setValue(record.data.TASK_NUMBER);
			getCurrentView().contentPanel.getForm().findField('COLLATERAL_TYPE').setValue(record.data.COLLATERAL_TYPE);
			getCurrentView().contentPanel.getForm().findField('ESTIMATE_VALUE').setValue(record.data.ESTIMATE_VALUE);
			getCurrentView().contentPanel.getForm().findField('NET_VALUE').setValue(record.data.NET_VALUE);
			getCurrentView().contentPanel.getForm().findField('COLLATERAL_ADDR').setValue(record.data.COLLATERAL_ADDR);
		}
    },{
    	text:'删除',
    	handler :function(){
    		//删除
    		var selectLength = newPanel_12.getSelectionModel().getSelections().length;
    	 	var selectRecords = newPanel_12.getSelectionModel().getSelections();
    	 	
    		if(selectLength < 1){
     			Ext.Msg.alert('提示','请选择一条数据进行操作!');
    			return false;
    		}
    		var tempIdStr = '';
    		var tempStatus = '';
    		for(var i=0; i < selectLength; i++){
    			var selectRecord = selectRecords[i];
    			//临时变量，保存要删除的ID
    			tempIdStr +=  selectRecord.data.ID;
    			tempStatus = selectRecord.data.TASK_NUMBER;
    		 		if( i != selectLength - 1){
    		   		 	tempIdStr += ',';
    					}
    		 }
    		Ext.MessageBox.confirm('提示','你确定删除吗!',function(buttonId){
    		if(buttonId.toLowerCase() == 'no'){
        		return false;
    		}
    		Ext.Msg.wait('正在提交数据，请稍等...','提示');
    			Ext.Ajax.request({
    				url : basepath + '/ocrmFInterviewCollateral!batchDel.json?idStr='+ tempIdStr,
    				method : 'GET',
    				success : function() {
    					Ext.Msg.hide(); 
    					newPanelStroe_12.load({params:{ID:tempStatus}});
    				},
    				failure : function(response) {
    					var resultArray = Ext.util.JSON.decode(response.status);
    			 		if(resultArray == 403) {
    		           		Ext.Msg.alert('提示', response.responseText);
    			 		}else{
    						Ext.Msg.alert('提示', '操作失败,失败原因:' + response.responseText);
    	 				}
    				}
    			});
    	   });
    	}
      }],
	store : newPanelStroe_12,
	frame : true,
	sm:newSm_12,
	cm : newCm_12,
	loadMask : {
		msg : '正在加载表格数据,请稍等...'
	}
});

//=================担保品信息============end========================================
//===================公司相关人员信息====begin======================================
var newSm_13 = new Ext.grid.CheckboxSelectionModel();
var editRrownum = new Ext.grid.RowNumberer({
	  header : 'No.',
	  width : 28
});

var newCm_13=  new Ext.grid.ColumnModel([editRrownum,newSm_13,	
                                   {header:'ID', dataIndex : 'ID',sortable : true,width : 120,hidden:true},
                                   {header:'TASK_NUMBER',dataIndex:'TASK_NUMBER',sortable:true,width:120,hidden:true},
                                   {header:'人员角色',dataIndex:'PER_ROLE',sortable:true,width:120,renderer:function(value){
                        				var val = translateLookupByKey("PER_ROLE",value);
                        				return val?val:"";
                        			 }},
                                   {header:'人员姓名',dataIndex:'PER_NAME',sortable:true,width:120},
                                   {header:'人员电话',dataIndex:'PER_TELPHONE',sortable:true,width:120,vtype:'telephone'},
                                   {header:'股东持股出资比例',dataIndex:'SHOLDER_CAPITAL',sortable:true,width:120},
                                   {header:'出资金额',dataIndex:'AMOUNT_CAPITAL',sortable:true,width:120},
                                   {header:'说明',dataIndex:'PER_EXPLAIN',sortable:true,width:200}
      	                                     ]); 
var newPanelStroe_13 = new Ext.data.Store({
	restful : true,
	proxy : new Ext.data.HttpProxy(
			{
				url : basepath + '/ocrmFInterviewPerson.json' 
			}),
			reader : new Ext.data.JsonReader( {
				root : 'json.data'
			}, [{name:'ID'},
			    {name:'TASK_NUMBER'},
			    {name:'PER_ROLE'},
			    {name:'PER_NAME'},
			    {name:'PER_TELPHONE'},
			    {name:'SHOLDER_CAPITAL'},
			    {name:'AMOUNT_CAPITAL'},
			    {name:'PER_EXPLAIN'}
			     ])
});	

var newPanel_13 = new Ext.grid.GridPanel({
	title : '公司相关人员信息',
	autoScroll: true,
	height:200,
  tbar : [{
  	 text : '新增',
		 handler:function() {
			/**
			 * 保存CALLREPORT维护部分页面数据
			 */
			saveBaseValue(getCurrentView().contentPanel.getForm());
			showCustomerViewByTitle('新增公司相关人员信息');
		}
  },{
  	text : '修改',
		handler:function(){
			var selectLength = newPanel_13.getSelectionModel().getSelections().length;
			
			if (selectLength != 1) {
				Ext.Msg.alert('提示', '请选择一条记录！');
				return false;
			} 
		    record = newPanel_13.getSelectionModel().getSelections()[0];
		    /**
			 * 保存CALLREPORT维护部分页面数据
			 */
			saveBaseValue(getCurrentView().contentPanel.getForm());
			
		    showCustomerViewByTitle("修改公司相关人员信息");
		    getCurrentView().contentPanel.getForm().findField('ID').setValue(record.data.ID);
		    getCurrentView().contentPanel.getForm().findField('TASK_NUMBER').setValue(record.data.TASK_NUMBER);
			getCurrentView().contentPanel.getForm().findField('PER_ROLE').setValue(record.data.PER_ROLE);
			getCurrentView().contentPanel.getForm().findField('PER_NAME').setValue(record.data.PER_NAME);
			getCurrentView().contentPanel.getForm().findField('PER_TELPHONE').setValue(record.data.PER_TELPHONE);
			getCurrentView().contentPanel.getForm().findField('SHOLDER_CAPITAL').setValue(record.data.SHOLDER_CAPITAL);
			getCurrentView().contentPanel.getForm().findField('AMOUNT_CAPITAL').setValue(record.data.AMOUNT_CAPITAL);
			getCurrentView().contentPanel.getForm().findField('PER_EXPLAIN').setValue(record.data.PER_EXPLAIN);
		}
  },{
  	text:'删除',
  	handler :function(){
  		//删除
  		var selectLength = newPanel_13.getSelectionModel().getSelections().length;
  	 	var selectRecords = newPanel_13.getSelectionModel().getSelections();
  	 	
  		if(selectLength < 1){
   			Ext.Msg.alert('提示','请选择一条数据进行操作!');
  			return false;
  		}
  		var tempIdStr = '';
  		var tempStatus = '';
  		for(var i=0; i < selectLength; i++){
  			var selectRecord = selectRecords[i];
  			//临时变量，保存要删除的ID
  			tempIdStr +=  selectRecord.data.ID;
  			tempStatus = selectRecord.data.TASK_NUMBER;
		 		if( i != selectLength - 1){
		   		 	tempIdStr += ',';
					}
  		 }
  		Ext.MessageBox.confirm('提示','你确定删除吗!',function(buttonId){
  		if(buttonId.toLowerCase() == 'no'){
      		return false;
  		}
  		Ext.Msg.wait('正在提交数据，请稍等...','提示');
  			Ext.Ajax.request({
  				url : basepath + '/ocrmFInterviewPerson!batchDel.json?idStr='+ tempIdStr,
  				method : 'GET',
  				success : function() {
  					Ext.Msg.hide(); 
//  					Ext.Msg.alert('提示',"操作成功!");
  					newPanelStroe_13.load({params:{ID:tempStatus}});
  				},
  				failure : function(response) {
  					var resultArray = Ext.util.JSON.decode(response.status);
  			 		if(resultArray == 403) {
  		           		Ext.Msg.alert('提示', response.responseText);
  			 		}else{
  						Ext.Msg.alert('提示', '操作失败,失败原因:' + response.responseText);
  	 				}
  				}
  			});
  	   });
  	}
    }],
	store : newPanelStroe_13,
	frame : true,
	sm:newSm_13,
	cm : newCm_13,
	loadMask : {
		msg : '正在加载表格数据,请稍等...'
	}
});
//=================公司相关人员信息====end=======================================


var beforeviewshow = function(view){
//	if(view._defaultTitle == '新增股东及持股比例' || view._defaultTitle == '修改股东及持股比例'){
//		if(newPanelStroe_1.getCount() != 0){
//			bl.PEC = 0;
//			for(var i=0;i<newPanelStroe_1.getCount();i++){
//				var data = newPanelStroe_1.getAt(i).data;
//				bl.PEC  = parseFloat(bl.PEC)+parseFloat(data.M_RATIO);
//			}
//		}
//	}
	if(view._defaultTitle == '主管审批意见'){
		if (getSelectedData() == false) {
			Ext.Msg.alert('提示', '请选择一条数据！');
			return false;
		}else{
			view.setParameters({
				taskNumber : getSelectedData().data.TASK_NUMBER
			});
		}
	}
	if(view._defaultTitle == 'CALLREPORT维护'){
		if(getSelectedData() == false){
			Ext.Msg.alert('提示','请选择一条数据');
			return false;
		}
		if(getSelectedData().data.REVIEW_STATE == '2' || getSelectedData().data.REVIEW_STATE == '02'){
			Ext.Msg.alert('提示','拜访CALLREPORT维护信息在审核中...');
			return false;
		}
		//主要股东及持股比例
//		newPanelStroe_1.load({params:{ID:getSelectedData().data.TASK_NUMBER}});
		//主要固定资产
		newPanelStroe_2.load({params:{ID:getSelectedData().data.TASK_NUMBER}});
		//营收获利情况
		newPanelStroe_3.load({params:{ID:getSelectedData().data.TASK_NUMBER}});
		//原材料采购情况
		newPanelStroe_4.load({params:{ID:getSelectedData().data.TASK_NUMBER}});
		//产品销售状况
		newPanelStroe_5.load({params:{ID:getSelectedData().data.TASK_NUMBER}});
		//存款往来银行表
		newPanelStroe_6.load({params:{ID:getSelectedData().data.TASK_NUMBER}});
		//贷款往来银行表
		newPanelStroe_7.load({params:{ID:getSelectedData().data.TASK_NUMBER}});
		//拟承做存款产品
		newPanelStroe_8.load({params:{ID:getSelectedData().data.TASK_NUMBER}});
		//拟申请外汇产品额度
		newPanelStroe_9.load({params:{ID:getSelectedData().data.TASK_NUMBER}});
		//拟申请授信产品_旧户
//		newPanelStroe_10.load({params:{ID:getSelectedData().data.TASK_NUMBER}});
		//拟申请授信产品_新户
		newPanelStroe_11.load({params:{ID:getSelectedData().data.TASK_NUMBER}});
		//担保品信息
		newPanelStroe_12.load({params:{ID:getSelectedData().data.TASK_NUMBER}});
		//公司相关人员信息
		newPanelStroe_13.load({params:{ID:getSelectedData().data.TASK_NUMBER}});
		
		//按钮权限
		if(getSelectedData().data.REVIEW_STATE == '0'){
			Ext.getCmp("fk_toSave").hide();
		}else{
			Ext.getCmp("fk_toSave").show();
		}
		var taskType = getSelectedData().data.TASK_TYPE;
        var visitType = getSelectedData().data.VISIT_TYPE;
        if(visitType==null || visitType == ''){
        	visitType = custData.VISIT_TYPE;
        }
        if(taskType == 0 && visitType != 2){
        	view.contentPanel.getForm().findField('VISIT_TYPE').bindStore(findLookupByType('VISIT_TYPE_2'));
        }else{
        	if(taskType == 0 && visitType == 2){
        		view.contentPanel.getForm().findField('VISIT_TYPE').bindStore(findLookupByType('VISIT_TYPE_1'));
        	}else if(taskType == 1){
        		view.contentPanel.getForm().findField('VISIT_TYPE').bindStore(findLookupByType('VISIT_TYPE_NEW'));
        	}
        } 
	}
};

var viewshow = function(view){
//	if(view._defaultTitle == '新增拟申请授信产品'){
//		getCurrentView().contentPanel.getForm().findField('CP_PRODUCT').hide();
//	}else 
	if(view._defaultTitle == '补充CALLREPORT维护'){
		view.contentPanel.getForm().findField('VISIT_TYPE').bindStore(findLookupByType('VISIT_TYPE_OLD'));
	}else if(view._defaultTitle == 'CALLREPORT维护'){
		var taskType = view.contentPanel.getForm().findField('TASK_TYPE').getValue();
        var visitType = view.contentPanel.getForm().findField('VISIT_TYPE').getValue();	
        if(visitType==null || visitType == ''){
        	visitType = custData.VISIT_TYPE;
        	view.contentPanel.getForm().findField('VISIT_TYPE').setValue(visitType);
        }
//        alert("viewshow  taskType:"+taskType+"  visitType:"+visitType);
		if(taskType == 0 && visitType != 2){//旧户
//			showCustomerViewByIndex(12);
			showCustomerViewByIndex(24);
			var oldType = view.contentPanel.getForm().findField('MARK_RESULT_OLD').getValue();
			if(oldType == '5'){
				view.contentPanel.getForm().findField('MARK_REFUSEREASON_OLD').show();
				view.contentPanel.getForm().findField('MARK_REFUSEREASON_OLD').bindStore(findLookupByType('MARK_REASON_OLD'));
			}else{
				view.contentPanel.getForm().findField('MARK_REFUSEREASON_OLD').hide();
			}
			view.contentPanel.getForm().findField('VISIT_TYPE').bindStore(findLookupByType('VISIT_TYPE_2'));
		}else{
			showCustomerViewByIndex(3);
			var reType = view.contentPanel.getForm().findField('MARK_RESULT').getValue();
			if(reType == '3'){
				view.contentPanel.getForm().findField('CALL_NEXTTIME').hide(true);
				view.contentPanel.getForm().findField('MARK_REFUSEREASON').show();
				view.contentPanel.getForm().findField('MARK_REFUSEREASON').bindStore(findLookupByType('MARK_REFUSEREASON'));
			}else if(reType == '4'){
				view.contentPanel.getForm().findField('CALL_NEXTTIME').show(true);
				view.contentPanel.getForm().findField('MARK_REFUSEREASON').hide();
			}else{
				view.contentPanel.getForm().findField('CALL_NEXTTIME').hide(true);
				view.contentPanel.getForm().findField('MARK_REFUSEREASON').hide();
			}
//			var cusOnmark = view.contentPanel.getForm().findField('CUS_ONMARK').getValue();
//	        if(cusOnmark == 1){
//	        	view.contentPanel.getForm().findField('CUS_ONMARKPLACE').show();
//	        }else{
//	        	view.contentPanel.getForm().findField('CUS_ONMARKPLACE').hide();
//	        }
		}
	}else if(view._defaultTitle == '新增'){
		getCurrentView().contentPanel.getForm().findField('VISIT_TYPE').hide();
	}else if(view._defaultTitle ==  '详情'){
		//主要股东及持股比例
//		newPanelStroe_1_detail.load({params:{ID:getSelectedData().data.TASK_NUMBER}});
		//主要固定资产
		newPanelStroe_2_detail.load({params:{ID:getSelectedData().data.TASK_NUMBER}});
		//营收获利情况
		newPanelStroe_3_detail.load({params:{ID:getSelectedData().data.TASK_NUMBER}});
		//原材料采购情况
		newPanelStroe_4_detail.load({params:{ID:getSelectedData().data.TASK_NUMBER}});
		//产品销售状况
		newPanelStroe_5_detail.load({params:{ID:getSelectedData().data.TASK_NUMBER}});
		//存款往来银行表
		newPanelStroe_6_detail.load({params:{ID:getSelectedData().data.TASK_NUMBER}});
		//贷款往来银行表
		newPanelStroe_7_detail.load({params:{ID:getSelectedData().data.TASK_NUMBER}});
		//拟承做存款产品
		newPanelStroe_8_detail.load({params:{ID:getSelectedData().data.TASK_NUMBER}});
		//拟申请外汇产品额度
		newPanelStroe_9_detail.load({params:{ID:getSelectedData().data.TASK_NUMBER}});
		//拟申请授信产品
//		newPanelStroe_10_detail.load({params:{ID:getSelectedData().data.TASK_NUMBER}});
		//拟申请授信产品_新户
		newPanelStroe_11_detail.load({params:{ID:getSelectedData().data.TASK_NUMBER}});
		//担保品信息
		newPanelStroe_12_detail.load({params:{ID:getSelectedData().data.TASK_NUMBER}});
		//公司相关人员信息
		newPanelStroe_13_detail.load({params:{ID:getSelectedData().data.TASK_NUMBER}});

        var visitType = getCurrentView().contentPanel.getForm().findField('VISIT_TYPE').getValue();	
        var taskType = getCurrentView().contentPanel.getForm().findField('TASK_TYPE').getValue();
		if(taskType ==0 && visitType != 2){//旧户
			showCustomerViewByIndex(2);
			var oldType = view.contentPanel.getForm().findField('MARK_RESULT_OLD').getValue();
			if(oldType == '5'){
				view.contentPanel.getForm().findField('MARK_REFUSEREASON_OLD').show();
				view.contentPanel.getForm().findField('MARK_REFUSEREASON_OLD').bindStore(findLookupByType('MARK_REASON_OLD'));
			}else{
				view.contentPanel.getForm().findField('MARK_REFUSEREASON_OLD').hide();
			}
			
		}else {//新户
			showCustomerViewByIndex(1);
			var reType = view.contentPanel.getForm().findField('MARK_RESULT').getValue();
			if(reType == '3'){
				view.contentPanel.getForm().findField('CALL_NEXTTIME').hide(true);
				
				view.contentPanel.getForm().findField('MARK_REFUSEREASON').show();
				view.contentPanel.getForm().findField('MARK_REFUSEREASON').bindStore(findLookupByType('MARK_REFUSEREASON'));
			}else if(reType == '4'){
				view.contentPanel.getForm().findField('CALL_NEXTTIME').show(true);
				view.contentPanel.getForm().findField('MARK_REFUSEREASON').hide();
			}else{
				view.contentPanel.getForm().findField('CALL_NEXTTIME').hide(true);
				view.contentPanel.getForm().findField('MARK_REFUSEREASON').hide();
			}
			var cusOnmark = view.contentPanel.getForm().findField('CUS_ONMARK').getValue();
//	        if(cusOnmark == 1){
//	        	view.contentPanel.getForm().findField('CUS_ONMARKPLACE').show();
//	        }else{
//	        	view.contentPanel.getForm().findField('CUS_ONMARKPLACE').hide();
//	        }
		}
	}
};
/**
 * 清除页面数据
 * @param mratio
 * @returns
 */
var cleanData = function(form,commintData) {
	form.reset();
	form.findField('TASK_NUMBER').setValue(commintData.taskNumber);
};
/**
 * 保存连续添加出资比列数据
 * @param mratio
 * @returns
 */
var getmratio = function(mratio) {
	bl.PEC  = parseFloat(bl.PEC)+parseFloat(mratio);
};
/**
 * 保存CALLREPORT维护_新户页面部分数据
 * @param form
 * @returns
 */
var saveBaseValue = function(form){
	temp.INTERVIEWEE_NAME = form.findField('INTERVIEWEE_NAME').getValue();
	temp.INTERVIEWEE_POST = form.findField('INTERVIEWEE_POST').getValue();
	temp.INTERVIEWEE_PHONE = form.findField('INTERVIEWEE_PHONE').getValue();	
	temp.VISIT_TYPE = form.findField('VISIT_TYPE').getValue();
	temp.VISIT_TIME = form.findField('VISIT_TIME').getValue();
	temp.VISIT_START_TIME = form.findField('VISIT_START_TIME').getValue();
	temp.VISIT_END_TIME = form.findField('VISIT_END_TIME').getValue();
	temp.CALL_TIME = form.findField('CALL_TIME').getValue();
	temp.JOIN_PERSON_ID = form.findField('JOIN_PERSON_ID').getValue();
	temp.JOIN_PERSON = form.findField('JOIN_PERSON').getValue();
	
	temp.CUS_DOMICILE = form.findField('CUS_DOMICILE').getValue();
	temp.CUS_NATURE = form.findField('CUS_NATURE').getValue();
	temp.CUS_REGTIME = form.findField('CUS_REGTIME').getValue();
	temp.DCRB_MAJORSHOLDER = form.findField('DCRB_MAJORSHOLDER').getValue();
	temp.DCRB_FLOW = form.findField('DCRB_FLOW').getValue();
	temp.CUS_OPERATEAGE = form.findField('CUS_OPERATEAGE').getValue();
	temp.CUS_ONMARK = form.findField('CUS_ONMARK').getValue();
	temp.CUS_CNTPEOPLE = form.findField('CUS_CNTPEOPLE').getValue();
	temp.CUS_SITEOPERATETIME = form.findField('CUS_SITEOPERATETIME').getValue();
	temp.CUS_SITEOPERATEAGE = form.findField('CUS_SITEOPERATEAGE').getValue();
	temp.CUS_BUSISTATUS = form.findField('CUS_BUSISTATUS').getValue();
	temp.CUS_MAJORRIVAL = form.findField('CUS_MAJORRIVAL').getValue();
	temp.CUS_OWNBUSI = form.findField('CUS_OWNBUSI').getValue();
	temp.CUS_MAJORPRODUCT = form.findField('CUS_MAJORPRODUCT').getValue();
//	temp.CUS_INCOMETY = form.findField('CUS_INCOMETY').getValue();
//	temp.CUS_INCOMELY = form.findField('CUS_INCOMELY').getValue();
	temp.CUS_OPERATEADDR = form.findField('CUS_OPERATEADDR').getValue();
//	temp.CUS_LEGALPERSON = form.findField('CUS_LEGALPERSON').getValue();
//	temp.CUS_LEGALPTEL = form.findField('CUS_LEGALPTEL').getValue();
//	temp.CUS_ACCOUNTPERSON = form.findField('CUS_ACCOUNTPERSON').getValue();
//	temp.CUS_ACCOUNTPTEL = form.findField('CUS_ACCOUNTPTEL').getValue();
//	temp.CUS_OPERATEPERSON = form.findField('CUS_OPERATEPERSON').getValue();
//	temp.CUS_OPERATEPTEL = form.findField('CUS_OPERATEPTEL').getValue();
//	temp.CUS_OPERATEPWAGE = form.findField('CUS_OPERATEPWAGE').getValue();
//	temp.CUS_OPERATEPMAGE = form.findField('CUS_OPERATEPMAGE').getValue();
	
	temp.DCRB_MYSELFTRADE = form.findField('DCRB_MYSELFTRADE').getValue();
	temp.RES_OTHERINFO = form.findField('RES_OTHERINFO').getValue();
	temp.RES_FOLLOWUP = form.findField('RES_FOLLOWUP').getValue();
	temp.RES_CUSTSOURCE = form.findField('RES_CUSTSOURCE').getValue();
	temp.RES_CUSTSOURCEDATE = form.findField('RES_CUSTSOURCEDATE').getValue();
	temp.IF_OWNBANKCUST = form.findField('IF_OWNBANKCUST').getValue();
	temp.RES_CASEBYPERSON = form.findField('RES_CASEBYPERSON').getValue();
	temp.RES_CASEBYPTEL = form.findField('RES_CASEBYPTEL').getValue();
	temp.MARK_RESULT = form.findField('MARK_RESULT').getValue();
	temp.MARK_REFUSEREASON = form.findField('MARK_REFUSEREASON').getValue();
	temp.CALL_SPENDTIME = form.findField('CALL_SPENDTIME').getValue();
	temp.CALL_NEXTTIME = form.findField('CALL_NEXTTIME').getValue();
	
	temp.DCRB_FIXEDASSETS = form.findField('DCRB_FIXEDASSETS').getValue();//固定资产补充说明
	temp.DCRB_PROFIT = form.findField('DCRB_PROFIT').getValue();//获利补充说明
	temp.DCRB_SYMBIOSIS = form.findField('DCRB_SYMBIOSIS').getValue();//上下游合作交易情况补充说明
	temp.DCRB_OTHERTRADE = form.findField('DCRB_OTHERTRADE').getValue();//他行往来情况补充说明
};
/**
 * 回填CALLREPORT维护_新户页面部分数据
 * @param form
 * @returns
 */
var setBaseValue = function(form) {
	form.findField('INTERVIEWEE_NAME').setValue(temp.INTERVIEWEE_NAME);
	form.findField('INTERVIEWEE_POST').setValue(temp.INTERVIEWEE_POST);
	form.findField('INTERVIEWEE_PHONE').setValue(temp.INTERVIEWEE_PHONE);
	form.findField('VISIT_TYPE').setValue(temp.VISIT_TYPE);
	form.findField('VISIT_TIME').setValue(temp.VISIT_TIME);
	form.findField('VISIT_START_TIME').setValue(temp.VISIT_START_TIME);
	form.findField('VISIT_END_TIME').setValue(temp.VISIT_END_TIME);
	form.findField('CALL_TIME').setValue(temp.CALL_TIME);
	form.findField('JOIN_PERSON_ID').setValue(temp.JOIN_PERSON_ID);
	form.findField('JOIN_PERSON').setValue(temp.JOIN_PERSON);
	
	form.findField('CUS_DOMICILE').setValue(temp.CUS_DOMICILE);
	form.findField('CUS_NATURE').setValue(temp.CUS_NATURE);
	form.findField('CUS_REGTIME').setValue(temp.CUS_REGTIME);
	form.findField('DCRB_MAJORSHOLDER').setValue(temp.DCRB_MAJORSHOLDER);
	form.findField('DCRB_FLOW').setValue(temp.DCRB_FLOW);
	form.findField('CUS_OPERATEAGE').setValue(temp.CUS_OPERATEAGE);
	form.findField('CUS_ONMARK').setValue(temp.CUS_ONMARK);
	form.findField('CUS_CNTPEOPLE').setValue(temp.CUS_CNTPEOPLE);
	form.findField('CUS_SITEOPERATETIME').setValue(temp.CUS_SITEOPERATETIME);
	form.findField('CUS_SITEOPERATEAGE').setValue(temp.CUS_SITEOPERATEAGE);
	form.findField('CUS_BUSISTATUS').setValue(temp.CUS_BUSISTATUS);
	form.findField('CUS_MAJORRIVAL').setValue(temp.CUS_MAJORRIVAL);
	form.findField('CUS_OWNBUSI').setValue(temp.CUS_OWNBUSI);
	form.findField('CUS_MAJORPRODUCT').setValue(temp.CUS_MAJORPRODUCT);
//	form.findField('CUS_INCOMETY').setValue(temp.CUS_INCOMETY);
//	form.findField('CUS_INCOMELY').setValue(temp.CUS_INCOMELY);
	form.findField('CUS_OPERATEADDR').setValue(temp.CUS_OPERATEADDR);
//	form.findField('CUS_LEGALPERSON').setValue(temp.CUS_LEGALPERSON);
//	form.findField('CUS_LEGALPTEL').setValue(temp.CUS_LEGALPTEL);
//	form.findField('CUS_ACCOUNTPERSON').setValue(temp.CUS_ACCOUNTPERSON);
//	form.findField('CUS_ACCOUNTPTEL').setValue(temp.CUS_ACCOUNTPTEL);
//	form.findField('CUS_OPERATEPERSON').setValue(temp.CUS_OPERATEPERSON);
//	form.findField('CUS_OPERATEPTEL').setValue(temp.CUS_OPERATEPTEL);
//	form.findField('CUS_OPERATEPWAGE').setValue(temp.CUS_OPERATEPWAGE);
//	form.findField('CUS_OPERATEPMAGE').setValue(temp.CUS_OPERATEPMAGE);
	
	form.findField('DCRB_MYSELFTRADE').setValue(temp.DCRB_MYSELFTRADE);
	form.findField('RES_OTHERINFO').setValue(temp.RES_OTHERINFO);
	form.findField('RES_FOLLOWUP').setValue(temp.RES_FOLLOWUP);
	form.findField('RES_CUSTSOURCE').setValue(temp.RES_CUSTSOURCE);
	form.findField('RES_CUSTSOURCEDATE').setValue(temp.RES_CUSTSOURCEDATE);
	form.findField('IF_OWNBANKCUST').setValue(temp.IF_OWNBANKCUST);
	form.findField('RES_CASEBYPERSON').setValue(temp.RES_CASEBYPERSON);
	form.findField('RES_CASEBYPTEL').setValue(temp.RES_CASEBYPTEL);
	form.findField('MARK_RESULT').setValue(temp.MARK_RESULT);
	form.findField('MARK_REFUSEREASON').setValue(temp.MARK_REFUSEREASON);
	form.findField('CALL_SPENDTIME').setValue(temp.CALL_SPENDTIME);
	form.findField('CALL_NEXTTIME').setValue(temp.CALL_NEXTTIME);
	
	form.findField('DCRB_FIXEDASSETS').setValue(temp.DCRB_FIXEDASSETS);
	form.findField('DCRB_PROFIT').setValue(temp.DCRB_PROFIT);
	form.findField('DCRB_SYMBIOSIS').setValue(temp.DCRB_SYMBIOSIS);
	form.findField('DCRB_OTHERTRADE').setValue(temp.DCRB_OTHERTRADE);
};
//===============================================================================================
//var editRrownum = new Ext.grid.RowNumberer({
//	  header : 'No.',
//	  width : 28
//});
//var newCm_detail =  new Ext.grid.ColumnModel([editRrownum,
//                                     {header : 'ID', dataIndex : 'ID',sortable : true,width : 120,hidden:true},
//                                     {header:'TASK_NUMBER',dataIndex:'TASK_NUMBER',sortable:true,width:120,hidden:true},
//                                     {header:'出资人',dataIndex:'M_SPONSOR',sortable:true,width:120},
//                                     {header:'出资比例(%)',dataIndex:'M_RATIO',sortable:true,width:120},
//                                     {header:'出资金额(人民币/千元)',dataIndex:'M_MONEY',sortable:true,width:120}
//        	                                     ]); 
//var newPanelStroe_1_detail = new Ext.data.Store({
//	restful : true,
//	proxy : new Ext.data.HttpProxy(
//			{
//				url : basepath + '/ocrmFInterviewShareholder.json' 
//			}),
//			reader : new Ext.data.JsonReader( {
//				root : 'json.data'
//			}, [{name:'ID'},
//			    {name:'TASK_NUMBER'},
//			    {name:'M_SPONSOR'},
//			    {name:'M_RATIO'},
//			    {name:'M_MONEY'}
//			     ])
//});	
//
//var newPanel_1_detail = new Ext.grid.GridPanel({
//	title : '主要股东及持股比例',
//	autoScroll: true,
//	height:200,
//	store : newPanelStroe_1_detail,
//	frame : true,
//	cm : newCm_detail,
//	loadMask : {
//		msg : '正在加载表格数据,请稍等...'
//	}
//});

//==================主要固定资产==========begin===================================//
var editRrownum = new Ext.grid.RowNumberer({
	  header : 'No.',
	  width : 28
});

var newCm_2_detail =  new Ext.grid.ColumnModel([editRrownum,	
                                     {header : 'ID', dataIndex : 'ID',sortable : true,width : 120,hidden:true},
                                     {header:'TASK_NUMBER',dataIndex:'TASK_NUMBER',sortable:true,width:120,hidden:true},
                                     {header:'房产类型',dataIndex:'F_HTYPE',sortable:true,width:120,renderer:function(value){
                             			var val = translateLookupByKey("F_HTYPE",value);
                            			return val?val:"";
                            			}},
                                     {header:'持有类型',dataIndex:'F_OTYPE',sortable:true,width:120,renderer:function(value){
                             			var val = translateLookupByKey("F_OTYPE",value);
                            			return val?val:"";
                            			}},
                            		 {header:'持有人',dataIndex:'F_HOLDER',sortable:true,width:120},
                            		 {header:'所在区域',dataIndex:'F_REGION',sortable:true,width:120},
                                     {header:'面积（平方米）',dataIndex:'F_AREA',sortable:true,width:120},
                                     {header:'使用状况',dataIndex:'F_UTYPE',sortable:true,width:120,renderer:function(value){
                             			var val = translateLookupByKey("F_UTYPE",value);
                            			return val?val:"";
                            			}},
                                     {header:'估价(人民币/千元)',dataIndex:'F_ASSESS',sortable:true,width:120},
                                     {header:'是否已抵押',dataIndex:'F_SECURED',sortable:true,width:120,renderer:function(value){
                                 		var val = translateLookupByKey("F_SECURED",value);
                                 		return val?val:"";
                                 		}},
                                     {header:'备注',dataIndex:'F_MEMO',sortable:true,width:120}
        	                                     ]); 
var newPanelStroe_2_detail = new Ext.data.Store({
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
			    {name:'F_MEMO'},
			    {name:'F_HOLDER'},
			    {name:'F_REGION'},
			    {name:'F_SECURED'}
			     ])
});	
var newPanel_2_detail = new Ext.grid.GridPanel({
	title : '主要固定资产',
	autoScroll: true,
	height:200,
	store : newPanelStroe_2_detail,
	frame : true,
	cm : newCm_2_detail,
	loadMask : {
		msg : '正在加载表格数据,请稍等...'
	}
});
//======================主要固定资产======end===========================================//

///////////////////////营收，获利情况//////////begin//////////////////////////////////////////////
var editRrownum = new Ext.grid.RowNumberer({
	  header : 'No.',
	  width : 28
});

var newCm_3_detail =  new Ext.grid.ColumnModel([editRrownum,
                                     {header : 'ID', dataIndex : 'ID',sortable : true,width : 120,hidden:true},
                                     {header:'TASK_NUMBER',dataIndex:'TASK_NUMBER',sortable:true,width:120,hidden:true},
                                     {header:'开始年份',dataIndex:'P_YEARS',sortable:true,width:120},
                                     {header:'结束年份',dataIndex:'P_YEARS_END',sortable:true,width:120},
                                     {header:'营收(人民币/千元)',dataIndex:'P_REVENUE',sortable:true,width:120},
                                     {header:'毛利率(%)',dataIndex:'P_GROSS',sortable:true,width:120},
                                     {header:'税后净利率(%)',dataIndex:'P_PNET',sortable:true,width:120},
                                     {header:'备注',dataIndex:'P_MEMO',sortable:true,width:120}
        	                                     ]); 
var newPanelStroe_3_detail = new Ext.data.Store({
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

var newPanel_3_detail = new Ext.grid.GridPanel({
	title : '营收,获利情况',
	autoScroll: true,
	height:200,
	store : newPanelStroe_3_detail,
	frame : true,
	cm : newCm_3_detail,
	loadMask : {
		msg : '正在加载表格数据,请稍等...'
	}
});
/////////////////////营收，获利情况//////end///////////////////////////////////////////////////

//====================原材料采购情况====begin================================================
var editRrownum = new Ext.grid.RowNumberer({
	  header : 'No.',
	  width : 28
});

var newCm_4_detail =  new Ext.grid.ColumnModel([editRrownum,
                                     {header : 'ID', dataIndex : 'ID',sortable : true,width : 120,hidden:true},
                                     {header:'TASK_NUMBER',dataIndex:'TASK_NUMBER',sortable:true,width:120,hidden:true},
                                     {header:'供应商品',dataIndex:'MP_GOODS',sortable:true,width:120},
                                     {header:'供应商名称',dataIndex:'MP_SUPPLIER',sortable:true,width:120},
                                     {header:'是否关联企业',dataIndex:'MP_ISRELATE',sortable:true,width:120,renderer:function(value){
                             			var val = translateLookupByKey("IF_FLAG",value);
                            			return val?val:"";
                            			}},
                                     {header:'月采购金额(人民币/千元)',dataIndex:'MP_MONTH2MONEY',sortable:true,width:120},
                                     {header:'结算天数(天)',dataIndex:'MP_BALANCEDAYS',sortable:true,width:120},
                                     {header:'往来年数(年)',dataIndex:'MP_TRADEYEARS',sortable:true,width:120},
                                     {header:'结算方式',dataIndex:'MP_PAYWAY',sortable:true,width:120,renderer:function(value){
                             			var val = translateLookupByKey("PS_PAYWAY",value);
                            			return val?val:"";
                            			}},
                                     {header:'备注',dataIndex:'MP_MEMO',sortable:true,width:120}
        	                                     ]); 
var newPanelStroe_4_detail = new Ext.data.Store({
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

var newPanel_4_detail = new Ext.grid.GridPanel({
	title : '原材料采购情况',
	autoScroll: true,
	height:200,
	store : newPanelStroe_4_detail,
	frame : true,
	cm : newCm_4_detail,
	loadMask : {
		msg : '正在加载表格数据,请稍等...'
	}
});
//================原材料采购情况=====end=============================================

//=================产品销售状况====begin================================================
var editRrownum = new Ext.grid.RowNumberer({
	  header : 'No.',
	  width : 28
});

var newCm_5_detail=  new Ext.grid.ColumnModel([editRrownum,
                                     {header : 'ID', dataIndex : 'ID',sortable : true,width : 120,hidden:true},
                                     {header:'TASK_NUMBER',dataIndex:'TASK_NUMBER',sortable:true,width:120,hidden:true},
                                     {header:'销售产品',dataIndex:'PS_GOODS',sortable:true,width:120},
                                     {header:'购买方名称',dataIndex:'PS_BUYER',sortable:true,width:120},
                                     {header:'是否关联企业',dataIndex:'PS_ISRELATE',sortable:true,width:120,renderer:function(value){
                             			var val = translateLookupByKey("IF_FLAG",value);
                            			return val?val:"";
                            			}},
                                     {header:'月销售金额(人民币/千元)',dataIndex:'PS_MONTH2MONEY',sortable:true,width:120},
                                     {header:'结算天数(天)',dataIndex:'PS_BALANCEDAYS',sortable:true,width:120},
                                     {header:'往来年数(年)',dataIndex:'PS_TRADEYEARS',sortable:true,width:120},
                                     {header:'结算方式',dataIndex:'PS_PAYWAY',sortable:true,width:120,renderer:function(value){
                             			var val = translateLookupByKey("PS_PAYWAY",value);
                            			return val?val:"";
                            			}},
                                     {header:'备注',dataIndex:'PS_MEMO',sortable:true,width:120}
        	                                     ]); 
var newPanelStroe_5_detail = new Ext.data.Store({
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

var newPanel_5_detail = new Ext.grid.GridPanel({
	title : '产品销售状况',
	autoScroll: true,
	height:200,
	store : newPanelStroe_5_detail,
	frame : true,
	cm : newCm_5_detail,
	loadMask : {
		msg : '正在加载表格数据,请稍等...'
	}
});
//================产品销售状况=========end=============================================
//================存款往来银行表=====begin==============================================
var editRrownum = new Ext.grid.RowNumberer({
	  header : 'No.',
	  width : 28
});

var newCm_6_detail=  new Ext.grid.ColumnModel([editRrownum, 
                                     {header : 'ID', dataIndex : 'ID',sortable : true,width : 120,hidden:true},
                                     {header:'TASK_NUMBER',dataIndex:'TASK_NUMBER',sortable:true,width:120,hidden:true},
                                     {header:'往来银行',dataIndex:'D_BANKNAME',sortable:true,width:120},
                                     {header:'平均存款量（人民币/千元）',dataIndex:'D_AVGDEPOSIT',sortable:true,width:120},
                                     {header:'存款类型',dataIndex:'D_DEPOSIT_TYPE',sortable:true,width:120},
                                     {header:'期限',dataIndex:'D_TERM',sortable:true,width:120}
        	                                     ]); 
var newPanelStroe_6_detail = new Ext.data.Store({
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
			    {name:'D_AVGDEPOSIT'},
			    {name:'D_DEPOSIT_TYPE'},
			    {name:'D_TERM'}
			     ])
});	
var newPanel_6_detail = new Ext.grid.GridPanel({
	title : '存款往来银行表',
	autoScroll: true,
	height:200,
	store : newPanelStroe_6_detail,
	frame : true,
	cm : newCm_6_detail,
	loadMask : {
		msg : '正在加载表格数据,请稍等...'
	}
});
//====================存款往来银行表======end====================================================
//====================贷款往来银行表======begin==========================================
var editRrownum = new Ext.grid.RowNumberer({
	  header : 'No.',
	  width : 28
});

var newCm_7_detail=  new Ext.grid.ColumnModel([editRrownum, 
                                     {header:'ID', dataIndex : 'ID',sortable : true,width : 120,hidden:true},
                                     {header:'TASK_NUMBER',dataIndex:'TASK_NUMBER',sortable:true,width:120,hidden:true},
                                     {header:'往来银行',dataIndex:'L_BANKNAME',sortable:true,width:120},
                                     {header:'额度类型',dataIndex:'L_LIMITTYPE',sortable:true,width:120},
                                     {header:'额度金额(人民币/千元)',dataIndex:'L_LIMITMONEY',sortable:true,width:120},
                                     {header:'动拨余额(人民币/千元)',dataIndex:'L_BALANCE',sortable:true,width:120},
                                     {header:'利率(%)',dataIndex:'L_RATE',sortable:true,width:120},
                                     {header:'担保率(%)',dataIndex:'L_DBRATE',sortable:true,width:120,hidden:true},
                                     {header:'担保品',dataIndex:'L_COLLATERAL',sortable:true,width:120},
                                     {header:'备注',dataIndex:'L_MEMO',sortable:true,width:120}
        	                                     ]); 
var newPanelStroe_7_detail = new Ext.data.Store({
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

var newPanel_7_detail = new Ext.grid.GridPanel({
	title : '贷款往来银行表',
	autoScroll: true,
	height:200,
	store : newPanelStroe_7_detail,
	frame : true,
	cm : newCm_7_detail,
	loadMask : {
		msg : '正在加载表格数据,请稍等...'
	}
});
//===================贷款往来银行表=====end======================================
//===================拟承做存款产品====begin======================================
var editRrownum = new Ext.grid.RowNumberer({
	  header : 'No.',
	  width : 28
});

var newCm_8_detail=  new Ext.grid.ColumnModel([editRrownum, 
                                     {header:'ID', dataIndex : 'ID',sortable : true,width : 120,hidden:true},
                                     {header:'TASK_NUMBER',dataIndex:'TASK_NUMBER',sortable:true,width:120,hidden:true},
                                     {header:'存款产品',dataIndex:'DP_NAME',sortable:true,width:120},
                                     {header:'预计平均存款量（人民币/千元）',dataIndex:'DP_AVGDEPOSIT',sortable:true,width:120}
        	                                     ]); 
var newPanelStroe_8_detail = new Ext.data.Store({
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

var newPanel_8_detail = new Ext.grid.GridPanel({
	title : '拟承做存款产品',
	autoScroll: true,
	height:200,
	store : newPanelStroe_8_detail,
	frame : true,
	cm : newCm_8_detail,
	loadMask : {
		msg : '正在加载表格数据,请稍等...'
	}
});
//=================拟承做存款产品====end=======================================
//=================拟申请外汇产品额度==begin===================================
var editRrownum = new Ext.grid.RowNumberer({
	  header : 'No.',
	  width : 28
});
var newCm_9_detail=  new Ext.grid.ColumnModel([editRrownum, 
                                     {header:'ID', dataIndex : 'ID',sortable : true,width : 120,hidden:true},
                                     {header:'TASK_NUMBER',dataIndex:'TASK_NUMBER',sortable:true,width:120,hidden:true},
                                     {header:'外汇产品',dataIndex:'FL_NAME',sortable:true,width:120},
                                     {header:'月交易量（等值美金/千元）',dataIndex:'FL_DEAL2MONTH',sortable:true,width:120},
                                     {header:'额度金额（等值美金/千元）',dataIndex:'FL_LIMITMONEY',sortable:true,width:120}
        	                                     ]); 
var newPanelStroe_9_detail = new Ext.data.Store({
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

var newPanel_9_detail = new Ext.grid.GridPanel({
	title : '拟申请外汇产品额度',
	autoScroll: true,
	height:200,
	store : newPanelStroe_9_detail,
	frame : true,
	cm : newCm_9_detail,
	loadMask : {
		msg : '正在加载表格数据,请稍等...'
	}
});
//=================拟申请外汇产品额度==end=====================================
//=================拟申请授信产品(旧户)====begin=====================================
//var editRrownum = new Ext.grid.RowNumberer({
//	  header : 'No.',
//	  width : 28
//});
//var newCm_10_detail=  new Ext.grid.ColumnModel([editRrownum, 	
//                                     {header:'ID', dataIndex : 'ID',sortable : true,width : 120,hidden:true},
//                                     {header:'TASK_NUMBER',dataIndex:'TASK_NUMBER',sortable:true,width:120,hidden:true},
//                            		 {header:'产品类型',dataIndex:'CP_PRODUCT_P',sortable:true,width:120,renderer:function(value){
//                            				var val = translateLookupByKey("CP_PRODUCT_P",value);
//                            				return val?val:"";
//                            		 }},
//                                     {header:'授信产品',dataIndex:'CP_PRODUCT',sortable:true,width:120,renderer:function(value){
//                             			var val = translateLookupByKey("CP_PRODUCT",value);
//                            			return val?val:"";
//                            			}},
//                        			 {header:'用途',dataIndex:'CP_USE',sortable:true,width:120,renderer:function(value){
//                        				var val = translateLookupByKey("CP_USE",value);
//                        				return val?val:"";
//                        			 }},
//                                     {header:'币种',dataIndex:'CP_CURRENCY',sortable:true,width:120,renderer:function(value){
//                             			var val = translateLookupByKey("CURRENCY",value);
//                            			return val?val:"";
//                            			}},
//                                     {header:'额度金额(人民币/千元)',dataIndex:'CP_LIMITMONEY',sortable:true,width:120},
//                                     {header:'担保品',dataIndex:'CP_COLLATERAL',sortable:true,width:120},
//                                     {header:'担保比率(%)',dataIndex:'CP_DBRATE',sortable:true,width:120},
//                                     {header:'备注',dataIndex:'CP_MEMO',sortable:true,width:120}
//        	                                     ]); 
//var newPanelStroe_10_detail = new Ext.data.Store({
//	restful : true,
//	proxy : new Ext.data.HttpProxy(
//			{
//				url : basepath + '/ocrmFInterviewCreditpro.json' 
//			}),
//			reader : new Ext.data.JsonReader( {
//				root : 'json.data'
//			}, [{name:'ID'},
//			    {name:'TASK_NUMBER'},
//			    {name:'CP_USE'},
//			    {name:'CP_PRODUCT'},
//			    {name:'CP_CURRENCY'},
//			    {name:'CP_LIMITMONEY'},
//			    {name:'CP_MEMO'},
//			    {name:'CP_PRODUCT_P'},
//				{name:'CP_COLLATERAL'},
//				{name:'CP_DBRATE'}
//			     ])
//});	
//var newPanel_10_detail = new Ext.grid.GridPanel({
//	title : '拟申请授信产品',
//	autoScroll: true,
//	height:200,
//	store : newPanelStroe_10_detail,
//	frame : true,
//	cm : newCm_10_detail,
//	loadMask : {
//		msg : '正在加载表格数据,请稍等...'
//	}
//});
//=================拟申请授信产品(旧户)====end=======================================

//=================拟申请授信产品(新户)====begin=====================================
var editRrownum = new Ext.grid.RowNumberer({
	  header : 'No.',
	  width : 28
});
var newCm_11_detail =  new Ext.grid.ColumnModel([editRrownum, 	
                                     {header:'ID', dataIndex : 'ID',sortable : true,width : 120,hidden:true},
                                     {header:'TASK_NUMBER',dataIndex:'TASK_NUMBER',sortable:true,width:120,hidden:true},
                        			 {header:'贷款用途',dataIndex:'CP_USE',sortable:true,width:120,renderer:function(value){
                        				var val = translateLookupByKey("CP_USE",value);
                        				return val?val:"";
                        			 }},
                        			 {header:'授信产品',dataIndex:'CP_PRODUCT',sortable:true,width:120,renderer:function(value){
                              			var val = translateLookupByKey("CP_PRODUCT",value);
                             			return val?val:"";
                             			}},
                                     {header:'币种',dataIndex:'CP_CURRENCY',sortable:true,width:120,renderer:function(value){
                             			var val = translateLookupByKey("CURRENCY",value);
                            			return val?val:"";
                            			}},
                                     {header:'额度金额(人民币/千元)',dataIndex:'CP_LIMITMONEY',sortable:true,width:120},
                                     {header: '贷款期限(月)',dataIndex: 'CREDIT_PERIOD',sortable: true,width: 120},
                                     {header: '还款方式',dataIndex: 'REPAYMENT_METHOD',sortable: true,width: 120,renderer:function(value){
                                    	 var val = translateLookupByKey("DM0004",value);
                                    	 return val?val:"";
                                     }},
                                     {header:'备注',dataIndex: 'CP_MEMO',sortable:true,width:120}
        	                                     ]); 
var newPanelStroe_11_detail = new Ext.data.Store({
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
			    {name:'CREDIT_PERIOD'},
			    {name:'REPAYMENT_METHOD'}
			     ])
});	
var newPanel_11_detail = new Ext.grid.GridPanel({
	title : '拟申请授信产品',
	autoScroll: true,
	height:200,
	store : newPanelStroe_11_detail,
	frame : true,
	
	cm : newCm_11_detail,
	loadMask : {
		msg : '正在加载表格数据,请稍等...'
	}
});
//=================拟申请授信产品(新户)====end=======================================


//=================担保品信息========begin=====================================
var editRrownum = new Ext.grid.RowNumberer({
	  header : 'No.',
	  width : 28
});
var newCm_12_detail = new Ext.grid.ColumnModel([editRrownum,
                                                {header: 'ID', dataIndex: 'ID',sortable: true,width: 120,hidden: true},
                                                {header: 'TASK_NUMBER', dataIndex: 'TASK_NUMBER',sortable: true,width: 120,hidden: true},
                                                {header: '担保品类型',dataIndex: 'COLLATERAL_TYPE',width: 120,renderer:function(value){
                                                	var val = translateLookupByKey("DM0019",value);
                                                	return val?val:"";
                                                }},
                                                {header: '担保品估值(人民币/千元)',dataIndex: 'ESTIMATE_VALUE',sortable: 'true',width: 120},
                                                {header: '担保品净值(人民币/千元)',dataIndex: 'NET_VALUE',sortable: 'true',width: 120},
                                                {header:'担保品所在地',dataIndex: 'COLLATERAL_ADDR',sortable:true,width:120}
                                                ]);
var newPanelStroe_12_detail = new Ext.data.Store({
	restful : true,
	proxy : new Ext.data.HttpProxy(
			{
				url : basepath + '/ocrmFInterviewCollateral.json'
			}),
	reader : new Ext.data.JsonReader( {
				root : 'json.data'
			}, [{name:'ID'},
			    {name:'TASK_NUMBER'},
			    {name:'COLLATERAL_TYPE'},
			    {name:'ESTIMATE_VALUE'},
			    {name:'COLLATERAL_ADDR'},
			    {name:'NET_VALUE'}
			     ])
});
var newPanel_12_detail = new Ext.grid.GridPanel({
	title : '担保品信息',
	autoScroll: true,
	height:200,
	store : newPanelStroe_12_detail,
	frame : true,
	cm : newCm_12_detail,
	loadMask : {
		msg : '正在加载表格数据,请稍等...'
	}
});
//=================担保品信息========end======================================

//===================公司相关人员信息====begin======================================
var editRrownum = new Ext.grid.RowNumberer({
	  header : 'No.',
	  width : 28
});

var newCm_13_detail=  new Ext.grid.ColumnModel([editRrownum, 
                                            {header:'ID', dataIndex : 'ID',sortable : true,width : 120,hidden:true},
                                            {header:'TASK_NUMBER',dataIndex:'TASK_NUMBER',sortable:true,width:120,hidden:true},
                                            {header:'人员角色',dataIndex:'PER_ROLE',sortable:true,width:120,renderer:function(value){
                                 				var val = translateLookupByKey("PER_ROLE",value);
                                 				return val?val:"";
                                 			 }},
                                            {header:'人员姓名',dataIndex:'PER_NAME',sortable:true,width:120},
                                            {header:'人员电话',dataIndex:'PER_TELPHONE',sortable:true,width:120,vtype:'telephone'},
                                            {header:'股东持股出资比例',dataIndex:'SHOLDER_CAPITAL',sortable:true,width:120},
                                            {header:'出资金额',dataIndex:'AMOUNT_CAPITAL',sortable:true,width:120},
                                            {header:'说明',dataIndex:'PER_EXPLAIN',sortable:true,width:160}
    	                                     ]); 
var newPanelStroe_13_detail = new Ext.data.Store({
	restful : true,
	proxy : new Ext.data.HttpProxy(
			{
				url : basepath + '/ocrmFInterviewPerson.json' 
			}),
			reader : new Ext.data.JsonReader( {
				root : 'json.data'
			}, [{name:'ID'},
			    {name:'TASK_NUMBER'},
			    {name:'PER_ROLE'},
			    {name:'PER_NAME'},
			    {name:'PER_TELPHONE'},
			    {name:'SHOLDER_CAPITAL'},
			    {name:'AMOUNT_CAPITAL'},
			    {name:'PER_EXPLAIN'}
			     ])
});	

var newPanel_13_detail = new Ext.grid.GridPanel({
	title : '公司相关人员信息',
	autoScroll: true,
	height:200,
	store : newPanelStroe_13_detail,
	frame : true,
	cm : newCm_13_detail,
	loadMask : {
		msg : '正在加载表格数据,请稍等...'
	}
});
//=================公司相关人员信息====end=======================================
var jt_form_detail = new Ext.form.FieldSet({
	xtype:'fieldset',
	title:'拜访目的',
	titleCollapse : true,
	collapsed :false,
	anchor : '90%',
	items:[{
		layout : 'column',
		items:[{
			layout : 'form',
			columnWidth : .43,
			items:[
			       {fieldLabel:'正常客户定期回访',xtype:'checkbox',labelStyle:'text-align:right;',anchor:'95%',name:'PUR_CUST2CALL',inputValue:1,disabled:true},
			       {fieldLabel:'勘察担保品',xtype:'checkbox',labelStyle:'text-align:right;',anchor:'95%',name:'PUR_SEEK2COLL',disabled:true},
			       {fieldLabel:'预警客户定期回访',xtype:'checkbox',labelStyle:'text-align:right;',anchor:'95%',name:'PUR_WARN2CALL',disabled:true}
			       ]
		},{
			layout : 'form',
			columnWidth : .43,
			labelWidth:200,
			items:[
			       {fieldLabel:'年审/条件变更风管部协访',xtype:'checkbox',labelWidth:150,anchor:'95%',name:'PUR_DEFEND2CALL',disabled:true},
			       {fieldLabel:'营销新产品',xtype:'checkbox',labelStyle:'text-align:right;',anchor:'95%',name:'PUR_MARK2PRO',disabled:true},
			       {fieldLabel:'授信风险增加临时拜访',xtype:'checkbox',labelStyle:'text-align:right;',anchor:'95%',name:'PUR_RISK2CALL',disabled:true}
			       ]
		}]
	}]
});

//页面之内实现跳转  str为欲跳转出处
var pagejump = function(str){
	 var win = window; 
		var n = 0; 
		var  i, found; 
		var txt = win.document.body.createTextRange(); 
		for (i = 0; i <= n && (found = txt.findText(str)) != false; i++) { 
		txt.moveStart("character", 1); 
		txt.moveEnd("textedit"); 
		} 
		if (found) { 
		txt.findText(str); 
		txt.select(); 
		txt.scrollIntoView(); 
		} 
};

var beforeconditioninit = function(panel, app){
	app.pageSize = 500;
};