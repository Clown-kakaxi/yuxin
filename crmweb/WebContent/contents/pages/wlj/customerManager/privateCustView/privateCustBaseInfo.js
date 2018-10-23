/**
 * 
 * @description 零售客户基本信息
 * @author wangmk1
 * @since 2014-07-28
 */
imports([
    '/contents/pages/com.yucheng.bcrm/com.yucheng.bcrm.js',
	'/contents/pages/common/Com.yucheng.crm.common.ImpExpNew.js',
	'/contents/pages/common/Com.yucheng.bcrm.common.BusiType.js',
	'/contents/pages/wlj/customerManager/privateCustView/perLookup.js', //所有数据字典定义
	'/contents/pages/wlj/customerManager/queryAllCustomer/updateHisCust.js',
	'/jQuery/js/jquery-1.11.3.min.js'
	
]);

Ext.QuickTips.init();

var custId = _custId;
var custName = "";

var needGrid = false;
WLJUTIL.suspendViews=false;  //自定义面板是否浮动
var fields = [{name:'XX',text:'XX'}];

/**数据字典**/
//本地数据字典定义
var localLookup = {
   	//是否按揭
	'IS_MOGGG' : [
   		{key : '1',value : '是'},
		{key : '0',value : '否'}
   	],
   	//联络频率
   	'REN_RAKU' : [
   		{key : 'A',value : '每月一次'},
		{key : 'B',value : '每季一次'},
		{key : 'C',value : '半年一次'},
		{key : 'D',value : '一年一次'}
   	],
   	//账户经营策略
   	'ACC_STRA' : [
   		{key : 'A',value : '保留'},
		{key : 'B',value : '升级'},
		{key : 'C',value : '退出/降级'},
		{key : 'D',value : '重新评估'}
   	]
};

var record = Ext.data.Record.create([
  //基本信息  ACRM_F_CI_CUSTOMER
   {name:'CUST_ID'}
  ,{name:'RISK_LEVEL'}
  ,{name:'CUST_NAME'}
  ,{name:'SHORT_NAME'}
  ,{name:'EN_NAME'}
  ,{name:'IDENT_TYPE'}
  ,{name:'IDENT_NO'}
  ,{name:'INDUST_TYPE'}
  ,{name:'INDUST_TYPE_NAME'}
  //基本信息  ACRM_F_CI_PERSON
  ,{name:'GENDER'}
  ,{name:'PERSON_TITLE'}
  ,{name:'BIRTHDAY'}
  ,{name:'BIRTHLOCALE'}
  ,{name:'NATIONALITY'}
  ,{name:'PINYIN_NAME'}
  ,{name:'RISK_DATE'}
  ,{name:'DUTY'}
  ,{name:'CAREER_TYPE'}
  ,{name:'HIGHEST_SCHOOLING'}
  ,{name:'GRADUATE_SCHOOL'}
  ,{name:'MARRIAGE'}
  ,{name:'CITIZENSHIP'}
  ,{name:'ANNUAL_INCOME'}
  ,{name: 'FAMILY_ANN_INC_SCOPE'}
  ,{name: 'FAMILY_ID'}
  //配偶信息 取值于家庭信息表 ACRM_F_CI_PER_FAMILIES
  ,{name:'MEMBERNAME'}//成员名称
  ,{name:'TEL'}//电话
  ,{name:'MOBILE'}//手机号码
  ,{name:'MXTID'}//成员编号
  ,{name: 'CORE_NO'}//成员核心号
  //黑名单信息   取值于特殊名单ACRM_F_CI_SPECIALLIST
  ,{name:'SPECIAL_LIST_FLAG'}//特殊名单标志
  ,{name:'ENTER_REASON'}//列入原因
  ,{name: 'START_DATE'}//起始日期
  
  //地址信息 ACRM_F_CI_PERSON
  ,{name:'UNIT_ADDR'}
  ,{name:'UNIT_ZIPCODE'}
  ,{name:'UNIT_TEL'}
  ,{name:'HOME_ADDR'}
  ,{name:'HOME_ZIPCODE'}
  ,{name:'HOME_TEL'}
  ,{name:'MOBILE_PHONE'}
  ,{name:'EMAIL'}
  ,{name:'WEIXIN'}
  ,{name:'UNIT_NAME'}
  ,{name:'UNIT_FEX'}
  ,{name:'PREFERENCE_ID'}
  //个人客户重要标志表 ACRM_F_CI_PER_KEYFLAG
  ,{name:'KEY_CUST_ID'}
  ,{name:'INOUT_FLAG'}	//ACRM_F_CI_CUSTOMER
  ,{name:'IS_ON_JOB_WORKER'}
  ,{name:'IS_PAYROLL_CUST'}
  ,{name:'USA_TAX_FLAG'}
  ,{name:'USTIN'}	//ACRM_F_CI_PERSON
  ,{name:'IS_DIVIDEND_CUST'}
  ,{name:'HAS_PHOTO'}
  //个人客户偏好信息表 ACRM_F_CI_PER_PREFERENCE
  ,{name:'RECEIVE_SMS_FLAG'}
  //个人客户表
  ,{name:'LAST_DEALINGS_DESC'}
  //客户表
  ,{name:'FAXTRADE_NOREC_NUM'}
  ,{name:'SOURCE_CHANNEL'}
  //客户满意度调查OCRM_F_SE_CUST_SATISFY_LIST
  ,{name:'SATISFY_TYPE'}
  //贵宾客户信息ACRM_A_CI_VIP_CUST
  //,{name:'H_CARD_LEV'}
  //,{name:'CARD_LVL'}
  //,{name:'H_CARD_TYPE'}  
  //,{name:'IS_VALIDATE'}
  ,{name:'HOLD_CARD_LEVEL'}//可发卡等级
  ,{name:'HIGHEST_CARD_LEVEL'}//当前持有最高等级贵宾卡等级
  ,{name:'IS_STANDARD'}//是否有效贵宾
  //ORCM_F_CI_CARD_INFO借记卡信息表  
  ,{name:'CB_CUST_ID'}
  ,{name:'GET_WAY'}
  ,{name:'SENT_ADDRESS'}
  ,{name:'IS_STANDARD'}
  ,{name:'CARD_APPER'}
  //财务信息
  ,{name:'FIN_ID'}
  ,{name:'IS_HOUSE_PLAN'}
  ,{name:'IS_EDU_PLAN'}
  ,{name:'IS_RETIRE_PLAN'}
  ,{name:'ASSET_AMT'}
  ,{name:'FINA_INFO_IN'}
  ,{name:'IS_MORT'}
  ,{name:'HOUSE_INFO'}
  ,{name:'ASSET_INFO_OUT'}
  //策略信息
  ,{name:'ACC_RUN_STRATEGY'}
  ,{name:'PROD_ADVICE'}
  ,{name:'SERVICE_ATTENTION'}
  //联络频率
  ,{name:'CONTACT_FREQ_PREFER'}
  
  ,{name:'IS_VIP_CUST'}
  ,{name:'IS_FLIGHT_INSURANCE'}
]);
  
//照片上传
var uploadForm = new Ext.form.FormPanel({
	frame : true,
	fileUpload : true,//这个属性可以让form实现上传
	uploadFile:true,
	style : 'margin:10px',
	items : [{
		xtype : 'box',
		labelAlign:'right',
		width:200,
		height:300,
		name : 'photo',
		id : 'photo',
		autoEl : {
			style : 'width:150px;height:150px;margin:0px auto;border:1px solid #ccc; text-align:center;vertical-align:middle;',
			tag : 'div',
			id : 'imageshow',
			html : '暂无图片'
		}
	}, {
		xtype: 'textfield',
		name: 'image',
		id:'upload',
		width:220,
		fieldLabel: '文件上传',
		inputType: 'file',
		allowBlank: false,
		blankText: '请选择图片'
	}],
	buttonAlign:'right',
	buttons : [{
		text : '上传',
		handler : function() {
			//限制图片格式
			var local=Ext.getCmp('upload').getEl().getValue();
			var t_ext=local.substring(local.lastIndexOf('.'));
			var arr=new Array(".jpg", ".jpeg", ".gif", ".png");
			var i=0;
			for(;i<arr.length;i++){
				if(t_ext==arr[i]){
					break;
				}
			}
			if(i==arr.length){
				Ext.MessageBox.alert("提示", '上传文件格式必须为:"jpg", "jpeg", "gif", "png"！');
				return false;
			}
			if (uploadForm.getForm().isValid()) {
				uploadForm.getForm().submit({
					url : basepath + '/FileUpload?isImage=isImage', // 上传图片处理 ,
					waitTitle : "请稍候",
					waitMsg : '正在上传...',
					success : function(form, o) {
						Ext.MessageBox.alert("提示", "上传成功！");
						var data=o.response.responseText;
						_tempFileName = Ext.util.JSON.decode(o.response.responseText).realFileName;
						Ext.Ajax.request({
							url:basepath+'/privateBaseInfo!savaImage.json',//保存图片信息
							method:'post',
							params:{
								custId:_custId,
								_tempFileName:_tempFileName
							},
							success:function(response){
								custBusinessPanel.getForm().load({
									restful : true,
									url: basepath + '/privateCustInfo!searchBusiInfo.json',//刷新form内容
									method:'get',
									params : {
										'custId':_custId
								    }
								});
							},
							failure: function(response){
								Ext.Msg.alert('提示', '操作失败'+response.responseText);
							}
						});
						document.getElementById('imageshow').innerHTML = '<img " src="'
							+ basepath+ '/imgshow.json?t='+new Date().getTime()+'&path='+_tempFileName+'"' +'width=200px height=300px />';
						Ext.getCmp('image').getEl().dom.src = basepath+ '/imgshow.json?t='+new Date().getTime()+'&path='+_tempFileName ;
					},
					failure : function() {
						Ext.MessageBox.alert("提示", "上传失败！");
					}
				});
			}
		}
	}]
});
		
var uploadWin = new Ext.Window({
	title : '照片上传',
	width : 476,
	height : 474,
	resizable : true,
	modal : true,
	closable : true,
	maximizable : true,
	minimizable : true,
	closeAction: 'hide',
	buttonAlign : 'center',
	items : [uploadForm]
});


   
var basreader = new Ext.data.JsonReader({
	successProperty : 'success',
	idProperty : 'custId',
	messageProperty : 'message',
	root : 'data'
}, record);
var reader = new Ext.data.JsonReader( {
	successProperty : 'success',
	idProperty : 'custId',
	messageProperty : 'message',
	root : 'data'
}, record);
var conreader = new Ext.data.JsonReader( {
	successProperty : 'success',
	idProperty : 'custId',
	messageProperty : 'message',
	root : 'data'
}, record);
var vipreader = new Ext.data.JsonReader( {
	successProperty : 'success',
	idProperty : 'custId',
	messageProperty : 'message',
	root : 'data'
}, record);
debugger;
var custBaseInfoPanel=new Ext.FormPanel({
	title : '客户信息',
	reader : basreader,
	collapsible : true,
	autoHeight:true,
	autoWidth:true,
	labelWidth:200,//label的宽度
	labelAlign:'right',
	frame:false,
	autoScroll : true,
	buttonAlign:'center',
	anchor:'95%',
	items : [ {
		xtype : 'fieldset',
		title : '基本信息',
		titleCollapse : true,
		collapsible : true,
//		autoHeight:true,
		items:[{
			layout:'column',
			items:[{
				columnWidth:.5,  
				layout:'form',
				items:[
					 {xtype:'textfield',anchor:'90%',fieldLabel:'客户编号',name:'CUST_ID',cls:'x-readOnly',readOnly:true},
//		             {xtype:'combo',anchor:'90%',fieldLabel:'风险等级',name:'RISK_LEVEL',cls:'x-readOnly',readOnly:true,
//		        		store:riskStore,valueField : 'key',displayField : 'value',mode : 'local',
//		              typeAhead : true,forceSelection : true,triggerAction : 'all',emptyText : '未知',
//		              labelStyle:'text-align:right;',selectOnFocus : true}	,
//		              {xtype:'datefield',anchor:'90%',labelStyle:'text-align:right;',format : 'Y-m-d',fieldLabel:'风险等级有效期',name:'RISK_DATE',cls:'x-readOnly',readOnly:true},
		              {xtype:'textfield',anchor:'90%',fieldLabel:'客户名称',name:'CUST_NAME',cls:'x-readOnly',readOnly:true},
		              {xtype:'textfield',anchor:'90%',fieldLabel:'客户简称',name:'SHORT_NAME',maxLength:40},
		              {xtype:'textfield',anchor:'90%',fieldLabel:'客户英文名称',name:'EN_NAME',cls:'x-readOnly',readOnly:true}, 
		              {xtype:'textfield',anchor:'90%',fieldLabel:'客户拼音名',name:'PINYIN_NAME',maxLength:40},
		              {xtype:'combo',anchor:'90%',fieldLabel:'证件类型',name:'IDENT_TYPE',cls:'x-readOnly',readOnly:true,
		               store:cardTypeStore,valueField : 'key',displayField : 'value',mode : 'local',
		              typeAhead : true,forceSelection : true,triggerAction : 'all',emptyText : '未知',
		              labelStyle:'text-align:right;',selectOnFocus : true}	,
		              {xtype:'textfield',anchor:'90%',fieldLabel:'证件号码',name:'IDENT_NO',cls:'x-readOnly',readOnly:true},	
		              {xtype:'combo',anchor:'90%',fieldLabel:'最高学历',name:'HIGHEST_SCHOOLING',
		                 store:educationStore,valueField : 'key',displayField : 'value',mode : 'local',
		              typeAhead : true,forceSelection : true,triggerAction : 'all',emptyText : '未知',
		              labelStyle:'text-align:right;',selectOnFocus : true}	,
		              {xtype:'textfield',anchor:'90%',fieldLabel:'毕业学校',name:'GRADUATE_SCHOOL',maxLength:40},
		              {xtype:'combo',anchor:'90%',fieldLabel:'国籍',name:'CITIZENSHIP',cls:'x-readOnly',readOnly:true,
		              store:citizenshipStore,valueField : 'key',displayField : 'value',mode : 'local',
		              typeAhead : true,forceSelection : true,triggerAction : 'all',emptyText : '未知',
		              labelStyle:'text-align:right;',selectOnFocus : true},
		              {xtype:'combo',anchor:'90%',fieldLabel:'婚姻状态',name:'MARRIAGE',
		              store:marriageStore,valueField : 'key',displayField : 'value',mode : 'local',
		              typeAhead : true,forceSelection : true,triggerAction : 'all',emptyText : '未知',
		              labelStyle:'text-align:right;',selectOnFocus : true},
		              {xtype:'combo',anchor:'90%',fieldLabel:'职业',name:'CAREER_TYPE',
		              	store:occupationStore,valueField : 'key',displayField : 'value',mode : 'local',
		              typeAhead : true,forceSelection : true,triggerAction : 'all',emptyText : '未知',
		              labelStyle:'text-align:right;',selectOnFocus : true},
		              {xtype:'textfield',anchor:'90%',fieldLabel:'家庭表ID',name:'FAMILY_ID',hidden:true},
		              {xtype:'combo',anchor:'90%',fieldLabel:'家庭年收入',name:'FAMILY_ANN_INC_SCOPE',
		              	store:fmyAnnIncStore,valueField : 'key',displayField : 'value',mode : 'local',
		              typeAhead : true,forceSelection : true,triggerAction : 'all',emptyText : '未知',
		              labelStyle:'text-align:right;',selectOnFocus : true},
		              {xtype:'numberfield',anchor:'90%',fieldLabel:'个人年收入(万元)',name:'ANNUAL_INCOME'},
		              {xtype:'textfield',anchor:'90%',fieldLabel:'配偶姓名',name:'MEMBERNAME',cls:'x-readOnly',readOnly:true},
		              {xtype:'textfield',anchor:'90%',fieldLabel:'配偶移动电话',name:'TEL',cls:'x-readOnly',readOnly:true},
		              {xtype:'textfield',anchor:'90%',fieldLabel:'配偶联系电话',name:'MOBILE',cls:'x-readOnly',readOnly:true},
		              {xtype:'textfield',anchor:'90%',fieldLabel:'配偶对应客户号',name:'MXTID',cls:'x-readOnly',readOnly:true},
		              {xtype:'textfield',anchor:'90%',fieldLabel:'配偶对应核心客户号',name:'CORE_NO',cls:'x-readOnly',readOnly:true}
		          ]
		    },{
				columnWidth:.5,  
				layout:'form',
				items:[
				      {xtype: 'panel',fieldLabel:'用户照片',id: 'custImage',name:'PHOTO', width: 300, height: 230,
					      items:[{
				      		xtype: 'box',fieldLabel:'用户照片',id: 'image',width: 160, height: 200,
				      		autoEl: {
           						 tag: 'img',
           						 src:''
        					}
				     	},{
							width : '160',
							xtype : 'button',
							text : "上传照片",
							hidden:JsContext.checkGrant('privateBaseInfo_pic'),
							handler:function(){
								uploadWin.show();
							}
						}]
					},
		              {xtype : 'combo', anchor:'90%',store:sexStore,fieldLabel:'性别',name:'GENDER',fieldLabel :'性别',valueField : 'key',displayField : 'value',mode : 'local',
					   	typeAhead : true,forceSelection : true,triggerAction : 'all',emptyText : '请选择',labelStyle:'text-align:right;',selectOnFocus : true,cls:'x-readOnly',readOnly:true},
		              {xtype:'textfield',anchor:'90%',fieldLabel:'称谓',name:'PERSON_TITLE',maxLengths:10},    
		              {xtype:'datefield',anchor:'90%',labelStyle:'text-align:right;',format : 'Y-m-d',fieldLabel:'出生日期',name:'BIRTHDAY',cls:'x-readOnly',readOnly:true},
		              {xtype:'textfield',anchor:'90%',fieldLabel:'出生地',name:'BIRTHLOCALE',cls:'x-readOnly',readOnly:true},
		              {xtype:'combo',anchor:'90%',fieldLabel:'民族',name:'NATIONALITY',
		              	store:nationStore,valueField : 'key',displayField : 'value',mode : 'local',
		              	typeAhead : true,forceSelection : true,triggerAction : 'all',emptyText : '请选择',labelStyle:'text-align:right;',selectOnFocus : true}, 
		              {xtype:'combo',anchor:'90%',fieldLabel:'职务',name:'DUTY',
		                 store:postStore,valueField : 'key',displayField : 'value',mode : 'local',
		              	typeAhead : true,forceSelection : true,triggerAction : 'all',emptyText : '未知',
		              	labelStyle:'text-align:right;',selectOnFocus : true}, 
		              	new Com.yucheng.bcrm.common.BusiType({
	        				xtype : 'busiType',
	        				fieldLabel : '所属行业',
	        				name : 'INDUST_TYPE_NAME',
	        				hiddenName:'INDUST_TYPE',
	        				//readonly : true,
	        				//disabled:true,
	        				//cls:'x-readOnly',
	        				anchor : '90%'
	        			}),
		              {xtype:'combo',anchor:'90%',fieldLabel:'上本行黑名单标志',name:'SPECIAL_LIST_FLAG',cls:'x-readOnly',readOnly:true,
			              	store:ifStore,valueField : 'key',displayField : 'value',mode : 'local',
			              	typeAhead : true,forceSelection : true,triggerAction : 'all',emptyText : '未知',
			              	labelStyle:'text-align:right;',selectOnFocus : true},
					  {xtype:'datefield',anchor:'90%',labelStyle:'text-align:right;',format : 'Y-m-d',fieldLabel:'上本行黑名单日期',name:'START_DATE',cls:'x-readOnly',readOnly:true},
			          {xtype:'textarea',anchor:'90%',fieldLabel:'上黑名单原因',name:'ENTER_REASON',maxLength:200,cls:'x-readOnly',readOnly:true},
			          {xtype:'combo',anchor:'90%',fieldLabel:'是否农户',name:'FARMERS_FLAG',cls:'x-readOnly',readOnly:true,
			              	store:ifStore,valueField : 'key',displayField : 'value',mode : 'local',
			              	typeAhead : true,forceSelection : true,triggerAction : 'all',emptyText : '未知',
			              	labelStyle:'text-align:right;',selectOnFocus : true}
			       ]
			}]
		}]
	},{
		xtype : 'fieldset',
		title : '地址及联系信息',
		titleCollapse : true,
		collapsible : true,
		autoHeight : true,
		items:[{
			layout:'column',
			items:[{
				columnWidth:.5,  
				layout:'form',
				items:[
					   {xtype:'textfield',anchor:'90%',fieldLabel:'单位地址国家地区',name:'ADDW_NATION',cls:'x-readOnly',readOnly:true
//					   		listeners:{
//								blur : function(){
//									var reg = /^\D{1,20}$/;
//									var result = custBaseInfoPanel.getForm().findField('ADDW_NATION').getValue();
//									if(result == ""){
//										return false;
//									}
//									if(!reg.test(result)){
//										Ext.Msg.alert('提示','请输入正确格式的国家或地区！');
//										custBaseInfoPanel.getForm().findField('ADDW_NATION').setValue("")
//										return false;
//									}
//									var syuuseiA = result+"/"+custBaseInfoPanel.getForm().findField('UNITX_ADDR').getValue();
//									custBaseInfoPanel.getForm().findField('UNIT_ADDR').setValue(syuuseiA);
//								}
//							}
					   },
					   {xtype:'textfield',anchor:'90%',fieldLabel:'单位地址',name:'UNITX_ADDR',cls:'x-readOnly',readOnly:true
//					   		listeners:{
//								blur : function(){
//									var result = custBaseInfoPanel.getForm().findField('ADDW_NATION').getValue();
//									var syuuseiB = result+"/"+custBaseInfoPanel.getForm().findField('UNITX_ADDR').getValue();
//									custBaseInfoPanel.getForm().findField('UNIT_ADDR').setValue(syuuseiB);
//								}
//							}
					   }, 
					   {xtype:'textfield',anchor:'90%',fieldLabel:'储存单位地址',name:'UNIT_ADDR',hidden:true}, 
					   {xtype:'textfield',anchor:'90%',fieldLabel:'单位邮编',name:'UNIT_ZIPCODE',cls:'x-readOnly',readOnly:true},
					   {xtype:'textfield',anchor:'90%',fieldLabel:'单位名称',name:'UNIT_NAME',cls:'x-readOnly',readOnly:true},
					   {xtype:'textfield',anchor:'90%',fieldLabel:'单位电话',name:'UNIT_TEL',cls:'x-readOnly',readOnly:true},
					   {xtype:'textfield',anchor:'90%',fieldLabel:'传真号码',name:'UNIT_FEX',cls:'x-readOnly',readOnly:true},
					   {xtype:'textfield',anchor:'90%',fieldLabel:'EMAIL',name:'EMAIL',cls:'x-readOnly',readOnly:true},
					   {xtype:'textfield',anchor:'90%',fieldLabel:'微信',name:'WEIXIN',maxLength:50}
		              ]
				},{
				columnWidth:.5,  
				layout:'form',
				items:[
					   {xtype:'textfield',anchor:'90%',fieldLabel:'住宅地址国家地区',name:'ADDH_NATION',cls:'x-readOnly',readOnly:true
//					   		listeners:{
//								blur : function(){
//									var reg = /^\D{1,20}$/;
//									var result = custBaseInfoPanel.getForm().findField('ADDH_NATION').getValue();
//									if(result == ""){
//										return false;
//									}
//									if(!reg.test(result)){
//										Ext.Msg.alert('提示','请输入正确格式的国家或地区！');
//										custBaseInfoPanel.getForm().findField('ADDH_NATION').setValue("")
//										return false;
//									}
//									var syuuseiA = result+"/"+custBaseInfoPanel.getForm().findField('HOMEX_ADDR').getValue();
//									custBaseInfoPanel.getForm().findField('HOME_ADDR').setValue(syuuseiA);
//								}
//							}
					   },
				       {xtype:'textfield',anchor:'90%',fieldLabel:'住宅地址',name:'HOMEX_ADDR',cls:'x-readOnly',readOnly:true
//				       		listeners:{
//								blur : function(){
//									var result = custBaseInfoPanel.getForm().findField('ADDH_NATION').getValue();
//									var syuuseiB = result+"/"+custBaseInfoPanel.getForm().findField('HOMEX_ADDR').getValue();
//									custBaseInfoPanel.getForm().findField('HOME_ADDR').setValue(syuuseiB);
//								}
//							}
				       },
				       {xtype:'textfield',anchor:'90%',fieldLabel:'储存住宅地址',name:'HOME_ADDR',hidden:true},
				       {xtype:'textfield',anchor:'90%',fieldLabel:'住宅邮编',name:'HOME_ZIPCODE',cls:'x-readOnly',readOnly:true},
				       {xtype:'textfield',anchor:'90%',fieldLabel:'住宅国家地区区号',name:'TELH_NATION',cls:'x-readOnly',readOnly:true
//					   		listeners:{
//								blur : function(){
//									var reg = /^[0\+]\d{2,3}$/;
//									var result = custBaseInfoPanel.getForm().findField('TELH_NATION').getValue();
//									if(result == ""){
//										return false;
//									}
//									if(!reg.test(result)){
//										Ext.Msg.alert('提示','请输入正确格式的区号！');
//										custBaseInfoPanel.getForm().findField('TELH_NATION').setValue("")
//										return false;
//									}
//									var syuuseiA = result+"/"+custBaseInfoPanel.getForm().findField('HOMEX_TEL').getValue();
//									custBaseInfoPanel.getForm().findField('HOME_TEL').setValue(syuuseiA);
//								}
//							}
					   },
				       {xtype:'textfield',anchor:'90%',fieldLabel:'住宅电话',name:'HOMEX_TEL',cls:'x-readOnly',readOnly:true
//				       		listeners:{
//								blur : function(){
//									var result = custBaseInfoPanel.getForm().findField('TELH_NATION').getValue();
//									var syuuseiB = result+"/"+custBaseInfoPanel.getForm().findField('HOMEX_TEL').getValue();
//									custBaseInfoPanel.getForm().findField('HOME_TEL').setValue(syuuseiB);
//								}
//							}
				       },
				       {xtype:'textfield',anchor:'90%',fieldLabel:'储存住宅电话',name:'HOME_TEL',hidden:true},
				       {xtype:'textfield',anchor:'90%',fieldLabel:'手机国家地区区号',name:'TELM_NATION',cls:'x-readOnly',readOnly:true
//					   		listeners:{
//								blur : function(){
//									var reg = /^[0\+]\d{2,3}$/;
//									var result = custBaseInfoPanel.getForm().findField('TELM_NATION').getValue();
//									if(result == ""){
//										return false;
//									}
//									if(!reg.test(result)){
//										Ext.Msg.alert('提示','请输入正确格式的区号！');
//										custBaseInfoPanel.getForm().findField('TELM_NATION').setValue("")
//										return false;
//									}
//									var syuuseiA = result+"/"+custBaseInfoPanel.getForm().findField('MOBILEX_PHONE').getValue();
//									custBaseInfoPanel.getForm().findField('MOBILE_PHONE').setValue(syuuseiA);
//								}
//							}
					   },
				       {xtype:'textfield',anchor:'90%',fieldLabel:'手机号码',name:'MOBILEX_PHONE',cls:'x-readOnly',readOnly:true
//				       		listeners:{
//								blur : function(){
//									var result = custBaseInfoPanel.getForm().findField('TELM_NATION').getValue();
//									var syuuseiB = result+"/"+custBaseInfoPanel.getForm().findField('MOBILEX_PHONE').getValue();
//									custBaseInfoPanel.getForm().findField('MOBILE_PHONE').setValue(syuuseiB);
//								}
//							}
				       },
				       {xtype:'textfield',anchor:'90%',fieldLabel:'偏好表ID',name:'PREFERENCE_ID',hidden:true},
				       {xtype:'combo',anchor:'90%',fieldLabel:'联络频率',name:'CONTACT_FREQ_PREFER',mode : 'local',store: new Ext.data.Store(),triggerAction : 'all',displayField:'value',valueField:'key'},
				       {xtype:'textfield',anchor:'90%',fieldLabel:'储存手机号码',name:'MOBILE_PHONE',hidden:true}
			          ]
			}]
		}]
	},{
		xtype : 'fieldset',
		title : '其他信息',
		titleCollapse : true,
		collapsible : true,
		autoHeight : true,
		autoWidth:true,
		items:[{
			layout:'column',
			items:[{
				columnWidth:.5,  
				layout:'form',
				items:[
						{xtype:'combo',anchor:'90%',fieldLabel:'客户来源渠道',name:'SOURCE_CHANNEL',
						store:custSourceStore,valueField : 'key',displayField : 'value',mode : 'local',
		              typeAhead : true,forceSelection : true,triggerAction : 'all',emptyText : '未知',
		              labelStyle:'text-align:right;',selectOnFocus : true},
					   {xtype:'textfield',anchor:'90%',fieldLabel:'来源备注',name:'LAST_DEALINGS_DESC',maxLength:100},
					   {xtype:'textfield',anchor:'90%',fieldLabel:'传真交易正本未回收数量',name:'FAXTRADE_NOREC_NUM',vtype:'number',maxLength:40}
		              ]
				},{
				columnWidth:.5,  
				layout:'form',
				items:[
					 {xtype:'combo',anchor:'90%',fieldLabel:'是否境内居民',name:'INOUT_FLAG',cls:'x-readOnly',readOnly:true,
					 store:inoutStore,valueField : 'key',displayField : 'value',mode : 'local',
		              typeAhead : true,forceSelection : true,triggerAction : 'all',emptyText : '未知',
		              labelStyle:'text-align:right;',selectOnFocus : true},
				       {xtype:'combo',anchor:'90%',fieldLabel:'客户满意度',name:'SATISFY_TYPE',cls:'x-readOnly',readOnly:true,
				       store:satisfyStore,valueField : 'key',displayField : 'value',mode : 'local',
		              typeAhead : true,forceSelection : true,triggerAction : 'all',emptyText : '未知',
		              labelStyle:'text-align:right;',selectOnFocus : true}
			          ]
			}]
		}]
	}]
});


var custBusinessPanel=new Ext.FormPanel({
	title : '重要标志信息',
	reader : reader,
	collapsible : true,
	layout:'fit',
	autoHeight:true,
	autoWidth:true,
	labelWidth:200,//label的宽度
	labelAlign:'right',
	frame:false,
	autoScroll : true,
	border:false,
	buttonAlign:'center',
	items : [ {
		xtype : 'fieldset',
		title : '重要标志信息',
		titleCollapse : true,
		collapsible : true,
		autoHeight : true,
		autoWidth:true,
		items:[{
			layout:'column',
			items:[{
				columnWidth:.5,  
				layout:'form',
				items:[
		        	{xtype:'combo',anchor:'90%',fieldLabel:'是否正式在岗职工',name:'IS_ON_JOB_WORKER',
		             	store:isStaffStore,valueField : 'key',displayField : 'value',mode : 'local',
		              	typeAhead : true,forceSelection : true,triggerAction : 'all',emptyText : '未知',
		              	labelStyle:'text-align:right;',selectOnFocus : true}	,
		            {xtype:'combo',anchor:'90%',fieldLabel:'是否代发工资客户',name:'IS_PAYROLL_CUST',
		              	store:isPayrollStore,valueField : 'key',displayField : 'value',mode : 'local',
		              	typeAhead : true,forceSelection : true,triggerAction : 'all',emptyText : '未知',
		              	labelStyle:'text-align:right;',selectOnFocus : true},
		            {xtype:'combo',anchor:'90%',fieldLabel:'是否留存照片',name:'HAS_PHOTO',cls:'x-readOnly',readOnly:true,
		              	store:ifStore,valueField : 'key',displayField : 'value',mode : 'local',
		              	typeAhead : true,forceSelection : true,triggerAction : 'all',emptyText : '未知',
		              	labelStyle:'text-align:right;',selectOnFocus : true}
				]
			},{
				columnWidth:.5,  
				layout:'form',
				items:[				       
					{xtype:'combo',anchor:'90%',fieldLabel:'客户是否愿意接受短信',name:'RECEIVE_SMS_FLAG',
						store:isSMSStore,valueField : 'key',displayField : 'value',mode : 'local',
		              	typeAhead : true,forceSelection : true,triggerAction : 'all',emptyText : '未知',
		              	labelStyle:'text-align:right;',selectOnFocus : true}, 
			        {xtype:'combo',anchor:'90%',fieldLabel:'是否分润',name:'IS_DIVIDEND_CUST',cls:'x-readOnly',readOnly:true,
			           	store:isSplitStore,valueField : 'key',displayField : 'value',mode : 'local',
		              	typeAhead : true,forceSelection : true,triggerAction : 'all',emptyText : '未知',
		              	labelStyle:'text-align:right;',selectOnFocus : true},
				   	{xtype:'combo',anchor:'90%',fieldLabel:'是否美国纳税人',name:'USA_TAX_FLAG',cls:'x-readOnly',readOnly:true,
				       	store:isUSAStore,valueField : 'key',displayField : 'value',mode : 'local',
		              	typeAhead : true,forceSelection : true,triggerAction : 'all',emptyText : '未知',
		              	labelStyle:'text-align:right;',selectOnFocus : true,listeners:{
							select:function(combo,record){		
								comboSelect(record.data.key,custBusinessPanel);
							}
						}},
			     	{xtype:'textfield',anchor:'90%',fieldLabel:'US TIN/SSN',name:'USTIN',cls:'x-readOnly',readOnly:true},
			     	{xtype:'textfield',anchor:'90%',fieldLabel:'主键',name:'KEY_CUST_ID',cls:'x-readOnly',readOnly:true,hidden:true}
				]
			}]
		}]
	}
	]
});

var customerFinancialInfoPanel= new Ext.FormPanel({
		title : '财务和策略信息',
		reader : conreader,
		collapsible : true,
		layout:'fit',
		autoWidth:true,
		autoHeight:true,
		labelWidth:200,//label的宽度
		labelAlign:'right',
		frame:false,
		autoScroll : true,
		border:false,
		buttonAlign:'center',
		items :	 [ {
			xtype : 'fieldset',
			title : '客户财务目标与财务信息',
			titleCollapse : true,
			collapsible : true,
			autoHeight:true,
			autoWidth:true,
			items:[{
				layout:'column',
				items:[{
					columnWidth:.5,  
					layout:'form',
					items:[
					       {xtype:'textfield',width:250,anchor:'90%',fieldLabel:'主键ID',name:'FIN_ID',hidden:true},
					       {xtype:'textfield',width:250,anchor:'90%',fieldLabel:'退休计划',name:'IS_RETIRE_PLAN',maxLength:50},
					       {xtype:'textfield',width:250,anchor:'90%',fieldLabel:'购房计划',name:'IS_HOUSE_PLAN',maxLength:50},
					       {xtype:'textfield',width:250,anchor:'90%',fieldLabel:'子女教育计划',name:'IS_EDU_PLAN',maxLength:50},
					       {xtype:'textfield',width:250,anchor:'90%',fieldLabel:'资产总值（不含房产）',name:'ASSET_AMT',maxLength:10}
			              ]
					},{
					columnWidth:.5,  
					layout:'form',
					items:[
					       {xtype:'textfield',width:250,anchor:'90%',fieldLabel:'国内金融信息(他行)',name:'FINA_INFO_IN',maxLength:100},
					       {xtype:'combo',anchor:'90%',fieldLabel:'是否按揭',name:'IS_MORT',mode : 'local',store: new Ext.data.Store(),triggerAction : 'all',displayField:'value',valueField:'key',editable: false},
					       {xtype:'textfield',width:250,anchor:'90%',fieldLabel:'房产信息',name:'HOUSE_INFO',maxLength:50},
					       {xtype:'textfield',width:250,anchor:'90%',fieldLabel:'海外资产情况',name:'ASSET_INFO_OUT',maxLength:100}
				          ]
				}]
			}]
		},{
			xtype : 'fieldset',
				title : '策略信息',
				titleCollapse : true,
				collapsible : true,
				autoHeight:true,
				autoWidth:true,
				items:[{
					layout:'column',
					items:[{
						columnWidth:.5,  
						layout:'form',
						items:[
						       {xtype:'combo',anchor:'90%',fieldLabel:'账户经营策略',name:'ACC_RUN_STRATEGY',mode : 'local',store: new Ext.data.Store(),triggerAction : 'all',displayField:'value',valueField:'key',editable: false,maxLength:25},
						       {xtype:'textfield',width:250,anchor:'90%',fieldLabel:'建议投资产品及营销策略',name:'PROD_ADVICE',maxLength:100}
				        ]
					},{
						columnWidth:.5,  
						layout:'form',
						items:[
						       {xtype:'textfield',width:250,anchor:'90%',fieldLabel:'客户服务注意事项',name:'SERVICE_ATTENTION',maxLength:100}
				        ]
					}]
				}]
		}]
	});
	
	var customerCardInfoPanel= new Ext.FormPanel({
		title : '客户贵宾信息',
		reader : vipreader,
		collapsible : true,
		layout:'fit',
		autoWidth:true,
		autoHeight:true,
		labelWidth:200,//label的宽度
		labelAlign:'right',
		frame:false,
		autoScroll : true,
		border:false,
		buttonAlign:'center',
		items :	 [ {
			xtype : 'fieldset',
			title : '客户贵宾信息',
			titleCollapse : true,
			collapsible : true,
			autoHeight:true,
			autoWidth:true,
			items:[{
				layout:'column',
				items:[{
					columnWidth:.5,  
					layout:'form',
					items:[
					       {xtype:'combo',anchor:'90%',fieldLabel:'当前持有最高等级贵宾卡等级',name:'HIGHEST_CARD_LEVEL',cls:'x-readOnly',readOnly:true,store:creditType,valueField : 'key',displayField : 'value',mode : 'local',
		            		typeAhead : true,forceSelection : true,triggerAction : 'all',emptyText : '未知',
		            		labelStyle:'text-align:right;',selectOnFocus : true}, 
						   {xtype:'combo',anchor:'90%',fieldLabel:'可发卡等级',name:'HOLD_CARD_LEVEL',cls:'x-readOnly',readOnly:true,store:creditType,valueField : 'key',displayField : 'value',mode : 'local',
		            		typeAhead : true,forceSelection : true,triggerAction : 'all',emptyText : '未知',
		            		labelStyle:'text-align:right;',selectOnFocus : true}, 
						   {xtype:'combo',anchor:'90%',fieldLabel:'是否为免收账户管理费贵宾',name:'IS_VIP_CUST',cls:'x-readOnly',readOnly:true,
						   	store:isValidate,valueField : 'key',displayField : 'value',mode : 'local',
		            		typeAhead : true,forceSelection : true,triggerAction : 'all',emptyText : '未知',
		            		labelStyle:'text-align:right;',selectOnFocus : true}
			              ]
					},{
					columnWidth:.5,  
					layout:'form',
					items:[
					   	   {xtype:'combo',anchor:'90%',fieldLabel:'是否有效贵宾',name:'IS_STANDARD',cls:'x-readOnly',readOnly:true,
							store:isStandardStore,valueField : 'key',displayField : 'value',mode : 'local',
			            	typeAhead : true,forceSelection : true,triggerAction : 'all',emptyText : '未知',
			            	labelStyle:'text-align:right;',selectOnFocus : true}, 
					       {xtype:'combo',anchor:'90%',fieldLabel:'是否有航空意外险',name:'IS_FLIGHT_INSURANCE',cls:'x-readOnly',readOnly:true,
					   		store:isValidate,valueField : 'key',displayField : 'value',mode : 'local',
		            		typeAhead : true,forceSelection : true,triggerAction : 'all',emptyText : '未知',
		            		labelStyle:'text-align:right;',selectOnFocus : true}
				          ]
				}]
			}]
		}]
	});

var comboSelect = function(selectValue,custPanel){
	if(selectValue == 'Y'){
		custPanel.getForm().findField('USTIN').setVisible(true);
	}else{
		custPanel.getForm().findField('USTIN').setValue('');
		custPanel.getForm().findField('USTIN').setVisible(false);
	}
}

/**
 * 客户callReport信息
 */
	
	var callReportPanel= new Ext.Panel({
		title:'客户callReport信息',
		height:1120,
		frame:false,
		autoScroll : false,
		layout:'column',
		style:'padding:0px 0px 0px 0px',
		draggable:false,
		items:[{
			columnWidth:1,  
			layout:'form',
			items:[ {
				autoWidth: true,
				height:1120,
//				style:'padding:0px 0px 0px 0px',
				
				html: '<iframe scrolling="auto" id="t1" frameborder="0"  width="100%" height="100%" src="/crmweb/contents/pages/wlj/customerManager/privateCustView/CustCallReportInfo.jsp?custId='+_custId+'"> </iframe>'
					
			 }]
			}]
	});
	


/**
 * 客户持卡信息
 */
var creditrownum = new Ext.grid.RowNumberer( {
	header : 'No.',
	width : 40
});
var creditcm = new Ext.grid.ColumnModel( [
	creditrownum,
    {header:'客户号', dataIndex:'CUST_ID', sortable:true, align:'left', width : 100,hidden:true},
    {header:'核心客户号', dataIndex:'CB_CUST_ID', sortable:true, align:'left', width : 100},
	{header:'账号', dataIndex:'ACCT_NO', sortable:true, align:'left', width : 120},
	{header:'卡号', dataIndex:'CARD_NO', sortable:true, align:'left', width : 120},
    {header:'持卡类别', dataIndex:'CARD_TYPE', sortable:true, align:'left', width : 90},
    {header:'账户余额(人民币)', dataIndex:'AMT', viewFn: money('0,000.00'),sortable:true, align:'left', width : 120},
    {header:'领卡方式', dataIndex:'GET_WAY', sortable:true, align:'left', width : 90},
    {header:'领卡邮寄地址', dataIndex:'SENT_ADDRESS', sortable:true, align:'left', width : 100},
    {header:'卡面设计', dataIndex:'CARD_APPER', sortable:true, align:'left', width : 90},
    {header:'开卡日期', dataIndex:'OPEN_DATE', sortable:true, align:'left', width : 90},
    {header:'销卡日期', dataIndex:'CLOSE_DATE', sortable:true, align:'left', width : 90},
    {header:'卡片状态', dataIndex:'BUSSINESS_CARD_STATE', sortable:true, align:'left', width : 90}
]);
var creditStore = new Ext.data.Store( {
	restful : true,
	proxy : new Ext.data.HttpProxy( {
		url : basepath + '/privateCustInfo!searchCredit.json?custId='+_custId,
		method: 'POST',
		failure : function(response) {//状态码解码.
			var resultArray = Ext.util.JSON.decode(response.status);
			if (resultArray == 403) {
				Ext.Msg.alert('提示',response.responseText);
			}
		}
	}),
	reader : new Ext.data.JsonReader( {
		successProperty : 'success',
		root : 'data'
	}, [ 
	    {name : 'CUST_ID'},
	    {name : 'CB_CUST_ID'},
		{name : 'CARD_TYPE',mapping:'CARD_TYPE_ORA'},
	    {name : 'ACCT_NO'},
	    {name : 'CARD_NO'},
	    {name : 'AMT'},
		{name : 'GET_WAY',mapping:'GET_WAY_ORA'},
		{name : 'SENT_ADDRESS'},
		{name : 'CARD_APPER'},
		{name : 'OPEN_DATE'},
		{name : 'CLOSE_DATE'},
		{name : 'BUSINESS_CARD_STATE',mapping:'BUSINESS_CARD_STATE_ORA'}
	])
});
creditStore.load();

var creditGrid = new Ext.grid.GridPanel( {
	title : '客户持卡信息',
	collapsible : true,
	autoHeight:true,
	frame : true,
	overflow : 'auto',
	autoScroll : true,
	store : creditStore, // 数据存储
	stripeRows : true, // 斑马线
	cm : creditcm, // 列模型
	loadMask : {
		msg : '正在加载表格数据,请稍等...'
	}
});

/****************************************客户历史达标情况**************************************/
var standardHisrownum = new Ext.grid.RowNumberer( {
	header : 'No.',
	width : 40
});
var standardHiscm = new Ext.grid.ColumnModel( [
	standardHisrownum,
    {header:'客户编号', dataIndex:'CUST_ID', sortable:true, align:'left', width : 180,hidden: true},
    {header:'日期(年/月)', dataIndex:'ETL_DATE', sortable:true, align:'left', width : 180},
    {header:'月日均AUM(万元)', dataIndex:'AUM_MONTH', sortable:true, align:'left', width : 180},
    {header:'当月持有最高贵宾卡等级', dataIndex:'HIGHEST_CARD_LEVEL', sortable:true, align:'left', width : 180},
	{header:'可发卡等级', dataIndex:'HOLD_CARD_LEVEL', sortable:true, align:'left', width : 180},
    {header:'达标情况', dataIndex:'IS_STANDARD', sortable:true, align:'left', width : 180}
]);
var standardHisStore = new Ext.data.Store( {
	restful : true,
	proxy : new Ext.data.HttpProxy( {
		url : basepath + '/privateCustInfo!searchStandardHis.json?custId='+_custId,
		method: 'POST',
		failure : function(response) {//状态码解码.
			var resultArray = Ext.util.JSON.decode(response.status);
			if (resultArray == 403) {
				Ext.Msg.alert('提示',response.responseText);
			}
		}
	}),
	reader : new Ext.data.JsonReader( {
		successProperty : 'success',
		root : 'data'
	}, [ 
	    {name : 'CUST_ID'},
	    {name : 'ETL_DATE'},
	    {name : 'AUM_MONTH'},
	    {name : 'HIGHEST_CARD_LEVEL',mapping:'HIGHEST_CARD_LEVEL_ORA'},
		{name : 'HOLD_CARD_LEVEL',mapping:'HOLD_CARD_LEVEL_ORA'},
		{name : 'IS_STANDARD',mapping:'IS_STANDARD_ORA'}
	])
});
standardHisStore.load();

var standardHisGrid = new Ext.grid.GridPanel( {
	title : '客户历史达标情况',
	collapsible : true,
	autoHeight:true,
	frame : true,
	overflow : 'auto',
	autoScroll : true,
	store : standardHisStore, // 数据存储
	stripeRows : true, // 斑马线
	cm : standardHiscm, // 列模型
	loadMask : {
		msg : '正在加载表格数据,请稍等...'
	}
});

var customerView =[{
	/**
	 * 自定义面板
	 */
	title:'基本信息',
	hideTitle:true,
	type: 'form',
	items:[custBaseInfoPanel,custBusinessPanel,customerCardInfoPanel,standardHisGrid,customerFinancialInfoPanel,creditGrid,callReportPanel],
	formButtons:[{
		text:'修改历史',
		hidden:JsContext.checkGrant("_perQueryHis"),
		fn:function(){
			custId = _custId;
			custName = custBaseInfoPanel.getForm().findField("CUST_NAME").getValue();
			updateHisWin.show();
		}
	},'-',{
		text:'提交',
		hidden:JsContext.checkGrant("privateBaseInfo_forApprove"),
		fn : function(){
			if(!custBaseInfoPanel.getForm().isValid()){
				Ext.Msg.alert('提示','输入有误或存在漏输入项,请检查输入项');
				return false;
			}
			if(!custBusinessPanel.getForm().isValid()){
				Ext.Msg.alert('提示','输入有误或存在漏输入项,请检查输入项');
				return false;
			}
			var finArr = ['FIN_ID','IS_RETIRE_PLAN','IS_HOUSE_PLAN','IS_EDU_PLAN','ASSET_AMT','FINA_INFO_IN','IS_MORT','HOUSE_INFO','ASSET_INFO_OUT',
			'ACC_RUN_STRATEGY','PROD_ADVICE','SERVICE_ATTENTION'];
			var busiArr=['KEY_CUST_ID','IS_ON_JOB_WORKER','RECEIVE_SMS_FLAG','IS_PAYROLL_CUST']
			var busiflag=false;//重要标志信息
			var keyflag=false;
			var json1 = custBaseInfoPanel.reader.jsonData.data[0];//读取时，原数据
			var json2 = custBaseInfoPanel.form.getValues(false);//(返回键值对)提交时的数据
			var perModel = [];//perModel是一个数组/集合
			for(var key in json2){
				var pcbhModel = {};
				var field = custBaseInfoPanel.getForm().findField(key);
				//排除放大镜,放大镜必须单独处理
				if(key == 'INDUST_TYPE'){
					continue;//放大镜隐藏字段不处理
				}else if(key == 'INDUST_TYPE_NAME'){
					var tempkey = field.hiddenName?field.hiddenName:key;
					var tempField = tempkey == key ? field:custBaseInfoPanel.getForm().findField(tempkey);
					if(!((json1[tempkey]==tempField.getValue()) || (null==json1[tempkey]&&null==tempField.getValue()))){
						var pcbhModel = {};
						pcbhModel.custId = _custId;
						pcbhModel.updateBeCont = json1[key];
						pcbhModel.updateAfCont = custBaseInfoPanel.getForm().findField(tempkey).getValue();
						pcbhModel.updateAfContView = json2[key];
						pcbhModel.updateItem = field.fieldLabel;
						pcbhModel.updateItemEn = field.hiddenName;
						pcbhModel.fieldType = field.getXType() == 'datefield'?'2':'1';
						perModel.push(pcbhModel);
					}
					continue;
				}
				if(field.getXType() == 'combo'){
					var s=field.getValue();
					if(json1[key]!=s){
						if(json1[key]!=null&&s!=""){
							pcbhModel.custId = _custId;
							pcbhModel.updateBeCont = getStoreFieldValue(field.store,'key',json1[key],'value');
							pcbhModel.updateAfCont = s;
							pcbhModel.updateAfContView = field.getRawValue();//getStoreFieldValue(field.store,'key',s,'value');
							pcbhModel.updateItem = field.fieldLabel;
							pcbhModel.updateItemEn = field.name;
							pcbhModel.fieldType = '1';
							perModel.push(pcbhModel);
						}
						if("FAMILY_ANN_INC_SCOPE"==key){
							addKeyFn(perModel,_custId,custBaseInfoPanel,'FAMILY_ID','家庭信息表ID');
							addFieldFn(perModel,_custId,_custId,_custId,'FAMILY_CUST_ID');
						}
						if(key=='CONTACT_FREQ_PREFER'){
							addKeyFn(perModel,_custId,custBaseInfoPanel,'PREFERENCE_ID','偏好表ID');
						}
					}
				}else{
					if(!((json1[key]==json2[key]) || (null==json1[key]&&null==json2[key])||((json1[key] == undefined)&&(json2[key] == "")))){
						var pcbhModel = {};
						pcbhModel.custId = _custId;
						pcbhModel.updateBeCont = json1[key];
						pcbhModel.updateAfCont = json2[key];
						pcbhModel.updateAfContView = json2[key];
						pcbhModel.updateItem = field.fieldLabel;
						pcbhModel.updateItemEn = field.name;
						pcbhModel.fieldType = field.getXType() == 'datefield'?'2':'1';
						perModel.push(pcbhModel);
					}
					
					
					
				}
			}
			var json3 = custBusinessPanel.reader.jsonData.data[0];//重要标志信息
			var json4 = custBusinessPanel.form.getValues(false);
			var perModel2 = [];
			for(var key in json4){
				var pcbhModel = {};
				var field = custBusinessPanel.getForm().findField(key);
				if(field.getXType() == 'combo'){
					var s = field.getValue();
					if(json3 == undefined){
						if(!(json4[key] == "未知")){
							pcbhModel.custId = _custId;
							pcbhModel.updateBeCont = "";
							pcbhModel.updateAfCont = s;
							pcbhModel.updateAfContView = field.getRawValue();
							pcbhModel.updateItem = custBusinessPanel.getForm().findField(key).fieldLabel;
							pcbhModel.updateItemEn = custBusinessPanel.getForm().findField(key).name;
							pcbhModel.fieldType = '1';
							perModel2.push(pcbhModel);
							if(busiArr.indexOf(key) > - 1){
								busiflag = true;
							}
						}
					}else if(json3[key] != s){
							if((json3[key] == "")&&(json4[key] == "")||(json3[key] == undefined)&&(json4[key] == "")){
							}else if(json3[key]!=null && s!=""){
								pcbhModel.custId = _custId;
								pcbhModel.updateBeCont = getStoreFieldValue(field.store,'key',json3[key],'value');
								pcbhModel.updateAfCont = s;
								pcbhModel.updateAfContView = field.getRawValue();//getStoreFieldValue(field.store,'key',s,'value');
								pcbhModel.updateItem = field.fieldLabel;
								pcbhModel.updateItemEn = field.name;
								pcbhModel.fieldType = '1';
								perModel2.push(pcbhModel);
								if(busiArr.indexOf(key) > - 1){
									busiflag = true;
								}
							}
					}
				}else{
					if(json3 == undefined){
						if(!(json4[key] == "")){
							pcbhModel.custId = _custId;
							pcbhModel.updateBeCont = "";
							pcbhModel.updateAfCont = s;
							pcbhModel.updateAfContView = field.getRawValue();
							pcbhModel.updateItem = custBusinessPanel.getForm().findField(key).fieldLabel;
							pcbhModel.updateItemEn = custBusinessPanel.getForm().findField(key).name;
							pcbhModel.fieldType = field.getXType() == 'datefield'?'2':'1';
							perModel2.push(pcbhModel);
							if(busiArr.indexOf(key) > - 1){
								busiflag = true;
							}
						}
					}else if(((json3[key] == undefined)&&(json4[key] == ""))||((json3[key] == "")&&(json4[key] == ""))){
					}else if(!((json3[key]==json4[key]) || (null==json3[key]&&null==json4[key]))){
							var pcbhModel = {};
							pcbhModel.custId = _custId;
							pcbhModel.updateBeCont = json3[key];
							pcbhModel.updateAfCont = json4[key];
							pcbhModel.updateAfContView = json4[key];
							pcbhModel.updateItem = field.fieldLabel;
							pcbhModel.updateItemEn = field.name;
							pcbhModel.fieldType = field.getXType() == 'datefield'?'2':'1';
							perModel2.push(pcbhModel);
							if(busiArr.indexOf(key) > - 1){
								busiflag = true;
							}
					}
				}
			}
			var json5 = customerFinancialInfoPanel.reader.jsonData.data[0];//财务策略信息
			var json6 = customerFinancialInfoPanel.form.getValues(false);
			var perModel3 = [];
			for(var key in json6){
				var pcbhModel = {};
				var field = customerFinancialInfoPanel.getForm().findField(key);
				var s = field.getValue();
				if(field.getXType() == 'combo'){
					if(json5 == undefined && s!=""){
						pcbhModel.custId = _custId;
						pcbhModel.updateBeCont = "";
						pcbhModel.updateAfCont = s;
						pcbhModel.updateAfContView = field.getRawValue();
						pcbhModel.updateItem = customerFinancialInfoPanel.getForm().findField(key).fieldLabel;
						pcbhModel.updateItemEn = customerFinancialInfoPanel.getForm().findField(key).name;
						pcbhModel.fieldType = '1';
						perModel3.push(pcbhModel);
						if(finArr.indexOf(key) > - 1){
								keyflag = true;
						}
					}else if(json5!= undefined &&  json5[key] != s){
							if(json5[key]!=null && s!=""){
								pcbhModel.custId = _custId;
								pcbhModel.updateBeCont = getStoreFieldValue(field.store,'key',json5[key],'value');
								pcbhModel.updateAfCont = s;
								pcbhModel.updateAfContView = field.getRawValue();//getStoreFieldValue(field.store,'key',s,'value');
								pcbhModel.updateItem = field.fieldLabel;
								pcbhModel.updateItemEn = field.name;
								pcbhModel.fieldType = '1';
								perModel3.push(pcbhModel);
								if(finArr.indexOf(key) > - 1){
									keyflag = true;
								}
							}
					}
				}else{
					if(json5 == undefined && s!="" ){
						pcbhModel.custId = _custId;
						pcbhModel.updateBeCont = "";
						pcbhModel.updateAfCont = s;
						pcbhModel.updateAfContView = field.getRawValue();
						pcbhModel.updateItem = customerFinancialInfoPanel.getForm().findField(key).fieldLabel;
						pcbhModel.updateItemEn = customerFinancialInfoPanel.getForm().findField(key).name;
						pcbhModel.fieldType = field.getXType() == 'datefield'?'2':'1';
						perModel3.push(pcbhModel);
						if(finArr.indexOf(key) > - 1){
								keyflag = true;
						}
					}else if( json5!=undefined && !((json5[key]==json6[key]) || (null==json5[key]&&null==json6[key]))){
							var pcbhModel = {};
							pcbhModel.custId = _custId;
							pcbhModel.updateBeCont = json5[key];
							pcbhModel.updateAfCont = json6[key];
							pcbhModel.updateAfContView = json6[key];
							pcbhModel.updateItem = field.fieldLabel;
							pcbhModel.updateItemEn = field.name;
							pcbhModel.fieldType = field.getXType() == 'datefield'?'2':'1';
							perModel3.push(pcbhModel);
							if(finArr.indexOf(key) > - 1){
								keyflag = true;
							}
					}
				}
			}
			if(perModel.length < 1 && perModel2.length < 1 && perModel3.length < 1){
				Ext.Msg.alert('提示', '未作任何修改,不允许提交!');
				return false;
			}
			if(keyflag){//财务信息
					//addKeyFn(perModel,_custId,customerFinancialInfoPanel,'FIN_ID','主键ID');
					addFieldFn(perModel3,_custId,'ID_SEQUENCE.NEXTVAL',customerFinancialInfoPanel.getForm().findField('FIN_ID').getValue(),'FIN_ID','1','1');
					addFieldFn(perModel3,_custId,_custId,_custId,'BUSI_CUST_ID');
			}
			if(busiflag){//重要标志信息
				addFieldFn(perModel2,_custId,_custId
					,custBusinessPanel.getForm().findField('KEY_CUST_ID').getValue(),'KEY_CUST_ID','1','1');
			}
			addFieldFn(perModel,_custId,'CRM','CRM','LAST_UPDATE_SYS','','1');
			addFieldFn(perModel,_custId,Ext.util.Format.date(new Date(),'Y-m-d'),Ext.util.Format.date(new Date(),'Y-m-d'),'LAST_UPDATE_TM','','2');
			addFieldFn(perModel,_custId,__userId,__userId,'LAST_UPDATE_USER','','1');
			
			var custId_v  = custBaseInfoPanel.getForm().findField("CUST_ID").getValue();
            var custName_v= custBaseInfoPanel.getForm().findField("CUST_NAME").getValue();
			Ext.Ajax.request({
				url:basepath+'/privateBaseInfo!initFlowBI.json',//触发提交流程
				method:'POST',
				params : {
		        	'perModel': Ext.encode(perModel),
		        	'perModel2': Ext.encode(perModel2),
		        	'perModel3':Ext.encode(perModel3),
		        	'custId': custId_v,
		        	'custName': custName_v
				},
				waitMsg : '正在提交申请,请等待...',
				success : function(response) {
					var ret = Ext.decode(response.responseText);
					var instanceid = ret.instanceid;//流程实例ID
					var currNode = ret.currNode;//当前节点
					var nextNode = ret.nextNode;//下一步节点
					selectUserList(instanceid,currNode,nextNode);//选择下一步办理人
				},
				failure: function() {
					Ext.Msg.alert('提示', '操作失败');
        		}
			});
		}
	}]
}];



//customerBaseInfoPanel加载数据
custBaseInfoPanel.getForm().load({
	url: basepath + '/privateCustInfo!searchInfo.json',
	method:'get',
	params : {
		'custId':_custId
    },
	success: function(form,action){
    	//手机号码与国家区号分发
    	var telMoto = form.findField('MOBILE_PHONE').getValue();
    	if(telMoto.indexOf("/",0)>0){
    		var telMArr = telMoto.split("/");
	    	form.findField('MOBILEX_PHONE').setValue(telMArr[1]);
	    	form.findField('TELM_NATION').setValue(telMArr[0]);
    	}else{
    		form.findField('MOBILEX_PHONE').setValue(telMoto);
    	}
    	//住宅号码与国家区号分发
    	var telHoto = form.findField('HOME_TEL').getValue();
    	if(telHoto.indexOf("/",0)>0){
    		var telHArr = telHoto.split("/");
	    	form.findField('HOMEX_TEL').setValue(telHArr[1]);
	    	form.findField('TELH_NATION').setValue(telHArr[0]);
    	}else{
    		form.findField('HOMEX_TEL').setValue(telHoto);
    	}
    	//家庭住址与国家地区分发
    	var addHoto = form.findField('HOME_ADDR').getValue();
    	if(addHoto.indexOf("/",0)>0){
    		var addHArr = addHoto.split("/");
	    	form.findField('HOMEX_ADDR').setValue(addHArr[1]);
	    	form.findField('ADDH_NATION').setValue(addHArr[0]);
    	}else{
    		form.findField('HOMEX_ADDR').setValue(addHoto);
    	}
    	//单位地址与国家地区分发
    	var addUoto = form.findField('UNIT_ADDR').getValue();
    	if(addUoto.indexOf("/",0)>0){
    		var addUArr = addUoto.split("/");
	    	form.findField('UNITX_ADDR').setValue(addUArr[1]);
	    	form.findField('ADDW_NATION').setValue(addUArr[0]);
    	}else{
    		form.findField('UNITX_ADDR').setValue(addUoto);
    	}
//    	var sta = JsContext.checkGrant('privateBaseInfo_mtain1AO');//JS默认true，勾选/admin后[false]
//    	var stp = JsContext.checkGrant('privateBaseInfo_mtain2OP');
//		
//    	if(sta == false && stp == false){
//    		
//    	}else if(sta == true && stp == false){//OP
//    		setDisabledP(stp);
//    	}else if(sta == false && stp == true){
//    		setDisabledA(sta);
//    	}
    }
});

//custBusinessPanel加载数据
custBusinessPanel.getForm().load({
	url: basepath + '/privateCustInfo!searchBusiInfo.json',
	method:'get',
	params : {
		'custId':_custId
    },
    success:function(){
    	//根据是否美国纳税人判断是否显示USITN
    	if(custBusinessPanel.getForm().findField('USA_TAX_FLAG').getValue()=='N'){
			custBusinessPanel.getForm().findField('USTIN').setVisible(false);
		}
    }
});

//加载数据
customerFinancialInfoPanel.getForm().load({
  	url: basepath + '/privateCustInfo!searchFinInfo.json',
  	method:'get',
  	params : {
  		'custId':_custId
    }
});

//加载数据
customerCardInfoPanel.getForm().load({
  	url: basepath + '/privateCustInfo!searchCardInfo.json',
  	method:'get',
  	params : {
  		'custId':_custId
    }
});

var beforeviewshow = function(theView) {
	customerFinancialInfoPanel.getForm().findField('IS_MORT').bindStore(findLookupByType('IS_MOGGG'));
	custBaseInfoPanel.getForm().findField('CONTACT_FREQ_PREFER').bindStore(findLookupByType('REN_RAKU'));
	customerFinancialInfoPanel.getForm().findField('ACC_RUN_STRATEGY').bindStore(findLookupByType('ACC_STRA'));
	//获取用户头像
	Ext.Ajax.request({
		url:basepath+'/privateCustInfo!searchImage.json',//保存信息
		method:'get',
		params:{
			custId:_custId
		},
		success:function(response){
			var data=Ext.util.JSON.decode(response.responseText).data;
			if(data[0].CUST_IMAGE!=null&&data[0].CUST_IMAGE!=""){
				Ext.getCmp('image').getEl().dom.src = basepath+ '/imgshow.json?path='+data[0].CUST_IMAGE ;
			}else{
				Ext.getCmp('image').getEl().dom.src = basepath+'/imgshow.json?path=noImage.jpg' ;
				Ext.Ajax.request({
					url:basepath+'/privateBaseInfo!setHasPhoto.json',
					method:'post',
					params:{
						custId:_custId
					},
					success:function(response){
						custBusinessPanel.getForm().load({
							restful : true,
							url: basepath + '/privateCustInfo!searchBusiInfo.json',//刷新form内容
							method:'get',
							params : {
								'custId':_custId
						    }
						});
					},
					failure: function(response){
						Ext.Msg.alert('提示', '操作失败'+response.responseText);
					}
				});
			}
		},
		failure: function(response){
			Ext.Msg.alert('提示', '操作失败'+response.responseText);
		}
	});
};


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
 * @param {} fieldLabel 字段label
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


