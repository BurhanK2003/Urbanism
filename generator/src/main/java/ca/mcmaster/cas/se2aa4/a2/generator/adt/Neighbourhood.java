package ca.mcmaster.cas.se2aa4.a2.generator.adt;

import ca.mcmaster.cas.se2aa4.a2.generator.PrecisionModel;
import ca.mcmaster.cas.se2aa4.a2.generator.geomfilters.ExtractNeighbours;
import org.locationtech.jts.geom.*;
import org.locationtech.jts.triangulate.DelaunayTriangulationBuilder;

import java.util.*;
import java.util.stream.Collectors;

public class Neighbourhood {

    private Map<Vertex, Polygon> registry;
    private Map<Polygon, Set<Polygon>> neighbors;

    public Neighbourhood(Set<Polygon> polygons) {
        this.registry = new HashMap<>();
        for(Polygon p: polygons){
            registry.put(p.centroid(), p);
        }
        this.neighbors = new HashMap<>();
        compute();
    }

    public Set<Polygon> neighbors(Polygon p) {
        return this.neighbors.getOrDefault(p, Set.of());
    }

    private void compute() {
        Set<Coordinate> sites = new HashSet<>();
        for(Vertex v: registry.keySet()){
            sites.add(new Coordinate(v.x(), v.y()));
        }
        DelaunayTriangulationBuilder builder = new DelaunayTriangulationBuilder();
        builder.setSites(sites);
        Geometry triangles = builder.getTriangles(PrecisionModel.FACTORY);
        ExtractNeighbours visitor = new ExtractNeighbours();
        triangles.apply(visitor);
        store(visitor.getLinks());
    }

    private void store(Map<Vertex, Set<Vertex>> links) {
        for(Vertex v: links.keySet()){
            Polygon current = registry.get(v);
            Set<Polygon> linked = links.get(v).stream().map(x -> registry.get(x)).collect(Collectors.toSet());
            this.neighbors.put(current, linked);
        }
    }

}
