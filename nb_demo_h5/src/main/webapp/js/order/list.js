var nextPageIndex = 1;
$(function(){
	getPagerList();
});

/**
 * 加载数据
 */
function getPagerList(){
	var ulObj = $("#ulOrderList");
	//显示加载中
	ulObj.append("<li id='loading'><div class='loading'><img src='../images/common/loading.gif'></div></li>");
	//删除加载更多
	if($("#ulOrderList li[id=more]")){
		$("#ulOrderList li").remove("li[id=more]");
	}
	
	//获取数据并绑定
	var url = "../order/loadlist?";
	url = url + "pageIndex=" + nextPageIndex;
	
	var result = ajaxCommon(url,"GET","");
	//删除加载中
	$("#ulOrderList li").remove("li[id=loading]");
	if(result.state){
		if(result.data.list != null && result.data.list.length > 0){
			$.each(result.data.list,function(i,info){
				var detailUrl = "./detail?orderId="+info.orderId;
				var liString =  '<div class="aui-card-list-content-padded aui-border-b aui-border-t">'+
        						'<li id="'+info.orderId+'" class="aui-list-item"><a href="'+detailUrl+'">'+
        						'<div class="aui-list-item-inner">'+
        						'<section class="aui-content-padded">'+
        						'<div class="aui-card-list">'+
        						'<div class="aui-card-list-header">'+
        						'<p class="aui-font-size-14 aui-text-success">订单编号：'+info.orderId+'</p>'+
        						'</div>'+
        						'<div class="aui-card-list-content-padded aui-border-b aui-border-t">'+
        						'<p class="aui-list-item-title aui-font-size-14">'+
        						info.hotelName+
        						'</p>'+
        						'<p class="aui-font-size-12">'+
        						info.roomTypeName+	
        						'<span class="aui-font-size-14 aui-text-danger total-price">' + info.currencyFlag + info.totalPrice+'</span>'+
     	 	  					'</p>'+
        						'<p class="aui-font-size-12">'+
        						info.arrivalDateCh+'-'+info.departureDateCh+
        						'<span class="aui-font-size-12 night-number">共'+info.nightNumber+'晚</span>'+
        						'</p>'+
        						'</div>'+
        						'</div>'+
        						'</section>'+
        						'</div>'+
        						'</li>'+
								'</div>';
				ulObj.append(liString);
			})
			if(result.data.isHaveNextPage){
				nextPageIndex++;
				ulObj.append("<li id='more'><div class='more aui-font-size-12' onclick='getPagerList()'>点击加载更多</div></li>");
			}
		}else{
			toastError(result.msg);
		}
	}else{
		toastError(result.msg);
	}
		
}