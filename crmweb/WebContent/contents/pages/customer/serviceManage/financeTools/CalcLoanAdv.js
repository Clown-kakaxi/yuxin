/*
 * 刘勇     2003.3.19
 *                        提前还款计算器
 * */

var PayList;	

	/// <summary>
	/// 系统计算
	/// </summary>
	/// <returns>0，成功；-1，产生异常</returns>
	 
	function CalcAdvanceExecute(LoanType,LoanSum,LoanYears,InitReturnDate,LoanYearRate,AdvanceReturnDate,IsOnceReturn,PartReturn,DealWay)
	{
		//fnInit();
	/*var LoanType=2;			///贷款类型：1.公积金贷款、2.商业贷款（默认值为商业贷款）int
	var LoanSum=0;		/// 贷款总额：元（正整数） double
	var LoanYears=10;		/// 原贷款期限：年（正整数；默认值为10）int
	var InitReturnDate=new Date();	/// 初始还款日期：年月日	DateTime
	var LoanYearRate=0;	/// 贷款年利率（正小数，“公积金贷款利率表”或“住房贷款利率表”）double
	var AdvanceReturnDate=new Date();/// 提前还款日期：年月日（大于初始还款日期，小于初始还款日期+原贷款期限）DateTime
	var IsOnceReturn=false;	/// 一次性提前还清：是、否（与部分提前还款互斥）bool
	var PartReturn=0;		/// 部分提前还款：元（显示“不含当月应还款额”）（与一次性提前还清互斥）double
	var DealWay=1;			/// 处理方式：1.“减少月还款额，还款年限不变”、2.“缩短还款年限，月还款额不变”int
	*/
		var OriginalMonthReturn=0;/// 系统计算结果:原月还款额：元（正数，保留两位小数）double
		var OriginalEndReturnDate=new Date();	/// 系统计算结果:原最后还款日期：年月DateTime
		var ReturnedSum=0;	/// 系统计算结果:至提前还款上月已还款总额：元（正数，保留两位小数）double
		var ReturnedInterest=0;	/// 系统计算结果:至提前还款上月已还利息额：元（正数，保留两位小数）double

		var ThisMonthTotalAdvancReturn=0;	/// 系统计算结果:提前还款当月还款额（含当月应还款额）： 元double
		var AfterMonthReturn=0;	/// 系统计算结果:提前还款后的月还款额：元double
		var ScantyInterest=0;	/// 系统计算结果:提前还贷节省的利息支出：元（正数，保留两位小数）double

	var EndReturnDate=new Date();	/// 系统计算结果:提前还贷后的最后还款日期：年月DateTime
	
	PayList = new Array();
	
	/// 系统计算结果:根据算法决定信息显示与否！
	/// 信息显示－－－“您预期的部分提前还款额已经足够一次性提前还清剩余本金，可直接进行一次性还清。”
	var Description='';
	var s='您预期的部分提前还款额已经足够一次性提前还清剩余本金，可直接进行一次性还清。';
		
		var result=0,i=0,months=0;//int
		var Banlance=LoanSum;//double
		var TotalInterest=0,PartInterest=0,TotalIn=0,sum=0;//double
		var PaySch;
		var dt = new Date();//DateTime
		//PayList=new Array();
		
		var tmpDT = new Date();
		
		//TimeSpan ts;
		//ts=this.AdvanceReturnDate-this.InitReturnDate;
		tmpDT = new Date(AdvanceReturnDate);
		tmpDT.setMonth(tmpDT.getMonth() - 1);
		dt = new Date(tmpDT);

		OriginalMonthReturn = CalcLoanBalance(Banlance,LoanYears*12,LoanYearRate,1); 
		OriginalMonthReturn=Math.round(OriginalMonthReturn*100)/100;
		
		tmpDT = new Date(InitReturnDate);
		tmpDT.setFullYear(tmpDT.getFullYear() + LoanYears);
		tmpDT.setMonth(tmpDT.getMonth() - 1);
		
		OriginalEndReturnDate = new Date(tmpDT);
		//OriginalEndReturnDate=InitReturnDate.AddYears(LoanYears).AddMonths(-1);
		result=CalcLoanPay(Banlance,new Date(InitReturnDate),LoanYears*12,LoanYearRate,1);
//alert(PayList.length);
		if (IsOnceReturn)
		{
			for (i=0;i<PayList.length;i++)
			{
				PaySch=PayList[i];
				//alert('PayList[i].RateSum = ' + PaySch.RateSum);
				TotalInterest=TotalInterest+PaySch.RateSum;//利息总额
				if (fnConveDate(PaySch.Year)<=fnConveDate(dt))//计算：至提前还款上月已还款总额和至提前还款上月已还利息额												Request change
				{
					ReturnedSum=ReturnedSum+OriginalMonthReturn;//至提前还款上月已还款总额
					ReturnedInterest =ReturnedInterest +PaySch.RateSum;//至提前还款上月已还利息额
				}
				else if ((PaySch.Year.getFullYear()==AdvanceReturnDate.getFullYear())&&(PaySch.Year.getMonth()==AdvanceReturnDate.getMonth()))
				{   
					ThisMonthTotalAdvancReturn=Banlance-(ReturnedSum-ReturnedInterest)+PaySch.RateSum;// 提前还款当月还款额（含当月应还款额）
					AfterMonthReturn=0;//提前还款后月还款额：F＝0
					PartInterest=ReturnedInterest+PaySch.RateSum;//至提前还款当月已还利息额
				}
			}
			ScantyInterest=TotalInterest-PartInterest;//提前还贷节省的利息支出
			EndReturnDate = new Date(AdvanceReturnDate);    //提前还贷后的最后还款期
		}
		else
		{
//alert(result);
			for (i=0;i<PayList.length;i++)
			{
				PaySch=PayList[i];
				TotalInterest=TotalInterest+PaySch.RateSum;//利息总额
//alert(fnConveDate(PaySch.Year) + '~~~' + fnConveDate(dt) + '~~~' + (fnConveDate(PaySch.Year)<=fnConveDate(dt)));
//alert(PaySch.Times + ' || ' + PaySch.Year + ' || ' + PaySch.Corpus + ' || ' + PaySch.RateSum + ' || ' + PaySch.LeavCorpus);
				if (fnConveDate(PaySch.Year)<=fnConveDate(dt))//计算：至提前还款上月已还款总额和至提前还款上月已还利息额												Request change
				{
					ReturnedSum=ReturnedSum+OriginalMonthReturn;//至提前还款上月已还款总额
					ReturnedInterest =ReturnedInterest +PaySch.RateSum;//至提前还款上月已还利息额
				}
				else
				{//alert((PaySch.Year.getFullYear()==AdvanceReturnDate.getFullYear()) + '||' + (PaySch.Year.getMonth()==AdvanceReturnDate.getMonth()))
				 if ((PaySch.Year.getFullYear()==AdvanceReturnDate.getFullYear())&&(PaySch.Year.getMonth()==AdvanceReturnDate.getMonth()))
				{   
					sum=Banlance-(ReturnedSum-ReturnedInterest)+PaySch.RateSum;
					ThisMonthTotalAdvancReturn=OriginalMonthReturn+PartReturn;// 提前还款当月还款额（含当月应还款额）
					PartInterest=ReturnedInterest+PaySch.RateSum;//至提前还款当月已还利息额
					Banlance=PaySch.LeavCorpus-PartReturn;
				}
				}
			}
			
			tmpDT = new Date(InitReturnDate);
			tmpDT.setFullYear(tmpDT.getFullYear() + LoanYears);
			tmpDT.setMonth(tmpDT.getMonth() - 1);
			dt = new Date(tmpDT);
			
			//dt=InitReturnDate.AddYears(LoanYears).AddMonths(-1);
			months=(dt.getFullYear()-AdvanceReturnDate.getFullYear())*12+dt.getMonth()-AdvanceReturnDate.getMonth();
			if (parseInt(DealWay)==1)
			{//alert('w1');
				AfterMonthReturn=CalcLoanBalance(Banlance,months,LoanYearRate,1);//提前还款后月还款额
				
				tmpDT = new Date(AdvanceReturnDate);
				tmpDT.setMonth(tmpDT.getMonth() + 1);
				
				result=CalcLoanPay(Banlance,new Date(tmpDT),months,LoanYearRate,1);
				EndReturnDate = new Date(dt);    //提前还贷后的最后还款期
			}
			else if (parseInt(DealWay)==2)
			{//alert('w2');
				var r,y;//double
				TotalIn=0;
				r=LoanYearRate/12.0;
				AfterMonthReturn=OriginalMonthReturn;
				y=Math.log((AfterMonthReturn/(AfterMonthReturn-Banlance*r)))/Math.log(1+r);//T=Log（1+ r ％）[A÷（A－J×r％）]

				if ((y%1)==0)
					months = parseInt(y);
				else
					months=parseInt(y)+1;
					
				tmpDT = new Date(AdvanceReturnDate);
				tmpDT.setMonth(tmpDT.getMonth() + 1);
				
				result=CalcLoanPay(Banlance,new Date(tmpDT),months,LoanYearRate,1);
				
				tmpDT = new Date(AdvanceReturnDate);
				tmpDT.setMonth(tmpDT.getMonth() + months);
				
				EndReturnDate = new Date(tmpDT);    //提前还贷后的最后还款期
			}
			TotalIn=0;
			for (i=0;i<PayList.length;i++)
			{
				PaySch=PayList[i];
//alert(PaySch.Times + ' || ' + PaySch.Year + ' || ' + PaySch.Corpus + ' || ' + PaySch.RateSum + ' || ' + PaySch.LeavCorpus);
				TotalIn=TotalIn+PaySch.RateSum;
			}
			ScantyInterest=TotalInterest-PartInterest-TotalIn;//提前还贷节省的利息支出
			if (ThisMonthTotalAdvancReturn>=sum)
				Description=s;//显示“您预期的部分提前还款额已经足够一次性提前还清剩余本金，可直接进行一次性还清。”
			else Description='';//不显示
		}
		document.all.OrgMthRet.innerText = NBround(OriginalMonthReturn,2);
		document.all.RetOrgEndRetDate.innerText = fnDateToStr(OriginalEndReturnDate);
		document.all.RetedInterest.innerText = NBround(ReturnedInterest,2);
		document.all.ThisMthTotalAdvRet.innerText = NBround(ThisMonthTotalAdvancReturn,2);
		document.all.RetedSum.innerText = NBround(ReturnedSum,2);
		document.all.AfterMthRet.innerText = NBround(AfterMonthReturn,2);
		document.all.ScantyInterest.innerText = NBround(ScantyInterest,2);
		document.all.EndRetDate.innerText = fnDateToStr(EndReturnDate);
		return result;
	}


	/// <summary>
	/// 等额付款的现金支出（还款计划）数据生成  
	/// </summary>
	/// <param name="LastSum">债务本金（贷款总额）</param>
	/// <param name="BeginYear">债务开始年份（贷款日期）</param>
	/// <param name="times">债务的期限（贷款期限（月份数））</param>
	/// <param name="Rate">债务的年利率</param>
	/// <param name="Freq">债务的偿还频率</param>
	/// <param name="PayList">还款表</param>
	/// <returns></returns>
	function CalcLoanPay(LastSum,BeginYear,times,Rate,Freq)
    {			
		var i=0,result=0;
		var r,M,Inter,C,dtemp;
		var mRec;
		var tmpD = new Date();
		PayList=new Array();
			r=Rate*Freq/12;
			dtemp=Math.pow((1+r),times);
			if (dtemp!=1)
				M=(LastSum*r*dtemp/(dtemp-1));
			else
				M=LastSum/times;
//result = 'r=' + r + ' || dtemp=' + dtemp + ' || M=' + M;
			for (i=0;i<times;i++) 
			{
				Inter=LastSum*r;
				C=M-Inter;
				
				tmpD = new Date(BeginYear);
				tmpD.setMonth(tmpD.getMonth() + i*Freq);
				
				mRec=new TPaySchedule(i+1,new Date(tmpD),C,Inter,LastSum-C);
				LastSum=LastSum-C;
				
				/*mRec.Times=i+1;
				mRec.Year=BeginYear.AddMonths(i*Freq);
				LastSum=LastSum-C;
				mRec.Corpus=C;
				mRec.RateSum=Inter;
				mRec.LeavCorpus=LastSum;
				PayList.Add(mRec);*/
				
				PayList.push(mRec);
			}

		return result;
	}
	
	
	/// <summary>
	/// 计算每期还款金额   
	/// </summary>
	/// <param name="LastSum">债务本金（贷款总额）</param>double
	/// <param name="times">债务的期限（贷款期次）</param>int
	/// <param name="Rate">债务的年利率</param>double
	/// <param name="Freq">债务的偿还频率</param>int
	/// <param name="Balance">返回每期还款金额</param>		删掉,用OriginalMonthReturn代替
	/// <returns></returns>
	function CalcLoanBalance(LastSum,times,Rate,Freq)
	{
		//var result=0;
		var r,dtemp,vResult;

			r=Rate*Freq/12;
			dtemp=Math.pow((1+r),times);
			if (dtemp!=1)
				vResult=LastSum*r*dtemp/(Math.pow((1+r),times)-1);
			else
				vResult=0;

		return vResult;
	}


//定义对象
function TPaySchedule(a1,a2,a3,a4,a5)
{
	/// <summary>
	/// 还款期次
	/// </summary>
	this.Times = a1;
    /// <summary>
    /// 还款时间
    /// </summary>
	this.Year = a2;
	/// <summary>
	/// 偿还的本金
	/// </summary>
	this.Corpus = a3;   
	/// <summary>
	/// 偿还的 利息
	/// </summary>
	this.RateSum = a4;  
	/// <summary>
	/// 剩余的本金
	/// </summary>
	this.LeavCorpus = a5;
}
///////  2003-3-27  刘川庆修改////////////////
///////把一个日期对象转换为该日期当月1号的日期
///////为了比较按月还款列表与当前还款的月份
//////////////////////////////////////////////
    function  fnConvertFirstDate(dt)
    {
        var  tmppD = new Date(dt)
		tmppD = new Date(tmppD.getFullYear(),tmppD.getMonth(),1);
		return(new Date(tmppD));
    }

//把一个带时分秒的日期对象转换为不带时/分/秒的日期对象
	function fnConveDate(pD)
	{
		var tmppD = new Date(pD)
		tmppD = new Date(tmppD.getFullYear(),tmppD.getMonth(),tmppD.getDate());
		
		return(new Date(tmppD));
	}
	
//把一个形如2003-3-20的字符串转换成日期对象
	function fnStrToDate(pStr)
	{
		var i,sY,sM,sD;
		var tmpStr = new String(pStr);
		
		i = tmpStr.indexOf('-',0);
		sY = tmpStr.substring(0,i);

		tmpStr = tmpStr.substr(i+1,tmpStr.length);
		i = tmpStr.indexOf('-',0);
		sM = tmpStr.substring(0,i);

		sD = tmpStr.substring(i+1,tmpStr.length);

		return(new Date(sY,(parseInt(sM)-1),sD));
	}
	
//转换日期数据为字符串
	function fnDateToStr(pDateTime)
	{
		var tmpStr;
		
		tmpStr = pDateTime.getFullYear() + '年' + (pDateTime.getMonth()+1) + '月' + pDateTime.getDate() + '日';
		
		return tmpStr;
	}
	
	
//
//
//
	function CheckData()
	{
		if (!CheckPN(document.all.edLoanSum, "贷款总额请输入正整数", false))				 
			return false;
		if (!CheckPN(document.all.edLoanYears, "原贷款期限请输入正整数", false))
			return false;
		if (!CheckEmpty(document.all.edInitRetDate, "无效的初始还款日期"))
			return false;
		if (!CheckFN3(document.all.edRate, "贷款年利率请输入非负数", true,null,4))
			return false;
		if (!CheckEmpty(document.all.edAdvRetDate, "无效的提前还款日期"))
			return false;
		if (document.all.rbOnce_0.checked)
			if (!CheckFN3(document.all.edPartRet, "部分提前还款请输入正数", false))
				return false;
		return true;
	}
	
		function btnCalClick()
		{
		if ( !CheckData() ) return false;
	var LoanSum=parseInt(document.all.edLoanSum.value);
	var LoanYears=parseInt(document.all.edLoanYears.value);
	var LoanYearRate=parseFloat(document.all.edRate.value) / 100;
	var InitReturnDate=fnStrToDate(document.all.edInitRetDate.value);
	var AdvanceReturnDate=fnStrToDate(document.all.edAdvRetDate.value);
	var LoanType=document.all.edType.selectedIndex+1;
	if (InitReturnDate > AdvanceReturnDate)
	{
		DispMessage(document.all.edAdvRetDate, "部分提前还款日期必须晚于或等于初始还款日期");
		return false;
	}
	var tmpEndReturnDate = new Date(InitReturnDate);
	tmpEndReturnDate.setFullYear(tmpEndReturnDate.getFullYear() + LoanYears);
	tmpEndReturnDate.setMonth(tmpEndReturnDate.getMonth() - 1);
	if (tmpEndReturnDate <= AdvanceReturnDate)
	{
		DispMessage(document.all.edAdvRetDate, "部分提前还款日期必须早于原最后还款日期");
		return false;
	}
	if(document.all.rd1.checked)
	  var DealWay=1;
	else
	  var DealWay=2;	  
	if(document.all.rbOnce_0.checked)
	{
	  var PartReturn=parseInt(document.all.edPartRet.value);
	  var IsOnceReturn=0;
	  if (PartReturn > LoanSum)
	  {
		DispMessage(document.all.edPartRet, "部分提前还款必须小于或等于贷款金额");
		return false;
	  }
	}
	else
	{
	  var PartReturn=0;
	  var IsOnceReturn=1;	  
	}
	

	CalcAdvanceExecute(LoanType,LoanSum,LoanYears,InitReturnDate,LoanYearRate,AdvanceReturnDate,IsOnceReturn,PartReturn,DealWay);

	return true;
	}
	
