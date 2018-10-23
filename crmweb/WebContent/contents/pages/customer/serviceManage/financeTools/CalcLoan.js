//贷款计算器
	    
	    /// 根据属性PayWay（还款方式）计算还款表
	    /// <param name="PlayList">还款表</param>
		function GetPlayList(oDocument)
		{
		var LastSum=oDocument.all.edLastSum.value;
		var BeginYear=0;
		var LoanTimes=parseInt(oDocument.all.edTimes.value);
		var Rate=parseFloat(oDocument.all.edRate.value)/100;
		var PayWay=oDocument.all.ddlPayWay.value;
		if (oDocument.all.rbPayFreq_0.checked)
			PayFreq=1;
		else
		{
			if (oDocument.all.rbPayFreq_1.checked)
				PayFreq=3;
			else
				PayFreq=1;
		}
		
			var result=0;
			var LoanTimesTemp=0;
			LoanTimesTemp=LoanTimes/PayFreq;
			PlayList=new Array();
			var nowdate=new Date();
			var addnowdate=new Date();
			
			addnowdate.setMonth(addnowdate.getMonth()+LoanTimes)
			switch (PayWay)
			{
				case "1"://等额还款
					result=CalcLoanPay(document.all.xmldso.XMLDocument, LastSum,nowdate,LoanTimesTemp,Rate,PayFreq);
					break;
				case "2"://等本还款
					result=ECorpus(document.all.xmldso.XMLDocument, LastSum,nowdate,LoanTimesTemp,Rate,PayFreq);
					break;
				case "3"://一次性还本付息
					result=CalcPayOnce(document.all.xmldso.XMLDocument, LastSum,nowdate,addnowdate,Rate);
					break;
			}
			//return result;
		}
		
//---------------------公用部分-----------------------------------------
/// 等额付款的现金支出（还款计划）数据生成  
/// <param name="oXMLDoc">还款表</param>
/// <param name="LastSum">债务本金（贷款总额）</param>
/// <param name="BeginYear">债务开始年份（贷款日期）</param>
/// <param name="times">债务的期限（贷款期限（月份数））</param>
/// <param name="Rate">债务的年利率</param>
/// <param name="Freq">债务的偿还频率</param>
function CalcLoanPay(oXMLDoc, LastSum,BeginYear,times,Rate,Freq)
{			
	var root = oXMLDoc.documentElement;
	var elem;
	var node;
	var i, m;
	var fTotalInterest = new Number(0), fTotalSum = new Number(0);
	//清空xml中的数据
	m = root.childNodes.length;
	for (i=0;i<m;i++)
	{
		node = root.childNodes.item(0);
		root.removeChild(node);
	}

	i=0;
	var r,M,Inter,C,dtemp;
	r=Rate*Freq/12;
	dtemp=Math.pow((1+r),times);
	if (dtemp!=1)
		M=(LastSum*r*dtemp/(dtemp-1));
	else
		M=LastSum/times;
	for (i=0;i<times;i++) 
	{
		Inter=LastSum*r;
		C=M-Inter;
		mRecTimes=i+1;
		mRecYear=new Date(BeginYear.getYear(), BeginYear.getMonth() + 1, BeginYear.getDate());
		mRecYear.setMonth(mRecYear.getMonth()+(i*Freq));
		LastSum=LastSum-C;
		mRecCorpus=C;
		mRecRateSum=Inter;
		mRecLeavCorpus=LastSum;

		//-----绑定数据到表格-----
		// 还款期次Times
		// 还款时间Year
		// 偿还的本金Corpus 
		// 偿还的 利息RateSum
		// 剩余的本金LeavCorpus
		elem = oXMLDoc.createElement("Items");
		root.appendChild(elem);
		node = root.lastChild;

		elem = oXMLDoc.createElement("Times");
		node.appendChild(elem);
		node.lastChild.text = mRecTimes;
	
		elem = oXMLDoc.createElement("Year");
		node.appendChild(elem);
		node.lastChild.text = mRecYear.getYear() + "-" + (mRecYear.getMonth() + 1) + "-" + mRecYear.getDate();

		elem = oXMLDoc.createElement("RateSum");
		node.appendChild(elem);
		node.lastChild.text = (Math.round(mRecRateSum * 100) / 100).toString();
		fTotalInterest += mRecRateSum;//new Number(node.lastChild.text);
	
		elem = oXMLDoc.createElement("Corpus");
		node.appendChild(elem);
		node.lastChild.text = Math.round(mRecCorpus * 100) / 100;
		fTotalSum += mRecCorpus;//new Number(node.lastChild.text);

		elem = oXMLDoc.createElement("CorpusRate");
		node.appendChild(elem);
		node.lastChild.text = Math.round((mRecRateSum + mRecCorpus) * 100) / 100;
	
		elem = oXMLDoc.createElement("LeavCorpus");
		node.appendChild(elem);
		node.lastChild.text = Math.round(mRecLeavCorpus * 100) / 100;		
	}
	elem = oXMLDoc.createElement("Items");
	root.appendChild(elem);
	node = root.lastChild;

	elem = oXMLDoc.createElement("Times");
	node.appendChild(elem);
	node.lastChild.text = "合计";
	
	elem = oXMLDoc.createElement("Year");
	node.appendChild(elem);

	elem = oXMLDoc.createElement("RateSum");
	node.appendChild(elem);
	node.lastChild.text = (Math.round(fTotalInterest * 100) / 100).toString();
	
	elem = oXMLDoc.createElement("Corpus");
	node.appendChild(elem);
	node.lastChild.text = Math.round(fTotalSum * 100) / 100;

	elem = oXMLDoc.createElement("CorpusRate");
	node.appendChild(elem);
	node.lastChild.text = Math.round((fTotalInterest + fTotalSum) * 100) / 100;
	
	elem = oXMLDoc.createElement("LeavCorpus");
	node.appendChild(elem);
}
		
/// 等本付款的现金支出（还款计划）数据生成（本金还款法） 
/// <param name="oXMLDoc">还款表</param> 
/// <param name="LastSum">债务本金（贷款总额）</param>
/// <param name="BeginYear">债务开始年份（贷款日期）</param>
/// <param name="times">债务的期限（贷款期限（月份数））</param>
/// <param name="Rate">债务的年利率</param>
/// <param name="Freq">债务的偿还频率</param>
function ECorpus(oXMLDoc,LastSum,BeginYear,times,Rate,Freq)
{
	var r,Inter,c;
	var i=0,result=0;
	var TotalLoan=LastSum;
	
	var root = oXMLDoc.documentElement;
	var elem;
	var node;
	var i, m;
	var fTotalInterest = new Number(0), fTotalSum = new Number(0);
	//清空xml中的数据
	m = root.childNodes.length;
	for (i=0;i<m;i++)
	{
		node = root.childNodes.item(0);
		root.removeChild(node);
	}
	
	r=Rate*Freq/12;
	if (times!=0)
		c=LastSum/times;
	else
		c=0;
	for (i=0;i<times;i++)
	{
		RecTimes=i+1;
		//RecYear=BeginYear;
		//RecYear.setMonth(BeginYear.getMonth()+(i*Freq));
		RecYear=new Date(BeginYear.getYear(), BeginYear.getMonth() + 1, BeginYear.getDate());
		RecYear.setMonth(RecYear.getMonth()+(i*Freq));
		Inter=LastSum*r;
		LastSum=LastSum-c;
		RecCorpus=c;
		RecRateSum=Inter;
		RecLeavCorpus=LastSum;
		
		//-----绑定数据到表格-----
		// 还款期次Times
		// 还款时间Year
		// 偿还的本金Corpus 
		// 偿还的 利息RateSum
		// 剩余的本金LeavCorpus					
		elem = oXMLDoc.createElement("Items");
		root.appendChild(elem);
		node = root.lastChild;

		elem = oXMLDoc.createElement("Times");
		node.appendChild(elem);
		node.lastChild.text = RecTimes;
	
		elem = oXMLDoc.createElement("Year");
		node.appendChild(elem);
		node.lastChild.text = RecYear.getYear() + "-" + (RecYear.getMonth() + 1) + "-" + RecYear.getDate();

		elem = oXMLDoc.createElement("RateSum");
		node.appendChild(elem);
		node.lastChild.text = (Math.round(RecRateSum * 100) / 100).toString();
		fTotalInterest += RecRateSum;//new Number(node.lastChild.text);
	
		elem = oXMLDoc.createElement("Corpus");
		node.appendChild(elem);
		
		node.lastChild.text = Math.round(RecCorpus * 100) / 100;
		fTotalSum += RecCorpus;//new Number(node.lastChild.text);

		elem = oXMLDoc.createElement("CorpusRate");
		node.appendChild(elem);
		node.lastChild.text = Math.round((RecRateSum + RecCorpus) * 100) / 100;
	
		elem = oXMLDoc.createElement("LeavCorpus");
		node.appendChild(elem);
		node.lastChild.text = Math.round(RecLeavCorpus * 100) / 100;
	}
	elem = oXMLDoc.createElement("Items");
	root.appendChild(elem);
	node = root.lastChild;

	elem = oXMLDoc.createElement("Times");
	node.appendChild(elem);
	node.lastChild.text = "合计";
	
	elem = oXMLDoc.createElement("Year");
	node.appendChild(elem);

	elem = oXMLDoc.createElement("RateSum");
	node.appendChild(elem);
	node.lastChild.text = (Math.round(fTotalInterest * 100) / 100).toString();
	
	elem = oXMLDoc.createElement("Corpus");
	node.appendChild(elem);
	node.lastChild.text = Math.round(fTotalSum * 100) / 100;

	elem = oXMLDoc.createElement("CorpusRate");
	node.appendChild(elem);
	node.lastChild.text = Math.round((fTotalInterest + fTotalSum) * 100) / 100;
	
	elem = oXMLDoc.createElement("LeavCorpus");
	node.appendChild(elem);
}
		
///一次性还本付息的现金支出（还款计划）数据生成 
/// <param name="oXMLDoc">还款表</param>
/// <param name="LastSum">债务本金（贷款总额）</param>
/// <param name="BeginYear"> 债务开始年份（贷款日期）</param>
/// <param name="EndYear">债务结束年份（贷款日期）</param>
/// <param name="Rate">债务的年利率</param>
function CalcPayOnce(oXMLDoc,LastSum,BeginYear,EndYear,Rate)
{
	var result=0;
	var r;
	var days;
			
	var root = oXMLDoc.documentElement;
	var elem;
	var node;
	var i, m;
	//清空xml中的数据
	m = root.childNodes.length;
	for (i=0;i<m;i++)
	{
		node = root.childNodes.item(0);
		root.removeChild(node);
	}
	
	r=Rate;
	days=GetDayLen(EndYear,BeginYear);//计算债务期限（天数）
	RecYear=EndYear;
	RecTimes =1;
	RecCorpus=new Number(LastSum);
	RecRateSum=RecCorpus*r/360*days;
	RecLeavCorpus=0;

	//-----绑定数据到表格-----	
	// 还款期次Times
	// 还款时间Year
	// 偿还的本金Corpus 
	// 偿还的 利息RateSum
	// 剩余的本金LeavCorpus
	
	elem = oXMLDoc.createElement("Items");
	root.appendChild(elem);
	node = root.lastChild;

	elem = oXMLDoc.createElement("Times");
	node.appendChild(elem);
	node.lastChild.text = RecTimes;
	
	elem = oXMLDoc.createElement("Year");
	node.appendChild(elem);
	node.lastChild.text = RecYear.getYear() + "-" + (RecYear.getMonth() + 1) + "-" + RecYear.getDate();

	elem = oXMLDoc.createElement("RateSum");
	node.appendChild(elem);
	node.lastChild.text = (Math.round(RecRateSum * 100) / 100).toString();

	elem = oXMLDoc.createElement("Corpus");
	node.appendChild(elem);
	node.lastChild.text = Math.round(RecCorpus * 100) / 100;

	elem = oXMLDoc.createElement("CorpusRate");
	node.appendChild(elem);
	node.lastChild.text = Math.round((RecRateSum + RecCorpus) * 100) / 100;
	
	elem = oXMLDoc.createElement("LeavCorpus");
	node.appendChild(elem);
	node.lastChild.text = Math.round(RecLeavCorpus * 100) / 100;
}
			
		/// 计算段后计息天数
		/// <param name="StartDate">债务结束日期</param>
		/// <param name="StandDate">债务开始日期或者标准日期（1999年11月1日）</param>
		/// <returns></returns>
function GetDayLen(StartDate,StandDate)
{
//以每月30天算一月，一年为360天
	return (StartDate.getYear()-StandDate.getYear())*360+(StartDate.getMonth()-StandDate.getMonth())*30+(StartDate.getDate()-StandDate.getDate());
}