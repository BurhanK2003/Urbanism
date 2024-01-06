package ca.mcmaster.cas.se2aa4.island.biomes;

import ca.mcmaster.cas.se2aa4.a2.io.Structs;

public class BiomeSpecification {

    public Structs.Mesh biomeSpecification(String biomesOption, Structs.Mesh aMesh) {
        if (biomesOption.equals("arctic")) {
            Arctic arcticMesh = new Arctic();
            Structs.Mesh newBiomeMesh = arcticMesh.genBiomeMesh(aMesh);
            return newBiomeMesh;
        } else { // warm climate is the default Whittaker climate
            Warm warmMesh = new Warm();
            Structs.Mesh newBiomeMesh = warmMesh.genBiomeMesh(aMesh);
            return newBiomeMesh;
        }
    }
}
