package github.tdurieux.crashGraph.entities;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Validator {
	private double precision;
	private double recall;
	private int numValidated;

	private List<matchReport> notedMismatches;

	public Validator() {
		precision = 0;
		recall = 0;
		numValidated = 0;

		notedMismatches = new ArrayList<matchReport>();
	}

	public void validate(Buckets allBuckets, Buckets actuallyMatched,
			Report report) {
		Buckets shouldBeMatched = findBuckets(report.getGroupId(),
				allBuckets);
		Buckets correctlyMatched = intersect(actuallyMatched,
				shouldBeMatched);

		double localPrecision;
		if (actuallyMatched.size() != 0) {
			localPrecision = ((double) correctlyMatched.size())
					/ actuallyMatched.size();
		} else {
			localPrecision = 1;
		}
		precision += localPrecision;

		double localRecall;
		if (shouldBeMatched.size() != 0) {
			localRecall = ((double) correctlyMatched.size())
					/ shouldBeMatched.size();
		} else {
			localRecall = 1;
		}
		recall += localRecall;

		numValidated++;

		if (localPrecision != 1.0 || localRecall != 1.0) {
			notedMismatches.add(new matchReport(report.getBugId(), shouldBeMatched,
					actuallyMatched, localPrecision, localRecall));
		}
	}

	private Buckets findBuckets(int groupId, Set<Bucket> allBuckets) {
		Buckets groupBuckets = new Buckets();
		for (Bucket bucket : allBuckets) {
			if (bucket.containsBug(groupId))
				groupBuckets.add(bucket);
		}
		return groupBuckets;
	}

	private Buckets intersect(Set<Bucket> set1, Set<Bucket> set2) {
		Buckets intersection = new Buckets();
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

	public List<matchReport> getAllMismatches() {
		return notedMismatches;
	}
}
