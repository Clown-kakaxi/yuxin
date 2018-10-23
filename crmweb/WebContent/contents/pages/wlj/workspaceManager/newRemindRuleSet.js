/**
 * 提醒规则设置
 * hujun
 * 2014-07-07
 * */

	imports([
	         '/contents/pages/com.yucheng.bcrm/com.yucheng.bcrm.js',
	         '/contents/pages/common/LovCombo.js'
	         ]);
	var createView=false;
	var editView=false;
	var detailView=false;
	var showColums = '';//需要展示的字段
	var colums = 'RULE_ROLE,ADJUST_CUST,COM_CUST_LEVEL,INDIV_CUST_LEVEL,BEFORE_DAYS,LAST_DAYS,THRESHHOLD,IF_MESSAGE,MESSAGE_TO_CUST_CHANNEL,FOLLOW_DEAL,IS_USE,REMIND_MODLE,MESSAGE_MODEL,MICRO_MODEL,MAIL_MODEL,CUST_AGE,SEX,RULE_CODE';//所有需要控制的字段	
	
	var lookupTypes=[
	                 'FOLLOW_DEAL',
	                 'ADJUST_SEX',
	                 'INDIV_CUST_LEVEL',
	                 'COM_CUST_LEVEL',
	                 'CUST_AGE',
	                 'IF_FLAG'
	                 ];
	var localLookup = {//本地数据字典
			'SONEL_ROLE' : [
			       		{key : 'R304',value : '客户经理'}
//			       		,
//			       	    {key : 'R301',value : '分行行长'},
//			       	    {key : 'R103',value : '总行主管'}
			       	],
			  'FASONGQUDAO':[
						{key : '1',value : '短信'},
						   {key : '2',value : '邮件'},
						   {key : '3',value : '微信'}
			 ],
			 'TYPE_RANGE':[
			 		{key : '0',value : '全部'},
						   {key : '1',value : '零售'},
						   {key : '2',value : '对公'}
			 ]
	};
	//角色
	var roleStore = new Ext.data.ArrayStore({
		fields : [ 'key', 'value' ],
		data : [ [ 'R304', '客户经理' ],
				[ 'R301', '分行行长' ],
				['R103','总行主管']]
	});
	var role=new Ext.ux.form.LovCombo({
		fieldLabel : '接收角色',
		labelStyle : 'text-align:right;',
		name : 'RULE_ROLE',
		hiddenName : 'RULE_ROLE',
		hidden:true,
		triggerAction : 'all',
		store : roleStore,
		displayField : 'value',
		valueField : 'key',
		mode : 'local',
		emptyText : '请选择 ',
		resizable : true,
		hideOnSelect : false,
		triggerAction : 'all',
		allowBlank : false,
		editable : true,
		anchor:'90%'
	});
	var needGrid = false;
	WLJUTIL.suspendViews=false;  //自定义面板是否浮动
	var fields=[{name: 'TEST',text:'此文件fields必须要有一个无用字段', resutlWidth:80}];
	var Record = Ext.data.Record.create( [ {
		name : 'RULE_ID',
		mapping : 'RULE_ID'
	}, {
		name : 'RULE_CODE',
		mapping : 'RULE_CODE'
	}, {
		name : 'RULE_NAME',
		mapping : 'RULE_NAME'
	}, {
		name : 'RULE_ROLE',
		mapping : 'RULE_ROLE'
	},{
		name:'ADJUST_CUST',
		mapping:'ADJUST_CUST'
	},{
		name:'CUST_AGE',
		mapping:'CUST_AGE'
	},{
		name:'SEX',
		mapping:'SEX'
	},{
		name:'BEFORE_DAYS',
		mapping:'BEFORE_DAYS'
	},{
		name:'LAST_DAYS',
		mapping:'LAST_DAYS'
	},{
		name:'THRESHHOLD',
		mapping:'THRESHHOLD'
	},{
		name:'REMIND_MODLE',
		mapping:'REMIND_MODLE'
	},{
		name:'IF_MESSAGE',
		mapping:'IF_MESSAGE'
	},{
		name:'FOLLOW_DEAL',
		mapping:'FOLLOW_DEAL'
	},{
		name:'COM_CUST_LEVEL',
		mapping:'COM_CUST_LEVEL'
	},{
		name:'INDIV_CUST_LEVEL',
		mapping:'INDIV_CUST_LEVEL'
	},{
		name:'MESSAGE_MODEL',
		mapping:'MESSAGE_MODEL'
	},{
		name:'MESSAGE_TO_CUST_CHANNEL',
		mapping:'MESSAGE_TO_CUST_CHANNEL'
	},{
		name:'MICRO_MODEL',
		mapping:'MICRO_MODEL'
	},{
		name:'MAIL_MODEL',
		mapping:'MAIL_MODEL'
	},{
		name:'IS_USE',
		mapping:'IS_USE'
	}]);

	var store = new Ext.data.Store({
		restful:true,	
        proxy : new Ext.data.HttpProxy(
        		{
        			url:basepath+'/remindRuleSetAction.json'
        		}),
        reader: new Ext.data.JsonReader({
            successProperty: 'success',
        root:'json.data',
        idProperty : 'RULE_ID',
        totalProperty: 'json.count'
        }, Record)
	});
	var customerView=[{
		title : '提醒规则设置',
		type : 'form',
		hideTitle : true,
		autoLoadSeleted : true,
		groups : [{
			columnCount : 2 ,
			labelWidth : 140,
			fields : [																	
				        {name:'RULE_NAME',xtype:'textfield',text:'规则名称',editable : false,allowBlank : false},																			
				        {name:'RULE_ROLE',text:'接收角色',translateType:'SONEL_ROLE',multiSelect:true,multiSeparator:',',allowBlank : false},																			
				        {name:'ADJUST_CUST',text:'适用客户类别',translateType:'TYPE_RANGE',allowBlank : false,
				        	listeners:{
				        		/*2018-01-09崔恒薇注释
				        		'select':function(val){
				        			if(val.value=='1'){
				        				getCustomerViewByTitle('提醒规则设置').getFieldByName('COM_CUST_LEVEL').hide();
				        				getCustomerViewByTitle('提醒规则设置').getFieldByName('INDIV_CUST_LEVEL').show();
				        				
				        			}
				        			if(val.value=='2'){
				        				getCustomerViewByTitle('提醒规则设置').getFieldByName('COM_CUST_LEVEL').show();
				        				getCustomerViewByTitle('提醒规则设置').getFieldByName('INDIV_CUST_LEVEL').hide();
				        			}
				        			if(val.value=='0'){
				        				getCustomerViewByTitle('提醒规则设置').getFieldByName('COM_CUST_LEVEL').show();
				        				getCustomerViewByTitle('提醒规则设置').getFieldByName('INDIV_CUST_LEVEL').show();
				        			}
				        		}
				        	*/}},																			
				        
				        {name:'COM_CUST_LEVEL',text:'对公基线客户层级',translateType:'COM_CUST_LEVEL',allowBlank : false},
				        {name:'INDIV_CUST_LEVEL',text:'对私基线客户层级',translateType:'INDIV_CUST_LEVEL',allowBlank : false},
				        {name:'CUST_AGE',xtype:'textfield',text:'客户年龄控制',transLateType:'ADJUST_SEX',allowBlank : false},																			
				        {name:'SEX',xtype:'textfield',text:'客户性别控制',transLateType:'ADJUST_SEX',allowBlank : false},
				        {name:'BEFORE_DAYS',xtype:'numberfield',text:'提醒提前天数',allowBlank : false},																			
				        {name:'LAST_DAYS',xtype:'numberfield',text:'提醒持续天数',allowBlank : false},																			
				        {name:'THRESHHOLD',xtype:'numberfield',text:'变动阈值',allowBlank : false},																			
				        
				        {name:'IF_MESSAGE',text:'是否发送提醒信息',translateType:'IF_FLAG',allowBlank : false,listeners:{
				        	'select':function(val){
				        		
				        		/*2018-01-09崔恒薇注释
				        		 * if(val.value=="1"){
				        			getCustomerViewByTitle('提醒规则设置').getFieldByName('MESSAGE_TO_CUST_CHANNEL').show();
				        		}else if(val.value=="0"){
				        			getCustomerViewByTitle('提醒规则设置').getFieldByName('MESSAGE_TO_CUST_CHANNEL').hide();
				        			getCustomerViewByTitle('提醒规则设置').getFieldByName('MESSAGE_MODEL').hide();
				        			getCustomerViewByTitle('提醒规则设置').getFieldByName('MAIL_MODEL').hide();
				        			getCustomerViewByTitle('提醒规则设置').getFieldByName('MICRO_MODEL').hide();
				        		}*/
				        	}
				        }},
				        {name:'MESSAGE_TO_CUST_CHANNEL',xtype:'textfield',text:'提醒发送渠道',translateType:'FASONGQUDAO',multiSelect:true,allowBlank : false,listeners:{
				        	'select':function(val){
				        		/*2018-01-09崔恒薇注释
				        		var value1=val.value;
								if (value1 == "1"){
									getCustomerViewByTitle('提醒规则设置').getFieldByName('MESSAGE_MODEL').show();
									getCustomerViewByTitle('提醒规则设置').getFieldByName('MAIL_MODEL').hide();
				            		getCustomerViewByTitle('提醒规则设置').getFieldByName('MICRO_MODEL').hide();
								}else if(value1=="2"){
									getCustomerViewByTitle('提醒规则设置').getFieldByName('MAIL_MODEL').show();
									getCustomerViewByTitle('提醒规则设置').getFieldByName('MESSAGE_MODEL').hide();
				            		getCustomerViewByTitle('提醒规则设置').getFieldByName('MICRO_MODEL').hide();
								}else if(value1=="3"){
									getCustomerViewByTitle('提醒规则设置').getFieldByName('MICRO_MODEL').show();
									getCustomerViewByTitle('提醒规则设置').getFieldByName('MAIL_MODEL').hide();
									getCustomerViewByTitle('提醒规则设置').getFieldByName('MESSAGE_MODEL').hide();
								}else if(value1=='1,2'||value1=='2,1'){
									getCustomerViewByTitle('提醒规则设置').getFieldByName('MAIL_MODEL').show();
									getCustomerViewByTitle('提醒规则设置').getFieldByName('MICRO_MODEL').hide();
									getCustomerViewByTitle('提醒规则设置').getFieldByName('MESSAGE_MODEL').show();
				            	}else if(value1=='3,2'||value1=='2,3'){
				            		getCustomerViewByTitle('提醒规则设置').getFieldByName('MAIL_MODEL').show();
				            		getCustomerViewByTitle('提醒规则设置').getFieldByName('MICRO_MODEL').show();
				            		getCustomerViewByTitle('提醒规则设置').getFieldByName('MESSAGE_MODEL').hide();
				            	}else if(value1=='1,3'||value1=='3,1'){
				            		getCustomerViewByTitle('提醒规则设置').getFieldByName('MESSAGE_MODEL').show();
				            		getCustomerViewByTitle('提醒规则设置').getFieldByName('MICRO_MODEL').show();
				            	}else if(value1=='1,2,3'||value1=='3,2,1'||value1=='2,1,3'||
				            			value1=='2,3,1'||value1=='1,3,2'||value1=='3,1,2'){
				            		getCustomerViewByTitle('提醒规则设置').getFieldByName('MESSAGE_MODEL').show();
				            		getCustomerViewByTitle('提醒规则设置').getFieldByName('MAIL_MODEL').show();
									getCustomerViewByTitle('提醒规则设置').getFieldByName('MESSAGE_MODEL').show();
				            	}
		        			*/}
				        }},	
				        
				        {name:'FOLLOW_DEAL',text:'后续处理方式',translateType:'FOLLOW_DEAL',allowBlank : false},
				        {name:'IS_USE',text:'是否启用',translateType:'IF_FLAG',allowBlank : false},
				        {name:'REMIND_MODLE',resutlWidth:350,xtype:'textarea',text:'提醒模板',allowBlank : false},
				        {name:'MICRO_MODEL',resutlWidth:350,xtype:'textarea',text:'微信模板'},
				        {name:'MAIL_MODEL',resutlWidth:350,xtype:'textarea',text:'邮件模板'},
				        {name:'MESSAGE_MODEL',resutlWidth:350,xtype:'textarea',text:'短信内容模板'},
				        {name:'RULE_ID',text:'规则id',xtype:'textfield'},
				        {name:'RULE_CODE',xtype:'textfield',text:'规则类别编号'}],
        	fn : function(RULE_NAME,RULE_ROLE,ADJUST_CUST,COM_CUST_LEVEL,INDIV_CUST_LEVEL,CUST_AGE,SEX,BEFORE_DAYS,
				LAST_DAYS,THRESHHOLD,IF_MESSAGE,MESSAGE_TO_CUST_CHANNEL
				,FOLLOW_DEAL,IS_USE,REMIND_MODLE,MESSAGE_MODEL,MICRO_MODEL,MAIL_MODEL,RULE_ID,RULE_CODE){
        	RULE_NAME.hidden=false; RULE_ROLE.hidden=false;
        	ADJUST_CUST.hidden=false; COM_CUST_LEVEL.hidden=true;
        	INDIV_CUST_LEVEL.hidden=true; CUST_AGE.hidden=true;
        	SEX.hidden=true; BEFORE_DAYS.hidden=true;
        	LAST_DAYS.hidden=true; THRESHHOLD.hidden=true;
        	IF_MESSAGE.hidden=false; MESSAGE_TO_CUST_CHANNEL.hidden=true;
        	FOLLOW_DEAL.hidden=false; IS_USE.hidden=false; REMIND_MODLE.hidden=false;
        	MESSAGE_MODEL.hidden=true; MICRO_MODEL.hidden=true;
        	MAIL_MODEL.hidden=true; RULE_ID.hidden=true;
        	RULE_CODE.hidden=true;
			return [RULE_NAME,RULE_ROLE,ADJUST_CUST,COM_CUST_LEVEL,INDIV_CUST_LEVEL,CUST_AGE,SEX,BEFORE_DAYS,
					LAST_DAYS,THRESHHOLD,IF_MESSAGE,MESSAGE_TO_CUST_CHANNEL
					,FOLLOW_DEAL,IS_USE,REMIND_MODLE,MESSAGE_MODEL,MICRO_MODEL,MAIL_MODEL,RULE_ID,RULE_CODE];
		}
		}/*,{
			columnCount : 1 ,
			labelWidth : 140,
			fields : [	
				{name: 'QTIPS',text:'<font color=red>提醒模板变量命名规则</font>',xtype: 'displayfield',anchor:'95%',value:
					'客户号：${CUST_ID}，客户名称：${CUST_NAME}\n' +
					'客户经理号：${USER_ID}，客户经理姓名：${USER_NAME}\n' +
					'机构号：${ORG_ID},机构名称：${ORG_NAME}\n' +
					'产品号：${PROD_ID}，产品名称：${PROD_NAME}\n' +
					'账号：${ACC_NO}，金额：${MONEY}\n' +
					'日期：${DATE}，联系电话：${LX_PHONE}\n' +
					'更多表达式，请和ETL后台共同确定.'
				}
			],
			fn : function(QTIPS){
				QTIPS.anchor = '95%';
				return [QTIPS];
			}
		}*/],
		formButtons : [{
       	 text:'保存',
       	 id:'save',
       	 disabled:true,
    	 fn: function(formPanel,basicForm){
    	 if (!ifRight(showColums)) {
    		 Ext.MessageBox.alert('系统提示信息', '请正确输入各项必要信息！');
    		 basicForm.isValid();
    		 return false;
    	 }else{
    		 var commintData = translateDataKey(basicForm.getFieldValues(),_app.VIEWCOMMITTRANS);
    		 Ext.Ajax.request({
					url : basepath+ '/remindRuleSetAction!saveData.json',
					method : 'POST',
					params :commintData,
					success : function() {
						Ext.Msg.alert('提示','保存成功');
						//重新加载（返回ruleId）
						initForm('',
								getCustomerViewByTitle('提醒规则设置').getFieldByName('RULE_CODE').getValue()
								,false);
					},
					failure : function() {
						Ext.Msg.alert('提示','保存失败');
					}
				});
    	 }
    	 }
		},{
			text : '重置',
			id:'conz',
			disabled:true,
			fn : function(formPanel,basicForm){
				var ruleCode = basicForm.findField("RULE_CODE").getValue();
				basicForm.reset();
				
				initForm('',
						ruleCode
						,false);
			}
		}]	
	}];
	
	var treeLoaders = [{
			key : 'REMINDRULELOADER',
			url : basepath + '/RemindRuleTypeGetAction.json',
			jsonRoot : 'JSON',
			parentAttr : 'TYPE',//指向父节点的属性列
			locateAttr : 'F_CODE',//节点定位属性列，也是父属性所指向的列
			rootValue :'0',//虚拟根节点id 若果select的值为null则为根节点
			textField : 'F_VALUE',//用于展示节点名称的属性列
			idProperties : 'F_CODE'//,//指定节点ID的属性列	
		}];
		
		//面板左边的配置信息		
		var treeCfgs = [{
			key : 'REMINDRULETREE',
			loaderKey : 'REMINDRULELOADER',
			autoScroll:true,
			rootCfg : {
				expanded:true,
				text:'提醒规则类型',
				autoScroll:true,
				children:[]
			},
			clickFn : function(node){//单击事件
				//叶子节点的点击处理
				if(node.isLeaf()){
					showColums = 'RULE_ROLE,ADJUST_CUST,REMIND_MODLE,IF_MESSAGE,FOLLOW_DEAL,IS_USE';
					//1.根据说点击的提醒类型，画出右侧表单
					getCustomerViewByTitle('提醒规则设置').reset();
					getCustomerViewByTitle('提醒规则设置').setTitle(node.text);
					getCustomerViewByTitle('提醒规则设置').getFieldByName('RULE_CODE').setValue(node.id);
					getCustomerViewByTitle('提醒规则设置').getFieldByName('RULE_NAME').setValue(node.text);
					dealWithColums(colums,'hide');
					Ext.getCmp('save').setDisabled(true);
					Ext.getCmp('conz').setDisabled(true);
					
					/*--2018-01-09崔恒薇注释
					 * if(node.id=='101'||node.id=='102')
						showColums += ',LAST_DAYS,THRESHHOLD,IF_MESSAGE';*/
					if(node.id=='701'||node.id=='702'||node.id=='201'||node.id=='801')
						showColums +=',LAST_DAYS,IF_MESSAGE';
					if(node.id.substring(0,1)=='4'||node.id.substring(0,1)=='5'||node.id=='703'||node.id=='704'
						||node.id=='705'||node.id=='706')
						showColums += ',LAST_DAYS';
					/*--2018-01-09崔恒薇注释
					 * if(node.id=='202'||node.id=='203')
						showColums += ',BEFORE_DAYS,LAST_DAYS,THRESHHOLD,IF_MESSAGE';*/
					//if(node.id=='204'||node.id=='301'||node.id=='302'||node.id=='303'||node.id=='601'||node.id=='602')
						
					if(node.id=='301'||node.id=='300'||node.id=='903'||node.id=='101'||node.id=='102'||
						node.id=='202'||node.id=='203'){
						getCustomerViewByTitle('提醒规则设置').getFieldByName('COM_CUST_LEVEL').allowBlank = true;
						getCustomerViewByTitle('提醒规则设置').getFieldByName('INDIV_CUST_LEVEL').allowBlank = true;
						getCustomerViewByTitle('提醒规则设置').getFieldByName('CUST_AGE').allowBlank = true;
						getCustomerViewByTitle('提醒规则设置').getFieldByName('SEX').allowBlank = true;
						getCustomerViewByTitle('提醒规则设置').getFieldByName('LAST_DAYS').allowBlank = true;
						getCustomerViewByTitle('提醒规则设置').getFieldByName('IF_MESSAGE').allowBlank = true;
						getCustomerViewByTitle('提醒规则设置').getFieldByName('MESSAGE_TO_CUST_CHANNEL').allowBlank = true;
						getCustomerViewByTitle('提醒规则设置').getFieldByName('FOLLOW_DEAL').allowBlank = true;
						getCustomerViewByTitle('提醒规则设置').getFieldByName('REMIND_MODLE').allowBlank = true;
							
						if(node.id=='903')//拜访提醒
							getCustomerViewByTitle('提醒规则设置').getFieldByName('BEFORE_DAYS').allowBlank = true;
							getCustomerViewByTitle('提醒规则设置').getFieldByName('THRESHHOLD').allowBlank = true;
							showColums = 'RULE_NAME,RULE_ROLE,ADJUST_CUST,IS_USE';
						if(node.id=='101'||node.id=='102'){//账户余额变动（转出）||账户余额变动（转入）
							getCustomerViewByTitle('提醒规则设置').getFieldByName('BEFORE_DAYS').allowBlank = true;
							showColums = 'RULE_NAME,RULE_ROLE,ADJUST_CUST,IS_USE,THRESHHOLD';
						}
						if(node.id=='301'||node.id=='300'||node.id=='202'||node.id=='203'){//客户生日||证件到期提醒||定期存款到期||理财产品到期
							getCustomerViewByTitle('提醒规则设置').getFieldByName('THRESHHOLD').allowBlank = true;
							showColums = 'RULE_NAME,RULE_ROLE,ADJUST_CUST,IS_USE,BEFORE_DAYS';
						}
					}
					if(node.id=='204'||node.id=='302'||node.id=='303'||node.id=='601'||node.id=='602')
						showColums += ',BEFORE_DAYS,LAST_DAYS';
					if(node.id=='205'||node.id=='206')
						showColums += ',LAST_DAYS,THRESHHOLD';
					if(node.id=='304')
						showColums += ',CUST_AGE,BEFORE_DAYS,LAST_DAYS';
					if(node.id=='305')
						showColums += ',SEX,BEFORE_DAYS,LAST_DAYS';
					if(node.id=='705')
						showColums += ',THRESHHOLD';
					initForm('',node.id,true);
					dealWithColums(showColums,'show');
					
					//2.根据所点击类型和当前法人机构号，查询规则配置并反显
					
				}
		 	}
		}];
		//面板边缘配置信息
		var edgeVies = {
			left : {
				width : 265,
				layout : 'fit',
				items : [TreeManager.createTree('REMINDRULETREE')]
			}
		};
	
		//控制页面字段的方法
		function dealWithColums(fields,action){
			var fields_arr = fields.split(",");
			if (fields_arr != null && fields_arr.length > 0) {
				var fieldName = null;
				for ( var i = 0; i < fields_arr.length; i++) {
					fieldName = fields_arr[i];
					if (fieldName != null && fieldName != "") {
						if(action=='hide'){
							getCustomerViewByTitle('提醒规则设置').getFieldByName(fieldName).hide();
						}
						if(action=='show'){
							getCustomerViewByTitle('提醒规则设置').getFieldByName(fieldName).show();
						}
					}
					}
				}
			}

//判断表单填写情况  showColums 全部填写
function ifRight(showColums){
	var fields_arr = showColums.split(",");
	if (fields_arr != null && fields_arr.length > 0) {
		var fieldName = null;
		for ( var i = 0; i < fields_arr.length; i++) {
			fieldName = fields_arr[i];
			if (fieldName != null && fieldName != "") {
				var value =	getCustomerViewByTitle('提醒规则设置').getFieldByName(fieldName).getValue();
				if(value == null||value == ''){
					return false;
				}
				/*else if(fieldName=='ADJUST_CUST'){//如果是适用客户类型字段还要判断客户等级是否填写
					if (value == "0") {//所有
						if(getCustomerViewByTitle('提醒规则设置').getFieldByName("COM_CUST_LEVEL").getValue()==''||getCustomerViewByTitle('提醒规则设置').getFieldByName("INDIV_CUST_LEVEL").getValue()=='')
							return false;
					}
					if (value == "1"){//零售
						if(getCustomerViewByTitle('提醒规则设置').getFieldByName("INDIV_CUST_LEVEL").getValue()=='')
							return false;
					}
					if (value == "2"){//对公
						if(getCustomerViewByTitle('提醒规则设置').getFieldByName("COM_CUST_LEVEL").getValue()=='')
							return false;
					}
				}*/
				
			}
			}
		return true;
	}
}

//根据所点击类型和当前法人机构号，查询规则配置并反显
function initForm(frid,ruleCode,ifFirst){
	store.load({
		params : {
			frid:frid,
			ruleCode:ruleCode
        },
        callback:function(){
			if(store.getCount()!=0){
	    		getCustomerViewByTitle('提醒规则设置').loadRecord(store.getAt(0));
	    		//控制按钮
	    		if(ifFirst){//true:点击节点时调用，需要控制按钮  false：点击重置时调用，不用控制按钮
	    			Ext.getCmp('save').setDisabled(false);
					Ext.getCmp('conz').setDisabled(false);
	    		}
	    		/*//根据所加载的数据控制客户基线和短信模板是否可见
	    		//适用客户类型字段
	    		var value = getCustomerViewByTitle('提醒规则设置').getFieldByName('ADJUST_CUST').getValue();
	    		if(value=='1'){
					getCustomerViewByTitle('提醒规则设置').getFieldByName('COM_CUST_LEVEL').hide();
					getCustomerViewByTitle('提醒规则设置').getFieldByName('INDIV_CUST_LEVEL').show();
				}
				if(value=='2'){
					getCustomerViewByTitle('提醒规则设置').getFieldByName('COM_CUST_LEVEL').show();
					getCustomerViewByTitle('提醒规则设置').getFieldByName('INDIV_CUST_LEVEL').hide();
				}
				if(value=='0'){
					getCustomerViewByTitle('提醒规则设置').getFieldByName('COM_CUST_LEVEL').show();
					getCustomerViewByTitle('提醒规则设置').getFieldByName('INDIV_CUST_LEVEL').show();
				}
				//是否短信字段
				var if_mass=getCustomerViewByTitle('提醒规则设置').getFieldByName('IF_MESSAGE').getValue();	
				if(if_mass=="1"){
					var value1 = getCustomerViewByTitle('提醒规则设置').getFieldByName('MESSAGE_TO_CUST_CHANNEL').getValue();
					getCustomerViewByTitle('提醒规则设置').getFieldByName('MESSAGE_TO_CUST_CHANNEL').show();
					if (value1 == "1"){
						getCustomerViewByTitle('提醒规则设置').getFieldByName('MESSAGE_MODEL').show();
					}else if(value1=="2"){
						getCustomerViewByTitle('提醒规则设置').getFieldByName('MAIL_MODEL').show();
					}else if(value1=="3"){
						getCustomerViewByTitle('提醒规则设置').getFieldByName('MICRO_MODEL').show();
					}else if(value1=='1,2'||value1=='2,1'){
						getCustomerViewByTitle('提醒规则设置').getFieldByName('MAIL_MODEL').show();
						getCustomerViewByTitle('提醒规则设置').getFieldByName('MESSAGE_MODEL').show();
	            	}else if(value1=='3,2'||value1=='2,3'){
	            		getCustomerViewByTitle('提醒规则设置').getFieldByName('MAIL_MODEL').show();
	            		getCustomerViewByTitle('提醒规则设置').getFieldByName('MICRO_MODEL').show();
	            	}else if(value1=='1,3'||value1=='3,1'){
	            		getCustomerViewByTitle('提醒规则设置').getFieldByName('MESSAGE_MODEL').show();
	            		getCustomerViewByTitle('提醒规则设置').getFieldByName('MICRO_MODEL').show();
	            	}else if(value1=='1,2,3'||value1=='3,2,1'||value1=='2,1,3'||
	            			value1=='2,3,1'||value1=='1,3,2'||value1=='3,1,2'){
	            		getCustomerViewByTitle('提醒规则设置').getFieldByName('MESSAGE_MODEL').show();
	            		getCustomerViewByTitle('提醒规则设置').getFieldByName('MAIL_MODEL').show();
						getCustomerViewByTitle('提醒规则设置').getFieldByName('MESSAGE_MODEL').show();
	            	}
				}*/
	    	}else{//未返回（当前为法人机构（非总行），总行未配置该规则），当前不能配置该规则
    			if(ifFirst){//true:点击节点时调用，需要控制按钮  false：点击重置时调用，不用控制按钮
    				Ext.getCmp('save').setDisabled(false);
					Ext.getCmp('conz').setDisabled(false);
	    		}
	    	}
		}
	});	        		
}
	
	
	
	