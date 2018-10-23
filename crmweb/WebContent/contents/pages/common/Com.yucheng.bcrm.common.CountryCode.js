Ext.ns('Com.yucheng.bcrm.common');
/**
 * 客户属性数据字典放大镜
 * @author:liliang
 * @since:2015.02.06
 */
Com.yucheng.bcrm.common.CountryCode = Ext.extend(Ext.form.TwinTriggerField, {
	
	initComponent : function(){
		Com.yucheng.bcrm.common.CountryCode.superclass.initComponent.call(this);
	},
	
	onRender : function(ct, position){
		Com.yucheng.bcrm.common.CountryCode.superclass.onRender.call(this, ct, position);
		if(this.hiddenName){
			var ownerForm = this;
			while(ownerForm.ownerCt && !Ext.instanceOf(ownerForm.ownerCt,'form')/*&&!Ext.instanceOf(ownerForm.ownerCt,'toolbar')*/){				//根据条件查询放大镜控件的最外层容器
				ownerForm = ownerForm.ownerCt;
			};
			if(Ext.instanceOf(ownerForm.ownerCt,'form')){										//判断父容器是否为form类型
				ownerForm = ownerForm.ownerCt;
				this.ownerForm = ownerForm;
				if(ownerForm.getForm().findField(this.hiddenName)){								//如果已经创建隐藏域
					this.hiddenField = ownerForm.getForm().findField(this.hiddenName);
				}else {																			//如果未创建隐藏域，则根据hiddenName属性创建隐藏域
					this.hiddenField = ownerForm.add({
						xtype : 'hidden',
						name: this.hiddenName
					});
				}
			}
		}
	},
	callback : false,							//点击确定按钮后回调函数。Type： function
	userId : '',
	validationEvent : false,
	validateOnBlur : false,
	trigger1Class : 'x-form-clear-trigger',
	trigger2Class : 'x-form-search-trigger',
	hideTrigger1 : true,
	width : 180,
	paramName : 'query',
	adminZoneWindow : false,
	editable : false,
//	searchType:'ALL',
	checkBox:'',
	hiddenName:false, 
	listeners:{//增加鼠标点击放大镜输入框触发onTrigger2Click事件
		focus:function(){
			if(!this.disabled){ //禁用的放大镜不允许弹出选择
				this.onTrigger2Click();
			}
		}
	},
	onTrigger2Click : function() {
		if(this.disabled){ //禁用的放大镜不允许弹出选择
			return;
		}
		var _this = this;
		if (_this.adminZoneWindow) {
			_this.adminZoneWindow.show();
			return;
		}	

		/**
		 * 查询方法定义
		 */
		var searchFunction = function(){
			var indexId = JsContext.indexId;
//			debugger;
//			var parameters = _this.zonePanel.getForm().getFieldValues(false);
			var parameters = {fLookupId:indexId};
			_this.zoneStore.removeAll();
			_this.zoneStore.load({
				params:{
					'condition':Ext.encode(parameters),
					start:0,
					limit: parseInt(_this.pagesize_combo.getValue())
				}
			});
		};
		var searchField = _this;
		/**
		 * 任务查询面板
		 */
//		_this.zonePanel = new Ext.form.FormPanel({//查询panel
//			height:90,
//			labelWidth:100,//label的宽度
//			labelAlign:'right',
//			frame:true,
//			autoScroll : true,
//			region:'north',
//			split:true,
//			buttonAlign:'center',
//			items:[{
//				layout:'column',
//				items:[{
//					columnWidth:.5,
//					layout:'form',
//					items:[
//						{xtype:'textfield',name:'F_VALUE',fieldLabel:'国家或地区名称',anchor:'90%'}
//					]
//				},{
//					columnWidth:.5,
//					layout:'form',
//					items:[
//						{xtype:'textfield',name:'F_CODE',fieldLabel:'国家或地区代码',anchor:'90%'}
//					]
//				}]
//			}],
//			buttons:[{
//				text:'查询',
//				handler:searchFunction
//			},{
//				text:'重置',
//				handler:function(){
//					_this.zonePanel.getForm().reset();
//					searchFunction();
//				}
//			}]
//		});
		// 定义自动当前页行号
		_this.rownum = new Ext.grid.RowNumberer({
			header : 'No.',
			width : 28
		});
		//复选框
		var sm = new Ext.grid.CheckboxSelectionModel({
			singleSelect:_this.singleSelect
		});
		_this.zoneCm = new Ext.grid.ColumnModel([
		    _this.rownum,
		    {header:'指标值ID',dataIndex:'ID',width:100,sortable : true, hidden:'true'},
		    {header:'属性ID',dataIndex:'ATTRI_ID',width:100,sortable : true, hidden:'true'},
		    {header:'指标值',dataIndex:'INDEX_VALUE',width:200,sortable : true},	
		    {header:'指标值名称',dataIndex:'INDEX_VALUE_NAME',width:250,sortable : true},	
		    {header:'指标值1',dataIndex:'INDEX_VALUE1',width:200,sortable : true, hidden:'true'},	
		    {header:'指标值2',dataIndex:'INDEX_VALUE2',width:200,sortable : true, hidden:'true'}
		]);
		_this.zoneRecord = new Ext.data.Record.create([
		    {name:'ID'},
		    {name:'ATTRI_ID'},
		    {name:'INDEX_VALUE'},
		    {name:'INDEX_VALUE_NAME'},
		    {name:'INDEX_VALUE1'},
		    {name:'INDEX_VALUE2'}
		]);
		_this.zoneReader = new Ext.data.JsonReader({
			totalProperty:'json.count',
			root:'json.data'
		}, _this.zoneRecord);
		
		_this.zoneProxy = new Ext.data.HttpProxy({
			//F_LOOKUP_ID
			url:basepath+'/customerAttriItem.json'
		});
		_this.zoneStore = new Ext.data.Store({
			restful : true,
			proxy : _this.zoneProxy,
			reader :_this.zoneReader,
			recordType: _this.zoneRecord
		});
		// 每页显示条数下拉选择框
		_this.pagesize_combo = new Ext.form.ComboBox({
			name : 'pagesize',
			triggerAction : 'all',
			mode : 'local',
			store : new Ext.data.ArrayStore({
				fields : [ 'value', 'text' ],
				data : [ [ 10, '10条/页' ], [ 20, '20条/页' ], [ 50, '50条/页' ],
				         [ 100, '100条/页' ], [ 250, '250条/页' ],
				         [ 500, '500条/页' ] ]
			}),
			valueField : 'value',
			displayField : 'text',
			value : '20',
			forceSelection : true,
			width : 85
		});
		//当前分页条数
		var number = parseInt(_this.pagesize_combo.getValue());
		/**
		 * 监听分页下拉框选择事件
		 */
		_this.pagesize_combo.on("select", function(comboBox) {
			_this.bbar.pageSize = parseInt(_this.pagesize_combo.getValue()),
			searchFunction();
		});
		//分页工具条定义
		_this.bbar = new Ext.PagingToolbar({
			pageSize : number,
			store : _this.zoneStore,
			displayInfo : true,
			displayMsg : '显示{0}条到{1}条,共{2}条',
			emptyMsg : "没有符合条件的记录",
			items : ['-', '&nbsp;&nbsp;', _this.pagesize_combo]
		});
		
		/*****************************新增内容*******************************/
		_this.winForm = new Ext.form.FormPanel({
		    frame: true,// 是否渲染表单面板背景色
		    buttonAlign: 'center',
		    labelWidth: 100,		    
		    height: 200,
		    frame: true,	// 是否渲染表单面板背景色
		    labelAlign: 'right',	// 标签对齐方式
		    buttonAlign: 'center',
		    defaults: {
		        anchor:'0'
		    },
		    items: [
		        {	xtype: 'compositefield',
			        fieldLabel: '指标值',
			        anchor:'-20',
			        defaults: {
		            	flex: 1
		            },
				    items: [
			        	{xtype:'textfield',name:'INDEX_VALUE1',labelStyle:'text-align:right;',width:'216',id:'INDEX_VALUE1'},
				    	{xtype:'displayfield',value: '-'},
			        	{xtype:'textfield',name:'INDEX_VALUE2',labelStyle:'text-align:right;',width:'216',id:'INDEX_VALUE2'}
				    ]
				},
		        {xtype:'textfield',fieldLabel:'指标值名称',name:'INDEX_VALUE_NAME',labelStyle:'text-align:right;',anchor:'-20',allowBlank:false,id:'INDEX_VALUE_NAME'},
		        {xtype:'textfield',fieldLabel:'编号',name:'ID',labelStyle:'text-align:right;',hidden:true,id:'ID'},
		        {xtype:'textfield',fieldLabel:'属性编码',name:'ATTRI_ID',labelStyle:'text-align:right;',hidden:true,id:'ATTRI_ID'}
//		        ,{xtype: 'displayfield',fieldLabel:'提示',name:'QTIPS',labelStyle:'text-align:right;',
//		        	value:'指标值填写规则：如果是普通值，请在指标值前框输入内容；如果是数值范围，请在前后框输入区间数值。'},
			]
		});
		
		_this.win = new Ext.Window({
		    title: '指标值编辑',
		    width: 600,
		    height: 300,
		    autoShow: true,
		    modal:true,
		    buttonAlign: 'center',
		    items : [_this.winForm],
		    buttons: [
		        {
		        	xtype: 'button',
		        	text: '保存',
		        	handler:function(form){
		        		var formData = _this.winForm.getForm().getValues();
			    	 	var id = formData.ID;
			    	 	var attriId = formData.ATTRI_ID;
			    	 	var indexValueName = formData.INDEX_VALUE_NAME;
			        	var minv = formData.INDEX_VALUE1;//获取字段值
			        	var maxv = formData.INDEX_VALUE2;			        	
			        	
			        	if(minv == '' && maxv == ''){
							Ext.Msg.alert('提示','指标值不可为空！');
							return false;
			        	}
			        	var reg = /^[0-9]+$/;
			        	if(minv == '' && !reg.test(maxv)){
							Ext.Msg.alert('提示','数值型指标值应是区间！');
							return false;
			        	}        	
			
			        	if(minv != '' && maxv != ''){
				        	if(reg.test(minv) && reg.test(maxv)){
			        			indexValue = minv + '-' + maxv;
				        	}else if(reg.test(minv) && !reg.test(maxv)){
								Ext.Msg.alert('提示','数值型指标值应都是是数字！');
								return false;
				        	}else if(reg.test(maxv) && !reg.test(minv)){
								Ext.Msg.alert('提示','数值型指标值应都是数字！');
								return false;
				        	}else{
			        			indexValue = minv;	        	
				        	}
			        	}else{
			        		indexValue = minv;
			        	}
			        	
			    	 	Ext.MessageBox.confirm('提示','确认保存上述信息？',function(buttonId){
							if(buttonId.toLowerCase() == "no"){
							 	return false;
							} 
							Ext.Msg.wait('正在保存，请稍后......','系统提示');
					    	Ext.Ajax.request({
								url : basepath + '/customerAttriItem!save.json',
								params : {
									'id':id,
									'attriId':attriId,
									'indexValue':indexValue,
									'indexValueName':indexValueName
								},
								method : 'POST',
								waitMsg : '正在保存数据,请等待...', // 显示读盘的动画效果，执行完成后效果消失
								success : function(response) {
									Ext.Msg.alert('提示', '操作成功!');
									_this.zoneStore.reload();
		        					_this.win.hide();
								},
								failure : function(response) {
									var resultArray = Ext.util.JSON.decode(response.status);
									if(resultArray == 403) {
										Ext.Msg.alert('提示', response.responseText);
									} else{
										Ext.Msg.alert('提示', '操作失败,失败原因:' + response.responseText);
									}
								}
							});
					 	});
		        	}
		        },
		        { xtype: 'button', text: '取消', handler:function(){ _this.win.hide(); } }
		    ]
		});
		
		//新增的工具条
		_this.tbar = new Ext.Toolbar({
			items:[{
				text : '添加',
				handler : function(){
					_this.winForm.getForm().reset();
					_this.winForm.getForm().findField("ATTRI_ID").setValue(JsContext.indexId);
					_this.win.show();
				}
			},{
				text : '修改',
				handler : function(grid){
					var selectLength = _this.zoneGrid.getSelectionModel().getSelections().length;
					var selectValue = _this.zoneGrid.getSelectionModel().getSelections()[0];
					if (selectLength != 1) {
						Ext.Msg.alert('提示', '请选择一条数据！');
						return false;
					}
					_this.winForm.getForm().reset();
					Ext.getCmp('ID').setValue(selectValue.data.ID);
					Ext.getCmp('ATTRI_ID').setValue(selectValue.data.ATTRI_ID);
					Ext.getCmp('INDEX_VALUE1').setValue(selectValue.data.INDEX_VALUE1);
					Ext.getCmp('INDEX_VALUE2').setValue(selectValue.data.INDEX_VALUE2);
					Ext.getCmp('INDEX_VALUE_NAME').setValue(selectValue.data.INDEX_VALUE_NAME);
					_this.win.show();
				}
			},{
				text : '删除',
				handler : function(){
					var records = _this.zoneGrid.getSelectionModel().getSelections();
					if (records.length != 1) {
						Ext.Msg.alert('提示', '请选择一条数据！');
						return false;
					}
					Ext.each(records, function(record){
						//先通过ajax从后台删除数据，删除成功后再从页面删除数据			
						var id = record.data.ID;
						Ext.MessageBox.confirm('提示','确认删除选中的记录！',function(buttonId){
							if(buttonId.toLowerCase() == "no"){
							 	return false;
							} 
							Ext.Msg.wait('正在保存，请稍后......','系统提示');
					    	Ext.Ajax.request({
								url : basepath + '/customerAttriItem!delete.json',
								params : {
									'id':id
								},
								method : 'POST',
								waitMsg : '正在保存数据,请等待...', // 显示读盘的动画效果，执行完成后效果消失
								success : function(response) {
									Ext.Msg.alert('提示', '操作成功!');
									_this.zoneStore.remove(record);
								},
								failure : function(response) {
									Ext.Msg.alert('提示', '操作失败,失败原因:')
								}
							});
					 	});
					})
				}			
			}]
		});
		/*****************************新增内容*******************************/
		
		/**
		 * 指标选择grid定义
		 */
		_this.zoneGrid =  new Ext.grid.GridPanel({//产品列表数据grid
			frame:true,
			autoScroll : true,
			bbar:_this.bbar,
			stripeRows : true, // 斑马线
			store:_this.zoneStore,
			loadMask:true,
			cm :_this.zoneCm,
			tbar : _this.tbar,
			sm :sm,
			region:'center',
			viewConfig:{
				forceFit:false,
				autoScroll:true
			},
			loadMask : {
				msg : '正在加载表格数据,请稍等...'
			}
		});
		
		/**
		 * 指标选择window定义
		 */
		_this.adminZoneWindow=new Ext.Window({
			title : '指标值选择',
			closable : true,
			plain : true,
			resizable : false,
			collapsible : false,
			height:400,
			width:760,
			draggable : false,
			closeAction : 'hide',
			modal : true, // 模态窗口 
			border : false,
			autoScroll : true,
			closable : true,
			animateTarget : Ext.getBody(),
			constrain : true,
			layout:'border',
			buttonAlign:'center',
			items:[
//				_this.zonePanel,
				_this.zoneGrid],
			buttons:[{
				/**
				 * 选定指标操作
				 */
				text : '选定',
				handler : function() {
					var checkedNodes = _this.zoneGrid.getSelectionModel().selections.items;
					if(_this.singleSelect && checkedNodes.length > 0) {
						_this.setValue(checkedNodes[0].data.INDEX_VALUE_NAME);
						if(_this.hiddenField){
							_this.hiddenField.setValue(checkedNodes[0].data.INDEX_VALUE);
						}
					}else{
						var sName='';
						var json = '';
						if(checkedNodes.length > 0){
							json = json + checkedNodes[0].data.INDEX_VALUE;
							sName = sName + checkedNodes[0].data.INDEX_VALUE_NAME;
						}
						for(var i=1;i<checkedNodes.length;i++){
							json = json + ',' + checkedNodes[i].data.INDEX_VALUE;
							sName = sName + ',' + checkedNodes[i].data.INDEX_VALUE_NAME;
						}
						_this.setValue(sName);
						if(_this.hiddenField){
							_this.hiddenField.setValue(json);
						}
					};
					_this.adminZoneWindow.hide();
					if(typeof searchField.callback == 'function') {
						searchField.callback(checkedNodes);
					}
				}
			},{
				text : '取消',
				handler : function() {
					searchField.setRawValue('');
					if(searchField.hiddenField){
						searchField.hiddenField.setValue('');
					}
					_this.adminZoneWindow.hide();
				}
			}]
		});
		/**
		 * 添加事件监听,在隐藏指标面板时移除面板数据
		 */
		_this.adminZoneWindow.on('hide',function(){
//			_this.zonePanel.getForm().reset();
			_this.zoneStore.removeAll();
		});
		_this.adminZoneWindow.on('show',function(){
			searchFunction();
		});
		_this.adminZoneWindow.show();
		return;
	}
});
Ext.reg('countryCodeChoose',Com.yucheng.bcrm.common.CountryCode);