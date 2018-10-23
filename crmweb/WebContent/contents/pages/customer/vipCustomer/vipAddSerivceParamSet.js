/**
 * 贵宾客户增值服务管理-增值服务参数设置
 * hujun
 * 2014-06-16
 * 
 */
	imports([
		         '/contents/pages/com.yucheng.bcrm/com.yucheng.bcrm.js',
		         '/contents/pages/common/Com.yucheng.bcrm.common.OrgField.js',
		         '/contents/pages/common/Com.yucheng.crm.common.AlianceProg.js',
		         '/contents/pages/common/Com.yucheng.crm.common.AddService.js',
		         '/contents/pages/common/Com.yucheng.crm.common.OrgUserManage.js',
		         '/contents/pages/common/Com.yucheng.crm.common.ImpExpNew.js'
		        	         
		         ]);
	Ext.QuickTips.init();
	var createView=true;
	var editView=true;
	var detailView=true;
	var lookupTypes = [{
			TYPE : 'ADD_SERVICE_NAME',
			url : '/VipAddValueNameAction.json',
			key : 'key',
			value : 'value',
			root : 'JSON'
		},'VIP_CARD_LEVEL' ];
	
	var url= basepath + '/vipAddServiceParamtSetQueryAction.json';
	var comitUrl=basepath + '/vipAddServiceParamtSetAction.json';
	
	var condition = {searchType:'SUBTREE'};
	var treeLoaders = [{
		key : 'DATASETMANAGERLOADER',
		url : basepath + '/commsearch.json?condition='+Ext.encode(condition),
		parentAttr : 'SUPERUNITID',
		locateAttr : 'UNITID',
		jsonRoot:'json.data',
		rootValue : JsContext._orgId,
		textField : 'UNITNAME',
		idProperties : 'UNITID'
	}];
	var treeCfgs = [{
		key : 'DATASETMANAGERTREE',
		loaderKey : 'DATASETMANAGERLOADER',
		autoScroll:true,
		rootCfg : {
			expanded:true,
			id:JsContext._orgId,
			text:JsContext._unitname,
			UNITID: JsContext._orgId,
			UNITNAME: JsContext._unitname,
			autoScroll:true,
			children:[]
		}
	}];
	var fields=[
	            {name:'ID',text:'编号',hidden:true},
	            {name:'VIP_CARD_LEVEL',text:'贵宾卡级别',allowBlank : false,
	             searchField:true,resutlFloat:'right',translateType:'VIP_CARD_LEVEL'},
	            {name:'ADD_SERVICE_NAME',gridField:true,
				   xtype:'addServiceChoose',hiddenName:'ID',allowBlank : false,searchField:true,text:'增值服务名称',callback:function(a){
				    	   var ss=a.addFlag;
				    	   var s1=a.range;
				    	   var s2=a.alianceName;
				    	   getCreateView().getFieldsByName('ADD_SERVICE_IDENTIFY').value=a.addFlag;
				    	   getCreateView().getFieldsByName('RANGE_APPLY').value=a.range;
				    	   getCreateView().getFieldsByName('PROVIDER_NAME').value=a.alianceName;
				       }},
	            {name:'ADD_SERVICE_IDENTIFY',text:'增值服务标识'},
	            {name:'PROVIDER_NAME',text:'提供商',xtype:'textfield'},
	            {name:'RANGE_APPLY',text:'服务范围',gridField:false,allowBlank:false,xtype: 'wcombotree',
		              innerTree:'DATASETMANAGERTREE',allowBlank:false,showField:'text',hideField:'UNITID',editable:false},
		        {name:'CREATE_USER',text:'创建人',gridField:false},
	            {name:'CREATE_USER_NAME',text:'创建人',hiddenName:'CREATE_USER',searchField:true,xtype:'userchoose'},
	            {name:'CREATE_DATE',text:'创建日期',searchField: true, xtype:'datefield', format:'Y-m-d'},
	            {name:'CREATE_ORG',text:'机构',gridField:false},
	            {name:'CREATE_ORG_NAME',text:'创建机构',xtype: 'wcombotree',searchField:true,
	            	innerTree:'DATASETMANAGERTREE',allowBlank:false,showField:'text',editable:false},
	            {name:'REMARK',text:'备注',
	             resutlWidth:350,xtype:'textarea'},
	            {name:'SERVICE_CONTENT',gridField:false,text:'增值服务内容',
		             resutlWidth:350,xtype:'textarea'}
	            ];
		
	var createFormViewer=[{
		columnCount:2,
		fields:['VIP_CARD_LEVEL','ADD_SERVICE_NAME','ADD_SERVICE_IDENTIFY','PROVIDER_NAME',
		        'RANGE_APPLY','CREATE_USER','CREATE_DATE','CREATE_ORG'],
		fn:function(VIP_CARD_LEVEL,ADD_SERVICE_NAME,ADD_SERVICE_IDENTIFY,PROVIDER_NAME,
				RANGE_APPLY,CREATE_USER,CREATE_DATE,CREATE_ORG){
			//ADD_SERVICE_IDENTIFY.hidden=true;
			CREATE_USER.value =  __userId;
			CREATE_DATE.value =  new Date().format('Y-m-d');
			CREATE_ORG.value = __units;
			CREATE_USER.hidden = true;
			CREATE_DATE.hidden = true;
			CREATE_ORG.hidden = true;
			return [VIP_CARD_LEVEL,ADD_SERVICE_NAME,ADD_SERVICE_IDENTIFY,
			        PROVIDER_NAME,RANGE_APPLY,CREATE_USER,CREATE_DATE,CREATE_ORG];
		}						
		},{columnCount:1,
			fields:['SERVICE_CONTENT','REMARK'],
			fn:function(SERVICE_CONTENT,REMARK){
				return [SERVICE_CONTENT,REMARK];
			}
	}];
		
	var formViewers=[{
		columnCount:2,
		fields:['ID','VIP_CARD_LEVEL','ADD_SERVICE_NAME','ADD_SERVICE_IDENTIFY','PROVIDER_NAME',
		        'RANGE_APPLY','CREATE_USER','CREATE_DATE','CREATE_ORG'],
		fn:function(ID,VIP_CARD_LEVEL,ADD_SERVICE_NAME,ADD_SERVICE_IDENTIFY,PROVIDER_NAME,
				RANGE_APPLY,CREATE_USER,CREATE_DATE,CREATE_ORG){
			ADD_SERVICE_IDENTIFY.hidden=true;
			ID.hidden=true;
			CREATE_USER.hidden=true;
			CREATE_DATE.hidden=true;
			CREATE_ORG.hidden=true;
			return [ID,VIP_CARD_LEVEL,ADD_SERVICE_NAME,ADD_SERVICE_IDENTIFY,PROVIDER_NAME,
					RANGE_APPLY,CREATE_USER,CREATE_DATE,CREATE_ORG];
		}							
		},{columnCount:1,
			fields:['SERVICE_CONTENT','REMARK'],
			fn:function(SERVICE_CONTENT,REMARK){
				return [SERVICE_CONTENT,REMARK];
			}
	}];
	
	//添加详情页面只读限制by:sujm 20140821
	var detailFormViewer=[{
			columnCount:2,
			fields:['VIP_CARD_LEVEL','ADD_SERVICE_NAME','ADD_SERVICE_IDENTIFY','PROVIDER_NAME',
			        'RANGE_APPLY'],
			fn:function(VIP_CARD_LEVEL,ADD_SERVICE_NAME,ADD_SERVICE_IDENTIFY,PROVIDER_NAME,
					RANGE_APPLY){
		VIP_CARD_LEVEL.cls = "x-readOnly";
		VIP_CARD_LEVEL.readOnly = true;
		ADD_SERVICE_NAME.cls = "x-readOnly";
		ADD_SERVICE_NAME.readOnly = true;
		ADD_SERVICE_IDENTIFY.cls = "x-readOnly";
		PROVIDER_NAME.cls = "x-readOnly";
		RANGE_APPLY.cls = "x-readOnly";
		RANGE_APPLY.readOnly = true;
		VIP_CARD_LEVEL.cls = "x-readOnly";
		VIP_CARD_LEVEL.readOnly = true;
				ADD_SERVICE_IDENTIFY.hidden=true;
				return [VIP_CARD_LEVEL,ADD_SERVICE_NAME,ADD_SERVICE_IDENTIFY,
				        PROVIDER_NAME,RANGE_APPLY];
			}							
			},{columnCount:1,
				fields:['SERVICE_CONTENT','REMARK'],
				fn:function(SERVICE_CONTENT,REMARK){
				SERVICE_CONTENT.cls = "x-readOnly";
				REMARK.cls = "x-readOnly";
				SERVICE_CONTENT.readOnly = true;
				REMARK.readOnly = true;
					return [SERVICE_CONTENT,REMARK];
				}
	}];
	var condition = {searchType:'SUBTREE'};
	var treeLoaders = [{
		key : 'DATASETMANAGERLOADER',
		url : basepath + '/commsearch.json?condition='+Ext.encode(condition),
		parentAttr : 'SUPERUNITID',
		locateAttr : 'UNITID',
		jsonRoot:'json.data',
		rootValue : JsContext._orgId,
		textField : 'UNITNAME',
		idProperties : 'UNITID'
	}];
	var treeCfgs = [{
		key : 'DATASETMANAGERTREE',
		loaderKey : 'DATASETMANAGERLOADER',
		autoScroll:true,
		rootCfg : {
			expanded:true,
			id:JsContext._orgId,
			text:JsContext._unitname,
			UNITID: JsContext._orgId,
			UNITNAME: JsContext._unitname,
			autoScroll:true,
			children:[]
		}
	},{
		key : 'DATASETMANAGERTREE1',
		loaderKey : 'DATASETMANAGERLOADER',
		autoScroll:true,
		rootCfg : {
			expanded:true,
			id:JsContext._orgId,
			text:JsContext._unitname,
			UNITID: JsContext._orgId,
			UNITNAME: JsContext._unitname,
			autoScroll:true,
			children:[]
		}
	}];
	var tbar=[/*{
		text:'导入',
		handler:function(){
		importWindow.show();
	}
	},*/{
	text:'删除',
	handler:function(){
		var records =getAllSelects();// 得到被选择的行的数组
		var selectLength = getAllSelects().length;// 得到行数组的长度
		if (selectLength < 1) {
			Ext.Msg.alert('提示信息', '请至少选择一条记录！');
		} else {
			var serviceId;
			var idStr = '';
			for ( var i = 0; i < selectLength; i++) {
				selectRe = records[i];
				serviceId = selectRe.data.ID;// 获得选中记录的id
				idStr += serviceId;
				if (i != selectLength - 1)
					idStr += ',';
			};
			if (idStr != '') {
				Ext.MessageBox.confirm('系统提示信息','确认进行结束操作吗？',
				function(buttonobj) {
					if (buttonobj == 'yes'){
					Ext.Ajax.request({
						url : basepath+ '/vipAddServiceParamtSetAction!batchDestroy.json',
						params : {
							idStr : idStr
						},
						waitMsg : '正在保存数据,请等待...', // 显示读盘的动画效果，执行完成后效果消失
						method : 'GET',
						scope : this,
						success : function() {
							Ext.Msg.alert('提示信息', '操作成功！');
							reloadCurrentData();
						}
					});}
			 }, this);
			}
		}
	
	}
	},
		new Com.yucheng.crm.common.NewExpButton({
        formPanel : 'searchCondition',
        url : basepath+'/vipAddServiceParamtSetQueryAction.json'
    })];
	
	/***********view 滑入前控制************/
	
	var beforeviewshow=function(view){
	/*修改查询面板滑入时，做相应判断*/
	if(view.baseType=='editView'||view.baseType == 'detailView'){
			if(getSelectedData() == false || getAllSelects().length > 1){
				Ext.Msg.alert('提示','请选择一条数据');
				return false;
			}	
	}
	};
	var beforecommit = function(){
		lockGrid();//锁定结果列表
	};		
	
	/***************导入窗口定义模块*********************/
	var _tempImpFileName = "";
	var pkHead = "";
	/**
	 * 导入表单对象，此对象为全局对象，页面直接调用。
	 */
	var importForm = new Ext.FormPanel({ 
	    height : 200,
	    width : '100%',
	    title:'文件导入',
	    fileUpload : true, 
	    dataName:'file',
	    frame:true,
	    tradecode:"",
	    
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
	    items: [],
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
	                    this.ownerCt.ownerCt.ownerCt.hide();
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
	                                                        if(proxyStorePS!=undefined){
	                                                            var condiFormP = {};
	                                                            condiFormP['pkHaed'] =res.json.PK_HEAD;
	                                                            pkHead = res.json.PK_HEAD;
	                                                            proxyStorePS.load({
	                                                                params:{
	                                                                    pkHead: pkHead
	                                                                }
	                                                            });
	                                                        }else {
	                                                        	importForm.importSuccessHandler(res.json); 
	                                                        	showSuccessWin(res.json.curRecordNum);
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
	        }]
	});
	/**
	 * 导入弹出窗口，此对象为全局对象，由各页面直接调用。
	 */

	var importWindow = new Ext.Window({     
	    width : 700,
	    hideMode : 'offsets',
	    modal : true,
	    height : 250,
	    closeAction:'hide',
	    items : [importForm]
	});
	importWindow.on('show',function(upWindow){
//		if(Ext.getCmp('littleup')){
//			importForm.remove(Ext.getCmp('littleup'));
//		}
		importForm.removeAll(true);
		importForm.add(new Ext.form.TextField({
	        xtype : 'textfield',
	        id:'littleim',
	        name:'annexeName',
	        inputType:'file',
	        fieldLabel : '文件名称',
	        anchor : '90%'
	    }));
		importForm.doLayout();
	});
	var progressBar = {};
	var importState = false;
	var progressWin = new Ext.Window({     
	    width : 300,
	    hideMode : 'offsets',
	    closable : true,
	    modal : true,
	    autoHeight : true,
	    closeAction:'hide',
	    items : [],
	    listeners :{
			'beforehide': function(){
				return importState;
			}
		}
	});
	function showProgressBar(count,curnum,bartext,msg,title) {
		importState = false;
		progressBar = new Ext.ProgressBar({width : 285 });
		progressBar.wait({
	        interval: 200,          	//每次更新的间隔周期
	        duration: 5000,             //进度条运作时候的长度，单位是毫秒
	        increment: 5,               //进度条每次更新的幅度大小，默示走完一轮要几次（默认为10）。
	        fn: function () {           //当进度条完成主动更新后履行的回调函数。该函数没有参数。
				progressBar.reset();
	        }
	    });
		progressWin.removeAll();
		progressWin.setTitle(title);
		if (msg.length == 0) {
			msg = '正在导入...';
		}
		var importContext = new Ext.Panel({
									title: '',
									frame : true,
									region :'center',
									height : 100,
									width : '100%',
									autoScroll:true,
									html : '<span>'+ msg +'</span>'
								});
		progressWin.add(importContext);
		progressWin.add(progressBar);
		progressWin.doLayout();
		progressWin.show();
		
	}
	function showSuccessWin(curRecordNum) {
		importState = true;
		progressWin.removeAll();
		progressWin.setTitle("成功导入记录数为["+curRecordNum+"]");
		progressWin.add(new Ext.Panel({
			title:'',
			width:300,
			layout : 'fit',
			autoHeight : true,
			bodyStyle:'text-align:center',
			html: '<img src="'+basepath+'/contents/img/UltraMix55.gif" />'
		}));
		progressWin.doLayout();
		progressWin.show();
	}

			
			
		
		