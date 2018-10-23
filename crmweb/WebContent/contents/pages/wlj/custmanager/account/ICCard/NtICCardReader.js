
var ICCardInfo;
var MagCardInfo;
/*
调用ocx控件获取IC卡数据
*/
function Nt_GetICCardInfo(obj, Icflag, Taglist, AIDList, com, brate,ExtNo)
{
ICCardInfo=null;
ICCardInfo= obj.getICCardInfo(Icflag,Taglist,AIDList,com,brate,ExtNo);

}

/*
调用ocx控件产生ARQC
*/
function Nt_genARQC(obj,Icflag,aryInput,AIDList,Port,Brate,ExtNo)
{
 ICCardInfo =null;
 ICCardInfo = obj.genARQC(Icflag,aryInput,AIDList,Port,Brate,ExtNo);
}
/*
调用ocx控件获取交易明细
*/
function Nt_GetTxDetail(obj,Icflag,AIDList,Port,Brate,ExtNo)
{
 ICCardInfo =null;
 ICCardInfo = obj.GetTxDetail(Icflag,AIDList,Port,Brate,ExtNo);
}
/*
调用ocx控件验证主机返回的ARPC
*/
function Nt_ARPCICScript(obj,Icflag,Txdata,ARPC,status,Port,Brate,lpicappdata,ExtNo)
{
  ICCardInfo =null;
  ICCardInfo = obj.ARPCICScript(Icflag,Txdata,ARPC,status,Port,Brate,lpicappdata,ExtNo);
}
/*
调用ocx控件进行脱机PIN验证
*/
function Nt_VerifyOfflinePinCode(obj,Icflag,Port,Brate,ExtNo,PinValue,PinLenth)
{
  ICCardInfo =null;
  ICCardInfo = obj.VerifyOfflinePinCode(Icflag,Port,Brate,ExtNo,PinValue,PinLenth);
}

/*
 调用ocx控件读磁卡
*/
function Nt_getCardNum(obj,iTrackNum,Port,Brate)
{
 MagCardInfo= null;
 MagCardInfo=obj.getCardNum(iTrackNum,Port,Brate);
}
/*
 调用ocx控件读存折
*/
function Nt_getBookAcct(obj,Port,Brate)
{
 MagCardInfo= null;
 MagCardInfo=obj.getBookAcct(Port,Brate);
}
/*
 调用ocx控件写存折
*/
function Nt_wrtBookAcct(obj,strContent,Port,Brate)
{
 MagCardInfo= null;
 MagCardInfo=obj.wrtBookAcct(strContent,Port,Brate);
}
/*
 调用ocx控件写磁卡
*/
function Nt_wrtCard(obj,strContent,iTrackNum,Port,Brate)
{
 MagCardInfo= null;
 MagCardInfo=obj.wrtCard(strContent,iTrackNum,Port,Brate);
}
/*

获取上个执行函数的返回值
*/
function GetReturnValue()
{
  return (ICCardInfo.substring(0,ICCardInfo.indexOf("|")));
}
/*
获取上个执行函数返回值IC实际接触方式
*/
function GetIctype()
{

return (ICCardInfo.substring(ICCardInfo.lastIndexOf("|")+1));
}

/*
在执行GetICCardInfo()后，获取标签编码串返回的多个标签变量
*/
function GetvalueRet()
{
 return (ICCardInfo.substring(ICCardInfo.indexOf("|")+1,ICCardInfo.lastIndexOf("|")));
}

/*
在执行genARQC()后，获取ARQC
*/

function GetARQC()
{
  var tmp = (ICCardInfo.substring(ICCardInfo.indexOf("|")+1,ICCardInfo.lastIndexOf("|")));
  return (tmp.substring(0,tmp.indexOf("|")));
}

/*
在执行genARQC()后，获取Field60
*/
function GetField60()
{
  return "";
}

/*
 在执行genARQC()后，获取lpicappdata
*/
function GetLpicappdata()
{
if(ICCardInfo==null)
{
 alert("请先执行获取ARQC");
return -1;
}
  var tmp = (ICCardInfo.substring(ICCardInfo.indexOf("|")+1));
      tmp = (tmp.substring(tmp.indexOf("|")+1));
  return (tmp.substring(0,tmp.indexOf("|")));
}

/*
在执行genARQC()后，获取交易明细值
*/
function GettxDetailValue()
{
  return (ICCardInfo.substring(ICCardInfo.indexOf("|")+1,ICCardInfo.lastIndexOf("|")));
}

/*
 在执行ARPCICScript()后，获取TC
*/
function GetTC()
{
var tmp = (ICCardInfo.substring(ICCardInfo.indexOf("|")+1));
      tmp = (tmp.substring(tmp.indexOf("|")+1));
  return (tmp.substring(0,tmp.indexOf("|")));
}

/*
 在执行ARPCICScript()后，获取脚本执行结果
*/
function GetScriptResult()
{
 var tmp = (ICCardInfo.substring(ICCardInfo.indexOf("|")+1,ICCardInfo.lastIndexOf("|")));
  return (tmp.substring(0,tmp.indexOf("|")));
}

