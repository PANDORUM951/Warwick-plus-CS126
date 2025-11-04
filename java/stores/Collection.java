package stores;

import structures.*;

public class Collection {
    private int collectionID;
    private String collectionName;
    private String collectionPosterPath;
    private String collectionBackdropPath;
    private List<Movie> collectionMovies;

    // Constructor
    public Collection(int collectionID, String collectionName, String collectionPosterPath, String collectionBackdropPath) {
        this.collectionID = collectionID;
        this.collectionName = collectionName;
        this.collectionPosterPath = collectionPosterPath;
        this.collectionBackdropPath = collectionBackdropPath;
        this.collectionMovies = new List<>();
    }

    public List<Movie> getCollectionMovies() {
        return collectionMovies;
    }

    public void setCollectionMovies(List<Movie> collectionMovies) {
        this.collectionMovies = collectionMovies;
    }

    public boolean addMovieToCollection(Movie movie) {
        collectionMovies.add(movie);
        return true;
    }

    public int getCollectionID() {
        return collectionID;
    }

    public void setCollectionID(int collectionID) {
        this.collectionID = collectionID;
    }

    public String getCollectionName() {
        return collectionName;
    }

    public void setCollectionName(String collectionName) {
        this.collectionName = collectionName;
    }

    public String getCollectionPosterPath() {
        return collectionPosterPath;
    }

    public void setCollectionPosterPath(String collectionPosterPath) {
        this.collectionPosterPath = collectionPosterPath;
    }

    public String getCollectionBackdropPath() {
        return collectionBackdropPath;
    }

    public void setCollectionBackdropPath(String collectionBackdropPath) {
        this.collectionBackdropPath = collectionBackdropPath;
    }
}

