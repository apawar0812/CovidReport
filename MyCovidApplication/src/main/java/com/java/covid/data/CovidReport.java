package com.java.covid.data;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

import org.apache.commons.csv.CSVRecord;

public class CovidReport {
	private Integer fips;
	private String admin2;
	private String provinceSate;
	private String countryRegion;
	private Date lastUpdated;
	private String lat;
	private String longitude;
	private Integer confirmed;
	private Integer deaths;
	private Integer recovered;
	private Integer active;

	public CovidReport(CSVRecord record) {
		try {
			this.fips = isEmptyField(record.get("FIPS")) ? Integer.parseInt(record.get("FIPS")) : null;
			this.admin2 = record.get("Admin2");
			this.provinceSate = record.get("Province_State");
			this.countryRegion = record.get("Country_Region");
			try {
				this.lastUpdated = new SimpleDateFormat("yyyy-mm-dd").parse(record.get("Last_Update"));
			} catch (ParseException e) {
			}
			this.lat = record.get("Lat");
			this.longitude = record.get("Long_");
			this.confirmed = isEmptyField(record.get("Confirmed")) ? Integer.parseInt(record.get("Confirmed")) : 0;
			this.deaths = isEmptyField(record.get("Deaths")) ? Integer.parseInt(record.get("Deaths")) : 0;
			this.recovered = isEmptyField(record.get("Recovered")) ? Integer.parseInt(record.get("Recovered")) : 0;
			this.active = isEmptyField(record.get("Active")) ? Integer.parseInt(record.get("Active")) : 0;
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public CovidReport() {
		this.confirmed = 0;
		this.deaths = 0;
		this.recovered = 0;
		this.active = 0;
	}
	
	public void aggregateCases(CovidReport covidReport) {
		this.confirmed += covidReport.getConfirmed() != null ? covidReport.getConfirmed() : 0;
		this.deaths += covidReport.getDeaths() != null ? covidReport.getDeaths() : 0;
		this.recovered += covidReport.getRecovered() != null ? covidReport.getRecovered() : 0;
		this.active += covidReport.getActive() != null ? covidReport.getActive() : 0;
	}
	

	public void aggregate(CovidReport covidReport) {
		this.fips = this.fips != null ? this.fips : covidReport.getFips();
		this.admin2 = this.admin2 != null ? this.admin2 : covidReport.getAdmin2();
		this.provinceSate = this.provinceSate != null ? this.provinceSate : covidReport.getProvinceSate();
		this.countryRegion = this.countryRegion != null ? this.countryRegion : covidReport.getCountryRegion();
		this.lastUpdated = this.lastUpdated != null ? this.lastUpdated : covidReport.getLastUpdated();
		this.lat = this.lat != null ? this.lat : covidReport.getLat();
		this.longitude = this.longitude != null ? this.longitude : covidReport.getLongitude();
		this.confirmed += covidReport.getConfirmed() != null ? covidReport.getConfirmed() : 0;
		this.deaths += covidReport.getDeaths() != null ? covidReport.getDeaths() : 0;
		this.recovered += covidReport.getRecovered() != null ? covidReport.getRecovered() : 0;
		this.active += covidReport.getActive() != null ? covidReport.getActive() : 0;
	}

	private boolean isEmptyField(String field) {
		return field != null && field != "";
	}

	public String getProvinceSate() {
		return provinceSate;
	}

	public void setProvinceSate(String provinceSate) {
		this.provinceSate = provinceSate;
	}

	public String getCountryRegion() {
		return countryRegion;
	}

	public void setCountryRegion(String countryRegion) {
		this.countryRegion = countryRegion;
	}

	public Date getLastUpdated() {
		return lastUpdated;
	}

	public void setLastUpdated(Date lastUpdated) {
		this.lastUpdated = lastUpdated;
	}

	public String getLat() {
		return lat;
	}

	public void setLat(String lat) {
		this.lat = lat;
	}

	public String getLongitude() {
		return longitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	public Integer getConfirmed() {
		return confirmed;
	}

	public void setConfirmed(Integer confirmed) {
		this.confirmed = confirmed;
	}

	public Integer getDeaths() {
		return deaths;
	}

	public void setDeaths(Integer deaths) {
		this.deaths = deaths;
	}

	public Integer getRecovered() {
		return recovered;
	}

	public void setRecovered(Integer recovered) {
		this.recovered = recovered;
	}

	public Integer getActive() {
		return active;
	}

	public void setActive(Integer active) {
		this.active = active;
	}

	@Override
	public String toString() {
		return "CovidReport [fips=" + fips + ", admin2=" + admin2 + ", provinceSate=" + provinceSate
				+ ", countryRegion=" + countryRegion + ", lastUpdated=" + lastUpdated + ", lat=" + lat + ", longitude="
				+ longitude + ", confirmed=" + confirmed + ", deaths=" + deaths + ", recovered=" + recovered
				+ ", active=" + active + "]";
	}

	public Integer getFips() {
		return fips;
	}

	public void setFips(Integer fips) {
		this.fips = fips;
	}

	public String getAdmin2() {
		return admin2;
	}

	public void setAdmin2(String admin2) {
		this.admin2 = admin2;
	}

	@Override
	public int hashCode() {
		return Objects.hash(active, admin2, confirmed, countryRegion, deaths, fips, lastUpdated, lat, longitude,
				provinceSate, recovered);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CovidReport other = (CovidReport) obj;
		return Objects.equals(active, other.active) && Objects.equals(admin2, other.admin2)
				&& Objects.equals(confirmed, other.confirmed) && Objects.equals(countryRegion, other.countryRegion)
				&& Objects.equals(deaths, other.deaths) && Objects.equals(fips, other.fips)
				&& Objects.equals(lastUpdated, other.lastUpdated) && Objects.equals(lat, other.lat)
				&& Objects.equals(longitude, other.longitude) && Objects.equals(provinceSate, other.provinceSate)
				&& Objects.equals(recovered, other.recovered);
	}

}
