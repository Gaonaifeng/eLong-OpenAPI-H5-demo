var nextPageIndex = 1;

//加载中
var loadingHtml = '<li id="loading" class="aui-list-item aui-text-center">' +
					'<div class="aui-list-item-inner" ><img src="../images/common/loading.gif"/></div>' +
				  '</li>';

//加载更多
var moreHtml = '<li id="more" class="aui-list-item aui-font-size-12 aui-text-center">' +
	        		'<div class="aui-list-item-inner" onclick="getPagerList()">点击加载更多...</div>' +
	        	'</li>';

$(function(){
	getPagerList();
	
	/**
	 * 搜索按钮事件
	 */
	$("#btnsearch").click(function(){
		//重新开始取第一页
		nextPageIndex = 1;
		//清空目前取到的项
		$("#divhotelsid").html("");
		getPagerList();
	});
});

/**
 * 跳转
 */
function goDetail(hotelId){
	var detailUrl = "../hotel/detail?hotelId="+ hotelId + "&arrivalDate=" + $("#indate").val() + "&departureDate=" + $("#offdate").val();
	linking(detailUrl);
}

/**
 * 加载数据
 */
function getPagerList(){
	var ulObj = $("#divhotelsid");
	//显示加载中
	ulObj.append(loadingHtml);
	//删除加载更多
	if($("#divhotelsid li[id=more]")){
		$("#divhotelsid li").remove("li[id=more]");
	}
	
	//获取数据并绑定
	var url = "../hotel/loadlist?";
	url = url + "pageIndex=" + nextPageIndex;
	
	url = url + "&arrivalDate=" + $("#indate").val();
	url = url + "&departureDate=" + $("#offdate").val();
	if($("#searchtext").val().trim() != ""){
		url = url + "&queryText=" + $("#searchtext").val().trim() ;
	}
	var result = ajaxCommon(url, "GET", "");
	//删除加载中
	$("#divhotelsid li").remove("li[id=loading]");
	if(result.state){
		if(result.data.list != null && result.data.list.length > 0){
			$.each(result.data.list, function (i, info){
			var liString = '<li class="aui-list-item">'+
						      '<div class="aui-media-list-item-inner">'+
					            	'<div class="aui-list-item-media">'+
					                	'<img src="'+ info.thumbnailUrl +'">'+
					            	'</div>'+
					            	'<div class="aui-list-item-inner">'+
					            		'<div class="aui-list-item-text hand" onclick="goDetail('+ info.hotelId +');">'+
					            			'<div class="aui-list-item-left">'+
					                        	'<div class="aui-list-item-title">'+ info.hotelName +'</div>'+
					                        	'<div class="aui-font-size-12">'+ info.reviewCount +'条点评</div>'+
					                        	'<div class="aui-font-size-12">'+ info.districtName +'|'+ info.businessZoneName +'</div>'+
					            			'</div>'+
					            			'<div class="aui-list-item-right aui-text-danger">'+ info.currencyFlag +''+ info.lowRate +'</div>'+
					            		'</div>'+
					            	'</div>'+
					        	'</div>'+
					    	'</li>';
				ulObj.append(liString);
			});
		}
		if(result.data.isHaveNextPage){
			nextPageIndex++;
			ulObj.append(moreHtml);
		}
	}else{
		toastError(result.msg);
	}
}