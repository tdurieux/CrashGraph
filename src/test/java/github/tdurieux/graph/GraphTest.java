package github.tdurieux.graph;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.IOException;

import github.tdurieux.crashGraph.entities.Bucket;
import github.tdurieux.crashGraph.entities.Report;

import org.junit.Before;
import org.junit.Test;

public class GraphTest {

	private Graph<NodeElement> g1;
	private Graph<NodeElement> g2;
	private NodeElement n1;
	private NodeElement n2;
	private NodeElement n3;
	private NodeElement n4;
	private NodeElement n5;

	@Before
	public void setUp() {
		g1 = new GraphElement();
		g2 = new GraphElement();
		n1 = new NodeElement();
		n2 = new NodeElement();
		n3 = new NodeElement();
		n4 = new NodeElement();
		n5 = new NodeElement();
	}

	@Test
	public void creation_empty_graph() {
		assertEquals(0, g1.numberOfEdges());
		assertEquals(0, g1.getNodes().size());
		assertTrue(g1.isEmpty());
	}

	@Test
	public void add_edge_to_graph() {
		g1.addEdge(n1, n2);

		assertEquals(1, g1.numberOfEdges());
		assertEquals(2, g1.getNodes().size());
		assertFalse(g1.isEmpty());
	}

	@Test
	public void add_recursive_edge_to_graph() {
		g1.addEdge(n2, n2);
		assertEquals(1, g1.numberOfEdges());
		assertEquals(1, g1.getNodes().size());
	}

	@Test
	public void add_existing_edge_to_graph() {
		g1.addEdge(n1, n2);

		assertEquals(1, g1.numberOfEdges());
		assertEquals(2, g1.getNodes().size());

		g1.addEdge(n1, n2);
		assertEquals(1, g1.numberOfEdges());
		assertEquals(2, g1.getNodes().size());
	}

	@Test
	public void remove_existing_edge_from_graph() {
		g1.addEdge(n1, n2);

		g1.removeEdge(n1, n2);

		assertEquals(0, g1.numberOfEdges());
		assertEquals(0, g1.getNodes().size());
	}

	@Test
	public void remove_existing_edge_with_2_parent_from_graph() {
		g1.addEdge(n1, n2);
		g1.addEdge(n3, n2);

		g1.removeEdge(n1, n2);

		assertEquals(1, g1.numberOfEdges());
		assertEquals(2, g1.getNodes().size());
	}

	@Test
	public void intersection_null_graph() {
		assertTrue(g1.intersection(null).isEmpty());
	}

	@Test
	public void intersection_empty_graph() {
		assertTrue(g1.intersection(g2).isEmpty());
	}

	@Test
	public void no_intersection_graph() {
		g1.addEdge(n1, n2);
		g2.addEdge(n3, n4);
		assertTrue(g1.intersection(g2).isEmpty());
	}

	@Test
	public void intersection_one_node_graph() {
		g1.addEdge(n1, n2);
		g1.addEdge(n2, n3);
		g2.addEdge(n3, n4);

		Graph<NodeElement> intersection = g1.intersection(g2);
		assertEquals(0, intersection.numberOfEdges());
		assertEquals(0, intersection.getNodes().size());

		assertFalse("The node n3 was found in the graph", intersection
				.getNodes().contains(n3));
	}

	@Test
	public void intersection_graph() {
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
	public void union_null_graph() {
		g1.union(null);
		assertTrue(g1.isEmpty());
	}

	@Test
	public void union_empty_graph() {
		assertTrue(g1.intersection(g2).isEmpty());
	}

	@Test
	public void union_graph() {
		g1.addEdge(n1, n2);
		g1.addEdge(n2, n3);
		g1.addEdge(n3, n5);
		g2.addEdge(n3, n4);
		g2.addEdge(n3, n5);

		g1.union(g2);
		assertEquals(4, g1.numberOfEdges());
		assertEquals(5, g1.getNodes().size());
	}

	@Test
	public void similarity_empty_graph() {
		double value = g1.similarity(g2);
		assertEquals(0.0, value, 0);
	}

	@Test
	public void similarity_identical_graph() {
		g1.addEdge(n1, n2);
		g2.addEdge(n1, n2);
		double value = g1.similarity(g2);

		assertEquals(1.0, value, 0);
	}

	@Test
	public void similarity_distinct_graph() {
		g1.addEdge(n1, n2);
		g2.addEdge(n3, n4);
		double value = g1.similarity(g2);

		assertEquals(0, value, 0);
	}

	@Test
	public void similarity_graph() {
		g1.addEdge(n1, n2);
		g1.addEdge(n2, n3);
		g1.addEdge(n3, n5);
		g2.addEdge(n3, n4);
		g2.addEdge(n3, n5);

		double value = g1.similarity(g2);

		assertEquals(1 / 2.0, value, 0);
	}

	@Test
	public void similarity_38861_44221() throws IOException {
		Report report38861 = Report.openReport("src/main/resource/reports/38861.json");
		Bucket bucket38861 = new Bucket(report38861);
		Report report44221 = Report.openReport("src/main/resource/reports/44221.json");
		assertEquals(1.0, bucket38861.similarity(report44221.getLastTrace().getLastCausedBy()),0.001);
	}
	
	private class NodeElement extends Node {
	}

	private class GraphElement extends Graph<NodeElement> {
	}
}
