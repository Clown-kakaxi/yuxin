/**
 * 本js主要负责显示成员加入客户群界面（选择创建客户群还是加入已有客户群）
 * 2014-09-02 update
 */

//群成员类型
var groupMemberType = '';

var wayStore =  new Ext.data.ArrayStore({
	fields : [ 'key', 'value'  ],
	data : [ [ 1, '加入已有客户群' ], [ 2, '创建新的客户群' ] ]
});
 
 //客户群放大镜
var search_cust_group = new Com.yucheng.bcrm.common.CustGroup({ 
	fieldLabel : '选择客户群', 
	labelStyle: 'text-align:right;',
	name : 'custGroup',
	allowDtGroup:false,//不能选择动态客户群
    singleSelected:true,//单选复选标志
	editable : false,
	blankText:"请填写",
	//hidden:true,
	anchor : '85%',
	hiddenName:'groupStr'
});

//选择加入已有客户群还是新建客户群
var choseWayForm = new Ext.form.FormPanel({
	 labelWidth : 80,
	 height : 200,
	 frame : true,
	 labelAlign : 'right',
	 region : 'center',
	 autoScroll : true,
	 buttonAlign : "center",
	 items : [{
		 layout : 'column',
    	 items : [ {
    		 columnWidth : .5,
    		 layout : 'form',
    		 items : [ 
    		 	{xtype : 'combo',name : 'jionWay',fieldLabel : '加入方式',forceSelection : true,resizable : true,labelStyle : 'text-align:right;',
                    triggerAction : 'all',mode : 'local', store : wayStore, valueField : 'key',displayField : 'value',emptyText : '请选择',anchor : '85%',
                    listeners:{
    			 		select :function(){
			    			if(choseWayForm.form.findField('jionWay').getValue()=='1'){
			    				choseWayForm.form.findField('custGroup').setVisible(true);
			    				choseWayForm.form.findField('custGroup').reset();
			    			}
			    			if(choseWayForm.form.findField('jionWay').getValue()=='2'){
			    				choseWayForm.form.findField('custGroup').setVisible(false);
	    						Ext.MessageBox.confirm('提示','系统将建立客户群信息，确定要执行吗?',function(buttonId){
    	    						if(buttonId.toLowerCase() == "no"){
    	    							return;
    	    						}
					    			//设置初始值
	    	    					editGroupBaseInfoForm.getForm().reset();
					    			editGroupBaseInfoForm.form.findField('custFrom').setValue('1');//来源 
					    			editGroupBaseInfoForm.form.findField('groupMemberType').setValue(groupMemberType);//成员类型   对公
					    			//默认提交一次保存请求，创建客户群，返回客户群号
					    			Ext.Ajax.request({
					    	    		url : basepath + '/customergroupinfo.json',
					    	    		params : {
					    	    			operate: 'addBySearch',
					    	    			'groupMemberType':groupMemberType
					    	    		},
					    	    		method : 'POST',
					    	    		form : editGroupBaseInfoForm.getForm().id,
					    	    		success : function(resp) {
					    	    			Ext.Ajax.request({
					    	    				url: basepath +'/customergroupinfo!getPid.json',
					    	    				success:function(response){
					    	    					var groupId = Ext.util.JSON.decode(response.responseText).pid;
					    	    					var tempGroupNumber = '';
					    	    					if(groupId.length==5){
					    						    	tempGroupNumber=tempGroupNumber+'C00'+groupId;
					    						  	}else if(groupId.length==6){
					    						  	    tempGroupNumber=tempGroupNumber+'C0'+groupId;
					    						  	}else {
					    						  		tempGroupNumber=tempGroupNumber+'C0'+groupId;
					    						  	}
		    		    							editGroupBaseInfoForm.form.findField('id').setValue(groupId);
		    		    							editGroupBaseInfoForm.form.findField('custBaseNumber').setValue(tempGroupNumber);
		    		    							editGroupBaseInfoForm.form.findField('custBaseCreateDate').setValue(new Date());
		    		    							editGroupBaseInfoForm.form.findField('custBaseCreateName').setValue(__userId);
		    		    							editGroupBaseInfoForm.form.findField('custBaseCreateOrg').setValue(__units);
					    		    							 
	    		    							 	//将选中的客户存入关联客户信息
	    		    							 	Ext.Ajax.request({
	    		    		    	    				url : basepath + '/groupmemberedit!saveMemberBySearch.json',
	    		    		    	    				params:{
	    		    		    	    					'custId':idStr,
	    		    		    	    					type:'new',
	    		    		    	    					'groupId':groupId
	    		    		    	    				},
	    		    		    	    				success : function() {
	    		    		    	    					//展示客户群新增页面（其实是修改页面）
															editGroupBaseInfoWindow.setTitle('客户群新增');
															editGroupBaseInfoWindow.show();
															editGroupBaseInfoPanel.buttons[0].setDisabled(true);
	    		    		    		    				choseWin.hide();
	    		    		    		    				editGroupBaseInfoPanel.layout.setActiveItem('info1'); 
	    		    		    		    				editGroupBaseInfoForm.form.findField('groupMemberType').setReadOnly(false);
											    			editGroupBaseInfoForm.form.findField('custFrom').setReadOnly(true);
											    			editGroupBaseInfoForm.form.findField('custFrom').cls = 'x-readOnly';
	    		    		    		    				idStr = '';
	    		    		    	    				}
	    		    							 	});
					    	    				}
					    	    			});
			    	    				},
			    	    				failure : function(response) {
			    	    					var resultArray = Ext.util.JSON.decode(response.status);
			    	    				    if(resultArray == 403) {
			    	    				        Ext.Msg.alert('系统提示', response.responseText);
			    	    				  	} else{
			    	    						Ext.Msg.alert('提示', '操作失败,失败原因:' + response.responseText);
			    	    					}
			    	    				    idStr = '';
			    	    				}
    	    				
    	    						});
    			 				});
    		 				}
    					}
    		 		}
                }]
    	 }, {
    		 columnWidth : .5,
    		 layout : 'form',
    		 items : [search_cust_group]
    	 }]
	 }]
 });

//选择加入已有客户群还是新建客户群的窗口
var choseWin = new Ext.Window({
	title : '成员加入客户群',
	closeAction:'hide',
	height:'200',
	width:'600',
	modal : true,//遮罩
	buttonAlign:'center',
	layout:'fit',
	items:[choseWayForm],
	buttons:[{
    	 text:'保存',
    	 handler: function(){
    	 if (choseWayForm.form.findField('jionWay').getValue()==null||choseWayForm.form.findField('jionWay').getValue()=='') {
    		 Ext.MessageBox.alert('系统提示信息', '请先选择加入方式！');
    		 return false;
    	 }
    	 if(choseWayForm.form.findField('custGroup').getValue()==null||choseWayForm.form.findField('custGroup').getValue()==''){
    		 Ext.MessageBox.alert('系统提示信息', '请先选择客户群！');
    		 return false;
    	 }
    	//将选中的客户存入关联客户信息
		Ext.Ajax.request({
			url : basepath + '/groupmemberedit!saveMemberBySearch.json',
			params:{
				'custId':idStr,
				type:'add',
				'groupId':choseWayForm.form.findField('custGroup').custBaseId
			},
			method : 'GET',
			waitMsg : '正在保存,请等待...',
			success :checkResult,
			failure :checkResult
		 });
		 function checkResult(response){
			var resultArray = Ext.util.JSON.decode(response.status);
			if (resultArray == 200 ||resultArray == 201) {
				var number =  Ext.util.JSON.decode(response.responseText).number;
				Ext.MessageBox.alert('系统提示信息', '操作成功，排除重复之后加入客户'+number+'位');
			}else if(resultArray == 403) {
		        Ext.Msg.alert('系统提示', response.responseText);
			} else{
				Ext.Msg.alert('提示', '操作失败,失败原因:' + response.responseText);
			}
			choseWin.hide();
			idStr = '';
		 }
     }
	         
 }, {
	 text:'重置',
	 handler:function(){
		 choseWayForm.getForm().reset();
 	}
 }]
});

