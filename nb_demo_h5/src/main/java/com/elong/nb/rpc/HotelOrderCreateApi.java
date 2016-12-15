/**   
 * @(#)HotelOrderCreateApi.java	2016年11月21日	下午3:27:47	   
 *     
 * Copyrights (C) 2016艺龙旅行网保留所有权利
 */
package com.elong.nb.rpc;

import org.springframework.stereotype.Service;

import com.elong.nb.model.elong.CreateOrderCondition;
import com.elong.nb.model.h5.result.HotelOrderCreateResult;

/**
 * 创建订单API
 *
 * <p>
 * 修改历史:											<br>  
 * 修改日期    		修改人员   	版本	 		修改内容<br>  
 * -------------------------------------------------<br>  
 * 2016年11月21日 下午3:27:47   user     1.0    	初始化创建<br>
 * </p> 
 *
 * @author		user 
 * @version		1.0  
 * @since		JDK1.7
 */

@Service
public class HotelOrderCreateApi extends BaseApi<CreateOrderCondition, HotelOrderCreateResult> {
	@Override
	public String method() {
		return "hotel.order.create";
	}
	
	@Override
	public boolean isRequiredSSL() {
		return true;
	}
}
