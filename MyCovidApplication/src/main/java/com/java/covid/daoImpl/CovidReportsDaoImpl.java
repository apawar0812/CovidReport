package com.java.covid.daoImpl;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

import org.apache.commons.csv.CSVFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.java.covid.dao.CovidReportRepository;
import com.java.covid.dao.CovidReportsDao;
import com.java.covid.data.CovidReport;
import com.java.covid.data.CovidReportDb;

@Service
public class CovidReportsDaoImpl implements CovidReportsDao {

	@Autowired
	CovidReportRepository repository;

	@Value("${csv_file_path}")
	private String csvFilePath;

	private String path = "C:\\Users\\VISHAL\\Documents\\Ankit_Pawar\\testCsv/";

	public void laodData() {
		System.out.println(csvFilePath);
		File[] fileArray = new File(csvFilePath).listFiles(obj -> obj.isFile() && obj.getName().endsWith(".csv"));
		List<CovidReportDb> tempList = null;
		try {
			Integer counter = 0;
			for (File file : fileArray) {
				FileReader fileReader = new FileReader(file);
				String fileName = file.getName().split(".csv")[0];
				tempList = CSVFormat.DEFAULT.withFirstRecordAsHeader().parse(fileReader).stream().map(row -> {
					CovidReportDb obj = new CovidReportDb(row);
					return obj;
				}).collect(Collectors.toList());
				repository.saveAll(tempList);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public List<CovidReport> getAllRecords(Map<String, String> filter) {
		System.out.println(csvFilePath);
		File[] fileArray = new File(csvFilePath).listFiles(obj -> obj.isFile() && obj.getName().endsWith(".csv"));
		List<CovidReport> tempList = null;
		List<CovidReport> list = new ArrayList<CovidReport>();
		List<CovidReport> resultTempList = new ArrayList<>();
		CovidReport aggreagateRecord = new CovidReport();
		try {
			Integer counter = 0;
			for (File file : fileArray) {
				FileReader fileReader = new FileReader(file);
				String fileName = file.getName().split(".csv")[0];
				// System.out.println("FileName : " + fileName);

				tempList = CSVFormat.DEFAULT.withFirstRecordAsHeader().parse(fileReader).stream().map(row -> {
					CovidReport obj = new CovidReport(row);
					return obj;
				}).filter(t1 -> {
					return filter.get("city") != null ? filter.get("city").equals(t1.getAdmin2()) : true;

				}).filter(t1 -> {
					return filter.get("state") != null ? filter.get("state").equals(t1.getProvinceSate()) : true;

				}).filter(t1 -> {
					return filter.get("country") != null ? filter.get("country").equals(t1.getCountryRegion()) : true;

				}).filter(t1 -> {
					if (filter.get("startDate") != null) {
						SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
						try {
							Date start = sdf.parse(filter.get("startDate"));
							Date previousDate = getDate(t1.getLastUpdated());
							return previousDate.after(start);
						} catch (ParseException e) {
							e.printStackTrace();
						}

					} else {
						return true;
					}
					return true;
				}).collect(Collectors.toList());
				counter = counter + tempList.size();
				resultTempList.addAll(tempList);
				aggreagateRecord = resultTempList.parallelStream().collect(CovidReport::new,
						CovidReport::aggregateCases, CovidReport::aggregateCases);
				// System.out.println("aggreagateRecord : " + aggreagateRecord);

			}
			System.out.println("Couter : " + counter);
			list.add(aggreagateRecord);
			return list;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	private Date getDate(Date date) {

		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.DAY_OF_YEAR, -1);
		Date previousDate = calendar.getTime();
		return previousDate;

	}

	public List<CovidReport> getAllRecords_1() throws IOException, InterruptedException, ExecutionException {
		File[] fileArray = new File(path).listFiles(obj -> obj.isFile() && obj.getName().endsWith(".csv"));
		Map<String, List<CovidReport>> map = new HashMap<String, List<CovidReport>>();
		Map<String, List<CovidReport>> map1 = new HashMap<String, List<CovidReport>>();
		Map<String, CovidReport> result = new HashMap<String, CovidReport>();
		List<CovidReport> tempList = null;
		try {
			for (File file : fileArray) {
				FileReader fileReader = new FileReader(file);
				String fileName = file.getName().split(".csv")[0];
				System.out.println(fileName);
				tempList = CSVFormat.DEFAULT.withFirstRecordAsHeader().parse(fileReader).stream().map(row -> {
					CovidReport obj = new CovidReport(row);
					return obj;
				}).collect(Collectors.toList());
				map.putAll(map1);
				map1 = tempList.parallelStream().collect(Collectors.toMap(
						t -> t.getFips() + ":" + t.getAdmin2() + ":" + t.getProvinceSate() + ":" + t.getCountryRegion(),
						q -> {
							String key = q.getFips() + ":" + q.getAdmin2() + ":" + q.getProvinceSate() + ":"
									+ q.getCountryRegion();
							List<CovidReport> o = new ArrayList<CovidReport>();
							if (map.containsKey(key)) {
								o = map.get(key);
							}
							o.add(q);
							return o;
						}, (a, b) -> a));

				result = map1.entrySet().parallelStream().collect(Collectors.toMap(t -> t.getKey(), v -> v.getValue()
						.parallelStream().collect(CovidReport::new, CovidReport::aggregate, CovidReport::aggregate)));
			}
			List<CovidReport> list = new ArrayList<CovidReport>(result.values());
			return list;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
