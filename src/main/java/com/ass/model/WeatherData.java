package com.ass.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@Entity
@JsonInclude(Include.NON_NULL)
public class WeatherData {
	
	@Id
	@Column
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column
	private String cityName;
	
	@Column
	private Long cityId;
	
	@Column
	private Date date;
	
	@Column
	private String weather;
	
	@Column
	private Double temprature;
	
	@Column
	private Double minT;
	
	@Column
	private Double maxT;
	
	@Column
	private Double pressure;
	
	@Column
	private Double airspeed;
	
	@Column
	private Double humidity;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getWeather() {
		return weather;
	}

	public void setWeather(String weather) {
		this.weather = weather;
	}

	public Double getTemprature() {
		return temprature;
	}

	public void setTemprature(Double temprature) {
		this.temprature = temprature;
	}

	public Double getMinT() {
		return minT;
	}

	public void setMinT(Double minT) {
		this.minT = minT;
	}

	public Double getMaxT() {
		return maxT;
	}

	public void setMaxT(Double maxT) {
		this.maxT = maxT;
	}

	public Double getPressure() {
		return pressure;
	}

	public void setPressure(Double pressure) {
		this.pressure = pressure;
	}

	public Double getAirspeed() {
		return airspeed;
	}

	public void setAirspeed(Double airspeed) {
		this.airspeed = airspeed;
	}

	public Double getHumidity() {
		return humidity;
	}

	public void setHumidity(Double humidity) {
		this.humidity = humidity;
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public Long getCityId() {
		return cityId;
	}

	public void setCityId(Long cityId) {
		this.cityId = cityId;
	}
	
}
