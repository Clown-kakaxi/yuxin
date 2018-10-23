
	var ifGroup = false;//是否从客户群处创建
	var ifProd = false;//是否从适合产品处创建
	var groupId = '';
	var prodId = '';
	var prodName = '';
	var custId = '' ;
	var custName = '';
	
	//营销方式
	var mactiWayeStore = new Ext.data.Store( {
        restful : true,
        sortInfo : {
            field : 'key',
            direction : 'ASC'
        },
        autoLoad : true,
        proxy : new Ext.data.HttpProxy( {
            url : basepath + '/lookup.json?name=MKT_WAY'
        }),
        reader : new Ext.data.JsonReader( {
            root : 'JSON'
        }, [ 'key', 'value' ])
    });
	//营销活动类型
	var mactiTypeStore = new Ext.data.Store( {
        restful : true,
        sortInfo : {
            field : 'key',
            direction : 'ASC'
        },
        autoLoad : true,
        proxy : new Ext.data.HttpProxy( {
            url : basepath + '/lookup.json?name=ACTI_TYPE'
        }),
        reader : new Ext.data.JsonReader( {
            root : 'JSON'
        }, [ 'key', 'value' ])
    });
	   //营销活动状态
	    var mactiStatusStore = new Ext.data.Store( {
	        restful : true,
	        sortInfo : {
	            field : 'key',
	            direction : 'ASC'
	        },
	        autoLoad : true,
	        proxy : new Ext.data.HttpProxy( {
	            url : basepath + '/lookup.json?name=MACTI_STATUS'
	        }),
	        reader : new Ext.data.JsonReader( {
	            root : 'JSON'
	        }, [ 'key', 'value' ])
	    });
	    mactiStatusStore.load();
	//产品放大镜
	var prodAddCode = new Com.yucheng.crm.common.ProductManage( {
		xtype : 'productChoose',
		fieldLabel : '目标营销产品',
		name : 'productName',
		hiddenName : 'aimProd',
		singleSelect : false,
		anchor : '90%',
		callback :function(){
		var prodCode = addActivityProdForm.form.findField('aimProd').getValue();
		addActivityProdForm.form.findField('prodCode').setValue(prodCode);
	}
		});
	var search_cust_add = new Com.yucheng.bcrm.common.CustomerQueryField({ 
		fieldLabel : '目标客户', 
		labelStyle: 'text-align:right;',
		name : 'custNameStr',
		custtype :'2',//客户类型：  1：对私, 2:对公,  不设默认全部
	    custStat:'1',//客户状态: 1:正式 2：潜在     , 不设默认全部
	    singleSelected:false,//单选复选标志
		editable : false,
		blankText:"请填写",
		anchor : '90%',
		hiddenName:'abcd',
		callback :function(){
		var cust_name = null;
		var linkNum = '';
//		cust_name = Ext.getCmp('add_aimCustomerName').getValue();
//		if (cust_name != null && cust_name != '') {
//			linkNum = Ext.getCmp('add_aimCustomerName').mobileNum.mobilenum[0];
////			dailyForm.getForm().findField('newcusttel').setValue(linkNum);
//		}
	}
	});
	
    // 新增、修改、详情信息窗口展示的from
    var editBasePlanForm = new Ext.form.FormPanel({
            frame : true,
            region : 'center',
            autoScroll : true,
            items : [ {
                layout : 'column',
                items : [ {
                    columnWidth : .25,
                    layout : 'form',
                    items : [ {
                        xtype : 'textfield',
                        labelStyle : 'text-align:right;',
                        width : 134,
                        fieldLabel : '<span style="color:red">*</span>营销活动名称',
                        allowBlank : false,
                        name : 'mktActiName',
                        anchor : '99%'
                    },{
						store : mactiStatusStore,
						xtype : 'combo', 
						resizable : true,
						fieldLabel : '<span style="color:red">*</span>营销活动状态',
						hiddenName : 'mktActiStat',
						name : 'mktActiStat',
						valueField : 'key',
						labelStyle : 'text-align:right;',
						displayField : 'value',
						mode : 'local',
						width : 100,
						readOnly : true,
						forceSelection : true,
						triggerAction : 'all',
						emptyText : '请选择',
						anchor : '99%'
					} ]
                },{
                    columnWidth : .25,
                    layout : 'form',
                    items : [ {
						store : mactiTypeStore,
						xtype : 'combo', 
						resizable : true,
						fieldLabel : '<span style="color:red">*</span>营销活动类型',
						hiddenName : 'mktActiType',
						name : 'mktActiType',
						valueField : 'key',
						labelStyle : 'text-align:right;',
						displayField : 'value',
						allowBlank : false,
						mode : 'local',
						width : 100,
						editable :false,
						forceSelection : true,
						triggerAction : 'all',
						emptyText : '请选择',
						anchor : '99%'
					},{
                        xtype : 'datefield',
                        labelStyle : 'text-align:right;',
                        width : 134,
                        editable:false,
                        allowBlank : false,
                        format : 'Y-m-d',
                        fieldLabel : '<span style="color:red">*</span>计划开始时间',
                        name : 'pstartDate',
                        anchor : '99%'
                    } ]
                },{
                    columnWidth : .25,
                    layout : 'form',
                    items : [{
						store : mactiWayeStore,
						xtype : 'combo', 
						resizable : true,
						fieldLabel : '<span style="color:red">*</span>营销方式',
						hiddenName : 'mktActiMode',
						name : 'mktActiMode',
						valueField : 'key',
						allowBlank : false,
						labelStyle : 'text-align:right;',
						displayField : 'value',
						mode : 'local',
						width : 134,
						editable :false,
						forceSelection : true,
						triggerAction : 'all',
						emptyText : '请选择',
						anchor : '99%'
					},{
                        xtype : 'textfield',
                        fieldLabel : '创建人',
                        hidden:true,
                        name : 'createUser',
                        anchor : '99%'
                    },{
                        xtype : 'datefield',
                        fieldLabel : '创建时间',
                        format : 'Y-m-d',
                        hidden:true,
                        name : 'createDate',
                        anchor : '99%'
                    },{
                        xtype : 'textfield',
                        fieldLabel : '更新人',
                        hidden:true,
                        name : 'updateUser',
                        anchor : '99%'
                    },{
                        xtype : 'datefield',
                        fieldLabel : '更新时间',
                        format : 'Y-m-d',
                        hidden:true,
                        name : 'updateDate',
                        anchor : '99%'
                    },{
                        xtype : 'textfield',
                        fieldLabel : '创建机构',
                        hidden:true,
                        name : 'createOrg',
                        anchor : '99%'
                    },{
						fieldLabel : '<span style="color:red">*</span>计划结束时间',
						xtype : 'datefield',
						width : 134,
						labelStyle : 'text-align:right;',
						format : 'Y-m-d',
						editable : false,
						allowBlank : false,
						name : 'pendDate',
						anchor : '99%'
					} ]
                },{
                    columnWidth : .25,
                    layout : 'form',
                    items : [{
                        labelStyle : 'text-align:right;',
                        width : 134,
                        fieldLabel : '<span style="color:red">*</span>费用预算',
                        xtype : 'numberfield', // 设置为数字输入框类型
						decimalPrecision:2,
						maxValue:99999999,
                        allowBlank : false,
                        name : 'mktActiCost',
                        anchor : '99%'
                    } ]
                },{
                    columnWidth : .25,
                    layout : 'form',
                    items : [ {
                        xtype : 'textfield',
                        labelStyle : 'text-align:right;',
                        width : 134,
                        hidden:true,
                        fieldLabel : '营销活动ID',
                        name : 'mktActiId',
                        anchor : '99%'
                    } ]
                },{
                    columnWidth : .50,
                    layout : 'form',
                    items : [ {
                        xtype : 'textarea',
                        labelStyle : 'text-align:right;',
                        width : 400,
                        fieldLabel : '活动地点',
                        name : 'mktActiAddr',
                        anchor : '99%'
                    } ]
                },{
                    columnWidth : .50,
                    layout : 'form',
                    items : [ {
                        xtype : 'textarea',
                        labelStyle : 'text-align:right;',
                        width : 400,
                        fieldLabel : '营销活动内容',
                        // readOnly : true,
                        name : 'mktActiCont',
                        anchor : '99%'
                    } ]
                },{
                    columnWidth : .50,
                    layout : 'form',
                    items : [ {
                        xtype : 'textarea',
                        labelStyle : 'text-align:right;',
                        width : 400,
                        fieldLabel : '涉及客户群描述',
                        // readOnly : true,
                        name : 'actiCustDesc',
                        anchor : '99%'
                    } ]
                },{
                    columnWidth : .50,
                    layout : 'form',
                    items : [ {
                        xtype : 'textarea',
                        labelStyle : 'text-align:right;',
                        width : 400,
                        fieldLabel : '涉及执行人描述',
                        // readOnly : true,
                        name : 'actiOperDesc',
                        anchor : '99%'
                    } ]
                },{
                    columnWidth : .50,
                    layout : 'form',
                    items : [ {
                        xtype : 'textarea',
                        labelStyle : 'text-align:right;',
                        width : 400,
                        fieldLabel : '涉及产品描述',
                        // readOnly : true,
                        name : 'actiProdDesc',
                        anchor : '99%'
                    } ]
                },{
                    columnWidth : .50,
                    layout : 'form',
                    items : [ {
                        xtype : 'textarea',
                        labelStyle : 'text-align:right;',
                        width : 400,
                        fieldLabel : '营销活动目的',
                        // readOnly : true,
                        name : 'mktActiAim',
                        anchor : '99%'
                    } ]
                },{
                    columnWidth : 50,
                    layout : 'form',
                    items : [ {
                        xtype : 'textarea',
                        labelStyle : 'text-align:right;',
                        width : 400,
                        fieldLabel : '备注',
                        // readOnly : true,
                        name : 'actiRemark',
                        anchor : '90%'
                    } ]
                } ]
            }]
        });
	
    // 修改窗口展示的from
    var editPlanPanel = new Ext.Panel( {
        layout : 'fit',
        autoScroll : true,
        buttonAlign : "center",
        items : [ editBasePlanForm ]
    });

    // 定义修改窗口
    var editPlanWindow = new Ext.Window( {
        title : '营销活动修改',
        plain : true,
        layout : 'fit',
        autoScroll : true,
        maximized : true,
        draggable : true,
        closable : true,
        closeAction : 'hide',
        modal : true,
        titleCollapse : true,
        loadMask : true,
        border : false,
        items : [ {
            xtype : 'fieldset',
            title : '基本信息',
            titleCollapse : true,
			collapsible : true,
            buttonAlign : "center",
			id:'jbxx',
            layout : 'fit',
            items : [editBasePlanForm],
            buttons : [ {
    			text : '保存',
    			handler : function() {
    			if (!editBasePlanForm.getForm().isValid()) {
                    Ext.MessageBox.alert('提示','输入有误,请检查输入项');
                    return false;
                };
                var _date = new Date();
                var _pstartDate = editBasePlanForm.form.findField('pstartDate').getValue();
                var _pendDate   = editBasePlanForm.form.findField('pendDate').getValue();
                if(_pstartDate.format('Y-m-d')<_date.format('Y-m-d')){
                	Ext.MessageBox.alert('提示','计划开始时间不能小于今天');
                	return false;
                }if(_pendDate<_pstartDate){
                	Ext.MessageBox.alert('提示','计划开始时间不能小于计划结束时间');
                	return false;
                }
    			Ext.MessageBox.confirm('提示','你填写的记录将要被保存，确定要执行吗?',function(buttonId){
    				if(buttonId.toLowerCase() == "no"){
    				return;
    				}
    				Ext.Msg.wait('正在保存，请稍后......','系统提示');
    			Ext.Ajax.request({
    				url : basepath + '/market-activity.json',
    				params : {
    				},
    				method : 'POST',
    				form : editBasePlanForm.getForm().id,
    				waitMsg : '正在保存数据,请等待...', // 显示读盘的动画效果，执行完成后效果消失
    				success : function() {
    					 Ext.Ajax.request({
    				         url: basepath +'/market-activity!getPid.json',
    					         success:function(response){
    							 var mktActStr = Ext.util.JSON.decode(response.responseText).pid;
    							 editBasePlanForm.form.findField('mktActiId').setValue(mktActStr);
    							 if(ifGroup){//客户群生成营销活动，保存客户到目标客户表中
    								 Ext.Ajax.request({
    		    				         url: basepath +'/market-activity!saveCust.json',
    		    				         	params : {
    		    				        	 groupId:groupId,
    		    				        	 id:mktActStr
    		    		    				},
    		    					         success:function(response){
    		    					        	 Ext.Msg.alert('提示', '操作成功');
    		    						 	},
    		    						 	failure:function(){
    		    						 		Ext.Msg.alert('提示', '添加目标客户失败');
    		    						 	}
    		    						 });
    							 }else if(ifProd){//推荐产品生成营销活动，保存产品到目标产品表中  添加当前客户
    								 Ext.Ajax.request({
    		    				         url: basepath +'/market-activity!saveProd.json',
    		    				         	params : {
    		    				         	prodId:prodId,
    		    				         	prodName:prodName,
    		    				         	custId:custId,
    		    				         	custName:custName,
    		    				        	id:mktActStr
    		    		    				},
    		    					         success:function(response){
    		    					        	 Ext.Msg.alert('提示', '操作成功');
    		    						 	},
    		    						 	failure:function(){
    		    						 		Ext.Msg.alert('提示', '添加目标产品失败');
    		    						 	}
    		    						 });
    							 }else{
    								 Ext.Msg.alert('提示', '操作成功'); 
    							 }
    					         }
    						 });
    				},
    				failure : function(response) {
    					var resultArray = Ext.util.JSON.decode(response.status);
    				       if(resultArray == 403) {
    				           Ext.Msg.alert('系统提示', response.responseText);
    				  } else{

    					Ext.Msg.alert('提示', '操作失败,失败原因:' + response.responseText);
    				}
    				}
    			});
    		});
    		}
    		}, {
    			text : '下一步',
    			handler : function() {
    			if(''==editBasePlanForm.form.findField('mktActiId').getValue()){
    				 Ext.Msg.alert('提示', '填写营销活动基本信息并点击保存！');
    				 return false;
    			}
    			Ext.getCmp('jbxx').collapse();
    			Ext.getCmp('glcpxx').expand();
    			if(!_sheetVisible){//判定是否为新增操作，是则不显示其他页签，反之亦然
    			Ext.getCmp('jbxx').hide();
    			Ext.getCmp('glcpxx').show();
    			}
    			productContrastStore.load(
    			{
    				params : {
                         start : 0,
                         limit : prod_bbar.pageSize
                             }
                 });
    			}
    		}, {
    			text : '关  闭',
    			handler : function() {
    			editPlanWindow.hide();
    			}
    		} ]
        }, {
            xtype : 'fieldset',
            title : '关联产品信息',
            collapsed:true,
			collapsible : true,
			id:'glcpxx',
//			autoHeight : true,
            layout : 'fit',
            buttonAlign : "center",
            items : [productContrastGrid],
            buttons : [{
    			text : '上一步',
    			handler : function() {
    			Ext.getCmp('glcpxx').collapse();
    			Ext.getCmp('jbxx').expand();
    				if(!_sheetVisible){//判定是否为新增操作，是则不显示其他页签，反之亦然
    			Ext.getCmp('glcpxx').hide();
    			Ext.getCmp('jbxx').show();
    				}
    			}
    		},{
    			text : '下一步',
    			handler : function() {
    			if(''==editBasePlanForm.form.findField('mktActiId').getValue()){
   				 Ext.Msg.alert('提示', '填写营销活动基本信息并点击保存！');
   				 return false;
   			}
    			Ext.getCmp('glcpxx').collapse();
    			Ext.getCmp('glkkxx').expand();
    				if(!_sheetVisible){//判定是否为新增操作，是则不显示其他页签，反之亦然
    			Ext.getCmp('glcpxx').hide();
    			Ext.getCmp('glkkxx').show();
    				}
    			custContrastStore.load({
					  params : {
					  start : 0,
					  limit : cust_bbar.pageSize
		  }
		  });
    			}
    		}, {
    			text : '关  闭',
    			handler : function() {
    			editPlanWindow.hide();
    			}
    		} ]
        }, {
            xtype : 'fieldset',
            title : '关联客户信息',
            collapsed:true,
        	id:'glkkxx',
			collapsible : true,
//			autoHeight : true,
            layout : 'fit',
            buttonAlign : "center",
            items : [custContrastGrid],
            buttons : [{
    			text : '上一步',
    			handler : function() {
    			Ext.getCmp('glkkxx').collapse();
    			Ext.getCmp('glcpxx').expand();
    				if(!_sheetVisible){//判定是否为新增操作，是则不显示其他页签，反之亦然
    			Ext.getCmp('glkkxx').hide();
    			Ext.getCmp('glcpxx').show();
    				}
    			productContrastStore.load( 
    				{
                     params : {
                     start : 0,
                     limit : prod_bbar.pageSize
                     }
                 });
    			}
    		},{
    			text : '下一步',
    			handler : function() {
    			if(''==editBasePlanForm.form.findField('mktActiId').getValue()){
   				 Ext.Msg.alert('提示', '填写营销活动基本信息并点击保存！');
   				 return false;
   			}
    			Ext.getCmp('glkkxx').collapse();
    			Ext.getCmp('glqdxx').expand();
    				if(!_sheetVisible){//判定是否为新增操作，是则不显示其他页签，反之亦然
    			Ext.getCmp('glkkxx').hide();
    			Ext.getCmp('glqdxx').show();
    				}
    			chanelContrastStore.load(
    				{ 
    				 params : {
                     start : 0,
                     limit : chanel_bbar.pageSize
                 }
             });
    			}
    		}, {
    			text : '关  闭',
    			handler : function() {
    			editPlanWindow.hide();
    			}
    		} ]
        }, {
            xtype : 'fieldset',
            title : '关联渠道信息',
            collapsed:true,
			collapsible : true,
			id:'glqdxx',
//			autoHeight : true,
			 buttonAlign : "center",
            layout : 'fit',
            items : [chanelContrastGrid],
            buttons : [{
    			text : '上一步',
    			handler : function() {
    			Ext.getCmp('glqdxx').collapse();
    			Ext.getCmp('glkkxx').expand();
    				if(!_sheetVisible){//判定是否为新增操作，是则不显示其他页签，反之亦然
    			Ext.getCmp('glqdxx').hide();
    			Ext.getCmp('glkkxx').show();
    				}
    			custContrastStore.load({
				  params : {
				  start : 0,
				  limit : cust_bbar.pageSize
			  }
			  });
    			}
    		},{
    			text : '下一步',
    			handler : function() {
    			if(''==editBasePlanForm.form.findField('mktActiId').getValue()){
   				 Ext.Msg.alert('提示', '填写营销活动基本信息并点击保存！');
   				 return false;
   			}
    			//&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&
    	    	var messageIdStr = editBasePlanForm.form.findField('mktActiId').getValue();
    	        uploadForm.relaId = messageIdStr;
    	        uploadForm.modinfo = 'infomation';
    	        var condi = {};
    	        condi['relationInfo'] = messageIdStr;
    	        condi['relationMod'] = 'infomation';
    	        Ext.Ajax.request({
    	            url:basepath+'/queryanna.json',
    	            method : 'GET',
    	            params : {
    	                "condition":Ext.encode(condi)
    	            },
    	            failure : function(a,b,c){
    	                Ext.MessageBox.alert('查询异常', '查询失败！');
    	            },
    	            success : function(response){
    	                var anaExeArray = Ext.util.JSON.decode(response.responseText);
    	                appendixStore.loadData(anaExeArray.json.data);
    	            }
    	        });
    			Ext.getCmp('glqdxx').collapse();
    			Ext.getCmp('fjxx').expand();
    				if(!_sheetVisible){//判定是否为新增操作，是则不显示其他页签，反之亦然
    			Ext.getCmp('glqdxx').hide();
    			Ext.getCmp('fjxx').show();
    				}
    			}
    		}, {
    			text : '关  闭',
    			handler : function() {
    			editPlanWindow.hide();
    			}
    		} ]
        }, {
            xtype : 'fieldset',
            title : '附件信息',
            collapsed:true,
			collapsible : true,
			id:'fjxx',
			buttonAlign : "center",
            layout : 'fit',
            items : [appendixGridPanel2],
            buttons : [{
    			text : '上一步',
    			handler : function() {
    			Ext.getCmp('fjxx').collapse();
    			Ext.getCmp('glqdxx').expand();
    				if(!_sheetVisible){//判定是否为新增操作，是则不显示其他页签，反之亦然
    			Ext.getCmp('fjxx').hide();
    			Ext.getCmp('glqdxx').show();
    				}
    			chanelContrastStore.load({ 
    			params : 
    				{
                     start : 0,
                     limit : chanel_bbar.pageSize
                 }
             });
    			}
    		},{
    			text : '下一步',
    			handler : function() {
    			Ext.getCmp('fjxx').collapse();
    			Ext.getCmp('spxx').expand();
    			if(!_sheetVisible){//判定是否为新增操作，是则不显示其他页签，反之亦然
    			Ext.getCmp('fjxx').hide();
    			Ext.getCmp('spxx').show();
    				}
    			approveHistoryStore.load();
    			}
    		}, {
    			text : '关  闭',
    			handler : function() {
    			editPlanWindow.hide();
    			}
    		} ]
        }, {
            xtype : 'fieldset',
            title : '审批信息',
            collapsed:true,
			collapsible : true,
			id:'spxx',
			buttonAlign : "center",
            layout : 'fit',
            items : [approveHistoryGrid],
            buttons : [{
    			text : '上一步',
    			handler : function() {
    			Ext.getCmp('spxx').collapse();
    			Ext.getCmp('fjxx').expand();
    			if(!_sheetVisible){//判定是否为新增操作，是则不显示其他页签，反之亦然
    			Ext.getCmp('spxx').hide();
    			Ext.getCmp('fjxx').show();
    				}
    			var messageIdStr = editBasePlanForm.form.findField('mktActiId').getValue();
    	        uploadForm.relaId = messageIdStr;
    	        uploadForm.modinfo = 'infomation';
    	        var condi = {};
    	        condi['relationInfo'] = messageIdStr;
    	        condi['relationMod'] = 'infomation';
    	        Ext.Ajax.request({
    	            url:basepath+'/queryanna.json',
    	            method : 'GET',
    	            params : {
    	                "condition":Ext.encode(condi)
    	            },
    	            failure : function(a,b,c){
    	                Ext.MessageBox.alert('查询异常', '查询失败！');
    	            },
    	            success : function(response){
    	                var anaExeArray = Ext.util.JSON.decode(response.responseText);
    	                appendixStore.loadData(anaExeArray.json.data);
    	            }
    	        });
    			}
    		}, {
    			text : '关  闭',
    			handler : function() {
    			editPlanWindow.hide();
    			}
    		} ]
        }
        ],
        listeners : {
		beforeshow : function(){
    	//判定，当目标客户来源只有产品关联客户时，禁止启用关联客户维护界面的新增按钮。
    	if(__aimCustSource.substring(0, 2)=='00'){
        	Ext.getCmp('__aimCustAdd').setVisible(false);	
    	}
    	//end
    	custContrastGrid.tbar.setDisplayed(_buttonVisible);
    	chanelContrastGrid.tbar.setDisplayed(_buttonVisible);
    	productContrastGrid.tbar.setDisplayed(_buttonVisible);
    	appendixGridPanel2.tbar.setDisplayed(_buttonVisible);
    	Ext.getCmp('jbxx').buttons[0].setVisible(_buttonVisible);
    	if(__mktAppType=='00'){
        	Ext.getCmp('fjxx').buttons[1].setVisible(!_buttonVisible);	
    	}else{
    		Ext.getCmp('fjxx').buttons[1].setVisible(false);		
    	}
    	if(__aimCustSource.substring(0, 2)=='11'){
    	custContrastForm.form.findField('custNameStr').setVisible(false);
   		custContrastForm.form.findField('custGroup').setVisible(false);
    	}
    	//产品信息store
    	productContrastStore.on('beforeload', function() {
    		this.baseParams = {
    				mktActiId:editBasePlanForm.form.findField('mktActiId').getValue(),
    				querysign:'prod'
    		};
    	});
    	productContrastStore.load({
    		params : {
    			start : 0,
    			limit : prod_bbar.pageSize
    		}
    	});
    	//客户信息store
    	custContrastStore.on('beforeload', function() {
    		this.baseParams = {
    				mktActiId:editBasePlanForm.form.findField('mktActiId').getValue(),
    				querysign:'customer'
    		};
    	});
    	custContrastStore.load({
    		params : {
    			start : 0,
    			limit : cust_bbar.pageSize
    		}
    	});
    	//渠道信息store
    	chanelContrastStore.on('beforeload', function() {
    		this.baseParams = {
    				mktActiId:editBasePlanForm.form.findField('mktActiId').getValue(),
    				querysign:'chanel'
    		};
    	});
    	chanelContrastStore.load({
    		params : {
    			start : 0,
    			limit : chanel_bbar.pageSize
    		}
    	});
    	//审批信息store
    	approveHistoryStore.on('beforeload', function() {
    		approveHistoryStore.proxy.setUrl(basepath+'/mktapphistoryqueryaction.json?mktId='+editBasePlanForm.form.findField('mktActiId').getValue());
    	});
    	approveHistoryStore.load();
    	//附件信息
    	var messageIdStr = editBasePlanForm.form.findField('mktActiId').getValue();
    	        uploadForm.relaId = messageIdStr;
    	        uploadForm.modinfo = 'infomation';
    	        var condi = {};
    	        condi['relationInfo'] = messageIdStr;
    	        condi['relationMod'] = 'infomation';
    	        Ext.Ajax.request({
    	            url:basepath+'/queryanna.json',
    	            method : 'GET',
    	            params : {
    	                "condition":Ext.encode(condi)
    	            },
    	            failure : function(a,b,c){
    	                Ext.MessageBox.alert('查询异常', '查询失败！');
    	            },
    	            success : function(response){
    	                var anaExeArray = Ext.util.JSON.decode(response.responseText);
    	                appendixStore.loadData(anaExeArray.json.data);
    	            }
    	        });
    	
    	//start 调整在点击修改界面时出现的展示问题。
    	Ext.getCmp('jbxx').expand();
		Ext.getCmp('glcpxx').collapse();
		Ext.getCmp('glkkxx').collapse();
		Ext.getCmp('glqdxx').collapse();
		Ext.getCmp('fjxx').collapse();
		Ext.getCmp('spxx').collapse();
    	//end
    	
		}}
    });