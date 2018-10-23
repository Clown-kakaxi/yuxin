/**
 * 定义全局变量
 */
 var oCustInfo =
    {cust_id:false,//客户id
	 cust_name:false,//客户名称
	 cust_type:false,
	 omain_type:false
	 };
 var oBaseInfo =
 {base_id:false,//客户群id
	 base_name:false//客户群名称
	 };
/* function isOmainType(main_type){
	 if(main_type=="1"){
		 return true;
	 }else{
		 return false;
	 }
}*/
function getOmainType(mgrId,userId){
	 if(mgrId!=null && mgrId==userId){
		 return "1";
	 }else{
		 return "2";
	 }
}