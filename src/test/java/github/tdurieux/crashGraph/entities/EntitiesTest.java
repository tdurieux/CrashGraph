package github.tdurieux.crashGraph.entities;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import github.tdurieux.crashGraph.classifier.Classifier;
import github.tdurieux.crashGraph.classifier.GraphViewClassifier;
import github.tdurieux.crashGraph.parser.BucketsParser;

import java.io.File;
import java.io.IOException;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

public class EntitiesTest {

	private Report report1524;
	private Report report1755;
	private Report report1759;

	@Before
	public void setUpClass() throws IOException {
		report1524 = Report.openReport("src/main/resource/reports/1524.json");
		report1755 = Report.openReport("src/main/resource/reports/1755.json");
		report1759 = Report.openReport("src/main/resource/reports/1759.json");
	}

	@Test
	public void creation_report_1524() {
		assertEquals(1524, report1524.getBugId());
		assertEquals(1002766487000l, report1524.getDate().getTime());
		assertEquals(1524, report1524.getGroupId());
		assertEquals(14, report1524.getLastTrace().getLastCausedBy().numberOfEdges());
	}

	@Test
	public void comparing_reports_1755_and_1759() {
		assertEquals(
				0.56,
				report1755.getLastTrace().getLastCausedBy().similarity(report1759.getLastTrace().getLastCausedBy()),
				0.01);
	}

	@Test
	public void get_all_report_Files() {
		Set<File> reports = Report.getReportFiles("src/main/resource/reports/");
		assertEquals(22, reports.size());
	}

	@Test
	public void get_all_report_Names() {
		Set<String> reports = Report
				.getReportNames("src/main/resource/reports/");
		assertEquals(22, reports.size());
		assertTrue("The file 1524.json is not found in the report name list",
				reports.contains("src/main/resource/reports/1524.json"));
	}

	@Test
	public void creation_bucket_with_1524() {
		Bucket bucket1524 = new Bucket(report1524);
		assertEquals(1, bucket1524.getReportIds().size());
		assertTrue("Bucket 1524 does not contain group id of report 1524",
				bucket1524.containsBug(report1524.getGroupId()));
		assertTrue(
				"The bucket made from report 1524 does not contain the report id of 1524",
				bucket1524.getReportIds().contains(1524));
	}

	@Test
	public void fit_bucket_1524() {
		Bucket bucket1524 = new Bucket(report1524);
		double similarityTreshold = 0.9;
		Classifier classifier = new GraphViewClassifier(similarityTreshold);
		assertTrue(
				"The report 1524 does not fit the bucket made from this same report"
						+ " with a threshold of " + similarityTreshold,
				classifier.isSameBucket(bucket1524, report1524.getLastTrace().getLastCausedBy()));
		assertFalse("The report 1755 fits the bucket made from report 1524"
				+ " with a threshold of " + similarityTreshold,
				classifier.isSameBucket(bucket1524, report1755.getLastTrace().getLastCausedBy()));
		assertFalse("The report 1759 fits the bucket made from report 1524"
				+ " with a threshold of " + similarityTreshold,
				classifier.isSameBucket(bucket1524, report1759.getLastTrace().getLastCausedBy()));
	}

	@Test
	public void fit_bucket_1755() {
		Bucket bucket1755 = new Bucket(report1755);
		double similarityTreshold = 0.55;
		Classifier classifier = new GraphViewClassifier(similarityTreshold);
		assertFalse("The report 1524 fits the bucket made from report 1755"
				+ " with a threshold of " + similarityTreshold,
				classifier.isSameBucket(bucket1755, report1524.getLastTrace().getLastCausedBy()));
		assertTrue(
				"The report 1755 does not fit the bucket made from this same report"
						+ " with a threshold of " + similarityTreshold,
				classifier.isSameBucket(bucket1755, report1755.getLastTrace().getLastCausedBy()));
		assertTrue(
				"The report 1759 does not fit the bucket made from report 1755"
						+ " with a threshold of " + similarityTreshold,
				classifier.isSameBucket(bucket1755, report1759.getLastTrace().getLastCausedBy()));
	}

	@Test
	public void add_1759_to_bucket_1755() {
		Bucket bucket1755 = new Bucket(report1755);
		bucket1755.add(report1759);
		double similarityTreshold = 1;
		Classifier classifier = new GraphViewClassifier(similarityTreshold);
		assertFalse(
				"The report 1524 fits the bucket made from report 1755 and 1759"
						+ " with a threshold of " + similarityTreshold,
				classifier.isSameBucket(bucket1755, report1524.getLastTrace().getLastCausedBy()));
		assertTrue(
				"The report 1755 does not fit the bucket made from report 1755 and 1759"
						+ " with a threshold of " + similarityTreshold,
				classifier.isSameBucket(bucket1755, report1755.getLastTrace().getLastCausedBy()));
		assertTrue(
				"The report 1759 does not fit the bucket made from report 1755 and 1759"
						+ " with a threshold of " + similarityTreshold,
				classifier.isSameBucket(bucket1755, report1759.getLastTrace().getLastCausedBy()));
	}

	@Test
	public void count_bugs_in_bucket() {
		Bucket bucket1755 = new Bucket(report1755);
		bucket1755.add(report1524);
		bucket1755.add(report1759);
		assertEquals(2, bucket1755.numBugs());
	}

	@Test
	public void printingBucket() {
		Bucket bucket = new Bucket(report1524);
		bucket.add(report1755);
		bucket.add(report1759);
		assertEquals("1524 1755 1759 ", bucket.toString());
	}

	@Test
	public void bucketing() throws IOException {
		double similarityTreshold = 0.5;
		Classifier classifier = new GraphViewClassifier(similarityTreshold);
		Validator valReport = new Validator();
		Buckets buckets = BucketsParser.parse("src/main/resource/reports/",
				classifier, valReport);
		assertEquals(11, buckets.size());
	}
}
