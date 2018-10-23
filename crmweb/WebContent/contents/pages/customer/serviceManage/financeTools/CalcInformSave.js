
//type=0 计算--实得本息总额
function CalcGetSum(oDocument,fFullrate,ytime,dtime)
{
	var initsum;	//提取金额
	var ShresholdDate=new Date("1999/11/1");  //第一时间段
    var secondSegment = new Date("2007/08/14"); //第二个日期段
    
    var fullsum;//本息总和
    var taxsum;//利息税

	initsum=parseInt(oDocument.all.edInitSaveSum.value);				
	
	    //如果到期日<1999年11月01日或者（1999年11月01日<=起存日<=2007年08月14日 并且 到期日<=2007年08月14日）或者起存日>2007年08月14日
        if(ytime<ShresholdDate)
        {
           fullsum = initsum*(fFullrate/360)* getSaveTime(dtime,ytime,3) + initsum;
           taxsum = 0;
        }
      
		if(ShresholdDate<=dtime && dtime<=secondSegment && ytime<=secondSegment)
		{
	       fullsum = initsum*(fFullrate/360)* getSaveTime(dtime,ytime,3)*0.8 + initsum;
           taxsum = initsum*(fFullrate/360)* getSaveTime(dtime,ytime,3)*0.2;   
		}
		if(dtime>secondSegment)
		{
	       fullsum = initsum*(fFullrate/360)* getSaveTime(dtime,ytime,3)*0.95 + initsum;
           taxsum = initsum*(fFullrate/360)* getSaveTime(dtime,ytime,3)*0.05;  
		}
     
     
             
        //如果起存日<1999年11月01日，并且到期日>2007年08月14日【这在实际中不可能出现，不过从算法上是完善的】
		if(dtime<ShresholdDate && ytime>secondSegment)
		{
	       var days1 = getSaveTime(dtime,ShresholdDate,3);
	       var days2 = getSaveTime(ShresholdDate,secondSegment,3);
	       var days3 = getSaveTime(secondSegment,ytime,3);
	      
	       fullsum = initsum*(fFullrate/360)* (days3*0.95+days2*0.8+days1) + initsum;
           taxsum = initsum*(fFullrate/360)* (days2*0.2+days3*0.05); 	       
		}
		
		
		
		
	    //如果起存日<1999年11月01日，并且1999年11月01日<=到期日<=2007年08月14日
		if(dtime<ShresholdDate && ShresholdDate<=ytime && ytime<=secondSegment)
		{
	       var days1 = getSaveTime(dtime,ShresholdDate,3);
	       var days2 = getSaveTime(ShresholdDate,ytime,3);
	       
	       fullsum = initsum*(fFullrate/360)* (days2*0.8+days1) + initsum;
           taxsum = initsum*(fFullrate/360)* (days2*0.2); 
		}
      
        
        
         //如果1999年11月01日<=起存日<=2007年08月14日，并且到期日>2007年08月14日
		if(ytime>secondSegment && ShresholdDate<=dtime && dtime<=secondSegment)
		{
           var days1 = getSaveTime(dtime,secondSegment,3);
           var days2 = getSaveTime(secondSegment,ytime,3);
           fullsum = initsum*(fFullrate/360)* (days2*0.95+days1*0.8) + initsum;
           taxsum = initsum*(fFullrate/360)* (days2*0.05+days1*0.2); 
		}
	      

	/*oDocument.all.edFullSum.value=Round(initsum*(fFullrate/360)* iDiffday * (1-interestRate) + initsum);
	oDocument.all.edTaxSum.value=Round(initsum*(fFullrate/360)* iDiffday * interestRate);*/
	
	oDocument.all.edFullSum.value=Round(fullsum);
	oDocument.all.edTaxSum.value=Round(taxsum);
}


//type=1 计算--提取金额
function CalcSaveSum(oDocument,fFullrate,ytime,dtime)
{
	var fullsum;	//实得本息总额
	var initsum;    //初始金额
	var taxsum;     //利息税

    var ShresholdDate=new Date("1999/11/1");  //第一时间段
    var secondSegment = new Date("2007/08/14"); //第二个日期段
    
	fullsum=parseFloat(oDocument.all.edFullSum.value);

	//如果到期日<1999年11月01日或者（1999年11月01日<=起存日<=2007年08月14日 并且 到期日<=2007年08月14日）或者起存日>2007年08月14日
        if(ytime<ShresholdDate)
        {
           initsum = fullsum/((fFullrate/360)*getSaveTime(dtime,ytime,3) +1);
           taxsum = 0;
        }
      
		if(ShresholdDate<=dtime && dtime<=secondSegment && ytime<=secondSegment)
		{
	       initsum = fullsum/((fFullrate/360)*getSaveTime(dtime,ytime,3)*0.8 +1);
	      
           taxsum = initsum*(fFullrate/360)* getSaveTime(dtime,ytime,3)*0.2;   
		}
		if(dtime>secondSegment)
		{
	        initsum = fullsum/((fFullrate/360)*getSaveTime(dtime,ytime,3)*0.95 +1);
            taxsum = initsum*(fFullrate/360)* getSaveTime(dtime,ytime,3)*0.05;  
		}
     
     
             
        //如果起存日<1999年11月01日，并且到期日>2007年08月14日【这在实际中不可能出现，不过从算法上是完善的】
		if(dtime<ShresholdDate && ytime>secondSegment)
		{
	       var days1 = getSaveTime(dtime,ShresholdDate,3);
	       var days2 = getSaveTime(ShresholdDate,secondSegment,3);
	       var days3 = getSaveTime(secondSegment,ytime,3);
	      
	       initsum = fullsum/((fFullrate/360)*(days3*0.95+days2*0.8+days1) +1);//fullsum = initsum*(fFullrate/360)* (days3*0.95+days2*0.8+days1) + initsum;
           taxsum = initsum*(fFullrate/360)* (days2*0.2+days3*0.05); 	       
		}
		
		
		
		
	    //如果起存日<1999年11月01日，并且1999年11月01日<=到期日<=2007年08月14日
		if(dtime<ShresholdDate && ShresholdDate<=ytime && ytime<=secondSegment)
		{
	       var days1 = getSaveTime(dtime,ShresholdDate,3);
	       var days2 = getSaveTime(ShresholdDate,ytime,3);
	       
	       initsum = fullsum/((fFullrate/360)*(days2*0.8+days1) +1);//fullsum = initsum*(fFullrate/360)* (days2*0.8+days1) + initsum;
           taxsum = initsum*(fFullrate/360)* (days2*0.2); 
		}
      
        
        
         //如果1999年11月01日<=起存日<=2007年08月14日，并且到期日>2007年08月14日
		if(ytime>secondSegment && ShresholdDate<=dtime && dtime<=secondSegment)
		{
           var days1 = getSaveTime(dtime,secondSegment,3);
           var days2 = getSaveTime(secondSegment,ytime,3);
           
           initsum = fullsum/((fFullrate/360)*(days2*0.95+days1*0.8) +1);//fullsum = initsum*(fFullrate/360)* (days2*0.95+days1*0.8) + initsum;
           taxsum = initsum*(fFullrate/360)* (days2*0.05+days1*0.2); 
		}
	
	 oDocument.all.edInitSaveSum.value=Math.round(initsum);
	 oDocument.all.edTaxSum.value=Round(taxsum);
	/*
	oDocument.all.edInitSaveSum.value=Math.round(fullsum/((fFullrate/360)*iDiffday*(1-interestRate) +1));
	
	initsum=parseInt(oDocument.all.edInitSaveSum.value);
	oDocument.all.edTaxSum.value=Round(initsum*(fFullrate/360)*iDiffday*interestRate);*/
}

//type==2  计算--提取日期
function CalcGetDate(document,fFullrate,SaveDate,initsum,fullsum)
{
	var taxsum = 0;
	var ShresholdDate=new Date("1999/11/1");  //第一时间段
    var secondSegment = new Date("2007/08/14"); //第二个日期段	
    var fullsumJ =0;//计算的本息和
    var tax = fullsum-initsum;  //利息
    var days =0;
    var flag =false;
    var tempmid = tax/(initsum*(fFullrate/360));

	    //如果开始日期>2007-08-14
	    if(SaveDate>secondSegment)
        {
           days = Math.ceil(tempmid/0.95);
           flag = addday(SaveDate,days);
           taxsum =Round(initsum*(fFullrate/360)* days * 0.05);
        }
        //开始日期>1999-11-1 并且<2007-08-14
        if(SaveDate>ShresholdDate && SaveDate<=secondSegment)
        {
           fullsumJ = initsum*(fFullrate/360)* getSaveTime(SaveDate,secondSegment,3)*0.8 + initsum;
           if(fullsumJ>fullsum)
           {
             days = Math.ceil(tempmid/0.8);
             flag = addday(SaveDate,days);
             taxsum =Round(initsum*(fFullrate/360)* days * 0.2);
           }else
           {
              var d = getSaveTime(SaveDate,secondSegment,3); 
              var d1 = Math.ceil((tempmid-d*0.8)/0.95);

              days = d1 + d;
              flag = addday(SaveDate,days);
              taxsum =Round(initsum*(fFullrate/360)* (d*0.2+d1*0.05));
           }
        }
        //开始日期<1999-11-1
        if(SaveDate<ShresholdDate)
        {
           fullsumJ = initsum*(fFullrate/360)* getSaveTime(SaveDate,ShresholdDate,3) + initsum;
           if(fullsumJ>fullsum)//在1999-11-1内
           {
             days = Math.ceil(tempmid);
             flag = addday(SaveDate,days);
             taxsum = 0;
           }else 
           {
             var d1 = getSaveTime(SaveDate,ShresholdDate,3);
             fullsumJ = initsum*(fFullrate/360)* (d1+getSaveTime(SaveDate,secondSegment,3)*0.8) + initsum;
             if(fullsumJ>fullsum) //只跨了1999-11-1
             {
               var d2 = Math.ceil((tempmid-d1)/0.8);
               days = d2 + d1;
               flag = addday(SaveDate,days);
               taxsum =Round(initsum*(fFullrate/360)* (d2*0.2));
             }else//跨了1999-11-1和2007-08-14
             {
                var d2 = getSaveTime(ShresholdDate,secondSegment,3);
                var d3 = Math.ceil((tempmid-d-d2*0.8)/0.95);
                days = d1+d2+d3;
                flag = addday(SaveDate,days);
                taxsum =Round(initsum*(fFullrate/360)* (d2*0.2+d3*0.05));                 
             }
           }
        }
      
		
				
	if(flag)
	{
		CompPartSumCalc.drawDateID.value = SaveDate.getFullYear() + "-" + (SaveDate.getMonth()+1) + "-" + SaveDate.getDate();
		document.all.edTaxSum.value = taxsum;
	}else
	{
	   alert("计算出来的提取日期不符合实际情况");
	}
	/*CompPartSumCalc.drawDateID.value = dSaveDate.getFullYear() + "-" + (dSaveDate.getMonth()+1) + "-" + dSaveDate.getDate();
	oDocument.all.edTaxSum.value=Round(iInitsum*(fFullrate/360)* iTemp * interestRate);*/
}



//type=0
/*function CalcGetSum(oDocument,fFullrate,iDiffday)
{
	var initsum;	//提取金额
    var interestRate = 0.05;  //利息税率
	
	initsum=parseInt(oDocument.all.edInitSaveSum.value);				
	
	oDocument.all.edFullSum.value=Round(initsum*(fFullrate/360)* iDiffday * (1-interestRate) + initsum);
	oDocument.all.edTaxSum.value=Round(initsum*(fFullrate/360)* iDiffday * interestRate);
}

//type=1
function CalcSaveSum(oDocument,fFullrate,iDiffday)
{
	var fullsum;	//实得本息总额
	var initsum;
    var interestRate = 0.05;  //利息税率

	fullsum=parseFloat(oDocument.all.edFullSum.value);
	oDocument.all.edInitSaveSum.value=Math.round(fullsum/((fFullrate/360)*iDiffday*(1-interestRate) +1));
	
	initsum=parseInt(oDocument.all.edInitSaveSum.value);
	oDocument.all.edTaxSum.value=Round(initsum*(fFullrate/360)*iDiffday*interestRate);
}

//type==2
function CalcGetDate(oDocument,dSaveDate,iInitsum,fFullrate,iTemp)
{		
    var interestRate = 0.05;  //利息税率			    
	CompPartSumCalc.drawDateID.value = dSaveDate.getFullYear() + "-" + (dSaveDate.getMonth()+1) + "-" + dSaveDate.getDate();
	oDocument.all.edTaxSum.value=Round(iInitsum*(fFullrate/360)* iTemp * interestRate);
}*/