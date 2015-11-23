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
    /*public static int BruteForce(int[] taskArray, int numProcessors) {

        int p1sum = 0, p2sum = 0, bestL = Integer.MAX_VALUE;

        for (int i = 0; i < taskArray.length; i++) {
            p2sum += taskArray[i];
        }

        for (int i = 0; i < taskArray.length - 1; i++) {
            p1sum += taskArray[i];
            p2sum -= taskArray[i];
            int max = getMax(p1sum, p2sum);

            if (max < bestL) {
                bestL = max;
            }
        }

        return bestL;

        //printArray(taskArray);
    }*/

    public static void BruteForce(int[] taskArray, int numProcessors) {

        int[] procArray = new int[numProcessors];
        int maxL = Integer.MAX_VALUE;

        for (int i = 0; i < numProcessors - 1; i++) {
            procArray[i] = taskArray[i];
        }

        int max = BruteForce(taskArray, procArray, numProcessors - 1, 0, maxL, numProcessors, 3);

        System.out.print("maxL: " + max);
    }

    private static int BruteForce(int[] taskArray, int[] procArray, int start, int lo, int maxL, int numProc, int count) {

        if (start < taskArray.length) {

            for (int i = start; i < taskArray.length; i++) {
                procArray[numProc - 1] += taskArray[i];
            }

            int tempMax = getMax(procArray);

            if (tempMax < maxL) {
                maxL = tempMax;
            }

            procArray[numProc - 2] += taskArray[start];
            procArray[numProc - 1] = 0;
            return BruteForce(taskArray, procArray, start + 1, lo, maxL, numProc, count);

            /** Work out the next iteration for more than 2 */
            /*procArray[lo] += taskArray[numProc - 2];
            procArray[1] = taskArray[2];
            procArray[2] = 0;
            return BruteForce(taskArray, procArray, count, lo, maxL, numProc, count);*/

        } else {

            return maxL;
        }
    }

    private static int getMax(int[] parray) {

        int max = 0;
        for (int i = 0; i < parray.length; i++) {
            if (parray[i] > max){
                max = parray[i];
            }
        }

        return max;
    }

    private static int getMax(int p1sum, int p2sum) {

        if (p1sum > p2sum)
            return p1sum;
        else
            return p2sum;
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
