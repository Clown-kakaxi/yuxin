var HKEY_Root, HKEY_Path, HKEY_Key;
var HKEY_Root = "HKEY_CURRENT_USER";
var HKEY_Path = "\\Software\\Microsoft\\Internet Explorer\\PageSetup\\";
var head ="", foot ="", top ="0.75", bottom = "0.75", left = "0.75", righ= "0.75", Shrink_To_Fit = "yes";//设置默认值
// Shrink_To_Fit 缩小字体功能
/**
 * 取得页面打印设置的原参数数据
 */ 
function getPageSetupParamDefault() {
	try {
		var Wsh = new ActiveXObject("WScript.Shell");
		HKEY_Key = "header";
		// 取得页眉默认值
		head = Wsh.RegRead(HKEY_Root + HKEY_Path + HKEY_Key);
		HKEY_Key = "footer";
		// 取得页脚默认值
		foot = Wsh.RegRead(HKEY_Root + HKEY_Path + HKEY_Key);
		HKEY_Key = "margin_bottom";
		// 取得下页边距
		bottom = Wsh.RegRead(HKEY_Root + HKEY_Path + HKEY_Key);
		HKEY_Key = "margin_left";
		// 取得左页边距
		left = Wsh.RegRead(HKEY_Root + HKEY_Path + HKEY_Key);
		HKEY_Key = "margin_right";
		// 取得右页边距
		right = Wsh.RegRead(HKEY_Root + HKEY_Path + HKEY_Key);
		HKEY_Key = "margin_top";
		// 取得上页边距
		top = Wsh.RegRead(HKEY_Root + HKEY_Path + HKEY_Key);
		HKEY_Key = "Shrink_To_Fit";
		// 取得是否缩小字体
		Shrink_To_Fit = Wsh.RegRead(HKEY_Root + HKEY_Path + HKEY_Key);
		return 0;//0表示成功
	} catch (e) {
		//alert("ActiveX控件被禁用，请按打印帮助文档设置！");
		if(confirm("ActiveX控件被禁用，请按打印帮助文档设置!\n是否下载帮助文档?")){
			downloadHelpDoc();
		}
		return -1;//-1表示失败
	}
}

/**
 * 设置网页打印的页眉页脚和页边距
 */ 
function setPageSetupParamNew() {
	try {
		var Wsh = new ActiveXObject("WScript.Shell");
		HKEY_Key = "header";
		// 设置页眉（为空）
		Wsh.RegWrite(HKEY_Root + HKEY_Path + HKEY_Key, "");
		HKEY_Key = "footer";
		// 设置页脚（为空）
		Wsh.RegWrite(HKEY_Root + HKEY_Path + HKEY_Key, "");
		HKEY_Key = "margin_bottom";
		// 设置下页边距（0.5） 边距的1相当于25.4mm
		Wsh.RegWrite(HKEY_Root + HKEY_Path + HKEY_Key, "0.5");
		HKEY_Key = "margin_left";
		// 设置左页边距（0.25）
		Wsh.RegWrite(HKEY_Root + HKEY_Path + HKEY_Key, "0.25");
		HKEY_Key = "margin_right";
		// 设置右页边距（0.25）
		Wsh.RegWrite(HKEY_Root + HKEY_Path + HKEY_Key, "0.25");
		HKEY_Key = "margin_top";
		// 设置上页边距（0.5）
		Wsh.RegWrite(HKEY_Root + HKEY_Path + HKEY_Key, "0.5");
		HKEY_Key = "Shrink_To_Fit";
		// 设置缩小字体 为no
		Wsh.RegWrite(HKEY_Root + HKEY_Path + HKEY_Key, "no");
		return 0;//0表示成功
	} catch (e) {
		//alert("ActiveX控件被禁用，请按打印帮助文档设置！");
		if(confirm("ActiveX控件被禁用，请按打印帮助文档设置!\n是否下载帮助文档?")){
			downloadHelpDoc();
		}
		return -1;//-1表示失败
	}
}
/** 
 * 此方法暂时不能用，必须等打印结束才能调用
 * 将网页打印的页眉页脚和页边距  恢复设置为默认值
 */ 
function setPageSetupParamDefault() {
	try {
		var Wsh = new ActiveXObject("WScript.Shell");
		HKEY_Key = "header";
		// 还原页眉
		Wsh.RegWrite(HKEY_Root + HKEY_Path + HKEY_Key, head);
		HKEY_Key = "footer";
		// 还原页脚
		Wsh.RegWrite(HKEY_Root + HKEY_Path + HKEY_Key, foot);
		HKEY_Key = "margin_bottom";
		// 还原下页边距
		Wsh.RegWrite(HKEY_Root + HKEY_Path + HKEY_Key, bottom);
		HKEY_Key = "margin_left";
		// 还原左页边距
		Wsh.RegWrite(HKEY_Root + HKEY_Path + HKEY_Key, left);
		HKEY_Key = "margin_right";
		// 还原右页边距
		Wsh.RegWrite(HKEY_Root + HKEY_Path + HKEY_Key, right);
		HKEY_Key = "margin_top";
		// 还原上页边距
		Wsh.RegWrite(HKEY_Root + HKEY_Path + HKEY_Key, top);
		HKEY_Key = "Shrink_To_Fit";
		// 还原缩小字体为yes
		Wsh.RegWrite(HKEY_Root + HKEY_Path + HKEY_Key, Shrink_To_Fit);
	} catch (e) {
		//alert("ActiveX控件被禁用，请按打印帮助文档设置！");
		if(confirm("ActiveX控件被禁用，请按打印帮助文档设置!\n是否下载帮助文档?")){
			downloadHelpDoc();
		}
		return -1;//-1表示失败
	}
}

///**
// * 打印页面
// * @param frameId
// */
//function printPage(frameId) {
//	getPageSetupParamDefault();// 取得默认打印参数
//	setPageSetupParamNew();// 设置页面打印参数
//	frameId.execwb(6, 6);// 打印页面
//	setPageSetupParamDefault();// 还原页面打印参数
//}
//
///**
// * 打印预览
// */
//function printView(frameId) {
//	getPageSetupParamDefault();// 取得默认打印参数
//	setPageSetupParamNew();// 设置页面打印参数
//	frameId.execwb(7, 1);// 打印预览
//	setPageSetupParamDefault();// 还原页面打印参数
//}

/**
 *打印当前页面
 */
function printPageCurr(){
	var isSucc = setPageSetupParamNew();
	if(isSucc == -1){
		return ;
	}
	if(isIE7() == 0){
		document.execCommand('print',false,null);
	}else{
		Ext.getCmp('wbPanel').add({
			html: "<object id=\"WebBrowser\" width=0 height=0 classid=\"clsid:8856F961-340A-11D0-A96B-00C04FD705A2\">"
		});
		Ext.getCmp('wbPanel').doLayout();
		document.all.WebBrowser.ExecWB(6,6); //直接打印
		Ext.getCmp('wbPanel').removeAll();
		Ext.getCmp('wbPanel').doLayout();
	}
}

/**
 *打印预览当前页面
 *由于是在Iframe里面预览，所以此方法在Iframe里面没用
 */
function printViewCurr(){
	$('#beforeEnd').html("<object id=\"WebBrowser\" width=0 height=0 classid=\"clsid:8856F961-340A-11D0-A96B-00C04FD705A2\">");
	var isSucc = setPageSetupParamNew();
	if(isSucc == -1){
		return ;
	}
//	window.parent.frames["printCancelExchange"].document.all.WebBrowser.ExecWB(7,1);
	document.all.WebBrowser.ExecWB(7,1);  // 打印预览
	$('#beforeEnd').html('');
}

/**
 * 关闭打印标签页
 * @param tabTitle
 */
function printClose(tabTitle){
	tabTitle = decodeURI(tabTitle);
	var tabs = window.parent.$("#tabs");
   	tabs.tabs('close', tabTitle); 
}

/**
 * 下载帮助文档
 */
function downloadHelpDoc(){
	var winPara = 'height=100, width=200, top=300, left=500, toolbar=no,menubar=no,'
		+ ' scrollbars=no, resizable=no,location=no, status=no';
	var fileName = 'ConfigPrintHelper.doc';	//打印设置帮助文档
	var uploadUrl = basepath + '/TempDownload?filename=' + fileName;
	window.open(uploadUrl, '', winPara);
}

/**
 * 判断是否为IE7
 * 0表示是IE7
 * -1表示不是
 */
function isIE7(){
	 if(navigator.appVersion.indexOf("MSIE 7") != -1){
		 return 0;
	 }else{
		 return -1;
	 }
}

