Ext.onReady(function() {
	Ext.QuickTips.init();
	// 得到当前用户的客户号
		var cust_id = oCustInfo.cust_id;
		var cust_name = oCustInfo.cust_name;

		 var textType = new Ext.data.ArrayStore({
		        fields:['myId','displayText'],
		        data:[['1','营业执照'],['2','代码证'],['3','法定代表人身份证、实际经营者身份证复印件'],['4','贷款卡'],['5','授权委托书'],
		              ['6','授权代理人身份证'],['7','企业章程'],['8','资信等级证书'],['9','国税、地税登记证'],['10','特种行业经营许可证'],['11','其他资料']]
		    });
		
		var new_form = new Ext.form.FormPanel({
			id :'new_form',
			layout : 'form',
			autoScroll : true,
			labelAlign : 'right',
			frame : true,
			buttonAlign : "center",
			items:[
			{name : 'custId',xtype : 'textfield',fieldLabel : '客户编号',readOnly:true,anchor : '65%'},
			{name : 'custType',fieldLabel : '客户名称',xtype : 'textfield',readOnly:true,anchor : '65%'},
			{	
				fieldLabel: '文件类型',name: 'fileType',hiddenName: 'fileType',  allowBlank : false,forceSelection : true,resizable:true,xtype:'combo',labelStyle: 'text-align:right;',triggerAction:'all',mode:'local',store:textType,valueField:'myId',displayField:'displayText',emptyText:'请选择', anchor : '65%'
            },
//			{name : 'fileType',fieldLabel : '文件类型',xtype : 'textfield'},
			{name : 'fileName',fieldLabel : '文件名称',  allowBlank : false,xtype : 'textfield',anchor : '65%'},
			{name : 'docuAddr',fieldLabel : '文件地址',xtype : 'textfield',anchor : '65%'},
			{name : 'id',xtype : 'hidden'}
			],
			buttons :[{
					text:'保存',
					id :'new_save',
					handler:function(){
						if (!new_form.form.isValid()) {
						Ext.Msg.alert('提示', '输入不合法，请重新输入');
						return;
						}
						Ext.Ajax.request({
						 url : basepath + '/ElecMgrInfo.json',
						 form: new_form.form.id,
						 method : 'POST',
						 failure : function(form, action) {
								Ext.MessageBox.alert('新增操作', '新增失败！');
						},
						success : function(response) {
								Ext.MessageBox.alert('新增操作', '新增成功！');
								listPanel.grid.getStore().reload();
								new_reset();
								new_win.hide();
							}
						});
					}
			},'-',{
				text :'修改',
				id :'new_update',
				handler:function(){
					if (!new_form.form.isValid()) {
						Ext.Msg.alert('提示', '输入不合法，请重新输入');
						return;
						}
						Ext.Ajax.request({
						 url : basepath + '/ElecMgrInfo.json',
						 form: new_form.form.id,
						 method : 'POST',
						 failure : function(form, action) {
								Ext.MessageBox.alert('修改操作', '修改失败！');
						},
						success : function(response) {
								Ext.MessageBox.alert('修改操作', '修改成功！');
								listPanel.grid.getStore().reload();
								new_reset();
								new_win.hide();
							}
						});
				}
			},'-',{
				text:'重置',
				id :'new_reset',
				handler:function(){
				new_reset();
				}
			
			},'-',{
				text:'关闭',
				handler:function(){
				new_reset();
				new_win.hide();
				}
			}]
		});
					var new_win = new Ext.Window({
							plain : true,
							layout : 'fit',
							resizable : true,
							draggable : true,
							closable : true,
							closeAction : 'hide',
							modal : true, // 模态窗口
							shadow : true,
							loadMask : true,
							maximizable : true,
							collapsible : true,
							titleCollapse : true,
							border : false,
							width : 450,
							height : 260,
							title : '客户电子管理信息',
							items : [ new_form ]
						});
		
		// 最终展现的panel
		var listPanel = new Mis.Ext.CrudPanel( {
			id : "listPanel",
			title : "客户电子管理信息",
			stUrl : basepath + '/ElecMgrInfo!indexPage.json?cust_id='+cust_id,
//			addUrl : basepath + '/ElecMgrInfo.json',
//			updateUrl : basepath + '/ElecMgrInfo.json',
//			deUrl : basepath + '/ElecMgrInfo!batchDestroy.json',
			primary : "id",
			checkbox : true,
			//自动滚动
			autoScroll:true,
			//定义宽度
			gridWidth : document.body.clientWidth-225,
			//禁止自适应宽度
			forceFit : false,
			//定义查询条件Form的高度
//			gridHeight :document.body.scrollHeight-60,
			seFormHeight : 30,
			//查询列表字段定义，有header属性则在页面显示
			//如果需要做映射需要定义store , mappingkey ,mappingvalue 三个属性
			gclms : [ 
			    {name : 'id'	},  
			    {name : 'custId',header : '客户编号'},  
				{name : 'custType',header : '客户名称'},
				{name : 'fileType',header : '文件类型',hidden:true},
				{name : 'fileName',header : '文件名称'},
				{name : 'docuAddr',header:'文档地址'}
			],
			pagesize : 20,
					// 新增、修改、详情的form的字段
			buts :[{
					text :'新增',
					iconCls :'addIconCss',
					handler:function(){
				text_arr = new_form.findByType('textfield');
				for(var ta=0;ta<text_arr.length;ta++){
					text_arr[ta].setReadOnly(false);
				}
						new_win.show();
						new_reset();
						Ext.getCmp('new_save').show();
						Ext.getCmp('new_update').hide();
						Ext.getCmp('new_reset').show();
						
					}
				},'-',{
					text :'修改',
					 iconCls:'editIconCss',
					handler:function(){
					text_arr = new_form.findByType('textfield');
					for(var ta=0;ta<text_arr.length;ta++){
						text_arr[ta].setReadOnly(false);
					}
						var records = listPanel.grid.getSelectionModel().getSelections();
									var recordsLen = records.length;
									if (recordsLen !=1) {
										Ext.Msg.alert("系统提示信息","请选择其中一条记录进行修改！");
										return ;
									} else {
										new_win.show();
										new_form.getForm().loadRecord(records[0]);
										Ext.getCmp('new_save').hide();
										Ext.getCmp('new_update').show();
										Ext.getCmp('new_reset').hide();
									}
					}
				},'-',{
					text:'删除',
					iconCls : 'deleteIconCss',
					handler:function(){
						if (listPanel.grid.selModel.hasSelection()) {
							Ext.MessageBox
									.confirm(
											'系统提示信息',
											'确定要删除所选的记录吗?',
											function(buttonobj) {
												if (buttonobj == 'yes'
														&& listPanel.primary) {
													var records = listPanel.grid.selModel
															.getSelections();// 得到被选择的行的数组
													var selectLength = records.length;// 得到行数组的长度
													var idStr = '';
													for ( var i = 0; i < selectLength; i++) {
														selectRe = records[i];
														tempId = selectRe
																.get(listPanel.primary);
														idStr += tempId;
														if (i != selectLength - 1)
															idStr += ',';
													};
													Ext.Ajax
															.request({
																url : basepath + '/ElecMgrInfo!batchDestroy.json',
																params : {
																	idStr : idStr
																},
																waitMsg : '正在保存数据,请等待...', // 显示读盘的动画效果，执行完成后效果消失
																method : 'POST',
																scope : listPanel,
																success : function() {
																	Ext.Msg.alert('提示', '操作成功');
																	listPanel.loadCurrData();
																},
																failure : function() {
																	Ext.Msg.alert('提示', '操作失败');
																	listPanel.loadCurrData();
																}
															});
												}
											}, listPanel);
						} else {
							Ext.Msg.alert("提示", "请先选择要删除的行!");
						}
					}
				},'-',{
					text:'查看',
					iconCls : 'detailIconCss',
					handler:function(){
							var records = listPanel.grid.getSelectionModel().getSelections();
									var recordsLen = records.length;
									if (recordsLen !=1) {
										Ext.Msg.alert("系统提示信息","请选择其中一条记录进行修改！");
										return ;
									} else {
										
										new_win.show();
										new_form.getForm().loadRecord(records[0]);
										Ext.getCmp('new_save').hide();
										Ext.getCmp('new_update').hide();
										Ext.getCmp('new_reset').hide();
										text_arr = new_form.findByType('textfield');
												for(var ta=0;ta<text_arr.length;ta++){
													text_arr[ta].setReadOnly(true);
												}
									}
					}
				},'-',{
					text :'附件信息',
					handler : function (){
                var record = listPanel.grid.getSelectionModel().getSelected(); 
                if (!record) {
                    Ext.MessageBox.alert('查询操作', '请选择要操作的数据！');
                    return false;
                }
                var checkedNodes = listPanel.grid.getSelectionModel().selections.items;
                if(checkedNodes.length>1){
                    Ext.MessageBox.alert('查询操作', '您选择的记录过多！');
                    return false;
                }
                var idStr = record.get('id');
                
                uploadForm.relaId = idStr;
                uploadForm.modinfo = 'customer';
                var condi = {};
                condi['relationInfo'] = idStr;
                condi['relationMod'] = 'customer';
                Ext.Ajax.request({
                    url:basepath+'/queryanna.json',
                    method : 'GET',
                    params : {
                        "condition":Ext.encode(condi)
                    },
                    failure : function(a,b,c){
                        Ext.MessageBox.alert('查询异常', '查询失败！');
                    },
                    success : function(response){
                        var anaExeArray = Ext.util.JSON.decode(response.responseText);
                        appendixStore.loadData(anaExeArray.json.data);
                        appendixGridPanel.getView().refresh();
                    }
                });
                appendixWindow.show();
            }
			}]
		});
		
		var new_reset = function(){
			new_form.form.reset();
			new_form.getForm().findField('custId').setValue(cust_id);	
			new_form.getForm().findField('custType').setValue(cust_name);
		};
		// 布局模型
		var viewport = new Ext.Panel( {
			renderTo:'viewport_center',
			height:document.body.scrollHeight-35,
     		layout : 'fit',
//			autoScroll:true,
			items : [ listPanel ]
		});
	});