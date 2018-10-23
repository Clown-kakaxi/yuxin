//aiai for cacul resize
function CaculResize(mw, mh) 
{
	var iWidth=100, iHeight=100;
	window.resizeTo(iWidth, iHeight);
	window.moveTo((screen.Width - iWidth)/2, (screen.Height - iHeight)/2);
	var iCircle = 20;
	var iWidthStep = (mw - iWidth) / iCircle
	var iHeightStep = (mh - iHeight) / iCircle;

	while (iWidth<=mw && iHeight<=mh)
	{
		iWidth += iWidthStep;
		iHeight += iHeightStep;
		window.resizeTo((iWidth>=mw?mw:iWidth), (iHeight>=mh?mh:iHeight));
		window.moveBy((iWidth>=mw?0:-iWidthStep/2), (iHeight>=mh?0:-iHeightStep/2));
	}
	window.resizeTo(mw, mh);
}
//aiai end
