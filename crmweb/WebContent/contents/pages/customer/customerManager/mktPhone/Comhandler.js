/**
 * 设备公用js处理
 */


//客户类型
	var boxstore = new Ext.data.Store({
		restful:true,   
		autoLoad :true,
		proxy : new Ext.data.HttpProxy({
				url :basepath+'/lookup.json?name=XD000080'
		}),
		reader : new Ext.data.JsonReader({
			root : 'JSON'
		}, [ 'key', 'value' ])
	});
	//证件类型数据集
	var certstore = new Ext.data.Store({  
		restful:true,   
		autoLoad :true,
		proxy : new Ext.data.HttpProxy({
				url :basepath+'/lookup.json?name=XD000040'
		}),
		reader : new Ext.data.JsonReader({
			root : 'JSON'
		}, [ 'key', 'value' ])
	});
	
	//上传文件
	var _annaSize = 0;
	function uploadFiles(formPanel,phoneId,phoneuploadWindow){
		if(formPanel.items.items.length==0){
			return;
		}
		if(formPanel.items.items[0].getValue()== ""){
			Ext.Msg.alert('操作提示','请选择录音文件!');
			return;
		}
	    var mods = formPanel.modinfo;
	    var reins = formPanel.relaId;
	    if(mods==undefined ||mods==''){
	        return false;
	    }
	    if(formPanel.getForm().isValid()){
	    	formPanel.getForm().submit({
	            url : basepath + '/FileUpload?phoneId='+phoneId,
	            success : function(form,o){
	                _tempFileName = Ext.util.JSON.decode(o.response.responseText).realFileName;
	                var fileName =form.items.items[0].getValue();
	                var simpleFileName = fileName.substring(fileName.lastIndexOf("\\")+1, fileName.length);
	                Ext.Ajax.request({
	                	url:basepath+'/workplatannexe.json',
	                	method:'POST',
	                	params: {
	                		relationInfo : reins,
	                		annexeName : simpleFileName,
	                		relationMod : mods,
	                		physicalAddress : _tempFileName,
	                		annexeSize : _annaSize 
	                	},
	                	success : function(a,b){
	                		phoneuploadWindow.hide();
	                	},
	                	failure : function(a,b){
	                		Ext.Msg.alert('操作提示','录音文件上传失败!');
	                	}
	                });
	            },
	            failure : function(form, o){
	            	if(o.result.reason=="SizeLimitExceeded")
	                    Ext.Msg.alert('操作提示','录音文件上传失败,文件超出最大限制!');
	            	else
	            		 Ext.Msg.alert('操作提示','录音文件上传失败!');
	            }
	        });
	    }
		
	};
	
	//手动上传文件窗口
	function showUploadWindow(){
		var phoneuploadWindow = new Ext.Window({
			width : 700,
		    height : 150,
		    closeAction:'hide',
			items:[ {
			id:'phoneField',
			xtype:'form',
			height : 80,
		    width : '100%',
		    fileUpload : true, 
		    dataName:'file',
		    frame:true,
		    relaId:phoneId,/**关联数据ID*/
		    modinfo:'phone',
		    items: [
		        new Ext.form.TextField({
		        	xtype :'textfield',
		        	name:'phoneannexeName',
		        	fieldLabel : '附件名称',
		        	inputType:'file',
		        	labelStyle: 'text-align:right;',
		        	anchor :'90%'
			})]}],
			buttonAlign : 'center',
			buttons:[{text:'上传',
				handler:function(){
					//上传录音文件
					var filefields = Ext.getCmp('phoneField');
				    filefields.relaId = phoneId;
				    filefields.modinfo = 'phone';
				    uploadFiles(filefields,phoneId,phoneuploadWindow);
				}
				}]
			
		});
		phoneuploadWindow.show();
	};
	
	//在附件表插入信息
	function addFile(phoneId,MType){
		var annexeName = '';
		var physicalAddress = '';
		if(MType=='1'){
			annexeName = 'PhoneOcx.mp3';
			physicalAddress = 'PHONE'+phoneId+'.mp3';
		}else{
			annexeName = 'PhoneOcx.wav';
			physicalAddress = 'PHONE'+phoneId+'.wav';
		}
        Ext.Ajax.request({
        	url:basepath+'/workplatannexe.json',
        	method:'POST',
        	params: {
        		relationInfo : phoneId,
        		annexeName : annexeName,
        		relationMod : 'phone',
        		physicalAddress : physicalAddress,
        		annexeSize : 0 
        	},
        	success : function(a,b){
        	},
        	failure : function(a,b){
        		Ext.Msg.alert('操作提示','增加附件信息失败!');
        	}
        });
    
	}
	//新建潜在客户处理..........
	function newCust(phoneId){
	var addPotentialCustomerPanel = new Ext.FormPanel({
		 id:'add',
		  frame:true,
	        bodyStyle:'padding:5px 5px 0',
	        width: '100%',
	        items: [{
	           autoHeight:true,
	            items :[{ layout:'column',
	                     items:[{
	                         columnWidth:.33,
	                         layout: 'form',
	                         items: [{
	                             xtype:'textfield',
	                             fieldLabel: '*客户ID',
	                             labelStyle: 'text-align:right;',
	                             maxLength:50,
	                             hidden:true,
	                             id: 'custId',
	                             name: 'custId',
	                             anchor:'95%'
	                         },{
	                             xtype:'textfield',
	                             fieldLabel: '*客户名称',
	                             labelStyle: 'text-align:right;',
	                             maxLength:50,
	                             allowBlank : false,
	                             id: 'custZhName',
	                             name: 'custZhName',
	                             anchor:'95%'
	                         },new Ext.form.ComboBox({
	 							name : 'custTyp',
	 							id:'custTyp',
								fieldLabel : '*客户大类',
								labelStyle: 'text-align:right;',
								triggerAction : 'all',
								store : boxstore,
								displayField : 'value',
								allowBlank : false,
								valueField : 'key',
								mode : 'local',
								forceSelection : true,
								typeAhead : true,
								emptyText:'请选择',
								resizable : true,
								anchor : '95%'
							}),{
	                             xtype:'textfield',
	                             fieldLabel: '联系人',
	                             labelStyle: 'text-align:right;',
	                             maxLength:50,
	                             id: 'linkUser',
	                             name: 'linkUser',
	                             anchor:'95%'
	                         }]
	                     },{
	                         columnWidth:.33,
	                         layout: 'form',
	                         items: [new Ext.form.ComboBox({
									name : 'certType',
									id:'certType',
									fieldLabel : '*证件类型',
									labelStyle: 'text-align:right;',
									triggerAction : 'all',
									store : certstore,
									allowBlank : false,
									displayField : 'value',
									valueField : 'key',
									mode : 'local',
									forceSelection : true,
									typeAhead : true,
									emptyText:'请选择',
									resizable : true,
									anchor : '95%'
								}),{
	                             xtype:'textfield',
	                             fieldLabel: '客户英文名称',
	                             maxLength:50,
	                             labelStyle: 'text-align:right;',
	                             id: 'custEnName',
	                             name: 'custEnName',
	                             anchor:'95%'
	                         },{
	                             xtype:'textfield',
	                             fieldLabel: '联系电话',
	                             labelStyle: 'text-align:right;',
	                             maxLength:50,
	                             id: 'linkPhone',
	                             name: 'linkPhone',
	                             anchor:'95%'
	                         },{
	                             xtype:'textfield',
	                             fieldLabel: '客户状态',
	                             labelStyle: 'text-align:right;',
	                             maxLength:50,
	                             hidden:true,
	                             id: 'custStat',
	                             name: 'custStat',
	                             anchor:'95%'
	                         }]
	                     },{
	                         columnWidth:.33,
	                         layout: 'form',
	                         items: [{
	                             xtype:'textfield',
	                             fieldLabel: '*证件号码',
	                             labelStyle: 'text-align:right;',
	                             maxLength:50,
	                             allowBlank : false,
	                             id: 'certNum',
	                             name: 'certNum',
	                             anchor:'95%'
	                         },{
	                             xtype:'textfield',
	                             fieldLabel: '其它名称',
	                             maxLength:50,
	                             labelStyle: 'text-align:right;',
	                             id: 'otherName',
	                             name: 'otherName',
	                             anchor:'95%'
	                         },{
	                             xtype:'textfield',
	                             fieldLabel: '邮编',
	                             vtype: 'number',
								 maxLength : '6',
								 minLength : '6',
	                             labelStyle: 'text-align:right;',
	                             id: 'postNo',
	                             name: 'postNo',
	                             anchor:'95%'
	                         }]
	                     },{
	                         columnWidth:.99,
	                         layout: 'form',
	                         items: [{
	                             xtype:'textarea',
	                             fieldLabel: '通讯地址',
	                             labelStyle: 'text-align:right;',
	                             maxLength:50,
	                             id: 'addr',
	                             name: 'addr',
	                             anchor:'99%'
	                         }]
	                     }
	            ]}
	            ]}]
	    });
	 var addPotentialCustomerWindow = new Ext.Window(
				{
					layout : 'fit',
					width : 700,
					height : 280,
					draggable : true,//是否可以拖动
					closable : true,// 是否可关闭
					modal : true,
					closeAction : 'hide',
					titleCollapse : true,
					buttonAlign : 'center',
					border : false,
					animCollapse : true,
					animateTarget : Ext.getBody(),
					constrain : true,
					items : [addPotentialCustomerPanel],
					buttons : [
							{
								text : '保存',
								handler : function(){
									if(!addPotentialCustomerPanel.getForm().isValid()){
										Ext.Msg.alert("系统提醒","输入有误，请重新输入!");
									return false;
									}
									Ext.Ajax.request({
										url : basepath + '/myPotentialCustomer.json?use=phone',
										method : 'POST',
										form : addPotentialCustomerPanel.getForm().id,
										waitMsg : '正在保存数据,请等待...', // 显示读盘的动画效果，执行完成后效果消失
										params : {
											'custId':Ext.getCmp('custId').getValue(),
											'custZhName':Ext.getCmp('custZhName').getValue(),
											'custTyp':Ext.getCmp('custTyp').getValue(),
											'linkUser':Ext.getCmp('linkUser').getValue(),
											'postNo':Ext.getCmp('postNo').getValue(),
											'custEnName':Ext.getCmp('custEnName').getValue(),
											'certType':Ext.getCmp('certType').getValue(),
											'linkPhone':Ext.getCmp('linkPhone').getValue(),
											'custStat':Ext.getCmp('custStat').getValue(),
											'otherName':Ext.getCmp('otherName').getValue(),
											'certNum':Ext.getCmp('certNum').getValue(),
											'addr':Ext.getCmp('addr').getValue(),
											'operate':'add'
										},
										success :checkResult,
								  		failure:function(a,b){
											var t = Ext.decode(a.responseText);
											Ext.Msg.alert('系统提示','客户已重复，无法新增!');
										}
									});
									function checkResult(response) {
										debugger;
										var resultArray = Ext.util.JSON.decode(response.status);
										var custId = response.responseText;
										if ((resultArray == 200 ||resultArray == 201)) {
					    					        addPotentialCustomerWindow.hide();
													CustdataInfo.custId = custId;
													CustdataInfo.custZhName=Ext.getCmp('custZhName').getValue();
			    					        		CustdataInfo.custTyp=Ext.getCmp('custTyp').getValue();
			    					        		CustdataInfo.ContactInfo = false;
			    					        		//修改记录客户id
			    					        		Ext.Ajax.request({
			    										url : basepath + '/ocrmFMmTelMain!saveData.json',
			    										params : {
			    										'operate':'updateCust',//修改客户ID
			    										phoneId:phoneId,
			    										custId:custId
			    										},
			    										method : 'POST',
			    										waitMsg : '正在保存数据,请等待...', // 显示读盘的动画效果，执行完成后效果消失
			    										success : function() {
			    											 //打开通话页面
			    											initdata(CustdataInfo,phoneId);
			    										},
			    										failure : function(response) {
			    											Ext.Msg.alert('提示', '修改客户ID失败');
			    										}
			    									});
										} else{
											if(resultArray == 403){
												Ext.Msg.alert('提示', response.responseText);
												}
											else {
											Ext.Msg.alert('提示', '操作失败');
										}
									}
									};
								}
							}, {
								text : '重置',
								id : 'btnReset',
								handler : function() {
								addPotentialCustomerPanel.getForm().reset();   
								}
							}, {
								text : '关闭',
								handler : function() {
								addPotentialCustomerWindow.hide();
								}
							} ]
				});
	 	Ext.getCmp('custId').setValue('');
		Ext.getCmp('custZhName').setValue('');
		Ext.getCmp('custTyp').setValue('');
		Ext.getCmp('linkUser').setValue('');
		Ext.getCmp('certType').setValue('');
		Ext.getCmp('custEnName').setValue('');
		Ext.getCmp('linkPhone').setValue(phoneNum);
		Ext.getCmp('custStat').setValue('');
		Ext.getCmp('certNum').setValue('');
		Ext.getCmp('otherName').setValue('');
		Ext.getCmp('postNo').setValue('');
		Ext.getCmp('addr').setValue('');
		Ext.getCmp("custZhName").setReadOnly(false);
		Ext.getCmp("certNum").setReadOnly(false);
		addPotentialCustomerWindow.show();
	}
	//新建潜在客户处理end..........
	
	//选择已知客户.............
	function choseCust(phoneId){
		var store = new Ext.data.Store({
			restful:true,	
	        proxy : new Ext.data.HttpProxy({url:basepath+'/customerBaseInformation.json'}),
	        reader: new Ext.data.JsonReader({
	        	totalProperty : 'json.count',
	        	root:'json.data'
	        }, [
				{name: 'custId',mapping :'CUST_ID'},
				{name: 'custZhName',mapping :'CUST_ZH_NAME'},
				{name:'custTyp',mapping:'CUST_TYP'},
				{name:'CUST_STAT_ORA'},
				{name: 'CUST_TYP_ORA'},
				{name: 'CUST_LEV_ORA'}
				
			])
		});
		 //复选框
		var sm = new Ext.grid.CheckboxSelectionModel({singleSelect:true});

		// 定义自动当前页行号
		var rownum = new Ext.grid.RowNumberer({
					header : 'No.',
					width : 28
				});
		// 定义列模型
		var cm = new Ext.grid.ColumnModel([rownum,sm, 
		        {header : '客户号',dataIndex : 'custId',sortable : true,width : 150},
			    {header : '客户名称',dataIndex : 'custZhName',width : 200,sortable : true},
			    {header : '客户状态',dataIndex : 'CUST_STAT_ORA',width : 150,sortable : true},
			    {header : '客户类型',dataIndex : 'custTyp',width : 200,hidden : true},
			    {header : '客户类型',dataIndex : 'CUST_TYP_ORA',width : 200,sortable : true,hidden : true},
			    {header : '客户级别',dataIndex : 'CUST_LEV_ORA',width : 200,sortable : true,hidden : true}
				]);
		var pagesize_combo = new Ext.form.ComboBox({
	         name : 'pagesize',
	         triggerAction : 'all',
	         mode : 'local',
	         store : new Ext.data.ArrayStore({
	             fields : ['value', 'text'],
	             data : [ [ 10, '10条/页' ], [ 20, '20条/页' ], [ 50, '50条/页' ],
						[ 100, '100条/页' ], [ 250, '250条/页' ],
						[ 500, '500条/页' ] ]
	         }),
	         valueField : 'value',
	         displayField : 'text',
	         value : '20',
	         editable : false,
	         width : 85
	     });
	    var number = parseInt(pagesize_combo.getValue());
	    pagesize_combo.on("select", function(comboBox) {
	    	  bbar.pageSize = parseInt(pagesize_combo.getValue()),
			store.load({
						params : {
							start : 0,
							limit : parseInt(pagesize_combo.getValue())
						}
					});
		});
		var bbar = new Ext.PagingToolbar({
	        pageSize : number,
	        store : store,
	        displayInfo : true,
	        displayMsg : '显示{0}条到{1}条,共{2}条',
	        emptyMsg : "没有符合条件的记录",
	        items : ['-', '&nbsp;&nbsp;', pagesize_combo]
	    });
		var resultGrid = new Ext.grid.GridPanel({
			height : 300,
			frame : true,
			autoScroll : true,
			region : 'center', 
			store : store, // 数据存储
			stripeRows : true, // 斑马线
			cm : cm, // 列模型
			sm : sm, // 复选框
			tbar : [{text:'选择',
					handler:function(){
					//更新客户id信息，更新客户联系方式，打开通话界面
						var selectLength = resultGrid.getSelectionModel().getSelections().length;
						var selectRe = resultGrid.getSelectionModel().getSelections()[0];
				if (selectLength != 1) {
					Ext.Msg.alert('提示', '请选择一条记录');
				} 
						custId = selectRe.data.custId;
						//修改记录客户id
		        		Ext.Ajax.request({
							url : basepath + '/ocrmFMmTelMain!saveData.json',
							params : {
							'operate':'updateCust',//修改客户ID信息
							phoneId:phoneId,
							custId:custId
							},
							method : 'POST',
							waitMsg : '正在保存数据,请等待...', // 显示读盘的动画效果，执行完成后效果消失
							success : function() {
								//更新联系方式
								Ext.Ajax.request({
									url : basepath + '/ocrmFMmTelMain!changeNum.json',
									params : {
									custId:custId,
									num:phoneNum
									},
									method : 'POST',
									waitMsg : '正在保存数据,请等待...', // 显示读盘的动画效果，执行完成后效果消失
									success : function() {
										debugger;
										CustdataInfo.custId = custId,
										CustdataInfo.custZhName=selectRe.data.custZhName;
    					        		CustdataInfo.custTyp=selectRe.data.custTyp;
    					        		CustdataInfo.ContactInfo= false;
										//打开通话页面
										initdata(CustdataInfo,phoneId);
										custChoseWin.hide();
									},
									failure : function(response) {
										Ext.Msg.alert('提示', '更新联系方式失败');
									}
								});
								 
							},
							failure : function(response) {
								Ext.Msg.alert('提示', '修改客户ID信息失败');
							}
						});
				}}], // 表格工具栏
			bbar:bbar,
			viewConfig:{
				   forceFit:false,
				   autoScroll:true
				},
			loadMask : {
				msg : '正在加载表格数据,请稍等...'
			}
		});

		var searchForm =new Ext.form.FormPanel({
			labelWidth : 90, // 标签宽度
			frame : true, //是否渲染表单面板背景色
			labelAlign : 'middle', // 标签对齐方式
			buttonAlign : 'center',
			height : 80,
			layout : 'column',
			border : false,
			items : [{
						columnWidth : .5,
						layout : 'form',
						labelWidth : 70, // 标签宽度
						defaultType : 'textfield',
						border : false,
						items : [{
							fieldLabel : '客户名称',
							name : 'CUST_ZH_NAME',
							xtype : 'textfield', // 设置为数字输入框类型
							labelStyle: 'text-align:right;',
							anchor : '90%'}]}],
							buttons : [{
								text : '查询',
								handler : function() {
									var parameters = searchForm.getForm().getValues(false);
									store.baseParams = {
										'condition':Ext.util.JSON.encode(parameters)
									};
									store.load({      
										params : {
			                               start : 0,
			                               limit : bbar.pageSize
			                            }
									});     
							
							   }},{
								text : '重置',
								     handler : function() {
								    	 searchForm.getForm().reset();
									}
								}]			});
		
		
		 var custChoseWin = new Ext.Window(
					{
						width : 700,
						height : 400,
						draggable : true,//是否可以拖动
						closable : true,// 是否可关闭
						modal : true,
						closeAction : 'hide',
						titleCollapse : true,
						buttonAlign : 'center',
						border : false,
						animCollapse : true,
						animateTarget : Ext.getBody(),
						constrain : true,
						items : [searchForm,resultGrid]
					});
		 custChoseWin.show();
						
	}
	//选择已知客户end.............