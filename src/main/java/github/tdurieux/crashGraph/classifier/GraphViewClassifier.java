package github.tdurieux.crashGraph.classifier;

import github.tdurieux.crashGraph.entities.Method;
import github.tdurieux.graph.Graph;

public class GraphViewClassifier implements Classifier {

	private double threshold;

	public GraphViewClassifier(double similarityTreshold) {
		this.threshold = similarityTreshold;
	}

	@Override
	public boolean isSameBucket(Graph<Method> elem1, Graph<Method> elem2) {
		return elem1.similarity(elem2) >= this.threshold;
	}

}
