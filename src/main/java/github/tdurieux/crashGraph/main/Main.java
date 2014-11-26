package github.tdurieux.crashGraph.main;

import github.tdurieux.crashGraph.classifier.Classifier;
import github.tdurieux.crashGraph.classifier.GraphViewClassifier;
import github.tdurieux.crashGraph.entities.Buckets;
import github.tdurieux.crashGraph.entities.Validator;
import github.tdurieux.crashGraph.entities.matchReport;
import github.tdurieux.crashGraph.parser.BucketsParser;

import java.io.IOException;

public class Main {

	public static void main(String[] args) {
		if (args.length < 2) {
			usage();
			return;
		}
		try {
			Classifier classifier = new GraphViewClassifier(
					Double.parseDouble(args[1]));
			Validator valReport = new Validator();
			Buckets buckets = BucketsParser.parse(args[0], classifier,
					valReport);

			System.out.println("The following unperfected matches where found:");
			
			for (matchReport unperfectMatch : valReport.getAllMismatches()) {
				System.out.println(unperfectMatch + "\n");
			}
			
			System.out.println("Creating buckets for the reports in: "
					+ args[0] + "\nwith similarity threshold: " + args[1]);

			System.out.println("\nCONCLUSION: "
					+ buckets.size()
					+ " buckets where created from: "
					+ buckets.numReports()
					+ " reports.\n There was \n"
					+ " a precision of: " + valReport.getMeanPrecision()
					+ "\n and a recall of: " + valReport.getMeanRecall());
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
