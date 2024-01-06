package ca.mcmaster.cas.se2aa4.island.starnetworks;

import ca.mcmaster.cas.se2aa4.a2.io.Structs;
import ca.mcmaster.cas.se2aa4.a2.pathfinder.graph.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class star {


    public Map<Edges, Integer> createStarNetwork(Nodes hubNode, GraphADT graph, ArrayList<Nodes> nodes, Map<Edges, Integer> edges) {
        // initialize a map for the star network edges
        Map<Edges, Integer>  final_edges = new HashMap<>();

        // create an instance of DijkstraSSpP
        PathFinder pathfinder = new DijkstraSSP();
        pathfinder.Graph(graph);

        // compute the shortest path from hubnode
        pathfinder.compute_SP(hubNode);
        // the loop will go over all the special nodes (cities etc) and compute the shortest path to that
        for (Nodes node : nodes) {
            if (node != hubNode) {
                List<Nodes> path = pathfinder.SP(node);
                if (!path.isEmpty()) {
                    Nodes prevNode = hubNode;
                    for (int i = 0; i < path.size(); i++) {
                        Nodes nextNode = path.get(i);
                        // actually computes the edges
                        map_final(prevNode, nextNode, final_edges,edges);
                        prevNode = nextNode;
                    }
                }
            }
        }
        return final_edges;
    }
    public void map_final(Nodes prevNode, Nodes nextNode, Map<Edges, Integer>  final_edges,Map<Edges, Integer> edges){
        //EDGES ON THE LAKES ACT AS BRIDGES AS SAID BY THE PROFESSOR (IMPORTANT)

        // I go over the previous edges I had, I then just check if the nodes match the given nodes in the parameter
        for (Map.Entry<Edges,Integer> edge: edges.entrySet()){
            Integer value = edge.getValue();
            Edges edge_key = edge.getKey();
            Nodes node_1=edge_key.getNode1();
            Nodes node_2=edge_key.getNode2();
            if (prevNode==node_1 || prevNode==node_2){
                if(nextNode==node_2 || nextNode==node_1){
                    // add that existing edge in the map
                    final_edges.put(edge_key,value);
                }
            }

        }

    }
}

