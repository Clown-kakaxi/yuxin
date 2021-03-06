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
			id:JsContext._orgId,  //
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
//				allowBlank : false,
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
	
	 var dataValStore = new Ext.data.ArrayStore({
	        fields:['key','value'],
	        data:[['1','累计较年初'],['2','累计较上季'],['3','累计较上月'],['4','累计较上日'],['5','累计比去年同期'],['6','本季较上季'],['7','本月较上月']]
	        });
		var searchStore = new Ext.data.ArrayStore({
			fields:['key','value'],
		    data:[['1','机构'],['2','经理名称'],['3','吸存号']]
		});
	var qForm = new Ext.form.FormPanel({
		title: "客户经理业绩查询->汇总查询->增量业务查询", 
		labelWidth : 140, // 标签宽度
//		frame : true, //是否渲染表单面板背景色
		labelAlign : 'right', // 标签对齐方式
		region : 'north',
		bodyStyle : 'padding:19 19 0', // 表单元素和表单面板的边距
		buttonAlign : 'center',
		height : 180,
					layout : 'column',
					border : false,
					items : [{
								columnWidth : .33,
								layout : 'form',
								labelWidth : 80, // 标签宽度
								border : false,
								items : [{
									xtype:'datefield',
									format : 'Y-m-d',
									fieldLabel : '查询日期', // 标签
									name : 'CHECK_DATE', 
									allowBlank : false,
									anchor : '90%' // 宽度百分比
								},{
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
							}, {
								columnWidth : .33,
								layout : 'form',
								labelWidth : 80, // 标签宽度
								border : false,
								items : [
								         {
									columnWidth : .33,
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
								},{
									xtype : 'combo',
									store : dataValStore,
									fieldLabel : '数据值',
									id :'dataVal',
									hiddenName : 'dataValue',
									triggerAction : 'all',
									valueField : 'key',
									displayField : 'value',
									editable : false,
									emptyText : '请选择',
									mode : 'local',
									anchor : '90%'
								}]
							},{
								columnWidth : .33,
								layout : 'form',
								labelWidth : 80, // 标签宽度
								defaultType : 'textfield',
								border : false,
								id:'customer',
								hidden :true,
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
								columnWidth : .33,
								layout : 'form',
								labelWidth : 80, // 标签宽度
								defaultType : 'textfield',
								border : false,
								id :'xcID',
								hidden :true,
								items : [
										{
											fieldLabel : '吸存号', // 标签
											name : 'CUST_MGR_NO', 
											id : 'custMgrNo',
											xtype : 'textfield',
											anchor : '90%' // 宽度百分比
										}]
							},{
								columnWidth : .33,
								layout : 'form',
								labelWidth : 80, // 标签宽度
								defaultType : 'textfield',
								border : false,
								id :'org',
								hidden : true,
								items : [
										{
											xtype : 'textfield',
											fieldLabel : '所属机构',
											name:'instncode',
											hidden :true,
											anchor : '90%'
										},
										 instnCombo6]
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
	function select(){
		var dates = qForm.getForm().findField('CHECK_DATE').getValue();
		var cbOrgTree = Ext.getCmp('groupHostOrgNoName6').getValue();//机构树NAME
	    var cbCustMgrName = Ext.getCmp('custMgrName').getValue();//客户经理名称
	    var cbCustMgrNo = Ext.getCmp('custMgrNo').getValue();//吸存号
	    var cbBizh = Ext.getCmp('bizh').getValue();//币种
	    var cbDataVal = Ext.getCmp('dataVal').getValue();//数据值
		var org_diString = checkId(orgTreePanel6);//机构ID
	    var dDate=Ext.util.Format.date(dates,'Y-m-d');

	    var winWidth = screen.width - 10;
			var winHeight = screen.height - 60;
			var winFeatures = "toolbar=no,location=no,directories=no,status=yes,menubar=no,scrollbars=yes,resizable=yes,";
			winFeatures += "top=0,left=0,height="
					+ winHeight + ",width=" + winWidth;
			if(dates=='') dDate='2012-08-26';
			if(org_diString=='' && cbCustMgrName=='')
			{
				Ext.Msg.alert("提醒","请选择机构或者客户经理");
	    	return false;
	    	}
			var url=basepath+'/reportJsp/showReport.jsp?raq=/CM101.raq&searchType=0'+'&qdate='+dDate;
			
			if(org_diString!=''){
				orgTreePanel6.root.getUI().toggleCheck(false);
				  url=url+'&org_id='+org_diString;
			}
			else if(cbCustMgrName!=''){
				Ext.getCmp('custMgrName').setValue('');
				 url=url+'&mname='+cbCustMgrName;
			}
			var winOpen = window.open(url,'chat' + new Date().getTime(),winFeatures);
		}
}); 