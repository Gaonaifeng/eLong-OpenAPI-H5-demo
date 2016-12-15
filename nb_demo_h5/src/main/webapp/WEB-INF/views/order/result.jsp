<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>订单结果页</title>
	<jsp:include page="/WEB-INF/views/common/common.jsp"></jsp:include>
	<link href="../css/order/result.css" rel="stylesheet" type="text/css" />
	<script type="text/javascript" src="../js/order/result.js"></script>
</head>
<body>
	<header class="aui-bar aui-bar-nav">
	    <a class="aui-pull-left aui-btn" href="../hotel/list">
	        <span class="aui-iconfont aui-icon-left"></span>
	    </a>
	    <div class="aui-title">订单完成</div>
	    <a class="aui-pull-right aui-btn" href="./list">
	        <span class="aui-iconfont aui-icon-my"></span>
	    </a>
	</header>
	
	<section class="aui-content-padded">
		<!-- 订单状态 -->
		<div class="aui-card-list">
			<div class="aui-card-list-header aui-font-size-18">
				<div class="order-status-result"><img src="../images/common/corrent.jpg" alt="some_text" style="width:25px;height:25px">  订单提交成功</div>
			</div>
			
			<!-- 订单状态列表 -->
			<div class="aui-card-list-content">
				<div class="aui-content">
				    <ul class="aui-list aui-list-in">
				    	<li class="aui-list-item">
				            <div class="aui-list-item-inner aui-font-size-14">
				                订单进度
				            </div>
				        </li>
				        <li class="aui-list-item">
				            <div class="aui-list-item-label-icon">
				                <i class="aui-iconfont"><img src="../images/common/corrent.jpg" alt="some_text" style="width:20px;height:20px"></i>
				            </div>
				            <div class="aui-list-item-inner aui-font-size-12">
				                ${result.showStatusString}
				            </div>
				        </li>
				    </ul>
				</div>
			</div>
		
		</div>
		
		<!-- 订单信息 -->
		
		<div class="aui-content aui-margin-b-15">
		    <ul class="aui-list aui-media-list">
		        <li class="aui-list-item">
		            <div class="aui-card-list aui-font-size-12">
						<div class="aui-card-list-content">
							<section class="aui-content-padded">
								<div class="aui-list-item-text aui-col-xs-3 aui-ellipsis-1">订单号：</div>
								<div class="aui-col-xs-9 aui-ellipsis-1">${result.orderId}</div>
				    			<Br />
				    			<div class="aui-list-item-text aui-col-xs-3 aui-ellipsis-1">酒店名称：</div>
								<div class="aui-col-xs-9 aui-ellipsis-1">${result.hotelName}</div>
				    			<Br />
				    			<div class="aui-list-item-text aui-col-xs-3 aui-ellipsis-1">房型：</div>
								<div class="aui-col-xs-9 aui-ellipsis-1">${result.roomTypeName }</div>
				    			<Br />
				    			<div class="aui-list-item-text aui-col-xs-3 aui-ellipsis-1">入离日期：</div>
								<div class="aui-col-xs-9 aui-ellipsis-1">${result.arrivalDate}-${result.departureDate}  共${result.nightNumber }晚</div>
				    			<Br />
			    			</section>
						</div>
					</div>
		        </li>
			</ul>
		</div>
		
		<!-- 订单金额 -->
		<div class="aui-content aui-margin-b-15">
		    <ul class="aui-list aui-media-list">
		        <li class="aui-list-item">
		            <div class="aui-card-list aui-font-size-12">
						<div class="aui-card-list-content">
							<section class="aui-content-padded">
								<div class="aui-list-item-text aui-col-xs-3 aui-ellipsis-1">消费金额：</div>
								<div class="aui-col-xs-9 aui-ellipsis-1 aui-text-danger">${result.currencyType}${result.totalPriceExchanged}</div>
				    			<Br />
			    			</section>
						</div>
					</div>
		        </li>
			</ul>
		</div>
		
	</section>
	
	<!-- 功能按钮 -->
	<div class="aui-content">
  		<div class="aui-col-xs-12"><div class="aui-btn aui-btn-info" onclick="showOrderDetail(${result.orderId})">查看订单</div></div>
	</div>
</body>
</html>