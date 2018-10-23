<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page contentType="text/html;charset = utf-8" pageEncoding="utf-8"%>
<%@ include file="/contents/pages/common/includes.jsp"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>一键开户--开通借记卡</title>
<script type="text/javascript" src="/crmweb/jQuery/js/jquery-1.11.3.min.js"></script>
<script type="text/javascript" src="/crmweb/contents/pages/wlj/custmanager/account/ICCard/NtICCardReader.js"></script>
<style type="text/css">
	body{
		width:100%;
		height:100%;
		margin:10% 0 10% 0;
		padding:0;
		background-color: #eee;
	}
	.area-ICCard{
		/* border: 1px solid blue; */
	}
	.d-cardaccount{
		margin-top: 10%;
	}
	.row{
		width:100%;
		padding:5px 0px 5px 0px;
		margin:5px 0px 5px 0px;
	}
	.row div,input,button{
		float:left;
	}
	.ipt-show{
		width: 40%;
		margin-right:10px;
	}
	.s-label{
		width:150px;
	}
	.rowHead{
		width:20%;
		text-align: right;
		margin-right: 10px;
	}
</style>
<script type="text/javascript">
var comport = 2;//端口号
var bps = 9600;//波特率
var nTimeout = 5000;//超时时间(ms)
//卡信息
var ICCardNo = "";//卡号
var ICCardSeq = "";//卡序列号
var ICCardDs = "";//数据来源
var ICCardChan2 = "";//2磁道信息
var ICCard55 = "";//55域信息
var ICCardType;//

//密码键盘信息
var ICCardPsw1;//第一次密码
var ICCardPsw = "";//第二次密码
var validPsw = false;//两次输入的密码是否一致

//状态
var gotCardInfo;//是否已经获取卡信息

//返回信息
var openStatus = "error";
var openMsg = "取消开卡";


var cardTypeRelationship;
//  = {
// 	'55' : '1',//金卡
// 	'66' : '2',//白金卡
// 	'88' : '3',//钻石卡
// 	'10' : '0011',//数位卡粉
// 	'11' : '0012'//数位卡蓝

// };

//开户信息
var accountInfo = {};

$(function(){
	clearInfo();
	//IC卡具端口
	if(window.parent.crmData.IcPort){
		comport = parseInt(window.parent.crmData.IcPort, 10);
	}else{
		Ext.MessageBox.alert("提示", "没有默认的端口，请自行设置");
		$("#d_IcPort").show();
	}
	$("#readCardInfo").on("click", GetICCard);
	$("#inputPsw1").on("click", function(){
		
		ReadAnsiX98Web(comport,0,nTimeout);
	});
	$("#inputPsw2").on("click", function(){
		
		ReadAnsiX98Web(comport,1,nTimeout);
	});
	$("#confirm").on("click", queryICCard);
	$("#cancel").on("click", cancelAccount);
	window.onunload = function(){
		cancel();
	}
	getCustAccountInfo();
});
 
/**
 * [getCustAccountInfo 从客户信息页面获取IC卡开卡所需的信息]
 * @return {[type]} [description]
 */
function getCustAccountInfo(){
	if(window.parent){
		if(window.parent.getICCardAccountInfo && typeof window.parent.getICCardAccountInfo == 'function'){
			var openCardInfo = window.parent.getICCardAccountInfo();
			if(openCardInfo){
				if(openCardInfo.coreAccNo){//有核心客户账号
					accountInfo.coreNo = openCardInfo.coreAccNo;
					if(openCardInfo.cardType){//有卡类型
						ICCardType = openCardInfo.cardType;
					}else{
						openStatus = "error";
						openMsg = "没有获取到选择的卡片类型，请重新选择";
						cancel();
					}
				}else{
					openStatus = "error";
					openMsg = "没有获取到核心客户账号，无法开卡";
					cancel();
				}
			}else{
				Ext.MessageBox.alert('错误',"没有获取到开卡需要的信息，无法开卡");
				cancel();
			}
		}
		if(window.parent.crmData.cardTypeValid){
			cardTypeRelationship = window.parent.crmData.cardTypeValid;
		}else{
			Ext.Msg.confirm('没有获取到卡片类型校验规则,是否继续开卡?',endMsg,function(btn){
				if(btn == 'yes'){
					//继续开卡
				}else{
					cancel();
				}
			},this);
		}
	}
}


function clearInfo(){
	ICCardNo = "";//卡号
	ICCardSeq = "";//卡序列号
 	ICCardDs = "";//数据来源
 	ICCardChan2 = "";//2磁道信息
 	ICCard55 = "";//55域信息
	ICCardType;//
	document.getElementById("ipt-readCardInfo").value = "";
 	document.getElementById("ipt-inputPsw1").value = "";
	document.getElementById("ipt-inputPsw2").value = "";
}


/**
 * [setComport 设置端口号]
 */
function setComport(sel){
	comport = sel.options[sel.selectedIndex].value;
	AbortReadCard();
	CancelBpWeb();
	comport = sel.options[sel.selectedIndex].value;
}


function validCardType(cardNo){
	if(cardNo && cardNo.length >= 8 && ICCardType){
		var cardType = cardNo.substring(6, 8);
		if(cardType){
			var relCardNo = cardTypeRelationship[ICCardType];
			if(relCardNo){
				if(relCardNo == cardType){
					return;
				}else{
					ICCardNo = "";
					document.getElementById("ipt-readCardInfo").value = ICCardNo;
					Ext.Msg.alert("提示", "当前卡片与选择的卡片类型不一致，请重试");
					return;
				}
			}else{
				ICCardNo = "";
				document.getElementById("ipt-readCardInfo").value = ICCardNo;
				Ext.Msg.alert("提示", "没有选择的卡类型对应的卡号，请重试");
				return;
			}
		}else{
			ICCardNo = "";
			document.getElementById("ipt-readCardInfo").value = ICCardNo;
			Ext.Msg.alert("提示", "无法根据卡号截取卡类型编号，请联系管理员");
			return;
		}
	}
}


/**
 * [GetICCard 获取IC卡信息]
 */
function GetICCard() {
	//清空所有信息
	document.getElementById("ipt-readCardInfo").value = "";
	document.getElementById("ipt-inputPsw1").value = "";
	document.getElementById("ipt-inputPsw2").value = "";

	var myMask = new Ext.LoadMask(Ext.getBody(), {msg:"正在读取卡号，请稍等..."});
	myMask.show();
	document.getElementById("ipt-readCardInfo").value = "";
	CancelBpWeb();
	var m_ocx = document.getElementById("iccard");
	Nt_GetICCardInfo(m_ocx,3,"AEJ","A000000333010101|A000000333010102",""+comport,""+bps,"B");
	var ret= GetReturnValue();
	if(ret){
		if(ret == 0){
			// alert("打开读卡器成功");
		}else{
			myMask.hide();
			Ext.MessageBox.alert("提示", "打开读卡器失败");
			return;
		}
	}else{
		Ext.MessageBox.alert("提示", "打开读卡器失败");
		myMask.hide();
		return;
	}

	var Ictype = GetIctype();
	ICCardDs = Ictype;
	var cardInfo = GetvalueRet();
	if(cardInfo){
		cardInfo = cardInfo.substring(1, cardInfo.length);//去掉A
		var cardNoLen = cardInfo.substring(0, 3);
		cardInfo = cardInfo.substring(3, cardInfo.length);//去掉卡号长度
		var cardNo = cardInfo.substring(0, parseInt(cardNoLen, 10));//截取卡号
		ICCardNo = cardNo;
		//alert(ICCardNo);
		cardInfo = cardInfo.substring(parseInt(cardNoLen, 10)+1, cardInfo.length);//去掉E
		ICCardChan2Len = cardInfo.substring(0, 3);
		cardInfo = cardInfo.substring(3, cardInfo.length);//去掉二磁道长度
		ICCardChan2 = cardInfo.substring(0, parseInt(ICCardChan2Len, 10));//截取二磁道
		//alert(ICCardChan2);
		cardInfo = cardInfo.substring(parseInt(ICCardChan2Len, 10)+1, cardInfo.length);//去掉J
		var ICCardSeqLen = cardInfo.substring(0, 3);
		cardInfo = cardInfo.substring(3, cardInfo.length);//去掉二磁道长度
		ICCardSeq = cardInfo.substring(0, parseInt(ICCardSeqLen, 10));//截取二磁道
		if(ICCardSeq.length >= 3){
			ICCardSeq = ICCardSeq.substring(1, 3);
		}
		//alert(ICCardSeq);
	}
	Nt_genARQC(m_ocx,3,"P012000000000000Q012000000000000R003156S00820130813T00201U006143837V015012310280000001","A000000333010101|A000000333010102",comport,bps,"B");
	ICCard55 = GetARQC();
	document.getElementById("ipt-readCardInfo").value = ICCardNo;
	gotCardInfo = true;
	myMask.hide();
	//校验卡片类型
	if(window.parent.crmData.cardTypeValid){
		validCardType(ICCardNo);
	}else{
		Ext.Msg.confirm('提示',"没有获取到卡类型校验规则，是否继续开卡?",function(btn){
			if(btn == 'yes'){
				return;
			}else{
				cancel();
			}
		},this);
	}
}



/*
读密码键盘操作。
short ReadPinpad(short Comport,short Baud, short Extport, short PinMsg, long Outtime)
short ReadPinpadExt(short Comport,short Baud, short Extport, short PinMsg, long Outtime)  事件方式版本
参数：
Comport       	设置PC串口端口号码。
Baud		通信波特率。支持9600/4800/2400/1200。
Extport		扩展盒操作口选择 对BP8902VB3-AS机型,必须选择4
PinMsg		语音及液晶密码键盘提示信息选择  0:提示"请输入密码" 1:提示"请再输入一次"

Outtime		超时时间
返回：
iReturnStatus	
4		密码输入成功
-4		驱动忙，后台还有线程在工作
-5		用户终止
-6		超时
-7		返回数据格式错误
-8		打开串口失败
-9		输入参数错误
cPin		输入密码成功后，返回输入的密码数据。
*/
function ReadPinWeb(Ext,Comport,Msg,Outtime){
	if(Msg == 0){
		document.getElementById("ipt-inputPsw1").value = "";
	}else{
		document.getElementById("ipt-inputPsw2").value = "";
	}
	AbortReadCard();
 	if(!ICCardNo){
 		Ext.MessageBox.alert("提示", "没有卡号，请先获取卡号再设置密码！！！");
 		return;
 	}
	AbortReadCard();
 	if(Ext==0){
 		BpStandWeb1.ReadPinpad(Comport,9600,4,Msg,Outtime);
 	}else{
 		BpStandWeb1.ReadPinpadExt(Comport,9600,4,Msg,Outtime);
 	}
 	var msg_psw = "";
 	switch(BpStandWeb1.iReturnStatus) {
 	case 0:
 		/*msg_psw="操作完成";
 		break;*/
 	case 4:
 		if(Msg == 0){
	 		ICCardPsw1 = BpStandWeb1.cPin;

 			// alert("第一次密码长度"+ICCardPsw1.length);
	 		// if(ICCardPsw1.length != 6){
	 		// 	alert("密码长度错误，请重新输入");
	 		// 	break;
	 		// }
 			document.getElementById("ipt-inputPsw1").value = "******";
 			ReadPinWeb(comport,1,nTimeout);
 			//alert("输入的密码是：【"+ICCardPsw1+"】");
 		}else if(Msg == 1){
	 		ICCardPsw = BpStandWeb1.cPin;

 			// alert("第一次密码长度"+ICCardPsw.length);
	 		// if(ICCardPsw.length != 6){
	 		// 	alert("密码长度错误，请重新输入");
	 		// 	break;
	 		// }
	 		// alert("第二次密码输入完成");
	 		document.getElementById("ipt-inputPsw2").value = "******";
 			//alert("输入的密码是：【"+ICCardPsw+"】");
	 		if(ICCardPsw1 != ICCardPsw){
	 			Ext.MessageBox.alert("提示", "两次输入密码不一致，请重新输入!!!");
 				document.getElementById("ipt-inputPsw1").value = "";
 				document.getElementById("ipt-inputPsw2").value = "";
				AbortReadCard();
	 			return;
	 		}else{
	 			validPsw = true;
	 		}
 		}
 		
 		break;
 		break;
	case -4:
		msg_psw="驱动忙，后台还有线程在工作";
		break;	
	case -5:
		msg_psw="用户终止";
		break;	
	case -6:
		msg_psw="超时";
		break;	
	case -7:
		msg_psw="返回数据格式错误";
		break;	
	case -8:
		msg_psw="打开串口失败";
		break;	
	case -9:
		msg_psw="输入参数错误";
		break;	
	default:
		msg_psw="其他错误";
		break;								 	
 	} 
 	if(BpStandWeb1.iReturnStatus != 0 || BpStandWeb1.iReturnStatus != 4){
 		Ext.MessageBox.alert("提示", msg_psw);
 	}
}



/*
ANSI X9.8格式输入密码
short KPReadAnsiX98Pin(short Comport, short Baud, short Extport, short Mkeyid, short Ukeyid, LPCTSTR AnsiX98AccNo, short PinMsg, long Outtime) 
参数：
Comport       	设置PC串口端口号码。
Baud		通信波特率。支持9600/4800/2400/1200。
Extport		扩展盒扩展口。0：无扩展盒，1：扩展口A 2：扩展口B 3：扩展口C 4：扩展口K
Mkeyid		主密钥编号
Ukeyid		工作密钥编号
AnsiX98AccNo	 ANSI X9.8 格式账号
PinMsg		语音及液晶密码键盘提示信息选择  0:提示"请输入密码" 1:提示"请再输入一次"
Outtime		超时时间
iReturnStatus	
4		密码输入成功
-4		驱动忙，后台还有线程在工作
-5		用户终止
-6		超时
-7		返回数据格式错误
-8		打开串口失败
-9		输入参数错误
cPin		输入密码成功后，返回输入的密码数据。
iPinlength	输入密码成功后，返回输入的密码位数。
*/
function ReadAnsiX98Web(Comport,Msg,Outtime){
	if(Msg == 0){
		document.getElementById("ipt-inputPsw1").value = "";
	}else{
		document.getElementById("ipt-inputPsw2").value = "";
	}
	AbortReadCard();
 	if(!ICCardNo){
 		Ext.MessageBox.alert("提示", "没有卡号，请先获取卡号再设置密码！！！");
 		return;
 	}
 	var AnsiX98AccNo=BpStandWeb1.KPFormatAnsiX98Account(ICCardNo);
 	//alert(AnsiX98AccNo	);
 	BpStandWeb1.KPReadAnsiX98Pin(Comport,9600,4,3,1,AnsiX98AccNo,Msg,Outtime);
 	// BpStandWeb1.KPReadAnsiX98Pin(Comport,9600,4,100,12,AnsiX98AccNo,Msg,Outtime);
 	var msg_psw2 = "";
 	switch(BpStandWeb1.iReturnStatus) {
 	case 0:
 	case 4:
 		//MessageCtrl.value="密码输入完成";
 		//PinSeqNoCtrl.value=BpStandWeb1.cSeqNo;
 		//PinAPPIDCtrl.value=BpStandWeb1.cAppID;
 		//PinCtrl.value=BpStandWeb1.cPin;
 		//KeyValueCtrl.value=BpStandWeb1.cKeyValue;
 		
 		if(Msg == 0){
	 		ICCardPsw1 = BpStandWeb1.cPin;

 			// alert("第一次密码长度"+ICCardPsw1.length);
	 		// if(ICCardPsw1.length != 6){
	 		// 	alert("密码长度错误，请重新输入");
	 		// 	break;
	 		// }
 			document.getElementById("ipt-inputPsw1").value = "******";
 			ReadAnsiX98Web(comport,1,nTimeout);
 			//alert("输入的密码是：【"+ICCardPsw1+"】");
 		}else if(Msg == 1){
	 		ICCardPsw = BpStandWeb1.cPin;

 			// alert("第一次密码长度"+ICCardPsw.length);
	 		// if(ICCardPsw.length != 6){
	 		// 	alert("密码长度错误，请重新输入");
	 		// 	break;
	 		// }
	 		// alert("第二次密码输入完成");
	 		document.getElementById("ipt-inputPsw2").value = "******";
 			//alert("输入的密码是：【"+ICCardPsw+"】");
	 		if(ICCardPsw1 != ICCardPsw){
	 			Ext.MessageBox.alert("提示", "两次输入密码不一致，请重新输入!!!");
 				document.getElementById("ipt-inputPsw1").value = "";
 				document.getElementById("ipt-inputPsw2").value = "";
				AbortReadCard();
	 			return;
	 		}else{
	 			validPsw = true;
	 		}
 		}
 		
 		break;
	case -4:
		msg_psw2="驱动忙，后台还有线程在工作";
		break;	
	case -5:
		msg_psw2="用户终止";
		break;	
	case -6:
		msg_psw2="超时";
		break;	
	case -7:
		msg_psw2="返回数据格式错误";
		break;	
	case -8:
		msg_psw2="打开串口失败";
		break;	
	case -9:
		msg_psw2="输入参数错误";
		break;	
	default:
		msg_psw2="其他错误";
		Ext.MessageBox.alert("提示", BpStandWeb1.iReturnStatus);
		break;								 	
 	} 
 	if(BpStandWeb1.iReturnStatus != 0 && BpStandWeb1.iReturnStatus != 4){
 		Ext.MessageBox.alert("提示", msg_psw2);
 	}
}

/**
 * [AbortReadCard 终止读卡器读取信息]
 */
function AbortReadCard(){
    var ocx = document.getElementById("iccard");
    ocx.AbortReadCard();
}
/*
中止后台读取进程。
EndBpThread 
用于中止ReadMagExt及ReadPinpadExt后台读取进程。
*/
function CancelBpWeb(){
 	BpStandWeb1.EndBpThread();
}

/**
 * [queryICCard 卡系统开卡]
 * @return {[type]} [description]
 */
function queryICCard(){
	var myMask;//
	if(!gotCardInfo){
		Ext.MessageBox.alert("提示", "获取卡信息失败，请重试");
		return;
	}
	if(!validPsw){
		Ext.MessageBox.alert("提示", "两次输入的密码不一致，请重试");
		return;
	}
	var reqData;
	//测试用数据
	/*reqData = {
			'AccountNo' : '50100001900003400',//账户信息   核心客户号
			'CardNo' : '6235655588800067397',//卡号
			'MainCardNo' : '6235655588800067397',//主卡号
			'Psw' : '7B6BCE21E3E0F007',//密码
			'SerialNo' : '00',//序列号
			'DataSource' : '1',//数据来源
			'SecondTrackInfo' : '6235655588800067397=26112200000070900',//二磁道信息
			'Unionpay55Info' : '9F26085E1725034E4B2DB99F2701809F101307800103A0A810010A0100000200004BF934639F37041BA185E49F360200F2950500000008009A031308139C01019F02060000000000005F2A02015682027C009F1A0201569F03060000000000009F330320A100'//银联55域
	};*/
	//实际运行环境获取数据
	reqData = {

			'AccountNo' : accountInfo.coreNo,//账户信息   核心客户号
			'CardNo' : ICCardNo,//卡号
			'MainCardNo' : ICCardNo,//主卡号
			'Psw' : ICCardPsw,//密码
			'SerialNo' : ICCardSeq,//序列号
			'DataSource' : ICCardDs,//数据来源
			'SecondTrackInfo' : ICCardChan2,//二磁道信息
			'Unionpay55Info' : ICCard55
	};
	window.parent.crmData.accountCardNo = reqData.CardNo;
	window.parent.crmData.ICCardInfo = reqData;
	//window.parent.openNormalCardAccount();
	
	openNormalCardAccount();
	/*
	$.ajax({
		url : '/crmweb/oneKeyAccountAction!invokeCardSysImpl.json',
		type : "post",
		dataType : "json",
		data : reqData,
		timeout : 30000,
		beforeSend : function(){
			window.parent.crmData.accountCardNo = reqData.CardNo;
			myMask = new Ext.LoadMask(Ext.getBody(), {msg:"正在请求卡系统开卡，请稍等..."});
			myMask.show();
		},
		success : function(response) {
			if(myMask){
				myMask.hide();
			}
			if(response && response.status && response.msg){
				openStatus = response.status;
				openMsg = response.msg;
			}
			 if(!response){
				alert("开卡响应信息为空");
				cancel();
				return;
				window.parent.crmData.normalCardMsg = "成功开卡";
				window.parent.crmData.normalCardStatus = "success";
			}
			if(response.status && response.status == 'success'){
				window.parent.crmData.normalCardMsg = "成功开卡";
				window.parent.crmData.normalCardStatus = "success";
				Ext.MessageBox.alert('成功开卡');
				cancel();
			}else if(response.status && response.status == 'failure'){
				Ext.MessageBox.alert("提示", response.msg);
				document.getElementById("ipt-inputPsw1").value = "";
				document.getElementById("ipt-inputPsw2").value = "";
			}else{
				if(response.msg){
					Ext.MessageBox.alert('开卡失败，具体原因：'+response.msg);
					cancel();
				}
			} 
	    },
	    complete : function(request, status){
			if(myMask){
				myMask.hide();
			}
	    	if(status && status == 'timeout'){//超时
		    	openStatus = 'error';
				openMsg = '请求超时，开卡失败...';
				cancel();
			}
	    }
	});
*/
}


//开通普通卡
function openNormalCardAccount(){
	if(!window.parent.crmData || !window.parent.crmData.ICCardInfo){
		Ext.alert("开通普通卡", "获取卡信息失败");
		return;
	}

	var myMask;
	$.ajax({
		url : '/crmweb/oneKeyAccountAction!invokeCardSysImpl.json',
		type : "GET",
		dataType : "json",
		data : window.parent.crmData.ICCardInfo,
		timeout : 120000,
		beforeSend : function(){
			myMask = new Ext.LoadMask(Ext.getBody(), {msg:"正在请求卡系统开卡，请稍等..."});
			myMask.show();
		},
		success : function(response) {
			if(myMask){
				myMask.hide();
			}
			
			if(response && response.status && response.msg){
				if(response.status == 'failure'){
					Ext.MessageBox.alert("提示",response.msg);
					document.getElementById("ipt-inputPsw1").value = "";
					document.getElementById("ipt-inputPsw2").value = "";
				}else{
					document.getElementById("ipt-readCardInfo").value = "";
					document.getElementById("ipt-inputPsw1").value = "";
					document.getElementById("ipt-inputPsw2").value = "";
					window.parent.openCardWin.hide();
					window.parent.adjustNormalCardOpen(response.status, response.msg);
				}
				
			}else{
				document.getElementById("ipt-readCardInfo").value = "";
				document.getElementById("ipt-inputPsw1").value = "";
				document.getElementById("ipt-inputPsw2").value = "";
				window.parent.openCardWin.hide();
				window.parent.adjustNormalCardOpen('error', '开卡失败，响应信息为空');
			}
	    },
	    complete : function(request, status){
			// if(myMask){
			// 	myMask.hide();
			// }
	    	if(status && status == 'timeout'){//超时
	    		document.getElementById("ipt-readCardInfo").value = "";
				document.getElementById("ipt-inputPsw1").value = "";
				document.getElementById("ipt-inputPsw2").value = "";
				window.parent.openCardWin.hide();
	    		window.parent.adjustNormalCardOpen('error', '请求超时，开卡失败...');
			}
	    }
	});

}

 
/**
 * 取消开卡
 */
function cancelAccount(){
	openStatus = "error";
	openMsg = "取消开卡";
	cancel();
}

/**
 * [cancel 关闭页面]
 * @return {[type]} [description]
 */
function cancel(){
	AbortReadCard();
	CancelBpWeb();
	window.parent.adjustNormalCardOpen(openStatus, openMsg);//
	document.getElementById("ipt-readCardInfo").value = "";
	document.getElementById("ipt-inputPsw1").value = "";
	document.getElementById("ipt-inputPsw2").value = "";
	if(window.parent.openCardWin){
		window.parent.openCardWin.hide();
		window.parent.openCardWin = null;
		// window.parent.openCardWin.hide();
	}
	// window.close();//
}
</script>
</head>
<body>
	<div id="d-cardaccount" style="width: 100%;height: 50%;">
		<object id="iccard" classid="clsid:6A102BA2-FB11-48EA-8587-D811AE4655D8" codebase="PBOC_Nantian.ocx#version=1,0,0,23" style="VISIBILITY: hidden;" hidden=true></object>
		<object classid="clsid:F46BE840-0D81-4FA5-9B69-4A8AED284849" id="BpStandWeb1" codebase="http://172.16.27.90/BpStandWeb.ocx#version=1,0,0,1" style="VISIBILITY: hidden;" hidden=true></object>
		<div class="row area-ICCard">
			<div id="d_IcPort" style="display:none" class="row">
				<div class="rowHead">设置端口:</div>
				<select style="width:150px;" onChange="setComport(this)">
					<option value="0">0</option>
					<option value="1">1</option>
					<option value="2">2</option>
					<option value="3">3</option>
					<option value="4">4</option>
					<option value="5">5</option>
					<option value="6">6</option>
					<option value="7">7</option>
					<option value="8">8</option>
					<option value="9">9</option>
				</select>
			</div>
			<div class="row">
				<div class="rowHead">卡号:</div>
				<input class="ipt-show" id="ipt-readCardInfo" disabled="disabled" readOnly="readOnly" type="text" name="">
				<button id="readCardInfo" value="读取卡号">读取卡号</button>
			</div>
			<div class="row">
				<div class="rowHead">请输入密码:</div>
				<input class="ipt-show" id="ipt-inputPsw1" disabled="disabled" readOnly="readOnly" type="text" name="">
				<button id="inputPsw1" value="输入密码">输入密码</button>
			</div>
			<div class="row">
				<div class="rowHead">请再次输入密码:</div>
				<input class="ipt-show" id="ipt-inputPsw2" disabled="disabled" readOnly="readOnly" type="text" name="">
				<button id="inputPsw2" value="输入密码">输入密码</button>
			</div>
			<div class="row">
				<div style="width:50%;">
					<button style="margin-left:80%;" id="confirm" value="确认">确认</button>
				</div>
				<div>
					<button id="cancel" value="取消">取消</button>
				</div>
			</div>
		</div>
	</div>
</body>
</html>