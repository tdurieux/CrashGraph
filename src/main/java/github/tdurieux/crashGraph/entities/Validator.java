package github.tdurieux.crashGraph.entities;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Validator {
	private int numFalsePositives;
	private Map<Bucket, List<Report>> falsePositives = new HashMap<Bucket, List<Report>>();
	private int numFalseNegatives;
	private Map<Bucket, List<Report>> falseNegatives = new HashMap<Bucket, List<Report>>();

	public void validate(boolean fits, Bucket bucket, Report report) {
		if (fits != bucket.containsBug(report.getGroupId())) {
			if (fits) {
				increaseNumFalsePositives();
				if (!falsePositives.containsKey(bucket)) {
					falsePositives.put(bucket, new ArrayList<Report>());
				}
				falsePositives.get(bucket).add(report);
			} else {
				increaseNumFalseNegatives();
				if (!falseNegatives.containsKey(bucket)) {
					falseNegatives.put(bucket, new ArrayList<Report>());
				}
				falseNegatives.get(bucket).add(report);
			}
		}
	}

	public int getNumFalsePositives() {
		return numFalsePositives;
	}

	public int getNumFalseNegatives() {
		return numFalseNegatives;
	}

	public Map<Bucket, List<Report>> getFalseNegatives() {
		return falseNegatives;
	}

	public Map<Bucket, List<Report>> getFalsePositives() {
		return falsePositives;
	}

	public void increaseNumFalsePositives() {
		numFalsePositives++;
	}

	public void increaseNumFalseNegatives() {
		numFalseNegatives++;
	}
}
