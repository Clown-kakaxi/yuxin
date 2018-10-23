<%@ page contentType="text/html; charset=utf-8"%>
<html>
<head>
<%@ include file="/contents/pages/common/includes.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>信息查询</title>
<!-- script type="text/javascript" src="../../../commonjs/pageEffects.js"></script-->
<style type="text/css">
/* 	p{
	font-size: 15px;font-weight: bold;text-align: center;
	}
	li{
	font-size: 13px;font-weight: bold; font:'宋体';color: #0066CC;
	} */
</style>
	<script>
	function showMoney(){/* 
		document.getElementById('money1').style.display='';
		document.getElementById('money2').style.display='';
		document.getElementById('money3').style.display='';	 */	
	}
	function show(imgDivId)
	{	  
		document.getElementById(imgDivId).style.visibility='visible';
	}

	function hide(imgDivId){
		document.getElementById(imgDivId).style.visibility='hidden';
   	}

	function imgDivMove(id){
		var divIndex = id.substr(3,id.length-3);
		document.getElementById(id).onmouseover = function(){};
		document.getElementById('img'+divIndex).style.visibility='visible';
	}
	function imgDivMoveOut(id){
        	var divIndex = id.substr(3,id.length-3);
		document.getElementById('img'+divIndex).style.visibility='hidden';
		document.getElementById(id).onmouseover = function(){
			document.getElementById('img'+divIndex).style.visibility='visible';
		};
	}
	function turnToRealTime(){
		document.getElementById("contentFrame").src="../../realTimeQuery/realTimeQuery.html";
	}
	function baseInfo(){
		parent.c1.events.click.listeners[0].fn();
	/**		parent.document.getElementById('viewport_center').innerHTML = '';
			ScriptMgr = new ScriptLoaderMgr();
		    ScriptMgr.load({        
				scripts: ['customerBaseInformation/customerBase.js'],        
				callback: function() {  
				}
			});
		debugger;
	*/	
	}
</script>
<script type="text/javascript"src="<%=request.getContextPath()%>/contents/pages/customer/customerManager/jquery-1.7.1.min.js"></script>
    

<script type="text/javascript">


     function load1(){
	   // var name1=parent.oCustInfo.cust_name;
		    //window.location.href.split("custName=")[1];
	    var name2=parent.oCustInfo.cust_name;
		    //name1.split("&")[0];
	    var cust_name=document.getElementById("content");
	    cust_name.innerHTML=name2; 
     	}
    	function redirect(url)
    	{    url=url+window.location.href.split("customerId=")[1];
    	window.location=url;
    	}
    	function to(url){
			window.location.href=url;
		}
    </script>

</head>
<body onload="load1()"><!--
    <table  border="0" width="100%" height="100%">
    <tr>
<td width="777px" height="100%">
	--><div style="z-index:2;position:absolute;width:310px;height:190px;left:347px;top:110px;background-image:url(../../../img/custmerView/center.png);border:1px   none   #000000;">
         <table  border="0" style="z-index:2;position:absolute;width:175px;height:120px;left:70px;top:35px;">
    <tr class="result" id="cust_name"><!-- 
    <span class="result" id="cust_name"style="position:absolute;left:0px;top:0px;"></span> -->
    <td>
   <div id="box">
 <div id="content" style="font-size: 15px;cursor:hand;font-weight: bold; font:'宋体';text-align: center;"></div>
 </div>
    </tr></table>
        	<!-- <p>&nbsp;</P><p>&nbsp;</P>
        	<p>&nbsp;</P><p>&nbsp;</P>
        	<p>&nbsp;</P> -->
        	
        	</div>		
            
             <div id="div4" onmousemove="show('img4');" onmouseout="hide('img4');" style="position:absolute;width:100px;height:110px;left:650px;top:150px;background-image:url(../../../img/custmerView/circle.png);border:1px   none   #000000;background-position:center;background-repeat:no-repeat;">
             <p>&nbsp;</P>
             <p style="position:absolute;left:23px;top:45px;font-size: 12px;cursor:hand;font-weight: bold;text-align: center;">开户网点</p></div>
             <div id="img4" onmousemove="imgDivMove('div4');" onmouseout="imgDivMoveOut('div4');"   style="position:absolute;width:270px;height:205px;left:732px;top:153px;background-image:url(../../../img/custmerView/popup22.png);border:1px  none   #000000;visibility: hidden;">
	          <table border="0" style="position:absolute;left:22px;top:8px;">
                 <tr>
                      <td width="70" style="font-size: 14px;font-weight: bold; font:'宋体';color: #0066CC;text-align: center;">机构号</td>
                      <td width="120" style="font-size: 14px;font-weight: bold; font:'宋体';color: #0066CC;text-align: center;">机构名称</td>
                 </tr>
                 <tr>
                      <td style="text-align: left;"><span class="result" id="UNITNO0" style="font-size: 14px;font-weight: bold; font:'宋体';color: #0066CC;"></span></td>
                      <td style="text-align: left;"><span class="result" id="UNITNAME0" style="font-size: 14px;font-weight: bold; font:'宋体';color: #0066CC;"></span></td>
                 </tr>
                 <tr>
                      <td style="text-align: left;"><span class="result" id="UNITNO1" style="font-size: 14px;font-weight: bold; font:'宋体';color: #0066CC;"></span></td>
                      <td style="text-align: left;"><span class="result" id="UNITNAME1" style="font-size: 14px;font-weight: bold; font:'宋体';color: #0066CC;"></span></td>
                 </tr>
                 <tr>
                      <td style="text-align: left;"><span class="result" id="UNITNO2" style="font-size: 14px;font-weight: bold; font:'宋体';color: #0066CC;"></span></td>
                      <td style="text-align: left;"><span class="result" id="UNITNAME2" style="font-size: 14px;font-weight: bold; font:'宋体';color: #0066CC;"></span></td>
                 </tr>
                 <tr>
                      <td style="text-align: left;"><span class="result" id="UNITNO3" style="font-size: 14px;font-weight: bold; font:'宋体';color: #0066CC;"></span></td>
                      <td style="text-align: left;"><span class="result" id="UNITNAME3" style="font-size: 14px;font-weight: bold; font:'宋体';color: #0066CC;"></span></td>
                 </tr>
                 <tr>
                      <td style="text-align: left;"><span class="result" id="UNITNO4" style="font-size: 14px;font-weight: bold; font:'宋体';color: #0066CC;"></span></td>
                      <td style="text-align: left;"><span class="result" id="UNITNAME4" style="font-size: 14px;font-weight: bold; font:'宋体';color: #0066CC;"></span></td>
                 </tr>
                 <tr>
                      <td style="text-align: left;"><span class="result" id="UNITNO5" style="font-size: 14px;font-weight: bold; font:'宋体';color: #0066CC;"></span></td>
                      <td style="text-align: left;"><span class="result" id="UNITNAME5" style="font-size: 14px;font-weight: bold; font:'宋体';color: #0066CC;"></span></td>
                 </tr>
                 <tr>
                      <td style="text-align: left;"><span class="result" id="UNITNO6" style="font-size: 14px;font-weight: bold; font:'宋体';color: #0066CC;"></span></td>
                      <td style="text-align: left;"><span class="result" id="UNITNAME6" style="font-size: 14px;font-weight: bold; font:'宋体';color: #0066CC;"></span></td>
                 </tr>
                 <tr>
                      <td style="text-align: left;"><span class="result" id="UNITNO7" style="font-size: 14px;font-weight: bold; font:'宋体';color: #0066CC;"></span></td>
                      <td style="text-align: left;"><span class="result" id="UNITNAME7" style="font-size: 14px;font-weight: bold; font:'宋体';color: #0066CC;"></span></td>
                 </tr>
                 <tr>
                      <td style="text-align: left;"><span class="result" id="UNITNO8" style="font-size: 14px;font-weight: bold; font:'宋体';color: #0066CC;"></span></td>
                      <td style="text-align: left;"><span class="result" id="UNITNAME8" style="font-size: 14px;font-weight: bold; font:'宋体';color: #0066CC;"></span></td>
                 </tr>
            </table>
	         </div>
	         
            
            <div id="div2" onmousemove="show('img2');"  onmouseout="hide('img2');" style="position:absolute;width:140px;height:110px;left:517px;top:15px;background-image:url(../../../img/custmerView/circle.png);border:1px   none   #000000;background-position:center;background-repeat:no-repeat;" >
            <p style="position:absolute;left:50px;top:45px;font-size: 12px;cursor:hand;font-weight: bold;text-align: center;">联系人</p></div>
            <div id="img2" onmousemove="imgDivMove('div2');" onmouseout="imgDivMoveOut('div2');" style="position:absolute;width:309px;height:205px;left:612px;z-index:4;top:5px;background-image:url(../../../img/custmerView/popup3.png);border:1px   none   #000000;visibility: hidden;">
	         <table border="0" style="position:absolute;left:22px;top:12px;">
                 <tr>
                      <td width="60" style="font-size: 14px;font-weight: bold; font:'宋体';color: #0066CC;text-align: center;">姓名</td>
                      <td width="70" style="font-size: 14px;font-weight: bold; font:'宋体';color: #0066CC;text-align: center;">职务</td>
                      <td width="80" style="font-size: 14px;font-weight: bold; font:'宋体';color: #0066CC;text-align: center;">电话</td>
                 </tr>
                 <tr>
                      <td style="text-align: left;"><span class="result" id="NAME0" style="font-size: 14px;font-weight: bold; font:'宋体';color: #0066CC;"></span></td>
                      <td style="text-align: left;"><span class="result" id="POSITION0" style="font-size: 14px;font-weight: bold; font:'宋体';color: #0066CC;"></span></td>
                      <td style="text-align: left;"><span class="result" id="TELEPHONE0" style="font-size: 14px;font-weight: bold; font:'宋体';color: #0066CC;"></span></td>
                 </tr>
                 <tr>
                      <td style="text-align: left;"><span class="result" id="NAME1" style="font-size: 14px;font-weight: bold; font:'宋体';color: #0066CC;"></span></td>
                      <td style="text-align: left;"><span class="result" id="POSITION1" style="font-size: 14px;font-weight: bold; font:'宋体';color: #0066CC;"></span></td>
                      <td style="text-align: left;"><span class="result" id="TELEPHONE1" style="font-size: 14px;font-weight: bold; font:'宋体';color: #0066CC;"></span></td>
                 </tr>
                  <tr>
                      <td style="text-align: left;"><span class="result" id="NAME2" style="font-size: 14px;font-weight: bold; font:'宋体';color: #0066CC;"></span></td>
                      <td style="text-align: left;"><span class="result" id="POSITION2" style="font-size: 14px;font-weight: bold; font:'宋体';color: #0066CC;"></span></td>
                      <td style="text-align: left;"><span class="result" id="TELEPHONE2" style="font-size: 14px;font-weight: bold; font:'宋体';color: #0066CC;"></span></td>
                 </tr>
                  <tr>
                      <td style="text-align: left;"><span class="result" id="NAME3" style="font-size: 14px;font-weight: bold; font:'宋体';color: #0066CC;"></span></td>
                      <td style="text-align: left;"><span class="result" id="POSITION3" style="font-size: 14px;font-weight: bold; font:'宋体';color: #0066CC;"></span></td>
                      <td style="text-align: left;"><span class="result" id="TELEPHONE3" style="font-size: 14px;font-weight: bold; font:'宋体';color: #0066CC;"></span></td>
                 </tr>
                  <tr>
                      <td style="text-align: left;"><span class="result" id="NAME4" style="font-size: 14px;font-weight: bold; font:'宋体';color: #0066CC;"></span></td>
                      <td style="text-align: left;"><span class="result" id="POSITION4" style="font-size: 14px;font-weight: bold; font:'宋体';color: #0066CC;"></span></td>
                      <td style="text-align: left;"><span class="result" id="TELEPHONE4" style="font-size: 14px;font-weight: bold; font:'宋体';color: #0066CC;"></span></td>
                 </tr>
                  <tr>
                      <td style="text-align: left;"><span class="result" id="NAME5" style="font-size: 14px;font-weight: bold; font:'宋体';color: #0066CC;"></span></td>
                      <td style="text-align: left;"><span class="result" id="POSITION5" style="font-size: 14px;font-weight: bold; font:'宋体';color: #0066CC;"></span></td>
                      <td style="text-align: left;"><span class="result" id="TELEPHONE5" style="font-size: 14px;font-weight: bold; font:'宋体';color: #0066CC;"></span></td>
                 </tr>
                  <tr>
                      <td style="text-align: left;"><span class="result" id="NAME6" style="font-size: 14px;font-weight: bold; font:'宋体';color: #0066CC;"></span></td>
                      <td style="text-align: left;"><span class="result" id="POSITION6" style="font-size: 14px;font-weight: bold; font:'宋体';color: #0066CC;"></span></td>
                      <td style="text-align: left;"><span class="result" id="TELEPHONE6" style="font-size: 14px;font-weight: bold; font:'宋体';color: #0066CC;"></span></td>
                 </tr>
                  <tr>
                      <td style="text-align: left;"><span class="result" id="NAME7" style="font-size: 14px;font-weight: bold; font:'宋体';color: #0066CC;"></span></td>
                      <td style="text-align: left;"><span class="result" id="POSITION7" style="font-size: 14px;font-weight: bold; font:'宋体';color: #0066CC;"></span></td>
                      <td style="text-align: left;"><span class="result" id="TELEPHONE7" style="font-size: 14px;font-weight: bold; font:'宋体';color: #0066CC;"></span></td>
                 </tr>
                  <tr>
                      <td style="text-align: left;"><span class="result" id="NAME8" style="font-size: 14px;font-weight: bold; font:'宋体';color: #0066CC;"></span></td>
                      <td style="text-align: left;"><span class="result" id="POSITION8" style="font-size: 14px;font-weight: bold; font:'宋体';color: #0066CC;"></span></td>
                      <td style="text-align: left;"><span class="result" id="TELEPHONE8" style="font-size: 14px;font-weight: bold; font:'宋体';color: #0066CC;"></span></td>
                 </tr>
                
                 </table>
            </div>
            	
           
            <div id="div1" onmousemove="show('img1');" onmouseout="hide('img1');" style="position:absolute;width:100px;height:110px;left:377px;top:15px;background:url(../../../img/custmerView/circle.png);border:1px   none   #000000;background-position:center;background-repeat:no-repeat;">
         
            <p style="position:absolute;left:22px;top:45px;font-size: 12px;cursor:hand;font-weight: bold;text-align: center;">客户经理</p></div>
            <div id="img1" onmousemove="imgDivMove('div1');" onmouseout="imgDivMoveOut('div1');" style="z-index:3;position:absolute;width:360px;height:205px;left:35px;top:5px;background-image:url(../../../img/custmerView/popup44.png);border:1px   none   #000000;visibility: hidden">
            <table  border="0" style="position:absolute;left:8px;top:10px;">
                 <tr>
                      <td width="60" style="font-size: 14px;font-weight: bold; font:'宋体';color: #0066CC;text-align: center;">姓名</td>
                      <td width="130" style="font-size: 14px;font-weight: bold; font:'宋体';color: #0066CC;text-align: center;">电话</td>
                      <td width="130" style="font-size: 14px;font-weight: bold; font:'宋体';color: #0066CC;text-align: center;">所属机构</td>
                 </tr>
                  <tr>
                      <td style="text-align: left;"><span class="result" id="KNAME0" style="font-size: 14px;font-weight: bold; font:'宋体';color: #0066CC;"></span></td>
                      <td style="text-align: left;"><span class="result" id="KTELEPHONE0" style="font-size: 14px;font-weight: bold; font:'宋体';color: #0066CC;"></span></td>
                      <td style="text-align: left;"><span class="result" id="ORG0" style="font-size: 14px;font-weight: bold; font:'宋体';color: #0066CC;"></span></td>
                 </tr>
                 <tr>
                      <td style="text-align: left;"><span class="result" id="KNAME1" style="font-size: 14px;font-weight: bold; font:'宋体';color: #0066CC;"></span></td>
                      <td style="text-align: left;"><span class="result" id="KTELEPHONE1" style="font-size: 14px;font-weight: bold; font:'宋体';color: #0066CC;"></span></td>
                      <td style="text-align: left;"><span class="result" id="ORG1" style="font-size: 14px;font-weight: bold; font:'宋体';color: #0066CC;"></span></td>
                 </tr>
                 <tr>
                      <td style="text-align: left;"><span class="result" id="KNAME2" style="font-size: 14px;font-weight: bold; font:'宋体';color: #0066CC;"></span></td>
                      <td style="text-align: left;"><span class="result" id="KTELEPHONE2" style="font-size: 14px;font-weight: bold; font:'宋体';color: #0066CC;"></span></td>
                      <td style="text-align: left;"><span class="result" id="ORG2" style="font-size: 14px;font-weight: bold; font:'宋体';color: #0066CC;"></span></td>
                 </tr>
                     <tr>
                      <td style="text-align: left;"><span class="result" id="KNAME3" style="font-size: 14px;font-weight: bold; font:'宋体';color: #0066CC;"></span></td>
                      <td style="text-align: left;"><span class="result" id="KTELEPHONE3" style="font-size: 14px;font-weight: bold; font:'宋体';color: #0066CC;"></span></td>
                      <td style="text-align: left;"><span class="result" id="ORG3" style="font-size: 14px;font-weight: bold; font:'宋体';color: #0066CC;"></span></td>
                 </tr>
                     <tr>
                      <td style="text-align: left;"><span class="result" id="KNAME4" style="font-size: 14px;font-weight: bold; font:'宋体';color: #0066CC;"></span></td>
                      <td style="text-align: left;"><span class="result" id="KTELEPHONE4" style="font-size: 14px;font-weight: bold; font:'宋体';color: #0066CC;"></span></td>
                      <td style="text-align: left;"><span class="result" id="ORG4" style="font-size: 14px;font-weight: bold; font:'宋体';color: #0066CC;"></span></td>
                 </tr>
                     <tr>
                      <td style="text-align: left;"><span class="result" id="KNAME5" style="font-size: 14px;font-weight: bold; font:'宋体';color: #0066CC;"></span></td>
                      <td style="text-align: left;"><span class="result" id="KTELEPHONE5" style="font-size: 14px;font-weight: bold; font:'宋体';color: #0066CC;"></span></td>
                      <td style="text-align: left;"><span class="result" id="ORG5" style="font-size: 14px;font-weight: bold; font:'宋体';color: #0066CC;"></span></td>
                 </tr>
                     <tr>
                      <td style="text-align: left;"><span class="result" id="KNAME6" style="font-size: 14px;font-weight: bold; font:'宋体';color: #0066CC;"></span></td>
                      <td style="text-align: left;"><span class="result" id="KTELEPHONE6" style="font-size: 14px;font-weight: bold; font:'宋体';color: #0066CC;"></span></td>
                      <td style="text-align: left;"><span class="result" id="ORG6" style="font-size: 14px;font-weight: bold; font:'宋体';color: #0066CC;"></span></td>
                 </tr>
                 </table>
           
           
            </div>	
            
            <div id="div3" onclick="baseInfo();" onmousemove="show('img3');"  onmouseout="hide('img3');" style="position:absolute;width:100px;height:110px;left:252px;top:150px;background-image:url(../../../img/custmerView/circle.png);border:1px   none   #000000;background-position:center;background-repeat:no-repeat;">
            	<p style="position:absolute;left:22px;top:45px;font-size: 12px;cursor:hand;font-weight: bold;text-align: center;">基本信息</p>
            </div>	
            <div id="img3"  onmousemove="imgDivMove('div3');" onmouseout="imgDivMoveOut('div3');" style="z-index:128889;position:absolute;width:275px;height:205px;left:-10px;top:155px;background-image:url(../../../img/custmerView/popup11.png);border:1px   none   #000000;visibility: hidden">
	         <table style="position:absolute;left:12px;top:12px;">
                 <tr>
                      <td style="font-size: 14px;font-weight: bold; font:'宋体';color: #0066CC;text-align: right;">法人姓名：</td>
                      <td><span class="result" id="FR_NAME" style="font-size: 14px;font-weight: bold; font:'宋体';color: #0066CC;"></span></td>
                 </tr>
                 <tr>
                      <td style="font-size: 14px;font-weight: bold; font:'宋体';color: #0066CC;text-align: right;">负责人姓名：</td>
                      <td><span class="result" id="FZ_NAME" style="font-size: 14px;font-weight: bold; font:'宋体';color: #0066CC;"></span></td>
                 </tr>
                 <tr>
                      <td style="font-size: 14px;font-weight: bold; font:'宋体';color: #0066CC;text-align: right;">证明文件名称：</td>
                      <td><span class="result" id="CRET_NAME" style="font-size: 14px;font-weight: bold; font:'宋体';color: #0066CC;"></span></td>
                 </tr>
                 <tr>
                      <td style="font-size: 14px;font-weight: bold; font:'宋体';color: #0066CC;text-align: right;">证明文件号码：</td>
                      <td><span class="result" id="CRET_NO" style="font-size: 14px;font-weight: bold; font:'宋体';color: #0066CC;"></span></td>
                 </tr>
                 <tr>
                      <td style="font-size: 14px;font-weight: bold; font:'宋体';color: #0066CC;text-align: right;">税务证号：</td>
                      <td><span class="result" id="TAX_CARD" style="font-size: 14px;font-weight: bold; font:'宋体';color: #0066CC;"></span></td>
                 </tr>
                   <tr>
                      <td style="font-size: 14px;font-weight: bold; font:'宋体';color: #0066CC;text-align: right;">资产总额：</td>
                      <td><span class="result" id="ASS_AMT" style="font-size: 14px;font-weight: bold; font:'宋体';color: #0066CC;"></span></td>
                 </tr>
                 <tr>
                      <td style="font-size: 14px;font-weight: bold; font:'宋体';color: #0066CC;text-align: right;">销售额：</td>
                      <td><span class="result" id="BUSINESS_JSR" style="font-size: 14px;font-weight: bold; font:'宋体';color: #0066CC;"></span></td>
                 </tr>
                  <tr>
                      <td style="font-size: 14px;font-weight: bold; font:'宋体';color: #0066CC;text-align: right;">员工人数：</td>
                      <td><span class="result" id="EMPLOYEE_NUM" style="font-size: 14px;font-weight: bold; font:'宋体';color: #0066CC;"></span></td>
                 </tr>
            </table>
             </div>
           
            	
            <div id="div5" onmousemove="show('img5');"  onmouseout="hide('img5');" style="position:absolute;width:100px;height:110px;left:377px;top:290px;background-image:url(../../../img/custmerView/circle.png);border:1px   none   #000000;background-position:center;background-repeat:no-repeat;">
           	<p>&nbsp;</p>
            <p style="position:absolute;left:15px;top:45px;font-size: 12px;font-weight: bold;cursor:hand;text-align: center;">客户贡献度</p></div>
            <div id="img5"  onmousemove="imgDivMove('div5');" onmouseout="imgDivMoveOut('div5');" style="position:absolute;z-index:128888;width:380;height:350;left:2px;top:115px;background-image:url(../../../img/custmerView/popup33.png);border:1px   none   #000000;visibility: hidden">
 	        	<iframe id="contentFrame1" name="content1" height="350" frameborder="no" width="380" src="../../fusionChart/content/degreeOfContributionChart.jsp"  scrolling="no"> </iframe>
            </div>
            
            <div id="div6" onmousemove="show('img6');" onmouseout="hide('img6');" style="position:absolute;width:100px;height:110px;left:542px;top:285px;background-image:url(../../../img/custmerView/circle.png);border:1px   none   #000000;background-position:center;background-repeat:no-repeat;">
           <p>&nbsp;</p>
            <p style="position:absolute;left:22px;top:45px;font-size: 12px;font-weight: bold;cursor:hand;text-align: center;">产品信息</p></div>
            <div id="img6" onmousemove="imgDivMove('div6');" onmouseout="imgDivMoveOut('div6');" style="z-index:3;position:absolute;width:380;height:350;left:635px;top:115px;background-image:url(../../../img/custmerView/popup33.png);border:1px   none   #000000;visibility: hidden">
	   			<iframe id="contentFrame2" name="content2" height="350" frameborder="no" width="380" src="../../fusionChart/content/productInfoChart.jsp"  scrolling="no"> </iframe>
	    </div>
</body>
</html>