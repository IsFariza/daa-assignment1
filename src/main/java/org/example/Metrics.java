package org.example;

public class Metrics {
    public static int comparisons = 0;
    public static int allocations = 0;
    public static int maxDepth = 0;

    public static void reset(){
        comparisons=0;
        allocations=0;
        maxDepth=0;
    }

    public static void printMetrics(){
        System.out.println("Comparisons: " + Metrics.comparisons);
        System.out.println("allocations: " + Metrics.allocations);
        System.out.println("Max Recursion Depth: " + Metrics.maxDepth);
    }
}

