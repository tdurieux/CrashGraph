package github.tdurieux.crashGraph.classifier;

import github.tdurieux.crashGraph.entities.Method;
import github.tdurieux.graph.Graph;

public interface Classifier {
	/**
	 * 
	 * @param elem1
	 * @param elem2
	 * @return
	 */
	boolean isSameBucket(Graph<Method> elem1, Graph<Method> elem2);
}
