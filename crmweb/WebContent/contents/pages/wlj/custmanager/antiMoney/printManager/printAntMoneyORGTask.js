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
			html+=' <div class="single"><div class="titleDiv" style="text-align: center;font-weight:bolder;font-size:16px">反洗钱等级审核信息-企业</div><hr/>';
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
		'<td class="css2">'+CORE_NO+'</td>'+
		'<td width="320" class="css3">注册地 </td>'+
		'<td class="css2">'+NATION_CODE+'</td>'+
	'</tr>'+
		
				'<tr>'+
					'<td width="320" class="css1">成立日期</td>'+
					'<td class="css2">'+BUILD_DATE+'</td>'+
					'<td width="320" class="css3">企业规模</td>'+
					'<td class="css2">'+ENT_SCALE_CK+'</td>'+
				'</tr>'+
		'<tr>'+
			'<td width="320" class="css1">证件1类型</td>'+
			'<td class="css2">'+IDENT_TYPE1+'</td>'+
			'<td width="320" class="css3">证件1类型 </td>'+
			'<td class="css2">'+IDENT_TYPE2+'</td>'+
		'</tr>'+
		'<tr>'+
			'<td width="320" class="css1">证件2号码</td>'+
			'<td class="css2">'+INDENT_NO1+'</td>'+
			'<td width="320" class="css3">证件2号码</td>'+
			'<td class="css2">'+INDENT_NO2+'</td>'+
		'</tr>'+
		'<tr>'+
			'<td class="css1">证件1到期日</td>'+
			'<td class="css2">'+IDENT_EXPIRED_DATE1+'</td>'+
			'<td class="css3">证件2到期日</td>'+
			'<td class="css2">'+IDENT_EXPIRED_DATE2+'</td>'+
		'</tr>'+
	
		'<tr>'+
			'<td class="css1">行业分类</td>'+
			'<td class="css2">'+In_Cll_Type+'</td>'+
			'<td class="css3">客户是否为自贸区客户</td>'+
			'<td class="css2">'+if_org_sub_type_ORG+'</td>'+
		'</tr>'+		

		
		'<tr>'+
			'<td class="css1">是否为代理开户</td>'+
			'<td class="css2">'+FLAG_AGENT+'</td>'+
			'<td class="css3">代理人姓名</td>'+
			'<td class="css2">'+AGENT_NAME+'</td>'+
		'</tr>'+		

		

		
		'<tr>'+
			'<td class="css1">代理人证件类型</td>'+
			'<td class="css2">'+AGE_IDENT_TYPE+'</td>'+
			'<td class="css3">代理人国籍</td>'+
			'<td class="css2">'+AGENT_NATION_CODE+'</td>'+
		'</tr>'+		

		

		
		'<tr>'+
			'<td class="css1">代理人证件号码</td>'+
			'<td class="css2">'+AGE_IDENT_NO+'</td>'+
			'<td class="css3">代理人联系电话</td>'+
			'<td class="css2">'+TEL+'</td>'+
		'</tr>'+		

		

		
		'<tr>'+
			'<td class="css1">与客户建立业务关系的渠道</td>'+
			'<td class="css2">'+FXQ021+'</td>'+
			'<td class="css3">客户是否存在隐名股东或匿名股东</td>'+
			'<td class="css2">'+FXQ024+'</td>'+
		'</tr>'+		

		'<tr>'+
			'<td class="css1">是否在规范证券市场上市</td>'+
			'<td class="css2">'+FXQ022+'</td>'+
			'<td class="css3">客户是否涉及反洗钱黑名单</td>'+
			'<td class="css2">'+DQSH024 +'</td>'+
		'</tr>'+		

		'<tr>'+
			'<td class="css1">客户的股权或控制权结构</td>'+
			'<td class="css2">'+FXQ023+'</td>'+
			'<td class="css3">客户是否涉及风险提示信息或权威媒体报道信息</td>'+
			'<td class="css2">'+FXQ008+'</td>'+
		'</tr>'+		

		'<tr>'+
			'<td class="css1">客户办理的业务</td>'+
			'<td class="css2" colspan="3" >'+FXQ025+'</td>'+
			'<td ></td>'+
			'<td ></td>'+
		'</tr>'+		

		'<tr>'+
			'<td class="css1">企业证件是否过期</td>'+
			'<td class="css2">'+DQSH025+'</td>'+
			'<td class="css3">联系人证件是否过期</td>'+
			'<td class="css2">'+DQSH027 +'</td>'+
		'</tr>'+		

		'<tr>'+
			'<td class="css1">客户是否无法取得联系</td>'+
			'<td class="css2">'+DQSH002+'</td>'+
			'<td class="css3">联系时间</td>'+
			'<td class="css2">'+DQSH003 +'</td>'+
		'</tr>'+		

		'<tr>'+
			'<td class="css1">联系人的身份</td>'+
			'<td class="css2">'+DQSH028+'</td>'+
			'<td class="css3">法定代表人证件是否过期</td>'+
			'<td class="css2">'+DQSH026 +'</td>'+
		'</tr>'+		

		
		
		
		
		'<tr>'+
			'<td class="css1"> 联系人的身份说明</td>'+
			'<td class="css2" colspan="3" >'+DQSH038 +'</td>'+
			'<td ></td>'+
			'<td ></td>'+
		'</tr>'+		

		'<tr>'+
			'<td class="css1">预计证件更新时间</td>'+
			'<td class="css2">'+DQSH005+'</td>'+
			'<td class="css3">未及时更新证件的理由</td>'+
			'<td class="css2">'+DQSH006+'</td>'+
		'</tr>'+		

		'<tr>'+
			'<td class="css1">客户是否无正当理由拒绝更新证件 </td>'+
			'<td class="css2">'+DQSH007+'</td>'+
			'<td class="css3">当前账户状态是否正常</td>'+
			'<td class="css2">'+DQSH023+'</td>'+
		'</tr>'+		

		'<tr>'+
			'<td class="css1"> 客户留存的证件及信息是否存在疑点或矛盾</td>'+
			'<td class="css2">'+DQSH008+'</td>'+
			'<td class="css3">账户是否频繁收取与其经营业务明显无关的汇款</td>'+
			'<td class="css2">'+DQSH031+'</td>'+
		'</tr>'+		

		'<tr>'+
			'<td class="css1">账户是否频繁发生大额现金交易</td>'+
			'<td class="css2">'+DQSH009+'</td>'+
			'<td class="css3">客户实际受益人、实际控制人或者其亲属、关系密切人等是否属于外国政要</td>'+
			'<td class="css2">'+FXQ009+'</td>'+
		'</tr>'+				

		'<tr>'+
			'<td class="css1">账户资金交易频度、金额是否与其经营背景不符</td>'+
			'<td class="css2">'+DQSH032+'</td>'+
			'<td class="css3">账户交易对手及资金用途是否与其经营背景不符</td>'+
			'<td class="css2">'+DQSH033+'</td>'+
		'</tr>'+				

		'<tr>'+
			'<td class="css1">账户是否与自然人账户之间发生频繁或大额的交易</td>'+
			'<td class="css2">'+DQSH029+'</td>'+
			'<td class="css3">账户是否长期闲置不明原因地突然启用，且短期内出现大量资金收付</td>'+
			'<td class="css2">'+DQSH017+'</td>'+
		'</tr>'+				

		'<tr>'+
			'<td class="css1">账户是否存在分散转入集中转出或集中转入分散转出，且与经营背景、财务状况明显不符</td>'+
			'<td class="css2">'+DQSH030+'</td>'+
			'<td class="css3">账户是否平常资金流量小，突然有异常资金流入，且短期内出现大量资金收付</td>'+
			'<td class="css2">'+DQSH018+'</td>'+
		'</tr>'+				

		'<tr>'+
			'<td class="css1">账户资金是否快进快出，不留余额或少留余额</td>'+
			'<td class="css2">'+DQSH015+'</td>'+
			'<td class="css3">账户的跨境交易是否涉及贩毒、走私、恐怖活动、赌博严重地区或避税型离岸金融中心</td>'+
			'<td class="css2">'+DQSH019+'</td>'+
		'</tr>'+				

		'<tr>'+
			'<td class="css1">账户是否存在相同收付款人之间频繁发生交易，且金额接近大额交易标准</td>'+
			'<td class="css2">'+DQSH016+'</td>'+
			'<td class="css3">账户是否与关联企业之间频繁发生大额交易</td>'+
			'<td class="css2">'+DQSH034+'</td>'+
		'</tr>'+				

		'<tr>'+
			'<td class="css1">客户是否提前偿还贷款，且与其财务状况明显不符 </td>'+
			'<td class="css2">'+DQSH022+'</td>'+
			'<td class="css3">AUM(人民币)</td>'+
			'<td class="css2">'+CURRENT_AUM+'</td>'+
		'</tr>'+	

		'<tr>'+
			'<td class="css1">当前客户洗钱风险等级</td>'+
			'<td class="css2">'+CUST_GRADE+'</td>'+
			'<td class="css3">审核结果</td>'+
			'<td class="css2">'+CUST_GRADE_CHECK+'</td>'+
		'</tr>'+
		'<tr>'+
			'<td class="css1" >审核意见</td>'+
			'<td class="css2" colspan="3" >'+INSTRUCTION+'</td>'+
			'<td></td>'+
			'<td></td>'+
		'</tr>';		
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
	       			  		       			
	        	}else{
				html +='<tr><td colspan=6>没有反洗钱交易检测记录说明</td></tr>';
		        	}
  	          	html +='</table></div>';
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

