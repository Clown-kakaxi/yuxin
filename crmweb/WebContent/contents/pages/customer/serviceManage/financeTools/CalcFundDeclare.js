
		   function calc(oDocument)
		   {
			//	申购基金单位＝申购金额 * （1-申购手续费率）/ 基金单位净值
				var valamount;
				var valone;
				var valrate;
				var valnumber;
				
				valamount=parseInt(oDocument.all.edamount.value);
				valone=parseFloat(oDocument.all.edone.value);
				valrate=parseFloat(oDocument.all.edrate.value)/100;
				
				valnumber=valamount/(1+valrate)/valone;
				//oDocument.all.ednumber.value=Math.floor(valnumber);
				oDocument.all.ednumber.value=NBround(valnumber,2);//aiai 保留两位小数
	   
		   }
