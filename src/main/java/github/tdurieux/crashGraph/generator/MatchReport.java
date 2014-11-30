package github.tdurieux.crashGraph.generator;

import github.tdurieux.crashGraph.entities.Bucket;
import github.tdurieux.crashGraph.entities.Buckets;

public class MatchReport {
	private Integer bugId;
	private Buckets shouldBeMatched;
	private Buckets matched;
	private double localPrecision;
	private double localRecall;

	public MatchReport(Integer bugId, Buckets shouldBeMatched, Buckets matched,
			double localPrecision, double localRecall) {
		this.bugId = bugId;
		this.shouldBeMatched = shouldBeMatched;
		this.matched = matched;
		this.localPrecision = localPrecision;
		this.localRecall = localRecall;
	}

	public String toString() {
		String output = "Report " + bugId + " was matched to Buckets:";
		for (Bucket bucket : matched) {
			output += "\n\t- " + bucket.toString();
		}
		if(matched.isEmpty()) {
			output += "\n\tNo matched bucket ";
		}
		output += "\nWhile group Id sugested Buckets:";
		for (Bucket bucket : shouldBeMatched) {
			output += "\n\t- " + bucket.toString();
		}
		if(shouldBeMatched.isEmpty()) {
			output += "\n\tNo sugested bucket ";
		}
		output += "\nThe local precision was: " + localPrecision;
		output += "\nThe local recall was: " + localRecall;
		return output;
	}
}
