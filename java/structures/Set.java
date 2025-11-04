package structures;

public class Set<T> extends List<T> {

    /**
     * Adds an element to the set only if it doesn't already exist in the set.
     * It checks for the element's existence and ensures no duplicates.
     * 
     * @param element The element to add to the set.
     * @return {@code true} if the element was added, {@code false} if the element already exists in the set.
     */
    @Override
    public boolean add(T element) {
        if (!contains(element)) { // Check if the element is not already in the set
            super.add(element);    // Call the superclass's add method to add the element
            return true;           // Return true indicating the element was added
        }
        return false;               // Return false if the element already exists
    }
}
