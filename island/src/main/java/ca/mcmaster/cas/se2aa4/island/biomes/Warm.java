package ca.mcmaster.cas.se2aa4.island.biomes;

import ca.mcmaster.cas.se2aa4.a2.io.Structs;

public class Warm extends Whittaker{

    @Override
    public Structs.Mesh genBiomeMesh(Structs.Mesh aMesh) {
        return super.genBiomeMesh(aMesh);
    }

    @Override
    Structs.Polygon biome1(Structs.Polygon poly) {
        String RGB = 180 + "," + 215 + "," + 145;
        Structs.Property color = Structs.Property.newBuilder().setKey("rgb_color").setValue(RGB).build();
        Structs.Polygon montaneGrasslands = Structs.Polygon.newBuilder(poly).clearProperties().addProperties(color).build();
        return montaneGrasslands;
    }

    @Override
    Structs.Polygon biome2(Structs.Polygon poly) {
        String RGB = 100 + "," + 100 + "," + 20;
        Structs.Property color = Structs.Property.newBuilder().setKey("rgb_color").setValue(RGB).build();
        Structs.Polygon chaparral = Structs.Polygon.newBuilder(poly).clearProperties().addProperties(color).build();
        return chaparral;
    }

    @Override
    Structs.Polygon biome3(Structs.Polygon poly) {
        String RGB = 60 + "," + 210 + "," + 60;
        Structs.Property color = Structs.Property.newBuilder().setKey("rgb_color").setValue(RGB).build();
        Structs.Polygon rainforest = Structs.Polygon.newBuilder(poly).clearProperties().addProperties(color).build();
        return rainforest;
    }

    @Override
    Structs.Polygon biome4(Structs.Polygon poly) {
        String RGB = 255 + "," + 225 + "," + 140;
        Structs.Property color = Structs.Property.newBuilder().setKey("rgb_color").setValue(RGB).build();
        Structs.Polygon desert = Structs.Polygon.newBuilder(poly).clearProperties().addProperties(color).build();
        return desert;
    }
    @Override
    Structs.Polygon lake(Structs.Polygon poly) {
        String RGB = 70 + "," + 160 + "," + 180;
        Structs.Property color = Structs.Property.newBuilder().setKey("rgb_color").setValue(RGB).build();
        Structs.Polygon lake = Structs.Polygon.newBuilder(poly).clearProperties().addProperties(color).build();
        return lake;
    }
}
