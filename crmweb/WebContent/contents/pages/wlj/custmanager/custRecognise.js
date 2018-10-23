/**
  * @description 客户识别
  * @author likai
  * @since 2014-09-19
  * 
  **/

imports([
	'/contents/pages/common/Com.yucheng.crm.common.ImpExpNew.js'
]);

Ext.QuickTips.init();//提示信息

var url = basepath + '/acrmACiCustRecognise.json';
var comitUrl = basepath + '/acrmACiCustRecognise!save.json';

var needCondition = true;
var needGrid = true;
var createView = true;
var editView = true;
var detailView = true;

// 复选框
var sm = new Ext.grid.CheckboxSelectionModel();
   
// 定义自动当前页行号
var rownum = new Ext.grid.RowNumberer({
	header : 'No.',
	width : 28
});

var lookupTypes = [
	'IF_FLAG',
	'XD000353'
];

var fields = [
	{name: 'CUST_ID', text: '客户编号',dataType: 'string', searchField: true, xtype: 'textfield',readOnly:true,cls:'x-readOnly'},
	{name: 'CUST_NAME', text: '客户名',dataType: 'string', searchField: true, allowBlank: false},
	{name: 'NICK_NAME', text: '昵称',dataType: 'string'},
	{name: 'IS_PRIV_BANK_CUST', text: '是否私人银行', translateType: 'IF_FLAG'},
	{name: 'IS_HUGE', text: '是否大额客户', translateType: 'IF_FLAG'},
	{name: 'NEEDS_LAST', text: '前次需求',dataType: 'string'},
	{name: 'AVOID', text: '客户禁忌',dataType: 'string'},
	{name: 'DRINK', text: '客户饮料喜好',dataType: 'string'},
	{name: 'BARCODE', text: '二维码/条形码',dataType: 'string'},
	{name: 'CARD_CODE', text: '银行卡(磁条)',dataType: 'string'},
	{name: 'CARD_IC', text: '银行卡(IC)',dataType: 'string'},
	{name: 'CARD_NFC', text: '银行卡(NFC)',dataType: 'string'},
	{name: 'BANKBOOK', text: '存折',dataType: 'string'},
	{name: 'CUS_FINGERPRINT', text: '指纹',dataType: 'string'},
	{name: 'CUS_FACE_IDENT', text: '人脸识别',dataType: 'string'},
	{name: 'CUS_VEIN', text: '静脉',dataType: 'string'},
	{name: 'NOTE', text: '备忘录显示', xtype: 'textarea', maxLength: 200},
	{name: 'BACK_MRK', text: '黑名单及难缠客户备注', xtype: 'textarea', maxLength: 400},
	{name: 'CUST_SOURCE', text: '业务来源', translateType: 'XD000353'},
	{name: 'CUS_CONS', text: '星座',dataType: 'string'},
	{name: 'COME_DATE', text: '历史来行日期列表',dataType: 'date'}
];

var createFormViewer = [{
	fields:['CUST_ID','CUST_NAME','NICK_NAME','IS_PRIV_BANK_CUST','IS_HUGE','NEEDS_LAST','AVOID','DRINK','BARCODE','CARD_CODE','CARD_IC','CARD_NFC',
			'BANKBOOK','CUS_FINGERPRINT','CUS_FACE_IDENT','CUS_VEIN','CUST_SOURCE','CUS_CONS','COME_DATE','NOTE','BACK_MRK'],
	fn:function(CUST_ID,CUST_NAME,NICK_NAME,IS_PRIV_BANK_CUST,IS_HUGE,NEEDS_LAST,AVOID,DRINK,BARCODE,CARD_CODE,CARD_IC,CARD_NFC,
			BANKBOOK,CUS_FINGERPRINT,CUS_FACE_IDENT,CUS_VEIN,CUST_SOURCE,CUS_CONS,COME_DATE,NOTE,BACK_MRK){
				CUST_ID.hidden = true;
		return[CUST_ID,CUST_NAME,NICK_NAME,IS_PRIV_BANK_CUST,IS_HUGE,NEEDS_LAST,AVOID,DRINK,BARCODE,CARD_CODE,CARD_IC,CARD_NFC,
			BANKBOOK,CUS_FINGERPRINT,CUS_FACE_IDENT,CUS_VEIN,CUST_SOURCE,CUS_CONS,COME_DATE,NOTE,BACK_MRK]
	}
}];

var editFormViewer = [{
	fields:['CUST_ID','CUST_NAME','NICK_NAME','IS_PRIV_BANK_CUST','IS_HUGE','NEEDS_LAST','AVOID','DRINK','BARCODE','CARD_CODE','CARD_IC','CARD_NFC',
			'BANKBOOK','CUS_FINGERPRINT','CUS_FACE_IDENT','CUS_VEIN','CUST_SOURCE','CUS_CONS','COME_DATE','NOTE','BACK_MRK'],
	fn:function(CUST_ID,CUST_NAME,NICK_NAME,IS_PRIV_BANK_CUST,IS_HUGE,NEEDS_LAST,AVOID,DRINK,BARCODE,CARD_CODE,CARD_IC,CARD_NFC,
			BANKBOOK,CUS_FINGERPRINT,CUS_FACE_IDENT,CUS_VEIN,CUST_SOURCE,CUS_CONS,COME_DATE,NOTE,BACK_MRK){
		return[CUST_ID,CUST_NAME,NICK_NAME,IS_PRIV_BANK_CUST,IS_HUGE,NEEDS_LAST,AVOID,DRINK,BARCODE,CARD_CODE,CARD_IC,CARD_NFC,
			BANKBOOK,CUS_FINGERPRINT,CUS_FACE_IDENT,CUS_VEIN,CUST_SOURCE,CUS_CONS,COME_DATE,NOTE,BACK_MRK]
	}
}];

var detailFormViewer = [{
	fields:['CUST_ID','CUST_NAME','NICK_NAME','IS_PRIV_BANK_CUST','IS_HUGE','NEEDS_LAST','AVOID','DRINK','BARCODE','CARD_CODE','CARD_IC','CARD_NFC',
			'BANKBOOK','CUS_FINGERPRINT','CUS_FACE_IDENT','CUS_VEIN','CUST_SOURCE','CUS_CONS','COME_DATE','NOTE','BACK_MRK'],
	fn:function(CUST_ID,CUST_NAME,NICK_NAME,IS_PRIV_BANK_CUST,IS_HUGE,NEEDS_LAST,AVOID,DRINK,BARCODE,CARD_CODE,CARD_IC,CARD_NFC,
			BANKBOOK,CUS_FINGERPRINT,CUS_FACE_IDENT,CUS_VEIN,CUST_SOURCE,CUS_CONS,COME_DATE,NOTE,BACK_MRK){
				CUST_ID.readOnly = true;
				CUST_ID.cls = 'x-readOnly';
				CUST_NAME.readOnly = true;
				CUST_NAME.cls = 'x-readOnly';
				NICK_NAME.readOnly = true;
				NICK_NAME.cls = 'x-readOnly';
				IS_PRIV_BANK_CUST.readOnly = true;
				IS_PRIV_BANK_CUST.cls = 'x-readOnly';
				IS_HUGE.readOnly = true;
				IS_HUGE.cls = 'x-readOnly';
				NEEDS_LAST.readOnly = true;
				NEEDS_LAST.cls = 'x-readOnly';
				AVOID.readOnly = true;
				AVOID.cls = 'x-readOnly';
				DRINK.readOnly = true;
				DRINK.cls = 'x-readOnly';
				BARCODE.readOnly = true;
				BARCODE.cls = 'x-readOnly';
				CARD_CODE.readOnly = true;
				CARD_CODE.cls = 'x-readOnly';
				CARD_IC.readOnly = true;
				CARD_IC.cls = 'x-readOnly';
				CARD_NFC.readOnly = true;
				CARD_NFC.cls = 'x-readOnly';
				BANKBOOK.readOnly = true;
				BANKBOOK.cls = 'x-readOnly';
				CUS_FINGERPRINT.readOnly = true;
				CUS_FINGERPRINT.cls = 'x-readOnly';
				CUS_FACE_IDENT.readOnly = true;
				CUS_FACE_IDENT.cls = 'x-readOnly';
				CUS_VEIN.readOnly = true;
				CUS_VEIN.cls = 'x-readOnly';
				NOTE.readOnly = true;
				NOTE.cls = 'x-readOnly';
				BACK_MRK.readOnly = true;
				BACK_MRK.cls = 'x-readOnly';
				CUST_SOURCE.readOnly = true;
				CUST_SOURCE.cls = 'x-readOnly';
				CUS_CONS.readOnly = true;
				CUS_CONS.cls = 'x-readOnly';
				COME_DATE.readOnly = true;
				COME_DATE.cls = 'x-readOnly';
		return[CUST_ID,CUST_NAME,NICK_NAME,IS_PRIV_BANK_CUST,IS_HUGE,NEEDS_LAST,AVOID,DRINK,BARCODE,CARD_CODE,CARD_IC,CARD_NFC,
			BANKBOOK,CUS_FINGERPRINT,CUS_FACE_IDENT,CUS_VEIN,CUST_SOURCE,CUS_CONS,COME_DATE,NOTE,BACK_MRK]
	}
}];

var tbar = [{
	text: '删除',
	handler:function(){
		if(getSelectedData() == false){
			Ext.Msg.alert('提示','请至少选择一条数据');
			return false;
		}else{
			Ext.MessageBox.confirm('提示','确定删除吗？',function(buttonId){
				if(buttonId.toLowerCase() == "no"){
					return false;
				}
				var selectRecords = getAllSelects();
				var custId = '';
				for (var i = 0; i < selectRecords.length; i++) {
					if(i == 0){
						custId =  selectRecords[i].data.CUST_ID;
					}else{
						custId += ","+ selectRecords[i].data.CUST_ID;
					}
				}
				Ext.Ajax.request({
					url: basepath + '/acrmACiCustRecognise!batchDel.json',
					params: {
						custId : custId
					},
					success: function(){
						Ext.Msg.alert('提示','删除成功！');
						reloadCurrentData();
					},
					failure: function(){
						Ext.Msg.alert('提示','删除失败！');
						reloadCurrentData();
					}
				});
			});
		}
	}
},new Com.yucheng.crm.common.NewExpButton({
	formPanel: 'searchCondition',
	url: basepath + '/acrmACiCustRecognise.json'
	})
];

var beforeviewshow = function(view){
	if(view == getEditView() || view == getDetailView()){
		if(getSelectedData() == false){
			Ext.Msg.alert('提示','请选择一条数据');
			return false;
		}
	}
}