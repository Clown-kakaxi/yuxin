function CalcBond11(Method)
{
	var Year = 0,Freq = 0,UnitPrice = 0;		//int
	var UnitCost,Rate = 0;		//double
	var BuyDate=new Date();
	var SellDate=new Date();
	
	UnitCost = parseFloat(BondERateCalc.Bond11_edUnitCost.value);
	UnitPrice = parseInt(BondERateCalc.Bond11_edUnitPrice.value);
	BuyDate.setTime(StrToDate(BondERateCalc.Bond11_edBuyDate.value));
	SellDate.setTime(StrToDate(BondERateCalc.Bond11_edSellDate.value));
	if(Method == 1)
	{
		Rate = parseFloat(BondERateCalc.Bond11_edRate1.value)/100;
		Year = parseInt(BondERateCalc.Bond11_edYear.value);
	}
	else if(Method == 2)
	{
		Rate = parseFloat(BondERateCalc.Bond11_edRate2.value)/100;
		Freq = parseInt(BondERateCalc.Bond11_edFreq.value);
	}
	
	Excute(UnitCost,BuyDate,SellDate,UnitPrice,Rate,Year,Freq,Method);
}

function CalcBond21()
{
	var Year,UnitPrice;
	var UnitCost,Rate;
	var BuyDate=new Date();
	var SellDate=new Date();
	
	UnitCost = parseFloat(BondERateCalc.Bond21_edUnitCost.value);
	UnitPrice = parseInt(BondERateCalc.Bond21_edUnitPrice.value);
	BuyDate.setTime(StrToDate(BondERateCalc.Bond21_edBuyDate.value));
	SellDate.setTime(StrToDate(BondERateCalc.Bond21_edSellDate.value));
	Rate = parseFloat(BondERateCalc.Bond21_edRate.value)/100;
	Year = parseInt(BondERateCalc.Bond21_edYear.value);

	Excute(UnitCost,BuyDate,SellDate,UnitPrice,Rate,Year,0,3);
}

function CalcBond31()
{
	var Freq,UnitPrice;
	var UnitCost,Rate;
	var BuyDate=new Date();
	var SellDate=new Date();
	
	UnitCost = parseFloat(BondERateCalc.Bond31_edUnitCost.value);
	UnitPrice = parseInt(BondERateCalc.Bond31_edUnitPrice.value);
	BuyDate.setTime(StrToDate(BondERateCalc.Bond31_edBuyDate.value));
	SellDate.setTime(StrToDate(BondERateCalc.Bond31_edSellDate.value));
	Rate = parseFloat(BondERateCalc.Bond31_edRate.value)/100;
	Freq = parseInt(BondERateCalc.Bond31_edFreq.value);

	Excute(UnitCost,BuyDate,SellDate,UnitPrice,Rate,0,Freq,4);
}
	/// 债券单位成本（包括成交价格和应计利息）: 　  元，正数，小数点后两位
	//var Cost=0;
	/// 债券购买交割日: 　  年 /月/日
	//var BondBuyDate=new Date();
	/// 债券到期兑付日: 　  年 /月/日，晚于购买交割日
	//var BondEndDate=new Date(); 
	///债券单位面值: 　  元 ，正整数 
	//var EndCost=0;
	///  债券票面年利率: 　  (%)  ，正数，小数点后两位
	//var BondRate=0;
	/// 债券偿还期限: 　  年，正整数
	//var YearTimes=0;
	/// 债券每年的利息支付频率: 　  次，正整数
	//var Freq=0;
	/// 该债券的到期收益率为：%（正数，保留两位小数；计算得出）
	/// <return>到期收益率</return>
	/// <param name="options">参数 0、贴现
	///                            1、到期一次还本付息债券
	///                            2、剩余流通期限在一年以内（含一年）的定期支付债券
	///                            3、剩余流通期限在一年以上的到期一次还本付息债券 
	///	                           4、处于最后付息周期的固定利率附息债券和浮动利率债券
	///			</param>
function Excute(Cost,BondBuyDate,BondEndDate,EndCost,BondRate,YearTimes,Freq,options)
{
	var w,m,tDays=0;	//int
	var l,pv,x,s,e,isetp;	//double
	var CurrDate=new Date(BondBuyDate.getFullYear(),0,1);
	var BondEndRate;
	
	tDays=GetDayLen1(BondEndDate,BondBuyDate)+1;
	
	switch(options)
	{
		case 0:
			BondEndRate=(EndCost-Cost)/Cost*365/tDays;
			break;
		case 1:
			BondEndRate=(EndCost+YearTimes*EndCost*BondRate-Cost)/Cost*365/tDays;
			break;
		case 2:
			BondEndRate=((EndCost+EndCost*BondRate/Freq)-Cost)/Cost*365.0/tDays;
			break;
		case 3:
			BondEndRate=Math.pow((EndCost+EndCost*YearTimes*BondRate)/Cost,365.0/tDays)-1;
			break;
		case 4:
			l=tDays*Freq/365;
			m=parseInt(l + 1);
			w= tDays % (365/Freq) / (365/Freq);
			isetp=0.0001;
			s=0.001;
			e=1;
			pv=0;						
			x=(e-s)/2;
			while ((Math.abs(pv-Cost)>0.001)&&(Math.abs(e-s)>isetp))
			{
				pv=Calc(x,w,m,EndCost,BondRate,Freq);
				if (pv==0) break;
				if (pv<Cost) 
				{
					e=x;
					x=(s+e)/2;
				}
				if (pv>Cost)
				{
					s=x;
					x=(s+e)/2;
				}
			}
			BondEndRate=x;
			break;
	}
	BondERateCalc.lbResult.value = NBround(BondEndRate*100,2);
}

function Calc(x, w, m, EndCost, BondRate, Freq)
{
	var y=0;
	for (var i=0;i<=m-1;i++)
		y=y+((EndCost*BondRate)/Freq)/Math.pow((1+x/Freq),i+w);
	y=y+(EndCost/Math.pow((1+x/Freq),(w+m-1)));
	
	return y;
}