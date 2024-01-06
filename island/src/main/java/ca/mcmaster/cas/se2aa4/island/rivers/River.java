package ca.mcmaster.cas.se2aa4.island.rivers;

import ca.mcmaster.cas.se2aa4.a2.io.Structs;
import ca.mcmaster.cas.se2aa4.island.elevationspecification.ElevationSpecification;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Segment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import static ca.mcmaster.cas.se2aa4.island.IslandBuilder.allRiverSegs;

public class River {
    private final int numRivers;
    private final List<Segment> segments;
    private final ElevationSpecification elev;
    private final Random rd;
    private final Structs.Mesh aMesh;
    private final HashMap<Integer, Integer> elevationHMap;
    private final List<Integer> riverSegments;
    private final List<Integer> startingPoints;

    public River(Structs.Mesh aMesh, int numRivers) {
        this.aMesh = aMesh;
        this.numRivers = numRivers;
        this.segments = aMesh.getSegmentsList();
        this.elev = new ElevationSpecification();
        this.rd = new Random();
        this.elevationHMap = elev.getElevation();
        this.riverSegments = new ArrayList<>();
        this.startingPoints = new ArrayList<>();
    }


    public Structs.Mesh buildRivers() {


        Structs.Mesh tempMesh = aMesh;
        List<Segment> riverSegs = new ArrayList<>();

        for (int i = 0; i < numRivers; i++) {
            boolean check = false;
            int startVIdx = -1;
            while (!check) {
                startVIdx = rd.nextInt(elevationHMap.size()); // add element to make sure elevation at startVIdx isn't 0
                if (!startingPoints.contains(startVIdx)) {
                    check = checkStart(check,startVIdx);
                }
            }
            List<Integer> currentRiver= new ArrayList<>();
            buildRiverSegs(startVIdx, currentRiver);
        }
        String RGB = 70 + "," + 160 + "," + 180;
        Structs.Property color = Structs.Property.newBuilder().setKey("rgb_color").setValue(RGB).build();
        for (Segment s: tempMesh.getSegmentsList()) {
            if (riverSegments.contains(tempMesh.getSegmentsList().indexOf(s))) {
                String line = riverThickness(s, riverSegs);
                Structs.Property width = Structs.Property.newBuilder().setKey("thickness").setValue(line).build();
                riverSegs.add(s.newBuilder(s).clearProperties().addProperties(color).addProperties(width).build());
            } else {
                riverSegs.add(s);
            }
        }
        for (Integer i : riverSegments) {
            if (!allRiverSegs.contains(i)) {
                allRiverSegs.add(i);
            }
        }
        return Structs.Mesh.newBuilder().addAllVertices(tempMesh.getVerticesList()).addAllSegments(riverSegs).addAllPolygons(tempMesh.getPolygonsList()).build();
    }


    private boolean checkStart(boolean check, int startVIdx) {
        int startEVal = getElevationVal(startVIdx);
        if (startEVal == 0) {
            return check = false;
        }
        List<Segment> connectedSegs = new ArrayList<>();
        for (Segment s : segments) {
            int v1Idx = s.getV1Idx();
            int v2Idx = s.getV2Idx();
            if (v1Idx == startVIdx || v2Idx == startVIdx) {
                connectedSegs.add(aMesh.getSegments(segments.indexOf(s)));
            }
        }
        for (Segment s : connectedSegs) {
            int v1Idx = s.getV1Idx();
            int v2Idx = s.getV2Idx();
            if (v1Idx == startVIdx) {
                int eVal = getElevationVal(v2Idx);
                check = checkLowest(startEVal, eVal);
            } else if (v2Idx == startVIdx) {
                int eVal = getElevationVal(v1Idx);
                check = checkLowest(startEVal, eVal);
            }
            if (check) {return check;}
        }
        return check;
    }

    private boolean checkLowest(int elevationVal1, int elevationVal2) {
        if (elevationVal1 >= elevationVal2) {
            return true;
        } else {
            return false;
        }
    }

    private Integer compareVElev(int v1Idx, int v2Idx) {
        int elevationVal1 = getElevationVal(v1Idx);
        int elevationVal2 = getElevationVal(v2Idx);
        if (elevationVal1 >= elevationVal2) {
            return v2Idx;
        } else {
            return v1Idx;
        }
    }

    private List<Segment> genConnectedSegs(int startVIdx) {
        List<Segment> connectedSegs = new ArrayList<>();
        for (Segment s : segments) {
            int v1Idx = s.getV1Idx();
            int v2Idx = s.getV2Idx();
            if (v1Idx == startVIdx || v2Idx == startVIdx) {
                connectedSegs.add(aMesh.getSegments(segments.indexOf(s)));
            }
        }
        return connectedSegs;
    }

    private Integer getNextVIdx(int startVIdx, List<Integer> traversedVIdx) {
        int smallerVIdx = startVIdx;
        int nextVIdx = startVIdx;
        List<Segment> connectedSegs = genConnectedSegs(startVIdx);
        for (Segment s : connectedSegs) {
            int v1Idx = s.getV1Idx();
            int v2Idx = s.getV2Idx();
            if (v1Idx == startVIdx && !traversedVIdx.contains(v2Idx)) {
                nextVIdx = compareVElev(smallerVIdx, v2Idx);
            } else if (v2Idx == startVIdx && !traversedVIdx.contains(v1Idx)) {
                nextVIdx = compareVElev(smallerVIdx, v1Idx);
            }
            smallerVIdx = nextVIdx;
        }
        return smallerVIdx;
    }

    private List<Integer> buildRiverSegs(int startVIdx, List<Integer> currentRiver) {
        int nextVIdx = getNextVIdx(startVIdx, currentRiver);
        int elevationStartVal = getElevationVal(startVIdx);
        if ((elevationStartVal == 0) || (nextVIdx == startVIdx)) {
            return riverSegments;
        } else {
            for (Segment s: segments) {
                int v1Idx = s.getV1Idx();
                int v2Idx = s.getV2Idx();
                if ((v1Idx == startVIdx && v2Idx == nextVIdx) || (v1Idx == nextVIdx && v2Idx == startVIdx) ) {
                    startingPoints.add(v1Idx);
                    startingPoints.add(v2Idx);
                    riverSegments.add(segments.indexOf(s));
                    if (v1Idx == startVIdx) {
                        currentRiver.add(v1Idx);
                    }
                    if (v2Idx == startVIdx) {
                        currentRiver.add(v2Idx);
                    }
                }
            }
            buildRiverSegs(nextVIdx, currentRiver);
        }
        return riverSegments;
    }

    private Integer getElevationVal(int idx) {
        if (elevationHMap.get(idx) == null) {
            return 0;
        } else {
            return elevationHMap.get(idx);
        }
    }

    private String riverThickness(Segment containsSeg, List<Segment> riverS) {
        int count = 3;
        for (Segment s: riverS) {
             if (s == containsSeg) {
                 count+=3;
             }
        }
        return Integer.toString(count);
    }
}
