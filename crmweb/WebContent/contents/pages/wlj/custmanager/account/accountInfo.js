/**
 * @description 一键开户开户信息页面
 * @author ganyu
 * @since 2017-08-18
*/
imports([
         '/jQuery/js/jquery-1.11.3.min.js',
         '/contents/pages/com.yucheng.bcrm/com.yucheng.bcrm.js',
         '/contents/pages/common/Com.yucheng.crm.common.OrgUserManage.js',
         '/contents/pages/wlj/customerManager/queryAllCustomer/allLookup.js', //所有数据字典定义
         '/contents/pages/wlj/customerManager/queryAllCustomer/newAllLookup.js' //所有数据字典定义2
         ]);
Ext.QuickTips.init();

function initLayout2() {
	$("#x-form-el-BIRTHDAY").append('<span style="color:blue;">(可手工录入出生日期，格式为：年月日/年-月-日)</span>');
	
}
//移动电话类型
var Chkresult_comboData1=[['1','住宅电话'],['2','办公电话'],['3','其他电话']];
var Chkresult_comboData2=[['1','集团转介'],['2','行内员工转介'],['3','MGM'],['4','其他']];

var exceptionNumber = 1;


var account_info = new Ext.FormPanel({
	title : '基本信息',
	header:false,
	collapsible : true,
	autoHeight:true,
	autoWidth:true,
	labelWidth:200,//label的宽度
	labelAlign:'left',
	frame:false,//滚动条
	autoScroll : true,
	buttonAlign:'center',
	anchor:'95%',
	buttons:[{
		      text:'下一页',
		      handler:function(){
					tab_custinfo.setActiveTab(1);}
	        },{
	           text:'保存',
	           handler:function(){}
	        }],
	items : [{
			  layout:'column',
			  items:[{
				  	  title:'请您填写基本信息（*为必填项）',
				  	  columnWidth:1,
				  	  xtype:'fieldset',
				  	  style:'margin:0 10px;',
				  	  items:[{
							 layout : 'column',
							 items:[{//客户姓名
								    columnWidth:0.4,
								    layout : 'form',
							        items:[{
										xtype : 'textfield',
										width : '200',
										fieldLabel : '<font color=red>\*</font>姓名',
										readOnly:true,
										cls:'x-readOnly',
										  titleCollapse : true,
										  collapsible : true,
										allowBlank:false
										     }]
							       },{//是否为联名户
								    columnWidth:0.6,
								    layout : 'form',
								    labelWidth:1,
							        items:[{
								     	   xtype:'checkbox',
										   id:'islianminghu',
										   boxLabel:'联名户',
										   name:'islianminghu',
										   inputvalue:'1',
										listeners:{
												'check':function(obj,ischecked){
													if(ischecked == true){
													Ext.getCmp('lianminghu').setVisible(true);
													
												}else
												{
													Ext.getCmp('lianminghu').setVisible(false);
												}
												}
											}
									  	  }]
									  
								   },{//客户姓名拼音
									columnWidth:1,
								    layout : 'form',
							        items:[{
								         xtype : 'textfield',
								         id:'zhengjian',
										 width : '200',
										 fieldLabel : '<font color=red>\*</font>姓名拼音',
										 emptyText:'与开户证件一致',
										 titleCollapse : true,
										 collapsible : true,
										 allowBlank:false
							          },{//客户性别
							        	 xtype : 'combo',
							        	 name : 'GENDER',
							        	 hiddenName : 'GENDER',
							        	 fieldLabel : '<font color="red">*</font>性别',
							        	 store : sexStore,
							        	 editable:false,
							        	 emptyText:'请选择',
							        	 blankText:'该输入项不能为空',
							        	 resizable : true,
							        	 valueField : 'key',
							        	 displayField : 'value',
										 mode : 'local',
										 forceSelection : true,
										 triggerAction : 'all',
										 allowBlank:false
									 },{//民族
											xtype : 'combo',
											name : 'nationNality',
											hiddenName : 'nationNality',
											fieldLabel : '<font color="red">*</font>民族',
											emptyText:'请选择',
											store : nationnalityStore,
											resizable : true,
											valueField : 'key',
											displayField : 'value',
											mode : 'local',
											forceSelection : true,
											triggerAction : 'all'
									  },{//个人生日
										 id:'BIRTHDAY',
										 xtype : 'datefield',
										 name : 'BIRTHDAY',
										 fieldLabel : '<font color="red">*</font>个人生日',
										 format:'Y-m-d',
										 width:150,
										 allowBlank:false
									 },{//国籍
										xtype : 'combo',
										name : 'CITIZENSHIP',
										hiddenName : 'CITIZENSHIP',
										fieldLabel : '<font color="red">*</font>国籍',
										emptyText:'请选择',
										store : conStore,
										resizable : true,
										valueField : 'key',
										displayField : 'value',
										mode : 'local',
										forceSelection : true,
										triggerAction : 'all'
									 },{//出生地
										xtype : 'combo',
										name : 'BIRTHLOCALE',
										hiddenName : 'BIRTHLOCALE',
										fieldLabel : '<font color="red">*</font>出生地',
										emptyText:'请选择',
										store : conStore,
										resizable : true,
										valueField : 'key',
										displayField : 'value',
										mode : 'local',
										forceSelection : true,
										triggerAction : 'all'
									 },{//客户证件类型
										xtype : 'textfield',
										width : '200',
										fieldLabel : '<font color=red>\*</font>证件类型',
										titleCollapse : true,
										readOnly:true,
										cls:'x-readOnly',
									    collapsible : true,
									    allowBlank:false
									 },{//客户证件类型码值
										xtype : 'textfield',
										width : '200',
										fieldLabel : '<font color=red>\*</font>证件类型',
										titleCollapse : true,
										readOnly:true,
										hidden:true,
										cls:'x-readOnly',
									    collapsible : true,
									    allowBlank:false,
									    listeners:{
											'select':function(a,b,c){
												if(a.value == '6' || a.value == 'X24' || a.value == 'X3'){
													Ext.getCmp('twIdentNum').setVisible(true);
													Ext.getCmp('gaIdentNum').setVisible(false);
												}else if(a.value == '5'){
													Ext.getCmp('gaIdentNum').setVisible(true);
													Ext.getCmp('twIdentNum').setVisible(false);
												}else
												{
													Ext.getCmp('twIdentNum').setVisible(false);
													Ext.getCmp('gaIdentNum').setVisible(false);
												}
											 }
										  }
									 },{//证件号码
										xtype : 'textfield',
										width : '200',
									    fieldLabel : '<font color=red>\*</font>证件号码',
									    titleCollapse : true,
									    collapsible : true,
									    readOnly:true,
									    cls:'x-readOnly',
									    allowBlank:false
									  },{
										 xtype : 'textfield',
										 width : '200',
										 name:'twIdentNum',
										 id:'twIdentNum',
										 fieldLabel : '<font color=red>\*</font>台湾国民身份证号码',
								    	 titleCollapse : true,
								    	 collapsible : true,
								    	 disabled:true,
										 readOnly:true
								     },{
										 xtype : 'textfield',
										 width : '200',
										 name:'gaIdentNum',
										 id:'gaIdentNum',
										 fieldLabel : '<font color=red>\*</font>港澳通行证件号码',
								    	 titleCollapse : true,
								    	 collapsible : true,
								    	 disabled:true,
										 readOnly:true
								     },{//有效日期
										 xtype : 'datefield',
										 id:'LEGAL_EXPIRED_DATE',
										 name : 'LEGAL_EXPIRED_DATE',
										 fieldLabel : '<font color="red">*</font>有效日期',
										 width:150,
										 format:'Y-m-d'
									   },{//是否长期有效
								     	   xtype:'checkbox',
										   id:'LONGTERM',
										   boxLabel:'长期有效<font color = blue>(可手工录入证件有效期，格式为：年月日/年-月-日)</font>',
										   name:'LONGTERM',
										   inputvalue:'1',
										   listeners:{
												'check':function(obj,ischeck){
														if (ischeck == true){
															Ext.getCmp("LEGAL_EXPIRED_DATE").setValue("9999-12-31");  
															Ext.getCmp("LEGAL_EXPIRED_DATE").setReadOnly(true);
														}else{
															Ext.getCmp("LEGAL_EXPIRED_DATE").setValue(""); 
															Ext.getCmp("LEGAL_EXPIRED_DATE").setReadOnly(false);
														}
													}
										   }
									  	  }]
			                 }/*,{//移动电话所属国家（地区）
				        	    	columnWidth:.26,
				        	    	layout:'form',
									items:[{
									    xtype : 'combo',
										name : 'mobilePhone',
										id : 'mobilePhone',
										fieldLabel : '<font color="red">*</font>移动电话',
										emptyText:'请选择',
										store : globalRoamingStore,
										valueField : 'value',
										displayField : 'key',
										resizable : true,
										mode : 'local',
										forceSelection : true,
										triggerAction : 'all',
										listeners:{
											'select':function(a,b,c){
												Ext.getCmp('QUYUMA').setValue(a.value);
										}
									   }
									 }]
								 },{//移动电话国际区号
									  columnWidth:.04,
										 layout:'form',
										 labelWidth:1,
										 items:[{
												xtype : 'textfield',
												name : 'QUYUMA',
												id:'QUYUMA',
												width:"30",
												readOnly:true,
												cls:'x-readOnly'
											 }]
								  },{//移动电话电话号码
									 columnWidth:0.7,
									 layout:'form',
									 labelWidth:1,
									 items:[{
										    xtype : 'textfield',
											name : 'MOBILEPHONE_INFO',
											id : 'MOBILEPHONE_INFO',
											emptyText:'此电话将用于接收短信验证码和账务变动通知',
											width:'200',
											allowBlank:false
									 }]
								 }*/,{//居住地址（国籍）
			        	        columnWidth:0.35,
			        	        layout:'form',
			        	        items:[{
									    xtype : 'combo',
										name : 'HOME_ADDR',
										id : 'HOME_ADDR',
										fieldLabel : '<font color="red">*</font>居住地址',
										emptyText:'请选择',
										store : conStore,
										resizable : true,
										valueField : 'key',
										displayField : 'value',
										mode : 'local',
										forceSelection : true,
										triggerAction : 'all'
									 }]
			        	},{//居住地址（详细内容）
		        	        columnWidth:0.35,
		        	        layout:'form',
		                    labelWidth : 0.1,
		        	        items:[{
								    xtype : 'textfield',
									name : 'HOME_ADDR_INFO',
									id : 'HOME_ADDR_INFO',
									width:'350',
									allowBlank:false
								 }]
		        	    },{//居住地状态
		        	       columnWidth:0.3,
		        	       layout:'form',
		                   labelWidth : 5,
		        	       items:[{
						     	   xtype:'radiogroup',
								   id:'isRent',
								   columns:2,
								   anchor:'50%',
								   items:[
								       {boxLabel:'租赁',name:'isRent',inputValue:'1'},
							           {boxLabel:'自有',name:'isRent',inputValue:'2'}
							          ]
		        	        }]
		        	},{//居住地邮编
	        	        columnWidth:1,
	        	        layout:'form',
	        	        items:[{
							    xtype : 'textfield',
								name : 'POST_ZIPCODE',
								id : 'POST_ZIPCODE',
								fieldLabel : '<font color="red">*</font>居住地邮编',
								width:'200',
								maxLength:10,
								allowBlank:false
							 }]     
	        	    },{
	        	        columnWidth:0.23,
	        	        layout:'form',
	        	        items:[{//邮寄地址同上
					     	   xtype:'checkbox',
							   id:'same',
							   fieldLabel : '<font color="red">*</font>邮寄地址',
							   boxLabel:'同上',
							   name:'same',
							   inputvalue:'1',
							   listeners:{
									'check':function(obj,ischeck){
											if (ischeck == true){
												Ext.getCmp("MAIL_ADDR").setValue(Ext.getCmp("HOME_ADDR").getValue());  
												Ext.getCmp("MAIL_ADDR_INFO").setValue(Ext.getCmp("HOME_ADDR_INFO").getValue());  
												Ext.getCmp("MAIL_ZIPCODE").setValue(Ext.getCmp("POST_ZIPCODE").getValue());  
												Ext.getCmp("MAIL_ADDR").setReadOnly(true);
												Ext.getCmp("MAIL_ADDR_INFO").setReadOnly(true);
												Ext.getCmp("MAIL_ZIPCODE").setReadOnly(true);
												Ext.getCmp("MAIL_ADDR").cls = 'x-readOnly';  
												Ext.getCmp("MAIL_ADDR_INFO").cls = 'x-readOnly';  
												Ext.getCmp("MAIL_ZIPCODE").cls = 'x-readOnly';
											}else{
												Ext.getCmp("MAIL_ADDR").setValue("");  
												Ext.getCmp("MAIL_ADDR_INFO").setValue("");  
												Ext.getCmp("MAIL_ZIPCODE").setValue("");
												Ext.getCmp("MAIL_ADDR").setReadOnly(false);
												Ext.getCmp("MAIL_ADDR_INFO").setReadOnly(false);
												Ext.getCmp("MAIL_ZIPCODE").setReadOnly(false);
											}
										}
							   }
	        	             }]
	        	    },{//邮寄地址国籍
	        	        columnWidth:0.17,
	        	        layout:'form',
	        	        labelWidth:0.1,
	        	        items:[{
							    xtype : 'combo',
								name : 'MAIL_ADDR',
								id : 'MAIL_ADDR',
								store : conStore,
								resizable : true,
								valueField : 'key',
								displayField : 'value',
								emptyText:'请选择',
								mode : 'local',
								forceSelection : true,
								triggerAction : 'all'
							 }]
	        	    },{//邮寄详细地址
	        	        columnWidth:0.65,
	        	        layout:'form',
	                    labelWidth : 0.1,
	        	        items:[{
							    xtype : 'textfield',
								name : 'MAIL_ADDR_INFO',
								id : 'MAIL_ADDR_INFO',
								width:'350',
								allowBlank:false
							 }]
	        	    },{//邮寄地邮编
	        	    	columnWidth:1,
	        	        layout:'form',
	        	        items:[{
							    xtype : 'textfield',
								name : 'MAIL_ZIPCODE',
								id : 'MAIL_ZIPCODE',
								fieldLabel : '<font color="red">*</font>邮寄邮编',
								width:'200',
								maxLength:10,
								allowBlank:false
							 }]
	        	    },{//移动电话归属国家
						 columnWidth:.35,
						 layout:'form',
						 items:[{
						   xtype : 'combo',
						   name : 'mbPhone',
						   hiddenName : 'mbPhone',
						   ID:'mbPhone',
						   fieldLabel : '<font color="red">*</font>移动电话',
						   emptyText:'请选择',
						   store:globalRoamingStore,
						   resizable : true,
						   valueField : 'value',
						   displayField : 'key',
						   mode : 'local',
						   forceSelection : true,
						   triggerAction : 'all',
						   listeners:{
								'select':function(a,b,c){
									Ext.getCmp('QUYUMA').setValue(a.value);
							}
						   } 
						 }]
					  },{//移动电话国际区号
						  columnWidth:.05,
							 layout:'form',
							 labelWidth:1,
							 items:[{
									xtype : 'textfield',
									name : 'QUYUMA',
									id:'QUYUMA',
									width:"30",
									hiddenName : 'QUYUMA',
									readOnly:true,
									cls:'x-readOnly'
								 }]
					  },{//移动电话电话号
						  columnWidth:.5,
							 layout:'form',
							 labelWidth:50,
							 items:[{
							    xtype : 'textfield',
							    fieldLabel : '-电话',
							    emptyText:'此电话将用于接受短信验证码和账务变通通知',
								name : 'mbPHONENUM',
								id : 'mbPHONENUM',
								width:'200',
								allowBlank:false
							 }]
					 },{//其他电话1类型选择
						 columnWidth:.35,
						 layout:'form',
						 items:[{
						   xtype : 'combo',
						   name : 'ORTHERPHONE',
						   hiddenName : 'ORTHERPHONE',
						   ID:'ORTHERPHONE',
						   fieldLabel : '其他电话',
						   emptyText:'请选择',
						   store:new Ext.data.SimpleStore(
									    {
										  fields:['key','value'],
										  data:Chkresult_comboData1
										 }),
						   //resizable : true,
						   valueField : 'key',
						   displayField : 'value',
						   mode : 'local',
						   forceSelection : true,
						   triggerAction : 'all' 
						 }]
					  },{//其他电话1国籍
						 columnWidth:.17,
						 layout:'form',
						 labelWidth:1,
						 items:[{
								xtype : 'combo',
								name : 'PHONE_CITIZENSHIP',
								hiddenName : 'PHONE_CITIZENSHIP',
								emptyText:'请选择',
								id:'PHONE_CITIZENSHIP',
								store : globalRoamingStore,
								resizable : true,
								valueField : 'value',
								displayField : 'key',
								mode : 'local',
								forceSelection : true,
								triggerAction : 'all',
								listeners:{
									'select':function(a,b,c){
											Ext.getCmp('QUYUMA1').setValue(a.value);
									         }
								           }
							 }]
					  },{//其他电话1国际区号
						  columnWidth:.05,
							 layout:'form',
							 labelWidth:1,
							 items:[{
									xtype : 'textfield',
									name : 'QUYUMA1',
									id:'QUYUMA1',
									width:"30",
									hiddenName : 'QUYUMA1',
									readOnly:true,
									cls:'x-readOnly'
								 }]
					  },{//其他电话1区域码
					     columnWidth:.1,
						 layout:'form',
						 labelWidth:50,
						 items:[{
						    xtype : 'textfield',
						    fieldLabel : '-区域码',
							name : 'QUYUMA2',
							id : 'QUYUMA2',
							width:'30',
							allowBlank:false
						 }]
					 },{//其他电话1电话号
						  columnWidth:.4,
							 layout:'form',
							 labelWidth:40,
							 items:[{
							    xtype : 'textfield',
							    fieldLabel : '-电话',
								name : 'PHONENUM1',
								id : 'PHONENUM1',
								width:'150',
								allowBlank:false
							 }]
					 },{//其他电话2类型选择
						 columnWidth:.35,
						 layout:'form',
						 items:[{
						   xtype : 'combo',
						   name : 'ORTHERPHONE1',
						   hiddenName : 'ORTHERPHONE1',
						   emptyText:'请选择',
					       editable:false,
						   ID:'ORTHERPHONE1',
						   store:new Ext.data.SimpleStore(
									  {
										  fields:['key','value'],
										  data:Chkresult_comboData1
										 }),
						   resizable : true,
						   valueField : 'key',
						   displayField : 'value',
						   mode : 'local',
						   forceSelection : true,
						   triggerAction : 'all' 
						 }]
					  },{//其他电话2国籍选择
						 columnWidth:.17,
						 layout:'form',
						 labelWidth:1,
						 items:[{
								xtype : 'combo',
								name : 'PHONE_CITIZENSHIP1',
								hiddenName : 'PHONE_CITIZENSHIP1',
								emptyText:'请选择',
								id:'PHONE_CITIZENSHIP1',
								store : globalRoamingStore,
								resizable : true,
								valueField : 'value',
								displayField : 'key',
								mode : 'local',
								forceSelection : true,
								triggerAction : 'all',
								listeners:{
									'select':function(a,b,c){
											Ext.getCmp('QUYUMA3').setValue(a.value);
									         }
								           }
							 }]
					  },{//其他电话2国际区号
						  columnWidth:.05,
							 layout:'form',
							 labelWidth:1,
							 items:[{
									xtype : 'textfield',
									name : 'QUYUMA3',
									id:'QUYUMA3',
									width:"30",
									hiddenName : 'QUYUMA3',
									readOnly:true,
									cls:'x-readOnly'
								 }]
					 },{//其他电话2区域码
						  columnWidth:.1,
							 layout:'form',
							 labelWidth:50,
							 items:[{
							    xtype : 'textfield',
							    fieldLabel : '-区域码',
								name : 'QUYUMA4',
								id : 'QUYUMA4',
								width:'30',
								allowBlank:false
							 }]
					 },{//其他电话2电话号码
							 layout:'form',
							 labelWidth:40,
							 items:[{
							    xtype : 'textfield',
							    fieldLabel : '-电话',
								name : 'PHONENUM2',
								id : 'PHONENUM2',
								width:'150',
								allowBlank:false
							 }]
					 },{//职业资料
	        	       columnWidth:.6,
	        	       layout:'form',
	        	       items:[{
					     	   xtype:'radiogroup',
							   id:'JOBINFO',
							   fieldLabel:'<font color="red">*</font>职业资料',
							   columns:4,
							   allowBlank:false,
							   anchor:'90%',
							   items:[
							       {boxLabel:'全日制雇员',name:'JOBINFO',inputValue:'1'},
						           {boxLabel:'自雇',name:'JOBINFO',inputValue:'2'},
						           {boxLabel:'退休',name:'JOBINFO',inputValue:'3'},
						           {boxLabel:'其他',name:'JOBINFO',inputValue:'4'}
						          ],
						          listeners:{
										'change':function(a,b){
											if(b.inputValue == '4'){
												Ext.getCmp('JOBREMARK').setVisible(true);
												Ext.getCmp('JOBNAME').setVisible(false);
												Ext.getCmp('JOBNAME').allowBlank = true;
												Ext.getCmp('JOB').allowBlank = true;
												Ext.getCmp('JOB').setVisible(false);
												Ext.getCmp('JOBREMARK').allowBlank = false;
											}else if(b.inputValue == '1'){
												Ext.getCmp('JOBREMARK').setVisible(false);
												Ext.getCmp('JOBNAME').setVisible(true);
												Ext.getCmp('JOB').setVisible(true);
												Ext.getCmp('JOBNAME').allowBlank = false;
												Ext.getCmp('JOB').allowBlank = false;
												Ext.getCmp('JOBREMARK').allowBlank = true;
											}else
											{
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
		        	
				   },{//职业资料其他备注
					  columnWidth:.4,
						 layout:'form',
						 labelWidth:100,
						 items:[{
						    xtype : 'textfield',
						    fieldLabel : '（请具体注明）',
							name : 'JOBREMARK',
							id : 'JOBREMARK',
							width:'200',
							allowBlank:false
							 }]
					  },{//职业资料自雇单位名称
						  columnWidth:1,
							 layout:'form',
							 items:[{
							    xtype : 'textfield',
							    fieldLabel : '<font color=red>*</font>单位名称',
								name : 'JOBNAME',
								id : 'JOBNAME',
								width:'200'
							 }]
					  },{//职业资料自雇职位
						  columnWidth:1,
							 layout:'form',
							 items:[{
							    xtype : 'textfield',
							    fieldLabel : '<font color=red>*</font>职位',
								name : 'JOB',
								id : 'JOB',
								width:'200'
							 }]
					  },{//电子邮箱E-mail
						  columnWidth:1,
							 layout:'form',
							 items:[{
							    xtype : 'textfield',
							    fieldLabel : '电子邮箱E-mail',
								name : 'EMAIL',
								id : 'EMAIL',
								width:'200'
							 }]
					 },{//在我行有无关联人
	        	       columnWidth:.4,
	        	       layout:'form',
	        	       items:[{
					     	   xtype:'radiogroup',
							   id:'HASRELATED',
							   fieldLabel:'<font color="red">*</font>在我行有无关联人',
							   columns:2,
							   anchor:'70%',
							   items:[
							       {boxLabel:'有',name:'HASRELATED',inputValue:'1'},
						           {boxLabel:'无',name:'HASRELATED',inputValue:'2'}
						          ],
						          listeners:{
										'change':function(a,b){
											if(b.inputValue == '1'){
												Ext.getCmp('RELATEDNAME').setVisible(true);
												Ext.getCmp('RELATEDNAME').allowBlank = false;
												Ext.getCmp('RELATION').setVisible(true);
												Ext.getCmp('RELATION').allowBlank=false;
											}else
											{
												Ext.getCmp('RELATEDNAME').setVisible(false);
												Ext.getCmp('RELATEDNAME').allowBlank=true;
												Ext.getCmp('RELATION').setVisible(false);
												Ext.getCmp('RELATION').allowBlank=true;
											}
										}
									}
			        	        }]
			        	},{//关联人姓名
							  columnWidth:.25,
								 layout:'form',
								 labelWidth:30,
								 items:[{
									 fieldLabel:'姓名',
								    xtype : 'textfield',
									name : 'RELATEDNAME',
									id : 'RELATEDNAME',
									width:'200'
								 }]
						 },{//与关联人关系
							  columnWidth:.25,
								 layout:'form',
								 labelWidth:30,
								 items:[{
									 fieldLabel:'关系',
								    xtype : 'textfield',
									name : 'RELATION',
									id : 'RELATION',
									width:'200'
								 }]
						 },{ //与我行关联关系
							 columnWidth:1,
								 layout:'form',
								 items:[{
									 	fieldLabel:'<font color="red">*</font>与我行关联关系',
							        	 xtype : 'combo',
							        	 name : 'RELATION1',
							        	 id:'RELATION1',
							        	 hiddenName : 'RELATION1',
							        	 emptyText:'请选择',
							        	 store : staffinStore,
							        	 resizable : true,
							        	 valueField : 'key',
							        	 displayField : 'value',
										 mode : 'local',
										 forceSelection : true,
										 triggerAction : 'all',
										 allowBlank:false
									 }]
						     },{//来源渠道
						    	 columnWidth:1,
								 layout:'form',
								 items:[{
										xtype : 'combo',
										name : 'SOURCECHANNEL',
										hiddenName : 'SOURCECHANNEL',
										fieldLabel : '<font color="red">*</font>来源渠道',
										emptyText:'请选择',
									    editable:false,
										store : Chkresult_comboData2,
										resizable : true,
										valueField : 'key',
										displayField : 'value',
										mode : 'local',
										forceSelection : true,
										triggerAction : 'all'
								 		}]
								 },{//预约网点
									 columnWidth:1,
									 layout:'form',
									 items:[{
									    fieldLabel:'预约网点',
									    xtype : 'textfield',
										name : 'YUYUEWANGDIAN',
										id : 'YUYUEWANGDIAN',
								    	readOnly:true,
								    	cls:'x-readOnly',
										width:'150'
									 }]
								  },{//预约日期
									 columnWidth:1,
									 layout:'form',
									 items:[{
									    fieldLabel:'预约日期',
									    xtype : 'textfield',
										name : 'YUYUERIQI',
										id : 'YUYUERIQI',
								    	readOnly:true,
								    	cls:'x-readOnly',
										width:'150'
										 }]
								     },{//预约时间段
										 columnWidth:1,
										 layout:'form',
										 items:[{
										    fieldLabel:'预约时间段',
										    xtype : 'textfield',
											name : 'YUYUESHIJIANDUAN',
											id : 'YUYUESHIJIANDUAN',
									    	readOnly:true,
									    	cls:'x-readOnly',
											width:'150'
											 }]
									  },{//签发地点
											 columnWidth:1,
											 layout:'form',
											 items:[{
											    fieldLabel:'签发地点',
											    xtype : 'textfield',
												name : 'qianfadidian',
												id : 'qianfadidian',
										    	readOnly:true,
										    	cls:'x-readOnly',
												width:'150'
												 }]
									  },{//签发机关
												 columnWidth:1,
												 layout:'form',
												 items:[{
												    fieldLabel:'签发机关',
												    xtype : 'textfield',
													name : 'qianfajiguan',
													id : 'qianfajiguan',
											    	readOnly:true,
											    	cls:'x-readOnly',
													width:'150'
													 }]
									  },{//有效期限
											 columnWidth:1,
											 layout:'form',
											 items:[{
											    fieldLabel:'有效期限',
											    xtype : 'textfield',
												name : 'youxiaoqixian',
												id : 'youxiaoqixian',
										    	readOnly:true,
										    	cls:'x-readOnly',
												width:'150'
												 }]
								  },{//持证次数
											 columnWidth:1,
											 layout:'form',
											 items:[{
											    fieldLabel:'持证次数',
											    xtype : 'textfield',
												name : 'chizhengcishu',
												id : 'chizhengcishu',
										    	readOnly:true,
										    	cls:'x-readOnly',
												width:'150'
												 }]  
									  },{//所属客户经理
							  	    	 layout:'form',
							  	    	 columnWidth:1,
							  	    	 items:[{
										 xtype : 'userchoose',
										 width : '150',
										 fieldLabel : '<font color=red>*</font>所属客户经理',
										 singleSelect: false,
										 searchField: true					      
								  	         }]
								  	     }
					   ]
	            }]
	      }]
     } ,{
	    layout:'column',
	    items:[{
		  	  title:'联名户信息',
		  	  columnWidth:1,
		  	  id:'lianminghu',
		  	  xtype:'fieldset',
		  	  style:'margin:0 10px;',
		  	  items:[{
					columnWidth:1,
				    layout : 'form',
			        items:[{
						 xtype : 'textfield',
						 width : '200',
						 fieldLabel : '<font color=red>\*</font>联名户姓名拼音',
				    	 titleCollapse : true,
				    	 collapsible : true,
						 allowBlank:false
				         },{
				     	   xtype:'radiogroup',
						   id:'sex',
						   fieldLabel:'<font color=red>\*</font>联名户性别',
						   allowBlank:false,
						   columns:2,
						   anchor:'30%',
						   items:[
						          {boxLabel:'男',name:'sex',inputValue:'1'},
					              {boxLabel:'女',name:'sex',inputValue:'2'}
					             ]
	        	        },{
							xtype : 'combo',
							name : 'CITIZENSHIP1',
							hiddenName : 'CITIZENSHIP1',
							fieldLabel : '<font color="red">*</font>联名户国籍',
							width:'150',
							emptyText:'请选择',
							store : conStore,
							resizable : true,
							valueField : 'key',
							displayField : 'value',
							mode : 'local',
							forceSelection : true,
							triggerAction : 'all'
						 },{//联名户证件类型
							 xtype : 'textfield',
							 width : '200',
							 fieldLabel : '<font color=red>\*</font>证件类型',
					    	 titleCollapse : true,
					    	 collapsible : true,
					    	 readOnly:true,
					    	 cls:'x-readOnly'
					     },{//联名户证件类型码值
							 xtype : 'textfield',
							 width : '200',
                             hidden:true,
					    	 titleCollapse : true,
					    	 collapsible : true,
					    	 readOnly:true,
					    	 cls:'x-readOnly',
					    	 listeners:{
									'select':function(a,b,c){
										if(a.value == '6' || a.value == 'X24' || a.value == 'X3'){
											Ext.getCmp('twIdentNum1').setVisible(true);
											Ext.getCmp('gaIdentNum1').setVisible(false);
										}else if(a.value == '5'){
											Ext.getCmp('gaIdentNum1').setVisible(true);
											Ext.getCmp('twIdentNum1').setVisible(false);
										}else
										{
											Ext.getCmp('twIdentNum1').setVisible(false);
											Ext.getCmp('gaIdentNum1').setVisible(false);
										}
									 }
								  }
					     },{
							 xtype : 'textfield',
							 width : '200',
							 fieldLabel : '<font color=red>\*</font>证件号码',
					    	 titleCollapse : true,
					    	 collapsible : true,
							 readOnly:true,
							 cls:'x-readOnly'
					     },{
							 xtype : 'textfield',
							 width : '200',
							 fieldLabel : '<font color=red>\*</font>台湾国民身份证号码',
							 name:'twIdentNum1',
							 id:'twIdentNum1',
					    	 titleCollapse : true,
					    	 collapsible : true
					     },{
							 xtype : 'textfield',
							 width : '200',
							 fieldLabel : '<font color=red>\*</font>港澳通行证件号码',
							 name:'gaIdentNum1',
							 id:'gaIdentNum1',
					    	 titleCollapse : true,
					    	 collapsible : true
					     }
					      ]
		  	  },{
		  		 columnWidth:.3,
				 layout : 'form',
				 items:[{
					 xtype : 'datefield',
					 id:'LEGAL_EXPIRED_DATE1',
					 width:'150',
					 name : 'LEGAL_EXPIRED_DATE1',
					 fieldLabel : '<font color="red">*</font>有效日期',
					 format:'Y-m-d'
				   }]
		  	  },{
		  		columnWidth:.6,
				layout : 'form',
				items:[{
			     	   xtype:'checkbox',
					   id:'LONGTERM1',
					   boxLabel:'长期有效<font color = blue>(可手工录入证件有效期，格式为：年月日/年-月-日)</font>',
					   name:'LONGTERM1',
					   inputvalue:'1',
					   listeners:{
							'check':function(obj,ischeck){
									if (ischeck == true){
										Ext.getCmp("LEGAL_EXPIRED_DATE1").setValue("9999-12-31");  
										Ext.getCmp("LEGAL_EXPIRED_DATE1").setReadOnly(true);
									}else{
										Ext.getCmp("LEGAL_EXPIRED_DATE1").setValue("");  
										Ext.getCmp("LEGAL_EXPIRED_DATE1").setReadOnly(false);
									}
								}
					   }
    	               }] 
		  	  }/*,{
				columnWidth:1,
			    layout : 'form',
		        items:[{
		        	id : 'gaIdentNum1',
					xtype : 'textfield',
					width : '200',
					fieldLabel : '港澳居民身份证',
			    	titleCollapse : true,
			    	collapsible : true
				    }]
			  },{
				columnWidth:1,
			    layout : 'form',
		        items:[{
		        	id : 'twIdentNum1',
					xtype : 'textfield',
					width : '200',
					fieldLabel : '台湾国民身份证',
			    	titleCollapse : true,
			    	collapsible : true
					    }]
				 }*/,{
					 columnWidth:1,
					 layout: 'form',
					 items:[{
						xtype : 'combo',
						name : 'CITIZENSHIP1',
						hiddenName : 'CITIZENSHIP1',
						fieldLabel : '<font color="red">*</font>联名户证件发证机关所在地',
						emptyText:'请选择',
						width:'150',
						store : dqStore,
						resizable : true,
						valueField : 'key',
						displayField : 'value',
						mode : 'local',
						forceSelection : true,
						triggerAction : 'all'
					 }]
				 }
		  	    ]
	          }]
	       },{
			  layout:'column',
			  items:[{
				  	  title:'本人声明',
				  	  columnWidth:1,
				  	  id:'shengming',
				  	  xtype:'fieldset',
				  	  style:'margin:0 10px;',
				  	  items:[{
				  		      layout:'column',
				  		      items:[{
				        	       columnWidth:1,
				        	       layout:'form',
				        	       items:[{
								     	   xtype:'radiogroup',
										   columns:1,
										   allowBlank:false,
										   anchor:'90%',
										   items:[
										       {boxLabel:'1、仅为中国税收居民',name:'radio1',inputValue:'1'},
									           {boxLabel:'2、仅为非居民',name:'radio1',inputValue:'2'},
									           {boxLabel:'3、既是中国税收居民又是其他国税收居民',name:'radio1',inputValue:'3'}
									          ],
									          listeners:{
													'change':function(a,b){
														if(b.inputValue == '2' || b.inputValue == '3' ){
															Ext.getCmp('shengMing').setVisible(true);
															
														}else
														{
														 Ext.getCmp('shengMing').setVisible(false);
														
														}
													}
												}
					        	        }]
					        	
							   },
							 {layout:'column',
							  columnWidth:1,
							  id:'shengMing',
							  items:[{
			  	        	  layout:'form',
				  		      columnWidth:1,
				  		      labelWidth:100,
				  		      items:[{
				  		    	      xtype:'tbtext',
				  		    	      text:'<br/><b>如您在以上选项中勾选第2项或者第3项，请填写下列信息：</b><br/>'
				  		      }]
				  	         },{
			        	       columnWidth:1,
			        	       layout:'form',
			        	       items:[{
							     	   xtype:'radiogroup',
									   id:'isUNtaxpayer',
									   fieldLabel:'是否为美国纳税人',
									   columns:2,
									   anchor:'30%',
									   items:[
									       {boxLabel:'是',name:'isUNtaxpayer',inputValue:'1'},
								           {boxLabel:'否',name:'isUNtaxpayer',inputValue:'2'}
								          ],
								          listeners:{
												'change':function(a,b){
													if(b.inputValue == '1'){
														Ext.getCmp('USTIN').setVisible(true);
													}else
													{
														Ext.getCmp('USTIN').setVisible(false);
													}
												}
											}
			        	        }]
				           	},{
							     columnWidth:1,
								 layout:'form',
								 items:[{
								    xtype : 'textfield',
									name : 'USTIN',
									id : 'USTIN',
									fieldLabel:'US TIN/SSN',
									width:'200'
									 }]
							},{
							  columnWidth:1,
			  	        	  layout:'form',
				  		      items:[{
				  		    	      xtype:'tbtext',
				  		    	      text:'请填写您需履行纳税义务的所有税收居民国（地区）以及所关联的纳税人识别号(TIN)<br/><br/>'
				  		      }]
					  	    },{
					  	        id:'exceptionHand',
					  	    	columnWidth:1,
					  	    	layout:'form',
					  	    	items:[{
					  	    		  columnWidth:.68,
								  	  layout:'form',
								  	  items:[{
					  	               xtype:"button",
					  	               style:'margin-left:'+($(window).width()/60)+'px;',
					  	               width:'40',
					  	               text:"+",
					  	               handler : function() {
					  	            	   	var exceptionHandling =new String;
					  	            	   	exceptionNumber++;
					  	            	   	var exceptionForm = Ext.getCmp("exceptionHand");
					  	            	   	var configItem=[{
					  	            	   		layout:'form',
					  	                   items:[
					  	                           {
					  	                               xtype:'textfield',
					  	                               labelAlign:'top',
					  	                               width : '200',
					  	                               id:'juMinGuo'+exceptionNumber,
					  	                               name: 'juMinGuo'+exceptionNumber,                                
					  	                               fieldLabel: +exceptionNumber+'.税收居民国（地区）'

					  	                           },
					  	                           {
				  	                                  xtype:'textfield',
				  	                                  labelAlign:'top',
				  	                                  width : '200',
				  	                                  id:'TIN'+exceptionNumber,         
				  	                                  name: 'TIN'+exceptionNumber,                               
				  	                                  fieldLabel: '纳税人识别号（TIN）'
					  	                           }
					  	                         ]
					  	                      }];
					  	                                     exceptionForm.add(configItem[0]);
					  	                                     exceptionForm.doLayout();
					  	                                     }
					  	           }
								  	]},{   
							  	    	columnWidth:.68,
							  	    	layout:'form',
							  	    	items:[
							  	              {
							  	               xtype:"button",
							  	               style:'margin-left:'+($(window).width()/60)+'px;',
							  	               text:"-",
							  	               width:'40',
							  	               handler : function() {
							  	            	 var a = Ext.getCmp('juMinGuo'+exceptionNumber);
							  	               	 var b = Ext.getCmp('TIN'+exceptionNumber);
							  	            	 a.destroy(); 
							  	            	 b.destroy();
							  	            	 exceptionNumber--;
							  	                                     }
							  	              }
							  	              ]
							              },{
									  	       columnWidth:1,
							  	               layout:'form',
							  	               items:[
							  	                      {
							  	                        xtype:'textfield',
							  	                        labelAlign:'top',
							  	                        id:'juMinGuo1',
							  	                        width : '200',
							  	                        name: 'juMinGuo1',                              
							  	                        fieldLabel: '1.税收居民国（地区）'
							  	                      },{
							  	                        xtype:'textfield',
							  	                        labelAlign:'top',
							  	                        width : '200',
							  	                        id:'TIN1',
							  	                        name: 'TIN1',                                
							  	                        fieldLabel: '纳税人识别号（TIN）'
							  	                       }
							  	                      ]
								  	             }]
					  	    },{
							  columnWidth:1,
			  	        	  layout:'form',
				  		      items:[{
				  		    	      xtype:'tbtext',
				  		    	      text:'<br/>如您不能提供居民国（地区）纳税人识别号，请选择原因(TIN)<br/>'
					  		      }]
					  	    },{
				  		      layout:'form',
				  		      columnWidth:1,
				  		      labelWidth:100,
				  		      items:[{
							     	   xtype:'checkbox',
									   id:'RESON',
									   boxLabel:'居民国（地区）不发放纳税人识别号',
									   name:'shengming',
									   inputvalue:'1'
							  	    },{
								  	   xtype:'checkbox',
									   id:'RESON2',
									   boxLabel:'账户持有人未能取得纳税人识别号',
									   name:'shengming',
									   inputvalue:'2' 
							  	    }]
					  	     },{
					  	    	 layout:'form',
					  	    	 columnWidth:1,
					  	    	 items:[{
								 xtype : 'textarea',
								 width : '500',
								 fieldLabel : '请解释具体原因',
						    	 titleCollapse : true,
						    	 collapsible : true					      
					  	         }]
					  	     }]}]
			           }]
			      }]
			  }]
/*store.load();*/


});

Ext.getCmp('JOBREMARK').setVisible(false);
Ext.getCmp('shengMing').setVisible(false);
Ext.getCmp('RELATEDNAME').setVisible(false);
Ext.getCmp('RELATION').setVisible(false);
Ext.getCmp('gaIdentNum').setVisible(false);
Ext.getCmp('twIdentNum').setVisible(false);
Ext.getCmp('USTIN').setVisible(false);
Ext.getCmp('lianminghu').setVisible(false);
Ext.getCmp('JOBNAME').setVisible(false);
Ext.getCmp('JOB').setVisible(false);

/**
 * [crm2EcifAccount CRM到ECIF卡户]
 * @return {[type]} [description]
 */
function crm2EcifAccount(){
	
	var custInfo = {};
	$.ajax({
		url : basepath + '/oneKeyAccountAction!ECIFAndCoreAccount.json',
		type : "post",
		dataType : "json",
		data : {
			'custInfo' : {},
			'custName' : '刘明',
			'identyId' : '123456789012345678',
			'identyType' : '01'
		},
		beforeSend : function(){
			myMask = new Ext.LoadMask(Ext.getBody(), {msg:"正在开户，请稍等..."});
			myMask.show();
		},
		success : function(response) {
			var allowAccount = true;
			var tipCont = "";
			if(response){
			}
	    },
	    complete : function(){
			if(myMask){
				myMask.hide();
			}
		}
	});
}


var account_info1 = new Ext.FormPanel({
	title : '银行信息',
	header:false,
	collapsible : true,
	autoHeight:true,
	autoWidth:true,
	labelWidth:200,//label的宽度
	labelAlign:'left',
	frame:false,//滚动条
	autoScroll : true,
	buttonAlign:'center',
	anchor:'95%',
	buttons:[
		{
	      text:'上一页',
	      handler:function(){
			tab_custinfo.setActiveTab(0);
		  }
        },
        {
           text:'保存',
           handler:function(){
       		alert("baocun");
       		crm2EcifAccount();
           }
        }
    ],
	items:[{}]
});


var tab_custinfo = new Ext.TabPanel({
	activeItem : 0,
	autoScroll : true,
	items:[account_info,account_info1]
});

var panel_custinfo1 = new Ext.Panel({
	title : '个金一键开户',
	activeItem : 0,
	autoScroll : true,
	collapsible : true,
	items:[tab_custinfo]
});


/*var panel=new Ext.Panel({
	layout : 'form', 
	autoScroll: true,
	buttonAlign:'center',
	items : [account_info]
	});	*/





