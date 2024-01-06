package ca.mcmaster.cas.se2aa4.island.shapespecification;

import ca.mcmaster.cas.se2aa4.a2.io.Structs;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Mesh;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Polygon;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Vertex;
import ca.mcmaster.cas.se2aa4.island.Hull2;

import java.awt.geom.Path2D;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

import static ca.mcmaster.cas.se2aa4.island.IslandBuilder.*;

public class Irregular {
    private int numGulfs = 10;
    private int avgSize = 500;
    private int numPolygonInstances = 0;

    public Mesh generateIsland(Mesh mesh) {

        double xcenter = 0;
        double ycenter = 0;

        Mesh initMesh = mesh;

        for (Vertex vertex : mesh.getVerticesList()) {
            xcenter += vertex.getX();
            ycenter += vertex.getY();
        }
        xcenter = xcenter / mesh.getVerticesCount();
        ycenter = ycenter / mesh.getVerticesCount();


        double radius = 0;
        double Centerx = 0;
        double Centery = 0;


        ArrayList<Polygon> tempList = new ArrayList<>();
        ArrayList<String> type = new ArrayList<>();
        ArrayList<Integer> gulfSizes = new ArrayList<>();

        for (Polygon p : mesh.getPolygonsList()) {

            Centerx = mesh.getVerticesList().get(p.getCentroidIdx()).getX();
            Centery = mesh.getVerticesList().get(p.getCentroidIdx()).getY();
            radius = Math.sqrt(Math.pow(Centerx - xcenter, 2) + Math.pow(Centery - ycenter, 2));

            //LAND (GREEN)
            if (radius < 500.0) {

                greenPolygons.add(p);

            }
            //OCEAN (DARK BLUE)
            else {
                bluePolygons.add(p);
            }
        }


        //create gulfs from existing shape


        Random rand = new Random();
        int numGreenPolygons = greenPolygons.size();

        int numReplacedPolygons = 0;
        while (numPolygonInstances < numGulfs) {
            // randomly select a green polygon w/ at least one blue neighbor
            Structs.Polygon p = greenPolygons.get(rand.nextInt(greenPolygons.size()));
            if (p.getNeighborIdxsList().stream()
                    .anyMatch(i -> bluePolygons.contains(mesh.getPolygons(i)))) {
                // fill polygon with sea blue color
                Hull2 hull = new Hull2();
                for (Integer segmentIdx : p.getSegmentIdxsList()) {
                    hull.add(mesh.getSegments(segmentIdx), mesh);
                }
                Path2D path = new Path2D.Float();
                Iterator<Vertex> vertices = hull.iterator();
                Vertex current = vertices.next();
                path.moveTo(current.getX(), current.getY());
                while (vertices.hasNext()) {
                    current = vertices.next();
                    path.lineTo(current.getX(), current.getY());
                }
                path.closePath();


                // add polygon to the blue polygons list remove from the green polygons list
                bluePolygons.add(p);
                greenPolygons.remove(p);
                numPolygonInstances++;
                numReplacedPolygons++;

                // randomly fill adjacent green polygons with sea blue color to make the gulf irregularly shaped
                int size = avgSize + rand.nextInt(avgSize);
                for (int i = 0; i < size; i++) {
                    Structs.Polygon neighbor = mesh.getPolygons(
                            p.getNeighborIdxs(rand.nextInt(p.getNeighborIdxsCount())));
                    if (greenPolygons.contains(neighbor)) {
                        Hull2 neighborHull = new Hull2();
                        for (Integer neighborSegmentIdx : neighbor.getSegmentIdxsList()) {
                            neighborHull.add(mesh.getSegments(neighborSegmentIdx), mesh);
                        }
                        Path2D neighborPath = new Path2D.Float();
                        Iterator<Vertex> neighborVertices = neighborHull.iterator();
                        Vertex neighborCurrent = neighborVertices.next();
                        neighborPath.moveTo(neighborCurrent.getX(), neighborCurrent.getY());
                        while (neighborVertices.hasNext()) {
                            neighborCurrent = neighborVertices.next();
                            neighborPath.lineTo(neighborCurrent.getX(), neighborCurrent.getY());
                        }
                        neighborPath.closePath();

                        // add the neighbor polygon to the blue polygons list and remove it from the green polygons list
                        bluePolygons.add(neighbor);
                        greenPolygons.remove(neighbor);
                        numReplacedPolygons++;
                    }
                }
            }
        }


        for (Structs.Polygon p : mesh.getPolygonsList()) {
            if (greenPolygons.contains(p)) {
                type.add("land");
                String RGB = 51 + "," + 153 + "," + 51;
                Structs.Property color = Structs.Property.newBuilder().setKey("rgb_color").setValue(RGB).build();

                tempList.add(Structs.Polygon.newBuilder(p).clearProperties().addProperties(color).build());
            } else if (bluePolygons.contains(p)) {
                type.add("ocean");
                String RGB = 0 + "," + 0 + "," + 139;
                Structs.Property color = Structs.Property.newBuilder().setKey("rgb_color").setValue(RGB).build();
                tempList.add(Structs.Polygon.newBuilder(p).clearProperties().addProperties(color).build());
            }
        }
        return finalizeMesh(initMesh, tempList);
    }

    public Mesh finalizeMesh(Mesh tempMesh, ArrayList<Polygon> temp) {
        return Mesh.newBuilder().addAllVertices(tempMesh.getVerticesList()).addAllSegments(tempMesh.getSegmentsList()).addAllPolygons(temp).build();
    }

    public int getGulfs() {
        return numGulfs;
    }

    public void setGulfs(int newValue) {
        numGulfs = newValue;
    }

    public int getavgGulfSize() {
        return avgSize;
    }

    public void setavgGulfSize(int newValue) {
        avgSize = newValue;
    }

    public int getGulfInstances() {
        return numPolygonInstances;
    }

    public void setGulfInstances(int newValue) {
        numPolygonInstances = newValue;
    }
}