/**
 * 第三页：联系人、联系、证件信息
 */

imports([
'/contents/pages/common/Com.yucheng.bcrm.common.Address.js'  // 地址放大镜
]);
var _sysCurrDate = new Date().format('Y-m-d');
//临时存储地址数据
var tempComAddrStore = new Ext.data.Store();
var tempComContactPersonStore = new Ext.data.Store();
var tempComContactInfoStore = new Ext.data.Store();
var tempComIdentStore=new Ext.data.Store();
//var tempComHolderInfoStore = new Ext.data.Store();

//既有客户需屏蔽所有新增、修改、移除按钮
//潜在客户显示所有新增、修改、移除按钮
//modify by liuming 20170820
var setXiugai=function(potentialFlag){
	if(potentialFlag=='0'){//既有客户
		//地址信息
		Ext.getCmp('com_xz_3-dz').setVisible(false);//新增
		Ext.getCmp('com_yc_3-dz').setVisible(false);//移除
		Ext.getCmp('com_xg_3-dz').setVisible(false);//修改
		//联系人信息
		Ext.getCmp('com_xz_3-lxr').setVisible(false);
		Ext.getCmp('com_yc_3-lxr').setVisible(false);
		Ext.getCmp('com_xg_3-lxr').setVisible(false);
		//联系信息
		Ext.getCmp('com_xz_3-lx').setVisible(false);
		Ext.getCmp('com_yc_3-lx').setVisible(false);
		Ext.getCmp('com_xg_3-lx').setVisible(false);
		//证件信息
		Ext.getCmp('com_xz_3-zj').setVisible(false);
		Ext.getCmp('com_yc_3-zj').setVisible(false);
		Ext.getCmp('com_xg_3-zj').setVisible(false);
		//股东信息
		/*Ext.getCmp('com_xz_3-holder').setVisible(false);
		Ext.getCmp('com_yc_3-holder').setVisible(false);
		Ext.getCmp('com_xg_3-holder').setVisible(false);*/
	}else{
		//地址信息
		Ext.getCmp('com_xz_3-dz').setVisible(true);
		Ext.getCmp('com_yc_3-dz').setVisible(true);
		Ext.getCmp('com_xg_3-dz').setVisible(true);
		//联系人信息
		Ext.getCmp('com_xz_3-lxr').setVisible(true);
		Ext.getCmp('com_yc_3-lxr').setVisible(true);
		Ext.getCmp('com_xg_3-lxr').setVisible(true);
		//联系信息
		Ext.getCmp('com_xz_3-lx').setVisible(true);
		Ext.getCmp('com_yc_3-lx').setVisible(true);
		Ext.getCmp('com_xg_3-lx').setVisible(true);
		//证件信息
		Ext.getCmp('com_xz_3-zj').setVisible(true);
		Ext.getCmp('com_yc_3-zj').setVisible(true);
		Ext.getCmp('com_xg_3-zj').setVisible(true);
		//股东信息
        /*Ext.getCmp('com_xz_3-holder').setVisible(true);
        Ext.getCmp('com_yc_3-holder').setVisible(true);
        Ext.getCmp('com_xg_3-holder').setVisible(true);*/
	}
};
/**
 * 是否是详情状态，即是否蔽所第三页屏有按钮
 * @param {Boolean} ifDetail 是否详情，即是否屏蔽所有按钮。true:是，因此第三页所有增删改按钮;false:否，显示第三页所有增删改按钮
 */
var setDetail=function(ifDetail){
	//地址信息
    Ext.getCmp('com_xz_3-dz').setVisible(!ifDetail);//新增
    Ext.getCmp('com_yc_3-dz').setVisible(!ifDetail);//移除
    Ext.getCmp('com_xg_3-dz').setVisible(!ifDetail);//修改
    //联系人信息
    Ext.getCmp('com_xz_3-lxr').setVisible(!ifDetail);
    Ext.getCmp('com_yc_3-lxr').setVisible(!ifDetail);
    Ext.getCmp('com_xg_3-lxr').setVisible(!ifDetail);
    //联系信息
    Ext.getCmp('com_xz_3-lx').setVisible(!ifDetail);
    Ext.getCmp('com_yc_3-lx').setVisible(!ifDetail);
    Ext.getCmp('com_xg_3-lx').setVisible(!ifDetail);
    //证件信息
    Ext.getCmp('com_xz_3-zj').setVisible(!ifDetail);
    Ext.getCmp('com_yc_3-zj').setVisible(!ifDetail);
    Ext.getCmp('com_xg_3-zj').setVisible(!ifDetail);
};
var allowflag=false;//修改开户证件时校验是否通过；
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

//////////////////////////////////////////////地址信息///////////////////////////////////////////////////////////
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
		url : basepath + '/lookup.json?name=XD000192'
	}),
	reader : new Ext.data.JsonReader( {
		root : 'JSON'
	},['key','value'])
});
/**
 * 代理人国籍
 */
var countryStore =  new Ext.data.Store( {
	restful : true,
	autoLoad : true,
	sortInfo : {
            field:'key',
            direction:'ASC'
        },
	proxy : new Ext.data.HttpProxy( {
		url : basepath + '/lookup.json?name=XD000025'
	}),
	reader : new Ext.data.JsonReader( {
		root : 'JSON'
	},['key','value'])
});
var addrCustInfoRecord = new Ext.data.Record.create([
    {name:'ADDR_ID'},
	{name:'ADDR_TYPE'},
	{name:'ADDR_TYPE_ORA'},
	{name:'ADDR'},
	{name:'ZIPCODE'},
	{name:'ADDRESS_CUST_ID'},
	{name:'ADDR_COUNTRY'},
	{name:'ADDR_COUNTRY_ORA'},
	{name:'ADMIN_ZONE'},
	{name:'ADMIN_ZONE_ID'},
	{name:'ADDRESS_LAST_UPDATE_SYS'},
	{name:'ADDRESS_LAST_UPDATE_USER'},
	{name:'ADDRESS_LAST_UPDATE_TM'},
	{name:'IS_ADD_FLAG'}
]);
/**
 * 潜在客户地址信息数据集
 */
var addrCustInfoStore = new Ext.data.Store({
    restful:true,
    proxy: new Ext.data.HttpProxy({
        url: basepath + '/dealWithComThree!queryAddr.json',
        method:'GET'
    }),
    reader: new Ext.data.JsonReader({
        root:'json.data',
        totalProperty:'json.count'
    },addrCustInfoRecord)
    
});
/**
 * 潜在客户地址信息展示列
 */
var addrCustInfoRowNumber = new Ext.grid.RowNumberer({
    header:'NO.',
    width:35
});
var addrCustInfoCm = new Ext.grid.ColumnModel([
   addrCustInfoRowNumber,
   {dataIndex:'ADDR_TYPE_ORA',header:'地址类型',width : 120,sortable : true},
   {dataIndex:'ADDR_COUNTRY_ORA',header:'国别',width : 120,sortable : true},
   {dataIndex:'ADMIN_ZONE',header:'行政区划',width : 350,sortable : true},
   {dataIndex:'ADDR',header:'详细地址',width : 350,sortable : true},
   {dataIndex:'ZIPCODE',header:'邮政编码',width : 100,sortable : true},
   {dataIndex:'ADDRESS_LAST_UPDATE_SYS',header:'最后更新系统',width : 100,sortable : true}
]);
/**
 * 点击新增OR修改时弹出FORM
 */
var maintainForm = new Ext.FormPanel({
    frame : true,
    autoScroll : true,
    split : true,
    items : [
        {xtype:'textfield',name:'ADDR_ID',hidden:true},
        {xtype:'textfield',name:'ADDRESS_CUST_ID',hidden:true},
        {xtype:'textfield',name:'ADDRESS_LAST_UPDATE_SYS',hidden:true},
        {xtype : 'combo',name : 'ADDR_TYPE',hiddenName : 'ADDR_TYPE',fieldLabel : '<font color="red">*</font>地址类型',store : addrTypeStore,resizable : true,valueField : 'key',displayField : 'value',
			mode : 'local',forceSelection : true,triggerAction : 'all',anchor : '90%',allowBlank:false,	listeners:{
				'focus': {  
			        fn: function(e) {  
			            e.expand();  
			            this.doQuery(this.allQuery, true);  
			        },  
			        buffer:200  
			 }}},
        {xtype : 'combo',id:'country',name : 'ADDR_COUNTRY',hiddenName : 'ADDR_COUNTRY',fieldLabel : '国别',store : countryStore,resizable : true,valueField : 'key',displayField : 'value',
    			mode : 'local',forceSelection : true,triggerAction : 'all',anchor : '90%',	listeners:{
    				'focus': {  
    			        fn: function(e) {  
    			            e.expand();  
    			            this.doQuery(this.allQuery, true);  
    			        },  
    			        buffer:200  
    			 },select:function(combo,record){
    				 var ownerForm=this;
        			 while(ownerForm && !ownerForm.form){
        				 ownerForm=ownerForm.ownerCt;
        			 }
         			var value = combo.value;
         			if(value=='CHN'){//中国
         				ownerForm.form.findField('ADMIN_ZONE').setVisible(true);
         			}else{
         				ownerForm.form.findField('ADMIN_ZONE').setVisible(false);
         				ownerForm.form.findField('ADMIN_ZONE').setValue('');
         				ownerForm.form.findField('ADMIN_ZONE_ID').setValue('');
         			}
    			 },validator:function(){
    				 var ownerForm=this;
	            	  while(ownerForm && !ownerForm.form){
	            			ownerForm=ownerForm.ownerCt;
	            	  }
         			var value = this.value;
         			if(value=='CHN'){//中国
         				ownerForm.form.findField('ADMIN_ZONE').setVisible(true);
         			}else{
         				ownerForm.form.findField('ADMIN_ZONE').setVisible(false);
         				ownerForm.form.findField('ADMIN_ZONE').setValue('');
         				ownerForm.form.findField('ADMIN_ZONE_ID').setValue('');
         				
         			}
    			 
    			 }}},	 
    	{xtype:'textfield',name:'ADMIN_ZONE_ID',hidden:true},
        {xtype:'address',anchor:'90%',maxLength:30,fieldLabel:'行政区划',name:'ADMIN_ZONE',hiddenName:'ADMIN_ZONE_ID',maxLength:100},       
        {xtype:'textarea',name:'ADDR',fieldLabel: '<font color="red">*</font>地址',anchor:'90%',allowBlank:false,maxLength:100},
		{xtype:'textfield',name:'ZIPCODE',fieldLabel: '邮政编码',anchor:'90%',maxLength:50},
    	{xtype:'displayfield',name: 'QTIPS',fieldLabel:'<font color="red">注</font>',anchor:'90%'
    		,value:'<font color="red">“邮寄地址”、“注册地址”为原核心系统地址类型！\n</font>'}
    ]
});
/**
 * 点击新增OR修改时弹出WINDOW
 */
var maintainWindow = new Ext.Window({
    title:'新增OR修改',
    width: 500,
    height: 320,
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
//	        var validType=false;//添加校验，不允许新增注册地址和经营地址
//	        addrCustInfoStore.each(function(record){
//				if( tempJson.ADDR_TYPE =='07'|| tempJson.ADDR_TYPE =='08'){
//					validType = true;
//					return;
//				}				
//	        });
//	        if(validType){
//	        	Ext.Msg.alert("提示", "此页不允许新增注册地址和经营地址！");
//	            return false;
//	        }
            if(maintainWindow.title =='修改'){
            	var tempRowData = addrGridPanel.getSelectionModel().getSelections()[0].data;
            	//移除当前修改数据
            	addrCustInfoStore.removeAt(addrCustInfoStore.findExact('ADDR_ID', tempRowData.ADDR_ID));
            	tempRowData.ADDR_TYPE = tempJson.ADDR_TYPE;
            	tempRowData.ADDR_TYPE_ORA = maintainForm.getForm().findField('ADDR_TYPE').getRawValue();
            	tempRowData.ZIPCODE = tempJson.ZIPCODE;
            	tempRowData.ADDR = tempJson.ADDR;
            	tempRowData.ADDR_COUNTRY=tempJson.ADDR_COUNTRY;
            	tempRowData.ADMIN_ZONE_ID=tempJson.ADMIN_ZONE_ID;
            	tempRowData.ADDR_COUNTRY_ORA=maintainForm.getForm().findField('ADDR_COUNTRY').getRawValue();
            	tempRowData.ADMIN_ZONE=maintainForm.getForm().findField('ADMIN_ZONE').getValue();

            	tempRowData.ADDRESS_LAST_UPDATE_SYS = 'CRM';
            	tempRowData.ADDRESS_LAST_UPDATE_USER = JsContext._userId;
            	tempRowData.ADDRESS_LAST_UPDATE_TM = _sysCurrDate;
            	tempRowData.IS_ADD_FLAG = Number(tempJson.ADDR_ID) > 0?'0':'1';
            	var tempRecord = new Ext.data.Record(tempRowData,null);
            	addrCustInfoStore.addSorted(tempRecord);

            }else{
	            var tempRowData = {
	            	ADDR_ID : -(new Date().getTime()),
	            	ADDRESS_CUST_ID : custId,
	            	ADDR_TYPE : tempJson.ADDR_TYPE,
	            	ADDR_TYPE_ORA : maintainForm.getForm().findField('ADDR_TYPE').getRawValue(),
	            	ZIPCODE : tempJson.ZIPCODE,
	            	ADDR : tempJson.ADDR,
	            	ADDR_COUNTRY:tempJson.ADDR_COUNTRY,
	            	ADMIN_ZONE_ID:tempJson.ADMIN_ZONE_ID,
	            	ADDR_COUNTRY_ORA:maintainForm.getForm().findField('ADDR_COUNTRY').getRawValue(),
	            	ADMIN_ZONE:maintainForm.getForm().findField('ADMIN_ZONE').getValue(),
	            	ADDRESS_LAST_UPDATE_SYS : 'CRM',
	            	ADDRESS_LAST_UPDATE_USER : JsContext._userId,
	            	ADDRESS_LAST_UPDATE_TM : _sysCurrDate,//2014-10-29
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

/**
 * grid按钮
 */
var addrCustInfoTbar = new Ext.Toolbar({
    items: [{
        text: '新增',
        hidden:false,
        id:'com_xz_3-dz',
        handler: function(){
            if(maintainForm.getForm().getEl()){
                maintainForm.getForm().getEl().dom.reset();
            }
            Ext.getCmp('country').setValue('CHN');
            maintainWindow.setTitle('新增');
            maintainForm.getForm().findField('ADDR_TYPE').setReadOnly(false);
            maintainForm.getForm().findField('ADDR_TYPE').removeClass('x-readOnly');
            //add by liuming 20170831
            maintainForm.form.findField('ADMIN_ZONE').setVisible(true);
            maintainForm.form.findField('ADMIN_ZONE').setValue('');
            maintainForm.form.findField('ADMIN_ZONE_ID').setValue('');
            
        	var _store = findLookupByType('ADDRTYPE');//新增时的码值
				_store.load();
			maintainForm.form.findField('ADDR_TYPE').bindStore(_store);
            maintainWindow.show();
        }
    },{
        text: '修改',
        hidden:false,
        id:'com_xg_3-dz',
        handler: function(){
        	var viewtitle=getCustomerViewByIndex(0).title;
            var selectLength = addrGridPanel.getSelectionModel().getSelections().length;
            var selectRecord = addrGridPanel.getSelectionModel().getSelections()[0];
            if(selectLength != 1){
                Ext.Msg.alert('提示','请选择一条记录!');
                return false;
            }
            
            //该限制只能添加于正式客户中
            if(viewtitle.indexOf('既有客户')>-1){
            	 if(selectRecord.data.ADDR_TYPE!=01 && selectRecord.data.ADDR_TYPE!=04){
             		Ext.Msg.alert("提示", "只允许新增和修改 居住地址、邮寄地址");
     	            return false;
             	}
            }           
            maintainWindow.setTitle('修改');
            maintainForm.form.findField('ADDR_TYPE').bindStore(addrTypeStore);
            maintainForm.getForm().findField('ADDR_TYPE').setReadOnly(true);
            maintainForm.getForm().findField('ADDR_TYPE').addClass('x-readOnly');
            maintainForm.getForm().loadRecord(selectRecord);
            //add by liuming 20170831
            maintainForm.form.findField('ADMIN_ZONE').setVisible(true);
            maintainForm.form.findField('ADMIN_ZONE').setValue('');
            maintainForm.form.findField('ADMIN_ZONE_ID').setValue('');
            maintainWindow.show();
        }
    },{
        text: '移除',
        hidden:false,
        id:'com_yc_3-dz',
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
/**
 * 潜在客户地址信息
 */
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
//			custId:'110004061233'
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
    {dataIndex:'LINKMAN_TYPE_ORA',header:'干系人类型',width : 100,sortable : true},
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
    {dataIndex:'PER_LAST_UPDATE_SYS',header:'最后更新系统',width : 100,sortable : true},
    {dataIndex:'PER_LAST_UPDATE_USER_NAME',header:'最后更新人',width : 100,sortable : true},
    {dataIndex:'PER_LAST_UPDATE_TM',header:'最后修改日期',width : 120,sortable : true}
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
	{name :  'PER_IS_ADD_FLAG'},
	{name : 'PER_LAST_UPDATE_SYS'},
	{name : 'PER_LAST_UPDATE_USER'},
	{name : 'PER_LAST_UPDATE_USER_NAME'},
	{name : 'PER_LAST_UPDATE_TM'}
]);
// create the data store
/**
 * 客户联系人信息表格数据集
 */
var comContactPersonStore = new Ext.data.Store({
    restful:true,
    proxy: new Ext.data.HttpProxy({
        url: basepath+'/dealWithComThree!queryComContactPerson.json',
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
					mode : 'local',forceSelection : true,triggerAction : 'all',anchor : '90%',allowBlank:false,
					listeners:{
						'focus': {  
    			        fn: function(e) {  
    			            e.expand();  
    			            this.doQuery(this.allQuery, true);  
    			        },  
    			        buffer:200  
    			 }}},
		        {xtype : 'combo',name : 'IDENT_TYPE',hiddenName : 'IDENT_TYPE',fieldLabel : '证件类型',store : identTypeStore,resizable : true,valueField : 'key',displayField : 'value',
					mode : 'local',forceSelection : true,triggerAction : 'all',anchor : '90%',
					listeners:{
						'focus': {  
    			        fn: function(e) {  
    			            e.expand();  
    			            this.doQuery(this.allQuery, true);  
    			        },  
    			        buffer:200  
    			 }}},
				{xtype:'datefield',name:'IDENT_EXPIRED_DATE',fieldLabel: '证件失效日期',format:'Y-m-d',anchor:'90%',beforeBlur:function(){ return false;},
					 listeners:{
		    			 'blur': function(datefield,record){
		    				 if(!this.validate()){
		    					 this.markInvalid(this.getRawValue()+' 是无效的日期 - 必须符合格式:yyyy-mm-dd'); 
		    				 }else{
		    					 if(!CheckDate('证件失效日期',this.getRawValue())){
		    						 this.markInvalid(this.getRawValue()+' 是无效的日期 - 必须符合格式:yyyy-mm-dd'); 
		    					 }
		    				  }
		         		    }
		    		 }
				},
				{xtype : 'combo',name : 'GENDER',hiddenName : 'GENDER',fieldLabel : '性别',store : genderTypeStore,resizable : true,valueField : 'key',displayField : 'value',
					mode : 'local',forceSelection : true,triggerAction : 'all',anchor : '90%',
					listeners:{
						'focus': {  
    			        fn: function(e) {  
    			            e.expand();  
    			            this.doQuery(this.allQuery, true);  
    			        },  
    			        buffer:200  
    			 }}},
		        {xtype : 'combo',name : 'LINKMAN_TITLE',hiddenName : 'LINKMAN_TITLE',fieldLabel : '联系人称谓',store : linkTitleStore,resizable : true,valueField : 'key',displayField : 'value',
					mode : 'local',forceSelection : true,triggerAction : 'all',anchor : '90%',
					listeners:{
						'focus': {  
    			        fn: function(e) {  
    			            e.expand();  
    			            this.doQuery(this.allQuery, true);  
    			        },  
    			        buffer:200  
    			 }}},
		        {xtype:'textfield',name:'OFFICE_TEL',fieldLabel: '办公电话',anchor:'90%',maxLength:20},
		        {xtype:'textfield',name:'FEX',fieldLabel: '传真号码',anchor:'90%',maxLength:20}
    		]
    	},{
        	columnWidth : .5,
			layout : 'form',
			items :[
				{xtype:'textfield',name:'LINKMAN_NAME',fieldLabel: '<font color="red">*</font>姓名',anchor:'90%',allowBlank:false,maxLength:100},
				{xtype:'textfield',name:'IDENT_NO',fieldLabel: '证件号码',anchor:'90%',maxLength:40},
				{xtype:'datefield',name:'BIRTHDAY',fieldLabel: '出生日期',format:'Y-m-d',anchor:'90%',beforeBlur:function(){ return false;},
					 listeners:{
		    			 'blur': function(datefield,record){
		    				 if(!this.validate()){
		    					 this.markInvalid(this.getRawValue()+' 是无效的日期 - 必须符合格式:yyyy-mm-dd'); 
		    				 }else{
		    					 if(!CheckDate('出生日期',this.getRawValue())){
		    						 this.markInvalid(this.getRawValue()+' 是无效的日期 - 必须符合格式:yyyy-mm-dd'); 
		    					 }
		    				  }
		         		    }
		    		 }
				},
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
    		debugger;
    		if (!comContactPersonForm.getForm().isValid()) {
                Ext.Msg.alert("提示", "输入有误或存在漏输项，请重新输入！");
                return false;
            }
    		
    	    //add by liuming 20170831单独增加日期控件的校验
    		var ident_expired_date = comContactPersonForm.form.findField('IDENT_EXPIRED_DATE').getRawValue();
    		if(!CheckDate('证件失效日期',ident_expired_date)){
    			comContactPersonForm.form.findField('IDENT_EXPIRED_DATE').markInvalid(ident_expired_date+' 是无效的日期 - 必须符合格式:yyyy-mm-dd'); 
    			 Ext.Msg.alert('提示信息', "校验失败：证件失效日期【"+ident_expired_date+'】是无效的日期 - 必须符合格式:yyyy-mm-dd');
    			 return false;
    		}
    		
    		var birthday = comContactPersonForm.form.findField('BIRTHDAY').getRawValue();
    		if(!CheckDate('出生日期',birthday)){
    			comContactPersonForm.form.findField('BIRTHDAY').markInvalid(birthday+' 是无效的日期 - 必须符合格式:yyyy-mm-dd'); 
    			 Ext.Msg.alert('提示信息', "校验失败：出生日期【"+birthday+'】是无效的日期 - 必须符合格式:yyyy-mm-dd');
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
//	        	debugger;
	        	var tempRowData = comContactPersonGrid.getSelectionModel().getSelections()[0].data;
	        	//移除当前修改数据
	        	comContactPersonStore.removeAt(comContactPersonStore.findExact('LINKMAN_ID', tempRowData.LINKMAN_ID));
	        	tempRowData.ORG_CUST_ID = custId,
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
	            tempRowData.PER_LAST_UPDATE_SYS = 'CRM';
	        	tempRowData.PER_LAST_UPDATE_USER = JsContext._userId;
	        	tempRowData.PER_LAST_UPDATE_USER_NAME = JsContext._userId,
	        	tempRowData.PER_LAST_UPDATE_TM = _sysCurrDate;
	        	tempRowData.PER_IS_ADD_FLAG = Number(tempJson.LINKMAN_ID) > 0?'0':'1';
	        	var tempRecord = new Ext.data.Record(tempRowData,null);
	        	comContactPersonStore.addSorted(tempRecord);

	        }else{
	            var tempRowData = {
	            	LINKMAN_ID : -(new Date().getTime()),
	            	ORG_CUST_ID : custId,
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
		        	PER_LAST_UPDATE_SYS : 'CRM',
		        	PER_LAST_UPDATE_USER : JsContext._userId,
		        	PER_LAST_UPDATE_USER_NAME :JsContext._userId,
		        	PER_LAST_UPDATE_TM : _sysCurrDate,//2014-10-29
		        	PER_IS_ADD_FLAG : '1'
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
        hidden:false,
        id:'com_xz_3-lxr',
        handler: function(){
            if(comContactPersonForm.getForm().getEl()){
                comContactPersonForm.getForm().getEl().dom.reset();
            }
            comContactPersonWindow.setTitle('新增');
            comContactPersonForm.getForm().findField('LINKMAN_TYPE').setReadOnly(false);
            comContactPersonForm.getForm().findField('LINKMAN_TYPE').removeClass('x-readOnly');
            var _store = findLookupByType('LINKTYPE');//新增时的码值
			_store.load();
			comContactPersonForm.form.findField('LINKMAN_TYPE').bindStore(_store);
			
            comContactPersonWindow.show();
        }
    },{
        text: '修改',
        hidden:false,
        id:'com_xg_3-lxr',
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
            comContactPersonForm.form.findField('LINKMAN_TYPE').bindStore(linkTypeStore1);
            comContactPersonForm.getForm().loadRecord(selectRecord);
            comContactPersonWindow.show();
        }
    },{
        text: '移除',
        hidden:false,
        id:'com_yc_3-lxr',
        handler: function(){
            var selectLength = comContactPersonGrid.getSelectionModel().getSelections().length;
            var selectRecord = comContactPersonGrid.getSelectionModel().getSelections()[0];
            if(selectLength != 1 || selectRecord.data.PER_IS_ADD_FLAG != '1'){
                Ext.Msg.alert('提示','请选择一条新增暂存的记录!');
                return false;
            }
            comContactPersonStore.remove(selectRecord);
        }
    }]
});

// create the comAddrGridPanel
/**
 * 客户联系人信息
 */
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
			if(record.data.PER_IS_ADD_FLAG=='0'){//修改过
			  	return 'my_row_set_blue';
		  	}else if(record.data.PER_IS_ADD_FLAG == '1'){//新增
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
    {dataIndex:'CONTMETH_INFO',header : '联系方式内容',width : 100,sortable : true}, 
    //{dataIndex:'IS_PRIORI_ORA',header:'是否首选',width : 100,sortable : true},
    {dataIndex:'REMARK',header:'备注',width : 100,sortable : true},
    {dataIndex:'CONTMETH_LAST_UPDATE_SYS',header:'最后更新系统',width : 100,sortable : true}
]);
// create the data record
var comContactInfoRecord = new Ext.data.Record.create([
    {name:'CONTMETH_ID'},
	{name:'CONTMETH_CUST_ID'},
	{name:'CONTMETH_TYPE'},
	{name:'CONTMETH_TYPE_ORA'},
	{name:'IS_PRIORI'},
	{name:'IS_PRIORI_ORA'},
	{name:'CONTMETH_INFO'},
	{name:'CONTMETH_SEQ'},
	{name:'REMARK'},
	{name:'STAT'},
	{name:'CONTMETH_LAST_UPDATE_SYS'},
	{name:'CONTMETH_LAST_UPDATE_USER'},
	{name:'CONTMETH_LAST_UPDATE_TM'}
]);
// create the data store
var comContactInfoStore = new Ext.data.Store({
    restful:true,
    proxy: new Ext.data.HttpProxy({
        url: basepath + '/dealWithComThree!queryContact.json?check=1',
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
        {xtype:'textfield',name:'CONTMETH_CUST_ID',hidden:true,anchor:'95%'},
        {xtype:'combo',name:'IS_PRIORI',hiddenName:'IS_PRIORI',hidden:true,anchor:'95%',store : isPrioriStore,resizable : true,valueField : 'key',displayField : 'value',
			mode : 'local',forceSelection : true,triggerAction : 'all',anchor : '90%',	listeners:{
				'focus': {  
			        fn: function(e) {  
			            e.expand();  
			            this.doQuery(this.allQuery, true);  
			        },  
			        buffer:200  
			 }}},
        {xtype:'textfield',name:'CONTMETH_SEQ',hidden:true,anchor:'95%'},
        {xtype:'textfield',name:'STAT',hidden:true,anchor:'95%'},
        {xtype : 'combo',name : 'CONTMETH_TYPE',hiddenName : 'CONTMETH_TYPE',fieldLabel : '<font color="red">*</font>联系方式类型',store : contmethTypesStore,resizable : true,valueField : 'key',displayField : 'value',
			mode : 'local',forceSelection : true,triggerAction : 'all',anchor : '90%',allowBlank:false,
			listeners:{
				'focus': {  
		        fn: function(e) {  
		            e.expand();  
		            this.doQuery(this.allQuery, true);  
		        },  
		        buffer:200  
		 }}},
		{xtype:'textarea',name:'CONTMETH_INFO',fieldLabel: '<font color="red">*</font>联系方式内容',anchor:'90%',allowBlank:false,maxLength:100},
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
            	tempRowData.CONTMETH_LAST_UPDATE_SYS = 'CRM';
            	tempRowData.CONTMETH_LAST_UPDATE_USER = JsContext._userId;
            	tempRowData.CONTMETH_LAST_UPDATE_TM = _sysCurrDate;
            	tempRowData.IS_ADD_FLAG = Number(tempJson.CONTMETH_ID) > 0?'0':'1';
            	var tempRecord = new Ext.data.Record(tempRowData,null);
            	comContactInfoStore.addSorted(tempRecord);

            }else{
	            var tempRowData = {
	            	CONTMETH_ID : -(new Date().getTime()),
	            	CONTMETH_CUST_ID : custId,
	            	CONTMETH_TYPE : tempJson.CONTMETH_TYPE,
	            	CONTMETH_TYPE_ORA : comContactInfoForm.getForm().findField('CONTMETH_TYPE').getRawValue(),
	            	CONTMETH_INFO : tempJson.CONTMETH_INFO,
	            	REMARK : tempJson.REMARK,
	            	IS_PRIORI : '9',
	            	IS_PRIORI_ORA : '未知',
	            	STAT : '1',
	            	CONTMETH_LAST_UPDATE_SYS : 'CRM',
	            	CONTMETH_LAST_UPDATE_USER : JsContext._userId,
	            	CONTMETH_LAST_UPDATE_TM : _sysCurrDate,//2014-10-29
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
        hidden:false,
        id:'com_xz_3-lx',
        handler: function(){
            if(comContactInfoForm.getForm().getEl()){
                comContactInfoForm.getForm().getEl().dom.reset();
            }
            comContactInfoWindow.setTitle('新增');
            comContactInfoForm.getForm().findField('CONTMETH_TYPE').setReadOnly(false);
            comContactInfoForm.getForm().findField('CONTMETH_TYPE').removeClass('x-readOnly');
            comContactInfoWindow.show();
        }
    },{
        text: '修改',
        hidden:false,
        id:'com_xg_3-lx',
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
            comContactInfoWindow.show();
        }
    },{
        text: '移除',
        hidden:false,
        id:'com_yc_3-lx',
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
//create the data record
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

// create the maintain formpanel
/*var comHolderInfoForm = new Ext.FormPanel({
    frame : true,
    autoScroll : true,
    split : true,
    items : []
});*/

// create the comContactInfo window
/*var comHolderInfoWindow = new Ext.Window({
    title:'新增OR修改',
    width: 600,
    height: 300,
    layout: 'fit',
    closable: true,
    closeAction: 'hide',
    modal: true,
    buttonAlign : 'center',
    //items : [comHolderInfoForm],
    buttons:[{
        text:'暂存',
        handler:function(){
            comHolderInfoWindow.hide();
        }
    },{
        text: '返回',
        handler: function(){
            comHolderInfoWindow.hide();
        }
    }]
});*/

//create the toolbar
/*var comHolderInfoTbar = new Ext.Toolbar({
    items: [{
        text: '新增',
        hidden:true,
        id:'com_xz_3-holder',
        handler: function(){
            comHolderInfoWindow.show();
        }
    },{
        text: '修改',
        hidden:true,
        id:'com_xg_3-holder',
        handler: function(){
            comHolderInfoWindow.show();
        }
    },{
        text: '移除',
        hidden:true,
        id:'com_yc_3-holder',
        handler: function(){
        }
    }]
});*/

// create the addrGridPanel
var comHolderGridPanel = new Ext.grid.GridPanel({
    height: 180,
    region: 'center',
    autoScroll: true,
    stripeRows: true,
    store: comHolderInfoStore,
    cm : comHolderInfoCm,
    //tbar: comHolderInfoTbar,
    /*viewConfig : {
        getRowClass : function(record,rowIndex,rowParams,store){
            //根据是否修改状态修改背景颜色  
            if(record.data.IS_ADD_FLAG=='0'){//修改过
                return 'my_row_set_blue';
            }else if(record.data.IS_ADD_FLAG == '1'){//新增
                return 'my_row_set_red';
            }
        }
    },*/
    loadMask: {
        msg: '正在加载表格数据,请稍等...'
    }
});

comHolderInfoStore.on('beforeload',function(){
    comHolderInfoStore.baseParams = {
        custId : custId
    };
});

/////////////////////////////////////////证件信息////////////////////////////////
/**
 * 证件类型
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
/**
 * 企业所在国别
 */
var comCountryStore =  new Ext.data.Store( {
	restful : true,
	autoLoad : true,
	sortInfo : {
            field:'key',
            direction:'ASC'
        },
	proxy : new Ext.data.HttpProxy( {
		url : basepath + '/lookup.json?name=XD000025'
	}),
	reader : new Ext.data.JsonReader( {
		root : 'JSON'
	},['key','value'])
});
/**
 * 是否开户证件
 */
var isOpenStore =  new Ext.data.Store( {
	restful : true,
	autoLoad : true,
	sortInfo : {
            field:'key',
            direction:'ASC'
        },
	proxy : new Ext.data.HttpProxy( {
		url : basepath + '/lookup.json?name=XD000300'
	}),
	reader : new Ext.data.JsonReader( {
		root : 'JSON'
	},['key','value'])
});

var identInfoRowNumber = new Ext.grid.RowNumberer({
    header:'NO.',
    width:35
});

/**
 *证件信息展示列
 */
var identInfoCm = new Ext.grid.ColumnModel([
    identInfoRowNumber,
    {dataIndex:'LIST_IDENT_TYPE_ORA',header:'证件类型',width : 150,sortable : true},
    {dataIndex:'LIST_IDENT_NO',header : '证件号码',width : 100,sortable : true}, 
    {dataIndex:'IDEN_REG_DATE',header:'证件颁发日期',width : 100,sortable : true},
    {dataIndex:'IDENT_VALID_PERIOD',header:'证件有效期',width : 100,sortable : true},
    {dataIndex:'LIST_EXPIRED_DATE',header:'证件失效日期',width : 100,sortable : true},
    {dataIndex:'COUNTRY_OR_REGION_ORA',header:'发证机关所在地',width : 100,sortable : true},
    {dataIndex:'IDENT_ORG',header:'证件颁发机关',width : 100,sortable : true},
    {dataIndex:'IDENT_CHECKING_DATE',header:'年检到期日',width : 100,sortable : true},
    {dataIndex:'IS_OPEN_ACC_IDENT_ORA',header:'是否开户证件',width:100,sortable:true}
]);
/**
 * 证件信息record
 */
var identInfoRecord = new Ext.data.Record.create([
		{name:'LIST_IDENT_ID'},
		{name:'IDENT_CUST_ID'},
		{name:'LIST_IDENT_TYPE'},
		{name:'LIST_IDENT_TYPE_ORA'},
		{name:'LIST_IDENT_NO'},
		{name:'IDENT_CUST_NAME'},
		{name:'IDENT_DESC'},
		{name:'COUNTRY_OR_REGION'},
		{name:'COUNTRY_OR_REGION_ORA'},
		{name:'IDENT_ORG'},
		{name:'IDENT_APPROVE_UNIT'},
		{name:'IDENT_CHECK_FLAG'},
		{name:'IDEN_REG_DATE'},
		{name:'IDENT_CHECKING_DATE'},
		{name:'IDENT_CHECKED_DATE'},
		{name:'IDENT_VALID_PERIOD'},
		{name:'IDENT_EFFECTIVE_DATE'},
		{name:'LIST_EXPIRED_DATE'},
		{name:'IDENT_VALID_FLAG'},
		{name:'IDENT_PERIOD'},
		{name:'IS_OPEN_ACC_IDENT'},
		{name:'IS_OPEN_ACC_IDENT_ORA'},
		{name:'OPEN_ACC_IDENT_MODIFIED_FLAG'},
		{name:'IDENT_MODIFIED_TIME'},
		{name:'VERIFY_DATE'},
		{name:'VERIFY_EMPLOYEE'},
		{name:'VERIFY_RESULT'},
		{name:'IDENT_LAST_UPDATE_SYS'},
		{name:'IDENT_LAST_UPDATE_USER'},
		{name:'IDENT_LAST_UPDATE_TM'},
		{name:'TX_SEQ_NO'},
		{name:'ETL_DATE'},
		{name:'IS_OPEN_ACC_IDENT_LN'}
]);
/**
 * 证件信息store
 */
var identInfoStore = new Ext.data.Store({
    restful:true,
    proxy: new Ext.data.HttpProxy({
        url: basepath + '/dealWithComThree!queryIdent.json',
        method:'GET'
    }),
    reader: new Ext.data.JsonReader({
        root:'json.data',
        totalProperty:'json.count'
    },identInfoRecord)
});

/**
 * 证件信息新增or修改form
 */
var identInfoForm = new Ext.FormPanel({
    frame : true,
    autoScroll : true,
    split : true,
    items : [
        {xtype:'textfield',name:'LIST_IDENT_ID',hidden:true,anchor:'95%',fieldLabel:'证件ID'},
        {xtype:'textfield',name:'IDENT_CUST_ID',hidden:true,anchor:'95%',fieldLabel:'IDENT_CUST_ID'},
        {xtype:'combo',name:'LIST_IDENT_TYPE',hiddenName:'LIST_IDENT_TYPE',fieldLabel : '<font color="red">*</font>证件类型',hidden:false,anchor:'90%',store : identTypeStore,resizable : true,valueField : 'key',displayField : 'value',
			mode : 'local',forceSelection : true,triggerAction : 'all',anchor : '90%',allowBlank:false,	listeners:{
				'focus': {  
			        fn: function(e) {  
			            e.expand();  
			            this.doQuery(this.allQuery, true);  
			        },  
			        buffer:200  
			 }}
        },
		{xtype:'textfield',name:'IDENT_CUST_NAME',fieldLabel : '证件人姓名',hidden:true,anchor:'90%'},
        {xtype:'textfield',name:'LIST_IDENT_NO',fieldLabel : '<font color="red">*</font>证件号码',hidden:false,anchor:'90%',allowBlank:true,anchor:'90%',maxLength:30},
        {xtype:'datefield',name:'IDEN_REG_DATE',fieldLabel : '证件颁发日期',hidden:false,anchor:'90%',format:'Y-m-d',allowBlank:true,beforeBlur:function(){ return false;},
			 listeners:{
    			 'blur': function(datefield,record){
    				 if(!this.validate()){
    					 this.markInvalid(this.getRawValue()+' 是无效的日期 - 必须符合格式:yyyy-mm-dd'); 
    				 }else{
    					 if(!CheckDate('证件颁发日期',this.getRawValue())){
    						 this.markInvalid(this.getRawValue()+' 是无效的日期 - 必须符合格式:yyyy-mm-dd'); 
    					 }
    				  }
         		    }
    		 }
        },
        {xtype:'numberfield',name:'IDENT_VALID_PERIOD',fieldLabel : '证件有效期',hidden:false,anchor:'90%',allowBlank:true,regex:/^[0-9]*$/
        },
        {xtype:'datefield',name : 'LIST_EXPIRED_DATE',format:'Y-m-d',fieldLabel : '证件失效日期',hidden:false,anchor:'90%',beforeBlur:function(){ return false;},
		 listeners:{
    			 'blur': function(datefield,record){
    				 if(!this.validate()){
    					 this.markInvalid(this.getRawValue()+' 是无效的日期 - 必须符合格式:yyyy-mm-dd'); 
    				 }else{
    					 if(!CheckDate('证件失效日期',this.getRawValue())){
    						 this.markInvalid(this.getRawValue()+' 是无效的日期 - 必须符合格式:yyyy-mm-dd'); 
    					 }
    				  }
         		    }
    		 }
        },
		{xtype:'combo',name:'COUNTRY_OR_REGION',fieldLabel : '发证机关所在地',hiddenName:'COUNTRY_OR_REGION',anchor:'90%',allowBlank:false,maxLength:100,hidden:false,anchor:'90%',store : comCountryStore,resizable : true,valueField : 'key',displayField : 'value',
			mode : 'local',forceSelection : true,triggerAction : 'all',anchor : '90%',allowBlank:true,	listeners:{
				'focus': {  
			        fn: function(e) {  
			            e.expand();  
			            this.doQuery(this.allQuery, true);  
			        },  
			        buffer:200  
			 }}},
        {xtype:'textfield',name:'IDENT_ORG',fieldLabel : '证件颁发机关',anchor:'90%',maxLength:100,hidden:false},
        {xtype:'datefield',name: 'IDENT_CHECKING_DATE',fieldLabel : '年检到期日',anchor:'90%',format:'Y-m-d',allowBlank:true,hidden:false,beforeBlur:function(){ return false;},
			 listeners:{
    			 'blur': function(datefield,record){
    				 if(!this.validate()){
    					 this.markInvalid(this.getRawValue()+' 是无效的日期 - 必须符合格式:yyyy-mm-dd'); 
    				 }else{
    					 if(!CheckDate('年检到期日',this.getRawValue())){
    						 this.markInvalid(this.getRawValue()+' 是无效的日期 - 必须符合格式:yyyy-mm-dd'); 
    					 }
    				  }
         		    }
    		 }
        } ,
        {xtype:'combo',name:'IS_OPEN_ACC_IDENT',fieldLabel:'是否开户证件',hiddenName:'IS_OPEN_ACC_IDENT',anchor:'90%',allowBlank:true,maxLength:100,hidden:true,anchor:'90%',store : isOpenStore,resizable : true,valueField : 'key',displayField : 'value',
			mode : 'local',forceSelection : true,triggerAction : 'all',anchor : '90%',allowBlank:true,	listeners:{
				'focus': {  
			        fn: function(e) {  
			            e.expand();  
			            this.doQuery(this.allQuery, true);  
			        },  
			        buffer:200  
			 }}}
    ]
});

/**
 * 证件信息新增or修改window
 */
var identInfoWindow = new Ext.Window({
    title:'新增OR修改',
    width: 600,
    height: 400,
    layout: 'fit',
    closable: true,
    closeAction: 'hide',
    modal: true,
    buttonAlign : 'center',
    items : [identInfoForm],
    buttons:[{
    	text:'暂存',
    	handler:function(){
    		debugger;
    		if (!identInfoForm.getForm().isValid()) {
                Ext.Msg.alert("提示", "输入有误或存在漏输项，请重新输入！");
                return false;
            }
    		//校验选择开户许可证时，位数为14
    		var openIdent=identInfoForm.form.findField('LIST_IDENT_TYPE').getValue();
    		var openIdentNo=identInfoForm.form.findField('LIST_IDENT_NO').getValue();
    		var openIdentNoLength=identInfoForm.form.findField('LIST_IDENT_NO').getValue().length;
    		var isopenFlag=identInfoForm.form.findField('IS_OPEN_ACC_IDENT').getValue();
    		
    		if(openIdentNo.indexOf(' ')>-1){					
    			Ext.Msg.alert('提示','证件号码不能出现空格！');
    			return false;
    		}
    		if (openIdent=='Z'&& openIdentNoLength!=14){
    			 Ext.Msg.alert("提示", "当证件类型为“开户许可证”时，位数只能为14位");
                 return false;
    		}
    		if (openIdent=='V'&& openIdentNoLength!=15&& openIdentNoLength!=20&& openIdentNoLength!=18){
   			 Ext.Msg.alert("提示", "当证件类型为“税务登记证”时，位数只能为15位或18位或20位");
                return false;
    		}
    		if (openIdent=='Y'&& openIdentNoLength!=15&& openIdentNoLength!=20&& openIdentNoLength!=18){
     			 Ext.Msg.alert("提示", "当证件类型为“地税税务登记证”时，位数只能为15位或18位或20位");
                  return false;
      		}
    		if (openIdent=='W'&& openIdentNoLength!=15&& openIdentNoLength!=20&& openIdentNoLength!=18){
    			 Ext.Msg.alert("提示", "当证件类型为“国税税务登记证”时，位数只能为15位或18位或20位");
                 return false;
     		}
    		if (openIdent=='20'&& openIdentNoLength!=10){
      			 Ext.Msg.alert("提示", "当证件类型为“境内组织机构代码”时，位数只能为10位");
                   return false;
      		}
    		if(openIdent == '20'&& openIdentNo.indexOf('-')!=8){
    			Ext.Msg.alert('提示','"当证件类型为“境内组织机构代码”时，证件号码必须满足xxxxxxxx-x形式”！');
    			return false;
    		}
    		if (openIdent=='2X'&& openIdentNoLength!=9){
     			 Ext.Msg.alert("提示", "当证件类型为“境外登记证件代码（赋码）”时，位数只能为9位");
                  return false;
     		}
    		if (openIdent=='11X'&& openIdentNoLength!=18){
    			 Ext.Msg.alert("提示", "当证件类型为“机构信用代码”时，位数只能为18位");
                 return false;
    		}
    		
    	    //add by liuming 20170831单独增加日期控件的校验
    		var iden_reg_date = identInfoForm.form.findField('IDEN_REG_DATE').getRawValue();
    		if(!CheckDate('证件颁发日期',iden_reg_date)){
    			 identInfoForm.form.findField('IDEN_REG_DATE').markInvalid(iden_reg_date+' 是无效的日期 - 必须符合格式:yyyy-mm-dd'); 
    			 Ext.Msg.alert('提示信息', "校验失败：证件颁发日期【"+iden_reg_date+'】是无效的日期 - 必须符合格式:yyyy-mm-dd');
    			 return false;
    		}
    		var list_expired_date = identInfoForm.form.findField('LIST_EXPIRED_DATE').getRawValue();
    		if(!CheckDate('证件失效日期',list_expired_date)){
    			 identInfoForm.form.findField('LIST_EXPIRED_DATE').markInvalid(list_expired_date+' 是无效的日期 - 必须符合格式:yyyy-mm-dd'); 
    			 Ext.Msg.alert('提示信息', "校验失败：证件失效日期【"+list_expired_date+'】是无效的日期 - 必须符合格式:yyyy-mm-dd');
    			 return false;
    		}
    		var ident_checking_date = identInfoForm.form.findField('IDENT_CHECKING_DATE').getRawValue();
    		if(!CheckDate('年检到期日',ident_checking_date)){
    			 identInfoForm.form.findField('IDENT_CHECKING_DATE').markInvalid(ident_checking_date+' 是无效的日期 - 必须符合格式:yyyy-mm-dd'); 
    			 Ext.Msg.alert('提示信息', "校验失败：年检到期日【"+ident_checking_date+'】是无效的日期 - 必须符合格式:yyyy-mm-dd');
    			 return false;
    		}
    		 
    		if(isopenFlag=="Y"){//如果是开户证件需要校验，校验通过后方可暂存
    			var custid='';
    			Ext.Ajax.request({
					url : basepath + '/customerManagerNew!doParameterCheck.json',
					method : 'GET',
					async : false,
					params : {
						custName : qzCombaseInfo.form.findField('CUST_NAME').getValue(),
						identNo : identInfoForm.form.findField('LIST_IDENT_NO').getValue(),
						identType : identInfoForm.form.findField('LIST_IDENT_TYPE').getValue(),
						custid:custId
					},
					success : function(response) {
						var ret = Ext.decode(response.responseText);
						custid = ret.custId;
						var nametype = ret.type;
						var nametype1 = ret.type1;
						var mgrId = ret.mgrId;
						var mgrName = ret.mgrName;
						var userName = ret.userName;
						identTypeVar= identInfoForm.form.findField('LIST_IDENT_TYPE').getRawValue();
						identNoVar= identInfoForm.form.findField('LIST_IDENT_NO').getValue();
						if (nametype == '1') {//既有客户
							if(nametype == '1' &&nametype1 == '5'){
								Ext.MessageBox.alert('提示', '客户【'+userName+'】已存在(既有客户)，归属于客户经理：' + mgrId+'【' +mgrName+ '】，如有需要，请联系此客户经理！');
								return false;
							}
							else if(nametype == '1' &&nametype1 == '6'){
								Ext.MessageBox.alert('提示', '【证件类型：'+identTypeVar+'，证件号码：'+identNoVar+'】已被客户【'+userName+'】(既有客户)使用，归属于客户经理：' + mgrId+'【' +mgrName+ '】，如有需要，请联系此客户经理！');
								return false;
							}
							
						} else if (nametype == '2' &&nametype1 == '5') {
							Ext.MessageBox.alert('提示', '客户【'+userName+'】已存在(潜在客户)，归属于客户经理：' + mgrId+'【' +mgrName+ '】，如有需要，请联系此客户经理！');
							return false;
						} else if (nametype == '2'&&nametype1 == '6' ){
							Ext.MessageBox.alert('提示', '【证件类型：'+identTypeVar+'，证件号码：'+identNoVar+'】已被客户【'+userName+'】(潜在客户)使用，归属于客户经理：' + mgrId+'【' +mgrName+ '】，如有需要，请联系此客户经理！');
							return false;
						} else {
							allowflag=true;//校验通过；
						}
					}
    			});
    		}else{
    			allowflag=true;//不曾修改开户证件，无需交验，直接通过；
    		}
    	 	setTimeout(function(){      
    	 	   var tempJson = identInfoForm.getForm().getValues(false);
               var validFlag = false;//判断类型是否已存在,由于不允许修改类型,故不作与原值比较
   	        identInfoStore.each(function(record){
   				if(record.data.LIST_IDENT_TYPE == tempJson.LIST_IDENT_TYPE 
   					&& (tempJson.LIST_IDENT_ID === undefined || tempJson.LIST_IDENT_ID === "")){
   					validFlag = true;
   					return;
   				}
   				if(record.data.LIST_IDENT_TYPE == tempJson.LIST_IDENT_TYPE && tempJson.CONTMETH_ID != undefined 
   					&& tempJson.LIST_IDENT_ID != "" && record.data.LIST_IDENT_ID != tempJson.LIST_IDENT_ID){
   					validFlag = true;
   					return;
   				}
   	        });
   	        if(validFlag){
   	        	Ext.Msg.alert("提示", "证件类型已存在，请重新输入！");
   	            return false;
   	        }
               if(identInfoWindow.title =='修改'){
               	if(allowflag){//校验通过时的修改
               		var tempRowData = identGridPanel.getSelectionModel().getSelections()[0].data;
                   	//移除当前修改数据
                   	identInfoStore.removeAt(identInfoStore.findExact('LIST_IDENT_ID', tempRowData.LIST_IDENT_ID));
                   	tempRowData.LIST_IDENT_TYPE = tempJson.LIST_IDENT_TYPE;
                   	tempRowData.LIST_IDENT_TYPE_ORA = identInfoForm.getForm().findField('LIST_IDENT_TYPE').getRawValue();
                   	tempRowData.LIST_IDENT_NO = tempJson.LIST_IDENT_NO;
                   	tempRowData.IDENT_CUST_NAME=qzCombaseInfo.form.findField('CUST_NAME').getValue();
                   	tempRowData.IDEN_REG_DATE = tempJson.IDEN_REG_DATE;
                   	tempRowData.IDENT_VALID_PERIOD = tempJson.IDENT_VALID_PERIOD;
                   	tempRowData.LIST_EXPIRED_DATE = tempJson.LIST_EXPIRED_DATE;
                   	tempRowData.COUNTRY_OR_REGION = tempJson.COUNTRY_OR_REGION;
                   	tempRowData.COUNTRY_OR_REGION_ORA = identInfoForm.getForm().findField('COUNTRY_OR_REGION').getRawValue();
                   	tempRowData.IDENT_ORG = tempJson.IDENT_ORG;
                   	tempRowData.IDENT_CHECKING_DATE = tempJson.IDENT_CHECKING_DATE;
                   	tempRowData.IS_OPEN_ACC_IDENT= tempJson.IS_OPEN_ACC_IDENT;
                   	tempRowData.IS_OPEN_ACC_IDENT_ORA = identInfoForm.getForm().findField('IS_OPEN_ACC_IDENT').getRawValue();
                   	tempRowData.IDENT_LAST_UPDATE_SYS = 'CRM';
                   	tempRowData.IDENT_LAST_UPDATE_USER = JsContext._userId;
                   	tempRowData.IDENT_LAST_UPDATE_TM = _sysCurrDate;
                   	tempRowData.IS_ADD_FLAG = Number(tempJson.LIST_IDENT_ID) > 0?'0':'1';
                   	var tempRecord = new Ext.data.Record(tempRowData,null);
                   	identInfoStore.addSorted(tempRecord);
               	}           	
               }else{//新增
   	            var tempRowData = {
   	            	LIST_IDENT_ID : -(new Date().getTime()),
   	            	IDENT_CUST_ID : custId,
   	            	LIST_IDENT_TYPE : tempJson.LIST_IDENT_TYPE,
   	            	LIST_IDENT_TYPE_ORA : identInfoForm.getForm().findField('LIST_IDENT_TYPE').getRawValue(),
   	            	LIST_IDENT_NO: tempJson.LIST_IDENT_NO,
   	            	IDENT_CUST_NAME:qzCombaseInfo.form.findField('CUST_NAME').getValue(),
   	            	IDEN_REG_DATE : tempJson.IDEN_REG_DATE,
   	            	IDENT_VALID_PERIOD: tempJson.IDENT_VALID_PERIOD,
   	            	LIST_EXPIRED_DATE: tempJson.LIST_EXPIRED_DATE,
   	            	COUNTRY_OR_REGION: tempJson.COUNTRY_OR_REGION,
   	            	COUNTRY_OR_REGION_ORA : identInfoForm.getForm().findField('COUNTRY_OR_REGION').getRawValue(),
   	            	IDENT_ORG : tempJson.IDENT_ORG,
   	            	IDENT_CHECKING_DATE: tempJson.IDENT_CHECKING_DATE,
   	            	IS_OPEN_ACC_IDENT:'N',
   	            	IS_OPEN_ACC_IDENT_ORA : identInfoForm.getForm().findField('IS_OPEN_ACC_IDENT').getRawValue(),
   	            	IDENT_LAST_UPDATE_SYS : 'CRM',
   	            	IDENT_LAST_UPDATE_USER : JsContext._userId,
   	            	IDENT_LAST_UPDATE_TM : _sysCurrDate,//2014-10-29
   	            	IS_ADD_FLAG : '1'
   	            };
   	            var tempRecord = new Ext.data.Record(tempRowData,null);
   	            identInfoStore.addSorted(tempRecord);
               }
    	 	},1000); 
            identInfoStore.sort('LIST_IDENT_TYPE','ASC');
            identInfoWindow.hide();
    	}
    },{
        text: '返回',
        handler: function(){
            identInfoWindow.hide();
        }
    }]
});

/**
 * 证件信息按钮栏
 */
var identInfoTbar = new Ext.Toolbar({
    items: [{
        text: '新增',
        hidden:false,
        id:'com_xz_3-zj',
        handler: function(){
            if(identInfoForm.getForm().getEl()){
                identInfoForm.getForm().getEl().dom.reset();
            }
            identInfoWindow.setTitle('新增');
            identInfoForm.getForm().findField('LIST_IDENT_TYPE').setReadOnly(false);
            identInfoForm.getForm().findField('LIST_IDENT_TYPE').removeClass('x-readOnly');
            //add by liuming 20170820
            var _store = findLookupByType('IDENTTYPE');
			_store.load();
			identInfoForm.form.findField('LIST_IDENT_TYPE').bindStore(_store);
            identInfoWindow.show();
        }
    },{
        text: '修改',
        hidden:false,
        id:'com_xg_3-zj',
        handler: function(){
        	var viewtitle=getCustomerViewByIndex(0).title;
            var selectLength = identGridPanel.getSelectionModel().getSelections().length;
            var selectRecord = identGridPanel.getSelectionModel().getSelections()[0];
            if(selectLength != 1){
                Ext.Msg.alert('提示','请选择一条记录!');
                return false;
            }
          
            if(viewtitle.indexOf("既有客户")>-1){
            	Ext.Msg.alert('提示','该客户为既有客户，其证件信息不允许修改!');
            	return false;}
            if(qzCombaseInfo.form.findField('BUSI_LIC_NO').getValue()!=='' && 
            		(selectRecord.data.LIST_IDENT_TYPE=='W'||selectRecord.data.LIST_IDENT_TYPE=='Y'||selectRecord.data.LIST_IDENT_TYPE=='V')){
            	Ext.Msg.alert('提示','该客户的“统一社会信用代码”有值，不允许修改“税务登记证”、“地税登记证”、“国税登记证”!');
            	return false;
            }
            identInfoWindow.setTitle('修改');
            identInfoForm.getForm().findField('LIST_IDENT_TYPE').setReadOnly(true);
            identInfoForm.getForm().findField('LIST_IDENT_TYPE').addClass('x-readOnly');
            identInfoForm.form.findField('LIST_IDENT_TYPE').bindStore(identTypeStore);
            identInfoForm.getForm().loadRecord(selectRecord);
//          identInfoForm.form.findField('LIST_IDENT_TYPE').bindStore(identTypeStore);
            identInfoWindow.show();
        }
    },{
        text: '移除',
        hidden:false,
        id:'com_yc_3-zj',
        handler: function(){
            var selectLength = identGridPanel.getSelectionModel().getSelections().length;
            var selectRecord = identGridPanel.getSelectionModel().getSelections()[0];
            if(selectLength != 1 || selectRecord.data.IS_ADD_FLAG != '1'){
                Ext.Msg.alert('提示','请选择一条新增暂存的记录!');
                return false;
            }
            identInfoStore.remove(selectRecord);
        }
    }]
});

/**
 * 证件信息grid
 */
var identGridPanel = new Ext.grid.GridPanel({
	height: 180,
    region: 'center',
    autoScroll: true,
    stripeRows: true,
    store: identInfoStore,
    cm : identInfoCm,
    tbar: identInfoTbar,
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
identInfoStore.on('beforeload',function(){
	identInfoStore.baseParams = {
		custId : custId
	};
});
