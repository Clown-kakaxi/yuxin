		  // is inside content frame?
			var __isInContentFrame = (window.name === "contentFrame") ;
		 	if (__isInContentFrame)
			{
				 var head  = document.getElementsByTagName('head')[0];
				 var link  = document.createElement('link');
				 link.id   = "frameCSS";
				 link.rel  = 'stylesheet';
				 link.type = 'text/css';
				 link.href = '../assets/ui/css/gallerycleanup.css';
				 link.media = 'all';
				 head.appendChild(link);
			}

		 
            $(document).ready ( function () {

				
					
                $("a.view-chart-data").click( function () {
                    var chartDATA = '';
                    if ($(this).children("span").html() == "View XML" ) {
                        chartDATA = FusionCharts('ChartId').getChartData('xml').replace(/\</gi, "&lt;").replace(/\>/gi, "&gt;");
								$("#titlebar").html("Chart XML Data");								
                    } else if ($(this).children("span").html() == "View JSON") {
                        chartDATA = JSON.stringify( FusionCharts('ChartId').getChartData('json') ,null, 2);
								$("#titlebar").html("Chart JSON Data");
                    }
                    $('pre.prettyprint').html( chartDATA );
                    $('.show-code-block').css('height', ($(document).height() - 56) ).show();
                    prettyPrint();
                })

                $('.show-code-close-btn a').click(function() {
                    $('.show-code-block').hide();
                });
					 
                $('.show-code-close-icon a').click(function() {
                    $('.show-code-block').hide();
                });

					 
            })
