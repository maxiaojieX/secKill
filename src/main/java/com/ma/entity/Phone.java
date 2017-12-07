package com.ma.entity;

import org.joda.time.DateTime;

import java.math.BigDecimal;
import java.util.Date;

public class Phone {
    private Integer id;

    private String title;

    private String productName;

    private String productDetils;

    private String productImage;

    private BigDecimal price;

    private BigDecimal publicPrice;

    /**
     * other用来存库存
     */
    private String other;

    private Date starttime;

    private Date endtime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductDetils() {
        return productDetils;
    }

    public void setProductDetils(String productDetils) {
        this.productDetils = productDetils;
    }

    public String getProductImage() {
        return productImage;
    }

    public void setProductImage(String productImage) {
        this.productImage = productImage;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public BigDecimal getPublicPrice() {
        return publicPrice;
    }

    public void setPublicPrice(BigDecimal publicPrice) {
        this.publicPrice = publicPrice;
    }

    public String getOther() {
        return other;
    }

    public void setOther(String other) {
        this.other = other;
    }

    public Date getStarttime() {
        return starttime;
    }

    /**
     * 用于倒计时
     * @return
     */
    public Long getStartTime() {
        return getStarttime().getTime();
    }

    public void setStarttime(Date starttime) {
        this.starttime = starttime;
    }

    public Date getEndtime() {
        return endtime;
    }

    public void setEndtime(Date endtime) {
        this.endtime = endtime;
    }

    public boolean isStart() {
        DateTime dateTime = new DateTime(getStarttime().getTime());
        return dateTime.isBeforeNow();
    }

    public boolean isEnd() {
        DateTime dateTime = new DateTime(getEndtime().getTime());
        return dateTime.isBeforeNow();
    }

}