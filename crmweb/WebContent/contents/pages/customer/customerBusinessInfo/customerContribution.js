/*
 * 客户贡献度查询
 * 
 * 姚亮
 * */

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
	 
	 function checkText(orgTreePanel_p){
	 	
	 	var checkTextString="";
	 	if(orgTreePanel_p.root.getUI().isChecked()){
	 		var rootId = orgTreePanel_p.root.id;
	 		var text = orgTreePanel_p.root.text;
	 		checkTextString = checkTextString+text;
	 		return checkTextString	 		
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
//	 		return checkLevel;	 	
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
//	 var checkNode;
	 var orgTreePanel = new Ext.tree.TreePanel(
	 	{	 		
	 	autoScroll:true,
//	 	enableDD:true,
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
				
//				var level = spanBank(orgTreePanel);
//				var checkedNodes = getCheckedNode(orgTreePanel);
//				customerContributionSearchPanel.getForm().findField("level").setValue(level);
//				customerContributionSearchPanel.getForm().findField("checkedNodes").setValue(checkedNodes);				
//				tempCombo = customerContributionSearchPanel.getForm().findField("instn_no");
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
//				tempCombo = Ext.getCmp("depAndLonContriAdvanceForm").getForm().findField("instn_no");
//				tempCombo.setRawValue(node.text);
//				tempCombo.collapse();
//				var level = spanBank(orgTreePanelAdvance);
//				var checkedNodes = getCheckedNode(orgTreePanelAdvance);
//				Ext.getCmp("depAndLonContriAdvanceForm").getForm().findField("checkedNodes").setValue(checkedNodes);				
//				Ext.getCmp("depAndLonContriAdvanceForm").getForm().findField("level").setValue(level);				
				
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

	 
	var customerContributionSearchPanel = new Ext.form.FormPanel({//查询panel
		height:100,
		labelWidth:80,//label的宽度
		labelAlign:'right',
		frame:true,
//		region:'north',
		split:true,
		items:[
		{
			layout:'column',
			items:[

			{
				layout:'form',
				labelWidth:80,
				columnWidth:.25,
				items:[
				 	{
						xtype:'combo',					
						store : new Ext.data.SimpleStore( {
							fields : [],
							data : [ [] ]
						}),
//						title :'请选择...',
						name:'instn_no',
						emptyText : '请选择...',
						resizable :true,
						fieldLabel : '*机构',
						anchor : '90%',
						editable:false,
						mode : 'local',
						triggerAction : 'all',
						maxHeight : 390,
						// 下拉框的显示模板,addDeptTreeDiv作为显示下拉树的容器
						tpl:"<tpl for='.' <div style='height:390px'> <div id='addOrgTreeDivForAdd'></div></div></tpl>",
	//					tpl:"<div style='height:390px'> <div id='addOrgTreeDivForAdd'></div></div>",
						onSelect : Ext.emptyFn,
						listeners:{
							'expand':function(combo){			
	//							combo.doLayout();
								orgTreePanel.render('addOrgTreeDivForAdd');
							},
							'collapse':function(combo){
								var checkedString = checkText(orgTreePanel);
								combo.setValue(checkedString);
							}
						}
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
					name:'crm_dt',								
					anchor:'90%',
					xtype:'datefield',
					value:lastDay,
					format:'Y-m-d',
					fieldLabel:'*统计日期'
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
			 },			 
			{
				layout:'form',
				labelWidth:80,
				columnWidth:.25,
				items:[
				 	{
						xtype:'combo',
						name:'instn_level',
						hiddenName:'instn_level',
						fieldLabel:'*机构层次',
						anchor:'90%',
						resizable:true,
						editable:false,
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
				        		customerContributionSearchPanel.getForm().findField("instn_level").setValue("level_3");
				        	}	
				        }
				 	   }),
				 	   valueField:'key',
				 	   displayField:'value'
					}
			
				]							
			},
			 
			 {
			 	columnWidth:.25,
			 	layout:'form',
			 	labelWidth:80,
				items:[	
								{
								name:'cust_name',								
								anchor:'90%',
								xtype:'textfield',
								format:'Y-m-d',
								fieldLabel:'客户名称'
								},
								{
									name:'checkedNodes',
									xtype:'textfield',
									hidden:true
								},
								{
									name:'level',
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

					
					var level = spanBank(orgTreePanel);
					if(level ==null || level =="" || level == undefined ){
						Ext.MessageBox.alert('提示','请选择机构.');			
						customerContributionSearchPanel.getForm().findField("checkedNodes").setValue("");						
						return;
					}
					else 
					{
						var crm_dt = customerContributionSearchPanel.getForm().findField("crm_dt").getValue();
						var org_level = customerContributionSearchPanel.getForm().findField("instn_level").value;
						
						if(crm_dt == null || crm_dt==""||crm_dt==undefined){
							Ext.MessageBox.alert('提示','请选择统计日期.');	
							return;
						}
						if(org_level == null || org_level==""||org_level==undefined){
							Ext.MessageBox.alert('提示','请选择机构层次.');	
							return;
						}						
						
						var checkedNodes = getCheckedNode(orgTreePanel);
						customerContributionSearchPanel.getForm().findField("level").setValue(level);
						customerContributionSearchPanel.getForm().findField("checkedNodes").setValue(checkedNodes);		
						
						var instn_level = customerContributionSearchPanel.getForm().findField("instn_level").value;
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
							return;
						}												
					}
					var parameters = customerContributionSearchPanel.getForm().getValues(false);
					customerContributionStore.removeAll();
					customerContributionStore.baseParams={
									start:0,
									limit:parseInt(pagesize_combo.getValue()),									
									'condition':Ext.util.JSON.encode(parameters)
					};
					customerContributionStore.load();
				}				
			},
			{
				text:'高级查询',
				handler:function(){
					depAndLonContriAdvanceWindow.show();				

				}
			},
			{
				text:'重置',
				handler:function(){
					
					customerContributionSearchPanel.getForm().findField("checkedNodes").setValue("");
					customerContributionSearchPanel.getForm().findField("level").setValue("");		
					orgTreePanel.root.getUI().toggleCheck(false);
					customerContributionSearchPanel.getForm().reset();
				}
			}
			]
		}
		
	 ]
	}
	);

	var depAndLonContriAdvanceWindow = new Ext.Window(//新增一个特征项 弹出window
	{		
		width:1200,
		height:450,
		closeAction:'hide',
		closable:true,
		buttonAlign:'right',
		maximized:true,
		border:false,
		title:'客户贡献度高级查询',
		layout:'fit',
		draggable:false,
		collapsible:false,
		titleCollapse:false,
		items:
		new Ext.form.FormPanel(
				{//查询panel
				height:130,
				id:'depAndLonContriAdvanceForm',
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
							store : new Ext.data.SimpleStore( {
								fields : [],
								data : [ [] ]
							}),
							name:'instn_no',
							emptyText : '请选择...',
							fieldLabel : '*机构',
							anchor : '90%',
							editable:false,
							resizable:true,
							mode : 'local',
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
							xtype:'combo',
							name:'instn_level',
							hiddenName:'instn_level',
							fieldLabel:'*机构层次',
							anchor:'90%',
							mode:'local',
							editable:false,
							resizable:true,
							triggerAction:'all',
							store: new Ext.data.Store({			        
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
					        		Ext.getCmp("depAndLonContriAdvanceForm").getForm().findField("instn_level").setValue("level_3");
					        	}	
					        }
					 	   }),
					 	   valueField:'key',
					 	   displayField:'value'
						},				
//						{
//							columnWidth:.25,
//							anchor:'90%',
//							name:'cur_type',
//							hiddenName:'cur_type',
//							xtype:'combo',
//							fieldLabel:'币种',
//							triggerAction:'all',
//							anchor:'90%',
//							emptyText:'全部',
//							mode:'local',
//							triggerAction:'all',
//							store: new Ext.data.Store({			        
//					        autoLoad:true,
//					        sortInfo:{
//					        	field:'key',
//					        	direction:'ASC'
//					        },
//					        proxy:new Ext.data.HttpProxy({
//					        	url:basepath+'/lookup.json?name=CUR_TYPE',
//					        	method:'GET'
//					        }),
//					        reader:new Ext.data.JsonReader({
//					        	root:'JSON'
//					        },['key','value']),
//					        fields: [
//					            'key',
//					            'value'
//					        ]			        
//					 	   }),
//					 	   valueField:'key',
//					 	   displayField:'value'					
//					    },
					 	{
							columnWidth:.25,
							xtype:'combo',
							name:'hy_class',
							hiddenName:'hy_class',
							fieldLabel:'行业大类',
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
							name:'hy_typ',
							hiddenName:'hy_typ',
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
							name:'cbal_from',							
							fieldLabel:'存款时点起始余额',
							anchor:'90%'
						},
					 	{
							columnWidth:.25,
							xtype:'textfield',
							name:'cbal_to',							
							fieldLabel:'存款时点截止余额',
							anchor:'90%'
						},
					 	{
							columnWidth:.25,
							xtype:'textfield',
							name:'avg_cbal_from',							
							fieldLabel:'存款年均起始余额',
							anchor:'90%'
						},
					 	{
							columnWidth:.25,
							xtype:'textfield',
							name:'avg_cbal_to',							
							fieldLabel:'存款年均截止余额',
							anchor:'90%'
						},
					 	{
							columnWidth:.25,
							xtype:'textfield',
							name:'mrcv',							
							fieldLabel:'中间业务贡献度大于',
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
										value:lastDay,
										format:'Y-m-d',
										fieldLabel:'*统计日期'
										},
										{
										name:'create_dt',
										anchor:'90%',
										xtype:'datefield',
										format:'Y-m-d',
										fieldLabel:'客户建立日期'
										},										
//										{
//										name:'caculate_type',
//										hiddeName:'caculate_type',
//										anchor:'90%',
//										xtype:'combo',
//										format:'Y-m-d',
//										fieldLabel:'统计口径',
//										emptyText:'全部',
//										mode:'local',
//										triggerAction:'all',
//										store: new Ext.data.Store({			        
//								        autoLoad:true,
//								        sortInfo:{
//								        	field:'key',
//								        	direction:'ASC'
//								        },
//								        proxy:new Ext.data.HttpProxy({
//								        	url:basepath+'/lookup.json?name=UNIT_LEVEL',
//								        	method:'GET'
//								        }),
//								        reader:new Ext.data.JsonReader({
//								        	root:'JSON'
//								        },['key','value']),
//								        fields: [
//								            'key',
//								            'value'
//								        ]			        
//								 	   }),
//								 	   valueField:'key',
//								 	   displayField:'value'										
//										},
										{
											name:'cust_sts',
											hiddenName:'cust_sts',
											anchor:'90%',
											xtype:'combo',
											fieldLabel:'客户状态',
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
										
//										{
//										name:'cust_big_lev',
//										hiddeName:'cust_big_lev',
//										anchor:'90%',
//										xtype:'combo',
//										format:'Y-m-d',
//										fieldLabel:'大客户级别',
//										emptyText:'全部',
//										mode:'local',
//										triggerAction:'all',
//										store: new Ext.data.Store({			        
//								        autoLoad:true,
//								        sortInfo:{
//								        	field:'key',
//								        	direction:'ASC'
//								        },
//								        proxy:new Ext.data.HttpProxy({
//								        	url:basepath+'/lookup.json?name=UNIT_LEVEL',
//								        	method:'GET'
//								        }),
//								        reader:new Ext.data.JsonReader({
//								        	root:'JSON'
//								        },['key','value']),
//								        fields: [
//								            'key',
//								            'value'
//								        ]			        
//								 	   }),
//								 	   valueField:'key',
//								 	   displayField:'value'										
//										},
//										{
//										name:'cust_small_lev',
//										hiddeName:'cust_small_lev',
//										anchor:'90%',
//										xtype:'combo',
//										format:'Y-m-d',
//										fieldLabel:'中小客户级别',
//										emptyText:'全部',
//										mode:'local',
//										triggerAction:'all',
//										store: new Ext.data.Store({			        
//								        autoLoad:true,
//								        sortInfo:{
//								        	field:'key',
//								        	direction:'ASC'
//								        },
//								        proxy:new Ext.data.HttpProxy({
//								        	url:basepath+'/lookup.json?name=UNIT_LEVEL',
//								        	method:'GET'
//								        }),
//								        reader:new Ext.data.JsonReader({
//								        	root:'JSON'
//								        },['key','value']),
//								        fields: [
//								            'key',
//								            'value'
//								        ]			        
//								 	   }),
//								 	   valueField:'key',
//								 	   displayField:'value'										
//										},
									 	{
											columnWidth:.25,
											xtype:'textfield',
											name:'dbal_from',							
											fieldLabel:'贷款时点起始余额',
											anchor:'90%'
										},
									 	{
											columnWidth:.25,
											xtype:'textfield',
											name:'dbal_to',							
											fieldLabel:'贷款时点截止余额',
											anchor:'90%'
										},
									 	{
											columnWidth:.25,
											xtype:'textfield',
											name:'davg_dbal_from',							
											fieldLabel:'贷款年均起始余额',
											anchor:'90%'
										},
									 	{
											columnWidth:.25,
											xtype:'textfield',
											name:'davg_dbal_to',							
											fieldLabel:'贷款年均截止余额',
											anchor:'90%'
										},
									 	{
											columnWidth:.25,
											xtype:'textfield',
											name:'dtzcon',							
											fieldLabel:'贷款贡献度（经济资本调整后模拟利润）大于',
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
//										{
//										name:'cust_lev',
//										anchor:'90%',
//										xtype:'textfield',
//										fieldLabel:'客户评级',
//										emptyText:'全部',
//										mode:'local',
//										triggerAction:'all',
//										store: new Ext.data.Store({			        
//								        autoLoad:true,
//								        sortInfo:{
//								        	field:'key',
//								        	direction:'ASC'
//								        },
//								        proxy:new Ext.data.HttpProxy({
//								        	url:basepath+'/lookup.json?name=UNIT_LEVEL',
//								        	method:'GET'
//								        }),
//								        reader:new Ext.data.JsonReader({
//								        	root:'JSON'
//								        },['key','value']),
//								        fields: [
//								            'key',
//								            'value'
//								        ]			        
//								 	   }),
//								 	   valueField:'key',
//								 	   displayField:'value'										
//										},
									 	{
											columnWidth:.25,
											xtype:'textfield',
											name:'rcv_from',							
											fieldLabel:'中间业务收入起始金额',
											anchor:'90%'
										},
									 	{
											columnWidth:.25,
											xtype:'textfield',
											name:'rcv_to',							
											fieldLabel:'中间业务收入截止金额',
											anchor:'90%'
										},
									 	{
											columnWidth:.25,
											xtype:'textfield',
											name:'cmncon_from',							
											fieldLabel:'存款贡献度(模拟利润)大于',
											anchor:'90%'
										},
									 	{
											columnWidth:.25,
											xtype:'textfield',
											name:'cmncon_to',							
											fieldLabel:'贷款贡献度(模拟利润)大于',
											anchor:'90%'
										}										
										]							
									}
								]
					 },			 
					 {
					 	columnWidth:.25,
					 	layout:'form',
					 	labelWidth:120,
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
							columnWidth:.25,
							xtype:'textfield',
							name:'mncon',							
							fieldLabel:'综合贡献度(模拟利润)大于',
							anchor:'90%'
						},
					 	{
							columnWidth:.25,
							xtype:'textfield',
							name:'tzcon',							
							fieldLabel:'综合贡献度（经济资本调整后模拟利润）大于',
							anchor:'90%'
						},
					 	{
							columnWidth:.25,
							xtype:'textfield',
							name:'mnjcon',							
							fieldLabel:'综合贡献度（模拟净利润）大于',
							anchor:'90%'
						},					 	
						{
							name:'checkedNodes',
							xtype:'textfield',
							hidden:true
						},
						{
							name:'level',
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
								var level = spanBank(orgTreePanelAdvance);
								if(level ==null || level == undefined || level=="" ){
									Ext.MessageBox.alert('提示','请选择机构.');
									Ext.getCmp("depAndLonContriAdvanceForm").getForm().findField("checkedNodes").setValue("");
									return;
								}
								else 
								{
									var crm_dt = Ext.getCmp("depAndLonContriAdvanceForm").getForm().findField("crm_dt").getValue();
									var org_level = Ext.getCmp("depAndLonContriAdvanceForm").getForm().findField("instn_level").getValue();
									if(crm_dt == null || crm_dt==""||crm_dt==undefined){
										Ext.MessageBox.alert('提示','请选择统计日期.');	
										return;
									}	
									if(org_level == null || org_level==""||org_level==undefined){
										Ext.MessageBox.alert('提示','请选择机构层次.');	
										return;
									}								
									
									var checkedNodes = getCheckedNode(orgTreePanelAdvance);
									Ext.getCmp("depAndLonContriAdvanceForm").getForm().findField("checkedNodes").setValue(checkedNodes);				
									Ext.getCmp("depAndLonContriAdvanceForm").getForm().findField("level").setValue(level);				
									
									var instn_level = Ext.getCmp("depAndLonContriAdvanceForm").getForm().findField("instn_level").value;
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
										return;
									}												
								}
//								var checkedNodes = getCheckedNode(orgTreePanelAdvance);
//								Ext.getCmp("depAndLonContriAdvanceForm").getForm().findField("checkedNodes").setValue(checkedNodes);				
								var parameters = Ext.getCmp("depAndLonContriAdvanceForm").getForm().getValues(false);
								customerContributionStore.removeAll();
								customerContributionStore.baseParams={
												start:0,
												limit:parseInt(pagesize_combo.getValue()),									
												'condition':Ext.util.JSON.encode(parameters)								
								};
								customerContributionStore.load();
								depAndLonContriAdvanceWindow.hide();
								
							}

						},
						{
							text:'重置',
							handler:function(){
								Ext.getCmp("depAndLonContriAdvanceForm").getForm().findField("checkedNodes").setValue("");
								Ext.getCmp("depAndLonContriAdvanceForm").getForm().findField("level").setValue("");
								Ext.getCmp("depAndLonContriAdvanceForm").getForm().reset();
								orgTreePanelAdvance.root.getUI().toggleCheck(false);
							}
						},						
						{
							text:'返回',
							handler:function(){
								depAndLonContriAdvanceWindow.hide();
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
	
	var customerContributionColumns = new Ext.grid.ColumnModel([//gridtable中的列定义
	 new Ext.grid.RowNumberer(),
	{header:'统计日期',dataIndex:'crm_dt',sortable:true,id:'crm_dt'},
	{header:'机构号',dataIndex:'instn_level_id',sortable:true,id:'instn_level_id',width:150},
	{header:'机构名称',dataIndex:'unitname',sortable:true,id:'unitname',width:150},
	{header:'客户名称',dataIndex:'cust_name',sortable:true,id:'cust_name',width:150},
	{header:'组织机构代码',dataIndex:'cust_zzdm',sortable:true,width:150,id:'cust_zzdm'},
	{header:'客户规模',dataIndex:'scope_GP',sortable:true,id:'scope'},
	{header:'考核口径客户规模',dataIndex:'crm_scope_GP', sortable:true,width:150,id:'crm_scope'},
	{header:'行业大类',dataIndex:'hy_class_GP',id:'hy_class',sortable:true,width:200},
	{header:'行业小类',dataIndex:'hy_typ_GP',id:'hy_typ',sortable:true,width:200},
	{header:'客户状态',	  dataIndex:'cust_sts_GP',sortable:true,id:'cust_sts'},
	{header:'客户建立日期',dataIndex:'create_dt',sortable:true,id:'create_dt',align:'right'},
	{header:'存款时点余额',dataIndex:'cbal',width:200,sortable:true,align:'right',renderer: money('0,000.00')},

	{header:'存款年均余额',dataIndex:'avg_cbal',width:200,sortable:true,align:'right',renderer: money('0,000.00')},
	{header:'贷款时点余额',dataIndex:'dbal',width:200,sortable:true,align:'right',renderer: money('0,000.00')},
	{header:'贷款年均余额',dataIndex:'davg_dbal',sortable:true,width:200,align:'right',renderer: money('0,000.00')},
	{header:'中间业务收入',dataIndex:'rcv',width:200,sortable:true,align:'right',renderer: money('0,000.00')},
	{header:'经济资本占用',dataIndex:'zbzy',width:200,sortable:true,align:'right',renderer: money('0,000.00')},
	{header:'营销成本费用',dataIndex:'cbfy',width:200,sortable:true,align:'right',renderer: money('0,000.00')},
	{header:'存款贡献度(模拟利润)',dataIndex:'cmncon',sortable:true,width:200,align:'right',renderer: money('0,000.00')},
	{header:'贷款贡献度(模拟利润)',dataIndex:'dmncon',sortable:true,width:200,align:'right',renderer: money('0,000.00')},

	{header:'贷款贡献度(经济资本调整后模拟利润)',sortable:true,dataIndex:'dtzcon',width:250,align:'right',renderer: money('0,000.00')},
	{header:'中间业务贡献度',dataIndex:'mrcv',sortable:true,width:200,align:'right',renderer: money('0,000.00')},
	{header:'综合贡献度(模拟利润)',dataIndex:'mncon',sortable:true,width:200,align:'right',renderer: money('0,000.00')},
	{header:'综合贡献度(经济资本调整后模拟利润)',sortable:true,dataIndex:'tzcon',width:250,align:'right',renderer: money('0,000.00')},
	{header:'综合贡献度(模拟净利润)',dataIndex:'mnjcon',sortable:true,width:200,align:'right',renderer: money('0,000.00')}
	]);
	var customerContributionRecord = new Ext.data.Record.create([
	{name:'cust_id'},
	{name:'instn_level_id'},
	{name:'unitname'},
	{name:'cust_name'},
	{name:'cust_zzdm'},
	{name:'scope'},
	{name:'scope_GP'},
	{name:'crm_scope'},
	{name:'crm_scope_GP'},
	{name:'hy_class'},
	{name:'hy_class_GP'},
	{name:'hy_typ'},	
	{name:'hy_typ_GP'},
	{name:'cust_sts_GP'},
	{name:'create_dt'},
	{name:'cbal',type:'float'},
	{name:'avg_cbal',type:'float'},
	
	{name:'dbal',type:'float'},
	{name:'davg_dbal',type:'float'},
	{name:'rcv',type:'float'},
	{name:'zbzy',type:'float'},
	{name:'cbfy',type:'float'},
	{name:'cmncon',type:'float'},
	{name:'dmncon',type:'float'},
	
	{name:'dtzcon',type:'float'},
	{name:'mrcv',type:'float'},
	{name:'mncon',type:'float'},
	{name:'tzcon',type:'float'},
	{name:'mnjcon',type:'float'},
	{name:'crm_dt'}
	]);
	var customerContributionReader = new Ext.data.JsonReader({//读取json数据的panel
	root:'json.data',
	totalProperty:'json.count'
	},customerContributionRecord);
	
	var customerContributionStore = new Ext.data.Store(
	{
		proxy:new Ext.data.HttpProxy({
			url:basepath+'/customer-contribution!index',
			method:'POST'			
		}),
		reader:customerContributionReader
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
    	  bbar.pageSize = parseInt(pagesize_combo.getValue());
    	  var param = Ext.util.JSON.encode(customerContributionStore.baseParams);
    	  var paramJson = Ext.util.JSON.decode(param);
    	  var condition = paramJson.condition;
    	  if(condition==null || condition == "" || condition == undefined){
    	  	return;
    	  }
		customerContributionStore.load({
					params : {
						start : 0,
						limit : parseInt(pagesize_combo.getValue())
					}
				});
	});
	
	var bbar = new Ext.PagingToolbar({
        pageSize : number,
        store : customerContributionStore,
        displayInfo : true,
        displayMsg : '显示{0}条到{1}条,共{2}条',
        emptyMsg : "没有符合条件的记录",
        items : ['-', '&nbsp;&nbsp;', pagesize_combo
                 ]
    });	
	
//	customerContributionStore.baseParams={
//		start:0,
//		limit:pagesize_combo.getValue()
//	};
//	
//	customerContributionStore.load();
	
	var customerContributionGrid =  new Ext.grid.GridPanel({//列表数据grid
		
//		title:'客户贡献度列表',
		
		frame:true,
		autoScroll:true,
		id:'customerContributionGrid',
		loadMask : {
			msg : '正在加载表格数据,请稍等...'
		},
		sm:new Ext.grid.RowSelectionModel({
			singleSelect:true
		}),
		store:customerContributionStore,

		cm :customerContributionColumns,
//		tbar: [new Com.yucheng.bob.ExpButton({
//			formPanel:customerContributionSearchPanel,
//			url:basepath+'/customer-contribution.json'
//		})],		
		bbar:bbar
	});

	customerContributionGrid.on('rowdblclick', function(grid, rowIndex, event) {
		var checkedNodes = grid.getSelectionModel().selections.items;
		if(checkedNodes.length==0)
			{
				Ext.Msg.alert('提示', '请选择一条记录.');
				return ;
			}
		  var custId = grid.getSelectionModel().selections.items[0].data.cust_id;
		  window.location.href = basepath+'/contents/pages/customer/customerManager/customerBaseInformation.jsp?customerId='+custId;
	});
	
	
	var view = new Ext.Viewport({//页面展示
//		layout:'fit',
//		items:[
//			{
//					layout:'border',
					autoScroll:true,
					items:
					[
					customerContributionSearchPanel,
					{
//						region:'center',
//						autoScroll:true,
//						layout:'fit',
//						width: document.body.scrollWidth-3,							
//						height: document.body.scrollHeight-80,						
//						title:'客户贡献度列表',
//						items:[customerContributionGrid]
						
							autoScroll:true,
						    width: document.body.scrollWidth,							
							height: document.body.scrollHeight-100,
//							height:350,
							layout:'fit',
							title:'客户贡献度列表',
							items:[customerContributionGrid]
						
					}
					
					]				
//			}
//		
//		]

	});	

});