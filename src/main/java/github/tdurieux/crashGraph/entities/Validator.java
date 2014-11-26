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

	public void validate(Set<Bucket> allBuckets, Set<Bucket> potentialBuckets,
			Report report) {
		Set<Bucket> shouldBeMatched = new HashSet<Bucket>();
		for (Bucket bucket : allBuckets) {
			if (bucket.getGroupIds().contains(report.getGroupId())) {
				shouldBeMatched.add(bucket);
			}
		}
		int numShouldBeMatched = shouldBeMatched.size();
		shouldBeMatched.retainAll(potentialBuckets);
		double correctMatching = shouldBeMatched.size();
		if (potentialBuckets.size() != 0) {
			precision += correctMatching / potentialBuckets.size();
		} else {
			precision += 1;
		}
		if (numShouldBeMatched != 0) {
			recall += correctMatching / numShouldBeMatched;
		} else {
			recall += 1;
		}
		numValidated++;
	}

	public double getMeanPrecision() {
		return precision / numValidated;
	}

	public double getMeanRecall() {
		return recall / numValidated;
	}
}
