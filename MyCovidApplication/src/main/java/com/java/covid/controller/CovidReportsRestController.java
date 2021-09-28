package com.java.covid.controller;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import javax.websocket.server.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.java.covid.dao.CovidReportsDao;
import com.java.covid.data.CovidReport;
import com.java.covid.service.CovidReportsService;

@RestController
@RequestMapping("/covid")
public class CovidReportsRestController {

	@Autowired
	CovidReportsDao dao;

	@Autowired
	CovidReportsService service;

	@GetMapping("/getallrecords")
	@Cacheable(value = "getAllRecord")
	public List<CovidReport> getAllActiveCases(@PathParam(value = "city") String city,
			@PathParam(value = "state") String state, @PathParam(value = "country") String country,
			@PathParam(value = "startDate") String startDate)
			throws IOException, InterruptedException, ExecutionException {
		Map<String, String> filter = new HashMap<String, String>();
		if (city != null) {
			filter.put("city", city);
		}
		if (state != null) {
			filter.put("state", state);
		}
		if (country != null) {
			filter.put("country", country);
		}
		if (startDate != null) {
			filter.put("startDate", startDate);
		}

		return dao.getAllRecords(filter);

	}

}
