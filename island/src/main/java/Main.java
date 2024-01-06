
import ca.mcmaster.cas.se2aa4.a2.io.MeshFactory;
import ca.mcmaster.cas.se2aa4.a2.io.Structs;


import ca.mcmaster.cas.se2aa4.island.IslandConfig;
import ca.mcmaster.cas.se2aa4.island.shapesconfiguration.ShapeConfiguration;

public class Main {

    public static void main(String[] args) throws Exception {
        ShapeConfiguration config = new ShapeConfiguration(args);
        System.out.println("Options: " + config.export());

        Structs.Mesh aMesh = new MeshFactory().read(config.input());
        String modeOption = config.export(ShapeConfiguration.MODE);
        String shapeOption = config.export(ShapeConfiguration.SHAPE);
        String LakeOption = config.export(ShapeConfiguration.LAKE);
        String ElevationOption = config.export(ShapeConfiguration.ALTITUDE);
        String SoilOption = config.export(ShapeConfiguration.SOIL);
        String riverOption = config.export(ShapeConfiguration.RIVER);
        String aquifersOption = config.export(ShapeConfiguration.AQUIFER);
        String biomesOption = config.export(ShapeConfiguration.BIOMES);
        String cityOption = config.export(ShapeConfiguration.CITY);
        String villageOption = config.export(ShapeConfiguration.VILLAGE);
        String hamletOption = config.export(ShapeConfiguration.HAMLET);
       /* String seedOption = config.export(ShapeConfiguration.SEED);*/
        IslandConfig Mesh = new IslandConfig();

        Structs.Mesh FinalMesh = Mesh.island_config(config, aMesh, modeOption, shapeOption, cityOption, villageOption, hamletOption, aquifersOption, LakeOption, ElevationOption, SoilOption, riverOption, biomesOption);
        new MeshFactory().write(FinalMesh, config.export(ShapeConfiguration.OUTPUT));



    }



}

