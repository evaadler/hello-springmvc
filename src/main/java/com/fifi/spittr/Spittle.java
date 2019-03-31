package com.fifi.spittr;

import java.util.Date;

/**
 * @author fifi
 */
public class Spittle {
    private final Long id;
    private final String message;

    /**
     * 时间戳
     */
    private final Date time;

    /**
     * 经度
     */
    private Double latitude;
    private Double longtitude;

    public Spittle(String message, Date time){
        this(message, time, null, null);
    }

    public Spittle(String message, Date time, Double latitude, Double longtitude){
        this.id = null;
        this.message = message;
        this.time = time;
        this.latitude = latitude;
        this.longtitude = longtitude;
    }

    public Long getId() {
        return id;
    }

    public String getMessage() {
        return message;
    }

    public Date getTime() {
        return time;
    }

    public Double getLatitude() {
        return latitude;
    }

    public Double getLongtitude() {
        return longtitude;
    }

    @Override
    public boolean equals(Object that) {
        return EqualsBuilder.reflectionEquals(this, that, "id", "time");
    }

    @Override
    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this,"id", "time");
    }
}
