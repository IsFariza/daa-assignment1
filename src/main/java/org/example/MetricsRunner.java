package org.example;

import java.io.*;
import java.util.*;

public class MetricsRunner {
    public static void main(String[] args) throws IOException {
        int[] sizes = {100, 1000, 5000, 10000, 20000};

        PrintWriter out = new PrintWriter(new FileWriter("results.csv"));
        out.println("n,algorithm,time,comparisons,maxDepth,allocations");

        for (int n : sizes) {
            int[] arr = generateRandomArray(n);
            //Merge sort
            int[] copy1 = Arrays.copyOf(arr, arr.length);
            Metrics.reset();
            long start1 = System.nanoTime();
            int[] buffer = new int[copy1.length];
            MergeSort.mergeSort(copy1, buffer, 0, copy1.length-1, 1);
            long time1 = System.nanoTime() - start1;
            out.printf("%d,MergeSort,%d,%d,%d,%d%n",
                    n, time1, Metrics.comparisons, Metrics.maxDepth, Metrics.allocations);

            // quick sort
            int[] copy2 = Arrays.copyOf(arr, arr.length);
            Metrics.reset();
            long start2 = System.nanoTime();
            QuickSort.quickSort(copy2, 0, copy2.length-1, 1);
            long time2 = System.nanoTime() - start2;
            out.printf("%d,QuickSort,%d,%d,%d,%d%n",
                    n, time2, Metrics.comparisons, Metrics.maxDepth, Metrics.allocations);

            // deterministic select
            int[] copy3 = Arrays.copyOf(arr, arr.length);
            Metrics.reset();
            long start3 = System.nanoTime();
            int k = n / 2;
            int selected = DeterministicSelect.deterministicSelect(copy3, 0, copy3.length-1, k+1, 1);
            long time3 = System.nanoTime() - start3;
            out.printf("%d,DeterministicSelect,%d,%d,%d,%d%n",
                    n, time3, Metrics.comparisons, Metrics.maxDepth, Metrics.allocations);

            //closets pair
//
//            ClossestPair.Point[] points = generateRandomPoints(n);
//            Metrics.reset();
//            long start4 = System.nanoTime();
//            System.out.println("n = " + n + ", points.length = " + points.length);
//            double dist = ClossestPair.closestPair(points, points.length);
//            long time4 = System.nanoTime() - start4;
//            out.printf("%d,ClosestPair,%d,%d,%d,%d%n",
//                    n, time4, Metrics.comparisons, Metrics.maxDepth, Metrics.allocations);
        }

        out.close();
        System.out.println("Results saved to results.csv");
    }

    static int[] generateRandomArray(int n) {
        Random rand = new Random();
        int[] arr = new int[n];
        for (int i = 0; i < n; i++) arr[i] = rand.nextInt(1000000);
        return arr;
    }

    static ClosestPair.Point[] generateRandomPoints(int n) {
        Random rand = new Random();
        ClosestPair.Point[] points = new ClosestPair.Point[n];
        for (int i = 0; i < n; i++) {
            points[i] = new ClosestPair.Point(rand.nextInt(10000), rand.nextInt(10000));
        }
        return points;
    }
}
