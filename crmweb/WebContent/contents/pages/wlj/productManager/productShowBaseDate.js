/**
 * @description 产品展示基础数据处理
 * @author luyy
 * @since 2014-05-13
 */

		//引用数据字典store
		var lookupIdStore = new Ext.data.JsonStore({
			id : lookupIdStore,
			restful : true,
			proxy : new Ext.data.HttpProxy({
				url : basepath + '/lookup-mapping.json'
			}),
			fields : [ 'name','comment' ],
			reader : new Ext.data.JsonReader({
				totalProperty : 'list'
			}, [ {
				name : 'name',
				mapping : 'name'
			},{
				name : 'comment',
				mapping : 'comment'
			} ])
		});
		lookupIdStore.load({
			callback:function(){
			var newcreate = Ext.data.Record.create(['name', 'comment']);
			var recordadd = new newcreate({name:'',comment:'无'});
			lookupIdStore.insert(0,recordadd);
		}
		});
		
		var alignStore = new Ext.data.Store( {
			restful : true,
			autoLoad : true,
			proxy : new Ext.data.HttpProxy( {
				url : basepath + '/lookup.json?name=ALIGN_TYPE'
			}),
			reader : new Ext.data.JsonReader( {
				root : 'JSON'
			},['key','value'])
		});
		
		var columnTypeStore = new Ext.data.Store( {
			restful : true,
			autoLoad : true,
			proxy : new Ext.data.HttpProxy( {
				url : basepath + '/lookup.json?name=COLUMN_TYPE'
			}),
			reader : new Ext.data.JsonReader( {
				root : 'JSON'
			},['key','value'])
		});
		
		var numberField = new Ext.form.NumberField({allowBlank : false,minValue:100,maxValue:400,decimalPrecision : 0});  
		var textField = new Ext.form.TextField({allowBlank : false,minValue:0}); 
		var columnrownum = new Ext.grid.RowNumberer({
				header : 'No.',
				width : 28
				});   
		//数据集方案字段设置CM
		var cmodel = new Ext.grid.ColumnModel([
		                      		         columnrownum, 
		                      		        {header :'COLUMN_ID',dataIndex:'COLUMN_ID',width:130,sortable : false,hidden:true},
		                      				{header :'TABLE_ID',dataIndex:'TABLE_ID',width:130,sortable : false,hidden:true},
		                      				{header :'表名',dataIndex:'TABLE_NAME',width:130,sortable : false,hidden:true},
		                      				{header :'表中文名',dataIndex:'TABLE_CH_NAME',width:130,sortable : false,hidden:true},
		                      				{header :'表别名',dataIndex:'TABLE_OTH_NAME',width:130,sortable : false,hidden:true},
		                      		        {header :'字段名',dataIndex:'COLUMN_NAME',width:130,sortable : false},
		                      		        {header :'中文名称 ',dataIndex:'COLUMN_OTH_NAME',width:130,sortable : false,editor:textField},
		                      		        {header :'字段类型',dataIndex:'COLUMN_TYPE',width:100,sortable:true,editor :{
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
		                      		        }},
		                      				{header :'对齐方式',dataIndex:'ALIGN_TYPE',width:100,sortable:false,editor :{
		                      		        	xtype:'combo',
		                      		        	store : alignStore,
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
		                      		        		var stolength = alignStore.data.items;
		                      		        		var i=0;
		                      		        		for(i=0;i< stolength.length;i++){
		                      		        			if(stolength[i].data.key==val){
		                      		        					return stolength[i].data.value;
		                      		        			}
		                      		        		}
		                      		        	}
		                      		        return val;	
		                      		        }},
		                      		        {header :'宽度',dataIndex:'SHOW_WIDTH',width:80,sortable:false,editor:numberField},
		                      		        {header :'引用字典',dataIndex : 'DIC_NAME',width:150,editor :{
		                      		        	xtype:'combo',
		                      		        	store : lookupIdStore,
		                      		        	mode : 'local',
		                      		        	triggerAction : 'all',
		                      		        	valueField : 'name',
		                      		        	displayField : 'comment',
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
		                      		        		var stolength = lookupIdStore.data.items;
		                      		        		var i=0;
		                      		        		for(i=0;i< stolength.length;i++){
		                      		        			if(stolength[i].data.name==val){
		                      		        					return stolength[i].data.comment;
		                      		        			}
		                      		        		}
		                      		        	}
		                      		        return val;	
		                      		        }}
		                      		        ]);
		//数据集方案字段设置store
		var columnStore = new Ext.data.Store({
			restful : true,
			proxy : new Ext.data.HttpProxy(
					{
						url : basepath + '/productCloumn.json'
					}),
					reader : new Ext.data.JsonReader( {
						root : 'json.data'
					}, [{name:'COLUMN_ID'},
					{name:'TABLE_ID'},
					{name:'TABLE_NAME'},
					{name:'TABLE_CH_NAME'},
					{name:'TABLE_OTH_NAME'},
					{name:'COLUMN_NAME'},
					{name:'COLUMN_OTH_NAME'},
					{name:'COLUMN_TYPE'},
					{name:'ALIGN_TYPE'},
					{name:'DIC_NAME'},
					{name:'SHOW_WIDTH'}])
		});
		
		//数据集方案字段设置Grid
		var columnGrid = new Ext.grid.EditorGridPanel( {
			frame : true,
			clicksToEdit : 1,
			id : 'assignInfoGrid',				
			height : 400,
			store : columnStore,
			loadMask : true,
			cm : cmodel,
			buttonAlign : 'center',
			buttons : [{
				text : '保存',
				handler : function() {
					var json1 = {'COLUMN_ID':[]};
					var json2 = {'TABLE_ID':[]};
					var json3 = {'TABLE_NAME':[]};
					var json4 = {'TABLE_CH_NAME':[]};
					var json5 = {'TABLE_OTH_NAME':[]};
					var json6 = {'COLUMN_NAME':[]};
					var json7 = {'COLUMN_OTH_NAME':[]};
					var json8 = {'COLUMN_TYPE':[]};
					var json9 = {'ALIGN_TYPE':[]};
					var json10 = {'DIC_NAME':[]};
					var json11 = {'SHOW_WIDTH':[]};
				 
					 for(var i=0;i<columnStore.getCount();i++){
			             var temp=columnStore.getAt(i);
			                 json1.COLUMN_ID.push(temp.data.COLUMN_ID);
			                 json2.TABLE_ID.push(temp.data.TABLE_ID);
			                 json3.TABLE_NAME.push(temp.data.TABLE_NAME);
			                 json4.TABLE_CH_NAME.push(temp.data.TABLE_CH_NAME);
			                 json5.TABLE_OTH_NAME.push(temp.data.TABLE_OTH_NAME);
							 json6.COLUMN_NAME.push(temp.data.COLUMN_NAME);
							 json7.COLUMN_OTH_NAME.push(temp.data.COLUMN_OTH_NAME);
							 json8.COLUMN_TYPE.push(temp.data.COLUMN_TYPE);
							 json9.ALIGN_TYPE.push(temp.data.ALIGN_TYPE);
							 json10.DIC_NAME.push(temp.data.DIC_NAME);
							 json11.SHOW_WIDTH.push(temp.data.SHOW_WIDTH);									
			         }
					Ext.Msg.wait('正在保存，请稍后......','系统提示');
					Ext.Ajax.request({
			             url : basepath + '/productCloumn!saveData.json',
			             method : 'POST',
			             waitMsg : '正在保存数据,请等待...', // 显示读盘的动画效果，执行完成后效果消失
			             params:{
			            	 COLUMN_IDs:Ext.encode(json1),
							 TABLE_IDs:Ext.encode(json2),
							 TABLE_NAMEs:Ext.encode(json3),
							 TABLE_CH_NAMEs:Ext.encode(json4),
							 TABLE_OTH_NAMEs:Ext.encode(json5),
							 COLUMN_NAMEs:Ext.encode(json6),
							 COLUMN_OTH_NAMEs:Ext.encode(json7),
							 COLUMN_TYPEs:Ext.encode(json8),
							 ALIGN_TYPEs:Ext.encode(json9),
							 DIC_NAMEs:Ext.encode(json10),
							 SHOW_WIDTHs:Ext.encode(json11)
							 
			             },
			             success : function() {
			                 Ext.Msg.alert('提示', '操作成功');
			                 columnStore.load({
			                 params:{
			                	 "tableId":getSelectedData().data.TABLE_ID
								 }
			                 });
			                 columnGrid.getView().refresh();
			             },
			             failure : function(response) {
			                 Ext.Msg.alert('提示', '操作失败,失败原因:' + response.responseText);
			             }
			         });
				}
			}]
		});	
	//用于做提交前检查的store		
		var TableStore = new Ext.data.Store( {
			restful : true,
			autoLoad : true,
			proxy : new Ext.data.HttpProxy( {
				url : basepath + '/productShowBase.json'
			}),
			reader : new Ext.data.JsonReader( {
				root : 'json.data'
			},['TABLE_ID','TABLE_NAME','TABLE_OTH_NAME'])
		});		
		
var createView = true;
var editView = true;
var detailView = true;

var url = basepath+'/productShowBase.json';

var lookupTypes = ['TABLE_TYPE',{
	TYPE : 'TABLE_NAME',
	url : '/datasetmanagerquery!queryDataSetSolution.json',
	key : 'VALUE',
	value : 'VALUE',
	root : 'JSON.data'
}];

var fields = [
	  		    {name: 'TABLE_ID',text:'表ID'},
	  		    {name: 'TABLE_NAME',text : '表名',translateType : 'TABLE_NAME',allowBlank:false,resutlWidth:250},
	  		    {name: 'TABLE_CH_NAME', text : '中文表名', searchField: true,allowBlank:false,resutlWidth:250},                                   
	  		    {name: 'TABLE_TYPE',text:'类型',translateType : 'TABLE_TYPE',allowBlank:false},  
	  		    {name: 'TABLE_OTH_NAME', text :'表别名',allowBlank:false}
	  		];

var createFormViewer = [{
	fields : ['TABLE_ID','TABLE_NAME','TABLE_CH_NAME','TABLE_TYPE','TABLE_OTH_NAME'],
	fn : function(TABLE_ID,TABLE_NAME,TABLE_CH_NAME,TABLE_TYPE,TABLE_OTH_NAME){
		TABLE_ID.hidden = true;
		return [TABLE_NAME,TABLE_OTH_NAME,TABLE_CH_NAME,TABLE_TYPE,TABLE_ID];
	}
}];

var editFormViewer = [{
	fields : ['TABLE_ID','TABLE_NAME','TABLE_CH_NAME','TABLE_TYPE','TABLE_OTH_NAME'],
	fn : function(TABLE_ID,TABLE_NAME,TABLE_CH_NAME,TABLE_TYPE,TABLE_OTH_NAME){
		TABLE_ID.hidden = true;
		TABLE_NAME.readOnly = true;
		TABLE_OTH_NAME.readOnly = true;
		TABLE_NAME.cls = 'x-readOnly';
		TABLE_OTH_NAME.cls = 'x-readOnly';
		return [TABLE_ID,TABLE_NAME,TABLE_OTH_NAME,TABLE_CH_NAME,TABLE_TYPE];
	}
}];

var detailFormViewer = [{
	fields : ['TABLE_ID','TABLE_NAME','TABLE_CH_NAME','TABLE_TYPE','TABLE_OTH_NAME'],
	fn : function(TABLE_ID,TABLE_NAME,TABLE_CH_NAME,TABLE_TYPE,TABLE_OTH_NAME){
		TABLE_ID.hidden = true;
		TABLE_NAME.readOnly = true;
		TABLE_OTH_NAME.readOnly = true;
		TABLE_NAME.cls = 'x-readOnly';
		TABLE_OTH_NAME.cls = 'x-readOnly';
		TABLE_TYPE.readOnly = true;
		TABLE_OTH_NAME.readOnly = true;
		TABLE_TYPE.cls = 'x-readOnly';
		TABLE_OTH_NAME.cls = 'x-readOnly';
		return [TABLE_ID,TABLE_NAME,TABLE_OTH_NAME,TABLE_CH_NAME,TABLE_TYPE];
	}
}];



var tbar = [{
	text : '删除',
	handler : function(){
		if(getSelectedData() == false){
			Ext.Msg.alert('提示','请选择数据！');
			return false;
		}else{
			//如果没有被方案引用则可以删除
			var ID = '';
			for (var i=0;i<getAllSelects().length;i++){
				ID += getAllSelects()[i].data.TABLE_ID;
				ID += ",";
			}
			ID = ID.substring(0, ID.length-1);
			Ext.Ajax.request({
				url : basepath + '/productShowBase!checkDel.json',
				params : {
				'ids':ID
				},
				success : function(response) {
					var del = 'ok';
					del =  response.responseText;
					if(del != 'ok'){
						Ext.Msg.alert('提示', '表：'+del+'被展示方案引用，不能删除！' );
						return  false;
					}else if(del == 'ok'){
						Ext.MessageBox.confirm('提示','确定删除吗?',function(buttonId){
							if(buttonId.toLowerCase() == "no"){
							return false;
							} 
						    Ext.Ajax.request({
			                    url: basepath+'/productShowBase!batchDel.json?idStr='+ID,                                
			                    success : function(){
			                        Ext.Msg.alert('提示', '删除成功');
			                        TableStore.reload();
			                        reloadCurrentData();
			                    },
			                    failure : function(){
			                        Ext.Msg.alert('提示', '删除失败');
			                        reloadCurrentData();
			                    }
			                });
						});
					}
				},
				failure : function(response) {
					Ext.Msg.alert('提示', '检查可否删除失败');
					return false;
				}
			});
		}
	}
}];

var customerView = [{
	title : '表属性定义',
	xtype : 'panel',
	frame : true,
	layout : 'fit',
	items:[columnGrid],
	recordView : true
}];

/**修改和详情面板滑入之前判断是否选择了数据 **/
var beforeviewshow = function(view){
	if(view == getEditView()||view == getDetailView()||view._defaultTitle=='表属性定义'){
			if(getSelectedData() == false||getAllSelects().length>1){
				Ext.Msg.alert('提示','请选择一条数据');
				return false;
			}else if(view._defaultTitle=='表属性定义'){//加载属性数据
				columnStore.load({
					params:{
	                  "tableId":getSelectedData().data.TABLE_ID
					}
				});
				columnGrid.getView().refresh();
			}	
	}
};

//判断所选表是否已经配置过，判断别名是否已经使用 
var linkages = {
			TABLE_NAME:{
			fields : [],
			fn : function(TABLE_NAME){
				if(TableStore.getCount() != 0 && getCurrentView() == getCreateView()){
					TableStore.each(function(e){
						if(e.get('TABLE_NAME') == TABLE_NAME.getValue()){
							Ext.Msg.alert('提示', '表:'+TABLE_NAME.getValue()+'已经存在，请修改！');
							getCurrentView().setValues({
								TABLE_NAME : ''
							});
							return false;
						}
					});
				}
			}},
			TABLE_OTH_NAME:{
				fields : [],
				fn : function(TABLE_OTH_NAME){
					if(TableStore.getCount() != 0 && getCurrentView() == getCreateView()){
						TableStore.each(function(e){
							if(e.get('TABLE_OTH_NAME') == TABLE_OTH_NAME.getValue()){
								Ext.Msg.alert('提示', '表:'+TABLE_OTH_NAME.getValue()+'已经存在，请修改！');
								getCurrentView().setValues({
									TABLE_OTH_NAME : ''
								});
								return false;
							}
						});
					}
				}}
};


/** 提交之前判断所选表是否已经配置过，判断别名是否已经使用         已修改到linkages中**/
//var beforecommit = function(data){
//	if(TableStore.getCount() != 0 && getCurrentView() == getCreateView()){
//		TableStore.each(function(e){
//			if(e.get('TABLE_NAME') == data.TABLE_NAME){
//				Ext.Msg.alert('提示', '表:'+data.TABLE_NAME+'已经存在，请修改！');
//				getCurrentView().setValues({
//					TABLE_NAME : ''
//				});
//				return false;
//			}else if(e.get('TABLE_OTH_NAME') == data.TABLE_OTH_NAME){
//				Ext.Msg.alert('提示', '别名:'+data.TABLE_OTH_NAME+'已使用,请修改！');
//				getCurrentView().setValues({
//					TABLE_OTH_NAME : ''
//				});
//				return false;
//			}
//		});
//	}
//};

/*提交完成后，重新加载TableStore*/
var afertcommit = function(){
	TableStore.reload();
};

TableStore.load();


