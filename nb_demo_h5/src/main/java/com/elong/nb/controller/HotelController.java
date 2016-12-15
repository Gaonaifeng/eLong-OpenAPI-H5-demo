/**   
 * @(#)HotelController.java	2016年11月21日	下午1:10:23	   
 *     
 * Copyrights (C) 2016艺龙旅行网保留所有权利
 */
package com.elong.nb.controller;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.elong.nb.model.h5.viewmodel.common.JsonResult;
import com.elong.nb.model.h5.viewmodel.common.PagerDateResult;
import com.elong.nb.model.h5.viewmodel.common.ResultInfo;
import com.elong.nb.model.h5.viewmodel.hotel.HotelDetailForListPager;
import com.elong.nb.model.h5.viewmodel.hotel.HotelDetailRequest;
import com.elong.nb.model.h5.viewmodel.hotel.HotelDetailViewModel;
import com.elong.nb.model.h5.viewmodel.hotel.ListPagerRequest;
import com.elong.nb.service.IHotelService;
import com.elong.nb.util.DateUtil;


/**
 * 酒店相关页面
 *
 * <p>
 * 修改历史:											<br>  
 * 修改日期    		修改人员   	版本	 		修改内容<br>  
 * -------------------------------------------------<br>  
 * 2016年11月21日 下午1:10:23   foster     1.0    	初始化创建<br>
 * </p> 
 *
 * @author		foster 
 * @version		1.0  
 * @since		JDK1.7
 */
@Controller
@RequestMapping("/hotel")
public class HotelController {
	
	@Autowired
	private IHotelService hotelService;

	/**
	 * 
	 * 酒店列表页
	 *
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/list", method=RequestMethod.GET)
	public ModelAndView list(HttpServletRequest request,HttpServletResponse response) {
		ModelAndView mv = new ModelAndView("/WEB-INF/views/hotel/list.jsp");
		
		mv.addObject("indate", DateUtil.getDateStrByParam("yyy-MM-dd", new Date()));
		mv.addObject("offdate", DateUtil.getDateStrByParam("yyy-MM-dd", DateUtil.addDate(new Date(), 1)));
		return mv;
	}
	
	/**
	 * 
	 * 酒店列表页数据异步加载
	 *
	 * @param pagerReq
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/loadlist", method=RequestMethod.GET)
	@ResponseBody
	public JsonResult<PagerDateResult<HotelDetailForListPager>> loadList(ListPagerRequest pagerReq, HttpServletRequest request) {
		JsonResult<PagerDateResult<HotelDetailForListPager>> result = new JsonResult<PagerDateResult<HotelDetailForListPager>>();
		try{
			//参数验证与处理
			if(pagerReq == null){
				pagerReq = new ListPagerRequest();
			}
			if(pagerReq.getArrivalDate() == null){
				pagerReq.setArrivalDate(new Date());
			}
			if(pagerReq.getDepartureDate() == null){
				pagerReq.setDepartureDate(DateUtil.addDate(new Date(), 1));
			}
			if(pagerReq.getCityId() == null || "".equals(pagerReq.getCityId())){
				pagerReq.setCityId("0101");//默认设置为北京
			}
			if(pagerReq.getPageIndex() == null || pagerReq.getPageIndex() <= 0){
				pagerReq.setPageIndex(1);//默认设置为第一页
			}
			if(pagerReq.getArrivalDate().getTime() >= pagerReq.getDepartureDate().getTime()){
				result.setMsg("离店日期必须大于入店日期");
				return result;
			}
			Date nowDay = DateUtil.getDateTimeFormat(DateUtil.getCurrentDateStr("yyyy-MM-dd") + " 00:00:00", "yyyy-MM-dd");
			if(pagerReq.getArrivalDate().getTime() < DateUtil.addDate(nowDay, -1).getTime()){
				result.setMsg("入店日期必须大于等于昨天");
				return result;
			}
			if(DateUtil.getDifferenceBetweenDay(pagerReq.getDepartureDate(), pagerReq.getArrivalDate()) > 20){
				result.setMsg("入离店时间距离不能大于20天");
				return result;
			}
			
			//请求业务层获取数据
			ResultInfo<PagerDateResult<HotelDetailForListPager>> hotelListRet = hotelService.getHotelList(pagerReq);
			if (hotelListRet.getIsSuccess()) {
				result.setData(hotelListRet.getData());
			    result.setState(true);
			    result.setMsg("获取成功");
			}else{
				result.setMsg("获取数据失败：" + hotelListRet.getMsg());
				System.out.print(hotelListRet.getMsg());
			}
		}catch(Exception e){
			e.printStackTrace();
			result.setMsg("查询异常");
		}
		return result;
	}
	
	/**
	 * 
	 * 酒店详情页
	 *
	 * @param detailReq
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/detail", method=RequestMethod.GET)
	public ModelAndView detail(HotelDetailRequest detailReq, HttpServletRequest request,HttpServletResponse response) {
		ModelAndView mv = new ModelAndView("/WEB-INF/views/hotel/detail.jsp");
		try {
			//参数判断
			if(detailReq == null 
					|| detailReq.getHotelId() == null
					|| "".equals(detailReq.getHotelId().trim())
					|| detailReq.getArrivalDate() == null
					|| detailReq.getDepartureDate() == null
					|| detailReq.getArrivalDate().getTime() > detailReq.getDepartureDate().getTime()){
				mv.addObject("errorMsg", "请求参数有误");
				return mv;
			}
			
			//数据查询
			ResultInfo<HotelDetailViewModel> detailRet = hotelService.getHotelDetail(detailReq.getHotelId(), detailReq.getArrivalDate(), detailReq.getDepartureDate());
			if(detailRet.getIsSuccess()){
				HotelDetailViewModel data = detailRet.getData();
				mv.addObject("detail", data);
			}else {
				mv.addObject("errorMsg", "查询失败：" + detailRet.getMsg());
				System.out.print(detailRet.getMsg());
			}
		} catch (Exception e) {
			e.printStackTrace();
			mv.addObject("errorMsg", "查询异常");
		}
		return mv;
	}
}
