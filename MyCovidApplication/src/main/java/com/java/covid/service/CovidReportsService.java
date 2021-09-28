package com.java.covid.service;

import java.util.List;
import java.util.Map;

import com.java.covid.data.CovidReport;

public interface CovidReportsService {
	
	public List<CovidReport> getAllRecords(Map<String, String> filter);



}
