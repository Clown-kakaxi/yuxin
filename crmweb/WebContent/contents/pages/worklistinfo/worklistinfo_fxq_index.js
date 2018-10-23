	Ext.onReady(function() {
		Ext.QuickTips.init();
		var instanceid = curNodeObj.instanceid;
		var id = instanceid.split('_')[1];
		var nodeid = curNodeObj.nodeid;
		//当前指标配置
		var storeOld = new Ext.data.Store({
			restful : true,
			proxy : new Ext.data.HttpProxy(
					{
						url : basepath + '/antiMoney!old.json'
					}),
					reader : new Ext.data.JsonReader( {
						root : 'data'
					}, [{name:'id',mapping:'ID'},
					    {name:'indexValue',mapping:'INDEX_VALUE'},
					    {name:'indexValueName',mapping:'INDEX_VALUE_NAME'},
					    {name:'indexScore',mapping:'INDEX_SCORE'},
					    {name:'indexRight',mapping:'INDEX_RIGHT'},
		                {name:'highFlag',mapping:'HIGH_FLAG'}])
		});
		
		//备注（数据字典类型）store
		var columnTypeStore = new Ext.data.Store( {
			restful : true,
			autoLoad : true,
			proxy : new Ext.data.HttpProxy( {
				url : basepath + '/lookup.json?name=IF_FLAG'
			}),
			reader : new Ext.data.JsonReader( {
				root : 'JSON'
			},['key','value'])
		});
		columnTypeStore.load();
		//复核中的指标配置
		var storeApply = new Ext.data.Store({
			restful : true,
			proxy : new Ext.data.HttpProxy(
					{
						url : basepath + '/antiMoney!apply.json'
					}),
					reader : new Ext.data.JsonReader( {
						root : 'data'
					}, [{name:'id',mapping:'ID'},
					    {name:'indexValue',mapping:'INDEX_VALUE'},
					    {name:'indexValueName',mapping:'INDEX_VALUE_NAME'},
					    {name:'indexScore',mapping:'INDEX_SCORE'},
					    {name:'indexRight',mapping:'INDEX_RIGHT'},
					    {name:'highFlag',mapping:'HIGH_FLAG'}])
		});
	    var colold1= new Ext.grid.RowNumberer({
	    	header : 'No.',
	    	width : 28
	    	});   
	    //数据集方案字段设置CM
	    var cmold1 = new Ext.grid.ColumnModel([
	                      		         colold1, 
	                      		        {header :'id',dataIndex:'ID',width:130,sortable : false,hidden:true},
	                      				{header :'indexValue',dataIndex:'indexValue',width:130,sortable : false,hidden:true},
	                      				{header :'指标取值',dataIndex:'indexValueName',width:130,sortable : false},
	                      				{header :'指标得分',dataIndex:'indexScore',width:130,sortable : false},
	                      				{header :'指标权重(%)',dataIndex:'indexRight',width:130,sortable : false},
	                      				{header :'高风险标识',dataIndex:'highFlag',width:130,sortable : false,editor :{
	                    		        	xtype:'combo',
	                    		        	store : columnTypeStore,
	                    		        	mode : 'local',
	                    		        	triggerAction : 'all',
	                    		        	valueField : 'key',
	                    		        	displayField : 'value',
	                    		        	forceSelection:true,
	                    					resizable:true,
	                    					typeAhead : true,
	                    					emptyText : '请选择',
	                    		        	listeners:{
	                    		        	select:function(){
	                    		        		var valuefind = this.value;
	                    		        		this.fireEvent('blur',this);
	                    		        	}
	                    		        	}},sortable : false,
	                    		        	renderer:function(val){
	                    		        	if(val!=''){
	                    		        		var stolength = columnTypeStore.data.items;
	                    		        		var i=0;
	                    		        		for(i=0;i< stolength.length;i++){
	                    		        			if(stolength[i].data.key==val){
	                    		        					return stolength[i].data.value;
	                    		        			}
	                    		        		}
	                    		        	}
	                    		        return val;	
	                    		        }}
	                      		        ]);
	    var oldGrid1 = new Ext.grid.GridPanel( {
	    	frame : true,
	    	title:'原指标配置',
	    	height : 300,
	    	store : storeOld,
	    	loadMask : true,
	    	cm : cmold1
	    });	


	    var colapply= new Ext.grid.RowNumberer({
	    	header : 'No.',
	    	width : 28
	    	});   
	    //数据集方案字段设置CM
	    var cmapply = new Ext.grid.ColumnModel([
	                      		         colapply, 
	                      		        {header :'id',dataIndex:'ID',width:130,sortable : false,hidden:true},
	                      				{header :'indexValue',dataIndex:'indexValue',width:130,sortable : false,hidden:true},
	                      				{header :'指标取值',dataIndex:'indexValueName',width:130,sortable : false},
	                      				{header :'指标得分',dataIndex:'indexScore',width:130,sortable : false},
	                      				{header :'指标权重(%)',dataIndex:'indexRight',width:130,sortable : false},
	                      				{header :'高风险标识',dataIndex:'highFlag',width:130,sortable : false,editor :{
	                    		        	xtype:'combo',
	                    		        	store : columnTypeStore,
	                    		        	mode : 'local',
	                    		        	triggerAction : 'all',
	                    		        	valueField : 'key',
	                    		        	displayField : 'value',
	                    		        	forceSelection:true,
	                    					resizable:true,
	                    					typeAhead : true,
	                    					emptyText : '请选择',
	                    		        	listeners:{
	                    		        	select:function(){
	                    		        		var valuefind = this.value;
	                    		        		this.fireEvent('blur',this);
	                    		        	}
	                    		        	}},sortable : false,
	                    		        	renderer:function(val){
	                    		        	if(val!=''){
	                    		        		var stolength = columnTypeStore.data.items;
	                    		        		var i=0;
	                    		        		for(i=0;i< stolength.length;i++){
	                    		        			if(stolength[i].data.key==val){
	                    		        					return stolength[i].data.value;
	                    		        			}
	                    		        		}
	                    		        	}
	                    		        return val;	
	                    		        }}
	                      		        ]);
	    var applyGrid = new Ext.grid.GridPanel( {
	    	frame : true,
	    	height : 300,
	    	title:'新指标配置',
	    	store : storeApply,
	    	loadMask : true,
	    	cm : cmapply
	    });	
	    var applyPanel = new Ext.Panel({
	    	autoScroll : true,
	    	layout : 'column',
	    	items : [  {columnWidth : .5,
	    	            items :[oldGrid1]
	    	           },{columnWidth : .5,
	    	            items :[applyGrid]
	    	           } ]
	    	});
	    var bussFieldSetGrid = new Ext.form.FieldSet({
		    animCollapse :true,
		    collapsible:true,
		    title: '流程业务信息',
		    items:[applyPanel]
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
		storeOld.load({
			params:{
				applyId:id
			}
		});
		storeApply.load({
			params:{
				applyId:id
			}
		});
		
		
	});
