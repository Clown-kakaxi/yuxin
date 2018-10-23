//袁晓荣     2003-02-20
//最佳存款组合计算器

function calc(oDocument)
{
	var ratio = 0;
	var endsum = 0;
	var type = oDocument.all.cbTerm.options[oDocument.all.cbTerm.selectedIndex].value;
	var result = "";

	if ((type==2)||(type==3)||(type==5))
	{
		ratio = GetRMBSaveRatio(2,type*12,window.xmlRMBSaveRate.XMLDocument);
		endsum = Execute(type,ratio,oDocument.all.edSum.value);
		result = "您可以一次存"+type+"年期定期存款。\n税后到期本息总额为";
	}
	if (type==4)
	{
		ratio = GetRMBSaveRatio(2,1*12,window.xmlRMBSaveRate.XMLDocument);  
		endsum = Execute(1,ratio,oDocument.all.edSum.value);
		
		ratio = GetRMBSaveRatio(2,3*12,window.xmlRMBSaveRate.XMLDocument);  
		endsum = Execute(3,ratio,endsum);
		result = "您可以考虑先存1年期定期存款，\n到期本息再转存3年期定期存款。\n税后到期本息总额为";
	}
	if ((type==6)||(type==7)||(type==8)||(type==10))
	{
		ratio = GetRMBSaveRatio(2,(type-5)*12,window.xmlRMBSaveRate.XMLDocument);
		endsum = Execute(type-5,ratio,oDocument.all.edSum.value);
		
		ratio = GetRMBSaveRatio(2,5*12,window.xmlRMBSaveRate.XMLDocument);
		endsum = Execute(5,ratio,endsum);
		result = "您可以考虑先存"+(type-5)+"年期定期存款，\n到期本息再转存5年期定期存款。\n税后到期本息总额为";
	}
	if (type==9)
	{
		ratio = GetRMBSaveRatio(2,1*12,window.xmlRMBSaveRate.XMLDocument);
		endsum = Execute(1,ratio,oDocument.all.edSum.value); 
		
		ratio = GetRMBSaveRatio(2,3*12,window.xmlRMBSaveRate.XMLDocument);
		endsum = Execute(3,ratio,endsum);
		
		ratio = GetRMBSaveRatio(2,5*12,window.xmlRMBSaveRate.XMLDocument);
		endsum = Execute(5,ratio,endsum);
		result = "您可以考虑先存1年期定期存款，\n到期本息再转存3年期定期存款，\n到期后本息再存入5年期定期存款。\n税后到期本息总额为";
	}
	window.lbResult.innerText = result+NBround(new Number(endsum),2)+"元";
	return false;
}

function Execute(type,ratio,edSum)
{
	var EndSum=0;
        var interestRate = 0.05;  //利息税率

	EndSum=edSum*(1+(1-interestRate)*ratio/100*type);
	EndSum=NBround(EndSum,2);
	return EndSum;
}