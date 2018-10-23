		
		
			function Calc3(a1,a2,a3)//计算储期
			{
				return Round(a1/(a2*a3));
			}
			
			function getFreq(oDocument)
			{
				if(oDocument.all.rblFreq_0.checked)
					return 1;
				else
					if(oDocument.all.rblFreq_1.checked)
						return 3;
					else
						return 6;
			}
			
			function Calc(oDocument)
			{
				var nc = 12/getFreq(oDocument);//年次数
				var dtime = StrToDate(oDocument.all.beginDateID.value); //开始日期
				var InitSaveSum;//初始存入金额
				var SaveMon ;//储蓄存期
				var enddate = StrToDate(oDocument.all.beginDateID.value);  //到期日
				var ShresholdDate=new Date("1999/11/1");  //第一时间段
                var secondSegment = new Date("2007/08/14"); //第二个日期段
               
				var YearRate = parseFloat(oDocument.all.tbRate.value)/100;
				InitSaveSum = parseInt(oDocument.all.tbFirstSum.value);//初始存入金额或是每次支取金额

				if((!(oDocument.all.rblItem_2.checked)))
				{
					SaveMon = parseInt(oDocument.all.cbMonth.value);  
					//到期日    
					//var enddatemid = Date.parse(dtime)+(SaveMon/12)*360*1000*3600*24;
					//var enddatemid1 = new Date(enddatemid) ;
					//enddate = new Date(enddatemid1.getFullYear(),enddatemid1.getMonth(),enddatemid1.getDate()-1);
					enddate = new Date(enddate.setYear(enddate.getYear()+1));
				}
				
				 if(oDocument.all.rblItem_0.checked)  //计算每次支取金额
				 {
				    var Interest ;//利息
					var InterestTax;//利息税
					 var ZQ = InitSaveSum/(nc*SaveMon/12); //   B/F* N
					
					//如果到期日<1999年11月01日或者（1999年11月01日<=起存日<=2007年08月14日 并且 到期日<=2007年08月14日）或者起存日>2007年08月14日
					//利息=(存期+1) * 存期 / 2 * 月利率
					if(enddate<ShresholdDate)
					{
                       Interest = ZQ*(nc*SaveMon/12+1)/2*(nc*SaveMon/12)*(YearRate/nc);
                       InterestTax = 0;
					}
					if(enddate<=secondSegment)
					{
					   Interest = ZQ*(nc*SaveMon/12+1)/2*(nc*SaveMon/12)*(YearRate/nc);
                       InterestTax = Interest*0.2;
					}
					if(enddate>secondSegment)
					{
						//alert(ZQ+"|"+nc+"|"+SaveMon);
					   Interest = ZQ*(nc*SaveMon/12+1)/2*(nc*SaveMon/12)*(YearRate/nc);
                       InterestTax = Interest*0.05;
					}
					
					
					/*
					//如果起存日<1999年11月01日，并且1999年11月01日<=到期日<=2007年08月14日
					if(dtime<ShresholdDate && ShresholdDate<=enddate && enddate<=secondSegment)
					{
						    //存期A【单位：日】= 1999年11月01日 - 起存日的年月 - 1
							var mA = getSaveTime(dtime,ShresholdDate,2)-1;
							//利息A = 起存金额 * ((总存期 + 1) * 总存期 / 2 - (总存期 - 存期A + 1) * (总存期 - 存期A) / 2) * 月利率
							var InterestA = InitSaveSum*((SaveMon+1)*SaveMon/2-(SaveMon-mA+1)*(SaveMon-mA)/2)*YearRate/12.0;
							var InterestSA = 0;
						    
							//存期B【单位：日】= 30 - 存期的日 + 1
							var mB = 30-dtime.getDay()+1;
							//利息B = 起存金额 * (总存期 - 存期A) * 存期B * 日利率
							var InterestB = InitSaveSum*(SaveMon-mA)*mB*YearRate/12.0/30;
							var InterestSB = 0;
						    
							//存期C【单位：日】= 存期的日 - 1
							var mC = dtime.getDay()-1;
							//利息C = 起存金额 * (总存期 - 存期A) * 存期C * 日利率
							var InterestC = InitSaveSum*(SaveMon-mA)*mC*YearRate/12.0/30;
							//利息税C = 利息C * 20%
							var InterestSC = InterestC*0.2;
						    
							//存期D【单位：月】 = 到期日的年月 - 1999年11月
							var mD = getSaveTime(ShresholdDate,enddate,2);
							//利息D = 起存金额 * (存期D + 1) * 存期D / 2 * 月利率
							var InterestD = InitSaveSum*(mD+1)*mD/2*YearRate/12.0;
							//利息税D = 利息D * 20%
							var InterestSD = InterestD * 0.2;
						    
						    Interest = InterestA + InterestB + InterestC + InterestD;
                            InterestTax = InterestSC + InterestSD;					
					}
					//如果1999年11月01日<=起存日<=2007年08月14日，并且到期日>2007年08月14日
					if(enddate>secondSegment && ShresholdDate<=dtime && dtime<=secondSegment)
					{
						    //存期A【单位：月】= 
							//起存日的日>14，2007年08月 - 起存日的年月 – 1
							//起存日的日<=14，2007年08月 – 起存日的年月
							var mA ;
							if(dtime.getDay()>14)
							{
							mA = getSaveTime(dtime,secondSegment,2)-1;
							}else
							{
							mA = getSaveTime(dtime,secondSegment,2);
							}
							//利息A = 起存金额 * ((总存期 + 1) * 总存期 / 2 - (总存期 - 存期A + 1) * (总存期 - 存期A) / 2) * 月利率
							var InterestA = InitSaveSum*((SaveMon+1)*SaveMon/2-(SaveMon-mA+1)*(SaveMon-mA)/2)*YearRate/12.0;
							//利息税A = 利息税A * 20%
							var InterestSA = InterestA*0.2;
						    
							//存期B【单位：日】= 
							//起存日的日>14，44 - 存期的日 + 1
							//起存日的日<=14，14 – 存期的日 + 1
							var mB;
							if(dtime.getDay()>14)
							{
							mB = 44-dtime.getDay()+1;
							}else
							{
							mB = 14-dtime.getDay()+1;
							}
							//利息B = 起存金额 * (总存期 - 存期A) * 存期B * 日利率
							var InterestB = InitSaveSum*(SaveMon-mA)*mB*YearRate/12.0/30;
							//利息税B = 利息税B * 20%
							var InterestSB = InterestB*0.2;
						    
							//存期C【单位：日】=
							//起存日的日>14，存期的日 – 15
							//起存日的日<=14，存期的日 + 30 – 15
							var mC ;
							if(dtime.getDay()>14)
							{
							mC = dtime.getDay()-15;
							}else
							{
							mC = dtime.getDay()+30-15;
							}
							//利息C = 起存金额 * (总存期 - 存期A) * 存期C * 日利率
							var InterestC = InitSaveSum*(SaveMon-mA)*mC*YearRate/12.0/30;
							//利息税C = 利息C * 5%
							var InterestSC = InterestC*0.05;
						    
							//存期D【单位：月】= 
							//起存日的日>14，到期日的年月 - 2007年08月
							//起存日的日<=14，到期日的年月 - 2007年08月 - 1
							var mD;
							if(dtime.getDay()>14)
							{
							mD = getSaveTime(secondSegment,enddate,2);
							}else
							{
							mD = getSaveTime(secondSegment,enddate,2)-1;
							}
							//利息D = 起存金额 * (存期D + 1) * 存期D / 2 * 月利率
							var InterestD = InitSaveSum*(mD+1)*mD/2*YearRate/12.0;
							//利息税D = 利息D * 5%
							var InterestSD = InterestD*0.05;
						    
						    Interest = InterestA + InterestB + InterestC + InterestD;
                            InterestTax = InterestSA + InterestSB + InterestSC + InterestSD;	
					}
		            //如果起存日<1999年11月01日，并且到期日>2007年08月14日【这在实际中不可能出现，不过从算法上是完善的】
					if(dtime<ShresholdDate && enddate>secondSegment)
					{
						    //存期A【单位：日】= 1999年11月01日 - 起存日的年月 - 1
							var mA = getSaveTime(dtime,ShresholdDate,2)-1;
							//利息A = 起存金额 * ((总存期 + 1) * 总存期 / 2 - (总存期 - 存期A + 1) * (总存期 - 存期A) / 2) * 月利率
							var InterestA = InitSaveSum*((SaveMon+1)*SaveMon/2-(SaveMon-mA+1)*(SaveMon-mA)/2)*YearRate/12.0;
							var InterestSA = 0;
						    
							//存期B【单位：日】= 30 - 存期的日 + 1
							var mB = 30-dtime.getDay()+1;
							//利息B = 起存金额 * (总存期 - 存期A) * 存期B * 日利率
							var InterestB = InitSaveSum*(SaveMon-mA)*mB*YearRate/12.0/30;
							var InterestSB = 0;
						    
							//存期C【单位：日】= 存期的日 - 1
							var mC = dtime.getDay()-1;
							//利息C = 起存金额 * (总存期 - 存期A) * 存期C * 日利率
							var InterestC = InitSaveSum*(SaveMon-mA)*mC*YearRate/12.0/30;
							//利息税C = 利息C * 20%
							var InterestSC = InterestC*0.2;
						    
							//存期D【单位：月】= 
							//起存日的日>14，2007年08月 – 1999年11月 – 1
							//起存日的日<=14，2007年08月 – 1999年11月
							var mD ;
							if(dtime.getDay()>14)
							{
							mD = getSaveTime(ShresholdDate,secondSegment,2)-1;
							}else
							{
							mD = getSaveTime(ShresholdDate,secondSegment,2);
							}
							//利息D = 起存金额 * ((总存期 - 存期A) * 存期D + (存期D + 1) * 存期D / 2) * 月利率
							var InterestD = InitSaveSum*((SaveMon-mA)*mD+(mD+1)*mD/2)*YearRate/12.0;
							//利息税D = 利息税D * 20%
							var InterestSD = InterestD*0.2;
						    
							//存期E【单位：日】= 
							//起存日的日>14，44 - 存期的日 + 1
							//起存日的日<=14，14 – 存期的日 + 1
							var mE ;
							if(dtime.getDay()>14)
							{
							mE = 44-dtime.getDay()+1;
							}else
							{
							mE = 14-dtime.getDay()+1;
							}
							//利息E = 起存金额 * (总存期 - 存期A - 1 - 存期D) * 存期E * 日利率
							var InterestE = InitSaveSum*(SaveMon-mA-1-mD)*mE*YearRate/12.0/30;
							//利息税E = 利息税B * 20%
							var InterestSE = InterestE*0.2;
						    
							//存期F【单位：日】= 
							//起存日的日>14，存期的日 – 15
							//起存日的日<=14，存期的日 + 30 – 15
							var mF ;
							if(dtime.getDay()>14)
							{
							mF = dtime.getDay()-15;
							}else
							{
							mF = dtime.getDay()+30-15;
							}
							//利息F = 起存金额 * (总存期 - 存期A - 1 - 存期D) * 存期F * 日利率
							var InterestF = InitSaveSum*(SaveMon-mA-1-mD)*mF*YearRate/12.0/30;
							//利息税F = 利息F * 5%
							var InterestSF = InterestF*0.05;
						    
							//存期G【单位：月】= 
							//起存日的日>14，到期日的年月 - 2007年08月
							//起存日的日<=14，到期日的年月 - 2007年08月 - 1
							var mG ;
							if(dtime.getDay()>14)
							{
							   mG = getSaveTime(secondSegment,enddate,2);
							}else
							{
							   mG = getSaveTime(secondSegment,enddate,2)-1;
							}
							//利息G = 起存金额 * (存期G + 1) * 存期G / 2 * 月利率
							var InterestG = InitSaveSum*((mG+1)*mG/2)*YearRate/12.0;
							//利息税G = 利息G * 5%
							var InterestSG = InterestG*0.05;
						    
						    Interest = InterestA + InterestB + InterestC + InterestD + InterestE + InterestF + InterestG;
                            InterestTax = InterestSA + InterestSB + InterestSC + InterestSD + InterestSE + InterestSF + InterestSG ;						    
					}
					*/
					
					  
                       oDocument.all.tbEverySum.value=Round(ZQ);
                       oDocument.all.tbInSum.value=Round(Interest-InterestTax);
					   oDocument.all.tbTaxSum.value=Round(InterestTax);
				 }
				
				if(oDocument.all.rblItem_1.checked)  //计算初始存入金额
				{
				    var QInSum = InitSaveSum*(SaveMon/12)*nc;//支取金额总数
                    var initJinE ;  //初始存入金额
                    var InterestTax;  //利息税
					//如果到期日<1999年11月01日或者（1999年11月01日<=起存日<=2007年08月14日 并且 到期日<=2007年08月14日）或者起存日>2007年08月14日
					//利息=(存期+1) * 存期 / 2 * 月利率  X＝A×（F×N+1）/2×（F×N）×（R/F）
					if(enddate<ShresholdDate)
					{
                       initJinE = QInSum/(1+(SaveMon+1)*SaveMon/2*(YearRate/12));
                       InterestTax = 0;
					}
					if(enddate<=secondSegment)
					{
					   initJinE = QInSum/(1+(SaveMon+1)*SaveMon/2*(YearRate/12)*0.8);
                       InterestTax = initJinE*(SaveMon+1)*SaveMon/2*(YearRate/12)*0.2;
					}
					if(enddate>secondSegment)
					{
						initJinE = InitSaveSum*(nc*SaveMon/12+1)/2*(nc*SaveMon/12)*(YearRate/nc);
						InterestTax = initJinE*0.05;
					}
					
					
					/*
					//如果起存日<1999年11月01日，并且1999年11月01日<=到期日<=2007年08月14日
					if(dtime<ShresholdDate && ShresholdDate<=enddate && enddate<=secondSegment)
					{
						    //存期A【单位：日】= 1999年11月01日 - 起存日的年月 - 1
							var mA = getSaveTime(dtime,ShresholdDate,2)-1;
						    
							//存期B【单位：日】= 30 - 存期的日 + 1
							var mB = 30-dtime.getDay()+1;
						    
							//存期C【单位：日】= 存期的日 - 1
							var mC = dtime.getDay()-1;
						    
							//存期D【单位：月】 = 到期日的年月 - 1999年11月
							var mD = getSaveTime(ShresholdDate,enddate,2);
						    
						    initJinE = QInSum/(1+(((SaveMon+1)*SaveMon/2-(SaveMon-mA+1)*(SaveMon-mA)/2)*YearRate/12.0+(SaveMon-mA)*mB*YearRate/12.0/30+(SaveMon-mA)*mC*YearRate/12.0/30*0.8+(mD+1)*mD/2*YearRate/12.0*0.8));
                            InterestTax = initJinE*((SaveMon-mA)*mC*YearRate/12.0/30+(mD+1)*mD/2*YearRate/12.0)*0.2;
					}
					
					
					//如果1999年11月01日<=起存日<=2007年08月14日，并且到期日>2007年08月14日
					if(enddate>secondSegment && ShresholdDate<=dtime && dtime<=secondSegment)
					{
						    //存期A【单位：月】= 
							//起存日的日>14，2007年08月 - 起存日的年月 – 1
							//起存日的日<=14，2007年08月 – 起存日的年月
							var mA ;
							if(dtime.getDay()>14)
							{
							mA = getSaveTime(dtime,secondSegment,2)-1;
							}else
							{
							mA = getSaveTime(dtime,secondSegment,2);
							}
						    
							//存期B【单位：日】= 
							//起存日的日>14，44 - 存期的日 + 1
							//起存日的日<=14，14 – 存期的日 + 1
							var mB;
							if(dtime.getDay()>14)
							{
							mB = 44-dtime.getDay()+1;
							}else
							{
							mB = 14-dtime.getDay()+1;
							}
						    
							//存期C【单位：日】=
							//起存日的日>14，存期的日 – 15
							//起存日的日<=14，存期的日 + 30 – 15
							var mC ;
							if(dtime.getDay()>14)
							{
							mC = dtime.getDay()-15;
							}else
							{
							mC = dtime.getDay()+30-15;
							}
						    
							//存期D【单位：月】= 
							//起存日的日>14，到期日的年月 - 2007年08月
							//起存日的日<=14，到期日的年月 - 2007年08月 - 1
							var mD;
							if(dtime.getDay()>14)
							{
							   mD = getSaveTime(secondSegment,enddate,2);
							}else
							{
							   mD = getSaveTime(secondSegment,enddate,2)-1;
							}
						    
						    initJinE = QInSum/(1+(((SaveMon+1)*SaveMon/2-(SaveMon-mA+1)*(SaveMon-mA)/2)*YearRate/12.0*0.8+(SaveMon-mA)*mB*YearRate/12.0/30*0.8+(SaveMon-mA)*mC*YearRate/12.0/30*0.95+(mD+1)*mD/2*YearRate/12.0*0.95));
                            InterestTax = initJinE*(((SaveMon+1)*SaveMon/2-(SaveMon-mA+1)*(SaveMon-mA)/2)*YearRate/12.0*0.2+(SaveMon-mA)*mB*YearRate/12.0/30*0.2+(SaveMon-mA)*mC*YearRate/12.0/30*0.05+(mD+1)*mD/2*YearRate/12.0*0.05);
					}
					
		            //如果起存日<1999年11月01日，并且到期日>2007年08月14日【这在实际中不可能出现，不过从算法上是完善的】
					if(dtime<ShresholdDate && enddate>secondSegment)
					{
						    //存期A【单位：日】= 1999年11月01日 - 起存日的年月 - 1
							var mA = getSaveTime(dtime,ShresholdDate,2)-1;
						    
							//存期B【单位：日】= 30 - 存期的日 + 1
							var mB = 30-dtime.getDay()+1;
						    
							//存期C【单位：日】= 存期的日 - 1
							var mC = dtime.getDay()-1;
						    
							//存期D【单位：月】= 
							//起存日的日>14，2007年08月 – 1999年11月 – 1
							//起存日的日<=14，2007年08月 – 1999年11月
							var mD ;
							if(dtime.getDay()>14)
							{
							mD = getSaveTime(ShresholdDate,secondSegment,2)-1;
							}else
							{
							mD = getSaveTime(ShresholdDate,secondSegment,2);
							}
						    
							//存期E【单位：日】= 
							//起存日的日>14，44 - 存期的日 + 1
							//起存日的日<=14，14 – 存期的日 + 1
							var mE ;
							if(dtime.getDay()>14)
							{
							mE = 44-dtime.getDay()+1;
							}else
							{
							mE = 14-dtime.getDay()+1;
							}
						    
							//存期F【单位：日】= 
							//起存日的日>14，存期的日 – 15
							//起存日的日<=14，存期的日 + 30 – 15
							var mF ;
							if(dtime.getDay()>14)
							{
							mF = dtime.getDay()-15;
							}else
							{
							mF = dtime.getDay()+30-15;
							}
						    
							//存期G【单位：月】= 
							//起存日的日>14，到期日的年月 - 2007年08月
							//起存日的日<=14，到期日的年月 - 2007年08月 - 1
							var mG ;
							if(dtime.getDay()>14)
							{
							   mG = getSaveTime(secondSegment,enddate,2);
							}else
							{
							   mG = getSaveTime(secondSegment,enddate,2)-1;
							}
						    initJinE = QInSum/(1+(((SaveMon+1)*SaveMon/2-(SaveMon-mA+1)*(SaveMon-mA)/2)*YearRate/12.0+(SaveMon-mA)*mB*YearRate/12.0/30+(SaveMon-mA)*mC*YearRate/12.0/30*0.8+((SaveMon-mA)*mD+(mD+1)*mD/2)*YearRate/12.0*0.8+(SaveMon-mA-1-mD)*mE*YearRate/12.0/30*0.8+(SaveMon-mA-1-mD)*mF*YearRate/12.0/30*0.95+((mG+1)*mG/2)*YearRate/12.0*0.95));
                            InterestTax = initJinE*((SaveMon-mA)*mC*YearRate/12.0/30*0.2+((SaveMon-mA)*mD+(mD+1)*mD/2)*YearRate/12.0*0.2+(SaveMon-mA-1-mD)*mE*YearRate/12.0/30*0.2+(SaveMon-mA-1-mD)*mF*YearRate/12.0/30*0.05+((mG+1)*mG/2)*YearRate/12.0*0.05);						    
					}
					*/
					
                       oDocument.all.tbEverySum.value=Round(QInSum);
                       oDocument.all.tbInSum.value=Round(initJinE-InterestTax);
					   oDocument.all.tbTaxSum.value=Round(InterestTax);
				}
				
               
              if(oDocument.all.rblItem_2.checked)  //计算储蓄存期
               {
                  		var Month = 12*Calc3(parseInt(oDocument.all.tbFirstSum.value),parseFloat(oDocument.all.tbEverySum.value),nc);
						var x = getTerm(Month);
						if(x < 0)
						{
							msg="计算得出的储蓄存期为"+Round(Month/12)+"年，不符合银行储蓄的实际情况。";
							DispMessage(oDocument.all.tbFirstSum,msg);
							return;
						}
						//oDocument.all.cbMonth.selectedIndex=getTerm(Month);
						msg="计算得出的储蓄存期为"+oDocument.all.cbMonth.options[x].text+"。";
						alert(msg);  
               }
				
	
				
			/*	if(oDocument.all.rblItem_0.checked)  //计算每次支取金额
				{
					
				}
				else
				{
					if(oDocument.all.rblItem_1.checked)  //计算初始存入金额
					{

						
					}
					else  //计算储蓄存期
					{
						//Tax=CalcTax(parseFloat(oDocument.all.tbEverySum.value),nc,parseInt(oDocument.all.cbMonth.value)/12,parseFloat(oDocument.all.tbRate.value)/100);
						Tax=CalcTax(parseFloat(oDocument.all.tbEverySum.value),nc,parseInt(oDocument.all.cbMonth.value)/12,parseFloat(oDocument.all.tbRate.value)/100,begindate);
						TaxYinFu=CalcTaxYF(parseFloat(oDocument.all.tbEverySum.value),nc,parseInt(oDocument.all.cbMonth.value)/12,parseFloat(oDocument.all.tbRate.value)/100,begindate);
						
						//oDocument.all.tbInSum.value=Round(Tax*(1-interestRate));
						//oDocument.all.tbTaxSum.value=Round(Tax*interestRate);					
						oDocument.all.tbInSum.value=Round(Tax);
						oDocument.all.tbTaxSum.value=Round(TaxYinFu);
						
						var Month = 12*Calc3(parseInt(oDocument.all.tbFirstSum.value),parseFloat(oDocument.all.tbEverySum.value),nc);
						var x = getTerm(Month);
						if(x < 0)
						{
							msg="计算得出的储蓄存期为"+Round(Month/12)+"年，不符合银行储蓄的实际情况。";
							DispMessage(oDocument.all.tbFirstSum,msg);
							return;
						}
						//oDocument.all.cbMonth.selectedIndex=getTerm(Month);
						msg="计算得出的储蓄存期为"+oDocument.all.cbMonth.options[x].text+"。";
						alert(msg);
					}
				}*/
			}
