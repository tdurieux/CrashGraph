package github.tdurieux.crashGraph.entities;

public class matchReport {
	private Integer bugId;
	private Buckets shouldBeMatched;
	private Buckets matched;
	private double localPrecision;
	private double localRecall;

	public matchReport(Integer bugId, Buckets shouldBeMatched, Buckets matched,
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
			output += "\nMatch: " + bucket.toString();
		}
		output += "\nWhile group Id sugested Buckets:";
		for (Bucket bucket : shouldBeMatched) {
			output += "\nSuggested: " + bucket.toString();
		}
		output += "\nThe local precision was: " + localPrecision;
		output += "\nThe local recall was: " + localRecall;
		return output;
	}
}
