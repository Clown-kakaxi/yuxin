<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Frameset//EN" "http://www.w3.org/TR/html4/frameset.dtd">
<%@ page contentType="text/html;charset = utf-8" pageEncoding="utf-8"%>
<%@ include file="/contents/pages/common/includes.jsp"%>

<html xmlns:o="urn:schemas-microsoft-com:office:office" xmlns:w="urn:schemas-microsoft-com:office:word">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>富邦华一银行个人开户手册</title>
<script type="text/javascript" src="/crmweb/jQuery/js/jquery-1.11.3.min.js"></script>

<style type="text/css">

.content {
	width: 100%;
}
.p{
 font-family : 宋体;
 font-size : 70px;
}
.c1 {
  width:20px;
  height:20px;
}
.c2 {
  width:11px;
  height:11px;
}
.p1{
 font-family : 宋体;
 font-size : 30px;
}
.p2{
 font-family : 宋体;
 font-size : 35px;
}
.header {
	height: 640pt;
}

.body {
	margin: 0;
	padding: 0;
	width: 635pt;
	margin: 0 auto;
	border: 1px black solid;
	height:98%;
}
.body1 {
	margin: 0;
	padding: 0;
	width: 640pt;
	height:10px;
	margin: 0 auto;
	border: 0px black solid;
}
.bottom{
boder-bottom-style :dashed; 
}
.document {
	margin: 0;
	padding: 0;
	width: 100%;
}

.document li {
	line-height: 2em;
}

.document ul li {
	border-bottom: 1px black solid;
}

.title-row,table .td1 {
	width: 100%;
	height: 30px;
	background-color: #2faec7;
	font-size: 18px;
	font-family:楷体;
	font-weight:bold;
}
#li-agreement table{
	border:1px solid;
}
#li-agreement tbody table{
	border:none;
}
#li-agreement table tbody .s1{
	font-weight:bold;
}
.content-row {
	width: 100%;
	height: 2em;
}

.content-row-pro1 {
	width: 100%;
	height: 7em;
}

.content-row-pro2 {
	width: 100%;
	height: 14em;
}

.content-row-pro3 {
	width: 100%;
	height: 36em;
}

.content-row-pro4 {
	width: 100%;
}

.content-row-pro5 {
	width: 100%;
	height: 17em;
}

.content-row-pro6 {
	width: 100%;
	height: 18em;
	
}
.content-row-pro7 {
	width: 100%;
	height: 15em;
}
.content-row-pro8 {
	width: 100%;
	height: 4em;
}
.content-row-pro9 {
	width: 100%;
	height: 12em;
}
.content-row-pro10 {
	width: 100%;
	height: 10em;
}
.content-row-pro4 .xieyi {
	padding: 20px 20px;
}

.text-title {
	text-align: center;
}

.customer-detail {
	width: 33%;
	height: 100%;
	float: left;
	border-left: 1px black solid;
}

.info-table {
	border-collapse: collapse;
	border: 1px #ccc solid;
}

table{
	width:100%;
}

.table-td {
	border: 1px #ccc solid;
}

.text-bold {
	font-weight: bold;
}

</style>
<script type="text/javascript">
var myMask;
var openerInfo;
var interval1;
$(function(){
	loadCustAgreementHtml();
	if(window.opener){
		if(window.opener.getFullCustInfo){
			openerInfo = window.opener.getFullCustInfo();
			initData();
		}
	}else{
		
	}
});
//循环获取custinfo信息
//初始化表单数据
function initData(){
	if(openerInfo){	
		var custName = openerInfo.custname; //客户姓名
		var identityType = openerInfo.identityType3; //证件类型
		var identityType3mz = openerInfo.identityType3mz; //证件类型码值
		var lianMingIdenType1mz = openerInfo.lianMingIdenType1mz;//联名户证件类型码值
		var identityNo = openerInfo.identityNo3; //证件号
		var customPinYin = openerInfo.customPinYin;//姓名拼音
		var twIdentNum3 = openerInfo.twIdentNum3;//台湾证件号
		var gaIdentNum3 = openerInfo.gaIdentNum3;//港澳证件号
		var LEGAL_EXPIRED_DATE = openerInfo.LEGAL_EXPIRED_DATE;//证件有效期
		var MAIL_ADDR = openerInfo.MAIL_ADDR;//邮寄地所属国籍
		var MAIL_ADDR_INFO = openerInfo.MAIL_ADDR_INFO;//邮寄地详细地址
		var MAIL_ZIPCODE = openerInfo.MAIL_ZIPCODE;//邮寄邮编
		var QUYUMA = openerInfo.QUYUMA;//区域码
		var mbPHONENUM = openerInfo.mbPHONENUM;//移动电话
		var EMAIL = openerInfo.EMAIL;//电子邮箱
		var USTIN = openerInfo.USTIN; //US/TIN
		var shengming = openerInfo.shengming; //本人声明
		var isUNtaxpayer = openerInfo.isUNtaxpayer;//是否为美国纳税人
		var jnOrJwCheckbox = openerInfo.jnOrJwCheckbox;//境内外
		var jnOrJwCategory = openerInfo.jnOrJwCategory;//判断境内境外
		var elecState = openerInfo.elecState; //电子对账单
		var chgNotice = openerInfo.chgNotice; //账务变动通知
		var lianMinPinYin = openerInfo.lianMinPinYin;//联名户拼音
		var lianMingIdenType1 = openerInfo.lianMingIdenType1Text;//联名户证件类型
		var lianMingIdenNo1 = openerInfo.lianMingIdenNo1; //联名户证件号码
		var LEGAL_EXPIRED_DATE2 = openerInfo.LEGAL_EXPIRED_DATE2; //联名户证件有效日期
		var lianMingTwIdentNum1 = openerInfo.lianMingTwIdentNum1; // 联名户台湾身份证号码
		var lianMingGaIdentNum1 = openerInfo.lianMingGaIdentNum1;  //联名户港澳身份证号码
		var USTIN2 = openerInfo.USTIN2;
		var REASON = openerInfo.REASON;
		var REASON2 = openerInfo.REASON2;
		var REASON3 = openerInfo.REASON3;
		var REASON4 = openerInfo.REASON4;
		var detailReason = openerInfo.detailReason; 
		var detailReason2 = openerInfo.detailReason2; 
		var shengming2 = openerInfo.shengming2;
		var isUNtaxpayer2 = openerInfo.isUNtaxpayer2;
 		if(1==1){
 			var e_customerName = document.getElementById("customerName");
			e_customerName.innerHTML = "<b>&nbsp;客户姓名：</b>"+custName;
 			var e_customPinYin = document.getElementById("customPinYin");
			e_customPinYin.innerHTML = "<b>&nbsp;姓名拼音：</b>"+customPinYin+"/"+lianMinPinYin;
			var e_identType1 = document.getElementById("identType1");
			e_identType1.innerHTML = "<b>&nbsp;证件种类1：</b>"+identityType;
			var e_identType2 = document.getElementById("identType2");
			e_identType2.innerHTML = "<b>&nbsp;证件种类2：</b>"+lianMingIdenType1;
			var e_identityNo = document.getElementById("identNo1");
			e_identityNo.innerHTML = "<b>&nbsp;证件号码1：</b>"+identityNo;
			var e_identityNo2 = document.getElementById("identNo2");
			e_identityNo2.innerHTML = "<b>&nbsp;证件号码2：</b>"+lianMingIdenNo1;
			var e_tgIdentNum = document.getElementById("tgIdentNum");
			
			if(gaIdentNum3 != "" || lianMingGaIdentNum1 != ""||twIdentNum3 != ""|| lianMingTwIdentNum1 != ""){
			e_tgIdentNum.innerHTML = "<b>&nbsp;台湾身份证/港澳身份证号：</b>" +gaIdentNum3+twIdentNum3+"/"+lianMingGaIdentNum1+lianMingTwIdentNum1;
			}else{
		    e_tgIdentNum.innerHTML = "<b>&nbsp;台湾身份证/港澳身份证号：</b>" ;
			};
			if(identityType3mz != "5" || identityType3mz != "6" || identityType3mz != "X3" || identityType3mz != "X24"
				||lianMingIdenType1mz != "5" || lianMingIdenType1mz != "6"||lianMingIdenType1mz != "X3"||lianMingIdenType1mz != "X24"){
		        		e_tgIdentNum.innerHTML = '';
		        		$("#tg").hide();
		        };
			var e_LEGAL_EXPIRED_DATE1 = document.getElementById("LEGAL_EXPIRED_DATE1");
			e_LEGAL_EXPIRED_DATE1.innerHTML = "<b>&nbsp;证件有效期1：</b>"+LEGAL_EXPIRED_DATE;
			var e_LEGAL_EXPIRED_DATE2 = document.getElementById("LEGAL_EXPIRED_DATE2");
			e_LEGAL_EXPIRED_DATE2.innerHTML = "<b>&nbsp;证件有效期2：</b>"+LEGAL_EXPIRED_DATE2;
			var e_MAIL_ADDR = document.getElementById("MAIL_ADDR");
			e_MAIL_ADDR.innerHTML = "<b>&nbsp;邮寄地址：</b>"+MAIL_ADDR+"&nbsp;"+MAIL_ADDR_INFO;
			var e_MAIL_ZIPCODE = document.getElementById("MAIL_ZIPCODE");
			e_MAIL_ZIPCODE.innerHTML = "<b>&nbsp;邮寄邮编：</b>"+MAIL_ZIPCODE;
			var e_mbPHONENUM = document.getElementById("mbPHONENUM");
			e_mbPHONENUM.innerHTML = "<b>&nbsp;移动电话：</b>"+QUYUMA+"&nbsp;"+mbPHONENUM+"&nbsp;<b>（此电话将用于接收短信验证码和账务变动通知）</b>";
			var e_USTIN = document.getElementById("USTIN");
			e_USTIN.innerHTML = "US/TIN："+USTIN;
			var e_chUSTIN = document.getElementById("chUSTIN");
			e_chUSTIN.innerHTML = "US/TIN："+USTIN2;
			var e_brIdentNo1 = document.getElementById("brIdentNo1");
			e_brIdentNo1.innerHTML = "&nbsp;证件号1："+identityNo;
			var e_brIdentNo2 = document.getElementById("brIdentNo2");
			e_brIdentNo2.innerHTML = "&nbsp;证件号2："+lianMingIdenNo1;
			var e_zjh1 = document.getElementById("zjh1");
			e_zjh1.innerHTML = "<b>&nbsp;证件号1："+identityNo+"</b>";
			var e_zjh2 = document.getElementById("zjh2");
			e_zjh2.innerHTML = "<b>&nbsp;证件号2："+lianMingIdenNo1+"</b>";
 			var taxData = [{
				seq	: 1,
				name: "name1",
				code: "code1"
			},{
				seq	: 1,
				name: "name1",
				code: "code1"
			},{
				seq	: 1,
				name: "name1",
				code: "code1"
			},{
				seq	: 1,
				name: "name1",
				code: "code1"
			}];
			var arr = [
		   		'<table  border = "1" style="border-collapse:collapse;">',
		   			'<thead>',
		   				'<th>序号</th>',
		   				'<th>税收居民国（地区）</th>',
		   				'<th>纳税人识别号（TIN）</th>',
		   			'</thead>',
		   			'<tbody>'
		   	];
			taxData = Ext.decode(openerInfo.taxData);
			for(var i=0;i<taxData.length;i++){
				arr.push('<tr>');
				arr.push('<td>'+taxData[i].seq+'</td>');
				arr.push('<td>'+taxData[i].name+'</td>');
				arr.push('<td>'+taxData[i].code+'</td>');
				arr.push('</tr>');
			}
			arr.push('</tbody>');
			arr.push('</table>');
			document.getElementById("taxInfo").innerHTML=arr.join("");
			
			var taxData1 = [{
				seq	: 1,
				name: "name1",
				code: "code1"
			},{
				seq	: 1,
				name: "name1",
				code: "code1"
			},{
				seq	: 1,
				name: "name1",
				code: "code1"
			},{
				seq	: 1,
				name: "name1",
				code: "code1"
			}];
			var arr1 = [
		   		'<table  border = "1" style="border-collapse:collapse;">',
		   			'<thead>',
		   				'<th>序号</th>',
		   				'<th>税收居民国（地区）</th>',
		   				'<th>税收居民国号（TIN）</th>',
		   			'</thead>',
		   			'<tbody>'
		   	];
			taxData2 = Ext.decode(openerInfo.taxData2);
			for(var i=0;i<taxData2.length;i++){
				arr1.push('<tr>');
				arr1.push('<td>'+taxData2[i].seq+'</td>');
				arr1.push('<td>'+taxData2[i].name+'</td>');
				arr1.push('<td>'+taxData2[i].code+'</td>');
				arr1.push('</tr>');
			}
			arr1.push('</tbody>');
			arr1.push('</table>');
			document.getElementById("taxInfo1").innerHTML=arr1.join("");
		 	var e_shengming1 = document.getElementById("shengMing1");
			var e_shengming2 = document.getElementById("shengMing2");
			var e_shengming3 = document.getElementById("shengMing3");
			if(shengming == "01"){
				e_shengming1.setAttribute("checked","checked");;
			}else if(shengming == "02"){
				e_shengming2.setAttribute("checked","checked");;
			}else if(shengming == "03"){
				e_shengming3.setAttribute("checked","checked");;
			}else{
				e_shengming1.checked = false;
				e_shengming2.checked = false;
				e_shengming3.checked = false;
			};
			var e_chShengMing1 = document.getElementById("chShengMing1");
			var e_chShengMing2 = document.getElementById("chShengMing2");
			var e_chShengMing3 = document.getElementById("chShengMing3");
			if(shengming2 == "01"){
				e_chShengMing1.setAttribute("checked","checked");;
			}else if(shengming2 == "02"){
				e_chShengMing2.setAttribute("checked","checked");;
			}else if(shengming2 == "03"){
				e_chShengMing3.setAttribute("checked","checked");;
			}else{
				e_chShengMing1.checked = false;
				e_chShengMing2.checked = false;
				e_chShengMing3.checked = false;
			};
			var e_isUNtaxpayer1 = document.getElementById("isNaShui1");
			var e_isUNtaxpayer2 = document.getElementById("isNaShui2");
			if(isUNtaxpayer == '1'){
				e_isUNtaxpayer1.setAttribute("checked","checked");;
			}else if(isUNtaxpayer == '0'){
				e_isUNtaxpayer2.setAttribute("checked","checked");;
			}else{
				e_isUNtaxpayer1.checked = false;
				e_isUNtaxpayer2.checked = false;
			};
			var e_chIsUNtaxpayer1 = document.getElementById("chIsNaShui1");
			var e_chIsUNtaxpayer2 = document.getElementById("chIsNaShui2");
			if(isUNtaxpayer2 == '1'){
				e_chIsUNtaxpayer1.setAttribute("checked","checked");;
			}else if(isUNtaxpayer2 == '0'){
				e_chIsUNtaxpayer2.setAttribute("checked","checked");;
			}else{
				e_chIsUNtaxpayer1.checked = false;
				e_chIsUNtaxpayer2.checked = false;
			}; 
			var e_jnOrJwCheckbox1 = document.getElementById("jingnei1");
			var e_jnOrJwCheckbox2 = document.getElementById("jingnei2");
			var e_jnOrJwCheckbox3 = document.getElementById("jingnei3");
			var e_jnOrJwCheckbox4 = document.getElementById("jingnei4");
			var e_jnOrJwCheckbox5 = document.getElementById("jingwai1");
			var e_jnOrJwCheckbox6 = document.getElementById("jingwai2");
			var e_jnOrJwCheckbox7 = document.getElementById("jingwai3");
			var e_jnOrJwCheckbox8 = document.getElementById("jingwai4");
			var e_jnOrJwCheckbox9 = document.getElementById("jingwai5");
			var e_jnOrJwCheckbox10 = document.getElementById("jingwai6");
			if (jnOrJwCategory == "D"){
			for(var i = 0; i < jnOrJwCheckbox.length; i++){
				if(jnOrJwCheckbox[i] == 'K'){
					e_jnOrJwCheckbox1.setAttribute("checked","checked");;
				 }else if(jnOrJwCheckbox[i] == 'S'){
					e_jnOrJwCheckbox2.setAttribute("checked","checked");;
				   }else if(jnOrJwCheckbox[i] == 'C'){
					e_jnOrJwCheckbox3.setAttribute("checked","checked");;
				   }else if(jnOrJwCheckbox[i] == 'f'){
					e_jnOrJwCheckbox4.setAttribute("checked","checked");;
				   }else{}
			                              }
			}else if (jnOrJwCategory == "F"){
				 for(var i = 0; i < jnOrJwCheckbox.length; i++)
				 {	 if(jnOrJwCheckbox[i] == 'H'){
						e_jnOrJwCheckbox5.setAttribute("checked","checked");;
				   }else if(jnOrJwCheckbox[i] == 'S'){
					e_jnOrJwCheckbox6.setAttribute("checked","checked");;
				   }else if(jnOrJwCheckbox[i] == 'C'){
					e_jnOrJwCheckbox7.setAttribute("checked","checked");;
				   }else if(jnOrJwCheckbox[i] == 'L'){
					e_jnOrJwCheckbox8.setAttribute("checked","checked");;
				   }else if(jnOrJwCheckbox[i] == 'n'){
					e_jnOrJwCheckbox9.setAttribute("checked","checked");;
				   }else if(jnOrJwCheckbox[i] == 'o'){
					e_jnOrJwCheckbox10.setAttribute("checked","checked");;
				   }else{}
				 }
			    }else{};
		 var e_chgNotice = document.getElementById("chgNotice");
		 if(chgNotice == '1'){e_chgNotice.setAttribute("checked","checked");;}
		 var e_REASON1 = document.getElementById("reason1");
		 var e_REASON2 = document.getElementById("reason2");
		    if(REASON == 'on'){
		    	e_REASON1.setAttribute("checked","checked");;
		    	}else{
		    	e_REASON1.checked = false;
		    	};
		    if(REASON2 == 'on'){
		    	e_REASON2.setAttribute("checked","checked");;
		   }else{
			    e_REASON2.checked = false;
				};
		 var e_chReason1 = document.getElementById("chReason1");
		 var e_chReason2 = document.getElementById("chReason2");
			 if(REASON3 == 'on'){
				 e_chReason1.setAttribute("checked","checked");;
				   }else{
				 e_chReason1.checked = false;
				    };
		      if(REASON4 == 'on'){
		    	  e_chReason2.setAttribute("checked","checked");;
			 }else{
				 e_chReason2.checked = false;
				   };
		var e_juTiYuanYin = document.getElementById("juTiYuanYin");
		e_juTiYuanYin.innerHTML = "&nbsp;"+detailReason;
		var e_chJuTiYuanYin = document.getElementById("chJuTiYuanYin");
		e_chJuTiYuanYin.innerHTML = "&nbsp;"+detailReason2;
		    	
	    var e_isEquemail = document.getElementById("isEquemail");
	    var e_elEmail =  document.getElementById("elEmail");
	  	if(elecState == "1"){
	  		e_elEmail.innerHTML = Email1 ;
	  		if(isEquEmail == "1"){
	  			e_isEquemail.setAttribute("checked","checked");;
	  			}else{
	  			e_isEquemail.checked = false;	
	  			};	
	  	}else{
	  		e_elEmail.innerHTML = "_________________________" ;
	  		e_isEquemail.checked = false;
	  		};
	  	
	
      } 
 		}
	}
 		



//添加用户协议word转换后的html
 function loadCustAgreementHtml(){
	if($("#li-agreement").length > 0){
		$.ajax({
			url : basepath + '',
			type : "post",
			dataType : "json",
			data : {},
			beforeSend : function(){
				 myMask = new Ext.LoadMask(Ext.getBody(), {msg:"正在加载用户协议，请稍等..."});
				 myMask.show();
			},
			success : function(result) {
				 if(myMask){
				 	myMask.hide();
				 }
				if(result.msg){
					var wordHtml = result.msg;
					Ext.get("li-agreement").update(wordHtml);
					if(wordHtml.indexOf("<style") > -1 && wordHtml.indexOf("</style>") > -1){
						//var styleHtml = wordHtml.substring(wordHtml.indexOf("<style"), wordHtml.indexOf("</style>")+8);
						//$("head").append(styleHtml);
						//var bodyHtml = wordHtml.substring(wordHtml.indexOf("<body"), wordHtml.indexOf("</body>")+7);
						//$("#li-agreement").append(bodyHtml);
						//调整宽度
						//$("#li-agreement").height();

					}
				}
			},
			complete : function(){
				 if(myMask){
				 	myMask.hide();
				 }
			}
		});
	}
} 
</script>
</head>
<body onbeforePrint="beforePrint()"  onafterprint="afterPrint()">
	<div style="text-align:center" id='PrintButton' >
		<br/>
		<object ID='WebBrowser' WIDTH=0 HEIGHT=0 border=1  style="display:none" CLASSID='CLSID:8856F961-340A-11D0-A96B-00C04FD705A2' > </object>
		<input type=button value='确认打印' onclick="WebBrowser.ExecWB(6,1)">
		<input type=button value='打印预览' onclick="WebBrowser.ExecWB(7,1)">
		<input type=button value='打印设置' onclick="WebBrowser.ExecWB(8,1)">
		<input type=button value='关闭打印页面' onclick="WebBrowser.ExecWB(45,1)">
	</div>
	<div class="body" style="border:none;height:1200px;">
		<div style='padding: 5% 30% 0 50%'>
			<div style="font-family: Simsun; float: right;">
				<p class='p2'>客户号：</p>
				<span id='kehuhao' style="width: 150; float: right;"></span></font>
			</div>
			<br />
			<br />
			<br />
			<div style="font-family: Simsun; float: right;">
				<p class='p2'>账号：</p>
				<span id='zhanghao' style="width: 150; float: right;"></span></font>
			</div>
		</div>
		<div align=center style='padding: 20% 10% 10%'>
			<h1>
				<p class='p'>
					富邦华一银行<br /> 个人开户手册
				</p>
				</font>
			</h1>
		</div>
		<!-- padding:10% 50% 42% 20%; -->
		<div id="selfCheckList"
			style="font-family: Simsun; padding-left: 10%; padding-top: 10%;">
			<p class='p2'>自查清单</p>
			</font>
			<table style="border: none; width: 400px;">
				<tr>
					<td>&nbsp;
						<p class='p1'>□证件复印件+查验原件</p>
					</td>
				</tr>
				<tr>
					<td>&nbsp;
						<p class='p1'>□页码</p>
					</td>
				</tr>
				<tr>
					<td>&nbsp;
						<p class='p1'>□客户亲签</p>
					</td>
				</tr>
				<tr>
					<td>&nbsp;
						<p class='p1'>□双人见证</p>
					</td>
				</tr>
				<tr>
					<td>&nbsp;
						<p class='p1'>□借记卡</p>
					</td>
				</tr>
				<tr>
					<td>&nbsp;
						<p class='p1'>□U-Key & 密码函</p>
					</td>
				</tr>
			</table>
		</div>
		<div style="font-family: Simsun; float: right;">
			<br />
			<br />
			<br />
			<br />
			<br />
			<br />
			<br />
			<br />
			<br />
			<br />
			<br />
			<br />
			<br />
			<br />
			<br />
			<font size=3>版本：&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</font><span
				id='zhanghao' style="width: 150; float: right;"></span></font>
		</div>
	</div>
	<div style='page-break-before:always;'>
	</div>
	<div class="content">
		<div class="body" style="border-bottom:none;">
			<div id="custContent" class="document">
				<ul>
					<li class="title-row">&nbsp;客户信息</li>
					<li class="content-row">
						<div id="customerName" class="customer-detail" style="border-left-width: 0;"><b>姓名：</b>
						</div>
						<div class="customer-detail" id="customPinYin"><b>姓名拼音：</b></div>
					</li>
					<li class="content-row">
						<div id="identType1" class="customer-detail" style="border-left-width: 0;"><b>证件种类1：</b></div>
						<div id="identNo1" class="customer-detail"><b>证件号1：</b></div>
						<div id="LEGAL_EXPIRED_DATE1" class="customer-detail"><b>证件有效期1：</b></div>
					</li>
					<li class="content-row">
						<div id="identType2" class="customer-detail" style="border-left-width: 0;"><b>证件种类2：</b></div>
						<div id="identNo2" class="customer-detail"><b>证件号2：</b></div>
						<div id="LEGAL_EXPIRED_DATE2" class="customer-detail"><b>证件有效期2：</b></div>
					</li>
					<li id = 'tg' class="content-row"><span id = "tgIdentNum"><b>台湾身份证/港澳身份证号：</b></span></li>
					<li class="content-row" style="word-break:normal;height:2em;">
						<div style="border-right: 1px black solid;width:70%;height:100%;float: left;">
							<div id="MAIL_ADDR" style="width: 75%; float: left;"><b>邮寄地址：</b></div>
						</div>
						<div id = MAIL_ZIPCODE
							style="width: 29%; float: left; "><b>邮寄邮编：</b>
						</div>
					</li>
					<li id="mbPHONENUM" class="content-row"><b>移动电话：</b>
						<div style="width: 50%; float: right;"><b>（此电话将用于接收短信验证码和账务变动通知）</b></div>
					</li>
                    <li class="content-row-pro:1" style = 'border-bottom:0;'>
						<div>
							<b>&nbsp;本人声明：</b>
						</div>
						<b><div id='brIdentNo1'>
						&nbsp;证件号1：
						</div></b>
						<div>
						&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input
						type="checkbox" class = 'c2' disabled="disabled" name="shengMing" id="shengMing1" value="1"> 1、仅为中国税收居民&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						<input
						type="checkbox" class = 'c2' disabled="disabled" name="shengMing" id="shengMing2" value="2"> 2、仅为非居民<br/>
						&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input
						type="checkbox" class = 'c2' disabled="disabled" name="shengMing" id="shengMing3" value="3"> 3、既是中国税收居民又是其他国家（地区）税收居民&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						</div>
					</li>
					<li class="content-row" style = 'border-bottom:0;'><b>&nbsp;如您在以上选项中勾选第2项或者第3项，请填写下列信息：</b></li>
					<li class="">
                     <div style="width: 35%; float: left;"><b>1）是否为美国纳税人：</b>
                           <input type="checkbox" class = 'c2' disabled="disabled" name="isNaShui" id="isNaShui1" value="1"> 是&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                           <input type="checkbox" class = 'c2' disabled="disabled" name="isNaShui" id="isNaShui2" value="2"> 否</div>
                     <b><div id="USTIN" style="width: 65%; float: left;">（US TIN/SSN：________________________）</div></b>
                     	<div>
						<b>2)请填写您需履行纳税义务的所有税收居民国（地区）以及所关联的纳税人识别号（TIN）:</b>
						</div>
						<div id="taxInfo">
							<table  border = "1" style="border-collapse:collapse;">
								<thead>
					   				<th>序号</th>
					   				<th>税收居民国（地区）</th>
					   				<th>纳税人识别号（TIN）</th>
					   			</thead>
					   			<tbody>
					   				<tr>
					   					<td>&nbsp;</td>
					   					<td>&nbsp;</td>
					   					<td>&nbsp;</td>
					   				</tr>
					   			</tbody>
					   		</table>
						</div>
						<div>&nbsp;如您不能提供居民国（地区）纳税人识别号，请选择原因：</div>
						<div>
						     &nbsp;<input type="checkbox" class = 'c2' disabled="disabled" name="isNaShui" id="reason1" value="1"> 居民国（地区）不发放纳税人识别号<br/>
                             &nbsp;<input type="checkbox" class = 'c2' disabled="disabled" name="isNaShui" id="reason2" value="2"> 账户持有人未能取得纳税人识别号，如选此项，请解释具体原因：
                             <div id = 'juTiYuanYin'></div>
						</div>
					
					</li>
					 <li class="content-row-pro:1" style = 'border-bottom:0;'>
						<b><div id='brIdentNo2'>&nbsp;
						证件号2：
						</div></b>
						<div>
						&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input
						type="checkbox" class = 'c2' disabled="disabled" name="shengMing" id="chShengMing1" value="1"> 1、仅为中国税收居民&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						<input
						type="checkbox" class = 'c2' disabled="disabled" name="shengMing" id="chShengMing2" value="2"> 2、仅为非居民<br/>
						&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input
						type="checkbox" class = 'c2' disabled="disabled" name="shengMing" id="chShengMing3" value="3"> 3、既是中国税收居民又是其他国家（地区）税收居民&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						</div>
					</li>
					<li class="content-row" style = 'border-bottom:0;'><b>&nbsp;如您在以上选项中勾选第2项或者第3项，请填写下列信息：</b></li>
					<li class="">
                     <div style="width: 35%; float: left;"><b>1）是否为美国纳税人：</b>
                           <input type="checkbox" class = 'c2' disabled="disabled" name="isNaShui" id="chIsNaShui1" value="1"> 是&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                           <input type="checkbox" class = 'c2' disabled="disabled" name="isNaShui" id="chIsNaShui2" value="2"> 否</div>
                     <b><div id="chUSTIN" style="width: 65%; float: left;">（US TIN/SSN：________________________）</div></b>
                     	<div>
						<b>2)请填写您需履行纳税义务的所有税收居民国（地区）以及所关联的纳税人识别号（TIN）:</b>
						</div>
						<div id="taxInfo1">
							<table  border = "1" style="border-collapse:collapse;">
								<thead>
					   				<th>序号</th>
					   				<th>税收居民国（地区）</th>
					   				<th>纳税人识别号（TIN）</th>
					   			</thead>
					   			<tbody>
					   				<tr>
					   					<td>&nbsp;</td>
					   					<td>&nbsp;</td>
					   					<td>&nbsp;</td>
					   				</tr>
					   			</tbody>
					   		</table>
						</div>
						<div>&nbsp;如您不能提供居民国（地区）纳税人识别号，请选择原因：</div>
						<div>
						     &nbsp;<input type="checkbox" class = 'c2' disabled="disabled" name="chIsNaShui" id="chReason1" value="1"> 居民国（地区）不发放纳税人识别号<br/>
                             &nbsp;<input type="checkbox" class = 'c2' disabled="disabled" name="chIsNaShui" id="chReason2" value="2"> 账户持有人未能取得纳税人识别号，如选此项，请解释具体原因：
                             <div id = 'chJuTiYuanYin'></div>
						</div>
					
					</li>
					<li class="title-row">&nbsp;A账户信息</li>
					<li class="content-row-pro10">
						<div style="width: 50%; float: left;">
							<b>&nbsp;境内客户：</b><br /> 
							&nbsp;<input type="checkbox" class = 'c2' disabled="disabled" name="jingnei" id="jingnei1" value="一般综合账户"> 一般综合账户<br /> 
							&nbsp;<input type="checkbox" class = 'c2' disabled="disabled" name="jingnei" id="jingnei2" value="外汇结算户" > 外汇结算户<br />
							&nbsp;<input type="checkbox" class = 'c2' disabled="disabled" name="jingnei" id="jingnei4" value="外汇境内资产变现账户"> 外汇境内资产变现账户
						</div>
						<div style="width: 49%; float: left; border-left: 1px black solid;">
							<b>&nbsp;境外客户：</b><br /> 
							&nbsp;<input type="checkbox" class = 'c2' disabled="disabled" name="jingwai" id="jingwai1" value="一般综合账户"> 一般综合账户<br /> 
							&nbsp;<input type="checkbox" class = 'c2' disabled="disabled" name="jingwai" id="jingwai2" value="外汇结算户"> 外汇结算户<br />
							&nbsp;<input type="checkbox" class = 'c2' disabled="disabled" name="jingwai" id="jingwai4" value="外汇境内资产变现账户"> 港澳居民人民币储蓄账户<br /> 
							&nbsp;<input type="checkbox" class = 'c2' disabled="disabled" name="jingwai" id="jingwai6" value="外汇境内资产变现账户"> 人民币再投资专用结算账户
						</div>

					</li>
					<li class="title-row">&nbsp;B服务信息</li>
					<div style="width: 100%; height: 2.8em; border-bottom: 1px solid black;"><br/>
						&nbsp;<input type="checkbox" class = 'c2' disabled="disabled" name="adress" id="elecState" value=""><b>&nbsp;电子对账单 E-mail：</b>&nbsp;
						<span id = "elEmail">_________________________</span>（
						<input type="checkbox" class = 'c2' disabled="disabled" name="isEquemail" id="isEquemail" value="同邮件地址"><b> 同邮件地址</b>）
						
					</div>
					<div style="width: 100%; height: 2.8em; border-bottom: 1px solid black;"><br/>
						&nbsp;<input type="checkbox" class = 'c2' disabled="disabled" name="adress" id="chgNotice"
							value="账务变动通知 "><b>&nbsp;账务变动通知</b>
					</div>
				</ul>
			</div>
		</div>
	<div style='page-break-before:always;'><br/></div>
				<div style="border:none;margin: 0 auto;width:640pt">
	<!--用户协议部分-->
				<table class=MsoNormalTable border=1 cellspacing=0 cellpadding=0 width=640pt height=98%
	style='width:640pt; border-collapse: collapse; border: none; mso-border-alt: solid windowtext .5pt; mso-yfti-tbllook: 1184; mso-padding-alt: 0cm 5.4pt 0cm 5.4pt; mso-border-insideh: .5pt solid windowtext; mso-border-insidev: .5pt solid windowtext'>
	<tr style='mso-yfti-irow: 25; height: 27px'>
		<td width=640 colspan=11
			style='width:640pt; border: solid windowtext 1.0pt; mso-border-alt: solid windowtext .25pt; background: #4BACC6; padding: 0cm 5.4pt 0cm 5.4pt; height: 27px'>
			<p class=MsoNormalCxSpMiddle
				style='mso-line-height-alt: 12.0pt; layout-grid-mode: char'>
				<b style='mso-bidi-font-weight: normal'><span
					style='font-size: 17.0px; font-family: 宋体'>富邦华一银行个人开户及综合服务协议<span
						lang=EN-US><o:p></o:p>
					</span>
				</span>
				</b>
			</p></td>
	</tr>
	<tr style='mso-yfti-irow: 26; height: 29.7pt'>
		<td width=630 colspan=11 valign=top
			style='width: 640pt; border-top: none; border-left: solid windowtext 1.0pt; border-bottom: solid windowtext 0.5pt; border-right: solid windowtext 1.0pt; mso-border-top-alt: solid windowtext .25pt; mso-border-alt: solid windowtext .25pt; mso-border-bottom-alt: solid windowtext 1.5pt; background: transparent;
			 padding: 0cm 5.4pt 0cm 5.4pt; height: 29.7pt;'>
			<p class=MsoNormal
							style='text-indent: 18.0pt; mso-char-indent-count: 2.0; layout-grid-mode: char'>
							<span style='font-size: 10.5pt; font-family: 宋体'>客户（定义如下）兹向富邦华一银行（定义如下）申请开立个人账户，并依照客户之选择开通富邦华一银行提供的各项服务。为保证合法、规范使用个人账户，根据《人民币银行结算账户管理办法》及相关法律、法规，双方签订《富邦华一银行个人开户及综合服务协议书》（以下简称<span
								lang=EN-US>“</span>本协议<span lang=EN-US>”</span>），并于各适用服务的范围内，遵守以下条款：<span
								lang=EN-US><o:p></o:p> </span>
							</span>
						</p>
						<p class=MsoListParagraph align=center
							style='margin-top: 0cm; margin-right: 0cm; margin-top: 4pt; margin-bottom: 6pt; margin-left: 50.2pt; mso-para-margin-top: 0cm; mso-para-margin-right: 0cm; mso-para-margin-bottom: 1.0gd; mso-para-margin-left: 50.2pt; text-align: center; text-indent: -50.2pt; mso-char-indent-count: 0; mso-outline-level: 1; mso-list: l50 level1 lfo2; layout-grid-mode: char'>
							<a name="_Toc389184868"><![if !supportLists]><b
								style='mso-bidi-font-weight: normal'><span lang=EN-US
									style='font-size: 10.5pt; font-family: 宋体; mso-bidi-font-family: 宋体'><span
										style='mso-list: Ignore'>第一章<span
											style='font: 7.0pt "Times New Roman"'>&nbsp;&nbsp; </span>
									</span> </span> </b> <![endif]><b style='mso-bidi-font-weight: normal'><span
									style='font-size: 10.5pt; font-family: 宋体'>定义及解释</span> </b> </a><b
								style='mso-bidi-font-weight: normal'><span lang=EN-US
								style='font-size: 10.5pt; font-family: 宋体'><o:p></o:p> </span> </b>
						</p>
						<p class=MsoListParagraph
							style='margin-top: 0cm; margin-right: 0cm; margin-bottom: 7.8pt; margin-left: 63.8pt; mso-para-margin-top: 0cm; mso-para-margin-right: 0cm; mso-para-margin-bottom: .5gd; mso-para-margin-left: 63.8pt; text-indent: -63.8pt; mso-char-indent-count: 0; mso-list: l1 level1 lfo1; layout-grid-mode: char'>
							<![if !supportLists]>
							<b style='mso-bidi-font-weight: normal'><span lang=EN-US
								style='font-size: 10.5pt; font-family: 宋体; mso-bidi-font-family: 宋体'><span
									style='mso-list: Ignore'>第一条<span
										style='font: 7.0pt "Times New Roman"'>&nbsp;&nbsp; </span>
								</span> </span> </b>
							<![endif]>
							<span style='font-size: 10.5pt; font-family: 宋体'>&nbsp;&nbsp;如无特别说明，下列用语在本协议中的含义为：<span
								lang=EN-US><o:p></o:p> </span>
							</span>
						</p>

						<table class=MsoNormalTable border=0 cellspacing=0 cellpadding=0
							style='width: 610pt; margin-left: 5.4pt; border-collapse: collapse; mso-yfti-tbllook: 1184; mso-padding-alt: 0cm 5.4pt 0cm 5.4pt'>
							<tr style='mso-yfti-irow: 0; mso-yfti-firstrow: yes'>
								<td width=179 valign=top
									style='width: 165.55pt; padding: 0cm 5.4pt 0cm 5.4pt'>
									<p class=MsoNormal align=left
										style='margin-bottom: 7.8pt; mso-para-margin-bottom: .5gd; text-align: left; layout-grid-mode: char'>
										<b style='mso-bidi-font-weight: normal'><span lang=EN-US
											style='font-size: 10.5pt; font-family: 宋体'>“</span> </b><b
											style='mso-bidi-font-weight: normal'><span
											style='font-size: 10.5pt; font-family: 宋体'>银行<span
												lang=EN-US>”</span>或<span lang=EN-US>“</span>富邦华一银行<span
												lang=EN-US>”<o:p></o:p>
											</span>
										</span> </b>
									</p>
								</td>
								<td width=29 valign=top
									style='width: 21.85pt; padding: 0cm 5.4pt 0cm 5.4pt'>
									<p class=MsoNormal align=right
										style='margin-bottom: 7.8pt; mso-para-margin-bottom: .5gd; text-align: right; layout-grid-mode: char'>
										<b style='mso-bidi-font-weight: normal'><span
											style='font-size: 10.5pt; font-family: 宋体'>：</span> </b><span
											lang=EN-US style='font-size: 10.5pt; font-family: 宋体'><o:p></o:p>
										</span>
									</p>
								</td>
								<td width=359 valign=top
									style='width: 450.0pt; padding: 0cm 5.4pt 0cm 5.4pt'>
									<p class=MsoNormal align=left
										style='margin-bottom: 7.8pt; mso-para-margin-bottom: .5gd; text-align: left; layout-grid-mode: char'>
										<span style='font-size: 10.5pt; font-family: 宋体'>指富邦华一银行有限公司（包括总行及其各分支机构）。
											<span lang=EN-US><o:p></o:p> </span>
										</span>
									</p>
								</td>
							</tr>
							<tr style='mso-yfti-irow: 1'>
								<td width=179 valign=top
									style='width: 134.55pt; padding: 0cm 5.4pt 0cm 5.4pt'>
									<p class=MsoNormal align=left
										style='margin-bottom: 7.8pt; mso-para-margin-bottom: .5gd; text-align: left; layout-grid-mode: char'>
										<b style='mso-bidi-font-weight: normal'><span lang=EN-US
											style='font-size: 10.5pt; font-family: 宋体'>“</span> </b><b
											style='mso-bidi-font-weight: normal'><span
											style='font-size: 10.5pt; font-family: 宋体'>客户<span
												lang=EN-US>”<o:p></o:p>
											</span>
										</span> </b>
									</p>
								</td>
								<td width=29 valign=top
									style='width: 21.85pt; padding: 0cm 5.4pt 0cm 5.4pt'>
									<p class=MsoNormal align=right
										style='margin-bottom: 7.8pt; mso-para-margin-bottom: .5gd; text-align: right; layout-grid-mode: char'>
										<b style='mso-bidi-font-weight: normal'><span
											style='font-size: 10.5pt; font-family: 宋体'>：</span> </b><span
											lang=EN-US style='font-size: 10.5pt; font-family: 宋体'><o:p></o:p>
										</span>
									</p>
								</td>
								<td valign=top
									style='width: 450.0pt; padding: 0cm 5.4pt 0cm 5.4pt'>
									<p class=MsoNormal align=left
										style='margin-bottom: 7.8pt; mso-para-margin-bottom: .5gd; text-align: left; layout-grid-mode: char'>
										<span style='font-size: 10.5pt; font-family: 宋体'>指申请在银行开立个人账户，并基于有关协议，与银行有金融业务往来的客户。<span
											lang=EN-US><o:p></o:p> </span>
										</span>
									</p>
								</td>
							</tr>
							<tr style='mso-yfti-irow: 2'>
								<td width=179 valign=top
									style='width: 134.55pt; padding: 0cm 5.4pt 0cm 5.4pt'>
									<p class=MsoNormal align=left
										style='margin-bottom: 7.8pt; mso-para-margin-bottom: .5gd; text-align: left; layout-grid-mode: char'>
										<b style='mso-bidi-font-weight: normal'><span lang=EN-US
											style='font-size: 10.5pt; font-family: 宋体'>“</span> </b><b
											style='mso-bidi-font-weight: normal'><span
											style='font-size: 10.5pt; font-family: 宋体'>客户资料<span
												lang=EN-US>”<o:p></o:p>
											</span>
										</span> </b>
									</p>
								</td>
								<td width=29 valign=top
									style='width: 21.85pt; padding: 0cm 5.4pt 0cm 5.4pt'>
									<p class=MsoNormal align=right
										style='margin-bottom: 7.8pt; mso-para-margin-bottom: .5gd; text-align: right; layout-grid-mode: char'>
										<b style='mso-bidi-font-weight: normal'><span
											style='font-size: 10.5pt; font-family: 宋体'>：<span
												lang=EN-US><o:p></o:p> </span>
										</span> </b>
									</p>
								</td>
								<td width=359 valign=top
									style='width: 450.0pt; padding: 0cm 5.4pt 0cm 5.4pt'>
									<p class=MsoNormal align=left
										style='margin-bottom: 7.8pt; mso-para-margin-bottom: .5gd; text-align: left; layout-grid-mode: char'>
										<span style='font-size: 10.5pt; font-family: 宋体'>指银行不时取得的与客户有关的信息和资料以及与任何实际或可能的保证人或担保提供人（若有）和<span
											lang=EN-US>/</span>或其他相关人员和实体有关的信息和资料，形式上包括但不限于各类书面文件、电子数据、视听资料及电话录音等。<span
											lang=EN-US><o:p></o:p> </span>
										</span>
									</p>
								</td>
							</tr>
							<tr style='mso-yfti-irow: 3'>
								<td width=179 valign=top
									style='width: 134.55pt; padding: 0cm 5.4pt 0cm 5.4pt'>
									<p class=MsoNormal align=left
										style='margin-bottom: 7.8pt; mso-para-margin-bottom: .5gd; text-align: left; layout-grid-mode: char'>
										<b style='mso-bidi-font-weight: normal'><span lang=EN-US
											style='font-size: 10.5pt; font-family: 宋体'>“</span> </b><b
											style='mso-bidi-font-weight: normal'><span
											style='font-size: 10.5pt; font-family: 宋体'>开户手册<span
												lang=EN-US>”<o:p></o:p>
											</span>
										</span> </b>
									</p>
								</td>
								<td width=29 valign=top
									style='width: 21.85pt; padding: 0cm 5.4pt 0cm 5.4pt'>
									<p class=MsoNormal align=right
										style='margin-bottom: 7.8pt; mso-para-margin-bottom: .5gd; text-align: right; layout-grid-mode: char'>
										<b style='mso-bidi-font-weight: normal'><span
											style='font-size: 10.5pt; font-family: 宋体'>：<span
												lang=EN-US><o:p></o:p> </span>
										</span> </b>
									</p>
								</td>
								<td width=359 valign=top
									style='width: 450pt; padding: 0cm 5.4pt 0cm 5.4pt'>
									<p class=MsoNormal align=left
										style='margin-bottom: 7.8pt; mso-para-margin-bottom: .5gd; text-align: left; layout-grid-mode: char'>
										<span style='font-size: 10.5pt; font-family: 宋体'>指《富邦华一银行开户手册》。<span
											lang=EN-US><o:p></o:p> </span>
										</span>
									</p>
								</td>
							</tr>
							<tr style='mso-yfti-irow: 4'>
								<td width=179 valign=top
									style='width: 134.55pt; padding: 0cm 5.4pt 0cm 5.4pt'>
									<p class=MsoNormal align=left
										style='margin-bottom: 7.8pt; mso-para-margin-bottom: .5gd; text-align: left; layout-grid-mode: char'>
										<b style='mso-bidi-font-weight: normal'><span lang=EN-US
											style='font-size: 10.5pt; font-family: 宋体'>“</span> </b><b
											style='mso-bidi-font-weight: normal'><span
											style='font-size: 10.5pt; font-family: 宋体'>富邦金控成员<span
												lang=EN-US>”<o:p></o:p>
											</span>
										</span> </b>
									</p>
								</td>
								<td width=29 valign=top
									style='width: 21.85pt; padding: 0cm 5.4pt 0cm 5.4pt'>
									<p class=MsoNormal align=right
										style='margin-bottom: 7.8pt; mso-para-margin-bottom: .5gd; text-align: right; layout-grid-mode: char'>
										<b style='mso-bidi-font-weight: normal'><span
											style='font-size: 10.5pt; font-family: 宋体'>：<span
												lang=EN-US><o:p></o:p> </span>
										</span> </b>
									</p>
								</td>
								<td width=359 valign=top
									style='width: 450pt; padding: 0cm 5.4pt 0cm 5.4pt'>
									<p class=MsoNormal align=left
										style='margin-bottom: 7.8pt; mso-para-margin-bottom: .5gd; text-align: left; layout-grid-mode: char'>
										<span style='font-size: 10.5pt; font-family: 宋体'>指富邦金融控股股份有限公司及其直接或间接投资的公司、企业或其他组织。<span
											lang=EN-US><o:p></o:p> </span>
										</span>
									</p>
								</td>
							</tr>
							<tr style='mso-yfti-irow: 5'>
								<td width=179 valign=top
									style='width: 134.55pt; padding: 0cm 5.4pt 0cm 5.4pt'>
									<p class=MsoNormal align=left
										style='margin-bottom: 7.8pt; mso-para-margin-bottom: .5gd; text-align: left; layout-grid-mode: char'>
										<b style='mso-bidi-font-weight: normal'><span lang=EN-US
											style='font-size: 10.5pt; font-family: 宋体'>“</span> </b><b
											style='mso-bidi-font-weight: normal'><span
											style='font-size: 10.5pt; font-family: 宋体'>账户<span
												lang=EN-US>”<o:p></o:p>
											</span>
										</span> </b>
									</p>
								</td>
								<td width=29 valign=top
									style='width: 21.85pt; padding: 0cm 5.4pt 0cm 5.4pt'>
									<p class=MsoNormal align=right
										style='margin-bottom: 7.8pt; mso-para-margin-bottom: .5gd; text-align: right; layout-grid-mode: char'>
										<b style='mso-bidi-font-weight: normal'><span
											style='font-size: 10.5pt; font-family: 宋体'>：<span
												lang=EN-US><o:p></o:p> </span>
										</span> </b>
									</p>
								</td>
								<td width=359 valign=top
									style='width: 450pt; padding: 0cm 5.4pt 0cm 5.4pt'>
									<p class=MsoNormal align=left
										style='margin-bottom: 7.8pt; mso-para-margin-bottom: .5gd; text-align: left; layout-grid-mode: char'>
										<span style='font-size: 10.5pt; font-family: 宋体'>指客户在银行开立的全部或任一类型的银行账户。<span
											lang=EN-US><o:p></o:p> </span>
										</span>
									</p>
								</td>
							</tr>
							<tr style='mso-yfti-irow: 6'>
								<td width=179 valign=top
									style='width: 134.55pt; padding: 0cm 5.4pt 0cm 5.4pt'>
									<p class=MsoNormal align=left
										style='margin-bottom: 7.8pt; mso-para-margin-bottom: .5gd; text-align: left; layout-grid-mode: char'>
										<b style='mso-bidi-font-weight: normal'><span lang=EN-US
											style='font-size: 10.5pt; font-family: 宋体'>“</span> </b><b
											style='mso-bidi-font-weight: normal'><span
											style='font-size: 10.5pt; font-family: 宋体'>一般综合账户<span
												lang=EN-US>”<o:p></o:p>
											</span>
										</span> </b>
									</p>
								</td>
								<td width=29 valign=top
									style='width: 21.85pt; padding: 0cm 5.4pt 0cm 5.4pt'>
									<p class=MsoNormal align=right
										style='margin-bottom: 7.8pt; mso-para-margin-bottom: .5gd; text-align: right; layout-grid-mode: char'>
										<b style='mso-bidi-font-weight: normal'><span
											style='font-size: 10.5pt; font-family: 宋体'>：<span
												lang=EN-US><o:p></o:p> </span>
										</span> </b>
									</p>
								</td>
								<td width=359 valign=top
									style='width: 450pt; padding: 0cm 5.4pt 0cm 5.4pt'>
									<p class=MsoNormal align=left
										style='margin-bottom: 7.8pt; mso-para-margin-bottom: .5gd; text-align: left; layout-grid-mode: char'>
										<span style='font-size: 10.5pt; font-family: 宋体'>指在银行所获相关业务许可的范围内，客户在银行开立的涵盖人民币结算账户及<span
											lang=EN-US>/</span>或外汇账户的综合性账户。<span lang=EN-US><o:p></o:p>
										</span>
										</span>
									</p>
								</td>
							</tr>
							<tr style='mso-yfti-irow: 7'>
								<td width=179 valign=top
									style='width: 134.55pt; padding: 0cm 5.4pt 0cm 5.4pt'>
									<p class=MsoNormal align=left
										style='margin-bottom: 7.8pt; mso-para-margin-bottom: .5gd; text-align: left; layout-grid-mode: char'>
										<b style='mso-bidi-font-weight: normal'><span lang=EN-US
											style='font-size: 10.5pt; font-family: 宋体'>“</span> </b><b
											style='mso-bidi-font-weight: normal'><span
											style='font-size: 10.5pt; font-family: 宋体'>人民币结算账户<span
												lang=EN-US>”<o:p></o:p>
											</span>
										</span> </b>
									</p>
								</td>
								<td width=29 valign=top
									style='width: 21.85pt; padding: 0cm 5.4pt 0cm 5.4pt'>
									<p class=MsoNormal align=right
										style='margin-bottom: 7.8pt; mso-para-margin-bottom: .5gd; text-align: right; layout-grid-mode: char'>
										<b style='mso-bidi-font-weight: normal'><span
											style='font-size: 10.5pt; font-family: 宋体'>：<span
												lang=EN-US><o:p></o:p> </span>
										</span> </b>
									</p>
								</td>
								<td width=359 valign=top
									style='width: 450pt; padding: 0cm 5.4pt 0cm 5.4pt'>
									<p class=MsoNormal align=left
										style='margin-bottom: 7.8pt; mso-para-margin-bottom: .5gd; text-align: left; layout-grid-mode: char'>
										<span style='font-size: 10.5pt; font-family: 宋体'>指依据管理办法中规定的人民币银行结算账户，即客户凭个人身份证件以自然人名称在银行开立的具有资金收付结算功能的人民币活期存款账户的总称。在开户手册中具体是指含有<span
											lang=EN-US>“</span>人民币<span lang=EN-US>”</span>及<span
											lang=EN-US>“</span>结算<span lang=EN-US>”</span>字样的账户类型。<span
											lang=EN-US><o:p></o:p> </span>
										</span>
									</p>
								</td>
							</tr>
							<tr style='mso-yfti-irow: 8'>
								<td width=179 valign=top
									style='width: 134.55pt; padding: 0cm 5.4pt 0cm 5.4pt'>
									<p class=MsoNormal align=left
										style='margin-bottom: 7.8pt; mso-para-margin-bottom: .5gd; text-align: left; layout-grid-mode: char'>
										<b style='mso-bidi-font-weight: normal'><span lang=EN-US
											style='font-size: 10.5pt; font-family: 宋体'>“</span> </b><b
											style='mso-bidi-font-weight: normal'><span
											style='font-size: 10.5pt; font-family: 宋体'>人民币储蓄账户<span
												lang=EN-US>”<o:p></o:p>
											</span>
										</span> </b>
									</p>
								</td>
								<td width=29 valign=top
									style='width: 21.85pt; padding: 0cm 5.4pt 0cm 5.4pt'>
									<p class=MsoNormal align=right
										style='margin-bottom: 7.8pt; mso-para-margin-bottom: .5gd; text-align: right; layout-grid-mode: char'>
										<b style='mso-bidi-font-weight: normal'><span
											style='font-size: 10.5pt; font-family: 宋体'>：<span
												lang=EN-US><o:p></o:p> </span>
										</span> </b>
									</p>
								</td>
								<td width=359 valign=top
									style='width: 450pt; padding: 0cm 5.4pt 0cm 5.4pt'>
									<p class=MsoNormal align=left
										style='margin-bottom: 7.8pt; mso-para-margin-bottom: .5gd; text-align: left; layout-grid-mode: char'>
										<span style='font-size: 10.5pt; font-family: 宋体'>指客户凭个人有效身份证件以自然人名称在银行开立的办理资金存取业务的人民币储蓄存款账户。在开户手册中具体是指含有<span
											lang=EN-US>“</span>人民币<span lang=EN-US>”</span>及<span
											lang=EN-US>“</span>储蓄<span lang=EN-US>”</span>字样的账户类型。<span
											lang=EN-US><o:p></o:p> </span>
										</span>
									</p>
								</td>
							</tr>
							<tr style='mso-yfti-irow: 9'>
								<td width=179 valign=top
									style='width: 134.55pt; padding: 0cm 5.4pt 0cm 5.4pt'>
									<p class=MsoNormal align=left
										style='margin-bottom: 7.8pt; mso-para-margin-bottom: .5gd; text-align: left; layout-grid-mode: char'>
										<b style='mso-bidi-font-weight: normal'><span lang=EN-US
											style='font-size: 10.5pt; font-family: 宋体'>“</span> </b><b
											style='mso-bidi-font-weight: normal'><span
											style='font-size: 10.5pt; font-family: 宋体'>外汇账户<span
												lang=EN-US>”<o:p></o:p>
											</span>
										</span> </b>
									</p>
								</td>
								<td width=29 valign=top
									style='width: 21.85pt; padding: 0cm 5.4pt 0cm 5.4pt'>
									<p class=MsoNormal align=right
										style='margin-bottom: 7.8pt; mso-para-margin-bottom: .5gd; text-align: right; layout-grid-mode: char'>
										<b style='mso-bidi-font-weight: normal'><span
											style='font-size: 10.5pt; font-family: 宋体'>：<span
												lang=EN-US><o:p></o:p> </span>
										</span> </b>
									</p>
								</td>
								<td width=359 valign=top
									style='width: 450pt; padding: 0cm 5.4pt 0cm 5.4pt'>
									<p class=MsoNormal align=left
										style='margin-bottom: 7.8pt; mso-para-margin-bottom: .5gd; text-align: left; layout-grid-mode: char'>
										<span style='font-size: 10.5pt; font-family: 宋体'>指经国家外汇管理局批准，客户按照有关规定在银行以可自由兑换货币开立的账户类型总称。在开户手册中具体是指含有<span
											lang=EN-US>“</span>外汇<span lang=EN-US>”</span>字样的账户类型。<span
											lang=EN-US><o:p></o:p> </span>
										</span>
									</p>
								</td>
							</tr>
							<tr style='mso-yfti-irow: 10'>
								<td width=179 valign=top
									style='width: 134.55pt; padding: 0cm 5.4pt 0cm 5.4pt'>
									<p class=MsoNormal align=left
										style='margin-bottom: 7.8pt; mso-para-margin-bottom: .5gd; text-align: left; layout-grid-mode: char'>
										<b style='mso-bidi-font-weight: normal'><span lang=EN-US
											style='font-size: 10.5pt; font-family: 宋体'>“</span> </b><b
											style='mso-bidi-font-weight: normal'><span
											style='font-size: 10.5pt; font-family: 宋体'>开户申请资料<span
												lang=EN-US>”<o:p></o:p>
											</span>
										</span> </b>
									</p>
								</td>
								<td width=29 valign=top
									style='width: 21.85pt; padding: 0cm 5.4pt 0cm 5.4pt'>
									<p class=MsoNormal align=right
										style='margin-bottom: 7.8pt; mso-para-margin-bottom: .5gd; text-align: right; layout-grid-mode: char'>
										<b style='mso-bidi-font-weight: normal'><span
											style='font-size: 10.5pt; font-family: 宋体'>：<span
												lang=EN-US><o:p></o:p> </span>
										</span> </b>
									</p>
								</td>
								<td width=359 valign=top
									style='width: 450pt; padding: 0cm 5.4pt 0cm 5.4pt'>
									<p class=MsoNormal align=left
										style='margin-bottom: 7.8pt; mso-para-margin-bottom: .5gd; text-align: left; layout-grid-mode: char'>
										<span style='font-size: 10.5pt; font-family: 宋体'>指客户申请开立人民币结算账户、人民币储蓄账户、外汇账户及<span
											lang=EN-US>/</span>或开通各项银行服务的申请和文件。<span lang=EN-US><o:p></o:p>
										</span>
										</span>
									</p>
								</td>
							</tr>
							<tr style='mso-yfti-irow: 11'>
								<td width=179 valign=top
									style='width: 134.55pt; padding: 0cm 5.4pt 0cm 5.4pt'>
									<p class=MsoNormal align=left
										style='margin-bottom: 7.8pt; mso-para-margin-bottom: .5gd; text-align: left; layout-grid-mode: char'>
										<b style='mso-bidi-font-weight: normal'><span lang=EN-US
											style='font-size: 10.5pt; font-family: 宋体'>“</span> </b><b
											style='mso-bidi-font-weight: normal'><span
											style='font-size: 10.5pt; font-family: 宋体'>借记卡<span
												lang=EN-US>”<o:p></o:p>
											</span>
										</span> </b>
									</p>
								</td>
								<td width=29 valign=top
									style='width: 21.85pt; padding: 0cm 5.4pt 0cm 5.4pt'>
									<p class=MsoNormal align=right
										style='margin-bottom: 7.8pt; mso-para-margin-bottom: .5gd; text-align: right; layout-grid-mode: char'>
										<b style='mso-bidi-font-weight: normal'><span
											style='font-size: 10.5pt; font-family: 宋体'>：<span
												lang=EN-US><o:p></o:p> </span>
										</span> </b>
									</p>
								</td>
								<td width=359 valign=top
									style='width: 450pt; padding: 0cm 5.4pt 0cm 5.4pt'>
									<p class=MsoNormal align=left
										style='margin-bottom: 7.8pt; mso-para-margin-bottom: .5gd; text-align: left; layout-grid-mode: char'>
										<span style='font-size: 10.5pt; font-family: 宋体'>指由银行发行、可供个人持卡人在银行自动柜员机、银联自动柜员机和商户终端上使用的具有消费结算、存取现金、转账汇款、投资理财等功能的磁条芯片复合卡。<span
											lang=EN-US><o:p></o:p> </span>
										</span>
									</p>
								</td>
							</tr>
							<tr style='mso-yfti-irow: 12'>
								<td width=179 valign=top
									style='width: 134.55pt; padding: 0cm 5.4pt 0cm 5.4pt'>
									<p class=MsoNormal align=left
										style='margin-bottom: 7.8pt; mso-para-margin-bottom: .5gd; text-align: left; layout-grid-mode: char'>
										<b style='mso-bidi-font-weight: normal'><span lang=EN-US
											style='font-size: 10.5pt; font-family: 宋体'>“</span> </b><b
											style='mso-bidi-font-weight: normal'><span
											style='font-size: 10.5pt; font-family: 宋体'>借记卡章程<span
												lang=EN-US>”<o:p></o:p>
											</span>
										</span> </b>
									</p>
								</td>
								<td width=29 valign=top
									style='width: 21.85pt; padding: 0cm 5.4pt 0cm 5.4pt'>
									<p class=MsoNormal align=right
										style='margin-bottom: 7.8pt; mso-para-margin-bottom: .5gd; text-align: right; layout-grid-mode: char'>
										<b style='mso-bidi-font-weight: normal'><span
											style='font-size: 10.5pt; font-family: 宋体'>：<span
												lang=EN-US><o:p></o:p> </span>
										</span> </b>
									</p>
								</td>
								<td width=359 valign=top
									style='width: 450pt; padding: 0cm 5.4pt 0cm 5.4pt'>
									<p class=MsoNormal align=left
										style='margin-bottom: 7.8pt; mso-para-margin-bottom: .5gd; text-align: left; layout-grid-mode: char'>
										<span style='font-size: 10.5pt; font-family: 宋体'>指银行制定的、并不时修订的《富邦华一银行个人借记卡章程》。<span
											lang=EN-US><o:p></o:p> </span>
										</span>
									</p>
								</td>
							</tr>
							<tr style='mso-yfti-irow: 13'>
								<td width=179 valign=top
									style='width: 134.55pt; padding: 0cm 5.4pt 0cm 5.4pt'>
									<p class=MsoNormal align=left
										style='margin-bottom: 7.8pt; mso-para-margin-bottom: .5gd; text-align: left; layout-grid-mode: char'>
										<b style='mso-bidi-font-weight: normal'><span lang=EN-US
											style='font-size: 10.5pt; font-family: 宋体'>“</span> </b><b
											style='mso-bidi-font-weight: normal'><span
											style='font-size: 10.5pt; font-family: 宋体'>未成年人<span
												lang=EN-US>”<o:p></o:p>
											</span>
										</span> </b>
									</p>
								</td>
								<td width=29 valign=top
									style='width: 21.85pt; padding: 0cm 5.4pt 0cm 5.4pt'>
									<p class=MsoNormal align=right
										style='margin-bottom: 7.8pt; mso-para-margin-bottom: .5gd; text-align: right; layout-grid-mode: char'>
										<b style='mso-bidi-font-weight: normal'><span
											style='font-size: 10.5pt; font-family: 宋体'>：<span
												lang=EN-US><o:p></o:p> </span>
										</span> </b>
									</p>
								</td>
								<td width=359 valign=top
									style='width: 450pt; padding: 0cm 5.4pt 0cm 5.4pt'>
									<p class=MsoNormal align=left
										style='margin-bottom: 7.8pt; mso-para-margin-bottom: .5gd; text-align: left; layout-grid-mode: char'>
										<span style='font-size: 10.5pt; font-family: 宋体'>指未满十八周岁的公民。<span
											lang=EN-US><o:p></o:p> </span>
										</span>
									</p>
								</td>
							</tr>
							<tr style='mso-yfti-irow: 14'>
								<td width=179 valign=top
									style='width: 134.55pt; padding: 0cm 5.4pt 0cm 5.4pt'>
									<p class=MsoNormal align=left
										style='margin-bottom: 7.8pt; mso-para-margin-bottom: .5gd; text-align: left; layout-grid-mode: char'>
										<b style='mso-bidi-font-weight: normal'><span lang=EN-US
											style='font-size: 10.5pt; font-family: 宋体'>“</span> </b><b
											style='mso-bidi-font-weight: normal'><span
											style='font-size: 10.5pt; font-family: 宋体'>监护人<span
												lang=EN-US>”<o:p></o:p>
											</span>
										</span> </b>
									</p>
								</td>
								<td width=29 valign=top
									style='width: 21.85pt; padding: 0cm 5.4pt 0cm 5.4pt'>
									<p class=MsoNormal align=right
										style='margin-bottom: 7.8pt; mso-para-margin-bottom: .5gd; text-align: right; layout-grid-mode: char'>
										<b style='mso-bidi-font-weight: normal'><span
											style='font-size: 10.5pt; font-family: 宋体'>：<span
												lang=EN-US><o:p></o:p> </span>
										</span> </b>
									</p>
								</td>
								<td width=359 valign=top
									style='width: 450pt; padding: 0cm 5.4pt 0cm 5.4pt'>
									<p class=MsoNormal align=left
										style='margin-bottom: 7.8pt; mso-para-margin-bottom: .5gd; text-align: left; layout-grid-mode: char'>
										<span style='font-size: 10.5pt; font-family: 宋体'>指根据法律规定，对未成年的人身、财产和其他合法权益负有监督和保护责任的人。<span
											lang=EN-US><a
												href="http://baike.baidu.com/view/308753.htm"
												target="_blank"><span lang=EN-US
													style='color: windowtext; text-decoration: none; text-underline: none'><span
														lang=EN-US></span> </span> </a> </span><span lang=EN-US><a
												href="http://baike.baidu.com/view/13792.htm" target="_blank"><span
													lang=EN-US
													style='color: windowtext; text-decoration: none; text-underline: none'><span
														lang=EN-US></span> </span> </a> </span><span lang=EN-US><o:p></o:p> </span>
										</span>
									</p>
								</td>
							</tr>
							<tr style='mso-yfti-irow: 15'>
								<td width=179 valign=top
									style='width: 134.55pt; padding: 0cm 5.4pt 0cm 5.4pt'>
									<p class=MsoNormal align=left
										style='margin-bottom: 7.8pt; mso-para-margin-bottom: .5gd; text-align: left; layout-grid-mode: char'>
										<b style='mso-bidi-font-weight: normal'><span lang=EN-US
											style='font-size: 10.5pt; font-family: 宋体'>“</span> </b><b
											style='mso-bidi-font-weight: normal'><span
											style='font-size: 10.5pt; font-family: 宋体'>持卡人<span
												lang=EN-US>”<o:p></o:p>
											</span>
										</span> </b>
									</p>
								</td>
								<td width=29 valign=top
									style='width: 21.85pt; padding: 0cm 5.4pt 0cm 5.4pt'>
									<p class=MsoNormal align=right
										style='margin-bottom: 7.8pt; mso-para-margin-bottom: .5gd; text-align: right; layout-grid-mode: char'>
										<b style='mso-bidi-font-weight: normal'><span
											style='font-size: 10.5pt; font-family: 宋体'>：<span
												lang=EN-US><o:p></o:p> </span>
										</span> </b>
									</p>
								</td>
								<td width=359 valign=top
									style='width: 450pt; padding: 0cm 5.4pt 0cm 5.4pt'>
									<p class=MsoNormal align=left
										style='margin-bottom: 7.8pt; mso-para-margin-bottom: .5gd; text-align: left; layout-grid-mode: char'>
										<span style='font-size: 10.5pt; font-family: 宋体'>指向银行申请借记卡并获得卡片核发的个人。<span
											lang=EN-US><o:p></o:p> </span>
										</span>
									</p>
								</td>
							</tr>
							<tr style='mso-yfti-irow: 16'>
								<td width=179 valign=top
									style='width: 134.55pt; padding: 0cm 5.4pt 0cm 5.4pt'>
									<p class=MsoNormal align=left
										style='margin-bottom: 7.8pt; mso-para-margin-bottom: .5gd; text-align: left; layout-grid-mode: char'>
										<b style='mso-bidi-font-weight: normal'><span lang=EN-US
											style='font-size: 10.5pt; font-family: 宋体'>“</span> </b><b
											style='mso-bidi-font-weight: normal'><span
											style='font-size: 10.5pt; font-family: 宋体'>电子现金账户<span
												lang=EN-US>”<o:p></o:p>
											</span>
										</span> </b>
									</p>
								</td>
								<td width=29 valign=top
									style='width: 21.85pt; padding: 0cm 5.4pt 0cm 5.4pt'>
									<p class=MsoNormal align=right
										style='margin-bottom: 7.8pt; mso-para-margin-bottom: .5gd; text-align: right; layout-grid-mode: char'>
										<b style='mso-bidi-font-weight: normal'><span
											style='font-size: 10.5pt; font-family: 宋体'>：<span
												lang=EN-US><o:p></o:p> </span>
										</span> </b>
									</p>
								</td>
								<td width=359 valign=top
									style='width: 450pt; padding: 0cm 5.4pt 0cm 5.4pt'>
									<p class=MsoNormal align=left
										style='margin-bottom: 7.8pt; mso-para-margin-bottom: .5gd; text-align: left; layout-grid-mode: char'>
										<span style='font-size: 10.5pt; font-family: 宋体'>指以借记卡为载体的具有小额脱机消费、绑定账户充值、圈提、查询等功能的模拟现金账户。<span
											lang=EN-US><o:p></o:p> </span>
										</span>
									</p>
								</td>
							</tr>
							<tr style='mso-yfti-irow: 17'>
								<td width=179 valign=top
									style='width: 134.55pt; padding: 0cm 5.4pt 0cm 5.4pt'>
									<p class=MsoNormal align=left
										style='margin-bottom: 7.8pt; mso-para-margin-bottom: .5gd; text-align: left; layout-grid-mode: char'>
										<b style='mso-bidi-font-weight: normal'><span lang=EN-US
											style='font-size: 10.5pt; font-family: 宋体'>“</span> </b><b
											style='mso-bidi-font-weight: normal'><span
											style='font-size: 10.5pt; font-family: 宋体'>电子银行<span
												lang=EN-US>”<o:p></o:p>
											</span>
										</span> </b>
									</p>
								</td>
								<td width=29 valign=top
									style='width: 21.85pt; padding: 0cm 5.4pt 0cm 5.4pt'>
									<p class=MsoNormal align=right
										style='margin-bottom: 7.8pt; mso-para-margin-bottom: .5gd; text-align: right; layout-grid-mode: char'>
										<b style='mso-bidi-font-weight: normal'><span
											style='font-size: 10.5pt; font-family: 宋体'>：</span> </b><span
											lang=EN-US style='font-size: 10.5pt; font-family: 宋体'><o:p></o:p>
										</span>
									</p>
								</td>
								<td width=359 valign=top
									style='width: 450pt; padding: 0cm 5.4pt 0cm 5.4pt'>
									<p class=MsoNormal align=left
										style='margin-bottom: 7.8pt; mso-para-margin-bottom: .5gd; text-align: left; layout-grid-mode: char'>
										<span style='font-size: 10.5pt; font-family: 宋体'>指通过网络和电子终端为客户提供自助金融服务的虚拟银行。电子银行通过网络银行、电话银行、手机银行、微信银行等为客户提供多种金融服务。<span
											lang=EN-US><o:p></o:p> </span>
										</span>
									</p>
								</td>
							</tr>
							<tr style='mso-yfti-irow: 18'>
								<td width=179 valign=top
									style='width: 134.55pt; padding: 0cm 5.4pt 0cm 5.4pt'>
									<p class=MsoNormal align=left
										style='margin-bottom: 7.8pt; mso-para-margin-bottom: .5gd; text-align: left; layout-grid-mode: char'>
										<b style='mso-bidi-font-weight: normal'><span lang=EN-US
											style='font-size: 10.5pt; font-family: 宋体'>“</span> </b><b
											style='mso-bidi-font-weight: normal'><span
											style='font-size: 10.5pt; font-family: 宋体'>网站<span
												lang=EN-US>”<o:p></o:p>
											</span>
										</span> </b>
									</p>
								</td>
								<td width=29 valign=top
									style='width: 21.85pt; padding: 0cm 5.4pt 0cm 5.4pt'>
									<p class=MsoNormal align=right
										style='margin-bottom: 7.8pt; mso-para-margin-bottom: .5gd; text-align: right; layout-grid-mode: char'>
										<b style='mso-bidi-font-weight: normal'><span
											style='font-size: 10.5pt; font-family: 宋体'>：<span
												lang=EN-US><o:p></o:p> </span>
										</span> </b>
									</p>
								</td>
								<td width=359 valign=top
									style='width: 450pt; padding: 0cm 5.4pt 0cm 5.4pt'>
									<p class=MsoNormal align=left
										style='margin-bottom: 7.8pt; mso-para-margin-bottom: .5gd; text-align: left; layout-grid-mode: char'>
										<span style='font-size: 10.5pt; font-family: 宋体'>指富邦华一银行有限公司的官方网站。<span
											lang=EN-US><o:p></o:p> </span>
										</span>
									</p>
								</td>
							</tr>
							<tr style='mso-yfti-irow: 19'>
								<td width=179 valign=top
									style='width: 134.55pt; padding: 0cm 5.4pt 0cm 5.4pt'>
									<p class=MsoNormal align=left
										style='margin-bottom: 7.8pt; mso-para-margin-bottom: .5gd; text-align: left; layout-grid-mode: char'>
										<b style='mso-bidi-font-weight: normal'><span lang=EN-US
											style='font-size: 10.5pt; font-family: 宋体'>“</span> </b><b
											style='mso-bidi-font-weight: normal'><span
											style='font-size: 10.5pt; font-family: 宋体'>数字证书<span
												lang=EN-US>”<o:p></o:p>
											</span>
										</span> </b>
									</p>
								</td>
								<td width=29 valign=top
									style='width: 21.85pt; padding: 0cm 5.4pt 0cm 5.4pt'>
									<p class=MsoNormal align=right
										style='margin-bottom: 7.8pt; mso-para-margin-bottom: .5gd; text-align: right; layout-grid-mode: char'>
										<b style='mso-bidi-font-weight: normal'><span
											style='font-size: 10.5pt; font-family: 宋体'>：</span> </b><span
											lang=EN-US style='font-size: 10.5pt; font-family: 宋体'><o:p></o:p>
										</span>
									</p>
								</td>
								<td width=359 valign=top
									style='width: 450pt; padding: 0cm 5.4pt 0cm 5.4pt'>
									<p class=MsoNormal align=left
										style='margin-bottom: 7.8pt; mso-para-margin-bottom: .5gd; text-align: left; layout-grid-mode: char'>
										<span style='font-size: 10.5pt; font-family: 宋体'>指由第三方认证机构－中金金融认证中心有限公司或银行不时指定的其他机构采用数字签名技术，颁发给客户，用以在网络银行中证实客户其本身的一种数字凭证。<span
											lang=EN-US><o:p></o:p> </span>
										</span>
									</p>
								</td>
							</tr>
							<tr style='mso-yfti-irow: 20'>
								<td width=179 valign=top
									style='width: 134.55pt; padding: 0cm 5.4pt 0cm 5.4pt'>
									<p class=MsoNormal align=left
										style='margin-bottom: 7.8pt; mso-para-margin-bottom: .5gd; text-align: left; layout-grid-mode: char'>
										<b style='mso-bidi-font-weight: normal'><span lang=EN-US
											style='font-size: 10.5pt; font-family: 宋体'>“</span> </b><b
											style='mso-bidi-font-weight: normal'><span
											style='font-size: 10.5pt; font-family: 宋体'>证书设备<span
												lang=EN-US>”<o:p></o:p>
											</span>
										</span> </b>
									</p>
								</td>
								<td width=29 valign=top
									style='width: 21.85pt; padding: 0cm 5.4pt 0cm 5.4pt'>
									<p class=MsoNormal align=right
										style='margin-bottom: 7.8pt; mso-para-margin-bottom: .5gd; text-align: right; layout-grid-mode: char'>
										<b style='mso-bidi-font-weight: normal'><span
											style='font-size: 10.5pt; font-family: 宋体'>：</span> </b><span
											lang=EN-US style='font-size: 10.5pt; font-family: 宋体'><o:p></o:p>
										</span>
									</p>
								</td>
								<td width=359 valign=top
									style='width: 450pt; padding: 0cm 5.4pt 0cm 5.4pt'>
									<p class=MsoNormal align=left
										style='margin-bottom: 7.8pt; mso-para-margin-bottom: .5gd; text-align: left; layout-grid-mode: char'>
										<span style='font-size: 10.5pt; font-family: 宋体'>指由第三方认证机构－中国金融认证中心有限公司或银行不时指定的其他机构签发的存有客户身份标识的<span
											lang=EN-US>USB Key</span>，用于网络银行交易类服务。<span lang=EN-US><o:p></o:p>
										</span>
										</span>
									</p>
								</td>
							</tr>
							<tr style='mso-yfti-irow: 21'>
								<td width=179 valign=top
									style='width: 134.55pt; padding: 0cm 5.4pt 0cm 5.4pt'>
									<p class=MsoNormal align=left
										style='margin-bottom: 7.8pt; mso-para-margin-bottom: .5gd; text-align: left; layout-grid-mode: char'>
										<b style='mso-bidi-font-weight: normal'><span lang=EN-US
											style='font-size: 10.5pt; font-family: 宋体'>“</span> </b><b
											style='mso-bidi-font-weight: normal'><span
											style='font-size: 10.5pt; font-family: 宋体'>验证短信<span
												lang=EN-US>”<o:p></o:p>
											</span>
										</span> </b>
									</p>
								</td>
								<td width=29 valign=top
									style='width: 21.85pt; padding: 0cm 5.4pt 0cm 5.4pt'>
									<p class=MsoNormal align=right
										style='margin-bottom: 7.8pt; mso-para-margin-bottom: .5gd; text-align: right; layout-grid-mode: char'>
										<b style='mso-bidi-font-weight: normal'><span
											style='font-size: 10.5pt; font-family: 宋体; color: black'>：<span
												lang=EN-US><o:p></o:p> </span>
										</span> </b>
									</p>
								</td>
								<td width=359 valign=top
									style='width: 450pt; padding: 0cm 5.4pt 0cm 5.4pt'>
									<p class=MsoNormal align=left
										style='margin-bottom: 7.8pt; mso-para-margin-bottom: .5gd; text-align: left; layout-grid-mode: char'>
										<span style='font-size: 10.5pt; font-family: 宋体'>指银行根据客户在申请开立个人账户时提供的实名手机号码,在提供相关银行服务时为验证客户身份信息或交易信息,通过手机短信发送给客户的验证信息。<span
											lang=EN-US><o:p></o:p> </span>
										</span>
									</p>
								</td>
							</tr>
							<tr style='mso-yfti-irow: 22'>
								<td width=179 valign=top
									style='width: 134.55pt; padding: 0cm 5.4pt 0cm 5.4pt'>
									<p class=MsoNormal align=left
										style='margin-bottom: 7.8pt; mso-para-margin-bottom: .5gd; text-align: left; layout-grid-mode: char'>
										<b style='mso-bidi-font-weight: normal'><span lang=EN-US
											style='font-size: 10.5pt; font-family: 宋体'>“</span> </b><b
											style='mso-bidi-font-weight: normal'><span
											style='font-size: 10.5pt; font-family: 宋体'>数字证书申请<span
												lang=EN-US>”<o:p></o:p>
											</span>
										</span> </b>
									</p>
								</td>
								<td width=29 valign=top
									style='width: 21.85pt; padding: 0cm 5.4pt 0cm 5.4pt'>
									<p class=MsoNormal align=right
										style='margin-bottom: 7.8pt; mso-para-margin-bottom: .5gd; text-align: right; layout-grid-mode: char'>
										<b style='mso-bidi-font-weight: normal'><span
											style='font-size: 10.5pt; font-family: 宋体'>：</span> </b><span
											lang=EN-US style='font-size: 10.5pt; font-family: 宋体'><o:p></o:p>
										</span>
									</p>
								</td>
								<td width=359 valign=top
									style='width: 450pt; padding: 0cm 5.4pt 0cm 5.4pt'>
									<p class=MsoNormal align=left
										style='margin-bottom: 7.8pt; mso-para-margin-bottom: .5gd; text-align: left; layout-grid-mode: char'>
										<span style='font-size: 10.5pt; font-family: 宋体'>指客户申请、注销或变更数字证书的书面申请，包括证书的增加、注销、挂失、补发、冻结、解冻及展期。<span
											lang=EN-US><o:p></o:p> </span>
										</span>
									</p>
								</td>
							</tr>
							<tr style='mso-yfti-irow: 23'>
								<td width=179 valign=top
									style='width: 134.55pt; padding: 0cm 5.4pt 0cm 5.4pt'>
									<p class=MsoNormal align=left
										style='margin-bottom: 7.8pt; mso-para-margin-bottom: .5gd; text-align: left; layout-grid-mode: char'>
										<b style='mso-bidi-font-weight: normal'><span lang=EN-US
											style='font-size: 10.5pt; font-family: 宋体'>“</span> </b><b
											style='mso-bidi-font-weight: normal'><span
											style='font-size: 10.5pt; font-family: 宋体'>业务指令<span
												lang=EN-US>”<o:p></o:p>
											</span>
										</span> </b>
									</p>
									<p class=MsoNormal align=left
										style='margin-bottom: 7.8pt; mso-para-margin-bottom: .5gd; text-align: left; layout-grid-mode: char'>
										<b style='mso-bidi-font-weight: normal'><span lang=EN-US
											style='font-size: 10.5pt; font-family: 宋体'><o:p>&nbsp;</o:p>
										</span> </b>
									</p>
								</td>
								<td width=29 valign=top
									style='width: 21.85pt; padding: 0cm 5.4pt 0cm 5.4pt'>
									<p class=MsoNormal align=right
										style='margin-bottom: 7.8pt; mso-para-margin-bottom: .5gd; text-align: right; mso-pagination: widow-orphan; layout-grid-mode: char'>
										<b style='mso-bidi-font-weight: normal'><span
											style='font-size: 10.5pt; font-family: 宋体'>：</span> </b><span
											lang=EN-US style='font-size: 10.5pt; font-family: 宋体'><o:p></o:p>
										</span>
									</p>
									<p class=MsoNormal align=right
										style='margin-bottom: 7.8pt; mso-para-margin-bottom: .5gd; text-align: right; layout-grid-mode: char'>
										<span lang=EN-US style='font-size: 10.5pt; font-family: 宋体'><o:p>&nbsp;</o:p>
										</span>
									</p>
								</td>
								<td width=359 valign=top
									style='width: 450pt; padding: 0cm 5.4pt 0cm 5.4pt'>
									<p class=MsoNormal align=left
										style='margin-top: 0cm; margin-right: 0cm; margin-bottom: 7.8pt; margin-left: 1.35pt; mso-para-margin-top: 0cm; mso-para-margin-right: 0cm; mso-para-margin-bottom: .5gd; mso-para-margin-left: 1.35pt; text-align: left; layout-grid-mode: char'>
										<span style='font-size: 10.5pt; font-family: 宋体'>指客户以数字证书、证书设备以及密码，通过网络和电子终端向银行发出的服务要求。<span
											lang=EN-US><o:p></o:p> </span>
										</span>
									</p>
								</td>
							</tr>

							<tr style='mso-yfti-irow: 26'>
								<td width=179 valign=top
									style='width: 134.55pt; padding: 0cm 5.4pt 0cm 5.4pt'>
									<p class=MsoNormal align=left
										style='margin-bottom: 7.8pt; mso-para-margin-bottom: .5gd; text-align: left; layout-grid-mode: char'>
										<b style='mso-bidi-font-weight: normal'><span lang=EN-US
											style='font-size: 10.5pt; font-family: 宋体'>“</span> </b><b
											style='mso-bidi-font-weight: normal'><span
											style='font-size: 10.5pt; font-family: 宋体'>收费标准<span
												lang=EN-US>”<o:p></o:p>
											</span>
										</span> </b>
									</p>
									<p class=MsoNormal align=left
										style='margin-bottom: 7.8pt; mso-para-margin-bottom: .5gd; text-align: left; layout-grid-mode: char'>
										<b style='mso-bidi-font-weight: normal'><span lang=EN-US
											style='font-size: 10.5pt; font-family: 宋体'><o:p>&nbsp;</o:p>
										</span> </b>
									</p>
								</td>
								<td width=29 valign=top
									style='width: 21.85pt; padding: 0cm 5.4pt 0cm 5.4pt'>
									<p class=MsoNormal align=right
										style='margin-bottom: 7.8pt; mso-para-margin-bottom: .5gd; text-align: right; mso-pagination: widow-orphan; layout-grid-mode: char'>
										<b style='mso-bidi-font-weight: normal'><span
											style='font-size: 10.5pt; font-family: 宋体'>：</span> </b><span
											lang=EN-US style='font-size: 10.5pt; font-family: 宋体'><o:p></o:p>
										</span>
									</p>
									<p class=MsoNormal align=right
										style='margin-bottom: 7.8pt; mso-para-margin-bottom: .5gd; text-align: right; layout-grid-mode: char'>
										<span lang=EN-US style='font-size: 10.5pt; font-family: 宋体'><o:p>&nbsp;</o:p>
										</span>
									</p>
								</td>
								<td width=359 valign=top
									style='width: 450pt; padding: 0cm 5.4pt 0cm 5.4pt'>
									<p class=MsoNormal align=left
										style='margin-top: 0cm; margin-right: 0cm; margin-bottom: 7.8pt; margin-left: 1.35pt; mso-para-margin-top: 0cm; mso-para-margin-right: 0cm; mso-para-margin-bottom: .5gd; mso-para-margin-left: 1.35pt; text-align: left; layout-grid-mode: char'>
										<span style='font-size: 10.5pt; font-family: 宋体'>指银行根据同类业务标准制定并通过网络公告、营业网点公示的，关于银行服务项目具体收费的详细列表，包括但不限于《富邦华一银行服务价目表》及其不时修订的版本。<span
											lang=EN-US><o:p></o:p> </span>
										</span>
									</p>
								</td>
							</tr>
							<tr style='mso-yfti-irow: 27'>
								<td width=179 valign=top
									style='width: 134.55pt; padding: 0cm 5.4pt 0cm 5.4pt'>
									<p class=MsoNormal align=left
										style='margin-bottom: 7.8pt; mso-para-margin-bottom: .5gd; text-align: left; layout-grid-mode: char'>
										<b style='mso-bidi-font-weight: normal'><span lang=EN-US
											style='font-size: 10.5pt; font-family: 宋体'>“</span> </b><b
											style='mso-bidi-font-weight: normal'><span
											style='font-size: 10.5pt; font-family: 宋体'>中国境内<span
												lang=EN-US>”</span>或<span lang=EN-US>“</span>境内<span
												lang=EN-US>”<o:p></o:p>
											</span>
										</span> </b>
									</p>
								</td>
								<td width=29 valign=top
									style='width: 21.85pt; padding: 0cm 5.4pt 0cm 5.4pt'>
									<p class=MsoNormal align=right
										style='margin-bottom: 7.8pt; mso-para-margin-bottom: .5gd; text-align: right; layout-grid-mode: char'>
										<b style='mso-bidi-font-weight: normal'><span
											style='font-size: 10.5pt; font-family: 宋体'>：<span
												lang=EN-US><o:p></o:p> </span>
										</span> </b>
									</p>
								</td>
								<td width=359 valign=top
									style='width: 450pt; padding: 0cm 5.4pt 0cm 5.4pt'>
									<p class=MsoNormal align=left
										style='margin-top: 0cm; margin-right: 0cm; margin-bottom: 7.8pt; margin-left: 1.35pt; mso-para-margin-top: 0cm; mso-para-margin-right: 0cm; mso-para-margin-bottom: .5gd; mso-para-margin-left: 1.35pt; text-align: left; layout-grid-mode: char'>
										<span style='font-size: 10.5pt; font-family: 宋体'>指中华人民共和国，仅为本协议条款下事宜之目的不包括香港特别行政区、澳门特别行政区和台湾地区。<span
											lang=EN-US><o:p></o:p> </span>
										</span>
									</p>
								</td>
							</tr>
							<tr style='mso-yfti-irow: 28'>
								<td width=179 valign=top
									style='width: 134.55pt; padding: 0cm 5.4pt 0cm 5.4pt'>
									<p class=MsoNormal align=left
										style='margin-bottom: 7.8pt; mso-para-margin-bottom: .5gd; text-align: left; layout-grid-mode: char'>
										<b style='mso-bidi-font-weight: normal'><span lang=EN-US
											style='font-size: 10.5pt; font-family: 宋体'>“</span> </b><b
											style='mso-bidi-font-weight: normal'><span
											style='font-size: 10.5pt; font-family: 宋体'>外籍人士<span
												lang=EN-US>”<o:p></o:p>
											</span>
										</span> </b>
									</p>
								</td>
								<td width=29 valign=top
									style='width: 21.85pt; padding: 0cm 5.4pt 0cm 5.4pt'>
									<p class=MsoNormal align=right
										style='margin-bottom: 7.8pt; mso-para-margin-bottom: .5gd; text-align: right; layout-grid-mode: char'>
										<b style='mso-bidi-font-weight: normal'><span
											style='font-size: 10.5pt; font-family: 宋体'>：<span
												lang=EN-US><o:p></o:p> </span>
										</span> </b>
									</p>
								</td>
								<td width=359 valign=top
									style='width: 450pt; padding: 0cm 5.4pt 0cm 5.4pt'>
									<p class=MsoNormal align=left
										style='margin-top: 0cm; margin-right: 0cm; margin-bottom: 7.8pt; margin-left: 1.35pt; mso-para-margin-top: 0cm; mso-para-margin-right: 0cm; mso-para-margin-bottom: .5gd; mso-para-margin-left: 1.35pt; text-align: left; layout-grid-mode: char'>
										<span style='font-size: 10.5pt; font-family: 宋体'>指不具有中国国籍的外国人（但不包括无国籍人士），仅为本协议条款下事宜之目的，还包括居住在香港特别行政区、澳门特别行政区和台湾地区的居民。<span
											lang=EN-US><o:p></o:p> </span>
										</span>
									</p>
								</td>
							</tr>
							<tr style='mso-yfti-irow: 29; mso-yfti-lastrow: yes'>
								<td width=179 valign=top
									style='width: 134.55pt; padding: 0cm 5.4pt 0cm 5.4pt'>
									<p class=MsoNormal align=left
										style='margin-bottom: 7.8pt; mso-para-margin-bottom: .5gd; text-align: left; layout-grid-mode: char'>
										<b style='mso-bidi-font-weight: normal'><span lang=EN-US
											style='font-size: 10.5pt; font-family: 宋体'>“</span> </b><b
											style='mso-bidi-font-weight: normal'><span
											style='font-size: 10.5pt; font-family: 宋体'>银行工作日<span
												lang=EN-US>”<o:p></o:p>
											</span>
										</span> </b>
									</p>
								</td>
								<td width=29 valign=top
									style='width: 21.85pt; padding: 0cm 5.4pt 0cm 5.4pt'>
									<p class=MsoNormal align=right
										style='margin-bottom: 7.8pt; mso-para-margin-bottom: .5gd; text-align: right; layout-grid-mode: char'>
										<b style='mso-bidi-font-weight: normal'><span
											style='font-size: 10.5pt; font-family: 宋体'>：</span> </b><span
											lang=EN-US style='font-size: 10.5pt; font-family: 宋体'><o:p></o:p>
										</span>
									</p>
								</td>
								<td width=359 valign=top
									style='width: 450pt; padding: 0cm 5.4pt 0cm 5.4pt'>
									<p class=MsoNormal align=left
										style='margin-top: 0cm; margin-right: 0cm; margin-bottom: 7.8pt; margin-left: 1.35pt; mso-para-margin-top: 0cm; mso-para-margin-right: 0cm; mso-para-margin-bottom: .5gd; mso-para-margin-left: 1.35pt; text-align: left; layout-grid-mode: char'>
										<span style='font-size: 10.5pt; font-family: 宋体'>国家法定节假日及休息日以外的银行营业的工作日。<span
											lang=EN-US><o:p></o:p> </span>
										</span>
									</p>
								</td>
							</tr>
						</table>


					</td>
				</tr>
			</table>

			<div style='page-break-before: always;'>
				<br />
			</div>
			<table class=MsoNormalTable border=1 cellspacing=0 cellpadding=0
				width=640pt height=98%
				style='width: 640pt; border-collapse: collapse; border: none; mso-border-alt: solid windowtext .5pt; mso-yfti-tbllook: 1184; mso-padding-alt: 0cm 5.4pt 0cm 5.4pt; mso-border-insideh: .5pt solid windowtext; mso-border-insidev: .5pt solid windowtext'>

				<tr style='mso-yfti-irow: 26; height: 29.7pt'>
					<td width=640pt colspan=11 valign=top
						style='width: 640pt; border-top: solid windowtext 1.0pt; border-left: solid windowtext 1.0pt; border-bottom: solid windowtext 1.0pt; border-right: solid windowtext 1.0pt; mso-border-top-alt: solid windowtext .25pt; mso-border-alt: solid windowtext .25pt; mso-border-bottom-alt: solid windowtext 1.5pt; background: transparent; padding: 0cm 5.4pt 0cm 5.4pt; height: 29.7pt'>
						<p class=MsoListParagraph
							style='margin-top: 0cm; margin-right: 0cm; margin-bottom: 4.8pt; margin-left: 63.8pt; mso-para-margin-top: 0cm; mso-para-margin-right: 0cm; mso-para-margin-bottom: .5gd; mso-para-margin-left: 63.8pt; text-indent: -63.8pt; mso-char-indent-count: 0; mso-list: l1 level1 lfo1; layout-grid-mode: char'>
							<![if !supportLists]>
							<b style='mso-bidi-font-weight: normal'><span lang=EN-US
								style='font-size: 10.5pt; font-family: 宋体; mso-bidi-font-family: 宋体'><span
									style='mso-list: Ignore'>第二条<span
										style='font: 7.0pt "Times New Roman"'>&nbsp;&nbsp; </span>
								</span> </span> </b>
							<![endif]>
							<span style='font-size: 10.5pt; font-family: 宋体'>&nbsp;除非文意上另有需要，否则凡本协议提及：<span
								lang=EN-US><o:p></o:p> </span>
							</span>
						</p>

						<table style='width: 630px;'>
							<tr
								style='font-size: 10.5pt; font-family: 宋体; vertical-align: top;'>
								<td width="2%" style="text-indent: 40pt;">1.</td>
								<td>“条款”应解释为本协议的条款；</td>
							</tr>
							<tr
								style='font-size: 10.5pt; font-family: 宋体; vertical-align: top;'>
								<td width="2%" style="text-indent: 40pt;">2.</td>
								<td>本协议或任何其他协议或文件，应解释为该等经过不时修订、更改或补充的协议、条款或文件；</td>
							</tr>
							<tr
								style='font-size: 10.5pt; font-family: 宋体; vertical-align: top;'>
								<td width="2%" style="text-indent: 40pt;">3.</td>
								<td>法律规定，应解释为经过不时修订或重新颁布的、有效的法律、法规、规章或规范性文件；</td>
							</tr>
							<tr
								style='font-size: 10.5pt; font-family: 宋体; vertical-align: top;'>
								<td width="2%" style="text-indent: 40pt;">4.</td>
								<td>本协议中所称的“债务”应解释为包括任何及所有应付的金额，而无论其如何产生，包括但不限于本金、利息、手续费、账户服务费、滞纳金、罚金、费用（由于催收本金、利息、手续费等所发生的全部费用）、支出、成本、损失和损害。</td>
							</tr>
						</table>

						<p class=MsoListParagraph
							style='margin-top: 0cm; margin-right: 0cm; margin-bottom: 5.8pt; margin-left: 63.8pt; mso-para-margin-top: 0cm; mso-para-margin-right: 0cm; mso-para-margin-bottom: .5gd; mso-para-margin-left: 63.8pt; text-indent: -63.8pt; mso-char-indent-count: 0; mso-list: l1 level1 lfo1; layout-grid-mode: char'>
							<![if !supportLists]>
							<b style='mso-bidi-font-weight: normal'><span lang=EN-US
								style='font-size: 10.5pt; font-family: 宋体; mso-bidi-font-family: 宋体'><span
									style='mso-list: Ignore'>第三条<span
										style='font: 7.0pt "Times New Roman"'>&nbsp;&nbsp; </span>
								</span> </span> </b>
							<![endif]>
							<span style='font-size: 10.5pt; font-family: 宋体'>&nbsp;本协议的标题仅为方便参考而加入。<span
								lang=EN-US><o:p></o:p> </span>
							</span>
						</p>

						<p class=MsoListParagraph align=center
							style='margin-top: 0cm; margin-right: 0cm; margin-bottom: 8pt; margin-left: 50.2pt; mso-para-margin-top: 0cm; mso-para-margin-right: 0cm; mso-para-margin-bottom: 1.0gd; mso-para-margin-left: 50.2pt; text-align: center; text-indent: -50.2pt; mso-char-indent-count: 0; mso-outline-level: 1; mso-list: l50 level1 lfo2; layout-grid-mode: char'>
							<a name="_Toc389184869"><![if !supportLists]><b
								style='mso-bidi-font-weight: normal'><span lang=EN-US
									style='font-size: 10.5pt; font-family: 宋体; mso-bidi-font-family: 宋体'><span
										style='mso-list: Ignore'>第二章<span
											style='font: 7.0pt "Times New Roman"'>&nbsp;&nbsp; </span>
									</span> </span> </b> <![endif]><b style='mso-bidi-font-weight: normal'><span
									style='font-size: 10.5pt; font-family: 宋体'>一般约定事项</span> </b> </a><b
								style='mso-bidi-font-weight: normal'><span lang=EN-US
								style='font-size: 10.5pt; font-family: 宋体'><o:p></o:p> </span> </b>
						</p>
						<p class=MsoListParagraph
							style='margin-top: 0cm; margin-right: 0cm; margin-bottom: 7pt; margin-left: 44.25pt; mso-para-margin-top: 0cm; mso-para-margin-right: 0cm; mso-para-margin-bottom: .5gd; mso-para-margin-left: 44.25pt; text-indent: -44.25pt; mso-char-indent-count: 0; mso-list: l1 level1 lfo1; layout-grid-mode: char'>
							<![if !supportLists]>
							<b style='mso-bidi-font-weight: normal'><span lang=EN-US
								style='font-size: 10.5pt; font-family: 宋体; mso-bidi-font-family: 宋体'><span
									style='mso-list: Ignore'>第四条<span
										style='font: 7.0pt "Times New Roman"'>&nbsp;&nbsp; </span>
								</span> </span> </b>
							<![endif]>
							<span style='font-size: 10.5pt; font-family: 宋体'>&nbsp;本章所述之一般约定事项适用于在中国境内开立于富邦华一银行的所有账户及开通的所有服务。此外，银行亦有适用于特定种类的账户或服务的特定条款，特定种类的账户或服务还将适用相应的特定条款。<span
								lang=EN-US><o:p></o:p> </span>
							</span>
						</p>
						<p class=MsoListParagraph
							style='margin-top: 0cm; margin-right: 0cm; margin-bottom: 7pt; margin-left: 44.25pt; mso-para-margin-top: 0cm; mso-para-margin-right: 0cm; mso-para-margin-bottom: .5gd; mso-para-margin-left: 44.25pt; text-indent: -44.25pt; mso-char-indent-count: 0; mso-list: l1 level1 lfo1; layout-grid-mode: char'>
							<![if !supportLists]>
							<b style='mso-bidi-font-weight: normal'><span lang=EN-US
								style='font-size: 10.5pt; font-family: 宋体; mso-bidi-font-family: 宋体'><span
									style='mso-list: Ignore'>第五条<span
										style='font: 7.0pt "Times New Roman"'>&nbsp;&nbsp; </span>
								</span> </span> </b>
							<![endif]>
							<span style='font-size: 10.5pt; font-family: 宋体'>&nbsp;为使银行考虑是否为客户提供任何服务，银行将获取相关客户资料。若客户未能向银行充分提供有关客户资料，银行可能无法提供相关服务。<span
								lang=EN-US><o:p></o:p> </span>
							</span>
						</p>

						<p class=MsoListParagraph
							style='margin-top: 0cm; margin-right: 0cm; margin-bottom: 0pt; margin-left: 44.25pt; mso-para-margin-top: 0cm; mso-para-margin-right: 0cm; mso-para-margin-bottom: .5gd; mso-para-margin-left: 44.25pt; text-indent: -44.25pt; mso-char-indent-count: 0; mso-list: l1 level1 lfo1; layout-grid-mode: char'>

							<b style='mso-bidi-font-weight: normal'><span lang=EN-US
								style='font-size: 10.5pt; font-family: 宋体; mso-bidi-font-family: 宋体'><span
									style='mso-list: Ignore'>第六条<span
										style='font: 7.0pt "Times New Roman"'>&nbsp;&nbsp; </span><span
										style='font-size: 11pt; font-family: 宋体'>&nbsp;银行将遵循合法、正当、必要的原则收集、使用客户资料。在银行同意提供相关服务的情况下，客户资料以及有关客户与银行进行交易的其他一切详情和信息，将为向客户提供服务有关的目的使用。在符合法律法规的前提下,银行将为下述目的使用、存储、向银行认为必要的所有主体（包括但不限于富邦金控成员）披露从该等主体取得客户资料和该等其他详情和信息：
									</span>
								</span> </span> </b>
						</p>
						<p class=MsoListParagraph
							style='margin-top: 0cm; margin-right: 0cm; margin-bottom: 4pt; margin-left: 65.45pt; mso-para-margin-top: 0cm; mso-para-margin-right: 0cm; mso-para-margin-bottom: .5gd; mso-para-margin-left: 4.21gd; text-indent: -21.25pt; mso-char-indent-count: -2.35; mso-list: l11 level2 lfo17; layout-grid-mode: char'>
							<![if !supportLists]>
							<b style='mso-bidi-font-weight: normal'><span lang=EN-US
								style='font-size: 10.5pt; font-family: 宋体; mso-bidi-font-family: 宋体'><span
									style='mso-list: Ignore'>1.<span
										style='font: 7.0pt "Times New Roman"'>&nbsp;&nbsp;&nbsp;</span>
								</span> </span> </b>
							<![endif]>
							<b style='mso-bidi-font-weight: normal'><span
								style='font-size: 11pt; font-family: 宋体'>用银行和富邦金控成员持有的有关客户的其它资料核对客户资料；<span
									lang=EN-US><o:p></o:p> </span>
							</span> </b>
						</p>
						<p class=MsoListParagraph
							style='margin-top: 0cm; margin-right: 0cm; margin-bottom: 4pt; margin-left: 65.45pt; mso-para-margin-top: 0cm; mso-para-margin-right: 0cm; mso-para-margin-bottom: .5gd; mso-para-margin-left: 4.21gd; text-indent: -21.25pt; mso-char-indent-count: -2.35; mso-list: l11 level2 lfo17; layout-grid-mode: char'>
							<![if !supportLists]>
							<b style='mso-bidi-font-weight: normal'><span lang=EN-US
								style='font-size: 10.5pt; font-family: 宋体; mso-bidi-font-family: 宋体'><span
									style='mso-list: Ignore'>2.<span
										style='font: 7.0pt "Times New Roman"'>&nbsp;&nbsp;&nbsp;</span>
								</span> </span> </b>
							<![endif]>
							<b style='mso-bidi-font-weight: normal'><span
								style='font-size: 11pt; font-family: 宋体'>向信用查询机构或其他主体进行查询以及将客户资料提供给信用查询机构（包括但不限于，为了审核信贷申请、审核信用卡申请、审核担保安排和/或对已发放的贷款和/或已使用的信贷进行管理等目的向中国人民银行个人信用信息基础数据库进行查询，并向中国人民银行个人信用信息基础数据库提供客户资料）；<span
									lang=EN-US><o:p></o:p> </span>
							</span> </b>
						</p>
						<p class=MsoListParagraph
							style='margin-top: 0cm; margin-right: 0cm; margin-bottom: 4pt; margin-left: 65.45pt; mso-para-margin-top: 0cm; mso-para-margin-right: 0cm; mso-para-margin-bottom: .5gd; mso-para-margin-left: 4.21gd; text-indent: -21.25pt; mso-char-indent-count: -2.35; mso-list: l11 level2 lfo17; layout-grid-mode: char'>
							<![if !supportLists]>
							<b style='mso-bidi-font-weight: normal'><span lang=EN-US
								style='font-size: 10.5pt; font-family: 宋体; mso-bidi-font-family: 宋体'><span
									style='mso-list: Ignore'>3.<span
										style='font: 7.0pt "Times New Roman"'>&nbsp;&nbsp;&nbsp;</span>
								</span> </span> </b>
							<![endif]>
							<b style='mso-bidi-font-weight: normal'><span
								style='font-size: 11pt; font-family: 宋体'>追讨债务；<span
									lang=EN-US><o:p></o:p> </span>
							</span> </b>
						</p>
						<p class=MsoListParagraph
							style='margin-top: 0cm; margin-right: 0cm; margin-bottom: 4pt; margin-left: 65.45pt; mso-para-margin-top: 0cm; mso-para-margin-right: 0cm; mso-para-margin-bottom: .5gd; mso-para-margin-left: 4.21gd; text-indent: -21.25pt; mso-char-indent-count: -2.35; mso-list: l11 level2 lfo17; layout-grid-mode: char'>
							<![if !supportLists]>
							<b style='mso-bidi-font-weight: normal'><span lang=EN-US
								style='font-size: 10.5pt; font-family: 宋体; mso-bidi-font-family: 宋体'><span
									style='mso-list: Ignore'>4.<span
										style='font: 7.0pt "Times New Roman"'>&nbsp;&nbsp;&nbsp;</span>
								</span> </span> </b>
							<![endif]>
							<b style='mso-bidi-font-weight: normal'><span
								style='font-size: 11pt; font-family: 宋体'>一般地为推动、改善和促进银行和富邦金控成员向客户提供其他服务；<span
									lang=EN-US><o:p></o:p> </span>
							</span> </b>
						</p>
						<p class=MsoListParagraph
							style='margin-top: 0cm; margin-right: 0cm; margin-bottom: 4pt; margin-left: 65.45pt; mso-para-margin-top: 0cm; mso-para-margin-right: 0cm; mso-para-margin-bottom: .5gd; mso-para-margin-left: 4.21gd; text-indent: -21.25pt; mso-char-indent-count: -2.35; mso-list: l11 level2 lfo17; layout-grid-mode: char'>
							<![if !supportLists]>
							<b style='mso-bidi-font-weight: normal'><span lang=EN-US
								style='font-size: 10.5pt; font-family: 宋体; mso-bidi-font-family: 宋体'><span
									style='mso-list: Ignore'>5.<span
										style='font: 7.0pt "Times New Roman"'>&nbsp;&nbsp;&nbsp;</span>
								</span> </span> </b>
							<![endif]>
							<b style='mso-bidi-font-weight: normal'><span
								style='font-size: 11pt; font-family: 宋体'>审核和管理客户与富邦金控成员之间的关系；<span
									lang=EN-US><o:p></o:p> </span>
							</span> </b>
						</p>
						<p class=MsoListParagraph
							style='margin-top: 0cm; margin-right: 0cm; margin-bottom: 4pt; margin-left: 65.45pt; mso-para-margin-top: 0cm; mso-para-margin-right: 0cm; mso-para-margin-bottom: .5gd; mso-para-margin-left: 4.21gd; text-indent: -21.25pt; mso-char-indent-count: -2.35; mso-list: l11 level2 lfo17; layout-grid-mode: char'>
							<![if !supportLists]>
							<b style='mso-bidi-font-weight: normal'><span lang=EN-US
								style='font-size: 10.5pt; font-family: 宋体; mso-bidi-font-family: 宋体'><span
									style='mso-list: Ignore'>6.<span
										style='font: 7.0pt "Times New Roman"'>&nbsp;&nbsp;&nbsp;</span>
								</span> </span> </b>
							<![endif]>
							<b style='mso-bidi-font-weight: normal'><span
								style='font-size: 11pt; font-family: 宋体'>遵守适用于银行的任何和所有法律法规和监管要求；和<span
									lang=EN-US>/</span>或为任何其他目的；<span lang=EN-US><o:p></o:p>
								</span>
							</span> </b>
						</p>

						<p class=MsoListParagraph
							style='margin-top: 0cm; margin-right: 0cm; margin-bottom: 5pt; margin-left: 44.25pt; mso-para-margin-top: 0cm; mso-para-margin-right: 0cm; mso-para-margin-bottom: .5gd; mso-para-margin-left: 44.25pt; text-indent: -44.25pt; mso-char-indent-count: 0; mso-list: l1 level1 lfo1; layout-grid-mode: char'>

							<b style='mso-bidi-font-weight: normal'><span lang=EN-US
								style='font-size: 10.5pt; font-family: 宋体; mso-bidi-font-family: 宋体'><span
									style='mso-list: Ignore'>第七条<span
										style='font: 7.0pt "Times New Roman"'>&nbsp;&nbsp; </span><span
										style='font-size: 11pt; font-family: 宋体'>&nbsp;
											银行和富邦金控成员还可向符合银行不时向客户提供的声明、指引、通知和其他条款条件所载明的银行一般客户资料披露政策的其他主体披露从该等主体取得客户资料和该等其他详情和信息，只要该等披露不被法律法规规章所禁止。
									</span>
								</span> </span> </b>

						</p>

						<p class=MsoListParagraph
							style='margin-top: 0cm; margin-right: 0cm; margin-bottom: 5pt; margin-left: 44.25pt; mso-para-margin-top: 0cm; mso-para-margin-right: 0cm; mso-para-margin-bottom: .5gd; mso-para-margin-left: 44.25pt; text-indent: -44.25pt; mso-char-indent-count: 0; mso-list: l1 level1 lfo1; layout-grid-mode: char'>

							<b style='mso-bidi-font-weight: normal'><span lang=EN-US
								style='font-size: 10.5pt; font-family: 宋体; mso-bidi-font-family: 宋体'><span
									style='mso-list: Ignore'>第八条<span
										style='font: 7.0pt "Times New Roman"'>&nbsp;&nbsp; </span><span
										style='font-size: 11pt; font-family: 宋体'>&nbsp;在符合法律法规的前提下,银行可将任何该等客户资料、详情或资料提供给在中国境内或境外的任何服务供应商，以便该（等）供应商为银行进行资料储存、处理或代表银行向客户提供任何服务。若该（等）境外服务供应商所在地区的信息保障法律较为宽松，银行将要求该（等）服务供应商向银行作出与中国内地的信息保障法律基本相同的保密承诺。在任何情况下银行将会继续负责将此等客户资料、详情或资料保密，本协议另有约定或银行另有规定的除外。
									</span>
								</span> </span> </b>

						</p>
						<p class=MsoListParagraph
							style='margin-top: 0cm; margin-right: 0cm; margin-bottom: 5pt; margin-left: 44.25pt; mso-para-margin-top: 0cm; mso-para-margin-right: 0cm; mso-para-margin-bottom: .5gd; mso-para-margin-left: 44.25pt; text-indent: -44.25pt; mso-char-indent-count: 0; mso-list: l1 level1 lfo1; layout-grid-mode: char'>

							<b style='mso-bidi-font-weight: normal'><span lang=EN-US
								style='font-size: 10.5pt; font-family: 宋体; mso-bidi-font-family: 宋体'><span
									style='mso-list: Ignore'>第九条<span
										style='font: 7.0pt "Times New Roman"'>&nbsp;&nbsp; </span><span
										style='font-size: 11pt; font-family: 宋体'>&nbsp;银行有权在其认为必须或适当的期间内保存客户资料（无论账户是否已关闭），且上述规定应继续适用于客户资料的整个保存期间。
									</span>
								</span> </span> </b>

						</p>

						<p class=MsoListParagraph
							style='margin-top: 0cm; margin-right: 0cm; margin-bottom: 5pt; margin-left: 44.25pt; mso-para-margin-top: 0cm; mso-para-margin-right: 0cm; mso-para-margin-bottom: .5gd; mso-para-margin-left: 44.25pt; text-indent: -42.55pt; mso-char-indent-count: 0; mso-list: l1 level1 lfo1; layout-grid-mode: char'>
							<![if !supportLists]>
							<b style='mso-bidi-font-weight: normal'><span lang=EN-US
								style='font-size: 10.5pt; font-family: 宋体; mso-bidi-font-family: 宋体'><span
									style='mso-list: Ignore'>第十条<span
										style='font: 7.0pt "Times New Roman"'>&nbsp;&nbsp;&nbsp;&nbsp;
									</span>
								</span> </span> </b>
							<![endif]>
							<span style='font-size: 10.5pt; font-family: 宋体'>客户同意银行对其所负债务应为扣除欠银行的任何债务和<span
								lang=EN-US>/</span>或预留金额相当于客户对银行所负各种形式债务总额的款项后（不论前述债务为实有的、现有的、将有的、递延的、或有的、主债务性质的、担保性的、个别性的、连带性的或其他的，且如果在联名账户情况下，则该等债务包括该账户的全部或其中一名或多名客户的债务总额）（合称<span
								lang=EN-US>“</span>客户之总负债<span lang=EN-US>”</span>），银行仍欠客户的净金额。在不限制以上一般性规则的前提下，并在银行对任何账户所可能拥有的一般性抵销权或其他因持有担保而产生的优先权益之外，客户同意，若客户之总负债相等或超过银行对其所负的债务时，银行对客户的任何到期负债或其偿还要求有完全权利拒绝偿还并毋须给予客户通知。若银行据此拒绝偿付欠客户的任何负债，有关的债务将按照银行行使此项权力前有效的银行条款或依银行视当时情况而认为适当的其他条款列为未付款项。惟银行可随时毋须给予客户通知而将其在银行的任何或全部账户余额与客户之总负债的任何部分或全部抵销。银行在此条款下的权利不会因客户的死亡或法律上无行为能力而受影响。<span
								lang=EN-US><o:p></o:p> </span>
							</span>
						</p>

						<p class=MsoListParagraph
							style='margin-top: 0cm; margin-right: 0cm; margin-bottom: 5pt; margin-left: 63.8pt; mso-para-margin-top: 0cm; mso-para-margin-right: 0cm; mso-para-margin-bottom: .5gd; mso-para-margin-left: 63.8pt; text-indent: -63.8pt; mso-char-indent-count: 0; mso-list: l1 level1 lfo1; tab-stops: 65.55pt; layout-grid-mode: char'>
							<![if !supportLists]>
							<b style='mso-bidi-font-weight: normal'><span lang=EN-US
								style='font-size: 10.5pt; font-family: 宋体; mso-bidi-font-family: 宋体'><span
									style='mso-list: Ignore'>第十一条<span
										style='font: 7.0pt "Times New Roman"'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
									</span>
								</span> </span> </b>
							<![endif]>
							<span style='font-size: 10.5pt; font-family: 宋体'>客户应以银行不时规定或采纳的方式，包括但不限于用书面、传真、电报、电话、或通过自动柜员机、销售点终端机、或其他电子方式或媒介及/或其他方式或媒介发出指示或通讯。
							</span>
						</p>
						<p class=MsoListParagraph
							style='margin-top: 0cm; margin-right: 0cm; margin-bottom: 5pt; margin-left: 63.8pt; mso-para-margin-top: 0cm; mso-para-margin-right: 0cm; mso-para-margin-bottom: .5gd; mso-para-margin-left: 63.8pt; text-indent: -63.8pt; mso-char-indent-count: 0; mso-list: l1 level1 lfo1; tab-stops: 65.55pt; layout-grid-mode: char'>
							<![if !supportLists]>
							<b style='mso-bidi-font-weight: normal'><span lang=EN-US
								style='font-size: 10.5pt; font-family: 宋体; mso-bidi-font-family: 宋体'><span
									style='mso-list: Ignore'>第十二条<span
										style='font: 7.0pt "Times New Roman"'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
									</span>
								</span> </span> </b>
							<![endif]>
							<span style='font-size: 10.5pt; font-family: 宋体'>在提供银行服务的过程中，银行有权以录音方式记存客户的口头指示或客户与银行有关该服务的任何对话。银行保留将已有缩微摄影/电子扫描/电子文件方式保存的任何有关账户的文件销毁的权利。<span
								lang=EN-US><o:p></o:p> </span>
							</span>
						</p>
						<p class=MsoListParagraph
							style='margin-top: 0cm; margin-right: 0cm; margin-bottom: 5pt; margin-left: 63.8pt; mso-para-margin-top: 0cm; mso-para-margin-right: 0cm; mso-para-margin-bottom: .5gd; mso-para-margin-left: 63.8pt; text-indent: -63.8pt; mso-char-indent-count: 0; mso-list: l1 level1 lfo1; tab-stops: 65.55pt; layout-grid-mode: char'>
							<![if !supportLists]>
							<b style='mso-bidi-font-weight: normal'><span lang=EN-US
								style='font-size: 10.5pt; font-family: 宋体; mso-bidi-font-family: 宋体'><span
									style='mso-list: Ignore'>第十三条<span
										style='font: 7.0pt "Times New Roman"'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
									</span>
								</span> </span> </b>
							<![endif]>
							<span style='font-size: 10.5pt; font-family: 宋体'>客户保证其所提供予银行的资料（不论在开户申请资料或于其他文件中）尽其所知均属真实、合法、有效、完整、准确。客户亦承诺以书面形式通知银行其地址更改或其他保存在银行的有关记录的更改。凡以邮递方式按照客户最后登记于银行之地址寄出的所有信件，得视为已寄达客户。如银行认为无法根据客户最后登记地址向客户投递信件，银行可全权决定不再寄出信件（包括陆续印发的对账单，出入账通知书或其他信件）到该地址或客户。客户如有需要可书面要求银行提供对账单或其他文件的副本，作为有关账户的交易的证明，对该项服务银行将收取手续费。<span
								lang=EN-US><o:p></o:p> </span>
							</span>
						</p>

						<p class=MsoListParagraph
							style='margin-top: 0cm; margin-right: 0cm; margin-bottom: 5pt; margin-left: 63.8pt; mso-para-margin-top: 0cm; mso-para-margin-right: 0cm; mso-para-margin-bottom: .5gd; mso-para-margin-left: 63.8pt; text-indent: -63.8pt; mso-char-indent-count: 0; mso-list: l1 level1 lfo1; tab-stops: 65.55pt; layout-grid-mode: char'>
							<![if !supportLists]>
							<b style='mso-bidi-font-weight: normal'><span lang=EN-US
								style='font-size: 10.5pt; font-family: 宋体; mso-bidi-font-family: 宋体'><span
									style='mso-list: Ignore'>第十四条<span
										style='font: 7.0pt "Times New Roman"'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
									</span>
								</span> </span> </b>
							<![endif]>
							<span style='font-size: 10.5pt; font-family: 宋体'>客户保证其提供的任何客户资料发生变更时，客户应于发生变更后的5个工作日内向银行提出变更申请并出具有关部门的证明文件，变更内容的生效日期，银行记录中的客户资料变更以银行审核通过的日期为准。客户应按照银行不时的合理要求,及时变更交易密码、预留手机号码或其他客户资料。所有因客户未能及时更新其交易密码、预留手机号码或其他客户资料而产生的风险，由客户自行承担。
								<span lang=EN-US><o:p></o:p> </span>
							</span>
						</p>

						<p class=MsoListParagraph
							style='margin-top: 0cm; margin-right: 0cm; margin-bottom: 5pt; margin-left: 63.8pt; mso-para-margin-top: 0cm; mso-para-margin-right: 0cm; mso-para-margin-bottom: .5gd; mso-para-margin-left: 63.8pt; text-indent: -63.8pt; mso-char-indent-count: 0; mso-list: l1 level1 lfo1; tab-stops: 65.55pt; layout-grid-mode: char'>
							<![if !supportLists]>
							<b style='mso-bidi-font-weight: normal'><span lang=EN-US
								style='font-size: 10.5pt; font-family: 宋体; mso-bidi-font-family: 宋体'><span
									style='mso-list: Ignore'>第十五条<span
										style='font: 7.0pt "Times New Roman"'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
									</span>
								</span> </span> </b>
							<![endif]>
							<span style='font-size: 10.5pt; font-family: 宋体'>如果任何适用的法律法规（包括客户享用富邦金控成员提供的服务所在司法管辖区的法律法规）要求客户对其应付给本银行或富邦金控成员的任何款项做出任何扣减或预提，那么有关该等扣减或预提的责任应当由客户承担，以使在作出该等扣减或预提后的付款净额应相当于银行或该等富邦金控成员在没有该等扣减或预提的情况下应收取的金额。客户应单独负责在适用的时限内向有关部门支付该等扣减或预提。客户应当赔偿银行及富邦金控成员由于客户未能履行该等纳税义务而造成的所有后果。<span
								lang=EN-US><o:p></o:p> </span>
							</span>
						</p>

						<p class=MsoListParagraph
							style='margin-top: 0cm; margin-right: 0cm; margin-bottom: 5pt; margin-left: 63.8pt; mso-para-margin-top: 0cm; mso-para-margin-right: 0cm; mso-para-margin-bottom: .5gd; mso-para-margin-left: 63.8pt; text-indent: -63.8pt; mso-char-indent-count: 0; mso-list: l1 level1 lfo1; tab-stops: 65.55pt; layout-grid-mode: char'>
							<![if !supportLists]>
							<b style='mso-bidi-font-weight: normal'><span lang=EN-US
								style='font-size: 10.5pt; font-family: 宋体; mso-bidi-font-family: 宋体'><span
									style='mso-list: Ignore'>第十六条<span
										style='font: 7.0pt "Times New Roman"'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
									</span>
								</span> </span> </b>
							<![endif]>
							<span style='font-size: 10.5pt; font-family: 宋体'>银行将全面遵守有关税务的当地法律法规、国际条约及对富邦金控及银行产生约束力或受其影响的其他法律法规及法案（合称“税务法案”），包括但不限于美国国外账户税收遵从法案（“FATCA”）。为遵守相关税务法案，银行有权从客户处获得客户签署的关于客户个人身份信息的确认表示或其他法律文件。对于依照相关税务法案作为纳税申报人的客户，银行可使用其客户资料并在必要时向相关有权机关申报或披露。客户应保证其提供的客户资料真实、合法、有效、完整、准确。<span
								lang=EN-US><o:p></o:p> </span>
							</span>
						</p>



					</td>
				</tr>
			</table>


			<div style='page-break-before: always;'>
				<br />
			</div>
			<table class=MsoNormalTable border=1 cellspacing=0 cellpadding=0
				width=640 height=98%
				style='width: 640pt; border-collapse: collapse; border: none; mso-border-alt: solid windowtext .5pt; mso-yfti-tbllook: 1184; mso-padding-alt: 0cm 5.4pt 0cm 5.4pt; mso-border-insideh: .5pt solid windowtext; mso-border-insidev: .5pt solid windowtext'>

				<tr style='mso-yfti-irow: 26; height: 29.7pt'>
					<td width=640 colspan=11 valign=top
						style='width: 640pt; border-top: solid windowtext 1.0pt; border-left: solid windowtext 1.0pt; border-bottom: solid windowtext 1.0pt; border-right: solid windowtext 1.0pt; mso-border-top-alt: solid windowtext .25pt; mso-border-alt: solid windowtext .25pt; mso-border-bottom-alt: solid windowtext 1.5pt; background: transparent; padding: 0cm 5.4pt 0cm 5.4pt; height: 29.7pt'>
						<p class=MsoListParagraph
							style='margin-top: 0cm; margin-right: 0cm; margin-bottom: 5pt; margin-left: 63.8pt; mso-para-margin-top: 0cm; mso-para-margin-right: 0cm; mso-para-margin-bottom: .5gd; mso-para-margin-left: 63.8pt; text-indent: -63.8pt; mso-char-indent-count: 0; mso-list: l1 level1 lfo1; tab-stops: 65.55pt; layout-grid-mode: char'>
							<![if !supportLists]>
							<b style='mso-bidi-font-weight: normal'><span lang=EN-US
								style='font-size: 10.5pt; font-family: 宋体; mso-bidi-font-family: 宋体'><span
									style='mso-list: Ignore'>第十七条<span
										style='font: 7.0pt "Times New Roman"'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
									</span>
								</span> </span> </b>
							<![endif]>
							<b> <span style='font-size: 11pt; font-family: 宋体'>银行有权决定修订或补充本协议条款及与银行所提供服务有关的其他条款。有关修订或补充将以公告方式在银行营业场所张贴或通过网站，或以银行决定的其他方式事先通知客户，倘客户并未于通知期结束前取消其账户或该等服务，将被视为同意该等修订或补充。客户同意双方无须再另行签署任何书面文件。<span
									lang=EN-US><o:p></o:p> </span>
							</span></b>
						</p>
						<p class=MsoListParagraph align=center
							style='margin-top: 0cm; margin-right: 0cm; margin-bottom: 5pt; margin-left: 50.2pt; mso-para-margin-top: 0cm; mso-para-margin-right: 0cm; mso-para-margin-bottom: 1.0gd; mso-para-margin-left: 50.2pt; text-align: center; text-indent: -50.2pt; mso-char-indent-count: 0; mso-outline-level: 1; mso-list: l50 level1 lfo2'>
							<a name="_Toc389184870"><![if !supportLists]><b
								style='mso-bidi-font-weight: normal'><span lang=EN-US
									style='font-size: 10.5pt; font-family: 宋体; mso-bidi-font-family: 宋体'><span
										style='mso-list: Ignore'>第三章<span
											style='font: 7.0pt "Times New Roman"'>&nbsp;&nbsp; </span>
									</span> </span> </b> <![endif]><b style='mso-bidi-font-weight: normal'><span
									style='font-size: 10.5pt; font-family: 宋体'>人民币结算账户及储蓄账户管理事项</span>
							</b> </a><b style='mso-bidi-font-weight: normal'><span lang=EN-US
								style='font-size: 10.5pt; font-family: 宋体'><o:p></o:p> </span> </b>
						</p>

						<p class=MsoListParagraph
							style='margin-top: 0cm; margin-right: 0cm; margin-bottom: 5pt; margin-left: 63.8pt; mso-para-margin-top: 0cm; mso-para-margin-right: 0cm; mso-para-margin-bottom: .5gd; mso-para-margin-left: 63.8pt; text-indent: -63.8pt; mso-char-indent-count: 0; mso-list: l1 level1 lfo1; tab-stops: 65.55pt; layout-grid-mode: char'>
							<![if !supportLists]>
							<b style='mso-bidi-font-weight: normal'><span lang=EN-US
								style='font-size: 10.5pt; font-family: 宋体; mso-bidi-font-family: 宋体'><span
									style='mso-list: Ignore'>第十八条<span
										style='font: 7.0pt "Times New Roman"'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
									</span>
								</span> </span> </b>
							<![endif]>
							<span style='font-size: 10.5pt; font-family: 宋体'>客户自愿选择在银行处开立个人人民币结算账户或个人人民币储蓄账户（以下合称“人民币账户”）。客户人民币账户开立及管理事项适用本章节条款之约定；客户开立的一般综合账户中涵盖的人民币结算账户（若有）相关事项亦适用本章节条款之约定。<span
								lang=EN-US><o:p></o:p> </span>
							</span>
						</p>

						<p class=MsoListParagraph
							style='margin-top: 0cm; margin-right: 0cm; margin-bottom: 5pt; margin-left: 63.8pt; mso-para-margin-top: 0cm; mso-para-margin-right: 0cm; mso-para-margin-bottom: .5gd; mso-para-margin-left: 63.8pt; text-indent: -63.8pt; mso-char-indent-count: 0; mso-list: l1 level1 lfo1; tab-stops: 65.55pt; layout-grid-mode: char'>
							<![if !supportLists]>
							<b style='mso-bidi-font-weight: normal'><span lang=EN-US
								style='font-size: 10.5pt; font-family: 宋体; mso-bidi-font-family: 宋体'><span
									style='mso-list: Ignore'>第十九条<span
										style='font: 7.0pt "Times New Roman"'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
									</span>
								</span> </span> </b>
							<![endif]>
							<span style='font-size: 10.5pt; font-family: 宋体'>人民币储蓄账户仅限于办理现金存取业务，不得办理转账结算业务。<span
								lang=EN-US><o:p></o:p> </span>
							</span>
						</p>
						<p class=MsoListParagraph
							style='margin-top: 0cm; margin-right: 0cm; margin-bottom: 5pt; margin-left: 63.8pt; mso-para-margin-top: 0cm; mso-para-margin-right: 0cm; mso-para-margin-bottom: .5gd; mso-para-margin-left: 63.8pt; text-indent: -63.8pt; mso-char-indent-count: 0; mso-list: l1 level1 lfo1; tab-stops: 65.55pt; layout-grid-mode: char'>
							<![if !supportLists]>
							<b style='mso-bidi-font-weight: normal'><span lang=EN-US
								style='font-size: 10.5pt; font-family: 宋体; mso-bidi-font-family: 宋体'><span
									style='mso-list: Ignore'>第二十条<span
										style='font: 7.0pt "Times New Roman"'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
									</span>
								</span> </span> </b>
							<![endif]>
							<span style='font-size: 10.5pt; font-family: 宋体'>客户在银行开立人民币账户应填写开户申请书并预留印鉴或密码（若有），银行受理后客户应确认开户申请书填写内容正确。<b
								style='mso-bidi-font-weight: normal; font-size: 11pt;'>凡使用密码进行的支付结算交易均视为客户本人或本人授权的合法交易。客户应当妥善保管账户介质、密码、印鉴、本人有效身份证件及有关业务凭证，因客户保管不善造成的损失由客户自行承担。</b><span
								lang=EN-US><o:p></o:p> </span>
							</span>
						</p>
						<p class=MsoListParagraph
							style='margin-top: 0cm; margin-right: 0cm; margin-bottom: 5pt; margin-left: 62.8pt; mso-para-margin-top: 0cm; mso-para-margin-right: 0cm; mso-para-margin-bottom: .5gd; mso-para-margin-left: 63.8pt; text-indent: -63.8pt; mso-char-indent-count: 0; mso-list: l1 level1 lfo1; layout-grid-mode: char'>
							<![if !supportLists]>
							<b style='mso-bidi-font-weight: normal'><span lang=EN-US
								style='font-size: 10.5pt; font-family: 宋体; mso-bidi-font-family: 宋体'><span
									style='mso-list: Ignore'>第二十一条<span
										style='font: 7.0pt "Times New Roman"'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
									</span>
								</span> </span> </b>
							<![endif]>
							<span style='font-size: 10.5pt; font-family: 宋体'>客户在银行开立、使用和撤销人民币账户应遵守《人民币银行结算账户管理办法》、《储蓄管理条例》及相关法律、法规的规定。客户使用在银行开立的人民币账户办理业务时，应遵守人民银行和银行的相关制度规定。客户在银行开立人民币账户时，需向银行提交相应的证明文件，并接受银行的审核，客户承诺对所提交的证明文件的真实性、完整性、合规性负责。<span
								lang=EN-US><o:p></o:p> </span>
							</span>
						</p>

						<p class=MsoListParagraph
							style='margin-top: 0cm; margin-right: 0cm; margin-bottom: 5pt; margin-left: 62.8pt; mso-para-margin-top: 0cm; mso-para-margin-right: 0cm; mso-para-margin-bottom: .5gd; mso-para-margin-left: 63.8pt; text-indent: -63.8pt; mso-char-indent-count: 0; mso-list: l1 level1 lfo1; layout-grid-mode: char'>
							<![if !supportLists]>
							<b style='mso-bidi-font-weight: normal'><span lang=EN-US
								style='font-size: 10.5pt; font-family: 宋体; mso-bidi-font-family: 宋体'><span
									style='mso-list: Ignore'>第二十二条<span
										style='font: 7.0pt "Times New Roman"'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
									</span>
								</span> </span> </b>
							<![endif]>
							<span style='font-size: 10.5pt; font-family: 宋体'>客户应配合银行开展身份识别工作（包括但不限于预留印签，肖像采集等），保证所提供的资料真实、完整、有效，资料信息如有更改，应及时办理变更手续。<b
								style='mso-bidi-font-weight: normal; font-size: 11pt;'>客户同意并授权银行除为进行身份识别之目的使用其采集的客户肖像外，银行还可为向客户提供有关服务之目的使用客户肖像。<span
									lang=EN-US><o:p></o:p> </span>
							</b>
							</span>
						</p>

						<p class=MsoListParagraph
							style='margin-top: 0cm; margin-right: 0cm; margin-bottom: 5pt; margin-left: 62.8pt; mso-para-margin-top: 0cm; mso-para-margin-right: 0cm; mso-para-margin-bottom: .5gd; mso-para-margin-left: 63.8pt; text-indent: -63.8pt; mso-char-indent-count: 0; mso-list: l1 level1 lfo1; layout-grid-mode: char'>
							<![if !supportLists]>
							<b style='mso-bidi-font-weight: normal'><span lang=EN-US
								style='font-size: 10.5pt; font-family: 宋体; mso-bidi-font-family: 宋体'><span
									style='mso-list: Ignore'>第二十三条<span
										style='font: 7.0pt "Times New Roman"'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
									</span>
								</span> </span> </b>
							<![endif]>
							<span style='font-size: 10.5pt; font-family: 宋体'>银行对先前获得的客户身份资料的真实性、有效性或者完整性有疑问的，可以重新识别客户身份，客户应给与配合。若由于客户的原因导致无法完成或更新身份识别信息采集的，由此产生的任何不利后果由客户承担。<span
								lang=EN-US><o:p></o:p> </span>
							</span>
						</p>

						<p class=MsoListParagraph
							style='margin-top: 0cm; margin-right: 0cm; margin-bottom: 7.8pt; margin-left: 62.8pt; mso-para-margin-top: 0cm; mso-para-margin-right: 0cm; mso-para-margin-bottom: .5gd; mso-para-margin-left: 63.8pt; text-indent: -63.8pt; mso-char-indent-count: 0; mso-list: l1 level1 lfo1; layout-grid-mode: char'>
							<![if !supportLists]>
							<b style='mso-bidi-font-weight: normal'><span lang=EN-US
								style='font-size: 10.5pt; font-family: 宋体; mso-bidi-font-family: 宋体'><span
									style='mso-list: Ignore'>第二十四条<span
										style='font: 7.0pt "Times New Roman"'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
									</span>
								</span> </span> </b>
							<![endif]>
							<span style='font-size: 10.5pt; font-family: 宋体'>如客户先前提交的身份证件或身份证明文件已过有效期，客户没有在合理期限内更新且没有提出合理理由，银行有权中止为客户办理业务。<span
								lang=EN-US><o:p></o:p> </span>
							</span>
						</p>
						<p class=MsoListParagraph
							style='margin-top: 0cm; margin-right: 0cm; margin-bottom: 7.8pt; margin-left: 62.8pt; mso-para-margin-top: 0cm; mso-para-margin-right: 0cm; mso-para-margin-bottom: .5gd; mso-para-margin-left: 63.8pt; text-indent: -63.8pt; mso-char-indent-count: 0; mso-list: l1 level1 lfo1; layout-grid-mode: char'>
							<![if !supportLists]>
							<b style='mso-bidi-font-weight: normal'><span lang=EN-US
								style='font-size: 10.5pt; font-family: 宋体; mso-bidi-font-family: 宋体'><span
									style='mso-list: Ignore'>第二十五条<span
										style='font: 7.0pt "Times New Roman"'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
									</span>
								</span> </span> </b>
							<![endif]>
							<span style='font-size: 10.5pt; font-family: 宋体'>客户不得利用在银行开立的人民币账户进行偷逃税款、逃废债务、套取现金、洗钱及其它违法犯罪活动。&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span
								lang=EN-US><o:p></o:p> </span>
							</span>
						</p>


						<p class=MsoListParagraph
							style='margin-top: 0cm; margin-right: 0cm; margin-bottom: 7.8pt; margin-left: 62.8pt; mso-para-margin-top: 0cm; mso-para-margin-right: 0cm; mso-para-margin-bottom: .5gd; mso-para-margin-left: 63.8pt; text-indent: -63.8pt; mso-char-indent-count: 0; mso-list: l1 level1 lfo1; layout-grid-mode: char'>
							<![if !supportLists]>
							<b style='mso-bidi-font-weight: normal'><span lang=EN-US
								style='font-size: 10.5pt; font-family: 宋体; mso-bidi-font-family: 宋体'><span
									style='mso-list: Ignore'>第二十六条<span
										style='font: 7.0pt "Times New Roman"'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
									</span>
								</span> </span> </b>
							<![endif]>
							<span style='font-size: 10.5pt; font-family: 宋体'>客户不得出租、出借在银行开立的人民币账户，不得利用在银行开立的人民币账户套取银行信用。<span
								lang=EN-US><o:p></o:p> </span>
							</span>
						</p>
						<p class=MsoListParagraph
							style='margin-top: 0cm; margin-right: 0cm; margin-bottom: 7.8pt; margin-left: 62.8pt; mso-para-margin-top: 0cm; mso-para-margin-right: 0cm; mso-para-margin-bottom: .5gd; mso-para-margin-left: 63.8pt; text-indent: -63.8pt; mso-char-indent-count: 0; mso-list: l1 level1 lfo1; layout-grid-mode: char'>
							<![if !supportLists]>
							<b style='mso-bidi-font-weight: normal'><span lang=EN-US
								style='font-size: 10.5pt; font-family: 宋体; mso-bidi-font-family: 宋体'><span
									style='mso-list: Ignore'>第二十七条<span
										style='font: 7.0pt "Times New Roman"'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
									</span>
								</span> </span> </b>
							<![endif]>
							<span style='font-size: 10.5pt; font-family: 宋体'>客户撤销在银行开立的人民币账户，必须与银行核对该账户存款余额；客户撤销人民币结算账户的，还应交回各种重要空白票据及结算凭证，银行核对无误后方可办理销户手续，客户因故未交回各种重要空白票据及结算凭证的，须出具书面证明，由此造成的损失由客户承担。<span
								style='color: #333333; background: white'>客户尚未清偿银行债务的，不得申请撤销人民币账户。</span><span
								lang=EN-US><o:p></o:p> </span>
							</span>
						</p>
						<p class=MsoListParagraph
							style='margin-top: 0cm; margin-right: 0cm; margin-bottom: 7.8pt; margin-left: 62.8pt; mso-para-margin-top: 0cm; mso-para-margin-right: 0cm; mso-para-margin-bottom: .5gd; mso-para-margin-left: 63.8pt; text-indent: -63.8pt; mso-char-indent-count: 0; mso-list: l1 level1 lfo1; layout-grid-mode: char'>
							<![if !supportLists]>
							<b style='mso-bidi-font-weight: normal'><span lang=EN-US
								style='font-size: 10.5pt; font-family: 宋体; mso-bidi-font-family: 宋体'><span
									style='mso-list: Ignore'>第二十八条<span
										style='font: 7.0pt "Times New Roman"'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
									</span>
								</span> </span> </b>
							<![endif]>
							<span style='font-size: 10.5pt; font-family: 宋体'>除本协议约定的银行可披露或使用客户资料的情形外，银行应依法为客户在银行开立的人民币账户的存款和有关资料保密，对个人人民币账户，除国家法律另有规定外，银行有权拒绝任何单位或个人查询。<span
								lang=EN-US><o:p></o:p> </span>
							</span>
						</p>
						<p class=MsoListParagraph
							style='margin-top: 0cm; margin-right: 0cm; margin-bottom: 7.8pt; margin-left: 62.8pt; mso-para-margin-top: 0cm; mso-para-margin-right: 0cm; mso-para-margin-bottom: .5gd; mso-para-margin-left: 63.8pt; text-indent: -63.8pt; mso-char-indent-count: 0; mso-list: l1 level1 lfo1; layout-grid-mode: char'>
							<![if !supportLists]>
							<b style='mso-bidi-font-weight: normal'><span lang=EN-US
								style='font-size: 10.5pt; font-family: 宋体; mso-bidi-font-family: 宋体'><span
									style='mso-list: Ignore'>第二十九条<span
										style='font: 7.0pt "Times New Roman"'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
									</span>
								</span> </span> </b>
							<![endif]>
							<span style='font-size: 10.5pt; font-family: 宋体'>客户在银行开立、使用和撤销人民币账户及其相关行为均同意接受中国人民银行的监管。<span
								lang=EN-US><o:p></o:p> </span>
							</span>
						</p>

						<p class=MsoListParagraph
							style='margin-top: 0cm; margin-right: 0cm; margin-bottom: 7.8pt; margin-left: 63.8pt; mso-para-margin-top: 0cm; mso-para-margin-right: 0cm; mso-para-margin-bottom: .5gd; mso-para-margin-left: 63.8pt; text-indent: -63.8pt; mso-char-indent-count: 0; mso-list: l1 level1 lfo1; tab-stops: 65.55pt; layout-grid-mode: char'>
							<![if !supportLists]>
							<b style='mso-bidi-font-weight: normal'><span lang=EN-US
								style='font-size: 10.5pt; font-family: 宋体; mso-bidi-font-family: 宋体'><span
									style='mso-list: Ignore'>第三十条<span
										style='font: 7.0pt "Times New Roman"'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
									</span>
								</span> </span> </b>
							<![endif]>
							<span style='font-size: 10.5pt; font-family: 宋体'>&nbsp;&nbsp;开立人民币结算账户的客户因结算需要，向银行提出使用支票申请的，应遵照《中华人民共和国票据法》和《支付结算办法》办理。<span
								lang=EN-US><o:p></o:p> </span>
							</span>
						</p>
						<p class=MsoListParagraph
							style='margin-top: 0cm; margin-right: 0cm; margin-bottom: 7.8pt; margin-left: 62.8pt; mso-para-margin-top: 0cm; mso-para-margin-right: 0cm; mso-para-margin-bottom: .5gd; mso-para-margin-left: 63.8pt; text-indent: -63.8pt; mso-char-indent-count: 0; mso-list: l1 level1 lfo1; layout-grid-mode: char'>
							<![if !supportLists]>
							<b style='mso-bidi-font-weight: normal'><span lang=EN-US
								style='font-size: 10.5pt; font-family: 宋体; mso-bidi-font-family: 宋体'><span
									style='mso-list: Ignore'>第三十一条<span
										style='font: 7.0pt "Times New Roman"'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
									</span>
								</span> </span> </b>
							<![endif]>
							<b style='mso-bidi-font-weight: normal'><span
								style='font-size: 11.5pt; font-family: 宋体'>客户须定期与银行核对账务。客户发生账务后，应及时通过银行提供的柜台、电话银行、网上银行、自助设备等设施核对账务，如有异议应及时向银行提出。</span>
							</b><span lang=EN-US style='font-size: 11.5pt; font-family: 宋体'><o:p></o:p>
							</span>
						</p>
						<p class=MsoListParagraph
							style='margin-top: 0cm; margin-right: 0cm; margin-bottom: 7.8pt; margin-left: 62.8pt; mso-para-margin-top: 0cm; mso-para-margin-right: 0cm; mso-para-margin-bottom: .5gd; mso-para-margin-left: 63.8pt; text-indent: -63.8pt; mso-char-indent-count: 0; mso-list: l1 level1 lfo1; layout-grid-mode: char'>
							<![if !supportLists]>
							<b style='mso-bidi-font-weight: normal'><span lang=EN-US
								style='font-size: 10.5pt; font-family: 宋体; mso-bidi-font-family: 宋体'><span
									style='mso-list: Ignore'>第三十二条<span
										style='font: 7.0pt "Times New Roman"'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
									</span>
								</span> </span> </b>
							<![endif]>
							<span style='font-size: 10.5pt; font-family: 宋体'>客户遗失或更换预留个人印章或更换授权签字人的，应按《人民币银行结算账户管理办法》、《储蓄管理条例》及银行的相关制度规定，向银行提供经客户本人签名确认的书面申请，以及原预留印鉴或签字人的个人身份证件。银行应留存相应的复印件，并凭以办理预留银行签章的变更。<span
								lang=EN-US><o:p></o:p> </span>
							</span>
						</p>
						<p class=MsoListParagraph
							style='margin-top: 0cm; margin-right: 0cm; margin-bottom: 7.8pt; margin-left: 62.8pt; mso-para-margin-top: 0cm; mso-para-margin-right: 0cm; mso-para-margin-bottom: .5gd; mso-para-margin-left: 63.8pt; text-indent: -63.8pt; mso-char-indent-count: 0; mso-list: l1 level1 lfo1; layout-grid-mode: char'>
							<![if !supportLists]>
							<b style='mso-bidi-font-weight: normal'><span lang=EN-US
								style='font-size: 10.5pt; font-family: 宋体; mso-bidi-font-family: 宋体'><span
									style='mso-list: Ignore'>第三十三条<span
										style='font: 7.0pt "Times New Roman"'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
									</span>
								</span> </span> </b>
							<![endif]>
							<span style='font-size: 10.5pt; font-family: 宋体'>客户有下列违规或违约行为之一的，按照《人民币银行结算账户管理办法》、《储蓄管理条例》及相关法律法规的有关规定执行：<span
								lang=EN-US><o:p></o:p> </span>
							</span>
						</p>

						<p class=MsoListParagraph
							style='margin-top: 0cm; margin-right: 0cm; margin-bottom: 7.8pt; margin-left: 3.0cm; mso-para-margin-top: 0cm; mso-para-margin-right: 0cm; mso-para-margin-bottom: .5gd; mso-para-margin-left: 3.0cm; text-indent: -21pt; mso-char-indent-count: 0; mso-list: l33 level1 lfo16; layout-grid-mode: char'>
							<![if !supportLists]>
							<span lang=EN-US
								style='font-size: 10.5pt; font-family: 宋体; mso-bidi-font-family: 宋体'><span
								style='mso-list: Ignore'>1.<span
									style='font: 7.0pt "Times New Roman"'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
								</span>
							</span> </span>
							<![endif]>
							<span style='font-size: 10.5pt; font-family: 宋体'>客户在开户时，未向银行提供真实、完整、合法的开户资料，造成银行无法确认或被有关部门处罚时，客户应承担一切责任，造成损失的，客户全部赔偿；<span
								lang=EN-US><o:p></o:p> </span>
							</span>
						</p>
						<p class=MsoListParagraph
							style='margin-top: 0cm; margin-right: 0cm; margin-bottom: 7.8pt; margin-left: 3.0cm; mso-para-margin-top: 0cm; mso-para-margin-right: 0cm; mso-para-margin-bottom: .5gd; mso-para-margin-left: 3.0cm; text-indent: -21pt; mso-char-indent-count: 0; mso-list: l33 level1 lfo16; layout-grid-mode: char'>
							<![if !supportLists]>
							<span lang=EN-US
								style='font-size: 10.5pt; font-family: 宋体; mso-bidi-font-family: 宋体'><span
								style='mso-list: Ignore'>2.<span
									style='font: 7.0pt "Times New Roman"'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
								</span>
							</span> </span>
							<![endif]>
							<span style='font-size: 10.5pt; font-family: 宋体'>客户在人民币账户的使用中不执行有关规定，或出租、出借人民币账户的，银行有权向人民银行报告，由人民银行予以处罚，构成犯罪的，由司法机关依法追究刑事责任；<span
								lang=EN-US><o:p></o:p> </span>
							</span>
						</p>
						<p class=MsoListParagraph
							style='margin-top: 0cm; margin-right: 0cm; margin-bottom: 7.8pt; margin-left: 3.0cm; mso-para-margin-top: 0cm; mso-para-margin-right: 0cm; mso-para-margin-bottom: .5gd; mso-para-margin-left: 3.0cm; text-indent: -21pt; mso-char-indent-count: 0; mso-list: l33 level1 lfo16; layout-grid-mode: char'>
							<![if !supportLists]>
							<span lang=EN-US
								style='font-size: 10.5pt; font-family: 宋体; mso-bidi-font-family: 宋体'><span
								style='mso-list: Ignore'>3.<span
									style='font: 7.0pt "Times New Roman"'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
								</span>
							</span> </span>
							<![endif]>
							<span style='font-size: 10.5pt; font-family: 宋体'>客户开户资料的变更未在规定时间通知银行，造成银行无法准确、及时地为其处理有关业务时，银行不承担任何责任；<span
								lang=EN-US><o:p></o:p> </span>
							</span>
						</p>
						<p class=MsoListParagraph
							style='margin-top: 0cm; margin-right: 0cm; margin-bottom: 7.8pt; margin-left: 3.0cm; mso-para-margin-top: 0cm; mso-para-margin-right: 0cm; mso-para-margin-bottom: .5gd; mso-para-margin-left: 3.0cm; text-indent: -21.25pt; mso-char-indent-count: 0; mso-list: l33 level1 lfo16; layout-grid-mode: char'>
							<![if !supportLists]>
							<span lang=EN-US
								style='font-size: 10.5pt; font-family: 宋体; mso-bidi-font-family: 宋体'><span
								style='mso-list: Ignore'>4.<span
									style='font: 7.0pt "Times New Roman"'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
								</span>
							</span> </span>
							<![endif]>
							<span style='font-size: 10.5pt; font-family: 宋体'>客户不按规定使用支付结算工具及不按规定支付服务费用，银行有权停止其结算账户的支付；<span
								lang=EN-US><o:p></o:p> </span>
							</span>
						</p>
						<p class=MsoListParagraph
							style='margin-top: 0cm; margin-right: 0cm; margin-bottom: 7.8pt; margin-left: 62.8pt; mso-para-margin-top: 0cm; mso-para-margin-right: 0cm; mso-para-margin-bottom: .5gd; mso-para-margin-left: 63.8pt; text-indent: -63.8pt; mso-char-indent-count: 0; mso-list: l1 level1 lfo1; layout-grid-mode: char'>
							<![if !supportLists]>
							<b style='mso-bidi-font-weight: normal'><span lang=EN-US
								style='font-size: 10.5pt; font-family: 宋体; mso-bidi-font-family: 宋体'><span
									style='mso-list: Ignore'>第三十四条<span
										style='font: 7.0pt "Times New Roman"'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
									</span>
								</span> </span> </b>
							<![endif]>
							<span style='font-size: 10.5pt; font-family: 宋体'>本章节未尽事宜，按《储蓄管理条例》、《支付结算办法》、《人民币银行结算账户管理办法》、《中国人民银行关于内地银行与香港银行办理个人人民币业务有关问题的通知》、《关于转发中国人民银行内地银行与香港和澳门银行办理个人人民币业务有关问题的通知》、《中国人民银行关于内地银行与香港银行办理人民币业务有关问题的补充通知》等有关法律、法规执行。<span
								lang=EN-US><o:p></o:p> </span>
							</span>
						</p>

						<p class=MsoListParagraph align=center
							style='margin-top: 0cm; margin-right: 0cm; margin-bottom: 12.6pt; margin-left: 50.2pt; mso-para-margin-top: 0cm; mso-para-margin-right: 0cm; mso-para-margin-bottom: 1.0gd; mso-para-margin-left: 50.2pt; text-align: center; text-indent: -50.2pt; mso-char-indent-count: 0; mso-outline-level: 1; mso-list: l50 level1 lfo2'>
							<![if !supportLists]>
							<b style='mso-bidi-font-weight: normal'><span lang=EN-US
								style='font-size: 10.5pt; font-family: 宋体; mso-bidi-font-family: 宋体'><span
									style='mso-list: Ignore'>第四章<span
										style='font: 7.0pt "Times New Roman"'>&nbsp;&nbsp; </span>
								</span> </span> </b>
							<![endif]>
							<b style='mso-bidi-font-weight: normal'><span
								style='font-size: 10.5pt; font-family: 宋体'>外汇账户管理事项<span
									lang=EN-US><o:p></o:p> </span>
							</span> </b>
						</p>

						<p class=MsoListParagraph
							style='margin-top: 0cm; margin-right: 0cm; margin-bottom: 7.8pt; margin-left: 62.8pt; mso-para-margin-top: 0cm; mso-para-margin-right: 0cm; mso-para-margin-bottom: .5gd; mso-para-margin-left: 63.8pt; text-indent: -63.8pt; mso-char-indent-count: 0; mso-list: l1 level1 lfo1; layout-grid-mode: char'>
							<![if !supportLists]>
							<b style='mso-bidi-font-weight: normal'><span lang=EN-US
								style='font-size: 10.5pt; font-family: 宋体; mso-bidi-font-family: 宋体'><span
									style='mso-list: Ignore'>第三十五条<span
										style='font: 7.0pt "Times New Roman"'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
									</span>
								</span> </span> </b>
							<![endif]>
							<span style='font-size: 10.5pt; font-family: 宋体'>客户自愿选择在银行处开立个人外汇账户。外汇账户的开立及管理相关事项适用本章节条款之约定；客户开立的一般综合账户中涵盖的外汇账户相关事项亦适用本章节条款之约定。<span
								lang=EN-US><o:p></o:p> </span>
							</span>
						</p>
						<p class=MsoListParagraph
							style='margin-top: 0cm; margin-right: 0cm; margin-bottom: 7.8pt; margin-left: 62.8pt; mso-para-margin-top: 0cm; mso-para-margin-right: 0cm; mso-para-margin-bottom: .5gd; mso-para-margin-left: 63.8pt; text-indent: -63.8pt; mso-char-indent-count: 0; mso-list: l1 level1 lfo1; layout-grid-mode: char'>
							<![if !supportLists]>
							<b style='mso-bidi-font-weight: normal'><span lang=EN-US
								style='font-size: 10.5pt; font-family: 宋体; mso-bidi-font-family: 宋体'><span
									style='mso-list: Ignore'>第三十六条<span
										style='font: 7.0pt "Times New Roman"'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
									</span>
								</span> </span> </b>
							<![endif]>
							<span style='font-size: 10.5pt; font-family: 宋体'>客户可以凭本人有效身份证件在银行开立外汇账户<span
								lang=EN-US>, </span>所开立账户户名应与本人有效身份证件记载的姓名一致。外汇账户的收支范围为非经营性外汇收付、客户本人或与其直系亲属之间同一主体类别的外汇账户间的资金划转。<span
								lang=EN-US><o:p></o:p> </span>
							</span>
						</p>




					</td>
				</tr>
			</table>
			<div style='page-break-before: always;'>
				<br />
			</div>

			<table class=MsoNormalTable border=1 cellspacing=0 cellpadding=0
				height=98% width=640
				style='width: 640pt; border-collapse: collapse; border: none; mso-border-alt: solid windowtext .5pt; mso-yfti-tbllook: 1184; mso-padding-alt: 0cm 5.4pt 0cm 5.4pt; mso-border-insideh: .5pt solid windowtext; mso-border-insidev: .5pt solid windowtext'>

				<tr style='mso-yfti-irow: 26; height: 29.7pt'>
					<td width=640 colspan=11 valign=top
						style='width: 640pt; border-top: solid windowtext 1.0pt; border-left: solid windowtext 1.0pt; border-bottom: solid windowtext 1.0pt; border-right: solid windowtext 1.0pt; mso-border-top-alt: solid windowtext .25pt; mso-border-alt: solid windowtext .25pt; mso-border-bottom-alt: solid windowtext 1.5pt; background: transparent; padding: 0cm 5.4pt 0cm 5.4pt; height: 29.7pt'>
						<p class=MsoListParagraph
							style='margin-top: 0cm; margin-right: 0cm; margin-bottom: 7.8pt; margin-left: 62.8pt; mso-para-margin-top: 0cm; mso-para-margin-right: 0cm; mso-para-margin-bottom: .5gd; mso-para-margin-left: 63.8pt; text-indent: -63.8pt; mso-char-indent-count: 0; mso-list: l1 level1 lfo1; layout-grid-mode: char'>
							<![if !supportLists]>
							<b style='mso-bidi-font-weight: normal'><span lang=EN-US
								style='font-size: 10.5pt; font-family: 宋体; mso-bidi-font-family: 宋体'><span
									style='mso-list: Ignore'>第三十七条<span
										style='font: 7.0pt "Times New Roman"'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
									</span>
								</span> </span> </b>
							<![endif]>
							<span style='font-size: 10.5pt; font-family: 宋体'>外汇账户的开立、使用、撤销、客户身份识别、客户资料的提供及变更等事项按《中华人民共和国外汇管理条例》、《个人外汇管理办法》和《个人外汇管理办法实施细则》等相关外汇法律法规及监管政策执行，并参照第三章人民币结算账户及储蓄账户管理事项相关条款的约定。<span
								lang=EN-US><o:p></o:p> </span>
							</span>
						</p>
						<p class=MsoListParagraph
							style='margin-top: 0cm; margin-right: 0cm; margin-bottom: 7.8pt; margin-left: 62.8pt; mso-para-margin-top: 0cm; mso-para-margin-right: 0cm; mso-para-margin-bottom: .5gd; mso-para-margin-left: 63.8pt; text-indent: -63.8pt; mso-char-indent-count: 0; mso-list: l1 level1 lfo1; layout-grid-mode: char'>
							<![if !supportLists]>
							<b style='mso-bidi-font-weight: normal'><span lang=EN-US
								style='font-size: 10.5pt; font-family: 宋体; mso-bidi-font-family: 宋体'><span
									style='mso-list: Ignore'>第三十八条<span
										style='font: 7.0pt "Times New Roman"'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
									</span>
								</span> </span> </b>
							<![endif]>
							<span style='font-size: 10.5pt; font-family: 宋体'>外汇账户分为现汇账户和现钞账户。凡从境外汇入、携入和境内居民持有可自由兑换的外汇，均可存入现汇账户或现钞账户，存入现钞账户需按汇转钞有关规定收取手续费。不能立即付款的外币票据，需经银行办理托收，受托后方可存入。凡从境外携入或个人持有的可自由兑换的外币现钞，均可存入外币现钞账户或现汇账户。<span
								lang=EN-US><o:p></o:p> </span>
							</span>
						</p>
						<p class=MsoListParagraph
							style='margin-top: 0cm; margin-right: 0cm; margin-bottom: 7.8pt; margin-left: 62.8pt; mso-para-margin-top: 0cm; mso-para-margin-right: 0cm; mso-para-margin-bottom: .5gd; mso-para-margin-left: 63.8pt; text-indent: -63.8pt; mso-char-indent-count: 0; mso-list: l1 level1 lfo1; layout-grid-mode: char'>
							<![if !supportLists]>
							<b style='mso-bidi-font-weight: normal'><span lang=EN-US
								style='font-size: 10.5pt; font-family: 宋体; mso-bidi-font-family: 宋体'><span
									style='mso-list: Ignore'>第三十九条<span
										style='font: 7.0pt "Times New Roman"'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
									</span>
								</span> </span> </b>
							<![endif]>
							<span style='font-size: 10.5pt; font-family: 宋体'>存款的货币有美元、英镑、港币、澳币、日元、欧元、加拿大元、新西兰元及瑞士法郎等。<span
								lang=EN-US><o:p></o:p> </span>
							</span>
						</p>
						<p class=MsoListParagraph
							style='margin-top: 0cm; margin-right: 0cm; margin-bottom: 7.8pt; margin-left: 63.8pt; mso-para-margin-top: 0cm; mso-para-margin-right: 0cm; mso-para-margin-bottom: .5gd; mso-para-margin-left: 63.8pt; text-indent: -63.8pt; mso-char-indent-count: 0; mso-list: l1 level1 lfo1; tab-stops: 65.55pt; layout-grid-mode: char'>
							<![if !supportLists]>
							<b style='mso-bidi-font-weight: normal'><span lang=EN-US
								style='font-size: 10.5pt; font-family: 宋体; mso-bidi-font-family: 宋体'><span
									style='mso-list: Ignore'>第四十条<span
										style='font: 7.0pt "Times New Roman"'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
									</span>
								</span> </span> </b>
							<![endif]>
							<span style='font-size: 10.5pt; font-family: 宋体'>客户应根据《中华人民共和国外汇管理条例》、《个人外汇管理办法》和《个人外汇管理办法实施细则》等相关外汇法律法规及监管政策办理外汇存款业务。<b
								style='mso-bidi-font-weight: normal'><span lang=EN-US><o:p></o:p>
								</span> </b>
							</span>
						</p>
						<p class=MsoListParagraph
							style='margin-top: 0cm; margin-right: 0cm; margin-bottom: 6.8pt; margin-left: 62.8pt; mso-para-margin-top: 0cm; mso-para-margin-right: 0cm; mso-para-margin-bottom: .5gd; mso-para-margin-left: 63.8pt; text-indent: -63.8pt; mso-char-indent-count: 0; mso-list: l1 level1 lfo1; layout-grid-mode: char'>
							<![if !supportLists]>
							<b style='mso-bidi-font-weight: normal'><span lang=EN-US
								style='font-size: 10.5pt; font-family: 宋体; mso-bidi-font-family: 宋体'><span
									style='mso-list: Ignore'>第四十一条<span
										style='font: 7.0pt "Times New Roman"'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
									</span>
								</span> </span> </b>
							<![endif]>
							<span style='font-size: 10.5pt; font-family: 宋体'>本章节未尽事宜，按《中华人民共和国外汇管理条例》、《个人外汇管理办法》和《个人外汇管理办法实施细则》等相关外汇法律法规及监管政策执行，并可参照第三章人民币结算账户及储蓄账户管理事项相关条款的约定。<b
								style='mso-bidi-font-weight: normal'><span lang=EN-US><o:p></o:p>
								</span> </b>
							</span>
						</p>

						<p class=MsoListParagraph align=center
							style='margin-top: 0cm; margin-right: 0cm; margin-bottom: 13.6pt; margin-left: 50.2pt; mso-para-margin-top: 0cm; mso-para-margin-right: 0cm; mso-para-margin-bottom: 1.0gd; mso-para-margin-left: 50.2pt; text-align: center; text-indent: -50.2pt; mso-char-indent-count: 0; mso-outline-level: 1; mso-list: l50 level1 lfo2'>
							<a name="_Toc389184871"><![if !supportLists]><b
								style='mso-bidi-font-weight: normal'><span lang=EN-US
									style='font-size: 10.5pt; font-family: 宋体; mso-bidi-font-family: 宋体'><span
										style='mso-list: Ignore'>第五章<span
											style='font: 7.0pt "Times New Roman"'>&nbsp;&nbsp; </span>
									</span> </span> </b> <![endif]><b style='mso-bidi-font-weight: normal'><span
									style='font-size: 10.5pt; font-family: 宋体'>借记卡服务事项<span
										lang=EN-US><o:p></o:p> </span>
								</span> </b> </a>
						</p>
						<p class=MsoListParagraph
							style='margin-top: 0cm; margin-right: 0cm; margin-bottom: 7.8pt; margin-left: 62.8pt; mso-para-margin-top: 0cm; mso-para-margin-right: 0cm; mso-para-margin-bottom: .5gd; mso-para-margin-left: 63.8pt; text-indent: -63.8pt; mso-char-indent-count: 0; mso-list: l1 level1 lfo1; layout-grid-mode: char'>
							<span style='mso-bookmark: _Toc389184871'><![if !supportLists]><b
								style='mso-bidi-font-weight: normal'><span lang=EN-US
									style='font-size: 10.5pt; font-family: 宋体; mso-bidi-font-family: 宋体'><span
										style='mso-list: Ignore'>第四十二条<span
											style='font: 7.0pt "Times New Roman"'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
										</span>
									</span> </span> </b> <![endif]><span style='font-size: 10.5pt; font-family: 宋体'>客户申请开立借记卡的，可于银行开立之人民币及外汇账户办理借记卡业务，银行向客户提供借记卡服务。相关服务适用本章节条款之约定。<span
									lang=EN-US><o:p></o:p> </span>
							</span> </span>
						</p>
						<p class=MsoListParagraph
							style='margin-top: 0cm; margin-right: 0cm; margin-bottom: 7.8pt; margin-left: 62.8pt; mso-para-margin-top: 0cm; mso-para-margin-right: 0cm; mso-para-margin-bottom: .5gd; mso-para-margin-left: 63.8pt; text-indent: -63.8pt; mso-char-indent-count: 0; mso-list: l1 level1 lfo1; layout-grid-mode: char'>
							<span style='mso-bookmark: _Toc389184871'><![if !supportLists]><b
								style='mso-bidi-font-weight: normal'><span lang=EN-US
									style='font-size: 10.5pt; font-family: 宋体; mso-bidi-font-family: 宋体'><span
										style='mso-list: Ignore'>第四十三条<span
											style='font: 7.0pt "Times New Roman"'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
										</span>
									</span> </span> </b> <![endif]><span style='font-size: 10.5pt; font-family: 宋体'>凡在银行拥有人民币结算账户，并自愿接受和遵守《富邦华一银行个人借记卡章程》的外籍人士均可凭本人真实、合法、有效的身份证件，向银行实名制申请借记卡。申请借记卡须如实填写《富邦华一银行个人借记卡申请及服务申请表》或开户手册，确认接受和遵守《富邦华一银行个人借记卡章程》。<span
									lang=EN-US><o:p></o:p> </span>
							</span> </span>
						</p>
						<p class=MsoListParagraph
							style='margin-top: 0cm; margin-right: 0cm; margin-bottom: 7.8pt; margin-left: 62.8pt; mso-para-margin-top: 0cm; mso-para-margin-right: 0cm; mso-para-margin-bottom: .5gd; mso-para-margin-left: 63.8pt; text-indent: -63.8pt; mso-char-indent-count: 0; mso-list: l1 level1 lfo1; layout-grid-mode: char'>
							<span style='mso-bookmark: _Toc389184871'><![if !supportLists]><b
								style='mso-bidi-font-weight: normal'><span lang=EN-US
									style='font-size: 10.5pt; font-family: 宋体; mso-bidi-font-family: 宋体'><span
										style='mso-list: Ignore'>第四十四条<span
											style='font: 7.0pt "Times New Roman"'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
										</span>
									</span> </span> </b> <![endif]><b style='mso-bidi-font-weight: normal'><span
									style='font-size: 10.5pt; font-family: 宋体'>借记卡申领<span
										lang=EN-US><o:p></o:p> </span>
								</span> </b> </span>
						</p>

						<p class=MsoNormal
							style='margin-top: 0cm; margin-right: 0cm; margin-bottom: 5.8pt; margin-left: 86.8pt; mso-para-margin-top: 0cm; mso-para-margin-right: 0cm; mso-para-margin-bottom: .5gd; mso-para-margin-left: 86.8pt; text-indent: -21.0pt; mso-list: l37 level1 lfo21; layout-grid-mode: char'>
							<span style='mso-bookmark: _Toc389184871'><![if !supportLists]><span
								lang=EN-US
								style='font-size: 10.5pt; font-family: 宋体; mso-bidi-font-family: 宋体'><span
									style='mso-list: Ignore'>1.<span
										style='font: 7.0pt "Times New Roman"'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
									</span>
								</span> </span> <![endif]><span style='font-size: 10.5pt; font-family: 宋体'>凡已在银行开立人民币结算账户的客户申领借记卡的，可携带本人有效身份证件到银行营业机构申领借记卡。<span
									lang=EN-US><o:p></o:p> </span>
							</span> </span>
						</p>
						<p class=MsoNormal
							style='margin-top: 0cm; margin-right: 0cm; margin-bottom: 5.8pt; margin-left: 86.8pt; mso-para-margin-top: 0cm; mso-para-margin-right: 0cm; mso-para-margin-bottom: .5gd; mso-para-margin-left: 86.8pt; text-indent: -21.0pt; mso-list: l37 level1 lfo21; layout-grid-mode: char'>
							<span style='mso-bookmark: _Toc389184871'><![if !supportLists]><span
								lang=EN-US
								style='font-size: 10.5pt; font-family: 宋体; mso-bidi-font-family: 宋体'><span
									style='mso-list: Ignore'>2.<span
										style='font: 7.0pt "Times New Roman"'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
									</span>
								</span> </span> <![endif]><span style='font-size: 10.5pt; font-family: 宋体'>凡未在银行开立人民币结算账户的申领人需申领借记卡的，应先在银行开立人民币结算账户，可同时申请借记卡并提供相关申请资料。<span
									lang=EN-US><o:p></o:p> </span>
							</span> </span>
						</p>

						<p class=MsoListParagraph
							style='margin-top: 0cm; margin-right: 0cm; margin-bottom: 5.8pt; margin-left: 86.8pt; mso-para-margin-top: 0cm; mso-para-margin-right: 0cm; mso-para-margin-bottom: 1.0gd; mso-para-margin-left: 86.8pt; text-indent: -21.0pt; mso-char-indent-count: 0; mso-outline-level: 1; mso-list: l37 level1 lfo21; layout-grid-mode: char'>
							<span style='mso-bookmark: _Toc389184871'><![if !supportLists]><span
								lang=EN-US
								style='font-size: 10.5pt; font-family: 宋体; mso-bidi-font-family: 宋体'><span
									style='mso-list: Ignore'>3.<span
										style='font: 7.0pt "Times New Roman"'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
									</span>
								</span> </span> <![endif]><span style='font-size: 10.5pt; font-family: 宋体'>申领人申办借记卡时，应按规定，出具本人有效身份证件并填写银行相关借记卡申请表。申领人提供的各项资料以及在申请表中填报的各项资料必须真实、完整、有效。经银行审查后，对符合条件的申领人准予发卡。<b
									style='mso-bidi-font-weight: normal'><span lang=EN-US><o:p></o:p>
									</span> </b>
							</span> </span>
						</p>
						<p class=MsoNormal
							style='margin-top: 0cm; margin-right: 0cm; margin-bottom: 5pt; margin-left: 86.8pt; mso-para-margin-top: 0cm; mso-para-margin-right: 0cm; mso-para-margin-bottom: .5gd; mso-para-margin-left: 86.8pt; text-indent: -21.0pt; mso-list: l37 level1 lfo21; layout-grid-mode: char'>
							<span style='mso-bookmark: _Toc389184871'><![if !supportLists]><span
								lang=EN-US
								style='font-size: 10.5pt; font-family: 宋体; mso-bidi-font-family: 宋体'><span
									style='mso-list: Ignore'>4.<span
										style='font: 7.0pt "Times New Roman"'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
									</span>
								</span> </span> <![endif]><b style='mso-bidi-font-weight: normal'><span
									style='font-size: 11pt; font-family: 宋体'>银行在审查申领人提供的身份证件等各项资料时，可要求申领人提供其他辅助身份证明文件，进一步核实客户身份。</span>
							</b> </span><span style='mso-bookmark: _Toc389184871'><span
								style='font-size: 10.5pt; font-family: 宋体'>申领人在境外产生的申请表或各项资料，若有必要，银行有权要求申领人提供与该等文件相关的公证及认证文件，费用由客户自行承担。<span
									lang=EN-US><o:p></o:p> </span>
							</span> </span>
						</p>

						<p class=MsoListParagraph
							style='margin-top: 0cm; margin-right: 0cm; margin-bottom: 5pt; margin-left: 86.8pt; mso-para-margin-top: 0cm; mso-para-margin-right: 0cm; mso-para-margin-bottom: 1.0gd; mso-para-margin-left: 86.8pt; text-indent: -21.0pt; mso-char-indent-count: 0; mso-outline-level: 1; mso-list: l37 level1 lfo21; layout-grid-mode: char'>
							<span style='mso-bookmark: _Toc389184871'><![if !supportLists]><b
								style='mso-bidi-font-weight: normal'><span lang=EN-US
									style='font-size: 10.5pt; font-family: 宋体; mso-bidi-font-family: 宋体'><span
										style='mso-list: Ignore'>5.<span
											style='font: 7.0pt "Times New Roman"'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
										</span>
									</span> </span> </b> <![endif]><span style='font-size: 10.5pt; font-family: 宋体'>未成年人申领借记卡的，应由其监护人代为申领，或在其监护人陪同下办理。监护人应提供未成年人的有效身份证件、监护关系证明文件、监护人的有效身份证件及相关申请资料。<b
									style='mso-bidi-font-weight: normal'><span lang=EN-US><o:p></o:p>
									</span> </b>
							</span> </span>
						</p>
						<p class=MsoListParagraph
							style='margin-top: 0cm; margin-right: 0cm; margin-bottom: 6.8pt; margin-left: 62.8pt; mso-para-margin-top: 0cm; mso-para-margin-right: 0cm; mso-para-margin-bottom: .5gd; mso-para-margin-left: 63.8pt; text-indent: -63.8pt; mso-char-indent-count: 0; mso-list: l1 level1 lfo1; layout-grid-mode: char'>
							<span style='mso-bookmark: _Toc389184871'><![if !supportLists]><b
								style='mso-bidi-font-weight: normal'><span lang=EN-US
									style='font-size: 10.5pt; font-family: 宋体; mso-bidi-font-family: 宋体'><span
										style='mso-list: Ignore'>第四十五条<span
											style='font: 7.0pt "Times New Roman"'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
										</span>
									</span> </span> </b> <![endif]><b style='mso-bidi-font-weight: normal'><span
									style='font-size: 10.5pt; font-family: 宋体'>借记卡使用<span
										lang=EN-US><o:p></o:p> </span>
								</span> </b> </span>
						</p>
						<p class=MsoNormal
							style='margin-top: 0cm; margin-right: 0cm; margin-bottom: 6.0pt; margin-left: 86.8pt; mso-para-margin-top: 0cm; mso-para-margin-right: 0cm; mso-para-margin-bottom: .5gd; mso-para-margin-left: 86.8pt; text-indent: -21.0pt; mso-list: l58 level1 lfo22; layout-grid-mode: char'>
							<span style='mso-bookmark: _Toc389184871'><![if !supportLists]><span
								lang=EN-US
								style='font-size: 10.5pt; font-family: 宋体; mso-bidi-font-family: 宋体'><span
									style='mso-list: Ignore'>1.<span
										style='font: 7.0pt "Times New Roman"'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
									</span>
								</span> </span> <![endif]><span style='font-size: 10.5pt; font-family: 宋体'>借记卡等级分为钻石卡、白金卡和金卡。申领人在符合各等级借记卡的申请条件及同意相应收费标准的前提下可申请变更所申请借记卡的等级，但银行最终向申领人核发的借记卡应以银行根据相关内部审核标准确定后的等级为准。<span
									lang=EN-US><o:p></o:p> </span>
							</span> </span>
						</p>
						<p class=MsoNormal
							style='margin-top: 0cm; margin-right: 0cm; margin-bottom: 6.0pt; margin-left: 86.8pt; mso-para-margin-top: 0cm; mso-para-margin-right: 0cm; mso-para-margin-bottom: .5gd; mso-para-margin-left: 86.8pt; text-indent: -21.0pt; mso-list: l58 level1 lfo22; layout-grid-mode: char'>
							<span style='mso-bookmark: _Toc389184871'><![if !supportLists]><span
								lang=EN-US
								style='font-size: 10.5pt; font-family: 宋体; mso-bidi-font-family: 宋体'><span
									style='mso-list: Ignore'>2.<span
										style='font: 7.0pt "Times New Roman"'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
									</span>
								</span> </span> <![endif]><span style='font-size: 10.5pt; font-family: 宋体'>借记卡为本外币多币种借记卡。根据持卡人申请及持卡人已开立的账户情况，借记卡账户内具有人民币活期、定期账户、理财账户、电子现金账户、及多个币种的外币活期、定期账户。<span
									lang=EN-US><o:p></o:p> </span>
							</span> </span>
						</p>
						<p class=MsoNormal
							style='margin-top: 0cm; margin-right: 0cm; margin-bottom: 5pt; margin-left: 86.8pt; mso-para-margin-top: 0cm; mso-para-margin-right: 0cm; mso-para-margin-bottom: .5gd; mso-para-margin-left: 86.8pt; text-indent: -21.0pt; mso-list: l58 level1 lfo22; layout-grid-mode: char'>
							<span style='mso-bookmark: _Toc389184871'><![if !supportLists]><span
								lang=EN-US
								style='font-size: 10.5pt; font-family: 宋体; mso-bidi-font-family: 宋体'><span
									style='mso-list: Ignore'>3.<span
										style='font: 7.0pt "Times New Roman"'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
									</span>
								</span> </span> <![endif]><b style='mso-bidi-font-weight: normal'><span
									style='font-size: 11pt; font-family: 宋体'>凡使用密码（包括交易密码和查询密码，下同）进行的交易，均视为持卡人本人所为。</span>
							</b> </span><span style='mso-bookmark: _Toc389184871'><span
								style='font-size: 10.5pt; font-family: 宋体'>凡依据密码等电子信息办理的各类交易所产生的电子信息记录均为该项交易的有效凭据；凡未用密码进行的交易，则载有持卡人签名的交易凭证为该项交易的有效凭据，银行有权将持卡人使用借记卡的收支款项、费用记入其账户。<span
									lang=EN-US><o:p></o:p> </span>
							</span> </span>
						</p>
						<p class=MsoNormal
							style='margin-top: 0cm; margin-right: 0cm; margin-bottom: 5pt; margin-left: 86.8pt; mso-para-margin-top: 0cm; mso-para-margin-right: 0cm; mso-para-margin-bottom: .5gd; mso-para-margin-left: 86.8pt; text-indent: -21.0pt; mso-list: l58 level1 lfo22; layout-grid-mode: char'>
							<span style='mso-bookmark: _Toc389184871'><![if !supportLists]><span
								lang=EN-US
								style='font-size: 10.5pt; font-family: 宋体; mso-bidi-font-family: 宋体'><span
									style='mso-list: Ignore'>4.<span
										style='font: 7.0pt "Times New Roman"'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
									</span>
								</span> </span> <![endif]><span style='font-size: 10.5pt; font-family: 宋体'>持卡人使用借记卡进行提现和<span
									lang=EN-US>/</span>或消费以在借记卡下关联的相关账户中存在足额的可用存款余额为前提，借记卡不具有透支功能，如果相关账户中的可用存款余额不足，则相关的提现和<span
									lang=EN-US>/</span>或消费将无法进行。<span lang=EN-US><o:p></o:p>
								</span>
							</span> </span>
						</p>
						<p class=MsoNormal
							style='margin-top: 0cm; margin-right: 0cm; margin-bottom: 5pt; margin-left: 86.8pt; mso-para-margin-top: 0cm; mso-para-margin-right: 0cm; mso-para-margin-bottom: .5gd; mso-para-margin-left: 86.8pt; text-indent: -21.0pt; mso-list: l58 level1 lfo22; layout-grid-mode: char'>
							<span style='mso-bookmark: _Toc389184871'><![if !supportLists]><span
								lang=EN-US
								style='font-size: 10.5pt; font-family: 宋体; mso-bidi-font-family: 宋体'><span
									style='mso-list: Ignore'>5.<span
										style='font: 7.0pt "Times New Roman"'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
									</span>
								</span> </span> <![endif]><span style='font-size: 10.5pt; font-family: 宋体'>借记卡外币账户的存、取款、转账等业务须按国家外汇制度办理；借记卡的电子现金账户为人民币账户，有金额上限，金额上限遵循监管机构及银行的相关规定，该限额将视监管及银行的相关规定调整而调整；持卡人持借记卡可在商户终端进行电子现金卡片脱机余额查询、脱机消费；在银行柜面及自动柜员机进行电子现金账户余额查询、电子现金绑定账户充值、现金充值转入交易等。<span
									lang=EN-US><o:p></o:p> </span>
							</span> </span>
						</p>
						<p class=MsoNormal
							style='margin-top: 0cm; margin-right: 0cm; margin-bottom: 5pt; margin-left: 86.8pt; mso-para-margin-top: 0cm; mso-para-margin-right: 0cm; mso-para-margin-bottom: .5gd; mso-para-margin-left: 86.8pt; text-indent: -21.0pt; mso-list: l58 level1 lfo22; layout-grid-mode: char'>
							<span style='mso-bookmark: _Toc389184871'><![if !supportLists]><span
								lang=EN-US
								style='font-size: 10.5pt; font-family: 宋体; mso-bidi-font-family: 宋体'><span
									style='mso-list: Ignore'>6.<span
										style='font: 7.0pt "Times New Roman"'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
									</span>
								</span> </span> <![endif]><span style='font-size: 10.5pt; font-family: 宋体'>在销卡或换卡时，电子现金账户余额可以通过柜面圈提交易转回关联的人民币结算账户。<span
									lang=EN-US><o:p></o:p> </span>
							</span> </span>
						</p>
						<p class=MsoNormal
							style='margin-top: 0cm; margin-right: 0cm; margin-bottom: 5pt; margin-left: 86.8pt; mso-para-margin-top: 0cm; mso-para-margin-right: 0cm; mso-para-margin-bottom: .5gd; mso-para-margin-left: 86.8pt; text-indent: -21.0pt; mso-list: l58 level1 lfo22; layout-grid-mode: char'>
							<span style='mso-bookmark: _Toc389184871'><![if !supportLists]><span
								lang=EN-US
								style='font-size: 10.5pt; font-family: 宋体; mso-bidi-font-family: 宋体'><span
									style='mso-list: Ignore'>7.<span
										style='font: 7.0pt "Times New Roman"'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
									</span>
								</span> </span> <![endif]><span style='font-size: 10.5pt; font-family: 宋体'>借记卡电子现金账户交易不设交易密码，凡使用电子现金账户进行的交易均视为持卡人本人所为。<b
									style='mso-bidi-font-weight: normal; font-size: 11pt;'>电子现金账户中的资金视同现金，持卡人借记卡保管不善造成的损失，由持卡人承担，银行不承担责任。</b><span
									lang=EN-US><o:p></o:p> </span>
							</span> </span>
						</p>

						<p class=MsoNormal
							style='margin-top: 0cm; margin-right: 0cm; margin-bottom: 5pt; margin-left: 86.8pt; mso-para-margin-top: 0cm; mso-para-margin-right: 0cm; mso-para-margin-bottom: .5gd; mso-para-margin-left: 86.8pt; text-indent: -21.0pt; mso-list: l58 level1 lfo22; layout-grid-mode: char'>
							<span style='mso-bookmark: _Toc389184871'><![if !supportLists]><span
								lang=EN-US
								style='font-size: 10.5pt; font-family: 宋体; mso-bidi-font-family: 宋体'><span
									style='mso-list: Ignore'>8.<span
										style='font: 7.0pt "Times New Roman"'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
									</span>
								</span> </span> <![endif]><span style='font-size: 10.5pt; font-family: 宋体'>持卡人在贴有银联标识的自动柜员机提取现金或通过贴有银联标识的<span
									lang=EN-US>POS</span>机上进行消费，持卡人使用其借记卡人民币结算账户，境外取现或消费发生的外币金额将按银联设定的规则折算成人民币金额，银行根据银联提供的人民币金额对持卡人在借记卡关联的人民币结算账户进行借记。<span
									lang=EN-US><o:p></o:p> </span>
							</span> </span>
						</p>
						<p class=MsoNormal
							style='margin-top: 0cm; margin-right: 0cm; margin-bottom: 5pt; margin-left: 86.8pt; mso-para-margin-top: 0cm; mso-para-margin-right: 0cm; mso-para-margin-bottom: .5gd; mso-para-margin-left: 86.8pt; text-indent: -21.0pt; mso-list: l58 level1 lfo22; layout-grid-mode: char'>
							<span style='mso-bookmark: _Toc389184871'><![if !supportLists]><span
								lang=EN-US
								style='font-size: 10.5pt; font-family: 宋体; mso-bidi-font-family: 宋体'><span
									style='mso-list: Ignore'>9.<span
										style='font: 7.0pt "Times New Roman"'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
									</span>
								</span> </span> <![endif]><span style='font-size: 10.5pt; font-family: 宋体'>借记卡按不同渠道分别设置每日累计扣款金额的上限，以增强借记卡风险控制，可能导致当日累计消费金额超过所使用的每日累计消费限额的相关消费交易将无法进行。持卡人可根据自身的用卡需求以及对借记卡风险控制的需求在监管机构及银行规定的限额（若有）内向银行申请设置或调整每日累计消费限额。若持卡人未申请设定每日累计消费限额，则将自动适用银行设置的默认每日累计消费限额，银行可不时调整默认每日累计消费限额。每一种渠道的默认限额以银行不时以其认为适当的方式（包括但不限于公告、电力银行渠道、开户或开卡时提供的书面通知）向持卡人作出的告知为准。<span
									lang=EN-US><o:p></o:p> </span>
							</span> </span>
						</p>
						<p class=MsoNormal
							style='margin-top: 0cm; margin-right: 0cm; margin-bottom: 5pt; margin-left: 86.8pt; mso-para-margin-top: 0cm; mso-para-margin-right: 0cm; mso-para-margin-bottom: .5gd; mso-para-margin-left: 86.8pt; text-indent: -21.0pt; mso-list: l58 level1 lfo22; layout-grid-mode: char'>
							<span style='mso-bookmark: _Toc389184871'><![if !supportLists]><span
								lang=EN-US
								style='font-size: 10.5pt; font-family: 宋体; mso-bidi-font-family: 宋体'><span
									style='mso-list: Ignore'>10.<span
										style='font: 7.0pt "Times New Roman"'>&nbsp;&nbsp; </span>
								</span> </span> <![endif]><span style='font-size: 10.5pt; font-family: 宋体'>在中国境内，借记卡的存取款业务，按现行中国人民银行以及国家外汇管理局的相关规定办理。持卡人每卡每日在自动柜员机上取款的累计金额不得超过监管机构的相关规定及银行的相关业务规定，该限额将视监管规定调整而调整，银行不再另行通知持卡人。在中国境外，具体可提取的金额还须受限于各国外汇管理办法和收单行的规定。<span
									lang=EN-US><o:p></o:p> </span>
							</span> </span>
						</p>


					</td>
				</tr>
			</table>
			<div style='page-break-before: always;'>
				<br />
			</div>
			<table class=MsoNormalTable border=1 cellspacing=0 cellpadding=0
				height=98% width=640
				style='width: 640pt; border-collapse: collapse; border: none; mso-border-alt: solid windowtext .5pt; mso-yfti-tbllook: 1184; mso-padding-alt: 0cm 5.4pt 0cm 5.4pt; mso-border-insideh: .5pt solid windowtext; mso-border-insidev: .5pt solid windowtext'>

				<tr style='mso-yfti-irow: 26; height: 29.7pt'>
					<td width=640 colspan=11 valign=top
						style='width: 640pt; border-top: solid windowtext 1.0pt; border-left: solid windowtext 1.0pt; border-bottom: solid windowtext 1.0pt; border-right: solid windowtext 1.0pt; mso-border-top-alt: solid windowtext .25pt; mso-border-alt: solid windowtext .25pt; mso-border-bottom-alt: solid windowtext 1.5pt; background: transparent; padding: 0cm 5.4pt 0cm 5.4pt; height: 29.7pt'>
						<p class=MsoNormal
							style='margin-top: 0cm; margin-right: 0cm; margin-bottom: 5pt; margin-left: 86.8pt; mso-para-margin-top: 0cm; mso-para-margin-right: 0cm; mso-para-margin-bottom: .5gd; mso-para-margin-left: 86.8pt; text-indent: -21.0pt; mso-list: l58 level1 lfo22; layout-grid-mode: char'>
							<span style='mso-bookmark: _Toc389184871'><![if !supportLists]><span
								lang=EN-US
								style='font-size: 10.5pt; font-family: 宋体; mso-bidi-font-family: 宋体'><span
									style='mso-list: Ignore'>11.<span
										style='font: 7.0pt "Times New Roman"'>&nbsp;&nbsp; </span>
								</span> </span> <![endif]><span style='font-size: 10.5pt; font-family: 宋体'>持卡人凭借记卡在银行自动柜员机办理业务时，因机器故障或操作问题造成借记卡被吞没（除没收卡）的，可当场输入正确密码后取卡，或在吞卡后次日起三个工作日内凭本人有效身份证件到该自动柜员机的吞没卡指定领取网点领取被吞卡。逾期未领的，吞没卡指定领取网点有权按规定程序处理。若持卡人在他行自动柜员机上办理业务时所造成的上述情况，须参照他行相关规定。<span
									lang=EN-US><o:p></o:p> </span>
							</span> </span>
						</p>
						<p class=MsoNormal
							style='margin-top: 0cm; margin-right: 0cm; margin-bottom: 5pt; margin-left: 86.8pt; mso-para-margin-top: 0cm; mso-para-margin-right: 0cm; mso-para-margin-bottom: .5gd; mso-para-margin-left: 86.8pt; text-indent: -21.0pt; mso-list: l58 level1 lfo22; layout-grid-mode: char'>
							<span style='mso-bookmark: _Toc389184871'><![if !supportLists]><span
								lang=EN-US
								style='font-size: 10.5pt; font-family: 宋体; mso-bidi-font-family: 宋体'><span
									style='mso-list: Ignore'>12.<span
										style='font: 7.0pt "Times New Roman"'>&nbsp;&nbsp; </span>
								</span> </span> <![endif]><span style='font-size: 10.5pt; font-family: 宋体'>银行通过银行网站、营业网点、自动柜员机、电子银行、客服中心等渠道为持卡人提供借记卡咨询、查询和投诉服务。每一种渠道所提供的具体查询服务以银行不时以其认为适当的方式（包括但不限于公告、电子银行渠道、开户或开卡时提供的书面通知）向持卡人作出的告知为准。<span
									lang=EN-US><o:p></o:p> </span>
							</span> </span>
						</p>
						<p class=MsoNormal
							style='margin-top: 0cm; margin-right: 0cm; margin-bottom: 5pt; margin-left: 86.8pt; mso-para-margin-top: 0cm; mso-para-margin-right: 0cm; mso-para-margin-bottom: .5gd; mso-para-margin-left: 86.8pt; text-indent: -21.0pt; mso-list: l58 level1 lfo22; layout-grid-mode: char'>
							<span style='mso-bookmark: _Toc389184871'><![if !supportLists]><span
								lang=EN-US
								style='font-size: 10.5pt; font-family: 宋体; mso-bidi-font-family: 宋体'><span
									style='mso-list: Ignore'>13.<span
										style='font: 7.0pt "Times New Roman"'>&nbsp;&nbsp; </span>
								</span> </span> <![endif]><span style='font-size: 10.5pt; font-family: 宋体'>银行向持卡人提供对账服务，对账方式和频率由银行和持卡人申请开立相关账户时约定。<span
									lang=EN-US><o:p></o:p> </span>
							</span> </span>
						</p>
						<p class=MsoNormal
							style='margin-top: 0cm; margin-right: 0cm; margin-bottom: 5pt; margin-left: 86.8pt; mso-para-margin-top: 0cm; mso-para-margin-right: 0cm; mso-para-margin-bottom: .5gd; mso-para-margin-left: 86.8pt; text-indent: -21.0pt; mso-list: l58 level1 lfo22; layout-grid-mode: char'>
							<span style='mso-bookmark: _Toc389184871'><![if !supportLists]><b
								style='mso-bidi-font-weight: normal'><span lang=EN-US
									style='font-size: 10.5pt; font-family: 宋体; mso-bidi-font-family: 宋体'><span
										style='mso-list: Ignore'>14.<span
											style='font: 7.0pt "Times New Roman"'>&nbsp;&nbsp; </span>
									</span> </span> </b> <![endif]><b style='mso-bidi-font-weight: normal'><span
									style='font-size: 11pt; font-family: 宋体'>由于电力、系统通讯故障、不可抗力等客观原因导致借记卡暂时无法使用的，银行不承担相关责任。因网络故障等客观原因导致持卡人交易失败而造成的损失，以及持卡人在互联网上使用借记卡所导致的风险和损失，由<span
										lang=EN-US><o:p></o:p> </span>
								</span> </b> </span>
						</p>
						<p class=MsoNormal
							style='margin-top: 0cm; margin-right: 0cm; margin-bottom: 5pt; margin-left: 86.8pt; mso-para-margin-top: 0cm; mso-para-margin-right: 0cm; mso-para-margin-bottom: .5gd; mso-para-margin-left: 86.8pt; text-indent: -21.0pt; mso-list: l58 level1 lfo22; layout-grid-mode: char'>
							<span style='mso-bookmark: _Toc389184871'><![if !supportLists]><b
								style='mso-bidi-font-weight: normal'><span lang=EN-US
									style='font-size: 10.5pt; font-family: 宋体; mso-bidi-font-family: 宋体'><span
										style='mso-list: Ignore'><span
											style='font: 7.0pt "Times New Roman"'>&nbsp;&nbsp; </span> </span> </span>
							</b> <![endif]><b style='mso-bidi-font-weight: normal'><span
									style='font-size: 11pt; font-family: 宋体'>&nbsp;&nbsp;&nbsp;持卡人自行承担，银行存在重大过失或过错的除外。持卡人与商户之间发生的任何交易纠纷，均应由双方自行解决，银行不承担任何责任。持卡人不得以与商户发生纠纷为由要求银行撤销交易或退回款项。<span
										lang=EN-US><o:p></o:p> </span>
								</span> </b> </span>
						</p>
						<p class=MsoListParagraph
							style='margin-top: 0cm; margin-right: 0cm; margin-bottom: 8pt; margin-left: 86.8pt; mso-para-margin-top: 0cm; mso-para-margin-right: 0cm; mso-para-margin-bottom: 1.0gd; mso-para-margin-left: 86.8pt; text-indent: -21.0pt; mso-char-indent-count: 0; mso-outline-level: 1; mso-list: l58 level1 lfo22; layout-grid-mode: char'>
							<span style='mso-bookmark: _Toc389184871'><![if !supportLists]><b
								style='mso-bidi-font-weight: normal'><span lang=EN-US
									style='font-size: 10.5pt; font-family: 宋体; mso-bidi-font-family: 宋体'><span
										style='mso-list: Ignore'>15.<span
											style='font: 7.0pt "Times New Roman"'>&nbsp;&nbsp; </span>
									</span> </span> </b> <![endif]><span style='font-size: 10.5pt; font-family: 宋体'>借记卡有效期最长为十年。如卡片损坏、失灵或过期等原因需要更换时，持卡人应持借记卡和本人有效身份证件至银行办理换卡手续，同时交回原卡。<b
									style='mso-bidi-font-weight: normal; font-size: 11pt;'>如卡片芯片损坏不可读，则需自持卡人申请换卡交易之日起，等待<span
										lang=EN-US>30</span>个自然日的清算周期后，持卡人原借记卡电子现金账户中的余额可转至新卡的活期账户。银行可根据需要对卡片进行技术或功能调整而换发新卡，换发新卡无需事先征得持卡人的同意，持卡人须在银行以其认为适当的方式（包括但不限于公告、电子银行渠道、开户或开卡时提供的书面通知）通知的期限内前往营业网点办理换卡。
								</b>如果持卡人没有及时办理换卡，则持卡人所持的旧卡在银行换卡通知中说明的旧卡失效日后即不可继续使用。<b
									style='mso-bidi-font-weight: normal'><span lang=EN-US><o:p></o:p>
									</span> </b>
							</span> </span>
						</p>
						<p class=MsoListParagraph
							style='margin-top: 0cm; margin-right: 0cm; margin-bottom: 5.0pt; margin-left: 62.8pt; mso-para-margin-top: 0cm; mso-para-margin-right: 0cm; mso-para-margin-bottom: .5gd; mso-para-margin-left: 63.8pt; text-indent: -63.8pt; mso-char-indent-count: 0; mso-list: l1 level1 lfo1; layout-grid-mode: char'>
							<span style='mso-bookmark: _Toc389184871'><![if !supportLists]><b
								style='mso-bidi-font-weight: normal'><span lang=EN-US
									style='font-size: 10.5pt; font-family: 宋体; mso-bidi-font-family: 宋体'><span
										style='mso-list: Ignore'>第四十六条<span
											style='font: 7.0pt "Times New Roman"'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
										</span>
									</span> </span> </b> <![endif]><b style='mso-bidi-font-weight: normal'><span
									style='font-size: 10.5pt; font-family: 宋体'>遗失密码与挂失<span
										lang=EN-US><o:p></o:p> </span>
								</span> </b> </span>
						</p>

						<p class=MsoNormal
							style='margin-top: 0cm; margin-right: 0cm; margin-bottom: 5pt; margin-left: 86.8pt; mso-para-margin-top: 0cm; mso-para-margin-right: 0cm; mso-para-margin-bottom: .5gd; mso-para-margin-left: 86.8pt; text-indent: -21.3pt; mso-list: l26 level1 lfo24; layout-grid-mode: char'>
							<span style='mso-bookmark: _Toc389184871'><![if !supportLists]><b
								style='mso-bidi-font-weight: normal'><span lang=EN-US
									style='font-size: 10.5pt; font-family: 宋体; mso-bidi-font-family: 宋体'><span
										style='mso-list: Ignore'>1.<span
											style='font: 7.0pt "Times New Roman"'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
										</span>
									</span> </span> </b> <![endif]><b style='mso-bidi-font-weight: normal'><span
									style='font-size: 11pt; font-family: 宋体'>持卡人遗忘或遗失密码，应持借记卡和本人有效身份证件，向银行书面申请密码重置申请。持卡人自申请密码重置之日起</span>
							</b> </span><span style='mso-bookmark: _Toc389184871'><span
								style='font-size: 10.5pt; font-family: 宋体'>，借记卡下关联账户将不能通过银行卡进行任何操作（包括但不限于取现、消费、转账或更改密码），直至持卡人完成重置密码业务，新密码实时生效。对于借记卡密码遗忘的，银行当场可以核实确认持卡人本人身份的，应当及时为<b
									style='mso-bidi-font-weight: normal; font-size: 11pt;'>其办理密码重置服务。<span
										lang=EN-US><o:p></o:p> </span>
								</b>
							</span> </span>
						</p>
						<p class=MsoNormal
							style='margin-top: 0cm; margin-right: 0cm; margin-bottom: 5pt; margin-left: 86.8pt; mso-para-margin-top: 0cm; mso-para-margin-right: 0cm; mso-para-margin-bottom: .5gd; mso-para-margin-left: 86.8pt; text-indent: -21.3pt; mso-list: l26 level1 lfo24; layout-grid-mode: char'>
							<span style='mso-bookmark: _Toc389184871'><![if !supportLists]><b
								style='mso-bidi-font-weight: normal'><span lang=EN-US
									style='font-size: 10.5pt; font-family: 宋体; mso-bidi-font-family: 宋体'><span
										style='mso-list: Ignore'>2.<span
											style='font: 7.0pt "Times New Roman"'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
										</span>
									</span> </span> </b> <![endif]><b style='mso-bidi-font-weight: normal'><span
									style='font-size: 11pt; font-family: 宋体'>对于一张借记卡，持卡人如一日内在各渠道连续共三次输入交易密码不正确，银行有权按有关规定冻结该借记卡，冻结后取款、消费等部分交易不能进行。当日持卡人可凭本人有效身份证件到营业网点申请解锁，或次日有且仅有一次机会通过成功交易而解锁。若借记卡冻结<span
										lang=EN-US>1</span>个自然日后仍未成功解锁，则持卡人必须凭本人有效身份证件到营业网点申请解锁。<span
										lang=EN-US><o:p></o:p> </span>
								</span> </b> </span>
						</p>

						<p class=MsoNormal
							style='margin-top: 0cm; margin-right: 0cm; margin-bottom: 5pt; margin-left: 86.8pt; mso-para-margin-top: 0cm; mso-para-margin-right: 0cm; mso-para-margin-bottom: .5gd; mso-para-margin-left: 86.8pt; text-indent: -21.3pt; mso-list: l26 level1 lfo24; layout-grid-mode: char'>
							<span style='mso-bookmark: _Toc389184871'><![if !supportLists]><b
								style='mso-bidi-font-weight: normal'><span lang=EN-US
									style='font-size: 10.5pt; font-family: 宋体; mso-bidi-font-family: 宋体'><span
										style='mso-list: Ignore'>3.<span
											style='font: 7.0pt "Times New Roman"'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
										</span>
									</span> </span> </b> <![endif]><span style='font-size: 10.5pt; font-family: 宋体'>如借记卡被盗或遗失，持卡人应及时办理书面挂失或口头挂失。持卡人可通过银行电子银行、客服中心或营业网点等渠道办理口头挂失。持卡人办理口头挂失后，须在<span
									lang=EN-US>15</span>天内持本人有效身份证件至营业网点补办正式挂失手续，持卡人未在15天内办理正式挂失的，借记卡将自动解除口头挂失。持卡人须注意，一旦办理挂失，借记卡下关联的任何和所有账户将不能通过银行卡进行任何操作（包括但不限于取现、消费、转账或更改密码），直至持卡人补办新卡或销卡。<b
									style='mso-bidi-font-weight: normal; font-size: 11pt;'>挂失生效前借记卡被冒用所发生的资金损失及风险由持卡人承担。挂失生效后借记卡被冒用所发生的资金损失及风险，持卡人不需承担责任，但持卡人与他人合谋故意欺诈或有其他不诚实的行为，或者不配合银行调查情况，则由持卡人承担所有损失，银行不承担任何责任。<span
										lang=EN-US><o:p></o:p> </span>
								</b>
							</span> </span>
						</p>
						<p class=MsoNormal
							style='margin-top: 0cm; margin-right: 0cm; margin-bottom: 4pt; margin-left: 86.8pt; mso-para-margin-top: 0cm; mso-para-margin-right: 0cm; mso-para-margin-bottom: .5gd; mso-para-margin-left: 86.8pt; text-indent: -21.3pt; mso-list: l26 level1 lfo24; layout-grid-mode: char'>
							<span style='mso-bookmark: _Toc389184871'><![if !supportLists]><b
								style='mso-bidi-font-weight: normal'><span lang=EN-US
									style='font-size: 10.5pt; font-family: 宋体; mso-bidi-font-family: 宋体'><span
										style='mso-list: Ignore'>4.<span
											style='font: 7.0pt "Times New Roman"'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
										</span>
									</span> </span> </b> <![endif]><span style='font-size: 11pt; font-family: 宋体'>持卡人可书面申请撤销借记卡挂失。如果持卡人仅申请了借记卡口头或书面挂失，未办理补发新卡手续，则持卡人找到遗失卡仍想继续使用的，应持有效身份证件到营业网点书面申请撤销挂失，银行受理后可继续使用原卡。撤销挂失的申请人与原挂失的申请人必须一致。<b
									style='mso-bidi-font-weight: normal; font-size: 11pt;'>如果持卡人在借记卡书面挂失后申请了补发新卡手续，则持卡人的撤销挂失申请将不被受理。</b><span
									lang=EN-US><o:p></o:p> </span>
							</span> </span>
						</p>
						<p class=MsoNormal
							style='margin-top: 0cm; margin-right: 0cm; margin-bottom: 4pt; margin-left: 86.8pt; mso-para-margin-top: 0cm; mso-para-margin-right: 0cm; mso-para-margin-bottom: .5gd; mso-para-margin-left: 86.8pt; text-indent: -21.3pt; mso-list: l26 level1 lfo24; layout-grid-mode: char'>
							<span style='mso-bookmark: _Toc389184871'><![if !supportLists]><b
								style='mso-bidi-font-weight: normal'><span lang=EN-US
									style='font-size: 10.5pt; font-family: 宋体; mso-bidi-font-family: 宋体'><span
										style='mso-list: Ignore'>5.<span
											style='font: 7.0pt "Times New Roman"'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
										</span>
									</span> </span> </b> <![endif]><b style='mso-bidi-font-weight: normal'><span
									style='font-size: 11pt; font-family: 宋体'>借记卡电子现金账户中的电子现金账户余额不能挂失。<span
										lang=EN-US><o:p></o:p> </span>
								</span> </b> </span>
						</p>

						<p class=MsoListParagraph
							style='margin-top: 0cm; margin-right: 0cm; margin-bottom: 4pt; margin-left: 62.8pt; mso-para-margin-top: 0cm; mso-para-margin-right: 0cm; mso-para-margin-bottom: .5gd; mso-para-margin-left: 63.8pt; text-indent: -63.8pt; mso-char-indent-count: 0; mso-list: l1 level1 lfo1; layout-grid-mode: char'>
							<span style='mso-bookmark: _Toc389184871'><![if !supportLists]><b
								style='mso-bidi-font-weight: normal'><span lang=EN-US
									style='font-size: 10.5pt; font-family: 宋体; mso-bidi-font-family: 宋体'><span
										style='mso-list: Ignore'>第四十七条<span
											style='font: 7.0pt "Times New Roman"'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
										</span>
									</span> </span> </b> <![endif]><b style='mso-bidi-font-weight: normal'><span
									style='font-size: 10.5pt; font-family: 宋体'>借记卡销卡<span
										lang=EN-US><o:p></o:p> </span>
								</span> </b> </span>
						</p>
						<p class=MsoListParagraph
							style='margin-top: 0cm; margin-right: 0cm; margin-bottom: 2.5pt; margin-left: 65.55pt; text-indent: 0cm; mso-char-indent-count: 0; layout-grid-mode: char'>
							<span style='mso-bookmark: _Toc389184871'><span
								style='font-size: 10.5pt; font-family: 宋体'>持卡人终止使用借记卡，应向银行提出书面销卡申请，银行受理后，应结清全部交易款项、有关费用以及取消所有业务功能，方可办理销卡手续，同时取消借记卡与相关账户的关联。<span
									lang=EN-US><o:p></o:p> </span>
							</span> </span>
						</p>
						<p class=MsoNormal
							style='margin-bottom: 2.5pt; text-indent: 63.0pt; mso-char-indent-count: 7.0; layout-grid-mode: char'>
							<span style='mso-bookmark: _Toc389184871'>&nbsp;<span
								style='font-size: 10.5pt; font-family: 宋体'>属于下列情况之一的，银行可为持卡人办理销卡：<span
									lang=EN-US><o:p></o:p> </span>
							</span>
							</span>
						</p>
						<p class=MsoNormal
							style='margin-top: 0cm; margin-right: 0cm; margin-bottom: 2.5pt; margin-left: 86.8pt; text-indent: -21.85pt; mso-list: l6 level1 lfo23; layout-grid-mode: char'>
							<span style='mso-bookmark: _Toc389184871'><![if !supportLists]><span
								lang=EN-US
								style='font-size: 10.5pt; font-family: 宋体; mso-bidi-font-family: 宋体'><span
									style='mso-list: Ignore'>1)<span
										style='font: 7.0pt "Times New Roman"'>&nbsp;&nbsp;</span>
								</span> </span> <![endif]><span style='font-size: 10.5pt; font-family: 宋体'>&nbsp;持卡人凭本人有效身份证件，交回借记卡，要求结束其借记卡；或<span
									lang=EN-US><o:p></o:p> </span>
							</span> </span>
						</p>
						<p class=MsoNormal
							style='margin-top: 0cm; margin-right: 0cm; margin-bottom: 2.5pt; margin-left: 86.8pt; text-indent: -21.85pt; mso-list: l6 level1 lfo23; layout-grid-mode: char'>
							<span style='mso-bookmark: _Toc389184871'><![if !supportLists]><span
								lang=EN-US
								style='font-size: 10.5pt; font-family: 宋体; mso-bidi-font-family: 宋体'><span
									style='mso-list: Ignore'>2)<span
										style='font: 7.0pt "Times New Roman"'>&nbsp;&nbsp;</span>
								</span> </span> <![endif]><span style='font-size: 10.5pt; font-family: 宋体'>&nbsp;持卡人办理书面挂失当天，凭本人有效身份证件，要求结束其借记卡；或<span
									lang=EN-US><o:p></o:p> </span>
							</span> </span>
						</p>
						<p class=MsoNormal
							style='margin-top: 0cm; margin-right: 0cm; margin-bottom: 2.5pt; margin-left: 86.8pt; text-indent: -21.85pt; mso-list: l6 level1 lfo23; layout-grid-mode: char'>
							<span style='mso-bookmark: _Toc389184871'><![if !supportLists]><span
								lang=EN-US
								style='font-size: 10.5pt; font-family: 宋体; mso-bidi-font-family: 宋体'><span
									style='mso-list: Ignore'>3)<span
										style='font: 7.0pt "Times New Roman"'>&nbsp;&nbsp;&nbsp;&nbsp;</span>
								</span> </span>
							<![endif]><span style='font-size: 10.5pt; font-family: 宋体'>未成年持卡人按照<span
									lang=EN-US>1</span>）、<span lang=EN-US>2</span>）的规定办理销卡的，应由其监护人代为办理，或在其监护人陪同下办理，并应提供银行要求的相应文件。<span
									lang=EN-US><o:p></o:p> </span>
							</span> </span>
						</p>

						<p class=MsoListParagraph
							style='margin-top: 0cm; margin-right: 0cm; margin-bottom: 5.8pt; margin-left: 62.8pt; mso-para-margin-top: 0cm; mso-para-margin-right: 0cm; mso-para-margin-bottom: .5gd; mso-para-margin-left: 63.8pt; text-indent: -63.8pt; mso-char-indent-count: 0; mso-list: l1 level1 lfo1; layout-grid-mode: char'>
							<span style='mso-bookmark: _Toc389184871'><![if !supportLists]><b
								style='mso-bidi-font-weight: normal'><span lang=EN-US
									style='font-size: 10.5pt; font-family: 宋体; mso-bidi-font-family: 宋体'><span
										style='mso-list: Ignore'>第四十八条<span
											style='font: 7.0pt "Times New Roman"'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
										</span>
									</span> </span> </b> <![endif]><b style='mso-bidi-font-weight: normal'><span
									style='font-size: 10.5pt; font-family: 宋体'>权利义务<span
										lang=EN-US><o:p></o:p> </span>
								</span> </b> </span>
						</p>

						<p class=MsoNormal
							style='margin-top: 0cm; margin-right: 0cm; margin-bottom: 2.5pt; margin-left: 86.8pt; text-indent: -21.3pt; mso-list: l36 level1 lfo26; layout-grid-mode: char'>
							<span style='mso-bookmark: _Toc389184871'><![if !supportLists]><span
								lang=EN-US
								style='font-size: 10.5pt; font-family: 宋体; mso-bidi-font-family: 宋体'><span
									style='mso-list: Ignore'>1.<span
										style='font: 7.0pt "Times New Roman"'>&nbsp;&nbsp; </span>
								</span> </span> <![endif]><span style='font-size: 10.5pt; font-family: 宋体'>持卡人的权利义务<span
									lang=EN-US><o:p></o:p> </span>
							</span> </span>
						</p>
						<p class=MsoNormal
							style='margin-top: 0cm; margin-right: 0cm; margin-bottom: 2.5pt; margin-left: 108.05pt; text-indent: -23pt; mso-list: l55 level1 lfo28; layout-grid-mode: char'>
							<span style='mso-bookmark: _Toc389184871'><![if !supportLists]><span
								lang=EN-US
								style='font-size: 10.5pt; font-family: 宋体; mso-bidi-font-family: 宋体'><span
									style='mso-list: Ignore'>1)<span
										style='font: 7.0pt "Times New Roman"'>&nbsp;&nbsp;&nbsp;&nbsp;</span>
								</span> </span> <![endif]><span style='font-size: 10.5pt; font-family: 宋体'>持卡人凭有效借记卡可按规定办理存取现金、转账结算、消费、查询，还可办理电子现金绑定账户充值、圈提交易等。<span
									lang=EN-US><o:p></o:p> </span>
							</span> </span>
						</p>

						<p class=MsoNormal
							style='margin-top: 0cm; margin-right: 0cm; margin-bottom: 2.5pt; margin-left: 108.05pt; text-indent: -23pt; mso-list: l55 level1 lfo28; layout-grid-mode: char'>
							<span style='mso-bookmark: _Toc389184871'><![if !supportLists]><span
								lang=EN-US
								style='font-size: 10.5pt; font-family: 宋体; mso-bidi-font-family: 宋体'><span
									style='mso-list: Ignore'>2)<span
										style='font: 7.0pt "Times New Roman"'>&nbsp;&nbsp;&nbsp;&nbsp;</span>
								</span> </span> <![endif]><span style='font-size: 10.5pt; font-family: 宋体'>持卡人应关注借记卡账户及交易变动情况。若持卡人对某笔交易账目有异议，持卡人须在该笔交易的银行记账日起<span
									lang=EN-US>60</span>天内提出查询申请和更正要求，银行应当在<span lang=EN-US>30</span>天内给予答复，并按国家有关法律规定对持卡人的资信资料保密，本协议另有约定或银行另有规定的除外。<b
									style='mso-bidi-font-weight: normal; font-size: 11pt;'>若持卡人未在规定时间提出异议，则视同持卡人认可该等交易的相关记账内容。</b><span
									lang=EN-US><o:p></o:p> </span>
							</span> </span>
						</p>
						<p class=MsoNormal
							style='margin-top: 0cm; margin-right: 0cm; margin-bottom: 2.5pt; margin-left: 108.05pt; text-indent: -23pt; mso-list: l55 level1 lfo28; layout-grid-mode: char'>
							<span style='mso-bookmark: _Toc389184871'><![if !supportLists]><span
								lang=EN-US
								style='font-size: 10.5pt; font-family: 宋体; mso-bidi-font-family: 宋体'><span
									style='mso-list: Ignore'>3)<span
										style='font: 7.0pt "Times New Roman"'>&nbsp;&nbsp;&nbsp;&nbsp;</span>
								</span> </span> <![endif]><b style='mso-bidi-font-weight: normal'><span
									style='font-size: 11pt; font-family: 宋体'>持卡人须妥善保管借记卡和密码。因卡片保管不善或密码泄露所造成的全部损失由持卡人承担，由于银行管理原因造成的密码或卡片信息泄露除外。</span>
							</b> </span><span style='mso-bookmark: _Toc389184871'><span
								lang=EN-US style='font-size: 10.5pt; font-family: 宋体'><o:p></o:p>
							</span> </span>
						</p>
						<p class=MsoNormal
							style='margin-top: 0cm; margin-right: 0cm; margin-bottom: 2.5pt; margin-left: 108.05pt; text-indent: -23pt; mso-list: l55 level1 lfo28; layout-grid-mode: char'>
							<span style='mso-bookmark: _Toc389184871'><![if !supportLists]><span
								lang=EN-US
								style='font-size: 10.5pt; font-family: 宋体; mso-bidi-font-family: 宋体'><span
									style='mso-list: Ignore'>4)<span
										style='font: 7.0pt "Times New Roman"'>&nbsp;&nbsp;&nbsp;&nbsp;</span>
								</span> </span> <![endif]><b style='mso-bidi-font-weight: normal'><span
									style='font-size: 11pt; font-family: 宋体'>持卡人收到借记卡，应及时在卡片背面的签名栏内签上与申请表上相同的姓名，并在用卡时使用此签名。<span
										lang=EN-US><o:p></o:p> </span>
								</span> </b> </span>
						</p>
						<p class=MsoNormal
							style='margin-top: 0cm; margin-right: 0cm; margin-bottom: 2.5pt; margin-left: 108.05pt; text-indent: -23pt; mso-list: l55 level1 lfo28; layout-grid-mode: char'>
							<span style='mso-bookmark: _Toc389184871'><![if !supportLists]><span
								lang=EN-US
								style='font-size: 10.5pt; font-family: 宋体; mso-bidi-font-family: 宋体'><span
									style='mso-list: Ignore'>5)<span
										style='font: 7.0pt "Times New Roman"'>&nbsp;&nbsp;&nbsp;&nbsp;</span>
								</span> </span> <![endif]><b style='mso-bidi-font-weight: normal'><span
									style='font-size: 11pt; font-family: 宋体'>借记卡只限持卡人本人使用，不得出租、出借或转卖，否则，由此引起的风险和损失由持卡人本人承担。持卡人在申请表中填写的资料如有变更，如通讯地址、联系电话、住址、职业等，应立即通过临柜、网银、手机银行、电话银行等渠道通知银行，否则，由此引起的任何延误或损失均由持卡人承担。</span>
							</b> </span><span style='mso-bookmark: _Toc389184871'><span
								lang=EN-US style='font-size: 10.5pt; font-family: 宋体'><o:p></o:p>
							</span> </span>
						</p>

						



					</td>
				</tr>
			</table>
			<div style='page-break-before: always;'>
				<br />
			</div>
			<table class=MsoNormalTable border=1 cellspacing=0 cellpadding=0
				height=98% width=640
				style='width: 640pt; border-collapse: collapse; border: none; mso-border-alt: solid windowtext .5pt; mso-yfti-tbllook: 1184; mso-padding-alt: 0cm 5.4pt 0cm 5.4pt; mso-border-insideh: .5pt solid windowtext; mso-border-insidev: .5pt solid windowtext'>

				<tr style='mso-yfti-irow: 26; height: 29.7pt'>
					<td width=640 colspan=11 valign=top
						style='width: 640pt; border-top: solid windowtext 1.0pt; border-left: solid windowtext 1.0pt; border-bottom: solid windowtext 1.0pt; border-right: solid windowtext 1.0pt; mso-border-top-alt: solid windowtext .25pt; mso-border-alt: solid windowtext .25pt; mso-border-bottom-alt: solid windowtext 1.5pt; background: transparent; padding: 0cm 5.4pt 0cm 5.4pt; height: 29.7pt'>
						<p class=MsoNormal
							style='margin-top: 0cm; margin-right: 0cm; margin-bottom: 2.5pt; margin-left: 108.05pt; text-indent: -23pt; mso-list: l55 level1 lfo28; layout-grid-mode: char'>
							<span style='mso-bookmark: _Toc389184871'><![if !supportLists]><span
								lang=EN-US
								style='font-size: 10.5pt; font-family: 宋体; mso-bidi-font-family: 宋体'><span
									style='mso-list: Ignore'>6)<span
										style='font: 7.0pt "Times New Roman"'>&nbsp;&nbsp;&nbsp;&nbsp;</span>
								</span> </span> <![endif]><span style='font-size: 10.5pt; font-family: 宋体'>持卡人理解，银行可以不时以其认为适当的方式（包括但不限于公告、电子银行渠道、开户或开卡时提供的书面通知）向持卡人告知有关借记卡使用方面的限制和须知等，持卡人同意该等限制和须知对持卡人具有约束力，持卡人在使用借记卡时应当遵守该等限制和须知。<span
									lang=EN-US><o:p></o:p> </span>
							</span> </span>
						</p>
						<p class=MsoNormal
							style='margin-top: 0cm; margin-right: 0cm; margin-bottom: 4pt; margin-left: 108.05pt; mso-para-margin-top: 0cm; mso-para-margin-right: 0cm; mso-para-margin-bottom: .5gd; mso-para-margin-left: 108.05pt; text-indent: -23pt; mso-list: l55 level1 lfo28; layout-grid-mode: char'>
							<span style='mso-bookmark: _Toc389184871'><![if !supportLists]><span
								lang=EN-US
								style='font-size: 10.5pt; font-family: 宋体; mso-bidi-font-family: 宋体'><span
									style='mso-list: Ignore'>7)<span
										style='font: 7.0pt "Times New Roman"'>&nbsp;&nbsp;&nbsp;&nbsp;</span>
								</span> </span> <![endif]><span style='font-size: 10.5pt; font-family: 宋体'>持卡人理解和同意，持卡人对借记卡的使用须受限于相关法律法规的规定（包括但不限于银行卡方面的法规、账户使用和管理方面的法规、外汇管制方面的法规）。持卡人同时理解和同意，如果任何一项服务的提供需要涉及第三方（包括但不限于银联、收单机构、银行以外的其他自动终端所属机构等），则该等服务的提供还需受限于该等第三方可能规定或施加的限制。<span
									lang=EN-US><o:p></o:p> </span>
							</span> </span>
						</p>
						<p class=MsoNormal
							style='margin-top: 0cm; margin-right: 0cm; margin-bottom: 4pt; margin-left: 108.05pt; mso-para-margin-top: 0cm; mso-para-margin-right: 0cm; mso-para-margin-bottom: .5gd; mso-para-margin-left: 108.05pt; text-indent: -23pt; mso-list: l55 level1 lfo28; layout-grid-mode: char'>
							<span style='mso-bookmark: _Toc389184871'><![if !supportLists]><span
								lang=EN-US
								style='font-size: 10.5pt; font-family: 宋体; mso-bidi-font-family: 宋体'><span
									style='mso-list: Ignore'>8)<span
										style='font: 7.0pt "Times New Roman"'>&nbsp;&nbsp;&nbsp;&nbsp;</span>
								</span> </span> <![endif]><span style='font-size: 10.5pt; font-family: 宋体'>持卡人承诺并保证向银行提供的所有客户资料是合法、真实、正确、完整、有效的。持卡人同意并授权，为提供与借记卡有关服务之目的，银行可将其客户资料披露给银行认为必需的第三方，包括但不限于银行分支机构、关联机构、银行的服务机构、增值产品（服务）提供商、代理人、外包作业机构、联名合作方以及相关资信机构。<span
									lang=EN-US><o:p></o:p> </span>
							</span> </span>
						</p>
						<p class=MsoNormal
							style='margin-top: 0cm; margin-right: 0cm; margin-bottom: 4pt; margin-left: 108.05pt; mso-para-margin-top: 0cm; mso-para-margin-right: 0cm; mso-para-margin-bottom: .5gd; mso-para-margin-left: 108.05pt; text-indent: -23pt; mso-list: l55 level1 lfo28; layout-grid-mode: char'>
							<span style='mso-bookmark: _Toc389184871'><![if !supportLists]>
								<![endif]><span style='font-size: 10.5pt; font-family: 宋体'>&nbsp;&nbsp;&nbsp;&nbsp;借记卡申领人同意，无论银行是否同意其借记卡申请，银行进行其他产品和服务的销售时可使用其相关客户资料，法律法规另有规定的除外。<span
									lang=EN-US><o:p></o:p> </span>
							</span> </span>
						</p>

						<p class=MsoNormal
							style='margin-top: 0cm; margin-right: 0cm; margin-bottom: 2.5pt; margin-left: 86.8pt; text-indent: -21.3pt; mso-list: l36 level1 lfo26; layout-grid-mode: char'>
							<span style='mso-bookmark: _Toc389184871'><![if !supportLists]><span
								lang=EN-US
								style='font-size: 10.5pt; font-family: 宋体; mso-bidi-font-family: 宋体'><span
									style='mso-list: Ignore'>2.<span
										style='font: 7.0pt "Times New Roman"'>&nbsp;&nbsp; </span>
								</span> </span> <![endif]><span style='font-size: 10.5pt; font-family: 宋体'>银行的权利义务<span
									lang=EN-US><o:p></o:p> </span>
							</span> </span>
						</p>
						<p class=MsoNormal
							style='margin-top: 0cm; margin-right: 0cm; margin-bottom: 2.5pt; margin-left: 108.05pt; text-indent: -23pt; mso-list: l14 level1 lfo29; layout-grid-mode: char'>
							<span style='mso-bookmark: _Toc389184871'><![if !supportLists]><span
								lang=EN-US
								style='font-size: 10.5pt; font-family: 宋体; mso-bidi-font-family: 宋体'><span
									style='mso-list: Ignore'>1)<span
										style='font: 7.0pt "Times New Roman"'>&nbsp;&nbsp;&nbsp;&nbsp;</span>
								</span> </span> <![endif]><span style='font-size: 10.5pt; font-family: 宋体'>银行应依法合规经营借记卡业务，根据本协议及借记卡章程规定保护持卡人的合法权益，为持卡人提供优质、快捷的服务。<span
									lang=EN-US><o:p></o:p> </span>
							</span> </span>
						</p>
						<p class=MsoNormal
							style='margin-top: 0cm; margin-right: 0cm; margin-bottom: 2.5pt; margin-left: 108.05pt; text-indent: -23pt; mso-list: l14 level1 lfo29; layout-grid-mode: char'>
							<span style='mso-bookmark: _Toc389184871'><![if !supportLists]><span
								lang=EN-US
								style='font-size: 10.5pt; font-family: 宋体; mso-bidi-font-family: 宋体'><span
									style='mso-list: Ignore'>2)<span
										style='font: 7.0pt "Times New Roman"'>&nbsp;&nbsp;&nbsp;&nbsp;</span>
								</span> </span> <![endif]><b style='mso-bidi-font-weight: normal'><span
									style='font-size: 11pt; font-family: 宋体'>借记卡属于银行所有，银行保留收回或不发卡给客户的权利。</span>
							</b> </span><span style='mso-bookmark: _Toc389184871'><span
								style='font-size: 10.5pt; font-family: 宋体'>若发现持卡人在用卡过程中有不遵守本协议或借记卡章程或其他违规、违法行为的，或拖欠相关费用的，银行有权随时停止或终止向持卡人提供相应服务并通知持卡人。<span
									lang=EN-US><o:p></o:p> </span>
							</span> </span>
						</p>
						<p class=MsoNormal
							style='margin-top: 0cm; margin-right: 0cm; margin-bottom: 2.5pt; margin-left: 108.05pt; text-indent: -23pt; mso-list: l14 level1 lfo29; layout-grid-mode: char'>
							<span style='mso-bookmark: _Toc389184871'><![if !supportLists]><span
								lang=EN-US
								style='font-size: 10.5pt; font-family: 宋体; mso-bidi-font-family: 宋体'><span
									style='mso-list: Ignore'>3)<span
										style='font: 7.0pt "Times New Roman"'>&nbsp;&nbsp;&nbsp;&nbsp;</span>
								</span> </span> <![endif]><span style='font-size: 10.5pt; font-family: 宋体'>银行有权出于欺诈、伪冒或盗用等风险控制方面的考虑或需要而随时暂停或终止向持卡人提供本协议下的任何和所有服务，无需持卡人事先同意。<span
									lang=EN-US><o:p></o:p> </span>
							</span> </span>
						</p>
						<p class=MsoNormal
							style='margin-top: 0cm; margin-right: 0cm; margin-bottom: 2.5pt; margin-left: 108.05pt; text-indent: -23pt; mso-list: l14 level1 lfo29; layout-grid-mode: char'>
							<span style='mso-bookmark: _Toc389184871'><![if !supportLists]><span
								lang=EN-US
								style='font-size: 10.5pt; font-family: 宋体; mso-bidi-font-family: 宋体'><span
									style='mso-list: Ignore'>4)<span
										style='font: 7.0pt "Times New Roman"'>&nbsp;&nbsp;&nbsp;&nbsp;</span>
								</span> </span> <![endif]><b style='mso-bidi-font-weight: normal'><span
									style='font-size: 11pt; font-family: 宋体'>如果由于银行无法合理控制的任何事件或原因（包括但不限于其他机构的任何作为或不作为）而导致银行未能提供或履行本协议或借记卡章程下的任何服务或义务，银行对此不承担任何责任。<span
										lang=EN-US><o:p></o:p> </span>
								</span> </b> </span>
						</p>
						<p class=MsoNormal
							style='margin-top: 0cm; margin-right: 0cm; margin-bottom: 2.5pt; margin-left: 108.05pt; text-indent: -23pt; mso-list: l14 level1 lfo29; layout-grid-mode: char'>
							<span style='mso-bookmark: _Toc389184871'><![if !supportLists]><span
								lang=EN-US
								style='font-size: 10.5pt; font-family: 宋体; mso-bidi-font-family: 宋体'><span
									style='mso-list: Ignore'>5)<span
										style='font: 7.0pt "Times New Roman"'>&nbsp;&nbsp;&nbsp;&nbsp;</span>
								</span> </span> <![endif]><span style='font-size: 10.5pt; font-family: 宋体'>银行有权对伪造、盗用、冒领冒用借记卡进行诈骗以及利用借记卡在互联网上进行非法活动的行为进行起诉，并递交司法机关追究其刑事责任。<b
									style='mso-bidi-font-weight: normal; font-size: 11pt;'>凡是用密码进行的交易，银行均有权视为持卡人本人所为，由于银行的管理原因造成的密码泄露除外。</b>依据密码等电子信息办理的各类交易所产生的电子信息记录均为该项交易的有效凭据。<span
									lang=EN-US><o:p></o:p> </span>
							</span> </span>
						</p>
						<p class=MsoNormal
							style='margin-top: 0cm; margin-right: 0cm; margin-bottom: 2.5pt; margin-left: 108.05pt; text-indent: -23pt; mso-list: l14 level1 lfo29; layout-grid-mode: char'>
							<span style='mso-bookmark: _Toc389184871'><![if !supportLists]><span
								lang=EN-US
								style='font-size: 10.5pt; font-family: 宋体; mso-bidi-font-family: 宋体'><span
									style='mso-list: Ignore'>6)<span
										style='font: 7.0pt "Times New Roman"'>&nbsp;&nbsp;&nbsp;&nbsp;</span>
								</span> </span> <![endif]><span style='font-size: 10.5pt; font-family: 宋体'>银行有权依照法律法规监管规定、本协议或借记卡章程的规定协助国家司法机关或其他有权机关对持卡人的账户进行查询、冻结和扣划。<span
									lang=EN-US><o:p></o:p> </span>
							</span> </span>
						</p>
						<p class=MsoNormal
							style='margin-top: 0cm; margin-right: 0cm; margin-bottom: 2.5pt; margin-left: 108.05pt; text-indent: -23pt; mso-list: l14 level1 lfo29; layout-grid-mode: char'>
							<span style='mso-bookmark: _Toc389184871'><![if !supportLists]><span
								lang=EN-US
								style='font-size: 10.5pt; font-family: 宋体; mso-bidi-font-family: 宋体'><span
									style='mso-list: Ignore'>7)<span
										style='font: 7.0pt "Times New Roman"'>&nbsp;&nbsp;&nbsp;&nbsp;</span>
								</span> </span> <![endif]><span style='font-size: 10.5pt; font-family: 宋体'>
									借记卡章程由银行制定、解释和修改，向国家有关部门备案后执行。<b
									style='mso-bidi-font-weight: normal; font-size: 11pt;'>银行有权修改借记卡章程，并有权调整借记卡收费项目和标准。如银行修改借记卡章程或收费项目和收费标准，应通过营业网点、网站公告修改后的内容，公告满<span
										lang=EN-US>30</span>天后，修改后的借记卡章程、收费项目和收费标准即为生效。在公告期内，持卡人可以选择是否继续使用借记卡，持卡人因对章程、收费项目和收费标准的修改有异议而决定不继续使用借记卡的，可向银行提出销卡申请，银行为其办理销卡手续。公告期满，持卡人未提出销卡申请的，视为同意。<span
										lang=EN-US><o:p></o:p> </span>
								</b>
							</span> </span>
						</p>
						<p class=MsoNormal
							style='margin-top: 0cm; margin-right: 0cm; margin-bottom: 2.5pt; margin-left: 108.05pt; text-indent: -23pt; mso-list: l14 level1 lfo29; layout-grid-mode: char'>
							<span style='mso-bookmark: _Toc389184871'><![if !supportLists]><span
								lang=EN-US
								style='font-size: 10.5pt; font-family: 宋体; mso-bidi-font-family: 宋体'><span
									style='mso-list: Ignore'>8)<span
										style='font: 7.0pt "Times New Roman"'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span>
								</span> </span> <![endif]><span style='font-size: 10.5pt; font-family: 宋体'>为提供与借记卡有关服务之目的，银行有权将其客户资料披露给银行认为必需的第三方，包括但不限于银行分支机构、关联机构、银行的服务机构、增值产品（服务）提供商、代理人、外包作业机构、联名合作方以及相关资信机构。无论银行是否同意申领人的借记卡申请，银行进行其他产品和服务的销售时可使用其相关客户资料，法律法规另有规定的除外。<span
									lang=EN-US><o:p></o:p> </span>
							</span> </span>
						</p>

						<p class=MsoListParagraph
							style='margin-top: 0cm; margin-right: 0cm; margin-bottom: 4pt; margin-left: 62.8pt; mso-para-margin-top: 0cm; mso-para-margin-right: 0cm; mso-para-margin-bottom: .5gd; mso-para-margin-left: 63.8pt; text-indent: -63.8pt; mso-char-indent-count: 0; mso-list: l1 level1 lfo1; layout-grid-mode: char'>
							<span style='mso-bookmark: _Toc389184871'><![if !supportLists]><b
								style='mso-bidi-font-weight: normal'><span lang=EN-US
									style='font-size: 10.5pt; font-family: 宋体; mso-bidi-font-family: 宋体'><span
										style='mso-list: Ignore'>第四十九条<span
											style='font: 7.0pt "Times New Roman"'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
										</span>
									</span> </span> </b> <![endif]><b style='mso-bidi-font-weight: normal'><span
									style='font-size: 10.5pt; font-family: 宋体'>借记卡计息 <span
										lang=EN-US><o:p></o:p> </span>
								</span> </b> </span>
						</p>
						<p class=MsoNormal
							style='margin-top: 0cm; margin-right: 0cm; margin-bottom: 4pt; margin-left: 86.8pt; mso-para-margin-top: 0cm; mso-para-margin-right: 0cm; mso-para-margin-bottom: .5gd; mso-para-margin-left: 86.8pt; text-indent: -21.25pt; mso-list: l4 level1 lfo27; layout-grid-mode: char'>
							<span style='mso-bookmark: _Toc389184871'><![if !supportLists]><b
								style='mso-bidi-font-weight: normal'><span lang=EN-US
									style='font-size: 10.5pt; font-family: 宋体; mso-bidi-font-family: 宋体'><span
										style='mso-list: Ignore'>1.<span
											style='font: 7.0pt "Times New Roman"'>&nbsp;&nbsp;&nbsp;</span>
									</span> </span> </b> <![endif]><span style='font-size: 10.5pt; font-family: 宋体'>借记卡下所关联的相关账户中的存款按照中国人民银行公布的存款利率和计息办法计付利息，并由银行依法代扣缴利息税。<span
									lang=EN-US><o:p></o:p> </span>
							</span> </span>
						</p>
						<p class=MsoNormal
							style='margin-top: 0cm; margin-right: 0cm; margin-bottom: 4pt; margin-left: 86.8pt; mso-para-margin-top: 0cm; mso-para-margin-right: 0cm; mso-para-margin-bottom: .5gd; mso-para-margin-left: 86.8pt; text-indent: -21.25pt; mso-list: l4 level1 lfo27; layout-grid-mode: char'>
							<span style='mso-bookmark: _Toc389184871'><![if !supportLists]><b
								style='mso-bidi-font-weight: normal'><span lang=EN-US
									style='font-size: 10.5pt; font-family: 宋体; mso-bidi-font-family: 宋体'><span
										style='mso-list: Ignore'>2.<span
											style='font: 7.0pt "Times New Roman"'>&nbsp;&nbsp;&nbsp;</span>
									</span> </span> </b> <![endif]><b style='mso-bidi-font-weight: normal'><span
									style='font-size: 11pt; font-family: 宋体'>借记卡电子现金账户不计息。<span
										lang=EN-US><o:p></o:p> </span>
								</span> </b> </span>
						</p>
						<p class=MsoNormal
							style='margin-top: 0cm; margin-right: 0cm; margin-bottom: 1pt; margin-left: 86.8pt; mso-para-margin-top: 0cm; mso-para-margin-right: 0cm; mso-para-margin-bottom: .5gd; mso-para-margin-left: 86.8pt; text-indent: -21.25pt; mso-list: l4 level1 lfo27; layout-grid-mode: char'>
							<span style='mso-bookmark: _Toc389184871'><![if !supportLists]><b
								style='mso-bidi-font-weight: normal'><span lang=EN-US
									style='font-size: 10.5pt; font-family: 宋体; mso-bidi-font-family: 宋体'><span
										style='mso-list: Ignore'>3.<span
											style='font: 7.0pt "Times New Roman"'>&nbsp;&nbsp;&nbsp;</span>
									</span> </span> </b> <![endif]><span style='font-size: 10.5pt; font-family: 宋体'>根据银行相关管理办法，持卡人不再符合其所持金卡、白金卡或钻石卡客户资格，则持卡人将无法继续享受该客户资格的相关优惠。<b
									style='mso-bidi-font-weight: normal'><span lang=EN-US><o:p></o:p>
									</span> </b>
							</span> </span>
						</p>

						<p class=MsoListParagraph align=center
							style='margin-top: 0cm; margin-right: 0cm; margin-bottom: 10.6pt; margin-left: 50.2pt; mso-para-margin-top: 0cm; mso-para-margin-right: 0cm; mso-para-margin-bottom: 1.0gd; mso-para-margin-left: 50.2pt; text-align: center; text-indent: -50.2pt; mso-char-indent-count: 0; mso-outline-level: 1; mso-list: l50 level1 lfo2'>
							<span style='mso-bookmark: _Toc389184871'><![if !supportLists]><b
								style='mso-bidi-font-weight: normal'><span lang=EN-US
									style='font-size: 10.5pt; font-family: 宋体; mso-bidi-font-family: 宋体'><span
										style='mso-list: Ignore'>第六章<span
											style='font: 7.0pt "Times New Roman"'>&nbsp;&nbsp; </span>
									</span> </span> </b> <![endif]><b style='mso-bidi-font-weight: normal'><span
									style='font-size: 10.5pt; font-family: 宋体'>电子银行服务事项</span> </b> </span><b
								style='mso-bidi-font-weight: normal'><span lang=EN-US
								style='font-size: 10.5pt; font-family: 宋体'><o:p></o:p> </span> </b>
						</p>

						<p class=MsoListParagraph
							style='margin-top: 0cm; margin-right: 0cm; margin-bottom: 7pt; margin-left: 63.8pt; mso-para-margin-top: 0cm; mso-para-margin-right: 0cm; mso-para-margin-bottom: .5gd; mso-para-margin-left: 63.8pt; text-indent: -63.8pt; mso-char-indent-count: 0; mso-list: l1 level1 lfo1; tab-stops: 65.55pt; layout-grid-mode: char'>
							<![if !supportLists]>
							<b style='mso-bidi-font-weight: normal'><span lang=EN-US
								style='font-size: 10.5pt; font-family: 宋体; mso-bidi-font-family: 宋体'><span
									style='mso-list: Ignore'>第五十条<span
										style='font: 7.0pt "Times New Roman"'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
									</span>
								</span> </span> </b>
							<![endif]>
							<span style='font-size: 10.5pt; font-family: 宋体'>客户申请开通任一电子银行服务的，可于银行开立之人民币及外汇账户办理电子银行业务，银行向客户提供电子银行服务。相关服务适用本章节条款之约定。<b
								style='mso-bidi-font-weight: normal'><span lang=EN-US><o:p></o:p>
								</span> </b>
							</span>
						</p>

						<p class=MsoListParagraph
							style='margin-top: 0cm; margin-right: 0cm; margin-bottom: 7pt; margin-left: 62.8pt; mso-para-margin-top: 0cm; mso-para-margin-right: 0cm; mso-para-margin-bottom: .5gd; mso-para-margin-left: 63.8pt; text-indent: -63.8pt; mso-char-indent-count: 0; mso-list: l1 level1 lfo1; layout-grid-mode: char'>
							<![if !supportLists]>
							<b style='mso-bidi-font-weight: normal'><span lang=EN-US
								style='font-size: 10.5pt; font-family: 宋体; mso-bidi-font-family: 宋体'><span
									style='mso-list: Ignore'>第五十一条<span
										style='font: 7.0pt "Times New Roman"'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
									</span>
								</span> </span> </b>
							<![endif]>
							<b style='mso-bidi-font-weight: normal'><span
								style='font-size: 10.5pt; font-family: 宋体'>电子银行服务<span
									lang=EN-US><o:p></o:p> </span>
							</span> </b>
						</p>
						<p class=MsoListParagraph
							style='margin-top: 0cm; margin-right: 0cm; margin-bottom: 6pt; margin-left: 86.8pt; mso-para-margin-top: 0cm; mso-para-margin-right: 0cm; mso-para-margin-bottom: .5gd; mso-para-margin-left: 86.8pt; text-indent: -21.3pt; mso-char-indent-count: 0; mso-list: l22 level1 lfo5; layout-grid-mode: char'>
							<![if !supportLists]>
							<span lang=EN-US
								style='font-size: 10.5pt; font-family: 宋体; mso-bidi-font-family: 宋体'><span
								style='mso-list: Ignore'>1．<span
									style='font: 7.0pt "Times New Roman"'>&nbsp;&nbsp; </span>
							</span> </span>
							<![endif]>
							<span style='font-size: 10.5pt; font-family: 宋体'>电子银行服务的内容依照客户填写的开户申请资料中所选择的项目来确定。<span
								lang=EN-US><o:p></o:p> </span>
							</span>
						</p>
						<p class=MsoListParagraph
							style='margin-top: 0cm; margin-right: 0cm; margin-bottom: 6pt; margin-left: 86.8pt; mso-para-margin-top: 0cm; mso-para-margin-right: 0cm; mso-para-margin-bottom: .5gd; mso-para-margin-left: 86.8pt; text-indent: -21.3pt; mso-char-indent-count: 0; mso-list: l22 level1 lfo5; layout-grid-mode: char'>
							<![if !supportLists]>
							<span lang=EN-US
								style='font-size: 10.5pt; font-family: 宋体; mso-bidi-font-family: 宋体'><span
								style='mso-list: Ignore'>2．<span
									style='font: 7.0pt "Times New Roman"'>&nbsp;&nbsp; </span>
							</span> </span>
							<![endif]>
							<span style='font-size: 10.5pt; font-family: 宋体'>银行有权在事先网站公告的情况下，可随时变更所提供的服务种类：对使用电子银行服务附加或更改任何限制及<span
								lang=EN-US>/</span>或条件，包括但不限于银行不时规定的最低及最高限制；对提供的部分电子银行服务附加或更改任何限制，包括但不限于对电子银行服务任何部分的服务时间加以限制及对其暂停及<span
								lang=EN-US>/</span>或中止。客户有权随时变更（增加及<span lang=EN-US>/</span>或减少）所申请的服务种类，但须以银行约定的方式通知银行。<span
								lang=EN-US><o:p></o:p> </span>
							</span>
						</p>
						<p class=MsoListParagraph
							style='margin-top: 0cm; margin-right: 0cm; margin-bottom: 6pt; margin-left: 86.8pt; mso-para-margin-top: 0cm; mso-para-margin-right: 0cm; mso-para-margin-bottom: .5gd; mso-para-margin-left: 86.8pt; text-indent: -21.3pt; mso-char-indent-count: 0; mso-list: l22 level1 lfo5; layout-grid-mode: char'>
							<![if !supportLists]>
							<span lang=EN-US
								style='font-size: 10.5pt; font-family: 宋体; mso-bidi-font-family: 宋体'><span
								style='mso-list: Ignore'>3．<span
									style='font: 7.0pt "Times New Roman"'>&nbsp;&nbsp; </span>
							</span> </span>
							<![endif]>
							<span style='font-size: 10.5pt; font-family: 宋体'>客户每次使用电子银行服务均须遵守当时有效之电子银行服务事项的相关条款及银行网站公布的《富邦华一银行电子银行业务服务规定》。<span
								lang=EN-US><o:p></o:p> </span>
							</span>
						</p>
						<p class=MsoListParagraph
							style='margin-top: 0cm; margin-right: 0cm; margin-bottom: 6pt; margin-left: 86.8pt; mso-para-margin-top: 0cm; mso-para-margin-right: 0cm; mso-para-margin-bottom: .5gd; mso-para-margin-left: 86.8pt; text-indent: -21.3pt; mso-char-indent-count: 0; mso-list: l22 level1 lfo5; layout-grid-mode: char'>
							<![if !supportLists]>
							<span lang=EN-US
								style='font-size: 10.5pt; font-family: 宋体; mso-bidi-font-family: 宋体'><span
								style='mso-list: Ignore'>4．<span
									style='font: 7.0pt "Times New Roman"'>&nbsp;&nbsp; </span>
							</span> </span>

							<![endif]>
							<span style='font-size: 10.5pt; font-family: 宋体'>因不可抗力及客户自身操作失误所造成的客户损失，银行将予免责。不可抗力是指银行方面不可预见、不可避免并不可克服的任何客观情况，包括自然灾害、军事行为、法律或行政指示以及政府批准的变更、流行病、战争、恐怖主义行为、暴乱、叛乱、火灾、爆炸、地震、核事故、水灾、影响设施的断电、非常恶劣的天气条件等；<b
								style='mso-bidi-font-weight: normal; font-size: 11pt;'>此外，基于网络的特征，不可抗力还包括互联网服务、电信业务、电力供应的中断或停止，金融欺诈、电脑病毒感染、黑客侵入、软件炸弹或类似情况下的任何电脑硬件或软件故障。<span
									lang=EN-US><o:p></o:p> </span>
							</b>
							</span>
						</p>
						<p class=MsoListParagraph
							style='margin-top: 0cm; margin-right: 0cm; margin-bottom: 6pt; margin-left: 86.8pt; mso-para-margin-top: 0cm; mso-para-margin-right: 0cm; mso-para-margin-bottom: .5gd; mso-para-margin-left: 86.8pt; text-indent: -21.3pt; mso-char-indent-count: 0; mso-list: l22 level1 lfo5; layout-grid-mode: char'>
							<![if !supportLists]>
							<span lang=EN-US
								style='font-size: 10.5pt; font-family: 宋体; mso-bidi-font-family: 宋体'><span
								style='mso-list: Ignore'>5．<span
									style='font: 7.0pt "Times New Roman"'>&nbsp;&nbsp; </span>
							</span> </span>
							<![endif]>
							<span style='font-size: 10.5pt; font-family: 宋体'>客户应通过银行网站或官方认可的渠道下载、安装、登录或使用电子银行。使用非银行网站或官方认可的渠道所造成的风险及损失由客户自行承担。<span
								lang=EN-US><o:p></o:p> </span>
							</span>
						</p>
						
					</td>
				</tr>
			</table>

			<div style='page-break-before: always;'>
				<br />
			</div>
			<table class=MsoNormalTable border=1 cellspacing=0 cellpadding=0
				width=640 height=98%
				style='width: 640pt; border-collapse: collapse; border: none; mso-border-alt: solid windowtext .5pt; mso-yfti-tbllook: 1184; mso-padding-alt: 0cm 5.4pt 0cm 5.4pt; mso-border-insideh: .5pt solid windowtext; mso-border-insidev: .5pt solid windowtext'>

				<tr style='mso-yfti-irow: 26; height: 29.7pt'>
					<td width=640 colspan=11 valign=top
						style='width: 640pt; border-top: solid windowtext 1.0pt; border-left: solid windowtext 1.0pt; border-bottom: solid windowtext 1.0pt; border-right: solid windowtext 1.0pt; mso-border-top-alt: solid windowtext .25pt; mso-border-alt: solid windowtext .25pt; mso-border-bottom-alt: solid windowtext 1.5pt; background: transparent; padding: 0cm 5.4pt 0cm 5.4pt; height: 29.7pt'>
						<p class=MsoListParagraph
							style='margin-top: 0cm; margin-right: 0cm; margin-bottom: 6pt; margin-left: 86.8pt; mso-para-margin-top: 0cm; mso-para-margin-right: 0cm; mso-para-margin-bottom: .5gd; mso-para-margin-left: 86.8pt; text-indent: -21.3pt; mso-char-indent-count: 0; mso-list: l22 level1 lfo5; layout-grid-mode: char'>
							<![if !supportLists]>
							<span lang=EN-US
								style='font-size: 10.5pt; font-family: 宋体; mso-bidi-font-family: 宋体'><span
								style='mso-list: Ignore'>6．<span
									style='font: 7.0pt "Times New Roman"'>&nbsp;&nbsp; </span>
							</span> </span>
							<![endif]>
							<span style='font-size: 10.5pt; font-family: 宋体'>银行有对电子银行系统进行升级、改造的权利。在银行电子银行平台公告的系统升级、改造期间，银行有权中止有关电子银行服务。该期间客户因无法正常使用各项服务造成任何损失的，银行不承担任何赔偿责任。<span
								lang=EN-US><o:p></o:p> </span>
							</span>
						</p>
						<p class=MsoListParagraph
							style='margin-top: 0cm; margin-right: 0cm; margin-bottom: 6pt; margin-left: 86.8pt; mso-para-margin-top: 0cm; mso-para-margin-right: 0cm; mso-para-margin-bottom: .5gd; mso-para-margin-left: 86.8pt; text-indent: -21.3pt; mso-char-indent-count: 0; mso-list: l22 level1 lfo5; layout-grid-mode: char'>
							<![if !supportLists]>
							<span lang=EN-US
								style='font-size: 10.5pt; font-family: 宋体; mso-bidi-font-family: 宋体'><span
								style='mso-list: Ignore'>7．<span
									style='font: 7.0pt "Times New Roman"'>&nbsp;&nbsp; </span>
							</span> </span>
							<![endif]>
							<span style='font-size: 10.5pt; font-family: 宋体'>客户因银行系统差错、故障、错账或其他原因获得不当得利的，银行有权从客户账户中扣划不当得利涉及款项及<span
								lang=EN-US>/</span>或暂停为客户提供电子银行服务。如从客户账户中扣划的金额不足以抵扣所有不当得利的，银行有权就差额部分向客户追索。<span
								lang=EN-US><o:p></o:p> </span>
							</span>
						</p>
						<p class=MsoListParagraph
							style='margin-top: 0cm; margin-right: 0cm; margin-bottom: 6pt; margin-left: 86.8pt; mso-para-margin-top: 0cm; mso-para-margin-right: 0cm; mso-para-margin-bottom: .5gd; mso-para-margin-left: 86.8pt; text-indent: -21.3pt; mso-char-indent-count: 0; mso-list: l22 level1 lfo5; layout-grid-mode: char'>
							<![if !supportLists]>
							<span lang=EN-US
								style='font-size: 10.5pt; font-family: 宋体; mso-bidi-font-family: 宋体'><span
								style='mso-list: Ignore'>8．<span
									style='font: 7.0pt "Times New Roman"'>&nbsp;&nbsp; </span>
							</span> </span>
							<![endif]>
							<span style='font-size: 10.5pt; font-family: 宋体'>客户使用数字证书、证书设备时应遵守颁发数字证书或证书设备的第三方的相关服务协议的规定。<b
								style='mso-bidi-font-weight: normal; font-size: 11pt;'>因使用第三方颁发的数字证书或证书设备给客户造成经济损失的，客户应向第三方索赔。除银行存在故意或重大过失造成客户经济，银行对客户损失不承担赔偿责任。</b><span
								lang=EN-US><o:p></o:p> </span>
							</span>
						</p>
						<p class=MsoListParagraph
							style='margin-top: 0cm; margin-right: 0cm; margin-bottom: 5.8pt; margin-left: 62.8pt; mso-para-margin-top: 0cm; mso-para-margin-right: 0cm; mso-para-margin-bottom: .5gd; mso-para-margin-left: 63.8pt; text-indent: -63.8pt; mso-char-indent-count: 0; mso-list: l1 level1 lfo1; layout-grid-mode: char'>
							<![if !supportLists]>
							<b style='mso-bidi-font-weight: normal'><span lang=EN-US
								style='font-size: 10.5pt; font-family: 宋体; mso-bidi-font-family: 宋体'><span
									style='mso-list: Ignore'>第五十二条<span
										style='font: 7.0pt "Times New Roman"'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
									</span>
								</span> </span> </b>
							<![endif]>
							<b style='mso-bidi-font-weight: normal'><span
								style='font-size: 10.5pt; font-family: 宋体'>客户<span
									lang=EN-US><o:p></o:p> </span>
							</span> </b>
						</p>
						<p class=MsoListParagraph
							style='margin-top: 0cm; margin-right: 0cm; margin-bottom: 5.8pt; margin-left: 86.8pt; mso-para-margin-top: 0cm; mso-para-margin-right: 0cm; mso-para-margin-bottom: .5gd; mso-para-margin-left: 86.8pt; text-indent: -21.3pt; mso-char-indent-count: 0; mso-list: l49 level1 lfo6; layout-grid-mode: char'>
							<![if !supportLists]>
							<span lang=EN-US
								style='font-size: 10.5pt; font-family: 宋体; mso-bidi-font-family: 宋体'><span
								style='mso-list: Ignore'>1．<span
									style='font: 7.0pt "Times New Roman"'>&nbsp; </span>
							</span> </span>
							<![endif]>
							<span style='font-size: 10.5pt; font-family: 宋体'>电子银行服务的客户须具备如下条件：<span
								lang=EN-US><o:p></o:p> </span>
							</span>

						</p>

						<p class=MsoNormal
							style='margin-left: 108.05pt; text-indent: -21.0pt; mso-list: l31 level1 lfo3; tab-stops: list 42.0pt'>
							<![if !supportLists]>
							<span lang=EN-US
								style='font-size: 10.5pt; font-family: 宋体; mso-bidi-font-family: 宋体'><span
								style='mso-list: Ignore'>1)<span
									style='font: 7.0pt "Times New Roman"'>&nbsp;&nbsp;&nbsp;&nbsp;
								</span>
							</span> </span>
							<![endif]>
							<span style='font-size: 10.5pt; font-family: 宋体'>在申请电子银行服务前已在银行开户。<span
								lang=EN-US><o:p></o:p> </span>
							</span>
						</p>


						<p class=MsoNormal
							style='margin-left: 108.05pt; text-indent: -21.0pt; mso-list: l31 level1 lfo3; tab-stops: list 42.0pt'>
							<![if !supportLists]>
							<span lang=EN-US
								style='font-size: 10.5pt; font-family: 宋体; mso-bidi-font-family: 宋体'><span
								style='mso-list: Ignore'>2)<span
									style='font: 7.0pt "Times New Roman"'>&nbsp;&nbsp;&nbsp;&nbsp;
								</span>
							</span> </span>
							<![endif]>
							<span style='font-size: 10.5pt; font-family: 宋体'>完成电子银行开户申请。<span
								lang=EN-US><o:p></o:p> </span>
							</span>
						</p>
						<p class=MsoNormal
							style='margin-left: 108.05pt; text-indent: -21.0pt; mso-list: l31 level1 lfo3; tab-stops: list 42.0pt'>
							<![if !supportLists]>
							<span lang=EN-US
								style='font-size: 10.5pt; font-family: 宋体; mso-bidi-font-family: 宋体'><span
								style='mso-list: Ignore'>3)<span
									style='font: 7.0pt "Times New Roman"'>&nbsp;&nbsp;&nbsp;&nbsp;
								</span>
							</span> </span>
							<![endif]>
							<span style='font-size: 10.5pt; font-family: 宋体'>除查询业务外，客户需要网络银行提供电子银行服务的，须完成数字证书申请以获得银行签发的数字证书和设备。<span
								lang=EN-US><o:p></o:p> </span>
							</span>
						</p>

						<p class=MsoNormal
							style='margin-left: 108.05pt; text-indent: -21.0pt; mso-list: l31 level1 lfo3; tab-stops: list 42.0pt'>
							<![if !supportLists]>
							<span lang=EN-US
								style='font-size: 10.5pt; font-family: 宋体; mso-bidi-font-family: 宋体'><span
								style='mso-list: Ignore'>4)<span
									style='font: 7.0pt "Times New Roman"'>&nbsp;&nbsp;&nbsp;&nbsp;
								</span>
							</span> </span>
							<![endif]>
							<span style='font-size: 10.5pt; font-family: 宋体'>除查询业务外，客户需要微信银行提供电子银行服务的，须临柜提交纸质申请。<span
								lang=EN-US><o:p></o:p> </span>
							</span>
						</p>
						<p class=MsoNormal
							style='margin-left: 108.05pt; text-indent: -21.0pt; mso-list: l31 level1 lfo3; tab-stops: list 42.0pt'>
							<![if !supportLists]>
							<span lang=EN-US
								style='font-size: 10.5pt; font-family: 宋体; mso-bidi-font-family: 宋体'><span
								style='mso-list: Ignore'>5)<span
									style='font: 7.0pt "Times New Roman"'>&nbsp;&nbsp;&nbsp;&nbsp;
								</span>
							</span> </span>
							<![endif]>
							<span style='font-size: 10.5pt; font-family: 宋体'>除查询业务外，客户需要手机银行提供电子银行服务的，须临柜提交纸质申请。<span
								lang=EN-US><o:p></o:p> </span>
							</span>
						</p>
						<p class=MsoNormal style='margin-left: 86.8pt'>
							<span lang=EN-US style='font-size: 10.5pt; font-family: 宋体'><o:p>&nbsp;</o:p>
							</span>
						</p>
						<p class=MsoListParagraph
							style='margin-top: 0cm; margin-right: 0cm; margin-bottom: 5.8pt; margin-left: 86.8pt; mso-para-margin-top: 0cm; mso-para-margin-right: 0cm; mso-para-margin-bottom: .5gd; mso-para-margin-left: 86.8pt; text-indent: -21.3pt; mso-char-indent-count: 0; mso-list: l49 level1 lfo6; layout-grid-mode: char'>
							<![if !supportLists]>
							<span lang=EN-US
								style='font-size: 10.5pt; font-family: 宋体; mso-bidi-font-family: 宋体'><span
								style='mso-list: Ignore'>2．<span
									style='font: 7.0pt "Times New Roman"'>&nbsp; </span>
							</span> </span>
							<![endif]>
							<span style='font-size: 10.5pt; font-family: 宋体'>客户应保证办理业务关联账户的支付能力，并严格遵守支付结算业务的相关法律法规规定。<span
								lang=EN-US><o:p></o:p> </span>
							</span>
						</p>
						<p class=MsoListParagraph
							style='margin-top: 0cm; margin-right: 0cm; margin-bottom: 5.8pt; margin-left: 86.8pt; mso-para-margin-top: 0cm; mso-para-margin-right: 0cm; mso-para-margin-bottom: .5gd; mso-para-margin-left: 86.8pt; text-indent: -21.3pt; mso-char-indent-count: 0; mso-list: l49 level1 lfo6; layout-grid-mode: char'>
							<![if !supportLists]>
							<span lang=EN-US
								style='font-size: 10.5pt; font-family: 宋体; mso-bidi-font-family: 宋体'><span
								style='mso-list: Ignore'>3．<span
									style='font: 7.0pt "Times New Roman"'>&nbsp; </span>
							</span> </span>
							<![endif]>
							<span style='font-size: 10.5pt; font-family: 宋体'>客户不得以与第三方发生纠纷为由拒绝支付应付银行的各类款项。<span
								lang=EN-US><o:p></o:p> </span>
							</span>
						<p class=MsoListParagraph
							style='margin-top: 0cm; margin-right: 0cm; margin-bottom: 5.8pt; margin-left: 86.8pt; mso-para-margin-top: 0cm; mso-para-margin-right: 0cm; mso-para-margin-bottom: .5gd; mso-para-margin-left: 86.8pt; text-indent: -21.3pt; mso-char-indent-count: 0; mso-list: l49 level1 lfo6; layout-grid-mode: char'>
							<![if !supportLists]>
							<span lang=EN-US
								style='font-size: 10.5pt; font-family: 宋体; mso-bidi-font-family: 宋体'><span
								style='mso-list: Ignore'>4．<span
									style='font: 7.0pt "Times New Roman"'>&nbsp; </span>
							</span> </span>
							<![endif]>
							<span style='font-size: 10.5pt; font-family: 宋体'>客户应保证不得诋毁、损害银行声誉或恶意攻击银行电子银行系统。<span
								lang=EN-US><o:p></o:p> </span>
							</span>
						</p>
						<p class=MsoListParagraph
							style='margin-top: 0cm; margin-right: 0cm; margin-bottom: 5.8pt; margin-left: 86.8pt; mso-para-margin-top: 0cm; mso-para-margin-right: 0cm; mso-para-margin-bottom: .5gd; mso-para-margin-left: 86.8pt; text-indent: -21.3pt; mso-char-indent-count: 0; mso-list: l49 level1 lfo6; layout-grid-mode: char'>
							<![if !supportLists]>
							<span lang=EN-US
								style='font-size: 10.5pt; font-family: 宋体; mso-bidi-font-family: 宋体'><span
								style='mso-list: Ignore'>5．<span
									style='font: 7.0pt "Times New Roman"'>&nbsp; </span>
							</span> </span>
							<![endif]>
							<span style='font-size: 10.5pt; font-family: 宋体'>客户办理电子银行业务时，如其使用的功能涉及银行其他业务规定或规则时应同时遵守。<span
								lang=EN-US><o:p></o:p> </span>
							</span>
						</p>
						<p class=MsoListParagraph
							style='margin-top: 0cm; margin-right: 0cm; margin-bottom: 6pt; margin-left: 62.8pt; mso-para-margin-top: 0cm; mso-para-margin-right: 0cm; mso-para-margin-bottom: .5gd; mso-para-margin-left: 63.8pt; text-indent: -63.8pt; mso-char-indent-count: 0; mso-list: l1 level1 lfo1; layout-grid-mode: char'>
							<![if !supportLists]>
							<b style='mso-bidi-font-weight: normal'><span lang=EN-US
								style='font-size: 10.5pt; font-family: 宋体; mso-bidi-font-family: 宋体'><span
									style='mso-list: Ignore'>第五十三条<span
										style='font: 7.0pt "Times New Roman"'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
									</span>
								</span> </span> </b>
							<![endif]>
							<b style='mso-bidi-font-weight: normal'><span
								style='font-size: 10.5pt; font-family: 宋体'>数字证书、证书设备、密码和验证短信<span
									lang=EN-US><o:p></o:p> </span>
							</span> </b>
						</p>
						<p class=MsoListParagraph
							style='margin-top: 0cm; margin-right: 0cm; margin-bottom: 6pt; margin-left: 86.8pt; mso-para-margin-top: 0cm; mso-para-margin-right: 0cm; mso-para-margin-bottom: .5gd; mso-para-margin-left: 86.8pt; text-indent: -21.3pt; mso-char-indent-count: 0; mso-list: l57 level1 lfo7; layout-grid-mode: char'>
							<![if !supportLists]>
							<span lang=EN-US
								style='font-size: 10.5pt; font-family: 宋体; mso-bidi-font-family: 宋体'><span
								style='mso-list: Ignore'>1．<span
									style='font: 7.0pt "Times New Roman"'>&nbsp;&nbsp; </span>
							</span> </span>
							<![endif]>
							<span style='font-size: 10.5pt; font-family: 宋体'>就不同类型的电子银行服务而言，银行签发的数字证书、证书设备、密码、验证短信是客户进入相应银行电子银行系统或办理交易时确认客户身份的有效凭据。<span
								lang=EN-US><o:p></o:p> </span>
							</span>
						</p>
						<p class=MsoListParagraph
							style='margin-top: 0cm; margin-right: 0cm; margin-bottom: 6pt; margin-left: 86.8pt; mso-para-margin-top: 0cm; mso-para-margin-right: 0cm; mso-para-margin-bottom: .5gd; mso-para-margin-left: 86.8pt; text-indent: -21.3pt; mso-char-indent-count: 0; mso-list: l57 level1 lfo7; layout-grid-mode: char'>
							<![if !supportLists]>
							<span lang=EN-US
								style='font-size: 10.5pt; font-family: 宋体; mso-bidi-font-family: 宋体'><span
								style='mso-list: Ignore'>2．<span
									style='font: 7.0pt "Times New Roman"'>&nbsp;&nbsp; </span>
							</span> </span>
							<![endif]>
							<b style='mso-bidi-font-weight: normal'><span
								style='font-size: 11pt; font-family: 宋体'>客户同意，客户对保管数字证书、证书设备、密码和短信接收设备负全责，并将严加保密，自负风险。无论客户实际上是否将数字证书、证书设备、密码和短信接收设备提供给他人使用，均须对该条件下完成的一切金融交易负责。如客户已将数字证书、证书设备、密码和短信接收设备提供给他人使用，客户保证不因银行接收并执行了该他人的业务指令而对银行提起任何索赔要求。<span
									lang=EN-US><o:p></o:p> </span>
							</span> </b>
						</p>
						<p class=MsoListParagraph
							style='margin-top: 0cm; margin-right: 0cm; margin-bottom: 6pt; margin-left: 86.8pt; mso-para-margin-top: 0cm; mso-para-margin-right: 0cm; mso-para-margin-bottom: .5gd; mso-para-margin-left: 86.8pt; text-indent: -21.3pt; mso-char-indent-count: 0; mso-list: l57 level1 lfo7; layout-grid-mode: char'>
							<![if !supportLists]>
							<span lang=EN-US
								style='font-size: 10.5pt; font-family: 宋体; mso-bidi-font-family: 宋体'><span
								style='mso-list: Ignore'>3．<span
									style='font: 7.0pt "Times New Roman"'>&nbsp;&nbsp; </span>
							</span> </span>
							<![endif]>
							<span style='font-size: 10.5pt; font-family: 宋体'>如数字证书、证书设备、密码遗失、被盗，应及时到开户银行办理书面挂失手续，挂失受理后即时生效。挂失手续生效前，客户仍须对挂失前数字证书、设备和密码下完成的一切金融交易而引起的后果及可能造成的一切损失或损害负全责。客户留存于银行的手机号码如发生变更，客户应当于发生变更后5个工作日内以书面形式通知银行。<span
								lang=EN-US><o:p></o:p> </span>
							</span>
						</p>
						<p class=MsoListParagraph
							style='margin-top: 0cm; margin-right: 0cm; margin-bottom: 6pt; margin-left: 86.8pt; mso-para-margin-top: 0cm; mso-para-margin-right: 0cm; mso-para-margin-bottom: .5gd; mso-para-margin-left: 86.8pt; text-indent: -21.3pt; mso-char-indent-count: 0; mso-list: l57 level1 lfo7; layout-grid-mode: char'>
							<![if !supportLists]>
							<span lang=EN-US
								style='font-size: 10.5pt; font-family: 宋体; mso-bidi-font-family: 宋体'><span
								style='mso-list: Ignore'>4．<span
									style='font: 7.0pt "Times New Roman"'>&nbsp;&nbsp; </span>
							</span> </span>
							<![endif]>
							<span style='font-size: 10.5pt; font-family: 宋体'>如客户申请撤销挂失的，须临柜办理撤销挂失申请手续，撤销挂失受理后即时生效。银行不接受客户的网上撤销挂失申请业务。<span
								lang=EN-US><o:p></o:p> </span>
							</span>
						</p>
						<p class=MsoListParagraph
							style='margin-top: 0cm; margin-right: 0cm; margin-bottom: 7pt; margin-left: 62.8pt; mso-para-margin-top: 0cm; mso-para-margin-right: 0cm; mso-para-margin-bottom: .5gd; mso-para-margin-left: 63.8pt; text-indent: -63.8pt; mso-char-indent-count: 0; mso-list: l1 level1 lfo1; layout-grid-mode: char'>
							<![if !supportLists]>
							<b style='mso-bidi-font-weight: normal'><span lang=EN-US
								style='font-size: 10.5pt; font-family: 宋体; mso-bidi-font-family: 宋体'><span
									style='mso-list: Ignore'>第五十四条<span
										style='font: 7.0pt "Times New Roman"'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
									</span>
								</span> </span> </b>
							<![endif]>
							<b style='mso-bidi-font-weight: normal'><span
								style='font-size: 10.5pt; font-family: 宋体'>客户指令<span
									lang=EN-US><o:p></o:p> </span>
							</span> </b>
						</p>
						<p class=MsoListParagraph
							style='margin-top: 0cm; margin-right: 0cm; margin-bottom: 8.8pt; margin-left: 86.8pt; mso-para-margin-top: 0cm; mso-para-margin-right: 0cm; mso-para-margin-bottom: .5gd; mso-para-margin-left: 86.8pt; text-indent: -21.3pt; mso-char-indent-count: 0; mso-list: l38 level1 lfo8; layout-grid-mode: char'>
							<![if !supportLists]>
							<span lang=EN-US
								style='font-size: 10.5pt; font-family: 宋体; mso-bidi-font-family: 宋体'><span
								style='mso-list: Ignore'>1．<span
									style='font: 7.0pt "Times New Roman"'>&nbsp;&nbsp; </span>
							</span> </span>
							<![endif]>
							<span style='font-size: 10.5pt; font-family: 宋体'>客户对发出的任何业务指令承担责任。<span
								lang=EN-US><o:p></o:p> </span>
							</span>
						</p>
						<p class=MsoListParagraph
							style='margin-top: 0cm; margin-right: 0cm; margin-bottom: 7.8pt; margin-left: 86.8pt; mso-para-margin-top: 0cm; mso-para-margin-right: 0cm; mso-para-margin-bottom: .5gd; mso-para-margin-left: 86.8pt; text-indent: -21.3pt; mso-char-indent-count: 0; mso-list: l38 level1 lfo8; layout-grid-mode: char'>
							<![if !supportLists]>
							<span lang=EN-US
								style='font-size: 10.5pt; font-family: 宋体; mso-bidi-font-family: 宋体'><span
								style='mso-list: Ignore'>2．<span
									style='font: 7.0pt "Times New Roman"'>&nbsp;&nbsp; </span>
							</span> </span>
							<![endif]>
							<span style='font-size: 10.5pt; font-family: 宋体'>银行有义务快速准确地执行客户的业务指令。银行执行客户发送的业务指令，以客户在电子银行系统中成功递交指令的时间为准。<span
								lang=EN-US><o:p></o:p> </span>
							</span>
						</p>

						<p class=MsoListParagraph
							style='margin-top: 0cm; margin-right: 0cm; margin-bottom: 7.8pt; margin-left: 86.8pt; mso-para-margin-top: 0cm; mso-para-margin-right: 0cm; mso-para-margin-bottom: .5gd; mso-para-margin-left: 86.8pt; text-indent: -21.3pt; mso-char-indent-count: 0; mso-list: l38 level1 lfo8; layout-grid-mode: char'>
							<![if !supportLists]>
							<span lang=EN-US
								style='font-size: 10.5pt; font-family: 宋体; mso-bidi-font-family: 宋体'><span
								style='mso-list: Ignore'>3．<span
									style='font: 7.0pt "Times New Roman"'>&nbsp;&nbsp; </span>
							</span> </span>
							<![endif]>
							<span style='font-size: 10.5pt; font-family: 宋体'>客户发出业务指令后如发现业务指令在银行系统中被堵塞应及时通知银行，银行应按照双方约定的方式对该指令进行应急处理。如客户发现银行执行其业务指令确有错误，应当在其知道或应当知道错误发生之日起五日内以<span
								lang=EN-US>“</span>账务错误声明表<span lang=EN-US>”</span>的形式通知银行。银行应在接到通知之日起的七个银行工作日内告知客户调查结果。如错误确已发生并系银行原因，银行应在告知客户调查结果后三个银行工作日内作出改正，若因此造成客户损失的，将按《支付结算办法》的有关标准赔偿。预约交易的业务指令在预约交易日当天不得修改。
								<span lang=EN-US><o:p></o:p> </span>
							</span>
						</p>
						<p class=MsoListParagraph
							style='margin-top: 0cm; margin-right: 0cm; margin-bottom: 5.8pt; margin-left: 86.8pt; mso-para-margin-top: 0cm; mso-para-margin-right: 0cm; mso-para-margin-bottom: .5gd; mso-para-margin-left: 86.8pt; text-indent: -21.3pt; mso-char-indent-count: 0; mso-list: l38 level1 lfo8; layout-grid-mode: char'>
							<![if !supportLists]>
							<span lang=EN-US
								style='font-size: 10.5pt; font-family: 宋体; mso-bidi-font-family: 宋体'><span
								style='mso-list: Ignore'>4．<span
									style='font: 7.0pt "Times New Roman"'>&nbsp;&nbsp; </span>
							</span> </span>
							<![endif]>
							<span style='font-size: 10.5pt; font-family: 宋体'>银行因以下情况没有正确执行客户指令的，银行不承担任何责任：
								<span lang=EN-US><o:p></o:p> </span>
							</span>
						</p>
						<p class=MsoNormal
							style='margin-left: 108.05pt; text-indent: -21.0pt; mso-list: l5 level1 lfo4; tab-stops: list 42.0pt'>
							<![if !supportLists]>
							<span lang=EN-US
								style='font-size: 10.5pt; font-family: 宋体; mso-bidi-font-family: 宋体'><span
								style='mso-list: Ignore'>1)<span
									style='font: 7.0pt "Times New Roman"'>&nbsp;&nbsp; </span>
							</span> </span>
							<![endif]>
							<span style='font-size: 10.5pt; font-family: 宋体'>银行接收到的业务指令信息不明、存在乱码、不完整等。<span
								lang=EN-US><o:p></o:p> </span>
							</span>
						</p>
						<p class=MsoNormal
							style='margin-left: 108.05pt; text-indent: -21.0pt; mso-list: l5 level1 lfo4; tab-stops: list 42.0pt'>
							<![if !supportLists]>
							<span lang=EN-US
								style='font-size: 10.5pt; font-family: 宋体; mso-bidi-font-family: 宋体'><span
								style='mso-list: Ignore'>2)<span
									style='font: 7.0pt "Times New Roman"'>&nbsp;&nbsp; </span>
							</span> </span>
							<![endif]>
							<span style='font-size: 10.5pt; font-family: 宋体'>客户账户存款余额不足或信用额度不足。<span
								lang=EN-US><o:p></o:p> </span>
							</span>
						</p>
						<p class=MsoNormal
							style='margin-left: 108.05pt; text-indent: -21.0pt; mso-list: l5 level1 lfo4; tab-stops: list 42.0pt'>
							<![if !supportLists]>
							<span lang=EN-US
								style='font-size: 10.5pt; font-family: 宋体; mso-bidi-font-family: 宋体'><span
								style='mso-list: Ignore'>3)<span
									style='font: 7.0pt "Times New Roman"'>&nbsp;&nbsp; </span>
							</span> </span>
							<![endif]>
							<span style='font-size: 10.5pt; font-family: 宋体'>客户账户内资金被依法冻结。<span
								lang=EN-US><o:p></o:p> </span>
							</span>
						</p>
						<p class=MsoNormal
							style='margin-left: 108.05pt; text-indent: -21.0pt; mso-list: l5 level1 lfo4; tab-stops: list 42.0pt'>
							<![if !supportLists]>
							<span lang=EN-US
								style='font-size: 10.5pt; font-family: 宋体; mso-bidi-font-family: 宋体'><span
								style='mso-list: Ignore'>4)<span
									style='font: 7.0pt "Times New Roman"'>&nbsp;&nbsp; </span>
							</span> </span>
							<![endif]>
							<span style='font-size: 10.5pt; font-family: 宋体'>客户之行为乃出于欺诈或其他非法目的。<span
								lang=EN-US><o:p></o:p> </span>
							</span>
						</p>

						<p class=MsoNormal
							style='margin-left: 108.05pt; text-indent: -21.0pt; mso-list: l5 level1 lfo4; tab-stops: list 42.0pt'>
							<![if !supportLists]>
							<span lang=EN-US
								style='font-size: 10.5pt; font-family: 宋体; mso-bidi-font-family: 宋体'><span
								style='mso-list: Ignore'>5)<span
									style='font: 7.0pt "Times New Roman"'>&nbsp;&nbsp; </span>
							</span> </span>
							<![endif]>
							<span style='font-size: 10.5pt; font-family: 宋体'>客户未能正确依据电子银行服务的说明行事。<span
								lang=EN-US><o:p></o:p> </span>
							</span>
						</p>
						<p class=MsoNormal
							style='margin-left: 108.05pt; text-indent: -21.0pt; mso-list: l5 level1 lfo4; tab-stops: list 42.0pt'>
							<![if !supportLists]>
							<span lang=EN-US
								style='font-size: 10.5pt; font-family: 宋体; mso-bidi-font-family: 宋体'><span
								style='mso-list: Ignore'>6)<span
									style='font: 7.0pt "Times New Roman"'>&nbsp;&nbsp; </span>
							</span> </span>
							<![endif]>
							<span style='font-size: 10.5pt; font-family: 宋体'>不可抗力或其他不可归因于银行的情况。
								<span lang=EN-US><o:p></o:p> </span>
							</span>
						</p>

						<p class=MsoListParagraph
							style='margin-top: 0cm; margin-right: 0cm; margin-bottom: 5.8pt; margin-left: 62.8pt; mso-para-margin-top: 0cm; mso-para-margin-right: 0cm; mso-para-margin-bottom: .5gd; mso-para-margin-left: 63.8pt; text-indent: -63.8pt; mso-char-indent-count: 0; mso-list: l1 level1 lfo1; layout-grid-mode: char'>
							<![if !supportLists]>
							<b style='mso-bidi-font-weight: normal'><span lang=EN-US
								style='font-size: 10.5pt; font-family: 宋体; mso-bidi-font-family: 宋体'><span
									style='mso-list: Ignore'>第五十五条<span
										style='font: 7.0pt "Times New Roman"'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
									</span>
								</span> </span> </b>
							<![endif]>
							<b style='mso-bidi-font-weight: normal'><span
								style='font-size: 10.5pt; font-family: 宋体'>知识产权<span
									lang=EN-US><o:p></o:p> </span>
							</span> </b>
						</p>
						<p class=MsoListParagraph
							style='margin-top: 0cm; margin-right: 0cm; margin-bottom: 5.8pt; margin-left: 86.8pt; mso-para-margin-top: 0cm; mso-para-margin-right: 0cm; mso-para-margin-bottom: .5gd; mso-para-margin-left: 86.8pt; text-indent: -21.3pt; mso-char-indent-count: 0; mso-list: l3 level1 lfo9; layout-grid-mode: char'>
							<![if !supportLists]>
							<span lang=EN-US
								style='font-size: 10.5pt; font-family: 宋体; mso-bidi-font-family: 宋体'><span
								style='mso-list: Ignore'>1．<span
									style='font: 7.0pt "Times New Roman"'>&nbsp;&nbsp; </span>
							</span> </span>
							<![endif]>
							<span style='font-size: 10.5pt; font-family: 宋体'>电子银行之任何部分或有关信息供应商之商业秘密等均系银行或任何第三方的版权及<span
								lang=EN-US>/</span>或其它知识产权。<span lang=EN-US><o:p></o:p> </span>
							</span>
						</p>
						<p class=MsoListParagraph
							style='margin-top: 0cm; margin-right: 0cm; margin-bottom: 5.8pt; margin-left: 86.8pt; mso-para-margin-top: 0cm; mso-para-margin-right: 0cm; mso-para-margin-bottom: .5gd; mso-para-margin-left: 86.8pt; text-indent: -21.3pt; mso-char-indent-count: 0; mso-list: l3 level1 lfo9; layout-grid-mode: char'>
							<![if !supportLists]>
							<span lang=EN-US
								style='font-size: 10.5pt; font-family: 宋体; mso-bidi-font-family: 宋体'><span
								style='mso-list: Ignore'>2．<span
									style='font: 7.0pt "Times New Roman"'>&nbsp;&nbsp; </span>
							</span> </span>
							<![endif]>
							<span style='font-size: 10.5pt; font-family: 宋体'>非经银行书面授权或法律强制，客户不得作出，亦不得参与或容许任何第三人作出任何侵犯银行及信息供应商知识产权的行为。<span
								lang=EN-US><o:p></o:p> </span>
							</span>
						</p>
						<p class=MsoListParagraph
							style='margin-top: 0cm; margin-right: 0cm; margin-bottom: 5.8pt; margin-left: 86.8pt; mso-para-margin-top: 0cm; mso-para-margin-right: 0cm; mso-para-margin-bottom: .5gd; mso-para-margin-left: 86.8pt; text-indent: -21.3pt; mso-char-indent-count: 0; mso-list: l3 level1 lfo9; layout-grid-mode: char'>
							<![if !supportLists]>
							<span lang=EN-US
								style='font-size: 10.5pt; font-family: 宋体; mso-bidi-font-family: 宋体'><span
								style='mso-list: Ignore'>3．<span
									style='font: 7.0pt "Times New Roman"'>&nbsp;&nbsp; </span>
							</span> </span>
							<![endif]>
							<span style='font-size: 10.5pt; font-family: 宋体'>客户选择终止电子银行服务后，知识产权条款仍然有效。就保护银行及信息供应商知识产权的未尽事宜，按我国有关法律执行。<span
								lang=EN-US><o:p></o:p> </span>
							</span>
						</p>
						<p class=MsoListParagraph
							style='margin-top: 0cm; margin-right: 0cm; margin-bottom: 5.8pt; margin-left: 62.8pt; mso-para-margin-top: 0cm; mso-para-margin-right: 0cm; mso-para-margin-bottom: .5gd; mso-para-margin-left: 63.8pt; text-indent: -63.8pt; mso-char-indent-count: 0; mso-list: l1 level1 lfo1; layout-grid-mode: char'>
							<![if !supportLists]>
							<b style='mso-bidi-font-weight: normal'><span lang=EN-US
								style='font-size: 10.5pt; font-family: 宋体; mso-bidi-font-family: 宋体'><span
									style='mso-list: Ignore'>第五十六条<span
										style='font: 7.0pt "Times New Roman"'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
									</span>
								</span> </span> </b>
							<![endif]>
							<b style='mso-bidi-font-weight: normal'><span
								style='font-size: 10.5pt; font-family: 宋体'>暂停、恢复、展期及终止<span
									lang=EN-US><o:p></o:p> </span>
							</span> </b>
						</p>
						<p class=MsoListParagraph
							style='margin-top: 0cm; margin-right: 0cm; margin-bottom: 6.8pt; margin-left: 86.8pt; mso-para-margin-top: 0cm; mso-para-margin-right: 0cm; mso-para-margin-bottom: .5gd; mso-para-margin-left: 86.8pt; text-indent: -21.3pt; mso-char-indent-count: 0; mso-list: l13 level1 lfo11; layout-grid-mode: char'>
							<![if !supportLists]>
							<span lang=EN-US
								style='font-size: 10.5pt; font-family: 宋体; mso-bidi-font-family: 宋体'><span
								style='mso-list: Ignore'>1．<span
									style='font: 7.0pt "Times New Roman"'>&nbsp;&nbsp; </span>
							</span> </span>
							<![endif]>
							<span style='font-size: 10.5pt; font-family: 宋体'>客户暂停、恢复、展期或终止电子银行的，应本人亲自办理。办理时应提供相关资料，填写相关申请表，并亲自签名确认。<span
								lang=EN-US><o:p></o:p> </span>
							</span>
						</p>
						
					</td>
				</tr>
			</table>
			<div style='page-break-before: always;'>
				<br />
			</div>
			<table class=MsoNormalTable border=1 cellspacing=0 cellpadding=0
				width=640
				style='width: 640pt; border-collapse: collapse; border: none; mso-border-alt: solid windowtext .5pt; mso-yfti-tbllook: 1184; mso-padding-alt: 0cm 5.4pt 0cm 5.4pt; mso-border-insideh: .5pt solid windowtext; mso-border-insidev: .5pt solid windowtext'>

				<tr style='mso-yfti-irow: 27; height: 26.85pt'>
					<td width=640 colspan=11
						style='width: 640pt; border-top: solid windowtext 1.0pt; border-left: solid windowtext 1.0pt; border-bottom: solid windowtext 1pt; border-right: solid windowtext 1.0pt; mso-border-top-alt: solid windowtext 1.5pt; mso-border-top-alt: 1.5pt; mso-border-left-alt: .5pt; mso-border-bottom-alt: 1.5pt; mso-border-right-alt: .5pt; mso-border-color-alt: windowtext; mso-border-style-alt: solid; padding: 0cm 5.4pt 0cm 5.4pt; height: 26.85pt'>
						<p class=MsoListParagraph
							style='margin-top: 0cm; margin-right: 0cm; margin-bottom: 6.8pt; margin-left: 86.8pt; mso-para-margin-top: 0cm; mso-para-margin-right: 0cm; mso-para-margin-bottom: .5gd; mso-para-margin-left: 86.8pt; text-indent: -21.3pt; mso-char-indent-count: 0; mso-list: l13 level1 lfo11; layout-grid-mode: char'>
							<![if !supportLists]>
							<span lang=EN-US
								style='font-size: 10.5pt; font-family: 宋体; mso-bidi-font-family: 宋体'><span
								style='mso-list: Ignore'>2．<span
									style='font: 7.0pt "Times New Roman"'>&nbsp;&nbsp; </span>
							</span> </span>
							<![endif]>
							<span style='font-size: 10.5pt; font-family: 宋体'>客户如需要暂时停止使用或恢复使用电子银行系统，均应以书面形式通知银行，银行自收到客户通知之日起的七个银行工作日内按客户要求冻结或解冻其全部数字证书、证书设备和密码。<span
								lang=EN-US><o:p></o:p> </span>
							</span>
						</p>
						<p class=MsoListParagraph
							style='margin-top: 0cm; margin-right: 0cm; margin-bottom: 6.8pt; margin-left: 86.8pt; mso-para-margin-top: 0cm; mso-para-margin-right: 0cm; mso-para-margin-bottom: .5gd; mso-para-margin-left: 86.8pt; text-indent: -21.3pt; mso-char-indent-count: 0; mso-list: l13 level1 lfo11; layout-grid-mode: char'>
							<![if !supportLists]>
							<span lang=EN-US
								style='font-size: 10.5pt; font-family: 宋体; mso-bidi-font-family: 宋体'><span
								style='mso-list: Ignore'>3．<span
									style='font: 7.0pt "Times New Roman"'>&nbsp;&nbsp; </span>
							</span> </span>
							<![endif]>
							<span style='font-size: 10.5pt; font-family: 宋体'>数字证书有效期最少为一年（自银行发证之日起计算），期满后便不能使用。客户如需继续使用的，应按银行要求的形式办理展期手续。每次展期有效期最少为一年，展期次数不限。<span
								lang=EN-US><o:p></o:p> </span>
							</span>
						</p>
						<p class=MsoListParagraph
							style='margin-top: 0cm; margin-right: 0cm; margin-bottom: 6.8pt; margin-left: 86.8pt; mso-para-margin-top: 0cm; mso-para-margin-right: 0cm; mso-para-margin-bottom: .5gd; mso-para-margin-left: 86.8pt; text-indent: -21.3pt; mso-char-indent-count: 0; mso-list: l13 level1 lfo11; layout-grid-mode: char'>
							<![if !supportLists]>
							<span lang=EN-US
								style='font-size: 10.5pt; font-family: 宋体; mso-bidi-font-family: 宋体'><span
								style='mso-list: Ignore'>4．<span
									style='font: 7.0pt "Times New Roman"'>&nbsp;&nbsp; </span>
							</span> </span>
							<![endif]>
							<span style='font-size: 10.5pt; font-family: 宋体'>客户如需终止使用全部或部分电子银行服务应以书面形式通知银行。银行自收到客户通知之日起的七个银行工作日停止该客户全部或部分电子银行服务；客户在停止服务前发出的指令仍为有效指令，其应承担相应的法律后果。若全部电子银行服务停止后，本章节条款的效力即终止，但并不影响客户承担终止前因交易所产生的法律后果。<span
								lang=EN-US><o:p></o:p> </span>
							</span>
						</p>
						<p class=MsoListParagraph
							style='margin-top: 0cm; margin-right: 0cm; margin-bottom: 6.8pt; margin-left: 86.8pt; mso-para-margin-top: 0cm; mso-para-margin-right: 0cm; mso-para-margin-bottom: .5gd; mso-para-margin-left: 86.8pt; text-indent: -21.3pt; mso-char-indent-count: 0; mso-list: l13 level1 lfo11; layout-grid-mode: char'>
							<![if !supportLists]>
							<span lang=EN-US
								style='font-size: 10.5pt; font-family: 宋体; mso-bidi-font-family: 宋体'><span
								style='mso-list: Ignore'>5．<span
									style='font: 7.0pt "Times New Roman"'>&nbsp;&nbsp; </span>
							</span> </span>
							<![endif]>
							<span style='font-size: 10.5pt; font-family: 宋体'>银行提供的电子银行服务受客户银行账户状态的制约，如客户银行账户挂失、止付、被冻结或其他原因不能正常使用的，则电子银行相关服务自动中止。银行账号状态恢复正常时，相关电子银行服务自动恢复。<span
								lang=EN-US><o:p></o:p> </span>
							</span>
						</p>
						<p class=MsoListParagraph
							style='margin-top: 0cm; margin-right: 0cm; margin-bottom: 6.8pt; margin-left: 86.8pt; mso-para-margin-top: 0cm; mso-para-margin-right: 0cm; mso-para-margin-bottom: .5gd; mso-para-margin-left: 86.8pt; text-indent: -21.3pt; mso-char-indent-count: 0; mso-list: l13 level1 lfo11; layout-grid-mode: char'>
							<![if !supportLists]>
							<span lang=EN-US
								style='font-size: 10.5pt; font-family: 宋体; mso-bidi-font-family: 宋体'><span
								style='mso-list: Ignore'>6．<span
									style='font: 7.0pt "Times New Roman"'>&nbsp;&nbsp; </span>
							</span> </span>
							<![endif]>
							<span style='font-size: 10.5pt; font-family: 宋体'>客户存在未按时支付有关费用，不遵守银行有关业务规定或存在恶意操作、诋毁、损害银行声誉等情况的，银行有权单方终止对客户提供电子银行服务，并保留追究客户责任的权利。<span
								lang=EN-US><o:p></o:p> </span>
							</span>
						</p>
						<p class=MsoListParagraph
							style='margin-top: 0cm; margin-right: 0cm; margin-bottom: 5.8pt; margin-left: 86.8pt; mso-para-margin-top: 0cm; mso-para-margin-right: 0cm; mso-para-margin-bottom: .5gd; mso-para-margin-left: 86.8pt; text-indent: -21.3pt; mso-char-indent-count: 0; mso-list: l13 level1 lfo11; layout-grid-mode: char'>
							<![if !supportLists]>
							<span lang=EN-US
								style='font-size: 10.5pt; font-family: 宋体; mso-bidi-font-family: 宋体'><span
								style='mso-list: Ignore'>7．<span
									style='font: 7.0pt "Times New Roman"'>&nbsp;&nbsp; </span>
							</span> </span>
							<![endif]>
							<span style='font-size: 10.5pt; font-family: 宋体'>本章节效力终止后，客户无须退回数字证书和证书设备，银行不退还客户已缴纳的有关费用。<span
								lang=EN-US><o:p></o:p> </span>
							</span>
						</p>
						<p class=MsoListParagraph
							style='margin-top: 0cm; margin-right: 0cm; margin-bottom: 5.0pt; margin-left: 62.8pt; mso-para-margin-top: 0cm; mso-para-margin-right: 0cm; mso-para-margin-bottom: .5gd; mso-para-margin-left: 63.8pt; text-indent: -63.8pt; mso-char-indent-count: 0; mso-list: l1 level1 lfo1; layout-grid-mode: char'>
							<![if !supportLists]>
							<b style='mso-bidi-font-weight: normal'><span lang=EN-US
								style='font-size: 10.5pt; font-family: 宋体; mso-bidi-font-family: 宋体'><span
									style='mso-list: Ignore'>第五十七条<span
										style='font: 7.0pt "Times New Roman"'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
									</span>
								</span> </span> </b>
							<![endif]>
							<span style='font-size: 10.5pt; font-family: 宋体'>客户不得在电子银行系统内发送与电子银行业务无关或破坏性信息，否则由此造成的风险及损失由客户承担，银行保留追偿的权利。<b
								style='mso-bidi-font-weight: normal'><span lang=EN-US><o:p></o:p>
								</span> </b>
							</span>
						</p>

						<p class=MsoListParagraph align=center
							style='margin-top: 0cm; margin-right: 0cm; margin-bottom: 11pt; margin-left: 50.2pt; mso-para-margin-top: 0cm; mso-para-margin-right: 0cm; mso-para-margin-bottom: 1.0gd; mso-para-margin-left: 50.2pt; text-align: center; text-indent: -50.2pt; mso-char-indent-count: 0; mso-outline-level: 1; mso-list: l50 level1 lfo2'>
							<a name="_Toc389184873"><![if !supportLists]><b
								style='mso-bidi-font-weight: normal'><span lang=EN-US
									style='font-size: 10.5pt; font-family: 宋体; mso-bidi-font-family: 宋体'><span
										style='mso-list: Ignore'>第七章<span
											style='font: 7.0pt "Times New Roman"'>&nbsp;&nbsp; </span>
									</span> </span> </b> <![endif]><b style='mso-bidi-font-weight: normal'><span
									style='font-size: 10.5pt; font-family: 宋体'>其他事项</span> </b> </a><b
								style='mso-bidi-font-weight: normal'><span lang=EN-US
								style='font-size: 10.5pt; font-family: 宋体'><o:p></o:p> </span> </b>
						</p>
						<p class=MsoListParagraph
							style='margin-top: 0cm; margin-right: 0cm; margin-bottom: 6pt; margin-left: 62.8pt; mso-para-margin-top: 0cm; mso-para-margin-right: 0cm; mso-para-margin-bottom: .5gd; mso-para-margin-left: 63.8pt; text-indent: -63.8pt; mso-char-indent-count: 0; mso-list: l1 level1 lfo1; layout-grid-mode: char'>
							<![if !supportLists]>
							<b style='mso-bidi-font-weight: normal'><span lang=EN-US
								style='font-size: 10.5pt; font-family: 宋体; mso-bidi-font-family: 宋体'><span
									style='mso-list: Ignore'>第五十八条<span
										style='font: 7.0pt "Times New Roman"'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
									</span>
								</span> </span> </b>
							<![endif]>
							<b style='mso-bidi-font-weight: normal'><span
								style='font-size: 10.5pt; font-family: 宋体'>服务费用<span
									lang=EN-US><o:p></o:p> </span>
							</span> </b>
						</p>
						<p class=MsoListParagraph
							style='margin-top: 0cm; margin-right: 0cm; margin-bottom: 6pt; margin-left: 86.8pt; mso-para-margin-top: 0cm; mso-para-margin-right: 0cm; mso-para-margin-bottom: .5gd; mso-para-margin-left: 86.8pt; text-indent: -21.3pt; mso-char-indent-count: 0; mso-list: l32 level1 lfo10; layout-grid-mode: char'>
							<![if !supportLists]>
							<span lang=EN-US
								style='font-size: 10.5pt; font-family: 宋体; mso-bidi-font-family: 宋体'><span
								style='mso-list: Ignore'>1．<span
									style='font: 7.0pt "Times New Roman"'>&nbsp;&nbsp; </span>
							</span> </span>
							<![endif]>
							<span style='font-size: 10.5pt; font-family: 宋体'>客户开立本协议项下账户及选择使用本协议项下服务的，应支付相关费用，具体费用详见收费标准。客户可向银行在中国内地的各分支机构索阅收费标准。<span
								lang=EN-US><o:p></o:p> </span>
							</span>
						</p>
						<p class=MsoListParagraph
							style='margin-top: 0cm; margin-right: 0cm; margin-bottom: 6pt; margin-left: 86.8pt; mso-para-margin-top: 0cm; mso-para-margin-right: 0cm; mso-para-margin-bottom: .5gd; mso-para-margin-left: 86.8pt; text-indent: -21.3pt; mso-char-indent-count: 0; mso-list: l32 level1 lfo10; layout-grid-mode: char'>
							<![if !supportLists]>
							<span lang=EN-US
								style='font-size: 10.5pt; font-family: 宋体; mso-bidi-font-family: 宋体'><span
								style='mso-list: Ignore'>2．<span
									style='font: 7.0pt "Times New Roman"'>&nbsp;&nbsp; </span>
							</span> </span>
							<![endif]>
							<span style='font-size: 10.5pt; font-family: 宋体'>客户选择开立账户或开通某一项或某几项银行服务的，客户同意授权银行根据收费标准从其相关账户和<span
								lang=EN-US>/</span>或任何其他账户中自动扣收办理相关银行业务应支付的任何和所有收费和费用。若相关交易账户内余额不足以扣付手续费的，银行有权不予开通，后果概由客户自行承担，与银行无涉。<span
								lang=EN-US><o:p></o:p> </span>
							</span>
						</p>
						<p class=MsoListParagraph
							style='margin-top: 0cm; margin-right: 0cm; margin-bottom: 6pt; margin-left: 86.8pt; mso-para-margin-top: 0cm; mso-para-margin-right: 0cm; mso-para-margin-bottom: .5gd; mso-para-margin-left: 86.8pt; text-indent: -21.3pt; mso-char-indent-count: 0; mso-list: l32 level1 lfo10; layout-grid-mode: char'>
							<![if !supportLists]>
							<span lang=EN-US
								style='font-size: 10.5pt; font-family: 宋体; mso-bidi-font-family: 宋体'><span
								style='mso-list: Ignore'>3．<span
									style='font: 7.0pt "Times New Roman"'>&nbsp;&nbsp; </span>
							</span> </span>
							<![endif]>
							<span style='font-size: 10.5pt; font-family: 宋体'>客户于银行通过本协议项下服务（包括但不限于借记卡、电子银行等）办理银行业务的，如该业务有收取手续费之规定者，银行得直接自各该相关交易账户内扣除应付之手续费。若相关交易账户内余额不足以扣付手续费的，银行有权不予办理，后果概由客户自行承担，与银行无涉。<span
								lang=EN-US><o:p></o:p> </span>
							</span>
						</p>
						<p class=MsoListParagraph
							style='margin-top: 0cm; margin-right: 0cm; margin-bottom: 6pt; margin-left: 86.8pt; mso-para-margin-top: 0cm; mso-para-margin-right: 0cm; mso-para-margin-bottom: .5gd; mso-para-margin-left: 86.8pt; text-indent: -21.3pt; mso-char-indent-count: 0; mso-list: l32 level1 lfo10; layout-grid-mode: char'>
							<![if !supportLists]>
							<span lang=EN-US
								style='font-size: 10.5pt; font-family: 宋体; mso-bidi-font-family: 宋体'><span
								style='mso-list: Ignore'>4．<span
									style='font: 7.0pt "Times New Roman"'>&nbsp;&nbsp; </span>
							</span> </span>
							<![endif]>
							<span style='font-size: 10.5pt; font-family: 宋体'>若有其它银行服务的，其它服务的收费标准依该服务项目收取。<span
								lang=EN-US><o:p></o:p> </span>
							</span>
						</p>
						<p class=MsoListParagraph
							style='margin-top: 0cm; margin-right: 0cm; margin-bottom: 7.8pt; margin-left: 62.8pt; mso-para-margin-top: 0cm; mso-para-margin-right: 0cm; mso-para-margin-bottom: .5gd; mso-para-margin-left: 63.8pt; text-indent: -63.8pt; mso-char-indent-count: 0; mso-list: l1 level1 lfo1; layout-grid-mode: char'>
							<![if !supportLists]>
							<b style='mso-bidi-font-weight: normal'><span lang=EN-US
								style='font-size: 10.5pt; font-family: 宋体; mso-bidi-font-family: 宋体'><span
									style='mso-list: Ignore'>第五十九条<span
										style='font: 7.0pt "Times New Roman"'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
									</span>
								</span> </span> </b>
							<![endif]>
							<b style='mso-bidi-font-weight: normal'><span
								style='font-size: 10.5pt; font-family: 宋体'>协议的解释<span
									lang=EN-US><o:p></o:p> </span>
							</span> </b>
						</p>
						<p class=MsoListParagraph
							style='margin-top: 0cm; margin-right: 0cm; margin-bottom: 6pt; margin-left: 86.8pt; mso-para-margin-top: 0cm; mso-para-margin-right: 0cm; mso-para-margin-bottom: .5gd; mso-para-margin-left: 86.8pt; text-indent: -21.3pt; mso-char-indent-count: 0; mso-list: l56 level1 lfo13; layout-grid-mode: char'>
							<![if !supportLists]>
							<span lang=EN-US
								style='font-size: 10.5pt; font-family: 宋体; mso-bidi-font-family: 宋体'><span
								style='mso-list: Ignore'>1．<span
									style='font: 7.0pt "Times New Roman"'>&nbsp;&nbsp; </span>
							</span> </span>
							<![endif]>
							<span style='font-size: 10.5pt; font-family: 宋体'>本协议由银行负责解释，在解释时将充分考虑银行个人账户管理制度、银行服务的性质及相关法律、法规及规章等规范性文件的规定。<span
								lang=EN-US><o:p></o:p> </span>
							</span>
						</p>
						<p class=MsoListParagraph
							style='margin-top: 0cm; margin-right: 0cm; margin-bottom: 6pt; margin-left: 62.8pt; mso-para-margin-top: 0cm; mso-para-margin-right: 0cm; mso-para-margin-bottom: .5gd; mso-para-margin-left: 63.8pt; text-indent: -63.8pt; mso-char-indent-count: 0; mso-list: l1 level1 lfo1; layout-grid-mode: char'>
							<![if !supportLists]>
							<b style='mso-bidi-font-weight: normal'><span lang=EN-US
								style='font-size: 10.5pt; font-family: 宋体; mso-bidi-font-family: 宋体'><span
									style='mso-list: Ignore'>第六十条<span
										style='font: 7.0pt "Times New Roman"'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
									</span>
								</span> </span> </b>
							<![endif]>
							<b style='mso-bidi-font-weight: normal'><span
								style='font-size: 10.5pt; font-family: 宋体'>完整协议及可分割性<span
									lang=EN-US><o:p></o:p> </span>
							</span> </b>
						</p>
						<p class=MsoListParagraph
							style='margin-top: 0cm; margin-right: 0cm; margin-bottom: 6pt; margin-left: 86.8pt; mso-para-margin-top: 0cm; mso-para-margin-right: 0cm; mso-para-margin-bottom: .5gd; mso-para-margin-left: 86.8pt; text-indent: -21.3pt; mso-char-indent-count: 0; mso-list: l15 level1 lfo14; layout-grid-mode: char'>
							<![if !supportLists]>
							<span lang=EN-US
								style='font-size: 10.5pt; font-family: 宋体; mso-bidi-font-family: 宋体'><span
								style='mso-list: Ignore'>1．<span
									style='font: 7.0pt "Times New Roman"'>&nbsp;&nbsp; </span>
							</span> </span>
							<![endif]>
							<span style='font-size: 10.5pt; font-family: 宋体'>客户填写的开户手册、开户申请资料、相关声明文件是本协议的重要组成部分，与本协议具有同等效力。<span
								lang=EN-US><o:p></o:p> </span>
							</span>
						</p>
						<p class=MsoListParagraph
							style='margin-top: 0cm; margin-right: 0cm; margin-bottom: 6pt; margin-left: 86.8pt; mso-para-margin-top: 0cm; mso-para-margin-right: 0cm; mso-para-margin-bottom: .5gd; mso-para-margin-left: 86.8pt; text-indent: -21.3pt; mso-char-indent-count: 0; mso-list: l15 level1 lfo14; layout-grid-mode: char'>
							<![if !supportLists]>
							<span lang=EN-US
								style='font-size: 10.5pt; font-family: 宋体; mso-bidi-font-family: 宋体'><span
								style='mso-list: Ignore'>2．<span
									style='font: 7.0pt "Times New Roman"'>&nbsp;&nbsp; </span>
							</span> </span>
							<![endif]>
							<span style='font-size: 10.5pt; font-family: 宋体'>如本协议的部分规定与现行有效的法律、法规及规章等规范性文件的规定相抵触，银行及客户应按有关的法律、法规及规章等规范性文件的规定履行自己的权利义务，协议的其他部分效力不受影响。<span
								lang=EN-US><o:p></o:p> </span>
							</span>
						</p>
						<p class=MsoListParagraph
							style='margin-top: 0cm; margin-right: 0cm; margin-bottom: 6pt; margin-left: 62.8pt; mso-para-margin-top: 0cm; mso-para-margin-right: 0cm; mso-para-margin-bottom: .5gd; mso-para-margin-left: 63.8pt; text-indent: -63.8pt; mso-char-indent-count: 0; mso-list: l1 level1 lfo1; layout-grid-mode: char'>
							<![if !supportLists]>
							<b style='mso-bidi-font-weight: normal'><span lang=EN-US
								style='font-size: 10.5pt; font-family: 宋体; mso-bidi-font-family: 宋体'><span
									style='mso-list: Ignore'>第六十一条<span
										style='font: 7.0pt "Times New Roman"'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
									</span>
								</span> </span> </b>
							<![endif]>
							<b style='mso-bidi-font-weight: normal'><span
								style='font-size: 10.5pt; font-family: 宋体'>法律适用及争议解决方式<span
									lang=EN-US><o:p></o:p> </span>
							</span> </b>
						</p>
						<p class=MsoListParagraph
							style='margin-top: 0cm; margin-right: 0cm; margin-bottom: 6pt; margin-left: 86.8pt; mso-para-margin-top: 0cm; mso-para-margin-right: 0cm; mso-para-margin-bottom: .5gd; mso-para-margin-left: 86.8pt; text-indent: -21.3pt; mso-char-indent-count: 0; mso-list: l34 level1 lfo19; layout-grid-mode: char'>
							<![if !supportLists]>
							<span lang=EN-US
								style='font-size: 10.5pt; font-family: 宋体; mso-bidi-font-family: 宋体'><span
								style='mso-list: Ignore'>1．<span
									style='font: 7.0pt "Times New Roman"'>&nbsp;&nbsp; </span>
							</span> </span>
							<![endif]>
							<span style='font-size: 10.5pt; font-family: 宋体'>本协议及相关解释适用中华人民共和国法律。法律无明文规定的，可适用通行的金融行业惯例。<span
								lang=EN-US><o:p></o:p> </span>
							</span>
						</p>
						<p class=MsoListParagraph
							style='margin-top: 0cm; margin-right: 0cm; margin-bottom: 6pt; margin-left: 86.8pt; mso-para-margin-top: 0cm; mso-para-margin-right: 0cm; mso-para-margin-bottom: .5gd; mso-para-margin-left: 86.8pt; text-indent: -21.3pt; mso-char-indent-count: 0; mso-list: l34 level1 lfo19; layout-grid-mode: char'>
							<![if !supportLists]>
							<span lang=EN-US
								style='font-size: 10.5pt; font-family: 宋体; mso-bidi-font-family: 宋体'><span
								style='mso-list: Ignore'>2．<span
									style='font: 7.0pt "Times New Roman"'>&nbsp;&nbsp; </span>
							</span> </span>
							<![endif]>
							<span style='font-size: 10.5pt; font-family: 宋体'>本协议是对客户与银行的其它既有协议和约定的补充而非替代，如本协议与其它既有协议和约定有冲突，就本协议项下所述事项而言，应以本协议为准。<span
								lang=EN-US><o:p></o:p> </span>
							</span>
						</p>

						<p class=MsoListParagraph
							style='margin-top: 0cm; margin-right: 0cm; margin-bottom: 6pt; margin-left: 86.8pt; mso-para-margin-top: 0cm; mso-para-margin-right: 0cm; mso-para-margin-bottom: .5gd; mso-para-margin-left: 86.8pt; text-indent: -21.3pt; mso-char-indent-count: 0; mso-list: l34 level1 lfo19; layout-grid-mode: char'>
							<![if !supportLists]>
							<span lang=EN-US
								style='font-size: 10.5pt; font-family: 宋体; mso-bidi-font-family: 宋体'><span
								style='mso-list: Ignore'>3．<span
									style='font: 7.0pt "Times New Roman"'>&nbsp;&nbsp; </span>
							</span> </span>
							<![endif]>
							<span style='font-size: 10.5pt; font-family: 宋体'>在履行本协议的过程中，如发生争议，应首先通过协商解决；协商不成的，银行或客户任何一方可向协议签订地或银行所在地的人民法院提起诉讼。<span
								lang=EN-US><o:p></o:p> </span>
							</span>
						</p>
						<p class=MsoListParagraph
							style='margin-top: 0cm; margin-right: 0cm; margin-bottom: 6pt; margin-left: 62.8pt; mso-para-margin-top: 0cm; mso-para-margin-right: 0cm; mso-para-margin-bottom: .5gd; mso-para-margin-left: 63.8pt; text-indent: -63.8pt; mso-char-indent-count: 0; mso-list: l1 level1 lfo1; layout-grid-mode: char'>
							<![if !supportLists]>
							<b style='mso-bidi-font-weight: normal'><span lang=EN-US
								style='font-size: 10.5pt; font-family: 宋体; mso-bidi-font-family: 宋体'><span
									style='mso-list: Ignore'>第六十二条<span
										style='font: 7.0pt "Times New Roman"'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
									</span>
								</span> </span> </b>
							<![endif]>
							<b style='mso-bidi-font-weight: normal'><span
								style='font-size: 10.5pt; font-family: 宋体'>协议的效力和生效<span
									lang=EN-US><o:p></o:p> </span>
							</span> </b>
						</p>
						<p class=MsoListParagraph
							style='margin-top: 0cm; margin-right: 0cm; margin-bottom: 6pt; margin-left: 86.8pt; mso-para-margin-top: 0cm; mso-para-margin-right: 0cm; mso-para-margin-bottom: .5gd; mso-para-margin-left: 86.8pt; text-indent: -21.3pt; mso-char-indent-count: 0; mso-list: l17 level1 lfo12; layout-grid-mode: char'>
							<![if !supportLists]>
							<span lang=EN-US
								style='font-size: 10.5pt; font-family: 宋体; mso-bidi-font-family: 宋体'><span
								style='mso-list: Ignore'>1．<span
									style='font: 7.0pt "Times New Roman"'>&nbsp;&nbsp; </span>
							</span> </span>
							<![endif]>
							<span style='font-size: 10.5pt; font-family: 宋体'>协议的任何条款如因任何原因而被确认无效，都不影响本协议其他条款的效力。<span
								lang=EN-US><o:p></o:p> </span>
							</span>
						</p>
						<p class=MsoListParagraph
							style='margin-top: 0cm; margin-right: 0cm; margin-bottom: 6pt; margin-left: 86.8pt; mso-para-margin-top: 0cm; mso-para-margin-right: 0cm; mso-para-margin-bottom: .5gd; mso-para-margin-left: 86.8pt; text-indent: -21.3pt; mso-char-indent-count: 0; mso-list: l17 level1 lfo12; layout-grid-mode: char'>
							<![if !supportLists]>
							<span lang=EN-US
								style='font-size: 10.5pt; font-family: 宋体; mso-bidi-font-family: 宋体'><span
								style='mso-list: Ignore'>2．<span
									style='font: 7.0pt "Times New Roman"'>&nbsp;&nbsp; </span>
							</span> </span>
							<![endif]>
							<span style='font-size: 10.5pt; font-family: 宋体'>本协议自客户在开户手册签章之日起生效。双方确认，在签订本协议前，已就全部条款进行了详细地说明和讨论，双方对协议的全部条款均无疑义，并对协议涉及的权利义务、责任限制及免责条款的法律含义有了准确无误的理解。<span
								lang=EN-US><o:p></o:p> </span>
							</span>
						</p>

						<p class=MsoListParagraph
							style='margin-top: 0cm; margin-right: 0cm; margin-bottom: 6pt; margin-left: 86.8pt; mso-para-margin-top: 0cm; mso-para-margin-right: 0cm; mso-para-margin-bottom: .5gd; mso-para-margin-left: 86.8pt; text-indent: -21.3pt; mso-char-indent-count: 0; mso-list: l17 level1 lfo12; layout-grid-mode: char'>
							<![if !supportLists]>
							<span lang=EN-US
								style='font-size: 10.5pt; font-family: 宋体; mso-bidi-font-family: 宋体'><span
								style='mso-list: Ignore'>3．<span
									style='font: 7.0pt "Times New Roman"'>&nbsp;&nbsp; </span>
							</span> </span>
							<![endif]>
							<span style='font-size: 10.5pt; font-family: 宋体'>本协议于客户在银行开立的银行账户存续期间有效，如客户撤销在银行的银行账户，自正式销户之日起，本协议自动终止。在本协议生效期间，若客户申请注销部分或全部银行服务的，并为银行受理完成的，本协议中相关条款效力即为终止。<span
								lang=EN-US><o:p></o:p> </span>
							</span>
						</p>
						<p class=MsoListParagraph
							style='margin-top: 0cm; margin-right: 0cm; margin-bottom: 6pt; margin-left: 86.8pt; mso-para-margin-top: 0cm; mso-para-margin-right: 0cm; mso-para-margin-bottom: .5gd; mso-para-margin-left: 86.8pt; text-indent: -21.3pt; mso-char-indent-count: 0; mso-list: l17 level1 lfo12; layout-grid-mode: char'>
							<![if !supportLists]>
							<span lang=EN-US
								style='font-size: 10.5pt; font-family: 宋体; mso-bidi-font-family: 宋体'><span
								style='mso-list: Ignore'>4．<span
									style='font: 7.0pt "Times New Roman"'>&nbsp;&nbsp; </span>
							</span> </span>
							<![endif]>
							<span style='font-size: 10.5pt; font-family: 宋体'>若无其他相反约定，本协议之条款除适用于客户与银行之间就相关事项的约定外，也适用于客户与银行的分支机构之间就相关事项的约定。<span
								lang=EN-US><o:p></o:p> </span>
							</span>
						</p>
						<p class=MsoListParagraph
							style='margin-top: 0cm; margin-right: 0cm; margin-bottom: 7.8pt; margin-left: 86.8pt; mso-para-margin-top: 0cm; mso-para-margin-right: 0cm; mso-para-margin-bottom: .5gd; mso-para-margin-left: 86.8pt; text-indent: -21.3pt; mso-char-indent-count: 0; mso-list: l17 level1 lfo12; layout-grid-mode: char'>
							<![if !supportLists]>
							<span lang=EN-US
								style='font-size: 10.5pt; font-family: 宋体; mso-bidi-font-family: 宋体'><span
								style='mso-list: Ignore'>5．<span
									style='font: 7.0pt "Times New Roman"'>&nbsp;&nbsp; </span>
							</span> </span>
							<![endif]>
							<span style='font-size: 10.5pt; font-family: 宋体'>依照本协议的约定，银行有权提前终止本协议。<span
								lang=EN-US><o:p></o:p> </span>
							</span>
			</p>
			</td>
			</tr>
			</table>
<div style='page-break-before:always;'><br/></div>
<table class=MsoNormalTable border=1 cellspacing=0 cellpadding=0 width=640pt
	style='width: 640pt; border-collapse: collapse; border: none; mso-border-alt: solid windowtext .5pt; mso-yfti-tbllook: 1184; mso-padding-alt: 0cm 5.4pt 0cm 5.4pt; mso-border-insideh: .5pt solid windowtext; mso-border-insidev: .5pt solid windowtext'>
	
	<tr style='mso-yfti-irow: 27; height: 26.85pt'>
		<td width=610pt colspan=11
			style='width: 610pt; border-top: solid windowtext 1.0pt; border-left: solid windowtext 1.0pt; border-bottom: solid windowtext 1pt; border-right: solid windowtext 1.0pt; mso-border-top-alt: solid windowtext 1.5pt; mso-border-top-alt: 1.5pt; mso-border-left-alt: .5pt; mso-border-bottom-alt: 1.5pt; mso-border-right-alt: .5pt; mso-border-color-alt: windowtext; mso-border-style-alt: solid; background: #4BACC6; padding: 0cm 5.4pt 0cm 5.4pt; height: 26.85pt'>
			<p class=MsoNormalCxSpMiddle
				style='line-height: 115%; layout-grid-mode: char'>
				<b style='mso-bidi-font-weight: normal'><span
					style='font-size: 16.0pt; line-height: 110%; font-family: 宋体'>富邦华一银行美国纳税人须知<span
						lang=EN-US><o:p></o:p>
					</span>
				</span>
				</b>
			</p></td>
	</tr>
	<tr
		style='mso-yfti-irow: 28; page-break-inside: avoid;'>
		<td width=610pt colspan=11
			style='width: 610pt; border-top: none; border-left: solid windowtext 1.0pt; border-bottom: solid windowtext 1pt; border-right: solid windowtext 1.0pt; mso-border-top-alt: solid windowtext 1.5pt; mso-border-top-alt: 1.5pt; mso-border-left-alt: .5pt; mso-border-bottom-alt: 1.5pt; mso-border-right-alt: .5pt; mso-border-color-alt: windowtext; mso-border-style-alt: solid; background: transparent; padding: 0cm 5.4pt 0cm 5.4pt; height: 26.75pt'>
			
			<table style="width:610pt;border:solid windowtext 0.0pt;">
			  <tr>
			  	<td width="1%" style="vertical-align:top;"><b><span lang=EN-US
			  	style='line-height:150%;font-size:10.5pt;font-family:宋体;'>1.<span
			  	style='font:7.0pt "Times New Roman"'>&nbsp; </span></span></b></td>
			  	<td><b><span lang=EN-US
			  	style='font-size:10.5pt;line-height:150%;font-family:宋体'>美国纳税居民是指根据相关法规及税收协定，在美国负有纳税义务的个人。主要包括具有美国国籍，或持有美国绿卡或在<u>美国长期逗留的自然人<span lang=EN-US>*</span></u>。<span
			 	 style='font:7.0pt "Times New Roman"'>&nbsp; </span></span></b></td>
			  </tr>
			    <tr><!-- <td colspan="2">&nbsp;</td> --></tr>
			  <tr>
			  	<td width="1%" style="vertical-align: top;"><b><span lang=EN-US
			  	style='line-height:150%;font-size:10.5pt;font-family:宋体;'><span
			  	style='font:7.0pt "Times New Roman"'>&nbsp; </span></span></b></td>
			  	<td><span lang=EN-US
			  	style='font-size:10.5pt;line-height:150%;font-family:宋体'>*在美国长期逗留的自然人：满足当年内待满31天以上，并在三年内累计超过183天。（累计方式是当年每一天算一天，去年每一天算三分之一天，前年每一天算六分之一天）<span
			 	 style='font:7.0pt "Times New Roman"'>&nbsp; </span></span></td>
			  </tr>
			  <tr><!--<td colspan="2">&nbsp;</td>  --></tr>
			    <tr>
			  	<td width="1%" style="vertical-align: top;"><b><span lang=EN-US
			  	style='line-height:150%;font-size:10.5pt;font-family:宋体;'>2.<span
			  	style='font:7.0pt "Times New Roman"'>&nbsp; </span></span></b></td>
			  	<td><b><span lang=EN-US
			  	style='font-size:10.5pt;line-height:150%;font-family:宋体'>补充声明<span
			 	 style='font:7.0pt "Times New Roman"'>&nbsp; </span></span></b></td>
			  </tr>
			   <tr>
			   <td width="1%" style="vertical-align: top;"><b><span lang=EN-US
			  	style='line-height:155%;font-size:10.5pt;font-family:宋体;'><span
			  	style='font:7.0pt "Times New Roman"'>&nbsp; </span></span></b></td>
			  <td><span id= 'zjh1' lang=EN-US
			  	style='font-size:10.5pt;line-height:155%;font-family:宋体'><b>证件号1：</b><span
			 	 style='font:7.0pt "Times New Roman"'>&nbsp; </span></span></td>
			   </tr>
			    <tr>
			  	<td width="1%" style="vertical-align: top;"><b><span lang=EN-US
			  	style='line-height:155%;font-size:10.5pt;font-family:宋体;'><span
			  	style='font:7.0pt "Times New Roman"'>&nbsp; </span></span></b></td>
			  	<td><span lang=EN-US
			  	style='font-size:10.5pt;line-height:155%;font-family:宋体'><b>若身份声明与客户所填信息冲突，请完成以下部分：</b>If&nbsp;U.S. Status. Declaration is conflict with other
					information, please complete following part:<span
			 	 style='font:7.0pt "Times New Roman"'>&nbsp; </span></span></td>
			  </tr>
			   <tr><!-- <td colspan="2">&nbsp;</td> --></tr>
			 <tr>
			  	<td width="1%" style="vertical-align: top;"><b><span lang=EN-US
			  	style='line-height:155%;font-size:10.5pt;font-family:宋体;'><span
			  	style='font:7.0pt "Times New Roman"'>&nbsp; </span></span></b></td>
			  	<td><span lang=EN-US
			  	style='font-size:10.5pt;line-height:155%;font-family:宋体'><b>本人承诺本人为__________(国家)纳税公民，因以下原因：</b><span
			 	 style='font:7.0pt "Times New Roman"'>&nbsp; </span></span></td>
			  </tr>
			   <tr>
			  	<td width="1%" style="vertical-align: top;"><b><span lang=EN-US
			  	style='line-height:150%;font-size:10.5pt;font-family:宋体;'><span
			  	style='font:7.0pt "Times New Roman"'>&nbsp; </span></span></b></td>
			  	<td><span lang=EN-US
			  	style='font-size:10.5pt;line-height:150%;font-family:宋体'>I
					certify that I am a tax resident of__________(country)，for the following reason:<span
			 	 style='font:7.0pt "Times New Roman"'>&nbsp; </span></span></td>
			  </tr>
			    <tr><!-- <td colspan="2">&nbsp;</td> --></tr>
			  <tr>
			  	<!-- <td width="1%" style="vertical-align:top;"><b><span lang=EN-US
			  	style='line-height:150%;font-size:10.5pt;font-family:宋体;'><span
			  	style='font:7.0pt "Times New Roman"'>&nbsp; </span></span></b></td> -->
			  	<!-- <td><span lang=EN-US
			  	style='font-size:10.5pt;line-height:150%;font-family:宋体'><b><u>因以下原因</u>：for
					the following reason:</b><span
			 	 style='font:7.0pt "Times New Roman"'>&nbsp; </span></span></td> -->
			  </tr>
			  <tr>
			  	<td width="1%" style="vertical-align: top;"><b><span lang=EN-US
			  	style='line-height:150%;font-size:10.5pt;font-family:宋体;'><span
			  	style='font:7.0pt "Times New Roman"'>&nbsp; </span></span></b></td>
			  	<td><span lang=EN-US
			  	style='font-size:10.5pt;line-height:150%;font-family:宋体'>□在美国上学，但不负有纳税义务Studying
						in the U.S. but have no tax obligation<span
			 	 style='font:7.0pt "Times New Roman"'>&nbsp; </span></span></td>
			  </tr>
			  <tr>
			  	<td width="1%" style="vertical-align: top;"><b><span lang=EN-US
			  	style='line-height:150%;font-size:10.5pt;font-family:宋体;'><span
			  	style='font:7.0pt "Times New Roman"'>&nbsp; </span></span></b></td>
			  	<td><span lang=EN-US
			  	style='font-size:10.5pt;line-height:150%;font-family:宋体'>□在美国工作，但不负有纳税义务Working
						in the U.S. but have no tax obligation<span
			 	 style='font:7.0pt "Times New Roman"'>&nbsp; </span></span></td>
			  </tr>
			  <tr>
			  	<td width="1%" style="vertical-align: top;"><b><span lang=EN-US
			  	style='line-height:150%;font-size:10.5pt;font-family:宋体;'><span
			  	style='font:7.0pt "Times New Roman"'>&nbsp; </span></span></b></td>
			  	<td><span lang=EN-US
			  	style='font-size:10.5pt;line-height:150%;font-family:宋体'>□在美国出生，但已放弃或由于某种原因没有获取美国国籍（请出示CLN证明）<span
			 	 style='font:7.0pt "Times New Roman"'>&nbsp; </span></span></td>
			  </tr>
			  <tr>
			  	<td width="1%" style="vertical-align: top;"><b><span lang=EN-US
			  	style='line-height:150%;font-size:10.5pt;font-family:宋体;'><span
			  	style='font:7.0pt "Times New Roman"'>&nbsp; </span></span></b></td>
			  	<td><span lang=EN-US
			  	style='font-size:10.5pt;line-height:150%;font-family:宋体'>Was
					born in the U.S. ,but have relinquished or not obtained U.S.
					citizenship ( Please provide Certificate of Loss of Nationality of
					the United States)<span
			 	 style='font:7.0pt "Times New Roman"'>&nbsp; </span></span></td>
			  </tr>
			   <tr>
			  	<td width="1%" style="vertical-align: top;"><b><span lang=EN-US
			  	style='line-height:150%;font-size:10.5pt;font-family:宋体;'><span
			  	style='font:7.0pt "Times New Roman"'>&nbsp; </span></span></b></td>
			  	<td><span lang=EN-US
			  	style='font-size:10.5pt;line-height:150%;font-family:宋体'>□其他原因，请详细说明Other
						reason, please specify:_____________________________<span
			 	 style='font:7.0pt "Times New Roman"'>&nbsp; </span></span></td>
			  </tr>
			    <tr><!-- <td colspan="2">&nbsp;</td> --></tr>
			   <tr>
			  	<td width="1%" style="vertical-align: top;"><b><span lang=EN-US
			  	style='line-height:150%;font-size:10.5pt;font-family:宋体;'><span
			  	style='font:7.0pt "Times New Roman"'>&nbsp; </span></span></b></td>
			  	<td><span lang=EN-US
			  	style='font-size:10.5pt;line-height:150%;font-family:宋体'><b><u>导致出现以下信息</u>：
					the following information is identified:</b><span
			 	 style='font:7.0pt "Times New Roman"'>&nbsp; </span></span></td>
			  </tr>
			  <tr>
			  	<td width="1%" style="vertical-align: top;"><b><span lang=EN-US
			  	style='line-height:150%;font-size:10.5pt;font-family:宋体;'><span
			  	style='font:7.0pt "Times New Roman"'>&nbsp; </span></span></b></td>
			  	<td><span lang=EN-US
			  	style='font-size:10.5pt;line-height:150%;font-family:宋体'>□预留地址为美国地址&nbsp;U.S.
						address<span
			 	 style='font:7.0pt "Times New Roman"'>&nbsp; </span></span></td>
			  </tr>
			  <tr>
			  	<td width="1%" style="vertical-align: top;"><b><span lang=EN-US
			  	style='line-height:150%;font-size:10.5pt;font-family:宋体;'><span
			  	style='font:7.0pt "Times New Roman"'>&nbsp; </span></span></b></td>
			  	<td><span lang=EN-US
			  	style='font-size:10.5pt;line-height:150%;font-family:宋体'>□预留电话为美国电话&nbsp;U.S.
						phone number<span
			 	 style='font:7.0pt "Times New Roman"'>&nbsp; </span></span></td>
			  </tr>
			  <tr>
			  	<td width="1%" style="vertical-align: top;"><b><span lang=EN-US
			  	style='line-height:150%;font-size:10.5pt;font-family:宋体;'><span
			  	style='font:7.0pt "Times New Roman"'>&nbsp; </span></span></b></td>
			  	<td><span lang=EN-US
			  	style='font-size:10.5pt;line-height:150%;font-family:宋体'>□出生地为美国&nbsp;U.S. place
						of birth<span
			 	 style='font:7.0pt "Times New Roman"'>&nbsp; </span></span></td>
			  </tr>
			  <tr>
			  	<td width="1%" style="vertical-align: top;"><b><span lang=EN-US
			  	style='line-height:150%;font-size:10.5pt;font-family:宋体;'><span
			  	style='font:7.0pt "Times New Roman"'>&nbsp; </span></span></b></td>
			  	<td><span lang=EN-US
			  	style='font-size:10.5pt;line-height:150%;font-family:宋体'>□其他，请详细说明&nbsp;Other, please specify:_____________________________________<span
			 	 style='font:7.0pt "Times New Roman"'>&nbsp; </span></span></td>
			  </tr>
			   <tr>
			   <td width="1%" style="vertical-align: top;"><b><span lang=EN-US
			  	style='line-height:155%;font-size:10.5pt;font-family:宋体;'><span
			  	style='font:7.0pt "Times New Roman"'>&nbsp; </span></span></b></td>
			  <td><span id= 'zjh2' lang=EN-US
			  	style='font-size:10.5pt;line-height:155%;font-family:宋体'><b>证件号2：</b><span
			 	 style='font:7.0pt "Times New Roman"'>&nbsp; </span></span></td>
			   </tr>
			  			    <tr>
			  	<td width="1%" style="vertical-align: top;"><b><span lang=EN-US
			  	style='line-height:155%;font-size:10.5pt;font-family:宋体;'><span
			  	style='font:7.0pt "Times New Roman"'>&nbsp; </span></span></b></td>
			  	<td><span lang=EN-US
			  	style='font-size:10.5pt;line-height:155%;font-family:宋体'><b>若身份声明与客户所填信息冲突，请完成以下部分：</b>If&nbsp;U.S. Status. Declaration is conflict with other
					information, please complete following part:<span
			 	 style='font:7.0pt "Times New Roman"'>&nbsp; </span></span></td>
			  </tr>
			   <tr><!-- <td colspan="2">&nbsp;</td> --></tr>
			 <tr>
			  	<td width="1%" style="vertical-align: top;"><b><span lang=EN-US
			  	style='line-height:155%;font-size:10.5pt;font-family:宋体;'><span
			  	style='font:7.0pt "Times New Roman"'>&nbsp; </span></span></b></td>
			  	<td><span lang=EN-US
			  	style='font-size:10.5pt;line-height:155%;font-family:宋体'><b>本人承诺本人为__________(国家)纳税公民，因以下原因：</b><span
			 	 style='font:7.0pt "Times New Roman"'>&nbsp; </span></span></td>
			  </tr>
			   <tr>
			  	<td width="1%" style="vertical-align: top;"><b><span lang=EN-US
			  	style='line-height:150%;font-size:10.5pt;font-family:宋体;'><span
			  	style='font:7.0pt "Times New Roman"'>&nbsp; </span></span></b></td>
			  	<td><span lang=EN-US
			  	style='font-size:10.5pt;line-height:150%;font-family:宋体'>I
					certify that I am a tax resident of__________(country)，for the following reason:<span
			 	 style='font:7.0pt "Times New Roman"'>&nbsp; </span></span></td>
			  </tr>
			    <tr><!-- <td colspan="2">&nbsp;</td> --></tr>
			  <tr>
			  	<!-- <td width="1%" style="vertical-align:top;"><b><span lang=EN-US
			  	style='line-height:150%;font-size:10.5pt;font-family:宋体;'><span
			  	style='font:7.0pt "Times New Roman"'>&nbsp; </span></span></b></td> -->
			  	<!-- <td><span lang=EN-US
			  	style='font-size:10.5pt;line-height:150%;font-family:宋体'><b><u>因以下原因</u>：for
					the following reason:</b><span
			 	 style='font:7.0pt "Times New Roman"'>&nbsp; </span></span></td> -->
			  </tr>
			  <tr>
			  	<td width="1%" style="vertical-align: top;"><b><span lang=EN-US
			  	style='line-height:150%;font-size:10.5pt;font-family:宋体;'><span
			  	style='font:7.0pt "Times New Roman"'>&nbsp; </span></span></b></td>
			  	<td><span lang=EN-US
			  	style='font-size:10.5pt;line-height:150%;font-family:宋体'>□在美国上学，但不负有纳税义务Studying
						in the U.S. but have no tax obligation<span
			 	 style='font:7.0pt "Times New Roman"'>&nbsp; </span></span></td>
			  </tr>
			  <tr>
			  	<td width="1%" style="vertical-align: top;"><b><span lang=EN-US
			  	style='line-height:150%;font-size:10.5pt;font-family:宋体;'><span
			  	style='font:7.0pt "Times New Roman"'>&nbsp; </span></span></b></td>
			  	<td><span lang=EN-US
			  	style='font-size:10.5pt;line-height:150%;font-family:宋体'>□在美国工作，但不负有纳税义务Working
						in the U.S. but have no tax obligation<span
			 	 style='font:7.0pt "Times New Roman"'>&nbsp; </span></span></td>
			  </tr>
			  <tr>
			  	<td width="1%" style="vertical-align: top;"><b><span lang=EN-US
			  	style='line-height:150%;font-size:10.5pt;font-family:宋体;'><span
			  	style='font:7.0pt "Times New Roman"'>&nbsp; </span></span></b></td>
			  	<td><span lang=EN-US
			  	style='font-size:10.5pt;line-height:150%;font-family:宋体'>□在美国出生，但已放弃或由于某种原因没有获取美国国籍（请出示CLN证明）<span
			 	 style='font:7.0pt "Times New Roman"'>&nbsp; </span></span></td>
			  </tr>
			  <tr>
			  	<td width="1%" style="vertical-align: top;"><b><span lang=EN-US
			  	style='line-height:150%;font-size:10.5pt;font-family:宋体;'><span
			  	style='font:7.0pt "Times New Roman"'>&nbsp; </span></span></b></td>
			  	<td><span lang=EN-US
			  	style='font-size:10.5pt;line-height:150%;font-family:宋体'>Was
					born in the U.S. ,but have relinquished or not obtained U.S.
					citizenship ( Please provide Certificate of Loss of Nationality of
					the United States)<span
			 	 style='font:7.0pt "Times New Roman"'>&nbsp; </span></span></td>
			  </tr>
			   <tr>
			  	<td width="1%" style="vertical-align: top;"><b><span lang=EN-US
			  	style='line-height:150%;font-size:10.5pt;font-family:宋体;'><span
			  	style='font:7.0pt "Times New Roman"'>&nbsp; </span></span></b></td>
			  	<td><span lang=EN-US
			  	style='font-size:10.5pt;line-height:150%;font-family:宋体'>□其他原因，请详细说明Other
						reason, please specify:_____________________________<span
			 	 style='font:7.0pt "Times New Roman"'>&nbsp; </span></span></td>
			  </tr>
			    <tr><!-- <td colspan="2">&nbsp;</td> --></tr>
			   <tr>
			  	<td width="1%" style="vertical-align: top;"><b><span lang=EN-US
			  	style='line-height:150%;font-size:10.5pt;font-family:宋体;'><span
			  	style='font:7.0pt "Times New Roman"'>&nbsp; </span></span></b></td>
			  	<td><span lang=EN-US
			  	style='font-size:10.5pt;line-height:150%;font-family:宋体'><b><u>导致出现以下信息</u>：
					the following information is identified:</b><span
			 	 style='font:7.0pt "Times New Roman"'>&nbsp; </span></span></td>
			  </tr>
			  <tr>
			  	<td width="1%" style="vertical-align: top;"><b><span lang=EN-US
			  	style='line-height:150%;font-size:10.5pt;font-family:宋体;'><span
			  	style='font:7.0pt "Times New Roman"'>&nbsp; </span></span></b></td>
			  	<td><span lang=EN-US
			  	style='font-size:10.5pt;line-height:150%;font-family:宋体'>□预留地址为美国地址&nbsp;U.S.
						address<span
			 	 style='font:7.0pt "Times New Roman"'>&nbsp; </span></span></td>
			  </tr>
			  <tr>
			  	<td width="1%" style="vertical-align: top;"><b><span lang=EN-US
			  	style='line-height:150%;font-size:10.5pt;font-family:宋体;'><span
			  	style='font:7.0pt "Times New Roman"'>&nbsp; </span></span></b></td>
			  	<td><span lang=EN-US
			  	style='font-size:10.5pt;line-height:150%;font-family:宋体'>□预留电话为美国电话&nbsp;U.S.
						phone number<span
			 	 style='font:7.0pt "Times New Roman"'>&nbsp; </span></span></td>
			  </tr>
			  <tr>
			  	<td width="1%" style="vertical-align: top;"><b><span lang=EN-US
			  	style='line-height:150%;font-size:10.5pt;font-family:宋体;'><span
			  	style='font:7.0pt "Times New Roman"'>&nbsp; </span></span></b></td>
			  	<td><span lang=EN-US
			  	style='font-size:10.5pt;line-height:150%;font-family:宋体'>□出生地为美国&nbsp;U.S. place
						of birth<span
			 	 style='font:7.0pt "Times New Roman"'>&nbsp; </span></span></td>
			  </tr>
			  <tr>
			  	<td width="1%" style="vertical-align: top;"><b><span lang=EN-US
			  	style='line-height:150%;font-size:10.5pt;font-family:宋体;'><span
			  	style='font:7.0pt "Times New Roman"'>&nbsp; </span></span></b></td>
			  	<td><span lang=EN-US
			  	style='font-size:10.5pt;line-height:150%;font-family:宋体'>□其他，请详细说明&nbsp;Other, please specify:_____________________________________<span
			 	 style='font:7.0pt "Times New Roman"'>&nbsp; </span></span></td>
			  </tr>
			 <tr><!-- <td colspan="2">&nbsp;</td> --></tr>
			 <tr>
			 <!-- 
			  	<td width="1%" style="vertical-align: top;"><b><span lang=EN-US
			  	style='line-height:150%;font-size:10.5pt;font-family:宋体;'>3.<span
			  	style='font:7.0pt "Times New Roman"'>&nbsp; </span></span></b></td>
			  	<td><b><span lang=EN-US
			  	style='font-size:10.5pt;line-height:150%;font-family:宋体'>□
						客户了解并同意富邦华一银行为证实客户为美国纳税人身份，可于必要时向客户索取与此相关的证明文件，可代理客户向美国税法项下的扣缴义务人出示开户申请书或交付开户申请书副本，并可根据美国国外账户税收遵从法案等所适用的法律、法规、法令的有关规定向有权机关提供客户资料（包括但不限于账户名称、账号及账户余额等信息）。<span
			 	 style='font:7.0pt "Times New Roman"'>&nbsp; </span></span></b></td>
			 	 
			 	  -->
			  </tr>
  			</table>

			<p class=huawen
				style='margin-left: 30.05pt; mso-para-margin-left: .01gd; text-indent: -29.95pt; mso-char-indent-count: -2.84; line-height: 150%'>
				<b style='mso-bidi-font-weight: normal'><span lang=EN-US
					style='font-size: 10.5pt; line-height: 150%; font-family: 宋体; mso-font-kerning: 1.0pt; mso-ansi-language: EN-US; mso-fareast-language: ZH-CN'><o:p>&nbsp;</o:p>
				</span>
				</b>
			</p>
			
			</td>
	</tr>
</table>

<div style='page-break-before:always;'><br/></div>



<table class=MsoNormalTable border=1 cellspacing=0 cellpadding=0 width=650 
 style='width:640pt;border-collapse:
 collapse;border:none;mso-border-alt:solid windowtext .5pt;mso-yfti-tbllook:
 1184;mso-padding-alt:0cm 5.4pt 0cm 5.4pt;mso-border-insideh:.5pt solid windowtext;
 mso-border-insidev:.5pt solid windowtext'>
 <tr style='page-break-inside:avoid;height:26.9pt'>
  <td width=650 colspan=12 style='width:640pt;border-top:solid windowtext 1.0pt;border-left:
  solid windowtext 1.0pt;border-bottom:solid windowtext 1pt;border-right:
  solid windowtext 1.0pt;background:#4BACC6;padding:0cm 5.4pt 0cm 5.4pt;
  height:23pt'>
  <p class=MsoNormalCxSpMiddle style='line-height:115%;layout-grid-mode:char'><b><span
  style='font-size:16.0pt;line-height:110%;font-family:宋体'>客户声明及阅知</span></b></p>
  </td>
 </tr>
 
 <tr style='page-break-inside:avoid;height:240pt'>
  <td width=650 colspan=12 valign=top style='width:640pt;border:solid windowtext 1.0pt;
  border-top:none;background:transparent;padding:0cm 5.4pt 0cm 5.4pt;
  height:256.25pt'>
  
  <table style="width:610pt;border:solid windowtext 0.0pt;">
  <tr>
  	<td width="1%" style="vertical-align: top;"><span lang=EN-US
  style='line-height:128%;font-size:10.5pt;font-family:宋体;'>1.<span
  style='font:7.0pt "Times New Roman"'>&nbsp; </span></span></td>
  	<td><span lang=EN-US
  style='font-size:10.5pt;line-height:128%;font-family:宋体'>本人确认已阅读、明白及接受本申请书的内容，本申请书应与《富邦华一银行个人开户及综合服务协议》、《富邦华一银行个人借记卡章程》、《富邦华一银行个人联名账户管理协议书》及贵行其他业务规则以及本人不时提交贵行的其他申请、文件一并理解，并应视为规范本人与贵行之间关于开户及相关账户服务的一份完整协议。<span
  style='font:7.0pt "Times New Roman"'>&nbsp; </span></span></td>
  </tr>
  <tr><td colspan="2"></td></tr> <tr><td colspan="2"></td></tr>
 <!--  <tr >
  	<td width="1%" style="vertical-align: top;" ><span lang=EN-US
  style='line-height:128%;font-size:10.5pt;font-family:宋体;'>2.<span
  style='font:7.0pt "Times New Roman"'>&nbsp; </span></span></td>
  	<td><span lang=EN-US
  style='font-size:10.5pt;line-height:128%;font-family:宋体'>本人知晓并同意在本开户手册中申请的借记卡卡片等级默认为金卡（预制卡）。如需升级为高等级借记卡，可按贵行相关规定办理升级业务。<span
  style='font:7.0pt "Times New Roman"'>&nbsp; </span></span></td>
  </tr> -->
  <tr><td colspan="2"></td></tr> <tr><td colspan="2"></td></tr>
  <tr>
  	<td width="1%" style="vertical-align: top"><span lang=EN-US
  style='line-height:128%;font-size:10.5pt;font-family:宋体;'>2.<span
  style='font:7.0pt "Times New Roman"'>&nbsp; </span></span></td>
  	<td><span lang=EN-US
  style='font-size:10.5pt;line-height:128%;font-family:宋体'>本人保证在本开户申请书中及任何其他情况下提供的所有信息、资料（包括但不限于各类书面文件、电子数据、视听资料及电话录音等，下同）及所作声明均真实、准确、完整、合法、有效，否则责任自负。若上述提供的信息、材料及所作声明内容发生变更，本人应主动于变更发生之日起5个工作日内持身份证件原件，变更证明，新、旧预留印鉴亲自到原开户行处办理变更手续。变更内容的生效日期以银行通过审核的日期为准。因本人信息发生变更未及时通知贵行所造成的一切后果由本人承担。<span
  style='font:7.0pt "Times New Roman"'>&nbsp; </span></span></td>
  </tr>
   <tr><td colspan="2"></td></tr> <tr><td colspan="2"></td></tr>
  <tr>
  	<td width="1%" style="vertical-align: top"><span lang=EN-US
  style='line-height:128%;font-size:10.5pt;font-family:宋体;'>3.<span
  style='font:7.0pt "Times New Roman"'>&nbsp; </span></span></td>
  	<td><span lang=EN-US
  style='font-size:10.5pt;line-height:128%;font-family:宋体'>本人确认在此申请书中的“客户声明及阅知”栏中留下的客户签章视同本人已在《富邦华一银行个人开户及综合服务协议》和《美国纳税人须知》中签字确认。<span
  style='font:7.0pt "Times New Roman"'>&nbsp; </span></span></td>
  </tr>
   <tr><td colspan="2"></td></tr> <tr><td colspan="2"></td></tr>
    
  <tr><td colspan="2"></td></tr> <tr><td colspan="2"></td></tr>
  <tr>
  	<td width="1%" style="vertical-align: top"><span lang=EN-US
  style='line-height:128%;font-size:10.5pt;font-family:宋体;'><b>4.</b><span
  style='font:7.0pt "Times New Roman"'>&nbsp; </span></span></td>
  	<td><span lang=EN-US
  style='font-size:10.5pt;line-height:128%;font-family:宋体'><b>本人已经收到、阅读、理解并且接受贵行相关服务费率表，本人同意贵行从本人账户中扣除本人应付的相关费用。</b><span
  style='font:7.0pt "Times New Roman"'>&nbsp; </span></span></td>
  </tr>
  
  
  
  
  
   <tr><td colspan="2"></td></tr> <tr><td colspan="2"></td></tr>
  <!--   <tr>
  	<td width="1%" style="vertical-align: top"><span lang=EN-US
  style='line-height:128%;font-size:10.5pt;font-family:宋体;'>6.<span
  style='font:7.0pt "Times New Roman"'>&nbsp; </span></span></td>
  	<td><span lang=EN-US
  style='font-size:10.5pt;line-height:128%;font-family:宋体'>如本人违反本声明书，贵行有权视具体情形采取下列措施：将本人账户信息报告国家有权机关；如经核实本人提供的身份信息严重失实，有权终止与本人的业务关系，拒绝提供服务；认为必要的其他措施。<span
  style='font:7.0pt "Times New Roman"'>&nbsp; </span></span></td>
  </tr>-->
  <tr><td colspan="2"></td></tr> <tr><td colspan="2"></td></tr>
  <tr>
  	<td width="1%" style="vertical-align: top"><span lang=EN-US
  style='line-height:128%;font-size:10.5pt;font-family:宋体;'>5.<span
  style='font:7.0pt "Times New Roman"'>&nbsp; </span></span></td>
  	<td><span lang=EN-US
  style='font-size:10.5pt;line-height:128%;font-family:宋体'>本人了解并同意接受贵行借记卡产品附赠的增值产品（服务）及优惠活动，并同意遵守所公示的相关增值产品（服务）及优惠活动的条款与细则。该等附赠权益仅在借记卡有效期间享有，并且本人知晓并同意该等增值产品（服务）及优惠活动由第三方提供，并由该第三方承担相关义务及责任。<span
  style='font:7.0pt "Times New Roman"'>&nbsp; </span></span></td>
  </tr>
    <tr><td colspan="2"></td></tr> <tr><td colspan="2"></td></tr>
   <tr>
  	<td width="1%" style="vertical-align: top"><span lang=EN-US
  style='line-height:128%;font-size:10.5pt;font-family:宋体;'>6.<span
  style='font:7.0pt "Times New Roman"'>&nbsp; </span></span></td>
  	<td><span lang=EN-US
  style='font-size:10.5pt;line-height:128%;font-family:宋体'>若本人使用相关借记卡并满足获赠相关团体保险权益的条件的，本人同意并授权贵行为本人订立相关保险合同，并可将本人信息及资料提供给相关保险公司，本人认可相关保险金额及保险合同条款。<span
  style='font:7.0pt "Times New Roman"'>&nbsp; </span></span></td>
  </tr>
  <tr><td colspan="2"></td></tr> <tr><td colspan="2"></td></tr>
  
  <tr>
  	<td width="1%" style="vertical-align: top"><span lang=EN-US
  style='line-height:128%;font-size:10.5pt;font-family:宋体;'>7.<span
  style='font:7.0pt "Times New Roman"'>&nbsp; </span></span></td>
  	<td><b><span lang=EN-US
  style='font-size:11.5pt;line-height:128%;font-family:宋体'>本人授权贵行根据有关协议及规定对本人提供的个人信息及资料进行核查、使用和披露，包括但不限于为提供有关服务之目的， 将本人客户资料披露给贵行认为必需的第三方，包括但不限于贵行分支机构、关联机构、贵行的服务机构、增值产品（服务）提供商、代理人、外包作业机构、联名合作方以及相关资信机构。本人同意，贵行在进行其他产品和服务的销售时可使用本人信息及资料，法律法规另有规定的除外。本人理解并知晓本人作出该等授权的可能后果。<span
  style='font:7.0pt "Times New Roman"'>&nbsp; </span></span></b></td>
  </tr>
   <tr><td colspan="2"></td></tr> <tr><td colspan="2"></td></tr>
   
    <tr>
  	<td width="1%" style="vertical-align: top"><span lang=EN-US
  style='line-height:128%;font-size:10.5pt;font-family:宋体;'>8.<span
  style='font:7.0pt "Times New Roman"'>&nbsp; </span></span></td>
  	<td><span lang=EN-US
  style='font-size:10.5pt;line-height:128%;font-family:宋体'>客户了解并同意富邦华一银行为证实客户为美国纳税人身份，可于必要时向客户索取与此相关的证明文件，可代理客户向美国税法项下的扣缴义务人出示开户申请书或交付开户申请书副本，并可根据美国国外账户税收遵从法案等所适用的法律、法规、法令的有关规定向有权机关提供客户资料（包括但不限于账户名称、账号及账户余额等信息）。<span
  style='font:7.0pt "Times New Roman"'>&nbsp; </span></span></td>
  </tr>
   
   
  
  
  
   <tr><td colspan="2"></td></tr> <tr><td colspan="2"></td></tr>
 
   <tr>
  	<td width="1%" style="vertical-align: top"><span lang=EN-US
  style='line-height:128%;font-size:10.5pt;font-family:宋体;'>9.<span
  style='font:7.0pt "Times New Roman"'>&nbsp; </span></span></td>
  	<td><b><span lang=EN-US
  style='font-size:11pt;line-height:128%;font-family:宋体'>本人知晓作为纳税义务人的权利与义务，不偷税，不漏税，依法履行纳税义务。<span
  style='font:7.0pt "Times New Roman"'>&nbsp; </span></span></b></td>
  </tr>
  
  <tr><td colspan="2"></td></tr> <tr><td colspan="2"></td></tr>
 
   <tr>
  	<td width="1%" style="vertical-align: top"><span lang=EN-US
  style='line-height:128%;font-size:10.5pt;font-family:宋体;'>10.<span
  style='font:7.0pt "Times New Roman"'>&nbsp; </span></span></td>
  	<td><b><span lang=EN-US
  style='font-size:11pt;line-height:128%;font-family:宋体'>本人依法对本人在贵行的资产履行纳税义务。<span
  style='font:7.0pt "Times New Roman"'>&nbsp; </span></span></b></td>
  </tr>
  
  <tr>
  	<td width="1%" style="vertical-align: top"><span lang=EN-US
  style='line-height:128%;font-size:10.5pt;font-family:宋体;'>11.<span
  style='font:7.0pt "Times New Roman"'>&nbsp; </span></span></td>
  	<td><b><span lang=EN-US
  style='font-size:11pt;line-height:128%;font-family:宋体'>本人确认开户申请书中移动电话栏位所填写的联系号码为本人通过实名认证手机号码。<span
  style='font:7.0pt "Times New Roman"'>&nbsp; </span></span></b></td>
  </tr>
  
  <tr>
  	<td width="1%" style="vertical-align: top"><span lang=EN-US
  style='line-height:128%;font-size:10.5pt;font-family:宋体;'>12.<span
  style='font:7.0pt "Times New Roman"'>&nbsp; </span></span></td>
  	<td><b><span lang=EN-US
  style='font-size:11pt;line-height:128%;font-family:宋体'>如本人违反本声明书，贵行有权视具体情形采取下列措施：将本人账户信息报告国家有权机关；如经核实本人提供的身份信息严重失实，有权终止与本人的业务关系，拒绝提供服务；认为必要的其他措施。<span
  style='font:7.0pt "Times New Roman"'>&nbsp; </span></span></b></td>
  </tr>
  
  
  <tr>
  	<td width="1%" style="vertical-align: top"><span lang=EN-US
  style='line-height:128%;font-size:10.5pt;font-family:宋体;'>13.<span
  style='font:7.0pt "Times New Roman"'>&nbsp; </span></span></td>
  	<td><b><span lang=EN-US
  style='font-size:11pt;line-height:128%;font-family:宋体'>不论批准与否，本人同意此申请书及相关资料由富邦华一银行保留。<span
  style='font:7.0pt "Times New Roman"'>&nbsp; </span></span></b></td>
  </tr>
  
   <tr>
  	<td width="1%" style="vertical-align: top;padding-top:8px;" colspan="2"><span lang=EN-US
  style='line-height:128%;font-size:10.5pt;font-family:宋体;'>□本人已详细阅读上述内容并明确同意上述内容<span
  style='font:7.0pt "Times New Roman"'>&nbsp; </span></span></td>
  	
  </tr>
  
  </table>

  <p class=MsoNormalCxSpFirst style='line-height:128%;layout-grid-mode:char'><b>
  <span style='font-size:10.5pt;line-height:128%;font-family:宋体'>客户签章 
  <!-- （下列签章共<span
  lang=EN-US>&nbsp;&nbsp; </span>式凭<span lang=EN-US>&nbsp;&nbsp; </span>式有效） -->
  </span>
  </b></p>
  <p style='height:160pt'></p>
 <p>
   <span  style='font-size:10.5pt;line-height:128%;font-family:宋体;padding-left:3px;'>日期：&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;年&nbsp;&nbsp;&nbsp;&nbsp;月&nbsp;&nbsp;&nbsp;&nbsp;日</span>
 
 </p>
  </td>
  
 </tr>
 
 <!-- 
 <tr style='page-break-inside:avoid;height:240pt'>
 <td width="100%" >
 <span  style='font-size:10.5pt;line-height:128%;font-family:宋体;padding-left:330px;'>日期：&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;年&nbsp;&nbsp;&nbsp;&nbsp;月&nbsp;&nbsp;&nbsp;&nbsp;日</span>
 
 </td>
  <td colspan=6 valign=top style='width:35%;border:solid windowtext 1.0pt;
  border-top:none;background:transparent;padding:0cm 5.4pt 0cm 5.4pt;
  height:220pt'>
  <p class=MsoNormalCxSpMiddle style='line-height:12.0pt;layout-grid-mode:char'><span
  lang=EN-US style='font-size:10.5pt;font-family:宋体'>&nbsp;</span></p>
  </td>
  <td colspan=6 valign=top style='width:50%;border-top:none;
  border-left:none;border-bottom:solid windowtext 1.0pt;border-right:solid windowtext 1.0pt;
  background:transparent;padding:0cm 5.4pt 0cm 5.4pt;height:220pt'>
  <p class=MsoNormalCxSpMiddle style='line-height:12.0pt;layout-grid-mode:char'><span
  lang=EN-US style='font-size:10.5pt;font-family:宋体'>&nbsp;</span></p>
  </td> 
 </tr>
  -->
 
 <tr style='page-break-inside:avoid;height:36pt'>
  <td width=650 colspan=12 valign=top style='width:640pt;border:solid windowtext 1.0pt;
  border-top:none;background:transparent;padding:0cm 5.4pt 0cm 5.4pt;
  height:30.6pt'>
  <p class=MsoNormalCxSpLast style='line-height:9.0pt;layout-grid-mode:char;padding-top:3px;'><span
  style='font-size:11.0pt;font-family:宋体'>本人确认已领取<br/><br/></span></p>
  <p class=MsoListParagraphCxSpFirst style='margin-left:18.0pt;text-indent:
  -18.0pt;line-height:7pt;layout-grid-mode:char'><span lang=EN-US
  style='font-family:楷体'>□<span style='font:7.0pt "Times New Roman"'>&nbsp; </span></span><span
  style='font-size:11.0pt;font-family:宋体'>借记卡<span lang=EN-US>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
  </span>卡号：<span lang=EN-US>___________________________<br/><br/> </span></span></p>
  <p class=MsoListParagraphCxSpMiddle style='margin-left:18.0pt;text-indent:
  -18.0pt;line-height:7pt;layout-grid-mode:char'><span lang=EN-US
  style='font-family:楷体'>□<span style='font:7.0pt "Times New Roman"'>&nbsp; </span></span><span
  style='font-size:11.0pt;font-family:宋体'>网络/手机银行密码函<br/><br/></span></p>
   <p class=MsoListParagraphCxSpMiddle style='margin-left:18.0pt;text-indent:
  -18.0pt;line-height:7pt;layout-grid-mode:char'><span lang=EN-US
  style='font-family:楷体'>□<span style='font:7.0pt "Times New Roman"'>&nbsp; </span></span><span
  style='font-size:11.0pt;font-family:宋体'>电话银行密码函<br/><br/></span></p>
   <p class=MsoListParagraphCxSpMiddle style='margin-left:18.0pt;text-indent:
  -18.0pt;line-height:7pt;layout-grid-mode:char'><span lang=EN-US
  style='font-family:楷体'>□<span style='font:7.0pt "Times New Roman"'>&nbsp; </span></span><span
  style='font-size:11.0pt;font-family:宋体'>U-Key</span></p>
  <p class=MsoListParagraphCxSpLast style='margin-left:18.0pt;text-indent:0cm;
  line-height:9pt;layout-grid-mode:char;margin-bottom:6pt;'><span lang=EN-US style='font-size:
  11.0pt;font-family:宋体'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
  </span><span style='font-size:11.0pt;font-family:宋体;'>领取人签署：<span lang=EN-US>___________________________ </span></span></p>
  </td>
 </tr>
 <!--<tr style='page-break-inside:avoid;height:36pt'>
 <td width=650 colspan=12 valign=top style='width:640pt;border:solid windowtext 1.0pt;
  border-top:none;background:transparent;padding:0cm 5.4pt 0cm 5.4pt;
  height:30.6pt'>
  <p class=MsoNormalCxSpLast style='line-height:9.0pt;layout-grid-mode:char;padding-top:3px;'><span
  style='font-size:11.0pt;font-family:宋体'>CRM已完成补录</span></p>
  <p class=MsoListParagraphCxSpFirst style='margin-left:18.0pt;text-indent:
  -18.0pt;line-height:7pt;layout-grid-mode:char'><span lang=EN-US
  style='font-family:楷体'>1<span style='font:7.0pt "Times New Roman"'>&nbsp; </span></span><span
  style='font-size:11.0pt;font-family:宋体'>客户姓名拼音<span lang=EN-US>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
  </span></span></p>
  <p class=MsoListParagraphCxSpMiddle style='margin-left:18.0pt;text-indent:
  -18.0pt;line-height:7pt;layout-grid-mode:char'><span lang=EN-US
  style='font-family:楷体'>2<span style='font:7.0pt "Times New Roman"'>&nbsp; </span></span><span
  style='font-size:11.0pt;font-family:宋体'>客户性别</span></p>
   <p class=MsoListParagraphCxSpMiddle style='margin-left:18.0pt;text-indent:
  -18.0pt;line-height:7pt;layout-grid-mode:char'><span lang=EN-US
  style='font-family:楷体'>3<span style='font:7.0pt "Times New Roman"'>&nbsp; </span></span><span
  style='font-size:11.0pt;font-family:宋体'>客户出生日期</span></p>
   <p class=MsoListParagraphCxSpMiddle style='margin-left:18.0pt;text-indent:
  -18.0pt;line-height:7pt;layout-grid-mode:char'><span lang=EN-US
  style='font-family:楷体'>4<span style='font:7.0pt "Times New Roman"'>&nbsp; </span></span><span
  style='font-size:11.0pt;font-family:宋体'>证件有效期</span></p>
  <p class=MsoListParagraphCxSpLast style='margin-left:18.0pt;text-indent:0cm;
  line-height:9pt;layout-grid-mode:char;margin-bottom:6pt;'></p>
  </td>
 
 </tr> -->
</table>

<div style="align:center;width:640pt;padding-top:4px;">
<p class=MsoNormal align=left style='text-align:left'><span
style='font-size:11.0pt;font-family:宋体'>业务经办：</span><span lang=EN-US
style='font-size:11.0pt'>&nbsp;&nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span><span
style='font-size:11.0pt;font-family:宋体'>作业经办：</span><span lang=EN-US
style='font-size:11.0pt'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span><span
style='font-size:11.0pt;font-family:宋体'>作业主管：</span><span lang=EN-US
style='font-size:11.0pt'>&nbsp;&nbsp;&nbsp; </span></p>
</div>
				</div>
	<div style="text-align:center" id='PrintButton1' >
		<br/>
		<object ID='WebBrowser1' WIDTH=0 HEIGHT=0 border=1  style="display:none" CLASSID='CLSID:8856F961-340A-11D0-A96B-00C04FD705A2' > </object>
		<input type=button value='确认打印' onclick="WebBrowser1.ExecWB(6,1)">
		<input type=button value='打印预览' onclick="WebBrowser1.ExecWB(7,1)">
		<input type=button value='打印设置' onclick="WebBrowser1.ExecWB(8,1)">
		<input type=button value='关闭打印页面' onclick="WebBrowser1.ExecWB(45,1)">
	</div>
	<div class="footer" style="width:100%;height:4em;">
		<div style="width:100%;height:4em;">
			<iframe src="#" style="display:none;" id="iframe-print2"></iframe>
		</div>
	</div>

</div>
</body>
<script type="text/javascript">
	var hkey_root, hkey_path, hkey_key;
	hkey_root = "HKEY_CURRENT_USER";
	hkey_path = "\\Software\\Microsoft\\Internet Explorer\\PageSetup\\";
	//设置网页打印的页眉页脚为空
	function pagesetup_null() {
		try {
			var RegWsh = new ActiveXObject("WScript.Shell");
			hkey_key = "header";
			RegWsh.RegWrite(hkey_root + hkey_path + hkey_key, "");
		} catch (e) {
		}
	}
	//设置网页打印的页眉页脚为默认值
	function pagesetup_default() {
		try {
		 	var RegWsh = new ActiveXObject("WScript.Shell");
			hkey_key = "footer";
			RegWsh.RegWrite(hkey_root + hkey_path + hkey_key, "&w&b页码，&p/&P");
		} catch (e) {
		}
	}
	function beforePrint(){
		pagesetup_null();
		document.all('PrintButton').style.display='none';
		document.all('PrintButton1').style.display='none';
	}
		
	function afterPrint(){
		pagesetup_default();
		document.all('PrintButton').style.display="";
		document.all('PrintButton1').style.display="";
	}
	function goBack(){
		self.close();
	}
</script>
</html>