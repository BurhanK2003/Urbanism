package ca.mcmaster.cas.se2aa4.a2.pathfinder.graph;

public class Edges {

    // edges can have atributes

    private Nodes node_1;
    private Nodes node_2;
    private int distance;

    // constructor for them
    public Edges(Nodes node_1, Nodes node_2,int distance){
        this.node_1=node_1;
        this.node_2=node_2;
        this.distance=distance;

    }

    // getter method
    public Nodes getNode1() {
        return node_1;
    }

    public Nodes getNode2() {
        return node_2;
    }

    public int getDistance() {
        return distance;
    }


}

