/**   
 * @(#)PayController.java	2016年11月21日	下午1:25:07	   
 *     
 * Copyrights (C) 2016艺龙旅行网保留所有权利
 */
package com.elong.nb.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.elong.nb.model.elong.EnumShowStatus;
import com.elong.nb.model.h5.viewmodel.common.JsonResult;
import com.elong.nb.model.h5.viewmodel.common.ResultInfo;
import com.elong.nb.model.h5.viewmodel.order.OrderDetailViewModel;
import com.elong.nb.model.h5.viewmodel.pay.CardValidateRequest;
import com.elong.nb.model.h5.viewmodel.pay.PayIndexRequest;
import com.elong.nb.model.h5.viewmodel.pay.PaySubmitRequest;
import com.elong.nb.service.IOrderService;
import com.elong.nb.service.IPayService;
import com.elong.nb.service.impl.common.CommonService;

/**
 * 支付相关
 *
 * <p>
 * 修改历史:											<br>  
 * 修改日期    		修改人员   	版本	 		修改内容<br>  
 * -------------------------------------------------<br>  
 * 2016年11月21日 下午1:25:07   user     1.0    	初始化创建<br>
 * </p> 
 *
 * @author		user 
 * @version		1.0  
 * @since		JDK1.7
 */
@Controller
@RequestMapping("/pay")
public class PayController {
	
	@Autowired
	private IOrderService orderservice;
	@Autowired
	private IPayService payService;
	@Autowired
	private CommonService commonService;
	
	/**
	 * 
	 * 支付页面
	 *
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/index", method=RequestMethod.GET)
	public ModelAndView detail(PayIndexRequest payReq, HttpServletRequest request) {
		ModelAndView mv = new ModelAndView("/WEB-INF/views/pay/index.jsp");
		
		try {
			//参数验证
			if(payReq == null 
					|| payReq.getOrderId() == null
					|| payReq.getOrderId() == 0){
				mv.addObject("errorMsg", "参数错误");
				return mv;
			}
			
			//查询订单
			ResultInfo<OrderDetailViewModel> orderDetailRet = orderservice.getOrderDetail(payReq.getOrderId());
			if(orderDetailRet.getIsSuccess() == false || orderDetailRet.getData() == null){
				mv.addObject("errorMsg", "订单查询失败");
				return mv;
			}
			
			//订单状态判断
			if(commonService.isNeedPay(orderDetailRet.getData().getStatus(), orderDetailRet.getData().getShowStatus()) == false){
				mv.addObject("errorMsg", "错误的支付请求！订单状态：" + EnumShowStatus.parseEnumShowStatus(orderDetailRet.getData().getShowStatus()).getDescription());
				return mv;
			}
			
			mv.addObject("orderDetail", orderDetailRet.getData());
		} catch (Exception e) {
			e.printStackTrace();
			mv.addObject("errorMsg", "未知的异常");
		}
		
		return mv;
	}
	
	/**
	 * 
	 * 信用卡验证
	 * state : 代表信用卡号验证是否成功
	 * data : 代表是否需要提供CVV
	 *
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/cardvalidate", method=RequestMethod.GET)
	@ResponseBody
	public JsonResult<Boolean> cardValidate(CardValidateRequest cardValidateReq, HttpServletRequest request){
		JsonResult<Boolean> result = new JsonResult<Boolean>();
		try {
			//参数验证
			if(cardValidateReq == null
					|| cardValidateReq.getCardNo() == null
					|| "".equals(cardValidateReq.getCardNo().trim())
					|| cardValidateReq.getCardNo().trim().length() > 20){
				result.setMsg("参数不正确");
			}
			
			//调用API进行验证
			ResultInfo<Boolean> validateRet = payService.validateCreditCard(cardValidateReq.getCardNo());
			result.setState(validateRet.getIsSuccess());//信用卡号码验证是否成功
			result.setMsg(validateRet.getMsg());
			result.setData(validateRet.getData());//是否需要CVV信息
		} catch (Exception e) {
			e.printStackTrace();
			result.setMsg("信用卡验证异常");
		}
		return result;
	}
	
	/**
	 * 
	 * 提交支付
	 *
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/submitpay", method=RequestMethod.POST)
	@ResponseBody
	public JsonResult<Boolean> submitOrder(PaySubmitRequest paySubmitReq, HttpServletRequest request){
		JsonResult<Boolean> result = new JsonResult<Boolean>();
		try {
			//参数验证
			if(paySubmitReq == null
					|| paySubmitReq.getCardNo() == null
					|| "".equals(paySubmitReq.getCardNo().trim())
					|| paySubmitReq.getExpiration() == null
					|| "".equals(paySubmitReq.getExpiration().trim())
					|| paySubmitReq.getExpiration().trim().length() != 6
					|| paySubmitReq.getHolderName() == null
					|| "".equals(paySubmitReq.getHolderName().trim())
					|| paySubmitReq.getIdNo() == null
					|| "".equals(paySubmitReq.getIdNo().trim())
					|| paySubmitReq.getIdType() == null
					|| paySubmitReq.getOrderId() == 0
					|| paySubmitReq.getCardNo().length() > 20
					|| paySubmitReq.getHolderName().length() > 20
					|| paySubmitReq.getIdNo().length() > 20){
				result.setMsg("请求参数错误");
				return result;
			}
			
			//支付请求
			ResultInfo<Boolean> payRet = payService.submitOrderPay(paySubmitReq);
			result.setMsg(payRet.getMsg());
			result.setState(payRet.getIsSuccess());
		} catch (Exception e) {
			e.printStackTrace();
			result.setMsg("支付请求异常");
		}
		return result;
	}
}
