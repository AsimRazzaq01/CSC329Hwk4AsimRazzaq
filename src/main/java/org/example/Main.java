package org.example;

import com.sun.nio.sctp.SctpStandardSocketOptions;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * @author asim razzaq
 * Main Class
 * getMinFrontierEdge method
 * minimumSpanningTree method
 */
public class Main {

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


    public static void main(String[] args) {
        MyGraph g = new MyGraph();

        // Add vertices 0-4
        for (int i = 0; i < 5; i++) {
            g.addVertex(i);
        }

        // Add edges with weights
        g.addEdge(0, 1, 2);  // 0-1
        g.addEdge(0, 3, 6);  // 0-3
        g.addEdge(1, 2, 3);  // 1-2
        g.addEdge(1, 3, 8);  // 1-3
        g.addEdge(1, 4, 5);  // 1-4
        g.addEdge(2, 4, 7);  // 2-4
        g.addEdge(3, 4, 9);  // 3-4

        // Calculate MST starting from vertex 0
        MyGraph mst = minimumSpanningTree(g, 0);

        System.out.println("Original Graph Edges:");
        printGraphEdges(g);

        System.out.println("\nMinimum Spanning Tree Edges:");
        printGraphEdges(mst);
    }
}

