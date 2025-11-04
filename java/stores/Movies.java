package stores;

import java.time.LocalDate;

import interfaces.IMovies;
import structures.*;

public class Movies implements IMovies{
    Stores stores;
    HashMap<Integer, Movie> movieMap;
    HashMap<Integer, Collection> collectionMap;

    /**
     * The constructor for the Movies data store. This is where you should
     * initialise your data structures.
     * @param stores An object storing all the different key stores,
     *               including itself
     */
    public Movies(Stores stores) {
        this.stores = stores;
        this.movieMap = new HashMap<>(); // Initialise movieMap
        this.collectionMap = new HashMap<>(); // Initialise collectionMap
    }

    /**
     * Adds data about a film to the data structure
     * 
     * @param id               The unique ID for the film
     * @param title            The English title of the film
     * @param originalTitle    The original language title of the film
     * @param overview         An overview of the film
     * @param tagline          The tagline for the film (empty string if there is no
     *                         tagline)
     * @param status           Current status of the film
     * @param genres           An array of Genre objects related to the film
     * @param release          The release date for the film
     * @param budget           The budget of the film in US Dollars
     * @param revenue          The revenue of the film in US Dollars
     * @param languages        An array of ISO 639 language codes for the film
     * @param originalLanguage An ISO 639 language code for the original language of
     *                         the film
     * @param runtime          The runtime of the film in minutes
     * @param homepage         The URL to the homepage of the film
     * @param adult            Whether the film is an adult film
     * @param video            Whether the film is a "direct-to-video" film
     * @param poster           The unique part of the URL of the poster (empty if
     *                         the URL is not known)
     * @return TRUE if the data able to be added, FALSE otherwise
     */
    @Override
    public boolean add(int id, String title, String originalTitle, String overview, String tagline, String status, Genre[] genres, LocalDate release, long budget, long revenue, String[] languages, String originalLanguage, double runtime, String homepage, boolean adult, boolean video, String poster) {
        // Check if the movie ID already exists in the map
        if (movieMap.containsKey(id)) {
            return false;  // Return false if movie already exists
        }

        // Create a new Movie object with the given details
        Movie movie = new Movie(id, title, originalTitle, overview, tagline, status, genres, release, budget, revenue, languages, originalLanguage, runtime, homepage, adult, video, poster);

        // Add the new movie to the movieMap
        movieMap.put(id, movie);

        // Return true to indicate that the movie was successfully added
        return true;
    }


    /**
     * Removes a film from the data structure, and any data
     * added through this class related to the film
     * 
     * @param id The film ID
     * @return TRUE if the film has been removed successfully, FALSE otherwise
     */
    @Override
    public boolean remove(int id) {
        // Check if the movie exists in the map
        if (movieMap.containsKey(id)) {
            // Remove the movie from the movieMap
            movieMap.remove(id);
            return true;  // Successfully removed the movie
        }
        return false;  // Movie not found in the map
    }


    /**
     * Gets all the IDs for all films
     * 
     * @return An array of all film IDs stored
     */
    @Override
    public int[] getAllIDs() {
        // Retrieve the keys (movie IDs) from the movieMap as a list
        List<Integer> keyList = movieMap.keys();  // Get the list of all movie IDs

        // Create an array to hold the movie IDs
        int[] ids = new int[keyList.size()];

        // Convert the list of Integer objects to an array of primitive ints
        for (int i = 0; i < keyList.size(); i++) {
            ids[i] = keyList.get(i);  // Copy each movie ID into the array
        }

        // Return the array of movie IDs
        return ids;
    }


    /**
     * Finds the film IDs of all films released within a given range. If a film is
     * released either on the start or end dates, then that film should not be
     * included
     * 
     * @param start The start point of the range of dates
     * @param end   The end point of the range of dates
     * @return An array of film IDs that were released between start and end
     */
    @Override
    public int[] getAllIDsReleasedInRange(LocalDate start, LocalDate end) {
        // List to store valid movie IDs that satisfy the date range condition
        List<Integer> validIDs = new List<>();

        // Retrieve all movie IDs from the movieMap
        List<Integer> allIDs = movieMap.keys();

        // Iterate through each movie ID to check the release date
        for (int i = 0; i < allIDs.size(); i++) {
            Integer movieID = allIDs.get(i);

            // Get the movie object by its ID
            Movie movie = movieMap.get(movieID);

            // Retrieve the release date of the movie
            LocalDate releaseDate = movie.getRelease();

            // Check if the release date is within the range
            if (releaseDate != null && releaseDate.isAfter(start) && releaseDate.isBefore(end)) {
                validIDs.add(movieID);  // Add valid movie ID to the list
            }
        }

        // Convert the list of valid movie IDs to an array
        int[] idsArray = new int[validIDs.size()];
        for (int i = 0; i < validIDs.size(); i++) {
            idsArray[i] = validIDs.get(i);  // Copy valid IDs into the array
        }

        // Return the array of movie IDs
        return idsArray;
    }


    /**
     * Gets the title of a particular film, given the ID number of that film
     * 
     * @param id The movie ID
     * @return The title of the requested film. If the film cannot be found, then
     *         return null
     */
    @Override
    public String getTitle(int id) {
        // Retrieve the movie object using its ID
        Movie movie = movieMap.get(id);
        
        // Return the title if the movie exists, otherwise return null
        return (movie != null) ? movie.getTitle() : null;
    }


    /**
     * Gets the original title of a particular film, given the ID number of that
     * film
     * 
     * @param id The movie ID
     * @return The original title of the requested film. If the film cannot be
     *         found, then return null
     */
    @Override
    public String getOriginalTitle(int id) {
        // Retrieve the movie object using its ID
        Movie movie = movieMap.get(id);
        
        // Return the original title if the movie exists, otherwise return null
        return (movie != null) ? movie.getOriginalTitle() : null;
    }


    /**
     * Gets the overview of a particular film, given the ID number of that film
     * 
     * @param id The movie ID
     * @return The overview of the requested film. If the film cannot be found, then
     *         return null
     */
    @Override
    public String getOverview(int id) {
        // Retrieve the movie object using its ID
        Movie movie = movieMap.get(id);
        
        // Return the overview if the movie exists, otherwise return null
        return (movie != null) ? movie.getOverview() : null;
    }


    /**
     * Gets the tagline of a particular film, given the ID number of that film
     * 
     * @param id The movie ID
     * @return The tagline of the requested film. If the film cannot be found, then
     *         return null
     */
    @Override
    public String getTagline(int id) {
        // Retrieve the movie object using its ID
        Movie movie = movieMap.get(id);
        
        // Return the tagline if the movie exists, otherwise return null
        return (movie != null) ? movie.getTagline() : null;
    }


    /**
     * Gets the status of a particular film, given the ID number of that film
     * 
     * @param id The movie ID
     * @return The status of the requested film. If the film cannot be found, then
     *         return null
     */
    @Override
    public String getStatus(int id) {
        // Retrieve the movie object from the movie map using the provided movie ID
        Movie movie = movieMap.get(id);

        // If the movie exists, return its status; otherwise, return null
        return (movie != null) ? movie.getStatus() : null;
    }

    /**
     * Gets the genres of a particular film, given the ID number of that film
     * 
     * @param id The movie ID
     * @return The genres of the requested film. If the film cannot be found, then
     *         return null
     */
    @Override
    public Genre[] getGenres(int id) {
        // Retrieve the movie object from the movie map using the provided movie ID
        Movie movie = movieMap.get(id);

        // If the movie exists, return its genres; otherwise, return null
        return (movie != null) ? movie.getGenres() : null;
    }

    /**
     * Gets the release date of a particular film, given the ID number of that film
     * 
     * @param id The movie ID
     * @return The release date of the requested film. If the film cannot be found,
     *         then return null
     */
    @Override
    public LocalDate getRelease(int id) {
        // Retrieve the movie object from the movie map using the provided movie ID
        Movie movie = movieMap.get(id);

        // If the movie exists, return its release date; otherwise, return null
        return (movie != null) ? movie.getRelease() : null;
    }

    /**
     * Gets the budget of a particular film, given the ID number of that film
     * 
     * @param id The movie ID
     * @return The budget of the requested film. If the film cannot be found, then
     *         return -1
     */
    @Override
    public long getBudget(int id) {
        // Retrieve the movie object from the movie map using the provided movie ID
        Movie movie = movieMap.get(id);

        // If the movie exists, return its budget; otherwise, return -1
        return (movie != null) ? movie.getBudget() : -1;
    }

    /**
     * Gets the revenue of a particular film, given the ID number of that film
     * 
     * @param id The movie ID
     * @return The revenue of the requested film. If the film cannot be found, then
     *         return -1
     */
    @Override
    public long getRevenue(int id) {
        // Retrieve the movie object from the movie map using the provided movie ID
        Movie movie = movieMap.get(id);

        // If the movie exists, return its revenue; otherwise, return -1
        return (movie != null) ? movie.getRevenue() : -1;
    }

    /**
     * Gets the languages of a particular film, given the ID number of that film
     * 
     * @param id The movie ID
     * @return The languages of the requested film. If the film cannot be found,
     *         then return null
     */
    @Override
    public String[] getLanguages(int id) {
        // Retrieve the movie object from the movie map using the provided movie ID
        Movie movie = movieMap.get(id);

        // If the movie exists, return its languages; otherwise, return null
        return (movie != null) ? movie.getLanguages() : null;
    }

    /**
     * Gets the original language of a particular film, given the ID number of that
     * film
     * 
     * @param id The movie ID
     * @return The original language of the requested film. If the film cannot be
     *         found, then return null
     */
    @Override
    public String getOriginalLanguage(int id) {
        // Retrieve the movie object from the movie map using the provided movie ID
        Movie movie = movieMap.get(id);

        // If the movie exists, return its original language; otherwise, return null
        return (movie != null) ? movie.getOriginalLanguage() : null;
    }

    /**
     * Gets the runtime of a particular film, given the ID number of that film
     * 
     * @param id The movie ID
     * @return The runtime of the requested film. If the film cannot be found, then
     *         return -1.0d
     */
    @Override
    public double getRuntime(int id) {
        // Retrieve the movie object from the movie map using the provided movie ID
        Movie movie = movieMap.get(id);

        // If the movie exists, return its runtime; otherwise, return -1.0d
        return (movie != null) ? movie.getRuntime() : -1.0d;
    }

    /**
     * Gets the homepage of a particular film, given the ID number of that film
     * 
     * @param id The movie ID
     * @return The homepage of the requested film. If the film cannot be found, then
     *         return null
     */
    @Override
    public String getHomepage(int id) {
        // Retrieve the movie object from the movie map using the provided movie ID
        Movie movie = movieMap.get(id);

        // If the movie exists, return its homepage URL; otherwise, return null
        return (movie != null) ? movie.getHomepage() : null;
    }

    /**
     * Gets wether a particular film is classed as "adult", given the ID number of
     * that film
     * 
     * @param id The movie ID
     * @return The "adult" status of the requested film. If the film cannot be
     *         found, then return false
     */
    @Override
    public boolean getAdult(int id) {
        // Retrieve the movie object from the movie map using the provided movie ID
        Movie movie = movieMap.get(id);

        // If the movie exists, return whether it's classified as adult; otherwise, return false
        return (movie != null) && movie.isAdult();
    }

    /**
     * Gets wether a particular film is classed as "direct-to-video", given the ID
     * number of that film
     * 
     * @param id The movie ID
     * @return The "direct-to-video" status of the requested film. If the film
     *         cannot be found, then return false
     */
    @Override
    public boolean getVideo(int id) {
        // Retrieve the movie object from the movie map using the provided movie ID
        Movie movie = movieMap.get(id);

        // If the movie exists, return whether it's classified as direct-to-video; otherwise, return false
        return (movie != null) && movie.isVideo();
    }

    /**
     * Gets the poster URL of a particular film, given the ID number of that film
     * 
     * @param id The movie ID
     * @return The poster URL of the requested film. If the film cannot be found,
     *         then return null
     */
    @Override
    public String getPoster(int id) {
        // Retrieve the movie object from the movie map using the provided movie ID
        Movie movie = movieMap.get(id);

        // If the movie exists, return its poster URL; otherwise, return null
        return (movie != null) ? movie.getPoster() : null;
    }

    /**
     * Sets the average IMDb score and the number of reviews used to generate this
     * score, for a particular film
     * 
     * @param id          The movie ID
     * @param voteAverage The average score on IMDb for the film
     * @param voteCount   The number of reviews on IMDb that were used to generate
     *                    the average score for the film
     * @return TRUE if the data able to be added, FALSE otherwise
     */
    @Override
    public boolean setVote(int id, double voteAverage, int voteCount) {
        // Retrieve the movie object from the movie map using the provided movie ID
        Movie movie = movieMap.get(id); 

        // If the movie doesn't exist, return false
        if (movie == null) return false;

        // Set the vote average and vote count for the movie
        movie.setVoteAverage(voteAverage);
        movie.setVoteCount(voteCount);

        // Return true indicating that the data was successfully set
        return true;
    }

    /**
     * Gets the average score for IMDb reviews of a particular film, given the ID
     * number of that film
     * 
     * @param id The movie ID
     * @return The average score for IMDb reviews of the requested film. If the film
     *         cannot be found, then return -1.0d
     */
    @Override
    public double getVoteAverage(int id) {
        // Retrieve the movie object from the movie map using the provided movie ID
        Movie movie = movieMap.get(id);

        // If the movie exists, return its IMDb average score; otherwise, return -1.0d
        if (movie != null) return movie.getVoteAverage();
        else return -1.0d;
    }

    /**
     * Gets the amount of IMDb reviews used to generate the average score of a
     * particular film, given the ID number of that film
     * 
     * @param id The movie ID
     * @return The amount of IMDb reviews used to generate the average score of the
     *         requested film. If the film cannot be found, then return -1
     */
    @Override
    public int getVoteCount(int id) {
        // Retrieve the movie object from the movie map using the provided movie ID
        Movie movie = movieMap.get(id);

        // If the movie exists, return the count of IMDb reviews; otherwise, return -1
        if (movie != null) return movie.getVoteCount();
        else return -1;
    }

    /**
     * Adds a given film to a collection. The collection is required to have an ID
     * number, a name, and a URL to a poster for the collection
     * 
     * @param filmID                 The movie ID
     * @param collectionID           The collection ID
     * @param collectionName         The name of the collection
     * @param collectionPosterPath   The URL where the poster can
     *                               be found
     * @param collectionBackdropPath The URL where the backdrop can
     *                               be found
     * @return TRUE if the data able to be added, FALSE otherwise
     */
    @Override
    public boolean addToCollection(int filmID, int collectionID, String collectionName, String collectionPosterPath, String collectionBackdropPath) {
        // Retrieve the movie by its ID
        Movie movie = movieMap.get(filmID);

        // If the movie exists, proceed to add it to the collection
        if (movie != null) {
            // Create a new Collection object with the provided details
            Collection collection = new Collection(collectionID, collectionName, collectionPosterPath, collectionBackdropPath);

            // Add the movie to the collection
            collection.addMovieToCollection(movie);

            // Set the collection for the movie
            movie.setBelongsToCollection(collection);

            // Add the collection to the collection map if it doesn't already exist
            if (!collectionMap.containsKey(collectionID)) collectionMap.put(collectionID, collection);
            else collectionMap.get(collectionID).addMovieToCollection(movie);

            // Return true indicating the collection was successfully added
            return true;
        } else return false;  // Movie not found
    }

    /**
     * Get all films that belong to a given collection
     * 
     * @param collectionID The collection ID to be searched for
     * @return An array of film IDs that correspond to the given collection ID. If
     *         there are no films in the collection ID, or if the collection ID is
     *         not valid, return an empty array.
     */
    @Override
    public int[] getFilmsInCollection(int collectionID) {
        // Retrieve the collection by its ID
        Collection collection = collectionMap.get(collectionID);

        // If the collection doesn't exist or has no movies, return an empty array
        if (collection == null || collection.getCollectionMovies().isEmpty()) {
            return new int[0];
        }

        // Retrieve the list of movies in the collection
        List<Movie> movies = collection.getCollectionMovies();
        int[] result = new int[movies.size()];

        // Fill the result array with movie IDs
        for (int i = 0; i < movies.size(); i++) {
            result[i] = movies.get(i).getId();
        }

        return result;  // Return the array of movie IDs
    }

    /**
     * Gets the name of a given collection
     * 
     * @param collectionID The collection ID
     * @return The name of the collection. If the collection cannot be found, then
     *         return null
     */
    @Override
    public String getCollectionName(int collectionID) {
        // Retrieve the collection by its ID
        Collection collection = collectionMap.get(collectionID);

        // If the collection doesn't exist, return null
        if (collection == null) {
            return null;
        }

        // Return the collection's name
        return collection.getCollectionName();
    }

    /**
     * Gets the poster URL for a given collection
     * 
     * @param collectionID The collection ID
     * @return The poster URL of the collection. If the collection cannot be found,
     *         then return null
     */
    @Override
    public String getCollectionPoster(int collectionID) {
        // Retrieve the collection by its ID
        Collection collection = collectionMap.get(collectionID);

        // If the collection doesn't exist, return null
        if (collection == null) {
            return null;
        }

        // Return the collection's poster URL
        return collection.getCollectionPosterPath();
    }

    /**
     * Gets the backdrop URL for a given collection
     * 
     * @param collectionID The collection ID
     * @return The backdrop URL of the collection. If the collection cannot be
     *         found, then return null
     */
    @Override
    public String getCollectionBackdrop(int collectionID) {
        // Retrieve the collection by its ID
        Collection collection = collectionMap.get(collectionID);

        // If the collection doesn't exist, return null
        if (collection == null) {
            return null;
        }

        // Return the collection's backdrop URL
        return collection.getCollectionBackdropPath();
    }

    /**
     * Gets the collection ID of a given film
     * 
     * @param filmID The movie ID
     * @return The collection ID for the requested film. If the film cannot be
     *         found, then return -1
     */
    @Override
    public int getCollectionID(int filmID) {
        // Retrieve the movie by its ID
        Movie movie = movieMap.get(filmID);
        
        // If the movie doesn't exist, return -1
        if (movie == null) return -1;

        // Get the collection that the movie belongs to
        Collection collection = movie.getBelongsToCollection();
        
        // If the movie doesn't belong to a collection, return -1
        if (collection == null) return -1;

        // Return the collection ID
        return collection.getCollectionID();
    }

    /**
     * Sets the IMDb ID for a given film
     * 
     * @param filmID The movie ID
     * @param imdbID The IMDb ID
     * @return TRUE if the data able to be set, FALSE otherwise
     */
    @Override
    public boolean setIMDB(int filmID, String imdbID) {
        // Retrieve the movie by its ID
        Movie movie = movieMap.get(filmID);
        
        // If the movie doesn't exist, return false
        if (movie == null) return false;
        
        // Set the IMDb ID for the movie
        movie.setImdbId(imdbID);
        
        // Return true to indicate success
        return true;
    }

    /**
     * Gets the IMDb ID for a given film
     * 
     * @param filmID The movie ID
     * @return The IMDb ID for the requested film. If the film cannot be found,
     *         return null
     */
    @Override
    public String getIMDB(int filmID) {
        // Retrieve the movie by its ID
        Movie movie = movieMap.get(filmID);
        
        // If the movie doesn't exist, return null
        if (movie == null) return null;
        
        // Return the IMDb ID for the movie
        return movie.getImdbId();
    }

    /**
     * Sets the popularity of a given film. If the popularity for a film already exists, replace it with the new value
     * 
     * @param id         The movie ID
     * @param popularity The popularity of the film
     * @return TRUE if the data able to be set, FALSE otherwise
     */
    @Override
    public boolean setPopularity(int id, double popularity) {
        // Retrieve the movie by its ID
        Movie movie = movieMap.get(id);
        
        // If the movie doesn't exist, return false
        if (movie == null) return false;
        
        // Set the popularity for the movie
        movie.setPopularity(popularity);
        
        // Return true to indicate success
        return true;
    }

    /**
     * Gets the popularity of a given film
     * 
     * @param id The movie ID
     * @return The popularity value of the requested film. If the film cannot be
     *         found, then return -1.0d. If the popularity has not been set, return 0.0
     */
    @Override
    public double getPopularity(int id) {
        // Retrieve the movie by its ID
        Movie movie = movieMap.get(id);
        
        // If the movie doesn't exist, return -1.0
        if (movie == null) return -1.0d;

        // Return the popularity value for the movie
        return movie.getPopularity();
    }


    /**
     * Adds a production company to a given film
     * 
     * @param id      The movie ID
     * @param company A Company object that represents the details on a production
     *                company
     * @return TRUE if the data able to be added, FALSE otherwise
     */
    @Override
    public boolean addProductionCompany(int id, Company company) {
        // Retrieve the movie by its ID
        Movie movie = movieMap.get(id);
        
        // If the movie doesn't exist, return false
        if (movie == null) return false;
        
        // Add the production company to the movie
        movie.getProductionCompanies().add(company);
        
        // Return true to indicate success
        return true;
    }   

    /**
     * Adds a production country to a given film
     * 
     * @param id      The movie ID
     * @param country A ISO 3166 string containing the 2-character country code
     * @return TRUE if the data able to be added, FALSE otherwise
     */
    @Override
    public boolean addProductionCountry(int id, String country) {
        // Retrieve the movie by its ID
        Movie movie = movieMap.get(id);
        
        // If the movie doesn't exist, return false
        if (movie == null) return false;
        
        // Add the production country to the movie
        movie.getProductionCountries().add(country);
        
        // Return true to indicate success
        return true;
    }

    /**
     * Gets all the production companies for a given film
     * 
     * @param id The movie ID
     * @return An array of Company objects that represent all the production
     *         companies that worked on the requested film. If the film cannot be
     *         found, then return null
     */
    @Override
    public Company[] getProductionCompanies(int id) {
        // Retrieve the movie by its ID
        Movie movie = movieMap.get(id);
        
        // If the movie doesn't exist, return null
        if (movie == null) return null;

        // Get the list of production companies for the movie
        List<Company> companies = movie.getProductionCompanies();
        
        // Convert the list to an array
        Company[] result = new Company[companies.size()];
        for (int i = 0; i < companies.size(); i++) {
            result[i] = companies.get(i);
        }

        // Return the array of production companies
        return result;
    }

    /**
     * Gets all the production companies for a given film
     * 
     * @param id The movie ID
     * @return An array of Strings that represent all the production countries (in
     *         ISO 3166 format) that worked on the requested film. If the film
     *         cannot be found, then return null
     */
    @Override
    public String[] getProductionCountries(int id) {
        // Retrieve the movie by its ID
        Movie movie = movieMap.get(id);
        
        // If the movie doesn't exist, return null
        if (movie == null) return null;

        // Get the list of production countries for the movie
        List<String> countries = movie.getProductionCountries();
        
        // Convert the list to an array
        String[] result = new String[countries.size()];
        for (int i = 0; i < countries.size(); i++) {
            result[i] = countries.get(i);
        }

        // Return the array of production countries
        return result;
    }

    /**
     * States the number of movies stored in the data structure
     * 
     * @return The number of movies stored in the data structure
     */
    @Override
    public int size() {
        return movieMap.size();
    }

    /**
     * Produces a list of movie IDs that have the search term in their title,
     * original title or their overview
     * 
     * @param searchTerm The term that needs to be checked
     * @return An array of movie IDs that have the search term in their title,
     *         original title or their overview. If no movies have this search term,
     *         then an empty array should be returned
     */
    @Override
    public int[] findFilms(String searchTerm) {
        // If the search term is empty or null, return an empty array
        if (searchTerm == null || searchTerm.isEmpty()) {
            return new int[0];
        }

        // Convert the search term to lowercase for case-insensitive comparison
        searchTerm = searchTerm.toLowerCase();

        // Create a list to store matching movie IDs
        List<Integer> matchingIDs = new List<>();
        
        // Get all movies from the map
        List<Movie> movies = movieMap.values();

        // Loop through all movies and check if the search term is in any of the relevant fields
        for (int i = 0; i < movies.size(); i++) {
            Movie movie = movies.get(i);
            if (movie == null) continue;

            // Get the title, original title, and overview for comparison
            String title = movie.getTitle() != null ? movie.getTitle().toLowerCase() : "";
            String originalTitle = movie.getOriginalTitle() != null ? movie.getOriginalTitle().toLowerCase() : "";
            String overview = movie.getOverview() != null ? movie.getOverview().toLowerCase() : "";

            // If the search term is found in any of the fields, add the movie ID to the list
            if (title.contains(searchTerm) || originalTitle.contains(searchTerm) || overview.contains(searchTerm)) {
                matchingIDs.add(movie.getId());
            }
        }

        // Convert the list of matching IDs to an array
        int[] result = new int[matchingIDs.size()];
        for (int i = 0; i < matchingIDs.size(); i++) {
            result[i] = matchingIDs.get(i);
        }

        // Return the array of matching movie IDs
        return result;
    }

}
