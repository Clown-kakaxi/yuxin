	Ext.onReady(function() {
		Ext.QuickTips.init();
		var instanceid = curNodeObj.instanceid;
		var id = instanceid.split('_')[1];
		var nodeid = curNodeObj.nodeid;
		//客户评级
		var p_cust_grade = new Ext.data.Store( {
			restful : true,
			sortInfo : {
				field : 'key',
				direction : 'ASC'
			},
			autoLoad : true,
			proxy : new Ext.data.HttpProxy( {
				url : basepath + '/lookup.json?name=P_CUST_GRADE'
			}),
			reader : new Ext.data.JsonReader( {
				root : 'JSON',
				totalProperty : 'list'
			}, [ 'key', 'value' ])
		});
		p_cust_grade.load();
		
//		var p_cust_grade1 =  new Ext.data.Store({  
//			sortInfo: {
//		    	field: 'key',
//		    	direction: 'ASC' // or 'DESC' (case sensitive for local sorting)
//			},
//			autoLoad :true,
//			restful:true,   
//			proxy : new Ext.data.HttpProxy({
//				url :basepath + '/customerBaseInformation!searchHandLevel.json'//默认去查找全部客户的方案
//			}),
//			reader : new Ext.data.JsonReader({
//				root : 'json.data'
//			}, [ {name: 'key', mapping: 'KEY'},
//			      {name: 'value', mapping: 'VALUE'} ])
//		});
		
		//证件类型
		var certTypStore = new Ext.data.Store( {
			restful : true,
			autoLoad : true,
			proxy : new Ext.data.HttpProxy( {
				url : basepath + '/lookup.json?name=XD000040'
			}),
			reader : new Ext.data.JsonReader( {
				root : 'JSON'
			},['key','value'])
		});
		certTypStore.load();
		
		var rsRecord = Ext.data.Record.create([ 
									{name:'custId'},
									{name:'custName'},
									{name:'certType'},
									{name:'certCode'},
									{name:'workUnit'},
									{name:'position'},
									{name:'salary'},
									{name:'telphone'},
									{name:'currentGrade'},
									{name:'toGrade'},
									{name:'reason'}
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
				        			url:basepath+'/ocrmFCiGradeApply-info!indexPage.json'
				        		}),
				        reader: rsreader
					});
	    var panel2 = new Ext.FormPanel({ 
	        layout:'form',
			autoScroll:true,
//			bodyStyle:'padding:5px 5px 0',
			items: [{
			        layout:'column',
			        items:[{
		                	 columnWidth : .5,
				                layout : 'form',
				                items:[{
									fieldLabel: '客户编号',
									xtype:'textfield',
									name: 'custId',
									anchor:'90%',
									readOnly:true//,
				                },{
									store : p_cust_grade,
									xtype : 'combo',
									resizable : true,
									fieldLabel : '申请评级',
									name : 'toGrade',
									hiddenName : 'toGrade',
									valueField : 'key',
									displayField : 'value',
									readOnly:true,
									mode : 'local',
									typeAhead : true,
									forceSelection : true,
									triggerAction : 'all',
									emptyText : '请选择',
									selectOnFocus : true,
									anchor : '90%',
									readOnly:true
								} ]
		                },{
		                	 columnWidth : .5,
				                layout : 'form',
				                items:[{
									name: 'custName',
									xtype: 'textfield',
									fieldLabel: '客户名称',
									anchor:'90%',
										readOnly:true
				                },{
									store : p_cust_grade,
									xtype : 'combo',
									resizable : true,
									fieldLabel : '现有客观评级',
									name : 'currentGrade',
									hiddenName : 'currentGrade',
									valueField : 'key',
									displayField : 'value',
									mode : 'local',
									typeAhead : true,
									forceSelection : true,
									triggerAction : 'all',
									emptyText : '请选择',
									selectOnFocus : true,
									anchor : '90%',
									readOnly:true
								}]
		                },{  columnWidth : 1,
			                layout : 'form',
			                items:[{
								name : 'reason',
								fieldLabel : '申请原因',
								xtype : 'textarea',
								allowBlank : false,
								anchor:'90%',
								readOnly:true
							}]}]
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
			id:id
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
