/*

package ca.mcmaster.cas.se2aa4.a2.island;

import ca.mcmaster.cas.se2aa4.a2.io.Structs;
import ca.mcmaster.cas.se2aa4.island.IslandBuilder;
import ca.mcmaster.cas.se2aa4.island.lakes.LagoonLakes;
import ca.mcmaster.cas.se2aa4.island.shapespecification.Circle;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class CircleTest {
    @Test
    public void testCircle() {
        // Create a simple mesh with one green polygon
        Structs.Mesh mesh = Structs.Mesh.newBuilder()
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


        // Run the lake generation algorithm
        Circle circle = new Circle();
        Structs.Mesh island = circle.generateIsland(mesh);

        // Check that the lake was generated correctly
        List<Structs.Polygon> lakePolygons = IslandBuilder.lakePolygons;
        assertEquals(1, lakePolygons.size());

        // Check that the colors of the polygons were set correctly
        for (Structs.Polygon p : island.getPolygonsList()) {
            if (IslandBuilder.greenPolygons.contains(p)) {
                assertEquals("51,153,51", getColor(p));
            } else if (IslandBuilder.bluePolygons.contains(p)) {
                assertEquals("0,0,139", getColor(p));
            }
        }
    }

    private String getColor(Structs.Polygon polygon) {
        return polygon.getPropertiesList().stream()
                .filter(prop -> prop.getKey().equals("rgb_color"))
                .map(Structs.Property::getValue)
                .findFirst()
                .orElse(null);
    }
}
*/
