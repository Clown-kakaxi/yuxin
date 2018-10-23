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
	    width:490,
      	height : 390,
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
		        		        	        {  xtype:'textfield',fieldLabel:'客户号',name:'custId',labelStyle:'text-align:right;',anchor:'90%',hidden:true},
										    {  xtype:'textfield',fieldLabel:'姓名',name:'custId',labelStyle:'text-align:right;',anchor:'90%'},
									     	 {  xtype:'textfield',fieldLabel:'称谓',name:'custId',labelStyle:'text-align:right;',anchor:'90%'},
										 {  xtype:'textfield',fieldLabel:'职位',name:'custId',labelStyle:'text-align:right;',anchor:'90%'},
									 {  xtype:'textfield',fieldLabel:'办公电话',name:'custId',labelStyle:'text-align:right;',anchor:'90%'},
									 {  xtype:'textfield',fieldLabel:'分机号码',name:'custId',labelStyle:'text-align:right;',anchor:'90%'},
									 {  xtype:'textfield',fieldLabel:'手机号码',name:'custId',labelStyle:'text-align:right;',anchor:'90%'},
									 {  xtype:'textfield',fieldLabel:'家庭电话',name:'custId',labelStyle:'text-align:right;',anchor:'90%'},
									 {  xtype:'textfield',fieldLabel:'传真',name:'custId',labelStyle:'text-align:right;',anchor:'90%'},
									 {  xtype:'textfield',fieldLabel:'电子邮件',name:'custId',labelStyle:'text-align:right;',anchor:'90%'},
									 {  xtype:'textfield',fieldLabel:'国籍',name:'custId',labelStyle:'text-align:right;',anchor:'90%'},
										 {  xtype:'textfield',fieldLabel:'证件类型',name:'custId',labelStyle:'text-align:right;',anchor:'90%'},
										 {  xtype:'textfield',fieldLabel:'证件号码',name:'custId',labelStyle:'text-align:right;',anchor:'90%'},
									    {  xtype:'datefield',fieldLabel:'出生日期',name : 'issueDate',format:'Y-m-d',labelStyle:'text-align:right;',anchor:'90%'}
									   
										
									

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
//		        	   var addIss = Ext.getCmp('issueDate').getValue();
//		        	   var addl = Ext.getCmp('lostDate').getValue();
//		        	   if(addIss >= addl){
//		        		   Ext.Msg.alert("系统提示信息", "证件登记日期不能或等于大于证件到期日，请重新输入!");
//		        		   return false;
//		        	   }
		        	   Ext.getCmp('custId').setValue(custid);
		        	   Ext.Ajax.request( {
		        		   url : basepath + '/ComCertInfo-action.json',
		        		   method : 'POST',
		        		   params : addCertInfoPanel.getForm().getFieldValues(),
		        		   success : checkResult,
		        		   failure: checkResult
		        	   });		        	   
		        	   
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
	                                             //{header : '联系人',dataIndex : 'custId',width : 120,sortable : true},
	                                             {header : '姓名',dataIndex : 'f1',width : 150,sortable : true},
	                                             {header : '称谓',dataIndex : 'f2',width : 150,sortable : true},
	                                             {header : '职位',dataIndex : 'f3',width : 150,sortable : true},
	                                             {header : '办公电话',dataIndex : 'f4',width : 150,sortable : true},
	                                             {header : '分机号码',dataIndex : 'f5',width : 150 ,sortable : true},
	                                             {header : '手机号码',dataIndex : 'f6',width : 145,sortable : true},
	                                             {header : '家庭电话',dataIndex : 'f7',width : 145,sortable : true},
	                                             {header : '传真',dataIndex : 'f8',width : 145,sortable : true},
	                                             {header : '电子邮件',dataIndex : 'f9',width : 145,sortable : true}//,
//	                                             {header : '国籍',dataIndex : 'asAnnId',width : 145,sortable : true},
//	                                             {header : '证件类型',dataIndex : 'asAnnId',width : 145,sortable : true},
//	                                             {header : '证件号码',dataIndex : 'asAnnId',width : 145,sortable : true},
//	                                             {header : '来源系统',dataIndex : 'asAnnId',width : 145,sortable : true},
//	                                             {header : '出生日期',dataIndex : 'asAnnId',width : 145,sortable : true}
	                                             ]);
//	var certInfoRecord = new Ext.data.Record.create([
//	                                                 {name : 'id',mapping : 'ID'},
//	                                                 {name : 'custId',mapping : 'CUST_ID'},
//	                                                 {name : 'cretType',mapping : 'CRET_TYPE'},
//	                                                 {name : 'cretTypeOra',mapping : 'CRET_TYPE_ORA'},
//	                                                 {name : 'cretNo',mapping : 'CRET_NO'},
//	                                                 {name : 'cardOnName',mapping : 'CARD_ON_NAME'},
//	                                                 {name : 'issueDate',mapping : 'ISSUE_DATE'},
//	                                                 {name : 'lostDate',mapping : 'LOST_DATE'},
//	                                                 {name : 'tackInstn',mapping : 'TACK_INSTN'},
//	                                                 {name : 'asAnnId',mapping : 'AS_ANN_ID'}
//	                                                 ]);
	var certInfoRecord = new Ext.data.Record.create([
	                                                 {name : 'f1'},
	                                                 {name : 'f2'},
	                                                 {name : 'f3'},
	                                                 {name : 'f4'},
	                                                 {name : 'f5'},
	                                                 {name : 'f6'},
	                                                 {name : 'f7'},
	                                                 {name : 'f8'},
	                                                 {name : 'f9'}
	                                                 ]);
	var certInfoReader = new Ext.data.JsonReader({//读取json数据的panel
//		totalProperty:'json.count',
//		root:'json.data'
	            root:'rows',
	            totalProperty: 'num'
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
// 	certInfoStore.load({
// 		params : {
// 			'custId' : custid
// 	}
// 	});
 		
 	var tb_memberData2= {
	num:1,
	rows:[
	{"f1":"左思明","f2":"先生","f3":"主管","f4":"028-87122345","f5":"2135","f6":"13537458234","f7":"028-87129345","f8":"028-8712932","f9":"zsm@163.com"}
	]
};
 		
certInfoStore.loadData(tb_memberData2);
 	
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
		title : '客户联系人信息',
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
 		        	handler : function(){
 		        	//certInfoWind.setTitle('证件信息新增'); //当新增的时候将弹出的窗口的title设置为‘证件信息新增’    			
 		        	certInfoWind.show();
 		        }},'-',{
 		        	text : '修改',
 		        	handler : function(){
 		       /* 	var record = certBaseInfoGrid.getSelectionModel().getSelected();//选中的记录
 		        	var selectLength = certBaseInfoGrid.getSelectionModel().getSelections().length;//所选信息的条数
 		        	if(selectLength.length==0)//判断是否选择要修改的信息
 		        	{
 		        		Ext.Msg.alert('提示', '请选择一条要修改的证件信息！');
 		        		return ;
 		        	}else if(selectLength.length>1){//判断当前是否只选择一条信息
 		        		Ext.Msg.alert('提示','请选择一条记录进行修改！');
 		        	}
 		        	certInfoWind.setTitle('证件信息修改');//当修改的时候将弹出的窗口的title设置为‘证件信息修改’       			
*/ 		        	certInfoWind.show();
 		        	//addCertInfoPanel.getForm().loadRecord(record);//addCertInfoPanel加载所选的信息
 		        }},'-',{
 		        	text : '删除',
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
		      			                Ext.Ajax.request({
		      			                	url : basepath + '/ComCertInfo-action!batchDestroy.json',
		      			                	params : {
		      			                		'id':idStr
		      			                },
		      			                failure : function() {
		      			                	Ext.Msg.alert('提示', '操作失败');
		      			                	certInfoStore.reload();
		      			                },
		      			                success : function(){
		      			                	certInfoStore.reload();
		      			                	Ext.Msg.alert('提示', '删除成功!');
		      			                }
		      			                });		                
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
