/**
 * 营销活动反馈-客户经理页面js
 * hujun
 * 2014-07-03
 */

	imports([
	         '/contents/pages/com.yucheng.bcrm/com.yucheng.bcrm.js',
	         '/contents/pages/common/Com.yucheng.crm.common.OrgUserManage.js',
	         '/contents/pages/common/Com.yucheng.bcrm.common.OrgField.js',
	         '/contents/pages/common/Com.yucheng.crm.common.managerSearch.js',
    		 '/contents/pages/common/LovCombo.js',
   			 '/contents/pages/common/Com.yucheng.crm.common.ImpExpNew.js'//导出
	         ]);
	
	var createView=false;
	var editView=false;
	var detailView=false;
	
	var extraParams = {
	    impFlag : "0"
	};
	
	
	var url=basepath+'/mktCustManegerback.json';
	var lookupTypes=[
	                 	'IF_FLAG',
	                 	'STAGE_LEAVL',
	                 	'MACTI_STATUS',
	                 	'MGR_ACCEPT'
	                 ];
	
	var fields=[
	            {name:'ORG_NAME',text:'机构',hiddenName:'ORG_ID',searchField:true,xtype:'orgchoose'},
	            {name:'CUST_ID',text:'客户编号',searchField:true},
	            {name:'CUST_NAME',text:'客户名称',searchField:true},
	            {name:'CORE_NO',text:'核心客户号',hidden:true},
	            {name:'MGR_NO',text:'客户经理编号',hidden:true},
	            {name:'MGR_NAME',text:'客户经理',searchField:true,xtype:'userchoose'},
	            {name:'TEL_NO',text:'联系电话'},
	            {name:'MGR_STATUS',text:'是否同意',searchField:true,translateType:'MGR_ACCEPT'}
	            ];
	var progressWin = new Ext.Window({     
	    width : 300,
	    hideMode : 'offsets',
	    closable : true,
	    modal : true,
	    autoHeight : true,
	    closeAction:'hide'
	});
	
	
   var tbar=[
	   {
			text:'模板下载',
			handler:function(){
				var winPara = 'height=100, width=200, top=300, left=500, toolbar=no,menubar=no,'
								+ ' scrollbars=no, resizable=no,location=no, status=no';
				var fileName = 'impMenuTemp.xlsx';
				var uploadUrl = basepath + '/TempDownload?filename=' + fileName;
				window.open(uploadUrl, '', winPara);
			}
	   },{
			text:'名单下发',
			hidden:JsContext.checkGrant('mktCustManegerback_import'),
			handler:function(){
				importForm.tradecode = 'importMenusManegerback';
				importWindow.show();
			}
	   },{
			text:'连接callreport',
			handler:function(){
				if(getSelectedData() == false){
					Ext.Msg.alert('提示','请选择数据！');
					return false;
				}
				var custId = getSelectedData().data.CUST_ID;
				var custName = getSelectedData().data.CUST_NAME;
				var coreNo = getSelectedData().data.CORE_NO;
				var hasMgr = false;
				/**
				 * 控制同意和拒绝按钮的显示：
				 * 1.有核心客户号，同意或拒绝后，同意和拒绝按钮隐藏
				 * 2.没有核心客户号，同意或拒绝后，同意按钮隐藏，拒绝按钮一直显示
				 */
				//检查是否已经分配过客户经理OCRM_F_CI_BELONG_CUSTMGR
				Ext.Ajax.request({
					url:basepath+'/mktCustManegerback!isHasMgr.json',
					method : 'GET',
					params : {
						'custId' : custId,
						'coreNo' : coreNo
					},
					success:function(result){
						if(result.responseText){
							var resJson = Ext.decode(result.responseText);
							if(resJson.success){
								var acceptBtn = resJson.acceptBtn;
								var rejectBtn = resJson.rejectBtn;
								parent.Wlj.ViewMgr.openViewWindow(6,custId,custName,2,
									{'viewResId':__resId,'acceptBtn':acceptBtn,'rejectBtn':rejectBtn});
							}else{
								Ext.Msg.alert('提示',resJson.msg);
							}
						}
					}
				});
			}
       },{
       		text : '名单回收',
       		handler : function(){
				importState = false;
				progressWin.removeAll();
				progressWin.setTitle("名单回收");
				progressWin.add(new Ext.Panel({
					title : '',
					width : 300,
					layout : 'fit',
					autoHeight : true,
					bodyStyle : 'text-align:center',
					html : '<img src="' + basepath + '/contents/img/UltraMix55.gif" />',
					buttonAlign	: 'center',
					buttons	: [{
						text	: '导出',
						url : basepath+'/mktCustManegerback.json',
						handler	: function(){
					        var fieldArray = [
					        	{text: '核心客户号',name: 'CORE_NO'},
					        	{text: '客户编号',name: 'CUST_ID'},
								{text: '客户名称',name: 'CUST_NAME'},
								{text: '手机号',name: 'TEL_NO'}
							];
					        var fieldMap = {};
					        var translateMap = {};
					        Ext.each(fieldArray,function(item){
					            var header = "";
					            var mapping = "";
					            if(item.name!=undefined&&item.name!=""&&(item.hidden==undefined||!item.hidden)){
					                if(item.text!=undefined&&item.text!=""&&item.text!="NO"
					                    &&(item.gridField==undefined||item.gridField)){
					                    header = item.text;
					                    mapping = item.name;
					                    if(item.translateType && item.translateType != ""){
					                    	mapping = item.name + '_ORA';
					                    	translateMap[item.name] = item.translateType;
					                    }
					                    fieldMap[mapping]=header;
					                    if(mapping!=undefined&&mapping!=""){
					                        fieldMap[mapping]=header;
					                    }else{
					                    	fieldMap[fieldArray[i].name]=header;
					                    }
					                }
					            }
					        });
					        var tmpFormPanel;
					        var conditionString;
					        var requestParams = {};
					        
					        var fieldMapString = Ext.encode(fieldMap);
					        var expPar = {};
					        expPar.fieldMap = fieldMapString;
					        expPar.translateMap = Ext.encode(translateMap);
					//        conditionString = Ext.encode(_app.searchDomain.items.items[0].getForm().getFieldValues());
					        if(conditionString!=undefined){
					        	expPar.condition = conditionString;
					        	expPar.menuId = __resId;
					        }
					        Ext.apply(expPar,this.exParams);
					        
					        if(this.url=='#'){
					            /**
					             * For debug.
					             */
					            Ext.MessageBox.alert('Debugging！','You forgot to define the url for the ExpButton!');
					        }
					        var refreshHandler = 0;
					        var backgroundExport = this.backgroundExport;
					        var expUrl = this.url.replace('.json','!exportMenuRecovery.json');
					        
					        //进度title
					        var progressBarTitle = '正在导出数据...';
					        //当前导出进度信息
					        var getMsg = function(expRecNum, total) {
					        	return '当前导出'+expRecNum+'条；<br>共导出'+total+'条。';
					        };      
					        Ext.Ajax.request({
					            url:expUrl,
					            method:'POST',
					            params:expPar,
					            success:function(a,b){
					        		if (backgroundExport){
					        			Ext.MessageBox.alert('提示！','下载启动成功，请在下载中心下导出文件!');
					        		} else {
					        			showProgressBar(1,0,'','正在导出，请稍后...','导出文件');
					                    var refreshUrl = basepath + '/mktCustManegerback!refresh.json';
					                    var freshFish = function(){
					                        Ext.Ajax.request({
					                            url:refreshUrl,
					                            method:'GET',
					                            success:function(a){
					                                if(a.status == '200' || a.status=='201'){
					                                    var res = Ext.util.JSON.decode(a.responseText);
					                                    if(res.json.filename){
					                                    	showSuccessWin(res.json.expRecNum,basepath+'/FileDownload?filename='+res.json.filename);
					                                        if(refreshHandler != 0){
					                                            window.clearInterval(refreshHandler);
					                                        }
					                                    } else {
					                                    	var res = Ext.util.JSON.decode(a.responseText);
					                                    	//Ext.MessageBox.hide();
					                                    	if ((typeof res.json.expRecNum) != 'undefined' && (typeof res.json.total) != 'undefined') {
					                                    		showProgressBar(res.json.total,res.json.expRecNum,'',getMsg(res.json.expRecNum,res.json.total),progressBarTitle);
					                                    	} else {
					                                    		 progressBar.hide();
					                                             Ext.MessageBox.alert('提示！','下载进度信息获取失败!');
					                                    	}
					                                    }
					                                } 
					                            }
					                        });
					                    };
					                    refreshHandler = window.setInterval(freshFish, 1000);
					        		}
					            },
					            failure:function(a,b){
					            	if (!backgroundExport){progressBar.hide();}
					                Ext.MessageBox.alert('失败！','请等待当前下载任务完成!');
					            }
					        });
						}
					}]
				}));
				
				progressWin.doLayout();
				progressWin.show();
			
       		}
       }]
	   
      var importForm = new Ext.FormPanel({
		height : 200,
		width : '100%',
		title : '文件导入',
		fileUpload : true,
		dataName : 'file',
		frame : true,
		tradecode : "",
		/** 是否显示导入状态 */
		importStateInfo : '',
		importStateMsg : function(state) {
			var titleArray = [ 'excel数据转化为SQL脚本', '执行SQL脚本...','正在将临时表数据导入到业务表中...', '导入成功！' ];
			this.importStateInfo = "当前状态为：[<font color='red'>"+ titleArray[state] + "</font>];<br>";
		},
		/** 是否显示 当前excel数据转化为SQL脚本成功记录数 */
		curRecordNumInfo : '',
		curRecordNumMsg : function(o) {
			this.curRecordNumInfo = "当前excel数据转化为SQL脚本成功记录数[<font color='red'>"+ o + "</font>];<br>";
		},
		/** 是否显示 当前sheet页签记录数 */
		curSheetRecordNumInfo : '',
		curSheetRecordNumMsg : function(o) {
			this.curSheetRecordNumInfo = "当前sheet页签记录数：[<font color='red'>"+ o + "</font>];<br>";
		},
		/** 是否显示 当前sheet页签号 */
		sheetNumInfo : '',
		sheetNumMsg : function(o) {
			this.sheetNumInfo = "当前sheet页签号为：[<font color='red'>" + o+ "</font>];<br>";
		},
		/** 是否显示 sheet页签总数 */
		totalSheetNumInfo : '',
		totalSheetNumMsg : function(o) {
			this.totalSheetNumInfo = "sheet页签总数：[<font color='red'>" + o+ "</font>];<br>";
		},
		/** 是否显示 已导入完成sheet数 */
		finishSheetNumInfo : '',
		finishSheetNumMsg : function(o) {
			this.finishSheetNumInfo = "已导入完成sheet数[<font color='red'>" + o+ "</font>];<br>";
		},
		/** 是否显示 已导入完成记录数 */
		finishRecordNumInfo : '',
		finishRecordNumMsg : function(o) {
			this.finishRecordNumInfo = "已导入完成记录数[<font color='red'>" + o+ "</font>];<br>";
		},
		/** 当前excel数据转化为SQL脚本成功记录数 */
		curRecordNum : 0,
		/** 导入总数 */
		totalNum : 1,
		/** 进度条信息 */
		progressBarText : '',
		/** 进度条Msg */
		progressBartitle : '',
		proxyStore : undefined,
		items : [],
		/** 进度条 */
		progressBar : null,
		/** *import成功句柄 */
		importSuccessHandler : function(json) {
			if (json != null) {
				if (typeof (json.curRecordNum) != 'undefined'&& this.curRecordNumMsg) {
					this.curRecordNumMsg(json.curRecordNum);
					this.curRecordNum = json.curRecordNum;
				}
				if (typeof (json.importState) != 'undefined'&& this.importStateMsg) {
					this.importStateMsg(json.importState);
				}
				if (typeof (json.curSheetRecordNum) != 'undefined'&& this.curSheetRecordNumMsg) {
					this.curSheetRecordNumMsg(json.curSheetRecordNum);
				}
				if (typeof (json.sheetNum) != 'undefined'&& this.sheetNumMsg) {
					this.sheetNumMsg(json.sheetNum);
				}
				if (typeof (json.totalSheetNum) != 'undefined'&& this.totalSheetNumMsg) {
					this.totalSheetNumMsg(json.totalSheetNum);
				}
				if (typeof (json.finishSheetNum) != 'undefined'&& this.finishSheetNumMsg) {
					this.finishSheetNumMsg(json.finishSheetNum);
				}
				if (typeof (json.finishRecordNum) != 'undefined'&& this.finishRecordNumMsg) {
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
			/** 进度条Msg信息配置：各信息项目显示内容由各自方法配置 */
			this.progressBartitle += this.curRecordNumMsg ? this.curRecordNumInfo: '';
			this.progressBartitle += this.importStateMsg ? this.importStateInfo: '';
			this.progressBartitle += this.curSheetRecordNumMsg ? this.curSheetRecordNumInfo: '';
			this.progressBartitle += this.sheetNumMsg ? this.sheetNumInfo: '';
			this.progressBartitle += this.totalSheetNumMsg ? this.totalSheetNumInfo: '';
			this.progressBartitle += this.finishSheetNumMsg ? this.finishSheetNumInfo: '';
			this.progressBartitle += this.finishRecordNumMsg ? this.finishRecordNumInfo: '';
			showProgressBar(this.totalNum, this.curRecordNum,this.progressBarText, this.progressBartitle,"上传成功，正在导入数据，请稍候");
		},
		buttons : [ {
			text : '导入文件',
			handler : function() {
				var tradecode = this.ownerCt.ownerCt.tradecode;
				var proxyStorePS = this.ownerCt.ownerCt.proxyStore;
				var proxyHttpPH = this.ownerCt.ownerCt.proxyHttp;
				if (tradecode == undefined || tradecode == '') {
					Ext.MessageBox.alert('Debugging！','You forgot to define the tradecode for the import form!');
					return false;
				}
				var impRefreshHandler = 0;
				if (this.ownerCt.ownerCt.getForm().isValid()) {
					this.ownerCt.ownerCt.ownerCt.hide();
					var fileNamesFull = this.ownerCt.ownerCt.items.items[0].getValue();
					var extPoit = fileNamesFull.substring(fileNamesFull.indexOf('.'));
					if (extPoit == '.xls' || extPoit == '.XLS'|| extPoit == '.xlsx' || extPoit == '.XLSX') {
					} else {
						Ext.MessageBox.alert("文件错误", "导入文件不是XLS或XLSX文件。");
						return false;
					}
					showProgressBar(1, 0, '', '', '正在上传文件...');

					this.ownerCt.ownerCt.getForm().submit({
						url : basepath+ '/FileUpload?isImport=true',
						success : function(form, o) {
							_tempImpFileName = Ext.util.JSON.decode(o.response.responseText).realFileName;
							var condi = {};
							condi['filename'] = _tempImpFileName;
							condi['tradecode'] = tradecode;
							Ext.Ajax.request({
								url : basepath+ "/ImportAction.json",
								method : 'GET',
								params : {
									"condition" : Ext.encode(condi)
								},
								success : function() {
									importForm.importSuccessHandler(null);
									var importFresh = function() {
										Ext.Ajax.request({
											url : basepath+ "/ImportAction!refresh.json",
											method : 'GET',
											success : function(a) {
												if (a.status == '200'|| a.status == '201') {
													var res = Ext.util.JSON.decode(a.responseText);
													if (res.json.result != undefined&& res.json.result == '200') {
														window.clearInterval(impRefreshHandler);
														if (res.json.BACK_IMPORT_ERROR&& res.json.BACK_IMPORT_ERROR == 'FILE_ERROR') {
															Ext.Msg.alert("提示","导出文件格式有误，请下载导入模版。");
															return;
														}
														if (proxyStorePS != undefined) {
															var condiFormP = {};
															condiFormP['pkHaed'] = res.json.PK_HEAD;
															pkHead = res.json.PK_HEAD;
															proxyStorePS.load({
																params : {
																	pkHead : pkHead
																}
															});
														} else {
															importForm.importSuccessHandler(res.json);
															showSuccessWinImp(res.json.curRecordNum);// 导入数据条数
														}
													} else if (res.json.result != undefined&& res.json.result == '900') {
														window.clearInterval(impRefreshHandler);
														if(res.json.BACK_RUN_ERROR==true){
															Ext.Msg.alert("导入失败","失败原因：\n"+ res.json.BACK_IMPORT_ERROR);
														}else{
															showFailuerWinImp(res.json.PK_HEAD,res.json.BACK_IMPORT_ERROR);
														}
													} else if (res.json.result != undefined&& res.json.result == '999') {
														importForm.importSuccessHandler(res.json);
													}
												}
											}
										});
									};
									impRefreshHandler = window.setInterval(importFresh,1000);
								},
								failure : function() {
								}
							});
						},
						failure : function(form, o) {
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
		} ]
	});
	
	/**
	 * 导入成功
	 * @param {} curRecordNum
	 */
	function showSuccessWinImp(curRecordNum) {
		importState = true;
		progressWin.hide();
		Ext.Msg.alert("提示","成功导入[" + curRecordNum + "]条数据");
		_app.searchDomain.searchHandler();
	}
	
	/**
	 * 导入失败
	 * @param {} PK_HEAD
	 * @param {} msg
	 */
	function showFailuerWinImp(PK_HEAD,msg) {
		importState = false;
		progressWin.removeAll();
		progressWin.setTitle(msg);
		progressWin.add(new Ext.Panel({
			title : '',
			width : 300,
			layout : 'fit',
			autoHeight : true,
			bodyStyle : 'text-align:center',
			html : '<img src="' + basepath + '/contents/img/UltraMix55.gif" />',
			buttonAlign	: 'center',
			buttons	: [{
				text	: '导出',
				url : basepath+'/mktCustManegerback.json',
				handler	: function(){
			        var fieldArray = [
			        	{text: '核心客户号',name: 'CORE_NO'},
						{text: '客户名称',name: 'CUST_NAME'},
						{text: '手机号',name: 'TEL_NO'},
						{text: '所属客户经理',name: 'MGR_NO'},
					    {text: '校验信息',name:'IMP_MSG'}
					];
			        var fieldMap = {};
			        var translateMap = {};
			        Ext.each(fieldArray,function(item){
			            var header = "";
			            var mapping = "";
			            if(item.name!=undefined&&item.name!=""&&(item.hidden==undefined||!item.hidden)){
			                if(item.text!=undefined&&item.text!=""&&item.text!="NO"
			                    &&(item.gridField==undefined||item.gridField)){
			                    header = item.text;
			                    mapping = item.name;
			                    if(item.translateType && item.translateType != ""){
			                    	mapping = item.name + '_ORA';
			                    	translateMap[item.name] = item.translateType;
			                    }
			                    fieldMap[mapping]=header;
			                    if(mapping!=undefined&&mapping!=""){
			                        fieldMap[mapping]=header;
			                    }else{
			                    	fieldMap[fieldArray[i].name]=header;
			                    }
			                }
			            }
			        });
			        var tmpFormPanel;
			        var conditionString;
			        var requestParams = {};
			        
			        var fieldMapString = Ext.encode(fieldMap);
			        var expPar = {};
			        expPar.fieldMap = fieldMapString;
			        expPar.translateMap = Ext.encode(translateMap);
			//        conditionString = Ext.encode(_app.searchDomain.items.items[0].getForm().getFieldValues());
			        if(conditionString!=undefined){
			        	expPar.condition = conditionString;
			        	expPar.menuId = __resId;
			        }
			        Ext.apply(expPar,this.exParams);
			        
			        if(this.url=='#'){
			            /**
			             * For debug.
			             */
			            Ext.MessageBox.alert('Debugging！','You forgot to define the url for the ExpButton!');
			        }
			        var refreshHandler = 0;
			        var backgroundExport = this.backgroundExport;
			        var expUrl = this.url.replace('.json','!export.json');
			        
			        //进度title
			        var progressBarTitle = '正在导出数据...';
			        //当前导出进度信息
			        var getMsg = function(expRecNum, total) {
			        	return '当前导出'+expRecNum+'条；<br>共导出'+total+'条。';
			        };      
			        Ext.Ajax.request({
			            url:expUrl,
			            method:'POST',
			            params:expPar,
			            success:function(a,b){
			        		if (backgroundExport){
			        			Ext.MessageBox.alert('提示！','下载启动成功，请在下载中心下导出文件!');
			        		} else {
			        			showProgressBar(1,0,'','正在导出，请稍后...','导出文件');
			                    var refreshUrl = b.url.replace('export','refresh');
			                    var freshFish = function(){
			                        Ext.Ajax.request({
			                            url:refreshUrl,
			                            method:'GET',
			                            success:function(a){
			                                if(a.status == '200' || a.status=='201'){
			                                    var res = Ext.util.JSON.decode(a.responseText);
			                                    if(res.json.filename){
			                                    	showSuccessWin(res.json.expRecNum,basepath+'/FileDownload?filename='+res.json.filename);
			                                        if(refreshHandler != 0){
			                                            window.clearInterval(refreshHandler);
			                                        }
			                                    } else {
			                                    	var res = Ext.util.JSON.decode(a.responseText);
			                                    	//Ext.MessageBox.hide();
			                                    	if ((typeof res.json.expRecNum) != 'undefined' && (typeof res.json.total) != 'undefined') {
			                                    		showProgressBar(res.json.total,res.json.expRecNum,'',getMsg(res.json.expRecNum,res.json.total),progressBarTitle);
			                                    	} else {
			                                    		 progressBar.hide();
			                                             Ext.MessageBox.alert('提示！','下载进度信息获取失败!');
			                                    	}
			                                    }
			                                } 
			                            }
			                        });
			                    };
			                    refreshHandler = window.setInterval(freshFish, 1000);
			        		}
			            },
			            failure:function(a,b){
			            	if (!backgroundExport){progressBar.hide();}
			                Ext.MessageBox.alert('失败！','请等待当前下载任务完成!');
			            }
			        });
				}
			}]
		}));
		
		progressWin.doLayout();
		progressWin.show();
	}
   
   var importWindow = new Ext.Window({
		width : 700,
		hideMode : 'offsets',
		modal : true,
		height : 250,
		closeAction : 'hide',
		items : [ importForm ]
	});
	
   importWindow.on('show', function(upWindow) {
		if (Ext.getCmp('littleup')) {
			importForm.remove(Ext.getCmp('littleup'));
		}
		importForm.removeAll(true);
		importForm.add(new Ext.form.TextField({
			xtype : 'textfield',
			id : 'littleim',
			name : 'annexeName',
			inputType : 'file',
			fieldLabel : '文件名称',
			anchor : '90%'
		}));
		importForm.doLayout();
	});
	
	//页面初始化
	var afterinit = function(app){
		app.setSearchParams = app.setSearchParams.createInterceptor(function(params, forceLoad, add, transType){
	       Ext.apply(params,extraParams)
	    },app);
	};
	
