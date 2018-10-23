/*******
 *@author : songxs
 *@since  : 2012-10-10
 *@constructor :对公客户证件信息管理
 * 
 */
Ext.onReady(function() {
	Ext.QuickTips.init(); 
	var custid =oCustInfo.cust_id;//当前用户所查看的客户的客户号
	
	var certTypeStore = new Ext.data.Store({//证件类型store
		restful:true,   
		autoLoad :true,
		proxy : new Ext.data.HttpProxy({
			url :basepath+'/lookup.json?name=COM_CRET_TYPE'
			}),
			reader : new Ext.data.JsonReader({
				root : 'JSON'
			}, [ 'key', 'value' ])
	});
	var addCertInfoPanel = new Ext.FormPanel({//证件信息新增PANEL
		frame : true,
	    region : 'center',
      	height : 450,
      	autoScroll : true,
		split : true,
		items : [
		         {
		        	 items :[ {  
		        		 layout:'column',
		        		 items:[
		        		        {
		        		        	columnWidth:.9,
		        		        	layout: 'form',
		        		        	items: [
		        		        	        {  xtype:'textfield',fieldLabel:'id',name:'id',labelStyle:'text-align:right;',anchor:'90%',hidden:true},
		        		        	        {  xtype:'textfield',fieldLabel:'客户号',id:'custId',name:'custId',labelStyle:'text-align:right;',anchor:'90%',hidden:true},
		        		        	        {  xtype : 'combo', store:certTypeStore,resizable : true,name : 'cretType',hiddenName:'cretType', fieldLabel :'证件类型',valueField : 'key',displayField : 'value',mode : 'local',editable : false,
		        		        	           typeAhead : true,forceSelection : true,triggerAction : 'all',emptyText : '请选择',labelStyle:'text-align:right;',selectOnFocus : true,allowBlank:false,anchor : '90%'},
		        		        	        {  xtype:'numberfield',fieldLabel:'证件号码',name:'cretNo',labelStyle:'text-align:right;',allowBlank:false,anchor:'90%',maxLength:'18'},
		        		        	        {  xtype:'textfield',fieldLabel:'证件上名称',name:'cardOnName',labelStyle:'text-align:right;',allowBlank:false,anchor:'90%'},
		        		        	        {  xtype:'datefield',fieldLabel:'证件登记日期',id:'issueDate',name : 'issueDate',format:'Y-m-d',labelStyle:'text-align:right;',anchor:'90%'},
		        		        	        {  xtype:'datefield',fieldLabel:'证件到期日',id:'lostDate',name : 'lostDate',format:'Y-m-d',labelStyle:'text-align:right;',anchor:'90%'},    
		        		        	        {  xtype:'textfield',fieldLabel:'发证机构',name : 'tackInstn',labelStyle:'text-align:right;',anchor:'90%'},    
		        		        	        {  xtype:'textfield',fieldLabel:'年检标识',name:'asAnnId',labelStyle:'text-align:right;',anchor:'90%'}
		        		        	        ]
		        		        }]
		        	 }]
		         }]
		});
	var certInfoWind = new Ext.Window({
		closeAction:'hide',
		closable:true,
		constrain:true,
		modal:true,
		maximizable:true,
		height:450,
		width:500,
		buttonAlign:'center',
		items : [addCertInfoPanel],
		buttons : [
		           {
		        	   text : '保存',
		        	   handler:function(){
		        	   if (!addCertInfoPanel.getForm().isValid()) {
		        		   Ext.Msg.alert("系统提示信息", "输入有误或存在漏输项，请重新输入!");
		        		   return false;
		        	   } 
		        	   var addIss = Ext.getCmp('issueDate').getValue();
		        	   var addl = Ext.getCmp('lostDate').getValue();
		        	   if(addIss >= addl){
		        		   Ext.Msg.alert("系统提示信息", "证件登记日期不能或等于大于证件到期日，请重新输入!");
		        		   return false;
		        	   }
		        	   Ext.getCmp('custId').setValue(custid);
//		        	   Ext.Ajax.request( {
//		        		   url : basepath + '/ComCertInfo-action.json',
//		        		   method : 'POST',
//		        		   params : addCertInfoPanel.getForm().getFieldValues(),
//		        		   success : checkResult,
//		        		   failure: checkResult
//		        	   });		        	   
		        	   
		        	   function checkResult(response) {
		        		   var resultArray = Ext.util.JSON.decode(response.status);
		        		   var resultError = response.responseText;
		        		   if ((resultArray == 200 ||resultArray == 201)&&resultError=='') {
		        			   Ext.Msg.alert('提示', '保存成功');
		        			   certInfoStore.reload();
		        			   certInfoWind.hide();
		        			   addCertInfoPanel.getForm().reset();
		        		   } else {
		        			   if(resultArray == 403){
		        				   Ext.Msg.alert('提示', response.responseText);
		        			   }else{
		        				   Ext.Msg.alert('提示', '操作失败,失败原因:' + resultError);
		        			   }
		        		   }
		        	   }	   
		           }
		           },'-',{
		        	   text:'取消',
		        	   handler:function(){
		        	   		certInfoWind.hide();
		        	   		addCertInfoPanel.getForm().reset();
		           }
		           }
		           ]
	});
	
	var sm = new Ext.grid.CheckboxSelectionModel();//复选框
	var rownum = new Ext.grid.RowNumberer({
		header : 'No.',
		width : 28
	});
	var certInfogrid = new Ext.grid.ColumnModel([//gridtable中的列定义
	                                             sm,rownum,
	                                             {header : 'ID',dataIndex : 'id',width : 100,sortable : true,hidden : true},
	                                             {header : '客户号',dataIndex : 'custId',width : 120,sortable : true,hidden : true},
	                                             {header : '证件类型',dataIndex : 'cretTypeOra',id : "cretType",width : 150,sortable : true},
	                                             {header : '证件号码',dataIndex : 'cretNo',width : 150,sortable : true},
	                                             {header : '证件上名称',dataIndex : 'cardOnName',width : 150,sortable : true},
	                                             {header : '证件登记日期',dataIndex : 'issueDate',width : 150,sortable : true},
	                                             {header : '证件到期日',dataIndex : 'lostDate',width : 150 ,sortable : true},
	                                             {header : '发证机构',dataIndex : 'tackInstn',width : 145,sortable : true},
	                                             {header : '年检标识',dataIndex : 'asAnnId',width : 145,sortable : true} 
	                                             ]);
	var certInfoRecord = new Ext.data.Record.create([
	                                                 {name : 'id',mapping : 'ID'},
	                                                 {name : 'custId',mapping : 'CUST_ID'},
	                                                 {name : 'cretType',mapping : 'CRET_TYPE'},
	                                                 {name : 'cretTypeOra',mapping : 'CRET_TYPE_ORA'},
	                                                 {name : 'cretNo',mapping : 'CRET_NO'},
	                                                 {name : 'cardOnName',mapping : 'CARD_ON_NAME'},
	                                                 {name : 'issueDate',mapping : 'ISSUE_DATE'},
	                                                 {name : 'lostDate',mapping : 'LOST_DATE'},
	                                                 {name : 'tackInstn',mapping : 'TACK_INSTN'},
	                                                 {name : 'asAnnId',mapping : 'AS_ANN_ID'}
	                                                 ]);
	var certInfoReader = new Ext.data.JsonReader({//读取json数据的panel
		totalProperty:'json.count',
		root:'json.data'
	},certInfoRecord);
 	var certInfoStore = new Ext.data.Store(
 			{
 				proxy:new Ext.data.HttpProxy({
 					url:basepath+'/certInfoQuery-Action.json',
 					failure : function(response){
 					var resultArray = Ext.util.JSON.decode(response.status);
 					if(resultArray == 403) {
 						Ext.Msg.alert('提示', response.responseText);
 					}
 				},
 				method:'GET'
 				}),
 				reader:certInfoReader
 			}
 		);
 	certInfoStore.baseParams ={
 		'custId' : custid
 	};
 	certInfoStore.load();
 	
	var spagesize_combo = new Ext.form.ComboBox({	// 每页显示条数下拉选择框
		name : 'pagesize',
		triggerAction : 'all',
		mode : 'local',
		store : new Ext.data.ArrayStore({
			fields : [ 'value', 'text' ],
			data : [ [ 10, '10条/页' ], [ 20, '20条/页' ], [ 50, '50条/页' ],
			         [ 100, '100条/页' ], [ 250, '250条/页' ],
			         [ 500, '500条/页' ] ]
		}),
		valueField : 'value',
		displayField : 'text',
		value : '20',
		forceSelection : true,
		width : 85
	});
	
	spagesize_combo.on("select", function(comboBox) {	// 改变每页显示条数reload数据
		sbbar.pageSize = parseInt(spagesize_combo.getValue()),
		certInfoStore.reload({
			params : {
			start : 0,
			limit : parseInt(spagesize_combo.getValue())
		}
		}); 
	});
	var sbbar = new Ext.PagingToolbar({	// 分页工具栏
		pageSize : parseInt(spagesize_combo.getValue()),
		store : certInfoStore,
		displayInfo : true,
		displayMsg : '显示{0}条到{1}条,共{2}条',
		emptyMsg : "没有符合条件的记录",
		items : [ '-', '&nbsp;&nbsp;', spagesize_combo ]
	});
	var certBaseInfoGrid =  new Ext.grid.GridPanel({//主要证件信息列表数据grid
		title : '客户证件信息',
 		frame : true,
 		id : 'certBaseInfoGrid',
		width : document.body.clientWidth-240,
		frame : true,
 		store : certInfoStore,
 		loadMask : true,
 		cm : certInfogrid,
 		sm : sm,
 		bbar : sbbar,
 		tbar : [
 		        {
 		        	text : '新增',
 		        	iconCls : 'addIconCss',
 		        	handler : function(){
 		        	certInfoWind.setTitle('证件信息新增'); //当新增的时候将弹出的窗口的title设置为‘证件信息新增’    			
 		        	certInfoWind.show();
 		        }},'-',{
 		        	text : '修改',
 		        	iconCls:'editIconCss',
 		        	handler : function(){
 		        	var record = certBaseInfoGrid.getSelectionModel().getSelected();//选中的记录
 		        	var selectLength = certBaseInfoGrid.getSelectionModel().getSelections().length;//所选信息的条数
 		        	if(selectLength.length==0)//判断是否选择要修改的信息
 		        	{
 		        		Ext.Msg.alert('提示', '请选择一条要修改的证件信息！');
 		        		return ;
 		        	}else if(selectLength.length>1){//判断当前是否只选择一条信息
 		        		Ext.Msg.alert('提示','请选择一条记录进行修改！');
 		        	}
 		        	certInfoWind.setTitle('证件信息修改');//当修改的时候将弹出的窗口的title设置为‘证件信息修改’       			
 		        	certInfoWind.show();
 		        	addCertInfoPanel.getForm().loadRecord(record);//addCertInfoPanel加载所选的信息
 		        }},'-',{
 		        	text : '删除',
 		        	iconCls:'deleteIconCss',
 		        	handler : function(){
 		        	var record = certBaseInfoGrid.getSelectionModel().getSelected();
 		        	var selectLength = certBaseInfoGrid.getSelectionModel().getSelections().length;
 		        	if(selectLength<1){
 		        		Ext.Msg.alert('提示','请选择要删除的证件信息！');}
 		        	else{
 		        		Ext.Msg.confirm(
 		        				'请确认','继续删除吗？',
 		        				function(btn, text) {
 		        					if (btn == 'yes') {
 		        						var selectRe;
 		        						var tempId;
 		        						var idStr = '';
		      			                for(var i = 0; i<selectLength;i++)//将所选择的记录的ID放入idStr中
		      			                {
		      			                	selectRe = certBaseInfoGrid.getSelectionModel().getSelections()[i];
		      			                	tempId = selectRe.data.id;
		      			                	idStr += tempId;
		      			                	if( i != selectLength-1)
		      			                		idStr += ',';
		      			                }
//		      			                Ext.Ajax.request({
//		      			                	url : basepath + '/ComCertInfo-action!batchDestroy.json',
//		      			                	params : {
//		      			                		'id':idStr
//		      			                },
//		      			                failure : function() {
//		      			                	Ext.Msg.alert('提示', '操作失败');
//		      			                	certInfoStore.reload();
//		      			                },
//		      			                success : function(){
//		      			                	certInfoStore.reload();
//		      			                	Ext.Msg.alert('提示', '删除成功!');
//		      			                }
//		      			                });		                
 		        					}
 		        				});
 		        	}
 		        }
 		        }
 		        ],
 		        loadMask : {
					msg : '正在加载表格数据,请稍等...'
				} 
		});	
	var viewport_center = new Ext.Panel({
		renderTo:'viewport_center',
		height:document.body.clientHeight-30,
		layout:'fit',
		autoScroll:true,
		items: [certBaseInfoGrid] 
	});
});