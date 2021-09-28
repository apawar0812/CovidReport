package com.java.covid.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.stereotype.Service;

import com.java.covid.dao.CovidReportsDao;
import com.java.covid.data.CovidReport;

@Service
@EnableCaching
public class CovidReportsServiceImpl implements CovidReportsService {

	@Autowired
	CovidReportsDao dao;

	public List<CovidReport> getAllRecords(Map<String, String> filter) {
		return dao.getAllRecords(filter);

	}

}
