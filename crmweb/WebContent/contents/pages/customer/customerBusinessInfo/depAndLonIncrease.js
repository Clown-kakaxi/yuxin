Ext.onReady(function(){
	
	/*得到昨天的日期*/
	var today =  new Date();
	var year1 = today.getYear();
	var mon1 = today.getMonth()+1;
	var date1 = today.getDate(); 
	
	var lastDate = new Date(year1,mon1,date1-1);
	
	var year = lastDate.getYear();
	var month = lastDate.getMonth();
	var day = lastDate.getDate();
	var lastDay = year+"-"+month+"-"+day;	  
		
	Ext.override(Ext.form.ComboBox, {
	      onViewClick : function(doFocus) {
	        var index = this.view.getSelectedIndexes()[0], s = this.store, r = s.getAt(index);
	        if (r) {
	          this.onSelect(r, index);
	        } else if (s.getCount() === 0) {
	          this.collapse();
	              
	        }
	        if (doFocus !== false) {
	          this.el.focus();
	        }
	      }
	    });	    

	 //递归收起子节点
	 function childCollapse(node){
	 	 node.eachChild(function(currNode)
	 	 {
	 	 	if(!currNode.isLeaf())
	 	 	{
		 	 	currNode.collapse();		 	 	         
		 	 	childCollapse(currNode);
	 	 	}
	 	 }
	 	 );
	 }	 
	 
	function submitValidate(orgTreePanel,validateForm){
								var level = spanBank(orgTreePanel);
								var checkedNodes = getCheckedNode(orgTreePanel);
								if(level ==null || level=="" || level == undefined ){
									Ext.MessageBox.alert('提示','请选择机构.');	
									validateForm.findField("checkedNodes").setValue("");									
									return false;
								}								
								else 
								{
									var crm_dt = validateForm.findField("crm_dt").getValue();
									var org_level = validateForm.findField("instn_level").value;
									var caculate_type = validateForm.findField("caculate_type").value;
									if(crm_dt == null || crm_dt==""||crm_dt==undefined){
										Ext.MessageBox.alert('提示','请选择统计日期.');	
										return false;
									}
									if(org_level == null || org_level==""||org_level==undefined){
										Ext.MessageBox.alert('提示','请选择机构层次.');	
										return false;
									}	
									if(caculate_type == null || caculate_type==""||caculate_type==undefined){
										Ext.MessageBox.alert('提示','请选择对比口径.');	
										return false;
									}	
									
									validateForm.findField("checkedNodes").setValue(checkedNodes);	
									validateForm.findField("level").setValue(level);
									var instn_level = validateForm.findField("instn_level").value;
									var sta_level;
									if(instn_level == "level_0"){
										sta_level=1;
									}else if(instn_level =="level_1"){
									
										sta_level=2;
									}else if(instn_level == "level_2"){
										sta_level=3;
									}else if(instn_level =="level_3"){
										sta_level=4;
									}
									if(parseInt(sta_level)<parseInt(level)){
										Ext.MessageBox.alert('提示','选择的层次无效,请选择下级层次!');
										return false;
									}												
								}
								return true;
		
	}	 
	 
	var boxstoreCluster = new Ext.data.Store({  
		restful:true,   
		autoLoad :true,
		proxy : new Ext.data.HttpProxy({
				url :basepath+'/querycustomerbase.json'
			}),
			reader : new Ext.data.JsonReader({
				root : 'json.data'
			}, [ 'ID', 'CUST_BASE_NAME' ])
		});
		
		var boxstoreRoll = new Ext.data.Store({  
			restful:true,   
			autoLoad :true,
			proxy : new Ext.data.HttpProxy({
					url :basepath+'/queryrollcustomer.json'
				}),
				reader : new Ext.data.JsonReader({
					root : 'json.data'
				}, [ 'ROLL_ID', 'ROLL_NAME' ])
			});	 
	 
			
	 //机构text框中显示的机构名称
	 function checkText(orgTreePanel_p){
	 	
	 	var checkTextString="";
	 	if(orgTreePanel_p.root.getUI().isChecked()){
	 		var rootId = orgTreePanel_p.root.id;
	 		var text = orgTreePanel_p.root.text;
	 		checkTextString = checkTextString+text;
	 		return checkTextString;
	 	}	 	
	 	var level2Nodes = orgTreePanel_p.root.childNodes;
	 	var level3Nodes = new Array();
	 	var level4Nodes = new Array();
	 	var level2NodesChecked = new Array();
	 	var level3NodesChecked = new Array();
	 	for(var i=0;i<level2Nodes.length;i++){
	 		var tempNode = level2Nodes[i];
	 		if(tempNode.getUI().isChecked()){
	 			checkTextString  = checkTextString + tempNode.text+";";
	 			level2NodesChecked[level2NodesChecked.length] = tempNode;
	 		}
	 		tempNode.eachChild(function(currNode){
	 			level3Nodes[level3Nodes.length] = currNode;
	 		});
	 	}
	 	
	 	for(var j=0;j<level3Nodes.length;j++){
	 		
	 		var anode = level3Nodes[j];
			
	 		if(anode.getUI().isChecked())
	 		{
	 			var flag= false;
		 		for(var k=0;k<level2NodesChecked.length;k++){
		 			
		 			if(anode.parentNode ==level2NodesChecked[k] ){
		 				flag=true;
		 				break;
		 			}	
		 		}
		 		if(!flag){		 			
		 			checkTextString  = checkTextString + anode.text+";";
		 		}		 		
		 		level3NodesChecked[level3NodesChecked.length] = anode;
	 		}
	 		
	 		anode.eachChild(function(childNode){	 			
	 			level4Nodes[level4Nodes.length] = childNode;	 			
	 		});	 		
	 	}
	 	
	 	for(var m=0;m<level4Nodes.length;m++){
	 		var node4 = level4Nodes[m];
	 		if(node4.getUI().isChecked()){
	 			var flag = false;
	 			for(var n=0;n<level3NodesChecked.length;n++){
					if(node4.parentNode == level3NodesChecked[n]){
						flag = true;
						break;
					}					
	 			}	 			
	 			if(!flag){
	 				checkTextString  = checkTextString + node4.text+";";	 			
	 			}
	 		}	 	
	 	}
		var lastIndex = checkTextString.lastIndexOf(";");
		checkedTextString = checkTextString.substring(0,lastIndex);		
		return checkedTextString;
	 }
	 
	 
	 //选中节点后判断，所有被选中节点是否有跨行的情况
	 function spanBank(orgTreePanel_p){
	 	if(orgTreePanel_p.root.getUI().isChecked()){
	 		
//	 		var rootId = orgTreePanel_p.root.id;
//	 		var checkLevel = rootId.substring(rootId.length-1);
	 		
	 		var childNode = orgTreePanel_p.root.firstChild;
	 		if(childNode==null || childNode == "" || childNode==undefined){
	 			return "4";
	 		}
	 		var childNodeId = childNode.id;
	 		var childLevel = childNodeId.substring(childNodeId.length-1);
	 		var checkLevel = parseInt(childLevel)-1;
	 		return checkLevel;	 		
	 	}
	 	var level2Nodes = orgTreePanel_p.root.childNodes;//二级节点数组
	 	var level3Nodes = new Array();//三级节点数组
	 	var level4Nodes = new Array();//四级节点数组
	 	//for 循环得到所有3级节点
	 	for(var m=0;m<level2Nodes.length;m++){
	 		level2Nodes[m].eachChild(function(level3Node){	 		
	 			level3Nodes[level3Nodes.length] = level3Node;	 		
	 		} );
	 	}
	 	//for 循环得到所有4级节点
	 	for(var n=0;n<level3Nodes.length;n++){
	 		level3Nodes[n].eachChild(function(level4Node){
	 			level4Nodes[level4Nodes.length] = level4Node;
	 		});
	 	}	 	
	 	var num=0;
	 	var level2NodeChecked;
	 	var level3Checked = new Array();
	 	for(var i=0;i<level2Nodes.length;i++){
	 		if(level2Nodes[i].getUI().isChecked()){
	 			level2NodeChecked = level2Nodes[i];//得到第二层级被选择的节点
	 			num++;
	 			if(num >=2){
//	 				alert(" 一级没选,2级选择 有跨行:"+"4");
	 				return "4";//表示跨行选择
	 			}
	 		}
	 	}
		
	 	if(num ==1){	 	
			for(var j=0;j<level3Nodes.length;j++){			
				if(level3Nodes[j].getUI().isChecked()){
					level3Checked[level3Checked.length] = level3Nodes[j];
					if(level3Nodes[j].parentNode != level2NodeChecked){//如果被选择的三级节点的父节点不是同一个被选择二级节点,则表示有跨行选择
//						alert(" 二级选择了一个,三级选择有跨行:" + "4");
						return "4";
					}					
				}
			}			
			for(var k=0;k<level4Nodes.length;k++){
				
				if(level4Nodes[k].getUI().isChecked()){
					var flag=false;
					for(var a=0;a<level3Checked.length;a++){
						if(level4Nodes[k].parentNode == level3Checked[a]){
							flag = true;
							break;
						}
					}
					if(!flag){
//						alert(level2NodeChecked.text);
//						alert(" 二级选择了一个,三级选择了一个,四级选择有跨行:" + "4");
						return "4";
					}
				}
			}
			var check2NodeId = level2NodeChecked.id;
			var checkLevel = check2NodeId.substring(check2NodeId.length-1);
//			alert("选中2级节点统计: "+checkLevel	);
			return checkLevel;
			
		}else if(num==0){
			
			var nodeNum3=0;
			var tempChecked3Node;
			for(var b=0;b<level3Nodes.length;b++){
				if(level3Nodes[b].getUI().isChecked()){
					tempChecked3Node = level3Nodes[b];
					nodeNum3++;
					if(nodeNum3>=2){
//						alert("2级没选,三级选择有跨行: "+4);
						return "4";
					}
				}
			}			
			if(nodeNum3==1){
				
				for(var c = 0;c<level4Nodes.length;c++){
					if( (level4Nodes[c].getUI().isChecked()) && (level4Nodes[c].parentNode!=tempChecked3Node) ){
//							alert("二级没选,3级选择了一个,4级选择有跨行.");
							return "4";					
					}
				}			
				var check3NodeId = tempChecked3Node.id;
				var checkLevel = check3NodeId.substring(check3NodeId.length-1);
//						alert("选择了三级节点统计:"+checkLevel);
				return checkLevel;			
			}else if(nodeNum3==0){
				for(var d=0;d<level4Nodes.length;d++){
					if(level4Nodes[d].getUI().isChecked()){
						var check4NodeId = level4Nodes[d].id;
						var checkLevel = check4NodeId.substring(check4NodeId.length-1);
//						alert("选择了四级节点统计: "+checkLevel);
						return checkLevel;
					}
				}
			}
		}
			 	
	 } 
	 
	 function getCheckedStr(checkedStr,node){
	 		node.eachChild(function(tempNode){
	 			if(tempNode!=null)
	 			{	
	 				if( tempNode.getUI().isChecked())
	 				{
				 		var idCode = tempNode.id;
				 		var id = idCode.substring(0,idCode.length-1);
			 			checkedStr = checkedStr+"'"+id+"',";
			 			checkedStr = getCheckedStr(checkedStr,tempNode);
			 			return checkedStr;
	 				}else
	 				{
	 					checkedStr = getCheckedStr(checkedStr,tempNode);
	 					return checkedStr;
	 				}
	 			}
	 		});	 		

		 	return checkedStr;
	 }
	 
	 function getCheckedNode(orgTreePanel_p){
		var rootNode = orgTreePanel_p.root;	
		var checkedStr="";
		if(rootNode.getUI().isChecked()){
	 		var idCode = rootNode.id;
	 		var id_2 = idCode.substring(0,idCode.length-1);			
		 	checkedStr=checkedStr+"'" + id_2+"'"+",";	
		}	 	
	 	checkedStr = getCheckedStr(checkedStr,rootNode);
	 	
	 	
	 	return checkedStr.substring(0,checkedStr.length-1);
	 }
	 var orgTreePanel = new Ext.tree.TreePanel(
	 	{	 		
	 	autoScroll:true,
	 	height:350,
	 	width:200,
		listeners:{
			'click':function(node)
			{
				if(node.getUI().isChecked())
				{
					node.getUI().toggleCheck(false);
				}else
				{
					node.getUI().toggleCheck(true);
				}				
//				node.getUI().toggleCheck(true);
				
//				var level = spanBank(orgTreePanel);
//				var checkedNodes = getCheckedNode(orgTreePanel);
//				depAndLonIncreSearchPanel.getForm().findField("checkedNodes").setValue(checkedNodes);		
//				depAndLonIncreSearchPanel.getForm().findField("level").setValue(level);

//				tempCombo = depAndLonIncreSearchPanel.getForm().findField("instn_no");
//				tempCombo.setRawValue(node.text);

//				tempCombo.collapse();
				
			},
			'checkchange':function(node,checked){
				if(checked){					
					var childNodes = node.childNodes;
					for(var i=0;i<childNodes.length;i++){
						childNodes[i].getUI().toggleCheck(true);					
					}
					
				}				
				else
				{	
					var childNodes = node.childNodes;
					for(var i=0;i<childNodes.length;i++){						
						childNodes[i].getUI().toggleCheck(false);
					}
				}			
			},
			'dblclick':function(node){
				if(node.getUI().isChecked() ){
					node.getUI().toggleCheck(false);			
				}else
				{
					node.getUI().toggleCheck(true);	
				}
			}
		},
		root:new Ext.tree.AsyncTreeNode({
			id:orgId,
			text:orgName,			
			autoScroll:true,
			expanded:true,
			leaf:false,
			checked:false,
			loader:new Ext.tree.TreeLoader({
				url:basepath+'/system-unit-recursive.json',
				requestMethod:'GET',
				listeners:{
					'load':function(){
						var rootNode = orgTreePanel.root;
						rootNode.eachChild(function(node){
							if(!node.isLeaf()){
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
	 }
	 );	
	 
	 var orgTreePanelAdvance = new Ext.tree.TreePanel(
	 	{	 		
	 	autoScroll:true,
	 	height:350,
	 	width:200,
		listeners:{
			'click':function(node)
			{

				if(node.getUI().isChecked())
				{
					node.getUI().toggleCheck(false);
				}else
				{
					node.getUI().toggleCheck(true);
				}
//				node.getUI().toggleCheck(true);
//				tempCombo = Ext.getCmp("depAndLonIncreaseAdvanceSearch").getForm().findField("instn_no");
//				tempCombo.setRawValue(node.text);
//				tempCombo.collapse();
//				var level = spanBank(orgTreePanelAdvance);
//				var checkedNodes = getCheckedNode(orgTreePanelAdvance);
//				Ext.getCmp("depAndLonIncreaseAdvanceSearch").getForm().findField("checkedNodes").setValue(checkedNodes);	
//				Ext.getCmp("depAndLonIncreaseAdvanceSearch").getForm().findField("level").setValue(level);
			},
			'checkchange':function(node,checked){
				if(checked){					
					var childNodes = node.childNodes;
					for(var i=0;i<childNodes.length;i++){
						childNodes[i].getUI().toggleCheck(true);					
					}
					
				}				
				else
				{	
					var childNodes = node.childNodes;
					for(var i=0;i<childNodes.length;i++){						
						childNodes[i].getUI().toggleCheck(false);
					}
				}			
			},
			'dblclick':function(node){
				if(node.getUI().isChecked() ){
					node.getUI().toggleCheck(false);			
				}else
				{
					node.getUI().toggleCheck(true);	
				}
			}
		},
		root:new Ext.tree.AsyncTreeNode({
			id:orgId,
			text:orgName,			
			autoScroll:true,
			expanded:true,
			leaf:false,
			checked:false,
			loader:new Ext.tree.TreeLoader({
				url:basepath+'/system-unit-recursive.json',
				requestMethod:'GET',
				listeners:{
					'load':function(){
						var rootNode = orgTreePanelAdvance.root;
						rootNode.eachChild(function(node){
							if(!node.isLeaf()){
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
	 }
	 );	
	 
	var instnCombo = new Ext.form.ComboBox({	
					columnWidth:.25,
					xtype:'combo',					
					store : new Ext.data.SimpleStore( {
						fields : [],
						data : [ [] ]
					}),
					name:'instn_no',
					emptyText : '请选择...',
					fieldLabel : '*机构',
					editable:false,
					resizable:true,
					anchor : '90%',
					mode : 'local',
					triggerAction : 'all',
					maxHeight : 390,
					// 下拉框的显示模板,addDeptTreeDiv作为显示下拉树的容器
					tpl:"<tpl for='.' <div style='height:390px'> <div id='addOrgTreeDivForAdd'></div></div></tpl>",
//					tpl:"<div style='height:390px'> <div id='addOrgTreeDivForAdd'></div></div>",
					onSelect : Ext.emptyFn,
					listeners:{
						'expand':function(combo){			
							orgTreePanel.render('addOrgTreeDivForAdd');
						},
						'collapse':function(combo){
							var checkString = checkText(orgTreePanel);
							combo.setValue(checkString);
						}
					}
				});	 
	 
 
	var depAndLonIncreSearchPanel = new Ext.form.FormPanel(
		{
		//查询panel
		height:140,
		labelWidth:80,//label的宽度
		labelAlign:'right',
		frame:true,
		region:'north',
		split:true,
		items:[
		{
			layout:'column',
			items:[
			{
			 columnWidth:.25,
			 labelWidth:80,
			 layout:'form',
			 items:[
				instnCombo,	
			 	{
			 		xtype:'textfield',
			 		name:'level',
			 		hidden:true
			 	},
			 	{
					xtype:'combo',
					name:'instn_level',
					hiddenName:'instn_level',
					fieldLabel:'*机构层次',
					anchor:'90%',
					mode:'local',
					editable:false,
					triggerAction:'all',
					resizable:true,
					store: new Ext.data.Store({			        
			        autoLoad:true,
			        sortInfo:{
			        	field:'key',
			        	direction:'ASC'
			        },
			        proxy:new Ext.data.HttpProxy({
			        	url:basepath+'/lookup.json?name=UNIT_LEVEL',
			        	method:'GET'
			        }),
			        reader:new Ext.data.JsonReader({
			        	root:'JSON'
			        },['key','value']),
			        fields: [
			            'key',
			            'value'
			        ],
			        listeners:{
			        	'load':function(){
			        		depAndLonIncreSearchPanel.getForm().findField("instn_level").setValue("level_3");
			        	}
			        }
			 	   }),
			 	   valueField:'key',
			 	   displayField:'value'
				},				
				{
					anchor:'90%',
					name:'cur_type',
					hiddenName:'cur_type',
					xtype:'combo',
					fieldLabel:'币种',
					triggerAction:'all',
					resizable:true,
					anchor:'90%',
					mode:'local',
					store: new Ext.data.Store({
						autoLoad:true,
						proxy:new Ext.data.HttpProxy(
						{
							url:basepath+'/lookup.json?name=CUR_TYPE',
							method:'GET'
						}
						),
						sortInfo:{
							field:'key',
							direction:'ASC'
						},
						fields:['key','value'],
						reader:new Ext.data.JsonReader({
							root:'JSON'
						},['key','value']),
						listeners:{
							'load':function(){								
								depAndLonIncreSearchPanel.getForm().findField("cur_type").setValue("01");
							}
						}
					}),
			       valueField:'key',
			       displayField:'value'							
			    },
			 	{
			 		xtype:'textfield',
			 		name:'checkedNodes',
			 		hidden:true	
			 	}			    
				]
			 },
			{
				layout:'form',
				columnWidth:.25,
				labelWidth:80,
				items:[
				{
				name:'crm_dt',
				anchor:'90%',
				xtype:'datefield',
				format:'Y-m-d',
				value:lastDay,
				fieldLabel:'*统计日期'
				},
				{
					name:'caculate_type',
					hiddenName:'caculate_type',
					anchor:'90%',
					xtype:'combo',
					editable:false,
					format:'Y-m-d',
					fieldLabel:'*对比口径',
					resizable:true,
					mode:'local',
					triggerAction:'all',
					store: new Ext.data.Store({
					sortInfo:{
						field:'key',
						direction:'ASC'
					},
			        autoLoad:true,
			        proxy:new Ext.data.HttpProxy({
			        	url:basepath+'/lookup.json?name=CACULATE_TYPE',
			        	method:'GET'
			        }),
			        reader:new Ext.data.JsonReader({
			        	root:'JSON'
			        },['key','value']),
			        fields: [
			            'key',
			            'value'
			        ],
			        listeners:{
			        	'load':function(){
			        		depAndLonIncreSearchPanel.getForm().findField("caculate_type").setValue("01");							        		
			        	}
			        }
			 	   }),
			 	   valueField:'key',
			 	   displayField:'value'
				},
				{
					name:'sts',
					hiddenName:'sts',
					anchor:'90%',
					xtype:'combo',
					resizable:true,
					fieldLabel:'客户状态',
					mode:'local',
					triggerAction:'all',
					emptyText:'全部',
					store:new Ext.data.Store({  
					sortInfo:{
						field:'key',
						direction:'ASC'
					},
					restful:true,   
					autoLoad :true,
					proxy : new Ext.data.HttpProxy({
							url :basepath+'/lookup.json?name=KHZT',
							method:'GET'
						}),
						reader : new Ext.data.JsonReader({
							root : 'JSON'
						}, [ 'key', 'value' ])
					}),
					valueField:'key',
					displayField:'value'
				}							
				]
			},
			{
				layout:'form',
				labelWidth:80,
				columnWidth:.25,
				items:[

				{
				name:'cust_name',
				anchor:'90%',
				xtype:'textfield',
				format:'Y-m-d',
				fieldLabel:'客户名称'
				},				
				{
					name:'cust_zzdm',
					anchor:'90%',
					xtype:'textfield',
					fieldLabel:'组织机构代码'
				},
				{
					hiddenName : 'roll_name',
					fieldLabel : '客户名单',
					labelStyle: 'text-align:right;',
					triggerAction : 'all',
					xtype:'combo',
					store : boxstoreRoll,
					displayField : 'ROLL_NAME',
					valueField : 'ROLL_ID',
					mode : 'local',
					forceSelection : true,
					emptyText:'请选择',
					typeAhead : true,
					resizable : true,
					anchor : '90%'
				}				
				]							
			},			 
			{
			 	columnWidth:.25,
			 	labelWidth:80,
			 	layout:'form',
				items:[	
				{
							name:'scope',
							hiddenName:'scope',
							xtype:'combo',
							anchor:'90%',
							fieldLabel:'客户规模',
							emptyText:'全部',
							mode:'local',
							resizable:true,
							triggerAction:'all',
							store:new Ext.data.Store({  
							restful:true,   
							sortInfo:{
								field:'key',
								direction:'ASC'
							},
							autoLoad :true,
							proxy : new Ext.data.HttpProxy({
									url :basepath+'/lookup.json?name=QYGM',
									method:'GET'
								}),
								reader : new Ext.data.JsonReader({
									root : 'JSON'
								}, [ 'key', 'value' ])
							}),
							valueField:'key',
							displayField:'value'
			 	},
				{
							name:'crm_scope',
							hiddenName:'crm_scope',
							xtype:'combo',
							anchor:'90%',
							resizable:true,
							triggerAction:'all',
							fieldLabel:'考核口径客户规模',
							mode:'local',
							emptyText:'全部',
							store:new Ext.data.Store({  
							sortInfo:{
								field:'key',
								direction:'ASC'
							},
							restful:true,   
							autoLoad :true,
							proxy : new Ext.data.HttpProxy({
									url :basepath+'/lookup.json?name=KHQYGM',									
									method:'GET'
								}),
								reader : new Ext.data.JsonReader({
									root : 'JSON'
								}, [ 'key', 'value' ])
							}),
							valueField:'key',
							displayField:'value'
			 	},
				{
							hiddenName : 'cust_base_name',
							fieldLabel : '客户群组',
							labelStyle: 'text-align:right;',
							triggerAction : 'all',
							xtype:'combo',
							store : boxstoreCluster,
							displayField : 'CUST_BASE_NAME',
							valueField : 'ID',
							mode : 'local',
							forceSelection : true,
							typeAhead : true,
							emptyText:'请选择',
							resizable : true,
							anchor : '90%'
				}			 	
				]
			}
		],
		
			buttonAlign:'center',
			buttons:[
			{
				text:'查询',
				handler:function(){
					var validateForm = depAndLonIncreSearchPanel.getForm();
					var flag = submitValidate(orgTreePanel,validateForm);
					if(!flag){
						return;
					}
					var parameters = depAndLonIncreSearchPanel.getForm().getValues(false);
					
					depAndLonIncreStore.removeAll();
					depAndLonIncreStore.baseParams={
									start:0,
									limit:pagesize_combo.getValue(),	
									'condition':Ext.encode(parameters)
					};					
					depAndLonIncreStore.load();
					
				}
			},
			{
				text:'高级查询',
				handler:function(){				
					depAndLonIncreaseWindow.show();
				}				
			}
			,
			{
				text:'重置',
				handler:function(){									
					depAndLonIncreSearchPanel.getForm().reset();
					orgTreePanel.root.getUI().toggleCheck(false);
					depAndLonIncreSearchPanel.getForm().findField("checkedNodes").setValue("");	
					depAndLonIncreSearchPanel.getForm().findField("level").setValue("");	
				}
			}
			]
		}
		
	 ]
	}
	);
	
	var depAndLonIncreaseWindow = new Ext.Window(//新增一个特征项 弹出window
	{		
//		width:1000,
//		height:450,
		closeAction:'hide',
		closable:true,
		title:'存贷款增减高级查询',
		maximizable:false,
		maximized:true,
		buttonAlign:'right',
		border:false,
		layout:'fit',
		draggable:false,
		collapsible:false,
		titleCollapse:false,
		items:
		new Ext.form.FormPanel(
				{//查询panel
				height:130,
				id:'depAndLonIncreaseAdvanceSearch',
				labelWidth:120,//label的宽度
				labelAlign:'right',
				frame:true,
				region:'north',
				split:true,
				items:[
				{
					layout:'column',
					items:[
					{
					 columnWidth:.25,
					 layout:'form',
					 items:[
					 	{
							xtype:'combo',					
							store : new Ext.data.SimpleStore({
								fields : [],
								data : [ [] ]
							}),
							name:'instn_no',
							emptyText : '请选择...',
							fieldLabel : '*机构',
							anchor : '90%',
							mode : 'local',
							resizable:true,
							editable:false,
							triggerAction : 'all',
							maxHeight : 390,
							// 下拉框的显示模板,addDeptTreeDiv作为显示下拉树的容器
							tpl:"<tpl for='.' <div style='height:390px'> <div id='advanceOrgTreeDiv'></div></div></tpl>",
		//					tpl:"<div style='height:390px'> <div id='addOrgTreeDivForAdd'></div></div>",
							onSelect : Ext.emptyFn,
							listeners:{
								'expand':function(combo){			
		//							combo.doLayout();
									orgTreePanelAdvance.render('advanceOrgTreeDiv');
								},
								'collapse':function(combo){
										var checkedString = checkText(orgTreePanelAdvance);
										combo.setValue(checkedString);
								}
							}
						},
					 	{
							columnWidth:.25,
							xtype:'combo',
							name:'instn_level',
							hiddenName:'instn_level',
							fieldLabel:'*机构层次',
							anchor:'90%',
							editable:false,
							resizable:true,
							mode:'local',
							triggerAction:'all',
							store: new Ext.data.Store({	
							sortInfo:{
								field:'key',
								direction:'ASC'
							},
					        autoLoad:true,
					        proxy:new Ext.data.HttpProxy({
					        	url:basepath+'/lookup.json?name=UNIT_LEVEL',
					        	method:'GET'
					        }),
					        reader:new Ext.data.JsonReader({
					        	root:'JSON'
					        },['key','value']),
					        fields: [
					            'key',
					            'value'
					        ],
					        listeners:{
					        	'load':function(){
					        		Ext.getCmp("depAndLonIncreaseAdvanceSearch").getForm().findField("instn_level").setValue("level_3");
					        	}	
					        }
					 	   }),
					 	   valueField:'key',
					 	   displayField:'value'
						},
					 	{
					 		xtype:'textfield',
					 		name:'level',
					 		hidden:true
					 	},					 	
						{
							columnWidth:.25,
							anchor:'90%',
							name:'cur_type',
							hiddenName:'cur_type',
							xtype:'combo',
							resizable:true,
							fieldLabel:'币种',
							triggerAction:'all',
							anchor:'90%',
							mode:'local',
							store: new Ext.data.Store({		
							sortInfo:{
								field:'key',
								direction:'ASC'
							},
					        autoLoad:true,
					        proxy:new Ext.data.HttpProxy({
					        	url:basepath+'/lookup.json?name=CUR_TYPE',
					        	method:'GET'
					        }),
					        reader:new Ext.data.JsonReader({
					        	root:'JSON'
					        },['key','value']),
					        fields: [
					            'key',
					            'value'
					        ],
					        listeners:{
					        	'load':function(){
					        		
					        		Ext.getCmp("depAndLonIncreaseAdvanceSearch").getForm().findField("cur_type").setValue("01");
					        	}
					        }
					 	   }),
					       valueField:'key',
					       displayField:'value'					
					    },
						{
							hiddenName : 'roll_name',
							fieldLabel : '客户名单',
							labelStyle: 'text-align:right;',
							triggerAction : 'all',
							xtype:'combo',
							store : boxstoreRoll,
							displayField : 'ROLL_NAME',
							valueField : 'ROLL_ID',
							mode : 'local',
							forceSelection : true,
							emptyText:'请选择',
							typeAhead : true,
							resizable : true,
							anchor : '90%'
						},					    
					 	{
							columnWidth:.25,
							xtype:'combo',
							name:'cust_typ',
							hiddenName:'cust_typ',
							fieldLabel:'行业大类',
							anchor:'90%',
							emptyText:'全部',
							resizable:true,
							mode:'local',
							triggerAction:'all',
							store: new Ext.data.Store({			        
					        autoLoad:true,
					        sortInfo:{
					        	field:'key',
					        	direction:'ASC'
					        },
					        proxy:new Ext.data.HttpProxy({
					        	url :basepath+'/lookup.json?name=HYFL',
					        	method:'GET'
					        }),
					        reader:new Ext.data.JsonReader({
					        	root:'JSON'
					        },['key','value']),
					        fields: [
					            'key',
					            'value'
					        ]			        
					 	   }),
					 	   valueField:'key',
					 	   displayField:'value'							
						},
					 	{
							columnWidth:.25,
							xtype:'combo',
							name:'cust_typ_desc',
							hiddenName:'cust_typ_desc',
							fieldLabel:'行业小类',
							anchor:'90%',
							emptyText:'全部',
							mode:'local',
							resizable:true,
							triggerAction:'all',
							store: new Ext.data.Store({			        
					        autoLoad:true,
					        sortInfo:{
					        	field:'key',
					        	direction:'ASC'
					        },
					        proxy:new Ext.data.HttpProxy({
					        	url :basepath+'/lookup.json?name=HYMXFL',
					        	method:'GET'
					        }),
					        reader:new Ext.data.JsonReader({
					        	root:'JSON'
					        },['key','value']),
					        fields: [
					            'key',
					            'value'
					        ]			        
					 	   }),
					 	   valueField:'key',
					 	   displayField:'value'							
						},
					 	{
							columnWidth:.25,
							xtype:'textfield',
							name:'dep_bal_from',							
							fieldLabel:'存款时点余额起始',
							anchor:'90%'
						},
					 	{
							columnWidth:.25,
							xtype:'textfield',
							name:'dep_bal_to',							
							fieldLabel:'存款时点余额截止',
							anchor:'90%'
						},
					 	{
							columnWidth:.25,
							xtype:'textfield',
							name:'dep_year_from',							
							fieldLabel:'存款年均余额起始',
							anchor:'90%'
						},
					 	{
							columnWidth:.25,
							xtype:'textfield',
							name:'dep_year_to',							
							fieldLabel:'存款年均余额截止',
							anchor:'90%'
						}						
						]
					 },			 
					 {			columnWidth:.5,
								layout:'column',
								items:[
								
									{
										layout:'form',
										labelWidth:120,
										columnWidth:.5,
										items:[
										{
										name:'crm_dt',
										anchor:'90%',
										xtype:'datefield',
										format:'Y-m-d',
										value:lastDay,
										fieldLabel:'*统计日期'
										},
										{
											name:'caculate_type',
											hiddenName:'caculate_type',
											anchor:'90%',
											xtype:'combo',
											format:'Y-m-d',
											editable:false,
											fieldLabel:'*对比口径',
											mode:'local',
											resizable:true,
											triggerAction:'all',
											store: new Ext.data.Store({		
											sortInfo:{
												field:'key',
												direction:'ASC'
											},
									        autoLoad:true,
									        proxy:new Ext.data.HttpProxy({
									        	url:basepath+'/lookup.json?name=CACULATE_TYPE',
									        	method:'GET'
									        }),
									        reader:new Ext.data.JsonReader({
									        	root:'JSON'
									        },['key','value']),
									        fields: [
									            'key',
									            'value'
									        ],
									        listeners:{
									        	'load':function(){
									        		Ext.getCmp("depAndLonIncreaseAdvanceSearch").getForm().findField("caculate_type").setValue("01");
									        	}
									        }
									 	   }),
									 	   valueField:'key',
									 	   displayField:'value'
										},
										{
											name:'sts',
											hiddenName:'sts',
											anchor:'90%',
											xtype:'combo',
											fieldLabel:'客户状态',
											emptyText:'全部',
											resizable:true,
											mode:'local',
											triggerAction:'all',
											store: new Ext.data.Store({			        
									        autoLoad:true,
									        sortInfo:{
									        	field:'key',
									        	direction:'ASC'
									        },
									        proxy:new Ext.data.HttpProxy({
									        	url :basepath+'/lookup.json?name=KHZT',
									        	method:'GET'
									        }),
									        reader:new Ext.data.JsonReader({
									        	root:'JSON'
									        },['key','value']),
									        fields: [
									            'key',
									            'value'
									        ]			        
									 	   }),
									 	   valueField:'key',
									 	   displayField:'value'										
										},
										{
											hiddenName : 'cust_base_name',
											fieldLabel : '客户群组',
											labelStyle: 'text-align:right;',
											triggerAction : 'all',
											xtype:'combo',
											store : boxstoreCluster,
											displayField : 'CUST_BASE_NAME',
											valueField : 'ID',
											mode : 'local',
											forceSelection : true,
											typeAhead : true,
											emptyText:'请选择',
											resizable : true,
											anchor : '90%'
										},										
										{
										name:'cust_big_lev',
										hiddenName:'cust_big_lev',
										anchor:'90%',
										xtype:'combo',
										resizable:true,
										format:'Y-m-d',
										fieldLabel:'大客户级别',
										triggerAction:'all',
										mode:'local',
										emptyText:'全部',
										store: new Ext.data.Store({
										sortInfo:{
											field:'key',
											direction:'ASC'
										},
										restful:true,   
										autoLoad :true,
										proxy : new Ext.data.HttpProxy({
												url :basepath+'/lookup.json?name=CUST_LEVEL1',
												method:'GET'
											}),
											reader : new Ext.data.JsonReader({
												root : 'JSON'
											}, [ 'key', 'value' ])
										}),
										valueField:'key',
										displayField:'value'
										},
										{
										name:'cust_small_lev',
										hiddenName:'cust_small_lev',
										anchor:'90%',
										xtype:'combo',
										mode:'local',
										format:'Y-m-d',
										emptyText:'全部',
										resizable:true,
										triggerAction:'all',
										fieldLabel:'中小客户级别',
										store: new Ext.data.Store({
										sortInfo:{
											field:'key',
											direction:'ASC'
										},
										restful:true,   
										autoLoad :true,
										proxy : new Ext.data.HttpProxy({
												url :basepath+'/lookup.json?name=CUST_LEVEL2',
												method:'GET'
											}),
											reader : new Ext.data.JsonReader({
												root : 'JSON'
											}, [ 'key', 'value' ])
										}),
										valueField:'key',
										displayField:'value'
										},
									 	{
											columnWidth:.25,
											xtype:'textfield',
											name:'lon_bal_from',							
											fieldLabel:'贷款时点余额起始',
											anchor:'90%'
										},
									 	{
											columnWidth:.25,
											xtype:'textfield',
											name:'lon_bal_to',							
											fieldLabel:'贷款时点余额截止',
											anchor:'90%'
										},
									 	{
											columnWidth:.25,
											xtype:'textfield',
											name:'lon_year_from',							
											fieldLabel:'贷款年均余额起始',
											anchor:'90%'
										},
									 	{
											columnWidth:.25,
											xtype:'textfield',
											name:'lon_year_to',							
											fieldLabel:'贷款年均余额截止',
											anchor:'90%'
										}										
										]
									},
									{
										layout:'form',
										labelWidth:120,
										columnWidth:.5,
										items:[

										{
										name:'cust_name',
										anchor:'90%',
										xtype:'textfield',
										format:'Y-m-d',
										fieldLabel:'客户名称'
										},										
										{
										name:'cust_zzdm',
										anchor:'90%',
										xtype:'textfield',
										fieldLabel:'组织机构代码'
										},
										{
											name:'cust_lev',
											hiddenName:'cust_lev',
											anchor:'90%',
											xtype:'combo',
											fieldLabel:'客户评级',
											emptyText:'全部',
											mode:'local',
											resizable:true,
											triggerAction:'all',
											store: new Ext.data.Store({			        
									        autoLoad:true,
									        sortInfo:{
									        	field:'key',
									        	direction:'ASC'
									        },
									        proxy:new Ext.data.HttpProxy({
									        	url:basepath+'/lookup.json?name=CUST_LEVEL4',
									        	method:'GET'
									        }),
									        reader:new Ext.data.JsonReader({
									        	root:'JSON'
									        },['key','value']),
									        fields: [
									            'key',
									            'value'
									        ]			        
									 	   }),
									 	   valueField:'key',
									 	   displayField:'value'										
										},
									 	{
											columnWidth:.25,
											xtype:'textfield',
											name:'dep_cy',							
											fieldLabel:'存款时点增减大于等于',
											anchor:'90%'
										},
									 	{
											columnWidth:.25,
											xtype:'textfield',
											name:'dep_avg_cy',							
											fieldLabel:'存款月均增减大于等于',
											anchor:'90%'
										},
									 	{
											columnWidth:.25,
											xtype:'textfield',
											name:'dep_qua_cy',							
											fieldLabel:'存款季均增减大于等于',
											anchor:'90%'
										},
									 	{
											columnWidth:.25,
											xtype:'textfield',
											name:'dep_year_cy',							
											fieldLabel:'存款年均增减大于等于',
											anchor:'90%'
										},
									 	{
											columnWidth:.25,
											xtype:'textfield',
											name:'dep_year_cy_11',							
											fieldLabel:'存款年均(较上年末11天日均)增减大于等于',
											anchor:'90%'
										}										
										]							
									}
								]
					 },			 
					 {
					 	columnWidth:.25,
					 	layout:'form',
					 	labelWidth:80,
						items:[	
						{
									name:'scope',
									hiddenName:'scope',
									xtype:'combo',
									anchor:'90%',
									fieldLabel:'客户规模',
									emptyText:'全部',
									mode:'local',
									resizable:true,
									triggerAction:'all',
									store: new Ext.data.Store({			        
							        autoLoad:true,
							        sortInfo:{
							        	field:'key',
							        	direction:'ASC'
							        },
							        proxy:new Ext.data.HttpProxy({
							        	url :basepath+'/lookup.json?name=QYGM',
							        	method:'GET'
							        }),
							        reader:new Ext.data.JsonReader({
							        	root:'JSON'
							        },['key','value']),
							        fields: [
							            'key',
							            'value'
							        ]			        
							 	   }),
							 	   valueField:'key',
							 	   displayField:'value'									
					 	},
						{
									name:'crm_scope',
									hiddenName:'crm_scope',
									xtype:'combo',
									anchor:'90%',
									fieldLabel:'考核口径客户规模',
									emptyText:'全部',
									resizable:true,
									mode:'local',
									triggerAction:'all',
									store: new Ext.data.Store({			        
							        autoLoad:true,
							        sortInfo:{
							        	field:'key',
							        	direction:'ASC'
							        },
							        proxy:new Ext.data.HttpProxy({
							        	url :basepath+'/lookup.json?name=KHQYGM',
							        	method:'GET'
							        }),
							        reader:new Ext.data.JsonReader({
							        	root:'JSON'
							        },['key','value']),
							        fields: [
							            'key',
							            'value'
							        ]			        
							 	   }),
							 	   valueField:'key',
							 	   displayField:'value'									
					 	},
					 	{
							columnWidth:.25,
							xtype:'textfield',
							name:'lon_cy',							
							fieldLabel:'贷款时点增减大于等于',
							anchor:'90%'
						},
					 	{
							columnWidth:.25,
							xtype:'textfield',
							name:'lon_avg_cy',							
							fieldLabel:'贷款月均增减大于等于',
							anchor:'90%'
						},
					 	{
							columnWidth:.25,
							xtype:'textfield',
							name:'lon_qua_cy',							
							fieldLabel:'贷款季均增减大于等于',
							anchor:'90%'
						},
					 	{
							columnWidth:.25,
							xtype:'textfield',
							name:'lon_year_cy',							
							fieldLabel:'贷款年均增减大于等于',
							anchor:'90%'
						},
					 	{
							columnWidth:.25,
							xtype:'textfield',
							name:'lon_year_cy_11',							
							fieldLabel:'贷款年均(较上年末11天日均)增减大于等于',
							anchor:'90%'
						},{
							name:'checkedNodes',
							xtype:'textfield',
							hidden:true
						}					 	
					  ]
					}
				],
					buttonAlign:'center',
					buttons:[
						{
							text:'查询',
							handler:function(){
								var validateForm = Ext.getCmp("depAndLonIncreaseAdvanceSearch").getForm();
								var flag = submitValidate(orgTreePanelAdvance,validateForm);//执行非空校验
								if(!flag){
									return;
								}								
								var parameters = Ext.getCmp("depAndLonIncreaseAdvanceSearch").getForm().getValues(false);
								depAndLonIncreStore.removeAll();
								depAndLonIncreStore.baseParams={
												start:0,
												limit:pagesize_combo.getValue(),									
												'condition':Ext.util.JSON.encode(parameters)
								
								};
								
								depAndLonIncreStore.load();
								
								depAndLonIncreaseWindow.hide();
								
							}
						},
						{
							text:'重置',							
							handler:function()
							{
								Ext.getCmp("depAndLonIncreaseAdvanceSearch").getForm().reset();	
								Ext.getCmp("depAndLonIncreaseAdvanceSearch").getForm().findField("checkedNodes").setValue("");
								Ext.getCmp("depAndLonIncreaseAdvanceSearch").getForm().findField("level").setValue("");
								orgTreePanelAdvance.root.getUI().toggleCheck(false);

							}
						},
						{
							text:'返 回',
							handler:function(){
								depAndLonIncreaseWindow.hide();
							}
						}
					]
				}
				
			 ]
			}
			),
		buttonAlign:'center'	
	}
	);
	
	

	var depAndLonIncreColumns = new Ext.grid.ColumnModel([//gridtable中的列定义
	 new Ext.grid.RowNumberer(),
	{header:'统计日期',dataIndex:'crm_dt',sortable:true,id:'crm_dt'},
//	{header:'机构号',dataIndex:'instn_no',id:'instn_no',width:150},
	{header:'客户名称',dataIndex:'cust_name',sortable:true,id:'cust_name'},
	{header:'组织机构代码',dataIndex:'cust_zzdm',sortable:true,id:'cust_zzdm'},
	{header:'机构号',dataIndex:'instn_level_id',sortable:true,id:'instn_level_id',width:150},	
	{header:'机构名称',dataIndex:'unitname',sortable:true,id:'instn_name',width:150},	
	{header:'客户状态',dataIndex:'sts_GP',id:'sts',sortable:true,width:150},
	{header:'客户规模',dataIndex:'scope_GP',sortable:true,id:'scope'},
	{header:'考核口径客户规模',dataIndex:'crm_scope_GP',sortable:true,id:'crm_scope'},
	{header:'行业大类',dataIndex:'cust_typ_GP',sortable:true,id:'cust_typ',width:200},
	{header:'行业小类',dataIndex:'cust_typ_desc_GP',sortable:true,id:'cust_typ_desc',width:200},
	{header:'客户类型',dataIndex:'cust_crm_typ',sortable:true,id:'cust_crm_typ'},
	{header:'大客户级别',	  dataIndex:'cust_big_lev_GP',sortable:true,id:'cust_big_lev'},
	{header:'中小客户级别',dataIndex:'cust_small_lev_GP',sortable:true,id:'cust_small_lev',align:'right'},
	{header:'存款时点余额',dataIndex:'dep_bal',sortable:true,width:200,align:'right',renderer: money('0,000.00')},
	{header:'存款月均余额',dataIndex:'dep_avg',sortable:true,width:200,align:'right',renderer: money('0,000.00')},
	{header:'存款季均余额',dataIndex:'dep_qua',width:200,sortable:true,align:'right',renderer: money('0,000.00')},
	{header:'存款年均余额',dataIndex:'dep_year',width:200,align:'right',sortable:true,renderer: money('0,000.00')},

	{header:'贷款时点余额',dataIndex:'lon_bal',width:200,align:'right',sortable:true,renderer: money('0,000.00')},
	{header:'贷款月均余额',dataIndex:'lon_avg',width:200,align:'right',sortable:true,renderer: money('0,000.00')},
	{header:'贷款季均余额',dataIndex:'lon_qua',width:200,align:'right',sortable:true,renderer: money('0,000.00')},
	{header:'贷款年均余额',dataIndex:'lon_year',width:200,align:'right',sortable:true,renderer: money('0,000.00')},
	
	{header:'对比口径',dataIndex:'caculate_type_desc',sortable:true,width:100,align:'right'},

	{header:'存款时点增量',dataIndex:'dep_cy',width:200,align:'right',sortable:true,renderer: money('0,000.00')},
	{header:'存款月均增量',dataIndex:'dep_avg_cy',width:200,align:'right',sortable:true,renderer: money('0,000.00')},
	{header:'存款季均增量',dataIndex:'dep_qua_cy',width:200,align:'right',sortable:true,renderer: money('0,000.00')},
	{header:'存款年均增量',dataIndex:'dep_year_cy',width:200,align:'right',sortable:true,renderer: money('0,000.00')},
	{header:'存款年均(较上年末11天日均)增量',dataIndex:'dep_year_cy_11',sortable:true,width:350,align:'right',renderer: money('0,000.00')},	

	{header:'贷款时点增量',dataIndex:'lon_cy',width:200,align:'right',sortable:true,renderer: money('0,000.00')},
	{header:'贷款月均增量',dataIndex:'lon_avg_cy',width:200,align:'right',sortable:true,renderer: money('0,000.00')},
	{header:'贷款季均增量',dataIndex:'lon_qua_cy',width:200,align:'right',sortable:true,renderer: money('0,000.00')},
	{header:'贷款年均增量',dataIndex:'lon_year_cy',width:200,align:'right',sortable:true,renderer: money('0,000.00')},
	{header:'贷款年均(较上年末11天日均)增量',dataIndex:'lon_year_cy_11',sortable:true,width:350,align:'right',renderer: money('0,000.00')}	
	]);
	
	var depAndLonIncreRecord = new Ext.data.Record.create([
	{name:'crm_dt'},
	{name:'instn_level_id'},
	{name:'unitname'},
	{name:'cust_zzdm'},
	{name:'cust_id'},
	{name:'cust_name'},
	{name:'sts_GP'},
	{name:'scope_GP'},
	{name:'crm_scope_GP'},
	{name:'cust_typ_GP'},
	{name:'cust_typ_desc_GP'},	
	{name:'cust_crm_typ'},
	{name:'cust_big_lev_GP'},
	{name:'cust_small_lev_GP'},
	{name:'dep_bal',type:'float'},
	{name:'dep_avg',type:'float'},
	{name:'dep_qua',type:'float'},
	{name:'dep_year',type:'float'},
	{name:'lon_bal',type:'float'},
	{name:'lon_avg',type:'float'},
	{name:'lon_qua',type:'float'},
	{name:'lon_year',type:'float'},
	{name:'caculate_type_desc'},
	{name:'dep_cy',type:'float'},
	{name:'dep_avg_cy',type:'float'},
	{name:'dep_qua_cy',type:'float'},
	{name:'dep_year_cy',type:'float'},	
	{name:'dep_year_cy_11',type:'float'},
	{name:'lon_cy',type:'float'},
	{name:'lon_avg_cy',type:'float'},
	{name:'lon_qua_cy',type:'float'},
	{name:'lon_year_cy',type:'float'},
	{name:'lon_year_cy_11',type:'float'}
	
	]);
	var depAndLonIncreReader = new Ext.data.JsonReader({//读取json数据的panel
		totalProperty:'json.count',
		root:'json.data'
	},depAndLonIncreRecord);
	
	var depAndLonIncreStore = new Ext.data.Store(
	{
		proxy:new Ext.data.HttpProxy({
			url:basepath+'/dep-and-lon-increase!index',
			method:'POST'
		}),
		reader:depAndLonIncreReader
	}
	);
	

 
    var pagesize_combo = new Ext.form.ComboBox({
         name : 'pagesize',
         triggerAction : 'all',
         mode : 'local',
         store : new Ext.data.ArrayStore({
             fields : ['value', 'text'],
             data : [[100, '100条/页'], [200, '200条/页'], [500, '500条/页'], [1000, '1000条/页']]
         }),
         valueField : 'value',
         displayField : 'text',
         value : '100',
         editable : false,
         width : 85
     });
    
    var number = parseInt(pagesize_combo.getValue());
    
    pagesize_combo.on("select", function(comboBox) {    
     	  var baseP = Ext.util.JSON.encode(depAndLonIncreStore.baseParams);
     	  var tempJson = Ext.util.JSON.decode(baseP);
     	  var condition = tempJson.condition;
     	  if(condition==null || condition=="" || condition==undefined ){
     	  	return;
     	  }
    	  bbar.pageSize = parseInt(pagesize_combo.getValue());
		  depAndLonIncreStore.reload({
					method:'POST',
					params : {
						start : 0,
						limit : parseInt(pagesize_combo.getValue())
					}
				});
	});             
	var bbar = new Ext.PagingToolbar({
        pageSize : number,
        store : depAndLonIncreStore,
        displayInfo : true,
        displayMsg : '显示{0}条到{1}条,共{2}条',
        emptyMsg : "没有符合条件的记录",
        items : ['-', '&nbsp;&nbsp;', pagesize_combo
                 ]
    });	
	
	var depAndLonIncreGrid =  new Ext.grid.GridPanel({//产品列表数据grid
		frame:true,		
//		autoScroll:true,
		id:'DepAndLonIncreListGrid',
		loadMask : {
			msg : '正在加载表格数据,请稍等...'
		},
//		tbar: [new Com.yucheng.bob.ExpButton({
//			formPanel:depAndLonIncreSearchPanel,
//			url:basepath+'/dep-and-lon-increase.json'
//		})],
		sm:new Ext.grid.RowSelectionModel({
			singleSelect:true
		}),

		store:depAndLonIncreStore,
		cm :depAndLonIncreColumns,
		bbar:bbar
	
	});
	
	depAndLonIncreGrid.on('rowdblclick', function(grid, rowIndex, event) {
		var checkedNodes = grid.getSelectionModel().selections.items;
		if(checkedNodes.length==0)
			{
				Ext.Msg.alert('提示', '请选择一条记录.');
				return ;
			}
		  var custId = grid.getSelectionModel().selections.items[0].data.cust_id;
		  window.location.href = basepath+'/contents/pages/customer/customerManager/customerBaseInformation.jsp?customerId='+custId;
	});
	
//	depAndLonIncreStore.baseParams={
//		start:0,
//		limit:pagesize_combo.getValue()
//	};
//	
//	depAndLonIncreStore.load();
	
	var view = new Ext.Viewport({//页面展示
//		layout:'fit',		
//		items:[
//			{
//				region:'west',
//				width:200,
//				items:orgTreePanel
//			},
//			{
//					region:'center',
//					layout:'border',
					autoScroll:true,
					items:
					[
						depAndLonIncreSearchPanel,
						{
							autoScroll:true,
							width: document.body.scrollWidth-3,							
							height: document.body.scrollHeight-140,
							layout:'fit',
							title:'客户存贷款增减查询列表',
							items:[depAndLonIncreGrid]
						}
					]				
//			}
//			]
//		
		

	});	

});