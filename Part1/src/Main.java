import java.io.*;
import java.util.List;

/**
 * Created by Brad on 11/20/2015.
 *
 * Main class for Project 2 - part 1 assignment
 *  - Contains entry point and execution handling for
 *      implementing and using the static Algorithm class.
 *  - Run the program from the command line like the following:
 *      -- java Main <file to process> <algorithm flag> <# of processors> <iff -p algo flag then target load>
 */
public class Main {


    public static void main (String[] args) {

        /**
         * Catch if the program was instantiated incorrectly and print error message
         * and how to use the program message.
         */
        if (args.length != 3) ShowErrorMessage();



        /**
         * If the program was instantiated correctly start program
         * execution here.
         */
        else {

            /** Local Variables **/
            File file = new File(args[0]);                  // File to read tasks from
            String flag = args[1];                          // Algo flag
            int numProcessors = Integer.parseInt(args[2]);  // # of processors
            BufferedReader reader;                          // For reading from file
            String line;                                    // Used in reading from file
            int numTasks;                                   // First element in the file is # of tasks
            int[] taskArray;                                // Array for tasks to fill

            try {

                // Initialize local variables
                reader = new BufferedReader(new FileReader(file));
                numTasks = Integer.parseInt(reader.readLine());
                taskArray = new int[numTasks];

                // Populate taskArray from file data
                for (int i = 0; i < numTasks; i++)
                    if ((line = reader.readLine()) != null) taskArray[i] = Integer.parseInt(line);

                // Route program based on flag
                switch (flag) {
                    case "-b":
                        System.out.print( printBruteForce(Algorithm.BruteForce(taskArray, numProcessors)) );
                        break;
                    case "-d":
                        Algorithm.DynamicProgramming(taskArray, numProcessors);
                        break;
                    case "-p":
                        System.out.println( printPartitions(Algorithm.ParametricSearch(taskArray, numProcessors)));
                        break;
                    case "-g":
                        System.out.println( printPartitions(Algorithm.Greedy(taskArray, numProcessors)) );
                        break;
                    default:
                        System.out.println(
                                "\nInvalid flag " + flag + " used."
                                + "\nValid flags are:"
                                + "\n\t-b for Brute Force"
                                + "\n\t-d for Dynamic Programming\n"
                        );

                        System.exit(2);
                }

            } catch (IOException e) {
                System.out.println(
                        "Cannot find the file " + file.toString()
                        + "\nExiting Program"
                );
                System.exit(3);
            }
        }
    }

    private static String printPartitions(List<List<Integer>> partitionLists) {

        StringBuilder sb = new StringBuilder();

        for (List<Integer> list : partitionLists)
            sb.append(print(list)).append("\n");

        return sb.toString();
    }

    private static String print(List<Integer> arrayList) {

        StringBuilder sb = new StringBuilder();
        int sum = 0;

        for (Integer i : arrayList) {
            sb.append(i).append(" ");
            sum += i;
        }

        return sb.append(" sum = ").append(sum).toString();
    }

    private static String printBruteForce(List<Integer> list) {

        StringBuilder sb = new StringBuilder("Most Loaded Processor = " + list.get(0) + "\nProcessor loads -> ");

        for (int i = list.size() - 1; i > 0; i--)
            sb.append(list.get(i)).append(" ");

        return sb.toString();
    }

    private static void ShowErrorMessage() {

        System.out.println(
                "\nIncorrect usage of program. See Program Instructions below:\n\n"
                        + "PROGRAM INSTRUCTIONS"
                        + "\nUse program like follows:"
                        + "\njava Main <file to process> [<-b for brute force || -d for dynamic programming || <-p for parametric search> || <-g for gready algorithm>] <# of processors>"
                        + "\n\nExample for using dynamic programming with 8 processors on the file test.txt"
                        + "\n\tjava Main test.txt -d 8\n"
        );

        System.exit(3);
    }
}
