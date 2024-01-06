package ca.mcmaster.cas.se2aa4.a2.pathfinder.graph;



import java.util.*;

public class GraphADT {

    // a graph is nothing but a set of nodes and list of edges for the nodes
    private HashMap<Nodes, ArrayList<Edges>> edges;
    public GraphADT() {

        this.edges = new HashMap<>();

    }

    //adds the nodes
    public void addNodes(Nodes node) {

        edges.put(node, new ArrayList<>());

    }

    // adds the edges
    public void addEdges(Edges edge) {
        Nodes node1 = edge.getNode1();
        Nodes node2 = edge.getNode2();

        ArrayList<Edges> node1_Edges = edges.get(node1);
        node1_Edges.add(edge);

        ArrayList<Edges> node2_Edges = edges.get(node2);
        node2_Edges.add(edge);
    }

    // a method to access nodes
    public Set<Nodes> getNodes() {

        return edges.keySet();

    }

    // a method to access edges
    public List<Edges> getEdges(Nodes node){

        return edges.get(node);

    }


}
