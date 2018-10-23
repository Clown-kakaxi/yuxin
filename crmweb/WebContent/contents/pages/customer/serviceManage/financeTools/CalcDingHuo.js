			function calc1(oDocument)  //计算到期本息和
			{
			    var valstart;
				var valrate;
				var valstartdate;
				var valenddate;
				var valresult;
				var valtax;
				var dayMi=24*60*60*1000;
				var months;
                var interestRate = 0.05; //利息税率
                var days1;
                var days2;
                var days3;
	
		
	     		valstart=parseFloat(oDocument.all.edstart.value); //初始存入金额

				valstartdate=StrToDate(oDocument.all.beginDateID.value);
				
				valenddate=StrToDate(oDocument.all.endDateID.value);
				
				var ShresholdDate=new Date("1999/11/1");  //第一时间段
                var secondSegment = new Date("2007/08/14"); //第二个日期段
								
				valrate=parseFloat(oDocument.all.edRate.value)/100;
			//到期本息总额＝初始存入金额×（年利率/360）×（提取日期－初始存入日期）×(1-利息税率)+初始存入金额
			/*	
				valmonths=getDiffDay(valenddate,valstartdate);
				valresult=valstart*(valrate/360)*valmonths*(1-interestRate)+valstart;
				valtax=valstart*(valrate/360)*valmonths*interestRate;
            */
             //如果到期日<1999年11月01日或者（1999年11月01日<=起存日<=2007年08月14日 并且 到期日<=2007年08月14日）或者起存日>2007年08月14日
             valmonths=getSaveTime(valstartdate,valenddate);
			 var yearret = valmonths;
			 valrate=yearret>1?valrate:valrate/360;
             valmonths=yearret>1?valmonths:valmonths*360; //大于1年按年利率算
             
             if(valenddate<ShresholdDate)
             {
                valresult=valstart*valrate*valmonths+valstart;
				valtax=0;
             }
            if(ShresholdDate<=valstartdate && valstartdate<=secondSegment && valenddate<=secondSegment)
             {
                valresult = valstart*(1+valrate*valmonths*0.8);
                valtax = valstart*valrate*valmonths*0.2;
             }
            if(valstartdate>secondSegment)
            {
               valresult = valstart*(1+valrate*valmonths*0.95);
               valtax = valstart*valrate*valmonths*0.05;
            }
            //如果起存日<1999年11月01日，并且到期日>2007年08月14日【这在实际中不可能出现，不过从算法上是完善的】
            if(valstartdate<ShresholdDate && valenddate>secondSegment)
             {
                days1=yearret>1?getSaveTime(valstartdate,ShresholdDate):getSaveTime(valstartdate,ShresholdDate)*360;
                days2=yearret>1?getSaveTime(ShresholdDate,secondSegment):getSaveTime(ShresholdDate,secondSegment)*360;
                days3=yearret>1?getSaveTime(secondSegment,valenddate):getSaveTime(secondSegment,valenddate)*360;
            
                valresult = valstart*(1+valrate*(days1+days2*0.8+days3*0.95));
                valtax = valstart*valrate*(days2*0.2+days3*0.05);
             }
            //如果起存日<1999年11月01日，并且1999年11月01日<=到期日<=2007年08月14日
            if(valstartdate<ShresholdDate && ShresholdDate<=valenddate && valenddate<=secondSegment)
            {
              days1=yearret>1?getSaveTime(valstartdate,ShresholdDate):getSaveTime(valstartdate,ShresholdDate)*360;
              days2=yearret>1?getSaveTime(ShresholdDate,valenddate):getSaveTime(ShresholdDate,valenddate)*360;
              
              valresult = valstart*(1+valrate*(days1+days2*0.8));
              valtax = valstart*valrate*(days2*0.2);
            } 
             //如果1999年11月01日<=起存日<=2007年08月14日，并且到期日>2007年08月14日
            if(valenddate>secondSegment && ShresholdDate<=valstartdate && valstartdate<=secondSegment)
            {
              days1=yearret>1?getSaveTime(valstartdate,secondSegment):getSaveTime(valstartdate,secondSegment)*360;
              days2=yearret>1?getSaveTime(secondSegment,valenddate):getSaveTime(secondSegment,valenddate)*360;
              
              valresult = valstart*(1+valrate*(days1*0.8+days2*0.95));
              valtax = valstart*valrate*(days1*0.2+days2*0.05);
            }
			   oDocument.all.edend.value=Round(valresult);
			   oDocument.all.edtax.value=Round(valtax);
			}
			
			function calc2(oDocument)
			{
				var valstart;
				var valrate;
				var valstartdate;
				var valenddate;
				var valresult;
				var valtax;
				var dayMi=24*60*60*1000;
				var valmonths;
				var interestRate = 0.05;  //利息税率
				var days1;
                var days2;
                var days3;
	
	            var ShresholdDate=new Date("1999/11/1");  //第一时间段
                var secondSegment = new Date("2007/08/14"); //第二个日期段
                
                
				valstartdate=StrToDate(oDocument.all.beginDateID.value);
				valenddate=StrToDate(oDocument.all.endDateID.value);				
				valrate=parseFloat(oDocument.all.edRate.value)/100;
	     		valresult=parseFloat(oDocument.all.edend.value);		
	     				
				//初始存入金额=到期本息总额/ (1+（年利率/360）×（提取日期－初始存入日期）×（1-利息税率）)
				/*valmonths=getDiffDay(valenddate,valstartdate);
				valstart=valresult /( valrate/360*valmonths*(1-interestRate)+1);
				valtax=(valresult-valstart)/4;*/
				
			 //如果到期日<1999年11月01日或者（1999年11月01日<=起存日<=2007年08月14日 并且 到期日<=2007年08月14日）或者起存日>2007年08月14日
             valmonths=getSaveTime(valstartdate,valenddate);
			 var yearret = valmonths;
             valmonths=yearret>1?valmonths:valmonths*360; //大于1年按年利率算
             valrate=yearret>1?valrate:valrate/360;
             if(valenddate<ShresholdDate)
             {
                valstart=valresult /( valrate*valmonths*+1);
				valtax=(valresult-valstart)/4; //?
             }
            if(ShresholdDate<=valstartdate && valstartdate<=secondSegment && valenddate<=secondSegment)
             {
                valstart = valresult /( 1+valrate*valmonths*0.8);
                valtax = valstart*valrate*valmonths*0.2;
             }
            if(valstartdate>secondSegment)
            {
               valstart = valresult /( 1+valrate*valmonths*0.95);
               valtax = valstart*valrate*valmonths*0.05;
            }
            //如果起存日<1999年11月01日，并且到期日>2007年08月14日【这在实际中不可能出现，不过从算法上是完善的】
            if(valstartdate<ShresholdDate && valenddate>secondSegment)
             {
                days1=yearret>1?getSaveTime(valstartdate,ShresholdDate):getSaveTime(valstartdate,ShresholdDate)*360;
                days2=yearret>1?getSaveTime(ShresholdDate,secondSegment):getSaveTime(ShresholdDate,secondSegment)*360;
                days3=yearret>1?getSaveTime(secondSegment,valenddate):getSaveTime(secondSegment,valenddate)*360;
            
                valstart = valresult /( 1+valrate*(days1+days2*0.8+days3*0.95));
                valtax = (valresult-valstart)/4;
             }
            //如果起存日<1999年11月01日，并且1999年11月01日<=到期日<=2007年08月14日
            if(valstartdate<ShresholdDate && ShresholdDate<=valenddate && valenddate<=secondSegment)
            {
              days1=yearret>1?getSaveTime(valstartdate,ShresholdDate):getSaveTime(valstartdate,ShresholdDate)*360;
              days2=yearret>1?getSaveTime(ShresholdDate,valenddate):getSaveTime(ShresholdDate,valenddate)*360;
              
              valstart = valresult /( 1+valrate*(days1+days2*0.8)); 
              valtax = valstart*valrate*valmonths*0.2;
            } 
             //如果1999年11月01日<=起存日<=2007年08月14日，并且到期日>2007年08月14日
            if(valenddate>secondSegment && ShresholdDate<=valstartdate && valstartdate<=secondSegment)
            {
              days1=yearret>1?getSaveTime(valstartdate,secondSegment):getSaveTime(valstartdate,secondSegment)*360;
              days2=yearret>1?getSaveTime(secondSegment,valenddate):getSaveTime(secondSegment,valenddate)*360;
              
              valstart = valresult /( 1+valrate*(days1*0.8+days2*0.95)); 
              valtax = valstart*valrate*(days1*0.2+days2*0.05)
            }
			    
			    oDocument.all.edstart.value=Round(valstart);
			    oDocument.all.edtax.value=Round(valtax);
			}

			function calc3(oDocument)//算提取日期
			{
				var valrate;
				var taxsum = 0 ;
				var dayMi=24*60*60*1000;
				var valday = 0;
			
			    var ShresholdDate=new Date("1999/11/1");  //第一时间段
                var secondSegment = new Date("2007/08/14"); //第二个日期段
	
				var initsum=parseFloat(oDocument.all.edstart.value); //初始存入金额
				var SaveDate=StrToDate(oDocument.all.beginDateID.value);
						
				valrate=parseFloat(oDocument.all.edRate.value)/100;
	     		var fullsum=parseFloat(oDocument.all.edend.value);	//到期本息和
	     		
	     		var tax = fullsum-initsum;
	     		var flag = false;
	     		var fullsumJ = 0;
	     		var tempmid = tax/(initsum*(valrate/360));
	     		
				//如果开始日期>2007-08-14
				if(SaveDate>secondSegment)
				{
				  days = Math.ceil(tempmid/0.95);
				  flag = addday(SaveDate,days);
				  taxsum =Round(initsum*(valrate/360)* days * 0.05);
				}
				//开始日期>1999-11-1 并且<2007-08-14
				if(SaveDate>ShresholdDate && SaveDate<=secondSegment)
				{
				fullsumJ = initsum*(valrate/360)* getSaveDay(SaveDate,secondSegment)*0.8 + initsum;
				if(fullsumJ>fullsum)
				{
					days = Math.ceil(tempmid/0.8);
					flag = addday(SaveDate,days);
					taxsum =Round(initsum*(valrate/360)* days * 0.2);
				}else
				{
					var d = getSaveDay(SaveDate,secondSegment);
					var d1 = Math.ceil((tempmid-d*0.8)/0.95);
					days = d1 + d;
					flag = addday(SaveDate,days);
					taxsum =Round(initsum*(valrate/360)* (d*0.2+d1*0.05));
				}
				}
		        
				//开始日期<1999-11-1
				if(SaveDate<ShresholdDate)
				{
				fullsumJ = initsum*(valrate/360)* getSaveDay(SaveDate,ShresholdDate) + initsum;
				if(fullsumJ>fullsum)//在1999-11-1内
				{
					days = Math.ceil(tempmid);
					flag = addday(SaveDate,days);
					taxsum = 0;
				}else 
				{
					var d1 = getSaveDay(SaveDate,ShresholdDate);
					fullsumJ = initsum*(valrate/360)* (d1+getSaveDay(SaveDate,secondSegment)*0.8) + initsum;
					if(fullsumJ>fullsum) //只跨了1999-11-1
					{
					var d2 = Math.ceil((tempmid-d)/0.8);
					days = d2 + d1;
					flag = addday(SaveDate,days);
					taxsum =Round(initsum*(valrate/360)* (d2*0.2));
					}else//跨了1999-11-1和2007-08-14
					{
						var d2 = getSaveDay(ShresholdDate,secondSegment);
						var d3 = Math.ceil((tempmid-d-d2*0.8)/0.95);
						days = d1+d2+d3;
						flag = addday(SaveDate,days);
						taxsum =Round(initsum*(valrate/360)* (d2*0.2+d3*0.05));                 
					}
				}
				}
        
                if(flag)
                {
                  oDocument.all.endDateID.value=datetostring(SaveDate) ;
				  oDocument.all.edtax.value=Round(taxsum);
	     		}else
	     		{
	     		  alert("计算出来的提取日期不符合实际情况");
	     		}	
	     		
	     	/*	//提取日期=开始日期+(到期本息总额-初始存入金额)/初始存入金额*360/（1-利息税率）/年利率
				valday=(valresult-valstart)*360/valstart/(1-interestRate)/valrate;
	
		        valday=Math.ceil(valday);
				addday(valstartdate,valday);
				valtax=(valresult-valstart)/4;
				oDocument.all.endDateID.value=datetostring(valstartdate) ;
				
				oDocument.all.edtax.value=Round(valtax);*/

			}

//获得实际的存期(年)
function getSaveTime(startdate,enddate)
{
        //实际年
        var tsYear = enddate.getFullYear()-startdate.getFullYear();
        var tstartMonth = startdate.getMonth();
        var tendMonth = enddate.getMonth();
        var tstartDay = startdate.getDate();
        var tendDay = enddate.getDate();
        if(tendMonth<tstartMonth)
        {
          tsYear--;
          tendMonth += 12;
        }
        //实际月
        var tsMonth = tendMonth-tstartMonth;
        if(tendDay<tstartDay)
        {
          tsMonth--;
          if(tsMonth<0)
          {
            tsYear--;
            tsMonth += 12;
          }
          tendDay += 30;
        }
        //实际日
        var tsDay = tendDay - tstartDay;

        return tsYear+tsMonth/12+tsDay/360;
}


//获得实际的储期（日）
function getSaveDay(startdate,enddate)
{
        //实际年
        var tsYear = enddate.getFullYear()-startdate.getFullYear();
        var tstartMonth = startdate.getMonth();
        var tendMonth = enddate.getMonth();
        var tstartDay = startdate.getDate();
        var tendDay = enddate.getDate();
        if(tendMonth<tstartMonth)
        {
          tsYear--;
          tendMonth += 12;
        }
        //实际月
        var tsMonth = tendMonth-tstartMonth;
        
        if(tendDay<tstartDay)
        {
          tsMonth--;
          if(tsMonth<0)
          {
            tsYear--;
            tsMonth += 12;
          }
          tendDay += 30;
        }
        //实际日
        var tsDay = tendDay - tstartDay;
        return tsYear*360+tsMonth*30+tsDay;
}
