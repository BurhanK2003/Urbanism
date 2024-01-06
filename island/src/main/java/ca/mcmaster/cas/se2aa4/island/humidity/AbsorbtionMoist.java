package ca.mcmaster.cas.se2aa4.island.humidity;

import ca.mcmaster.cas.se2aa4.a2.io.Structs;

import java.util.ArrayList;
import java.util.HashMap;

import static ca.mcmaster.cas.se2aa4.island.IslandBuilder.*;

public class AbsorbtionMoist {
    private final HashMap<Integer, Double> tempSoil = new HashMap<>();
    public Structs.Mesh AbsorbtionProfileMoist(Structs.Mesh aMesh) {
        Structs.Mesh initMesh = aMesh;
        AbsorbtionProfile absorb= new AbsorbtionProfile();

        double xcenter = 0;
        double ycenter = 0;

        double radius = 0;
        double Centerx = 0;
        double Centery = 0;

        for (Structs.Polygon poly : bluePolygons) {
            String RGB2 = 0 + "," + 0 + "," + 139;
            Structs.Property color2 = Structs.Property.newBuilder().setKey("rgb_color").setValue(RGB2).build();
            tempList.add(Structs.Polygon.newBuilder(poly).clearProperties().addProperties(color2).build());
        }
        for (Structs.Polygon poly : lakePolygons) {
            xcenter = aMesh.getVerticesList().get(poly.getCentroidIdx()).getX();
            ycenter = aMesh.getVerticesList().get(poly.getCentroidIdx()).getY();

            for (Structs.Polygon poly2 : greenPolygons) {
                Centerx = aMesh.getVerticesList().get(poly2.getCentroidIdx()).getX();
                Centery = aMesh.getVerticesList().get(poly2.getCentroidIdx()).getY();
                //checks radius of green polygon to the humidity polygon
                radius = Math.sqrt(Math.pow(Centerx - xcenter, 2) + Math.pow(Centery - ycenter, 2));
                //humidity value of green polygon is 600(the humidity of polygon closest to lake) and gets less depending on the radius
                double humidityVal = absorb.initialHumidityMoist - absorb.AbsorbtionRateMoist*radius;
                if(absorbtionMap.get(poly2)!=null){
                    double curretValue = absorbtionMap.get(poly2);
                    curretValue += humidityVal;
                    absorbtionMap.put(poly2, curretValue);
                }
                else if(absorbtionMap.get(poly2)==null){
                    absorbtionMap.put(poly2, humidityVal);
                }

                //set humidity value
            }
        }
        for (Structs.Polygon poly : AquiferPolygons) {
            //original polygon centroid coordinate
            xcenter = aMesh.getVerticesList().get(poly.getCentroidIdx()).getX();
            ycenter = aMesh.getVerticesList().get(poly.getCentroidIdx()).getY();

            for (Structs.Polygon poly2 : greenPolygons) {
                Centerx = aMesh.getVerticesList().get(poly2.getCentroidIdx()).getX();
                Centery = aMesh.getVerticesList().get(poly2.getCentroidIdx()).getY();
                //checks radius of green polygon to the humidity polygon
                radius = Math.sqrt(Math.pow(Centerx - xcenter, 2) + Math.pow(Centery - ycenter, 2));
                //humidity value of green polygon is 600(the humidity of polygon closest to lake) and gets less depending on the radius
                double humidityVal = 1000 - radius;
                if (absorbtionMap.get(poly2) != null) {
                    double curretValue = absorbtionMap.get(poly2);
                    curretValue += humidityVal;
                    absorbtionMap.put(poly2, curretValue);
                } else if (absorbtionMap.get(poly2) == null) {
                    absorbtionMap.put(poly2, humidityVal);
                }
                //set humidity value
            }
        }



        for (Integer i : allRiverSegs) {
            Structs.Segment s = initMesh.getSegments(i);
            Structs.Vertex v1 = initMesh.getVertices(s.getV1Idx());
            Structs.Vertex v2 = initMesh.getVertices(s.getV2Idx());

            double segCenterX = (v1.getX() + v2.getX())/2;
            double segCenterY = (v1.getY() + v2.getY())/2;
            for (Structs.Polygon poly2 : greenPolygons) {
                Centerx = aMesh.getVerticesList().get(poly2.getCentroidIdx()).getX();
                Centery = aMesh.getVerticesList().get(poly2.getCentroidIdx()).getY();
                //checks radius of green polygon to the humidity polygon
                radius = Math.sqrt(Math.pow(Centerx - segCenterX, 2) + Math.pow(Centery - segCenterY, 2));
                radiusList.add(radius);
                //humidity value of green polygon is 600(the humidity of polygon closest to lake) and gets less depending on the radius
                double humidityVal = 1000 - radius;
                if(absorbtionMap.get(poly2)!=null){
                    double curretValue = absorbtionMap.get(poly2);
                    curretValue += humidityVal;
                    absorbtionMap.put(poly2, curretValue);
                }
                else if(absorbtionMap.get(poly2)==null){
                    absorbtionMap.put(poly2, humidityVal);
                }
                //set humidity value
            }
        }

        for (Structs.Polygon polyAbsorb : absorbtionMap.keySet()) {
            for (Structs.Polygon polyOG : greenPolygons) {
                if (polyOG == polyAbsorb) {
                    int idx = greenPolygons.indexOf(polyOG);
                    tempSoil.put(idx, absorbtionMap.get(polyAbsorb));
                }
            }
        }

        for (Structs.Polygon poly2 : greenPolygons){
            double gradient=255-0.005*absorbtionMap.get(poly2);
            if (gradient < 0) {gradient = 0;}
            if (gradient > 255) {gradient = 255;}
            int gradient1 = (int)gradient;
            String RGB = 0 + "," + gradient1 + "," + 0;
            Structs.Property color = Structs.Property.newBuilder().setKey("rgb_color").setValue(RGB).build();
            Structs.Polygon storePoly = Structs.Polygon.newBuilder(poly2).clearProperties().addProperties(color).build();
            tempList.add(storePoly);
        }

        for (Structs.Polygon poly : AquiferPolygons){
            String RGB = 51 + "," + 153 + "," + 51;
            Structs.Property color = Structs.Property.newBuilder().setKey("rgb_color").setValue(RGB).build();
            tempList.add(Structs.Polygon.newBuilder(poly).clearProperties().addProperties(color).build());
        }


        for (Structs.Polygon poly : lakePolygons){
            String RGB = 70 + "," + 160 + "," + 180;
            Structs.Property color = Structs.Property.newBuilder().setKey("rgb_color").setValue(RGB).build();
            tempList.add(Structs.Polygon.newBuilder(poly).clearProperties().addProperties(color).build());
        }



        addToIsland(tempSoil);
        return finalizeMesh(initMesh, tempList);

    }

    public Structs.Mesh finalizeMesh(Structs.Mesh tempMesh, ArrayList<Structs.Polygon> temp) {
        return Structs.Mesh.newBuilder().addAllVertices(tempMesh.getVerticesList()).addAllSegments(tempMesh.getSegmentsList()).addAllPolygons(temp).build();

    }
    private void addToIsland(HashMap<Integer, Double> absorbMap) {
        for (Integer i : absorbMap.keySet()) {
            soilMap.put(i, absorbMap.get(i));
        }
    }
}
