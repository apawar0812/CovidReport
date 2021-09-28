package com.java.covid.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.java.covid.data.CovidReportDb;

@Repository
public interface CovidReportRepository extends JpaRepository<CovidReportDb, Integer> {

}
