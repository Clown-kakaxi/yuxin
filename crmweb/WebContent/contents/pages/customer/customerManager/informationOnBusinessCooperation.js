var base_id="";
Ext.onReady(function() {
	Ext.QuickTips.init();
	//传入参数,客户编号
	var cust_id ="";
	var custid = "";
	custid = oCustInfo.cust_id;
	var acct_no =null;
	var loan_ac = null;
	var Nowdate=new Date(); 
	var MonthFirstDay=new Date(Nowdate.getFullYear(),Nowdate.getMonth(),1); 
	var fnJudgeEntrance= function(){
		if(!oCustInfo.groupId){
			base_id=oCustInfo.groupId;
		}
		if(oCustInfo.cust_id!=false)
			cust_id=oCustInfo.cust_id;
	};
	var isOmainType = "0";
	if(oCustInfo.omain_type==true)
	{
		isOmainType = "1";
	}
	var saveAccountStore = new Ext.data.Store({
		
		restful:true,
		autoLoad:true,
		proxy:new Ext.data.HttpProxy({
			url:basepath+'/querySaveAcc-Action.json?custid='+custid
		}),
	
		reader:new Ext.data.JsonReader({
			root:'json.data'
		},['VALUE','NAME'])
		
	});
	saveAccountStore.load();
	fnJudgeEntrance();
		var moneyType_Store=util.form._store('/lookup.json?name=ACC1300012');
			moneyType_Store.load();
		var type_Store=util.form._store('/lookup.json?name=LED0100016');
			type_Store.load();
		var tran_listPanel = new Mis.Ext.CrudPanel( {
			//seBaseForm ：true,
			defaultLoad : false,
			stUrl : basepath + '/querytranslog.json?acct_no='+acct_no,
			checkbox : true,
			//定义查询条件Form的高度
			seFormHeight : 100,
			grid : false,
			dbclick : false,

			gridHeight : 327,
//			labelwidth:150,
			//设置分页每页显示条数，若不设置则不出现分页栏
			pagesize : 10,
			frame : true,
			autoScroll:true,
			
			
			//重载afterSeOneFun方法，加载一条数据后做的特殊处理
//			afterSeOneFun : function(b) {
//				//debugger;
////				Ext.getCmp('createDate').setValue(new Date(b.createDate.time));
////		    	Ext.getCmp('updateDate').setValue(new Date(b.updateDate.time));
//			},
			// 查询字段定义，若不定义则不出现查询条件From
			selectItems :new Ext.form.FieldSet({items:[
				util.layout._tr(
								[util.form._td({id:'tran_createDateS',name : 'createDateS',xtype : 'datefield',fieldLabel : '开始日期',value:MonthFirstDay})],
								[util.form._td({id:'tran_createDateE',name : 'createDateE',xtype : 'datefield',fieldLabel : '截至日期',value:Nowdate})	]
								)
			]}),
	
			//查询列表字段定义，有header属性则在页面显示
			//如果需要做映射需要定义store , mappingkey ,mappingvalue 三个属性
			gclms : [ 
			  		{name : 'ACCT_NO',header:'账号',mapping : 'ACCT_NO',width : 100},
				        {name : 'TRAN_DATE',header:'交易日期',mapping : 'TRAN_DATE',width : 100},
				        {name : 'TRAN_TIME',header:'交易时间',mapping : 'TRAN_TIME',width : 100},
				        {name : 'CD_FLAG_ORA',header:'借贷标识',width : 100},
				        {name : 'TRAN_AMT',header:'交易金额',mapping : 'TRAN_AMT',width : 100},
				        {name : 'CURR_ORA',header:'币种',width : 100},
				        {name : 'BAL',header:'余额',mapping : 'BAL',width : 100},
				        {name : 'TRAN_BRIEF',header:'摘要',mapping : 'TRAN_BRIEF',width : 100},
				        {name : 'TRAN_BRC',header:'交易机构',mapping : 'TRAN_BRC',width : 100},
				        {name : 'TELLER',header:'交易柜员',mapping : 'TELLER',width : 100},
				        {name : 'FTF_ACCT_NO',header:'对方帐号',mapping : 'FTF_ACCT_NO',width : 100},
				        {name : 'FTF_ACC_NAME',header:'对方户名',mapping : 'FTF_ACC_NAME',width : 100}
			]
		});
	//存款账号信息listPanel2
	var listPanel2 = new Mis.Ext.CrudPanel({
		id : "listPanel2",
	    title : "存款账号信息",
	    //客户编号
		primary : "custId",
		//单选框
		singleSelect : true,
		//查询路径设置
		stUrl : basepath + '/acrmFCiDepositAct-info.json?cust_id=' + cust_id+"&base_id="+base_id+"&omain_type="+isOmainType,
		//详情的url
		detailUrl : basepath + '/acrmFCiDepositAct-info.json',
		//定义查询条件Form的高度
		seFormHeight : 60,
		
		//定义增删详情页面弹出窗口高度
		winHeight : 380,
		//宽度
		winWidth : 900,
//		width : document.body.scrollWidth-180,
//		gridHeight : document.body.clientHeight-100,
//		labelwidth:150,
		//设置分页每页显示条数，若不设置则不出现分页栏
		pagesize : 20,
		frame : true,
	//	autoScroll : true,
		//重载afterSeOneFun方法，加载一条数据后做的特殊处理
//		afterSeOneFun : function(b) {
//			//debugger;
//			Ext.getCmp('OPEN_ACCOUNT_DATE').setValue(new Date(b.OPEN_ACCOUNT_DATE.time));
//	    	Ext.getCmp('LOGOUT_ACCOUNT_DATE').setValue(new Date(b.LOGOUT_ACCOUNT_DATE.time));
//	    	Ext.getCmp('START_INTER_DATE').setValue(new Date(b.START_INTER_DATE.time));
//	    	Ext.getCmp('MATURE_DATE').setValue(new Date(b.MATURE_DATE.time));
//		},
		//查询列表字段定义，有header属性则在页面显示 
		
		//如果需要做映射需要定义store , mappingkey ,mappingvalue 三个属性
		buts :[{
			  text :'交易流水',
			  iconCls : 'detailIconCss',
			  handler:function(){
			  		records =listPanel2.grid.selModel.getSelections();
			  		if(records.length!=1){
			  		Ext.Msg.alert("系统提示信息", "请选择其中一条记录！");
			  		return;
			  		}else{
			  			var opWin = new Ext.Window({
			  				plain : true,
			  				layout : 'fit',
			  				resizable : false,
			  				draggable : true,
			  				closable : true,
			  				autoScroll:true,
			  				closeAction : 'hide',
			  				modal : true, // 模态窗口
			  				shadow : true,
			  				loadMask : true,
			  				maximizable : true,
			  				collapsible : true,
			  				titleCollapse : true,
			  				border : false,
			  				width : 800,
			  				height : 460,
			  				title : '交易流水信息',
			  				items :[tran_listPanel]
			  			});
			  			opWin.show();
			  			acct_no = records[0].data.ACCOUNT;
						tran_listPanel.stUrl=basepath + '/querytranslog.json?acct_no='+acct_no;
						tran_listPanel.grid.store.proxy.url=tran_listPanel.stUrl;
						//tran_listPanel.store.baseParams.acct_no=acct_no;
			  			tran_listPanel.store.on('beforeload',function(){
			  					var start = Ext.getCmp('tran_createDateS').getValue();
								var end = Ext.getCmp('tran_createDateE').getValue();
								if(start==''&&end!=''){
										Ext.Msg.alert('消息框','请先选择开始时间！');
										Ext.getCmp('tran_createDateE').reset();
										return false;
								}else if(end!=''&&start>end){
										Ext.Msg.alert('消息框','开始时间大于结束时间，请检查！');
										Ext.getCmp('tran_createDateE').reset();
										return false;
								}
//			  				tran_listPanel.store.proxy.api.read.url = basepath + '/querytranslog.json?acct_no='+acct_no;
			  			});
//			  			tran_listPanel.store.load();
			  			
			  		}
			  }
		}],
		gclms : [ 
		    {name : 'CUST_ID'}, 
		    {name : 'CACULATE_DATE',header : '统计日期',fomart:'Y-m-d',width : 100},
		    {name : 'ACCOUNT',header : '账号',width : 100},
		    {name : 'ACCT_NAME',header : '账户名称',width : 100},
		    {name : 'WEB_POSIT_NAME',header : '开户网点名称',width : 100},
		    {name : 'CURR',header : '币种',width : 100},
		    {name : 'AMOUNT_ORG_MONEY_TYPE',header : '余额（原币种）',type:'float',width : 100},
		    {name : 'AMOUNT',header : '余额（折人民币）',type:'float',width : 130},
		    {name : 'YEAR_AVG_AMOUNT_ORG_MONEY_TYPE',header : '年日均余额（原币种）',type:'float',width : 180,anchor : '90%'},
		    {name : 'YEAR_AVG_AMOUNT',header : '年日均余额（折人民币）',type:'float',width : 180,anchor : '90%'},
//		    {name : 'curreFirmInterest',header : '本年实付利息',width : 100},
//		    {name : 'curreMustInterest',header : '本年应付利息',width : 100},
//		    {name : 'accountStat',header : '账户状态',width : 100},
//		    {name : 'openAccountDate',header : '开户日期',type : 'date',width : 100},
//		    {name : 'logoutAccountDate',header : '销户日期',type : 'date',width : 100},
//		    {name : 'startInterDate',header : '起息日',type : 'date',width : 100},
//		    {name : 'matureDate',header : '到期日',type : 'date',width : 100},
//		    {name : 'ftp',header : 'FTP（内转价格）',width : 100},
//		    {name : 'exchangeRateMidValue',header : '汇率中间价',width : 100},
//		    {name : 'rate',header : '当前利率',width : 100},
		    {name : 'CURRE_FIRM_INTEREST'},{name : 'CURRE_MUST_INTEREST'},{name : 'ACCOUNT_STAT'},
		    {name : 'OPEN_ACCOUNT_DATE',fomart:'Y-m-d'},{name : 'LOGOUT_ACCOUNT_DATE',fomart:'Y-m-d'},{name : 'START_INTER_DATE',fomart:'Y-m-d'},
		    {name : 'MATURE_DATE',fomart:'Y-m-d'},{name : 'FTP'},{name : 'EXCHANGE_RATE_MID_VALUE'},{name : 'RATE'},
		    
		    {name : 'MONTH_AVG_AMOUNT_ORG_MONEY_TYP'},
		    {name : 'MONTH_AVG_AMOUNT'},
		    {name : 'SEASON_AVG_AMOUNT_ORG_MONEY_TY'},
		    {name : 'SEASON_AVG_AMOUNT_USD'},
		    {name : 'SEASON_AVG_AMOUNT'},
		    {name : 'INNER_CODE_CRM'},
		    {name : 'OUTER_CODE_CEN'},
		    {name : 'ACCT_TYPE'}
		    ],
		    
		 // 新增、修改、详情的form的字段
		baseForm :{labelWidth:125},
		formColums :function(){
			return new Ext.form.FieldSet({items:[
				util.layout._tr([util.form._td({name : 'ACCOUNT',xtype : 'textfield',fieldLabel : '账号'})],
						[util.form._td({name : 'AMOUNT_ORG_MONEY_TYPE',xtype : 'textfield',fieldLabel : '余额（原币种）'})],
						[util.form._td({name : 'AMOUNT',xtype : 'textfield',fieldLabel : '余额（折人民币）'})]
				),
				util.layout._tr([util.form._td({name : 'ACCT_NAME',xtype : 'textfield',fieldLabel : '账户名称'})],
						[util.form._td({name : 'YEAR_AVG_AMOUNT_ORG_MONEY_TYPE',xtype : 'textfield',fieldLabel : '年日均余额（原币种）'})],
						[util.form._td({name : 'YEAR_AVG_AMOUNT',xtype : 'textfield',fieldLabel : '年日均余额（折人民币）'})]
				),
				util.layout._tr([util.form._td({name : 'WEB_POSIT_NAME',xtype : 'textfield',fieldLabel : '开户网点名称'})],
						[util.form._td({name : 'MONTH_AVG_AMOUNT_ORG_MONEY_TYP',xtype : 'textfield',fieldLabel : '月日均余额（原币种）'})],
						[util.form._td({name : 'MONTH_AVG_AMOUNT',xtype : 'textfield',fieldLabel : '月日均余额（折人民币）'})]
				),
				util.layout._tr([util.form._td({name : 'CURR',xtype : 'textfield',fieldLabel : '币种'})],
						[util.form._td({name : 'SEASON_AVG_AMOUNT_ORG_MONEY_TY',xtype : 'textfield',fieldLabel : '季日均余额（原币种）'})],
						[util.form._td({name : 'SEASON_AVG_AMOUNT',xtype : 'textfield',fieldLabel : '季日均余额（折人民币）'})]
				),
				util.layout._tr([util.form._td({name : 'ACCOUNT_STAT',xtype : 'textfield',fieldLabel : '账户状态'})],
						[util.form._td({name : 'CURRE_FIRM_INTEREST',xtype : 'textfield',fieldLabel : '本年实付利息'})],
						[util.form._td({name : 'CURRE_MUST_INTEREST',xtype : 'textfield',fieldLabel : '本年应付利息'})]
				),
				util.layout._tr([util.form._td({name : 'ACCT_TYPE',xtype : 'textfield',fieldLabel : '账户类型'})],
						[util.form._td({name : 'OPEN_ACCOUNT_DATE',fieldLabel : '开户日期',xtype : 'datefield',readOnly : true,fomart:'Y-m-d'})],
						[util.form._td({name : 'LOGOUT_ACCOUNT_DATE',fieldLabel : '销户日期',xtype : 'datefield',readOnly : true,fomart:'Y-m-d'})]
				),
				util.layout._tr([util.form._td({name : 'ACCT_TYPE',xtype : 'textfield',fieldLabel : '账户类型',hidden:true})],
						[util.form._td({name : 'START_INTER_DATE',fieldLabel : '起息日',xtype : 'datefield',readOnly : true,fomart:'Y-m-d'})],
						[util.form._td({name : 'MATURE_DATE',fieldLabel : '到期日',xtype : 'datefield',readOnly : true,fomart:'Y-m-d'})]
				),
				util.layout._tr([util.form._td({name : 'FTP',xtype : 'textfield',fieldLabel : 'FTP（内转价格）'})],
						[util.form._td({name : 'EXCHANGE_RATE_MID_VALUE',xtype : 'textfield',fieldLabel : '汇率中间价'})],
						[util.form._td({name : 'RATE',xtype : 'textfield',fieldLabel : '当前利率'})]
				),
				util.layout._tr([util.form._td({name : 'CUST_ID',xtype : 'hidden'})]
				)
		]});}
	});
	var addSaveInfoPanel = new Ext.FormPanel({//证件信息新增PANEL
		frame : true,
	    region : 'center',
      	height : 450,
      	autoScroll : true,
		split : true,
		items : [
		         {
		        	 items :[ {  
		        		 layout:'column',
		        		 items:[
		        		        {
		        		        	columnWidth:.9,
		        		        	layout: 'form',
		        		        	items: [
		        		        	        {  xtype:'numberfield',fieldLabel:'id',name:'id',labelStyle:'text-align:right;',anchor:'90%',hidden:true},
		        		        	        {  xtype : 'combo', store:saveAccountStore,id:'saveActt',resizable : true,name : 'saveAcc',hiddenName:'saveAcc', fieldLabel :'存款账号',valueField : 'VALUE',displayField : 'NAME',mode : 'local',editable : false,
			        		        	      typeAhead : true,forceSelection : true,triggerAction : 'all',emptyText : '请选择',labelStyle:'text-align:right;',selectOnFocus : true,allowBlank:false,anchor : '90%'}, 
		        		        	        {  xtype:'numberfield',fieldLabel:'关联比例',id:'relRates',name:'relRate',labelStyle:'text-align:right;',allowBlank:false,anchor:'90%'},
		        		        	        {  xtype:'numberfield',fieldLabel:'贷款账号',id:'loanAccs',name : 'loanAcc',labelStyle:'text-align:right;',anchor:'90%',hidden:true},
		        		        	        {  xtype:'datefield',fieldLabel:'生效日期',id:'starDate',name : 'statDate',format:'Y-m-d',labelStyle:'text-align:right;',anchor:'90%'},    
		        		        	        {  xtype:'datefield',fieldLabel:'截止日期',id:'endDates',name : 'endDate',format:'Y-m-d',labelStyle:'text-align:right;',anchor:'90%'},
		        		        	        {  xtype:'datefield',fieldLabel:'修改日期',id:'updateDate',name:'updateDate',format:'Y-m-d',value:new Date(),labelStyle:'text-align:right;',anchor:'90%'},
		        		        	        {  xtype:'textfield',fieldLabel:'修改人',id:'updateUser',name:'updateUser',value:__userId,labelStyle:'text-align:right;',anchor:'90%'}
		        		        	        ]
		        		        }]
		        	 }]
		         }]
		});
	
	var saveRelaInfoWind = new Ext.Window({
		closeAction:'hide',
		closable:true,
		constrain:true,
		modal:true,
		maximizable:true,
		height:450,
		width:500,
		buttonAlign:'center',
		items : [addSaveInfoPanel],
		buttons:[{
			text:'保存',
			 handler:function(){
			 if (!addSaveInfoPanel.getForm().isValid()) {
      		   Ext.Msg.alert("系统提示信息", "输入有误或存在漏输项，请重新输入!");
      		   return false;
      	   }
			  var rates = Ext.getCmp('relRates');
			  if(rates>1){
				  Ext.Msg.alert("系统提示信息","关联比例不得大于1，请重新输入！");
			  }

			   var starDate = Ext.getCmp('starDate').getValue();
        	   var endDate = Ext.getCmp('endDates').getValue();
        	   if(starDate>endDate){
        		   Ext.Msg.alert("系统提示信息", "截止日期不得小于生效日期，请重新输入!");
          		   return false;
        	   }
        	  Ext.getCmp('loanAccs').setValue(loan_ac);
        	  var t = Ext.getCmp('saveActt').getValue();
        	  for(var i= 0;i<saveAccountInfoStore.data.items.length;i++){
        		  if(t == saveAccountInfoStore.data.items[i].data.account){
           		   Ext.Msg.alert("提示信息", "该账号已经添加，请重新输入!");
          		   return false;
        		  }
        	  }
        	  Ext.Ajax.request( {
       		   url : basepath + '/saveRelaInfo-action.json',
       		   method : 'POST',
       		   params : addSaveInfoPanel.getForm().getFieldValues(),
       		   success : checkResult,
       		   failure: checkResult
       	   });
       	   function checkResult(response) {
    		   var resultArray = Ext.util.JSON.decode(response.status);
    		   var resultError = response.responseText;
    		   if ((resultArray == 200 ||resultArray == 201)&&resultError=='') {
    			   Ext.Msg.alert('提示', '保存成功');
    			   saveAccountInfoStore.reload();
    			   saveRelaInfoWind.hide();
    			   addSaveInfoPanel.getForm().reset();
    		   } else {
    			   if(resultArray == 403){
    				   Ext.Msg.alert('提示', response.responseText);
    			   }else{
    				   Ext.Msg.alert('提示', '操作失败,失败原因:' + resultError);
    			   }
    		   }
    	   }   
		}
		},'-',{
			   text:'取消',
        	   handler:function(){
				saveRelaInfoWind.hide();
				addSaveInfoPanel.getForm().reset();
           }
		}]

	});
	
	var rownum = new Ext.grid.RowNumberer({// 定义自动当前页行号
		header : 'No.',
		width : 28
	});
	var saveAccountInfoColumns = new Ext.grid.ColumnModel([rownum,
	                                                       	 {header :'营销账户关联表ID',dataIndex:'id',sortable :true,hidden:true},
	                                                       	 {header :'贷款账号',dataIndex:'loanAcc',sortable:true,hidden:true},
	    	                                                 {header :'存款账号',dataIndex:'account',sortable : true},
	    	                                                 {header :'存款账号名称',dataIndex:'acctName',sortable:true},
	    	                                                 {header :'关联比例',dataIndex:'relRate',sortable:true},
	    	                                                 {header :'存款余额',dataIndex:'amount',sortable:true,renderer: money('0,000.00')},
	    	                                                 {header :'存款日均',dataIndex:'yearAvgAmount',sortable:true,renderer: money('0,000.00')},
	    	                                                 {header :'执行利率',dataIndex:'curreFirmInterest',sortabel:true},
	    	                                                 {header :'数据日期',dataIndex:'caculateDate',sortable:true},
	    	                                                 {header :'生效日期',dataIndex:'statDate',sortable:true},
	    	                                                 {header :'截止日期',dataIndex:'endDate',sortable:true}
	    	                                                 ]);
	
	var saveAccountInfoRecord =  new Ext.data.Record.create([  {name:'id',mapping:'ID'},
	                                                           {name:'loanAcc',mapping:'LOAN_ACC'},
	                                                           {name:'account',mapping:'ACCOUNT'},
	                                                           {name:'acctName',mapping:'ACCT_NAME'},
	                                                           {name:'relRate',mapping:'REL_RATE'},
	                                                           {name:'amount',mapping:'AMOUNT'},
	                                                           {name:'yearAvgAmount',mapping:'YEAR_AVG_AMOUNT'},
	                                                           {name:'curreFirmInterest',mapping:'CURRE_FIRM_INTEREST'},
	                                                           {name:'caculateDate',mapping:'CACULATE_DATE'},
	                                                           {name:'statDate',mapping:'STAT_DATE'},
	                                                           {name:'endDate',mapping:'END_DATE'}
	                                                   ]);
	
	var saveAccountInfoReader = new Ext.data.JsonReader({//读取json数据的panel
		totalProperty:'json.count',
		root:'json.data'
		},saveAccountInfoRecord);
	
	var saveAccountInfoStore = new Ext.data.Store({
		
		proxy:new Ext.data.HttpProxy({
			url:basepath+'/saveAccountInfoQuery-Action.json',
			method:'GET'
		}),
		reader:saveAccountInfoReader
		});

	
	
	
	
	var saveAccountInfoGrid =  new Ext.grid.GridPanel({//日程查询列表数据grid
		
		title:'关联派生存款信息',
		id:'saveAccountInfoGrid',
		store:saveAccountInfoStore,
		layout:'fit',
		frame:true,
		height:document.body.scrollHeight-100,
		loadMask:true,
		cm :saveAccountInfoColumns,
			stripeRows : true,
			loadMask : {
			msg : '正在加载表格数据,请稍等...'
	},
		tbar:[{
			text : '添加',
			iconCls : 'addIconCss',
			handler : function(){
			saveRelaInfoWind.setTitle('关联账号信息新增'); //当新增的时候将弹出的窗口的title设置为‘证件信息新增’    			
			saveRelaInfoWind.show();
		}
		},{
			text:'删除',
			iconCls : 'deleteIconCss',
			handler:function(){
			var record = saveAccountInfoGrid.getSelectionModel().getSelected();
	        	var selectLength = saveAccountInfoGrid.getSelectionModel().getSelections().length;
	        	if(selectLength<1){
	        		Ext.Msg.alert('提示','请选择要删除的信息！');}
	        	else{
	        		Ext.Msg.confirm(
		        				'请确认','继续删除吗？',
		        				function(btn, text) {
		        					if (btn == 'yes') {
		        						var selectRe;
		        						var tempId;
		        						var idStr = '';
	      			                for(var i = 0; i<selectLength;i++)//将所选择的记录的ID放入idStr中
	      			                {
	      			                	selectRe = saveAccountInfoGrid.getSelectionModel().getSelections()[i];
	      			                	tempId = selectRe.data.id;
	      			                	idStr += tempId;
	      			                	if( i != selectLength-1)
	      			                		idStr += ',';
	      			                }
	      			                Ext.Ajax.request({
	      			                	url : basepath + '/saveRelaInfo-action!batchDestroy.json',
	      			                	params : {
	      			                		'id':idStr
	      			                },
	      			                failure : function() {
	      			                	Ext.Msg.alert('提示', '操作失败');
	      			                	saveAccountInfoStore.reload();
	      			                },
	      			                success : function(){
	      			                	saveAccountInfoStore.reload();
	      			                	Ext.Msg.alert('提示', '删除成功!');
	      			                }
	      			                });		                
		        					}
		        				});
	        	}
		}
		}]
	});
	//贷款账号信息listPanel3
	var listPanel3 = new Mis.Ext.CrudPanel({
		id : "listPanel3",
	    title : "贷款账号信息",
	    //客户编号
		primary : "custId",
		//单选框
		singleSelect : true,
		//查询路径设置
		stUrl : basepath + '/acrmFCiLoanAct-info.json?cust_id=' + cust_id+"&base_id="+base_id+"&omain_type="+isOmainType,
		//详情的url
		detailUrl : basepath + '/acrmFCiLoanAct-info.json',
		//定义查询条件Form的高度
		seFormHeight : 60,
//		width : document.body.scrollWidth-180,
//		gridHeight : document.body.clientHeight-100,
		//定义增删详情页面弹出窗口高度
		winHeight : 360,
		//宽度
		winWidth : 900,
		//设置分页每页显示条数，若不设置则不出现分页栏
		pagesize : 20,
//		autoScroll : true,
		//重载afterSeOneFun方法，加载一条数据后做的特殊处理
//		afterSeOneFun : function(b) {
//			//debugger;
//			Ext.getCmp('OPEN_ACCOUNT_DATE').setValue(new Date(b.OPEN_ACCOUNT_DATE.time));
//	    	Ext.getCmp('LOGOUT_ACCOUNT_DATE').setValue(new Date(b.LOGOUT_ACCOUNT_DATE.time));
//	    	Ext.getCmp('START_INTER_DATE').setValue(new Date(b.START_INTER_DATE.time));
//	    	Ext.getCmp('MATURE_DATE').setValue(new Date(b.MATURE_DATE.time));
//		},
		//查询列表字段定义，有header属性则在页面显示 
		//如果需要做映射需要定义store , mappingkey ,mappingvalue 三个属性
				buts :[{
			  text :'交易流水',
			  iconCls : 'detailIconCss',
			  handler:function(){
			  		records =listPanel3.grid.selModel.getSelections();
			  		if(records.length!=1){
			  		Ext.Msg.alert("系统提示信息", "请选择其中一条记录！");
			  		return;
			  		}else{
			  			
			  			var opWin2 = new Ext.Window({
			  				plain : true,
			  				layout : 'fit',
			  				resizable : true,
			  				draggable : true,
			  				closable : true,
			  				closeAction : 'hide',
			  				modal : true, // 模态窗口
			  				shadow : true,
			  				loadMask : true,
			  				maximizable : true,
			  				collapsible : true,
			  				titleCollapse : true,
			  				border : false,
			  				width : 800,
			  				height : 460,
			  				title : '交易流水信息',
			  				items :[tran_listPanel]
			  			});
			  			opWin2.show();
			  			acct_no = records[0].data.ACCOUNT;
						tran_listPanel.stUrl=basepath + '/querytranslog.json?acct_no='+acct_no;
						tran_listPanel.grid.store.proxy.url=tran_listPanel.stUrl;
						//tran_listPanel.store.baseParams.acct_no=acct_no;
			  			tran_listPanel.store.on('beforeload',function(){
			  					var start = Ext.getCmp('tran_createDateS').getValue();
								var end = Ext.getCmp('tran_createDateE').getValue();
								if(start==''&&end!=''){
										Ext.Msg.alert('消息框','请先选择开始时间！');
										Ext.getCmp('tran_createDateE').reset();
										return false;
								}else if(end!=''&&start>end){
										Ext.Msg.alert('消息框','开始时间大于结束时间，请检查！');
										Ext.getCmp('tran_createDateE').reset();
										return false;
								}
			  			});
//			  			tran_listPanel.store.load();
			  		}
			  }
		},{
			 text :'关联存款账户信息查看',
			 iconCls : 'detailIconCss',
			 handler:function(){
			records =listPanel3.grid.selModel.getSelections();
	  		if(records.length!=1){
	  		Ext.Msg.alert("系统提示信息", "请选择其中一条记录！");
	  		}else{
				records =listPanel3.grid.selModel.getSelections();
				var acc = records[0].data.ACCOUNT;
				loan_ac = records[0].data.ACCOUNT;
				debugger;
				var opWin9 = new Ext.Window({
					plain : true,
					layout : 'fit',
					resizable : true,
					draggable : true,
					closable : true,
					closeAction : 'hide',
					modal : true, // 模态窗口
					shadow : true,
					loadMask : true,
					maximizable : true,
					collapsible : true,
					titleCollapse : true,
					border : false,
					width : 800,
					height : document.body.scrollHeight-38,
					items :[saveAccountInfoGrid]
				});
				
	  			opWin9.show();
	  			saveAccountInfoStore.load({
	  				params:{	
	  				'acc':acc
	  			}});
	  		}
		}
		}],
		gclms : [ 
		    {name : 'CUST_ID'}, 
		    {name : 'ACCOUNT',header : '账号',width : 100},
		    {name : 'ACCOUNT_NAME',header : '账户名称',width : 100},
		    {name : 'WEB_POSIT_NAME',header : '开户网点名称',width : 100},
		    {name : 'MONEY_TYPE',header : '币种',width : 100},
		    {name : 'CURR_FORMERLY',header : '余额（原币种）',type:'float',width : 100},
		    {name : 'AMOUNT',header : '余额',type:'float',width : 100},
		    {name : 'YEAR_AVG_AMOUNT',header : '年均余额',type:'float',width : 100},
		    {name : 'CURRE_FIRM_INTEREST',header : '本年实收利息',type:'float',width : 100},
		    {name : 'CURRE_MUST_INTEREST',header : '本年应收利息',type:'float',width : 100},
		    {name : 'ACCOUNT_STAT',header : '账户状态',width : 100},
		    {name : 'FIVE_LEVEL_TYPE',header : '五级分类',width : 100},
		    {name : 'BEF_DEGREE_OF_CONTRIBUTION'},
		    {name : 'EXCHANGE_RATE_MID_VALUE'},
		    {name : 'FTP'},
		    {name : 'LOGOUT_ACCOUNT_DATE',fomart:'Y-m-d'},
		    {name : 'MATURE_DATE',fomart:'Y-m-d'},
		    {name : 'OPEN_ACCOUNT_DATE',fomart:'Y-m-d'},
		    {name : 'RATE'},
		    {name : 'START_INTER_DATE',fomart:'Y-m-d'},
		    {name : 'STAT_DATE'},
		    {name : 'SUBJECTS'},
		    {name : 'YEAR_AVG_AMOUNT_ORG_MONEY_TYPE'},
		    {name : 'YEAR_SAVE_AMOUNT',header : '派生存款日均',type:'float',width : 100},
		    {name : 'AVG_RATE',header:'结算资金归行率(%)',width:100}


		    ],
		baseForm :{labelWidth:125},
		 // 新增、修改、详情的form的字段
		formColums :function(){
			return new Ext.form.FieldSet({items:[
				util.layout._tr([util.form._td({name : 'ACCOUNT',xtype : 'textfield',fieldLabel : '账号'})],
						[util.form._td({name : 'CURR_FORMERLY',xtype : 'textfield',fieldLabel : '余额（原币种）'})],
						[util.form._td({name : 'AMOUNT',xtype : 'textfield',fieldLabel : '余额'})]
				),
				util.layout._tr([util.form._td({name : 'ACCOUNT_NAME',xtype : 'textfield',fieldLabel : '账户名称'})],
						[util.form._td({name : 'YEAR_AVG_AMOUNT',xtype : 'textfield',fieldLabel : '年均余额'})],
						[util.form._td({name : 'YEAR_AVG_AMOUNT_ORG_MONEY_TYPE',xtype : 'textfield',fieldLabel : '年均余额（原币种）'})]
				),
				util.layout._tr([util.form._td({name : 'WEB_POSIT_NAME',xtype : 'textfield',fieldLabel : '开户网点名称'})],
						[util.form._td({name : 'CURRE_FIRM_INTEREST',xtype : 'textfield',fieldLabel : '本年实收利息'})],
						[util.form._td({name : 'CURRE_MUST_INTEREST',xtype : 'textfield',fieldLabel : '本年应收利息'})]
				),
				util.layout._tr([util.form._td({name : 'MONEY_TYPE',xtype : 'textfield',fieldLabel : '币种'})],
						[util.form._td({name : 'START_INTER_DATE',fieldLabel : '起息日',xtype : 'datefield',readOnly : true,fomart:'Y-m-d'})],
						[util.form._td({name : 'MATURE_DATE',fieldLabel : '到期日',xtype : 'datefield',readOnly : true,fomart:'Y-m-d'})]
				),
				util.layout._tr([util.form._td({name : 'ACCOUNT_STAT',xtype : 'textfield',fieldLabel : '账户状态'})],
						[util.form._td({name : 'OPEN_ACCOUNT_DATE',fieldLabel : '开户日期',xtype : 'datefield',readOnly : true,fomart:'Y-m-d'})],
						[util.form._td({name : 'LOGOUT_ACCOUNT_DATE',fieldLabel : '销户日期',xtype : 'datefield',readOnly : true,fomart:'Y-m-d'})]
				),
				util.layout._tr(
						[util.form._td({name : 'EXCHANGE_RATE_MID_VALUE',xtype : 'textfield',fieldLabel : '汇率中间价'})],
						[util.form._td({name : 'RATE',xtype : 'textfield',fieldLabel : '利率'})],
						[util.form._td({name : 'BEF_DEGREE_OF_CONTRIBUTION',xtype : 'textfield',fieldLabel : '贡献度（模拟利润）'})]
				),
				util.layout._tr([util.form._td({name : 'FIVE_LEVEL_TYPE',xtype : 'textfield',fieldLabel : '五级分类'})],
						[util.form._td({name : 'FTP',xtype : 'textfield',fieldLabel : 'FTP'})],
						[util.form._td({name : 'FTP',xtype : 'textfield',fieldLabel : 'FTP',hidden:true})]
				),
				util.layout._tr([util.form._td({name : 'CUST_ID',xtype : 'hidden'})]
				)
		]});}
	});
	var listPanel = new Ext.TabPanel({
		renderTo : oCustInfo.view_source,
		id : 'listPanel',
		activeTab : 0,//判断是否是从首页链接过来
		tabPosition : 'top',
		height : document.body.clientHeight - 30,
		items : [{
			title : '存款账户信息',
			forceFit : false,
			autoScroll : true,
			layout : 'fit',
			items : [listPanel2]
		}, {
			title : '贷款账户信息',
			forceFit : false,
			autoScroll : true,
			layout : 'fit',
			items : [listPanel3]
		}]
	});
//	// 布局模型
//	var viewport = new Ext.Panel({
//		 renderTo:oCustInfo.view_source,
//		 height:document.body.scrollHeight-30,
//		 layout : 'fit',
////		 autoScroll:true,
//		 items : [ listPanel ]
//	});

});