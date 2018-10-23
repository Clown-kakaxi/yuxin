<%@ page contentType="text/html; charset=utf-8"%>
<%@ include file="/contents/pages/common/includes.jsp"%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
	<script type="text/javascript" src="<%=request.getContextPath()%>/jQuery/js/jquery-1.11.3.min.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/contents/pages/com.yucheng.bcrm/com.yucheng.bcrm.js"></script>
	<style type="text/css">
		.content{
			width:100%;
		}
		.header{
			height:40px;
		}
		.body{
			margin:0;
			padding:0;
			width:700px;
			margin:0 auto;
			border:1px black solid;
		}
		.document{
			margin:0;
			padding:0;
			width:100%;
		}
		.document ul li{
			border-bottom:1px black solid;
		}
		.title-row{
			width:100%;
			height:30px;
			background-color:#2faec7;
			font-size:1.5em;
			font-family:楷体；
		}
		.content-row{
			width:100%;
			height:2em;
		}
		.content-row-pro1{
			width:100%;
			height:6em;
		}
		.content-row-pro2{
			width:100%;
			height:12em;
		}
		.content-row-pro3{
			width:100%;
			height:36em;
		}
		.content-row-pro4{
			width:100%;
		}
		.content-row-pro4 .xieyi{
			padding:20px 20px;
		}
		.text-title{
			text-align:center;
		}
		.customer-detail{
			width:33%;
			height:100%;
			float:left;
			border-left:1px black solid;
		}
	</style>
	
	<script type="text/javascript">
		function postData() {
/* 			$.ajax({
				url : basepath + '/oneKeyAccountAction!invokeCoreSysImpl.json',
				type : "post",
				dataType : "json",
				data : {
					remark : $("#data").val()
				},
				success : function() {
					
				}
			}); */
			
 			Ext.Ajax.request({
				url: basepath + '/oneKeyAccountAction!invokeCardSysImpl.json',
				method: 'get',
	            params: {},
	            success: function(response) {
	           		
	               Ext.Msg.alert('提示', response.responseText);
	            },
	            failure: function(response) {
	               Ext.Msg.alert('提示', '停用用户失败');
	            }
	        }); 
		}
		
		function cyberBank() {
 			Ext.Ajax.request({
				url: basepath + '/oneKeyAccountAction!invokeCyberBankSysImpl.json',
				method: 'POST',
	            params: {},
	            success: function(response) {
	               Ext.Msg.alert('提示', '停用用户成功');
	            },
	            failure: function(response) {
	               Ext.Msg.alert('提示', '停用用户失败');
	            }
	        }); 
		}
		
		//联网核查
		function networkCheck() {
 			Ext.Ajax.request({
				url: basepath + '/oneKeyAccountAction!invokeNetworkSysImpl.json',
				method: 'POST',
	            params: {},
	            success: function(response) {
	               Ext.Msg.alert('提示', response.responseText);
	            },
	            failure: function(response) {
	               Ext.Msg.alert('提示', '查询失败');
	            }
	        }); 
		}
		
		function coreSystem() {
 			Ext.Ajax.request({
				url: basepath + '/oneKeyAccountAction!invokeCoreSysImpl.json',
				method: 'POST',
	            params: {},
	            success: function(response) {
	               Ext.Msg.alert('提示', response.responseText);
	            },
	            failure: function(response) {
	               Ext.Msg.alert('提示', '查询失败');
	            }
	        }); 
		}


		/**
		 * [blackGrayOrderCheck 电信黑灰名单核查]
		 * @return {[type]} [description]
		 */
		function blackGrayOrderCheck(){
			Ext.Ajax.request({
				url: basepath + '/oneKeyAccountAction!blackGrayOrderCheckSysImpl.json',
				method: 'POST',
	            params: {
	            	'mdlx' : '200501',//名单类型
	            	'sjlx' : 'IDType_IDNumber',//数据类型
	            	'yhbh' : '*',//银行编号
	            	'sjx' : '01_210203198711072015',//数据项
	            	'zhm' : '叶鹏'//账户名
	            },
	            success: function(response) {
	               Ext.Msg.alert('提示', response.responseText);
	            },
	            failure: function(response) {
	               Ext.Msg.alert('提示', '查询失败');
	            }
	        });
		}

		/**
		 * [CRM2ECIFAccount CRM到ECIF开户]
		 */
		function CRM2ECIFAccount(){
			Ext.Ajax.request({
				url: basepath + '/oneKeyAccountAction!CRM2ECIFAccount.json',
				method: 'POST',
	            params: {
	            	'mdlx' : '200501',//名单类型
	            	'sjlx' : 'IDType_IDNumber',//数据类型
	            	'yhbh' : '*',//银行编号
	            	'sjx' : '01_210203198711072015',//数据项
	            	'zhm' : '叶鹏'//账户名
	            },
	            success: function(response) {
	               Ext.Msg.alert('提示', response.responseText);
	            },
	            failure: function(response) {
	               Ext.Msg.alert('提示', '查询失败');
	            }
	        });
		}


		/**
		 * [readICCard 读取ICCard信息]
		 * @return {[type]} [description]
		 */
		function readICCard(){
			window.open('/crmweb/contents/pages/wlj/custmanager/account/ICCard/ICCardAndPswKey.html');
		}


		/**
		 * [passWordBord 密码键盘]
		 * @return {[type]} [description]
		 */
		function passWordBord(){
			window.open('/crmweb/contents/pages/wlj/custmanager/account/ICCard/ICCardAndPswKey.html');
		}
	</script>
</head>
<body>
	<div class="content">
		<div class="header"></div>
		<div class="body">
			<div class="document">
				<ul>
					<li class="title-row">客户信息</li>
					<li class="content-row">
						<div class="customer-detail" style="border-left-width:0;">姓名：</div>
						<div class="customer-detail"><input type="button" value="卡系统测试" onclick="postData()"></div>
						<div class="customer-detail"><input type="button" value="网银系统测试" onclick="cyberBank()"></div>
					</li>
					<li class="content-row">
						<div class="customer-detail" style="border-left-width:0;"></div>
						<div class="customer-detail"><input type="button" value="联网核查系统测试" onclick="networkCheck()"></div>
						<div class="customer-detail"><input type="button" value="核心系统测试" onclick="coreSystem()"></div>
					</li>
					<li class="content-row">
						<div class="customer-detail" style="border-left-width:0;">
							<div class="customer-detail">
								<input type="button" value="电信黑灰名单系统测试" onclick="blackGrayOrderCheck()">
							</div>
							<div class="customer-detail">
								<input type="button" value="ECIF开户" onclick="CRM2ECIFAccount()">
							</div>
						</div>	
					</li>
					<li class="content-row">
						<div class="customer-detail" style="border-left-width:0;">
							<div class="customer-detail">
								<input type="button" value="IC卡测试" onclick="readICCard()">
							</div>
							<div class="customer-detail">
								<input type="button" value="密码键盘测试" onclick="passWordBord()">
							</div>
						</div>
					</li>
					<li class="content-row">
						<div style="width:70%;height:100%;float:left;"></div>
						<div style="width:29%;height:100%;float:left;border-left:1px black solid;"></div>
					</li>
					<li class="content-row">
						<div style="width:70%;height:100%;float:left;"></div>
						<div style="width:29%;height:100%;float:left;border-left:1px black solid;"></div>
					</li>
					<li class="content-row"></li>
					<li class="content-row-pro1"></li>
					<li class="content-row"></li>
					<li class="content-row"></li>
					<li class="content-row"></li>
					<li class="title-row"><span class="title">A账户信息</span></li>
					<li class="content-row-pro2">
						
					</li>
					<li class="title-row"><span class="title">B服务信息</span></li>
					<li class="content-row-pro3">
						
					</li>
					<li class="title-row"><span class="title">富邦华一银行个人开户及综合服务协议</span></li>
					<li class="content-row-pro4">
						<div class="xieyi">
							<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;客户（定义如下）兹向富邦华一银行（定义如下）申请开立个人账户，并依照客户之选择开通富邦华一银行提供的各项服务。
							为保证合法、规范使用个人账户，根据《人民币银行结算账户管理办法》及相关法律、法规，双方签订《富邦华一银行个人开户及综合服务协议书》（以下简称“本协议”），并于各适用服务的范围内，遵守以下条款：</p>
							<p class="text-title"><b>第一章	定义及解释</b></p>
							<p><b>第一条</b>	如无特别说明，下列用语在本协议中的含义为：</p>
						</div>
					</li>
				</ul>
			</div>
		</div>
		

	</div>
</body>
</html>