package structures;

public class HashMap<K, V> {
    private KeyValueList<K, V> keyValueList;
    private static final float LOAD_FACTOR = 0.7f; // Load factor for resizing
    private static final KeyValuePair<?, ?> DELETED = new KeyValuePair<>(null, null); // Deleted instance for proper Hash Map functionality


    public HashMap() {
        this.keyValueList = new KeyValueList<K, V>(11); // Start with a prime number
    }

    /**
     * Resizes the hash table if the load factor exceeds the threshold (0.7).
     * The table is resized to the next prime number greater than double the current capacity.
     * 
     * @return {@code true} if the resize was successful, {@code false} otherwise.
     */
    private boolean resize() {
        if ((float) keyValueList.size() / keyValueList.capacity() > LOAD_FACTOR) {
            int newCapacity = nextPrime(keyValueList.capacity() * 2); // Find the next prime number as new capacity
            KeyValuePair<K, V>[] oldTable = keyValueList.table;
            keyValueList.table = new KeyValuePair[newCapacity]; // Allocate new table
            keyValueList.capacity = newCapacity; // Update capacity
            keyValueList.size = 0; // Reset size

            // Rehash all elements into the new table
            for (KeyValuePair<K, V> entry : oldTable) {
                if (entry != null) {
                    put(entry.getKey(), entry.getValue()); // Reinsert entries into the resized table
                }
            }
            return true; // Return true to indicate successful resize
        }
        return false; // Return false if resizing is not necessary
    }

    /**
     * Finds the next prime number greater than the given number.
     * 
     * @param num The number to find the next prime after.
     * @return The next prime number.
     */
    private int nextPrime(int num) {
        int candidate = num + 1;
        while (!isPrime(candidate)) {
            candidate++; // Increment until a prime is found
        }
        return candidate;
    }
    
    /**
     * Checks if the given number is a prime number.
     * 
     * @param number The number to check.
     * @return {@code true} if the number is prime, {@code false} otherwise.
     */
    private boolean isPrime(int number) {
        if (number <= 1) return false; // Numbers <= 1 are not prime
        if (number == 2) return true; // 2 is prime
        if (number % 2 == 0) return false; // Eliminate even numbers
        int sqrt = (int) Math.sqrt(number); // Check divisibility up to the square root of the number
        for (int i = 3; i <= sqrt; i += 2) {
            if (number % i == 0) return false; // Return false if divisible by any number
        }
        return true; // Return true if prime
    }
    
    /**
     * Inserts a key-value pair into the map.
     * If the map needs resizing (based on the load factor), it will be resized before inserting.
     *
     * @param key The key to be inserted into the map.
     * @param value The value associated with the key.
     */
    public void put(K key, V value) {
        resize();  // Resize if needed
        KeyValuePair<K, V> pair = new KeyValuePair<>(key, value);
        keyValueList.put(pair);
    }

    /**
     * Retrieves the value associated with the given key.
     *
     * @param key The key whose associated value is to be retrieved.
     * @return The value associated with the key, or null if the key does not exist in the map.
     */
    public V get(K key) {
        KeyValuePair<K, V> pair = keyValueList.get(key);
        return pair != null ? pair.getValue() : null;
    }

    /**
     * Checks if the map contains the specified key.
     *
     * @param key The key to check for existence in the map.
     * @return {@code true} if the map contains the key, otherwise {@code false}.
     */
    public boolean containsKey(K key) {
        return keyValueList.containsKey(key);
    }

    /**
     * Removes the key-value pair associated with the specified key from the map.
     *
     * @param key The key whose associated key-value pair is to be removed.
     */
    public void remove(K key) {
        keyValueList.remove(key);
    }

    /**
     * Returns the current number of key-value pairs in the map.
     * This is equivalent to the size of the map.
     *
     * @return The number of key-value pairs in the map.
     */
    public int size() {
        return keyValueList.size();
    }

    /**
     * Returns the current capacity of the map (the number of buckets in the hash table).
     *
     * @return The number of buckets in the hash table.
     */
    public int capacity() {
        return keyValueList.capacity();
    }

    /**
     * Returns a list of all the keys present in the map.
     * The list will contain only the keys that are not null or deleted.
     *
     * @return A list of all the keys in the map.
     */
    public List<K> keys() {
        List<K> list = new List<>();  // Create a new list to store the keys
        // Iterate over the entire table of key-value pairs
        for (KeyValuePair<K, V> pair : keyValueList.table) {
            // If the pair is not null and not deleted, add the key to the list
            if (pair != null && pair != DELETED) {
                list.add(pair.getKey());  // Add the key of the current key-value pair to the list
            }
        }
        return list;  // Return the list of keys
    }

    /**
     * Returns a list of all the values present in the map.
     * The list will contain only the values that are not associated with a null or deleted key.
     *
     * @return A list of all the values in the map.
     */
    public List<V> values() {
        List<V> list = new List<>();  // Create a new list to store the values
        // Iterate over the entire table of key-value pairs
        for (KeyValuePair<K, V> pair : keyValueList.table) {
            // If the pair is not null and not deleted, add the value to the list
            if (pair != null && pair != DELETED) {
                list.add(pair.getValue());  // Add the value of the current key-value pair to the list
            }
        }
        return list;  // Return the list of values
    }

}
