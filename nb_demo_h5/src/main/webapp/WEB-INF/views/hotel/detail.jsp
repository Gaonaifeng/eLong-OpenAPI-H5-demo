<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %> 
<!DOCTYPE html>
<html lang="zh-cn">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport"
	content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
<jsp:include page="/WEB-INF/views/common/common.jsp"></jsp:include>
<link href="../plugins/aui/css/aui-slide.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="../plugins/aui/script/aui-slide.js"></script>
<link href="../css/hotel/detail.css" rel="stylesheet" type="text/css" />
<title>酒店详情</title>
</head>
<body>
<div class="aui-content aui-font-size-14">
	<!-- 标题栏 -->
	<header class="aui-bar aui-bar-nav">
		<a class="aui-pull-left aui-btn" href="../hotel/list"> <span
			class="aui-iconfont aui-icon-left"></span>
		</a>
		<div class="aui-title">酒店详情</div>
	</header>
	
	<c:choose>
		<c:when test="${errorMsg != null}">
			<jsp:include page="/WEB-INF/views/common/error.jsp"></jsp:include>
		</c:when>
		<c:otherwise>
			<c:if test="${detail.hotelImageList != null}">
			<!-- 酒店图片-->
			<div class="aui-content">
				<section class="aui-grid">
					<div class="aui-row">
						<!-- 酒店图片轮播图 -->
						<div id="aui-slide3">
							<div class="aui-slide-wrap">
								<c:forEach items="${detail.hotelImageList}" var="item" begin="0" end="9">
									<div class="aui-slide-node bg-dark">
										<img src="${item.url}"/>
									</div>
								</c:forEach>
							</div>
							<div class="aui-slide-page-wrap">
								<!--分页容器-->
							</div>
						</div>
					</div>
				</section>
			</div>
			</c:if>
			
			<!-- 酒店基本信息 -->
			<section class="aui-content">
			 <div class="aui-card-list">
			 	<div class="aui-card-list-header">
		           ${detail.hotelName}
		       </div>
		       <div class="aui-card-list-content-padded aui-font-size-12">
			       	<div><i class="aui-iconfont aui-icon-location"></i> ${detail.address}</div>
			        <div><i class="aui-iconfont aui-icon-phone"></i> ${detail.phone}</div>
		       </div>
		    </div>
		    </section>			
		
			<!-- 入住时间 -->
			<div class="aui-hr"></div>
			<div class="aui-grid aui-text-center">
				<div class="aui-row">
					<div class="aui-col-xs-4">
						<span class="aui-font-size-12">入住</span><br/>
						<span class="aui-text-info">${detail.arrivalDate}</span>
					</div>
					<div class="aui-col-xs-4">${detail.intervalDay } 晚</div>
					<div class="aui-col-xs-4">
						<span class="aui-font-size-12">离店</span><br/>
						<span class="aui-text-info">${detail.departureDate}</span>
					</div>
				</div>
			</div>
		
			<!-- 房型 -->
			<div class="aui-content aui-margin-b-15">
				<ul class="aui-list aui-media-list">
					<li class="aui-list-header">房型列表</li>
					<c:choose>
						<c:when test="${detail.roomList == null}">
							<li class="aui-list-item">
								<div class="aui-list-item-inner">
									<div class="aui-list-item-text aui-text-center">
									没有可售的房型！
									</div>
								</div>
							</li>
						</c:when>
						<c:otherwise>
							<c:forEach items="${detail.roomList}" varStatus="i" var="room">
							<li class="aui-list-item">
								<div class="aui-media-list-item-inner">
									<div class="aui-list-item-media">
										<img src="${room.imageUrl}">	 
									</div>
									<div class="aui-list-item-inner">
										<div class="aui-list-item-text">
											<div class="aui-list-item-title aui-font-size-14">${room.name }</div>
										</div>
										<div class="aui-list-item-text aui-font-size-14">
											<span class="aui-font-size-12">${room.area}平米 ${room.bedType}</span>
											<span class="aui-font-size-14 aui-text-danger">${detail.currencyFlag}
											<fmt:formatNumber value="${room.lowRate}" pattern="0"/> 
											起</span> 
											<a href="javascript:void(0)" class="plusBtn" plus-data="${room.roomId}"><i
												class="aui-iconfont aui-icon-plus"></i></a> <a
												href="javascript:void(0)" class="minusBtn" style="display: none"
												minus-data="${room.roomId}"><i class="aui-iconfont aui-icon-minus"></i></a>
										</div>
									</div>
								</div>
								
								<c:if test="${room.ratePlanList != null}">
								<!-- 产品列表 -->
								<div class="aui-content" id="child-div-${room.roomId}" style="display: none">
									<ul class="aui-list aui-list-in">
									<c:forEach items="${room.ratePlanList}" varStatus="i" var="plan">
										<li class="aui-list-item">
											<div class="aui-list-item-inner">
												<div class="aui-list-item-text">
													<div class="aui-list-item-title aui-font-size-12">
														<div class="aui-grid">
															<div class="aui-row">
																<div class="aui-col-xs-4 aui-text-left">
																	${plan.ratePlanName }
																</div>
																<div class="aui-col-xs-4 aui-text-left">
																	<c:choose>
																		<c:when test="${plan.gurantee}"> 
																		<div class="aui-label aui-label-danger aui-label-outlined">担保</div>
																		</c:when>
																	</c:choose>
																	<c:choose>
																	   <c:when test="${plan.isPrepay}"> 
																	      <div class="aui-label aui-label-danger aui-label-outlined">${plan.paymenTypeDesc}</div>	     
																	   </c:when>
																	   <c:otherwise>
																	     <div class="aui-label aui-label-info aui-label-outlined">${plan.paymenTypeDesc}</div>	
																	   </c:otherwise>
																	</c:choose>
																	<c:if test="${plan.instantConfirmation}">
																		<div class="aui-label aui-label-info aui-label-outlined">立即确认</div>
																	</c:if>
																	<c:if test="${plan.promotion}">
																		<div class="aui-label aui-label-info aui-label-outlined">促销</div>
																	</c:if>
																	<c:if test="${plan.haveGift}">
																		<div class="aui-label aui-label-info aui-label-outlined">礼包</div>
																	</c:if>
																	<c:if test="${plan.lastMinuteSale}">
																		<div class="aui-label aui-label-info aui-label-outlined">特价</div>
																	</c:if>
																</div>
																<div class="aui-col-xs-2 aui-text-danger">
																	${plan.currencyFlag}
																	<fmt:formatNumber value="${plan.averageRate}" pattern="0"/>
																</div>
																<div class="aui-col-xs-2">
																	<div class="aui-btn aui-btn-info" flag="book" data="arrivalDate=${detail.arrivalDate}&departureDate=${detail.departureDate}&hotelId=${detail.hotelId}&roomTypeId=${plan.roomTypeId}&ratePlanId=${plan.ratePlanId}&hotelCode=${plan.hotelCode}&totalPrice=${plan.totalRate}">
																		预订
																	</div>
																</div>
															</div>
														</div>
													</div>
												</div>
											</div>
										</li>
									</c:forEach>
									</ul>
								</div>
								</c:if>
							</li>
							</c:forEach>
						</c:otherwise>
					</c:choose>
				</ul>
			</div>			
			<script type="text/javascript" src="../js/hotel/detail.js"></script>
		</c:otherwise>
	</c:choose>
</div>
</body>
</html>
