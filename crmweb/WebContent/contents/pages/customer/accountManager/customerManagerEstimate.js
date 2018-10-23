Ext.onReady(function() {
	Ext.QuickTips.init();
	var ss='';
	var id='';
	// 渠道类型下拉框的数据查询
	var cmeStore=util.form._store('/lookup.json?name=CUST_MANAGER_ESTI_STATUS');
	cmeStore.load();
	//
	 var xlStore = new Ext.data.ArrayStore({
	        fields:['myId','displayText'],
	        data : [['1','良好'], ['2','一般' ], ['3','较差' ]]
		});
	 var monthNum = new Ext.data.ArrayStore({
	        fields:['myId','displayText'],
	        data : [['1','一月'], ['2','二月'], ['3','三月'], ['4','四月'], ['5','五月'], ['6','六月'], ['7','七月'], ['8','八月'], ['9','九月'], ['10','十月'], ['11','十一月'], ['12','十二月']]
		});
	 var rsRecord = Ext.data.Record.create([
	    {name:'id',mapping:'ID'},
        {name:'userId',mapping:'USER_ID'},
        {name:'custMgrName',mapping:'CUST_MGR_NAME'},
        {name:'affiGroup',mapping:'AFFI_GROUP'},
        {name:'entrTime',mapping:'ENTR_TIME'},
        {name:'assMon',mapping:'ASS_MON'},
        {name:'loanCurrRelCnt',mapping:'LOAN_CURR_REL_CNT'},
        {name:'loanCurrMissCnt',mapping:'LOAN_CURR_MISS_CNT'},
        {name:'loanOveRatio',mapping:'LOAN_OVE_RATIO'},
        {name:'savEndmonCnt',mapping:'SAV_ENDMON_CNT'},
        {name:'savMondayAve',mapping:'SAV_MONDAY_AVE'},
        {name:'upmonWrkSum',mapping:'UPMON_WRK_SUM'},
        {name:'proIdeaFb',mapping:'PRO_IDEA_FB'},
        {name:'empPlan',mapping:'EMP_PLAN'},
        {name:'assTime',mapping:'ASS_TIME'},
        {name:'achiEffic',mapping:'ACHI_EFFIC'},
        {name:'chkAnalyse',mapping:'CHK_ANALYSE'},
        {name:'commuExpand',mapping:'COMMU_EXPAND'},
        {name:'reportExp',mapping:'REPORT_EXP'},
        {name:'operFlow',mapping:'OPER_FLOW'},
        {name:'leanAbility',mapping:'LEAN_ABILITY'},
        {name:'timeMgr',mapping:'TIME_MGR'},
        {name:'wrkAttitude',mapping:'WRK_ATTITUDE'},
        {name:'groupColla',mapping:'GROUP_COLLA'},
        {name:'status',mapping:'STATUS'},
        {name:'behavRule',mapping:'BEHAV_RULE'},
        {name:'sevAttitude',mapping:'SEV_ATTITUDE'},
        {name:'subjEvaluate',mapping:'SUBJ_EVALUATE'},
        {name:'evaTime',mapping:'EVA_TIME'}
        ]);
	 var rsreader = new Ext.data.JsonReader({
			successProperty : 'success',
			idProperty : 'CUST_ID',
			messageProperty : 'message',
			root : 'json.data',
			totalProperty : 'json.count'
		}, rsRecord);
		 
		
		// 最终展现的panel
		var listPanel = new Mis.Ext.CrudPanel( {
			id : "listPanel",
			title : "客户经理评估管理",
			//
			//seBaseForm ：true,
			stUrl : basepath + '/customerManagerEstimate!indexPage.json',
			// demoData :
			// {"json":{"count":9,"data":[{"UNITNAME":"总行","CREATE_ORG":"00001","CHANNEL_ID":"45396","CHANNEL_CODE":"","UPDATE_DATE":"2011-10-27","GUARANTEE":"","REMARK":"","CHANNEL_TYPE_NAME":"发改委","CHANNEL_NAME":"23","CHANNEL_FEATURE":"","ACCESS_CONDITION":"","CHANNEL_POLICY":"","CHANNEL_TYPE_ID":"10327","CREATE_USER":"admin","USERNAME":"系统管理员*","CREATE_DATE":"2011-10-27","UPDATE_USER":"admin"},{"UNITNAME":"总行","CREATE_ORG":"00001","CHANNEL_ID":"45386","CHANNEL_CODE":"","UPDATE_DATE":"2011-10-25","GUARANTEE":"","REMARK":"","CHANNEL_TYPE_NAME":"商会","CHANNEL_NAME":"7777777","CHANNEL_FEATURE":"","ACCESS_CONDITION":"","CHANNEL_POLICY":"","CHANNEL_TYPE_ID":"10328","CREATE_USER":"admin","USERNAME":"系统管理员*","CREATE_DATE":"2011-10-25","UPDATE_USER":"admin"},{"UNITNAME":"总行","CREATE_ORG":"00001","CHANNEL_ID":"45391","CHANNEL_CODE":"","UPDATE_DATE":"2011-10-25","GUARANTEE":"","REMARK":"","CHANNEL_TYPE_NAME":"商会","CHANNEL_NAME":"444444","CHANNEL_FEATURE":"","ACCESS_CONDITION":"123123","CHANNEL_POLICY":"","CHANNEL_TYPE_ID":"10328","CREATE_USER":"admin","USERNAME":"系统管理员*","CREATE_DATE":"2011-10-25","UPDATE_USER":"admin"},{"UNITNAME":"总行","CREATE_ORG":"00001","CHANNEL_ID":"45376","CHANNEL_CODE":"","UPDATE_DATE":"2011-10-24","GUARANTEE":"1","REMARK":"1","CHANNEL_TYPE_NAME":"委办局","CHANNEL_NAME":"测试2","CHANNEL_FEATURE":"1","ACCESS_CONDITION":"1","CHANNEL_POLICY":"1","CHANNEL_TYPE_ID":"10352","CREATE_USER":"admin","USERNAME":"系统管理员*","CREATE_DATE":"2011-10-09","UPDATE_USER":"admin"},{"UNITNAME":"北京管理部","CREATE_ORG":"00021","CHANNEL_ID":"17251","CHANNEL_CODE":"","UPDATE_DATE":"2011-10-14","GUARANTEE":"","REMARK":"","CHANNEL_TYPE_NAME":"委办局","CHANNEL_NAME":"新渠道22223333","CHANNEL_FEATURE":"","ACCESS_CONDITION":"","CHANNEL_POLICY":"","CHANNEL_TYPE_ID":"10352","CREATE_USER":"008755","USERNAME":"范*","CREATE_DATE":"2011-10-14","UPDATE_USER":"008755"},{"UNITNAME":"白云支行","CREATE_ORG":"04101","CHANNEL_ID":"17148","CHANNEL_CODE":"","UPDATE_DATE":"2011-10-14","GUARANTEE":"","REMARK":"","CHANNEL_TYPE_NAME":"委办局","CHANNEL_NAME":"白云山","CHANNEL_FEATURE":"","ACCESS_CONDITION":"","CHANNEL_POLICY":"","CHANNEL_TYPE_ID":"10352","CREATE_USER":"000727","USERNAME":"刘*","CREATE_DATE":"2011-10-09","UPDATE_USER":"000727"},{"UNITNAME":"总行","CREATE_ORG":"00001","CHANNEL_ID":"12367","CHANNEL_CODE":"","UPDATE_DATE":"2011-09-24","GUARANTEE":"","REMARK":"","CHANNEL_TYPE_NAME":"委办局","CHANNEL_NAME":"啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊1","CHANNEL_FEATURE":"","ACCESS_CONDITION":"","CHANNEL_POLICY":"","CHANNEL_TYPE_ID":"10352","CREATE_USER":"admin","USERNAME":"系统管理员*","CREATE_DATE":"2011-09-24","UPDATE_USER":"admin"},{"UNITNAME":"总行","CREATE_ORG":"00001","CHANNEL_ID":"45390","CHANNEL_CODE":"","UPDATE_DATE":"2011-10-25","GUARANTEE":"","REMARK":"","CHANNEL_TYPE_NAME":"财政","CHANNEL_NAME":"12212","CHANNEL_FEATURE":"","ACCESS_CONDITION":"","CHANNEL_POLICY":"","CHANNEL_TYPE_ID":"11006","CREATE_USER":"admin","USERNAME":"系统管理员*","CREATE_DATE":"2011-10-25","UPDATE_USER":"admin"},{"UNITNAME":"总行","CREATE_ORG":"00001","CHANNEL_ID":"17253","CHANNEL_CODE":"","UPDATE_DATE":"2011-10-14","GUARANTEE":"","REMARK":"","CHANNEL_TYPE_NAME":"财政","CHANNEL_NAME":"总行客户经理","CHANNEL_FEATURE":"","ACCESS_CONDITION":"","CHANNEL_POLICY":"","CHANNEL_TYPE_ID":"11006","CREATE_USER":"010514","USERNAME":"韩*","CREATE_DATE":"2011-10-14","UPDATE_USER":"010514"}]}},
			//新增URL，如果不定义则不出现新增按钮
			addUrl : basepath + '/customerManagerEstimate.json',
			detailUrl: basepath + '/customerManagerEstimate.json',
//			updateUrl : basepath + '/customerManagerEstimate.json',
//			deUrl : basepath + '/customerManagerEstimate!batchDestroy.json',
			primary : "id",
			checkbox : true,
			//定义查询条件Form的高度
			seFormHeight : 100,
			//定义增删详情页面弹出窗口高度
			winHeight : 450,
			//宽度
			winWidth : 800,
			//设置分页每页显示条数，若不设置则不出现分页栏
			pagesize : 20,
			//重载afterSeOneFun方法，加载一条数据后做的特殊处理
			createFun :function(){
			Ext.getCmp('asst').setValue(new Date());
				Ext.getCmp('user_Id').setValue(__userId);
				Ext.getCmp('custMgr_Name').setValue(__userName);
				Ext.getCmp('pdyf').on('select',function(){
					if(Ext.getCmp('pdyf').getValue()!='')
//					alert(Ext.getCmp('pdyf').value.toString().substr(0,6));
						Ext.getCmp('assMon').setValue(Ext.getCmp('pdyf').value.toString().substr(0,6));
				});
				var fenzi=Ext.getCmp('fenzi');
				var fenmu=Ext.getCmp('fenmu');
				var result=Ext.getCmp('result');
				fenzi.on('keyup',function(){
					if(fenmu.getValue()!=''&&fenmu.getValue()!=null&&fenmu.getValue()!=0){
						if(fenzi.getValue()!=''&&fenzi.getValue()!=null){
							result.setValue(fenzi.getValue()/fenmu.getValue()*100);
						}
					}
				});
				fenmu.on('keyup',function(){
					if(fenmu.getValue()!=''&&fenmu.getValue()!=null&&fenmu.getValue()!=0){
						if(fenzi.getValue()!=''&&fenzi.getValue()!=null){
							result.setValue(fenzi.getValue()/fenmu.getValue()*100);
						}
					}
				});
			},
			afterSeOneFun : function(b) {
				Ext.getCmp('pdyf').on('select',function(){
					if(Ext.getCmp('pdyf').getValue()!='')
						Ext.getCmp('assMon').setValue(Ext.getCmp('pdyf').value.toString().substr(0,6));
				});
				var fenzi=Ext.getCmp('fenzi');
				var fenmu=Ext.getCmp('fenmu');
				var result=Ext.getCmp('result');
				fenzi.on('keyup',function(){
					if(fenmu.getValue()!=''&&fenmu.getValue()!=null&&fenmu.getValue()!=0){
						if(fenzi.getValue()!=''&&fenzi.getValue()!=null){
							result.setValue(fenzi.getValue()/fenmu.getValue()*100);
						}
					}
				});
				fenmu.on('keyup',function(){
					if(fenmu.getValue()!=''&&fenmu.getValue()!=null&&fenmu.getValue()!=0){
						if(fenzi.getValue()!=''&&fenzi.getValue()!=null){
							result.setValue(fenzi.getValue()/fenmu.getValue()*100);
						}
					}
				});
				Ext.getCmp('pdyf').setValue(Ext.getCmp('assMon').getValue());
			if(b.entrTime!=''&&b.entrTime!=undefined&&b.entrTime!=null)
				Ext.getCmp('ent').setValue(new Date(b.entrTime.time));
//		    	//Ext.getCmp('assm').setValue(new Date(b.assMon.time));
//			if(b.assTime!=''&&b.assTime!=undefined&&b.assTime!=null)
//		    	Ext.getCmp('asst').setValue(new Date(b.assTime.time));
//			else 
				Ext.getCmp('asst').setValue(new Date());
			if(b.evaTime!=''&&b.evaTime!=undefined&&b.evaTime!=null)
		    	Ext.getCmp('evat').setValue(new Date(b.evaTime.time));
			},
			buts : [{
				text:'修改',
				iconCls : 'editIconCss',
				handler:function(){
				if (listPanel.grid.selModel.hasSelection()) {
					var records = listPanel.grid.selModel.getSelections();// 得到被选择的行的数组
					var recordsLen = records.length;// 得到行数组的长度
					if (recordsLen > 1) {
						Ext.Msg.alert("系统提示信息", "请选择其中一条记录进行修改！");
					} else {
						var record = listPanel.grid.getSelectionModel()
								.getSelected();
						var id = record.get(listPanel.primary);
						var status = record.get('status');
						if(status!=5&&status!=1){
							if(status==4){
								Ext.Msg.alert("系统提示信息", "已完成评估，不能修改");
							}else{
								Ext.Msg.alert("系统提示信息", "已提交上级评估，不能修改");
							}
						}else{
							listPanel.opUrl = basepath + '/customerManagerEstimate.json';
							var winButsArray = [];
							winButsArray.push({text : "保存",handler : listPanel.save, scope : listPanel});
							winButsArray.push({text : "关闭",handler : listPanel.closeWin,scope : listPanel});
							listPanel.winButs = winButsArray;
							listPanel.showWin();
//							alert(Ext.getCmp('assMon').getValue());
							if(listPanel.editFun)
								listPanel.editFun();
							if(listPanel.stUrl){
								listPanel.seOneRecord(id);
							}
							else if(listPanel.demoData)
								listPanel.fp.getForm().loadRecord(record);
						}
						
					}
				} else {
					Ext.Msg.alert("提示", "请先选择要修改的记录!");
				}
			}
			},'-',{
				text:'删除',
				iconCls : 'deleteIconCss',
				handler:function(){
				if (listPanel.grid.selModel.hasSelection()) {
					Ext.MessageBox
							.confirm(
									'系统提示信息',
									'确定要删除所选的记录吗?',
									function(buttonobj) {
										if (buttonobj == 'yes'
												&& listPanel.primary) {
											var flag=0;
											var records = listPanel.grid.selModel
													.getSelections();// 得到被选择的行的数组
											var selectLength = records.length;// 得到行数组的长度
											var idStr = '';
											for ( var i = 0; i < selectLength; i++) {
												selectRe = records[i];
												tempId = selectRe
														.get(listPanel.primary);
												var status = selectRe.get('status');
												if(status!=5){
													flag = 1;
												}
												idStr += tempId;
												if (i != selectLength - 1)
													idStr += ',';
											};
											if(flag==0){
												
												Ext.Ajax
												.request({
													url : basepath + '/customerManagerEstimate!batchDestroy.json',
													params : {
													idStr : idStr
												},
												waitMsg : '正在保存数据,请等待...', // 显示读盘的动画效果，执行完成后效果消失
												method : 'POST',
												scope : listPanel,
												success : function() {
													Ext.Msg.alert('提示', '操作成功');
													listPanel.loadCurrData();
												},
												failure : function() {
													Ext.Msg.alert('提示', '操作失败');
													listPanel.loadCurrData();
												}
												});
											}else{
												Ext.Msg.alert("系统提示信息", "已提交的记录不能删除！");
											}
										}
									}, listPanel);
			}}
			},'-',
{
						text :'提交评估',
						iconCls:'completeIconCss',
						handler :function(){
				    	   if (listPanel.grid.selModel.hasSelection()) {
								var records = listPanel.grid.selModel.getSelections();// 得到被选择的行的数组
								var recordsLen = records.length;// 得到行数组的长度
								if (recordsLen > 1) {
									Ext.Msg.alert("系统提示信息", "请选择其中一条记录！");
								} else {
									var record = listPanel.grid.getSelectionModel()
											.getSelected();
									if(record.get('status')==5){
										
										var id = record.get(listPanel.primary);
										Ext.Ajax.request({
											url : basepath + '/customerManagerEstimate!initFlow.json',
											method : 'POST',
											params : {
											instanceid:id,
											custMgrName:record.get('custMgrName')
										},
										waitMsg : '正在提交申请,请等待...', // 显示读盘的动画效果，执行完成后效果消失
										success : function() {
//											Ext.Msg.alert('提示', '操作成功');
											//直接提交到主管评价节点
											var url = basepath+'/echaincommonservlet?method=echainflowdemo&actionType=submit&instanceid='+id+'&nodeid=12_a6&nextnodeid=12_a4&userid='+__userId+'&orgid='+__units+'';
											var retObj = window.showModalDialog(url,'result','dialogHeight:300px;dialogWidth:600px;help:no;resizable:no;status:no;');
											listPanel.loadCurrData();
												
										},
										failure : function() {
											Ext.Msg.alert('提示', '操作失败');
											listPanel.loadCurrData();
										}
										});
									}else{
										Ext.Msg.alert("系统提示信息", "该记录已经提交！");
									}
								}
						}else{
							Ext.Msg.alert("提示", "请先选择要提交的记录!");
						}
				       }
				}
					],
			// 查询字段定义，若不定义则不出现查询条件From
			selectItems :new Ext.form.FieldSet({items:[
				util.layout._tr([util.form._td({name : 'custMgrName',xtype : 'textfield',fieldLabel : '客户经理名称',columnWidth : .25,anchor:'95%'})],
						[util.form._td({	
							fieldLabel: '评定月份',
							name: 'assMon2',
							id : 'assMon2',
							xtype:'datefield',
							columnWidth : .25,
							editable:false,
							labelStyle: 'text-align:right;',
							format:'Ym',
							anchor : '95%'
						})]
//								[util.form._td({name : 'createDateS',xtype : 'datefield',fieldLabel : '建立日期'})],
//								[util.form._td({name : 'createDateE',xtype : 'datefield',fieldLabel : '至'})	]
								),
								util.layout._tr(
										[util.form._td({id:'assMon3',columnWidth : .25,name : 'assMon',xtype : 'textfield',fieldLabel : '评定月份',hidden:true})]
)
			]}),
	
			//查询列表字段定义，有header属性则在页面显示
			//如果需要做映射需要定义store , mappingkey ,mappingvalue 三个属性
			gclms : [ 
			    {name : 'id'}, 
			    {name : 'userId'},
			    {name : 'savEndmonCnt'},
			    {name : 'custMgrName',header : '客户经理名称'},  
			    {name : 'affiGroup',header : '所在团队'}, 
				{name : 'loanCurrRelCnt',header : '贷款本月放款笔数'},
				{name : 'loanCurrMissCnt',header : '贷款本月任务笔数'},
				{name : 'loanOveRatio',header : '贷款完成率'},
				{name : 'savMondayAve',header:'存款月日均'},
				{name : 'assMon',header:'评定月份'},
				{name : 'status',header:'评估状态',type :'mapping',store : cmeStore, mappingkey : 'key',mappingvalue : 'value'},
			    {name : 'entrTime',type:'date'},
			    {name : 'upmonWrkSum'},
			    {name : 'proIdeaFb'},
			    {name : 'empPlan'},
			    {name : 'assTime',type:'date'},
				{name:'chkAnalyse'},
				{name:'reportExp'},
				{name:'leanAbility'},
				{name:'wrkAttitude'},
				{name:'behavRule'},
				{name:'achiEffic'},
				{name:'commuExpand'},
				{name:'operFlow'},
				{name:'timeMgr'},
				{name:'groupColla'},
				{name:'sevAttitude'},
				{name:'subjEvaluate'},
				{name:'evaTime',type:'date'}
				//{name : 'assTime',header:'评定时间',type : 'date'}
//				{name : 'channelPolicy'},
//				{name : 'guarantee'},
//				{name : 'remark'},
//				{name : 'updateDate'}
			],
			
			// 新增、修改、详情的form的字段
			formColums :function(){
				return new Ext.form.FieldSet(
					{title:'客户经理评估录入',items:[
					util.layout._tr([util.form._td({id:'user_Id',name : 'userId',xtype : 'textfield',readOnly:true,fieldLabel : '客户经理编号',value:__userId})],
							[util.form._td({id:'custMgr_Name',name : 'custMgrName',xtype : 'textfield',readOnly:true,fieldLabel : '客户经理名称',value:__userName})]
									),
					util.layout._tr([util.form._td({name : 'affiGroup',fieldLabel : '所在团队',xtype : 'textfield'})],
									[util.form._td({id : 'ent',name : 'entrTime',fieldLabel : '入行时间',xtype : 'datefield'})]
					),
					util.layout._tr(//[util.form._td({name : 'assMon',fieldLabel : '评定月份',xtype : 'numberfield'})],
							[util.form._td({id : 'pdyf',name : 'assMon1',fieldLabel : '评定月份',xtype : 'datefield',format:'Ym'})],
								[util.form._td({id:'fenzi',name : 'loanCurrRelCnt',fieldLabel : '贷款本月放款笔数',xtype : 'numberfield',value:0,minValue:0,allowDecimals:false})]
					),
					util.layout._tr([util.form._td({id:'fenmu',name : 'loanCurrMissCnt',xtype : 'numberfield',fieldLabel : '贷款本月任务笔数',value:0,minValue:0,allowDecimals:false})],
								[util.form._td({id:'result',name : 'loanOveRatio',xtype : 'numberfield',fieldLabel : '贷款完成率',value:0,minValue:0,readOnly:true})]
					),
					util.layout._tr([util.form._td({name : 'savEndmonCnt',xtype : 'numberfield',fieldLabel : '存款月末时点数',value:0})],
							[util.form._td({name : 'savMondayAve',xtype : 'numberfield',fieldLabel : '存款月日均',value:0})]
					),
					util.layout._tr([util.form._td({name : 'upmonWrkSum',fieldLabel : '上月工作总结',xtype : 'textarea',maxLength : 400,anchor : '95%'})]
					),
					util.layout._tr([util.form._td({name : 'proIdeaFb',fieldLabel : '问题意见反馈',xtype : 'textarea',maxLength : 400,anchor : '95%'})]
					),
					util.layout._tr([util.form._td({name : 'empPlan',fieldLabel : '职业规划',xtype : 'textarea',maxLength : 400,anchor : '95%'})]
					),
					util.layout._tr([util.form._td({id:'asst',name : 'assTime',xtype : 'datefield',fieldLabel : '评定时间',anchor : '95%'})]
					),
					util.layout._tr([util.form._td({	
						id:'assMon',
                        name: 'assMon',
                        xtype:'textfield',hidden:true
                    })]
					),
					util.layout._tr([util.form._td({name : 'id',xtype : 'hidden'})],
							[util.form._td({name : 'status',value:'5',xtype : 'textfield',hidden:true},
									{name : 'chkAnalyse',xtype : 'textfield',hidden:true},
									{name : 'reportExp',xtype : 'textfield',hidden:true},
									{name : 'leanAbility',xtype : 'textfield',hidden:true},
									{name : 'wrkAttitude',xtype : 'textfield',hidden:true},
									{name : 'behavRule',xtype : 'textfield',hidden:true},
									{name : 'achiEffic',xtype : 'textfield',hidden:true},
									{name : 'commuExpand',xtype : 'textfield',hidden:true},
									{name : 'operFlow',xtype : 'textfield',hidden:true},
									{name : 'timeMgr',xtype : 'textfield',hidden:true},
									{name : 'groupColla',xtype : 'textfield',hidden:true},
									{name : 'sevAttitude',xtype : 'textfield',hidden:true},
									{name : 'subjEvaluate',xtype : 'textfield',hidden:true},
									{id:'evat',name : 'evaTime',xtype : 'datefield',hidden:true})]
					)
				]}

								)}

		});
		
		
		
		// 布局模型
		var viewport = new Ext.Viewport( {
			layout : 'fit',
			items : [ listPanel ]
		});

		Ext.getCmp('assMon2').addListener('select',function(){
		if(Ext.getCmp('assMon2').value!='')
//			alert(Ext.getCmp('pdyf').value.toString().substr(0,6));
			Ext.getCmp('assMon3').setValue(Ext.getCmp('assMon2').value.toString().substr(0,6));
		});
		
//		listPanel.store.load({callback:function(){
//			Ext.Msg.alert('debug',listPanel.store.getAt(4))	;	
//			
//		}});
		
	});