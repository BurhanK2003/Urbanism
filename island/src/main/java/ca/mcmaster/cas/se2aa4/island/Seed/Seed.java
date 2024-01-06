package ca.mcmaster.cas.se2aa4.island.Seed;

import ca.mcmaster.cas.se2aa4.a2.io.MeshFactory;
import ca.mcmaster.cas.se2aa4.a2.io.Structs;

import java.io.IOException;

public class Seed {

    public void generate_seed(Structs.Mesh aMesh, long seed) throws IOException {

        String seed_contain = "img/"+ String.valueOf(seed)+".mesh";
        new MeshFactory().write(aMesh,seed_contain);


    }

    public Structs.Mesh getMesh(long seed) throws IOException {
        String seed_contain = "img/"+ String.valueOf(seed)+".mesh";
        System.out.println(seed_contain);
        return new MeshFactory().read(seed_contain);

    }

}
