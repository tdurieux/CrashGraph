package github.tdurieux.crashGraph.entities;

import java.util.HashSet;
import java.util.Set;

public class Validator {
	private double precision;
	private double recall;
	private int numValidated;

	public Validator() {
		precision = 0;
		recall = 0;
		numValidated = 0;
	}

	public void validate(Set<Bucket> allBuckets, Set<Bucket> actuallyMatched,
			Report report) {
		Set<Bucket> shouldBeMatched = findBuckets(report.getGroupId(),
				allBuckets);
		Set<Bucket> correctlyMatched = intersect(actuallyMatched, shouldBeMatched);
		if (actuallyMatched.size() != 0) {
			precision += ((double)correctlyMatched.size()) / actuallyMatched.size();
		} else {
			precision += 1;
		}
		if (shouldBeMatched.size() != 0) {
			recall += ((double)correctlyMatched.size()) / shouldBeMatched.size();
		} else {
			recall += 1;
		}
		numValidated++;
	}

	private Set<Bucket> findBuckets(int groupId, Set<Bucket> allBuckets) {
		Set<Bucket> groupBuckets = new HashSet<Bucket>();
		for (Bucket bucket : allBuckets) {
			if (bucket.containsBug(groupId))
				groupBuckets.add(bucket);
		}
		return groupBuckets;
	}

	private Set<Bucket> intersect(Set<Bucket> set1, Set<Bucket> set2) {
		Set<Bucket> intersection = new HashSet<Bucket>();
		for (Bucket bucket : set1) {
			if (set2.contains(bucket)) {
				intersection.add(bucket);
			}
		}
		return intersection;
	}

	public double getMeanPrecision() {
		return precision / numValidated;
	}

	public double getMeanRecall() {
		return recall / numValidated;
	}
}
