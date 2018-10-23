	
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
                              }      else{
                  				var checkedId = checkId(orgTreePanel);
                                  validateForm.findField("checkedNodes").setValue(checkedId);
                              }                         
                              
                              return true;
      
  }    
   
//归属机构编号
	 function checkId(orgTreePanel_p){
		 	var checkIdString="";
		 	if(orgTreePanel_p.root.getUI().isChecked()){
		 		var rootId = orgTreePanel_p.root.childNodes[0].attributes.SUPERUNITID;
		 		var text = orgTreePanel_p.root.text;
		 		checkIdString = checkIdString+"'"+rootId+"',";
//		 		return checkIdString;	 		
		 	}	 	
		 	var level2Nodes = orgTreePanel_p.root.childNodes;
		 	var level3Nodes = new Array();
		 	var level4Nodes = new Array();
		 	var level2NodesChecked = new Array();
		 	var level3NodesChecked = new Array();
		 	for(var i=0;i<level2Nodes.length;i++){
		 		var tempNode = level2Nodes[i];
		 		if(tempNode.getUI().isChecked()){
		 			checkIdString  = checkIdString +"'"+ tempNode.attributes.UNITID+"',";
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
			 			checkIdString  = checkIdString + "'"+anode.attributes.UNITID+"',";
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
		 				checkIdString  = checkIdString +"'"+ node4.attributes.UNITID+"',";	 			
		 			}
		 		}	 	
		 	}
			var lastIndex = checkIdString.lastIndexOf(",");
			checkedIdString = checkIdString.substring(0,lastIndex);		
			return checkedIdString;
		 }
          
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
//                alert(" 一级没选,2级选择 有跨行:"+"4");
                  return "4";//表示跨行选择
              }
          }
      }
      
      if(num ==1){        
          for(var j=0;j<level3Nodes.length;j++){          
              if(level3Nodes[j].getUI().isChecked()){
                  level3Checked[level3Checked.length] = level3Nodes[j];
                  if(level3Nodes[j].parentNode != level2NodeChecked){//如果被选择的三级节点的父节点不是同一个被选择二级节点,则表示有跨行选择
//                    alert(" 二级选择了一个,三级选择有跨行:" + "4");
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
//                    alert(level2NodeChecked.text);
//                    alert(" 二级选择了一个,三级选择了一个,四级选择有跨行:" + "4");
                      return "4";
                  }
              }
          }
          var check2NodeId = level2NodeChecked.id;
          var checkLevel = check2NodeId.substring(check2NodeId.length-1);
//        alert("选中2级节点统计: "+checkLevel   );
          return checkLevel;
          
      }else if(num==0){
          
          var nodeNum3=0;
          var tempChecked3Node;
          for(var b=0;b<level3Nodes.length;b++){
              if(level3Nodes[b].getUI().isChecked()){
                  tempChecked3Node = level3Nodes[b];
                  nodeNum3++;
                  if(nodeNum3>=2){
//                    alert("2级没选,三级选择有跨行: "+4);
                      return "4";
                  }
              }
          }           
          if(nodeNum3==1){
              
              for(var c = 0;c<level4Nodes.length;c++){
                  if( (level4Nodes[c].getUI().isChecked()) && (level4Nodes[c].parentNode!=tempChecked3Node) ){
//                        alert("二级没选,3级选择了一个,4级选择有跨行.");
                          return "4";                 
                  }
              }           
              var check3NodeId = tempChecked3Node.id;
              var checkLevel = check3NodeId.substring(check3NodeId.length-1);
//                    alert("选择了三级节点统计:"+checkLevel);
              return checkLevel;          
          }else if(nodeNum3==0){
              for(var d=0;d<level4Nodes.length;d++){
                  if(level4Nodes[d].getUI().isChecked()){
                      var check4NodeId = level4Nodes[d].id;
                      var checkLevel = check4NodeId.substring(check4NodeId.length-1);
//                    alert("选择了四级节点统计: "+checkLevel);
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
 
  var instnCombo = new Ext.form.ComboBox({    
                  columnWidth:.25,
                  xtype:'combo',                  
                  store : new Ext.data.SimpleStore({
                      fields : [],
                      data : [ [] ]
                  }),
                  name:'INSTN_ID',
                  id :'orgTree',
                  labelStyle: 'text-align:right;',
                  emptyText : '请选择',
                  fieldLabel : '机构名称',
                  editable:false,
                  resizable:true,
                 // allowBlank : false,
                  anchor : '90%',
                  mode : 'local',
                  triggerAction : 'all',
                  maxHeight : 390,
                  // 下拉框的显示模板,addDeptTreeDiv作为显示下拉树的容器
                  tpl:"<tpl for='.' <div style='height:390px'> <div id='addOrgTreeDivForAdd'></div></div></tpl>",
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
  var instnCombo1 = new Ext.form.ComboBox({    
      columnWidth:.25,
      xtype:'combo',                  
      store : new Ext.data.SimpleStore({
          fields : [],
          data : [ [] ]
      }),
      name:'INSTN_ID1',
      id :'orgTree1',
      labelStyle: 'text-align:right;',
      emptyText : '请选择',
      fieldLabel : '机构名称',
      editable:false,
      resizable:true,
     // allowBlank : false,
      anchor : '90%',
      mode : 'local',
      triggerAction : 'all',
      maxHeight : 390,
      // 下拉框的显示模板,addDeptTreeDiv作为显示下拉树的容器
      tpl:"<tpl for='.' <div style='height:390px'> <div id='addOrgTreeDivForAdd1'></div></div></tpl>",
      onSelect : Ext.emptyFn,
      listeners:{
          'expand':function(combo){           
              orgTreePanel1.render('addOrgTreeDivForAdd1');
          },
          'collapse':function(combo){
              var checkString = checkText(orgTreePanel1);
              combo.setValue(checkString);
          }
      }
  });