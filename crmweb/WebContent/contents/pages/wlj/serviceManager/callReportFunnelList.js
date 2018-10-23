
    imports([
             	'/contents/pages/common/Com.yucheng.crm.common.ImpExpNew.js',
                '/FusionCharts/FusionCharts.js',
                '/contents/pages/com.yucheng.bcrm/com.yucheng.bcrm.js',
                '/contents/pages/common/Com.yucheng.crm.common.ImpExpNew.js',
//		        '/contents/pages/common/Com.yucheng.bcrm.common.ProductManage.js',//产品放大镜
		        '/contents/pages/common/Com.yucheng.bcrm.common.OrgFieldx.js',//组织机构放大镜
		    	'/contents/pages/common/Com.yucheng.crm.common.OrgUserManage.js'	//客户经理选择放大镜
//              '/contents/pages/fusionChart/tool/FusionCharts.js'
		        ]);
    
    var url = basepath + '/callReportFunnelQueryAction.json';
    var formCfgs = false;
    var needCondition = true;
    var needGrid = true;
    var createView = true;
    var editView = true;
    var detailView = true;
	var lookupTypes=[
	                 'CALLREPORT_SAVES_STAGE',
	                 'CALLREPORT_FAIL_REASON',
	                 'CALLREPORT_PRODUCT_NAME'
	                 ];
	// 机构树加载条件
	var condition = {
		searchType : 'SUBTREE' // 查询子机构
	};

	//加载机构树
	var treeLoaders = [ {
		key : 'ORGTREELOADER',
		url : basepath + '/commsearch.json?condition=' + Ext.encode(condition),
		parentAttr : 'SUPERUNITID',
		locateAttr : 'UNITID',
		jsonRoot : 'json.data',
		rootValue : JsContext._orgId,
		textField : 'UNITNAME',
		idProperties : 'UNITID'
	} ,{
		key : 'BLINETREELOADER',
		url : basepath + '/businessLineTree.json',
		parentAttr : 'PARENT_ID',
		locateAttr : 'BL_ID',
		jsonRoot : 'json.data',
		rootValue : '0',
		textField : 'BL_NAME',
		idProperties : 'BL_ID'
	}];


	//树配置
	var treeCfgs = [ {
		key : 'ORGTREE',
		loaderKey : 'ORGTREELOADER',
		autoScroll : true,
		rootCfg : {
			expanded : true,
			id : JsContext._orgId,
			text : JsContext._unitname,
			autoScroll : true,
			children : [],
			UNITID : JsContext._orgId,
			UNITNAME : JsContext._unitname
		}
	},{
		key : 'BLTREE',
		loaderKey : 'BLINETREELOADER',
		autoScroll : true,
		rootCfg : {
			expanded : true,
			id : '0',
			text : '归属业务条线',
			autoScroll : true,
			children : []
		}
	} ];
	var fields = [
				  {name:'ID',hidden:true},
				  {name:'OPPOR_START_DATE',text:'开始日期',searchField:true,gridField:false,dataType:'date',format : 'Y-m-d'},
				  {name:'OPPOR_END_DATE',text:'结束日期',searchField:true,gridField:false,dataType:'date',format : 'Y-m-d'},
				  {name:'ORG_NAME',text:'归属机构',xtype : 'wcombotree',innerTree:'ORGTREE',resutlWidth:80,searchField: true, showField:'text',hideField:'UNITID',editable:false,allowBlank:false,gridField:false},	   
			      {name: 'MGR_NAME',text:'所属客户经理',xtype:'userchoose',hiddenName:'MGR_ID',resutlWidth:150,singleSelect: false,searchField: true,gridField:false},
		          {name: 'PROD_NAME',hiddenName:'PRODUCT_ID',singleSelect:true,translateType:'CALLREPORT_PRODUCT_NAME',
		       	  text:"关联产品",searchField : true,prodState:(''),riskLevel:(''),gridField:false},
				  {name:'F_CODE',text:'商机阶段编码',hidden:true},
				  {name:'F_C_V',text:'商机阶段'},
				  {name:'COUNT_PERCENT',text:'达成概率'},
				  {name:'COUNT_NUMBER',text:'商机数量' ,viewFn:function(data){
					  return  "<h1 onclick='' style='color:red' >"+data+"</h1>";
				  }},
				  {name:'COUNT_AMOUNT',text:'预计金额',viewFn: money('0,000.00'),resutlFloat:'right'},
				  {name:'COUNT_WEIGHT',text:'权重金额',viewFn: money('0,000.00'),resutlFloat:'right'},
				  {name:'ZHL',text:'转化率(%)'},
				  {name:'SJ',text:'平均时间'} 
	              ];
	
	var tbar = [{
		text : '收起',
		handler : function(){
			collapseSearchPanel();
		}
	},{
		text : '展开',
		handler : function(){
			expandSearchPanel();
		}
	},{
		text:'展示销售漏斗',
//		hidden:JsContext.checkGrant('opporFunnel_show'),
		handler:function(){
			var store = getResultStore();
			// 查询漏斗图形
			displayFunnel(store);
		}
	},{
		text:'展示失败图',
//		hidden:JsContext.checkGrant('opporFunnel_show'),
		handler:function(){
			// 查询失败占比图
			displayFields();
		}
	}];
	
	// 查询漏斗图形
	function displayFunnel(store) {
		var swfUrl = basepath + "/FusionCharts/Funnel.swf";
		var chart = new FusionCharts(swfUrl, "ChartId", "100%", "100%", "0", "0");
		chart.setJSONData(praseData(store));
		chart.render("chartdiv");
	}

	//  查询失败占比图
	function displayFields() {
		Ext.Ajax.request({
			url : basepath + '/callReportFunnelQueryAction!assetXml.json',
			method : 'GET',
			success : function(response) {
				var data = Ext.util.JSON.decode(response.responseText).data;
				var xml = "";
				xml +=  "<chart   caption=\""+"失败原因"+"\" subCaption=\"(单位：个)\"      formatNumberScale=\"0\"> ";
				for(var i=0;i<data.length;i++){
					xml+="  <set label=\"" + data[i].F_VALUE + "\"  value=\""+ data[i].AMOUNT_VALUE + "\"/>";
				}
				xml+="</chart>";
				var myChart = new FusionCharts(basepath+"/FusionCharts/Column3D.swf", "ChartId", "100%", "100%", "0", "0");
				myChart.setDataXML(xml);
				myChart.render("chartdiv1");
			},
			failure : function(response) {
				alert("读取数据失败");
			}
		});
	}
	
	// 数据解析转换
	function praseData(store) {
		var arr=[];
		for(var i=0;i<store.data.items.length;i++){
			arr.push(store.data.items[i].data.F_C_V);
			arr.push(store.data.items[i].data.COUNT_NUMBER);
		}
		
		var caption = "";
		if (arr[1] == 0 && arr[3] == 0 && arr[5] == 0 && arr[7] == 0 && arr[9] == 0) {
			caption = "各阶段商机数量全为0，漏斗不能展示。";
		}
		var jsonData = {
			"chart" : {
				"manageresize" : "1",
				"caption" : caption,
				"subcaption" : "",
				"showpercentvalues" : "0",
				"decimals" : "2",
				"basefontsize" : "10",
				"issliced" : "1",
				"connectNullData" : "1"
			},
			"data" : [{
						"label" : arr[0],
						"value" : arr[1] == 0 ? "0.000001" : arr[1]
					}, {
						"label" : arr[2],
						"value" : arr[3] == 0 ? "0.000001" : arr[3]
					}, {
						"label" : arr[4],
						"value" : arr[5] == 0 ? "0.000001" : arr[5]
					}, {
						"label" : arr[6],
						"value" : arr[7] == 0 ? "0.000001" : arr[7]
					}, {
						"label" : arr[8],
						"value" : arr[9] == 0 ? "0.000001" : arr[9]
					}],
			"styles" : {
				"definition" : [{
							"type" : "font",
							"name" : "captionFont",
							"size" : "12"
						}],
				"application" : [{
							"toobject" : "CAPTION",
							"styles" : "captionFont"
						}]
			}
		};
		return jsonData;
	}
	
	// 销售漏斗图形
	var funnelPicPanel = new Ext.form.FormPanel({
				width : '27%',
				height : '100%',
				frame : true,
				autoScroll : true,
				region : 'west',
				split : true,
				text : '正在加载漏斗数据,请稍等...',
				html : '<div id="chartdiv" style="width:100%;height:100%"></div>'
			});
	// 失败原因
	var funnelPicPanel1 = new Ext.form.FormPanel({
				width : '27%',
				height : '100%',
				frame : true,
				autoScroll : true,
				region : 'west',
				split : true,
				text : '正在加载数据,请稍等...',
				html : '<div id="chartdiv1" style="width:100%;height:100%"></div>'
			});
	var edgeVies = {
			left : {
				width : 300,
				layout : 'fit',
				title:'销售漏斗图形',
				items : [funnelPicPanel]
			},
			right : {
				width : 300,
				layout : 'fit',
				title:'失败原因',
				items : [funnelPicPanel1]
			}
		};
	
	var param='';
	/**
	 * 自定义
	 * @param data
	 * @param cUrl
	 * @returns
	 */
	var customerView=[{
		title:'展示商机列表',
//		hideTitle:JsContext.checkGrant('opporList_show'),
		type:'grid',
		url:basepath + '/ocrmFSeCallreportBusi.json',
		frame:true,
		fields : {fields:[
		          {name:'CUST_NAME',text:'客户名称',allowBlank:false},
		  		  {name:'BUSI_NAME',text:'商机名称',allowBlank:false},
				  {name:'PRODUCT_ID',text:'产品编号',searchField:true,allowBlank:false,renderer:function(value){
	           		var val = translateLookupByKey("CALLREPORT_PRODUCT_NAME",value);
	        		return val?val:"";
	        		}},
//				  {name:'PRODUCT_NAME',text:'产品编码',searchField:true,allowBlank:false},
				  {name:'SALES_STAGE',text:'销售阶段',searchField:true,allowBlank:false,renderer:function(value){
           			var val = translateLookupByKey("CALLREPORT_SAVES_STAGE",value);
        			return val?val:"";
        			}},
				  {name:'AMOUNT',text:'金额',searchField:true,allowBlank:false},
				  {name:'FAIL_REASON',text:'失败原因',searchField:true,allowBlank:false,renderer:function(value){
	           			var val = translateLookupByKey("CALLREPORT_FAIL_REASON",value);
	        			return val?val:"";
	        			}},
				  {name:'LAST_UPDATE_TM',text:'商机开始时间',searchField:true,allowBlank:false},
				  {name:'ESTIMATED_TIME',text:'预计成交时间',searchField:true,allowBlank:false}
		        ]},
		gridButtons:[{
			text:'详情',
//			hidden:JsContext.checkGrant('opporList_detail'),
			fn:function(grid){
				   var selectLength = grid.getSelectionModel().getSelections().length;
				   var selectRe= grid.getSelectionModel().getSelections()[0];
				   if (selectLength < 1) {
						Ext.Msg.alert('提示', '请先选择一条记录！');
						return false;
					} else if (selectLength > 1) {
						Ext.Msg.alert('提示', '只能选择一条记录！');
						return false;
					}
				   getCustomerViewByTitle('商机详情').contentPanel.form.loadRecord(selectRe);
				   showCustomerViewByTitle('商机详情');
			}
		},{
			url:'#',
		    exParams : false,
			backgroundExport : false,
			text:'导出',
			handler:function(){
				var F_CODE=getSelectedData().data.F_CODE;
				var prodName = _app.searchDomain.items.items[0].getForm().getFieldValues().PROD_NAME;
				var orgName = _app.searchDomain.items.items[0].getForm().getFieldValues().ORG_NAME;
				var mgrName = _app.searchDomain.items.items[0].getForm().getFieldValues().MGR_NAME;
				var opporStartDt = Ext.util.Format.date(_app.searchDomain.items.items[0].getForm().getFieldValues().OPPOR_START_DATE,'Y-m-d');
				var opporEndDt = Ext.util.Format.date(_app.searchDomain.items.items[0].getForm().getFieldValues().OPPOR_END_DATE,'Y-m-d');
				var url=basepath + '/ocrmFSeCallreportBusi.json?opporStage='+F_CODE+'&prodName='+prodName+'&orgName='+orgName+'&mgrName='+mgrName+'&opporStartDt='+opporStartDt+'&opporEndDt='+opporEndDt;
				var fieldArray = this.ownerCt.ownerCt.store.fields.items;
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
			    conditionString = Ext.encode(_app.searchDomain.items.items[0].getForm().getFieldValues());
			    if(conditionString!=undefined){
			      	expPar.condition = conditionString;
			      	expPar.menuId = __resId;
			    }
			    Ext.apply(expPar,this.exParams);
				var refreshHandler = 0;
				var backgroundExport = this.backgroundExport;
			    var expUrl = url.replace('.json','!export.json');
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
		}
		
		]       
	},{
		title:'商机详情',
		hideTitle : true,
		type:'form',
		groups:[{
			fields:[
			      {name:'CUST_NAME',text:'客户名称',readOnly:true,cls:'x-readOnly'},
			      {name:'BUSI_NAME',text:'商机名称',readOnly:true,cls:'x-readOnly'},
				  {name:'PRODUCT_ID',text:'产品编号',translateType:'CALLREPORT_PRODUCT_NAME',readOnly:true,cls:'x-readOnly'},
				  {name:'PRODUCT_NAME',text:'产品编码',readOnly:true,cls:'x-readOnly'},
				  {name:'SALES_STAGE',text:'销售阶段',translateType:'CALLREPORT_SAVES_STAGE',readOnly:true,cls:'x-readOnly'},
				  {name:'AMOUNT',text:'金额',readOnly:true,cls:'x-readOnly'},
				  {name:'FAIL_REASON',text:'失败原因',translateType:'CALLREPORT_FAIL_REASON',readOnly:true,cls:'x-readOnly'},
				  {name:'LAST_UPDATE_TM',text:'商机开始时间',readOnly:true,cls:'x-readOnly'},
				  {name:'ESTIMATED_TIME',text:'预计成交时间',readOnly:true,cls:'x-readOnly'}
		 ],
		 fn:function(BUSI_NAME,PRODUCT_ID,PRODUCT_NAME,SALES_STAGE,AMOUNT,FAIL_REASON,LAST_UPDATE_TM,ESTIMATED_TIME){
			 return [BUSI_NAME,PRODUCT_ID,PRODUCT_NAME,SALES_STAGE,AMOUNT,FAIL_REASON,LAST_UPDATE_TM,ESTIMATED_TIME]
		 }}],
		formButtons:[{
				text : '返回',
				fn : function(grid){
					 showCustomerViewByTitle('展示商机列表');
				}
		}]
	}];
	 
var beforecommit = function(data,cUrl){
	
};

	
var beforeviewshow=function(view){
	if(view._defaultTitle=='展示商机列表'){
		if(!getSelectedData()){
			Ext.Msg.alert('提示','请选择一条数据');
			return false;
		};
		var F_CODE=getSelectedData().data.F_CODE;
		var prodName = _app.searchDomain.items.items[0].getForm().getFieldValues().PROD_NAME;
		var orgName = _app.searchDomain.items.items[0].getForm().getFieldValues().ORG_NAME;
		var mgrName = _app.searchDomain.items.items[0].getForm().getFieldValues().MGR_NAME;
		var opporStartDt = Ext.util.Format.date(_app.searchDomain.items.items[0].getForm().getFieldValues().OPPOR_START_DATE,'Y-m-d');
		var opporEndDt = Ext.util.Format.date(_app.searchDomain.items.items[0].getForm().getFieldValues().OPPOR_END_DATE,'Y-m-d');
		view.setParameters ({
			opporStage : F_CODE,
			prodName : prodName,
			orgName : orgName,
			mgrName : mgrName,
			opporStartDt : opporStartDt,
			opporEndDt : opporEndDt
		});
	}
	if(view._defaultTitle=='销售活动'){
		view.setParameters ({
			oppor_id : param
		}); 
	}
};

/**
 * 结果域面板滑入后触发,系统提供listener事件方法
 */
var viewshow = function(theview){
	
};	
