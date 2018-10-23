/**
 * @description 打印预览之 反洗钱指标详情
 * @author helin
 * @since 2014-10-28
 */
Ext.onReady(function(){
	saveFXQIndexStore = new Ext.data.Store({
		restful : true,
		proxy : new Ext.data.HttpProxy({
			url :basepath + '/EchainCommon!getAllComments.json?instanceID='+instanceid
		}),
		reader:new Ext.data.JsonReader({
			root : ''
		},[{
			name: 'nodeName'},//审批节点
	      	{name: 'commentTime'},//意见时间
	      	{name: 'userName'},//审批人
	      	{name: 'commentSign'},//意见标识(没取到)
	      	{name: 'commentContent'}//流程意见
	   ])
	});
	
	var saveFXQIndexStoretSize=0;
	var spstatusStoresize=0;
	saveFXQIndexStoreT = new Ext.data.Store({
		restful : true,
		proxy : new Ext.data.HttpProxy({
			url :basepath + '/EchainCommon!getWorkFlowHistory.json?instanceID='+instanceid
		}),
		reader:new Ext.data.JsonReader({
			root : ''
		},[{
			name: 'nodeName'},//流程呢办理节点
	      	{name: 'userName'},//办理人
	      	{name: 'nodeStartTime'},//办理时间
	      	{name: 'methods'}//下一办理人
	      	
	   ])
	});
	
	saveFXQIndexStore.load({
        callback: function(){
        	size = saveFXQIndexStore.getCount();
        }});
	
	spstatusStore = new Ext.data.Store({
		restful : true,
		proxy : new Ext.data.HttpProxy({
			url :basepath + '/antMoneyAdjust!getspstatus.json?instanceid='+instanceid
		}),
		reader:new Ext.data.JsonReader({
			root : 'json.data',
			totalProperty:'json.count'
		},[{
			name: 'SPSTATUS'}//审批状态
	   ])
	});
	
	spstatusStore.load( {callback: function(){
		spstatusStoresize = spstatusStore.getCount();
    }});
	
	
	/*		// 20160328修改打印页面信息
	 * saveFXQIndexStore2 = new Ext.data.Store({
		restful : true,
		proxy : new Ext.data.HttpProxy({
			url :basepath + '/antmoneyprintquery.json?instanceID='+instanceid
		}),
		reader:new Ext.data.JsonReader({
			root : ''
		},[
			{name : 'INSTANCEID'},
            {name : 'WFJOBNAME',header : '工作名称'},
            {name : 'WFNAME',header : '流程名称'},
            {name : 'WFSTARTTIME',header : '流程开始时间'},
            {name : 'AUTHOR',header : '发起人ID',hidden:true},
            {name : 'AUTHOR_NAME',header : '发起人'},
            {name : 'PRENODENAME',header : '上一办理人'},
            {name : 'NODENAME',header : '当前环节'},
            {name : 'SPSTATUS',header :'审批状态'
                ,renderer : function(value){
                    if(value==0){//实际为审批中
                        return '正常办理';
                    }else if(value ==1){//同意
                        return '同意';
                    }else if(value ==2){//不同意
                        return '否决';
                    }else if(value ==3){//部分同意
                        return '部分同意';
                    }else if(value ==-1){//不明确
                        return '撤销';
                    }else{
                        return '未知';
                    }
                }
            },
            {name : 'WFSIGN'},
            {name : 'NODEID'}
	   ])
	});
	
	
	
*/	
	
	/*saveFXQIndexStore.baseParams = {
		custId : custId
	};*/
	/**
	 * 获取前时间
	 */
	function getNowFormatDate() {
	    var date = new Date();
	    var seperator1 = "-";
	    var seperator2 = ":";
	    var month = date.getMonth() + 1;
	    var strDate = date.getDate();
	    var minute= date.getMinutes()
	    var hour=date.getHours()
	    var second=date.getSeconds()
	    if (month >= 1 && month <= 9) {
	        month = "0" + month;
	    }
	    if (strDate >= 0 && strDate <= 9) {
	        strDate = "0" + strDate;
	    }
	    if (minute >= 0 && minute <= 9) {
	    	minute = "0" + minute;
	    }
	    if (hour >= 0 && hour <= 9) {
	    	hour = "0" + hour;
	    }
	    if (second >= 0 && second <= 9) {
	    	second = "0" + second;
	    }
	    var currentdate = date.getFullYear() + seperator1 + month + seperator1 + strDate
	            + " " + hour+ seperator2 + minute
	            + seperator2 +second ;
	    return currentdate;
	} 
	
//	//debugger;
//	var html = '<div class="showPrintContent"><div class="Noprint"><input type="button" class="button" value="打印" onclick="printPageCurr();"><div id="beforeEnd"></div></div>';
//	html += '<div id="showPrintView">';
//	html +='<div class="single"><div class="titleDiv">客户反洗钱指标信息</div><br><table class="print_tab_01">';
//	html +='<div class="title2">客户号：'+custId+'&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <span class="endDiv">打印时间：'+getNowFormatDate()+'</span></div><br></table>';
	var html='';
	html+=' <div class="showPrintContent" >';
		html+=' <div class="Noprint"><input type="button" class="button" value="打印" onclick="return PrintTable(showPrintView);"><div id="beforeEnd"></div></div>';
		html+=' <div id="showPrintView"  >';
			html+=' <div class="single"><div class="titleDiv" style="text-align: center;font-weight:bolder;font-size:16px">反洗钱等级调整信息-个人</div><hr/>';
			//html+=' <div class="title2" ><span class="endDiv">打印时间：'+getNowFormatDate()+'</span></div>';	
			html+=' <div class="title2" >打印时间：'+getNowFormatDate()+' <span class="endDiv">打印人：'+__userName+'</span></div><hr/>';	
	saveFXQIndexStoreT.load({
        callback: function(){
        	saveFXQIndexStoretSize= saveFXQIndexStoreT.getCount();


	///*******************************************打印页面信息*************************************************************	
			html+=' <table class="print_tab_01" >';	
			html+=' 		<tbody>';	
					
		html+=''+
			
		'<tr>'+
			'<td width="320" class="css1">客户号</td>'+
			'<td width="200" class="css2">'+CUST_ID+'</td>'+
			'<td width="320" class="css3">客户姓名</td>'+
			'<td width="200" class="css2">'+CUST_NAME+'</td>'+
		'</tr>'+
		
		'<tr>'+
			'<td width="320" class="css1">核心客户号</td>'+
			'<td width="200" class="css2">'+CORE_NO+'</td>'+
			'<td width="320" class="css3">国籍</td>'+
			'<td width="200" class="css2">'+CITIZENSHIP+'</td>'+
		'</tr>'+
		
		'<tr>'+
			'<td width="320" class="css1">出生日期</td>'+
			'<td width="200" class="css2">'+BIRTHDAY+'</td>'+
			'<td width="320" class="css3">职业</td>'+
			'<td width="200" class="css2">'+CAREER_TYPE+'</td>'+
		'</tr>'+
		
		'<tr>'+
			'<td width="320" class="css1">证件类型1</td>'+
			'<td width="200" class="css2">'+IDENT_TYPE1+'</td>'+
			'<td width="320" class="css3">证件类型2</td>'+
			'<td width="200" class="css2">'+IDENT_TYPE2+'</td>'+
		'</tr>'+
		
		'<tr>'+
			'<td width="320" class="css1">证件号1</td>'+
			'<td width="200" class="css2">'+INDENT_NO1+'</td>'+
			'<td width="320" class="css3">证件号2</td>'+
			'<td width="200" class="css2">'+INDENT_NO2+'</td>'+
		'</tr>'+
		
		'<tr>'+
			'<td width="320" class="css1">证件1到期日</td>'+
			'<td width="200" class="css2">'+IDENT_EXPIRED_DATE1+'</td>'+
			'<td width="320" class="css3">证件2到期日</td>'+
			'<td width="200" class="css2">'+IDENT_EXPIRED_DATE2+'</td>'+
		'</tr>'+
		
		'<tr>'+
			'<td width="320" class="css1">是否为代理开户</td>'+
			'<td width="200" class="css2">'+FLAG_AGENT+'</td>'+
			'<td width="320" class="css3">代理人姓名</td>'+
			'<td width="200" class="css2">'+AGENT_NAME+'</td>'+
		'</tr>'+
		
		'<tr>'+
			'<td width="320" class="css1">代理人证件类型</td>'+
			'<td width="200" class="css2">'+AGE_IDENT_TYPE+'</td>'+
			'<td width="320" class="css3">代理人国籍</td>'+
			'<td width="200" class="css2">'+AGENT_NATION_CODE+'</td>'+
		'</tr>'+
		
		'<tr>'+
			'<td width="320" class="css1">代理人证件号码</td>'+
			'<td width="200" class="css2">'+AGE_IDENT_NO+'</td>'+
			'<td width="320" class="css3">代理人联系方式</td>'+
			'<td width="200" class="css2">'+TEL+'</td>'+
		'</tr>'+
		'<tr>'+
		'<td width="320" class="css1">客户类型</td>'+
		'<td width="200" class="css2">'+CUST_TYPE+'</td>'+

		'<td width="320" class="css3">客户办理的业务</td>'+
		'<td width="200" class="css2">'+FXQ007+'</td>'+
	'</tr>'+
	
	
	'<tr>'+

		'<td width="320" class="css1">客户是否涉及风险提示信息或权威媒体报道信息</td>'+
		'<td width="200" class="css2">'+FXQ008+'</td>'+
		'<td width="320" class="css3">客户或其亲属、关系密切人等是否属于外国政要</td>'+
		'<td width="200" class="css2">'+FXQ009+'</td>'+
	'</tr>'+
	'<tr>'+
	'<td width="320" class="css1">系统预评</td>'+
	'<td width="200" class="css2">'+CUST_GRADE+'</td>'+

	'<td width="320" class="css3">客户是否为自贸区客户</td>'+
	'<td width="200" class="css2">'+IF_ORG_SUB_TYPE_PER+'</td>'+
	'</tr>'+

	'<tr>'+
		'<td width="320" class="css1">复评等级</td>'+
		'<td width="200" class="css2" colspan="3">'+CUST_GRADE_FP+'</td>'+
		'<td ></td>'+
		'<td ></td>'+
	'</tr>'+
		'<tr>'+
		'<td width="320" class="css1">调整风险等级说明</td>'+
		'<td width="200" class="css2" colspan="3" >'+INSTRUCTION+'</td>'+
		'<td ></td>'+
		'<td ></td>'+
		'</tr>'+
		'';
		
		
	
				html+='	</tbody>'+ 
				'	</table> ';
				
				html+='<div style=" margin-top:10px;">';
		        	html+='<table  class="print_tab_02">';
		        	html+='<tr>';
		        	html+='<th width="150" align="center">流程办理节点</th>';
		        	html+='<th width="150" align="center">办理人</th>';
		        	html+='<th width="200" align="center">流程意见</th>';
		        	html+='<th width="150" align="center">办理时间</th>';
		        	html+='<th width="150" align="center">下一办理人</th>';
		        	
		        	html+='</tr>';
				if(saveFXQIndexStoretSize > 0){
		        		for(var i=0;i<saveFXQIndexStoretSize;i++){
		       			var datat = saveFXQIndexStoreT.getAt(i).data;
		       			//html +='<tr><td width="20%">'+data.CUST_ID+'</td><td width="20%">'+data.CUST_ID+'</td><td width="20%">'+data.CUST_ID+'</td><td width="10%">'+data.CUST_ID+'</td><td width="10%">'+data.CUST_ID+'</td><td width="15%">'+data.CUST_ID+'</td></tr>';       		
		       			html +='<tr>';
		       			html +='<td align="center">'+datat.nodeName+'</td>';
		       			
		       			html +='<td align="center">'+datat.userName+'</td>';
		       			
		       			//html +='<td width="100" align="center">'+data.commentSign+'</td>';
		       			var flsgstr=false;
		       			if(saveFXQIndexStore.getCount()>0){
		       				for(var j=0;j<saveFXQIndexStore.getCount();j++){
		       					var nodeNamev=saveFXQIndexStore.getAt(j).data.nodeName;
		       					if(nodeNamev==datat.nodeName){
		       						var objecct =new Ext.util.MixedCollection();
		       						objecct=saveFXQIndexStore.query('nodeName', datat.nodeName);
		       						var ssr='';
		       						for(k=0;k<objecct.length;k++){
		       							ssr+=objecct.items[k].data.commentContent+" ";
		       						}
		       						var ary = new Array(); 
		       						ary=saveFXQIndexStore.find('nodeName', datat.nodeName);
		       						saveFXQIndexStore.remove(saveFXQIndexStore.getAt(ary));
		    	       				html +='<td align="center">'+ssr+'</td>'
		    	       				flsgstr=true;
		       					}
		       					
		       				}
		       			  
		       			}
		       			if(i==0&&datat.nodeName&&flsgstr==false){
		       				html +='<td align="center">-</td>';
		       			}else if(datat.nodeName.indexOf("发起")>=0&&flsgstr==false){
		       				html +='<td align="center">-</td>';
		       			}
		       			if(datat.userName=="-"&&datat.nodeStartTime=="-"&&datat.methods=="-"){
		       			 if(flsgstr){
	                     		
	                     	}else{
	 	       				html +='<td align="center">-</td>';
	                     	}
		       			}
		       			
		       			html +='<td align="center">'+datat.nodeStartTime+'</td>';
		       			if(datat.methods!='-'){
		       			nextuserNamev=datat.methods.substring(datat.methods.lastIndexOf("：")+1,datat.methods.length-1);
		       			html +='<td align="center">'+nextuserNamev+'</td>';
		       			}else{
		       				html +='<td align="center">-</td>';
		       			}
		        	}
		        		html +='</tr>';  	
		        		if(saveFXQIndexStoreT.getAt(saveFXQIndexStoreT.getCount()-1).data.nodeName=='结束'){
		       		    	html +='<tr><td align="left" colspan=6>当前流程状态：流程已结束</td></tr>';
		       		    }else{
		       		    	html +='<tr><td align="left" colspan=6>当前流程状态：正常办理中</td></tr>';
		       		    }
		        		
		        		if(spstatusStore.getAt(0).data.SPSTATUS=='1'){
		        			html +='<tr><td align="left" colspan=6>审批状态：同意</td></tr>';
		        		}else if(spstatusStore.getAt(0).data.SPSTATUS=='2'){
		        			html +='<tr><td align="left" colspan=6>当前流程状态：否决</td></tr>';
		        		}
		       			  		       			
		        	}
				   else{
				      html +='<tr><td colspan=6>没有反洗钱交易检测记录说明</td></tr>';
		        	}
  	          	html +='</table></div>';
  	       
  	          	
  /*	        saveFXQIndexStore2.load({
  	        	
  	          callback: function(){
  	        	  debugger;
  	          
  	              //var size2 = saveFXQIndexStore2.getCount();
  	        	var size2 = saveFXQIndexStore2.getCount();
  	        	if(size2>0){
  	        		var data2 = saveFXQIndexStore2.getAt(0).data;
  	  	            html+='当前流程审批状态：'+data2.SPSTATUS; 
  	        	}
  	             	
  	      ///*******************************************打印页面信息*************************************************************    

  	                  html+='    </div> </div> </div> ';

  	      ///*********************************************打印页面信息***********************************************************    

  	            
  	          }
  	      });
  	          */
  	          
				html+='	</div> </div> </div> ';
	
	///*********************************************打印页面信息***********************************************************	
		
        	printPanel.removeAll();
        	printPanel.add({
        		autoScroll : true,
        		html:html
        	});
        	printPanel.doLayout();
        }
    });
			
    var printPanel = new Ext.Panel({
    	layout:'fit',
    	height:1200,
		items:[]
	});
    
    var wbPanel = new Ext.Panel({
		id : 'wbPanel',
		hidden: true
	});
	
	var viewport = new Ext.Viewport({
		layout : 'fit',
		height:1200,
		items : [printPanel,wbPanel]
	});
});

