package github.tdurieux.graph;

import java.util.Collection;
import java.util.HashSet;
import java.util.Map.Entry;
import java.util.Set;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;

/**
 * A abstract graph representation
 *
 * @author Thomas Durieux
 *
 * @param <T> le type d'un noeud
 */
public abstract class Graph<T extends Node> {

    protected Multimap<T, T> graph;

    public Graph() {
        this.graph = HashMultimap.create();
    }
    
    /**
     * Constructed the intersection between 2 graphs
     *
     * @param g2 2nd graph
     * @return intersection graph between 2 graphs
     */
    public Graph<T> intersection(Graph<T> g2) {
        Graph<T> output = new Graph<T>() {
        };
        if (g2 == null) {
            return output;
        }
        for (Entry<T, T> edge : graph.entries()) {
            if (g2.graph.containsEntry(edge.getKey(), edge.getValue())) {
                output.addEdge(edge.getKey(), edge.getValue());
            }
        }
        return output;
    }

    /**
     * performs the union between two graph
     *
     * @param g2 the second graph
     * @return the union graph
     */
    public void union(Graph<T> g2) {
        if (g2 == null) {
            return;
        }
        for (Entry<T, T> edgesG2 : g2.graph.entries()) {
            if (!graph.containsEntry(edgesG2.getKey(), edgesG2.getValue())) {
                this.addEdge(edgesG2.getKey(), edgesG2.getValue());
            }
        }
    }

    /**
     * Indicate the similarity between two graphs
     *
     * @param g2 a graph
     * @return similarity between two graphs
     */
    public double similarity(Graph<T> g2) {
        // Number of edges of the graphs intersection
        double output = this.intersection(g2).numberOfEdges();
        double minEdgeSize = Math.min(this.numberOfEdges(), g2.numberOfEdges());
        if (minEdgeSize == 0) {
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
        return graph.size();
    }

    /**
     * add a new node in the graph
     *
     * @param node the node
     */
    public void addEdge(T node1, T node2) {
        graph.put(node1, node2);
    }

    /**
     * remove a node in the graph
     *
     * @param node
     */
    public void removeEdge(T node1, T node2) {
        graph.remove(node1, node2);
    }

    public Set<T> getNodes() {
        Set<T> Nodes = new HashSet<T>(graph.keySet());
        Nodes.addAll(graph.values());
        return Nodes;
    }

    public Collection<T> getEdges(T node) {
        return graph.get(node);
    }

    public Multimap<T, T> getEdges() {
        return graph;
    }

    public String toDot() {
        String output = "digraph G {\n";
        for (Entry<T, T> edge : graph.entries()) {
            output += "\t\"" + edge.getKey().getName() + "\" -> \""
                    + edge.getValue().getName() + "\";\n";
        }
        return output + "}\n";
    }
}
