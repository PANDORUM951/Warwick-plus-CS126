package stores;

import interfaces.IPerson;
import structures.*;

public class Person implements IPerson{
    private int id;
    private String name;
    private String profilePath;
    private Set<Integer> movieApperances;

    public Person(int id, String name, String profilePath){
        this.id = id;
        this.name = name;
        this.profilePath = profilePath;
        this.movieApperances = new Set<>();
    }
    
    public int getID() {
        return id;
    }
    public String getName() {
        return name;
    }
    public String getProfilePath() {
        return profilePath;
    }
    
    public Set<Integer> getMovieApperances() {
        return movieApperances;
    }

    public void setMovieApperances(Set<Integer> movieApperances) {
        this.movieApperances = movieApperances;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;               // Check if the current object is the same as the provided object
        if (!(obj instanceof Person)) return false; // Check if the provided object is not an instance of Person
        Person other = (Person) obj;                // Cast the object to Person for comparison
        return this.id == other.id;                 // Compare the id of both Person objects; if they are equal, return true
    }
}
