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
     * Implements the greedy algorithm (Algorithm.Greedy) with an added target load parameter. If the target load is less than the
     * optimal* (based on the greedy algo - not actually optimal) most loaded processor then it will return null.
     * If the optimal* solution is less than the target load then the processor partitions will be returned.
     *
     * @param taskArray - the arra of tasks
     * @param numProcessors - how many processors will share the tasks
     * @param targetLoad - the target max load of the most loaded processor
     * @return - List<List<Integer>> of processor partitions, or null if target load not met
     */
    public static List<List<Integer>> ParametricSearch(int[] taskArray, int numProcessors, int targetLoad) {

        // Divide sum of task array by the ideal load factor
        List<List<Integer>> processorWork = Greedy(taskArray, numProcessors);

        int mostLoaded = Integer.MIN_VALUE, sum;

        for (List<Integer> list : processorWork)
            if ((sum = sum(list)) > mostLoaded) mostLoaded = sum;

        return (mostLoaded <= targetLoad) ? processorWork : null;
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
    public static List<List<Integer>> Greedy(int[] taskArray, int numProcessors) {

        // Divide sum of task array by the # of processors
        int taskSum = sum(taskArray);
        int workPerProcessor = taskSum / numProcessors;

        // Make lists to hold processors work load
        List<List<Integer>> processorWork = new ArrayList<>();
        for (int i = 0; i < numProcessors; i++) processorWork.add(new ArrayList<>());

        // Assign (around) this amount of work to each processor
        int sum = 0, currentProcessor = 0;
        for (int i : taskArray) {

            if ((sum + i) <= workPerProcessor && currentProcessor < numProcessors) {
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
