Ext.onReady(function() {

	//机构树
	Ext.override(
					Ext.form.ComboBox,
					{
						onViewClick : function(doFocus) {
							var index = this.view.getSelectedIndexes()[0], s = this.store, r = s
									.getAt(index);
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
				emptyText : '请选择',
				resizable : false,
				labelStyle : 'text-align:right;',
				fieldLabel : '<font color=red>*</font>机构',
				anchor : '90%',
				editable : false,
				allowBlank : false,
				mode : 'local',
				triggerAction : 'all',
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
						qForm.getForm().findField("instncode")
								.setValue(checkedId);
					}
				}
			});
	

	var statusStore = new Ext.data.ArrayStore({
		fields:['key','value'],
	    data:[['1','全辖对公客户'],['2','新开对公客户']]
	});
	var boxstore8 = new Ext.data.ArrayStore({
		fields:['key','value'],
	    data:[['1','基本户'],['2','一般结算户'],['3','专用存款户'],['4','临时存款户']]
	});
	
	var boxstore9 = new Ext.data.ArrayStore({
		fields:['key','value'],
	    data:[['1','存款余额规模'],['2','存款日均规模']]
	});
	
	

/**********************************************************/

	var qForm = new Ext.form.FormPanel({
		id : "qfrom",
		labelWidth : 90, // 标签宽度
		 title: "客户管理->客户信息查询->信贷业务变化检索->增加一定规模", 
		frame : true, //是否渲染表单面板背景色
		labelAlign : 'middle', // 标签对齐方式
		buttonAlign : 'center',
   				layout : 'column',
   				border : false,
   	           items :[{
   	        	   columnWidth : .33,
   	        	   layout : 'form',
   	        	   border : false,
   	        	   labelWidth : 120,
					items : [ {
						name:'startDate',
						fieldLabel:'起始时间',
						xtype:'datefield',
						value:' ',
						format:'Y-m-d',
						allowBlank : true,
						labelStyle : 'text-align:right;',
						anchor:'90%'
					},
					{
						name:'endDate',
						fieldLabel:'终止时间',
						xtype:'datefield',
						value:' ',
						format:'Y-m-d',
						allowBlank : true,
						labelStyle : 'text-align:right;',
						validator:ckDate,
						anchor:'90%'
					}
					]
				   },  {
					layout : 'form',
					columnWidth : .33,
					labelWidth : 160,
					items : [ new Com.yucheng.bcrm.common.OrgField({
						searchType:'SUBTREE',/*指定查询机构范围属性  SUBTREE（子机构树）SUBORGS（直接子机构）PARENT（父机构）PARPATH （所有父、祖机构）ALLORG（所有机构）*/
						fieldLabel : '所属机构',
						labelStyle : 'text-align:right;',
						id : 'CUST_ORG', //放大镜组件ID，用于在重置清空时获取句柄
						name : 'CUST_ORG', 
						hiddenName: 'instncode',   //后台获取的参数名称
						anchor : '90%',
						checkBox:true //复选标志
					}),{
						xtype : 'numberfield',
						name : 'moneyBalance',
						fieldLabel : '用信变化额>=<font color=red>(单位：万元)</font>',
						labelStyle : 'text-align:right;',
						anchor : '90%'
					}
					]
					},{
						layout : 'form',
						columnWidth : .33,
						labelWidth : 160,
						items : [
						{
						xtype : 'numberfield',
						name : 'effectAmount',
						fieldLabel : '授信变化额>=<font color=red>(单位：万元)</font>',
						labelStyle : 'text-align:right;',
						anchor : '90%'
						} ]
	           }],
		buttons : [
				{
					text : '查询',
					handler : function() {
				
					if(!qForm.getForm().isValid()){
						Ext.Msg.alert("提醒","请填写必填项");
						return false;
					}
					select();
//						report4ShowWindow.show();
						var parameters = qForm.getForm()
								.getValues(false);
					}
				}, {
					text : '重置',
					handler : function() {
						qForm.getForm().reset();
						Ext.getCmp("CUST_ORG").setValue('');
					}
				} ] 	     
	});	
	
	function ckDate(){
	    var v1 = qForm.getForm().findField('startDate').getValue();
	    var v2 = qForm.getForm().findField('endDate').getValue();
	    if(v1=="" || v2=="") return true;
	    return v2 > v1;
	}
		function select(){
			var startDate = qForm.getForm().findField('startDate').getValue();
			var endDate = qForm.getForm().findField('endDate').getValue();
			var moneyBalance = qForm.getForm().findField("moneyBalance").getValue();
			var effectAmount = qForm.getForm().findField("effectAmount").getValue();
			var org_diString = Ext.getCmp("CUST_ORG").getValue();//机构
		
		 if(moneyBalance*1==0){
			 moneyBalance=0;
		 }
		 if(effectAmount*1==0){
			 effectAmount=0;
		 }
			    
		    var bDate=Ext.util.Format.date(startDate,'Y-m-d');
		    var eDate=Ext.util.Format.date(endDate,'Y-m-d');
			
			 if(startDate=='') bDate='2012-08-01'; 
			    if(endDate=='') eDate='2012-10-01';
			    
				var winWidth = screen.width - 10;
				var winHeight = screen.height - 60;
				var winFeatures = "toolbar=no,location=no,directories=no,status=yes,menubar=no,scrollbars=yes,resizable=yes,";
				winFeatures += "top=0,left=0,height="
						+ winHeight + ",width=" + winWidth;
				var url=basepath+'/reportJsp/showReport.jsp?raq=/report_49.raq&sdate='+bDate+'&uid='+__units+'&edate'+eDate+'&org_id='+org_diString+'&amt1='+(moneyBalance*10000)+'&amt2='+(effectAmount*10000);
				var winOpen = window.open(url,'chat' + new Date().getTime(),winFeatures);
			}
	

	// 布局模型
	    
	var viewport = new Ext.Viewport({
				layout : 'fit',
				items: [qForm] 

			});
	
}); 