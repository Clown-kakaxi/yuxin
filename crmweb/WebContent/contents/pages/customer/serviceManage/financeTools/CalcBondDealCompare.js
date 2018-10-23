			function GetHaveYear(s, e)
			{
				var dt_s = Date.parse(s.replace(/-/g, "/"));
				var dt_e = Date.parse(e.replace(/-/g, "/"));
				return (dt_e - dt_s) / (1000 * 60 * 60 * 24) / 365;
			}
			
			function CalcBuyDq(CurrCost, YearRate, EndYears, BuyYears, Rate, RetTerm)
			{
                                var interestRate = 0.05;  //利息税率
				var MaxBuyPrice;
				CurrCost = new Number(CurrCost);
				YearRate = new Number(YearRate);
				Rate = new Number(Rate);
				RetTerm = new Number(RetTerm);
				//2）	到期一次还本付息债券：
				//增加输入项：债券偿还期限，单位为月。该项输入只有在选择了到期一次性还本付息债券以及计算买方时才显示。默认为12个月。
				//最高买入价格＝债券票面面值×（1+债券偿还期限÷12×债券票面年利率）÷（1+银行年利率×(1-利息税率)）持有年限
				MaxBuyPrice = CurrCost * (1 + RetTerm / 12 * YearRate) / Math.pow(1 + Rate * (1-interestRate), GetHaveYear(BuyYears, EndYears));
				return MaxBuyPrice;
			}

			function CalcBuyGd(CurrCost, YearRate, Freq, EndYears, BuyYears, Rate)
			{
                                var interestRate = 0.05;  //利息税率
				var MaxBuyPrice, D, WillInterestTimes;
				CurrCost = new Number(CurrCost);
				YearRate = new Number(YearRate);
				Rate = new Number(Rate);
				Freq = new Number(Freq);
				//3）	固定利率附息债券和浮动利率债券：
				//最高买入价格＝债券票面面值÷（1+银行年利率×(1-利息税率)）持有年限+（债券票面面值×债券票面年利率÷年付息次数）
				//  ×[1－（1+银行年利率×(1-利息税率)÷年付息次数）－将领取利息次数]÷（银行年利率×(1-利息税率)÷年付息次数）
				//其中：将领取利息次数＝（债券到期兑换日期－预计买入日期）/（365÷年付息次数）（如有小数，进1位整数，再取整）
				D = GetHaveYear(BuyYears, EndYears) * Freq;
				WillInterestTimes = Math.ceil(D);
				MaxBuyPrice = CurrCost / Math.pow(1 + Rate * (1-interestRate), GetHaveYear(BuyYears, EndYears)) + (CurrCost * YearRate / Freq)
						* (1 - Math.pow(1 + Rate * (1-interestRate) / Freq, - WillInterestTimes)) / (Rate * (1-interestRate) / Freq);
				return MaxBuyPrice;
			}

			function CalcBuyTx(CurrCost, EndYears, BuyYears, Rate)
			{
                                var interestRate = 0.05; //利息税率
				var MaxBuyPrice;
				CurrCost = new Number(CurrCost);
				Rate = new Number(Rate);
				//1）	贴现债券：
				//最高买入价格＝债券票面面值÷（1+银行年利率×持有年限×（1-利息税率））
				MaxBuyPrice = CurrCost / (1 + Rate * GetHaveYear(BuyYears, EndYears) * (1-interestRate));
				return MaxBuyPrice;
			}

			function CalcSaleDq(CurrCost, YearRate, EndYears, Cost, BuyYears, SaleYears, Rate)
			{
                                var interestRate = 0.05; //利息税率
				var MinSalePrice;
				CurrCost = new Number(CurrCost);
				YearRate = new Number(YearRate);
				Cost = new Number(Cost);
				Rate = new Number(Rate);
				//2）	到期一次还本付息债券：
				//最低卖出价格＝债券成本价格×（1+银行年利率×(1-利息税率)）持有年限
				MinSalePrice = Cost * Math.pow(1 + Rate * (1-interestRate), GetHaveYear(BuyYears, SaleYears));
				return MinSalePrice;
			}

			function CalcSaleGd(CurrCost, YearRate, Freq, EndYears, Cost, BuyYears, SaleYears, Rate)
			{
                                var interestRate = 0.05; //利息税率
				var MinSalePrice, D, AlreadyInterestedTimes;
				CurrCost = new Number(CurrCost);
				YearRate = new Number(YearRate);
				Cost = new Number(Cost);
				Rate = new Number(Rate);
				Freq = new Number(Freq);
				//3）	固定利率附息债券和浮动利率债券：
				//最低卖出价格＝债券成本价格×（1+银行年利率×(1-利息税率)）持有年限－
				//  （债券票面面值×债券票面年利率÷年付息次数）×[（1+银行年利率×（1-利息税率）÷年付息次数）已领取利息次数－1]
				//  ÷（银行年利率×（1-利息税率）÷年付息次数）
				//已领取利息次数＝（预计卖出日期－债券购入日期）/（365÷年付息次数）（如有小数，取整数部分）
				D = GetHaveYear(BuyYears, SaleYears) * Freq;
				AlreadyInterestedTimes = Math.floor(D);
				MinSalePrice = Cost * Math.pow(1 + Rate * (1-interestRate), GetHaveYear(BuyYears, SaleYears))
					- (CurrCost * YearRate / Freq) * (Math.pow(1 + Rate * (1-interestRate) / Freq, AlreadyInterestedTimes) - 1)
					/ (Rate * (1-interestRate) / Freq);
				return MinSalePrice;
			}

			function CalcSaleTx(Currcost, EndYears, Cost, BuyYears, SaleYears, Rate)
			{
                                var interestRate = 0.05; //利息税率
				var MinSalePrice;
				Currcost = new Number(Currcost);
				Cost = new Number(Cost);
				Rate = new Number(Rate);
				//1）	贴现债券：
				//最低卖出价格＝债券成本价格×银行年利率×持有年限×（1-利息税率）+债券成本价格
				MinSalePrice = Cost * Rate * GetHaveYear(BuyYears, SaleYears) * (1-interestRate) + Cost;
				return MinSalePrice;
			}
			
			function Calc(oDocument)
			{
				var msg="作为0方，您的债券最1价格是2元，否则，您的投资回报将低于当前银行利率。";
				var result;
				if(oDocument.all.rblID_0.checked)
				{
					var HaveMonth=Cal_MonthDiff(Cal_strtodate(oDocument.all.dpWillBuyDate.value),Cal_strtodate(oDocument.all.dpLimitDate.value));
					Rate=GetRate(HaveMonth);
					switch(parseInt(oDocument.all.ddlType.value))
					{
						//贴现债券
						case 1:
							result=CalcBuyTx(document.EduCalc.tbCost.value,document.EduCalc.dpLimitDate.value,document.EduCalc.dpWillBuyDate.value,Rate);
							break;
							
						//到期一次性还本付息	
						case 2:
							var RetTerm = parseInt(document.EduCalc.tbRetTerm.value);
							result=CalcBuyDq(document.EduCalc.tbCost.value,parseFloat(document.EduCalc.tbRate.value)/100,document.EduCalc.dpLimitDate.value,document.EduCalc.dpWillBuyDate.value,Rate, RetTerm);
							break;
							
						//固定利率债券和浮动利率债券	
						case 3:
							result=CalcBuyGd(document.EduCalc.tbCost.value,parseFloat(document.EduCalc.tbRate.value)/100,document.EduCalc.tbYearTime.value,document.EduCalc.dpLimitDate.value,document.EduCalc.dpWillBuyDate.value,Rate);
							break;
					}
					oDocument.all.lblInfo.innerText=msg.replace("0","买").replace("1","高买入").replace("2",NBround(result,2).toString());
				}
				else
				{
					var HaveMonth=Cal_MonthDiff(Cal_strtodate(oDocument.all.dpBuyDate.value),Cal_strtodate(oDocument.all.dpSellDate.value));
					Rate=GetRate(HaveMonth);
					switch(parseInt(oDocument.all.ddlType.value))
					{
						//贴现债券
						case 1:
							result=CalcSaleTx(document.EduCalc.tbCost.value,document.EduCalc.dpLimitDate.value,document.EduCalc.tbPrice.value,document.EduCalc.dpBuyDate.value,document.EduCalc.dpSellDate.value,Rate);
							break;
						//到期一次性还本付息		
						case 2:
							result=CalcSaleDq(document.EduCalc.tbCost.value,parseFloat(document.EduCalc.tbRate.value)/100,document.EduCalc.dpLimitDate.value,document.EduCalc.tbPrice.value,document.EduCalc.dpBuyDate.value,document.EduCalc.dpSellDate.value,Rate);
							break;
						//固定利率债券和浮动利率债券	
						case 3:
							result=CalcSaleGd(document.EduCalc.tbCost.value,parseFloat(document.EduCalc.tbRate.value)/100,document.EduCalc.tbYearTime.value,document.EduCalc.dpLimitDate.value,document.EduCalc.tbPrice.value,document.EduCalc.dpBuyDate.value,document.EduCalc.dpSellDate.value,Rate);
							break;					
					}
					oDocument.all.lblInfo.innerText=msg.replace("0","卖").replace("1","低卖出").replace("2",NBround(result,2).toString());
				}
			}
