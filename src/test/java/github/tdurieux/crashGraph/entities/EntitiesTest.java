package github.tdurieux.crashGraph.entities;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import java.util.Set;

import org.junit.BeforeClass;
import org.junit.Test;

public class EntitiesTest {

	private static Report report1524;
	private static Report report1755;
	private static Report report1759;

	@BeforeClass
	public static void setUpClass() throws IOException {
		report1524 = Report.openReport("src/main/resource/reports/1524.json");
		report1755 = Report.openReport("src/main/resource/reports/1755.json");
		report1759 = Report.openReport("src/main/resource/reports/1759.json");
	}

	@Test
	public void creation_report_1524() {
		assertEquals(1524, report1524.getBugId());
		assertEquals(1002766487000l, report1524.getDate().getTime());
		assertEquals(1524, report1524.getGroupId());
		assertEquals(13, report1524.getLastTrace().numberOfEdges());
	}

	@Test
	public void comparing_reports_1755_and_1759() {
		assertEquals(
				0.55,
				report1755.getLastTrace().similarity(report1759.getLastTrace()),
				0.01);
	}

	@Test
	public void get_all_report_Files() {
		Set<File> reports = Report.getReportFiles("src/main/resource/reports/");
		assertEquals(20, reports.size());
	}

	@Test
	public void get_all_report_Names() {
		Set<String> reports = Report
				.getReportNames("src/main/resource/reports/");
		assertEquals(20, reports.size());
		assertTrue("The file 1524.json is not found in the report name list",
				reports.contains("src/main/resource/reports/1524.json"));
	}

	@Test
	public void creation_bucket_with_1524() {
		Bucket bucket1524 = new Bucket(report1524);
		assertEquals(1, bucket1524.getReportIds().size());
		assertTrue(
				"The bucket made from report 1524 does not contain the report id of 1524",
				bucket1524.getReportIds().contains(1524));
	}

	@Test
	public void fit_bucket_1524() {
		Bucket bucket1524 = new Bucket(report1524);
		double similarityTreshold = 0.5;
		assertTrue(
				"The report 1524 does not fit the bucket made from this same report"
						+ " with a threshold of " + similarityTreshold,
				bucket1524.fits(report1524, similarityTreshold));
		assertFalse("The report 1755 fits the bucket made from report 1524"
				+ " with a threshold of " + similarityTreshold,
				bucket1524.fits(report1755, similarityTreshold));
		assertFalse("The report 1759 fits the bucket made from report 1524"
				+ " with a threshold of " + similarityTreshold,
				bucket1524.fits(report1759, similarityTreshold));
	}

	@Test
	public void fit_bucket_1755() {
		Bucket bucket1755 = new Bucket(report1755);
		double similarityTreshold = 0.5;
		assertFalse("The report 1524 fits the bucket made from report 1755"
				+ " with a threshold of " + similarityTreshold,
				bucket1755.fits(report1524, similarityTreshold));
		assertTrue(
				"The report 1755 does not fit the bucket made from this same report"
						+ " with a threshold of " + similarityTreshold,
				bucket1755.fits(report1755, similarityTreshold));
		assertTrue(
				"The report 1759 does not fit the bucket made from report 1755"
						+ " with a threshold of " + similarityTreshold,
				bucket1755.fits(report1759, similarityTreshold));
	}
}
