<%@ page contentType="text/html; charset=utf-8"%>
<%@ include file="/contents/pages/common/includes.jsp"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <SCRIPT type="text/JavaScript"	
	src="<%=request.getContextPath()%>/contents/pages/customer/serviceManage/financeTools/WinControl.js" language="JavaScript"> </SCRIPT>
	
<SCRIPT type="text/JavaScript"	
	src="<%=request.getContextPath()%>/contents/pages/customer/serviceManage/financeTools/Calendar.js" language="JavaScript"> </SCRIPT>
	
<SCRIPT type="text/JavaScript"	
	src="<%=request.getContextPath()%>/contents/pages/customer/serviceManage/financeTools/Calculator.js" language="JavaScript"> </SCRIPT>
	
<SCRIPT type="text/JavaScript"	
	src="<%=request.getContextPath()%>/contents/pages/customer/serviceManage/financeTools/WBselect.js" language="JavaScript"> </SCRIPT>
<SCRIPT type="text/JavaScript"	
	src="<%=request.getContextPath()%>/contents/pages/customer/serviceManage/financeTools/GetRate.js" language="JavaScript"> </SCRIPT>
	
	
<SCRIPT type="text/JavaScript"	
	src="<%=request.getContextPath()%>/contents/pages/customer/serviceManage/financeTools/Components.js" language="JavaScript"> </SCRIPT>
	
<SCRIPT type="text/JavaScript"	
	src="<%=request.getContextPath()%>/contents/pages/customer/serviceManage/financeTools/CheckDataFunction.js" language="JavaScript"> </SCRIPT>
	
<SCRIPT type="text/JavaScript"	
	src="<%=request.getContextPath()%>/contents/pages/customer/serviceManage/financeTools/a1.js" language="JavaScript"> </SCRIPT>
	<SCRIPT type="text/JavaScript"	
	src="<%=request.getContextPath()%>/contents/pages/customer/serviceManage/financeTools/CalcLoanAdv.js" language="JavaScript"> </SCRIPT>

   <LINK href="<%=request.getContextPath()%>/contents/pages/customer/serviceManage/financeTools/Style.css" type="text/css" rel="STYLESHEET">
   
   </HEAD>
  <body text="#000000" bottomMargin="0" bgColor="#ffffff" leftMargin="0" topMargin="0" rightMargin="0" MS_POSITIONING="GridLayout">
<SCRIPT LANGUAGE="JavaScript">
// var cImage; 
// cImage = new Image; 
// cImage.src = "http://c.msn.com.cn/c.gif?DI=6689&PI=33235&TP=http://www.msn.com.cn/msnmobile/msnfashiontemplate/Default.asp&PS=70635&NA=1154&NC=10009" 
</SCRIPT> 
<NOSCRIPT>
<img height="1" width="1" src="http://c.msn.com.cn/c.gif?DI=6689&PI=33235&TP=http://www.msn.com.cn/msnmobile/msnfashiontemplate/Default.asp&PS=70635&NA=1154&NC=10009">
</NOSCRIPT>

<!-- START WRating v1.0 -->
<script type="text/javascript" src="http://msn.wrating.com/a1.js">
</script>
<script type="text/javascript">
//var vjAcc="860010-0218010200";
//var wrUrl="http://msn.wrating.com/";
//vjTrack("");
</script>
<noscript><img src="http://msn.wrating.com/a.gif?a=&c=860010-0218010200" width="1" height="1"/></noscript>
<!-- END WRating v1.0 -->
    <form id="LoanAdvCalc" name="LoanAdvCalc" action="LoanAdvCalc.aspx" method="post">
    <!-- <div id="Div1"><IMG src="../Images/bg1.gif"><IMG height="126" src="../Images/bg2.gif" width="415"><IMG src="../Images/bg3.gif"></div>
      <div id="Div2"><IMG height="200" src="../Images/bg4.gif" width="25"><IMG height="200" src="../Images/bg5.gif" width="640"><IMG height="200" src="../Images/bg6.gif" width="25"></div>
      <div id="Div3"><IMG src="../Images/bg7.gif"><IMG height="119" src="../Images/bg8.gif" width="640"><IMG src="../Images/bg9.gif"></div> -->  
      <div class="FDiv1" id="FDiv1" style="LEFT:280px;color:black">【提前还款计算器】</div>
      <div class="FDiv2" id="FDiv2" style="LEFT:30px; WIDTH:640px; TOP:70px">在按揭一段时间后（等额本息还款），如果客户的收入水平有了较大的提高，则可以考虑提前偿还部分或全部的贷款，以减少还款利息的支出。</div>
      <div class="FDiv3" id="Div6">
        <TABLE cellSpacing="0" cellPadding="0" width="656" border="0" ms_2d_layout="TRUE">
          <TBODY>
            <TR vAlign="top">
              <TD>
                <div id="bg1">
                  <TABLE cellSpacing="0" cellPadding="0" width="649" border="0" ms_2d_layout="TRUE" id="TABLE2">
                    <TR vAlign="top">
                      <table id="Table1" width="607" border="0">
                        <tr>
                          <td width="157" style="display:NONE">贷款类型</td>
                          <td width="168" style="display:NONE"><select id="edType" onchange="TypeChange()" name="edType">
                              <option value="1" selected>商业贷款</option>
                              <option value="2">公积金贷款</option>
                            </select></td>
                          <td width="100"></td>
                          <td></td>
                        </tr>
                        <tr>
                          <td width="157">贷款总额(元)</td>
                          <td width="168"><input class="txtnum" id="edLoanSum" tabIndex="1" type="text" name="edLoanSum"></td>
                          <td width="100">提前还款日期</td>
                          <td><input class="txt" id="edAdvRetDate" onblur="ChkCZDate(edAdvRetDate);" tabIndex="5" type="text" value="2003-1-3" name="edAdvRetDate">
                            <IMG style="cursor:hand;" onclick="javascript:Cal_dropdown(edAdvRetDate)" src="images/Calendar1.gif" align="absmiddle"></td>
                        </tr>
                        <TR>
                          <TD width="157" DESIGNTIMEURL="../Images/Calendar1.gif">原贷款期限(年)</TD>
                          <td width="168"><input class="txtnum" id="edLoanYears" onblur="TypeChange()" tabIndex="2" type="text" value="10" name="edLoanYears"></td>
                          <td width="100">一次性提前还清</td>
                          <td><input id="rbOnce_0" tabIndex="6" type="radio" CHECKED value="false" name="rbOnce">否
                            <input id="rbOnce_1" tabIndex="6" type="radio" value="true" name="rbOnce">是
                          </td>
                        </TR>
                        <tr>
                          <td width="157">初始还款日期</td>
                          <td width="168"><input class="txt" id="edInitRetDate" onblur="ChkCZDate(edInitRetDate);" tabIndex="3" type="text" value="2003-1-3" name="edInitRetDate">
                            <IMG style="cursor:hand;" onclick="javascript:Cal_dropdown(edInitRetDate)" src="images/Calendar1.gif" align="absMiddle">
                          </td>
                          <td width="100">部分提前还款(元)</td>
                          <td><input class="txtnum" id="edPartRet" tabIndex="7" type="text" name="edPartRet"></td>
                        </tr>
                        <tr>
                          <td width="157">贷款年利率(%)</td>
                          <td width="168"><input class="txtnum" id="edRate" tabIndex="4" type="text" value="4.77" name="edRate"></td>
                          <td width="100">处理方式</td>
                          <td id="dealway1"><input tabindex="8" id="rd1" type="radio" CHECKED name="RadioGroup" VALUE="rd1">减月还款,年限不变</td>
                        </tr>
                        <tr>
                          <td width="431" colSpan="3">&nbsp;</td>
                          <td id="dealway2"><input tabindex="8" id="rd2" type="radio" name="RadioGroup" VALUE="rd2">缩短年限,还款不变</td>
                        </tr>
                        <tr>
                          <td width="157"></td>
                          <td align="right" width="168"><input class="btn" id="btnCalc" tabIndex="9" type="button" value=" 计算 " name="btnCalc"></td>
                          <td width="100"></td>
                          <td></td>
                        </tr>
                        <tr>
                          <td align="middle" colSpan="4">
                            <hr width="550" SIZE="1">
                          </td>
                        </tr>
                        <tr>
                          <td align="middle" colSpan="4">
                            <table border="0" ID="Table3">
                              <TBODY>
                                <tr>
                                  <td>原月还款额</td>
                                  <td width="120"><span id="OrgMthRet"></span></td>
                                  <td>提前还款当月还款额</td>
                                  <td width="120"><span id="ThisMthTotalAdvRet"></span></td>
                                </tr>
                                <tr>
                                  <td>原最后还款日期</td>
                                  <td width="120"><span id="RetOrgEndRetDate"></span></td>
                                  <td>提前还款后的月还款额</td>
                                  <td width="120"><span id="AfterMthRet"></span></td>
                                </tr>
                                <tr>
                                  <td>至提前还款上月已还款总额</td>
                                  <td width="120"><span id="RetedSum"></span></td>
                                  <td>提前还贷节省的利息支出</td>
                                  <td width="120"><span id="ScantyInterest"><FONT face="宋体"></FONT></span></td>
                                </tr>
                                <tr>
                                  <td>至提前还款上月已还利息额</td>
                                  <td width="120"><span id="RetedInterest"></span></td>
                                  <td>提前还贷后的最后还款日期</td>
                                  <td width="120"><span id="EndRetDate"></span></td>
                                </tr>
                              </TBODY>
                            </table>
                          </td>
                      </table>
                    </TR>
                  </TABLE>
                </div>
              </TD>
            </TR>
          </TBODY>
        </TABLE>
      </div>
    </form>
    <xml id="xmlLoan" src="http://localhost:8080/CRM/scripts/financeTools/LoanAndOtherLoan.xml"></xml>
    <script language="javascript">
		function ChkCZDate(edit)
		{
			edit.value=Trim(edit.value);
			if(edit.value=='') return true;
			if(!Cal_datevalid(edit,'1910-1-1','3000-1-1')) 
			{
				alert('日期格式不正确,日期有效范围为1910年到3000年');
				edit.focus();
			}
		}
    </script>
    <script language="javascript" event="onclick" for="rbOnce">
		  ToggleRbOnce();
    </script>
    <script language="javascript" event="onclick" for="btnCalc">
			btnCalClick();
    </script>
    <script language="javascript">
		function TypeChange()
		{
			var IDType = "1";
			if (document.all.edType.selectedIndex == 1) 
				IDType = "2";
			var period = document.all.edLoanYears.value * 12;
			if(!CheckPN(document.all.edLoanYears,'输入了非法的贷款年限！',false))
				document.all.edRate.value = '';
			else
				document.all.edRate.value = GetLoanRatio(IDType, period, xmlLoan);
		}
		function ToggleRbOnce()
		{
		  	if(document.all.rbOnce_0.checked)
			{				
    			  document.all.edPartRet.disabled = false;
    			  if(isNaN(document.all.edPartRet.value))    			      
					document.all.edPartRet.value="";											
				document.all.dealway1.style["display"] = "";
				document.all.dealway2.style["display"] = "";
			}
			if(document.all.rbOnce_1.checked)
			{				
				document.all.edPartRet.disabled = true;
				document.all.edPartRet.value="不填";
				document.all.dealway1.style["display"] = "none";
				document.all.dealway2.style["display"] = "none";
			}
		}
		function init()
		{
			ToggleRbOnce();
	//		TypeChange();
			document.all.edAdvRetDate.value=datetostring(new Date());
			document.all.edInitRetDate.value=datetostring(new Date());
		}
		window.attachEvent("onload",init);
		  
     //GetCalc_Close();
		
    </script>
  </body>
</HTML>
