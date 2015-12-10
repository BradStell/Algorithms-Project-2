import java.util.ArrayList;
import java.util.List;

/**
 * Created by Brad on 11/20/2015.
 *
 * Static class that contains Project 2 - part 1 & 2 algorithms
 *  - Brute Force
 *  - Dynamic Programming
 *  - Parametric Search
 *  - Greedy
 */
public class Algorithm {

    // Used with BruteForce to keep track of processor loads
    private static List<Integer> masterLoadKeeper = new ArrayList<>();

    /**
     * Brute Force algorithms entry point from outside code
     *
     * Designed to accept the task array to process and the number of processors.
     * Hides implementation for more complex BruteForce algo that recurses on itself
     */
    public static List<Integer> BruteForce(int[] taskArray, int numProcessors) {
        /*Method Call:  BruteForce(
                            <task array>,
                            <# of processors (-1 for array locations>,
                            <array to store work load for each processor>,
                            <the current best load interval for processors (Max int)>,
                            <the starting position for iterating the array>
                        );*/
        return new ArrayList<Integer>() {{
            add(BruteForce(taskArray, numProcessors - 1, new int[numProcessors], Integer.MAX_VALUE, 0));
            for (int i : masterLoadKeeper)
                add(i);
        }};
    }

    /**
     * Main brute force algo that recurses on itself to solve the brute force approach to
     * partitioning the task array into all possible partitoins given the number of processors.
     *
     * @param taskArray - The array of tasks
     * @param currentProcessor - The number of processors -1 to compensate for array placement
     * @param processorWork - An array of length of number of processors, used to store
     *                      each processors total work load at any given time
     * @param bestLoad - The minimal most loaded processor at a given partition
     * @param i - The index corresponding to the item being processed currently in the task array
     * @return
     */
    private static int BruteForce(int[] taskArray, int currentProcessor, int[] processorWork, int bestLoad, int i) {

        // Recursion Base Condition (current processor must be greater than 0)
        // For position in processor work load array
        if (currentProcessor >= 0) {

            // Loop through task array & assign current task(s) to current working processor
            while (i < taskArray.length) {

                // Add current task to the processor we are working with
                processorWork[currentProcessor] += taskArray[i];

                // Recurse with the next (previous) processor and the next item in the task array
                int currentLoad = BruteForce(taskArray, currentProcessor - 1, processorWork, bestLoad, i++ + 1);

                // Change overall best load if current load is better
                if (currentLoad < bestLoad) bestLoad = currentLoad;
            }

            // Reset the current working processors tasks to 0 to get ready for the next round of permutations
            processorWork[currentProcessor] = 0;

        } else {    // If base case was met

            // If we have done all permutations of the task array
            if (i == taskArray.length) {
                // Get most loaded processors load
                int worstLoad = getMaxLoad(processorWork);

                // Change the overall best max processor load if the current is better than overall
                if (worstLoad < bestLoad) {
                    bestLoad = worstLoad;
                    masterLoadKeeper.clear();
                    for (int in : processorWork)
                        masterLoadKeeper.add(in);
                }
            }
        }

        // Return the most loaded processors total load
        return bestLoad;
    }

    /**
     * Returns the most loaded processors work load from the processor array
     *
     * @param procWork - Array of length number of processors containing the work load for
     *                 each processor at a given partition of the task array
     * @return - The most loaded processors amount of work
     */
    private static int getMaxLoad(int[] procWork) {
        int max = Integer.MIN_VALUE;

        for (int in : procWork)
            if (in > max) max = in;

        return max;
    }

    /**
     * Dynamic Programming algorithm
     *
     * Cannot figure out subproblem property. Also cannot figure out how to memoize brute force algorithm.
     */
    public static void DynamicProgramming(int[] taskArray, int numProcessors) {

        // Implement algorithm here
    }

    /**
     * This search is similar to a binary search. It calls the Greedy algorithm below with a target load.
     * This target load will start out as the sum of the process times in the task array. If there is a valid
     * solution to the greedy algorithm with the target load then the greedy alorithm will be called again with
     * half of the target load. If that is a valid solution the target load will get cut in half again, if it is
     * not a valid solution then the new target load will be between the original target load and the current. This
     * acts like a binary search moving in halves each time until the most valid solution is retrieved.
     *
     * @param taskArray - the array of tasks
     * @param numProcessors - how many processors will share the tasks
     * @return - List<List<Integer>> of processor partitions that is most optimal given the target load
     *              and the greedy algorithm
     */
    public static List<List<Integer>> ParametricSearch(int[] taskArray, int numProcessors) {

        // Local variables needed
        List<List<Integer>> listPointer = null;     // Will hold the optimal partitions of tasks of the processors
        int targetLoad = sum(taskArray);            // The current target load
        int mostLoaded;                             // most loaded processor after each call to greedy algorithm
        int low = 0;                                // Holds the low point for binary searching
        int previous = 0;                           // Holds the upper point for binary searching

        // Call greedy algorithm until we converge on a single target load
        while (low != targetLoad) {
            mostLoaded = calculateMostLoaded((listPointer = Greedy(taskArray, numProcessors, targetLoad)));

            if (mostLoaded <= targetLoad) {
                previous = targetLoad;
                targetLoad = ((targetLoad - low) / 2) + low;

            } else {
                low = targetLoad;
                targetLoad = ((targetLoad - previous) / 2) + previous;
            }
        }

        return listPointer;
    }

    /**
     * Returns the most loaded processor out of the processor partition lists
     * @param procList - List<List<Integer>> List of Lists representing processor tasks
     * @return int - Most loaded processor value
     */
    private static int calculateMostLoaded(List<List<Integer>> procList) {

        int max = Integer.MIN_VALUE, sum;
        for (List<Integer> list : procList)
            max = ((sum = calcSum(list)) > max) ? sum : max;
        return max;
    }

    /**
     * Works with calculateMostLoaded()
     * returns the sum of a List<Integer>
     * @param list - List<Integer> list of a particular processor to be summed
     * @return
     */
    private static int calcSum(List<Integer> list) {
        int sum = 0;
        for (int i : list) sum += i;
        return sum;
    }

    /**
     * Greedy algorithm entry point. This calls the greedy algorithm with a target load factor with a greedy
     * approach. The average task load per processor. It sums the task times and divides by the number of
     * processors. It then attempts to assign to each processor tasks until the average is met. The last
     * processor will take any overflow tasks.
     *
     * @param taskArray - array of tasks
     * @param numProcessors - how many processors there are
     * @return - List<List<Integer>> a list of lists of each processors tasks
     */
    public static List<List<Integer>> Greedy(int[] taskArray, int numProcessors) {

        int taskSum = sum(taskArray);
        int workPerProcessor = taskSum / numProcessors;

        return Greedy(taskArray, numProcessors, workPerProcessor);
    }

    /**
     * Takes a greedy approach which is ok and works practically for real life situations, however
     * is not guaranteed to return the actual optimal solution. However this can compute almost
     * optimal solutions in O(n) time compared to the O(n!) time from Brute Force algorithm.
     *
     * Divides the sum of all tasks by the number of processors and attempts to distribute tasks
     * evenly until the sum of an individual processor exceeds (or would exceed) the decided sum per
     * processor.
     *
     * @param taskArray - the array of tasks
     * @param numProcessors - how many processors will share these tasks
     * @return - List<List<Integer>> containing the partitions of each processor
     */
    private static List<List<Integer>> Greedy(int[] taskArray, int numProcessors, int load) {

        // Make lists to hold processors work load and initialize arraylist
        List<List<Integer>> processorWork = new ArrayList<>();
        for (int i = 0; i < numProcessors; i++) processorWork.add(new ArrayList<>());

        // Assign (around) this amount of work to each processor
        int sum = 0, currentProcessor = 0;
        for (int i : taskArray) {

            if ((sum + i) <= load && currentProcessor < numProcessors) {
                processorWork.get(currentProcessor).add(i);
                sum += i;
            } else {
                sum = i;
                currentProcessor += (currentProcessor == numProcessors - 1) ? 0 : 1;
                processorWork.get(currentProcessor).add(i);
            }
        }

        return processorWork;
    }

    /**
     * Calculates the sum of elements in a List<Integer> data structure
     * @param list - the list to sum
     * @return - int of the sum of all elements in the list
     */
    private static int sum(List<Integer> list) {
        int sum = 0;
        for (int i : list) sum += i;

        return sum;
    }

    /**
     * Calculates the sum of elements in an int[] array
     * @param array - array to sum
     * @return - int of the sum of all elements in the array
     */
    private static int sum(int[] array) {
        int sum = 0;
        for (int i : array) sum += i;

        return sum;
    }
}
