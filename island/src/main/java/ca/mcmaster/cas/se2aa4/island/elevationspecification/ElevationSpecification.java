package ca.mcmaster.cas.se2aa4.island.elevationspecification;

import ca.mcmaster.cas.se2aa4.a2.io.Structs;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Vertex;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import static ca.mcmaster.cas.se2aa4.island.IslandBuilder.*;
import static ca.mcmaster.cas.se2aa4.island.IslandBuilder.gradesMap;

public class ElevationSpecification {

    //to know if its blue polygons, the vertex is null
    //we will make a gradient for the elevation

    public int high = 1000;
    public int medium = 100;
    public int low = 10;




    private final List<Integer> elevationVal = new ArrayList<>();
    private static HashMap<Integer, Integer> retElevation = new HashMap<>();
    private static HashMap<Integer, Double> polygonElev = new HashMap<>();

    public Structs.Mesh ElevationProfileVolcanic(Structs.Mesh aMesh) {
        Structs.Mesh elevationMesh = aMesh;

        Random random = new Random();



        int count = 0;
        // List<Integer> elevationVal = new ArrayList<>();

        for (Structs.Polygon p : greenPolygons) {
            newGreen.add(p);
        }
        for (Structs.Vertex vertices : aMesh.getVerticesList()) {
            elevationVal.add(null);
        }
        double xcenter = 0;
        double ycenter = 0;


        for (Vertex vertex : aMesh.getVerticesList()) {
            xcenter += vertex.getX();
            ycenter += vertex.getY();
        }
        xcenter = xcenter / aMesh.getVerticesCount();
        ycenter = ycenter / aMesh.getVerticesCount();

        double radius = 0;
        double Centerx = 0;
        double Centery = 0;



            for (Structs.Polygon poly : aMesh.getPolygonsList()) {
                Centerx = aMesh.getVerticesList().get(poly.getCentroidIdx()).getX();
                Centery = aMesh.getVerticesList().get(poly.getCentroidIdx()).getY();
                radius = Math.sqrt(Math.pow(Centerx - xcenter, 2) + Math.pow(Centery - ycenter, 2));
                double gradient = 500001 - 100*radius;
               /* System.out.println("============================================="+radius);
                System.out.println("============================================="+gradient);*/
                int gradient1 = (int) gradient;
                int max = gradient1 + 500;
                int min = gradient1 - 500;

                for (Integer segIdx : poly.getSegmentIdxsList()) {
                    Structs.Segment seg = aMesh.getSegments(segIdx);
                    int v1Idx = seg.getV1Idx();
                    int v2Idx = seg.getV2Idx();
                    int v1Height = random.nextInt(max-min) + min;
                    int v2Height = random.nextInt(max-min) + min;

                    if (!(greenPolygons.contains(poly))) {
                        if (elevationVal.get(v1Idx) == null) {
                            gradesMap.put(v1Idx, 0);
                        }
                        if (elevationVal.get(v2Idx) == null) {
                            gradesMap.put(v2Idx, 0);
                            // set elevationval[v1idx] to ---
                        }
                        String RGB = 0 + "," + 0 + "," + 139;
                        Structs.Property color = Structs.Property.newBuilder().setKey("rgb_color").setValue(RGB).build();
                        tempList.add(Structs.Polygon.newBuilder(poly).clearProperties().addProperties(color).build());
                    } else {
                        if (elevationVal.get(v1Idx) == null) {
                            gradesMap.put(v1Idx, v1Height);
                            /*System.out.println("v1height "+v1Height);*/
                        }
                        if (elevationVal.get(v2Idx) == null) {
                            gradesMap.put(v2Idx, v2Height);
                            /*System.out.println("v1height "+v2Height);*/
                        }

                        double gradient2 = 255 - 0.00008 * gradient1;
                        int gradient3 = (int) gradient2;
                        String RGB = 0 + "," + gradient3 + "," + 0;
                        Structs.Property color = Structs.Property.newBuilder().setKey("rgb_color").setValue(RGB).build();
                        int idx = greenPolygons.indexOf(poly);
                        Structs.Polygon newPoly = Structs.Polygon.newBuilder(poly).clearProperties().addProperties(color).build();
                        /*System.out.println(idx+"    "+gradient);*/
                        // newGreen.set(idx, newPoly);
                        polygonElev.put(idx, gradient);
                        tempList.add(Structs.Polygon.newBuilder(poly).clearProperties().addProperties(color).build());
                    }
                }

            }
        /*System.out.println("HERE ---------- "+gradesMap.size());
        System.out.println("get elevation "+retElevation.size());*/
       /* System.out.println(aMesh.getVerticesList().size());*/

        for (Integer key : gradesMap.keySet()) {
            retElevation.put(key, gradesMap.get(key));
        }
        /*System.out.println("get elevation "+retElevation.size());*/
        addToIsland(polygonElev);
        return finalizeMesh(elevationMesh, tempList);
    }

    public Structs.Mesh ElevationProfileDunes(Structs.Mesh aMesh) {

        Structs.Mesh elevationMesh = aMesh;

        Random random = new Random();
        ArrayList<Structs.Polygon> tempList = new ArrayList<>();

        int count = 0;
        List<Integer> elevationVal = new ArrayList<>();
        for (Structs.Vertex vertices : aMesh.getVerticesList()) {
            elevationVal.add(null);
        }
        double xcenter = 0;
        double ycenter = 0;


        for (Vertex vertex : aMesh.getVerticesList()) {
            xcenter += vertex.getX();
            ycenter += vertex.getY();
        }
        xcenter = xcenter / aMesh.getVerticesCount();
        ycenter = ycenter / aMesh.getVerticesCount();

        double radius = 0;
        double Centerx = 0;
        double Centery = 0;

        HashMap<Integer, Integer> gradesMap = new HashMap<>();
        for (Structs.Polygon poly : aMesh.getPolygonsList()) {
            Centerx = aMesh.getVerticesList().get(poly.getCentroidIdx()).getX();
            Centery = aMesh.getVerticesList().get(poly.getCentroidIdx()).getY();
            radius = Math.sqrt(Math.pow(Centerx - xcenter, 2) + Math.pow(Centery - ycenter, 2));

            if (radius <125) {
                for (Integer segIdx : poly.getSegmentIdxsList()) {
                    Structs.Segment seg = aMesh.getSegments(segIdx);
                    int v1Idx = seg.getV1Idx();
                    int v2Idx = seg.getV2Idx();
                    int v1Height = random.nextInt(medium - 10) + 11;
                    int v2Height = random.nextInt(medium - 10) + 11;
                    if (greenPolygons.contains(poly)) {
                        if (elevationVal.get(v1Idx) == null) {
                            gradesMap.put(v1Idx, v1Height);
                        }
                        if (elevationVal.get(v2Idx) == null) {
                            gradesMap.put(v2Idx, v2Height);
                            // set elevationval[v1idx] to ---
                        }
                        String RGB = 255 + "," + 102 + "," + 0;
                        Structs.Property color = Structs.Property.newBuilder().setKey("rgb_color").setValue(RGB).build();
                        tempList.add(Structs.Polygon.newBuilder(poly).clearProperties().addProperties(color).build());
                    }
                }


            } else if (radius < 250&&radius>125) {
                for (Integer segIdx : poly.getSegmentIdxsList()) {
                    Structs.Segment seg = aMesh.getSegments(segIdx);
                    int v1Idx = seg.getV1Idx();
                    int v2Idx = seg.getV2Idx();
                    int v1Height = random.nextInt(low - 1) + 1;
                    int v2Height = random.nextInt(low - 1) + 1;
                    if (greenPolygons.contains(poly)) {
                        if (elevationVal.get(v1Idx) == null) {
                            gradesMap.put(v1Idx, v1Height);
                        }
                        if (elevationVal.get(v2Idx) == null) {
                            gradesMap.put(v2Idx, v2Height);
                            // set elevationval[v1idx] to ---
                        }
                        String RGB = 255 + "," + 255 + "," + 0;
                        Structs.Property color = Structs.Property.newBuilder().setKey("rgb_color").setValue(RGB).build();
                        tempList.add(Structs.Polygon.newBuilder(poly).clearProperties().addProperties(color).build());

                    }
                }

            }
            else if (radius <375&& radius>250) {
                for (Integer segIdx : poly.getSegmentIdxsList()) {
                    Structs.Segment seg = aMesh.getSegments(segIdx);
                    int v1Idx = seg.getV1Idx();
                    int v2Idx = seg.getV2Idx();
                    int v1Height = random.nextInt(medium - 10) + 11;
                    int v2Height = random.nextInt(medium - 10) + 11;
                    if (greenPolygons.contains(poly)) {
                        if (elevationVal.get(v1Idx) == null) {
                            gradesMap.put(v1Idx, v1Height);
                        }
                        if (elevationVal.get(v2Idx) == null) {
                            gradesMap.put(v2Idx, v2Height);
                            // set elevationval[v1idx] to ---
                        }
                        String RGB = 255 + "," + 102 + "," + 0;
                        Structs.Property color = Structs.Property.newBuilder().setKey("rgb_color").setValue(RGB).build();
                        tempList.add(Structs.Polygon.newBuilder(poly).clearProperties().addProperties(color).build());
                    }
                }
            }
            else if (radius < 500&&radius>375) {
                for (Integer segIdx : poly.getSegmentIdxsList()) {
                    Structs.Segment seg = aMesh.getSegments(segIdx);
                    int v1Idx = seg.getV1Idx();
                    int v2Idx = seg.getV2Idx();
                    int v1Height = random.nextInt(low - 1) + 1;
                    int v2Height = random.nextInt(low - 1) + 1;
                    if (greenPolygons.contains(poly)) {
                        if (elevationVal.get(v1Idx) == null) {
                            gradesMap.put(v1Idx, v1Height);
                        }
                        if (elevationVal.get(v2Idx) == null) {
                            gradesMap.put(v2Idx, v2Height);
                            // set elevationval[v1idx] to ---
                        }
                        String RGB = 255 + "," + 255 + "," + 0;
                        Structs.Property color = Structs.Property.newBuilder().setKey("rgb_color").setValue(RGB).build();
                        tempList.add(Structs.Polygon.newBuilder(poly).clearProperties().addProperties(color).build());

                    }
                }

            }


        }

        for (Integer key : gradesMap.keySet()) {
            retElevation.put(key, gradesMap.get(key));
        }
        addToIsland(polygonElev);

        return finalizeMesh(elevationMesh, tempList);
    }
    public Structs.Mesh finalizeMesh(Structs.Mesh tempMesh, ArrayList<Structs.Polygon> temp) {
        return Structs.Mesh.newBuilder().addAllVertices(tempMesh.getVerticesList()).addAllSegments(tempMesh.getSegmentsList()).addAllPolygons(temp).build();

    }

    public HashMap<Integer, Integer> getElevation() {
        /*System.out.println("get elevation "+retElevation.size()+"!!!");*/
        return retElevation;
    }

    private void addToIsland(HashMap<Integer, Double> elevMap) {
        for (Integer i : elevMap.keySet()) {
            elevationMap.put(i, elevMap.get(i));
        }
    }

}
