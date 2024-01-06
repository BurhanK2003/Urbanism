package ca.mcmaster.cas.se2aa4.island.biomes;

import ca.mcmaster.cas.se2aa4.a2.io.Structs;

import java.util.ArrayList;
import java.util.List;

public class Arctic extends Whittaker {

    @Override
    public Structs.Mesh genBiomeMesh(Structs.Mesh aMesh) {
        return super.genBiomeMesh(aMesh);
    }

    @Override
    Structs.Polygon biome1(Structs.Polygon poly) {
        String RGB = 200 + "," + 255 + "," + 225;
        Structs.Property color = Structs.Property.newBuilder().setKey("rgb_color").setValue(RGB).build();
        Structs.Polygon arcticForest = Structs.Polygon.newBuilder(poly).clearProperties().addProperties(color).build();
        return arcticForest;
    }

    @Override
    Structs.Polygon biome2(Structs.Polygon poly) {
        String RGB = 255 + "," + 255 + "," + 200;
        Structs.Property color = Structs.Property.newBuilder().setKey("rgb_color").setValue(RGB).build();
        Structs.Polygon mountainTundra = Structs.Polygon.newBuilder(poly).clearProperties().addProperties(color).build();
        return mountainTundra;
    }

    @Override
    Structs.Polygon biome3(Structs.Polygon poly) {
        String RGB = 100 + "," + 140 + "," + 70;
        Structs.Property color = Structs.Property.newBuilder().setKey("rgb_color").setValue(RGB).build();
        Structs.Polygon wetlandTundra = Structs.Polygon.newBuilder(poly).clearProperties().addProperties(color).build();
        return wetlandTundra;
    }

    @Override
    Structs.Polygon biome4(Structs.Polygon poly) {
        String RGB = 225 + "," + 215 + "," + 185;
        Structs.Property color = Structs.Property.newBuilder().setKey("rgb_color").setValue(RGB).build();
        Structs.Polygon desertTundra = Structs.Polygon.newBuilder(poly).clearProperties().addProperties(color).build();
        return desertTundra;
    }

    @Override
    Structs.Polygon lake(Structs.Polygon poly) {
        String RGB = 70 + "," + 160 + "," + 180;
        Structs.Property color = Structs.Property.newBuilder().setKey("rgb_color").setValue(RGB).build();
        Structs.Polygon lake = Structs.Polygon.newBuilder(poly).clearProperties().addProperties(color).build();
        return lake;
    }
}
