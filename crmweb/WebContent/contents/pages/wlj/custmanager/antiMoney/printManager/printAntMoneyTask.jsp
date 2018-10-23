<%@ page contentType="text/html; charset=utf-8"%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title>客户关系管理系统</title>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
	<meta name="keywords" content="客户关系管理系统,CRM" />
	<meta name="description" content="客户关系管理系统,CRM" />
	<meta name="Author" content="YuchengTech" />
	<link rel="shortcut icon" href="favicon.ico" />
	<%@ include file="/contents/pages/wlj/printManager/common/printInclude.jsp"%>
	<version:frameLink  type="text/css" rel="stylesheet" href="/contents/pages/wlj/custmanager/antiMoney/printManager/printAntMoneyTaskCss.css" />
	<%-- <version:frameScript type="text/javascript" src="/contents/pages/wlj/printManager/common/printParam.js"/> --%>
	<script type="text/javascript">
		debugger;
		//打印方法1
		function printPage() {
			window.print();
		}
		
		function PrintTable(Id){
			//打印方法2 应用中
            var mStr;
            mStr = window.document.body.innerHTML ;
            var mWindow = window;               
            window.document.body.innerHTML =Id.innerHTML;
            mWindow.print();
            window.document.body.innerHTML = mStr;
    }
		
		
		
		var instanceid= '<%=request.getParameter("instanceid")%>';
		var custId = '<%=request.getParameter("custId")%>';
		var custName = '<%=request.getParameter("custName")%>';
		var roleCodes='<%=request.getParameter("roleCodes")%>';
	

		
		
		 var CUST_ID='<%=request.getParameter("CUST_ID")==null?"":request.getParameter("CUST_ID")%>';	//"'&CUST_ID='+cond.CUST_ID+" +	//客户编号
		 var CUST_NAME='<%=request.getParameter("CUST_NAME")==null?"":request.getParameter("CUST_NAME")%>';	//"'&CUST_NAME='+cond.CUST_NAME+" +	//客户名称
		 var CORE_NO='<%=request.getParameter("CORE_NO")==null?"":request.getParameter("CORE_NO")%>';	//"'&CORE_NO='+cond.CORE_NO+" +	//核心客户号
		 var CITIZENSHIP='<%=request.getParameter("CITIZENSHIP")==null?"":request.getParameter("CITIZENSHIP")%>';	//"'&CITIZENSHIP='+cond.CITIZENSHIP+" +	//国籍
		 var BIRTHDAY='<%=request.getParameter("BIRTHDAY")==null?"":request.getParameter("BIRTHDAY")%>';	//"'&BIRTHDAY='+cond.BIRTHDAY+" +	//出生日期
		 var CAREER_TYPE='<%=request.getParameter("CAREER_TYPE")==null?"":request.getParameter("CAREER_TYPE")%>';	//"'&CAREER_TYPE='+cond.CAREER_TYPE+" +	//职业
		 var IDENT_TYPE1='<%=request.getParameter("IDENT_TYPE1")==null?"":request.getParameter("IDENT_TYPE1")%>';	//"'&IDENT_TYPE1='+cond.IDENT_TYPE1+" +	//证证件类型1
		 var INDENT_NO2='<%=request.getParameter("INDENT_NO2")==null?"":request.getParameter("INDENT_NO2")%>';	//"'&INDENT_NO2='+cond.INDENT_NO2+" +	//证件号2
		 var INDENT_NO1='<%=request.getParameter("INDENT_NO1")==null?"":request.getParameter("INDENT_NO1")%>';	//"'&INDENT_NO1='+cond.INDENT_NO1+" +	//证件号1
		 var IDENT_TYPE2='<%=request.getParameter("IDENT_TYPE2")==null?"":request.getParameter("IDENT_TYPE2")%>';	//"'&IDENT_TYPE2='+cond.IDENT_TYPE2+" +	//证件类型2
		 var IDENT_EXPIRED_DATE1='<%=request.getParameter("IDENT_EXPIRED_DATE1")==null?"":request.getParameter("IDENT_EXPIRED_DATE1")%>';	//"'&IDENT_EXPIRED_DATE1='+cond.IDENT_EXPIRED_DATE1+" +	//证件1到期日
		 var IDENT_EXPIRED_DATE2='<%=request.getParameter("IDENT_EXPIRED_DATE2")==null?"":request.getParameter("IDENT_EXPIRED_DATE2")%>';	//"'&IDENT_EXPIRED_DATE2='+cond.IDENT_EXPIRED_DATE2+" +	//证件2到期日
    	
    	
		 var FLAG_AGENT='<%=request.getParameter("FLAG_AGENT")==null?"":request.getParameter("FLAG_AGENT")%>';	//"'&FLAG_AGENT='+cond.FLAG_AGENT+" +	//是否为代理开户
    	
		 var AGENT_NAME='<%=request.getParameter("AGENT_NAME")==null?"":request.getParameter("AGENT_NAME")%>';	//"'&AGENT_NAME='+cond.AGENT_NAME+" +	//代理人姓名
		 var AGE_IDENT_TYPE='<%=request.getParameter("AGE_IDENT_TYPE")==null?"":request.getParameter("AGE_IDENT_TYPE")%>';	//"'&AGE_IDENT_TYPE='+cond.AGE_IDENT_TYPE+" +	//代理人证件类型
    	 var AGENT_NATION_CODE='<%=request.getParameter("AGENT_NATION_CODE")==null?"":request.getParameter("AGENT_NATION_CODE")%>';	//"'&AGENT_NATION_CODE='+cond.AGENT_NATION_CODE+" +	//代理人国籍
    	 var AGE_IDENT_NO='<%=request.getParameter("AGE_IDENT_NO")==null?"":request.getParameter("AGE_IDENT_NO")%>';	//"'&AGE_IDENT_NO='+cond.AGE_IDENT_NO+" +	//代理人证件号码
    	 var TEL='<%=request.getParameter("TEL")==null?"":request.getParameter("TEL")%>';	//"'&TEL='+cond.TEL+" +	//代理人联系电话
    	 var FXQ009='<%=request.getParameter("FXQ009")==null?"":request.getParameter("FXQ009")%>';	//"'&FXQ009='+cond.FXQ009+" +	//客户或其亲属、关系密切人等是否属于外国政要
    	 var FXQ008='<%=request.getParameter("FXQ008")==null?"":request.getParameter("FXQ008")%>';	//"'&FXQ008='+cond.FXQ008+" +	//客户是否涉及风险提示信息或权威媒体报道信息

    	 var FXQ007='<%=request.getParameter("FXQ007")==null?"":request.getParameter("FXQ007")%>';	//"'&FXQ007='+cond.FXQ007+" +	//客户办理的业务
    	 var DQSH001='<%=request.getParameter("DQSH001")==null?"":request.getParameter("DQSH001")%>';	//"'&DQSH001='+cond.DQSH001+" +	//证件是否过期
    	 var DQSH003='<%=request.getParameter("DQSH003")==null?"":request.getParameter("DQSH003")%>';	//"'&DQSH003='+cond.DQSH003+" +	//联系时间
    	 var DQSH004='<%=request.getParameter("DQSH004")==null?"":request.getParameter("DQSH004")%>';	//"'&DQSH004='+cond.DQSH004+" +	//联系人与帐户持有人的关系
    	 var DQSH002='<%=request.getParameter("DQSH002")==null?"":request.getParameter("DQSH002")%>';	//"'&DQSH002='+cond.DQSH002+" +	//客户是否可取得联系
    	 var DQSH037='<%=request.getParameter("DQSH037")==null?"":request.getParameter("DQSH037")%>';	//"'&DQSH037='+cond.DQSH037+" +	//联系人与帐户持有人的关系说明(占据一行) 
    	 var DQSH005='<%=request.getParameter("DQSH005")==null?"":request.getParameter("DQSH005")%>';	//"'&DQSH005='+cond.DQSH005+" +	//预计证件更新时间
    	 var DQSH006='<%=request.getParameter("DQSH006")==null?"":request.getParameter("DQSH006")%>';	//"'&DQSH006='+cond.DQSH006+" +	//未及时更新证件的理由
    	 var DQSH007='<%=request.getParameter("DQSH007")==null?"":request.getParameter("DQSH007")%>';	//"'&DQSH007='+cond.DQSH007+" +	//客户是否无正当理由拒绝更新证件
    	 var DQSH009='<%=request.getParameter("DQSH009")==null?"":request.getParameter("DQSH009")%>';	//"'&DQSH009='+cond.DQSH009+" +	//账户是否频繁发生大额现金交易
    	 var DQSH008='<%=request.getParameter("DQSH008")==null?"":request.getParameter("DQSH008")%>';	//"'&DQSH008='+cond.DQSH008+" +	//客户留存的证件及信息是否存在疑点或矛盾
    	 var DQSH010='<%=request.getParameter("DQSH010")==null?"":request.getParameter("DQSH010")%>';	//"'&DQSH010='+cond.DQSH010+" +	//账户是否频繁发生外币现钞存取业务
    	 var DQSH011='<%=request.getParameter("DQSH011")==null?"":request.getParameter("DQSH011")%>';	//"'&DQSH011='+cond.DQSH011+" +	//账户现金交易是否与客户职业特性相符
    	 var DQSH012='<%=request.getParameter("DQSH012")==null?"":request.getParameter("DQSH012")%>';	//"'&DQSH012='+cond.DQSH012+" +	//账户是否频繁发生大额的网上银行交易
    	 var DQSH013='<%=request.getParameter("DQSH013")==null?"":request.getParameter("DQSH013")%>';	//"'&DQSH013='+cond.DQSH013+" +	//账户是否与公司账户之间发生频繁或大额的交易
    	 var DQSH015='<%=request.getParameter("DQSH015")==null?"":request.getParameter("DQSH015")%>';	//"'&DQSH015='+cond.DQSH015+" +	//账户资金是否快进快出，不留余额或少留余额
    	 var DQSH014='<%=request.getParameter("DQSH014")==null?"":request.getParameter("DQSH014")%>';	//"'&DQSH014='+cond.DQSH014+" +	//账户是否存在分散转入集中转出或集中转入分散转出，且与客户身份、财务状况明显不符
    	 var DQSH018='<%=request.getParameter("DQSH018")==null?"":request.getParameter("DQSH018")%>';	//"'&DQSH018='+cond.DQSH018+" +	//账户是否平常资金流量小，突然有异常资金流入，且短期内出现大量资金收付
    	 var DQSH017='<%=request.getParameter("DQSH017")==null?"":request.getParameter("DQSH017")%>';	//"'&DQSH017='+cond.DQSH017+" +	//账户是否长期闲置不明原因地突然启用，且短期内出现大量资金收付
    	 var DQSH016='<%=request.getParameter("DQSH016")==null?"":request.getParameter("DQSH016")%>';	//"'&DQSH016='+cond.DQSH016+" +	//账户是否存在相同收付款人之间频繁发生交易，且金额接近大额交易标准
    	 var DQSH021='<%=request.getParameter("DQSH021")==null?"":request.getParameter("DQSH021")%>';	//"'&DQSH021='+cond.DQSH021+" +	//账户是否经常由他人代为办理业务
    	 var DQSH023='<%=request.getParameter("DQSH023")==null?"":request.getParameter("DQSH023")%>';	//"'&DQSH023='+cond.DQSH023+" +	//当前账户状态是否正常
    	 var DQSH020='<%=request.getParameter("DQSH020")==null?"":request.getParameter("DQSH020")%>';	//"'&DQSH020='+cond.DQSH020+" +	//账户是否频繁发生跨境交易，且金额大于1万美元
    	 var DQSH022='<%=request.getParameter("DQSH022")==null?"":request.getParameter("DQSH022")%>';	//"'&DQSH022='+cond.DQSH022+" +	//客户是否提前偿还贷款，且与其财务状况明显不符
    	 var DQSH019='<%=request.getParameter("DQSH019")==null?"":request.getParameter("DQSH019")%>';	//"'&DQSH019='+cond.DQSH019+" +	//账户的跨境交易是否涉及贩毒、走私、恐怖活动、赌博严重地区或避税型离岸金融中心
    	 
    	 var CURRENT_AUM='<%=request.getParameter("CURRENT_AUM")==null?"":request.getParameter("CURRENT_AUM")%>';	//"'&CURRENT_AUM='+cond.CURRENT_AUM+" +	//AUM(人民币)
    	 var DQSH024='<%=request.getParameter("DQSH024")==null?"":request.getParameter("DQSH024")%>';	//"'&DQSH024='+cond.DQSH024+" +	//AUM(人民币)改为  客户是否涉及反洗钱黑名单
     	
    	 var CUST_GRADE='<%=request.getParameter("CUST_GRADE")==null?"":request.getParameter("CUST_GRADE")%>';	//"'&CUST_GRADE='+cond.CUST_GRADE+" +	//当前客户洗钱风险等级
    	 var CUST_GRADE_CHECK='<%=request.getParameter("CUST_GRADE_CHECK")==null?"":request.getParameter("CUST_GRADE_CHECK")%>';	//"'&CUST_GRADE_CHECK='+cond.CUST_GRADE_CHECK+ +	//审核结果
    	 var INSTRUCTION='<%=request.getParameter("INSTRUCTION")==null?"":request.getParameter("INSTRUCTION")%>';	//"'&INSTRUCTION='+cond.INSTRUCTION+" +	//审核意见
    	 var IF_ORG_SUB_TYPE_PER='<%=request.getParameter("IF_ORG_SUB_TYPE_PER")==null?"":request.getParameter("IF_ORG_SUB_TYPE_PER")%>';	//"'&IF_ORG_SUB_TYPE_PER=+cond.IF_ORG_SUB_TYPE_PER+" ,IF_ORG_SUB_TYPE_PER //是否自贸区(对私)
    	
	</script>
	<version:frameScript type="text/javascript" src="/contents/pages/wlj/custmanager/antiMoney/printManager/printAntMoneyTask.js"/>

</head>
<body>
</body>
 <!-- <body>

	<div class="showPrintContent" >
		<div class="Noprint">
			<input type="button" class="button" value="打印"
				onclick="printPageCurr();">
			<div id="beforeEnd"></div>
		</div>
		<div id="showPrintView" >
			<div class="single">
			
				<div class="titleDiv" style="text-align: center;font-weight:bolder;font-size:18px">客户反洗钱指标信息</div>
			<hr/>
				<div class="title2" >
					客户号：110000106290 <span class="endDiv">打印时间：2016-03-01
						10:5:17</span>
				</div>
				<hr/>
				<table class="print_tab_01" >
					<tbody>
					
						共有（高中低）开始  
						<tr>
							<td width="300" class="css1">客户号</td>
							<td width="200" class="css2">CUST_ID</td>
							<td width="300" class="css3">客户姓名</td>
							<td width="200" class="css2">CUST_NAME</td>
						</tr>
						<tr>
							<td width="300" class="css1">核心客户号</td>
							<td class="css2">CORE_NO</td>
							<td width="300" class="css3">国籍</td>
							<td class="css2">CITIZENSHIP</td>
						</tr>
						<tr>
							<td width="300" class="css1">出生日期</td>
							<td class="css2">BIRTHDAY</td>
							<td width="300" class="css3">职业</td>
							<td class="css2">CAREER_TYPE</td>
						</tr>
						
						
						
						<tr>
							<td width="300" class="css1">证件1类型</td>
							<td class="css2">IDENT_TYPE1</td>
							<td width="300" class="css3">证件1类型 </td>
							<td class="css2">IDENT_TYPE2</td>
						</tr>
						<tr>
							<td width="300" class="css1">证件2号码</td>
							<td class="css2">INDENT_NO1</td>
							<td width="300" class="css3">证件2号码</td>
							<td class="css2">INDENT_NO2</td>
						</tr>
						<tr>
							<td class="css1">证件1到期日</td>
							<td class="css2">IDENT_EXPIRED_DATE1</td>
							<td class="css3">证件2到期日</td>
							<td class="css2">IDENT_EXPIRED_DATE2</td>
						</tr>
						<tr>
							<td  class="css1">客户是否为代理开户 </td>
							<td class="css2">FLAG_AGENT</td>
							<td class="css3">代理人姓名 </td>
							<td class="css2">AGENT_NAME</td>
						</tr>
						<tr>
							<td class="css1">代理人国家代码</td>
							<td class="css2">AGENT_NATION_CODE</td>
							<td class="css3">代理人联系电话 </td>
							<td class="css2">TEL</td>
						</tr>
						<tr>
							<td class="css1">代理人证件类型</td>
							<td class="css2">AGE_IDENT_TYPE</td>
							<td class="css3">代理人证件号码</td>
							<td class="css2">AGE_IDENT_NO</td>
						</tr>
						<tr>
							<td class="css1">客户或其亲属、关系密切人等是否属于外国政要</td>
							<td class="css2">"FXQ009"</td>
							<td class="css3">客户是否涉及风险提示信息或权威媒体报道信息</td>
							<td class="css2">FXQ008</td>
						</tr>
						<tr>
							<td class="css1">客户是否为黑名单客户</td>
							<td class="css2">DQSH024</td>
							<td class="css3">客户办理的业务</td>
							<td class="css2">FXQ007</td>
						</tr>
						<tr>
							<td class="css1">证件是否过期</td>
							<td class="css2">DQSH001</td>
							<td class="css3">联系时间</td>
							<td class="css2">DQSH003</td>
						</tr>
						<tr>
							<td class="css1">联系人与帐户持有人的关系</td>
							<td class="css2">DQSH004</td>
							<td class="css3">客户是否可取得联系</td>
							<td class="css2">DQSH002</td>
						</tr>
						<tr>
							<td class="css1" >联系人与帐户持有人的关系说明</td>
							<td class="css2" colspan="3" >DQSH037</td>
							<td></td>
							<td></td>
						</tr>
						<tr>
							<td class="css1">预计证件更新时间</td>
							<td class="css2">DQSH005</td>
							<td class="css3">未及时更新证件的理由</td>
							<td class="css2">DQSH006</td>
						</tr>
						<tr>
							<td class="css1">客户是否无正当理由拒绝更新证件</td>
							<td class="css2">DQSH007</td>
							<td class="css3">账户是否频繁发生大额现金交易</td>
							<td class="css2">DQSH009</td>
						</tr>
						<tr>
							<td class="css1">客户留存的证件及信息是否存在疑点或矛盾</td>
							<td class="css2">DQSH008</td>
							<td class="css3">账户是否频繁发生外币现钞存取业务</td>
							<td class="css2">DQSH010</td>
						</tr>
						
						<tr>
							<td class="css1">账户现金交易是否与客户职业特性相符</td>
							<td class="css2">DQSH011</td>
							<td class="css3">账户是否频繁发生大额的网上银行交易</td>
							<td class="css2">DQSH012</td>
						</tr>
						<tr>
							<td class="css1">账户是否与公司账户之间发生频繁或大额的交易</td>
							<td class="css2">DQSH013</td>
							<td class="css3">账户资金是否快进快出，不留余额或少留余额</td>
							<td class="css2">DQSH015</td>
						</tr>
						<tr>
							<td class="css1">账户是否存在分散转入集中转出或集中转入分散转出，且与客户身份、财务状况明显不符</td>
							<td class="css2">DQSH014</td>
							<td class="css3">账户是否平常资金流量小，突然有异常资金流入，且短期内出现大量资金收付</td>
							<td class="css2">DQSH018</td>
						</tr>
						<tr>
							<td class="css1">账户是否长期闲置不明原因地突然启用，且短期内出现大量资金收付</td>
							<td class="css2">DQSH017</td>
							<td class="css3">账户是否存在相同收付款人之间频繁发生交易，且金额接近大额交易标准</td>
							<td class="css2">DQSH016</td>
						</tr>
						<tr>
							<td class="css1">账户是否经常由他人代为办理业务</td>
							<td class="css2">DQSH021</td>
							<td class="css3">当前账户状态是否正常</td>
							<td class="css2">DQSH023</td>
						</tr>
						<tr>
							<td class="css1">账户是否频繁发生跨境交易，且金额大于1万美元</td>
							<td class="css2">DQSH020</td>
							<td class="css3">客户是否提前偿还贷款，且与其财务状况明显不符</td>
							<td class="css2">DQSH022</td>
						</tr><tr>
							<td class="css1">账户的跨境交易是否涉及贩毒、走私、恐怖活动、赌博严重地区或避税型离岸金融中心</td>
							<td class="css2">DQSH019</td>
							<td class="css3">AUM(人民币)</td>
							<td class="css2">DQSH024</td>
						</tr>
						<tr>
							<td class="css1">当前客户洗钱风险等级</td>
							<td class="css2">CUST_GRADE</td>
							<td class="css3">审核结果</td>
							<td class="css2">CUST_GRADE_CHECK</td>
						</tr>
						<tr>
							<td class="css1" >审核意见</td>
							<td class="css2" colspan="3" >INSTRUCTION</td>
							<td></td>
							<td></td>
						</tr>
						
					
						
						
						
						
						
					</tbody>
				</table>
				<div style=" margin-top:10px; ">
						<table class="print_tab_02">
								<tr>
									<th width="500" align="center">反洗钱交易监测记录说明</th>
									<th width="150" align="center">录入时间</th>
									<th width="150" align="center">上报可疑交易报告时间</th>
									<th width="80" align="center">录入人员</th>
								</tr>
								<tr>
									<td align="left">1</td>
									<td align="center">2016-02-17 16:04:04</td>
									<td align="center">2016-2-16</td>
									<td align="center">舒一婷</td>
								</tr>
						</table>
				
				</div>
				
				
			</div>
		</div>
		</div>
</body>  -->

<!-- 在页面中要打印 -->
<body>

</body>
</html>