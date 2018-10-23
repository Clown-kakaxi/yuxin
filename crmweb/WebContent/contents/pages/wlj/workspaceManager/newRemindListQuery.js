/**
 * 提醒查询NEW
 * hujun
 * 2014-07-08
 */
	imports([
			'/contents/pages/com.yucheng.bcrm/com.yucheng.bcrm.js',//树控件
			'/contents/pages/customer/customerManager/mktPhone/custPhoneList.js',
			'/contents/pages/common/LovCombo.js'
			]);
	//是否需要展示新增，修改，详情
	var createView=false;
	var editView=false;
	var detailView=false;
	Ext.QuickTips.init();
	var myCheckboxItems = []; 

	var lookupTypes=[
	                 'REMIND_TYPE',
	                 'IF_FLAG'
	                 ];
	var localLookup={
			'IF_READ':[
	                            
				{key : '0',value : '未阅'},
				{key : '1',value : '已阅'}          
			           ],

			'FASONGQUDAO':[
			  	{key : '1',value : '短信'},
			  	{key : '2',value : '邮箱'}
			  //  {key : '3',value : '微信'}
			  	 ]
	};
	
	var treeLoaders = [ {
		key : 'PROUDUCTLOADER',
		url : basepath + '/remindListTreeAction.json',//加载产品种类树
		parentAttr : 'PARENT_ID',
		locateAttr : 'RULE_VALUE',
		rootValue : '0',
		textField : 'T_VALUE',
		idProperties : 'RULE_VALUE',
		jsonRoot : 'json.data'
	} ];
	var treeCfgs = [ {
		key : 'PROUDUCTTREE',
		loaderKey : 'PROUDUCTLOADER',
		autoScroll : true,
		rootCfg : {
			expanded : true,
			id : '0',
			text : '提醒信息树',
			autoScroll : true,
			children : []
		},
		clickFn : function(node) {
			if(node.attributes.RULE_CODE){
				setSearchParams({
					READ : '0',
					RULE_CODE : node.attributes.RULE_CODE
				});
			}else{
				//首先处理字段展示
//				getCloumnShow(node.attributes.PROD_VIEW);
				//然后重新查询
				setSearchParams({
					READ : '0',
					RULE_CODE : node.attributes.RULE_CODE
				});
			}
			getConditionField('READ').setValue("0");
			getConditionField('RULE_CODE').setValue(node.attributes.RULE_CODE);
			
			if(node.attributes.RULE_CODE == '101'||node.attributes.RULE_CODE == '102'
				||node.attributes.RULE_CODE == 'CUSTLOSS'){
				//账户余额变动（转出）||账户余额变动（转入）||大额流失
				showGridFields("CHANGE_AMT");//大额异动金额
				hideGridFields("DUE_AMT");
				hideGridFields("DUE_AMT2");
				hideGridFields("HAPPENED_DATE");
				hideGridFields("HAPPENED_DATE2");
				hideGridFields("BIRTHDAY_S");
				hideGridFields("PRODUCT_NAME");
				hideGridFields("CALLREPORT_INFO");
			}else if(node.attributes.RULE_CODE=='300'){//证件到期提醒
				showGridFields("HAPPENED_DATE");//证件到期日
				hideGridFields("HAPPENED_DATE2");
				hideGridFields("CHANGE_AMT");
				hideGridFields("DUE_AMT");
				hideGridFields("DUE_AMT2");
				hideGridFields("BIRTHDAY_S");
				hideGridFields("PRODUCT_NAME");
				hideGridFields("CALLREPORT_INFO");
			}else if(node.attributes.RULE_CODE=='202'){//定期存款到期
				showGridFields("DUE_AMT");//定存到期金额
				hideGridFields("DUE_AMT2");
				showGridFields("HAPPENED_DATE2");//定存到期日
				hideGridFields("HAPPENED_DATE");
				hideGridFields("CHANGE_AMT");
				hideGridFields("BIRTHDAY_S");
				hideGridFields("PRODUCT_NAME");
				hideGridFields("CALLREPORT_INFO");
			}else if(node.attributes.RULE_CODE=='203'){//理财产品到期
				showGridFields("DUE_AMT2");//到期金额
				showGridFields("PRODUCT_NAME");//理财名称
				hideGridFields("DUE_AMT");
				hideGridFields("HAPPENED_DATE");
				hideGridFields("HAPPENED_DATE2");
				hideGridFields("CHANGE_AMT");
				hideGridFields("BIRTHDAY_S");
				hideGridFields("CALLREPORT_INFO");
			}else if(node.attributes.RULE_CODE=='301'){
				showGridFields("BIRTHDAY_S");//客户阳历生日
				hideGridFields("DUE_AMT");
				hideGridFields("DUE_AMT2");
				hideGridFields("HAPPENED_DATE");
				hideGridFields("HAPPENED_DATE2");
				hideGridFields("CHANGE_AMT");
				hideGridFields("PRODUCT_NAME");
				hideGridFields("CALLREPORT_INFO");
			}else if(node.attributes.RULE_CODE=='306'||node.attributes.RULE_CODE=='307'||node.attributes.RULE_CODE=='308'||node.attributes.RULE_CODE=='309'||node.attributes.RULE_CODE=='310'||node.attributes.RULE_CODE=='311'){
				hideGridFields("DUE_AMT");
				hideGridFields("DUE_AMT2");
				hideGridFields("HAPPENED_DATE");
				hideGridFields("HAPPENED_DATE2");
				hideGridFields("CHANGE_AMT");
				hideGridFields("PRODUCT_NAME");
				hideGridFields("BIRTHDAY_S");
				showGridFields("CALLREPORT_INFO");
			}else{//客户阳历生日
				hideGridFields("DUE_AMT");
				hideGridFields("DUE_AMT2");
				hideGridFields("HAPPENED_DATE");
				hideGridFields("HAPPENED_DATE2");
				hideGridFields("CHANGE_AMT");
				hideGridFields("PRODUCT_NAME");
				hideGridFields("BIRTHDAY_S");
				hideGridFields("CALLREPORT_INFO");
			}
			
		}
	}, {
		key : 'PROUDUCTTREECOMBO',
		loaderKey : 'PROUDUCTLOADER',
		autoScroll : true,
		rootCfg : {
			expanded : true,
			id : 'root',
			text : '提醒信息树',
			autoScroll : true,
			children : []
		},
		clickFn : function(node){
		}
	} ];
	
		//查询URL
	var url= basepath + '/remindListQueryAction.json';
	//加载树到左侧区域
	var edgeVies = {
		left : {
			width : 200,
			layout : 'form',
			items : [ TreeManager.createTree('PROUDUCTTREE') ]
		}
	};
	
	var fields=[
	            {name:'INFO_ID',text:'编号',resutlWidth:80,hidden:true},
	            {name: 'RULE_CODE', text : '提醒类型',hidden : true,resutlWidth:80,xtype: 'wcombotree',
			    	innerTree:'PROUDUCTTREECOMBO',allowBlank:false,showField:'text',
			    	translateType:'REMIND_TYPE',hideField:'RULE_CODE',editable:false,searchField: true},
//	            {name:'RULE_CODE',text:'提醒类型',translateType:'REMIND_TYPE',editable:true,xtype: 'wcombotree',innerTree:'PROUDUCTTREECOMBO',showField:'text',hideField:'RULE_CODE',editable:false,searchField: true},
	            {name:'READ',text:'是否已读',searchField:true,translateType:'IF_READ',resutlWidth:80},
	            {name:'CUST_ID',text:'客户ID',resutlWidth:100},
	            {name:'CUST_NAME',text:'客户名称',resutlWidth:80},
	            {name:'MSG_CRT_DATE',text:'提醒生成日期',resutlWidth:80,searchField:true,xtype:'datefield',format:'Y-m-d'},
	            {name:'MSG_START_DATE',text:'提醒开始日期',resutlWidth:80,searchField:true,xtype:'datefield',format:'Y-m-d'},
	            {name:'MSG_END_DATE',text:'提醒到期日期',resutlWidth:80,searchField:true,xtype:'datefield',format:'Y-m-d'},
	            {name:'CALLREPORT_INFO',text:'callreport信息',resutlWidth:600,xtype:'textarea',hidden:true},
	            {name:'LAST_DATE',text:'剩余天数',resutlWidth:80,xtype: 'numberfield',hidden:true},
	            {name:'USER_NAME',text:'提醒接收人',resultWidth:100,xtype:'textarea',hidden:true},
	            {name:'REMIND_REMARK',text:'提醒内容',resutlWidth:600,xtype:'textarea',hidden:true},
	            {name:'BIRTHDAY_S',text:'生日日期',resutlWidth:100,xtype:'datefield',format:'Y-m-d',hidden:true},
	            //{name:'IDENT_EXPIRED_DATE',text:'证件到期日期',resutlWidth:100,xtype:'datefield',format:'Y-m-d',hidden:true},
	           // {name:'MESSAGE_REMARK',text:'发送信息内容',resultWidth:350,xtype:'textarea',gridField:false},
	            //{name:'MAIL_REMARK',text:'发送邮件内容',resultWidth:350,xtype:'textarea',gridField:false},
	           // {name:'MICRO_REMARK',text:'发送微信内容',resultWidth:350,xtype:'textarea',gridField:false},
	            
	            {name:'CHANGE_AMT',text:'大额异动金额',resutlWidth:80,xtype: 'numberfield',hidden:true},
	            {name:'PRODUCT_NAME',text:'理财名称',resutlWidth:80,hidden:true},
	            {name:'DUE_AMT',text:'定存到期金额',resutlWidth:80,xtype: 'numberfield',hidden:true},
	            {name:'DUE_AMT2',text:'到期金额',resutlWidth:80,xtype: 'numberfield',hidden:true},
	            {name:'HAPPENED_DATE',text:'证件到期日',resutlWidth:100,xtype:'datefield',format:'Y-m-d',hidden:true},
	            {name:'HAPPENED_DATE2',text:'定存到期日',resutlWidth:100,xtype:'datefield',format:'Y-m-d',hidden:true}
	            
//	            {name:'AA',text:'',resutlWidth:80,xtype: 'numberfield'}
	           /* {name:'SEND_MANTHED',hidden:true,text:'可发送渠道',resutlWidth:80,viewFn:function(val){	
	            	if(val=='1'){
	            		return "短信";
	            	}else if(val=='2'){
	            		return '邮件';
	            	}else if(val=='3'){
	            		return '微信';
	            	}else if(val=='1,2'||val=='2,1'){
	            		return '短信,邮件';
	            	}else if(val=='3,2'||val=='2,3'){
	            		return '邮件,微信';
	            	}else if(val=='1,3'||val=='3,1'){
	            		return '短信,微信';
	            	}else if(val=='1,2,3'||val=='3,2,1'||val=='2,1,3'||
	            			val=='2,3,1'||val=='1,3,2'||val=='3,1,2'){
	            		return '短信,邮件,微信';
	            	}else{
	            		return '无';
	            	}
	            		
	            }},*/
	            //{name:'IF_MESSAGE',text:'是否已发送短信',translateType:'IF_FLAG',resutlWidth:120,hidden:true},
	           // {name:'IF_MAIL',text:'是否已发送邮件',translateType:'IF_FLAG',resutlWidth:120,hidden:true},
	           // {name:'IF_MICRO',text:'是否已发送微信',translateType:'IF_FLAG',resutlWidth:80},
	            //{name:'MOBILE_PHONE',text:'手机号',hidden:true,resutlWidth:80},
	           // {name:'WEIXIN',text:'微信号',hidden:true},
	           // {name:'MAIL',text:'邮箱号',hidden:true,resutlWidth:80}
	            ];
	var detailFormViewer=[{
			columnCount:2,
			fields:['RULE_CODE','CUST_NAME','MSG_END_DATE','LAST_DATE','MSG_CRT_DATE'],
			fn:function(RULE_CODE,CUST_NAME,MSG_END_DATE,LAST_DATE,MSG_CRT_DATE){
				RULE_CODE.readOnly=true;
				RULE_CODE.cls='x-readOnly';
				CUST_NAME.readOnly=true;
				CUST_NAME.cls='x-readOnly';
				MSG_END_DATE.readOnly=true;
				MSG_END_DATE.cls='x-readOnly';
				LAST_DATE.readOnly=true;
				LAST_DATE.cls='x-readOnly';
				MSG_CRT_DATE.readOnly=true;
				MSG_CRT_DATE.cls='x-readOnly';
				return [RULE_CODE,CUST_NAME,MSG_END_DATE,LAST_DATE,MSG_CRT_DATE];
			}
	},{
			columnCount:1,
			fields:['REMIND_REMARK','MESSAGE_REMARK','MAIL_REMARK','MICRO_REMARK'],
			fn:function(REMIND_REMARK,MESSAGE_REMARK,MAIL_REMARK,MICRO_REMARK){
				REMIND_REMARK.readOnly=true;
				REMIND_REMARK.cls='x-readOnly';
				MESSAGE_REMARK.readOnly=true;
				MESSAGE_REMARK.cls='x-readOnly';
				MAIL_REMARK.readOnly=true;
				MAIL_REMARK.cls='x-readOnly';
				MICRO_REMARK.readOnly=true;
				MICRO_REMARK.cls='x-readOnly';
				return [REMIND_REMARK,MESSAGE_REMARK,MAIL_REMARK,MICRO_REMARK];
			}
	}];
	//首页磁贴数据刷新
	var newRemind = function(infoId){
//		debugger;
		var len = parent.Wlj.TileMgr.tiles.items.length;
		for(i=0;i<len;i++){
			var tileName = parent.Wlj.TileMgr.tiles.items[i].tileName;
			if(tileName == '提醒查询'){
				var gridQ = parent.Wlj.TileMgr.tiles.items[i].get('listPanelRemind');
				if(gridQ != null && gridQ != undefined){
					var div = gridQ.el.dom.document.getElementById("demo");
					if(div != null && div != undefined){
						var ul = gridQ.el.dom.document.getElementById("ulText");
						var ulNew = ul;
						var ulOld = ul;
						var liId = "xinxitixing"+infoId;
						var children = ul.children;
						var childLen = children.length;
						var newChild;
						Ext.Ajax.request({
							url : basepath + '/remindListQueryNewAction.json',
							method : 'GET',
							success : function(response) {
								var ret = Ext.decode(response.responseText);
								var data = ret.json.data;
								var len = data.length;
								//判断所选数据是否已读，不在未读数据中
								var flg = 0;
								var id;
								for(var n=0;n<len;n++){
									id = data[n].INFO_ID;
									if(id == infoId){
										flg = 1;
										break;
									}
								}
								if(flg == 0){
									for(var j=0;j<childLen;j++){
									var liN = children.item(j);
									var childLiId = liN.id;
									if(childLiId == '' || childLiId == 'marquee'){
										if(len == 0){
											marquee.stop();
										}else if(len < 8){
											var marquee = gridQ.el.dom.document.getElementById("marquee");
											ulNew.removeChild(marquee);
											var childMarquee = liN.children;
											var lenMarquee = childMarquee.length;
											var m = 0;
											for(var k=0;k<lenMarquee;k++){
												var liMarquee = childMarquee.item(m);
												var liIdMarquee = liMarquee.id;
												if(liId == liIdMarquee){
													m++;
												}else{
													newChild = liMarquee;
													ulNew.appendChild(newChild);
												}
											}
//											div.appendChild(ulNew);
										}else{
											var marquee = gridQ.el.dom.document.getElementById("marquee");
											var childMarquee = liN.children;
											var lenMarquee = childMarquee.length;
											for(var k=0;k<lenMarquee;k++){
												var liMarquee = childMarquee.item(k);
												var liIdMarquee = liMarquee.id;
												if(liId == liIdMarquee){
													liN.removeChild(liMarquee);
													break;
												}
											}
										}
									}else{
										if(liId == childLiId){
											ul.removeChild(liN);
											break;
										}
									}
								}
							  }
							}
						});
					}
				}
//				break;
			}
		}
	};
	/*var tbar=[
		{
			text : '设为已读',
			//iconCls : 'ReadIconCss',
			handler : function() {
				var selectLength = getAllSelects().length;
				if (selectLength > 1) {
					Ext.Msg.alert('请选择一条记录!');
				} else {
					var infoRecord = getSelectedData();
					if (infoRecord == null || infoRecord == '') {
						Ext.Msg.alert('提示', '请选择一行数据');
					}else if(infoRecord.data.READ!='0'){
						Ext.Msg.alert('提示', '请选择未阅读的数据操作');
					} else {
						Ext.Ajax.request({
							url : basepath+ '/remindListQueryAction!read.json',
							params : {
								id:infoRecord.data.INFO_ID
								},
							success : function() {
								Ext.Msg.alert('提示','保存成功');
								reloadCurrentData();
								var infoId = infoRecord.data.INFO_ID;
								newRemind(infoId);
							},
							failure : function() {
								Ext.Msg.alert('提示','失败');
							}
						});
					}
				}
			}
		}
		,{
			//text:'发送信息',
			//iconCls : 'editIconCss',
			handler:function(){
				var selectLength = getAllSelects.length;
				var rex='';
				if (selectLength > 1) {
					Ext.Msg.alert('请选择一条记录!');
				} else {
					var infoRecord = getSelectedData();
					if (infoRecord == null || infoRecord == '') {
						Ext.Msg.alert('提示', '请选择一行数据');
					} else {
						//判断对应的发送方式是否有号码，内容，返回
						if(infoRecord.data.SEND_MANTHED!=''){
						
							var s =infoRecord.data.SEND_MANTHED.split(',');
							for(var i=0;s.length>i;i++){//判断是否有电话号码，邮件地址，微信号
								if(s[i]=='1'){
									if(infoRecord.data.MOBILE_PHONE==''){
										rex+=' 无电话号码 ';
									}
								}else if(s[i]=='2'){
									if(infoRecord.data.MAIL==''){
										rex+=' 无邮箱地址 ';
									}
								}else if(s[i]=='2'){
									if(infoRecord.data.WEIXIN==''){
										rex+=' 无微信地址 ';
									}
								}
							}
//							if(rex.length>0){
//								Ext.Msg.alert("提示",rex);
//								return false;
//							}
							  showCustomerViewByTitle('发送渠道');
							
						}
						
					}
				}
			}
			},{
				text : '连接CallReport',
				handler:function(){
					if(getSelectedData() == false){
						Ext.Msg.alert('提示','请选择数据！');
						return false;
					}
					var custId = getSelectedData().data.CUST_ID;
					var custName = getSelectedData().data.CUST_NAME;
					parent.Wlj.ViewMgr.openViewWindow(5,custId,custName);
					Ext.Ajax.request({
						url : basepath+ '/remindListQueryAction!read.json',
						params : {
							id:getSelectedData().data.INFO_ID
							},
						success : function() {
							Ext.Msg.alert('提示','保存成功');
							reloadCurrentData();
							var infoId = getSelectedData().data.INFO_ID;
							newRemind(infoId);
						},
						failure : function() {
							Ext.Msg.alert('提示','失败');
						}
					});
				}
//				handler : function() {
//					parent._APP.openWindow({
//						name : 'callReport',
//						action : basepath
//								+ '/crmweb/contents/pages/wlj/serviceManager/callReport.js',
//						resId : 192278,//系统菜单表ID
//						id : 'task_192278',
//						serviceObject : false
//					});
//				}
			}];*/

	
		/*var customerView = [ {
				title : '发送渠道',
				hideTitle: JsContext.checkGrant('_msgSend'),
				type : 'form',
				groups : [ {
					columnCount : 1,
					fields : [ {
						name : 'SEND_MANTHED',
						columns: 3,  //在上面定义的宽度上展示3列  
						text : '提醒发送渠道',
						xtype:'checkboxgroup',
						vertical : true,
						autoWidth : true,
						allowBlank : false,
						items:[{boxLabel:'短信',name:'1'},
						       {boxLabel:'邮件',name:'2'},	     
							   {boxLabel:'微信',name:'3'}]	     
					} ]
				} ],
				formButtons:[{
					text:'发送',
					fn:function(formPanel,basicForm){
						if(!basicForm.isValid())
						{
							Ext.Msg.alert('提示','请输入完整！');
							return false;
						}
						var ifChannel='';
						 var ss = basicForm.findField('SEND_MANTHED').getValue();
						 for(var i=0;ss.length>i;i++){
							 ifChannel 	+= ','+ ss[i].name;
						 }
						if(getSelectedData().data.IF_MESSAGE=='1'||getSelectedData().data.IF_MICRO=='1'||getSelectedData().data.IF_MAIL=='1'){//发送过的提示，确认是否在发送
							Ext.MessageBox.confirm('提示','本提醒已经通过短信或邮件发送过，是否再次发送?',function(buttonId){
								if(buttonId.toLowerCase() == "no"){
								return;
								} 
								Ext.Ajax.request({
									url : basepath+ '/remindListQueryAction!send.json',
									params : {
										ifChannel:ifChannel,
										id:getSelectedData().data.INFO_ID,
										custId:getSelectedData().data.CUST_ID,
										custName:getSelectedData().data.CUST_NAME,
										number:getSelectedData().data.MOBILE_PHONE,
										messageRemark:getSelectedData().data.MESSAGE_REMARK,
										weixinRe:getSelectedData().data.MICRO_REMARK,
										emailRe:getSelectedData().data.MAIL_REMARK,
										weixin:getSelectedData().data.WEIXIN,
										email:getSelectedData().data.EMAIL
										//sendChannel:getSelectedData().data.SEND_MANTHED
										},
									success : function() {
										Ext.Msg.alert('提示','短信邮件已发送，请确认');
										reloadCurrentData();
									},
									failure : function() {
										Ext.Msg.alert('提示','失败');
									}
								});
							});
						}else{//直接发送
							Ext.Ajax.request({
								url : basepath+ '/remindListQueryAction!send.json',
								params : {
									ifChannel:ifChannel,
									id:getSelectedData().data.INFO_ID,
									custId:getSelectedData().data.CUST_ID,
									custName:getSelectedData().data.CUST_NAME,
									number:getSelectedData().data.MOBILE_PHONE,
									messageRemark:getSelectedData().data.MESSAGE_REMARK,
									weixinRe:getSelectedData().data.MICRO_REMARK,
									emailRe:getSelectedData().data.MAIL_REMARK,
									weixin:getSelectedData().data.WEIXIN,
									email:getSelectedData().data.EMAIL
									},
								success : function() {
									Ext.Msg.alert('提示','短信邮件已发送，请确认');
									reloadCurrentData();
								},
								failure : function() {
									Ext.Msg.alert('提示','失败');
								}
							});
						}
					}
				},{
					 text:'关闭',
					 fn:function(){
						 hideCurrentView();
					 }
				}]
			} ];*/
	 var beforeviewshow = function(view){
		 if(view.baseType == 'detailView'){
			if(getSelectedData() == false){
					Ext.Msg.alert('提示','请选择一条数据');
					return false;
			}	
			 var ruleCode = getSelectedData().data.RULE_CODE;
			 if(ruleCode == '901'){//客户经理团队成员维护详情界面暂定显示字段
				 view.contentPanel.getForm().findField('CUST_NAME').hide();
				 view.contentPanel.getForm().findField('MSG_END_DATE').hide();
				 view.contentPanel.getForm().findField('LAST_DATE').hide();
				 view.contentPanel.getForm().findField('MESSAGE_REMARK').hide();
				 view.contentPanel.getForm().findField('MAIL_REMARK').hide();
				 view.contentPanel.getForm().findField('MICRO_REMARK').hide();
				 view.contentPanel.getForm().findField('MSG_CRT_DATE').show();
			 }else{
				 view.contentPanel.getForm().findField('MSG_CRT_DATE').show();
				 view.contentPanel.getForm().findField('CUST_NAME').show();
				 view.contentPanel.getForm().findField('MSG_END_DATE').show();
				 view.contentPanel.getForm().findField('LAST_DATE').show();
				 view.contentPanel.getForm().findField('MESSAGE_REMARK').show();
				 view.contentPanel.getForm().findField('MAIL_REMARK').show();
				 view.contentPanel.getForm().findField('MICRO_REMARK').show();
			 }
		 }
		/* if(view._defaultTitle =='发送渠道'){
			if(getSelectedData() == false){
				Ext.Msg.alert('提示','请选择一条数据');
				return false;
			}
			if(getSelectedData().data.SEND_MANTHED == ''){
				Ext.Msg.alert('提示','该提醒无发送渠道！');
				return false;
			}
			var value1= getSelectedData().data.SEND_MANTHED.split(',');
			var ster=view.contentPanel.getForm().findField('SEND_MANTHED').items.items;
			
		    
		    	if (value1 == "1"){
		    		ster[1].setVisible(false);
		    		ster[0].setVisible(true);
		    		ster[2].setVisible(false);
				}else if(value1=="2"){
					ster[0].setVisible(false);
					ster[1].setVisible(true);
		    		ster[2].setVisible(false);
				}else if(value1=="3"){
					ster[0].setVisible(false);
		    		ster[1].setVisible(false);
		    		ster[2].setVisible(true);
				}else if(value1=='1,2'||value1=='2,1'){
					ster[2].setVisible(false);
					ster[1].setVisible(true);
					ster[0].setVisible(true);
            	}else if(value1=='3,2'||value1=='2,3'){
            		ster[0].setVisible(false);
            		ster[1].setVisible(true);
            		ster[2].setVisible(true);
            	}else if(value1=='1,3'||value1=='3,1'){
            		ster[1].setVisible(false);
            		ster[0].setVisible(true);
            		ster[2].setVisible(true);
            	}
		       
		 }*/
	 };
	 //数据双击事件
	 var rowdblclick = function(){
	 	var custId = getSelectedData().data.CUST_ID;
			var custName = getSelectedData().data.CUST_NAME;
			parent.Wlj.ViewMgr.openViewWindow(5,custId,custName);
			Ext.Ajax.request({
				url : basepath+ '/remindListQueryAction!read.json',
				params : {
					id:getSelectedData().data.INFO_ID,
					read:getSelectedData().data.READ
					},
				success : function() {
//					Ext.Msg.alert('提示','保存成功');
					reloadCurrentData();
//					var infoId = getSelectedData().data.INFO_ID;
//					newRemind(infoId);
				},
				failure : function() {
					Ext.Msg.alert('提示','失败');
				}
			});
	 }
	
	/*var viewhide=function(view){
		if(view.baseType == 'detailView'){//查看详情后设为已读
			var infoRecord = getSelectedData();
			if(infoRecord.data.READ=='0'){
			Ext.Ajax.request({
				url : basepath+ '/remindListQueryAction!read.json',
				params : {
					id:infoRecord.data.INFO_ID
					},
				success : function() {
					reloadCurrentData();
					var infoId = infoRecord.data.INFO_ID;
					newRemind(infoId);
				},
				failure : function() {
					Ext.Msg.alert('提示','失败');
				}
			});
			}
		}
	}*/
	
	//页面完成初始化之后，首先查询得到默认的显示方案(因为页面最初展示全部产品，所以此时查询运用最多的方案)，然后根据方案显示字段
	/*var afterinit = function(){
		Ext.Ajax.request({
			url : basepath + '/#.json',
			success : function(response) {
				var result = response.responseText;
				var results = result.split("#");
				if(results[0] == 'no'){
					Ext.Msg.alert('提示', '产品均未配置展示方案，请到[产品类别管理]部分配置');
				}else {
					getCloumnShow(results[1]);
				}
			}
		});
	};*/