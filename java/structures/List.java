package structures;

public class List<T> {
    private Object[] elements;
    private int size;
    private static final int DEFAULT_CAPACITY = 8;

    /**
     * Default constructor that initializes the list with a default capacity.
     */
    public List() {
        this.elements = new Object[DEFAULT_CAPACITY]; // Initialize the list with the given capacity
        this.size = 0; // Start with an empty list
    }
    
    /**
     * Adds an element to the end of the list. If the list is full, it resizes the list.
     * 
     * @param element The element to add to the list.
     * @return {@code true} if the element was added successfully.
     */
    public boolean add(T element) {
        resize(); // Resize if necessary
        elements[size] = element; // Add the element at the end of the list
        size++; // Increment the size of the list
        return true;
    }
    /**
     * Sets the element at the specified index.
     * 
     * @param index The index where the element should be set.
     * @param element The element to set at the specified index.
     * @throws IndexOutOfBoundsException if the index is out of bounds.
     */
    public void set(int index, T element) {
        if (index >= size || index < 0) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }
        elements[index] = element; // Set the element at the specified index
    }
    
    /**
     * Gets the element at the specified index.
     * 
     * @param index The index of the element to retrieve.
     * @return The element at the specified index.
     * @throws IndexOutOfBoundsException if the index is out of bounds.
     */
    @SuppressWarnings("unchecked")
    public T get(int index) {
        if (index >= size || index < 0) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }
        return (T) elements[index]; // Return the element at the specified index
    }
    
    /**
     * Removes the first occurrence of the specified element from the list.
     * 
     * @param element The element to remove.
     * @return {@code true} if the element was removed, {@code false} if the element was not found.
     */
    public boolean remove(T element) {
        int index = indexOf(element); // Find the index of the element
        if (index == -1) return false; // If element is not found, return false
        // Shift elements to the left to fill the gap
        for (int i = index; i < size - 1; i++) {
            elements[i] = elements[i + 1];
        }
        elements[size - 1] = null; // Nullify the last element
        size--; // Decrement the size
        return true; // Return true indicating successful removal
    }
    
    /**
     * Returns the index of the first occurrence of the element, or -1 if the element is not found.
     * 
     * @param element The element to search for.
     * @return The index of the first occurrence of the element, or -1 if not found.
     */
    public int indexOf(T element) {
        for (int i = 0; i < size; i++) {
            if (elements[i].equals(element)) {
                return i; // Return the index if element is found
            }
        }
        return -1; // Return -1 if the element is not found
    }
    
    /**
     * Checks if the list contains the specified element.
     * 
     * @param element The element to check for.
     * @return {@code true} if the list contains the element, {@code false} otherwise.
     */
    public boolean contains(T element) {
        for (int i = 0; i < size(); i++) {
            if (get(i).equals(element)) {
                return true; // Return true if the element is found
            }
        }
        return false; // Return false if the element is not found
    }
    
    /**
     * Checks if the list is empty.
     * 
     * @return {@code true} if the list is empty, {@code false} otherwise.
     */
    public boolean isEmpty() {
        return size == 0; // The list is empty if the size is 0
    }
    
    /**
     * Returns the size of the list (i.e., the number of elements currently in the list).
     * 
     * @return The size of the list.
     */
    public int size() {
        return size; // Return the current size of the list
    }
    
    /**
     * Ensures there is enough capacity to add more elements. If the list is full, the capacity is doubled.
     */
    private void resize() {
        if (size == elements.length) { // Check if resizing is needed
            Object[] newElements = new Object[elements.length * 2]; // Create a new array with double capacity
            System.arraycopy(elements, 0, newElements, 0, size); // Copy elements to the new array
            elements = newElements; // Update the elements array to the new one
        }
    }
}
