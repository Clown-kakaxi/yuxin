//计算每次支取利息金额
function CalcGetAcc(oDocument)
{
    var QInterest = 0;  //每次支取利息金额---月取
    var InterestAndInit = 0;  //本息和
    var InterestTaxSum = 0 ;      //扣除利息税金额
    var InterestSum  = 0;  //利息和
    
	var initsavesum=parseInt(oDocument.all.edInitSaveSum.value);  //初始存入金额
	var YearRate = oDocument.all.edFullRate.value/100;  //年利率
	var SaveYears=parseInt(document.all.SaveTerm.value)/12; //年为单位--存期
	
	var ShresholdDate=new Date("1999/11/1");  //第一时间段
    var secondSegment = new Date("2007/08/14"); //第二个日期段
	
	var InitSaveInDate = new Date(StrToDate(oDocument.all.beginDateID.value)); //存入日期
    var dtime=new Date();
	//var enddate = new Date(InitSaveInDate.getFullYear(),InitSaveInDate.getMonth(),InitSaveInDate.getDate());
    dtime = new Date(InitSaveInDate.getFullYear(),InitSaveInDate.getMonth(),InitSaveInDate.getDate());
    
    //到期日
	//var enddate = new Date(enddate.setMonth(enddate.getMonth()+SaveYears*12));
    var enddatemid = Date.parse(dtime)+SaveYears*360*1000*3600*24;
	var enddatemid1 = new Date(enddatemid) ;
	var enddate = new Date(enddatemid1.getFullYear(),enddatemid1.getMonth(),enddatemid1.getDate()-1);
	    //如果到期日<1999年11月01日或者（1999年11月01日<=起存日<=2007年08月14日 并且 到期日<=2007年08月14日）或者起存日>2007年08月14日
        if(enddate<ShresholdDate)
        {
           InterestSum = initsavesum*SaveYears*YearRate;
           InterestTaxSum = 0;
        }
		if(ShresholdDate<=dtime && dtime<=secondSegment && enddate<=secondSegment)
		{
			InterestSum = initsavesum*SaveYears*YearRate*0.8;
			InterestTaxSum = initsavesum*SaveYears*YearRate*0.2;
		}
		if(dtime>secondSegment)
		{
			InterestSum = initsavesum*SaveYears*YearRate*0.95;
			InterestTaxSum = initsavesum*SaveYears*YearRate*0.05;
		}
		
		//如果起存日<1999年11月01日，并且到期日>2007年08月14日【这在实际中不可能出现，不过从算法上是完善的】
		if(dtime<ShresholdDate && enddate>secondSegment)
		{
	        InterestSum = initsavesum*(getSaveTime(dtime,ShresholdDate,1)+getSaveTime(ShresholdDate,secondSegment,1)*0.8+getSaveTime(secondSegment,enddate,1)*0.95)*YearRate;
			InterestTaxSum = initsavesum*(getSaveTime(ShresholdDate,secondSegment,1)*0.2+getSaveTime(secondSegment,enddate,1)*0.05)*YearRate;
		}
		
	   //如果起存日<1999年11月01日，并且1999年11月01日<=到期日<=2007年08月14日
		if(dtime<ShresholdDate && ShresholdDate<=enddate && enddate<=secondSegment)
		{
	        InterestSum = initsavesum*(getSaveTime(dtime,ShresholdDate,1)+getSaveTime(ShresholdDate,enddate,1)*0.8)*YearRate;
			InterestTaxSum = initsavesum*(getSaveTime(ShresholdDate,enddate,1)*0.2)*YearRate;
		}
		
		//如果1999年11月01日<=起存日<=2007年08月14日，并且到期日>2007年08月14日
		if(enddate>secondSegment && ShresholdDate<=dtime && dtime<=secondSegment)
		{
	        InterestSum = initsavesum*(getSaveTime(dtime,secondSegment,1)*0.8+getSaveTime(secondSegment,enddate,1)*0.95)*YearRate;
			InterestTaxSum = initsavesum*(getSaveTime(dtime,secondSegment,1)*0.2+getSaveTime(secondSegment,enddate,1)*0.05)*YearRate;
		}
		InterestSum = initsavesum*SaveYears*YearRate;
		
	var capitalsum=InterestSum/(SaveYears*12);
    document.all.edCapitalSum.value=Round(capitalsum);
    document.all.edTaxSum.value=Round(InterestTaxSum);
	document.all.edFullSum.value=Round(parseFloat(initsavesum)+parseFloat(capitalsum)-parseFloat(InterestTaxSum));
}

//计算初始存入金额
function CalcSaveCap(oDocument)
{
    var initsavesum =0;  //初始存入金额
    var InterestAndInit=0 ;  //本息和
    var InterestTaxSum=0 ;      //扣除利息税金额
    var InterestSum=0 ;  //利息和
    
	var InterestSumEvery=parseInt(oDocument.all.edCapitalSum.value);  //每次支取利息
	
	var YearRate = oDocument.all.edFullRate.value/100;  //年利率
	var SaveYears=parseInt(document.all.SaveTerm.value)/12; //年为单位--存期
	
	InterestSum = InterestSumEvery*SaveYears*12;  
	var ShresholdDate=new Date("1999/11/1");  //第一时间段
    var secondSegment = new Date("2007/08/14"); //第二个日期段
	
	var InitSaveInDate = new Date(StrToDate(oDocument.all.beginDateID.value)); //存入日期
    var dtime=new Date();
    dtime = new Date(InitSaveInDate.getFullYear(),InitSaveInDate.getMonth(),InitSaveInDate.getDate());
	//var enddate= new Date(InitSaveInDate.getFullYear(),InitSaveInDate.getMonth(),InitSaveInDate.getDate());
    
    //到期日    
	//var enddate = new Date(enddate.setMonth(enddate.getMonth()+SaveYears*12));
    var enddatemid = Date.parse(dtime)+SaveYears*360*1000*3600*24;
	var enddatemid1 = new Date(enddatemid) ;
	var enddate = new Date(enddatemid1.getFullYear(),enddatemid1.getMonth(),enddatemid1.getDate()-1);

	    initsavesum = InterestSum/(SaveYears*YearRate);
		//如果到期日<1999年11月01日或者（1999年11月01日<=起存日<=2007年08月14日 并且 到期日<=2007年08月14日）或者起存日>2007年08月14日
        if(enddate<ShresholdDate)
        {
           //initsavesum = InterestSum/(SaveYears*YearRate);
           InterestTaxSum = 0;
        }
		if(ShresholdDate<=dtime && dtime<=secondSegment && enddate<=secondSegment)
		{
			//initsavesum = InterestSum/(SaveYears*YearRate*0.8);
			InterestTaxSum = initsavesum*SaveYears*YearRate*0.2;
		}
		if(dtime>secondSegment)
		{
			//initsavesum = InterestSum/(SaveYears*YearRate*0.95);
			InterestTaxSum = initsavesum*SaveYears*YearRate*0.05;
		}
		
		//如果起存日<1999年11月01日，并且到期日>2007年08月14日【这在实际中不可能出现，不过从算法上是完善的】
		if(dtime<ShresholdDate && enddate>secondSegment)
		{
	        //initsavesum = InterestSum/((getSaveTime(dtime,ShresholdDate,1)+getSaveTime(ShresholdDate,secondSegment,1)*0.8+getSaveTime(secondSegment,enddate,1)*0.95)*YearRate);
			InterestTaxSum = initsavesum*(getSaveTime(ShresholdDate,secondSegment,1)*0.2+getSaveTime(secondSegment,enddate,1)*0.05)*YearRate;
		}
		
	   //如果起存日<1999年11月01日，并且1999年11月01日<=到期日<=2007年08月14日
		if(dtime<ShresholdDate && ShresholdDate<=enddate && enddate<=secondSegment)
		{
	       //initsavesum = InterestSum/((getSaveTime(dtime,ShresholdDate,1)+getSaveTime(ShresholdDate,enddate,1)*0.8)*YearRate);
			InterestTaxSum = initsavesum*(getSaveTime(ShresholdDate,enddate,1)*0.2)*YearRate;
		}
		
		
		//如果1999年11月01日<=起存日<=2007年08月14日，并且到期日>2007年08月14日
		if(enddate>secondSegment && ShresholdDate<=dtime&&dtime<=secondSegment)
		{
	        //initsavesum = InterestSum/((getSaveTime(dtime,secondSegment,1)*0.8+getSaveTime(secondSegment,enddate,1)*0.95)*YearRate);
			InterestTaxSum = initsavesum*(getSaveTime(dtime,secondSegment,1)*0.2+getSaveTime(secondSegment,enddate,1)*0.05)*YearRate;
		}
		
    document.all.edInitSaveSum.value=Round(initsavesum);
    document.all.edTaxSum.value=Round(InterestTaxSum);
	document.all.edFullSum.value=Round(parseFloat(initsavesum)+parseFloat(InterestSumEvery)-parseFloat(InterestTaxSum));
}

/*//计算利息税
function CalcTaxofRate(oDocument,fInitsavesum,fTerm)
{
        var interestRate = 0.05;  //利息税率

	var taxsum=fInitsavesum*fTerm*(oDocument.all.edFullRate.value/100)*interestRate;

	return taxsum;
}*/
