package ca.mcmaster.cas.se2aa4.island.shapesconfiguration;

import org.apache.commons.cli.*;
import java.util.HashMap;
import java.util.Map;



public class ShapeConfiguration {
    public static final String INPUT = "i";
    public static final String OUTPUT = "o";
    public static final String MODE = "mode";
    public static final String SHAPE = "shape";
    public static final String HELP = "help";
    public static final String LAKE = "lakes";
    public static final String ALTITUDE = "altitude";
    public static final String CITY= "city";
    public static final String VILLAGE= "village";
    public static final String HAMLET = "hamlet";
    public static final String SOIL = "soil";
    public static final String RIVER = "rivers";
    public static final String AQUIFER = "aquifers";
    public static final String BIOMES= "biomes";

    public static final String SEED = "seed";

    private CommandLine cli;

    public ShapeConfiguration(String[] args) {
        try {
            this.cli = parser().parse(options(), args);
            if (cli.hasOption(HELP)) {
                help();
            }
        } catch (ParseException pe) {
            throw new IllegalArgumentException(pe);
        }
    }

    private void help() {
        HelpFormatter formatter = new HelpFormatter();
        formatter.printHelp("java -jar island.jar", options());
        System.exit(0);
    }

    public Map<String, String> export() {
        Map<String, String> result = new HashMap<>();
        for(Option o: cli.getOptions()){
            System.out.println("Option: " + o.getOpt() + ", value: " + o.getValue(""));
            result.put(o.getOpt(), o.getValue(""));
        }
        return result;
    }

    public String export(String key) {
        return cli.getOptionValue(key);
    }

    private CommandLineParser parser() {
        return new DefaultParser();
    }
    public String input() {
        return this.cli.getOptionValue(INPUT);
    }



    private Options options() {
        Options options = new Options();
        options.addOption(new Option(INPUT, true, "Input file (SVG)"));
        options.addOption(new Option(OUTPUT, true, "Output file name"));
        options.addOption(new Option(MODE, true, "Mode (Regular or Lagoon)"));
        options.addOption(new Option(SHAPE, true, "Shape (Circle or Irregular)"));
        options.addOption(new Option(LAKE, true, "Lakes (number of lakes)"));
        options.addOption(new Option(ALTITUDE, true, "Elevation Profile (Volcanic or Dunes)"));
        options.addOption(new Option(SOIL, true, "Island Absorbtion Type (moist or dry)"));
        options.addOption(new Option(RIVER, true, "Rivers (number of rivers)"));
        options.addOption(new Option(AQUIFER, true, "Aquifers (number of aquifers)"));
        options.addOption(new Option(BIOMES, true, "Biomes (Arctic or Warm)"));
        options.addOption(new Option(CITY, true, "number of cities"));
        options.addOption(new Option(VILLAGE, true, "number of villages"));
        options.addOption(new Option(HAMLET, true, "number of hamlet"));
        /*options.addOption(new Option(SEED, true, "Seed"));*/


        // Global help
        options.addOption(new Option(HELP, false, "print help message"));
        return options;
    }

}
