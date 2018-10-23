Ext.onReady(function() {
    var branchType = '';
 
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
	//时间类型
    var typeStore = new Ext.data.ArrayStore({
		fields:['key','value'],
		//wzy,20130509,modify:选项“年-月”查询时，后台缺少对应的表（找不到表结构而没法建表），所以，屏蔽此选项
//	    data:[['1','年-月-日'],['2','年-月']]
	    data:[['1','年-月-日']]
	}); 

/**********************************************************/

		var qForm = new Ext.form.FormPanel({
			id : "qfrom",
			labelWidth : 100, // 标签宽度
//			frame : true, //是否渲染表单面板背景色
			labelAlign : 'middle', // 标签对齐方式
			//bodyStyle : 'padding:3 5 0', // 表单元素和表单面板的边距
			buttonAlign : 'center',
			height:240,
//			height: document.body.clientHeight-40,
			items :[
			        {
				layout : 'column',
				border : false,
	           items :[
			        {
						layout : 'form',
						border : false,
						columnWidth : .5,
							items : [
							         {
											xtype : 'combo',
											store : typeStore,
											fieldLabel : '时间类型',
											id:'selectType',
											hiddenName : 'deposit_average',
											valueField : 'key',
											displayField : 'value',
											triggerAction : 'all',
											mode:'local',
											value:'',
											allowBlank : false,
											editable : false,
											emptyText : '请选择',
											labelStyle : 'text-align:right;',
											anchor : '90%',
											listeners :{'select' : function(){
							        	 if(Ext.getCmp('selectType').getValue()==1){
							        		 Ext.getCmp('StartDate1').show();
							        		 Ext.getCmp('monthType1').hide();
							        	 }
							        	 else if(Ext.getCmp('selectType').getValue()==2){
							        		 Ext.getCmp('monthType1').show();
							        		 Ext.getCmp('StartDate1').hide();
							        	 }
							         }}
										       }
							    ]
	                         },
				        {
							layout : 'form',
						border : false,
						columnWidth : .5,
						hidden : true,
						id : 'monthType1',
							items : [
							         {
								  id : 'monthType',
									name : 'monthType',
									format : 'Y-m',
									xtype : 'datefield',
									// 引用的只选择月份的日期控件
									plugins: 'monthPickerPlugin',
									labelStyle : 'text-align:right;',
									resizable : true,
									fieldLabel : '<font color=red>*</font>选择月份',
									width : '100',
									anchor : '90%'

		  						}      		
					    ]
		             },
	 	                    {
	 						layout : 'form',
	 						columnWidth : .5,
	 						labelWidth : 120,
	 						border : false,
	 						hidden :true,
	 						id :'StartDate1',
 							items : [
 							         
							  {
								  xtype : 'datefield',
		  							fieldLabel : '<font color=red>*</font>数据日期',
		  							name:'StartDate',
		  							id : 'StartDate',
		  							format:"Y-m-d", 
		  							labelStyle : 'text-align:right;',
		  							anchor : '90%' 
							            }
		 							  ]
	 	                         }]},
	           {
					layout : 'column',
					border : false,
		           items :[
	                   { 
						layout : 'form',
						columnWidth : .9,
						labelWidth : 120,
						border : false,
							items : [
							         {
									xtype : 'radiogroup',
									labelStyle : 'text-align:right;',
									fieldLabel : '分行级别',
									id : 'branchType',
									items : [{boxLabel :  '一级(一级分行)', name : 'branchType', inputValue : '211000',checked:true},
										      {boxLabel : '二级(含二级分行)', name : 'branchType', inputValue : '211151'},
										      {boxLabel : '三级(含归属机构)', name : 'branchType', inputValue : '211111'}
										      
									],
									//以下是监听事件
									
									listeners : {
									           'change' : function(radiofield,oldvalue){//这事件是当radiogroup的值发生改变时进入
							        	       branchType = radiofield.getValue().inputValue;
									           }}
													 
							         }
									]
	                         	}
		                   ]
		           }
	           ],
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
			var month = Ext.getCmp('monthType').getValue();
			var day = Ext.getCmp('StartDate').getValue();
			//branchType  分行类型
			branchType = Ext.getCmp('branchType').getValue().inputValue;
//			alert(branchType);
		    var dDate=Ext.util.Format.date(day,'Y-m-d');
		    var mth =Ext.util.Format.date(month,'m');
		    var yth=Ext.util.Format.date(month,'Y');
		    if(month=='' && day=='') 
		    	{Ext.Msg.alert("提醒","请选择一种日期方式");
		    	return false;}
		    var winWidth = screen.width - 10;
				var winHeight = screen.height - 60;
				var winFeatures = "toolbar=no,location=no,directories=no,status=yes,menubar=no,scrollbars=yes,resizable=yes,";
				winFeatures += "top=0,left=0,height="
						+ winHeight + ",width=" + winWidth;
				var url='';
//				alert(branchType);
				if(dDate!='')
				{
					Ext.getCmp('StartDate').setValue('');
					url=basepath+'/reportJsp/showReport.jsp?raq=/report_93_2.raq&qdate='+dDate+'&uid='+__units+'&bank_level='+branchType;	
				}
				if(month!='')
				{
					Ext.getCmp('monthType').setValue('');
					url=basepath+'/reportJsp/showReport.jsp?raq=/931.raq&vdoing='+yth+'&uid='+__units+'&vdoing2='+mth+'&org_id='+branchType;
				}
				var winOpen = window.open(url,'chat' + new Date().getTime(),winFeatures);
			}
		var fpanel = new Ext.Panel({
			id : "fpanel",
//			labelWidth : 90, // 标签宽度
			frame : true, //是否渲染表单面板背景色
			labelAlign : 'middle', // 标签对齐方式
			buttonAlign : 'center',
//			height: document.body.clientHeight,
			items :[qForm]
			        });
	// 布局模型
	var viewport = new Ext.Panel({
		title:'统计报表->有效户分析->有效户客户分析',
		renderTo : 'viewport_center',
		width:600,
		height:300,
		frame : true,
				items: [{   
				    hidden:false,
				    margins: '0 0 0 0',
					items:[fpanel]
			     }] 
			});
	
}); 