/**
 * @description 一键开户
 * @author cuihw
 * @since 2017-08-24
*/
imports([
     '/contents/commonjs/jquery-1.5.2.min.js',
     '/contents/resource/ext3/ux/Ext.ux.Notification.js',
     '/contents/pages/com.yucheng.bcrm/com.yucheng.bcrm.js',
     '/contents/pages/common/Com.yucheng.crm.common.OrgUserManage.js',
     '/contents/pages/common/Com.yucheng.crm.common.OrgCustomerUserManage.js'
    ]);
if(!Date.now){
	Date.now = function(){
		return new Date().getMilliseconds();
	}
}
var jnwAccountType = [];//境内外账户类型
var buttonUsable = false;//默认校验不通过
var crmData = {};

//页面表单数据
var fullFormData = null;
var fullDataJson = null;
var taxSubInfoListLength = 1;
var taxSubInfoList2Length = 1;
//修改报错样式
Ext.form.MessageTargets = {
    'qtip-nowave' : {
        mark: function(field, msg){
        	if( field.xtype == 'compositefield'){
        		field.el.setStyle("border","");
        	}else{
        		field.el.setStyle("border","1px solid #F00");
        	}
        	field.el.dom.qtip = msg;
            field.el.dom.qclass = 'x-form-invalid-tip';
            if(Ext.QuickTips){ 
                Ext.QuickTips.enable();
            }
        },
        clear: function(field){
        	if(field.xtype == 'radiogroup' || field.xtype == 'checkboxgroup' || field.xtype == 'compositefield'){
        		field.el.setStyle("border","");
        	}else{
        		field.el.setStyle("border","1px solid #a6b1be");
        	}
            field.el.dom.qtip = '';
        }
    }
};
Ext.form.Field.prototype.msgTarget='qtip-nowave';
//对私联系信息类型
var store_contmethTypes=new Ext.data.Store({
	restful : true,
	autoLoad : true,
	sortInfo : {
            field:'key',
            direction:'ASC'
        },
	proxy : new Ext.data.HttpProxy({
		url : basepath + '/oneKeyAccountAction!getContmethTypes.json'
	}),
	reader : new Ext.data.JsonReader( {root : 'JSON'},['key','value'])
});

//来源渠道
var store_channels=new Ext.data.Store( {
	restful : true,
	autoLoad : true,
	sortInfo : {
            field:'key',
            direction:'ASC'
        },
	proxy : new Ext.data.HttpProxy( {
		url : basepath + '/oneKeyAccountAction!getChannels.json'
	}),
	reader : new Ext.data.JsonReader( {root : 'JSON'},['key','value'])
});

//证件类型的下拉选项
var store_ident=new Ext.data.Store( {
	restful : true,
	autoLoad : true,
	sortInfo : {
            field:'key',
            direction:'ASC'
        },
	proxy : new Ext.data.HttpProxy( {
		url : basepath + '/lookup.json?name=XD000368'
	}),
	reader : new Ext.data.JsonReader({
		root : 'JSON'
	},['key','value'])
});
//国际区号
//var globalRoamingStore =  new Ext.data.Store({
//	restful : true,
//	autoLoad : true,
//	sortInfo : {
//            field:'key',
//            direction:'ASC'
//        },
//	proxy : new Ext.data.HttpProxy( {
//		url : basepath + '/lookup.json?name=IDD_CODE'
//	}),
//	reader : new Ext.data.JsonReader( {
//		root : 'JSON'
//	},['key','value'])
//});

//国际区号
var globalRoamingStore =  new Ext.data.Store({
	restful : true,
	autoLoad : true,
	proxy : new Ext.data.HttpProxy( {
		url : basepath + '/oneKeyAccountAction!getglobalRoamingStore.json'
	}),
	reader : new Ext.data.JsonReader( {
		root : 'JSON'
	},['key','value'])
});

//国籍
var conStore =  new Ext.data.Store( {
	restful : true,
	autoLoad : true,
	proxy : new Ext.data.HttpProxy( {
		url : basepath + '/oneKeyAccountAction!getNationNalityStore.json'
	}),
	reader : new Ext.data.JsonReader( {
		root : 'JSON'
	},['key','value'])
});

//风险国别代码
var riskStore = new Ext.data.Store( {
	restful : true,
	autoLoad : true,
	proxy : new Ext.data.HttpProxy( {
		url : basepath + '/oneKeyAccountAction!getRiskCountryStore.json'
	}),
	reader : new Ext.data.JsonReader( {
		root : 'JSON'
	},['key','value'])
});

//签发地点
var dqStore =  new Ext.data.Store( {
	restful : true,
	autoLoad : true,
	sortInfo : {
            field:'key',
            direction:'ASC'
        },
	proxy : new Ext.data.HttpProxy( {
		url : basepath + '/oneKeyAccountAction!getOrgRegionStore.json'
	}),
	reader : new Ext.data.JsonReader( {
		root : 'JSON'
	},['key','value'])
});
//与我行关联关系
var staffinStore =  new Ext.data.Store( {
	restful : true,
	autoLoad : true,
	sortInfo : {
            field:'key',
            direction:'ASC'
        },
	proxy : new Ext.data.HttpProxy( {
		url : basepath + '/lookup.json?name=XD000306'
	}),
	reader : new Ext.data.JsonReader( {
		root : 'JSON'
	},['key','value'])
});

//预约时间段的下拉选项
var store_reviewTime=new Ext.data.Store( {
	restful : true,
	autoLoad : true,
	sortInfo : {
            field:'key',
            direction:'ASC'
        },
	proxy : new Ext.data.HttpProxy( {
		url : basepath + '/lookup.json?name=XD000373'
	}),
	reader : new Ext.data.JsonReader( {
		root : 'JSON'
	},['key','value'])
});


//开户机构
var orgStore =  new Ext.data.Store( {
	restful : true,
	autoLoad : true,
	sortInfo : {
            field:'key',
            direction:'ASC'
        },
	proxy : new Ext.data.HttpProxy( {
		url : basepath + '/lookup.json?name=XD000367'
	}),
	reader : new Ext.data.JsonReader( {
		root : 'JSON'
	},['key','value'])
});

//卡种
var cardTypeStore =  new Ext.data.Store( {
	restful : true,
	autoLoad : true,
	sortInfo : {
            field:'key',
            direction:'ASC'
        },
	proxy : new Ext.data.HttpProxy( {
		url : basepath + '/lookup.json?name=CARD_CATLG'
	}),
	reader : new Ext.data.JsonReader( {
		root : 'JSON'
	},['key','value'])
});
var cardType1Store = new Ext.data.Store( {
		restful : true,
		sortInfo : {
	            field:'key',
	            direction:'ASC'
	        },
		proxy : new Ext.data.HttpProxy( {
			url : basepath + '/oneKeyAccountAction!getCardType.json'
		}),
		reader : new Ext.data.JsonReader( {
			root : 'JSON'
		},['key','value'])
	});
cardType1Store.load();
var cardType2Store  = new Ext.data.Store( {
		restful : true,
		sortInfo : {
	            field:'key',
	            direction:'ASC'
	        },
		proxy : new Ext.data.HttpProxy( {
			url : basepath + '/oneKeyAccountAction!getCardType2.json'
		}),
		reader : new Ext.data.JsonReader( {
			root : 'JSON'
		},['key','value'])
	});
cardType2Store.load();
//判断输入框中输入的日期格式为yyyy-mm-dd和正确的日期 
var IsDate = function (sm,mystring) { 
	var reg = /^(\d{4})-(\d{2})-(\d{2})$/; 
	var str = mystring; 
	var arr = reg.exec(str); 
	if (str=="") 
		return true; 
	if (!(reg.test(str) && parseInt(RegExp.$2)<=12 && parseInt(RegExp.$3)<=31)){ 
//		Ext.Msg.alert('提示信息', "请保证【"+sm+"】中输入的日期格式为yyyy-mm-dd或正确的日期!");
		return false; 
	} 
	return true; 
} 

var CheckDate = function (sm,mystring) { 
    var date = mystring;
    var result = date.match(/^(\d{1,4})(-|\/)(\d{1,2})\2(\d{1,2})$/);
    if (result == null){
//		Ext.Msg.alert('提示信息', "请保证【"+sm+"】中输入的日期格式为yyyy-mm-dd或正确的日期!");
		return true; 
    }else{
    	return (new Date(date).getDate()==date.substring(date.length-2));
    }
}
CheckDate = IsDate;
//证件1新增/删除一条纳税人信息
window.taxManager = (function(){
	var count = 1;
	function getId(){
		return count++;
	}
	function getCfg() {
		var index = getId()+ taxSubInfoListLength;
		var cfg = {
			xtype : 'compositefield',
			hideLabel : true,
			style:'margin-left:30px;',
			id : 'tax_' + index,
			items : [{
				xtype : 'label',
				html : convertFontCss('税收居民国（地区）'),
				width : 140
			}, {
				xtype : 'textfield',
				labelAlign : 'top',
				id : 'juMinGuo' + index,
				width : 200,
				maxLength : 100,
				allowBlank : false,
				name : 'juMinGuo' + index
			}, {
				xtype : 'label',
				html : convertFontCss('纳税人识别号（TIN）'),
				width : 140
			}, {
				xtype : 'textfield',
				labelAlign : 'top',
				width : 200,
				maxLength : 32,
				allowBlank : false,
				id : 'TIN' + index,
				name : 'TIN' + index
			}, {
				xtype : 'button',
				width : 30,
				text : "删除",
				id : 'deleteBtn'+index,
				index : index,
				listeners:{
					'click':function(){
							var exceptionPanel = Ext.getCmp("exceptionHand");
		            	   	var _id = 'tax_'+this.index;
		            	   	var juMinGuo = account_info.getForm().findField("juMinGuo"+this.index);
		            	   	var TIN = account_info.getForm().findField("TIN"+this.index);
		            	   	account_info.getForm().items.remove(juMinGuo);
		            	   	account_info.getForm().items.remove(TIN);
		            	   	exceptionPanel.remove(_id);
		       				exceptionPanel.doLayout();
					},
					'disable' : function(){
		   				if(this.el){
		   					this.el.up("div").removeClass("x-item-disabled");
		            		this.el.up("div").setStyle({
		            			color : "#555",
		            			cursor: "default",
		            			opacity: 1
		            		});
		   				}
	            	}
				}
			}]
		}
		return cfg;
	}
	return {
		add	: function(){
			var exceptionForm = Ext.getCmp("exceptionHand");
			var cfg = getCfg();
			exceptionForm.add(cfg);
			exceptionForm.doLayout();
		},
		initSeq	: function(){
			count = 1;
		},
		getCurrentSeq	: function(){
			return Ext.getCmp("exceptionHand").items.length;
		}
	}
})(); 

//证件1
function getCfg(data) {
	var cfg = {
		xtype : 'compositefield',
		hideLabel : true,
		style:'margin-left:30px;',
		id : 'tax_' + (data.index+1),
		items : [{
					xtype : 'label',
					html : convertFontCss('税收居民国（地区）'),
					width : 140
				}, {
					xtype : 'textfield',
					labelAlign : 'top',
					id : 'juMinGuo' + (data.index+1),
					allowBlank : false,
					width : 200,
					maxLength : 100,
					value : data ? data.area : '',
					name : 'juMinGuo' + (data.index+1)
				}, {
					xtype : 'label',
					html : '纳税人识别号（TIN）',
					width : 140
				}, {
					xtype : 'textfield',
					labelAlign : 'top',
					maxLength : 32,
					width : 200,
					allowBlank : false,
					id : 'TIN' + (data.index+1),
					value : data ? data.tin : '',
					name : 'TIN' + (data.index+1)
				},{
	        	   xtype	: 'button',
	        	   width	: 30,
	               text		: "删除",
	               index	: (data.index+1),
	               id : 'deleteBtn'+ (data.index+1),
	               handler	: function(){
	            	   	var exceptionPanel = Ext.getCmp("exceptionHand");
	            	   	var _id = 'tax_'+this.index;
	            	   	var juMinGuo = account_info.getForm().findField("juMinGuo"+this.index);
	            	   	var TIN = account_info.getForm().findField("TIN"+this.index);
	            	   	account_info.getForm().items.remove(juMinGuo);
	            	   	account_info.getForm().items.remove(TIN);
	            	   	exceptionPanel.remove(_id);
	       				exceptionPanel.doLayout();
	               }
	           }]
	}
	return cfg;
}
//重写CompositeField组件的部分属性
Ext.override(Ext.form.CompositeField,{
	combineErrors:false,  //去除CompositeField组件本身的校验
	onDestroy : function(){
        this.eachItem(function(item){
            item.destroy();
        });
    }
});
//拓展VTypes -- 只能输入数字
Ext.apply(Ext.form.VTypes, {
    numeric: function (val, field) {
        return /^\d+$/.test(val);
    },
    numericText: '只能输入数字！'
});
//证件2新增/删除一条纳税人信息
window.taxManager2 = (function(){
	var count = 1;
	function getId(){
		return count++;
	}
	function getCfg2() {
		var index = getId() + taxSubInfoList2Length;
		var cfg = {
			xtype : 'compositefield',
			hideLabel : true,
			style:'margin-left:30px;',
			id : 'tax2_' + index,
			items : [{
				xtype : 'label',
				html : convertFontCss('税收居民国（地区）'),
				width : 140
			}, {
				xtype : 'textfield',
				labelAlign : 'top',
				id : 'juMinGuo2_' + index,
				width : 200,
				maxLength : 100,
				allowBlank : false,
				name : 'juMinGuo2_' + index
			}, {
				xtype : 'label',
				html : convertFontCss('纳税人识别号（TIN）'),
				width : 140
			}, {
				xtype : 'textfield',
				labelAlign : 'top',
				width : 200,
				maxLength : 32,
				allowBlank : false,
				id : 'TIN2_' + index,
				name : 'TIN2_' + index
			}, {
				xtype : 'button',
				width : 30,
				text : "删除",
				id : 'deleteBtn2_'+index,
				index : index,
				listeners:{
					'click':function(){
						var exceptionPanel = Ext.getCmp("exceptionHand2");
	            	    var _id = 'tax2_' + this.index;
	            	   	var juMinGuo = account_info.getForm().findField("juMinGuo2_"+this.index);
	            	   	var TIN = account_info.getForm().findField("TIN2_"+this.index);
	            	   	account_info.getForm().items.remove(juMinGuo);
	            	   	account_info.getForm().items.remove(TIN);
	            	   	exceptionPanel.remove(_id);
	       				exceptionPanel.doLayout();
					},
					'disable' : function(){
		   				if(this.el){
		   					this.el.up("div").removeClass("x-item-disabled");
		            		this.el.up("div").setStyle({
		            			color : "#555",
		            			cursor: "default",
		            			opacity: 1
		            		});
		   				}
	            	}
				}
			}]
		}
		return cfg;
	}
	return {
		add	: function(){
			var exceptionForm = Ext.getCmp("exceptionHand2");
			var cfg = getCfg2();
			exceptionForm.add(cfg);
			exceptionForm.doLayout();
		},
		initSeq	: function(){
			count = 1;
		},
		getCurrentSeq	: function(){
			return count;
		}
	}
})(); 

//证件2
function getCfg2(data) {
	var cfg = {
		xtype : 'compositefield',
		hideLabel : true,
		style:'margin-left:30px;',
		id : 'tax2_' + (data.index+1),
		items : [{
					xtype : 'label',
					html : convertFontCss('税收居民国（地区）'),
					width : 140
				}, {
					xtype : 'textfield',
					labelAlign : 'top',
					allowBlank : false,
					id : 'juMinGuo2_' + (data.index+1),
					width : 200,
					maxLength : 100,
					value : data ? data.area : '',
					name : 'juMinGuo2_' + (data.index+1)
				}, {
					xtype : 'label',
					html : '纳税人识别号（TIN）',
					width : 140
				}, {
					xtype : 'textfield',
					labelAlign : 'top',
					allowBlank : false,
					maxLength : 32,
					width : 200,
					id : 'TIN2_' + (data.index+1),
					value : data ? data.tin : '',
					name : 'TIN2_' + (data.index+1)
				},{
	        	   xtype	: 'button',
	        	   width	: 30,
	               text		: "删除",
	               index	: (data.index+1),
	               id : 'deleteBtn2_'+(data.index+1),
	               handler	: function(){
	            	    var exceptionPanel = Ext.getCmp("exceptionHand2");
	            	    var _id = 'tax2_' + this.index;
	            	   	var juMinGuo = account_info.getForm().findField("juMinGuo2_"+this.index);
	            	   	var TIN = account_info.getForm().findField("TIN2_"+this.index);
	            	   	account_info.getForm().items.remove(juMinGuo);
	            	   	account_info.getForm().items.remove(TIN);
	            	   	exceptionPanel.remove(_id);
	       				exceptionPanel.doLayout();
	               }
	           }]
	}
	return cfg;
}

/**
  *放大字体
  */
function convertFontCss(text){
  	return '<font style="font-size:14px;">'+text+'</font>';
 }


/**
 * [getBusinessVouchersData 获取打印开户凭证需要的数据]
 * @return {[type]} [description]
 */
function getBusinessVouchersData(){
	var baseInfo = fullDataJson.objectjson1;//基本信息
	var accountInfo = fullDataJson.accountInfoJson;//卡账信息
	var serviceInfo = fullDataJson.serviceInfoJson;//服务信息
	var customerNm = baseInfo.custname;//客户姓名
	var accountIdentyTypeNm = '';//开户证件类型名称
	var accountIdentyNo = '';//开户证件号码
	var identyType1 = baseInfo.identityType3;//证件类型1
	if(identyType1 == 'X2'){//台湾通行证
		accountIdentyTypeNm = '台湾身份证';
		accountIdentyNo = baseInfo.twIdentNum3;
	}else if(identyType1 == 'X1'){//港澳通行证|旅行证
		accountIdentyTypeNm = '港澳通行证';
		accountIdentyNo = baseInfo.gaIdentNum3;
	}else{
		accountIdentyTypeNm = Ext.getCmp("identityType3").getRawValue();//证件类型
		accountIdentyNo = baseInfo.identityNo3;
	}
	var POST_ZIPCODE = baseInfo.POST_ZIPCODE;//邮政编码
	var HOME_ADDR = baseInfo.HOME_ADDR_INFO;//居住地址
	var mbPhone = baseInfo.mbPHONENUM;//手机号码
	var contactTel = '';//联系电话
	if(baseInfo.PHONENUM1){
		contactTel = baseInfo.PHONENUM1;
	}else if(!baseInfo.PHONENUM1 && baseInfo.PHONENUM2){
		contactTel = baseInfo.PHONENUM2;
	}else{
		contactTel = mbPhone;
	}
	var EMAIL = baseInfo.EMAIL;//电子邮箱地址
	var accountCardNo = crmData.accountCardNo;
	var accountCardType = Ext.getCmp("cardType").getRawValue() + '-'
							+ Ext.getCmp("cardType1_0").getRawValue() + '-'
							+ Ext.getCmp("cardType2_0").getRawValue();
	var businessType = '';
	var aggreMeth = '';
	var businessType_mobile = "";
	var aggreMeth_mobile = "";
	if(serviceInfo && serviceInfo.dianziBank && serviceInfo.dianziBank == '1'){
		//是否开通网络银行
		if(serviceInfo.netBank && serviceInfo.netBank == '1'){
			businessType = '网上银行'
			if(serviceInfo.ukey && serviceInfo.ukey == '1' && (!serviceInfo.shortMessage || serviceInfo.shortMessage != '1')){
				aggreMeth = 'U-key汇款认证';
			}else if((!serviceInfo.ukey || serviceInfo.ukey != '1') && serviceInfo.shortMessage && serviceInfo.shortMessage == '1'){
				aggreMeth = '短信认证';
			}else if(serviceInfo.ukey && serviceInfo.ukey == '1' && serviceInfo.shortMessage && serviceInfo.shortMessage == '1'){
				aggreMeth = 'U-key汇款+短信认证';
			}
		}
		if(serviceInfo.mobileBank && serviceInfo.mobileBank == '1'){
			businessType_mobile = "手机银行";
			if(serviceInfo.shortMessage2 && serviceInfo.shortMessage2 == '1'){
				aggreMeth_mobile = "短信验证";
			}
		}
	}
	var date = new Date();
	var yyyy = date.getFullYear();
	var MM = date.getMonth() + 1;
	var dd = date.getDate();
	var dealDate = yyyy + '-' + MM + '-' + dd;
	var dealDept = __unitname;
	//经办/复核
	//var dealUser = '501N8888/501N8888';
	var dealUser = __userId + '/' + crmData.sendto;
	var dealSerial = serializeId;
	var businessData = {
		//'coreNo' : '501N45860001',
		'coreNo' :crmData.coreNo,
		'customerNm' : customerNm,
		'accountIdentyTypeNm' : accountIdentyTypeNm,
		'accountIdentyNo' : accountIdentyNo,
		'POST_ZIPCODE' : POST_ZIPCODE,
		'HOME_ADDR' : HOME_ADDR,
		'mbPhone' : mbPhone,
		'contactTel' : contactTel,
		'EMAIL' : EMAIL,
		'accountCardNo' : accountCardNo,
		'accountCardType' : accountCardType,
		'businessType' : businessType,
		'aggreMeth' : aggreMeth,
		'businessType_mobile' : businessType_mobile,
		'aggreMeth_mobile' : aggreMeth_mobile,
		'dealDate' : dealDate,
		'dealDept' : dealDept,
		'dealUser' : dealUser,
		'dealSerial' : dealSerial
	}
	return businessData;
}


/**
 * 校验和获取表单数据
 * @return {} 第一页和第二页数据 
 */
var getFormData = function(){
	fullFormData = null;
	// 校验第一页必输项
	if (!account_info.getForm().isValid()) {
		Ext.MessageBox.alert('页面校验','校验失败，基本信息第一页输入有误或存在漏输入项,请检查输入项');
		return  false;
	}
	
	// 校验第二页必输项
	if (!(accountInfo.getForm().isValid()&&serviceInfo.getForm().isValid())) {
		Ext.MessageBox.alert('页面校验','校验失败，基本信息第二页输入有误或存在漏输入项,请检查输入项');
		return null;
	}
	
	buttonUsable = true;
	var objectjson1 = account_info.form.getValues();//第一屏的基本信息
	objectjson1.citizenShipText = Ext.getCmp("citizenShipId").getRawValue();//国籍
	objectjson1.birthLocaleText = Ext.getCmp("birthLocale").getRawValue();//出生地
	objectjson1.identityType3Text = Ext.getCmp("identityType3").getRawValue();//证件类型
	objectjson1.lianMingIdenType1Text = Ext.getCmp("lianMingIdenType1").getRawValue(); //联名户证件类型码值
	objectjson1.HOME_ADDRText = Ext.getCmp("HOME_ADDR").getRawValue();//居住地址
	objectjson1.MAIL_ADDRText = Ext.getCmp("MAIL_ADDR").getRawValue();//邮寄地址
	objectjson1.ecifIsOpen = visitJson.ecifIsOpen;
	
	var accountInfoJson = accountInfo.form.getValues();//账户信息
	var serviceInfoJson = serviceInfo.form.getValues();//服务信息
	
	if(objectjson1["ORTHERPHONE"] != '' && objectjson1["ORTHERPHONE"] ==  objectjson1["ORTHERPHONE1"]){
		Ext.MessageBox.alert('页面校验','校验失败，基本信息第一页“其他电话”类型不能重复');
		return null;
	}
	
	if(accountInfoJson["DCheckbox"] == undefined  && accountInfoJson["FCheckbox"] == undefined){
		Ext.MessageBox.alert('页面校验','校验失败，基本信息第二页“账户信息”必填');
		return null;
	}
	
	//7.1境内/外标志--：证件1类型为“境内居民身份证”、“境内居民护照”时，境内/外标志只能选择境内
	if(( objectjson1["identityType3"] == '0' || objectjson1["identityType3"] == 'X4') && accountInfoJson.cusCategory != 'D'){
		Ext.Msg.alert('页面校验','开户证件类型为“境内居民身份证”、“境内居民护照”时，境内/外标志只能选择境内！');
		return false;
	}
	
	//7.2境内/外标志--:证件1类型为“台湾同胞来往内地通行证--6”、“台湾居民身份证”、“港澳居民来往内地通行证--5”、“港澳居民身份证”、“境外居民护照”、“旅行证件”时，境内/外标志只能选择境外
	if(( objectjson1["identityType3"] == 'X2' || objectjson1["identityType3"] == 'X1'
		|| objectjson1["identityType3"] == '2' || objectjson1["identityType3"] == 'X3'
		|| objectjson1["identityType3"] == '5' || objectjson1["identityType3"] == '6') && accountInfoJson.cusCategory != 'F'){
		Ext.Msg.alert('页面校验','开户证件类型为“台湾居民身份证”、“港澳居民身份证”、“境外居民护照”、“旅行证件”时，境内/外标志只能选择境外！');
		return false;
	}
	//7.3判断身份证号码中出生日期与个人生日字段是否相符
	//截取身份证出生日期(年月日)
	
	/*var dateValue;
	var year="",month="",day="";
	var identityType = objectjson1["identityType3"];
	var identityNo = objectjson1["identityNo3"];
	if(identityType == '0' || identityType == '7' ){
	if (identityNo.length == 15){
		dateValue = "19" + identityNo.substring(6, 12);
		}		
	else{
		dateValue = identityNo.substring(6, 14);
		}
	year = dateValue.substring(0, 4).trim();
	month = dateValue.substring(4, 6).trim();
	day = dateValue.substring(6, 8).trim();
	//截取个人生日字段年月日
	var birthday = objectjson1["birthday"];
	var year1,month1,day1;
	year1 = birthday.substring(0, 4).trim();
	month1 = birthday.substring(5, 7).trim();
	day1 = birthday.substring(8, 10).trim();
	//对比
	if(year!=year1 || month || month1 && day || day1 ){
	Ext.Msg.alert('提示', "个人生日与身份证号码中出生日期不匹配，请检查输入项");
	return false;
	}
	}*/	
	objectjson1.customManager = Ext.getCmp("customManager").hiddenField.getValue();//归属客户经理
	
	var seq = 1;
	var taxData = [];
	Ext.getCmp("exceptionHand").items.each(function(item){
		taxData.push({
			seq	: seq++,
			name: Ext.get(item.items.keys[0]).getValue(),
			code: Ext.get(item.items.keys[1]).getValue() 
		});
   	});
   	objectjson1.taxData = Ext.encode(taxData);//主户税收信息
   	
   	var seq2 = 1;
	var taxData2 = [];
	Ext.getCmp("exceptionHand2").items.each(function(item){
		taxData2.push({
			seq	: seq2++,
			name: Ext.get(item.items.keys[0]).getValue(),
			code: Ext.get(item.items.keys[1]).getValue() 
		});
   	});
   	objectjson1.taxData2 = Ext.encode(taxData2);//从户税收信息
	
	fullDataJson = {
		'objectjson1' : objectjson1,
		'isLianMingHu' : visitJson.jointaccount,
		'accountInfoJson' : accountInfoJson,
		'serviceInfoJson' : serviceInfoJson
	};
	var params = {
				'objectjson1' : JSON.stringify(objectjson1),
				'isLianMingHu' : visitJson.jointaccount,
				'accountInfoJson' :JSON.stringify(accountInfoJson),
				'serviceInfoJson' :JSON.stringify(serviceInfoJson)
		};
	 fullFormData = params;
	 return fullFormData;
}

var wkflowInterval;
var flowCount = 1;

/**
 * ECIF开户检查流程状态
 * @param {} fhFlag 复核和直接复核的标识 0-流程复核 1--直接复核
 */
var checkEcifWkFlowStatus = function(fhFlag){
	if(!crmData.coreAccNo){//核心已经开户，就不能更新信息
		getFormData();
	}
	if(!fullFormData){
		return;
	}
	if(serializeId){
		fullFormData.serializeId = serializeId;
	}else{
		Ext.Msg.alert('提示', '获取交易流水号失败，请联系管理员');
		return;
	}
	
//	if(fhFlag == 0){//流程复核
		Ext.getCmp("reCheckBtn").disable();
		Ext.getCmp("directReviewBtn").disable();
//	}
	//检查流程状态，查询当前客户是否有流程正在进行中
	$.ajax({
		url:  basepath + '/oneKeyAccountAction!checkWorkFlowInfo.json?requestTime='+new Date(),
        type : 'GET',
        dataType : 'json',
        data : {
        	'logId' : serializeId,
        	'custId' : crmData.custId,
        	'instanceid' : crmData.instanceid
        },
        success : function(result){
        	if(result){
	        	if(result.flag){
	        		//流程还未提交或者流程已处理
	        		if(result.flag == '00' || result.flag == '04'|| result.flag == '03' ||result.flag == '02'||result.flag == '05'){//被否决，同意，未提交
	        			if(Ext.MessageBox){
	        				Ext.MessageBox.hide();
	        			}
	        			if(flowCount > 1){
		        			if(wkflowInterval){
		        				window.clearInterval(wkflowInterval);
		        				if(result.flag == '03' || result.flag == '04'){//否决||撤销
		        					flowCount = 1;
		        					Ext.getCmp("reCheckBtn").enable();
									Ext.getCmp("directReviewBtn").enable();
		        					Ext.Msg.alert("提示", result.msg);
		        					return;
		        				}
		        			}
	        			}
	        			//继续开户
	        			if(fullFormData){
	        				Ext.MessageBox.wait("正在提交，请稍等...");
							$.ajax({
						        url:  basepath + '/oneKeyAccountAction!crm2EcifAccount.json',
						        type : 'GET',
						        dataType : 'json',
						        data : fullFormData,
						        success: function (result) {
						            if(result && result.status && result.status == 'success'){//开户成功
						            	visitJson.ecifIsOpen = true;
						            	if(result.custId){//新户开户成功
						            		if(crmData){
						            			crmData.custId = result.custId;//ecif客户号
						            			//卡类型.3个联动的
							            		crmData.cardType = Ext.getCmp("cardType").getValue();
							            		crmData.cardType1_0 = Ext.getCmp("cardType1_0").getValue();
							            		crmData.cardType2_0 = Ext.getCmp("cardType2_0").getValue();
							            		crmData.dianziBank  = Ext.getCmp("dianziBank").getValue();//电子银行复选框的值
							            		//复核
							            		if(fhFlag == 0){//流程复核
							            			reCheckAccount();
							            		}else if(fhFlag == 1){//直接复核
							            			directReview();
							            		}
						            		}
						            	}else{
						            		Ext.MessageBox.alert('ECIF开户', result.msg);
						            	}
						            }else{
						            	Ext.MessageBox.alert('ECIF开户', result.msg);
						            }
						        },complete : function(request, status){
						        	Ext.getCmp("reCheckBtn").enable();
									Ext.getCmp("directReviewBtn").enable();
						        }
						    });
						}
	        		}else if(result.flag == '01'){//审批中
	        			crmData.instanceid = result.instanceid;
	        			if(flowCount <= 1){
	        				Ext.MessageBox.wait(result.msg);
	        			}
	        			flowCount++;
	        			if(wkflowInterval){
	        				window.clearInterval(wkflowInterval);
	        			}
	        			wkflowInterval = window.setInterval(function(){//创建定时器
	        				checkEcifWkFlowStatus();
						}, 5000);
	        		}
	        	}
        	}
        }
    });
}

//////////////////////////////////////第一页开始///////////////////////////////////
/**
 * 初始化页面控件信息
 */
function initLayout2() {
	//境内外
	//境内：居民身份证、临时身份证、军官证、武警证、户口簿
	//境外：港澳居民往来内地通行证、台湾居民来往大陆通行证、中国护照、外国护照、外国人永久居留证、边民出入境通行证、旅行证、临时台胞证
	//开户证件
	var identTypeJoint = visitJson.identTypeJoint;
	var jnwType =  crmData.resultMap1[identTypeJoint];
	var jnwList = crmData.jnwList;
	if(jnwType){
		Ext.getCmp("radio" + jnwType).enable();
		Ext.getCmp("radio" + jnwType).setValue(true);
		Ext.getCmp(jnwType + "Checkbox").enable();
		/*if(visitJson.personInfo != null && visitJson.personInfo.jointCustType == 'N'){//新开联名户
			jnwList = [];
		}else{
			for(var jnw in jnwList){
				var jnwValue = jnwList[jnw].value;
				var jnwKey = jnwList[jnw].code;
				if(jnwValue != '未知' && jnwKey != jnwType){
					Ext.getCmp("radio" + jnwKey).disable();
					Ext.getCmp(jnwKey + "Checkbox").disable();
				}
			}
		}*/
		for(var jnw in jnwList){
			var jnwValue = jnwList[jnw].value;
			var jnwKey = jnwList[jnw].code;
			if(jnwValue != '未知' && jnwKey != jnwType){
				Ext.getCmp("radio" + jnwKey).disable();
				Ext.getCmp(jnwKey + "Checkbox").disable();
			}
		}
	}else{//后期添加的证件类型，境内外都可以选
		for(var jnw in jnwList){
			var jnwValue = jnwList[jnw].value;
			var jnwKey = jnwList[jnw].code;
			if(jnwValue != '未知' ){
				Ext.getCmp("radio" + jnwKey).enable();
				Ext.getCmp(jnwKey + "Checkbox").enable();
			}
		}
	}
	
	/*if(identTypeJoint == '0' || identTypeJoint == '7' || identTypeJoint == '3' 
		|| identTypeJoint == 'X14' || identTypeJoint == '1'){//境内
		Ext.getCmp("radioD").enable();
		Ext.getCmp("radioD").setValue(true);
		Ext.getCmp("radioF").disable();
		Ext.getCmp("DCheckbox").enable();
		Ext.getCmp("FCheckbox").disable();
	}else if(identTypeJoint == '5' || identTypeJoint == '6' || identTypeJoint == '2' 
					|| identTypeJoint == '8' || identTypeJoint == 'X5' 
					|| identTypeJoint == 'X3' || identTypeJoint == 'X24'){//境外
		Ext.getCmp("radioD").disable();
		Ext.getCmp("radioF").enable();
		Ext.getCmp("radioF").setValue(true);
		Ext.getCmp("DCheckbox").disable();
		Ext.getCmp("FCheckbox").enable();
	}else{
		Ext.getCmp("radioD").enable();
		Ext.getCmp("radioF").enable();
		Ext.getCmp("DCheckbox").enable();
		Ext.getCmp("FCheckbox").enable();
	}*/
	
	//ecif已经开户
	if(visitJson.ecifIsOpen == true || visitJson.jointaccount == 1){
		
		//------------ecif已开户开始-------------------
	    if(visitJson.custId1 != null && visitJson.custId1 != ""){
	    	Ext.getCmp("custId1").setValue(visitJson.custId1);
	    }
		Ext.getCmp("custname").setValue(visitJson.customername);//客户姓名
		if(visitJson.customer != null){
			var customer = visitJson.customer;
			crmData.custId = customer.custId;
			
			$.ajax({
				url:  basepath + '/oneKeyAccountAction!checkWorkFlowInfo.json?requestTime='+new Date(),
		        type : 'GET',
		        dataType : 'json',
		        data : {
		        	'logId' : serializeId,
		        	'custId' : crmData.custId
		        },
		        success : function(result){
		        	if(result && result.flag){
		        		 if(result.flag == '01'){//审批中
		        		 	showMsgNotification('您暂无修改权限，该客户开户流程正在审批中，审批人是'+result.sendtoUserName+'['+result.sendtoAccountName+']！',300000);
		        		}
		        	}
		        }
		    });
			
			//Ext.getCmp("custname").setValue(customer.custName);//客户姓名
			Ext.getCmp("RELATION1").setValue(customer.staffin);//与我行关联关系
			Ext.getCmp("SOURCECHANNEL").setValue(customer.sourceChannel);// 来源渠道
			Ext.getCmp("RISK_NATION_CODE").setValue(customer.riskNationCode);//风险国别代码
		}
		
		if(visitJson.personInfo != null){
			var personInfo = visitJson.personInfo;
			Ext.getCmp("customPinYin").setValue(personInfo.pinyinName);
			Ext.getCmp("gender").setValue(personInfo.gender);
			Ext.getCmp("birthday").setValue(personInfo.birthday);//个人生日
			Ext.getCmp("citizenShipId").setValue(personInfo.citizenship);//国籍
			Ext.getCmp("birthLocale").setValue(personInfo.birthlocale);//出生地
			
			//职业资料
			Ext.getCmp("JOBINFO").setValue(personInfo.careerStat);
			if(personInfo.careerStat == '04'){// 全日制雇员
				// 全日制的单位名称s
				Ext.getCmp("JOBNAME").setVisible(true);
				Ext.getCmp("JOBNAME").setValue(personInfo.unitName);
				// 全日制的职位:
				Ext.getCmp("JOB").setVisible(true);
				Ext.getCmp("JOB").setValue(personInfo.duty);
			}else if(personInfo.careerStat == '99'){//其他
				//其他备注
				Ext.getCmp("JOBREMARK").setValue(personInfo.otherCareer);
			}
			
			// 在我行有无关联人
			var perkeyflagInfo = visitJson.perkeyflagInfo;
			if(perkeyflagInfo){
				Ext.getCmp("HASRELATED").setValue(perkeyflagInfo.isRealtedBank);
				if(perkeyflagInfo.isRealtedBank == '1'){
					if(visitJson.contactorInfo != null ){
						// 有关联人关联人姓名
						Ext.getCmp("RELATEDNAME").setVisible(true);
						Ext.getCmp("RELATEDNAME").setValue(visitJson.contactorInfo.personalName);
						// 有关联人与关联人关系
						Ext.getCmp("RELATION").setVisible(true);
						Ext.getCmp("RELATION").setValue(visitJson.contactorInfo.relation);
					}
				}
			}
			
			if(visitJson.jointaccount == 1){//有联名户
				Ext.getCmp("openAccountManualBtn").hidden = true;
				if(visitJson.customer2 !=null ){
					Ext.getCmp("custId2").setValue(visitJson.customer2.custId);
				}
				if(personInfo.jointCustType == 'Y'){
					Ext.getCmp("isLianMingHu").setValue(true);
					//Ext.getCmp('lianminghu').setVisible(true);
					//联名户
					if(visitJson.lianmingInfo != null){
						var lianmingInfo = visitJson.lianmingInfo;
						Ext.getCmp("lianMinPinYin").setValue(lianmingInfo.pinyinName);
						Ext.getCmp("sex").setValue(lianmingInfo.gender);
						Ext.getCmp("CITIZENSHIP1").setValue(lianmingInfo.citizenship);
						Ext.getCmp('lianMinPinYin').allowBlank = false;//联名户姓名拼音
						Ext.getCmp('sex').allowBlank = false;//联名户性别
						Ext.getCmp('CITIZENSHIP1').allowBlank = false;//联名户国籍
						if(lianmingInfo.identType1 != null && lianmingInfo.identType1 != ''){
							if(lianmingInfo.identType1 == 'X2'){
								Ext.getCmp("lianMingIdenType1").setValue(lianmingInfo.identType2);
								Ext.getCmp("lianMingIdenNo1").setValue(lianmingInfo.identNo2);
								Ext.getCmp("LEGAL_EXPIRED_DATE2").setValue(lianmingInfo.idtExpireDt2);
								Ext.getCmp('LEGAL_EXPIRED_DATE2').allowBlank = false;
								if(lianmingInfo.isActPerm2 == '1'){
									Ext.getCmp("LONGTERM2").setValue(true);
								}
								Ext.getCmp("lianMingTwIdentNum1").setVisible(true);
								Ext.getCmp("lianMingTwIdentNum1").setValue(lianmingInfo.identNo1);
								/*Ext.getCmp("dateCompositefield2").setVisible(true);
								Ext.getCmp("LEGAL_EXPIRED_DATE1").setVisible(true);
								Ext.getCmp("LONGTERM1").setVisible(true);*/
								Ext.getCmp("LEGAL_EXPIRED_DATE1").setValue(lianmingInfo.idtExpireDt1);
								Ext.getCmp('LEGAL_EXPIRED_DATE1').allowBlank = false;
								if(lianmingInfo.isActPerm1 == '1'){
									Ext.getCmp("LONGTERM1").setValue(true);
								}
							}else if(lianmingInfo.identType1 == 'X1'){
								Ext.getCmp("lianMingIdenType1").setValue(lianmingInfo.identType2);
								Ext.getCmp("lianMingIdenNo1").setValue(lianmingInfo.identNo2);
								Ext.getCmp("LEGAL_EXPIRED_DATE2").setValue(lianmingInfo.idtExpireDt2);
								Ext.getCmp('LEGAL_EXPIRED_DATE2').allowBlank = false;
								if(lianmingInfo.isActPerm2 == '1'){
									Ext.getCmp("LONGTERM2").setValue(true);
								}
								Ext.getCmp("lianMingGaIdentNum1").setVisible(true);
								Ext.getCmp("lianMingGaIdentNum1").setValue(lianmingInfo.identNo1);
								/*Ext.getCmp("dateCompositefield2").setVisible(true);
								Ext.getCmp("LEGAL_EXPIRED_DATE1").setVisible(true);
								Ext.getCmp("LONGTERM1").setVisible(true);*/
								Ext.getCmp("LEGAL_EXPIRED_DATE1").setValue(lianmingInfo.idtExpireDt1);
								Ext.getCmp('LEGAL_EXPIRED_DATE1').allowBlank = false;
								if(lianmingInfo.isActPerm1 == '1'){
									Ext.getCmp("LONGTERM1").setValue(true);
								}
							}else{
								Ext.getCmp("dateCompositefield2").setVisible(false);
								Ext.getCmp("LEGAL_EXPIRED_DATE1").setVisible(false);
								Ext.getCmp("LEGAL_EXPIRED_DATE1").allowBlank = true;
								Ext.getCmp("LONGTERM1").setVisible(false);
								
								Ext.getCmp("lianMingIdenType1").setValue(lianmingInfo.identType1);
								Ext.getCmp("lianMingIdenNo1").setValue(lianmingInfo.identNo1);
								Ext.getCmp("LEGAL_EXPIRED_DATE2").setValue(lianmingInfo.idtExpireDt1);
								Ext.getCmp('LEGAL_EXPIRED_DATE2').allowBlank = false;
								if(lianmingInfo.isActPerm1 == '1'){
									Ext.getCmp("LONGTERM2").setValue(true);
								}
							}
						}
						//联名户证件发证机关所在地
						Ext.getCmp("CITIZENSHIP2").setValue(lianmingInfo.countryOrRegion);
					}
				}else{
					//新开联名户
					Ext.getCmp("isLianMingHu").setValue(true);
					//Ext.getCmp('lianminghu').setVisible(true);
					//联名户
					if(visitJson.customer2 != null){
						var customerInfo2 = visitJson.customer2;
						if(visitJson.personInfo2 != null){//个人信息
							var personInfo2 = visitJson.personInfo2;
							Ext.getCmp('lianMinPinYin').allowBlank = false;//联名户姓名拼音
							Ext.getCmp('sex').allowBlank = false;//联名户性别
							Ext.getCmp('CITIZENSHIP1').allowBlank = false;//联名户国籍
							Ext.getCmp("lianMinPinYin").setValue(personInfo2.pinyinName);
							Ext.getCmp("sex").setValue(personInfo2.gender);
							Ext.getCmp("CITIZENSHIP1").setValue(personInfo2.citizenship);
							if(visitJson.identInfoList2 != null ){
								var identInfoList2 = visitJson.identInfoList2;
								if(identInfoList2.length == 1){
									Ext.getCmp("dateCompositefield2").setVisible(false);
									Ext.getCmp("LEGAL_EXPIRED_DATE1").setVisible(false);
									Ext.getCmp("LEGAL_EXPIRED_DATE1").allowBlank = true;
									Ext.getCmp("LONGTERM1").setVisible(false);
									Ext.getCmp("lianMingIdenType1").setValue(identInfoList2[0].identType);
									Ext.getCmp("lianMingIdenNo1").setValue(identInfoList2[0].identNo);
									Ext.getCmp("LEGAL_EXPIRED_DATE2").setValue(identInfoList2[0].identExpiredDate);
									Ext.getCmp('LEGAL_EXPIRED_DATE2').allowBlank = false;
									if(identInfoList2[0].identExpiredDate == '9999-12-31'){
										Ext.getCmp("LONGTERM2").setValue(true);
									}
									//联名户证件发证机关所在地
									Ext.getCmp("CITIZENSHIP2").setValue(identInfoList2[0].countryOrRegion);
								}else if(identInfoList2.length == 2){
									for(var i = 0; i < identInfoList2.length; i++){
										if(identInfoList2[i].isOpenAccIdent == 'Y'){
											if(identInfoList2[i].identType == 'X2'){
												Ext.getCmp("lianMingTwIdentNum1").setVisible(true);
												Ext.getCmp("lianMingTwIdentNum1").setValue(identInfoList2[i].identNo);
												/*Ext.getCmp("dateCompositefield2").setVisible(true);
												Ext.getCmp("LEGAL_EXPIRED_DATE1").setVisible(true);
												Ext.getCmp("LONGTERM1").setVisible(true);*/
												Ext.getCmp("LEGAL_EXPIRED_DATE1").setValue(identInfoList2[i].identExpiredDate);
												Ext.getCmp('LEGAL_EXPIRED_DATE1').allowBlank = false;
												if(identInfoList2[i].identExpiredDate == '9999-12-31'){
													Ext.getCmp("LONGTERM1").setValue(true);
												}
											}else if(identInfoList2[i].identType == 'X1'){
												Ext.getCmp("lianMingGaIdentNum1").setVisible(true);
												Ext.getCmp("lianMingGaIdentNum1").setValue(identInfoList2[i].identNo);
												/*Ext.getCmp("dateCompositefield2").setVisible(true);
												Ext.getCmp("LEGAL_EXPIRED_DATE1").setVisible(true);
												Ext.getCmp("LONGTERM1").setVisible(true);*/
												Ext.getCmp("LEGAL_EXPIRED_DATE1").setValue(identInfoList2[i].identExpiredDate);
												Ext.getCmp('LEGAL_EXPIRED_DATE1').allowBlank = false;
												if(identInfoList2[i].identExpiredDate == '9999-12-31'){
													Ext.getCmp("LONGTERM1").setValue(true);
												}
											}
											//联名户证件发证机关所在地
											Ext.getCmp("CITIZENSHIP2").setValue(identInfoList2[i].countryOrRegion);
										}else if(identInfoList2[i].isOpenAccIdent == 'N'){
											Ext.getCmp("lianMingIdenType1").setValue(identInfoList2[i].identType);
											Ext.getCmp("lianMingIdenNo1").setValue(identInfoList2[i].identNo);
											Ext.getCmp("LEGAL_EXPIRED_DATE2").setValue(identInfoList2[i].identExpiredDate);
											Ext.getCmp('LEGAL_EXPIRED_DATE2').allowBlank = false;
											if(identInfoList2[i].identExpiredDate == '9999-12-31'){
												Ext.getCmp("LONGTERM2").setValue(true);
											}
										}
									}
								}
							}
						}
					}
				}
				
				//证件2本人声明
				if(visitJson.taxMainInfo2 != null){
					var taxMainInfo2 = visitJson.taxMainInfo2;
					
					Ext.getCmp("radio2").setValue(taxMainInfo2.personStatement);
					if(taxMainInfo2.personStatement == '02' || taxMainInfo2.personStatement == '03'){
						Ext.getCmp('shengMing2').setVisible(true);
					 	
						// 是否为美国纳税人
						if(taxMainInfo2.usaflag == '1'){
							// 美国纳税人识别号--USTIN  USTIN
							Ext.getCmp("isUNtaxpayer2").setValue('1');
							Ext.getCmp("USTIN2").setVisible(true);
							Ext.getCmp("USTIN2").setValue(taxMainInfo2.ustin);
						}else if(taxMainInfo2.usaflag == '0'){
							Ext.getCmp("isUNtaxpayer2").setValue('2');
						}
						
						//是否居民国（地区）不发放纳税人识别号 
						if(taxMainInfo2.ifNoTinCountry == '1'){
							Ext.getCmp("REASON3").setValue(true);
						}
						// 是否账户持有人未能取得纳税人识别号
						if(taxMainInfo2.ifNoTinPerson == '1'){
							Ext.getCmp("REASON4").setValue(true);
						}
						// 账户持有人未能取得纳税人识别号原因
						Ext.getCmp("detailReason2").setValue(taxMainInfo2.reason);
					}
				}
				
				if(visitJson.taxSubInfoList2 != null){
					var taxSubInfoList2 = visitJson.taxSubInfoList2;
					var ct2 = Ext.getCmp("exceptionHand2");
					taxSubInfoList2Length = taxSubInfoList2.length;
					for(var i = 0; i < taxSubInfoList2.length; i++){
						var taxCountry = taxSubInfoList2[i].taxCountry;
						var tin = taxSubInfoList2[i].tin;
						var data = {
							area : taxCountry,
							tin : tin,
							index : i
						};
						var cfg2 = getCfg2(data);
						ct2.add(cfg2);
					}
					ct2.doLayout();
					account_info.doLayout();
				}
				
			}else{
				Ext.getCmp('lianminghu').setVisible(false);
				Ext.getCmp("openJointAccountAgreement").hidden = true;//联名协议书按钮
				Ext.getCmp("openAccountManualBtnLmh").hidden = true;//联名户信息打印按钮
				Ext.getCmp('shengming').el.setStyle({'border':0});
				Ext.getCmp('shengming').setTitle("");
				//从户的本人声明隐藏
				Ext.getCmp('shenming2').setVisible(false);
				Ext.getCmp('radio2').allowBlank = true;
				
			}
		}
		
		if(visitJson.identInfoList != null){
			var identInfoList = visitJson.identInfoList;
			if(identInfoList.length == 1){
				Ext.getCmp('dateCompositefield').setVisible(false);
				Ext.getCmp('youxiaoqixian').setVisible(false);
				Ext.getCmp('youxiaoqixian').allowBlank = true;
				var identType = identInfoList[0].identType;
				var identNo = identInfoList[0].identNo;
				var identExpiredDate = identInfoList[0].identExpiredDate;//有效期
				//证件类型
				Ext.getCmp('identityType3').setValue(identType);
				//证件号码
				Ext.getCmp('identityNo3').setValue(identNo.replace(/\*/g,""));
				//有效期限
				Ext.getCmp('LEGAL_EXPIRED_DATE').setValue(identExpiredDate);
				Ext.getCmp('LEGAL_EXPIRED_DATE').allowBlank = false;
				//是否长期有效
				if(identExpiredDate == '9999-12-31'){
					Ext.getCmp('longterm').setValue(true);
				}
				
				//发证机关所在地
				Ext.getCmp('qianfajiguan').setValue(identInfoList[0].countryOrRegion);
			}else if(identInfoList.length == 2){
				for(var i = 0; i < identInfoList.length; i++){
					if(identInfoList[i].isOpenAccIdent == 'Y'){
						//发证机关所在地
						Ext.getCmp('qianfajiguan').setValue(identInfoList[0].countryOrRegion);
						if(identInfoList[i].identType == 'X2' ){//台湾身份证
							Ext.getCmp('twIdentNum3').setVisible(true);
							Ext.getCmp('twIdentNum3').setValue(identInfoList[i].identNo.replace(/\*/g,""));
							Ext.getCmp('youxiaoqixian').setValue(identInfoList[i].identExpiredDate);//有效日期
							Ext.getCmp('youxiaoqixian').allowBlank = false;
							Ext.getCmp('longterm2').setVisible(true);
							//是否长期有效
							if(identInfoList[i].identExpiredDate == '9999-12-31'){
								Ext.getCmp('longterm2').setValue(true);
							}
						}else if(identInfoList[i].identType == 'X1' ){//港澳身份证
							Ext.getCmp('gaIdentNum3').setVisible(true);
							Ext.getCmp('gaIdentNum3').setValue(identInfoList[i].identNo.replace(/\*/g,""));
							Ext.getCmp('youxiaoqixian').setValue(identInfoList[i].identExpiredDate);//有效日期
							Ext.getCmp('youxiaoqixian').allowBlank = false;
							Ext.getCmp('longterm2').setVisible(true);
							//是否长期有效
							if(identInfoList[i].identExpiredDate == '9999-12-31'){
								Ext.getCmp('longterm2').setValue(true);
							}
						}
					}else if(identInfoList[i].isOpenAccIdent == 'N'){
						if(identInfoList[i].identType == '6'){//台胞证(台湾同胞来往内地通行证)
							Ext.getCmp('chizhengcishu').setVisible(true);
							//持证次数
							Ext.getCmp('chizhengcishu').setValue(identInfoList[i].identCount);
						}
						//证件类型
						Ext.getCmp('identityType3').setValue(identInfoList[i].identType);
						//证件号码
						Ext.getCmp('identityNo3').setValue(identInfoList[i].identNo);
						//有效期限
						Ext.getCmp('LEGAL_EXPIRED_DATE').allowBlank = false;
						Ext.getCmp('LEGAL_EXPIRED_DATE').setValue(identInfoList[i].identExpiredDate);
						//是否长期有效
						if(identInfoList[i].identExpiredDate == '9999-12-31'){
							Ext.getCmp('longterm').setValue(true);
						}
					}
				}
			}
		}
		
		//地址
		if(visitJson.addrInfoList != null){
			var addrInfoList = visitJson.addrInfoList;
			for(var i = 0; i < addrInfoList.length; i++){
				if(addrInfoList[i].addrType == '04'){//居住地址
					Ext.getCmp("HOME_ADDR").setValue(addrInfoList[i].countryOrRegion);
					Ext.getCmp("HOME_ADDR_INFO").setValue(addrInfoList[i].addr);
					Ext.getCmp("POST_ZIPCODE").setValue(addrInfoList[i].zipcode);
				}else if(addrInfoList[i].addrType == '01'){//邮寄地址
					Ext.getCmp("MAIL_ADDR").setValue(addrInfoList[i].countryOrRegion);
					Ext.getCmp("MAIL_ADDR_INFO").setValue(addrInfoList[i].addr);
					Ext.getCmp("MAIL_ZIPCODE").setValue(addrInfoList[i].zipcode);
				}
			}
		}
		
		// 租赁还是自有
		 if(visitJson.familyInfo != null){
		 	Ext.getCmp("isRent").setValue(visitJson.familyInfo.houseStat);
		 }

		 if(visitJson.contMethInfoList != null){
			var contMethInfoList = visitJson.contMethInfoList;
			for(var i = 0; i < contMethInfoList.length; i++){
				if(contMethInfoList[i].contmethType == '102' ){//移动电话
					var contmethInfo = contMethInfoList[i].contmethInfo;
					var arr = contmethInfo.split("-");
					if(arr!= null){
						if(arr[0] != null){
							Ext.getCmp("mbPhone").setValue(arr[0]);
							Ext.getCmp("QUYUMA").allowBlank = false;
							Ext.getCmp("mbPHONENUM").allowBlank = false;
							Ext.getCmp("QUYUMA").setValue(arr[0]);
						}
						if(arr[1] != null){
							Ext.getCmp("mbPHONENUM").setValue(arr[1]);
						}
					}
				}else if(contMethInfoList[i].contmethType == '209' ||
				contMethInfoList[i].contmethType == '501'){//业务手机||业务邮箱
					
				}else if(contMethInfoList[i].contmethType == '500'){// 电子邮箱E-mail
					Ext.getCmp("EMAIL").setValue(contMethInfoList[i].contmethInfo);
				}else if(contMethInfoList[i].contmethType == '106'){// 电子对账单E-mail
					Ext.getCmp("email").setValue(contMethInfoList[i].contmethInfo);
				}else if(contMethInfoList[i].contmethType == '999'){
					var contmethInfo = contMethInfoList[i].contmethInfo;
					var arr = contmethInfo.split("-");
					if(arr!= null){
						if(Ext.getCmp("ORTHERPHONE").getValue() == ""){//其他电话1
							if(contMethInfoList[i].contmethType != ""){
								Ext.getCmp("ORTHERPHONE").setValue(contMethInfoList[i].contmethType);
								Ext.getCmp("PHONE_CITIZENSHIP").allowBlank = false;
								Ext.getCmp("QUYUMA1").allowBlank = false;
								Ext.getCmp("QUYUMA2").allowBlank = false;
								Ext.getCmp("PHONENUM1").allowBlank = false;
								if(arr[0] != null){
									Ext.getCmp("PHONE_CITIZENSHIP").setValue(arr[0]);
								}
								if(arr[1] != null){
									Ext.getCmp("QUYUMA1").setValue(arr[1]);
								}
								if(arr[2] != null){
									Ext.getCmp("QUYUMA2").setValue(arr[2]);
								}
								if(arr[3] != null){
									Ext.getCmp("PHONENUM1").setValue(arr[3]);
								}
							}
						}else{//其他电话2
							if(contMethInfoList[i].contmethType != ""){
								Ext.getCmp("ORTHERPHONE1").setValue(contMethInfoList[i].contmethType);
								Ext.getCmp("PHONE_CITIZENSHIP1").allowBlank = false;
								Ext.getCmp("QUYUMA3").allowBlank = false;
								Ext.getCmp("QUYUMA4").allowBlank = false;
								Ext.getCmp("PHONENUM2").allowBlank = false;
								if(arr[0] != null){
									Ext.getCmp("PHONE_CITIZENSHIP1").setValue(arr[0]);
								}
								if(arr[1] != null){
									Ext.getCmp("QUYUMA3").setValue(arr[1]);
								}
								if(arr[2] != null){
									Ext.getCmp("QUYUMA4").setValue(arr[2]);
								}
								if(arr[3] != null){
									Ext.getCmp("PHONENUM2").setValue(arr[3]);
								}
							}
						}
					}
				}
			}
		}
		
		
		//所属客户经理
		if(visitJson.belongManagerInfo != null){
			Ext.getCmp("customManager").setValue(visitJson.belongManagerInfo.custManagerName);
			Ext.getCmp("customManager").hiddenField.setValue(visitJson.belongManagerInfo.userId);
		}
		
		//本人声明
		if(visitJson.taxMainInfo != null){
			var taxMainInfo = visitJson.taxMainInfo;
			
			Ext.getCmp("radio1").setValue(taxMainInfo.personStatement);
			if(taxMainInfo.personStatement == '02' || taxMainInfo.personStatement == '03'){
				Ext.getCmp('shengMing').setVisible(true);
			 	
				// 是否为美国纳税人
				if(taxMainInfo.usaflag == '1'){
					Ext.getCmp("isUNtaxpayer").setValue('1');
					// 美国纳税人识别号--USTIN  USTIN
					Ext.getCmp("USTIN").setVisible(true);
					Ext.getCmp("USTIN").setValue(taxMainInfo.ustin);
				}else if(taxMainInfo.usaflag == '0'){
					Ext.getCmp("isUNtaxpayer").setValue('2');
				}
				
				//是否居民国（地区）不发放纳税人识别号 
				if(taxMainInfo.ifNoTinCountry == '1'){
					Ext.getCmp("REASON").setValue(true);
				}
				// 是否账户持有人未能取得纳税人识别号
				if(taxMainInfo.ifNoTinPerson == '1'){
					Ext.getCmp("REASON2").setValue(true);
				}
				// 账户持有人未能取得纳税人识别号原因
				Ext.getCmp("detailReason").setValue(taxMainInfo.reason);
			}
		}
		
		if(visitJson.taxSubInfoList != null){
			var taxSubInfoList = visitJson.taxSubInfoList;
			var ct = Ext.getCmp("exceptionHand");
			taxSubInfoListLength = taxSubInfoList.length;
			for(var i = 0; i < taxSubInfoList.length; i++){
				var taxCountry = taxSubInfoList[i].taxCountry;
				var tin = taxSubInfoList[i].tin;
				var data = {
					area : taxCountry,
					tin : tin,
					index : i
				};
				var cfg = getCfg(data);
				ct.add(cfg);
			}
			ct.doLayout();
			account_info.doLayout();
		}
		
		//账户信息
		if(visitJson.openInfo != null){
			var openInfo = visitJson.openInfo;
			// 邮寄地址是否与居住地址相同
			if(openInfo.isEquAdd == '1'){
				Ext.getCmp("same").setValue(true);
			}
			if(openInfo.inoutFlag != null && openInfo.inoutFlag != ''){
				Ext.getCmp("radio"+openInfo.inoutFlag).setValue(true);
				Ext.getCmp(openInfo.inoutFlag + "Checkbox").enable();
				if(!jnwType){//后来增加的账户类型
					for(var jnw in jnwList){
						var jnwValue = jnwList[jnw].value;
						var jnwKey = jnwList[jnw].code;
						if(jnwValue != '未知' && jnwKey != openInfo.inoutFlag){
							Ext.getCmp("radio" + jnwKey).enable();
							Ext.getCmp(jnwKey + "Checkbox").disable();
						}
					}
				}
				
				/*if(identTypeJoint == '0' || identTypeJoint == '7' || identTypeJoint == '3' 
					|| identTypeJoint == 'X14' || identTypeJoint == '1'//境内
					|| identTypeJoint == '5' || identTypeJoint == '6' || identTypeJoint == '2' 
						|| identTypeJoint == '8' || identTypeJoint == 'X5' || identTypeJoint == 'X3'//境外
						){
					
				}else{
					if(openInfo.inoutFlag == 'F'){
						Ext.getCmp("DCheckbox").disable();
					}else if(openInfo.inoutFlag == 'D'){
						Ext.getCmp("FCheckbox").disable();
					}
				}*/
				//境内外账户类型
				if(visitJson.jointaccount == 1 && visitJson.personInfo != null && visitJson.personInfo.jointCustType == 'N'){//新开联名户
					hideCardPanel();
				}else{
					if(visitJson.accountInfoList != null){
						var accountInfoList = visitJson.accountInfoList;
						var dfCount = 0;
						for(var i = 0; i < accountInfoList.length; i++){
							if(accountInfoList[i] != null &&
									accountInfoList[i].actType != null
									  && accountInfoList[i].actType != ''){
								for(var j = 0; j <Ext.getCmp(openInfo.inoutFlag+'Checkbox').items.length;j++ ){
									if(Ext.getCmp(openInfo.inoutFlag+'Checkbox').items[j].id == openInfo.inoutFlag+accountInfoList[i].actType){
										Ext.getCmp(openInfo.inoutFlag+'Checkbox').items[j].checked = true;
									}
								}
								
								if(visitJson.jointaccount == 1){
									hideCardPanel();
								}else{
									if(openInfo.inoutFlag+accountInfoList[i].actType == 'DK' ||
											openInfo.inoutFlag+accountInfoList[i].actType == 'FH'){
				            			showCardPanel();
				            			dfCount = 1;
				            			// 借记卡申请
										if(openInfo.isOpenCard == '1'){
											Ext.getCmp('jiejikaFieldset').border = '1px solid #b5b8c8';
											Ext.getCmp("jiejika").setValue(true);
											Ext.getCmp("cardType").setVisible(true);
								  			Ext.getCmp("cardType").setValue(openInfo.cardCatlg);
								  			Ext.getCmp("cardType").allowBlank = false;
								  			cardType1Store.load({
									   			params	: {
									   				name	: openInfo.cardCatlg
									   			}
									   		});
								  			Ext.getCmp("cardType1_0").setVisible(true);
								  			Ext.getCmp("cardType1_0").setValue(openInfo.cardType);
								  			Ext.getCmp("cardType1_0").allowBlank = false;
								  			cardType2Store.load({
									   			params	: {
									   				name	: openInfo.cardType
									   			}
									   		});
								  			Ext.getCmp("cardType2_0").setVisible(true);
								  			Ext.getCmp("cardType2_0").setValue(openInfo.cardFc);
								  			Ext.getCmp("cardType2_0").allowBlank = false;
								  			// ATM转账限额设置
								  			Ext.getCmp('ATM').setVisible(true);
								  			// 每日累计限额
											Ext.getCmp('ATMDayLimitPanel').setVisible(true);
											if(openInfo.isDftlmtdAtm == '1'){
												Ext.getCmp('ATMDayLimitDefault').setValue(true);
												Ext.getCmp("ATMDayLimit").setDisabled(true);
											}else if(openInfo.isDftlmtdAtm == '0'){
												Ext.getCmp('ATMDayLimitDefine').setValue(true);
												Ext.getCmp("ATMDayLimit").setValue(openInfo.lmtamtDAtm);
											}
											// 每日累计笔数
											Ext.getCmp('ATMDayCountPanel').setVisible(true);
											if(openInfo.isDftcntAtm == '1'){
												Ext.getCmp('ATMDayCountDefault').setValue(true);
												Ext.getCmp('ATMDayLimitCount').setDisabled(true);
											}else if(openInfo.isDftcntAtm == '0'){
												Ext.getCmp('ATMDayCountDefine').setValue(true);
												Ext.getCmp("ATMDayLimitCount").setValue(openInfo.lmtcntDAtm);
											}
											// 每年累计限额
											Ext.getCmp('ATMYearLimitPanel').setVisible(true);
											if(openInfo.isDftlmtyAtm == '1'){
												Ext.getCmp('ATMYearLimitDefault').setValue(true);
												Ext.getCmp('ATMYearLimit').setDisabled(true);
											}else if(openInfo.isDftlmtyAtm == '0'){
												Ext.getCmp('ATMYearLimitDefine').setValue(true);
												Ext.getCmp("ATMYearLimit").setValue(openInfo.lmtamtYAtm);
											}
											// POS消费限额设置
											Ext.getCmp('POS').setVisible(true);
											Ext.getCmp('POSPanel').setVisible(true);
											if(openInfo.isDftlmtPos == '1'){
												Ext.getCmp('POSDefault').setValue(true);
												Ext.getCmp('eachCustemLimit').setDisabled(true);
											}else if(openInfo.isDftlmtPos == '0'){
												Ext.getCmp('POSDefine').setValue(true);
												Ext.getCmp("eachCustemLimit").setValue(openInfo.lmtamtPos);
											}
										}
										
										//电子银行服务
										if(openInfo.isOpenEbk == '1'){
											Ext.getCmp('dianziBankFieldset').border = '1px solid #b5b8c8';
											Ext.getCmp("dianziBank").setValue(true);
											Ext.getCmp("EMAIL").allowBlank = false;
											Ext.getCmp("netBank").setVisible(true);
											if(openInfo.isNetbk == '1'){
												Ext.getCmp("netBank").setValue(true);
												// ukey
												Ext.getCmp("ukeyPanel").setVisible(true);
												if(openInfo.isUkey =='1'){
													Ext.getCmp("ukey").setValue(true);
												}
												// 短信认证
												Ext.getCmp("shortMessagePanel").setVisible(true);
												if(openInfo.isMsgNetbk =='1'){
													Ext.getCmp("shortMessage").setValue(true);
												}
											}
											
										   	// 手机银行
										    Ext.getCmp("mobileBank").setVisible(true);
										    if(openInfo.isPhone =='1'){
										   		Ext.getCmp("mobileBank").setValue(true);
										   		Ext.getCmp("shortMessage2Panel").setVisible(true);
										   		// 短信验证
										   		if(openInfo.isMsgPhone =='1'){
										   			Ext.getCmp("shortMessage2").setValue(true);
										   		}
										   	}
										   	
										   	// 日累计转账限额
										   	Ext.getCmp("dayAccLimitPanel").setVisible(true);
											if(openInfo.isDftlmtdEb == '1'){
												Ext.getCmp('dayAccLimitDefault').setValue(true);
												Ext.getCmp("dayAccSelfDefine").setDisabled(true);
											}else if(openInfo.isDftlmtdEb == '0'){
												Ext.getCmp('dayAccLimitDefine').setValue(true);
												Ext.getCmp("dayAccSelfDefine").setValue(openInfo.lmtamtDEb);
											}
										    // 日累计转账笔数
								 		    Ext.getCmp("dayAccCountPanel").setVisible(true);
								 		    if(openInfo.isDftcntEb == '1'){
												Ext.getCmp('dayAccCountDefault').setValue(true);
												Ext.getCmp("dayCountSelfDefine").setDisabled(true);
											}else if(openInfo.isDftcntEb == '0'){
												Ext.getCmp('dayAccCountDefine').setValue(true);
												Ext.getCmp("dayCountSelfDefine").setValue(openInfo.lmtcntDEb);
											}
											// 年累计转账限额
								            Ext.getCmp("yearAccLimitPanel").setVisible(true);
								            if(openInfo.isDftlmtyEb == '1'){
												Ext.getCmp('yearAccLimitDefault').setValue(true);
												Ext.getCmp("yearAccSelfDefine").setDisabled(true);
											}else if(openInfo.isDftlmtyEb == '0'){
												Ext.getCmp('yearAccLimitDefine').setValue(true);
												Ext.getCmp("yearAccSelfDefine").setValue(openInfo.lmtamtYEb);
											}
										}else{
											//Ext.getCmp('dianziBankFieldset').setVisible(false);
											//Ext.getCmp("dianziBank").setVisible(false);
											Ext.getCmp("netBank").setVisible(false);
											Ext.getCmp("ukeyPanel").setVisible(false);
											Ext.getCmp("shortMessagePanel").setVisible(false);
											Ext.getCmp("mobileBank").setVisible(false);
											Ext.getCmp("shortMessage2Panel").setVisible(false);
											Ext.getCmp("dayAccLimitPanel").setVisible(false);
											Ext.getCmp("dayAccCountPanel").setVisible(false);
											Ext.getCmp("yearAccLimitPanel").setVisible(false);
										}
										
										//电子对账单
										if(openInfo.isChk == '1'){
											Ext.getCmp("elecState").setValue(true);
											// 对账单是否同email
											if(openInfo.isEquEmail == '1'){
												Ext.getCmp("isEquEmail").setValue(true);
											}
											if(visitJson.contMethInfoList != null){
												for(var i = 0; i < visitJson.contMethInfoList.length; i++){
													if(visitJson.contMethInfoList[i].contmethType == '106'){//电子对账单E-mail
														Ext.getCmp("email").setValue(contMethInfoList[i].contmethInfo);
													}
												}
											}
										}
									}
								}
							}
						}
						
						if(dfCount == 0){
							hideCardPanel();
						}
					}
				}
			}
			// 财务变动通知
			if(openInfo.isChgNotice == '1'){
				Ext.getCmp("chgNotice").setValue(true);
			}
			accountInfo.doLayout();
		}
		//------------ecif已开户结束-------------------
		
		/* if(visitJson.isReviewCus == true){//有预约
			//国家代码
			 Ext.getCmp("mbPhone").setValue(visitJson.mobilecouncode);
			 
			 //地区代码
			 Ext.getCmp("QUYUMA").setValue(visitJson.mobilecouncode);
			 
			 //手机号
			 Ext.getCmp("mbPHONENUM").setValue(visitJson.mobilephoneno);
			 
			//预约网点
			Ext.getCmp("YUYUEWANGDIAN").setVisible(true);
			Ext.getCmp("YUYUEWANGDIAN").setValue(visitJson.orderDept);
			//预约日期
			Ext.getCmp("YUYUERIQI").setVisible(true);
			Ext.getCmp("YUYUERIQI").setValue(visitJson.orderDate);
			//预约时间段
			Ext.getCmp("YUYUESHIJIANDUAN").setVisible(true);
			Ext.getCmp("YUYUESHIJIANDUAN").setValue(visitJson.orderTime);
		}*/
		
	}else{
		//------------ecif未开户开始------------------
			hideCardPanel();
			//临柜开户进入
			Ext.getCmp("custname").setValue(visitJson.customername);//客户姓名
			
			if(visitJson.customPinYin){//高拍仪扫描的性别返显
				Ext.getCmp("customPinYin").setValue(visitJson.customPinYin);
			}
			
			if(visitJson.sex){//高拍仪扫描的性别返显
				Ext.getCmp("gender").setValue(visitJson.sex);
			}
			if(visitJson.qianfajiguan){//高拍仪扫描的签发地点返显
				Ext.getCmp("qianfajiguan").setValue(visitJson.qianfajiguan);
			}
			if(visitJson.LEGAL_EXPIRED_DATE){//高拍仪扫描的证件有效期限返显
				Ext.getCmp("LEGAL_EXPIRED_DATE").setValue(visitJson.LEGAL_EXPIRED_DATE);
			}
			if(visitJson.address){//高拍仪扫描的 地址
				Ext.getCmp("HOME_ADDR_INFO").setValue(visitJson.address);
			}
			//alert(Ext.encode(visitJson));
			//alert("sex:"+visitJson.sex+";qianfajiguan:"+visitJson.qianfajiguan+";LEGAL_EXPIRED_DATE:"+visitJson.LEGAL_EXPIRED_DATE);
			
			if(visitJson.jointaccount == 1){//有联名户
				Ext.getCmp("openAccountManualBtn").hidden = true;
				//客户证件号码1
				Ext.getCmp("isLianMingHu").setValue(true);
				//Ext.getCmp('lianminghu').setVisible(true);
				Ext.getCmp('lianMinPinYin').allowBlank = false;//联名户姓名拼音
				Ext.getCmp('sex').allowBlank = false;//联名户性别
				Ext.getCmp('CITIZENSHIP1').allowBlank = false;//联名户国籍
				Ext.getCmp('LEGAL_EXPIRED_DATE2').allowBlank = false;//有效日期
				
				Ext.getCmp('twIdentNum3').setVisible(false);
				Ext.getCmp('gaIdentNum3').setVisible(false);
				
				Ext.getCmp("lianMingTwIdentNum1").setVisible(false);
				Ext.getCmp("lianMingGaIdentNum1").setVisible(false);
				

				if(visitJson.certtype == 'X2'){//台湾身份证
					if(visitJson.fzCertType == '6'){//台胞证(台湾同胞来往内地通行证)
						Ext.getCmp('chizhengcishu').setVisible(true);
					}
					Ext.getCmp('identityType3').setValue(visitJson.fzCertType);//客户证件类型
			   	    Ext.getCmp('identityNo3').setValue(visitJson.fzCertNo);
					Ext.getCmp('twIdentNum3').setVisible(true);
//					Ext.getCmp('twIdentNum3').setValue(visitJson.certid.replace(/\*/g,""));
					Ext.getCmp('LEGAL_EXPIRED_DATE1').allowBlank = false;//有效日期
					Ext.getCmp('youxiaoqixian').allowBlank = false;//有效日期
				}else if(visitJson.certtype == 'X1'){//港澳身份证
					Ext.getCmp('identityType3').setValue(visitJson.fzCertType);//客户证件类型
			   	    Ext.getCmp('identityNo3').setValue(visitJson.fzCertNo);
					Ext.getCmp('gaIdentNum3').setVisible(true);
					Ext.getCmp('gaIdentNum3').setValue(visitJson.certid.replace(/\*/g,""));
					Ext.getCmp('LEGAL_EXPIRED_DATE1').allowBlank = false;//有效日期
					Ext.getCmp('youxiaoqixian').allowBlank = false;//有效日期
				}else if(visitJson.certtype == '0'|| visitJson.certtype == '7'){//身份证
						//截取身份证出生日期赋值给个人生日
					 	var identityNo = visitJson.certid;
						if (identityNo.length == 15){
							dateValue = "19" + identityNo.substring(6, 12);
						}else{
							dateValue = identityNo.substring(6, 14);
						}
					var	year = dateValue.substring(0, 4).trim();
	                var month = dateValue.substring(4, 6).trim();
	                var day = dateValue.substring(6, 8).trim();
	                var birthday = year+"-"+month+"-"+day;
	                Ext.getCmp('birthday').setValue(birthday);//个人生日
	                Ext.getCmp('identityType3').setValue(visitJson.certtype);//客户证件类型
				    Ext.getCmp('identityNo3').setValue(visitJson.certid.replace(/\*/g,""));
				}else{
					Ext.getCmp('identityType3').setValue(visitJson.certtype);//客户证件类型
			   	    Ext.getCmp('identityNo3').setValue(visitJson.certid.replace(/\*/g,""));
				}
				
				if(visitJson.certtype2 == 'X2'){//台湾身份证
					Ext.getCmp("lianMingIdenType1").setValue(visitJson.fzCertType2);//客户证件类型
					Ext.getCmp("lianMingIdenNo1").setValue(visitJson.fzCertNo2);
					Ext.getCmp("lianMingTwIdentNum1").setVisible(true);
					Ext.getCmp("lianMingTwIdentNum1").setValue(visitJson.certid2);
					Ext.getCmp('LEGAL_EXPIRED_DATE1').allowBlank = false;//有效日期
					/*Ext.getCmp("dateCompositefield2").setVisible(true);
					Ext.getCmp("LEGAL_EXPIRED_DATE1").setVisible(true);
					Ext.getCmp("LONGTERM1").setVisible(true);*/
				}else if(visitJson.certtype2 == 'X1'){//港澳身份证
					Ext.getCmp("lianMingIdenType1").setValue(visitJson.fzCertType2);//客户证件类型
					Ext.getCmp("lianMingIdenNo1").setValue(visitJson.fzCertNo2);
					Ext.getCmp("lianMingGaIdentNum1").setVisible(true);
					Ext.getCmp("lianMingGaIdentNum1").setValue(visitJson.certid2);
					Ext.getCmp('LEGAL_EXPIRED_DATE1').allowBlank = false;//有效日期
					/*Ext.getCmp("dateCompositefield2").setVisible(true);
					Ext.getCmp("LEGAL_EXPIRED_DATE1").setVisible(true);
					Ext.getCmp("LONGTERM1").setVisible(true);*/
				}else{
					Ext.getCmp("lianMingIdenType1").setValue(visitJson.certtype2);//客户证件类型
					Ext.getCmp("lianMingIdenNo1").setValue(visitJson.certid2);
					Ext.getCmp('LEGAL_EXPIRED_DATE1').allowBlank = true;//有效日期
					Ext.getCmp("dateCompositefield2").setVisible(false);
					Ext.getCmp("LEGAL_EXPIRED_DATE1").setVisible(false);
					Ext.getCmp("LONGTERM1").setVisible(false);
				}
				hideCardPanel();
			}else{//没有联名户
					Ext.getCmp('shengming').el.setStyle({'border':0,title:''});
					Ext.getCmp('shengming').setTitle("");
					
					//客户证件号码
					Ext.getCmp("isLianMingHu").setValue(false);
					Ext.getCmp('lianminghu').setVisible(false);
					Ext.getCmp("openJointAccountAgreement").hidden = true;
					Ext.getCmp("openAccountManualBtnLmh").hidden = true;
					Ext.getCmp('lianMinPinYin').allowBlank = true;//联名户姓名拼音
					Ext.getCmp('sex').allowBlank = true;//联名户性别
					Ext.getCmp('CITIZENSHIP1').allowBlank = true;//联名户国籍
					Ext.getCmp('LEGAL_EXPIRED_DATE1').allowBlank = true;//有效日期
			
					Ext.getCmp('twIdentNum3').setVisible(false);
					Ext.getCmp('gaIdentNum3').setVisible(false);
					Ext.getCmp('birthday').setValue(visitJson.birthday);//个人生日
					Ext.getCmp('LEGAL_EXPIRED_DATE').setValue(visitJson.expiredDate);//有效日期
					
					if(visitJson.certtype == 'X2'){//台湾身份证
						Ext.getCmp('identityType3').setValue(visitJson.fzCertType);//客户证件类型
						if(visitJson.fzCertType == '6'){//台胞证（台湾来往内地通行证）
							Ext.getCmp('chizhengcishu').setVisible(true);
						}
						Ext.getCmp('identityNo3').setValue(visitJson.fzCertNo);
						Ext.getCmp('twIdentNum3').setVisible(true);
						Ext.getCmp('twIdentNum3').setValue(visitJson.certid);
						Ext.getCmp('youxiaoqixian').allowBlank = false;
//						Ext.getCmp('youxiaoqixian').setValue(visitJson.twExpiredDate);//台湾国民身份证有效期限
//						Ext.getCmp('youxiaoqixian').setValue(visitJson.expiredDate);//台湾国民身份证有效期限
						Ext.getCmp('longterm2').setValue(true);//台湾国民身份证有效期限未永久
					}else if(visitJson.certtype == 'X1'){//港澳身份证
						Ext.getCmp('identityType3').setValue(visitJson.fzCertType);//客户证件类型
						Ext.getCmp('identityNo3').setValue(visitJson.fzCertNo);
						Ext.getCmp('gaIdentNum3').setVisible(true);
						Ext.getCmp('gaIdentNum3').setValue(visitJson.certid);
						Ext.getCmp('youxiaoqixian').allowBlank = false;
//						Ext.getCmp('youxiaoqixian').setValue(visitJson.expiredDate);//港澳身份证有效期限
						Ext.getCmp('longterm2').setValue(true);//台湾国民身份证有效期限未永久
					}else{
						if(visitJson.certtype == '0'){//境内居民身份证
							//截取身份证出生日期赋值给个人生日
						 	var identityNo = visitJson.certid;
							if (identityNo.length == 15){
								dateValue = "19" + identityNo.substring(6, 12);
							}else{
								dateValue = identityNo.substring(6, 14);
							}
							var	year = dateValue.substring(0, 4).trim();
			                var month = dateValue.substring(4, 6).trim();
			                var day = dateValue.substring(6, 8).trim();
			                var birthday = year+"-"+month+"-"+day;
			                Ext.getCmp('birthday').setValue(birthday);//个人生日
			                
			                
						}
						Ext.getCmp('identityType3').setValue(visitJson.certtype);//客户证件类型
						Ext.getCmp('identityNo3').setValue(visitJson.certid);
						Ext.getCmp('dateCompositefield').setVisible(false);
						Ext.getCmp('youxiaoqixian').setVisible(false);
						Ext.getCmp('youxiaoqixian').allowBlank = true;
						Ext.getCmp('longterm2').setVisible(false);
					}
					
					//从户的本人声明隐藏
					Ext.getCmp('shenming2').setVisible(false);
					Ext.getCmp('radio2').allowBlank = true;
				}
		//------------ecif未开户结束-------------------
		
		if(visitJson.isReviewCus == true){//有预约
			 //国家代码
			 Ext.getCmp("mbPhone").setValue(visitJson.mobilecouncode);
			 //地区代码
			 Ext.getCmp("QUYUMA").setValue(visitJson.mobilecouncode);
			 //手机号
			 Ext.getCmp("mbPHONENUM").setValue(visitJson.mobilephoneno);
			 
			//预约网点
			Ext.getCmp("YUYUEWANGDIAN").setVisible(true);
			Ext.getCmp("YUYUEWANGDIAN").setValue(visitJson.orderDept);
			//预约日期
			Ext.getCmp("YUYUERIQI").setVisible(true);
			Ext.getCmp("YUYUERIQI").setValue(visitJson.orderDate);
			//预约时间段
			Ext.getCmp("YUYUESHIJIANDUAN").setVisible(true);
			Ext.getCmp("YUYUESHIJIANDUAN").setValue(visitJson.orderTime);
		}
			
	}
}
/**
 * 基本信息容器面板
 */
var account_info = new Ext.FormPanel({
	title	    : '基本信息',
	header	    : false,
	collapsible	: true,
	id	        : 'account_infoPanel',
	name	    : 'account_infoPanel',
	autoHeight	: true,
	autoWidth	: true,
	labelWidth	: 140,// label的宽度
	labelAlign	: 'left',
	frame	    : false,// 滚动条
	autoScroll	: true,
	buttonAlign	: 'center',
	anchor	    : '95%',
	buttons	    : [{
		text	: '下一页',
		handler	: function() {
			if (!account_info.getForm().isValid()) {
				Ext.MessageBox.alert('页面校验','校验失败，基本信息第一页输入有误或存在漏输入项,请检查输入项');
				return  false;
			}else{
				panel.setActiveTab(1);//切换到第2页
			}
			
		}
	}, {
		text	: '退出系统',
		handler	: function() {
			Ext.MessageBox.confirm('提示','确定退出系统?',function(buttonId){
				if(buttonId.toLowerCase() == "no"){
					return false;
				}else{
					//刷新功能窗口页面即可
					parent.window._APP.taskBar.currentItem.reload();
				} 
			});	
		}
	}],
	items	    : [{
		layout	: 'column',
		items	: [{
			title		: '<font style="font-size:16px;">请您填写基本信息（*为必填项）</font>',
			columnWidth	: 1,
			xtype		: 'fieldset',
			style		: 'margin:0 180px;',// background-color:blue
			items		: [{
				layout	: 'column',
				items	: [{
					columnWidth	: 0.5,
					layout		: 'form',
					items		: [{
						xtype		: 'compositefield',
						fieldLabel	: '<font color="red">*</font>' + convertFontCss("姓名"),
						anchor		: '90%',
						items		: [{
							id			: "custname",
							xtype		: 'textfield',
							anchor		: '92%',
							width		: 150,
							readOnly	: true,
							cls			: 'x-readOnly'
						}, {
							xtype		: 'checkbox',
							id			: 'isLianMingHu',
							boxLabel	: convertFontCss("联名户"),
							name		: 'isLianMingHu',
							inputValue	: '1',
							readOnly	: true,
							disabled	: true,
							listeners	: {
								'afterrender'	: function() {
									this.el.up("div").removeClass("x-item-disabled");
									this.el.up("div").setStyle({
										color	: "#555",
										cursor	: "default",
										opacity	: 1
									});
								},
								'disable'		: function() {
									if(this.el) {
										this.el.up("div").removeClass("x-item-disabled");
										this.el.up("div").setStyle({
											color	: "#555",
											cursor	: "default",
											opacity	: 1
										});
									}
								}
							}
						}]
					}]
				}, {// 主户custId1
					columnWidth	: 0.5,
					layout		: 'form',
					items		: [{
						xtype	: 'textfield',
						id		: 'custId1',
						hidden	: true
					}]
				}, {// 客户姓名拼音
					columnWidth	: 0.5,
					layout		: 'form',
					items		: [{
						xtype			: 'textfield',
						id				: 'customPinYin',
						width			: 200,
						maxLength		: 128,
						fieldLabel		: '<font color=red>\*</font>' + convertFontCss("姓名拼音"),
						emptyText		: "与开户证件一致",
						allowBlank		: false,
						enableKeyEvents	: true,
						listeners		: {
							'blur'	: function() {
								//失去焦点的时候，将拼音字母大写
								this.setValue(Ext.util.Format.uppercase(this.getValue()));
							}
						}
					}]
				}, {
					columnWidth	: 0.5,
					layout		: 'form',
					items		: [{
						xtype		: 'radiogroup',
						id			: 'gender',
						hiddenName	: 'gender',
						fieldLabel	: '<font color=red>\*</font>' + convertFontCss("性别"),
						columns		: 2,
						anchor		: '60%',
						allowBlank	: false,
						items		: [{
							boxLabel	: '男',
							name		: 'gender',
							inputValue	: '1',
							listeners	: {
								'disable'	: function() {
									if(this.el) {
										this.el.up("div").removeClass("x-item-disabled");
										this.el.up("div").setStyle({
											color	: "#555",
											cursor	: "default",
											opacity	: 1
										});
									}
								}
							}
						}, {
							boxLabel	: '女',
							name		: 'gender',
							inputValue	: '2',
							listeners	: {
								'disable'	: function() {
									if(this.el) {
										this.el.up("div").removeClass("x-item-disabled");
										this.el.up("div").setStyle({
											color	: "#555",
											cursor	: "default",
											opacity	: 1
										});
									}
								}
							}
						}]
					}]
				}, {
					columnWidth	: 0.5,
					layout		: 'form',
					items		: [{// 国籍
						xtype				: 'combo',
						hiddenName			: 'citizenShip',
						id					: 'citizenShipId',
						fieldLabel			: '<font color="red">*</font>' + convertFontCss("国籍"),
						width				: 200,
						store				: conStore,
						emptyText			: '请选择',
						allowBlank			: false,
						valueField			: 'key',
						displayField		: 'value',
						mode				: 'local',
						valueNotFoundText	: "",
						triggerAction		: 'all',
						listeners			: {
							'focus'	: {//获得焦点时自动展开
								fn		: function(e) {
									e.expand();
									this.doQuery(this.allQuery, true);
								},
								buffer	: 200
							}
						}
					}]
				}, {
					columnWidth	: 1,
					layout		: 'form',
					items		: [{
						xtype		: 'compositefield',
						fieldLabel	: '<font color="red">*</font>' + convertFontCss("个人生日"),
						anchor		: '90%',
						items		: [{// 个人生日
							id			: 'birthday',
							xtype		: 'datefield',
							width		: 200,
							name		: 'birthday',
							format		: 'Y-m-d',
							msgTarget	: 'qtip-nowave',
							maxValue	: new Date(),
							invalidText	: '您输入的日期格式有误，必须是"YYYY-mm-dd"',
							allowBlank	: false,
							beforeBlur	: function() {
								return false;
							},
							listeners	: {
								'blur'	: function(datefield, record) {
									if(!this.validate()) {
										new Ext.ToolTip({
											title		: '提示',
											target		: 'birthday',
											anchor		: 'left',
											html		: ' 是无效的日期 - 必须符合格式:yyyy-mm-dd',
											autoHide	: true,
											closable	: true,
											draggable	: true
										});
									} else {
										if(!CheckDate('个人生日', this.getRawValue())) {
											new Ext.ToolTip({
												title		: '提示',
												target		: 'birthday',
												anchor		: 'left',
												html		: ' 是无效的日期 - 必须符合格式:yyyy-mm-dd',
												autoHide	: true,
												closable	: true,
												draggable	: true
											});
										}
									}
								}
							}
						}, {// (可手工录入出生日期，格式为：年-月-日)
							xtype	: 'label',
							html	: '<font style="font-size:14px;color:blue">(可手工录入出生日期，格式为：年-月-日)</font>'
						}]
					}]
				}, {
					columnWidth	: 0.5,
					layout		: 'form',
					items		: [{
						xtype				: 'combo',
						id					: 'birthLocale',
						hiddenName			: 'birthLocale',
						fieldLabel			: '<font color=red>\*</font>' + convertFontCss("出生地"),
						emptyText			: "请选择",
						store				: conStore,
						allowBlank			: false,
						resizable			: true,
						valueField			: 'key',
						displayField		: 'value',
						mode				: 'local',
						valueNotFoundText	: "",
						triggerAction		: 'all',
						width				: 200,
						listeners			: {
							'focus'	: {
								fn		: function(e) {
									e.expand();
									this.doQuery(this.allQuery, true);
								},
								buffer	: 200
							}
						}
					}]
				}, {// 签发地点
					columnWidth	: 0.5,
					layout		: 'form',
					items		: [{
						xtype				: 'combo',
						id					: 'qianfajiguan',
						hiddenName			: 'qianfajiguan',
						fieldLabel			: convertFontCss("发证机关所在地"),
						emptyText			: "请选择",
						store				: dqStore,
						resizable			: true,
						valueField			: 'key',
						displayField		: 'value',
						mode				: 'local',
						valueNotFoundText	: "",
						triggerAction		: 'all',
						width				: 200,
						listeners			: {
							'focus'	: {
								fn		: function(e) {
									e.expand();
									this.doQuery(this.allQuery, true);
								},
								buffer	: 200
							}
						}
					}]
				}, {
					columnWidth	: 0.5,
					layout		: 'form',
					items		: [{
						xtype				: 'combo',
						id					: 'identityType3',
						hiddenName			: 'identityType3',
						fieldLabel			: '<font color="red">*</font>' + convertFontCss("证件类型"),
						emptyText			: '请选择',
						store				: store_ident,
						resizable			: true,
						allowBlank			: false,
						width				: 200,
						valueField			: 'key',
						displayField		: 'value',
						mode				: 'local',
						readOnly			: true,
						cls					: 'x-readOnly',
						valueNotFoundText	: "",
						triggerAction		: 'all'
					}]
				}, {
					columnWidth	: 0.5,
					layout		: 'form',
					items		: [{// 证件号码
						xtype		: 'textfield',
						id			: 'identityNo3',
						width		: 200,
						fieldLabel	: '<font color=red>\*</font>' + convertFontCss("证件号码"),
						readOnly	: true,
						cls			: 'x-readOnly'
					}]
				}, {
					columnWidth	: 1,
					layout		: 'form',
					items		: [{
						xtype	: 'compositefield',
						anchor	: '90%',
						items	: [{// 有效日期
							xtype		: 'datefield',
							id			: 'LEGAL_EXPIRED_DATE',
							name		: 'LEGAL_EXPIRED_DATE',
							fieldLabel	: '<font color="red">*</font>' + convertFontCss("有效日期"),
							width		: 200,
							allowBlank	: false,
							invalidText	: '您输入的日期格式有误，必须是"YYYY-mm-dd"',
							minValue	: new Date(),
							format		: 'Y-m-d',
							beforeBlur	: function() {
								return false;
							},
							listeners	: {
								'blur'	: function(datefield, record) {
									if(!this.validate()) {
										new Ext.ToolTip({
											title		: '提示',
											target		: 'LEGAL_EXPIRED_DATE',
											anchor		: 'left',
											html		: ' 是无效的日期 - 必须符合格式:yyyy-mm-dd',
											autoHide	: true,
											closable	: true,
											draggable	: true
										});
									} else {
										if(!CheckDate('个人生日', this.getRawValue())) {
											new Ext.ToolTip({
												title		: '提示',
												target		: 'LEGAL_EXPIRED_DATE',
												anchor		: 'left',
												html		: ' 是无效的日期 - 必须符合格式:yyyy-mm-dd',
												autoHide	: true,
												closable	: true,
												draggable	: true
											});
										}
									}
								}
							}
						}, {// 是否长期有效
							xtype		: 'checkbox',
							id			: 'longterm',
							boxLabel	: convertFontCss("长期有效") + '<font color = blue>' + convertFontCss("(可手工录入证件有效期，格式为：年-月-日)") + '</font>',
							inputValue	: '1',
							listeners	: {
								'check'		: function(obj, ischeck) {
									if(ischeck == true) {
										Ext.getCmp("LEGAL_EXPIRED_DATE").setValue("9999-12-31");
										Ext.getCmp("LEGAL_EXPIRED_DATE").setReadOnly(true);
									} else {
										Ext.getCmp("LEGAL_EXPIRED_DATE").setValue("");
										Ext.getCmp("LEGAL_EXPIRED_DATE").setReadOnly(false);
									}
								},
								'disable'	: function() {
									if(this.el) {
										this.el.up("div").removeClass("x-item-disabled");
										this.el.up("div").setStyle({
											color	: "#555",
											cursor	: "default",
											opacity	: 1
										});
									}
								}
							}
						}]
					}]
				}, {
					columnWidth	: 0.5,
					layout		: 'form',
					items		: [{
						xtype		: 'textfield',
						width		: 200,
						name		: 'twIdentNum3',
						id			: 'twIdentNum3',
						fieldLabel	: '<font color=red>\*</font>' + convertFontCss("台湾身份证"),
						hidden		: true,
						readOnly	: true,
						cls			: 'x-readOnly'
					}]
				}, {// 持证次数
					columnWidth	: 0.5,
					layout		: 'form',
					items		: [{
						fieldLabel			: convertFontCss("持证次数"),
						xtype				: 'numberfield',
						name				: 'chizhengcishu',
						decimalPrecision	: 0,
						id					: 'chizhengcishu',
						hidden				: true,
						width				: 200
					}]
				}, {
					columnWidth	: 1,
					layout		: 'form',
					items		: [{
						xtype		: 'textfield',
						width		: 200,
						name		: 'gaIdentNum3',
						id			: 'gaIdentNum3',
						fieldLabel	: '<font color=red>\*</font>' + convertFontCss("港澳身份证"),
						hidden		: true,
						readOnly	: true,
						cls			: 'x-readOnly'
					}]
				}, {
					columnWidth	: 1,
					layout		: 'form',
					items		: [{
						id		: 'dateCompositefield',
						xtype	: 'compositefield',
						anchor	: '90%',
						items	: [{// 有效日期
							xtype		: 'datefield',
							id			: 'youxiaoqixian',
							name		: 'youxiaoqixian',
							fieldLabel	: '<font color="red">*</font>' + convertFontCss("有效日期"),
							width		: 200,
							invalidText	: '您输入的日期格式有误，必须是"YYYY-mm-dd"',
							minValue	: new Date(),
							format		: 'Y-m-d'
						}, {// 是否长期有效
							xtype		: 'checkbox',
							id			: 'longterm2',
							boxLabel	: convertFontCss("长期有效") + '<font color = blue>' + convertFontCss("(可手工录入证件有效期，格式为：年-月-日)") + '</font>',
							inputValue	: '1',
							listeners	: {
								'check'		: function(obj, ischeck) {
									if(ischeck == true) {
										Ext.getCmp("youxiaoqixian").setValue("9999-12-31");
										Ext.getCmp("youxiaoqixian").setReadOnly(true);
									} else {
										Ext.getCmp("youxiaoqixian").setValue("");
										Ext.getCmp("youxiaoqixian").setReadOnly(false);
									}
								},
								'disable'	: function() {
									if(this.el) {
										this.el.up("div").removeClass("x-item-disabled");
										this.el.up("div").setStyle({
											color	: "#555",
											cursor	: "default",
											opacity	: 1
										});
									}
								}
							}
						}]
					}]
				}, {// 居住地址
					columnWidth	: 1,
					layout		: 'form',
					items		: [{
						xtype		: 'compositefield',
						fieldLabel	: '<font color="red">*</font>' + convertFontCss("居住地址"),
						anchor		: '99%',
						items		: [{
							xtype				: 'combo',
							hiddenName			: 'HOME_ADDR',
							id					: 'HOME_ADDR',
							emptyText			: '请选择',
							store				: conStore,
							width				: 200,
							resizable			: true,
							valueField			: 'key',
							allowBlank			: false,
							displayField		: 'value',
							mode				: 'local',
							valueNotFoundText	: "",
							triggerAction		: 'all',
							listeners			: {
								'focus'	: {
									fn		: function(e) {
										e.expand();
										this.doQuery(this.allQuery, true);
									},
									buffer	: 200
								}
							}
						}, {// 居住地址（详细内容）
							xtype		: 'textfield',
							name		: 'HOME_ADDR_INFO',
							emptyText	: "请输入详细居住地址",
							id			: 'HOME_ADDR_INFO',
							maxLength	: 200,
							anchor		: '90%',
							width		: 400,
							allowBlank	: false
						}, {// 居住地状态
							xtype	: 'radiogroup',
							id		: 'isRent',
							columns	: 2,
							width	: 150,
							items	: [{
								boxLabel	: convertFontCss('租赁'),
								name		: 'isRent',
								inputValue	: '2',
								listeners	: {
									'disable'	: function() {
										if(this.el) {
											this.el.up("div").removeClass("x-item-disabled");
											this.el.up("div").setStyle({
												color	: "#555",
												cursor	: "default",
												opacity	: 1
											});
										}
									}
								}
							}, {
								boxLabel	: convertFontCss('自有'),
								name		: 'isRent',
								inputValue	: '1',
								listeners	: {
									'disable'	: function() {
										if(this.el) {
											this.el.up("div").removeClass("x-item-disabled");
											this.el.up("div").setStyle({
												color	: "#555",
												cursor	: "default",
												opacity	: 1
											});
										}
									}
								}
							}]
						}]
					}]
				}, {// 居住地邮编
					columnWidth	: 1,
					layout		: 'form',
					items		: [{
						xtype		: 'textfield',
						name		: 'POST_ZIPCODE',
						id			: 'POST_ZIPCODE',
						fieldLabel	: convertFontCss("居住地邮编"),
						width		: 200,
						maxLength	: 50,
						vtype		:'numeric'
					}]
				}, {
					columnWidth	: 1,
					layout		: 'form',
					items		: [{
						xtype		: 'compositefield',
						fieldLabel	: '<font color="red">*</font>' + convertFontCss("邮寄地址"),
						anchor		: '90%',
						items		: [{// 邮寄地址同上
							xtype		: 'checkbox',
							id			: 'same',
							boxLabel	: convertFontCss("同上"),
							name		: 'same',
							anchor		: '92%',
							inputValue	: '1',
							listeners	: {
								'check'		: function(obj, ischeck) {
									if(ischeck == true) {
										Ext.getCmp("MAIL_ADDR").setValue(Ext.getCmp("HOME_ADDR").getValue());
										Ext.getCmp("MAIL_ADDR_INFO").setValue(Ext.getCmp("HOME_ADDR_INFO").getValue());
										Ext.getCmp("MAIL_ZIPCODE").setValue(Ext.getCmp("POST_ZIPCODE").getValue());
										Ext.getCmp("MAIL_ADDR").setReadOnly(true);
										Ext.getCmp("MAIL_ADDR_INFO").setReadOnly(true);
										Ext.getCmp("MAIL_ZIPCODE").setReadOnly(true);
										Ext.getCmp("MAIL_ADDR").cls = 'x-readOnly';
										Ext.getCmp("MAIL_ADDR_INFO").cls = 'x-readOnly';
										Ext.getCmp("MAIL_ZIPCODE").cls = 'x-readOnly';
									} else {
										Ext.getCmp("MAIL_ADDR").setValue("");
										Ext.getCmp("MAIL_ADDR_INFO").setValue("");
										Ext.getCmp("MAIL_ZIPCODE").setValue("");
										Ext.getCmp("MAIL_ADDR").setReadOnly(false);
										Ext.getCmp("MAIL_ADDR_INFO").setReadOnly(false);
										Ext.getCmp("MAIL_ZIPCODE").setReadOnly(false);
									}
								},
								'disable'	: function() {
									if(this.el) {
										this.el.up("div").removeClass("x-item-disabled");
										this.el.up("div").setStyle({
											color	: "#555",
											cursor	: "default",
											opacity	: 1
										});
									}
								}
							}
						}, {// 邮寄地址国籍
							xtype				: 'combo',
							hiddenName			: 'MAIL_ADDR',
							id					: 'MAIL_ADDR',
							store				: conStore,
							anchor				: '90%',
							width				: 150,
							resizable			: true,
							allowBlank			: false,
							valueField			: 'key',
							displayField		: 'value',
							emptyText			: '请选择',
							mode				: 'local',
							valueNotFoundText	: "",
							triggerAction		: 'all',
							listeners			: {
								'focus'	: {
									fn		: function(e) {
										e.expand();
										this.doQuery(this.allQuery, true);
									},
									buffer	: 200
								}
							}
						}, {// 邮寄详细地址
							xtype		: 'textfield',
							name		: 'MAIL_ADDR_INFO',
							id			: 'MAIL_ADDR_INFO',
							maxLength	: 200,
							width		: 400,
							anchor		: '90%',
							allowBlank	: false,
							emptyText	: "请输入详细邮寄地址"
						}]
					}]
				}, {// 邮寄地邮编
					columnWidth	: 1,
					layout		: 'form',
					items		: [{
						xtype		: 'textfield',
						name		: 'MAIL_ZIPCODE',
						id			: 'MAIL_ZIPCODE',
						fieldLabel	: convertFontCss("邮寄邮编"),
						width		: 200,
						maxLength	: 50,
						vtype		:'numeric'

					}]
				}, {
					columnWidth	: 1,
					layout		: 'form',
					items		: [{
						xtype		: 'compositefield',
						fieldLabel	: '<font color="red">*</font>' + convertFontCss("移动电话"),
						anchor		: '95%',
						items		: [{
							xtype				: 'combo',
							hiddenName			: 'mbPhone',
							anchor				: '95%',
							id					: 'mbPhone',
							// fieldLabel : '<font color="red">*</font>'+convertFontCss("移动电话"),
							emptyText			: '请选择',
							store				: globalRoamingStore,
							width				: 200,
							resizable			: true,
							allowBlank			: false,
							valueField			: 'value',
							displayField		: 'key',
							mode				: 'local',
							valueNotFoundText	: "",
							triggerAction		: 'all',
							listeners			: {
								'select'	: function(a, b, c) {
									Ext.getCmp('QUYUMA').setValue(a.value);
								},
								'focus'		: {
									fn		: function(e) {
										e.expand();
										this.doQuery(this.allQuery, true);
									},
									buffer	: 200
								}
							}
						}, {
							xtype		: 'textfield',
							name		: 'QUYUMA',
							id			: 'QUYUMA',
							anchor		: '95%',
							readOnly	: true,
							width		: 50,
							allowBlank	: false,
							cls			: 'x-readOnly'
						}, {
							xtype		: 'textfield',
							name		: 'mbPHONENUM',
							id			: 'mbPHONENUM',
							anchor		: '95%',
							width		: 250,
							allowBlank	: false,
							maxLength	: 100,
							vtype		:'numeric'
						}, {
							xtype	: 'label',
							html	: '<font style="font-size:14px;color:blue">(此电话将用于接收短信验证码和账务变动通知；台湾手机请您录入9位号码)</font>'
						}]
					}]
				}, {
					columnWidth	: 1,
					layout		: 'form',
					items		: [{
						xtype		: 'compositefield',
						fieldLabel	: convertFontCss("其他电话"),
						anchor		: '99%',
						items		: [{// 其他电话1类型选择
							xtype				: 'combo',
							hiddenName			: 'ORTHERPHONE',
							id					: 'ORTHERPHONE',
							anchor				: '92%',
							width				: 75,
							emptyText			: "请选择",
							store				: store_contmethTypes,
							valueField			: 'key',
							displayField		: 'value',
							mode				: 'local',
							valueNotFoundText	: "",
							triggerAction		: 'all',
							listeners			: {
								'change'		: function(combo, oldValue, newValue) {
									if(oldValue != newValue) {
										Ext.getCmp("PHONE_CITIZENSHIP").setValue("");
										Ext.getCmp("QUYUMA1").setValue("");
										Ext.getCmp("QUYUMA2").setValue("");
										Ext.getCmp("PHONENUM1").setValue("");
										if(this.value == "") {
											// 其他电话1国籍
											Ext.getCmp("PHONE_CITIZENSHIP").allowBlank = true;
											// 区号
											Ext.getCmp("QUYUMA1").allowBlank = true;
											// 区域码
											Ext.getCmp("QUYUMA2").allowBlank = true;
											// 电话号
											Ext.getCmp("PHONENUM1").allowBlank = true;
										} else {
											// 其他电话1国籍
											Ext.getCmp("PHONE_CITIZENSHIP").allowBlank = false;
											// 区号
											Ext.getCmp("QUYUMA1").allowBlank = false;
											// 区域码
											Ext.getCmp("QUYUMA2").allowBlank = false;
											// 电话号
											Ext.getCmp("PHONENUM1").allowBlank = false;
										}
									}

								},
								'beforeselect'	: function(combo, record, index) {
									this.oldValue == this.value;
								},
								'select'		: function() {
									if(this.oldValue == this.value) {
										return false;
									}
									if(this.value != "") {
										// 其他电话1国籍
										Ext.getCmp("PHONE_CITIZENSHIP").allowBlank = false;
										// 区号
										Ext.getCmp("QUYUMA1").allowBlank = false;
										// 区域码
										Ext.getCmp("QUYUMA2").allowBlank = false;
										// 电话号
										Ext.getCmp("PHONENUM1").allowBlank = false;
									} else {
										// 其他电话1国籍
										Ext.getCmp("PHONE_CITIZENSHIP").allowBlank = true;
										// 区号
										Ext.getCmp("QUYUMA1").allowBlank = true;
										// 区域码
										Ext.getCmp("QUYUMA2").allowBlank = true;
										// 电话号
										Ext.getCmp("PHONENUM1").allowBlank = true;
									}
								},
								'focus'			: {
									fn		: function(e) {
										e.expand();
										this.doQuery(this.allQuery, true);
									},
									buffer	: 200
								}
							}
						}, {// 其他电话1国籍
							xtype				: 'combo',
							hiddenName			: 'PHONE_CITIZENSHIP',
							anchor				: '90%',
							width				: 120,
							emptyText			: '请选择',
							id					: 'PHONE_CITIZENSHIP',
							store				: globalRoamingStore,
							resizable			: true,
							valueField			: 'value',
							displayField		: 'key',
							mode				: 'local',
							valueNotFoundText	: "",
							triggerAction		: 'all',
							listeners			: {
								'change'		: function(combo, oldValue, newValue) {
									if(this.value == "") {
										// 区号
										Ext.getCmp("QUYUMA1").setValue("");
									}
								},
								'beforeselect'	: function(combo, record, index) {
									this.oldValue == this.value;
								},
								'select'		: function() {
									if(this.oldValue == this.value) {
										return false;
									}
									if(this.value != "") {
										Ext.getCmp('QUYUMA1').setValue(this.value);
									}
								},
								'focus'			: {
									fn		: function(e) {
										e.expand();
										this.doQuery(this.allQuery, true);
									},
									buffer	: 200
								}
							}
						}, {
							xtype		: 'textfield',
							name		: 'QUYUMA1',
							id			: 'QUYUMA1',
							anchor		: '90%',
							width		: 50,
							readOnly	: true,
							cls			: 'x-readOnly'

						}, {
							xtype	: 'label',
							html	: '<font style="font-size:14px;">-区域码</font>'
						}, {// 其他电话1区域码
							xtype		: 'textfield',
							name		: 'QUYUMA2',
							id			: 'QUYUMA2',
							width		: 50,
							anchor		: '90%',
							regex		: /^[0-9]{0,10}$/,
							regexText	: convertFontCss("该输入项只能输入数字")
						}, {
							xtype	: 'label',
							html	: '<font style="font-size:14px;">-电话</font>'
						}, {// 其他电话1电话号
							xtype		: 'textfield',
							name		: 'PHONENUM1',
							id			: 'PHONENUM1',
							anchor		: '50%',
							width		: 250,
							maxLength	: 100,
							vtype		:'numeric'
						}]
					}]
				}, {
					columnWidth	: 1,
					layout		: 'form',
					items		: [{
						xtype	: 'compositefield',
						anchor	: '99%',
						items	: [{// 其他电话2类型选择
							xtype				: 'combo',
							anchor				: '92%',
							hiddenName			: 'ORTHERPHONE1',
							emptyText			: "请选择",
							width				: 75,
							id					: 'ORTHERPHONE1',
							store				: store_contmethTypes,
							resizable			: true,
							valueField			: 'key',
							displayField		: 'value',
							mode				: 'local',
							valueNotFoundText	: "",
							triggerAction		: 'all',
							listeners			: {
								'change'		: function(combo, oldValue, newValue) {
									if(oldValue != newValue) {
										Ext.getCmp("PHONE_CITIZENSHIP1").setValue("");
										Ext.getCmp("QUYUMA3").setValue("");
										Ext.getCmp("QUYUMA4").setValue("");
										Ext.getCmp("PHONENUM2").setValue("");
										if(this.value == "") {
											// 其他电话2国籍
											Ext.getCmp("PHONE_CITIZENSHIP1").allowBlank = true;
											// 区号
											Ext.getCmp("QUYUMA3").allowBlank = true;
											// 区域码
											Ext.getCmp("QUYUMA4").allowBlank = true;
											// 电话号
											Ext.getCmp("PHONENUM2").allowBlank = true;
										}
									}
								},
								'beforeselect'	: function(combo, record, index) {
									this.oldValue == this.value;
								},
								'select'		: function() {
									if(this.oldValue == this.value) {
										return false;
									}
									if(this.value != "") {
										// 其他电话2国籍
										Ext.getCmp("PHONE_CITIZENSHIP1").allowBlank = false;
										// 区号
										Ext.getCmp("QUYUMA3").allowBlank = false;
										// 区域码
										Ext.getCmp("QUYUMA4").allowBlank = false;
										// 电话号
										Ext.getCmp("PHONENUM2").allowBlank = false;
									} else {
										// 其他电话2国籍
										Ext.getCmp("PHONE_CITIZENSHIP1").allowBlank = true;
										// 区号
										Ext.getCmp("QUYUMA3").allowBlank = true;
										// 区域码
										Ext.getCmp("QUYUMA4").allowBlank = true;
										// 电话号
										Ext.getCmp("PHONENUM2").allowBlank = true;
									}
								},
								'focus'			: {
									fn		: function(e) {
										e.expand();
										this.doQuery(this.allQuery, true);
									},
									buffer	: 200
								}
							}
						}, {// 其他电话2国籍选择
							xtype				: 'combo',
							anchor				: '90%',
							width				: 120,
							hiddenName			: 'PHONE_CITIZENSHIP1',
							emptyText			: "请选择",
							id					: 'PHONE_CITIZENSHIP1',
							store				: globalRoamingStore,
							resizable			: true,
							valueField			: 'value',
							displayField		: 'key',
							valueNotFoundText	: "",
							mode				: 'local',
							triggerAction		: 'all',
							listeners			: {
								'change'		: function(combo, oldValue, newValue) {
									if(this.value == "") {
										// 区号
										Ext.getCmp("QUYUMA3").setValue("");
									}
								},
								'beforeselect'	: function(combo, record, index) {
									this.oldValue == this.value;
								},
								'select'		: function() {
									if(this.oldValue == this.value) {
										return false;
									}
									if(this.value != "") {
										Ext.getCmp('QUYUMA3').setValue(this.value);
									}
								},
								'focus'			: {
									fn		: function(e) {
										e.expand();
										this.doQuery(this.allQuery, true);
									},
									buffer	: 200
								}
							}
						}, {// 其他电话2国际区号
							xtype		: 'textfield',
							anchor		: '90%',
							width		: 50,
							name		: 'QUYUMA3',
							id			: 'QUYUMA3',
							readOnly	: true,
							cls			: 'x-readOnly'
						}, {
							xtype	: 'label',
							html	: '<font style="font-size:14px;">-区域码</font>'
						}, {// 其他电话2区域码
							xtype		: 'textfield',
							id			: 'QUYUMA4',
							anchor		: '90%',
							width		: 50,
							regex		: /^[0-9]{0,10}$/,
							regexText	: convertFontCss("该输入项只能输入数字")
						}, {
							xtype	: 'label',
							html	: '<font style="font-size:14px;">-电话</font>'
						}, {// 其他电话2电话号码
							xtype		: 'textfield',
							name		: 'PHONENUM2',
							id			: 'PHONENUM2',
							anchor		: '50%',
							width		: 250,
							maxLength	: 100,
							vtype		:'numeric'
						}]
					}]
				}, {// 职业资料
					columnWidth	: 0.6,
					layout		: 'form',
					items		: [{
						xtype		: 'radiogroup',
						id			: 'JOBINFO',
						fieldLabel	: '<font color="red">*</font>' + convertFontCss("职业资料"),
						columns		: 4,
						allowBlank	: false,
						anchor		: '90%',
						autoWidth	: true,
						items		: [{
							boxLabel	: convertFontCss('全日制雇员'),
							name		: 'JOBINFO',
							inputValue	: '04',
							listeners	: {
								'disable'	: function() {
									if(this.el) {
										this.el.up("div").removeClass("x-item-disabled");
										this.el.up("div").setStyle({
											color	: "#555",
											cursor	: "default",
											opacity	: 1
										});
									}
								}
							}
						}, {
							boxLabel	: convertFontCss('自雇'),
							name		: 'JOBINFO',
							inputValue	: '05',
							listeners	: {
								'disable'	: function() {
									if(this.el) {
										this.el.up("div").removeClass("x-item-disabled");
										this.el.up("div").setStyle({
											color	: "#555",
											cursor	: "default",
											opacity	: 1
										});
									}
								}
							}
						}, {
							boxLabel	: convertFontCss('退休'),
							name		: 'JOBINFO',
							inputValue	: '10',
							listeners	: {
								'disable'	: function() {
									if(this.el) {
										this.el.up("div").removeClass("x-item-disabled");
										this.el.up("div").setStyle({
											color	: "#555",
											cursor	: "default",
											opacity	: 1
										});
									}
								}
							}
						}, {
							boxLabel	: convertFontCss('其他'),
							name		: 'JOBINFO',
							inputValue	: '99',
							listeners	: {
								'disable'	: function() {
									if(this.el) {
										this.el.up("div").removeClass("x-item-disabled");
										this.el.up("div").setStyle({
											color	: "#555",
											cursor	: "default",
											opacity	: 1
										});
									}
								}
							}
						}],
						listeners	: {
							'change'	: function(a, b) {
								if(b.inputValue == '99') {
									Ext.getCmp('JOBREMARK').setVisible(true);
									Ext.getCmp('JOBNAME').setVisible(false);
									Ext.getCmp('JOBNAME').allowBlank = true;
									Ext.getCmp('JOB').allowBlank = true;
									Ext.getCmp('JOB').setVisible(false);
									Ext.getCmp('JOBREMARK').allowBlank = false;
								} else if(b.inputValue == '04') {
									Ext.getCmp('JOBREMARK').setVisible(false);
									Ext.getCmp('JOBNAME').setVisible(true);
									Ext.getCmp('JOB').setVisible(true);
									Ext.getCmp('JOBNAME').allowBlank = false;
									Ext.getCmp('JOB').allowBlank = false;
									Ext.getCmp('JOBREMARK').allowBlank = true;
								} else {
									Ext.getCmp('JOBREMARK').setVisible(false);
									Ext.getCmp('JOBNAME').setVisible(false);
									Ext.getCmp('JOB').setVisible(false);
									Ext.getCmp('JOBNAME').allowBlank = true;
									Ext.getCmp('JOB').allowBlank = true;
									Ext.getCmp('JOBREMARK').allowBlank = true;
								}
							}
						}
					}]

				}, {// 职业资料其他备注
					columnWidth	: .40,
					layout		: 'form',
					// labelWidth:100,
					items		: [{
						xtype		: 'textfield',
						fieldLabel	: convertFontCss("（请具体注明）"),
						name		: 'JOBREMARK',
						id			: 'JOBREMARK',
						width		: 200,
						maxLength	: 100,
						allowBlank	: false
					}]
				}, {// 职业资料全日制单位名称
					columnWidth	: 0.5,
					layout		: 'form',
					items		: [{
						xtype		: 'textfield',
						fieldLabel	: '<font color=red>*</font>' + convertFontCss("单位名称"),
						name		: 'JOBNAME',
						id			: 'JOBNAME',
						width		: 200
					}]
				}, {// 职业资料全日制职位
					columnWidth	: 0.5,
					layout		: 'form',
					items		: [{
						xtype		: 'textfield',
						fieldLabel	: '<font color=red>*</font>' + convertFontCss("职位"),
						name		: 'JOB',
						id			: 'JOB',
						width		: 200
					}]
				}, {// 电子邮箱E-mail
					columnWidth	: 0.5,
					layout		: 'form',
					items		: [{
						xtype		: 'textfield',
						fieldLabel	: convertFontCss("电子邮箱E-mail"),
						name		: 'EMAIL',
						id			: 'EMAIL',
						width		: 200,
						vtype		: 'email'
					}]
				}, {// 在我行有无关联人
					columnWidth	: 0.5,
					layout		: 'form',
					items		: [{
						xtype		: 'radiogroup',
						id			: 'HASRELATED',
						fieldLabel	: '<font color="red">*</font>' + convertFontCss("在我行有无关联人"),
						columns		: 2,
						allowBlank	: false,
						anchor		: '70%',
						items		: [{
							boxLabel	: '有',
							name		: 'HASRELATED',
							inputValue	: 1,
							listeners	: {
								'disable'	: function() {
									if(this.el) {
										this.el.up("div").removeClass("x-item-disabled");
										this.el.up("div").setStyle({
											color	: "#555",
											cursor	: "default",
											opacity	: 1
										});
									}
								}
							}
						}, {
							boxLabel	: '无',
							name		: 'HASRELATED',
							inputValue	: 0,
							listeners	: {
								'disable'	: function() {
									if(this.el) {
										this.el.up("div").removeClass("x-item-disabled");
										this.el.up("div").setStyle({
											color	: "#555",
											cursor	: "default",
											opacity	: 1
										});
									}
								}
							}
						}],
						listeners	: {
							'change'	: function(a, b) {
								if(b.inputValue == 1) {
									Ext.getCmp('RELATEDNAME').setVisible(true);
									Ext.getCmp('RELATEDNAME').allowBlank = false;
									Ext.getCmp('RELATION').setVisible(true);
									Ext.getCmp('RELATION').allowBlank = false;
								} else {
									Ext.getCmp('RELATEDNAME').setVisible(false);
									Ext.getCmp('RELATEDNAME').allowBlank = true;
									Ext.getCmp('RELATION').setVisible(false);
									Ext.getCmp('RELATION').allowBlank = true;
								}
							}
						}
					}]
				}, {// 关联人姓名
					columnWidth	: 0.5,
					layout		: 'form',
					items		: [{
						fieldLabel	: '<font color="red">*</font>'+convertFontCss("关联人姓名"),
						xtype		: 'textfield',
						name		: 'RELATEDNAME',
						id			: 'RELATEDNAME',
						maxLength	: 20,
						width		: 200
					}]
				}, {// 与关联人关系
					columnWidth	: 0.5,
					layout		: 'form',
					items		: [{
						fieldLabel	: '<font color="red">*</font>'+convertFontCss("与关联人关系"),
						xtype		: 'textfield',
						name		: 'RELATION',
						id			: 'RELATION',
						maxLength	: 50,
						width		: 200
					}]
				}, { // 与我行关联关系
					columnWidth	: 0.5,
					layout		: 'form',
					items		: [{
						fieldLabel			: '<font color="red">*</font>' + convertFontCss("与我行关联关系"),
						xtype				: 'combo',
						hiddenName			: 'RELATION1',
						id					: 'RELATION1',
						emptyText			: "请选择",
						store				: staffinStore,
						resizable			: true,
						valueField			: 'key',
						displayField		: 'value',
						mode				: 'local',
						valueNotFoundText	: "",
						triggerAction		: 'all',
						allowBlank			: false,
						width				: 200,
						listeners			: {
							'focus'	: {
								fn		: function(e) {
									e.expand();
									this.doQuery(this.allQuery, true);
								},
								buffer	: 200
							}
						}
					}]
				}, {// 来源渠道
					columnWidth	: 0.5,
					layout		: 'form',
					items		: [{
						xtype				: 'combo',
						hiddenName			: 'SOURCECHANNEL',
						id					: 'SOURCECHANNEL',
						fieldLabel			: '<font color="red">*</font>' + convertFontCss("来源渠道"),
						emptyText			: "请选择",
						editable			: false,
						store				: store_channels,
						resizable			: true,
						allowBlank			: false,
						valueField			: 'key',
						displayField		: 'value',
						mode				: 'local',
						valueNotFoundText	: "",
						triggerAction		: 'all',
						width				: 200,
						listeners			: {
							'focus'	: {
								fn		: function(e) {
									e.expand();
									this.doQuery(this.allQuery, true);
								},
								buffer	: 200
							}
						}
					}]
				}, {// 风险国别代码
					columnWidth	: 0.5,
					layout		: 'form',
					items		: [{
						xtype				: 'combo',
						width				: 200,
						readOnly			: false,
						maxLength			: 30,
						allowBlank			: false,
						fieldLabel			: '<font color="red">*</font>' + convertFontCss("风险国别代码"),
						name				: 'RISK_NATION_CODE',
						id					: 'RISK_NATION_CODE',
						hiddenName			: 'RISK_NATION_CODE',
						store				: riskStore,
						resizable			: true,
						valueField			: 'key',
						displayField		: 'value',
						mode				: 'local',
						valueNotFoundText	: "",
						triggerAction		: 'all',
						listeners			: {
							'focus'	: {
								fn		: function(e) {
									e.expand();
									this.doQuery(this.allQuery, true);
								},
								buffer	: 200
							}
						}
					}]
				}, {// 所属客户经理
					layout		: 'form',
					columnWidth	: 0.5,
					items		: [{
						xtype			: 'customerUserchoose',
						searchType		: 'ALLORG',
						id				: 'customManager',
						hiddenName		: 'id',
						width			: 200,
						fieldLabel		: '<font color=red>*</font>' + convertFontCss("所属客户经理"),
						singleSelect	: true,
						allowBlank		: false,
						searchField		: true
					}]
				}, {// 预约网点
					columnWidth	: 0.5,
					layout		: 'form',
					items		: [{
						xtype				: 'combo',
						id					: 'YUYUEWANGDIAN',
						hiddenName			: 'YUYUEWANGDIAN',
						fieldLabel			: convertFontCss("预约网点"),
						emptyText			: "请选择",
						store				: orgStore,
						resizable			: true,
						valueField			: 'key',
						displayField		: 'value',
						mode				: 'local',
						valueNotFoundText	: "",
						triggerAction		: 'all',
						readOnly			: true,
						cls					: 'x-readOnly',
						hidden				: true,
						width				: 200
					}]
				}, {// 预约日期
					columnWidth	: 0.5,
					layout		: 'form',
					items		: [{
						xtype		: 'datefield',
						id			: 'YUYUERIQI',
						width		: 200,
						name		: 'YUYUERIQI',
						fieldLabel	: convertFontCss("预约日期"),
						invalidText	: '您输入的日期格式有误，必须是"YYYY-mm-dd"',
						format		: 'Y-m-d',
						minValue	: new Date(),
						readOnly	: true,
						cls			: 'x-readOnly',
						hidden		: true
					}]
				}, /*{
                    // 预约时间段
                    columnWidth : 1,
                    layout      : 'form',
                    items       : [{
                        xtype       : 'textfield',
                        fieldLabel  : convertFontCss("预约时间段"),
                        name        : 'YUYUESHIJIANDUAN',
                        id          : 'YUYUESHIJIANDUAN',
                        hidden      : true,
                        readOnly    : true,
                        cls         : 'x-readOnly',
                        width       : 200
                    }]
                },*/ {
					// 与我行关联关系
					columnWidth	: 0.5,
					layout		: 'form',
					items		: [{
						fieldLabel			: convertFontCss("预约时间段"),
						xtype				: 'combo',
						hiddenName			: 'YUYUESHIJIANDUAN',
						id					: 'YUYUESHIJIANDUAN',
						emptyText			: "请选择",
						store				: store_reviewTime,
						resizable			: true,
						hidden				: true,
						valueField			: 'key',
						displayField		: 'value',
						mode				: 'local',
						valueNotFoundText	: "",
						triggerAction		: 'all',
						width				: 200,
						listeners			: {
							'focus'	: {
								fn		: function(e) {
									e.expand();
									this.doQuery(this.allQuery, true);
								},
								buffer	: 200
							}
						}
					}]
				}]
			}]
		}]
	}, {
		layout	: 'column',
		items	: [{
			title		: '<font style="font-size:16px;">联名户信息</font>',
			columnWidth	: 1,
			id			: 'lianminghu',
			// hidden:true,
			xtype		: 'fieldset',
			style		: 'margin:0 180px;',
			items		: [{
				layout	: 'column',
				items	: [{
					columnWidth	: 0.5,
					layout		: 'form',
					items		: [{
						xtype		: 'textfield',
						id			: 'lianMinPinYin',
						width		: 200,
						maxLength	: 80,
						fieldLabel	: '<font color=red>\*</font>' + convertFontCss("联名户姓名拼音"),
						listeners	: {
							'blur'	: function() {
								var value = Ext.getCmp("lianMinPinYin").getValue();
								Ext.getCmp("lianMinPinYin").setValue(Ext.util.Format.uppercase(value));
							}
						}

					}]
				}, {
					columnWidth	: 0.5,
					layout		: 'form',
					items		: [{
						xtype	: 'textfield',
						id		: 'custId2',
						hidden	: true
					}]
				}, {
					columnWidth	: 0.5,
					layout		: 'form',
					items		: [{
						xtype		: 'radiogroup',
						id			: 'sex',
						fieldLabel	: '<font color=red>\*</font>' + convertFontCss("联名户性别"),
						columns		: 2,
						anchor		: '60%',
						items		: [{
							boxLabel	: '男',
							name		: 'sex',
							inputValue	: '1',
							listeners	: {
								'disable'	: function() {
									if(this.el) {
										this.el.up("div").removeClass("x-item-disabled");
										this.el.up("div").setStyle({
											color	: "#555",
											cursor	: "default",
											opacity	: 1
										});
									}
								}
							}
						}, {
							boxLabel	: '女',
							name		: 'sex',
							inputValue	: '2',
							listeners	: {
								'disable'	: function() {
									if(this.el) {
										this.el.up("div").removeClass("x-item-disabled");
										this.el.up("div").setStyle({
											color	: "#555",
											cursor	: "default",
											opacity	: 1
										});
									}
								}
							}
						}]
					}]
				}, {
					columnWidth	: 0.5,
					layout		: 'form',
					items		: [{
						xtype				: 'combo',
						hiddenName			: 'CITIZENSHIP1',
						id					: 'CITIZENSHIP1',
						fieldLabel			: '<font color="red">*</font>' + convertFontCss("联名户国籍"),
						width				: 200,
						emptyText			: "请选择",
						store				: conStore,
						resizable			: true,
						valueField			: 'key',
						displayField		: 'value',
						mode				: 'local',
						valueNotFoundText	: "",
						triggerAction		: 'all'
					}]
				}, {
					columnWidth	: 0.5,
					layout		: 'form',
					items		: [{
						xtype				: 'combo',
						id					: 'CITIZENSHIP2',
						hiddenName			: 'CITIZENSHIP2',
						fieldLabel			: convertFontCss("发证机关所在地"),
						emptyText			: "请选择",
						width				: 200,
						store				: dqStore,
						resizable			: true,
						valueField			: 'key',
						displayField		: 'value',
						mode				: 'local',
						valueNotFoundText	: "",
						triggerAction		: 'all'
					}]
				}, {
					columnWidth	: 0.5,
					layout		: 'form',
					items		: [{
						xtype				: 'combo',
						hiddenName			: 'lianMingIdenType1',
						id					: 'lianMingIdenType1',
						fieldLabel			: '<font color="red">*</font>' + convertFontCss("证件类型"),
						width				: 200,
						emptyText			: "请选择",
						store				: store_ident,
						resizable			: true,
						valueField			: 'key',
						displayField		: 'value',
						mode				: 'local',
						valueNotFoundText	: "",
						readOnly			: true,
						cls					: 'x-readOnly',
						triggerAction		: 'all'
					}]
				}, {
					columnWidth	: 0.5,
					layout		: 'form',
					items		: [{
						xtype		: 'textfield',
						id			: 'lianMingIdenNo1',
						width		: 200,
						fieldLabel	: '<font color=red>\*</font>' + convertFontCss("证件号码"),
						readOnly	: true,
						cls			: 'x-readOnly'
					}]
				}, {
					columnWidth	: 1,
					layout		: 'form',
					items		: [{
						xtype	: 'compositefield',
						anchor	: '90%',
						items	: [{// 有效日期
							xtype		: 'datefield',
							id			: 'LEGAL_EXPIRED_DATE2',
							width		: 200,
							name		: 'LEGAL_EXPIRED_DATE2',
							invalidText	: '您输入的日期格式有误，必须是"YYYY-mm-dd"',
							minValue	: new Date(),
							fieldLabel	: '<font color="red">*</font>' + convertFontCss("有效日期"),
							format		: 'Y-m-d',
							beforeBlur	: function() {
								return false;
							},
							listeners	: {
								'blur'	: function(datefield, record) {
									if(!this.validate()) {
										new Ext.ToolTip({
											title		: '提示',
											target		: 'LEGAL_EXPIRED_DATE2',
											anchor		: 'left',
											html		: ' 是无效的日期 - 必须符合格式:yyyy-mm-dd',
											autoHide	: true,
											closable	: true,
											draggable	: true
										});
									} else {
										if(!CheckDate('个人生日', this.getRawValue())) {
											new Ext.ToolTip({
												title		: '提示',
												target		: 'LEGAL_EXPIRED_DATE2',
												anchor		: 'left',
												html		: ' 是无效的日期 - 必须符合格式:yyyy-mm-dd',
												autoHide	: true,
												closable	: true,
												draggable	: true
											});
										}
									}
								}
							}
						}, {// 是否长期有效
							xtype		: 'checkbox',
							id			: 'LONGTERM2',
							boxLabel	: convertFontCss("长期有效") + '<font color = blue>' + convertFontCss("(可手工录入证件有效期，格式为：年-月-日)") + '</font>',
							name		: 'LONGTERM2',
							inputValue	: '1',
							listeners	: {
								'check'		: function(obj, ischeck) {
									if(ischeck == true) {
										Ext.getCmp("LEGAL_EXPIRED_DATE2").setValue("9999-12-31");
										Ext.getCmp("LEGAL_EXPIRED_DATE2").setReadOnly(true);
									} else {
										Ext.getCmp("LEGAL_EXPIRED_DATE2").setValue("");
										Ext.getCmp("LEGAL_EXPIRED_DATE2").setReadOnly(false);
									}
								},
								'disable'	: function() {
									if(this.el) {
										this.el.up("div").removeClass("x-item-disabled");
										this.el.up("div").setStyle({
											color	: "#555",
											cursor	: "default",
											opacity	: 1
										});
									}
								}
							}
						}]
					}]
				}, {
					columnWidth	: 0.5,
					layout		: 'form',
					items		: [{
						xtype		: 'textfield',
						width		: 200,
						fieldLabel	: '<font color=red>\*</font>' + convertFontCss("台湾身份证"),
						name		: 'lianMingTwIdentNum1',
						id			: 'lianMingTwIdentNum1',
						readOnly	: true,
						hidden		: true,
						cls			: 'x-readOnly'
					}]
				}, {
					columnWidth	: 0.5,
					layout		: 'form',
					items		: [{
						xtype		: 'textfield',
						width		: 200,
						fieldLabel	: '<font color=red>\*</font>' + convertFontCss("港澳身份证"),
						name		: 'lianMingGaIdentNum1',
						id			: 'lianMingGaIdentNum1',
						readOnly	: true,
						hidden		: true,
						cls			: 'x-readOnly'
					}]
				}, {
					columnWidth	: 1,
					layout		: 'form',
					items		: [{
						id		: 'dateCompositefield2',
						xtype	: 'compositefield',
						anchor	: '90%',
						items	: [{// 有效日期
							xtype		: 'datefield',
							id			: 'LEGAL_EXPIRED_DATE1',
							width		: 200,
							name		: 'LEGAL_EXPIRED_DATE1',
							invalidText	: '您输入的日期格式有误，必须是"YYYY-mm-dd"',
							minValue	: new Date(),
							fieldLabel	: '<font color="red">*</font>' + convertFontCss("有效日期"),
							format		: 'Y-m-d',
							beforeBlur	: function() {
								return false;
							},
							listeners	: {
								'blur'	: function(datefield, record) {
									if(!this.validate()) {
										new Ext.ToolTip({
											title		: '提示',
											target		: 'LEGAL_EXPIRED_DATE1',
											anchor		: 'left',
											html		: ' 是无效的日期 - 必须符合格式:yyyy-mm-dd',
											autoHide	: true,
											closable	: true,
											draggable	: true
										});
									} else {
										if(!CheckDate('个人生日', this.getRawValue())) {
											new Ext.ToolTip({
												title		: '提示',
												target		: 'LEGAL_EXPIRED_DATE1',
												anchor		: 'left',
												html		: ' 是无效的日期 - 必须符合格式:yyyy-mm-dd',
												autoHide	: true,
												closable	: true,
												draggable	: true
											});
										}
									}
								}
							}
						}, {// 是否长期有效
							xtype		: 'checkbox',
							id			: 'LONGTERM1',
							boxLabel	: convertFontCss("长期有效") + '<font color = blue>' + convertFontCss("(可手工录入证件有效期，格式为：年-月-日)") + '</font>',
							name		: 'LONGTERM1',
							inputValue	: '1',
							listeners	: {
								'check'		: function(obj, ischeck) {
									if(ischeck == true) {
										Ext.getCmp("LEGAL_EXPIRED_DATE1").setValue("9999-12-31");
										Ext.getCmp("LEGAL_EXPIRED_DATE1").setReadOnly(true);
									} else {
										Ext.getCmp("LEGAL_EXPIRED_DATE1").setValue("");
										Ext.getCmp("LEGAL_EXPIRED_DATE1").setReadOnly(false);
									}
								},
								'disable'	: function() {
									if(this.el) {
										this.el.up("div").removeClass("x-item-disabled");
										this.el.up("div").setStyle({
											color	: "#555",
											cursor	: "default",
											opacity	: 1
										});
									}
								}
							}
						}]
					}]
				}]
			}]
		}]
	}, {
		layout	: 'column',
		items	: [{
			title		: '<font style="font-size:16px;">本人声明</font>',
			columnWidth	: 1,
			// id:'shengming',
			xtype		: 'fieldset',
			style		: 'margin:0px 180px 30px 180px;',
			items		: [{
				layout	: 'column',
				items	: [{
					columnWidth	: 1,
					xtype		: 'fieldset',
					id			: 'shengming',
					title		: '<font style="font-size:16px;">证件1：</font>',
					style		: 'margin:10px 20px 20px 10px;',
					items		: [{
						columnWidth	: 1,
						layout		: 'form',
						items		: [{
							xtype		: 'radiogroup',
							columns		: 1,
							id			: 'radio1',
							allowBlank	: false,
							_inited		: false,// 自定义属性，用来标识是否回显数据，如果是回显则不删除exceptionHand下内容
							anchor		: '90%',
							items		: [{
								boxLabel	: convertFontCss('1、仅为中国税收居民'),
								name		: 'radio1',
								inputValue	: '01',
								listeners	: {
									'disable'	: function() {
										if(this.el) {
											this.el.up("div").removeClass("x-item-disabled");
											this.el.up("div").setStyle({
												color	: "#555",
												cursor	: "default",
												opacity	: 1
											});
										}
									}
								}
							}, {
								boxLabel	: convertFontCss('2、仅为非居民'),
								name		: 'radio1',
								inputValue	: '02',
								listeners	: {
									'disable'	: function() {
										if(this.el) {
											this.el.up("div").removeClass("x-item-disabled");
											this.el.up("div").setStyle({
												color	: "#555",
												cursor	: "default",
												opacity	: 1
											});
										}
									}
								}
							}, {
								boxLabel	: convertFontCss('3、既是中国税收居民又是其他国税收居民'),
								name		: 'radio1',
								inputValue	: '03',
								listeners	: {
									'disable'	: function() {
										if(this.el) {
											this.el.up("div").removeClass("x-item-disabled");
											this.el.up("div").setStyle({
												color	: "#555",
												cursor	: "default",
												opacity	: 1
											});
										}
									}
								}
							}],
							listeners	: {
								'change'	: function(a, b) {
									Ext.getCmp("detailReason").allowBlank = true;
									// 清空3个原因项的值
									Ext.getCmp("REASON").setValue("");
									Ext.getCmp("REASON2").setValue("");
									Ext.getCmp("detailReason").setValue("");
									// 2、仅为非居民、3、既是中国税收居民又是其他国税收居民
									if(b.inputValue == '02' || b.inputValue == '03') {
										// 显示他国税收信息区域
										Ext.getCmp('shengMing').setVisible(true);
										// 是否为美国纳税人设置为必输项
										Ext.getCmp('isUNtaxpayer').allowBlank = false;
										if(this._inited == false) {
											this._inited = true;
										} else {
											Ext.getCmp('isUNtaxpayer').reset();
											// 删除原有的税收居民国（地区）和纳税人识别号（TIN）输入框
											Ext.each(Ext.getCmp("exceptionHand").items.items, function(compsitItem) {
												Ext.each(compsitItem.items.items, function(item) {
													account_info.getForm().remove(item);
												});
											});
											Ext.getCmp("exceptionHand").removeAll();
											taxManager.initSeq();
											// 新添加一行居民国（地区）和纳税人识别号（TIN）输入框
											taxManager.add();
										}
									} else {// 1、仅为中国税收居民
										// 隐藏他国税收信息区域
										Ext.each(Ext.getCmp("exceptionHand").items.items, function(compsitItem) {
											Ext.each(compsitItem.items.items, function(item) {
												account_info.getForm().remove(item);
											});
										});
										Ext.getCmp("exceptionHand").removeAll();
										Ext.getCmp('shengMing').setVisible(false);
										// 是否为美国纳税人设置为非必输项
										Ext.getCmp('isUNtaxpayer').allowBlank = true;
										Ext.getCmp('isUNtaxpayer').reset();
										Ext.getCmp('USTIN').setVisible(false);
										Ext.getCmp('USTIN').allowBlank = true;
									}
									Ext.getCmp('shengMing').doLayout();
									Ext.getCmp('isUNtaxpayer').doLayout();
								}
							}
						}]

					}, {
						layout		: 'column',
						columnWidth	: 1,
						id			: 'shengMing',
						style		: 'margin-left:80px;',
						items		: [{
							layout		: 'form',
							columnWidth	: 1,
							labelWidth	: 100,
							items		: [{
								xtype	: 'tbtext',
								text	: '<br/><b>' + convertFontCss("如您在以上选项中勾选第2项或者第3项，请填写下列信息：") + '</b><br/>'
							}]
						}, {
							columnWidth	: 1,
							layout		: 'hbox',
							items		: [{
								layout		: 'form',
								width		: 240,
								labelWidth	: 140,
								items		: [{
									xtype		: 'radiogroup',
									id			: 'isUNtaxpayer',
									width		: 70,
									fieldLabel	: convertFontCss("1)是否为美国纳税人"),
									_initparam	: false,
									items		: [{
										boxLabel	: '是',
										name		: 'isUNtaxpayer',
										inputValue	: '1',
										id			: 'isPayer',
										listeners	: {
											'disable'	: function() {
												if(this.el) {
													this.el.up("div").removeClass("x-item-disabled");
													this.el.up("div").setStyle({
														color	: "#555",
														cursor	: "default",
														opacity	: 1
													});
												}
											}
										}
									}, {
										boxLabel	: '否',
										name		: 'isUNtaxpayer',
										inputValue	: '2',
										id			: 'isnotPayer',
										listeners	: {
											'disable'	: function() {
												if(this.el) {
													this.el.up("div").removeClass("x-item-disabled");
													this.el.up("div").setStyle({
														color	: "#555",
														cursor	: "default",
														opacity	: 1
													});
												}
											}
										}
									}],
									listeners	: {
										'change'	: function(a, b) {
											if(this._initparam == false) {
												this._initparam = true;
											} else {
												Ext.getCmp('USTIN').setValue("");
											}
											if(b && b.inputValue == '1') {
												Ext.getCmp('USTIN').setVisible(true);
												Ext.getCmp('USTIN').allowBlank = false;
											} else {
												Ext.getCmp('USTIN').setVisible(false);
												Ext.getCmp('USTIN').allowBlank = true;
											}
										}
									}
								}]
							}, {
								layout		: 'form',
								width		: 300,
								labelWidth	: 100,
								items		: [{
									xtype		: 'textfield',
									name		: 'USTIN',
									id			: 'USTIN',
									maxLength	: 32,
									regex		: /^[^\u4E00-\u9FA5]{0,}$/,
									fieldLabel	: convertFontCss("US TIN/SSN")
									// width:'200'
							}	]
							}]
						}, {
							columnWidth	: 1,
							layout		: 'form',
							items		: [{
								layout	: 'hbox',
								items	: [{
									xtype	: 'tbtext',
									style	: 'margin-left:10px;',
									text	: convertFontCss("2)请填写您需履行纳税义务的所有税收居民国（地区）以及所关联的纳税人识别号(TIN)")
								}, {
									xtype		: 'button',
									width		: 30,
									style		: 'margin-left:10px;',
									text		: '增加',
									id			: 'addBtn',
									listeners	: {
										'click'		: function() {
											taxManager.add();
										},
										'disable'	: function() {
											if(this.el) {
												this.el.up("div").removeClass("x-item-disabled");
												this.el.up("div").setStyle({
													color	: "#555",
													cursor	: "default",
													opacity	: 1
												});
											}
										}
									}
								}]
							}]
						}, {
							id			: 'exceptionHand',
							columnWidth	: 1,
							layout		: 'form',
							xtype		: 'panel',
							items		: []
						}, {
							columnWidth	: 1,
							layout		: 'form',
							items		: [{
								xtype	: 'tbtext',
								style	: 'margin-left:10px;',
								text	: '<br/>' + convertFontCss("如您不能提供居民国（地区）纳税人识别号，请选择原因(TIN)") + '<br/>'
							}]
						}, {
							layout		: 'form',
							columnWidth	: 1,
							labelWidth	: 1,
							hideLabel	: true,
							style		: 'margin-left:30px;',
							items		: [{
								xtype		: 'checkbox',
								id			: 'REASON',
								boxLabel	: convertFontCss("居民国（地区）不发放纳税人识别号"),
								listeners	: {
									'check'		: function(obj, ischeck) {
										if(ischeck == true) {
											Ext.getCmp("detailReason").allowBlank = false;
										} else {
											Ext.getCmp("detailReason").allowBlank = true;
										}
									},
									'disable'	: function() {
										if(this.el) {
											this.el.up("div").removeClass("x-item-disabled");
											this.el.up("div").setStyle({
												color	: "#555",
												cursor	: "default",
												opacity	: 1
											});
										}
									}
								}
							}, {
								xtype		: 'checkbox',
								id			: 'REASON2',
								boxLabel	: convertFontCss("账户持有人未能取得纳税人识别号"),
								listeners	: {
									'check'		: function(obj, ischeck) {
										if(ischeck == true) {
											Ext.getCmp("detailReason").allowBlank = false;
										} else {
											Ext.getCmp("detailReason").allowBlank = true;
										}
									},
									'disable'	: function() {
										if(this.el) {
											this.el.up("div").removeClass("x-item-disabled");
											this.el.up("div").setStyle({
												color	: "#555",
												cursor	: "default",
												opacity	: 1
											});
										}
									}
								}
							}]
						}, {
							layout		: 'form',
							columnWidth	: 1,
							items		: [{
								xtype		: 'textarea',
								width		: '500',
								id			: 'detailReason',
								fieldLabel	: convertFontCss("请解释具体原因"),
								maxLength	: 1000
							}]
						}]
					}]
				}, {
					columnWidth	: 1,
					xtype		: 'fieldset',
					id			: 'shenming2',
					title		: '<font style="font-size:16px;">证件2：</font>',
					style		: 'margin:0px 20px 20px 10px;',
					items		: [{
						columnWidth	: 1,
						layout		: 'form',
						items		: [{
							xtype		: 'radiogroup',
							columns		: 1,
							id			: 'radio2',
							allowBlank	: false,
							_inited		: false,// 自定义属性，用来标识是否回显数据，如果是回显则不删除exceptionHand下内容
							anchor		: '90%',
							items		: [{
								boxLabel	: convertFontCss('1、仅为中国税收居民'),
								name		: 'radio2',
								inputValue	: '01',
								listeners	: {
									'disable'	: function() {
										if(this.el) {
											this.el.up("div").removeClass("x-item-disabled");
											this.el.up("div").setStyle({
												color	: "#555",
												cursor	: "default",
												opacity	: 1
											});
										}
									}
								}
							}, {
								boxLabel	: convertFontCss('2、仅为非居民'),
								name		: 'radio2',
								inputValue	: '02',
								listeners	: {
									'disable'	: function() {
										if(this.el) {
											this.el.up("div").removeClass("x-item-disabled");
											this.el.up("div").setStyle({
												color	: "#555",
												cursor	: "default",
												opacity	: 1
											});
										}
									}
								}
							}, {
								boxLabel	: convertFontCss('3、既是中国税收居民又是其他国税收居民'),
								name		: 'radio2',
								inputValue	: '03',
								listeners	: {
									'disable'	: function() {
										if(this.el) {
											this.el.up("div").removeClass("x-item-disabled");
											this.el.up("div").setStyle({
												color	: "#555",
												cursor	: "default",
												opacity	: 1
											});
										}
									}
								}
							}],
							listeners	: {
								'change'	: function(a, b) {
									Ext.getCmp("detailReason2").allowBlank = true;
									Ext.getCmp("REASON3").setValue("");
									Ext.getCmp("REASON4").setValue("");
									Ext.getCmp("detailReason2").setValue("");
									if(b.inputValue == '02' || b.inputValue == '03') {
										Ext.getCmp('shengMing2').setVisible(true);
										Ext.getCmp('isUNtaxpayer2').allowBlank = false;
										if(this._inited == false) {
											this._inited = true;
										} else {
											Ext.getCmp('isUNtaxpayer2').reset();
											Ext.each(Ext.getCmp("exceptionHand2").items.items, function(compsitItem) {
												Ext.each(compsitItem.items.items, function(item) {
													account_info.getForm().remove(item);
												});
											});
											Ext.getCmp("exceptionHand2").removeAll();
											taxManager2.initSeq();
											taxManager2.add();
										}
									} else {
										Ext.each(Ext.getCmp("exceptionHand2").items.items, function(compsitItem) {
											Ext.each(compsitItem.items.items, function(item) {
												account_info.getForm().remove(item);
											});
										});
										Ext.getCmp("exceptionHand2").removeAll();
										Ext.getCmp('shengMing2').setVisible(false);
										Ext.getCmp('isUNtaxpayer2').allowBlank = true;
										Ext.getCmp('isUNtaxpayer2').reset();
										Ext.getCmp('USTIN2').setVisible(false);
										Ext.getCmp('USTIN2').allowBlank = true;
									}
									Ext.getCmp('shengMing2').doLayout();
									Ext.getCmp('isUNtaxpayer2').doLayout();
								}
							}
						}]

					}, {
						layout		: 'column',
						columnWidth	: 1,
						id			: 'shengMing2',
						style		: 'margin-left:80px;',
						items		: [{
							layout		: 'form',
							columnWidth	: 1,
							labelWidth	: 100,
							items		: [{
								xtype	: 'tbtext',
								text	: '<br/><b>' + convertFontCss("如您在以上选项中勾选第2项或者第3项，请填写下列信息：") + '</b><br/>'
							}]
						}, {
							columnWidth	: 1,
							layout		: 'hbox',
							items		: [{
								layout		: 'form',
								width		: 240,
								labelWidth	: 140,
								items		: [{
									xtype		: 'radiogroup',
									id			: 'isUNtaxpayer2',
									fieldLabel	: convertFontCss("1)是否为美国纳税人"),
									width		: 70,
									_initparam	: false,
									items		: [{
										boxLabel	: '是',
										name		: 'isUNtaxpayer2',
										inputValue	: '1',
										id			: 'isPayer2',
										listeners	: {
											'disable'	: function() {
												if(this.el) {
													this.el.up("div").removeClass("x-item-disabled");
													this.el.up("div").setStyle({
														color	: "#555",
														cursor	: "default",
														opacity	: 1
													});
												}
											}
										}
									}, {
										boxLabel	: '否',
										name		: 'isUNtaxpayer2',
										inputValue	: '2',
										id			: 'isnotPayer2',
										listeners	: {
											'disable'	: function() {
												if(this.el) {
													this.el.up("div").removeClass("x-item-disabled");
													this.el.up("div").setStyle({
														color	: "#555",
														cursor	: "default",
														opacity	: 1
													});
												}
											}
										}
									}],
									listeners	: {
										'change'	: function(a, b) {
											if(this._initparam == false) {
												this._initparam = true;
											} else {
												Ext.getCmp('USTIN2').setValue("");
											}
											if(b && b.inputValue == '1') {
												Ext.getCmp('USTIN2').setVisible(true);
												Ext.getCmp('USTIN2').allowBlank = false;
											} else {
												Ext.getCmp('USTIN2').setVisible(false);
												Ext.getCmp('USTIN2').allowBlank = true;
											}
										}
									}
								}]
							}, {
								layout		: 'form',
								width		: 300,
								labelWidth	: 100,
								items		: [{
									xtype		: 'textfield',
									name		: 'USTIN2',
									id			: 'USTIN2',
									maxLength	: 32,
									regex		: /^[^\u4E00-\u9FA5]{0,}$/,
									fieldLabel	: convertFontCss("US TIN/SSN")
									// width:'200'
							}	]
							}]
						}, {
							columnWidth	: 1,
							layout		: 'form',
							items		: [{
								layout	: 'hbox',
								items	: [{
									xtype	: 'tbtext',
									style	: 'margin-left:10px;',
									text	: convertFontCss("2)请填写您需履行纳税义务的所有税收居民国（地区）以及所关联的纳税人识别号(TIN)")
								}, {
									xtype		: 'button',
									width		: 30,
									style		: 'margin-left:10px;',
									text		: '增加',
									id			: 'addBtn2',
									listeners	: {
										'click'		: function() {
											taxManager2.add();
										},
										'disable'	: function() {
											if(this.el) {
												this.el.up("div").removeClass("x-item-disabled");
												this.el.up("div").setStyle({
													color	: "#555",
													cursor	: "default",
													opacity	: 1
												});
											}
										}
									}
								}]
							}]
						}, {
							id			: 'exceptionHand2',
							columnWidth	: 1,
							layout		: 'form',
							xtype		: 'panel',
							items		: []
						}, {
							columnWidth	: 1,
							layout		: 'form',
							items		: [{
								xtype	: 'tbtext',
								text	: '<br/>' + convertFontCss("如您不能提供居民国（地区）纳税人识别号，请选择原因(TIN)") + '<br/>'
							}]
						}, {
							layout		: 'form',
							columnWidth	: 1,
							labelWidth	: 1,
							hideLabel	: true,
							style		: 'margin-left:30px;',
							items		: [{
								xtype		: 'checkbox',
								id			: 'REASON3',
								boxLabel	: convertFontCss("居民国（地区）不发放纳税人识别号"),
								listeners	: {
									'check'		: function(obj, ischeck) {
										if(ischeck == true) {
											Ext.getCmp("detailReason2").allowBlank = false;
										} else {
											Ext.getCmp("detailReason2").allowBlank = true;
										}
									},
									'disable'	: function() {
										if(this.el) {
											this.el.up("div").removeClass("x-item-disabled");
											this.el.up("div").setStyle({
												color	: "#555",
												cursor	: "default",
												opacity	: 1
											});
										}
									}
								}
							}, {
								xtype		: 'checkbox',
								id			: 'REASON4',
								boxLabel	: convertFontCss("账户持有人未能取得纳税人识别号"),
								listeners	: {
									'check'		: function(obj, ischeck) {
										if(ischeck == true) {
											Ext.getCmp("detailReason2").allowBlank = false;
										} else {
											Ext.getCmp("detailReason2").allowBlank = true;
										}
									},
									'disable'	: function() {
										if(this.el) {
											this.el.up("div").removeClass("x-item-disabled");
											this.el.up("div").setStyle({
												color	: "#555",
												cursor	: "default",
												opacity	: 1
											});
										}
									}
								}
							}]
						}, {
							layout		: 'form',
							columnWidth	: 1,
							items		: [{
								xtype		: 'textarea',
								width		: '500',
								id			: 'detailReason2',
								fieldLabel	: convertFontCss("请解释具体原因"),
								maxLength	: 1000
							}]
						}]
					}]
				}]
			}]
		}]
	}]
});

// ////////////////////////////////////第一页结束///////////////////////////////////
/**
 * 账号信息面板
 */
var accountInfo = new Ext.FormPanel({ 
   baseCls:"x-plain",
   layout:"column",
   id : 'accountInfoPanel',
   name : 'accountInfoPanel',
   frame:true,
   items:[{
	   layout:"table",
	   header:false,
	   width:950,
	   defaultType:"checkbox",
	   layoutConfig:{columns:2}// 将父容器分成2列
   }]
});

/**
 * 服务信息面板
 */
var serviceInfo = new Ext.FormPanel({
	baseCls	   : "x-plain",
	style	   : 'margin:0 10px;',
	layout	   : "column",
	id	       : 'serviceInfoPanel',
	name	   : 'serviceInfoPanel',
	frame	   : true,
	autoScroll	: true,// 有滚动条
	items	   : [{
		layout	: 'column',
		xtype	: 'fieldset',
		id		: 'jiejikaFieldset',
		border	: false,
		style	: 'margin:10px 20px 0px 10px;',
		width	: 950,
		items	: [{
			layout			: "table",
			anchor			: '98%',
			defaultType		: "checkbox",
			layoutConfig	: {
				columns	: 2,
				padding	: 10
			},// 将父容器分成2列
			items			: [{
				width		: 150,
				boxLabel	: convertFontCss("借记卡申请"),
				inputValue	: '1',
				rowspan		: 7,
				name		: 'jiejika',
				id			: 'jiejika',
				listeners	: {
					afterrender	: function() {
						this.el.up("div").setStyle("margin", "5px 0px");
					},
					'disable'	: function() {
						if(this.el) {
							this.el.up("div").removeClass("x-item-disabled");
							this.el.up("div").setStyle({
								color	: "#555",
								cursor	: "default",
								opacity	: 1
							});
						}
					},
					'check'		: function(obj, isChecked) {
						if(isChecked == true) {
							if(this.el && this.el.up("fieldset")) {
								this.el.up("fieldset").setStyle({
									'border'	: '1px solid #b5b8c8'
								});
							}
							Ext.getCmp("cardType").setVisible(true);
							Ext.getCmp("cardType").setValue("");
							Ext.getCmp("cardType").allowBlank = false;
							Ext.getCmp("cardType1_0").setVisible(true);
							Ext.getCmp("cardType1_0").setValue("");
							Ext.getCmp("cardType1_0").allowBlank = false;
							Ext.getCmp("cardType2_0").setVisible(true);
							Ext.getCmp("cardType2_0").setValue("");
							Ext.getCmp("cardType2_0").allowBlank = false;
							Ext.getCmp('ATM').setVisible(true);
							Ext.getCmp('ATMDayLimitPanel').setVisible(true);
							Ext.getCmp('ATMDayCountPanel').setVisible(true);
							Ext.getCmp('ATMYearLimitPanel').setVisible(true);
							Ext.getCmp("ATMDayLimit").setDisabled(true);
							Ext.getCmp('ATMDayLimitCount').setDisabled(true);
							Ext.getCmp('ATMYearLimit').setDisabled(true);
							Ext.getCmp('eachCustemLimit').setDisabled(true);

							Ext.getCmp('POS').setVisible(true);
							Ext.getCmp('POSPanel').setVisible(true);

						} else if(isChecked == false) {
							if(this.el && this.el.up("fieldset")) {
								this.el.up("fieldset").setStyle({
									'border'	: 'none'
								});
							}

							Ext.getCmp("cardType").setVisible(false);
							Ext.getCmp("cardType").allowBlank = true;
							Ext.getCmp("cardType").setValue("");
							Ext.getCmp("cardType1_0").setVisible(false);
							Ext.getCmp("cardType1_0").allowBlank = true;
							Ext.getCmp("cardType1_0").setValue("");
							Ext.getCmp("cardType2_0").setVisible(false);
							Ext.getCmp("cardType2_0").allowBlank = true;
							Ext.getCmp("cardType2_0").setValue("");
							Ext.getCmp('ATM').setVisible(false);
							Ext.getCmp('ATMDayLimitDefault').setValue(true);
							Ext.getCmp('ATMDayLimitDefine').setValue(false);
							Ext.getCmp('ATMDayLimit').setValue("");
							Ext.getCmp('ATMDayLimitPanel').setVisible(false);

							Ext.getCmp('ATMDayCountDefault').setValue(true);
							Ext.getCmp('ATMDayCountDefine').setValue(false);
							Ext.getCmp('ATMDayLimitCount').setValue("");
							Ext.getCmp('ATMDayCountPanel').setVisible(false);

							Ext.getCmp('ATMYearLimitDefault').setValue(true);
							Ext.getCmp('ATMYearLimitDefine').setValue(false);
							Ext.getCmp('ATMYearLimit').setValue("");
							Ext.getCmp('ATMYearLimitPanel').setVisible(false);
							Ext.getCmp('POS').setVisible(false);

							Ext.getCmp('POSDefault').setValue(true);
							Ext.getCmp('POSDefine').setValue(false);
							Ext.getCmp('eachCustemLimit').setValue("");
							Ext.getCmp('POSPanel').setVisible(false);

						}
					}
				}
			}, {
				xtype	: 'panel',
				layout	: 'column',
				id		: 'cardTypePanel',
				name	: 'cardTypePanel',
				width	: 800,
				items	: [{
					columnWidth	: .3,
					layout		: 'form',
					labelWidth	: 1,
					width		: 230,
					items		: [{
						xtype				: 'combo',
						hiddenName			: 'cardType',
						id					: 'cardType',
						emptyText			: '请选择',
						width				: 200,
						editable			: false,
						store				: cardTypeStore,
						hidden				: true,
						anchor				: "80%",
						valueField			: 'key',
						displayField		: 'value',
						mode				: 'local',
						valueNotFoundText	: "",
						triggerAction		: 'all',
						listeners			: {
							'beforeselect'	: function(combo, record, index) {
								this.oldValue = this.getValue();
							},
							'select'		: function(combo, record, index) {
								if(this.oldValue == this.value) {
									return false;
								}
								cardType1Store.load({
									params	: {
										name	: this.value
									}
								});
								cardType2Store.removeAll();
								Ext.getCmp("cardType1_0").setValue("");
								Ext.getCmp("cardType2_0").setValue("");

							},
							'focus'			: {
								fn		: function(e) {
									e.expand();
									this.doQuery(this.allQuery, true);
								},
								buffer	: 200
							}
						}
					}]
				}, {
					columnWidth	: .3,
					layout		: 'form',
					labelWidth	: 1,
					width		: 230,
					items		: [{
						xtype				: 'combo',
						hiddenName			: 'cardType1_0',
						id					: 'cardType1_0',
						width				: 200,
						emptyText			: '请选择',
						editable			: false,
						hidden				: true,
						store				: cardType1Store,
						anchor				: "80%",
						valueField			: 'key',
						displayField		: 'value',
						mode				: 'local',
						valueNotFoundText	: "",
						triggerAction		: 'all',
						listeners			: {
							'beforeselect'	: function(combo, record, index) {
								this.oldValue = this.getValue();
							},
							'select'		: function(combo, record, index) {
								if(this.oldValue == this.value) {
									return false;
								}
								cardType2Store.load({
									params	: {
										name	: this.value
									}
								});

								Ext.getCmp("cardType2_0").setValue("");
							},
							'focus'			: {
								fn		: function(e) {
									e.expand();
									this.doQuery(this.allQuery, true);
								},
								buffer	: 200
							}
						}
					}]
				}, {
					columnWidth	: .3,
					layout		: 'form',
					labelWidth	: 1,
					width		: 230,
					items		: [{
						xtype				: 'combo',
						hiddenName			: 'cardType2_0',
						id					: 'cardType2_0',
						emptyText			: '请选择',
						editable			: false,
						hidden				: true,
						width				: 200,
						store				: cardType2Store,
						anchor				: "80%",
						valueField			: 'key',
						displayField		: 'value',
						mode				: 'local',
						valueNotFoundText	: "",
						triggerAction		: 'all',
						listeners			: {
							'focus'	: {
								fn		: function(e) {
									e.expand();
									this.doQuery(this.allQuery, true);
								},
								buffer	: 200
							}
						}
					}]
				}]
			}, {
				xtype	: 'tbtext',
				width	: 1000,
				id		: 'ATM',
				name	: 'ATM',
				hidden	: true,
				text	: '<br/><b><font style="font-size:14px;">ATM转账限额设置</font></b><br/>'
			}, {
				xtype	: 'panel',
				layout	: 'column',
				id		: 'ATMDayLimitPanel',
				hidden	: true,
				name	: 'ATMDayLimitPanel',
				items	: [{
					columnWidth	: .35,
					layout		: 'form',
					labelWidth	: 1,
					items		: [{
						anchor		: "90%",
						id			: 'ATMDayLimitDefault',
						name		: 'ATMDayLimitDefault',
						xtype		: 'radio',
						boxLabel	: convertFontCss("默认每日累计限额（RMB50,000元）"),
						inputValue	: 1,
						checked		: true,
						listeners	: {
							'check'		: function(obj, isChecked) {
								if(isChecked == true) {
									Ext.getCmp("ATMDayLimit").reset();
									Ext.getCmp("ATMDayLimit").setDisabled(true);
								}
							},
							'disable'	: function() {
								if(this.el) {
									this.el.up("div").removeClass("x-item-disabled");
									this.el.up("div").setStyle({
										color	: "#555",
										cursor	: "default",
										opacity	: 1
									});
								}
							}
						}
					}]
				}, {
					columnWidth	: .22,
					layout		: 'form',
					labelWidth	: 1,
					items		: [{
						anchor		: "98%",
						xtype		: 'radio',
						id			: 'ATMDayLimitDefine',
						name		: 'ATMDayLimitDefault',
						boxLabel	: convertFontCss("每日累计转账最高限额RMB"),
						inputValue	: 0,
						listeners	: {
							'check'		: function(obj, isChecked) {
								if(isChecked == true) {
									Ext.getCmp("ATMDayLimit").allowBlank = false;
									Ext.getCmp("ATMDayLimit").setDisabled(false);
									Ext.getCmp("ATMDayLimit").focus();
								}
							},
							'disable'	: function() {
								if(this.el) {
									this.el.up("div").removeClass("x-item-disabled");
									this.el.up("div").setStyle({
										color	: "#555",
										cursor	: "default",
										opacity	: 1
									});
								}
							}
						}
					}]
				}, {
					columnWidth	: .1,
					layout		: 'form',
					labelWidth	: 1,
					items		: [{
						anchor		: "98%",
						xtype		: 'numberfield',
						width		: '50',
						id			: 'ATMDayLimit',
						name		: 'ATMDayLimit',
						maxValue	: 50000
					}]
				}, {
					columnWidth	: .2,
					layout		: 'form',
					labelWidth	: 1,
					items		: [{
						anchor	: "90%",
						xtype	: 'tbtext',
						style	: "font-size:14px;",
						text	: "<br>元（0-50,000）</br>"
					}]
				}]
			}, {
				xtype	: 'panel',
				layout	: 'column',
				id		: 'ATMDayCountPanel',
				name	: 'ATMDayCountPanel',
				hidden	: true,
				items	: [{
					columnWidth	: .35,
					layout		: 'form',
					labelWidth	: 1,
					items		: [{
						anchor		: "90%",
						xtype		: 'radio',
						boxLabel	: convertFontCss("默认每日累计笔数（10笔）"),
						id			: 'ATMDayCountDefault',
						name		: 'ATMDayCountDefault',
						checked		: true,
						inputValue	: 1,
						listeners	: {
							'check'		: function(obj, isChecked) {
								if(isChecked == true) {
									Ext.getCmp("ATMDayLimitCount").reset();
									Ext.getCmp("ATMDayLimitCount").setDisabled(true);
								}
							},
							'disable'	: function() {
								if(this.el) {
									this.el.up("div").removeClass("x-item-disabled");
									this.el.up("div").setStyle({
										color	: "#555",
										cursor	: "default",
										opacity	: 1
									});
								}
							}
						}

					}]
				}, {
					columnWidth	: .22,
					layout		: 'form',
					labelWidth	: 1,
					items		: [{
						anchor		: "98%",
						xtype		: 'radio',
						boxLabel	: convertFontCss("每日累计转账笔数"),
						id			: 'ATMDayCountDefine',
						name		: 'ATMDayCountDefault',
						inputValue	: 0,
						listeners	: {
							'check'		: function(obj, isChecked) {
								if(isChecked == true) {
									Ext.getCmp("ATMDayLimitCount").allowBlank = false;
									Ext.getCmp("ATMDayLimitCount").setDisabled(false);
									Ext.getCmp("ATMDayLimitCount").focus();
								}
							},
							'disable'	: function() {
								if(this.el) {
									this.el.up("div").removeClass("x-item-disabled");
									this.el.up("div").setStyle({
										color	: "#555",
										cursor	: "default",
										opacity	: 1
									});
								}
							}
						}
					}]
				}, {
					columnWidth	: .1,
					layout		: 'form',
					labelWidth	: 1,
					items		: [{
						anchor	: "98%",
						xtype	: 'numberfield',
						width	: '50',
						id		: 'ATMDayLimitCount',
						name	: 'ATMDayLimitCount'
					}]
				}, {
					columnWidth	: .2,
					layout		: 'form',
					labelWidth	: 1,
					items		: [{
						anchor	: "90%",
						xtype	: 'tbtext',
						text	: "<br>笔</br>",
						style	: "font-size:14px;"
					}]
				}]
			}, {
				xtype	: 'panel',
				layout	: 'column',
				id		: 'ATMYearLimitPanel',
				name	: 'ATMYearLimitPanel',
				hidden	: true,
				items	: [{
					columnWidth	: .35,
					layout		: 'form',
					labelWidth	: 1,
					items		: [{
						anchor		: "90%",
						xtype		: 'radio',
						boxLabel	: convertFontCss("默认每年累计限额（RMB10,000,000元）"),
						id			: 'ATMYearLimitDefault',
						name		: 'ATMYearLimitDefault',
						checked		: true,
						inputValue	: 1,
						listeners	: {
							'check'		: function(obj, isChecked) {
								if(isChecked == true) {
									Ext.getCmp("ATMYearLimit").reset();
									Ext.getCmp("ATMYearLimit").setDisabled(true);
								}
							},
							'disable'	: function() {
								if(this.el) {
									this.el.up("div").removeClass("x-item-disabled");
									this.el.up("div").setStyle({
										color	: "#555",
										cursor	: "default",
										opacity	: 1
									});
								}
							}
						}

					}]
				}, {
					columnWidth	: .22,
					layout		: 'form',
					labelWidth	: 1,
					items		: [{
						anchor		: "98%",
						xtype		: 'radio',
						boxLabel	: convertFontCss("每年累计转账最高限额RMB"),
						id			: 'ATMYearLimitDefine',
						name		: 'ATMYearLimitDefault',
						inputValue	: 0,
						listeners	: {
							'check'		: function(obj, isChecked) {
								if(isChecked == true) {
									Ext.getCmp("ATMYearLimit").allowBlank = false;
									Ext.getCmp("ATMYearLimit").setDisabled(false);
									Ext.getCmp("ATMYearLimit").focus();
								}
							},
							'disable'	: function() {
								if(this.el) {
									this.el.up("div").removeClass("x-item-disabled");
									this.el.up("div").setStyle({
										color	: "#555",
										cursor	: "default",
										opacity	: 1
									});
								}
							}
						}
					}]
				}, {
					columnWidth	: .1,
					layout		: 'form',
					labelWidth	: 1,
					items		: [{
						anchor	: "98%",
						xtype	: 'numberfield',
						width	: '50',
						id		: 'ATMYearLimit',
						name	: 'ATMYearLimit'
					}]
				}, {
					columnWidth	: .2,
					layout		: 'form',
					labelWidth	: 1,
					items		: [{
						anchor	: "90%",
						xtype	: 'tbtext',
						text	: "<br>元</br>",
						style	: "font-size:14px;"
					}]

				}]
			}, {
				xtype	: 'tbtext',
				width	: 1000,
				id		: 'POS',
				name	: 'POS',
				hidden	: true,
				text	: "<b><font style='font-size:14px;'>POS消费限额设置</font></b>"
			}, {
				xtype	: 'panel',
				layout	: 'column',
				id		: 'POSPanel',
				name	: 'POSPanel',
				hidden	: true,
				items	: [{
					columnWidth	: .35,
					layout		: 'form',
					labelWidth	: 1,
					items		: [{
						anchor		: "90%",
						xtype		: 'radio',
						checked		: true,
						boxLabel	: convertFontCss("默认单笔限额（RMB500,000元）"),
						id			: 'POSDefault',
						name		: 'POSDefault',
						inputValue	: 1,
						listeners	: {
							'check'		: function(obj, isChecked) {
								if(isChecked == true) {
									Ext.getCmp("eachCustemLimit").reset();
									Ext.getCmp("eachCustemLimit").setDisabled(true);
								}
							},
							'disable'	: function() {
								if(this.el) {
									this.el.up("div").removeClass("x-item-disabled");
									this.el.up("div").setStyle({
										color	: "#555",
										cursor	: "default",
										opacity	: 1
									});
								}
							}
						}
					}]
				}, {
					columnWidth	: .22,
					layout		: 'form',
					labelWidth	: 1,
					items		: [{
						anchor		: "98%",
						xtype		: 'radio',
						boxLabel	: convertFontCss("单笔消费限额RMB"),
						id			: 'POSDefine',
						name		: 'POSDefault',
						inputValue	: 0,
						listeners	: {
							'check'		: function(obj, isChecked) {
								if(isChecked == true) {
									Ext.getCmp("eachCustemLimit").allowBlank = false;
									Ext.getCmp("eachCustemLimit").setDisabled(false);
									Ext.getCmp("eachCustemLimit").focus();
								}
							},
							'disable'	: function() {
								if(this.el) {
									this.el.up("div").removeClass("x-item-disabled");
									this.el.up("div").setStyle({
										color	: "#555",
										cursor	: "default",
										opacity	: 1
									});
								}
							}
						}
					}]
				}, {
					columnWidth	: .1,
					layout		: 'form',
					labelWidth	: 1,
					items		: [{
						anchor	: "98%",
						xtype	: 'numberfield',
						width	: '50',
						id		: 'eachCustemLimit',
						name	: 'eachCustemLimit'
					}]
				}, {
					columnWidth	: .2,
					layout		: 'form',
					labelWidth	: 1,
					items		: [{
						anchor	: "90%",
						xtype	: 'tbtext',
						text	: "<br>元</br>",
						style	: "font-size:14px;"
					}]
				}]
			}]
		}]

	}, {
		layout	: 'column',
		xtype	: 'fieldset',
		border	: false,
		id		: 'dianziBankFieldset',
		style	: 'margin:0px 20px 0px 10px;',
		width	: 950,
		items	: [{
			layout			: "table",
			anchor			: '98%',
			defaultType		: "checkbox",
			layoutConfig	: {
				columns	: 2,
				padding	: 10
			},// 将父容器分成2列
			items			: [{
				width		: 150,
				boxLabel	: convertFontCss("电子银行服务"),
				inputValue	: '1',
				rowspan		: 8,
				id			: 'dianziBank',
				name		: 'dianziBank',
				// hidden:true,
				listeners	: {
					afterrender	: function() {
						this.el.up("div").setStyle("margin", "5px 0px");
					},
					'disable'	: function() {
						if(this.el) {
							this.el.up("div").removeClass("x-item-disabled");
							this.el.up("div").setStyle({
								color	: "#555",
								cursor	: "default",
								opacity	: 1
							});
						}
					},
					'check'		: function(obj, isChecked) {
						if(isChecked == true) {
							if(this.el && this.el.up("fieldset")) {
								this.el.up("fieldset").setStyle({
									'border'	: '1px solid #b5b8c8'
								});
							}
							Ext.getCmp("EMAIL").allowBlank = false;
							if(!Ext.getCmp("EMAIL").isValid()) {
								Ext.Msg.alert('错误提示', '校验失败，开通电子银行服务时，必须填写电子邮箱Email！');
							}
							Ext.getCmp("netBank").setVisible(true);
							Ext.getCmp("mobileBank").setVisible(true);
							Ext.getCmp("dayAccLimitPanel").setVisible(true);
							Ext.getCmp("dayAccCountPanel").setVisible(true);
							Ext.getCmp("yearAccLimitPanel").setVisible(true);
							Ext.getCmp("dayAccSelfDefine").setDisabled(true);
							Ext.getCmp("dayCountSelfDefine").setDisabled(true);
							Ext.getCmp("yearAccSelfDefine").setDisabled(true);

						} else if(isChecked == false) {
							if(this.el && this.el.up("fieldset")) {
								this.el.up("fieldset").setStyle({
									'border'	: 'none'
								});
							}
							Ext.getCmp("EMAIL").allowBlank = true;
							Ext.getCmp("netBank").setVisible(false);
							Ext.getCmp("netBank").setValue(false);
							Ext.getCmp("ukeyPanel").setVisible(false);
							Ext.getCmp("shortMessagePanel").setVisible(false);
							Ext.getCmp("mobileBank").setVisible(false);
							Ext.getCmp("mobileBank").setValue(false);
							Ext.getCmp("shortMessage2Panel").setVisible(false);

							Ext.getCmp("dayAccLimitDefault").setValue(true);
							Ext.getCmp("dayAccLimitDefine").setValue(false);
							Ext.getCmp("dayAccSelfDefine").setValue("");
							Ext.getCmp("dayAccLimitPanel").setVisible(false);

							Ext.getCmp("dayAccCountDefault").setValue(true);
							Ext.getCmp("dayAccCountDefine").setValue(false);
							Ext.getCmp("dayCountSelfDefine").setValue("");
							Ext.getCmp("dayAccCountPanel").setVisible(false);

							Ext.getCmp("yearAccLimitDefault").setValue(true);
							Ext.getCmp("yearAccLimitDefine").setValue(false);
							Ext.getCmp("yearAccSelfDefine").setValue("");
							Ext.getCmp("yearAccLimitPanel").setVisible(false);
						}
					}
				}
			}, {
				anchor		: "98%",
				xtype		: 'checkbox',
				style		: 'padding-left:0px',
				name		: 'netBank',
				hidden		: true,
				id			: 'netBank',
				width		: 800,
				boxLabel	: convertFontCss("网络银行（若不选汇款认证方式，则默认只有查询功能）"),
				inputValue	: '1',
				listeners	: {
					'check'		: function(obj, isChecked) {
						if(isChecked == true) {
							Ext.getCmp("ukeyPanel").setVisible(true);
							Ext.getCmp("shortMessagePanel").setVisible(true);
						} else if(isChecked == false) {
							Ext.getCmp("ukey").setValue(false);
							Ext.getCmp("shortMessage").setValue(false);
							Ext.getCmp("ukeyPanel").setVisible(false);
							Ext.getCmp("shortMessagePanel").setVisible(false);
						}
					},
					'disable'	: function() {
						if(this.el) {
							this.el.up("div").removeClass("x-item-disabled");
							this.el.up("div").setStyle({
								color	: "#555",
								cursor	: "default",
								opacity	: 1
							});
						}
					}
				}
			}, {
				xtype	: 'panel',
				id		: 'ukeyPanel',
				name	: 'ukeyPanel',
				layout	: 'column',
				hidden	: true,
				items	: [{
					columnWidth	: 1,
					layout		: 'form',
					labelWidth	: 1,
					style		: 'padding-left:30px',
					items		: [{
						xtype		: 'checkbox',
						id			: 'ukey',
						name		: 'ukey',
						anchor		: "50%",
						boxLabel	: convertFontCss("U-key汇款认证"),
						inputValue	: '1',
						listeners	: {
							'disable'	: function() {
								if(this.el) {
									this.el.up("div").removeClass("x-item-disabled");
									this.el.up("div").setStyle({
										color	: "#555",
										cursor	: "default",
										opacity	: 1
									});
								}
							}
						}
					}]
				}]
			}, {
				xtype	: 'panel',
				layout	: 'column',
				id		: 'shortMessagePanel',
				name	: 'shortMessagePanel',
				hidden	: true,
				items	: [{
					columnWidth	: 1,
					layout		: 'form',
					style		: 'padding-left:30px',
					labelWidth	: 1,
					items		: [{
						xtype		: 'checkbox',
						id			: 'shortMessage',
						name		: 'shortMessage',
						anchor		: "50%",
						boxLabel	: convertFontCss("短信认证"),
						inputValue	: '1',
						listeners	: {
							'disable'	: function() {
								if(this.el) {
									this.el.up("div").removeClass("x-item-disabled");
									this.el.up("div").setStyle({
										color	: "#555",
										cursor	: "default",
										opacity	: 1
									});
								}
							}
						}
					}]
				}]
			}, {
				anchor		: "98%",
				xtype		: 'checkbox',
				id			: 'mobileBank',
				name		: 'mobileBank',
				hidden		: true,
				boxLabel	: convertFontCss("手机银行"),
				inputValue	: '1',
				listeners	: {
					'check'		: function(obj, isChecked) {
						if(isChecked == true) {
							Ext.getCmp("shortMessage2Panel").setVisible(true);
							Ext.getCmp("shortMessage2").setValue(true);
						} else if(isChecked == false) {
							Ext.getCmp("shortMessage2").setValue(false);
							Ext.getCmp("shortMessage2Panel").setVisible(false);
						}
					},
					'disable'	: function() {
						if(this.el) {
							this.el.up("div").removeClass("x-item-disabled");
							this.el.up("div").setStyle({
								color	: "#555",
								cursor	: "default",
								opacity	: 1
							});
						}
					}
				}
			}, {
				xtype	: 'panel',
				layout	: 'column',
				id		: 'shortMessage2Panel',
				name	: 'shortMessage2Panel',
				hidden	: true,
				items	: [{
					columnWidth	: 1,
					layout		: 'form',
					labelWidth	: 1,
					style		: 'padding-left:30px',
					items		: [{
						anchor		: "50%",
						xtype		: 'checkbox',
						id			: 'shortMessage2',
						name		: 'shortMessage2',
						checked		: true,
						boxLabel	: convertFontCss("短信验证"),
						inputValue	: '1',
						listeners	: {
							'disable'	: function() {
								if(this.el) {
									this.el.up("div").removeClass("x-item-disabled");
									this.el.up("div").setStyle({
										color	: "#555",
										cursor	: "default",
										opacity	: 1
									});
								}
							}
						}
					}]
				}]
			}, {
				xtype	: 'panel',
				layout	: 'column',
				id		: 'dayAccLimitPanel',
				name	: 'dayAccLimitPanel',
				hidden	: true,
				items	: [{
					columnWidth	: .18,
					layout		: 'form',
					labelWidth	: 1,
					items		: [{
						xtype	: 'tbtext',
						width	: 800,
						height	: 37.81,
						style	: 'padding-top:5px',
						text	: convertFontCss("日累计转账限额")
					}]

				}, {
					columnWidth	: .2,
					layout		: 'form',
					labelWidth	: 1,
					items		: [{
						anchor		: "90%",
						xtype		: 'radio',
						boxLabel	: convertFontCss("默认无限制"),
						id			: 'dayAccLimitDefault',
						name		: 'dayAccLimitDefault',
						checked		: true,
						inputValue	: 1,
						listeners	: {
							'check'		: function(obj, isChecked) {
								if(isChecked == true) {
									Ext.getCmp("dayAccSelfDefine").reset();
									Ext.getCmp("dayAccSelfDefine").setDisabled(true);
								}
							},
							'disable'	: function() {
								if(this.el) {
									this.el.up("div").removeClass("x-item-disabled");
									this.el.up("div").setStyle({
										color	: "#555",
										cursor	: "default",
										opacity	: 1
									});
								}
							}
						}
					}]
				}, {
					columnWidth	: .1,
					layout		: 'form',
					labelWidth	: 1,
					items		: [{
						anchor		: "98%",
						xtype		: 'radio',
						boxLabel	: convertFontCss("自定义"),
						inputValue	: 0,
						id			: 'dayAccLimitDefine',
						name		: 'dayAccLimitDefault',
						listeners	: {
							'check'		: function(obj, isChecked) {
								if(isChecked == true) {
									Ext.getCmp("dayAccSelfDefine").allowBlank = false;
									Ext.getCmp("dayAccSelfDefine").setDisabled(false);
									Ext.getCmp("dayAccSelfDefine").focus();
								}
							},
							'disable'	: function() {
								if(this.el) {
									this.el.up("div").removeClass("x-item-disabled");
									this.el.up("div").setStyle({
										color	: "#555",
										cursor	: "default",
										opacity	: 1
									});
								}
							}
						}
					}]
				}, {
					columnWidth	: .1,
					layout		: 'form',
					labelWidth	: 1,
					items		: [{
						anchor	: "98%",
						xtype	: 'numberfield',
						width	: '50',
						id		: 'dayAccSelfDefine',
						name	: 'dayAccSelfDefine'
					}]
				}, {
					columnWidth	: .1,
					layout		: 'form',
					labelWidth	: 1,
					items		: [{
						anchor	: "90%",
						xtype	: 'tbtext',
						text	: "<br>元</br>",
						style	: "font-size:14px;"
					}]
				}]
			}, {
				xtype	: 'panel',
				layout	: 'column',
				id		: 'dayAccCountPanel',
				name	: 'dayAccCountPanel',
				hidden	: true,
				items	: [{
					columnWidth	: 0.18,
					layout		: 'form',
					labelWidth	: 1,
					items		: [{
						xtype	: 'tbtext',
						width	: 800,
						style	: 'padding-top:5px',
						text	: convertFontCss("日累计转账笔数")
					}]

				}, {
					columnWidth	: 0.2,
					layout		: 'form',
					labelWidth	: 1,
					items		: [{
						anchor		: "90%",
						xtype		: 'radio',
						boxLabel	: convertFontCss("默认200笔"),
						id			: 'dayAccCountDefault',
						name		: 'dayAccCountDefault',
						checked		: true,
						inputValue	: 1,
						listeners	: {
							'check'		: function(obj, isChecked) {
								if(isChecked == true) {
									Ext.getCmp("dayCountSelfDefine").reset();
									Ext.getCmp("dayCountSelfDefine").setDisabled(true);
								}
							},
							'disable'	: function() {
								if(this.el) {
									this.el.up("div").removeClass("x-item-disabled");
									this.el.up("div").setStyle({
										color	: "#555",
										cursor	: "default",
										opacity	: 1
									});
								}
							}
						}
					}]
				}, {
					columnWidth	: 0.1,
					layout		: 'form',
					labelWidth	: 1,
					items		: [{
						anchor		: "98%",
						xtype		: 'radio',
						boxLabel	: convertFontCss("自定义"),
						id			: 'dayAccCountDefine',
						name		: 'dayAccCountDefault',
						inputValue	: 0,
						listeners	: {
							'check'		: function(obj, isChecked) {
								if(isChecked == true) {
									Ext.getCmp("dayCountSelfDefine").allowBlank = false;
									Ext.getCmp("dayCountSelfDefine").setDisabled(false);
									Ext.getCmp("dayCountSelfDefine").focus();
								}
							},
							'disable'	: function() {
								if(this.el) {
									this.el.up("div").removeClass("x-item-disabled");
									this.el.up("div").setStyle({
										color	: "#555",
										cursor	: "default",
										opacity	: 1
									});
								}
							}
						}
					}]
				}, {
					columnWidth	: .1,
					layout		: 'form',
					labelWidth	: 1,
					items		: [{
						anchor			: "98%",
						xtype			: 'numberfield',
						width			: '50',
						id				: 'dayCountSelfDefine',
						name			: 'dayCountSelfDefine',
						titleCollapse	: true,
						collapsible		: true
					}]
				}, {
					columnWidth	: .1,
					layout		: 'form',
					labelWidth	: 1,
					items		: [{
						anchor	: "90%",
						xtype	: 'tbtext',
						text	: "<br>笔</br>",
						style	: "font-size:14px;"
					}]
				}]
			}, {
				xtype	: 'panel',
				layout	: 'column',
				id		: 'yearAccLimitPanel',
				name	: 'yearAccLimitPanel',
				hidden	: true,
				items	: [{
					columnWidth	: .18,
					layout		: 'form',
					labelWidth	: 1,
					items		: [{
						xtype	: 'tbtext',
						width	: 800,
						style	: 'padding-top:5px',
						text	: convertFontCss("年累计转账限额")
					}]
				}, {
					columnWidth	: .20,
					layout		: 'form',
					labelWidth	: 1,
					items		: [{
						anchor		: "90%",
						xtype		: 'radio',
						boxLabel	: convertFontCss("默认无限制"),
						id			: 'yearAccLimitDefault',
						name		: 'yearAccLimitDefault',
						checked		: true,
						inputValue	: 1,
						listeners	: {
							'check'		: function(obj, isChecked) {
								if(isChecked == true) {
									Ext.getCmp("yearAccSelfDefine").reset();
									Ext.getCmp("yearAccSelfDefine").setDisabled(true);
								}
							},
							'disable'	: function() {
								if(this.el) {
									this.el.up("div").removeClass("x-item-disabled");
									this.el.up("div").setStyle({
										color	: "#555",
										cursor	: "default",
										opacity	: 1
									});
								}
							}
						}
					}]
				}, {
					columnWidth	: .1,
					layout		: 'form',
					labelWidth	: 1,
					items		: [{
						anchor		: "98%",
						xtype		: 'radio',
						boxLabel	: convertFontCss("自定义"),
						inputValue	: 0,
						id			: 'yearAccLimitDefine',
						name		: 'yearAccLimitDefault',
						listeners	: {
							'check'		: function(obj, isChecked) {
								if(isChecked == true) {
									Ext.getCmp("yearAccSelfDefine").allowBlank = false;
									Ext.getCmp("yearAccSelfDefine").setDisabled(false);
									Ext.getCmp("yearAccSelfDefine").focus();
								}
							},
							'disable'	: function() {
								if(this.el) {
									this.el.up("div").removeClass("x-item-disabled");
									this.el.up("div").setStyle({
										color	: "#555",
										cursor	: "default",
										opacity	: 1
									});
								}
							}
						}
					}]
				}, {
					columnWidth	: .1,
					layout		: 'form',
					labelWidth	: 1,
					items		: [{
						anchor			: "98%",
						xtype			: 'numberfield',
						width			: '50',
						titleCollapse	: true,
						id				: 'yearAccSelfDefine',
						name			: 'yearAccSelfDefine',
						collapsible		: true
					}]
				}, {
					columnWidth	: .1,
					layout		: 'form',
					labelWidth	: 1,
					items		: [{
						anchor	: "90%",
						xtype	: 'tbtext',
						text	: "<br>元</br>",
						style	: "font-size:14px;"
					}]
				}]
			}]
		}]
	}, {
		layout	: 'column',
		style	: 'margin:0px 20px 0px 10px;',
		width	: 950,
		items	: [{
			layout			: "table",
			anchor			: '98%',
			defaultType		: "checkbox",
			layoutConfig	: {
				columns	: 2,
				padding	: 10
			},// 将父容器分成2列
			items			: [{
				anchor		: "90%",
				xtype		: 'checkbox',
				boxLabel	: convertFontCss("电子对账单"),
				id			: 'elecState',
				name		: 'elecState',
				inputValue	: '1',
				width		: 150,
				listeners	: {
					afterrender	: function() {
						this.el.up("div").setStyle("margin", "5px 0px");
					},
					'check'		: function(obj, isChecked) {
						if(isChecked == true) {
							Ext.getCmp('elecStatePanel').setVisible(true);
							Ext.getCmp('email').allowBlank = false;

						} else if(isChecked == false) {
							Ext.getCmp('email').setValue("");
							Ext.getCmp('email').allowBlank = true;
							Ext.getCmp('elecStatePanel').setVisible(false);

						}
					},
					'disable'	: function() {
						if(this.el) {
							this.el.up("div").removeClass("x-item-disabled");
							this.el.up("div").setStyle({
								color	: "#555",
								cursor	: "default",
								opacity	: 1
							});
						}
					}
				}

			}, {
				xtype	: 'panel',
				layout	: 'column',
				id		: 'elecStatePanel',
				name	: 'elecStatePanel',
				hidden	: true,
				colspan	: 2,
				width	: 800,
				items	: [{
					columnWidth	: .35,
					layout		: 'form',
					labelWidth	: 50,
					items		: [{
						xtype		: 'textfield',
						fieldLabel	: convertFontCss("E-mail"),
						name		: 'email',
						id			: 'email',
						width		: '200',
						vtype		: 'email'
					}]
				}, {
					columnWidth	: .01,
					layout		: 'form',
					labelWidth	: 1,
					items		: [{
						xtype	: 'tbtext',
						style	: 'padding-top : 8px;',
						text	: '<b>(</b>'
					}]
				}, {
					columnWidth	: .13,
					layout		: 'form',
					labelWidth	: 1,
					items		: [{
						anchor		: "98%",
						xtype		: 'checkbox',
						id			: 'isEquEmail',
						boxLabel	: convertFontCss("同电邮地址"),
						inputValue	: '1',
						listeners	: {
							'check'		: function(obj, isChecked) {
								if(isChecked == true) {
									if(Ext.getCmp("EMAIL").getValue() == '') {
										Ext.Msg.alert('提示', "第一页没有填写电子邮箱");
									}
									Ext.getCmp('email').setValue(Ext.getCmp("EMAIL").getValue());
								}
							},
							'disable'	: function() {
								if(this.el) {
									this.el.up("div").removeClass("x-item-disabled");
									this.el.up("div").setStyle({
										color	: "#555",
										cursor	: "default",
										opacity	: 1
									});
								}
							}
						}
					}]
				}, {
					columnWidth	: .01,
					layout		: 'form',
					labelWidth	: 1,
					items		: [{
						xtype	: 'tbtext',
						style	: 'padding-top : 8px;',
						text	: '<b>)</b>'
					}]
				}]
			}]
		}]
	}, {
		layout	: 'column',
		style	: 'margin:0px 20px 0px 10px;',
		width	: 950,
		items	: [{
			layout			: "table",
			anchor			: '98%',
			defaultType		: "checkbox",
			layoutConfig	: {
				columns	: 2,
				padding	: 10
			},// 将父容器分成2列
			items			: [{
				anchor		: "90%",
				xtype		: 'checkbox',
				boxLabel	: convertFontCss("财务变动通知"),
				id			: 'chgNotice',
				width		: 150,
				inputValue	: '1',
				listeners	: {
					afterrender	: function() {
						this.el.up("div").setStyle("margin", "5px 0px");
					},
					'disable'	: function() {
						if(this.el) {
							this.el.up("div").removeClass("x-item-disabled");
							this.el.up("div").setStyle({
								color	: "#555",
								cursor	: "default",
								opacity	: 1
							});
						}
					}
				}
			}]
		}]
	}]
});	
/**
 * 银行信息容器面板
 */
var panel2 = new Ext.Panel({
	title	    : '银行信息',
	id	        : 'Tab_bankInfo',
	autoScroll	: true,
	buttonAlign	: 'center',
	buttons	    : [{
		text	: '上一页',
		id		: 'openBeforeBtn',
		handler	: function() {
			panel.setActiveTab(0);
		}
	}, {
		text	: '打印开户手册',
		id		: 'openAccountManualBtn',
		handler	: function() {
			if(!crmData.coreAccNo) {// 核心已经开户，就不能更新信息
				getFormData();
			}
			if(fullFormData) {
				window.open("/crmweb/contents/pages/wlj/custmanager/account/accountManual.jsp");
                // 打开一个遮罩，加载开卡界面
				/*
				 * if(!window.openAgreeMentWin){ window.openAgreeMentWin = new Ext.Window({ title : '打印开户手册', width : 800, height: 600, frame : false, border : true, bodyBorder : true, modal : true,
				 * closable : false, closeAction : 'hide', html : '<iframe border=0 width="100%" height="100%" src="/crmweb/contents/pages/wlj/custmanager/account/accountManual.jsp"></iframe>' }); }
				 * window.openAgreeMentWin.show();
				 */
				Ext.getCmp("reCheckBtn").show();
				Ext.getCmp("directReviewBtn").show();
			}
		}
	}, {
		text	: '打印开户手册',
		id		: 'openAccountManualBtnLmh',
		handler	: function() {
			getFormData();
			if(fullFormData) {
				window.open("/crmweb/contents/pages/wlj/custmanager/account/accountManualLmh.jsp");
				Ext.getCmp("openJointAccountAgreement").show();
			}
		}
	}, {
		text	: '打印联名协议书',
		id		: 'openJointAccountAgreement',
		hidden	: true,
		handler	: function() {
			window.open("/crmweb/contents/pages/wlj/custmanager/account/jointAccountAgreement.html");
			Ext.getCmp("reCheckBtn").show();
			Ext.getCmp("directReviewBtn").show();
		}
	}, {
		text	: '提交复核',
		id		: 'reCheckBtn',
		hidden	: true,
		handler	: function() {
			checkEcifWkFlowStatus(0);
		}
	}, {
		text	: '直接复核',
		id		: 'directReviewBtn',
		hidden	: true,
		handler	: function() {
			checkEcifWkFlowStatus(1);
		}
	}, {
		text	: '打印密码函',
		id		: 'openPrintPswBtn',
		hidden	: true,
		handler	: function() {
            /*
			 * crmData.netBankData = { 'coreNo' : crmData.coreNo, 'coreNo' : '50199992212',// 核心客户号 'psw' : 'c554322',// 明文密码 'controlUser' : __userId, 'handleUser' : __userId, // 'checkUser' :
			 * '501N8888', 'checkUser' : crmData.sendto };
			 */
      		// window.open('/crmweb/contents/pages/wlj/custmanager/account/netBank/printPsw.jsp');
      		printNetBankPswMail();// 打印密码函
      	}
  	}, {
		text	: '打印银行业务凭证',
		id		: 'openPrintBVBtn',
		hidden	: true,
		handler	: function() {
			window.open('/crmweb/contents/pages/wlj/custmanager/account/netBank/printBusinessVouchers.html');
		}
	}, {
		text	: '退出系统',
		id		: 'exitSystemBtn',
		handler	: function() {
			Ext.MessageBox.confirm('提示','确定退出系统?',function(buttonId){
				if(buttonId.toLowerCase() == "no"){
					return false;
				}else{
					parent.window._APP.taskBar.currentItem.reload();
				} 
			});	
		}
	}],
    items	    : [{
		layout	: 'column',
		xtype	: 'fieldset',
		id		: 'Tab_accountInfo',
		title	: '<b style="font-size:16px;">账户信息</b>',
		style	: 'margin:10px 180px 20px 180px;',
		items	: [accountInfo]
	}, {
		layout	: 'column',
		xtype	: 'fieldset',
		id		: 'Tab_serviceInfo',
		title	: '<font style="font-size:16px;">服务信息</font>',
		style	: 'margin:10px 180px 20px 180px;',
		items	: [serviceInfo]
	}, {
		layout	: 'column',
		style	: 'margin:10px 180px 20px 180px;',
		items	: [{
			columnWidth	: .4,
			layout		: 'form',
			labelWidth	: 80,
			items		: [{
				xtype		: 'textfield',
				fieldLabel	: convertFontCss("复核人工号"),
				name		: 'reviewNo',
				id			: 'reviewNo',
				width		: '200'
			}]
		}, {
			columnWidth	: .35,
			layout		: 'form',
			labelWidth	: 50,
			items		: [{
				xtype		: 'textfield',
				inputType	: 'password',
				fieldLabel	: convertFontCss("密码"),
				name		: 'reviewPsw',
				id			: 'reviewPsw',
				width		: '200'
			}]
		}]
	}]
});	
/**
 * 基本信息、银行信息的父容器
 */
var panel = new Ext.TabPanel({
	autoScroll:true,
	activeItem : 0,
	layoutOnTabChange : true,
	items:[account_info,panel2]
});

window.getFullCustInfo = function(){
	var jnOrJwCategory = Ext.decode(fullFormData.accountInfoJson).cusCategory;
	var jnwId = jnOrJwCategory+"Checkbox";
	var jnOrJwCheckbox = Ext.decode(fullFormData.accountInfoJson)[jnwId];
	var fullIsLianMinghu = fullFormData.isLianMingHu;
	var fullServiceInfoJson = Ext.decode(fullFormData.serviceInfoJson);
	var fullObjectJson1 = Ext.decode(fullFormData.objectjson1);
	
	window.testPrintCustInfo = {
			"custname" : fullObjectJson1.custname, // 客户姓名
			"customPinYin" : fullObjectJson1.customPinYin, // 姓名拼音
			"citizenShip" : fullObjectJson1.citizenShipText, // 国籍
			"POST_ZIPCODE" :fullObjectJson1.POST_ZIPCODE,// 居住地邮编
			"birthday" :fullObjectJson1.birthday,// 出生日期
			"birthLocale": fullObjectJson1.birthLocaleText,// 出生地
			"identityType3" : fullObjectJson1.identityType3Text,// 证件类型
			"identityType3mz" : fullObjectJson1.identityType3,// 证件类型码值
			"identityNo3" : fullObjectJson1.identityNo3, // 证件号码
			"twIdentNum3" :fullObjectJson1.twIdentNum3,// 台湾证件号
			"gaIdentNum3" :fullObjectJson1.gaIdentNum3,// 港澳证件号
			"LEGAL_EXPIRED_DATE" : fullObjectJson1.LEGAL_EXPIRED_DATE,// 有效日期
			"HOME_ADDR" : fullObjectJson1.HOME_ADDRText,// 居住地所属国籍
			"HOME_ADDR_INFO": fullObjectJson1.HOME_ADDR_INFO,// 居住地详细地址
			"MAIL_ADDR" : fullObjectJson1.MAIL_ADDRText,// 邮寄地所属国籍
			"MAIL_ADDR_INFO" : fullObjectJson1.MAIL_ADDR_INFO,// 邮寄地详细地址
			"MAIL_ZIPCODE" : fullObjectJson1.MAIL_ZIPCODE,// 邮寄邮编
			"QUYUMA" : fullObjectJson1.QUYUMA,// 移动电话国际区号
			"mbPHONENUM" : fullObjectJson1.mbPHONENUM,// 移动电话
			"EMAIL" : fullObjectJson1.EMAIL,// 电子邮箱
			"JOBNAME": fullObjectJson1.JOBNAME,// 单位名称
			"JOB" : fullObjectJson1.JOB, // 职位
			"JOBREMARK" : fullObjectJson1.JOBREMARK, // 职业备注
			"RELATEDNAME" : fullObjectJson1.RELATEDNAME,// 关联人姓名
			"RELATION" : fullObjectJson1.RELATION, // 与关联人关系
			"USTIN"  : fullObjectJson1.USTIN,// US/TIN
			"SEX" : fullObjectJson1.gender, // 性别
			"isRent" : fullObjectJson1.isRent, // 租赁自有
			"JOBINFO" : fullObjectJson1.JOBINFO, // 工作性质
			"HASRELATED" : fullObjectJson1.HASRELATED,// 有无关联人
			"shengming" : fullObjectJson1.radio1, // 本人声明
			"shengming2" : fullObjectJson1.radio2,// 证件2本人声明
			"isUNtaxpayer" : fullObjectJson1.isUNtaxpayer,// 是否为美国纳税人
			"juMinGuo" : fullObjectJson1.juMinGuo,// 居民国
			"taxData"	: fullObjectJson1.taxData,// Ext.encode(taxData),//TIN
			"isUNtaxpayer2" : fullObjectJson1.isUNtaxpayer2, // 证件2是否为美国纳税人
			"USTIN2" : fullObjectJson1.USTIN2,// 证件2US/TIN
			"juMinGuo2" : fullObjectJson1.juMinGuo2,// 证件2居民国
			"taxData2": fullObjectJson1.taxData2, // 证件2TIN
			"REASON3" : fullObjectJson1.REASON3,// 证件2原因
			"REASON4" : fullObjectJson1.REASON4,// 证件2原因
			"detailReason2" : fullObjectJson1.detailReason2,// 证件2具体原因
			"lianMinPinYin" :fullObjectJson1.lianMinPinYin, // 联名户拼音
			"lianMingIdenType1Text" : fullObjectJson1.lianMingIdenType1Text, // 联名户证件类型
			"lianMingIdenType1mz" : fullObjectJson1.lianMingIdenType1, // 联名户证件类型码值
			"lianMingIdenNo1" : fullObjectJson1.lianMingIdenNo1, // 联名户证件号码
			"LEGAL_EXPIRED_DATE2" : fullObjectJson1.LEGAL_EXPIRED_DATE2,// 联名户证件有效日期
			"lianMingTwIdentNum1" : fullObjectJson1.lianMingTwIdentNum1, // 联名户台湾身份证号
			"lianMingGaIdentNum1" : fullObjectJson1.lianMingGaIdentNum1, // 联名户港澳身份证号
			"jnOrJwCheckbox" : jnOrJwCheckbox,// objectjson2.jnOrJwCheckbox, //境内外
			"jnOrJwCategory" : jnOrJwCategory,// objectjson2.jnOrJwCategory, //判断境内境外
			"jiejika" : fullServiceInfoJson.jiejika,  // 借记卡
			"dianziBank" : fullServiceInfoJson.dianziBank, // 电子银行
			"elecState" : fullServiceInfoJson.elecState, // 电子对账单
			"chgNotice" : fullServiceInfoJson.chgNotice,//账务变动通知
			"cardType" : fullServiceInfoJson.cardType, // 普通卡/定制姓名卡
			"cardType1_0" : fullServiceInfoJson.cardType1_0, // 金卡/白金卡/钻石卡
			"cardType2_0" : fullServiceInfoJson.cardType2_0, // 粉蓝/粉红
			"ATMDayLimitDefault" : fullServiceInfoJson.ATMDayLimitDefault, //默认每日累计限额（RMB50,000元）
			"ATMDayCountDefault" : fullServiceInfoJson.ATMDayCountDefault, //默认每日累计笔数（10笔）
			"ATMYearLimitDefault" : fullServiceInfoJson.ATMYearLimitDefault, //默认每年累计限额（RMB10,000,000元）
			"POSDefault" : fullServiceInfoJson.POSDefault, //默认单笔限额（RMB500,000元）
			"ATMDayLimitDefine" : fullServiceInfoJson.ATMDayLimitDefine, //每日累计转账最高限额RMB
			"ATMDayCountDefine" : fullServiceInfoJson.ATMDayCountDefine, //每日累计转账笔数
			"ATMYearLimitDefine" : fullServiceInfoJson.ATMYearLimitDefine, //每年累计转账最高限额RMB
			"POSDefine" : fullServiceInfoJson.POSDefine, //单笔消费限额RMB
			"ATMDayLimit" : fullServiceInfoJson.ATMDayLimit, //每日最高限额
			"ATMDayLimitCount" : fullServiceInfoJson.ATMDayLimitCount, //每日最高笔数
			"ATMYearLimit" : fullServiceInfoJson.ATMYearLimit,//每年累计限额
			"eachCustemLimit" : fullServiceInfoJson.eachCustemLimit, //单笔消费限额
			"netBank" : fullServiceInfoJson.netBank,//网络银行2
			"ukey" : fullServiceInfoJson.ukey,//ukey认证3
			"shortMessage" : fullServiceInfoJson.shortMessage,//短信认证4
			"mobileBank" : fullServiceInfoJson.mobileBank,//手机银行3
			"shortMessage2" : fullServiceInfoJson.shortMessage2,//短信验证4
			"dayAccLimitDefault" : fullServiceInfoJson.dayAccLimitDefault,//默认无限制（日）
			"dayAccCountDefault" : fullServiceInfoJson.dayAccCountDefault,//默认200笔
			"yearAccLimitDefault" : fullServiceInfoJson.yearAccLimitDefault,//默认无限制（年）
			"dayAccSelfDefine" : fullServiceInfoJson.dayAccSelfDefine,//日自定义元
			"dayCountSelfDefine" : fullServiceInfoJson.dayCountSelfDefine,//自定义笔数
			"yearAccSelfDefine" : fullServiceInfoJson.yearAccSelfDefine,//年自定义元
			"email" : fullServiceInfoJson.email, //电子对账单邮箱
			"isEquEmail" : fullServiceInfoJson.isEquEmail, //同电子邮箱
			"REASON" : fullObjectJson1.REASON,//原因
			"REASON2" : fullObjectJson1.REASON2,//原因2
			"detailReason":fullObjectJson1.detailReason//具体原因
	};
	return window.testPrintCustInfo;
};
/**
 * [getICCardAccountInfo 获取开IC卡需要的信息]
 * @return {[type]} [description]
 */
function getICCardAccountInfo(){
	var openCardInfo = {};
	if(crmData){
		if(crmData && crmData.coreAccNo){
			openCardInfo.coreAccNo = crmData.coreAccNo;
			if(crmData && crmData.cardType1_0){
				if(crmData.cardType1_0 == '1' || crmData.cardType1_0 == '2' || crmData.cardType1_0 == '3'){
					openCardInfo.cardType = crmData.cardType1_0;
				}else if(crmData.cardType1_0 == '001' && crmData.cardType2_0){
					openCardInfo.cardType = crmData.cardType2_0;
				}
			}else{
				openCardInfo.errorMsg = "没有获取到选择的卡片类型，无法与读卡器匹配";
			}
		}else{
			openCardInfo.errorMsg = "没有获取到核心客户账号，无法开卡";
		}
		return openCardInfo;
	}else{
		return null;
	}
}


/**
 * [getAccountCardType 判断卡类型]
 * @return {[type]} [description]
 */
function getAccountCardType(){
	var openCardType = '000001';
	var branchId = __units;
	var cardType0 = Ext.getCmp("cardType").getValue();//卡类型码值
	var cardType1 = Ext.getCmp("cardType1_0").getValue();//卡类型码值
	var cardType2 = Ext.getCmp("cardType2_0").getValue();//卡类型码值
	if(cardType0){
		if(cardType0 == '0'){//特色卡
			if(cardType1 && cardType1 == '001'){//特色生活卡(数字卡)
				if(cardType2){
					if(cardType2 == '0011'){//粉蓝
						if(branchId == '502' || branchId == '503' || branchId == '505' || branchId == '506'
							|| branchId == '507' || branchId == '509' || branchId == '510' || branchId == '511'
							|| branchId == '512' || branchId == '515' || branchId == '516'){
							openCardType = '000013';//000013-----数位卡蓝-上海
						}
					}else if(cardType2 == '0012'){//粉红
						if(branchId == '502' || branchId == '503' || branchId == '505' || branchId == '506'
							|| branchId == '507' || branchId == '509' || branchId == '510' || branchId == '511'
							|| branchId == '512' || branchId == '515' || branchId == '516'){
							openCardType = '000017';//000017-----数位卡红-上海
						}
					}
				}
			}
		}else if(cardType0 == '1'){//基础卡
			if(cardType1){
				if(cardType1 == '1'){//金卡
					if(branchId == '502' || branchId == '503' || branchId == '505' || branchId == '506'
						|| branchId == '507' || branchId == '509' || branchId == '510' || branchId == '511'
						|| branchId == '512' || branchId == '515' || branchId == '516'){
						openCardType = '000001';//000001-----金卡-上海
					}
				}else if(cardType1 == '2'){//白金卡
					if(branchId == '502' || branchId == '503' || branchId == '505' || branchId == '506'
						|| branchId == '507' || branchId == '509' || branchId == '510' || branchId == '511'
						|| branchId == '512' || branchId == '515' || branchId == '516'){
						openCardType = '000002';//000002----白金卡-上海
					}
				}else if(cardType1 == '3'){//钻石卡
					if(branchId == '502' || branchId == '503' || branchId == '505' || branchId == '506'
						|| branchId == '507' || branchId == '509' || branchId == '510' || branchId == '511'
						|| branchId == '512' || branchId == '515' || branchId == '516'){
						openCardType = '000003';//000003—钻石卡-上海
					}
				}			
			}
		}
	}
	return openCardType;
}

/**
 * [accountSpeCard 开通定制卡]
 * @return {[type]} [description]
 */
function accountReserceCard(){
	var myMask;
	if(!crmData || !crmData.coreAccNo){
		Ext.Msg.alert('开通定制姓名卡', "没有核心客户账号，无法开通借记卡");
	    return;
	}
	if(fullFormData && fullFormData.objectjson1){
		if(typeof fullFormData.objectjson1 == 'string'){
			var json_firstPage = JSON.parse(fullFormData.objectjson1);
			var accountName = json_firstPage.customPinYin;//客户名
		}
	}
	//处理卡类型
	//var speCardType = getAccountCardType();
	var speCardType = crmData.cardType1_0;//卡类型码值
	if(!speCardType){
	    Ext.Msg.alert('开通定制姓名卡', "获取借记卡类型失败，请联系管理员");
	    return;
	}
	$.ajax ({
		url:  basepath + '/oneKeyAccountAction!accountReserceCard.json?requestTime='+Date.now(),
        type : 'GET',
        dataType : 'json',
        timeout : 180000,
        data : {
        	'AccountNo' : crmData.coreAccNo,
        	'custNm' : accountName,
        	'speCardType' : speCardType
        },
		/*beforeSend : function(){
			myMask = new Ext.LoadMask(Ext.getBody(), {msg:"开通定制卡，请稍等..."});
			myMask.show();
		},*/
        success : function(result){
        	if(result){
        		//****************************开通定制卡结果
        		/*if(result.status){
        			crmData.openSpecialCardStatus = result.status;
        			crmData.openSpecialCardMsg = result.msg;
        		}*/
        		crmData.accountCardNo = result.cardNo;
        		adjustSpecialCardOpen(result.status,result.msg);
        	}
        },
        complete : function(request, status){
        	if(myMask){
        		myMask.hide();
        	}
    		if(status && status == 'timeout'){//超时
				Ext.Msg.alert('开通定制姓名卡', '开卡超时，请联系管理员');
				return;		
			}
        }
    });
}

var wkflowInterval2;
/**
 * 流程复核
 */
function reCheckAccount(){
	//var nowFlowTime =  new Date().getTime().toString();
	var cusId  = crmData.custId;
	Ext.Ajax.request({
		url : basepath + '/oneKeyAccountAction!initFlow.json',
		method : 'GET',
		params : {
			'cusId':cusId,
			'custName':fullDataJson.objectjson1.custname
		},
		success : function(response) {
			var ret = Ext.decode(response.responseText);
			var instanceid = ret.instanceid;//流程实例ID
			//将流程实例id塞入全局json中
			crmData.instanceid = ret.instanceid;
			var currNode = ret.currNode;//当前节点
			var nextNode = ret.nextNode;//下一步节点
			//提交----撤销
			selectUserList(instanceid,currNode,nextNode,function(){
				//获取下一节点办理人
				 $.ajax({
					url : basepath + '/oneKeyAccountAction!getNextNodeUser.json',
					type : 'GET',
					dataType:'json',
					data : {
						instanceid : ret.instanceid
					},
					success:function(result){
						if(result.success == true){
							crmData.sendto = result.sendto;
							Ext.MessageBox.wait("流程正在审批中，审批人是"+ result.userName+"["+result.sendto+"]请勿关闭当前页面...");
						}else{
							Ext.Msg.alert("提示", "流程下一办理人获取失败！");
							return;
						}
					}
				});
			});//选择下一步办理人
			
			var checkCount = 0;
			wkflowInterval2 = window.setInterval(function(){//创建定时器
				$.ajax({
					url:  basepath + '/oneKeyAccountAction!checkWorkFlowInfo.json?requestTime='+new Date(),
			        type : 'GET',
			        dataType : 'json',
			        data : {
			        	'logId' : serializeId,
			        	'custId' : crmData.custId,
			        	'instanceid' : crmData.instanceid
			        	 //'nowFlowTime' : nowFlowTime
			        },
			        success : function(result){
			        	//Ext.MessageBox.wait("流程正在审批中，请勿关闭当前页面...");
			        	
						Ext.getCmp("reCheckBtn").enable();
						Ext.getCmp("directReviewBtn").enable();
		    			if(checkCount > 240){
		    				Ext.MessageBox.hide();
		        			window.clearInterval(wkflowInterval2);//清除定时器
		        			if(myMask){
				        		myMask.hide();
				        	}
		        			Ext.Msg.alert("流程审批", "审批耗时太长，请联系审批人");
		        			return;
		    			}
			        	if(result){
				        	if(result.flag){
				        		if(result.flag == '00' || result.flag == '03' ||  result.flag == '04'){//03否决||04撤销
				        			Ext.MessageBox.hide();
				        			window.clearInterval(wkflowInterval2);
				        			Ext.Msg.alert("流程审批", result.msg);
				        			return;
				        		}else if(result.flag == '01' || result.flag == '05'){
				        			//审批中01||未提交流程05
				        		}else if(result.flag == '02'){//审批同意
				        			Ext.MessageBox.hide();
				        			window.clearInterval(wkflowInterval2);
									//请求核心开户
				        			Crm2CBAccount();
				        		}
				        	}
			        	}
			    		checkCount++;
			        }
			    });
			}, 5000);
		},
		failure : function() {
			Ext.Msg.alert('流程审批', '操作失败');
			reloadCurrentData();
		}
	});
}

/**
 * 直接复核
 */
function directReview(){
	//复核人工号
	var reviewNo = Ext.getCmp("reviewNo").getValue();
	//密码
	var reviewPsw = Ext.getCmp("reviewPsw").getValue();
	
	if(reviewNo == ''){
		Ext.MessageBox.alert('直接复核',"请填写复核人工号！");
		return;
	}
	
	if(reviewPsw == ''){
		Ext.MessageBox.alert('直接复核',"请填写复核人密码！");
		return;
	}
	if(crmData.custId == null || crmData.custId ==''){
		Ext.MessageBox.alert('直接复核',"未获取到Ecif客户号！无法直接复核！");
		return;
	}
	$.ajax({
        url:  basepath + '/oneKeyAccountAction!directReview.json',
        type : 'GET',
        dataType : 'json',
        data : {
        		reviewNo :reviewNo,
        		reviewPsw :reviewPsw,
        		cust_id : crmData.custId,
        		cust_name : fullDataJson.objectjson1.custname
        	},
        success: function (result) {
        	if(result.success == false){
        		Ext.MessageBox.alert('直接复核',result.msg);
        		return;
        	}else if(result.success == true){
        		crmData.sendto = reviewNo;
        		//核心开户
        		Crm2CBAccount();
        	}
        }
    });
}

/**
 * [Crm2CBAccount 请求核心开户]
 */
function Crm2CBAccount(){
	crmData.serviceAccountStatus = '0';

	var myMask;
	if(!crmData || !crmData.custId){
		Ext.Msg.alert('核心开户', "没有获取到ECIF客户号，无法请求核心开户");
		return;
	}
	Ext.MessageBox.wait("请求核心开户，请稍等...");
	$.ajax({
		url:  basepath + '/oneKeyAccountAction!CRM2CBAccount.json?requestTime='+Date.now(),
        type : 'GET',
        dataType : 'json',
		/*beforeSend : function(){
			myMask = new Ext.LoadMask(Ext.getBody(), {msg:"请求核心开户，请稍等..."});
			myMask.show();
		},*/
        data : {
        	'logId' : serializeId,
        	'custId' : crmData.custId
        },
        success : function(result){
        	if(Ext.MessageBox){
        		Ext.MessageBox.hide();
        	}
        	if(result){
        		if(result.status && result.status == 'success'){//核心开户成功
        			if(result.CUSTCOD){
        				Ext.getCmp("addBtn").hide();
        				if(fullDataJson.objectjson1["taxData"]){
        					Ext.getCmp("exceptionHand").items.each(function(item){
        						var aa = item.items.keys[0].substr(8);
        						Ext.getCmp("deleteBtn"+ item.items.keys[0].substr(8)).hide();
						   	});
        				}
        				account_info.getForm().items.each(function(f){
        					f.disable();
        				});
        				accountInfo.getForm().items.each(function(f){
        					f.disable();
        				});
        				serviceInfo.getForm().items.each(function(f){
        					f.disable();
        				});
        				Ext.getCmp("reviewNo").setReadOnly(true);
        				Ext.getCmp("reviewPsw").setReadOnly(true);
        				
        				crmData.coreNo = result.CUSTCOD;
        				crmData.coreAccNo = result.ACCNO;//核心客户账号

        				var serviceInfoJson1 =  Ext.decode(fullFormData.serviceInfoJson);
        				var checkCardApply = serviceInfoJson1["jiejika"];
						var checkNetBank  = serviceInfoJson1["dianziBank"];
        				if(checkCardApply == 1){
	        				crmData.cardType2_0 = serviceInfoJson1["cardType2_0"];
	        				if(crmData.cardType2_0 ){
	        					if(crmData.cardType2_0 == '1002'){//定制姓名卡
		        					if(checkNetBank == 1){//开网银
			    						crmData.serviceAccountStatus = '5';
		        						Ext.MessageBox.wait("正在开卡和网银，请稍等...");
			    						getNetBankAccountInfo();
			    					}else{
			    						crmData.serviceAccountStatus = '2';
			    						Ext.MessageBox.wait("正在开卡，请稍等...");
			    					}
		        					accountReserceCard();
		        				}else{//普通卡
		        					if(checkNetBank == 1){//开网银
			    						crmData.serviceAccountStatus = '4';
		        						Ext.MessageBox.wait("正在开卡和网银，请稍等...");
			    						getNetBankAccountInfo();
			    					}else{
			    						crmData.serviceAccountStatus = '1';
			    						Ext.MessageBox.wait("正在开卡，请稍等...");
			    					}
		        					
							    	//获取IC卡具端口
							    	$.ajax({
										url : '/crmweb/oneKeyAccountAction!getCardOpenInfo.json',
										type : "GET",
										dataType : "json",
										success : function(result){
											if(result && result.IcPort && result.cardTypeValid){
												if(crmData){
													crmData.IcPort = result.IcPort;
													crmData.cardTypeValid = result.cardTypeValid;
													//打开一个遮罩，加载开卡界面
											    	if(!window.openCardWin){
											    		window.openCardWin = new Ext.Window({
											    			title : '开卡',
											    			width : 800,
											    			height: 600,
											    			frame : false,
											    			border : true,
											    			bodyBorder : true,
											    			modal : true,
											    			closable : false,
											    			closeAction : 'hide',
											    			html : '<iframe id="cardFrame" name="cardFrame" border=0 width="100%" height="100%" src="/crmweb/contents/pages/wlj/custmanager/account/ICCard/ICCardAndPswKey.jsp"></iframe>',
											    			listeners : {
											    				render : function(){
											    					// Ext.getDom("cardFrame").
											    				}
											    			}
											    		});
											    	}
												    window.openCardWin.show();
												}
											}else{
												Ext.Msg.alert("开通普通卡", "没有获取到预设端口号或卡片类型的校验");
												return;
											}
										},
										complete : function(){
									    	
										}
									});
							    	//打开一个遮罩，加载开卡界面
							    	/*if(!window.openCardWin){
							    		window.openCardWin = new Ext.Window({
							    			title : '开卡',
							    			width : 800,
							    			height: 600,
							    			frame : false,
							    			border : true,
							    			bodyBorder : true,
							    			modal : true,
							    			closable : false,
							    			closeAction : 'hide',
							    			html : '<iframe border=0 width="100%" height="100%" src="/crmweb/contents/pages/wlj/custmanager/account/ICCard/ICCardAndPswKey.jsp?accountNetBank='+checkNetBank+'"></iframe>'
							    		});
							    	}
								    window.openCardWin.show();*/
							      	// window.open("/crmweb/contents/pages/wlj/custmanager/account/ICCard/ICCardAndPswKey.html?accountNetBank="+checkNetBank);
		        				}
	        				}
        				}else{
        					if(checkNetBank == 1){//开网银
		    					crmData.serviceAccountStatus = '3';
        						Ext.MessageBox.wait("正在开网银，请稍等...");
	    						getNetBankAccountInfo();
	    					}else{
	    						//只开核心
	    						if(result.msg){
			        				Ext.MessageBox.alert("核心开户",result.msg,function(){
									  	parent.window._APP.taskBar.currentItem.reload();
									});
			        			}
	    					}
        				}
    					adjustCardAndNetBank();
        			}else{
        				Ext.MessageBox.alert("核心开户",result.msg,function(){
						  	parent.window._APP.taskBar.currentItem.reload();
						});
        			}
        		}else{//开核心失败
        			if(result.msg){
        				Ext.Msg.alert("核心开户", result.msg);
        			}
        			return;
        		}
        	}
        }
    });
}

/*//开通普通卡
function openNormalCardAccount(){
	if(!crmData || !crmData.ICCardInfo){
		Ext.alert("开通普通卡", "获取卡信息失败");
		return;
	}

	// var myMask;
	$.ajax({
		url : '/crmweb/oneKeyAccountAction!invokeCardSysImpl.json',
		type : "GET",
		dataType : "json",
		data : crmData.ICCardInfo,
		timeout : 120000,
		beforeSend : function(){
			// myMask = new Ext.LoadMask(Ext.getBody(), {msg:"正在请求卡系统开卡，请稍等..."});
			// myMask.show();
		},
		success : function(response) {
			// if(myMask){
			// 	myMask.hide();
			// }
			if(response && response.status && response.msg){
				adjustNormalCardOpen(response.status, response.msg);
			}else{
				adjustNormalCardOpen('error', '开卡失败，响应信息为空');
			}
	    },
	    complete : function(request, status){
			// if(myMask){
			// 	myMask.hide();
			// }
	    	if(status && status == 'timeout'){//超时
	    		adjustNormalCardOpen('error', '请求超时，开卡失败...');
			}
	    }
	});

}*/

//判断开普通卡
function adjustNormalCardOpen(status, msg){
	if(status && msg){
		crmData.openNormalCardMsg = msg;
		crmData.openNormalCardStatus = status;
	}
}

//判断开定制卡
function adjustSpecialCardOpen(status, msg){
	if(status && msg){
		crmData.openSpecialCardMsg = msg;
		crmData.openSpecialCardStatus = status;
	}
}

//判断开通网银
function adjustNetBankStatus(status, msg){
	if(status && msg){
		crmData.openNetBankMsg = msg;
		crmData.openNetBankStatus = status;
	}
}


//清除开卡开网银的返回数据
function clearAccountServiceRetInfo(){
	crmData.openNormalCardMsg = null;
	crmData.openNormalCardStatus = null;
	crmData.openSpecialCardMsg = null;
	crmData.openSpecialCardStatus = null;
	crmData.openNetBankMsg = null;
	crmData.openNetBankStatus = null;
}

//开卡、开网银提示信息
function adjustCardAndNetBank(){
	var msg = "";
	//判断当前开通的业务类型
	var interval_finalMsg = window.setInterval(function(){
		if(crmData.serviceAccountStatus){
			if(crmData.serviceAccountStatus == '1'){//只开普通卡
				if(crmData.openNormalCardStatus && crmData.openNormalCardMsg){
					if(interval_finalMsg){
						window.clearInterval(interval_finalMsg);
					}
					var logMsg = "开卡结果--[" + crmData.openNormalCardMsg + "]";
					if(crmData.openNormalCardStatus != 'success'){
						logMsg += '<br/>核心客户号为['+crmData.coreNo+'],核心客户账号为['+crmData.coreAccNo+'],请到卡系统自行开卡';
					}
					Ext.getCmp("openPrintBVBtn").show();//打印银行业务凭证
					Ext.Msg.alert("提示--普通卡开通结果", logMsg);
					clearAccountServiceRetInfo();
					/*Ext.MessageBox.alert("提示--普通卡开通结果",logMsg,function(){
					  	parent.window._APP.taskBar.currentItem.reload();
					});*/
				}
			}else if(crmData.serviceAccountStatus == '2'){//只开定制卡
				if(crmData.openSpecialCardStatus && crmData.openSpecialCardMsg){
					if(interval_finalMsg){
						window.clearInterval(interval_finalMsg);
					}
					var logMsg = "开卡结果--[" + crmData.openSpecialCardMsg + "]";
					if(crmData.openSpecialCardStatus != 'success'){
						logMsg += '<br/>核心客户号为['+crmData.coreNo+'],核心客户账号为['+crmData.coreAccNo+'],请到卡系统自行开卡';
					}
					Ext.getCmp("openPrintBVBtn").show();//打印银行业务凭证
					Ext.Msg.alert("提示--定制卡开通结果", logMsg);
					clearAccountServiceRetInfo();
					/*Ext.MessageBox.alert("提示--定制卡开通结果",logMsg,function(){
					  	parent.window._APP.taskBar.currentItem.reload();
					});*/
				}
			}else if(crmData.serviceAccountStatus == '3'){//只开网银
				if(crmData.openNetBankStatus && crmData.openNetBankMsg){
					if(interval_finalMsg){
						window.clearInterval(interval_finalMsg);
					}
					var logMsg = "开网银结果--[" + crmData.openNetBankMsg + "]";
					if(crmData.openNetBankStatus != 'success'){
						logMsg += '<br/>核心客户号为['+crmData.coreNo+'],核心客户账号为['+crmData.coreAccNo+'],请到网银系统自行开通';
					}
					Ext.getCmp("openPrintBVBtn").show();//打印银行业务凭证
					Ext.getCmp("openPrintPswBtn").show();//打印密码函
					Ext.Msg.alert("提示--网银开通结果", logMsg);
					clearAccountServiceRetInfo();
				}
			}else if(crmData.serviceAccountStatus == '4'){//开通普通卡和网银
				if(crmData.openNormalCardStatus && crmData.openNormalCardMsg
					&& crmData.openNetBankStatus && crmData.openNetBankMsg){
					if(interval_finalMsg){
						window.clearInterval(interval_finalMsg);
					}
					var logMsg = "开卡结果--[" + crmData.openNormalCardMsg + "]<br/>开网银结果--[" + crmData.openNetBankMsg + "]";
					if(crmData.openNormalCardStatus != 'success' || crmData.openNetBankStatus != 'success'){
						logMsg += '<br/>核心客户号为['+crmData.coreNo+'],核心客户账号为['+crmData.coreAccNo+'],请到对应系统自行开通';
					}
					Ext.getCmp("openPrintBVBtn").show();//打印银行业务凭证
					Ext.getCmp("openPrintPswBtn").show();//打印密码函
					Ext.Msg.alert("提示--普通卡和网银开通结果", logMsg);
					clearAccountServiceRetInfo();
					/*Ext.MessageBox.alert("提示--普通卡和网银开通结果",logMsg,function(){
					  	parent.window._APP.taskBar.currentItem.reload();
					});*/
				}
			}else if(crmData.serviceAccountStatus == '5'){//开通定制卡和网银
				if(crmData.openSpecialCardStatus && crmData.openSpecialCardMsg 
					&& crmData.openNetBankStatus && crmData.openNetBankMsg){
					if(interval_finalMsg){
						window.clearInterval(interval_finalMsg);
					}
					var logMsg = "开卡结果--[" + crmData.openSpecialCardMsg + "]<br/>开网银结果--[" + crmData.openNetBankMsg + "]";
					if(crmData.openSpecialCardStatus != 'success' || crmData.openNetBankStatus != 'success'){
						logMsg += '<br/>核心客户号为['+crmData.coreNo+'],核心客户账号为['+crmData.coreAccNo+'],请到对应系统自行开通';
					}
					Ext.getCmp("openPrintBVBtn").show();//打印银行业务凭证
					Ext.getCmp("openPrintPswBtn").show();//打印密码函
					Ext.Msg.alert("提示--定制卡和网银开通结果", logMsg);
					clearAccountServiceRetInfo();
					/*Ext.MessageBox.alert("提示--定制卡和网银开通结果",logMsg,function(){
					  	parent.window._APP.taskBar.currentItem.reload();
					});*/
				}
			}
		}
	}, 3000);
}

function checkValue(valueStatus){
	var windowInterval8
	if(!valueStatus){
		windowInterval8 = window.setInterval(function(){
			valueStatus = checkValue(valueStatus);
		},3000);
	}else{
		window.clearInterval(windowInterval8);
		Ext.MessageBox.hide();
		return valueStatus;
	}
}

/**
 * [printPswPage 打印密码函]
 * @param  {[type]} result [description]
 * @return {[type]}        [description]
 *//*
function printPswPage(result){
	if(!result || !result.psw){
		Ext.Msg.alert("返回结果中没有密码信息，无法打印密码函");
		return;
	}	
	Ext.Msg.alert("打印密码函", "当前网银账户密码是：【"+result.psw+"】");
}
*/

/**
 * [getEcifAccountInfo 获取开网银需要的信息]
 * @return {[type]} [description]
 */

// {
// 	'CifNo' : '502000015426',//核心客户号
// 	'CustName' : '彭伯祉',//客户姓名
// 	'IdType' : 'P13',//证件类型 IdType.P00=身份证 IdType.P01=军官证 IdType.P02=文职干部证 IdType.P03=警官证 IdType.P04=士兵军人证 IdType.P05=护照 IdType.P06=港澳台居民身份证 IdType.P07=户口簿 IdType.P13=台湾居民往来通行证 IdType.P99=其他
// 	'IdNo' : 'T220318413',//证件号
// 	'BirthDate' : '1972-02-06',//出生日期
// 	'PmbsTelNo' : '30-01463193718',//手机银行签约手机号码
// 	'CifType' : 'PN',//客户类别
// 	'TELType' : 'M',//电话类型 C=办公电话 H=家庭电话 L=固定电话 M=移动电话
// 	'TELNo' : '30-01463193718',//电话号码
// 	'TelAuthFlg' : 'Y',//验证标识 N=未认证 Y=已认证 Z=自助认证  默认Y
// 	'EAddrType' : 'Email',//地址类型 默认输入Email
// 	'EAddr' : 'anlghh.pna@fatkhnsfuodw.pmt',//地址 邮箱地址
// 	'ChannelList' : [
// 		{
// 			'MChannelId' : 'PIBS',//模块渠道 PIBS=个人网上银行 PMBS=个人手机银行
// 			'AuthMod' : 'U'//审核方式 U：USBkey证书， C：云证通，F：文件证书，O：动态口令，Z：普通（无认证）,OU:短信+证书;
// 		},
// 		{
// 			'MChannelId' : 'PMBS',//模块渠道 PIBS=个人网上银行 PMBS=个人手机银行
// 			'AuthMod' : 'O',//审核方式 U：USBkey证书， C：云证通，F：文件证书，O：动态口令，Z：普通（无认证）,OU:短信+证书;
// 			'mchMobilePhone' : '30-01463193718'//
// 		}
// 	],
// 	'DayPerLimit' : '999999999999.99',//人民币日累计转账限额
// 	'DayTransTimes' : '200',//人民币日累计转账笔数
// 	'LimitPerYear' : '999999999999.99'//人民币年累计转账限额
// }

/**
 * [printNetBankPswMail 打印密码函]
 * @return {[type]} [description]
 */
function printNetBankPswMail(){
	if(crmData && crmData.netBankData){
		var netBankData = {
			'coreNo' : crmData.netBankData.coreNo,//核心客户号
			'custNm' : crmData.netBankData.custNm,//客户姓名
			'identNo' : crmData.netBankData.identNo,//证件号码
			'psw' : crmData.netBankData.psw,//密码
			'controlUser' : crmData.netBankData.controlUser,
			'handleUser' : crmData.netBankData.handleUser,
			'checkUser' : crmData.netBankData.checkUser
		};
	}	
	//创建一个隐藏域用于初始化密码函打印控件
	var printActiveX = '<iframe id="d_pswMail" style="display:none;" src="/crmweb/contents/pages/wlj/custmanager/account/netBank/printPsw.jsp"></iframe>';
	$('body').append(printActiveX);

}

/**
 * 开网银
 */
function getNetBankAccountInfo(){
	// crmData.coreAccNo = '503000019393';
	//开网银需要的信息
	var NetBankCustInfo = {};
	if(!serializeId){
		Ext.Msg.alert('开通网银', '没有获取到交易流水号，请联系管理员...');
		return;
	}else{
		NetBankCustInfo.serializeId = serializeId;
	}
	if(crmData && crmData.coreNo){
		NetBankCustInfo.CifNo = crmData.coreNo;//核心客户号
	}else{
		Ext.Msg.alert("开通网银", "没有核心客户号，无法开网银");
		return;
	}
	
	if(!visitJson){
		Ext.Msg.alert('开通网银', '获取客户基本信息失败，无法开网银...');
	}
	NetBankCustInfo.CustName = visitJson.customername;//客户名
	var netBankIdentType;
	if(visitJson.certtype){
		// if(visitJson.certtype == '1'){//P07
		// 	netBankIdentType = 'P07';
		// }else if(visitJson.certtype == '3'){
		// 	netBankIdentType = 'P01';
		// }else if(visitJson.certtype == '4'){
		// 	netBankIdentType = 'P04';
		// }else if(visitJson.certtype == '9'){
		// 	netBankIdentType = 'P03';
		// }else if(visitJson.certtype == '0'){
		// 	netBankIdentType = 'P00';
		// }else if(visitJson.certtype == 'X1'){
		// 	netBankIdentType = 'P06';
		// }else if(visitJson.certtype == 'X2'){
		// 	netBankIdentType = 'P13';
		// }else if(visitJson.certtype == 'X4'){
		// 	netBankIdentType = 'P05';
		// }else{
		// 	netBankIdentType = 'P99';
		// }
		NetBankCustInfo.IdType = visitJson.certtype;//证件类型，需要转换
	}else{
		Ext.Msg.alert('开通网银', '获取证件类型失败，无法开网银...');
		return;
	}
	NetBankCustInfo.IdNo = visitJson.certid;//证件号码
	if(fullFormData && fullFormData.objectjson1){
		var firstPageInfo = fullFormData.objectjson1;
		if(typeof firstPageInfo == 'string'){
			firstPageInfo =JSON.parse(firstPageInfo);
		}
		if(!firstPageInfo.EMAIL){
			Ext.Msg.alert('开通网银', '开通网银需要提供电子邮件地址，请补充信息后重试...');
			return;
		}
		NetBankCustInfo.BirthDate = firstPageInfo.birthday;//客户生日
		NetBankCustInfo.PmbsTelNo = firstPageInfo.mbPHONENUM;//手机号
		NetBankCustInfo.TELType = 'M';//手机号
		NetBankCustInfo.CifType = 'PN';//客户类型
		NetBankCustInfo.TELNo = firstPageInfo.mbPHONENUM;//手机号
		NetBankCustInfo.HomeAddr = firstPageInfo.HOME_ADDR_INFO;//家庭地址信息
		NetBankCustInfo.PostZipCode = firstPageInfo.POST_ZIPCODE;//居住地邮编
		NetBankCustInfo.EAddr = firstPageInfo.EMAIL;//Email地址
	}
	if(fullFormData && fullFormData.serviceInfoJson){
		var serviceInfo = fullFormData.serviceInfoJson;
		if(typeof serviceInfo == 'string'){
			serviceInfo = JSON.parse(serviceInfo);
		}
		//是否开通电子银行
		if(serviceInfo && serviceInfo.dianziBank && serviceInfo.dianziBank == '1'){
			NetBankCustInfo.ChannelList = [];
			//是否开通网络银行
			if(serviceInfo.netBank && serviceInfo.netBank == '1'){
				
				var checkUkey = false;//是否开通UKEY验证
				var checkShortMsg = false;//是否开通短信验证
				if(serviceInfo.ukey && serviceInfo.ukey == '1'){
					checkUkey = true;
				}
				if(serviceInfo.shortMessage && serviceInfo.shortMessage == '1'){
					checkShortMsg = true;
				}
/*
				var netBank = {
					'MChannelId' : 'PIBS'
				};
				if(checkUkey && !checkShortMsg){
					netBank.AuthMod = 'U';
				}else if(!checkUkey && checkShortMsg){
					netBank.AuthMod = 'O';
				}else if(checkUkey && checkShortMsg){
					netBank.AuthMod = 'OU';
				}
				NetBankCustInfo.ChannelList.push(netBank);
*/
				if(checkUkey){
					var netBank = {
						'MChannelId' : 'PIBS'
					};
					netBank.AuthMod = 'U';
					NetBankCustInfo.ChannelList.push(netBank);
				}
				if(checkShortMsg){
					var netBank = {
						'MChannelId' : 'PIBS'
					};
					netBank.AuthMod = 'O';
					netBank.mchMobilePhone = NetBankCustInfo.PmbsTelNo;
					NetBankCustInfo.ChannelList.push(netBank);
				}
				
			}
			//是否开通手机银行
			if(serviceInfo.mobileBank && serviceInfo.mobileBank == '1'){
				var mobBank = {
					'MChannelId' : 'PMBS'
				};
				//是否开通短信验证
				if(serviceInfo.shortMessage2 && serviceInfo.shortMessage2 == '1'){
					mobBank.AuthMod = 'O';
					mobBank.mchMobilePhone = NetBankCustInfo.PmbsTelNo;
				}
				NetBankCustInfo.ChannelList.push(mobBank);
			}
			//转账限额
			NetBankCustInfo.DayPerLimit = '999999999999.99';//人民币日累计转账限额
			NetBankCustInfo.DayTransTimes = '200';//人民币日累计转账笔数
			NetBankCustInfo.LimitPerYear = '999999999999.99';//人民币年累计转账限额
			if(serviceInfo.dayAccLimitDefault && serviceInfo.dayAccLimitDefault == '0'
				&& serviceInfo.dayAccSelfDefine){
				NetBankCustInfo.DayPerLimit = serviceInfo.dayAccSelfDefine;
			}
			if(serviceInfo.dayAccCountDefault && serviceInfo.dayAccCountDefault == '0'
				&& serviceInfo.dayCountSelfDefine){
				NetBankCustInfo.DayTransTimes = serviceInfo.dayCountSelfDefine;
			}
			if(serviceInfo.yearAccLimitDefault && serviceInfo.yearAccLimitDefault == '0'
				&& serviceInfo.yearAccSelfDefine){
				NetBankCustInfo.LimitPerYear = serviceInfo.yearAccSelfDefine;
			}
		}else{
			Ext.Msg.alert('开通网银', '没有勾选网银服务，不需要开通网银');
			return;
		}
	}else{
		Ext.Msg.alert('开通网银', '没有勾选网银服务，不需要开通网银');
		return;
	}
	var myMask;
	$.ajax({
		url:  basepath + '/oneKeyAccountAction!invokeCyberBankSysImpl.json?requestTime='+Date.now(),
        type : 'GET',
        dataType : 'json',
        timeout : 180000,
		/*beforeSend : function(){
			myMask = new Ext.LoadMask(Ext.getBody(), {msg:"正在开通网银，请稍等..."});
			myMask.show();
		},*/
        data : {
        	'netBankAccountInfo' : JSON.stringify(NetBankCustInfo)
        },
        success : function(result){
        	if(result && result.status && result.msg){
        		var resStatus = result.status;
        		if(resStatus == 'success'){
        			if(!result.psw){
        				adjustNetBankStatus("error","开网银失败【返回信息中没有密码】");
        				return;
        			}
        			crmData.netBankData = {
        				'coreNo' : crmData.coreNo,//核心客户号
        				'custNm' : NetBankCustInfo.CustName,//客户姓名
        				'identNo' : NetBankCustInfo.IdNo,//证件号码
        				'psw' : result.psw,//明文密码
        				'controlUser' : __userId,
        				'handleUser' : __userId,
        				//'checkUser' : '501N8888'
        				'checkUser' : crmData.sendto
        			};
        		}
        		adjustNetBankStatus(result.status,result.msg);
        	}
        },
        complete : function(){
        	if(myMask){
        		myMask.hide();
        	}
        }
	});
}

/*function getNextNodeUser(){
	//获取下一节点办理人
	 $.ajax({
		url : basepath + '/oneKeyAccountAction!getNextNodeUser.json',
		type : 'GET',
		dataType:'json',
		data : {
			instanceid : crmData.instanceid
		},
		success:function(result){
			if(result.success == true){
				crmData.sendto = result.sendto;
			//alert("流程下一办理人获取成功！"+result.sendto);
			}else{
				Ext.Msg.alert("提示", "流程下一办理人获取失败！");
				return;
			}
		}
	});
}*/

/**
 * 初始化第二页页面
 */
function initPage(){
	viewport.removeAll();
	viewport.add(panel);
	viewport.doLayout();
	
	Ext.getCmp('JOBREMARK').setVisible(false);
	Ext.getCmp('shengMing').setVisible(false);
	Ext.getCmp('RELATEDNAME').setVisible(false);
	Ext.getCmp('RELATION').setVisible(false);
	Ext.getCmp('USTIN').setVisible(false);
	//Ext.getCmp('lianminghu').setVisible(false);
	Ext.getCmp('JOBNAME').setVisible(false);
	Ext.getCmp('JOB').setVisible(false);
	
	//huangxin20180823 --根据证件号码设置发证机关是否必输
	//当是身份证时必输
	if(visitJson.jointaccount == 1){  //判断是否联名户 -- 1是 0否
		if(visitJson.certtype == 0){  //当证件1类型是身份证时
			Ext.getCmp('qianfajiguan').allowBlank = false;  //必输
			Ext.DomQuery.selectNode('label[for=qianfajiguan]').innerHTML='<font color=red>*</font>'+convertFontCss("发证机关所在地");
		}else{
			Ext.getCmp('qianfajiguan').allowBlank = true;  //必输
			Ext.DomQuery.selectNode('label[for=qianfajiguan]').innerHTML=convertFontCss("发证机关所在地");
		}
		if(visitJson.certtype2 == 0){  //当证件2类型是身份证时
			Ext.getCmp('CITIZENSHIP2').allowBlank = false;  //必输
			Ext.DomQuery.selectNode('label[for=CITIZENSHIP2]').innerHTML='<font color=red>*</font>'+convertFontCss("发证机关所在地");
		}else{
			Ext.getCmp('CITIZENSHIP2').allowBlank = true;  //非必输
			Ext.DomQuery.selectNode('label[for=CITIZENSHIP2]').innerHTML=convertFontCss("发证机关所在地");
		}
	}else{
		if(visitJson.certtype == 0){  //当证件1类型是身份证时
			Ext.getCmp('qianfajiguan').allowBlank = false;  //必输
			Ext.DomQuery.selectNode('label[for=qianfajiguan]').innerHTML='<font color=red>*</font>'+convertFontCss("发证机关所在地");
		}else{
			Ext.getCmp('qianfajiguan').allowBlank = true;  //非必输
			Ext.DomQuery.selectNode('label[for=qianfajiguan]').innerHTML=convertFontCss("发证机关所在地");
		}
	}
	
	
	//查询标准码表信息(境内外标志)，以及对应的账户类型,主要用于账户信息区域控件的动态生成
	//返回信息格式：{"code":"境内外类型编号","value":"境内外类型名称","items":[{"code":"账户类型","value":"账户类型名称"}]}
	$.ajax({
	    url: basepath + '/oneKeyAccountAction!getAccountType.json',
	    type : 'GET',
	    dataType : 'json',
	    success: function (info) {
	    	crmData.jnwList = info;
	    	//遍历返回的境内外信息,生成账户信息面板下的控件
	    	for(var i in info){
	    		if(i=='remove'){
	    			continue;
	    		}
	    		var infoItem = info[i];
	    		if(infoItem.value == '未知'){//去掉未知的选项，只保留境内、境外
	    			continue;
	    		}else{
		    		var value = infoItem.value;//境内/外名称
		    		var code = infoItem.code;//境内/外名称
		    		var items = infoItem.items;//境内/外类型对应的账户类型
		    		var radioItems = [];
		    		if(jnwAccountType.indexOf(code) < 0){
		    			jnwAccountType.push(code);
		    		}
		    		//生成境内/外类型对应的单选框控件
		    		radioItems.push({
		    			  xtype:'radio',
					  	  boxLabel:convertFontCss(value),
					  	  inputValue:code,
					  	  name:'cusCategory',
					  	  id:"radio"+code,
					  	  listeners:{
					  	  		'afterrender':function(){//调整样式
					  	  			if(this.el){
					  	  				this.el.up("div").removeClass("x-item-disabled");
					  	  				this.el.up("div").setStyle({
					  	  					color : "#555",
					            			cursor: "default",
					            			opacity: 1
					  	  				});
					  	  			}
					  	  		},
							   'check':function(obj,isChecked){
								   var selCode = obj.inputValue;
								   var identTypeJoint = visitJson.identTypeJoint;//联名户类型
								   var jnwType =  crmData.resultMap1[identTypeJoint];
	
								  /* if(identTypeJoint == '0' || identTypeJoint == '7' || identTypeJoint == '3' 
										|| identTypeJoint == 'X14' || identTypeJoint == '1'//境内
										|| identTypeJoint == '5' || identTypeJoint == '6' || identTypeJoint == '2' 
											|| identTypeJoint == '8' || identTypeJoint == 'X5' || identTypeJoint == 'X3'//境外
											){*/
								   if(!jnwType){//后期添加的证件类型
									   hideCardPanel();
									   if(isChecked == true){
											for(var t in jnwAccountType){
												var currCode = jnwAccountType[t];//D F
												if(currCode == selCode){
													var checkboxList = document.getElementsByName(currCode+"Checkbox");
													for(var kk = 0; kk <checkboxList.length; kk++){
														checkboxList[kk].disabled = false;
													}
												}else{
													var checkboxList = document.getElementsByName(currCode+"Checkbox");
													for(var kk = 0; kk <checkboxList.length; kk++){
														checkboxList[kk].disabled = true;
														checkboxList[kk].checked = false;
													}
												}
											}
										}
								   }
								},
								'disable' : function(){
				            		if(this.el){//调整样式
				            			this.el.up("div").removeClass("x-item-disabled");
					            		this.el.up("div").setStyle({
					            			color : "#555",
					            			cursor: "default",
					            			opacity: 1
					            		});
				            		}
				            	}
						  }
		    		});
		    		//境内或境外容器面板
		    		var jnwPanel = new Ext.Panel({
		    			baseCls:'',
			    		layout:"column",
			    		width:400,
			    		items :radioItems
			    	});
		    		//遍历当前类型下的可选账户类型，并生成复选框控件
		    		var checkboxItems = [];
		    		for(var k in items){
		    			if(k=='remove'){
			    			continue;
			    		}
		    			var checkItem = items[k];
		    			var checkValue = checkItem.value;
		    			var checkCode = checkItem.code;
		    			
		    			if(checkValue!=undefined){
		    				checkboxItems.push({
		    					xtype:"checkbox",
					            inputValue:checkCode,
					            id: code+checkCode,
					            name : code+'Checkbox',
					            boxLabel: convertFontCss(checkValue),
					            style: 'margin-left:30px;',
					            checked:false,
					            listeners : {
					            	'afterrender'	: function(){
					            		var el = this.el;
					            		if(el){
					            			el.up("div").removeClass("x-item-disabled");
						            		el.up("div").setStyle({
						            			color : "#555",
						            			cursor: "default",
						            			opacity: 1
						            		});
					            		}
					            		this.el.on("click",function(){
					            			var obj = this;
					            			//var ischeck = this.getAttribute("checked");
					            			var ischeck = this.dom.checked;
					            			if(ischeck == true){
							            		if((obj.id == 'DK' ||  obj.id == 'FH') && visitJson.jointaccount == 0){
							            			showCardPanel();
							            		}
							            	}else{
							            		if((obj.id == 'DK' ||  obj.id == 'FH') && visitJson.jointaccount == 0){
							            			hideCardPanel();
							            		}
							            	}
					            		});
					            	},
					            	'disable' : function(){
					            		if(this.el){
					            			this.el.up("div").removeClass("x-item-disabled");
						            		this.el.up("div").setStyle({
						            			color : "#555",
						            			cursor: "default",
						            			opacity: 1
						            		});
					            		}
					            	}
					            }
		    				});
		    			}
		    		}
		    		//可选账户类型容器面板
		    		var jnwCusCheckboxGroup = new Ext.form.CheckboxGroup({    
		    				xtype: 'checkboxgroup',
					       vertical : true,   
					       columns : 1,   
					       width:400,
					       items : checkboxItems,
					       id : code+'Checkbox'
		    		});
		    		//可选账户类型面板添加到类型面板下面
		    		jnwPanel.add(jnwCusCheckboxGroup);
		    		accountInfo.add(jnwPanel);
	    		}
	    	}
	    	//查询证件、境内外、账户类型的校验规则
	    	Ext.Ajax.request({
				url : basepath + "/oneKeyAccountAction!checkAccountAuth.json",
				method : 'GET',
				success : function(response){
					var result = Ext.decode(response.responseText);
					crmData.resultMap1 = result.JSON.resultMap1;//开户证件-境内外关系
					crmData.resultMap2 = result.JSON.resultMap2;//境内外-账户关系
					crmData.resultMap3 = result.JSON.resultMap3;//账户-银行服务关系
					accountInfo.doLayout();	
					initLayout2();
				}
			});
	    }
	 });
}

/**
 * 显示开卡
 */
function showCardPanel(){
	Ext.getCmp("jiejika").setVisible(true);//借记卡申请
	Ext.getCmp("dianziBank").setVisible(true);//电子银行服务
	Ext.getCmp("elecState").setVisible(true);//电子对账单
}

function hideCardPanel(){
	Ext.getCmp("jiejika").setVisible(false);//借记卡申请
	Ext.getCmp("jiejika").setValue(false);
	Ext.getCmp("dianziBank").setVisible(false);//电子银行服务
	Ext.getCmp("dianziBank").setValue(false);
	 Ext.getCmp("EMAIL").allowBlank = true;
	Ext.getCmp("elecState").setVisible(false);//电子对账单
	Ext.getCmp("elecState").setValue(false);
	
	Ext.getCmp("cardType").setVisible(false);
	Ext.getCmp("cardType").setValue("");
	Ext.getCmp("cardType").allowBlank = true;
	Ext.getCmp("cardType1_0").setVisible(false);
	Ext.getCmp("cardType1_0").setValue("");
	Ext.getCmp("cardType1_0").allowBlank = true;
	Ext.getCmp("cardType2_0").setVisible(false);
	Ext.getCmp("cardType2_0").setValue("");
	Ext.getCmp("cardType2_0").allowBlank = true;
	Ext.getCmp('ATM').setVisible(false);
	Ext.getCmp('ATMDayLimitDefault').setValue(true);
	Ext.getCmp('ATMDayLimitDefine').setValue(false);
	Ext.getCmp('ATMDayLimit').setValue("");
	Ext.getCmp('ATMDayLimitPanel').setVisible(false);

	Ext.getCmp('ATMDayCountDefault').setValue(true);
	Ext.getCmp('ATMDayCountDefine').setValue(false);
	Ext.getCmp('ATMDayLimitCount').setValue("");
	Ext.getCmp('ATMDayCountPanel').setVisible(false);

	Ext.getCmp('ATMYearLimitDefault').setValue(true);
	Ext.getCmp('ATMYearLimitDefine').setValue(false);
	Ext.getCmp('ATMYearLimit').setValue("");
	Ext.getCmp('ATMYearLimitPanel').setVisible(false);
	Ext.getCmp('POS').setVisible(false);

	Ext.getCmp('POSDefault').setValue(true);
	Ext.getCmp('POSDefine').setValue(false);
	Ext.getCmp('eachCustemLimit').setValue("");
	Ext.getCmp('POSPanel').setVisible(false);
	
    Ext.getCmp("netBank").setVisible(false);
    Ext.getCmp("netBank").setValue(false);
    Ext.getCmp("ukeyPanel").setVisible(false);
    Ext.getCmp("ukey").setValue(false);
    Ext.getCmp("shortMessagePanel").setVisible(false);
    Ext.getCmp("shortMessage").setValue(false);
    Ext.getCmp("mobileBank").setVisible(false);
    Ext.getCmp("mobileBank").setValue(false);
    Ext.getCmp("shortMessage2Panel").setVisible(false);
    Ext.getCmp("shortMessage2").setValue(false);

    Ext.getCmp("dayAccLimitDefault").setValue(true);
    Ext.getCmp("dayAccLimitDefine").setValue(false);
    Ext.getCmp("dayAccSelfDefine").setValue("");
    Ext.getCmp("dayAccLimitPanel").setVisible(false);

    Ext.getCmp("dayAccCountDefault").setValue(true);
    Ext.getCmp("dayAccCountDefine").setValue(false);
    Ext.getCmp("dayCountSelfDefine").setValue("");
    Ext.getCmp("dayAccCountPanel").setVisible(false);

    Ext.getCmp("yearAccLimitDefault").setValue(true);
    Ext.getCmp("yearAccLimitDefine").setValue(false);
    Ext.getCmp("yearAccSelfDefine").setValue("");
    Ext.getCmp("yearAccLimitPanel").setVisible(false);
   
    Ext.getCmp('email').setValue("");
    Ext.getCmp('email').allowBlank = true;
    Ext.getCmp('isEquEmail').setValue(false);
    Ext.getCmp('elecStatePanel').setVisible(false);
}

/**
 * 右下角显示提示信息
 * @param {} msg 提示信息
 * @param {} error
 * @param {} hideDelay
 */
var showMsgNotification = function(msg, hideDelay, error) {
	new Ext.ux.Notification( {
		iconCls : 'ico-g-51',
		title : error ? '<font color=red>错误</font>' : '<font color=red>提示</font>',
		html : "<br><font style='font-weight:bold;' color=red text-align=left>" + msg
				+ '</font><br><br>',
		autoDestroy : true,
		plain : false,
		shadow : false,
		draggable : false,
		hideDelay : hideDelay || (error ? 30000 : 30000),
		width : 400
	}).show(document);
};
