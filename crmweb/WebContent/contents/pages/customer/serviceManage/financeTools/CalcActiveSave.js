//活期储蓄计算器
            function computefullsum(oDocument) //计算出实得本息总额和存入总额
			 {
				if (savedatearray.length==0)
				{			   
					var tmparray=new Array();
					tmparray.push(oDocument.all.edSaveSum.value);
					tmparray.push(oDocument.all.beginDateID.value);
					tmparray.push(oDocument.all.endDateID.value);
					tmparray.push(oDocument.all.edFullRate.value);
					var obj=computeoncefullsum(tmparray);
					oDocument.all.edFullSum.value=Round(obj.oncefullsum);
					oDocument.all.edSaveTotal.value=Round(obj.oncesavesum);
					oDocument.all.edTaxSum.value=Round(obj.interesttax);
				}
				else
				{
				   var fullsumtotal=0;
				   var savesumtotal=0;
				   var needinteresttax=0;
				   for (var i=0;i<savedatearray.length;i++)
				    {
						var tmparray=new Array();
						tmparray.push(savesumarray[i]);
						tmparray.push(savedatearray[i]);
						tmparray.push(oDocument.all.endDateID.value);
						tmparray.push(oDocument.all.edFullRate.value);
						var obj=computeoncefullsum(tmparray);
						fullsumtotal+=obj.oncefullsum;
						savesumtotal+=obj.oncesavesum;
						needinteresttax+=obj.interesttax;
					}
						oDocument.all.edFullSum.value=Round(fullsumtotal);
						oDocument.all.edSaveTotal.value=Round(savesumtotal);
						oDocument.all.edTaxSum.value=Round(needinteresttax);
				    
				}
			 }
			 
			 
			 
			 
			 
			 
			 
			 function computeoutdate(oDocument) //计算出提取日期
			 {
				var fullsumtotal=0;
				var savesumtotal=0;
				var lastsavedate="";
				var currinterestsum=0 ;                              //当前的利息和
				var needinteresttax=0 ;                              //要付的利息税
				
				if (savedatearray.length==0) 
				{		
					lastsavedate=oDocument.all.beginDateID.value; ////最后存入日期等于本次存入日期, 字符串	  
					var tmparray=new Array();
					tmparray.push(oDocument.all.edSaveSum.value);
					tmparray.push(oDocument.all.beginDateID.value);
					tmparray.push(lastsavedate); 
					tmparray.push(oDocument.all.edFullRate.value);
					var obj=computeoncefullsum(tmparray);
					fullsumtotal=(obj.oncefullsum);
					savesumtotal=(obj.oncesavesum);
					needinteresttax=(obj.interesttax);
				}
				else
				{							
				   for (var i=0;i<savedatearray.length;i++)
				    {
						lastsavedate=getmaxsavedate(); //返回字符串
						var tmparray=new Array();
						tmparray.push(savesumarray[i]);
						tmparray.push(savedatearray[i]);
						tmparray.push(lastsavedate);
						tmparray.push(oDocument.all.edFullRate.value);
						var obj=computeoncefullsum(tmparray);
						fullsumtotal+=obj.oncefullsum;
						currinterestsum+=obj.interestsum;
						needinteresttax+=obj.interesttax;
						savesumtotal+=obj.oncesavesum;
					}			    
				}
				if (fullsumtotal>=parseFloat(oDocument.all.edFullSum.value))
						oDocument.all.endDateID.value=lastsavedate;
				else
				{
					var currfullsum=parseFloat(oDocument.all.edFullSum.value);               //要取的本息和
					var currfullrate=parseFloat(oDocument.all.edFullRate.value)/100;         //利息
					
					var needinterestsum = currfullsum - savesumtotal;  //要取得的利息和1
					
					var d1 = new Date("1999/11/2");  //第一时间段
                    var d2 = new Date("2007/08/15"); //第二个日期段  
                    var lastdate = new Date(Cal_strtodate(lastsavedate));
					if(lastdate>d2)
					{			    
						  var days = (needinterestsum-currinterestsum)/((savesumtotal*currfullrate/360)*0.95);
						  needinteresttax=needinterestsum/0.95*0.05;
						  datevaild=addday(lastdate,days);
						  
					}else if(lastdate>d1)
					{
						  var tempdays = getSaveTime(lastdate,d2,3);  //取日
						  var tempinterest = savesumtotal*currfullrate/360*tempdays*0.8;

						  if((needinterestsum-currinterestsum)<=tempinterest)
						  {
						    var days = (needinterestsum-currinterestsum)/(savesumtotal*currfullrate/360*0.8);
							needinteresttax=needinterestsum/0.8*0.2;
						    datevaild=addday(lastdate,days);
						  }else
						  {
						     var tempd = (needinterestsum-currinterestsum-tempinterest)/(savesumtotal*currfullrate/360*0.95);
							 needinteresttax=tempinterest/0.8*0.2+(needinterestsum-tempinterest)/0.95*0.05;
						     datevaild=addday(lastdate,(tempdays+tempd));
						  }
					}else
					{
						  var tempdays1 = getSaveTime(lastdate,d1,3);
						  var tempinterest1 = savesumtotal*currfullrate/360*tempdays1;
						  if((needinterestsum-currinterestsum)<=tempinterest1)
						  {
						    var days = (needinterestsum-currinterestsum)/(savesumtotal*currfullrate/360);
							//TODO:needinteresttax=0;
						    datevaild=addday(lastdate,days);
						  }else
						  {
						     var tempinterest2 = savesumtotal*currfullrate/360*getSaveTime(d1,d2,3)*0.8;//1999-11-1---2007-08-14可得利息
						     if((needinterestsum-currinterestsum-tempinterest1)<=tempinterest2)
						     {
						        var tempd2 = (needinterestsum-currinterestsum-tempinterest1)/(savesumtotal*currfullrate/360*0.8);
								//needinteresttax=needinterestsum/0.8*0.2;
                                datevaild=addday(lastdate,(tempdays1+tempd2));
						     }else
						     {
						        var tempd3 = (needinterestsum-currinterestsum-tempinterest1-tempinterest2)/(savesumtotal*currfullrate/360*0.95);
						       // needinteresttax=needinterestsum/0.95*0.05;
								datevaild=addday(lastdate,(tempdays1+getSaveTime(d1,d2,3)+tempd3));
						     }
						     
						   }
					}
				/*	
					var gg=Math.ceil((currfullsum-fullsumtotal)/(fullsumtotal * (currfullrate/360)*(1-interestRate)));
					var objlastsavedate=new Date();
					objlastsavedate.setTime(Cal_strtodate(lastsavedate));
					datevaild=addday(objlastsavedate,gg);*/
					if (datevaild)
						oDocument.all.endDateID.value=lastdate.getFullYear()+"-"+(lastdate.getMonth()+1)+"-"+(lastdate.getDate()+1);
					else
					   {
						DispMessage(oDocument.all.edSaveSum,"计算出来的提取日期不符合实际情况");
						return false;
					   }	
				
				}	
				oDocument.all.edSaveTotal.value=Round(savesumtotal);
				oDocument.all.edTaxSum.value=Round(needinteresttax);
			 }

			 function computeoncefullsum(s) //计算出一次实得本息和存入金额
			 {
					SaveInSum=parseInt(s[0]); //初始存入金额
					var SaveDate=new Date();
					SaveDate.setTime(Cal_strtodate(s[1]));	//开始日期
					var AdvDrawDate=new Date();
					AdvDrawDate.setTime(Cal_strtodate(s[2])); //结束日期
					YearRate=parseFloat(s[3])/100;            //利率
					
					var ShresholdDate=new Date("1999/11/2");  //第一时间段
                    var secondSegment = new Date("2007/08/15"); //第二个日期段
     
                    //如果到期日<1999年11月01日或者（1999年11月01日<=起存日<=2007年08月14日 并且 到期日<=2007年08月14日）或者起存日>2007年08月14日
					if(AdvDrawDate<ShresholdDate)
					{
					    InterestSum = SaveInSum*(getSaveTime(SaveDate,AdvDrawDate,3))*YearRate/360;
					    InterestTaxSum = 0;
					}
					if(ShresholdDate<=SaveDate && SaveDate<=secondSegment && AdvDrawDate<=secondSegment)
					{
						InterestSum = SaveInSum*(getSaveTime(SaveDate,AdvDrawDate,3))*YearRate/360*0.8;
						InterestTaxSum = SaveInSum*(getSaveTime(SaveDate,AdvDrawDate,3))*YearRate/360*0.2;
					}
					if(SaveDate>secondSegment)
					{
						InterestSum = SaveInSum*(getSaveTime(SaveDate,AdvDrawDate,3))*YearRate/360*0.95;
						InterestTaxSum = SaveInSum*(getSaveTime(SaveDate,AdvDrawDate,3))*YearRate/360*0.05;
					}
					
					//如果起存日<1999年11月01日，并且到期日>2007年08月14日【这在实际中不可能出现，不过从算法上是完善的】
					if(SaveDate<ShresholdDate && AdvDrawDate>secondSegment)
					{
						InterestSum = SaveInSum*(getSaveTime(SaveDate,ShresholdDate,3)+getSaveTime(ShresholdDate,secondSegment,3)*0.8+getSaveTime(secondSegment,AdvDrawDate,3)*0.95)*YearRate/360;
						InterestTaxSum = SaveInSum*(getSaveTime(ShresholdDate,secondSegment,3)*0.2+getSaveTime(secondSegment,AdvDrawDate,3)*0.05)*YearRate/360;
					}
					
				    //如果起存日<1999年11月01日，并且1999年11月01日<=到期日<=2007年08月14日
					if(SaveDate<ShresholdDate && ShresholdDate<=AdvDrawDate && AdvDrawDate<=secondSegment)
					{
						InterestSum = SaveInSum*(getSaveTime(SaveDate,ShresholdDate,3)+getSaveTime(ShresholdDate,AdvDrawDate,3)*0.8)*YearRate/360;
						InterestTaxSum = SaveInSum*(getSaveTime(ShresholdDate,AdvDrawDate,3)*0.2)*YearRate/360;
					}
					
					
					//如果1999年11月01日<=起存日<=2007年08月14日，并且到期日>2007年08月14日
					if(AdvDrawDate>secondSegment && ShresholdDate<=SaveDate && SaveDate<=secondSegment)
					{
						InterestSum = SaveInSum*(getSaveTime(SaveDate,secondSegment,3)*0.8+getSaveTime(secondSegment,AdvDrawDate,3)*0.95)*YearRate/360;
						InterestTaxSum = SaveInSum*(getSaveTime(SaveDate,secondSegment,3)*0.2+getSaveTime(secondSegment,AdvDrawDate,3)*0.05)*YearRate/360;
					}
					var obj=new Object();
					obj.oncefullsum=InterestSum + SaveInSum;
					obj.interestsum=InterestSum;
					obj.interesttax=InterestTaxSum;
					obj.oncesavesum=SaveInSum;
					return obj;
			}