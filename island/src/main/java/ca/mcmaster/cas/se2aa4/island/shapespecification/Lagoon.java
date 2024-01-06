package ca.mcmaster.cas.se2aa4.island.shapespecification;

import ca.mcmaster.cas.se2aa4.a2.io.Structs;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Mesh;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Polygon;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Vertex;

import java.util.ArrayList;

import static ca.mcmaster.cas.se2aa4.island.IslandBuilder.*;

public class Lagoon {
    public Mesh generateIsland(Mesh mesh){

        double xcenter = 0;
        double ycenter = 0;

        Mesh initMesh = mesh;


        for(Vertex vertex: mesh.getVerticesList()){
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

        for(Polygon p: mesh.getPolygonsList()){

            Centerx = mesh.getVerticesList().get(p.getCentroidIdx()).getX();
            Centery = mesh.getVerticesList().get(p.getCentroidIdx()).getY();
            radius = Math.sqrt(Math.pow(Centerx - xcenter, 2) + Math.pow(Centery - ycenter, 2));

            //LAND (GREEN)
            if(radius < 500.0 && radius > 300){
                type.add("land");
                String RGB = 51 + "," + 153 + "," + 51;
                Structs.Property color = Structs.Property.newBuilder().setKey("rgb_color").setValue(RGB).build();

                tempList.add(Polygon.newBuilder(p).clearProperties().addProperties(color).build());

                greenPolygons.add(p);

            }

            //LAGOON (LIGHT BLUE)
            else if(radius < 300){
                type.add("lagoon");
                String RGB = 70 + "," + 160 + "," + 180;
                Structs.Property color = Structs.Property.newBuilder().setKey("rgb_color").setValue(RGB).build();
                tempList.add(Polygon.newBuilder(p).clearProperties().addProperties(color).build());
                lakePolygons.add(p);
            }

            //OCEAN (DARK BLUE)
            else{

                type.add("ocean");
                String RGB = 0 + "," + 0 + "," + 139;
                Structs.Property color = Structs.Property.newBuilder().setKey("rgb_color").setValue(RGB).build();
                tempList.add(Polygon.newBuilder(p).clearProperties().addProperties(color).build());
                bluePolygons.add(p);
            }

        }
        for(Polygon p: mesh.getPolygonsList()){
            if(!(type.get(mesh.getPolygonsList().indexOf(p)).equals("lagoon") || type.get(mesh.getPolygonsList().indexOf(p)).equals("ocean"))){
                for(int i: p.getNeighborIdxsList()){
                    if(type.get(i).equals("lagoon") || type.get(i).equals("ocean")){

                        type.set(mesh.getPolygonsList().indexOf(p), "beach");
                        String colorCode = 180 + "," + 156 + "," + 70;
                        Structs.Property color = Structs.Property.newBuilder().setKey("rgb_color").setValue(colorCode).build();
                        tempList.set(mesh.getPolygonsList().indexOf(p), Polygon.newBuilder(p).clearProperties().addProperties(color).build());
                        break;
                    }
                }
            }
        }

        return finalizeMesh(initMesh, tempList);
    }
    public Mesh finalizeMesh(Mesh tempMesh, ArrayList<Polygon> temp) {
        return Mesh.newBuilder().addAllVertices(tempMesh.getVerticesList()).addAllSegments(tempMesh.getSegmentsList()).addAllPolygons(temp).build();
    }

}
