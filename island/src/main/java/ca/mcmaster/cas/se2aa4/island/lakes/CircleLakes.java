package ca.mcmaster.cas.se2aa4.island.lakes;

import ca.mcmaster.cas.se2aa4.a2.io.Structs;

import java.util.ArrayList;
import java.util.Random;

import static ca.mcmaster.cas.se2aa4.island.IslandBuilder.*;


import ca.mcmaster.cas.se2aa4.island.shapespecification.Circle;
import ca.mcmaster.cas.se2aa4.island.IslandBuilder;

public class CircleLakes {
    public Structs.Mesh lake(Structs.Mesh aMesh,int numLakes) {

       Circle circle = new Circle();
       Structs.Mesh landAndLakeMesh = circle.generateIsland(aMesh);




        Random rand = new Random();
        int numGreenPolygons = IslandBuilder.greenPolygons.size();
        int numFilledPolygons = 0;

        while (numFilledPolygons < numLakes) {
            Structs.Polygon pol = IslandBuilder.greenPolygons.get(rand.nextInt(numGreenPolygons));

            if (!(pol.getNeighborIdxsList().stream()
                    .anyMatch(i -> IslandBuilder.bluePolygons.contains(aMesh.getPolygons(i))))) {
                IslandBuilder.lakePolygons.add(pol);
                IslandBuilder.greenPolygons.remove(pol);
                numFilledPolygons++;

                // randomly fill adjacent green polygons with sea blue color to make the gulf irregularly shaped
                int avgSize = 5;
                int size = avgSize + rand.nextInt(avgSize);

                for (int i = 0; i < size; i++) {
                    Structs.Polygon neighbor = aMesh.getPolygons(
                            pol.getNeighborIdxs(rand.nextInt(pol.getNeighborIdxsCount())));
                    if (!(neighbor.getNeighborIdxsList().stream()
                            .anyMatch(j -> IslandBuilder.bluePolygons.contains(aMesh.getPolygons(j))))) {

                        if (IslandBuilder.greenPolygons.contains(neighbor)) {
                            IslandBuilder.lakePolygons.add(neighbor);
                            IslandBuilder.greenPolygons.remove(neighbor);
                        }
                    }
                }
            }
        }


        for (Structs.Polygon p : landAndLakeMesh.getPolygonsList()) {
            if (greenPolygons.contains(p)) {

                String RGB = 51 + "," + 153 + "," + 51;
                Structs.Property color = Structs.Property.newBuilder().setKey("rgb_color").setValue(RGB).build();

                tempList.add(Structs.Polygon.newBuilder(p).clearProperties().addProperties(color).build());
            } else if (bluePolygons.contains(p)) {

                String RGB = 0 + "," + 0 + "," + 139;
                Structs.Property color = Structs.Property.newBuilder().setKey("rgb_color").setValue(RGB).build();
                tempList.add(Structs.Polygon.newBuilder(p).clearProperties().addProperties(color).build());
            } else if (lakePolygons.contains(p)) {

                String RGB = 70 + "," + 160 + "," + 180;
                Structs.Property color = Structs.Property.newBuilder().setKey("rgb_color").setValue(RGB).build();
                tempList.add(Structs.Polygon.newBuilder(p).clearProperties().addProperties(color).build());
            }
        }
        return finalizeMesh(landAndLakeMesh, tempList);
    }
    public Structs.Mesh finalizeMesh(Structs.Mesh tempMesh, ArrayList<Structs.Polygon> temp) {
        return Structs.Mesh.newBuilder().addAllVertices(tempMesh.getVerticesList()).addAllSegments(tempMesh.getSegmentsList()).addAllPolygons(temp).build();

    }





}