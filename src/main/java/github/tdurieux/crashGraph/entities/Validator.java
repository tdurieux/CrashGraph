package github.tdurieux.crashGraph.entities;

public class Validator {
	private int numFalsePositives;
	private int numFalseNegatives;
	
	public void validate(boolean fits, Bucket bucket, Report report) {
		if(fits != bucket.containsBug(report.getGroupId())) {
			if(fits)
				increaseNumFalsePositives();
			else
				increaseNumFalseNegatives();
		}
	}
	
	public int getNumFalsePositives() {
		return numFalsePositives;
	}
	public int getNumFalseNegatives() {
		return numFalseNegatives;
	}
	
	public void increaseNumFalsePositives() {
		numFalsePositives++;
	}
	public void increaseNumFalseNegatives() {
		numFalseNegatives++;
	}
}
