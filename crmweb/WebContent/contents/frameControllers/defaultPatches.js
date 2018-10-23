/**
 * 默认配置补丁文件,项目组可根据自身情况,订制一些相关的功能内部的默认属性以及默认逻辑之类;
 * 本文件中所有配置项以及逻辑,如在功能代码中有同名属性,即被功能代码覆盖.
 * 如下例子中:添加默认逻辑[双击数据行打开详情面板],如功能中有自定义数据行双击事件,则此出逻辑不生效.
 */



var rowdblclick = function(tile, record){
	showDetailView();
};