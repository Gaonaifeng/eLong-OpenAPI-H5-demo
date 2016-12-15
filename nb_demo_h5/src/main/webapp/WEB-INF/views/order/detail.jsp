<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="zh-cn">
<head>
	<meta charset="utf-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
	<title>订单详情</title>
	<jsp:include page="/WEB-INF/views/common/common.jsp"></jsp:include>
	<link href="../css/order/detail.css" rel="stylesheet" type="text/css" />
	<script type="text/javascript" src="../js/order/detail.js"></script>
</head>
<body>

	<header class="aui-bar aui-bar-nav" id="header">
    	<a class="aui-pull-left aui-btn" href="./list">
        	<span class="aui-iconfont aui-icon-left"></span>
    	</a>
    	<div class="aui-title">酒店订单详情</div>
	</header>
	
	<c:choose>
		<c:when test="${errorMsg != null}">
			<jsp:include page="/WEB-INF/views/common/error.jsp"></jsp:include>
		</c:when>
		<c:otherwise>
			<div class="aui-content aui-margin-b-15">
		    	<ul class="aui-list aui-media-list">
		        	<li class="aui-list-item">
		            	<div class="aui-media-list-item-inner">
		                	<div class="aui-list-item-inner">
		                    	<div class="aui-list-item-title aui-ellipsis-2 aui-font-size-14">
		                        	订单编号：${detail.orderId}
		                    	</div>
		                    	<div class="aui-list-item-title aui-ellipsis-2 aui-font-size-14">
		                        	订单状态：${detail.showStatusString}
		                    	</div>
		                	</div>
		            	</div>
		        	</li>
		        	
		        	<li class="aui-list-header"></li>
		        	<li class="aui-list-item">
		            	<div class="aui-media-list-item-inner">
		                	<div class="aui-list-item-inner">
		                    	<div class="aui-list-item-title aui-ellipsis-2 aui-font-size-14">
		                        	${detail.hotelName}
		                    	</div>
		                    	<div class="aui-list-item-text aui-ellipsis-2 aui-margin-b-5 aui-font-size-12">
		                        	${detail.hotelAddress}
		                    	</div>
		                    	<hr class="aui-hr"/>
		                    	<hr class="aui-hr"/>
		                    	<div class="aui-list-item-title aui-ellipsis-2 aui-margin-t-5 aui-font-size-14">
		                        	酒店电话：${detail.hotelPhone}
		                    	</div>
		                	</div>
		            	</div>
		        	</li>
		        	
		        	<li class="aui-list-header"></li>
		        	<li class="aui-list-item">
		            	<div class="aui-media-list-item-inner">
		                	<div class="aui-list-item-inner">
		                    	<div class="aui-list-item-title aui-ellipsis-2 aui-margin-b-5 aui-font-size-14">
		                        	入离日期：${detail.arrivalDate }-${detail.departureDate }
		                    	</div>
		                    	<hr class="aui-hr"/>
		                    	<hr class="aui-hr"/>
		                    	<div class="aui-list-item-title aui-ellipsis-2 aui-margin-t-5 aui-font-size-14">
		                        	入住房型：${detail.roomTypeName }
		                    	</div>
		                    	<div class="aui-list-item-title aui-ellipsis-2 aui-font-size-14">
		                        	最晚到店：${detail.latestArrivalTime}
		                    	</div>
		                    	<div class="aui-list-item-title aui-ellipsis-2 aui-font-size-14">
		                        	入住人：${detail.contactName}
		                    	</div>
		                    	<div class="aui-list-item-title aui-ellipsis-2 aui-margin-b-5 aui-font-size-14">
		                        	联系电话：${detail.contactMobile}
		                    	</div>
		                    	<hr class="aui-hr"/>
		                    	<hr class="aui-hr"/>
		                    	<div class="aui-list-item-title aui-ellipsis-2 aui-margin-t-5 aui-font-size-14">
		                        	早餐信息：${detail.breakfastCount }
		                    	</div>
		                	</div>
		            	</div>
		        	</li>
		        	
		        	<li class="aui-list-header"></li>
		        	<li class="aui-list-item">
		            	<div class="aui-media-list-item-inner">
		                	<div class="aui-list-item-inner">
		                    	<div class="aui-list-item-title aui-ellipsis-2 aui-font-size-14">
		                        	预订时间：${detail.creationDate }
		                    	</div>
		                    	<hr class="aui-hr"/>
		                    	<hr class="aui-hr"/>
		                    	<div class="aui-list-item-title aui-ellipsis-2 aui-font-size-14">
		                        	订单金额：${detail.currencyType}${detail.totalPriceExchanged}${detail.paymentType}
		                    	</div>
		                	</div>
		            	</div>
		        	</li>
		        	
		   		</ul>
		 	</div>
		 	
			<c:choose>
				<c:when test="${detail.isCancelable == true}">
					<div class="aui-content">
				  		<div class="aui-col-xs-6"><div class="aui-btn aui-btn-info" onclick="cancelOrder(${detail.orderId})">取消订单</div></div>
					</div>
					
					<c:choose>
						<c:when test="${detail.isPay == true}">
						<div class="aui-content">
					  		<div class="aui-col-xs-6"><div class="aui-btn aui-btn-info" onclick="goPay(${detail.orderId})">去支付</div></div>
						</div>
						</c:when>
						<c:when test="${detail.isPay == false}">
							<div class="aui-content">
						  		<div class="aui-col-xs-6">&nbsp;</div>
							</div>
						</c:when>
					</c:choose>
					
				</c:when>
				<c:when test="${detail.isCancelable == false}">
					<div class="aui-content">
				  		<div class="aui-col-xs-6">&nbsp;</div>
					</div>
					
					<c:choose>
						<c:when test="${detail.isPay == true}">
						<div class="aui-content">
					  		<div class="aui-col-xs-6"><div class="aui-btn aui-btn-info" onclick="goPay(${detail.orderId})">去支付</div></div>
						</div>
						</c:when>
						<c:when test="${detail.isPay == false}">
							<div class="aui-content">
						  		<div class="aui-col-xs-6">&nbsp;</div>
							</div>
						</c:when>
					</c:choose>
					
				</c:when>
			</c:choose>
		</c:otherwise>
	</c:choose>


</body>

</html>