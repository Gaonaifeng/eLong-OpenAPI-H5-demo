/**   
 * @(#)OrderService.java	2016年11月21日	下午4:01:07	   
 *     
 * Copyrights (C) 2016艺龙旅行网保留所有权利
 */
package com.elong.nb.service.impl;

import java.math.BigDecimal;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.elong.nb.model.elong.CancelOrderCondition;
import com.elong.nb.model.elong.Contact;
import com.elong.nb.model.elong.CreateOrderCondition;
import com.elong.nb.model.elong.CreateOrderRoom;
import com.elong.nb.model.elong.Customer;
import com.elong.nb.model.elong.EnumConfirmationType;
import com.elong.nb.model.elong.EnumCurrencyCode;
import com.elong.nb.model.elong.EnumShowStatus;
import com.elong.nb.model.elong.OrderDetailResult;
import com.elong.nb.model.elong.OrderIdCondition;
import com.elong.nb.model.elong.OrderListCondition;
import com.elong.nb.model.elong.OrderSummary;
import com.elong.nb.model.h5.result.HotelOrderCancelResult;
import com.elong.nb.model.h5.result.HotelOrderCreateResult;
import com.elong.nb.model.h5.result.HotelOrderDetailResult;
import com.elong.nb.model.h5.result.HotelOrderListResult;
import com.elong.nb.model.h5.viewmodel.common.PagerDateResult;
import com.elong.nb.model.h5.viewmodel.common.ResultInfo;
import com.elong.nb.model.h5.viewmodel.hotel.HotelDetailViewModel;
import com.elong.nb.model.h5.viewmodel.order.CustomerInfo;
import com.elong.nb.model.h5.viewmodel.order.ListPagerRequest;
import com.elong.nb.model.h5.viewmodel.order.OrderDetailForListPager;
import com.elong.nb.model.h5.viewmodel.order.OrderDetailViewModel;
import com.elong.nb.model.h5.viewmodel.order.OrderSubmitRequest;
import com.elong.nb.model.h5.viewmodel.order.OrderSubmitResult;
import com.elong.nb.rpc.HotelOrderCancelApi;
import com.elong.nb.rpc.HotelOrderCreateApi;
import com.elong.nb.rpc.HotelOrderDetailApi;
import com.elong.nb.rpc.HotelOrderListApi;
import com.elong.nb.service.IHotelService;
import com.elong.nb.service.IOrderService;
import com.elong.nb.service.impl.common.CommonService;
import com.elong.nb.util.DateUtil;

/**
 * 订单逻辑层
 *
 * <p>
 * 修改历史:											<br>  
 * 修改日期    		修改人员   	版本	 		修改内容<br>  
 * -------------------------------------------------<br>  
 * 2016年11月21日 下午4:01:07   user     1.0    	初始化创建<br>
 * </p> 
 *
 * @author		user 
 * @version		1.0  
 * @since		JDK1.7
 */
@Service
public class OrderService implements IOrderService {
	
	private static final int PAGE_SIZE = 10;
	@Autowired
	private HotelOrderListApi orderListApi;
	@Autowired
	private HotelOrderDetailApi orderDetailApi;
	@Autowired
	private IHotelService hotelService;
	@Autowired
	private HotelOrderCancelApi hotelOrderCancelApi;
	@Autowired
	private HotelOrderCreateApi orderCreateApi;
	@Autowired
	private CommonService commonService;

	/************************************************start  订单列表 start**************************************************/
	/**
	 * 
	 * 获取订单列表数据
	 * 
	 * @param req
	 * @return 
	 * 
	 * @see com.elong.nb.service.IOrderService#getOrderList(com.elong.nb.model.h5.viewmodel.order.ListPagerRequest)
	 */
	public ResultInfo<PagerDateResult<OrderDetailForListPager>> getOrderList(ListPagerRequest req){
		ResultInfo<PagerDateResult<OrderDetailForListPager>> result = new ResultInfo<PagerDateResult<OrderDetailForListPager>>();
		
		//请求参数赋值
		OrderListCondition orderListCondition = new OrderListCondition();
		
		orderListCondition.setArrivalDateFrom(req.getArrivalDateFrom());
		orderListCondition.setArrivalDateTo(req.getArrivalDateTo());
		orderListCondition.setCreationTimeFrom(req.getCreationTimeFrom());
		orderListCondition.setCreationTimeTo(req.getCreationTimeTo());
		orderListCondition.setDepartureDateFrom(req.getDepartureDateFrom());
		orderListCondition.setDepartureDateTo(req.getDepartureDateTo());
		orderListCondition.setPageIndex(req.getPageIndex());
	
		//调用elong API
		HotelOrderListResult rawResult = orderListApi.Invoke(orderListCondition);
		if("0".equals(rawResult.getCode()) && rawResult.getResult() != null){
			//返回参数整理
			if(rawResult.getResult().getOrders() != null && rawResult.getResult().getOrders().size() > 0){
				PagerDateResult<OrderDetailForListPager> data = new PagerDateResult<OrderDetailForListPager>();
				data.setTotalCount(rawResult.getResult().getOrders().size());
				int totalPage = (rawResult.getResult().getCount() / PAGE_SIZE ) + ((rawResult.getResult().getCount() % PAGE_SIZE) == 0? 0 :1);
				data.setIsHaveNextPage((int)req.getPageIndex() < totalPage);
				data.setList(convertToDetailForlistPagers(rawResult.getResult().getOrders()));
				
				result.setData(data);
				result.setIsSuccess(true);
				result.setMsg("API获取订单列表成功");
			}else {
				result.setMsg("API获取订单列表成功，但没有更多订单了");
			}
		}else{
			result.setMsg("获取订单列表失败");
			System.out.println(rawResult.getCode());
		}
		return result;
	}
	
	/**
	 * 
	 * 获取页面使用到的数据实体
	 * 
	 * @param rawOrderList
	 * @return 
	 */
	private List<OrderDetailForListPager> convertToDetailForlistPagers(List<OrderSummary> rawOrderList){
		List<OrderDetailForListPager> vmOrderList = null;
		if(rawOrderList != null && rawOrderList.size() > 0){
			vmOrderList = new ArrayList<OrderDetailForListPager>();
			for(OrderSummary order: rawOrderList){
				OrderDetailForListPager vmInfo = convertToDetailViewModel(order);
				vmOrderList.add(vmInfo);
			}
		}
		return vmOrderList;
	}
	
	/**
	 * 
	 * 把API返回的实体转换为我们视图层的实体
	 * 
	 * @param orderSummary
	 * @return 
	 */
	private OrderDetailForListPager convertToDetailViewModel(OrderSummary orderSummary){
		OrderDetailForListPager vmInfo = new OrderDetailForListPager();
		//赋值订单基本信息
		vmInfo.setOrderId(orderSummary.getOrderId());
		vmInfo.setStatus(orderSummary.getStatus());
		vmInfo.setHotelName(orderSummary.getHotelName());
		vmInfo.setRoomTypeName(orderSummary.getRoomTypeName());
		vmInfo.setArrivalDate(orderSummary.getArrivalDate());
		vmInfo.setDepartureDate(orderSummary.getDepartureDate());
		vmInfo.setCurrencyCode(orderSummary.getCurrencyCode());
		vmInfo.setCurrencyFlag(orderSummary.getCurrencyCode().getDescription());
		vmInfo.setTotalPrice(orderSummary.getTotalPrice());
		vmInfo.setStatus(orderSummary.getStatus());
		
		//中文日期赋值
		Format f = new SimpleDateFormat("yyyy年MM月dd日");
		vmInfo.setArrivalDateCh( f.format(orderSummary.getArrivalDate().getTime()));
		vmInfo.setDepartureDateCh( f.format(orderSummary.getDepartureDate().getTime()));
		
		//共几晚
		vmInfo.setNightNumber(DateUtil.getDifferenceBetweenDay(orderSummary.getDepartureDate(), orderSummary.getArrivalDate()));
		
		return vmInfo;
	}
	/************************************************end  订单列表 end**************************************************/
	
	/************************************************start  订单详情 start**************************************************/
	/**
	 * 
	 * 获取订单详情数据
	 * 
	 * @param orderId
	 * @return
	 */
	@Override
	public ResultInfo<OrderDetailViewModel> getOrderDetail(Long orderId){
		ResultInfo<OrderDetailViewModel> result = new ResultInfo<OrderDetailViewModel>();
		
		OrderIdCondition condition = new OrderIdCondition();
		condition.setOrderId(orderId);
		
		//获取数据
		HotelOrderDetailResult rawResult = orderDetailApi.Invoke(condition);
		if(rawResult.getCode().equals("0")){
			//获取酒店详情数据
			ResultInfo<HotelDetailViewModel> hotelViewModel = 
					hotelService.getHotelDetail(rawResult.getResult().getHotelId(), new Date(), DateUtil.addDate(new Date(), 2));
			
			OrderDetailViewModel data;
			if(hotelViewModel.getIsSuccess()){
				data = convertToDetailViewModel(rawResult.getResult(), hotelViewModel.getData());
			}else{
				data = convertToDetailViewModel(rawResult.getResult(), null);
			}
			result.setData(data);
			result.setIsSuccess(true);
			result.setMsg("获取订单详情成功");
		}else{
			result.setMsg("API获取订单详情失败");
			System.out.println(rawResult.getCode());
		}
		
		return result;
	}
	
	/**
	 * 
	 * 转换订单数据为ModelView
	 * 
	 * @param orderDetail
	 * @param hotelDetail
	 * @return
	 */
	private OrderDetailViewModel convertToDetailViewModel(OrderDetailResult orderDetail, HotelDetailViewModel hotelDetail){
		OrderDetailViewModel vmInfo = new OrderDetailViewModel();
		
		//酒店信息
		if(hotelDetail != null){
			vmInfo.setHotelAddress(hotelDetail.getAddress() != null ? hotelDetail.getAddress() : "--");
			vmInfo.setHotelPhone(hotelDetail.getPhone() != null ? hotelDetail.getPhone() : "--");
		}else{
			vmInfo.setHotelAddress("--");
			vmInfo.setHotelPhone("--");
		}
		
		//入住日期
		vmInfo.setArrivalDate(DateUtil.getDateStrByParam("yyyy年MM月dd日", orderDetail.getArrivalDate()));
		//离店日期
		vmInfo.setDepartureDate(DateUtil.getDateStrByParam("yyyy年MM月dd日", orderDetail.getDepartureDate()));
		//共几晚
		vmInfo.setNightNumber(DateUtil.getDifferenceBetweenDay(orderDetail.getDepartureDate(), orderDetail.getArrivalDate()));
		//酒店名称
		vmInfo.setHotelName(orderDetail.getHotelName() != null ? orderDetail.getHotelName() : "--");
		//订单号码
		vmInfo.setOrderId(orderDetail.getOrderId());
		//系统用的订单状态
		vmInfo.setStatus(orderDetail.getStatus());
		//向用户展示的订单状态id
		vmInfo.setShowStatus(orderDetail.getShowStatus());
		//向用户展示的订单状态字符串
		vmInfo.setShowStatusString(EnumShowStatus.getDescription(orderDetail.getShowStatus()));
		//入住房型id
		vmInfo.setRoomTypeId(orderDetail.getRoomTypeId() != null ? orderDetail.getRoomTypeId() : "--");
		//入住房型名称
		vmInfo.setRoomTypeName(orderDetail.getRoomTypeName() != null ? orderDetail.getRoomTypeName() : "--");
		//房间数量
		vmInfo.setNumberOfRooms(orderDetail.getNumberOfRooms());
		//最晚到店
		vmInfo.setLatestArrivalTime(orderDetail.getLatestArrivalTime() != null ? DateUtil.getDateStrByParam("yyyy年MM月dd日", orderDetail.getLatestArrivalTime()) : "--");
		//最早到店
		vmInfo.setEarliestArrivalTime(orderDetail.getEarliestArrivalTime() != null ? DateUtil.getDateStrByParam("yyyy年MM月dd日", orderDetail.getEarliestArrivalTime()) : "--");
		//入住人信息
		if(orderDetail.getContact() != null){
			//入住人姓名
			if(orderDetail.getContact().getName() != null){
				vmInfo.setContactName(orderDetail.getContact().getName());
			}else{
				vmInfo.setContactName("--");
			}
			
			//入住人电话
			if(orderDetail.getContact().getMobile() != null){
				vmInfo.setContactMobile(orderDetail.getContact().getMobile());
			}else{
				vmInfo.setContactMobile("--");
			}
		}else{
			vmInfo.setContactName("--");
			vmInfo.setContactMobile("--");
		}
		
		//早餐信息
		if(orderDetail.getValueAdds().isEmpty() == false){
			vmInfo.setBreakfastCount(orderDetail.getValueAdds().get(0));
		}else{
			vmInfo.setBreakfastCount("--");
		}
		
		//预定日期
		vmInfo.setCreationDate(orderDetail.getCreationDate() != null ?  DateUtil.getDateStrByParam("yyyy年MM月dd日", orderDetail.getCreationDate()) : "--" );
		//货币类型
		vmInfo.setCurrencyType(orderDetail.getCurrencyCode().getDescription());
		//订单金额
		vmInfo.setTotalPriceExchanged(orderDetail.getTotalPrice().setScale(2, BigDecimal.ROUND_HALF_UP));
		//付款类型
		vmInfo.setPaymentType(orderDetail.getPaymentType().getDescriptio());
			
		//是否可以取消
		vmInfo.setIsCancelable(orderDetail.isIsCancelable());
		if(vmInfo.getIsCancelable()){
			vmInfo.setCancelTime(DateUtil.getDateStrByParam("yyyy-MM-dd", orderDetail.getCancelTime()));
		}
		
		//根据showStatus判断是否显示支付按钮
		vmInfo.setIsPay(commonService.isNeedPay(orderDetail.getStatus(), orderDetail.getShowStatus()));
		return vmInfo;
	}
	/************************************************end  订单列表 end**************************************************/
	
	/**
	 * 
	 * 取消订单
	 * 
	 * @param orderId
	 * @return
	 */
	@Override
	public Boolean cancelOrder(Long orderId){
		CancelOrderCondition condition = new CancelOrderCondition();
		condition.setOrderId(orderId);
		//取消订单
		HotelOrderCancelResult rawResult = hotelOrderCancelApi.Invoke(condition);
		
		return rawResult.getResult().isSuccesss();
	}
	
	/************************************************start  创建订单 start**************************************************/
	/**
	 * 
	 * 创建订单 
	 *
	 * @param orderReq
	 * @return 
	 *
	 * @see com.elong.nb.service.IOrderAddService#submitOrder(com.elong.nb.model.h5.viewmodel.order.OrderSubmitRequest)
	 */
	@Override
	public ResultInfo<OrderSubmitResult> submitOrder(OrderSubmitRequest orderReq) {
		ResultInfo<OrderSubmitResult> result = new ResultInfo<OrderSubmitResult>();
		
		/**************start  请求酒店&&产品详情 start**************/
		HotelDetailViewModel hotelDetail;
		ResultInfo<HotelDetailViewModel> hotelDetailRet = hotelService.getHotelDetail(orderReq.getHotelId(), orderReq.getArrivalDate(), 
				orderReq.getDepartureDate(), orderReq.getRatePlanId(), orderReq.getRoomTypeId());
		if(hotelDetailRet.getIsSuccess() && hotelDetailRet.getData() != null){
			hotelDetail = hotelDetailRet.getData();
		}else {
			result.setMsg("酒店信息不存在");
			return result;
		}
		/**************end  请求酒店&&产品详情 end**************/
		
		/**************start 请求参数整理 start**************/
		CreateOrderCondition condition = new CreateOrderCondition();
		String platOrderId = generatePlatOrderId();
		condition.setAffiliateConfirmationId(platOrderId);//产生代理方自己的订单号
		condition.setHotelId(orderReq.getHotelId());
		condition.setRoomTypeId(orderReq.getRoomTypeId());
		condition.setRatePlanId(orderReq.getRatePlanId());
		condition.setArrivalDate(orderReq.getArrivalDate());
		condition.setDepartureDate(orderReq.getDepartureDate());
		condition.setNumberOfRooms(orderReq.getNumberOfRooms());
		condition.setCustomerIPAddress(orderReq.getCustomerIPAddress());
		condition.setIsGuaranteeOrCharged(false);//根据代理商与艺龙的协定是否担保
		condition.setConfirmationType(EnumConfirmationType.NotAllowedConfirm);//默认不通知
		condition.setIsCreateOrderOnly(true);//默认只创建订单
		if(hotelDetail.getRoomList() != null 
				&& hotelDetail.getRoomList().size() > 0
				&& hotelDetail.getRoomList().get(0).getRatePlanList() != null
				&& hotelDetail.getRoomList().get(0).getRatePlanList().get(0).getRoomTypeId().equals(orderReq.getRoomTypeId())
				&& hotelDetail.getRoomList().get(0).getRatePlanList().get(0).getRatePlanId() == orderReq.getRatePlanId()){
			condition.setCustomerType(hotelDetail.getRoomList().get(0).getRatePlanList().get(0).getGuestType());
			condition.setCurrencyCode(hotelDetail.getRoomList().get(0).getRatePlanList().get(0).getCurrencyCode());
			condition.setPaymentType(hotelDetail.getRoomList().get(0).getRatePlanList().get(0).getPaymentType());
			condition.setTotalPrice(hotelDetail.getRoomList().get(0).getRatePlanList().get(0).getTotalRate().multiply(new BigDecimal(orderReq.getNumberOfRooms())));
		}else {
			result.setMsg("酒店产品信息不匹配");
			return result;
		}
		
		//赋值联系人
		Contact c = new Contact();
		c.setName(orderReq.getContact().getName());
		c.setMobile(orderReq.getContact().getMobile());
		c.setEmail(orderReq.getContact().getEmail());
		c.setGender(orderReq.getContact().getGender());
		condition.setContact(c);
		
		//赋值宾客信息 注：接口支持每个房间可以住多个人，但此处我们默认每间房只赋值一个客人信息
		List<CreateOrderRoom> orderRoomList = new ArrayList<CreateOrderRoom>();
		for(final CustomerInfo info : orderReq.getGuesterList()){
			CreateOrderRoom orderRoom = new CreateOrderRoom();
			List<Customer> cList = new ArrayList<Customer>();
			Customer customer = new Customer();
			customer.setName(info.getName());
			customer.setEmail(info.getEmail());
			customer.setMobile(info.getMobile());
			customer.setGender(info.getGender());
			cList.add(customer);
			orderRoom.setCustomers(cList);
			orderRoomList.add(orderRoom);
		}
		condition.setOrderRooms(orderRoomList);
		condition.setNumberOfCustomers(condition.getOrderRooms().size());//此处填了几个客人的信息，就有几个客人
		condition.setLatestArrivalTime(orderReq.getLatestArrivalTime());
		condition.setEarliestArrivalTime(getEarlistArrivalTime(orderReq.getLatestArrivalTime(), orderReq.getArrivalDate()));
		/**************end 请求参数整理 end**************/
		
		/**
		 * 调用hotel.order.create接口
		 */
		HotelOrderCreateResult rawCreateRet = orderCreateApi.Invoke(condition);
		if("0".equals(rawCreateRet.getCode()) && rawCreateRet.getResult()!= null){
			OrderSubmitResult createResultInfo = new OrderSubmitResult();
			createResultInfo.setOrderId(rawCreateRet.getResult().getOrderId());
			createResultInfo.setCancelTime(rawCreateRet.getResult().getCancelTime());
			createResultInfo.setPaymentDeadlineTime(rawCreateRet.getResult().getPaymentDeadlineTime());
			createResultInfo.setIsNeedPay(rawCreateRet.getResult().getPaymentDeadlineTime() != null);//该字段用来标示订单是否需要去支付页面
			
			result.setData(createResultInfo);
			result.setIsSuccess(true);
			result.setMsg("创建成功");
		}else {
			System.out.print(rawCreateRet.getCode());
			result.setMsg(commonService.getErrorCode(rawCreateRet.getCode()));//此处代理商可以翻译错误码为友好提示
		}
		
		return result;
	}
	
	/**
	 * 
	 * 产生最早到店时间
	 *
	 * @param lastArrivalDate
	 * @param arrivalDate
	 * @return
	 */
	private Date getEarlistArrivalTime(Date lastArrivalDate, Date arrivalDate){
		Date nowDay = DateUtil.getDateTimeFormat(DateUtil.getCurrentDateStr("yyyy-MM-dd") + " 00:00:00", "yyyy-MM-dd");//今天的日期对象
		if(nowDay.getTime() > arrivalDate.getTime()){
			//若是昨天
			return DateUtil.getDateTimeFormat(DateUtil.getDateStrByParam("yyyy-MM-dd", arrivalDate) + " " +"23:59:00", "yyyy-MM-dd hh:mm:ss");
		}else {
			if(lastArrivalDate.getMinutes() == 59){
				return DateUtil.addOrMinusHours(DateUtil.addOrMinusMin(lastArrivalDate, 1), -1);
			}else {
				//其他：取最晚的前一个小时
				return DateUtil.addOrMinusHours(lastArrivalDate, -1);
			}
		}
	}
	
	/**
	 * 
	 * 产生代理方平台订单号
	 * 注：为了演示，此处的订单编号为12位日期时间+4位随机数；
	 * 代理商不必采用此生成规则，而应该根据自己的业务生成订单编号；
	 * 订单编号务必保证唯一性，否则在创建订单接口，会产生重单失败；
	 *
	 * @return
	 */
	private String generatePlatOrderId(){
		String dateString = DateUtil.getCurrentDateStr("yyMMddHHmmss");
		String randomString = com.elong.nb.util.Tool.getRandomNumber(4);
		return dateString + randomString;
	}
	/************************************************end  创建订单 end**************************************************/
}
