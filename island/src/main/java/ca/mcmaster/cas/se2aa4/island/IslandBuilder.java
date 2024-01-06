package ca.mcmaster.cas.se2aa4.island;
import ca.mcmaster.cas.se2aa4.a2.io.Structs;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public interface IslandBuilder {
    public List<Structs.Polygon> greenPolygons = new ArrayList<>();
    public List<Structs.Polygon> bluePolygons = new ArrayList<>();

    public List<Structs.Polygon> lakePolygons = new ArrayList<>();
    public List<Structs.Polygon> AquiferPolygons = new ArrayList<>();


    public List<Integer> allRiverSegs = new ArrayList<>();
    public ArrayList<Structs.Polygon> tempList = new ArrayList<>();
    public HashMap<Integer, Integer> gradesMap = new HashMap<>();
    public HashMap<Structs.Polygon, Double> absorbtionMap = new HashMap<>();
    public  List<Double> radiusList = new ArrayList<>();




    public HashMap<Integer, Double> soilMap = new HashMap<>();

    public HashMap<Integer, Double> elevationMap = new HashMap<>();
    List<Structs.Polygon> newGreen = new ArrayList<>();


}
