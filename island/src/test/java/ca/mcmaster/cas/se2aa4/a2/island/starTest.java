package ca.mcmaster.cas.se2aa4.a2.island;

import ca.mcmaster.cas.se2aa4.a2.pathfinder.graph.Edges;
import ca.mcmaster.cas.se2aa4.a2.pathfinder.graph.GraphADT;
import ca.mcmaster.cas.se2aa4.a2.pathfinder.graph.Nodes;
import ca.mcmaster.cas.se2aa4.island.starnetworks.star;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class starTest {

    @Test
    public void testCreateStarNetwork() {
        ArrayList<Nodes> node = new ArrayList<>();
        // create the nodes
        Nodes hubNode = new Nodes("New York City", 10);
        Nodes n2 = new Nodes("Los Angeles", 20);
        Nodes n3 = new Nodes("Chicago", 30);
        Nodes n4 = new Nodes("Houston", 40);
        Nodes n5 = new Nodes("Miami", 50);
        // add to the arraylist
        node.add(hubNode);
        node.add(n2);
        node.add(n3);
        node.add(n4);
        node.add(n5);

        //create the arraylist
        Edges e1 = new Edges(hubNode, n2, 5000);
        Edges e2 = new Edges(hubNode, n3, 2000);
        Edges e3 = new Edges(hubNode, n4, 4000);
        Edges e4 = new Edges(hubNode, n5, 3000);

        // make the graph
        GraphADT graph = new GraphADT();
        graph.addNodes(hubNode);
        graph.addNodes(n2);
        graph.addNodes(n3);
        graph.addNodes(n4);
        graph.addNodes(n5);
        graph.addEdges(e1);
        graph.addEdges(e2);
        graph.addEdges(e3);
        graph.addEdges(e4);

        // make the hashmap for edges and segId
        Map<Edges, Integer> edges = new HashMap<>();
        edges.put(e1,1);
        edges.put(e2,2);
        edges.put(e3,3);
        edges.put(e4,4);

        // create the star network
        star star = new star();
        Map<Edges, Integer> starNetwork = star.createStarNetwork(hubNode, graph, node, edges);

        // check the results
        assertEquals(4, starNetwork.size());
        assertTrue(starNetwork.containsKey(e1));
        assertTrue(starNetwork.containsKey(e2));
        assertTrue(starNetwork.containsKey(e3));
        assertTrue(starNetwork.containsKey(e4));
        assertEquals(1, (int)starNetwork.get(e1));
        assertEquals(2, (int)starNetwork.get(e2));
        assertEquals(3, (int)starNetwork.get(e3));
        assertEquals(4, (int)starNetwork.get(e4));
    }
}
