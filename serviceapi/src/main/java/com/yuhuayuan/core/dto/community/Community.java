package com.yuhuayuan.core.dto.community;

import java.util.Date;

public class Community {
    private Long id;

    private Long propertyId;

    private Long provinceId;

    private Long cityId;

    private Long districtId;

    private String name;

    private String address;

    private String lon;

    private String lat;

    private Long operator;

    private String phone;

    private String state;

    private Date createTime;

    private Integer ringId;

    private Integer cooperationType;

    private String hwId;

    private Integer intelligenceDoorFactory;

    private String maintenancePhone;

    private String maintenanceTime;

    private Long extrinsicCommunityId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getPropertyId() {
        return propertyId;
    }

    public void setPropertyId(Long propertyId) {
        this.propertyId = propertyId;
    }

    public Long getProvinceId() {
        return provinceId;
    }

    public void setProvinceId(Long provinceId) {
        this.provinceId = provinceId;
    }

    public Long getCityId() {
        return cityId;
    }

    public void setCityId(Long cityId) {
        this.cityId = cityId;
    }

    public Long getDistrictId() {
        return districtId;
    }

    public void setDistrictId(Long districtId) {
        this.districtId = districtId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address == null ? null : address.trim();
    }

    public String getLon() {
        return lon;
    }

    public void setLon(String lon) {
        this.lon = lon == null ? null : lon.trim();
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat == null ? null : lat.trim();
    }

    public Long getOperator() {
        return operator;
    }

    public void setOperator(Long operator) {
        this.operator = operator;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone == null ? null : phone.trim();
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state == null ? null : state.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getRingId() {
        return ringId;
    }

    public void setRingId(Integer ringId) {
        this.ringId = ringId;
    }

    public Integer getCooperationType() {
        return cooperationType;
    }

    public void setCooperationType(Integer cooperationType) {
        this.cooperationType = cooperationType;
    }

    public String getHwId() {
        return hwId;
    }

    public void setHwId(String hwId) {
        this.hwId = hwId == null ? null : hwId.trim();
    }

    public Integer getIntelligenceDoorFactory() {
        return intelligenceDoorFactory;
    }

    public void setIntelligenceDoorFactory(Integer intelligenceDoorFactory) {
        this.intelligenceDoorFactory = intelligenceDoorFactory;
    }

    public String getMaintenancePhone() {
        return maintenancePhone;
    }

    public void setMaintenancePhone(String maintenancePhone) {
        this.maintenancePhone = maintenancePhone == null ? null : maintenancePhone.trim();
    }

    public String getMaintenanceTime() {
        return maintenanceTime;
    }

    public void setMaintenanceTime(String maintenanceTime) {
        this.maintenanceTime = maintenanceTime == null ? null : maintenanceTime.trim();
    }

    public Long getExtrinsicCommunityId() {
        return extrinsicCommunityId;
    }

    public void setExtrinsicCommunityId(Long extrinsicCommunityId) {
        this.extrinsicCommunityId = extrinsicCommunityId;
    }
}