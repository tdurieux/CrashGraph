package github.tdurieux.crashGraph.parser;

import github.tdurieux.crashGraph.classifier.Classifier;
import github.tdurieux.crashGraph.entities.Bucket;
import github.tdurieux.crashGraph.entities.Buckets;
import github.tdurieux.crashGraph.entities.Report;
import github.tdurieux.crashGraph.entities.Trace;
import github.tdurieux.crashGraph.validator.Validator;

import java.io.File;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;

public class BucketsParser {
	private static Logger log = Logger.getLogger(BucketsParser.class.getName());

	public static Buckets parse(String foldername, Classifier classifier, Validator valReport) {
		Buckets buckets = new Buckets();

		File[] files = getFiles(foldername);
		for (int i = 0; i < files.length; i++) {
			File file = files[i];
			outputLog(files, i + 1, file);
			Report report = ReportParse.parseFile(file);
			Trace reportTraceToAnalyse = report.getLastTrace().getLastCausedBy();
			
			Buckets potentialBuckets = new Buckets();
			for (Bucket bucket : buckets) {
				if (classifier.isSameBucket(bucket, reportTraceToAnalyse)) {
					potentialBuckets.add(bucket);
				}
			}
			
			if (potentialBuckets.isEmpty()) {
				log.log(Level.FINER, "Creating a new Bucket.");
				Bucket bucket = new Bucket(report);
				buckets.add(bucket);
				potentialBuckets.add(bucket);
				valReport.validate(buckets, potentialBuckets, report);
			}
			else {
				valReport.validate(buckets, potentialBuckets, report);
				potentialBuckets.iterator().next().add(report);
			}
		}
		return buckets;
	}

	private static File[] getFiles(String foldername) {
		File folder = new File(foldername);
		if (!folder.isDirectory()) {
			throw new RuntimeException("The " + foldername + " is not a folder");
		}

		File[] files = folder.listFiles();
		Arrays.sort(files);
		return files;
	}

	private static void outputLog(File[] files, int i, File file) {
		log.log(Level.INFO, i + "/" + files.length + " ("
				+ ((int) ((double) i / (double) files.length * 100)) + "%)");
		log.log(Level.INFO, "Scanning report: " + file.getAbsolutePath());
	}
}
