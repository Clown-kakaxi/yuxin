Ext.onReady(function() {
	var h= document.body.clientHeight;
	//币种Store
	var bizhongStore = new Ext.data.Store({  
		sortInfo: {
	    field: 'key',
	    direction: 'ASC' // or 'DESC' (case sensitive for local sorting)
	},
		restful:true,   
		autoLoad :true,
		proxy : new Ext.data.HttpProxy({
				url :basepath+'/lookup.json?name=ACC1300012'
			}),
			reader : new Ext.data.JsonReader({
				root : 'JSON'
			}, [ 'key', 'value' ])
	});
	//查询期间Store
	 var checkDateDateStore = new Ext.data.ArrayStore({
	        fields:['key','value'],
	        data:[['1','当月累计'],['2','当季累计'],['3','当年累计']]
	        });
	 //客户类别Store
	 var custTypeStore = new Ext.data.ArrayStore({
		  	autoLoad : true,
			fields:['key','value'],
		    data:[['1','对公客户'],['2','个人客户']]
		});
	// custTypeStore.loadData(data);
	 //统计类型Store
	 var countTypeStore = new Ext.data.ArrayStore({
			fields:['key','value'],
		    data:[['1','按业务统计'],['2','按产品统计']]
		});
	var qForm = new Ext.form.FormPanel({
		title: "客户经理业绩查询->明细查询->客户明细查询", 
		labelWidth : 100, // 标签宽度
		frame : true, //是否渲染表单面板背景色
		labelAlign : 'right', // 标签对齐方式
		region : 'north',
		bodyStyle : 'padding:19 19 0', // 表单元素和表单面板的边距
		buttonAlign : 'center',
		height : 180,
		items : [{
					layout : 'column',
					border : false,
					items : [{
								columnWidth : .25,
								layout : 'form',
								labelWidth : 80, // 标签宽度
								defaultType : 'textfield',
								border : false,
								items : [{
											fieldLabel : '客户类型',
											name : 'CUST_TYPE',
											id : 'qCustType',
											xtype : 'combo', 
											store : custTypeStore,
											hiddenName : 'custType',
											triggerAction : 'all',
											valueField : 'key',
											displayField : 'value',
											forceSelection : true,
											typeAhead : true,
											editable : false,
											emptyText : '请选择',
											mode : 'local',
											anchor : '90%'
										},{
											fieldLabel : '统计类型',
											name : 'COUNT_TYPE',
											id : 'qCountType',
											xtype : 'combo', 
											store : countTypeStore,
											hiddenName : 'countType',
											hidden :true,
											triggerAction : 'all',
											valueField : 'key',
											displayField : 'value',
											forceSelection : true,
											typeAhead : true,
											editable : false,
											emptyText : '请选择',
											mode : 'local',
											anchor : '90%'
										}]
							}, {
								columnWidth : .25,
								layout : 'form',
								labelWidth : 80, // 标签宽度
								defaultType : 'textfield',
								border : false,
								items : [instnCombo,{
									xtype:'datefield',
									format : 'Y-m-d',
									fieldLabel : '查询日期', // 标签
									name : 'CHECK_DATE', 
									anchor : '90%' // 宽度百分比
								}]
							}, {
								columnWidth : .25,
								layout : 'form',
								labelWidth : 80, // 标签宽度
								defaultType : 'textfield',
								border : false,
								items : [{
											fieldLabel : '客户经理姓名', // 标签
											name : 'CUST_MGR_NAME', 
											id : 'custMgrName',
											xtype : 'textfield',
											anchor : '90%' // 宽度百分比
										},{
											fieldLabel : '吸存号', // 标签
											name : 'CUST_MGR_NO', 
											id : 'custMgrNo',
											xtype : 'textfield',
											anchor : '90%' // 宽度百分比
										}]
							}, {
								columnWidth : .25,
								layout : 'form',
								labelWidth : 80, // 标签宽度
								defaultType : 'textfield',
								border : false,
								items : [{
									xtype : 'combo',
									store : bizhongStore,
									fieldLabel : '币种',
									id :'bizh',
									hiddenName : 'bizhong',
									triggerAction : 'all',
									valueField : 'key',
									displayField : 'value',
									editable : false,
									emptyText : '请选择',
									mode : 'local',
									anchor : '90%'
								},{
											fieldLabel : '查询期间', // 标签
											name : 'CHECK_DATE_DATE', 
											xtype : 'combo',
											id :'chDate',
											store : checkDateDateStore,
											hiddenName : 'checkDateDate',
											triggerAction : 'all',
											valueField : 'key',
											displayField : 'value',
											editable : false,
											emptyText : '请选择',
											mode : 'local',
											anchor : '90%'
										}]
							}]
				}],
		buttons : [{
					text : '查询',
					handler : function() {
						
						if(!qForm.getForm().isValid()){
							Ext.Msg.alert("提醒","请填写必填项");
							return false;
						}
						select();
						}
				}, {
					text : '重置',
					handler : function() {
						qForm.getForm().reset();
						orgTreePanel.root.getUI().toggleCheck(false);
						//联动并重置后重新2选1
						cbOrgTree.show();//机构树
					    cbCustMgrName.show();//客户经理名称
					    cbCustMgrNo.show();//吸存号
					    cbBizh.show();//币种
					    cbChDate.show();//查询期间
					    cbCountType.hide();//统计类型
					}
				}]
	});
	// 定义自动当前页行号
	var rownum = new Ext.grid.RowNumberer({
		header : 'No.',
		width : 28
	});
   // if((Ext.getCmp('qCustType').getValue()==1)&&(Ext.getCmp('qCountType').getValue()==1)){}
	
	// 定义展示窗口的tabPanel
	var listPanel = new Ext.TabPanel({
		id : 'listPanel',
		loyout : 'fit',
		autoScroll :true,
		region : 'center',
		activeTab : 0,
		tabPosition : 'bottom',
		items : [ {title : '对公客户业务统计明细列表',items : [ grid ]},
		          {title : '对公客户产品统计明细列表',items : [ grid1]},
		          {title : '个人客户主要业务明细列表',items : [ grid2]}]
	});
	
	function select(){
		 var cbCustType = Ext.getCmp('qCustType').getValue();//客户类型
		 var cbCountType = Ext.getCmp('qCountType').getValue();//统计类型
		 var cbChDate = Ext.getCmp('chDate').getValue();//查询期间
		var dates = qForm.getForm().findField('CHECK_DATE').getValue();
	    var cbCustMgrName = Ext.getCmp('custMgrName').getValue();//客户经理名称
	    var cbCustMgrNo = Ext.getCmp('custMgrNo').getValue();//吸存号
	    var cbBizh = Ext.getCmp('bizh').getValue();//币种
		var org_diString = checkId(orgTreePanel);//机构ID
	    var dDate=Ext.util.Format.date(dates,'Y-m-d');

	    var winWidth = screen.width - 10;
			var winHeight = screen.height - 60;
			var winFeatures = "toolbar=no,location=no,directories=no,status=yes,menubar=no,scrollbars=yes,resizable=yes,";
			winFeatures += "top=0,left=0,height="
					+ winHeight + ",width=" + winWidth;
			var url=basepath+'/reportJsp/showReport.jsp?raq=/report_94_2.raq&oddate='+dDate+'&uid='+__units+'&currency=156';
			var winOpen = window.open(url,'chat' + new Date().getTime(),winFeatures);
		}
	var fpanel = new Ext.Panel({
		id : "fpanel",
		labelWidth : 90, // 标签宽度
//		frame : true, //是否渲染表单面板背景色
		labelAlign : 'middle', // 标签对齐方式
		buttonAlign : 'center',
		height: document.body.clientHeight,
		items :[qForm]
		        });
	var viewport = new Ext.Viewport({
		//layout : 'border',
		items: [{   
			region: 'north',
		    id: 'north-panel',
		    height: document.body.clientHeight,
		    hidden:false,
		    margins: '0 0 0 0',
			items:[fpanel]
	     }] 
	});		
	//*********************************下拉联动****************************************
	 var cbCustType = Ext.getCmp('qCustType');//客户类型
	 var cbCountType = Ext.getCmp('qCountType');//统计类型
	 cbCustType.on('select',function(){//客户类型联动统计类型
	    	if(cbCustType.getValue()=='1'){
	    		cbCountType.show();
	    	}
	    	else
	    	{
	    		cbCountType.hide();
	    	}
	    	cbCustType.triggerBlur();//失去焦点以出发change事件的监听
	    });
	 cbCustType.on('change',function(){//1改变或清空时的操作
		 cbCountType.setValue('');
		   	if(cbCustType.getValue()==''){
		   		cbCountType.hide();
		   	}
		   });
	 
	 
	 
	  //*********************************查询条件2选1****************************************
	    var cbOrgTree = Ext.getCmp('orgTree');//机构树
	    var cbCustMgrName = Ext.getCmp('custMgrName');//客户经理名称
	    var cbCustMgrNo = Ext.getCmp('custMgrNo');//吸存号
	    var cbBizh = Ext.getCmp('bizh');//币种
	    var cbChDate = Ext.getCmp('chDate');//查询期间
	    cbOrgTree.on('change',function(){//机构树联动客户经理、吸存号、币种、查询期间
		    	if(cbOrgTree.getValue()!='请选择'){
		    		cbCustMgrName.hide();
		    		cbCustMgrNo.hide();
		    		cbBizh.hide();
		    		cbChDate.hide();
		    	}
		    	if(cbOrgTree.getValue()=='请选择'){
		    		cbCustMgrName.show();
		    		cbCustMgrNo.show();
		    		cbBizh.show();
		    		cbChDate.show();
		    	}
		    });
	    cbCustMgrName.on('change',function(){//客户经理联动机构树
	    	if(cbCustMgrName.getValue()!=''){
	    		cbOrgTree.hide();
	    	}
	    	if(cbCustMgrName.getValue()==''){
	    		cbOrgTree.show();
	    	}
	    });
	    cbCustMgrNo.on('change',function(){//吸存号联动机构数
	    	if(cbCustMgrNo.getValue()!=''){
	    		cbOrgTree.hide();
	    	}
	    	if(cbCustMgrNo.getValue()==''){
	    		cbOrgTree.show();
	    	}
	    });
	    cbBizh.on('select',function(){//币种联动机构数
	    	if(cbBizh.getValue()!='请选择'){
	    		cbOrgTree.hide();
	    	}
	    	if(cbBizh.getValue()=='请选择'){
	    		cbOrgTree.show();
	    	}
	    });
	    cbChDate.on('select',function(){//查询期间联动机构数
	    	if(cbChDate.getValue()!='请选择'){
	    		cbOrgTree.hide();
	    	}
	    	if(cbChDate.getValue()=='请选择'){
	    		cbOrgTree.show();
	    	}
	    });
}); 