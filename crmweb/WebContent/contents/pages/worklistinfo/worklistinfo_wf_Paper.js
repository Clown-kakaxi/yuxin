	Ext.onReady(function() {
		Ext.QuickTips.init();
		var instanceid = curNodeObj.instanceid;
		var id = instanceid.split('_')[1];
		var nodeid = curNodeObj.nodeid;
		
		var type = new Ext.data.Store( {
			restful : true,
			sortInfo : {
				field : 'key',
				direction : 'ASC'
			},
			autoLoad : true,
			proxy : new Ext.data.HttpProxy( {
				url : basepath + '/lookup.json?name=OPTION_TYPE'
			}),
			reader : new Ext.data.JsonReader( {
				root : 'JSON'
			}, [ 'key', 'value' ])
		});
		type.load();
		
	    var store1 = new Ext.data.Store({
						restful:true,	
				        proxy : new Ext.data.HttpProxy(
				        		{
				        			url:basepath+'/paperManage.json'
				        		}),
				        reader: new Ext.data.JsonReader({
				        	root : 'json.data'
				        }, [{name:'PAPER_NAME'},
				        		{name:'OPTION_TYPE'},
				        		{name:'REMARK'}]
					)
	});
	    /**
	     * 试卷选题列表
	     */
	    var titleTypeStore =  new Ext.data.Store( {
	    	restful : true,
	    	autoLoad : true,
	    	proxy : new Ext.data.HttpProxy( {
	    		url : basepath + '/lookup.json?name=TITLE_TYPE'
	    	}),
	    	reader : new Ext.data.JsonReader( {
	    		root : 'JSON'
	    	}, [ 'key', 'value' ])
	    });
	    var sm2 = new Ext.grid.CheckboxSelectionModel();
	    var rownum = new Ext.grid.RowNumberer( {
	    	header : 'No.',
	    	width : 28
	    });

	    // 定义列模型
	    var questionCm = new Ext.grid.ColumnModel( [ rownum,{
	    	header : '试题标题',
	    	dataIndex : 'titleName',
	    	sortable : true,
	    	menuDisabled : true,
	    	width : document.body.scrollWidth / 4,
	    	renderer : function(value, meta, record) {
	    		meta.attr = 'style="white-space:normaddl;"';
	            		return value;
	    	}
	    }, {
	    	header : '试题分类',
	    	dataIndex : 'titleType',
	    	sortable : true,
	    	menuDisabled : true,
	    	width : document.body.scrollWidth / 8,
	    	renderer : function(value) {
	    		if (value != '') {
	    			var index = titleTypeStore.find('key', value);
	    			return titleTypeStore.getAt(index).get('value');
	    		}

	    	}
	    }, {
	    	header : '选项顺序',
	    	dataIndex : 'titlesort',
	    	renderer:function(value,record,e){
	    	var sortValue =e.json.QUESTION_ORDER;
	    		var sort = '<input id='+e.id+'sort type="textfield" value='+sortValue+' />';
	    		return  sort;
	    	},
	    	width : 165,
	    	sortable : true
	    }, {
	    	header : 'title_id',
	    	dataIndex : 'titleId',
	    	menuDisabled : true,
	    	hidden : true
	    }, {
	    	header : '选题',
	    	dataIndex : 'IS_CHECKED',
	    	width : document.body.scrollWidth / 12,
	    	renderer:function(value,record,e){
	    		var checked =(e.json.IS_CHECKED=='1')?'checked':'';
	    		var checkBox = '<input id='+e.id+'_check type="checkbox" '+checked+' />';
	    		return  checkBox;
	    	},
	    	menuDisabled : true,
	    	hidden : false
	    } ]);

	    var store = new Ext.data.Store( {
	    	restful : true,
	    	proxy : new Ext.data.HttpProxy( {
	    		url : basepath + '/questionQuery.json'
	    	}),
	    	reader : new Ext.data.JsonReader( {
	    		totalProperty : 'json.count',
	    		root : 'json.data'
	    	}, [ {
	    		name : 'titleName',
	    		mapping : 'TITLE_NAME'
	    	}, {
	    		name : 'titleType',
	    		mapping : 'TITLE_TYPE'
	    	}, {
	    		name : 'titleSort',
	    		type:'number',
	    		mapping : 'QUESTION_ORDER'
	    	}, {
	    		name : 'available',
	    		mapping : 'AVAILABLE'
	    	}, {
	    		name : 'updator',
	    		mapping : 'USER_NAME'
	    	}, {
	    		name : 'updateDate',
	    		mapping : 'UPDATE_DATE'
	    	}, {
	    		name : 'titleId',
	    		mapping : 'TITLE_ID'
	    	}, {
	    		name : 'is_checked',
	    		mapping : 'IS_CHECKED'
	    	} ])
	    });

	    var questionGrid =  new Ext.grid.EditorGridPanel( {
	    	height : 250,
//	    	width : 790,
	    	frame : true,
	    	autoScroll : true,
	    	region : 'center', // 返回给页面的div
	    	store : store,
	    	stripeRows : true, // 斑马线
	    	cm : questionCm,
	    	sm : sm2,
	    	viewConfig : {}
	    });
	    
	    var infoForm = new Ext.FormPanel( {
			frame : true,
			items : [ {
				layout : 'column',
				items : [{
					layout : 'form',columnWidth : .5,labelWidth:100,
					items : [ {name : 'PAPER_NAME',xtype : 'textfield',fieldLabel : '问卷名称',labelStyle : 'text-align:right;',disabled:true,anchor : '95%'}]
				},{
					layout : 'form',columnWidth : .5,labelWidth:100,
					items : [ {xtype:'combo',fieldLabel: '问卷类型',disabled:true,name: 'OPTION_TYPE',labelStyle: 'text-align:right;',
             			store: type,valueField : 'key',displayField : 'value',anchor:'95%'}]
				}]
			},{
				layout : 'form',labelWidth:100,
				items:[{xtype : 'textarea',name : 'REMARK',fieldLabel : '问卷备注',labelStyle : 'text-align:right;',disabled:true,anchor : '97%'}]
			}]
		});
	    var bussFieldSetGrid = new Ext.form.FieldSet({
		    animCollapse :true,
		    collapsible:true,
		    title: '流程业务信息',
		    items:[infoForm,questionGrid]
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
			
		store1.load({params : {
			id:id
        },
        callback:function(){
        	if(store1.getCount()!=0){
        		loadFormData();
        	}
		}});
		function loadFormData(){
    		infoForm.getForm().loadRecord(store1.getAt(0));
		}
		
		store.load({
			 params : {
				'paperId':id
			 }
		});
		
	});
