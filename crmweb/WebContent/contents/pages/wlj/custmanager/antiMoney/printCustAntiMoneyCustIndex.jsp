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
	<version:frameScript type="text/javascript" src="/contents/pages/wlj/printManager/common/printParam.js"/>
	<script type="text/javascript">
		debugger;
		function PrintTable(Id){
			//打印方法2 应用中
            var mStr;
            mStr = window.document.body.innerHTML;
            var mWindow = window;               
            window.document.body.innerHTML =Id.innerHTML;
            mWindow.print();
            window.document.body.innerHTML = mStr;
    }
		
		var custId = '<%=request.getParameter("custId")%>';
		var custName = '<%=request.getParameter("custName")%>';
		var roleCodes='<%=request.getParameter("roleCodes")%>';
		<%--	乱码问题自解决方案
			var CUST_ID='<%=new String(request.getParameter("custName").getBytes("ISO8859-1"),"UTF-8")==null?"":new String(request.getParameter("custName").getBytes("ISO8859-1"),"UTF-8")%>';
		 --%>
		var CUST_ID='<%=request.getParameter("CUST_ID")==null?"":request.getParameter("CUST_ID")%>';
		var CORE_NO='<%=request.getParameter("CORE_NO")==null?"":request.getParameter("CORE_NO")%>';
		var CUST_NAME='<%=request.getParameter("CUST_NAME")==null?"":request.getParameter("CUST_NAME")%>';
		var CUST_TYPE='<%=request.getParameter("CUST_TYPE")==null?"":request.getParameter("CUST_TYPE")%>';
		var IDENT_TYPE1='<%=request.getParameter("IDENT_TYPE1")==null?"":request.getParameter("IDENT_TYPE1")%>';
		var INDENT_NO1='<%=request.getParameter("INDENT_NO1")==null?"":request.getParameter("INDENT_NO1")%>';
		var IDENT_EXPIRED_DATE1='<%=request.getParameter("IDENT_EXPIRED_DATE1")==null?"":request.getParameter("IDENT_EXPIRED_DATE1")%>';
		var IDENT_TYPE2='<%=request.getParameter("IDENT_TYPE2")==null?"":request.getParameter("IDENT_TYPE2")%>';
		var INDENT_NO2='<%=request.getParameter("INDENT_NO2")==null?"":request.getParameter("INDENT_NO2")%>';
		var IDENT_EXPIRED_DATE2='<%=request.getParameter("IDENT_EXPIRED_DATE2")==null?"":request.getParameter("IDENT_EXPIRED_DATE2")%>';
		var FLAG_AGENT='<%=request.getParameter("FLAG_AGENT")==null?"":request.getParameter("FLAG_AGENT")%>';
		var AGENT_NAME='<%=request.getParameter("AGENT_NAME")==null?"":request.getParameter("AGENT_NAME")%>';
		var AGENT_NATION_CODE='<%=request.getParameter("AGENT_NATION_CODE")==null?"":request.getParameter("AGENT_NATION_CODE")%>';
		var AGE_IDENT_TYPE='<%=request.getParameter("AGE_IDENT_TYPE")==null?"":request.getParameter("AGE_IDENT_TYPE")%>';
		var AGE_IDENT_NO='<%=request.getParameter("AGE_IDENT_NO")==null?"":request.getParameter("AGE_IDENT_NO")%>';
		var TEL='<%=request.getParameter("TEL")==null?"":request.getParameter("TEL")%>';
		var FXQ008='<%=request.getParameter("FXQ008")==null?"":request.getParameter("FXQ008")%>';
		var CUST_GRADE='<%=request.getParameter("CUST_GRADE")==null?"":request.getParameter("CUST_GRADE")%>';
		var DQSH001='<%=request.getParameter("DQSH001")==null?"":request.getParameter("DQSH001")%>';
		var DQSH002='<%=request.getParameter("DQSH002")==null?"":request.getParameter("DQSH002")%>';
		var DQSH003='<%=request.getParameter("DQSH003")==null?"":request.getParameter("DQSH003")%>';
		var DQSH004='<%=request.getParameter("DQSH004")==null?"":request.getParameter("DQSH004")%>';
		var DQSH005='<%=request.getParameter("DQSH005")==null?"":request.getParameter("DQSH005")%>';
		var DQSH006='<%=request.getParameter("DQSH006")==null?"":request.getParameter("DQSH006")%>';
		var DQSH007='<%=request.getParameter("DQSH007")==null?"":request.getParameter("DQSH007")%>';
		var DQSH008='<%=request.getParameter("DQSH008")==null?"":request.getParameter("DQSH008")%>';
		var DQSH009='<%=request.getParameter("DQSH009")==null?"":request.getParameter("DQSH009")%>';
		var DQSH010='<%=request.getParameter("DQSH010")==null?"":request.getParameter("DQSH010")%>';
		var DQSH011='<%=request.getParameter("DQSH011")==null?"":request.getParameter("DQSH011")%>';
		var DQSH012='<%=request.getParameter("DQSH012")==null?"":request.getParameter("DQSH012")%>';
		var DQSH013='<%=request.getParameter("DQSH013")==null?"":request.getParameter("DQSH013")%>';
		var DQSH014='<%=request.getParameter("DQSH014")==null?"":request.getParameter("DQSH014")%>';
		var DQSH015='<%=request.getParameter("DQSH015")==null?"":request.getParameter("DQSH015")%>';
		var DQSH016='<%=request.getParameter("DQSH016")==null?"":request.getParameter("DQSH016")%>';
		var DQSH017='<%=request.getParameter("DQSH017")==null?"":request.getParameter("DQSH017")%>';
		var DQSH018='<%=request.getParameter("DQSH018")==null?"":request.getParameter("DQSH018")%>';
		var DQSH019='<%=request.getParameter("DQSH019")==null?"":request.getParameter("DQSH019")%>';
		var DQSH020='<%=request.getParameter("DQSH020")==null?"":request.getParameter("DQSH020")%>';
		var DQSH021='<%=request.getParameter("DQSH021")==null?"":request.getParameter("DQSH021")%>';
		var DQSH022='<%=request.getParameter("DQSH022")==null?"":request.getParameter("DQSH022")%>';
		var DQSH023='<%=request.getParameter("DQSH023")==null?"":request.getParameter("DQSH023")%>';
		var CURRENT_AUM='<%=request.getParameter("CURRENT_AUM")==null?"":request.getParameter("CURRENT_AUM")%>';
		
		var DQSH024='<%=request.getParameter("DQSH024")==null?"":request.getParameter("DQSH024")%>';
		
		var DQSH025='<%=request.getParameter("DQSH025")==null?"":request.getParameter("DQSH025")%>';
		var DQSH026='<%=request.getParameter("DQSH026")==null?"":request.getParameter("DQSH026")%>';
		var DQSH027='<%=request.getParameter("DQSH027")==null?"":request.getParameter("DQSH027")%>';
		var DQSH028='<%=request.getParameter("DQSH028")==null?"":request.getParameter("DQSH028")%>';
		var DQSH029='<%=request.getParameter("DQSH029")==null?"":request.getParameter("DQSH029")%>';
		var DQSH030='<%=request.getParameter("DQSH030")==null?"":request.getParameter("DQSH030")%>';
		var DQSH031='<%=request.getParameter("DQSH031")==null?"":request.getParameter("DQSH031")%>';
		var DQSH032='<%=request.getParameter("DQSH032")==null?"":request.getParameter("DQSH032")%>';
		var DQSH033='<%=request.getParameter("DQSH033")==null?"":request.getParameter("DQSH033")%>';
		var DQSH034='<%=request.getParameter("DQSH034")==null?"":request.getParameter("DQSH034")%>';
		var DQSH0351='<%=request.getParameter("DQSH0351")==null?"":request.getParameter("DQSH0351")%>';
		var DQSH0352='<%=request.getParameter("DQSH0352")==null?"":request.getParameter("DQSH0352")%>';
		var DQSH0361='<%=request.getParameter("DQSH0361")==null?"":request.getParameter("DQSH0361")%>';
		var DQSH0362='<%=request.getParameter("DQSH0362")==null?"":request.getParameter("DQSH0362")%>';
		var CITIZENSHIP='<%=request.getParameter("CITIZENSHIP")==null?"":request.getParameter("CITIZENSHIP")%>';
		var CAREER_TYPE='<%=request.getParameter("CAREER_TYPE")==null?"":request.getParameter("CAREER_TYPE")%>';
		var BIRTHDAY='<%=request.getParameter("BIRTHDAY")==null?"":request.getParameter("BIRTHDAY")%>';
		var IF_ORG_SUB_TYPE_PER='<%=request.getParameter("IF_ORG_SUB_TYPE_PER")==null?"":request.getParameter("IF_ORG_SUB_TYPE_PER")%>';
		var FXQ007='<%=request.getParameter("FXQ007")==null?"":request.getParameter("FXQ007")%>';
		var FXQ009='<%=request.getParameter("FXQ009")==null?"":request.getParameter("FXQ009")%>';
		var BUILD_DATE='<%=request.getParameter("BUILD_DATE")==null?"":request.getParameter("BUILD_DATE")%>';
		var IF_ORG_SUB_TYPE_ORG='<%=request.getParameter("IF_ORG_SUB_TYPE_ORG")==null?"":request.getParameter("IF_ORG_SUB_TYPE_ORG")%>';
		var NATION_CODE='<%=request.getParameter("NATION_CODE")==null?"":request.getParameter("NATION_CODE")%>';
		var ENT_SCALE_CK='<%=request.getParameter("ENT_SCALE_CK")==null?"":request.getParameter("ENT_SCALE_CK")%>';
		var IN_CLL_TYPE='<%=request.getParameter("IN_CLL_TYPE")==null?"":request.getParameter("IN_CLL_TYPE")%>';
		var FXQ021='<%=request.getParameter("FXQ021")==null?"":request.getParameter("FXQ021")%>';
		var FXQ022='<%=request.getParameter("FXQ022")==null?"":request.getParameter("FXQ022")%>';
		var FXQ023='<%=request.getParameter("FXQ023")==null?"":request.getParameter("FXQ023")%>';
		var FXQ024='<%=request.getParameter("FXQ024")==null?"":request.getParameter("FXQ024")%>';
		var FXQ025='<%=request.getParameter("FXQ025")==null?"":request.getParameter("FXQ025")%>';
		var FXQ010='<%=request.getParameter("FXQ010")==null?"":request.getParameter("FXQ010")%>';
		var FXQ012='<%=request.getParameter("FXQ012")==null?"":request.getParameter("FXQ012")%>';
		var FXQ013='<%=request.getParameter("FXQ013")==null?"":request.getParameter("FXQ013")%>';
		var FXQ015='<%=request.getParameter("FXQ015")==null?"":request.getParameter("FXQ015")%>';
		var FXQ016='<%=request.getParameter("FXQ016")==null?"":request.getParameter("FXQ016")%>';
		var FXQ014='<%=request.getParameter("FXQ014")==null?"":request.getParameter("FXQ014")%>';
		var FXQ026='<%=request.getParameter("FXQ026")==null?"":request.getParameter("FXQ026")%>';
		var FXQ011='<%=request.getParameter("FXQ011")==null?"":request.getParameter("FXQ011")%>';
	/* 	var turl = '?custId=' + custId+'&custName='+custName+'&roleCodes='+roleCodes;//+'&UPDATE_ITEM='+cond.UPDATE_ITEM+'&UPDATE_USER='+cond.UPDATE_USER+'&START_DATE='+cond.START_DATE+'&END_DATE='+cond.END_DATE;
		turl +='&=CUST_ID'+cond.CUST_ID+
			'&=CORE_NO'+cond.CORE_NO+
			'&=CUST_NAME'+cond.CUST_NAME+
			'&=CUST_TYPE'+cond.CUST_TYPE+
			'&=IDENT_TYPE1'+cond.IDENT_TYPE1+
			'&=INDENT_NO1'+cond.INDENT_NO1+
			'&=IDENT_EXPIRED_DATE1'+cond.IDENT_EXPIRED_DATE1+
			'&=IDENT_EXPIRED_DATE1'+cond.IDENT_EXPIRED_DATE1+
			'&=INDENT_NO2'+cond.INDENT_NO2+
			'&=IDENT_EXPIRED_DATE2'+cond.IDENT_EXPIRED_DATE2+
			'&=FLAG_AGENT'+cond.FLAG_AGENT+
			'&=AGENT_NAME'+cond.AGENT_NAME+
			'&=AGENT_NATION_CODE'+cond.AGENT_NATION_CODE+
			'&=AGE_IDENT_TYPE'+cond.AGE_IDENT_TYPE+
			'&=AGE_IDENT_NO'+cond.AGE_IDENT_NO+
			'&=TEL'+cond.TEL+
			'&=FXQ008'+cond.FXQ008+
			'&=CUST_GRADE'+cond.CUST_GRADE+
			'&=DQSH001'+cond.DQSH001+
			'&=DQSH002'+cond.DQSH002+
			'&=DQSH003'+cond.DQSH003+
			'&=DQSH004'+cond.DQSH004+
			'&=DQSH005'+cond.DQSH005+
			'&=DQSH006'+cond.DQSH006+
			'&=DQSH007'+cond.DQSH007+
			'&=DQSH008'+cond.DQSH008+
			'&=DQSH009'+cond.DQSH009+
			'&=DQSH010'+cond.DQSH010+
			'&=DQSH011'+cond.DQSH011+
			'&=DQSH012'+cond.DQSH012+
			'&=DQSH013'+cond.DQSH013+
			'&=DQSH014'+cond.DQSH014+
			'&=DQSH015'+cond.DQSH015+
			'&=DQSH016'+cond.DQSH016+
			'&=DQSH017'+cond.DQSH017+
			'&=DQSH018'+cond.DQSH018+
			'&=DQSH019'+cond.DQSH019+
			'&=DQSH020'+cond.DQSH020+
			'&=DQSH021'+cond.DQSH021+
			'&=DQSH022'+cond.DQSH022+
			'&=DQSH023'+cond.DQSH023+
			'&=DQSH024'+cond.DQSH024+
			'&=DQSH025'+cond.DQSH025+
			'&=DQSH026'+cond.DQSH026+
			'&=DQSH027'+cond.DQSH027+
			'&=DQSH028'+cond.DQSH028+
			'&=DQSH029'+cond.DQSH029+
			'&=DQSH030'+cond.DQSH030+
			'&=DQSH031'+cond.DQSH031+
			'&=DQSH032'+cond.DQSH032+
			'&=DQSH033'+cond.DQSH033+
			'&=DQSH034'+cond.DQSH034+
			'&=DQSH0351'+cond.DQSH0351+
			'&=DQSH0352'+cond.DQSH0352+
			'&=DQSH0361'+cond.DQSH0361+
			'&=DQSH0362'+cond.DQSH0362+
			'&=CITIZENSHIP'+cond.CITIZENSHIP+
			'&=CAREER_TYPE'+cond.CAREER_TYPE+
			'&=BIRTHDAY'+cond.BIRTHDAY+
			'&=IF_ORG_SUB_TYPE_PER'+cond.IF_ORG_SUB_TYPE_PER+
			'&=FXQ007'+cond.FXQ007+
			'&=FXQ009'+cond.FXQ009+
			'&=BUILD_DATE'+cond.BUILD_DATE+
			'&=IF_ORG_SUB_TYPE_ORG'+cond.IF_ORG_SUB_TYPE_ORG+
			'&=NATION_CODE'+cond.NATION_CODE+
			'&=ENT_SCALE_CK'+cond.ENT_SCALE_CK+
			'&=IN_CLL_TYPE'+cond.IN_CLL_TYPE+
			'&=FXQ021'+cond.FXQ021+
			'&=FXQ022'+cond.FXQ022+
			'&=FXQ023'+cond.FXQ023+
			'&=FXQ024'+cond.FXQ024+
			'&=FXQ025'+cond.FXQ025+
			'&=FXQ010'+cond.FXQ010+
			'&=FXQ012'+cond.FXQ012+
			'&=FXQ013'+cond.FXQ013+
			'&=FXQ013'+cond.FXQ013+
			'&=FXQ015'+cond.FXQ015+
			'&=FXQ016'+cond.FXQ016;
			
		 */
		
		
	</script>
	<version:frameScript type="text/javascript" src="/contents/pages/wlj/custmanager/antiMoney/printCustAntiMoneyCustIndex.js"/>
</head>
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
							<td width="300" class="css1">客户号</th>
							<td width="200" class="css2">CUST_ID</th>
							<td width="300" class="css3">客户姓名</th>
							<td width="200" class="css2">CUST_NAME</th>
						</tr>
						<tr>
							<td width="300" class="css1">核心客户号</td>
							<td class="css2">CORE_NO</td>
							<td width="300" class="css2">客户类型</td>
							<td class="css2">CUST_TYPE</td>
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
							<td class="css1">客户是否涉及反洗钱黑名单</td>
							<td class="css2">""</td>
							<td class="css3">是客户是否涉及风险提示信息或权威</td>
							<td class="css2">FXQ008</td>
						</tr>
						<tr>
							<td class="css1">当前客户洗钱风险等级</td>
							<td class="css2">CUST_GRADE</td>
							<td class="css3">以上是公共字段</td>
							<td class="css2"></td>
						</tr>
						共有（高中低）结束
						
						
												<tr>
							<td class="css1">个人(高中低) 开始</td>
							<td class="css2">----------</td>
							<td class="css3">-----------</td>
							<td class="css2">---------</td>
						</tr>
						  个人(高中低) 开始
						<tr>
							<td class="css1">国籍</td>
							<td class="css2">CITIZENSHIP</td>
							<td class="css3" >职业  </td>
							<td class="css2" >CAREER_TYPE</td>
						</tr>
						<tr>
							<td class="css1">出生年月日</td>
							<td class="css2">BIRTHDAY</td>
							<td class="css3">客户是否为自贸区客户</td>
							<td class="css2">IF_ORG_SUB_TYPE_PER</td>
						</tr>
						<tr>
							<td class="css1">客户在我行办理的业务包括</td>
							<td class="css2" >FXQ007</td>style="border-top: 1px #ccc solid;" colspan="3"
							<td class="css3">客户或其亲属、关系密切人是否属于外国政要</td>
							<td class="css2">FXQ009</td>
							
						</tr>
						
						个人(高中低)结束
						<tr>
							<td class="css1">-- 个人(高中)开始 -</td>
							<td class="css2">---------------</td>
							<td class="css3">-------------</td>
							<td class="css2">--------------------------</td>
						</tr>
						
						个人(高中)开始
						<tr>
							<td class="css1">证件是否过期</td>
							<td class="css2">DQSH001</td>
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
							<td class="css3">-账户是否存在分散转入集中转出或集中转入分散转出，且与客户身份、财务状况明显不符</td>
							<td class="css2">DQSH014</td>
						</tr>
						<tr>
							<td class="css1">账户是否频繁发生跨境交易，且金额大于1万美元 </td>
							<td class="css2">DQSH020</td>
							<td class="css3">账户是否经常由他人代为办理业务</td>
							<td class="css2">DQSH021</td>
						</tr>
						个人(高中)结束
						
						<tr>
							<td class="css1">//企业高中低</td>
							<td class="css2">--------------</td>
							<td class="css3">------------</td>
							<td class="css2">------------------</td>
						</tr>
							企业(高中低)开始
						<tr>
							<td class="css1">成立日期</td>
							<td class="css2">BUILD_DATE</td>
							<td class="css3">客户是否为自贸区客户</td>
							<td class="css2">IF_ORG_SUB_TYPE_ORG</td>
						</tr>
						<tr>
							<td class="css1">注册地</td>
							<td class="css2">NATION_CODE</td>
							<td class="css3">企业规模</td>
							<td class="css2">ENT_SCALE_CK</td>
						</tr>
						<tr>
							<td class="css1">行业分类</td>
							<td class="css2">IN_CLL_TYPE</td>
							<td class="css3">与客户建立业务关系的渠道</td>
							<td class="css2">FXQ021</td>
						</tr>
						<tr>
							<td class="css1">是否在规范证券市场上市</td>
							<td class="css2">FXQ022</td>
							<td class="css3">客户的股权或控制权结构</td>
							<td class="css2">FXQ023</td>
						</tr>
						<tr>
							<td class="css1">客户是否存在隐名股东或匿名股东</td>
							<td class="css2">FXQ024</td>
							<td class="css3">客户在我行办理的业务包括</td>
							<td class="css2">FXQ025</td>
						</tr>
						企业(高中低)结束
						<tr>
							<td class="css1">企业(高中)开始</td>
							<td class="css2">--------------</td>
							<td class="css3">--------------</td>
							<td class="css2">----------</td>
						</tr>
						
						企业(高中)开始
						
						<tr>
							<td class="css1">企业证件是否过期</td>
							<td class="css2">DQSH025</td>
							<td class="css3">法定代表人证件是否过期</td>
							<td class="css2">DQSH026</td>
						</tr>
						
						<tr>
							<td class="css1">联系人证件是否过期</td>
							<td class="css2">DQSH027</td>
							<td class="css3">联系人的身份</td>
							<td class="css2">DQSH028</td>
						</tr>
						<tr>
							<td class="css1">账户是否与自然人账户之间发生频繁或大额的交易</td>
							<td class="css2">DQSH029</td>
							<td class="css3">账户是否存在分散转入集中转出或集中转入分散转出，且与经营背景、财务状况明显不符</td>
							<td class="css2">DQSH030</td>
						</tr>
						<tr>
							<td class="css1">账户是否频繁收取与其经营业务明显无关的汇款</td>
							<td class="css2">DQSH031</td>
							<td class="css3">账户资金交易频度、金额是否与其经营背景相符</td>
							<td class="css2">DQSH032</td>
						</tr>
						<tr>
							<td class="css1">账户交易对手及资金用途是否与其经营背景相符</td>
							<td class="css2">DQSH033</td>
							<td class="css3">账户是否与关联企业之间频繁发生大额交易</td>
							<td class="css2">DQSH034</td>
						</tr>
						
						企业(高中)结束
						
						
							<tr>
							<td class="css1">--------</td>
							<td class="css2">--------</td>
							<td class="css3">--------</td>
							<td class="css2">--------</td>
						</tr>
						共有（高中）开始
						<tr>
							<td class="css1">客户是否可取得联系</td>
							<td class="css2">DQSH002</td>
							<td class="css3">联系时间</td>
							<td class="css2">DQSH003</td>
						</tr>
						<tr>
							<td class="css1">联系人与帐户持有人的关系</td>
							<td class="css2">DQSH004</td>
							<td class="css3">预计证件更新时间</td>
							<td class="css2">DQSH005</td>
						</tr>
						<tr>
							<td class="css1">未及时更新证件的理由</td>
							<td class="css2">DQSH006</td>
							<td class="css3">客户是否无正当理由拒绝更新证件</td>
							<td class="css2">DQSH007</td>
						</tr>
						<tr>
							<td class="css1">客户留存的证件及信息是否存在疑点或矛盾</td>
							<td class="css2">DQSH008</td>
							<td class="css3">账户是否频繁发生大额现金交易</td>
							<td class="css2">DQSH009</td>
						</tr>
						<tr>
							<td class="css1">账户资金是否快进快出，不留余额或少留余额</td>
							<td class="css2">DQSH015</td>
							<td class="css3">账户是否存在相同收付款人之间频繁发生交易，且金额接近大额交易标准</td>
							<td class="css2">DQSH016</td>
						</tr>
						<tr>
							<td class="css1">账户是否长期闲置不明原因地突然启用，且短期内出现大量资金收付</td>
							<td class="css2">DQSH017</td>
							<td class="css3">账户是否平常资金流量小，突然有异常资金流入，且短期内出现大量资金收付</td>
							<td class="css2">DQSH018</td>
						</tr>
						<tr>
							<td class="css1">账户的跨境交易是否涉及贩毒、走私、恐怖活动、赌博严重地区或避税型离岸金融中心</td>
							<td class="css2">DQSH019</td>
							<td class="css3">客户是否提前偿还贷款，且与其财务状况明显不符</td>
							<td class="css2">DQSH022</td>
						</tr>
						<tr>
							<td class="css1">当前账户状态是否正常</td>
							<td class="css2">DQSH023</td>
							<td class="css3">AUM(人民币) 对公对私</td>
							<td class="css2">DQSH024</td>
						</tr>
						
						
						共有（高中）结束

						
						
						
						
						
						<tr>
							<td class="css1">合规处开始</td>
							<td class="css2">-------------</td>
							<td class="css3">-------------</td>
							<td class="css2">-------------</td>
						</tr>
						合规处开始
						<tr>
							<td class="css1">是否发生具有异常特征的大额现金交易</td>
							<td class="css2">FXQ012</td>
							<td class="css3">是否发生具有异常特征的非面对面交易</td>
							<td class="css2">FXQ013</td>
						</tr>
						<tr>
							<td class="css1">是否存在多次涉及跨境异常交易报告</td>
							<td class="css2">FXQ014</td>
							<td class="css3">是否频繁进行异常交易</td>
							<td class="css2">FXQ016</td>
						</tr>
						<tr>
							<td class="css1">反洗钱交易监测记录</td>
							<td class="css2">FXQ010 FXQ015</td>
							<td class="css3">代办业务是否存在异常情况</td>
							<td class="css2">FXQ015</td>
						</tr>
						合规处结束
						
						
						
						
						
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
</body> -->
</html>