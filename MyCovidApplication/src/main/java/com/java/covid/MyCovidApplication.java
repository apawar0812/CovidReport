package com.java.covid;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import com.java.covid.dao.CovidReportsDao;

@SpringBootApplication

public class MyCovidApplication {
	
	@Autowired
	CovidReportsDao service;

	public static void main(String[] args) throws IOException, InterruptedException, ExecutionException {
		SpringApplication.run(MyCovidApplication.class, args);
		//ApplicationContext applicationContext = SpringApplication.run(MyCovidApplication.class, args);
		//CovidReportsDao service = applicationContext.getBean(CovidReportsDao.class);
		//service.laodData();
		
	}

}
