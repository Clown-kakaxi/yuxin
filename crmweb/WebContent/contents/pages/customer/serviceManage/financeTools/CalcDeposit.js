/// <summary>
///运行存款计算器，根据属性DepositWay和CalcOption的值
///把运行的不同结果存入
///属性 InitSaveSum，TermEndSum和InterestTaxSum中。
/// </summary>
function CalcDeposit()
{
	var DepositWay = 1;	//存款方式
	var YearRate = parseFloat(DepositCalculator.tbYearRate.value)/100;		//存款利率
	var InitSaveInDate = new Date();
	InitSaveInDate.setTime(StrToDate(DepositCalculator.beginDateID.value));	//初始存入日期
	var SaveYears = parseFloat(DepositCalculator.tbSaveTime.options[DepositCalculator.tbSaveTime.selectedIndex].value/12);	 //存期
	var ShresholdDate=new Date("1999/11/1");
	var SecondDate = new Date("2007/8/14")
	var InitSaveSum = 0;	//初始存入金额
	var TermEndSum = 0;		//到期本息总额
	var InterestTaxSum=0;	//扣除利息税
	var	 CalcOption; 		//计算选项
	var dtime=new Date(InitSaveInDate.getFullYear(),InitSaveInDate.getMonth(),InitSaveInDate.getDate());
	var months;
	
	dtime.setMonth(dtime.getMonth()+SaveYears*12);
	/*	 零存整取本息和＝月存额×12×存期（年）+月存额×累计月数×存款月利率
			 其中累计月数＝（12×存期+1）÷2×（12×存期）
	*/
	months=((12*SaveYears+1)/2*(12*SaveYears));
	if (DepositCalculator.rbDepositWay_0.checked==true) DepositWay = 1;
	if (DepositCalculator.rbDepositWay_1.checked==true) DepositWay = 2;
	if (DepositCalculator.rbCalcOption_0.checked==true)
	{
		CalcOption = 1;
		InitSaveSum = parseFloat(DepositCalculator.tbInitSaveSum.value);
	}
	else
	{
		CalcOption = 2;
		TermEndSum = parseFloat(DepositCalculator.tbInitSaveSum.value);
		//Calc(initdate,term,ratio,sum,savetype,2);   
	 }

	/*
	 * 计算条件：
	   年利率：r％（客户经理输入值优于参数表对应值）
	   储蓄存期：n年（3个月为0.25年，半年为0.5年）
	   计算到期本息总额，则还需知道：初期存入金额：A元
	   计算初期存入金额，则还需知道：到期本息总额：B元

	   1. 整存整取计算方法：
	 * */
	
	if (dtime<ShresholdDate) // 取<1999
	{
		if (DepositWay==1)//整存整取
		{
			/*
			 *（1）初始存入日期+储蓄存期在1999年11月1日之前：
				   已知A，求B：B＝A× r％ ×n +A
				   已知B，求A：A＝B÷[r％×n+1]
				   扣除利息税金额＝0
			 * */					
			if (CalcOption==1)
			{
				TermEndSum=InitSaveSum*(1+YearRate*SaveYears);
			}
			else if(CalcOption==2)
			{
				InitSaveSum=TermEndSum*1.0/(1+YearRate*SaveYears); 
			}
			InterestTaxSum=0;
		}
		else if (DepositWay==2)// 零存整取计算方法
		{
			/*（1）初始存入日期+储蓄存期在1999年11月1日之前：
				 已知A，求B：B＝12×A×n+[A×（12×n+1）÷2×12×n×（r％÷12）]
				 已知B，求A：A＝B÷[12×n+（12×n+1)÷2×12×n×（r％÷12）]
				 扣除利息税金额＝0
			*/
			if (CalcOption==1)
			{   //零存整取本息和＝月存额×12×存期（年）+月存额×累计月数×存款月利率
				TermEndSum=InitSaveSum*(12*SaveYears+months*(YearRate/12.0));
			}
			else if(CalcOption==2)
			{
				InitSaveSum=TermEndSum*1.0/(12*SaveYears+months*(YearRate/12.0)); 
			}
			InterestTaxSum=0;
		}
	}
	else 
	if (InitSaveInDate<ShresholdDate && dtime<SecondDate) // 存 - 1999- 取  -2007
	{
		var tDays;
		//ts=dtime-ShresholdDate;
		tDays=GetDayLen(dtime,ShresholdDate);
		if (DepositWay==1)//
			/*
			 * （3）初始存入日期在1999年11月1日之前，初始存入日期+储蓄存期在1999年11月1日之后（含）：
				   已知A，求B：B＝A+ A×n×r％－0.2×A×（初始存入日期+储蓄存期－1999年11月1日）/360×r％
				   其中：（初始存入日期+储蓄存期－1999年11月1日）为天数
				   已知B，求A：A＝B÷[1+n×r％－0.2×（初始存入日期+储蓄存期－1999年11月1日）/360×r％]
				   扣除利息税金额＝0.2×A×（初始存入日期+储蓄存期－1999年11月1日）/360×r％
			 * */
		{
			if (CalcOption==1)
			{
				TermEndSum=InitSaveSum*(1+YearRate*SaveYears-0.2*tDays/360*YearRate);
			}
			else if(CalcOption==2)
			{
				InitSaveSum=TermEndSum*1.0/(1+YearRate*SaveYears-0.2*tDays/360*YearRate); 
			}
			InterestTaxSum=0.2*InitSaveSum*tDays/360*YearRate;
		}
		else //
			/*（3）初始存入日期在1999年11月1日之前，初始存入日期+储蓄存期都在1999年11月1日之后（含）：
				 已知A，求B：B＝12×A×n+ A×（12×n+1）÷2×12×n×（r％÷12）－0.2×[A×（12×n+1）÷2×12×n×（r％÷12）]×（初始存入日期+储蓄存期－1999年11月1日）÷（储蓄存期×360）
				 其中：（初始存入日期+储蓄存期－1999年11月1日）、（储蓄存期×360）都为天数。
				 已知B，求A：A＝B÷[12×n+（12×n+1）÷2×12×n×（r％÷12）－0.2×（12×n+1）÷2×12×n×（r％÷12）×（初始存入日期+储蓄存期－1999年11月1日）÷（储蓄存期×360）]
				 扣除利息税金额＝0.2×[A×（12×n+1）÷2×12×n×（r％÷12）]×（初始存入日期+储蓄存期－1999年11月1日）÷（储蓄存期×360）
			 */
		{
			if (CalcOption==1)
			{
				TermEndSum=InitSaveSum*(12*SaveYears+months*(YearRate/12.0)*(1-0.2*tDays/(360*SaveYears)));
			}
			else if(CalcOption==2)
			{
				InitSaveSum=TermEndSum*1.0/(12*SaveYears+months*(YearRate/12.0)*(1-0.2*tDays/(360*SaveYears)));
			}  
			InterestTaxSum=0.2*InitSaveSum*months*(YearRate/12.0)*tDays/(360*SaveYears);
		}
	}
	else
	if (ShresholdDate<InitSaveInDate && dtime<SecondDate)//1999 -存 -取-2007
	{
		if (DepositWay==1)
			/*
			 *（2）初始存入日期、初始存入日期+储蓄存期都在1999年11月1日之后（含）：
					已知A，求B：B＝A+A×n×r％×0.8
					已知B，求A：A＝B÷[r％×n×0.8+1]
					扣除利息税金额＝A×n×r％×0.2
			  * */
		{
			if (CalcOption==1)
			{
				TermEndSum=InitSaveSum*(1+YearRate*SaveYears*0.8);
			}
			else if(CalcOption==2)
			{
				InitSaveSum=TermEndSum*1.0/(1+YearRate*SaveYears*0.8); 
			}
			InterestTaxSum=InitSaveSum*YearRate*SaveYears*0.2;
		}
		else
		{
			/*（2）初始存入日期、初始存入日期+储蓄存期都在1999年11月1日之后（含）：
				 已知A，求B：B＝12×A×n+[0.8×A×（12×n+1）÷2×12×n×（r％÷12）]
				 已知B，求A：A＝B÷[12×n+0.8×（12×n+1）÷2×12×n×（r％÷12）]
				 扣除利息税金额＝0.2×A×（12×n+1）÷2×12×n×（r％÷12）
			*/
			if (CalcOption==1)
			{
				TermEndSum=InitSaveSum*(12*SaveYears+0.8*months*(YearRate/12.0));
			}
			else if(CalcOption==2)
			{
				InitSaveSum=TermEndSum*1.0/(12*SaveYears+0.8*months*(YearRate/12.0)); 
			}
			InterestTaxSum=0.2*InitSaveSum*months*(YearRate/12.0);
		}
	}
	else
	if (InitSaveInDate<SecondDate && SecondDate<dtime) // 村 - 2007- 取
	{
		var tDays;
		//ts=dtime-ShresholdDate;
		tDays=GetDayLen(SecondDate,InitSaveInDate);
		tDays1=GetDayLen(dtime,SecondDate);
		if (DepositWay==1)//
			/*
			 * （3）初始存入日期在1999年11月1日之前，初始存入日期+储蓄存期在1999年11月1日之后（含）：
				   已知A，求B：B＝A+ A×n×r％－0.2×A×（初始存入日期+储蓄存期－1999年11月1日）/360×r％
				   其中：（初始存入日期+储蓄存期－1999年11月1日）为天数
				   已知B，求A：A＝B÷[1+n×r％－0.2×（初始存入日期+储蓄存期－1999年11月1日）/360×r％]
				   扣除利息税金额＝0.2×A×（初始存入日期+储蓄存期－1999年11月1日）/360×r％
			 * */
		{
			if (CalcOption==1)
			{
				TermEndSum=InitSaveSum*(1+YearRate*SaveYears-0.2*tDays/360*YearRate-0.05*tDays1/360*YearRate);
			}
			else if(CalcOption==2)
			{
				InitSaveSum=TermEndSum*1.0/(1+YearRate*SaveYears-0.2*tDays/360*YearRate-0.05*tDays1/360*YearRate); 
			}
			InterestTaxSum=0.2*InitSaveSum*tDays/360*YearRate+0.05*InitSaveSum*tDays1/360*YearRate;
		}
		else //
			/*（3）初始存入日期在1999年11月1日之前，初始存入日期+储蓄存期都在1999年11月1日之后（含）：
				 已知A，求B：B＝12×A×n+ A×（12×n+1）÷2×12×n×（r％÷12）－0.2×[A×（12×n+1）÷2×12×n×（r％÷12）]×（初始存入日期+储蓄存期－1999年11月1日）÷（储蓄存期×360）
				 其中：（初始存入日期+储蓄存期－1999年11月1日）、（储蓄存期×360）都为天数。
				 已知B，求A：A＝B÷[12×n+（12×n+1）÷2×12×n×（r％÷12）－0.2×（12×n+1）÷2×12×n×（r％÷12）×（初始存入日期+储蓄存期－1999年11月1日）÷（储蓄存期×360）]
				 扣除利息税金额＝0.2×[A×（12×n+1）÷2×12×n×（r％÷12）]×（初始存入日期+储蓄存期－1999年11月1日）÷（储蓄存期×360）
			 */
		{
			if (CalcOption==1)
			{
				TermEndSum=InitSaveSum*(12*SaveYears+months*(YearRate/12.0)*(1-0.2*tDays/(360*SaveYears)-0.05*tDays1/(360*SaveYears)));
			}
			else if(CalcOption==2)
			{
				InitSaveSum=TermEndSum*1.0/(12*SaveYears+months*(YearRate/12.0)*(1-0.2*tDays/(360*SaveYears)-0.05*tDays1/(360*SaveYears)));
			}  
			InterestTaxSum=0.2*InitSaveSum*months*(YearRate/12.0)*tDays/(360*SaveYears)+0.05*InitSaveSum*months*(YearRate/12.0)*tDays1/(360*SaveYears);
		}
	}
	else
	{
		if (DepositWay==1)
			/*
			 *（2）初始存入日期、初始存入日期+储蓄存期都在1999年11月1日之后（含）：
					已知A，求B：B＝A+A×n×r％×0.8
					已知B，求A：A＝B÷[r％×n×0.8+1]
					扣除利息税金额＝A×n×r％×0.2
			  * */
		{
			if (CalcOption==1)
			{
				TermEndSum=InitSaveSum*(1+YearRate*SaveYears*0.95);
			}
			else if(CalcOption==2)
			{
				InitSaveSum=TermEndSum*1.0/(1+YearRate*SaveYears*0.95); 
			}
			InterestTaxSum=InitSaveSum*YearRate*SaveYears*0.05;
		}
		else
		{
			/*（2）初始存入日期、初始存入日期+储蓄存期都在1999年11月1日之后（含）：
				 已知A，求B：B＝12×A×n+[0.8×A×（12×n+1）÷2×12×n×（r％÷12）]
				 已知B，求A：A＝B÷[12×n+0.8×（12×n+1）÷2×12×n×（r％÷12）]
				 扣除利息税金额＝0.2×A×（12×n+1）÷2×12×n×（r％÷12）
			*/
			if (CalcOption==1)
			{
				TermEndSum=InitSaveSum*(12*SaveYears+0.95*months*(YearRate/12.0));
			}
			else if(CalcOption==2)
			{
				InitSaveSum=TermEndSum*1.0/(12*SaveYears+0.95*months*(YearRate/12.0)); 
			}
			InterestTaxSum=0.05*InitSaveSum*months*(YearRate/12.0);
		}
	}
	
	if (CalcOption==1)
	{
		DepositCalculator.tbTermEndSum.value = NBround(TermEndSum,2);
	}
	else if(CalcOption==2)
	{
	  	DepositCalculator.tbTermEndSum.value = NBround(InitSaveSum,2);
	}
	DepositCalculator.tbInterestTaxSum.value = NBround(InterestTaxSum,2);
}
