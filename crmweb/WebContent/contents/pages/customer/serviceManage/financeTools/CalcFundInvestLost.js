
			function calc(oDocument)
			{
				var profit=parseFloat(oDocument.all.edoutprice.value)-parseFloat(oDocument.all.edinprice.value);
	   			var sum=parseFloat(oDocument.all.edoutprice.value)+parseFloat(oDocument.all.edinprice.value);
	   			var fundlost= profit*parseFloat(oDocument.all.edoutnum.value)
	   				+ parseFloat(oDocument.all.edcake.value)
	   				- sum * parseFloat(oDocument.all.edoutnum.value)
	   					* (parseFloat(oDocument.all.edtraderate.value)/100);	   		
	   			oDocument.all.txttotal.value=Round(fundlost);
			}