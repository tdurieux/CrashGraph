package github.tdurieux.crashGraph.entities;

import java.util.List;

import github.tdurieux.graph.Graph;

public class Trace extends Graph<Method> {
	private String exceptionType;
	private Trace causedBy;

	public Trace getCausedBy() {
		return causedBy;
	}

	public void setCausedBy(Trace causedBy) {
		this.causedBy = causedBy;
	}

	public String getExceptionType() {
		return exceptionType;
	}

	public void setExceptionType(String exceptionType) {
		this.exceptionType = exceptionType;
	}

	public void setElements(List<Method> elements) {
		int size = elements.size();
		if (size < 2) {
			return;
		}
		Method method1 = elements.get(0);

		for (int i = 1; i < size; i++) {
			Method method2 = elements.get(i);
			this.addEdge(method1, method2);
			method1 = method2;
		}
	}
	
	public Trace getLastCausedBy() {
		if(causedBy == null) {
			return this;
		}
		return causedBy.getLastCausedBy();
	}
}
