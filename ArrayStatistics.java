import java.util.Scanner;

public class ArrayStatistics {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Get the size of the array from the user
        System.out.print("Enter the size of the array: ");
        int size = scanner.nextInt();

        // Create an array based on user input
        int[] numbers = new int[size];

        // Get the elements of the array from the user
        System.out.println("Enter the elements of the array:");
        for (int i = 0; i < size; i++) {
            System.out.print("Element " + (i + 1) + ": ");
            numbers[i] = scanner.nextInt();
        }

        // Call the method to calculate statistics
        double[] statistics = calculateStatistics(numbers);

        // Print the calculated statistics
        System.out.println("Statistics: ");
        System.out.println("Median: " + statistics[0]);
        System.out.println("Variance: " + statistics[1]);
        System.out.println("Standard Deviation: " + statistics[2]);
        System.out.println("Sum of Squares: " + statistics[3]);
    }

    // Method to calculate statistics for an array of integers
    public static double[] calculateStatistics(int[] array) {
        double[] statistics = new double[4];

        // Sort the array in ascending order
        sortAscending(array);

        // Calculate Median
        statistics[0] = calculateMedian(array);

        // Calculate Variance
        statistics[1] = calculateVariance(array);

        // Calculate Standard Deviation
        statistics[2] = Math.sqrt(statistics[1]);

        // Calculate Sum of Squares
        statistics[3] = calculateSumOfSquares(array);

        return statistics;
    }

    // Helper method to sort the array in ascending order using Bubble Sort
    private static void sortAscending(int[] array) {
        int n = array.length;
        boolean swapped;

        do {
            swapped = false;
            for (int i = 0; i < n - 1; i++) {
                if (array[i] > array[i + 1]) {
                    // Swap the elements if they are in the wrong order
                    int temp = array[i];
                    array[i] = array[i + 1];
                    array[i + 1] = temp;
                    swapped = true;
                }
            }
        } while (swapped);
    }

    // Helper method to calculate the median of a sorted array
    private static double calculateMedian(int[] sortedArray) {
        int n = sortedArray.length;
        if (n % 2 == 0) {
            return (sortedArray[n / 2 - 1] + sortedArray[n / 2]) / 2.0;
        } else {
            return sortedArray[n / 2];
        }
    }

    // Helper method to calculate the variance of an array
    private static double calculateVariance(int[] array) {
        double mean = calculateMean(array);
        double sumSquaredDifferences = 0;

        for (int value : array) {
            sumSquaredDifferences += Math.pow(value - mean, 2);
        }

        return sumSquaredDifferences / array.length;
    }

    // Helper method to calculate the mean of an array
    private static double calculateMean(int[] array) {
        int sum = 0;
        for (int value : array) {
            sum += value;
        }
        return (double) sum / array.length;
    }

    // Helper method to calculate the sum of squares of an array
    private static double calculateSumOfSquares(int[] array) {
        double sum = 0;
        for (int value : array) {
            sum += Math.pow(value, 2);
        }
        return sum;
    }
}
