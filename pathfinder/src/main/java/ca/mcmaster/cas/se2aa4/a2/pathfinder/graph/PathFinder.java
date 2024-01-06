package ca.mcmaster.cas.se2aa4.a2.pathfinder.graph;

import java.util.List;

public interface PathFinder {

    // get the graph
    void Graph(GraphADT graph);

    // select a node to start the path and compute path
    void compute_SP(Nodes start_node);

    // select a destination node and compute the shortest path
    List<Nodes> SP(Nodes end_node);

}
