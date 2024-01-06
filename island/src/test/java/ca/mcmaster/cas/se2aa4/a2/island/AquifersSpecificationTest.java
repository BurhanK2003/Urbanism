
package ca.mcmaster.cas.se2aa4.a2.island;

import static org.junit.jupiter.api.Assertions.*;

import ca.mcmaster.cas.se2aa4.a2.io.Structs;
import ca.mcmaster.cas.se2aa4.island.IslandBuilder;
import ca.mcmaster.cas.se2aa4.island.aquifers.AquifersSpecification;
import ca.mcmaster.cas.se2aa4.island.lakes.CircleLakes;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;



import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AquifersSpecificationTest {

    @Test
    public void testAquifersGeneration() {
        // generate lake mesh
        Structs.Mesh aquiferMesh = Structs.Mesh.newBuilder()
                .addVertices(Structs.Vertex.newBuilder().setX(0.0).setY(0.0).build())
                .addVertices(Structs.Vertex.newBuilder().setX(1.0).setY(0.0).build())
                .addVertices(Structs.Vertex.newBuilder().setX(0.0).setY(1.0).build())
                .addVertices(Structs.Vertex.newBuilder().setX(1.0).setY(1.0).build())
                .addSegments(Structs.Segment.newBuilder().setV1Idx(0).setV2Idx(1).build())
                .addSegments(Structs.Segment.newBuilder().setV1Idx(1).setV2Idx(2).build())
                .addSegments(Structs.Segment.newBuilder().setV1Idx(2).setV2Idx(3).build())
                .addSegments(Structs.Segment.newBuilder().setV1Idx(3).setV2Idx(0).build())
                .addPolygons(Structs.Polygon.newBuilder()
                        .addSegmentIdxs(0).addSegmentIdxs(1).addSegmentIdxs(2)
                        .addSegmentIdxs(3).build())
                .build();

        // verify the number of polygons

        assertEquals(aquiferMesh.getPolygonsList().size(), 1);

        // verify the number of blue polygons
        int actualNumBluePolygons = IslandBuilder.bluePolygons.size();
        assertEquals(0, actualNumBluePolygons);

        // verify the colors of the polygons
        List<Structs.Polygon> polygons = aquiferMesh.getPolygonsList();
        for (Structs.Polygon polygon : polygons) {
            if (IslandBuilder.greenPolygons.contains(polygon)) {
                assertTrue(polygon.getPropertiesList().contains(Structs.Property.newBuilder()
                        .setKey("rgb_color").setValue("51,153,51").build()));
            } else if (IslandBuilder.AquiferPolygons.contains(polygon)) {
                assertTrue(polygon.getPropertiesList().contains(Structs.Property.newBuilder()
                        .setKey("rgb_color").setValue("70,160,180").build()));
            }
        }
    }
}
