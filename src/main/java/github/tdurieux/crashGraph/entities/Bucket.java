package github.tdurieux.crashGraph.entities;

import github.tdurieux.graph.Graph;

import java.util.HashSet;
import java.util.Set;

/* The Bucket class only keeps track of the representing graph
 * and the Id of the reports inserted in the bucket. This should
 * be all the information it needs, if unexpectedly more 
 * information is needed it can ask to load the report again.
 * This saves up on memory in the context of BIG data. 
 */
public class Bucket extends Graph<Method> {

	/**
	 * Set of crash reports
	 */
	Set<Integer> reportIds;

	public Bucket(Report initialReport) {
		this.graph = initialReport.getLastTrace().getEdges();
		this.reportIds = new HashSet<Integer>();
		this.reportIds.add(initialReport.getBugId());
	}

	public Set<Integer> getReportIds() {
		return reportIds;
	}

	/**
	 * Add a report in a bucket
	 * 
	 * @param report
	 */
	public void add(Report report) {
		// Add bugId is list of report Id
		reportIds.add(report.getBugId());
		// construct the new bucket graph
		this.union(report.getLastTrace());
	}

	public String toString() {
		String output = "";
		for (Integer integer : reportIds) {
			output += integer + " ";
		}
		return output;
	}
}
