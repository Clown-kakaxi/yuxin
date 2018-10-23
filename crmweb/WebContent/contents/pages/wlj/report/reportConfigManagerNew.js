/**
 * 
* @Description: 报表配置管理NEW
* @author chenmeng
* @date 2014-8-18 
*
 */



var url=basepath+"/ReportCfgNewAction.json";
var itemTypeStore = new Ext.data.SimpleStore({
	fields: ['key','value'],
	data : [['0','文本框'],['1','数值'],['2','日期'],['3','下拉框'],['4','弹出页面'],['5','多选下拉框']]
});
var YNStore = new Ext.data.SimpleStore({
	fields: ['key','value'],
	data : [['1','是'],['0','否']]
});
var teamStatusStore = new Ext.data.ArrayStore( {
    fields : [ 'key', 'value' ],
    data : [ [ '0', '配置' ], [ '1', '自定义' ] ]
});
var localLookup = {
		'REPORT_STATUS_ID' : [
		   {key : '0',value : '未发布'},
		   {key : '1',value : '已发布'},
		   {key : '2',value : '已停用'}
		],
		'REPORT_SERVER_TYPE_ID':[
		   {key : '0',value : 'Cognos'},
		   {key : '1',value : 'MicroStategy'},
		   {key : '2',value : 'BIEE'},
		   {key:'3',value:'润乾报表'}
		                      ],
		'REPORT_TYPE_ID':[
		      		   {key : '0',value : '配置'},
		    		   {key : '1',value : '自定义'}
		    		                      ]
	};
var fields=[{name:'REPORT_STATUS',text:'报表状态',resutlWidth:150,searchField:true,translateType:'REPORT_STATUS_ID'},
            {name:'REPORT_CODE',text:'报表编码',resutlWidth:150,searchField:true,dataType:'string',allowBlank:false},
            {name:'REPORT_NAME',text:'报表名称',resutlWidth:150,searchField:true,dataType:'string',allowBlank:false},
            {name:'REPORT_SERVER_TYPE',text:'服务器类型',resutlWidth:140,translateType:'REPORT_SERVER_TYPE_ID',emptyText :'请选择',allowBlank:false},
            {name:'REPORT_URL',text:'报表URL',resutlWidth:450,dataType:'string',allowBlank:false},
            {name:'REPORT_DESC',text:'备注',resutlWidth:190,dataType:'string'},
            {name:'REPORT_SORT',text:'报表排序',resutlWidth:20,dataType:'string',hidden:true},
            {name:'REPORT_TYPE',text:'报表类型',resutlWidth:20,dataType:'string',hidden:true,allowBlank:false,translateType:'REPORT_TYPE_ID',value : '0'},
            {name:'CREATOR',text:'报表人',resutlWidth:20,dataType:'string',hidden:true,allowBlank:false},
            {name:'ID',text:'ID',resutlWidth:20,dataType:'string',hidden:true}
            ];

var reportQueryRecord = Ext.data.Record.create([
                                        		{name : 'id',       mapping : 'ID'},
                                        		{name : 'reportCode',	mapping : 'REPORT_CODE'},
                                        		{name : 'reportName',	mapping : 'REPORT_NAME'},
                                        		{name : 'reportUrl',	mapping : 'REPORT_URL'},
                                        		{name : 'reportServerType',	mapping : 'REPORT_SERVER_TYPE'},
                                        		{name : 'reportStatus',	mapping : 'REPORT_STATUS'},
                                        		{name : 'reportSort',	mapping : 'REPORT_SORT'},
                                        		{name : 'reportGroup',	mapping : 'REPORT_GROUP'},
                                        		{name : 'reportType',	mapping : 'REPORT_TYPE'},
 
                                        		{name : 'reportDesc',	mapping : 'REPORT_DESC'}]);

var showReportWin = function (editFlag, loadFlag) {
	if(editFlag){
		Ext.getCmp('saveReport').setDisabled(false);
		Ext.getCmp('addRec').setDisabled(false);
		Ext.getCmp('removeRec').setDisabled(false);
	} else {
		Ext.getCmp('saveReport').setDisabled(true);
		Ext.getCmp('addRec').setDisabled(true);
		Ext.getCmp('removeRec').setDisabled(true);
	}
	showCustomerViewByTitle('新增');
	if(loadFlag) {
		var selectRe = reportGrid.getSelectionModel().getSelections()[0];
		formPanel.getForm().loadRecord(selectRe);
		
		reportCfgStore.reload({
			params : {
				reportCode : selectRe.json.REPORT_CODE
			}
		});
	} else {
		formPanel.getForm().reset();
		reportCfgStore.removeAll();
	}
};
/**
 * 
*新增面板查询部分
 */

var teamrownum = new Ext.grid.RowNumberer( {
			header : 'No.',
			width : 28
		});
		
var teamstore = new Ext.data.Store({
			restful : true,
			proxy : new Ext.data.HttpProxy( {
				url : basepath + '/ReportCfgNewAction!getCfgItems.json'
			}),
			reader : new Ext.data.JsonReader( {
				totalProperty : 'json.count',
				root : 'json.data'
			}, [
  {
				name : 'id',
				mapping : 'ID'
			}, {
				name : 'conditionField',
				mapping : 'conditionField'
			}, {
				name : 'conditionName',
				mapping : 'conditionName'
			}, {
				name : 'conditionType',
				mapping : 'conditionType'
			} , {
				name : 'isAllowBlank',
				mapping : 'isAllowBlank'
			} , {
				name : 'isHidden',
				mapping : 'isHidden'
			}, {
				name : 'conditionDefault',
				mapping : 'conditionDefault'
			}
			])
		});
		
var teamsm = new Ext.grid.CheckboxSelectionModel();
var teamcm = new Ext.grid.ColumnModel( [ teamrownum, 
                           {header : 'ID',id : 'ID',hidden : true},
                           {header : '查询项ID',dataIndex : 'conditionField',width : 100,
	                         editor : new Ext.form.TextField( {
		                                           allowBlank: false
	                                              })}, 
	                       {header : '查询项名称',dataIndex : 'conditionName',sortable : true,width : 100,
	                         editor : new Ext.form.TextField( {
		                                           allowBlank: false
	                                              })}, 
	                       {header : '查询项类型',dataIndex : 'conditionType',width : 100,sortable : false,
	                         editor : new Ext.form.ComboBox( {
				                                   mode : 'local',
				                                   store: itemTypeStore,
				                                   triggerAction : 'all',
				                                   displayField:'value',  	  					
				                                   valueField:'key',
				                                   emptyText : '文本框'
	                                               }),
	                                               renderer : function(v){
	                      	  						if (v == '0') 
	                      	  							return '文本框';
	                      	  						else if (v == '1') 
	                      	  							return '数值';
	                      	  						else if (v == '2') 
	                      	  							return '日期';
	                      	  						else if (v == '3') 
	                      	  							return '下拉框';
	                      	  						else if (v == '4') 
	                      	  							return '弹出页面';
	                      	  					    else if (v == '5') 
                      	  							    return '多选下拉框';
	                      	  						else 
	                      	  							return v;
	                      	  					}}, 
	                       {header : '必填项',dataIndex : 'isAllowBlank',width : 100,sortable : false,
	                         editor : new Ext.form.ComboBox( {
                	  			  	  			   mode : 'local',
                	  			  	  			   store: YNStore,
                	  			  	  			   triggerAction : 'all',
                	  			  	  			   displayField:'value',  	  					
                	  			  	  			   valueField:'key',
                	  			  	  			   emptyText : '否'
	                                               }),
	           	  			  	  				renderer : function(v){
	    	  				  						if (v == '1') 
	    	  				  							return '是';
	    	  				  						else if (v == '0') 
	    	  				  							return '否';
	    	  				  						else 
	    	  				  							return v;
	    	  				  					}},
	                      {header : '隐藏项',dataIndex : 'isHidden',width : 100,sortable : false,
	                        editor : new Ext.form.ComboBox( {
				                                  mode : 'local',
				                                  store: YNStore,
			                                      triggerAction : 'all',
			                                   	  displayField:'value',  	  					
				                                  valueField:'key',
				                                  emptyText : '否'
				                                }),
				  			  	  				renderer : function(v){
	       	  				  						if (v == '1') 
	       	  				  							return '是';
	       	  				  						else if (v == '0') 
	       	  				  							return '否';
	       	  				  						else 
	       	  				  							return v;
	       	  				  					}},
				          {header : '默认值',dataIndex : 'conditionDefault',width : 100,sortable : true,
				            editor : new Ext.form.TextField( {
		                                         allowBlank: true
                                                })}
]);


var teamgrid = new Ext.grid.EditorGridPanel( {
	height : 300,
	frame : true,
	overflow : 'auto',
	autoScroll : true,
	store : teamstore, // 数据存储
	stripeRows : true, // 斑马线
	cm : teamcm, // 列模型
	sm : teamsm,
	tbar:[{
		text : '新增',		
		id : 'addRec',
		handler : function() {
			var obj = {
					id : null,
					conditionField : null,
					conditionName : null,
					conditionType : '0',
					isAllowBlank : '0',
					isHidden : '0',
					conditionDefault : null								
				};
			var r = new Ext.data.Record(obj,null);
			teamstore.addSorted(r);
		}
	},'-',{
		text : '移除',
		id : 'removeRec',
		handler : function() {					
			var selectedFlag = false;
            var records = teamgrid.getSelectionModel().getSelections();
            for(var i=0;i<records.length;++i)
            {
            	teamstore.remove(records[i]);
                selectedFlag=true;
            }
            if(!selectedFlag)
            {
            	Ext.Msg.alert('提示', '请选择要移除的记录</font>!');
            }					
		}
	}
],
	loadMask : {
		msg : '正在加载表格数据,请稍等...'
	}
});
/**
 * 
*修改面板查询部分
 */	
var teamrownum1 = new Ext.grid.RowNumberer( {
header : 'No.',
width : 28
});

var teamsm1 = new Ext.grid.CheckboxSelectionModel();
var teamcm1 = new Ext.grid.ColumnModel( [ teamrownum1, 
                           {header : 'ID',id : 'ID',hidden : true},
                           {header : '查询项ID',dataIndex : 'conditionField',width : 100,
	                         editor : new Ext.form.TextField( {
		                                           allowBlank: false
	                                              })}, 
	                       {header : '查询项名称',dataIndex : 'conditionName',sortable : true,width : 100,
	                         editor : new Ext.form.TextField( {
		                                           allowBlank: false
	                                              })}, 
	                       {header : '查询项类型',dataIndex : 'conditionType',width : 100,sortable : false,
	                     	  editor : new Ext.form.ComboBox( {
	                     				           mode : 'local',
	                     				           store: itemTypeStore,
	                     				           triggerAction : 'all',
	                     				           displayField:'value',  	  					
	                     				           valueField:'key',
	                     				           emptyText : '文本框'
	                     	                       }),
	                     	                       renderer : function(v){
	                     	                      if (v == '0') 
	                     	                      	  return '文本框';
	                     	                      else if (v == '1') 
	                     	                      	  return '数值';
	                     	                      else if (v == '2') 
	                     	                      	  return '日期';
	                     	                      else if (v == '3') 
	                     	                      	  return '下拉框';
	                     	                      else if (v == '4') 
	                     	                      	  return '弹出页面';
                     	                      	  else if (v == '5') 
                     	                      	 	  return '多选下拉框';
	                     	                      else 
	                     	                      	  return v;
	                     	                      	  			}}, 
	                     	{header : '必填项',dataIndex : 'isAllowBlank',width : 100,sortable : false,
	                     	     editor : new Ext.form.ComboBox( {
	                                     	  	  mode : 'local',
	                                     	  	  store: YNStore,
	                                     	  	  triggerAction : 'all',
	                                     	  	  displayField:'value',  	  					
	                                     	  	  valueField:'key',
	                                     	  	  emptyText : '否'
	                     	                                 }),
	                     	           	  		renderer : function(v){
	                     	    	  			 if (v == '1') 
	                     	    	  			 return '是';
	                     	    	  			 else if (v == '0') 
	                     	    	  			 return '否';
	                     	    	  			 else 
	                     	    	  		    	return v;
	                     	    	  				  			}},
	                     {header : '隐藏项',dataIndex : 'isHidden',width : 100,sortable : false,
	                     	   editor : new Ext.form.ComboBox( {
	                     				         mode : 'local',
	                     				         store: YNStore,
	                     			             triggerAction : 'all',
	                     			             displayField:'value',  	  					
	                     				         valueField:'key',
	                     				         emptyText : '否'
	                     				            }),
	                     				  		renderer : function(v){
	                     	       	  				 if (v == '1') 
	                     	       	  				 return '是';
	                     	       	  				 else if (v == '0') 
	                     	       	  				  return '否';
	                     	       	  				  else 
	                     	       	  				  return v;
	                     	       	  				  }},
				          {header : '默认值',dataIndex : 'conditionDefault',width : 100,sortable : true,
				            editor : new Ext.form.TextField( {
		                                         allowBlank: true
                                                })}
]);


var teamgrid1 = new Ext.grid.EditorGridPanel( {
	height : 300,
	frame : true,
	overflow : 'auto',
	autoScroll : true,
	store : teamstore, // 数据存储
	stripeRows : true, // 斑马线
	cm : teamcm1, // 列模型
	sm : teamsm1,
	tbar:[{
		text : '新增',		
		id : 'addRec',
		handler : function() {
			var obj = {
					id : null,
					conditionField : null,
					conditionName : null,
					conditionType : '0',
					isAllowBlank : '0',
					isHidden : '0',
					conditionDefault : null								
				};
			var r = new Ext.data.Record(obj,null);
			teamstore.addSorted(r);
		}
	},'-',{
		text : '移除',
		id : 'removeRec',
		handler : function() {					
			var selectedFlag = false;
            var records = teamgrid1.getSelectionModel().getSelections();
            for(var i=0;i<records.length;++i)
            {
            	teamstore.remove(records[i]);
                selectedFlag=true;
            }
            if(!selectedFlag)
            {
            	Ext.Msg.alert('提示', '请选择要移除的记录</font>!');
            }					
		}
	}
],
	loadMask : {
		msg : '正在加载表格数据,请稍等...'
	}
});
/**
 * 
*详情面板查询部分
 */
var teamrownum2 = new Ext.grid.RowNumberer( {
header : 'No.',
width : 28
});

var teamsm2 = new Ext.grid.CheckboxSelectionModel();
var teamcm2 = new Ext.grid.ColumnModel( [ teamrownum2, 
                           {header : 'ID',id : 'ID',hidden : true},
                           {header : '查询项ID',dataIndex : 'conditionField',width : 100,
	                         editor : new Ext.form.TextField( {
		                                           allowBlank: false
	                                              })}, 
	                       {header : '查询项名称',dataIndex : 'conditionName',sortable : true,width : 100,
	                         editor : new Ext.form.TextField( {
		                                           allowBlank: false
	                                              })}, 
	                                              {header : '查询项类型',dataIndex : 'conditionType',width : 100,sortable : false,
	                     	                         editor : new Ext.form.ComboBox( {
	                     				                                   mode : 'local',
	                     				                                   store: itemTypeStore,
	                     				                                   triggerAction : 'all',
	                     				                                   displayField:'value',  	  					
	                     				                                   valueField:'key',
	                     				                                   emptyText : '文本框'
	                     	                                               }),
	                     	                                               renderer : function(v){
	                     	                      	  						if (v == '0') 
	                     	                      	  							return '文本框';
	                     	                      	  						else if (v == '1') 
	                     	                      	  							return '数值';
	                     	                      	  						else if (v == '2') 
	                     	                      	  							return '日期';
	                     	                      	  						else if (v == '3') 
	                     	                      	  							return '下拉框';
	                     	                      	  						else if (v == '4') 
	                     	                      	  							return '弹出页面';
	                     	                      	  						else if (v == '5') 
                     	                      	 	 							return '多选下拉框';
	                     	                      	  						else 
	                     	                      	  							return v;
	                     	                      	  					}}, 
	                     	                       {header : '必填项',dataIndex : 'isAllowBlank',width : 100,sortable : false,
	                     	                         editor : new Ext.form.ComboBox( {
	                                     	  			  	  			   mode : 'local',
	                                     	  			  	  			   store: YNStore,
	                                     	  			  	  			   triggerAction : 'all',
	                                     	  			  	  			   displayField:'value',  	  					
	                                     	  			  	  			   valueField:'key',
	                                     	  			  	  			   emptyText : '否'
	                     	                                               }),
	                     	           	  			  	  				renderer : function(v){
	                     	    	  				  						if (v == '1') 
	                     	    	  				  							return '是';
	                     	    	  				  						else if (v == '0') 
	                     	    	  				  							return '否';
	                     	    	  				  						else 
	                     	    	  				  							return v;
	                     	    	  				  					}},
	                     	                      {header : '隐藏项',dataIndex : 'isHidden',width : 100,sortable : false,
	                     	                        editor : new Ext.form.ComboBox( {
	                     				                                  mode : 'local',
	                     				                                  store: YNStore,
	                     			                                      triggerAction : 'all',
	                     			                                   	  displayField:'value',  	  					
	                     				                                  valueField:'key',
	                     				                                  emptyText : '否'
	                     				                                }),
	                     				  			  	  				renderer : function(v){
	                     	       	  				  						if (v == '1') 
	                     	       	  				  							return '是';
	                     	       	  				  						else if (v == '0') 
	                     	       	  				  							return '否';
	                     	       	  				  						else 
	                     	       	  				  							return v;
	                     	       	  				  					}},
				          {header : '默认值',dataIndex : 'conditionDefault',width : 100,sortable : true,
				            editor : new Ext.form.TextField( {
		                                         allowBlank: true
                                                })}
]);


var teamgrid2 = new Ext.grid.EditorGridPanel( {
	height : 300,
	frame : true,
	overflow : 'auto',
	autoScroll : true,
	store : teamstore, // 数据存储
	stripeRows : true, // 斑马线
	cm : teamcm2, // 列模型
	sm : teamsm2,
	tbar:[{
		text : '新增'
	},{
		text : '移除'
	}
],
	loadMask : {
		msg : '正在加载表格数据,请稍等...'
	}
});
/**
 * 校验整个grid必填信息
 * @author CHANGZH
 **/
var checkFeilds = function(grid) {
	if(!this.needCheckFields)
    {
        this.needCheckFields=
        {
        };
        for(var i = 0; i < grid.colModel.getColumnCount(); i++)
        {
            var oneCm = grid.colModel.getColumnById(grid.colModel.getColumnId(i));
            if(oneCm && oneCm.editor != null)
            {
                if(oneCm.editor.allowBlank == false)
                {
                    this.needCheckFields[oneCm.dataIndex] = i+1;
                }
            }
        }
    }
    grid.stopEditing();
    var records = grid.store.getRange();
    for(var i=0;i<records.length;++i)
    {
        if(records[i].data!=null)
        {
            for(var k in this.needCheckFields)
            {
                if(this.needCheckFields[k]&&((records[i].data)[k]===''||(records[i].data)[k]===null||(records[i].data)[k]===undefined))
                {
                	grid.selModel.selectRow(grid.store.indexOf(records[i]));
                    grid.startEditing(grid.store.indexOf(records[i]),this.needCheckFields[k]-1);
                    return false;
                }
            }
        }
    }
    return true;
};
/** 
 * @author CHANGZH
 * 将Ext.Json.Store对象 
 * 数组对象 
 * 转换成Json形式的字符串 
 * @param {} store 
 * @return {} 
 */  
function storeToJson(jsondata){  
    var listRecord;  
    if(jsondata instanceof Ext.data.Store){  
        listRecord = new Array();  
	    jsondata.each(function(record){  
	        listRecord.push(record.data);  
	    });  
    }else if(jsondata instanceof Array){  
        listRecord = new Array();  
        Ext.each(jsondata,function(record){  
            listRecord.push(record.data);  
        });  
    }  
    return Ext.encode(listRecord);  
} 
var reportStore = new Ext.data.Store({//查询数据源
	restful : true,
	proxy : new Ext.data.HttpProxy({
		url : basepath + '/ReportCfgNewAction.json',
		method : 'GET'
	}),
	reader : new Ext.data.JsonReader({
		successProperty : 'success',
		idProperty : 'ID',
		messageProperty : 'message',
		root : 'json.data',
		totalProperty : 'json.count'
	}, reportQueryRecord)
});
//保存调用的方法
function save(formPanel){
	if (!formPanel.getForm().isValid()) {
		Ext.MessageBox.alert('系统提示','请正确输入各项必要信息！');
		return false;
		}
	if (!checkFeilds(teamgrid)) {
        return ;
	}
	if (!checkFeilds(teamgrid1)) {
        return ;
	}
	var paramObj = {};
	paramObj.formPanel = formPanel.getForm().getValues(false);
	paramObj.reportList  = teamstore.data;
		Ext.Ajax.request( {
					url : basepath + '/ReportCfgNewAction!saveReport.json?',
					method : 'POST',
					params : {
			formPanel :  Ext.encode(formPanel.getForm().getValues(false)), 
			reportList  :  storeToJson(teamstore)
					},
					failure : function(form,action) {
						Ext.MessageBox.alert('系统提示','保存失败！');

					},
					success : function(response) {
						Ext.MessageBox.alert('系统提示','保存成功！');
						reloadCurrentData();
						hideCurrentView();
					}
				});

}
//更改报表状态方法
var updateStatus = function(msg, status, id) {
	Ext.Ajax.request({
		url : basepath + '/ReportCfgNewAction!updateReportStatus.json?reportStatus='+status+'&id='+id,
		method:'GET',
		success:function(response){
			reportStore.reload();
    		Ext.Msg.alert('提示', msg+'成功.');
    		 reloadCurrentData();
		},
		failure:function(){
			Ext.Msg.alert('提示', msg+'失败.');
		}
	});
};

/**
 * 
*新增面板
 */
var customerView =  [{
	title : '新增',
	type : 'form',
	autoLoadSeleted : false,
	groups : [{
		fields : ['REPORT_CODE','REPORT_NAME','REPORT_SERVER_TYPE','REPORT_SORT','REPORT_URL','REPORT_TYPE']
	},{
		columnCount : 1 ,
		fields : ['REPORT_DESC'],
		fn : function(REPORT_DESC){
			return [REPORT_DESC];
		}
	},{
		columnCount : 1 ,
		fields : ['REPORT_DESC'],
		fn : function(REPORT_DESC){
			return [teamgrid];
		}
	}
	],
	formButtons : [
	               {
						text : '保存',
						fn : function(formPanel,basicForm) {
	            	   save(formPanel);
						}
					}, {
						text : '重置',
						id : 'reset',
						fn : function(formPanel,basicForm) {
							teamstore.removeAll();
							formPanel.getForm().reset();
						}
					} ]
},{
	title : '修改',
	type : 'form',
	autoLoadSeleted : true,
	groups : [{
		fields : ['REPORT_CODE','REPORT_NAME','REPORT_SERVER_TYPE','REPORT_SORT','REPORT_URL','REPORT_TYPE']
	},{
		columnCount : 1 ,
		fields : ['REPORT_DESC','ID'],
		fn : function(REPORT_DESC,ID){
		ID.hidden=true;
			return [REPORT_DESC,ID];
		}
	},{
		columnCount : 1 ,
		fields : ['REPORT_DESC'],
		fn : function(REPORT_DESC){
			return [teamgrid1];
		}
	}
	],
	formButtons : [
	               {
						text : '保存',					
						fn : function(formPanel,basicForm) {
	            	   save(formPanel);
						}
					}, {
						text : '重置',
						id : 'reset',
						fn : function(formPanel,basicForm) {
							teamstore.removeAll();
							formPanel.getForm().reset();
						}
					} ]
},{
	title : '详情',
	type : 'form',
	autoLoadSeleted : true,
	groups : [{
		fields : ['REPORT_CODE','REPORT_NAME','REPORT_SERVER_TYPE','REPORT_SORT','REPORT_URL','REPORT_TYPE']
	},{
		columnCount : 1 ,
		fields : ['REPORT_DESC'],
		fn : function(REPORT_DESC){
			return [REPORT_DESC];
		}
	},{
		columnCount : 1 ,
		fields : ['REPORT_DESC'],
		fn : function(REPORT_DESC){
			return [teamgrid2];
		}
	}
	]
}
];
/**
 * 
*删除按钮
 */
var tbar = [{
	text : '删除',
	handler : function() {
	if(!getSelectedData()){
	Ext.Msg.alert('提示','请选择一条数据进行操作！');
	return false;
}
		var tempId;
		var idStr = '';
		for (var i = 0;i<getAllSelects().length;i++){//循环获取所选列id并拼装成字符串
			tempId = getAllSelects()[i].data.ID;
			idStr += tempId;
			if( i !=getAllSelects().length-1){
				idStr += ',';
			}
		}
			Ext.MessageBox.confirm('提示','确定删除吗?',function(buttonId){
				if(buttonId.toLowerCase() == "no"){
				return false;
				} 
				Ext.Ajax.request({
                    url: basepath+'/ReportCfgNewAction!delReport.json?id='+idStr, 
                    success : function(response){
                        Ext.Msg.alert('提示', '删除成功');
                        reloadCurrentData();
                    },
                    failure : function(){
                        Ext.Msg.alert('提示', '删除失败');
                    }
                });
			});
		
			}
	},{
		text : '发布',
		handler : function() {
		if(getSelectedData()==false){
			Ext.Msg.alert('提示','请选择一条数据进行操作');
			return false;
		}
		else if(getAllSelects().length>1){
			Ext.Msg.alert('提示','只能选择一条数据');
			return false;
		}
		 if (getSelectedData().data.REPORT_STATUS!= '0') {
	        	Ext.Msg.alert('提示', '请选择<font color="red">状态为未发布的记录</font>!');
	        	return false;
	        }
		 updateStatus('发布','1',getSelectedData().data.ID);	
	}
		},{
			text : '启用',
			handler : function() {	
			if(getSelectedData()==false){
				Ext.Msg.alert('提示','请选择一条数据进行操作');
				return false;
			}
			else if(getAllSelects().length>1){
				Ext.Msg.alert('提示','只能选择一条数据');
				return false;
			}
			if (getSelectedData().data.REPORT_STATUS!= '2')  {
	            	Ext.Msg.alert('提示', '请选择<font color="red">状态为已停用的记录</font>!');
	            	return;
	            }	
	            updateStatus('启用','1',getSelectedData().data.ID);						
			}
		},{
			text : '停用',
			handler : function() {
			if(getSelectedData()==false){
				Ext.Msg.alert('提示','请选择一条数据进行操作');
				return false;
			}
			else if(getAllSelects().length>1){
				Ext.Msg.alert('提示','只能选择一条数据');
				return false;
			}
			if (getSelectedData().data.REPORT_STATUS!= '1') {
	            	Ext.Msg.alert('提示', '请选择<font color="red">状态为已发布的记录</font>!');
	            	return;
	            }	
	            updateStatus('停用','2',getSelectedData().data.ID);
			}
		},
		
		{
			text : '个金目标值模板下载',
			handler : function() {
				var winPara = 'height=100, width=200, top=300, left=500, toolbar=no,menubar=no,'
						+ ' scrollbars=no, resizable=no,location=no, status=no';
				var fileName = 'importReportCfgNew.xlsx';// 模板名称
				var uploadUrl = basepath + '/TempDownload?filename=' + fileName;
				window.open(uploadUrl, '', winPara);
			}
		}, {
			text : '个金目标值导入',
			handler : function() {
				importForm.tradecode = 'importReportCfgNewInfo';
				importWindow.show();
			}
		}
		
];
/**
 * 当数据字段被动态拖动到查询条件框时触发;
 * params : fCfg：添加之前默认生成的数据项配置；
 * 			columnIndexT：将要被添加的列数；
 * 			searchPanel：查询条件form面板；
 * return ：false阻止条件添加事件；默认为true；
 */
var beforeconditionadd = function(){
	REPORT_URL;
	return false;
};


/**
 * 结果域面板滑入前触发：
 * params：theview : 当前滑入面板；
 * return： false，阻止面板滑入操作；默认为true；
 */
var beforeviewshow = function(theview){
	if(theview._defaultTitle!='新增'){
	if(getSelectedData()==false){
		Ext.Msg.alert('提示','请选择一条数据进行操作');
		return false;
	}
	else if(getAllSelects().length>1){
		Ext.Msg.alert('提示','只能选择一条数据');
		return false;
	}}
	if(theview._defaultTitle=='修改'){		
        if (getSelectedData().data.REPORT_STATUS== '1') {
        	Ext.Msg.alert('提示', '不能修改<font color="red">已发布的报表配置</font>，请先停用！');
        	return false;
        }
        teamstore.load( {
			params : {
        	reportCode : getSelectedData().data.REPORT_CODE
			}
		});
	}
	if(theview._defaultTitle=='详情'){		
        teamstore.load( {
			params : {
        	reportCode : getSelectedData().data.REPORT_CODE
			}
		});
	}
};
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
/*************导入窗口定义模块开始****************/
var _tempImpFileName = "";
var pkHead = "";
/**
 * 导入表单对象，此对象为全局对象，页面直接调用。
 */
var importForm = new Ext.FormPanel({ 
    height : 200,
    width : '100%',
    title:'文件导入',
    fileUpload : true, 
    dataName:'file',
    frame:true,
    tradecode:"",
    
    /**是否显示导入状态*/
    importStateInfo : '',
    importStateMsg : function (state){
		var titleArray = ['excel数据转化为SQL脚本','执行SQL脚本...','正在将临时表数据导入到业务表中...','导入成功！'];
		this.importStateInfo = "当前状态为：[<font color='red'>"+titleArray[state]+"</font>];<br>";
	},    
    /**是否显示 当前excel数据转化为SQL脚本成功记录数*/
    curRecordNumInfo : '',
    curRecordNumMsg : function(o){
		this.curRecordNumInfo = "当前excel数据转化为SQL脚本成功记录数[<font color='red'>"+o+"</font>];<br>";
	},
    /**是否显示 当前sheet页签记录数*/
	curSheetRecordNumInfo : '',
    curSheetRecordNumMsg : function (o) {
		this.curSheetRecordNumInfo = "当前sheet页签记录数：[<font color='red'>"+o+"</font>];<br>";
	},
    /**是否显示 当前sheet页签号*/
    sheetNumInfo : '',
    sheetNumMsg : function(o){
		this.sheetNumInfo = "当前sheet页签号为：[<font color='red'>"+o+"</font>];<br>";
	},
    /**是否显示 sheet页签总数*/
    totalSheetNumInfo : '',
    totalSheetNumMsg : function(o){
		this.totalSheetNumInfo = "sheet页签总数：[<font color='red'>"+o+"</font>];<br>";
	},
    /**是否显示 已导入完成sheet数*/
    finishSheetNumInfo : '',
    finishSheetNumMsg : function(o){
		this.finishSheetNumInfo = "已导入完成sheet数[<font color='red'>"+o+"</font>];<br>";
	},
    /**是否显示 已导入完成记录数*/
	finishRecordNumInfo : '',
    finishRecordNumMsg : function(o){
		this.finishRecordNumInfo = "已导入完成记录数[<font color='red'>"+o+"</font>];<br>";
	},
    /**当前excel数据转化为SQL脚本成功记录数*/
    curRecordNum : 0,
    /**导入总数*/
	totalNum : 1,
	/**进度条信息*/
	progressBarText : '',
	/**进度条Msg*/
	progressBartitle : '',
    proxyStore : undefined,
    items: [],
	/**进度条*/
    progressBar : null,    
    /***import成功句柄*/
    importSuccessHandler : function (json){	
		if (json != null) {	
			if (typeof(json.curRecordNum) != 'undefined' && this.curRecordNumMsg) {
				this.curRecordNumMsg(json.curRecordNum);
				this.curRecordNum = json.curRecordNum;
			}
			if (typeof(json.importState) != 'undefined' && this.importStateMsg) {
				this.importStateMsg(json.importState);
			}				
			if (typeof(json.curSheetRecordNum) != 'undefined' && this.curSheetRecordNumMsg) {
				this.curSheetRecordNumMsg(json.curSheetRecordNum);
			}
			if (typeof(json.sheetNum) != 'undefined' && this.sheetNumMsg) {
				this.sheetNumMsg(json.sheetNum);
			}
			if (typeof(json.totalSheetNum) != 'undefined' && this.totalSheetNumMsg) {
				this.totalSheetNumMsg(json.totalSheetNum);
			}	
			if (typeof(json.finishSheetNum) != 'undefined' && this.finishSheetNumMsg) {
				this.finishSheetNumMsg(json.finishSheetNum);
			}
			if (typeof(json.finishRecordNum) != 'undefined' && this.finishRecordNumMsg) {
				this.finishRecordNumMsg(json.finishRecordNum);
			}
		} else {
			this.curRecordNumInfo = '';
			this.importStateInfo = '';
			this.curSheetRecordNumInfo = '';
			this.sheetNumInfo = '';
			this.totalSheetNumInfo = '';
			this.finishSheetNumInfo = '';
			this.finishRecordNumInfo = '';
		}
		
		this.progressBartitle = '';
		/**进度条Msg信息配置：各信息项目显示内容由各自方法配置*/
		this.progressBartitle += this.curRecordNumMsg      ?this.curRecordNumInfo:'';
		this.progressBartitle += this.importStateMsg 	   ?this.importStateInfo:'';
		this.progressBartitle += this.curSheetRecordNumMsg ?this.curSheetRecordNumInfo:'';
		this.progressBartitle += this.sheetNumMsg 	   	   ?this.sheetNumInfo:'';
		this.progressBartitle += this.totalSheetNumMsg 	   ?this.totalSheetNumInfo:'';
		this.progressBartitle += this.finishSheetNumMsg    ?this.finishSheetNumInfo:'';
		this.progressBartitle += this.finishRecordNumMsg   ?this.finishRecordNumInfo:'';
		
		showProgressBar(this.totalNum,this.curRecordNum,this.progressBarText,this.progressBartitle,"上传成功，正在导入数据，请稍候");
	},
    buttons : [{
            text : '导入文件',
            handler : function() {
                var tradecode = this.ownerCt.ownerCt.tradecode;
                var proxyStorePS = this.ownerCt.ownerCt.proxyStore;
                var proxyHttpPH = this.ownerCt.ownerCt.proxyHttp;
                if(tradecode==undefined ||tradecode==''){
                    Ext.MessageBox.alert('Debugging！','You forgot to define the tradecode for the import form!');
                    return false;
                }
                var impRefreshHandler = 0;
                if (this.ownerCt.ownerCt.getForm().isValid()){
                    this.ownerCt.ownerCt.ownerCt.hide();
                    var fileNamesFull = this.ownerCt.ownerCt.items.items[0].getValue();
                    var extPoit = fileNamesFull.substring(fileNamesFull.indexOf('.'));
                    if(extPoit=='.xls'||extPoit=='.XLS'||extPoit=='.xlsx'||extPoit=='.XLSX'){
                    } else {
                    	Ext.MessageBox.alert("文件错误","导入文件不是XLS或XLSX文件。");
                        return false;
                    }
                    showProgressBar(1,0,'','','正在上传文件...');
                    
                    this.ownerCt.ownerCt.getForm().submit({
                        url : basepath + '/FileUpload?isImport=true',
                        success : function(form,o){                    		 
                            _tempImpFileName = Ext.util.JSON.decode(o.response.responseText).realFileName;
                            var condi = {};
                            condi['filename'] =_tempImpFileName;
                            condi['tradecode'] = tradecode;
                            Ext.Ajax.request({
                                url:basepath+"/ImportAction.json",
                                method:'GET',
                                params:{
                                    "condition":Ext.encode(condi)
                                },
                                success:function(){
                                	importForm.importSuccessHandler(null);                                	
                                    var importFresh = function(){
                                        Ext.Ajax.request({
                                            url:basepath+"/ImportAction!refresh.json",
                                            method:'GET',
                                            success:function(a){                                        		
                                                if(a.status == '200' || a.status=='201'){
                                                    var res = Ext.util.JSON.decode(a.responseText);
                                                    if(res.json.result!=undefined&&res.json.result=='200'){
                                                        window.clearInterval(impRefreshHandler); 
                                                        if(res.json.BACK_IMPORT_ERROR&&res.json.BACK_IMPORT_ERROR=='FILE_ERROR'){
                                                        	Ext.Msg.alert("提示","导出文件格式有误，请下载导入模版。");
                                                        	return;
                                                        }
                                                        if(proxyStorePS!=undefined){
                                                            var condiFormP = {};
                                                            condiFormP['pkHaed'] =res.json.PK_HEAD;
                                                            pkHead = res.json.PK_HEAD;
                                                            proxyStorePS.load({
                                                                params:{
                                                                    pkHead: pkHead
                                                                }
                                                            });
                                                        }else {
                                                        	importForm.importSuccessHandler(res.json); 
                                                        	showSuccessWin(res.json.curRecordNum);
                                                        }
                                                    }else if(res.json.result!=undefined&&res.json.result=='900'){
                                                        window.clearInterval(impRefreshHandler);
                                                        new Ext.Window({
                                                            title:"导入失败：导入线程处理失败！",
                                                            width:200,
                                                            height:200,
                                                            bodyStyle:'text-align:center',
                                                            html: '<img src="'+basepath+'/contents/img/UltraMix55.gif" />'
                                                        }).show();
                                                    }else if (res.json.result!=undefined&&res.json.result=='999'){
                                                    	importForm.importSuccessHandler(res.json);
                                                    }
                                                }
                                            }
                                        });
                                    };
                                    impRefreshHandler = window.setInterval(importFresh, 1000);
                                },
                                failure:function(){}
                            });
                           
                        },
                        failure : function(form, o){
                            Ext.Msg.show({
                                title : 'Result',
                                msg : '数据文件上传失败，请稍后重试!',
                                buttons : Ext.Msg.OK,
                                icon : Ext.Msg.ERROR
                            });
                        }
                    });
                }
            }
        }]
});
/**
 * 导入弹出窗口，此对象为全局对象，由各页面直接调用。
 */

var importWindow = new Ext.Window({     
    width : 700,
    hideMode : 'offsets',
    modal : true,
    height : 250,
    closeAction:'hide',
    items : [importForm]
});
importWindow.on('show',function(upWindow){
	if(Ext.getCmp('littleup')){
		importForm.remove(Ext.getCmp('littleup'));
	}
	importForm.removeAll(true);
	importForm.add(new Ext.form.TextField({
        xtype : 'textfield',
        id:'littleim',
        name:'annexeName',
        inputType:'file',
        fieldLabel : '文件名称',
        anchor : '90%'
    }));
	importForm.doLayout();
});
var progressBar = {};
var importState = false;
var progressWin = new Ext.Window({     
    width : 300,
    hideMode : 'offsets',
    closable : true,
    modal : true,
    autoHeight : true,
    closeAction:'hide',
    items : [],
    listeners :{
		'beforehide': function(){
			return importState;
		}
	}
});
function showProgressBar(count,curnum,bartext,msg,title) {
	importState = false;
	progressBar = new Ext.ProgressBar({width : 285 });
	progressBar.wait({
        interval: 200,          	//每次更新的间隔周期
        duration: 5000,             //进度条运作时候的长度，单位是毫秒
        increment: 5,               //进度条每次更新的幅度大小，默示走完一轮要几次（默认为10）。
        fn: function () {           //当进度条完成主动更新后履行的回调函数。该函数没有参数。
			progressBar.reset();
        }
    });
	progressWin.removeAll();
	progressWin.setTitle(title);
	if (msg.length == 0) {
		msg = '正在导入...';
	}
	var importContext = new Ext.Panel({
								title: '',
								frame : true,
								region :'center',
								height : 100,
								width : '100%',
								autoScroll:true,
								html : '<span>'+ msg +'</span>'
							});
	progressWin.add(importContext);
	progressWin.add(progressBar);
	progressWin.doLayout();
	progressWin.show();
	
}
function showSuccessWin(curRecordNum) {
	importState = true;
	progressWin.removeAll();
	progressWin.setTitle("成功导入记录数为["+curRecordNum+"]");
	progressWin.add(new Ext.Panel({
		title:'',
		width:300,
		layout : 'fit',
		autoHeight : true,
		bodyStyle:'text-align:center',
		html: '<img src="'+basepath+'/contents/img/UltraMix55.gif" />'
	}));
	progressWin.doLayout();
	progressWin.show();
}

/*************导入窗口定义模块结束****************/
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
