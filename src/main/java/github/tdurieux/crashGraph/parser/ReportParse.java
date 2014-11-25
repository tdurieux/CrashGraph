package github.tdurieux.crashGraph.parser;

import github.tdurieux.crashGraph.entities.Report;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;

/**
 * A parser of report file
 * @author edmondvanovertveldt
 */
public class ReportParse {
	public static Report parseFile(File file) {
		try {
			return parse(Files.readAllBytes(file.toPath()));
		} catch (IOException e) {
			throw new RuntimeException("Unable to parse "
					+ file.getAbsolutePath(), e);
		}
	}

	public static Report parse(String text) {
		try {
			return JSON.parseObject(text, Report.class);
		} catch (JSONException e) {
			throw new RuntimeException("Unable to parse " + text, e);
		}
	}

	public static Report parse(byte[] bytes) {
		return JSON.parseObject(bytes, Report.class);
	}

	public static List<Report> parseFolder(String foldername) {
		File folder = new File(foldername);
		List<Report> reports = new ArrayList<Report>();
		if (!folder.isDirectory()) {
			throw new RuntimeException("The " + foldername + " is not a folder");
		}

		File[] files = folder.listFiles();
		for (File file : files) {
			String fileName = file.getAbsolutePath();
			// log.log(Level.FINER, "Scanning report: " + fileName);
			Report report = ReportParse.parse(fileName);
			reports.add(report);
		}
		return reports;
	}
}
