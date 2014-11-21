package github.tdurieux.crashGraph.entities;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
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

	public static Set<Bucket> createBuckets(String reportDir,
			double similarityTreshold) throws IOException {
		Set<Bucket> buckets = new HashSet<>();
		for (String reportName : Report.getReportNames(reportDir)) {
			System.out.println("Scanning report: " + reportName);
			Report report = Report.openReport(reportName);
			boolean reportBucketed = false;
			for (Bucket bucket : buckets) {
				if (bucket.fits(report, similarityTreshold)) {
					bucket.add(report);
					reportBucketed = true;
					break;
				}
			}
			if (!reportBucketed) {
				System.out.println("Creating a new Bucket.");
				buckets.add(new Bucket(report));
			}
		}
		return buckets;
	}

	public static int numReports(Set<Bucket> buckets) {
		int numReports = 0;
		for (Bucket bucket : buckets) {
			numReports += bucket.reportIds.size();
		}
		return numReports;
	}

	public static void printBuckets(Set<Bucket> buckets, String fileName)
			throws FileNotFoundException, UnsupportedEncodingException {
		PrintWriter writer = new PrintWriter(fileName, "UTF-8");
		for (Bucket bucket : buckets) {
			writer.println(bucket.toString());
		}
		writer.close();
	}

	public Set<Integer> getReportIds() {
		return reportIds;
	}

	public boolean fits(Report report, double similarityTreshold) {
		return similarity(report.getLastTrace()) >= similarityTreshold;
	}

	public void add(Report report) {
		reportIds.add(report.getBugId());
		union(report.getLastTrace());
	}

	public String toString() {
		String output = "";
		for (Integer integer : reportIds) {
			output += integer.toString() + ".json ";
		}
		return output;
	}
}
