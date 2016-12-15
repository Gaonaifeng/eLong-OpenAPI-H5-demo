/**   
 * @(#)HotelService.java	2016年11月21日	下午3:58:15	   
 *     
 * Copyrights (C) 2016艺龙旅行网保留所有权利
 */
package com.elong.nb.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.elong.nb.model.elong.BookingRule;
import com.elong.nb.model.elong.DrrRule;
import com.elong.nb.model.elong.EnumHotelImageType;
import com.elong.nb.model.elong.EnumPaymentType;
import com.elong.nb.model.elong.EnumQueryType;
import com.elong.nb.model.elong.Gift;
import com.elong.nb.model.elong.GuaranteeRule;
import com.elong.nb.model.elong.HAvailPolicy;
import com.elong.nb.model.elong.Hotel;
import com.elong.nb.model.elong.HotelDetailCondition;
import com.elong.nb.model.elong.HotelListCondition;
import com.elong.nb.model.elong.Position;
import com.elong.nb.model.elong.PrepayRule;
import com.elong.nb.model.elong.ValueAdd;
import com.elong.nb.model.h5.result.HotelListResult;
import com.elong.nb.model.h5.viewmodel.common.CommonConstant;
import com.elong.nb.model.h5.viewmodel.common.PagerDateResult;
import com.elong.nb.model.h5.viewmodel.common.ResultInfo;
import com.elong.nb.model.h5.viewmodel.hotel.HotelDetailForListPager;
import com.elong.nb.model.h5.viewmodel.hotel.HotelDetailViewModel;
import com.elong.nb.model.h5.viewmodel.hotel.HotelImage;
import com.elong.nb.model.h5.viewmodel.hotel.HotelRoom;
import com.elong.nb.model.h5.viewmodel.hotel.ListPagerRequest;
import com.elong.nb.model.h5.viewmodel.hotel.RoomRatePlan;
import com.elong.nb.rpc.HotelDetailApi;
import com.elong.nb.rpc.HotelListApi;
import com.elong.nb.service.IHotelService;
import com.elong.nb.util.DateUtil;

/**
 * 酒店业务逻辑层
 *
 * <p>
 * 修改历史:											<br>  
 * 修改日期    		修改人员   	版本	 		修改内容<br>  
 * -------------------------------------------------<br>  
 * 2016年11月21日 下午3:58:15   user     1.0    	初始化创建<br>
 * </p> 
 *
 * @author		user 
 * @version		1.0  
 * @since		JDK1.7
 */
@Service
public class HotelService implements IHotelService{
	
	//每页多少条
	private static final int PAGE_SIZE = 10;
	
	@Autowired
	private HotelListApi hotelListApi;
	
	@Autowired
	private HotelDetailApi hotelDetailApi;

	/**
	 * 
	 * 获取酒店列表数据
	 *
	 * @param req
	 * @return 
	 *
	 * @see com.elong.nb.service.IHotelService#getHotelList(com.elong.nb.model.h5.viewmodel.hotel.ListPagerRequest)
	 */
	@Override
	public ResultInfo<PagerDateResult<HotelDetailForListPager>> getHotelList(ListPagerRequest req){
		ResultInfo<PagerDateResult<HotelDetailForListPager>> result = new ResultInfo<PagerDateResult<HotelDetailForListPager>>();
		
		//请求参数赋值
		HotelListCondition hotelListCondition = new HotelListCondition();
		hotelListCondition.setArrivalDate(req.getArrivalDate());
		hotelListCondition.setDepartureDate(req.getDepartureDate());
		hotelListCondition.setCityId(req.getCityId());
		hotelListCondition.setPageIndex(req.getPageIndex());
		hotelListCondition.setPageSize(PAGE_SIZE);
		hotelListCondition.setCustomerType("None");//宾客类型默认为全部
		hotelListCondition.setResultType("3,4");//返回的数据默认为仅仅酒店详情
		if(req.getLatitude() != null && req.getLongitude() != null){
			Position position = new Position();
			position.setLatitude(req.getLatitude());
			position.setLongitude(req.getLongitude());
			position.setRadius(1000);//默认搜索附近1000米的酒店
			hotelListCondition.setPosition(position);
		}
		if(req.getQueryText() != null && "".equals(req.getQueryText().trim()) == false){
			hotelListCondition.setQueryText(req.getQueryText().trim());
			hotelListCondition.setQueryType(EnumQueryType.Intelligent);//智能搜索：支持酒店名称、位置搜索
		}
		
		//调用elong API
		HotelListResult rawResult = hotelListApi.Invoke(hotelListCondition);
		if("0".equals(rawResult.getCode()) && rawResult.getResult()!= null){
			//返回参数整理
			PagerDateResult<HotelDetailForListPager> data = new PagerDateResult<HotelDetailForListPager>();
			data.setTotalCount(rawResult.getResult().getCount());
			int totalPage = (rawResult.getResult().getCount() / PAGE_SIZE) + ((rawResult.getResult().getCount() % PAGE_SIZE) == 0 ? 0 : 1);
			data.setIsHaveNextPage((int)req.getPageIndex() < totalPage);
			data.setList(convertToDetailForListPagers(rawResult.getResult().getHotels()));
			
			result.setData(data);
			result.setIsSuccess(true);
			result.setMsg("获取酒店列表成功");
		}else {
			result.setMsg("API获取酒店列表失败");
			System.out.print(rawResult.getCode());
		}
		return result;
	}
	
	/**
	 * 
	 * 获取页面使用到的数据实体 
	 *
	 * @param rawHotelList
	 * @return
	 */
	private List<HotelDetailForListPager> convertToDetailForListPagers(List<Hotel> rawHotelList){
		List<HotelDetailForListPager> vmHotelList = null;
		if(rawHotelList != null && rawHotelList.size() > 0){
			vmHotelList = new ArrayList<HotelDetailForListPager>();
			for (Hotel hotel : rawHotelList) {
				HotelDetailForListPager vmInfo = convertToDetailViewModel(hotel);
				vmHotelList.add(vmInfo);
			}
		}
		
		return vmHotelList;
	}
	
	/**
	 * 
	 * 获取酒店详情 
	 *
	 * @param hotelId 酒店ID
	 * @param arrivalDate 到店日期
	 * @param departdDate 离店日期
	 * @return
	 */
	@Override
	public ResultInfo<HotelDetailViewModel> getHotelDetail(String hotelId, Date arrivalDate, Date departdDate){
		return getHotelDetail(hotelId, arrivalDate, departdDate, null, null);
	}

	/**
	 * 
	 * 获取酒店详情数据 
	 *
	 * @param hotelId 酒店ID
	 * @param arrivalDate 到店日期
	 * @param departdDate 离店日期
	 * @param ratePlanId 产品ID
	 * @param roomTypeId 房型ID 
	 * @return
	 */
	@Override
	public ResultInfo<HotelDetailViewModel> getHotelDetail(String hotelId, Date arrivalDate, Date departdDate, Integer ratePlanId, String roomTypeId) {
		ResultInfo<HotelDetailViewModel> result = new ResultInfo<HotelDetailViewModel>();
		
		//请求参数整理
		HotelDetailCondition condition = new HotelDetailCondition();
		condition.setArrivalDate(arrivalDate);
		condition.setDepartureDate(departdDate);
		condition.setHotelIds(hotelId);
		condition.setOptions("0,1,2,3,4,5,8");
		if(ratePlanId != null){
			condition.setRatePlanId(ratePlanId);
		}
		if(roomTypeId != null){
			condition.setRoomTypeId(roomTypeId);
		}
		
		//请求elongAPI
		HotelListResult rawResult = hotelDetailApi.Invoke(condition);
		if("0".equals(rawResult.getCode()) && rawResult.getResult()!= null){
			if(rawResult.getResult().getHotels() != null && rawResult.getResult().getHotels().size() > 0){
				HotelDetailViewModel data = convertToDetailViewModel(rawResult.getResult().getHotels().get(0));
				data.setArrivalDate(DateUtil.getDateStrByParam("yyyy-MM-dd", arrivalDate));
				data.setDepartureDate(DateUtil.getDateStrByParam("yyyy-MM-dd", departdDate));
				data.setIntervalDay((int)DateUtil.getDifferenceBetweenDay(departdDate, arrivalDate));
				result.setData(data);
			}
			result.setIsSuccess(true);
			result.setMsg("获取酒店列表成功");
		}else {
			result.setMsg("API获取酒店列表失败");
			System.out.print(rawResult.getCode());
		}
		return result;
	}
	
	/**
	 * 
	 * 把API返回的实体转换为我们视图层的实体
	 *
	 * @param hotel
	 * @return
	 */
	private HotelDetailViewModel convertToDetailViewModel(Hotel hotel){
		HotelDetailViewModel vmInfo = new HotelDetailViewModel();
		
		//赋值酒店基本信息
		vmInfo.setHotelId(hotel.getHotelId());
		vmInfo.setLowRate(hotel.getLowRate());
		vmInfo.setCurrencyFlag(hotel.getCurrencyCode() != null ? hotel.getCurrencyCode().getDescription() : "￥");
		if(hotel.getDetail() != null){
			vmInfo.setHotelName(hotel.getDetail().getHotelName());
			vmInfo.setDistrictName(hotel.getDetail().getDistrictName());
			vmInfo.setBusinessZoneName(hotel.getDetail().getBusinessZoneName());
			vmInfo.setReviewCount(Integer.valueOf(hotel.getDetail().getReview().getCount()));
			vmInfo.setThumbnailUrl(hotel.getDetail().getThumbNailUrl());
			vmInfo.setAddress(hotel.getDetail().getAddress());
			vmInfo.setPhone(hotel.getDetail().getPhone());
			vmInfo.setTraffic(hotel.getDetail().getTraffic());
		}else {
			vmInfo.setHotelName("--");
			vmInfo.setDistrictName("--");
			vmInfo.setBusinessZoneName("--");
			vmInfo.setReviewCount(0);
			vmInfo.setThumbnailUrl(CommonConstant.DEFAULT_HOTEL_IMAGE_THUMBNAIL);
			vmInfo.setAddress("--");
		}
		if(vmInfo.getThumbnailUrl() == null || "".equals(vmInfo.getThumbnailUrl())){
			vmInfo.setThumbnailUrl(CommonConstant.DEFAULT_HOTEL_IMAGE_THUMBNAIL);
		}
		//获取酒店的图片
		vmInfo.setHotelImageList(getHotelImages(hotel));
		
		//赋值房型属性
		if(hotel.getRooms() != null && hotel.getRooms().size() > 0){
			List<HotelRoom> hotelRoomList = new ArrayList<HotelRoom>();
			for(com.elong.nb.model.elong.Room rawRoom : hotel.getRooms()){
				HotelRoom room = new HotelRoom();
				//赋值房型基本信息
				room.setRoomId(rawRoom.getRoomId());
				room.setName(rawRoom.getName());
				room.setArea(rawRoom.getArea() != null ? rawRoom.getArea() : "--");
				room.setBedType(rawRoom.getBedType() != null ? rawRoom.getBedType() : "--");
				room.setImageUrl(rawRoom.getImageUrl() != null ? rawRoom.getImageUrl() : CommonConstant.DEFAULT_HOTEL_IMAGE_THUMBNAIL);
				
				BigDecimal lowRate = new BigDecimal(Integer.MAX_VALUE);
				
				//赋值房型的产品
				if(rawRoom.getRatePlans() != null && rawRoom.getRatePlans().size() > 0){
					List<RoomRatePlan> ratePlanList = new ArrayList<RoomRatePlan>();
					for(com.elong.nb.model.elong.ListRatePlan rawRatePlan : rawRoom.getRatePlans()){
						//若不可销售，则不显示
						if(rawRatePlan.isStatus() == false){
							continue;
						}
						
						//赋值产品信息
						RoomRatePlan ratePlan = new RoomRatePlan();
						ratePlan.setRatePlanId(rawRatePlan.getRatePlanId());
						ratePlan.setRoomTypeId(rawRatePlan.getRoomTypeId());
						ratePlan.setHotelCode(rawRatePlan.getHotelCode());
						ratePlan.setRatePlanName(rawRatePlan.getRatePlanName());
						ratePlan.setStatus(rawRatePlan.isStatus());
						ratePlan.setTotalRate(rawRatePlan.getTotalRate());
						ratePlan.setAverageRate(rawRatePlan.getAverageRate());
						ratePlan.setGuestType(rawRatePlan.getCustomerType());
						ratePlan.setCurrencyCode(rawRatePlan.getCurrencyCode());
						ratePlan.setCurrencyFlag(rawRatePlan.getCurrencyCode().getDescription());
						ratePlan.setPaymentType(rawRatePlan.getPaymentType());
						ratePlan.setPaymenTypeDesc(rawRatePlan.getPaymentType().getDescriptio());
						//是否立即确认
						ratePlan.setInstantConfirmation(rawRatePlan.isInstantConfirmation());
						//是否特价
						ratePlan.setLastMinuteSale(rawRatePlan.isIsLastMinuteSale());
						
						//赋值预订规则
						ratePlan.setBookingRuleIds(rawRatePlan.getBookingRuleIds());
						ratePlan.setBookingRulesDesc(getBookingRulesDesc(rawRatePlan.getBookingRuleIds(), hotel.getBookingRules()));
						
						//赋值担保属性
						ratePlan.setGuaranteeRuleIds(rawRatePlan.getGuaranteeRuleIds());
						ratePlan.setGurantee("".equals(rawRatePlan.getGuaranteeRuleIds()) == false);
						ratePlan.setGuaranteeRuleDesc(getGuaranteeRuleDesc(rawRatePlan.getGuaranteeRuleIds(), hotel.getGuaranteeRules()));
						
						//赋值预付规则
						ratePlan.setIsPrepay(rawRatePlan.getPaymentType() == EnumPaymentType.Prepay);
						ratePlan.setPrepayRuleIds(rawRatePlan.getPrepayRuleIds());
						ratePlan.setPrepayRuleDesc(getPrepayRuleDesc(rawRatePlan.getPrepayRuleIds(), hotel.getPrepayRules()));
						
						//赋值促销规则
						ratePlan.setDrrRuleIds(rawRatePlan.getDrrRuleIds());
						ratePlan.setPromotion(rawRatePlan.getDrrRuleIds() != null ? true : false);
						ratePlan.setDrrRuleDesc(getDrrRuleDesc(rawRatePlan.getDrrRuleIds(), hotel.getDrrRules()));
						
						//赋值增值服务
						ratePlan.setValueAddIds(rawRatePlan.getValueAddIds());
						ratePlan.setPlusValue(rawRatePlan.getValueAddIds() != null ? true : false);
						ratePlan.setValueDesc(getValueDesc(rawRatePlan.getValueAddIds(), hotel.getValueAdds() ));
						
						//赋值礼品信息
						ratePlan.setGiftIds(rawRatePlan.getGiftIds());
						ratePlan.setHaveGift(rawRatePlan.getGiftIds() != null ? true : false);
						ratePlan.setGiftDesc(getGiftDesc(rawRatePlan.getGiftIds(), hotel.getGifts()));
						
						//赋值特殊提醒
						ratePlan.sethAvailPolicyIds(rawRatePlan.getHAvailPolicyIds());
						ratePlan.sethAvailPolicyDesc(getAvailPolicyDesc(rawRatePlan.getHAvailPolicyIds(), hotel.getHAvailPolicys()));
						
						ratePlanList.add(ratePlan);
						if(lowRate.compareTo(rawRatePlan.getAverageRate()) == 1){
							lowRate = rawRatePlan.getAverageRate();
						}
					}
					//设置最产品中房型最低价
					room.setLowRate(lowRate);
					room.setRatePlanList(ratePlanList);
				}
				hotelRoomList.add(room);
			}
			vmInfo.setRoomList(hotelRoomList);
		}
		return vmInfo;
	}

	/** 
	 * 获取酒店的图片
	 *
	 * @param hotel
	 * @return
	 */
	private List<HotelImage> getHotelImages(Hotel hotel) {
		List<HotelImage> imageList = new ArrayList<HotelImage>();
		if(hotel.getImages() != null && hotel.getImages().size() > 0){
			for(com.elong.nb.model.elong.HotelImg rawImg : hotel.getImages()){
				HotelImage image = new HotelImage();
				image.setImageTitle(EnumHotelImageType.getImageTitle(rawImg.getType()));
				image.setRoomId(rawImg.getRoomId());
				if(rawImg.getLocations() != null && rawImg.getLocations().size() > 0){
					//取第一张图片，即：SizeType = 1，350px*350px
					image.setUrl(rawImg.getLocations().get(0).getUrl());
				}
				imageList.add(image);
			}
		}else {
			imageList.add(new HotelImage(CommonConstant.DEFAULT_HOTEL_IMAGE));
		}
		return imageList;
	}
	
	/**
	 * 
	 * 获取预订规则描述
	 *
	 * @param idList
	 * @param ruleList
	 * @return
	 */
	private List<String> getBookingRulesDesc(String idList, List<BookingRule> ruleList){
		List<String> strList = null;
		if(idList != null && "".equals(idList) == false && ruleList != null && ruleList.size() > 0){
			String[] ids = idList.split(",");
			strList = new ArrayList<String>();
			for(String id : ids){
				for(BookingRule r : ruleList){
					if(r.getBookingRuleId() == Long.parseLong(id)){
						strList.add(r.getDescription());
						break;
					}
				}
			}
		}
		return strList;
	}
	
	/**
	 * 
	 * 获取担保规则描述
	 *
	 * @param idList
	 * @param ruleList
	 * @return
	 */
	private List<String> getGuaranteeRuleDesc(String idList, List<GuaranteeRule> ruleList){
		List<String> strList = null;
		if(idList != null && "".equals(idList) == false && ruleList != null && ruleList.size() > 0){
			String[] ids = idList.split(",");
			strList = new ArrayList<String>();
			for(String id : ids){
				for(GuaranteeRule r : ruleList){
					if(r.getGuranteeRuleId() == Integer.parseInt(id)){
						strList.add(r.getDescription());
						break;
					}
				}
			}
		}
		return strList;
	}
	
	/**
	 * 
	 * 获取预付规则
	 *
	 * @param idList
	 * @param ruleList
	 * @return
	 */
	private List<String> getPrepayRuleDesc(String idList, List<PrepayRule> ruleList){
		List<String> strList = null;
		if(idList != null && "".equals(idList) == false && ruleList != null && ruleList.size() > 0){
			String[] ids = idList.split(",");
			strList = new ArrayList<String>();
			for(String id : ids){
				for(PrepayRule r : ruleList){
					if(r.getPrepayRuleId() == Integer.parseInt(id)){
						strList.add(r.getDescription());
						break;
					}
				}
			}
		}
		return strList;
	}
	
	/**
	 * 
	 * 获取促销规则说明
	 *
	 * @param idList
	 * @param ruleList
	 * @return
	 */
	private List<String> getDrrRuleDesc(String idList, List<DrrRule> ruleList){
		List<String> strList = null;
		if(idList != null && "".equals(idList) == false && ruleList != null && ruleList.size() > 0){
			String[] ids = idList.split(",");
			strList = new ArrayList<String>();
			for(String id : ids){
				for(DrrRule r : ruleList){
					if(r.getDrrRuleId() == Integer.parseInt(id)){
						strList.add(r.getDescription());
						break;
					}
				}
			}
		}
		return strList;
	}
	
	/**
	 * 
	 * 获取增值服务的描述
	 *
	 * @param idList
	 * @param ruleList
	 * @return
	 */
	private List<String> getValueDesc(String idList, List<ValueAdd> ruleList){
		List<String> strList = null;
		if(idList != null && "".equals(idList) == false && ruleList != null && ruleList.size() > 0){
			String[] ids = idList.split(",");
			strList = new ArrayList<String>();
			for(String id : ids){
				for(ValueAdd r : ruleList){
					if(r.getValueAddId().equals(id)){
						strList.add(r.getDescription());
						break;
					}
				}
			}
		}
		return strList;
	}
	
	/**
	 * 
	 * 获取礼品的描述
	 *
	 * @param idList
	 * @param ruleList
	 * @return
	 */
	private List<String> getGiftDesc(String idList, List<Gift> ruleList){
		List<String> strList = null;
		if(idList != null && "".equals(idList) == false && ruleList != null && ruleList.size() > 0){
			String[] ids = idList.split(",");
			strList = new ArrayList<String>();
			for(String id : ids){
				for(Gift r : ruleList){
					if(r.getGiftId() == Integer.parseInt(id)){
						strList.add(r.getGiftContent());
						break;
					}
				}
			}
		}
		return strList;
	}
	
	/**
	 * 
	 * 获取特殊说明的描述
	 *
	 * @param idList
	 * @param ruleList
	 * @return
	 */
	private List<String> getAvailPolicyDesc(String idList, List<HAvailPolicy> ruleList){
		List<String> strList = null;
		if(idList != null && "".equals(idList) == false && ruleList != null && ruleList.size() > 0){
			String[] ids = idList.split(",");
			strList = new ArrayList<String>();
			for(String id : ids){
				for(HAvailPolicy r : ruleList){
					if(r.getId().equals(id)){
						strList.add(r.getAvailPolicyText());
						break;
					}
				}
			}
		}
		return strList;
	}
}
