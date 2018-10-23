/**
*@description 潜在客户查询
*@author:hujun
*@since:2014-06-04
*@checkedby:
*/
		imports([
                 '/contents/pages/common/Com.yucheng.crm.common.ImpExpNew.js',
		         '/contents/pages/com.yucheng.bcrm/com.yucheng.bcrm.js',
		         '/contents/pages/common/Com.yucheng.crm.common.OrgUserManage.js',
		         '/contents/pages/common/Com.yucheng.crm.common.OrgUserManageNew.js',
		         '/contents/pages/common/Com.yucheng.crm.common.OrgUserManageNews.js',
		         '/contents/pages/common/Com.yucheng.bcrm.common.indexfeild.js',
		         '/contents/pages/common/Com.yucheng.bcrm.common.CustomerQueryField.js',
		         '/contents/pages/common/Com.yucheng.bcrm.common.OrgField.js',
		         '/contents/pages/common/Com.yucheng.bcrm.common.OrgFieldForSource.js',
		         '/contents/pages/common/Com.yucheng.bcrm.common.BusiType.js',
		         '/contents/pages/common/Com.yucheng.bcrm.common.ProductManage.js',//产品放大镜
		         '/contents/pages/common/Com.yucheng.bcrm.common.MktActivityCommonQueryNew.js',//新写营销活动放大镜
		         '/contents/pages/common/Com.yucheng.bcrm.common.CustGroup.js',
		         '/contents/pages/common/Com.yucheng.bcrm.common.LatentRefereesQuery.js',
		         '/contents/pages/customer/customerManager/impcreateCallreport.js'
		         ]);
		Ext.QuickTips.init();
		var needCondition = true;
		var needGrid = true;
		var createView=true;
		var editView=true;
		var json = null;// 保存选中的客户
		var formViewers = true;
		var cID ={};
		var callIds ='';
		var lookupTypes=[
		                 'XD000353',  //客户来源渠道
		                 'XD000025',  //国籍
		                 'XD000040',
		                 'XD000005',
		                 'P_CUST_GRADE','GROUP_MEMEBER_TYPE',
		                 'SHARE_FLAG','CUSTOMER_GROUP_TYPE',
		                 'PAR0400044','HYFL','CUSTOMER_SOURCE_TYPE',
		                 'POTENTAIL_STATE','BACK_STATE_V','XD000080','IDD_CODE_NEW','LOOKUP_ID','LATENT_DELETE_REASON','INTERVIEW_RESULTS','XD000007',
		                 
		                 'CALLREPORT_VISIT_TYPE',//callreport拜访方式
		                   'CALLREPORT_CUST_TYPE',//callreport客户类型
		                   'TYPE_RANGE',//客户类型
		                   'IF_READ',
		                   'REMIND_TYPE',
		                   'CALLREPORT_REMIND_REASON',//call Repor汇款原因
		                   'CALLREPORT_CASE_STAGE',//call Repor是否结案
		                   'CALLREPORT_DQ_REASON',//call Repor出账原因
		                   'XD000353',//既有客户渠道
		                   'CALLREPORT_CUST_CHANNEL',//call Report客户渠道
		                   'CALLREPORT_SAVES_STAGE',//call Report销售阶段
		                   'CALLREPORT_FAIL_REASON',//call Report失败原因
		                   'CALLREPORT_SEQUEL_STAGE',//call Report是否续作
		                   'CALLREPORT_PRODUCT_NAME',
		                   {
		           			TYPE : 'MKT_ACTIVITIE_VAL',
		           			url : '/myPotentialCustomer!querymktActivitys.json',
		           			key : 'MKT_ACTI_CODE',
		           			value : 'MKT_ACTI_NAME',
		           			root : 'json.data'}
		                 ];
		var localLookup = {
				'CUST_TYPE_VALUE' : [
				   {key : '2',value : '个人'}
				],
				'CONCLUSION_V' : [ {
					key : '2',
					value : '未提交'
				}, {
					key : '1',
					value : '未处理'
				}, {
					key : '3',
					value : '未通过'
				}, {
					key : '4',
					value : '通过'
				} ]/*,
				'BACK_STATE_V' : [ {
					key : '1',
					value : '未回收'
				}, {
					key : '2',
					value : '已分派'
				}, {
					key : '3',
					value : '新建'
				}, {
					key : '0',
					value : '已回收'
				} ]*/,
				'STATE_VALUE' : [ {
					key : '1',
					value : '分派'
				}, {
					key : '2',
					value : '回收'
				}],
				'FORMAL_CUST_FLAG_V' : [ {
					key : '1',
					value : '非正式客户'
				}, {
					key : '2',
					value : '正式客户'
				}],
				'REFEREES_ID_TYPEV' : [ {
					key : '1',
					value : '员工'
				}, {
					key : '2',
					value : '客户'
				}],
				'IDD_CODE_NEW_V' : [
{value :'中国',key : '86'},
{value :'台湾省',key : '886'},
{value :'香港',key : '852'},
{value :'澳门',key : '853'},
{key : '1',value :'加拿大'},
{key :'961',value :'黎巴嫩'},  
{key :'1',value :'美国/加拿大'},  
{key :'7',value :'俄罗斯'},
{value :'英国',key : '44'},
{value :'日本',key : '81'},
{key :'20',value :'埃及'},
{key :'27',value :'南非'}, 
{key :'30',value :'希腊'},  
{key :'31',value :'荷兰'},  
{key :'32',value :'比利时'},  
{key :'33',value :'法国'},  
{key :'34',value :'西班牙'},  
{key :'36',value :'匈牙利'}, 
{key :'39',value :'意大利'},  
{key :'40',value :'罗马尼亚'},  
{value :'瑞士',key : '41'},
{value :'奥地利',key : '43'},
{value :'丹麦',key : '45'},
{value :'瑞典',key : '46'}, 
{value :'挪威',key : '47'}, 
{value :'波兰',key : '48'}, 
{value :'德国',key : '49'},
{value :'秘鲁',key : '51'},
{value :'墨西哥',key : '52'},
{value :'古巴',key : '53'},
{value :'阿根廷',key : '54'},
{value :'巴西',key : '55'},
{value :'智利',key : '56'},
{value :'哥伦比亚',key : '57'},
{value :'委内瑞拉',key : '58'},
{value :'马来西亚',key : '60'},
{value :'澳大利亚',key : '61'},
{value :'印度尼西亚',key : '62'},
{value :'菲律宾',key : '63'},
{value :'新西兰',key : '64'},
{value :'新加坡',key : '65'},
{value :'泰国',key : '66'},
{value :'韩国',key : '82'},
{value :'越南',key : '84'},
{value :'土耳其',key : '90'},
{value :'印度',key : '91'},
{value :'巴基斯坦',key : '92'},
{value :'阿富汗',key : '93'},
{value :'斯里兰卡',key : '94'},
{value :'缅甸',key : '95'},
{value :'伊朗',key : '98'},
{value :'摩洛哥',key : '212'},
{value :'阿尔及利亚',key : '213'},
{value :'突尼斯',key : '216'},
{value :'利比亚',key : '218'},
{value :'冈比亚',key : '220'},
{value :'塞内加尔',key : '221'},
{value :'马里',key : '223'},
{value :'几内亚',key : '224'},
{value :'科特迪瓦',key : '225'},
{value :'布基纳法索',key : '226'},
{value :'尼日尔',key : '227'},
{value :'多哥',key : '228'},
{value :'贝宁',key : '229'},
{value :'毛里求斯',key : '230'},
{value :'利比里亚',key : '231'},
{value :'塞拉利昂',key : '232'},
{value :'加纳',key : '233'},
{value :'乌兹别克斯坦',key : '233'},
{value :'尼日利亚',key : '234'},
{value :'乍得',key : '235'},
{value :'中非共和国',key : '236'},
{value :'喀麦隆',key : '237'},
{value :'圣多美和普林西比',key : '239'},
{value :'加蓬',key : '241'},
{value :'刚果',key : '242'},
{value :'扎伊尔',key : '243'},
{value :'安哥拉',key : '244'},
{value :'阿森松',key : '247'},
{value :'塞舌尔',key : '248'},
{value :'苏丹',key : '249'},
{value :'埃塞俄比亚',key : '251'},
{value :'索马里',key : '252'},
{value :'吉布提',key : '253'},
{value :'肯尼亚',key : '254'},
{value :'坦桑尼亚',key : '255'},
{value :'乌干达',key : '256'},
{value :'布隆迪',key : '257'},
{value :'莫桑比克',key : '258'},
{value :'赞比亚',key : '260'},
{value :'马达加斯加',key : '261'},
{value :'留尼旺',key : '262'},
{value :'津巴布韦',key : '263'},
{value :'纳米比亚',key : '264'},
{value :'马拉维',key : '265'},
{value :'莱索托',key : '266'},
{value :'博茨瓦纳',key : '267'},
{value :'斯威士兰',key : '268'},
{value :'哈萨克斯坦',key : '327'},
{value :'吉尔吉斯坦',key : '331'},
{value :'直布罗陀',key : '350'},
{value :'葡萄牙',key : '351'},
{value :'卢森堡',key : '352'},
{value :'爱尔兰',key : '353'},
{value :'冰岛',key : '354'},
{value :'阿尔巴尼亚',key : '355'},
{value :'马耳他',key : '356'},
{value :'塞浦路斯',key : '357'},
{value :'芬兰',key : '358'},
{value :'保加利亚',key : '359'},
{value :'立陶宛',key : '370'},
{value :'拉脱维亚',key : '371'},
{value :'爱沙尼亚',key : '372'},
{value :'摩尔多瓦',key : '373'},
{value :'亚美尼亚',key : '374'},
{value :'白俄罗斯',key : '375'},
{value :'安道尔共和国',key : '376'},
{value :'摩纳哥',key : '377'},
{value :'圣马力诺',key : '378'},
{value :'乌克兰',key : '380'},
{value :'南斯拉夫',key : '381'},
{value :'斯洛文尼亚',key : '386'},
{value :'捷克',key : '420'},
{value :'斯洛伐克',key : '421'},
{value :'列支敦士登',key : '423'},
{value :'伯利兹',key : '501'},
{value :'危地马拉',key : '502'},
{value :'萨尔瓦多',key : '503'},
{value :'洪都拉斯',key : '504'},
{value :'尼加拉瓜',key : '505'},
{value :'哥斯达黎加',key : '506'},
{value :'巴拿马',key : '507'},
{value :'海地',key : '509'},
{value :'玻利维亚',key : '591'},
{value :'圭亚那',key : '592'},
{value :'厄瓜多尔',key : '593'},
{value :'法属圭亚那',key : '594'},
{value :'巴拉圭',key : '595'},
{value :'马提尼克',key : '596'},
{value :'苏里南',key : '597'},
{value :'乌拉圭',key : '598'},
{value :'荷属安的列斯',key : '599'},
{value :'文莱',key : '673'},
{value :'瑙鲁',key : '674'},
{value :'巴布亚新几内亚',key : '675'},
{value :'汤加',key : '676'},
{value :'所罗门群岛',key : '677'},
{value :'斐济',key : '679'},
{value :'库克群岛',key : '682'},
{value :'东萨摩亚(美)',key : '684'},
{value :'西萨摩亚',key : '685'},
{value :'法属玻利尼西亚',key : '689'},
{value :'朝鲜',key : '850'},
{value :'柬埔寨',key : '855'},
{value :'老挝',key : '856'},
{value :'孟加拉国',key : '880'},
{value :'马尔代夫',key : '960'},
{value :'约旦',key : '962'},
{value :'叙利亚',key : '963'},
{value :'伊拉克',key : '964'},
{value :'科威特',key : '965'},
{value :'沙特阿拉伯',key : '966'},
{value :'也门',key : '967'},
{value :'阿曼',key : '968'},
{value :'阿拉伯联合酋长国',key : '971'},
{value :'以色列',key : '972'},
{value :'巴林',key : '973'},
{value :'卡塔尔',key : '974'},
{value :'蒙古',key : '976'},
{value :'尼泊尔',key : '977'},
{value :'塔吉克斯坦',key : '992'},
{value :'土库曼斯坦',key : '993'},
{value :'阿塞拜疆',key : '994'},
{value :'格鲁吉亚',key : '995'},
{value :'巴哈马',key : '1242'},
{value :'巴巴多斯',key : '1246'},
{value :'安圭拉岛',key : '1264'},
{value :'安提瓜和巴布达',key : '1268'},
{value :'开曼群岛',key : '1345'},
{value :'百慕大群岛',key : '1441'},
{value :'蒙特塞拉特岛',key : '1664'},
{value :'马里亚那群岛',key : '1670'},
{value :'关岛',key : '1671'},
{value :'圣卢西亚',key : '1758'},
{value :'圣卢西亚',key : '1758'},
{value :'圣文森特岛',key : '1784'},
{value :'圣文森特',key : '1784'},
{value :'波多黎各',key : '1787'},
{value :'格林纳达',key : '1809'},
{value :'特立尼达和多巴哥',key : '1809'},
{value :'牙买加',key : '1876'},
{value :'多米尼加共和国',key : '1890'}
]

			};
		//客户来源渠道
		var custSourceStore = new Ext.data.Store({
			sortInfo: {
		    	field: 'key',
		    	direction: 'ASC' 
			},
			restful:true,   
			autoLoad :true,
			proxy : new Ext.data.HttpProxy({
				url :basepath+'/lookup.json?name=XD000353'
			}),
			reader : new Ext.data.JsonReader({
				root : 'JSON'
			}, [ 'key', 'value' ])
		});
		
		var mktActiStore = new Ext.data.Store({
			restful:true,   
//			autoLoad :true,
			proxy : new Ext.data.HttpProxy({
				url :basepath+'/myPotentialCustomer!querymktActivitys.json'
			}),
			reader : new Ext.data.JsonReader( {
				root : 'json.data'
			}, [ 'MKT_ACTI_CODE', 'MKT_ACTI_NAME'
			])
		});
		mktActiStore.load({
			callback:function(response){
			}
		});
        var deletestart='';
		var roleCondedit = JsContext.checkGrant('edit_phoneno')?'1':'2'; 
		var submit_id='';//查询修改历史分派回收历史客户id
		var url=basepath+'/latentCustInfoQueryAction.json';
		var fields=[
                    {name:'DELETE_CUST_STATE',xtype:'textfield',text:'删除审批状态',hidden:true,viewFn:function(v){
                    	deletestart=v;
                    	return v;}},
				    {name:'CUS_ID',text:'客户号',xtype:'textfield',searchField:true},
				    {name:'CUS_NAME',text:'客户名称',xtype:'textfield',allowBlank:false,searchField:true,maxLength:80,viewFn:function(v){
		            	  //return v;
		            	 if(deletestart==1){
						  return  "<h1 onclick='' style='color:red' >"+v+"</h1>";
		            	  }else{
		            		  return v;
		            	  }
					  }},
			        {name:'CALL_NO',text:'手机号码',invalidText:'请输入数字',maxLength :20,allowBlank:false,
			            	maxLengthText:'最多只能输入20位数字',xtype:'textfield',viewFn:function(v){
			            		return v.replace("#-","")
			            	}
			            },
			        {name : 'MOVER_DATE',text : '分派日期',gridField : true,xtype : 'textarea',maxLength : 20},
			        {name : 'DEFAULT_RECEIVE_DATE',text : '回收截止日期',gridField : true,xtype : 'textarea',maxLength : 20},
			        {name:'SOURCE_CHANNEL',text:'来源渠道',searchField:true,resutlFloat:'right',translateType:'XD000353',gridField:true,multiSelect : true,multiSeparator : ',',allowBlank:false},
			        {name:'CUS_ADDR',text:'通讯地址',xtype:'textarea',gridField:true,maxLength:200},
			        {name:'SUMCOUNTS',text:'已经录入callreport次数',xtype:'textfield',gridField : true},
			        {name:'CUST_MGR_NAME',text:'所属客户经理',gridField:true,searchField:false},
		            {name:'MAIN_BR_NAME',text:'所属机构',gridField:true,searchField:false},
			        {name:'CUST_MGR',text:'所属客户经理',gridField:true,searchField:false,hidden:true},
		            {name:'MAIN_BR_ID',text:'所属机构',gridField:true,searchField:false,hidden:true},
		            {name : 'MOVER_USER',text : '分派/回收人',gridField : true},
		            {name:'BACK_STATE',text:'分派状态',translateType:'BACK_STATE_V',gridField:true,searchField:true},
		            {name:'MKT_ACTIVITIE_V',text:'营销活动',xtype:'textfield',gridField:true,maxLength:370},
			        {name:'MKT_ACTIVITIE',text:'营销活动code',hidden:true,gridField:true,maxLength:80,resutlFloat:'right',translateType:'MKT_ACTIVITIE_VAL',gridField:true
			       },
			       // {name : 'MKT_ACTIVITIE',text : '营销活动',xtype : 'lovcombo',store : mktActiStore,forceSelection : true,
		        	//	editable : false,ComboBox : true,mode : 'local',triggerAction : 'all',displayField : 'MKT_ACTI_NAME',valueField : 'MKT_ACTI_CODE',width : 100,anchor : '95%'},
			        {name:'RECORD_SESSION',text:'沟通话术',xtype:'textarea',gridField:true,maxLength:200},
		            {name:'STATE',text:'潜在客户状态',translateType:'POTENTAIL_STATE',gridField:true,searchField:false},
		            {name:'CERT_TYPE',text:'证件类型',searchField:true,resutlFloat:'right',translateType:'XD000040'},
		            {name:'CERT_CODE',text:'证件号码',xtype:'textfield',resutlFloat:'right',searchField:true,maxLength:40,viewFn:function(v){
		            	if(v!=null&&v!=""&&v!='&nbsp;')
							return "******";
						else 
							return v;
		            }},
		            {name:'CUST_TYPE',text:'客户类型',resutlFloat:'right',translateType:'XD000080'},
		            {name:'CUS_NATIONALITY',text:'国籍',searchField:true,resutlFloat:'right',translateType:'XD000025',searchField:true,gridField:true},
		            {name:'REFEREES_ID',text:'推荐人ID',xtype:'latentRefereesQuery',hiddenName:'CID',singleSelected: true,editable : true,callback : function(a,b) {
						  var REFEREES_ID_P=getCurrentView().contentPanel.form.findField("REFEREES_ID");
						  REFEREES_ID_P.setValue(b[0].data.CID);
					  }},
				    {name:'JOB_TYPE',text:'职业',translateType:'XD000005'},
				    {name:'INDUST_TYPE',text:'职务',searchField:false,resutlFloat:'right',translateType:'XD000007'},
		            {name:'CONTMETH_INFO',text:'座机号码',xtype:'textfield',gridField:false,maxLength:20},
		            {name:'ZIPCODE',text:'邮编',xtype:'numberfield',gridField:false,maxLength:50},
		            {name:'ATTEN_NAME',text:'联系人',xtype:'textfield',gridField:false,maxLength:80},
		            
		            {name:'CUS_EMAIL',text:'E-mail',xtype:'textfield',gridField:true,maxLength:100},
		            {name:'CUS_WECHATID',text:'微信号',xtype:'textfield',gridField:true,maxLength:100},
		            {name : 'ACTUAL_RECEIVE_DATE',text : '实际回收日期',gridField : true,xtype : 'textarea',maxLength : 20},
		            {name:'TREE_STORE',text:'所属机构ID',xtype:'textfield',hidden:true,searchField:true},
		            {name:'MOVER_DATE',text:'分派时间',xtype:'textfield',gridField : true},
		            {name:'INPUT_DATE',text:'创建时间',xtype:'textfield',gridField : true},
		           
		            {name:'LASTVISITDATE',text:'最后一次拜访时间',xtype:'textfield',gridField : true}
		            ];
	

		//树配置查询类型
		var treeCondition = {
			searchType: 'SUBTREE'
		};
		var treeLoaders = [{
			key : 'DATASETMANAGERLOADER',
			url : basepath + '/commsearch.json?condition=' + Ext.encode(treeCondition),
			parentAttr : 'SUPERUNITID',
			locateAttr : 'root',
			jsonRoot:'json.data',
			rootValue : JsContext._orgId,
			textField : 'UNITNAME',
			idProperties : 'ID'
		}];
		var treeCfgs = [{
			key : 'DATASETMANAGERTREE',
			loaderKey : 'DATASETMANAGERLOADER',
			autoScroll:true,
			rootCfg : {
				expanded:true,
				id:JsContext._orgId,
				text:JsContext._unitname,
				autoScroll:true,
				children:[]
			},
			clickFn : function(node){
				setSearchParams({
					TREE_STORE : node.id
				});
				getConditionField('TREE_STORE').setValue(node.id);
			}
		}];
		//边缘面板配置
		var edgeVies = {
			left : {
				width : 200,
				layout : 'form',
				items : [TreeManager.createTree('DATASETMANAGERTREE')]
			}
		};
		
		
		/**
		 * 树形结构的loader对象配置
		 *//*
		var treeLoaders = [{
			key : 'LATENT_ORG_LOADER',
			url : basepath + '/latentRewriteOrgTreeAction.json',
			parentAttr : 'UP_ORG_ID',
			locateAttr : 'ORG_ID',
			jsonRoot:'json.data',
			rootValue :'0000',
			textField : 'ORG_NAME',
			idProperties : 'ORG_ID'
		}];
		*//**
		 * 树形面板对象预配置
		 *//*
		var treeCfgs = [{
			key : 'LATENT_ORG_TREE',
			loaderKey : 'LATENT_ORG_LOADER',
			autoScroll:true,
			rootCfg : {
				expanded:true,
				id:'0000',
				text:'机构树',
				autoScroll:true,
				children:[]
			},
			clickFn : function(node){
				if(node.attributes.ORG_ID == undefined){
					setSearchParams({
						ORG_ID : node.attributes.ORG_ID
					});
				}else{
					//然后重新查询
					setSearchParams({
						TREE_STORE : node.attributes.ORG_ID
					});
				}
				getConditionField('TREE_STORE').setValue(node.attributes.ORG_ID);
			}
		},{
			key : 'LATENT_ORG_LOADER_TREE',
			loaderKey : 'LATENT_ORG_LOADER',
			autoScroll:true,
			rootCfg : {
				expanded:true,
				id:'root',
				text:'机构树',
				autoScroll:true,
				children:[]
			},
			clickFn : function(node){
			}
		}];
		
		//边缘面板配置
		var edgeVies = {
			left : {
				width : 200,
				layout : 'form',
				items : [TreeManager.createTree('LATENT_ORG_TREE')]
			}
		};
		*/
		//提交保存
		function savelaterFn(data){
			var commitData = translateDataKey(data,_app.VIEWCOMMITTRANS);	
			Ext.Ajax.request({
				url : basepath + '/myPotentialCustomer!create.json',
				method : 'POST',
			    params:commitData ,
				waitMsg : '正在保存数据,请等待...',
				success : function(response) {
				    Ext.Msg.alert('提示', '提交成功！');
					reloadCurrentData();
				},
				failure : function(response) {
					Ext.Msg.alert('提示', '提交失败！');
					reloadCurrentData();
				}
			});
		}
		
		//提交保存
		function savelaterarginFn(data){
			var commitData = translateDataKey(data,_app.VIEWCOMMITTRANS);	
			Ext.Ajax.request({
				url : basepath + '/myPotentialCustomer!createargen.json',
				method : 'POST',
			    params:commitData ,
				waitMsg : '正在保存数据,请等待...',
				success : function(response) {
				    Ext.Msg.alert('提示', '提交成功！');
					reloadCurrentData();
					showCustomerViewByTitle("重复客户清单");
				},
				failure : function(response) {
					Ext.Msg.alert('提示', '提交失败！');
					reloadCurrentData();
					showCustomerViewByTitle("重复客户清单");
				}
			});
		}
	//更新营销活动
	function updateSourceChannelFn(cusId,mktActivitie){
			Ext.Msg.wait('正在保存，请稍等...', '提示');
			Ext.Ajax.request({
				url : basepath + '/myPotentialCustomer!editedmktActivitie.json',
				method : 'POST',
				params :{
					cusId:cusId,
					mktActivitie:mktActivitie
				}, 
				waitMsg : '正在保存数据,请等待...', // 显示读盘的动画效果，执行完成后效果消失
				success : function(response) {
					Ext.Msg.alert('提示', '追加营销活动成功！');
					reloadCurrentData();
				},
				failure : function() {
					Ext.Msg.alert('提示', '追加营销活动失败');
					updateChannewindow.hide();
					reloadCurrentData();
				}
			})
	}
	
	//获取来源渠道 按机构  活动编号 最大编号
	/*function getlargestNumberByOrder(orgId,p){
		Ext.Ajax.request({
			url : basepath + '/latentSourceChannelQueryAction!getlargestNumberByorgid.json',
			method : 'GET',
			params :{
				orgId:orgId
			}, 
			waitMsg : '正在获取数据,请等待...', // 显示读盘的动画效果，执行完成后效果消失
			success : function(response) {
				var ret = Ext.decode(response.responseText);
				var larges;
				if(p!='1'){
					larges=parseInt(ret.larges)+1;
					getCurrentView().contentPanel.getForm().findField('LARGEST_NUMBER').setValue(larges);
				}
				var str1=getCurrentView().contentPanel.getForm().findField('CODE').getValue();
					var str2=getCurrentView().contentPanel.getForm().findField('CODE_F').getValue();
					var str3=getCurrentView().contentPanel.getForm().findField('LARGEST_NUMBER').getValue();
					if(str1!=""&&str2!=""&&str3!=""){
						str1=Ext.util.Format.date(getCurrentView().contentPanel.getForm().findField('CODE').getValue(),'Ymd');
						getCurrentView().contentPanel.getForm().findField('LARGEST_NUMBER_V').setValue(str1+str2+str3);
					}
					
			},
			failure : function() {
				Ext.Msg.alert('提示', '获取编号失败');
				updateChannewindow.hide();
				reloadCurrentData();
			}
		})
}*/
	 /**
	  * 过滤重复字符
	  */
	  var removeDuplicate = function(str) {
			var arr = str.split(",");
			for (var i = 0; arr.length - 1 > i; i++) {
				for (var j = i + 1; j < arr.length; j++) {
					if (arr[j] == arr[i]) {
						arr.splice(j, 1);// 删除之后，数组长度随之减少
						j--;
					}
				}
			}
	             return arr.toString();
		}
	  /**
	   * 验证手机号码
	   * @returns {Boolean}
	   */
	  function visibleCallNo(){
		  var reg = /^[0-9]*$/;
			var regCallno=/^[0-9]{11}$/
			var regcallnot=/^[0-9]{10}$/
			var result = getCurrentView().contentPanel.form.findField("CALL_NO").getValue();
			var resultcode = getCurrentView().contentPanel.form.findField("PHONE_AREA_CODE").getValue();
			/*if(resultcode == ""){
				return false;
			}*/
			if(result == ""){
				return false;
			}
			if(!reg.test(result)){
				Ext.Msg.alert('提示','请输入数字！');
				getCurrentView().contentPanel.form.findField('CALL_NO').setValue("")
				return false;
			}
			if(resultcode=="86"&&result.length>11){
				Ext.Msg.alert('提示','请输入正确格式的中国手机号！');
				getCurrentView().contentPanel.form.findField('CALL_NO').setValue("")
				return false;
			}
			if(resultcode=="86"&&!regCallno.test(result)){
				Ext.Msg.alert('提示','请输入正确格式的中国手机号！');
				getCurrentView().contentPanel.form.findField('CALL_NO').setValue("")
				return false;
			}
			if(resultcode=="886"&&!regcallnot.test(result)){
				Ext.Msg.alert('提示','请输入正确格式的台湾手机号！');
				getCurrentView().contentPanel.form.findField('CALL_NO').setValue("")
				return false;
			}
	  }
	  /* var updateChannelForm = new Ext.form.FormPanel({
	    //title:'',
	    autoWidth:true,
		height:180,
		autoScroll: true,
		buttonAlign:'center',
		padding : '0px 0px 0px',
		layout : 'form', // 整个大的表单是form布局,从上往下
		labelAlign : 'center',
		frame : false,
		items : [{
			  layout:'column',
		      items:[{
			  columnWidth:1,  
			  layout:'form',
			  items:[ {xtype:'lovcombo',anchor:'90%',fieldLabel:'客户已有渠道来源',name:'SOURCE_CHANNEL_V',
					store:custSourceStore,valueField : 'key',displayField : 'value',mode : 'local',
		              typeAhead : true,forceSelection : true,triggerAction : 'all',emptyText : '未知',
		              labelStyle:'text-align:right;',selectOnFocus : true,readOnly:true,cls:'x-readOnly'},
			         {xtype:'lovcombo',anchor:'90%',fieldLabel:'追加渠道来源',name:'SOURCE_CHANNEL',
							store:custSourceStore,valueField : 'key',displayField : 'value',mode : 'local',
			              typeAhead : true,forceSelection : true,triggerAction : 'all',emptyText : '未知',
			              labelStyle:'text-align:right;',selectOnFocus : true},
			         {xtype:'textfield',anchor:'95%',fieldLabel:'id',name:'CUS_ID',hidden:true}]
		      }]
		}],
		buttons:[{
          xtype : 'button',
			text : '重置',
			width : 60,
			handler : function() {
				updateChannelForm.getForm().reset();
			}},{
          xtype : 'button',
			text : '提交',
			width : 60,
			handler : function() {
				var data=updateChannelForm.getForm().getFieldValues();
				data.SOURCE_CHANNEL=data.SOURCE_CHANNEL_V+","+data.SOURCE_CHANNEL;
				var commintData =translateDataKey(data,1);
				Ext.Msg.wait('正在保存，请稍等...', '提示');
				Ext.Ajax.request({
					url : basepath + '/myPotentialCustomer!editedResource.json',
					method : 'POST',
					params :commintData, 
					waitMsg : '正在保存数据,请等待...', // 显示读盘的动画效果，执行完成后效果消失
					success : function(response) {
						Ext.Msg.alert('提示', '操作成功！');
						updateChannelForm.getForm().reset();
						updateChannewindow.hide();
						reloadCurrentData();
					},
					failure : function() {
						Ext.Msg.alert('提示', '操作失败');
						updateChannewindow.hide();
						reloadCurrentData();
					}
				})
				
			}}]
		
});	

var updateChannewindow=new Ext.Window({   
      width:460,
	    height:270,
		modal:true,
	    closeAction:'hide',
		autoScroll: true,
	    constrain:true,
		collapsible : true,
      plain:true,   
		buttonAlign:'center',
      items:[updateChannelForm],
		buttons:[{
		 text:'关闭',
		 handler:function(){
			 updateChannewindow.hide();
			 reloadCurrentData();
		 }
		}]
  });*/
	 var handStore = new Ext.data.Store({
			restful : true,
			autoLoad : true,
			sortInfo : {
				field : 'key',
				direction : 'ASC'
			},
			proxy : new Ext.data.HttpProxy({
				url : basepath + '/lookup.json?name=FJMTASK_OPER_TYPE'
			}),
			reader : new Ext.data.JsonReader({
				root : 'JSON'
			}, [ 'key', 'value' ])
		});
	 var transForm1 = new Ext.FormPanel({
			frame : true,
			height : 140,
			region : 'north',
			items : [ {
				layout : 'column',
				items : [
						{
							layout : 'form',
							columnWidth :1,
							labelWidth : 100,
							items : [ new Com.yucheng.crm.common.OrgUserManageNews({
								xtype : 'userchooseNews',
								fieldLabel : '<font color="red">*</font>客户执行对象',
								labelStyle : 'text-align:right;',
								name : 'custMgrName1',
								hiddenName : 'custMgrId',
								singleSelected : true,// 单选复选标志
								searchField : true,
								allowBlank : false,
								anchor : '47.5%',
								callback : function(b) {
									transForm1.getForm().findField("custMgrName1")
											.setValue(b[0].data.userName);
									transForm1.getForm().findField("custMgrId")
											.setValue(b[0].data.userId);
								}
							})
							]
						
						},
						{
							layout : 'form',
							columnWidth : 1,
							labelWidth : 100,
							items : [ 
							    {xtype:'datefield',format:'Y-m-d',anchor:'47.5%',fieldLabel:'指定回收日期',name:'MONTHVALUE',value:new Date().add(Date.MONTH, 3)},
								{xtype:'label',html:"<div style='margin-left:23px;display:Inline;'>默认回收日期为当前日期开始3个月之后，也可重新指定日期</div>"}
							 ]
						} ]
			} ]
		});
		
		
		var hstransForm = new Ext.FormPanel({
			frame : true,
			height : 80,
			region : 'north',
			items : [ {
				layout : 'column',
				items : [	
						{
							layout : 'form',
							columnWidth :1,
							labelWidth : 140,
							items : [ /*new Com.yucheng.crm.common.OrgUserManageNew({
								xtype : 'userchooseNew',
								fieldLabel : '<font color="red">*</font>回收接受对象当前',
								labelStyle : 'text-align:right;',
								name : 'custMgrName1',
								hiddenName : 'custMgrId1',
								singleSelected : true,// 单选复选标志
								searchField : true,
								allowBlank : false,
								anchor : '60%',
								callback : function(b) {
									hstransForm.getForm().findField("custMgrName1")
											.setValue(b[0].data.userName);
									hstransForm.getForm().findField("custMgrId1")
											.setValue(b[0].data.userId);
									hstransForm.getForm().findField("orgId1")
									.setValue(b[0].data.orgId);
								}
							})*/
							  {xtype:'textfield',anchor:'60%',fieldLabel:'回收接受对象当前用户',name:'custMgrName1_value',readOnly:true,cls:'x-readOnly'}
							]
						},{
							layout : 'form',
							columnWidth : 1,
							labelWidth : 100,
							items : [ 
							  {xtype:'textfield',anchor:'45%',fieldLabel:'回收接受对象当前用户id',hidden:true,name:'custMgrId1'},       
							  {xtype:'textfield',anchor:'45%',fieldLabel:'当前 用户机构id',hidden:true,name:'orgId1'} 
							]
						} ]
			} ]
		});
	 var transedRecord = Ext.data.Record.create([ {
			name : 'CUS_ID',
			mapping : 'CUS_ID'
		}, {
			name : 'CUS_NAME',
			mapping : 'CUS_NAME'
		} ]);

		var transedStore = new Ext.data.Store({
			reader : new Ext.data.JsonReader({
				root : 'transedData'
			}, transedRecord)
		});

		var num = new Ext.grid.RowNumberer({
			header : 'No.',
			width : 28
		});

		var cm = new Ext.grid.ColumnModel([ num, // 定义列模型
		{
			header : '客户编号',
			dataIndex : 'CUS_ID',
			sortable : true,
			width : 100
		}, {
			header : '客户名称',
			dataIndex : 'CUS_NAME',
			sortable : true,
			width : 150
		}]);

		var custGrid = new Ext.grid.GridPanel({
			title : '客户列表',
			autoScroll : true,
			height : 130,
			region : 'center',
			store : transedStore,
			stripeRows : true, // 斑马线
			cm : cm,
			viewConfig : {}
		});
		var cTrans = new Ext.Panel({
			autoScroll : true,
			buttonAlign : "center",
			layout : 'border',
			items : [ transForm1, custGrid ],
			buttons : [ {
				text : '提交',
				handler : function() {
					var custMgrId = transForm1.form.findField('custMgrId').getValue();
					var cusId = '';
					for ( var i = 0; i < transedStore.getCount(); i++) {
						var temp = transedStore.getAt(i);
						if (i == 0) {
							cusId = "'" + temp.data.CUS_ID + "'";
						} else {
							cusId += "," + "'" + temp.data.CUS_ID + "'";
						}
					}
					Ext.Msg.wait('正在保存，请稍后......', '系统提示');
					Ext.Ajax.request({
						url : basepath + '/myPotentialCustomer!fpPotCusInfo.json',
						method : 'GET',
						waitMsg : '正在保存数据,请等待...', // 显示读盘的动画效果，执行完成后效果消失
						// form:transForm1.getForm().CUS_ID,
						params : {
							cusId : cusId,
							monthvalue:transForm1.form.findField('MONTHVALUE').getValue(),
							custMgr : transForm1.form.findField('custMgrId').getValue()
						},
						success : function(response) {
							Ext.Msg.alert('提示', '分配成功');
							reloadCurrentData();
						},
						failure : function(response) {
							var resultArray = Ext.util.JSON.decode(response.status);
							if(resultArray == 403) {
						           Ext.Msg.alert('提示', response.responseText);
						     } else {
							Ext.Msg.alert('提示', '分配失败,失败原因:' + response.responseText);
							reloadCurrentData();
						  }
						}
					});	
					
				}
			} ]
		});
	 
		var hscustGrid = new Ext.grid.GridPanel({
			title : '回收客户列表',
			autoScroll : true,
			height : 130,
			region : 'center',
			store : transedStore,
			stripeRows : true, // 斑马线
			cm : cm,
			viewConfig : {}
		});
		
	    var hscpanel = new Ext.Panel({
			autoScroll : true,
			buttonAlign : "center",
			layout : 'border',
			items : [ hstransForm, hscustGrid ],
			buttons : [ {
				text : '提交',
				handler : function() {
					var cusId = '';
					for ( var i = 0; i < transedStore.getCount(); i++) {
						var temp = transedStore.getAt(i);
						if (i == 0) {
							cusId = "'" + temp.data.CUS_ID + "'";
						} else {
							cusId += "," + "'" + temp.data.CUS_ID + "'";
						}
					}
					var custMgr='';
					custMgr=hstransForm.getForm().findField('custMgrId1').getValue();
					var  orgId='';
					
					orgId=hstransForm.getForm().findField('orgId1').getValue();
					Ext.Msg.wait('正在保存，请稍后......', '系统提示');
					Ext.Ajax.request({
						url : basepath + '/myPotentialCustomer!backReceiveLatentCus.json',
						method : 'GET',
						waitMsg : '正在保存数据,请等待...', // 显示读盘的动画效果，执行完成后效果消失
						// form:transForm1.getForm().CUS_ID,
						params : {
							cusId : cusId,
							custMgr :custMgr,
							orgId: orgId
						},
						success : function(response) {
							Ext.Msg.alert('提示', '回收成功');
							reloadCurrentData();
						},
						failure : function(response) {
							var resultArray = Ext.util.JSON.decode(response.status);
							if(resultArray == 403) {
						           Ext.Msg.alert('提示', response.responseText);
						     } else {
							Ext.Msg.alert('提示', '回收失败,失败原因:' + response.responseText);
							reloadCurrentData();
						  }
						}
					});
				}
			} ]
		});
	 
	  //修改历史grid----------------------------------------------
	    var editHisrownum = new Ext.grid.RowNumberer({// 定义自动当前页行号
	    			header : 'No.',
	    			width : 28
	    		});
	    var editHiscolumnmodel = new Ext.grid.ColumnModel([
	        editHisrownum,
	        {header:'客户编号',dataIndex:'CUS_ID',width:100,sortable : true},
	        {header:'客户所属经理',dataIndex:'CUS_FOR_MGR',width:100,sortable : true},
	        
	        {header:'修改人',dataIndex:'EDIT_USER',width:100,sortable : true},
	        {header:'修改时间',dataIndex:'EDIT_DATE',width:100,sortable : true},
	        {header:'修改前栏位',dataIndex:'EDIT_BEFORE_ITEM',width:100,sortable : true},
	        {header:'修改前内容',dataIndex:'EDIT_BEFORE_CONTENT',width:100,sortable : true},
	        {header:'修改后栏位',dataIndex:'EDIT_AFTER_ITEM',width:100,sortable : true},
	        {header:'修改后内容',dataIndex:'EDIT_AFTER_CONTENT',width:100,sortable : true}
	    ]);
	    var editHisRecord = new Ext.data.Record.create([
	    			{name:'CUS_ID'},
	    		    {name:'CUS_FOR_MGR'},
	    		    {name:'EDIT_USER'},
	    		    {name:'EDIT_DATE'},
	    		    {name:'EDIT_BEFORE_ITEM'},
	    		    {name:'EDIT_BEFORE_CONTENT'},
	    		    {name:'EDIT_AFTER_ITEM'},
	    			{name:'EDIT_AFTER_CONTENT'}

	    		]);
	    var editHisInfoReader = new Ext.data.JsonReader({
	    			totalProperty:'json.count',
	    			root:'json.data'
	    		},editHisRecord);
	    var editHisInfoProxy = new Ext.data.HttpProxy({
	    			url:basepath+'/latentEditHisQueryAction.json'
	    		});	
	    var	editHisInfoStore = new Ext.data.Store({
	    			restful : true,
	    			proxy : editHisInfoProxy,
	    			reader :editHisInfoReader,
	    			recordType:editHisRecord
	    		});	
	    editHisInfoStore.on('beforeload', function(editHisInfoStore) { 
	    	editHisInfoStore.baseParams = {'cusId':submit_id}; 
	    });
	    var	editHispagesize_combo = new Ext.form.ComboBox({
	    			name : 'pagesize',
	    			triggerAction : 'all',
	    			mode : 'local',
	    			store : new Ext.data.ArrayStore({
	    				fields : [ 'value', 'text' ],
	    				data : [ [ 10, '10条/页' ], [ 20, '20条/页' ], [ 50, '50条/页' ],
	    				         [ 100, '100条/页' ], [ 250, '250条/页' ],
	    				         [ 500, '500条/页' ] ]
	    			}),
	    			valueField : 'value',
	    			displayField : 'text',
	    			value : '20',
	    			forceSelection : true,
	    			width : 85
	    		});
	    var editHisnumber = parseInt(editHispagesize_combo.getValue());
	    		/**
	    		 * 监听分页下拉框选择事件
	    		 */
	    		editHispagesize_combo.on("select", function(comboBox) {
	    			editHisbbar.pageSize = parseInt(editHispagesize_combo.getValue()),
	    			editHisInfoStore.load({
	    			params:{
	    				'cusId':submit_id,
	    				start:0,
	    				limit: parseInt(editHispagesize_combo.getValue())
	    			}
	    		});
	    		});
	    		//分页工具条定义
	    var	editHisbbar = new Ext.PagingToolbar({
	    			pageSize : editHisnumber,
	    			store : editHisInfoStore,
	    			displayInfo : true,
	    			displayMsg : '显示{0}条到{1}条,共{2}条',
	    			emptyMsg : "没有符合条件的记录",
	    			items : ['-', '&nbsp;&nbsp;', editHispagesize_combo]
	    		});	
	    var editHisTbar=new Ext.Toolbar({
	    		items:[]
	    })		
	    var editHisgrid =new Ext.grid.GridPanel({//
	    			title:'修改历史记录',
	    			frame:true,
	    			height:240,
	    			autoScroll : true,
	    			bbar:editHisbbar,
	    			tbar:editHisTbar,
	    			stripeRows : true, // 斑马线
	    			store:editHisInfoStore,
	    			loadMask:true,
	    			cm :editHiscolumnmodel,
	    			region:'center',
	    			viewConfig:{
	    				forceFit:false,
	    				autoScroll:true
	    			},
	    			loadMask : {
	    				msg : '正在加载表格数据,请稍等...'
	    			}
	    });
	  //分派回收历史grid---------------------------------------------------------------------
	    var disRecyHisrownum = new Ext.grid.RowNumberer({// 定义自动当前页行号
	    			header : 'No.',
	    			width : 28
	    		});
	    var disRecyHisColumnmodel = new Ext.grid.ColumnModel([
	        disRecyHisrownum,
	        {header:'客户编号',dataIndex:'CUS_ID',width:100,sortable : true},
	        {header:'客户名称',dataIndex:'CUS_NAME',width:100,sortable : true},
	        {header:'状态',dataIndex:'STATE',width:100,renderer:function(value){
	    				var val = translateLookupByKey("STATE_VALUE",value);
	    				return val?val:"";
	    				},sortable : true},
	        {header:'分配回收人',dataIndex:'MOVER_USER',width:100,sortable : true},
	        {header:'分配回收日期',dataIndex:'MOVER_DATE',width:100,sortable : true},
	        {header:'变更前所属主管经理',dataIndex:'CUST_MGR_BEFORE',width:100,sortable : true},
	        {header:'变更后所属主管经理',dataIndex:'CUST_MGR_AFTER',width:100,sortable : true}   
	    ]);
	    var disRecyHisRecord = new Ext.data.Record.create([
	    			{name:'CUS_ID'},
	    		    {name:'CUS_NAME'},
	    		    {name:'STATE'},
	    		    {name:'MOVER_USER'},
	    		    {name:'MOVER_DATE'},
	    		    {name:'CUST_MGR_BEFORE'},
	    		    {name:'CUST_MGR_AFTER'}

	    		]);
	    var disRecyHisInfoReader = new Ext.data.JsonReader({
	    			totalProperty:'json.count',
	    			root:'json.data'
	    		},disRecyHisRecord);
	   
	    var disRecyHisInfoProxy = new Ext.data.HttpProxy({
	    			url:basepath+'/latentDispatchHisQueryAction.json'
	    		});	
	    var	disRecyHisInfoStore = new Ext.data.Store({
	    			restful : true,
	    			proxy : disRecyHisInfoProxy,
	    			reader :disRecyHisInfoReader,
	    			recordType:disRecyHisRecord
	    		});	
	    disRecyHisInfoStore.on('beforeload', function(disRecyHisInfoStore) { 
	    	disRecyHisInfoStore.baseParams = {'cusId':submit_id}; 
	    });
	    var	disRecyHispagesize_combo = new Ext.form.ComboBox({
	    			name : 'pagesize',
	    			triggerAction : 'all',
	    			mode : 'local',
	    			store : new Ext.data.ArrayStore({
	    				fields : [ 'value', 'text' ],
	    				data : [ [ 10, '10条/页' ], [ 20, '20条/页' ], [ 50, '50条/页' ],
	    				         [ 100, '100条/页' ], [ 250, '250条/页' ],
	    				         [ 500, '500条/页' ] ]
	    			}),
	    			valueField : 'value',
	    			displayField : 'text',
	    			value : '20',
	    			forceSelection : true,
	    			width : 85
	    		});
	    var disRecyHisnumber = parseInt(disRecyHispagesize_combo.getValue());
	    		/**
	    		 * 监听分页下拉框选择事件
	    		 */
	    		disRecyHispagesize_combo.on("select", function(comboBox) {
	    			disRecyHisbbar.pageSize = parseInt(disRecyHispagesize_combo.getValue()),
	    			disRecyHisInfoStore.load({
	    			params:{
	    				'cusId':submit_id,
	    				start:0,
	    				limit: parseInt(disRecyHispagesize_combo.getValue())
	    			}
	    		});
	    		});
	    		//分页工具条定义
	    var	disRecyHisbbar = new Ext.PagingToolbar({
	    			pageSize : disRecyHisnumber,
	    			store : disRecyHisInfoStore,
	    			displayInfo : true,
	    			displayMsg : '显示{0}条到{1}条,共{2}条',
	    			emptyMsg : "没有符合条件的记录",
	    			items : ['-', '&nbsp;&nbsp;', disRecyHispagesize_combo]
	    		});	
	    var disRecyHisTbar=new Ext.Toolbar({
	    		items:[]
	    })		
	    var disRecyHisgrid =new Ext.grid.GridPanel({//
	    			title:'分派历史信息',
	    			frame:true,
	    			height:240,
	    			autoScroll : true,
	    			bbar:disRecyHisbbar,
	    			tbar:disRecyHisTbar,
	    			stripeRows : true, // 斑马线
	    			store:disRecyHisInfoStore,
	    			loadMask:true,
	    			cm :disRecyHisColumnmodel,
	    			region:'center',
	    			viewConfig:{
	    				forceFit:false,
	    				autoScroll:true
	    			},
	    			loadMask : {
	    				msg : '正在加载表格数据,请稍等...'
	    			}
	    });  
	    //---------------------------------------
	    var tabs_panel = new Ext.TabPanel({
	        activeTab : 0,//默认激活第一个tab页  
	        animScroll : true,//使用动画滚动效果  
	        enableTabScroll : true,//tab标签超宽时自动出现滚动按钮 
	        buttonAlign:'center',
	        items: [editHisgrid,disRecyHisgrid],
		    buttons : [ {
			text : '返回',
			xtype : 'button',
			hidden : JsContext.checkGrant('latent_edit_his'),
			handler : function() {
				showCustomerViewByIndex(2);
			}
		}]
	   });
	    //删除复核流程提交
	    var delbyidFn=function(commitData){
	    	Ext.MessageBox.confirm('提示','确定提交审批吗?',function(buttonId){
				if(buttonId.toLowerCase() == "no"){
					return;
				} 
				Ext.Ajax.request({
					url : basepath+ '/myPotentialCustomer!commitApproval.json',
					params:commitData,
					waitMsg : '正在提交数据,请等待...', // 显示读盘的动画效果，执行完成后效果消失
					success : function(response) {
						var ret = Ext.decode(response.responseText);
						var instanceid = ret.instanceid;//流程实例ID
						var currNode = ret.currNode;//当前节点
						var nextNode = ret.nextNode;//下一步节点
						selectUserList(instanceid,currNode,nextNode);//选择下一步办理人
					},
					failure : function(response) {
						var resultArray = Ext.util.JSON.decode(response.status);
						if(resultArray == 403) {
					           Ext.Msg.alert('提示', response.responseText);
					  } else {
						Ext.Msg.alert('提示', '操作失败,失败原因:' + response.responseText);
						reloadCurrentData();
					  }
					}
				});
			});
		
	    }
	    //来源渠道复核流程提交
	    var sourcesSubmitbyidFn=function(commitData){
	    	Ext.MessageBox.confirm('提示','确定提交保存审批吗?',function(buttonId){
				if(buttonId.toLowerCase() == "no"){
					return;
				} 
				Ext.Ajax.request({
					url : basepath+ '/latentSourceChannelTempAction!saveDateCommit.json',
					method : 'POST',
					params:commitData,
					waitMsg : '正在提交数据,请等待...', // 显示读盘的动画效果，执行完成后效果消失
					success : function(response) {
						var ret = Ext.decode(response.responseText);
						var instanceid = ret.instanceid;//流程实例ID
						var currNode = ret.currNode;//当前节点
						var nextNode = ret.nextNode;//下一步节点
						selectUserList(instanceid,currNode,nextNode);//选择下一步办理人
					},
					failure : function(response) {
						var resultArray = Ext.util.JSON.decode(response.status);
						if(resultArray == 403) {
					           Ext.Msg.alert('提示', response.responseText);
					  } else {
						Ext.Msg.alert('提示', '操作失败,失败原因:' + response.responseText);
						reloadCurrentData();
					  }
					}
				});
			});
		
	    }
	  //重复客户清单列表grid--------------------------------------------------------------------------------
	    var againCreateCheck = new Ext.grid.CheckboxSelectionModel(); 
	    var againCreaterownum = new Ext.grid.RowNumberer({// 定义自动当前页行号
	    			header : 'No.',
	    			width : 30
	    		});
	    var againCreateColumnmodel = new Ext.grid.ColumnModel([
	        againCreaterownum,againCreateCheck,
	        {header:'客户编号',dataIndex:'CUS_ID',width:100,sortable : true,hidden:true},
	        {header:'客户名称',dataIndex:'CUS_NAME',width:100,sortable : true},
	        {header:'手机号码',dataIndex:'CALL_NO',width:100,sortable : true,renderer:function(value){
	        	return value.replace("#-","")
    			}},

	    	{header:'营销活动',dataIndex:'MKT_ACTIVITIE',width:100,sortable : true,hidden:true}, 
	    	{header:'营销活动',dataIndex:'MKT_ACTIVITIE_V',width:100,sortable : true}, 
	        {header:'来源渠道',dataIndex:'SOURCE_CHANNEL',width:100,renderer:function(value){
	    				var val = translateLookupByKey("XD000353",value);
	    				return val?val:"";
	    				},sortable : true},
	    	{header:'沟通话术',dataIndex:'RECORD_SESSION',width:100,sortable : true},
	    	{header:'推荐人ID',dataIndex:'REFEREES_ID',width:100,sortable : true},
	        {header:'证件类型',dataIndex:'CUST_TYPE',width:100,renderer:function(value){
	    				var val = translateLookupByKey("XD000040",value);
	    				return val?val:"";
	    				},sortable : true},
	        {header:'证件号码',dataIndex:'CERT_CODE',width:100,sortable : true},
	        
	        {header:'国籍',dataIndex:'CUS_NATIONALITY',width:100,renderer:function(value){
	    				var val = translateLookupByKey("XD000025",value);
	    				return val?val:"";
	    				},sortable : true},
			{header:'Email',dataIndex:'CUS_EMAIL',width:100,sortable : true},
	        {header:'微信号',dataIndex:'CUS_WECHATID',width:100,sortable : true},
	        {header:'联系人姓名',dataIndex:'ATTEN_NAME',width:100,sortable : true},
	        {header:'座机',dataIndex:'CONTMETH_INFO',width:100,sortable : true},
	        {header:'职业',dataIndex:'JOB_TYPE',width:100,renderer:function(value){
	    				var val = translateLookupByKey("XD000005",value);
	    				return val?val:"";
	    				},sortable : true},	
	    	{header:'客户类型',dataIndex:'CUST_TYPE',width:100,renderer:function(value){
	    				var val = translateLookupByKey("XD000080",value);
	    				return val?val:"";
	    				},sortable : true},	
	    	{header:'邮编',dataIndex:'ZIPCODE',width:100,sortable : true},	
	    	{header:'职务',dataIndex:'INDUST_TYPE',width:100,renderer:function(value){
				var val = translateLookupByKey("XD000007",value);
				return val?val:"";
				},sortable : true},	
	    	{header:'通讯地址',dataIndex:'CUS_ADDR',width:100,sortable : true}	
	    ]);
	    var againCreateRecord = new Ext.data.Record.create([
	    			{name:'CUS_NAME'},
	    		    {name:'CALL_NO'},
	    		    {name:'CERT_TYPE'},
	    		    {name:'CERT_CODE'},
	    		    {name:'CUST_TYPE'},
	    			{name:'MKT_ACTIVITIE'},
	    			{name:'MKT_ACTIVITIE_V'},
	    		    {name:'SOURCE_CHANNEL'},
	    		    {name:'CUS_NATIONALITY'},
	    			{name:'REFEREES_ID'},
	    			{name:'ATTEN_NAME'},
	    			{name:'CONTMETH_INFO'},
	    			{name:'ZIPCODE'},
	    			{name:'JOB_TYPE'},
	    			{name:'INDUST_TYPE'},
	    			{name:'CUS_ID'},
	    			{name:'RECORD_SESSION'},
	    			{name:'CUS_EMAIL'},
	    			{name:'CUS_WECHATID'},
	    			{name:'CUS_ADDR'}

	    		]);
	    var againCreateReader = new Ext.data.JsonReader({
	    			totalProperty:'json.count',
	    			root:'json.data'
	    		},againCreateRecord);
	    var againCreateProxy = new Ext.data.HttpProxy({
	    			url:basepath+'/latentPotentialCusTwoAction.json'
	    		});	
	    var	againCreateStore = new Ext.data.Store({
	    			restful : true,
	    			proxy : againCreateProxy,
	    			reader :againCreateReader,
	    			recordType:againCreateRecord
	    		});	
	    var	againCreatepagesize_combo = new Ext.form.ComboBox({
	    			name : 'pagesize',
	    			triggerAction : 'all',
	    			mode : 'local',
	    			store : new Ext.data.ArrayStore({
	    				fields : [ 'value', 'text' ],
	    				data : [ [ 10, '10条/页' ], [ 20, '20条/页' ], [ 50, '50条/页' ],
	    				         [ 100, '100条/页' ], [ 250, '250条/页' ],
	    				         [ 500, '500条/页' ] ]
	    			}),
	    			valueField : 'value',
	    			displayField : 'text',
	    			value : '20',
	    			forceSelection : true,
	    			width : 85
	    		});
	    var againCreatenumber = parseInt(againCreatepagesize_combo.getValue());
	    		/**
	    		 * 监听分页下拉框选择事件
	    		 */
	    		againCreatepagesize_combo.on("select", function(comboBox) {
	    			againCreatebbar.pageSize = parseInt(againCreatepagesize_combo.getValue()),
	    			againCreateStore.load({
	    			params:{
	    				start:0,
	    				limit: parseInt(againCreatepagesize_combo.getValue())
	    			}
	    		});
	    		});
	    		//分页工具条定义
	    var	againCreatebbar = new Ext.PagingToolbar({
	    			pageSize : againCreatenumber,
	    			store : againCreateStore,
	    			displayInfo : true,
	    			displayMsg : '显示{0}条到{1}条,共{2}条',
	    			emptyMsg : "没有符合条件的记录",
	    			items : ['-', '&nbsp;&nbsp;', againCreatepagesize_combo]
	    		});	
	    var againCreateTbar=new Ext.Toolbar({
	    		items:[{
	    	    	text : '处理',
	    			id:'modifyLate',
	    			handler:function(){
	    				var selectLength = againCreateGrid.getSelectionModel().getSelections().length;
	    				
	    				if (selectLength != 1) {
	    					Ext.Msg.alert('提示', '请选择一条记录！');
	    					return false;
	    				} 
	    				
	    			    record = againCreateGrid.getSelectionModel().getSelections()[0];
	    			    showCustomerViewByTitle("重复客户清单编辑");
	    			    var subcallno=record.data.CALL_NO;
	    	    		var newstrcallno=subcallno.split('-');
	    	    		getCurrentView().contentPanel.getForm().findField("CALL_NO").setValue(newstrcallno[2]);
	    	    		getCurrentView().contentPanel.getForm().findField("PHONE_AREA_CODE").setValue(newstrcallno[0]);
	    			    getCurrentView().contentPanel.getForm().findField('CUS_ID').setValue(record.data.CUS_ID);
	    				getCurrentView().contentPanel.getForm().findField('CUS_NAME').setValue(record.data.CUS_NAME);
	    				getCurrentView().contentPanel.getForm().findField('CERT_TYPE').setValue(record.data.CERT_TYPE);
	    				getCurrentView().contentPanel.getForm().findField('CERT_CODE').setValue(record.data.CERT_CODE);
	    				getCurrentView().contentPanel.getForm().findField('CUST_TYPE').setValue(record.data.CUST_TYPE);
	    				getCurrentView().contentPanel.getForm().findField('MKT_ACTIVITIE').setValue(record.data.MKT_ACTIVITIE);
	    				getCurrentView().contentPanel.getForm().findField('SOURCE_CHANNEL').setValue(record.data.SOURCE_CHANNEL);
	    				getCurrentView().contentPanel.getForm().findField('CUS_NATIONALITY').setValue(record.data.CUS_NATIONALITY);
	    				getCurrentView().contentPanel.getForm().findField('REFEREES_ID').setValue(record.data.REFEREES_ID);
	    				getCurrentView().contentPanel.getForm().findField('ATTEN_NAME').setValue(record.data.ATTEN_NAME);
	    				getCurrentView().contentPanel.getForm().findField('CONTMETH_INFO').setValue(record.data.CONTMETH_INFO);
	    				getCurrentView().contentPanel.getForm().findField('ZIPCODE').setValue(record.data.ZIPCODE);
	    				getCurrentView().contentPanel.getForm().findField('JOB_TYPE').setValue(record.data.JOB_TYPE);
	    				getCurrentView().contentPanel.getForm().findField('INDUST_TYPE').setValue(record.data.INDUST_TYPE);
	    				getCurrentView().contentPanel.getForm().findField('CUS_ADDR').setValue(record.data.CUS_ADDR);
	    				getCurrentView().contentPanel.getForm().findField('RECORD_SESSION').setValue(record.data.RECORD_SESSION);
	    				getCurrentView().contentPanel.getForm().findField('CUS_EMAIL').setValue(record.data.CUS_EMAIL);
	    				getCurrentView().contentPanel.getForm().findField('CUS_WECHATID').setValue(record.data.CUS_WECHATID);


	    			}
	    	    },{
	    	    	text:'删除',
	    	    	id:'deleteLate',
	    	    	handler :function(){
	    	    		//删除
	    	    		var selectLength = againCreateGrid.getSelectionModel().getSelections().length;
	    	    	 	var selectRecords = againCreateGrid.getSelectionModel().getSelections();
	    	    	 	
	    	    		if(selectLength < 1){
	    	     			Ext.Msg.alert('提示','请选择一条数据进行操作!');
	    	    			return false;
	    	    		}
	    	    		var tempIdStr = '';
	    	    		var tempStatus = '';
	    	    		for(var i=0; i < selectLength; i++){
	    	    			var selectRecord = selectRecords[i];
	    	    			//临时变量，保存要删除的ID
	    	    			tempIdStr +=  selectRecord.data.CUS_ID;
	    	    		 		if( i != selectLength - 1){
	    	    		   		 	tempIdStr += ',';
	    	    					}
	    	    		 }
	    	    		Ext.MessageBox.confirm('提示','你确定删除吗!',function(buttonId){
	    	    		if(buttonId.toLowerCase() == 'no'){
	    	        		return false;
	    	    		}
	    	    		Ext.Msg.wait('正在提交数据，请稍等...','提示');
	    	    			Ext.Ajax.request({
	    	    				url : basepath + '/latentPotentialCusTwoAction!batchDel.json?idStr='+ tempIdStr,
	    	    				method : 'GET',
	    	    				success : function() {
	    	    					Ext.Msg.alert('提示',"操作成功!");
	    	    					againCreateStore.load();
	    	    				},
	    	    				failure : function(response) {
	    	    					var resultArray = Ext.util.JSON.decode(response.status);
	    	    			 		if(resultArray == 403) {
	    	    		           		Ext.Msg.alert('提示', response.responseText);
	    	    			 		}else{
	    	    						Ext.Msg.alert('提示', '操作失败,失败原因:' + response.responseText);
	    	    	 				}
	    	    				}
	    	    			});
	    	    	   });
	    	    	}
	    	      }]
	    })	
	    var againCreateGrid =new Ext.grid.GridPanel({//
	    			//title:'重复客户清单信息',
	    			frame:true,
	    			height:240,
	    			autoScroll : true,
	    			bbar:againCreatebbar,
	    			tbar:againCreateTbar,
	    			stripeRows : true, // 斑马线
	    			store:againCreateStore,
	    			loadMask:true,
	    			cm :againCreateColumnmodel,
	    			sm:againCreateCheck,
	    			region:'center',
	    			viewConfig:{
	    				forceFit:false,
	    				autoScroll:true
	    			},
	    			loadMask : {
	    				msg : '正在加载表格数据,请稍等...'
	    			}
	    });
	    
	  //来源渠道维护grid-----------------------------------------------------------
/*	    var sourceChannelCheck = new Ext.grid.CheckboxSelectionModel(); 
	    var sourceChannelrownum = new Ext.grid.RowNumberer({// 定义自动当前页行号
	    			header : 'No.',
	    			width : 30
	    		});
	    var sourceChannelColumnmodel = new Ext.grid.ColumnModel([
	        sourceChannelrownum,sourceChannelCheck,
	        {header:'属性ID',dataIndex:'ID',width:100,sortable : true,hidden:true},
	        {header:'字典类别',dataIndex:'LOOKUP',width:100,sortable : true,hidden:true},	
	        {header:'活动编码',dataIndex:'CODE',width:150,sortable : true},
	        {header:'活动名称',dataIndex:'VALUE',width:300,sortable : true},
	        {header:'活动说明',dataIndex:'COMMENT',width:200,sortable : true}
	         
	    ]);
	    var sourceChannelRecord = new Ext.data.Record.create([
	    			{name:'ID',mapping:'F_ID'},
	    		    {name:'LOOKUP',mapping:'F_LOOKUP_ID'},
	    		    {name:'CODE',mapping:'F_CODE'},
	    		    {name:'VALUE',mapping:'F_VALUE'},
	    		    {name:'COMMENT',mapping:'F_COMMENT'}
	    		]);
	    var sourceChannelReader = new Ext.data.JsonReader({
	    			totalProperty:'json.count',
	    			root:'json.data'
	    		},sourceChannelRecord);
	    var sourceChannelProxy = new Ext.data.HttpProxy({
	    			url:basepath+'/latentSourceChannelQueryAction.json'
	    		});	
	    var	sourceChannelStore = new Ext.data.Store({
	    			restful : true,
	    			proxy : sourceChannelProxy,
	    			reader :sourceChannelReader,
	    			recordType:sourceChannelRecord
	    		});	
	    var	sourceChannelsize_combo = new Ext.form.ComboBox({
	    			name : 'pagesize',
	    			triggerAction : 'all',
	    			mode : 'local',
	    			store : new Ext.data.ArrayStore({
	    				fields : [ 'value', 'text' ],
	    				data : [ [ 10, '10条/页' ], [ 20, '20条/页' ], [ 50, '50条/页' ],
	    				         [ 100, '100条/页' ], [ 250, '250条/页' ],
	    				         [ 500, '500条/页' ] ]
	    			}),
	    			valueField : 'value',
	    			displayField : 'text',
	    			value : '20',
	    			forceSelection : true,
	    			width : 85
	    		});
	    var sourceChannelnumber = parseInt(sourceChannelsize_combo.getValue());
	    		*//**
	    		 * 监听分页下拉框选择事件
	    		 *//*
	    		sourceChannelsize_combo.on("select", function(comboBox) {
	    			sourceChannelbbar.pageSize = parseInt(sourceChannelsize_combo.getValue()),
	    			sourceChannelStore.load({
	    			params:{
	    				start:0,
	    				limit: parseInt(sourceChannelsize_combo.getValue())
	    			}
	    		});
	    		});
	    		//分页工具条定义
	    var	sourceChannelbbar = new Ext.PagingToolbar({
	    			pageSize : sourceChannelnumber,
	    			store : sourceChannelStore,
	    			displayInfo : true,
	    			displayMsg : '显示{0}条到{1}条,共{2}条',
	    			emptyMsg : "没有符合条件的记录",
	    			items : ['-', '&nbsp;&nbsp;', sourceChannelsize_combo]
	    		});	
	    var sourceChannelTbar=new Ext.Toolbar({
	    		items:[{
				    	text:'新增',
				    	id:'addsource',
				    	handler :function(){
				    		 showCustomerViewByTitle("来源渠道编辑");
				    		 getCurrentView().contentPanel.getForm().findField('CODE').removeClass('x-readOnly');
				    		 getCurrentView().contentPanel.getForm().findField('CODE_F_NAME').removeClass('x-readOnly');
				    	}
	    		       },{
    	    	    	text:'修改',
    	    	    	id:'editsource',
    	    	    	handler :function(){
    	    	    		var checkedNodes=sourceChannelgrid.getSelectionModel().getSelections();
    		    			var selectLength=0;
    		    			selectLength = checkedNodes.length;// 得到行数组的长度
    		    			if (selectLength<=0) {
    		    				Ext.Msg.alert('提示', '请选择一条数据！');
    		    				return false;
    		    			} 
    	    	    		record = sourceChannelgrid.getSelectionModel().getSelections()[0];
			    			showCustomerViewByTitle("来源渠道编辑");
			    			getCurrentView().contentPanel.getForm().findField('ID').setValue(record.data.ID);
			    			getCurrentView().contentPanel.getForm().findField('LOOKUP').setValue(record.data.LOOKUP);
			    			getCurrentView().contentPanel.getForm().findField('LARGEST_NUMBER_V').setValue(record.data.CODE);
			    			var strtmp=record.data.CODE;
			    			var code=strtmp.substr(0, 8);
			    			var code_f=strtmp.substr(8,3);
			    			getlargestNumberByOrder(code_f,1);
			    			var largest_number=strtmp.substr(11);
			    			getCurrentView().contentPanel.getForm().findField('CODE').setValue(code);
			    			getCurrentView().contentPanel.getForm().findField('CODE_F_NAME').setValue(code_f);
			    			getCurrentView().contentPanel.getForm().findField('CODE_F').setValue(code_f);
			    			getCurrentView().contentPanel.getForm().findField('LARGEST_NUMBER').setValue(largest_number);
			    			var value=record.data.VALUE;
				    		var valuev=value.split('-');
				    		getCurrentView().contentPanel.getForm().findField('VALUE').setValue(valuev[1]);
			    			getCurrentView().contentPanel.getForm().findField('COMMENT').setValue(record.data.COMMENT);
			    			
			    			getCurrentView().contentPanel.getForm().findField('CODE').addClass('x-readOnly');
			    			getCurrentView().contentPanel.getForm().findField('CODE_F_NAME').addClass('x-readOnly');
			    			
			    			
	    	    	    	}
	    		      },{
	    	    	    	text:'详情',
	    	    	    	id:'viewsource',
	    	    	    	handler :function(){
	    	    	    		var checkedNodes=sourceChannelgrid.getSelectionModel().getSelections();
	    		    			var selectLength=0;
	    		    			selectLength = checkedNodes.length;// 得到行数组的长度
	    		    			if (selectLength<=0) {
	    		    				Ext.Msg.alert('提示', '请选择一条数据！');
	    		    				return false;
	    		    			} 
	    	    	    		record = sourceChannelgrid.getSelectionModel().getSelections()[0];
				    			showCustomerViewByTitle("来源渠道详情");
				    			getCurrentView().contentPanel.getForm().findField('ID').setValue(record.data.ID);
				    			getCurrentView().contentPanel.getForm().findField('LOOKUP').setValue(record.data.LOOKUP);
				    			var strtmp=record.data.CODE;
				    			var code=strtmp.substr(0, 8);
				    			var code_f=strtmp.substr(8,3);
				    			getlargestNumberByOrder(code_f,1);
				    			var largest_number=strtmp.substr(11);
				    			getCurrentView().contentPanel.getForm().findField('CODE').setValue(code);
				    			getCurrentView().contentPanel.getForm().findField('CODE_F_NAME').setValue(code_f);
				    			getCurrentView().contentPanel.getForm().findField('CODE_F').setValue(code_f);
				    			getCurrentView().contentPanel.getForm().findField('LARGEST_NUMBER').setValue(largest_number);
					    		getCurrentView().contentPanel.getForm().findField('VALUE').setValue(record.data.VALUE);
				    			getCurrentView().contentPanel.getForm().findField('COMMENT').setValue(record.data.COMMENT);
		    	    	    	}
		    		      },{
    	    	    	text:'删除',
    	    	    	id:'deletesource',
    	    	    	handler :function(){
		    			var checkedNodes=sourceChannelgrid.getSelectionModel().getSelections()[0];
		    			var selectLength=0;
		    			selectLength = checkedNodes.length;// 得到行数组的长度
		    			if (selectLength<=0||selectLength>1) {
		    				Ext.Msg.alert('提示', '请选择一条数据！');
		    				return false;
		    			} 		
		    			var id=checkedNodes.data.ID;
		    			Ext.MessageBox.confirm('提示','确定删除吗?',function(buttonId){
		    				if(buttonId.toLowerCase() == "no"){
		    					return;
		    				}  
		    				var selectRe;
		    				var tempId;
		    				var tempCount;
		    				var idStr = '';
		    				Ext.Ajax.request({
		    					url : basepath+ '/latentSourceChannelQueryAction!destroy.json?id='+ id,
		    					waitMsg : '正在保存数据,请等待...', // 显示读盘的动画效果，执行完成后效果消失
		    					success : function() {
		    						Ext.Msg.alert('提示', '操作成功!' );
		    						sourceChannelStore.reload();
		    					},
		    					failure : function() {
		    						Ext.Msg.alert('提示', '操作失败!' );
		    						sourceChannelStore.reload();
		    					}
		    				});
		    			});
		    	     }
	    		   }   
	    		]
	    })		
	    var sourceChannelgrid =new Ext.grid.GridPanel({//
	    			frame:true,
	    			height:240,
	    			autoScroll : true,
	    			bbar:sourceChannelbbar,
	    			tbar:sourceChannelTbar,
	    			stripeRows : true, // 斑马线
	    			store:sourceChannelStore,
	    			loadMask:true,
	    			cm :sourceChannelColumnmodel,
	    			region:'center',
	    			viewConfig:{
	    				forceFit:false,
	    				autoScroll:true
	    			},
	    			loadMask : {
	    				msg : '正在加载表格数据,请稍等...'
	    			}
	    });*/
	    
		  //已回收记录grid---------------------------------------------------------------------
	    var disRecyHisSerownum = new Ext.grid.RowNumberer({// 定义自动当前页行号
	    			header : 'No.',
	    			width : 28
	    		});
	    var disRecyHisSeColumnmodel = new Ext.grid.ColumnModel([
	        disRecyHisSerownum,
	        {header:'客户编号',dataIndex:'CUS_ID',width:100,sortable : true},
	        {header:'客户名称',dataIndex:'CUS_NAME',width:100,sortable : true},
	        {header:'客户手机号',dataIndex:'CALL_NO',width:100,sortable : true},
	        {header:'状态',dataIndex:'STATE',width:100,renderer:function(value){
	    				var val = translateLookupByKey("STATE_VALUE",value);
	    				return val?val:"";
	    				},sortable : true},
	        {header:'分配回收人',dataIndex:'MOVER_USER',width:100,sortable : true},
	        {header:'分配回收日期',dataIndex:'MOVER_DATE',width:100,sortable : true},
	        {header:'变更前所属主管经理',dataIndex:'CUST_MGR_BEFORE',width:100,sortable : true},
	        {header:'变更后所属主管经理',dataIndex:'CUST_MGR_AFTER',width:100,sortable : true}   
	    ]);
	    var disRecyHisSeRecord = new Ext.data.Record.create([
	    			{name:'CUS_ID'},
	    		    {name:'CUS_NAME'},
	    		    {name:'STATE'},
	    		    {name:'MOVER_USER'},
	    		    {name:'MOVER_DATE'},
	    		    {name:'CUST_MGR_BEFORE'},
	    		    {name:'CUST_MGR_AFTER'},
	    		    {name:'CALL_NO'}

	    		]);
	    var disRecyHisSeInfoReader = new Ext.data.JsonReader({
	    			totalProperty:'json.count',
	    			root:'json.data'
	    		},disRecyHisSeRecord);
	   
	    var disRecyHisSeInfoProxy = new Ext.data.HttpProxy({
	    			url:basepath+'/latentDispatchHisQueryAction.json'
	    		});	
	    var	disRecyHisSeInfoStore = new Ext.data.Store({
	    			restful : true,
	    			proxy : disRecyHisSeInfoProxy,
	    			reader :disRecyHisSeInfoReader,
	    			recordType:disRecyHisSeRecord
	    		});	
	    disRecyHisSeInfoStore.on('beforeload', function(disRecyHisSeInfoStore) { 
	    	disRecyHisSeInfoStore.baseParams = {'stateval':2}; 
	    });
	    var	disRecyHisSepagesize_combo = new Ext.form.ComboBox({
	    			name : 'pagesize',
	    			triggerAction : 'all',
	    			mode : 'local',
	    			store : new Ext.data.ArrayStore({
	    				fields : [ 'value', 'text' ],
	    				data : [ [ 10, '10条/页' ], [ 20, '20条/页' ], [ 50, '50条/页' ],
	    				         [ 100, '100条/页' ], [ 250, '250条/页' ],
	    				         [ 500, '500条/页' ] ]
	    			}),
	    			valueField : 'value',
	    			displayField : 'text',
	    			value : '20',
	    			forceSelection : true,
	    			width : 85
	    		});
	    var disRecyHisSenumber = parseInt(disRecyHisSepagesize_combo.getValue());
	    		/**
	    		 * 监听分页下拉框选择事件
	    		 */
	    		disRecyHisSepagesize_combo.on("select", function(comboBox) {
	    			disRecyHisbbar.pageSize = parseInt(disRecyHisSepagesize_combo.getValue()),
	    			disRecyHisSeInfoStore.load({
	    			params:{
	    				'stateval':2,
	    				start:0,
	    				limit: parseInt(disRecyHisSepagesize_combo.getValue())
	    			}
	    		});
	    		});
	    		//分页工具条定义
	    var	disRecyHisSebbar = new Ext.PagingToolbar({
	    			pageSize : disRecyHisSenumber,
	    			store : disRecyHisSeInfoStore,
	    			displayInfo : true,
	    			displayMsg : '显示{0}条到{1}条,共{2}条',
	    			emptyMsg : "没有符合条件的记录",
	    			items : ['-', '&nbsp;&nbsp;', disRecyHisSepagesize_combo]
	    		});	
	    var disRecyHisSeTbar=new Ext.Toolbar({
	    		items:[]
	    })		
	    var disRecyHisSegrid =new Ext.grid.GridPanel({//
	    			//title:'已回收记录',
	    			frame:true,
	    			height:240,
	    			autoScroll : true,
	    			bbar:disRecyHisSebbar,
	    			tbar:disRecyHisSeTbar,
	    			stripeRows : true, // 斑马线
	    			store:disRecyHisSeInfoStore,
	    			loadMask:true,
	    			cm :disRecyHisSeColumnmodel,
	    			region:'center',
	    			viewConfig:{
	    				forceFit:false,
	    				autoScroll:true
	    			},
	    			loadMask : {
	    				msg : '正在加载表格数据,请稍等...'
	    			}
	    }); 
	  //-----------------------------------------------  
	    
		var tbar=[{
			text:'创建CallReport',
			handler:function(){
				showCustomerViewByTitle('callreport创建');
			   }
		   },{
			text:'新增',
			hidden:JsContext.checkGrant('latentc_adds'),
			handler:function(){
				showCustomerViewByIndex(0);
			   }
		     },
		     {
				text:'修改',
				hidden:JsContext.checkGrant('latentc_edit'),
					handler:function(){
						showCustomerViewByIndex(1);
					   }
				     }
		     ,{
					text:'详情',
					hidden:JsContext.checkGrant('latentc_details'),
						handler:function(){
							showCustomerViewByIndex(2);
						   }
					     }
				,
				{
					text : '删除',
					hidden : JsContext.checkGrant('delete_latentInfo'),
					handler : function() {
						if(!getSelectedData()){
							Ext.Msg.alert('提示','请选择一条数据进行操作！');
							return false;
						}
						var appStrat = getSelectedData().data.STATE;  //潜在客户状态
						var delStates=getSelectedData().data.DELETE_CUST_STATE;//删除提交标记
						var id=getSelectedData().data.CUS_ID;
						if(appStrat=='1'){
							Ext.Msg.alert('提示','该客户已经是无效潜在客户!');
							return false;
						}
						if(delStates=='1'){
							Ext.Msg.alert('提示','该客户已经提交删除审批!');
							return false;
						}
						var roleStr=__roleCodes.toString();
						if(roleStr.indexOf("R125$")>=0||roleStr.indexOf("admin")>=0){
							Ext.MessageBox.confirm('提示', '确定删除吗?', function(buttonId) {
								if (buttonId.toLowerCase() == "no") {
									return false;
								}
								 showCustomerViewByTitle("删除编辑视图");
				    			 getCurrentView().contentPanel.getForm().findField('CUS_ID').setValue(getSelectedData().data.CUS_ID);
				    			 getCurrentView().contentPanel.getForm().findField('CUS_NAME').setValue(getSelectedData().data.CUS_NAME);
								
							});
						}else{
							if(!getSelectedData()){
								Ext.Msg.alert('提示','请选择一条数据进行操作！');
								return false;
							}else{
								var	createUser = getSelectedData().data.CUST_MGR;
								if(__roleCodes=='R303$'){
								if(createUser!=__userId){
									Ext.Msg.alert('提示','只能选择自己创建的数据!');
									return false;
								}}
								
								Ext.Ajax.request({
									url : basepath + '/myPotentialCustomer!delBeforCheck.json',
									params : {
										cusId : id
									},
									success : function(response) {
										var ret = Ext.decode(response.responseText);
										var conclusionstr=ret.conclusionstr;
										var spandaysstr=ret.spandaysstr;
										if(conclusionstr=='2'){
											Ext.Msg.alert('提示','该客户已经已经提交审批!');
											return false;
										}else{
											/*if(spandaysstr=='2'){
												Ext.Msg.alert('提示','该客户上传时间已经超过3天，不允许删除!');
												return false;
											}else{*/
												 showCustomerViewByTitle("删除编辑视图");
								    			 getCurrentView().contentPanel.getForm().findField('CUS_ID').setValue(getSelectedData().data.CUS_ID);
								    			 getCurrentView().contentPanel.getForm().findField('CUS_NAME').setValue(getSelectedData().data.CUS_NAME);
											//}
										}
									},
									failure : function(response) {
										var resultArray = Ext.util.JSON.decode(response.status);
				    			 		if(resultArray == 403) {
				    		           		Ext.Msg.alert('提示', response.responseText);
				    			 		}else{
				    						Ext.Msg.alert('提示', '删除失败,失败原因:' + response.responseText);
				    	 				}
									}
								});
									
							} 
							
						}
					}
				},
				/*{
					text : '恢复',
					//hidden : JsContext.checkGrant('reback_latentInfo'),
					handler : function() {
						if (getSelectedData() == false) {
							Ext.Msg.alert('提示', '请选择一条数据！');
							return false;
						} else {
							var selectRecords = getAllSelects();
							var state = '';
							for ( var i = 0; i < selectRecords.length; i++) {
								state += '' + selectRecords[i].data.STATE + ',';
							}
							var flag = state.indexOf('0,');
							if (state != '' && flag > (-1)) {
								Ext.Msg.alert('提示', '只能恢复无效的潜在客户');
								return false;
							}

							Ext.MessageBox.confirm('提示', '确定恢复吗?', function(buttonId) {
								if (buttonId.toLowerCase() == "no") {
									return false;
								}
								var selectRecords = getAllSelects();
								var cusId = '';
								for ( var i = 0; i < selectRecords.length; i++) {
									if (i == 0) {
										cusId = "'" + selectRecords[i].data.CUS_ID
												+ "'";
									} else {
										cusId += "," + "'"
												+ selectRecords[i].data.CUS_ID + "'";
									}
								}
								Ext.Ajax.request({
									url : basepath
											+ '/myPotentialCustomer!recoverBackInfo.json',
									params : {
										cusId : cusId
									},
									success : function() {
										Ext.Msg.alert('提示', '恢复成功');
										reloadCurrentData();
									},
									failure : function() {
										Ext.Msg.alert('提示', '恢复失败');
										reloadCurrentData();
									}
								});
							});
						}
					}
				},*/{
					text : '分派',
				    hidden : JsContext.checkGrant('fp_latentInfo'),
					handler : function() {
						if (getSelectedData() == false) {
							Ext.Msg.alert('提示', '请选择数据！');
							return false;
						}
						var selectRecords = getAllSelects();
						var cusId = '';
						for ( var i = 0; i < selectRecords.length; i++) {
							/*if(selectRecords[i].data.BACK_STATE=='2'){
								Ext.Msg.alert('提示', '请先回收已分派数据！');
								return false;
							}*/
							if (i == 0) {
								cusId = "'" + selectRecords[i].data.CUS_ID + "'";
							} else {
								cusId += "," + "'" + selectRecords[i].data.CUS_ID + "'";
							}
						}
							Ext.Ajax.request({
								url : basepath + '/myPotentialCustomer!doFpCheckRole.json',
								method : 'GET',
								waitMsg : '正在保存数据,请等待...', // 显示读盘的动画效果，执行完成后效果消失
								// form:transForm1.getForm().CUS_ID,
								params : {
									cusId : cusId
								},
								success : function(response) {
									var ret = Ext.decode(response.responseText);
									var result=ret.result;
									var result1=ret.result1;
									var levelmover1=ret.levelmover1;
									var levelmover=ret.levelmover;
									if(result=='1'){
										/*if(levelmover1==levelmover&&result1=='1'){
											Ext.Msg.alert('提示', '请先回收已分派数据！');
											return false;
										}else{*/
											json = {
													'transedData' : []
												};
												for ( var i = 0; i < getAllSelects().length; i++) {
													var records = getAllSelects()[i].data;
													var temp = {};
													temp.CUS_ID = records.CUS_ID;
													temp.CUS_NAME = records.CUS_NAME;
													json.transedData.push(temp);
												}
												transedStore.loadData(json);
												transForm1.form.reset();
												var d = new Date();
												d.setMonth(d.getMonth()+3);
												transForm1.getForm().findField("MONTHVALUE").setValue(d);
												showCustomerViewByIndex(3);
										//}
										
									}else{
										Ext.Msg.alert('提示', '请先回收已分派数据！');
										return false;
									}
								},
								failure : function(response) {
									var resultArray = Ext.util.JSON.decode(response.status);
									if(resultArray == 403) {
								           Ext.Msg.alert('提示', response.responseText);
								     } else {
									Ext.Msg.alert('提示', '分配失败,失败原因:' + response.responseText);
									reloadCurrentData();
								  }
								}
							});
						/*Ext.Ajax.request({
									url : basepath+ '/myPotentialCustomer!doCheckRole.json',
									method : 'GET',
									params : {
										cusId : cusId
									},
									success : function(response) {
										var ret = Ext.decode(response.responseText);
										var nametype = ret.type;
										if (nametype == '1') {
											Ext.MessageBox.alert('提示','所选客户存在已有分配人且归属人为RM的个金潜在客户,只能先进行回收,回收后再分配');
											return false;
										} else if (nametype == '3') {
											Ext.MessageBox.alert('提示','只能分配尚且没有分配人且归属人不为RM的潜在客户,所选客户存在不满足条件的');
											return false;
										} else if (nametype == '4') {
											Ext.MessageBox.alert('提示','所选潜在客户存在已发生具体业务或者未收回,个金潜在客户不能进行分配,只能先回收再分配');
											return false;
										} else if (nametype == '2') {
											json = {
												'transedData' : []
											};
											for ( var i = 0; i < getAllSelects().length; i++) {
												var records = getAllSelects()[i].data;
												var temp = {};
												temp.CUS_ID = records.CUS_ID;
												temp.CUS_NAME = records.CUS_NAME;
												json.transedData.push(temp);
											}
											transedStore.loadData(json);
											transForm1.form.reset();
											showCustomerViewByIndex(3);
										}
									}
								});
*/
					}
				},{
					text : '回收',
					hidden : JsContext.checkGrant('hs_latentInfo'),
					handler : function() {
						if (getSelectedData() == false) {
							Ext.Msg.alert('提示', '请选择数据！');
							return false;
						}
							var selectRecords = getAllSelects();
							var cusId = '';
							for ( var i = 0; i < selectRecords.length; i++) {
								if (i == 0) {
									cusId = "'" + selectRecords[i].data.CUS_ID + "'";
								} else {
									cusId += "," + "'" + selectRecords[i].data.CUS_ID
											+ "'";
								}
							}
							 json = {
												'transedData' : []
											};
											for ( var i = 0; i < getAllSelects().length; i++) {
												var records = getAllSelects()[i].data;
												var temp = {};
												temp.CUS_ID = records.CUS_ID;
												temp.CUS_NAME = records.CUS_NAME;
												json.transedData.push(temp);
											}
											transedStore.loadData(json);
											hstransForm.form.reset();
											showCustomerViewByIndex(4);
					}
				},{
					text : '模板下载',
					//hidden : JsContext.checkGrant('template_down'),
					handler : function() {
						var winPara = 'height=100, width=200, top=300, left=500, toolbar=no,menubar=no,'
								+ ' scrollbars=no, resizable=no,location=no, status=no';
						var fileName = 'latentPersonalCusImport.xlsx';// 模板名称
						var uploadUrl = basepath + '/TempDownload?filename=' + fileName;
						window.open(uploadUrl, '', winPara);
					}
				}, {
					text : '批量导入',
					//hidden : JsContext.checkGrant('latentc_import'),
					handler : function() {
						importForm.tradecode = 'importLantentPersonalCus';
						importWindow.show();
					}
				},// 导出按钮
				new Com.yucheng.crm.common.NewExpButton({
					formPanel : 'searchCondition',
					hidden : JsContext.checkGrant('latentc_export'),
					url : basepath + '/latentCustInfoQueryAction.json'
				}),/*{
					text:'来源渠道维护',
					//hidden:JsContext.checkGrant('sourceChannel_etid'),
						handler:function(){
							showCustomerViewByTitle('来源渠道维护');
						}
				},*/{
					text:'重复客户清单',
					//hidden:JsContext.checkGrant('latentc_details'),
						handler:function(){
							showCustomerViewByTitle('重复客户清单');
						   }
				},{
					text:'被回收客户清单',
					//hidden:JsContext.checkGrant('latentc_huis'),
						handler:function(){
							showCustomerViewByTitle('被回收客户清单');
						}
				},{
					  text:'已删除潜在客户清单',
						hidden:JsContext.checkGrant('deleted_latentc_view'),
							handler:function(){
								showCustomerViewByTitle('已删除潜在客户清单');
							}
					}];
		var customerView = [
							{
								title : '新增',
								hideTitle:true,
								type : 'form',
								groups : [
											{
												columnCount : 1 ,
												fields : [
													{name:'CUS_NAME',text:'客户名称',xtype:'textfield',searchField:true,maxLength:80,allowBlank:false}
                                                  ],
												fn : function(CUS_NAME){
													CUS_NAME.anchor = '95%';
													return [CUS_NAME];
												}},
										   {
							                fields:[
								              {name:'CUS_ID',text:'客户号',xtype:'textfield',searchField:true},
								             {name:'PHONE_AREA_CODE',text:'国际电话区号',gridField:false,maxLength:20,resutlFloat:'left',translateType:'IDD_CODE_NEW_V',allowBlank:false,
								            	  listeners:{
								            		  change : function(){
								            			  visibleCallNo();
								            	  }}	  
								             },	
											{name:'CALL_NO',text:'手机号码',invalidText:'请输入数字',maxLength :20,allowBlank:false,resutlFloat:'left',
												    	maxLengthText:'最多只能输入20位数字',xtype:'textfield',gridField:false
												    	,listeners:{
												    		blur : function(){
												    			  visibleCallNo();
												    	}}
											},
										    {name:'SOURCE_CHANNEL',text:'来源渠道',searchField:true,resutlFloat:'right',translateType:'XD000353',gridField:true,allowBlank:false},
								            {name:'CERT_TYPE',text:'证件类型',searchField:true,resutlFloat:'right',translateType:'XD000040'},
								            {name:'CERT_CODE',text:'证件号码',xtype:'textfield',resutlFloat:'right',searchField:true,maxLength:40},
								            {name:'CUST_TYPE',text:'客户类型',searchField:true,resutlFloat:'right',translateType:'CUST_TYPE_VALUE',editable : false},
								            {name:'MKT_ACTIVITIE',text:'营销活动',gridField:true,maxLength:80,resutlFloat:'right',translateType:'MKT_ACTIVITIE_VAL',gridField:true,multiSelect : true,multiSeparator : ','
										      },
								           // {name : 'MKT_ACTIVITIE',text : '营销活动',xtype : 'lovcombo',store : mktActiStore,forceSelection : true,
								        	//	editable : false,ComboBox : true,mode : 'local',triggerAction : 'all',displayField : 'MKT_ACTI_NAME',valueField : 'MKT_ACTI_CODE',width : 100,anchor : '95%'},
								            {name:'CUS_NATIONALITY',text:'国籍',searchField:true,resutlFloat:'right',translateType:'XD000025',gridField:true},
								            {name:'REFEREES_ID',text:'推荐人ID',xtype:'latentRefereesQuery',hiddenName:'CID',singleSelected: true,editable : true,callback : function(a,b) {
												  var REFEREES_ID_P=getCurrentView().contentPanel.form.findField("REFEREES_ID");
												  REFEREES_ID_P.setValue(b[0].data.CID);
											  }},
								            {name:'ATTEN_NAME',text:'联系人',xtype:'textfield',gridField:false,maxLength:80},
								            {name:'CONTMETH_INFO',text:'座机号码',xtype:'textfield',gridField:false,maxLength:20},
								            {name:'ZIPCODE',text:'邮编',xtype:'numberfield',gridField:false,maxLength:50},
								            {name:'JOB_TYPE',text:'职业',translateType:'XD000005'},
								            {name:'INDUST_TYPE',text:'职务',searchField:true,resutlFloat:'right',translateType:'XD000007'},
								            {name:'STATE',text:'潜在客户状态',translateType:'POTENTAIL_STATE',gridField:true},
								            {name:'CUS_EMAIL',text:'E-mail',xtype:'textfield',gridField:true,maxLength:100},
								            {name:'CUS_WECHATID',text:'微信号',xtype:'textfield',gridField:false,maxLength:100},
								            {name:'RECORD_SESSION',text:'沟通话术',xtype:'textarea',gridField:false,maxLength:200}
								           
								            ],          	
											fn : function(CUS_ID,PHONE_AREA_CODE,CALL_NO,SOURCE_CHANNEL,CERT_TYPE,CERT_CODE,CUST_TYPE,MKT_ACTIVITIE,CUS_NATIONALITY,REFEREES_ID,ATTEN_NAME,
													CONTMETH_INFO,ZIPCODE,JOB_TYPE,INDUST_TYPE,STATE,CUS_EMAIL,CUS_WECHATID){			
												CUS_ID.hidden=true;
												STATE.hidden = true;
												return [PHONE_AREA_CODE,CALL_NO,SOURCE_CHANNEL,CERT_TYPE,CERT_CODE,CUST_TYPE,MKT_ACTIVITIE,CUS_NATIONALITY,REFEREES_ID,ATTEN_NAME,CONTMETH_INFO,ZIPCODE
												        ,JOB_TYPE,INDUST_TYPE,STATE,CUS_ID,CUS_EMAIL,CUS_WECHATID];
											}
										},{
											columnCount : 1 ,
											fields : ['RECORD_SESSION'],
											fn : function(RECORD_SESSION){
												RECORD_SESSION.anchor = '95%';
												return [RECORD_SESSION];
											}},{
											columnCount : 1 ,
											fields : ['CUS_ADDR'],
											fn : function(CUS_ADDR){
												CUS_ADDR.anchor = '95%';
												return [CUS_ADDR];
											}}
								],
								formButtons : [ {
									text : '保存',
									fn : function(formPanel, basicForm) {
										debugger;
										if (!formPanel.getForm().isValid()) {
											Ext.MessageBox.alert('提示', '输入有误,请检查输入项');
											return false;
										};
										var data=formPanel.getForm().getFieldValues();
										var custnamev=data.CUS_NAME;
										var identtypev=data.CERT_TYPE;
										var identnov=data.CERT_CODE;
										var callnov=data.CALL_NO;
										var phoneareacode=data.PHONE_AREA_CODE;
										Ext.Msg.wait('正在进行验证处理，请稍后......', '系统提示');
										if(custnamev!=''&&identtypev!=''&&identnov!=''){
										Ext.Ajax.request({
										  url : basepath+ '/myPotentialCustomer!doParameterCheck.json',
										  method : 'GET',
										  params : {
											  custnamev:custnamev,
											  identtypev:identtypev,
											  identnov:identnov,
											  callnov:phoneareacode+'-#-'+callnov
											},
										  success : function(response) {
											  debugger;
											var ret = Ext.decode(response.responseText);
											var nametype = ret.type;
											var phtype=ret.phtype;
											var custid=ret.custid;
											var source=ret.source;
											var cusname=ret.cusname;
											var custmgr=ret.custmgr;
											var cusorg=ret.cusorg;
											var custmgrv=ret.custmgrv;
											var cusorgv=ret.cusorgv;
											var callnov=ret.callno;
											var callno;
											if(callnov!=null){
												callno=callnov.replace("#-","");
											}
											if (nametype == '1') {
												Ext.MessageBox.alert('提示','客户已是正式客户，将不继续进行新增！');
												return false;
											} else if (nametype == '2') {
												Ext.MessageBox.alert('提示','该潜在客户已存在，将不继续进行新增！');
												return false;
											} else if (nametype == '3') {
												Ext.MessageBox.confirm('提示','该潜在客户证件类型及号码已存在，是否继续！',
														function(buttonId) {
															if (buttonId.toLowerCase() == "no") {
																if(phtype=='5'){
																	Ext.MessageBox.confirm('提示','该客户可能已经存在 !\n '+cusname+'；<br/>所属机构：'+cusorgv+'；<br/>所属经理：'+custmgrv+'；<br/>是否继续新增？',function(buttonId){
																		if(buttonId.toLowerCase() == "no"){
																			var upactivitie=getCurrentView().contentPanel.getForm().findField('SOURCE_CHANNEL').getValue();
																			var tempactivitie;
																			if(source==""){
																				tempactivitie=upactivitie;
																			}else{
																				tempactivitie=source+","+upactivitie;
																			}
																			updateSourceChannelFn(custid,tempactivitie);
																		}
																	});
																	return false;
																}else if(phtype=='4'){
																	Ext.MessageBox.confirm('提示','该客户可能已经存在 !\n '+cusname+'；<br/>所属机构：'+cusorgv+'；<br/>所属经理：'+custmgrv+'；<br/>是否继续新增？',
																			function(buttonId) {
																				if (buttonId.toLowerCase() == "yes") {
																					//新增
																					savelaterFn(data);
																				}else{
																					var upactivitie=getCurrentView().contentPanel.getForm().findField('SOURCE_CHANNEL').getValue();
																					var tempactivitie;
																					if(source==""){
																						tempactivitie=upactivitie;
																					}else{
																						tempactivitie=source+","+upactivitie;
																					}
																					updateSourceChannelFn(custid,tempactivitie);
																					return false;
																				}
																	});
																}else{
																	//新增保存
																	savelaterFn(data);
																}
															}else{
																//新增
																savelaterFn(data);
															}
														});
											}else{
												if(phtype=='5'){
													Ext.MessageBox.confirm('提示','该客户可能已经存在 !\n '+cusname+'；<br/>所属机构：'+cusorgv+'；<br/>所属经理：'+custmgrv+'；<br/>是否继续新增？',function(buttonId){
														if(buttonId.toLowerCase() == "no"){
															var upactivitie=getCurrentView().contentPanel.getForm().findField('SOURCE_CHANNEL').getValue();
															var tempactivitie;
															if(source==""){
																tempactivitie=upactivitie;
															}else{
																tempactivitie=source+","+upactivitie;
															}
															updateSourceChannelFn(custid,tempactivitie);
														}
													});
													return false;
												}else if(phtype=='4'){
													Ext.MessageBox.confirm('提示','该客户可能已经存在 !\n '+cusname+'；<br/>所属机构：'+cusorgv+'；<br/>所属经理：'+custmgrv+'；<br/>是否继续新增？',
															function(buttonId) {
																if (buttonId.toLowerCase() == "yes") {
																	//新增
																	savelaterFn(data);
																}else{
																	var upactivitie=getCurrentView().contentPanel.getForm().findField('SOURCE_CHANNEL').getValue();
																	var tempactivitie;
																	if(source==""){
																		tempactivitie=upactivitie;
																	}else{
																		tempactivitie=source+","+upactivitie;
																	}
																	updateSourceChannelFn(custid,tempactivitie);
																	return false;
																}
													});
												}else{
													//新增保存
													savelaterFn(data);
												}
											}
											},
											failure : function(response) {
											}
										});
										}else{
											Ext.Ajax.request({
												  url : basepath+ '/myPotentialCustomer!doParameterCheck.json',
												  method : 'GET',
												  params : {
													  callnov:phoneareacode+'-#-'+callnov,
													  custnamev:custnamev
													},
												  success : function(response) {
													var ret = Ext.decode(response.responseText);
													var nametype = ret.type;
													var phtype=ret.phtype;
													var custid=ret.custid;
													var source=ret.source;
													var cusname=ret.cusname;
													var custmgr=ret.custmgr;
													var cusorg=ret.cusorg;
													var custmgrv=ret.custmgrv;
													var cusorgv=ret.cusorgv;
													var callnov=ret.callno;
													var callno;
													if(callnov!=null){
														callno=callnov.replace("#-","");
													}
													if (nametype == '1') {
														Ext.MessageBox.alert('提示','客户已是正式客户，将不继续进行新增！');
														return false;
													}
													if(phtype=='5'){
														Ext.MessageBox.confirm('提示','该客户可能已经存在 !\n '+cusname+'；<br/>所属机构：'+cusorgv+'；<br/>所属经理：'+custmgrv+'；<br/>是否继续新增？',function(buttonId){
															if(buttonId.toLowerCase() == "no"){
																var upactivitie=getCurrentView().contentPanel.getForm().findField('SOURCE_CHANNEL').getValue();
																var tempactivitie;
																if(source==""){
																	tempactivitie=upactivitie;
																}else{
																	tempactivitie=source+","+upactivitie;
																}
																updateSourceChannelFn(custid,tempactivitie);
															}
														});
														return false;
													}else if(phtype=='4'){
														Ext.MessageBox.confirm('提示','该客户可能已经存在 !\n '+cusname+'；<br/>所属机构：'+cusorgv+'；<br/>所属经理：'+custmgrv+'；<br/>是否继续新增？',
																function(buttonId) {
																	if (buttonId.toLowerCase() == "yes") {
																		//新增
																		savelaterFn(data);
																	}else{
																		var upactivitie=getCurrentView().contentPanel.getForm().findField('SOURCE_CHANNEL').getValue();
																		var tempactivitie;
																		if(source==""){
																			tempactivitie=upactivitie;
																		}else{
																			tempactivitie=source+","+upactivitie;
																		}
																		updateSourceChannelFn(custid,tempactivitie);
																		return false;
																	}
														});
													}else{
														//新增保存
														savelaterFn(data);
													}
													},
													failure : function(response) {}
											
										});}
										
									}
									
								}]
							     
							    },
								{
									title : '修改',
									hideTitle:true,
									autoLoadSeleted : true,
									type : 'form',
									groups : [
										{
											columnCount : 1 ,
											fields : [
												{name:'CUS_NAME',text:'客户名称',xtype:'textfield',searchField:true,maxLength:80,allowBlank:false}
										      ],
											fn : function(CUS_NAME){
												CUS_NAME.anchor = '95%';
												return [CUS_NAME];
											}},
											{
											  columnCount : 2,
								              fields:[
									            {name:'CUS_ID',text:'客户号',xtype:'textfield',searchField:true},
									            {name:'PHONE_AREA_CODE',text:'国际电话区号',gridField:false,maxLength:20,resutlFloat:'left',translateType:'IDD_CODE_NEW_V',allowBlank:false
									            	,listeners:{
											    		blur : function(){
											    			  visibleCallNo();
											    	}}
										         },
												{name:'CALL_NO',text:'手机号码',invalidText:'请输入数字',maxLength :20,allowBlank:false,resutlFloat:'left',
												    	maxLengthText:'最多只能输入20位数字',xtype:'textfield',gridField:false
												    	,listeners:{
												    		blur : function(){
												    			  visibleCallNo();
												    	}}
											     },
												{name:'SOURCE_CHANNEL',text:'来源渠道',searchField:true,resutlFloat:'right',translateType:'XD000353',gridField:true,allowBlank:false},
									            {name:'CERT_TYPE',text:'证件类型',searchField:true,resutlFloat:'right',translateType:'XD000040'},
									            {name:'CERT_CODE',text:'证件号码',xtype:'textfield',resutlFloat:'right',searchField:true,maxLength:40},
									            {name:'CUST_TYPE',text:'客户类型',searchField:true,resutlFloat:'right',translateType:'CUST_TYPE_VALUE',editable : false},
									            {name:'MKT_ACTIVITIE',text:'营销活动',gridField:true,maxLength:80,resutlFloat:'right',translateType:'MKT_ACTIVITIE_VAL',gridField:true,multiSelect : true,multiSeparator : ','
											       },
									           // {name : 'MKT_ACTIVITIE',text : '营销活动',xtype : 'lovcombo',store : mktActiStore,forceSelection : true,
									        	//	editable : false,ComboBox : true,mode : 'local',triggerAction : 'all',displayField : 'MKT_ACTI_NAME',valueField : 'MKT_ACTI_CODE',width : 100,anchor : '95%'},
									            {name:'CUS_NATIONALITY',text:'国籍',searchField:true,resutlFloat:'right',translateType:'XD000025',searchField:true,gridField:true},
									           // {name:'REFEREES_ID',text:'推荐人',xtype:'textfield',gridField:false,maxLength:80,hidden:true},
									            {name:'REFEREES_ID',text:'推荐人ID',xtype:'latentRefereesQuery',hiddenName:'CID',singleSelected: true,editable : true,callback : function(a,b) {
													  var REFEREES_ID_P=getCurrentView().contentPanel.form.findField("REFEREES_ID");
													  REFEREES_ID_P.setValue(b[0].data.CID);
												  }},
									            {name:'ATTEN_NAME',text:'联系人',xtype:'textfield',gridField:false,maxLength:80},
									            {name:'CONTMETH_INFO',text:'座机号码',xtype:'textfield',gridField:false,maxLength:20},
									            {name:'ZIPCODE',text:'邮编',xtype:'numberfield',gridField:false,maxLength:50},
									            {name:'JOB_TYPE',text:'职业',translateType:'XD000005'},
									            {name:'INDUST_TYPE',text:'职务',searchField:true,resutlFloat:'right',translateType:'XD000007'},
									            {name:'STATE',text:'潜在客户状态',translateType:'POTENTAIL_STATE',gridField:true},
									            {name:'CUS_EMAIL',text:'E-mail',xtype:'textfield',gridField:true,maxLength:100},
									            {name:'CUS_WECHATID',text:'微信号',xtype:'textfield',gridField:false,maxLength:100},
									            {name:'RECORD_SESSION',text:'沟通话术',xtype:'textarea',gridField:false,maxLength:200},
									            {name:'CUS_ADDR',text:'通讯地址',xtype:'textarea',gridField:false,maxLength:200}
									            ],          	
												fn : function(CUS_ID,PHONE_AREA_CODE,CALL_NO,SOURCE_CHANNEL,CERT_TYPE,CERT_CODE,CUST_TYPE,MKT_ACTIVITIE,CUS_NATIONALITY,REFEREES_ID,ATTEN_NAME,
														CONTMETH_INFO,ZIPCODE,JOB_TYPE,INDUST_TYPE,STATE,CUS_EMAIL,CUS_WECHATID){			
													CUS_ID.hidden=true;
													STATE.hidden = true;
													return [PHONE_AREA_CODE,CALL_NO,SOURCE_CHANNEL,CERT_TYPE,CERT_CODE,CUST_TYPE,MKT_ACTIVITIE,CUS_NATIONALITY,REFEREES_ID,ATTEN_NAME,CONTMETH_INFO,ZIPCODE
													        ,JOB_TYPE,INDUST_TYPE,STATE,CUS_ID,CUS_EMAIL,CUS_WECHATID];
												}
											},{
												columnCount : 1 ,
												fields : ['RECORD_SESSION'],
												fn : function(RECORD_SESSION){
													RECORD_SESSION.anchor = '95%';
													return [RECORD_SESSION];
												}},{
												columnCount : 1 ,
												fields : ['CUS_ADDR'],
												fn : function(CUS_ADDR){
													CUS_ADDR.anchor = '95%';
													return [CUS_ADDR];
												}}
									],
									formButtons : [ {
										text : '保存',
										fn : function(formPanel, basicForm) {
											if (!formPanel.getForm().isValid()) {
												Ext.MessageBox.alert('提示', '输入有误,请检查输入项');
												return false;
											};
											var commitData = translateDataKey(basicForm.getFieldValues(),_app.VIEWCOMMITTRANS);
											
											Ext.Ajax.request({
												url : basepath + '/myPotentialCustomer.json',
												method : 'POST',
											    params:commitData ,
												waitMsg : '正在保存数据,请等待...',
												success : function(response) {
												    Ext.Msg.alert('提示', '提交成功！');
													reloadCurrentData();
												},
												failure : function(response) {
													Ext.Msg.alert('提示', '提交失败！');
													reloadCurrentData();
												}
											});
										}
									}]
							   },
							   {
									title : '详情',
									hideTitle:true,
									autoLoadSeleted : true,
									type : 'form',
									groups : [
											{
												columnCount : 2,
												fields : [
													{name:'CUS_NAME',text:'客户名称',xtype:'textfield',searchField:true,maxLength:80,allowBlank:false},
													  {name:'CUS_ID',text:'客户号',xtype:'textfield',searchField:true,hidden:true}
											      ],
												fn : function(CUS_NAME,CUS_ID){
													CUS_NAME.readOnly = true;
													CUS_NAME.cls = 'x-readOnly';
													CUS_ID.readOnly = true;
													CUS_ID.cls = 'x-readOnly';
													return [CUS_NAME,CUS_ID];
												}},
											{
												columnCount : 2,
								              fields:[
									          
									            {name:'PHONE_AREA_CODE',text:'国际电话区号',gridField:false,maxLength:20,resutlFloat:'left',translateType:'IDD_CODE_NEW',allowBlank:false
									            	,listeners:{
											    		blur : function(){
											    			  visibleCallNo();
											    	}}
										         },
												{name:'CALL_NO',text:'手机号码',invalidText:'请输入数字',maxLength :20,allowBlank:false,resutlFloat:'left',
												    	maxLengthText:'最多只能输入20位数字',xtype:'textfield',gridField:false
												    	,listeners:{
												    		blur : function(){
												    			  visibleCallNo();
											 	    	}}
											    },
											    {name:'SOURCE_CHANNEL',text:'来源渠道',searchField:true,resutlFloat:'right',translateType:'XD000353',multiSelect : true,multiSeparator : ',',gridField:true,allowBlank:false},
									            {name:'CERT_TYPE',text:'证件类型',searchField:true,resutlFloat:'right',translateType:'XD000040'},
									            {name:'CERT_CODE',text:'证件号码',xtype:'textfield',resutlFloat:'right',searchField:true,maxLength:40},
									            {name:'CUST_TYPE',text:'客户类型',searchField:true,resutlFloat:'right',translateType:'CUST_TYPE_VALUE'},
									            {name:'MKT_ACTIVITIE',text:'营销活动',gridField:true,maxLength:80,resutlFloat:'right',translateType:'MKT_ACTIVITIE_VAL',gridField:true,multiSelect : true,multiSeparator : ','
											       },
									           //// {name : 'MKT_ACTIVITIE',text : '营销活动',xtype : 'lovcombo',store : mktActiStore,forceSelection : true,
									        	//	editable : false,ComboBox : true,mode : 'local',triggerAction : 'all',displayField : 'MKT_ACTI_NAME',valueField : 'MKT_ACTI_CODE',width : 100,anchor : '95%'},
									            {name:'CUS_NATIONALITY',text:'国籍',searchField:true,resutlFloat:'right',translateType:'XD000025',searchField:true,gridField:true},
									            //{name:'REFEREES_ID',text:'推荐人',xtype:'textfield',gridField:false,maxLength:80},
									            {name:'REFEREES_ID',text:'推荐人ID',xtype:'latentRefereesQuery',hiddenName:'CID',singleSelected: true,editable : true,callback : function(a,b) {
													  var REFEREES_ID_P=getCurrentView().contentPanel.form.findField("REFEREES_ID");
													  REFEREES_ID_P.setValue(b[0].data.CID);
												  }},
									            {name:'ATTEN_NAME',text:'联系人',xtype:'textfield',gridField:false,maxLength:80},
									            {name:'CONTMETH_INFO',text:'座机号码',xtype:'textfield',gridField:false,maxLength:20},
									            {name:'ZIPCODE',text:'邮编',xtype:'numberfield',gridField:false,maxLength:50},
									            {name:'JOB_TYPE',text:'职业',translateType:'XD000005'},
									            {name:'INDUST_TYPE',text:'职务',searchField:true,resutlFloat:'right',translateType:'XD000007'},
									            {name:'STATE',text:'潜在客户状态',translateType:'POTENTAIL_STATE',gridField:true},
									            {name:'CUS_EMAIL',text:'E-mail',xtype:'textfield',gridField:true,maxLength:100},
									            {name:'CUS_WECHATID',text:'微信号',xtype:'textfield',gridField:false,maxLength:100}
									           // {name:'FORMAL_CUST_FLAG',text:'客户标识',translateType:'FORMAL_CUST_FLAG_V',gridField:true},
									           // {name:'RECORD_SESSION',text:'沟通话术',xtype:'textarea',gridField:false,maxLength:200},
									            //{name:'CUS_ADDR',text:'通讯地址',xtype:'textarea',gridField:false,maxLength:200}
									            ],          	
												fn : function(PHONE_AREA_CODE,CALL_NO,CERT_TYPE,CERT_CODE,CUST_TYPE,MKT_ACTIVITIE,SOURCE_CHANNEL,CUS_NATIONALITY,REFEREES_ID,ATTEN_NAME,
														CONTMETH_INFO,ZIPCODE,JOB_TYPE,INDUST_TYPE,STATE,CUS_EMAIL,CUS_WECHATID){			
													
													PHONE_AREA_CODE.readOnly = true;
													PHONE_AREA_CODE.cls = 'x-readOnly';
													CALL_NO.readOnly = true;
													CALL_NO.cls = 'x-readOnly';
													CERT_TYPE.readOnly = true;
													CERT_TYPE.cls = 'x-readOnly';
													CERT_CODE.readOnly = true;
													CERT_CODE.cls = 'x-readOnly';
													CUST_TYPE.readOnly = true;
													CUST_TYPE.cls = 'x-readOnly';
													MKT_ACTIVITIE.readOnly = true;
													MKT_ACTIVITIE.cls = 'x-readOnly';
													SOURCE_CHANNEL.readOnly = true;
													SOURCE_CHANNEL.cls = 'x-readOnly';
													CUS_NATIONALITY.readOnly = true;
													CUS_NATIONALITY.cls = 'x-readOnly';
													REFEREES_ID.readOnly = true;
													REFEREES_ID.cls = 'x-readOnly';
													ATTEN_NAME.readOnly = true;
													ATTEN_NAME.cls = 'x-readOnly';
													CONTMETH_INFO.readOnly = true;
													CONTMETH_INFO.cls = 'x-readOnly';
													ZIPCODE.readOnly = true;
													ZIPCODE.cls = 'x-readOnly';
													JOB_TYPE.readOnly = true;
													JOB_TYPE.cls = 'x-readOnly';
													INDUST_TYPE.readOnly = true;
													INDUST_TYPE.cls = 'x-readOnly';
													STATE.readOnly = true;
													STATE.cls = 'x-readOnly';
													CUS_EMAIL.readOnly = true;
													CUS_EMAIL.cls = 'x-readOnly';
													CUS_WECHATID.readOnly = true;
													CUS_WECHATID.cls = 'x-readOnly';
												
													return [PHONE_AREA_CODE,CALL_NO,CERT_TYPE,CERT_CODE,CUST_TYPE,MKT_ACTIVITIE,SOURCE_CHANNEL,CUS_NATIONALITY,REFEREES_ID,ATTEN_NAME,CONTMETH_INFO,ZIPCODE
													        ,JOB_TYPE,INDUST_TYPE,STATE,CUS_EMAIL,CUS_WECHATID];
												}
											},{
												columnCount : 1 ,
												fields : ['RECORD_SESSION'],
												fn : function(RECORD_SESSION){
													RECORD_SESSION.anchor = '95%';
													RECORD_SESSION.readOnly = true;
													RECORD_SESSION.cls = 'x-readOnly';
													return [RECORD_SESSION];
												}},{
												columnCount : 1 ,
												fields : ['CUS_ADDR'],
												fn : function(CUS_ADDR){
													CUS_ADDR.anchor = '95%';
													CUS_ADDR.readOnly = true;
													CUS_ADDR.cls = 'x-readOnly';
													return [CUS_ADDR];
												}}
									],
									formButtons : [ {
										text : '修改历史列表',
										hidden : JsContext.checkGrant('latent_edit_his'),
										fn : function(formPanel, basicForm) {
											showCustomerViewByIndex(5);
										}
									}]
							   }, {
									title : '客户分派',
									hideTitle : true,
									type : 'panel',
									items : [ cTrans ]
								}, {
									title : '客户回收',
									hideTitle : true,
									type : 'panel',
									items : [ hscpanel ]
								},{
									title:'修改历史',
									suspendWidth: 950,
									hideTitle : true,
									type : 'tabpanel',
									items : [ tabs_panel ]
							  },{
									title:'重复客户清单',
									suspendWidth: 950,
									hideTitle : true,
									type : 'gridpanel',
									items : [ againCreateGrid ]
							  },
								{
									title : '删除编辑视图',
									hideTitle:true,
									type : 'form',
									groups : [
											   {
												   columnCount : 1 ,
								                fields:[
									             {name:'CUS_ID',text:'客户号',xtype:'textfield',searchField:true}],          	
												fn : function(CUS_ID){
													CUS_ID.anchor = '60%';
													CUS_ID.readOnly = true;
													CUS_ID.cls = 'x-readOnly';
													return [CUS_ID];
												 }
											    },{
												columnCount : 1 ,
												fields : [ {name:'CUS_NAME',text:'客户名',xtype:'textfield',gridField:false,maxLength:200}],
												fn : function(CUS_NAME){
													CUS_NAME.anchor = '60%';
													CUS_NAME.readOnly = true;
													CUS_NAME.cls = 'x-readOnly';
													return [CUS_NAME];
												}},{
												columnCount : 1 ,
												fields : [ {name:'DELETE_REASON',text:'删除原因',searchField:true,resutlFloat:'right',translateType:'LATENT_DELETE_REASON',allowBlank:false}],
												fn : function(DELETE_REASON){
													DELETE_REASON.anchor = '60%';
													return [DELETE_REASON];
												}},{
													columnCount : 1,
													fields : [ {name:'DELETE_NOTE',text:'备注',xtype:'textarea',gridField:false,maxLength:150}],
													fn : function(DELETE_NOTE){
														DELETE_NOTE.anchor = '60%';
														return [DELETE_NOTE];
													}}
									],
									formButtons : [ {
										text : '提交',
										fn : function(formPanel, basicForm) {
											if (!formPanel.getForm().isValid()) {
												Ext.MessageBox.alert('提示', '输入有误,请检查输入项');
												return false;
											};
											var data=formPanel.getForm().getFieldValues();
											var commitData = translateDataKey(data,_app.VIEWCOMMITTRANS);	
											var roleStr=__roleCodes.toString();
											if(roleStr.indexOf("R125$")>=0||roleStr.indexOf("admin")>=0){
													Ext.Ajax.request({
														url : basepath + '/myPotentialCustomer!batchDel.json',
														params : commitData,
														success : function() {
															Ext.Msg.alert('提示', '删除成功');
															reloadCurrentData();
														},
														failure : function(response) {
															var resultArray = Ext.util.JSON.decode(response.status);
									    			 		if(resultArray == 403) {
									    		           		Ext.Msg.alert('提示', response.responseText);
									    			 		}else{
									    						Ext.Msg.alert('提示', '删除失败,失败原因:' + response.responseText);
									    	 				}
														}
													});
											}else{
												delbyidFn(commitData);//删除复核流程提交
											}
									}}]
								     
								},
								{
									title : '重复客户清单编辑',
									hideTitle:true,
									autoLoadSeleted : true,
									type : 'form',
									groups : [
										{
											columnCount : 1 ,
											fields : [
												{name:'CUS_NAME',text:'客户名称',xtype:'textfield',searchField:true,maxLength:80,allowBlank:false}
										      ],
											fn : function(CUS_NAME){
												CUS_NAME.anchor = '95%';
												return [CUS_NAME];
											}},
											{
												columnCount : 2,
								              fields:[
									           
									            {name:'PHONE_AREA_CODE',text:'国际电话区号',gridField:false,maxLength:20,resutlFloat:'left',translateType:'IDD_CODE_NEW_V',allowBlank:false
									            	,listeners:{
											    		blur : function(){
											    			  visibleCallNo();
											    	}}
										          },
												{name:'CALL_NO',text:'手机号码',invalidText:'请输入数字',maxLength :20,allowBlank:false,resutlFloat:'left',
												    	maxLengthText:'最多只能输入20位数字',xtype:'textfield',gridField:false
												    	,listeners:{
												    		blur : function(){
												    			  visibleCallNo();
												    	}}
											    },
										       {name:'SOURCE_CHANNEL',text:'来源渠道',searchField:true,resutlFloat:'right',translateType:'XD000353',gridField:true,allowBlank:false},
										       {name:'MKT_ACTIVITIE',text:'营销活动',gridField:true,maxLength:80,resutlFloat:'right',translateType:'MKT_ACTIVITIE_VAL',gridField:true,multiSelect : true,multiSeparator : ','
											      },
										       //{name : 'MKT_ACTIVITIE',text : '营销活动',xtype : 'lovcombo',store : mktActiStore,forceSelection : true,
									        	//	editable : false,ComboBox : true,mode : 'local',triggerAction : 'all',displayField : 'MKT_ACTI_NAME',valueField : 'MKT_ACTI_CODE',width : 100,anchor : '95%'},
									           // {name:'RECORD_SESSION',text:'沟通话术',xtype:'textarea',gridField:false,maxLength:200},
									            {name:'CERT_TYPE',text:'证件类型',searchField:true,resutlFloat:'right',translateType:'XD000040'},
									            {name:'CERT_CODE',text:'证件号码',xtype:'textfield',resutlFloat:'right',searchField:true,maxLength:40,viewFn:function(v){
									            	if(v!=null&&v!=""&&v!='&nbsp;')
														return "******";
													else 
														return v;
									            }},
									            {name:'CUST_TYPE',text:'客户类型',searchField:true,resutlFloat:'right',translateType:'CUST_TYPE_VALUE',editable : false},
									            {name:'CUS_NATIONALITY',text:'国籍',searchField:true,resutlFloat:'right',translateType:'XD000025',searchField:true,gridField:true},
									           // {name:'REFEREES_ID',text:'推荐人',xtype:'textfield',gridField:false,maxLength:80,hidden:true},
									            {name:'REFEREES_ID',text:'推荐人ID',xtype:'latentRefereesQuery',hiddenName:'CID',singleSelected: true,editable : true,callback : function(a,b) {
													  var REFEREES_ID_P=getCurrentView().contentPanel.form.findField("REFEREES_ID");
													  REFEREES_ID_P.setValue(b[0].data.CID);
												  }},
									            {name:'ATTEN_NAME',text:'联系人',xtype:'textfield',gridField:false,maxLength:80},
									            {name:'CONTMETH_INFO',text:'座机号码',xtype:'textfield',gridField:false,maxLength:20},
									            {name:'ZIPCODE',text:'邮编',xtype:'numberfield',gridField:false,maxLength:50},
									            {name:'JOB_TYPE',text:'职业',translateType:'XD000005'},
									            {name:'INDUST_TYPE',text:'职务',searchField:true,resutlFloat:'right',translateType:'XD000007'},
									            {name:'CUS_EMAIL',text:'E-mail',xtype:'textfield',gridField:true,maxLength:100},
									            {name:'CUS_WECHATID',text:'微信号',xtype:'textfield',gridField:true,maxLength:100},
									            {name:'CUS_ID',text:'临时编号',xtype:'textfield',searchField:false,editable:false,readOnly: true,cls:'x-readOnly'},
									            {name:'STATE',text:'潜在客户状态',translateType:'POTENTAIL_STATE',gridField:true},
									            {name:'CUS_ADDR',text:'通讯地址',xtype:'textarea',gridField:false,maxLength:200}
									           
									            ],          	
												fn : function(PHONE_AREA_CODE,CALL_NO,SOURCE_CHANNEL,CERT_TYPE,CERT_CODE,CUST_TYPE,MKT_ACTIVITIE,CUS_NATIONALITY,REFEREES_ID,ATTEN_NAME,
														CONTMETH_INFO,ZIPCODE,JOB_TYPE,INDUST_TYPE,CUS_EMAIL,CUS_WECHATID,CUS_ID){			
													return [PHONE_AREA_CODE,CALL_NO,SOURCE_CHANNEL,CERT_TYPE,CERT_CODE,CUST_TYPE,MKT_ACTIVITIE,CUS_NATIONALITY,REFEREES_ID,ATTEN_NAME,CONTMETH_INFO,ZIPCODE
													        ,JOB_TYPE,INDUST_TYPE,CUS_EMAIL,CUS_WECHATID,CUS_ID];
												}
											},{
												columnCount : 1 ,
												fields : ['RECORD_SESSION'],
												fn : function(RECORD_SESSION){
													RECORD_SESSION.anchor = '95%';
													return [RECORD_SESSION];
												}},{
												columnCount : 1 ,
												fields : ['CUS_ADDR'],
												fn : function(CUS_ADDR){
													CUS_ADDR.anchor = '95%';
													return [CUS_ADDR];
												}}
									],
									formButtons : [ {
										text : '提交',
										fn : function(formPanel, basicForm) {
											if (!formPanel.getForm().isValid()) {
												Ext.MessageBox.alert('提示', '输入有误,请检查输入项');
												return false;
											};
											var data=formPanel.getForm().getFieldValues();
											var custnamev=data.CUS_NAME;
											var identtypev=data.CERT_TYPE;
											var identnov=data.CERT_CODE;
											var callnov=data.CALL_NO;
											var phoneareacode=data.PHONE_AREA_CODE;
											Ext.Msg.wait('正在进行验证处理，请稍后......', '系统提示');
											if(custnamev!=''&&identtypev!=''&&identnov!=''){
											Ext.Ajax.request({
											  url : basepath+ '/myPotentialCustomer!doParameterCheck.json',
											  method : 'GET',
											  params : {
												  custnamev:custnamev,
												  identtypev:identtypev,
												  identnov:identnov,
												  callnov:phoneareacode+'-#-'+callnov 
												},
											  success : function(response) {
												var ret = Ext.decode(response.responseText);
												var nametype = ret.type;
												var phtype=ret.phtype;
												var custid=ret.custid;
												var source=ret.source;
												var cusname=ret.cusname;
												var custmgr=ret.custmgr;
												var cusorg=ret.cusorg;
												var custmgrv=ret.custmgrv;
												var cusorgv=ret.cusorgv;
												var callnov=ret.callno;
												var callno;
												if(callnov!=null){
													callno=callnov.replace("#-","");
												}
												if (nametype == '1') {
													Ext.MessageBox.alert('提示','客户已是正式客户，将不继续进行新增！');
													return false;
												} else if (nametype == '2') {
													Ext.MessageBox.alert('提示','该潜在客户已存在，将不继续进行新增！');
													return false;
												} else if (nametype == '3') {
													Ext.MessageBox.confirm('提示','该潜在客户证件类型及号码已存在，是否继续！',
															function(buttonId) {
																if (buttonId.toLowerCase() == "no") {
																	if(phtype=='5'){
																		Ext.MessageBox.confirm('提示','该客户可能已经存在 !\n '+cusname+'；<br/>所属机构：'+cusorgv+'；<br/>所属经理：'+custmgrv+'；<br/>是否继续新增？',function(buttonId){
																			if(buttonId.toLowerCase() == "no"){
																				var upactivitie=getCurrentView().contentPanel.getForm().findField('SOURCE_CHANNEL').getValue();
																				var tempactivitie;
																				if(source==""){
																					tempactivitie=upactivitie;
																				}else{
																					tempactivitie=source+","+upactivitie;
																				}
																				updateSourceChannelFn(custid,tempactivitie);
																			}
																		});
																		return false;
																	}else if(phtype=='4'){
																		Ext.MessageBox.confirm('提示','该客户可能已经存在 !\n '+cusname+'；<br/>所属机构：'+cusorgv+'；<br/>所属经理：'+custmgrv+'；<br/>是否继续新增？',
																				function(buttonId) {
																					if (buttonId.toLowerCase() == "yes") {
																						//新增
																						savelaterarginFn(data)
																					}else{
																						var upactivitie=getCurrentView().contentPanel.getForm().findField('SOURCE_CHANNEL').getValue();
																						var tempactivitie;
																						if(source==""){
																							tempactivitie=upactivitie;
																						}else{
																							tempactivitie=source+","+upactivitie;
																						}
																						updateSourceChannelFn(custid,tempactivitie);
																					}
																		});
																	}else{
																		//新增
																		savelaterarginFn(data);
																	}
																}else{
																	//新增
																	savelaterarginFn(data);
																}
															});
												}else{
													if(phtype=='5'){
														Ext.MessageBox.confirm('提示','该客户可能已经存在 !\n '+cusname+'；<br/>所属机构：'+cusorgv+'；<br/>所属经理：'+custmgrv+'；<br/>是否继续新增？',function(buttonId){
															if(buttonId.toLowerCase() == "no"){
																var upactivitie=getCurrentView().contentPanel.getForm().findField('SOURCE_CHANNEL').getValue();
																var tempactivitie;
																if(source==""){
																	tempactivitie=upactivitie;
																}else{
																	tempactivitie=source+","+upactivitie;
																}
																updateSourceChannelFn(custid,tempactivitie);
															}
														});
														return false;
													}else if(phtype=='4'){
														Ext.MessageBox.confirm('提示','该客户可能已经存在 !\n '+cusname+'；<br/>所属机构：'+cusorgv+'；<br/>所属经理：'+custmgrv+'；<br/>是否继续新增？',
																function(buttonId) {
																	if (buttonId.toLowerCase() == "yes") {
																		//新增
																		savelaterarginFn(data)
																	}else{
																		var upactivitie=getCurrentView().contentPanel.getForm().findField('SOURCE_CHANNEL').getValue();
																		var tempactivitie;
																		if(source==""){
																			tempactivitie=upactivitie;
																		}else{
																			tempactivitie=source+","+upactivitie;
																		}
																		updateSourceChannelFn(custid,tempactivitie);
																		return false;
																	}
														});
													}else{
														//新增保存
														savelaterarginFn(data)
													}
												}
												},
												failure : function(response) {
												}
											});
											}else{
												Ext.Ajax.request({
													  url : basepath+ '/myPotentialCustomer!doParameterCheck.json',
													  method : 'GET',
													  params : {
														  callnov:callnov,
														  custnamev:custnamev
														},
													  success : function(response) {
														var ret = Ext.decode(response.responseText);
														var nametype = ret.type;
														var phtype=ret.phtype;
														var custid=ret.custid;
														var source=ret.source;
														var cusname=ret.cusname;
														var custmgr=ret.custmgr;
														var cusorg=ret.cusorg;
														var custmgrv=ret.custmgrv;
														var cusorgv=ret.cusorgv;
														var callnov=ret.callno;
														var callno;
														if(callnov!=null){
															callno=callnov.replace("#-","");
														}
														if (nametype == '1') {
															Ext.MessageBox.alert('提示','客户已是正式客户，将不继续进行新增！');
															return false;
														}
														if(phtype=='5'){
															Ext.MessageBox.confirm('提示','该客户可能已经存在 !\n '+cusname+'；<br/>所属机构：'+cusorgv+'；<br/>所属经理：'+custmgrv+'；<br/>是否继续新增？',function(buttonId){
																if(buttonId.toLowerCase() == "no"){
																	var upactivitie=getCurrentView().contentPanel.getForm().findField('SOURCE_CHANNEL').getValue();
																	var tempactivitie;
																	if(source==""){
																		tempactivitie=upactivitie;
																	}else{
																		tempactivitie=source+","+upactivitie;
																	}
																	updateSourceChannelFn(custid,tempactivitie);
																}
															});
															return false;
														}else if(phtype=='4'){
															Ext.MessageBox.confirm('提示','该客户可能已经存在 !\n '+cusname+'；<br/>所属机构：'+cusorgv+'；<br/>所属经理：'+custmgrv+'；<br/>是否继续新增？',
																	function(buttonId) {
																		if (buttonId.toLowerCase() == "yes") {
																			//新增
																			savelaterarginFn(data)
																		}else{
																			var upactivitie=getCurrentView().contentPanel.getForm().findField('SOURCE_CHANNEL').getValue();
																			var tempactivitie;
																			if(source==""){
																				tempactivitie=upactivitie;
																			}else{
																				tempactivitie=source+","+upactivitie;
																			}
																			updateSourceChannelFn(custid,tempactivitie);
																			return false;
																		}
															});
														}else{
															//新增保存
															savelaterarginFn(data)
														}
														},
														failure : function(response) {}
												
											});}
										}
									},{

										text : '返回',
										fn : function(formPanel, basicForm) {
											 showCustomerViewByTitle("重复客户清单");
										}
									
									}]
							   },{
									title:'已删除潜在客户清单',
									hideTitle:true,
									suspendWidth: 980,
									type:'grid',
									layout:'fit',
									url:basepath+'/latentCusInvalidQueryAction.json',
									autoScroll : true,
									pageable:true,
									fields:{
										fields:[
									            {name:'CUS_NAME',text:'客户名称',xtype:'textfield',allowBlank:false,searchField:true,maxLength:80},
										        {name:'CALL_NO',text:'手机号码',invalidText:'请输入数字',maxLength :20,allowBlank:false,
										            	maxLengthText:'最多只能输入20位数字',xtype:'textfield',renderer:function(value){
										            		return value.replace("#-","")
										            	}
										            },
										        {name : 'MOVER_DATE',text : '分派日期',gridField : true,xtype : 'textarea',maxLength : 20},
										        {name : 'DEFAULT_RECEIVE_DATE',text : '回收截止日期',gridField : true,xtype : 'textarea',maxLength : 20},
										        {name:'SOURCE_CHANNEL',text:'来源渠道',searchField:true,resutlFloat:'right',gridField:true,allowBlank:false,renderer:function(value){
													var val = translateLookupByKey("XD000353",value);
													return val?val:"";
													}},
													{name:'MKT_ACTIVITIE_V',text:'营销活动',xtype:'textfield',gridField:true,maxLength:370},
											        {name:'MKT_ACTIVITIE',text:'营销活动code',hidden:true,gridField:true,maxLength:80,resutlFloat:'right',translateType:'MKT_ACTIVITIE_VAL',gridField:true
											       },
												//{name : 'MKT_ACTIVITIE',text : '营销活动',xtype : 'lovcombo',store : mktActiStore,forceSelection : true,
										      //   editable : false,ComboBox : true,mode : 'local',triggerAction : 'all',displayField : 'MKT_ACTI_NAME',valueField : 'MKT_ACTI_CODE',width : 100,anchor : '95%'},
										        {name:'RECORD_SESSION',text:'沟通话术',xtype:'textarea',gridField:true,maxLength:200},
									            {name:'CUST_MGR',text:'所属客户经理',gridField:true,searchField:false,hidden:true},
									            {name:'MAIN_BR_ID',text:'所属机构',gridField:true,searchField:false,hidden:true},
									            {name:'CUST_MGR_NAME',text:'所属客户经理',gridField:true,searchField:false},
									            {name:'MAIN_BR_NAME',text:'所属机构',gridField:true,searchField:false},
									            {name:'BACK_STATE',text:'分派状态',gridField:true,searchField:true,renderer:function(value){
													var val = translateLookupByKey("BACK_STATE_V",value);
													return val?val:"";
													}},
									            {name : 'MOVER_USER',text : '分派/回收人',gridField : true},
									            {name:'STATE',text:'潜在客户状态',gridField:true,searchField:true,renderer:function(value){
													var val = translateLookupByKey("POTENTAIL_STATE",value);
													return val?val:"";
													}},
									            {name:'CUS_ID',text:'客户号',xtype:'textfield',searchField:true},
									            {name:'CERT_TYPE',text:'证件类型',searchField:true,resutlFloat:'right',renderer:function(value){
													var val = translateLookupByKey("XD000040",value);
													return val?val:"";
													}},
									            {name:'CERT_CODE',text:'证件号码',xtype:'textfield',resutlFloat:'right',searchField:true,maxLength:40,viewFn:function(v){
									            	if(v!=null&&v!=""&&v!='&nbsp;')
														return "******";
													else 
														return v;
									            }},
									            {name:'CUST_TYPE',text:'客户类型',resutlFloat:'right',renderer:function(value){
													var val = translateLookupByKey("XD000080",value);
													return val?val:"";
													}},
									            {name:'CUS_NATIONALITY',text:'国籍',searchField:true,resutlFloat:'right',searchField:true,gridField:true,renderer:function(value){
													var val = translateLookupByKey("XD000025",value);
													return val?val:"";
													}},
									           // {name:'REFEREES_NAME',text:'推荐人',xtype:'textfield',gridField:false,maxLength:80},
									            {name:'REFEREES_ID',text:'推荐人ID',xtype:'latentRefereesQuery',hiddenName:'CID',singleSelected: true,editable : true,callback : function(a,b) {
													  var REFEREES_ID_P=getCurrentView().contentPanel.form.findField("REFEREES_ID");
													  REFEREES_ID_P.setValue(b[0].data.CID);
												  }},
											    {name:'JOB_TYPE',text:'职业',renderer:function(value){
													var val = translateLookupByKey("XD000005",value);
													return val?val:"";
													}},
											    {name:'INDUST_TYPE',text:'职务',renderer:function(value){
															var val = translateLookupByKey("XD000007",value);
															return val?val:"";
															}},
									            {name:'CONTMETH_INFO',text:'座机号码',xtype:'textfield',gridField:false,maxLength:20},
									            {name:'ZIPCODE',text:'邮编',xtype:'numberfield',gridField:false,maxLength:50},
									            {name:'ATTEN_NAME',text:'联系人',xtype:'textfield',gridField:false,maxLength:80},
									            {name:'CUS_ADDR',text:'通讯地址',xtype:'textarea',gridField:false,maxLength:200},
									            {name : 'ACTUAL_RECEIVE_DATE',text : '实际回收日期',gridField : true,xtype : 'textarea',maxLength : 20},
									            {name:'TREE_STORE',text:'所属机构ID',xtype:'textfield',hidden:true,searchField:true},
									            {name:'INPUT_DATE',text:'创建时间',xtype:'textfield',gridField : true},
									            {name:'CUS_EMAIL',text:'E-mail',xtype:'textfield',gridField:true,maxLength:100},
									            {name:'CUS_WECHATID',text:'微信号',xtype:'textfield',gridField:true,maxLength:100},
									            {name:'SUMCOUNTS',text:'已经录入callreport次数',xtype:'textfield',gridField : true},
									            {name:'LASTVISITDATE',text:'最后一次拜访时间',xtype:'textfield',gridField : true},
									            {name:'DELETE_REASON',text:'删除原因',searchField:true,resutlFloat:'right',searchField:true,gridField:true,renderer:function(value){
													var val = translateLookupByKey("LATENT_DELETE_REASON",value);
													return val?val:"";
													}},
												{name:'DELETE_NOTE',text:'删除备注',xtype:'textfield',gridField : true}
									            
									            ]
									},
									gridButtons:[{
										text : '恢复',
										hidden:false,
										fn : function(grid){
											var checkedNodes=getCustomerViewByTitle("已删除潜在客户清单").getGrid().getSelectionModel().getSelections();
											var selectLength=0;
											selectLength = checkedNodes.length;// 得到行数组的长度
											if (selectLength<=0) {
												Ext.Msg.alert('提示', '请选择一条数据！');
												return false;
											} else {
												var selectRecords = getCustomerViewByTitle("已删除潜在客户清单").getGrid().getSelectionModel().getSelections();
												var state = '';
												for ( var i = 0; i < selectRecords.length; i++) {
													state += '' + selectRecords[i].data.STATE + ',';
												}
												var flag = state.indexOf('0,');
												if (state != '' && flag > (-1)) {
													Ext.Msg.alert('提示', '只能恢复无效的潜在客户');
													return false;
												}

												Ext.MessageBox.confirm('提示', '确定恢复吗?', function(buttonId) {
													if (buttonId.toLowerCase() == "no") {
														return false;
													}
													var selectRecords = getCustomerViewByTitle("已删除潜在客户清单").getGrid().getSelectionModel().getSelections();
													var cusId = '';
													for ( var i = 0; i < selectRecords.length; i++) {
														if (i == 0) {
															cusId = "'" + selectRecords[i].data.CUS_ID
																	+ "'";
														} else {
															cusId += "," + "'"
																	+ selectRecords[i].data.CUS_ID + "'";
														}
													}
													Ext.Ajax.request({
														  url : basepath+ '/myPotentialCustomer!huifubeforCheck.json',
														  method : 'GET',
														  params : {
															  cusId : cusId
															},
														  success : function(response) {
															var ret = Ext.decode(response.responseText);
															var nametype = ret.type;
															if (nametype == '1') {
																Ext.MessageBox.alert('提示','所选客户存在已是正式客户，不能恢复！');
																return false;
															}
															Ext.Ajax.request({
																url : basepath
																		+ '/myPotentialCustomer!recoverBackInfo.json',
																params : {
																	cusId : cusId
																},
																success : function() {
																	Ext.Msg.alert('提示', '恢复成功');
																	reloadCurrentData();
																},
																failure : function() {
																	Ext.Msg.alert('提示', '恢复失败');
																	reloadCurrentData();
																}
															});
														  },
															failure : function() {
																reloadCurrentData();
															}
														});
												});
											}
										}	
									}]	
								},{
									title:'被回收客户清单',
									suspendWidth: 950,
									hideTitle : true,
									type : 'gridpanel',
									items : [ disRecyHisSegrid ]
							    },
								//-------------------------------------------------------callreport创建 相关开始---------------------------------------------------------------
								{
									title : 'callreport创建',
									hideTitle:true,
									suspendWidth: 950,
									autoLoadSeleted : true,
									type : 'form',
									groups : [{
											  columnCount : 2,
									          fields:[
													{name: 'CALL_ID',hidden:true},
													{name: 'CUST_ID',text:'客户编号',gridField:true,searchField: true,resutlWidth:100},
													{name: 'CUST_NAME',text:'客户名称',xtype:'textfield',singleSelected:true,searchField:true,allowBlank:false},
													{name: 'CUST_TYPE',text :'客户类型',translateType:'CALLREPORT_CUST_TYPE',searchField: true,resutlWidth:50},
													  {name: 'IDENT_TYPE',text:'证件类型',resutlWidth:100,gridField:false,translateType:'XD000040'},
											  		    {name: 'IDENT_NO',text:'证件编号',resutlWidth:100,gridField:false},
													{name: 'VISIT_DATE',text :'拜访日期',format:'Y-m-d',xtype:'datefield',allowBlank: false,value:new Date()
													//  		    ,
													//  		    	listeners:{
													//  		    		select:function(combo,record){
													//  		    			var v = this.getValue();
													//  		    			if(v.format('Y-m-d')<new Date().format('Y-m-d')){
													//  		    				alert("拜访日期["+v.format('Y-m-d')+"]不能小于当前日期["+new Date().format('Y-m-d')+"]");
													//  		    				getCurrentView().contentPanel.getForm().findField('VISIT_DATE').setValue('');
													//  		    			}
													//  					}
													//		    	}
													},
													{name: 'BEGIN_DATE',text :'起始时间',format:'H:i',xtype:'timefield',minValue:'09:00',maxValue:'17:45',allowBlank: false,value:new Date()},
													{name: 'END_DATE',text :'结束时间',format:'H:i',xtype:'timefield',minValue:'09:00',maxValue:'18:00',allowBlank: false,value:new Date(),
														listeners:{
															select:function(combo,record){
																//结束时间
																var v = this.getValue().split(":");
																var h1 = parseInt(v[0]);
																var s1 = parseInt(v[1]);
																//起始时间
																var o = getCurrentView().contentPanel.getForm().findField('BEGIN_DATE').getValue().split(":");
																var h2 = parseInt(o[0]);
																var s2 = parseInt(o[1]);
																if(h1<h2){
																	alert("结束时间["+this.getValue()+"]不能小于起始时间["+getCurrentView().contentPanel.getForm().findField('BEGIN_DATE').getValue()+"]");
																	getCurrentView().contentPanel.getForm().findField('END_DATE').setValue('');
																}else if(h1==h2){
																	if(s1<s2){
																		alert("结束时间["+this.getValue()+"]不能小于起始时间["+getCurrentView().contentPanel.getForm().findField('BEGIN_DATE').getValue()+"]");
																		getCurrentView().contentPanel.getForm().findField('END_DATE').setValue('');
																	}
																}
															}
														}},
													{name: 'LINK_PHONE',text:'联系电话',resutlWidth:100,maxLength:32},
													{name: 'VISIT_WAY',text:'拜访方式',translateType:'CALLREPORT_VISIT_TYPE',allowBlank: false},
													{name: 'CUST_CHANNEL',text:'客户渠道',resutlWidth:80,searchField:true,resutlFloat:'right',translateType:'XD000353',multiSelect : true,multiSeparator : ',',
														listeners:{
															select:function(combo,record){
																var v = this.getValue();
																if(v=='14'){//MGM
																	getCurrentView().contentPanel.getForm().findField('RECOMMEND_CUST_ID').setVisible(true);
																}else{
																	getCurrentView().contentPanel.getForm().findField('RECOMMEND_CUST_ID').setVisible(false);
																	getCurrentView().contentPanel.getForm().findField('RECOMMEND_CUST_ID').setValue("");
																}
															}
														}
													},
													{name: 'NEXT_VISIT_WAY',text:'下次拜访方式',resutlWidth:60,translateType:'CALLREPORT_VISIT_TYPE'},
													{name: 'NEXT_VISIT_DATE',text:'下次拜访时间',resutlWidth:60,format:'Y-m-d',xtype:'datefield'},
													{name: 'VISIT_CONTENT', text : '访谈结果',translateType:'INTERVIEW_RESULTS',allowBlank: false},
													
													{name:'RECORD_SESSION',text:'沟通话术',xtype:'textarea',gridField:false,maxLength:200},
													{name: 'RECOMMEND_CUST_ID',text : 'MGM推荐客户',hiddenName: 'RECOMMEND_CUST_ID',xtype: 'customerquery',singleSelected: true,resutlFloat:'right',resutlWidth:100},
													{name: 'CREATE_USER',text:'客户经理名称',resutlWidth:60,allowBlank: false,gridField:true},
													{name: 'LAST_UPDATE_USER',text:'最后更新人',resutlWidth:60,hidden:true},
													{name: 'LAST_UPDATE_TM',text:'最后更新时间',gridField:false},
													{name: 'TEST'}
												],          	
												fn : function(CALL_ID,CUST_ID,CUST_NAME,CUST_TYPE,IDENT_TYPE,IDENT_NO,VISIT_DATE,BEGIN_DATE,END_DATE,LINK_PHONE,
														  VISIT_WAY,CUST_CHANNEL,RECOMMEND_CUST_ID,NEXT_VISIT_DATE,NEXT_VISIT_WAY){
													CALL_ID.hidden= true;
													CUST_ID.hidden =true;
													CUST_TYPE.readOnly = true;
													CUST_TYPE.cls = 'x-readOnly';
											//		LINK_PHONE.readOnly = true;
											//		LINK_PHONE.cls = 'x-readOnly';
													CUST_CHANNEL.readOnly = true;
													CUST_CHANNEL.cls = 'x-readOnly';
													LINK_PHONE.readOnly = true;
													LINK_PHONE.cls = 'x-readOnly';
													return [CALL_ID,CUST_ID,CUST_NAME,CUST_TYPE,IDENT_TYPE,IDENT_NO,VISIT_DATE,BEGIN_DATE,END_DATE,LINK_PHONE,
															  VISIT_WAY,CUST_CHANNEL,RECOMMEND_CUST_ID,NEXT_VISIT_DATE,NEXT_VISIT_WAY];
												}
											},{/**内容富文本编辑框**/
											columnCount:1,
											fields : ['VISIT_CONTENT'],
											fn : function(VISIT_CONTENT){
												VISIT_CONTENT.anchor = '95%';
												return [VISIT_CONTENT];
											}
										},{/**内容富文本编辑框**/
											columnCount:1,
											fields : [{name:'MKT_BAK_NOTE',text:'访谈内容',xtype:'textarea',gridField:false,allowBlank: false,maxLength:200}],
											fn : function(MKT_BAK_NOTE){
												MKT_BAK_NOTE.anchor = '95%';
												return [MKT_BAK_NOTE];
											}
										},{/**内容富文本编辑框**/
											columnCount:1,
											fields : ['RECORD_SESSION'],
											fn : function(RECORD_SESSION){
												RECORD_SESSION.anchor = '95%';
												RECORD_SESSION.readOnly = true;
												RECORD_SESSION.cls = 'x-readOnly';
												return [RECORD_SESSION];
											}
										},{
											columnCount:1,
											fields : ['TEST'],
											fn : function(TEST){
												return [createPanel_SJ];//商机信息
											}
										},{
											columnCount:1,
											fields : ['TEST'],
											fn : function(TEST){
												return [createPanel_DQ];//到期通知信息
											}
										},{
											columnCount:1,
											fields : ['TEST'],
											fn : function(TEST){
												return [createPanel_LS];//大额流失
											}
										},{
											columnCount:1,
											fields : ['TEST'],
											fn : function(TEST){
												return [createPanel_SAN];//近三次联系内容createPanel_SAN
											}
										}
									],
									formButtons : [  {
										text : '保存',
										fn : function(formPanel, basicForm) {
											if (!formPanel.getForm().isValid()) {
												Ext.MessageBox.alert('提示', '输入有误,请检查输入项');
												return false;
											};
											var commitData = translateDataKey(basicForm.getFieldValues(),_app.VIEWCOMMITTRANS);
											Ext.Ajax.request({
												url : basepath + '/ocrmFSeCallreport.json',
												method : 'POST',
											    params:commitData ,
												waitMsg : '正在保存数据,请等待...',
												success : function(response) {
												    Ext.Msg.alert('提示', '提交成功！');
												    Ext.Ajax.request({
														url : basepath+'/session-info!getPid.json',
														method : 'GET',
														success : function(a,b,v) {//返回id值，显示tbar
														    var noticeIdStr = Ext.decode(a.responseText).pid;
														    getCurrentView().contentPanel.getForm().findField('CALL_ID').setValue(noticeIdStr);
														    callIds = noticeIdStr;
														}
													});
													//激活新增商机按钮
													Ext.getCmp("addSJ").show();
//													Ext.getCmp("addLS").show();
													//加载近三次联系内容
													var  custId = getCurrentView().contentPanel.getForm().findField('CUST_ID').getValue();
													//var  visitDate = getCurrentView().contentPanel.getForm().findField('VISIT_DATE').getValue();
													createStore_asn.load({params:{callReport_custId:custId}});
												},
												failure : function(response) {
													Ext.Msg.alert('提示', '提交失败！');
													reloadCurrentData();
												}
											});
										}
									},{
										text : '关闭',
										fn : function(formPanel) {
											reloadCurrentData();
											hideCurrentView();
												}
											}]
									   
								},{
									title : '新增商机',
									hideTitle : true,
									type : 'form',
									autoLoadSeleted : true,
									frame : true,
									groups : [{
									columnCount : 2 ,
									fields : [	
									            {name : 'ID',hidden : true},
									            {name : 'CALL_ID',hidden : true},
									            {name : 'BUSI_NAME',text  : '商机名称',hidden : false},
									            {name : 'PRODUCT_ID',text  : '产品编号',translateType:'CALLREPORT_PRODUCT_NAME',allowBlank: false},
//									            {name : 'PRODUCT_NAME',text  : '产品编码',dataType:'productChoose',hiddenName:'PRODUCT_ID',searchField: true,singleSelect:true,allowBlank: false},
												{name : 'SALES_STAGE',text  : '销售阶段',hidden : false,translateType:'CALLREPORT_SAVES_STAGE',allowBlank: false,
													listeners:{
											    		select:function(combo,record){
											    			var v = this.getValue();
											    			if(v=='6'){//6失败
											    				getCurrentView().contentPanel.getForm().findField('FAIL_REASON').setVisible(true);
											    				getCurrentView().contentPanel.getForm().findField('FAIL_REASON').allowBlank = false;
											    				getCurrentView().contentPanel.getForm().findField('REMARK').setVisible(true);
											    			}else{
											    				getCurrentView().contentPanel.getForm().findField('FAIL_REASON').setVisible(false);
											    				getCurrentView().contentPanel.getForm().findField('FAIL_REASON').allowBlank = true;
											    				getCurrentView().contentPanel.getForm().findField('REMARK').setVisible(false);
											    			}
														}
									        	}},
												{name : 'AMOUNT',text  : '金额(元)',hidden : false,xtype:'numberfield',allowBlank: false},
												{name : 'ESTIMATED_TIME',text  : '预计成交时间',hidden : false,xtype:'datefield',allowBlank: false}
												],
											fn : function(ID,CALL_ID,PRODUCT_ID,SALES_STAGE,AMOUNT,ESTIMATED_TIME,FAIL_REASON,REMARK,BUSI_NAME){
												return [ID,CALL_ID,PRODUCT_ID,SALES_STAGE,AMOUNT,ESTIMATED_TIME,FAIL_REASON,REMARK,BUSI_NAME];
											}
									},{
										columnCount :1 ,
										fields : [{name : 'FAIL_REASON',text  : '失败原因',hidden : true,translateType:'CALLREPORT_FAIL_REASON'},
												{name : 'REMARK',text  : '备注',hidden : true,maxLength:500,xtype:'textarea'}],
										fn : function(FAIL_REASON,REMARK){
											return [FAIL_REASON,REMARK];
										}
								   }],
								formButtons:[{
									text : '保存',
									//保存数据					 
									fn : function(formPanel,baseform){						
										if(!baseform.isValid())
											{
												Ext.Msg.alert('提示','请输入完整！');
												return false;
											}
										    var commintData = translateDataKey(baseform.getFieldValues(),_app.VIEWCOMMITTRANS);
											Ext.Msg.wait('正在提交数据，请稍等...','提示');
										    Ext.Ajax.request({
								 				url : basepath + '/ocrmFSeCallreportBusi.json?callIds='+callIds,
								 				method : 'POST',
								 				params : commintData,
												success : function() {
													Ext.Msg.alert('提示',"操作成功!");
													showCustomerViewByTitle("callreport创建");
													//加载商机面板
													createStore_sj.load({params:{callId:callIds}});
													//回写新增面板
													Ext.Ajax.request({
										 				url : basepath + '/ocrmFSeCallreport.json?callIds='+callIds,
										 				method : 'GET',
														success : function(response) {
															var resultArray = Ext.util.JSON.decode(response.responseText)
															getCreateView().contentPanel.getForm().findField('CALL_ID').setValue(resultArray.json.data[0].CALL_ID);
															getCreateView().contentPanel.getForm().findField('CUST_ID').setValue(resultArray.json.data[0].CUST_ID);
															getCreateView().contentPanel.getForm().findField('CUST_NAME').setValue(resultArray.json.data[0].CUST_NAME);
															getCreateView().contentPanel.getForm().findField('CUST_TYPE').setValue(resultArray.json.data[0].CUST_TYPE);
															getCreateView().contentPanel.getForm().findField('VISIT_DATE').setValue(resultArray.json.data[0].VISIT_DATE);
															getCreateView().contentPanel.getForm().findField('BEGIN_DATE').setValue(resultArray.json.data[0].BEGIN_DATE);
															getCreateView().contentPanel.getForm().findField('END_DATE').setValue(resultArray.json.data[0].END_DATE);
															getCreateView().contentPanel.getForm().findField('LINK_PHONE').setValue(resultArray.json.data[0].LINK_PHONE);
															getCreateView().contentPanel.getForm().findField('VISIT_WAY').setValue(resultArray.json.data[0].VISIT_WAY);
															getCreateView().contentPanel.getForm().findField('CUST_CHANNEL').setValue(resultArray.json.data[0].CUST_CHANNEL);
															getCreateView().contentPanel.getForm().findField('RECOMMEND_CUST_ID').setValue(resultArray.json.data[0].RECOMMEND_CUST_ID);
															getCreateView().contentPanel.getForm().findField('VISIT_CONTENT').setValue(resultArray.json.data[0].VISIT_CONTENT);
															getCreateView().contentPanel.getForm().findField('NEXT_VISIT_DATE').setValue(resultArray.json.data[0].NEXT_VISIT_DATE);
															getCreateView().contentPanel.getForm().findField('NEXT_VISIT_WAY').setValue(resultArray.json.data[0].NEXT_VISIT_WAY);
														}
													});
													if(cID.CUST_ID != undefined){
														Ext.getCmp("addSJ").show();
//														Ext.getCmp("addLS").show();
														createStore_ls.load({params:{callReport_custId:cID.CUST_ID}});
													}
												},
												failure : function(response) {
													var resultArray = Ext.util.JSON.decode(response.status);
											 		if(resultArray == 403) {
										           		Ext.Msg.alert('提示', response.responseText);
											 		}else{
														Ext.Msg.alert('提示', '操作失败,失败原因:' + response.responseText);
									 				}
												}
											});
										}
								}]
								},{
									title : '新增大额流失',
									hideTitle : true,
									type : 'form',
									autoLoadSeleted : false,
									frame : true,
									groups : [{
										columnCount : 2 ,
										fields : [	
										            {name : 'ID',hidden : false},
										            {name : 'CALL_ID',hidden : false},
										            {name : 'CUST_ID',hidden : false},
										            {name : 'REMIND_DATE',text  : '汇款时间',hidden : false,xtype:'datefield'},
										            {name : 'REMIND_AMOUNT',text  : '汇款金额(元)',renderer:money('0,000.00'),allowBlank: false},
													{name : 'RECEIVER',text  : '收款人',hidden : false,allowBlank: false},
													{name : 'RECEIVE_BANK',text  : '收款行',hidden : false,allowBlank: false},
													{name : 'BACKFLOW_DATE',text  : '预计回流时间',hidden : false,xtype:'datefield'},
													{name : 'BACKFLOW_AMOUNT',text  : '预计回流金额(元)',hidden : false,renderer:money('0,000.00')},
													{name : 'REMIND_CASE_STAGE',text  : '是否结案',hidden : false,translateType:'CALLREPORT_CASE_STAGE',allowBlank: false}
													],
												fn : function(ID,CUST_ID,CALL_ID,REMIND_DATE,REMIND_AMOUNT,RECEIVER,RECEIVE_BANK,BACKFLOW_DATE,BACKFLOW_AMOUNT,REMIND_CASE_STAGE){
													return [ID,CUST_ID,CALL_ID,REMIND_DATE,REMIND_AMOUNT,RECEIVER,RECEIVE_BANK,BACKFLOW_DATE,BACKFLOW_AMOUNT,REMIND_CASE_STAGE];
												}
										},{
											columnCount :0.95 ,
											fields : [{name : 'REMIND_REASON',text  : '汇款原因',hidden : true,translateType:'CALLREPORT_REMIND_REASON',allowBlank: false,
												listeners:{
										    		select:function(combo,record){
										    			var v = this.getValue();
										    			if(v=='5'){//5其他原因
										    				getCurrentView().contentPanel.getForm().findField('REMIND_REMARK').setVisible(true);
										    			}else{
										    				getCurrentView().contentPanel.getForm().findField('REMIND_REMARK').setVisible(false);
										    			}
													}
									    	}}],
											fn : function(REMIND_REASON){
												return [REMIND_REASON];
											}
									   },{
											columnCount :0.95 ,
											fields : [{name : 'REMIND_REMARK',text  : '备注',hidden : true,xtype:'textarea'}],
											fn : function(REMIND_REMARK){
												return [REMIND_REMARK];
											}
									   }],
								formButtons:[{
									text : '保存',
									//保存数据					 
									fn : function(formPanel,baseform){						
										if(!baseform.isValid())
											{
												Ext.Msg.alert('提示','请输入完整！');
												return false;
											}
										    var commintData = translateDataKey(baseform.getFieldValues(),_app.VIEWCOMMITTRANS);
											Ext.Msg.wait('正在提交数据，请稍等...','提示');
										    Ext.Ajax.request({
								 				url : basepath + '/ocrmFSeCallreportRemind.json?callIds='+callIds,
								 				method : 'POST',
								 				params : commintData,
												success : function() {
													Ext.Msg.alert('提示',"操作成功!");
													showCustomerViewByTitle("callreport创建");
													//回写新增面板
													Ext.Ajax.request({
										 				url : basepath + '/ocrmFSeCallreport.json?callIds='+callIds,
										 				method : 'GET',
														success : function(response) {
															var resultArray = Ext.util.JSON.decode(response.responseText)
															getCreateView().contentPanel.getForm().findField('CALL_ID').setValue(resultArray.json.data[0].CALL_ID);
															getCreateView().contentPanel.getForm().findField('CUST_ID').setValue(resultArray.json.data[0].CUST_ID);
															getCreateView().contentPanel.getForm().findField('CUST_NAME').setValue(resultArray.json.data[0].CUST_NAME);
															getCreateView().contentPanel.getForm().findField('CUST_TYPE').setValue(resultArray.json.data[0].CUST_TYPE);
															getCreateView().contentPanel.getForm().findField('VISIT_DATE').setValue(resultArray.json.data[0].VISIT_DATE);
															getCreateView().contentPanel.getForm().findField('BEGIN_DATE').setValue(resultArray.json.data[0].BEGIN_DATE);
															getCreateView().contentPanel.getForm().findField('END_DATE').setValue(resultArray.json.data[0].END_DATE);
															getCreateView().contentPanel.getForm().findField('LINK_PHONE').setValue(resultArray.json.data[0].LINK_PHONE);
															getCreateView().contentPanel.getForm().findField('VISIT_WAY').setValue(resultArray.json.data[0].VISIT_WAY);
															getCreateView().contentPanel.getForm().findField('CUST_CHANNEL').setValue(resultArray.json.data[0].CUST_CHANNEL);
															getCreateView().contentPanel.getForm().findField('RECOMMEND_CUST_ID').setValue(resultArray.json.data[0].RECOMMEND_CUST_ID);
															getCreateView().contentPanel.getForm().findField('VISIT_CONTENT').setValue(resultArray.json.data[0].VISIT_CONTENT);
															getCreateView().contentPanel.getForm().findField('NEXT_VISIT_DATE').setValue(resultArray.json.data[0].NEXT_VISIT_DATE);
															getCreateView().contentPanel.getForm().findField('NEXT_VISIT_WAY').setValue(resultArray.json.data[0].NEXT_VISIT_WAY);
														}
													});
													if(cID.CUST_ID != undefined){
														Ext.getCmp("addSJ").show();
//														Ext.getCmp("addLS").show();
														createStore_ls.load({params:{callReport_custId:cID.CUST_ID}});
													}
												},
												failure : function(response) {
													var resultArray = Ext.util.JSON.decode(response.status);
											 		if(resultArray == 403) {
										           		Ext.Msg.alert('提示', response.responseText);
											 		}else{
														Ext.Msg.alert('提示', '操作失败,失败原因:' + response.responseText);latentSourceChannelQueryAction
									 				}
												}
											});
										}
								}]
								},{
									title : '联系内容详情',
									hideTitle : true,
									type : 'form',
									suspendWidth: 900,
									autoLoadSeleted : true,
									frame : true,
									groups : [{
									columnCount : 2 ,
									fields : [	
									          {name: 'CUST_ID',text:'客户编号',gridField:true,readOnly:true,cls:'x-readOnly'},
									  		  {name: 'CUST_TYPE',text :'客户类型',translateType:'CALLREPORT_CUST_TYPE',readOnly:true,cls:'x-readOnly'},
										      {name: 'CUST_NAME',text:'客户名称',readOnly:true,cls:'x-readOnly'},
										      {name: 'LINK_PHONE',text:'联系电话',resutlWidth:100,readOnly:true,cls:'x-readOnly'},
										      {name: 'CUST_CHANNEL',text:'客户渠道',resutlWidth:80,translateType:'CALLREPORT_CUST_CHANNEL',readOnly:true,cls:'x-readOnly'},
									  		  {name: 'VISIT_DATE',text :'拜访日期',format:'Y-m-d',xtype:'datefield',readOnly:true,cls:'x-readOnly'},
									  		  {name: 'BEGIN_DATE',text :'起始时间',format:'H:i',xtype:'timefield',minValue:'09:00',maxValue:'17:45',readOnly:true,cls:'x-readOnly'},
									  		  {name: 'END_DATE',text :'结束时间',format:'H:i',xtype:'timefield',minValue:'09:00',maxValue:'18:00',readOnly:true,cls:'x-readOnly'},
									  		  {name: 'VISIT_WAY',text:'拜访方式',translateType:'CALLREPORT_VISIT_TYPE',readOnly:true,cls:'x-readOnly'},
									  		  {name: 'RECOMMEND_CUST_ID',text : 'MGM推荐客户',readOnly:true,cls:'x-readOnly'},
									  		  {name: 'NEXT_VISIT_DATE',text:'下次拜访时间',resutlWidth:60,format:'Y-m-d',xtype:'datefield',readOnly:true,cls:'x-readOnly'},
									  		  {name: 'NEXT_VISIT_WAY',text:'下次拜访方式',resutlWidth:60,translateType:'CALLREPORT_VISIT_TYPE',readOnly:true,cls:'x-readOnly'}
									  		    
												],
											fn : function(CUST_ID,CUST_TYPE,CUST_NAME,LINK_PHONE,CUST_CHANNEL,VISIT_DATE,BEGIN_DATE,END_DATE,VISIT_WAY,RECOMMEND_CUST_ID,
													NEXT_VISIT_DATE,NEXT_VISIT_WAY){
												return [CUST_ID,CUST_TYPE,CUST_NAME,LINK_PHONE,CUST_CHANNEL,VISIT_DATE,BEGIN_DATE,END_DATE,VISIT_WAY,RECOMMEND_CUST_ID,
														NEXT_VISIT_DATE,NEXT_VISIT_WAY];
											}
									},{
										columnCount : 0.95,
										fields : [{name:'VISIT_CONTENT',text:'访谈结果',translateType:'INTERVIEW_RESULTS',readOnly:true,cls:'x-readOnly',xtype:'textarea'}],
												fn : function(VISIT_CONTENT){
													return [VISIT_CONTENT];
												}
									},{
										columnCount:0.945,
										fields : ['TEST'],
										fn : function(TEST){
											return [detailPanel_SJ];//商机信息
										}
									},{
										columnCount:0.945,
										fields : ['TEST'],
										fn : function(TEST){
											return [detailPanel_DQ];//到期通知信息
										}
									},{
										columnCount:0.945,
										fields : ['TEST'],
										fn : function(TEST){
											return [detailPanel_LS];//大额流失
										}
									}],
								formButtons:[{
									text : '返回',
									fn : function(grid){
										showCustomerViewByTitle("callreport创建");
									}
								}]
								},
								//-------------------------------------------------------callreport创建 相关------结束---------------------------------------------------------------
								/*{
									title:'来源渠道维护',
									suspendWidth: 950,
									hideTitle : true,
									type : 'gridpanel',
									items : [ sourceChannelgrid ]
							   },{
									title : '来源渠道编辑',
									hideTitle:true,
									autoLoadSeleted : true,
									type : 'form',
									groups : [
											{
												columnCount : 1 ,
												fields : [
													{name: 'ID',text : '属性ID',xtype:'textfield'},
													{name: 'LOOKUP',xtype:'textfield',text:'字典类别', searchField: true, allowBlank:false,resutlWidth:150},
													  
												  ],
												fn : function(ID,LOOKUP){
													ID.hidden = true;
													LOOKUP.hidden = true;
													return [ID,LOOKUP];
												}
											},
										{
												columnCount :3,
											fields : [
												
												{name: 'CODE',text:'活动开始日期',resutlWidth:60,format:'Ymd',xtype:'datefield',allowBlank: false, listeners : {
							          				"select" : function() {
							          					var OrgId=getCurrentView().contentPanel.getForm().findField('CODE_F').getValue();
							          					if(OrgId!=''&&OrgId!=null&&OrgId!=undefined){
							          						getlargestNumberByOrder(OrgId);
							          					}
							          					
													}
							          			}},
												//{name:'ORG_ID',text:'-',searchField:true,resutlWidth:120,xtype:'orgchoose',hiddenName:'ORG_ID',searchType:'SUBTREE',allowBlank: false},
												{name:'CODE_F_NAME',text:'所属分支行',hiddenName:'CODE_F',allowBlank:false,xtype:'orgchooseSource',singleSelected:true},
												{name: 'LARGEST_NUMBER',text : '活动场次',xtype:'textfield'},
												{name:'CODE_F',text:'-',allowBlank:false,hidden:true}
												
											  ],
											fn : function(CODE,CODE_F_NAME,LARGEST_NUMBER,CODE_F){
												CODE_F.hidden = true;
												
												CODE.anchor = '97%';
												CODE_F_NAME.anchor = '97%';
												LARGEST_NUMBER.readOnly = true;
												LARGEST_NUMBER.cls = 'x-readOnly';
												return [CODE,CODE_F_NAME,LARGEST_NUMBER,CODE_F];
											}
										},
										{
											columnCount : 1 ,
											fields : [
                                                {name: 'LARGEST_NUMBER_V',text:'活动编码',xtype:'textfield',  resutlWidth:350},
												{name: 'VALUE',text : '活动名称', xtype:'textfield',searchField: true,allowBlank:false,resutlWidth:200},  
												{name: 'COMMENT',text:'活动说明',xtype:'textfield',  resutlWidth:350}     
											  ],
											fn : function(LARGEST_NUMBER_V,VALUE,COMMENT){
												LARGEST_NUMBER_V.readOnly = true;
												LARGEST_NUMBER_V.cls = 'x-readOnly';
												LARGEST_NUMBER_V.anchor = '97%';
												VALUE.anchor = '97%';
												COMMENT.anchor = '97%';
												return [LARGEST_NUMBER_V,VALUE,COMMENT];
											}
										}
									],
									formButtons : [ {
										text : '提交',
										fn : function(formPanel, basicForm) {
											if (!formPanel.getForm().isValid()) {
												Ext.MessageBox.alert('提示', '输入有误,请检查输入项');
												return false;
											};
											var commitData = translateDataKey(basicForm.getFieldValues(),_app.VIEWCOMMITTRANS);
											var roleStr=__roleCodes.toString();
											if(roleStr.indexOf("R125$")>=0){
												Ext.Ajax.request({
													url : basepath + '/latentSourceChannelQueryAction!saveData.json',
													method : 'POST',
													params:commitData ,
													waitMsg : '正在保存数据,请等待...',
													success : function(response) {
														Ext.Msg.alert('提示', '提交成功！');
														showCustomerViewByTitle("来源渠道维护");
													},
													failure : function(response) {
														Ext.Msg.alert('提示', '提交失败！');
														showCustomerViewByTitle("来源渠道维护");
													}
												});
											}else{
											//提交保存审批流程
											sourcesSubmitbyidFn(commitData);
											}
											
										}
									},{
										text : '返回',
										fn : function(formPanel, basicForm) {
											  showCustomerViewByTitle("来源渠道维护");
										}
									}]
								},{
									title : '来源渠道详情',
									hideTitle:true,
									autoLoadSeleted : true,
									type : 'form',
									groups : [
											{
												columnCount : 1 ,
												fields : [
													{name: 'ID',text : '属性ID',xtype:'textfield'},
													{name: 'LOOKUP',xtype:'textfield',text:'字典类别', searchField: true, allowBlank:false,resutlWidth:150},
													  
												  ],
												fn : function(ID,LOOKUP){
													ID.hidden = true;
													LOOKUP.hidden = true;
													return [ID,LOOKUP];
												}
											},
										{
												columnCount :3,
											fields : [
												
												{name: 'CODE',text:'活动开始日期',resutlWidth:60,format:'Ymd',xtype:'datefield',allowBlank: false, listeners : {
							          				"select" : function() {
							          					var OrgId=getCurrentView().contentPanel.getForm().findField('CODE_F').getValue();
							          					if(OrgId!=''&&OrgId!=null&&OrgId!=undefined){
							          						getlargestNumberByOrder(OrgId);
							          					}
							          					
													}
							          			}},
												{name:'CODE_F_NAME',text:'所属分支行',hiddenName:'CODE_F',allowBlank:false,xtype:'orgchooseSource',singleSelected:true},
												{name: 'LARGEST_NUMBER',text : '活动场次',xtype:'textfield'},
												{name:'CODE_F',text:'-',allowBlank:false,hidden:true}
												
											  ],
											fn : function(CODE,CODE_F_NAME,LARGEST_NUMBER,CODE_F){
												CODE_F.hidden = true;
												CODE.readOnly = true;
												CODE.cls = 'x-readOnly';
												CODE_F_NAME.readOnly = true;
												CODE_F_NAME.cls = 'x-readOnly';
												LARGEST_NUMBER.readOnly = true;
												LARGEST_NUMBER.cls = 'x-readOnly';
												return [CODE,CODE_F_NAME,LARGEST_NUMBER,CODE_F];
											}
										},
										{
											columnCount : 1 ,
											fields : [
                                                {name: 'LARGEST_NUMBER_V',text:'活动编码',xtype:'textfield',  resutlWidth:350},
												{name: 'VALUE',text : '活动名称', xtype:'textfield',searchField: true,allowBlank:false,resutlWidth:200},  
												{name: 'COMMENT',text:'活动说明',xtype:'textfield',  resutlWidth:350}     
											  ],
											fn : function(LARGEST_NUMBER_V,VALUE,COMMENT){
												LARGEST_NUMBER_V.readOnly = true;
												LARGEST_NUMBER_V.cls = 'x-readOnly';
												VALUE.readOnly = true;
												VALUE.cls = 'x-readOnly';
												COMMENT.readOnly = true;
												COMMENT.cls = 'x-readOnly';
												LARGEST_NUMBER_V.anchor = '97%';
												VALUE.anchor = '97%';
												COMMENT.anchor = '97%';
												return [LARGEST_NUMBER_V,VALUE,COMMENT];
											}
										}
									],
									formButtons : [{
										text : '返回',
										fn : function(formPanel, basicForm) {
											  showCustomerViewByTitle("来源渠道维护");
										}
									}]
								}*/
															   
		        ];
		var firstLayout = -1;//用于控制在自定义grid上添加查询form
		var seleRecod='';
		var model='';
		var gridStore='';
		var beforeviewshow = function(view){
			if(view._defaultTitle == '修改'|| view._defaultTitle == '详情'){
				if(getSelectedData() == false|| getAllSelects().length > 1){
					Ext.Msg.alert('提示','请选择一条数据');
					return false;
				}
			}
			
			if(view._defaultTitle =='重复客户清单'){
				againCreateStore.load({
	    			params:{
	    				start:0,
	    				limit: parseInt(againCreatepagesize_combo.getValue())
	    			}
			})}
				if(view._defaultTitle == '修改'){
					var creatdatestr = new Date(getSelectedData().data.INPUT_DATE);
					var datethis_ = new Date().getTime();
					var spanDays=((datethis_-creatdatestr)/(1000*60*60*24)).toFixed(2);
					if(roleCondedit=='1'&&spanDays>3){
						view.contentPanel.getForm().findField("CALL_NO").addClass("x-readOnly");
						view.contentPanel.getForm().findField("CALL_NO").setReadOnly(true);
						view.contentPanel.getForm().findField("SOURCE_CHANNEL").addClass("x-readOnly");
						view.contentPanel.getForm().findField("SOURCE_CHANNEL").setReadOnly(true);
					}
				}
				if(view._defaultTitle == '客户回收'){
					hstransForm.getForm().findField("custMgrName1_value").setValue(__userName);
					hstransForm.getForm().findField("custMgrId1").setValue(__userId);
					hstransForm.getForm().findField("orgId1").setValue(__units);
				}
				if(view._defaultTitle =='已删除潜在客户清单'){
					view.setParameters(); 
				};
			
			if(view._defaultTitle =='修改历史'){
				if (!getSelectedData()) {
					Ext.Msg.alert('提示', '请选择一条数据进行操作！');
					return false;
				}
				submit_id=getSelectedData().data.CUS_ID;
				editHisInfoStore.load({
	    			params:{
	    				'cusId':submit_id,
	    				start:0,
	    				limit: parseInt(editHispagesize_combo.getValue())
	    			}});
				disRecyHisInfoStore.load({
	    			params:{
	    				'cusId':submit_id,
	    				start:0,
	    				limit: parseInt(disRecyHispagesize_combo.getValue())
	    			}
	    		});
			};
			
			 if (view._defaultTitle == 'callreport创建') {
				
				 if(getSelectedData() == false|| getAllSelects().length > 1){
						Ext.Msg.alert('提示','请选择一条数据');
						return false;
					}
				 view.contentPanel.getForm().findField("CUST_TYPE").setValue(getSelectedData().data.STATE);
				//隐藏商机按钮
					Ext.getCmp("addSJ").hide();
					createStore_sj.removeAll();
					//隐藏大额流失
//					Ext.getCmp("addLS").hide();
					createStore_ls.removeAll();
					createStore_asn.removeAll();
					var  custId = getSelectedData().data.CUS_ID;
					createStore_asn.load({params:{callReport_custId:custId}});
				}
			 
			/* if(view._defaultTitle =='来源渠道维护'){
				 sourceChannelStore.load({
						params:{
							start:0,
							limit: parseInt(sourceChannelsize_combo.getValue())
						}
					});
			   }*/
			 if(view._defaultTitle =='被回收客户清单'){
					disRecyHisSeInfoStore.load({
		    			params:{
		    				'stateval':2,
		    				start:0,
		    				limit: parseInt(disRecyHisSepagesize_combo.getValue())
		    			}
		    		});
			   }
		};
		
		var viewshow = function(view){
			if(view._defaultTitle == '新增'){
				view.contentPanel.getForm().findField('CUST_TYPE').setValue("2");
			}
			if(view._defaultTitle == '修改'|| view._defaultTitle == '详情'||view._defaultTitle =='重复客户清单编辑 '){
				var subcallno=getSelectedData().data.CALL_NO;
	    		var newstrcallno=subcallno.split('-');
       		    view.contentPanel.getForm().findField("CALL_NO").setValue(newstrcallno[2]);
	     		view.contentPanel.getForm().findField("PHONE_AREA_CODE").setValue(newstrcallno[0]);
				
			}
			 if (view._defaultTitle == '客户分派') {
				transForm1.getForm().findField('custMgrName').setVisible(false);
				transForm1.getForm().findField('orgName').setVisible(false);
			}
			 

			 if (view._defaultTitle == 'callreport创建') {
					
				 view.contentPanel.getForm().findField("CUST_NAME").setValue(getSelectedData().data.CUS_NAME);
				 view.contentPanel.getForm().findField("CUST_ID").setValue(getSelectedData().data.CUS_ID);
				 view.contentPanel.getForm().findField("IDENT_TYPE").setValue(getSelectedData().data.CERT_TYPE);
				 view.contentPanel.getForm().findField("IDENT_NO").setValue(getSelectedData().data.CERT_CODE);
				 view.contentPanel.getForm().findField("RECORD_SESSION").setValue(getSelectedData().data.RECORD_SESSION);
				 var linkPhone = getSelectedData().data.CALL_NO.replace("#-","");
				 view.contentPanel.getForm().findField("LINK_PHONE").setValue(linkPhone);
				 cID.CUST_ID =getSelectedData().data.CUS_ID;
				// view.contentPanel.getForm().findField("CUST_TYPE").setValue(getSelectedData().data.STATE);
				 view.contentPanel.getForm().findField("CUST_CHANNEL").setValue(getSelectedData().data.SOURCE_CHANNEL);
				 if(getSelectedData().data.SOURCE_CHANNEL=='14'){//MGM
	    				getCurrentView().contentPanel.getForm().findField('RECOMMEND_CUST_ID').setVisible(true);
	    			}else{
	    				getCurrentView().contentPanel.getForm().findField('RECOMMEND_CUST_ID').setVisible(false);
	    				getCurrentView().contentPanel.getForm().findField('RECOMMEND_CUST_ID').setValue("");
	    			}
				//隐藏商机按钮
					Ext.getCmp("addSJ").hide();
					createStore_sj.removeAll();
					//隐藏大额流失
//					Ext.getCmp("addLS").hide();
					createStore_ls.removeAll();
					createStore_asn.removeAll();
					
					
				}
			 if(view._defaultTitle=='新增商机'){
					view.contentPanel.getForm().findField('FAIL_REASON').setVisible(false);
					view.contentPanel.getForm().findField('REMARK').setVisible(false);
				}
				
			 if(view._defaultTitle=='新增大额流失'){
					view.contentPanel.getForm().findField('CUST_ID').setValue(cID.CUST_ID);
				}
			 
			 /*if(view._defaultTitle == '来源渠道编辑'){
				 view.contentPanel.getForm().findField('LOOKUP').setValue("XD000353");
				}*/
			
		};

		/*************导入窗口定义模块****************/
		var _tempImpFileName = "";
		var pkHead = "";
		/**
		 * 导入表单对象，此对象为全局对象，页面直接调用。
		 */
		var importForm = new Ext.FormPanel({ 
		    height : 200,
		    width : '100%',
		    title:'文件导入',
		    fileUpload : true, 
		    dataName:'file',
		    frame:true,
		    tradecode:"",
		    
		    /**是否显示导入状态*/
		    importStateInfo : '',
		    importStateMsg : function (state){
				var titleArray = ['excel数据转化为SQL脚本','执行SQL脚本...','正在将临时表数据导入到业务表中...','导入成功！'];
				this.importStateInfo = "当前状态为：[<font color='red'>"+titleArray[state]+"</font>];<br>";
			},    
		    /**是否显示 当前excel数据转化为SQL脚本成功记录数*/
		    curRecordNumInfo : '',
		    curRecordNumMsg : function(o){
				this.curRecordNumInfo = "当前excel数据转化为SQL脚本成功记录数[<font color='red'>"+o+"</font>];<br>";
			},
		    /**是否显示 当前sheet页签记录数*/
			curSheetRecordNumInfo : '',
		    curSheetRecordNumMsg : function (o) {
				this.curSheetRecordNumInfo = "当前sheet页签记录数：[<font color='red'>"+o+"</font>];<br>";
			},
		    /**是否显示 当前sheet页签号*/
		    sheetNumInfo : '',
		    sheetNumMsg : function(o){
				this.sheetNumInfo = "当前sheet页签号为：[<font color='red'>"+o+"</font>];<br>";
			},
		    /**是否显示 sheet页签总数*/
		    totalSheetNumInfo : '',
		    totalSheetNumMsg : function(o){
				this.totalSheetNumInfo = "sheet页签总数：[<font color='red'>"+o+"</font>];<br>";
			},
		    /**是否显示 已导入完成sheet数*/
		    finishSheetNumInfo : '',
		    finishSheetNumMsg : function(o){
				this.finishSheetNumInfo = "已导入完成sheet数[<font color='red'>"+o+"</font>];<br>";
			},
		    /**是否显示 已导入完成记录数*/
			finishRecordNumInfo : '',
		    finishRecordNumMsg : function(o){
				this.finishRecordNumInfo = "已导入完成记录数[<font color='red'>"+o+"</font>];<br>";
			},
		    /**当前excel数据转化为SQL脚本成功记录数*/
		    curRecordNum : 0,
		    /**导入总数*/
			totalNum : 1,
			/**进度条信息*/
			progressBarText : '',
			/**进度条Msg*/
			progressBartitle : '',
		    proxyStore : undefined,
		    items: [],
			/**进度条*/
		    progressBar : null,    
		    /***import成功句柄*/
		    importSuccessHandler : function (json){	
				if (json != null) {	
					if (typeof(json.curRecordNum) != 'undefined' && this.curRecordNumMsg) {
						this.curRecordNumMsg(json.curRecordNum);
						this.curRecordNum = json.curRecordNum;
					}
					if (typeof(json.importState) != 'undefined' && this.importStateMsg) {
						this.importStateMsg(json.importState);
					}				
					if (typeof(json.curSheetRecordNum) != 'undefined' && this.curSheetRecordNumMsg) {
						this.curSheetRecordNumMsg(json.curSheetRecordNum);
					}
					if (typeof(json.sheetNum) != 'undefined' && this.sheetNumMsg) {
						this.sheetNumMsg(json.sheetNum);
					}
					if (typeof(json.totalSheetNum) != 'undefined' && this.totalSheetNumMsg) {
						this.totalSheetNumMsg(json.totalSheetNum);
					}	
					if (typeof(json.finishSheetNum) != 'undefined' && this.finishSheetNumMsg) {
						this.finishSheetNumMsg(json.finishSheetNum);
					}
					if (typeof(json.finishRecordNum) != 'undefined' && this.finishRecordNumMsg) {
						this.finishRecordNumMsg(json.finishRecordNum);
					}
				} else {
					this.curRecordNumInfo = '';
					this.importStateInfo = '';
					this.curSheetRecordNumInfo = '';
					this.sheetNumInfo = '';
					this.totalSheetNumInfo = '';
					this.finishSheetNumInfo = '';
					this.finishRecordNumInfo = '';
				}
				
				this.progressBartitle = '';
				/**进度条Msg信息配置：各信息项目显示内容由各自方法配置*/
				this.progressBartitle += this.curRecordNumMsg      ?this.curRecordNumInfo:'';
				this.progressBartitle += this.importStateMsg 	   ?this.importStateInfo:'';
				this.progressBartitle += this.curSheetRecordNumMsg ?this.curSheetRecordNumInfo:'';
				this.progressBartitle += this.sheetNumMsg 	   	   ?this.sheetNumInfo:'';
				this.progressBartitle += this.totalSheetNumMsg 	   ?this.totalSheetNumInfo:'';
				this.progressBartitle += this.finishSheetNumMsg    ?this.finishSheetNumInfo:'';
				this.progressBartitle += this.finishRecordNumMsg   ?this.finishRecordNumInfo:'';
				
				showProgressBar(this.totalNum,this.curRecordNum,this.progressBarText,this.progressBartitle,"上传成功，正在导入数据，请稍候");
			},
		    buttons : [{
		            text : '导入文件',
		            handler : function() {
		                var tradecode = this.ownerCt.ownerCt.tradecode;
		                var proxyStorePS = this.ownerCt.ownerCt.proxyStore;
		                var proxyHttpPH = this.ownerCt.ownerCt.proxyHttp;
		                if(tradecode==undefined ||tradecode==''){
		                    Ext.MessageBox.alert('Debugging！','You forgot to define the tradecode for the import form!');
		                    return false;
		                }
		                var impRefreshHandler = 0;
		                if (this.ownerCt.ownerCt.getForm().isValid()){
		                    this.ownerCt.ownerCt.ownerCt.hide();
		                    var fileNamesFull = this.ownerCt.ownerCt.items.items[0].getValue();
		                    var extPoit = fileNamesFull.substring(fileNamesFull.indexOf('.'));
		                    if(extPoit=='.xls'||extPoit=='.XLS'||extPoit=='.xlsx'||extPoit=='.XLSX'){
		                    } else {
		                    	Ext.MessageBox.alert("文件错误","导入文件不是XLS或XLSX文件。");
		                        return false;
		                    }
		                    showProgressBar(1,0,'','','正在上传文件...');
		                    
		                    this.ownerCt.ownerCt.getForm().submit({
		                        url : basepath + '/FileUpload?isImport=true',
		                        success : function(form,o){                    		 
		                            _tempImpFileName = Ext.util.JSON.decode(o.response.responseText).realFileName;
		                            var condi = {};
		                            condi['filename'] =_tempImpFileName;
		                            condi['tradecode'] = tradecode;
		                            Ext.Ajax.request({
		                                url:basepath+"/ImportAction.json",
		                                method:'GET',
		                                params:{
		                                    "condition":Ext.encode(condi)
		                                },
		                                success:function(){
		                                	importForm.importSuccessHandler(null);                                	
		                                    var importFresh = function(){
		                                        Ext.Ajax.request({
		                                            url:basepath+"/ImportAction!refresh.json",
		                                            method:'GET',
		                                            success:function(a){                                        		
		                                                if(a.status == '200' || a.status=='201'){
		                                                    var res = Ext.util.JSON.decode(a.responseText);
		                                                    if(res.json.result!=undefined&&res.json.result=='200'){
		                                                        window.clearInterval(impRefreshHandler); 
		                                                        if(res.json.BACK_IMPORT_ERROR&&res.json.BACK_IMPORT_ERROR=='FILE_ERROR'){
		                                                        	Ext.Msg.alert("提示","导出文件格式有误，请下载导入模版。");
		                                                        	return;
		                                                        }
		                                                        if(proxyStorePS!=undefined){
		                                                            var condiFormP = {};
		                                                            condiFormP['pkHaed'] =res.json.PK_HEAD;
		                                                            pkHead = res.json.PK_HEAD;
		                                                            proxyStorePS.load({
		                                                                params:{
		                                                                    pkHead: pkHead
		                                                                }
		                                                            });
		                                                        }else {
		                                                        	importForm.importSuccessHandler(res.json); 
		                                                        	var sumSuccesscount;
		                                                			var sunfirescount;
		                                                			var failureBycallno;
		                                                			var failurecount;
		                                                			Ext.Ajax.request({
		                                                				url : basepath + '/myPotentialCustomer!getSumimportRecord.json',
		                                                				method : 'GET',
		                                                				success : function(response) {
		                                                					var ret = Ext.decode(response.responseText);
		                                                					 sumSuccesscount=ret.sumSuccesscount;
		                                                					 sunfirescount=ret.sunfirescount;
		                                                					 failureBycallno=ret.failureBycallno;
		                                                					 failurecount=ret.failurecount;
		    		                                                		var titles="成功导入记录总数["+res.json.curRecordNum+"]&nbsp;&nbsp;&nbsp;&nbsp;实际成功导入客户["+sumSuccesscount+"]<br/>疑似重复客户记录["+sunfirescount+"]&nbsp;&nbsp;&nbsp;&nbsp;手机格式错误记录["+failureBycallno+"]<br/>已存在客户记录数["+failurecount+"]<br/><br/>";
		    		                                                        showSuccessWinImp(titles);
		                                                				},
		                                                				failure : function() {}
		                                                			});
		                                                		
		                                                        }
		                                                    }else if(res.json.result!=undefined&&res.json.result=='900'){
		                                                    	
		                                                    	
		                                                    	 window.clearInterval(impRefreshHandler);
		                                                         importState = true;
		                                                         progressWin.hide();//隐藏导入进度窗口
		                                                         Ext.Msg.alert("导入失败","失败原因：\n"+res.json.BACK_IMPORT_ERROR);
		                                                         /*new Ext.Window({
		                                                             title:"导入失败：导入线程处理失败！",
		                                                             width:300,
		                                                             height:150,
		                                                             bodyStyle:'text-align:center',
		                                                             html: '失败原因：\n'+res.json.BACK_IMPORT_ERROR //'<img src="'+basepath+'/contents/img/UltraMix55.gif" />'
		                                                         }).show();*/
		                                                    	
		                                                    	
		                                                    	/*
		                                                        window.clearInterval(impRefreshHandler);
		                                                        new Ext.Window({
		                                                            title:"导入失败：导入线程处理失败！",
		                                                            width:200,
		                                                            height:200,
		                                                            bodyStyle:'text-align:center',
		                                                            html: '<img src="'+basepath+'/contents/img/UltraMix55.gif" />'
		                                                        }).show();
		                                                        */
		                                                    }else if (res.json.result!=undefined&&res.json.result=='999'){
		                                                    	importForm.importSuccessHandler(res.json);
		                                                    }
		                                                }
		                                            }
		                                        });
		                                    };
		                                    impRefreshHandler = window.setInterval(importFresh, 1000);
		                                },
		                                failure:function(){}
		                            });
		                           
		                        },
		                        failure : function(form, o){
		                            Ext.Msg.show({
		                                title : 'Result',
		                                msg : '数据文件上传失败，请稍后重试!',
		                                buttons : Ext.Msg.OK,
		                                icon : Ext.Msg.ERROR
		                            });
		                        }
		                    });
		                }
		            }
		        }]
		});
		/**
		 * 导入弹出窗口，此对象为全局对象，由各页面直接调用。
		 */

		var importWindow = new Ext.Window({     
		    width : 700,
		    hideMode : 'offsets',
		    modal : true,
		    height : 250,
		    closeAction:'hide',
		    items : [importForm]
		});
		importWindow.on('show',function(upWindow){
			if(Ext.getCmp('littleup')){
				importForm.remove(Ext.getCmp('littleup'));
			}
			importForm.removeAll(true);
			importForm.add(new Ext.form.TextField({
		        xtype : 'textfield',
		        id:'littleim',
		        name:'annexeName',
		        inputType:'file',
		        fieldLabel : '文件名称',
		        anchor : '90%'
		    }));
			importForm.doLayout();
		});
		var progressBar = {};
		var importState = false;
		var progressWin = new Ext.Window({     
		    width : 300,
		    hideMode : 'offsets',
		    closable : true,
		    modal : true,
		    autoHeight : true,
		    closeAction:'hide',
		    items : [],
		    listeners :{
				'beforehide': function(){
					return importState;
				}
			}
		});
		function showProgressBar(count,curnum,bartext,msg,title) {
			importState = false;
			progressBar = new Ext.ProgressBar({width : 285 });
			progressBar.wait({
		        interval: 200,          	//每次更新的间隔周期
		        duration: 5000,             //进度条运作时候的长度，单位是毫秒
		        increment: 5,               //进度条每次更新的幅度大小，默示走完一轮要几次（默认为10）。
		        fn: function () {           //当进度条完成主动更新后履行的回调函数。该函数没有参数。
					progressBar.reset();
		        }
		    });
			progressWin.removeAll();
			progressWin.setTitle(title);
			if (msg.length == 0) {
				msg = '正在导入...';
			}
			var importContext = new Ext.Panel({
										title: '',
										frame : true,
										region :'center',
										height : 100,
										width : '100%',
										autoScroll:true,
										html : '<span>'+ msg +'</span>'
									});
			progressWin.add(importContext);
			progressWin.add(progressBar);
			progressWin.doLayout();
			progressWin.show();
			
		}
		function showSuccessWinImp(curRecordNum) {
			importState = true;
			progressWin.removeAll();
			progressWin.setTitle("提示");
			progressWin.add(new Ext.Panel({
				title:'',
				width:330,
				layout : 'fit',
				autoHeight : true,
				bodyStyle:'text-align:center',
				html: '<div style="margin-left:40px;">'+curRecordNum+'</div><img src="'+basepath+'/contents/img/importSuccessOk.jpg" />',
				buttonAlign: 'center',
				buttons: [
					new Ext.Button({
					  text:'关闭',
					  handler:function(){
						  reloadCurrentData();
						  progressWin.hide();
					  }
					})]
			}));
			progressWin.doLayout();
			progressWin.show();
		}
		