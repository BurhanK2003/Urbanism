package ca.mcmaster.cas.se2aa4.island;

import ca.mcmaster.cas.se2aa4.a2.io.Structs;
import ca.mcmaster.cas.se2aa4.island.Seed.Seed;
import ca.mcmaster.cas.se2aa4.island.aquifers.AquifersSpecification;
import ca.mcmaster.cas.se2aa4.island.biomes.BiomeSpecification;
import ca.mcmaster.cas.se2aa4.island.elevationspecification.ElevationSpecification;
import ca.mcmaster.cas.se2aa4.island.humidity.AbsorbtionDry;
import ca.mcmaster.cas.se2aa4.island.humidity.AbsorbtionMoist;
import ca.mcmaster.cas.se2aa4.island.lakes.CircleLakes;
import ca.mcmaster.cas.se2aa4.island.lakes.IrregularLakes;
import ca.mcmaster.cas.se2aa4.island.lakes.LagoonLakes;
import ca.mcmaster.cas.se2aa4.island.rivers.River;
import ca.mcmaster.cas.se2aa4.island.shapesconfiguration.ShapeConfiguration;
import ca.mcmaster.cas.se2aa4.island.shapespecification.Circle;
import ca.mcmaster.cas.se2aa4.island.shapespecification.Irregular;
import ca.mcmaster.cas.se2aa4.island.shapespecification.Lagoon;
import ca.mcmaster.cas.se2aa4.island.starnetworks.AddCities;

import static ca.mcmaster.cas.se2aa4.island.IslandBuilder.lakePolygons;

public class IslandConfig {

    public Structs.Mesh island_config(ShapeConfiguration config, Structs.Mesh aMesh,String modeOption, String shapeOption,String cityOption,String villageOption,String hamletOption, String aquifersOption, String LakeOption,String ElevationOption, String SoilOption, String riverOption, String biomesOption) {


        System.out.println("Shape option: " + shapeOption);


        if (modeOption.equals("lagoon") && shapeOption.equals("none")) {
            Lagoon lagoon = new Lagoon();
            Structs.Mesh islandMesh1 = lagoon.generateIsland(aMesh);
            return nodeChoice(islandMesh1, cityOption,villageOption,hamletOption);
        }
        else if (modeOption.equals("regular")) {
            if(shapeOption.equals("Circle")){
                Circle circle = new Circle();
                Structs.Mesh islandMesh = circle.generateIsland(aMesh);
                return nodeChoice(AbsorbtionChoice(riverChoice(elevationChoice(ElevationOption,circlelakeChoice(islandMesh,LakeOption)),riverOption),SoilOption), cityOption,villageOption,hamletOption);
            }
/*            else if(shapeOption.equals("Irregular")){
                Irregular irreg = new Irregular();
                Structs.Mesh islandMesh = irreg.generateIsland(aMesh);
                return biomeChoice(AbsorbtionChoice(riverChoice(elevationChoice(ElevationOption,irreglakeChoice(islandMesh,LakeOption)),riverOption),SoilOption),biomesOption);
            }*/
        } else if (shapeOption == null) {
            System.out.println("Shape option null");

        } else {
            System.out.println("No such command");

        }

        return null;
    }
    public Structs.Mesh circlelakeChoice(Structs.Mesh islandMesh, String LakeOption){
        int numLake = Integer.parseInt(LakeOption);


            CircleLakes Lake = new CircleLakes();
            Structs.Mesh lakeMesh = Lake.lake(islandMesh, numLake);

            System.out.println("Number of Lake Polygons" + lakePolygons.size());
            return lakeMesh;
    }
    public Structs.Mesh irreglakeChoice(Structs.Mesh islandMesh, String LakeOption){
        int numLake = Integer.parseInt(LakeOption);


        IrregularLakes Lake = new IrregularLakes();
        Structs.Mesh lakeMesh = Lake.lake(islandMesh, numLake);

        System.out.println("Number of Lake Polygons" + lakePolygons.size());
        return lakeMesh;


    }


    public Structs.Mesh elevationChoice(String ElevationOption, Structs.Mesh lakeMesh) {
    if (ElevationOption.equals("volcanic")) {
        ElevationSpecification elevation = new ElevationSpecification();
        Structs.Mesh ElevationMesh = elevation.ElevationProfileVolcanic(lakeMesh);
        return ElevationMesh;
    } else if (ElevationOption.equals("dunes")) {
        ElevationSpecification elevation = new ElevationSpecification();
        Structs.Mesh ElevationMesh = elevation.ElevationProfileDunes(lakeMesh);
        return ElevationMesh;
    }
    return elevationChoice(ElevationOption,lakeMesh);
    }

    public Structs.Mesh riverChoice(Structs.Mesh ElevationMesh, String riverOption){
        int numRiver = Integer.parseInt(riverOption);

        River rivers = new River(ElevationMesh, numRiver);
        Structs.Mesh riverMesh = rivers.buildRivers();
        return riverMesh;
    }
    public Structs.Mesh nodeChoice(Structs.Mesh ElevationMesh, String cityOption,String villageOption,String hamletOption){
        int numCity = Integer.parseInt(cityOption);
        int numVillage = Integer.parseInt(villageOption);
        int numHamlet = Integer.parseInt(hamletOption);
        AddCities nodes  = new AddCities();
        Structs.Mesh nodesMesh = nodes.map_nodes(ElevationMesh, numCity, numVillage, numHamlet);
        return nodesMesh;
    }


public Structs.Mesh aquiferChoice(Structs.Mesh riverMesh,String aquifersOption){
    int numaquifer = Integer.parseInt(aquifersOption);


    AquifersSpecification aquifer = new AquifersSpecification();
    Structs.Mesh aquiferMesh = aquifer.Aquifers(riverMesh, numaquifer);

    return aquiferMesh;

}

public Structs.Mesh AbsorbtionChoice(Structs.Mesh aquiferMesh,String SoilOption) {
    if (SoilOption.equals("moist")) {
        AbsorbtionMoist absorbtionMoist = new AbsorbtionMoist();
        Structs.Mesh AbsorbtionMesh = absorbtionMoist.AbsorbtionProfileMoist(aquiferMesh);
        return AbsorbtionMesh;
    } else if (SoilOption.equals("dry")) {
        AbsorbtionDry absorbtionMoist = new AbsorbtionDry();
        Structs.Mesh AbsorbtionMesh = absorbtionMoist.AbsorbtionProfileDry(aquiferMesh);
        return AbsorbtionMesh;

    }
    return AbsorbtionChoice(aquiferMesh, SoilOption);
}
/*
public Structs.Mesh biomeChoice(Structs.Mesh AbsorbtionMesh,String biomesOption){
    BiomeSpecification biome = new BiomeSpecification();
    Structs.Mesh biomeMesh = biome.biomeSpecification(biomesOption, AbsorbtionMesh);
    return biomeMesh;

}*/







    }

