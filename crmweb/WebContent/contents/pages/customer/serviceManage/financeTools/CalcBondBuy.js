function CalcBondBuy(oDocument)
{
		/// 债券面额：元（正整数；无默认值）
		var Cost=0;
		///  票面利率： %（正数，保留两位小数；债券种类为贴现，则不显示；无默认值）
		var Rate=0;
		///	认购价格：元（正整数；无默认值）
		var BuyPrice=0;
		///	债券期限：年（正整数）
		var Years=0;
		///	认购日期：年/月/日
		var BuyDate= new Date();
		/// 债券每年的利息支付频率: 　  次，正整数
		var Freq=0;
		/// options  1、贴现债券
		///		    2、到期一次还本付息债券 
		///         3、固定利率附息债券和浮动利率债券
var w,m;
var pv,x,s,e,isetp,ret=0;
var CurrDate=new Date();
			
//贴现债券
	if (oDocument.all.cbType_0.checked==true)
	{
		options=0;
		Cost=parseFloat(oDocument.all.edCost.value);
		BuyPrice=parseFloat(oDocument.all.edPrice.value);
		Years=parseInt(oDocument.all.edYear.value);
	 } 
//到期一次还本付息债券
	 if (oDocument.all.cbType_1.checked==true)
	 {
		options=1;
		Cost=parseFloat(oDocument.all.edCost.value);
		BuyPrice=parseFloat(oDocument.all.edPrice.value);
		Years=parseInt(oDocument.all.edYear.value);
		Rate=parseFloat(oDocument.all.edRate.value)/100;
    }
//固定利率和浮动利率
	if (oDocument.all.cbType_2.checked==true)
	{
		options=2;
		Cost=parseFloat(oDocument.all.edCost.value);
		BuyPrice=parseFloat(oDocument.all.edPrice.value);
		Years=parseInt(oDocument.all.edYear.value);
		Rate=parseFloat(oDocument.all.edRate.value)/100;
		BuyDate=oDocument.all.edDate.value;
		Freq=parseInt(oDocument.all.edFreq.value);
		
		CurrDate =StrToDate(BuyDate);
		CurrDate.setYear(CurrDate.getYear()+Years);
    }
			switch(options)
			{
				case 0:
					ret=(Cost-BuyPrice*1.0)/(BuyPrice*Years);
					break;
				case 1:
					ret= Math.pow((Cost+Years*Cost*Rate)/BuyPrice,1.0/Years)-1;
					break;
				case 2:
					m=Years*Freq;
					w=1;
					isetp=0.0001;
					s=0.001;
					e=1;
					pv=0;						
					x=(1-isetp)/2;
					while ((Math.abs(pv-BuyPrice)>0.001)&&(Math.abs(e-s)>isetp))
					{
						pv=Calc(x,w,m,Cost,Rate,Freq);
						if (pv==0)
							break;
						if (pv<BuyPrice) 
						{
							e=x;
							x=s+(e-s)/2;
						}
						if (pv>BuyPrice)
						{
							s=x;
							x=s+(e-s)/2;
						}
					}
					ret=x;
					break;
			}
			oDocument.all.lbResult.value = NBround(ret*100,2);
		}
		
		function Calc(x,w,m,Cost,Rate,Freq)
		{
			var y=0;
			for (i=w;i<=w+m-1;i++)
				y=y+(Cost*Rate/Freq)/Math.pow((1+x/Freq),i);
			y=y+(Cost/Math.pow((1+x/Freq),(w+m-1)));
			return y;
		}