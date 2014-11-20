package github.tdurieux.crashGraph.entities;

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

	public Set<Integer> getReportIds() {
		return reportIds;
	}
	
	public boolean fits(Report report, double similarityTreshold) {
		return similarity(report.getLastTrace()) >= similarityTreshold;
	}
}
