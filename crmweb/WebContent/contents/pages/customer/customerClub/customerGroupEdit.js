/**
 * 调整了客户群新增面板（代码格式化处理）
 * 2014-09-02 update
 */

var ifadd = 'true';
var __hiddeAble=false;
var __modelSign1=.45;
var __modelSign2=.55;
	
var _tempImpFileName = "";
var pkHead = "";
/**
 * 导入表单对象，此对象为全局对象，页面直接调用。
 */
var importForm = new Ext.FormPanel({
	id:'info5',
    height : 200,
    width : '100%',
    title:'文件导入',
    fileUpload : true, 
    dataName:'file',
    frame:true,
    tradecode:"importantGroupCust",
    
    /**是否显示导入状态*/
    importStateInfo : '',
    importStateMsg : function (state){
		var titleArray = ['excel数据转化为SQL脚本','执行SQL脚本...','正在将临时表数据导入到业务表中...','导入成功！'];
		this.importStateInfo = "当前状态为：[<font color='red'>"+titleArray[state]+"</font>];<br>";
	},    
    /**是否显示 当前excel数据转化为SQL脚本成功记录数*/
    curRecordNumInfo : '',
    curRecordNumMsg : function(o){
		this.curRecordNumInfo = "当前excel数据转化为SQL脚本成功记录数[<font color='red'>"+o+"</font>];<br>";
	},
    /**是否显示 当前sheet页签记录数*/
	curSheetRecordNumInfo : '',
    curSheetRecordNumMsg : function (o) {
		this.curSheetRecordNumInfo = "当前sheet页签记录数：[<font color='red'>"+o+"</font>];<br>";
	},
    /**是否显示 当前sheet页签号*/
    sheetNumInfo : '',
    sheetNumMsg : function(o){
		this.sheetNumInfo = "当前sheet页签号为：[<font color='red'>"+o+"</font>];<br>";
	},
    /**是否显示 sheet页签总数*/
    totalSheetNumInfo : '',
    totalSheetNumMsg : function(o){
		this.totalSheetNumInfo = "sheet页签总数：[<font color='red'>"+o+"</font>];<br>";
	},
    /**是否显示 已导入完成sheet数*/
    finishSheetNumInfo : '',
    finishSheetNumMsg : function(o){
		this.finishSheetNumInfo = "已导入完成sheet数[<font color='red'>"+o+"</font>];<br>";
	},
    /**是否显示 已导入完成记录数*/
	finishRecordNumInfo : '',
    finishRecordNumMsg : function(o){
		this.finishRecordNumInfo = "已导入完成记录数[<font color='red'>"+o+"</font>];<br>";
	},
    /**当前excel数据转化为SQL脚本成功记录数*/
    curRecordNum : 0,
    /**导入总数*/
	totalNum : 1,
	/**进度条信息*/
	progressBarText : '',
	/**进度条Msg*/
	progressBartitle : '',
    proxyStore : undefined,
    items: [new Ext.form.TextField({
        xtype : 'textfield',
        id:'littleim',
        name:'annexeName',
        inputType:'file',
        fieldLabel : '文件名称',
        anchor : '90%'
    })],
	/**进度条*/
    progressBar : null,    
    /***import成功句柄*/
    importSuccessHandler : function (json){	
		if (json != null) {	
			if (typeof(json.curRecordNum) != 'undefined' && this.curRecordNumMsg) {
				this.curRecordNumMsg(json.curRecordNum);
				this.curRecordNum = json.curRecordNum;
			}
			if (typeof(json.importState) != 'undefined' && this.importStateMsg) {
				this.importStateMsg(json.importState);
			}				
			if (typeof(json.curSheetRecordNum) != 'undefined' && this.curSheetRecordNumMsg) {
				this.curSheetRecordNumMsg(json.curSheetRecordNum);
			}
			if (typeof(json.sheetNum) != 'undefined' && this.sheetNumMsg) {
				this.sheetNumMsg(json.sheetNum);
			}
			if (typeof(json.totalSheetNum) != 'undefined' && this.totalSheetNumMsg) {
				this.totalSheetNumMsg(json.totalSheetNum);
			}	
			if (typeof(json.finishSheetNum) != 'undefined' && this.finishSheetNumMsg) {
				this.finishSheetNumMsg(json.finishSheetNum);
			}
			if (typeof(json.finishRecordNum) != 'undefined' && this.finishRecordNumMsg) {
				this.finishRecordNumMsg(json.finishRecordNum);
			}
		} else {
			this.curRecordNumInfo = '';
			this.importStateInfo = '';
			this.curSheetRecordNumInfo = '';
			this.sheetNumInfo = '';
			this.totalSheetNumInfo = '';
			this.finishSheetNumInfo = '';
			this.finishRecordNumInfo = '';
		}
		
		this.progressBartitle = '';
		/**进度条Msg信息配置：各信息项目显示内容由各自方法配置*/
		this.progressBartitle += this.curRecordNumMsg      ?this.curRecordNumInfo:'';
		this.progressBartitle += this.importStateMsg 	   ?this.importStateInfo:'';
		this.progressBartitle += this.curSheetRecordNumMsg ?this.curSheetRecordNumInfo:'';
		this.progressBartitle += this.sheetNumMsg 	   	   ?this.sheetNumInfo:'';
		this.progressBartitle += this.totalSheetNumMsg 	   ?this.totalSheetNumInfo:'';
		this.progressBartitle += this.finishSheetNumMsg    ?this.finishSheetNumInfo:'';
		this.progressBartitle += this.finishRecordNumMsg   ?this.finishRecordNumInfo:'';
		
		showProgressBar(this.totalNum,this.curRecordNum,this.progressBarText,this.progressBartitle,"上传成功，正在导入数据，请稍候");
	},
    buttons : [{
        text : '导入文件',
        handler : function() {
            var tradecode = this.ownerCt.ownerCt.tradecode;
            var proxyStorePS = this.ownerCt.ownerCt.proxyStore;
            var proxyHttpPH = this.ownerCt.ownerCt.proxyHttp;
            if(tradecode==undefined ||tradecode==''){
                Ext.MessageBox.alert('Debugging！','You forgot to define the tradecode for the import form!');
                return false;
            }
            var impRefreshHandler = 0;
            if (this.ownerCt.ownerCt.getForm().isValid()){
//	                    this.ownerCt.ownerCt.ownerCt.hide();
                var fileNamesFull = this.ownerCt.ownerCt.items.items[0].getValue();
                var extPoit = fileNamesFull.substring(fileNamesFull.indexOf('.'));
                if(extPoit=='.xls'||extPoit=='.XLS'||extPoit=='.xlsx'||extPoit=='.XLSX'){
                } else {
                	Ext.MessageBox.alert("文件错误","导入文件不是XLS或XLSX文件。");
                    return false;
                }
                showProgressBar(1,0,'','','正在上传文件...');
                
                this.ownerCt.ownerCt.getForm().submit({
                    url : basepath + '/FileUpload?isImport=true',
                    success : function(form,o){                    		 
                        _tempImpFileName = Ext.util.JSON.decode(o.response.responseText).realFileName;
                        var condi = {};
                        condi['filename'] =_tempImpFileName;
                        condi['tradecode'] = tradecode;
                        Ext.Ajax.request({
                            url:basepath+"/ImportAction.json",
                            method:'GET',
                            params:{
                                "condition":Ext.encode(condi)
                            },
                            success:function(){
                            	importForm.importSuccessHandler(null);                                	
                                var importFresh = function(){
                                    Ext.Ajax.request({
                                        url:basepath+"/ImportAction!refresh.json",
                                        method:'GET',
                                        success:function(a){                                        		
                                            if(a.status == '200' || a.status=='201'){
                                                var res = Ext.util.JSON.decode(a.responseText);
                                                if(res.json.result!=undefined&&res.json.result=='200'){
                                                    window.clearInterval(impRefreshHandler); 
                                                    if(res.json.BACK_IMPORT_ERROR&&res.json.BACK_IMPORT_ERROR=='FILE_ERROR'){
                                                    	Ext.Msg.alert("提示","导出文件格式有误，请下载导入模版。");
                                                    	return;
                                                    }
                                                    importState = true;
                                                    progressWin.hide();
                                                    //将临时表数据存入客户群成员表
                                                    var pkHead = res.json.pkHead;
                                                    Ext.Msg.wait('正在处理，请稍后......','系统提示');
                                                    Ext.Ajax.request({
	    		    		    	    				url : basepath + '/groupmemberedit!saveMemberByImp.json',
	    		    		    	    				params:{
	    		    		    	    					pkHead: pkHead,
	    		    		    	    					groupId:editGroupBaseInfoForm.form.findField('id').getValue()
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
                                 							Ext.MessageBox.alert('系统提示信息', '操作成功，成功加入客户'+number+'位');
    		    		    	    					//展示客户群新增页面（其实是修改页面）
														editGroupBaseInfoWindow.setTitle('客户群新增-->第2步，共2步');
														editGroupBaseInfoPanel.buttons[0].setDisabled(false);
														editGroupBaseInfoPanel.buttons[1].setDisabled(true);
    		    		    	    				}else if(resultArray == 403) {
    		    		    	 				           Ext.Msg.alert('系统提示', response.responseText);
    		    		  	    				  } else{
    		    		  	    					Ext.Msg.alert('提示', '操作失败,失败原因:' + response.responseText);
    		    		  	    				  }
    		    		  				 }
                                                    
                                                    
                                                }else if(res.json.result!=undefined&&res.json.result=='900'){
                                                    window.clearInterval(impRefreshHandler);
                                                    new Ext.Window({
                                                        title:"导入失败：导入线程处理失败！",
                                                        width:200,
                                                        height:200,
                                                        bodyStyle:'text-align:center',
                                                        html: '<img src="'+basepath+'/contents/img/UltraMix55.gif" />'
                                                    }).show();
                                                }else if (res.json.result!=undefined&&res.json.result=='999'){
                                                	importForm.importSuccessHandler(res.json);
                                                }
                                            }
                                        }
                                    });
                                };
                                impRefreshHandler = window.setInterval(importFresh, 1000);
                            },
                            failure:function(){}
                        });
                       
                    },
                    failure : function(form, o){
                        Ext.Msg.show({
                            title : 'Result',
                            msg : '数据文件上传失败，请稍后重试!',
                            buttons : Ext.Msg.OK,
                            icon : Ext.Msg.ERROR
                        });
                    }
                });
            }
        }
    },{
		id:'importTemple',
		text : '导入模板下载',
		iconCls:'addIconCss',
		disabled:false,
		handler : function(){
            window.open( basepath+'/TempDownload?filename=importantGroupCust.xlsx','', 'height=100, width=200, top=300, left=500, toolbar=no,menubar=no, scrollbars=no, resizable=no,location=no, status=no');
        }
	}]
});
	
//证件类型
var certTypeStore = new Ext.data.Store({
	restful : true,
	sortInfo : {
		field : 'key',
		direction : 'ASC'
	},
	autoLoad : true,
	proxy : new Ext.data.HttpProxy({
		url : basepath + '/lookup.json?name=XD000040'
	}),
	reader : new Ext.data.JsonReader({
		root : 'JSON'
	}, [ 'key', 'value' ])
});
 
 //客户群分类
 var customergroupTypeStore = new Ext.data.Store({
	restful : true,
	sortInfo : {field : 'key',direction : 'ASC'},
	autoLoad : true,
	proxy : new Ext.data.HttpProxy({
		url : basepath + '/lookup.json?name=CUSTOMER_GROUP_TYPE'
	}),
	reader : new Ext.data.JsonReader({
		root : 'JSON'
	}, [ 'key', 'value' ])
});
 //客户来源
 var customerSourceTypeStore = new Ext.data.Store({
	restful : true,
	sortInfo : {field : 'key',direction : 'ASC'},
	autoLoad : true,
	proxy : new Ext.data.HttpProxy({
		url : basepath + '/lookup.json?name=CUSTOMER_SOURCE_TYPE'
	}),
	reader : new Ext.data.JsonReader({
		root : 'JSON'
	}, [ 'key', 'value' ])
});
 //群成员类型
 var groupMemeberTypeStore = new Ext.data.Store({
	restful : true,
	sortInfo : {field : 'key',direction : 'ASC'},
	autoLoad : true,
	proxy : new Ext.data.HttpProxy({
		url : basepath + '/lookup.json?name=GROUP_MEMEBER_TYPE'
	}),
	reader : new Ext.data.JsonReader({
		root : 'JSON'
	}, [ 'key', 'value' ])
});
//客户群共享范围
var shareFlagStore = new Ext.data.Store({
	restful : true,
	sortInfo : {field : 'key',direction : 'ASC'},
	autoLoad : true,
	proxy : new Ext.data.HttpProxy({
		url : basepath + '/lookup.json?name=SHARE_FLAG'
	}),
	reader : new Ext.data.JsonReader({
		root : 'JSON'
	}, [ 'key', 'value' ])
});

var mainCustomerManager = new Com.yucheng.crm.common.OrgUserManage({ 
	xtype:'userchoose',
	fieldLabel : '所属客户经理', 
	labelStyle: 'text-align:right;',
	name : 'BELONG_CUSTMANAGER',
	id : 'BELONG_CUSTMANAGER',
	hiddenName:'custMgrId',
	searchRoleType:("'R014','R023'"),  //指定查询角色属性 ,默认全部角色
	searchType:'SUBTREE',/* 允许空，默认辖内机构用户，指定查询机构范围属性  SUBTREE（子机构树）SUBORGS（直接子机构）PARENT（父机构）PARPATH （所有父、祖机构）ALLORG（所有机构）*/
	singleSelect:false,
	anchor : '90%',
	callback:function(){
	}
});
var mainBelongOrg= new Com.yucheng.bcrm.common.OrgField({
	searchType:'SUBTREE',/*指定查询机构范围属性  SUBTREE（子机构树）SUBORGS（直接子机构）PARENT（父机构）PARPATH （所有父、祖机构）ALLORG（所有机构）*/
	fieldLabel : '所属机构',
	labelStyle : 'text-align:right;',
	name : 'BELONG_ORG', 
	id : 'BELONG_ORG',
	hiddenName: 'instncode',   //后台获取的参数名称
	anchor : '90%',
	checkBox:true //复选标志
});

/**
 * 切换客户群新增子面板
 * @param {} btn
 * @return {Boolean}
 */ 
function changePage(btn){
	
	if (!editGroupBaseInfoForm.getForm().isValid()) {
		Ext.MessageBox.alert('提示','输入有误,请检查输入项');
			return false;
    }
	var tempId = editGroupBaseInfoForm.form.findField('id').getValue();
	if(''==tempId||undefined==tempId){
		Ext.Msg.alert('系统提示','请先完善客户群基本信息并点击保存!');
		return false;
	}
	
	//客户来源为手动选择时执行的业务逻辑
	if('1'==editGroupBaseInfoForm.form.findField('custFrom').getValue()){
		var index = Number(editGroupBaseInfoPanel.layout.activeItem.id.substring(4)); 
		if(btn.text == '上一步'){
			index -= 1; 
		    if(index <1){
		     	index = 1; 
		    } 
		   	editGroupBaseInfoWindow.setTitle('客户群新增-->第'+index+'步，共2步');
		}else{
			index += 1; 
		    editGroupBaseInfoWindow.setTitle('客户群新增-->第'+index+'步，共2步');
		    if(index=='2'){
			    customerInfoStore.load({
					params : {
						start : 0,
						limit : parseInt(customerInfopagesize_combo.getValue())
					}
				});
	        	//判定，当群成员类型为对公或对私时，客户类型不展示
	        	if('1'==editGroupBaseInfoForm.form.findField('groupMemberType').getValue()||'2'==editGroupBaseInfoForm.form.findField('groupMemberType').getValue()){
	        		searchPanel1.form.findField('CUST_TYPE').setVisible(false);
	        	}else{
	        		searchPanel1.form.findField('CUST_TYPE').setVisible(true);
	        	};
		    }
		    if(index >2) index = 2; 
		}
		groupLeaguerStore.load();
		if(index==1){
			editGroupBaseInfoPanel.buttons[0].setDisabled(true);   
		}else{
			editGroupBaseInfoPanel.buttons[0].setDisabled(false);   
		}
		   
	   	if(index==2){
		   	editGroupBaseInfoPanel.buttons[1].setDisabled(true);   
	   	}else{
		   	editGroupBaseInfoPanel.buttons[1].setDisabled(false);   
	   	}
	   	editGroupBaseInfoPanel.layout.setActiveItem('info'+index); 
	}
	
	
	//客户来源为自动筛选时执行的业务逻辑
	else if('2'==editGroupBaseInfoForm.form.findField('custFrom').getValue()){
		var index = Number(editGroupBaseInfoPanel.layout.activeItem.id.substring(4)); 
		if(btn.text == '上一步'){
			if(index <1){ 
		     	index = 1; 
		    }
		    if(index=='1') {
		    	index=1;
		     	editGroupBaseInfoWindow.setTitle('客户群新增-->第1步，共2步');
		    }
		    else if(index=='4') {
		    	index=1;
		     	editGroupBaseInfoWindow.setTitle('客户群新增-->第1步，共2步');
		    }
		}else{ 
			if(index=='1') {
		    	index=4;
		     	editGroupBaseInfoWindow.setTitle('客户群新增-->第2步，共2步');
		     	//加载客户筛选方案
		     	var group_id = editGroupBaseInfoForm.form.findField('id').getValue();
		     	simple21.removeAllItems();
		     	Ext.Ajax.request({
					url:basepath+'/queryagilequery!queryGroupCondition.json?groupId='+group_id,
					method: 'GET',
					success : function(response) {
						var conditionData = Ext.util.JSON.decode(response.responseText);
						var conditionArray=conditionData.JSON.data;
						if(conditionArray.length>0){
							ifadd = 'false';
							Ext.each(conditionArray,function(con){
								var node = treeOfPoroduct2.root.findChild("id", "b"+con.SS_COL_ITEM, true);
								if(node){
									simple21.addItems(node,con.SS_COL_OP,con.SS_COL_VALUE);
								}
							});
							if(conditionArray[0].SS_COL_JOIN=='true'){
								radio2.items.items[0].items.items[0].setValue(true);
								right_panel2.conditionJoinType = 'true';
							}else{
								radio2.items.items[1].items.items[0].setValue(true);
								right_panel2.conditionJoinType = 'false';
							}
						}else{
							ifadd = 'true';
						}
					},
					failure : function(response) {
						var resultArray = Ext.util.JSON.decode(response.status);
						if(resultArray == 403) {
							Ext.Msg.alert('提示','您没有此权限!');
						} else {
							Ext.Msg.alert('提示','操作失败!');
						}
					}
				});
		    }
		    if(index >4) index = 4; 
		}
	   	groupLeaguerStore.load();
	   	if(index==1){
		   	editGroupBaseInfoPanel.buttons[0].setDisabled(true);   
	   	}else{
		   	editGroupBaseInfoPanel.buttons[0].setDisabled(false);   
	   	}
	   
	   	if(index==4){
		   	editGroupBaseInfoPanel.buttons[1].setDisabled(true);   
	   	}else{
		   	editGroupBaseInfoPanel.buttons[1].setDisabled(false);   
	   	}
	   	editGroupBaseInfoPanel.layout.setActiveItem('info'+index); 
	}
	
	
	//客户群为证件导入时
	else if('3'==editGroupBaseInfoForm.form.findField('custFrom').getValue()){
		var index = Number(editGroupBaseInfoPanel.layout.activeItem.id.substring(4)); 
		if(btn.text == '上一步'){ 
			if(index <1){ 
				index = 1; 
			}
			if(index=='1') {
				index=1;
				editGroupBaseInfoWindow.setTitle('客户群新增-->第1步，共2步');
			}else if(index=='5') {
				index=1;
				editGroupBaseInfoWindow.setTitle('客户群新增-->第1步，共2步');
			}
		}else{ 
			if(index=='1') {
				index=5;
				editGroupBaseInfoWindow.setTitle('客户群新增-->第2步，共2步');
				importForm.form.findField('annexeName').setValue("");
			}
				    
			if(index >5) index = 5; 
		}
	   	if(index==1){
		   	editGroupBaseInfoPanel.buttons[0].setDisabled(true);   
	   	}else{
		   	editGroupBaseInfoPanel.buttons[0].setDisabled(false);   
	   	}
	   
	   	if(index==5){
		   	editGroupBaseInfoPanel.buttons[1].setDisabled(true);   
	   	}else{
		   	editGroupBaseInfoPanel.buttons[1].setDisabled(false);   
	   	}
	   	editGroupBaseInfoPanel.layout.setActiveItem('info'+index); 
	}
};	
		  


//新增、修改、详情信息窗口展示的from
var editGroupBaseInfoForm = new Ext.form.FormPanel({
	frame : true,
    title : '基本信息',
    buttonAlign : "center",
    id:'info1',
    region : 'center',
    autoScroll : true,
    labelWidth : 140,
    items : [{
		layout : 'column',
		items : [ {
			columnWidth : .5,
			layout : 'form',
			items : [ 
				{xtype : 'textfield',name : 'custBaseName',fieldLabel : '<span style="color:red">*</span>客户群名称',labelStyle : 'text-align:right;', allowBlank : false,anchor : '90%'},
                {xtype : 'combo', name : 'shareFlag',hiddenName : 'shareFlag',fieldLabel : '<span style="color:red">*</span>共享范围',store : shareFlagStore,resizable : true,labelStyle : 'text-align:right;',
					valueField : 'key',displayField : 'value',allowBlank : false,mode : 'local',editable :false,forceSelection : true,triggerAction : 'all',emptyText : '请选择',anchor : '90%'}
			]
        },{
        	columnWidth : .5,
            layout : 'form',
            items : [
            	{xtype : 'combo', name : 'groupMemberType',hiddenName : 'groupMemberType',fieldLabel : '<span style="color:red">*</span>群成员类型',labelStyle : 'text-align:right;',store : groupMemeberTypeStore,resizable : true,
					valueField : 'key',displayField : 'value',allowBlank : false,mode : 'local',triggerAction : 'all',emptyText : '请选择',anchor : '90%'},
				{xtype : 'combo', name : 'custFrom',hiddenName : 'custFrom',fieldLabel : '<span style="color:red">*</span>客户来源',store : customerSourceTypeStore,resizable : true,labelStyle : 'text-align:right;',
					allowBlank:false,valueField : 'key',displayField : 'value',mode : 'local',forceSelection : true,triggerAction : 'all',emptyText : '请选择',anchor : '90%'},
				{xtype : 'datefield',name : 'custBaseCreateDate',fieldLabel : '创建时间',format : 'Y-m-d',hidden:true,labelStyle : 'text-align:right;',anchor : '90%'},
				{xtype : 'textfield',name : 'id',labelStyle : 'text-align:right;',hidden:true,fieldLabel : 'ID',anchor : '90%' },
                {xtype : 'textfield',labelStyle : 'text-align:right;',hidden:true, fieldLabel : '群编号', name : 'custBaseNumber', anchor : '90%'},
                {xtype : 'textfield',name : 'custBaseCreateName',fieldLabel : '创建人',hidden:true,anchor : '90%'},
                {xtype : 'textfield',name : 'custBaseCreateOrg',fieldLabel : '创建机构',hidden:true, anchor : '90%' }
            ]
        },{
//        	columnWidth : .33,
//        	layout : 'form',
//        	items : [
//        		{xtype : 'combo',name : 'groupType',hiddenName : 'groupType', store : customergroupTypeStore,fieldLabel : '<span style="color:red">*</span>客户群分类',resizable : true,labelStyle : 'text-align:right;',
//					valueField : 'key',allowBlank : true,displayField : 'value',mode : 'local',editable :false,forceSelection : true,hidden: true,triggerAction : 'all',emptyText : '请选择',anchor : '90%'}
//			]
//        },{
			columnWidth : 1,
			layout : 'form',
			items : [ 
				{xtype : 'textarea',name : 'custBaseDesc',labelStyle : 'text-align:right;', fieldLabel : '客户群描述',anchor : '95%'}
			]
        }]
    }],
	buttons:[{
		text : '保存',
		handler : function() {
			if (!editGroupBaseInfoForm.getForm().isValid()) {
				Ext.MessageBox.alert('提示','输入有误,请检查输入项');
 				return false;
            }
    		Ext.Msg.wait('正在保存，请稍后......','系统提示');
			Ext.Ajax.request({
				url : basepath + '/customergroupinfo.json',
				params : {
					operate:'add'
				},
				method : 'POST',
				form : editGroupBaseInfoForm.getForm().id,
				success : function() {
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
							 Ext.Msg.alert('提示', '操作成功');
							 editGroupBaseInfoForm.form.findField('groupMemberType').setReadOnly(true);
							 editGroupBaseInfoForm.form.findField('custFrom').setReadOnly(true);
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
				}
			});
		}
	}]
});



//客户群维护窗口展示的from
var editGroupBaseInfoPanel = new Ext.Panel( {
    layout : 'card',
    activeItem : 0,     
    autoScroll : true,
    buttonAlign : "center",
    items : [ editGroupBaseInfoForm,groupLeaguerPanel,agileQueryPanel2,importForm],
    buttons : [{ 
	    text : '上一步', 
	    handler :changePage 
	}, { 
	    text : '下一步', 
	    handler :changePage 
	}, {
		text : '完    成',
		handler : function() {
		
			if (!editGroupBaseInfoForm.getForm().isValid()) {
				Ext.MessageBox.alert('提示','输入有误,请检查输入项');
					return false;
		    }
			Ext.Msg.wait('正在保存，请稍后......','系统提示');
			Ext.Ajax.request({
				url : basepath + '/customergroupinfo.json',
				params : {
					operate:'add'
				},
				method : 'POST',
				form : editGroupBaseInfoForm.getForm().id,
				success : function() {
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
							 editGroupBaseInfoForm.form.findField('groupMemberType').setReadOnly(true);
							 editGroupBaseInfoForm.form.findField('custFrom').setReadOnly(true);
							 Ext.Msg.alert('提示', '操作成功');
								editGroupBaseInfoWindow.hide();
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
				}
			});
			
			
			
		}//function 完成
	}]
});



// 定义修改窗口
var editGroupBaseInfoWindow = new Ext.Window({
	layout : 'fit',
    autoScroll : true,
    draggable : true,
    closable : true,
    closeAction : 'hide',
    modal : true,
    width : 1000,
    height : 400,
    loadMask : true,
    border : false,
    items : [ {
        buttonAlign : "center",
        layout : 'fit',
        items : [editGroupBaseInfoPanel]
    }]
});
