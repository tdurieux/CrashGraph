package github.tdurieux.crashGraph.entities;

import github.tdurieux.crashGraph.classifier.Classifier;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Buckets extends HashSet<Bucket> implements Set<Bucket> {
	private static final Logger log = Logger.getLogger(Buckets.class.getName());

	private static final long serialVersionUID = -2876791058687731586L;

	public Buckets() {

	}

	public Buckets(Set<Report> reports, Classifier classifier)
			throws IOException {
		for (Report report : reports) {
			log.log(Level.FINER, "Scanning report: " + report.getBugId());
			boolean reportBucketed = false;
			for (Bucket bucket : this) {
				if (classifier.isSameBucket(bucket, report.getLastTrace().getLastCausedBy())) {
					bucket.add(report);
					reportBucketed = true;
					break;
				}
			}
			if (!reportBucketed) {
				log.log(Level.FINER, "Creating a new Bucket.");
				this.add(new Bucket(report));
			}
		}
	}

	public int numReports() {
		int numReports = 0;
		for (Bucket bucket : this) {
			numReports += bucket.getReportIds().size();
		}
		return numReports;
	}

	public void printBuckets(String fileName) throws FileNotFoundException,
			UnsupportedEncodingException {
		PrintWriter writer = new PrintWriter(fileName, "UTF-8");
		for (Bucket bucket : this) {
			writer.println(bucket.toString());
		}
		writer.close();
	}
}
