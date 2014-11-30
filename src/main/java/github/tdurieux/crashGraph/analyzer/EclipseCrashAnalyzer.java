package github.tdurieux.crashGraph.analyzer;

import github.tdurieux.crashGraph.entities.Report;
import github.tdurieux.crashGraph.entities.Trace;
import github.tdurieux.crashGraph.parser.ReportParse;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class EclipseCrashAnalyzer {
	public static void main(String[] args) {
		if(args.length < 1) {
			System.out.println("The first argument must be the path to the report folder");
			return;
		}
		
		Map<Integer, List<Report>> reportsByGroupId = new HashMap<Integer, List<Report>>();
		int reportCount = 0;
		int traceCount = 0;
		
		File folder = new File(args[0]);
		File[] files = folder.listFiles();
		for (File file : files) {
			Report report = ReportParse.parseFile(file);
			reportCount++;
			for (Trace trace : report.getTraces()) {
				traceCount++;
			}
			if(!reportsByGroupId.containsKey(report.getGroupId())){
				reportsByGroupId.put(report.getGroupId(), new ArrayList<Report>());
			}
			reportsByGroupId.get(report.getGroupId()).add(report);
		}
		Map<Integer, Integer> bucketSize = new HashMap<>();
		for (Iterator iterator = reportsByGroupId.values().iterator(); iterator.hasNext();) {
			List<Report> reports = (List<Report>) iterator.next();
			if(!bucketSize.containsKey(reports.size())) {
				bucketSize.put(reports.size(), 1);
			} else {
				bucketSize.put(reports.size(), bucketSize.get(reports.size()) + 1);
			}
		}
		System.out.println(String.format("Number of reports: %d\nNumber of traces: %d\nNumber of buckets: %d", reportCount, traceCount, reportsByGroupId.keySet().size()));
		for (Iterator iterator = bucketSize.keySet().iterator(); iterator.hasNext();) {
			Integer size = (Integer) iterator.next();
			System.out.println(String.format("%d buckets of size: %d", bucketSize.get(size), size));
		}
	}
}
