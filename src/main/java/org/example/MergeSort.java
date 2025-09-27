package org.example;

import java.util.Random;

public class MergeSort {

    static final int CUTOFF = 16;


    public static void main(String[] args){
        Metrics.reset();
        Random random = new Random();
        int[] numbers = new int [15];
        Metrics.allocations++;
        for(int i =0;i<numbers.length;i++){
            numbers[i] = random.nextInt(100);
            System.out.println(numbers[i]);
        }

        int[] buffer = new int[numbers.length];
        Metrics.allocations++;
        mergeSort(numbers, buffer, 0, numbers.length-1, 1);


        System.out.println("merge sort");
        for(int i =0;i<numbers.length;i++){
            System.out.println(numbers[i]);;
        }
        Metrics.printMetrics();
    }

    public static void mergeSort(int[] array, int[] buffer, int left, int right, int depth){
        Metrics.maxDepth = Math.max(Metrics.maxDepth, depth);
        if((right-left+1)<=CUTOFF){
            Utils.insertionSort(array, left, right);
            return;
        }


        int middleIndex = (left+right)/2;

        mergeSort(array, buffer, left, middleIndex, depth+1);
        mergeSort(array, buffer, middleIndex+1, right, depth+1);
        merge(array, buffer, left, middleIndex, right);
    }

    private static void merge(int[] array, int[] buffer, int left, int middle, int right){
        for(int i=left; i<=right;i++){
            buffer[i]=array[i];
            Metrics.allocations++;
        }
        int i=left;
        int j=middle+1;
        int k=left;
        while(i<=middle && j<=right){
            Metrics.comparisons++;
            if(buffer[i]<=buffer[j]) {
                array[k] = buffer[i];
                i++;

            } else{
                array[k] = buffer[j];
                j++;
            } k++;
        }

        while(i<=middle){
            array[k] = buffer[i];
            i++;
            k++;
        }
        while(j<=right){
            array[k] = buffer[j];
            j++;
            k++;
        }


    }
}
