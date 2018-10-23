/*
 * @author sena
 * @since 2011-12-26
 */
Ext.ns('Ext.ux.form');

Ext.ux.form.CustMgrField = Ext.extend(Ext.form.TwinTriggerField, {
    initComponent : function(){
        Ext.ux.form.SearchField.superclass.initComponent.call(this);
        this.on('specialkey', function(f, e){
            if(e.getKey() == e.ENTER){
                this.onTrigger2Click();
            }
        }, this);
    },
    singleSelected:false,
    userId:'',
    validationEvent:false,
    validateOnBlur:false,
    trigger1Class:'x-form-clear-trigger',
    trigger2Class:'x-form-search-trigger',
    hideTrigger1:true,
    width:180,
    hasSearch : false,
    paramName : 'query',
    
    onTrigger2Click : function(){
    	var oThisSearchField=this;
		
    	var condtionObject = {searchType : 'ORGUSER'};
    	
		//复选框
		var sm = new Ext.grid.CheckboxSelectionModel();
		// 定义自动当前页行号
		var rownum = new Ext.grid.RowNumberer({header : 'No.',width : 28});
		// 定义列模型
		var cm = new Ext.grid.ColumnModel([rownum,sm, 
		        {header : '客户经理编号',dataIndex : 'USERID',sortable : true,width : 175},
			    {header : '客户经理名称',dataIndex : 'USERNAME',width : 225,sortable : true},
			    {header : '吸存号',dataIndex : 'USERCODE',width : 255,sortable : true,hidden : true}
		]);
		/**
		 * 数据存储
		 */
		 var oCustomerQueryStore = new Ext.data.Store({
			autoLoad :true,
			restful:true,	
	        proxy : new Ext.data.HttpProxy({
	        	url:basepath+'/commsearch.json'//?condition='+Ext.encode({searchType : 'ORGUSER'})
	        	//params:{ condition:Ext.encode({searchType:'ORGUSER'}) }		//参数'ORGUSER'为获取当前机构的客户经理
	        }),
	       reader: new Ext.data.JsonReader({
	    	   root:'json.data'
	       }, [{name: 'USERID'},{name: 'USERNAME'},{name : 'USERCODE'}])
		});
	 
		 oCustomerQueryStore.on('beforeload',function(){
			 oCustomerQueryStore.baseParams = {
					 condition:Ext.encode(condtionObject)
			 }
		 });
		 
		 
	var oPagesizeCombo = new Ext.form.ComboBox({
         name : 'pagesize',
         triggerAction : 'all',
         mode : 'local',
         store : new Ext.data.ArrayStore({
             fields : ['value', 'text'],
             data : [ [100, '100条/页'], [200, '200条/页'], [500, '500条/页'], [1000, '1000条/页']]
         }),
         valueField : 'value',
         displayField : 'text',
         value : '50',
         editable : false,
         width : 85
     });
    var number = parseInt(oPagesizeCombo.getValue());
    oPagesizeCombo.on("select", function(comboBox) {
    	prepareParams();
    	oCustomerQueryBbar.pageSize = parseInt(oPagesizeCombo.getValue()),
    	oCustomerQueryStore.load({
			params : {
				start : 0,
				limit : parseInt(oPagesizeCombo.getValue())
			}
		});
	});
	var oCustomerQueryBbar = new Ext.PagingToolbar({
        pageSize : number,
        store : oCustomerQueryStore,
        displayInfo : true,
        displayMsg : '显示{0}条到{1}条,共{2}条',
        emptyMsg : "没有符合条件的记录",
        items : ['-'
                 //oPagesizeCombo     设置每页几条记录
                ]
    });
	// 表格实例
	var oCustomerQueryGrid = new Ext.grid.GridPanel({
		height : 275,
		//width:1000,
				id:'viewgrid',
				frame : true,
				autoScroll : true,
				store : oCustomerQueryStore, // 数据存储
				stripeRows : true, // 斑马线
				cm : cm, // 列模型
				sm : sm, // 复选框
				//tbar : tbar, // 表格工具栏
				bbar:oCustomerQueryBbar,
				viewConfig:{
					   forceFit:false,
					   autoScroll:true
					},
				loadMask : {
					msg : '正在加载表格数据,请稍等...'
				}
			});

	
	var custMgrCondition = new Ext.form.FormPanel({
		labelWidth : 80, // 标签宽度
		frame : true, // 是否渲染表单面板背景色
		labelAlign : 'right', // 标签对齐方式
		buttonAlign : 'center',
		height : 80,
		items : [{
			layout : 'column',
			border : false,
			items : [{
				columnWidth : .45,
				layout : 'form',
				
				items : [ {
					xtype : 'textfield',
					fieldLabel : '员工号',
					id : 'COM_USER_ID',
					name : 'COM_USER_ID',
					width : '100',
					anchor : '100%'
				} ]
			},{
				columnWidth : .45,
				layout : 'form',
				items : [ {
					xtype : 'textfield',
					fieldLabel : '员工姓名',
					id : 'COM_USER_NAME',
					name : 'COM_USER_NAME',
					width : '100',
					anchor : '100%'
				} ]
			}]
		}],
	buttons : [{
					text : '查询',
					handler : function() {
						prepareParams();
						oCustomerQueryStore.load();
						oCustomerQueryStore.reload({
							params : {
								start : 0,
								limit : parseInt(oPagesizeCombo.getValue())
							}
						});
			
			   }},{
				   	text : '重置',
					handler : function() {
				   		custMgrCondition.getForm().reset();
					}
				}]
	});
	function prepareParams(){
		resetParams();
		var COM_USER_ID = Ext.getCmp("COM_USER_ID").getValue();
		if(COM_USER_ID)
			condtionObject.COM_USER_ID = COM_USER_ID;
		var COM_USER_NAME = Ext.getCmp("COM_USER_NAME").getValue();
		if(COM_USER_NAME)
			condtionObject.COM_USER_NAME = COM_USER_NAME;
	}
	function resetParams(){
		delete condtionObject.COM_USER_ID;
		delete condtionObject.COM_USER_NAME;
	}
	
	var oCustomerQueryWindow=new Ext.Window({
		title : '客户经理列表',
		closable : true,
		plain : true,
		resizable : false,
		collapsible : false,
		height:300,
		width:500,
		draggable : true,
		closeAction : 'close',
		modal : true, // 模态窗口 
		//animCollapse : false,
		border : false,
		//maximized:true,
		//maximizable: true,
		//autoScroll : true,
		autoHeight : true,
		closable : true,
		animateTarget : Ext.getBody(),
		constrain : true,
		items : [custMgrCondition,oCustomerQueryGrid],
		buttonAlign:'center',
		buttons:[{
			text:'确定',
				handler:function()
				{
			var json={'aId':[]};
			var json1 = {'userCode':[]};
			var sName='';
			var checkedNodes = oCustomerQueryGrid.getSelectionModel().selections.items;
			if(Ext.isObject(Ext.getCmp('custId'))==true){
			Ext.getCmp('custId').setValue(checkedNodes[0].data.USERID);
			}
			if(oThisSearchField.singleSelected&&checkedNodes.length>1)
			{
				Ext.Msg.alert('提示', '您只能选择一个客户经理');
				return ;
			}
			
			for(var i=0;i<checkedNodes.length;i++)
			{
				json.aId.push(checkedNodes[i].data.USERID);
				json1.userCode.push(checkedNodes[0].data.USERCODE);
				if(i==0){
					sName=sName+checkedNodes[i].data.USERNAME;
				}
				else{
					sName=sName+','+checkedNodes[i].data.USERNAME;
				}
			}
				oThisSearchField.userId=json;
				oThisSearchField.userCode=json1;
				oThisSearchField.setRawValue(sName);
				oCustomerQueryWindow.close();
				if (typeof oThisSearchField.callback == 'function') {
					oThisSearchField.callback();
				   
				      }
				}
			},{
			text: '取消',
				handler:function(){
				oCustomerQueryWindow.close();
					}
					}]	
		});
	oCustomerQueryStore.load();
	oCustomerQueryWindow.show();
	custMgrCondition.getForm().reset();
	
    return;
    }
});