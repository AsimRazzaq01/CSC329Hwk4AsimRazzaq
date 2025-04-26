package org.example;

import java.util.*;

import static org.example.MyGraph.calculateConnectedComponents;

/**
 * @author asim razzaq
 * Main Class
 * getMinDistVertex method
 * shortestPath method
 * getMinFrontierEdge method
 * minimumSpanningTree method
 * printGraphEdges method
 */
public class Main {

    public static int getMinDistVertex(List<Integer> unvisitedList, int[] dist) {
        int minNeighborVertex = unvisitedList.get(0);
        int minNeighborDist = dist[minNeighborVertex];

        for (int v : unvisitedList) {
            if (dist[v] < minNeighborDist) {
                minNeighborDist = dist[v];
                minNeighborVertex = v;
            }
        }
        return minNeighborVertex;
    } // End getMinDistVertex method

    public static void shortestPath(MyGraph g, int startingVertex) {
        int maxVertex = g.vertices.size();
        int[] dist = new int[maxVertex ];
        int[] prev = new int[maxVertex ];
        boolean[] visited = new boolean[maxVertex ];

        // Create unvisited list and add all vertices
        List<Integer> unvisitedList = new ArrayList<>(g.vertices);

        // Initialize distances
        Arrays.fill(dist, Integer.MAX_VALUE);
        Arrays.fill(prev, -1);
        Arrays.fill(visited, false);

        // Set starting vertex distance to 0
        dist[startingVertex] = 0;


        // While unvisitedList is not empty
        while (!unvisitedList.isEmpty()) {
            int currV = getMinDistVertex(unvisitedList, dist);
            unvisitedList.remove(Integer.valueOf(currV));
            visited[currV] = true;

            // For all unvisited neighbors of currV
            for (Edge e : g.adjacencyList.get(currV)) {
                int neighbor = (e.v1 == currV) ? e.v2 : e.v1;
                if (!visited[neighbor]) {
                    int possibleDist = dist[currV] + e.weight;
                    if (possibleDist < dist[neighbor]) {
                        dist[neighbor] = possibleDist;
                        prev[neighbor] = currV;
                    }
                }
            }
        }

        // Print results
        System.out.println("Shortest Path Data");
        System.out.println("Starting Vertex : " + startingVertex);

        System.out.println("\nVertex Dist");
        for (int v : g.vertices) {
            System.out.printf(" %2d , %2d\n", v, dist[v]);
        }

        System.out.println("\nVertex Previous");
        for (int v : g.vertices) {
            System.out.printf(" %2d , %2d\n", v, prev[v]);
        }
    } // End shortestPath method




    public static Edge getMinFrontierEdge(MyGraph g, boolean[] visited) {
        Edge minEdge = null;
        int minWeight = Integer.MAX_VALUE;

        // Check all vertices in the graph
        for (int i = 0; i < g.vertices.size(); i++) {
            // Only consider visited vertices
            if (visited[i]) {
                int v = g.vertices.get(i);
                // Check all edges of this vertex
                for (Edge e : g.adjacencyList.get(v)) {
                    // Get the other vertex
                    int otherV = (e.v1 == v) ? e.v2 : e.v1;
                    int otherIndex = g.vertices.indexOf(otherV);

                    // Check if this is a frontier edge (one visited, one not)
                    if (visited[i] ^ visited[otherIndex]) {
                        // Check if it's the minimum weight frontier edge found so far
                        if (e.weight < minWeight) {
                            minEdge = e;
                            minWeight = e.weight;
                        }
                    }
                }
            }
        }
        return minEdge;
    } // End getMinFrontierEdge method





    public static MyGraph minimumSpanningTree(MyGraph g, int startingVertex) {
        // Graph MST
        MyGraph MST = new MyGraph();
        // Visited Array of Boolean -> set elements to false
        boolean[] visited = new boolean[g.vertices.size()];

        // Add all vertices to MST first (with no edges)
        for (int v : g.vertices) {
            MST.addVertex(v);
        }

        // pick starting vertex -> exception arg
        int startIndex = g.vertices.indexOf(startingVertex);
        if (startIndex == -1) {
            throw new IllegalArgumentException("Starting vertex not found in graph.");
        }

        // Set visited [startVertex] to true
        visited[startIndex] = true;

        // Continue until all vertices are visited
        while (true) {
            Edge minEdge = getMinFrontierEdge(g, visited);

            // If no more frontier edges, we're done
            if (minEdge == null) {
                break;
            }

            // Add the edge to MST
            MST.addEdge(minEdge.v1, minEdge.v2, minEdge.weight);

            // Mark both vertices as visited
            int v1Index = g.vertices.indexOf(minEdge.v1);
            int v2Index = g.vertices.indexOf(minEdge.v2);
            visited[v1Index] = true;
            visited[v2Index] = true;
        }
        return MST;
    }  // End minimumSpanningTree method




    private static void printGraphEdges(MyGraph g) {
        // use hashset to stop problem of repeat printing of v,e
        Set<String> printed = new HashSet<>();

        // loop through vertices
        for (int v : g.vertices) {
            // loop through edges in adjacencyList
            for (Edge e : g.adjacencyList.get(v)) {
                int a = Math.min(e.v1, e.v2);
                int b = Math.max(e.v1, e.v2);
                String edgeKey = a + "-" + b;
                if (!printed.contains(edgeKey)) {
                    System.out.printf("Vertex: %d is connected to: %d, weight: %d\n", e.v1, e.v2, e.weight);
                    printed.add(edgeKey);
                }
            }
        }
    } // End printGraphEdges method

//    private static void printGraphEdges(MyGraph g) {
//        // use hashset to stop problem of repeat printing of v,e
//        Set<String> printed = new HashSet<>();
//
//        // loop through vertices
//        for (int v : g.vertices) {
//            // loop through edges in adjacencyList
//            for (Edge e : g.adjacencyList.get(v)) {
//                int a = Math.min(e.v1, e.v2);
//                int b = Math.max(e.v1, e.v2);
//                String edgeKey = a + "-" + b;
//                if (!printed.contains(edgeKey)) {
//                    System.out.printf("Vertex: %d  (%d, %d, %d)\n", v ,e.v1, e.v2, e.weight);
//
//                    // System.out.printf("Vertex: %d is connected to: %d, weight: %d\n", e.v1, e.v2, e.weight);
//                    printed.add(edgeKey);
//                }
//            }
//        }
//    } // End printGraphEdges method


    public static void main(String[] args) {
        // connected components graph = ccg
        MyGraph ccg = new MyGraph();

        // Part 7 Main (connected components) - Add vertices 0-9
        for (int i = 0; i < 10; i++) {
            ccg.addVertex(i);
        }

        // add edges
        // v = 0
        ccg.addEdge(0, 1, 2);
        ccg.addEdge(0, 2, 13);
        // v = 1
        ccg.addEdge(1, 0, 2);
        // v = 2
        ccg.addEdge(2, 0, 13);
        // v = 3
        ccg.addEdge(3, 5, 1);
        // v = 4
        ccg.addEdge(4, 7, 1);
        // v = 5
        ccg.addEdge(5, 3, 1);
        // v = 6
        ccg.addEdge(6, 8, 1);
        // v = 7
        ccg.addEdge(7, 4, 1);
        // v = 8
        ccg.addEdge(8, 9, 4);
        // v = 9
        ccg.addEdge(9, 6, 9);
        //printGraphEdges(ccg);

        int[] componentArray = calculateConnectedComponents(ccg);
        System.out.println("Vertex Comp #");
        for (int i = 0; i< componentArray.length; i++) {
            //System.out.print("   "+i + "    " + componentArray[i]+ "\n");
            System.out.printf("   %d    %d\n", i, componentArray[i], "\n");
        }

        // Part 7 Main (Shortest Path & MST) - Add vertices 0-9
        MyGraph MyGraph = new MyGraph();
        ArrayList MyG_Array = new ArrayList();

        for (int i = 0; i < 10; i++) {
            MyGraph.addVertex(i);
            MyG_Array.add(i);
        }

        // add edges
        // v = 0
        MyGraph.addEdge(0, 1, 2);
        MyGraph.addEdge(0, 2, 13);
        MyGraph.addEdge(0, 3, 7);
        // v = 1
        MyGraph.addEdge(1, 0, 2);
        MyGraph.addEdge(1, 3, 2);
        // v = 2
        MyGraph.addEdge(2, 0, 13);
        MyGraph.addEdge(2, 4, 2);
        MyGraph.addEdge(2, 5, 7);
        // v = 3
        MyGraph.addEdge(3, 0, 7);
        MyGraph.addEdge(3, 1, 2);
        MyGraph.addEdge(3, 5, 1);
        MyGraph.addEdge(3, 6, 5);
        // v = 4
        MyGraph.addEdge(4, 2, 2);
        MyGraph.addEdge(4, 7, 1);
        // v = 5
        MyGraph.addEdge(5, 2, 7);
        MyGraph.addEdge(5, 3, 1);
        MyGraph.addEdge(5, 7, 4);
        MyGraph.addEdge(5, 8, 1);
        // v = 6
        MyGraph.addEdge(6, 3, 5);
        MyGraph.addEdge(6, 8, 1);
        MyGraph.addEdge(6, 9, 9);
        // v = 7
        MyGraph.addEdge(7, 4, 1);
        MyGraph.addEdge(7, 5, 4);
        MyGraph.addEdge(7, 8, 2);
        // v = 8
        MyGraph.addEdge(8, 5, 1);
        MyGraph.addEdge(8, 6, 1);
        MyGraph.addEdge(8, 7, 2);
        MyGraph.addEdge(8, 9, 4);
        // v = 9
        MyGraph.addEdge(9, 6, 9);
        MyGraph.addEdge(9, 8, 4);

        System.out.println("V = {" + MyG_Array.toString() + "}");

        shortestPath(MyGraph, 0);

        System.out.println();
        System.out.println("minimumSpanningTree");
        MyGraph mst = minimumSpanningTree(MyGraph, 0);
        mst.showGraph();


    } // End main method

} // End Main Class






// part 6 test sample data
/*
        // Add vertices 0-4
        for (int i = 0; i < 5; i++) {
            g.addVertex(i);
        }

        g.addEdge(0, 1, 1);  // 0-1
        g.addEdge(0, 2, 3);  // 0-3
        g.addEdge(1, 2, 1);  // 1-2
        g.addEdge(1, 4, 6);  // 1-3
        g.addEdge(1, 3, 2);  // 1-4
        g.addEdge(2, 3, 1);  // 2-4
        g.addEdge(3, 4, 2);  // 3-4

        // Calculate MST starting from vertex 0
        MyGraph mst = minimumSpanningTree(g, 0);

        System.out.println("Original Graph Edges:");
        printGraphEdges(g);

        System.out.println("\nMinimum Spanning Tree Edges:");
        printGraphEdges(mst);

        System.out.println();

        shortestPath(g, 0);
 */