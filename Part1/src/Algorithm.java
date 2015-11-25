/**
 * Created by Brad on 11/20/2015.
 *
 * Static class that contains Project 2 - part 1 algorithms
 *  - Brute Force
 *  - Dynamic Programming
 */
public class Algorithm {

    /**
     * Brute Force algorithms
     */

    public static void BruteForce(int[] taskArray, int numProcessors) {
        /*Method Call:  BruteForce(
                            <task array>,
                            <# of processors>,
                            <array to store work load for each processor>,
                            <the current best load interval for processors (Max int)>,
                            <the starting position for iterating the array>
                        );*/
        System.out.print(BruteForce(taskArray, numProcessors, new int[numProcessors], Integer.MAX_VALUE, 0));
    }

    private static int BruteForce(int[] taskArray, int currentProcessor, int[] procWork, int bestL, int i) {

        // Recursion Base Condition (current processor must be greater than 0)
        // For position in processor work load array
        if (currentProcessor > 0) {

            // Loop through task array & assign current task(s) to current working processor
            while (i < taskArray.length) {

                // Add current task to the processor we are working with
                procWork[currentProcessor - 1] += taskArray[i];

                // Recurse with the next (previous) processor and the next item in the task array
                int currentLoad = BruteForce(taskArray, currentProcessor - 1, procWork, bestL, i+1);

                // Change overall best load if current load is better
                if (currentLoad < bestL) {
                    bestL = currentLoad;
                }

                // Go to next item in task array
                i++;
            }

            // Reset the current working processors tasks to 0 to get ready for the next round of permutations
            procWork[currentProcessor - 1] = 0;

        } else {    // If base case was met

            // If we have done all permutations of the task array
            if (i == taskArray.length) {
                // Print for testing only
                Print(procWork);

                // Get most loaded processors load
                int worstLoad = getMaxLoad(procWork);

                // Change the overall best max processor load if the current is better than overall
                if (worstLoad < bestL) {
                    bestL = worstLoad;
                }
            }
        }

        // Return the most loaded processors total load
        return bestL;
    }

    private static void Print(int[] procWork) {
        /// Printing
        System.out.println("***");
        for (int j = procWork.length - 1; j >= 0; j--) {
            System.out.print(procWork[j] + " ");
        }
        System.out.println();
        ///// end Printing
    }

    private static int getMaxLoad(int[] procWork) {

        int max = procWork[0];
        for (int i = 1; i < procWork.length; i++) {
            if (procWork[i] > max) {
                max = procWork[i];
            }
        }
        return max;
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
