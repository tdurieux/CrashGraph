package github.tdurieux.crashGraph.parser;

import github.tdurieux.crashGraph.classifier.Classifier;
import github.tdurieux.crashGraph.entities.Bucket;
import github.tdurieux.crashGraph.entities.Buckets;
import github.tdurieux.crashGraph.entities.Report;
import github.tdurieux.crashGraph.entities.Validator;

import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;

public class BucketsParser {
	private static Logger log = Logger.getLogger(BucketsParser.class.getName());

	public static Buckets parse(String foldername, Classifier classifier, Validator valReport) {
		Buckets buckets = new Buckets();

		File folder = new File(foldername);
		if (!folder.isDirectory()) {
			throw new RuntimeException("The " + foldername + " is not a folder");
		}

		File[] files = folder.listFiles();
		for (int i = 0; i < files.length; i++) {
			File file = files[i];
			log.log(Level.INFO, i + "/" + files.length + " ("
					+ ((int) ((double) i / (double) files.length * 100)) + "%)");
			log.log(Level.FINER, "Scanning report: " + file.getAbsolutePath());
			Report report = ReportParse.parseFile(file);
			boolean reportBucketed = false;
			for (Bucket bucket : buckets) {
				if (classifier.isSameBucket(bucket, report.getLastTrace())) {
					bucket.add(report);
					reportBucketed = true;
					valReport.validate(true, bucket, report);
					break;
				}
				valReport.validate(false, bucket, report);
			}
			if (!reportBucketed) {
				log.log(Level.FINER, "Creating a new Bucket.");
				buckets.add(new Bucket(report));
			}
		}
		return buckets;
	}
}
