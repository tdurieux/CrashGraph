package github.tdurieux.crashGraph.main;

import github.tdurieux.crashGraph.classifier.Classifier;
import github.tdurieux.crashGraph.classifier.GraphViewClassifier;
import github.tdurieux.crashGraph.entities.Bucket;
import github.tdurieux.crashGraph.entities.Buckets;
import github.tdurieux.crashGraph.entities.Report;
import github.tdurieux.crashGraph.entities.Validator;
import github.tdurieux.crashGraph.parser.BucketsParser;

import java.io.IOException;

public class Main {

	public static void main(String[] args) {
		if (args.length < 2) {
			usage();
			return;
		}
		System.out.println("Creating buckets for the reports in: " + args[0]
				+ "\nwith similarity threshold: " + args[1]);
		try {
			Classifier classifier = new GraphViewClassifier(
					Double.parseDouble(args[1]));
			Validator valReport = new Validator();
			Buckets buckets = BucketsParser.parse(args[0], classifier,
					valReport);
			System.out.println("FALSE NEGATIVES");
			for (Bucket bucket : valReport.getFalseNegatives().keySet()) {
				System.out.println(bucket.getReportIds());
				for (Report report : valReport.getFalseNegatives().get(bucket)) {
					System.out.println("\t" + report.getBugId());
				}
			}
			System.out.println("FALSE POSITIVE");
			for (Bucket bucket : valReport.getFalsePositives().keySet()) {
				System.out.println(bucket.getReportIds());
				for (Report report : valReport.getFalsePositives().get(bucket)) {
					System.out.println("\t" + report.getBugId());
				}
			}

			System.out.println(buckets.size() + "CONCLUSION: buckets where created from: "
					+ buckets.numReports() + " reports. There where "
					+ valReport.getNumFalseNegatives()
					+ " false negatives and "
					+ valReport.getNumFalsePositives() + " false positives.");
			buckets.printBuckets("buckets.txt");
		} catch (NumberFormatException e) {
			System.out
					.println("The second argument to main should be a double.");
		} catch (IOException e) {
			System.out
					.println("Are you sure of the directory you are asking to scan?");
		}
	}

	private static void usage() {
		System.out
				.println("Main needs 2 arguments: \n"
						+ "\t 1) the directory where the crashes are saved. (String) \n"
						+ "\t 2) the similarity threshold. (double)");
	}
}
