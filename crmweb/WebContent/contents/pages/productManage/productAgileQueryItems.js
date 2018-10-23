/**
 * 条件、查询项展示面板
 */


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
//				value : _this.conditionValue,
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


	//查询条件对象
	var simple = new Com.yucheng.crm.query.SearchPanel({
		title : '查询条件'
	});
	
	
	var tabmain = new Ext.TabPanel({
		autoScroll : true,
		id : 'tabmain',
		width : document.body.scrollWidth / 1000 * 570,
		height : document.body.scrollHeight-180,
		activeTab : 0,
		frame : true,
		defaults : {
			autoHeight : true
		},
		items : [simple]
	});
	//条件链接符面板
	var radio = new Ext.Panel({
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
				name : "a",
				checked : true,
				listeners : {
					check : function(r,v){
						if(v)
							right_panel.conditionJoinType = 'true';
						else
							right_panel.conditionJoinType = 'false';
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
				name : "a"
			}) ]
		} ]
	});
	//右侧面板
	var right_panel = new Ext.Panel({
		
		currentSolutionsId : pProductId,//当前展示查询方案ID；
		conditionJoinType : 'true',//条件连接符数据，根据radio对象点选情况
		
		height : document.body.scrollHeight-60,
		width : document.body.scrollWidth / 100 * 60,
		frame : true,
		autoScroll : true,
		items : [ tabmain,radio],
		title : '查询设置',
		buttonAlign : 'center',
		buttons : [ {
			text : '保存',
			handler : function() {
//				if(simple.conditions.length==0){
//					Ext.Msg.alert('提示', '未加入任何条件列！');
//					return;
//				}
				if(!simple.getForm().isValid()){
					Ext.Msg.alert('提示', '查询条件输入有误！');
					return;
				}
				var conditions = simple.getConditionsAttrs();
				Ext.Msg.wait('正在保存方案及目标客户信息客户,请等待.........','系统提示');
						Ext.Ajax.request({
							url:basepath+'/targetcussearch.json',
							method: 'POST',
							success : function(response) {
								//将产品目标客户存入目标客户表
								Ext.Ajax.request({
									url:basepath+'/querytatgetcusquery!queryAgileCondition.json?SS_ID='+pProductId,
			                        method: 'GET',
									success : function(response) {
										var conditionData = Ext.util.JSON.decode(response.responseText);
										 conditionArray=conditionData.JSON.data;
										 if(conditionArray.length>0){
												var conditions = new Array();
												Ext.each(conditionArray,function(con){
													var conAtt = {};
													conAtt.ss_col_item = con.SS_COL_ITEM;
													conAtt.ss_col_op = con.SS_COL_OP;
													conAtt.ss_col_value = con.SS_COL_VALUE;
													conditions.push(conAtt);
												});
												Ext.Ajax.request({
													url:basepath+'/product-targetCust!addFitCust.json',
													params : {
								         		    		   conditionAttrs : Ext.encode(conditions),
								         		    		   radio: conditionArray[0].SS_COL_JOIN,
								         		    		   productId:pProductId,
								         		    		  productName:productName
													},
													method: 'POST',
													success : function(response) {
														Ext.Msg.alert('提示','操作成功!');
													},
													failure : function(response) {
														Ext.Msg.alert('提示','添加目标客户失败!');
													}
												});
			    				}else {
			    					Ext.Msg.alert('提示','请先设定目标客户!');
			    				}	 
									},
									failure : function(response) {
										  var resultArray = Ext.util.JSON.decode(response.status);
										   if(resultArray == 403) {
										      Ext.Msg.alert('提示','查询失败!');
										   } else {
											  Ext.Msg.alert('提示','操作失败!');
										   }
									}});
								
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
								conditionCols : Ext.encode(conditions),
								solutionID: right_panel.currentSolutionsId,
								'radio':right_panel.conditionJoinType
							}
						});
			}
		}]
	});
	right_panel.on('afterrender',function(){
		/**数据字段拖拽代理*/
		new Ext.dd.DropTarget(right_panel.body.dom, {
	    	ddGroup : 'rightPanel',
	    	notifyDrop : function(ddSource, e, data) {
	    	if (!data.node.leaf) {
	    		return;
	    	}
	    	if(tabmain.activeTab==simple){
	    		simple.addItems(data.node);
	    	}
	    	return true;
	    	}
	    });
	});
	

