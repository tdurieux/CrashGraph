package github.tdurieux.graph;

/**
 * is an abstract node in a graph
 * 
 * @author Thomas Durieux
 * 
 * @param <T>
 *            the node type
 */
public abstract class Node<T extends Node<?>> {
	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}