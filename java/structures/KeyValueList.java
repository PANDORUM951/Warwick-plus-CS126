package structures;

/**
 * A hash-based key-value data structure that supports insertion, retrieval,
 * deletion, and probing with double hashing for collision resolution.
 * 
 * @param <K> The type of keys maintained by this list.
 * @param <V> The type of mapped values.
 */
public class KeyValueList<K, V> {
    protected KeyValuePair<K, V>[] table; // Array for storing key-value pairs
    protected int size; // Number of key-value pairs in the table
    protected int capacity; // Maximum capacity of the table
    private static final KeyValuePair<?, ?> DELETED = new KeyValuePair<>(null, null); // Deleted instance for proper Hash Map functionality

    /**
     * Constructs a KeyValueList with the specified initial capacity.
     *
     * @param capacity The initial capacity of the underlying table.
     */
    @SuppressWarnings("unchecked")
    public KeyValueList(int capacity) {
        this.capacity = capacity; // Set the capacity of the table
        this.size = 0; // Initially, the table is empty
        this.table = new KeyValuePair[capacity]; // Initialize the table with the specified capacity
    }

    /**
     * Inserts or updates a key-value pair in the table.
     * Uses double hashing for collision resolution. If the key already exists,
     * the value is replaced. If the table is full, insertion fails.
     *
     * @param pair The key-value pair to insert or update.
     * @return {@code true} if the pair was inserted or updated successfully, {@code false} otherwise.
     */
    public boolean put(KeyValuePair<K, V> pair) {
        int capacity = table.length;
        int index = hash1(pair.getKey()); // First hash function to find the index
        int stepSize = hash2(pair.getKey()); // Second hash function for step size (collision resolution)
        int firstDeletedIndex = -1;

        // Search for an appropriate spot for the pair
        for (int i = 0; i < capacity; i++) {
            int probeIndex = (index + i * stepSize) % capacity; // Linear probe with step size
            if (table[probeIndex] == DELETED && firstDeletedIndex == -1) {
                firstDeletedIndex = probeIndex; // Mark first deleted spot
            } else if (table[probeIndex] == null) {
                // If an empty spot is found, insert the pair
                if (firstDeletedIndex != -1) table[firstDeletedIndex] = pair; // Place in deleted spot
                else table[probeIndex] = pair; // Place in available spot
                size++; // Increment the size
                return true;
            } else if (table[probeIndex].getKey().equals(pair.getKey())) {
                // Replace value if the key already exists
                table[probeIndex] = pair;
                return true;
            }
        }

        // If table is full but there was a deleted spot, use it for the pair
        if (firstDeletedIndex != -1) {
            table[firstDeletedIndex] = pair;
            size++;
            return true;
        }

        return false; // If no valid spot is found, return false
    }

    /**
     * Hashing function that calculates the index for a given key using the key's hash code.
     * 
     * @param key The key to hash.
     * @return The index in the hash table.
     */
    private int hash1(K key) {
        return Math.abs(key.hashCode()) % capacity; // Use hash code modulo capacity
    }
    
    /**
     * Secondary hashing function used for collision resolution (double hashing).
     * 
     * @param key The key to hash.
     * @return The step size for probing.
     */
    private int hash2(K key) {
        return 1 + (Math.abs(key.hashCode()) % (capacity - 1)); // Calculate step size
    }

    /**
     * Retrieves the key-value pair for a given key.
     * 
     * @param key The key whose value is to be retrieved.
     * @return The KeyValuePair associated with the key, or {@code null} if not found.
     */
    public KeyValuePair<K, V> get(K key) {
        int capacity = table.length;
        int index = hash1(key); // Hash to get the index
        int stepSize = hash2(key); // Step size for collision handling

        // Search for the key in the table
        for (int i = 0; i < capacity; i++) {
            int probeIndex = (index + i * stepSize) % capacity;
            if (table[probeIndex] == null) return null; // Key not found
            if (table[probeIndex] == DELETED) continue; // Skip deleted entries
            if (table[probeIndex].getKey().equals(key)) return table[probeIndex]; // Return the pair if found
        }
        return null; // Return null if key not found
    }

    /**
     * Checks if the table contains a key-value pair with the given key.
     * 
     * @param key The key to check for existence.
     * @return {@code true} if the key exists in the table, {@code false} otherwise.
     */
    public boolean containsKey(K key) {
        return get(key) != null; // Simply checks if the key exists by calling get()
    }     

    /**
     * Removes the key-value pair associated with the given key by marking it as deleted.
     * 
     * @param key The key whose key-value pair should be removed.
     * @return {@code true} if the pair was successfully removed, {@code false} otherwise.
     */
    @SuppressWarnings("unchecked")
    public boolean remove(K key) {
        int capacity = table.length;
        int index = hash1(key); // Get the index using the first hash function
        int stepSize = hash2(key); // Get step size for collision resolution

        // Search and remove the key-value pair
        for (int i = 0; i < capacity; i++) {
            int probeIndex = (index + i * stepSize) % capacity;
            if (table[probeIndex] == null) return false; // Key not found
            if (table[probeIndex].getKey().equals(key)) {
                table[probeIndex] = (KeyValuePair<K, V>) DELETED; // Mark as deleted
                size--; // Decrease the size
                return true;
            }
        }
        return false; // Return false if key not found
    }

    /**
     * Returns the number of key-value pairs currently stored in the table.
     * 
     * @return The current size of the table.
     */
    public int size() {
        return size; 
    }

    /**
     * Returns the current capacity of the table.
     * 
     * @return The capacity of the internal storage array.
     */
    public int capacity() {
        return capacity; 
    }
}
