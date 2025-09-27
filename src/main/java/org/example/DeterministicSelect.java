package org.example;

import java.util.Random;

public class DeterministicSelect {

    public static void main(String[] args){
        Metrics.reset();
        int[] numbers = new int[15];
        Metrics.allocations++;
        Random random = new Random();
        for(int i = 0 ; i<numbers.length;i++){
            numbers[i]= random.nextInt(20);
            System.out.print(numbers[i] + " ");
        }
        System.out.println();
        int rank = 1;
        System.out.println(rank + "th smallest number is " + deterministicSelect(numbers, 0, numbers.length-1, rank, 1));

        Metrics.printMetrics();

    }

    public static int deterministicSelect(int[] array, int left, int right, int rank, int depth){
        Metrics.maxDepth = Math.max(Metrics.maxDepth, depth);
        int subArrSize = right - left +1;
        if(subArrSize<=5){
            Utils.insertionSort(array, left, right);
            return array[left + rank -1];
        }

        int numberOfMedians = (subArrSize+4)/5;
        Metrics.allocations+= numberOfMedians;

        for(int i=0; i<numberOfMedians;i++){
            int subArrStart = left + i *5;
            int subArrEnd = Math.min(subArrStart+4, right);
            Utils.insertionSort(array, subArrStart, subArrEnd);

            int medianIndex = subArrStart+(subArrEnd-subArrStart)/2;
            Utils.swap(array, left+i, medianIndex);
        }

        int medianOfMediansIndex = (numberOfMedians+1)/2;
        int medianOfMedians = deterministicSelect(array, left, left + numberOfMedians-1, medianOfMediansIndex, depth+1);

        int pivotIndex = -1;
        for(int i=left; i<=right;i++){
            if(array[i]==medianOfMedians){
                pivotIndex = i;
                break;
            }
        }

        pivotIndex = partitioning(array, left, right, pivotIndex);

        int leftSize = pivotIndex -left + 1 ;

        if(leftSize==rank) return array[pivotIndex];

        if(rank < leftSize){
            return deterministicSelect(array, left, pivotIndex-1, rank, depth+1);
        }

        return deterministicSelect(array, pivotIndex+1, right, rank-leftSize, depth+1);
    }

    public static int partitioning(int[] array, int left, int right, int pivotIndex){
        Utils.swap(array, pivotIndex, right);
        int pivot = array[right];
        int i = left;
        for(int j=left; j<right; j++){
            Metrics.comparisons++;
            if(array[j] <= pivot){
                Utils.swap(array, i, j);
                i++;
            }
        }
        Utils.swap(array, i, right);
        return i;
    }

}
