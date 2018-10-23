/***
 * 联盟商审批流程展示页面js
 * hujun 
 * 2014-06-25
 */	
 Ext.onReady(function() {
		Ext.QuickTips.init();
		var instanceid = curNodeObj.instanceid;
		var id = instanceid.split('_')[1];
		var nodeid = curNodeObj.nodeid;
			

		var companyType = new Ext.data.Store( {
			restful : true,
			sortInfo : {
				field : 'key',
				direction : 'ASC'
			},
			autoLoad : true,
			proxy : new Ext.data.HttpProxy( {
				url : basepath + '/lookup.json?name=PAR2100001'
			}),
			reader : new Ext.data.JsonReader( {
				root : 'JSON'
			}, [ 'key', 'value' ])
		});
		companyType.load();
		
		var level = new Ext.data.Store( {
			restful : true,
			sortInfo : {
				field : 'key',
				direction : 'ASC'
			},
			autoLoad : true,
			proxy : new Ext.data.HttpProxy( {
				url : basepath + '/lookup.json?name=ALIANCE_PROG_LEVEL'
			}),
			reader : new Ext.data.JsonReader( {
				root : 'JSON'
			}, [ 'key', 'value' ])
		});
		level.load();
		var servicechar = new Ext.data.Store( {
			restful : true,
			sortInfo : {
				field : 'key',
				direction : 'ASC'
			},
			autoLoad : true,
			proxy : new Ext.data.HttpProxy( {
				url : basepath + '/lookup.json?name=SERVICE_CHARACT'
			}),
			reader : new Ext.data.JsonReader( {
				root : 'JSON'
			}, [ 'key', 'value' ])
		});
		servicechar.load();
		var orgStore = new Ext.data.Store({
			restful : true,
			proxy : new Ext.data.HttpProxy( {
				url : '/ocrmFSeGoods!searchOrg.json'
			}),
			reader : new Ext.data.JsonReader( {
				root : 'data'
			}, [ 'key', 'value' ])
		});
		orgStore.load();
		
	    var store = new Ext.data.Store({
						restful:true,	
				        proxy : new Ext.data.HttpProxy(
				        		{
				        			url:basepath+'/alianceProgramQueryAction.json'
				        		}),
				        reader: new Ext.data.JsonReader({
				        	root : 'json.data'
				        }, [{name:'ID'},
				            {name:'ALIANCE_PROGRAM_ID'},
				        		{name:'ALIANCE_PROGRAM_NAME'},
				        		{name:'ALIANCE_PROG_LEVEL'},
				        		{name:'ORG_NAME'},
								{name:'ORGAN_CODE'},
				        		{name:'COMPANY_TYPE'},
				        		{name:'SERVICE_CHARACT'},
				        		{name:'START_DATE'},
				        		{name:'END_DATE'}]
					)
	});
	    var createAnna = createAnnGrid(true,false,true,'');
	    var infoForm = new Ext.FormPanel( {
			frame : true,
			items : [ {
				layout : 'column',
				items : [{
					layout : 'form',columnWidth : .5,labelWidth:100,
					items : [ {name : 'ALIANCE_PROGRAM_ID',xtype : 'textfield',fieldLabel : '联盟商代码',labelStyle : 'text-align:right;',disabled:true,anchor : '95%'},
					          
					        
					        {xtype:'combo',fieldLabel: '联盟商等级',disabled:true,name: 'ALIANCE_PROG_LEVEL',
								labelStyle: 'text-align:right;',
                         		store:level ,valueField : 'key',displayField : 'value',anchor:'95%'},
                            
                         	{name : 'ORG_NAME',xtype : 'combo',fieldLabel : '服务范围',
                         		store:orgStore,valueField : 'value',displayField : 'key',labelStyle : 'text-align:right;',
                         		disabled:true,anchor : '95%'},
                             	
                         	{xtype:'combo',fieldLabel: '公司类型',disabled:true,name: 'COMPANY_TYPE',labelStyle: 'text-align:right;',
                             	store: companyType,valueField : 'key',displayField : 'value',anchor:'95%'},
                             
                             {xtype:'datefield',name:'END_DATE',fieldLabel:'合作结束日期',format:'Y-m-d',disabled:true,anchor:'95%'}
                         	]
				},{
					layout : 'form',columnWidth : .5,labelWidth:100,
					items : [ {name : 'ALIANCE_PROGRAM_NAME',xtype : 'textfield',fieldLabel : '联盟商名称',labelStyle : 'text-align:right;',disabled:true,anchor : '95%'},
					{name : 'ORGAN_CODE',xtype : 'textfield',fieldLabel : '组织机构代码',labelStyle : 'text-align:right;',disabled:true,anchor : '95%'},
					
						{name : 'SERVICE_CHARACT',xtype : 'combo',fieldLabel : '服务特性',labelStyle: 'text-align:right;',
                     	store: servicechar,valueField : 'key',disabled:true,displayField : 'value',anchor:'95%'},
					
                     	{xtype:'datefield',name:'START_DATE',fieldLabel:'合作开始日期',format:'Y-m-d',disabled:true,anchor:'95%'}]
				}]
			}]
		});
	    var bussFieldSetGrid = new Ext.form.FieldSet({
		    animCollapse :true,
		    collapsible:true,
		    title: '流程业务信息',
		    items:[infoForm,createAnna]
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
		store.load({params : {
			id:id
        },
        callback:function(){
        	if(store.getCount()!=0){
        		loadFormData();
        	}
		}});
		function loadFormData(){
    		infoForm.getForm().loadRecord(store.getAt(0));
		}
		uploadForm.relaId = id;
		uploadForm.modinfo = 'infomation';
		var condi = {};
        condi['relationInfo'] = id;
        condi['relationMod'] = 'infomation';
        Ext.Ajax.request({
            url:basepath+'/queryanna.json',
            method : 'GET',
            params : {
                "condition":Ext.encode(condi)
            },
            failure : function(a,b,c){
                Ext.MessageBox.alert('查询异常', '查询失败！');
            },
            success : function(response){
                var anaExeArray = Ext.util.JSON.decode(response.responseText);
                	createAnna.store.loadData(anaExeArray.json.data);
                	createAnna.getView().refresh();
            }
        });				
	});
