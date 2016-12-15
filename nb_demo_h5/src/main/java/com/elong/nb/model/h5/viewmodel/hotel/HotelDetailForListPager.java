/**   
 * @(#)HotelDetailForListPagerResult.java	2016年11月22日	上午10:45:08	   
 *     
 * Copyrights (C) 2016艺龙旅行网保留所有权利
 */
package com.elong.nb.model.h5.viewmodel.hotel;

import java.math.BigDecimal;

/**
 * 酒店列表页的返回实体
 * 为了减少页面返回数据，只添加需要展示的页面属性；
 *
 * <p>
 * 修改历史:											<br>  
 * 修改日期    		修改人员   	版本	 		修改内容<br>  
 * -------------------------------------------------<br>  
 * 2016年11月22日 上午10:45:08   user     1.0    	初始化创建<br>
 * </p> 
 *
 * @author		user 
 * @version		1.0  
 * @since		JDK1.7
 */
public class HotelDetailForListPager {
	
	/**
	 * 酒店ID
	 */
	private String hotelId;
	
	/**
	 * 酒店展示名称
	 */
	private String hotelName;
	
	/**
	 * 酒店缩略图
	 */
	private String thumbnailUrl;
	
	/**
	 * 酒店最低价格
	 */
	private BigDecimal lowRate;
	
	/**
	 * 最低价格货币类型标示
	 */
	private String currencyFlag;
	
	/**
	 * 行政区域
	 */
	private String districtName;
	
	/**
	 * 商业街区
	 */
	private String businessZoneName;
	
	/**
	 * 评价数量
	 */
	private Integer reviewCount;

	/**   
	 * 得到hotelId的值   
	 *   
	 * @return hotelId的值
	 */
	public String getHotelId() {
		return hotelId;
	}

	/**
	 * 设置hotelId的值
	 *   
	 * @param hotelId 被设置的值
	 */
	public void setHotelId(String hotelId) {
		this.hotelId = hotelId;
	}

	/**   
	 * 得到hotelName的值   
	 *   
	 * @return hotelName的值
	 */
	public String getHotelName() {
		return hotelName;
	}

	/**
	 * 设置hotelName的值
	 *   
	 * @param hotelName 被设置的值
	 */
	public void setHotelName(String hotelName) {
		this.hotelName = hotelName;
	}

	/**   
	 * 得到thumbnailUrl的值   
	 *   
	 * @return thumbnailUrl的值
	 */
	public String getThumbnailUrl() {
		return thumbnailUrl;
	}

	/**
	 * 设置thumbnailUrl的值
	 *   
	 * @param thumbnailUrl 被设置的值
	 */
	public void setThumbnailUrl(String thumbnailUrl) {
		this.thumbnailUrl = thumbnailUrl;
	}

	/**   
	 * 得到lowRate的值   
	 *   
	 * @return lowRate的值
	 */
	public BigDecimal getLowRate() {
		return lowRate;
	}

	/**
	 * 设置lowRate的值
	 *   
	 * @param lowRate 被设置的值
	 */
	public void setLowRate(BigDecimal lowRate) {
		this.lowRate = lowRate;
	}

	/**   
	 * 得到currencyFlag的值   
	 *   
	 * @return currencyFlag的值
	 */
	public String getCurrencyFlag() {
		return currencyFlag;
	}

	/**
	 * 设置currencyFlag的值
	 *   
	 * @param currencyFlag 被设置的值
	 */
	public void setCurrencyFlag(String currencyFlag) {
		this.currencyFlag = currencyFlag;
	}

	/**   
	 * 得到districtName的值   
	 *   
	 * @return districtName的值
	 */
	public String getDistrictName() {
		return districtName;
	}

	/**
	 * 设置districtName的值
	 *   
	 * @param districtName 被设置的值
	 */
	public void setDistrictName(String districtName) {
		this.districtName = districtName;
	}

	/**   
	 * 得到businessZoneName的值   
	 *   
	 * @return businessZoneName的值
	 */
	public String getBusinessZoneName() {
		return businessZoneName;
	}

	/**
	 * 设置businessZoneName的值
	 *   
	 * @param businessZoneName 被设置的值
	 */
	public void setBusinessZoneName(String businessZoneName) {
		this.businessZoneName = businessZoneName;
	}

	/**   
	 * 得到reviewCount的值   
	 *   
	 * @return reviewCount的值
	 */
	public Integer getReviewCount() {
		return reviewCount;
	}

	/**
	 * 设置reviewCount的值
	 *   
	 * @param reviewCount 被设置的值
	 */
	public void setReviewCount(Integer reviewCount) {
		this.reviewCount = reviewCount;
	}
}
