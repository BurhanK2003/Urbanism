# Assignment A4:

- Burhanuddin [kharodab@mcmaster.ca]


### Definition of Done & Additional Notes

As long as each feature compiles through maven without damaging the previous features, it will be marked as done


### Rationale For PathFinder
```
This pathFinder library provides a module to create a graph network and uses that for determining the shortest path between any two graph nodes. Moreover, it is extendable to allow for other algorithms. 
This module includes a DijkstraSSP algorithm class that uses Dijkstra's algorithm to determine the shortest path.
```


### Justification for Extending PathFinder Library
```
There are several advantages to expanding the library by adding a new algorithm. 
Currently, this library creates a graph with nodes and edges that have attributes and computes a shortest path.
For some usage circumstances, the present algorithm in the library might not be the most effective. 
We You might be able to increase the performance of the library and make it more beneficial for a wider variety of applications by putting a new algorithm into action.

Moreover, we can take care of certain use cases or demands that the standard methods are unable to accommodate. 
If the library, for instance, only allows locating the shortest path across a graph, we can also develop an algorithm that can also locate the longest path or the one with the fewest edges. 
Also, adding a DFS or BFS algorithm to the library to find the closest cities is a simple way to expand its functionality (like Google Maps).
```
