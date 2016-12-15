/**   
 * @(#)OrderController.java	2016年11月21日	下午1:11:38	   
 *     
 * Copyrights (C) 2016艺龙旅行网保留所有权利
 */
package com.elong.nb.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.elong.nb.model.h5.viewmodel.common.JsonResult;
import com.elong.nb.model.h5.viewmodel.common.PagerDateResult;
import com.elong.nb.model.h5.viewmodel.common.ResultInfo;
import com.elong.nb.model.h5.viewmodel.hotel.HotelDetailViewModel;
import com.elong.nb.model.h5.viewmodel.order.CreateOrderRequest;
import com.elong.nb.model.h5.viewmodel.order.LastArrivalOptionTime;
import com.elong.nb.model.h5.viewmodel.order.ListPagerRequest;
import com.elong.nb.model.h5.viewmodel.order.OrderDetailForListPager;
import com.elong.nb.model.h5.viewmodel.order.OrderDetailRequest;
import com.elong.nb.model.h5.viewmodel.order.OrderDetailViewModel;
import com.elong.nb.model.h5.viewmodel.order.OrderResultRequest;
import com.elong.nb.model.h5.viewmodel.order.OrderSubmitRequest;
import com.elong.nb.model.h5.viewmodel.order.OrderSubmitResult;
import com.elong.nb.service.IHotelService;
import com.elong.nb.service.IOrderService;
import com.elong.nb.service.IValidateService;
import com.elong.nb.util.DateUtil;

/**
 * 订单相关页面
 *
 * <p>
 * 修改历史:											<br>  
 * 修改日期    		修改人员   	版本	 		修改内容<br>  
 * -------------------------------------------------<br>  
 * 2016年11月21日 下午1:11:38   foster     1.0    	初始化创建<br>
 * </p> 
 *
 * @author		foster 
 * @version		1.0  
 * @since		JDK1.7
 */
@Controller
@RequestMapping("/order")
public class OrderController {
	
	@Autowired
	private IOrderService orderService;
	@Autowired
	private IValidateService validateService;
	@Autowired
	private IHotelService hotelService;
	
	/***********************************************start 订单列表 start***********************************************/
	/**
	 * 
	 * 订单列表页
	 *
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/list", method=RequestMethod.GET)
	public ModelAndView list(HttpServletRequest request,HttpServletResponse response) {
		ModelAndView mv = new ModelAndView("/WEB-INF/views/order/list.jsp");
		
		return mv;
	}
	
	/**
	 * 订单列表页数据异步加载
	 * 
	 * @param pagerReq
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/loadlist",method = RequestMethod.GET)
	@ResponseBody
	public JsonResult<PagerDateResult<OrderDetailForListPager>> loadlist(ListPagerRequest pagerReq, HttpServletRequest request){
		JsonResult<PagerDateResult<OrderDetailForListPager>> result = new JsonResult<PagerDateResult<OrderDetailForListPager>>();
		try{
			/**
			 * 请求参数处理
			 * 目前支持使用关键字（查询文本）页码 入住日期 离店日期和创建订单的日期来查询订单列表
			 * 但是此处只使用创建订单的日期来查询 如果为null 则默认查询最近30天内创建的订单 且默认从第一页开始查询
			 */
			if(pagerReq == null){
				pagerReq = new ListPagerRequest();
			}
			pagerReq.setArrivalDateFrom(null);
			pagerReq.setArrivalDateTo(null);
			pagerReq.setDepartureFrom(null);
			pagerReq.setDepartureTo(null);
			pagerReq.setCreationTimeTo(new Date());
			pagerReq.setCreationTimeFrom(DateUtil.addDate(pagerReq.getCreationTimeTo(), -30));
			if(pagerReq.getPageIndex() == null){
				pagerReq.setPageIndex(1);
			}
			
			//请求业务层获取数据
			ResultInfo<PagerDateResult<OrderDetailForListPager>> orderListRet =orderService.getOrderList(pagerReq);
			if(orderListRet.getIsSuccess()){
				result.setData(orderListRet.getData());
				result.setState(true);
				result.setMsg("获取成功");
			}else{
				result.setMsg("获取数据失败"+orderListRet.getMsg());
				System.out.print(orderListRet.getMsg());;
			}
		}catch(Exception e){
			e.printStackTrace();
			result.setMsg("查询异常");
		}
		
		return result;
	}
	/***********************************************end 订单列表 end***********************************************/
	
	/***********************************************start 订单详情 start***********************************************/
	/**
	 * 
	 * 订单详情页
	 *
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/detail", method=RequestMethod.GET)
	public ModelAndView detail(OrderDetailRequest detailReq,HttpServletRequest request,HttpServletResponse response) {
		ModelAndView mv = new ModelAndView("/WEB-INF/views/order/detail.jsp");
		try{
			//参数判断
			if(detailReq.getOrderId().equals(0)){
				mv.addObject("errorMsg","参数错误");
				return mv;
			}
			
			//数据查询
			ResultInfo<OrderDetailViewModel> detailRet = orderService.getOrderDetail(detailReq.getOrderId());
			if(detailRet.getIsSuccess()){
				OrderDetailViewModel data = detailRet.getData();
				mv.addObject("detail", data);
			}else {
				mv.addObject("errorMsg", "查询失败");
				System.out.print(detailRet.getMsg());
			}
		}catch(Exception e){
			e.printStackTrace();
			mv.addObject("errorMsg","查询异常");
		}
		return mv;
	}
	
	/**
	 * 
	 * 取消订单
	 * 注意：该方法只用了订单id  并没有说明取消原因
	 * 
	 * @param detailReq
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/cancel",method=RequestMethod.GET)
	@ResponseBody
	public JsonResult<Boolean> cancel(OrderDetailRequest detailReq,HttpServletRequest request,HttpServletResponse response){
		JsonResult<Boolean> result = new JsonResult<Boolean>();
		try{
			Boolean cancelRet = orderService.cancelOrder(detailReq.getOrderId());
			result.setState(cancelRet);
			result.setMsg(cancelRet ? "取消订单成功" : "取消订单失败");
		}catch(Exception e){
			e.printStackTrace();
			result.setMsg("取消订单异常");
		}
		return result;
	}
	
	/***********************************************end 订单详情 end***********************************************/
	
	/***********************************************start 创建订单页 start***********************************************/
	/**
	 * 
	 * 生成订单页面
	 * 注意：页面初始化首先得判断库存
	 *
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/add", method=RequestMethod.GET)
	public ModelAndView add(CreateOrderRequest orderRequest, HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mv = new ModelAndView("/WEB-INF/views/order/add.jsp");
		
		try {
			//参数验证
			if(orderRequest == null 
					|| orderRequest.getArrivalDate() == null
					|| orderRequest.getDepartureDate() == null
					|| orderRequest.getHotelCode() == null
					|| "".equals(orderRequest.getHotelCode().trim())
					|| orderRequest.getHotelId() == null
					|| "".equals(orderRequest.getHotelId().trim())
					|| orderRequest.getRoomTypeId() == null
					|| "".equals(orderRequest.getRoomTypeId().trim())
					|| orderRequest.getRatePlanId() <= 0
					|| orderRequest.getArrivalDate().getTime() > orderRequest.getDepartureDate().getTime()){
				mv.addObject("errorMsg", "请求参数错误");
				return mv;
			}
			
			//查询酒店详情
			ResultInfo<HotelDetailViewModel> detailRet = hotelService.getHotelDetail(orderRequest.getHotelId(), 
					orderRequest.getArrivalDate(), orderRequest.getDepartureDate(), orderRequest.getRatePlanId(), orderRequest.getRoomTypeId());
			if(detailRet.getIsSuccess()){
				HotelDetailViewModel data = detailRet.getData();
				mv.addObject("detail", data);
			}else {
				mv.addObject("errorMsg", "查询酒店详情失败");
				System.out.print(detailRet.getMsg());
				return mv;
			}
			
			//进行库存验证
			ResultInfo<Boolean> validateRet = validateService.validateData(orderRequest);
			if(validateRet.getIsSuccess() == false){
				mv.addObject("errorMsg", validateRet.getMsg());
				return mv;
			}

			//赋值最晚离开时间选项
			List<LastArrivalOptionTime> lastTimeOption = getOptionLatestArrivalTime(orderRequest.getArrivalDate());
			mv.addObject("lastTimeOption", lastTimeOption);
		} catch (Exception e) {
			e.printStackTrace();
			mv.addObject("errorMsg", "请求异常");
		}
		
		return mv;
	}
	
	/**
	 * 
	 * 获取可选的最晚到店时间 
	 * 1. 当入店日期 <= 当前日期（即入店日期是昨天或者今天）
	 * 可选时间段为：当前时间点之后的整点+1--凌晨6点
	 * 2.其他情况
	 * 可选时间段为：默认从14点到凌晨6点，
	 *
	 * @param arrivalDate
	 * @param departureDate
	 * @return
	 */
	private List<LastArrivalOptionTime> getOptionLatestArrivalTime(Date arrivalDate){
		List<LastArrivalOptionTime> list = new ArrayList<LastArrivalOptionTime>();
		
		@SuppressWarnings("deprecation")
		int nowHour = new Date().getHours();
		Date nowDay = DateUtil.getDateTimeFormat(DateUtil.getCurrentDateStr("yyyy-MM-dd") + " 00:00:00", "yyyy-MM-dd");//今天的日期对象
		
		for(int i = 14; i < 31; i++){
			//如果入住日期是昨天或者今天，则最晚入住时间必须大于当前时间
			if(arrivalDate.getTime() <= nowDay.getTime() && i-1 <= nowHour){
				continue;
			}
			
			LastArrivalOptionTime optionTime  = new LastArrivalOptionTime();
			if(i < 24){
				optionTime.setShowTime(i + ":00");
				optionTime.setValue(DateUtil.getDateStrByParam("yyyy-MM-dd", arrivalDate) + " " +optionTime.getShowTime() + ":00");
			}else if(i == 24){
				optionTime.setShowTime("23:59");
				optionTime.setValue(DateUtil.getDateStrByParam("yyyy-MM-dd", arrivalDate) + " " +optionTime.getShowTime() + ":00");
			}else{
				optionTime.setShowTime("凌晨0"+ (i-24) + ":00");
				optionTime.setValue(DateUtil.getDateStrByParam("yyyy-MM-dd", DateUtil.addDate(arrivalDate, 1)) + " 0" + (i-24) + ":00" + ":00");
			}
			
			list.add(optionTime);
		}	

		return list;
	}
	
	/**
	 * 
	 * 提交订单
	 * 注意：
	 * 1.把下单流程分为两步：1）创建订单；2)支付；此处只负责创建订单，即完成第一步。
	 * 2.可以把hotel.order.create接口返回的PaymentDeadlineTime（最晚取消时间）参数传递到支付页面，来提醒用户支付时间限制；
	 *
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/submitorder", method=RequestMethod.POST)
	@ResponseBody
	public JsonResult<OrderSubmitResult> submitOrder(HttpServletRequest request, @RequestBody OrderSubmitRequest orderRequest){
		JsonResult<OrderSubmitResult> result = new JsonResult<OrderSubmitResult>();
		try {
			/**
			 * 参数校验
			 * 注：此处统一返回参数错误，代理商在开发过程中，可根据相应的参数返回对应的提示语
			 */
			if(orderRequest == null 
					|| orderRequest.getHotelId() == null
					|| "".equals(orderRequest.getHotelId().trim())
					|| orderRequest.getRoomTypeId() == null
					|| "".equals(orderRequest.getRoomTypeId().trim())
					|| orderRequest.getRatePlanId() <= 0
					|| orderRequest.getArrivalDate() == null
					|| orderRequest.getDepartureDate() == null
					|| orderRequest.getArrivalDate().getTime() > orderRequest.getDepartureDate().getTime()
					|| orderRequest.getNumberOfRooms() <= 0
					|| orderRequest.getLatestArrivalTimeString() == null
					|| orderRequest.getContact() == null
					|| orderRequest.getGuesterList() == null
					|| orderRequest.getGuesterList().size() == 0){
				result.setMsg("请求参数错误");
				return result;
			}
			orderRequest.setLatestArrivalTime(DateUtil.getDateTimeFormat(orderRequest.getLatestArrivalTimeString(), "yyyy-MM-dd HH:mm:ss"));
			
			//业务处理
			ResultInfo<OrderSubmitResult> submitRet = orderService.submitOrder(orderRequest);
			if(submitRet.getIsSuccess()){
				result.setData(submitRet.getData());
				result.setState(true);
				result.setMsg("订单创建成功");
			}else {
				result.setMsg(submitRet.getMsg());
			}
		} catch (Exception e) {
			e.printStackTrace();
			result.setMsg("创建订单异常");
		}
		return result;
	}
	
	/***********************************************end 创建订单页 end***********************************************/
	
	/***********************************************start 订单结果页 start***********************************************/
	/**
	 * 
	 * 订单结果页
	 *
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/result", method=RequestMethod.GET)
	public ModelAndView result(OrderResultRequest orderResultRequest,HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mv = new ModelAndView("/WEB-INF/views/order/result.jsp");
		try{
			//参数判断
			if(orderResultRequest.getOrderId() == null){
				mv.addObject("errorMsg","没有联盟订单");
				return mv;
			}
			//查询订单
			ResultInfo<OrderDetailViewModel> orderDetailRet = orderService.getOrderDetail(orderResultRequest.getOrderId());
			if(orderDetailRet.getIsSuccess() == false || orderDetailRet.getData() == null){
				mv.addObject("errorMsg", "订单查询失败");
				return mv;
			}else{
				mv.addObject("result", orderDetailRet.getData());
			}
		}catch(Exception e){
			e.printStackTrace();
			mv.addObject("errorMsg", "订单查询异常");
		}
		return mv;
	}
	/***********************************************end 订单结果页 end***********************************************/
}
