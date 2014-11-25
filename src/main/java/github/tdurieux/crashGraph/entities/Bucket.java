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
	
	/**
	 * Set of group Id's, which denote the manually sorted buckets
	 */
	Set<Integer> groupIds;

	public Bucket(Report initialReport) {
		this.graph = initialReport.getLastTrace().getEdges();
		this.reportIds = new HashSet<Integer>();
		this.groupIds = new HashSet<Integer>();
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
		// Add groupId to identify how many different bugs end up in the buckets
		groupIds.add(report.getGroupId());
		// construct the new bucket graph
		this.union(report.getLastTrace());
	}

	public int numBugs() {
		return groupIds.size();
	}
	
	public boolean containsBug(int groupId) {
		return groupIds.contains(groupId);
	}
	
 	public String toString() {
		String output = "";
		for (Integer integer : reportIds) {
			output += integer + " ";
		}
		return output;
	}
}
