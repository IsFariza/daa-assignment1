package org.example;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

class QuickSortTest {
    @Test
    void testQuickSortRandom(){
        Metrics.reset();
        Random random = new Random();
        int[] arr=new int[100];
        for(int i=0;i< arr.length;i++){
            arr[i]=random.nextInt(1000);
        }

        int[] expected = Arrays.copyOf(arr, arr.length);
        Arrays.sort(expected);

        QuickSort.quickSort(arr, 0, arr.length-1, 1);
        assertArrayEquals(expected, arr);
    }

    @Test
    void testQuickSortSorted(){
        Metrics.reset();
        int[] arr={1, 2,3, 4, 5, 6, 7,8, 9};
        int[] expected = Arrays.copyOf(arr, arr.length);
        Arrays.sort(expected);
        QuickSort.quickSort(arr, 0, arr.length-1, 1);
        assertArrayEquals(expected, arr);
    }
    @Test
    void testQuickSortWithDuplicates(){
        Metrics.reset();
        int[] arr={5, 1, 5, 3, 5, 2, 5};
        int[] expected = Arrays.copyOf(arr, arr.length);
        Arrays.sort(expected);
        QuickSort.quickSort(arr, 0, arr.length-1, 1);
        assertArrayEquals(expected, arr);
    }

    @Test
    void testQuickSortReversed(){
        Metrics.reset();
        int[] arr={9, 8, 7, 6, 5, 4, 3, 2, 1};
        int[] expected = Arrays.copyOf(arr, arr.length);
        Arrays.sort(expected);

        QuickSort.quickSort(arr, 0, arr.length-1, 1);
        assertArrayEquals(expected, arr);
    }

    @Test
    void testQuickSortRecursionDepth(){
        Metrics.reset();
        int n=1024;
        Random random = new Random();
        int[] arr=new int[n];
        for(int i=0;i< arr.length;i++){
            arr[i]=random.nextInt(1000);
        }

        QuickSort.quickSort(arr, 0, arr.length-1, 1);
        int expectedMaxDepth=2*(int)Math.floor(Math.log(n)/Math.log(2))+2;
        assertTrue(Metrics.maxDepth <= expectedMaxDepth);
    }

}