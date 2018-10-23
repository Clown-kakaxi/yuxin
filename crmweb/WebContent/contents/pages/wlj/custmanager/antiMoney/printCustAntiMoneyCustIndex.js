/**
 * @description 打印预览之 反洗钱指标详情
 * @author helin
 * @since 2014-10-28
 */
Ext.onReady(function(){
	var saveFXQIndexRecord = new Ext.data.Record.create([
	"INSTRUCTION_CONTENT",  //fxq010 指标说明内容
	"INSTR_TIME", 			//时间	
	"INSTR_USER", 			//用户
	"KYJYBG_SBSJ", 			//指标编号
	"USER_NAME",
	  'CUST_ID',
	  'CUST_NAME',
	  'CORE_NO',	  
	  'CUST_TYPE', 
	  'CUST_TYPE1',
	  'IDENT_TYPE', 
	  'IDENT_NO',
	  'ORG_NAME',
	  'BELONG_TEAM_HEAD_NAME',
	  'FLAG_AGENT', 'FXQ007', 'FXQ008', 'FXQ009',
		'FXQ021', 'FXQ022', 'FXQ023', 'FXQ024',
		'FXQ025', 'CREATE_DATE', 'FLAG'
		
		,'FXQ016', 'FXQ011', 'FXQ012', 'FXQ013',
		'FXQ014', 'FXQ015', 'FXQ010','FXQ026',
		'INSTRUCTION_CONTENT1'
	]);
	// create the data store
	var saveFXQIndexStore = new Ext.data.Store({
	    restful:true,
	    proxy: new Ext.data.HttpProxy({
	        url: basepath + '/customerAntiMoneyQuery!queryCustFXQIndex.json'
	    }),
	    reader: new Ext.data.JsonReader({
	        root:'json.data',
	        totalProperty:'json.count'
	    },saveFXQIndexRecord)
	});

	saveFXQIndexStore.baseParams = {
		custId : custId
	};
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
	debugger;
	var html='';
	html+=' <div class="showPrintContent" >';
		html+=' <div class="Noprint"><input type="button" class="button" value="打印" onclick="PrintTable(showPrintView);"><div id="beforeEnd"></div></div>';
		html+=' <div id="showPrintView" >';
			html+=' <div class="single"><div class="titleDiv" style="text-align: center;font-weight:bolder;font-size:18px">客户反洗钱指标信息</div><hr/>';
			html+=' <div class="title2" >客户号：'+custId+' <span class="endDiv">打印时间：'+getNowFormatDate()+'</span></div><hr/>';	
	saveFXQIndexStore.load({
        callback: function(){
        	var size = saveFXQIndexStore.getCount();
        	var data1= saveFXQIndexStore.getAt(0).data;
//        	
//        	/**
//        	 * 客户办理的业务(对私)
//        	 */
//        	 if (data1.FXQ007 != null && data1.FXQ007 != "") {
//	  				var fxq007 = data1.FXQ007.split(',');
//	  				var str007="";
//	        	for ( var i = 0; i < fxq007.length; i++) {
//					if(i!=0){
//						str007+=",";
//					}
//	        		switch(fxq007[i])
//	            	{
//	            	case "1":
//	            		str007+="现金业务";
//	            	  break;
//	            	case "2":
//	            		str007+="网上银行";
//	            	  break;
//	            	case "3":
//	            		str007+="传真银行";
//		            	  break;
//	            	case "4":
//	            		str007+="跨境交易";
//		            	  break;
//	            	case "5":
//	            		str007+="代理业务";
//		            	  break;
//	            	case "6":
//	            		str007+="私人银行业务";
//		            	  break;
//	            	case "7":
//	            		str007+="不办理上述业务";
//		            	  break;
//	            	}
//	        		
//				}
//	        	data1.FXQ007=str007;
//        	 }
//        	 /**
//         	 * 客户办理的业务(对公)
//         	 */
//         	 if (data1.FXQ025 != null && data1.FXQ025 != "") {
// 	  				var fxq025= data1.FXQ025.split(',');
// 	  				var str025="";
// 	        	for ( var  i= 0; i < fxq025.length; i++) {
// 					if(i!=0){
// 						str025+=",";
// 					}
// 	        		switch(fxq025[i])
// 	            	{
// 	            	case "1":
// 	            		str025+="现金业务";
// 	            	  break;
// 	            	case "2":
// 	            		str025+="网上银行";
// 	            	  break;
// 	            	case "3":
// 	            		str025+="传真银行";
// 		            	  break;
// 	            	case "4":
// 	            		str025+="跨境交易";
// 		            	  break;
// 	            	case "5":
// 	            		str025+="代理业务";
// 		            	  break;
// 	            	case "6":
// 	            		str025+="信托业务";
// 		            	  break;
// 	            	case "7":
// 	            		str025+="代理行业务";
// 		            	  break;
// 	            	case "8":
// 	            		str025+="客户不办理上述所列业务";
// 		            	  break;
// 	            	}
// 	        		
// 				}
// 	        	data1.FXQ025=str025;
//         	 }
//         	
//        	
//       
//        	/**********************客户指标
//        	 * 
//        	 */
//        	html +='<table width="799"  cellspacing="0" cellpadding="2">';
//        		html +=' <tr>';
//        		html +=' <th width="249" align="right" >客户编号</th>';
//        		html +=' <th width="142" align="center" >'+data1.CUST_ID+'</th>';
//        		html +=' <th width="249" align="right" >客户名称：</th>';
//        		html +=' <th width="141" align="center" >'+data1.CUST_NAME+'</th>';
//        	html +=' </tr>';
//        	html +=' <tr>';
//        		html +=' <td width="249" align="right">核心客户号:</td>';
//        		html +=' <td align="center">'+data1.CORE_NO+'</td>';
//        		html +=' <td width="249" align="right">客户类型:</td>';
//        		html +=' <td align="center">'+data1.CUST_TYPE1+'</td>';
//        	html +=' </tr>';
//        	html +='<tr>';
//        		html +='<td width="249" align="right">证件类型:</td>';
//        		html +=' <td align="center">'+data1.IDENT_TYPE+'</td>';
//        		html +=' <td width="249" align="right">证件号码:</td>';
//        		html +='<td align="center">'+data1.IDENT_NO+'</td>';
//        	html +=' </tr>';
//        	html +='<tr>';
//        		html +='<td width="249" align="right">归属机构:</td>';
//        		html +='<td align="center">'+data1.ORG_NAME+'</td>';
//        		html +='<td width="249" align="right">所属团队负责人:</td>';
//        		html +='<td align="center">'+data1.BELONG_TEAM_HEAD_NAME+'</td>';
//        	html +='</tr>';
//        	if(data1.CUST_TYPE=="2"){
//        		html +='<!--个人-->';
//            	html +='<tr>';
//            		html +='<td width="249" align="right">客户是否为代理开户:</td>';
//            		html +='<td align="center">'+((data1.FLAG_AGENT=='0')?'否':'是')+'</td>';
//            		html +='<td width="249" align="right">是否涉及风险提示信息或权威媒体报道信息:</td>';
//            		html +='<td align="center">'+((data1.FXQ008=='0')?'否':'是')+'</td>';
//            	html +='</tr>';
//            	html +='<tr>';
//    	        	  html +='<td width="249" align="right">客户办理的业务(对私):</td>';
//    	        	  html +='<td align="center">'+data1.FXQ007+'</td>';
//    	        	  html +='<td width="249" align="right">客户或其亲属、关系密切人等是否属于外国政要:</td>';
//    	        	  html +='<td align="center">'+((data1.FXQ009=='0')?'否':'是')+'</td>';
//    	        	  html +='</tr>';
//    	        	  if (roleCodes != null && roleCodes != "") {
//    	  				var roleArrs = roleCodes.split('$');
//    	  				for ( var i = 0; i < roleArrs.length; i++) {
//    	  					if (roleArrs[i] == "R115"||roleArrs[i] == "R116"){
//    	  						
//    	  							html +='<tr>';
//    	  	    	        	  html +='<td width="249" align="right">是否频繁进行异常交易:</td>';
//    	  	    	        	  html +='<td align="center">'+((data1.FXQ016=='0')?'否':'是')+'</td>';
//    	  	    	        	  html +='<td width="249" align="right">是否被列入中国发布或承认的应实施反洗钱监控措施的名单</td>';
//    	  	    	        	  html +='<td align="center">'+((data1.FXQ011=='0')?'否':'是')+'</td>';
//    	  	    	        	  html +=' </tr>';
//    	  	    	        	  html +='<tr>';
//    	  	    	        	  html +='<td width="249" align="right">是否发生具有异常特征的大额现金交易:</td>';
//    	  	    	        	  html +='<td align="center">'+((data1.FXQ012=='0')?'否':'是')+'</td>';
//    	  	    	        	  html +='<td width="249" align="right">是否发生具有异常特征的非面对面交易:</td>';
//    	  	    	        	  html +='<td align="center">'+((data1.FXQ013=='0')?'否':'是')+'</td>';
//    	  	    	        	  html +='</tr>';
//    	  	    	        	  html +='<tr>';
//    	  	    	        	  html +='<td width="249" align="right">是否存在多次涉及跨境异常交易报告:</td>';
//    	  	    	        	  html +='<td align="center">'+((data1.FXQ014=='0')?'否':'是')+'</td>';
//    	  	    	        	  html +='<td width="249" align="right">代办业务是否存在异常情况:</td>';
//    	  	    	        	  html +='<td align="center">'+((data1.FXQ015=='0')?'否':'是')+'</td>';
//    	  	    	        	  html +='</tr>';
//    	  	    	        	  html +='<tr>';
//    	  	    	        	  html +='<td width="249" align="right">反洗钱交易监测记录:</td>';
//    	  	    	        	  html +='<td align="center">'+data1.FXQ010+'</td>';
//    	  	    	        	  html +='<td width="249" align="right">&nbsp;</td>';
//    	  	    	        	  html +='<td align="center"></td>';
//    	  	    	        	  html +='</tr>';
//    	  	    	        	  break;
//    	  						}
//    	  					}
//    	  				}
//    	        	 
//    	        	
//    	        	  
//        	}else if(data1.CUST_TYPE=="1"){
//        		var index1='100';
//        		  if (roleCodes != null && roleCodes != "") {
//  	  				var roleArrs = roleCodes.split('$');
//  	  				for ( var i = 0; i < roleArrs.length; i++) {
//  	  					if (roleArrs[i] == "R115"||roleArrs[i] == "R116"){
//  	  						index1='R115';	
//  	  						html +='<tr>';
//	  	 	        	  html +='<td align="right">是否涉及风险提示信息或权威媒体报道信息:</td>';
//	  	 	        	  html +='<td align="center">'+((data1.FXQ008=='0')?'否':'是')+'</td>';
//	  	 	        	  html +='<td align="right">客户或其亲属、关系密切人等是否属于外国政要:</td>';
//	  	 	        	  html +='<td align="center">'+((+data1.FXQ009=='0')?'否':'是')+'</td>';
//	  	         	    html +='</tr>';
//	  	         	    html +='<tr>';
//	  	         	    html +=' <td align="right">与客户建立业务关系的渠道:</td>';
//	  	         	    html +='<td align="center">'+((data1.FXQ021=='0')?'否':'是')+'</td>';
//	  	         	    html +='<td align="right">是否在规范证券市场上市:</td>';
//	  	         	    html +='<td align="center">'+((data1.FXQ022=='0')?'否':'是')+'</td>';
//	  	         	    html +='</tr>';
//	  	         	    html +='<tr>';
//	  	         	    html +=' <td align="right">客户的股权或控制权结构:</td>';
//	  	         	    html +='<td align="center">'+((data1.FXQ023=='0')?'否':'是')+'</td>';
//	  	         	    html +='<td align="right">客户是否存在隐名股东或匿名股东:</td>';
//	  	         	    html +='<td align="center">'+((data1.FXQ024=='0')?'否':'是')+'</td>';
//	  	         	    html +='</tr>';
//	  	         	    html +='<tr>';
//	  	         	    html +='<td align="right">客户办理的业务(对公):</td>';
//	  	         	    html +='<td align="center">'+((data1.FXQ025=='0')?'否':'是')+'</td>';
//	  	         	    html +='<td align="right">是否频繁进行异常交易</td>';
//	  	         	    html +='<td align="center">'+((data1.FXQ016=='0')?'否':'是')+'</td>';
//	  	         	    html +='</tr>';
//	  	         	    html +='<tr>';
//	  	         	    html +='<td align="right">是否发生具有异常特征的大额现金交易:</td>';
//	  	         	    html +='<td align="center">'+((data1.FXQ012=='0')?'否':'是')+'</td>';
//	  	         	    html +='<td align="right">是否被列入中国发布或承认的应实施反洗钱监控措施的名单:</td>';
//	  	         	    html +='<td align="center">'+((data1.FXQ011=='0')?'否':'是')+'</td>';
//	  	         	    html +='</tr>';
//	  	         	    html +='<tr>';
//	  	         	    html +='<td align="right">是否存在多次涉及跨境异常交易报告:</td>';
//	  	         	    html +='<td align="center">'+((data1.FXQ014=='0')?'否':'是')+'</td>';
//	  	         	    html +=' <td align="right">是否发生具有异常特征的非面对面交易:</td>';
//	  	         	    html +='<td align="center">'+((data1.FXQ013=='0')?'否':'是')+'</td>';
//	  	         	    html +='</tr>';
//	  	         	    html +='<tr>';
//	  	         	    html +='<td align="right">反洗钱交易监测记录:</td>';
//	  	         	    html +='<td align="center">'+data1.FXQ010+'</td>';
//	  	         	    html +='<td align="right">代办业务是否存在异常情况:</td>';
//	  	         	    html +='<td align="center">'+((data1.FXQ015=='0')?'否':'是')+'</td>';
//	  	         	    html +='</tr>';
//	  	         	    break;
//  	  					}
//  	  				}
//        		  }
//        		  if(index1=='100'){
//        				html +='<tr>';
//	  	 	        	html +='<td align="right">是否涉及风险提示信息或权威媒体报道信息:</td>';
//	  	 	        	html +='<td align="center">'+((data1.FXQ008=='0')?'否':'是')+'</td>';
//	  	 	        	html +='<td align="right">客户或其亲属、关系密切人等是否属于外国政要:</td>';
//	  	 	        	html +='<td align="center">'+((data1.FXQ009=='0')?'否':'是')+'</td>';
//	  	         	    html +='</tr>';
//	  	         	    html +='<tr>';
//	  	         	    html +=' <td align="right">与客户建立业务关系的渠道:</td>';
//	  	         	    html +='<td align="center">'+((data1.FXQ021=='0')?'否':'是')+'</td>';
//	  	         	    html +='<td align="right">是否在规范证券市场上市:</td>';
//	  	         	    html +='<td align="center">'+((data1.FXQ022=='0')?'否':'是')+'</td>';
//	  	         	    html +='</tr>';
//	  	         	    html +='<tr>';
//	  	         	    html +=' <td align="right">客户的股权或控制权结构:</td>';
//	  	         	    html +='<td align="center">'+((data1.FXQ023=='0')?'否':'是')+'</td>';
//	  	         	    html +='<td align="right">客户是否存在隐名股东或匿名股东:</td>';
//	  	         	    html +='<td align="center">'+((data1.FXQ024=='0')?'否':'是')+'</td>';
//	  	         	    html +='</tr>';
//	  	         	    html +='<tr>';
//	  	         	    html +='<td align="right">客户办理的业务(对公):</td>';
//	  	         	    html +='<td align="center">'+data1.FXQ025+'</td>';
//	  	         	    html +='</tr>';
//        		  }
//	        	 
//        	    html +='<!--企业-->';
//        	}
//        	    html +='</table>';
//        	
//        	//***********************客户指标结束
//        	
//        	
//        	
//        	    if (roleCodes != null && roleCodes != "") {
//        	    	debugger;
//  	  				var roleArrs = roleCodes.split('$');
//  	  				for ( var i = 0; i < roleArrs.length; i++) {
//  	  					if (roleArrs[i] == "R115"||roleArrs[i] == "R116"){
//  	  						index1='R115';
//  	  						//当上报时间和说明不为空则显示
//  	  					if(data1.INSTRUCTION_CONTENT!=""||data1.KYJYBG_SBSJ!=""){
//  	  	        		
//  	  		        	html+='<table width="800" border="1" cellspacing="0" cellpadding="0" style=" margin-top:20px;">';
//  	  		        	html+='<tr>';
//  	  		        	html+='<th width="100" scope="col">可疑交易报告上报时间</th>';
//  	  		        	html+='<th scope="col">反洗钱交易监测记录说明</th>';
//  	  		        	html+='<th width="100" scope="col">修改时间</th>';
//  	  		        	//html+='<th width="100" scope="col">用户编号</th>';
//  	  		        	html+='<th width="100" scope="col">录入人员</th>';
//  	  		        	html+='</tr>';
//  	  		        	if(size > 0){
//  	  		        		for(var i=0;i<size;i++){
//  	  		       			var data = saveFXQIndexStore.getAt(i).data;
//  	  		       			//html +='<tr><td width="20%">'+data.CUST_ID+'</td><td width="20%">'+data.CUST_ID+'</td><td width="20%">'+data.CUST_ID+'</td><td width="10%">'+data.CUST_ID+'</td><td width="10%">'+data.CUST_ID+'</td><td width="15%">'+data.CUST_ID+'</td></tr>';       		
//  	  		       			html +='<tr>';
//  	  		       			html +='<td width="100" align="center">'+data.KYJYBG_SBSJ+'</td>';
//  	  		       			html +='<td align="center">'+data.INSTRUCTION_CONTENT+'</td>';
//  	  		       			html +='<td width="100" align="center">'+data.INSTR_TIME+'</td>';
//  	  		       			//html +='<td width="100" align="center">'+data.INSTR_USER+'</td>';
//  	  		       			html +='<td width="100" align="center">'+data.USER_NAME+'</td>';
//  	  		       			html +='</tr>';
//  	  		       			
//  	  		        		}
//  	  		        	}else{
//  	  		        		html +='<tr><td colspan=6>无变更内容</td></tr>';
//  	  		     		     	}
//  	  		       	html +='</table>';
//  	  		       	break;
//  	          	}
//  	  					}
//  	  				}
//        	    }
//        	
//			html += '</div></div>';
	

	///*******************************************打印页面信息*************************************************************	
debugger;
			html+=' <table class="print_tab_01" >';	
			html+=' 		<tbody>';	
					
			html+=' <!-- 共有（高中低）开始--> '+
			' <tr> '+
			' <td width="300" class="css1">客户号</th> '+
			' <td width="200" class="css2">'+CUST_ID+'</th> '+
			' <td width="300" class="css3">客户姓名</th> '+
			' <td width="200" class="css2">'+CUST_NAME+'</th> '+
			' </tr> '+
			' <tr> '+
			' <td width="300" class="css1">核心客户号</td> '+
			' <td class="css2">'+CORE_NO+'</td> '+
			' <td width="300" class="css3">客户类型</td> '+
			' <td class="css2">'+CUST_TYPE+'</td> '+
			' </tr> '+
			' <tr> '+
			' <td width="300" class="css1">证件1类型</td> '+
			' <td class="css2">'+IDENT_TYPE1+'</td> '+
			' <td width="300" class="css3">证件1类型 </td> '+
			' <td class="css2">'+IDENT_TYPE2+'</td> '+
			' </tr> '+
			' <tr> '+
			' <td width="300" class="css1">证件2号码</td> '+
			' <td class="css2">'+INDENT_NO1+'</td> '+
			' <td width="300" class="css3">证件2号码</td> '+
			' <td class="css2">'+INDENT_NO2+'</td> '+
			' </tr> '+
			' <tr> '+
			' <td class="css1">证件1到期日</td> '+
			' <td class="css2">'+IDENT_EXPIRED_DATE1+'</td> '+
			' <td class="css3">证件2到期日</td> '+
			' <td class="css2">'+IDENT_EXPIRED_DATE2+'</td> '+
			' </tr> '+
			' <tr> '+
			' <td  class="css1">客户是否为代理开户 </td> '+
			' <td class="css2">'+FLAG_AGENT+'</td> '+
			' <td class="css3">代理人姓名 </td> '+
			' <td class="css2">'+AGENT_NAME+'</td> '+
			' </tr> '+
			' <tr> '+
			' <td class="css1">代理人国家代码</td> '+
			' <td class="css2">'+AGENT_NATION_CODE+'</td> '+
			' <td class="css3">代理人联系电话 </td> '+
			' <td class="css2">'+TEL+'</td> '+
			' </tr> '+
			' <tr> '+
			' <td class="css1">代理人证件类型</td> '+
			' <td class="css2">'+AGE_IDENT_TYPE+'</td> '+
			' <td class="css3">代理人证件号码</td> '+
			' <td class="css2">'+AGE_IDENT_NO+'</td> '+
			' </tr> '+
			' <tr> '+
			' <td class="css1">客户是否涉及反洗钱黑名单</td> '+
			' <td class="css2">'+DQSH024+'</td> '+
			' <td class="css3">是客户是否涉及风险提示信息或权威</td> '+
			' <td class="css2">'+FXQ008+'</td> '+
			' </tr> '+
			' <tr> '+
			' <td class="css1">当前客户洗钱风险等级</td> '+
			' <td class="css2">'+CUST_GRADE+'</td> '+
			' <td class="css3"></td> '+
			' <td class="css2"></td> '+
			' </tr> '+
			' <!-- 共有（高中低）结束 --> ';
		
			
						
				
				
				if(CUST_TYPE=="个人"){
					
					
					html+=''+
//					'<tr> '+
//					' <td class="css1">个人(高中低) 开始</td> '+
//					' <td class="css2">----------</td> '+
//					' <td class="css3">-----------</td> '+
//					' <td class="css2">---------</td>  '+
//					' </tr> '+
					' <!-- 个人(高中低) 开始 --> '+
					' <tr> '+
					' <td class="css1">国籍</td> '+
					' <td class="css2">'+CITIZENSHIP+'</td> '+
					' <td class="css3" >职业  </td> '+
					' <td class="css2" >'+CAREER_TYPE+'</td> '+
					' </tr> '+
					' <tr> '+
					' <td class="css1">出生年月日</td> '+
					' <td class="css2">'+BIRTHDAY+'</td> '+
					' <td class="css3">客户是否为自贸区客户</td> '+
					' <td class="css2">'+IF_ORG_SUB_TYPE_PER+'</td> '+
					' </tr> '+
					' <tr> '+
					' <td class="css1">客户在我行办理的业务包括</td> '+
					' <td class="css2" >'+FXQ007+'</td><!-- style="border-top: 1px #ccc solid;" colspan="3" --> '+
					' <td class="css3">客户或其亲属、关系密切人是否属于外国政要</td> '+
					' <td class="css2">'+FXQ009+'</td> '+
					' </tr> '+
					' <!-- 个人(高中低)结束 --> ';
					
					if(CUST_GRADE=="高风险"||CUST_GRADE=="中风险"){
						
						html+=''+
//						'<tr> '+
//						' <td class="css1">-- 个人(高中)开始 -</td> '+
//						' <td class="css2">---------------</td> '+
//						' <td class="css3">-------------</td> '+
//						' <td class="css2">--------------------------</td> '+
//						' </tr> '+
								
						' <!-- 个人(高中)开始 --> '+
						' <tr> '+
						' <td class="css1">证件是否过期</td> '+
						' <td class="css2">'+DQSH001+'</td> '+
						' <td class="css3">账户是否频繁发生外币现钞存取业务</td> '+
						' <td class="css2">'+DQSH010+'</td> '+
						' </tr> '+
						' <tr> '+
						' <td class="css1">账户现金交易是否与客户职业特性不符</td> '+
						' <td class="css2">'+DQSH011+'</td> '+
						' <td class="css3">账户是否频繁发生大额的网上银行交易</td> '+
						' <td class="css2">'+DQSH012+'</td> '+
						' </tr>	 '+
						' <tr> '+
						' <td class="css1">账户是否与公司账户之间发生频繁或大额的交易</td> '+
						' <td class="css2">'+DQSH013+'</td> '+
						' <td class="css3">-账户是否存在分散转入集中转出或集中转入分散转出，且与客户身份、财务状况明显不符</td> '+
						' <td class="css2">'+DQSH014+'</td> '+
						' </tr> '+
						' <tr> '+
						' <td class="css1">账户是否频繁发生跨境交易，且金额大于1万美元 </td> '+
						' <td class="css2">'+DQSH020+'</td> '+
						' <td class="css3">账户是否经常由他人代为办理业务</td> '+
						' <td class="css2">'+DQSH021+'</td> '+
						' </tr> '+
						' <!-- 个人(高中)结束--> ';
						
					}if(CUST_GRADE=="低风险"){
						
					}
					
				}else if(CUST_TYPE=="企业"){
					
					html+=''+
//					'<tr> '+
//					' <td class="css1">//企业高中低</td> '+
//					' <td class="css2">--------------</td> '+
//					' <td class="css3">------------</td> '+
//					' <td class="css2">------------------</td> '+
//					' </tr> '+
					' <!-- 企业(高中低)开始--> '+
					' <tr> '+
					' <td class="css1">成立日期</td> '+
					' <td class="css2">'+BUILD_DATE+'</td> '+
					' <td class="css3">客户是否为自贸区客户</td> '+
					' <td class="css2">'+IF_ORG_SUB_TYPE_ORG+'</td> '+
					' </tr> '+
					' <tr> '+
					' <td class="css1">注册地</td> '+
					' <td class="css2">'+NATION_CODE+'</td> '+
					' <td class="css3">企业规模</td> '+
					' <td class="css2">'+ENT_SCALE_CK+'</td> '+
					' </tr> '+
					' <tr> '+
					' <td class="css1">行业分类</td> '+
					' <td class="css2">'+IN_CLL_TYPE+'</td> '+
					' <td class="css3">与客户建立业务关系的渠道</td> '+
					' <td class="css2">'+FXQ021+'</td> '+
					' </tr> '+
					' <tr> '+
					' <td class="css1">是否在规范证券市场上市</td> '+
					' <td class="css2">'+FXQ022+'</td> '+
					' <td class="css3">客户的股权或控制权结构</td> '+
					' <td class="css2">'+FXQ023+'</td> '+
					' </tr> '+
					' <tr> '+
					' <td class="css1">客户是否存在隐名股东或匿名股东</td> '+
					' <td class="css2">'+FXQ024+'</td> '+
					' <td class="css3">客户在我行办理的业务包括</td> '+
					' <td class="css2">'+FXQ025+'</td> '+
					' </tr> '+
					' <!-- 企业(高中低)结束--> ';
					
					
					if(CUST_GRADE=="高风险"||CUST_GRADE=="中风险"){

						html+=''+
//						'<tr> '+
//						' <td class="css1">企业(高中)开始</td> '+
//						' <td class="css2">--------------</td> '+
//						' <td class="css3">--------------</td> '+
//						' <td class="css2">----------</td> '+
//						' </tr> '+
								
						' <!-- 企业(高中)开始--> '+
								
						' <tr> '+
						' <td class="css1">企业证件是否过期</td> '+
						' <td class="css2">'+DQSH025+'</td> '+
						' <td class="css3">法定代表人证件是否过期</td> '+
						' <td class="css2">'+DQSH026+'</td> '+
						' </tr> '+
								
						' <tr> '+
						' <td class="css1">联系人证件是否过期</td> '+
						' <td class="css2">'+DQSH027+'</td> '+
						' <td class="css3">联系人的身份</td> '+
						' <td class="css2">'+DQSH028+'</td> '+
						' </tr> '+
						' <tr> '+
						' <td class="css1">账户是否与自然人账户之间发生频繁或大额的交易</td> '+
						' <td class="css2">'+DQSH029+'</td> '+
						' <td class="css3">账户是否存在分散转入集中转出或集中转入分散转出，且与经营背景、财务状况明显不符</td> '+
						' <td class="css2">'+DQSH030+'</td> '+
						' </tr> '+
						' <tr> '+
						' <td class="css1">账户是否频繁收取与其经营业务明显无关的汇款</td> '+
						' <td class="css2">'+DQSH031+'</td> '+
						' <td class="css3">账户资金交易频度、金额是否与其经营背景不符</td> '+
						' <td class="css2">'+DQSH032+'</td> '+
						' </tr> '+
						' <tr> '+
						' <td class="css1">账户交易对手及资金用途是否与其经营背景不符</td> '+
						' <td class="css2">'+DQSH033+'</td> '+
						' <td class="css3">账户是否与关联企业之间频繁发生大额交易</td> '+
						' <td class="css2">'+DQSH034+'</td> '+
						' </tr> '+	
						' <tr> '+
						' <td class="css1">客户实际受益人、实际控制人或者其亲属、关系密切人等是否属于外国政要</td> '+
						' <td class="css2">'+FXQ009+'</td> '+
						' <td class="css3"></td> '+
						' <td class="css2"></td> '+
						' </tr> '+
						' <!-- 企业(高中)结束--> ';		
					}
				}


				if(CUST_GRADE=="高风险"||CUST_GRADE=="中风险"){
					html+=' '+
//					' <tr> '+
//					' <td class="css1">--------</td> '+
//					' <td class="css2">--------</td> '+
//					' <td class="css3">--------</td> '+
//					' <td class="css2">--------</td> '+
//					' </tr> '+
					' <!-- 共有（高中）开始 --> '+
					' <tr> '+
					' <td class="css1">客户是否无法取得联系</td> '+
					' <td class="css2">'+DQSH002+'</td> '+
					' <td class="css3">联系时间</td> '+
					' <td class="css2">'+DQSH003+'</td> '+
					' </tr> '+
					' <tr> '+
					' <td class="css1">联系人与帐户持有人的关系</td> '+
					' <td class="css2">'+DQSH004+'</td> '+
					' <td class="css3">预计证件更新时间</td> '+
					' <td class="css2">'+DQSH005+'</td> '+
					' </tr> '+
					' <tr> '+
					' <td class="css1">未及时更新证件的理由</td> '+
					' <td class="css2">'+DQSH006+'</td> '+
					' <td class="css3">客户是否无正当理由拒绝更新证件</td> '+
					' <td class="css2">'+DQSH007+'</td> '+
					' </tr> '+
					' <tr> '+
					' <td class="css1">客户留存的证件及信息是否存在疑点或矛盾</td> '+
					' <td class="css2">'+DQSH008+'</td> '+
					' <td class="css3">账户是否频繁发生大额现金交易</td> '+
					' <td class="css2">'+DQSH009+'</td> '+
					' </tr> '+
					' <tr> '+
					' <td class="css1">账户资金是否快进快出，不留余额或少留余额</td> '+
					' <td class="css2">'+DQSH015+'</td> '+
					' <td class="css3">账户是否存在相同收付款人之间频繁发生交易，且金额接近大额交易标准</td> '+
					' <td class="css2">'+DQSH016+'</td> '+
					' </tr> '+
					' <tr> '+
					' <td class="css1">账户是否长期闲置不明原因地突然启用，且短期内出现大量资金收付</td> '+
					' <td class="css2">'+DQSH017+'</td> '+
					' <td class="css3">账户是否平常资金流量小，突然有异常资金流入，且短期内出现大量资金收付</td> '+
					' <td class="css2">'+DQSH018+'</td> '+
					' </tr> '+
					' <tr> '+
					' <td class="css1">账户的跨境交易是否涉及贩毒、走私、恐怖活动、赌博严重地区或避税型离岸金融中心</td> '+
					' <td class="css2">'+DQSH019+'</td> '+
					' <td class="css3">客户是否提前偿还贷款，且与其财务状况明显不符</td> '+
					' <td class="css2">'+DQSH022+'</td> '+
					' </tr> '+
					' <tr> '+
					' <td class="css1">当前账户状态是否正常</td> '+
					' <td class="css2">'+DQSH023+'</td> '+
					' <td class="css3">AUM(人民币)</td> '+
					' <td class="css2">'+CURRENT_AUM+'</td> '+
					' </tr> '+
					' <!-- 共有（高中）结束 --> ';
				}else if(CUST_GRADE=="低风险"){
					if(CUST_TYPE=="企业"){
					
					html+=' <tr> '+
					' <td class="css1">客户行为是否存在异常</td> '+
					' <td class="css2">'+DQSH0351+'</td> '+
					' <td class="css3">账户交易是否存在异常</td> '+
					' <td class="css2">'+DQSH0352+'</td> '+
					' </tr> ';
					}else if(CUST_TYPE=="个人")
					html+=' <tr> '+
					' <td class="css1">客户行为是否存在异常</td> '+
					' <td class="css2">'+DQSH0351+'</td> '+
					' <td class="css3">账户交易是否存在异常</td> '+
					' <td class="css2">'+DQSH0352+'</td> '+
					' </tr> ';
				}		
						
				
				  if (roleCodes != null && roleCodes != "") {
	  				var roleArrs = roleCodes.split('$');
	  				for ( var i = 0; i < roleArrs.length; i++) {
	  					if (roleArrs[i] == "R115"||roleArrs[i] == "R116"){
	  						html+=' '+	
//	  						' <tr> '+
//	  						' <td class="css1">合规处开始</td> '+
//	  						' <td class="css2">-------------</td> '+
//	  						' <td class="css3">-------------</td> '+
//	  						' <td class="css2">-------------</td> '+
//	  						' </tr> '+
	  						' <!-- 合规处开始--> '+
	  						' <tr> '+
	  						' <td class="css1">是否发生具有异常特征的大额现金交易</td> '+
	  						' <td class="css2">'+FXQ012+'</td> '+
	  						' <td class="css3">是否发生具有异常特征的非面对面交易</td> '+
	  						' <td class="css2">'+FXQ013+'</td> '+
	  						' </tr> '+
	  						' <tr> '+
	  						' <td class="css1">是否存在多次涉及跨境异常交易报告</td> '+
	  						' <td class="css2">'+FXQ014+'</td> '+
	  						' <td class="css3">是否频繁进行异常交易</td> '+
	  						' <td class="css2">'+FXQ016+'</td> '+
	  						' </tr> '+
	  						' <tr> '+
	  						' <td class="css1">反洗钱交易监测记录</td> '+
	  						' <td class="css2">'+FXQ010+'</td> '+
	  						' <td class="css3">代办业务是否存在异常情况</td> '+
	  						' <td class="css2">'+FXQ015+'</td> '+
	  						' </tr> '+
	  						' <tr> '+
	  						' <td class="css1">客户所在行政区域是否存在严重犯罪</td> '+
	  						' <td class="css2">'+FXQ026+'</td> '+
	  						' <td class="css3">是否被列入中国发布或承认的应实施反洗钱监控措施的名单</td> '+
	  						' <td class="css2">'+FXQ011+'</td> '+
	  						' </tr> '+
	  						' <!-- 合规处结束--> ';
	  						break;
	  					}
	  				}
				  }
	  						
						
			
						
						
						
						
						
				html+='	</tbody>'+ 
				'	</table> ';
				
				
				

        	    if (roleCodes != null && roleCodes != "") {
        	    	debugger;
  	  				var roleArrs = roleCodes.split('$');
  	  				for ( var i = 0; i < roleArrs.length; i++) {
  	  					if (roleArrs[i] == "R115"||roleArrs[i] == "R116"){
  	  						index1='R115';
  	  						//当上报时间和说明不为空则显示
  	  					
  	  					html+='<div style=" margin-top:10px; ">';
  	  		        	html+='<table  class="print_tab_02">';
  	  		        	html+='<tr>';
  	  		        	html+='<th width="500" align="center">反洗钱交易监测记录说明</th>';
  	  		        	html+='<th width="150" align="center">录入时间</th>';
  	  		        	html+='<th width="150" align="center">上报可疑交易报告时间</th>';
  	  		        	html+='<th width="80" align="center">录入人员</th>';
  	  		        	html+='</tr>';
  	  		        if(data1.INSTRUCTION_CONTENT!=""||data1.KYJYBG_SBSJ!=""){
  	  		        	if(size > 0){
  	  		        		for(var i=0;i<size;i++){
  	  		       			var data = saveFXQIndexStore.getAt(i).data;
  	  		       			//html +='<tr><td width="20%">'+data.CUST_ID+'</td><td width="20%">'+data.CUST_ID+'</td><td width="20%">'+data.CUST_ID+'</td><td width="10%">'+data.CUST_ID+'</td><td width="10%">'+data.CUST_ID+'</td><td width="15%">'+data.CUST_ID+'</td></tr>';       		
  	  		       			html +='<tr>';
  	  		       			html +='<td align="left">'+data.INSTRUCTION_CONTENT+'</td>';
  	  		       			html +='<td align="left">'+data.INSTR_TIME+'</td>';
  	  		       			html +='<td align="center">'+data.KYJYBG_SBSJ+'</td>';
  	  		       			//html +='<td width="100" align="center">'+data.INSTR_USER+'</td>';
  	  		       			html +='<td align="center">'+data.USER_NAME+'</td>';
  	  		       			html +='</tr>';  	  		       			
  	  		        		}
  	  		        	}
  	  		       	
  	  		       	break;
  	          	}else{
  	          	html +='<tr><td colspan=6>没有反洗钱交易检测记录说明</td></tr>';
  	          	html +='</table></div>';
  	          	}
  	  		}}}
				

				
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
		items:[]
	});
    
    var wbPanel = new Ext.Panel({
		id : 'wbPanel',
		hidden: true
	});
	
	var viewport = new Ext.Viewport({
		layout : 'fit',
		items : [printPanel,wbPanel]
	});
});