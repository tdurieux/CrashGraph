package github.tdurieux.crashGraph.entities;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import github.tdurieux.crashGraph.parser.ReportParse;
import github.tdurieux.graph.Node;

/**
 * A crash report
 * @author edmondvanovertveldt
 */
public class Report extends Node {

	private List<String> comments;
	private List<Trace> traces;
	private List<Date> commentCreationDates;
	private int groupId;
	private int bugId;
	private int duplicateId;
	private Date date;
	private String product;
	private String component;
	private String severity;
	
        /**
         * Return a list of files describing a crash report contained in a directory
         * @param reportDirName directory contained crash reports
         * @return a list of files describing a crash report contained in a directory
         */
	public static Set<String> getReportNames(String reportDirName) {
		Set<String> reports = new HashSet<>();
		for (File file : getReportFiles(reportDirName)) {
			reports.add(file.getPath());
		}
		return reports;
	}
	
	public static Set<File> getReportFiles(String reportDirName) {
		File reportDir = new File(reportDirName);
		Set<File> reports = new HashSet<>();
		Collections.addAll(reports, reportDir.listFiles());
		return reports;
	}
	
        /**
         * Constructed a report from a file
         * @param filename file path contained crash report
         * @return the crash report
         * @throws IOException 
         */
	public static Report openReport(String filename) throws IOException {
		File file = new File(filename);
		return ReportParse.parse(Files.readAllBytes(file.toPath()));
	}

	public int getGroupId() {
		return groupId;
	}

	public void setGroupId(int groupId) {
		this.groupId = groupId;
	}

	public int getBugId() {
		return bugId;
	}

	public void setBugId(int bugId) {
		this.bugId = bugId;
	}

	public int getDuplicateId() {
		return duplicateId;
	}

	public void setDuplicateId(int duplicateId) {
		this.duplicateId = duplicateId;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getProduct() {
		return product;
	}

	public void setProduct(String product) {
		this.product = product;
	}

	public String getComponent() {
		return component;
	}

	public void setComponent(String component) {
		this.component = component;
	}

	public String getSeverity() {
		return severity;
	}

	public void setSeverity(String severity) {
		this.severity = severity;
	}

	public List<Date> getCommentCreationDates() {
		return commentCreationDates;
	}

	public void setCommentCreationDates(List<Date> commentCreationDates) {
		this.commentCreationDates = commentCreationDates;
	}

	public List<String> getComments() {
		return comments;
	}

	public void setComments(List<String> comments) {
		this.comments = comments;
	}

	public List<Trace> getTraces() {
		return traces;
	}
	
	public Trace getLastTrace() {
		return traces.get(traces.size()-1);
	}

	public void setTraces(List<Trace> traces) {
		this.traces = traces;
	}
}
