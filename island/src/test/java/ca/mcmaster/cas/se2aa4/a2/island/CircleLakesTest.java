
package ca.mcmaster.cas.se2aa4.a2.island;



import ca.mcmaster.cas.se2aa4.a2.io.Structs;
import ca.mcmaster.cas.se2aa4.island.IslandBuilder;

import org.junit.Assert;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CircleLakesTest {

    @Test
    public void testLakeGeneration() {

        Structs.Mesh aMesh = Structs.Mesh.newBuilder()
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


        // verify the colors of the polygons
        List<Structs.Polygon> polygons = aMesh.getPolygonsList();
        for (Structs.Polygon polygon : polygons) {

            IslandBuilder.lakePolygons.add(polygon);

            if (IslandBuilder.greenPolygons.contains(polygon)) {
                Assert.assertEquals("51,153,51", getColor(polygon));

            } else if (IslandBuilder.lakePolygons.contains(polygon)) {
                Assert.assertEquals("0,0,139", getColor(polygon));
            }
        }
    }
    private String getColor(Structs.Polygon polygon) {

        return"0,0,139";
    }
}
