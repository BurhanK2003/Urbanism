package ca.mcmaster.cas.se2aa4.a2.island;

import ca.mcmaster.cas.se2aa4.a2.io.Structs;
import ca.mcmaster.cas.se2aa4.island.humidity.AbsorbtionDry;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static ca.mcmaster.cas.se2aa4.island.IslandBuilder.absorbtionMap;
import static org.junit.jupiter.api.Assertions.*;

class AbsorbtionTest {

    @Test
    void testAbsorbtionProfileMoist() {

        Structs.Mesh aMesh = Structs.Mesh.newBuilder()
                .addVertices(Structs.Vertex.newBuilder().setX(0.0).setY(0.0).build())
                .addVertices(Structs.Vertex.newBuilder().setX(1.0).setY(0.0).build())
                .addVertices(Structs.Vertex.newBuilder().setX(0.0).setY(1.0).build())
                .addVertices(Structs.Vertex.newBuilder().setX(1.0).setY(1.0).build())
                .addPolygons(Structs.Polygon.newBuilder()
                        .addSegmentIdxs(0).addSegmentIdxs(1).addSegmentIdxs(2)
                        .setCentroidIdx(0).build())
                .build();

        AbsorbtionDry absorbtion = new AbsorbtionDry();

        // call the method to be tested
        Structs.Mesh resultMesh = absorbtion.AbsorbtionProfileDry(aMesh);

        // check that the result has the expected properties
        assertNotNull(resultMesh);
        assertNotNull(absorbtionMap);


    }

}
