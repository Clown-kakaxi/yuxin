Ext.ns('Com.yucheng.bcrm.common');
/**
 * 
 * 
 * @author mams
 * @since 2016-02-29
 */
Com.yucheng.bcrm.common.LatentRefereesQuery
Com.yucheng.bcrm.common.LatentRefereesQuery = Ext
		.extend(
				Ext.form.TwinTriggerField,
				{
					initComponent : function() {
						Ext.ux.form.SearchField.superclass.initComponent
								.call(this);
						this.on('specialkey', function(f, e) {
							if (e.getKey() == e.ENTER) {
								this.onTrigger2Click();
							}
						}, this);
					},
					onRender : function(ct, position) {
						Com.yucheng.bcrm.common.LatentRefereesQuery.superclass.onRender
								.call(this, ct, position);
						if (this.hiddenName) {
							var ownerForm = this;
							while (ownerForm.ownerCt
									&& !Ext.instanceOf(ownerForm.ownerCt,
											'form')) { // 根据条件查询放大镜控件的最外层容器
								ownerForm = ownerForm.ownerCt;
							}
							;
							if (Ext.instanceOf(ownerForm.ownerCt, 'form')) { // 判断父容器是否为form类型
								ownerForm = ownerForm.ownerCt;
								if (ownerForm.getForm().findField(
										this.hiddenName)) { // 如果已经创建隐藏域
									this.hiddenField = ownerForm.getForm()
											.findField(this.hiddenName);
								} else { // 如果未创建隐藏域，则根据hiddenName属性创建隐藏域
									this.hiddenField = ownerForm.add({
										xtype : 'hidden',
										id : this.hiddenName,
										name : this.hiddenName
									});
								}
							}
						}
					},
					autoLoadFlag : false,
					singleSelected : false,// 记录标志 true单选,false多选
					callback : false,
					validationEvent : false,
					validateOnBlur : false,
					trigger1Class : 'x-form-clear-trigger',
					trigger2Class : 'x-form-search-trigger',
					hideTrigger1 : true,
					width : 180,
					hasSearch : false,
					paramName : 'query',
					hiddenName : false, // 用于存隐藏ID字段
					oCustomerQueryWindow : false,

					onTrigger2Click : function() {
						var _this = this;
						if (_this.oCustomerQueryWindow) {
							_this.oCustomerQueryWindow.show();
							return;
						}
						if (this.disabled) {
							return;
						}
						var oThisSearchField = _this;

						// 活动类型下拉框定义
						_this.mkt_acti_type_boxstore = new Ext.data.Store({
							restful : true,
							autoLoad : true,
							proxy : new Ext.data.HttpProxy({
								url : basepath + '/lookup.json?name=ACTI_TYPE'
							}),
							reader : new Ext.data.JsonReader({
								root : 'JSON'
							}, [ 'key', 'value' ])
						});

						_this.oCustomerQueryForm = new Ext.form.FormPanel(
								{
									frame : true, // 是否渲染表单面板背景色
									labelAlign : 'middle', // 标签对齐方式
									buttonAlign : 'center',
									region : 'north',
									height : 100,
									width : 760,
									items : [ {
										layout : 'column',
										border : false,
										items : [
												{
													columnWidth : .4,
													layout : 'form',
													defaultType : 'textfield',
													border : false,
													items : [ {
														fieldLabel : '编号',
														name : 'CID',
														xtype : 'textfield', // 设置为数字输入框类型
														labelStyle : 'text-align:right;',
														anchor : '80%'
													} , {
														fieldLabel : '姓名',
														name : 'CNAME',
														xtype : 'textfield', // 设置为数字输入框类型
														labelStyle : 'text-align:right;',
														anchor : '80%'
													} ]
												},
												{
													columnWidth : .4,
													layout : 'form',
													defaultType : 'textfield',
													border : false,
													items : [ new Com.yucheng.crm.common.OrgUserManage({ 
													xtype:'userchoose',
													fieldLabel : '用户', 
													labelStyle: 'text-align:right;',
													name : 'MGR_NAME',
													hiddenName:'MGR_ID',
													//searchRoleType:('304,100009'),  //指定查询角色属性 ,默认全部角色
													searchType:'SUBTREE',/* 允许空，默认辖内机构用户，指定查询机构范围属性  SUBTREE（子机构树）SUBORGS（直接子机构）PARENT（父机构）PARPATH （所有父、祖机构）ALLORG（所有机构）*/
													singleSelect:false,
													anchor : '90%'
												   }),new Com.yucheng.bcrm.common.OrgField({
													searchType:'SUBTREE',/*指定查询机构范围属性  SUBTREE（子机构树）SUBORGS（直接子机构）PARENT（父机构）PARPATH （所有父、祖机构）ALLORG（所有机构）*/
													fieldLabel : '所属机构',
													labelStyle : 'text-align:right;',
													name : 'CORGNAME', 
													hiddenName: 'ORG_ID',   //后台获取的参数名称
													anchor : '90%',
													checkBox:true //复选标志
												}) ]
												}]
									} ],
									listeners : {
										'render' : function() {
										}
									},
									buttons : [
											{
												text : '查询',
												handler : function() {
													_this.oCustomerQueryStore
															.on(
																	'beforeload',
																	function() {
																		var conditionStr = _this.oCustomerQueryForm
																				.getForm()
																				.getValues(
																						false);
																		this.baseParams = {
																			"condition" : Ext
																					.encode(conditionStr)
																		};
																	});
													_this.oCustomerQueryStore
															.reload({
																params : {
																	start : 0,
																	limit : _this.oCustomerQueryBbar.pageSize
																}
															});
												}
											},
											{
												text : '重置',
												handler : function() {
													_this.oCustomerQueryForm
															.getForm().reset();
												}
											} ]
								});
						_this.sm = new Ext.grid.CheckboxSelectionModel({
							singleSelect : oThisSearchField.singleSelected
						});

						// 定义自动当前页行号
						_this.rownum = new Ext.grid.RowNumberer({
							header : 'No.',
							width : 28
						});
						// 定义列模型
						_this.cm = new Ext.grid.ColumnModel([ _this.rownum,
								_this.sm, {
									header : '推荐人ID',
									dataIndex : 'CID',
									sortable : true
								}, {
									header : '推荐人',
									dataIndex : 'CNAME',
									sortable : true,
									width : 200
								}, {
									header : '机构',
									dataIndex : 'CORGNAME',
									width : 120,
									sortable : true
								}, {
									header : '推荐人所属类型',
									dataIndex : 'REFEREESTYPE',
									width : 100,
									sortable : true
								}]);
						/**
						 * 数据存储
						 */
						_this.oCustomerQueryStore = new Ext.data.Store({
							restful : true,
							proxy : new Ext.data.HttpProxy({
								url : basepath
										+ '/latentcustRefereesQuery.json'
							}),
							reader : new Ext.data.JsonReader({
								totalProperty : 'json.count',
								root : 'json.data'
							}, [ {
								name : 'CID'
							}, {
								name : 'CNAME'
							}, {
								name : 'CORGNAME'
							}, {
								name : 'REFEREESTYPE'
							}])
						});

						_this.oPagesizeCombo = new Ext.form.ComboBox({
							name : 'pagesize',
							triggerAction : 'all',
							mode : 'local',
							store : new Ext.data.ArrayStore({
								fields : [ 'value', 'text' ],
								data : [ [ 10, '10条/页' ], [ 20, '20条/页' ],
										[ 50, '50条/页' ], [ 100, '100条/页' ],
										[ 250, '250条/页' ], [ 500, '500条/页' ] ]
							}),
							valueField : 'value',
							displayField : 'text',
							value : '20',
							editable : false,
							width : 85
						});
						_this.number = parseInt(_this.oPagesizeCombo.getValue());
						_this.oPagesizeCombo
								.on(
										"select",
										function(comboBox) {
													_this.oCustomerQueryBbar.pageSize = parseInt(_this.oPagesizeCombo
															.getValue()),
													_this.oCustomerQueryStore
															.load({
																params : {
																	start : 0,
																	limit : parseInt(_this.oPagesizeCombo
																			.getValue())
																}
															});
										});
						_this.oCustomerQueryBbar = new Ext.PagingToolbar(
								{
									pageSize : _this.number,
									store : _this.oCustomerQueryStore,
									displayInfo : true,
									displayMsg : '显示{0}条到{1}条,共{2}条',
									emptyMsg : "没有符合条件的记录",
									items : [ '-', '&nbsp;&nbsp;',
											_this.oPagesizeCombo ]
								});
						// 表格实例
						_this.oCustomerQueryGrid = new Ext.grid.GridPanel({
							height : 275,
							width : 1000,
							region : 'center',
							frame : true,
							autoScroll : true,
							store : _this.oCustomerQueryStore, // 数据存储
							stripeRows : true, // 斑马线
							cm : _this.cm, // 列模型
							sm : _this.sm, // 复选框
							bbar : _this.oCustomerQueryBbar,
							viewConfig : {
								forceFit : false,
								autoScroll : true
							},
							loadMask : {
								msg : '正在加载表格数据,请稍等...'
							}
						});

						_this.oCustomerQueryWindow = new Ext.Window(
								{
									title : '推荐人查询',
									closable : true,
									resizable : true,
									height : 400,
									width : 860,
									draggable : true,
									closeAction : 'hide',
									modal : true, // 模态窗口
									border : false,
									closable : true,
									layout : 'border',
									listeners : {
										'show' : function() {
											_this.oCustomerQueryForm.form
													.reset();
											_this.oCustomerQueryStore
													.removeAll();
											if (_this.autoLoadFlag)
												_this.oCustomerQueryStore
														.load();
										}
									},
									items : [ _this.oCustomerQueryForm,
											_this.oCustomerQueryGrid ],
									buttonAlign : 'center',
									buttons : [
											{
												text : '确定',
												handler : function() {
													var sName = '';
													var checkedNodes = null;
													if (!(_this.oCustomerQueryGrid
															.getSelectionModel().selections == null)) {
														if (oThisSearchField.hiddenField) {
															checkedNodes = _this.oCustomerQueryGrid
																	.getSelectionModel().selections.items;
															if (oThisSearchField.singleSelected
																	&& checkedNodes.length > 1) {
																Ext.Msg
																		.alert(
																				'提示',
																				'您只能选择一条记录！');
																return;
															}
															for ( var i = 0; i < checkedNodes.length; i++) {
																if (i == 0) {
																	sName = checkedNodes[i].data.CNAME;
																	oThisSearchField.hiddenField
																			.setValue(checkedNodes[i].data.CID);
																} else {
																	sName = sName
																			+ ','
																			+ checkedNodes[i].data.CNAME;
																	oThisSearchField.hiddenField
																			.setValue(_this.hiddenField.value
																					+ ','
																					+ checkedNodes[i].data.CID);
																}
															}
															oThisSearchField
																	.setRawValue(sName);
															if (checkedNodes.length == 1) {// 如果单选，则设置该客户相应的附属属性
															}
														}
													}
													if (typeof oThisSearchField.callback == 'function') {
														oThisSearchField
																.callback(
																		oThisSearchField,
																		checkedNodes);
													}
													_this.oCustomerQueryWindow
															.hide();
												}
											},
											{
												text : '取消',
												handler : function() {
													_this.oCustomerQueryWindow
															.hide();
												}
											} ]
								});
						_this.oCustomerQueryWindow.show();
						return;
					}
				});
Ext.reg('latentRefereesQuery', Com.yucheng.bcrm.common.LatentRefereesQuery);