package github.tdurieux.crashGraph.entities;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import java.util.Set;

import org.junit.Test;

public class EntitiesTest {

	@Test
	public void creation_report_1524() throws IOException {
		Report report1524 = Report.openReport("src/main/resource/reports/1524.json");
		assertEquals(1524,report1524.getBugId());
		assertEquals(1002766487000l, report1524.getDate().getTime());
		assertEquals(1524, report1524.getGroupId());
		assertEquals(13, report1524.getLastTrace().numberOfEdges());
	}
	
	@Test
	public void comparing_reports_1755_and_1759() throws IOException {
		Report report1755 = Report.openReport("src/main/resource/reports/1755.json");
		Report report1759 = Report.openReport("src/main/resource/reports/1759.json");
		assertEquals(0.55, report1755.getLastTrace().similarity(report1759.getLastTrace()),0.01);
	}
	
	@Test
	public void get_all_report_Files() {
		Set<File> reports = Report.getReportFiles("src/main/resource/reports/");
		assertEquals(20, reports.size());
	}
	
	@Test
	public void get_all_report_Names() {
		Set<String> reports = Report.getReportNames("src/main/resource/reports/");
		assertEquals(20, reports.size());
		System.out.println(reports.toString());
		assertTrue(reports.contains("src/main/resource/reports/1524.json"));
	}
	
	@Test
	public void creation_bucket_with_1524() throws IOException {
		Report report1524 = Report.openReport("src/main/resource/reports/1524.json");
		Bucket bucket1524 = new Bucket(report1524);
		assertEquals(1, bucket1524.getReportIds().size());
		assertTrue(bucket1524.getReportIds().contains(1524));
	}
}
