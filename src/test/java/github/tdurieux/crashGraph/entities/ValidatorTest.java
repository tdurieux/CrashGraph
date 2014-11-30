package github.tdurieux.crashGraph.entities;

import static org.junit.Assert.assertEquals;
import github.tdurieux.crashGraph.validator.Validator;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

public class ValidatorTest {
	Report reportGroup1;
	Report reportGroup2;
	Report reportGroup3;
	
	Bucket bucketGroup1;
	Bucket bucketGroup2;
	Bucket bucketGroup12;
	
	Buckets allBuckets;
	Buckets potentialBuckets;
	
	Validator validator;
	
	@Before
	public void setUpClass() throws IOException {
		bucketGroup1 = dummyBucket(1);
		bucketGroup2 = dummyBucket(2);
		bucketGroup12 = dummyBucket(1);
		bucketGroup12.add(dummyReport(2));
		
		allBuckets = new Buckets();
		allBuckets.add(bucketGroup1);
		allBuckets.add(bucketGroup2);
		allBuckets.add(bucketGroup12);
		
		potentialBuckets = new Buckets();
		
		reportGroup1 = dummyReport(1);
		reportGroup2 = dummyReport(2);
		reportGroup3 = dummyReport(3);
		
		validator = new Validator();
	}
	
	public Bucket dummyBucket(int groupId) {
		Report report = dummyReport(groupId);
		return new Bucket(report);
	}
	
	public Report dummyReport(int groupId) {
		Trace dummyTrace = new Trace();
		List<Trace> dummyTraceList = new ArrayList<Trace>();
		dummyTraceList.add(dummyTrace);
		Report report = new Report();
		report.setTraces(dummyTraceList);
		report.setGroupId(groupId);
		return report;
	}
	
	@Test
	public void validateFullCorrectMatch() {
		potentialBuckets.add(bucketGroup1);
		potentialBuckets.add(bucketGroup12);
		validator.validate(allBuckets, potentialBuckets, reportGroup1);
		assertEquals(1.0,validator.getMeanPrecision(),0.0001);
		assertEquals(1.0,validator.getMeanRecall(),0.0001);
	}
	
	@Test
	public void validatePartialCorrectMatch() {
		potentialBuckets.add(bucketGroup1);
		validator.validate(allBuckets, potentialBuckets, reportGroup1);
		assertEquals(1.0,validator.getMeanPrecision(),0.0001);
		assertEquals(0.5,validator.getMeanRecall(),0.0001);
	}
	
	@Test
	public void validateEmptyCorrectMatch() {
		validator.validate(allBuckets, potentialBuckets, reportGroup3);
		assertEquals(1.0,validator.getMeanPrecision(),0.0001);
		assertEquals(1.0,validator.getMeanRecall(),0.0001);
	}
	
	@Test
	public void validateFullUncorrectMatch() {
		potentialBuckets.add(bucketGroup1);
		validator.validate(allBuckets, potentialBuckets, reportGroup2);
		assertEquals(0.0,validator.getMeanPrecision(),0.0001);
		assertEquals(0.0,validator.getMeanRecall(),0.0001);
	}
	
	@Test
	public void validatePartialUncorrectMatch() {
		potentialBuckets.add(bucketGroup1);
		potentialBuckets.add(bucketGroup2);
		validator.validate(allBuckets, potentialBuckets, reportGroup2);
		assertEquals(0.5,validator.getMeanPrecision(),0.0001);
		assertEquals(0.5,validator.getMeanRecall(),0.0001);
	}
	
	@Test
	public void validateEmptyUncorrectMatch() {
		validator.validate(allBuckets, potentialBuckets, reportGroup2);
		assertEquals(1.0,validator.getMeanPrecision(),0.0001);
		assertEquals(0.0,validator.getMeanRecall(),0.0001);
	}
	
	@Test
	public void validateEmptyEmptyMatch() {
		Buckets emptyBuckets = new Buckets();
		validator.validate(emptyBuckets, emptyBuckets, reportGroup1);
		assertEquals(1.0,validator.getMeanPrecision(),0.0001);
		assertEquals(1.0,validator.getMeanRecall(),0.0001);
	}
}
