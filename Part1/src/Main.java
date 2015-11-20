import java.io.*;

/**
 * Created by Brad on 11/20/2015.
 *
 * Main class for Project 2 - part 1 assignment
 *  - Contains entry point and execution handling for
 *      implementing and using the static Algorithm class.
 *  - Run the program from the command line like the following:
 *      -- java Main <file to process> <-b for brute force || -d for dynamic programming> <# of processors>
 */
public class Main {


    public static void main (String[] args) {

        if (args.length != 3) {
            System.out.println(
                    "\nIncorrect usage of program. See Program Instructions below:\n\n"
                    + "PROGRAM INSTRUCTIONS"
                    + "\nUse program like follows:"
                    + "\njava Main <file to process> <-b for brute force || -d for dynamic programming> <# of processors>"
                    + "\n\nExample for using dynamic programming with 8 processors on the file test.txt"
                    + "\n\tjava Main test.txt -d 8\n\n"
            );

            System.exit(1);
        }

        /**
         * If the program was instantiated correctly start program
         * execution here.
         */
        else {

            /** Local Variables **/
            File file = new File(args[0]);
            String flag = args[1];
            int numProcessors = Integer.parseInt(args[2]);
            BufferedReader reader;
            String line;
            int numTasks;
            int[] taskArray;

            try {

                // Initialize local variables
                reader = new BufferedReader(new FileReader(file));
                numTasks = Integer.parseInt(reader.readLine());
                taskArray = new int[numTasks];

                // Populate taskArray from file data
                for (int i = 0; i < numTasks; i++) {
                    if ((line = reader.readLine()) != null) {
                        taskArray[i] = Integer.parseInt(line);
                    }
                }

                // Route program based on flag
                switch (flag) {
                    case "-b":
                        Algorithm.BruteForce(taskArray, numProcessors);
                        break;
                    case "-d":
                        Algorithm.DynamicProgramming(taskArray, numProcessors);
                        break;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}