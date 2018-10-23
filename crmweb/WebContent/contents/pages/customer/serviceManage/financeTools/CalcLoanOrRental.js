// 住房与租房比较器
function Excute(oDocument)
{
	CompareTime=parseInt(oDocument.all.edCompareTime.value);//希望进行比较的时间(月)
	HouseRate=parseFloat(oDocument.all.edHouseRate.value)/100; //房屋折旧/增值年比率(%)
	CashRate=parseFloat(oDocument.all.edCashRate.value)/100;//资金投资的年收益率(%)		    
	BuyFirstPay=parseInt(oDocument.all.edBuyFirstPay.value); //购房首付金额
	FundAmount=parseInt(oDocument.all.edFundAmount.value);//公积金贷款金额
	FundTime=parseInt(oDocument.all.edFundTime.value); //公积金贷款期限(月)
	FundRate=parseFloat(oDocument.all.edFundRate.value)/100;//公积金贷款年利率(%)
	LoanAmount=parseInt(oDocument.all.edLoanAmount.value); //商业贷款金额
	LoanTime=parseInt(oDocument.all.edLoanTime.value);//商业贷款期限(月)
	LoanRate=parseFloat(oDocument.all.edLoanRate.value)/100; //商业贷款年利率(%)
	RentOfMonth=parseInt(oDocument.all.edRentOfMonth.value);//每月租房费用
	//商业贷款每期还款金额             
	//公积金每期还款金额
	var onefund=0,oneloan=0;
	var lastfund=0,lastloan=0;
	var Dates = new Date();
	var result=0;	
			
			var dbltemp=0;
			//如果比较时间已经等于或大于公积金的期限，则公积金剩余本金为0
			if ((CompareTime>=FundTime))
			{
				lastfund = 0;
				onefund = CalcLoanBalance(FundAmount,FundTime,FundRate,1);
			}
			else
			{
				result = CalcLoanPay(document.all.xmldso.XMLDocument,FundAmount,Dates,FundTime,FundRate,1);
				var root = document.all.xmldso.XMLDocument.documentElement;
				var node;
				var i, m;
				m = root.childNodes.length;
				for (i=0;i<m;i++)
				{
					node = root.childNodes.item(i);
					if (new Number(node.childNodes.item(0).text)==CompareTime) 
					{
						onefund=new Number(node.childNodes.item(3).text)+new Number(node.childNodes.item(2).text);
						lastfund=new Number(node.childNodes.item(5).text);
						break;
					}
				}
			}
			//如果比较时间已经等于或大于商业贷款的期限，则剩余本金为0
			if ((CompareTime>=LoanTime))
			{
				lastloan=0;
				oneloan = CalcLoanBalance(LoanAmount,LoanTime,LoanRate,1);
			}
			else
			{
				result = CalcLoanPay(document.all.xmldso.XMLDocument,LoanAmount,Dates,LoanTime,LoanRate,1);
				var root = document.all.xmldso.XMLDocument.documentElement;
				var node;
				var i, m;
				m = root.childNodes.length;
				for (i=0;i<m;i++)
				{
					node = root.childNodes.item(i);
					if (new Number(node.childNodes.item(0).text)==CompareTime) 
					{
						oneloan=new Number(node.childNodes.item(3).text)+new Number(node.childNodes.item(2).text);
						lastloan=new Number(node.childNodes.item(5).text);
						break;
					}
				}
			}
		    //购买净资产=（首付+贷款）*（1+房屋增长率)
			BuyNetValue=(FundAmount+LoanAmount+BuyFirstPay)*Math.pow(1+HouseRate,CompareTime*1.0/12)-lastfund-lastloan;
			//租房净资产=按月循环计算：本月=(上月+公积金+贷款-P)*(1+r)
			RentalNetValue=BuyFirstPay;
			for (i=0;i<=CompareTime-1;i++)
			{
				dbltemp=RentalNetValue;
				if (i<LoanTime)
					dbltemp=dbltemp+oneloan;
				if (i<FundTime)
					dbltemp=dbltemp+onefund;
				RentalNetValue=(dbltemp-RentOfMonth)*(1+CashRate/12);
			}
			
			Comp=RentalNetValue-BuyNetValue;
    
	if(Comp>0)
		lbResult.innerText ="在偿还按揭贷款期间，每月所必须的还款支出为"+NBround(oneloan+onefund,2)+"元。假定租房和购房的支出相等，且租房费用不变，"+NBround(CompareTime,2)+"个月后，租房的净资产要比购房的净资产要多"+NBround(Comp,2)+"元";
	if(Comp<0)
		lbResult.innerText ="在偿还按揭贷款期间，每月所必须的还款支出为"+NBround(oneloan+onefund,2)+"元。假定租房和购房的支出相等，且租房费用不变，"+NBround(CompareTime,2)+"个月后，购房的净资产要比租房的净资产要多"+NBround(-Comp,2)+"元";
	if(Comp==0)
		lbResult.innerText ="在偿还按揭贷款期间，每月所必须的还款支出为"+NBround(oneloan+onefund,2)+"元。假定租房和购房的支出相等，且租房费用不变，"+NBround(CompareTime,2)+"个月后，购房的净资产与租房的净资产是相等的。";
		}
	

/// 等额付款的现金支出（还款计划）数据生成  
/// <param name="LastSum">债务本金（贷款总额）</param>
/// <param name="BeginYear">债务开始年份（贷款日期）</param>
/// <param name="times">债务的期限（贷款期限（月份数））</param>
/// <param name="Rate">债务的年利率</param>
/// <param name="Freq">债务的偿还频率</param>
/// <param name="result">还款表</param>
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
		/// 债务本金（贷款总额）
		/// 债务的期限（贷款期次）
		/// 债务的年利率
		/// 债务的偿还频率
		/// 返回每期还款金额
		function CalcLoanBalance(LastSum,times,Rate,Freq)
		{
			var r,dtemp,Balance;
			r=Rate*Freq/12;
			dtemp=Math.pow((1+r),times);
			if (dtemp!=1)
			    Balance=LastSum*r*dtemp/(Math.pow((1+r),times)-1);
			else
				Balance=0;
			return Balance;
		}