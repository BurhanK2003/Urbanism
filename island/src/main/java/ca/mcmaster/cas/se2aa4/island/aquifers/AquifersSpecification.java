package ca.mcmaster.cas.se2aa4.island.aquifers;

import ca.mcmaster.cas.se2aa4.a2.io.Structs;
import ca.mcmaster.cas.se2aa4.island.IslandBuilder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import static ca.mcmaster.cas.se2aa4.island.IslandBuilder.*;
import static ca.mcmaster.cas.se2aa4.island.IslandBuilder.tempList;

public class AquifersSpecification {

        public Structs.Mesh Aquifers(Structs.Mesh aMesh, int numAquifers){
            Structs.Mesh LandAquiferMesh = aMesh;
            Random random = new Random();
            int numGreenPolygons = IslandBuilder.greenPolygons.size();
            int numFilledPolygons = 0;
            while (numFilledPolygons < numAquifers) {
                Structs.Polygon pol = IslandBuilder.greenPolygons.get(random.nextInt(numGreenPolygons));

                if (!(pol.getNeighborIdxsList().stream()
                        .anyMatch(i -> IslandBuilder.bluePolygons.contains(aMesh.getPolygons(i))))) {
                    IslandBuilder.AquiferPolygons.add(pol);
                    IslandBuilder.greenPolygons.remove(pol);
                    numFilledPolygons++;
                }

            }





            for (Structs.Polygon p : greenPolygons) {
                String RGB = 51 + "," + 153 + "," + 51;
                Structs.Property color = Structs.Property.newBuilder().setKey("rgb_color").setValue(RGB).build();
                tempList.add(Structs.Polygon.newBuilder(p).clearProperties().addProperties(color).build());
            }
            for (Structs.Polygon p : bluePolygons) {
                String RGB = 0 + "," + 0 + "," + 139;
                Structs.Property color = Structs.Property.newBuilder().setKey("rgb_color").setValue(RGB).build();
                tempList.add(Structs.Polygon.newBuilder(p).clearProperties().addProperties(color).build());
            }
            for (Structs.Polygon p : lakePolygons) {
                String RGB = 70 + "," + 160 + "," + 180;
                Structs.Property color = Structs.Property.newBuilder().setKey("rgb_color").setValue(RGB).build();
                tempList.add(Structs.Polygon.newBuilder(p).clearProperties().addProperties(color).build());
            }
            for (Structs.Polygon p : AquiferPolygons) {
                String RGB = 51 + "," + 153 + "," + 51;
                Structs.Property color = Structs.Property.newBuilder().setKey("rgb_color").setValue(RGB).build();
                tempList.add(Structs.Polygon.newBuilder(p).clearProperties().addProperties(color).build());

            }
            return finalizeMesh(LandAquiferMesh, tempList);
        }
    public Structs.Mesh finalizeMesh(Structs.Mesh tempMesh, ArrayList<Structs.Polygon> temp) {
        return Structs.Mesh.newBuilder().addAllVertices(tempMesh.getVerticesList()).addAllSegments(tempMesh.getSegmentsList()).addAllPolygons(temp).build();

    }


}
