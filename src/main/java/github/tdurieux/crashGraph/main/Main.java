package github.tdurieux.crashGraph.main;

import github.tdurieux.crashGraph.classifier.Classifier;
import github.tdurieux.crashGraph.classifier.GraphViewClassifier;
import github.tdurieux.crashGraph.entities.Buckets;
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
			Buckets buckets = BucketsParser.parse(args[0], classifier);

			System.out.println(buckets.size() + " buckets where created from: "
					+ buckets.numReports() + " reports.");
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
