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
                尊敬的用户，您好！<br/>&nbsp;&nbsp;&nbsp;&nbsp;
                您在元数据系统订阅的元数据发生了变更，详情请点击 <a href="${redirectUrl}">${loginUrl}</a> 查看，谢谢！<br/>
            </td>
        </tr>
        <tr>
            <td>
        	<#assign x="">
            <#list ranges as range>
            <#if x!=range.subName>
            	订阅名称：${range.subName}
            	<#assign x=range.subName>
            </#if>
            <table width="100%" border="1" cellpadding="0" cellspacing="0" class="tb-css-top">
                <tr class="title title-hx"><th colspan="4" align="left" class="title">订阅元数据分支</th></tr>
                <tr class="title">
                    <th width="25%" class="title">分支代码</th>
                    <th width="25%" class="title">分支名称</th>
                    <th width="25%" class="title">分支所属元模型</th>
                    <th width="25%" class="title">过滤元模型</th>
                </tr>
                <tr>
                    <td class="value">${range.getInstanceCode()}</td>
                    <td class="value">${range.getInstanceName()}</td>
                    <td class="value">${range.getClassifierId()}</td>
                    <td class="value">${range.getSubClass()}</td>
                </tr>
            </table>
        
            <table width="100%" border="1" cellpadding="0" cellspacing="0" class="tb-css">
                <tr class="title title-hx"><th colspan="4" align="left" class="title">变更元数据统计</th></tr>
                <tr>
                  <th width="25%" class="title">元模型代码</th>
                  <th width="25%" class="title">元模型名称</th>
                  <th width="25%" class="title">变更类型</th>
                  <th width="25%" class="title">变更数量</th>
                </tr>
                <#list range.getAlterationInfoList() as stat>
                <tr>
                  <td class="value">${stat.getClassifierId()}</td>
                  <td class="value">${stat.getClassifierName()}</td>
                  <td class="value">
                    <#if stat.alterationType = '0' >新增
                    <#elseif stat.alterationType = '1'> 删除
                    <#else> 修改 </#if>
                  </td>
                  <td class="value">${stat.getCount()}</td>
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