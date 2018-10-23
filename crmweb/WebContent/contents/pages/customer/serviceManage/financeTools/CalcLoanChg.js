//利率变动后还款计算器

// 还款期次Times
// 还款时间Year
// 偿还的本金Corpus 
// 偿还的 利息RateSum
// 剩余的本金LeavCorpus
function Execute(oDocument)
{
	LoanType=oDocument.all.edType.selectedIndex;//贷款类型(暂停使用)
	LoanSum=parseInt(oDocument.all.edLoanSum.value);//贷款总额
	LoanYears=parseInt(oDocument.all.edLoanYears.value);//贷款年限
	Date1=oDocument.all.edDate1.value;	//贷款初始日期
	Date2=oDocument.all.edDate2.value;//第一次调整日期
	Date3=oDocument.all.edDate3.value;//第二次调整日期
	Date4=oDocument.all.edDate4.value;//第三次调整日期
	Rate1=oDocument.all.edRate1.value;//贷款初始利率
	Rate2=oDocument.all.edRate2.value;//第一次变动利率
	Rate3=oDocument.all.edRate3.value;//第二次变动利率
	Rate4=oDocument.all.edRate4.value;//第三次变动利率
    var i=0,j=0,result=0,count=0;			  
	var Balance=0;								  
	var Months=0;
	var dt= new Date();
	var dt1= new Date();
	
	Balance=LoanSum;
	var rootchg = document.all.xmlchg.XMLDocument.documentElement;
	var rootresult = document.all.xmlresult.XMLDocument.documentElement;
	var node;
	var i, m;

	//清空xml中的数据
	m = rootchg.childNodes.length;
	
	for (i=0;i<m;i++)
	{
		node = rootchg.childNodes.item(0);
		rootchg.removeChild(node);
	}

	//清空xml中的数据
	m = rootresult.childNodes.length;
	for (i=0;i<m;i++)
	{
		node = rootresult.childNodes.item(0);
		rootresult.removeChild(node);
	}
	
	if(Rate1!="")
	{
		RateXml(document.all.xmlchg.XMLDocument,Date1,parseFloat(Rate1)/100);
	}
	if(Rate2!="")
	{
		RateXml(document.all.xmlchg.XMLDocument,Date2,parseFloat(Rate2)/100);
	}
	if(Rate3!="")
	{
		RateXml(document.all.xmlchg.XMLDocument,Date3,parseFloat(Rate3)/100);
	}
	if(Rate4!="")
	{
		RateXml(document.all.xmlchg.XMLDocument,Date4,parseFloat(Rate4)/100);
	}
	count=rootchg.childNodes.length;
	rootchg = document.all.xmlchg.XMLDocument.documentElement;
	//var totalInterest = new Number(0);//aiai add
	for (i=0;i<count;i++)
	{
		node1=rootchg.childNodes.item(i);
		if (i<(count-1))
		{
			node2=rootchg.childNodes.item(i+1);
			if (StrToDate(node2.childNodes.item(0).text)<StrToDate(node1.childNodes.item(0).text))
			{
				result=-1;
				break;//下次调整在上次之前，错误 退出
			}
		}
		if ( i == 0 )
		{
			MonthReturnSum=CalcLoanBalance(Balance,LoanYears*12,new Number(node1.childNodes.item(1).text),1);//计算月还款额
			result=CalcLoanPay(document.all.xmldso.XMLDocument,Balance,StrToDate(node1.childNodes.item(0).text),
				LoanYears*12,new Number(node1.childNodes.item(1).text),1);
		}
		root = document.all.xmldso.XMLDocument.documentElement;
		var m;
		var dateyear = new Date();
		m = root.childNodes.length;
		var InterestSum = new Number(0);
		var MonthReturnSum;
		var memo;
		if (i==0) //计算初始的还款情况
		{
			for (j=0;j<m;j++)//计算初始的还款情况下的还款利息总额
			{
				node = root.childNodes.item(j);
				InterestSum =InterestSum +new Number(node.childNodes.item(2).text);
			}
			//totalInterest += InterestSum;
		}
		else//有调整时
		{   
			node2=rootchg.childNodes.item(0);
			dt = StrToDate(node2.childNodes.item(0).text);
			dt.setYear(dt.getYear()+LoanYears);
			//Months = Math.floor((dt - StrToDate(node1.childNodes.item(0).text)) / (1000 * 60 * 60 * 24 * 30));			
			for (j=0;j<m;j++)  //计算剩余本金
			{
				dt1=StrToDate(node1.childNodes.item(0).text);
				dt1.setMonth(dt1.getMonth()); // [added by huhao, 2003/4/21]  [modified by aiai -_-! haozi added an error!!!~~~ the dt1.Month does not have to -1  22/6/04]
				node = root.childNodes.item(j);
				//alert(node.childNodes.item(0).text+'  =  '+node.childNodes.item(1).text+'  =  '+node.childNodes.item(2).text+'  =  '+node.childNodes.item(3).text+'  =  '+node.childNodes.item(4).text+'  =  '+node.childNodes.item(5).text)
				dateyear = StrToDate(node.childNodes.item(1).text);
				if ((dt1.getYear()==dateyear.getYear())&&(dt1.getMonth()==dateyear.getMonth()))
				{
					Balance=new Number(node.childNodes.item(5).text);
					break;
				}
			}
			Months = m-(j+1);//剩余还款期数=总期数 - 已经还款期数			
			MonthReturnSum=CalcLoanBalance(Balance,Months,new Number(node1.childNodes.item(1).text),1);//计算月还款额
			result=CalcLoanPay(document.all.xmldso.XMLDocument,Balance, StrToDate(node1.childNodes.item(0).text),
				Months,new Number(node1.childNodes.item(1).text),1);
			root = document.all.xmldso.XMLDocument.documentElement;
			m = root.childNodes.length;
			for (j=0;j<m;j++)//计算利率变动后还款利息总额
			{
				node = root.childNodes.item(j);
				InterestSum = InterestSum + new Number(node.childNodes.item(2).text);
			}
			//totalInterest += InterestSum;
		}
		switch(i)
		{
		case 0:
			memo ="初始还款情况";
			break;
		case 1:
			memo ="第一次利率变动";
			break;
		case 2:
			memo ="第二次利率变动";
			break;
		case 3:
			memo ="第三次利率变动";
			break;
		default:
			memo ="其它情况";
			break;
		}
		
		ReturXml(document.all.xmlresult.XMLDocument,memo,MonthReturnSum,InterestSum);
	}
	//RetTotalXml(document.all.xmlresult.XMLDocument,totalInterest);
}

function RateXml(oXMLDoc,Datex,Ratex)
{
	var elem;
	var rootchg = oXMLDoc.documentElement;
	// 新利率执行时间：年月日Datex
	// 新贷款年利率（正小数数）chgrate
	elem = oXMLDoc.createElement("Items");
	rootchg.appendChild(elem);
	node = rootchg.lastChild;

	elem = oXMLDoc.createElement("chgdate");
	node.appendChild(elem);
	node.lastChild.text = Datex;
	
	elem = oXMLDoc.createElement("chgrate");
	node.appendChild(elem);
	node.lastChild.text = Ratex;
}
	
/*function RetTotalXml(oXMLDoc,Interest)
{
	var elem;
	var rootres = oXMLDoc.documentElement;
	// 调用Execute方法 计算得出的利息总额：元（正数，保留两位小数）
	elem = oXMLDoc.createElement("Items");
	rootres.appendChild(elem);
	node = rootres.lastChild;

	elem = oXMLDoc.createElement("Memo");
	node.appendChild(elem);
	node.lastChild.text = '';
	
	elem = oXMLDoc.createElement("MonthRet");
	node.appendChild(elem);
	node.lastChild.text = '';
	
	elem = oXMLDoc.createElement("Interest");
	node.appendChild(elem);
	node.lastChild.text = '利息合计： '+(Math.round(Interest * 10000) / 10000).toString();
}*/
	
function ReturXml(oXMLDoc,Memo,MonthRet,Interest)
{
	var elem;
	var rootres = oXMLDoc.documentElement;
	// 调用Execute方法 计算得出的月还款额：元（正数，保留两位小数）
	// 调用Execute方法 计算得出的利息总额：元（正数，保留两位小数）
	elem = oXMLDoc.createElement("Items");
	rootres.appendChild(elem);
	node = rootres.lastChild;

	elem = oXMLDoc.createElement("Memo");
	node.appendChild(elem);
	node.lastChild.text = Memo;
	
	elem = oXMLDoc.createElement("MonthRet");
	node.appendChild(elem);
	node.lastChild.text = (Math.round(MonthRet * 100) / 100).toString();
	
	elem = oXMLDoc.createElement("Interest");
	node.appendChild(elem);
	node.lastChild.text = (Math.round(Interest * 10000) / 10000).toString();
}
/// 等额付款的现金支出（还款计划）数据生成  
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
		node.lastChild.text = Math.round(mRecRateSum * 100) / 100;
	
		elem = oXMLDoc.createElement("Corpus");
		node.appendChild(elem);
		node.lastChild.text = Math.round(mRecCorpus * 100) / 100;

		elem = oXMLDoc.createElement("CorpusRate");
		node.appendChild(elem);
		node.lastChild.text = Math.round((mRecRateSum + mRecCorpus) * 100) / 100;
	
		elem = oXMLDoc.createElement("LeavCorpus");
		node.appendChild(elem);
		node.lastChild.text = Math.round(mRecLeavCorpus * 100) / 100;
	}
}

/// 计算每期还款金额   
/// LastSum债务本金（贷款总额）
/// times债务的期限（贷款期次）
/// Rate债务的年利率
/// Freq债务的偿还频率
/// Balance返回每期还款金额
function CalcLoanBalance(LastSum,times,Rate,Freq)
{
	var r,dtemp,Balance;
	//alert(LastSum+"&"+times+"&"+Rate+"&"+Freq);
	r=Rate*Freq/12;
	dtemp=Math.pow((1+r),times);
	if (dtemp!=1)
		Balance=LastSum*r*dtemp/(Math.pow((1+r),times)-1);
	else
		Balance=0;
	return Balance;
}