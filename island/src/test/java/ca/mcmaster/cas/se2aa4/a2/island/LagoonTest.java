package ca.mcmaster.cas.se2aa4.a2.island;

import ca.mcmaster.cas.se2aa4.a2.io.Structs;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Mesh;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Polygon;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Vertex;

import ca.mcmaster.cas.se2aa4.island.shapespecification.Lagoon;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


public class LagoonTest {

    @Test
    public void testLagoonIsland(){
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

        Lagoon lagoon = new Lagoon();
        Mesh resultMesh = lagoon.generateIsland(mesh);

        // Verify that the output mesh is not null
        assertNotNull(resultMesh);

        // Verify that the output mesh has the same number of vertices and polygons as the input mesh
        assertEquals(mesh.getVerticesCount(), resultMesh.getVerticesCount());
        assertEquals(mesh.getPolygonsCount(), resultMesh.getPolygonsCount());

        // Verify that the output mesh has the same vertex coordinates as the input mesh
        for (int i = 0; i < mesh.getVerticesCount(); i++) {
            assertEquals(mesh.getVertices(i), resultMesh.getVertices(i));
        }

        // Verify that the output mesh has the expected polygon colors based on radius
        for (int i = 0; i < mesh.getPolygonsCount(); i++) {
            Polygon inputPoly = mesh.getPolygons(i);
            Polygon resultPoly = resultMesh.getPolygons(i);
            double radius = Math.sqrt(Math.pow(mesh.getVertices(inputPoly.getCentroidIdx()).getX() - 0.5, 2) + Math.pow(mesh.getVertices(inputPoly.getCentroidIdx()).getY() - 0.5, 2));
            if (radius < 300) {
                assertEquals("70,160,180", resultPoly.getProperties(0).getValue());
                assertEquals("70,160,180", resultPoly.getProperties(0).getValue());
            } else if (radius < 500) {
                assertEquals("51,153,51", resultPoly.getProperties(0).getValue());
                assertEquals("51,153,51", resultPoly.getProperties(0).getValue());
            } else {
                assertEquals("0,0,139", resultPoly.getProperties(0).getValue());
                assertEquals("0,0,139", resultPoly.getProperties(0).getValue());
            }
        }

    }

}
