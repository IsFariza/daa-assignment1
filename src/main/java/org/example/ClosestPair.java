package org.example;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Random;

public class ClosestPair {

    public static void main(String[] args){
        Metrics.reset();
        Random random = new Random();
        int n = 6;
        Point[] points = new Point[n];
        Metrics.allocations++;
        for(int i=0;i<n;i++){
            points[i] = new Point(random.nextInt(15), random.nextInt(15));
            System.out.println(points[i].x + " " + points[i].y);
        }

        double minDist = closestPair(points, points.length);
        System.out.println("Minimal distance is " + minDist);
        System.out.println();
        Metrics.printMetrics();
    }

    static class Point{
        public int x;
        public int y;
        public Point(int x, int y){
            this.x = x;
            this.y = y;
        }
    }

    public static double distance(Point p1, Point p2){
        Metrics.comparisons++;
        return Math.sqrt(
                (p1.x-p2.x)*(p1.x-p2.x) + (p1.y-p2.y)*(p1.y-p2.y));
    }

    public static double bruteForce(int size, Point[] points){
        double minDistance = Double.MAX_VALUE;
        for(int i=0;i<size;i++){
            for(int j=i+1;j<size;j++){
                double dist = distance(points[i], points[j]);
                if(dist < minDistance){
                    minDistance = dist;
                }
            }
        }
        return minDistance;
    }

    public static double strip(Point[] points, int size, double bestDistance){
        double minDistance = bestDistance;
        for(int i=0;i<size;i++){
            for(int j=i+1;j<size;j++){
                if(j>i+7)break;
                if((points[j].y - points[i].y >= minDistance)) break;
                double dist = distance(points[i], points[j]);
                if(dist < minDistance){
                    minDistance = dist;
                }
            }
        }
        return minDistance;
    }

    public static double closestPairRecurse(Point[] px, Point[] py, int size, int depth){
        Metrics.maxDepth=Math.max(Metrics.maxDepth, depth);
        if(size<=3){
            return bruteForce(size, px);
        }

        int midIndex = size / 2;
        Point midPair = px[midIndex];

        ArrayList<Point> leftHalf = new ArrayList<>();
        ArrayList<Point> rightHalf = new ArrayList<>();

        for(int i=0; i<size;i++){
            if(py[i].x <= midPair.x){
                leftHalf.add(py[i]);
            } else{
                rightHalf.add(py[i]);
            }
        }

        Point[] pyLeft = leftHalf.toArray(new Point[leftHalf.size()]);
        Metrics.allocations++;
        Point[] pyRight = rightHalf.toArray(new Point[rightHalf.size()]);
        Metrics.allocations++;

        Point[] pxLeft = new Point[midIndex];
        Metrics.allocations++;
        Point[] pxRight = new Point[size-midIndex];
        Metrics.allocations++;
        for(int i=0; i<midIndex;i++){
            pxLeft[i]=px[i];
        }
        for(int i=midIndex;i<size;i++){
            pxRight[i-midIndex]=px[i];
        }

        double minDistLeft = closestPairRecurse(pxLeft, pyLeft, pxLeft.length, depth+1);
        double minDistRight = closestPairRecurse(pxRight, pyRight, pxRight.length, depth+1);
        double bestDistance = Math.min(minDistLeft, minDistRight);

        ArrayList<Point> stripList = new ArrayList<>();
        for(int i=0;i<size;i++){
            if(Math.abs(py[i].x - midPair.x) < bestDistance){
                stripList.add(py[i]);
            }
        }
        Point[] strip = stripList.toArray(new Point[stripList.size()]);
        Metrics.allocations++;

        double distStrip = strip(strip, strip.length, bestDistance);
        bestDistance=Math.min(bestDistance, distStrip);
        return bestDistance;
    }

    public static double closestPair(Point[] points, int size){
        Point[] px = new Point[size];
        Metrics.allocations++;
        Point[] py = new Point[size];
        Metrics.allocations++;
        for(int i=0;i<size;i++){
            px[i] = points[i];
            py[i] = points[i];
        }
        Arrays.sort(px, Comparator.comparingInt(p -> p.x));
        Arrays.sort(py, Comparator.comparingInt(p -> p.y));

        return closestPairRecurse(px, py, px.length, 1);
    }
}
