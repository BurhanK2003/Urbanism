
import ca.mcmaster.cas.se2aa4.a2.pathfinder.graph.*;
import org.junit.jupiter.api.Test;
import java.util.*;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DijkstraSSPTest {
    @Test
    public void testFindShortestPath() {
        //create the nodes
        Nodes n1 = new Nodes("Chicago", 10);
        Nodes n2 = new Nodes("Houston", 20);
        Nodes n3 = new Nodes("New York City", 30);
        Nodes n4 = new Nodes("Los Angeles", 40);
        Nodes n5 = new Nodes("Miami", 50);

        // create the edges
        Edges e1 = new Edges(n1, n2, 5000);
        Edges e2 = new Edges(n1, n3, 2000);
        Edges e3 = new Edges(n2, n4, 4000);
        Edges e4 = new Edges(n3, n4, 1000);
        Edges e5 = new Edges(n3, n5, 3000);
        Edges e6 = new Edges(n4, n5, 2000);

        // create the graph
        GraphADT graph = new GraphADT();
        // add the nodes
        graph.addNodes(n1);
        graph.addNodes(n2);
        graph.addNodes(n3);
        graph.addNodes(n4);
        graph.addNodes(n5);
        // add the edges
        graph.addEdges(e1);
        graph.addEdges(e2);
        graph.addEdges(e3);
        graph.addEdges(e4);
        graph.addEdges(e5);
        graph.addEdges(e6);

        // create an instance
        PathFinder pathfinder = new DijkstraSSP();
        pathfinder.Graph(graph);

        // find the shortest path
        pathfinder.compute_SP(n1);
        List<Nodes> path = pathfinder.SP(n5);


        // check the result
        List<Nodes> expectedPath = new ArrayList<>();
        expectedPath.add(n1);
        expectedPath.add(n3);
        expectedPath.add(n5);
        assertEquals(expectedPath, path);
    }

}
