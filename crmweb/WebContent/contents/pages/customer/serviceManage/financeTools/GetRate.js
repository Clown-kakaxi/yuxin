		//获取基金净值
		function GetFundNetValue(fundID,xmlDoc)
		{			
			if(fundID == "")
			{
				alert("未选择基金或者错误地选择了基金。");
				return 0;
			}			
			var xmlroot = xmlDoc.documentElement;
			if(xmlroot == null)
			{
				return 0;
			}		
			var xmlNode;
			/*for(i=0; i<xmlroot.childNodes.length;  i++)
			{
				xmlNode = xmlroot.childNodes.item(i);		
				if(xmlNode.selectSingleNode("CODE").text == fundID)
				{
					return xmlNode.selectSingleNode("NETVALUE").text;
				}
			}
			return 0;*/
			xmlNode = xmlroot.selectSingleNode("OPENFUND[CODE/text()="+fundID+"]/NETVALUE/text()");
			if(xmlNode!=null)
				return xmlNode.text
			else 
				return 0;
		}
		//获取基金净值的日期 
		function GetFundValueDate(fundID,xmlDoc)
		{			
			if(fundID == "")
			{
				alert("未选择基金或者错误地选择了基金。");
				return 0;
			}			
			var xmlroot = xmlDoc.documentElement;
			if(xmlroot == null)
			{
				return 0;
			}		
			var tmp,sDate;
			var xmlNode;
			/*for(i=0; i<xmlroot.childNodes.length;  i++)
			{
				xmlNode = xmlroot.childNodes.item(i);
				if(xmlNode.selectSingleNode("CODE").text == fundID)
				{
					tmp = xmlNode.selectSingleNode("MODIFYDATE").text;
				}
			}*/
			xmlNode = xmlroot.selectSingleNode("OPENFUND[CODE/text()="+fundID+"]/MODIFYDATE/text()");
			if(xmlNode!=null)
			{
					tmp = xmlNode.text
					sDate = tmp.split('-');
					return new Date(sDate[0],new Number(sDate[1])-1,sDate[2]);
			}
			else 
				return 0;
		}
		//基金---(基金ID) return 收益率
		function GetFundYield(fundID, xmlDoc)  
		{			
			if(fundID == "")
			{
				alert("未选择基金或者错误地选择了基金。");
				return;
			}	
			var result=0;
			var xmlroot = xmlDoc.documentElement;
			
			//比较fundID，找到对应的收益率
			for(i=0; i<xmlroot.childNodes.length;  i++)
			{
			   	if(xmlroot.childNodes.item(i).childNodes.item(0).text == fundID)	
				{
			   		result = xmlroot.childNodes.item(i).childNodes.item(2).text;
				}
			}
			//找到了返回找到的收益率，没有找到返回0
			return result;
		}
		
		//人民币---(存款方式，存期) return 利率
		function GetRMBSaveRatio(typeID, period, xmlDoc)  
		{			
			if(typeID == "" || period == "")
			{
				alert("缺少存款方式和存期");
				return;
			}	
			var find = 0;
			var result=0;
			var xmlroot = xmlDoc.documentElement;
			
			//比较TYPEID和ENDTERM，找到对应的利率
			for(i=0; i<xmlroot.childNodes.length;  i++)
			{
			   	if((xmlroot.childNodes.item(i).childNodes.item(4).text == typeID)	
			   	&& (new Number(period) <= xmlroot.childNodes.item(i).childNodes.item(3).text))
			   	//&& xmlroot.childNodes.item(i).childNodes.item(3).text < period))
				{
					if(find == 0 || new Number(find) >=xmlroot.childNodes.item(i).childNodes.item(3).text )
					{
				   		find =xmlroot.childNodes.item(i).childNodes.item(3).text;
				   		result = xmlroot.childNodes.item(i).childNodes.item(7).text;
				   	}
				}
			}
			//找到了返回找到的利率，没有找到返回0
			return result;//NBround(result,2) ;
			
		}
		
		//外币---(存款方式，存期) return 利率
		function GetForeignSaveRatio(typeID, moneyID, period, xmlDoc)  
		{	
			if(moneyID == "")
			{
				alert("缺少币种ID");
				return;
			}
			if(period == "" && typeID == "")
			{
				alert("缺少存期 | 存款方式ID");
				return;
			}
			if(typeID == "")
			{
				typeID = "0";
			}
			var result = 0;
			var xmlroot = xmlDoc.documentElement; 
			for(i=0; i<xmlroot.childNodes.length; i++)
			{		
				switch(typeID)
				{
				case "1":		//活期	
					if(xmlroot.childNodes.item(i).childNodes.item(0).text == 1
					&& moneyID== xmlroot.childNodes.item(i).childNodes.item(2).text )
					{
						result = xmlroot.childNodes.item(i).childNodes.item(7).text;
					}
					break;
				case "2":		//七天		
					if(xmlroot.childNodes.item(i).childNodes.item(0).text == 8
					&& xmlroot.childNodes.item(i).childNodes.item(2).text == moneyID)
					{
						result = xmlroot.childNodes.item(i).childNodes.item(7).text;
					}
					break;
				default:		//定期
						if(xmlroot.childNodes.item(i).childNodes.item(0).text != 8 
						&& xmlroot.childNodes.item(i).childNodes.item(0).text != 1
						&& xmlroot.childNodes.item(i).childNodes.item(2).text == moneyID
						&& (new Number(period) > xmlroot.childNodes.item(i).childNodes.item(4).text
						&& new Number(period) <= xmlroot.childNodes.item(i).childNodes.item(5).text))
						{
							result = xmlroot.childNodes.item(i).childNodes.item(7).text;	
						}
					break;
				}
			}
			return result;		
		}
		
		//其他---(存款方式，存期) return 利率
		function GetOtherSaveRatio(type, period, xmlDoc)  
		{			
			if(type == "" && period == "")
			{
				alert("缺少存款类别和存期");
				return;
			}	
			var result = 0;
			var xmlroot = xmlDoc.documentElement; 
			for(i=0; i<xmlroot.childNodes.length; i++)
			{
				if(xmlroot.childNodes.item(i).childNodes.item(1).text == type
				&& new Number(period) ==xmlroot.childNodes.item(i).childNodes.item(4).text)
				{
					result = xmlroot.childNodes.item(i).childNodes.item(5).text;
					break;	
				}
			}
			return result;			
		}
		
		//(币种1，币种2）return 兑率
		function GetChangeRatio(money1, money2, xmlDoc) 
		{
			if(money1 == "" || money2 == "")
			{
				alert("缺少币种1和币种2");
				return;
			}	
			if(money1 == money2) //相同币种
				return 1;
				
			var result = 0;
			var xmlroot = xmlDoc.documentElement; 
			if(money1 == 1)	//人民币 — 外币
			{
				for(i=0; i<xmlroot.childNodes.length; i++)
				{
					if(xmlroot.childNodes.item(i).childNodes.item(0).text == money2)
					{
						result = xmlroot.childNodes.item(i).childNodes.item(2).text;	
						return NBround(result, 2);
					}
				}
			}
			else
			{
				if(money2 == 1)	// 外币 — 人民币
				{
					for(i=0; i<xmlroot.childNodes.length; i++)
					{
						if(xmlroot.childNodes.item(i).childNodes.item(0).text == money1)
						{
							result = xmlroot.childNodes.item(i).childNodes.item(2).text;	
							return NBround(1/result, 2);
						}
					}	
				}
				else	//外币 — 外币
				{	
					var m1, m2;
					m1=m2=0;
					for(i=0; i<xmlroot.childNodes.length; i++)
					{
						if(xmlroot.childNodes.item(i).childNodes.item(0).text == money1)
						{
							m1 = xmlroot.childNodes.item(i).childNodes.item(2).text;
						}
						if(xmlroot.childNodes.item(i).childNodes.item(0).text == money2)
						{
							m2 = xmlroot.childNodes.item(i).childNodes.item(2).text;
						}
					}
					if(m1*m2 != 0)
						return NBround(m2/m1, 2);
					else
						return false;					
				}
			}
			return false;	
		}
		//取贷款利率 return 年利率 typeID 取0:普通贷款1:商业贷款2:公积金贷款/
		function GetLoanRatio(typeID, period, xmlDoc)  
		{			
			if(typeID == "" || period == "")
			{
				alert("缺少贷款方式和贷款期限");
				return;
			}	
			var find = 0;
			var result=0;
			var xmlroot = xmlDoc.documentElement;
			var dt = new Date(1900, 1, 1);
			var StartTerm, EndTerm;
			
			//比较TYPEID和ENDTERM，找到对应的利率
			for(i=0; i<xmlroot.childNodes.length;  i++)
			{
				StartTerm = new Number(xmlroot.childNodes.item(i).childNodes.item(1).text);
				EndTerm = new Number(xmlroot.childNodes.item(i).childNodes.item(2).text);
			   	if((xmlroot.childNodes.item(i).childNodes.item(3).text == typeID)	// TypeGroup
			   		&& (new Number(period) <= EndTerm || EndTerm == 0)
			   		&& (new Number(period) > StartTerm) ) // EndTerm
				{
					if(dt <= Date.parse(xmlroot.childNodes.item(i).childNodes.item(4).text.replace(/-/g,"/")))
					{
				   		dt = Date.parse(xmlroot.childNodes.item(i).childNodes.item(4).text.replace(/-/g,"/"));
				   		result = xmlroot.childNodes.item(i).childNodes.item(5).text;
				   	}
				}
			}
			//找到了返回找到的利率，没有找到返回0
			return result;//NBround(result,2) ;
		}
		//(币种1，币种2）return 兑率 money1/money2使用币种名称
		function GetChangeRatioByName(money1, money2, xmlDoc) 
		{
			if(money1 == "" || money2 == "")
			{
				alert("缺少币种1和币种2");
				return;
			}	
			if(money1 == money2) //相同币种
				return 1;
				
			var result = 0;
			var xmlroot = xmlDoc.documentElement; 
			if(money1 == "人民币")	//人民币 — 外币
			{
				for(i=0; i<xmlroot.childNodes.length; i++)
				{
					if(xmlroot.childNodes.item(i).childNodes.item(1).text == money2)
					{
						result = xmlroot.childNodes.item(i).childNodes.item(2).text;	
						return NBround(result, 4);
					}
				}
			}
			else
			{
				if(money2 == "人民币")	// 外币 — 人民币
				{
					for(i=0; i<xmlroot.childNodes.length; i++)
					{
						if(xmlroot.childNodes.item(i).childNodes.item(1).text == money1)
						{
							result = xmlroot.childNodes.item(i).childNodes.item(2).text;	
							return NBround(1/result, 4);
						}
					}	
				}
				else	//外币 — 外币
				{	
					var m1, m2;
					m1=m2=0;
					for(i=0; i<xmlroot.childNodes.length; i++)
					{
						if(xmlroot.childNodes.item(i).childNodes.item(1).text == money1)
						{
							m1 = xmlroot.childNodes.item(i).childNodes.item(2).text;
						}
						if(xmlroot.childNodes.item(i).childNodes.item(1).text == money2)
						{
							m2 = xmlroot.childNodes.item(i).childNodes.item(2).text;
						}
					}
					if(m1*m2 != 0)
						return NBround(m2/m1, 4);
					else
						return false;					
				}
			}
			return false;	
		}
