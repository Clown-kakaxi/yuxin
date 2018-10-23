/**
 * @description 打印之 价格计算结果明细
 * @author denghj
 * @since 2015-07-16
 */
 
 Ext.onReady(function(){
	
    var html =  '<div class="showPrintContent"><div class="Noprint"><input type="button" class="button" value="打印" onclick="printPageCurr();"><div id="beforeEnd"></div></div>';
		html += '<div id="showPrintView">';
		html +='<div class="single"><table class="print_tab_01" id="myTab">';
		html += '<tr height=35px><td width=540px align="center" colspan=6 class="title2Div">中小企业价格授权表</td><td class="tdDiv"><font color=blue>合同编号</font></td><td class="td2Div"><input type="text" name="contractNO" class="textDiv" "></td></tr>';
 	 	html += '<tr height=35px><td class="tdDiv"><font color=blue>企业名称</font></td><td width=270px align="center" colspan=3><input type="text" name="companyName" class ="textDiv"></td><td class="tdDiv">价格对应等级</td><td class="td2Div">'+PRICE+'</td><td class="tdDiv">抵押品类型</td><td width=90px class="td2Div">'+LOAN+'</td></tr>'; 	 	
 	 	html += '<tr height=35px><td class="tdDiv">动拨类型</td><td class="tdDiv"><select name="agreeableType" class="textDiv"><option value="firstAgree">首次动拨</option><option value="notFirstAgree">非首次动拨</option></select></td><td class="tdDiv">贷款期限(月)</td><td class="td2Div">'+(Number(LIMIT1_TIME)> Number(LIMIT2_TIME) ? Number(LIMIT1_TIME) : Number(LIMIT2_TIME))+'</td><td class="tdDiv">利率调整方式</td><td class="tdDiv"><select name="rateType" class="textDiv"><option value="oppositeFloatRate">相对浮动利率</option><option value="fixedRate">固定利率</option><option value="floatRate">浮动利率</option></select></td><td class="tdDiv">利率/FTP查询日期</td><td class="td2Div">'+new Date().format('Y/m/d')+'</td></tr>';
 	 	html += '<tr height=35px><td class="tdDiv"><font color=red>核准金额(千元)</font></td><td width=270px align="center" colspan=3 >'+numFormat((Number(LIMIT1)+Number(LIMIT2))*10,2)+'</td><td class="tdDiv">本次动拨总金额(千元)</td><td width=270px align="center" colspan=3><input type="text" id="amountAgree" readOnly="true" class="textDiv"></td></tr>'; 	 		 	
 	 	html += '<tr height=35px><td class="tdDiv">额度编号</td><td  align="center" colspan=3><font color=red>额度1</font></td><td align="center" colspan=4><font color=red>额度2</font></td></tr>'; 	 		 	
 	 	html += '<tr height=35px><td class="tdDiv">核准金额(千元)</td><td  align="center" colspan=3 >'+numFormat(Number(LIMIT1)*10,2)+'</td><td align="center" colspan=4>'+numFormat(Number(LIMIT2)*10,2)+'</td></tr>'; 	 		 	
 	 	html += '<tr height=35px><td class="tdDiv"><font color=blue>动拨资金(千元)</font></td><td  align="center" colspan=3><input type="text" id="limit1Agree" class="textDiv" onKeyUp="amount(this)" onBlur="overFormat(this)" oninput="amountAgree()"></td><td align="center" colspan=4><input type="text" id="limit2Agree" class="textDiv" onKeyUp="amount(this)" onBlur="overFormat(this)" oninput="amountAgree()"></td></tr>'; 	 		 	
 	 	html += '<tr height=35px><td class="tdDiv">期限(月)</td><td  align="center" colspan=3>'+LIMIT1_TIME+'</td><td align="center" colspan=4>'+LIMIT2_TIME+'</td></tr>'; 	 		 	
 	 	html += '<tr height=35px><td class="tdDiv">还款方式</td><td  align="center" colspan=3>'+REPAY1+'</td><td align="center" colspan=4>'+REPAY2+'</td></tr>'; 	 		 	
 	 	html += '<tr height=35px><td class="tdDiv">贷款利率采用固定/基准上浮</td><td  align="center" colspan=3>'+Number(LIMIT1_RATE).toFixed(4)+'% (基准利率'+Number(EMPOWER1_RMBRATE).toFixed(2)+'% * '+(Number(LIMIT1_RATE/EMPOWER1_RMBRATE)*100).toFixed(1)+'%)</td><td align="center" colspan=4 id="cleanRMB2">'+Number(LIMIT2_RATE).toFixed(4)+'% (基准利率'+Number(EMPOWER2_RMBRATE).toFixed(2)+'% * '+(Number(LIMIT2_RATE/EMPOWER2_RMBRATE)*100).toFixed(1)+'%)</td></tr>'; 	 		 	
 	 	html += '<tr height=35px><td class="tdDiv">授权利率标准</td><td  align="center" colspan=3>'+Number(EMPOWER1_RATE).toFixed(4)+'% (FTP'+Number(EMPOWER1_FTPRATE).toFixed(2)+'% * '+(Number(EMPOWER1_RATE/EMPOWER1_FTPRATE)*100).toFixed(0)+'%)</td><td align="center" colspan=4 id="cleanFTP2">'+Number(EMPOWER2_RATE).toFixed(4)+'%(FTP '+Number(EMPOWER2_FTPRATE).toFixed(2)+'% * '+(Number(EMPOWER2_RATE/EMPOWER2_FTPRATE)*100).toFixed(0)+'%)</td></tr>'; 	 		 	
 	 	html += '<tr height=35px><td class="tdDiv">额度需签核层级</td><td  align="center" colspan=3>'+LIMIT1_LEVEL+'</td><td align="center" colspan=4>'+LIMIT2_LEVEL+'</td></tr>'; 	 		 	
 	 	html += '<tr height=35px><td class="tdDiv">综合年化利率</td><td  align="center" colspan=3>'+AMOUNT_RATE+'</td><td class="tdDiv"><font color=red>最终需签核等级</font></td><td align="center" colspan=3><font color=red>'+FINAL_LEVEL+'</font></td></tr>'; 	 		 	
 	 	html += '<tr height=35px rowspan=2><td class="tdDiv">客户经理签名</td><td class="tdDiv"></td><td class="tdDiv">团队主管签名(授权等级Ⅰ)</td><td class="tdDiv"></td><td class="tdDiv">营业单位行长签字(授权等级Ⅱ)</td><td class="tdDiv"></td><td class="tdDiv">中小企业部部门主管签名(授权等级Ⅲ)</td><td class="tdDiv"></td></tr>';
 	 	html += '<tr height=35px><td class="tdDiv">签名日期</td><td class="tdDiv"></td><td class="tdDiv">签名日期</td><td class="tdDiv"></td><td class="tdDiv">签名日期</td><td class="tdDiv"></td><td class="tdDiv">签名日期</td><td class="tdDiv"></td></tr>'; 	 	
 	 	html +='</table>';
 	 	html +='</div></div>';
 	 
	var printPanel = new Ext.Panel({
    	layout:'fit',
		items:[]
	});
   
    var wbPanel = new Ext.Panel({
		id : 'wbPanel',
		hidden: true
	});
	
	var viewport = new Ext.Viewport({
		layout : 'fit',
		items : [printPanel,wbPanel]
	});	
	
	printPanel.removeAll();
    printPanel.add({
        autoScroll : true,
        html:html
    });
    printPanel.doLayout();
		
    	if(EMPOWER2_FTPRATE == "undefined" || EMPOWER2_RMBRATE == "undefined"){
    		document.getElementById("cleanRMB2").innerHTML = '';
    		document.getElementById("cleanFTP2").innerHTML = '';
    	}
   	       
 });  

 
 