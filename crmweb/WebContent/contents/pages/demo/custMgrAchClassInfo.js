Ext.onReady(function() {
	
	//机构树
	Ext.override(
					Ext.form.ComboBox,
					{
						onViewClick : function(doFocus) {
							var index = this.view.getSelectedIndexes()[0], s = this.store, r = s.getAt(index);
							if (r) {
								this.onSelect(r, index);
							} else if (s.getCount() == 0) {
								this.collapse();

							}
							if (doFocus !== false) {
								this.el.focus();
							}
						}
					});
	// 递归收起子节点
	function childCollapse(node) {
		node.eachChild(function(currNode) {
			if (!currNode.isLeaf()) {
				currNode.collapse();
				childCollapse(currNode);
			}
		});
	}
	// 归属机构编号
	function checkId(orgTreePanel_p) {
		var orgIdStr = '';
		var checkedOrgs = orgTreePanel_p.getChecked();
		for ( var i = 0; i < checkedOrgs.length; i++) {
			orgIdStr += checkedOrgs[i].id;
			if (i != checkedOrgs.length - 1) {
				orgIdStr += ",";
			}
		}
		return orgIdStr;
	}
//	// 归属机构编号
//	function checkId(orgTreePanel_p) {
//		var orgIdStr = '';
//		var checkedOrgs = orgTreePanel_p.getChecked();
//		for ( var i = 0; i < checkedOrgs.length; i++) {
//			if(checkedOrgs[i].id=='100000'){
//				orgIdStr += checkedOrgs[i].id;
//			}else
//			{
//			orgIdStr += (checkedOrgs[i].id).substring(0, (checkedOrgs[i].id).length-1);
//			}
//			if (i != checkedOrgs.length - 1) {
//				orgIdStr += ",";
//			}
//		}
//		return orgIdStr;
//	}
	// 归属机构名称
	function checkText(orgTreePanel_p) {
		var orgStr = '';
		var checkedOrgs = orgTreePanel_p.getChecked();
		for ( var i = 0; i < checkedOrgs.length; i++) {
			orgStr += checkedOrgs[i].text;
			if (i != checkedOrgs.length - 1) {
				orgStr += ";";
			}
		}
		return orgStr;
	}
	function getCheckedStr(checkedStr, node) {
		node.eachChild(function(tempNode) {
			if (tempNode != null) {
				if (tempNode.getUI().isChecked()) {
					var idCode = tempNode.id;
					var id = idCode.substring(0, idCode.length - 1);
					checkedStr = checkedStr + "'" + id + "',";
					checkedStr = getCheckedStr(checkedStr, tempNode);
					return checkedStr;
				} else {
					checkedStr = getCheckedStr(checkedStr, tempNode);
					return checkedStr;
				}
			}
		});

		return checkedStr;
	}
	function getCheckedNode(orgTreePanel_p) {
		var rootNode = orgTreePanel_p.root;
		var checkedStr = "";
		if (rootNode.getUI().isChecked()) {
			var idCode = rootNode.id;
			var id_2 = idCode.substring(0, idCode.length - 1);
			checkedStr = checkedStr + "'" + id_2 + "'" + ",";
		}
		checkedStr = getCheckedStr(checkedStr, rootNode);

		return checkedStr.substring(0, checkedStr.length - 1);
	}

	var orgTreePanel6 = new Ext.tree.TreePanel( {
		autoScroll : true,
		height : 350,
		width : 200,
		listeners : {
			'checkchange' : function(node, checked) {
				if (checked) {
					var childNodes = node.childNodes;
					for ( var i = 0; i < childNodes.length; i++) {
						childNodes[i].getUI().toggleCheck(true);/*
																 * var
																 * checkedNodes =
																 * getCheckedNode(orgTreePanel1);
																 */
					}
				} else {
					var childNodes = node.childNodes;
					for ( var i = 0; i < childNodes.length; i++) {
						childNodes[i].getUI().toggleCheck(false);/*
																	 * var
																	 * checkedNodes =
																	 * getCheckedNode(orgTreePanel1);
																	 */
					}
				}
			},
			'dblclick' : function(node) {
				if (node.getUI().isChecked()) {
					node.getUI().toggleCheck(false); /*
														 * var
														 * checkedNodes =
														 * getCheckedNode(orgTreePanel1);
														 */
				} else {
					node.getUI().toggleCheck(true); /*
													 * var checkedNodes =
													 * getCheckedNode(orgTreePanel1);
													 */
				}
			}
		},
		root : new Ext.tree.AsyncTreeNode( {
			// id:orgId,
			// text:orgName,
			id:JsContext._orgId,
			text : JsContext._unitname,
			autoScroll : true,
			expanded : true,
			leaf : false,
			checked : false,
			loader : new Ext.tree.TreeLoader( {
				url : basepath + '/system-unit-recursive1.json',
				requestMethod : 'GET',
				listeners : {
					'load' : function() {
						var rootNode = orgTreePanel6.root;
						rootNode.eachChild(function(node) {
							if (!node.isLeaf()) {
								node.collapse();
								childCollapse(node);
							}
						});
					}
				}
			})
		}),
		animate : false,
		useArrows : false,
		border : false
	});

	var instnCombo6 = new Ext.form.ComboBox(
			{
				xtype : 'combo',
				store : new Ext.data.SimpleStore( {
					fields : [],
					data : [ [] ]
				}),
				name : 'groupHostOrgNoName6',
				id : 'groupHostOrgNoName6',
				emptyText : '请选择',
				resizable : false,
				labelStyle : 'text-align:right;',
				fieldLabel : '<font color=red>*</font>机构',
				anchor : '90%',
				editable : false,
				allowBlank : true,
				mode : 'local',
				triggerAction : 'all',
				checked : false,
				maxHeight : 390,
				// 下拉框的显示模板,addDeptTreeDiv作为显示下拉树的容器
				tpl : "<tpl for='.' <div style='height:390px'> <div id='addOrgTreeDivForAdd6'></div></div></tpl>",
				onSelect : Ext.emptyFn,
				listeners : {
					'expand' : function(combo) {
						orgTreePanel6.render('addOrgTreeDivForAdd6');
					},
					'collapse' : function(combo) {
						var checkedString = checkText(orgTreePanel6);
						combo.setValue(checkedString);
						var checkedId = checkId(orgTreePanel6);
						qForm.getForm().findField("instncode").setValue(checkedId);
						
					}
				}
			});
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
	var searchStore = new Ext.data.ArrayStore({
		fields:['key','value'],
	    data:[['1','机构'],['2','经理名称'],['3','吸存号']]
	});
	//分类指标
	 var simple = new Ext.FormPanel({
	        frame:true,
			title : '<span style="font-weight:normal">分类查询</span>',
	        bodyStyle:'padding:5px 5px 0',
	        autoScroll : true,
	        width: 800,
	        items: [{
				autoHeight:true,
				xtype : 'fieldset',
				title : '分类指标',
				titleCollapse : true,
				collapsible : true,
				layout : "column", // 也可以是table,实现多列布局
				items : [{
					columnWidth : .20,
					xtype : 'checkboxgroup',
					id : 'cg1',
					fieldLabel : 'Multi-Column (horizontal)',
					items : [{
						id : 'cgb1',
						boxLabel : '一般性存款',
						inputValue:'nodeppubfunds',
						name : 'cb-auto-3-1'
					}]
				}, {
					columnWidth : .20,
					layout : 'form',
					items : [ {
						xtype : 'checkbox',
						boxLabel : '本月日均',
						name:'sum1',
						value:' ',
						labelStyle: 'text-align:right;',
						anchor : '95%'
					}]
				}, {
					columnWidth : .20,
					layout : 'form',
					items : [ {
						id : 'startDate1',
						xtype : 'checkbox',
						boxLabel : '本季日均',
						format : 'Y-m-d',
						name:'startDate1',
						value:' ',
						labelStyle: 'text-align:right;',
						anchor : '95%'
					}]
				}, {
					columnWidth : .20,
					layout : 'form',
					items : [ {
						id : 'endDate1',
						xtype : 'checkbox',
						boxLabel : '累计日均',
						format : 'y-m-d',
						name:'endDate1',
						value:' ',
						labelStyle: 'text-align:right;',
						anchor : '95%'
					}]
				}, {
					columnWidth : .20,
					layout : 'form',
					items : [ {
						id : 'interestRate1',
						xtype : 'checkbox',
						boxLabel : '本日余额',
						name:'interestRate1',
						value:' ',
						labelStyle: 'text-align:right;',
						anchor : '95%'
					}]
				} ]
			},{
				xtype : "panel",
				layout : "column", // 也可以是table,实现多列布局
				items : [{
					columnWidth : .20,
					xtype : 'checkboxgroup',
					id : 'cg1_1',
					fieldLabel : 'Multi-Column (horizontal)',
					items : [{
						id : 'cgb1_1',
						boxLabel : '对公存款',
						inputValue:'nodeppubfunds',
						name : 'cb-auto-3-1'
					}]
				}, {
					columnWidth : .20,
					layout : 'form',
					items : [ {
						id : 'sum1_1',
						xtype : 'checkbox',
						boxLabel : '本月日均',
						name:'sum1_1',
						value:' ',
						labelStyle: 'text-align:right;',
						anchor : '95%'
					}]
				}, {
					columnWidth : .20,
					layout : 'form',
					items : [ {
						id : 'startDate1_1',
						xtype : 'checkbox',
						boxLabel : '本季日均',
						format : 'Y-m-d',
						name:'startDate1_1',
						value:' ',
						labelStyle: 'text-align:right;',
						anchor : '95%'
					}]
				}, {
					columnWidth : .20,
					layout : 'form',
					items : [ {
						id : 'endDate1_1',
						xtype : 'checkbox',
						boxLabel : '累计日均',
						format : 'y-m-d',
						name:'endDate1_1',
						value:' ',
						labelStyle: 'text-align:right;',
						anchor : '95%'
					}]
				}, {
					columnWidth : .20,
					layout : 'form',
					items : [ {
						id : 'interestRate1_1',
						xtype : 'checkbox',
						boxLabel : '本日余额',
						name:'interestRate1_1',
						value:' ',
						labelStyle: 'text-align:right;',
						anchor : '95%'
					}]
				} ]
			},{
				xtype : "panel",
				layout : "column", // 也可以是table,实现多列布局
				items : [{
					columnWidth : .20,
					xtype : 'checkboxgroup',
					id : 'cg1_2',
					fieldLabel : 'Multi-Column (horizontal)',
					items : [{
						id : 'cgb1_2',
						boxLabel : '储蓄存款',
						inputValue:'nodeppubfunds',
						name : 'cb-auto-3-1'
					}]
				}, {
					columnWidth : .20,
					layout : 'form',
					items : [ {
						id : 'sum1_2',
						xtype : 'checkbox',
						boxLabel : '本月日均',
						name:'sum1_2',
						value:' ',
						labelStyle: 'text-align:right;',
						anchor : '95%'
					}]
				}, {
					columnWidth : .20,
					layout : 'form',
					items : [ {
						id : 'startDate1_2',
						xtype : 'checkbox',
						boxLabel : '本季日均',
						format : 'Y-m-d',
						name:'startDate1_2',
						value:' ',
						labelStyle: 'text-align:right;',
						anchor : '95%'
					}]
				}, {
					columnWidth : .20,
					layout : 'form',
					items : [ {
						id : 'endDate1_2',
						xtype : 'checkbox',
						boxLabel : '累计日均',
						format : 'y-m-d',
						name:'endDate1_2',
						value:' ',
						labelStyle: 'text-align:right;',
						anchor : '95%'
					}]
				}, {
					columnWidth : .20,
					layout : 'form',
					items : [ {
						id : 'interestRate1_2',
						xtype : 'checkbox',
						boxLabel : '本日余额',
						name:'interestRate1_2',
						value:' ',
						labelStyle: 'text-align:right;',
						anchor : '95%'
					}]
				} ]
			},{
				xtype : "panel",
				layout : "column", // 也可以是table,实现多列布局
				items : [{
					columnWidth : .20,
					xtype : 'checkboxgroup',
					id : 'cg2',
					fieldLabel : 'Multi-Column (horizontal)',
					items : [{
						id : 'cgb2_1',
						boxLabel : '纯贷款',
						inputValue:'deppubfunds',
						name : 'cb-auto-3-2'
					}]
				}, {
					columnWidth : .20,
					layout : 'form',
					items : [ {
						id : 'sum2',
						xtype : 'checkbox',
						boxLabel : '本月日均',
						name:'sum2',
						value:' ',
						labelStyle: 'text-align:right;',
						anchor : '95%'
					}]
				}, {
					columnWidth : .20,
					layout : 'form',
					items : [ {
						id : 'startDate2',
						xtype : 'checkbox',
						boxLabel : '本季日均',
						format : 'Y-m-d',
						name:'startDate2',
						value:' ',
						labelStyle: 'text-align:right;',
						anchor : '95%'
					}]
				}, {
					columnWidth : .20,
					layout : 'form',
					items : [ {
						id : 'endDate2',
						xtype : 'checkbox',
						boxLabel : '累计日均',
						format : 'Y-m-d',
						name:'endDate2',
						value:' ',
						labelStyle: 'text-align:right;',
						anchor : '95%'
					}]
				}, {
					columnWidth : .20,
					layout : 'form',
					items : [ {
						id : 'interestRate2',
						xtype : 'checkbox',
						boxLabel : '本日余额',
						name:'interestRate2',
						value:' ',
						labelStyle: 'text-align:right;',
						anchor : '95%'
					}]
				} ]
			},{
				xtype : "panel",
				layout : "column", // 也可以是table,实现多列布局
				items : [{
					columnWidth : .20,
					xtype : 'checkboxgroup',
					id : 'cg2_2',
					fieldLabel : 'Multi-Column (horizontal)',
					items : [{
						id : 'cgb2_2',
						boxLabel : '非贸易融资单位贷款',
						inputValue:'deppubfunds',
						name : 'cb-auto-3-2'
					}]
				}, {
					columnWidth : .20,
					layout : 'form',
					items : [ {
						xtype : 'checkbox',
						boxLabel : '本月日均',
						name:'sum2',
						value:' ',
						labelStyle: 'text-align:right;',
						anchor : '95%'
					}]
				}, {
					columnWidth : .20,
					layout : 'form',
					items : [ {
						xtype : 'checkbox',
						boxLabel : '本季日均',
						format : 'Y-m-d',
						name:'startDate2',
						value:' ',
						labelStyle: 'text-align:right;',
						anchor : '95%'
					}]
				}, {
					columnWidth : .20,
					layout : 'form',
					items : [ {
						xtype : 'checkbox',
						boxLabel : '累计日均',
						format : 'Y-m-d',
						name:'endDate2',
						value:' ',
						labelStyle: 'text-align:right;',
						anchor : '95%'
					}]
				}, {
					columnWidth : .20,
					layout : 'form',
					items : [ {
						xtype : 'checkbox',
						boxLabel : '本日余额',
						name:'interestRate2',
						value:' ',
						labelStyle: 'text-align:right;',
						anchor : '95%'
					}]
				} ]
			},{
				xtype : "panel",
				layout : "column", // 也可以是table,实现多列布局
				items : [{
					columnWidth : .20,
					xtype : 'checkboxgroup',
					id : 'cg2_3',
					fieldLabel : 'Multi-Column (horizontal)',
					items : [{
						id : 'cgb2_3',
						boxLabel : '贸易融资贷款',
						inputValue:'deppubfunds',
						name : 'cb-auto-3-2'
					}]
				}, {
					columnWidth : .20,
					layout : 'form',
					items : [ {
						xtype : 'checkbox',
						boxLabel : '本月日均',
						name:'sum2',
						value:' ',
						labelStyle: 'text-align:right;',
						anchor : '95%'
					}]
				}, {
					columnWidth : .20,
					layout : 'form',
					items : [ {
						xtype : 'checkbox',
						boxLabel : '本季日均',
						format : 'Y-m-d',
						name:'startDate2',
						value:' ',
						labelStyle: 'text-align:right;',
						anchor : '95%'
					}]
				}, {
					columnWidth : .20,
					layout : 'form',
					items : [ {
						xtype : 'checkbox',
						boxLabel : '累计日均',
						format : 'Y-m-d',
						name:'endDate2',
						value:' ',
						labelStyle: 'text-align:right;',
						anchor : '95%'
					}]
				}, {
					columnWidth : .20,
					layout : 'form',
					items : [ {
						xtype : 'checkbox',
						boxLabel : '本日余额',
						name:'interestRate2',
						value:' ',
						labelStyle: 'text-align:right;',
						anchor : '95%'
					}]
				} ]
			},{
				xtype : "panel",
				layout : "column", // 也可以是table,实现多列布局
				items : [{
					columnWidth : .20,
					xtype : 'checkboxgroup',
					id : 'cg2_4',
					fieldLabel : 'Multi-Column (horizontal)',
					items : [{
						id : 'cgb2_4',
						boxLabel : '个人贷款',
						inputValue:'deppubfunds',
						name : 'cb-auto-3-2'
					}]
				}, {
					columnWidth : .20,
					layout : 'form',
					items : [ {
						xtype : 'checkbox',
						boxLabel : '本月日均',
						name:'sum2',
						value:' ',
						labelStyle: 'text-align:right;',
						anchor : '95%'
					}]
				}, {
					columnWidth : .20,
					layout : 'form',
					items : [ {
						xtype : 'checkbox',
						boxLabel : '本季日均',
						format : 'Y-m-d',
						name:'startDate2',
						value:' ',
						labelStyle: 'text-align:right;',
						anchor : '95%'
					}]
				}, {
					columnWidth : .20,
					layout : 'form',
					items : [ {
						xtype : 'checkbox',
						boxLabel : '累计日均',
						format : 'Y-m-d',
						name:'endDate2',
						value:' ',
						labelStyle: 'text-align:right;',
						anchor : '95%'
					}]
				}, {
					columnWidth : .20,
					layout : 'form',
					items : [ {
						xtype : 'checkbox',
						boxLabel : '本日余额',
						name:'interestRate2',
						value:' ',
						labelStyle: 'text-align:right;',
						anchor : '95%'
					}]
				} ]
			}, {
				xtype : "panel",
				layout : "column", // 也可以是table,实现多列布局
				items : [{
					columnWidth : .20,
					xtype : 'checkboxgroup',
					id : 'cg7',
					fieldLabel : 'Multi-Column (horizontal)',
					items : [{
						id : 'cgb7',
						boxLabel : '同业存款',
						inputValue:'issuedpromissory',
						name : 'cb-auto-3-7'
					}]
				}, {
					columnWidth : .20,
					layout : 'form',
					items : [ {
						id : 'sum7',
						xtype : 'checkbox',
						boxLabel : '本月累计',
						name:'sum7',
						value:' ',
						labelStyle: 'text-align:right;',
						anchor : '95%'
					}]
				}, {
					columnWidth : .20,
					layout : 'form',
					items : [ {
						id : 'startDate7',
						xtype : 'checkbox',
						boxLabel : '本季累计',
						format : 'Y-m-d',
						name:'startDate7',
						value:' ',
						labelStyle: 'text-align:right;',
						anchor : '95%'
					}]
				}, {
					columnWidth : .20,
					layout : 'form',
					items : [ {
						id : 'endDate7',
						xtype : 'checkbox',
						boxLabel : '本年累计',
						format : 'Y-m-d',
						name:'endDate7',
						value:' ',
						labelStyle: 'text-align:right;',
						anchor : '95%'
					}]
				}]
			},{
				xtype : "panel",
				layout : "column", // 也可以是table,实现多列布局
				items : [{
					columnWidth : .20,
					xtype : 'checkboxgroup',
					id : 'cg8',
					fieldLabel : 'Multi-Column (horizontal)',
					items : [{
						id : 'cgb8',
						boxLabel : '经办业务量（主辅办）',
						inputValue:'openlettercredit',
						name : 'cb-auto-3-8'
					}]
				},  {
					columnWidth : .20,
					layout : 'form',
					items : [ {
						id : 'startDate8',
						xtype : 'checkbox',
						boxLabel : '本季累计',
						format : 'Y-m-d',
						//hidden : true,
						name:'startDate8',
						value:' ',
						labelStyle: 'text-align:right;',
						anchor : '95%'
					}]
				}, {
					columnWidth : .20,
					layout : 'form',
					items : [ {
						id : 'endDate8',
						xtype : 'checkbox',
						boxLabel : '本年累计',
						format : 'Y-m-d',
						//hidden : true,
						name:'endDate8',
						value:' ',
						labelStyle: 'text-align:right;',
						anchor : '95%'
					}]
				}]
			},{
				xtype : "panel",
				layout : "column", // 也可以是table,实现多列布局
				items : [{
					columnWidth : .20,
					xtype : 'checkboxgroup',
					id : 'cg10',
					fieldLabel : 'Multi-Column (horizontal)',
					items : [{
						id : 'cgb10',
						boxLabel : '客户',
						inputValue:'lettersguarantee',
						name : 'cb-auto-3-9'
					}]
				}, {
					columnWidth : .20,
					layout : 'form',
					items : [ {
						xtype : 'checkbox',
						boxLabel : '本月累计',
						//hidden : true,
						name:'sum9',
						value:' ',
						labelStyle: 'text-align:right;',
						anchor : '95%'
					}]
				}, {
					columnWidth : .20,
					layout : 'form',
					items : [ {
						xtype : 'checkbox',
						boxLabel : '本季累计',
						//hidden : true,
						format : 'Y-m-d',
						name:'startDate9',
						value:' ',
						labelStyle: 'text-align:right;',
						anchor : '95%'
					}]
				}, {
					columnWidth : .20,
					layout : 'form',
					items : [ {
						xtype : 'checkbox',
						boxLabel : '本年累计',
						//hidden : true,
						format : 'Y-m-d',
						name:'endDate9',
						value:' ',
						labelStyle: 'text-align:right;',
						anchor : '95%'
					}]
				}]
			}]
	    });
	 //分类查询的Window
		var classifyQWindow = new Ext.Window(
			{
				layout : 'fit',
				width : 1000,
				height : 420,
				draggable : true,//是否可以拖动
				closable : true,// 是否可关闭
				modal : true,
				closeAction : 'hide',
				titleCollapse : true,
				buttonAlign : 'center',
				border : false,
				animCollapse : true,
				animateTarget : Ext.getBody(),
				constrain : true,
				items : [simple],
				buttons : [
						{
							text : '查询',
							handler : function() {
							selectClass();
								classifyQWindow.hide();
							}
						}, {
							text : '重置',
							handler : function() {
								simple.getForm().reset();
								orgTreePanel.root.getUI().toggleCheck(false);
							}
						}, {
							text : '关闭',
							handler : function() {
								classifyQWindow.hide();
							}
						} ]
			});
		
		 
		function selectClass(){
			var dates = Ext.getCmp('CHECK_DATE').getValue();
			
		    var dDate=Ext.util.Format.date(dates,'Y-m-d');
		    var cbOrgTree = Ext.getCmp('groupHostOrgNoName6').getValue();//机构树NAME
		    var cbCustMgrName = Ext.getCmp('custMgrName').getValue();//客户经理名称		    
		    var cbBizh = Ext.getCmp('bizh').getValue();//币种
		    var org_diString = checkId(orgTreePanel6);//机构ID
		    
		    if(org_diString=='' && cbCustMgrName=='')
			{
				Ext.Msg.alert("提醒","请选择机构或者客户经理");
	    	    return false;
	    	}
		    
		    
		    var winWidth = screen.width - 10;
				var winHeight = screen.height - 60;
				var winFeatures = "toolbar=no,location=no,directories=no,status=yes,menubar=no,scrollbars=yes,resizable=yes,";
				winFeatures += "top=0,left=0,height="
						+ winHeight + ",width=" + winWidth;
				var url=url=basepath+'/reportJsp/showReport.jsp?raq=/CM102_10.raq&searchType=0'+'&qdate='+dDate+'&currency=156';
				if(Ext.getCmp('cgb10').checked==true)
				{
					Ext.getCmp('cgb10').setChecked==false;
					url=basepath+'/reportJsp/showReport.jsp?raq=/CM102_10.raq&searchType=0'+'&qdate='+dDate+'&currency=156';	
				}
				 
				 if(Ext.getCmp('cgb8').checked==true){
					 Ext.getCmp('cgb8').setChecked==false;
					url=basepath+'/reportJsp/showReport.jsp?raq=/CM102_11.raq&searchType=0'+'&qdate='+dDate+'&currency=156';	
				}
				 if(Ext.getCmp('cgb7').checked==true){
					 Ext.getCmp('cgb7').setChecked==false;
					url=basepath+'/reportJsp/showReport.jsp?raq=/CM102_8.raq&searchType=0'+'&qdate='+dDate+'&currency=156';	
				}
				 if(Ext.getCmp('cgb2_1').checked==true){
					 Ext.getCmp('cgb2_1').setChecked==false;
						url=basepath+'/reportJsp/showReport.jsp?raq=/CM102_4.raq&searchType=0'+'&qdate='+dDate+'&currency=156';	
					}
				 if(Ext.getCmp('cgb2_2').checked==true){
					 Ext.getCmp('cgb2_2').setChecked==false;
						url=basepath+'/reportJsp/showReport.jsp?raq=/CM102_5.raq&searchType=0'+'&qdate='+dDate+'&currency=156';	
					}
				 if(Ext.getCmp('cgb2_3').checked==true){
					 Ext.getCmp('cgb2_3').setChecked==false;
						url=basepath+'/reportJsp/showReport.jsp?raq=/CM102_6.raq&searchType=0'+'&qdate='+dDate+'&currency=156';	
					}
				 if(Ext.getCmp('cgb2_4').checked==true){
					 Ext.getCmp('cgb2_4').setChecked==false;
						url=basepath+'/reportJsp/showReport.jsp?raq=/CM102_7.raq&searchType=0'+'&qdate='+dDate+'&currency=156';	
					}
				 if(Ext.getCmp('cgb1').checked==true){
					 Ext.getCmp('cgb1').setChecked==false;
					url=basepath+'/reportJsp/showReport.jsp?raq=/CM102.raq&searchType=0'+'&qdate='+dDate+'&currency=156';	
				}
				 if(Ext.getCmp('cgb1_1').checked==true){
					 Ext.getCmp('cgb1_1').setChecked==false;
						url=basepath+'/reportJsp/showReport.jsp?raq=/CM102_2.raq&searchType=0'+'&qdate='+dDate+'&currency=156';	
					}
				 if(Ext.getCmp('cgb1_2').checked==true){
					 Ext.getCmp('cgb1_2').setChecked==false;
						url=basepath+'/reportJsp/showReport.jsp?raq=/CM102_3.raq&searchType=0'+'&qdate='+dDate+'&currency=156';	
					}
					
				 if(org_diString!='') url=url+'&org_id='+org_diString;
				 if(cbCustMgrName!='')url=url+'&mname='+cbCustMgrName;
				var winOpen = window.open(url,'chat' + new Date().getTime(),winFeatures);
			}
	 //查询Panel
	var qForm = new Ext.form.FormPanel({
		title: "客户经理业绩查询->汇总查询->分类业务查询", 
		labelWidth : 100, // 标签宽度
//		frame : true, //是否渲染表单面板背景色
		labelAlign : 'right', // 标签对齐方式
		bodyStyle : 'padding:19 19 0', // 表单元素和表单面板的边距
		region : 'north',
		buttonAlign : 'center',
		height : 180,
		layout : 'column',
		items : [{
			columnWidth : .25,
			layout : 'form',
			labelWidth : 80, // 标签宽度
			defaultType : 'textfield',
			border : false,							
			items : [{
				xtype:'datefield',
				format : 'Y-m-d',
				fieldLabel : '查询日期', // 标签
				name : 'CHECK_DATE', 
				id : 'CHECK_DATE', 
				allowBlank : false,
				anchor : '90%' // 宽度百分比
			}]
		},{
			columnWidth : .25,
			layout : 'form',
			labelWidth : 80, // 标签宽度
			border : false,
			items : [{
				xtype : 'combo',
				store : searchStore,
				fieldLabel : '查询条件',
				id:'searchtp',
				hiddenName : 'deposit_average',
				valueField : 'key',
				displayField : 'value',
				triggerAction : 'all',
				mode:'local',
				allowBlank : false,
				editable : false,
				emptyText : '请选择',
				labelStyle : 'text-align:right;',
				anchor : '90%',
				listeners :{'select' : function(){
				if(Ext.getCmp('searchtp').getValue()==1){
					Ext.getCmp('org').show();
					Ext.getCmp('xcID').hide();
					Ext.getCmp('customer').hide();
				}else if(Ext.getCmp('searchtp').getValue()==2){
					Ext.getCmp('customer').show();
					Ext.getCmp('xcID').hide();
					Ext.getCmp('org').hide();
				}else if(Ext.getCmp('searchtp').getValue()==3){
					Ext.getCmp('xcID').show();
					Ext.getCmp('customer').hide();
					Ext.getCmp('org').hide();
				}
			}}
			}]
		}, {
			columnWidth : .25,
			layout : 'form',
			labelWidth : 80, // 标签宽度
			defaultType : 'textfield',
			hidden :true,
			id :'org',
			border : false,
			items : [{
				xtype : 'textfield',
				fieldLabel : '所属机构',
				hidden : true,
				name:'instncode',
				anchor : '90%'
			},
			 instnCombo6]
		}, {
			columnWidth : .25,
			layout : 'form',
			labelWidth : 80, // 标签宽度
			id :'customer',
			hidden :true,
			border : false,
			items : [
					new Ext.ux.form.CustMgrField({ 
						fieldLabel : '所属客户经理', 
						id:'custMgrName',
						labelStyle: 'text-align:right;',
						name : 'CUST_MGR_NAME',
						anchor : '90%'
						})
					]
		},{
			columnWidth : .25,
			layout : 'form',
			labelWidth : 80, // 标签宽度
			defaultType : 'textfield',
			hidden :true,
			id :'xcID',
			border : false,
			items : [{
				fieldLabel : '吸存号', // 标签
				name : 'CUST_MGR_NO', 
				id : 'custMgrNo',
				xtype : 'textfield',
				listeners :{'change' : function(){
				if(Ext.getCmp('custMgrNo').getValue!=''){
					Ext.getCmp('custMgrName').hide();
				}else if(Ext.getCmp('custMgrName').getValue==''&&Ext.getCmp('custMgrNo').getValue==''){
					Ext.getCmp('custMgrName').show();
				}
			}},
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
			}]
				}],
		buttons : [
		           /* {
					text : '查询',
					handler : function() {
						
						if(!qForm.getForm().isValid()){
							Ext.Msg.alert("提醒","请填写必填项");
							return false;
						}
						select();
						}
				}, */{
					text : '分类指标',
					id :'classQButton',
					handler : function() {
					if(Ext.getCmp('searchtp').getValue()==''){
						Ext.Msg.alert("提示：","查询条件未填写！");
						return false;
					}else if(Ext.getCmp('CHECK_DATE').getValue()==''){
						Ext.Msg.alert("提示：","查询日期未填写！");
						return false;
					}
						classifyQWindow.show();
					}
				}, {
					text : '重置',
					handler : function() {
						qForm.getForm().reset();
						orgTreePanel6.root.getUI().toggleCheck(false);
					}
				}]
	});
	// 定义自动当前页行号
	var rownum = new Ext.grid.RowNumberer({
		header : 'No.',
		width : 28
	});
	
	var fpanel = new Ext.Panel({
		id : "fpanel",
		labelWidth : 90, // 标签宽度
		frame : true, //是否渲染表单面板背景色
		labelAlign : 'middle', // 标签对齐方式
		buttonAlign : 'center',
		items :[qForm]
		        });
	
	var viewport = new Ext.Viewport({
				layout : 'fit',
				items: [fpanel] 

			});
	
}); 