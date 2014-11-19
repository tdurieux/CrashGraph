package github.tdurieux.crashGraph.entities;

import java.util.Date;
import java.util.List;

import github.tdurieux.graph.Node;

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

	public void setTraces(List<Trace> traces) {
		this.traces = traces;
	}
}
