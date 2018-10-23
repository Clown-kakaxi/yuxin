/**
 * 对私客户  非授信信息维护页面
 * 2014-08-06 luyy
 * 2014-08-16 helin 继续开发...
 */
var _sysCurrDate = new Date().format('Y-m-d');
//临时存储地址数据
var tempAddrStore = new Ext.data.Store();
var tempContactPersonStore = new Ext.data.Store();
var tempContactInfoStore = new Ext.data.Store();




var noChinaPer=function(value){
	var flag=true;
	if (/[\u4E00-\u9FA5]/i.test(value)) {
		Ext.Msg.alert("提示", "该种联系方式内容不允许包括中文字符或全角符号，如有特殊需要请标注在备注栏");
		flag= false;
	};
	if (value.length > 0) {
		for (var i = value.length - 1; i >= 0; i--) {
			unicode = value.charCodeAt(i);
			if (unicode > 65280 && unicode < 65375) {
				Ext.Msg.alert("提示", "该种联系方式内容不允许包括中文字符或全角符号，如有特殊需要请标注在备注栏");
				flag = false;
				break;
			}
		}
	}
	return flag;
};
var noChinaPer2=function(value,type){
	var flag=true;
	if (/[\u4E00-\u9FA5]/i.test(value)) {
		switch(type){
			case  'TEL':
			Ext.Msg.alert("提示", "联系电话不允许包括中文字符或全角符号");
			break;
			case  'TEL2':
			Ext.Msg.alert("提示", "联系电话2不允许包括中文字符或全角符号");
			break;
			case  'EMAIL':
			Ext.Msg.alert("提示", "电子邮件不允许包括中文字符或全角符号");
			break;
			case  'MOBILE':
			Ext.Msg.alert("提示", "手机号码不允许包括中文字符或全角符号");
			break;
			case  'MOBILE2':
			Ext.Msg.alert("提示", "手机号码2不允许包括中文字符或全角符号");
			break;
			default:;
		}
		
		flag= false;
	};
	if (value.length > 0) {
		for (var i = value.length - 1; i >= 0; i--) {
			unicode = value.charCodeAt(i);
			if (unicode > 65280 && unicode < 65375) {
				switch(type){
			        case  'TEL':
					Ext.Msg.alert("提示", "联系电话不允许包括中文字符或全角符号");
					break;
					case  'TEL2':
					Ext.Msg.alert("提示", "联系电话2不允许包括中文字符或全角符号");
					break;
					case  'EMAIL':
					Ext.Msg.alert("提示", "电子邮件不允许包括中文字符或全角符号");
					break;
					case  'MOBILE':
					Ext.Msg.alert("提示", "手机号码不允许包括中文字符或全角符号");
					break;
					case  'MOBILE2':
					Ext.Msg.alert("提示", "手机号码2不允许包括中文字符或全角符号");
					break;
					default:;
				}
				flag = false;
				break;
			}
		}
	}
	return flag;
};

/**
 * 地址类型
 */
var addrTypeStore =  new Ext.data.Store({
	restful : true,
	autoLoad : true,
	sortInfo : {
        field:'key',
        direction:'ASC'
    },
	proxy : new Ext.data.HttpProxy( {
		url : basepath + '/lookup.json?name=XD000192_S'
	}),
	reader : new Ext.data.JsonReader( {
		root : 'JSON'
	},['key','value'])
});
var addrCustInfoRowNumber = new Ext.grid.RowNumberer({
    header:'NO.',
    width:35
});
// create the data columnModel
var addrCustInfoCm = new Ext.grid.ColumnModel([
    addrCustInfoRowNumber,
    {dataIndex:'ADDR_TYPE_ORA',header:'地址类型',width : 120,sortable : true},
    {dataIndex:'ADDR',header:'详细地址',width : 350,sortable : true},
    {dataIndex:'ZIPCODE',header:'邮政编码',width : 100,sortable : true},
    {dataIndex:'LAST_UPDATE_SYS',header:'最后更新系统',width : 100,sortable : true}
]);
// create the data record
var addrCustInfoRecord = new Ext.data.Record.create([
    {name:'ADDR_ID'},
	{name:'CUST_ID'},
	{name:'ADDR_TYPE'},
	{name:'ADDR_TYPE_ORA'},
	{name:'ADDR'},
	{name:'ZIPCODE'},
	{name:'LAST_UPDATE_SYS'},
	{name:'LAST_UPDATE_USER'},
	{name:'LAST_UPDATE_TM'},
	{name:'IS_ADD_FLAG'}
]);
// create the data store
var addrCustInfoStore = new Ext.data.Store({
    restful:true,
    proxy: new Ext.data.HttpProxy({
        url: basepath + '/dealWithFsx!queryAddr.json',
        method:'GET'
    }),
    reader: new Ext.data.JsonReader({
        root:'json.data',
        totalProperty:'json.count'
    },addrCustInfoRecord)
    
});

// create the maintain formpanel
var maintainForm = new Ext.FormPanel({
    frame : true,
    autoScroll : true,
    split : true,
    items : [
        {xtype:'textfield',name:'ADDR_ID',hidden:true},
        {xtype:'textfield',name:'CUST_ID',hidden:true},
        {xtype:'textfield',name:'LAST_UPDATE_SYS',hidden:true},
        {xtype : 'combo',name : 'ADDR_TYPE',hiddenName : 'ADDR_TYPE',fieldLabel : '<font color="red">*</font>地址类型',store : addrTypeStore,resizable : true,valueField : 'key',displayField : 'value',
			mode : 'local',forceSelection : true,triggerAction : 'all',anchor : '90%',allowBlank:false},
		{xtype:'textfield',name:'ZIPCODE',fieldLabel: '邮政编码',anchor:'90%',maxLength:50},
        {xtype:'textarea',name:'ADDR',fieldLabel: '<font color="red">*</font>地址',anchor:'90%',allowBlank:false,maxLength:100},
    	{xtype:'displayfield',name: 'QTIPS',fieldLabel:'<font color="red">注</font>',anchor:'90%'
    		,value:'<font color="red">“联系地址”、“注册地址”为原核心系统地址类型！\n</font>'}
    ]
});

// create the maintain window
var maintainWindow = new Ext.Window({
    title:'新增OR修改',
    width: 500,
    height: 260,
    layout: 'fit',
    closable: true,
    closeAction: 'hide',
    modal: true,
    buttonAlign : 'center',
    items : [maintainForm],
    buttons:[{
    	text:'暂存',
    	handler:function(){
    		if (!maintainForm.getForm().isValid()) {
                Ext.Msg.alert("提示", "输入有误或存在漏输项，请重新输入！");
                return false;
            }
            var tempJson = maintainForm.getForm().getValues(false);
            var validFlag = false;//判断类型是否已存在,由于不允许修改类型,故不作与原值比较
	        addrCustInfoStore.each(function(record){
				if(record.data.ADDR_TYPE == tempJson.ADDR_TYPE 
					&& (tempJson.ADDR_ID === undefined || tempJson.ADDR_ID === "")){
					validFlag = true;
					return;
				}
				if(record.data.ADDR_TYPE == tempJson.ADDR_TYPE && tempJson.ADDR_ID != undefined 
					&& tempJson.ADDR_ID != "" && record.data.ADDR_ID != tempJson.ADDR_ID){
					validFlag = true;
					return;
				}
	        });
	        if(validFlag){
	        	Ext.Msg.alert("提示", "地址类型已存在，请重新输入！");
	            return false;
	        }
            if(maintainWindow.title =='修改'){
            	var tempRowData = addrGridPanel.getSelectionModel().getSelections()[0].data;
            	//移除当前修改数据
            	addrCustInfoStore.removeAt(addrCustInfoStore.findExact('ADDR_ID', tempRowData.ADDR_ID));
            	tempRowData.ADDR_TYPE = tempJson.ADDR_TYPE;
            	tempRowData.ADDR_TYPE_ORA = maintainForm.getForm().findField('ADDR_TYPE').getRawValue();
            	tempRowData.ZIPCODE = tempJson.ZIPCODE;
            	tempRowData.ADDR = tempJson.ADDR;
            	tempRowData.LAST_UPDATE_SYS = 'CRM';
            	tempRowData.LAST_UPDATE_USER = JsContext._userId;
            	tempRowData.LAST_UPDATE_TM = _sysCurrDate;
            	tempRowData.IS_ADD_FLAG = Number(tempJson.ADDR_ID) > 0?'0':'1';
            	var tempRecord = new Ext.data.Record(tempRowData,null);
            	addrCustInfoStore.addSorted(tempRecord);
            }else{
	            var tempRowData = {
	            	ADDR_ID : -(new Date().getTime()),
	            	CUST_ID : custId,
	            	ADDR_TYPE : tempJson.ADDR_TYPE,
	            	ADDR_TYPE_ORA : maintainForm.getForm().findField('ADDR_TYPE').getRawValue(),
	            	ZIPCODE : tempJson.ZIPCODE,
	            	ADDR : tempJson.ADDR,
	            	LAST_UPDATE_SYS : 'CRM',
	            	LAST_UPDATE_USER : JsContext._userId,
	            	LAST_UPDATE_TM : _sysCurrDate,//2014-10-29
	            	IS_ADD_FLAG : '1'
	            };
	            var tempRecord = new Ext.data.Record(tempRowData,null);
	            addrCustInfoStore.addSorted(tempRecord);
            }
            addrCustInfoStore.sort('ADDR_TYPE','ASC');
            maintainWindow.hide();
    	}
    },{
        text: '返回',
        handler: function(){
            maintainWindow.hide();
        }
    }]
});
//create the toolbar
var addrCustInfoTbar = new Ext.Toolbar({
    items: [{
        text: '新增',
        hidden:true,
        id:'per_fsx_2-11',
        handler: function(){
            if(maintainForm.getForm().getEl()){
                maintainForm.getForm().getEl().dom.reset();
            }
            maintainWindow.setTitle('新增');
            maintainForm.getForm().findField('ADDR_TYPE').setReadOnly(false);
            maintainForm.getForm().findField('ADDR_TYPE').removeClass('x-readOnly');
            maintainWindow.show();
        }
    },{
        text: '修改',
        hidden:true,
        id:'per_fsx_2-12',
        handler: function(){
            var selectLength = addrGridPanel.getSelectionModel().getSelections().length;
            var selectRecord = addrGridPanel.getSelectionModel().getSelections()[0];
            if(selectLength != 1){
                Ext.Msg.alert('提示','请选择一条记录!');
                return false;
            }
            if(selectRecord.data.ADDR_TYPE!=01 && selectRecord.data.ADDR_TYPE!=04){
        		Ext.Msg.alert("提示", "只允许新增和修改 居住地址、邮寄地址");
	            return false;
        	}
            maintainWindow.setTitle('修改');
            maintainForm.getForm().findField('ADDR_TYPE').setReadOnly(true);
            maintainForm.getForm().findField('ADDR_TYPE').addClass('x-readOnly');
            maintainForm.getForm().loadRecord(selectRecord);
            maintainWindow.show();
        }
    },{
        text: '移除',
        hidden:true,
        id:'per_fsx_2-13',
        handler: function(){
            var selectLength = addrGridPanel.getSelectionModel().getSelections().length;
            var selectRecord = addrGridPanel.getSelectionModel().getSelections()[0];
            if(selectLength != 1 || selectRecord.data.IS_ADD_FLAG != '1'){
                Ext.Msg.alert('提示','请选择一条新增暂存的记录!');
                return false;
            }
            addrCustInfoStore.remove(selectRecord);
        }
    }]
});

// create the addrGridPanel
var addrGridPanel = new Ext.grid.GridPanel({
	height: 180,
    region: 'center',
    autoScroll: true,
    stripeRows: true,
    store: addrCustInfoStore,
    cm : addrCustInfoCm,
    tbar: addrCustInfoTbar,
    viewConfig : {
		getRowClass : function(record,rowIndex,rowParams,store){
			//根据是否修改状态修改背景颜色  
			if(record.data.IS_ADD_FLAG=='0'){//修改过
			  	return 'my_row_set_blue';
		  	}else if(record.data.IS_ADD_FLAG == '1'){//新增
		  		return 'my_row_set_red';
		  	}
		}
	},
    loadMask: {
        msg: '正在加载表格数据,请稍等...'
    }
});
addrCustInfoStore.on('beforeload',function(){
	addrCustInfoStore.baseParams = {
		custId : custId
	};
});

/////////////////////////////////////////联系人信息////////////////////////////////
/**
 * 联系方式类型
 */
var identTypeStore =  new Ext.data.Store( {
	restful : true,
	autoLoad : true,
	sortInfo : {
        field:'key',
        direction:'ASC'
    },
	proxy : new Ext.data.HttpProxy( {
		url : basepath + '/lookup.json?name=XD000040'
	}),
	reader : new Ext.data.JsonReader( {
		root : 'JSON'
	},['key','value'])
});
var genderTypeStore = new Ext.data.Store({
	restful : true,
	autoLoad : true,
	sortInfo : {
        field:'key',
        direction:'ASC'
    },
	proxy : new Ext.data.HttpProxy( {
		url : basepath + '/lookup.json?name=XD000016'
	}),
	reader : new Ext.data.JsonReader( {
		root : 'JSON'
	},['key','value'])
});
var linkTypeStore = new Ext.data.Store({
	restful : true,
	autoLoad : true,
	sortInfo : {
        field:'key',
        direction:'ASC'
    },
	proxy : new Ext.data.HttpProxy( {
		url : basepath + '/lookup.json?name=XD000195'
	}),
	reader : new Ext.data.JsonReader( {
		root : 'JSON'
	},['key','value'])
}); 
var contactPersonRowNumber = new Ext.grid.RowNumberer({
    header:'NO.',
    width:35
});
// create the data columnModel
var contactPersonCm = new Ext.grid.ColumnModel([
    contactPersonRowNumber,
    {dataIndex:'LINKMAN_TYPE_ORA',header:'联系人类型',width : 100,sortable : true},
    {dataIndex:'LINKMAN_NAME',header:'姓名',width : 100,sortable : true},
    {dataIndex:'IDENT_TYPE_ORA',header:'证件类型',width : 100,sortable : true},
    {dataIndex:'IDENT_NO',header:'证件号码',width : 100,sortable : true},
    {dataIndex:'GENDER_ORA',header:'性别',width : 100,sortable : true},
    {dataIndex:'BIRTHDAY',header:'出生日期',width : 100,sortable : true},
    {dataIndex:'TEL',header:'联系电话',width : 100,sortable : true},
    {dataIndex:'TEL2',header:'联系电话2',width : 100,sortable : true},
    {dataIndex:'MOBILE',header:'手机号码',width : 100,sortable : true},
    {dataIndex:'MOBILE2',header:'手机号码2',width : 100,sortable : true},
    {dataIndex:'EMAIL',header:'邮件',width : 100,sortable : true},
    {dataIndex:'ADDRESS',header:'地址',width : 100,sortable : true},
    {dataIndex:'LAST_UPDATE_SYS',header:'最后更新系统',width : 100,sortable : true}
]);
// create the data record
var contactPersonRecord = new Ext.data.Record.create([
    {name : 'LINKMAN_ID'},
	{name : 'CUST_ID'},
	{name : 'LINKMAN_TYPE'},
	{name : 'LINKMAN_TYPE_ORA'},
	{name : 'LINKMAN_NAME'}, 
	{name : 'IDENT_TYPE'},
	{name : 'IDENT_TYPE_ORA'},
	{name : 'IDENT_NO'},
	{name : 'TEL'},
	{name : 'TEL2'},
	{name : 'MOBILE'},
	{name : 'MOBILE2'},
	{name : 'EMAIL'},
	{name : 'ADDRESS'},
	{name : 'GENDER'},
	{name : 'GENDER_ORA'},
	{name : 'BIRTHDAY'},
	{name : 'LAST_UPDATE_SYS'},
	{name : 'LAST_UPDATE_USER'},
	{name : 'LAST_UPDATE_TM'}
]);
// create the data store
var contactPersonStore = new Ext.data.Store({
    restful:true,
    proxy: new Ext.data.HttpProxy({
        url: basepath+'/dealWithFsx!queryPerContactPerson.json',
        method:'GET'
    }),
    reader: new Ext.data.JsonReader({
        root:'json.data',
        totalProperty:'json.count'
    },contactPersonRecord)
});

// create the contactPerson formpanel
var contactPersonForm = new Ext.FormPanel({
    frame : true,
    autoScroll : true,
    split : true,
    items : [{
    	layout:'column',
    	items:[{
        	columnWidth : .5,
			layout : 'form',
			items :[
		    	{xtype:'textfield',name:'LINKMAN_ID',hidden:true,anchor:'95%'},
		        {xtype:'textfield',name:'CUST_ID',hidden:true,anchor:'95%'},
		        {xtype : 'combo',name : 'LINKMAN_TYPE',hiddenName : 'LINKMAN_TYPE',fieldLabel : '<font color="red">*</font>联系人类型',store : linkTypeStore,resizable : true,valueField : 'key',displayField : 'value',
					mode : 'local',forceSelection : true,triggerAction : 'all',anchor : '90%',allowBlank:false},
		        {xtype : 'combo',name : 'IDENT_TYPE',hiddenName : 'IDENT_TYPE',fieldLabel : '证件类型',store : identTypeStore,resizable : true,valueField : 'key',displayField : 'value',
					mode : 'local',forceSelection : true,triggerAction : 'all',anchor : '90%'},
				{xtype : 'combo',name : 'GENDER',hiddenName : 'GENDER',fieldLabel : '性别',store : genderTypeStore,resizable : true,valueField : 'key',displayField : 'value',
					mode : 'local',forceSelection : true,triggerAction : 'all',anchor : '90%'},
		        {xtype:'textfield',name:'TEL',fieldLabel: '联系电话',anchor:'90%',maxLength:20},
		        {xtype:'textfield',name:'TEL2',fieldLabel: '联系电话2',anchor:'90%',maxLength:20},
		        {xtype:'textfield',name:'EMAIL',fieldLabel: '电子邮件',anchor:'90%',vtype:'email',maxLength:40}
    		]
    	},{
        	columnWidth : .5,
			layout : 'form',
			items :[
				{xtype:'textfield',name:'LINKMAN_NAME',fieldLabel: '<font color="red">*</font>姓名',anchor:'90%',allowBlank:false},
				{xtype:'textfield',name:'IDENT_NO',fieldLabel: '证件号码',anchor:'90%',maxLength:40},
				{xtype:'datefield',name:'BIRTHDAY',fieldLabel: '出生日期',format:'Y-m-d',anchor:'90%'},
				{xtype:'textfield',name:'MOBILE',fieldLabel: '手机号码',anchor:'90%',vtype:'telephone',maxLength:20},
				{xtype:'textfield',name:'MOBILE2',fieldLabel: '手机号码2',anchor:'90%',vtype:'telephone',maxLength:20}
				
    		]
    	}]
    },
    {xtype:'textarea',name:'ADDRESS',fieldLabel: '地址',anchor:'95%',maxLength:100},
    {xtype:'displayfield',name: 'QTIPS',fieldLabel:'<font color="red">注</font>',anchor:'90%'
    	,value:'<font color="red">“联系人1”、“联系人2”、“联系人3”为原核心系统联系人类型！\n</font>'}
    ]
});

// create the contactPerson window
var contactPersonWindow = new Ext.Window({
    title:'新增OR修改',
    width: 700,
    height: 380,
    layout: 'fit',
    closable: true,
    closeAction: 'hide',
    modal: true,
    buttonAlign : 'center',
    items : [contactPersonForm],
    buttons:[{
    	text:'暂存',
    	handler:function(){
    		if (!contactPersonForm.getForm().isValid()) {
                Ext.Msg.alert("提示", "输入有误或存在漏输项，请重新输入！");
                return false;
            }
            var TEL=contactPersonForm.getForm().findField('TEL').getValue();
            var TEL2=contactPersonForm.getForm().findField('TEL2').getValue();
            var EMAIL=contactPersonForm.getForm().findField('EMAIL').getValue();
            var MOBILE=contactPersonForm.getForm().findField('MOBILE').getValue();
            var MOBILE2=contactPersonForm.getForm().findField('MOBILE2').getValue();
            if(!noChinaPer2(TEL,'TEL')){
            	return false;
            }
            if(!noChinaPer2(TEL2,'TEL2')){
            	return false;
            }
            if(!noChinaPer2(EMAIL,'EMAIL')){
            	return false;
            }
            if(!noChinaPer2(MOBILE,'MOBILE')){
            	return false;
            }
            if(!noChinaPer2(MOBILE2,'MOBILE2')){
            	return false;
            }
            var tempJson = contactPersonForm.getForm().getValues(false);
            var validFlag = false;//判断类型是否已存在,由于不允许修改类型,故不作与原值比较
	        contactPersonStore.each(function(record){
				if(record.data.LINKMAN_TYPE == tempJson.LINKMAN_TYPE 
					&& (tempJson.LINKMAN_ID === undefined || tempJson.LINKMAN_ID === "")){
					validFlag = true;
					return;
				}
				if(record.data.LINKMAN_TYPE == tempJson.LINKMAN_TYPE && tempJson.LINKMAN_ID != undefined 
					&& tempJson.LINKMAN_ID != "" && record.data.LINKMAN_ID != tempJson.LINKMAN_ID){
					validFlag = true;
					return;
				}
	        });
	        if(validFlag){
	        	Ext.Msg.alert("提示", "联系人类型已存在，请重新输入！");
	            return false;
	        }
            if(contactPersonWindow.title =='修改'){
            	var tempRowData = contactPersonGrid.getSelectionModel().getSelections()[0].data;
            	//移除当前修改数据
            	contactPersonStore.removeAt(contactPersonStore.findExact('LINKMAN_ID', tempRowData.LINKMAN_ID));
            	tempRowData.LINKMAN_TYPE = tempJson.LINKMAN_TYPE;
            	tempRowData.LINKMAN_TYPE_ORA = contactPersonForm.getForm().findField('LINKMAN_TYPE').getRawValue();
            	tempRowData.LINKMAN_NAME = tempJson.LINKMAN_NAME;
            	tempRowData.IDENT_TYPE = tempJson.IDENT_TYPE;
            	tempRowData.IDENT_TYPE_ORA = contactPersonForm.getForm().findField('IDENT_TYPE').getRawValue();
            	tempRowData.IDENT_NO = tempJson.IDENT_NO;
            	tempRowData.GENDER = tempJson.GENDER;
            	tempRowData.GENDER_ORA = contactPersonForm.getForm().findField('GENDER').getRawValue();
            	tempRowData.BIRTHDAY = tempJson.BIRTHDAY;
            	tempRowData.TEL = tempJson.TEL;
            	tempRowData.TEL2 = tempJson.TEL2;
            	tempRowData.MOBILE = tempJson.MOBILE;
            	tempRowData.MOBILE2 = tempJson.MOBILE2;
            	tempRowData.EMAIL = tempJson.EMAIL;
            	tempRowData.ADDRESS = tempJson.ADDRESS;
            	tempRowData.LAST_UPDATE_SYS = 'CRM';
            	tempRowData.LAST_UPDATE_USER = JsContext._userId;
            	tempRowData.LAST_UPDATE_TM = _sysCurrDate;
            	tempRowData.IS_ADD_FLAG = Number(tempJson.LINKMAN_ID) > 0?'0':'1';
            	var tempRecord = new Ext.data.Record(tempRowData,null);
            	contactPersonStore.addSorted(tempRecord);
            }else{
	            var tempRowData = {
	            	LINKMAN_ID : -(new Date().getTime()),
	            	CUST_ID : custId,
	            	LINKMAN_TYPE : tempJson.LINKMAN_TYPE,
	            	LINKMAN_TYPE_ORA : contactPersonForm.getForm().findField('LINKMAN_TYPE').getRawValue(),
	            	LINKMAN_NAME : tempJson.LINKMAN_NAME,
	            	IDENT_TYPE : tempJson.IDENT_TYPE,
	            	IDENT_TYPE_ORA : contactPersonForm.getForm().findField('IDENT_TYPE').getRawValue(),
	            	IDENT_NO : tempJson.IDENT_NO,
	            	GENDER : tempJson.GENDER,
	            	GENDER_ORA : contactPersonForm.getForm().findField('GENDER').getRawValue(),
	            	BIRTHDAY : tempJson.BIRTHDAY,
	            	TEL : tempJson.TEL,
	            	TEL2 : tempJson.TEL2,
	            	MOBILE : tempJson.MOBILE,
	            	MOBILE2 : tempJson.MOBILE2,
	            	EMAIL : tempJson.EMAIL,
	            	ADDRESS : tempJson.ADDRESS,
	            	LAST_UPDATE_SYS : 'CRM',
	            	LAST_UPDATE_USER : JsContext._userId,
	            	LAST_UPDATE_TM : _sysCurrDate,//2014-10-29
	            	IS_ADD_FLAG : '1'
	            };
	            var tempRecord = new Ext.data.Record(tempRowData,null);
	            contactPersonStore.addSorted(tempRecord);
            }
            contactPersonStore.sort('LINKMAN_TYPE','ASC');
            contactPersonWindow.hide();
    	}
    },{
        text: '返回',
        handler: function(){
            contactPersonWindow.hide();
        }
    }]
});

//create the toolbar
var contactPersonTbar = new Ext.Toolbar({
    items: [{
        text: '新增',
        hidden:true,
        id:'per_fsx_2-21',
        handler: function(){
            if(contactPersonForm.getForm().getEl()){
                contactPersonForm.getForm().getEl().dom.reset();
            }
            contactPersonWindow.setTitle('新增');
            contactPersonForm.getForm().findField('LINKMAN_TYPE').setReadOnly(false);
            contactPersonForm.getForm().findField('LINKMAN_TYPE').removeClass('x-readOnly');
            contactPersonWindow.show();
        }
    },{
        text: '修改',
        hidden:true,
        id:'per_fsx_2-22',
        handler: function(){
            var selectLength = contactPersonGrid.getSelectionModel().getSelections().length;
            var selectRecord = contactPersonGrid.getSelectionModel().getSelections()[0];
            if(selectLength != 1){
                Ext.Msg.alert('提示','请选择一条记录!');
                return false;
            }
            contactPersonWindow.setTitle('修改');
            contactPersonForm.getForm().findField('LINKMAN_TYPE').setReadOnly(true);
            contactPersonForm.getForm().findField('LINKMAN_TYPE').addClass('x-readOnly');
            contactPersonForm.getForm().loadRecord(selectRecord);
            contactPersonWindow.show();
        }
    },{
        text: '移除',
        hidden:true,
        id:'per_fsx_2-23',
        handler: function(){
            var selectLength = contactPersonGrid.getSelectionModel().getSelections().length;
            var selectRecord = contactPersonGrid.getSelectionModel().getSelections()[0];
            if(selectLength != 1 || selectRecord.data.IS_ADD_FLAG != '1'){
                Ext.Msg.alert('提示','请选择一条新增暂存的记录!');
                return false;
            }
            contactPersonStore.remove(selectRecord);
        }
    }]
});

// create the addrGridPanel
var contactPersonGrid = new Ext.grid.GridPanel({
	height: 180,
    region: 'center',
    autoScroll: true,
    stripeRows: true,
    store: contactPersonStore,
    cm : contactPersonCm,
    tbar: contactPersonTbar,
    viewConfig : {
		getRowClass : function(record,rowIndex,rowParams,store){
			//根据是否修改状态修改背景颜色  
			if(record.data.IS_ADD_FLAG=='0'){//修改过
			  	return 'my_row_set_blue';
		  	}else if(record.data.IS_ADD_FLAG == '1'){//新增
		  		return 'my_row_set_red';
		  	}
		}
	},
    loadMask: {
        msg: '正在加载表格数据,请稍等...'
    }
});

contactPersonStore.on('beforeload',function(){
	contactPersonStore.baseParams = {
		custId : custId
	};
});




/////////////////////////////////////////联系信息////////////////////////////////
/**
 * 联系方式类型
 */
var contmethTypesStore =  new Ext.data.Store({
	restful : true,
	autoLoad : true,
	sortInfo : {
        field:'key',
        direction:'ASC'
    },
	proxy : new Ext.data.HttpProxy({
		url : basepath + '/lookup.json?name=XD000193_S'
	}),
	reader : new Ext.data.JsonReader({
		root : 'JSON'
	},['key','value'])
});
//是否首选
var isPrioriStore =  new Ext.data.Store({
	restful : true,
	autoLoad : true,
	sortInfo : {
        field:'key',
        direction:'ASC'
    },
	proxy : new Ext.data.HttpProxy({
		url : basepath + '/lookup.json?name=XD000332'
	}),
	reader : new Ext.data.JsonReader({
		root : 'JSON'
	},['key','value'])
});
var contactInfoRowNumber = new Ext.grid.RowNumberer({
    header:'NO.',
    width:35
});
// create the data columnModel
var contactInfoCm = new Ext.grid.ColumnModel([
    contactInfoRowNumber,
    {dataIndex:'CONTMETH_TYPE_ORA',header:'联系方式类型',width : 150,sortable : true},
    {dataIndex:'CONTMETH_INFO',header : '联系方式内容',width : 100,sortable : true}, 
    //{dataIndex:'IS_PRIORI_ORA',header:'是否首选',width : 100,sortable : true},
    {dataIndex:'REMARK',header:'备注',width : 100,sortable : true},
    {dataIndex:'LAST_UPDATE_SYS',header:'最后更新系统',width : 100,sortable : true}
]);
// create the data record
var contactInfoRecord = new Ext.data.Record.create([
    {name:'CONTMETH_ID'},
	{name:'CUST_ID'},
	{name:'CONTMETH_TYPE'},
	{name:'CONTMETH_TYPE_ORA'},
	{name:'IS_PRIORI'},
	{name:'IS_PRIORI_ORA'},
	{name:'CONTMETH_INFO'},
	{name:'CONTMETH_SEQ'},
	{name:'REMARK'},
	{name:'STAT'},
	{name:'LAST_UPDATE_SYS'}
]);
// create the data store
var contactInfoStore = new Ext.data.Store({
    restful:true,
    proxy: new Ext.data.HttpProxy({
        url: basepath + '/acrmFCiContmethInfo.json?check=1',
        method:'GET'
    }),
    reader: new Ext.data.JsonReader({
        root:'json.data',
        totalProperty:'json.count'
    },contactInfoRecord)
});

// create the maintain formpanel
var contactInfoForm = new Ext.FormPanel({
    frame : true,
    autoScroll : true,
    split : true,
    items : [
        {xtype:'textfield',name:'CONTMETH_ID',hidden:true,anchor:'95%'},
        {xtype:'textfield',name:'CUST_ID',hidden:true,anchor:'95%'},
        {xtype:'combo',name:'IS_PRIORI',hidden:true,anchor:'95%',store : isPrioriStore,resizable : true,valueField : 'key',displayField : 'value',
			mode : 'local',forceSelection : true,triggerAction : 'all',anchor : '90%'},
        {xtype:'textfield',name:'CONTMETH_SEQ',hidden:true,anchor:'95%'},
        {xtype:'textfield',name:'STAT',hidden:true,anchor:'95%'},
        {xtype : 'combo',name : 'CONTMETH_TYPE',hiddenName : 'CONTMETH_TYPE',fieldLabel : '<font color="red">*</font>联系方式类型',store : contmethTypesStore,resizable : true,valueField : 'key',displayField : 'value',
			mode : 'local',forceSelection : true,triggerAction : 'all',anchor : '90%',allowBlank:false},
		{xtype:'textarea',name:'CONTMETH_INFO',fieldLabel: '<font color="red">*</font>联系方式内容',anchor:'90%',allowBlank:false,maxLength:100},
        {xtype:'textarea',name:'REMARK',fieldLabel: '备注',anchor:'90%',maxLength:100},
        {xtype:'displayfield',name: 'QTIPS',fieldLabel:'<font color="red">注</font>',anchor:'90%'
    		,value:'<font color="red">“手机电话(1/2/3)”、“办公电话(1/2/3)”、“家庭电话(1/2/3)”、“移动电话(1/2/3)”、“传真”为原核心系统联系方式类型！\n</font>'}
    ]
});

// create the contactInfo window
var contactInfoWindow = new Ext.Window({
    title:'新增OR修改',
    width: 600,
    height: 300,
    layout: 'fit',
    closable: true,
    closeAction: 'hide',
    modal: true,
    buttonAlign : 'center',
    items : [contactInfoForm],
    buttons:[{
    	text:'暂存',
    	handler:function(){
    		if (!contactInfoForm.getForm().isValid()) {
                Ext.Msg.alert("提示", "输入有误或存在漏输项，请重新输入！");
                return false;
            }
            var CONTMETH_INFO=contactInfoForm.getForm().findField('CONTMETH_INFO').getValue();
            if(!noChinaPer(CONTMETH_INFO)){
            	return false;
            }
            var tempJson = contactInfoForm.getForm().getValues(false);
            var validFlag = false;//判断类型是否已存在,由于不允许修改类型,故不作与原值比较
	        contactInfoStore.each(function(record){
				if(record.data.CONTMETH_TYPE == tempJson.CONTMETH_TYPE 
					&& (tempJson.CONTMETH_ID === undefined || tempJson.CONTMETH_ID === "")){
					validFlag = true;
					return;
				}
				if(record.data.CONTMETH_TYPE == tempJson.CONTMETH_TYPE && tempJson.CONTMETH_ID != undefined 
					&& tempJson.CONTMETH_ID != "" && record.data.CONTMETH_ID != tempJson.CONTMETH_ID){
					validFlag = true;
					return;
				}
	        });
	        if(validFlag){
	        	Ext.Msg.alert("提示", "联系方式类型已存在，请重新输入！");
	            return false;
	        }
            if(contactInfoWindow.title =='修改'){
            	var tempRowData = contactGridPanel.getSelectionModel().getSelections()[0].data;
            	//移除当前修改数据
            	contactInfoStore.removeAt(contactInfoStore.findExact('CONTMETH_ID', tempRowData.CONTMETH_ID));
            	tempRowData.CONTMETH_TYPE = tempJson.CONTMETH_TYPE;
            	tempRowData.CONTMETH_TYPE_ORA = contactInfoForm.getForm().findField('CONTMETH_TYPE').getRawValue();
            	tempRowData.CONTMETH_INFO = tempJson.CONTMETH_INFO;
            	tempRowData.REMARK = tempJson.REMARK;
            	tempRowData.LAST_UPDATE_SYS = 'CRM';
            	tempRowData.LAST_UPDATE_USER = JsContext._userId;
            	tempRowData.LAST_UPDATE_TM = _sysCurrDate;
            	tempRowData.IS_ADD_FLAG = Number(tempJson.CONTMETH_ID) > 0?'0':'1';
            	var tempRecord = new Ext.data.Record(tempRowData,null);
            	contactInfoStore.addSorted(tempRecord);
            }else{
	            var tempRowData = {
	            	CONTMETH_ID : -(new Date().getTime()),
	            	CUST_ID : custId,
	            	CONTMETH_TYPE : tempJson.CONTMETH_TYPE,
	            	CONTMETH_TYPE_ORA : contactInfoForm.getForm().findField('CONTMETH_TYPE').getRawValue(),
	            	CONTMETH_INFO : tempJson.CONTMETH_INFO,
	            	REMARK : tempJson.REMARK,
	            	IS_PRIORI : '9',
	            	IS_PRIORI_ORA : '未知',
	            	STAT : '1',
	            	LAST_UPDATE_SYS : 'CRM',
	            	LAST_UPDATE_USER : JsContext._userId,
	            	LAST_UPDATE_TM : _sysCurrDate,//2014-10-29
	            	IS_ADD_FLAG : '1'
	            };
	            var tempRecord = new Ext.data.Record(tempRowData,null);
	            contactInfoStore.addSorted(tempRecord);
            }
            contactInfoStore.sort('CONTMETH_TYPE','ASC');
            contactInfoWindow.hide();
    	}
    },{
        text: '返回',
        handler: function(){
            contactInfoWindow.hide();
        }
    }]
});

//create the toolbar
var contactInfoTbar = new Ext.Toolbar({
    items: [{
        text: '新增',
        hidden:true,
        id:'per_fsx_2-31',
        handler: function(){
            if(contactInfoForm.getForm().getEl()){
                contactInfoForm.getForm().getEl().dom.reset();
            }
            contactInfoWindow.setTitle('新增');
            contactInfoForm.getForm().findField('CONTMETH_TYPE').setReadOnly(false);
            contactInfoForm.getForm().findField('CONTMETH_TYPE').removeClass('x-readOnly');
            contactInfoWindow.show();
        }
    },{
        text: '修改',
        hidden:true,
        id:'per_fsx_2-32',
        handler: function(){
            var selectLength = contactGridPanel.getSelectionModel().getSelections().length;
            var selectRecord = contactGridPanel.getSelectionModel().getSelections()[0];
            if(selectLength != 1){
                Ext.Msg.alert('提示','请选择一条记录!');
                return false;
            }
        	if(selectRecord.data.CONTMETH_TYPE!=500 && selectRecord.data.CONTMETH_TYPE!=2031 && selectRecord.data.CONTMETH_TYPE!=2041 && selectRecord.data.CONTMETH_TYPE!=209 && selectRecord.data.CONTMETH_TYPE!=102  ){
        		Ext.Msg.alert("提示", "只允许新增和修改 联系邮箱、办公电话、家庭电话、联络手机号码、业务手机号码");
	            return false;
        	}
            contactInfoWindow.setTitle('修改');
            contactInfoForm.getForm().findField('CONTMETH_TYPE').setReadOnly(true);
            contactInfoForm.getForm().findField('CONTMETH_TYPE').addClass('x-readOnly');
            contactInfoForm.getForm().loadRecord(selectRecord);
            contactInfoWindow.show();
        }
    },{
        text: '移除',
        hidden:true,
        id:'per_fsx_2-33',
        handler: function(){
            var selectLength = contactGridPanel.getSelectionModel().getSelections().length;
            var selectRecord = contactGridPanel.getSelectionModel().getSelections()[0];
            if(selectLength != 1 || selectRecord.data.IS_ADD_FLAG != '1'){
                Ext.Msg.alert('提示','请选择一条新增暂存的记录!');
                return false;
            }
            contactInfoStore.remove(selectRecord);
        }
    }]
});

// create the addrGridPanel
var contactGridPanel = new Ext.grid.GridPanel({
	height: 180,
    region: 'center',
    autoScroll: true,
    stripeRows: true,
    store: contactInfoStore,
    cm : contactInfoCm,
    tbar: contactInfoTbar,
    viewConfig : {
		getRowClass : function(record,rowIndex,rowParams,store){
			//根据是否修改状态修改背景颜色  
			if(record.data.IS_ADD_FLAG=='0'){//修改过
			  	return 'my_row_set_blue';
		  	}else if(record.data.IS_ADD_FLAG == '1'){//新增
		  		return 'my_row_set_red';
		  	}
		}
	},
    loadMask: {
        msg: '正在加载表格数据,请稍等...'
    }
});
contactInfoStore.on('beforeload',function(){
	contactInfoStore.baseParams = {
		custId : custId
	};
});

var holderInfoRowNumber = new Ext.grid.RowNumberer({
    header:'NO.',
    width:35
});
// create the data columnModel
var holderInfoCm = new Ext.grid.ColumnModel([
    holderInfoRowNumber,
    {dataIndex:'HOLDER_TYPE',header:'股东类型',width : 150,sortable : true},
    {dataIndex:'HOLDER_NAME',header : '股东名称',width : 100,sortable : true}, 
    {dataIndex:'IDENT_TYPE',header : '证件类型',width : 100,sortable : true}, 
    {dataIndex:'IDENT_NO',header : '证件号码',width : 100,sortable : true}, 
    {dataIndex:'SPONSOR_KIND',header : '出资方式',width : 100,sortable : true}, 
    {dataIndex:'SPONSOR_AMT',header : '出资金额',width : 100,sortable : true}, 
    {dataIndex:'SPONSOR_CURR',header : '出资币种',width : 100,sortable : true}, 
    {dataIndex:'SPONSOR_PERCENT',header : '出资占比',width : 100,sortable : true}, 
    {dataIndex:'STOCK_PERCENT',header : '持股比例',width : 100,sortable : true}
]);
// create the data record
var holderInfoRecord = new Ext.data.Record.create([
    {name:'HOLDER_TYPE'},
    {name:'HOLDER_NAME'},
    {name:'IDENT_TYPE'},
    {name:'IDENT_NO'},
    {name:'SPONSOR_KIND'},
    {name:'SPONSOR_AMT'},
    {name:'SPONSOR_CURR'},
    {name:'SPONSOR_PERCENT'},
    {name:'STOCK_PERCENT'}
]);
// create the data store
var holderInfoStore = new Ext.data.Store({
    restful:true,
    proxy: new Ext.data.HttpProxy({
        url: basepath + '',
        method:'GET'
    }),
    reader: new Ext.data.JsonReader({
        root:'json.data',
        totalProperty:'json.count'
    },holderInfoRecord)
});
// create the maintain formpanel
var holderInfoForm = new Ext.FormPanel({
    frame : true,
    autoScroll : true,
    split : true,
    items : []
});
// create the contactInfo window
var holderInfoWindow = new Ext.Window({
    title:'新增OR修改',
    width: 600,
    height: 300,
    layout: 'fit',
    closable: true,
    closeAction: 'hide',
    modal: true,
    buttonAlign : 'center',
    items : [holderInfoForm],
    buttons:[{
        text:'暂存',
        handler:function(){
           
        }
    },{
        text: '返回',
        handler: function(){
            holderInfoWindow.hide();
        }
    }]
});
//create the toolbar
var holderInfoTbar = new Ext.Toolbar({
    items: [{
        text: '新增',
        hidden:true,
        id:'per_fsx_2-41',
        handler: function(){
            if(holderInfoForm.getForm().getEl()){
                holderInfoForm.getForm().getEl().dom.reset();
            }
            holderInfoWindow.setTitle('新增');
            holderInfoWindow.show();
        }
    },{
        text: '修改',
        hidden:true,
        id:'per_fsx_2-42',
        handler: function(){
        	holderInfoWindow.setTitle('修改');
            holderInfoWindow.show();
        }
    },{
        text: '移除',
        hidden:true,
        id:'per_fsx_2-43',
        handler: function(){
            
        }
    }]
});
/**
 * 股东信息表格
 */
var holderGridPanel = new Ext.grid.GridPanel({
	height: 180,
    region: 'center',
    autoScroll: true,
    stripeRows: true,
    store: holderInfoStore,
    cm : holderInfoCm,
    tbar: holderInfoTbar,
    viewConfig : {
		getRowClass : function(record,rowIndex,rowParams,store){
			//根据是否修改状态修改背景颜色  
			if(record.data.IS_ADD_FLAG=='0'){//修改过
			  	return 'my_row_set_blue';
		  	}else if(record.data.IS_ADD_FLAG == '1'){//新增
		  		return 'my_row_set_red';
		  	}
		}
	},
    loadMask: {
        msg: '正在加载表格数据,请稍等...'
    }
});
holderInfoStore.on('beforeload',function(){
	holderInfoStore.baseParams = {
		custId : custId
	};
});

/**
 * 
 * helin
 * 添加隐藏主键字段
 * @param {} perModel 要添加进的修改列表
 * @param {} _tempCustId 客户号
 * @param {} formpanel form面板
 * @param {} key 字段
 * @param {} fieldLabel 字段label
 */
var addKeyFn = function(perModel,_tempCustId,formpanel,key,fieldLabel){
	var field = formpanel.getForm().findField(key);
	var pcbhModel = {};
	pcbhModel.custId = _tempCustId;
	pcbhModel.updateBeCont = field.getValue();
	pcbhModel.updateAfCont = field.getValue();
	pcbhModel.updateAfContView = field.getValue();
	pcbhModel.updateItem = fieldLabel;
	pcbhModel.updateItemEn = key;
	pcbhModel.fieldType = '1';
	pcbhModel.updateTableId = '1';
	perModel.push(pcbhModel);
};
/**
 * helin
 * 添加隐藏字段
 * @param {} perModel 要添加进的修改列表
 * @param {} _tempCustId 客户号
 * @param {} beforeValue 修改前值
 * @param {} afterValue 修改后值
 * @param {} key 字段
 * @param {} updateTableId 是否主键字段:1是，''否
 * @param {} fieldType 字段类型:1文本框，'2'日期框
 */
var addFieldFn = function(perModel,_tempCustId,beforeValue,afterValue,key,updateTableId,fieldType){
	var pcbhModel = {};
	pcbhModel.custId = _tempCustId;
	pcbhModel.updateBeCont = beforeValue;
	pcbhModel.updateAfCont = afterValue;
	pcbhModel.updateAfContView = afterValue;
	pcbhModel.updateItem = '';
	pcbhModel.updateItemEn = key;
	pcbhModel.fieldType = fieldType == "2"?"2":"1";
	pcbhModel.updateTableId = updateTableId == "1"?"1":"";
	perModel.push(pcbhModel);
};
/**
 * helin
 * 添加变更历史字段可显示
 * @param {} perModel 要添加进的修改列表
 * @param {} _tempCustId 客户号
 * @param {} beforeValue 修改前值
 * @param {} afterValue 修改后值
 * @param {} key 字段
 * @param {} updateTableId 是否主键字段:1是，''否
 * @param {} fieldType 字段类型:1文本框，'2'日期框
 * @param {} afterValueView 修改后显示值
 * @param {} updateItem 修改项目
 */
var addFieldViewFn = function(perModel,_tempCustId,beforeValue,afterValue,key,updateTableId,fieldType,afterValueView,updateItem){
	var pcbhModel = {};
	pcbhModel.custId = _tempCustId;
	pcbhModel.updateBeCont = beforeValue;
	pcbhModel.updateAfCont = afterValue;
	pcbhModel.updateAfContView = afterValueView;
	pcbhModel.updateItem = updateItem;
	pcbhModel.updateItemEn = key;
	pcbhModel.fieldType = fieldType == "2"?"2":"1";
	pcbhModel.updateTableId = updateTableId == "1"?"1":"";
	perModel.push(pcbhModel);
}
/**
 * helin
 * @param store 要遍历的store
 * @param keyField 要比较的字段
 * @param keyValue 要比较的字段的值
 * @param valueField 要获取值的字段
 */
var getStoreFieldValue = function(store,keyField,keyValue,valueField){
	for(var i=0;i<store.getCount();i++){
		if(store.getAt(i).data[keyField] == keyValue){
			return store.getAt(i).data[valueField];
		}
	}
	return keyValue;
};
/**
 * 查询对私非授信信息第二屏
 */
window.queryFsxPerSecFn = function(){
	addrCustInfoStore.load({
		callback: function(){
			tempAddrStore.removeAll();
			addrCustInfoStore.each(function(record){
				var obj = {};
				Ext.apply(obj,record.data);
				var tempRecord = new Ext.data.Record(obj,null);
        		tempAddrStore.addSorted(tempRecord);
			});
		}
	});
 	contactPersonStore.load({
		callback: function(){
			tempContactPersonStore.removeAll();
			contactPersonStore.each(function(record){
				var obj = {};
				Ext.apply(obj,record.data);
				var tempRecord = new Ext.data.Record(obj,null);
        		tempContactPersonStore.addSorted(tempRecord);
			});
		}
	});
 	contactInfoStore.load({
		callback: function(){
			tempContactInfoStore.removeAll();
			contactInfoStore.each(function(record){
				var obj = {};
				Ext.apply(obj,record.data);
				var tempRecord = new Ext.data.Record(obj,null);
        		tempContactInfoStore.addSorted(tempRecord);
			});
		}
	});
};
/**
 * 返回地址信息变更历史
 * @return allUpdateModelArr
 */
window.getFsxSecPerModel_1 = function(){
	var allUpdateModelArr = [];
	addrCustInfoStore.each(function(record){
		//1新增,0修改
		if(record.data.IS_ADD_FLAG == '1'){
			var perModel = [];
			addFieldFn(perModel,custId,'','','ADDR_ID','1');//主键字段
			addFieldFn(perModel,custId,custId,custId,'ADDR_CUST_ID','');
			if(record.data.ADDR_TYPE != '')
				addFieldViewFn(perModel,custId,'',record.data.ADDR_TYPE,'ADDR_TYPE','','1',record.data.ADDR_TYPE_ORA,'地址类型');
			if(record.data.ZIPCODE != '')	
				addFieldViewFn(perModel,custId,'',record.data.ZIPCODE,'ZIPCODE','','1',record.data.ZIPCODE,'邮政编码');
			if(record.data.ADDR != '')	
				addFieldViewFn(perModel,custId,'',record.data.ADDR,'ADDR','','1',record.data.ADDR,'地址');
			addFieldFn(perModel,custId,'',record.data.LAST_UPDATE_SYS,'ADDR_LAST_UPDATE_SYS','');
			addFieldFn(perModel,custId,'',record.data.LAST_UPDATE_USER,'ADDR_LAST_UPDATE_USER','');
			addFieldFn(perModel,custId,'',record.data.LAST_UPDATE_TM,'ADDR_LAST_UPDATE_TM','','2');
			allUpdateModelArr.push({
				IS_ADD_FLAG : '1',
				permodel : perModel
			});
		}else if(record.data.IS_ADD_FLAG == '0'){
			var isActUpdateFlag = false;
			var tempData = tempAddrStore.getAt(tempAddrStore.findExact('ADDR_ID', record.data.ADDR_ID)).data;
			var perModel = [];
			addFieldFn(perModel,custId,tempData.ADDR_ID,tempData.ADDR_ID,'ADDR_ID','1');//主键字段
			addFieldFn(perModel,custId,custId,custId,'ADDR_CUST_ID','');
			if(record.data.ADDR_TYPE != tempData.ADDR_TYPE){
				isActUpdateFlag = true;
				addFieldViewFn(perModel,custId,tempData.ADDR_TYPE_ORA,record.data.ADDR_TYPE,'ADDR_TYPE','','1',record.data.ADDR_TYPE_ORA,'地址类型');
			}if(record.data.ZIPCODE != tempData.ZIPCODE){
				isActUpdateFlag = true;
				addFieldViewFn(perModel,custId,tempData.ZIPCODE,record.data.ZIPCODE,'ZIPCODE','','1',record.data.ZIPCODE,'邮政编码');
			}if(record.data.ADDR != tempData.ADDR){
				isActUpdateFlag = true;
				addFieldViewFn(perModel,custId,tempData.ADDR,record.data.ADDR,'ADDR','','1',record.data.ADDR,'地址');
			}
			addFieldFn(perModel,custId,tempData.LAST_UPDATE_SYS,record.data.LAST_UPDATE_SYS,'ADDR_LAST_UPDATE_SYS','');
			addFieldFn(perModel,custId,tempData.LAST_UPDATE_USER,record.data.LAST_UPDATE_USER,'ADDR_LAST_UPDATE_USER','');
			addFieldFn(perModel,custId,tempData.LAST_UPDATE_TM,record.data.LAST_UPDATE_TM,'ADDR_LAST_UPDATE_TM','','2');
			if(isActUpdateFlag){
				allUpdateModelArr.push({
					IS_ADD_FLAG : '0',
					permodel : perModel
				});
			}
		}
	});
	return allUpdateModelArr;
};
/**
 * 返回联系人信息变更历史
 * @return allUpdateModelArr
 */
window.getFsxSecPerModel_2 = function(){
	var allUpdateModelArr = [];
	contactPersonStore.each(function(record){
		//1新增,0修改
		if(record.data.IS_ADD_FLAG == '1'){
			var perModel = [];
			addFieldFn(perModel,custId,'ID_SEQUENCE.NEXTVAL','','PER_LINKMAN_ID','1');//主键字段
			addFieldFn(perModel,custId,custId,custId,'PER_LINKMAN_CUST_ID','');
			if(record.data.LINKMAN_TYPE != '')
				addFieldViewFn(perModel,custId,'',record.data.LINKMAN_TYPE,'PER_LINKMAN_TYPE','','1',record.data.LINKMAN_TYPE_ORA,'联系人类型');
			if(record.data.LINKMAN_NAME != '')
				addFieldViewFn(perModel,custId,'',record.data.LINKMAN_NAME,'PER_LINKMAN_NAME','','1',record.data.LINKMAN_NAME,'联系人姓名');
			if(record.data.IDENT_TYPE != '')
				addFieldViewFn(perModel,custId,'',record.data.IDENT_TYPE,'PER_IDENT_TYPE','','1',record.data.IDENT_TYPE_ORA,'联系人证件类型');
			if(record.data.IDENT_NO != '')	
				addFieldViewFn(perModel,custId,'',record.data.IDENT_NO,'PER_IDENT_NO','','1',record.data.IDENT_NO,'联系人证件号码');
			if(record.data.GENDER != '')	
				addFieldViewFn(perModel,custId,'',record.data.GENDER,'PER_GENDER','','1',record.data.GENDER_ORA,'联系人性别');
			if(record.data.BIRTHDAY != '')	
				addFieldViewFn(perModel,custId,'',record.data.BIRTHDAY,'PER_BIRTHDAY','','2',record.data.BIRTHDAY,'联系人出生日期');
			if(record.data.TEL != '')	
				addFieldViewFn(perModel,custId,'',record.data.TEL,'PER_TEL','','1',record.data.TEL,'联系人电话');
			if(record.data.TEL2 != '')	
				addFieldViewFn(perModel,custId,'',record.data.TEL2,'PER_TEL2','','1',record.data.TEL2,'联系人电话2');
			if(record.data.MOBILE != '')	
				addFieldViewFn(perModel,custId,'',record.data.MOBILE,'PER_MOBILE','','1',record.data.MOBILE,'联系人手机号码');
			if(record.data.MOBILE2 != '')	
				addFieldViewFn(perModel,custId,'',record.data.MOBILE2,'PER_MOBILE2','','1',record.data.MOBILE2,'联系人手机号码');
			if(record.data.EMAIL != '')	
				addFieldViewFn(perModel,custId,'',record.data.EMAIL,'PER_EMAIL','','1',record.data.EMAIL,'联系人电子邮件');
			if(record.data.ADDRESS != '')	
				addFieldViewFn(perModel,custId,'',record.data.ADDRESS,'PER_ADDRESS','','1',record.data.ADDRESS,'联系人地址');
			addFieldFn(perModel,custId,'',record.data.LAST_UPDATE_SYS,'PER_LAST_UPDATE_SYS','');
			addFieldFn(perModel,custId,'',record.data.LAST_UPDATE_USER,'PER_LAST_UPDATE_USER','');
			addFieldFn(perModel,custId,'',record.data.LAST_UPDATE_TM,'PER_LAST_UPDATE_TM','','2');
			allUpdateModelArr.push({
				IS_ADD_FLAG : '1',
				permodel : perModel
			});
		}else if(record.data.IS_ADD_FLAG == '0'){
			var isActUpdateFlag = false;
			var tempData = tempContactPersonStore.getAt(tempContactPersonStore.findExact('LINKMAN_ID', record.data.LINKMAN_ID)).data;
			var perModel = [];
			addFieldFn(perModel,custId,tempData.LINKMAN_ID,tempData.LINKMAN_ID,'PER_LINKMAN_ID','1');//主键字段
			addFieldFn(perModel,custId,custId,custId,'PER_LINKMAN_CUST_ID','');
			if(record.data.LINKMAN_TYPE != tempData.LINKMAN_TYPE){
				isActUpdateFlag = true;
				addFieldViewFn(perModel,custId,tempData.LINKMAN_TYPE_ORA,record.data.LINKMAN_TYPE,'PER_LINKMAN_TYPE','','1',record.data.LINKMAN_TYPE_ORA,'联系人类型');
			}if(record.data.LINKMAN_NAME != tempData.LINKMAN_NAME){
				isActUpdateFlag = true;
				addFieldViewFn(perModel,custId,tempData.LINKMAN_NAME,record.data.LINKMAN_NAME,'PER_LINKMAN_NAME','','1',record.data.LINKMAN_NAME,'联系人姓名');
			}if(record.data.IDENT_TYPE != tempData.IDENT_TYPE){
				isActUpdateFlag = true;
				addFieldViewFn(perModel,custId,tempData.IDENT_TYPE_ORA,record.data.IDENT_TYPE,'PER_IDENT_TYPE','','1',record.data.IDENT_TYPE_ORA,'联系人证件类型');
			}if(record.data.IDENT_NO != tempData.IDENT_NO){
				isActUpdateFlag = true;
				addFieldViewFn(perModel,custId,tempData.IDENT_NO,record.data.IDENT_NO,'PER_IDENT_NO','','1',record.data.IDENT_NO,'联系人证件号码');
			}if(record.data.GENDER != tempData.GENDER){
				isActUpdateFlag = true;
				addFieldViewFn(perModel,custId,tempData.GENDER_ORA,record.data.GENDER,'PER_GENDER','','1',record.data.GENDER_ORA,'联系人性别');
			}if(record.data.BIRTHDAY != tempData.BIRTHDAY){
				isActUpdateFlag = true;
				addFieldViewFn(perModel,custId,tempData.BIRTHDAY,record.data.BIRTHDAY,'PER_BIRTHDAY','','2',record.data.BIRTHDAY,'联系人出生日期');
			}if(record.data.TEL != tempData.TEL){
				isActUpdateFlag = true;
				addFieldViewFn(perModel,custId,tempData.TEL,record.data.TEL,'PER_TEL','','1',record.data.TEL,'联系人电话');
			}if(record.data.TEL2 != tempData.TEL2){
				isActUpdateFlag = true;
				addFieldViewFn(perModel,custId,tempData.TEL2,record.data.TEL2,'PER_TEL2','','1',record.data.TEL2,'联系人电话2');
			}if(record.data.MOBILE != tempData.MOBILE){
				isActUpdateFlag = true;
				addFieldViewFn(perModel,custId,tempData.MOBILE,record.data.MOBILE,'PER_MOBILE','','1',record.data.MOBILE,'联系人手机号码');
			}if(record.data.MOBILE2 != tempData.MOBILE2){
				isActUpdateFlag = true;
				addFieldViewFn(perModel,custId,tempData.MOBILE2,record.data.MOBILE2,'PER_MOBILE2','','1',record.data.MOBILE2,'联系人手机号码2');
			}if(record.data.EMAIL != tempData.EMAIL){
				isActUpdateFlag = true;
				addFieldViewFn(perModel,custId,tempData.EMAIL,record.data.EMAIL,'PER_EMAIL','','1',record.data.EMAIL,'联系人电子邮件');
			}if(record.data.ADDRESS != tempData.ADDRESS){
				isActUpdateFlag = true;
				addFieldViewFn(perModel,custId,tempData.ADDRESS,record.data.ADDRESS,'PER_ADDRESS','','1',record.data.ADDRESS,'联系人地址');
			}
			addFieldFn(perModel,custId,tempData.LAST_UPDATE_SYS,record.data.LAST_UPDATE_SYS,'PER_LAST_UPDATE_SYS','');
			addFieldFn(perModel,custId,tempData.LAST_UPDATE_USER,record.data.LAST_UPDATE_USER,'PER_LAST_UPDATE_USER','');
			addFieldFn(perModel,custId,tempData.LAST_UPDATE_TM,record.data.LAST_UPDATE_TM,'PER_LAST_UPDATE_TM','','2');
			if(isActUpdateFlag){
				allUpdateModelArr.push({
					IS_ADD_FLAG : '0',
					permodel : perModel
				});
			}
		}
	});
	return allUpdateModelArr;
};
/**
 * 返回联系信息变更历史
 * @return allUpdateModelArr
 */
window.getFsxSecPerModel_3 = function(){
	var allUpdateModelArr = [];
	contactInfoStore.each(function(record){
		//1新增,0修改
		if(record.data.IS_ADD_FLAG == '1'){
			var perModel = [];
			addFieldFn(perModel,custId,'','','CONTMETH_ID','1');//主键字段
			addFieldFn(perModel,custId,custId,custId,'CONTMETH_CUST_ID');
			addFieldFn(perModel,custId,'','1','STAT');
			if(record.data.CONTMETH_TYPE != '')
				addFieldViewFn(perModel,custId,'',record.data.CONTMETH_TYPE,'CONTMETH_TYPE','','1',record.data.CONTMETH_TYPE_ORA,'联系方式类型');
			if(record.data.IS_PRIORI != '')
				addFieldViewFn(perModel,custId,'',record.data.IS_PRIORI,'IS_PRIORI','','1',record.data.IS_PRIORI_ORA,'');
			if(record.data.CONTMETH_INFO != '')
				addFieldViewFn(perModel,custId,'',record.data.CONTMETH_INFO,'CONTMETH_INFO','','1',record.data.CONTMETH_INFO,'联系方式内容');
			if(record.data.REMARK != '')
				addFieldViewFn(perModel,custId,'',record.data.REMARK,'REMARK','','1',record.data.REMARK,'备注');
			addFieldFn(perModel,custId,'',record.data.LAST_UPDATE_SYS,'CONTMETH_LAST_UPDATE_SYS','');
			addFieldFn(perModel,custId,'',record.data.LAST_UPDATE_USER,'CONTMETH_LAST_UPDATE_USER','');
			addFieldFn(perModel,custId,'',record.data.LAST_UPDATE_TM,'CONTMETH_LAST_UPDATE_TM','','2');
			allUpdateModelArr.push({
				IS_ADD_FLAG : '1',
				permodel : perModel
			});
		}else if(record.data.IS_ADD_FLAG == '0'){
			var isActUpdateFlag = false;
			var tempData = tempContactInfoStore.getAt(tempContactInfoStore.findExact('CONTMETH_ID', record.data.CONTMETH_ID)).data;
			var perModel = [];
			addFieldFn(perModel,custId,tempData.CONTMETH_ID,tempData.CONTMETH_ID,'CONTMETH_ID','1');//主键字段
			addFieldFn(perModel,custId,custId,custId,'CONTMETH_CUST_ID','');
			if(record.data.CONTMETH_TYPE != tempData.CONTMETH_TYPE){
				isActUpdateFlag = true;
				addFieldViewFn(perModel,custId,tempData.CONTMETH_TYPE_ORA,record.data.CONTMETH_TYPE,'CONTMETH_TYPE','','1',record.data.CONTMETH_TYPE_ORA,'联系方式类型');
			}if(record.data.CONTMETH_INFO != tempData.CONTMETH_INFO){
				isActUpdateFlag = true;
				addFieldViewFn(perModel,custId,tempData.CONTMETH_INFO,record.data.CONTMETH_INFO,'CONTMETH_INFO','','1',record.data.CONTMETH_INFO,'联系方式内容');
			}if(record.data.REMARK != tempData.REMARK){
				isActUpdateFlag = true;
				addFieldViewFn(perModel,custId,tempData.REMARK,record.data.REMARK,'REMARK','','1',record.data.REMARK,'备注');
			}
			addFieldFn(perModel,custId,tempData.LAST_UPDATE_SYS,record.data.LAST_UPDATE_SYS,'CONTMETH_LAST_UPDATE_SYS','');
			addFieldFn(perModel,custId,tempData.LAST_UPDATE_USER,record.data.LAST_UPDATE_USER,'CONTMETH_LAST_UPDATE_USER','');
			addFieldFn(perModel,custId,tempData.LAST_UPDATE_TM,record.data.LAST_UPDATE_TM,'CONTMETH_LAST_UPDATE_TM','','2');
			if(isActUpdateFlag){
				allUpdateModelArr.push({
					IS_ADD_FLAG : '0',
					permodel : perModel
				});
			}
		}
	});
	return allUpdateModelArr;
};