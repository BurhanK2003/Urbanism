package ca.mcmaster.cas.se2aa4.a2.pathfinder.graph;

public class Nodes {

    //nodes can have attributes
    private String city_name;
    private double population;

    // construct them
    public Nodes(String city_name,double elevation) {
        this.city_name=city_name;
        this.population=elevation;
    }


}
