package stores;

import java.time.LocalDateTime;

import interfaces.IRatings;
import structures.*;
import utils.RadixSort;

public class Ratings implements IRatings {
    Stores stores;
    HashMap<Integer, List<Rating>> userMap;
    HashMap<Integer, List<Rating>> movieMap;

    /**
     * The constructor for the Ratings data store. This is where you should
     * initialise your data structures.
     * @param stores An object storing all the different key stores,
     *               including itself
     */
    public Ratings(Stores stores) {
        this.stores = stores;
        userMap = new HashMap<>();
        movieMap = new HashMap<>();
    }

    /**
     * Adds a rating to the data structure. The rating is made unique by its user ID
     * and its movie ID
     * 
     * @param userID    The user ID
     * @param movieID   The movie ID
     * @param rating    The rating gave to the film by this user (between 0 and 5
     *                  inclusive)
     * @param timestamp The time at which the rating was made
     * @return TRUE if the data able to be added, FALSE otherwise
     */
    @Override
    public boolean add(int userid, int movieid, float rating, LocalDateTime timestamp) {
        // Add the rating to the user's list of ratings
        List<Rating> userRatings = userMap.get(userid);

        // Check if the user already has ratings for the movie
        if (userRatings != null) {
            // Check if this user has already rated this movie
            for (int i = 0; i < userRatings.size(); i++) {
                if (userRatings.get(i).getTmdbId() == movieid) {
                    return false;  // Return false if the movie is already rated
                }
            }
        } else {
            // If the user has no existing ratings, create a new list for their ratings
            userRatings = new List<>();
            userMap.put(userid, userRatings);
        }

        // Create the new rating object
        Rating rate = new Rating(userid, movieid, rating, timestamp);

        // Add the new rating to the user's list of ratings
        userRatings.add(rate);

        // Add the rating to the movie's list of ratings
        List<Rating> movieRatings = movieMap.get(movieid);
        if (movieRatings != null) {
            // Check if this movie is already rated by this user
            for (int i = 0; i < movieRatings.size(); i++) {
                if (movieRatings.get(i).getUserId() == userid) {
                    return false;  // Return false if the user has already rated this movie
                }
            }
        } else {
            // If the movie has no ratings, create a new list to store ratings
            movieRatings = new List<>();
            movieMap.put(movieid, movieRatings);
        }

        // Add the new rating to the movie's ratings list
        movieRatings.add(rate);

        return true;  // Return true to indicate the rating was successfully added
    }

    /**
     * Removes a given rating, using the user ID and the movie ID as the unique
     * identifier
     * 
     * @param userID  The user ID
     * @param movieID The movie ID
     * @return TRUE if the data was removed successfully, FALSE otherwise
     */
    @Override
    public boolean remove(int userid, int movieid) {
        // Remove from movieMap
        if (movieMap.containsKey(movieid)) {
            List<Rating> movieRatingList = movieMap.get(movieid);

            // Iterate over the movie's ratings list to find the rating for the specified user
            for (int i = 0; i < movieRatingList.size(); i++) {
                Rating rating = movieRatingList.get(i);
                if (rating.getUserId() == userid) {
                    // If found, remove the rating
                    movieRatingList.remove(rating);
                    break;
                }
            }
        }

        // Remove from movieMap
        if (userMap.containsKey(userid)) {
            List<Rating> userRatingList = userMap.get(userid);
            
            // Iterate over the user's ratings list to find the rating for the specified movie
            for (int i = 0; i < userRatingList.size(); i++) {
                Rating rating = userRatingList.get(i);
                if (rating.getTmdbId() == movieid) {
                    // If found, remove the rating and return true
                    userRatingList.remove(rating);
                    return true;
                }
            }
        }
        return false;  // Return false if the rating was not found
    }

    /**
     * Sets a rating for a given user ID and movie ID. Therefore, should the given
     * user have already rated the given movie, the new data should overwrite the
     * existing rating. However, if the given user has not already rated the given
     * movie, then this rating should be added to the data structure
     * 
     * @param userID    The user ID
     * @param movieID   The movie ID
     * @param rating    The new rating to be given to the film by this user (between
     *                  0 and 5 inclusive)
     * @param timestamp The time at which the new rating was made
     * @return TRUE if the data able to be added/updated, FALSE otherwise
     */
    @Override
    public boolean set(int userid, int movieid, float rating, LocalDateTime timestamp) {
        // Create the new rating object
        Rating newRating = new Rating(userid, movieid, rating, timestamp);

        // Update the user's list of ratings in userMap
        if (userMap.containsKey(userid)) {
            List<Rating> ratings = userMap.get(userid);
            
            // Search through the user's ratings to find and update the rating for the movie
            for (int i = 0; i < ratings.size(); i++) {
                Rating r = ratings.get(i);
                if (r.getTmdbId() == movieid) {
                    ratings.set(i, newRating); // Replace the old rating with the new one
                    break;
                }
            }
            
            // If the movie is not in the user's ratings, add the new rating
            if (!ratings.contains(newRating)) {
                ratings.add(newRating);
            }
        } else {
            // If the user has no existing ratings, create a new list and add the rating
            List<Rating> newList = new List<>();
            newList.add(newRating);
            userMap.put(userid, newList);
        }

        // Update the movie's list of ratings in movieMap
        if (movieMap.containsKey(movieid)) {
            List<Rating> ratings = movieMap.get(movieid);
            
            // Search through the movie's ratings to find and update the rating for the user
            for (int i = 0; i < ratings.size(); i++) {
                Rating r = ratings.get(i);
                if (r.getUserId() == userid) {
                    ratings.set(i, newRating);  // Replace the old rating with the new one
                    break;
                }
            }
            
            // If the movie is not rated by this user, add the new rating
            if (!ratings.contains(newRating)) {
                ratings.add(newRating);
            }
        } else {
            // If the movie has no ratings, create a new list and add the rating
            List<Rating> newList = new List<>();
            newList.add(newRating);
            movieMap.put(movieid, newList);
        }

        return true;  // Return true to indicate the rating was successfully set
    }

    /**
     * Get all the ratings for a given film
     * 
     * @param movieID The movie ID
     * @return An array of ratings. If there are no ratings or the film cannot be
     *         found in Ratings, then return an empty array
     */
    @Override
    public float[] getMovieRatings(int movieid) {
        // Create a list to store all the ratings for the specified movie
        List<Float> ratingsList = new List<>();

        // Get all the rating of a movie
        List<Rating> ratings = movieMap.get(movieid);
        if (ratings != null) {
            for (int j = 0; j < ratings.size(); j++) {
                ratingsList.add(ratings.get(j).getRating());
            }
        } else return new float[0];


        // Convert the ratingsList to an array
        float[] result = new float[ratingsList.size()];
        for (int i = 0; i < ratingsList.size(); i++) {
            result[i] = ratingsList.get(i);
        }
        return result;  // Return the array of ratings for the movie
    }

    /**
     * Get all the ratings for a given user
     * 
     * @param userID The user ID
     * @return An array of ratings. If there are no ratings or the user cannot be
     *         found in Ratings, then return an empty array
     */
    @Override
    public float[] getUserRatings(int userid) {
        // Check if the user exists in userMap
        if (!userMap.containsKey(userid)) return new float[0];  // Return an empty array if the user doesn't exist

        // Get the list of ratings for the user
        List<Rating> ratings = userMap.get(userid);
        
        // Convert the list of ratings to an array of floats
        float[] result = new float[ratings.size()];
        for (int i = 0; i < ratings.size(); i++) {
            result[i] = ratings.get(i).getRating();
        }
        return result;  // Return the array of ratings for the user
    }

    /**
     * Get the average rating for a given film
     * 
     * @param movieID The movie ID
     * @return Produces the average rating for a given film. 
     *         If the film cannot be found in Ratings, but does exist in the Movies store, return 0.0f. 
     *         If the film cannot be found in Ratings or Movies stores, return -1.0f.
     */
    @Override
    public float getMovieAverageRating(int movieid) {
        // Check if the movie exists in the stores and movieMap
        boolean movieExistsInStores = false;
        int[] allMovies = stores.getMovies().getAllIDs();  // Retrieve all movie IDs from the store
        for (int i = 0; i < allMovies.length; i++) {
            if (allMovies[i] == movieid) {
                movieExistsInStores = true;
                break;
            }
        }

        // If the movie doesn't exist in the store and in movieMap, return 0.0f
        if (movieExistsInStores && !movieMap.containsKey(movieid)) return 0.0f;
        // If the movie doesn't exist in the store but exists in movieMap, return -1.0f
        else if (!movieExistsInStores && !movieMap.containsKey(movieid)) return -1.0f;

        // Retrieve all the ratings for the movie and calculate the average
        float[] ratings = getMovieRatings(movieid);
        float sum = 0.0f;
        for (float rating : ratings) {
            sum += rating;
        }
        return sum / ratings.length;  // Return the average rating for the movie
    }

    /**
     * Get the average rating for a given user
     * 
     * @param userID The user ID
     * @return Produces the average rating for a given user. If the user cannot be
     *         found in Ratings, or there are no rating, return -1.0f
     */
    @Override
    public float getUserAverageRating(int userid) {
        // Get all ratings for the user
        float[] ratings = getUserRatings(userid);

        // If the user has no ratings, return -1.0f to indicate no average
        if (ratings.length == 0) return -1.0f;

        // Calculate the average of the user's ratings
        float sum = 0.0f;
        for (float rating : ratings) {
            sum += rating;
        }
        return sum / ratings.length;  // Return the average rating for the user
    }

    /**
     * Gets the top N movies with the most ratings, in order from most to least
     * 
     * @param num The number of movies that should be returned
     * @return A sorted array of movie IDs with the most ratings. The array should be
     *         no larger than num. If there are less than num movies in the store,
     *         then the array should be the same length as the number of movies in Ratings
     */
    @Override
    public int[] getMostRatedMovies(int num) {
        // Step 1: Create a set to store sorted movie IDs
        Set<Integer> sortedMovies = new Set<>();
        List<Integer> allMovies = movieMap.keys();  // Get all movie IDs
        List<List<Rating>> allMovieRatings = movieMap.values();  // Get all ratings lists

        // Step 2: Create a list for movie IDs and their rating counts
        List<KeyValuePair<Integer, Integer>> movieAndNumOfRatings = new List<>();
        for (int i = 0; i < allMovies.size(); i++) {
            KeyValuePair<Integer, Integer> movieRatings = new KeyValuePair<>(allMovies.get(i), allMovieRatings.get(i).size());
            movieAndNumOfRatings.add(movieRatings);
        }

        // Step 3: Sort the list using RadixSort
        List<KeyValuePair<Integer, Integer>> sortedList = RadixSort.radixSort(movieAndNumOfRatings);

        // Step 4: Collect the top 'num' movie IDs
        for (int i = 0; i < num && i < sortedList.size(); i++) {
            if (sortedList.get(i) != null) {
                sortedMovies.add(sortedList.get(i).getKey());
            } else {
                break;
            }
        }

        // Step 5: Convert the sortedMovies list to an array and return it
        int size = Math.min(num, sortedList.size());
        int[] sortedMoviesArray = new int[size];
        for (int i = 0; i < sortedMovies.size(); i++) {
            sortedMoviesArray[i] = sortedMovies.get(i);
        }

        return sortedMoviesArray; // Return the array of most-rated movies
    }


    /**
     * Gets the top N users with the most ratings, in order from most to least
     * 
     * @param num The number of users that should be returned
     * @return A sorted array of user IDs with the most ratings. The array should be
     *         no larger than num. If there are less than num users in the store,
     *         then the array should be the same length as the number of users in Ratings
     */
    @Override
    public int[] getMostRatedUsers(int num) {
        // Step 1: Create a set to store sorted user IDs
        Set<Integer> sortedUsers = new Set<>();
        List<Integer> allUsers = userMap.keys();  // Get all user IDs
        List<List<Rating>> allRatings = userMap.values();  // Get all ratings lists

        // Step 2: Create a list to store user IDs and their corresponding number of ratings
        List<KeyValuePair<Integer, Integer>> userAndNumOfRatings = new List<>();
        for (int i = 0; i < allUsers.size(); i++) {
            KeyValuePair<Integer, Integer> userRatings = new KeyValuePair<>(allUsers.get(i), allRatings.get(i).size());
            userAndNumOfRatings.add(userRatings);
        }

        // Step 3: Sort the list by rating count using RadixSort
        List<KeyValuePair<Integer, Integer>> sortedList = RadixSort.radixSort(userAndNumOfRatings);

        // Step 4: Add the top 'num' users to the sortedUsers set
        for (int i = 0; i < num && i < sortedList.size(); i++) {
            if (sortedList.get(i) != null) {
                sortedUsers.add(sortedList.get(i).getKey());
            } else {
                break;
            }
        }

        // Step 5: Convert the sortedUsers set to an array and return it
        int size = Math.min(num, sortedUsers.size());
        int[] sortedUsersArray = new int[size];
        for (int i = 0; i < sortedUsers.size(); i++) {
            sortedUsersArray[i] = sortedUsers.get(i);
        }

        return sortedUsersArray;  // Return the array of most-rated users
    }

    /**
     * Get the number of ratings that a movie has
     * 
     * @param movieid The movie id to be found
     * @return The number of ratings the specified movie has. 
     *         If the movie exists in the Movies store, but there are no ratings for it, then return 0. 
     *         If the movie does not exist in the Ratings or Movies store, then return -1.
     */
    @Override
    public int getNumRatings(int movieid) {
        List<Rating> movieRatings = movieMap.get(movieid);  // Get ratings for the movie
        int[] allMovies = stores.getMovies().getAllIDs();  // Retrieve all movie IDs from the store

        // Step 1: Check if the movie exists in stores. 
        for (int movie : allMovies) {
            if (movie == movieid) {
                // If the movie exists in stores, return its number of ratings (0 if no ratings)
                return (movieRatings == null) ? 0 : movieRatings.size();
            } 
        }

        // Step 2: Movie has not been added to stores, but has a rating in the Ratings store
        if (movieMap.containsKey(movieid)) {
            // Return the number of ratings if the movie is found in the Ratings store
            return (movieRatings == null) ? 0 : movieRatings.size();
        }

        // If the movie is not found in either store, return -1 to indicate it does not exist
        return -1;
    }

    /**
     * Get the highest average rated film IDs, in order of there average rating
     * (hightst first).
     * 
     * @param numResults The maximum number of results to be returned
     * @return An array of the film IDs with the highest average ratings, highest
     *         first. If there are less than num movies in the store,
     *         then the array should be the same length as the number of movies in Ratings
     */
    @Override
    public int[] getTopAverageRatedMovies(int numResults) {
        // Step 1: Create a list to store movie IDs and their corresponding average ratings
        Set<Integer> sortedMovies = new Set<>();
        List<Integer> allMovies = movieMap.keys();  // Get all movie IDs
    
        // Step 2: Create a list of KeyValuePair<Integer, Integer> where each entry is
        // a movie ID and the average rating that movie has received
        List<KeyValuePair<Integer, Integer>> movieAndAvgRatings = new List<>();
        for (int i = 0; i < allMovies.size(); i++) {
            // Calculate the average rating for the movie
            float avgRating = getMovieAverageRating(allMovies.get(i));
            
            // Multiply by 10 to convert float to int (to avoid precision issues in comparison)
            int avgRatingAsInt = (int) (avgRating * 100);
            
            // Add the movie ID and the scaled average rating to the list
            KeyValuePair<Integer, Integer> movieAvgRating = new KeyValuePair<>(allMovies.get(i), avgRatingAsInt);
            movieAndAvgRatings.add(movieAvgRating);
        }
    
        // Step 3: Sort the movieAndAvgRatings list based on the scaled average rating (in descending order)
        movieAndAvgRatings = RadixSort.radixSort(movieAndAvgRatings);  // Use Radix sort for efficient sorting
    
        // Step 4: Collect the top 'numResults' movies with the highest average ratings
        for (int i = 0; i < numResults && i < movieAndAvgRatings.size(); i++) {
            if (movieAndAvgRatings.get(i) != null) {
                sortedMovies.add(movieAndAvgRatings.get(i).getKey());  // Add movie ID to the sorted list
            } else break;
        }
    
        // Step 5: Convert the sortedMovies list to an array and return it
        int size = Math.min(numResults, movieAndAvgRatings.size());
        int[] sortedMoviesArray = new int[size];
        for (int i = 0; i < sortedMovies.size(); i++) {
            sortedMoviesArray[i] = sortedMovies.get(i);  // Fill the array with the top rated movie IDs
        }
    
        return sortedMoviesArray;  // Return the array of movie IDs with the highest average ratings
    }    

    /**
     * Gets the number of ratings in the data structure
     * 
     * @return The number of ratings in the data structure
     */
    @Override
    public int size() {
        int total = 0;  // Initialize the total number of ratings

        // Iterate over all users in the userMap
        for (int i = 0; i < userMap.capacity(); i++) {
            List<Rating> ratings = userMap.get(i);  // Get the list of ratings for the user
            if (ratings != null) {
                total += ratings.size();  // Add the number of ratings for this user to the total
            }
        }

        return total;  // Return the total number of ratings in the data structure
    }
}
