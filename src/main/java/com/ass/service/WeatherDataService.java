package com.ass.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.ass.dao.WeatherDataRepository;
import com.ass.model.WeatherData;

@Service
public class WeatherDataService {

	@Value("${app.api.key}")
	private String apiKey;
	
	public String getKey() {
		return apiKey;
	}
	
	@Autowired
	private WeatherDataRepository dataRepository;
	
	public List<WeatherData> getWeather(String name){
		List<WeatherData> datas = new ArrayList<WeatherData>();
		String uri = "http://api.openweathermap.org/data/2.5/weather?q=CITYNAME&units=metric&APPID="+getKey();
		uri = uri.replace("CITYNAME", name);
		RestTemplate restTemplate = new RestTemplate();
		String result = restTemplate.getForObject(uri, String.class);
		System.out.println(result);
		JSONParser jsonParser = new JSONParser();
		try {
			JSONObject jsonObject = (JSONObject) jsonParser.parse(result);
			JSONArray weatherObj = (JSONArray) jsonObject.get("weather");
			JSONObject mainObj = (JSONObject) jsonObject.get("main");
			String weather = (String) ((JSONObject)weatherObj.get(0)).get("main");
			String cityName = (String) jsonObject.get("name");
			Long cityId = (Long) jsonObject.get("id");
			Date date = new Date((Long) jsonObject.get("dt")*1000);
			System.out.println("Date Object : " +date);
			Double temprature = Double.parseDouble(mainObj.get("temp").toString());
			Double tempMin = Double.parseDouble(mainObj.get("temp_min").toString());
			Double tempMax = Double.parseDouble(mainObj.get("temp_max").toString());
			Double humidity = Double.parseDouble(mainObj.get("humidity").toString());
			Double pressure	= Double.parseDouble(mainObj.get("pressure").toString());
			Double airSpeed = Double.parseDouble(((JSONObject)jsonObject.get("wind")).get("speed").toString());
			
			WeatherData data = new WeatherData();
			data.setDate(date);
			data.setAirspeed(airSpeed);
			data.setHumidity(humidity);
			data.setCityId(cityId);
			data.setCityName(cityName);
			data.setPressure(pressure);
			data.setTemprature(temprature);
			data.setMaxT(tempMax);
			data.setMinT(tempMin);
			data.setWeather(weather);
			
			datas.add(data);
			
		}catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return datas;
	}

	public String saveForecastData(List<WeatherData> datas) {
		String result = null;
		try {
			if(!datas.isEmpty()) {
				for(WeatherData data : datas) {
					dataRepository.save(data);
				}
				result = "success";
			}else {		
				result = "please provide data";
			}
		}catch (Exception e) {
			System.out.println(e.getMessage());
			result = "failure";
		}	
		return result;
	}

	public List<WeatherData> findAll() {
		return dataRepository.findAll();
	}
}
