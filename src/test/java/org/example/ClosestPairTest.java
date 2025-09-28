package org.example;

import org.junit.jupiter.api.Test;

import java.util.Random;


import static org.junit.jupiter.api.Assertions.*;

class ClosestPairTest {
    @Test
    void testClosestPairSmallN(){
        Metrics.reset();
        int n=1000;
        ClosestPair.Point[] points = new ClosestPair.Point[n];

        Random random = new Random();
        for(int i=0;i<n;i++){
            points[i] = new ClosestPair.Point(random.nextInt(10000), random.nextInt(10000));
        }
        double bruteForceDist = ClosestPair.bruteForce(n, points);
        double fastDist = ClosestPair.closestPair(points);
        assertEquals(bruteForceDist, fastDist);
    }

    @Test
    void testClosestPairLargeN(){
        Metrics.reset();
        int n=100000;
        ClosestPair.Point[] points = new ClosestPair.Point[n];
        Random random = new Random();
        for(int i=0;i<n;i++){
            points[i] = new ClosestPair.Point(random.nextInt(1000000), random.nextInt(1000000));
        }
        double fastDist = ClosestPair.closestPair(points);
        assertTrue(fastDist>=0);

    }

}