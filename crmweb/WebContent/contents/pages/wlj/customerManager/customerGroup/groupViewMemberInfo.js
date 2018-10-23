/**
 * 客户群成员信息
 * hujun
 * 2014-07-19
 * */
   imports([
     '/contents/pages/com.yucheng.bcrm/com.yucheng.bcrm.js'
     //'/contents/pages/wlj/customerManager/agileQueryNew.js'
     ]);
	var createView=false;
	var editView=false;
	var detailView=false;
	var groupId=_busiId;
	var groupMemberType=false;//客户群成员类型
	var custFrom=false;//客户来源
	var children='';
	var groupName=false;//客户群名称
	var lookupTypes=[
			'CUSTOMER_GROUP_TYPE',
			'GROUP_MEMEBER_TYPE',
			'SHARE_FLAG',
			'CUSTOMER_SOURCE_TYPE'
	                 
	                 ];
	WLJUTIL.suspendViews=false;  //自定义面板是否浮动
	var fields=[{name:'text',name:'TEST',hidden:true}];
	var needGrid=false;
	 
		var groupRecord = Ext.data.Record.create( [ {
			name : 'GROUP_MEMBER_TYPE',
			mapping : 'GROUP_MEMBER_TYPE'
		}, {
			name : 'CUST_FROM',
			mapping : 'CUST_FROM'
		} ]);
		
		//查询客户群信息
		var groupStore = new Ext.data.Store( {
			restful : true,
			proxy : new Ext.data.HttpProxy( {
				url : basepath + '/customergroupinfo!queryGroupInfo.json'
			}),
			reader : new Ext.data.JsonReader( {
				successProperty : 'success',
				messageProperty : 'message',
				root : 'data',
				totalProperty : 'count'
			}, groupRecord)
		});
		groupStore.on('beforeload', function() {
			this.baseParams = {
			groupId: groupId
		};
		});
		var loader1 = new Com.yucheng.bcrm.ArrayTreeLoader({
			parentAttr : 'PARENT_ID',
			locateAttr : 'NODEID',
			rootValue : '0',
			textField : 'NAME',
			idProperties : 'NODEID'
		});
		Ext.Ajax.request({
			url : basepath + '/queryagilequery.json',
			method : 'GET',
			success : function(response) {
				var nodeArra = Ext.util.JSON.decode(response.responseText);
				loader1.nodeArray = nodeArra.JSON.data;
				nodeArrays=nodeArra.JSON.data;
				for ( var item in loader1.nodeArray) {
					if (typeof loader1.nodeArray[item] === 'object') {
						if (loader1.nodeArray[item].TABLES == '2')
							loader1.nodeArray[item].NODEID = 'b' + loader1.nodeArray[item].NODEID;
					}
				}
				 children = loader1.loadAll();
				 treeOfPoroduct1.appendChild(children);
			}
		});
		
		 Ext.override(Ext.tree.TreeNode, {  
		     deepExpand : function(anim, callback, scope){  
		        // 先展开本节点  
		         this.expand(false, anim, function(){ 
		            // 然后展开子节点  
		        	 
		             var cs = treeOfPoroduct1.root.childNodes,   
		                 expanded = 0,  
		                len = cs.length,  
		                 taskDone = function(){  
		                     // 每展开成功一个子节点，计数+1  
		                     expanded++; 
		                     // 如果所有子节点都展开，调用最终回调  
		                     if(expanded >= len){  
		                         this.runCallback(callback, scope || this, [this]);  
		                     }  
		                 };  
						 if(len<=0){  
						     taskDone.call(this);    
						     return;  
						 } 

		            // 递归展开  
		             for(var i = 0, len = cs.length; i < len; i++) {  
		                 cs[i].deepExpand(anim, taskDone, this);  
		             }  
		         }, this);  
		     }  
		 }); 
		var treeOfPoroduct1 = new Com.yucheng.bcrm.TreePanel({
			title : '条件字段',
//			width : 350,
			height : 490,
			autoScroll : true,
			rootVisible : false,
			ddGroup : 'rightPanel1',
			split : true,
			nodeCount:0,
			enableDrag:true,
			/** 虚拟树形根节点 */
			root : new Ext.tree.AsyncTreeNode({
				id : 'root',
				expanded : true,
				text : '客户视图',
				autoScroll : true,
				children : []
			}),
			resloader : loader1
		});
		Ext.ns('Com.yucheng.crm.query');

		/**
		 * 查询条件常量
		 */
		Com.yucheng.crm.query.Util = {
				optypes :{
					INCLUDE : [['等于', '0002'],['包含', '0000']],
					COMPARE : [['大于', '0001'], ['等于', '0002'], ['小于', '0003'], ['大于等于', '0004'], ['小于等于', '0005']],
					EQUAL : [['等于', '0002']],
					ALL : [['包含', '0000'],['大于', '0001'], ['等于', '0002'], ['小于', '0003'], ['大于等于', '0004'], ['小于等于', '0005']]
				},
				types : {
					VARCHAR2:'QUERYUTIL.optypes.INCLUDE',
					DATE:'QUERYUTIL.optypes.COMPARE',
					NUMBER:'QUERYUTIL.optypes.COMPARE',
					DECIMAL:'QUERYUTIL.optypes.COMPARE',
					INTEGER:'QUERYUTIL.optypes.COMPARE',
					VARCHAR:'QUERYUTIL.optypes.INCLUDE',
					CHAR:'QUERYUTIL.optypes.INCLUDE',
					BIGINT:'QUERYUTIL.optypes.COMPARE'
				},
				orderTypes : new Ext.data.SimpleStore({
					fields : ['name', 'code'],
					data : [['不排序', '0'],['正序', '1'],['逆序', '2']]
				}),
				custBaseInfo : {
					dbTable :'ACRM_F_CI_CUSTOMER',
					idColumn : 'CUST_ID',
					nameColumn : 'CUST_NAME',
					typeColumn : 'CUST_TYPE',
					dbNode : false,
					idNode : false,
					nameNode : false,
					typeNode : false
				}
};
		QUERYUTIL = Com.yucheng.crm.query.Util;

		Com.yucheng.crm.query.QeuryPanel = Ext.extend(Ext.FormPanel, {
			height :400,
			labelAlign: 'top',
			bodyStyle:'padding:0px 0px 0px 5px',
			autoHeight : true,
			autoWidth : false
		});
		/**
		 * 查询条件面板类
		 */
		Com.yucheng.crm.query.SearchPanel = Ext.extend(Com.yucheng.crm.query.QeuryPanel, {
			
			conditions : new Array(),
			initComponent : function(){
				Com.yucheng.crm.query.SearchPanel.superclass.initComponent.call(this);
				this.add(new Ext.Panel({
					html:'<table><tr><td style= "text-align:center;width:80px;font-size:12px;">属性 </td><td style= "text-align:center;width:170px;font-size:12px;">操作符</td><td style= "text-align:center;width:170px;font-size:12px;">属性值</td><td></td></tr></table> '
				}));
			},
			/**
			 * 添加一个查询条件
			 * @param node:数据字段节点，从数据集树上获取
			 * @param op : 可选参数，查询条件操作符
			 * @param value : 可选参数，查询条件值
			 */
			addItems : function(node,op,value){
				
				for(var n=0;n<this.conditions.length;n++){
					if(this.conditions[n].nodeInfo === node){
						Ext.Msg.alert('提示','该列已选');
						return false;
					}
				}
				
				var si = new Com.yucheng.crm.query.SearchItem({
					nodeInfo:node,
					oprater :op,
					conditionValue : value
				});
				this.conditions.push(si);
				this.add(si);
				this.doLayout();
			},
			/**
			 * 移除一个查询条件项
			 * @param item:查询条件面板
			 */
			removeItem : function(item){
				this.conditions.remove(item);
				this.remove(item);
			},
			/**
			 * 移除所有查询条件
			 */
			removeAllItems : function(){
				var _this = this;
				while(_this.conditions.length>0){
					_this.removeItem(_this.conditions[0]);
				}
			},
			/**
			 * 获取查询条件
			 */
			getConditionsAttrs : function(){
				var _this = this;
				var conditions = new Array();
				Ext.each(_this.conditions, function(con){
					var conAtt = {};
					conAtt.ss_col_item = con.columnId.substring(1);
					conAtt.ss_col_op = con.oprater;
					conAtt.ss_col_value = con.conditionValue;
					conditions.push(conAtt);
				});
				return conditions;
			}
		});
		/**
		 * 查询条件项面板
		 */
		Com.yucheng.crm.query.SearchItem = Ext.extend(Ext.Panel,{
			nodeInfo : false, //关联数据字段节点
			oprater: null,	//条件操作符
			conditionValue:null,//条件值
			
			valueStore : false,
			/**
			 * 对象构建方法，初始化面板各数据属性，根据字段类型以及字典编码，创建操作符、字段值下拉框数据源（store）
			 */
			initComponent : function(){
		    	if(!this.nodeInfo){
		    		return false;
		    	}
		    	this.textName = this.nodeInfo.text;
		    	this.columnId = this.nodeInfo.id;
		    	this.columnName = this.nodeInfo.attributes.ENAME;
		    	this.columnType  = this.nodeInfo.attributes.CTYPE;
		    	this.datasetId = this.nodeInfo.attributes.PARENT_ID;
		    	this.opstore = new Ext.data.SimpleStore({
		    		fields : ['name', 'code']
		    	});
		    	this.opstore.loadData(this.nodeInfo.attributes.NOTES?QUERYUTIL.optypes.EQUAL:eval(QUERYUTIL.types[this.columnType]));
		    	if(this.nodeInfo.attributes.NOTES){
		    		this.valueStore = new Ext.data.Store({
		    			restful:true,
		    			proxy : new Ext.data.HttpProxy({
		    				url :basepath+'/lookup.json?name='+this.nodeInfo.attributes.NOTES
		    			}),
		    			reader : new Ext.data.JsonReader({
		    				root : 'JSON'
		    			}, [ 'key', 'value' ])
		    		});
		    	}
		    	
		    	Com.yucheng.crm.query.SearchItem.superclass.initComponent.call(this);
			},
			/**
			 * 销毁方法，对象销毁时触发，销毁对象所创建的数据源
			 */
			onDestroy :function(){
				var _this = this;
				if(_this.valueStore)
					_this.valueStore.destroy();
				if(_this.opstore){
					_this.opstore.destroy();
				}
				Com.yucheng.crm.query.SearchItem.superclass.onDestroy.call(this);
			},
			/**
			 * 渲染方法，对象渲染时（首次展示）触发，创建字段名、操作符、条件值展示框。
			 */
			onRender : function(ct, position){
				
				var _this = this;
				_this.nameField = new Ext.form.DisplayField({
					emptyText : '',
					editable : false,
					triggerAction : 'all',
					allowBlank : false,
					hideLabel:true,
					xtype : 'displayfield',
					name : 'attributeName_' + id,
					anchor : '95%',
					value : _this.textName
				});
				_this.opField = new Ext.form.ComboBox({
					hiddenName:'operateName_'+id,
					hideLabel:true,
					allowBlank : false,
					labelStyle: 'text-align:right;',
					triggerAction : 'all',
					store : _this.opstore,
					displayField : 'name',
					valueField : 'code',
					mode : 'local',
					forceSelection : true,
					typeAhead : true,
					emptyText:'请选择',
					resizable : true,
					anchor : '95%',
					value : _this.oprater,
					listeners : {
						select : function(combo,record,index){
							_this.oprater = record.data.code;
						}
					}
				});
				if(_this.nodeInfo.attributes.CTYPE == 'DATE'){
					_this.valueField = new Ext.form.DateField({
						allowBlank : false,
						hideLabel:true,
						labelStyle: 'text-align:right;',
						format:'Y-m-d', //日期格式化
						name : 'attributeValueName_' + id,
						anchor:'95%',
						value : _this.conditionValue,
						listeners : {
							select : function(combo,date){
								_this.conditionValue = date.format('Y-m-d');
							}
						}
					});
				} else if (_this.valueStore){
					_this.valueField = new Ext.form.ComboBox({
						hiddenName:'operateName_'+id,
						hideLabel:true,
						allowBlank : false,
						labelStyle: 'text-align:right;',
						triggerAction : 'all',
						store : _this.valueStore,
						displayField : 'value',
						valueField : 'key',
						mode : 'local',
						forceSelection : true,
						typeAhead : true,
						emptyText:'请选择',
						resizable : true,
						anchor : '95%',
						listeners : {
							select : function(combo,record,index){
								_this.conditionValue = record.data.key;
							}
						}
					});
					_this.valueStore.load({callback:function(){
						_this.valueField.setValue( _this.conditionValue);
					}});
				} else {
					_this.valueField = new Ext.form.TextField({
						emptyText : '',
						editable : false,
						triggerAction : 'all',
						allowBlank : false,
						hideLabel : true,
						name : 'attributeValueName_' + id,
						labelStyle : 'text-align:right;',
						xtype : 'textfield',
						anchor : '95%',
						value : _this.conditionValue,
						listeners : {
							change : function(field,newValue,oldValue){
								_this.conditionValue = field.getValue();
							}
						}
					});
				}
				this.add(new Ext.Panel({
					items : [{
						layout : 'column',
						border : false,
						items : [{
							columnWidth : .15,
							layout : 'form',
							border : false,
							items : [_this.nameField]
						}, {
							columnWidth : .30,
							layout : 'form',
							border : false,
							items : [_this.opField]
						}, {
							columnWidth : .30,
							layout : 'form',
							border : false,
							items : [_this.valueField]
						}, {
							columnWidth : .021,
							layout : 'form',
							border : false,
							items : []
						}, {
							columnWidth : .079,
							layout : 'form',
							border : false,
							items : [{ 
								xtype: 'button', 
								text: '删除', 
								scope: this, 
								handler: function(){ 
									_this.ownerCt.removeItem(_this);
								} 
							}]
						}
					]}]
				}));
				
				Com.yucheng.crm.query.SearchItem.superclass.onRender.call(this, ct, position);
			}
		});

		/**
		 * 查询结果列面板
		 */
		Com.yucheng.crm.query.ColumnsPanel = Ext.extend(Com.yucheng.crm.query.QeuryPanel, {
			
			resultColumns : new Array(),	//查询结果面板数组
			selectModel : new Ext.grid.CheckboxSelectionModel(),//公用复选框
		    resultNumber : new Ext.grid.RowNumberer({	//数据行号框
		        header : 'No.',
		        width : 35
		    }),
		    /**
		     * 构造方法，创建面板头
		     */
		    initComponent:function(){
				Com.yucheng.crm.query.ColumnsPanel.superclass.initComponent.call(this);
				this.add(new Ext.Panel({
					layout : 'column',
					border : false,
					items : [{
						html:'<table><tr><td style= "text-align:center;width:80px;font-size:12px;">名称 </td><td style= "text-align:center;width:170px;font-size:12px;">排序方式</td><td></td></tr></table> '
					}]
				}));
			},
			onRender : function(ct, position){
				Com.yucheng.crm.query.ColumnsPanel.superclass.onRender.call(this, ct, position);
			},
			
			/**
			 * 添加一个结果列
			 * @param node:字段节点对象；
			 * @param sortType:排序属性；
			 * @param hidden:是否隐藏字段；
			 * @param override:只有当值为：false时，不会改变hidden属性；
			 */
			addItems : function(node,sortType,hidden,override){
				var _this = this;
				for(var n=0;n<this.resultColumns.length;n++){
					if(this.resultColumns[n].nodeInfo === node){
						
						if(override === false){
							this.resultColumns[n].columnTotle = 'BASE';
						}else if(override !== false){
							this.resultColumns[n].show();
						}
						return false;
					}
				}
				var tSort = 0;
				if(sortType){
					tSort = sortType;
				}
				
				var columnTotle = 0;
				
				if( node == QUERYUTIL.custBaseInfo.idNode 
						|| node == QUERYUTIL.custBaseInfo.nameNode 
						|| node == QUERYUTIL.custBaseInfo.typeNode ){
					columnTotle = "BASE";
				}else {
					Ext.each(_this.resultColumns,function(rc){
						if(rc.nodeInfo.attributes.ENAME == node.attributes.ENAME){
							columnTotle ++;
						}
					});
				}
				var ci = new Com.yucheng.crm.query.ColumnsItem({
					nodeInfo:node,
					sortType:tSort,
					hidden:hidden,
					columnTotle:columnTotle
				});
				this.resultColumns.push(ci);
				this.add(ci);
				this.doLayout();
			},
			/**
			 * 移除一个结果字段面板
			 */
			removeItem : function(columnItem){
				this.resultColumns.remove(columnItem);
				this.remove(columnItem);
			},
			/**
			 * 移除所有结果字段
			 */
			removeAllItems : function(){
				var _this = this;
				while(_this.resultColumns.length>0){
					_this.removeItem(_this.resultColumns[0]);
				}
			},
			/**
			 * 获取查询结果字段属性，包括字段ID，排序类型，以及别名后缀，供后台使用
			 */
			getResults : function(){
				var _this = this;
				var rsults = [];
				Ext.each(_this.resultColumns,function(column){
					var r = {};
					r.columnId = column.nodeId.substring(1);
					r.sortType = column.sortType;
					r.columnTotle = column.columnTotle;
					rsults.push(r);
				});
				return rsults;
			},
			/**
			 * 获取查询结果字段ID拼串，保存查询方案时调用
			 */
			getResultsIds : function(){
				var resuldIds = [];
				var _this = this;
				Ext.each(_this.resultColumns,function(column){
					resuldIds.push(column.nodeId.substring(1));
				});
				return resuldIds.join(',');
			},
			/**
			 * 获取排序类型拼串，保存查询方案时调用
			 */
			getSortTypes : function(){
				var resuldIds = [];
				var _this = this;
				Ext.each(_this.resultColumns,function(column){
					resuldIds.push(column.sortType);
				});
				return resuldIds.join(',');
			},
			/**
			 * 获取查询结果reader属性，查询结果时调用；
			 * 有字典编码属性的字段讲生成两个字段。
			 */
			getResultReaderMetas : function(){
				var readerMetas = {
						successProperty: 'success',
						messageProperty: 'message',
						idProperty: 'CUST_ID',
						root:'json.data',
						totalProperty: 'json.count'
				};
				var readerFields = [];
				Ext.each(simple12.resultColumns,function(column){
					var t = {};
					t.name = column.columnName+'_'+column.columnTotle;
					readerFields.push(t);
					if(column.columnLookup){
						var t2 = {};
						t2.name = column.columnName+'_'+column.columnTotle+'_ORA';
						readerFields.push(t2);
					}
				});
				readerMetas.fields = readerFields;
				return readerMetas;
			},
			/**
			 * 获取查询结果列模型，查询结果时调用；
			 * 1、有字典编码属性的字段将生成两个字段；
			 * 2、隐藏字段将也包含在内。
			 */
			getResultColumnHeaders : function(){
				var _this = this;
				var columnHeaders = [];
				columnHeaders.push(_this.resultNumber);
				columnHeaders.push(_this.selectModel);
				Ext.each(simple12.resultColumns,function(column){
					
					var columnHead = {};
					
					columnHead.header = column.textName;
					columnHead.hidden = column.hidden;
					columnHead.hideable  = !column.hidden;
					columnHead.dataIndex = column.columnName+'_'+column.columnTotle;
					columnHeaders.push(columnHead);
					
					if(column.columnLookup){
						var columnHeadLooked = {};
						columnHeadLooked.header = column.textName;
						columnHeadLooked.hidden = column.hidden;
						columnHeadLooked.hideable  = !column.hidden;
						columnHeadLooked.dataIndex = column.columnName+'_'+column.columnTotle+'_ORA';
						columnHeaders.push(columnHeadLooked);
						columnHead.hidden = true;
						columnHead.hideable = false;
					}
				});
				return columnHeaders;
			},
			/**
			 *  获取可分组字段数据，排除隐藏字段，分组统计时调用
			 */
			getResultColumnHeaderByNodeId : function(id){
				var _this = this;
				var columnHeaders = [];
				for(var i=0;i<_this.resultColumns.length;i++){
					if(_this.resultColumns[i].nodeId == id){
						var header = {};
						header.header = _this.resultColumns[i].textName;
						if(_this.resultColumns[i].columnLookup){
							header.dataIndex = _this.resultColumns[i].columnName+'_'+_this.resultColumns[i].columnTotle+'_ORA';
						}else{
							header.dataIndex = _this.resultColumns[i].columnName+'_'+_this.resultColumns[i].columnTotle;
						}
						return header;
					}
				}
				return;
			},
			/**
			 * 获取可统计字段，排除隐藏字段，分组统计时调用
			 */
			getSumColumnsByNodeIds : function(ids){
				var _this = this;
				var sums = [];
				for(var i=0;i<_this.resultColumns.length;i++){
					if(ids.indexOf(_this.resultColumns[i].nodeId)>=0){
						var header = {};
						header.dataIndex = _this.resultColumns[i].columnName+'_'+_this.resultColumns[i].columnTotle+'_SUM';
						header.header = _this.resultColumns[i].textName+'(统计)';
						sums.push(header);
					}
				}
				return sums;
			},
			/**
			 * 获取当前展示列数
			 */
			viewedColumnsCount : function(){
				var _this = this;
				var count = 0;
				Ext.each(_this.resultColumns,function(column){
					if(!column.hidden){
						count ++ ;
					}
				});
				return count;
			}
		});

		/**
		 * 查询结果列面板
		 */
		Com.yucheng.crm.query.ColumnsItem =  Ext.extend(Ext.Panel,{
			nodeInfo : false,//字段节点
			sortType : 0,//排序类型
			/**
			 * 构造方法，初始化面板各项数据信息
			 */
			initComponent : function(){
			
				if(!this.nodeInfo){
					return false;
				}
				this.textName = this.nodeInfo.text;
				this.nodeId = this.nodeInfo.id;
				this.columnName = this.nodeInfo.attributes.ENAME;
				this.columnType = this.nodeInfo.attributes.CTYPE;
				this.datasetId = this.nodeInfo.attributes.PARENT_ID;
				this.columnLookup = this.nodeInfo.attributes.NOTES;
				Com.yucheng.crm.query.ColumnsItem.superclass.initComponent.call(this);
			},
			
			/**
			 * 渲染方法，面板渲染时调用，构建结果列名、排序下拉框
			 */
			onRender : function(ct, position){
				
				var _this = this;
				_this.columnField = new Ext.form.DisplayField({
					emptyText : '',
					editable : false,
					triggerAction : 'all',
					allowBlank : false,
					hideLabel:true,
					xtype : 'displayfield',
					anchor : '95%',
					value : _this.textName
				});
				
				_this.orderField =  new Ext.form.ComboBox({
					hideLabel : true,
					labelStyle : 'text-align:right;',
					triggerAction : 'all',
					store : QUERYUTIL.orderTypes,
					value:_this.sortType,
					displayField : 'name',
					valueField : 'code',
					mode : 'local',
					forceSelection : true,
					typeAhead : true,
					emptyText : '请选择',
					resizable : true,
					anchor : '95%',
					listeners : {
						select : function(combo,record,index){
							_this.sortType = record.data.code;
						}
					}
				}) ;
				
				this.add(new Ext.Panel({
					items : [{
						layout : 'column',
						border : false,
						items : [{
							columnWidth : .15,
							layout : 'form',
							labelWidth : 60,
							border : false,
							items : [_this.columnField]
						}, {
							columnWidth : .30,
							layout : 'form',
							labelWidth : 60,
							border : false,
							items : [_this.orderField]
						}, {
							columnWidth : .30,
							layout : 'form',
							labelWidth : 60,
							border : false,
							items : []
						}, {
							columnWidth : .021,
							layout : 'form',
							labelWidth : 100,
							border : false,
							items : []
						}, {
							columnWidth : .079,
							layout : 'form',
							labelWidth : 100,
							border : false,
							items : [{ 
								xtype: 'button', 
								text: '删除', 
								scope: this, 
								handler: function(){ 
									_this.ownerCt.removeItem(_this);
								} 
							}]
						}]
					}]
				}));
				Com.yucheng.crm.query.ColumnsItem.superclass.onRender.call(this, ct, position);
			}
		});
		
			//查询条件对象
			var simple1 = new Com.yucheng.crm.query.SearchPanel({
				title : '查询条件',
				listeners:{
					"activate":function(){
						this.doLayout();
					}
				}
			});
			//结果列对象
			var simple12 = new Com.yucheng.crm.query.ColumnsPanel({
				title : '显示列',
				hidden:true,
				listeners:{
					"activate":function(){
						this.doLayout();
					}
				}
			});
			
			var tabmain1 = new Ext.TabPanel({
				autoScroll : true,
				id : 'tabmain1',
				height : 350,
				activeTab : 0,
				frame : true,
				defaults : {
					autoHeight : true
				},
				items : [simple1/*,simple12*/]
			});
			//条件链接符面板
			var radio1 = new Ext.Panel({
				layout : 'column',
				border : false,
				items : [ {
					columnWidth : .09,
					layout : 'form',
					labelWidth : 8,
					border : false,
					items : [ new Ext.form.Radio({
						boxLabel : "与",
						labelStyle: 'text-align:right;',
						id : "Radio11",
						name : "a",
						checked : true,
						listeners : {
							check : function(r,v){
								if(v)
									right_panel1.conditionJoinType = 'true';
								else
									right_panel1.conditionJoinType = 'false';
							}
						}
					})]
				}, {
					columnWidth : .09,
					layout : 'form',
					labelWidth :8,
					border : false,
					items : [ new Ext.form.Radio({
						boxLabel : "或",
						labelStyle: 'text-align:right;',
						id : "Radio12",
						name : "a"
					}) ]
				} ]
			});
			
			//保存方案
			var fnBatchSave1= function(){
				var solutionAttr = {};
				solutionAttr.ss_results = simple12.getResultsIds();
				solutionAttr.ss_sort = simple12.getSortTypes();
				var conditions = simple1.getConditionsAttrs();
				Ext.Ajax.request({
					url:basepath+'/agilesearch!create1.json',
					success : function(response) {
						Ext.Msg.alert('提示', '操作成功');
						ifadd = 'false';
					},
					failure : function(response) {
						var resultArray = Ext.util.JSON.decode(response.status);
						if(resultArray == 403) {
							Ext.Msg.alert('提示','您没有此权限!');
						} else {
							Ext.Msg.alert('提示','操作失败!');
						}
					},
					params : {
						solutionAttr:Ext.encode(solutionAttr),
						conditionCols : Ext.encode(conditions),
						group_id:groupId,
						flag:ifadd,
						group_name:groupName,
						'radio':right_panel1.conditionJoinType
					}
				});
			};
			
			//查询结果数据源
			var getCustStore = new Ext.data.Store({
				restful : true,
				url:basepath+'/queryagileresult.json',
				reader:new Ext.data.JsonReader({
					successProperty: 'success',
					messageProperty: 'message',
					fields : [{
						name:'CUST_ID'
					}]
				})
			});
			
			/**
		 * 查询结果分页
		 */ 
		var getCustsize_combo = new Ext.form.ComboBox({
			  	name : 'pagesize',
			  	triggerAction : 'all',
			  	mode : 'local',
			  	store : new Ext.data.ArrayStore({
			  				fields : ['value', 'text'],
			  				data : [ 
			  						[ 20, '20条/页' ],[ 50, '50条/页' ],[ 100, '100条/页' ], [ 250, '250条/页' ],
			  						[ 500, '500条/页' ] ]
			  			}),
			  	valueField : 'value',
			  	displayField : 'text',
			  	value : '20',
			  	editable : false,
			  	width : 85,
			  	listeners : {
					select : function(combo){
						custBar.pageSize = parseInt(getCustsize_combo.getValue());
						getCustStore.reload({
							params : {
								groupMemberType:groupMemberType,
								start : 0,
								limit : parseInt(getCustsize_combo.getValue())
							}
						});
					}
				}
			});
			
		//查询结果列模型
		var getCustCm = new Ext.grid.ColumnModel(simple12.getResultColumnHeaders());
		getCustCm.on("configchange",function(){
			getCustGrid.view.refresh(true);
		}); 

		//分页工具栏
			var custBar = new Ext.PagingToolbar({
				pageSize : getCustsize_combo.getValue(),
				store : getCustStore,
				displayInfo : true,
				displayMsg : '显示{0}条到{1}条,共{2}条',
				//plugins : new Ext.ux.ProgressBarPager(), // 分页进度条
				emptyMsg : "没有符合条件的记录",
				items : ['-', '&nbsp;&nbsp;', getCustsize_combo]
			});
			
		//查询结果列表面板
			var getCustGrid = new Ext.grid.GridPanel({
				frame : true,
				region : 'center', // 返回给页面的div
				store : getCustStore, // 数据存储
				stripeRows : true, // 斑马线
				cm : getCustCm, // 列模型
				sm : simple12.selectModel,
				bbar : custBar,// 分页工具栏
				viewConfig : {
				},
				loadMask : {
					msg : '正在加载表格数据,请稍等...'
				}
			});


		//查询结果窗口
			var resultWindow = new Ext.Window({
				layout : 'fit',
				maximized : true,
				closable : true,// 是否可关闭
		        title : '查询结果展示',
				modal : true,
				closeAction : 'hide',
				titleCollapse : true,
				buttonAlign : 'center',
				border : false,
				animCollapse : true,
				animateTarget : Ext.getBody(),
				constrain : true,
				items : [{
					layout:'border',
					items:[
						getCustGrid
					]
				}],
				buttons : [{
					text : '关闭',
					handler : function() {
						resultWindow.hide();
						getCustStore.removeAll();
					}
				}]
			});
			
			function getResultReaderMetas1(){
				var readerMetas = {
						successProperty: 'success',
						messageProperty: 'message',
						idProperty: 'CUST_ID',
						root:'json.data',
						totalProperty: 'json.count'
				};
				var readerFields = [];
				Ext.each(simple12.resultColumns,function(column){
					var t = {};
					t.name = column.columnName+'_'+column.columnTotle;
					readerFields.push(t);
					if(column.columnLookup){
						var t2 = {};
						t2.name = column.columnName+'_'+column.columnTotle+'_ORA';
						readerFields.push(t2);
					}
				});
				readerMetas.fields = readerFields;
				return readerMetas;
			
			};
			
			function getResultColumnHeaders1() {
				var columnHeaders = [];
				Ext.each(simple12.resultColumns,function(column){
					
					var columnHead = {};
					
					columnHead.header = column.textName;
					columnHead.hidden = column.hidden;
					columnHead.hideable  = !column.hidden;
					columnHead.dataIndex = column.columnName+'_'+column.columnTotle;
					columnHeaders.push(columnHead);
					
					if(column.columnLookup){
						var columnHeadLooked = {};
						columnHeadLooked.header = column.textName;
						columnHeadLooked.hidden = column.hidden;
						columnHeadLooked.hideable  = !column.hidden;
						columnHeadLooked.dataIndex = column.columnName+'_'+column.columnTotle+'_ORA';
						columnHeaders.push(columnHeadLooked);
						columnHead.hidden = true;
						columnHead.hideable = false;
					}
				});
				return columnHeaders;
			};
			
			//查询结果方法
			var fnSearchResult = function(){
				if(simple1.conditions.length==0){
					Ext.Msg.alert('提示', '未加入任何条件列！');
					return;
				}
				if(!simple1.getForm().isValid()){
					Ext.Msg.alert('提示', '查询条件输入有误！');
					return;
				}
				debugger;
				
				//初始化客户ID、客户名称、证件类型，证件号码，客户类型字段节点，从数据集树上获取
				if(QUERYUTIL.custBaseInfo.dbNode == false){
					QUERYUTIL.custBaseInfo.dbNode = treeOfPoroduct1.root.findChild('VALUE',QUERYUTIL.custBaseInfo.dbTable,true);
					QUERYUTIL.custBaseInfo.idNode = QUERYUTIL.custBaseInfo.dbNode.findChild('ENAME',QUERYUTIL.custBaseInfo.idColumn);
					QUERYUTIL.custBaseInfo.nameNode = QUERYUTIL.custBaseInfo.dbNode.findChild('ENAME',QUERYUTIL.custBaseInfo.nameColumn);
					QUERYUTIL.custBaseInfo.typeNode = QUERYUTIL.custBaseInfo.dbNode.findChild('ENAME',QUERYUTIL.custBaseInfo.typeColumn);
				}
				//结果列添加客户ID、名称、类型字段
				simple12.addItems(QUERYUTIL.custBaseInfo.idNode,'0',false,false);
				simple12.addItems(QUERYUTIL.custBaseInfo.nameNode,'0',false,false);
				simple12.addItems(QUERYUTIL.custBaseInfo.typeNode,'0',false,false);
				//弹出窗口
				resultWindow.show();
				
				//根据查询结果列配置，修改查询结果数据源reader字段
				getCustGrid.store.reader.onMetaChange(getResultReaderMetas1());
				//根据查询结果列配置，修改查询结果列表面板列模型
				getCustGrid.colModel.setConfig(getResultColumnHeaders1(), false) ;			
				
				//获取动态查询所需要的各个数据参数
				getCustGrid.store.baseParams = {
					conditionAttrs : Ext.encode(simple1.getConditionsAttrs()),
					results :  Ext.encode(simple12.getResults()),
					radio : right_panel1.conditionJoinType
				};
				
				//查询数据
				getCustGrid.store.load({
					params: {
						groupMemberType:groupMemberType,
						start : 0,
						limit : getCustsize_combo.getValue()
					}
				});
			};
			
			//右侧面板
			var right_panel1 = new Ext.Panel({
				conditionJoinType : 'true',//条件连接符数据，根据radio1对象点选情况
				frame : true,
				autoScroll : true,
				items : [ tabmain1,radio1],
				title : '查询设置',
				buttonAlign : 'center',
				buttons : [ {
					text : '保存',
					handler : function() {
						
						if(simple1.conditions.length==0){
							Ext.Msg.alert('提示', '未加入任何条件列！');
							return;
						}
						if(!simple1.getForm().isValid()){
							Ext.Msg.alert('提示', '查询条件输入有误！');
							return;
						}
							fnBatchSave1();
					}
				},{
					text : '试查询',
					handler : function() {
						fnSearchResult();
					}
				},{
					text : '返回客户列表',
					handler : function() {
						win.hide();
						//查询数据
						custJustStore.load({
							params: {
								start : 0,
								limit : getCustsize_combo.getValue()
							}
						});
						
					}
				}]
			});
			right_panel1.on('afterrender',function(){
				/**数据字段拖拽代理*/
				new Ext.dd.DropTarget(right_panel1.body.dom, {
			    	ddGroup : 'rightPanel1',
			    	notifyDrop : function(ddSource, e, data) {
			    	if (!data.node.leaf) {
			    		return;
			    	}
			    	var changeFlag=false;
			    	if(tabmain1.activeTab==simple1){
			    		simple1.addItems(data.node);
			    		tabmain1.setActiveTab(1);	
			    		changeFlag = true;
			    	}
			    	simple12.addItems(data.node);
			    	if(changeFlag){
			    		tabmain1.setActiveTab(0);	
			    	}
			    	return true;
			    	}
			    });
			});
			
			
			 
		     	
		     	
		
		var custJustStore = new Ext.data.Store({
			restful:true,	
			proxy : new Ext.data.HttpProxy({
			url:basepath+'/getJustCust.json',
				failure : function(response) {
				var resultArray = Ext.util.JSON.decode(response.status);
				if(resultArray == 403) {
					Ext.Msg.alert('提示','您没有此权限!');
				} 
			}
			}),
			reader: new Ext.data.JsonReader({
				totalProperty : 'json.count',
				root:'json.data'
			}, [ 'CUST_ID','CUST_NAME','IDENT_TYPE_ORA','IDENT_NO','CUST_TYPE_ORA'])
		});
		    custJustStore.on('beforeload', function() {
			this.baseParams = {
					groupMemberType:groupMemberType,
					groupId: groupId
					
			  };
		    });
		
		// 每页显示条数下拉选择框
		var custJustpagesize_combo = new Ext.form.ComboBox({
			name : 'pagesize',
			triggerAction : 'all',
			mode : 'local',
			store : new Ext.data.ArrayStore({
									fields : ['value', 'text'],
									data : [ [ 10, '10条/页' ], [ 20, '20条/页' ], [ 50, '50条/页' ],
									         [ 100, '100条/页' ], [ 250, '250条/页' ],
									         [ 500, '500条/页' ] ]
			}),
			valueField : 'value',
			displayField : 'text',
			value : '20',
			editable : false,
			width : 85
		});
		var custJustnumber = parseInt(custJustpagesize_combo.getValue());
		custJustpagesize_combo.on("select", function(comboBox) {// 改变每页显示条数reload数据
			custJustBar.pageSize = parseInt(custJustpagesize_combo.getValue());
			custJustStore.reload({
				params : {
				start : 0,
				limit : parseInt(custJustpagesize_combo.getValue())
			}
			});
		});
		var custJustrownum = new Ext.grid.RowNumberer({
			header : 'No.',
			width : 35
		});

		// 定义列模型
		var custJustCm = new Ext.grid.ColumnModel([custJustrownum,
	        {header : '客户号', dataIndex : 'CUST_ID',sortable : true,width : 150}, 
	        {header : '客户名称', dataIndex : 'CUST_NAME',sortable : true,width : 150 }, 
	        {header : '证件类型',dataIndex : 'IDENT_TYPE_ORA',sortable : true,width : 150},
	        {header : '证件号码',dataIndex : 'IDENT_NO',sortable : true,width : 150}, 
	        {header : '客户类型',dataIndex : 'CUST_TYPE_ORA',sortable : true,width : 150}
	    ]);

		
		var custJustBar = new Ext.PagingToolbar({// 分页工具栏
			pageSize : custJustnumber,
			store : custJustStore,
			displayInfo : true,
			displayMsg : '显示{0}条到{1}条,共{2}条',
			emptyMsg : "没有符合条件的记录",
			items : ['-', '&nbsp;&nbsp;', custJustpagesize_combo]
		});
		
		var panelss=new Ext.Panel({
				
				layout : 'column',
		    	items : [ 
				{
		    		columnWidth : .25,
		    		layout : 'form',
		    		border : false,
		    		items : [ treeOfPoroduct1 ]
		    	}, {
		    		columnWidth : .7,
		    		layout : 'form',
		    		border : false,
		    		items : [ right_panel1 ]
		    	} ]	
		});
		var win=new Ext.Window({
			title:"筛选方案维护",
	        plain : true,
	        layout : 'fit',
	        width:900,
	        height:500,
	        autoScroll : true,
	        maximized : false,
	        draggable : true,
	        closable : true,
	        closeAction : 'hide',
	        modal : true,
	        titleCollapse : true,
	        loadMask : true,
	        border : false,
	        items:[panelss],
	        listeners:{
	        	show:function(){
	        	treeOfPoroduct1.expandAll();
	   			treeOfPoroduct1.on('expandnode',function(){
	   				this.nodeCount ++;
	   				if(this.nodeCount >= this.resloader.nodeArray.length)
		   				Ext.Ajax.request({
	   						url:basepath+'/queryagilequery!queryGroupCondition.json?groupId='+groupId,
	   						method: 'GET',
	   						success : function(response) {
	   							simple1.removeAllItems();
	   							simple12.removeAllItems();
	   							var conditionData = Ext.util.JSON.decode(response.responseText);
	   							var conditionArray=conditionData.JSON.data;
	   							if(conditionArray.length>0){
	   								ifadd = 'false';
	   								Ext.each(conditionArray,function(con){
	   									var id='b'+con.SS_COL_ITEM;
	   									var node=treeOfPoroduct1.root.findChild('id',id,true);
	   									if(node){
		   										simple1.addItems(node,con.SS_COL_OP,con.SS_COL_VALUE);
		   									}
	   								});
	   								if(conditionArray[0].SS_COL_JOIN=='true'){
	   									radio1.items.items[0].items.items[0].setValue(true);
	   									right_panel1.conditionJoinType = 'true';
	   								}else{
	   									radio1.items.items[1].items.items[0].setValue(true);
	   									right_panel1.conditionJoinType = 'false';
	   								}
	   							}else{
	   								ifadd = 'true';
	   							}
	   						},
	   						failure : function(response) {
	   							var resultArray = Ext.util.JSON.decode(response.status);
	   							if(resultArray == 403) {
	   								Ext.Msg.alert('提示','您没有此权限!');
	   							} else {
	   								Ext.Msg.alert('提示','操作失败!');
	   							}
	   						}
	   					});
	   			});	   		
	        	}
	        }
		});
		var agileQueryPanel1=new Ext.Panel({
			//width : document.body.scrollWidth,
			//height : document.body.scrollHeight-40,
			title:'匹配客户列表',
			autoHeight:true,
			items:[new Ext.grid.GridPanel({
 				frame : true,
				autoScroll : true,
				height:450,
				store : custJustStore, // 数据存储
				stripeRows : true, // 斑马线
				cm : custJustCm, // 列模型
				bbar : custJustBar,
		        tbar:[{
					text:'维护筛选方案',
					id:'shaixuan',
					handler : function() {
						win.show();
				}
				}],
		        viewConfig : {
				},
				loadMask : {
					msg : '正在加载表格数据,请稍等...'
				}
			}) ]
	});
		
		
		

				
	        var queryStr = '';
		 	  var batchAdd= function(){
			  var checkedNodes = customerInfoGrid.getSelectionModel().selections.items;
			  var json={'cust_id':[]};
			  var json1={'cust_zh_name':[]};
			  var json2={'mgr_id':[]};
			  var json3={'mgr_name':[]};
			  var json4={'institution':[]};
			  var json5={'institution_name':[]};
			  if(checkedNodes.length==0)
			  {
				  Ext.Msg.alert('提示', '未选择任何客户');
				  return false;
			  }
			  Ext.MessageBox.confirm('提示','确定将所选客户加入到该群吗?',function(buttonId){
				if(buttonId.toLowerCase() == "no"){
				return false;
				}else{
				for(var i=0;i<checkedNodes.length;i++)
			  {
				  json.cust_id.push(checkedNodes[i].data.CUST_ID);
				  json1.cust_zh_name.push(checkedNodes[i].data.CUST_NAME);
				  json2.mgr_id.push(checkedNodes[i].data.MGR_ID);
				  json3.mgr_name.push(checkedNodes[i].data.MGR_NAME);
				  json4.institution.push(checkedNodes[i].data.INSTITUTION);
				  json5.institution_name.push(checkedNodes[i].data.INSTITUTION_NAME);
			  }
			
			  Ext.Msg.wait('正在保存，请稍后......','系统提示');
			  Ext.Ajax.request({
				  url:basepath+'/groupmemberedit!saveData.json',
				  method: 'POST',
				  success : function(response) {
				  Ext.Msg.alert('提示', '加入成功');
				  groupLeaguerStore.reload({
			  		  params : {
					  start : 0,
					  limit : parseInt(groupLeaguerpagesize_combo.getValue())
					  }
					  });
				  customerInfoStore.reload({
					  params : {
					  start : 0,
					  limit : parseInt(customerInfopagesize_combo.getValue())
				      }
			});
			  },	
			  failure : function(response) {
				  var resultArray = Ext.util.JSON.decode(response.status);
				  if(resultArray == 403) {
					  Ext.Msg.alert('提示','您没有此权限!');
				  } else {
					  Ext.Msg.alert('提示','加入失败!');
				  }
			  },
			  params : {
				  'CUST_ID': Ext.encode(json),
				  'CUST_ZH_NAME': Ext.encode(json1),
				  'CUST_BASE_ID':groupId,
				  'MGR_ID':Ext.encode(json2),
				  'MGR_NAME':Ext.encode(json3),
				  'INSTITUTION':Ext.encode(json4),
				  'INSTITUTION_NAME':Ext.encode(json5)
			  }});
				}});
			  
		  };	
		  
		  //客户群成员删除功能
		   var batchDelete=function(){
			  var checkedNodes = groupLeaguerGrid.getSelectionModel().selections.items;
					if(checkedNodes.length==0)
					{
						Ext.Msg.alert('提示', '未选择任何客户');
						return ;
					}
					var json={'id':[]};
					for(var i=0;i<checkedNodes.length;i++)
					{
						json.id.push(checkedNodes[i].data.ID);
					}
					var id =checkedNodes[0].data.ID;
					Ext.Ajax.request({url: basepath+'/groupmemberedit!dropData.json',
						method: 'POST',
						success : function(response) {
						Ext.Msg.alert('提示', '删除成功');
						groupLeaguerStore.reload({
								params : {
								start : 0,
								limit : parseInt(groupLeaguerpagesize_combo.getValue())
							}
							});
					},
					failure : function(response) {
						var resultArray = Ext.util.JSON.decode(response.status);
						if(resultArray == 403) {
							Ext.Msg.alert('提示','您没有此权限!');
						} else {
							Ext.Msg.alert('提示','删除失败!');
						}
					},
					params : {
						'delStr':Ext.encode(json)
					}
					});
		  };
		  
		//新增客户群成员的表格面板 
		var customerInfoTar = new Ext.Toolbar({
			items:[{
			    text:'归入客户群',
			    iconCls:'guiRuIconCss',
			    handler:function(){
			        batchAdd();
			    }
			}]
		});	
		
		//客户类型
		var customerTypeStore = new Ext.data.Store({
			restful:true,   
			autoLoad :true,
			proxy : new Ext.data.HttpProxy({
					url :basepath+'/lookup.json?name=XD000080'
			}),
			reader : new Ext.data.JsonReader({
				root : 'JSON'
			}, [ 'key', 'value' ])
		});	
		var searchPanel1 = new Ext.form.FormPanel( {
			title : '待加入客户',
			region : 'north',
		    height : 120,
		    labelWidth : 60,
		    labelAlign : 'right',
		    frame : true,
		    autoScroll : true,
		    layout : 'column',
		    items : [ {
		    	id:'custZhName',
		        columnWidth : .33,
		        layout : 'form',
		        items : [{
		            xtype : 'textfield',
		            fieldLabel : '客户名称',
		            name : 'CUST_NAME',
		            anchor : '95%'
		        }]
		    }, {
		    	id:'custId',
		        columnWidth : .33,
		        layout : 'form',
		        items : [{
		            xtype : 'textfield',
		            fieldLabel : '客户编号',
		            name : 'CUST_ID',
		            anchor : '95%'
		        } ]
		    }, {
		        columnWidth : .33,
		        layout : 'form',
		        items : [new Ext.form.ComboBox({
					hiddenName : 'CUST_TYPE',
					name : 'CUST_TYPE',
					fieldLabel : '客户类型',
					labelStyle: 'text-align:right;',
					triggerAction : 'all',
					store : customerTypeStore,
					displayField : 'value',
					valueField : 'key',
					mode : 'local',
					hidden:false,
					forceSelection : true,
					typeAhead : true,
					emptyText:'请选择',
					resizable : true,
					anchor : '95%'
		        })]
		    }],
	        buttonAlign : 'center',
	        buttons : [ {
	            text : '查询',
	            handler : function() {
	            if (!searchPanel1.getForm().isValid()) {
	                Ext.Msg.alert("提醒", "请填写必填项");
	                return false;
	            }
	            queryStr = searchPanel1.getForm().getValues(false);
	            customerInfoStore.load( {
	                params : {
	                    start : 0,
	                    limit : parseInt(customerInfopagesize_combo.getValue())
	                }
	            });
	        }
	        }, {
	            text : '重置',
	            handler : function() {
	                searchPanel1.getForm().reset();
	            }
	        } ]
	    });
	        
		var custTypStore = new Ext.data.Store( {//客户类型代码
			restful : true,
			sortInfo : {
				field : 'key',
				direction : 'ASC'
			},
			autoLoad : true,
			proxy : new Ext.data.HttpProxy( {
				url : basepath + '/lookup.json?name=XD000080'
			}),
			reader : new Ext.data.JsonReader( {
				root : 'JSON',
				totalProperty : 'list'
			}, [ 'key', 'value' ])
		});
		
		//客户群成员分页，列模型等
		var groupLeaguerSm = new Ext.grid.CheckboxSelectionModel();
		var groupLeaguerrownum = new Ext.grid.RowNumberer({
			header : 'No.',
			width : 35
		});

		// 定义列模型
		var groupLeaguerCm = new Ext.grid.ColumnModel([groupLeaguerrownum,groupLeaguerSm,
			{header : 'ID', dataIndex : 'ID',sortable : true,width : 150,hidden:true,hideable:false}, 
	        {header : '客户号', dataIndex : 'CUST_ID',sortable : true,width : 150}, 
	        {header : '客户名称', dataIndex : 'CUST_ZH_NAME',sortable : true,width : 150 }, 
	        {header : '证件类型',dataIndex : 'IDENT_TYPE',sortable : true,width : 150,hidden:true,hideable:false},
	        {header : '证件类型',dataIndex : 'IDENT_TYPE_ORA',sortable : true,width : 150},
	        {header : '证件号码',dataIndex : 'IDENT_NO',sortable : true,width : 150}, 
	        {header : '客户类型',dataIndex : 'CUST_TYPE',sortable : true,width : 150,hidden:true},
	        {header : '客户类型',dataIndex : 'CUST_TYPE_ORA',sortable : true,width : 150}
	    ]);

		/**
		 * 数据存储
		 */
		var groupLeaguerStore = new Ext.data.Store({
			restful:true,	
			proxy : new Ext.data.HttpProxy({
			url:basepath+'/groupmemberedit.json',
				failure : function(response) {
				var resultArray = Ext.util.JSON.decode(response.status);
				if(resultArray == 403) {
					Ext.Msg.alert('提示','您没有此权限!');
				} 
			}
			}),
			reader: new Ext.data.JsonReader({
				totalProperty : 'json.count',
				root:'json.data'
			}, [ 'ID','CUST_ID','CUST_ZH_NAME','IDENT_TYPE','IDENT_TYPE_ORA','IDENT_NO','CUST_LEV','CUST_TYPE','CUST_TYPE_ORA'])
		});
			
		    groupLeaguerStore.on('beforeload', function() {
			this.baseParams = {
					querySign:'queryGroupMember',
					groupId: groupId
					
			  };
		    });
		
		// 每页显示条数下拉选择框
		var groupLeaguerpagesize_combo = new Ext.form.ComboBox({
			name : 'pagesize',
			triggerAction : 'all',
			mode : 'local',
			store : new Ext.data.ArrayStore({
									fields : ['value', 'text'],
									data : [ [ 10, '10条/页' ], [ 20, '20条/页' ], [ 50, '50条/页' ],
									         [ 100, '100条/页' ], [ 250, '250条/页' ],
									         [ 500, '500条/页' ] ]
			}),
			valueField : 'value',
			displayField : 'text',
			value : '20',
			editable : false,
			width : 85
		});
		var groupLeaguernumber = parseInt(groupLeaguerpagesize_combo.getValue());
		groupLeaguerpagesize_combo.on("select", function(comboBox) {// 改变每页显示条数reload数据
			groupLeaguerBar.pageSize = parseInt(groupLeaguerpagesize_combo.getValue());
			groupLeaguerStore.reload({
				params : {
				start : 0,
				limit : parseInt(groupLeaguerpagesize_combo.getValue())
			}
			});
		});
		
		var groupLeaguerBar = new Ext.PagingToolbar({// 分页工具栏
			pageSize : groupLeaguernumber,
			store : groupLeaguerStore,
			displayInfo : true,
			displayMsg : '显示{0}条到{1}条,共{2}条',
			emptyMsg : "没有符合条件的记录",
			items : ['-', '&nbsp;&nbsp;', groupLeaguerpagesize_combo]
		});
		//end
		
		//待加入成员列表
		var customerInfoSm = new Ext.grid.CheckboxSelectionModel();
		var customerInforownum = new Ext.grid.RowNumberer({
			header : 'No.',
			width : 35
		});
		// 定义列模型
		var customerInfoCm = new Ext.grid.ColumnModel([customerInforownum,customerInfoSm,
	        {header : '客户号', dataIndex : 'CUST_ID',sortable : true,width : 150}, 
	        {header : '客户名称', dataIndex : 'CUST_NAME',sortable : true,width : 150 }, 
	        {header : '归属客户经理ID', dataIndex : 'MGR_ID',sortable : true,width : 150 ,hidden:true,hideable:false},
	        {header : '归属客户经理', dataIndex : 'MGR_NAME',sortable : true,width : 150,hidden:true,hideable:false }, 
	        {header : '归属机构ID', dataIndex : 'INSTITUTION',sortable : true,width : 150 ,hidden:true,hideable:false},       
	        {header : '客户归属机构', dataIndex : 'INSTITUTION_NAME',sortable : true,width : 150 ,hidden:true,hideable:false},
	        {header : '客户类型',dataIndex : 'CUST_TYPE',sortable : true,width : 150,hidden:true},
	      {header : '客户类型',dataIndex : 'CUST_TYPE_ORA',sortable : true,width : 150}
	    ]);

		/**
		 * 数据存储
		 */
		var customerInfoStore = new Ext.data.Store({
			restful:true,	
			proxy : new Ext.data.HttpProxy({
			url:basepath+'/groupmemberedit.json',
				failure : function(response) {
				var resultArray = Ext.util.JSON.decode(response.status);
				if(resultArray == 403) {
					Ext.Msg.alert('提示','您没有此权限!');
				} 
			}
			}),
			reader: new Ext.data.JsonReader({
				totalProperty : 'json.count',
				root:'json.data'
			}, [ 'CUST_ID','CUST_NAME','IDENT_TYPE_ORA','IDENT_NO','CUST_LEV','CUST_TYPE','CUST_TYPE_ORA','MGR_ID','MGR_NAME','INSTITUTION','INSTITUTION_NAME'])
		});
		    customerInfoStore.on('beforeload', function() {
		    	if(''!=queryStr){
		    	__Str = Ext.encode(queryStr);	
		    	}else{
		    	__Str='';	
		    	}
			this.baseParams = {
					"condition":__Str,
					custType:groupMemberType,
					querySign:'queryCustomer',
					groupId: groupId
			  };
		    });
		
		// 每页显示条数下拉选择框
		var customerInfopagesize_combo = new Ext.form.ComboBox({
			name : 'pagesize',
			triggerAction : 'all',
			mode : 'local',
			store : new Ext.data.ArrayStore({
									fields : ['value', 'text'],
									data : [ [ 10, '10条/页' ], [ 20, '20条/页' ], [ 50, '50条/页' ],
									         [ 100, '100条/页' ], [ 250, '250条/页' ],
									         [ 500, '500条/页' ] ]
			}),
			valueField : 'value',
			displayField : 'text',
			value : '20',
			editable : false,
			width : 85
		});
		var customerInfonumber = parseInt(customerInfopagesize_combo.getValue());
		customerInfopagesize_combo.on("select", function(comboBox) {// 改变每页显示条数reload数据
			customerInfoBar.pageSize = parseInt(customerInfopagesize_combo.getValue());
			customerInfoStore.reload({
				params : {
				start : 0,
				limit : parseInt(customerInfopagesize_combo.getValue())
			}
			});
		});
		
		var customerInfoBar = new Ext.PagingToolbar({// 分页工具栏
			pageSize : customerInfonumber,
			store : customerInfoStore,
			displayInfo : true,
			displayMsg : '显示{0}条到{1}条,共{2}条',
			emptyMsg : "没有符合条件的记录",
			items : ['-', '&nbsp;&nbsp;', customerInfopagesize_combo]
		});
		//end

		var groupLeaguerGrid = new Ext.grid.GridPanel({
			height: 470,
			title : '客户群成员列表',
			frame : true,
			autoScroll : true,
			store : groupLeaguerStore, // 数据存储
			stripeRows : true, // 斑马线
			cm : groupLeaguerCm, // 列模型
			sm : groupLeaguerSm, // 复选框
			bbar : groupLeaguerBar,
			tbar:[{
				text : '客户视图',
				handler : function() {
		        	var checkedNodes = groupLeaguerGrid.getSelectionModel().selections.items;
					if(checkedNodes.length==0){
						Ext.Msg.alert('提示', '未选择任何客户');
						return ;
					}
					else if(checkedNodes.length>1){
						Ext.Msg.alert('提示', '您只能选中一个客户进行查看');
						return ;
					}
					var custid=checkedNodes[0].data.CUST_ID;
					var custName=checkedNodes[0].data.CUST_ZH_NAME;
					parent.parent.Wlj.ViewMgr.openViewWindow(0,custid,custName);
				}
			},{'text':'移除群成员',iconCls:'deleteIconCss',id:'moveOut',handler:function(){
				  batchDelete();
			  }}				 
			  ],
	        viewConfig : {
			},
			loadMask : {
				msg : '正在加载表格数据,请稍等...'
			}
		});
		
		var customerInfoGrid = new Ext.grid.GridPanel({
			region : 'center',
	        layout : 'fit',
			frame : true,
			autoScroll : true,
			height: 350,
			store : customerInfoStore, // 数据存储
			stripeRows : true, // 斑马线
			cm : customerInfoCm, // 列模型
			sm : customerInfoSm, // 复选框
			bbar : customerInfoBar,
	        tbar:customerInfoTar,
	        viewConfig : {
			},
			loadMask : {
				msg : '正在加载表格数据,请稍等...'
			}
		});
	
	        var replacePanel = new Ext.Panel( {
	            autoScroll : true,
	            autoHeight:true,
	            items : [  searchPanel1 ,customerInfoGrid]
				           
	        });
			
	        	
	var panel=new Ext.Panel({
		layout: 'fit',
		title : '成员信息',
		items:[]
	});
	var customerView=[{
		title:'成员信息',
		hideTitle:true,
		items:[panel]
	}];
	//划入面板前
	var beforeviewshow = function(view){
		if(view._defaultTitle == '成员信息'){
		groupStore.load({
			 callback : function() {
				 title_count = groupStore.getCount();
				if(title_count>0){
					var __hiddeAble= true;//按钮是否影藏
					var __modelSign1 = 0;
					var __modelSign2 = 0;
					title = groupStore.getAt(0);
					custFrom = title.json.CUST_FROM;
					groupMemberType=title.json.GROUP_MEMBER_TYPE;
					groupName=title.json.CUST_BASE_NAME;
					var creatId=title.json.CUST_BASE_CREATE_NAME;
					 if(creatId!=__userId){//非创建人
							 Ext.getCmp('shaixuan').setVisible(false);
							 Ext.getCmp('moveOut').setVisible(false);
							 __hiddeAble = true;
							 __modelSign1=0;
						     __modelSign2=1;
					}else{
							 Ext.getCmp('shaixuan').setVisible(true);
							 Ext.getCmp('moveOut').setVisible(true);
							 __hiddeAble = false;
							 __modelSign1=.45;
						     __modelSign2=.55;
					}
					if(custFrom=='2'){
						panel.add(agileQueryPanel1);
						panel.doLayout();
						custJustStore.on('beforeload', function() {
							this.baseParams = {
								groupMemberType:groupMemberType,
								groupId: groupId
							};
						});
						custJustStore.load({
							params : {
								start : 0,
								limit : parseInt(custJustpagesize_combo.getValue())
							}
						});
				    }else{
				    	var viewBlocBaseInfo = new Ext.Panel({
				        	layout : 'fit',
				        	items : [{
				        			 layout : 'column',
				        			 border : false,
				        			 items : [{
				        	        	 columnWidth :__modelSign1,
				        	        	 layout : 'form',
				        	        	 hidden:__hiddeAble,
				        	        	 border : false,
				        	        	 items : [{
				        	        		 layout : 'fit',
				        		        	 border : true,
				        		        	 items : [replacePanel]
				        	        	 }]
				        			 }
				        			 ,{
				        	        	 columnWidth :__modelSign2,
				        	        	 layout : 'form',
				        	        	 border : false,
				        	        	 items : [{
				        		             region : 'center',
				        		             layout : 'fit',
				        		             items : [ groupLeaguerGrid ]
				        	        	 }]
				        			 }
				        			 ]
				        		}]
				        	});
				        	if(groupMemberType == '1'||groupMemberType == '2'){
				        		searchPanel1.form.findField("CUST_TYPE").setValue(groupMemberType);
				    	    		searchPanel1.form.findField('CUST_TYPE').hide();
				    	    		Ext.getCmp('custZhName').columnWidth=0.5;
				    	    		Ext.getCmp('custId').columnWidth=0.5;
				        	}else{
				        		searchPanel1.form.findField('CUST_TYPE').show();
			    	        	Ext.getCmp('custZhName').columnWidth=0.33;
			    	    		Ext.getCmp('custId').columnWidth=0.33;
				        	}
				        	panel.add(viewBlocBaseInfo);
				        	panel.doLayout();
				        	groupLeaguerStore.load({
									params : {
									start : 0,
									limit : parseInt(groupLeaguerpagesize_combo.getValue())
								}
						 });
				        	customerInfoStore.reload({
								  params : {
								  start : 0,
								  limit : parseInt(customerInfopagesize_combo.getValue())
							      }
				        	});
				        }
					}
					panel.setTitle('');
				}
				
			});
		}
	};
	
