# Assignment A4: 

- Burhanuddin [kharodab@mcmaster.ca]

### Installation instructions

Run:
```
mosser@azrael A4 % mvn install
```
After installation, you'll find an application named `generator.jar` in the `generator` directory, and a file named `visualizer.jar` in the `visualizer` one.

## How to run the product

To generate a regular, grid mesh, run: 
```
java -jar generator/generator.jar -k grid -h 1080 -w 1920 -s 20 -o grid.mesh -d
java -jar visualizer/visualizer.jar -i grid.mesh -o grid.svg
```

To generate an irregular mesh, run: 
```
java -jar generator/generator.jar -k irregular -h 1080 -w 1920 -p 1000 -r 5 -o ireg.mesh -d
```

Help with generating island:
```
java -jar island/island.jar -help

```


To generate the star network on 
```
java -jar island/island.jar -i ireg.mesh -o ireg2.mesh -mode regular -shape Circle -lakes 3 -altitude volcanic -city 15 -village 10 -hamlet 10 -rivers 0 -soil moist

```

To visualize islands, run:
```
java -jar visualizer/visualizer.jar -i ireg2.mesh -o ireg_dbg2.svg
```

### Definition of Done & Additional Notes

As long as each feature compiles through maven without damaging the previous features, it will be marked as done



