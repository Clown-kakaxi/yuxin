Ext.onReady(function(){
	 	var khStore = new Ext.data.ArrayStore({
	        fields:['key','value'],
	        data:[['停用','停用'],['启用','启用']]
	    });
	    var store1 = new Ext.data.ArrayStore({
	        fields:['key','value'],
	        data:[['1','餐饮服务业/服装'],['2','服装、小商品贸易业'],['3','家居、建材、装饰业'],['4','汽车修理及配件业'],['5','钢材贸易业'],['6','数码家电业']]
	    });
	   var store2 = new Ext.data.ArrayStore({
	        fields:['key','value'],
	        data:[['1','普通客户群'],['2','商圈客户群'],['3','链式客户群']]
	    });
	   var store3 = new Ext.data.ArrayStore({
	        fields:['key','value'],
	        data:[['1','市中心'],['2','居民区'],['3','远郊区'],['4','开发区']]
	    });
	   var store4 = new Ext.data.ArrayStore({
	        fields:['key','value'],
	        data:[['1','国有'],['2','集体'],['3','民营'],['4','外资'],['4','合资']]
	    });
	   var store5 = new Ext.data.ArrayStore({
	        fields:['key','value'],
	        data:[['1','国家级'],['2','省级'],['3','市级'],['4','区县级']]
	    });
	   var store6 = new Ext.data.ArrayStore({
	        fields:['key','value'],
	        data:[['1','仅场地'],['2','物流一体化'],['3','报关报税'],['4','统购统销']]
	    });
	   var store7 = new Ext.data.ArrayStore({
	        fields:['key','value'],
	        data:[['1','是'],['2','否']]
	    });
	  var store8 = new Ext.data.ArrayStore({
	        fields:['key','value'],
	        data:[['1','统售统租'],['2','散售统租'],['3','零售']]
	    });
	var record = Ext.data.Record.create([
			     {name:'a11'}
				,{name:'a12'}
				,{name:'a13'}
				,{name:'a14'}
				,{name:'a15'}
				,{name:'a16'}
				,{name:'a17'}
				,{name:'a21'}
				,{name:'a22'}
				,{name:'a23'}
				,{name:'a24'}
				,{name:'a25'}
				,{name:'a26'}
				,{name:'a31'}
				,{name:'a32'}
				,{name:'a33'}
				,{name:'a34'}
				,{name:'a35'}
				,{name:'a36'}
				,{name:'a41'}
				,{name:'a42'}
	  			]);
	
	var groupExtendInfoPanel = new Ext.FormPanel({//集团基本信息表单
		labelWidth : 120,
		region : 'north',
		frame : true,
		split:true,
		labelAlign:'right',
		height:document.body.scrollHeight-30,
		autoScroll : true,
		//title : '集团基本信息',
		buttonAlign:"center" ,
		reader: new Ext.data.JsonReader({
            root:'json.data'
            },record),
		items : [{ 
			   xtype:'fieldset',
	           title: '客户群扩展信息', 
				layout : 'column',
				items : [{
				columnWidth : .33,
				layout : 'form',
				items : [{
							xtype:'textfield',
							name:'a11',
							triggerAction:'all',
							anchor:'90%',
							fieldLabel:'商圈名称'
						},{
							store: store5,
							xtype : 'combo',
							name : 'a12',
							hiddenName : 'groupStatus',
							fieldLabel : '商圈级别',
							valueField:'key',
							displayField:'value',
							mode : 'local',
							typeAhead: true,
							forceSelection : true,
							triggerAction: 'all',
							emptyText:'请选择',
							selectOnFocus:true,
							width : '100',
							anchor : '90%'
			              },{
							store: store6,
							xtype : 'combo',
							name : 'a13',
							hiddenName : 'groupStatus',
							fieldLabel : '管辖方式',
							valueField:'key',
							displayField:'value',
							mode : 'local',
							typeAhead: true,
							forceSelection : true,
							triggerAction: 'all',
							emptyText:'请选择',
							selectOnFocus:true,
							width : '100',
							anchor : '90%'
			              },{
							store: store7,
							xtype : 'combo',
							name : 'a14',
							hiddenName : 'groupStatus',
							fieldLabel : '是否有税收优惠',
							valueField:'key',
							displayField:'value',
							mode : 'local',
							typeAhead: true,
							forceSelection : true,
							triggerAction: 'all',
							emptyText:'请选择',
							selectOnFocus:true,
							width : '100',
							anchor : '90%'
			              },{
							xtype : 'textfield',
							fieldLabel : '业主/运营商',
							name : 'a15',
							anchor : '90%'
						},{
							xtype : 'textfield',
							fieldLabel : '运营商资质',
							name : 'a16',
							readOnly : true,
							anchor : '90%'
						},{
							store: store7,
							xtype : 'combo',
							name : 'a17',
							hiddenName : 'groupStatus',
							fieldLabel : '是否有政府背景',
							valueField:'key',
							displayField:'value',
							mode : 'local',
							typeAhead: true,
							forceSelection : true,
							triggerAction: 'all',
							emptyText:'请选择',
							selectOnFocus:true,
							width : '100',
							anchor : '90%'
			              }]
						},{
				columnWidth : .33,
				layout : 'form',
				items : [{
							store: store1,
							xtype : 'combo',
							name : 'a21',
							fieldLabel : '商圈类型',
							valueField:'key',
							displayField:'value',
							mode : 'local',
							typeAhead: true,
							forceSelection : true,
							triggerAction: 'all',
							emptyText:'请选择',
							selectOnFocus:true,
							width : '100',
							anchor : '90%'
			              },{
							xtype:'textfield',
							name:'a22',
							triggerAction:'all',
							anchor:'90%',
							fieldLabel:'商圈设立时间'
						},{
							store: store8,
							xtype : 'combo',
							name : 'a23',
							fieldLabel : '招商形态',
							valueField:'key',
							displayField:'value',
							mode : 'local',
							typeAhead: true,
							forceSelection : true,
							triggerAction: 'all',
							emptyText:'请选择',
							selectOnFocus:true,
							width : '100',
							anchor : '90%'
			              },{
							store: store7,
							xtype : 'combo',
							name : 'a24',
							hiddenName : 'groupStatus',
							fieldLabel : '是否保税区',
							valueField:'key',
							displayField:'value',
							mode : 'local',
							typeAhead: true,
							forceSelection : true,
							triggerAction: 'all',
							emptyText:'请选择',
							selectOnFocus:true,
							width : '100',
							anchor : '90%'
			              },{
							xtype : 'textfield',
							fieldLabel : '主管机构',
							name : 'a25',
							readOnly : true,
							anchor : '90%'
						},{
							xtype : 'textfield',
							fieldLabel : '运营商评级',
							name : 'a26',
							readOnly : true,
							anchor : '90%'
						},{
							store: store3,
							xtype : 'combo',
							name : 'a42',
							hiddenName : 'groupStatus',
							fieldLabel : '商圈位置',
							valueField:'key',
							displayField:'value',
							mode : 'local',
							typeAhead: true,
							forceSelection : true,
							triggerAction: 'all',
							emptyText:'请选择',
							selectOnFocus:true,
							width : '100',
							anchor : '90%'
			              }]
						},{
				columnWidth : .33,
				layout : 'form',
				items : [{
							store: store4,
							xtype : 'combo',
							name : 'a31',
							hiddenName : 'groupStatus',
							fieldLabel : '商圈性质',
							valueField:'key',
							displayField:'value',
							mode : 'local',
							typeAhead: true,
							forceSelection : true,
							triggerAction: 'all',
							emptyText:'请选择',
							selectOnFocus:true,
							width : '100',
							anchor : '90%'
			              },{
							xtype:'textfield',
							name:'a32',
							triggerAction:'all',
							anchor:'90%',
							fieldLabel:'商圈体量（规模）(平米)'
						},{
							xtype:'textfield',
							editable:true,
							name:'a33',
							triggerAction:'all',
							anchor:'90%',
							fieldLabel:'附近标志'
						},{
							store: store7,
							xtype : 'combo',
							name : 'a34',
							hiddenName : 'groupStatus',
							fieldLabel : '是否鼓励性行业',
							valueField:'key',
							displayField:'value',
							mode : 'local',
							typeAhead: true,
							forceSelection : true,
							triggerAction: 'all',
							emptyText:'请选择',
							selectOnFocus:true,
							width : '100',
							anchor : '90%'
			              },{
							xtype : 'textfield',
							fieldLabel : '行业协会/商会',
							name : 'a35',
							anchor : '90%'
						},{
							xtype : 'textfield',
							fieldLabel : '结算账户',
							name : 'a36',
							anchor : '90%'
						}]
						},{
				columnWidth : 0.99,
				layout : 'form',
				items : [{
							xtype:'textarea',
							name:'a41',
							triggerAction:'all',
							anchor:'90%',
							fieldLabel:'商圈地址'
						}]
						}],
								buttonAlign : 'center',
								buttons :[{
								id :'updat',
								text :'修改', 
								handler : function (){
								Ext.Msg.alert('系统提示','操作成功！');
								}
							}]
					}]
				});
				var store = new Ext.data.Store({
					reader : new Ext.data.JsonReader({
														root : 'rows',
														totalProperty : 'num'
													 }, 
													 record
					)
				});
				var memberData= {
						TOTALCOUNT:1,
						rows:[{
						 'a11':'娱乐圈'
					,'a12':'1'
						,'a13':'2'
						,'a14':'2'
						,'a15':'北京宇信易诚'
						,'a16':'AA级'
						,'a17':'1'
						,'a21':'2'
						,'a22':'1992-09-09'
						,'a23':'1'
						,'a24':'2'
						,'a25':'某某支行'
						,'a26':'2级'
						,'a31':'2'
						,'a32':'2400000'
						,'a33':'1'
						,'a34':'2'
						,'a35':'红花会'
						,'a36':'6225456543678765'
						,'a41':'2312312321'
						,'a42':'1'
						}		
							]
						};
						store.loadData(memberData);
						groupExtendInfoPanel.getForm().loadRecord(store.getAt(0)
							);
						var viewgroupExtendInfoPanel = new Ext.Panel({
							renderTo:'group_viewport_center',
							autoScroll:true,
							items : [{
								border : false,
								items : [groupExtendInfoPanel]
							}]
				});
			});