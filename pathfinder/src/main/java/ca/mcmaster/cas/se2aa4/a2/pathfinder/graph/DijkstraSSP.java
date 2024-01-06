package ca.mcmaster.cas.se2aa4.a2.pathfinder.graph;

import java.util.*;

public class DijkstraSSP implements PathFinder{

    // initialize graph and map
    private GraphADT graph;
    private HashMap<Nodes, Nodes> prev;

    public void Graph(GraphADT graph) {
        this.graph = graph;
    }
    public void compute_SP(Nodes source) {

        prev = new HashMap<>();
        // a map that takes care of the distance of the path so that the path isn't too long
        HashMap<Nodes, Double> distance = new HashMap<>();

        // iterator instance for the nodes
        Iterator<Nodes> node_Iterator = graph.getNodes().iterator();

        // set all distances to infinity
        do {
            Nodes node = node_Iterator.next();
            distance.put(node, Double.POSITIVE_INFINITY);
            prev.put(node, null);
        } while (node_Iterator.hasNext());

        //initialize a priority queue and set distance of source to 0
        PriorityQueue<Nodes> node_queue = new PriorityQueue<>(Comparator.comparingDouble(distance::get));
        distance.put(source, 0.0);
        node_queue.offer(source);

        // loop through to find all the edges that come out from the source node
        for (int i = 0; i < graph.getNodes().size(); i++) {
            Nodes node = node_queue.poll();
            List<Edges> edges = graph.getEdges(node);
            if (edges!=null) {
                for (Edges edge : edges) {
                    if ((distance.get(node) + edge.getDistance()) < distance.get(edge.getNode2())) {
                        prev.put(edge.getNode2(), node);
                        distance.put(edge.getNode2(), distance.get(node) + edge.getDistance());
                        node_queue.remove(edge.getNode2());
                        node_queue.add(edge.getNode2());
                    }
                }
            }
        }
    }
    public List<Nodes> SP(Nodes end_node) {

        // initialize a list of paths
        List<Nodes> path = new ArrayList<>();

        // loop through to get the path
        for (Nodes current_node = end_node; current_node != null; current_node = prev.get(current_node)) {
            path.add(current_node);
        }

        // reverse to get the actual path
        Collections.reverse(path);

        return path;
    }
}


