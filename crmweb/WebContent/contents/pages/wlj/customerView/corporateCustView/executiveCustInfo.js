/**
*@description 360客户视图 对公高管信息
*@author:xiebz
*@since:2014-07-19
*@checkedby:
*/
imports([
        '/contents/pages/com.yucheng.bcrm/com.yucheng.bcrm.js',
        '/contents/pages/common/Com.yucheng.crm.common.ImpExpNew.js'
		]);
var createView = !JsContext.checkGrant('executiveCust_create');
var editView = !JsContext.checkGrant('executiveCust_modify');
//var detailView = !JsContext.checkGrant('executiveCust_detail');
var needCondition = false;
var lookupTypes = ['XD000040',//证件类型
                   'PAR1300083',//发证机构
                   'IF_FLAG',
                   'XD000025',//国籍
                   'XD000020',//民族
                   'XD000001',//籍贯
                   'XD000024',//婚姻
                   'XD000011',//政治面貌
                   'XD000015',//最高学历
                   'XD000339',//联系人类型
                   'XD000016',//性别
                   'XD000308',//是否我行客户
                   'XD000204',//证件是否核查
                   'XD000047',//单位性质
                   'XD000153',//专业
                   'XD000154'//学制
                   ];
var custId =_custId;
Ext.QuickTips.init();
//flag 是否高管 1是0否
var url = basepath+'/acrmFCiOrgExecutiveinfo.json?custId='+custId+'&flag=1';

var certTypeStore = new Ext.data.Store({//证件类型store
	restful:true,   
	autoLoad :true,
	proxy : new Ext.data.HttpProxy({
			url :basepath+'/lookup.json?name=XD000040'
	}),
	reader : new Ext.data.JsonReader({
		root : 'JSON'
	}, [ 'key', 'value' ])
});

var sexTypeStore = new Ext.data.Store({//性别store
	restful:true,   
	autoLoad :true,
	proxy : new Ext.data.HttpProxy({
			url :basepath+'/lookup.json?name=XD000016'
	}),
	reader : new Ext.data.JsonReader({
		root : 'JSON'
	}, [ 'key', 'value' ])
});

var postDataStore = new Ext.data.Store({//是否接受我行寄发的资料
	restful:true,   
	autoLoad :true,
	proxy : new Ext.data.HttpProxy({
			url :basepath+'/lookup.json?name=XD000263'
	}),
	reader : new Ext.data.JsonReader({
		root : 'JSON'
	}, [ 'key', 'value' ])
});

var joinCampStore = new Ext.data.Store({//是否愿意参加联谊活动
	restful:true,   
	autoLoad :true,
	proxy : new Ext.data.HttpProxy({
			url :basepath+'/lookup.json?name=XD000264'
	}),
	reader : new Ext.data.JsonReader({
		root : 'JSON'
	}, [ 'key', 'value' ])
});

var recieveSmsStore = new Ext.data.Store({//是否愿意接收短信
	restful:true,   
	autoLoad :true,
	proxy : new Ext.data.HttpProxy({
			url :basepath+'/lookup.json?name=XD000265'
	}),
	reader : new Ext.data.JsonReader({
		root : 'JSON'
	}, [ 'key', 'value' ])
});

var unitCharStore = new Ext.data.Store({//单位性质
	restful:true,   
	autoLoad :true,
	proxy : new Ext.data.HttpProxy({
			url :basepath+'/lookup.json?name=XD000047'
	}),
	reader : new Ext.data.JsonReader({
		root : 'JSON'
	}, [ 'key', 'value' ])
});

var majorStore = new Ext.data.Store({//专业
	restful:true,   
	autoLoad :true,
	proxy : new Ext.data.HttpProxy({
			url :basepath+'/lookup.json?name=XD000053'
	}),
	reader : new Ext.data.JsonReader({
		root : 'JSON'
	}, [ 'key', 'value' ])
});

var eduSysStore = new Ext.data.Store({//学制
	restful:true,   
	autoLoad :true,
	proxy : new Ext.data.HttpProxy({
			url :basepath+'/lookup.json?name=XD000054'
	}),
	reader : new Ext.data.JsonReader({
		root : 'JSON'
	}, [ 'key', 'value' ])
});

//语言偏好
var langPreferStore = new Ext.data.Store({
	restful:true,   
	autoLoad :true,
	proxy : new Ext.data.HttpProxy({
			url :basepath+'/lookup.json?name=XD000249'
	}),
	reader : new Ext.data.JsonReader({
		root : 'JSON'
	}, [ 'key', 'value' ])
});
//称谓偏好
var titlePreferStore = new Ext.data.Store({
	restful:true,   
	autoLoad :true,
	proxy : new Ext.data.HttpProxy({
			url :basepath+'/lookup.json?name=XD000250'
	}),
	reader : new Ext.data.JsonReader({
		root : 'JSON'
	}, [ 'key', 'value' ])
});
//联络方式偏好
var contactTypeStore = new Ext.data.Store({
	restful:true,   
	autoLoad :true,
	proxy : new Ext.data.HttpProxy({
			url :basepath+'/lookup.json?name=XD000251'
	}),
	reader : new Ext.data.JsonReader({
		root : 'JSON'
	}, [ 'key', 'value' ])
});
//联络频率偏好
var contactFreqStore = new Ext.data.Store({
	restful:true,   
	autoLoad :true,
	proxy : new Ext.data.HttpProxy({
			url :basepath+'/lookup.json?name=XD000252'
	}),
	reader : new Ext.data.JsonReader({
		root : 'JSON'
	}, [ 'key', 'value' ])
});
//联络时间偏好
var contactTimeStore = new Ext.data.Store({
	restful:true,   
	autoLoad :true,
	proxy : new Ext.data.HttpProxy({
			url :basepath+'/lookup.json?name=XD000253'
	}),
	reader : new Ext.data.JsonReader({
		root : 'JSON'
	}, [ 'key', 'value' ])
});
//赠品礼物偏好
var giftPreferStore = new Ext.data.Store({
	restful:true,   
	autoLoad :true,
	proxy : new Ext.data.HttpProxy({
			url :basepath+'/lookup.json?name=XD000254'
	}),
	reader : new Ext.data.JsonReader({
		root : 'JSON'
	}, [ 'key', 'value' ])
});
//出行交通工具偏好
var vehiclePreferStore = new Ext.data.Store({
	restful:true,   
	autoLoad :true,
	proxy : new Ext.data.HttpProxy({
			url :basepath+'/lookup.json?name=XD000255'
	}),
	reader : new Ext.data.JsonReader({
		root : 'JSON'
	}, [ 'key', 'value' ])
});
//消费习惯
var consumHabitStore = new Ext.data.Store({
	restful:true,   
	autoLoad :true,
	proxy : new Ext.data.HttpProxy({
			url :basepath+'/lookup.json?name=XD000256'
	}),
	reader : new Ext.data.JsonReader({
		root : 'JSON'
	}, [ 'key', 'value' ])
});
//保险倾向
var insurancePreferStore = new Ext.data.Store({
	restful:true,   
	autoLoad :true,
	proxy : new Ext.data.HttpProxy({
			url :basepath+'/lookup.json?name=XD000257'
	}),
	reader : new Ext.data.JsonReader({
		root : 'JSON'
	}, [ 'key', 'value' ])
});
//投资经验
var investExprStore = new Ext.data.Store({
	restful:true,   
	autoLoad :true,
	proxy : new Ext.data.HttpProxy({
			url :basepath+'/lookup.json?name=XD000258'
	}),
	reader : new Ext.data.JsonReader({
		root : 'JSON'
	}, [ 'key', 'value' ])
});
//投资风险偏好
var riskPreferStore = new Ext.data.Store({
	restful:true,   
	autoLoad :true,
	proxy : new Ext.data.HttpProxy({
			url :basepath+'/lookup.json?name=XD000259'
	}),
	reader : new Ext.data.JsonReader({
		root : 'JSON'
	}, [ 'key', 'value' ])
});
//投资方向偏好
var investPositionStore = new Ext.data.Store({
	restful:true,   
	autoLoad :true,
	proxy : new Ext.data.HttpProxy({
			url :basepath+'/lookup.json?name=XD000344'
	}),
	reader : new Ext.data.JsonReader({
		root : 'JSON'
	}, [ 'key', 'value' ])
});
//投资周期偏好
var investCycleStore = new Ext.data.Store({
	restful:true,   
	autoLoad :true,
	proxy : new Ext.data.HttpProxy({
			url :basepath+'/lookup.json?name=XD000345'
	}),
	reader : new Ext.data.JsonReader({
		root : 'JSON'
	}, [ 'key', 'value' ])
});
//金融业务类型偏好
var financeBusinessStore = new Ext.data.Store({
	restful:true,   
	autoLoad :true,
	proxy : new Ext.data.HttpProxy({
			url :basepath+'/lookup.json?name=XD000321'
	}),
	reader : new Ext.data.JsonReader({
		root : 'JSON'
	}, [ 'key', 'value' ])
});
//客户风险承受能力
var investStyleStore = new Ext.data.Store({
	restful:true,   
	autoLoad :true,
	proxy : new Ext.data.HttpProxy({
			url :basepath+'/lookup.json?name=XD000260'
	}),
	reader : new Ext.data.JsonReader({
		root : 'JSON'
	}, [ 'key', 'value' ])
});
//主要的投资目标
var investTargetStore = new Ext.data.Store({
	restful:true,   
	autoLoad :true,
	proxy : new Ext.data.HttpProxy({
			url :basepath+'/lookup.json?name=XD000261'
	}),
	reader : new Ext.data.JsonReader({
		root : 'JSON'
	}, [ 'key', 'value' ])
});
//主要的投资渠道
var investChannelStore = new Ext.data.Store({
	restful:true,   
	autoLoad :true,
	proxy : new Ext.data.HttpProxy({
			url :basepath+'/lookup.json?name=XD000262'
	}),
	reader : new Ext.data.JsonReader({
		root : 'JSON'
	}, [ 'key', 'value' ])
});
//客户喜好电子渠道类型
var likeEchanlStore = new Ext.data.Store({
	restful:true,   
	autoLoad :true,
	proxy : new Ext.data.HttpProxy({
			url :basepath+'/lookup.json?name=XD000313'
	}),
	reader : new Ext.data.JsonReader({
		root : 'JSON'
	}, [ 'key', 'value' ])
});
//客户喜好休闲类型
var likeLeisureStore = new Ext.data.Store({
	restful:true,   
	autoLoad :true,
	proxy : new Ext.data.HttpProxy({
			url :basepath+'/lookup.json?name=XD000318'
	}),
	reader : new Ext.data.JsonReader({
		root : 'JSON'
	}, [ 'key', 'value' ])
});
//客户喜好媒体类型
var likeMediaStore = new Ext.data.Store({
	restful:true,   
	autoLoad :true,
	proxy : new Ext.data.HttpProxy({
			url :basepath+'/lookup.json?name=XD000314'
	}),
	reader : new Ext.data.JsonReader({
		root : 'JSON'
	}, [ 'key', 'value' ])
});
//客户喜好运动类型
var likeSportStore = new Ext.data.Store({
	restful:true,   
	autoLoad :true,
	proxy : new Ext.data.HttpProxy({
			url :basepath+'/lookup.json?name=XD000319'
	}),
	reader : new Ext.data.JsonReader({
		root : 'JSON'
	}, [ 'key', 'value' ])
});
//客户喜好杂志类型
var likeMagazineStore = new Ext.data.Store({
	restful:true,   
	autoLoad :true,
	proxy : new Ext.data.HttpProxy({
			url :basepath+'/lookup.json?name=XD000320'
	}),
	reader : new Ext.data.JsonReader({
		root : 'JSON'
	}, [ 'key', 'value' ])
});
//客户喜好电影节目类型
var likeFilmStore = new Ext.data.Store({
	restful:true,   
	autoLoad :true,
	proxy : new Ext.data.HttpProxy({
			url :basepath+'/lookup.json?name=XD000312'
	}),
	reader : new Ext.data.JsonReader({
		root : 'JSON'
	}, [ 'key', 'value' ])
});
//客户喜好宠物类型
var likePetStore = new Ext.data.Store({
	restful:true,   
	autoLoad :true,
	proxy : new Ext.data.HttpProxy({
			url :basepath+'/lookup.json?name=XD000311'
	}),
	reader : new Ext.data.JsonReader({
		root : 'JSON'
	}, [ 'key', 'value' ])
});
//客户喜好收藏类型
var likeCollectionStore = new Ext.data.Store({
	restful:true,   
	autoLoad :true,
	proxy : new Ext.data.HttpProxy({
			url :basepath+'/lookup.json?name=XD000316'
	}),
	reader : new Ext.data.JsonReader({
		root : 'JSON'
	}, [ 'key', 'value' ])
});
//客户喜好投资类型
var likeInvestStore = new Ext.data.Store({
	restful:true,   
	autoLoad :true,
	proxy : new Ext.data.HttpProxy({
			url :basepath+'/lookup.json?name=XD000317'
	}),
	reader : new Ext.data.JsonReader({
		root : 'JSON'
	}, [ 'key', 'value' ])
});
//客户喜好品牌类型
var likeBrandStore = new Ext.data.Store({
	restful:true,   
	autoLoad :true,
	proxy : new Ext.data.HttpProxy({
			url :basepath+'/lookup.json?name=XD000315'
	}),
	reader : new Ext.data.JsonReader({
		root : 'JSON'
	}, [ 'key', 'value' ])
});

var fields = [
             //基本信息
  		    {name: 'LINKMAN_ID',hidden : true},
  		    {name: 'IDENT_TYPE', text : '证件类型',translateType:'XD000040',allowBlank:false},
  		    {name: 'IDENT_NO', text : '证件号码',allowBlank:false,vtype:'non-chinese'},
  		    {name: 'LINKMAN_NAME', text : '姓名',allowBlank:false}, 
  		    {name: 'GENDER', text : '性别',allowBlank:false,translateType:'XD000016',resutlWidth:50}, 
  		    {name: 'BIRTHDAY', text : '出生日期',resutlWidth:70,allowBlank:false,xtype:'datefield',format:'Y-m-d',dataType:'date'},                                   
  		    {name: 'LINKMAN_TITLE',text:'称谓',resutlWidth:70},  
	        {name: 'WORK_POSITION', text : '职位',resutlWidth:70},
	        {name: 'OFFICE_TEL', text : '办公电话',resutlWidth:90,gridField:false,hidden:true},//无结尾的name，代表储存进数据库的“原值”
	        {name: 'OFFICE_TELN', text : '办公国家地区号',resutlWidth:120,gridField:false, //N结尾，区号(国家地区)，填入后前台监听处理与X结合发往“原值”
	        		listeners:{
							blur : function(){
								var reg = /^[0\+]\d{2,3}$/;
								if(getCurrentView()._defaultTitle == '新增'){
									var nation = getCreateView().contentPanel.getForm().findField('OFFICE_TELN').getValue();
									if(!reg.test(nation)){
										Ext.Msg.alert('提示','请输入正确格式的区号！');
										getCreateView().contentPanel.getForm().findField('OFFICE_TELN').setValue("")
										return false;
									}
									var result = nation+"/"+getCreateView().contentPanel.getForm().findField('OFFICE_TELX').getValue();
									getCreateView().contentPanel.getForm().findField('OFFICE_TEL').setValue(result);
								}
								if(getCurrentView()._defaultTitle == '修改'){
									var nation = getEditView().contentPanel.getForm().findField('OFFICE_TELN').getValue();
									if(!reg.test(nation)){
										Ext.Msg.alert('提示','请输入正确格式的区号！');
										getEditView().contentPanel.getForm().findField('OFFICE_TELN').setValue("")
										return false;
									}
									var result = nation+"/"+getEditView().contentPanel.getForm().findField('OFFICE_TELX').getValue();
									getEditView().contentPanel.getForm().findField('OFFICE_TEL').setValue(result);
								}
							}
				}
	        },
	        {name: 'OFFICE_TELX', text : '办公电话',resutlWidth:120,vtype:'telephone',gridField:false,  //X结尾，实际填入电话号码，填入后前台监听处理与N结合发往“原值”
	        		listeners:{
						blur : function(){
								if(getCurrentView()._defaultTitle == '新增'){
									var nation = getCreateView().contentPanel.getForm().findField('OFFICE_TELN').getValue();
									var result = nation+"/"+getCreateView().contentPanel.getForm().findField('OFFICE_TELX').getValue();
									getCreateView().contentPanel.getForm().findField('OFFICE_TEL').setValue(result);
								}
								if(getCurrentView()._defaultTitle == '修改'){
									var nation = getEditView().contentPanel.getForm().findField('OFFICE_TELN').getValue();
									var result = nation+"/"+getEditView().contentPanel.getForm().findField('OFFICE_TELX').getValue();
									getEditView().contentPanel.getForm().findField('OFFICE_TEL').setValue(result);
								}
						}
					}
	        },
	        {name: 'OFFICE_TELL', text : '办公电话',resutlWidth:120},//复写结尾，数据库SQL语句REPLACE别名字段，用于显示“-”连接结果
	        {name: 'MOBILE', text : '手机号码',resutlWidth:90,gridField:false,hidden:true},
	        {name: 'MOBILEN', text : '手机国家地区号',resutlWidth:120,gridField:false,
	        		listeners:{
							blur : function(){
								var reg = /^[0\+]\d{2,3}$/;
								if(getCurrentView()._defaultTitle == '新增'){
									var nation = getCreateView().contentPanel.getForm().findField('MOBILEN').getValue();
									if(!reg.test(nation)){
										Ext.Msg.alert('提示','请输入正确格式的区号！');
										getCreateView().contentPanel.getForm().findField('MOBILEN').setValue("")
										return false;
									}
									var result = nation+"/"+getCreateView().contentPanel.getForm().findField('MOBILEX').getValue();
									getCreateView().contentPanel.getForm().findField('MOBILE').setValue(result);
								}
								if(getCurrentView()._defaultTitle == '修改'){
									var nation = getEditView().contentPanel.getForm().findField('MOBILEN').getValue();
									if(!reg.test(nation)){
										Ext.Msg.alert('提示','请输入正确格式的区号！');
										getEditView().contentPanel.getForm().findField('MOBILEN').setValue("")
										return false;
									}
									var result = nation+"/"+getEditView().contentPanel.getForm().findField('MOBILEX').getValue();
									getEditView().contentPanel.getForm().findField('MOBILE').setValue(result);
								}
							}
				}
	        },
	        {name: 'MOBILEX', text : '手机号码',resutlWidth:120,vtype:'telephone',gridField:false,
	        		listeners:{
						blur : function(){
								if(getCurrentView()._defaultTitle == '新增'){
									var nation = getCreateView().contentPanel.getForm().findField('MOBILEN').getValue();
									var result = nation+"/"+getCreateView().contentPanel.getForm().findField('MOBILEX').getValue();
									getCreateView().contentPanel.getForm().findField('MOBILE').setValue(result);
								}
								if(getCurrentView()._defaultTitle == '修改'){
									var nation = getEditView().contentPanel.getForm().findField('MOBILEN').getValue();
									var result = nation+"/"+getEditView().contentPanel.getForm().findField('MOBILEX').getValue();
									getEditView().contentPanel.getForm().findField('MOBILE').setValue(result);
								}
						}
					}
	        },
	        {name: 'MOBILEE', text : '手机号码',resutlWidth:120},
//	        {name: 'EXTENSION_TEL', text : '分机号码',allowBlank:false,resutlWidth:90},
	        {name: 'HOME_TEL', text : '家庭电话',resutlWidth:90,gridField:false,hidden:true},
	        {name: 'HOME_TELN', text : '家庭国家地区号',resutlWidth:120,gridField:false,
	        		listeners:{
							blur : function(){
								var reg = /^[0\+]\d{2,3}$/;
								if(getCurrentView()._defaultTitle == '新增'){
									var nation = getCreateView().contentPanel.getForm().findField('HOME_TELN').getValue();
									if(!reg.test(nation)){
										Ext.Msg.alert('提示','请输入正确格式的区号！');
										getCreateView().contentPanel.getForm().findField('HOME_TELN').setValue("")
										return false;
									}
									var result = nation+"/"+getCreateView().contentPanel.getForm().findField('HOME_TELX').getValue();
									getCreateView().contentPanel.getForm().findField('HOME_TEL').setValue(result);
								}
								if(getCurrentView()._defaultTitle == '修改'){
									var nation = getEditView().contentPanel.getForm().findField('HOME_TELN').getValue();
									if(!reg.test(nation)){
										Ext.Msg.alert('提示','请输入正确格式的区号！');
										getEditView().contentPanel.getForm().findField('HOME_TELN').setValue("")
										return false;
									}
									var result = nation+"/"+getEditView().contentPanel.getForm().findField('HOME_TELX').getValue();
									getEditView().contentPanel.getForm().findField('HOME_TEL').setValue(result);
								}
							}
				}
	        },
	        {name: 'HOME_TELX', text : '家庭电话',resutlWidth:120,vtype:'telephone',gridField:false,
	        		listeners:{
						blur : function(){
								if(getCurrentView()._defaultTitle == '新增'){
									var nation = getCreateView().contentPanel.getForm().findField('HOME_TELN').getValue();
									var result = nation+"/"+getCreateView().contentPanel.getForm().findField('HOME_TELX').getValue();
									getCreateView().contentPanel.getForm().findField('HOME_TEL').setValue(result);
								}
								if(getCurrentView()._defaultTitle == '修改'){
									var nation = getEditView().contentPanel.getForm().findField('HOME_TELN').getValue();
									var result = nation+"/"+getEditView().contentPanel.getForm().findField('HOME_TELX').getValue();
									getEditView().contentPanel.getForm().findField('HOME_TEL').setValue(result);
								}
						}
					}
	        },
	        {name: 'HOME_TELL', text : '家庭电话',resutlWidth:120},
	        {name: 'FEX', text : '传真',resutlWidth:120,gridField:false,hidden:true},
	        {name: 'FEXN', text : '传真国家地区号',resutlWidth:120,gridField:false,
	        		listeners:{
							blur : function(){
								var reg = /^[0\+]\d{2,3}$/;
								if(getCurrentView()._defaultTitle == '新增'){
									var nation = getCreateView().contentPanel.getForm().findField('FEXN').getValue();
									if(!reg.test(nation)){
										Ext.Msg.alert('提示','请输入正确格式的区号！');
										getCreateView().contentPanel.getForm().findField('FEXN').setValue("");
										return false;
									}
									var result = nation+"/"+getCreateView().contentPanel.getForm().findField('FEXY').getValue();
									getCreateView().contentPanel.getForm().findField('FEX').setValue(result);
								}
								if(getCurrentView()._defaultTitle == '修改'){
									var nation = getEditView().contentPanel.getForm().findField('FEXN').getValue();
									if(!reg.test(nation)){
										Ext.Msg.alert('提示','请输入正确格式的区号！');
										getEditView().contentPanel.getForm().findField('FEXN').setValue("");
										return false;
									}
									var result = nation+"/"+getEditView().contentPanel.getForm().findField('FEXY').getValue();
									getEditView().contentPanel.getForm().findField('FEX').setValue(result);
								}
							}
				}
	        },
	        {name: 'FEXY', text : '传真',resutlWidth:120,gridField:false, //FEXX与下FEXX重复，故换成FEXY
	        		listeners:{
						blur : function(){
								if(getCurrentView()._defaultTitle == '新增'){
									var nation = getCreateView().contentPanel.getForm().findField('FEXN').getValue();
									var result = nation+"/"+getCreateView().contentPanel.getForm().findField('FEXY').getValue();
									getCreateView().contentPanel.getForm().findField('FEX').setValue(result);
								}
								if(getCurrentView()._defaultTitle == '修改'){
									var nation = getEditView().contentPanel.getForm().findField('FEXN').getValue();
									var result = nation+"/"+getEditView().contentPanel.getForm().findField('FEXY').getValue();
									getEditView().contentPanel.getForm().findField('FEX').setValue(result);
								}
						}
					}
	        },
	        {name: 'FEXX', text : '传真',resutlWidth:120},
	        {name: 'EMAIL', text : '电子邮件',resutlWidth:120,vtype:'email'},
	        {name: 'REMARK', text : '其他',xtype:'textarea'},
	        
	        {name: 'LINKMAN_TYPE', text : '干系人类型',translateType:'XD000339',gridField:false},
	        {name: 'ORG_CUST_ID', text : '机构客户编号',gridField:false},
  		    {name: 'LINKMAN_EN_NAME', text : '干系人英文名',gridField:false},
  		    {name: 'IS_THIS_BANK_CUST', text : '是否我行客户',gridField:false,translateType:'XD000308'},
  		    {name: 'INDIV_CUS_ID', text : '个人客户编号',gridField:false},
  		    {name: 'IDENT_REG_ADDR', text : '证件注册地址',gridField:false},
  		    {name: 'IDENT_REG_ADDR_POST', text : '证件注册地址邮编',gridField:false},
  		    {name: 'IDENT_EXPIRED_DATE', text : '证件失效日期',gridField:false,xtype:'datefield',format:'Y-m-d',dataType:'date'},
  		    {name: 'IDENT_IS_VERIFY', text : '证件是否核查',gridField:false,translateType:'XD000204'},
  		    
  		    {name: 'CITIZENSHIP', text : '国籍',gridField:false,translateType:'XD000025'},
  		    {name: 'NATIONALITY', text : '民族',gridField:false,translateType:'XD000020'},
  		    {name: 'NATIVEPLACE', text : '籍贯',gridField:false,translateType:'XD000001'},
  		    {name: 'HIGHEST_SCHOOLING', text : '最高学历',gridField:false,translateType:'XD000015'},
		    {name: 'MARRIAGE', text : '婚姻状况',gridField:false,translateType:'XD000024'},
		    {name: 'POLITICAL_FACE', text : '政治面貌',gridField:false,translateType:'XD000011'},
		    {name: 'OFFICE_TEL2', text : '办公电话2',gridField:false},
		    {name: 'HOME_TEL2', text : '家庭电话2',gridField:false},
		    {name: 'MOBILE2', text : '手机号码2',gridField:false},

		    {name: 'ADDRESS', text : '地址信息',gridField:false},
		    {name: 'ZIP_CODE', text : '邮政编码',gridField:false},
		    {name: 'WORK_DEPT', text : '任职部门',gridField:false},
		    {name: 'START_DATE', text : '职位开始日期',gridField:false,xtype:'datefield',format:'Y-m-d',dataType:'date'},
		    {name: 'END_DATE', text : '职位结束日期',gridField:false,xtype:'datefield',format:'Y-m-d',dataType:'date'},
	        {name: 'LAST_UPDATE_SYS', text : '最后更新系统',gridField:false},
	        {name: 'LAST_UPDATE_USER', text : '最后更新人',gridField:false},	
	        {name: 'LAST_UPDATE_TMM', text : '统计日期',gridField:false},
	        {name: 'TX_SEQ_NO', text : '交易流水号',gridField:false},
	        //从业经历
	        {name:'UNIT_NAME',text:'单位名称',gridField:false},
	        {name:'UNIT_CHAR',text:'单位性质',gridField:false,translateType:'XD000047'},
	        {name:'WORK_DEPT1',text:'所在部门',gridField:false},
	        {name:'LAST_UPDATE_USER1',text:'最后更新人',gridField:false},
	        {name:'POSITION',text:'担任职务',gridField:false},
	        {name:'UNIT_TEL',text:'单位电话',gridField:false},
	        {name:'UNIT_ADDRESS',text:'单位地址',gridField:false},
	        {name:'LAST_UPDATE_TM1',text:'最后更新时间',gridField:false},
	        {name:'UNIT_ZIPCODE',text:'单位邮编',gridField:false},
	        {name:'START_DATE1',text:'开始日期',gridField:false},
	        {name:'END_DATE1',text:'结束日期',gridField:false},
	        //个人资历
	        {name:'UNIVERSITY',text:'所在学校',gridField:false},
	        {name:'COLLEGE',text:'所在院系',gridField:false},
	        {name:'MAJOR',text:'专业',gridField:false,translateType:'XD000153'},
	        {name:'LAST_UPDATE_USER2',text:'最后更新人',gridField:false},
	        {name:'EDU_SYS',text:'学制',gridField:false,translateType:'XD000154'},
	        {name:'CERTIFICATE_NO',text:'学历证书号',gridField:false},
	        {name:'DIPLOMA_NO',text:'学位证书号',gridField:false},
	        {name:'LAST_UPDATE_TM2',text:'最后更新时间',gridField:false},
	        {name:'START_DATE2',text:'开始日期',gridField:false},
	        {name:'END_DATE2',text:'结束日期',gridField:false},
	        //个人偏好
	        {name:'LANG_PREFER',text:'语言偏好',gridField:false},
	        {name:'TITLE_PREFER',text:'称谓偏好',gridField:false},
	        {name:'CONTACT_TYPE',text:'联络方式偏好',gridField:false},
	        {name:'CONTACT_FREQ_PREFER',text:'联络频率偏好',gridField:false},
	        {name:'CONTACT_TIME_PREFER',text:'联络时间偏好',gridField:false},
	        {name:'GIFT_PREFER',text:'赠品礼物偏好',gridField:false},
	        {name:'VEHICLE_PREFER',text:'出行交通工具偏好',gridField:false},
	        {name:'LAST_UPDATE_USER3',text:'最后更新人',gridField:false},
	        {name:'CONSUM_HABIT',text:'消费习惯',gridField:false},
	        {name:'INSURANCE_PREFER',text:'保险倾向',gridField:false},
	        {name:'INVEST_EXPR',text:'投资经验',gridField:false},
	        {name:'RISK_PREFER',text:'投资风险偏好',gridField:false},
	        {name:'INVEST_POSITION',text:'投资方向偏好',gridField:false},
	        {name:'INVEST_CYCLE',text:'投资周期偏好',gridField:false},
	        {name:'FINANCE_BUSINESS_PREFER',text:'金融业务类型偏好',gridField:false},
	        {name:'LAST_UPDATE_TM3',text:'最后更新时间',gridField:false},
	        {name:'INTEREST_INVESTMENT',text:'感兴趣的投资信息',gridField:false},
	        {name:'INVEST_STYLE',text:'客户风险承受能力',gridField:false},
	        {name:'INVEST_TARGET',text:'主要的投资目标',gridField:false},
	        {name:'INVEST_CHANNEL',text:'主要的投资渠道',gridField:false},
	        {name:'POST_DATA_FLAG',text:'是否接受我行寄发的资料',gridField:false},
	        {name:'JOIN_CAMP_FLAG',text:'是否愿意参加联谊活动',gridField:false},
	        {name:'RECEIVE_SMS_FLAG',text:'是否愿意接受短信',gridField:false},
            {name:'WELCOME_TEXT',text:'个人欢迎文字',gridField:false},
	        //个人兴趣爱好
	        {name:'LIKE_ECHANL_TYPE',text:'客户喜好电子渠道类型',gridField:false},
	        {name:'LIKE_LEISURE_TYPE',text:'客户喜好休闲类型',gridField:false},
	        {name:'LIKE_MEDIA_TYPE',text:'客户喜好媒体类型',gridField:false},
	        {name:'LIKE_SPORT_TYPE',text:'客户喜好运动类型',gridField:false},
	        {name:'LIKE_MAGAZINE_TYPE',text:'客户喜好杂志类型',gridField:false},
	        {name:'LIKE_FILM_TYPE',text:'客户喜好电影节目类型',gridField:false},
	        {name:'LAST_UPDATE_USER4',text:'最后更新人',gridField:false},
	        {name:'LIKE_PET_TYPE',text:'客户喜好宠物类型',gridField:false},
	        {name:'LIKE_COLLECTION_TYPE',text:'客户喜好收藏类型',gridField:false},
	        {name:'LIKE_INVEST_TYPE',text:'客户喜好投资类型',gridField:false},
	        {name:'LIKE_BRAND_TYPE',text:'客户喜好品牌类型',gridField:false},
	        {name:'LIKE_BRAND_TEXT',text:'客户其他喜好品牌',gridField:false},
	        {name:'FINA_SERV',text:'希望得到的理财服务',gridField:false},
	        {name:'LAST_UPDATE_TM4',text:'最后更新时间',gridField:false},
	        {name:'CONTACT_TYPE1',text:'希望客户经理的联系方式',gridField:false},
	        {name:'FINA_NEWS',text:'希望得到的理财资讯',gridField:false},
	        {name:'SALON',text:'希望参加的沙龙活动',gridField:false},
	        {name:'INTERESTS',text:'个人兴趣爱好',gridField:false},
	        {name:'AVOID',text:'禁忌',gridField:false},
	        {name:'OTHER',text:'其他',gridField:false},
	        //家庭主要成员
	        {name:'MEMBERNAME',text:'成员名称',gridField:false},
	        {name:'FAMILYRELA',text:'家庭成员关系',gridField:false},
	        {name:'MEMBERCRET_TYP',text:'成员证件类型',gridField:false},
	        {name:'MEMBERCRET_NO',text:'成员证件号码',gridField:false},
	        {name:'TEL',text:'电话',gridField:false},
	        {name:'MOBILE1',text:'手机号码',gridField:false},
	        {name:'EMAIL1',text:'邮件地址',gridField:false},
	        {name:'BIRTHDAY1',text:'生日',gridField:false},
	        {name:'COMPANY',text:'家族成员所在企业名称',gridField:false},
	        {name:'LAST_UPDATE_USER5',text:'最后更新人',gridField:false},
	        {name:'LAST_UPDATE_TM5',text:'最后更新时间',gridField:false},
	        {name:'REMARK1',text:'备注',gridField:false}
  		   ];

var createFormViewer =[{
	fields : ['IDENT_TYPE','IDENT_NO','LINKMAN_NAME'],
	fn : function(IDENT_TYPE,IDENT_NO,LINKMAN_NAME){
		return [IDENT_TYPE,IDENT_NO,LINKMAN_NAME];
	}
},{
		columnCount:0.94,
		fields : ['IDENT_TYPE','IDENT_NO','LINKMAN_NAME'],
		fn : function(IDENT_TYPE,IDENT_NO,LINKMAN_NAME){
			return [createPanel];
		}
},{
	fields : ['LINKMAN_ID','GENDER','BIRTHDAY','LINKMAN_TITLE','WORK_POSITION','OFFICE_TEL','OFFICE_TELN','OFFICE_TELX','MOBILE','MOBILEN','MOBILEX','HOME_TEL','HOME_TELN','HOME_TELX','FEX','FEXN','FEXY','EMAIL'],
	fn : function(LINKMAN_ID,GENDER,BIRTHDAY,LINKMAN_TITLE,WORK_POSITION,OFFICE_TEL,OFFICE_TELN,OFFICE_TELX,MOBILE,MOBILEN,MOBILEX,HOME_TEL,HOME_TELN,HOME_TELX,FEX,FEXN,FEXY,EMAIL){
		return [LINKMAN_ID,GENDER,BIRTHDAY,LINKMAN_TITLE,WORK_POSITION,OFFICE_TEL,OFFICE_TELN,OFFICE_TELX,MOBILE,MOBILEN,MOBILEX,HOME_TEL,HOME_TELN,HOME_TELX,FEX,FEXN,FEXY,EMAIL];
	}
},{
	columnCount:0.95,
	fields : ['REMARK'],
	fn : function(REMARK){
		return [REMARK];
	}
}];

var editFormViewer = [{
	fields : ['IDENT_TYPE','IDENT_NO','LINKMAN_NAME'],
	fn : function(IDENT_TYPE,IDENT_NO,LINKMAN_NAME){
		return [IDENT_TYPE,IDENT_NO,LINKMAN_NAME];
	}
},{
		columnCount:0.94,
		fields : ['IDENT_TYPE','IDENT_NO','LINKMAN_NAME'],
		fn : function(IDENT_TYPE,IDENT_NO,LINKMAN_NAME){
			return [editPanel];
		}
},{
	fields : ['LINKMAN_ID','GENDER','BIRTHDAY','LINKMAN_TITLE','WORK_POSITION','OFFICE_TEL','OFFICE_TELN','OFFICE_TELX','MOBILE','MOBILEN','MOBILEX','HOME_TEL','HOME_TELN','HOME_TELX','FEX','FEXN','FEXY','EMAIL'],
	fn : function(LINKMAN_ID,GENDER,BIRTHDAY,LINKMAN_TITLE,WORK_POSITION,OFFICE_TEL,OFFICE_TELN,OFFICE_TELX,MOBILE,MOBILEN,MOBILEX,HOME_TEL,HOME_TELN,HOME_TELX,FEX,FEXN,FEXY,EMAIL){
//		IDENT_TYPE.readOnly = true;
//		IDENT_NO.readdOnly = true;
//		LINKMAN_NAME.readOnly = true;
		return [LINKMAN_ID,GENDER,BIRTHDAY,LINKMAN_TITLE,WORK_POSITION,OFFICE_TEL,OFFICE_TELN,OFFICE_TELX,MOBILE,MOBILEN,MOBILEX,HOME_TEL,HOME_TELN,HOME_TELX,FEX,FEXN,FEXY,EMAIL];
	}
},{
	columnCount:0.95,
	fields : ['REMARK'],
	fn : function(REMARK){
		return [REMARK];
	}
}];

var tbar = [{
	text : '删除',
	hidden:JsContext.checkGrant('executiveCust_delete'),
	handler : function(){
		if(getSelectedData() == false){
			Ext.Msg.alert('提示','请选择一条数据！');
			return false;
		}else{
			var messageId=getSelectedData().data.LINKMAN_ID;
			Ext.MessageBox.confirm('提示','确定删除吗?',function(buttonId){
				if(buttonId.toLowerCase() == "no"){
				return false;
				} 
			    Ext.Ajax.request({
                    url: basepath+'/acrmFCiOrgExecutiveinfo!batchDestroy.json?messageId='+messageId,                                
                    success : function(){
                        Ext.Msg.alert('提示', '删除成功');
                        reloadCurrentData();
                    },
                    failure : function(){
                        Ext.Msg.alert('提示', '删除失败');
                        reloadCurrentData();
                    }
                });
		});
	}
}
},new Com.yucheng.crm.common.NewExpButton({
    formPanel : 'searchCondition',
    hidden:JsContext.checkGrant('executiveCust_export'),
    url : basepath+'/acrmFCiOrgExecutiveinfo.json?custId='+custId+'&flag=1'
})];

var opForm = new Ext.form.FormPanel({
	id : 'opForm',
	layout : 'form',
	autoHeight : true,
	labelAlign : 'right',
	frame : true,
	buttonAlign : "center",
	items:[{
		xtype:'fieldset',
		title:'基本信息',
		titleCollapse : true,
		collapsible : true,
		autoHeight : true,
		collapsed :false,
		anchor : '95%',
		items:[{
			layout : 'column',
			items:[{
				layout : 'form',
				columnWidth : .33,
				items:[
				       {name:'IDENT_TYPE',fieldLabel:'证件类型',xtype:'combo',labelStyle:'text-align:right;',anchor:'95%',
				    	   store:certTypeStore,valueField : 'key',displayField : 'value',
				    	   typeAhead : true,forceSelection : true,triggerAction : 'all',emptyText : '请选择',selectOnFocus : true,disabled : true},
				       {name:'IDENT_NO',fieldLabel:'证件号码',xtype:'textfield',labelStyle:'text-align:right;',anchor:'95%',disabled : true},
				       {name:'LINKMAN_NAME',fieldLabel:'姓名',xtype:'textfield',labelStyle:'text-align:right;',anchor:'95%',disabled : true},
					   {name:'GENDER',fieldLabel:'性别',xtype:'combo',labelStyle:'text-align:right;',anchor:'95%',
				    	   store:sexTypeStore,valueField : 'key',displayField : 'value',disabled : true,
				    	   typeAhead : true,forceSelection : true,triggerAction : 'all',emptyText : '请选择',selectOnFocus : true},
				       {name:'BIRTHDAY',fieldLabel:'出生日期',xtype:'datefield',format : 'Y-m-d',labelStyle:'text-align:right;',anchor:'95%',disabled : true}
				       ]
			},{
				layout : 'form',
				columnWidth : .33,
				items:[
				       {name:'LINKMAN_TITLE',fieldLabel:'称谓',xtype:'textfield',labelStyle:'text-align:right;',anchor:'95%',disabled : true},
				       {name:'WORK_POSITION',fieldLabel:'职位',xtype:'textfield',labelStyle:'text-align:right;',anchor:'95%',disabled : true},
				       {name:'OFFICE_TELL',fieldLabel:'办公电话',xtype:'textfield',labelStyle:'text-align:right;',anchor:'95%',disabled : true},
				       {name:'MOBILEE',fieldLabel:'手机号码',xtype:'textfield',labelStyle:'text-align:right;',anchor:'95%',disabled : true},
//				       {name:'EXTENSION_TEL',fieldLabel:'分机号码',xtype:'textfield',labelStyle:'text-align:right;',anchor:'95%'}
				       {name:'LAST_UPDATE_USER',fieldLabel:'最后更新人',xtype:'textfield',labelStyle:'text-align:right;',anchor:'95%',disabled : true}
				       ]
			},{
				layout : 'form',
				columnWidth : .33,
				items:[
				       {name:'HOME_TELL',fieldLabel:'家庭电话',xtype:'textfield',labelStyle:'text-align:right;',anchor:'95%',disabled : true},
					   {name:'EMAIL',fieldLabel:'电子邮件',xtype:'textfield',labelStyle:'text-align:right;',anchor:'95%',disabled : true},
					   {name:'FEXX',fieldLabel:'传真',xtype:'textfield',labelStyle:'text-align:right;',anchor:'95%',disabled : true},
					   {name:'TX_SEQ_NO',fieldLabel:'交易流水号',xtype:'textfield',labelStyle:'text-align:right;',anchor:'95%',disabled : true},
					   {name:'LAST_UPDATE_TMM',fieldLabel:'统计日期',xtype:'textfield',labelStyle:'text-align:right;',anchor:'95%',disabled : true}
			           ]
			}]
		},{
			layout : 'form',
			columnWidth : .99,
			items:[
			       {name:'REMARK',fieldLabel:'其他',xtype:'textarea',labelStyle:'text-align:right;',anchor:'95%',disabled : true}
			       ]
		}]
	},{
		//ACRM_F_CI_PER_JOBRESUME 工作履历表
		xtype:'fieldset',
		title:'从业经历',
		titleCollapse : true,
		collapsible : true,
		autoHeight : true,
		collapsed :true,
		anchor : '95%',
		items:[{
			layout : 'column',
			items:[{
				layout : 'form',
				columnWidth : .33,
				items:[
				       {name:'UNIT_NAME',fieldLabel:'单位名称',xtype:'textfield',labelStyle:'text-align:right;',anchor:'95%',disabled : true},
				       {name:'UNIT_CHAR',fieldLabel:'单位性质',xtype:'combo',labelStyle:'text-align:right;',anchor:'95%',
				    	   store:unitCharStore,valueField : 'key',displayField : 'value',disabled : true,
				    	   typeAhead : true,forceSelection : true,triggerAction : 'all',emptyText : '请选择',selectOnFocus : true},
				       {name:'WORK_DEPT1',fieldLabel:'所在部门',xtype:'textfield',labelStyle:'text-align:right;',anchor:'95%',disabled : true},
				       {name:'LAST_UPDATE_USER1',fieldLabel:'最后更新人',xtype:'textfield',labelStyle:'text-align:right;',anchor:'95%',disabled : true}
				       ]
			},{
				layout : 'form',
				columnWidth : .33,
				items:[
				       {name:'POSITION',fieldLabel:'担任职务',xtype:'textfield',labelStyle:'text-align:right;',anchor:'95%',disabled : true},
				       {name:'UNIT_TEL',fieldLabel:'单位电话',xtype:'textfield',labelStyle:'text-align:right;',anchor:'95%',disabled : true},
				       {name:'UNIT_ADDRESS',fieldLabel:'单位地址',xtype:'textfield',labelStyle:'text-align:right;',anchor:'95%',disabled : true},
				       {name:'LAST_UPDATE_TM1',fieldLabel:'最后更新时间',xtype:'textfield',labelStyle:'text-align:right;',anchor:'95%',disabled : true}
				       ]
			},{
				layout : 'form',
				columnWidth : .33,
				items:[
				       {name:'UNIT_ZIPCODE',fieldLabel:'单位邮编',xtype:'textfield',labelStyle:'text-align:right;',anchor:'95%',disabled : true},
				       {name:'START_DATE1',fieldLabel:'开始日期',xtype:'datefield',format : 'Y-m-d',labelStyle:'text-align:right;',anchor:'95%',disabled : true},
				       {name:'END_DATE1',fieldLabel:'结束日期',xtype:'datefield',format : 'Y-m-d',labelStyle:'text-align:right;',anchor:'95%',disabled : true}
				       ]
			}]
		}]
	},{
		//ACRM_F_CI_PER_EDURESUME 学业履历表
		xtype:'fieldset',
		title:'个人资历',
		titleCollapse : true,
		collapsible : true,
		autoHeight : true,
		collapsed :true,
		anchor : '95%',
		items:[{
			layout : 'column',
			items:[{
				layout : 'form',
				columnWidth : .33,
				items:[
				       {name:'UNIVERSITY',fieldLabel:'所在学校',xtype:'textfield',labelStyle:'text-align:right;',anchor:'95%',disabled : true},
				       {name:'COLLEGE',fieldLabel:'所在院系',xtype:'textfield',labelStyle:'text-align:right;',anchor:'95%',disabled : true},
				       {name:'MAJOR',fieldLabel:'专业',xtype:'combo',labelStyle:'text-align:right;',anchor:'95%',
				    	   store:majorStore,valueField : 'key',displayField : 'value',disabled : true,
				    	   typeAhead : true,forceSelection : true,triggerAction : 'all',emptyText : '请选择',selectOnFocus : true},
				       {name:'LAST_UPDATE_USER2',fieldLabel:'最后更新人',xtype:'textfield',labelStyle:'text-align:right;',anchor:'95%',disabled : true}
				       ]
			},{
				layout : 'form',
				columnWidth : .33,
				items:[
				       {name:'EDU_SYS',fieldLabel:'学制',xtype:'combo',labelStyle:'text-align:right;',anchor:'95%',
				    	   store:eduSysStore,valueField : 'key',displayField : 'value',disabled : true,
				    	   typeAhead : true,forceSelection : true,triggerAction : 'all',emptyText : '请选择',selectOnFocus : true},
				       {name:'CERTIFICATE_NO',fieldLabel:'学历证书号',xtype:'textfield',labelStyle:'text-align:right;',anchor:'95%',disabled : true},
				       {name:'DIPLOMA_NO',fieldLabel:'学位证书号',xtype:'textfield',labelStyle:'text-align:right;',anchor:'95%',disabled : true},
				       {name:'LAST_UPDATE_TM2',fieldLabel:'最后更新时间',xtype:'textfield',labelStyle:'text-align:right;',anchor:'95%',disabled : true}
				       ]
			},{
				layout : 'form',
				columnWidth : .33,
				items:[
				       {name:'START_DATE2',fieldLabel:'开始日期',xtype:'datefield',format : 'Y-m-d',labelStyle:'text-align:right;',anchor:'95%',disabled : true},
				       {name:'END_DATE2',fieldLabel:'结束日期',xtype:'datefield',format : 'Y-m-d',labelStyle:'text-align:right;',anchor:'95%',disabled : true}
				       ]
			}]
		}]
	},{
		//ACRM_F_CI_PER_PREFERENCE 个人偏好表
		xtype:'fieldset',
		title:'个人偏好',
		titleCollapse : true,
		collapsible : true,
		autoHeight : true,
		collapsed :true,
		anchor : '95%',
		items:[{
			layout : 'column',
			items:[{
				layout : 'form',
				columnWidth : .33,
				items:[
				       {name:'LANG_PREFER',fieldLabel:'语言偏好',xtype:'combo',labelStyle:'text-align:right;',anchor:'95%',
				    	   store:langPreferStore,valueField : 'key',displayField : 'value',disabled : true,
				    	   typeAhead : true,forceSelection : true,triggerAction : 'all',emptyText : '请选择',selectOnFocus : true},
				       {name:'TITLE_PREFER',fieldLabel:'称谓偏好',xtype:'combo',labelStyle:'text-align:right;',anchor:'95%',
				    	   store:titlePreferStore,valueField : 'key',displayField : 'value',disabled : true,
				    	   typeAhead : true,forceSelection : true,triggerAction : 'all',emptyText : '请选择',selectOnFocus : true},
				       {name:'CONTACT_TYPE',fieldLabel:'联络方式偏好',xtype:'combo',labelStyle:'text-align:right;',anchor:'95%',
				    	   store:contactTypeStore,valueField : 'key',displayField : 'value',disabled : true,
				    	   typeAhead : true,forceSelection : true,triggerAction : 'all',emptyText : '请选择',selectOnFocus : true},
				       {name:'CONTACT_FREQ_PREFER',fieldLabel:'联络频率偏好',xtype:'combo',labelStyle:'text-align:right;',anchor:'95%',
				    	   store:contactFreqStore,valueField : 'key',displayField : 'value',disabled : true,
				    	   typeAhead : true,forceSelection : true,triggerAction : 'all',emptyText : '请选择',selectOnFocus : true},
				       {name:'CONTACT_TIME_PREFER',fieldLabel:'联络时间偏好',xtype:'combo',labelStyle:'text-align:right;',anchor:'95%',
				    	   store:contactTimeStore,valueField : 'key',displayField : 'value',disabled : true,
				    	   typeAhead : true,forceSelection : true,triggerAction : 'all',emptyText : '请选择',selectOnFocus : true},
				       {name:'GIFT_PREFER',fieldLabel:'赠品礼物偏好',xtype:'combo',labelStyle:'text-align:right;',anchor:'95%',
				    	   store:giftPreferStore,valueField : 'key',displayField : 'value',disabled : true,
				    	   typeAhead : true,forceSelection : true,triggerAction : 'all',emptyText : '请选择',selectOnFocus : true},
				       {name:'VEHICLE_PREFER',fieldLabel:'出行交通工具偏好',xtype:'combo',labelStyle:'text-align:right;',anchor:'95%',
				    	   store:vehiclePreferStore,valueField : 'key',displayField : 'value',disabled : true,
				    	   typeAhead : true,forceSelection : true,triggerAction : 'all',emptyText : '请选择',selectOnFocus : true},
				       {name:'LAST_UPDATE_USER3',fieldLabel:'最后更新人',xtype:'textfield',labelStyle:'text-align:right;',anchor:'95%',disabled : true}
				       ]
			},{
				layout : 'form',
				columnWidth : .33,
				items:[
				       {name:'CONSUM_HABIT',fieldLabel:'消费习惯',xtype:'combo',labelStyle:'text-align:right;',anchor:'95%',
				    	   store:consumHabitStore,valueField : 'key',displayField : 'value',disabled : true,
				    	   typeAhead : true,forceSelection : true,triggerAction : 'all',emptyText : '请选择',selectOnFocus : true},
				       {name:'INSURANCE_PREFER',fieldLabel:'保险倾向',xtype:'combo',labelStyle:'text-align:right;',anchor:'95%',
				    	   store:insurancePreferStore,valueField : 'key',displayField : 'value',disabled : true,
				    	   typeAhead : true,forceSelection : true,triggerAction : 'all',emptyText : '请选择',selectOnFocus : true},
				       {name:'INVEST_EXPR',fieldLabel:'投资经验',xtype:'combo',labelStyle:'text-align:right;',anchor:'95%',
				    	   store:investExprStore,valueField : 'key',displayField : 'value',disabled : true,
				    	   typeAhead : true,forceSelection : true,triggerAction : 'all',emptyText : '请选择',selectOnFocus : true},
				       {name:'RISK_PREFER',fieldLabel:'投资风险偏好',xtype:'combo',labelStyle:'text-align:right;',anchor:'95%',
				    	   store:riskPreferStore,valueField : 'key',displayField : 'value',disabled : true,
				    	   typeAhead : true,forceSelection : true,triggerAction : 'all',emptyText : '请选择',selectOnFocus : true},
				       {name:'INVEST_POSITION',fieldLabel:'投资方向偏好',xtype:'combo',labelStyle:'text-align:right;',anchor:'95%',
				    	   store:investPositionStore,valueField : 'key',displayField : 'value',disabled : true,
				    	   typeAhead : true,forceSelection : true,triggerAction : 'all',emptyText : '请选择',selectOnFocus : true},
				       {name:'INVEST_CYCLE',fieldLabel:'投资周期偏好',xtype:'combo',labelStyle:'text-align:right;',anchor:'95%',
				    	   store:investCycleStore,valueField : 'key',displayField : 'value',disabled : true,
				    	   typeAhead : true,forceSelection : true,triggerAction : 'all',emptyText : '请选择',selectOnFocus : true},
				       {name:'FINANCE_BUSINESS_PREFER',fieldLabel:'金融业务类型偏好',xtype:'combo',labelStyle:'text-align:right;',anchor:'95%',
				    	   store:financeBusinessStore,valueField : 'key',displayField : 'value',disabled : true,
				    	   typeAhead : true,forceSelection : true,triggerAction : 'all',emptyText : '请选择',selectOnFocus : true},
				       {name:'LAST_UPDATE_TM3',fieldLabel:'最后更新时间',xtype:'textfield',labelStyle:'text-align:right;',anchor:'95%',disabled : true}
				       ]
			},{ 
				layout : 'form',
				columnWidth : .33,
				items:[
				       {name:'INTEREST_INVESTMENT',fieldLabel:'感兴趣的投资信息',xtype:'textfield',labelStyle:'text-align:right;',anchor:'95%',disabled : true},
				       {name:'INVEST_STYLE',fieldLabel:'客户风险承受能力',xtype:'combo',labelStyle:'text-align:right;',anchor:'95%',
				    	   store:investStyleStore,valueField : 'key',displayField : 'value',disabled : true,
				    	   typeAhead : true,forceSelection : true,triggerAction : 'all',emptyText : '请选择',selectOnFocus : true},
				       {name:'INVEST_TARGET',fieldLabel:'主要的投资目标',xtype:'combo',labelStyle:'text-align:right;',anchor:'95%',
				    	   store:investTargetStore,valueField : 'key',displayField : 'value',disabled : true,
				    	   typeAhead : true,forceSelection : true,triggerAction : 'all',emptyText : '请选择',selectOnFocus : true},
				       {name:'INVEST_CHANNEL',fieldLabel:'主要的投资渠道',xtype:'combo',labelStyle:'text-align:right;',anchor:'95%',
				    	   store:investChannelStore,valueField : 'key',displayField : 'value',disabled : true,
				    	   typeAhead : true,forceSelection : true,triggerAction : 'all',emptyText : '请选择',selectOnFocus : true},
				       {name:'POST_DATA_FLAG',fieldLabel:'是否接受我行寄发的资料',xtype:'combo',labelStyle:'text-align:right;',anchor:'95%',disabled : true,
				    	   store:postDataStore,valueField : 'key',displayField : 'value',
				    	   typeAhead : true,forceSelection : true,triggerAction : 'all',emptyText : '请选择',selectOnFocus : true},
				       {name:'JOIN_CAMP_FLAG',fieldLabel:'是否愿意参加联谊活动',xtype:'combo',labelStyle:'text-align:right;',anchor:'95%',
			    		   store:joinCampStore,valueField : 'key',displayField : 'value',
				    	   typeAhead : true,forceSelection : true,triggerAction : 'all',emptyText : '请选择',selectOnFocus : true,disabled : true},
				       {name:'RECEIVE_SMS_FLAG',fieldLabel:'是否愿意接受短信',xtype:'combo',labelStyle:'text-align:right;',anchor:'95%',
			    		   store:recieveSmsStore,valueField : 'key',displayField : 'value',
				    	   typeAhead : true,forceSelection : true,triggerAction : 'all',emptyText : '请选择',selectOnFocus : true,disabled : true}
				       ]
			}]
		},{
			layout : 'form',
			columnWidth : .99,
			items:[
			       {name:'WELCOME_TEXT',fieldLabel:'个人欢迎文字',xtype:'textarea',labelStyle:'text-align:right;',anchor:'95%',disabled : true}
			       ]
		}]
	},{
		//ACRM_F_CI_PER_LIKEINFO 个人客户喜好信息
		xtype:'fieldset',
		title:'个人兴趣爱好',
		titleCollapse : true,
		collapsible : true,
		autoHeight : true,
		collapsed :true,
		anchor : '95%',
		items:[{
			layout : 'column',
			items:[{
				layout : 'form',
				columnWidth : .33,
				items:[
				       {name:'LIKE_ECHANL_TYPE',fieldLabel:'客户喜好电子渠道类型',xtype:'combo',labelStyle:'text-align:right;',anchor:'95%',
				    	   store:likeEchanlStore,valueField : 'key',displayField : 'value',disabled : true,
				    	   typeAhead : true,forceSelection : true,triggerAction : 'all',emptyText : '请选择',selectOnFocus : true},
				       {name:'LIKE_LEISURE_TYPE',fieldLabel:'客户喜好休闲类型',xtype:'combo',labelStyle:'text-align:right;',anchor:'95%',
				    	   store:likeLeisureStore,valueField : 'key',displayField : 'value',disabled : true,
				    	   typeAhead : true,forceSelection : true,triggerAction : 'all',emptyText : '请选择',selectOnFocus : true},
				       {name:'LIKE_MEDIA_TYPE',fieldLabel:'客户喜好媒体类型',xtype:'combo',labelStyle:'text-align:right;',anchor:'95%',
				    	   store:likeMediaStore,valueField : 'key',displayField : 'value',disabled : true,
				    	   typeAhead : true,forceSelection : true,triggerAction : 'all',emptyText : '请选择',selectOnFocus : true},
				       {name:'LIKE_SPORT_TYPE',fieldLabel:'客户喜好运动类型',xtype:'combo',labelStyle:'text-align:right;',anchor:'95%',
				    	   store:likeSportStore,valueField : 'key',displayField : 'value',disabled : true,
				    	   typeAhead : true,forceSelection : true,triggerAction : 'all',emptyText : '请选择',selectOnFocus : true},
				       {name:'LIKE_MAGAZINE_TYPE',fieldLabel:'客户喜好杂志类型',xtype:'combo',labelStyle:'text-align:right;',anchor:'95%',
				    	   store:likeMagazineStore,valueField : 'key',displayField : 'value',disabled : true,
				    	   typeAhead : true,forceSelection : true,triggerAction : 'all',emptyText : '请选择',selectOnFocus : true},
				       {name:'LIKE_FILM_TYPE',fieldLabel:'客户喜好电影节目类型',xtype:'combo',labelStyle:'text-align:right;',anchor:'95%',
				    	   store:likeFilmStore,valueField : 'key',displayField : 'value',disabled : true,
				    	   typeAhead : true,forceSelection : true,triggerAction : 'all',emptyText : '请选择',selectOnFocus : true},
				       {name:'LAST_UPDATE_USER4',fieldLabel:'最后更新人',xtype:'textfield',labelStyle:'text-align:right;',anchor:'95%',disabled : true}
				       ]
			},{
				layout : 'form',
				columnWidth : .33,
				items:[
				       {name:'LIKE_PET_TYPE',fieldLabel:'客户喜好宠物类型',xtype:'combo',labelStyle:'text-align:right;',anchor:'95%',
				    	   store:likePetStore,valueField : 'key',displayField : 'value',disabled : true,
				    	   typeAhead : true,forceSelection : true,triggerAction : 'all',emptyText : '请选择',selectOnFocus : true},
				       {name:'LIKE_COLLECTION_TYPE',fieldLabel:'客户喜好收藏类型',xtype:'combo',labelStyle:'text-align:right;',anchor:'95%',
				    	   store:likeCollectionStore,valueField : 'key',displayField : 'value',disabled : true,
				    	   typeAhead : true,forceSelection : true,triggerAction : 'all',emptyText : '请选择',selectOnFocus : true},
				       {name:'LIKE_INVEST_TYPE',fieldLabel:'客户喜好投资类型',xtype:'combo',labelStyle:'text-align:right;',anchor:'95%',
				    	   store:likeInvestStore,valueField : 'key',displayField : 'value',disabled : true,
				    	   typeAhead : true,forceSelection : true,triggerAction : 'all',emptyText : '请选择',selectOnFocus : true},
				       {name:'LIKE_BRAND_TYPE',fieldLabel:'客户喜好品牌类型',xtype:'combo',labelStyle:'text-align:right;',anchor:'95%',
				    	   store:likeBrandStore,valueField : 'key',displayField : 'value',disabled : true,
				    	   typeAhead : true,forceSelection : true,triggerAction : 'all',emptyText : '请选择',selectOnFocus : true},
				       {name:'LIKE_BRAND_TEXT',fieldLabel:'客户其他喜好品牌',xtype:'textfield',labelStyle:'text-align:right;',anchor:'95%',disabled : true},
				       {name:'FINA_SERV',fieldLabel:'希望得到的理财服务',xtype:'textfield',labelStyle:'text-align:right;',anchor:'95%',disabled : true},
				       {name:'LAST_UPDATE_TM4',fieldLabel:'最后更新时间',xtype:'textfield',labelStyle:'text-align:right;',anchor:'95%',disabled : true}
				       ]
			},{
				layout : 'form',
				columnWidth : .33,
				items:[
				       {name:'CONTACT_TYPE1',fieldLabel:'希望客户经理的联系方式',xtype:'textfield',labelStyle:'text-align:right;',anchor:'95%',disabled : true},
				       {name:'FINA_NEWS',fieldLabel:'希望得到的理财资讯',xtype:'textfield',labelStyle:'text-align:right;',anchor:'95%',disabled : true},
				       {name:'SALON',fieldLabel:'希望参加的沙龙活动',xtype:'textfield',labelStyle:'text-align:right;',anchor:'95%',disabled : true},
				       {name:'INTERESTS',fieldLabel:'个人兴趣爱好',xtype:'textfield',labelStyle:'text-align:right;',anchor:'95%',disabled : true},
				       {name:'AVOID',fieldLabel:'禁忌',xtype:'textfield',labelStyle:'text-align:right;',anchor:'95%',disabled : true}
				       ]
			}]
		},{
			layout : 'form',
			columnWidth : .99,
			items:[
			        {name:'OTHER',fieldLabel:'其他',xtype:'textarea',labelStyle:'text-align:right;',anchor:'95%',disabled : true}
			       ]
		}]
	},{
		//ACRM_F_CI_PER_FAMILIES 家庭成员表
		xtype:'fieldset',
		title:'家庭主要成员',
		titleCollapse : true,
		collapsible : true,
		autoHeight : true,
		collapsed :true,
		anchor : '95%',
		items:[{
			layout : 'column',
			items:[{
				layout : 'form',
				columnWidth : .33,
				items:[
				       {name:'MEMBERNAME',fieldLabel:'成员名称',xtype:'textfield',labelStyle:'text-align:right;',anchor:'95%',disabled : true},
				       {name:'FAMILYRELA',fieldLabel:'家庭成员关系',xtype:'textfield',labelStyle:'text-align:right;',anchor:'95%',disabled : true},
				       {name:'MEMBERCRET_TYP',fieldLabel:'成员证件类型',xtype:'combo',labelStyle:'text-align:right;',anchor:'95%',
			    		   store:certTypeStore,valueField : 'key',displayField : 'value',
				    	   typeAhead : true,forceSelection : true,triggerAction : 'all',emptyText : '请选择',selectOnFocus : true,disabled : true},
				       {name:'MEMBERCRET_NO',fieldLabel:'成员证件号码',xtype:'textfield',labelStyle:'text-align:right;',anchor:'95%',disabled : true}
				       ]
			},{
				layout : 'form',
				columnWidth : .33,
				items:[
				       {name:'TEL',fieldLabel:'电话',xtype:'textfield',labelStyle:'text-align:right;',anchor:'95%',disabled : true},
				       {name:'MOBILE1',fieldLabel:'手机号码',xtype:'textfield',labelStyle:'text-align:right;',anchor:'95%',disabled : true},
				       {name:'EMAIL1',fieldLabel:'邮件地址',xtype:'textfield',labelStyle:'text-align:right;',anchor:'95%',disabled : true},
				       {name:'BIRTHDAY1',fieldLabel:'生日',xtype:'datefield',format : 'Y-m-d',labelStyle:'text-align:right;',anchor:'95%',disabled : true}
				       ]
			},{
				layout : 'form',
				columnWidth : .33,
				items:[
				       {name:'COMPANY',fieldLabel:'家族成员所在企业名称',xtype:'textfield',labelStyle:'text-align:right;',anchor:'95%',disabled : true},
				       {name:'LAST_UPDATE_USER5',fieldLabel:'最后更新人',xtype:'textfield',labelStyle:'text-align:right;',anchor:'95%',disabled : true},
				       {name:'LAST_UPDATE_TM5',fieldLabel:'最后更新时间',xtype:'textfield',labelStyle:'text-align:right;',anchor:'95%',disabled : true}
				       ]
			}]
		},{
			layout : 'form',
			columnWidth : .99,
			items:[
			       {name:'REMARK1',fieldLabel:'备注',xtype:'textarea',labelStyle:'text-align:right;',anchor:'95%',disabled : true}
			       ]
		}]
	}]
});

var customerView = [{
	title:'详情',
	hideTitle:JsContext.checkGrant('executiveCust_detail'),
	xtype : 'form',
	frame : true,
	layout : 'fit',
	recordView : true,
	suspendWidth: 870,
	items:[opForm]
}];

/**修改和详情面板滑入之前判断是否选择了数据**/
var beforeviewshow = function(view){
	if(view == getEditView()||view == getDetailView() || view._defaultTitle=="详情"){
		if(getSelectedData() == false){
			Ext.Msg.alert('提示','请选择一条数据');
			return false;
		}
		if(view._defaultTitle=="详情"){
			var tempForm = opForm.getForm();
			tempForm.reset();
			tempForm.loadRecord(getSelectedData());
		}
	}
};

var viewshow = function(view){
	if(view._defaultTitle=="修改"){
		var offT = getSelectedData().data.OFFICE_TEL;
		if(offT.indexOf('/',0)>0){
			offTArr = offT.split('/');
			getEditView().contentPanel.getForm().findField('OFFICE_TELN').setValue(offTArr[0]);
			getEditView().contentPanel.getForm().findField('OFFICE_TELX').setValue(offTArr[1]);
		}else{
			getEditView().contentPanel.getForm().findField('OFFICE_TELX').setValue(offT);
		}
		var mob = getSelectedData().data.MOBILE;
		if(mob.indexOf('/',0)>0){
			mobArr = mob.split('/');
			getEditView().contentPanel.getForm().findField('MOBILEN').setValue(mobArr[0]);
			getEditView().contentPanel.getForm().findField('MOBILEX').setValue(mobArr[1]);
		}else{
			getEditView().contentPanel.getForm().findField('MOBILEX').setValue(mob);
		}
		var homT = getSelectedData().data.HOME_TEL;
		if(homT.indexOf('/',0)>0){
			homTArr = homT.split('/');
			getEditView().contentPanel.getForm().findField('HOME_TELN').setValue(homTArr[0]);
			getEditView().contentPanel.getForm().findField('HOME_TELX').setValue(homTArr[1]);
		}else{
			getEditView().contentPanel.getForm().findField('HOME_TELX').setValue(homT);
		}
		var fex = getSelectedData().data.FEX;
		if(fex.indexOf('/',0)>0){
			fexArr = fex.split('/');
			getEditView().contentPanel.getForm().findField('FEXN').setValue(fexArr[0]);
			getEditView().contentPanel.getForm().findField('FEXY').setValue(fexArr[1]);
		}else{
			getEditView().contentPanel.getForm().findField('FEXY').setValue(fex);
		}
	}
}

/**
 * 数据提交之后触发
 * params : data:提交的数据对象；
 * 			cUrl：提交地址；
 * 			result：提交成功失败结果，布尔型；
 */
var afertcommit = function(data,cUrl){
	Ext.Ajax.request({
		url : basepath + '/acrmFCiOrgExecutiveinfo!ifExit.json',
		params : {
		'identType':data.IDENT_TYPE,
		'identNo':data.IDENT_NO,
		'custName':data.LINKMAN_NAME,
		'gender':data.GENDER,
		'birthday':data.BIRTHDAY,
		'linkmanTitle':data.LINKMAN_TITLE,
		'workPosition':data.WORK_POSITION,
		'officeTel':data.OFFICE_TEL,
		'mobile':data.MOBILE,
		'homeTel':data.HOME_TEL,
		'fex':data.FEX,
		'email':data.EMAIL,
		'remark':data.REMARK
		},
		success : function(response) {
			var info =  response.responseText;
			if(info == 'no'){
				Ext.Msg.alert('提示', '高管未在个金开户，已为你建立潜在客户!');
			}
		},
	    failure : function(){
            Ext.Msg.alert('提示', '验证是否个金客户失败!');
        }
	});
};

var createPanel = new Ext.Panel({
	buttonAlign:'center',
    buttons:[{
		id : 'xy',
		text :'校验',
		handler:function(){
		var a = getCurrentView().contentPanel.getForm().findField('IDENT_TYPE').getValue();
		var b = getCurrentView().contentPanel.getForm().findField('IDENT_NO').getValue();
		var c = getCurrentView().contentPanel.getForm().findField('LINKMAN_NAME').getValue();
		if(a.trim() == "" || b.trim() == "" || c.trim() == ""){
			Ext.Msg.alert('提示','存在漏输入项,请重新输入！');
			return false;
		}
		Ext.Ajax.request({
			url : basepath+'/acrmFCiOrgExecutiveinfo!check.json',
			method : 'GET',
			params :{
				identType:a,
				identNo:b,
				linkmanName:c
			},
			success : function(response) {
			    var json = Ext.decode(response.responseText).json;
			    var flag = '0';
			    setViewData(json,flag);
			}
		});
		}}]
});
var editPanel = new Ext.Panel({
	buttonAlign:'center',
    buttons:[{
		id : 'xy',
		text :'校验',
		handler:function(){
		var a = getSelectedData().data.IDENT_TYPE;
		var b = getSelectedData().data.IDENT_NO;
		var c = getSelectedData().data.LINKMAN_NAME;
		if(a.trim() == "" || b.trim() == "" || c.trim() == ""){
			Ext.Msg.alert('提示','存在漏输入项,请重新输入！');
			return false;
		}
		Ext.Ajax.request({
			url : basepath+'/acrmFCiOrgExecutiveinfo!check.json',
			method : 'GET',
			params :{
				identType:a,
				identNo:b,
				linkmanName:c
			},
			success : function(response) {
			    var json = Ext.decode(response.responseText).json;
			    var flag = '0';
			    setViewData(json,flag);
			}
		});
		}},{
			id : 'cx',
			text :'撤销',
			handler:function(){
				var flag = '1';
				var json = '';
				setViewData(json,flag);
			}
		}]
});
var setViewData = function(json, flag){
	if(flag == '1'){//撤销
		getCurrentView().contentPanel.getForm().findField('GENDER').setValue(getSelectedData().data.GENDER);
		getCurrentView().contentPanel.getForm().findField('BIRTHDAY').setValue(getSelectedData().data.BIRTHDAY);
		getCurrentView().contentPanel.getForm().findField('LINKMAN_TITLE').setValue(getSelectedData().data.LINKMAN_TITLE);
		getCurrentView().contentPanel.getForm().findField('WORK_POSITION').setValue(getSelectedData().data.WORK_POSITION);
		getCurrentView().contentPanel.getForm().findField('OFFICE_TEL').setValue(getSelectedData().data.OFFICE_TEL);
		getCurrentView().contentPanel.getForm().findField('MOBILE').setValue(getSelectedData().data.MOBILE);
		getCurrentView().contentPanel.getForm().findField('HOME_TEL').setValue(getSelectedData().data.HOME_TEL);
		getCurrentView().contentPanel.getForm().findField('FEX').setValue(getSelectedData().data.FEX);
		getCurrentView().contentPanel.getForm().findField('EMAIL').setValue(getSelectedData().data.EMAIL);
		getCurrentView().contentPanel.getForm().findField('REMARK').setValue(getSelectedData().data.REMARK);
	}else{
//		getCurrentView().contentPanel.getForm().reset();
		getCurrentView().contentPanel.getForm().findField('GENDER').setValue(json.gender);
		getCurrentView().contentPanel.getForm().findField('BIRTHDAY').setValue(json.birthday);
		getCurrentView().contentPanel.getForm().findField('OFFICE_TEL').setValue(json.unit_tel);
		getCurrentView().contentPanel.getForm().findField('MOBILE').setValue(json.mobile_phone);
		getCurrentView().contentPanel.getForm().findField('HOME_TEL').setValue(json.home_tel);
		getCurrentView().contentPanel.getForm().findField('FEX').setValue(json.unit_fex);
		getCurrentView().contentPanel.getForm().findField('EMAIL').setValue(json.email);
		getCurrentView().contentPanel.getForm().findField('REMARK').setValue(json.remark);
	}
};