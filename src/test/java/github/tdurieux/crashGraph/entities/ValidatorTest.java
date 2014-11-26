package github.tdurieux.crashGraph.entities;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

public class ValidatorTest {
	Report reportGroup1;
	Report reportGroup2;
	Report reportGroup3;
	
	Bucket bucketGroup1;
	Bucket bucketGroup2;
	Bucket bucketGroup12;
	
	Set<Bucket> allBuckets;
	
	Validator validator;
	
	@Before
	public void setUpClass() throws IOException {
		bucketGroup1 = dummyBucket(1);
		bucketGroup2 = dummyBucket(2);
		bucketGroup12 = dummyBucket(1);
		bucketGroup12.add(dummyReport(2));
		
		allBuckets = new HashSet<Bucket>();
		allBuckets.add(bucketGroup1);
		allBuckets.add(bucketGroup2);
		allBuckets.add(bucketGroup12);
		
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
		Set<Bucket> potentialBuckets = new HashSet<Bucket>();
		potentialBuckets.add(bucketGroup1);
		potentialBuckets.add(bucketGroup12);
		validator.validate(allBuckets, potentialBuckets, reportGroup1);
		assertEquals(1.0,validator.getMeanPrecision(),0.0001);
		assertEquals(1.0,validator.getMeanRecall(),0.0001);
	}
	
	@Test
	public void validatePartialCorrectMatch() {
		Set<Bucket> potentialBuckets = new HashSet<Bucket>();
		potentialBuckets.add(bucketGroup1);
		validator.validate(allBuckets, potentialBuckets, reportGroup1);
		assertEquals(1.0,validator.getMeanPrecision(),0.0001);
		assertEquals(0.5,validator.getMeanRecall(),0.0001);
	}
	
	@Test
	public void validateEmptyCorrectMatch() {
		Set<Bucket> potentialBuckets = new HashSet<Bucket>();
		validator.validate(allBuckets, potentialBuckets, reportGroup3);
		assertEquals(1.0,validator.getMeanPrecision(),0.0001);
		assertEquals(1.0,validator.getMeanRecall(),0.0001);
	}
	
	@Test
	public void validateFullUncorrectMatch() {
		Set<Bucket> potentialBuckets = new HashSet<Bucket>();
		potentialBuckets.add(bucketGroup1);
		validator.validate(allBuckets, potentialBuckets, reportGroup2);
		assertEquals(0.0,validator.getMeanPrecision(),0.0001);
		assertEquals(0.0,validator.getMeanRecall(),0.0001);
	}
	
	@Test
	public void validatePartialUncorrectMatch() {
		Set<Bucket> potentialBuckets = new HashSet<Bucket>();
		potentialBuckets.add(bucketGroup1);
		potentialBuckets.add(bucketGroup2);
		validator.validate(allBuckets, potentialBuckets, reportGroup2);
		assertEquals(0.5,validator.getMeanPrecision(),0.0001);
		assertEquals(0.5,validator.getMeanRecall(),0.0001);
	}
	
	@Test
	public void validateEmptyUncorrectMatch() {
		Set<Bucket> potentialBuckets = new HashSet<Bucket>();
		validator.validate(allBuckets, potentialBuckets, reportGroup2);
		assertEquals(1.0,validator.getMeanPrecision(),0.0001);
		assertEquals(0.0,validator.getMeanRecall(),0.0001);
	}
	
	@Test
	public void validateEmptyEmptyMatch() {
		Set<Bucket> emptyBuckets = new HashSet<Bucket>();
		validator.validate(emptyBuckets, emptyBuckets, reportGroup1);
		assertEquals(1.0,validator.getMeanPrecision(),0.0001);
		assertEquals(1.0,validator.getMeanRecall(),0.0001);
	}
}
