package ca.mcmaster.cas.se2aa4.island.shapespecification;

import ca.mcmaster.cas.se2aa4.a2.io.Structs;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Vertex;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Mesh;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Polygon;

import java.util.ArrayList;

import static ca.mcmaster.cas.se2aa4.island.IslandBuilder.bluePolygons;
import static ca.mcmaster.cas.se2aa4.island.IslandBuilder.greenPolygons;
import static ca.mcmaster.cas.se2aa4.island.IslandBuilder.lakePolygons;

public class Circle {
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
            if(radius < 500.0){

                greenPolygons.add(p);

            }
            //OCEAN (DARK BLUE)
            else{

                bluePolygons.add(p);
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
            } else if (lakePolygons.contains(p)) {
                type.add("lake");
                String RGB = 18 + "," + 52 + "," + 139;
                Structs.Property color = Structs.Property.newBuilder().setKey("rgb_color").setValue(RGB).build();
                tempList.add(Structs.Polygon.newBuilder(p).clearProperties().addProperties(color).build());
            }
        }
        return finalizeMesh(initMesh, tempList);
    }
    public Mesh finalizeMesh(Mesh tempMesh, ArrayList<Polygon> temp) {
        return Mesh.newBuilder().addAllVertices(tempMesh.getVerticesList()).addAllSegments(tempMesh.getSegmentsList()).addAllPolygons(temp).build();

    }

}
