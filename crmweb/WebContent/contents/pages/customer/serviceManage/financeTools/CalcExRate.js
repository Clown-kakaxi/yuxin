
			function calc(oDocument)
			{
				oDocument.all.tbToCurrSum.value=Math.round(new Number(oDocument.all.tbFromCurrSum.value)
					*parseFloat(oDocument.all.tbQuotePrice.value)*100)/100;
			}