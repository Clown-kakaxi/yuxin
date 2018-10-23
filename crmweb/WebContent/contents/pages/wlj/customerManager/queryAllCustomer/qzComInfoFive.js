/**
 * 第五页：持股人、供货商和买售商、抵押、关联企业、主要固定资产盈利获利情况、往来银行表
 */
var RowNumber = new Ext.grid.RowNumberer({
    header:'NO.',
    width:35
});
//////////////////////////////////////////////持股人信息///////////////////////////////////////////////////////////

var addrCustInfoRecord = new Ext.data.Record.create([
    {name:'PARTNER_ID'},
	{name:'CUST_ID'},
	{name:'PARTNER_RATE'},
	{name:'PARTNER_MONEY'},
	{name:'PARTNER'},
	{name:'LAST_UPDATE_SYS'}, 
	{name:'LAST_UPDATE_USER'},
	{name:'LAST_UPDATE_TM'}
]);
var addrPartnerStore = new Ext.data.Store({
    restful:true,
    proxy: new Ext.data.HttpProxy({
        url: basepath + '/dealWithComFive!queryPartner.json',
        method:'GET'
    }),
    reader: new Ext.data.JsonReader({
        root:'json.data',
        totalProperty:'json.count'
    },addrCustInfoRecord)
    
});
/**
 * 潜在客户持股人信息展示列
 */

var addrPartnerCm = new Ext.grid.ColumnModel([
                                               RowNumber,
                                               {dataIndex:'PARTNER',header:'持股人',width : 120,sortable : true},
                                               {dataIndex:'PARTNER_RATE',header:'持股比例',width : 350,sortable : true},
                                               {dataIndex:'PARTNER_MONEY',header:'持股金额',width : 100,sortable : true}
                                           ]);
/**
 * 点击新增OR修改时弹出FORM
 */
var partnerForm = new Ext.FormPanel({
    frame : true,
    autoScroll : true,
    split : true,
    items : [
        {xtype:'textfield',name:'PARTNER_ID',hidden:true},
        {xtype:'textfield',name:'CUST_ID',hidden:true},
        {xtype:'textfield',name:'PARTNER',fieldLabel: '<font color="red">*</font>持股人',anchor:'90%',maxLength:50,allowBlank:false},
        {xtype :'numberfield',name :'PARTNER_RATE',fieldLabel:'<font color="red">*</font>持股比例',anchor:'90%',maxLength:50,allowBlank:false},
		{xtype:'numberfield',name:'PARTNER_MONEY',fieldLabel: '<font color="red">*</font>持股金额',anchor:'90%',maxLength:50,allowBlank:false}
    ]
});
/**
 * 点击新增OR修改时弹出WINDOW
 */
var partnerWindow = new Ext.Window({
    title:'新增OR修改',
    width: 500,
    height: 260,
    layout: 'fit',
    closable: true,
    closeAction: 'hide',
    modal: true,
    buttonAlign : 'center',
    items : [partnerForm],
    buttons:[{
    	text:'暂存',
    	handler:function(){
    		if (!partnerForm.getForm().isValid()) {
                Ext.Msg.alert("提示", "输入有误或存在漏输项，请重新输入！");
                return false;
            }
            var tempJson = partnerForm.getForm().getValues(false);
            var validFlag = false;//判断持股人是否已存在,由于不允许修改持股人,故不作与原值比较
	        addrPartnerStore.each(function(record){
				if(record.data.PARTNER == tempJson.PARTNER&& (partnerWindow.title !='修改')){
					validFlag = true;
					return;
				}
	        });
	        if(validFlag){
	        	Ext.Msg.alert("提示", "持股人已存在，请重新输入！");
	            return false;
	        }
            if(partnerWindow.title =='修改'){
            	var tempRowData = partnerGroupGrid.getSelectionModel().getSelections()[0].data;
            	//移除当前修改数据
            	addrPartnerStore.removeAt(addrPartnerStore.findExact('PARTNER_ID', tempRowData.PARTNER_ID));
            	tempRowData.PARTNER = tempJson.PARTNER;
            	tempRowData.PARTNER_RATE = tempJson.PARTNER_RATE;
            	tempRowData.PARTNER_MONEY = tempJson.PARTNER_MONEY;
            	tempRowData.LAST_UPDATE_SYS = 'CRM';
            	tempRowData.LAST_UPDATE_USER = JsContext._userId;
            	tempRowData.LAST_UPDATE_TM = _sysCurrDate;
            	tempRowData.IS_ADD_FLAG = Number(tempJson.PARTNER_ID) > 0?'0':'1';
            	var tempRecord = new Ext.data.Record(tempRowData,null);
            	addrPartnerStore.addSorted(tempRecord);
            }else{
	            var tempRowData = {
	            	PARTNER_ID : -(new Date().getTime()),
//	            	CUST_ID : custId,
	            	CUST_ID : 111,
	            	PARTNER : tempJson.PARTNER,
	            	PARTNER_RATE :tempJson.PARTNER_RATE,
	            	PARTNER_MONEY :tempJson.PARTNER_MONEY,
	            	LAST_UPDATE_SYS : 'CRM',
	            	LAST_UPDATE_USER : JsContext._userId,
	            	LAST_UPDATE_TM : _sysCurrDate,//2014-10-29
	            	IS_ADD_FLAG : '1'
	            };
	            var tempRecord = new Ext.data.Record(tempRowData,null);
	            addrPartnerStore.addSorted(tempRecord);
            }
            addrPartnerStore.sort('PARTNER','ASC');
            partnerWindow.hide();
    	}
    },{
        text: '返回',
        handler: function(){
            partnerWindow.hide();
        }
    }]
});

/**
 * grid按钮
 */
var addrPartnerTbar = new Ext.Toolbar({
    items: [{
        text: '新增',
        hidden:false,
        id:'com_xz_5-cgr',
        handler: function(){
            if(partnerForm.getForm().getEl()){
                partnerForm.getForm().getEl().dom.reset();
            }
            partnerWindow.setTitle('新增');
            partnerForm.getForm().findField('PARTNER').setReadOnly(false);
            partnerForm.getForm().findField('PARTNER').removeClass('x-readOnly');
            partnerWindow.show();
        }
    },{
        text: '修改',
        hidden:false,
        id:'com_xg_5-cgr',
        handler: function(){
            var selectLength = partnerGroupGrid.getSelectionModel().getSelections().length;
            var selectRecord = partnerGroupGrid.getSelectionModel().getSelections()[0];
            if(selectLength != 1){
                Ext.Msg.alert('提示','请选择一条记录!');
                return false;
            }
            partnerWindow.setTitle('修改');
            partnerForm.getForm().findField('PARTNER').setReadOnly(true);
            partnerForm.getForm().findField('PARTNER').addClass('x-readOnly');
            partnerForm.getForm().loadRecord(selectRecord);
            partnerWindow.show();
        }
    },{
        text: '移除',
        hidden:false,
        id:'com_yc_5-cgr',
        handler: function(){
            var selectLength = partnerGroupGrid.getSelectionModel().getSelections().length;
            var selectRecord = partnerGroupGrid.getSelectionModel().getSelections()[0];
            if(selectLength != 1 || selectRecord.data.IS_ADD_FLAG != '1'){
                Ext.Msg.alert('提示','请选择一条新增暂存的记录!');
                return false;
            }
            addrPartnerStore.remove(selectRecord);
        }
    }]
});
/**
 * 潜在客户持股人信息
 */
var partnerGroupGrid = new Ext.grid.GridPanel({
	height: 180,
    region: 'center',
    autoScroll: true,
    stripeRows: true,
    store: addrPartnerStore,
    cm : addrPartnerCm,
//    tbar: addrPartnerTbar,
//    viewConfig : {
//		getRowClass : function(record,rowIndex,rowParams,store){
//			//根据是否修改状态修改背景颜色  
//			if(record.data.IS_ADD_FLAG=='0'){//修改过
//			  	return 'my_row_set_blue';
//		  	}else if(record.data.IS_ADD_FLAG == '1'){//新增
//		  		return 'my_row_set_red';
//		  	}
//		}
//	},
    loadMask: {
        msg: '正在加载表格数据,请稍等...'
    }
});
addrPartnerStore.on('beforeload',function(){
	addrPartnerStore.baseParams = {
		custId : custId
	};
});
//////////////////////////////////////////////供货商及买售商///////////////////////////////////////////////////////////
/**
 * 供货商及买售商类型
 */
var infSignStore =  new Ext.data.Store( {
	restful : true,
	autoLoad : true,
	sortInfo : {
            field:'key',
            direction:'ASC'
        },
	proxy : new Ext.data.HttpProxy( {
		url : basepath + '/lookup.json?name=IF_SALE'
	}),
	reader : new Ext.data.JsonReader( {
		root : 'JSON'
	},['key','value'])
});
/**
 * 供货商及买售商展示列
 */
var infComCm = new Ext.grid.ColumnModel([
    RowNumber,
    {dataIndex:'INF_TYPE',header:'类型',width : 100,sortable : true},
    {dataIndex:'INF_RATE',header:'比例',width : 100,sortable : true}
]);
/**
 * 供货商及买售商record
 */
var infComRecord = new Ext.data.Record.create([
	{name : 'INF_TYPE'},
	{name : 'INF_RATE'},
	{name:'CUST_ID'},
	{name:'LAST_UPDATE_SYS'}, 
	{name:'LAST_UPDATE_USER'},
	{name:'LAST_UPDATE_TM'}
]);
/**
 * 供货商及买售商store
 */
var infComStore = new Ext.data.Store({
    restful:true,
    proxy: new Ext.data.HttpProxy({
        url: basepath+'/dealWithComFive!queryInOrOut.json',
        method:'GET'
    }),
    reader: new Ext.data.JsonReader({
        root:'json.data',
        totalProperty:'json.count'
    },infComRecord)
});

/**
 * 供货商及买售商的新增or修改FORM
 */
var infComForm = new Ext.FormPanel({
    frame : true,
    autoScroll : true,
    split : true,
    items : [{
    	layout:'column',
    	items:[{
        	columnWidth : .5,
			layout : 'form',
			items :[
		    	{xtype:'textfield',name:'INF_ID',hidden:true,anchor:'95%'},		       
		        {xtype:'combo',name:'INF_TYPE',fieldLabel: '<font color="red">*</font>类型',anchor:'90%',maxLength:20,store:infSignStore,resizable : true,valueField : 'key',displayField : 'value',
					mode : 'local',forceSelection : true,triggerAction : 'all',hidden:false,allowBlank:false}
    		]
    	},{
        	columnWidth : .5,
			layout : 'form',
			items :[
			        {xtype:'textfield',name:'CUST_ID',hidden:true,anchor:'95%'},
			        {xtype:'numberfield',name:'INF_RATE',fieldLabel: '<font color="red">*</font>比例',anchor:'90%',maxLength:20,allowBlank:false}
				
    		]
    	}]
    }]
});

/**
 * 供货商及买售商新增or修改的window
 */
var infComWindow = new Ext.Window({
    title:'新增OR修改',
    width: 500,
    height: 260,
    layout: 'fit',
    closable: true,
    closeAction: 'hide',
    modal: true,
    buttonAlign : 'center',
    items : [infComForm],
    buttons:[{
    	text:'暂存',
    	handler:function(){
    		if (!infComForm.getForm().isValid()) {
                Ext.Msg.alert("提示", "输入有误或存在漏输项，请重新输入！");
                return false;
            }
           
            var tempJson = infComForm.getForm().getValues(false);
            var validFlag = false;//判断类型是否已存在,由于不允许修改类型,故不作与原值比较
	        infComStore.each(function(record){
				if(record.data.INF_TYPE == tempJson.INF_TYPE 
					&& (tempJson.INF_ID === undefined || tempJson.INF_ID === "")){
					validFlag = true;
					return;
				}
				if(record.data.INF_TYPE == tempJson.INF_TYPE && tempJson.INF_ID != undefined 
					&& tempJson.INF_ID != "" && record.data.INF_ID != tempJson.INF_ID){
					validFlag = true;
					return;
				}
	        });
	        if(validFlag){	        	
	        	Ext.Msg.alert("提示",infComForm.form.findField('INF_TYPE').lastSelectionText+"已存在，请重新输入！");
	            return false;
	        }
            if(infComWindow.title =='修改'){
            	var tempRowData = infComGrid.getSelectionModel().getSelections()[0].data;
            	//移除当前修改数据
            	infComStore.removeAt(infComStore.findExact('INF_ID', tempRowData.INF_ID));
            	tempRowData.INF_TYPE = tempJson.INF_TYPE;
            	tempRowData.INF_TYPE_ORA = infComForm.getForm().findField('INF_TYPE').getRawValue();
            	tempRowData.INF_RATE = tempJson.INF_RATE;
            	tempRowData.LAST_UPDATE_SYS = 'CRM';
            	tempRowData.LAST_UPDATE_USER = JsContext._userId;
            	tempRowData.LAST_UPDATE_TM = _sysCurrDate;
            	tempRowData.IS_ADD_FLAG = Number(tempJson.INF_ID) > 0?'0':'1';
            	var tempRecord = new Ext.data.Record(tempRowData,null);
            	infComStore.addSorted(tempRecord);
            }else{
	            var tempRowData = {
	            	INF_ID : -(new Date().getTime()),
//	            	CUST_ID : custId,
	            	CUST_ID : 111,
	            	INF_TYPE : tempJson.INF_TYPE,
	            	INF_TYPE_ORA : infComForm.getForm().findField('INF_TYPE').getRawValue(),
	            	INF_RATE : tempJson.INF_RATE,
	            	LAST_UPDATE_SYS : 'CRM',
	            	LAST_UPDATE_USER : JsContext._userId,
	            	LAST_UPDATE_TM : _sysCurrDate,//2014-10-29
	            	IS_ADD_FLAG : '1'
	            };
	            var tempRecord = new Ext.data.Record(tempRowData,null);
	            infComStore.addSorted(tempRecord);
            }
            infComStore.sort('INF_TYPE','ASC');
            infComWindow.hide();
    	}
    },{
        text: '返回',
        handler: function(){
            infComWindow.hide();
        }
    }]
});

/**
 * 供货商及买售商的按钮栏
 */
var infComTbar = new Ext.Toolbar({
    items: [{
        text: '新增',
        hidden:false,
        id:'com_xz_5-ghs',
        handler: function(){
            if(infComForm.getForm().getEl()){
                infComForm.getForm().getEl().dom.reset();
            }
            infComWindow.setTitle('新增');
            infComForm.getForm().findField('INF_TYPE').setReadOnly(false);
            infComForm.getForm().findField('INF_TYPE').removeClass('x-readOnly');
            infComWindow.show();
        }
    },{
        text: '修改',
        hidden:false,
        id:'com_xg_5-ghs',
        handler: function(){
            var selectLength = infComGrid.getSelectionModel().getSelections().length;
            var selectRecord = infComGrid.getSelectionModel().getSelections()[0];
            if(selectLength != 1){
                Ext.Msg.alert('提示','请选择一条记录!');
                return false;
            }
            infComWindow.setTitle('修改');
            infComForm.getForm().findField('INF_TYPE').setReadOnly(true);
            infComForm.getForm().findField('INF_TYPE').addClass('x-readOnly');
            infComForm.getForm().loadRecord(selectRecord);
            infComWindow.show();
        }
    },{
        text: '移除',
        hidden:false,
        id:'com_yc_5-ghs',
        handler: function(){
            var selectLength = infComGrid.getSelectionModel().getSelections().length;
            var selectRecord = infComGrid.getSelectionModel().getSelections()[0];
            if(selectLength != 1 || selectRecord.data.IS_ADD_FLAG != '1'){
                Ext.Msg.alert('提示','请选择一条新增暂存的记录!');
                return false;
            }
            infComStore.remove(selectRecord);
        }
    }]
});

/**
 * 供货商及买售商grid
 */
var infComGrid = new Ext.grid.GridPanel({
	height: 180,
    region: 'center',
    autoScroll: true,
    stripeRows: true,
    store: infComStore,
    cm : infComCm,
//    tbar: infComTbar,
//    viewConfig : {
//		getRowClass : function(record,rowIndex,rowParams,store){
//			//根据是否修改状态修改背景颜色  
//			if(record.data.IS_ADD_FLAG=='0'){//修改过
//			  	return 'my_row_set_blue';
//		  	}else if(record.data.IS_ADD_FLAG == '1'){//新增
//		  		return 'my_row_set_red';
//		  	}
//		}
//	},
    loadMask: {
        msg: '正在加载表格数据,请稍等...'
    }
});

infComStore.on('beforeload',function(){
	infComStore.baseParams = {
		custId : custId
	};
});
/////////////////////////////////////////抵押信息////////////////////////////////

/**
 * 抵押类型
 */
var arCustTypeStore =  new Ext.data.Store( {
	restful : true,
	autoLoad : true,
	sortInfo : {
            field:'key',
            direction:'ASC'
        },
	proxy : new Ext.data.HttpProxy( {
		url : basepath + '/lookup.json?name=XD000278'
	}),
	reader : new Ext.data.JsonReader( {
		root : 'JSON'
	},['key','value'])
});

/**
 * 抵押信息展示列
 */
var mortgageCm = new Ext.grid.ColumnModel([
    RowNumber,
    {dataIndex:'MORTGAGE_TYPE',header:'抵押类型',width : 150,sortable : true}
]);
/**
 * 抵押信息record
 */
var mortgageRecord = new Ext.data.Record.create([
    {name:'MORTGAGE_ID'},
	{name:'CUST_ID'},
	{name:'MORTGAGE_TYPE'},
	{name:'LAST_UPDATE_SYS'}, 
	{name:'LAST_UPDATE_USER'},
	{name:'LAST_UPDATE_TM'}
]);
/**
 * 抵押信息store
 */
var mortgageStore = new Ext.data.Store({
    restful:true,
    proxy: new Ext.data.HttpProxy({
        url: basepath + '/acrmFCiContmethInfo.json?check=1',
        method:'GET'
    }),
    reader: new Ext.data.JsonReader({
        root:'json.data',
        totalProperty:'json.count'
    },mortgageRecord)
});

/**
 * 抵押信息新增or修改form
 */
var mortgageForm = new Ext.FormPanel({
    frame : true,
    autoScroll : true,
    split : true,
    items : [
        {xtype:'textfield',name:'MORTGAGE_ID',hidden:true,anchor:'95%'},
        {xtype:'textfield',name:'CUST_ID',hidden:true,anchor:'95%'},
        {xtype : 'combo',name : 'MORTGAGE_TYPE',fieldLabel : '<font color="red">*</font>抵押类型',store:arCustTypeStore,resizable : true,valueField : 'key',displayField : 'value',
			mode : 'local',forceSelection : true,triggerAction : 'all',hidden:false,allowBlank:false,anchor:'95%'}
    ]
});

/**
 * 抵押信息新增or修改window
 */
var mortgageWindow = new Ext.Window({
    title:'新增OR修改',
    width: 500,
    height: 260,
    layout: 'fit',
    closable: true,
    closeAction: 'hide',
    modal: true,
    buttonAlign : 'center',
    items : [mortgageForm],
    buttons:[{
    	text:'暂存',
    	handler:function(){
    		if (!mortgageForm.getForm().isValid()) {
                Ext.Msg.alert("提示", "输入有误或存在漏输项，请重新输入！");
                return false;
            }
           
            var tempJson = mortgageForm.getForm().getValues(false);
            var validFlag = false;//判断类型是否已存在,由于不允许修改类型,故不作与原值比较
	        mortgageStore.each(function(record){
				if(record.data.MORTGAGE_TYPE == tempJson.MORTGAGE_TYPE &&(mortgageWindow.title !='修改')){
					validFlag = true;
					return;
				}
	        });
	        if(validFlag){
	        	Ext.Msg.alert("提示", mortgageForm.form.findField('MORTGAGE_TYPE').lastSelectionText+"已存在，请重新输入！");
	            return false;
	        }
            if(mortgageWindow.title =='修改'){
            	var tempRowData = mortgageGridPanel.getSelectionModel().getSelections()[0].data;
            	//移除当前修改数据
            	mortgageStore.removeAt(mortgageStore.findExact('MORTGAGE_ID', tempRowData.MORTGAGE_ID));
            	tempRowData.MORTGAGE_TYPE = tempJson.MORTGAGE_TYPE;
            	tempRowData.MORTGAGE_TYPE_ORA = mortgageForm.getForm().findField('MORTGAGE_TYPE').getRawValue();
            	tempRowData.LAST_UPDATE_SYS = 'CRM';
            	tempRowData.LAST_UPDATE_USER = JsContext._userId;
            	tempRowData.LAST_UPDATE_TM = _sysCurrDate;
            	tempRowData.IS_ADD_FLAG = Number(tempJson.MORTGAGE_ID) > 0?'0':'1';
            	var tempRecord = new Ext.data.Record(tempRowData,null);
            	mortgageStore.addSorted(tempRecord);
            }else{
	            var tempRowData = {
	            	MORTGAGE_ID : -(new Date().getTime()),
//	            	CUST_ID : custId,
	            	CUST_ID : 111,
	            	MORTGAGE_TYPE : tempJson.MORTGAGE_TYPE,
	            	MORTGAGE_TYPE_ORA : mortgageForm.getForm().findField('MORTGAGE_TYPE').getRawValue(),
	            	LAST_UPDATE_SYS : 'CRM',
	            	LAST_UPDATE_USER : JsContext._userId,
	            	LAST_UPDATE_TM : _sysCurrDate,//2014-10-29
	            	IS_ADD_FLAG : '1'
	            };
	            var tempRecord = new Ext.data.Record(tempRowData,null);
	            mortgageStore.addSorted(tempRecord);
            }
            mortgageStore.sort('MORTGAGE_TYPE','ASC');
            mortgageWindow.hide();
    	}
    },{
        text: '返回',
        handler: function(){
            mortgageWindow.hide();
        }
    }]
});

/**
 * 抵押信息按钮栏
 */
var mortgageTbar = new Ext.Toolbar({
    items: [{
        text: '新增',
        hidden:false,
        id:'com_xz_5-dy',
        handler: function(){
            if(mortgageForm.getForm().getEl()){
                mortgageForm.getForm().getEl().dom.reset();
            }
            mortgageWindow.setTitle('新增');
            mortgageForm.getForm().findField('MORTGAGE_TYPE').setReadOnly(false);
            mortgageForm.getForm().findField('MORTGAGE_TYPE').removeClass('x-readOnly');
            mortgageWindow.show();
        }
    },{
        text: '修改',
        hidden:false,
        id:'com_xg_5-dy',
        handler: function(){
            var selectLength = mortgageGridPanel.getSelectionModel().getSelections().length;
            var selectRecord = mortgageGridPanel.getSelectionModel().getSelections()[0];
            if(selectLength != 1){
                Ext.Msg.alert('提示','请选择一条记录!');
                return false;
            }
            mortgageWindow.setTitle('修改');
//            mortgageForm.getForm().findField('MORTGAGE_TYPE').setReadOnly(true);
//            mortgageForm.getForm().findField('MORTGAGE_TYPE').addClass('x-readOnly');
            mortgageForm.getForm().loadRecord(selectRecord);
            mortgageWindow.show();
        }
    },{
        text: '移除',
        hidden:false,
        id:'com_yc_5-dy',
        handler: function(){
            var selectLength = mortgageGridPanel.getSelectionModel().getSelections().length;
            var selectRecord = mortgageGridPanel.getSelectionModel().getSelections()[0];
            if(selectLength != 1 || selectRecord.data.IS_ADD_FLAG != '1'){
                Ext.Msg.alert('提示','请选择一条新增暂存的记录!');
                return false;
            }
            mortgageStore.remove(selectRecord);
        }
    }]
});

/**
 * 抵押信息grid
 */
var mortgageGridPanel = new Ext.grid.GridPanel({
	height: 180,
    region: 'center',
    autoScroll: true,
    stripeRows: true,
    store: mortgageStore,
    cm : mortgageCm,
//    tbar: mortgageTbar,
//    viewConfig : {
//		getRowClass : function(record,rowIndex,rowParams,store){
//			//根据是否修改状态修改背景颜色  
//			if(record.data.IS_ADD_FLAG=='0'){//修改过
//			  	return 'my_row_set_blue';
//		  	}else if(record.data.IS_ADD_FLAG == '1'){//新增
//		  		return 'my_row_set_red';
//		  	}
//		}
//	},
    loadMask: {
        msg: '正在加载表格数据,请稍等...'
    }
});
mortgageStore.on('beforeload',function(){
	mortgageStore.baseParams = {
		custId : custId
	};
});
/////////////////////////////////////////关联企业信息////////////////////////////////

/**
 *关联企业信息展示列
 */
var relationComoCm = new Ext.grid.ColumnModel([
    RowNumber,
    {dataIndex:'CUST_ID',header:'编号',width : 150,sortable : true,hidden:true},
    {dataIndex:'RELATION_COM_ID',header : '关联企业编号',width : 100,sortable : true,hidden:true}, 
    {dataIndex:'RELATION_COM',header:'关联企业',width : 100,sortable : true},
    {dataIndex:'COM_INS_CODE',header:'组织机构代码',width: 100,sortable:true}
]);
/**
 * 关联企业信息record
 */
var relationComoRecord = new Ext.data.Record.create([
		{name:'CUST_ID'},
		{name:'RELATION_COM_ID'},
		{name:'RELATION_COM'},
		{name:'COM_INS_CODE'},
		{name:'LAST_UPDATE_SYS'}, 
		{name:'LAST_UPDATE_USER'},
		{name:'LAST_UPDATE_TM'}
]);
/**
 * 关联企业信息store
 */
var relationComoStore = new Ext.data.Store({
    restful:true,
    proxy: new Ext.data.HttpProxy({
        url: basepath + '/acrmFCiContmethInfo.json?check=1',
        method:'GET'
    }),
    reader: new Ext.data.JsonReader({
        root:'json.data',
        totalProperty:'json.count'
    },relationComoRecord)
});

/**
 * 关联企业信息新增or修改form
 */
var relationComoForm = new Ext.FormPanel({
    frame : true,
    autoScroll : true,
    split : true,
    items : [
        {xtype:'textfield',name:'RELATION_COM_ID',hidden:true,anchor:'95%'},
        {xtype:'textfield',name:'CUST_ID',hidden:true,anchor:'95%'},
        {xtype:'textfield',name:'RELATION_COM',anchor:'90%',maxLength:100,fieldLabel:'<font color="red">*</font>关联企业名称'},
        {xtype:'textfield',name:'COM_INS_CODE',anchor:'90%',maxLength:100,fieldLabel:'<font color="red">*</font>组织机构代码'}
    ]
});

/**
 * 关联企业信息新增or修改window
 */
var relationComoWindow = new Ext.Window({
    title:'新增OR修改',
    width: 500,
    height: 260,
    layout: 'fit',
    closable: true,
    closeAction: 'hide',
    modal: true,
    buttonAlign : 'center',
    items : [relationComoForm],
    buttons:[{
    	text:'暂存',
    	handler:function(){
    		if (!relationComoForm.getForm().isValid()) {
                Ext.Msg.alert("提示", "输入有误或存在漏输项，请重新输入！");
                return false;
            }
         
            var tempJson = relationComoForm.getForm().getValues(false);
            var validFlag = false;//判断关联企业名称是否已存在,由于不允许修改关联企业名称,故不作与原值比较
	        relationComoStore.each(function(record){
				if(record.data.RELATION_COM == tempJson.RELATION_COM 
					&& (relationComoWindow.title !='修改')){
					validFlag = true;
					return;
				}
	        });
	        if(validFlag){
	        	Ext.Msg.alert("提示", relationComoForm.form.findField('RELATION_COM').getValue()+"已存在，请重新输入！");
	            return false;
	        }
            if(relationComoWindow.title =='修改'){
            	var tempRowData = relationGridPanel.getSelectionModel().getSelections()[0].data;
            	//移除当前修改数据
            	relationComoStore.removeAt(relationComoStore.findExact('RELATION_COM_ID', tempRowData.RELATION_COM_ID));      	
            	tempRowData.RELATION_COM = tempJson.RELATION_COM;
            	tempRowData.COM_INS_CODE = tempJson.COM_INS_CODE;
            	tempRowData.LAST_UPDATE_SYS = 'CRM';
            	tempRowData.LAST_UPDATE_USER = JsContext._userId;
            	tempRowData.LAST_UPDATE_TM = _sysCurrDate;
            	tempRowData.IS_ADD_FLAG = Number(tempJson.RELATION_COM_ID) > 0?'0':'1';
            	var tempRecord = new Ext.data.Record(tempRowData,null);
            	relationComoStore.addSorted(tempRecord);
            }else{
	            var tempRowData = {
	            	RELATION_COM_ID : -(new Date().getTime()),
//	            	CUST_ID : custId,
	            	CUST_ID : 111,
	            	RELATION_COM : tempJson.RELATION_COM,
	            	COM_INS_CODE : tempJson.COM_INS_CODE,
	            	LAST_UPDATE_SYS : 'CRM',
	            	LAST_UPDATE_USER : JsContext._userId,
	            	LAST_UPDATE_TM : _sysCurrDate,//2014-10-29
	            	IS_ADD_FLAG : '1'
	            };
	            var tempRecord = new Ext.data.Record(tempRowData,null);
	            relationComoStore.addSorted(tempRecord);
            }
            relationComoStore.sort('RELATION_COM','ASC');
            relationComoWindow.hide();
    	}
    },{
        text: '返回',
        handler: function(){
            relationComoWindow.hide();
        }
    }]
});

/**
 * 关联企业信息按钮栏
 */
var relationComoTbar = new Ext.Toolbar({
    items: [{
        text: '新增',
        hidden:false,
        id:'com_xz_5-glqy',
        handler: function(){
            if(relationComoForm.getForm().getEl()){
                relationComoForm.getForm().getEl().dom.reset();
            }
            relationComoWindow.setTitle('新增');
            relationComoForm.getForm().findField('RELATION_COM').setReadOnly(false);
            relationComoForm.getForm().findField('RELATION_COM').removeClass('x-readOnly');
            relationComoWindow.show();
        }
    },{
        text: '修改',
        hidden:false,
        id:'com_xg_5-glqy',
        handler: function(){
            var selectLength = relationGridPanel.getSelectionModel().getSelections().length;
            var selectRecord = relationGridPanel.getSelectionModel().getSelections()[0];
            if(selectLength != 1){
                Ext.Msg.alert('提示','请选择一条记录!');
                return false;
            }
        
            relationComoWindow.setTitle('修改');
            relationComoForm.getForm().findField('RELATION_COM').setReadOnly(true);
            relationComoForm.getForm().findField('RELATION_COM').addClass('x-readOnly');
            relationComoForm.getForm().loadRecord(selectRecord);
            relationComoWindow.show();
        }
    },{
        text: '移除',
        hidden:false,
        id:'com_yc_5-glqy',
        handler: function(){
            var selectLength = relationGridPanel.getSelectionModel().getSelections().length;
            var selectRecord = relationGridPanel.getSelectionModel().getSelections()[0];
            if(selectLength != 1 || selectRecord.data.IS_ADD_FLAG != '1'){
                Ext.Msg.alert('提示','请选择一条新增暂存的记录!');
                return false;
            }
            relationComoStore.remove(selectRecord);
        }
    }]
});

/**
 * 关联企业信息grid
 */
var relationGridPanel = new Ext.grid.GridPanel({
	height: 180,
    region: 'center',
    autoScroll: true,
    stripeRows: true,
    store: relationComoStore,
    cm : relationComoCm,
//    tbar: relationComoTbar,
//    viewConfig : {
//		getRowClass : function(record,rowIndex,rowParams,store){
//			//根据是否修改状态修改背景颜色  
//			if(record.data.IS_ADD_FLAG=='0'){//修改过
//			  	return 'my_row_set_blue';
//		  	}else if(record.data.IS_ADD_FLAG == '1'){//新增
//		  		return 'my_row_set_red';
//		  	}
//		}
//	},
    loadMask: {
        msg: '正在加载表格数据,请稍等...'
    }
});
relationComoStore.on('beforeload',function(){
	relationComoStore.baseParams = {
		custId : custId
	};
});
/////////////////////////////////////////主要固定资产信息////////////////////////////////
/**
 * 房产类型
 */
var fhtypeStore =  new Ext.data.Store( {
	restful : true,
	autoLoad : true,
	sortInfo : {
            field:'key',
            direction:'ASC'
        },
	proxy : new Ext.data.HttpProxy( {
		url : basepath + '/lookup.json?name=F_HTYPE'
	}),
	reader : new Ext.data.JsonReader( {
		root : 'JSON'
	},['key','value'])
});
/**
 * 持有类型
 */
var fotypeStore =  new Ext.data.Store( {
	restful : true,
	autoLoad : true,
	sortInfo : {
            field:'key',
            direction:'ASC'
        },
	proxy : new Ext.data.HttpProxy( {
		url : basepath + '/lookup.json?name=F_OTYPE'
	}),
	reader : new Ext.data.JsonReader( {
		root : 'JSON'
	},['key','value'])
});
/**
 * 使用情况
 */
var futypeStore =  new Ext.data.Store( {
	restful : true,
	autoLoad : true,
	sortInfo : {
            field:'key',
            direction:'ASC'
        },
	proxy : new Ext.data.HttpProxy( {
		url : basepath + '/lookup.json?name=F_UTYPE'
	}),
	reader : new Ext.data.JsonReader( {
		root : 'JSON'
	},['key','value'])
});
/**
 *主要固定资产展示列
 */
var fixedComoCm = new Ext.grid.ColumnModel([
    RowNumber,
    {dataIndex:'TASK_NUMBER',header:'拜访任务编号',width:150,sortable:true},
    {dataIndex:'F_HTYPE',header:'房产类型',width:150,sortable:true},
    {dataIndex:'F_OTYPE',header:'持有类型',width:150,sortable:true},
    {dataIndex:'F_AREA',header:'面积（平方米）',width:150,sortable:true},
    {dataIndex:'F_UTYPE',header:'使用状况',width:150,sortable:true},
    {dataIndex:'F_ASSESS',header:'估价（rmb/千元）',width:150,sortable:true},
    {dataIndex:'F_MEMO',header:'备注',width:150,sortable:true},
    {dataIndex:'F_HOLDER',header:'持有人',width:150,sortable:true},
    {dataIndex:'F_REGION',header:'所在区域',width:150,sortable:true},
    {dataIndex:'F_SECURED',header:'是否已抵押',width:150,sortable:true}
]);
/**
 * 主要固定资产record
 */
var fixedComoRecord = new Ext.data.Record.create([
		{name:'CUST_ID'},
		{name:'F_ID'},
		{name:'TASK_NUMBER'},
		{name:'F_HTYPE'},
		{name:'F_OTYPE'},
		{name:'F_AREA'},
		{name:'F_UTYPE'},
		{name:'F_ASSESS'},
		{name:'F_MEMO'},
		{name:'F_HOLDER'},
		{name:'F_REGION'},
		{name:'F_SECURED'},
		{name:'LAST_UPDATE_SYS'}, 
		{name:'LAST_UPDATE_USER'},
		{name:'LAST_UPDATE_TM'}
]);
/**
 * 主要固定资产store
 */
var fixedComoStore = new Ext.data.Store({
    restful:true,
    proxy: new Ext.data.HttpProxy({
        url: basepath + '/acrmFCiContmethInfo.json?check=1',
        method:'GET'
    }),
    reader: new Ext.data.JsonReader({
        root:'json.data',
        totalProperty:'json.count'
    },fixedComoRecord)
});

/**
 * 主要固定资产新增or修改form
 */
var fixedComoForm = new Ext.FormPanel({
    frame : true,
    autoScroll : true,
    split : true,
    items : [
		{xtype:'textfield',width:100,sortable:true,name:'F_ID',hidden:true},
		{xtype:'textfield',width:100,sortable:true,name:'TASK_NUMBER',fieldLabel : '<font color="red">*</font>拜访任务编号',anchor : '90%',allowBlank:false},
		{xtype:'combo',width:100,sortable:true,name:'F_HTYPE',fieldLabel : '<font color="red">*</font>房产类型',store : fhtypeStore,resizable : true,valueField : 'key',displayField : 'value',
			mode : 'local',forceSelection : true,triggerAction : 'all',anchor : '90%',allowBlank:false},
		{xtype:'combo',width:100,sortable:true,name:'F_OTYPE',fieldLabel : '<font color="red">*</font>持有类型',store : fotypeStore,resizable : true,valueField : 'key',displayField : 'value',
			mode : 'local',forceSelection : true,triggerAction : 'all',anchor : '90%',allowBlank:false},
		{xtype:'numberfield',width:100,sortable:true,name:'F_AREA',fieldLabel : '<font color="red">*</font>面积（平方米）',anchor : '90%'},
		{xtype:'combo',width:100,sortable:true,name:'F_UTYPE',fieldLabel : '<font color="red">*</font>使用情况',store : futypeStore,resizable : true,valueField : 'key',displayField : 'value',
			mode : 'local',forceSelection : true,triggerAction : 'all',anchor : '90%',allowBlank:false},
		{xtype:'numberfield',width:100,sortable:true,name:'F_ASSESS',fieldLabel : '<font color="red">*</font>估价（rmb/千元）',anchor : '90%',allowBlank:false},
		{xtype:'textfield',width:100,sortable:true,name:'F_MEMO',fieldLabel : '<font color="red">*</font>备注',anchor : '90%',allowBlank:false},
		{xtype:'textfield',width:100,sortable:true,name:'F_HOLDER',fieldLabel : '<font color="red">*</font>持有人',anchor : '90%',allowBlank:false},
		{xtype:'textfield',width:100,sortable:true,name:'F_REGION',fieldLabel : '<font color="red">*</font>所在区域',anchor : '90%',allowBlank:false},
		{xtype:'textfield',width:100,sortable:true,name:'F_SECURED',fieldLabel : '<font color="red">*</font>是否已抵押',anchor : '90%',allowBlank:false}
		    ]
});

/**
 * 主要固定资产新增or修改window
 */
var fixedComoWindow = new Ext.Window({
    title:'新增OR修改',
    width: 700,
    height: 450,
    layout: 'fit',
    closable: true,
    closeAction: 'hide',
    modal: true,
    buttonAlign : 'center',
    items : [fixedComoForm],
    buttons:[{
    	text:'暂存',
    	handler:function(){
    		if (!fixedComoForm.getForm().isValid()) {
                Ext.Msg.alert("提示", "输入有误或存在漏输项，请重新输入！");
                return false;
            }
            var tempJson = fixedComoForm.getForm().getValues(false);
       
            if(fixedComoWindow.title =='修改'){
            	var tempRowData = fixedGridPanel.getSelectionModel().getSelections()[0].data;
            	//移除当前修改数据
            	fixedComoStore.removeAt(fixedComoStore.findExact('F_ID', tempRowData.F_ID));     
            	tempRowData.TASK_NUMBER = tempJson.TASK_NUMBER;
            	tempRowData.F_HTYPE = tempJson.F_HTYPE;
            	tempRowData.F_HTYPE = fixedComoForm.getForm().findField('F_HTYPE').getRawValue();
            	tempRowData.F_OTYPE = tempJson.F_OTYPE;
            	tempRowData.F_OTYPE = fixedComoForm.getForm().findField('F_OTYPE').getRawValue();
            	tempRowData.F_UTYPE = tempJson.F_UTYPE;
            	tempRowData.F_UTYPE = fixedComoForm.getForm().findField('F_UTYPE').getRawValue();
            	tempRowData.F_AREA = tempJson.F_AREA;
            	tempRowData.F_ASSESS = tempJson.F_ASSESS;
            	tempRowData.F_MEMO = tempJson.F_MEMO;
            	tempRowData.F_HOLDER = tempJson.F_HOLDER;
            	tempRowData.F_REGION = tempJson.F_REGION;
            	tempRowData.F_SECURED = tempJson.F_SECURED;            	
            	tempRowData.LAST_UPDATE_SYS = 'CRM';
            	tempRowData.LAST_UPDATE_USER = JsContext._userId;
            	tempRowData.LAST_UPDATE_TM = _sysCurrDate;
            	tempRowData.IS_ADD_FLAG = Number(tempJson.F_ID) > 0?'0':'1';
            	var tempRecord = new Ext.data.Record(tempRowData,null);
            	fixedComoStore.addSorted(tempRecord);
            }else{
	            var tempRowData = {
	            	F_ID : -(new Date().getTime()),
//	            	CUST_ID : custId,
	            	CUST_ID : 111,
	            	TASK_NUMBER : tempJson.TASK_NUMBER,
	            	F_HTYPE : tempJson.F_HTYPE,
	            	F_HTYPE_ORA : fixedComoForm.getForm().findField('F_HTYPE').getRawValue(),
	            	F_OTYPE : tempJson.F_OTYPE,
	            	F_OTYPE_ORA : fixedComoForm.getForm().findField('F_OTYPE').getRawValue(),
	            	F_UTYPE : tempJson.F_UTYPE,
	            	F_UTYPE_ORA : fixedComoForm.getForm().findField('F_UTYPE').getRawValue(),
	            	F_AREA : tempJson.F_AREA,
	            	F_ASSESS : tempJson.F_ASSESS,
	            	F_MEMO :tempJson.F_MEMO,
	            	F_HOLDER : tempJson.F_HOLDER,
	            	F_REGION : tempJson.F_REGION,
	            	F_SECURED : tempJson.F_SECURED, 
	            	LAST_UPDATE_SYS : 'CRM',
	            	LAST_UPDATE_USER : JsContext._userId,
	            	LAST_UPDATE_TM : _sysCurrDate,//2014-10-29
	            	IS_ADD_FLAG : '1'
	            };
	            var tempRecord = new Ext.data.Record(tempRowData,null);
	            fixedComoStore.addSorted(tempRecord);
            }
            fixedComoStore.sort('F_HTYPE','ASC');
            fixedComoWindow.hide();
    	}
    },{
        text: '返回',
        handler: function(){
            fixedComoWindow.hide();
        }
    }]
});

/**
 * 主要固定资产信息按钮栏
 */
var fixedComoTbar = new Ext.Toolbar({
    items: [{
        text: '新增',
        hidden:false,
        id:'com_xz_2-fixed',
        handler: function(){
            if(fixedComoForm.getForm().getEl()){
                fixedComoForm.getForm().getEl().dom.reset();
            }
            fixedComoWindow.setTitle('新增');
            fixedComoForm.getForm().findField('F_HTYPE').setReadOnly(false);
            fixedComoForm.getForm().findField('F_HTYPE').removeClass('x-readOnly');
            fixedComoWindow.show();
        }
    },{
        text: '修改',
        hidden:false,
        id:'com_xg_2-fixed',
        handler: function(){
            var selectLength = fixedGridPanel.getSelectionModel().getSelections().length;
            var selectRecord = fixedGridPanel.getSelectionModel().getSelections()[0];
            if(selectLength != 1){
                Ext.Msg.alert('提示','请选择一条记录!');
                return false;
            }
            fixedComoWindow.setTitle('修改');
//            fixedComoForm.getForm().findField('F_HTYPE').setReadOnly(true);
//            fixedComoForm.getForm().findField('F_HTYPE').addClass('x-readOnly');
            fixedComoForm.getForm().loadRecord(selectRecord);
            fixedComoWindow.show();
        }
    },{
        text: '移除',
        hidden:false,
        id:'com_yc_2-fixed',
        handler: function(){
            var selectLength = fixedGridPanel.getSelectionModel().getSelections().length;
            var selectRecord = fixedGridPanel.getSelectionModel().getSelections()[0];
            if(selectLength != 1 || selectRecord.data.IS_ADD_FLAG != '1'){
                Ext.Msg.alert('提示','请选择一条新增暂存的记录!');
                return false;
            }
            fixedComoStore.remove(selectRecord);
        }
    }]
});

/**
 * 主要固定资产信息grid
 */
var fixedGridPanel = new Ext.grid.GridPanel({
	height: 180,
    region: 'center',
    autoScroll: true,
    stripeRows: true,
    store: fixedComoStore,
    cm : fixedComoCm,
//    tbar: fixedComoTbar,
//    viewConfig : {
//		getRowClass : function(record,rowIndex,rowParams,store){
//			//根据是否修改状态修改背景颜色  
//			if(record.data.IS_ADD_FLAG=='0'){//修改过
//			  	return 'my_row_set_blue';
//		  	}else if(record.data.IS_ADD_FLAG == '1'){//新增
//		  		return 'my_row_set_red';
//		  	}
//		}
//	},
    loadMask: {
        msg: '正在加载表格数据,请稍等...'
    }
});
fixedComoStore.on('beforeload',function(){
	relationComoStore.baseParams = {
		custId : custId
	};
});

/////////////////////////////////////////盈利获利情况信息////////////////////////////////

/**
 *盈利获利情况展示列
 */
var profitComoCm = new Ext.grid.ColumnModel([
    RowNumber,
    {dataIndex:'P_YEARS',header:'年份',width:150,sortable:true},
    {dataIndex:'P_YEARS_END',header:'结束年份',width:150,sortable:true},
    {dataIndex:'P_REVENUE',header:'营收',width:150,sortable:true},
    {dataIndex:'P_GROSS',header:'毛利率',width:150,sortable:true},
    {dataIndex:'P_PNET',header:'税后净利率',width:150,sortable:true},
    {dataIndex:'P_MEMO',header:'备注',width:150,sortable:true}
    
]);
/**
 * 盈利获利情况record
 */
var profitComoRecord = new Ext.data.Record.create([
		{name:'CUST_ID'},
		{name:'P_ID'},
		{name:'TASK_NUMBER'},
		{name:'P_YEARS'},
		{name:'P_REVENUE'},
		{name:'P_GROSS'},
		{name:'P_PNET'},
		{name:'P_MEMO'},
		{name:'P_YEARS_END'},
		{name:'LAST_UPDATE_SYS'}, 
		{name:'LAST_UPDATE_USER'},
		{name:'LAST_UPDATE_TM'}
]);
/**
 * 盈利获利情况store
 */
var profitComoStore = new Ext.data.Store({
    restful:true,
    proxy: new Ext.data.HttpProxy({
        url: basepath + '/acrmFCiContmethInfo.json?check=1',
        method:'GET'
    }),
    reader: new Ext.data.JsonReader({
        root:'json.data',
        totalProperty:'json.count'
    },profitComoRecord)
});

/**
 * 盈利获利情况新增or修改form
 */
var profitComoForm = new Ext.FormPanel({
    frame : true,
    autoScroll : true,
    split : true,
    items : [
             {xtype:'textfield',width:100,sortable:true,anchor:'90%',name:'P_ID',hidden:true},
             {xtype:'textfield',width:100,sortable:true,anchor:'90%',name:'P_YEARS',fieldLabel:'<font color="red">*</font>开始年份',allowBlank:false,regex:/^[0-9]{4}$/,regexText:'请输入4位数字'},
             {xtype:'textfield',width:100,sortable:true,anchor:'90%',name:'P_YEARS_END',fieldLabel:'<font color="red">*</font>结束年份',allowBlank:false,regex:/^[0-9]{4}$/,regexText:'请输入4位数字'},
             {xtype:'numberfield',width:100,sortable:true,anchor:'90%',name:'P_REVENUE',fieldLabel:'<font color="red">*</font>营收（人民币/千元）'},
             {xtype:'numberfield',width:100,sortable:true,anchor:'90%',name:'P_GROSS',fieldLabel:'<font color="red">*</font>毛利率(%)'},
             {xtype:'numberfield',width:100,sortable:true,anchor:'90%',name:'P_PNET',fieldLabel:'<font color="red">*</font>税后净利率（%）'},
             {xtype:'textfield',width:100,sortable:true,anchor:'90%',name:'P_MEMO',fieldLabel:'备注'}
          ]
});

/**
 * 盈利获利情况新增or修改window
 */
var profitComoWindow = new Ext.Window({
    title:'新增OR修改',
    width: 600,
    height: 300,
    layout: 'fit',
    closable: true,
    closeAction: 'hide',
    modal: true,
    buttonAlign : 'center',
    items : [profitComoForm],
    buttons:[{
    	text:'暂存',
    	handler:function(){
    		if (!profitComoForm.getForm().isValid()) {
                Ext.Msg.alert("提示", "输入有误或存在漏输项，请重新输入！");
                return false;
            }
            var tempJson = profitComoForm.getForm().getValues(false);
          
            if(profitComoWindow.title =='修改'){
            	var tempRowData = profitGridPanel.getSelectionModel().getSelections()[0].data;
            	//移除当前修改数据
            	profitComoStore.removeAt(profitComoStore.findExact('P_ID', tempRowData.P_ID));     
            	tempRowData.P_YEARS = tempJson.P_YEARS;
            	tempRowData.P_YEARS_END = tempJson.P_YEARS_END;
            	tempRowData.P_REVENUE = tempJson.P_REVENUE;
            	tempRowData.P_GROSS = tempJson.P_GROSS;
            	tempRowData.P_PNET = tempJson.P_PNET;
            	tempRowData.P_MEMO = tempJson.P_MEMO;
            	tempRowData.LAST_UPDATE_SYS = 'CRM';
            	tempRowData.LAST_UPDATE_USER = JsContext._userId;
            	tempRowData.LAST_UPDATE_TM = _sysCurrDate;
            	tempRowData.IS_ADD_FLAG = Number(tempJson.P_ID) > 0?'0':'1';
            	var tempRecord = new Ext.data.Record(tempRowData,null);
            	profitComoStore.addSorted(tempRecord);
            }else{
	            var tempRowData = {
	            	P_ID : -(new Date().getTime()),
//	            	CUST_ID : custId,
	            	CUST_ID : 111,
	            	P_YEARS : tempJson.P_YEARS,
	            	P_YEARS_END : tempJson.P_YEARS_END,
	            	P_REVENUE : tempJson.P_REVENUE,
	            	P_GROSS: tempJson.P_GROSS,
	            	P_PNET :tempJson.P_PNET,
	            	P_MEMO :tempJson.P_MEMO,
	            	LAST_UPDATE_SYS : 'CRM',
	            	LAST_UPDATE_USER : JsContext._userId,
	            	LAST_UPDATE_TM : _sysCurrDate,//2014-10-29
	            	IS_ADD_FLAG : '1'
	            };
	            var tempRecord = new Ext.data.Record(tempRowData,null);
	            profitComoStore.addSorted(tempRecord);
            }
            profitComoStore.sort('P_YEARS','ASC');
            profitComoWindow.hide();
    	}
    },{
        text: '返回',
        handler: function(){
            profitComoWindow.hide();
        }
    }]
});

/**
 * 盈利获利情况信息按钮栏
 */
var profitComoTbar = new Ext.Toolbar({
    items: [{
        text: '新增',
        hidden:false,
        id:'com_xz_5',
        handler: function(){
            if(profitComoForm.getForm().getEl()){
                profitComoForm.getForm().getEl().dom.reset();
            }
            profitComoWindow.setTitle('新增');
            profitComoForm.getForm().findField('P_YEARS').setReadOnly(false);
            profitComoForm.getForm().findField('P_YEARS').removeClass('x-readOnly');
            profitComoWindow.show();
        }
    },{
        text: '修改',
        hidden:false,
        id:'com_xg_5-yl',
        handler: function(){
            var selectLength = profitGridPanel.getSelectionModel().getSelections().length;
            var selectRecord = profitGridPanel.getSelectionModel().getSelections()[0];
            if(selectLength != 1){
                Ext.Msg.alert('提示','请选择一条记录!');
                return false;
            }
        	
            profitComoWindow.setTitle('修改');
//            profitComoForm.getForm().findField('P_YEARS').setReadOnly(true);
//            profitComoForm.getForm().findField('P_YEARS').addClass('x-readOnly');
            profitComoForm.getForm().loadRecord(selectRecord);
            profitComoWindow.show();
        }
    },{
        text: '移除',
        hidden:false,
        id:'com_yc_5-yl',
        handler: function(){
            var selectLength = profitGridPanel.getSelectionModel().getSelections().length;
            var selectRecord = profitGridPanel.getSelectionModel().getSelections()[0];
            if(selectLength != 1 || selectRecord.data.IS_ADD_FLAG != '1'){
                Ext.Msg.alert('提示','请选择一条新增暂存的记录!');
                return false;
            }
            profitComoStore.remove(selectRecord);
        }
    }]
});

/**
 * 盈利获利情况信息grid
 */
var profitGridPanel = new Ext.grid.GridPanel({
	height: 180,
    region: 'center',
    autoScroll: true,
    stripeRows: true,
    store: profitComoStore,
    cm : profitComoCm,
//    tbar: profitComoTbar,
//    viewConfig : {
//		getRowClass : function(record,rowIndex,rowParams,store){
//			//根据是否修改状态修改背景颜色  
//			if(record.data.IS_ADD_FLAG=='0'){//修改过
//			  	return 'my_row_set_blue';
//		  	}else if(record.data.IS_ADD_FLAG == '1'){//新增
//		  		return 'my_row_set_red';
//		  	}
//		}
//	},
    loadMask: {
        msg: '正在加载表格数据,请稍等...'
    }
});
profitComoStore.on('beforeload',function(){
	relationComoStore.baseParams = {
		custId : custId
	};
});

/////////////////////////////////////////往来银行信息////////////////////////////////
/**
 * 往来银行类型
 */
var communiStore =  new Ext.data.Store( {
	restful : true,
	autoLoad : true,
	sortInfo : {
            field:'key',
            direction:'ASC'
        },
	proxy : new Ext.data.HttpProxy( {
		url : basepath + '/lookup.json?name=COM_BANK'
	}),
	reader : new Ext.data.JsonReader( {
		root : 'JSON'
	},['key','value'])
});
/**
 *往来银行展示列
 */
var commiComoCm = new Ext.grid.ColumnModel([
    RowNumber,
    {dataIndex:'COMMUNI_TYPE',header:'往来类型',width:150,sortable:true},
    {dataIndex:'BANKNAME',header:'往来银行',width:150,sortable:true},
    {dataIndex:'AVGDEPOSIT',header:'平均存款量(人民币/千元)',width:150,sortable:true}
]);
/**
 * 往来银行record
 */
var commiComoRecord = new Ext.data.Record.create([
		{name:'CUST_ID'},
		{name:'COMMUNI_ID'},
		{name:'COMMUNI_TYPE'},
		{name:'BANKNAME'},
		{name:'AVGDEPOSIT'},
		{name:'LAST_UPDATE_SYS'}, 
		{name:'LAST_UPDATE_USER'},
		{name:'LAST_UPDATE_TM'}
]);
/**
 * 往来银行store
 */
var commiComoStore = new Ext.data.Store({
    restful:true,
    proxy: new Ext.data.HttpProxy({
        url: basepath + '/acrmFCiContmethInfo.json?check=1',
        method:'GET'
    }),
    reader: new Ext.data.JsonReader({
        root:'json.data',
        totalProperty:'json.count'
    },commiComoRecord)
});

/**
 * 往来银行新增or修改form
 */
var commiComoForm = new Ext.FormPanel({
    frame : true,
    autoScroll : true,
    split : true,
    items : [
             {xtype:'textfield',sortable:true,anchor:'90%',name:'COMMUNI_ID',hidden:true},
             {xtype:'combo',sortable:true,anchor:'90%',name:'COMMUNI_TYPE',fieldLabel:'<font color="red">*</font>往来类型',store : communiStore,resizable : true,valueField : 'key',displayField : 'value',
     			mode : 'local',forceSelection : true,triggerAction : 'all',allowBlank:false},
             {xtype:'textfield',sortable:true,anchor:'90%',anchor:'90%',name:'BANKNAME',fieldLabel:'<font color="red">*</font>往来银行',allowBlank:false},
             {xtype:'numberfield',sortable:true,anchor:'90%',anchor:'90%',name:'AVGDEPOSIT',fieldLabel:'<font color="red">*</font>平均存款量(人民币/千元)',allowBlank:false}]
});

/**
 * 往来银行新增or修改window
 */
var commiComoWindow = new Ext.Window({
    title:'新增OR修改',
    width: 600,
    height: 300,
    layout: 'fit',
    closable: true,
    closeAction: 'hide',
    modal: true,
    buttonAlign : 'center',
    items : [commiComoForm],
    buttons:[{
    	text:'暂存',
    	handler:function(){
    		if (!commiComoForm.getForm().isValid()) {
                Ext.Msg.alert("提示", "输入有误或存在漏输项，请重新输入！");
                return false;
            }
            var tempJson = commiComoForm.getForm().getValues(false);
            var validFlag = false;//判断类型是否已存在,由于不允许修改类型,故不作与原值比较
            commiComoStore.each(function(record){
				if(record.data.COMMUNI_TYPE == tempJson.COMMUNI_TYPE 
					&& (commiComoWindow.title !='修改')){
					validFlag = true;
					return;
				}
	        });
	        if(validFlag){
	        	Ext.Msg.alert("提示", commiComoForm.form.findField('COMMUNI_TYPE').lastSelectionText+"已存在，请重新输入！");
	            return false;
	        }
            if(commiComoWindow.title =='修改'){
            	var tempRowData = commiGridPanel.getSelectionModel().getSelections()[0].data;
            	//移除当前修改数据
            	commiComoStore.removeAt(commiComoStore.findExact('COMMUNI_ID', tempRowData.COMMUNI_ID));
            	tempRowData.COMMUNI_TYPE = tempJson.COMMUNI_TYPE;
            	tempRowData.COMMUNI_TYPE_ORA = commiComoForm.getForm().findField('COMMUNI_TYPE').getRawValue();
            	tempRowData.BANKNAME = tempJson.BANKNAME;
            	tempRowData.AVGDEPOSIT = tempJson.AVGDEPOSIT;
            	tempRowData.LAST_UPDATE_SYS = 'CRM';
            	tempRowData.LAST_UPDATE_USER = JsContext._userId;
            	tempRowData.LAST_UPDATE_TM = _sysCurrDate;
            	tempRowData.IS_ADD_FLAG = Number(tempJson.COMMUNI_ID) > 0?'0':'1';
            	var tempRecord = new Ext.data.Record(tempRowData,null);
            	commiComoStore.addSorted(tempRecord);
            }else{
	            var tempRowData = {
	            	COMMUNI_ID : -(new Date().getTime()),
//	            	CUST_ID : custId,
	            	CUST_ID : 111,
	            	COMMUNI_TYPE : tempJson.COMMUNI_TYPE,
	            	COMMUNI_TYPE_ORA : commiComoForm.getForm().findField('COMMUNI_TYPE').getRawValue(),
	            	BANKNAME : tempJson.BANKNAME,
	            	AVGDEPOSIT : tempJson.AVGDEPOSIT,
	            	LAST_UPDATE_SYS : 'CRM',
	            	LAST_UPDATE_USER : JsContext._userId,
	            	LAST_UPDATE_TM : _sysCurrDate,//2014-10-29
	            	IS_ADD_FLAG : '1'
	            };
	            var tempRecord = new Ext.data.Record(tempRowData,null);
	            commiComoStore.addSorted(tempRecord);
            }
            commiComoStore.sort('COMMUNI_TYPE','ASC');
            commiComoWindow.hide();
    	}
    },{
        text: '返回',
        handler: function(){
            commiComoWindow.hide();
        }
    }]
});

/**
 * 往来银行信息按钮栏
 */
var commiComoTbar = new Ext.Toolbar({
    items: [{
        text: '新增',
        hidden:false,
        id:'com_xz_5-w',
        handler: function(){
            if(commiComoForm.getForm().getEl()){
                commiComoForm.getForm().getEl().dom.reset();
            }
            commiComoWindow.setTitle('新增');
            commiComoForm.getForm().findField('COMMUNI_TYPE').setReadOnly(false);
            commiComoForm.getForm().findField('COMMUNI_TYPE').removeClass('x-readOnly');
            commiComoWindow.show();
        }
    },{
        text: '修改',
        hidden:false,
        id:'com_xg_5-w',
        handler: function(){
            var selectLength = commiGridPanel.getSelectionModel().getSelections().length;
            var selectRecord = commiGridPanel.getSelectionModel().getSelections()[0];
            if(selectLength != 1){
                Ext.Msg.alert('提示','请选择一条记录!');
                return false;
            }
            commiComoWindow.setTitle('修改');
            commiComoForm.getForm().findField('COMMUNI_TYPE').setReadOnly(true);
            commiComoForm.getForm().findField('COMMUNI_TYPE').addClass('x-readOnly');
            commiComoForm.getForm().loadRecord(selectRecord);
            commiComoWindow.show();
        }
    },{
        text: '移除',
        hidden:false,
        id:'com_yc_5-w',
        handler: function(){
            var selectLength = commiGridPanel.getSelectionModel().getSelections().length;
            var selectRecord = commiGridPanel.getSelectionModel().getSelections()[0];
            if(selectLength != 1 || selectRecord.data.IS_ADD_FLAG != '1'){
                Ext.Msg.alert('提示','请选择一条新增暂存的记录!');
                return false;
            }
            commiComoStore.remove(selectRecord);
        }
    }]
});

/**
 * 往来银行信息grid
 */
var commiGridPanel = new Ext.grid.GridPanel({
	height: 180,
    region: 'center',
    autoScroll: true,
    stripeRows: true,
    store: commiComoStore,
    cm : commiComoCm,
//    tbar: commiComoTbar,
//    viewConfig : {
//		getRowClass : function(record,rowIndex,rowParams,store){
//			//根据是否修改状态修改背景颜色  
//			if(record.data.IS_ADD_FLAG=='0'){//修改过
//			  	return 'my_row_set_blue';
//		  	}else if(record.data.IS_ADD_FLAG == '1'){//新增
//		  		return 'my_row_set_red';
//		  	}
//		}
//	},
    loadMask: {
        msg: '正在加载表格数据,请稍等...'
    }
});
commiComoStore.on('beforeload',function(){
	relationComoStore.baseParams = {
		custId : custId
	};
});