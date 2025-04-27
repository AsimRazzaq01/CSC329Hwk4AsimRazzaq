package org.example;

import java.util.*;

/**
 * MyGraph Class
 * methods -> MyGraph, addVertex, addEdge, showGraph, calculateConnectedComponents
 */
public class MyGraph {

    // Member Variables
    List<Integer> vertices;                     // List of vertices
    Map<Integer, List<Edge>> adjacencyList;     // Map of vertices to edges

    // Default Constructor
    MyGraph(){
        this.vertices = new ArrayList<>();      // instance of ArrayList
        this.adjacencyList = new HashMap<>();   // instance of HashMap
    }

    // addVertex - add vertex as long as there is not an already existing one
    void addVertex(int v){
        if (!vertices.contains(v)) {
            vertices.add(v);
            // add empty adjacencyList and add new vertex
            adjacencyList.put(v, new ArrayList<>());
        }
    }

    // addEdge - check if vertex exists else add -> connect edges both directions
    void addEdge(int v1, int v2, int weight){
        // check if both vertices exist v1 & v2 because is a (undirected graph)
        if (!vertices.contains(v1)) {
            addVertex(v1);
        }
        if (!vertices.contains(v2)) {
            addVertex(v2);
        }

        // instance of new edge
        Edge edge = new Edge(v1, v2, weight);

        // Add the edge in both directions (undirected graph)
        adjacencyList.get(v1).add(edge);
        adjacencyList.get(v2).add(edge);
    }

    // method to print neatly for later steps
    public void showGraph() {
        for (int v : vertices) {
            System.out.print("Vertex " + v +": ");
            for (Edge edge : adjacencyList.get(v)) {
                //int neighbor = (edge.v1 == v) ? edge.v2 : edge.v1;
                //System.out.print(" - [ Vertex " + neighbor + ", (weight: " + edge.weight + ") ] ");
                System.out.printf(" (%d, %d, %d) " ,edge.v1, edge.v2, edge.weight);
            }
            System.out.println();
        }
    }


    // connected components
    public static int[] calculateConnectedComponents(MyGraph g) {
        // Initialize vertices
        List<Integer> vertices = g.vertices ;
        int numVertices = vertices.size();

        // Initialize numComponents to 0
        int numComponents = 0;

        // Initialize compMap - currently set to unvisited
        Map<Integer, Integer> compMap = new HashMap<>();
        for (int currVertex : vertices) {
            compMap.put(currVertex, -1);  // -1 means unvisited (for all vertices)
        }

        // Initialize bfsQueue
        Queue<Integer> bfsQueue = new LinkedList<>();

        // Loop over all vertices
        for (int currVertex : vertices) {
            if (compMap.get(currVertex) == -1) {    // If vertex is unvisited
                numComponents++;                    // Increment numComponents
                bfsQueue.add(currVertex);           // put currVertex in bfsQueue

                while (! bfsQueue.isEmpty()) {
                    int compV = bfsQueue.poll();
                    compMap.put(compV, numComponents);    // associates a vertex with a component number

                    // For each edge in compV's adjacency list
                    for (Edge e : g.adjacencyList.get(compV)) {
                        int destVertex = (e.v1 == compV) ? e.v2 : e.v1;

                        if (compMap.get(destVertex) == -1) {            // If unvisited
                            compMap.put(destVertex, numComponents);     // Mark as visited
                            bfsQueue.add(destVertex);
                        }
                    } // End inner For Loop
                } // End while Loop
            }
        } // End outer For Loop


        // Convert the map to an array in the same order as the vertices list
        int[] componentArray = new int[numVertices];
        for (int i = 0; i < numVertices; i++) {
            componentArray[i] = compMap.get(vertices.get(i));
        }

        return componentArray;
    }  // End calculateConnectedComponents method

} // End MyGraph class
