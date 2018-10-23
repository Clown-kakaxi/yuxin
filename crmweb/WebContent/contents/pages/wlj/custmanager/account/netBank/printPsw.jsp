<%@ page contentType="text/html; charset=utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<!-- <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"> -->
<title>打印密码函</title>
<script type="text/javascript" src="/crmweb/jQuery/js/jquery-1.11.3.min.js"></script>
<script type="text/javascript">
	
	window.onload = function(){
		checkPrint();
	}
	
/**
 * 获取网银打印密码函所需的数据
 */
function getNetBankData(){
	var retData = null;
	if(window.parent){
		if(window.parent.crmData && window.parent.crmData.netBankData
				&& window.parent.crmData.netBankData.coreNo
				&& window.parent.crmData.netBankData.psw){
			
			retData = {
				'coreNo' : window.parent.crmData.netBankData.coreNo,//核心客户号
				'custNm' : window.parent.crmData.netBankData.custNm,//客户姓名
				'identNo' : window.parent.crmData.netBankData.identNo,//证件号码
				'psw' : window.parent.crmData.netBankData.psw,//密码
				'controlUser' : window.parent.crmData.netBankData.controlUser,
				'handleUser' : window.parent.crmData.netBankData.handleUser,
				'checkUser' : window.parent.crmData.netBankData.checkUser
			};
		}
	}
	return retData;
}


function checkPrint() {
	var netBankData = getNetBankData();
	if(!netBankData || !netBankData.coreNo || !netBankData.psw){
		alert("没有获取到核心客户号或者密码，无法打印密码函");
		return;
	}
	var d = new Date();
    var year = d.getFullYear();
    var month = d.getMonth()+1;
    var date = d.getDate();
    var hour = d.getHours();
    var minute  = d.getMinutes();
    var second = d.getSeconds();
		var nowTime=year+"-"+month+"-"+date+"  "+hour+":"+minute+":"+second;
	

		var coNo= netBankData.identNo;//'500102222112211';
		var endNo;
		if(coNo.length > 4){
			coNo = coNo.substring(0,coNo.length-4)+"****    ";
		}else{
			coNo = "****    ";
		}
	
	
		endNo = coNo + netBankData.custNm;
		document.DCodePrinter1.F1Left = 4000;
		document.DCodePrinter1.F1Top = 7000;
		//个人是证件号，企业是组织机构代码
		document.DCodePrinter1.Field1 ="申办日期：" +nowTime ;
		
		document.DCodePrinter1.F2Left = 4000;
		document.DCodePrinter1.F2Top = 2000;
		document.DCodePrinter1.Field2 = endNo;
		
		document.DCodePrinter1.F3Left = 4000;
		document.DCodePrinter1.F3Top = 4000;
		document.DCodePrinter1.Field3 = "证件号码：" + coNo ;

		document.DCodePrinter1.F4Left = 4000;
		document.DCodePrinter1.F4Top = 5000;
		document.DCodePrinter1.Field4 = "    密码："+ netBankData.psw;//'${PassWord}' ;
		//"操作员号：" + netBankData.controlUser;//"ADMIN" ;
		
		// document.DCodePrinter1.F5Left = 10000;
		// document.DCodePrinter1.F5Top = 5000;
		// document.DCodePrinter1.Field5 = "  密码："+ netBankData.psw;//'${PassWord}' ;
		
		document.DCodePrinter1.F6Left = 4000;
		document.DCodePrinter1.F6Top = 6000;
		document.DCodePrinter1.Field6 = "  经办员："  + netBankData.handleUser.replace(/N/ ,"");//'${_USER.userId}';
		
		document.DCodePrinter1.F7Left = 10000;
		document.DCodePrinter1.F7Top = 6000;
		document.DCodePrinter1.Field7 = "审核员：" + netBankData.checkUser.replace(/N/ ,"");//'${AuthLoginId}';

		document.DCodePrinter1.F8Left = 10000;
		document.DCodePrinter1.F8Top = 2000;
		document.DCodePrinter1.Field8 =  '核心客户号';//'${coreCifName}' ;
	
	document.DCodePrinter1.Msg = "是否打印企业操作员密码信封？";
	document.DCodePrinter1.PrintCode();
	$('#d_pswMail', window.parent.document).remove();//移除节点
	return;
}
</script>
</head>
<body>
<OBJECT id="DCodePrinter1" style="LEFT: 0px; VISIBILITY: hidden; TOP: 0px;WIDTH: 0px; HEIGHT: 0px" codeBase=cfca_printer.dll#VERSION=1,0,0,1 classid=clsid:EFB07BC6-BA22-45CB-88FD-3D969E006023>
<!--     <PARAM NAME="_Version" VALUE="65536">
    <PARAM NAME="_ExtentX" VALUE="2646">
    <PARAM NAME="_ExtentY" VALUE="1323">
    <PARAM NAME="_StockProps" VALUE="0"> -->
</OBJECT>  

<button value='打印密码函' name='打印密码函' onClick=checkPrint()>打印密码函</button>
</body>
</html>