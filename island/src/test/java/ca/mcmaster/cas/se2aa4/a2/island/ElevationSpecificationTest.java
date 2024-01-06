package ca.mcmaster.cas.se2aa4.a2.island;

import ca.mcmaster.cas.se2aa4.a2.io.Structs;
import ca.mcmaster.cas.se2aa4.island.elevationspecification.ElevationSpecification;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static ca.mcmaster.cas.se2aa4.island.IslandBuilder.absorbtionMap;
import static ca.mcmaster.cas.se2aa4.island.IslandBuilder.gradesMap;
import static org.junit.jupiter.api.Assertions.*;

public class ElevationSpecificationTest {
    @Test
    public void testElevationSpecification() {
        // create a mesh to test on
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


        // create an instance of the class to test
        ElevationSpecification myClass = new ElevationSpecification();

        // call the method being tested
        Structs.Mesh resultMesh = myClass.ElevationProfileVolcanic(aMesh);

        // perform assertions to ensure the result is as expected
        assertNotNull(resultMesh);

        assertNotNull(gradesMap);


    }


}
