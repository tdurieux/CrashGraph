package github.tdurieux.crashGraph.entities;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import github.tdurieux.graph.Graph;

/* The Bucket class only keeps track of the representing graph
 * and the Id of the reports inserted in the bucket. This should
 * be all the information it needs, if unexpectedly more 
 * information is needed it can ask to load the report again.
 * This saves up on memory in the context of BIG data. 
 */
public class Bucket extends Graph<Method> {
	Set<Integer> reportIds;

	public Bucket(Report initialReport) {
		this.graph = initialReport.getLastTrace().getEdges();
		this.reportIds = new HashSet<Integer>();
		this.reportIds.add(initialReport.getBugId());
	}

	public static Set<Bucket> createBuckets(String reportDir, double similarityTreshold) throws IOException {
		Set<Bucket> buckets = new HashSet<>();
		for (String reportName : Report.getReportNames(reportDir)) {
			Report report = Report.openReport(reportName);
			boolean reportBucketed = false;
			for (Bucket bucket : buckets) {
				if(bucket.fits(report, similarityTreshold)) {
					bucket.add(report);
					reportBucketed = true;
					break;
				}
			}
			if(!reportBucketed) {
				buckets.add(new Bucket(report));
			}
		}
		return buckets;
	}
	
	public Set<Integer> getReportIds() {
		return reportIds;
	}
	
	public boolean fits(Report report, double similarityTreshold) {
		return similarity(report.getLastTrace()) >= similarityTreshold;
	}
	
	public void add(Report report) {
		union(report.getLastTrace());
	}

}
