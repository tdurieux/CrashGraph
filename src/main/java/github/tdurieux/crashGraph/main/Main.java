package github.tdurieux.crashGraph.main;

import github.tdurieux.crashGraph.entities.Report;
import github.tdurieux.crashGraph.parser.ReportParse;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class Main {
	
	public static void main(String[] args) throws IOException {
		Report report1 = openReport("src/main/resource/reports/1755.json");
		Report report2 = openReport("src/main/resource/reports/1759.json");
		
		double similarity = report1.getTraces().get(0).similarity(report2.getTraces().get(0));
		System.out.println("Similarity: " + similarity);
	}
	
	private static Report openReport(String filename) throws IOException {
		File file = new File(filename);
		return ReportParse.parse(Files.readAllBytes(file.toPath()));
	}
}