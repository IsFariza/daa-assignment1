package org.example;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

class DeterministicSelectTest {
    @Test
    void testDeterministicSelectRandomTrials(){
        Random random = new Random();
        int n=100;
        int trials = 100;

        for(int t=0;t<trials;t++){
            Metrics.reset();
            int[] arr=new int[n];
            for(int i=0;i<n;i++){
                arr[i] = random.nextInt(1000);
            }

            int randomRank = random.nextInt(n)+1;
            int[] copy= Arrays.copyOf(arr, arr.length);
            Arrays.sort(copy);
            int expected =copy[randomRank-1];

            int result = DeterministicSelect.deterministicSelect(
                    Arrays.copyOf(arr, arr.length), 0, arr.length-1, randomRank, 1);
            assertEquals(expected, result);

        }
    }

    @Test
    void testDeterministicSelectSmallArrays(){
        Metrics.reset();
        int[] arr={5, 2, 9, 1, 6};
        int k=3;

        int expected=5;
        int result = DeterministicSelect.deterministicSelect(
                Arrays.copyOf(arr, arr.length), 0, arr.length-1, k, 1);
        assertEquals(expected, result);
    }

}