package ca.mcmaster.cas.se2aa4.island.starnetworks;

import ca.mcmaster.cas.se2aa4.a2.io.Structs;
import ca.mcmaster.cas.se2aa4.a2.pathfinder.graph.*;

import java.util.*;

import static ca.mcmaster.cas.se2aa4.island.IslandBuilder.greenPolygons;

public class AddCities {
    private ArrayList<Nodes> nodes;
    private Map<Edges, Integer> edges;
    private Map<Nodes, Integer> land_node;
    private Map<Nodes, Integer> cities;
    private Map<Nodes, Integer> village;
    private Map<Nodes, Integer> hamlet;
    private Map<Nodes, Integer> node_map;


    public AddCities() {
        this.nodes = new ArrayList<>();
        this.edges = new HashMap<>();
        this.cities = new HashMap<>();
        this.village = new HashMap<>();
        this.hamlet = new HashMap<>();
        this.land_node = new HashMap<>();
        this.node_map = new HashMap<>();

    }

    public Structs.Mesh map_nodes(Structs.Mesh aMesh, int num_cities, int num_villages, int num_hamlets) {
        // I call the mesh, initialize the graph and some maps and ArrayLists
        Structs.Mesh tempMesh = aMesh;
        GraphADT graph = new GraphADT();
        Random rand = new Random();
        ArrayList<Nodes> special_node = new ArrayList<>();
        Map<Nodes, Integer> node_map1 = new HashMap<>();
        ArrayList<Integer> check = new ArrayList<>();
        ArrayList<Integer> vertices = new ArrayList<>();
        //I initialize the most central hubNode
        Nodes hubNode = null;
        int c = 0;

        //This loop basically goes over all the green polygons and add the indecies to a list
        for (Structs.Polygon pol : greenPolygons)
            for (Integer segIdx : pol.getSegmentIdxsList()) {
                Structs.Segment seg = tempMesh.getSegments(segIdx);
                int v1Idx = seg.getV1Idx();
                int v2Idx = seg.getV2Idx();
                if (!vertices.contains(v1Idx)) {
                    vertices.add(v1Idx);
                }
                if (!vertices.contains(v2Idx)) {
                    vertices.add(v2Idx);
                }
            }

        // here I add a check condition to make sure I don't make duplicate nodes
        for (int i = 0; i < vertices.size(); i++) {
            check.add(null);
        }

        //I initialize the counts
        int city_count = 0, village_count = 0, hamlet_count = 0;

        //loop that goes over all the vertex indecies and creates the cities, villages and hamlets
        for (int i = 0; i < vertices.size(); i++) {
            // a condition to randomly generate the cities
            boolean bool;
            if (i % 6 == 0) {
                bool = true;
            } else {
                bool = false;
            }

            Nodes node = null;
            if (bool == true) {
                if (city_count < num_cities) {
                    // node that is a city
                    if (check.get(i) == null) {
                        int population = rand.nextInt(10000000);
                        node = new Nodes("City " + (city_count + 1), population);
                        special_node.add(node);
                        cities.put(node, vertices.get(i));
                        check.set(i, 1);
                        city_count++;
                    }

                } else if (village_count < num_villages) {
                    // node that is a village
                    if (check.get(i) == null) {
                        int population = rand.nextInt(100000);
                        node = new Nodes("Village " + (village_count + 1), population);
                        if (c == 0) {
                            hubNode = node;

                            c++;
                        }
                        special_node.add(node);
                        village.put(node, vertices.get(i));
                        check.set(i, 1);
                        village_count++;
                    }

                } else if (hamlet_count < num_hamlets) {
                    // node that is a hamlet
                    if (check.get(i) == null) {
                        int population = rand.nextInt(100);
                        node = new Nodes("Hamlet " + (hamlet_count + 1), population);

                        special_node.add(node);
                        hamlet.put(node, vertices.get(i));
                        check.set(i, 1);
                        hamlet_count++;
                    }
                } else {
                    // just a regular node
                    if (check.get(i) == null) {
                        int population = 0;
                        node = new Nodes("Node " + nodes.size(), population);

                        land_node.put(node, vertices.get(i));
                        check.set(i, 1);
                    }
                }
            } else {
                // just a regular node
                if (check.get(i) == null) {
                    int population = 0;
                    node = new Nodes("Node " + nodes.size(), population);

                    land_node.put(node, vertices.get(i));
                    check.set(i, 1);
                }
            }
            //add nodes to the relevant places
            graph.addNodes(node);
            nodes.add(node);
            node_map.put(node, vertices.get(i));
            node_map1.put(node, vertices.get(i));

        }
        // create the edges
        add_edges(tempMesh, graph, node_map1);
        // get the final set of star edges
        star star_network = new star();
        Map<Edges, Integer> final_edge = star_network.createStarNetwork(hubNode, graph, special_node, edges);
        //get the proper colours for the edges and the nodes
        ArrayList<Structs.Segment> proper1 = colour_segment(tempMesh, final_edge);
        ArrayList<Structs.Vertex> proper = colour_vertices(tempMesh);
        //return the reconstructed and enriched mesh
        return Structs.Mesh.newBuilder().addAllVertices(proper).addAllSegments(proper1).addAllPolygons(tempMesh.getPolygonsList()).build();

    }

    public ArrayList<Structs.Segment> colour_segment(Structs.Mesh tempMesh, Map<Edges, Integer> finaledge) {
        // decide for a red path and colour all the segments that are in the edge map
        String RGB = 225 + "," + 0 + "," + 0;
        Structs.Property color = Structs.Property.newBuilder().setKey("rgb_color").setValue(RGB).build();
        ArrayList<Structs.Segment> specialseg = new ArrayList<>();
        for (Structs.Segment segment : tempMesh.getSegmentsList()) {
            if (finaledge.containsValue(tempMesh.getSegmentsList().indexOf(segment))) {
                int thickness = 2;
                String line = String.valueOf(thickness);
                Structs.Property width = Structs.Property.newBuilder().setKey("thickness").setValue(line).build();
                specialseg.add(segment.newBuilder(segment).clearProperties().addProperties(color).addProperties(width).build());
            } else {
                specialseg.add(segment);
            }
        }
        return specialseg;
    }

    public ArrayList<Structs.Vertex> colour_vertices(Structs.Mesh tempMesh) {
        // decide for different colour for all the vertices depending on whether they are cities, villages or hamlet
        Random rand = new Random();
        String RGB = 139 + "," + 0 + "," + 0;
        Structs.Property color = Structs.Property.newBuilder().setKey("rgb_color").setValue(RGB).build();
        String RGB2 = 255 + "," + 165 + "," + 0;
        Structs.Property color1 = Structs.Property.newBuilder().setKey("rgb_color").setValue(RGB2).build();
        String RGB3 = 255 + "," + 255 + "," + 180;
        Structs.Property color2 = Structs.Property.newBuilder().setKey("rgb_color").setValue(RGB3).build();
        String RGB4 = 0 + "," + 0 + "," + 0;
        Structs.Property color3 = Structs.Property.newBuilder().setKey("rgb_color").setValue(RGB4).build();
        ArrayList<Structs.Vertex> specialvertex = new ArrayList<>();
        for (Structs.Vertex vertex : tempMesh.getVerticesList()) {
            if (cities.containsValue(tempMesh.getVerticesList().indexOf(vertex))) {
                int thickness = rand.nextInt(5) + 15;
                String line = String.valueOf(thickness);
                Structs.Property width = Structs.Property.newBuilder().setKey("thickness").setValue(line).build();
                specialvertex.add(vertex.newBuilder(vertex).clearProperties().addProperties(color).addProperties(width).build());
            } else if (village.containsValue(tempMesh.getVerticesList().indexOf(vertex))) {
                int thickness = rand.nextInt(5) + 10;
                String line = String.valueOf(thickness);
                Structs.Property width = Structs.Property.newBuilder().setKey("thickness").setValue(line).build();
                specialvertex.add(vertex.newBuilder(vertex).clearProperties().addProperties(color1).addProperties(width).build());
            } else if (hamlet.containsValue(tempMesh.getVerticesList().indexOf(vertex))) {
                int thickness = rand.nextInt(5) + 5;
                String line = String.valueOf(thickness);
                Structs.Property width = Structs.Property.newBuilder().setKey("thickness").setValue(line).build();
                specialvertex.add(vertex.newBuilder(vertex).clearProperties().addProperties(color2).addProperties(width).build());
            } else if (land_node.containsValue(tempMesh.getVerticesList().indexOf(vertex))) {
                String line = "0";
                Structs.Property width = Structs.Property.newBuilder().setKey("thickness").setValue(line).build();
                specialvertex.add(vertex.newBuilder(vertex).clearProperties().addProperties(color3).addProperties(width).build());
            } else {
                specialvertex.add(vertex);
            }
        }
        return specialvertex;
    }

    public void add_edges(Structs.Mesh tempMesh, GraphADT graph, Map<Nodes, Integer> node_map) {
        //add all of the segment indecies to an arraylist
        ArrayList<Integer> segment = new ArrayList<>();
        for (Structs.Polygon pol : greenPolygons) {
            for (Integer segIdx : pol.getSegmentIdxsList()) {
                // make sure no duplicates are there
                if (!segment.contains(segIdx)) {
                    segment.add(segIdx);
                }
            }
        }

        // go through all of the segments, extract the vertices and make the edges
        for (int i = 0; i < segment.size(); i++) {
            Structs.Segment seg = tempMesh.getSegments(segment.get(i));
            int v1Idx = seg.getV1Idx();
            int v2Idx = seg.getV2Idx();
            for (Map.Entry<Nodes, Integer> node1 : node_map.entrySet()) {
                int value = node1.getValue();
                Nodes node_1 = node1.getKey();
                // check if the vertex that the node is mapped to, is the same as the segment vertex
                if (v1Idx == value) {
                    for (Map.Entry<Nodes, Integer> node2 : node_map.entrySet()) {
                        int value2 = node2.getValue();
                        Nodes node_2 = node2.getKey();
                        // check if the vertex that the node is mapped to, is the same as the segment vertex
                        if (v2Idx == value2) {
                            //if it reches here, that means that those nodes share an edge
                            Structs.Vertex v1 = tempMesh.getVertices(v1Idx);
                            int v1x = (int) v1.getX();
                            int v1y = (int) v1.getY();
                            Structs.Vertex v2 = tempMesh.getVertices(v2Idx);
                            int v2x = (int) v2.getX();
                            int v2y = (int) v2.getY();
                            int distance = (int) Math.sqrt(Math.pow(Math.abs(v1x - v2x), 2) + Math.pow(Math.abs(v1y - v2y), 2)) * 100;
                            Edges e1 = new Edges(node_1, node_2, distance);
                            Edges e2 = new Edges(node_2, node_1, distance);
                            //I add the edge to the graph and map it to the segment
                            graph.addEdges(e1);
                            graph.addEdges(e2);
                            edges.put(e1, segment.get(i));
                        }
                    }
                }
            }
        }
    }
}

