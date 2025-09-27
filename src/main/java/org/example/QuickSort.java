package org.example;

import java.util.Random;

public class QuickSort {

    static final int CUTOFF = 16;
    static Random random = new Random();

    public static void main(String[] args){
        Metrics.reset();
        int[] numbers = new int[20];
        for(int i=0;i<numbers.length;i++){
            numbers[i] = random.nextInt(100);
            System.out.println(numbers[i]);
        }

        System.out.println("Quick Sort: ");
        quickSort(numbers, 0, numbers.length-1, 1);
        for(int i : numbers){
            System.out.println(i);
        }
        Metrics.printMetrics();

    }
    public static void quickSort(int[] array, int lowIndex, int highIndex, int depth){
        Metrics.maxDepth = Math.max(Metrics.maxDepth, depth);

        while(lowIndex<highIndex){
            if(highIndex-lowIndex+1 <= CUTOFF) {
                Utils.insertionSort(array, lowIndex, highIndex);
                break;
            }

            int pivotIndex = random.nextInt(highIndex-lowIndex+1) + lowIndex;
            Utils.swap(array, pivotIndex, highIndex);

            int leftPointer = Utils.partitioning(array, lowIndex, highIndex);

            int leftPartSize = leftPointer - lowIndex;
            int rightPartSize = highIndex-leftPointer;
            Metrics.allocations++;

            if(leftPartSize<rightPartSize){
                quickSort(array, lowIndex, leftPointer - 1, depth+1);
                lowIndex = leftPointer + 1;
            } else{
                quickSort(array, leftPointer+1, highIndex, depth+1);
                highIndex = leftPointer-1;
            }
        }
    }
}
