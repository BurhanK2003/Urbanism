package ca.mcmaster.cas.se2aa4.island.biomes;

import ca.mcmaster.cas.se2aa4.a2.io.Structs;
import ca.mcmaster.cas.se2aa4.island.elevationspecification.ElevationSpecification;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import static ca.mcmaster.cas.se2aa4.island.IslandBuilder.*;

public abstract class Whittaker {
    HashMap<Integer, Integer> whitElevationMap = new HashMap<>();

    public Structs.Mesh genBiomeMesh(Structs.Mesh aMesh) {
        Structs.Mesh tempMesh = aMesh;
        List<Structs.Polygon> polygons = aMesh.getPolygonsList();
        List<Structs.Polygon> newPolygons = new ArrayList<>();
        List<Integer> elevRange = getRange(elevationMap);
        List<Integer> absorbRange = getRange(soilMap);
        for (Structs.Polygon p: polygons) {
            if (greenPolygons.contains(p)) {
                if (soilMap.keySet().contains(greenPolygons.indexOf(p)) && elevationMap.keySet().contains(greenPolygons.indexOf(p))) {
                    List<Double> coords = getMapCoords(greenPolygons.indexOf(p));
                    int soil = (int) Math.round(coords.get(1));
                    int elev = (int) Math.round(coords.get(0));
                    if (elevRange.get(2) < elev && elev <elevRange.get(0) && soil < absorbRange.get(0) && soil > absorbRange.get(2)) {
                        p = biome1(p);
                    } else if (elevRange.get(2) < elev && elev <elevRange.get(0) && soil < absorbRange.get(2) && soil > absorbRange.get(1)) {
                        p = biome2(p);
                    } else if (elevRange.get(1) < elev && elev <elevRange.get(2) && soil < absorbRange.get(0) && soil > absorbRange.get(2)) {
                        p = biome3(p);
                    } else if (elevRange.get(1) < elev && elev <elevRange.get(2) && soil < absorbRange.get(2) && soil > absorbRange.get(1)) {
                        p = biome4(p);
                    }
                    newPolygons.add(p);
                }
            } else if (lakePolygons.contains(p)) {
                p=lake(p);
                newPolygons.add(p);
            } else if (AquiferPolygons.contains(p)) {
                Structs.Polygon aquifer = biome2(p);
                newPolygons.add(aquifer);
            } else if (bluePolygons.contains(p)) {
                newPolygons.add(p);
            }
        }
        return finalMesh(tempMesh, newPolygons);
    }
    private List<Double> getMapCoords(int i) {
        double e = elevationMap.get(i);
        double a = soilMap.get(i);
        List<Double> coords = new ArrayList<>();
        coords.add(e);
        coords.add(a);
        return coords;
    }

    private List<Integer> getRange(HashMap<Integer,Double> eitherList) {
        int peak = 0;
        int valley = 500000;
        for (Double i : eitherList.values()) {
            int elev = (int) Math.round(i);
            if (elev > peak) {
                peak = elev;
            }
            if (elev < valley) {
                valley = elev;
            }
        }
        int mid = (peak + valley)/2;
        List<Integer> parameters = new ArrayList<>();
        parameters.add(peak);
        parameters.add(valley);
        parameters.add(mid);
        return parameters;
    }

    private Structs.Mesh finalMesh(Structs.Mesh aMesh, List<Structs.Polygon> polygons) {
        Structs.Mesh finalMesh = Structs.Mesh.newBuilder().addAllSegments(aMesh.getSegmentsList()).addAllVertices(aMesh.getVerticesList()).addAllPolygons(polygons).build();
        return finalMesh;
    }


    abstract Structs.Polygon biome1(Structs.Polygon poly);
    abstract Structs.Polygon biome2(Structs.Polygon poly);
    abstract Structs.Polygon biome3(Structs.Polygon poly);
    abstract Structs.Polygon biome4(Structs.Polygon poly);
    abstract Structs.Polygon lake(Structs.Polygon poly);
}
