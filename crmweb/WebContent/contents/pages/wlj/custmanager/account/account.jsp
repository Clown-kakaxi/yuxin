<%@ page contentType="text/html; charset=utf-8"%>
<%@ include file="/contents/pages/common/includes.jsp"%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<script type="text/javascript" src="/crmweb/jQuery/js/jquery-1.11.3.min.js"></script>
<style type="text/css">
.content {
	width: 100%;
}

.header {
	height: 40px;
}

.body {
	margin: 0;
	padding: 0;
	width: 700px;
	margin: 0 auto;
	border: 1px black solid;
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

.title-row {
	width: 100%;
	height: 30px;
	background-color: #2faec7;
	font-size: 1.5em;
	font-family: 楷体；
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
	height: 16em;
}

.content-row-pro6 {
	width: 100%;
	height: 18em;
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
var accountCustInfo;
$(function(){
	initBtnListener();
	loadCustAgreementHtml();

	setTimeout(function(){
		
		alert("setTimeout");
		if(accountCustInfo){
			alert("accountCustInfo is 1");
			return;
		}else{
			if(window.opener){
				alert("window.opener is 1");
				if(window.opener.getAccountInfo){
					alert("window.opener.getAccountInfo is 1");
					accountCustInfo = window.opener.getAccountInfo();
				}
			}
		}
	}, 1500);

});

//添加用户协议word转换后的html
function loadCustAgreementHtml(){

	if($("#li-agreement").length > 0){
		$.ajax({
			url : basepath + '/oneKeyAccountCustAgreementAction!convertWord2Html.json',
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
				//
				if(result.msg){
					//console.log(result.msg);
					var wordHtml = result.msg;
					if(wordHtml.indexOf("<style") > -1 && wordHtml.indexOf("</style>") > -1){
						
						var styleHtml = wordHtml.substring(wordHtml.indexOf("<style"), wordHtml.indexOf("</style>")+8);
						$("head").append(styleHtml);
						var bodyHtml = wordHtml.substring(wordHtml.indexOf("<body"), wordHtml.indexOf("</body>")+7);
						$("#li-agreement").append(bodyHtml);
						//调整宽度
						$("#li-agreement").height();
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


//添加按钮监听
function initBtnListener(){
	//头部打印按钮监听
	if($("#btn-printAgreement").length > 0){
		$("#btn-printAgreement").on("click", function(){
			//iframe-print
			if($("#custContent").length > 0){
				
				//获取需要打印的网页内容
				var printContent = $("#custContent")[0].innerHTML;
				//将需要打印的内容添加到iframe中
				window.document.body.innerHTML = printContent;
				//设置打印参数
				var regwsh = new ActiveXObject("WScript.Shell");
				console.log(regwsh);
				//打印
				window.print();
			}
		});
	}
}
</script>
</head>
<body>
	<div class="header">
		<div class="d-headbtn">
			<input type="button" id="btn-printAgreement" style="width:150px;height:45px;padding-left:100px;" name="" value="打印">
		</div>
	</div>
	<div id="custContent" class="content">
		<div class="body">
			<div class="document">
				<ul>
					<li class="title-row"><b>客户信息</b></li>
					<li class="content-row">
						<div class="customer-detail" style="border-left-width: 0;">姓名：</div>
						<div class="customer-detail">姓名拼音：</div>
						<div class="customer-detail">
							性别：&nbsp;&nbsp;&nbsp;&nbsp;<input type="radio" name="sex"
								id="sex" value="男">男&nbsp;&nbsp;&nbsp;&nbsp; <input
								type="radio" name="sex" id="sex" value="女">女
						</div>
					</li>
					<li class="content-row">
						<div class="customer-detail" style="border-left-width: 0;">出生日期：</div>
						<div class="customer-detail">国籍：</div>
						<div class="customer-detail">出生地：</div>
					</li>
					<li class="content-row">
						<div class="customer-detail" style="border-left-width: 0;">证件种类：</div>
						<div class="customer-detail">证件号：</div>
						<div class="customer-detail">证件有效期：</div>
					</li>
					<li class="content-row">台胞证/港澳通行证号：</li>
					<li class="content-row">
						<div style="width: 50%; float: left;">居住地址：</div>
						<div style="width: 20%; float: left;">
							<input type="radio" name="adress" id="adress" value="租赁">租赁&nbsp;&nbsp;&nbsp;&nbsp;
							<input type="radio" name="adress" id="adress" value="自有">自有
						</div>
						<div
							style="width: 29%; float: left; border-left: 1px black solid;">居住地邮编：</div>
					</li>
					<li class="content-row">
						<div style="width: 70%; float: left;">邮寄地址：</div>
						<div
							style="width: 29%; float: left; border-left: 1px black solid;">邮寄邮编：</div>
					</li>
					<li class="content-row">移动电话：
						<div style="width: 50%; float: right;">（此电话将用于接收短信验证码和账务变动通知）</div>
					</li>
					<li class="content-row-pro1"><b>职业资料</b><br /> <input
						type="radio" name="adress" id="adress" value="全日制雇员">全日制雇员&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						<input type="radio" name="adress" id="adress" value="自顾">自顾&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						<input type="radio" name="adress" id="adress" value="退休">退休&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						<input type="radio" name="adress" id="adress" value="其他">其他（请具体注明）___________________________________________<br />
						<div style="width: 50%; float: left;">单位名称：</div>
						<div style="width: 50%; float: left;">职位：</div></li>
					<li class="content-row">电子邮箱E-mail：</li>
					<li class="content-row">是否在我行有关联人：</li>
					<li class="content-row">是否为美国纳税人：</li>
					<li class="title-row"><span class="title"><b>A账户信息</b></span></li>
					<li class="content-row-pro2">
						<div style="width: 50%; float: left;">
							<b>境内客户：</b><br /> <input type="radio" name="jingnei"
								id="jingnei" value="一般综合账户">一般综合账户<br /> <input
								type="radio" name="jingnei" id="jingnei" value="外汇结算户">外汇结算户<br />
							<input type="radio" name="jingnei" id="jingnei" value="外汇资本金户">外汇资本金户<br />
							<input type="radio" name="jingnei" id="jingnei"
								value="外汇境内资产变现账户">外汇境内资产变现账户
						</div>
						<div
							style="width: 49%; float: left; border-left: 1px black solid;">
							<b>境外客户：</b><br /> <input type="radio" name="jingwai"
								id="jingwai" value="一般综合账户">一般综合账户<br /> <input
								type="radio" name="jingwai" id="jingwai" value="外汇结算户">外汇结算户<br />
							<input type="radio" name="jingwai" id="jingwai" value="外汇资本金户">外汇资本金户<br />
							<input type="radio" name="jingwai" id="jingwai"
								value="外汇境内资产变现账户">港澳居民人民币储蓄账户<br /> <input
								type="radio" name="jingwai" id="jingwai" value="外汇境内资产变现账户">人民币前期费用专用结算账户<br />
							<input type="radio" name="jingwai" id="jingwai"
								value="外汇境内资产变现账户">人民币再投资专用结算账户
						</div>

					</li>
					<li class="title-row"><span class="title"><b>B服务信息</b></span></li>

					<li class="content-row-pro5">
						<div>
							<input type="checkbox" name="bservice" id="bservice"
								value="借记卡申请"><b>借记卡申请</b><br />
						</div>
						<div>
							基础卡片&nbsp;&nbsp; <input type="radio" name="jichu" id="jichu"
								value="基础卡片">金卡 （<input type="radio" name="jinka"
								id="jinka" value="普通卡">普通卡&nbsp; <input type="radio"
								name="jinka" id="jingwai" value="定制姓名卡">定制姓名卡）
							&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
							&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <input type="radio" name="jichu"
								id="jichu" value="白金卡">白金卡 （<input type="radio"
								name="baijinka" id="baijinka" value="普通卡">普通卡&nbsp; <input
								type="radio" name="baijinka" id="baijingwai" value="定制姓名卡">定制姓名卡）<br />
							&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
							<input type="radio" name="jichu" id="jichu" value="钻石卡">钻石卡
							(<input type="radio" name="baijinka" id="baijinka" value="普通卡">普通卡&nbsp;
							<input type="radio" name="baijinka" id="baijingwai" value="定制姓名卡">定制姓名卡）
						</div>
						<div>
							特色卡片&nbsp;&nbsp; <input type="radio" name="jichu" id="jichu"
								value="数位生活卡">数位生活卡 （<input type="radio" name="shuwei"
								id="shuwei" value="粉蓝">粉蓝&nbsp; <input type="radio"
								name="shuwei" id="shuwei" value="粉红">粉红）
						</div>
						<div>
							ATM转账限额设置&nbsp;&nbsp; <input type="radio" name="jichu" id="jichu"
								value="数位生活卡">默认每日累计限额(RMB50,000元)&nbsp;&nbsp; <input
								type="radio" name="jichu" id="jichu" value="数位生活卡">每日累计转账最高限额RMB________元
							(0-50,000元)<br />
							&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
							&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
							<input type="radio" name="jichu" id="jichu" value="数位生活卡">默认每日累计笔数(10笔)
							&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <input
								type="radio" name="jichu" id="jichu" value="数位生活卡">每日累计转账笔数___________笔<br />
							&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
							&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
							<input type="radio" name="jichu" id="jichu" value="数位生活卡">默认每年累计限额(RMB10,000,000元)&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
							<input type="radio" name="jichu" id="jichu" value="数位生活卡">每年累计转账最高限额RMB___________元<br />
						</div>
						<div>
							POS消费限额设置&nbsp;&nbsp;&nbsp; <input type="radio" name="shuwei"
								id="shuwei" value="粉蓝">默认单笔限额(RMB500,000元)&nbsp;&nbsp;&nbsp;&nbsp;
							<input type="radio" name="shuwei" id="shuwei" value="粉红">单笔消费限额RMB________元
						</div>
					</li>




					<div class="content-row-pro2">
						<div
							style="width: calc(20% - 1px); height: calc(100% - 1px); border-bottom: 1px solid black; border-right: 1px black solid; float: left;">
							<br />
							<br />
							<br />
							<br />
							<br /> &nbsp;<input type="checkbox" name="bservice" value="借记卡申请" /><b>电子银行服务</b>
						</div>

						<div style="width: 80%; height: 100%; float: right">
							<div
								style="width: 100%; height: calc(58% - 1px); border-bottom: 1px solid black;">
								<div
									style="width: 100%; height: calc(50% - 1px); border-bottom: 1px solid black">
									<div
										style="width: calc(20% - 1px); height: 100%; border-right: 1px black solid; float: left;">
										<br />
										<input type="radio" name="dianzi" id="dianzi" value="网络银行" />网络银行
									</div>
									<div style="width: 80%; float: right;">
										<div
											style="width: 100%; height: 49%; border-bottom: 1px solid black;">
											<input type="radio" name="dianzi" id="dianzi" value="U-key认证" />U-key认证
										</div>
										<div
											style="width: 100%; height: calc(51% - 1px); border-bottom: 1px solid black;">
											<input type="radio" name="dianzi" id="dianzi" value="短信验证" />短信验证
										</div>
									</div>
								</div>


								<div style="width: 100%; height: 50%;">
									<div
										style="width: 100%; height: calc(50% - 1px); border-bottom: 1px solid black;">
										<input type="radio" name="dianzi" id="dianzi" value="电话银行" />电话银行（仅限查询功能，约定汇款需另行填单）
									</div>
									<div style="width: 100%; height: 50%;">
										<div
											style="width: calc(20% - 1px); height: 100%; border-right: 1px black solid; float: left">
											<input type="radio" name="dianzi" id="dianzi" value="手机银行" />手机银行
										</div>
										<div style="width: 80%; height: 100%; float: left">
											<input type="radio" name="dianzi" id="dianzi" value="短信验证" />短信验证
										</div>
									</div>
								</div>



							</div>


							<div
								style="width: 100%; height: calc(42% - 1px); border-bottom: 1px solid black;">
								&nbsp;每日累计转账限额&nbsp;&nbsp;&nbsp;&nbsp;<input type="radio"
									name="adress" id="adress" value="默认无限制">默认无限制
								&nbsp;&nbsp;&nbsp;&nbsp;<input type="radio" name="adress"
									id="adress" value="自定义">自定义______元<br />
								&nbsp;每日累计转账笔数&nbsp;&nbsp;&nbsp;&nbsp;<input type="radio"
									name="adress" id="adress" value="默认200笔">默认200笔
								&nbsp;&nbsp;&nbsp;&nbsp; <input type="radio" name="adress"
									id="adress" value="自定义">自定义______笔<br />
								&nbsp;每年累计转账限额&nbsp;&nbsp;&nbsp;&nbsp;<input type="radio"
									name="adress" id="adress" value="默认无限制">默认无限制
								&nbsp;&nbsp;&nbsp;&nbsp;<input type="radio" name="adress"
									id="adress" value="自定义">自定义______元
							</div>
						</div>


					</div>
					<div
						style="width: 100%; height: 2em; border-bottom: 1px solid black;">
						&nbsp;<input type="checkbox" name="adress" id="adress"
							value="电子对账单"><b>电子对账单 E-mail：</b>&nbsp; <input
							type="radio" name="adress" id="adress" value="同邮件地址"><b>同邮件地址
						</b>
					</div>
					<div
						style="width: 100%; height: 2em; border-bottom: 1px solid black;">
						&nbsp;<input type="checkbox" name="adress" id="adress"
							value="账务变动通知 "><b>账务变动通知</b>
					</div>

					<!-- <li class="title-row"><span class="title"><b>富邦华一银行个人开户及综合服务协议</b></span></li> -->
					<!--用户协议部分-->
					<li id="li-agreement">

					</li>
					

				</ul>
			</div>
		</div>


	</div>
	<div class="footer" style="width:100%;height:4em;">
		<div style="width:100%;height:4em;">
			<iframe src="#" style="display:none;" id="iframe-print"></iframe>
		</div>
	</div>
</body>
</html>