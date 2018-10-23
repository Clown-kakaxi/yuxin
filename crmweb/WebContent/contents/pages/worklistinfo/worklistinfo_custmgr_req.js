	Ext.onReady(function() {
		Ext.QuickTips.init();
		var instanceid = curNodeObj.instanceid;
		var idUsers = instanceid.split('_')[1];
		var nodeid = curNodeObj.nodeid;
		debugger;
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
		var rsRecord = Ext.data.Record.create([ 
									{name:'userId'},
									{name:'userName'},
									{name:'orgId'},
									{name:'orgName'},
									{name:'begTime'},
									{name:'endTime'},
									{name:'absentKind'},
									{name:'cause'},
									{name:'term'},
									{name:'absentStat'},
									{name:'id'}
		                                							 
		                                	    ]);
			var rsreader = new Ext.data.JsonReader({
				successProperty : 'success',
				idProperty : 'id',
				messageProperty : 'message',
				root : 'json.data',
				totalProperty : 'json.count'
			}, rsRecord);
	    var store = new Ext.data.Store({
						restful:true,	
				        proxy : new Ext.data.HttpProxy(
				        		{
				        			url:basepath+'/ocrmFCmAbsentInfo!indexPage.json'
				        		}),
				        reader: rsreader
					});
	    var panel2 = new Ext.FormPanel({ 
	        layout:'form',
			autoScroll:true,
//			bodyStyle:'padding:5px 5px 0',
			items: [{
			        layout:'column',
			        items:[{  columnWidth : .33,
		                layout : 'form',
		                items:[{
							name: 'userId',
							xtype: 'textfield',
							fieldLabel: '客户经理编号',
								readOnly:true
		                },{

							name: 'orgId',
							fieldLabel: '机构号',
							xtype: 'textfield',
							readOnly:true
						
		                },{

		                    xtype : 'combo',
		                    fieldLabel : '假别',
		                    editable : false,
		                    triggerAction:'all',
		                    mode:'local',
		                    store:absentKindStore,
		                    valueField:'key',
		                    displayField : 'value',
		                    name : 'absentKind'
		                }]},{
		                	 columnWidth : .33,
				                layout : 'form',
				                items:[{
									fieldLabel: '客户经理名称',
									xtype:'textfield',
									name: 'userName',
									readOnly:true//,
				                },{
									name: 'orgName',
									fieldLabel: '机构名称',
									xtype: 'textfield',
									readOnly:true
								},{

									name: 'term',
									xtype: 'numberfield',
									fieldLabel: '请假天数'
								
								}]
		                },{
		                	 columnWidth : .33,
				                layout : 'form',
				                items:[{
				            		xtype: 'datefield',
									name: 'begTime',
									fieldLabel: '请假起始时间',
									format:'Y-m-d'
				                },{
									xtype: 'datefield',
									name: 'endTime',
									fieldLabel: '请假结束时间',
									format:'Y-m-d'
								
				                },{
									name: 'id',
									xtype: 'hidden'
								},{
									name: 'absentStat',
									xtype: 'hidden'
								}]
		                }]
			},{
				  layout:'form',
				  columnWidth : .99,
				  items:[{
						name: 'cause',
						xtype: 'textarea',
						fieldLabel: '请假事由',
						maxLength:250,
						anchor: '95%'
					
				  }]
			}]
			});
		var bussFieldSetGrid = new Ext.form.FieldSet({
		    animCollapse :true,
		    collapsible:true,
//		    collapsed:true,
		    title: '流程业务信息',
		    items:[panel2]
	   }); 
		var EchainPanel = new Mis.Echain.EchainPanel({
			instanceID:instanceid,
			nodeId:nodeid,
			nodeName:curNodeObj.nodeName,
			fOpinionFlag:curNodeObj.fOpinionFlag,
			approvalHistoryFlag:curNodeObj.approvalHistoryFlag,
			WindowIdclode:curNodeObj.windowid,
			callbackCustomFun:'3_a10##1'
		});
		var view = new Ext.Panel( {
			renderTo : 'viewEChian',
			  frame : true,
			width : document.body.scrollWidth,
			height : document.body.scrollHeight-40,
			autoScroll : true,
			layout : 'form',
			items : [bussFieldSetGrid,EchainPanel]

		});
			// 布局模型
/*			var viewport = new Ext.Viewport( {
				layout : 'fit',
				frame:true,
				items : [ bussFieldSetGrid,EchainPanel]
			});*/
			
		store.load({params : {
            id:idUsers
        },
        callback:function(){
        	if(store.getCount()!=0){
        		loadFormData();
        	}
		}});
		function loadFormData(){
    		panel2.getForm().loadRecord(store.getAt(0));
		}
			
		
		});
