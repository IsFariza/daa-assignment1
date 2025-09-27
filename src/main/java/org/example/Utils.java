package org.example;

public class Utils {
    public static void swap(int[] array, int index1, int index2){
        int temp = array[index1];
        array[index1] = array[index2];
        array[index2]=temp;
    }

    public static void insertionSort(int[] array, int left, int right){
        for(int i=left;i<=right;i++){
            int temp = array[i];
            int j = i-1;
            while(j>=left){
                Metrics.comparisons++;
                if(array[j] > temp){
                    array[j+1] = array[j];
                    j--;
                } else break;


            }
            array[j+1] = temp;
        }
    }

    public static int partitioning(int[] array, int lowIndex, int highIndex){
        int pivot = array[highIndex];
        int i = lowIndex;
        for(int j=lowIndex; j<highIndex;j++){
            Metrics.comparisons++;
            if(array[j]<=pivot){
                swap(array, i, j);
                i++;
            }
        }
        swap(array, i, highIndex);
        return i;


    }
}

