////////////////////////////////////////////////////////////////////////////////////////////////
//以下部分编写： 瞿泽辉              日期： 2003年3月
function ChkCZDate(edit)
{
  edit.value=Trim(edit.value);
  if(edit.value=='') 
	return true;
  if(!Cal_datevalid(edit,'1910-1-1','3000-1-1')) 
  {
	alert('日期格式不正确,日期有效范围为1910年到3000年');
	edit.focus();
  }
}

function CheckData()
{   
	var Rate=parseFloat(CompPartSumCalc.edRate.value)/12/100;
	var TotalTerm=parseInt(CompPartSumCalc.edLoanYears.value)*12;
	var SpaceTerm=parseInt(CompPartSumCalc.edSpaceTerm.value);
	var StartTerm=parseInt(CompPartSumCalc.edStartTerm.value);
	var IncSum=CompPartSumCalc.RetType.selectedIndex==0 ? parseInt(CompPartSumCalc.edIncSum.value):-parseInt(CompPartSumCalc.edIncSum.value);
		            
	if (CompPartSumCalc.rd1.checked)
	  if (!CheckPN(CompPartSumCalc.edLoanSum,"贷款金额请输入正整数",false))				 
		return false;
	if (!CheckPN(CompPartSumCalc.edLoanYears,"贷款期限请输入正整数",false))				 
		return false;
	if (!CheckFN3(CompPartSumCalc.edRate,"贷款年利率请输入正数",false,null,4))
		return false;															
	if (!CheckEmpty(CompPartSumCalc.edSRetDate,"无效的开始还款日期"))
		return false;							
	if (!CheckPN(CompPartSumCalc.edStartTerm,"开始变动的期次请输入正整数",false))				 
		return false;					
	if (!CheckPN(document.all.edStartTerm,"开始变动的期次请输入正整数",false))
		return false;										
	if (!CheckPN(CompPartSumCalc.edSpaceTerm,"每次变动间隔期数请输入正整数",false))				 
		return false;										
	if (!CheckPN(CompPartSumCalc.edIncSum,"递增/减金额请输入非负整数",true))				 
		return false;		
	if (CompPartSumCalc.rd2.checked)
		if (!CheckFN3(CompPartSumCalc.edFirstSum,"首次还款金额请输入正数",false))				 
			return false;
			
	if ((StartTerm>TotalTerm)|| (StartTerm<1))
	{
	  DispMessage(edStartTerm,"开始变动的期次在1-"+TotalTerm+"之间");
	  return false;
	}
	if (SpaceTerm>TotalTerm-StartTerm+1)
	{
	  DispMessage(edSpaceTerm,"间隔期次不能大于"+(TotalTerm-StartTerm+1));
	  return false;
	}
	if (CompPartSumCalc.rd1.checked)
	  if (Math.abs(IncSum)>=parseInt(CompPartSumCalc.edLoanSum.value))
	  {
		DispMessage(CompPartSumCalc.edIncSum,"递增/减金额不能大于等于贷款金额");
		return false;
	  }		
		
	return true;
}

  // 计算首次还款  
  //  LoanSum:贷款金额，Rate:贷款月利率=贷款年利率/12；TotalTerm:还款期次还款年限*12，STerm:首次调整还款金额的期数=开始变动的期数-1
  //  SpaceTerm:每次变动间隔时间；IncSum:递增或递减金额； 
  // min,max无用 
  function CaclA(min,max,LoanSum, Rate, TotalTerm, STerm, SpaceTerm, IncSum)//aiai modify 13/6/05
  {
	var n=Math.ceil((TotalTerm-STerm)/SpaceTerm)+1;
	var A1=LoanSum*Rate*Math.pow((1+Rate),TotalTerm);
	//alert(LoanSum+' -- '+Rate+' -- '+ TotalTerm+' -- '+ STerm+' -- '+ SpaceTerm+' -- '+ IncSum+' -- '+n)	
	var A2=IncSum*CaclA2(Rate,TotalTerm,STerm,SpaceTerm,n);
	var B1=Math.pow((1+Rate),TotalTerm)-1;	
	return NBround((A1-A2)/B1,2);
  }
  function CaclA2(i,T,st,s,n)
  {
	var r;
	var ret=0;
	for(r=1;r<n;r++)
	{
	  Pr = st+(r-1)*s;
	  //alert(Math.pow((1+i), (T-Pr)))
	  ret += Math.pow((1+i), (T-Pr));
	}//alert(ret+'  '+n)
	return ret-(n-1);
  }
  
  // 计算总金额     
  //  A:首次还款金额，Rate:贷款月利率=贷款年利率/12；TotalTerm:还款期次还款年限*12，STerm:首次调整还款金额的期数=开始变动的期数-1
  //  SpaceTerm:每次变动间隔时间；IncSum:递增或递减金额；  
  // IsAlert无用
  function CalcM(A, Rate, TotalTerm, STerm, SpaceTerm, IncSum,IsAlert)
  {
	var n=Math.ceil((TotalTerm-STerm)/SpaceTerm)+1;
	var A1=A*(Math.pow((1+Rate),TotalTerm)-1);
	var A2=IncSum*CaclA2(Rate,TotalTerm,STerm,SpaceTerm,n);
	var B1=Rate*Math.pow((1+Rate),TotalTerm);
	return NBround((A1+A2)/B1,2);
  }
  //aiai modify end----
  
function CaclA1(min,max,M,r,n,w,t,G)
{   
var testM,testA=(min+max)/2,starChange=w+1; 
/*
 testM=testACaclM(testA,n,starChange,t,G);
  while ((Math.abs(testA-0)>0.01) && (Math.abs(testM-M)>0.01)  && (min<=max))  //
   {  if (testM<M)   min=testA;
      else max=testA;         
      testA=(min+max)/2;      
      testM=testACaclM(testA,n,starChange,t,G);      
        }
    if (Math.abs(testA-0)>0.01) return ((Math.round((testA)*100))/100)
       else return(0);     
      */
  testM=CalcM(testA, r, n, starChange, t, G, true); 
   while ((Math.abs(testA-0)>0.01) && (Math.abs(testM-M)>0.01)  && (min<=max))  //
   {  if (testM<M)   min=testA;
      else max=testA;         
      testA=(min+max)/2;      
      testM=CalcM(testA, r, n, starChange, t, G, true);
        }
    if (Math.abs(testA-0)>0.01) return ((Math.round((testA)*100))/100)
       else return(0);     
  }
  
function testACaclM(TestA,n,starChan,t1,G) 
//TestA试验的A，n:还款期次还款年限*12，starChan开始变动的期数
//t1:每次变动间隔时间；G递增或递减金额；
{
  var testM=0;  
  for (var i=1;i<=n;i++) 
  {
	//if(TestA+Math.ceil((i-starChan)/t1)*G>0)
    testM+=TestA+Math.ceil((i-starChan)/t1)*G;
  }
  return(testM);
}
 
 function CalcM1(A,r,n,w,t,G,IsAlert)
// 计算总金额     
   //  A:首次还款金额，r:贷款月利率=贷款年利率/12；n:还款期次还款年限*12，w保持首次还款金额的期数=开始变动的期数-1
   //  t:每次变动间隔时间；G递增或递减金额；d=(n-w)/t变动期数，向上取整

{var m0,m1,m3,d,M,i; //M贷款总额,
 d=Math.ceil((n-w)/t);
 m0=Math.pow((1+r),n);
 M=A*((Math.pow((1+r),w)-1)/r)*Math.pow((1+r),n-w);
 m_temp=(Math.pow((1+r),t)-1)/r;
 
 for (i=1;i<=d;i++) 
 {m1=(A+(i*G) )*m_temp;
  
  m3=Math.pow((1+r),(n-w-i*t));
  
  //if (!(m1*m3 < 0 && IsAlert)) // modified by huhao, 2003/4/24
  if (m1*m3 > 0)
	M=M+m1*m3; 
  }
 M=M/m0;
 M=(Math.round(M*100))/100;
 return(M); 
}


////////////////////////////////////////////////////////////////////////////////////////////////////////
function DelTable()  //各期还款表格清空
{ 
	while (document.all.Table5.rows.length > 1) document.all.Table5.deleteRow();
}			   


function CaclEqualSum()           //各期还款
		{  
		
			
			DelTable() ; //表格初始化
				    	   
		    if (!CheckData()) return false;//检查数据的合法性
		   
		    CompPartSumCalc.btnCalc.click();	  
		    
		    var  i,LoanSum=0,LoanTimes=0,ChangeFreq=0,
			FirstPay=0,YearRate=0,ProgressSum=0,TotalSum=0,
			StartChangeTimes=0,AddSum=0,IsCalc=false;
			var NeedReturn = new Number(0);
			j=1;
			
			//变量赋值
			LoanSum =parseFloat(CompPartSumCalc.edLoanSum.value);
		    AddSum=CompPartSumCalc.RetType.selectedIndex==0 ? parseInt(CompPartSumCalc.edIncSum.value):-parseInt(CompPartSumCalc.edIncSum.value) ;
			LoanTimes = parseInt(CompPartSumCalc.edLoanYears.value)*12;
			ChangeFreq = parseInt(CompPartSumCalc.edSpaceTerm.value);
			StartChangeTimes=parseInt(CompPartSumCalc.edStartTerm.value)-1;		
			
			YearRate = parseFloat(CompPartSumCalc.edRate.value) / 100;
			FirstDate=Date.parse(CompPartSumCalc.edSRetDate.value);

			// 共需偿还本息总额，用于是否还完的测试		by huhao, 2003/4/22
			NeedReturn = LoanSum * Math.pow(1 + YearRate / 12, LoanTimes);
			ReLoanBase=new Array();//第i期还款本金数组
			//if (!CompPartSumCalc.rd1.checked) CaclFrist();			
			FirstPay = parseFloat(CompPartSumCalc.edFirstSum.value);				
			ReLoanBase[0]=FirstPay;
			TotalSum=FirstPay;//记录是否在没有到最大期次时已经达到本金额度
			ReLoanIntr=new Array();//第i期还款利息数组
			//if (!CompPartSumCalc.rd1.checked) CaclFrist();			
			ReLoanIntr[0]=0;  //  (YearRate/100/12)*FirstPay;
		
			
			var reString=new String(CompPartSumCalc.edSRetDate.value);

			i=reString.indexOf("-");
		    j=reString.length;
		   
			var yearStr=new String(reString.substring(0,i));			
		   	reString=reString.substring(i+1,j);
		   	i=reString.indexOf("-");
		    j=reString.length;
		    
		    var monStr=new String(reString.substring(0,i));
		    j=reString.length;
		    var dayStr=new String(reString.substring(i+1,j));
		     
			
			ReYear=new Array();//第i期还款日期年数组
			ReMon=new Array();//第i期还款日期月数组
			ReYear[0]=parseInt(yearStr);
			ReMon[0]=parseInt(monStr);
			if(++ReMon[0]>12) { ReMon[0]=1; ReYear[0]++; }
		    
			//计算第i期还款本息、第i期还款日期
			var i=new Number(1);
			while (i<LoanTimes) // ) && (TotalSum<LoanSum)
		{
				if (ReMon[i-1]==12) 
				{   ReYear[i]=ReYear[i-1]+1;
					ReMon[i]=1;
				};
					else			    
				 {    ReMon[i]=ReMon[i-1]+1;
				      ReYear[i]=ReYear[i-1];
				      }
			if ((i>=StartChangeTimes) && (j>=ChangeFreq))
				{ ReLoanBase[i]=parseFloat(ReLoanBase[i-1])+AddSum;				
				j=1}
				else 
				{ReLoanBase[i]=ReLoanBase[i-1];
				j++;}
			ReLoanIntr[i]=(YearRate/100/12)*parseFloat(ReLoanBase[i]);
			//////    (*) added by huhao, 2003/4/22
			if (NeedReturn - (ReLoanBase[i-1]+ReLoanIntr[i-1]) <= 0) 
			{
				ReLoanIntr[i] = NeedReturn/(YearRate/100/12);
				ReLoanBase[i] = NeedReturn - ReLoanIntr[i];
				NeedReturn = 0;
			}
			else NeedReturn -= (ReLoanBase[i]+ReLoanIntr[i]);
			////////// (*)
			TotalSum+=ReLoanBase[i];  //本次已经累计归还本金
				    
				   //生成计算还款表		
				     var TR=document.all.Table5.insertRow();
				     var TD1, TD2, TD3;
				   		     
				     TD1=TR.insertCell();
				     TD1.innerText=i;	    
				     TD2=TR.insertCell();
				     TD2.innerText=ReYear[i-1]+"-"+ReMon[i-1]+"-"+dayStr;
				     TD3=TR.insertCell();				    
				     if ((ReLoanBase[i-1]+ReLoanIntr[i-1])<0)			    
				     TD3.innerText=0
				     else
				     TD3.innerText=Math.round((ReLoanBase[i-1]+ReLoanIntr[i-1])*100)/100;
			         //TR
			         TD1.align='center';
	                 TD2.align='center';
			          TD3.align="right";
			          TD2.style.display="none";//add by aiai
			i++;
			 } // end for  	
			 
			  TR=document.all.Table5.insertRow();
                     TR.style.color = "black";     
				     TD1=TR.insertCell();
				     TD1.innerText=i;	    
				     TD2=TR.insertCell();
				     TD2.innerText=ReYear[i-1]+"-"+ReMon[i-1]+"-"+dayStr;
				     TD3=TR.insertCell();	
				     if ((ReLoanBase[i-1]+ReLoanIntr[i-1])<0)			    
				     TD3.innerText=0
				     else
				     TD3.innerText=Math.round((ReLoanBase[i-1]+ReLoanIntr[i-1])*100)/100;
			         //TR
			         TD1.align='center';
	                 TD2.align='center';
			          TD3.align="right";		 
			          TD2.style.display="none";//add by aiai
			if (i<StartChangeTimes) window.alert("第"+i+"次时已经还本金"+ReLoanBase[i]+"圆"); 
			return true;
	}    