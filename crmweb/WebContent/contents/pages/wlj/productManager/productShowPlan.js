/**
 * @description 产品展示方案处理
 * @author luyy
 * @since 2014-05-14
 */
//待选字段store

var type = '';//type 表示客户视图方案或产品信息方案
var columnListStore = new Ext.data.Store({
	restful : true,
	proxy : new Ext.data.HttpProxy(
			{
				url : basepath + '/showcolumn!toAdd.json'
			}),
			reader : new Ext.data.JsonReader( {
				root : 'data'
			}, [{name:'columnId',mapping:'COLUMN_ID'},
			{name:'rTableId',mapping:'R_TABLE_ID'},
			{name:'tableChName',mapping:'TABLE_CH_NAME'},
			{name:'columnOthName',mapping:'COLUMN_OTH_NAME'}])
});
//已选字段store
var columnStore = new Ext.data.Store({
	restful : true,
	proxy : new Ext.data.HttpProxy(
			{
				url : basepath + '/showcolumn.json'
			}),
			reader : new Ext.data.JsonReader( {
				root : 'json.data'
					
			}, [{name:'rCloumnId',mapping:'R_CLOUMN_ID'},
			{name:'planId',mapping:'PLAN_ID'},
			{name:'rTableId',mapping:'R_TABLE_ID'},
			{name:'columnId',mapping:'COLUMN_ID'},
			{name:'columnOthName',mapping:'COLUMN_OTH_NAME'},
			{name:'cloumnSquence',mapping:'CLOUMN_SQUENCE'}])
});
//待选字段grid
var smcmAdd = new Ext.grid.CheckboxSelectionModel();
var rowcmnumAdd = new Ext.grid.RowNumberer({
	  header : 'No.',
	  width : 28
});

//待选关联表cm
var ColumnscmAdd =  new Ext.grid.ColumnModel([rowcmnumAdd,smcmAdd,	// 定义列模型
        	                                     {header : 'columnId', dataIndex : 'columnId',sortable : true,width : 120,hidden:true},
        	                                     {header : 'rTableId', dataIndex : 'rTableId',sortable : true,width : 120,hidden:true},
        	                                     {header : '引用表中文名', dataIndex : 'tableChName',sortable : true,width : 120},
        	                                     {header : '字段中文名', dataIndex : 'columnOthName',sortable : true,width : 120}
        	                                     ]);
//待选关联表grid
var columnAddList = new Ext.grid.GridPanel({
	height:430,
	stripeRows : true,
	tbar : [{
        text : '添加-》',
        handler:function() {
			var checkedNodes = columnAddList.getSelectionModel().selections.items;
      		var selectLength = columnAddList.getSelectionModel().getSelections().length;
      		var selectRe;
      		var tempId;
      		var idStr = '';
			var tableID = '';
      		if (selectLength < 1) {
      			Ext.Msg.alert('提示', '请选择要添加的记录！');
      		} else {
      				for (var i = 0; i < checkedNodes.length; i++) {
      					idStr += checkedNodes[i].data.columnId;
      					idStr += ',';
						tableID += checkedNodes[i].data.rTableId;
      					tableID += ',';
      				}
      				 Ext.Msg.wait('正在保存，请稍后......','系统提示');
      					Ext.Ajax.request({
      								url : basepath + '/showcolumn!addcolumns.json',
      								method : 'POST',
      								params : {
      									"planId":getSelectedData().data.PLAN_ID,
      									"tableID":tableID,
      									ids : idStr
      								},
      								success : function() {
      									Ext.Msg.alert('提示', '操作成功！');
      									columnListStore.load({
      										params:{
      						                  "planId":getSelectedData().data.PLAN_ID
      										}
      									});
      									columnAddList.getView().refresh();
										columnStore.load({
      										params:{
      						                  "planId":getSelectedData().data.PLAN_ID
      										}
      									});
      									columnList.getView().refresh();
      								},
      								failure : function(response) {
      										Ext.Msg.alert('提示','操作失败，失败原因：'+ response.responseText);
      								}
      							});
      		}
    }
    }],
	store : columnListStore,
	frame : true,
	sm:smcmAdd,
	cm : ColumnscmAdd
});
//已选字段grid
var smcm = new Ext.grid.CheckboxSelectionModel();
var rownumcm = new Ext.grid.RowNumberer({
	  header : 'No.',
	  width : 28
});
var numberField = new Ext.form.NumberField({allowBlank : false,minValue:1,maxValue:100,decimalPrecision : 0});  
//关联表cm
var rColumnscm =  new Ext.grid.ColumnModel([rownumcm,smcm,	// 定义列模型
												{header : 'rCloumnId', dataIndex : 'rCloumnId',sortable : true,width : 120,hidden:true},
												 {header : 'planId', dataIndex : 'planId',sortable : true,width : 120,hidden:true},
												 {header : 'rTableId', dataIndex : 'rTableId',sortable : true,width : 120,hidden:true},
        	                                     {header : 'columnId', dataIndex : 'columnId',sortable : true,width : 120,hidden:true},
        	                                     {header : '字段中文名', dataIndex : 'columnOthName',sortable : true,width : 120},
        	                                     {header : '字段排序', dataIndex : 'cloumnSquence',sortable : true,width : 120,editor:numberField}
        	                                     ]);
//关联表grid
var columnList = new Ext.grid.EditorGridPanel({
	clicksToEdit : 1,
	height:430,
	stripeRows : true,
	tbar : [{
        text : '《-移出',
        handler:function() {
		var checkedNodes = columnList.getSelectionModel().selections.items;
      		var selectLength = columnList.getSelectionModel().getSelections().length;
      		var selectRe;
      		var tempId;
      		var idStr = '';
			if (selectLength < 1) {
      			Ext.Msg.alert('提示', '请选择要移出的记录！');
      		} else {
      				for (var i = 0; i < checkedNodes.length; i++) {
      					idStr += checkedNodes[i].data.rCloumnId;
      					idStr += ',';
					}
      				 Ext.Msg.wait('正在保存，请稍后......','系统提示');
      					Ext.Ajax.request({
      								url : basepath + '/showcolumn!delcolumns.json',
      								method : 'POST',
      								params : {
      									ids : idStr
      								},
      								success : function() {
      									Ext.Msg.alert('提示', '操作成功！');
      									columnListStore.load({
      										params:{
      						                  "planId":getSelectedData().data.PLAN_ID
      										}
      									});
      									columnAddList.getView().refresh();
										columnStore.load({
      										params:{
      						                  "planId":getSelectedData().data.PLAN_ID
      										}
      									});
      									columnList.getView().refresh();
      								},
      								failure : function(response) {
      										Ext.Msg.alert('提示','操作失败，失败原因：'+ response.responseText);
      								}
      							});
		}
    }},{
    	text:'保存',
    	handler:function(){
			var json1 = {'rCloumnId':[]};
			var json2 = {'cloumnSquence':[]};
		 
			 for(var i=0;i<columnStore.getCount();i++){
	             var temp=columnStore.getAt(i);
	             if(temp.data.cloumnSquence != null&&temp.data.cloumnSquence != ''&&temp.data.cloumnSquence != undefined){
	            	 json1.rCloumnId.push(temp.data.rCloumnId);
	                 json2.cloumnSquence.push(temp.data.cloumnSquence);
	             }
	         }
			Ext.Msg.wait('正在保存，请稍后......','系统提示');
			Ext.Ajax.request({
	             url : basepath + '/showcolumn!saveData.json',
	             method : 'POST',
	             waitMsg : '正在保存数据,请等待...', // 显示读盘的动画效果，执行完成后效果消失
	             params:{
	            	 rCloumnIds:Ext.encode(json1),
					 cloumnSquences:Ext.encode(json2)
	             },
	             success : function() {
	                 Ext.Msg.alert('提示', '操作成功');
	                 columnStore.load({
										params:{
						                  "planId":getSelectedData().data.PLAN_ID
										}
									});
					columnList.getView().refresh();
	             },
	             failure : function(response) {
	                 Ext.Msg.alert('提示', '操作失败,失败原因:' + response.responseText);
	             }
	         });
		
    	}
    	}],
	store : columnStore,
	frame : true,
	sm:smcm,
	cm : rColumnscm
});

//属性配置											 
var columnPanel = new Ext.Panel({
autoScroll : true,
layout : 'column',
items : [  {columnWidth : .5,
            items :[columnAddList]
           },{columnWidth : .5,
            items :[columnList]
           } ]
});

//待选关联表
var tableListStore = new Ext.data.Store({
	restful : true,
	proxy : new Ext.data.HttpProxy(
			{
				url : basepath + '/showTables!toAdd.json'
			}),
			reader : new Ext.data.JsonReader( {
				root : 'data'
			}, [{name:'tableId',mapping:'TABLE_ID'},
			{name:'tableName',mapping:'TABLE_NAME'},
			{name:'tableChName',mapping:'TABLE_CH_NAME'},
			{name:'tableOthName',mapping:'TABLE_OTH_NAME'}])
});
var smAdd = new Ext.grid.CheckboxSelectionModel();
var rownumAdd = new Ext.grid.RowNumberer({
	  header : 'No.',
	  width : 28
});

//待选关联表cm
var relationColumnsAdd =  new Ext.grid.ColumnModel([rownumAdd,smAdd,	// 定义列模型
        	                                     {header : 'tableId', dataIndex : 'tableId',sortable : true,width : 120,hidden:true},
        	                                     {header : '引用表', dataIndex : 'tableName',sortable : true,width : 120},
        	                                     {header : '引用表中文名', dataIndex : 'tableChName',sortable : true,width : 120},
        	                                     {header : '引用表别名', dataIndex : 'tableOthName',sortable : true,width : 120}
        	                                     ]);
//待选关联表grid
var relationGridAdd = new Ext.grid.GridPanel({
	region : 'center',
	stripeRows : true,
	tbar : [{
        text : '选定',
        handler:function() {
        	var checkedNodes = relationGridAdd.getSelectionModel().selections.items;
      		var selectLength = relationGridAdd.getSelectionModel().getSelections().length;
      		var selectRe;
      		var tempId;
      		var idStr = '';
      		if (selectLength < 1) {
      			Ext.Msg.alert('提示', '请选择要添加的记录！');
      		} else {//添加表，然后重新加载relationStore
      				for (var i = 0; i < checkedNodes.length; i++) {
      					idStr += checkedNodes[i].data.tableId;
      					idStr += ',';
      				}
      				 Ext.Msg.wait('正在保存，请稍后......','系统提示');
      					Ext.Ajax.request({
      								url : basepath + '/showTables!addTable.json',
      								method : 'POST',
      								params : {
      									"planId":getSelectedData().data.PLAN_ID,
      									"planName":getSelectedData().data.PLAN_NAME,
      									ids : idStr
      								},
      								success : function() {
      									Ext.Msg.alert('提示', '操作成功！');
      									choseTableWin.hide();
      									relationStore.load({
      										params:{
      						                  "planId":getSelectedData().data.PLAN_ID
      										}
      									});
      									relationGrid.getView().refresh();
      								},
      								failure : function(response) {
      										Ext.Msg.alert('提示','操作失败，失败原因：'+ response.responseText);
      								}
      							});
      		}
    }}],
	store : tableListStore,
	frame : true,
	sm:smAdd,
	cm : relationColumnsAdd
});
//添加表窗口
var choseTableWin = new Ext.Window({
	layout : 'fit',
    autoScroll : true,
    draggable : true,
    closable : true,
    closeAction : 'hide',
    modal : true,
    width : 500,
    height : 300,
    loadMask : true,
    border : false,
    items : [ {
        buttonAlign : "center",
        layout : 'fit',
        items : [relationGridAdd]
    }]
});

//表关系store
var relationRStore = new Ext.data.Store({
	restful : true,
	proxy : new Ext.data.HttpProxy(
			{
				url : basepath + '/showTableR.json'
			}),
			reader : new Ext.data.JsonReader( {
				root : 'json.data'
			}, [{name:'rId',mapping:'R_ID'},
			    {name:'planId',mapping:'PLAN_ID'},
				{name:'planName',mapping:'PLAN_NAME'},
				{name:'rFrom',mapping:'R_FROM'},
				{name:'rWhere',mapping:'R_WHERE'},
				{name:'custColumn',mapping:'CUST_COLUMN'}])
});
//表关系from
var relationForm = new Ext.form.FormPanel({
    frame : true,
    buttonAlign : "center",
    region : 'center',
    autoScroll : true,
    height:250,
    labelWidth : 80,
    items:[{ 
    	layout : 'column',
        items:[
               {columnWidth : 1,
	            layout : 'form',
	            items :[{name:'rId',hidden:true,xtype:'textfield'},
	                    {xtype : 'textfield',fieldLabel : '<font color="red">*</font>客户连接字段',name : 'custColumn',anchor : '90%',hidden:true},
	                    {xtype : 'textarea',fieldLabel : '<font color="red">*</font>关联表语句',allowBlank:false,name : 'rFrom',anchor : '90%'},
	                    {xtype : 'textarea',fieldLabel : '<font color="red">*</font>表连接语句',name : 'rWhere',allowBlank:false,anchor : '90%'},
	                    {name:'planId',hidden:true,xtype:'textfield'},
	                    {name:'planName',hidden:true,xtype:'textfield'}
	                    ]
               }]
    }]
});

//关联表store
var relationStore = new Ext.data.Store({
	restful : true,
	proxy : new Ext.data.HttpProxy(
			{
				url : basepath + '/showTables.json'
			}),
			reader : new Ext.data.JsonReader( {
				root : 'json.data'
			}, [{name:'rTableId',mapping:'R_TABLE_ID'},
			{name:'planId',mapping:'PLAN_ID'},
			{name:'planName',mapping:'PLAN_NAME'},
			{name:'tableId',mapping:'TABLE_ID'},
			{name:'tableName',mapping:'TABLE_NAME'},
			{name:'tableChName',mapping:'TABLE_CH_NAME'},
			{name:'tableOthName',mapping:'TABLE_OTH_NAME'}])
});

var sm = new Ext.grid.CheckboxSelectionModel();
var rownum = new Ext.grid.RowNumberer({
	  header : 'No.',
	  width : 28
});

//关联表cm
var relationColumns =  new Ext.grid.ColumnModel([rownum,sm,	// 定义列模型
                                                 {header : 'rTableId', dataIndex : 'rTableId',sortable : true,width : 120,hidden : true}, 
        	                                     {header : 'planId', dataIndex : 'planId',sortable : true,width : 120,hidden : true}, 
        	                                     {header : '方案名称', dataIndex : 'planName',sortable : true,width : 120},
        	                                     {header : 'tableId', dataIndex : 'tableId',sortable : true,width : 120,hidden:true},
        	                                     {header : '引用表', dataIndex : 'tableName',sortable : true,width : 120},
        	                                     {header : '引用表中文名', dataIndex : 'tableChName',sortable : true,width : 120},
        	                                     {header : '引用表别名', dataIndex : 'tableOthName',sortable : true,width : 120}
        	                                     ]);
//关联表grid
var relationGrid = new Ext.grid.GridPanel({
	sm:sm,
	region : 'north',
	height:170,
	tbar : [{
        text : '新增',
        iconCls:'addIconCss',
        handler:function() {
        	choseTableWin.show();
        	tableListStore.reload({
        		params:{
	                  "planId":getSelectedData().data.PLAN_ID
					}
        	});
    }},{
    text : '删除',
    iconCls:'deleteIconCss',
    handler:function() {
    	var checkedNodes = relationGrid.getSelectionModel().selections.items;
  		var selectLength = relationGrid.getSelectionModel().getSelections().length;
  		var selectRe;
  		var tempId;
  		var idStr = '';
  		if (selectLength < 1) {
  			Ext.Msg.alert('提示', '请选择要删除的记录！');
  		} else {//删除表，然后重新加载relationStore
  				for (var i = 0; i < checkedNodes.length; i++) {
  					idStr += checkedNodes[i].data.rTableId;
  					idStr += ',';
  				}
  				 Ext.Msg.wait('正在保存，请稍后......','系统提示');
  					Ext.Ajax.request({
  								url : basepath + '/showTables!delTable.json',
  								method : 'POST',
  								params : {
  									ids : idStr
  								},
  								success : function() {
  									Ext.Msg.alert('提示', '操作成功！');
  									relationStore.load({
  										params:{
  						                  "planId":getSelectedData().data.PLAN_ID
  										}
  									});
  									relationGrid.getView().refresh();
  								},
  								failure : function(response) {
  										Ext.Msg.alert('提示','操作失败，失败原因：'+ response.responseText);
  								}
  							});
  		}

    }}],
	store : relationStore,
	frame : true,
	cm : relationColumns
});
	
//表关系处理的panel
var relationPanel = new Ext.Panel({
	autoScroll : true,
	buttonAlign : "center",
	layout:'border',
	items : [  relationGrid,relationForm ,new Ext.Panel({
		region : 'south',
		height:60,
		html:'<div style="line-height:150%"> &ensp;&ensp;&ensp;&ensp;填写要求:<p/>'
			+'&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;客户连接字段指连接客户条件的字段，必须包含表别名；'
			+'关联表语句 从from开始写，表别名必须使用关联表定义中的别名 ;表连接语句 从where写起，如果没有特殊条件，写where 1=1</div>'
	})],
	buttons : [
//	    {
//		text:'测试sql查看',
//		handler : function(){
//			if (!relationForm.getForm().isValid()) {
//		         Ext.MessageBox.alert('提示','输入有误,请检查输入项');
//		         return false;
//		     }else{
//		    	 Ext.MessageBox.alert('语句(仅用于查看表连接语句是否正确,不是最终的查询语句)','select '+relationForm.form.findField("custColumn").getValue()==null+
//		    			 ' '+relationForm.form.findField("rFrom").getValue()+' '+
//		    			 relationForm.form.findField("rWhere").getValue());
//		     }
//		}
//	    },
	{ 
		 text : '保存', 
		 handler : function(){
			 if (!relationForm.getForm().isValid()) {
		         Ext.MessageBox.alert('提示','输入有误,请检查输入项');
		         return false;
		     }
			 if(type=='1')//视图方案
	            	if(relationForm.getForm().findField("custColumn").getValue()==null||relationForm.getForm().findField("custColumn").getValue()==''){
	            		Ext.MessageBox.alert('提示','请填客户连接字段');
	                    return false;
	            	}
		     Ext.Msg.wait('正在保存，请稍后......','系统提示');
				Ext.Ajax.request({
		             url : basepath + '/showTableR.json',
		             method : 'POST',
		             waitMsg : '正在保存数据,请等待...', // 显示读盘的动画效果，执行完成后效果消失
		             form : relationForm.getForm().id,
		             success : function() {
		                 Ext.Msg.alert('提示', '操作成功');
						 Ext.Ajax.request({
					         url: basepath +'/showTableR!getPid.json',
						         success:function(response){
						        	 rId = Ext.util.JSON.decode(response.responseText).pid;
						        	 relationForm.form.findField("rId").setValue(rId);
							 	}
							 });
						 Ext.Msg.alert('提示','操作成功！');
		             },
		             failure : function(response) {
		                 Ext.Msg.alert('提示', '操作失败,失败原因:' + response.responseText);
		             }
		         });
		 }
		}]
});

var createView = true;
var editView = true;
var detailView = true;

var url = basepath+'/productPlan.json';
var comitUrl = basepath+'/productPlan!save.json';


var lookupTypes = ['PLAN_TYPE'];

var fields = [
	  		    {name: 'PLAN_ID',text:'方案ID'},
	  		    {name: 'PLAN_NAME',text : '方案名称',searchField: true,allowBlank:false,resutlWidth:250},
	  		    {name: 'PLAN_TYPE', text : '方案类型',translateType : 'PLAN_TYPE', allowBlank:false,resutlWidth:120},                                   
	  		    {name: 'CREATE_USER',text:'创建人',allowBlank:false},  
	  		    {name: 'CREATE_DATE',text:'创建时间',allowBlank:false},  
	  		    {name: 'REMARK', text :'备注',allowBlank:true,resutlWidth:250,xtype:'textarea'}
	  		];

var editFormViewer = [{
	fields : ['PLAN_ID','PLAN_NAME','PLAN_TYPE'],
	fn : function(PLAN_ID,PLAN_NAME,PLAN_TYPE){
		PLAN_ID.hidden = true;
		return [PLAN_NAME,PLAN_TYPE,PLAN_ID];
	}
},{
	columnCount : 1 ,
	fields : ['REMARK'],
	fn : function(REMARK){
		return [REMARK];
	}
}];

var formViewers = [{
	fields : ['PLAN_NAME','PLAN_TYPE'],
	fn : function(PLAN_NAME,PLAN_TYPE){
//		PLAN_NAME.readOnly=true;
//		PLAN_NAME.cls='x-readOnly';
//		PLAN_TYPE.readOnly=true;
//		PLAN_TYPE.cls='x-readOnly';
		return [PLAN_NAME,PLAN_TYPE];
	}
},{
	columnCount : 1 ,
	fields : ['REMARK'],
	fn : function(REMARK){
		REMARK.readOnly=true;
		REMARK.cls='x-readOnly';
		return [REMARK];
	}
}];

var tbar = [{
	text : '删除',
	handler : function(){
		if(getSelectedData() == false){
			Ext.Msg.alert('提示','请选择数据！');
			return false;
		}else{
			//如果没有被用则可以删除
			var ID = '';
			for (var i=0;i<getAllSelects().length;i++){
				ID += getAllSelects()[i].data.PLAN_ID;
				ID += ",";
			}
			ID = ID.substring(0, ID.length-1);
			Ext.Ajax.request({
				url : basepath + '/productPlan!checkDel.json',
				params : {
				'ids':ID
				},
				success : function(response) {
					var del = 'ok';
					del =  response.responseText;
					if(del != 'ok'){
						Ext.Msg.alert('提示', '方案：'+del+'被引用，不能删除！' );
						return  false;
					}else if(del == 'ok'){
						Ext.MessageBox.confirm('提示','确定删除吗?',function(buttonId){
							if(buttonId.toLowerCase() == "no"){
							return false;
							} 
						    Ext.Ajax.request({
			                    url: basepath+'/productPlan!batchDel.json?idStr='+ID,                                
			                    success : function(){
			                        Ext.Msg.alert('提示', '删除成功');
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
	title : '表关联定义',
	xtype : 'panel',
	frame : true,
	layout : 'fit',
	items:[relationPanel],
	recordView : true
},{
	title : '属性定义',
	xtype : 'panel',
	frame : true,
	layout : 'fit',
	items:[columnPanel],
	recordView : true
}];

/**修改和详情面板滑入之前判断是否选择了数据 **/
var beforeviewshow = function(view){
	if(view == getEditView()||view == getDetailView()||view._defaultTitle == '表关联定义'||view._defaultTitle=='属性定义'){
			if(getSelectedData() == false){
				Ext.Msg.alert('提示','请选择一条数据');
				return false;
			}else if(view._defaultTitle == '表关联定义'){
				type = getSelectedData().data.PLAN_TYPE;
				//1.查询relationStore（关联表）
				relationStore.load({
					params:{
	                  "planId":getSelectedData().data.PLAN_ID
					}
				});
				relationGrid.getView().refresh();
				//2.查询relationRStore（关联关系）
				relationRStore.load({
					params:{
	                  "planId":getSelectedData().data.PLAN_ID
					},
					callback:function(){
						if(relationRStore.getCount()!=0){
							relationForm.getForm().loadRecord(relationRStore.getAt(0));
			        	}else{//还没有设置，直接设置隐藏域的初始值
			        		relationForm.form.findField("planId").setValue(getSelectedData().data.PLAN_ID);
			        		relationForm.form.findField("planName").setValue(getSelectedData().data.PLAN_NAME);
			        		relationForm.form.findField("rFrom").setValue('');
			        		relationForm.form.findField("rWhere").setValue('');
			        		relationForm.form.findField("custColumn").setValue('');
			        		relationForm.form.findField("rId").setValue('');
			        	}
					}
				});
				if(type == '1'){//视图
					relationForm.getForm().findField("custColumn").show();
				}else{
					relationForm.getForm().findField("custColumn").hide();
				}
			}else if(view._defaultTitle == '属性定义'){
				columnListStore.load({
						params:{
		                  "planId":getSelectedData().data.PLAN_ID
						}
					});
					columnAddList.getView().refresh();
				columnStore.load({
						params:{
		                  "planId":getSelectedData().data.PLAN_ID
						}
					});
					columnList.getView().refresh();
			}	
	}
};	


