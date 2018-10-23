function CalcRetSum(oDocument)
{

	var price=parseFloat(oDocument.all.edprice.value);
	var unit=parseInt(oDocument.all.edunit.value);
	var rate=parseFloat(oDocument.all.edrate.value);

	return Round(price*unit*(1-rate/100));
}