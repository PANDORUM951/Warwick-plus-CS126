package stores;

import structures.*;
import utils.RadixSort;
import interfaces.ICredits;

public class Credits implements ICredits{
    Stores stores;
    HashMap<Integer, Credit> creditMap;
    Set<Person> uniqueCast;
    Set<Person> uniqueCrew;

    /**
     * The constructor for the Credits data store. This is where you should
     * initialise your data structures.
     * 
     * @param stores An object storing all the different key stores, 
     *               including itself
     */
    public Credits (Stores stores) {
        this.stores = stores;
        this.creditMap = new HashMap<>();
        this.uniqueCast = new Set<>(); // Initialise Set
        this.uniqueCrew = new Set<>(); // Initialise Set
    }

    /**
     * Adds data about the people who worked on a given film. The movie ID should be
     * unique
     * 
     * @param cast An array of all cast members that starred in the given film
     * @param crew An array of all crew members that worked on a given film
     * @param id   The (unique) movie ID
     * @return TRUE if the data able to be added, FALSE otherwise
     */
    @Override
    public boolean add(CastCredit[] cast, CrewCredit[] crew, int id) {
        // Check if the movie ID already exists
        if (creditMap.containsKey(id)) return false; // Movie ID already exists, return false

        // Add movie to movieMap
        Credit credits = new Credit (cast, crew, id); // Create a new Movie object
        creditMap.put(id, credits); // Add the movie to the map using the movie ID as the key

        // Add cast to uniqueCast
        CastCredit[] movieCast = credits.getCast();
        for (CastCredit castMember : movieCast) {
            Person person = new Person(castMember.getID(), castMember.getName(), castMember.getProfilePath());
            uniqueCast.add(person);
        }

        // Add crew to uniqueCrew
        CrewCredit[] movieCrew = credits.getCrew();
        for (CrewCredit crewMember : movieCrew) {
            Person person = new Person(crewMember.getID(), crewMember.getName(), crewMember.getProfilePath());
            uniqueCrew.add(person);
        }

        return true;  // Successfully added
    }

    /**
     * Remove a given films data from the data structure
     * 
     * @param id The movie ID
     * @return TRUE if the data was removed, FALSE otherwise
     */
    @Override
    public boolean remove(int id) {
        // Check if the movie exists in the credit map before attempting removal
        if (creditMap.containsKey(id)) {
            creditMap.remove(id);  // Remove the movie's credits from the map
            return true;  // Removal successful
        }
        return false;  // Movie ID was not found in the map
    }

    /**
     * Gets all the cast members for a given film
     * 
     * @param filmID The movie ID
     * @return An array of CastCredit objects, one for each member of cast that is 
     *         in the given film. The cast members should be in "order" order. If
     *         there is no cast members attached to a film, or the film cannot be 
     *         found in Credits, then return an empty array
     */
    @Override
    public CastCredit[] getFilmCast(int filmID) {
        // If the film ID does not exist in the map, return an empty array
        if (!creditMap.containsKey(filmID)) return new CastCredit[0];
    
        // Retrieve the credits object associated with the film
        Credit credits = creditMap.get(filmID);
        CastCredit[] cast = credits.getCast();
    
        // If there are no cast members associated, return an empty array
        if (cast == null) return new CastCredit[0];
    
        // Create a separate array to avoid modifying the original cast array
        CastCredit[] orderedCast = new CastCredit[cast.length];
        System.arraycopy(cast, 0, orderedCast, 0, cast.length);
    
        // Perform Insertion Sort based on the "order" field of each CastCredit
        for (int i = 1; i < orderedCast.length; i++) {
            CastCredit key = orderedCast[i];  // Element to insert into the sorted section
            int j = i - 1;
    
            // Shift larger elements to the right to make space for the key
            while (j >= 0 && orderedCast[j].getOrder() > key.getOrder()) {
                orderedCast[j + 1] = orderedCast[j];
                j--;
            }
            orderedCast[j + 1] = key;  // Place key into its correct sorted position
        }
    
        return orderedCast;
    }

    /**
     * Gets all the crew members for a given film
     * 
     * @param filmID The movie ID
     * @return An array of CrewCredit objects, one for each member of crew that is
     *         in the given film. The crew members should be in "id" order (not "elementID"). If there 
     *         is no crew members attached to a film, or the film cannot be found in Credits, 
     *         then return an empty array
     */
    @Override
    public CrewCredit[] getFilmCrew(int filmID) {
        // If the film ID does not exist, return an empty array
        if (!creditMap.containsKey(filmID)) return new CrewCredit[0];
    
        // Retrieve the credits object associated with the film
        Credit credits = creditMap.get(filmID);
        CrewCredit[] crew = credits.getCrew();
    
        // Return an empty array if there are no crew members
        if (crew == null) return new CrewCredit[0];
    
        // Make a defensive copy to avoid mutating the original crew array
        CrewCredit[] orderedCrew = new CrewCredit[crew.length];
        System.arraycopy(crew, 0, orderedCrew, 0, crew.length);
    
        // Perform Insertion Sort based on the "id" field of each CrewCredit
        for (int i = 1; i < orderedCrew.length; i++) {
            CrewCredit key = orderedCrew[i];  // Current element to position
            int j = i - 1;
    
            // Shift elements that have a larger ID to the right
            while (j >= 0 && orderedCrew[j].getID() > key.getID()) {
                orderedCrew[j + 1] = orderedCrew[j];
                j--;
            }
            orderedCrew[j + 1] = key;  // Insert the current crew member in the correct spot
        }
    
        return orderedCrew;
    }

    /**
     * Gets the number of cast that worked on a given film
     * 
     * @param filmID The movie ID
     * @return The number of cast member that worked on a given film. If the film
     *         cannot be found in Credits, then return -1
     */
    @Override
    public int sizeOfCast(int filmID) {
        // Check if the film exists in the creditMap.
        if (!creditMap.containsKey(filmID)) return -1;

        // Retrieve the Credit object associated with this film.
        Credit credits = creditMap.get(filmID);
        CastCredit[] cast = credits.getCast();

        // If the cast array is not null, return its length; otherwise, return 0.
        return cast != null ? cast.length : 0;
    }

    /**
     * Gets the number of crew that worked on a given film
     * 
     * @param filmID The movie ID
     * @return The number of crew member that worked on a given film. If the film
     *         cannot be found in Credits, then return -1
     */
    @Override
    public int sizeOfCrew(int filmID) {
        // Check if the film exists in the creditMap.
        if (!creditMap.containsKey(filmID)) return -1;

        // Retrieve the Credit object associated with this film.
        Credit credits = creditMap.get(filmID);
        CrewCredit[] crew = credits.getCrew();

        // If the crew array is not null, return its length; otherwise, return 0.
        return crew != null ? crew.length : 0;
    }

    /**
     * Gets a list of all unique cast members present in the data structure
     * 
     * @return An array of all unique cast members as Person objects. If there are 
     *         no cast members, then return an empty array
     */
    @Override
    public Person[] getUniqueCast() {
        // Create an array to hold the unique cast members
        Person[] uniqueCastArray = new Person[uniqueCast.size()];
    
        // Populate the array with cast members from the uniqueCast collection
        for (int i = 0; i < uniqueCast.size(); i++) {
            uniqueCastArray[i] = uniqueCast.get(i);  // Add each unique cast member to the array
        }
    
        // Return the array of unique cast members
        return uniqueCastArray;
    }

    /**
     * Gets a list of all unique crew members present in the data structure
     * 
     * @return An array of all unique crew members as Person objects. If there are
     *         no crew members, then return an empty array
     */
    @Override
    public Person[] getUniqueCrew() {
        // Create an array to hold the unique crew members
        Person[] uniqueCrewArray = new Person[uniqueCrew.size()];

        // Populate the array with crew members from the uniqueCrew collection
        for (int i = 0; i < uniqueCrew.size(); i++) {
            uniqueCrewArray[i] = uniqueCrew.get(i);  // Add each unique crew member to the array
        }

        // Return the array of unique crew members
        return uniqueCrewArray;
    }

    /**
     * Get all the cast members that have the given string within their name
     * 
     * @param cast The string that needs to be found
     * @return An array of unique Person objects of all cast members that have the 
     *         requested string in their name. If there are no matches, return an 
     *         empty array
     */
    @Override
    public Person[] findCast(String cast) {
        Set<Person> matchingCast = new Set<>();
        Person[] allCast = getUniqueCast();  // Fetch all known cast members.

        // Iterate through the list and check for matches.
        for (int i = 0; i < allCast.length; i++) {
            String name = allCast[i].getName();
            if (name != null && name.toLowerCase().contains(cast.toLowerCase())) {
                matchingCast.add(allCast[i]);
            }
        }

        // Convert the results into an array for output.
        Person[] result = new Person[matchingCast.size()];
        for (int i = 0; i < matchingCast.size(); i++) {
            result[i] = matchingCast.get(i);
        }

        return result;
    }

    /**
     * Get all the crew members that have the given string within their name
     * 
     * @param crew The string that needs to be found
     * @return An array of unique Person objects of all crew members that have the 
     *         requested string in their name. If there are no matches, return an 
     *         empty array
     */
    @Override
    public Person[] findCrew(String crew) {
        Set<Person> matchingCrew = new Set<>();
        Person[] allCrew = getUniqueCrew();  // Fetch all known crew members.

        // Iterate through the list and check for matches.
        for (int i = 0; i < allCrew.length; i++) {
            String name = allCrew[i].getName();
            if (name != null && name.toLowerCase().contains(crew.toLowerCase())) {
                matchingCrew.add(allCrew[i]);
            }
        }

        // Convert the results into an array for output.
        Person[] result = new Person[matchingCrew.size()];
        for (int i = 0; i < matchingCrew.size(); i++) {
            result[i] = matchingCrew.get(i);
        }

        return result;
    }

    /**
     * Gets the Person object corresponding to the cast ID
     * 
     * @param castID The cast ID of the person to be found
     * @return The Person object corresponding to the cast ID provided. 
     *         If a person cannot be found, then return null
     */
    @Override
    public Person getCast(int castID) {
        for (int i = 0; i < uniqueCast.size(); i++) {
            if (uniqueCast.get(i).getID() == castID) return uniqueCast.get(i);
        }
        return null; // No matching cast member found
    }
    
    /**
     * Gets the Person object corresponding to the crew ID
     * 
     * @param crewID The crew ID of the person to be found
     * @return The Person object corresponding to the crew ID provided. 
     *         If a person cannot be found, then return null
     */
    @Override
    public Person getCrew(int crewID) {
        for (int i = 0; i < uniqueCrew.size(); i++) {
            if (uniqueCrew.get(i).getID() == crewID) return uniqueCrew.get(i);  // Return the crew member if found
        }
        return null;  // No matching crew member found
    }

    
    /**
     * Get an array of film IDs where the cast member has starred in
     * 
     * @param castID The cast ID of the person
     * @return An array of all the films the member of cast has starred
     *         in. If there are no films attached to the cast member, 
     *         then return an empty array
     */
    @Override
    public int[] getCastFilms(int castID) {
        List<Integer> films = new List<>();
        List<Credit> creditsList = creditMap.values();

        // Loop through each credit in the creditMap
        for (int i = 0; i < creditsList.size(); i++) {
            Credit credit = creditsList.get(i);
            CastCredit[] castArray = credit.getCast();

            // Check if the current credit contains the specified cast member
            for (int j = 0; j < castArray.length; j++) {
                if (castArray[j].getID() == castID) {
                    films.add(credit.getId());  // Film ID from Credit
                    break;
                }
            }
        }

        // Convert list of film IDs to array
        int[] result = new int[films.size()];
        for (int i = 0; i < films.size(); i++) {
            result[i] = films.get(i);
        }

        return result;
    }

    /**
     * Get an array of film IDs where the crew member has starred in
     * 
     * @param crewID The crew ID of the person
     * @return An array of all the films the member of crew has starred
     *         in. If there are no films attached to the crew member, 
     *         then return an empty array
     */
    @Override
    public int[] getCrewFilms(int crewID) {
        List<Integer> films = new List<>();
        List<Credit> creditsList = creditMap.values();

        // Loop through each credit in the creditMap
        for (int i = 0; i < creditsList.size(); i++) {
            Credit credit = creditsList.get(i);
            CrewCredit[] crewArray = credit.getCrew();

            // Check if the current credit contains the specified crew member
            for (int j = 0; j < crewArray.length; j++) {
                if (crewArray[j].getID() == crewID) {
                    films.add(credit.getId());  // Film ID from Credit
                    break;
                }
            }
        }

        // Convert list of film IDs to array
        int[] result = new int[films.size()];
        for (int i = 0; i < films.size(); i++) {
            result[i] = films.get(i);
        }

        return result;
    }

    /**
     * Get the films that this cast member stars in (in the top 3 cast
     * members/top 3 billing). This is determined by the order field in
     * the CastCredit class
     * 
     * @param castID The cast ID of the cast member to be searched for
     * @return An array of film IDs where the the cast member stars in.
     *         If there are no films where the cast member has starred in,
     *         or the cast member does not exist, return an empty array
     */
    @Override
    public int[] getCastStarsInFilms(int castID) {
        List<Integer> films = new List<>();
        List<Credit> creditsList = creditMap.values();

        // Loop through each credit in the creditMap
        for (int i = 0; i < creditsList.size(); i++) {
            Credit credit = creditsList.get(i);
            CastCredit[] castArray = credit.getCast();

            // Check if the cast member is in the top 3 billing (order < 4)
            for (int j = 0; j < castArray.length; j++) {
                if (castArray[j].getID() == castID && castArray[j].getOrder() < 4) {
                    films.add(credit.getId());  // Add film ID if order is in top 3
                    break;  // No need to continue once found in this film
                }
            }
        }

        // Convert list of film IDs to array
        int[] result = new int[films.size()];
        for (int i = 0; i < films.size(); i++) {
            result[i] = films.get(i);
        }

        return result;
    }
    
    /**
     * Get Person objects for cast members who have appeared in the most
     * films. If the cast member has multiple roles within the film, then
     * they would get a credit per role played. For example, if a cast
     * member performed as 2 roles in the same film, then this would count
     * as 2 credits. The list should be ordered by the highest to lowest number of credits.
     * 
     * @param numResults The maximum number of elements that should be returned
     * @return An array of Person objects corresponding to the cast members
     *         with the most credits, ordered by the highest number of credits.
     *         If there are less cast members that the number required, then the
     *         list should be the same number of cast members found.
     */
    @Override
    public Person[] getMostCastCredits(int numResults) {
        Set<Person> sortedCast = new Set<>();
        List<KeyValuePair<Person, Integer>> allPairPersonAppearances = new List<>();

        // For each person in the cast, count the number of appearances
        for (int p = 0; p < uniqueCast.size(); p++) {
            Person person = uniqueCast.get(p);
            int personId = person.getID();
            int castCredits = 0;
            int[] moviesAppearedIn = getCastFilms(personId); // Films the person has appeared in

            // Loop through films the person appeared in and count their roles
            for (int m = 0; m < moviesAppearedIn.length; m++) {
                int movieId = moviesAppearedIn[m];
                CastCredit[] allCastMovie = getFilmCast(movieId); // Get all cast for a specific movie

                for (int role = 0; role < allCastMovie.length; role++){
                    if (allCastMovie[role].getID() == personId) castCredits++; // Count roles in the movie
                }
            }
            KeyValuePair<Person, Integer> pairPersonAppearances = new KeyValuePair<>(person, castCredits);
            allPairPersonAppearances.add(pairPersonAppearances);
        }

        // Sort the cast members by number of credits using RadixSort
        List<KeyValuePair<Person, Integer>> sortedList = RadixSort.radixSort(allPairPersonAppearances);

        // Collect top 'numResults' cast members with the most credits
        for (int i = 0; i < numResults && i < sortedList.size(); i++) {
            if (sortedList.get(i) != null) {
                sortedCast.add(sortedList.get(i).getKey());  // Add to the sorted cast
            } else break;
        }

        // Convert sorted cast to an array and return
        Person[] sortedCastArray = new Person[sortedCast.size()];
        for (int i = 0; i < sortedCast.size(); i++) {
            sortedCastArray[i] = sortedCast.get(i);
        }
    
        return sortedCastArray;
    }


    /**
     * Get the number of credits for a given cast member. If the cast member has
     * multiple roles within the film, then they would get a credit per role
     * played. For example, if a cast member performed as 2 roles in the same film,
     * then this would count as 2 credits.
     * 
     * @param castID A cast ID representing the cast member to be found
     * @return The number of credits the given cast member has. If the cast member
     *         cannot be found, return -1
     */
    @Override
    public int getNumCastCredits(int castID) {
        int count = 0;
        boolean found = false;

        List<Credit> creditsList = creditMap.values();
        for (int i = 0; i < creditsList.size(); i++) {
            Credit credit = creditsList.get(i);
            CastCredit[] castArray = credit.getCast();

            // Check if the cast member appears in the current credit
            for (int j = 0; j < castArray.length; j++) {
                if (castArray[j].getID() == castID) {
                    count++; // Increment count for each role the cast member appears in
                    found = true;
                }
            }
        }

        return found ? count : -1; // Return the count or -1 if not found
    }

    /**
     * Gets the number of films stored in this data structure
     * 
     * @return The number of films in the data structure
     */
    @Override
    public int size() {
        return creditMap.size();
    }
}
