Ext.apply(Ext.form.VTypes, {
    daterange : function(val, field) {
        var date = field.parseDate(val);

        if(!date){
            return false;
        }
        if (field.startDateField && (!this.dateRangeMax || (date.getTime() != this.dateRangeMax.getTime()))) {
            var start = Ext.getCmp(field.startDateField);
            start.setMaxValue(date);
            start.validate();
            this.dateRangeMax = date;
        }
        else if (field.endDateField && (!this.dateRangeMin || (date.getTime() != this.dateRangeMin.getTime()))) {
            var end = Ext.getCmp(field.endDateField);
            end.setMinValue(date);
            end.validate();
            this.dateRangeMin = date;
        }
        /*
         * Always return true since we're only using this vtype to set the
         * min/max allowed values (these are tested for after the vtype test)
         */
        return true;
    },

    password : function(val, field) {
        if (field.initialPassField) {
            var pwd = Ext.getCmp(field.initialPassField);
            return (val == pwd.getValue());
        }
        return true;
    },

    passwordText : 'Passwords do not match'
});

Ext.onReady(function() {
	Ext.QuickTips.init();
	var ss = '';
	var id = '';
	// 渠道类型下拉框的数据查询
	var cmeStore = util.form._store('/lookup.json?name=CUST_MANAGER_ESTI_STATUS');
	cmeStore.load();
	
	//var absentKindStore = util.form._store('/lookup.json?name=ABSENT_KIND');
	var absentKindStore = new Ext.data.Store( {
		restful : true,
		autoLoad : true,
		proxy : new Ext.data.HttpProxy( {
			url : basepath + '/lookup.json?name=ABSENT_KIND'
		}),
		reader : new Ext.data.JsonReader( {
			root : 'JSON'
		},['key','value'])
	});
	absentKindStore.load();
	//debugger;
	var spStore = util.form._store('/lookup.json?name=ABSENT_SP_STAT');
	spStore.load();
	                          		

	// 最终展现的panel
	var listPanel = new Mis.Ext.CrudPanel({
		id: "listPanel",
		title: "客户经理请假",
		//
		//seBaseForm ：true,
		stUrl: basepath + '/ocrmFCmAbsentInfo!indexPage.json',
		// demoData :
		// {"json":{"count":9,"data":[{"UNITNAME":"总行","CREATE_ORG":"00001","CHANNEL_ID":"45396","CHANNEL_CODE":"","UPDATE_DATE":"2011-10-27","GUARANTEE":"","REMARK":"","CHANNEL_TYPE_NAME":"发改委","CHANNEL_NAME":"23","CHANNEL_FEATURE":"","ACCESS_CONDITION":"","CHANNEL_POLICY":"","CHANNEL_TYPE_ID":"10327","CREATE_USER":"admin","USERNAME":"系统管理员*","CREATE_DATE":"2011-10-27","UPDATE_USER":"admin"},{"UNITNAME":"总行","CREATE_ORG":"00001","CHANNEL_ID":"45386","CHANNEL_CODE":"","UPDATE_DATE":"2011-10-25","GUARANTEE":"","REMARK":"","CHANNEL_TYPE_NAME":"商会","CHANNEL_NAME":"7777777","CHANNEL_FEATURE":"","ACCESS_CONDITION":"","CHANNEL_POLICY":"","CHANNEL_TYPE_ID":"10328","CREATE_USER":"admin","USERNAME":"系统管理员*","CREATE_DATE":"2011-10-25","UPDATE_USER":"admin"},{"UNITNAME":"总行","CREATE_ORG":"00001","CHANNEL_ID":"45391","CHANNEL_CODE":"","UPDATE_DATE":"2011-10-25","GUARANTEE":"","REMARK":"","CHANNEL_TYPE_NAME":"商会","CHANNEL_NAME":"444444","CHANNEL_FEATURE":"","ACCESS_CONDITION":"123123","CHANNEL_POLICY":"","CHANNEL_TYPE_ID":"10328","CREATE_USER":"admin","USERNAME":"系统管理员*","CREATE_DATE":"2011-10-25","UPDATE_USER":"admin"},{"UNITNAME":"总行","CREATE_ORG":"00001","CHANNEL_ID":"45376","CHANNEL_CODE":"","UPDATE_DATE":"2011-10-24","GUARANTEE":"1","REMARK":"1","CHANNEL_TYPE_NAME":"委办局","CHANNEL_NAME":"测试2","CHANNEL_FEATURE":"1","ACCESS_CONDITION":"1","CHANNEL_POLICY":"1","CHANNEL_TYPE_ID":"10352","CREATE_USER":"admin","USERNAME":"系统管理员*","CREATE_DATE":"2011-10-09","UPDATE_USER":"admin"},{"UNITNAME":"北京管理部","CREATE_ORG":"00021","CHANNEL_ID":"17251","CHANNEL_CODE":"","UPDATE_DATE":"2011-10-14","GUARANTEE":"","REMARK":"","CHANNEL_TYPE_NAME":"委办局","CHANNEL_NAME":"新渠道22223333","CHANNEL_FEATURE":"","ACCESS_CONDITION":"","CHANNEL_POLICY":"","CHANNEL_TYPE_ID":"10352","CREATE_USER":"008755","USERNAME":"范*","CREATE_DATE":"2011-10-14","UPDATE_USER":"008755"},{"UNITNAME":"白云支行","CREATE_ORG":"04101","CHANNEL_ID":"17148","CHANNEL_CODE":"","UPDATE_DATE":"2011-10-14","GUARANTEE":"","REMARK":"","CHANNEL_TYPE_NAME":"委办局","CHANNEL_NAME":"白云山","CHANNEL_FEATURE":"","ACCESS_CONDITION":"","CHANNEL_POLICY":"","CHANNEL_TYPE_ID":"10352","CREATE_USER":"000727","USERNAME":"刘*","CREATE_DATE":"2011-10-09","UPDATE_USER":"000727"},{"UNITNAME":"总行","CREATE_ORG":"00001","CHANNEL_ID":"12367","CHANNEL_CODE":"","UPDATE_DATE":"2011-09-24","GUARANTEE":"","REMARK":"","CHANNEL_TYPE_NAME":"委办局","CHANNEL_NAME":"啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊1","CHANNEL_FEATURE":"","ACCESS_CONDITION":"","CHANNEL_POLICY":"","CHANNEL_TYPE_ID":"10352","CREATE_USER":"admin","USERNAME":"系统管理员*","CREATE_DATE":"2011-09-24","UPDATE_USER":"admin"},{"UNITNAME":"总行","CREATE_ORG":"00001","CHANNEL_ID":"45390","CHANNEL_CODE":"","UPDATE_DATE":"2011-10-25","GUARANTEE":"","REMARK":"","CHANNEL_TYPE_NAME":"财政","CHANNEL_NAME":"12212","CHANNEL_FEATURE":"","ACCESS_CONDITION":"","CHANNEL_POLICY":"","CHANNEL_TYPE_ID":"11006","CREATE_USER":"admin","USERNAME":"系统管理员*","CREATE_DATE":"2011-10-25","UPDATE_USER":"admin"},{"UNITNAME":"总行","CREATE_ORG":"00001","CHANNEL_ID":"17253","CHANNEL_CODE":"","UPDATE_DATE":"2011-10-14","GUARANTEE":"","REMARK":"","CHANNEL_TYPE_NAME":"财政","CHANNEL_NAME":"总行客户经理","CHANNEL_FEATURE":"","ACCESS_CONDITION":"","CHANNEL_POLICY":"","CHANNEL_TYPE_ID":"11006","CREATE_USER":"010514","USERNAME":"韩*","CREATE_DATE":"2011-10-14","UPDATE_USER":"010514"}]}},
		//新增URL，如果不定义则不出现新增按钮
		addUrl: basepath + '/ocrmFCmAbsentInfo.json',
		updateUrl: basepath + '/ocrmFCmAbsentInfo.json',
		deUrl: basepath + '/ocrmFCmAbsentInfo!batchDestroy.json',
		primary: "id",
		checkbox: true,
		//定义查询条件Form的高度
//		seFormHeight: 80,
		//定义增删详情页面弹出窗口高度
		winHeight: 400,
		//宽度
		winWidth: 800,
		//设置分页每页显示条数，若不设置则不出现分页栏
		pagesize: 20,
		//重载afterSeOneFun方法，加载一条数据后做的特殊处理
		afterSeOneFun: function(b) {
			//debugger;
			//Ext.getCmp('ent').setValue(new Date(b.entrTime.time));
			//Ext.getCmp('assm').setValue(new Date(b.assMon.time));
			//Ext.getCmp('asst').setValue(new Date(b.assTime.time));
			//Ext.getCmp('evat').setValue(new Date(b.evaTime.time));
		},
		afterSeOneFun : function(b) {
			//debugger;
			Ext.getCmp('begTime').setValue(new Date(b.begTime.time));
	    	Ext.getCmp('endTime').setValue(new Date(b.endTime.time));
		},
		createFun : function(){
			Ext.getCmp('userId').setValue(JsContext._userId);
			Ext.getCmp('userName').setValue(__userName);
			Ext.getCmp('orgId').setValue(JsContext._orgId);
			Ext.getCmp('orgName').setValue(JsContext._unitname);
		},edit : function() {
			
			if (this.grid.selModel.hasSelection()) {
				var records = this.grid.selModel.getSelections();// 得到被选择的行的数组
				var recordsLen = records.length;// 得到行数组的长度
				if (recordsLen > 1) {
					Ext.Msg.alert("系统提示信息", "请选择其中一条记录进行修改！");
				} else {
					var record = this.grid.getSelectionModel()
							.getSelected();
					var idStr = '';
					for ( var i = 0; i < recordsLen; i++) {
						selectRe = records[i];
						
						if(selectRe.data.absentStat!=0){
							Ext.Msg.alert("系统提示信息", "该记录已进入审批流程，不可修改！");
							return;
						}
					}
					var id = record.get(this.primary);
					this.opUrl = this.updateUrl;
					var winButsArray = [];
					winButsArray.push({text : "保存",handler : this.save, scope : this});
					winButsArray.push({text : "关闭",handler : this.closeWin,scope : this});
		    		this.winButs = winButsArray;
		    		this.showWin();
		    		if(this.editFun)
						this.editFun();
		    		if(this.stUrl)
		    			this.seOneRecord(id);
		    		else if(this.demoData)
		    			this.fp.getForm().loadRecord(record);
					
				}
			} else {
				Ext.Msg.alert("提示", "请先选择要修改的记录!");
			}
		},remove : function(pid) {
			//alert("ok!");
			if (this.grid.selModel.hasSelection()) {
				Ext.MessageBox
						.confirm(
								'系统提示信息',
								'确定要删除所选的记录吗?',
								function(buttonobj) {
									if (buttonobj == 'yes'
											&& this.primary) {
										var records = this.grid.selModel
												.getSelections();// 得到被选择的行的数组
										var selectLength = records.length;// 得到行数组的长度
										var idStr = '';
										for ( var i = 0; i < selectLength; i++) {
											selectRe = records[i];
											
											if(selectRe.data.absentStat!=0){
												Ext.Msg.alert("系统提示信息", "该记录已进入审批流程，不可删除！");
												return;
											}
											tempId = selectRe.get(this.primary);
											idStr += tempId;
											if (i != selectLength - 1)
												idStr += ',';
										};
										Ext.Ajax.request({
													url : this.deUrl,
													params : {
														idStr : idStr
													},
													waitMsg : '正在保存数据,请等待...', // 显示读盘的动画效果，执行完成后效果消失
													method : 'POST',
													scope : this,
													success : function() {
														Ext.Msg.alert('提示', '操作成功');
														this.loadCurrData();
													},
													failure : function() {
														Ext.Msg.alert('提示', '操作失败');
														this.loadCurrData();
													}
												});
									}
								}, this);
			} else {
				Ext.Msg.alert("提示", "请先选择要删除的行!");
			}
		},
		buts: [
			{
			text :'提交申请',
			tooltip: '提交申请',
			iconCls:'completeIconCss',
			handler :function(){
				debugger;
			    	   if (listPanel.grid.selModel.hasSelection()) {
							var records = listPanel.grid.selModel.getSelections();								
							var recordsLen = records.length;// 得到行数组的长度
							if (recordsLen > 1) {
								Ext.Msg.alert("系统提示信息", "请选择其中一条记录！");
							} else {
								var record = listPanel.grid.getSelectionModel().getSelected();
								var id = record.get(listPanel.primary);//获取该条记录的主键
								if(records[0].data.absentStat!=0){
									Ext.Msg.alert("系统提示信息", "该记录已提交申请，不可重复提交！");
									return;
								}
								Ext.Ajax.request({
									url : basepath + '/ocrmFCmAbsentInfo!initFlow.json',
									method : 'POST',
									params : {
										instanceid:id //将id传给后台关联流程的实例号（唯一）
									},
									waitMsg : '正在提交申请,请等待...',										
									success : function() {
										Ext.Ajax.request({
											url : basepath + '/ocrmFCmAbsentInfo!initFlowJob.json',
											method : 'POST',
											params : {
												instanceid:id //将id传给后台关联流程的实例号（唯一）
											},success : function() {
												Ext.Msg.alert('提示', '提交成功!');	
											},	
											failure : function() {
												Ext.Msg.alert('提示', '提交失败,请手动到代办任务中提交!');	
											}
										});
										listPanel.loadCurrData();
									},
									failure : function() {
										Ext.Msg.alert('提示', '操作失败');
										listPanel.loadCurrData();
									}
								});
							}
					}
			}
		}
		],
		// 查询字段定义，若不定义则不出现查询条件From
		selectItems: {
		    
			items: [util.layout._tr([util.form._td({
				name: 'userName',
				xtype: 'textfield',
				fieldLabel: '客户经理名称'
			})], [util.form._td({
				name: 'absentKind',
				xtype: 'combo',
				store: absentKindStore,
				fieldLabel: '假别',
				valueField : 'key',
				displayField : 'value'
			})], [util.form._td({
				name: 'absentStat',
				xtype: 'combo',
				store: spStore,
				fieldLabel: '流程状态',
				valueField : 'key',
				displayField : 'value'
			})]
			//								[util.form._td({name : 'createDateS',xtype : 'datefield',fieldLabel : '建立日期'})],
			//								[util.form._td({name : 'createDateE',xtype : 'datefield',fieldLabel : '至'})	]
			)]
		},

		//查询列表字段定义，有header属性则在页面显示
		//如果需要做映射需要定义store , mappingkey ,mappingvalue 三个属性
		gclms: [{
			name: 'id'
		},
		{
			name: 'absentStat',
			header: '流程状态',
			type :'mapping',store : spStore, mappingkey : 'key',mappingvalue : 'value'
		},
		{
			name: 'absentKind',
			header: '假别',
			type :'mapping',store : absentKindStore, mappingkey : 'key',mappingvalue : 'value'
		},
		{
			name: 'userId',
			header: '客户经理编号'
		},
		{
			name: 'userName',
			header: '客户经理名称'
		},
		{
			name: 'orgId',
			header: '机构号'
		},
		{
			name: 'orgName',
			header: '机构名称'
		},
		{
			name: 'begTime',
			header: '请假起始时间',
			type : 'date'
		},
		{
			name: 'endTime',
			header: '请假结束时间',
			type : 'date'
			
		},
		{
			name: 'term',
			header: '请假时间'
		},
		{
			name: 'cause',
			header: '请假事由'
		}

		],

		// 新增、修改、详情的form的字段
		formColums: function() {
			return new Ext.form.FieldSet({
				title: '客户经理请假录入',
				items: [
				        util.layout._tr([
				            util.form._td({
							name: 'userId',
							id:'userId',
							xtype: 'textfield',
							fieldLabel: '客户经理编号',
								readOnly:true
						})], [util.form._td({
							fieldLabel: '客户经理名称',
							id:'userName',
							xtype:'textfield',
							//id: 'USER_NAME',
							//labelStyle: 'text-align:left;',
							name: 'userName',
							readOnly:true//,
							//store: store, 
							//singleSelected:true,
							//anchor: '90%'
						})]),
						util.layout._tr([util.form._td({
							name: 'orgId',
							fieldLabel: '机构号',
							id:'orgId',
							xtype: 'textfield',
							readOnly:true
						})], [util.form._td({
							name: 'orgName',
							id:'orgName',
							fieldLabel: '机构名称',
							xtype: 'textfield',
							readOnly:true
						})]),
						util.layout._tr([util.form._td({
							id: 'begTime',
							name: 'begTime',
							fieldLabel: '请假起始时间',
							allowBlank : false,
							vtype:'daterange',
							endDateField:'endTime',
							xtype: 'datefield',
							format:'Y-m-d'
						})], [util.form._td({
							id: 'endTime',
							name: 'endTime',
							fieldLabel: '请假结束时间',
							allowBlank : false,
							xtype: 'datefield',
							vtype:'daterange',
							startDateField:'begTime',
							format:'Y-m-d'
						})]),
						util.layout._tr([util.form._td({
							name: 'absentKind',
							xtype: 'combo',
							store: absentKindStore,
							allowBlank : false,
							fieldLabel: '假别',
							valueField : 'key',
							displayField : 'value'
						})], [util.form._td({
							name: 'term',
							xtype: 'numberfield',
							allowBlank : false,
							fieldLabel: '请假天数'
						})]), 
						util.layout._tr([util.form._td({
							name: 'cause',
							xtype: 'textarea',
							fieldLabel: '请假事由',
							allowBlank : false,
							maxLength:250,
							grow: true,
							anchor: '95%'
						})]), 
						util.layout._tr([util.form._td({
							name: 'id',
							xtype: 'hidden'
						})],[util.form._td({
							name: 'absentStat',
							xtype: 'hidden'
						})])
				]
			}

			)
		}

	});

//	Ext.getCmp("cddd").addListener("click",
//	function() {
//		//alert(listPanel.grid.getSelectionModel().getSelected().get(listPanel.primary));
//		simple.getForm().load({
//			restful: true,
//			url: basepath + '/custManaEs.json',
//			params: {
//				'condition': Ext.encode({
//					id: listPanel.grid.getSelectionModel().getSelected().get(listPanel.primary)
//				})
//
//			},
//			method: 'GET',
//			success: function(b, c) {
//				Ext.getCmp('ent1').setValue(c.reader.jsonData.json.data[0].ENTR_TIME.substring(0, 10));
//				Ext.getCmp('asst1').setValue(c.reader.jsonData.json.data[0].ASS_TIME.substring(0, 10));
//			}
//		});
//	});
	// 布局模型
	var viewport = new Ext.Viewport({
		layout: 'fit',
		items: [listPanel]
	});

	
});