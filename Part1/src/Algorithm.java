/**
 * Created by Brad on 11/20/2015.
 *
 * Static class that contains Project 2 - part 1 algorithms
 *  - Brute Force
 *  - Dynamic Programming
 */
public class Algorithm {

    /**
     * Brute Force algorithm
     */
    public static void BruteForce(int[] taskArray, int numProcessors) {

        // Implement algorithm here
        printArray(taskArray);
    }

    /**
     * Dynamic Programming algorithm
     */
    public static void DynamicProgramming(int[] taskArray, int numProcessors) {

        // Implement algorithm here
    }

    private static void printArray(int[] taskArray) {

        System.out.print("[");
        for (int i = 0; i < taskArray.length; i++) {
            System.out.print(taskArray[i]);
            if (i != taskArray.length - 1) {
                System.out.print(", ");
            }
        }
        System.out.print("]");
    }
}
