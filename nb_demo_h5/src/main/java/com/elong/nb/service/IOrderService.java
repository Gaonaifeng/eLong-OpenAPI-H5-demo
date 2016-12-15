/**   
 * @(#)IOrderService.java	2016年11月21日	下午4:00:35	   
 *     
 * Copyrights (C) 2016艺龙旅行网保留所有权利
 */
package com.elong.nb.service;

import com.elong.nb.model.h5.viewmodel.common.PagerDateResult;
import com.elong.nb.model.h5.viewmodel.common.ResultInfo;
//import com.elong.nb.model.h5.viewmodel.order.OrderDetailViewModel;
import com.elong.nb.model.h5.viewmodel.order.ListPagerRequest;
import com.elong.nb.model.h5.viewmodel.order.OrderDetailForListPager;
import com.elong.nb.model.h5.viewmodel.order.OrderDetailViewModel;
import com.elong.nb.model.h5.viewmodel.order.OrderSubmitRequest;
import com.elong.nb.model.h5.viewmodel.order.OrderSubmitResult;

/**
 * (类型功能说明描述)
 *
 * <p>
 * 修改历史:											<br>  
 * 修改日期    		修改人员   	版本	 		修改内容<br>  
 * -------------------------------------------------<br>  
 * 2016年11月21日 下午4:00:35   user     1.0    	初始化创建<br>
 * </p> 
 *
 * @author		user 
 * @version		1.0  
 * @since		JDK1.7
 */
public interface IOrderService {
	
	/**
	 * 
	 * 获取订单列表数据
	 * 
	 * @param req
	 * @return 
	 * @throws Exception
	 */
	public ResultInfo<PagerDateResult<OrderDetailForListPager>> getOrderList(ListPagerRequest req);
	
	/**
	 * 
	 * 获取订单详情数据
	 * 
	 * @param orderId
	 * @return
	 */
	public ResultInfo<OrderDetailViewModel> getOrderDetail(Long orderId);

	/**
	 * 
	 * 取消订单
	 * 
	 * @param orderId
	 * @return
	 */
	public Boolean cancelOrder(Long orderId);
	
	/**
	 * 
	 * 创建订单
	 *
	 * @param orderReq
	 * @return
	 */
	ResultInfo<OrderSubmitResult> submitOrder(OrderSubmitRequest orderReq);
}
