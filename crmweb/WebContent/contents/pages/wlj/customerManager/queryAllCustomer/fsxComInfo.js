/**
 * 对公客户  非授信信息维护页面
 * 2014-08-06  luyy
 */ 
var _sysCurrDate = new Date().format('Y-m-d');
//临时存储地址数据
var tempComAddrStore = new Ext.data.Store();
var tempComContactPersonStore = new Ext.data.Store();
var tempComContactInfoStore = new Ext.data.Store();
var tempComHolderInfoStore = new Ext.data.Store();

/**
 * 地址类型
 */
var addrTypeStore =  new Ext.data.Store( {
	restful : true,
	autoLoad : true,
	proxy : new Ext.data.HttpProxy( {
		url : basepath + '/lookup.json?name=XD000192'
	}),
	reader : new Ext.data.JsonReader( {
		root : 'JSON'
	},['key','value'])
});

var noChinaCom=function(value){
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
var noChinaCom2=function(value,type){
	var flag=true;
	if (/[\u4E00-\u9FA5]/i.test(value)) {
		switch(type){
			case  'OFFICE_TEL':
			Ext.Msg.alert("提示", "办公电话不允许包括中文字符或全角符号");
			break;
			case  'FEX':
			Ext.Msg.alert("提示", "传真号码不允许包括中文字符或全角符号");
			break;
			case  'HOME_TEL':
			Ext.Msg.alert("提示", "家庭电话不允许包括中文字符或全角符号");
			break;
			case  'MOBILE':
			Ext.Msg.alert("提示", "手机号码不允许包括中文字符或全角符号");
			break;
			case  'MOBILE2':
			Ext.Msg.alert("提示", "手机号码2不允许包括中文字符或全角符号");
			break;
			case  'EMAIL':
			Ext.Msg.alert("提示", "电子邮件不允许包括中文字符或全角符号");
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
			case  'OFFICE_TEL':
			Ext.Msg.alert("提示", "办公电话不允许包括中文字符或全角符号");
			break;
			case  'FEX':
			Ext.Msg.alert("提示", "传真号码不允许包括中文字符或全角符号");
			break;
			case  'HOME_TEL':
			Ext.Msg.alert("提示", "家庭电话不允许包括中文字符或全角符号");
			break;
			case  'MOBILE':
			Ext.Msg.alert("提示", "手机号码不允许包括中文字符或全角符号");
			break;
			case  'MOBILE2':
			Ext.Msg.alert("提示", "手机号码2不允许包括中文字符或全角符号");
			break;
			case  'EMAIL':
			Ext.Msg.alert("提示", "电子邮件不允许包括中文字符或全角符号");
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
var comAddrCustInfoRowNumber = new Ext.grid.RowNumberer({
    header:'NO.',
    width:35
});
// create the data columnModel
var comAddrCustInfoCm = new Ext.grid.ColumnModel([
    comAddrCustInfoRowNumber,
    {dataIndex:'ADDR_TYPE_ORA',header:'地址类型',width : 120,sortable : true},
    {dataIndex:'ADDR',header:'详细地址',width : 350,sortable : true},
    {dataIndex:'ZIPCODE',header:'邮政编码',width : 100,sortable : true},
    {dataIndex:'LAST_UPDATE_SYS',header:'最后更新系统',width : 100,sortable : true}
]);
// create the data record
var comAddrCustInfoRecord = new Ext.data.Record.create([
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
var comAddrCustInfoStore = new Ext.data.Store({
    restful:true,
    proxy: new Ext.data.HttpProxy({
        url: basepath + '/dealWithFsx!queryAddr.json',
        method:'GET'
    }),
    reader: new Ext.data.JsonReader({
        root:'json.data',
        totalProperty:'json.count'
    },comAddrCustInfoRecord)
});

// create the maintain formpanel
var comMaintainForm = new Ext.FormPanel({
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
var comMaintainWindow = new Ext.Window({
    title:'新增OR修改',
    width: 500,
    height: 300,
    layout: 'fit',
    closable: true,
    closeAction: 'hide',
    modal: true,
    buttonAlign : 'center',
    items : [comMaintainForm],
    buttons:[{
	   	text:'暂存',
	   	handler:function(){
	   		if (!comMaintainForm.getForm().isValid()) {
	            Ext.Msg.alert("提示", "输入有误或存在漏输项，请重新输入！");
	            return false;
	        }
	        var tempJson = comMaintainForm.getForm().getValues(false);
	        var validFlag = false;//判断类型是否已存在
	        comAddrCustInfoStore.each(function(record){
				if(record.data.ADDR_TYPE == tempJson.ADDR_TYPE && (tempJson.ADDR_ID === undefined || tempJson.ADDR_ID === "")){
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
	        if(comMaintainWindow.title =='修改'){
	        	var tempRowData = comAddrGridPanel.getSelectionModel().getSelections()[0].data;
	        	//移除当前修改数据
	        	comAddrCustInfoStore.removeAt(comAddrCustInfoStore.findExact('ADDR_ID', tempRowData.ADDR_ID));
	        	tempRowData.ADDR_TYPE = tempJson.ADDR_TYPE;
	        	tempRowData.ADDR_TYPE_ORA = comMaintainForm.getForm().findField('ADDR_TYPE').getRawValue();
	        	tempRowData.ZIPCODE = tempJson.ZIPCODE;
	        	tempRowData.ADDR = tempJson.ADDR;
	            tempRowData.LAST_UPDATE_SYS = 'CRM';
	        	tempRowData.LAST_UPDATE_USER = JsContext._userId;
	        	tempRowData.LAST_UPDATE_TM = _sysCurrDate;
	        	tempRowData.IS_ADD_FLAG = Number(tempJson.ADDR_ID) > 0?'0':'1';
	        	var tempRecord = new Ext.data.Record(tempRowData,null);
	        	comAddrCustInfoStore.addSorted(tempRecord);
	        }else{
	            var tempRowData = {
	            	ADDR_ID : -(new Date().getTime()),
	            	CUST_ID : custId,
	            	ADDR_TYPE : tempJson.ADDR_TYPE,
	            	ADDR_TYPE_ORA : comMaintainForm.getForm().findField('ADDR_TYPE').getRawValue(),
	            	ZIPCODE : tempJson.ZIPCODE,
	            	ADDR : tempJson.ADDR,
	            	LAST_UPDATE_SYS : 'CRM',
	            	LAST_UPDATE_USER : JsContext._userId,
	            	LAST_UPDATE_TM : _sysCurrDate,//2014-10-29
	            	IS_ADD_FLAG : '1'
	            };
	            var tempRecord = new Ext.data.Record(tempRowData,null);
	            comAddrCustInfoStore.addSorted(tempRecord);
	        }
	        comAddrCustInfoStore.sort('ADDR_TYPE','ASC');
	        comMaintainWindow.hide();
		}
	},{
        text: '返回',
        handler: function(){
            comMaintainWindow.hide();
        }
    }]
});

//create the toolbar
var comAddrCustInfoTbar = new Ext.Toolbar({
    items: [{
        text: '新增',
        hidden:true,
        id:'pub_fsx_2-11',
        handler: function(){
            if(comMaintainForm.getForm().getEl()){
                comMaintainForm.getForm().getEl().dom.reset();
            }
            comMaintainWindow.setTitle('新增');
            comMaintainForm.getForm().findField('ADDR_TYPE').setReadOnly(false);
            comMaintainForm.getForm().findField('ADDR_TYPE').removeClass('x-readOnly');
            comMaintainWindow.show();
        }
    },{
        text: '修改',
        hidden:true,
        id:'pub_fsx_2-12',
        handler: function(){
            var selectLength = comAddrGridPanel.getSelectionModel().getSelections().length;
            var selectRecord = comAddrGridPanel.getSelectionModel().getSelections()[0];
            if(selectLength != 1){
                Ext.Msg.alert('提示','请选择一条记录!');
                return false;
            }
            comMaintainWindow.setTitle('修改');
            comMaintainForm.getForm().findField('ADDR_TYPE').setReadOnly(true);
            comMaintainForm.getForm().findField('ADDR_TYPE').addClass('x-readOnly');
            comMaintainForm.getForm().loadRecord(selectRecord);
            comMaintainWindow.show();
        }
    },{
        text: '移除',
        hidden:true,
        id:'pub_fsx_2-13',
        handler: function(){
            var selectLength = comAddrGridPanel.getSelectionModel().getSelections().length;
            var selectRecord = comAddrGridPanel.getSelectionModel().getSelections()[0];
            if(selectLength != 1 || selectRecord.data.IS_ADD_FLAG != '1'){
                Ext.Msg.alert('提示','请选择一条新增暂存的记录!');
                return false;
            }
            comAddrCustInfoStore.remove(selectRecord);
        }
    }]
});

// create the comAddrGridPanel
var comAddrGridPanel = new Ext.grid.GridPanel({
	height: 200,
    region: 'center',
    autoScroll: true,
    stripeRows: true,
    store: comAddrCustInfoStore,
    cm : comAddrCustInfoCm,
    tbar: comAddrCustInfoTbar,
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

comAddrCustInfoStore.on('beforeload',function(){
	comAddrCustInfoStore.baseParams = {
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
	proxy : new Ext.data.HttpProxy( {
		url : basepath + '/lookup.json?name=XD000016'
	}),
	reader : new Ext.data.JsonReader( {
		root : 'JSON'
	},['key','value'])
});
//干系人类型
var linkTypeStore1 = new Ext.data.Store({
	restful : true,
	autoLoad : true,
	sortInfo : {
        field:'key',
        direction:'ASC'
    },
	proxy : new Ext.data.HttpProxy( {
		url : basepath + '/lookup.json?name=XD000339'
	}),
	reader : new Ext.data.JsonReader( {
		root : 'JSON'
	},['key','value'])
});
var linkTitleStore = new Ext.data.Store({
	restful : true,
	autoLoad : true,
	sortInfo : {
        field:'key',
        direction:'ASC'
    },
	proxy : new Ext.data.HttpProxy( {
		url : basepath + '/lookup.json?name=XD000250'
	}),
	reader : new Ext.data.JsonReader( {
		root : 'JSON'
	},['key','value'])
}); 
var comContactPersonRowNumber = new Ext.grid.RowNumberer({
    header:'NO.',
    width:35
});
// create the data columnModel
var comContactPersonCm = new Ext.grid.ColumnModel([
    comContactPersonRowNumber,
    {dataIndex:'LINKMAN_TYPE_ORA',header:'联系人类型',width : 100,sortable : true},
    {dataIndex:'LINKMAN_NAME',header:'姓名',width : 100,sortable : true},
    {dataIndex:'IDENT_TYPE_ORA',header:'证件类型',width : 100,sortable : true},
    {dataIndex:'IDENT_NO',header:'证件号码',width : 100,sortable : true},
    {dataIndex:'IDENT_EXPIRED_DATE',header:'证件失效日期',width : 100,sortable : true},
    {dataIndex:'GENDER_ORA',header:'性别',width : 100,sortable : true},
    {dataIndex:'BIRTHDAY',header:'出生日期',width : 100,sortable : true},
    {dataIndex:'LINKMAN_TITLE_ORA',header:'联系人称谓',width : 100,sortable : true},
    {dataIndex:'OFFICE_TEL',header:'办公电话',width : 100,sortable : true},
    {dataIndex:'HOME_TEL',header:'家庭电话',width : 100,sortable : true},
    {dataIndex:'MOBILE',header:'手机号码',width : 100,sortable : true},
    {dataIndex:'MOBILE2',header:'手机号码2',width : 100,sortable : true},
    {dataIndex:'FEX',header:'传真号码',width : 100,sortable : true},
    {dataIndex:'EMAIL',header:'邮件',width : 100,sortable : true},
    {dataIndex:'LAST_UPDATE_SYS',header:'最后更新系统',width : 100,sortable : true},
    {dataIndex:'LAST_UPDATE_USER',header:'最后更新人',width : 100,sortable : true},
    {dataIndex:'LAST_UPDATE_TM',header:'最后修改日期',width : 120,sortable : true}
]);
// create the data record
var comContactPersonRecord = new Ext.data.Record.create([
    {name : 'LINKMAN_ID'},
    {name : 'ORG_CUST_ID'},
    {name : 'LINKMAN_TYPE'},
	{name : 'LINKMAN_TYPE_ORA'},
	{name : 'LINKMAN_NAME'},
	{name : 'IDENT_NO'},
	{name : 'IDENT_TYPE'},
	{name : 'IDENT_TYPE_ORA'},
	{name : 'IDENT_EXPIRED_DATE'},
	{name : 'GENDER'},
	{name : 'GENDER_ORA'},
	{name : 'BIRTHDAY'},
	{name : 'LINKMAN_TITLE'},
	{name : 'LINKMAN_TITLE_ORA'},
	{name : 'EMAIL'},
	{name : 'FEX'},
	{name : 'HOME_TEL'},
	{name : 'MOBILE'},
	{name : 'MOBILE2'},
	{name : 'OFFICE_TEL'},
	{name : 'LAST_UPDATE_SYS'},
	{name : 'LAST_UPDATE_USER'},
	{name : 'LAST_UPDATE_TM'}
]);
// create the data store
var comContactPersonStore = new Ext.data.Store({
    restful:true,
    proxy: new Ext.data.HttpProxy({
        url: basepath+'/dealWithFsx!queryComContactPerson.json',
        method:'GET'
    }),
    reader: new Ext.data.JsonReader({
        root:'json.data',
        totalProperty:'json.count'
    },comContactPersonRecord)
});

// create the comContactPerson formpanel
var comContactPersonForm = new Ext.FormPanel({
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
		        {xtype:'textfield',name:'ORG_CUST_ID',hidden:true,anchor:'95%'},
		        {xtype : 'combo',name : 'LINKMAN_TYPE',hiddenName : 'LINKMAN_TYPE',fieldLabel : '<font color="red">*</font>联系人类型',store : linkTypeStore1,resizable : true,valueField : 'key',displayField : 'value',
					mode : 'local',forceSelection : true,triggerAction : 'all',anchor : '90%',allowBlank:false},
		        {xtype : 'combo',name : 'IDENT_TYPE',hiddenName : 'IDENT_TYPE',fieldLabel : '证件类型',store : identTypeStore,resizable : true,valueField : 'key',displayField : 'value',
					mode : 'local',forceSelection : true,triggerAction : 'all',anchor : '90%'},
				{
     			 layout : 'column',
	 			 items:[{
	 			 		 columnWidth : .7,
						 layout : 'form',
						 items :[{
						 		  xtype:'datefield',name:'IDENT_EXPIRED_DATE',fieldLabel: '证件失效日期',format:'Y-m-d',anchor:'98%',
						 		  listeners:{
											'change': function(obj,oldValue,newValue){
												var com_RILONGTERM = comContactPersonForm.getForm().findField('RILONGTERM');
													if(this.value == '9999-12-31'){
				            							  com_RILONGTERM.setValue(true);
				            						}else{
														  com_RILONGTERM.setValue(false);
													};
											}
									  	}
	 			 				}]
					  },{
		        		 columnWidth : .3,
						 layout : 'form',
						 labelWidth:1,
						 items :[{
						     	   xtype:'checkbox',
								   boxLabel:"长期有效",
								   name:'RILONGTERM',
								   inputvalue:'1',
								   listeners:{
										'check':function(obj,ischeck){
											var IDENT_EXPIRED_DATE = comContactPersonForm.getForm().findField('IDENT_EXPIRED_DATE');
												if (ischeck == true){
													IDENT_EXPIRED_DATE.setValue("9999-12-31");  
													IDENT_EXPIRED_DATE.setReadOnly(true);
												}else{
													IDENT_EXPIRED_DATE.setValue("");  
													IDENT_EXPIRED_DATE.setReadOnly(false);
												 }
											}
								  		}
			    	               }]
 			             }]
				},
				{xtype : 'combo',name : 'GENDER',hiddenName : 'GENDER',fieldLabel : '性别',store : genderTypeStore,resizable : true,valueField : 'key',displayField : 'value',
					mode : 'local',forceSelection : true,triggerAction : 'all',anchor : '90%'},
		        {xtype : 'combo',name : 'LINKMAN_TITLE',hiddenName : 'LINKMAN_TITLE',fieldLabel : '联系人称谓',store : linkTitleStore,resizable : true,valueField : 'key',displayField : 'value',
					mode : 'local',forceSelection : true,triggerAction : 'all',anchor : '90%'},
		        {xtype:'textfield',name:'OFFICE_TEL',fieldLabel: '办公电话',anchor:'90%',maxLength:20},
		        {xtype:'textfield',name:'FEX',fieldLabel: '传真号码',anchor:'90%',maxLength:20}
    		]
    	},{
        	columnWidth : .5,
			layout : 'form',
			items :[
				{xtype:'textfield',name:'LINKMAN_NAME',fieldLabel: '<font color="red">*</font>姓名',anchor:'90%',allowBlank:false,maxLength:100},
				{xtype:'textfield',name:'IDENT_NO',fieldLabel: '证件号码',anchor:'90%',maxLength:40},
				{xtype:'datefield',name:'BIRTHDAY',fieldLabel: '出生日期',format:'Y-m-d',anchor:'90%'},
				{xtype:'textfield',name:'HOME_TEL',fieldLabel: '家庭电话',anchor:'90%',maxLength:20},
				{xtype:'textfield',name:'MOBILE',fieldLabel: '手机号码',anchor:'90%',vtype:'telephone',maxLength:20},
				{xtype:'textfield',name:'MOBILE2',fieldLabel: '手机号码2',anchor:'90%',vtype:'telephone',maxLength:20},
		        {xtype:'textfield',name:'EMAIL',fieldLabel: '电子邮件',anchor:'90%',vtype:'email',maxLength:40}
    		]
    	}]
    },
    {xtype:'displayfield',name: 'QTIPS',fieldLabel:'<font color=red>注</font>',anchor:'90%'
    	,value:'<font color=red>“联系人1”、“联系人2”、“联系人3”为原核心系统联系人类型！\n</font>'}
    ]
});

// create the comContactPerson window
var comContactPersonWindow = new Ext.Window({
    title:'新增OR修改',
    width: 700,
    height: 350,
    layout: 'fit',
    closable: true,
    closeAction: 'hide',
    modal: true,
    buttonAlign : 'center',
    items : [comContactPersonForm],
    buttons:[{
    	text:'暂存',
    	handler:function(){
    		if (!comContactPersonForm.getForm().isValid()) {
                Ext.Msg.alert("提示", "输入有误或存在漏输项，请重新输入！");
                return false;
            }
            var  LINKMAN_NAME=   comContactPersonForm.getForm().findField('LINKMAN_NAME').getValue();
            if(LINKMAN_NAME.replace(/(^\s*)|(\s*$)/g,'') == ""){
            	 Ext.Msg.alert("提示", "联系人姓名不能为空,请重新输入！");
                return false;
            }
            var OFFICE_TEL=comContactPersonForm.getForm().findField('OFFICE_TEL').getValue();
            var FEX=comContactPersonForm.getForm().findField('FEX').getValue();
            var HOME_TEL=comContactPersonForm.getForm().findField('HOME_TEL').getValue();
            var MOBILE=comContactPersonForm.getForm().findField('MOBILE').getValue();
            var MOBILE2=comContactPersonForm.getForm().findField('MOBILE2').getValue();
            var EMAIL=comContactPersonForm.getForm().findField('EMAIL').getValue();
            if(!noChinaCom2(OFFICE_TEL,'OFFICE_TEL')){
            	return false;
            }
            if(!noChinaCom2(FEX,'FEX')){
            	return false;
            }
            if(!noChinaCom2(HOME_TEL,'HOME_TEL')){
            	return false;
            }
            if(!noChinaCom2(MOBILE,'MOBILE')){
            	return false;
            }
            if(!noChinaCom2(MOBILE2,'MOBILE2')){
            	return false;
            }
            if(!noChinaCom2(EMAIL,'EMAIL')){
            	return false;
            }
			var tempJson = comContactPersonForm.getForm().getValues(false);
			var validFlag = false;//判断类型是否已存在
	        comContactPersonStore.each(function(record){
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
	        if(comContactPersonWindow.title =='修改'){
	        	var tempRowData = comContactPersonGrid.getSelectionModel().getSelections()[0].data;
	        	//移除当前修改数据
	        	comContactPersonStore.removeAt(comContactPersonStore.findExact('LINKMAN_ID', tempRowData.LINKMAN_ID));
	        	tempRowData.LINKMAN_TYPE = tempJson.LINKMAN_TYPE;
	        	tempRowData.LINKMAN_TYPE_ORA = comContactPersonForm.getForm().findField('LINKMAN_TYPE').getRawValue();
	        	tempRowData.LINKMAN_NAME = tempJson.LINKMAN_NAME;
	        	tempRowData.IDENT_TYPE = tempJson.IDENT_TYPE;
	        	tempRowData.IDENT_TYPE_ORA = comContactPersonForm.getForm().findField('IDENT_TYPE').getRawValue();
	        	tempRowData.IDENT_NO = tempJson.IDENT_NO;
	        	tempRowData.GENDER = tempJson.GENDER;
	        	tempRowData.GENDER_ORA = comContactPersonForm.getForm().findField('GENDER').getRawValue();
	        	tempRowData.BIRTHDAY = tempJson.BIRTHDAY;
	        	tempRowData.IDENT_EXPIRED_DATE = tempJson.IDENT_EXPIRED_DATE;
	        	tempRowData.LINKMAN_TITLE = tempJson.LINKMAN_TITLE;
	        	tempRowData.LINKMAN_TITLE_ORA = comContactPersonForm.getForm().findField('LINKMAN_TITLE').getRawValue();
	        	tempRowData.EMAIL = tempJson.EMAIL;
	        	tempRowData.FEX = tempJson.FEX;
	        	tempRowData.HOME_TEL = tempJson.HOME_TEL;
	        	tempRowData.MOBILE = tempJson.MOBILE;
	        	tempRowData.MOBILE2 = tempJson.MOBILE2;
	        	tempRowData.OFFICE_TEL = tempJson.OFFICE_TEL;
	            tempRowData.LAST_UPDATE_SYS = 'CRM';
	        	tempRowData.LAST_UPDATE_USER = JsContext._userId;
	        	tempRowData.LAST_UPDATE_TM = _sysCurrDate;
	        	tempRowData.IS_ADD_FLAG = Number(tempJson.LINKMAN_ID) > 0?'0':'1';
	        	var tempRecord = new Ext.data.Record(tempRowData,null);
	        	comContactPersonStore.addSorted(tempRecord);
	        }else{
	            var tempRowData = {
	            	LINKMAN_ID : -(new Date().getTime()),
	            	CUST_ID : custId,
	            	LINKMAN_TYPE : tempJson.LINKMAN_TYPE,
	            	LINKMAN_TYPE_ORA : comContactPersonForm.getForm().findField('LINKMAN_TYPE').getRawValue(),
	            	LINKMAN_NAME : tempJson.LINKMAN_NAME,
	            	IDENT_TYPE : tempJson.IDENT_TYPE,
	            	IDENT_TYPE_ORA : comContactPersonForm.getForm().findField('IDENT_TYPE').getRawValue(),
	            	IDENT_NO : tempJson.IDENT_NO,
	            	GENDER : tempJson.GENDER,
	            	GENDER_ORA : comContactPersonForm.getForm().findField('GENDER').getRawValue(),
	            	BIRTHDAY : tempJson.BIRTHDAY,
	            	IDENT_EXPIRED_DATE : tempJson.IDENT_EXPIRED_DATE,
		        	LINKMAN_TITLE : tempJson.LINKMAN_TITLE,
		        	LINKMAN_TITLE_ORA : comContactPersonForm.getForm().findField('LINKMAN_TITLE').getRawValue(),
		        	EMAIL : tempJson.EMAIL,
		        	FEX : tempJson.FEX,
		        	HOME_TEL : tempJson.HOME_TEL,
		        	MOBILE : tempJson.MOBILE,
		        	MOBILE2 : tempJson.MOBILE2,
		        	OFFICE_TEL : tempJson.OFFICE_TEL,
	            	LAST_UPDATE_SYS : 'CRM',
	            	LAST_UPDATE_USER : JsContext._userId,
	            	LAST_UPDATE_TM : _sysCurrDate,//2014-10-29
	            	IS_ADD_FLAG : '1'
	            };
	            var tempRecord = new Ext.data.Record(tempRowData,null);
	            comContactPersonStore.addSorted(tempRecord);
	        }
	        comContactPersonStore.sort('LINKMAN_TYPE','ASC');
	        comContactPersonWindow.hide();
		}
	},{
        text: '返回',
        handler: function(){
            comContactPersonWindow.hide();
        }
    }]
});

//create the toolbar
var comContactPersonTbar = new Ext.Toolbar({
    items: [{
        text: '新增',
        hidden:true,
        id:'pub_fsx_2-21',
        handler: function(){

            if(comContactPersonForm.getForm().getEl()){
                comContactPersonForm.getForm().getEl().dom.reset();
            }
            var RILONGTERM = comContactPersonForm.getForm().findField('RILONGTERM');
            RILONGTERM.setValue(false);
            
            
            comContactPersonWindow.setTitle('新增');
            comContactPersonForm.getForm().findField('LINKMAN_TYPE').setReadOnly(false);
            comContactPersonForm.getForm().findField('LINKMAN_TYPE').removeClass('x-readOnly');
            comContactPersonWindow.show();
        }
    },{
        text: '修改',
        hidden:true,
        id:'pub_fsx_2-22',
        handler: function(){
            var selectLength = comContactPersonGrid.getSelectionModel().getSelections().length;
            var selectRecord = comContactPersonGrid.getSelectionModel().getSelections()[0];
            if(selectLength != 1){
                Ext.Msg.alert('提示','请选择一条记录!');
                return false;
            }
            comContactPersonWindow.setTitle('修改');
            comContactPersonForm.getForm().findField('LINKMAN_TYPE').setReadOnly(true);
            comContactPersonForm.getForm().findField('LINKMAN_TYPE').addClass('x-readOnly');
            comContactPersonForm.getForm().loadRecord(selectRecord);
            comContactPersonWindow.show();
        }
    },{
        text: '移除',
        hidden:true,
        id:'pub_fsx_2-23',
        handler: function(){
            var selectLength = comContactPersonGrid.getSelectionModel().getSelections().length;
            var selectRecord = comContactPersonGrid.getSelectionModel().getSelections()[0];
            if(selectLength != 1 || selectRecord.data.IS_ADD_FLAG != '1'){
                Ext.Msg.alert('提示','请选择一条新增暂存的记录!');
                return false;
            }
            comContactPersonStore.remove(selectRecord);
        }
    }]
});

// create the comAddrGridPanel
var comContactPersonGrid = new Ext.grid.GridPanel({
	height: 200,
    region: 'center',
    autoScroll: true,
    stripeRows: true,
    store: comContactPersonStore,
    cm : comContactPersonCm,
    tbar: comContactPersonTbar,
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

comContactPersonStore.on('beforeload',function(){
	comContactPersonStore.baseParams = {
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
		url : basepath + '/lookup.json?name=XD000193'
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
var comContactInfoRowNumber = new Ext.grid.RowNumberer({
    header:'NO.',
    width:35
});
// create the data columnModel
var comContactInfoCm = new Ext.grid.ColumnModel([
    comContactInfoRowNumber,
    {dataIndex:'CONTMETH_TYPE_ORA',header:'联系方式类型',width : 150,sortable : true},
    {dataIndex:'CONTMETH_INFO',header : '联系方式内容',width : 200,sortable : true}, 
    //{dataIndex:'IS_PRIORI_ORA',header:'是否首选',width : 100,sortable : true},
    {dataIndex:'REMARK',header:'备注',width : 100,sortable : true},
    {dataIndex:'LAST_UPDATE_SYS',header:'最后更新系统',width : 100,sortable : true}
]);
// create the data record
var comContactInfoRecord = new Ext.data.Record.create([
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
var comContactInfoStore = new Ext.data.Store({
    restful:true,
    proxy: new Ext.data.HttpProxy({
        url: basepath + '/acrmFCiContmethInfo.json?check=1&&custType=1',
        method:'GET'
    }),
    reader: new Ext.data.JsonReader({
        root:'json.data',
        totalProperty:'json.count'
    },comContactInfoRecord)
});

// create the maintain formpanel
var comContactInfoForm = new Ext.FormPanel({
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
			mode : 'local',forceSelection : true,triggerAction : 'all',anchor : '90%',allowBlank:false,  listeners:{
				select : function(){
					comContactInfoForm.getForm().findField('COUNTRY').setValue();
					comContactInfoForm.getForm().findField('NUMBER').setValue();
					comContactInfoForm.getForm().findField('AREA').setValue();
					if(this.value!=100 && this.value!=101 && this.value!=102 && this.value!=103&& this.value!=104&& this.value!=105&& this.value!=200&& this.value!=201&& this.value!=202&& this.value!=203
							&& this.value!=2031&& this.value!=2032&& this.value!=2033&& this.value!=204&& this.value!=2041&& this.value!=2042&& this.value!=2043&& this.value!=205&& this.value!=206 && this.value!=2071 
							&& this.value!=2072&& this.value!=2073&& this.value!=2074&& this.value!=208&& this.value!=209&& this.value!=210&& this.value!=211&& this.value!=212){
						comContactInfoForm.getForm().findField('CONTMETH_INFO_SP').hide();
						comContactInfoForm.getForm().findField('CONTMETH_INFO').show();
						comContactInfoForm.getForm().findField('CONTMETH_INFO').allowBlank=false;
						comContactInfoForm.getForm().findField('COUNTRY').allowBlank=true;
						comContactInfoForm.getForm().findField('NUMBER').allowBlank=true;
					}else{
						comContactInfoForm.getForm().findField('CONTMETH_INFO_SP').show();
						comContactInfoForm.getForm().findField('CONTMETH_INFO').hide();
						comContactInfoForm.getForm().findField('CONTMETH_INFO').allowBlank=true;
						comContactInfoForm.getForm().findField('COUNTRY').allowBlank=false;
						if(this.value==200|| this.value==201|| this.value==202|| this.value==203
								|| this.value==2031|| this.value==2032|| this.value==2033|| this.value==204|| this.value==2041|| this.value==2042|| this.value==2043|| this.value==205|| this.value==206){
							comContactInfoForm.getForm().findField('AREA').setReadOnly(false);
							comContactInfoForm.getForm().findField('AREA').removeClass('x-readOnly');
						}else{
							comContactInfoForm.getForm().findField('AREA').setReadOnly(true);
							comContactInfoForm.getForm().findField('AREA').addClass('x-readOnly');
						}
						comContactInfoForm.getForm().findField('NUMBER').allowBlank=false;
					}
				}
			}},
		{xtype:'textarea',name:'CONTMETH_INFO',fieldLabel: '<font color="red">*</font>联系方式内容',anchor:'90%',allowBlank:false,maxLength:100},
		{xtype: 'compositefield',name:'CONTMETH_INFO_SP',fieldLabel: '<font color="red">*</font>联系方式内容',anchor:'90%',combineErrors: false,items: [
            {
				xtype : 'displayfield',
				value : '国别码'
			}, {
				xtype : 'combo',
				name : 'COUNTRY',
				mode : 'local',
				triggerAction:  'all',
                forceSelection: true,
				hiddenName:'COUNTRY',
				displayField:'key',
                valueField:'value',
				width : 80,
				allowBlank : false,
				store:  new Ext.data.Store({
					restful : true,
					autoLoad : true,
                      fields : ['key', 'value'],
                      proxy : new Ext.data.HttpProxy( {
                   		url : basepath + '/lookup.json?name=IDD_CODE'
                   	 }),
                   	reader : new Ext.data.JsonReader( {
                   		root : 'JSON'
                   	},['key','value'])
                   })
			}, {
				xtype : 'displayfield',
				value : '地区码'
			}, {
				name : 'AREA',
				xtype : 'numberfield',
				width : 55
			}, {
				xtype : 'displayfield',
				value : '号码'
			}, {
				name : 'NUMBER',
				xtype : 'numberfield',
				width : 170,
				allowBlank : false
			}
          ]
      },
        {xtype:'textarea',name:'REMARK',fieldLabel: '备注',anchor:'90%',maxLength:100},
        {xtype:'displayfield',name: 'QTIPS',fieldLabel:'<font color=red>注</font>',anchor:'90%'
    		,value:'<font color=red>“手机电话(1/2/3)”、“办公电话(1/2/3)”、“家庭电话(1/2/3)”、“移动电话(1/2/3)”、“传真”为原核心系统联系方式类型！\n</font>'}
    ]
});

// create the comContactInfo window
var comContactInfoWindow = new Ext.Window({
    title:'新增OR修改',
    width: 600,
    height: 300,
    layout: 'fit',
    closable: true,
    closeAction: 'hide',
    modal: true,
    buttonAlign : 'center',
    items : [comContactInfoForm],
    buttons:[{
    	text:'暂存',
    	handler:function(){
    		if (!comContactInfoForm.getForm().isValid()) {
                Ext.Msg.alert("提示", "输入有误或存在漏输项，请重新输入！");
                return false;
            }
    		var CONTMETH_TYPE= comContactInfoForm.getForm().findField('CONTMETH_TYPE').getValue();
    		var COUNTRY=comContactInfoForm.getForm().findField('COUNTRY').getValue();
			var AREA=comContactInfoForm.getForm().findField('AREA').getValue();
			var NUMBER=comContactInfoForm.getForm().findField('NUMBER').getValue();
			if( CONTMETH_TYPE==100 || CONTMETH_TYPE==101 || CONTMETH_TYPE==102 || CONTMETH_TYPE==103|| CONTMETH_TYPE==104|| CONTMETH_TYPE==105|| CONTMETH_TYPE==200|| CONTMETH_TYPE==201|| CONTMETH_TYPE==202|| CONTMETH_TYPE==203
					|| CONTMETH_TYPE==2031|| CONTMETH_TYPE==2032|| CONTMETH_TYPE==2033|| CONTMETH_TYPE==204|| CONTMETH_TYPE==2041|| CONTMETH_TYPE==2042|| CONTMETH_TYPE==2043|| CONTMETH_TYPE==205|| CONTMETH_TYPE==206 || CONTMETH_TYPE==2071 
					|| CONTMETH_TYPE==2072|| CONTMETH_TYPE==2073|| CONTMETH_TYPE==2074|| CONTMETH_TYPE==208|| CONTMETH_TYPE==209|| CONTMETH_TYPE==210|| CONTMETH_TYPE==211|| CONTMETH_TYPE==212){
				if(CONTMETH_TYPE==200|| CONTMETH_TYPE==201|| CONTMETH_TYPE==202|| CONTMETH_TYPE==203
						|| CONTMETH_TYPE==2031|| CONTMETH_TYPE==2032|| CONTMETH_TYPE==2033|| CONTMETH_TYPE==204|| CONTMETH_TYPE==2041|| CONTMETH_TYPE==2042|| CONTMETH_TYPE==2043|| CONTMETH_TYPE==205|| CONTMETH_TYPE==206){
    			}else{
    				if(COUNTRY==886 && NUMBER.toString().length!=9){
    					Ext.Msg.alert("提示", "台湾省手机号码为9位！");
    	                return false;
    				}else if(COUNTRY==86 && NUMBER.toString().length!=11){
    					Ext.Msg.alert("提示", "中国手机号码为11位！");
    				    return false;
    				}
    			}
			}
    		if( CONTMETH_TYPE==100 || CONTMETH_TYPE==101 || CONTMETH_TYPE==102 || CONTMETH_TYPE==103|| CONTMETH_TYPE==104|| CONTMETH_TYPE==105|| CONTMETH_TYPE==200|| CONTMETH_TYPE==201|| CONTMETH_TYPE==202|| CONTMETH_TYPE==203
					|| CONTMETH_TYPE==2031|| CONTMETH_TYPE==2032|| CONTMETH_TYPE==2033|| CONTMETH_TYPE==204|| CONTMETH_TYPE==2041|| CONTMETH_TYPE==2042|| CONTMETH_TYPE==2043|| CONTMETH_TYPE==205|| CONTMETH_TYPE==206 || CONTMETH_TYPE==2071 
					|| CONTMETH_TYPE==2072|| CONTMETH_TYPE==2073|| CONTMETH_TYPE==2074|| CONTMETH_TYPE==208|| CONTMETH_TYPE==209|| CONTMETH_TYPE==210|| CONTMETH_TYPE==211|| CONTMETH_TYPE==212){
    			var contactNumber='';
    			if(CONTMETH_TYPE==200|| CONTMETH_TYPE==201|| CONTMETH_TYPE==202|| CONTMETH_TYPE==203
						|| CONTMETH_TYPE==2031|| CONTMETH_TYPE==2032|| CONTMETH_TYPE==2033|| CONTMETH_TYPE==204|| CONTMETH_TYPE==2041|| CONTMETH_TYPE==2042|| CONTMETH_TYPE==2043|| CONTMETH_TYPE==205|| CONTMETH_TYPE==206){
    				if(NUMBER==''){
    					Ext.Msg.alert("提示", "号码不准为0！");
    				    return false;
    				}
    				if(AREA==''){
    					AREA='#'
    				}
    				contactNumber=COUNTRY+'-'+AREA+'-'+NUMBER;
    			}else{
    				if(NUMBER==''){
    					Ext.Msg.alert("提示", "号码不准为0！");
    				    return false;
    				}
    				contactNumber=COUNTRY+'-'+NUMBER;
    			}
    			comContactInfoForm.getForm().findField('CONTMETH_INFO').setValue(contactNumber);
    		}
            var CONTMETH_INFO=comContactInfoForm.getForm().findField('CONTMETH_INFO').getValue();
            if(!noChinaCom(CONTMETH_INFO)){
            	return false;
            };
            var tempJson = comContactInfoForm.getForm().getValues(false);
            var validFlag = false;//判断类型是否已存在,由于不允许修改类型,故不作与原值比较
	        comContactInfoStore.each(function(record){
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
            if(comContactInfoWindow.title =='修改'){
            	var tempRowData = comContactGridPanel.getSelectionModel().getSelections()[0].data;
            	//移除当前修改数据
            	comContactInfoStore.removeAt(comContactInfoStore.findExact('CONTMETH_ID', tempRowData.CONTMETH_ID));
            	tempRowData.CONTMETH_TYPE = tempJson.CONTMETH_TYPE;
            	tempRowData.CONTMETH_TYPE_ORA = comContactInfoForm.getForm().findField('CONTMETH_TYPE').getRawValue();
            	tempRowData.CONTMETH_INFO = tempJson.CONTMETH_INFO;
            	tempRowData.REMARK = tempJson.REMARK;
            	tempRowData.LAST_UPDATE_SYS = 'CRM';
            	tempRowData.LAST_UPDATE_USER = JsContext._userId;
            	tempRowData.LAST_UPDATE_TM = _sysCurrDate;
            	tempRowData.IS_ADD_FLAG = Number(tempJson.CONTMETH_ID) > 0?'0':'1';
            	var tempRecord = new Ext.data.Record(tempRowData,null);
            	comContactInfoStore.addSorted(tempRecord);
            }else{
	            var tempRowData = {
	            	CONTMETH_ID : -(new Date().getTime()),
	            	CUST_ID : custId,
	            	CONTMETH_TYPE : tempJson.CONTMETH_TYPE,
	            	CONTMETH_TYPE_ORA : comContactInfoForm.getForm().findField('CONTMETH_TYPE').getRawValue(),
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
	            comContactInfoStore.addSorted(tempRecord);
            }
            comContactInfoStore.sort('CONTMETH_TYPE','ASC');
            comContactInfoWindow.hide();
    	}
    },{
        text: '返回',
        handler: function(){
            comContactInfoWindow.hide();
        }
    }]
});

//create the toolbar
var comContactInfoTbar = new Ext.Toolbar({
    items: [{
        text: '新增',
        hidden:true,
        id:'pub_fsx_2-31',
        handler: function(){
            if(comContactInfoForm.getForm().getEl()){
                comContactInfoForm.getForm().getEl().dom.reset();
            }
            comContactInfoWindow.setTitle('新增');
            comContactInfoForm.getForm().findField('CONTMETH_TYPE').setReadOnly(false);
            comContactInfoForm.getForm().findField('CONTMETH_TYPE').removeClass('x-readOnly');
            comContactInfoForm.getForm().findField('CONTMETH_INFO_SP').hide();
            comContactInfoForm.getForm().findField('CONTMETH_INFO').hide();
            comContactInfoWindow.show();
        }
    },{
        text: '修改',
        hidden:true,
        id:'pub_fsx_2-32',
        handler: function(){
            var selectLength = comContactGridPanel.getSelectionModel().getSelections().length;
            var selectRecord = comContactGridPanel.getSelectionModel().getSelections()[0];
            if(selectLength != 1){
                Ext.Msg.alert('提示','请选择一条记录!');
                return false;
            }
            comContactInfoWindow.setTitle('修改');
            comContactInfoForm.getForm().findField('CONTMETH_TYPE').setReadOnly(true);
            comContactInfoForm.getForm().findField('CONTMETH_TYPE').addClass('x-readOnly');
            comContactInfoForm.getForm().loadRecord(selectRecord);
            var CONTMETH_TYPE= comContactInfoForm.getForm().findField('CONTMETH_TYPE').getValue(); 
            if(CONTMETH_TYPE!=100 && CONTMETH_TYPE!=101 && CONTMETH_TYPE!=102 && CONTMETH_TYPE!=103&& CONTMETH_TYPE!=104&& CONTMETH_TYPE!=105&& CONTMETH_TYPE!=200&& CONTMETH_TYPE!=201&& CONTMETH_TYPE!=202&& CONTMETH_TYPE!=203
					&& CONTMETH_TYPE!=2031&& CONTMETH_TYPE!=2032&& CONTMETH_TYPE!=2033&& CONTMETH_TYPE!=204&& CONTMETH_TYPE!=2041&& CONTMETH_TYPE!=2042&& CONTMETH_TYPE!=2043&& CONTMETH_TYPE!=205&& CONTMETH_TYPE!=206 && CONTMETH_TYPE!=2071 
					&& CONTMETH_TYPE!=2072&& CONTMETH_TYPE!=2073&& CONTMETH_TYPE!=2074&& CONTMETH_TYPE!=208&& CONTMETH_TYPE!=209&& CONTMETH_TYPE!=210&& CONTMETH_TYPE!=211&& CONTMETH_TYPE!=212){
            	comContactInfoForm.getForm().findField('CONTMETH_INFO_SP').hide();
            	comContactInfoForm.getForm().findField('CONTMETH_INFO').show();
        		comContactInfoForm.getForm().findField('COUNTRY').allowBlank=true;
        		comContactInfoForm.getForm().findField('NUMBER').allowBlank=true;
            }else{
            	comContactInfoForm.getForm().findField('CONTMETH_INFO_SP').show();
            	comContactInfoForm.getForm().findField('CONTMETH_INFO').hide();
            	var CONTMETH_INFO=comContactInfoForm.getForm().findField('CONTMETH_INFO').getValue();
            	var CONTMETH_INFO_arry=CONTMETH_INFO.split('-');
            	if(CONTMETH_TYPE==200|| CONTMETH_TYPE==201|| CONTMETH_TYPE==202|| CONTMETH_TYPE==203
						|| CONTMETH_TYPE==2031|| CONTMETH_TYPE==2032|| CONTMETH_TYPE==2033|| CONTMETH_TYPE==204|| CONTMETH_TYPE==2041|| CONTMETH_TYPE==2042|| CONTMETH_TYPE==2043|| CONTMETH_TYPE==205|| CONTMETH_TYPE==206){
            		comContactInfoForm.getForm().findField('NUMBER').setValue(CONTMETH_INFO_arry[CONTMETH_INFO_arry.length-1]);
            		comContactInfoForm.getForm().findField('AREA').setValue(CONTMETH_INFO_arry[CONTMETH_INFO_arry.length-2]);
            		comContactInfoForm.getForm().findField('COUNTRY').setValue(CONTMETH_INFO_arry[CONTMETH_INFO_arry.length-3]);
            		comContactInfoForm.getForm().findField('COUNTRY').allowBlank=false;
            		comContactInfoForm.getForm().findField('NUMBER').allowBlank=false;
            	}else{
            		comContactInfoForm.getForm().findField('NUMBER').setValue(CONTMETH_INFO_arry[CONTMETH_INFO_arry.length-1]);
            		comContactInfoForm.getForm().findField('COUNTRY').setValue(CONTMETH_INFO_arry[CONTMETH_INFO_arry.length-2]);
            		comContactInfoForm.getForm().findField('AREA').setValue('');
            		comContactInfoForm.getForm().findField('NUMBER').allowBlank=false;
            		comContactInfoForm.getForm().findField('COUNTRY').allowBlank=false;
            	}
            	
            }
            comContactInfoWindow.show();
        }
    },{
        text: '移除',
        hidden:true,
        id:'pub_fsx_2-33',
        handler: function(){
            var selectLength = comContactGridPanel.getSelectionModel().getSelections().length;
            var selectRecord = comContactGridPanel.getSelectionModel().getSelections()[0];
            if(selectLength != 1 || selectRecord.data.IS_ADD_FLAG != '1'){
                Ext.Msg.alert('提示','请选择一条新增暂存的记录!');
                return false;
            }
            comContactInfoStore.remove(selectRecord);
        }
    }]
});

// create the addrGridPanel
var comContactGridPanel = new Ext.grid.GridPanel({
	height: 180,
    region: 'center',
    autoScroll: true,
    stripeRows: true,
    store: comContactInfoStore,
    cm : comContactInfoCm,
    tbar: comContactInfoTbar,
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
comContactInfoStore.on('beforeload',function(){
	comContactInfoStore.baseParams = {
		custId : custId
	};
});
var comHolderInfoRowNumber = new Ext.grid.RowNumberer({
    header:'NO.',
    width:35
});
// create the data columnModel
var comHolderInfoCm = new Ext.grid.ColumnModel([
    comHolderInfoRowNumber,
    {dataIndex:'HOLDER_TYPE',header:'股东类型',width:100,sortable:true,hidden:true},
    {dataIndex:'HOLDER_NAME',header:'姓名',width:100,sortable:true},
    {dataIndex:'IDENT_TYPE',header:'证件类型',width:100,sortable:true,hidden:true}, 
    {dataIndex:'IDENT_TYPE_ORA',header:'证件类型',width:100,sortable:true}, 
    {dataIndex:'IDENT_NO',header:'证件号码',width:150,sortable:true}, 
    {dataIndex:'IDENT_EXPIRED_DATE',header:'证件有效期限',width:100,sortable:true}, 
    {dataIndex:'HOLDER_PER_MOBILE',header:'联系电话',width:100,sortable:true}, 
    {dataIndex:'STOCK_PERCENT',header:'控股/表决权比例（%）',width:150,sortable:true}, 
    {dataIndex:'REMARK',header:'控制方式',width:100,sortable:true}, 
    {dataIndex:'HOLDER_PER_IND_POS',header:'职务',width:100,sortable:true}, 
    {dataIndex:'HOLDER_PER_POST_ADDR',header:'居住/联系地址',width:150,sortable:true}
]);
// create the data record
var comHolderInfoRecord = new Ext.data.Record.create([
    {name:'HOLDER_ID'},
    {name:'CUST_ID'},
    {name:'HOLDER_TYPE'},
    {name:'HOLDER_TYPE_ORA'},
    {name:'HOLDER_NAME'},
    {name:'IDENT_TYPE'},
    {name:'IDENT_TYPE_ORA'},
    {name:'IDENT_NO'},
    {name:'IDENT_EXPIRED_DATE'},
    {name:'HOLDER_PER_MOBILE'},
    {name:'STOCK_PERCENT'},
    {name:'REMARK'},
    {name:'HOLDER_PER_IND_POS'},
    {name:'HOLDER_PER_POST_ADDR'},
    {name:'LAST_UPDATE_SYS'},
    {name:'LAST_UPDATE_USER'},
    {name:'LAST_UPDATE_TM'}
]);
// create the data store
var comHolderInfoStore = new Ext.data.Store({
    restful:true,
    proxy: new Ext.data.HttpProxy({
        url: basepath + '/acrmFCiOrgHolderinfo.json',
        method:'GET'
    }),
    reader: new Ext.data.JsonReader({
        root:'json.data',
        totalProperty:'json.count'
    },comHolderInfoRecord)
});

comHolderInfoStore.on('beforeload',function(){
	comHolderInfoStore.baseParams = {
		custId : custId
	};
});
// create the maintain formpanel
var comHolderInfoForm = new Ext.FormPanel({
    frame : true,
    autoScroll : true,
    split : true,
    items : [{
    	layout:'column',
    	items:[{
    		columnWidth : .5,
    		layout : 'form',
    		items : [
    		         {xtype:'textfield',name:'HOLDER_ID',hidden:true,anchor:'90%'},
    		         {xtype:'textfield',name:'CUST_ID',hidden:true,anchor:'90%'},
    		         {xtype:'textfield',name:'HOLDER_TYPE',hidden:true,anchor:'90%'},
    		         {xtype:'textfield',name:'HOLDER_NAME',fieldLabel:'<font color="red">*</font>姓名',hidden:false,anchor:'90%'},
    		         {xtype:'textfield',name:'IDENT_NO',fieldLabel: '证件号码',anchor:'90%',maxLength:40},
    		         {xtype:'textfield',name:'HOLDER_PER_MOBILE',fieldLabel: '联系电话',anchor:'90%',/*vtype:'telephone',*/maxLength:20},
    		         {
    		        	 xtype:'combo',
    		        	 name:'REMARK',
    		        	 fieldLabel:'控制方式',
    		        	 anchor:'90%',
    		        	 triggerAction:'all',//是否开启自动查询功能
    		        	 store: new Ext.data.ArrayStore({
    					        id: 0,
    					        fields: ['key','value'],
    					        data: [[1, '直接'], [2, '间接']]
    					 }),
    		        	 displayField:'value',
    		        	 valueField:'key',
    		        	 editable:false,
    		        	 mode:'local'
    		        	 //emptyText:'请选择'
    		         },
    		         {xtype:'textfield',name:'HOLDER_PER_POST_ADDR',fieldLabel: '居住/联系地址',anchor:'90%'}
    		        ]
    	},{
    		columnWidth : .5,
    		layout : 'form',
    		items : [{xtype : 'combo',name : 'IDENT_TYPE',hiddenName : 'IDENT_TYPE',fieldLabel : '证件类型',store : identTypeStore,resizable : true,valueField : 'key',displayField : 'value',
					mode : 'local',forceSelection : true,triggerAction : 'all',anchor : '90%'},
				{
     			 layout : 'column',
	 			 items:[{
	 			 		 columnWidth : .7,
						 layout : 'form',
						 items :[{
						 		  xtype:'datefield',name:'IDENT_EXPIRED_DATE',fieldLabel: '证件失效日期',format:'Y-m-d',anchor:'98%',
						 		  listeners:{
											'change': function(obj,oldValue,newValue){
												var com_RILONGTERM = comHolderInfoForm.getForm().findField('HOLDERLONGTERM');
													if(this.value == '9999-12-31'){
				            							  com_RILONGTERM.setValue(true);
				            						}else{
														  com_RILONGTERM.setValue(false);
													};
											}
									  	}
	 			 				}]
					  },{
		        		 columnWidth : .3,
						 layout : 'form',
						 labelWidth:1,
						 items :[{
						     	   xtype:'checkbox',
								   boxLabel:"长期有效",
								   name:'HOLDERLONGTERM',
								   inputvalue:'1',
								   listeners:{
										'check':function(obj,ischeck){
											var IDENT_EXPIRED_DATE = comHolderInfoForm.getForm().findField('IDENT_EXPIRED_DATE');
												if (ischeck == true){
													IDENT_EXPIRED_DATE.setValue("9999-12-31");  
													IDENT_EXPIRED_DATE.setReadOnly(true);
												}else{
													IDENT_EXPIRED_DATE.setValue("");  
													IDENT_EXPIRED_DATE.setReadOnly(false);
												 }
											}
								  		}
			    	               }]
 			             }]
				},{name:'STOCK_PERCENT',fieldLabel:'持股比例(%)',xtype:'numberfield',anchor:'90%'},
				  {name:'HOLDER_PER_IND_POS',fieldLabel:'职务',xtype:'textfield',anchor:'90%'}
				]
    	}]
    }/*,{xtype:'displayfield',name:'QTIPS',fieldLabel:'<font color=red>注</font>',anchor:'90%'
    	,value:'sjb'}*/
    ]
});

// create the comContactInfo window
var comHolderInfoWindow = new Ext.Window({
    title:'新增OR修改',
    width: 600,
    height: 300,
    layout: 'fit',
    closable: true,
    closeAction: 'hide',
    modal: true,
    buttonAlign : 'center',
    items : [comHolderInfoForm],
    buttons:[{
    	text:'暂存',
    	handler:function(){
    		//校验
    		if (!comHolderInfoForm.getForm().isValid()) {
                Ext.Msg.alert("提示", "输入有误或存在漏输项，请重新输入！");
                return false;
            }
            var holderName = comHolderInfoForm.getForm().findField('HOLDER_NAME').getValue();
            if(holderName.replace(/(^\s*)|(\s*$)/g,'') == ""){
            	Ext.Msg.alert("提示", "姓名不能为空,请重新输入！");
                return false;
            }
            var MOBILE=comHolderInfoForm.getForm().findField('HOLDER_PER_MOBILE').getValue();//联系电话
            //联系电话检验
            if(!noChinaCom2(MOBILE,'MOBILE')){
            	return false;
            }
            debugger;
			var tempJson = comHolderInfoForm.getForm().getValues(false);
			if(comHolderInfoWindow.title =='修改'){
	        	var tempRowData = comHolderGridPanel.getSelectionModel().getSelections()[0].data;
	        	//移除当前修改数据
	        	comHolderInfoStore.removeAt(comHolderInfoStore.findExact('HOLDER_ID',tempRowData.HOLDER_ID));//股东ID
	        	tempRowData.HOLDER_TYPE = tempJson.HOLDER_TYPE;
	        	tempRowData.HOLDER_TYPE_ORA = comHolderInfoForm.getForm().findField('HOLDER_TYPE').getRawValue();
	        	tempRowData.HOLDER_NAME = tempJson.HOLDER_NAME;
	        	tempRowData.IDENT_TYPE = tempJson.IDENT_TYPE;
	        	tempRowData.IDENT_TYPE_ORA = comHolderInfoForm.getForm().findField('IDENT_TYPE').getRawValue();
	        	tempRowData.IDENT_NO = tempJson.IDENT_NO;
	        	tempRowData.IDENT_EXPIRED_DATE = tempJson.IDENT_EXPIRED_DATE;
	        	tempRowData.HOLDER_PER_MOBILE = tempJson.HOLDER_PER_MOBILE;
	        	tempRowData.STOCK_PERCENT = tempJson.STOCK_PERCENT;
	        	tempRowData.REMARK = tempJson.REMARK;
	        	tempRowData.HOLDER_PER_IND_POS = tempJson.HOLDER_PER_IND_POS;
	        	tempRowData.HOLDER_PER_POST_ADDR = tempJson.HOLDER_PER_POST_ADDR;
	            tempRowData.LAST_UPDATE_SYS = 'CRM';
	        	tempRowData.LAST_UPDATE_USER = JsContext._userId;
	        	tempRowData.LAST_UPDATE_TM = _sysCurrDate;
	        	tempRowData.IS_ADD_FLAG = Number(tempJson.HOLDER_ID) > 0 ? '0' : '1';
	        	var tempRecord = new Ext.data.Record(tempRowData,null);
	        	comHolderInfoStore.addSorted(tempRecord);
	        }else{
	            var tempRowData = {
	            	HOLDER_ID : -(new Date().getTime()),//股东ID
	            	CUST_ID : custId,//客户编号
	                HOLDER_TYPE : tempJson.HOLDER_TYPE,//股东类型
	                HOLDER_TYPE_ORA : comHolderInfoForm.getForm().findField('HOLDER_TYPE').getRawValue(),//证件类型display
	                HOLDER_NAME : tempJson.HOLDER_NAME,//股东名称
	            	IDENT_TYPE : tempJson.IDENT_TYPE,//证件类型
	            	IDENT_TYPE_ORA : comHolderInfoForm.getForm().findField('IDENT_TYPE').getRawValue(),//证件类型display
	            	IDENT_NO : tempJson.IDENT_NO,//证件号码
	            	IDENT_EXPIRED_DATE : tempJson.IDENT_EXPIRED_DATE,//证件有效期限
	            	HOLDER_PER_MOBILE : tempJson.HOLDER_PER_MOBILE,//联系电话
	            	STOCK_PERCENT : tempJson.STOCK_PERCENT,//控股/表决权比例	
	            	REMARK : tempJson.REMARK,//控制方式
	            	HOLDER_PER_IND_POS : tempJson.HOLDER_PER_IND_POS,//职务
	            	HOLDER_PER_POST_ADDR : tempJson.HOLDER_PER_POST_ADDR,//居住/联系地址
	            	LAST_UPDATE_SYS : 'CRM',
	            	LAST_UPDATE_USER : JsContext._userId,
	            	LAST_UPDATE_TM : _sysCurrDate,//2014-10-29
	            	IS_ADD_FLAG : '1'//新增
	            };
	            var tempRecord = new Ext.data.Record(tempRowData,null);
	            comHolderInfoStore.addSorted(tempRecord);
	        }
			comHolderInfoStore.sort('HOLDER_TYPE','ASC');
            comHolderInfoWindow.hide();
    	}
    },{
        text: '返回',
        handler: function(){
            comHolderInfoWindow.hide();
        }
    }]
});

//create the toolbar
var comHolderInfoTbar = new Ext.Toolbar({
    items: [{
        text: '新增',
        hidden:true,
        id:'pub_fsx_2-41',
        handler: function(){
        	if(comHolderInfoForm.getForm().getEl()){
        		comHolderInfoForm.getForm().getEl().dom.reset();
            }
            var HOLDERLONGTERM = comHolderInfoForm.getForm().findField('HOLDERLONGTERM');
            HOLDERLONGTERM.setValue(false);
        	comHolderInfoWindow.setTitle('新增');
            comHolderInfoWindow.show();
        }
    },{
        text: '修改',
        hidden:true,
        id:'pub_fsx_2-42',
        handler: function(){
        	var selectLength = comHolderGridPanel.getSelectionModel().getSelections().length;
            var selectRecord = comHolderGridPanel.getSelectionModel().getSelections()[0];
            if(selectLength != 1){
                Ext.Msg.alert('提示','请选择一条记录!');
                return false;
            }
            comHolderInfoWindow.setTitle('修改');
            comHolderInfoForm.getForm().findField('HOLDER_TYPE').setReadOnly(true);
            comHolderInfoForm.getForm().findField('HOLDER_TYPE').addClass('x-readOnly');
            comHolderInfoForm.getForm().loadRecord(selectRecord);
            comHolderInfoWindow.show();
        }
    },{
        text: '移除',
        hidden:true,
        id:'pub_fsx_2-43',
        handler: function(){
        	var selectLength = comHolderGridPanel.getSelectionModel().getSelections().length;
            var selectRecord = comHolderGridPanel.getSelectionModel().getSelections()[0];
            if(selectLength != 1 || selectRecord.data.IS_ADD_FLAG != '1'){
                Ext.Msg.alert('提示','请选择一条新增暂存的记录!');
                return false;
            }
            comHolderInfoStore.remove(selectRecord);
        }
    }]
});

// create the addrGridPanel
var comHolderGridPanel = new Ext.grid.GridPanel({
	height: 180,
    region: 'center',
    autoScroll: true,
    stripeRows: true,
    store: comHolderInfoStore,
    cm : comHolderInfoCm,
    tbar: comHolderInfoTbar,
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
 * 查询对公非授信信息第二屏
 */
window.queryFsxComSecFn = function(){
	comAddrCustInfoStore.load({
		callback: function(){
			tempComAddrStore.removeAll();
			comAddrCustInfoStore.each(function(record){
				var obj = {};
				Ext.apply(obj,record.data);
				var tempRecord = new Ext.data.Record(obj,null);
        		tempComAddrStore.addSorted(tempRecord);
			});
		}
	});
 	comContactPersonStore.load({
		callback: function(){
			tempComContactPersonStore.removeAll();
			comContactPersonStore.each(function(record){
				var obj = {};
				Ext.apply(obj,record.data);
				var tempRecord = new Ext.data.Record(obj,null);
        		tempComContactPersonStore.addSorted(tempRecord);
			});
		}
	});
	comContactInfoStore.load({
		callback: function(){
			tempComContactInfoStore.removeAll();
			comContactInfoStore.each(function(record){
				var obj = {};
				Ext.apply(obj,record.data);
				var tempRecord = new Ext.data.Record(obj,null);
        		tempComContactInfoStore.addSorted(tempRecord);
			});
		}
	});
	comHolderInfoStore.load({
		callback: function(){
			tempComHolderInfoStore.removeAll();
			comHolderInfoStore.each(function(record){
				var obj = {};
				Ext.apply(obj,record.data);
				var tempRecord = new Ext.data.Record(obj,null);
				tempComHolderInfoStore.addSorted(tempRecord);
			});
		}
	});
};

/**
 * 返回地址信息变更历史
 * @return allUpdateModelArr
 */
window.getFsxSecComModel_1 = function(){
	var allUpdateModelArr = [];
	comAddrCustInfoStore.each(function(record){
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
			var tempData = tempComAddrStore.getAt(tempComAddrStore.findExact('ADDR_ID', record.data.ADDR_ID)).data;
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
 * 返回股东信息变更历史
 * @return allUpdateModelArr
 */
window.getFsxSecComModel_4 = function(){
	debugger;
	var allUpdateModelArr = [];
	//遍历
	comHolderInfoStore.each(function(record){
		//1新增,0修改
		if(record.data.IS_ADD_FLAG == '1'){
			var perModel = [];
			addFieldFn(perModel,custId,'ID_SEQUENCE.NEXTVAL','','COM_HOLDER_ID','1');//主键字段
			addFieldFn(perModel,custId,custId,custId,'COM_CUST_ID','');
			if(record.data.HOLDER_TYPE != ''){//股东类型
				addFieldViewFn(perModel,custId,'',record.data.HOLDER_TYPE,'COM_HOLDER_TYPE','','1',record.data.HOLDER_TYPE_ORA,'股东类型');
			}
			if(record.data.HOLDER_NAME != ''){//股东姓名
				addFieldViewFn(perModel,custId,'',record.data.HOLDER_NAME,'COM_HOLDER_NAME','','1',record.data.HOLDER_NAME,'股东姓名');
			}
			if(record.data.IDENT_TYPE != ''){//证件类型
				addFieldViewFn(perModel,custId,'',record.data.IDENT_TYPE,'COM_IDENT_TYPE','','1',record.data.IDENT_TYPE_ORA,'证件类型');
			}
			if(record.data.IDENT_NO != ''){//证件号码
				addFieldViewFn(perModel,custId,'',record.data.IDENT_NO,'COM_IDENT_NO','','1',record.data.IDENT_NO,'证件号码');
			}
			if(record.data.IDENT_EXPIRED_DATE != ''){//证件有效期限
				addFieldViewFn(perModel,custId,'',record.data.IDENT_EXPIRED_DATE,'COM_IDENT_EXPIRED_DATE','','2',record.data.IDENT_EXPIRED_DATE,'证件有效期限');
			}
			if(record.data.HOLDER_PER_MOBILE != ''){//联系电话
				addFieldViewFn(perModel,custId,'',record.data.HOLDER_PER_MOBILE,'COM_HOLDER_PER_MOBILE','','1',record.data.HOLDER_PER_MOBILE,'联系电话');
			}
			if(record.data.STOCK_PERCENT != ''){//控股/表决权比例
				addFieldViewFn(perModel,custId,'',record.data.STOCK_PERCENT,'COM_STOCK_PERCENT','','1',record.data.STOCK_PERCENT,'控股/表决权比例');
			}
			if(record.data.REMARK != ''){//控制方式
				addFieldViewFn(perModel,custId,'',record.data.REMARK,'COM_REMARK','','1',record.data.REMARK,'控制方式');
			}
			if(record.data.HOLDER_PER_IND_POS != ''){//职务
				addFieldViewFn(perModel,custId,'',record.data.HOLDER_PER_IND_POS,'COM_HOLDER_PER_IND_POS','','1',record.data.HOLDER_PER_IND_POS,'职务');
			}
			if(record.data.HOLDER_PER_POST_ADDR != ''){//居住/联系地址
				addFieldViewFn(perModel,custId,'',record.data.HOLDER_PER_POST_ADDR,'COM_HOLDER_PER_POST_ADDR','','1',record.data.HOLDER_PER_POST_ADDR,'居住/联系地址');
			}
			addFieldFn(perModel,custId,'',record.data.LAST_UPDATE_SYS,'COM_LAST_UPDATE_SYS','');
			addFieldFn(perModel,custId,'',record.data.LAST_UPDATE_USER,'COM_LAST_UPDATE_USER','');
			addFieldFn(perModel,custId,'',record.data.LAST_UPDATE_TM,'COM_LAST_UPDATE_TM','','2');
			allUpdateModelArr.push({
				IS_ADD_FLAG : '1',
				permodel : perModel
			});
		}else if(record.data.IS_ADD_FLAG == '0'){
			var isActUpdateFlag = false;
			var tempData = tempComHolderInfoStore.getAt(tempComHolderInfoStore.findExact('HOLDER_ID', record.data.HOLDER_ID)).data;
			var perModel = [];
			addFieldFn(perModel,custId,tempData.HOLDER_ID,tempData.HOLDER_ID,'COM_HOLDER_ID','1');//主键字段
			addFieldFn(perModel,custId,custId,custId,'COM_CUST_ID','');
			if(record.data.HOLDER_TYPE != tempData.HOLDER_TYPE){
				isActUpdateFlag = true;
				addFieldViewFn(perModel,custId,tempData.HOLDER_TYPE,record.data.HOLDER_TYPE,'COM_HOLDER_TYPE','','1',record.data.HOLDER_TYPE_ORA,'股东类型');
			}
			if(record.data.HOLDER_NAME != tempData.HOLDER_NAME){
				isActUpdateFlag = true;
				addFieldViewFn(perModel,custId,tempData.HOLDER_NAME,record.data.HOLDER_NAME,'COM_HOLDER_NAME','','1',record.data.HOLDER_NAME,'股东姓名');
			}
			if(record.data.IDENT_TYPE != tempData.IDENT_TYPE){
				isActUpdateFlag = true;
				addFieldViewFn(perModel,custId,tempData.IDENT_TYPE,record.data.IDENT_TYPE,'COM_IDENT_TYPE','','1',record.data.IDENT_TYPE_ORA,'证件类型');
			}
			if(record.data.IDENT_NO != tempData.IDENT_NO){
				isActUpdateFlag = true;
				addFieldViewFn(perModel,custId,tempData.IDENT_NO,record.data.IDENT_NO,'COM_IDENT_NO','','1',record.data.IDENT_NO,'证件号码');
			}
			if(record.data.IDENT_EXPIRED_DATE != tempData.IDENT_EXPIRED_DATE){
				isActUpdateFlag = true;
				addFieldViewFn(perModel,custId,tempData.IDENT_EXPIRED_DATE,record.data.IDENT_EXPIRED_DATE,'COM_IDENT_EXPIRED_DATE','','2',record.data.IDENT_EXPIRED_DATE,'证件有效期限');
			}
			if(record.data.HOLDER_PER_MOBILE != tempData.HOLDER_PER_MOBILE){
				isActUpdateFlag = true;
				addFieldViewFn(perModel,custId,tempData.HOLDER_PER_MOBILE,record.data.HOLDER_PER_MOBILE,'COM_HOLDER_PER_MOBILE','','1',record.data.HOLDER_PER_MOBILE,'联系电话');
			}
			if(record.data.STOCK_PERCENT != tempData.STOCK_PERCENT){
				isActUpdateFlag = true;
				addFieldViewFn(perModel,custId,tempData.STOCK_PERCENT,record.data.STOCK_PERCENT,'COM_STOCK_PERCENT','','1',record.data.STOCK_PERCENT,'控股/表决权比例');
			}
			if(record.data.REMARK != tempData.REMARK){
				isActUpdateFlag = true;
				addFieldViewFn(perModel,custId,tempData.REMARK,record.data.REMARK,'COM_REMARK','','1',record.data.REMARK,'控制方式');
			}
			if(record.data.HOLDER_PER_IND_POS != tempData.HOLDER_PER_IND_POS){
				isActUpdateFlag = true;
				addFieldViewFn(perModel,custId,tempData.HOLDER_PER_IND_POS,record.data.HOLDER_PER_IND_POS,'COM_HOLDER_PER_IND_POS','','1',record.data.HOLDER_PER_IND_POS,'职务');
			}
			if(record.data.HOLDER_PER_POST_ADDR != tempData.HOLDER_PER_POST_ADDR){
				isActUpdateFlag = true;
				addFieldViewFn(perModel,custId,tempData.HOLDER_PER_POST_ADDR,record.data.HOLDER_PER_POST_ADDR,'COM_HOLDER_PER_POST_ADDR','','1',record.data.HOLDER_PER_POST_ADDR,'居住/联系地址');
			}
			addFieldFn(perModel,custId,'',record.data.LAST_UPDATE_SYS,'COM_LAST_UPDATE_SYS','');
			addFieldFn(perModel,custId,'',record.data.LAST_UPDATE_USER,'COM_LAST_UPDATE_USER','');
			addFieldFn(perModel,custId,'',record.data.LAST_UPDATE_TM,'COM_LAST_UPDATE_TM','','2');
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
window.getFsxSecComModel_2 = function(){
	var allUpdateModelArr = [];
	comContactPersonStore.each(function(record){
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
			if(record.data.IDENT_EXPIRED_DATE != '')
				addFieldViewFn(perModel,custId,'',record.data.IDENT_EXPIRED_DATE,'PER_IDENT_EXPIRED_DATE','','2',record.data.IDENT_EXPIRED_DATE,'联系人证件失效日期');
			if(record.data.GENDER != '')	
				addFieldViewFn(perModel,custId,'',record.data.GENDER,'PER_GENDER','','1',record.data.GENDER_ORA,'联系人性别');
			if(record.data.BIRTHDAY != '')	
				addFieldViewFn(perModel,custId,'',record.data.BIRTHDAY,'PER_BIRTHDAY','','2',record.data.BIRTHDAY,'联系人出生日期');
			if(record.data.LINKMAN_TITLE != '')
				addFieldViewFn(perModel,custId,'',record.data.LINKMAN_TITLE,'PER_LINKMAN_TITLE','','1',record.data.LINKMAN_TITLE_ORA,'联系人称谓');
			if(record.data.FEX != '')	
				addFieldViewFn(perModel,custId,'',record.data.FEX,'PER_FEX','','1',record.data.FEX,'联系人传真号码');
			if(record.data.HOME_TEL != '')	
				addFieldViewFn(perModel,custId,'',record.data.HOME_TEL,'PER_HOME_TEL','','1',record.data.HOME_TEL,'联系人家庭电话');
			if(record.data.MOBILE != '')
				addFieldViewFn(perModel,custId,'',record.data.MOBILE,'PER_MOBILE','','1',record.data.MOBILE,'联系人手机号码');
			if(record.data.MOBILE2 != '')
				addFieldViewFn(perModel,custId,'',record.data.MOBILE2,'PER_MOBILE2','','1',record.data.MOBILE2,'联系人手机号码2');
			if(record.data.OFFICE_TEL != '')
				addFieldViewFn(perModel,custId,'',record.data.OFFICE_TEL,'PER_OFFICE_TEL','','1',record.data.OFFICE_TEL,'联系人办公电话');
			if(record.data.EMAIL != '')
				addFieldViewFn(perModel,custId,'',record.data.EMAIL,'PER_EMAIL','','1',record.data.EMAIL,'联系人电子邮件');
			addFieldFn(perModel,custId,'',record.data.LAST_UPDATE_SYS,'PER_LAST_UPDATE_SYS','');
			addFieldFn(perModel,custId,'',record.data.LAST_UPDATE_USER,'PER_LAST_UPDATE_USER','');
			addFieldFn(perModel,custId,'',record.data.LAST_UPDATE_TM,'PER_LAST_UPDATE_TM','','2');
			allUpdateModelArr.push({
				IS_ADD_FLAG : '1',
				permodel : perModel
			});
		}else if(record.data.IS_ADD_FLAG == '0'){
			var isActUpdateFlag = false;
			var tempData = tempComContactPersonStore.getAt(tempComContactPersonStore.findExact('LINKMAN_ID', record.data.LINKMAN_ID)).data;
			var perModel = [];
			addFieldFn(perModel,custId,tempData.LINKMAN_ID,tempData.LINKMAN_ID,'PER_LINKMAN_ID','1');//主键字段
			addFieldFn(perModel,custId,custId,custId,'PER_LINKMAN_CUST_ID','');
			if(record.data.LINKMAN_TYPE != tempData.LINKMAN_TYPE){
				isActUpdateFlag = true;
				addFieldViewFn(perModel,custId,tempData.LINKMAN_TYPE,record.data.LINKMAN_TYPE,'PER_LINKMAN_TYPE','','1',record.data.LINKMAN_TYPE_ORA,'联系人类型');
			}if(record.data.LINKMAN_NAME != tempData.LINKMAN_NAME){
				isActUpdateFlag = true;
				addFieldViewFn(perModel,custId,tempData.LINKMAN_NAME,record.data.LINKMAN_NAME,'PER_LINKMAN_NAME','','1',record.data.LINKMAN_NAME,'联系人姓名');
			}if(record.data.IDENT_TYPE != tempData.IDENT_TYPE){
				isActUpdateFlag = true;
				addFieldViewFn(perModel,custId,tempData.IDENT_TYPE,record.data.IDENT_TYPE,'PER_IDENT_TYPE','','1',record.data.IDENT_TYPE_ORA,'联系人证件类型');
			}if(record.data.IDENT_NO != tempData.IDENT_NO){
				isActUpdateFlag = true;
				addFieldViewFn(perModel,custId,tempData.IDENT_NO,record.data.IDENT_NO,'PER_IDENT_NO','','1',record.data.IDENT_NO,'联系人证件号码');
			}if(record.data.IDENT_EXPIRED_DATE != tempData.IDENT_EXPIRED_DATE){
				isActUpdateFlag = true;
				addFieldViewFn(perModel,custId,tempData.IDENT_EXPIRED_DATE,record.data.IDENT_EXPIRED_DATE,'PER_IDENT_EXPIRED_DATE','','2',record.data.IDENT_EXPIRED_DATE,'联系人证件失效日期');
			}if(record.data.GENDER != tempData.GENDER){
				isActUpdateFlag = true;
				addFieldViewFn(perModel,custId,tempData.GENDER_ORA,record.data.GENDER,'PER_GENDER','','1',record.data.GENDER_ORA,'联系人性别');
			}if(record.data.BIRTHDAY != tempData.BIRTHDAY){
				isActUpdateFlag = true;
				addFieldViewFn(perModel,custId,tempData.BIRTHDAY,record.data.BIRTHDAY,'PER_BIRTHDAY','','2',record.data.BIRTHDAY,'联系人出生日期');
			}if(record.data.LINKMAN_TITLE != tempData.LINKMAN_TITLE){
				isActUpdateFlag = true;
				addFieldViewFn(perModel,custId,tempData.LINKMAN_TITLE,record.data.LINKMAN_TITLE,'PER_LINKMAN_TITLE','','1',record.data.LINKMAN_TITLE_ORA,'联系人称谓');
			}if(record.data.FEX != tempData.FEX){
				isActUpdateFlag = true;
				addFieldViewFn(perModel,custId,tempData.FEX,record.data.FEX,'PER_FEX','','1',record.data.FEX,'联系人传真号码');
			}if(record.data.HOME_TEL != tempData.HOME_TEL){
				isActUpdateFlag = true;
				addFieldViewFn(perModel,custId,tempData.HOME_TEL,record.data.HOME_TEL,'PER_HOME_TEL','','1',record.data.HOME_TEL,'联系人家庭电话');
			}if(record.data.MOBILE != tempData.MOBILE){
				isActUpdateFlag = true;
				addFieldViewFn(perModel,custId,tempData.MOBILE,record.data.MOBILE,'PER_MOBILE','','1',record.data.MOBILE,'联系人手机号码');
			}if(record.data.MOBILE2 != tempData.MOBILE2){
				isActUpdateFlag = true;
				addFieldViewFn(perModel,custId,tempData.MOBILE2,record.data.MOBILE2,'PER_MOBILE2','','1',record.data.MOBILE2,'联系人手机号码2');
			}if(record.data.OFFICE_TEL != tempData.OFFICE_TEL){
				isActUpdateFlag = true;
				addFieldViewFn(perModel,custId,tempData.OFFICE_TEL,record.data.OFFICE_TEL,'PER_OFFICE_TEL','','1',record.data.OFFICE_TEL,'联系人办公电话');
			}if(record.data.EMAIL != tempData.EMAIL){
				isActUpdateFlag = true;
				addFieldViewFn(perModel,custId,tempData.EMAIL,record.data.EMAIL,'PER_EMAIL','','1',record.data.EMAIL,'联系人电子邮件');
			}
			addFieldFn(perModel,custId,'',record.data.LAST_UPDATE_SYS,'PER_LAST_UPDATE_SYS','');
			addFieldFn(perModel,custId,'',record.data.LAST_UPDATE_USER,'PER_LAST_UPDATE_USER','');
			addFieldFn(perModel,custId,'',record.data.LAST_UPDATE_TM,'PER_LAST_UPDATE_TM','','2');
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
window.getFsxSecComModel_3 = function(){
	var allUpdateModelArr = [];
	comContactInfoStore.each(function(record){
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
			var tempData = tempComContactInfoStore.getAt(tempComContactInfoStore.findExact('CONTMETH_ID', record.data.CONTMETH_ID)).data;
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