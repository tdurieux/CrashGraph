package github.tdurieux.crashGraph.entities;

import github.tdurieux.graph.Node;

/**
 * A method of the stack trace
 * 
 * @author Thomas Durieux
 * 
 */
public class Method extends Node {

	private String simpleName;

	private String file;

	private int line;

	private boolean isCompiled;

	public Method() {

	}

	public Method(String simpleName, String qualifiedName, String file, int line) {
		super();
		this.simpleName = simpleName;
		this.setName(qualifiedName);
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
		return this.getName();
	}

	public void setQualifiedName(String qualifiedName) {
		this.setName(qualifiedName);
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
		this.setName(m);
	}

	public void setSource(String source) {
		if (source.contains("(Compiled Code)")) {
			this.isCompiled = true;
			source = source.replaceAll("\\(Compiled Code\\)", "");
		}
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
		return this.getQualifiedName().hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof Method)) {
			return false;
		}
		Method m2 = (Method) obj;

		if (m2.isCompiled || this.isCompiled) {
			return this.getQualifiedName().equals(m2.getQualifiedName())
					&& m2.file.equals(this.file);
		}

		if (this.line != m2.line) {
			return false;
		}
		return this.getQualifiedName().equals(m2.getQualifiedName());
	}
}
