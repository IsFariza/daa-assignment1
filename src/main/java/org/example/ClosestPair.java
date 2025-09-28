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

        double minDist = closestPair(points);
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

    public static double strip(Point[] points,double bestDistance){
        double minDistance = bestDistance;
        for(int i=0;i< points.length;i++){
            for(int j=i+1;j< points.length;j++){
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

    public static double closestPairRecurse(Point[] px, Point[] py, int depth){
        Metrics.maxDepth=Math.max(Metrics.maxDepth, depth);
        int size=px.length;
        if(size<=3){
            return bruteForce(size, px);
        }

        int midIndex = size / 2;
        Point midPair = px[midIndex];

        ArrayList<Point> leftHalf = new ArrayList<>();
        ArrayList<Point> rightHalf = new ArrayList<>();

        for(Point p:py){
            if(p.x <= midPair.x){
                leftHalf.add(p);
            } else{
                rightHalf.add(p);
            }
        }

        Point[] pyLeft = leftHalf.toArray(new Point[0]);
        Point[] pyRight = rightHalf.toArray(new Point[0]);
        Metrics.allocations+=2;

        Point[] pxLeft = Arrays.copyOfRange(px, 0, midIndex);
        Point[] pxRight = Arrays.copyOfRange(px, midIndex, size);
        Metrics.allocations+=2;


        double minDistLeft = closestPairRecurse(pxLeft, pyLeft, depth+1);
        double minDistRight = closestPairRecurse(pxRight, pyRight,depth+1);
        double bestDistance = Math.min(minDistLeft, minDistRight);

        ArrayList<Point> stripList = new ArrayList<>();
        for(Point p: py){
            if(Math.abs(p.x-midPair.x)<bestDistance){
                stripList.add(p);
            }
        }
        Point[] strip = stripList.toArray(new Point[0]);
        Metrics.allocations++;

        double distStrip = strip(strip, bestDistance);
        bestDistance=Math.min(bestDistance, distStrip);
        return bestDistance;
    }

    public static double closestPair(Point[] points){

        Point[] px = Arrays.copyOf(points, points.length);
        Point[] py = Arrays.copyOf(points, points.length);
        Metrics.allocations+=2;
        Arrays.sort(px, Comparator.comparingInt(p -> p.x));
        Arrays.sort(py, Comparator.comparingInt(p -> p.y));

        return closestPairRecurse(px, py, 1);
    }
}
