	Ext.onReady(function() {
		Ext.QuickTips.init();
		//debugger;
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
		var instanceid = '';
		if(window.location.href.split("instanceid=")[1]!=undefined){
			instanceid = window.location.href.split("instanceid=")[1];
		}
		/**
		 * 数据存储
		 */
	    var store = new Ext.data.Store({
						restful:true,	
				        proxy : new Ext.data.HttpProxy(
				        		{
				        			url:basepath+'/ocrmFCmAbsentInfo!indexPage.json?ID='+instanceid
				        		}),
				        reader: new Ext.data.JsonReader({
				            successProperty: 'success',
				        root:'json.data',
	                    totalProperty: 'json.count'
				        }, [{name: 'absentKind'},
				            {name: 'id'},
							{name: 'absentStat'},
							{name: 'begTime'},
							{name: 'createDate'},
							{name: 'endTime'},
							{name: 'orgId'},
							{name: 'orgName'},
							{name: 'userId'},
							{name: 'userName'},
							{name: 'term'},
							{name: 'cause'}
						])
					});
	    var panel2 = new Ext.FormPanel({ 
	        formId:'panel2',
			frame:true,
			autoScroll:true,
			bodyStyle:'padding:5px 5px 0',
			title : '<span style="font-weight:normal">请假申请信息</span>',
			width: '100%',
//		    height:380,
			items: [{
			    autoHeight:true,
				items :[{ layout:'column',
					buttonAlign : 'center',
						 items:[{
							 columnWidth:.5,
							 layout: 'form',
							 items: [{
								 xtype:'textfield',
								 fieldLabel: '客户经理编号',
								  labelStyle: 'text-align:right;',
								 name: 'userId',
								 //hidden:true,
								 anchor:'90%'
							 }, {
	                             xtype:'textfield',
	                             fieldLabel: '机构号',
	                             readOnly:true,
	                             name: 'orgId',
	                              labelStyle: 'text-align:right;',
	                             anchor:'90%'
	                         }, {
	                             xtype:'datefield',
	                             fieldLabel: '请假起始时间',
	                             readOnly:true,
	                             format:'Y-m-d',
	                             name: 'begTime',
	                             id:"begTime",
	                              labelStyle: 'text-align:right;',
	                             anchor:'90%'
	                         }, {
	                             xtype:'combo',
	                             id:'jiabie',
	                             fieldLabel: '假别',
	                             readOnly:true,
	                             name: 'absentKind',
	                              labelStyle: 'text-align:right;',
	                              store: absentKindStore,
	              				valueField : 'key',
	              				displayField : 'value',
	                             anchor:'90%'
	                         }, {
	                             xtype:'textarea',
	                             fieldLabel: '请假事由',
	                             readOnly:true,
	                             name: 'cause',
	                             grow: true,
	                              labelStyle: 'text-align:right;',
	                             anchor:'90%'
	                         }]
						 },{
							 columnWidth:.5,
							 layout: 'form',
							 items: [{
	                             xtype:'textfield',
	                             fieldLabel: '客户经理名称',
	                             readOnly:true,
	                             format:'Y-m-d',
	                             name: 'userName',
	                              labelStyle: 'text-align:right;',
	                             anchor:'90%'
	                         }, {
	                             xtype:'textfield',
	                             fieldLabel: '机构名称',
	                             readOnly:true,
	                             format:'Y-m-d',
	                             name: 'orgName',
	                              labelStyle: 'text-align:right;',
	                             anchor:'90%'
	                         }, {
	                             xtype:'datefield',
	                             fieldLabel: '请假结束时间',
	                             readOnly:true,
	                             format:'Y-m-d',
	                             name: 'endTime',
	                             id:"endTime",
	                              labelStyle: 'text-align:right;',
	                             anchor:'90%'
	                         }, {
	                             xtype:'numberfield',
	                             fieldLabel: '请假天数',
	                             readOnly:true,
	                             name: 'term',
	                              labelStyle: 'text-align:right;',
	                             anchor:'90%'
	                         }]
						 }
					]
					}
					]}]
			});
	    
//	    var addRoleWindow = new Ext.Window(
//	    		{
//	    			//layout : 'fit',
//	    	        height : 400,
//	    	        width:600,
//	    			draggable : true,//是否可以拖动
//	    			closable : true,// 是否可关闭
//	    			modal : true,
//	    	        autoScroll:true,
//	    			closeAction : 'hide',
//	    			// iconCls : 'page_addIcon',
//	    			//maximizable: true,
//	    			//maximized:true,
//	    			collapsible : true,// 是否可收缩
//	    			titleCollapse : true,
//	    			buttonAlign : 'center',
//	    			border : false,
//	    			animCollapse : true,
//	    			pageY : 20,
//	    			//pageX : document.body.clientWidth / 2 - 420 / 2,
//	    			animateTarget : Ext.getBody(),
//	    			constrain : true,
//	    			items : [panel2],
//	    			buttons : [
//	    	        { // 窗口底部按钮配置
//	    	            text : '关    闭', // 按钮文本
//	    	            handler : function() { // 按钮响应函数
//	    	                addRoleWindow.hide();
//	    	            }
//	    	        }]
//	    		});
			// 布局模型
			var viewport = new Ext.Viewport( {
				layout : 'fit',
				items : [ panel2 ]
			});
			
			store.load({callback:function(){
				debugger;
				panel2.getForm().loadRecord(store.getAt(0));
				viewport.render('info');
//				alert(store.getAt(0).data.begTime.time);
				Ext.getCmp('begTime').setValue(new Date(store.getAt(0).data.begTime.time));
		    	Ext.getCmp('endTime').setValue(new Date(store.getAt(0).data.endTime.time));
//				viewport.doLayout();    //如果render不正常，调用此方法
//				addRoleWindow.show();
			}});
			absentKindStore.load({callback:function(){
				Ext.getCmp('jiabie').setValue(Ext.getCmp('jiabie').getValue());
			}});
			
		});