Ext.onReady(function() {
    var custType = '';
    var openNature = '';
    var  dayScaleType   = '';
     var custAcct       = '';
    var  monthScaleType = '';
    var  profitType     = '';
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
		debugger;
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
				id : 'groupHostOrgNoName6',
				emptyText : '请选择',
				resizable : false,
				labelStyle : 'text-align:right;',
				fieldLabel : '<font color=red>*</font>机构',
				anchor : '99%',
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
	
	//业务类型
    var analysisTypeStore = new Ext.data.ArrayStore({
		fields:['key','value'],
	    data:[['1','客户规模'],['2','行业门类'],['3','所有制'],['4','组织类别'],['5','客户类别'],['6','开户性质']]
	}); 

/**********************************************************/

		var qForm = new Ext.form.FormPanel({
			id : "qfrom",
			labelWidth : 90, // 标签宽度
//			frame : true, //是否渲染表单面板背景色
			labelAlign : 'middle', // 标签对齐方式
			//bodyStyle : 'padding:3 5 0', // 表单元素和表单面板的边距
			buttonAlign : 'center',
			height:180,
//			height: document.body.clientHeight-40,
			items :[{
				layout : 'column',
				border : false,
	           items :[  
	                     {
	 						layout : 'form',
	 						columnWidth : .70,
	 						labelWidth : 120,
	 						border : false,
 							items : [
							  {
								xtype : 'radiogroup',
								labelStyle : 'text-align:right;',
								fieldLabel : '客户类别',
								id : 'custType',
//								columns : 2,
								items : [{boxLabel :  '<font color="red">企业客户</font>', name :'custType', inputValue : '1'},
									      {boxLabel : '<font color="red">机关事业单位</font>', name : 'custType', inputValue : '2'},
									      {boxLabel : '<font color="red">同业客户</font>', name : 'custType', inputValue : '3'}
								],
								//以下是监听事件s
								
								listeners : {
								           'change' : function(radiofield,oldvalue){//这事件是当radiogroup的值发生改变时进入
								            custType = radiofield.getValue().inputValue;
								          
								           }
								       }}
	 							     ]
	 	                         },
	 	                    {
	 						layout : 'form',
	 						columnWidth : .70,
	 						labelWidth : 120,
	 						border : false,
 							items : [
 							         
							  {
								xtype : 'radiogroup',
								labelStyle : 'text-align:right;',
								fieldLabel : '开户性质',
								id : 'openNature',
//	 									columns : 2,
								items : [{boxLabel : '<font color="red">基本户</font>', name :'openNature', inputValue : '1'},
									      {boxLabel : '<font color="red">一般结算户</font>', name : 'openNature', inputValue : '2'},
									      {boxLabel : '<font color="red">专用存款户</font>', name : 'openNature', inputValue : '3'},
									      {boxLabel : '<font color="red">临时存款户</font>', name : 'openNature', inputValue : '4'}
								],
								//以下是监听事件
								
								listeners : {
								           'change' : function(radiofield,oldvalue){//这事件是当radiogroup的值发生改变时进入
								            custType = radiofield.getValue().inputValue;
								          
								           }}
							  				}
		 							  ]
	 	                         },
                   { 
					layout : 'form',
					columnWidth : .50,
					labelWidth : 120,
					border : false,
						items : [
						  {
								xtype : 'radiogroup',
								labelStyle : 'text-align:right;',
								fieldLabel : '日均存款规模',
								id : 'dayScaleType',
//	 									columns : 2,
								items : [{boxLabel : '>', name :'dayScaleType', inputValue : '1'},
									      {boxLabel : '≥', name : 'dayScaleType', inputValue : '2'},
									      {boxLabel : '<', name : 'dayScaleType', inputValue : '3'},
									      {boxLabel : '≤', name : 'dayScaleType', inputValue : '4'}
								],
								//以下是监听事件
								
								listeners : {
								           'change' : function(radiofield,oldvalue){//这事件是当radiogroup的值发生改变时进入
							                dayScaleType = radiofield.getValue().inputValue;
								           }}
											},
								  {
								xtype : 'radiogroup',
								labelStyle : 'text-align:right;',
								fieldLabel : '累计结算量规模',
								id : 'custAcct',
//	          	 									columns : 2,
								items : [{boxLabel :  '>', name : 'custAcct', inputValue : '1'},
									      {boxLabel : '≥', name : 'custAcct', inputValue : '2'},
									      {boxLabel : '<', name : 'custAcct', inputValue : '3'},
									      {boxLabel : '≤', name : 'custAcct', inputValue : '4'}
								],
								//以下是监听事件
								
								listeners : {
								           'change' : function(radiofield,oldvalue){//这事件是当radiogroup的值发生改变时进入
									         custAcct = radiofield.getValue().inputValue;
								           }}
								  			},
							 {
								xtype : 'radiogroup',
								labelStyle : 'text-align:right;',
								fieldLabel : '月均结算次数',
								id : 'monthScaleType',
								items : [{boxLabel :  '>', name : 'monthScaleType', inputValue : '1'},
									      {boxLabel : '≥', name : 'monthScaleType', inputValue : '2'},
									      {boxLabel : '<', name : 'monthScaleType', inputValue : '3'},
									      {boxLabel : '≤', name : 'monthScaleType', inputValue : '4'}
								],
								//以下是监听事件
								
								listeners : {
								           'change' : function(radiofield,oldvalue){//这事件是当radiogroup的值发生改变时进入
											monthScaleType = radiofield.getValue().inputValue;
								           }}
										},
								 {
									xtype : 'radiogroup',
									labelStyle : 'text-align:right;',
									fieldLabel : '客户利润贡献度',
									id : 'profitType',
									items : [{boxLabel :  '>', name : 'profitType', inputValue : '1'},
										      {boxLabel : '≥', name : 'profitType', inputValue : '2'},
										      {boxLabel : '<', name : 'profitType', inputValue : '3'},
										      {boxLabel : '≤', name : 'profitType', inputValue : '4'}
									],
									//以下是监听事件
									
									listeners : {
									           'change' : function(radiofield,oldvalue){//这事件是当radiogroup的值发生改变时进入
												profitType = radiofield.getValue().inputValue;
									           }}
												}	
											]
                         	},
                         {
         					layout : 'form',
         					columnWidth : .50,
         					labelWidth : 140,
         					border : false,
         						items : [
         						    {
         							xtype : 'numberfield',
         							fieldLabel : '日均存款金额<font color="red">(万元)</font>',
         							name:'dayScaleTypeName',
         							labelStyle : 'text-align:right;',
         							anchor : '90%'
         						},
		  						    {
		  							xtype : 'numberfield',
		  							fieldLabel : '客户累计结算量<font color="red">(万元)</font>',
		  							name:'custAcctName',
		  							labelStyle : 'text-align:right;',
		  							anchor : '90%'
		  						},
		  						    {
		  							xtype : 'numberfield',
		  							fieldLabel : '月结算次数<font color="red">(次)</font>',
		  							name:'monthScaleTypeName',
		  							labelStyle : 'text-align:right;',
		  							anchor : '90%'
		  						},
		  						    {
		  							xtype : 'numberfield',
		  							fieldLabel : '利润贡献度<font color="red">(万元)</font>',
		  							name:'profitTypeName',
		  							labelStyle : 'text-align:right;',
		  							anchor : '90%'
		  						}
         						]
                                  } 
	                   ]
	           }],
	        buttonAlign : 'center',
			buttons : [
					{
						text : '查询',
						handler : function() {
					
						if(!qForm.getForm().isValid()){
							Ext.Msg.alert("提醒","请填写必填项");
							return false;
						}
						select();
//							report4ShowWindow.show();
							var parameters = qForm.getForm().getValues(false);
						}
					}, {
						text : '重置',
						handler : function() {
							qForm.getForm().reset();
							orgTreePanel6.root.getUI().toggleCheck(false);
						}
					} ] 	     
		});
		
		function select(){
			var dayScaleTypeName = qForm.getForm().findField('dayScaleTypeName').getValue();
			var custAcctName = qForm.getForm().findField('custAcctName').getValue();
			var monthScaleTypeName = qForm.getForm().findField('monthScaleTypeName').getValue();
			var profitTypeName = qForm.getForm().findField('profitTypeName').getValue();
		    
		    var winWidth = screen.width - 10;
				var winHeight = screen.height - 60;
				var winFeatures = "toolbar=no,location=no,directories=no,status=yes,menubar=no,scrollbars=yes,resizable=yes,";
				winFeatures += "top=0,left=0,height="
						+ winHeight + ",width=" + winWidth;
				var url=basepath+'/reportJsp/showReport.jsp?raq=/report_dep.raq&oddate='+'2012-07-08'+'&org_id=200001'+'&uid='+__units+'&cust_type=1'+'&cust_type1=26'+'&cust_open_type=1'+'&analyse_type=1';
				var winOpen = window.open(url,'chat' + new Date().getTime(),winFeatures);
			}
		var fpanel = new Ext.Panel({
			id : "fpanel",
			labelWidth : 90, // 标签宽度
			frame : true, //是否渲染表单面板背景色
			labelAlign : 'middle', // 标签对齐方式
			buttonAlign : 'center',
			height: document.body.clientHeight,
			items :[qForm]
			        });
	// 布局模型
	var viewport = new Ext.Panel({
		title:'RQ统计报表->有效户分析->设定有效客户标准',
		renderTo : 'viewport_center',
		frame : true,
				items: [{   
				    hidden:false,
				    margins: '0 0 0 0',
					items:[fpanel]
			     }] 
			});
	
}); 