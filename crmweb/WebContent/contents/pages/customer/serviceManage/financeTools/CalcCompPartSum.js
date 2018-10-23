var interestRate = 0.05;  //利息税率
function CalCompPartSum(oDocument)
{
	//初始存入金额
	var InitSaveSum = parseInt(CompPartSumCalc.tbInitSaveSum.value);
	//初始存入日期
	var SaveInDate = new Date(CompPartSumCalc.beginDateID.value.replace("-","/"));
	//定期存期
	var SaveTime = parseFloat(CompPartSumCalc.tbSaveTime.options[CompPartSumCalc.tbSaveTime.selectedIndex].value/12);
	//定期利率
	var FullSaveDrawYearRate = parseFloat(CompPartSumCalc.tbFullRate.value)/100;
	//活期利率
	var CurDepositYearRate = parseFloat(CompPartSumCalc.tbDepositRate.value)/100;
	//提前支取金额
	var PartAdvanceDrawSum = parseFloat(CompPartSumCalc.tbAdvDrawSum.value);
	//提前支取日期
	var AdvanceDrawDate = new Date(CompPartSumCalc.periorDateID.value.replace("-","/"));
	var ShresholdDate=new Date("1999/11/1");
	//cal.Calc(initsum,initdate,term,fullrate,partrate,drawsum,drawdate);		
 	var FullDraw=0,PartDraw=0,t=0;
	var dtime=new Date(SaveInDate.getFullYear(),SaveInDate.getMonth(),SaveInDate.getDate());
	//dtime=dtime.setTime(Cal_strtodate(SaveInDate));
	var tDays,tDays2,tDays3;

	tDays=GetDayLen(AdvanceDrawDate,SaveInDate);
	dtime.setMonth(dtime.getMonth() + parseInt(SaveTime)*12);
//	tDays2=GetDayLen(dtime.setMonth(dtime.getMonth() + parseInt(SaveTime)*12),ShresholdDate);
	tDays2=GetDayLen(dtime,ShresholdDate);

	tDays3=GetDayLen(AdvanceDrawDate,ShresholdDate);
	
	if (SaveInDate>=ShresholdDate)
	{
		t=(CurDepositYearRate/360.0)*tDays*(1-interestRate);
		FullDraw=InitSaveSum*t;
		PartDraw=(InitSaveSum-PartAdvanceDrawSum)*FullSaveDrawYearRate*SaveTime*(1-interestRate)+PartAdvanceDrawSum*t;
	}
	else
	{
		t=CurDepositYearRate/360.0;
		PartDraw=(InitSaveSum-PartAdvanceDrawSum)*(FullSaveDrawYearRate*SaveTime-interestRate*(FullSaveDrawYearRate/360.0)*tDays2)+PartAdvanceDrawSum*t*(tDays-tDays3*interestRate);
		FullDraw=InitSaveSum*t*(tDays-interestRate*tDays3);
	}
	PartToFullLoseSum=PartDraw-FullDraw;
	

	CompPartSumCalc.tbLoseSum.value = NBround(PartToFullLoseSum,2);

}