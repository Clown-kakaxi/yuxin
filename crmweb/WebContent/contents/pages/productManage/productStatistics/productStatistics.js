Ext.onReady(function() {
    Ext.QuickTips.init(); 
	/*得到昨天的日期*/
	var today =  new Date();
	var year1 = today.getYear();
	var mon1 = today.getMonth()+1;
	var date1 = today.getDate(); 
	
	var lastDate = new Date(year1,mon1,date1-1);
	
	var year = lastDate.getYear();
	var month = lastDate.getMonth();
	var day = lastDate.getDate();
	var monthStr =month+""; 
	var dayStr=""+day ;
	if(day<10){
		dayStr=  "0"+dayStr; 
	}
	if(month<10){
		monthStr = "0"+month;
	}
	
	var lastDay = year+"-"+monthStr+"-"+dayStr;	 
	
    var msFlag=0;
	//*******************************
	 var instnLevelStore = new Ext.data.ArrayStore({
	        fields:['myId','displayText'],
	        data:[['1','全行'],['2','分行管理部'],['3','管辖直属行'],['4','基础网点']]
	    });
	 
	 var lvstore = new Ext.data.ArrayStore({
	        fields:['myId','displayText'],
	        data:[['1','一级类'],['2','二级类'],['3','三级类'],['4','四级类']]
	    });
	    /*******************************************************************************************/
	    var lv = 1;
	    var classflg=0; //0表示为选择产品分类层级，产品分类名称不需重载，1表示选择了
	    
	    Ext.override(Ext.form.ComboBox, {//多选时下拉框不失去焦点
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
	    
	function checkSelected(node,combo,checkedStr){
	        
	        node.eachChild(function(currNode)
	        {
	           if(currNode.getUI().isChecked()){
	               checkedStr = checkedStr+currNode.text+"、";
	           }
	        }
	        );
	        var index = checkedStr.lastIndexOf("、");
	        checkedStr = checkedStr.substring(0,index);
	        
	        return checkedStr;
	    }

	    var prodTreePanel = new Ext.tree.TreePanel(
	            {           
	            autoScroll:true,
	            height:190,
	            rootVisible:false,
	            listeners:{
	                'click':function(node)
	                {
	                    if(node.getUI().isChecked()){
	                        node.getUI().toggleCheck(false);
	                    }else
	                    {
	                        node.getUI().toggleCheck(true);
	                    }
	                }
	            },
	            root:new Ext.tree.AsyncTreeNode({
	                id:'all',
	                text:'全选',    
	                height:150,
	                autoScroll:true,
	                expanded:true,
	                leaf:false,
	                loader:new Ext.tree.TreeLoader({
	                    url:basepath+'/queryprodleveltree.json?level='+lv+'',
	                    requestMethod:'GET'
	                })
	            }),
	            animate : false,
	            useArrows : false,
	            border : false
	         }
	         );
	    
	  /*******************************************************************************************/


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
            var org_level = validateForm.findField("instn_lev").value;
            if(crm_dt == null || crm_dt==""||crm_dt==undefined){
                Ext.MessageBox.alert('提示','请选择统计日期.');  
                return false;
            }
            if(org_level == null || org_level==""||org_level==undefined){
                Ext.MessageBox.alert('提示','请选择机构层次.');  
                return false;
            }   
            
            validateForm.findField("checkedNodes").setValue(checkedNodes);  
//            validateForm.findField("CATL_LEVEL").setValue(level);
            var instn_level = validateForm.findField("instn_lev").value;
            var sta_level;
            if(instn_level == "1"){
                sta_level=1;
            }else if(instn_level =="2"){
            
                sta_level=2;
            }else if(instn_level == "3"){
                sta_level=3;
            }else if(instn_level =="4"){
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
            var rootId = orgTreePanel_p.root.id;
            var checkLevel = rootId.substring(rootId.length-1);
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
//                  alert(" 一级没选,2级选择 有跨行:"+"4");
                    return "4";//表示跨行选择
                }
            }
        }
        
        if(num ==1){        
            for(var j=0;j<level3Nodes.length;j++){          
                if(level3Nodes[j].getUI().isChecked()){
                    level3Checked[level3Checked.length] = level3Nodes[j];
                    if(level3Nodes[j].parentNode != level2NodeChecked){//如果被选择的三级节点的父节点不是同一个被选择二级节点,则表示有跨行选择
//                      alert(" 二级选择了一个,三级选择有跨行:" + "4");
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
//                      alert(level2NodeChecked.text);
//                      alert(" 二级选择了一个,三级选择了一个,四级选择有跨行:" + "4");
                        return "4";
                    }
                }
            }
            var check2NodeId = level2NodeChecked.id;
            var checkLevel = check2NodeId.substring(check2NodeId.length-1);
//          alert("选中2级节点统计: "+checkLevel   );
            return checkLevel;
            
        }else if(num==0){
            
            var nodeNum3=0;
            var tempChecked3Node;
            for(var b=0;b<level3Nodes.length;b++){
                if(level3Nodes[b].getUI().isChecked()){
                    tempChecked3Node = level3Nodes[b];
                    nodeNum3++;
                    if(nodeNum3>=2){
//                      alert("2级没选,三级选择有跨行: "+4);
                        return "4";
                    }
                }
            }           
            if(nodeNum3==1){
                
                for(var c = 0;c<level4Nodes.length;c++){
                    if( (level4Nodes[c].getUI().isChecked()) && (level4Nodes[c].parentNode!=tempChecked3Node) ){
//                          alert("二级没选,3级选择了一个,4级选择有跨行.");
                            return "4";                 
                    }
                }           
                var check3NodeId = tempChecked3Node.id;
                var checkLevel = check3NodeId.substring(check3NodeId.length-1);
//                      alert("选择了三级节点统计:"+checkLevel);
                return checkLevel;          
            }else if(nodeNum3==0){
                for(var d=0;d<level4Nodes.length;d++){
                    if(level4Nodes[d].getUI().isChecked()){
                        var check4NodeId = level4Nodes[d].id;
                        var checkLevel = check4NodeId.substring(check4NodeId.length-1);
//                      alert("选择了四级节点统计: "+checkLevel);
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
     var orgTreePanel1 = new Ext.tree.TreePanel(
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
                             var rootNode = orgTreePanel1.root;
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
//              node.getUI().toggleCheck(true);
//              tempCombo = Ext.getCmp("depAndLonIncreaseAdvanceSearch").getForm().findField("instn_no");
//              tempCombo.setRawValue(node.text);
//              tempCombo.collapse();
//              var level = spanBank(orgTreePanelAdvance);
//              var checkedNodes = getCheckedNode(orgTreePanelAdvance);
//              Ext.getCmp("depAndLonIncreaseAdvanceSearch").getForm().findField("checkedNodes").setValue(checkedNodes);    
//              Ext.getCmp("depAndLonIncreaseAdvanceSearch").getForm().findField("level").setValue(level);
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
                    store : new Ext.data.SimpleStore({
                        fields : [],
                        data : [ [] ]
                    }),
                    name:'INSTN_ID',
                    labelStyle: 'text-align:right;',
                    emptyText : '请选择...',
                    fieldLabel : '*机构',
                    editable:false,
                    resizable:true,
                    allowBlank : false,
                    anchor : '100%',
                    mode : 'local',
                    triggerAction : 'all',
                    maxHeight : 390,
                    // 下拉框的显示模板,addDeptTreeDiv作为显示下拉树的容器
                    tpl:"<tpl for='.' <div style='height:390px'> <div id='addOrgTreeDivForAdd'></div></div></tpl>",
//                  tpl:"<div style='height:390px'> <div id='addOrgTreeDivForAdd'></div></div>",
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
		//*****************************************
			var searchPanel = new Ext.form.FormPanel({
				title : "产品统计--查询条件项",
				labelWidth : 105,
				labelAlign : 'right',
				height : 100,
				frame : true,
				region : 'north',
				id : 'searchPanel',
				autoScroll : true,
				items : [ {
					layout : 'column',
					items : [
							{
		                         columnWidth:.25,
		                         layout: 'form',
		                         items: [{
		                             xtype:'datefield',
		                             fieldLabel: '*统计日期',
		                             editable:false,
		                             value:lastDay,
		                             labelStyle: 'text-align:right;',
		                             format:'Y-m-d', //日期格式化
		                             name: 'crm_dt',
		                             anchor:'100%'
		                         }]
		                     },
							{
								name:"checkedNodes",
								id:"checkedNodes",
								hidden:true,
								xtype:'textfield'
							}/*,
							{
								columnWidth : .25,
								layout : 'form',
								items : [ {
									xtype:'combo',					
									store : new Ext.data.SimpleStore( {
										fields : [],
										data : [ [] ]
									}),
									name:'instn_no_name',
									id:'instn_no_name',
									emptyText : '请选择',
									fieldLabel : '机构',
									editable:false,
									resizable:true,
									anchor : '100%',
									mode : 'local',
									triggerAction : 'all',
									maxHeight : 3100,
									// 下拉框的显示模板,addDeptTreeDiv作为显示下拉树的容器
									tpl:"<tpl for='.' <div style='height:3100px'> <div id='addOrgTreeDivForAdd1'></div></div></tpl>",
//									tpl:"<div style='height:3100px'> <div id='addOrgTreeDivForAdd'></div></div>",
									onSelect : Ext.emptyFn,
									listeners:{
										'expand':function(combo){			
//											combo.doLayout();
											orgTreePanel1.render('addOrgTreeDivForAdd1');
										}
									}
								} ]
							}*/,{
								columnWidth : .25,
								layout : 'form',
								items : [
									{ 
			                    xtype:'combo',       
			                    fieldLabel : '*机构',
			                    store : new Ext.data.SimpleStore({
			                        fields : [],
			                        data : [ [] ]
			                    }),
			                    name:'instn_id',
			                    labelStyle: 'text-align:right;',
			                    emptyText : '请选择...',
			                    editable:false,
			                    resizable:true,
			                    allowBlank:false,
			                    anchor : '100%',
			                    mode : 'local',
			                    triggerAction : 'all',
			                    maxHeight : 390,
			                    // 下拉框的显示模板,addDeptTreeDiv作为显示下拉树的容器
			                    tpl:"<tpl for='.' <div style='height:390px'> <div id='addOrgTreeDivForAdd'></div></div></tpl>",
//			                  tpl:"<div style='height:390px'> <div id='addOrgTreeDivForAdd'></div></div>",
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
			                }
							]},{
								columnWidth : .25,
								layout : 'form',
								items : [
									{	
	                             fieldLabel: '*机构层级',
	                             name: 'instn_lev',
	                             id : 'instn_lev',
	                             forceSelection : true,
	 							 resizable:true,
	                             xtype:'combo',
	                             labelStyle: 'text-align:right;',
	                             triggerAction:'all',
	                             mode:'local',
	                             store:instnLevelStore,
	                             valueField:'myId',
	                             value:'4',
	                             displayField:'displayText',
	                             emptyText:'请选择',
	                             anchor : '100%'
	                         }
							]},{
									columnWidth : .25,
									layout : 'form',
									items : [ {
										xtype : 'textfield',
										labelStyle: 'text-align:right;',
										fieldLabel : '产品名称',
										id : 'prod_name',
										name : 'prod_name',
										anchor : '100%'
									} ]
								}/*,{
									columnWidth : .25,
									layout : 'form',
									items : [ {
										xtype:'combo',
										name:'CATL_LEVEL',
										hiddenName:'CATL_LEVEL',
										triggerAction:'all',
										anchor:'90%',
										fieldLabel:'<span style="color:red">*</span>产品分类层级',
										mode:'local',
										allowBlank : false,
										store: proLevelStore,
								 	   listeners:{
								 	   	'select':function(combo){
								 	   	var	catlCom = searchPanel.getForm().findField("CATL_CODE");
								 	   	catlCom.setValue("");
								 	   	catlCom.store.removeAll();
								 	   	catlCom.store.load({
								 	   		params:{
								 	   			'condition':'{"CATL_LEVEL":"'+combo.value+'"}'
								 	   			}
								 	   	});			 	   	
								 	   	}
								 	   },
								 	   resizable:true,
								 	   forceSelection : true,
								 	   emptyText:'请选择',
								       valueField:'key',
								       displayField:'value'
									} ]
								}*/,{
									columnWidth : .25,
									layout : 'form',
									items : [
										new Ext.form.ComboBox({
                    xtype:'combo',
                    fieldLabel : '产品分类层级',
                    name: 'plevel' ,
                    id:'plevel',
                    resizable:true,forceSelection : true,
                    labelStyle: 'text-align:right;',
                    triggerAction:'all',
                    mode:'local',
                    store:lvstore,
                    valueField:'myId',
                    displayField:'displayText',
                    emptyText:'请选择',
                    value:'1',
                    anchor : '100%'
               })
								]},{
									columnWidth : .25,
									layout : 'form',
									items : [{
					                    xtype: 'combo',
					                    fieldLabel:'产品分类名称',
					                    hiddenName:'PROD_LEVEL',
					                    labelStyle: 'text-align:right;',
					                    resizable:true,
					                    triggerAction : 'all',
					                    maxHeight : 390,
					                    height:150,
					                    emptyText:'请选择',
					                    mode:'local',
					                    tpl:"<tpl for='.' <div style='height:390px'> <div id='prodTree'></div></div></tpl>",
//					                    onSelect:Ext.emptyFn,
					                    store:new Ext.data.SimpleStore({
					                        fields:[],
					                        data:[ [] ]
					                    }),                                     
					                    listeners:{
					                        'expand':function(){

					                            if(classflg==1){
					                            prodTreePanel.root.reload();
					                            }
					                            classflg = 0;
					                            prodTreePanel.render('prodTree');
					                        },
					                        'collapse':function(combo){
					                        
					                            var checkedStr =''+ checkSelected(prodTreePanel.root,combo,"")+'';
					                            combo.setRawValue(checkedStr);
					                        }
					                    },
					                  anchor:'100%'
					              } ]
								}]
				}],
				buttonAlign : 'center',
				buttons : [ {
					text : '查询',
					handler : function() {
						 var flag = submitValidate(orgTreePanel,searchPanel.getForm());
	                        if(!flag){
	                            return;
	                        }
						if(!searchPanel.getForm().isValid()){
							Ext.Msg.alert("系统提醒","输入有误，请重新输入！");
							return false;
					}
						var conditionStr = searchPanel.getForm().getFieldValues();
						store.baseParams = {
								"condition" : Ext.encode(conditionStr)
							};
						store.load({
							params : {
								start : 0,
								limit : parseInt(pagesize_combo.getValue())
							}
						});}
				},{
					text : '重置',
						handler : function() {
							searchPanel.getForm().reset();
							orgTreePanel.root.getUI().toggleCheck(false);
							if(msFlag!=0){
                                Ext.getCmp('msFlag').setValue('1');
                                Ext.getCmp('msFlag').setReadOnly(true);
                            }
                            prodTreePanel .root.attributes.loader.url=basepath+"/queryprodleveltree.json?level=1";
                            classflg = 1;
						}
					} ]
			});
			/****************************************/
            if(window.location.href.split("msFlag=")[1]!=undefined){
                msFlag=1;
            }
            if(msFlag!=0){
                Ext.getCmp('msFlag').setValue('1');
                Ext.getCmp('msFlag').setReadOnly(true);
            }
//			// 复选框
//			var sm = new Ext.grid.CheckboxSelectionModel();
			
			// 定义自动当前页行号
			var rownum = new Ext.grid.RowNumberer({
				header : 'No.',
				width : 28
			});

			//列模型
			var columns = new Ext.grid.ColumnModel([ rownum,{
				header : '隐藏id',
				dataIndex : 'id', 
				sortable : true,
				hidden : true,
				width : 120
			},
			{	
				header : '统计日期',
				dataIndex : 'crm_dt',
				sortable : true,
				width : 120
			},{
				header : '机构号',
				dataIndex : 'instn_id', 
				sortable : true,
				width : 120
			},{
				header : '机构名称',
				dataIndex : 'system_unitname', 
				sortable : true,
				width : 120
			},{
				header : '产品编号', 
				dataIndex : 'product_id', 
				sortable : true,
				width : 120
			},{header : '产品名称',sortable : true,dataIndex : 'prod_name'},
			{header : '一级分类',sortable : true,dataIndex : 'prod_level1'},
//		    {header : '客户名称',dataIndex : 'cust_name',width :80},
		    {header : '二级分类',sortable : true,dataIndex : 'prod_level2'},
		    {header : '三级分类',sortable : true,dataIndex : 'prod_level3'},
		    {header : '四级分类',sortable : true,dataIndex : 'prod_level4'},
//		    {header : '状态',sortable : true,ataIndex : 'cust_sts'},
		    {header : '时点余额 ',dataIndex : 'bal',align:'right',sortable : true,renderer: money('0,000.00')},
		    {header : '年均余额',dataIndex :'year_avg',align:'right',sortable : true,renderer: money('0,000.00')},
		    {header : '销售金额',dataIndex : 'sell_amt',align:'right',sortable : true,renderer: money('0,000.00')},
		    {header : '产品收入',dataIndex : 'rcv',align:'right',sortable : true,renderer: money('0,000.00')}
		    ]);
		
			var record = Ext.data.Record.create([
			         {name: 'id', mapping: 'id'},
			         {name: 'styp', mapping: 'styp'},
			         {name: 'instn_id', mapping: 'instn_id'},
			         {name: 'system_unitname', mapping: 'system_unitname'},
			         {name: 'product_id', mapping: 'product_id'},
			         {name: 'crm_dt', mapping: 'crm_dt'},
			         {name: 'cust_sts', mapping: 'cust_sts'},
			         {name: 'prod_name', mapping: 'prod_name'},
			         {name: 'prod_level1', mapping: 'prod_level1'},
			         {name: 'prod_level2', mapping: 'prod_level2'},
			         {name: 'prod_level3', mapping: 'prod_level3'},
			         {name: 'prod_level4', mapping: 'prod_level4'},  
			         {name: 'bal', mapping: 'bal',type:'float'},
			         {name: 'year_avg', mapping: 'year_avg',type:'float'},
			         {name: 'sell_amt', mapping: 'sell_amt',type:'float'},
			         {name: 'rcv', mapping :'rcv',type:'float'}
			         
					 ]);
			
			var store = new Ext.data.Store({
//				restful : true,
				proxy : new Ext.data.HttpProxy({
					url : basepath + '/productCcountQuery!index',
					method:'POST',
//					success : function(response) {
//						Ext.Msg.alert('提示', response.responseText);
//					}
					failure : function(response) {
						var resultArray = Ext.util.JSON.decode(response.status);
						if(resultArray == 403) {
							Ext.Msg.alert('提示', response.responseText);
						}
					}

				}),
				reader : new Ext.data.JsonReader({
					successProperty: 'success',
			        idProperty: 'ID',
			        messageProperty: 'message',
					root : 'json.data',
					totalProperty: 'json.count'
				}, record)
			});
			
			

			// 每页显示条数下拉选择框
			var pagesize_combo = new Ext.form.ComboBox({
				name : 'pagesize',
				triggerAction : 'all',
				mode : 'local',
				store : new Ext.data.ArrayStore({
					fields : [ 'value', 'text' ],
					data : [ [ 100, '100条/页' ], [ 200, '200条/页' ], [ 500, '500条/页' ],
							[ 1000, '1000条/页' ] ]
				}),
				valueField : 'value',
				displayField : 'text',
				value : '100',
				forceSelection : true,
				width : 85
			});

			

			// 改变每页显示条数reload数据
			pagesize_combo.on("select", function(comboBox) {
//				store.reload();
				bbar.pageSize = parseInt(pagesize_combo.getValue()),
				store.reload({
					params : {
						start : 0,
						limit : parseInt(pagesize_combo.getValue())
					}
				});
			});
			// 分页工具栏
			var bbar = new Ext.PagingToolbar({
				pageSize : parseInt(pagesize_combo.getValue()),
				store : store,
				displayInfo : true,
				displayMsg : '显示{0}条到{1}条,共{2}条',
				emptyMsg : "没有符合条件的记录",
				items : [ '-', '&nbsp;&nbsp;', pagesize_combo ]
			});
			
			var listPanel = new Ext.grid.GridPanel(
					{
						id :'listPanelTest',
						title : "产品统计--信息列表",
						store : store,
						frame : true,
//						sm : sm,
						cm : columns,
						stripeRows : true,
						region : 'center',
						frame : true,
						tbar : [
								{
									text : '产品客户明细',
									handler : function() {

										var selectLength = listPanel
												.getSelectionModel()
												.getSelections().length;

										var selectRe = listPanel
												.getSelectionModel()
												.getSelections()[0];

										if (selectLength != 1) {
											alert('请选择一条记录');
										} else {
//												editBasePlanForm.getForm().loadRecord(selectRe);
//												document.getElementById('idStr').value = selectRe.data.id;
//												editInit();
											addGroupCustInfoWindow.show();
										}
									}

								},
						         new Com.yucheng.bob.ExpButton({
							            formPanel : 'searchPanel',
							            url : basepath + '/productCcountQuery.json'
							        })					 
									         ],
						bbar : bbar,// 分页工具栏
						viewConfig : {
						// 不产横向生滚动条, 各列自动扩展自动压缩, 适用于列数比较少的情况
						},
						loadMask : {
							msg : '正在加载表格数据,请稍等...'
						}
					});
	


			
			
			var rollTypeStore = new Ext.data.Store({  
				restful:true,   
				autoLoad :true,
				proxy : new Ext.data.HttpProxy({
						url :basepath+'/lookup.json?name=ROLL_TYPE'
					}),
					reader : new Ext.data.JsonReader({
						root : 'JSON'
					}, [ 'key', 'value' ])
				});

			var view = new Ext.Viewport({

				layout : 'border',
				items : [ {
					region : 'center',
					id : 'center-panel',
					layout : 'fit',
					items : [ listPanel ]
				},{
					region : 'north',
					id : 'north-panel',
					height : 130,
					layout : 'fit',
					items : [ searchPanel ]
				}
				]
			});
			/***********************************************************************************/
		    Ext.getCmp('plevel').addListener('select',function(){
		        if(Ext.getCmp('plevel').getValue()==1){
		            prodTreePanel .root.attributes.loader.url=basepath+"/queryprodleveltree.json?level=1";
		        }else if(Ext.getCmp('plevel').getValue()==2){
		            prodTreePanel .root.attributes.loader.url=basepath+"/queryprodleveltree.json?level=2";
		        }else if(Ext.getCmp('plevel').getValue()==3){
		            prodTreePanel .root.attributes.loader.url=basepath+"/queryprodleveltree.json?level=3";
		        }else if(Ext.getCmp('plevel').getValue()==4){
		            prodTreePanel .root.attributes.loader.url=basepath+"/queryprodleveltree.json?level=4";
		        }
		        classflg = 1;
		        searchPanel.getForm().findField('PROD_LEVEL').setValue('');
//		      debugger;
		    });
		    
		    /***********************************************************************************/
//            // 默认加载数据
//            var conditionStr = searchPanel.getForm().getFieldValues();
//            store.baseParams = {
//                    "condition" : Ext.encode(conditionStr)
//                };
//            store.load({
//                params : {
//                    start : 0,
//                    limit : parseInt(pagesize_combo.getValue())
//                }
//            });
		});