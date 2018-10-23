/**
 * @description 企金客户营销流程 -  电访信息页面
 * @author luyy
 * @since 2014-07-24
 * @modify dongyi 2014-11-25
 */
imports([
    '/contents/pages/com.yucheng.bcrm/com.yucheng.bcrm.js'
	,'/contents/pages/common/Com.yucheng.bcrm.common.OrgField.js' // 机构放大镜
	,'/contents/pages/common/Com.yucheng.crm.common.OrgUserManage.js'	//用户放大镜
	,'/contents/pages/common/Com.yucheng.bcrm.common.CustomerQueryFieldQZ.js'	//客户放大镜（企商金营销用）
]);

var url = basepath + '/mktCallC.json';

var lookupTypes = ['IF_FLAG','XD000286','CUST_CLASS','CALL_RESULT','CHECK_STAT',
					'CUST_SOURCE','TEL_CONTACTER','CUST_REVENUE','CALL_PURPOSE',
					'CUST_BUSI_CONDITION','COLLATERAL_CONDITION','CUS_OWNBUSI'];

Ext.QuickTips.init();
var localLookup = {
	'REFUSE_REASON_CALL' : [
		{key : '1',value : '无授信需求'},
		{key : '2',value : '无适合客户产品'},  
		{key : '3',value : '授信价格因素'},  
		{key : '4',value : '无套利空间'},    
		{key : '5',value : '无意更换往来银行'},  
		{key : '6',value : '有不良信用记录'},      
		{key : '7',value : '无合格抵/质押品'},      
		{key : '8',value : '客户规模太小'},      
		{key : '9',value : '暂不予准入行业'},      
		{key : '10',value : '客户净值为负'},    
		{key : '11',value : '客户营运时间太短'},    
		{key : '12',value : '政府融资平台'},    
		{key : '13',value : '无法联系到关键人'},    
		{key : '14',value : '其他原因'}
	],
	'CUST_CLASS_1' : [
		{key : '2',value : '既有客户'}
	]
};					

					
var custData={CUST_ID:"",CUST_NAME:"",LINKMAN:"",LINK_TEL:"",MGR_ID:"",MGR_NAME:"",IF_TARGETBUSI:'',CUS_OWNBUSI:'',IS_SX_CUST:''};//此变量用于存储所选择的客户以及客户的相关属性
var fields = [{name:"ID",text:'id',gridField:false},
              {name:"CUST_ID",text:"客户编号",dataType:'string',gridField:false},
              {name:"MGR_ID",text:"客户经理编号",dataType:'string',gridField:false},
              {name:'CUST_NAME',text:'客户名称',xtype:'customerqueryqz',hiddenName:'CUST_ID',resutlWidth:150,singleSelect: false,searchField: true},
              {name:'MGR_NAME',text:'客户经理',xtype:'userchoose',hiddenName:'MGR_ID',resutlWidth:150,singleSelect: false,searchField: true},
              {name:"CALL_DATE",text:"电访日期",dataType:'date',allowBlank:false,searchField: true},
              {name:'CUST_TYPE',text:'客户类别',translateType:'CUST_CLASS',searchField: true,allowBlank:false,editable: true}
              ];

var tbar=[{
	text:'删除',
	hidden:JsContext.checkGrant('mktCallCDelet'),
	handler:function(){
		if(getSelectedData() == false){
			Ext.Msg.alert('提示','请选择数据！');
			return false;
		}
		Ext.Ajax.request({
			url : basepath + '/mktCallC!checkTrans.json',
			method : 'GET',
			params : {
				id : getSelectedData().data.ID
			},
			success : function(response) {
				 var ret = Ext.decode(response.responseText);
				 var flg = ret.flg;//该客户是否存在新户首次拜访
				 if(flg == '1'){
					Ext.Msg.alert('提示', '转移数据，不允许此操作');
					return false;
				 }
				 var custType = getSelectedData().data.CUST_TYPE;
					if(custType=='1'){//新客户删除
						Ext.MessageBox.confirm('提示','确定删除吗?',function(buttonId){
							if(buttonId.toLowerCase() == "no"){
							return false;
							} 
						   Ext.Ajax.request({
								url : basepath + '/ocrmFCallNewRecord!delData.json',
								method : 'POST',
								params : {
									id : getSelectedData().data.ID
								},
								success : function() {
									Ext.Msg.alert('提示', '操作成功！');
									reloadCurrentData();
									
								},failure : function(response) {
									Ext.Msg.alert('提示','操作失败，失败原因：'+ response.responseText);}
							});
						});
			          	}else if (custType=='2'){//既有客户新增删除
			          		Ext.MessageBox.confirm('提示','确定删除吗?',function(buttonId){
								if(buttonId.toLowerCase() == "no"){
								return false;
								} 
							   Ext.Ajax.request({
									url : basepath + '/mktCallOldFRecord!delData.json',
									method : 'POST',
									params : {
										id : getSelectedData().data.ID
									},
									success : function() {
										Ext.Msg.alert('提示', '操作成功！');
										reloadCurrentData();
										
									},failure : function(response) {
									Ext.Msg.alert('提示','操作失败，失败原因：'+ response.responseText);}
								});
							});
			          	}
			}
		});
	}
},{
	text:'新增',
	hidden:JsContext.checkGrant('mktCallCNewAdd'),
	handler:function(){
		showCustomerViewByTitle('新增');
	}
},{
	text:'修改',
	hidden:JsContext.checkGrant('mktCallCEdit'),
	handler:function(){
		if(getSelectedData() == false){
			Ext.Msg.alert('提示','请选择数据！');
			return false;
		}
		Ext.Ajax.request({
			url : basepath + '/mktCallC!checkTrans.json',
			method : 'GET',
			params : {
				id : getSelectedData().data.ID
			},
			success : function(response) {
				 var ret = Ext.decode(response.responseText);
				 var flg = ret.flg;//该客户是否存在新户首次拜访
				 if(flg == '1'){
					Ext.Msg.alert('提示', '转移数据，不允许此操作');
					return false;
				 }
				 var custType = getSelectedData().data.CUST_TYPE;
					if(custType=='1'){//新增客户
			          		showCustomerViewByTitle('新客户修改');
			          	}else if (custType=='2'){//既有客户新增面板
			          		showCustomerViewByTitle('旧客户修改');
			          	}
			}
		});
	}
},{
	text:'详情',
	hidden:JsContext.checkGrant('mktCallCDetail'),
	handler:function(){
		if(getSelectedData() == false){
			Ext.Msg.alert('提示','请选择数据！');
			return false;
		}
		var custType = getSelectedData().data.CUST_TYPE;
		if(custType=='1'){//新增客户
          		showCustomerViewByTitle('新客户详情');
          	}else if (custType=='2'){//既有客户新增面板
          		showCustomerViewByTitle('旧客户详情');
          	}
	}
}]           
              
var customerView = [{
	title:'新增',
	hideTitle:true,
	type:'form',
	groups:[{
		labelWidth:150,
		columnCount : 2,
		fields:[{name:'CUST_NAME',text:'客户名称',xtype:'customerqueryqz',hiddenName:'CUST_ID',allowBlank:false,custType:'1',
			singleSelected:true,newCust:true,callback:function(a,b){
				custData.LINKMAN=getCurrentView().contentPanel.form.findField("CUST_NAME").linkUser;
				custData.LINK_TEL=getCurrentView().contentPanel.form.findField("CUST_NAME").mobileNum;
				custData.MGR_ID = getCurrentView().contentPanel.form.findField("CUST_NAME").mgrId;
				custData.MGR_NAME = getCurrentView().contentPanel.form.findField("CUST_NAME").mgrName;
				custData.CUS_OWNBUSI = getCurrentView().contentPanel.form.findField("CUST_NAME").industType;
				custData.IF_TARGETBUSI = getCurrentView().contentPanel.form.findField("CUST_NAME").ifTargetbusi;
				custData.IS_SX_CUST = getCurrentView().contentPanel.form.findField("CUST_NAME").isSxCust;
				
				var qz = b[0].json.IS_SX_CUST;
				if(qz == '1'){//授信
					getCurrentView().contentPanel.getForm().findField('CUST_TYPE').setValue(null);
					getCurrentView().contentPanel.getForm().findField('CUST_TYPE').bindStore(findLookupByType('CUST_CLASS_1'));
					getCurrentView().contentPanel.getForm().findField('CUST_TYPE').setValue("2");
				}else{
					getCurrentView().contentPanel.getForm().findField('CUST_TYPE').setValue(null);
					getCurrentView().contentPanel.getForm().findField('CUST_TYPE').bindStore(findLookupByType('CUST_CLASS'));
				}
		}},
		'CUST_TYPE'
		],
		fn:function(CUST_NAME,CUST_TYPE){
			return [CUST_NAME,CUST_TYPE];
		}
	}],
	formButtons : [{
		text:'确认',
		fn : function(formPanel,basicForm){
			if (!formPanel.getForm().isValid()) {
	                Ext.MessageBox.alert('提示','输入有误,请检查输入项');
	                return false;
          		};
          	var value = getCurrentView().contentPanel.form.findField("CUST_TYPE").getValue();//根据客户类别滑出不同的新增面板
          	custData.CUST_NAME=getCurrentView().contentPanel.form.getFieldValues().CUST_NAME;
          	custData.CUST_ID=getCurrentView().contentPanel.form.getFieldValues().CUST_ID;
          	if(value=='1'){//新增客户
          		showCustomerViewByTitle('新客户新增');
          	}else if (value=='2'){//既有客户新增面板
          		showCustomerViewByTitle('旧客户新增');
          	}}
      	}
	]	
},{
	title:'新客户新增',
	hideTitle:true,
	type:'form',
	groups:[{
		columnCount : 2,
		fields:[
			{name:'CUST_NAME',text:'客户名称',allowBlank:false,custType:'1'},
			{name:"CUS_OWNBUSI",text:'所属行业',translateType:'CUS_OWNBUSI',allowBlank:false,editable:true},
			{name:"IF_TARGETBUSI",text:'是否为目标行业',translateType:'IF_FLAG',allowBlank:false,editable:true},
			{name:'LINKMAN',text:'联系人',dataType:'string',allowBlank:false,maxLength:50},
			{name:'LINK_TEL',text:'联系号码',vtype:'phoneNumber',allowBlank:false,maxLength:50},
			{name:'CUST_SOURCE',text:'客户来源',translateType:'CUST_SOURCE',allowBlank:false,editable:true},
			'CALL_DATE',
			'MGR_NAME',
			{name:'CALL_RESULT',text:'电访结果',allowBlank:false,editable:true,translateType:'CALL_RESULT',listeners:{
	    		select:function(combo,record){
		    			var v = this.getValue();
		    			if(v=='2'){
		    				getCurrentView().contentPanel.getForm().findField('REFUSE_RESON').show(true);
		    				getCurrentView().contentPanel.getForm().findField('VISIT_DATE').hide(true);
		    				getCurrentView().contentPanel.getForm().findField('VISIT_DATE').setValue('');
		    			}else{
		    				if(v == '1'){
			    				getCurrentView().contentPanel.getForm().findField('VISIT_DATE').show(true);
			    				getCurrentView().contentPanel.getForm().findField('RECAL_DATE').hide(true);
			    				getCurrentView().contentPanel.getForm().findField('RECAL_DATE').setValue(null);
			    			}else if(v == '3'){
			    				getCurrentView().contentPanel.getForm().findField('VISIT_DATE').hide(true);
			    				getCurrentView().contentPanel.getForm().findField('VISIT_DATE').setValue(null);
			    				getCurrentView().contentPanel.getForm().findField('RECAL_DATE').show(true);
			    			}else{
			    				getCurrentView().contentPanel.getForm().findField('VISIT_DATE').hide(true);
			    				getCurrentView().contentPanel.getForm().findField('VISIT_DATE').setValue(null);
			    				getCurrentView().contentPanel.getForm().findField('RECAL_DATE').hide(true);
			    				getCurrentView().contentPanel.getForm().findField('RECAL_DATE').setValue(null);
			    			}
		    				getCurrentView().contentPanel.getForm().findField('REFUSE_RESON').hide(true);
		    				getCurrentView().contentPanel.getForm().findField('REFUSE_RESON').setValue(null);
		    			}
					}
	    	}},
			{name:'REFUSE_RESON',text:'<font color="red">*</font>拒绝原因',hidden:true,translateType:'REFUSE_REASON_CALL',editable:true},
			{name:"VISIT_DATE",text:"<font color='red'>*</font>拜访日期",dataType:'date'},
			{name:"RECAL_DATE",text:"<font color='red'>*</font>回拨日期",dataType:'date'},
			{name:'TEL_CONTACTER',text:'电访接洽人',translateType:'TEL_CONTACTER',editable:true},
			{name:'CUST_REVENUE',text:'公司营收',translateType:'CUST_REVENUE',editable:true},
			{name:'OTHERBANK_TRADE',text:'他行往来情况',maxLength:200},
			{name:'MARKT_PRODUCT',text:'拟营销产品',maxLength:50},
			{name:'MGR_ID',text:"MID",hidden:true},
			{name:'CUST_ID',text:"ID",hidden:true}
		],
		fn:function(CUST_NAME,CUS_OWNBUSI,IF_TARGETBUSI,LINKMAN,LINK_TEL,CUST_SOURCE,CALL_DATE,MGR_NAME,CALL_RESULT,REFUSE_RESON,VISIT_DATE,RECAL_DATE,TEL_CONTACTER,
			CUST_REVENUE,OTHERBANK_TRADE,MARKT_PRODUCT,MGR_ID,CUST_ID){
				CUST_NAME.readOnly= true;
				CUST_NAME.cls='x-readOnly';
				CUST_ID.hidden=true;
				MGR_ID.hidden=true;
				REFUSE_RESON.hidden=true;
			return [CUST_NAME,CUS_OWNBUSI,IF_TARGETBUSI,LINKMAN,LINK_TEL,CUST_SOURCE,CALL_DATE,MGR_NAME,CALL_RESULT,REFUSE_RESON,VISIT_DATE,RECAL_DATE,TEL_CONTACTER,
			CUST_REVENUE,OTHERBANK_TRADE,MARKT_PRODUCT,MGR_ID,CUST_ID];
		}
	},{
	   columnCount : 2,
	   fields:[
	   		{name:'TEL_CONTACTER_REMARK',text:'电访接洽人备注',xtype:'textarea',maxLength:200},
	   		{name:'CUST_REVENUE_REMARK',text:'公司营收备注',xtype:'textarea',maxLength:200},
	   		{name:'MARKT_PRODUCT_REMARK',text:'拟营销产品备注',xtype:'textarea',maxLength:200},
	   		{name:'CUST_SOURCE_REMARK',text:'客户来源备注',xtype:'textarea',maxLength:200},
	   		{name:'OTHER',text:'其他',xtype:'textarea',maxLength:200}],
	   fn:function(TEL_CONTACTER_REMARK,CUST_REVENUE_REMARK,MARKT_PRODUCT_REMARK,CUST_SOURCE_REMARK,OTHER){
//		   REFUSE_REASON.hidden = true;
	   		
		   return [TEL_CONTACTER_REMARK,CUST_REVENUE_REMARK,MARKT_PRODUCT_REMARK,CUST_SOURCE_REMARK,OTHER];
	   }
	}],
	formButtons : [
		{
		text: '保存',
		fn: function(formPanel,basicForm){
			if(!formPanel.getForm().isValid()){
				Ext.MessageBox.alert('提示','输入有误，请检查输入项!');
				return false;
			}
			var v = getCurrentView().contentPanel.getForm().findField('CALL_RESULT').getValue();
			if(v == '2'){
				if(getCurrentView().contentPanel.getForm().findField('REFUSE_RESON').getValue() == null||getCurrentView().contentPanel.getForm().findField('REFUSE_RESON').getValue() == ''){
					Ext.Msg.alert('提示','拒绝原因是必输项!');
					return false;
				}
			}else if(v == '3'){
				if(getCurrentView().contentPanel.getForm().findField('RECAL_DATE').getValue() == null||getCurrentView().contentPanel.getForm().findField('RECAL_DATE').getValue() == ''){
					Ext.Msg.alert('提示','回拨日期是必输项!');
					return false;
				}
			}
			var k = getCurrentView().contentPanel.getForm().findField('CUST_SOURCE').getValue();
			if(k == '11' || k == '20'){
				if(getCurrentView().contentPanel.getForm().findField('CUST_SOURCE_REMARK').getValue() == null||getCurrentView().contentPanel.getForm().findField('CUST_SOURCE_REMARK').getValue() == ''){
					Ext.Msg.alert('提示','客户来源备注是必输项!');
					return false;
				}
			}
			var data = formPanel.getForm().getFieldValues();
			var commintData = translateDataKey(data,1);
			if(commintData.callResult == 1 && commintData.visitDate == undefined ){
					Ext.MessageBox.alert('提示','请填写【拜访日期】');
					return false;
			}
			Ext.Ajax.request({
				url : basepath + '/ocrmFCallNewRecord!save.json',
				method : 'POST',
				params : commintData,
				success : function(response) {
						Ext.MessageBox.alert('提示','保存数据成功!');
						hideCurrentView(); 
						reloadCurrentData();
					}
				}); 
		}
		}
	]
},{
	title:'新客户修改',
	hideTitle:true,
	url:basepath + '/ocrmFCallNewRecord.json',
	type:'form',
	groups:[{
		labelWidth:150,
		columnCount : 2,
		fields:[
			{name:'CUST_NAME',text:'客户名称',allowBlank:false,custType:'1'},
			{name:"CUS_OWNBUSI",text:'所属行业',translateType:'CUS_OWNBUSI',allowBlank:false,editable:true},
			{name:"IF_TARGETBUSI",text:'是否为目标行业',translateType:'IF_FLAG',allowBlank:false,editable:true},
			{name:'LINKMAN',text:'联系人',dataType:'string',allowBlank:false,maxLength:50},
			{name:'LINK_TEL',text:'联系号码',vtype:'phoneNumber',allowBlank:false,maxLength:50},
			{name:'CUST_SOURCE',text:'客户来源',translateType:'CUST_SOURCE',allowBlank:false,editable:true},
			'CALL_DATE',
			'MGR_NAME',
			{name:'CALL_RESULT',text:'电访结果',allowBlank:false,editable:true,translateType:'CALL_RESULT',listeners:{
	    		select:function(combo,record){
		    			var v = this.getValue();
		    			if(v=='2'){
		    				getCurrentView().contentPanel.getForm().findField('REFUSE_RESON').show(true);
		    				getCurrentView().contentPanel.getForm().findField('VISIT_DATE').hide(true);
		    				getCurrentView().contentPanel.getForm().findField('VISIT_DATE').setValue('');
		    				getCurrentView().contentPanel.getForm().findField('RECAL_DATE').hide(true);
		    				getCurrentView().contentPanel.getForm().findField('RECAL_DATE').setValue('');
		    			}else{
		    				if(v == '1'){
			    				getCurrentView().contentPanel.getForm().findField('VISIT_DATE').show(true);
			    				getCurrentView().contentPanel.getForm().findField('RECAL_DATE').hide(true);
			    				getCurrentView().contentPanel.getForm().findField('RECAL_DATE').setValue(null);
			    			}else if(v == '3'){
			    				getCurrentView().contentPanel.getForm().findField('VISIT_DATE').hide(true);
			    				getCurrentView().contentPanel.getForm().findField('VISIT_DATE').setValue(null);
			    				getCurrentView().contentPanel.getForm().findField('RECAL_DATE').show(true);
			    			}else{
			    				getCurrentView().contentPanel.getForm().findField('VISIT_DATE').hide(true);
			    				getCurrentView().contentPanel.getForm().findField('VISIT_DATE').setValue(null);
			    				getCurrentView().contentPanel.getForm().findField('RECAL_DATE').hide(true);
			    				getCurrentView().contentPanel.getForm().findField('RECAL_DATE').setValue(null);
			    			}
		    				getCurrentView().contentPanel.getForm().findField('REFUSE_RESON').hide(true);
		    				getCurrentView().contentPanel.getForm().findField('REFUSE_RESON').setValue(null);
		    			}
		    			
					}
	    	}},
			{name:'REFUSE_RESON',text:'<font color="red">*</font>拒绝原因',hidden:true,translateType:'REFUSE_REASON_CALL',editable:true},
			{name:"VISIT_DATE",text:"<font color='red'>*</font>拜访日期",dataType:'date'},
			{name:"RECAL_DATE",text:"<font color='red'>*</font>回拨日期",dataType:'date'},
			{name:'TEL_CONTACTER',text:'电访接洽人',translateType:'TEL_CONTACTER',editable:true},
			{name:'CUST_REVENUE',text:'公司营收',translateType:'CUST_REVENUE',editable:true},
			{name:'OTHERBANK_TRADE',text:'他行往来情况',maxLength:200},
			{name:'MARKT_PRODUCT',text:'拟营销产品',maxLength:200},
			{name:'MGR_ID',text:"MID",hidden:true},
			{name:'CUST_ID',text:"ID",hidden:true},
			{name:'ID',text:'PK_ID',hidden:true}
		],
		fn:function(CUST_NAME,CUS_OWNBUSI,IF_TARGETBUSI,LINKMAN,LINK_TEL,CUST_SOURCE,CALL_DATE,MGR_NAME,CALL_RESULT,REFUSE_RESON,VISIT_DATE,RECAL_DATE,TEL_CONTACTER,
			CUST_REVENUE,OTHERBANK_TRADE,MARKT_PRODUCT,MGR_ID,CUST_ID,ID){
				CUST_NAME.readOnly= true;
				CUST_NAME.cls='x-readOnly';
				CUST_ID.hidden=true;
				MGR_ID.hidden=true;
				ID.hidden=true;
				REFUSE_RESON.hidden=true;
			return [CUST_NAME,CUS_OWNBUSI,IF_TARGETBUSI,LINKMAN,LINK_TEL,CUST_SOURCE,CALL_DATE,MGR_NAME,CALL_RESULT,REFUSE_RESON,VISIT_DATE,RECAL_DATE,TEL_CONTACTER,
			CUST_REVENUE,OTHERBANK_TRADE,MARKT_PRODUCT,MGR_ID,CUST_ID,ID];
		}
	},{
	   columnCount : 2,
	   fields:[
	   		{name:'TEL_CONTACTER_REMARK',text:'电访接洽人备注',xtype:'textarea',maxLength:200},
	   		{name:'CUST_REVENUE_REMARK',text:'公司营收备注',xtype:'textarea',maxLength:200},
	   		{name:'MARKT_PRODUCT_REMARK',text:'拟营销产品备注',xtype:'textarea',maxLength:200},
	   		{name:'CUST_SOURCE_REMARK',text:'客户来源备注',xtype:'textarea',maxLength:200},
	   		{name:'OTHER',text:'其他',xtype:'textarea',maxLength:200}],
	   fn:function(TEL_CONTACTER_REMARK,CUST_REVENUE_REMARK,MARKT_PRODUCT_REMARK,CUST_SOURCE_REMARK,OTHER){
	   		
		   return [TEL_CONTACTER_REMARK,CUST_REVENUE_REMARK,MARKT_PRODUCT_REMARK,CUST_SOURCE_REMARK,OTHER];
	   }
	}
	],formButtons : [
		{
			text: '保存',
			fn: function(formPanel,basicForm){
				if(!formPanel.getForm().isValid()){
					Ext.MessageBox.alert('提示','输入有误，请检查输入项!');
					return false;
				}
				var v = getCurrentView().contentPanel.getForm().findField('CALL_RESULT').getValue();
				if(v == '2'){
					if(getCurrentView().contentPanel.getForm().findField('REFUSE_RESON').getValue() == null||getCurrentView().contentPanel.getForm().findField('REFUSE_RESON').getValue() == ''){
						Ext.Msg.alert('提示','拒绝原因是必输项!');
						return false;
					}
				}else if(v == '3'){
					if(getCurrentView().contentPanel.getForm().findField('RECAL_DATE').getValue() == null||getCurrentView().contentPanel.getForm().findField('RECAL_DATE').getValue() == ''){
						Ext.Msg.alert('提示','回拨日期是必输项!');
						return false;
					}
				}
				var k = getCurrentView().contentPanel.getForm().findField('CUST_SOURCE').getValue();
				if(k == '11' || k == '20'){
					if(getCurrentView().contentPanel.getForm().findField('CUST_SOURCE_REMARK').getValue() == null||getCurrentView().contentPanel.getForm().findField('CUST_SOURCE_REMARK').getValue() == ''){
						Ext.Msg.alert('提示','客户来源备注是必输项!');
						return false;
					}
				}
				var data = formPanel.getForm().getFieldValues();
				var commintData = translateDataKey(data,1);
				if(commintData.callResult == 1 && commintData.visitDate == undefined ){
					Ext.MessageBox.alert('提示','请填写【拜访日期】');
					return false;
				}
				Ext.Ajax.request({
					url : basepath + '/ocrmFCallNewRecord!save.json',
					method : 'POST',
					params : commintData,
					success : function(response) {
							Ext.MessageBox.alert('提示','保存数据成功!');
							hideCurrentView(); 
							reloadCurrentData();
						}
					}); 
		}
		}
	]

},{
	
	title:'新客户详情',
	hideTitle:true,
	type:'form',
	groups:[{
		labelWidth:150,
		columnCount : 2,
		fields:[
			{name:'CUST_NAME',text:'客户名称',allowBlank:false,custType:'1'},
			{name:"CUS_OWNBUSI",text:'所属行业',translateType:'CUS_OWNBUSI',allowBlank:false,editable:true},
			{name:"IF_TARGETBUSI",text:'是否为目标行业',translateType:'IF_FLAG',allowBlank:false,editable:true},
			{name:'LINKMAN',text:'联系人',dataType:'string',allowBlank:false},
			{name:'LINK_TEL',text:'联系号码',vtype:'phoneNumber',allowBlank:false},
			{name:'CUST_SOURCE',text:'客户来源',translateType:'CUST_SOURCE',allowBlank:false,editable:true},
			'CALL_DATE',
			'MGR_NAME',
			{name:'CALL_RESULT',text:'电访结果',allowBlank:false,translateType:'CALL_RESULT',editable:true},
			{name:"VISIT_DATE",text:"拜访日期",dataType:'date'},
			{name:"RECAL_DATE",text:"回拨日期",dataType:'date'},
			{name:'REFUSE_RESON',text:'拒绝原因',hidden:true,allowBlank:false,translateType:'REFUSE_REASON_CALL',editable:true},
			{name:'TEL_CONTACTER',text:'电访接洽人',translateType:'TEL_CONTACTER',editable:true},
			{name:'CUST_REVENUE',text:'公司营收',translateType:'CUST_REVENUE',editable:true},
			{name:'OTHERBANK_TRADE',text:'他行往来情况'},
			{name:'MARKT_PRODUCT',text:'拟营销产品'},
			{name:'MGR_ID',text:"MID",hidden:true},
			{name:'CUST_ID',text:"ID",hidden:true},
			{name:'ID',text:'PK_ID',hidden:true}
		],
		fn:function(CUST_NAME,CUS_OWNBUSI,IF_TARGETBUSI,LINKMAN,LINK_TEL,CUST_SOURCE,CALL_DATE,MGR_NAME,CALL_RESULT,VISIT_DATE,RECAL_DATE,REFUSE_RESON,TEL_CONTACTER,
			CUST_REVENUE,OTHERBANK_TRADE,MARKT_PRODUCT,MGR_ID,CUST_ID,ID){
				CUST_NAME.readOnly= true;
				CUST_NAME.cls='x-readOnly';
				CUS_OWNBUSI.readOnly= true;
				CUS_OWNBUSI.cls='x-readOnly';
				IF_TARGETBUSI.readOnly= true;
				IF_TARGETBUSI.cls='x-readOnly';
				LINKMAN.readOnly= true;
				LINKMAN.cls='x-readOnly';
				LINK_TEL.readOnly= true;
				LINK_TEL.cls='x-readOnly';
				CUST_SOURCE.readOnly= true;
				CUST_SOURCE.cls='x-readOnly';
				CALL_DATE.readOnly= true;
				CALL_DATE.cls='x-readOnly';
				MGR_NAME.readOnly= true;
				MGR_NAME.cls='x-readOnly';
				CALL_RESULT.readOnly= true;
				CALL_RESULT.cls='x-readOnly';
				TEL_CONTACTER.readOnly= true;
				TEL_CONTACTER.cls='x-readOnly';
				CUST_REVENUE.readOnly= true;
				CUST_REVENUE.cls='x-readOnly';
				OTHERBANK_TRADE.readOnly= true;
				OTHERBANK_TRADE.cls='x-readOnly';
				MARKT_PRODUCT.readOnly= true;
				MARKT_PRODUCT.cls='x-readOnly';
				CUST_ID.hidden=true;
				MGR_ID.hidden=true;
				ID.hidden=true;
				REFUSE_RESON.hidden=true;
				REFUSE_RESON.readOnly= true;
				REFUSE_RESON.cls='x-readOnly';
				VISIT_DATE.readOnly= true;
				VISIT_DATE.cls='x-readOnly';
				RECAL_DATE.readOnly= true;
				RECAL_DATE.cls='x-readOnly';
			return [CUST_NAME,CUS_OWNBUSI,IF_TARGETBUSI,LINKMAN,LINK_TEL,CUST_SOURCE,CALL_DATE,MGR_NAME,CALL_RESULT,REFUSE_RESON,VISIT_DATE,RECAL_DATE,TEL_CONTACTER,
			CUST_REVENUE,OTHERBANK_TRADE,MARKT_PRODUCT,MGR_ID,CUST_ID,ID];
		}
	},{
	   columnCount : 2,
	   fields:[
	   		{name:'TEL_CONTACTER_REMARK',text:'电访接洽人备注',xtype:'textarea'},
	   		{name:'CUST_REVENUE_REMARK',text:'公司营收备注',xtype:'textarea'},
	   		{name:'MARKT_PRODUCT_REMARK',text:'拟营销产品备注',xtype:'textarea'},
	   		{name:'CUST_SOURCE_REMARK',text:'客户来源备注',xtype:'textarea'},
	   		{name:'OTHER',text:'其他',xtype:'textarea'}],
	   fn:function(TEL_CONTACTER_REMARK,CUST_REVENUE_REMARK,MARKT_PRODUCT_REMARK,CUST_SOURCE_REMARK,OTHER){
	   			TEL_CONTACTER_REMARK.readOnly= true;
				TEL_CONTACTER_REMARK.cls='x-readOnly';
				CUST_REVENUE_REMARK.readOnly= true;
				CUST_REVENUE_REMARK.cls='x-readOnly';
				MARKT_PRODUCT_REMARK.readOnly= true;
				MARKT_PRODUCT_REMARK.cls='x-readOnly';
				CUST_SOURCE_REMARK.readOnly= true;
				CUST_SOURCE_REMARK.cls='x-readOnly';
				OTHER.readOnly= true;
				OTHER.cls='x-readOnly';
		   return [TEL_CONTACTER_REMARK,CUST_REVENUE_REMARK,MARKT_PRODUCT_REMARK,CUST_SOURCE_REMARK,OTHER];
	   }
	}
	]
},{
	title:'旧客户新增',
	hideTitle:true,
	type:'form',
	groups:[{
		labelWidth:150,
		columnCount : 2,
		fields:[
			{name:'CUST_NAME',text:'客户名称',allowBlank:false,custType:'1'},
			{name:'RESPONDENTS_POSITION',text:'接洽人职位',maxLength:50},
			{name:'RESPONDENTS_NAME',text:'接洽人名称',maxLength:50},
			{name:'RESPONDENTS_CONTACT',text:'接洽人联系方式',dataType:'string',maxLength:50},
			{name:'BANK_PARTICIPANTS',text:'本行参加人员',dataType:'string',maxLength:100},
			{name:'CALL_PURPOSE',text:'电访目的',translateType:'CALL_PURPOSE',multiSelect:true,editable:true},
			'CALL_DATE',
			'MGR_NAME',
			{name:'CUST_BUSI_CONDITION',text:'客户营运状况',translateType:'CUST_BUSI_CONDITION',editable:true},
			{name:'MAIN_BUSI_CHANGE',text:'主营业务是否变更',translateType:'IF_FLAG',editable:true},
			{name:'REVENUE_CHANGE',text:'营收是否大幅变化',translateType:'IF_FLAG',editable:true},
			{name:'PROFI_CHANGE',text:'获利率是否大幅变化',translateType:'IF_FLAG',editable:true},
			{name:'MAIN_SUPPLIER_CHANGE',text:'主要供应商是否调整',translateType:'IF_FLAG',editable:true},
			{name:'MAIN_BUYER_CHANGE',text:'主要买方是否调整',translateType:'IF_FLAG',editable:true},
			{name:'EQUITY_STRUC_CHANGE',text:'股权结构是否变更',translateType:'IF_FLAG',editable:true},
			{name:'MANAGEMENT_CHANGE',text:'经营层是否有变更',translateType:'IF_FLAG',editable:true},
			{name:'COLLATERAL_CONDITION',text:'担保品状况',translateType:'COLLATERAL_CONDITION',editable:true},
			{name:'COOPERATION_CHANGE',text:'与银行合作状况是否有变化',translateType:'COLLATERAL_CONDITION',editable:true},
			{name:'IF_PRECONTRACT',text:'是否预约拜访',translateType:'IF_FLAG',editable:true,listeners:{
	    		select:function(combo,record){
	    			var v = this.getValue();
	    			if(v=='0'){
	    				getCurrentView().contentPanel.getForm().findField('VISIT_DATE').hide(true);
	    				getCurrentView().contentPanel.getForm().findField('VISIT_DATE').setValue('');
	    			}else{
		    			getCurrentView().contentPanel.getForm().findField('VISIT_DATE').show(true);
	    			}
				}
    	    }},
    	    {name:"VISIT_DATE",text:"<font color='red'>*</font>拜访日期",dataType:'date'},
			{name:'MARKT_RESULT',text:'营销结果',maxLength:200},
			{name:'MARKT_PRODUCT',text:'本次拜访拟营销产品',maxLength:200},
			{name:'MGR_ID',text:"MID",hidden:true},
			{name:'CUST_ID',text:"ID",hidden:true}
		],
		fn:function(CUST_NAME,RESPONDENTS_POSITION,RESPONDENTS_NAME,RESPONDENTS_CONTACT,BANK_PARTICIPANTS,CALL_PURPOSE,CALL_DATE,MGR_NAME,
			CUST_BUSI_CONDITION,MAIN_BUSI_CHANGE,REVENUE_CHANGE,PROFI_CHANGE,MAIN_SUPPLIER_CHANGE,MAIN_BUYER_CHANGE,EQUITY_STRUC_CHANGE,
			MANAGEMENT_CHANGE,COLLATERAL_CONDITION,COOPERATION_CHANGE,IF_PRECONTRACT,VISIT_DATE,MARKT_RESULT,MARKT_PRODUCT,MGR_ID,CUST_ID){
				CUST_NAME.readOnly= true;
				CUST_NAME.cls='x-readOnly';
				CUST_ID.hidden=true;
				MGR_ID.hidden=true;
			return [CUST_NAME,RESPONDENTS_POSITION,RESPONDENTS_NAME,RESPONDENTS_CONTACT,BANK_PARTICIPANTS,CALL_PURPOSE,CALL_DATE,MGR_NAME,
			CUST_BUSI_CONDITION,MAIN_BUSI_CHANGE,REVENUE_CHANGE,PROFI_CHANGE,MAIN_SUPPLIER_CHANGE,MAIN_BUYER_CHANGE,EQUITY_STRUC_CHANGE,
			MANAGEMENT_CHANGE,COLLATERAL_CONDITION,COOPERATION_CHANGE,IF_PRECONTRACT,VISIT_DATE,MARKT_RESULT,MARKT_PRODUCT,MGR_ID,CUST_ID];
		}
	},{
	   labelWidth:150,
	   columnCount : 2,
	   fields:[
	   		{name:'CALL_REASON',text:'电访目的补充说明',xtype:'textarea',maxLength:500},
	   		{name:'MAIN_BUSI_CHANGE_REMARK',text:'主营业务是否变更说明',xtype:'textarea',maxLength:200},
	   		{name:'REVENUE_CHANGE_REMARK',text:'营收是否大幅变化说明',xtype:'textarea',maxLength:200},
	   		{name:'PROFI_CHANGE_REMARK',text:'获利率是否大幅变化说明',xtype:'textarea',maxLength:200},
	   		{name:'MAIN_SUPPLIER_CHANGE_REMARK',text:'主要供应商是否调整说明',xtype:'textarea',maxLength:200},
	   		{name:'MAIN_BUYER_CHANGE_REMARK',text:'主要买方是否调整说明',xtype:'textarea',maxLength:200},
	   		{name:'EQUITY_STRUC_CHANGE_REMARK',text:'股权结构是否变更说明',xtype:'textarea',maxLength:200},
	   		{name:'MANAGEMENT_CHANGE_REMARK',text:'经营层是否有变更说明',xtype:'textarea',maxLength:200},
	   		{name:'COLLATERAL_CONDITION_REMARK',text:'担保品状况说明',xtype:'textarea',maxLength:200},
	   		{name:'COOPERATION_CHANGE_REMARK',text:'与银行合作状况是否有变化说明',xtype:'textarea',maxLength:200},
	   		{name:'OTHER',text:'其他补充说明',xtype:'textarea',maxLength:500},
	   		{name:'MATTERS_FOLLOW',text:'跟进事项',xtype:'textarea',maxLength:500}],
	   fn:function(CALL_REASON,MAIN_BUSI_CHANGE_REMARK,REVENUE_CHANGE_REMARK,PROFI_CHANGE_REMARK,MAIN_SUPPLIER_CHANGE_REMARK,
	   				MAIN_BUYER_CHANGE_REMARK,EQUITY_STRUC_CHANGE_REMARK,MANAGEMENT_CHANGE_REMARK,COLLATERAL_CONDITION_REMARK,
	   				COOPERATION_CHANGE_REMARK,OTHER,MATTERS_FOLLOW){
	   		
		   return [CALL_REASON,MAIN_BUSI_CHANGE_REMARK,REVENUE_CHANGE_REMARK,PROFI_CHANGE_REMARK,MAIN_SUPPLIER_CHANGE_REMARK,
	   				MAIN_BUYER_CHANGE_REMARK,EQUITY_STRUC_CHANGE_REMARK,MANAGEMENT_CHANGE_REMARK,COLLATERAL_CONDITION_REMARK,
	   				COOPERATION_CHANGE_REMARK,OTHER,MATTERS_FOLLOW];
	   }
	}
	],formButtons : [{
			text: '保存',
			fn: function(formPanel,basicForm){
				if(!formPanel.getForm().isValid()){
					Ext.MessageBox.alert('提示','输入有误，请检查输入项!');
					return false;
				}
				var data = formPanel.getForm().getFieldValues();
				var commintData = translateDataKey(data,1);
				if(commintData.ifPrecontract == 1 && commintData.visitDate == undefined ){
					Ext.MessageBox.alert('提示','请填写【拜访日期】');
					return false;
				}
				Ext.Ajax.request({
					url : basepath + '/mktCallOldFRecord!save.json',
					method : 'POST',
					params : commintData,
					success : function(response) {
							Ext.MessageBox.alert('提示','保存数据成功!');
							hideCurrentView(); 
							reloadCurrentData();
						}
					}); 
		}
		}]
},{
	title:'旧客户修改',
	hideTitle:true,
	type:'form',
	groups:[{
		labelWidth:150,
		columnCount : 2,
		fields:[
			{name:'CUST_NAME',text:'客户名称',allowBlank:false,custType:'1'},
			{name:'RESPONDENTS_POSITION',text:'接洽人职位',maxLength:50},
			{name:'RESPONDENTS_NAME',text:'接洽人名称',maxLength:50},
			{name:'RESPONDENTS_CONTACT',text:'接洽人联系方式',dataType:'string',maxLength:50},
			{name:'BANK_PARTICIPANTS',text:'本行参加人员',dataType:'string',maxLength:100},
			{name:'CALL_PURPOSE',text:'电访目的',translateType:'CALL_PURPOSE',multiSelect:true,editable:true},
			'CALL_DATE',
			'MGR_NAME',
			{name:'CUST_BUSI_CONDITION',text:'客户营运状况',translateType:'CUST_BUSI_CONDITION',editable:true},
			{name:'MAIN_BUSI_CHANGE',text:'主营业务是否变更',translateType:'IF_FLAG',editable:true},
			{name:'REVENUE_CHANGE',text:'营收是否大幅变化',translateType:'IF_FLAG',editable:true},
			{name:'PROFI_CHANGE',text:'获利率是否大幅变化',translateType:'IF_FLAG',editable:true},
			{name:'MAIN_SUPPLIER_CHANGE',text:'主要供应商是否调整',translateType:'IF_FLAG',editable:true},
			{name:'MAIN_BUYER_CHANGE',text:'主要买方是否调整',translateType:'IF_FLAG',editable:true},
			{name:'EQUITY_STRUC_CHANGE',text:'股权结构是否变更',translateType:'IF_FLAG',editable:true},
			{name:'MANAGEMENT_CHANGE',text:'经营层是否有变更',translateType:'IF_FLAG',editable:true},
			{name:'COLLATERAL_CONDITION',text:'担保品状况',translateType:'COLLATERAL_CONDITION',editable:true},
			{name:'COOPERATION_CHANGE',text:'与银行合作状况是否有变化',translateType:'COLLATERAL_CONDITION',editable:true},
			{name:'IF_PRECONTRACT',text:'是否预约拜访',translateType:'IF_FLAG',editable:true,listeners:{
	    		select:function(combo,record){
	    			var v = this.getValue();
	    			if(v=='0'){
	    				getCurrentView().contentPanel.getForm().findField('VISIT_DATE').hide(true);
	    				getCurrentView().contentPanel.getForm().findField('VISIT_DATE').setValue('');
	    			}else{
		    			getCurrentView().contentPanel.getForm().findField('VISIT_DATE').show(true);
	    			}
				}
    	    }},
			{name:"VISIT_DATE",text:"<font color='red'>*</font>拜访日期",dataType:'date'},
			{name:'MARKT_RESULT',text:'营销结果',maxLength:200},
			{name:'MARKT_PRODUCT',text:'本次拜访拟营销产品',maxLength:200},
			{name:'MGR_ID',text:"MID",hidden:true},
			{name:'CUST_ID',text:"ID",hidden:true},
			{name:'ID',text:"PK_ID",hidden:true}
		],
		fn:function(CUST_NAME,RESPONDENTS_POSITION,RESPONDENTS_NAME,RESPONDENTS_CONTACT,BANK_PARTICIPANTS,CALL_PURPOSE,CALL_DATE,MGR_NAME,
			CUST_BUSI_CONDITION,MAIN_BUSI_CHANGE,REVENUE_CHANGE,PROFI_CHANGE,MAIN_SUPPLIER_CHANGE,MAIN_BUYER_CHANGE,EQUITY_STRUC_CHANGE,
			MANAGEMENT_CHANGE,COLLATERAL_CONDITION,COOPERATION_CHANGE,IF_PRECONTRACT,VISIT_DATE,MARKT_RESULT,MARKT_PRODUCT,MGR_ID,CUST_ID,ID){
				CUST_NAME.readOnly= true;
				CUST_NAME.cls='x-readOnly';
				CUST_ID.hidden=true;
				MGR_ID.hidden=true;
				ID.hidden=true;
			return [CUST_NAME,RESPONDENTS_POSITION,RESPONDENTS_NAME,RESPONDENTS_CONTACT,BANK_PARTICIPANTS,CALL_PURPOSE,CALL_DATE,MGR_NAME,
			CUST_BUSI_CONDITION,MAIN_BUSI_CHANGE,REVENUE_CHANGE,PROFI_CHANGE,MAIN_SUPPLIER_CHANGE,MAIN_BUYER_CHANGE,EQUITY_STRUC_CHANGE,
			MANAGEMENT_CHANGE,COLLATERAL_CONDITION,COOPERATION_CHANGE,IF_PRECONTRACT,VISIT_DATE,MARKT_RESULT,MARKT_PRODUCT,MGR_ID,CUST_ID,ID];
		}
	},{
	   labelWidth:180,
	   columnCount : 2,
	   fields:[
	   		{name:'CALL_REASON',text:'电访目的补充说明',xtype:'textarea',maxLength:500},
	   		{name:'MAIN_BUSI_CHANGE_REMARK',text:'主营业务是否变更说明',xtype:'textarea',maxLength:200},
	   		{name:'REVENUE_CHANGE_REMARK',text:'营收是否大幅变化说明',xtype:'textarea',maxLength:200},
	   		{name:'PROFI_CHANGE_REMARK',text:'获利率是否大幅变化说明',xtype:'textarea',maxLength:200},
	   		{name:'MAIN_SUPPLIER_CHANGE_REMARK',text:'主要供应商是否调整说明',xtype:'textarea',maxLength:200},
	   		{name:'MAIN_BUYER_CHANGE_REMARK',text:'主要买方是否调整说明',xtype:'textarea',maxLength:200},
	   		{name:'EQUITY_STRUC_CHANGE_REMARK',text:'股权结构是否变更说明',xtype:'textarea',maxLength:200},
	   		{name:'MANAGEMENT_CHANGE_REMARK',text:'经营层是否有变更说明',xtype:'textarea',maxLength:200},
	   		{name:'COLLATERAL_CONDITION_REMARK',text:'担保品状况说明',xtype:'textarea',maxLength:200},
	   		{name:'COOPERATION_CHANGE_REMARK',text:'与银行合作状况是否有变化说明',xtype:'textarea',maxLength:200},
	   		{name:'OTHER',text:'其他补充说明',xtype:'textarea',maxLength:500},
	   		{name:'MATTERS_FOLLOW',text:'跟进事项',xtype:'textarea',maxLength:500}],
	   fn:function(CALL_REASON,MAIN_BUSI_CHANGE_REMARK,REVENUE_CHANGE_REMARK,PROFI_CHANGE_REMARK,MAIN_SUPPLIER_CHANGE_REMARK,
	   				MAIN_BUYER_CHANGE_REMARK,EQUITY_STRUC_CHANGE_REMARK,MANAGEMENT_CHANGE_REMARK,COLLATERAL_CONDITION_REMARK,
	   				COOPERATION_CHANGE_REMARK,OTHER,MATTERS_FOLLOW){
	   		
		   return [CALL_REASON,MAIN_BUSI_CHANGE_REMARK,REVENUE_CHANGE_REMARK,PROFI_CHANGE_REMARK,MAIN_SUPPLIER_CHANGE_REMARK,
	   				MAIN_BUYER_CHANGE_REMARK,EQUITY_STRUC_CHANGE_REMARK,MANAGEMENT_CHANGE_REMARK,COLLATERAL_CONDITION_REMARK,
	   				COOPERATION_CHANGE_REMARK,OTHER,MATTERS_FOLLOW];
	   }
	}
	],formButtons : [
		{
			text: '保存',
			fn: function(formPanel,basicForm){
				if(!formPanel.getForm().isValid()){
					Ext.MessageBox.alert('提示','输入有误，请检查输入项!');
					return false;
				}
				var data = formPanel.getForm().getFieldValues();
				var commintData = translateDataKey(data,1);
				if(commintData.ifPrecontract == 1 && commintData.visitDate == undefined ){
					Ext.MessageBox.alert('提示','请填写【拜访日期】');
					return false;
				}
				Ext.Ajax.request({
					url : basepath + '/mktCallOldFRecord!save.json',
					method : 'POST',
					params : commintData,
					success : function(response) {
							Ext.MessageBox.alert('提示','保存数据成功!');
							hideCurrentView(); 
							reloadCurrentData();
						}
					}); 
		}
	}
	]

},{
	
	title:'旧客户详情',
	hideTitle:true,
	type:'form',
	groups:[{
		labelWidth:150,
		columnCount : 2,
		fields:[
			{name:'CUST_NAME',text:'客户名称',allowBlank:false,custType:'1'},
			{name:'RESPONDENTS_POSITION',text:'接洽人职位'},
			{name:'RESPONDENTS_NAME',text:'接洽人名称'},
			{name:'RESPONDENTS_CONTACT',text:'接洽人联系方式',dataType:'string'},
			{name:'BANK_PARTICIPANTS',text:'本行参加人员',dataType:'string'},
			{name:'CALL_PURPOSE',text:'电访目的',translateType:'CALL_PURPOSE',multiSelect:true,editable:true},
			'CALL_DATE',
			'MGR_NAME',
			{name:'CUST_BUSI_CONDITION',text:'客户营运状况',translateType:'CUST_BUSI_CONDITION',editable:true},
			{name:'MAIN_BUSI_CHANGE',text:'主营业务是否变更',translateType:'IF_FLAG',editable:true},
			{name:'REVENUE_CHANGE',text:'营收是否大幅变化',translateType:'IF_FLAG',editable:true},
			{name:'PROFI_CHANGE',text:'获利率是否大幅变化',translateType:'IF_FLAG',editable:true},
			{name:'MAIN_SUPPLIER_CHANGE',text:'主要供应商是否调整',translateType:'IF_FLAG',editable:true},
			{name:'MAIN_BUYER_CHANGE',text:'主要买方是否调整',translateType:'IF_FLAG',editable:true},
			{name:'EQUITY_STRUC_CHANGE',text:'股权结构是否变更',translateType:'IF_FLAG',editable:true},
			{name:'MANAGEMENT_CHANGE',text:'经营层是否有变更',translateType:'IF_FLAG',editable:true},
			{name:'COLLATERAL_CONDITION',text:'担保品状况',translateType:'COLLATERAL_CONDITION',editable:true},
			{name:'COOPERATION_CHANGE',text:'与银行合作状况是否有变化',translateType:'COLLATERAL_CONDITION',editable:true},
			{name:'IF_PRECONTRACT',text:'是否预约拜访',translateType:'IF_FLAG',editable:true},
			{name:"VISIT_DATE",text:"拜访日期",dataType:'date'},
			{name:'MARKT_RESULT',text:'营销结果'},
			{name:'MARKT_PRODUCT',text:'本次拜访拟营销产品'},
			{name:'MGR_ID',text:"MID",hidden:true},
			{name:'CUST_ID',text:"ID",hidden:true},
			{name:'ID',text:"PK_ID",hidden:true}
		],
		fn:function(CUST_NAME,RESPONDENTS_POSITION,RESPONDENTS_NAME,RESPONDENTS_CONTACT,BANK_PARTICIPANTS,CALL_PURPOSE,CALL_DATE,MGR_NAME,
			CUST_BUSI_CONDITION,MAIN_BUSI_CHANGE,REVENUE_CHANGE,PROFI_CHANGE,MAIN_SUPPLIER_CHANGE,MAIN_BUYER_CHANGE,EQUITY_STRUC_CHANGE,
			MANAGEMENT_CHANGE,COLLATERAL_CONDITION,COOPERATION_CHANGE,IF_PRECONTRACT,VISIT_DATE,MARKT_RESULT,MARKT_PRODUCT,MGR_ID,CUST_ID,ID){
				CUST_NAME.readOnly= true;
				CUST_NAME.cls='x-readOnly';
				RESPONDENTS_POSITION.readOnly= true;
				RESPONDENTS_POSITION.cls='x-readOnly';
				RESPONDENTS_NAME.readOnly= true;
				RESPONDENTS_NAME.cls='x-readOnly';
				RESPONDENTS_CONTACT.readOnly= true;
				RESPONDENTS_CONTACT.cls='x-readOnly';
				BANK_PARTICIPANTS.readOnly= true;
				BANK_PARTICIPANTS.cls='x-readOnly';
				CALL_PURPOSE.readOnly= true;
				CALL_PURPOSE.cls='x-readOnly';
				CALL_DATE.readOnly= true;
				CALL_DATE.cls='x-readOnly';
				MGR_NAME.readOnly= true;
				MGR_NAME.cls='x-readOnly';
				CUST_BUSI_CONDITION.readOnly= true;
				CUST_BUSI_CONDITION.cls='x-readOnly';
				MAIN_BUSI_CHANGE.readOnly= true;
				MAIN_BUSI_CHANGE.cls='x-readOnly';
				REVENUE_CHANGE.readOnly= true;
				REVENUE_CHANGE.cls='x-readOnly';
				PROFI_CHANGE.readOnly= true;
				PROFI_CHANGE.cls='x-readOnly';
				MAIN_SUPPLIER_CHANGE.readOnly= true;
				MAIN_SUPPLIER_CHANGE.cls='x-readOnly';
				MAIN_BUYER_CHANGE.readOnly= true;
				MAIN_BUYER_CHANGE.cls='x-readOnly';
				EQUITY_STRUC_CHANGE.readOnly= true;
				EQUITY_STRUC_CHANGE.cls='x-readOnly';
				MANAGEMENT_CHANGE.readOnly= true;
				MANAGEMENT_CHANGE.cls='x-readOnly';
				COLLATERAL_CONDITION.readOnly= true;
				COLLATERAL_CONDITION.cls='x-readOnly';
				COOPERATION_CHANGE.readOnly= true;
				COOPERATION_CHANGE.cls='x-readOnly';
				IF_PRECONTRACT.readOnly= true;
				IF_PRECONTRACT.cls='x-readOnly';
				MARKT_RESULT.readOnly= true;
				MARKT_RESULT.cls='x-readOnly';
				VISIT_DATE.readOnly= true;
				VISIT_DATE.cls='x-readOnly';
				MARKT_PRODUCT.readOnly= true;
				MARKT_PRODUCT.cls='x-readOnly';
				CUST_ID.hidden=true;
				MGR_ID.hidden=true;
				ID.hidden=true;
			return [CUST_NAME,RESPONDENTS_POSITION,RESPONDENTS_NAME,RESPONDENTS_CONTACT,BANK_PARTICIPANTS,CALL_PURPOSE,CALL_DATE,MGR_NAME,
			CUST_BUSI_CONDITION,MAIN_BUSI_CHANGE,REVENUE_CHANGE,PROFI_CHANGE,MAIN_SUPPLIER_CHANGE,MAIN_BUYER_CHANGE,EQUITY_STRUC_CHANGE,
			MANAGEMENT_CHANGE,COLLATERAL_CONDITION,COOPERATION_CHANGE,IF_PRECONTRACT,VISIT_DATE,MARKT_RESULT,MARKT_PRODUCT,MGR_ID,CUST_ID,ID];
		}
	},{
	   labelWidth:180,
	   columnCount : 2,
	   fields:[
	   		{name:'CALL_REASON',text:'电访目的补充说明',xtype:'textarea'},
	   		{name:'MAIN_BUSI_CHANGE_REMARK',text:'主营业务是否变更说明',xtype:'textarea'},
	   		{name:'REVENUE_CHANGE_REMARK',text:'营收是否大幅变化说明',xtype:'textarea'},
	   		{name:'PROFI_CHANGE_REMARK',text:'获利率是否大幅变化说明',xtype:'textarea'},
	   		{name:'MAIN_SUPPLIER_CHANGE_REMARK',text:'主要供应商是否调整说明',xtype:'textarea'},
	   		{name:'MAIN_BUYER_CHANGE_REMARK',text:'主要买方是否调整说明',xtype:'textarea'},
	   		{name:'EQUITY_STRUC_CHANGE_REMARK',text:'股权结构是否变更说明',xtype:'textarea'},
	   		{name:'MANAGEMENT_CHANGE_REMARK',text:'经营层是否有变更说明',xtype:'textarea'},
	   		{name:'COLLATERAL_CONDITION_REMARK',text:'担保品状况说明',xtype:'textarea'},
	   		{name:'COOPERATION_CHANGE_REMARK',text:'与银行合作状况是否有变化说明',xtype:'textarea'},
	   		{name:'OTHER',text:'其他补充说明',xtype:'textarea'},
	   		{name:'MATTERS_FOLLOW',text:'跟进事项',xtype:'textarea'}],
	   fn:function(CALL_REASON,MAIN_BUSI_CHANGE_REMARK,REVENUE_CHANGE_REMARK,PROFI_CHANGE_REMARK,MAIN_SUPPLIER_CHANGE_REMARK,
	   				MAIN_BUYER_CHANGE_REMARK,EQUITY_STRUC_CHANGE_REMARK,MANAGEMENT_CHANGE_REMARK,COLLATERAL_CONDITION_REMARK,
	   				COOPERATION_CHANGE_REMARK,OTHER,MATTERS_FOLLOW){
	   			CALL_REASON.readOnly= true;
				CALL_REASON.cls='x-readOnly';
				MAIN_BUSI_CHANGE_REMARK.readOnly= true;
				MAIN_BUSI_CHANGE_REMARK.cls='x-readOnly';
				REVENUE_CHANGE_REMARK.readOnly= true;
				REVENUE_CHANGE_REMARK.cls='x-readOnly';
				PROFI_CHANGE_REMARK.readOnly= true;
				PROFI_CHANGE_REMARK.cls='x-readOnly';
				MAIN_SUPPLIER_CHANGE_REMARK.readOnly= true;
				MAIN_SUPPLIER_CHANGE_REMARK.cls='x-readOnly';
				MAIN_BUYER_CHANGE_REMARK.readOnly= true;
				MAIN_BUYER_CHANGE_REMARK.cls='x-readOnly';
				EQUITY_STRUC_CHANGE_REMARK.readOnly= true;
				EQUITY_STRUC_CHANGE_REMARK.cls='x-readOnly';
				MANAGEMENT_CHANGE_REMARK.readOnly= true;
				MANAGEMENT_CHANGE_REMARK.cls='x-readOnly';
				COLLATERAL_CONDITION_REMARK.readOnly= true;
				COLLATERAL_CONDITION_REMARK.cls='x-readOnly';
				COOPERATION_CHANGE_REMARK.readOnly= true;
				COOPERATION_CHANGE_REMARK.cls='x-readOnly';
				MATTERS_FOLLOW.readOnly= true;
				MATTERS_FOLLOW.cls='x-readOnly';
				OTHER.readOnly= true;
				OTHER.cls='x-readOnly';
		   return [CALL_REASON,MAIN_BUSI_CHANGE_REMARK,REVENUE_CHANGE_REMARK,PROFI_CHANGE_REMARK,MAIN_SUPPLIER_CHANGE_REMARK,
	   				MAIN_BUYER_CHANGE_REMARK,EQUITY_STRUC_CHANGE_REMARK,MANAGEMENT_CHANGE_REMARK,COLLATERAL_CONDITION_REMARK,
	   				COOPERATION_CHANGE_REMARK,OTHER,MATTERS_FOLLOW];
	   }
	}
	]
}];


var beforeviewshow = function(view){
	if(view._defaultTitle == '新客户新增'){
		view.contentPanel.form.findField("CUST_ID").setValue(custData.CUST_ID);
		view.contentPanel.form.findField("CUST_NAME").setValue(custData.CUST_NAME);
		view.contentPanel.form.findField("LINKMAN").setValue(custData.LINKMAN);
		view.contentPanel.form.findField("CUS_OWNBUSI").setValue(custData.CUS_OWNBUSI);
		view.contentPanel.form.findField("IF_TARGETBUSI").setValue(custData.IF_TARGETBUSI);
		view.contentPanel.form.findField("LINK_TEL").setValue(custData.LINK_TEL);
		view.contentPanel.form.findField("MGR_ID").setValue(custData.MGR_ID);
		view.contentPanel.form.findField("MGR_NAME").setValue(custData.MGR_NAME);
		
		view.contentPanel.form.findField("VISIT_DATE").hide();
		view.contentPanel.form.findField("RECAL_DATE").hide();
	}
	if(view._defaultTitle == '旧客户新增'){
		view.contentPanel.form.findField("CUST_ID").setValue(custData.CUST_ID);
		view.contentPanel.form.findField("CUST_NAME").setValue(custData.CUST_NAME);
		view.contentPanel.form.findField("MGR_ID").setValue(custData.MGR_ID);
		view.contentPanel.form.findField("MGR_NAME").setValue(custData.MGR_NAME);
		
		view.contentPanel.form.findField("VISIT_DATE").hide();
	}
	if(view._defaultTitle == '新客户修改'||view._defaultTitle == '新客户详情'){
		store = new Ext.data.Store({
				restful:true,	
		        proxy : new Ext.data.HttpProxy(
		        		{
		        			url:basepath+'/ocrmFCallNewRecord.json'
		        		}),
		        reader: new Ext.data.JsonReader({
		        	root : 'json.data'
		        }, [{name:'CUST_NAME'},
		            {name:'CUS_OWNBUSI'},
		            {name:'IF_TARGETBUSI'},
		            {name:'LINKMAN'},
		            {name:'LINK_TEL'},
		            {name:'CUST_SOURCE'},
		            {name:'CALL_DATE'},
		            {name:'MGR_NAME'},
		            {name:'CALL_RESULT'},
		            {name:'VISIT_DATE'},
		            {name:'RECAL_DATE'},
		            {name:'REFUSE_RESON'},
		            {name:'TEL_CONTACTER'},
		            {name:'CUST_REVENUE'},
		            {name:'OTHERBANK_TRADE'},
		            {name:'MARKT_PRODUCT'},
		            {name:'TEL_CONTACTER_REMARK'},
		            {name:'CUST_REVENUE_REMARK'},
		            {name:'MARKT_PRODUCT_REMARK'},
		            {name:'CUST_SOURCE_REMARK'},
		            {name:'OTHER'},
		            {name:'MGR_ID'},
		            {name:'CUST_ID'},
		            {name:'ID'}]
			)
			});
		store.load({params : {
			id:getSelectedData().data.ID
        },
        callback:function(){
        	if(store.getCount()!=0){
        		view.contentPanel.getForm().loadRecord(store.getAt(0));
        		var p=view.contentPanel.getForm().findField("CALL_RESULT").getValue();
        		if(p=='2'){
        			view.contentPanel.getForm().findField('REFUSE_RESON').show(true);
        		}else{
        			view.contentPanel.getForm().findField('REFUSE_RESON').hide(false);
        		}
        		if(p == '1'){
					view.contentPanel.form.findField("VISIT_DATE").show();
        		}else{
        			view.contentPanel.form.findField("VISIT_DATE").hide();
        		}
        		if(p == '3'){
					view.contentPanel.form.findField("RECAL_DATE").show();
        		}else{
        			view.contentPanel.form.findField("RECAL_DATE").hide();
        		}
        		
        	}
		}});
       
	}
	if(view._defaultTitle == '旧客户修改'||view._defaultTitle == '旧客户详情'){
		store = new Ext.data.Store({
				restful:true,	
		        proxy : new Ext.data.HttpProxy(
		        		{
		        			url:basepath+'/mktCallOldFRecord.json'
		        		}),
		        reader: new Ext.data.JsonReader({
		        	root : 'json.data'
		        }, [{name:'CUST_NAME'},
		            {name:'RESPONDENTS_POSITION'},
		            {name:'RESPONDENTS_NAME'},
		            {name:'RESPONDENTS_CONTACT'},
		            {name:'BANK_PARTICIPANTS'},
		            {name:'CALL_PURPOSE'},
		            {name:'CALL_DATE'},
		            {name:'MGR_NAME'},
		            {name:'CUST_BUSI_CONDITION'},
		            {name:'MAIN_BUSI_CHANGE'},
		            {name:'REVENUE_CHANGE'},
		            {name:'PROFI_CHANGE'},
		            {name:'MAIN_SUPPLIER_CHANGE'},
		            {name:'MAIN_BUYER_CHANGE'},
		            {name:'EQUITY_STRUC_CHANGE'},
		            {name:'MANAGEMENT_CHANGE'},
		            {name:'COLLATERAL_CONDITION'},
		            {name:'COOPERATION_CHANGE'},
		            {name:'IF_PRECONTRACT'},
		            {name:'MARKT_RESULT'},
		            {name:'MARKT_PRODUCT'},
		            {name:'MGR_ID'},
		            {name:'CUST_ID'},
		            {name:'ID'},
		            {name:'VISIT_DATE'},
		            {name:'CALL_REASON'},
		            {name:'MAIN_BUSI_CHANGE_REMARK'},
		            {name:'REVENUE_CHANGE_REMARK'},
		            {name:'PROFI_CHANGE_REMARK'},
		            {name:'MAIN_SUPPLIER_CHANGE_REMARK'},
		            {name:'MAIN_BUYER_CHANGE_REMARK'},
		            {name:'EQUITY_STRUC_CHANGE_REMARK'},
		            {name:'MANAGEMENT_CHANGE_REMARK'},
		            {name:'COLLATERAL_CONDITION_REMARK'},
		            {name:'COOPERATION_CHANGE_REMARK'},
		            {name:'OTHER'},
		            {name:'MATTERS_FOLLOW'}]
			)
			});
		store.load({params : {
			id:getSelectedData().data.ID
        },
        callback:function(){
        	if(store.getCount()!=0){
        		view.contentPanel.getForm().loadRecord(store.getAt(0));
        		var rst = view.contentPanel.form.findField("IF_PRECONTRACT").getValue();
				if(rst == 1){
					view.contentPanel.form.findField("VISIT_DATE").show();
				}else{
					view.contentPanel.form.findField("VISIT_DATE").hide();
				}
        	}
		}});
       
	}
};
