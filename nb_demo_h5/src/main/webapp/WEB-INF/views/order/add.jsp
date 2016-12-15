<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<!DOCTYPE html>
<html lang="zh-cn">
<head>
	<meta charset="utf-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
	<title>添加订单</title>
	<jsp:include page="/WEB-INF/views/common/common.jsp"></jsp:include>
	<link href="../css/order/add.css" rel="stylesheet" type="text/css" />
	<script type="text/javascript" src="../js/order/add.js"></script>
</head>
<body>
<div class="aui-content aui-font-size-14">
	<!-- 标题栏 -->
	<header class="aui-bar aui-bar-nav">
		<a class="aui-pull-left aui-btn" href="javascript:void(0)" flag="back"> <span
			class="aui-iconfont aui-icon-left"></span>
		</a>
		<div class="aui-title">订单填写</div>
	</header>
	
	<c:choose>
		<c:when test="${errorMsg != null}">
			<jsp:include page="/WEB-INF/views/common/error.jsp"></jsp:include>
		</c:when>
		<c:otherwise>
			<!-- 所有用于提交的隐藏域 -->
			<input type="hidden" id="hdHotelId" value="${detail.hotelId}"/>
			<input type="hidden" id="hdRatePlanId" value="${detail.roomList[0].ratePlanList[0].ratePlanId}"/>
			<input type="hidden" id="hdRoomTypeId" value="${detail.roomList[0].ratePlanList[0].roomTypeId}"/>
			<input type="hidden" id="hdArrivalDate" value="${detail.arrivalDate}"/>
			<input type="hidden" id="hdDepartureDate" value="${ detail.departureDate}"/>
			<input type="hidden" id="hdIsNeedPay" value="${detail.roomList[0].ratePlanList[0].isPrepay}"/>
			<input type="hidden" id="hdIsGurantee" value="${detail.roomList[0].ratePlanList[0].gurantee}"/>
			
			<!-- 酒店基本信息 -->
			<section class="aui-content">
			 <div class="aui-card-list">
		       <div class="aui-card-list-header">
		           ${detail.hotelName}
		       </div>
		       <div class="aui-card-list-content-padded aui-font-size-12">
			       	<div class="aui-hr aui-margin-b-15"></div>
			       	<div><i class="aui-iconfont aui-icon-location"></i> ${detail.address}</div>
			        <div><i class="aui-iconfont aui-icon-phone"></i> ${detail.phone}</div>
					<div><i class="aui-iconfont aui-icon-home"></i> ${detail.roomList[0].name}</div>
					<div><i class="aui-iconfont aui-icon-like"></i> ${detail.roomList[0].ratePlanList[0].ratePlanName}</div>
					<div><i class="aui-iconfont aui-icon-star"></i> ${detail.arrivalDate} 到 ${detail.departureDate} 共${detail.intervalDay}晚</div>
					<div><i class="aui-iconfont aui-icon-cert"></i> <span class="aui-text-danger">${detail.intervalDay}晚总价${detail.roomList[0].ratePlanList[0].currencyFlag}${detail.roomList[0].ratePlanList[0].totalRate}</span></div>
					<input type="hidden" id="price" value="${detail.roomList[0].ratePlanList[0].totalRate}"/>
		       </div>
		    </div>
		    </section>
		    
		    <!-- 房间 -->
		    <section class="aui-content">
			    <ul class="aui-list aui-form-list">
			      <li class="aui-list-item">
			         <div class="aui-list-item-inner">
			             <div class="aui-list-item-label">
			                 房间数量
			             </div>
			             <div class="aui-list-item-input">
			                 <input id="inputRoomAdd" type="number" placeholder="房间数量至少为1" value="1">
			             </div>
			         </div>
			       </li>			       
			       <li class="aui-list-item">
			         <div class="aui-list-item-inner">
			             <div class="aui-list-item-label">
			                 保留至
			             </div>
			 			<div class="aui-list-item-input">
			                   <select id="selectLatestArrivalTime">
			                   <c:forEach items="${lastTimeOption}" var="item">
			                   		<option value="${item.value}">${item.showTime}</option>
			                   </c:forEach>
			                   </select>
			            </div>
			         </div>
			       </li>      
			    </ul>
		    </section>
		    
		    <section id="contactContainer" class="aui-content">
				<!-- 联系人信息 -->
			    <ul class="aui-list aui-form-list aui-margin-t-15">
					<li class="aui-list-item">
			            <div class="aui-list-item-inner">
			                <div class="aui-list-item-label">
			                    <span class="mustStar">*</span>联系人姓名
			                </div>
			                <div class="aui-list-item-input">
			                    <input id="contactName" type="text" placeholder="联系人姓名">
			                </div>
			            </div>
			        </li>
					<li class="aui-list-item">
			            <div class="aui-list-item-inner">
			                <div class="aui-list-item-label">
			                    <span class="mustStar">*</span>手机号
			                </div>
			                <div class="aui-list-item-input">
			                    <input id="contactMobile" type="text" placeholder="联系人手机号">
			                </div>
			            </div>
			        </li>   
			    </ul>			    
		    </section>
			
			<!-- 宾客信息 -->
			<section id="guesterContainer" class="aui-content">		
			    <ul class="aui-list aui-form-list aui-margin-t-15">
					<li class="aui-list-item">
			            <div class="aui-list-item-inner">
			                <div class="aui-list-item-label">
			                    <span class="mustStar">*</span>姓名
			                </div>
			                <div class="aui-list-item-input">
			                    <input id="guesterName1" type="text" placeholder="入住人姓名（房间1）">
			                </div>
			            </div>
			        </li>
			    </ul>
		    </section>
		    
		    <!-- 规则部分 -->
		    <section class="aui-content">
		    	<!-- 担保规则说明 -->
			    <c:if test="${detail.roomList[0].ratePlanList[0].guaranteeRuleDesc != null}">
			    	<div class="aui-content-padded aui-text-danger">
				    	<c:forEach items="${detail.roomList[0].ratePlanList[0].guaranteeRuleDesc}" var="item">
				    		<i class="aui-iconfont aui-icon-info"></i>${item}<br/>
				    	</c:forEach>
					</div>
		    	</c:if>
		    	
		    	<!-- 预订规则说明 -->
		    	<c:if test="${detail.roomList[0].ratePlanList[0].bookingRulesDesc != null}">
			    	<div class="aui-content-padded">
						<c:forEach items="${detail.roomList[0].ratePlanList[0].bookingRulesDesc}" var="item">
				    		<i class="aui-iconfont aui-icon-info"></i>${item}<br/>
				    	</c:forEach>
					</div>	
		    	</c:if>
		    	
		    	<!-- 预付规则说明 -->
		    	<c:if test="${detail.roomList[0].ratePlanList[0].prepayRuleDesc != null}">
			    	<div class="aui-content-padded">
						<c:forEach items="${detail.roomList[0].ratePlanList[0].prepayRuleDesc}" var="item">
				    		<i class="aui-iconfont aui-icon-info"></i>${item}<br/>
				    	</c:forEach>
					</div>	
		    	</c:if>
		    	
		    	<!-- 促销规则说明 -->
		    	<c:if test="${detail.roomList[0].ratePlanList[0].drrRuleDesc != null}">
			    	<div class="aui-content-padded">
						<c:forEach items="${detail.roomList[0].ratePlanList[0].drrRuleDesc}" var="item">
				    		<i class="aui-iconfont aui-icon-info"></i>${item}<br/>
				    	</c:forEach>
					</div>	
		    	</c:if>		
		    	
		    	<!-- 增值服务说明 -->
		    	<c:if test="${detail.roomList[0].ratePlanList[0].valueDesc != null}">
			    	<div class="aui-content-padded">
						<c:forEach items="${detail.roomList[0].ratePlanList[0].valueDesc}" var="item">
				    		<i class="aui-iconfont aui-icon-star"></i>${item}<br/>
				    	</c:forEach>
					</div>	
		    	</c:if>	 
		    	
		    	<!-- 礼品描述 -->
		    	<c:if test="${detail.roomList[0].ratePlanList[0].giftDesc != null}">
			    	<div class="aui-content-padded">
						<c:forEach items="${detail.roomList[0].ratePlanList[0].giftDesc}" var="item">
				    		<i class="aui-iconfont aui-icon-like"></i>${item}<br/>
				    	</c:forEach>
					</div>	
		    	</c:if>	 
		    	
		    	<!-- 特殊提醒 -->
		    	<c:if test="${detail.roomList[0].ratePlanList[0].hAvailPolicyDesc != null}">
			    	<div class="aui-content-padded">
						<c:forEach items="${detail.roomList[0].ratePlanList[0].hAvailPolicyDesc}" var="item">
				    		<i class="aui-iconfont aui-icon-info"></i>${item}<br/>
				    	</c:forEach>
					</div>	
		    	</c:if>	   	
			</section>
			
			<!-- 提交部分 -->
			<section class="aui-grid">
			    <div class="row aui-text-center">
			        <div class="aui-col-xs-6 aui-text-danger">
			            总金额：${detail.roomList[0].ratePlanList[0].currencyFlag}<span id="totalMoney">${detail.roomList[0].ratePlanList[0].totalRate}</span>
			        </div>
			        <div class="aui-col-xs-6 aui-bg-info" flag="submit" style="color:white;cursor:pointer">
			           提交订单
			        </div>
			    </div>
		    </section>	
		</c:otherwise>
	</c:choose>
</div>
</body>
</html>
