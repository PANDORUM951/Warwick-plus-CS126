package utils;

import structures.*;

public class RadixSort {

    /**
     * Sorts a list of KeyValuePair<K, V> in descending order based on the value, assuming the value is an Integer.
     * This implementation uses Radix Sort, which works for non-negative integers only.
     * 
     * @param list The list of KeyValuePair objects to be sorted.
     * @param <K> The type of the key in KeyValuePair.
     * @param <V> The type of the value in KeyValuePair (must be positive integers).
     * @return A sorted list of KeyValuePair objects in descending order.
     */
    public static <K, V extends Number> List<KeyValuePair<K, V>> radixSort(List<KeyValuePair<K, V>> list) {
        if (list == null || list.size() == 0) return list;  // If list is null or empty, return as is.

        // Step 1: Find the maximum value to determine the number of digits
        int max = (Integer) list.get(0).getValue();
        for (int i = 1; i < list.size(); i++) {
            int value = (Integer) list.get(i).getValue();
            if (value > max) max = value;
        }

        int exp = 1;  // The exponent to divide the values for each digit (units, tens, hundreds, etc.)

        // Step 2: Perform counting sort for each digit (unit, tens, hundreds, etc.)
        while (max / exp > 0) {
            list = countingSortByDigitDescending(list, exp);  // Sort list based on the current digit
            exp *= 10;  // Move to the next higher digit place
        }

        return list;  // Return the sorted list
    }

    /**
     * Performs counting sort on a list of KeyValuePair objects based on the specified digit.
     * The sorting is done in descending order.
     * 
     * @param list The list of KeyValuePair objects to sort.
     * @param exp The exponent used to extract the digit (units, tens, etc.).
     * @param <K> The type of the key in KeyValuePair.
     * @param <V> The type of the value in KeyValuePair.
     * @return A sorted list of KeyValuePair objects based on the specified digit.
     */
    private static <K, V extends Number> List<KeyValuePair<K, V>> countingSortByDigitDescending(List<KeyValuePair<K, V>> list, int exp) {
        int n = list.size();
        List<KeyValuePair<K, V>> output = new List<>();
        for (int i = 0; i < n; i++) output.add(null);  // Fill output list with nulls first

        int[] count = new int[10];  // Array to store the count of digits (0-9)

        // Step 1: Count the occurrences of each digit at the current place value
        for (int i = 0; i < n; i++) {
            int digit = ((Integer) list.get(i).getValue() / exp) % 10;
            count[digit]++;
        }

        // Step 2: Accumulate the counts to get positions for descending order
        for (int i = 8; i >= 0; i--) {
            count[i] += count[i + 1];
        }

        // Step 3: Place the KeyValuePairs in the output list in sorted order
        for (int i = n - 1; i >= 0; i--) {
            int digit = ((Integer) list.get(i).getValue() / exp) % 10;
            output.set(--count[digit], list.get(i));
        }

        return output;  // Return the sorted list
    }
}