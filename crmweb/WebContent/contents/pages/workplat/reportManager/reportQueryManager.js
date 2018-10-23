/**
 * 报表查询
 * @author CHANGZH
 * @since 2013-05-22
 */
Ext.onReady(function(){
	var reportUrl = '';
	var rownum = new Ext.grid.RowNumberer({//定义自动当前页行号
		header : 'No.',
		width : 28
	});
	var reportQueryRecord = Ext.data.Record.create([
		{name : 'id',       mapping : 'ID'},
		{name : 'reportCode',	mapping : 'REPORT_CODE'},
		{name : 'reportName',	mapping : 'REPORT_NAME'}]);
	var reportStore = new Ext.data.Store({//查询数据源
		restful : true,
		autoLoad : true,
		proxy : new Ext.data.HttpProxy({
			url : basepath + '/ReportQueryAction.json',
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
	var pagesize_combo = new Ext.form.ComboBox({//每页显示条数下拉选择框
		name : 'pagesize',
		triggerAction : 'all',
		mode : 'local',
		store : new Ext.data.ArrayStore({
			fields : ['value', 'text'],
			data : [[10, '10条/页'], [20, '20条/页'],[50, '50条/页'], [100, '100条/页'],[250, '250条/页'], [500, '500条/页']]
		}),
		valueField : 'value',
		displayField : 'text',
		value : '20',
		forceSelection : true,
		width : 85
	});

	pagesize_combo.on("select", function(comboBox) {//改变每页显示条数reload数据
		pageBar.pageSize = parseInt(pagesize_combo.getValue()), reportStore.reload({
			params : {
				start : 0,
				limit : parseInt(pagesize_combo.getValue())
			}
		});
	});
	var reportColumns = new Ext.grid.ColumnModel([rownum, //multiSm,//列模型 
	{header : 'ID',dataIndex : 'id',sortable : true,hidden : true,width : 120},
	{header : '报表编码',dataIndex : 'reportCode',hidden : true,width : 100},
	{header : '报表名称',dataIndex : 'reportName',sortable : false,width : 100}]);
	var pageBar = new Ext.PagingToolbar({//分页工具栏
		pageSize : parseInt(pagesize_combo.getValue()),
		store : reportStore,
		displayInfo : true,
		displayMsg : '显示{0}条到{1}条,共{2}条',
		emptyMsg : "没有符合条件的记录",
		items : ['-', '&nbsp;&nbsp;', pagesize_combo]
	});
	var getQueryField = function(field) {
		var queryField = {};
		queryField.xtype = 'textfield';
		queryField.fieldLabel = field.conditionName;
		queryField.name = field.conditionField;
		if ('0' == reportServer) {//COGNOS报表参数前缀
			queryField.name = 'p_' + field.conditionField;	
		}
		queryField.labelStyle = 'text-align:right;',
		queryField.anchor = '90%';
		queryField.hidden = false;
		if ('1'== field.isHidden) {
			queryField.hidden = true;
		}
		queryField.allowBlank = true;
		if ('1'== field.isAllowBlank) {
			queryField.allowBlank = false;
		}
		if (field.conditionType == '0') {
			queryField.xtype = 'textfield';
			queryField.value = field.conditionDefault;
		} else if (field.conditionType == '1') {
			queryField.xtype = 'numberfield';
			queryField.value = field.conditionDefault;
		} else if (field.conditionType == '2') {
			queryField.xtype = 'datefield';
			if(field.conditionDefault == 'Y-M-D'){
				queryField.format = 'Y-m-d';	
			}else if(field.conditionDefault == 'YMD'){
				queryField.format = 'Ymd';	
			}else if(field.conditionDefault == 'Y-M'){
				queryField.format = 'Y-m';	
			}else if(field.conditionDefault == 'YM'){
				queryField.format = 'Ym';	
			}else {
				queryField.format = 'Ymd';			
			}
		} else if (field.conditionType == '3') {
			queryField.hiddenName = field.conditionField;
			queryField.name = field.conditionField+"_name";
			queryField.xtype = 'combo';
			queryField.mode = 'local';
			queryField.valueField = 'key',
			queryField.displayField = 'value',
			queryField.triggerAction = 'all',
			queryField.store = new Ext.data.JsonStore({
				fields: ['key','value'],
				data : Ext.util.JSON.decode(field.conditionDefault)
			});
		}else if (field.conditionType == '5') {
			queryField.hiddenName = field.conditionField;
			queryField.name = field.conditionField+"_name";
			queryField.xtype = 'lovcombo';
			queryField.labelStyle = 'text-align:right;';
			queryField.mode = 'local';
			queryField.valueField = 'key',
			queryField.displayField = 'value',
			queryField.triggerAction = 'all',
			queryField.store = new Ext.data.JsonStore({
					fields: ['key','value'],
					data : Ext.util.JSON.decode(field.conditionDefault)
				});
		} else if (field.conditionType == '4') {
			queryField.hiddenName = field.conditionField;
			queryField.name = field.conditionField+"_name";
			if ('0' == reportServer) {//COGNOS报表参数前缀
				queryField.hiddenName = 'p_' + field.conditionField;
			}
			//TODO MSTR 参数有问题(参数按个数传递)
			if (field.conditionDefault == 'ORG') {
				queryField.xtype = 'orgchoose';
				queryField.checkBox = true;
				queryField.searchType = 'SUBTREE';	
				/**
				 * 对法金报表做限制
				 */
				var roleCodes = __roleCodes;// 当前用户拥有的据角色编码  
				if (roleCodes != null && roleCodes != "") {
					var roleArrs = roleCodes.split('$');
//					for ( var i = 0; i < roleArrs.length; i++) {
//						if (roleArrs[i] == "R304" || roleArrs[i] == "R305" || roleArrs[i] == "R104" || roleArrs[i] == "R105" || //法金RM ARM 
//							roleArrs[i] == "R309" || roleArrs[i] == "R106" ) {//法金team head
							queryField.value = __unitname;
//						}
//					}
				}
			} else if (field.conditionDefault == 'USER') {
				queryField.xtype = 'userchoose';
				queryField.searchType = 'SUBTREE';/*指定查询机构范围属性  SUBTREE（子机构树）SUBORGS（直接子机构）PARENT（父机构）PARPATH （所有父、祖机构）ALLORG（所有机构）*/
				queryField.singleSelect = false;
				/**
				 * 对法金报表做限制
				 */
				var roleCodes = __roleCodes;// 当前用户拥有的据角色编码  
				if (roleCodes != null && roleCodes != "") {
					queryField.allowBlank = true;
					var roleArrs = roleCodes.split('$');
					for ( var i = 0; i < roleArrs.length; i++) {
						if (roleArrs[i] == "R304" || roleArrs[i] == "R305" || roleArrs[i] == "R104" || roleArrs[i] == "R105" || //法金RM ARM 
							roleArrs[i] == "R309" || roleArrs[i] == "R106" ) {//法金team head
							queryField.value = __userName;
							queryField.allowBlank = false;
							queryField.fieldLabel = "<font color='red'>*</font>"+field.conditionName;
						}
					}
				}
			} else if (field.conditionDefault == 'CUST') {
				queryField.xtype = 'customerquery';
				queryField.singleSelect = false;
			}
		}
		return queryField;
	};
	var getQueryConditions = function(type , reportCode) {
		Ext.getCmp('queryForm').removeAll(true);
		Ext.getCmp('queryForm').doLayout();
		if ('0' == reportServer) {
			var hiddenItems = [{xtype:'hidden', name :'m', value : 'portal/report-viewer.xts'},
			                   {xtype:'hidden', name :'ui.action', value : 'run'},
			                   {xtype:'hidden', name :'b_action' , value : 'cognosViewer'},
			                   {xtype:'hidden', name :'nh' , value : '1'},
			                   {xtype:'hidden', name :'tb' , value : '0'},
			                   {xtype:'hidden', name :'m_save' , value : 'false'},
			                   {xtype:'hidden', name :'m_execute' , value : 'false'},
			                   {xtype:'hidden', name :'run.prompt' , value : 'true'},
			                   {xtype:'hidden', name :'p_reportCode' , value : reportCode},
			                   {xtype:'hidden', name :'p_closeAPIcon' , value : 'NO'},
			                   {xtype:'hidden', name :'p_pageNum' , value : '1'},
			                   {xtype:'hidden', name :'run.outputFormat' , value : 'HTML'}];
			
			for(var i = 0; i < hiddenItems.length; i++) { 
				Ext.getCmp('queryForm').add(hiddenItems[i]);  
			}
			Ext.getCmp('queryForm').doLayout();
		}
		if (type === '1') { 
			if (getQueryConditionsByJs(reportCode) != null) {
				Ext.getCmp('queryForm').add(getQueryConditionsByJs(reportCode));
			}
			Ext.getCmp('queryForm').doLayout();
		} else {
			Ext.Ajax.request({
				url : basepath + '/ReportCfgAction!getCfgItems.json',
				method : 'GET',
				params : {
					reportCode :  reportCode
				},
				scope : this,
				waitMsg : '正在保存数据,请等待...', // 显示读盘的动画效果，执行完成后效果消失
				success : function(res) {					
					var resData = Ext.util.JSON.decode(res.responseText);
					if (resData.json.count == 0) {
						return;
					}
					var fields = [];
					fields = resData.json.data;
					for(var i = 0; i < resData.json.count; i++) {
						Ext.getCmp('queryForm').add({
						    columnWidth : .5,
							layout : 'form',
							labelWidth : 120,
							items : [getQueryField(fields[i])]
						});
						Ext.getCmp('queryForm').doLayout();
						
						/**
						 * 添加默认条件 add 20141226
						 */
						if (fields[i].conditionType == '4') {
							if (fields[i].conditionDefault == 'ORG') {
//								if (roleCodes != null && roleCodes != "") {
//									var roleArrs = roleCodes.split('$');
//									for ( var j = 0; j < roleArrs.length; j++) {
//										if (roleArrs[j] == "R304" || roleArrs[j] == "R305" || roleArrs[j] == "R104" || roleArrs[j] == "R105" || //法金RM ARM 
//											roleArrs[j] == "R309" || roleArrs[j] == "R106" ) {//法金team head
								Ext.getCmp('queryForm').getForm().findField(fields[i].conditionField).setValue(__units);
//										}
//									}
//								}
							} else if (fields[i].conditionDefault == 'USER') {
								var roleCodes = __roleCodes;// 当前用户拥有的据角色编码
								if (roleCodes != null && roleCodes != "") {
									var roleArrs = roleCodes.split('$');
									for ( var j = 0; j < roleArrs.length; j++) {
										if (roleArrs[j] == "R304" || roleArrs[j] == "R305" || roleArrs[j] == "R104" || roleArrs[j] == "R105" || //法金RM ARM 
											roleArrs[j] == "R309" || roleArrs[j] == "R106" ) {//法金team head
											Ext.getCmp('queryForm').getForm().findField(fields[i].conditionField).setValue(__userId);
										}
									}
								}
							} else if (fields[i].conditionDefault == 'CUST') {
								
							}
						}
						if(fields[i].conditionType=='5' ){
							 if (fields[i].conditionField == 'bus_line') {
								 Ext.getCmp('queryForm').getForm().findField(fields[i].conditionField).readOnly=true;
								 var roleCodes = __roleCodes;// 当前用户拥有的据角色编码
								 if(roleCodes != null && roleCodes != ""){
									 var roleArrs = roleCodes.split('$');
									 for ( var j = 0; j < roleArrs.length; j++) {
											if (roleArrs[j] == "R126" ||  roleArrs[j]=='R311' || roleArrs[j]=='R112' || roleArrs[j]=='R121' || roleArrs[j]=='R110') {//总行行长,支行行长,法金营销管理部管理专员
												Ext.getCmp('queryForm').getForm().findField(fields[i].conditionField).readOnly=false;
											}
										}
								 }
									Ext.getCmp('queryForm').getForm().findField(fields[i].conditionField).setValue(__busiLine);
								}
						}
//						if (i+1 >= resData.json.count) {
//							Ext.getCmp('queryForm').add({
//						        layout : 'column',
//								border : false,
//								items : [{
//								    columnWidth : .5,
//									defaultType : 'textfield',
//									layout : 'form',
//									labelWidth : 80,
//									border : false,
//									hidden : getQueryField(fields[i]).hidden,
//									items : [getQueryField(fields[i])]
//								}]
//							}); 
//							break;
//						} else {
//							Ext.getCmp('queryForm').add({
//						        layout : 'column',
//								border : false,
//								items : [{
//								    columnWidth : .5,
//									defaultType : 'textfield',
//									layout : 'form',
//									labelWidth : 80,
//									hidden : getQueryField(fields[i]).hidden,
//									border : false,
//									items : [getQueryField(fields[i])]
//								},{
//								    columnWidth : .5,
//									defaultType : 'textfield',
//									layout : 'form',
//									labelWidth : 80,
//									border : false,
//									hidden : getQueryField(fields[i+1]).hidden,
//									items : [getQueryField(fields[i+1])]
//								}]
//							}); 
//						}
//						i++;
					} 
					Ext.getCmp('queryForm').doLayout();
				},
				failure : function() {
					Ext.Msg.alert('提示', '获取查询条件失败');
				}
			});			
		}
		
	};
	
	var reportQuery = function(type){
		//['0','Cognos'],['1','MicroStategy'],['2','BIEE'],['3','润乾服务器']]
		if (type == '0') {
			Ext.Ajax.request({
				url : basepath + '/ReportQueryAction!getReportServerPath.json',
				method : 'GET',
				scope : this,
				success : function(res) {	
					var resData = Ext.util.JSON.decode(res.responseText);
					document.forms[0].action = encodeURI(resData.reportServerPath + "?ui.object="+reportUrl);
					document.forms[0].target = "winOpen" + new Date();
					document.forms[0].submit();
				},
				failure : function() {
					Ext.Msg.alert('提示', '获取报表服务路径失败。');
				}
			});	
		} else if (type == '1'){
			var object = Ext.getCmp('queryForm').getForm().getValues(false);
			var conditonsStr = '';
			for (var key in object) { 
				//只遍历本地属性 
				if (object.hasOwnProperty(key)){
					conditonsStr += object[key] + '^'; 
				}
			} 
			conditonsStr = conditonsStr.substring(0, conditonsStr.length-1); 
			conditonsStr = '&valuePromptAnswers='+conditonsStr+'&promptAnswerMode=1';
			Ext.Ajax.request({
				url : basepath + '/ReportQueryAction!getReportServerPath.json',
				method : 'GET',
				scope : this,
				success : function(res) {	
					var resData = Ext.util.JSON.decode(res.responseText);
					var reportWin = window.open("","","fullscreen=1,scrollbars=yes");
					reportWin.resizeTo(screen.width+20,screen.height);
					reportWin.focus();
					reportWin.location.href = encodeURI(resData.reportServerPath+ '?'+ reportUrl + conditonsStr);
				},
				failure : function() {
					Ext.Msg.alert('提示', '获取报表服务路径失败。');
				}
			});	
			
		} else if (type == '2') {
			
		} else if (type == '3') {
			//润乾报表
			var url = reportUrl;
			var condition = Ext.getCmp('queryForm').getForm().getValues(false);
			var winWidth = screen.width - 10;
			var winHeight = screen.height - 60;
			var winFeatures = "toolbar=no,location=no,directories=no,status=yes,menubar=no,scrollbars=yes,resizable=yes,";
			winFeatures +="top=0,left=0,height="
				+winHeight +",width=" +winWidth;
			var url =basepath+'/reportJsp/showReport.jsp?raq='+reportUrl+'&'+Ext.urlEncode(condition);
			var winOpen = window.open(url,'chat'+new Date().getTime(),winFeatures); 
		} else {
			alert('报表报务器类型：[未知]');
		}
		
	};
	
	var reportQueryForm = new Ext.form.FormPanel({//查询Form
	    labelWidth : 90,
	    height : 50,
	    frame : true,
	    autoScroll : true,
	    layout : 'column',
	    region : 'center',
	    labelAlign : 'middle',
	    buttonAlign : 'center',
	    id : 'queryForm',
	    items : [],
		buttons : [{
			text : '查询',
			id : 'reportQueryBtn',
			disabled : true,
			handler : function() {
				if(!reportQueryForm.getForm().isValid()){
					Ext.Msg.alert('提示', '请输入必填信息项');
	                return ;
				}
				reportQuery(reportServer);
			}
		}, {
			text : '重置',
			handler : function() {// 重置方法，查询表单重置
				reportQueryForm.getForm().reset();
			}
		}]
	});
	
 	var loader = new Com.yucheng.bcrm.ArrayTreeLoader({
//		/**节点数组，可以改为从后台读取*/
//		nodeArray :nodeArra,

 		/**指向父节点的属性列*/
 		parentAttr : 'parentSection',
 		
 		/**节点定位属性列，也是父属性所指向的列*/
		locateAttr : 'sectionId',
		
		/**虚拟根节点id 若果select的值为root则为根节点*/
		rootValue : 'root',
		
		/**用于展示节点名称的属性列*/
		textField : 'sectionName',
		
		/**指定节点ID的属性列*/
		idProperties : 'sectionId'
			
			/**节点点击事件句柄*/
 	});

  //tree的形状
    var leftTreeForShow = new Com.yucheng.bcrm.TreePanel({
		title:'',
		id:'blocMemberTree',
		width : 210,
		autoScroll:true,
		//rootVisible : false,
		/**虚拟树形根节点*/
		root: new Ext.tree.AsyncTreeNode({
			id:'root',
			expanded:true,
			text:'报表查询',
			autoScroll:true,
			children:[]
		}),
		resloader:loader,
		region:'west',
		collapsible: true,
		split:true,
		clickFn:function(node){
	    	if(node.attributes.isAble==0 || node.attributes.id=='root'){
				Ext.getCmp('reportQueryBtn').setDisabled(true);
	    	}else{
	    		reportUrl = node.attributes.reportUrl;
	    		reportServer = node.attributes.reportServer;
	    		Ext.getCmp('reportQueryBtn').setDisabled(false);
				getQueryConditions(node.attributes.reportType, node.attributes.reportCode);
	    	}
    	}
	});
    
    var arrLst = new Array();
	
	Ext.Ajax.request({
			url :basepath+'/lookup.json?name=reportGroup',
			method:'GET',
			success:function(response){
				var res = Ext.util.JSON.decode(response.responseText);	
				for(var i=0;i<res.JSON.length;i++){
					var children = {
							id:res.JSON[i].key,
							sectionId:res.JSON[i].key,
							parentSection:'root',
							sectionName:res.JSON[i].value,
							sectionSummary:'',
							sectionDistribute:"",
							isAble:0
						};
					arrLst.push(children);
				}
				ajaxAgain();
			}
		});
	
    function ajaxAgain(){
    	Ext.Ajax.request({
    		url :basepath + '/ReportQueryAction.json',
    		method:'GET',
    		success:function(response){
    		var res1 = Ext.util.JSON.decode(response.responseText);	
    		for(var i=0;i<res1.json.data.length;i++){
    			if(res1.json.data[i].REPORT_GROUP=="")
    				var a='root';
    			else 
    				var a=res1.json.data[i].REPORT_GROUP;
    			var children1 = {
    					id:res1.json.data[i].ID,
    					sectionId:res1.json.data[i].ID,
    					parentSection:a,
    					sectionName:res1.json.data[i].REPORT_NAME,
    					reportCode: res1.json.data[i].REPORT_CODE,
    					reportType:res1.json.data[i].REPORT_TYPE,
    					reportUrl:res1.json.data[i].REPORT_URL,
    					reportServer:res1.json.data[i].REPORT_SERVER_TYPE,
    					sectionSummary:'',
    					sectionDistribute:"",
    					isAble:1
					
					};
    			arrLst.push(children1);
    		}
    		loader.nodeArray =arrLst;
    		var children = loader.loadAll();
    		leftTreeForShow.appendChild(children);
		
			}
    	});
    }
	
	var viewport = new Ext.Viewport({//整体显示布局
	    layout : 'fit',
	    items:[{
    	        layout : 'border',
    	        title : '报表查询',
    	        items : [leftTreeForShow, reportQueryForm]
	    }]
	});
});