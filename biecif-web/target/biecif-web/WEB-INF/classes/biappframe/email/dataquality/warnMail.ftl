<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<title>alterationMail.ftl</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
<style type="text/css">
html, body {
    font:normal 12px verdana;
    margin:0; padding:0;
    border:0 none;
    height:100%;
}
.main-body {
    font-family: "宋体", "新宋体", "Courier New";
    color: #6C6C6C; padding: 2px;
    background-color:#dfe8f6;
    text-decoration: none;
}
table {
    font-size: 13px;
    padding: 2px;
}
.title {
    line-height: 22px;
    font-size: 14px;
    font-weight: bold;
}
.title-hx{
    background-color:#8aace5;
}
.value {
    line-height: 18px;
    font-size: 12px;
    color: #454545;
    background-color: #FFFFFF;
    overflow: hidden;
}
.tb-css{
    
}
.tb-css-top{
    border-bottom: none;
}
</style>
</head>
<body>
<div class="main-body">
    <table width="100%" border="0" cellpadding="0" cellspacing="1">
        <tr>
            <td style="text-align:left;">
                尊敬的用户，您好！<br/><br/>&nbsp;&nbsp;&nbsp;&nbsp;
                您在 数据质量系统 管理的部分数据经过系统检核后产生了质量问题，以下是出现问题的数量统计，详情请进入 数据质量系统 中查看，谢谢！<br/><br/>
            </td>
        </tr>
        <tr>
            <td>
            <#list warns as warn>

            <table width="100%" border="1" cellpadding="0" cellspacing="0" >
                <tr class="title title-hx"><th colspan="8" align="left" class="title">${warn.systemName}</th></tr>
                <tr class="title">
                    <th width="15%" class="title" rowspan="2">数据库</th>
                    <th width="15%" class="title" rowspan="2">数据集</th>
                    <th width="15%" class="title" rowspan="2">规则组名称</th>
                    <th width="15%" class="title" rowspan="2">规则组类型</th>
                    <th width="40%" class="title" colspan="4" align="center">问题级别</th>
                </tr>
                <tr class="title">
                	<th width="10%" class="title">正常</th>
                	<th width="10%" class="title">提示</th>
                	<th width="10%" class="title">警告</th>
                	<th width="10%" class="title">异常</th>
                </tr>
                <#list warn.getSysvoList()?sort_by(["ruleGrpType"]) as sysvo>                
                <tr>
                   <td class="value">
                     <#if sysvo.databaseId = ''> - 
                     <#else> ${sysvo.getDatabaseName()} </#if>
                   </td>
                   <td class="value">
                     <#if sysvo.datasetId = ''> - 
                     <#else> ${sysvo.getDatasetName()} </#if>
                   </td>
                   <td class="value">
                     <#if sysvo.ruleGrpId = ''> - 
                     <#else> ${sysvo.getRuleGrpName()} </#if>
                   </td>
                   <td class="value" align="center">
                     <#if sysvo.ruleGrpId = ''> -
                     <#else> ${sysvo.getRuleGrpTypeName()} </#if>
                   </td>
                   <td class="value" align="center">
                     <#if sysvo.normalCount??> ${sysvo.getNormalCount()} 
                     <#else> - </#if>
                   </td>
                   <td class="value" align="center">
                     <#if sysvo.infoCount??> ${sysvo.getInfoCount()} 
                     <#else> - </#if>
                   </td>
                   <td class="value" align="center">
                     <#if sysvo.warnCount??> ${sysvo.getWarnCount()} 
                     <#else> - </#if>
                   </td>
                   <td class="value" align="center">
                     <#if sysvo.errorCount??> ${sysvo.getErrorCount()} 
                     <#else> - </#if>
                   </td>
                </tr>
                </#list>
            </table>
        
            <br/>
           </#list>
            </td>
        </tr>
        <tr>
            <td style="text-align:left;">
                系统自动发送邮件,请勿回复! &nbsp;&nbsp;&nbsp;&nbsp;
                发送时间：${sendTime?string("yyyy-MM-dd HH:mm:ss")}
            </td>
        </tr>
    </table>
</div>
</body>
</html>