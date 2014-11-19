package github.tdurieux.graph;

import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;

/**
 * is an abstract representation of a graph
 * 
 * @author Thomas Durieux
 * 
 * @param <T>
 *            the node type
 */
public abstract class Graph<T extends Node<?>> {
	private Map<T, List<T>> graph;

	public Graph() {
		this.graph = new HashMap<T, List<T>>();
	}

	/**
	 * performs the intersection between two graph
	 * 
	 * @param g2
	 *            the second graph
	 * @return the intersection graph
	 */
	public Graph<T> intersection(Graph<T> g2) {
		Graph<T> output = new Graph<T>() {
		};
		if (g2 == null) {
			return output;
		}
		for (T node : this.graph.keySet()) {
			List<T> g2Current = g2.graph.get(node);
			if (g2Current != null) {
				if (!output.graph.containsKey(node)) {
					output.graph.put(node, new ArrayList<T>());
				}
				List<T> current = graph.get(node);
				for (T edge : current) {
					if (g2Current.contains(edge)) {
						output.graph.get(node).add(edge);
					}
				}
			}
		}

		return output;
	}

	public double similarity(Graph<T> g2) {
		double output = this.intersection(g2).numberOfEdges();
		double minEdgeSize = Math.min(this.numberOfEdges(), g2.numberOfEdges());
		if(minEdgeSize == 0) {
			return 0;
		}
		output /= minEdgeSize;
		return output;
	}

	public boolean isEmpty() {
		return this.graph.size() == 0;
	}

	/**
	 * gets the number of edges
	 * 
	 * @return the number of edges
	 */
	public int numberOfEdges() {
		int edges = 0;
		for (T node : this.graph.keySet()) {
			edges += this.graph.get(node).size();
		}
		return edges;
	}

	/**
	 * add a new node in the graph
	 * 
	 * @param node
	 *            the node
	 */
	public void addEdge(T node1, T node2) {
		if (!graph.containsKey(node1)) {
			graph.put(node1, new ArrayList<T>());
		}
		if (!graph.get(node1).contains(node2)) {
			graph.get(node1).add(node2);
		}
		if (!graph.containsKey(node2)) {
			graph.put(node2, new ArrayList<T>());
		}
	}

	/**
	 * remove a node in the graph
	 * 
	 * @param node
	 */
	public void removeEdge(T node1, T node2) {
		if (graph.containsKey(node1)) {
			graph.get(node1).remove(node2);
		}
		if (graph.get(node1).size() == 0) {
			graph.remove(node1);
		}
		if (graph.get(node2).size() == 0) {
			graph.remove(node2);
		}
	}

	public List<T> getNodes() {
		return new ArrayList<T>(graph.keySet());
	}

	public List<T> getEdges(T node) {
		List<T> edges = new ArrayList<T>();
		if (graph.containsKey(node)) {
			edges.addAll(graph.get(node));
		}
		return edges;
	}
}