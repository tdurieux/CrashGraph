package github.tdurieux.crashGraph.entities;

import static org.junit.Assert.*;

import java.io.IOException;

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
}
