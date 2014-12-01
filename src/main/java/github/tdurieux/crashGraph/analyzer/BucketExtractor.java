package github.tdurieux.crashGraph.analyzer;

import static java.nio.file.CopyOption.*;
import github.tdurieux.crashGraph.entities.Report;
import github.tdurieux.crashGraph.entities.Trace;
import github.tdurieux.crashGraph.parser.ReportParse;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class BucketExtractor {
	public static void main(String[] args) throws IOException {
		if (args.length < 2) {
			System.out
					.println("The first argument must be the path to the report folder");
			return;
		}

		Map<Integer, List<File>> reportsByGroupId = new HashMap<Integer, List<File>>();

		File folder = new File(args[0]);
		File outputFolder = new File(args[1]);
		outputFolder.mkdirs();
		
		File[] files = folder.listFiles();
		for (File file : files) {
			Report report = ReportParse.parseFile(file);
			if (!reportsByGroupId.containsKey(report.getGroupId())) {
				reportsByGroupId
						.put(report.getGroupId(), new ArrayList<File>());
			}
			reportsByGroupId.get(report.getGroupId()).add(file);
		}

		for (Iterator iterator = reportsByGroupId.values().iterator(); iterator
				.hasNext();) {
			List<File> reports = (List<File>) iterator.next();
			if (reports.size() > 1) {
				for (File file : reports) {
					File output = new File(outputFolder.getAbsolutePath() + "/"
							+ file.getName());
					Files.copy(file.toPath(), output.toPath());
				}
			}
		}
	}
}
