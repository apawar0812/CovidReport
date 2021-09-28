package com.java.covid.dao;

import java.util.List;
import java.util.Map;

import com.java.covid.data.CovidReport;

public interface CovidReportsDao {

	List<CovidReport> getAllRecords(Map<String, String> filter) ;
	//void laodData() ;

}
