function ComputeTradeFee(oDocument,iType,xmlDoc)
{
	var tradenum=parseInt(oDocument.all.edtradenum.value);
	var tradeprice=parseFloat(oDocument.all.edtradeprice.value);
	var traderate=parseFloat(oDocument.all.edtraderate.value) /100;
	var tradesum=tradeprice * tradenum;

	var obj=new Object();
	
//var xmlroot=xmlDoc.documentElement;
	var type;
	
	switch(iType)
	{
		case 1:
		{
		    type=6;
		    break;
		}
		case 2:
		{
		    type=10;
		    break;
		}
		case 3:
		{
		    type=1;
		    break;
		}
		case 4:
		{
		    type=5;
		    break;
		}						
	}
	

	/*for (i=0;i<xmlroot.childNodes.length;i++)
	{
		if(xmlroot.childNodes.item(i).childNodes.item(0).text == type)
		{
			var yhs=xmlroot.childNodes.item(i).childNodes.item(3).text;
			var zdyj=xmlroot.childNodes.item(i).childNodes.item(4).text;
			var ghf=xmlroot.childNodes.item(i).childNodes.item(5).text;
			var jsf=xmlroot.childNodes.item(i).childNodes.item(7).text;
			var jygf=xmlroot.childNodes.item(i).childNodes.item(8).text;
			break;
		}
	}	
	*/
	obj.r1=tradesum * 0.001; //印花税					
	obj.r2 =tradesum * traderate; //佣金
	switch (iType)
	{				
		case 1: //上A
		{					
			obj.r3=tradenum*0.001; //过户费
			if(obj.r3<1) //aiai, 过户费最低为￥1.0
				obj.r3=1;
			if(obj.r2 < 5)//aiai,佣金最低为￥5.0
				obj.r2 = 5;
			break;
		}
		case 3: //深A
		{					
			obj.r3=tradesum * 0.0001875; //监督管理费和经手费
			if(obj.r2 < 5)//aiai,佣金最低为￥5.0
				obj.r2 = 5;
			break;
		}
		case 2: //上B
		{					
			obj.r3=tradesum * 0.0005; //结算费
			obj.r4=tradenum * 0.001;  //交易过户费
			break;
		}	
		case 4: //深B
		{					
			obj.r3=tradesum * 0.000341; //交易规费
			obj.r4=tradesum * 0.0005;  //结算费
			if (obj.r4>500)
				 obj.r4=500;
			break;
		}			
	}  
	return obj;
}