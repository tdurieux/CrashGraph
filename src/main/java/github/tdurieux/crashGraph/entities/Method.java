package github.tdurieux.crashGraph.entities;

import github.tdurieux.graph.Node;

/**
 * is a method of the stack trace
 * 
 * @author Thomas Durieux
 * 
 */
public class Method extends Node<Method> {

	private String simpleName;

	private String qualifiedName;

	private String file;

	private int line;

	public Method() {

	}

	public Method(String simpleName, String qualifiedName, String file, int line) {
		super();
		this.simpleName = simpleName;
		this.qualifiedName = qualifiedName;
		this.file = file;
		this.line = line;
	}

	public String getSimpleName() {
		return simpleName;
	}

	public void setSimpleName(String simpleName) {
		this.simpleName = simpleName;
	}

	public String getQualifiedName() {
		return qualifiedName;
	}

	public void setQualifiedName(String qualifiedName) {
		this.qualifiedName = qualifiedName;
	}

	public int getLine() {
		return line;
	}

	public void setLine(int line) {
		this.line = line;
	}

	public String getFile() {
		return file;
	}

	public void setFile(String file) {
		this.file = file;
	}

	public void setMethod(String m) {
		String[] split = m.split("\\.");
		this.qualifiedName = m;
	}

	public void setSource(String source) {
		String[] split = source.split(":");
		this.file = split[0];
		if (split.length > 1) {
			String line = split[1];
			try {
				this.line = Integer.parseInt(line.replaceAll("([^0-9])+", ""));
			} catch (NumberFormatException e) {
				this.line = 0;
			}
		}
	}

	@Override
	public String toString() {
		return this.getQualifiedName();
	}

	@Override
	public int hashCode() {
		return (this.file + this.line).hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof Method)) {
			return false;
		}
		Method m2 = (Method) obj;

		return (this.getFile() + this.getLine()).equals((m2.getFile() + m2
				.getLine()));
	}

	@Override
	protected Object clone() {
		Method ouput = new Method(this.simpleName, this.qualifiedName,
				this.file, this.line);

		return ouput;
	}

}
