package github.tdurieux.crashGraph.main;

import github.tdurieux.crashGraph.entities.Report;

import java.io.IOException;

public class Main {
	
	public static void main(String[] args) throws IOException {
		Report report1 = Report.openReport("src/main/resource/reports/1755.json");
		Report report2 = Report.openReport("src/main/resource/reports/1759.json");
		
		double similarity = report1.getLastTrace().similarity(report2.getLastTrace());
		System.out.println("Similarity: " + similarity);
	}
}