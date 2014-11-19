package github.tdurieux.graph;

import static org.junit.Assert.*;

import org.junit.Test;

public class GraphTest {

	@Test
	public void creation_empty_graph() {
		Graph<?> g = new GraphElement();
		assertEquals(0, g.numberOfEdges());
		assertEquals(0, g.getNodes().size());
		assertTrue(g.isEmpty());
	}

	@Test
	public void add_edge_to_graph() {
		Graph<NodeElement> g = new GraphElement();
		NodeElement n1 = new NodeElement();
		NodeElement n2 = new NodeElement();

		g.addEdge(n1, n2);

		assertEquals(1, g.numberOfEdges());
		assertEquals(2, g.getNodes().size());
		assertFalse(g.isEmpty());
	}

	@Test
	public void add_recursive_edge_to_graph() {
		Graph<NodeElement> g = new GraphElement();
		NodeElement n2 = new NodeElement();

		g.addEdge(n2, n2);
		assertEquals(1, g.numberOfEdges());
		assertEquals(1, g.getNodes().size());
	}

	@Test
	public void add_existing_edge_to_graph() {
		Graph<NodeElement> g = new GraphElement();
		NodeElement n1 = new NodeElement();
		NodeElement n2 = new NodeElement();

		g.addEdge(n1, n2);

		assertEquals(1, g.numberOfEdges());
		assertEquals(2, g.getNodes().size());

		g.addEdge(n1, n2);
		assertEquals(1, g.numberOfEdges());
		assertEquals(2, g.getNodes().size());
	}
	
	@Test
	public void remove_existing_edge_from_graph() {
		Graph<NodeElement> g = new GraphElement();
		NodeElement n1 = new NodeElement();
		NodeElement n2 = new NodeElement();

		g.addEdge(n1, n2);
		
		g.removeEdge(n1, n2);

		assertEquals(0, g.numberOfEdges());
		assertEquals(0, g.getNodes().size());
	}

	@Test
	public void remove_existing_edge_with_2_parent_from_graph() {
		Graph<NodeElement> g = new GraphElement();
		NodeElement n1 = new NodeElement();
		NodeElement n2 = new NodeElement();
		NodeElement n3 = new NodeElement();

		g.addEdge(n1, n2);
		g.addEdge(n3, n2);
		
		g.removeEdge(n1, n2);

		assertEquals(1, g.numberOfEdges());
		assertEquals(2, g.getNodes().size());
	}
	
	@Test
	public void intersection_null_graph() {
		Graph<NodeElement> g1 = new GraphElement();

		assertTrue(g1.intersection(null).isEmpty());
	}

	@Test
	public void intersection_empty_graph() {
		Graph<NodeElement> g1 = new GraphElement();
		Graph<NodeElement> g2 = new GraphElement();

		assertTrue(g1.intersection(g2).isEmpty());
	}

	@Test
	public void no_intersection_graph() {
		Graph<NodeElement> g1 = new GraphElement();
		Graph<NodeElement> g2 = new GraphElement();

		NodeElement n1 = new NodeElement();
		NodeElement n2 = new NodeElement();
		NodeElement n3 = new NodeElement();
		NodeElement n4 = new NodeElement();

		g1.addEdge(n1, n2);
		g2.addEdge(n3, n4);
		assertTrue(g1.intersection(g2).isEmpty());
	}

	@Test
	public void intersection_one_node_graph() {
		Graph<NodeElement> g1 = new GraphElement();
		Graph<NodeElement> g2 = new GraphElement();

		NodeElement n1 = new NodeElement();
		NodeElement n2 = new NodeElement();
		NodeElement n3 = new NodeElement();
		NodeElement n4 = new NodeElement();

		g1.addEdge(n1, n2);
		g1.addEdge(n2, n3);
		g2.addEdge(n3, n4);

		Graph<NodeElement> intersection = g1.intersection(g2);
		assertEquals(0, intersection.numberOfEdges());
		assertEquals(1, intersection.getNodes().size());

		assertTrue("The node n3 is not found in the graph", intersection
				.getNodes().contains(n3));
	}

	@Test
	public void intersection_graph() {
		Graph<NodeElement> g1 = new GraphElement();
		Graph<NodeElement> g2 = new GraphElement();

		NodeElement n1 = new NodeElement();
		NodeElement n2 = new NodeElement();
		NodeElement n3 = new NodeElement();
		NodeElement n4 = new NodeElement();
		NodeElement n5 = new NodeElement();

		g1.addEdge(n1, n2);
		g1.addEdge(n2, n3);
		g1.addEdge(n3, n5);
		g2.addEdge(n3, n4);
		g2.addEdge(n3, n5);

		Graph<NodeElement> intersection = g1.intersection(g2);
		assertEquals(1, intersection.numberOfEdges());
		assertEquals(2, intersection.getNodes().size());

		assertTrue("The node n3 is not found in the graph", intersection
				.getNodes().contains(n3));
		assertTrue("The node n3 is not found in the graph", intersection
				.getNodes().contains(n5));
		assertTrue("The edge n3-n5 is not found in the graph", intersection
				.getEdges(n3).contains(n5));
	}

	@Test
	public void similarity_empty_graph() {
		Graph<NodeElement> g1 = new GraphElement();
		Graph<NodeElement> g2 = new GraphElement();
		double value = g1.similarity(g2);

		assertEquals(0.0, value, 0);
	}

	@Test
	public void similarity_identical_graph() {
		Graph<NodeElement> g1 = new GraphElement();
		Graph<NodeElement> g2 = new GraphElement();
		NodeElement n1 = new NodeElement();
		NodeElement n2 = new NodeElement();

		g1.addEdge(n1, n2);
		g2.addEdge(n1, n2);
		double value = g1.similarity(g2);

		assertEquals(1.0, value, 0);
	}
	
	@Test
	public void similarity_distinct_graph() {
		Graph<NodeElement> g1 = new GraphElement();
		Graph<NodeElement> g2 = new GraphElement();
		NodeElement n1 = new NodeElement();
		NodeElement n2 = new NodeElement();
		
		NodeElement n3 = new NodeElement();
		NodeElement n4 = new NodeElement();

		g1.addEdge(n1, n2);
		g2.addEdge(n3, n4);
		double value = g1.similarity(g2);

		assertEquals(0, value, 0);
	}
	
	@Test
	public void similarity_graph() {
		Graph<NodeElement> g1 = new GraphElement();
		Graph<NodeElement> g2 = new GraphElement();
		
		NodeElement n1 = new NodeElement();
		NodeElement n2 = new NodeElement();
		NodeElement n3 = new NodeElement();
		NodeElement n4 = new NodeElement();
		NodeElement n5 = new NodeElement();

		g1.addEdge(n1, n2);
		g1.addEdge(n2, n3);
		g1.addEdge(n3, n5);
		g2.addEdge(n3, n4);
		g2.addEdge(n3, n5);
		
		double value = g1.similarity(g2);

		assertEquals(1/2.0, value, 0);
	}

	private class NodeElement extends Node {
	}

	private class GraphElement extends Graph<NodeElement> {
	}
}
