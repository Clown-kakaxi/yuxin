// 等比累进还款计算器

//计算等比累进还款法的贷款金额
  /*
  PerSum :改变还款金额前每月还款额
  Rate  ：贷款月利率，等于（贷款年利率/12）；
  TotalTerm  ：贷款总期次，等于（贷款年限×12）
  SpaceTerm  ：每次变动间隔期数；
  StartTerm  : 开始变动的期次
  AdjRate ： 初次调整还款金额后与调整前比率； */
function ERateLoanSum(PerSum, Rate, TotalTerm, SpaceTerm, StartTerm, AdjRate) //算贷款金额
{
	var FirstTerm = StartTerm - 1;
	var ChgTerm = Math.ceil((TotalTerm - FirstTerm) / SpaceTerm);
	var Sum = 0;
	var i;
	for ( i = 0; i <= ChgTerm; i++ )
	{
		if ( i == 0 )
			Sum = Sum + PerSum * (Math.pow((1 + Rate), FirstTerm) - 1) / Rate * (1 / Math.pow((1 + Rate), FirstTerm))
		else
			Sum = Sum + PerSum * Math.pow(AdjRate, i) * (Math.pow((1 + Rate), SpaceTerm) - 1) / Rate * (1 / Math.pow(1 + Rate, FirstTerm + i * SpaceTerm));
	}
	return Sum;
}

//等比累进还款法的黄金分割法
  /*
  Rate r ：贷款月利率，等于（贷款年利率/12）；
  TotalTerm n ：还款期次，等于（贷款年限×12）
  SpaceTerm t ：每次变动间隔期数；
  StartTerm s: 开始变动的期次
	IncSum g ：递增金额（正数），或是递减金额（负数）；
  BPoint,EPoint 搜索范围
  LoanSum m :贷款金额 */
function ERateGolden(Rate, TotalTerm, SpaceTerm, StartTerm, AdjRate, BPoint, EPoint, LoanSum)
{
	var a1,b1,c,y1,y2,y;
	var i;
	if ( BPoint > EPoint )	{	c = EPoint; EPoint = BPoint; BPoint = c;	}
	for ( i = 0; i <= 100; i++ )
	{
	    b1 = BPoint + 0.618 * (EPoint - BPoint);
		a1 = EPoint - 0.618 * (EPoint - BPoint);
		y1 = Math.abs(ERateLoanSum(a1, Rate, TotalTerm, SpaceTerm, StartTerm, AdjRate) - LoanSum);
		y2 = Math.abs(ERateLoanSum(b1, Rate, TotalTerm, SpaceTerm, StartTerm, AdjRate) - LoanSum);
		if ( y1 > y2 ) BPoint = a1;
		else EPoint = b1;
	    c = (BPoint + EPoint) / 2;
		y = Math.abs(ERateLoanSum(c, Rate, TotalTerm, SpaceTerm, StartTerm, AdjRate) - LoanSum);
	    if ( y < 0.01 ) break; //误差不超过0.01
	}
	
	return c;
}

//计算等比累进还款法的改变还款金额前每月还款额
/*
  LoanSum :贷款金额
  Rate ：贷款月利率，等于（贷款年利率/12）；
  TotalTerm ：还款期次，等于（贷款年限×12）
  SpaceTerm ：每次变动间隔期数；
  StartTerm : 开始变动的期次
	IncSum ：递增金额（正数），或是递减金额（负数）； */
function ERateLoanPersum1(LoanSum, Rate, TotalTerm, SpaceTerm, StartTerm, AdjRate)
{
	var Persum = 0;
	var BPoint = LoanSum / TotalTerm;
	var j = 0;
	var Temp = ERateLoanSum(BPoint, Rate, TotalTerm, SpaceTerm, StartTerm, AdjRate);
	var EPoint = BPoint;
	if ( Temp > LoanSum )
	{
		do
		{
			EPoint = EPoint / 2;
			Temp = ERateLoanSum(EPoint, Rate, TotalTerm, SpaceTerm, StartTerm, AdjRate);
			j++ ;
		}while ( Temp > LoanSum && j < 100 );
		if ( Temp === LoanSum ) Persum = EPoint;
	}
	else if ( Temp < LoanSum )
	{
		do
		{
			EPoint = EPoint * 2;
			Temp = ERateLoanSum(EPoint, Rate, TotalTerm,SpaceTerm,StartTerm,AdjRate);
			j++ ;
		}while ( Temp < LoanSum && j < 100 );
		if ( Temp === LoanSum ) Persum = EPoint;
	}
	else
	{
		Persum = BPoint;
	}
	if ( Persum != 0 ) return Persum;
	Persum = ERateGolden(Rate, TotalTerm, SpaceTerm, StartTerm, AdjRate, BPoint, EPoint, LoanSum);
	return Persum;
}

//计算等比累进还款法的还款表
/*
  LoanSum :贷款金额
  FirstSum :首次还款金额（a）
  Rate ：贷款月利率，等于（贷款年利率/12）；
  TotalTerm ：还款期次，等于（贷款年限×12）
  SpaceTerm ：每次变动间隔期数；
  StartTerm : 开始变动的期次
  FirstDate : 开始还款日期
  AdjRate ：初次调整还款金额后与调整前比率； */
function ERateLoanReturnList(oXMLDoc, LoanSum, FirstSum, Rate, TotalTerm, SpaceTerm, StartTerm, FirstDate, AdjRate)
{
	var Sum = 0.0;
	var TotalSum = 0;
	var Corpus = 0;
	var i, m;
	var root = oXMLDoc.documentElement;
	var note;

	//清空xml中的数据
	m = root.childNodes.length;
	
	for (i=0;i<m;i++)
	{
		node = root.childNodes.item(0);
		root.removeChild(node);
	}	
	
	if ( FirstDate === 0 ) FirstDate = new Date();
	FirstSum = new Number(FirstSum);
	for ( i = 1; i <=TotalTerm; i++ )
	{
		if ( i == 1 ) Corpus = FirstSum;
		else if ( i < StartTerm ) Corpus = Sum;
		else if ( (i - StartTerm) % SpaceTerm == 0 )
		{
			//alert("Sum=" + Sum + ",AdjRate=" + AdjRate + ",Corpus=" + Corpus);
			Corpus = Sum * AdjRate;
		}
		else Corpus = Sum;
		Sum = Corpus;
		TotalSum += Sum;		
//-----绑定数据到表格-----
// 还款期次Times
// 本金利率CorpusRate
		elem = oXMLDoc.createElement("Items");
		root.appendChild(elem);
		node = root.lastChild;
		
		elem = oXMLDoc.createElement("Times");
		node.appendChild(elem);
		node.lastChild.text = i.toString();
		
		elem = oXMLDoc.createElement("CorpusRate");
		node.appendChild(elem);
		node.lastChild.text = (Math.round(Corpus * 100) / 100).toString();
	}
	return true;
}

/*aiai modify 13/6/05
  LoanSum :贷款金额
  Rate ：贷款月利率，等于（贷款年利率/12）；
  TotalTerm ：还款期次，等于（贷款年限×12）
  SpaceTerm ：每次变动间隔期数；
  StartTerm : 开始变动的期次
	IncSum ：递增金额（正数），或是递减金额（负数）；
  AdjRate ： 初次调整还款金额后与调整前比率； */
  function ERateLoanPersum(LoanSum, Rate, TotalTerm, SpaceTerm, StartTerm, AdjRate)
  {
	var ReturnSum = LoanSum*Math.pow((1+Rate*12),TotalTerm/12);
	var OldTerm = StartTerm-1;
	var n =Math.ceil((TotalTerm-OldTerm)/SpaceTerm)+1;
	
	var A1=LoanSum*Math.pow((1+Rate),TotalTerm)*Rate;
	if(A1>maxfn || A1<(-1*maxfn))
	  return -1;
	var B1 = Math.pow((1+Rate),TotalTerm)-Math.pow(AdjRate,n-1);
	if(B1>maxfn || B1<(-1*maxfn))
	  return -1;
	var B2 = CalcB2(AdjRate,Rate,n,TotalTerm,SpaceTerm,OldTerm); 
	if(B2>maxfn || B2<(-1*maxfn))
	  return -1;
//alert(n+'  '+A1+' '+B1+'  '+B2)	

	return NBround(A1/(B1+B2),2);	
}
function CalcB2(b,i,n,T,S,M)
{
	var result=0;
	var r;
	var Pn=T;
	var Pr;
	for(r=1;r<n; r++)
	{
		Pr = S*(r-1)+M;
		result +=(b-1)*Math.pow(b,(r-1))*Math.pow((1+i),(Pn-Pr));
	}
	return result;
}

