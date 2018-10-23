Ext.onReady(function() {	
var eventTypeStore =  new Ext.data.ArrayStore({
				fields : [ 'key', 'value'  ],
				data : [ [ 1, '政策引导' ], 
				         [ 2, '生产经营' ],
						[ 3, '宣传' ],
						[ 4, 'etc' ]
						]
			});

			var eventRecordPanel = new Mis.Ext.CrudPanel(
					{
						id : "eventRecordPanel",
						title : "大事记",
						addUrl : "1",
						updateUrl : "1",
						deUrl : "1",
						demoData :
						{"json":{"count":2,"data":[{"a":"加入商圈 ","b":"突然加入了商圈","c":"宣传","d":"李晶莉","e":"2012-10-27"},{"a":"参加大型营销会 ","b":"那天参加大型营销会","c":"宣传","d":"李晶莉","e":"2013-01-27"}]}},
						winHeight : 250,
						checkbox : true,
						// 定义查询条件Form的高度
						gclms : [ {
							name : 'a',
							header : '事件名称',
							width : 200
						},{
							name : 'b',
							header : '事件描述'
						},{
							name : 'c',
							header : '事件类型',
							width : 150
						},{
							name : 'd',
							header : '创建人',
							width : 150
						}, {
							name : 'e',
							header : '创建日期',
							width : 150
						} ],
						pagesize : 20,
						// 新增、修改、详情的form的字段
						fclms : [
								{
									layout : 'column',
									items : [ {
										columnWidth : .5,
										layout : 'form',
										items : [ {
											xtype : 'textfield',
											allowBlank : false,
											blankText : '此项不能为空',
											fieldLabel : '*事件名称',
											name : 'a',
											anchor : '90%'
										} ]
									}, {
										columnWidth : .5,
										layout : 'form',
										items : [ {
										store : eventTypeStore,
										xtype : 'combo',
										allowBlank : false,
										blankText : '此项不能为空',
										fieldLabel : '*事件类型',
										name : 'c',
										editable : false,
										hiddenName : 'c',
										valueField : 'key',
										displayField : 'value',
										mode : 'local',
										typeAhead : true,
										forceSelection : true,
										triggerAction : 'all',
										emptyText : '请选择',
										selectOnFocus : true,
										anchor : '90%'
									}]}
								]},
								 {
									layout : 'form',
									items : [ {
										name : 'b',
										xtype : 'textarea',
										fieldLabel : '事件描述',
										maxLength : 1000,
										anchor : '90%'
									}]
								}, 
								{
									layout : 'column',
									items : [ {
										columnWidth : .5,
										layout : 'form',
										items : [ {
											xtype : 'hidden',
											fieldLabel : '创建人ID',
											name : 'createUser',
											anchor : '90%'
										}, {
											xtype : 'hidden',
											name : 'createOrg',
											anchor : '90%'
										},{
											xtype : 'hidden',
											name : 'pOrC',
											anchor : '90%'
										},{
											id :'userName',
											xtype : 'textfield',
											fieldLabel : '创建人',
											readOnly : true,
											name : 'USER_NAME',
											value : '李晶莉',
											anchor : '90%'
										} ]
									}, {
										columnWidth : .5,
										layout : 'form',
										items : [ {
											id :'createDate',
											xtype : 'textfield',
											fieldLabel : '创建日期',
											readOnly : true,
											value : '2013-04-11',
											name : 'createDate',
											anchor : '90%'
										} ]
									} ]
								}, {
									layout : 'column',
									items : [ {
										columnWidth : .5,
										layout : 'form',
										items : [ {
											xtype : 'hidden',
											fieldLabel : '最近更新人ID',
											name : 'updateUser',
											anchor : '90%'
										}, {
											id : 'updateUserName',
											xtype : 'textfield',
											fieldLabel : '最近更新人',
											value : '李晶莉',
											name : 'UPDATEUSER_NAME',
											readOnly : true,
											anchor : '90%'
										} ]
									}, {
										columnWidth : .5,
										layout : 'form',
										items : [ {
											id : 'updateDate',
											xtype : 'textfield',
											fieldLabel : '最近更新日期',
											value : '2013-04-11',
											readOnly : true,
											name : 'updateDate',
											anchor : '90%'
										} ]
									} ]
								} ]
					});
			// 布局模型
			var view_panel = new Ext.Panel({
				renderTo:'group_viewport_center',
				 height:document.body.scrollHeight-30,
				 layout : 'fit',
				 autoScroll:true,
				items : [eventRecordPanel]
			});
});