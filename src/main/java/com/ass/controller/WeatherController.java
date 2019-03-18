package com.ass.controller;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ass.model.WeatherData;
import com.ass.service.WeatherDataService;
import com.ass.util.PdfGenerator;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class WeatherController {
	
	@Autowired
	private WeatherDataService weatherDataService;
	
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ResponseEntity<String> test(){
		String apiKey = weatherDataService.getKey();
		return new ResponseEntity<String>(apiKey,HttpStatus.OK);
	}
	
	@RequestMapping(value="/forecast/", method = RequestMethod.POST)
	public ResponseEntity<String> saveForecastData(@RequestBody List<WeatherData> datas){
		String status = weatherDataService.saveForecastData(datas);
		if(status.equalsIgnoreCase("success"))
			return new ResponseEntity<String>("Data Saved.", HttpStatus.CREATED);
		else if(status.equalsIgnoreCase("please provide data"))
			return new ResponseEntity<String>(status, HttpStatus.BAD_REQUEST);
		else
			return new ResponseEntity<String>("Please try after some time.", HttpStatus.BAD_REQUEST);
	}
	
	@RequestMapping(value="/forecast/{cityname}", method = RequestMethod.GET)
	public ResponseEntity<List<WeatherData>> getWeather(@PathVariable("cityname") String cityname){
		List<WeatherData> list = weatherDataService.getWeather(cityname);
		return new ResponseEntity<List<WeatherData>>(list, HttpStatus.OK);
	}
	
	@GetMapping(value = "/forecast/pdf", produces = MediaType.APPLICATION_PDF_VALUE)
	public ResponseEntity<InputStreamResource> customersReport() throws IOException {
        List<WeatherData> datas = (List<WeatherData>) weatherDataService.findAll();
 
        ByteArrayInputStream bis = PdfGenerator.generatePdf(datas);
 
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "inline; filename=weatherdata.pdf");
 
        return ResponseEntity
                .ok()
                .headers(headers)
                .contentType(MediaType.APPLICATION_PDF)
                .body(new InputStreamResource(bis));
    }
	
	

}
